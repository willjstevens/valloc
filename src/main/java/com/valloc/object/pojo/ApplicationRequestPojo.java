/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.pojo;

import com.valloc.object.domain.ApplicationRequest;

import java.util.Date;

/**
 * @author wstevens
 */
public class ApplicationRequestPojo implements ApplicationRequest
{
    private int userId;
    private String requestRemoteAddress;
    private String requestRemoteHost;
    private int requestRemotePort;
    private String requestMethod;
    private String requestScheme;
    private String requestContextPath;
    private String requestUri;
    private String requestQueryString;
    private String requestUrl;
    private int requestContentLength;
    private String requestEncoding;
    private String requestIfNoneMatchHeader;
    private String requestUserAgentHeader;
    private int responseStatusCode;
    private String responseEncoding;
    private String responseEtagHeader;
    private Date insertTimestamp;
    private Date startTimestamp;
    private Date endTimestamp;
    private long totalTimeDiffMillis;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRequestRemoteAddress() {
        return requestRemoteAddress;
    }

    public void setRequestRemoteAddress(String requestRemoteAddress) {
        this.requestRemoteAddress = requestRemoteAddress;
    }

    public String getRequestRemoteHost() {
        return requestRemoteHost;
    }

    public void setRequestRemoteHost(String requestRemoteHost) {
        this.requestRemoteHost = requestRemoteHost;
    }

    public int getRequestRemotePort() {
        return requestRemotePort;
    }

    public void setRequestRemotePort(int requestRemotePort) {
        this.requestRemotePort = requestRemotePort;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestScheme() {
        return requestScheme;
    }

    public void setRequestScheme(String requestScheme) {
        this.requestScheme = requestScheme;
    }

    public String getRequestContextPath() {
        return requestContextPath;
    }

    public void setRequestContextPath(String requestContextPath) {
        this.requestContextPath = requestContextPath;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getRequestQueryString() {
        return requestQueryString;
    }

    public void setRequestQueryString(String requestQueryString) {
        this.requestQueryString = requestQueryString;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public int getRequestContentLength() {
        return requestContentLength;
    }

    public void setRequestContentLength(int requestContentLength) {
        this.requestContentLength = requestContentLength;
    }

    public String getRequestEncoding() {
        return requestEncoding;
    }

    public void setRequestEncoding(String requestEncoding) {
        this.requestEncoding = requestEncoding;
    }

    public String getRequestIfNoneMatchHeader() {
        return requestIfNoneMatchHeader;
    }

    public void setRequestIfNoneMatchHeader(String requestIfNoneMatchHeader) {
        this.requestIfNoneMatchHeader = requestIfNoneMatchHeader;
    }

    public String getRequestUserAgentHeader() {
        return requestUserAgentHeader;
    }

    public void setRequestUserAgentHeader(String requestUserAgentHeader) {
        this.requestUserAgentHeader = requestUserAgentHeader;
    }

    public int getResponseStatusCode() {
        return responseStatusCode;
    }

    public void setResponseStatusCode(int responseStatusCode) {
        this.responseStatusCode = responseStatusCode;
    }

    public String getResponseEncoding() {
        return responseEncoding;
    }

    public void setResponseEncoding(String responseEncoding) {
        this.responseEncoding = responseEncoding;
    }

    public String getResponseEtagHeader() {
        return responseEtagHeader;
    }

    public void setResponseEtagHeader(String responseEtagHeader) {
        this.responseEtagHeader = responseEtagHeader;
    }

    public Date getInsertTimestamp() {
        return insertTimestamp;
    }

    public void setInsertTimestamp(Date insertTimestamp) {
        this.insertTimestamp = insertTimestamp;
    }

    public Date getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Date getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Date endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public long getTotalTimeDiffMillis() {
        return totalTimeDiffMillis;
    }

    public void setTotalTimeDiffMillis(long totalTimeDiffMillis) {
        this.totalTimeDiffMillis = totalTimeDiffMillis;
    }
}
