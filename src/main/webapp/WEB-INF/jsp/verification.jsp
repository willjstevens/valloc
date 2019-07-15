<!doctype html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="utf-8" />
    <title>${account_pageTitle}</title>
    <c:import var="scriptsFile" url="${scriptsFile}" />
    <c:out value="${scriptsFile}" escapeXml="false" />
    <link href="/r/bootstrap-3.0.0/css/bootstrap.css" rel="stylesheet" media="screen" />
    <script src="/r/bootstrap-3.0.0/js/bootstrap.js" type="text/javascript"></script>
    <link href="/r/c/verification.css" rel="stylesheet" />
    <%@ include file="include/marketing.jsp" %>
</head>
<body>
<noscript>
    <h2>${clientEnableJavascript}</h2>
</noscript>
<%@include file="include/top-navbar.jsp" %>
<div class="container verificationBox">
    <div class="well well-large">
        <c:choose>
            <c:when test="${viewPart == 'createComplete'}" >
                <h2>${account_verification_explanation_part1}</h2>
                <p>${account_verification_explanation_part2}</p>
                <p>${account_verification_explanation_part3}</p>
                <p>${account_verification_explanation_part4}</p>

                <script>
                    fbq('track', 'CompleteRegistration');
                </script>
            </c:when>
            <c:when test="${viewPart == 'success'}" >
                <h2>${account_verification_success_part1}</h2>
                <p>${account_verification_success_part2}</p>
                <p class="text-right"><h3><a class="btn btn-default btn-block" href="/login">${login} &raquo;</a></h3></p>
            </c:when>
            <c:when test="${viewPart == 'badVerification'}" >
                <h3>${account_verification_failure_badVerification}</h3>
            </c:when>
            <c:when test="${viewPart == 'expiredCode'}" >
                <h3>${account_verification_failure_expiredCode_part1}</h3>
                <p>${account_verification_failure_expiredCode_part2}</p>
            </c:when>
            <c:when test="${viewPart == 'verifyCompleted'}" >
                <h3>${account_verification_verifyCompleted}</h3>
            </c:when>
            <c:otherwise>
            Unspecified view part.
            </c:otherwise>
        </c:choose>
    </div>
</div>
<%@include file="include/footer.jsp" %>
</body>
</html>
