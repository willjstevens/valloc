/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.domain;

import java.util.Date;

/**
 * @author wstevens
 */
public interface ApplicationRequest
{
    public int getUserId();
    public void setUserId(int userId);
    public String getRequestRemoteAddress();
    public void setRequestRemoteAddress(String requestRemoteAddress);
    public String getRequestRemoteHost();
    public void setRequestRemoteHost(String requestRemoteHost);
    public int getRequestRemotePort();
    public void setRequestRemotePort(int requestRemotePort);
    public String getRequestMethod();
    public void setRequestMethod(String requestMethod);
    public String getRequestScheme();
    public void setRequestScheme(String requestScheme);
    public String getRequestContextPath();
    public void setRequestContextPath(String requestContextPath);
    public String getRequestUri();
    public void setRequestUri(String requestUri);
    public String getRequestQueryString();
    public void setRequestQueryString(String requestQueryString);
    public String getRequestUrl();
    public void setRequestUrl(String requestUrl);
    public int getRequestContentLength();
    public void setRequestContentLength(int requestContentLength);
    public String getRequestEncoding();
    public void setRequestEncoding(String requestEncoding);
    public String getRequestIfNoneMatchHeader();
    public void setRequestIfNoneMatchHeader(String requestIfNoneMatchHeader);
    public String getRequestUserAgentHeader();
    public void setRequestUserAgentHeader(String requestUserAgentHeader);
    public int getResponseStatusCode();
    public void setResponseStatusCode(int responseStatusCode);
    public String getResponseEncoding();
    public void setResponseEncoding(String responseEncoding);
    public String getResponseEtagHeader();
    public void setResponseEtagHeader(String responseEtagHeader);
    public Date getInsertTimestamp();
    public void setInsertTimestamp(Date insertTimestamp);
    public Date getStartTimestamp();
    public void setStartTimestamp(Date startTimestamp);
    public Date getEndTimestamp();
    public void setEndTimestamp(Date endTimestamp);
    public long getTotalTimeDiffMillis();
    public void setTotalTimeDiffMillis(long totalTimeDiffMillis);
}
