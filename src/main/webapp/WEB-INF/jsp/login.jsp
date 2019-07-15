<!doctype html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="utf-8" />
    <meta name="description" content='${tagline}' />
    <title>${login}</title>
    <c:import var="scriptsFile" url="${scriptsFile}" />
    <c:out value="${scriptsFile}" escapeXml="false" />
    <link href="/r/c/login.css" rel="stylesheet" />
    <%@ include file="include/marketing.jsp" %>
</head>
<body>
<noscript>
    <h2>${clientEnableJavascript}</h2>
</noscript>
<%@include file="include/top-navbar.jsp" %>
<div class="container loginBox" ng-app="vlc">
    <form name="f"
          ng-submit="login()"
          ng-controller="LoginController"
          novalidate
          autocomplete="off">
        <div class="error" ng-class="formSupport.displayErrorSection(f)">
            <div ng-show="formSupport.cse(1310) || (f.username.$dirty && f.username.$error.required)">
                ${account_username_validate_required}
            </div>
            <div ng-show="formSupport.cse(1312) || (f.username.$dirty && f.username.$error.minlength && submitted)">
                ${account_username_validate_length}
            </div>
            <div ng-show="formSupport.cse(1312) || (f.username.$dirty && f.username.$error.maxlength && submitted)">
                ${account_username_validate_length}
            </div>
            <div ng-show="formSupport.cse(1450)">
                {{serverErrors.login_validate_generalMismatch}}
            </div>
            <div ng-show="formSupport.cse(1311) || (f.username.$dirty && f.username.$error.pattern)">
                ${account_username_validate_format}
            </div>
            <div ng-show="formSupport.cse(1340) || (f.password.$dirty && f.password.$error.required)">
                ${account_password_validate_required}
            </div>
            <div ng-show="formSupport.cse(1341) || (f.password.$dirty && f.password.$error.minlength && submitted)">
                <span class="ui-icon ui-icon-alert decoratorIcon"></span>
                ${account_password_validate_length}
            </div>
            <div ng-show="formSupport.cse(1341) || (f.password.$dirty && f.password.$error.maxlength && submitted)">
                ${account_password_validate_length}
            </div>
            <div ng-show="formSupport.cse(1281) || (f.password.$dirty && f.password.$error.minlength && submitted)">
                <span class="ui-icon ui-icon-alert decoratorIcon"></span>
                ${account_verification_failure_verificationNeeded}
            </div>
        </div>
        <div class="well">
            <h2>${login}</h2>
            <div class="row">
                <div class="col-xs-12 form-group">
                    <label for="username">${account_username}</label>
                    <input id="username"
                           name="username"
                           type="text"
                           class="form-control input-lg"
                           maxlength="${account_username_spec_maxSize + account_maxlength_offset}"
                           required
                           ng-pattern=/^[a-zA-Z0-9]+$/
                           ng-model="username"
                           ng-disabled="isAccountEdit"
                           ng-minlength="${account_username_spec_minSize}"
                           ng-maxlength="${account_username_spec_maxSize}"
                    />
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12 form-group">
                    <label for="password">${account_password}</label>
                    <input id="password"
                           name="password"
                           type="password"
                           class="form-control input-lg"
                           maxlength="${account_password_spec_maxSize + account_maxlength_offset}"
                           required
                           ng-pattern=/^[a-zA-Z0-9\$\*\!\@\#]+$/
                           ng-model="password"
                           ng-minlength="${account_password_spec_minSize}"
                           ng-maxlength="${account_password_spec_maxSize}"
                    />
                </div>
            </div>
            <div class="row submit-panel">
                <div class="col-xs-12 form-group">
                    <button type="submit" class="btn btn-theme btn-lg btn-block">
                        ${login}
                    </button>
                </div>
            </div>

        </div>

    </form>
    <c:if test="${empty sessionScope.vsession}">
        <div class="row signup-box">
            <div class="col-xs-12">
                <a class="btn btn-default btn-block" href="/account/create">Register</a>
            </div>
        </div>
    </c:if>
    <hr>
</div>

<%@include file="include/footer.jsp" %>
<%--<div id="not-supported">--%>
<%--${account_validate_creationNotAllowed}--%>
<%--</div>--%>
</body>
</html>



