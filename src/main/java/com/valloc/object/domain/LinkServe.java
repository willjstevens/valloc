/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.domain;

import java.util.Date;

/**
 * @author wstevens
 */
public interface LinkServe
{
    public int getOwnerUserId();
    public void setOwnerUserId(int userId);
    public int getOwnerPageId();
    public void setOwnerPageId(int pageId);
    public String getRequestUrl();
    public void setRequestUrl(String url);
    public Date getServeTimestamp();
    public void setServeTimestamp(Date serveTimestamp);
}
