/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.serve;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 *
 * 
 * @author wstevens
 */
interface ServeStage
{
	boolean execute();
	ServeStage nextStage();
	void setResources(PageKey pageKey, HttpServletRequest request, HttpServletResponse response);
    void setPageCache(PageCache pageCache);
	void setNextStage(ServeStage pageLoadStage);
	void releaseForResponse();
	
	public abstract String toString();
}
