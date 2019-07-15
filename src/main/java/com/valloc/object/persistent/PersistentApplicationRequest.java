/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.persistent;

import com.valloc.object.domain.ApplicationRequest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author wstevens
 */
@Entity
@Table(name="application_request")
@SequenceGenerator(
        name="id_seq",
        allocationSize=1,
        sequenceName="application_request_id_seq"
)
public class PersistentApplicationRequest extends DomainObject implements ApplicationRequest
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
    private Date startTimestamp;
    private Date endTimestamp;
    private long totalTimeDiffMillis;

    @Column(name="user_id", nullable=true)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name="request_remote_address", nullable=true)
    public String getRequestRemoteAddress() {
        return requestRemoteAddress;
    }

    public void setRequestRemoteAddress(String requestRemoteAddress) {
        this.requestRemoteAddress = requestRemoteAddress;
    }

    @Column(name="request_remote_host", nullable=true)
    public String getRequestRemoteHost() {
        return requestRemoteHost;
    }

    public void setRequestRemoteHost(String requestRemoteHost) {
        this.requestRemoteHost = requestRemoteHost;
    }

    @Column(name="request_remote_port", nullable=true)
    public int getRequestRemotePort() {
        return requestRemotePort;
    }

    public void setRequestRemotePort(int requestRemotePort) {
        this.requestRemotePort = requestRemotePort;
    }

    @Column(name="request_method", nullable=true)
    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    @Column(name="request_scheme", nullable=true)
    public String getRequestScheme() {
        return requestScheme;
    }

    public void setRequestScheme(String requestScheme) {
        this.requestScheme = requestScheme;
    }

    @Column(name="request_context_path", nullable=true)
    public String getRequestContextPath() {
        return requestContextPath;
    }

    public void setRequestContextPath(String requestContextPath) {
        this.requestContextPath = requestContextPath;
    }

    @Column(name="request_uri", nullable=true)
    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    @Column(name="request_query_string", nullable=true)
    public String getRequestQueryString() {
        return requestQueryString;
    }

    public void setRequestQueryString(String requestQueryString) {
        this.requestQueryString = requestQueryString;
    }

    @Column(name="request_url", nullable=true)
    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    @Column(name="request_content_length", nullable=true)
    public int getRequestContentLength() {
        return requestContentLength;
    }

    public void setRequestContentLength(int requestContentLength) {
        this.requestContentLength = requestContentLength;
    }

    @Column(name="request_encoding", nullable=true)
    public String getRequestEncoding() {
        return requestEncoding;
    }

    public void setRequestEncoding(String requestEncoding) {
        this.requestEncoding = requestEncoding;
    }

    @Column(name="request_if_none_match_header", nullable=true)
    public String getRequestIfNoneMatchHeader() {
        return requestIfNoneMatchHeader;
    }

    public void setRequestIfNoneMatchHeader(String requestIfNoneMatchHeader) {
        this.requestIfNoneMatchHeader = requestIfNoneMatchHeader;
    }

    @Column(name="request_user_agent_header", nullable=true)
    public String getRequestUserAgentHeader() {
        return requestUserAgentHeader;
    }

    public void setRequestUserAgentHeader(String requestUserAgentHeader) {
        this.requestUserAgentHeader = requestUserAgentHeader;
    }

    @Column(name="response_status_code", nullable=true)
    public int getResponseStatusCode() {
        return responseStatusCode;
    }

    public void setResponseStatusCode(int responseStatusCode) {
        this.responseStatusCode = responseStatusCode;
    }

    @Column(name="response_encoding", nullable=true)
    public String getResponseEncoding() {
        return responseEncoding;
    }

    public void setResponseEncoding(String responseEncoding) {
        this.responseEncoding = responseEncoding;
    }

    @Column(name="response_etag_header", nullable=true)
    public String getResponseEtagHeader() {
        return responseEtagHeader;
    }

    public void setResponseEtagHeader(String responseEtagHeader) {
        this.responseEtagHeader = responseEtagHeader;
    }

    @Column(name="start_timestamp", nullable=true)
    public Date getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    @Column(name="end_timestamp", nullable=true)
    public Date getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Date endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    @Column(name="total_time_diff_millis", nullable=true)
    public long getTotalTimeDiffMillis() {
        return totalTimeDiffMillis;
    }

    public void setTotalTimeDiffMillis(long totalTimeDiffMillis) {
        this.totalTimeDiffMillis = totalTimeDiffMillis;
    }
}
