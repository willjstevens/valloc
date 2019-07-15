<!doctype html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="utf-8" />
    <meta name="description" content='${tagline}' />
    <title>Valloc</title>
    <c:import var="scriptsFile" url="${scriptsFile}" />
    <c:out value="${scriptsFile}" escapeXml="false" />
    <meta name="keywords" content="bookmarks, bookmarking, bookmark, pinning, collaboration">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="/r/home-1.2/lib/css/bootstrap.css">
    <link rel="stylesheet" href="/r/home-1.2/lib/css/bootstrap-glyphicons.css">
    <link rel="stylesheet" href="/r/home-1.2/lib/css/font-awesome.css">
    <link rel="stylesheet" href="/r/home-1.2/lib/js/prettyphoto/css/prettyPhoto.css">
    <link rel="stylesheet" href="/r/home-1.2/lib/css/animate.css" />
    <link rel="stylesheet" id="prod-css" href="/r/home-1.2/lib/css/style.css">
    <link rel="stylesheet" href="/r/c/vlc.css">
    <link rel="stylesheet" href="/r/home-1.2/lib/css/vlc-home.css">
    <%@ include file="include/marketing.jsp" %>
</head>
<body data-spy="scroll" data-target="#navbar-spy" data-offset="160">

<!--[if lt IE 7]>
<p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
<![endif]-->
<div id="wrapper">
<div id="home" class="container-section">
    <div id="navbar-section" class="navbar navbar-static-top">
        <div class="container">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-responsive-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">VALLOC</a>
            <div id="navbar-spy" class="nav-collapse collapse navbar-responsive-collapse">
                <ul id="nav-ul" class="nav navbar-nav pull-right">
                    <%--<li><a class="active" href="#home">HOME</a></li>--%>
                    <li><a href="#intro">2 MINUTE INTRO</a></li>
                    <li><a class="active" href="#summary">SUMMARY</a></li>
                    <li><a href="#features">FEATURES</a></li>
                    <li><a href="#contact">CONTACT</a></li>
                    <li><a href="/login">LOGIN <i class="icon-signin icon-1x"></i></a></li>
                </ul>
            </div>
        </div>
    </div>
</div>
<div id="carousel-section" class="container-section">
    <div id="carousel-1" class="carousel slide">
        <ol class="carousel-indicators visible-lg">
            <li data-target="#carousel-1" data-slide-to="0" class="active"></li>
            <li data-target="#carousel-1" data-slide-to="1"></li>
            <li data-target="#carousel-1" data-slide-to="2"></li>
         </ol>
         <div class="carousel-inner">
            <div class="item active">
                <img src="/r/home-1.2/images/slide1.png" alt="">
                <div class="carousel-caption">
                    <h3 class="carousel-title hidden-sm">VALLOC BOOKMARKING PAGES</h3>
                    <p class="carousel-body">NO-CLUTTER PERSONAL AND COLLABORATIVE BOOKMARKING</p>
                </div>
            </div>
            <div class="item">
                <img src="/r/home-1.2/images/slide3.png" alt="">
                <div class="carousel-caption">
                    <h3 class="carousel-title hidden-sm">MOBILE AND REACHABLE FROM ALL DEVICES</h3>
                </div>
            </div>
             <div class="item home-slideBackground">
                 <img src="/r/home-1.2/images/slide2_new.jpeg" alt="">
             </div>
         </div>
        <a class="left carousel-control" href="#carousel-1" data-slide="prev">
            <span class="glyphicon glyphicon-chevron-left"></span>
        </a>
        <a class="right carousel-control" href="#carousel-1" data-slide="next">
            <span class="glyphicon glyphicon-chevron-right"></span>
        </a>
    </div>
</div>
<hr />
<div id="intro" class="container">
    <div class="row">
        <div class="col-12 text-center">
            <h3>A VALLOC 2 MINUTE INTRODUCTION</h3>
        </div>
        <%--For laptops and larger--%>
        <div class="col-12 text-center visible-lg">
            <iframe width="853" height="480" src="//www.youtube.com/embed/KZLyv6wbX64?rel=0" frameborder="0" allowfullscreen></iframe>
        </div>
        <%--For tablets--%>
        <div class="col-12 text-center visible-md">
            <iframe width="640" height="360" src="//www.youtube.com/embed/KZLyv6wbX64?rel=0" frameborder="0" allowfullscreen></iframe>
        </div>
        <%--For phones--%>
        <div class="col-12 text-center visible-sm">
            <iframe width="560" height="315" src="//www.youtube.com/embed/KZLyv6wbX64?rel=0" frameborder="0" allowfullscreen></iframe>
        </div>
    </div>
</div>
<hr />

<div class="container">
    <div class="row">
        <div class="col-sm-6 col-12 home-bigBtnsCols">
            <p class="text-center"><i class="icon-star icon-5x"></i></p>
            <h4 class="text-center">Sign-up today. <strong>It's Free.</strong></h4>
            <p class="text-center">Then get started with a fast import of your existing browser favorites to quickly create your first Valloc page.</p>
            <a href="/account/create" class="btn btn-brand btn-large btn-block">
                <span></span>
                Register <span class="icon-pencil home-bigBtnIcon"></span>
            </a>
        </div>
        <div class="col-sm-6 col-12 home-bigBtnsCols">
            <p class="text-center"><i class="icon-signin icon-5x"></i></p>
            <h4 class="text-center">Login and visit your dashboard.</h4>
            <p class="text-center">
                Remember to get the Valloc link-pinning button for quick link adding:
                for <a href="https://chrome.google.com/webstore/detail/pin-a-link-to-your-valloc/cllbbodlpkecaaioglgeebjfcnplpepc">Chrome</a>
                <%--or--%>
                <%--<a href="http://crossrider.com/install/43404">all other browsers</a>.--%>
            </p>
            <a href="/login" class="btn btn-brand btn-large btn-block">
                <span></span>
                ${login} <span class="icon-signin home-bigBtnIcon"></span>
            </a>
        </div>
    </div>
</div>
<hr />


<div id="summary" class="services-section global-section container-section">
    <div class="container">

        <div class="row">
            <div class="section-header">
                <h3 class="section-header-title">Summary</h3>
                <p class="section-header-body">Explore the different corners of Valloc</p>
            </div>
        </div>

        <div class="row services-row services-row-head services-row-1">
            <div class="col-12 col-sm-4 col-lg-4">
                <div class="services-group">
                    <p class="services-icon"><i class="icon-desktop icon-5x"></i></p>
                    <h4 class="services-title">VALLOC PAGES</h4>
                    <p class="services-body">
                        Browse the Web and visit any page.
                        Pin the web page to a Valloc page section in only 4 clicks and continue browsing.
                        Later visit your page to browse your saved sites.
                    </p>
                </div>
            </div>
            <div class="col-12 col-sm-4 col-lg-4">
                <div class="services-group">
                    <p class="services-icon"><i class="icon-mobile-phone icon-5x"></i></p>
                    <h4 class="services-title">MOBILE</h4>
                    <p class="services-body">
                        Your page content will be accessible everywhere.
                        Browse your pages from your phone and swipe through columns, and scroll down to quickly access link sections.
                        On a tablet, you have full visibility to all pages and can use the Page Designer to touch-n-drag your pages the way you prefer.
                    </p>
                </div>
            </div>
            <div class="col-12 col-sm-4 col-lg-4">
                <div class="services-group">
                    <p class="services-icon"><i class="icon-group icon-5x"></i></p>
                    <h4 class="services-title">COLLABORATION</h4>
                    <p class="services-body">
                        Public pages are openly accessible over the Internet.
                        Private pages are too, but require you to be signed in, and are served with encryption over SSL.
                        You can add <i>page guests</i> to private pages if you wish to share private pages with other Valloc users.
                        <strong>NEW team link Notes to communicate with team members on each link!</strong>
                    </p>
                    <%--<p class="services-more"><a href="#">Find Out More</a></p>--%>
                </div>
            </div>
        </div>
        <div class="row services-row services-row-tail services-row-2">
            <div class="col-12 col-sm-4 col-lg-4">
                <div class="services-group">
                    <p class="services-icon"><span class="icon-gear icon-5x"></span></p>
                    <h4 class="services-title">PAGE DESIGNER</h4>
                    <p class="services-body">
                        Pages are easily created and updated with the Valloc Page Designer.
                        The Page Designer lets you add and continually update content and settings on your page.
                        Sections and links are arranged in visual fashion by dragging and dropping each wherever you like on the page.
                    </p>
                    <%--<p class="services-more"><a href="#">Find Out More</a></p>--%>
                </div>
            </div>
            <div class="col-12 col-sm-4 col-lg-4">
                <div class="services-group">
                    <p class="services-icon"><i class="icon-pushpin icon-5x"></i></p>
                    <h4 class="services-title">LINK PINNING</h4>
                    <p class="services-body">
                        Effortlessly pin links to save the link to your Valloc page.
                        Valloc provides browser extension buttons that sit "outside" the webpage so you can pin any web page to your preferred Valloc page and section - in less than 4 clicks.
                    </p>
                    <p class="services-more"><a href="https://chrome.google.com/webstore/detail/pin-a-link-to-your-valloc/cllbbodlpkecaaioglgeebjfcnplpepc"><h4>GET IT FOR CHROME</h4></a></p>
                    <%--<p class="services-more"><a href="http://crossrider.com/install/43404"><h4>ALL OTHER BROWSERS</h4></a></p>--%>
                </div>
            </div>
            <div class="col-12 col-sm-4 col-lg-4">
                <div class="services-group">
                    <p class="services-icon"><i class="icon-arrow-down icon-5x"></i></p>
                    <h4 class="services-title">QUICKSTART IMPORT</h4>
                    <p class="services-body">
                        Quickly launch your first page with a simple, 3-step import of your existing browser bookmarks.
                        Then go immediately to the Page Designer to rearrange things, just how you like it.
                    </p>
                    <p class="services-more"><a href="/quickstart"><h4>IMPORT YOUR BOOKMARKS</h4></a></p>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="parallax-section parallax-section-1" data-stellar-vertical-offset="-300" data-stellar-background-ratio="0.5">
    <h2 class="parallax-title">VALLOC PAGES</h2>
    <p class="parallax-body">NO-CLUTTER PERSONAL AND COLLABORATIVE BOOKMARKING PAGES</p>
</div>
<div id="features" class="portfolio-section global-section container-section">

    <div class="container">
        <div class="row">
            <div class="section-header">
                <h3 class="section-header-title">FEATURES</h3>
                <p class="section-header-body">Click or touch the tile below to see more information.</p>
                <div class="nav-pills-centered">
                    <ul id="filters" class="nav nav-pills">
                        <li><a href="javascript:;" data-option-value="*" class="active">ALL</a></li>
                        <li><a href="javascript:;" data-option-value=".pages">VALLOC PAGES</a></li>
                        <li><a href="javascript:;" data-option-value=".mobile">MOBILE</a></li>
                        <li><a href="javascript:;" data-option-value=".collaboration">COLLABORATION</a></li>
                        <li><a href="javascript:;" data-option-value=".designer">PAGE DESIGNER</a></li>
                        <li><a href="javascript:;" data-option-value=".dashboard">PAGE DASHBOARD</a></li>
                        <li><a href="javascript:;" data-option-value=".pinning">LINK PINNING</a></li>
                        <li><a href="javascript:;" data-option-value=".importing">IMPORTING</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="container">
        <div id="projects" class="projects-caption">
            <div class="element pages">
                <figure>
                    <img src="/r/home-1.2/images/features/tile-page-top-corner.png" alt="" class="img-responsive" />
                    <figcaption>
                        <h4 class="projects-caption-title">VALLOC PAGES</h4>
                        <div>
                            <a href="/r/home-1.2/images/features/full-page-simple.png" class="projects-caption-icon projects-caption-view projects-caption-link" data-rel="prettyPhoto"><i class="icon-plus"></i></a>
                        </div>
                    </figcaption>
                </figure>
            </div>
            <div class="element pages mobile">
                <figure>
                    <img src="/r/home-1.2/images/features/tile-mobile-pages.png" alt="Valloc pages on your mobile device." class="img-responsive" />
                    <figcaption>
                        <h4 class="projects-caption-title">PAGES ON MOBILE</h4>
                        <div>
                            <a href="/r/home-1.2/images/features/full-mobile-pages.png"
                               class="projects-caption-icon projects-caption-view projects-caption-link"
                               data-rel="prettyPhoto">
                                <i class="icon-plus"></i>
                            </a>
                        </div>
                    </figcaption>
                </figure>
            </div>
            <div class="element pages">
                <figure>
                    <img src="/r/home-1.2/images/features/tile-page-url.png" alt="" class="img-responsive" />
                    <figcaption>
                        <h4 class="projects-caption-title">SIMPLISTIC URLS</h4>
                        <div>
                            <a href="/r/home-1.2/images/features/full-page-url.png" class="projects-caption-icon projects-caption-view projects-caption-link" data-rel="prettyPhoto"><i class="icon-plus"></i></a>
                        </div>
                    </figcaption>
                </figure>
            </div>
            <div class="element mobile">
                <figure>
                    <img src="/r/home-1.2/images/features/tile-mobile-phone-tablet.png" alt="Valloc pages on your mobile device." class="img-responsive" />
                    <figcaption>
                        <h4 class="projects-caption-title">MOBILE</h4>
                        <div>
                            <a href="/r/home-1.2/images/features/full-mobile.png"
                               class="projects-caption-icon projects-caption-view projects-caption-link"
                               data-rel="prettyPhoto">
                                <i class="icon-plus"></i>
                            </a>
                        </div>
                    </figcaption>
                </figure>
            </div>
            <div class="element collaboration">
                <figure>
                    <img src="/r/home-1.2/images/features/tile-collaboration-private-page.png" alt="Valloc pages on your mobile device." class="img-responsive" />
                    <figcaption>
                        <h4 class="projects-caption-title">GUEST EDITING AND VIEWING</h4>
                        <div>
                            <a href="/r/home-1.2/images/features/full-collaboration-page-edit.png"
                               class="projects-caption-icon projects-caption-view projects-caption-link"
                               data-rel="prettyPhoto">
                                <i class="icon-plus"></i>
                            </a>
                        </div>
                    </figcaption>
                </figure>
            </div>
            <div class="element designer">
                <figure>
                    <img src="/r/home-1.2/images/features/tile-page-designer.png" alt="Valloc pages on your mobile device." class="img-responsive" />
                    <figcaption>
                        <h4 class="projects-caption-title">PAGE DESIGNER</h4>
                        <div>
                            <a href="/r/home-1.2/images/features/full-page-designer.png"
                               class="projects-caption-icon projects-caption-view projects-caption-link"
                               data-rel="prettyPhoto">
                                <i class="icon-plus"></i>
                            </a>
                        </div>
                    </figcaption>
                </figure>
            </div>
            <div class="element mobile">
                <figure>
                    <img src="/r/home-1.2/images/features/tile-mobile-interaction.png" alt="Valloc pages on your mobile device." class="img-responsive" />
                    <figcaption>
                        <h4 class="projects-caption-title">MOBILE NAVIGATION</h4>
                        <div>
                            <a href="/r/home-1.2/images/features/full-mobile-interaction.png"
                               class="projects-caption-icon projects-caption-view projects-caption-link"
                               data-rel="prettyPhoto">
                                <i class="icon-plus"></i>
                            </a>
                        </div>
                    </figcaption>
                </figure>
            </div>
            <div class="element collaboration">
                <figure>
                    <img src="/r/home-1.2/images/features/tile-collaboration-modal.png" alt="Valloc pages on your mobile device." class="img-responsive" />
                    <figcaption>
                        <h4 class="projects-caption-title">COLLABORATION</h4>
                        <div>
                            <a href="/r/home-1.2/images/features/full-collaboration-modal.png"
                               class="projects-caption-icon projects-caption-view projects-caption-link"
                               data-rel="prettyPhoto">
                                <i class="icon-plus"></i>
                            </a>
                        </div>
                    </figcaption>
                </figure>
            </div>
            <div class="element importing">
                <figure>
                    <img src="/r/home-1.2/images/features/tile-import.png" alt="Valloc pages on your mobile device." class="img-responsive" />
                    <figcaption>
                        <h4 class="projects-caption-title">BOOKMARKS IMPORT</h4>
                        <div>
                            <a href="/r/home-1.2/images/features/full-import.png"
                               class="projects-caption-icon projects-caption-view projects-caption-link"
                               data-rel="prettyPhoto">
                                <i class="icon-plus"></i>
                            </a>
                        </div>
                    </figcaption>
                </figure>
            </div>
            <div class="element pinning">
                <figure>
                    <img src="/r/home-1.2/images/features/tile-pinning.png" alt="Valloc pages on your mobile device." class="img-responsive" />
                    <figcaption>
                        <h4 class="projects-caption-title">LINK PINNING</h4>
                        <div>
                            <a href="/r/home-1.2/images/features/full-pinning.png"
                               class="projects-caption-icon projects-caption-view projects-caption-link"
                               data-rel="prettyPhoto">
                                <i class="icon-plus"></i>
                            </a>
                        </div>
                    </figcaption>
                </figure>
            </div>
            <div class="element dashboard">
                <figure>
                    <img src="/r/home-1.2/images/features/tile-dashboard.png" alt="Valloc pages on your mobile device." class="img-responsive" />
                    <figcaption>
                        <h4 class="projects-caption-title">PAGE DASHBOARD</h4>
                        <div>
                            <a href="/r/home-1.2/images/features/full-dashboard.png"
                               class="projects-caption-icon projects-caption-view projects-caption-link"
                               data-rel="prettyPhoto">
                                <i class="icon-plus"></i>
                            </a>
                        </div>
                    </figcaption>
                </figure>
            </div>
        </div>
    </div>
</div>
<div class="parallax-section parallax-section-2" data-stellar-vertical-offset="-300" data-stellar-background-ratio="0.5">
    <h2 class="parallax-title">PAGES ON ALL DEVICES</h2>
    <p class="parallax-body">YOUR PAGES WILL BE ACCESSIBLE EVERYWHERE.</p>
</div>
<%--<div id="about" class="about-section global-section container-section">--%>
    <%--<div class="container">--%>
        <%--<div class="row">--%>
            <%--<div class="section-header-text">--%>
                <%--<h3 class="section-header-title">ABOUT US</h3>--%>
                <%--<p class="section-header-body-lead">Proin pretium tristique tincidunt. Nunc viverra sapien metus, non sagittis odio commodo sit amet. Phasellus non velit pellentesque, ullamcorper lacus vitae, eleifend augue. Etiam at luctus urna. Ut eget mollis arcu. Sed id placerat augue, non sollicitudin est. Aenean congue ligula turpis, eu imperdiet dui luctus a. Nam fringilla nunc quis iaculis condimentum. Nulla eu magna sollicitudin, blandit arcu mollis, commodo orci.</p>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>
<div id="contact" class="contact-section global-section container-section">

    <div class="container feedback-containerBox" ng-app="vlc">
        <form name="feedbackForm"
              ng-submit="saveContactMessage()"
              ng-controller="FeedbackController"
              novalidate>
            <div class="error" ng-class="formSupport.displayErrorSectionNEW()">
                <div ng-show="violations.nameRequired">${feedback_name_validate_required}</div>
                <div ng-show="violations.emailRequired">${feedback_email_validate_required}</div>
                <div ng-show="violations.emailFormat">${feedback_email_validate_format}</div>
                <div ng-show="violations.commentRequired">${feedback_comment_validate_required}</div>
            </div>
            <alert ng-repeat="alert in alerts" type="alert.type" close="closeAlert($index)">{{alert.msg}}</alert>
                <div class="row">
                    <div class="section-header-text feedback-containerBox">
                        <h3 class="section-header-title">CONTACT US</h3>
                        <p class="section-header-body">Feel free to ask questions, for feature requests or support issues.</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12 col-sm-12 form-group">
                        <label for="name" class="pull-left">NAME</label>
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
                        <label for="email" class="pull-left">EMAIL</label>
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
                        <label for="comment" class="pull-left">COMMENT</label>
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
                    <span class="col-12 form-group">
                            <button type="submit"
                                    class="btn btn-brand btn-lg btn-block"
                                    ng-disabled="feedbackForm.$invalid || isUnchanged(user) || isValidAndSubmitted"
                                    >
                                SUBMIT
                            </button>
                    </span>
                </div>
        </form>
    </div>
</div>
<div id="footer" class="footer-section">
    <ul class="widget_social-list-wrapper widget_social-two list-inline clearfix">
        <li class="facebook">
            <a title="Facebook" onclick="window.open('https://www.facebook.com/valloccom/');"></a>
        </li>
        <%--<li class="twitter">--%>
            <%--<a href="https://twitter.com/share" target="_blank" title="Twitter" data-toggle="tooltip" data-url="http://www.valloc.com" data-count="none">Twitter</a>--%>
        <%--</li>--%>
        <%--<li class="gplus">--%>
            <%--<a title="Google+" onclick="popUp=window.open('https://plus.google.com/share?url=http://www.valloc.com', 'popupwindow', 'scrollbars=yes,width=800,height=400');popUp.focus();return false"></a>--%>
        <%--</li>--%>
        <%--<li class="linkedin">--%>
            <%--<a title="LinkedIn" onclick="popUp=window.open('http://www.linkedin.com/shareArticle?mini=true&url=http%3A%2F%2Fwww.valloc.com&summary=Check+us+out&title=No-clutter+personal+and+collaborative+bookmarking+pages.');"></a>--%>
        <%--</li>--%>
    </ul>
    <ul class="list-inline text-center">
        <c:choose>
            <c:when test="${not empty sessionScope.vsession}">
                <li><a href="/logout">LOGOUT</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="/login">LOGIN</a></li>
            </c:otherwise>
        </c:choose>
        <li><a href="/dashboard">DASHBOARD</a></li>
        <li><a href="/howitworks">HOW IT WORKS</a></li>
        <li><a href="/feedback">FEEDBACK</a></li>
        <li><a href="/privacypolicy">PRIVACY POLICY</a></li>
        <li><a href="/termsofservice">TERMS OF SERVICE</a></li>
    </ul>
    <div class="copyright text-center"><small>&copy;&nbsp;2012-2018 COPYRIGHT: VALLOC, LLC. ALL RIGHTS RESERVED</small></div>
</div>
</div>
<script src="/r/home-1.2/lib/js/jquery.min.js"></script>
<script src="/r/home-1.2/lib/js/bootstrap.js"></script>
<script src="/r/home-1.2/lib/js/prettyphoto/js/jquery.prettyPhoto.js"></script>
<script src="/r/home-1.2/lib/js/plugins.js"></script>
<script src="/r/home-1.2/lib/js/custom.js?v=4"></script>
</body>
</html>