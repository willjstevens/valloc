/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.persistent;

import com.valloc.object.domain.LinkServe;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author wstevens
 */
@Entity
@Table(name="link_serve")
@SequenceGenerator(
        name="id_seq",
        allocationSize=1,
        sequenceName="link_serve_id_seq"
)
public class PersistentLinkServe extends DomainObject implements LinkServe
{
    private int ownerUserId;
    private int ownerPageId;
    private String requestUrl;
    private Date serveTimestamp;

    @Column(name="owner_user_id", nullable=true)
    public int getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(int ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    @Column(name="owner_page_id", nullable=true)
    public int getOwnerPageId() {
        return ownerPageId;
    }

    public void setOwnerPageId(int ownerPageId) {
        this.ownerPageId = ownerPageId;
    }

    @Column(name="request_url", nullable=true)
    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    @Column(name="serve_timestamp", nullable=true)
    public Date getServeTimestamp() {
        return serveTimestamp;
    }

    public void setServeTimestamp(Date serveTimestamp) {
        this.serveTimestamp = serveTimestamp;
    }
}
