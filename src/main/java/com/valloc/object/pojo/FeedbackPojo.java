/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.pojo;

import com.valloc.object.domain.Feedback;

/**
 * @author wstevens
 */
public class FeedbackPojo implements Feedback
{
    private String name;
    private String email;
    private String type;
    private String comment;

    public FeedbackPojo() {}

    public FeedbackPojo(String name, String email, String type, String comment) {
        this.name = name;
        this.email = email;
        this.type = type;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
