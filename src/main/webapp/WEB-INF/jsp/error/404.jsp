<!doctype html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="utf-8" />
    <title>${error}</title>
    <c:import var="scriptsFile" url="${scriptsFile}" />
    <c:out value="${scriptsFile}" escapeXml="false" />
    <link href="/r/bootstrap-3.0.0/css/bootstrap.css" rel="stylesheet" media="screen" />
    <script src="/r/bootstrap-3.0.0/js/bootstrap.js" type="text/javascript"></script>
</head>
<body>
<noscript>
    <h2>${clientEnableJavascript}</h2>
</noscript>
<%@include file="../include/top-navbar.jsp" %>
<div class="container">
    <div class="well well-large text-center">
        <h3>${error_404_message}</h3>
    </div>
</div>
<%@include file="../include/footer.jsp" %>
</body>
</html>
