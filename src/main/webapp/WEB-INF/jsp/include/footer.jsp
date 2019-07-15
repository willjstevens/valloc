<footer>
    <ul class="list-inline text-center">
        <li><a href="/dashboard">${yourDashboard}</a></li>
        <c:choose>
            <c:when test="${not empty sessionScope.vsession}">
                <li><a href="/logout">Logout</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="/login">Login</a></li>
            </c:otherwise>
        </c:choose>
        <li><a href="/howitworks">${howitworks}</a></li>
        <li><a href="/feedback">${feedback}</a></li>
        <li><a href="/privacypolicy">${privacyPolicy}</a></li>
        <li><a href="/termsofservice">${termsOfService}</a></li>
    </ul>
    <div class="copyright text-center"><small>&copy;&nbsp;${copyright}</small></div>
</footer>

