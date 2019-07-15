/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.domain;

import java.util.Date;

/**
 * @author wstevens
 */
public interface ApplicationError
{
    public String getCustomMessage();
    public void setCustomMessage(String customMessage);
    public String getToString();
    public void setToString(String toString);
    public String getCauseMessage();
    public void setCauseMessage(String causeMessage);
    public String getSimpleClassName();
    public void setSimpleClassName(String simpleClassName);
    public String getFullClassName();
    public void setFullClassName(String fullClassName);
    public Date getOccuranceTimestamp();
    public void setOccuranceTimestamp(Date occuranceTimestamp);
}
