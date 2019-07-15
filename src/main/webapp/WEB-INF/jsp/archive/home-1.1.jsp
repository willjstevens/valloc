<!doctype html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="utf-8" />
    <meta name="description" content='${tagline}' />
    <title>Valloc</title>
    <c:import var="scriptsFile" url="${scriptsFile}" />
    <c:out value="${scriptsFile}" escapeXml="false" />
    <link href="/r/bootstrap-3.0.0/css/bootstrap.css" rel="stylesheet" media="screen" />
    <link href="/r/colorbox/colorbox.css" rel="stylesheet" />
    <link href="/r/c/howitworks.css" rel="stylesheet" />
    <script src="/r/bootstrap-3.0.0/js/bootstrap.js" type="text/javascript"></script>
    <script src="/r/j/how-it-works.js" type="text/javascript"></script>
</head>
<body>
<noscript>
    <h2>${clientEnableJavascript}</h2>
</noscript>
<%@include file="../include/top-navbar.jsp" %>
<div class="how-it-works-box">

    <%-- Visible on desktops and tablets, not phones --%>
    <div id="hiw-carousel" class="carousel slide hidden-xs" data-interval="3000" >
        <ol class="carousel-indicators">
            <li data-target="#hiw-carousel" data-slide-to="0" class="active"></li>
            <li data-target="#hiw-carousel" data-slide-to="1"></li>
            <li data-target="#hiw-carousel" data-slide-to="2"></li>
            <li data-target="#hiw-carousel" data-slide-to="3"></li>
            <li data-target="#hiw-carousel" data-slide-to="4"></li>
            <li data-target="#hiw-carousel" data-slide-to="5"></li>
        </ol>
        <div class="carousel-inner">
            <div class="item text-center img-vertical-align-center active">
                <span></span>
                <img id="carousel-logo" class="carousel-img" src="/r/i/index/carousel/logo.png" alt="Valloc">
                <div class="container">
                    <div class="carousel-caption">
                        <h3>No-clutter personal and collaborative</h3>
                        <h3>bookmarking pages</h3>
                        <%--<h3>Share and browse efficiently.</h3>--%>
                    </div>
                </div>
            </div>
            <div class="item text-center img-vertical-align-center">
                <span></span>
                <img id="carousel-informational" class="carousel-img" src="/r/i/index/carousel/sample-home-page.png" alt="Valloc Pages">
                <div class="container">
                    <div class="carousel-caption">
                        <h3>Valloc pages have memorable URLs</h3>
                    </div>
                </div>
            </div>
            <div class="item text-center img-vertical-align-center">
                <span></span>
                <img id="carousel-page-designer" class="carousel-img" src="/r/i/index/carousel/page-designer.png" alt="Page Designer">
                <div class="container">
                    <div class="carousel-caption">
                        <h3>The Page Designer</h3>
                    </div>
                </div>
            </div>
            <div class="item text-center img-vertical-align-center">
                <span></span>
                <img id="carousel-mobility" class="carousel-img" src="/r/i/index/carousel/mobility-phones.png" alt="Valloc Mobile Pages">
                <div class="container">
                    <div class="carousel-caption">
                        <h3>Valloc pages in mobile</h3>
                    </div>
                </div>
            </div>
            <div class="item text-center img-vertical-align-center">
                <span></span>
                <img id="carousel-collaborate" class="carousel-img" src="/r/i/index/carousel/collaborate.png" alt="Collaborate">
                <div class="container">
                    <div class="carousel-caption">
                        <h3>Publicly or privately collaborate with others</h3>
                    </div>
                </div>
            </div>
            <div class="item text-center img-vertical-align-center">
                <span></span>
                <img id="carousel-get-data-in" class="carousel-img" src="/r/i/index/carousel/get-data-in.png" alt="Quickly build your pages">
                <div class="container">
                    <div class="carousel-caption">
                        <h3>Pin links anytime from your browser</h3>
                    </div>
                </div>
            </div>
        </div>
        <div class="pull-right hidden-xs hidden-sm">
            <button type="button" class="btn btn-primary btn-lg scroll-btn">
                More Info <span class="glyphicon glyphicon-arrow-down"></span>
            </button>
        </div>
        <a class="left carousel-control" href="#hiw-carousel" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a>
        <a class="right carousel-control" href="#hiw-carousel" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
    </div>

    <%-- Visible on phones, not desktops and tablets --%>
    <div class="text-center visible-xs">
        <img src="/r/i/vlogo-midsize.png" alt="Valloc">
        <h4>No-clutter personal and collaborative</h4>
        <h4>bookmarking pages</h4>
        <%--<h4>Share and browse efficiently.</h4>--%>
        <div class="highlighted-background">
            <hr />
            <p><b>Please see the homepage on a full sized desktop for illustrations and a better understanding.</b></p>
            <hr />
        </div>
    </div>

    <div class="container marketing">
        <div class="row">
            <div class="col-lg-4 hidden-xs">
                <h2>Already signed up? Get started with an import.</h2>
                <p>With a quick 3-step process, you can import existing browser bookmarks into a "/quickstart" page, make some modifications, save, then you have your first page!</p>
                <p><a class="btn btn-default" href="/quickstart">Import &raquo;</a></p>
            </div>
            <div class="col-lg-4">
                <h2>Sign up</h2>
                <p>
                    It's free. Sign-up and get started.
                    After registering you can create pages or start by importing your existing bookmarks.
                </p>
                <p><a class="btn btn-default" href="/account/create">Sign up &raquo;</a></p>
            </div>
            <div class="col-lg-4 hidden-xs">
                <h2>Get the Valloc link-pinning browser button.</h2>
                <p>
                    Clicking on this browser-level button will capture the active tab's title and URL.
                    After clicking the browser button, in just 3 clicks you can select the page and section to store the link, and then return to your current browsing.
                </p>
                <p><a class="btn btn-default" href="http://crossrider.com/install/43404">Link pinning button &raquo;</a></p>
            </div>
        </div>
    </div>
    <hr id="scrolltop-summary" class="featurette-divider">
    <h2>Summary</h2>
    <div class="list-group">
        <a id="btn-informational" href="#" class="list-group-item active">
            <h4 class="list-group-item-heading">What Valloc provides</h4>
            <p class="list-group-item-text">
                You want to have all your favorite website links in one location, laid out visually for quick direct use.
                You want to access them from all devices.
                You want to reach them from a simple and memorable URL.
                <b>Valloc will do this for you.</b>
            </p>
        </a>
        <a id="btn-page-designer" href="#" class="list-group-item">
            <h4 class="list-group-item-heading">The Page Designer</h4>
            <p class="list-group-item-text">
                Pages are easily created and updated with the Valloc Page Designer.
                The Page Designer lets you add and continually update content and settings on your page.
                Sections and links are arranged in visual fashion by dragging and dropping each wherever you like on the page.
            </p>
        </a>
        <a id="btn-mobility" href="#" class="list-group-item">
            <h4 class="list-group-item-heading">Mobile accessibility is key</h4>
            <p class="list-group-item-text">
                Your page content will be accessible everywhere.  All devices, most browsers, via the same simple URL.
            </p>
        </a>
        <a id="btn-collaborate" href="#" class="list-group-item">
            <h4 class="list-group-item-heading">Collaborate with others by sharing your public and private pages</h4>
            <p class="list-group-item-text">
                Public pages are openly accessible over the Internet. Private pages are too, but require you to be signed in, and are served with encryption over SSL. You can add <i>page guests</i> to private pages if you wish to share private pages with other Valloc users.
            </p>
        </a>
        <a id="btn-get-data-in" href="#" class="list-group-item">
            <h4 class="list-group-item-heading">Quickly build your pages by pinning links or importing existing bookmarks</h4>
            <p class="list-group-item-text">
                Valloc makes it easy to get started and keep your pages "living". Start with a simple 3-step import of your existing browser bookmarks to launch your first page.
                Then by using any popular desktop browser's Valloc extension button, you can effortlessly pin links to any page to capture the current page information.
            </p>
        </a>
    </div>
    <hr id="scrolltop-informational" class="featurette-divider">
    <div id="div-informational" class="row featurette">
        <div class="col-md-7">
            <h3 class="featurette-heading">
                <span class="text-muted">You want to have all your favorite website links in one location.  You want to access them from all devices and all browsers. You want to reach them from a simple and easy-to-remember URL.</span>
                Valloc will do this for you.
            </h3>
            <p>Valloc pages are accessible in a browser. Your browser page URL will first contain your username followed by the page path you provided.  For example, if your username is <i>johnsmith</i> and you created a page with a path <i>school</i>, then the page path would be "www.valloc.com/johnsmith/school". How easy is that?</p>
        </div>
        <div class="col-md-5 img-vertical-align-center">
            <span></span>
            <a class='gallery-informational' href='/r/i/index/featurettes/home-page-full.png'>
                <img class="featurette-image img-responsive" src="/r/i/index/featurettes/home-page.png" alt="What Valloc does for you">
            </a>
        </div>
    </div>
    <hr id="scrolltop-page-designer" class="featurette-divider">
    <div id="div-page-designer" class="row featurette">
        <div class="col-md-5 img-vertical-align-center">
            <span></span>
            <a class='gallery-page-designer' href='/r/i/index/featurettes/page-designer-full.png'>
                <img class="featurette-image img-responsive" src="/r/i/index/featurettes/page-designer.png" alt="Page designer">
            </a>
        </div>
        <div class="col-md-7">
            <h3 class="featurette-heading"><span class="text-muted">Your pages are created and designed with the Valloc</span> Page Designer</h3>
            <p>In the Designer you make page changes such as setting your page title, path, privacy and more. Here you also add sections and links.  The arrangement of links and sections are done in a <i>visual</i> way technique where you can drag and drop them anywhere inside the columns you wish. Feel free to explore the Page Designer for other features. </p>
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
            <a class='gallery-mobility' href='/r/i/index/featurettes/mobility-full.png'>
                <img class="featurette-image img-responsive" src="/r/i/index/featurettes/mobility.png" alt="Valloc Pages in mobile">
            </a>
        </div>
    </div>
    <hr id="scrolltop-collaborate" class="featurette-divider">
    <div id="div-collaborate" class="row featurette">
        <div class="col-md-5 img-vertical-align-center">
            <span></span>
            <a class='gallery-collaborate' href='/r/i/index/featurettes/collaborate-full.png'>
                <img class="featurette-image img-responsive" src="/r/i/index/featurettes/collaborate.png" alt="Collaborate with others">
            </a>
        </div>
        <div class="col-md-7">
            <h3 class="featurette-heading">Collaborate with others by sharing your pages.</h3>
            <p>At work you want to share links of frequently used websites with your professional team. At school you want to share class resources with students and teachers. With friends and family you want to share the websites most important to you. Share your pages for all these reasons.</p>
            <p>If you freely want to share your page content then set the visibility to <b>public</b>. Public pages are accessible without signing in.  If you need a private page that only you can access after signing in, and also over an encrypted channel, then set your page to <b>private</b>.  You can also share private pages by inviting page guests which is helpful when working in teams on confidential material. You can add page guests on the Page Designer by adding other Valloc users by his/her username.  If desired, set the <b>Can modify</b> checkbox to allow an individual ability to modify sections and links on the page.</p>
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
                <a href="javascript:void(0);" onclick="window.open('http://crossrider.com/install/43404')">
                    <img src="/r/i/index/featurettes/v.png" alt="Valloc link-pinning browser button">
                </a>
                <img class="hiw-supportedBrowsers" src="/r/i/index/supported-browsers.png">
            </p>
        </div>
        <div class="col-md-5 img-vertical-align-center">
            <span></span>
            <a class='gallery-mobility' href='/r/i/index/featurettes/get-data-in-full.png'>
                <img class="featurette-image img-responsive" src="/r/i/index/featurettes/get-data-in.png" alt="Pinning and Importing">
            </a>
        </div>
    </div>
    <div class="row hidden-xs hidden-sm hiw-downloadBrowserButton">
        <div class="col-xs-12 text-center">
            <a class="btn btn-primary btn-lg" href="javascript:void(0);" onclick="window.open('http://crossrider.com/install/43404')">
                Get the Valloc link-pinning button &raquo;
            </a>
        </div>
    </div>
    <hr class="featurette-divider">
</div>
<%@include file="../include/footer.jsp" %>
<div class="page-overhang"></div>
</body>
</html>
