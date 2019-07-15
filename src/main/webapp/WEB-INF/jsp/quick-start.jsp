<!doctype html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="utf-8" />
    <title>${getStartedQuickly}</title>
    <c:import var="scriptsFile" url="${scriptsFile}" />
    <c:out value="${scriptsFile}" escapeXml="false" />
    <link href="/r/bootstrap-3.0.0/css/bootstrap.css" rel="stylesheet" media="screen" />
    <script src="/r/bootstrap-3.0.0/js/bootstrap.js" type="text/javascript"></script>
    <link href="/r/c/vlc.css" rel="stylesheet" />
    <link href="/r/c/quickstart.css" rel="stylesheet" />
    <%@ include file="include/marketing.jsp" %>
</head>
<body>
<noscript>
    <h2>${clientEnableJavascript}</h2>
</noscript>
<%@include file="include/top-navbar.jsp" %>
<div class="container quickStartBox hidden-xs">
    <div class="row">
        <div class="col-sm-12 text-center">
            <h2>Quick Start</h2>
        </div>
    </div>
    <div class="row well">
        <div class="col-sm-12">
            <div class="page-header">
                <h3>Step 1: Export your browser bookmarks</h3>
                You bookmarks will need to first be exported to a file so they can next be uploaded to Valloc.
                For export instructions, click on the link below for the browser you use:
            </div>
            <div>
                <button type="button" class="btn btn-link" data-toggle="collapse" data-target="#instructions-chrome">
                    Chrome (50+)
                </button>
                <ol id="instructions-chrome" class="collapse">
                    <li>Click on the Chrome menu button in the top right corner</li>
                    <li>
                        Select <span class="browserComponent">Bookmarks</span> then <span class="browserComponent">Bookmarks Manager</span>
                    </li>
                    <li>Click the vertical elipsis menu button in the top right</li>
                    <li>Select <span class="browserComponent">Export bookmarks</span></li>
                    <li>Specify the location to export the file to and save the file</li>
                </ol>
            </div>
            <div>
                <button type="button" class="btn btn-link" data-toggle="collapse" data-target="#instructions-firefox">
                    Firefox (50+)
                </button>
                <ol id="instructions-firefox" class="collapse">
                    <li>
                        Follow the instructions <a href="https://support.mozilla.org/en-US/kb/export-firefox-bookmarks-to-backup-or-transfer" target="_blank">here</a>.
                    </li>
                </ol>
            </div>
            <div>
                <button type="button" class="btn btn-link" data-toggle="collapse" data-target="#instructions-safari">
                    Safari (10+)
                </button>
                <ol id="instructions-safari" class="collapse">
                    <li>
                        Open Safari and choose <span class="browserComponent">File</span> then <span class="browserComponent">Export Bookmarks</span>
                    </li>
                    <li>Specify the location to export the file to and save the file</li>
                </ol>
            </div>
            <div>
                <button type="button" class="btn btn-link" data-toggle="collapse" data-target="#instructions-ie">
                    Internet Explorer (9+)
                </button>
                <ol id="instructions-ie" class="collapse">
                    <li>
                        From the <span class="browserComponent">File</span> menu select <span class="browserComponent">Import and export</span>
                    </li>
                    <li>
                        Select the <span class="browserComponent">Export to a file</span> option then
                        <span class="browserComponent">Next</span>
                    </li>
                    <li>
                        Check the <span class="browserComponent">Favorites</span> checkbox then
                        <span class="browserComponent">Next</span>
                    </li>
                    <li>
                        Select the top-level <span class="browserComponent">Favorites</span> folder then
                        <span class="browserComponent">Next</span>
                    </li>
                    <li>
                        Click the <span class="browserComponent">Browse</span> button to select the location of where to save the file
                        <span class="browserComponent">Export</span> then <span class="browserComponent">Finish</span>
                    </li>
                </ol>
            </div>
        </div>
    </div>
    <div class="row well">
        <div class="col-sm-12">
            <div class="page-header">
                <h3>Step 2: Upload the bookmarks file to Valloc.</h3>
                Valloc will read the file and save all your bookmarks to a page called "Quick Start".
            </div>
                <form
                        action="/import"
                        method="POST"
                        enctype="multipart/form-data"
                        novalidate>
                <input id="bmFile"
                       name="bmFile"
                       type="file"
                        />
                <button type="submit" class="btn btn-primary pull-right">
                    ${import_title}
                </button>
            </form>
        </div>
    </div>
    <div class="row well">
        <div class="col-sm-12">
            <div class="page-header">
                <h3>Step 3: Re-arrange the <i>Quick Start</i> page to your liking.</h3>
                Once the upload is complete from step 2, you will be taken the <i>Page Designer</i> where you can make custom modifications, re-arrange the sections and links, change the page title and path and more.
            </div>
        </div>
    </div>
    <hr>
</div>
<div class="container quickStartBox visible-xs">
    <div class="well cushion-20-top">
        <h4>Sorry the quick start import is only available on desktop devices where you can export your bookmarks</h4>
    </div>
</div>
<%@include file="include/footer.jsp" %>
</body>
</html>



