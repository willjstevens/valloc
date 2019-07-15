<!doctype html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="utf-8" />
    <meta name="description" content='${tagline}' />
    <title>How it works</title>
    <c:import var="scriptsFile" url="${scriptsFile}" />
    <c:out value="${scriptsFile}" escapeXml="false" />
    <link href="/r/bootstrap-3.0.0/css/bootstrap.css" rel="stylesheet" media="screen" />
    <link href="/r/colorbox/colorbox.css" rel="stylesheet" />
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
    <link href="/r/c/vlc.css" rel="stylesheet" />
    <link href="/r/c/howitworks.css?80818" rel="stylesheet" />
    <script src="/r/bootstrap-3.0.0/js/bootstrap.js" type="text/javascript"></script>
    <script src="/r/j/how-it-works.js" type="text/javascript"></script>
    <%@ include file="include/marketing.jsp" %>
</head>
<body>
<noscript>
    <h2>${clientEnableJavascript}</h2>
</noscript>
<%@include file="include/top-navbar.jsp" %>
<div class="how-it-works-box">

    <div class="container marketing">
        <div class="row">
            <div>
                <img id="carousel-logo" class="carousel-img" src="/r/i/index/carousel/logo.png" alt="Valloc">
            </div>
        </div>
        <hr id="scrolltop-summary" class="featurette-divider">
        <a href="/account/create" class="btn btn-info btn-large btn-block">
            <span></span>
            Sign Up <i class="fa fa-pencil"></i>
        </a>
        <div class="row">
            <div class="col-xs-12 text-center">
                <h2>How it works</h2>
            </div>
        </div>
        <div class="list-group">
            <a id="btn-informational" href="#" class="list-group-item active">
                <h4 class="list-group-item-heading">
                    <i class="fa fa-desktop fa-lg theme-text summary-icon"></i>
                    What Valloc provides
                </h4>
                <%--<i class="fa fa-camera-retro"></i>--%>
                <%--<p class="list-group-item-text">--%>
                    <%--Have all your favorite website links in one location, laid out visually for quick direct use.--%>
                    <%--Access them from all devices.--%>
                    <%--Reach your pages at a simple and memorable URL.--%>
                <%--</p>--%>
            </a>
            <a id="btn-page-designer" href="#" class="list-group-item">
                <h4 class="list-group-item-heading">
                    <i class="fa fa-gears fa-lg theme-text summary-icon"></i>
                    The Page Designer
                </h4>
                <%--<p class="list-group-item-text">--%>
                    <%--Pages are easily created and updated with the visual Valloc Page Designer.--%>
                    <%--The Page Designer lets you add and continually update content and settings on your page.--%>
                    <%--Sections and links are arranged in visual fashion by dragging and dropping each wherever you like on the page.--%>
                <%--</p>--%>
            </a>
            <a id="btn-mobility" href="#" class="list-group-item">
                <h4 class="list-group-item-heading">
                    <i class="fa fa-mobile-phone fa-lg theme-text summary-icon"></i>
                    Mobile accessibility is key
                </h4>
                <%--<p class="list-group-item-text">--%>
                    <%--Your page content will be accessible everywhere.--%>
                    <%--All devices, most browsers, via the same simple URL.--%>
                <%--</p>--%>
            </a>
            <a id="btn-collaborate" href="#" class="list-group-item">
                <h4 class="list-group-item-heading">
                    <i class="fa fa-group fa-lg theme-text summary-icon"></i>
                    Collaborate with others by sharing your public and private pages
                </h4>
                <%--<p class="list-group-item-text">--%>
                    <%--Public pages are openly accessible over the Internet.--%>
                    <%--Private pages are too, but require you to be signed in, and are served with encryption over SSL. --%>
                    <%--You can add <i>page guests</i> to private pages if you wish to share private pages with other Valloc users.--%>
                <%--</p>--%>
            </a>
            <a id="btn-get-data-in" href="#" class="list-group-item">
                <h4 class="list-group-item-heading">
                    <i class="fa fa-thumb-tack fa-lg theme-text summary-icon"></i>
                    Quickly build your pages by pinning links or importing existing bookmarks
                </h4>
                <%--<p class="list-group-item-text">--%>
                    <%--Valloc makes it easy to get started and keep your pages "living". --%>
                    <%--Start with a simple 3-step import of your existing browser bookmarks to launch your first page.--%>
                    <%--Then by using any popular desktop browser's Valloc extension button, you can effortlessly pin links to any page to capture the current page information.--%>
                        <%--By using the Chrome extension's Valloc button, you can pin links to your pages.--%>
                <%--</p>--%>
            </a>
        </div>
        <hr id="scrolltop-informational" class="featurette-divider">
        <div id="div-informational" class="row featurette">
            <div class="col-md-7">
                <h3 class="featurette-heading">
                    <span class="text-muted">Have all your favorite website links in one location. Access them from all devices and all browsers. You want to reach them from a simple and easy-to-remember URL.</span>
                </h3>
                <p>Valloc pages are accessible in a browser. Your browser page URL will first contain your username followed by the page path you provided.  For example, if your username is <i>johnsmith</i> and you created a page with a path <i>school</i>, then the page path would be "www.valloc.com/johnsmith/school". How easy is that?</p>
            </div>
            <div class="col-md-5 img-vertical-align-center">
                <span></span>
                <%--<a class='gallery-informational' href='/r/i/index/featurettes/home-page-full.png'>--%>
                    <img class="featurette-image img-responsive" src="/r/i/index/featurettes/home-page.png" alt="What Valloc does for you">
                <%--</a>--%>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12 col-md-7">
                <a href="/account/create" class="btn btn-info btn btn-block">Sign Up <i class="fa fa-pencil"></i></a>
            </div>
            <div class="col-xs-12 col-md-5">
                <button class="btn btn-link summary pull-right">Top <i class="fa fa-angle-double-up"></i></button>
            </div>
        </div>
        <hr id="scrolltop-page-designer" class="featurette-divider">
        <div id="div-page-designer" class="row featurette">
            <div class="col-md-5 img-vertical-align-center">
                <span></span>
                <%--<a class='gallery-page-designer' href='/r/i/index/featurettes/page-designer-full.png'>--%>
                    <img class="featurette-image img-responsive" src="/r/i/index/featurettes/page-designer.png" alt="Page designer">
                <%--</a>--%>
            </div>
            <div class="col-md-7">
                <h3 class="featurette-heading"><span class="text-muted">Your pages are created and designed with the Valloc</span> Page Designer</h3>
                <p>In the Designer you make page changes such as setting your page title, path, privacy and more. Here you also add sections and links.  The arrangement of links and sections are done in a <i>visual</i> way technique where you can drag and drop them anywhere inside the columns you wish. Feel free to explore the Page Designer for other features. </p>
            </div>
        </div>
        <div class="row cushion-10-top">
            <div class="col-xs-12 col-md-7">
                <a href="/account/create" class="btn btn-info btn btn-block">Sign Up <i class="fa fa-pencil"></i></a>
            </div>
            <div class="col-xs-12 col-md-5">
                <button class="btn btn-link summary pull-right">Top <i class="fa fa-angle-double-up"></i></button>
            </div>
        </div>
        <hr id="scrolltop-mobility" class="featurette-divider">
        <div id="div-mobility" class="row featurette">
            <div class="col-md-7">
                <h3 class="featurette-heading">Mobile accessibility is key. <span class="text-muted">Your page content is accessible everywhere.  All devices, all browsers, via a simple URL.</span></h3>
                <p>With your phone or tablet, easily access your Valloc pages and collaborative content. On tablets, in either horizontal or vertical orientation, you will be able to easily reach your page content.  On phones, you can easily navigate page content by swiping left-to-right to columns, then down for sections and links.</p>
            </div>
            <div class="col-md-5 img-vertical-align-center">
                <span></span>
                <%--<a class='gallery-mobility' href='/r/i/index/featurettes/mobility-full.png'>--%>
                    <img class="featurette-image img-responsive" src="/r/i/index/featurettes/mobility.png" alt="Valloc Pages in mobile">
                <%--</a>--%>
            </div>
        </div>
        <div class="row cushion-10-top">
            <div class="col-xs-12 col-md-7">
                <a href="/account/create" class="btn btn-info btn btn-block">Sign Up <i class="fa fa-pencil"></i></a>
            </div>
            <div class="col-xs-12 col-md-5">
                <button class="btn btn-link summary pull-right">Top <i class="fa fa-angle-double-up"></i></button>
            </div>
        </div>
        <hr id="scrolltop-collaborate" class="featurette-divider">
        <div id="div-collaborate" class="row featurette">
            <div class="col-md-5 img-vertical-align-center">
                <span></span>
                <%--<a class='gallery-collaborate' href='/r/i/hiw/featurettes/collaboration-full.jpg'>--%>
                    <img class="featurette-image img-responsive" src="/r/i/index/featurettes/collaborate.png" alt="Collaborate with others">
                <%--</a>--%>
            </div>
            <div class="col-md-7">
                <h3 class="featurette-heading">Collaborate with others by sharing your pages.</h3>
                <p>At work you want to share links of frequently used websites with your professional team. At school you want to share class resources with students and teachers. With friends and family you want to share the websites most important to you. Share your pages for all these reasons.</p>
                <p>If you freely want to share your page content then set the visibility to <b>public</b>. Public pages are accessible without signing in.  If you need a private page that only you can access after signing in, and also over an encrypted channel, then set your page to <b>private</b>.  You can also share private pages by inviting page guests which is helpful when working in teams on confidential material. You can add page guests on the Page Designer by adding other Valloc users by his/her username.  If desired, set the <b>Can modify</b> checkbox to allow an individual ability to modify sections and links on the page.</p>
                <p>Communicate with other team members with note history for each history you share. Have a full conversation based off web document or even a shared DropBox link.</p>
            </div>
        </div>
        <div class="row cushion-10-top">
            <div class="col-xs-12 col-md-7">
                <a href="/account/create" class="btn btn-info btn btn-block">Sign Up <i class="fa fa-pencil"></i></a>
            </div>
            <div class="col-xs-12 col-md-5">
                <button class="btn btn-link summary pull-right">Top <i class="fa fa-angle-double-up"></i></button>
            </div>
        </div>
        <hr id="scrolltop-get-data-in" class="featurette-divider">
        <div id="div-get-data-in" class="row featurette">
            <div class="col-md-7">
                <h3 class="featurette-heading">
                    <span class="text-muted">Quickly build your pages by</span>
                        pinning links
                    <span class="text-muted">and</span>
                        importing existing bookmarks.
                </h3>
                <p>
                    Valloc makes it easy to get started and keep your pages <i>living</i>. Start with a simple 3-step import of your existing browser bookmarks to launch your first page.
                    Then by using any popular desktop browser's Valloc extension button, you can effortlessly pin links to any page to capture the current page information.
                </p>
                <p class="text-center hiw-browserButton">
                    <a href="javascript:void(0);" onclick="window.open('https://chrome.google.com/webstore/detail/pin-a-link-to-your-valloc/cllbbodlpkecaaioglgeebjfcnplpepc')">
                        <img src="/r/i/logo-v.png" class="v-logo-for-chrome" alt="Valloc link-pinning browser button">
                    </a>
                    <%--<img class="hiw-supportedBrowsers" src="/r/i/index/supported-browsers.png">--%>
                </p>
            </div>
            <div class="col-md-5 img-vertical-align-center">
                <span></span>
                <%--<a class='gallery-mobility' href='/r/i/index/featurettes/get-data-in-full.png'>--%>
                    <img class="featurette-image img-responsive" src="/r/i/index/featurettes/get-data-in.png" alt="Pinning and Importing">
                <%--</a>--%>
            </div>
        </div>
        <div class="row hidden-xs hidden-sm hiw-downloadBrowserButton">
            <div class="col-xs-12 text-center">
                <a class="btn btn-primary" href="javascript:void(0);" onclick="window.open('https://chrome.google.com/webstore/detail/pin-a-link-to-your-valloc/cllbbodlpkecaaioglgeebjfcnplpepc')">
                    Get it for CHROME &raquo;
                </a>
                <%--<a class="btn btn-primary btn-lg" href="javascript:void(0);" onclick="window.open('http://crossrider.com/install/43404')">--%>
                    <%--All Other Browsers &raquo;--%>
                <%--</a>--%>
            </div>
        </div>
        <div class="row cushion-10-top">
            <div class="col-xs-12 col-md-offset-9 col-md-3">
                <a href="/account/create" class="btn btn-info btn btn-block">Sign Up <i class="fa fa-pencil"></i></a>
            </div>
        </div>
        <div class="row cushion-10-top">
            <div class="col-xs-12">
                <button class="btn btn-link summary pull-right">Top <i class="fa fa-angle-double-up"></i></button>
            </div>
        </div>
        <hr class="featurette-divider">

    </div>
</div>
<%@include file="include/footer.jsp" %>
<div class="page-overhang"></div>
</body>
</html>
