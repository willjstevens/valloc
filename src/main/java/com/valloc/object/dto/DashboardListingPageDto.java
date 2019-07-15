/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.dto;

import com.valloc.Visibility;

/**
 *
 *
 * 
 * @author wstevens
 */
//public class DashboardListingPageDto extends PageDto
public class DashboardListingPageDto
{

    private String username;
    private String name;
    private String path;
    private boolean isHome;
    private Visibility visibility;
    private boolean isSharedPrivately;
    private String description;
    private boolean isOwnerPage;
	private boolean isGuestPage;
	private String ownerFirstName;
	private String ownerLastName;
    private String listingMessage;
    private String homeMessage;
    private String editPath;
    private String displayPath;
    private String hrefUrl;

	public DashboardListingPageDto() {}

	public boolean isGuestPage() {
		return isGuestPage;
	}

    public void setGuestPage(boolean isGuestPage) {
		this.isGuestPage = isGuestPage;
	}

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public boolean isOwnerPage() {
        return isOwnerPage;
    }

    public void setOwnerPage(boolean ownerPage) {
        isOwnerPage = ownerPage;
    }

    public String getListingMessage() {
        return listingMessage;
    }

    public void setListingMessage(String listingMessage) {
        this.listingMessage = listingMessage;
    }

    public String getHomeMessage() {
        return homeMessage;
    }

    public void setHomeMessage(String homeMessage) {
        this.homeMessage = homeMessage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isHome() {
        return isHome;
    }

    public void setHome(boolean home) {
        isHome = home;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public boolean isSharedPrivately() {
        return isSharedPrivately;
    }

    public void setSharedPrivately(boolean sharedPrivately) {
        isSharedPrivately = sharedPrivately;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEditPath() {
        return editPath;
    }

    public void setEditPath(String editPath) {
        this.editPath = editPath;
    }

    public String getDisplayPath() {
        return displayPath;
    }

    public void setDisplayPath(String displayPath) {
        this.displayPath = displayPath;
    }

    public String getHrefUrl() {
        return hrefUrl;
    }

    public void setHrefUrl(String hrefUrl) {
        this.hrefUrl = hrefUrl;
    }
}
