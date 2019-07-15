/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.serve;

import com.valloc.framework.SessionManager;
import com.valloc.io.IoUtil;
import com.valloc.object.domain.Page;
import com.valloc.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 
 *
 * @author wstevens
 */
public interface PageServeService extends VallocService
{
    public void servePage(String username, HttpServletRequest request, HttpServletResponse response);
    public void servePage(String username, String path, HttpServletRequest request, HttpServletResponse response);
	
	public PageKey buildHomePageKey(String username);
	public PageKey buildStandardPageKey(String username, String path);
	public PageCache addPageCache(Page page);
	public void addPageCache(Page page, byte[] pageContents);
	public void removePageCache(Page page);
	public void addPageContentsToCache(Page page, byte[] pageContents); 
	public void setPageAsHome(Page page);
	public void setPagePrivacy(Page page);
	public void removePageAsHome(Page page);
	public void setRequestAttributesMap(Map<String, Object> requestAttributes);

	// methods for stages
	ServeStage getPageServeStage(String httpSessionId, PageKey pageKey);
	void addPageServeStage(String httpSessionId, PageKey pageKey, ServeStage serveStage);
	void removePageServeStage(String httpSessionId, PageKey pageKey);
	PageCache getPageCache(PageKey pageKey);
	ApplicationService getApplicationService();
	ConfigurationService getConfigurationService();
	AccountService getAccountService();
	UtilityService getUtilityService();
	DashboardService getDashboardService();
    PageService getPageService();
	SessionManager getSessionManager();
    Map<String, Object> getRequestAttributes();
	IoUtil getIoUtil();

}
