<!doctype html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<head>
	<meta charset="utf-8" />
	<title>${pinLink_pageTitle}</title>
	<c:import var="scriptsFile" url="${scriptsFile}" />
	<c:out value="${scriptsFile}" escapeXml="false" />
    <link href="/r/bootstrap-3.0.0/css/bootstrap.css" rel="stylesheet" media="screen" />
    <link href="/r/home-1.2/lib/css/bootstrap-glyphicons.css" rel="stylesheet">
    <link href="/r/home-1.2/lib/css/font-awesome.css" rel="stylesheet">
    <script src="/r/bootstrap-3.0.0/js/bootstrap.js" type="text/javascript"></script>
    <link href="/r/c/vlc.css" rel="stylesheet" />
    <%@ include file="include/marketing.jsp" %>
</head>
<body>
<noscript>
    <h2>${clientEnableJavascript}</h2>
</noscript>
<%@include file="include/top-navbar.jsp" %>
<div class="container pinLink-containerBox" ng-app="vlc" ng-controller="PinLinkController">
    <div ng-show="pinLink_validate_noPages" class="alert alert-danger">
        ${pinLink_validate_noPages}
    </div>
    <div class="well well-large">
        <h2 class="text-center pinLink-title">
            <i class="icon-pushpin icon-1x pinLink-pinPadding"></i>
            ${pinLink_title}
        </h2>
        <form name="pinLinkForm" novalidate>
            <div class="row">
                <div class="col-xs-12">
                    <dl class="dl-horizontal">
                        <dt><h4>${pinLink_linkTitle}</h4></dt>
                        <dd>
                            <div class="form-group">
                                <input id="linkTitle"
                                       name="linkTitle"
                                       type="text"
                                       class="form-control input-lg"
                                       maxlength="${designer_linkName_spec_maxSize}"
                                       required
                                       ng-model="linkTitle"
                                       ng-disabled="isSubmitted || pinLink_validate_noPages"
                                       ng-minlength="${designer_linkName_spec_minSize}"
                                       ng-maxlength="${designer_linkName_spec_maxSize}"
                                    />
                            </div>
                        </dd>
                        <dt><h4>${pinLink_linkUrl}</h4></dt>
                        <dd><h4>${linkUrl}</h4></dd>
                        <dt>
                            <h4>
                                <a href=""
                                   data-toggle="collapse"
                                   data-target="#linkNoteWrapper">
                                    ${designer_linkNote_addNote}
                                </a>
                            </h4>
                        </dt>
                        <dd id="linkNoteWrapper" class="collapse">
                            <div class="control-group">
                                <div class="controls">
                                    <textarea id="linkNote"
                                              name="linkNote"
                                              type="text"
                                              rows="7"
                                              class="input-xlarge pinLink-noteBox"
                                              ng-model="linkNote">
                                    </textarea>
                                </div>
                            </div>
                        </dd>
                    </dl>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12 form-group">
                    <label for="pagePath"><h4>${pinLink_selectPage}</h4></label>
                    <select id="pagePath"
                            name="pagePath"
                            class="form-control input-lg"
                            required
                            ng-model="pagePath"
                            ng-change="pageSelected()"
                            ng-disabled="isSubmitted || pinLink_validate_noPages"
                            ng-options="p.value as p.label for p in pages"
                            >
                        <option value="">
                            ${select}
                        </option>
                    </select>
                </div>
            </div>
            <div class="row" ng-show="isPageSelected">
                <div class="col-xs-12 form-group">
                    <label for="sectionName"><h4>${pinLink_selectSection}</h4></label>
                    <select id="sectionName"
                            name="sectionName"
                            class="form-control input-lg"
                            required
                            ng-model="sectionName"
                            ng-change="sectionSelected()"
                            ng-disabled="isSubmitted || pinLink_validate_noPages"
                            ng-options="p.value as p.label for p in availableSections"
                            >
                        <option value="">
                            ${select}
                        </option>
                    </select>
                </div>
            </div>
            <alert ng-repeat="alert in alerts" type="alert.type">
                <div class="row">
                    <div class="col-xs-12">
                        <h3>${pinLink_saveSuccess_part1} <strong>{{alertData.pageName}}</strong>${period}</h3>
                    </div>
                </div>
                <div class="row text-center">
                    <div class="col-xs-6">
                        <a href="javascript:window.close();" class="btn btn-lg btn-danger">
                            ${pinLink_closeTab} <span class="glyphicon glyphicon-remove"></span>
                        </a>
                    </div>
                    <div class="col-xs-6">
                        <a ng-href="{{alertData.hrefUrl}}" class="btn btn-lg btn-success" target="_blank">
                            ${pinLink_viewPage} <span class="glyphicon glyphicon-play"></span>
                        </a>
                    </div>
                </div>
            </alert>
        </form>
    </div>
</div>
<%@include file="include/footer.jsp" %>
<script>
    var pld = angular.fromJson(${pld});
</script>
</body>
</html>



