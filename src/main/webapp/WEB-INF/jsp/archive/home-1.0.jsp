<!doctype html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="utf-8" />
    <meta property="og:title" content="Connect with us" />
    <meta property="og:type" content="website" />
    <meta property="og:url" content="http://www.valloc.com" />
    <meta property="og:image" content="" />
    <meta property="og:site_name" content="Valloc" />
    <meta property="fb:admins" content="100000765062194" />
    <meta name="description" content='${tagline}' />
    <title>Valloc</title>
    <c:import var="scriptsFile" url="${scriptsFile}" />
    <c:out value="${scriptsFile}" escapeXml="false" />
    <link href="/r/bootstrap-3.0.0/css/bootstrap.css" rel="stylesheet" media="screen" />
    <script src="/r/bootstrap-3.0.0/js/bootstrap.js" type="text/javascript"></script>
    <link href="/r/c/index.css" rel="stylesheet" />
    <script>(function(d, s, id) {
        var js, fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id)) return;
        js = d.createElement(s); js.id = id;
        js.src = "//connect.facebook.net/en_US/all.js#xfbml=1";
        fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));</script>
    <script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src="//platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
    <script type="text/javascript">
        (function() {
            var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
            po.src = 'https://apis.google.com/js/plusone.js';
            var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
        })();
    </script>
</head>
<body>
<div class="body-wrapper">
    <div class="container indexBox">
        <c:if test="${empty sessionScope.vsession}">
            <div class="pull-right">
                <h3><a class="btn btn-default btn-lg" href="/login">Login &raquo;</a></h3>
            </div>
        </c:if>

        <div class="new-label hidden-xs hidden-sm">
            <h1><span class="label label-success">New!</span></h1>
        </div>
        <div class="logo visible-sm hidden-xs">
            <img src="/r/i/vlogo.png" />
            <span class="tagline"><h3>${tagline}</h3></span>
        </div>
        <div class="visible-xs">
            <img src="/r/i/vlogo-midsize.png" />
            <span class="tagline"><h4>${tagline}</h4></span>
        </div>
        <div class="row spacer">
            <div class="col-xs-12 col-sm-4">
                <h2>How it works</h2>
                <p>Take a quick tour to see what Valloc does for you and how it works.</p>
                <p><a class="btn btn-default" href="/howitworks">View details &raquo;</a></p>
            </div>
            <div class="col-xs-12 col-sm-4">
                <h2>Sign up</h2>
                <h4>(It's free)</h4>
                <p>Don't worry. It is free and easy to signup. </p>
                <p><a class="btn btn-default" href="/account/create">View details &raquo;</a></p>
            </div>
            <div class="col-xs-12 col-sm-4">
                <h2>${getStartedQuickly}</h2>
                <p>We have a quick and easy 3 step process for importing all your currect bookmarks into Valloc so you can launch into using our site.</p>
                <p><a class="btn btn-default" href="/quickstart">${dashboard_import} &raquo;</a></p>
            </div>
        </div>
        <hr>
    </div>

    <ul class="list-inline text-center">
        <li><a href="https://twitter.com/share" class="twitter-share-button" data-url="http://www.valloc.com" data-count="none">Tweet</a></li>
        <li><div class="g-plus" data-action="share" data-annotation="none" data-href="http://www.valloc.com"></div></li>
        <li>
            <script src="//platform.linkedin.com/in.js" type="text/javascript"></script>
            <script type="IN/Share" data-url="www.valloc.com"></script>
        </li>
        <li>
            <div style="display: inline;" class="fb-like" data-href="https://www.facebook.com/valloccom" data-send="false" data-layout="button_count" data-width="150" data-show-faces="false"></div>
        </li>
    </ul>
    <%@include file="../include/footer.jsp" %>
</div>
</body>
</html>
