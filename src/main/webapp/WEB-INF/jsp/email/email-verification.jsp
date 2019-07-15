<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="head.jsp" %>
<div style="margin: 15px;">
	<a href='<c:out value="${accountVerificationLink}" />'><img src='<c:out value="${logoBannerUrl}" />' style="max-height: 150px; width: auto;"></a>
	<br />
	<h2>Welcome to Valloc</h2>
    <div>
	    Please click on the link below to verify your email address. Then you can begin using Valloc.
    </div>
	<br />
	<div>
		<a href='<c:out value="${accountVerificationLink}" />'>CLICK TO VERIFY</a>
	</div>
	<br />
	Welcome!
</div>

