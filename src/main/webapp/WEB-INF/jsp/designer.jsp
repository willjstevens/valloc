<!doctype html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
	<meta charset="utf-8" />
	<title>${designer_pageTitle_create}</title>


    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" >
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <%-- DANGER - these override and may conflict with the Bootstrap 3.0 in the include file.--%>
    <link href="/r/bootstrap-2.3.2/css/bootstrap.css" rel="stylesheet" media="screen" />
    <script src="/r/bootstrap-2.3.2/js/bootstrap.js" type="text/javascript"></script>
    <script src="/r/j/jquery-1.8.3.min.js" type="text/javascript"></script>
    <script src="/r/colorbox/jquery.colorbox.min.js" type="text/javascript"></script>
    <script src="/r/jqueryui/js/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>
    <script src="/r/j/angular.js?v=1215" type="text/javascript"></script>
    <script src="/r/j/angular-mobile.js" type="text/javascript"></script>
    <script src="/r/j/ui-bootstrap.min.js" type="text/javascript"></script>
    <script src="/r/j/ui-bootstrap-tpls.min.js" type="text/javascript"></script>
    <script src="/r/j/sortable.js" type="text/javascript"></script>
    <script src="/r/j/jquery.ui.touch-punch.js" type="text/javascript"></script>
    <link href="/r/c/swiper.min.css" rel="stylesheet" />
    <script src="/r/j/swiper.min.js" type="text/javascript"></script>
    <link href="/r/c/vlc.css?v=81001" rel="stylesheet" />
    <script src="/r/j/vlc-validate.js" type="text/javascript"></script>
    <script src="/r/j/vlc.min.js?v=81002" type="text/javascript"></script>
    <link href="/r/c/designer.css" rel="stylesheet" />
    <%@ include file="include/marketing.jsp" %>
</head>
<body ng-app="vlc" ng-controller="DesignerController">
    <noscript>
        <h2>${clientEnableJavascript}</h2>
    </noscript>
    <div class="navbar navbar-inverse navbar-fixed-top">
        <div class="navbar-inner">
            <div class="container-fluid">
                <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="brand" href="/">Valloc</a>
                <div class="nav-collapse collapse">
                    <ul class="nav pull-right">
                        <li class="active"><a href="#">Designer</a></li>
                        <li><a href="/dashboard">Dashboard</a></li>
                        <li><a href="/logout">${logout}</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="container-fluid visible-desktop visible-tablet hidden-phone">
        <div class="row-fluid">
            <div class="span3">
                <div class="well sidebar-nav">
                    <ul class="nav nav-list">
                        <form name="pageForm" novalidate>
                            <fieldset ng-hide="designerData.isGuestEdit">
                                <legend>${designer_page}</legend>
                                <div class="row-fluid">
                                    <div class="span3">
                                        <label for="pageName">${designer_pageInfo_pageName}</label>
                                    </div>
                                    <div class="span9">
                                        <input
                                            id="pageName"
                                            name="pageName"
                                            type="text"
                                            placeholder="${designer_placeholderText_pageTitle}"
                                            class="repair-input"
                                            required
                                            ng-model="pageData.name"
                                            ng-minlength="${designer_pageName_spec_minSize}"
                                            ng-maxlength="${designer_pageName_spec_maxSize}"
                                            ng-disabled="designerData.isGuestEdit"
                                        />
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span3">
                                        <label for="path">${designer_pageInfo_pagePath}</label>
                                    </div>
                                    <div class="span9">
                                        <input
                                            id="path"
                                            name="path"
                                            type="text"
                                            placeholder="${designer_placeholderText_pagePath}"
                                            class="repair-input"
                                            required
                                            ng-model="pageData.path"
                                            ng-pattern=/^[a-z0-9]+$/i
                                            ng-minlength="${designer_pagePath_spec_minSize}"
                                            ng-maxlength="${designer_pagePath_spec_maxSize}"
                                            ng-disabled="designerData.isGuestEdit"
                                            />
                                    </div>
                                </div>
                                <div class="row-fluid visibility-block">
                                    <div class="span4">
                                        <label>
                                            ${dashboard_pageListing_visibility}
                                            <span tooltip-placement="right" tooltip="${designer_pageInfo_visibility_toolTip}" class="icon-question-sign toolTip"></span>
                                        </label>
                                        <label class="radio visibilityRadios">
                                            <input
                                                id="visibilityPublic"
                                                type="radio"
                                                value="PUBLIC"
                                                ng-model="pageData.visibility"
                                                ng-disabled="designerData.isGuestEdit"
                                                />
                                            ${designer_pageInfo_visibility_public}
                                        </label>
                                        <label class="radio visibilityRadios">
                                            <input
                                                id="visibilityPrivate"
                                                type="radio"
                                                value="PRIVATE"
                                                ng-model="pageData.visibility"
                                                ng-disabled="designerData.isGuestEdit"
                                                />
                                            ${designer_pageInfo_visibility_private}
                                        </label>
                                    </div>
                                    <div class="span8">
                                        <button
                                            type="button"
                                            class="btn btn-link pageGuestsBtn"
                                            data-title="${designer_pageInfo_pageGuests}"
                                            ng-click="openPageGuestPopup()"
                                            ng-disabled="pageData.visibility === 'PUBLIC' || designerData.isGuestEdit"
                                            >
                                            ${designer_pageInfo_pageGuests}
                                        </button>
                                        <span tooltip-placement="right" tooltip="${designer_pageInfo_pageGuests_toolTip}" class="icon-question-sign toolTip"></span>
                                    </div>
                                </div>
                            </fieldset>
                        </form>
                        <form name="sectionForm" ng-submit="createSection()" novalidate>
                            <fieldset>
                                <legend>${designer_pageInfo_sections}</legend>
                                <div class="row-fluid">
                                    <div class="span3">
                                        <label for="newSectionName">${designer_pageInfo_sectionTitle}</label>
                                    </div>
                                    <div class="span9">
                                        <input
                                            id="newSectionName"
                                            name="newSectionName"
                                            type="text"
                                            placeholder="${designer_placeholderText_sectionTitle}"
                                            class="repair-input"
                                            required
                                            ng-model="newSectionName"
                                            ng-minlength="${designer_sectionName_spec_minSize}"
                                            ng-maxlength="${designer_sectionName_spec_maxSize}"
                                            />
                                    </div>
                                </div>
                                <button type="submit"
                                        class="btn"
                                        ng-disabled="sectionForm.$invalid || isSectionFormPartiallyPristine()"
                                        >
                                    ${designer_pageInfo_create}
                                </button>
                            </fieldset>
                        </form>
                        <form name="linkForm" ng-submit="createLink()" novalidate>
                            <fieldset>
                                <legend>${designer_pageInfo_links}</legend>
                                <div class="row-fluid">
                                    <div class="span3">
                                        <label for="newLinkName">${designer_pageInfo_linkText}</label>
                                    </div>
                                    <div class="span9">
                                        <input
                                            id="newLinkName"
                                            name="newLinkName"
                                            type="text"
                                            placeholder="${designer_placeholderText_linkText}"
                                            class="repair-input"
                                            required
                                            ng-model="newLinkName"
                                            ng-minlength="${designer_linkName_spec_minSize}"
                                            ng-maxlength="${designer_linkName_spec_maxSize}"
                                            />
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span3">
                                        <label for="newLinkUrl">${designer_pageInfo_linkUrl}</label>
                                    </div>
                                    <div class="span9">
                                        <input
                                            id="newLinkUrl"
                                            name="newLinkUrl"
                                            type="text"
                                            placeholder="${designer_placeholderText_linkUrl}"
                                            class="repair-input"
                                            required
                                            ng-model="newLinkUrl"
                                            ng-minlength="${designer_linkUrl_spec_minSize}"
                                            ng-maxlength="${designer_linkUrl_spec_maxSize}"
                                            />
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span3">
                                        <label for="linkSection">${designer_pageInfo_linkSection}</label>
                                    </div>
                                    <div class="span9">
                                        <select id="linkSection"
                                                name="linkSection"
                                                class="repair-input"
                                                required
                                                ng-model="linkSection"
                                                ng-options="section.value as section.label for section in sections | orderBy:'label'"
                                                >
                                            <option value="">
                                                ${select}
                                            </option>
                                        </select>
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span3">
                                        <label for="linkNote">
                                            <a href=""
                                                data-toggle="collapse"
                                                data-target="#linkNoteWrapper">
                                                    ${designer_linkNote_addNote}
                                            </a>
                                        </label>
                                    </div>
                                </div>
                                <div id="linkNoteWrapper" class="row-fluid collapse">
                                    <div class="span11">
                                        <textarea id="linkNote"
                                                    name="linkNote"
                                                    class="repair-input"
                                                    rows="5"
                                                    ng-model="newLinkNote"
                                                >
                                        </textarea>
                                    </div>
                                </div>

                                <button ng-submit="submit()"
                                        class="btn"
                                        ng-disabled="linkForm.$invalid || isLinkFormPartiallyPristine()"
                                        >
                                    ${designer_pageInfo_create}
                                </button>
                            </fieldset>
                        </form>
                        <form name="otherForm" novalidate>
                            <fieldset ng-hide="designerData.isGuestEdit">
                                <label class="checkbox home-label">
                                    <input
                                            id="homePage"
                                            type="checkbox"
                                            ng-model="pageData.home"
                                            ng-disabled="designerData.isGuestEdit"
                                            />
                                    ${designer_pageInfo_homePage}
                                    <span tooltip-placement="right" tooltip="${designer_pageInfo_homePage_toolTip}" class="icon-question-sign toolTip"></span>
                                </label>
                                <legend>${other}</legend>
                                     <button type="button"
                                        class="btn btn-danger"
                                        ng-click="deletePage()"
                                        ng-disabled="designerData.isGuestEdit"
                                        >
                                    ${designer_deletePage_title}
                                </button>
                            </fieldset>
                        </form>
                    </ul>
                </div>
            </div>
            <div class="span9">
                <div class="page-header">


                    <div class="row-fluid">
                        <div class="span6">
                            <h3>${designer_pageHeader}</h3>
                        </div>
                        <div class="span6">
                            <a ng-click="savePage()"
                               ng-show="showSavePageBtn"
                               class="save-btn pull-right">${designer_savePage}</a>
                            <span ng-show="!showSavePageBtn" class="pull-right">
                                <%--<i class="icon-spinner icon-spin icon-large pull-right"></i> --%>
                                Saving...
                            </span>
                        </div>
                    </div>
                    <div class="row-fluid">
                        <div class="span12">
                            <alert ng-repeat="alert in saveAlerts" type="alert.type" close="closeSaveAlert()">
                                {{alert.msg}}  <strong>
                                                    <a
                                                        ng-href="{{saveAlertData.hrefUrl}}"
                                                        class="btn btn-info">
                                                        {{saveAlertData.displayUrl}}
                                                    </a>
                                                </strong>
                            </alert>
                        </div>
                    </div>
                </div>
                <div ng-class="formSupport.displayErrorSectionNEW()">
                    <div ng-show="violations.pageNameRequired">${designer_pageName_validate_required}</div>
                    <div ng-show="violations.pageNameLength">${designer_pageName_validate_length}</div>
                    <div ng-show="violations.pathRequired">${designer_pagePath_validate_required}</div>
                    <div ng-show="violations.pathLength">${designer_pagePath_validate_length}</div>
                    <div ng-show="violations.pathFormat">${designer_pagePath_validate_format}</div>
                    <div ng-show="violations.sectionNameRequired">${designer_sectionName_validate_required}</div>
                    <div ng-show="violations.sectionNameLength">${designer_sectionName_validate_length}</div>
                    <div ng-show="violations.sectionNoteLength">${designer_sectionNote_validate_length}</div>
                    <div ng-show="violations.linkNameRequired">${designer_linkName_validate_required}</div>
                    <div ng-show="violations.linkNameLength">${designer_linkName_validate_length}</div>
                    <div ng-show="violations.linkUrlRequired">${designer_linkUrl_validate_required}</div>
                    <div ng-show="violations.linkUrlLength">${designer_linkUrl_validate_length}</div>
                    <div ng-show="violations.linkNoteLength">${designer_linkNote_validate_length}</div>
                    <div ng-show="violations.linkSectionRequired">${designer_linkSection_validate_required}</div>
                </div>
                <alert ng-repeat="alert in alerts" type="alert.type" close="closeAlert($index)">
                    <h4>{{alert.msg}}</h4>
                </alert>

                <div class="row-fluid">
                    <ul ui-sortable="{connectWith: '.columnList',
                                        opacity: .5
                                    }"
                        ng-model="pageData.columns"
                        class="columnList">
                        <li ng-repeat="column in pageData.columns" class="columnListItem">
                            <ul ui-sortable="{connectWith: '.sectionList',
                                            placeholder: 'placeholder',
                                            forcePlaceholderSize: true
                                            }"
                                ng-model="column.sections"
                                class="sectionList">
                                <li ng-repeat="section in column.sections" class="sectionListItem ">
                                    <%@include file="include/designer-sectionEditModal.jsp" %>
                                    <ul ui-sortable="{connectWith: '.linkList',
                                            opacity: .5
                                            }"
                                        ng-model="section.links"
                                        class="linkList">
                                        <li ng-repeat="link in section.links" class="linkItem">
                                            <%@include file="include/designer-linkEditModal.jsp" %>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <hr>
    </div>
    <div class="well visible-phone hidden-desktop hidden-tablet cushion-20-top">
        <h4>${designer_notSupportedOnSmallDevices}</h4>
    </div>
    <footer class="text-center">
        <ul class="inline text-center">
            <li><a href="/dashboard">${yourDashboard}</a></li>
            <c:choose>
                <c:when test="${not empty sessionScope.vsession}">
                    <li><a href="/logout">Logout</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="/login">Login</a></li>
                </c:otherwise>
            </c:choose>
            <li><a href="/privacypolicy">${privacyPolicy}</a></li>
            <li><a href="/termsofservice">${termsOfService}</a></li>
        </ul>
        <div class="copyright text-center"><small>&copy;&nbsp;${copyright}</small></div>
    </footer>

    <script>
        var pld = angular.fromJson(${pld});
    </script>
</body>
</html>



