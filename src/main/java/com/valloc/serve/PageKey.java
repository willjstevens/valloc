/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.serve;

/**
 *
 *
 * 
 * @author wstevens
 */
final class PageKey
{
	private static final String HOME_PAGE_PLACEHOLDER = "{{$$$$HOME$$$$}}"; 
	private String username;
	private String path;
	
	PageKey(String username) {
		this.username = username;
		this.path = HOME_PAGE_PLACEHOLDER;
	}

	PageKey(String username, String path) {
		if (path.equals(HOME_PAGE_PLACEHOLDER)) {
			throw new IllegalArgumentException("Path cannot be equal to reserved home constant: " + HOME_PAGE_PLACEHOLDER);
		}
		this.username = username;
		this.path = path;
	}
	
	public String getUsername() {
		return username;
	}
	public String getPath() {
		return path;
	}
	public boolean isHomePage() {
		return path.equals(HOME_PAGE_PLACEHOLDER);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageKey other = (PageKey) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PageKey [username=").append(username).append(", path=")
				.append(path).append("]");
		return builder.toString();
	}
}
