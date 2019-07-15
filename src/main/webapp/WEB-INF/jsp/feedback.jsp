<!doctype html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<head>
	<meta charset="utf-8" />
	<title>${feedback}</title>
	<c:import var="scriptsFile" url="${scriptsFile}" />
	<c:out value="${scriptsFile}" escapeXml="false" />
    <link href="/r/bootstrap-3.0.0/css/bootstrap.css" rel="stylesheet" media="screen" />
    <script src="/r/bootstrap-3.0.0/js/bootstrap.js" type="text/javascript"></script>
    <link href="/r/c/vlc.css" rel="stylesheet" />
    <%@ include file="include/marketing.jsp" %>
</head>
<body>
<noscript>
<h2>${clientEnableJavascript}</h2>
</noscript>
<%@include file="include/top-navbar.jsp" %>
<div class="container feedback-containerBox" ng-app="vlc">
    <form name="feedbackForm"
          ng-submit="saveFeedback()"
          ng-controller="FeedbackController"
          novalidate>
    <div class="error" ng-class="formSupport.displayErrorSectionNEW()">
        <div ng-show="violations.nameRequired">${feedback_name_validate_required}</div>
        <div ng-show="violations.emailRequired">${feedback_email_validate_required}</div>
        <div ng-show="violations.emailFormat">${feedback_email_validate_format}</div>
        <div ng-show="violations.commentRequired">${feedback_comment_validate_required}</div>
    </div>
    <alert ng-repeat="alert in alerts" type="alert.type" close="closeAlert($index)">{{alert.msg}}</alert>
    <div class="well well-large">
        <h2>${feedback_headerMessage}</h2>
        <div class="row">
            <div class="col-xs-12 col-sm-12 form-group">
				<label for="name">${name}</label>
				<input id="name"
                        name="name"
                        type="text"
                        class="form-control input-lg"
                        maxlength="${feedback_name_spec_maxSize}"
                        required
					    ng-model="feedbackParams.name"
                        ng-disabled="isValidAndSubmitted"
                        ng-maxlength="${feedback_name_spec_maxSize}"
                        />
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12 col-sm-12 form-group">
				<label for="email">${email}</label>
				<input id="email"
                       name="email"
                       type="email"
                       class="form-control input-lg"
                       maxlength="${feedback_email_spec_maxSize}"
                       required
                       ng-model="feedbackParams.email"
                       ng-disabled="isValidAndSubmitted"
                       ng-maxlength="${feedback_email_spec_maxSize}"
                        />
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12 col-sm-12 form-group">
                <label for="comment">${comment}</label>
                <textarea id="comment"
                            name="comment"
                            class="form-control"
                            rows="5"
                            maxlength="${feedback_comment_spec_maxSize}"
                            required
                            ng-model="feedbackParams.comment"
                            ng-disabled="isValidAndSubmitted"
                            ng-maxlength="${feedback_comment_spec_maxSize}"
                        >
                </textarea>
            </div>
        </div>
        <div class="row">
            <span class="col-xs-12 col-sm-12 form-group">
                <div class="visible-xs">
                    <button type="submit"
                            class="btn btn-primary btn-lg btn-block"
                            ng-disabled="feedbackForm.$invalid || isUnchanged(user) || isValidAndSubmitted"
                            >
                        ${button_submit}
                    </button>
                </div>
                <%-- Below is rendering for larger devices --%>
                <div class="visible-sm hidden-xs">
                    <button type="submit"
                            class="btn btn-primary btn-lg pull-right feedback-saveBtn"
                            ng-disabled="feedbackForm.$invalid || isUnchanged(user) || isValidAndSubmitted"
                            >
                        ${button_submit}
                    </button>
                </div>
            </span>
        </div>
    </div>
    </form>
    <hr>
</div>
<%@include file="include/footer.jsp" %>
</body>
</html>
