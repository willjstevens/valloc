/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.serve;

import com.valloc.Category;
import com.valloc.Constants;
import com.valloc.io.IoUtil;
import com.valloc.io.JspWriterAdapter;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import static com.valloc.UrlConstants.PAGE_BUILDER;

/**
 *
 *
 * 
 * @author wstevens
 */
class WriteContentsStage extends AbstractServeStage
{
	private static final Logger logger = LogManager.manager().newLogger(LoginStage.class, Category.SERVICE_PAGE_SERVE);
	private static final String CONTENT_TYPE_TEXT_HTML = "text/html";
	
	public WriteContentsStage(PageServeService pageServeService) {
		super(pageServeService);
	}

	@Override
	public boolean execute() {
		byte[] pageContents = pageCache.getPageContents();
		String username = pageKey.getUsername();
		String path = pageCache.getPath();
		IoUtil ioUtil = pageServeService.getIoUtil();
		if (pageContents == null) {
			if (logger.isFine()) {
				logger.fine("No page contents byte[] found so attempting to read off of disk for page key %s.", pageKey);
			}
			// make attempt to get contents from file on disk
			pageContents = ioUtil.readPageFileIntoByteArray(username, path);
			if (pageContents != null) { 
				// file was found and read-in, so store contents for future
				pageCache.setPageContents(pageContents);
				if (logger.isFine()) {
					logger.fine("Page contents byte[] loaded from file read off disk for page key %s.", pageKey);
				}
			} else {
				if (logger.isFine()) {
					logger.fine("No file on disk when retrieving page contents byte[] so attempting to re-generate file and capture contents, for for page key %s.", pageKey);
				}
                // load request attributes
                Map<String, Object> loadedMap = pageServeService.getRequestAttributes();
                for (String key : loadedMap.keySet()) {
                    Object value = loadedMap.get(key);
                    request.setAttribute(key, value);
                }
				// file was not found, so try and write file to disk and re-read
				JspWriterAdapter jspWriterAdapter = 
						ioUtil.newUserPageFileWriterResponseAdapter(request, response, PAGE_BUILDER, username, path);
				pageServeService.getPageService().generatePageFile(username, path, jspWriterAdapter);
				pageContents = pageCache.getPageContents(); // now populated
			}
		}
		// write page Contents out to response
		response.setContentType(CONTENT_TYPE_TEXT_HTML);
		response.setCharacterEncoding(Constants.CHARSET_UTF_8_STR);
		response.setContentLength(pageContents.length);
		OutputStream responseOutputStream = null;
		try {
			responseOutputStream = response.getOutputStream();
		} catch (IOException e) {
			logger.error("Error while trying to get output stream on response for page key %s.", e, pageKey);
		}
		ioUtil.writeByteArrayToOutputStream(pageContents, responseOutputStream);
		if (logger.isFine()) {
			logger.fine("Successfully served page for page key %s.", pageKey);
		}
		
		cleanupPageStage();
		return false;
	}

}
