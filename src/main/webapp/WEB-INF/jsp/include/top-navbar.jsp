<div class="navbar navbar-default navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">
                <%--<img src="/r/i/home-page-logo.png" class="img-responsive home-page-logo" alt="Valloc Pages">--%>
                Valloc
            </a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/dashboard" class="active">Dashboard</a></li>
                <li><a href="/account/edit" class="active">Account</a></li>
                <c:choose>
                    <c:when test="${not empty sessionScope.vsession}">
                        <li><a href="/logout">Logout</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="/login">Login</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</div>