<!doctype html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<head>
	<meta charset="utf-8" />
	<title>${account_pageTitle}</title>
    <c:import var="scriptsFile" url="${scriptsFile}" />
    <c:out value="${scriptsFile}" escapeXml="false" />
	<link href="/r/c/account.css" rel="stylesheet" />
    <%@ include file="include/marketing.jsp" %>
</head>
<body>
<noscript>
<h2>${clientEnableJavascript}</h2>
</noscript>
<%@include file="include/top-navbar.jsp" %>
<div class="container accountBox" ng-app="vlc">
    <form name="f"
          ng-submit="saveAccount()"
          ng-controller="AccountController"
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
        <div ng-show="formSupport.cse(1313)">
            {{serverErrors.account_username_validate_taken}}
        </div>
        <div ng-show="formSupport.cse(1314)">
            {{serverErrors.account_username_validate_exclusionsList}}
        </div>
        <div ng-show="formSupport.cse(1311) || (f.username.$dirty && f.username.$error.pattern)">
            ${account_username_validate_format}
        </div>
        <div ng-show="formSupport.cse(1315) || (f.email.$dirty && f.email.$error.required)">
            ${account_email_validate_required}
        </div>
        <div ng-show="formSupport.cse(1317) || (f.email.$dirty && f.email.$error.minlength && submitted)">
            ${account_email_validate_length}
        </div>
        <div ng-show="formSupport.cse(1317) || (f.email.$dirty && f.email.$error.maxlength && submitted)">
            ${account_email_validate_length}
        </div>
        <div ng-show="formSupport.cse(1316) || (f.email.$dirty && f.email.$error.email)">
            ${account_email_validate_format}
        </div>
        <div ng-show="formSupport.cse(1318)">
            {{serverErrors.account_email_validate_taken}}
        </div>
        <div ng-show="formSupport.cse(1320) || (f.firstName.$dirty && f.firstName.$error.required)">
            ${account_firstName_validate_required}
        </div>
        <div ng-show="formSupport.cse(1321) || (f.firstName.$dirty && f.firstName.$error.minlength && submitted)">
            ${account_firstName_validate_length}
        </div>
        <div ng-show="formSupport.cse(1321) || (f.firstName.$dirty && f.firstName.$error.maxlength && submitted)">
            <span class="ui-icon ui-icon-alert decoratorIcon"></span>
            ${account_firstName_validate_length}
        </div>
        <div ng-show="formSupport.cse(1322) || (f.firstName.$dirty && f.firstName.$error.pattern)">
            ${account_firstName_validate_format}
        </div>
        <div ng-show="formSupport.cse(1325) || (f.lastName.$dirty && f.lastName.$error.required)">
            ${account_lastName_validate_required}
        </div>
        <div ng-show="formSupport.cse(1326) || (f.lastName.$dirty && f.lastName.$error.minlength && submitted)">
            ${account_lastName_validate_length}
        </div>
        <div ng-show="formSupport.cse(1326) || (f.lastName.$dirty && f.lastName.$error.maxlength && submitted)">
            ${account_lastName_validate_length}
        </div>
        <div ng-show="formSupport.cse(1327) || (f.lastName.$dirty && f.lastName.$error.pattern)">
            <span class="ui-icon ui-icon-alert decoratorIcon"></span>
            ${account_lastName_validate_format}
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
        <div ng-show="formSupport.cse(1342) || (f.password.$dirty && f.password.$error.pattern)">
            ${account_password_validate_format}
        </div>
        <div ng-show="formSupport.cse(1365) || (f.inAgreement.$dirty && f.inAgreement.$error.required)">
            ${account_inAgreement_validate_required}
        </div>

    </div>
    <alert ng-repeat="alert in alerts" type="alert.type" close="closeAlert($index)">{{alert.msg}}</alert>

    <div class="well well-large">
        <h2>${account_pageTitle}</h2>
        <div class="row">
            <div class="col-xs-12 form-group">
				<label for="email">${account_email}</label>
				<input id="email"
                       name="email"
                       type="email"
                       class="form-control input-lg"
                       maxlength="${account_email_spec_maxSize + account_maxlength_offset}"
                       required
                       ng-model="account.email"
                       ng-disabled="isAccountEdit"
                       ng-minlength="${account_email_spec_minSize}"
                       ng-maxlength="${account_email_spec_maxSize}"
                        />
            </div>
        </div>
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
						ng-model="account.username"
                       ng-disabled="isAccountEdit"
                       ng-minlength="${account_username_spec_minSize}"
                       ng-maxlength="${account_username_spec_maxSize}"
                        />
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12 form-group">
				<label for="firstName">${account_firstName}</label>
				<input id="firstName"
                       name="firstName"
                       type="text"
                       class="form-control input-lg"
                       maxlength="${account_firstName_spec_maxSize + account_maxlength_offset}"
                       required
                       ng-pattern=/^[a-zA-Z\s]+$/
                        ng-model="account.firstName"
                       ng-disabled="isAccountEdit"
                       ng-minlength="${account_firstName_spec_minSize}"
                       ng-maxlength="${account_firstName_spec_maxSize}"
                        />
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12 form-group">
				<label for="lastName">${account_lastName}</label>
				<input id="lastName"
                       name="lastName"
                       type="text"
                       class="form-control input-lg"
                       maxlength="${account_lastName_spec_maxSize + account_maxlength_offset}"
                       required
                       ng-pattern=/^[a-zA-Z\s]+$/
						ng-model="account.lastName"
                       ng-disabled="isAccountEdit"
                       ng-minlength="${account_lastName_spec_minSize}"
                       ng-maxlength="${account_lastName_spec_maxSize}"
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
                       <%--vlc-match-to="passwordConfirmation"--%>
                       ng-pattern=/^[a-zA-Z0-9\$\*\!\@\#]+$/
		                ng-model="account.password"
                       repeat-password="passwordConfirmation"
                       ng-minlength="${account_password_spec_minSize}"
                       ng-maxlength="${account_password_spec_maxSize}"
                        />
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12 form-group">
                <div class="checkbox" ng-show="isAccountNew">
                    <label for="inAgreement">
                        <input id="inAgreement"
                               name="inAgreement"
                               type="checkbox"
                               required
                               ng-model="account.inAgreement"
                        />
                        <small>
                            ${account_inAgreement_part1}
                            <a href='javascript: void(0)' onclick="window.open('/termsofservice')">${account_inAgreement_part2}</a>
                            ${account_inAgreement_part3}
                            <a href='javascript: void(0)' onclick="window.open('/privacypolicy')">${account_inAgreement_part4}</a>
                            ${account_inAgreement_part5}
                        </small>
                    </label>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12 form-group">
                <button
                        type="submit"
                        class="btn btn-theme btn-lg btn-block"
                        ng-disabled="isValidAndSubmitted"
                >
                    ${button_submit}
                </button>
            </div>
        </div>
    </div>
    </form>
    <hr>
</div>
<%@include file="include/footer.jsp" %>
<script>
    var pld = angular.fromJson(${pld});
</script>
<%--<div id="not-supported">--%>
	<%--${account_validate_creationNotAllowed}--%>
<%--</div>--%>
</body>
</html>
