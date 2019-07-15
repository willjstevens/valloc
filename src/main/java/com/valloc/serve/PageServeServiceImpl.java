/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.serve;

import com.valloc.Category;
import com.valloc.Constants;
import com.valloc.dao.PageDao;
import com.valloc.framework.SessionManager;
import com.valloc.io.IoUtil;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import com.valloc.object.domain.Page;
import com.valloc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 *
 * @author wstevens
 */
@Service
public class PageServeServiceImpl extends AbstractServiceImpl implements PageServeService
{
	private static final Logger logger = LogManager.manager().newLogger(PageServeServiceImpl.class, Category.SERVICE_PAGE_SERVE);
	private static final Map<PageKey, PageCache> pageCacheStore = new HashMap<>();
	private static final Map<String, Map<PageKey, ServeStage>> userPageServeStages = new HashMap<String, Map<PageKey, ServeStage>>();
    private Map<String, Object> requestAttributes;
	@Autowired private ConfigurationService configurationService;
	@Autowired private ApplicationService applicationService;
	@Autowired private AccountService accountService;
	@Autowired private UtilityService utilityService;
	@Autowired private DashboardService dashboardService;
    @Autowired private PageService pageService;
	@Autowired private SessionManager sessionManager;
	@Autowired private IoUtil ioUtil;
	@Autowired private PageDao pageDao;


    @Transactional
    @Override
    public void servePage(String username, HttpServletRequest request, HttpServletResponse response) {
        username = username.toLowerCase();
        servePage(new PageKey(username), request, response);
    }

    @Transactional
    @Override
    public void servePage(String username, String path, HttpServletRequest request, HttpServletResponse response) {
        username = username.toLowerCase();
        path = path.toLowerCase();
        servePage(new PageKey(username, path), request, response);
    }

	public void servePage(PageKey pageKey, HttpServletRequest request, HttpServletResponse response) {
		HttpSession httpSession = request.getSession();
		final String httpSessionId = httpSession.getId();
		// get all active stages for one session-based client
		Map<PageKey, ServeStage> userStages = userPageServeStages.get(httpSessionId);
		if (userStages == null) {
			userStages = new HashMap<PageKey, ServeStage>();
			userPageServeStages.put(httpSessionId, userStages);
		}
		
		// Now get the current stage for a user's particular page request.
		ServeStage serveStage = userStages.get(pageKey);
		if (serveStage == null) { // first time in for this page so set as initial
			serveStage = new InitialStage(this);
			userStages.put(pageKey, serveStage);
		}
		if (logger.isFiner()) {
			logger.finer("Serving page for session %s and page key %s for stage %s.", httpSessionId, pageKey, serveStage);
		}
		
		// Cycle through for next time around if necessary.
		boolean doExecuteNext = false;
		serveStage.setResources(pageKey, request, response);
		do {
			doExecuteNext = serveStage.execute();
			if (doExecuteNext) {
				serveStage = serveStage.nextStage();
				if (logger.isFiner()) {
					logger.finer("Setting next execute stage for %s on page key %s to %s.", httpSessionId, pageKey, serveStage);
				}
			} else {
				serveStage.releaseForResponse();
				if (logger.isFiner()) {
					logger.finer("Releasing page load stages for %s on page key %s.", httpSessionId, pageKey);
				}
			}
		} while (serveStage != null && doExecuteNext);
	}

	@Override
	public PageCache getPageCache(PageKey pageKey) {
		PageCache pageCache = pageCacheStore.get(pageKey);
		// if not found, then not already loaded so go fetch
		if (pageCache == null) {
			if (logger.isFiner()) {
				logger.finer("Cache miss for page key %s. About to load page from database.", pageKey);
			}
			Page page = null;
			if (pageKey.isHomePage()) {
				page = pageDao.findHomePageByUsername(pageKey.getUsername());
			} else {
				page = pageDao.findPage(pageKey.getUsername(), pageKey.getPath());
			}
			if (page != null) { // could be null if page or username does not exist
                pageCache = addPageCache(page);
				if (logger.isFiner()) {
					logger.finer("Page cache created and loaded from database for page %s.", pageKey);
				}
			} else {
				if (logger.isFiner()) {
					logger.finer("No page found for page key %s.", pageKey);
				}
			}
		} else {
			if (logger.isFiner()) {
				logger.finer("Cache hit for page key %s.", pageKey);
			}
		}
		return pageCache; // could be null if page does not exist
	}
	
	@Override
	public PageCache addPageCache(Page page) {
		PageKey key = buildKey(page);
		if (pageCacheStore.containsKey(key)) {
			String msg = String.format("Page key already exists (%s). It should either not exist or be cleared before adding.", key);
			IllegalArgumentException e = new IllegalArgumentException(msg);
			logger.error(msg, e);
			throw e;
		}
		// build the cache object 
		PageCache cache = new PageCache(page);
		pageCacheStore.put(key, cache);
		// If the page was added as a special home PageKey, then we need to re-add with a key
		//		for standard page lookup for if the user hits a home page with a full path and
		//		in case this page is marked no longer as home
		if (page.isHome()) {
			key = buildStandardPageKey(page.getUser().getUsername(), page.getPath());
			pageCacheStore.put(key, cache);
		}
		if (logger.isFine()) {
			logger.fine("Page cache was added for key %s.", key);
		}
		return cache;
	}
	
	@Override
	public void addPageCache(Page page, byte[] pageContents) {
		PageCache cache = addPageCache(page);
		cache.setPageContents(pageContents);
		if (logger.isFinest()) {
			logger.finest("Added page cache with byte contents (as string):\n%s", new String(pageContents, Constants.CHARSET_UTF_8));
		}
	}

	@Override
	public void setPageAsHome(Page page) {
		if (!page.isHome()) {
			throw new IllegalArgumentException("Page was expected to marked as home.");
		}
		String username = page.getUser().getUsername();
		// first try and fetch existing page cache object
		PageKey pageKey = buildStandardPageKey(username, page.getPath());
		PageCache cache = pageCacheStore.get(pageKey);
		if (cache != null) { // found so just set flag and re-use cache object
			cache.setHome(true);
		} else {
            cache = addPageCache(page);
		}
		// now create home page key and set into store
		PageKey homePageKey = buildHomePageKey(username);
		pageCacheStore.put(homePageKey, cache);
		if (logger.isFine()) {
			logger.fine("Added page as home into cache for page key %s.", pageKey);
		}
	}
	
	@Override
	public void setPagePrivacy(Page page) {
		String username = page.getUser().getUsername();
		// first try and fetch existing page cache object
		PageKey pageKey = buildStandardPageKey(username, page.getPath());
		PageCache cache = pageCacheStore.get(pageKey);
		if (cache != null) { // found so just set visibility and re-use cache object
			cache.setVisibility(page.getVisibility());
		} else {
            addPageCache(page);
		}
		if (logger.isFine()) {
			logger.fine("Set privacy to %s for page key %s.", page.getVisibility(), pageKey);
		}
	}
	
	@Override
	public void removePageCache(Page page) {
		PageKey key = buildKey(page);
		boolean wasRemoved = pageCacheStore.remove(key) != null;
		if (logger.isFine()) {
			logger.fine("Page key %s was found and removed: %b", key, wasRemoved);
		}
		if (page.isHome()) {
			// If home page then also need to remove corresponding full-page path key
			key = buildStandardPageKey(page.getUser().getUsername(), page.getPath());
			wasRemoved = pageCacheStore.remove(key) != null;
            if (logger.isFine()) {
                logger.fine("Page key %s for home was found and removed: %b", key, wasRemoved);
            }
		}
	}
	
	@Override
	public void removePageAsHome(Page page) {
		String username = page.getUser().getUsername();
		PageKey homePageKey = buildHomePageKey(username);
		PageCache cache = pageCacheStore.remove(homePageKey);
		if (cache == null) {
			logger.warn("Cache object SHOULD not be null for key %s.", homePageKey);
		}
		// now set cache standard page and path object flag to be false
		PageKey standardPageKey = buildStandardPageKey(username, page.getPath());
		cache = pageCacheStore.get(standardPageKey);
        if (cache != null) {
            cache.setHome(false);
            if (logger.isFine()) {
                logger.fine("Removed page as home from cache for key %s.", standardPageKey);
            }
        } else {
            if (logger.isFiner()) {
            	logger.finer("Cache entry was null when attempting to remove from home page cache for key %s.", homePageKey);
            }
        }
	}
	
	@Override
	public void addPageContentsToCache(Page page, byte[] pageContents) {
		String username = page.getUser().getUsername();
		PageKey key = buildStandardPageKey(username, page.getPath());
        PageCache cache = pageCacheStore.get(key);
        if (cache != null) { // could be null if page is a home page
            cache.setPageContents(pageContents);
        }
		if (page.isHome()) { // do the same for the home page cache
			key = buildHomePageKey(username);
			cache = pageCacheStore.get(key);
			cache.setPageContents(pageContents);
		}
	}

	private PageKey buildKey(Page page) {
		PageKey key = null;
		String username = page.getUser().getUsername();
		if (page.isHome()) {
			key = buildHomePageKey(username);
		} else {
			key = buildStandardPageKey(username, page.getPath());
		}
		return key;
	}

	@Override
	public PageKey buildHomePageKey(String username) {
		username = username.toLowerCase();
		return new PageKey(username);
	}

	@Override
	public PageKey buildStandardPageKey(String username, String path) {
		username = username.toLowerCase();
		path = path.toLowerCase();
		return new PageKey(username, path);
	}

	@Override
	public ServeStage getPageServeStage(String httpSessionId, PageKey pageKey) {
		ServeStage retval = null;
		Map<PageKey, ServeStage> pageServeStages = userPageServeStages.get(httpSessionId);
		if (pageServeStages != null && !pageServeStages.isEmpty()) {
			retval = pageServeStages.get(pageKey); // could be null
		}
		if (logger.isFiner()) {
			logger.finer("Returning the following stage for httpSessionId %s and PageKey %s: %s.", httpSessionId, pageKey, retval);
		}
		return retval;
	}

	@Override
	public void addPageServeStage(String httpSessionId, PageKey pageKey, ServeStage serveStage) {
		Map<PageKey, ServeStage> pageServeStages = userPageServeStages.get(httpSessionId);
		if (pageServeStages == null) {
			pageServeStages = new HashMap<>();
			userPageServeStages.put(httpSessionId, pageServeStages);
			if (logger.isFiner()) {
				logger.finer("Created serve stages container for httpSessionId %s.", httpSessionId);
			}
		}
		ServeStage previousServeStage = pageServeStages.put(pageKey, serveStage);
		if (logger.isFiner()) {
			logger.finer("Added the following stage for httpSessionId %s and PageKey %s: %s [previous stage: %s].", httpSessionId, pageKey, serveStage, previousServeStage);
		}
	}

	@Override
	public void removePageServeStage(String httpSessionId, PageKey pageKey) {
		Map<PageKey, ServeStage> pageServeStages = userPageServeStages.get(httpSessionId);
		ServeStage removedStage = pageServeStages.remove(pageKey);
		if (logger.isFiner()) {
			logger.finer("Removed the following stage for httpSessionId %s and PageKey %s: %s.", httpSessionId, pageKey, removedStage);
		}
		if (pageServeStages.isEmpty()) {
			userPageServeStages.remove(httpSessionId);
			if (logger.isFiner()) {
				logger.finer("Removed the following page stage container for httpSessionId %s.", httpSessionId);
			}
		}
	}

	@Override
	public ApplicationService getApplicationService() {
		return applicationService;
	}

	@Override
	public ConfigurationService getConfigurationService() {
		return configurationService;
	}
	
	@Override
	public AccountService getAccountService() {
		return accountService;
	}
	
	@Override
	public UtilityService getUtilityService() {
		return utilityService;
	}
	
	@Override
	public DashboardService getDashboardService() {
		return dashboardService;
	}

    @Override
    public PageService getPageService() { return pageService; }

    @Override
	public SessionManager getSessionManager() {
		return sessionManager;
	}

	@Override
	public IoUtil getIoUtil() {
		return ioUtil;
	}

    @Override
    public void setRequestAttributesMap(Map<String, Object> requestAttributes) {
        this.requestAttributes = requestAttributes;
    }

    @Override
    public Map<String, Object> getRequestAttributes() {
        return requestAttributes;
    }
}
