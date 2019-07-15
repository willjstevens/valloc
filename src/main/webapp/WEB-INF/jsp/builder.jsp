<!doctype html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
    <meta charset="utf-8" />
    <meta name="description" content='${tagline}' />
    <title><c:out value="${page.name}" /></title>
    <%-- Weird. This needs to be a static include because c:import was taking this path from the current JSP directory --%>
    <%@include file="/r/env-prod.html"%>
    <link href="/r/home-1.2/lib/css/font-awesome.css" rel="stylesheet">
    <link href="/r/c/builder.css?v=80709v1" rel="stylesheet" />
    <link rel="image_src" href="/r/i/logo-v.png" />
    <%@ include file="include/marketing.jsp" %>
</head>
<body ng-app="vlc">
<noscript>
    <h2>${clientEnableJavascript}</h2>
</noscript>
<%@include file="include/top-navbar.jsp" %>
<div class="container" ng-controller="BuilderController">
    <div class="row">
        <div class="col-xs-12 col-sm-12 text-center">
            <h2 class="name">
                ${page.name}
                <a href="/page/directedit/${user.username}/${page.path}" class="btn btn-default pull-right hidden-xs">
                    ${button_edit}
                    <span class="glyphicon glyphicon-edit"></span>
                </a>
            </h2>
        </div>
    </div>
    <div class="row">
        <%--This section for TABLETS and DESKTOPS --%>
        <c:forEach var="column" items="${page.columns}" varStatus="columnStatus">
            <div class="col-xs-12 col-sm-4 hidden-xs">
                <c:if test="${not empty column.sections}">
                    <ul class="list-unstyled page-column">
                        <c:forEach var="section" items="${column.sections}" varStatus="sectionStatus">
                            <li class="section">
                                <c:out value="${section.name}" />
                            </li>
                            <li><hr class="section-hr" /></li>
                            <c:forEach var="link" items="${section.links}">
                                <li class="link">
                                    <a href="/srv?url=${utilityService.urlEncode(link.url)}&uid=${user.id}&pid=${page.id}" target="_blank">
                                        <c:out value="${link.name}" />
                                    </a>
                                    <c:if test="${not empty link.linkNotes}">
                                        <span class="dropdown-new">
                                            <a class="dropdown-toggle link-decorator-icon">
                                                <c:if test="${fn:length(link.linkNotes) eq 1}">
                                                    <i class="icon-comment linknote-icon"></i>
                                                </c:if>
                                                <c:if test="${fn:length(link.linkNotes) gt 1}">
                                                    <i class="icon-comments-alt linknote-icon"></i>
                                                </c:if>
                                            </a>
                                            <ul class="dropdown-content">
                                                <c:set var="styleCounter" value="${0}"/>
                                                <c:forEach var="linkNote" items="${link.linkNotes}" varStatus="linkNoteStatus">
                                                    <div class="linknote">
                                                        <div class="${styleCounter % 2 == 0 ? 'linknote-header-left' : 'linknote-header-right'}">
                                                            <span class="linknote-header-name">
                                                                <c:out value="${linkNote.user.firstName}" /> <c:out value="${linkNote.user.lastName}" />
                                                            </span>
                                                            <span class="linknote-header-postTimestamp">
                                                                <c:out value="${utilityService.toConversationFormat(linkNote.postTimestamp)}" />
                                                            </span>
                                                        </div>
                                                        <div class="linknote-body bubble ${styleCounter % 2 == 0 ? 'me' : 'you'}">
                                                            <c:out value="${linkNote.note}" />
                                                        </div>
                                                    </div>
                                                    <c:set var="styleCounter" value="${styleCounter + 1}"/>
                                                </c:forEach>
                                            </ul>
                                        </span>
                                    </c:if>
                                </li>
                            </c:forEach>
                            <li class="section-br"></li>
                        </c:forEach>
                    </ul>
                </c:if>
            </div>
        </c:forEach>


        <%--This section for PHONES and SMALL DEVICES - it makes the columns SWIPE-ABLE--%>
        <div class="swiper-container visible-xs">
            <div class="swiper-wrapper">
                <c:forEach var="column" items="${page.columns}" varStatus="columnStatus">
                <div class="col-xs-12 list-unstyled  swiper-slide">
                    <c:if test="${not empty column.sections}">
                        <div class="page-column">
                            <c:forEach var="section" items="${column.sections}" varStatus="sectionStatus">
                                <div class="section">
                                    <c:out value="${section.name}" />
                                </div>
                                <div><hr class="section-hr" /></div>
                                <c:forEach var="link" items="${section.links}">
                                    <div class="link">
                                        <a href="/srv?url=${utilityService.urlEncode(link.url)}&uid=${user.id}&pid=${page.id}" target="_blank">
                                            <c:out value="${link.name}" />
                                        </a>
                                        <c:if test="${not empty link.linkNotes}">
                                        <span class="dropdown-new">
                                            <a class="dropdown-toggle link-decorator-icon">
                                                <c:if test="${fn:length(link.linkNotes) eq 1}">
                                                    <i class="icon-comment-alt linknote-icon"></i>
                                                </c:if>
                                                <c:if test="${fn:length(link.linkNotes) gt 1}">
                                                    <i class="icon-comments-alt linknote-icon"></i>
                                                </c:if>
                                            </a>
                                            <ul class="dropdown-content">
                                                <c:set var="styleCounter" value="${0}"/>
                                                <c:forEach var="linkNote" items="${link.linkNotes}" varStatus="linkNoteStatus">
                                                    <div class="linknote">
                                                        <div class="${styleCounter % 2 == 0 ? 'linknote-header-left' : 'linknote-header-right'}">
                                                            <span class="linknote-header-name">
                                                                <c:out value="${linkNote.user.firstName}" /> <c:out value="${linkNote.user.lastName}" />
                                                            </span>
                                                            <span class="linknote-header-postTimestamp">
                                                                <c:out value="${utilityService.toConversationFormat(linkNote.postTimestamp)}" />
                                                            </span>
                                                        </div>
                                                        <div class="linknote-body bubble ${styleCounter % 2 == 0 ? 'me' : 'you'}">
                                                            <c:out value="${linkNote.note}" />
                                                        </div>
                                                    </div>
                                                    <c:set var="styleCounter" value="${styleCounter + 1}"/>
                                                </c:forEach>
                                            </ul>
                                        </span>
                                        </c:if>
                                    </div>
                                </c:forEach>
                                <div class="section-br"></div>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>
                </c:forEach>
            </div>
        </div>

    </div>
    <hr>
</div>
<%@include file="include/footer.jsp" %>
</body>
</html>
