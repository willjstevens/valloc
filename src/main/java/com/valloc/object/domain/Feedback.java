/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.domain;

/**
 * @author wstevens
 */
public interface Feedback
{
    public String getName();
    public void setName(String name);
    public String getEmail();
    public void setEmail(String email);
    public String getType();
    public void setType(String type);
    public String getComment();
    public void setComment(String comment);
}
