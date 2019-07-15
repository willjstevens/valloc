/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.pojo;

import com.valloc.object.domain.LinkServe;

import java.util.Date;

/**
 * @author wstevens
 */
public class LinkServePojo implements LinkServe
{
    private int ownerUserId;
    private int ownerPageId;
    private String url;
    private Date serveTimestamp;

    public int getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(int ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public int getOwnerPageId() {
        return ownerPageId;
    }

    public void setOwnerPageId(int ownerPageId) {
        this.ownerPageId = ownerPageId;
    }

    public String getRequestUrl() {
        return url;
    }

    public void setRequestUrl(String url) {
        this.url = url;
    }

    public Date getServeTimestamp() {
        return serveTimestamp;
    }

    public void setServeTimestamp(Date serveTimestamp) {
        this.serveTimestamp = serveTimestamp;
    }
}
