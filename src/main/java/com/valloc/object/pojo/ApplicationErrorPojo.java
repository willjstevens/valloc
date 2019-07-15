/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.pojo;

import com.valloc.object.domain.ApplicationError;

import java.util.Date;

/**
 * @author wstevens
 */
public class ApplicationErrorPojo implements ApplicationError
{
    private String customMessage;
    private String toString;
    private String causeMessage;
    private String simpleClassName;
    private String fullClassName;
    private Date occuranceTimestamp;

    public ApplicationErrorPojo() {}

    public ApplicationErrorPojo(Throwable throwable, String customMessage) {
        this.customMessage = customMessage;
        toString = throwable.toString();
        simpleClassName = throwable.getClass().getSimpleName();
        fullClassName = throwable.getClass().getName();
        if (throwable.getCause() != null) {
            causeMessage = throwable.getCause().getMessage();
        }
    }


    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }

    public String getToString() {
        return toString;
    }

    public void setToString(String toString) {
        this.toString = toString;
    }

    public String getCauseMessage() {
        return causeMessage;
    }

    public void setCauseMessage(String causeMessage) {
        this.causeMessage = causeMessage;
    }

    public String getSimpleClassName() {
        return simpleClassName;
    }

    public void setSimpleClassName(String simpleClassName) {
        this.simpleClassName = simpleClassName;
    }

    public String getFullClassName() {
        return fullClassName;
    }

    public void setFullClassName(String fullClassName) {
        this.fullClassName = fullClassName;
    }

    public Date getOccuranceTimestamp() {
        return occuranceTimestamp;
    }

    public void setOccuranceTimestamp(Date occuranceTimestamp) {
        this.occuranceTimestamp = occuranceTimestamp;
    }
}
