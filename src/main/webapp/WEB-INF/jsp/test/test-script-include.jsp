<!doctype html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<head>
	<meta charset="utf-8" />
	<title>test-script-include</title>

	<c:import var="scriptsFile" url="${scriptsFile}" />
	<c:out value="${scriptsFile}" escapeXml="false" />
</head>
<body>
<h2>test-script-include.jsp</h2>
(See source)
<noscript>
<c:out value="${clientEnableJavascript}" escapeXml="false" />
</noscript>
</body>
</html>