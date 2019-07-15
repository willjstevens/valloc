<!doctype html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<head>
	<meta charset="utf-8" />
	<title>${dashboard_pageTitle}</title>
    <c:import var="scriptsFile" url="${scriptsFile}" />
    <c:out value="${scriptsFile}" escapeXml="false" />
    <link href="/r/home-1.2/lib/css/font-awesome.css" rel="stylesheet">
    <link href="/r/c/dashboard.css?v=81001" rel="stylesheet" />
    <%@ include file="include/marketing.jsp" %>
</head>
<body>
    <noscript>
    <h2>${clientEnableJavascript}</h2>
    </noscript>
    <%@include file="include/top-navbar.jsp" %>
    <div class="container dashboardBox" ng-app="vlc">
        <div class="well well-large" ng-controller="DashboardController">
            <c:choose>
                <c:when test="${hasPages == true}" >
                    <h2 class="text-center page-dashboard-title">${dashboard_pageDashboard}</h2>
                    <ul class="dashboard">
                        <li ng-repeat="page in pages" class="">
                            <div class="row">
                                <div class="col-xs-12 col-sm-10">
                                    <a ng-href="{{page.editPath}}" class="page-name" title="${designer_pageInfo_edit}">
                                        {{page.name}}
                                    </a>
                                    <span class="glyphicon glyphicon-home glyphicon-home"
                                          ng-show="page.home"
                                          title="${dashboard_pageListing_home_current}"></span>
                                </div>
                                <div class="hidden-xs col-sm-2">
                                    <a ng-href="{{page.editPath}}">
                                        <span class="glyphicon glyphicon-edit glyphicon-edit-page pull-right" title="${designer_pageInfo_edit}"></span>
                                    </a>
                                </div>
                            </div>
                            <div class="row li-row">
                                <div class="col-xs-12 col-sm-12">
                                    <a ng-href="{{page.hrefUrl}}" class="page-path text-center">
                                        ${dashboard_pageListing_view} <strong>{{page.displayPath}}</strong>
                                    </a>
                                </div>
                            </div>
                            <div class="row li-row">
                                <div class="col-xs-12 col-sm-12 text-center">
                                    <small>{{page.listingMessage}}</small>
                                </div>
                            </div>
                        </li>
                    </ul>
                </c:when>
                <c:otherwise>
                    <div class="row">
                        <div class="col-12">
                            <h2 class="text-center">${dashboard_noPages_part1}</h2>
                        </div>
                    </div>
                    <div class="row no-pages-box">
                        <div class="col-12 col-sm-6 col-lg-6 text-center">
                            <i class="icon-plus icon-3x icon"></i>
                            <h3>
                                ${dashboard_noPages_part2}
                            </h3>
                            <p>${dashboard_noPages_part4}</p>
                            <p><a class="btn btn-default btn-block" href="/page/new">${dashboard_createPage_label} &raquo;</a></p>
                        </div>
                        <div class="col-12 col-sm-6 col-lg-6 text-center">
                            <i class="icon-download-alt icon-3x icon"></i>
                            <h3>
                                ${dashboard_noPages_part3}
                            </h3>
                            <p>${dashboard_noPages_part5}</p>
                            <p><a class="btn btn-default btn-block" href="/quickstart">${dashboard_import} &raquo;</a></p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <hr />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12 text-center">
                            <i class="icon-pushpin icon-3x icon"></i>
                            <h3 class="text-center">
                                Get the Valloc link pinning button
                            </h3>
                            <p>
                                Effortlessly pin links to save the link to your Valloc page.
                                Valloc provides browser extension buttons that sit "outside" the webpage so you can pin any web page to your preferred Valloc page and section - in 4 clicks.
                            </p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12 col-sm-offset-3 col-sm-6 text-center">
                            <a class="btn btn-default btn-block" target="_blank" href="https://chrome.google.com/webstore/detail/pin-a-link-to-your-valloc/cllbbodlpkecaaioglgeebjfcnplpepc">Get it for Chrome &raquo</a>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <hr>
        <div class="row">
            <div class="col-12 col-sm-6 col-lg-6 text-center">
                <i class="icon-plus icon-2x icon"></i>
                <h4>${dashboard_createPage}</h4>
                <p>${dashboard_createPage_description}</p>
                <p><a class="btn btn-default btn-block" href="/page/new">${dashboard_createPage_label} &raquo;</a></p>
            </div>
            <div class="col-12 col-sm-6 col-lg-6 text-center">
                <i class="icon-download-alt icon-2x icon"></i>
                <h4>${dashboard_noPages_part3}</h4>
                <p>${dashboard_noPages_part5}</p>
                <p><a class="btn btn-default btn-block" href="/quickstart">${dashboard_import} &raquo;</a></p>
            </div>
        </div>
    </div>
    <%@include file="include/footer.jsp" %>
    <script>
        var pld = angular.fromJson(${pld});
    </script>
</body>
</html>



