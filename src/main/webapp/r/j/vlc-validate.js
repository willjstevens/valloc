"use strict";

(function() {
    /*
     * BrowserCompatibilityValidator
     */
    var BrowserCompatibilityValidator = function BrowserCompatibilityValidator() {};
    BrowserCompatibilityValidator.prototype.constructor = BrowserCompatibilityValidator;
    BrowserCompatibilityValidator.ACCEPTIBLE_BROWSERS = [
        { browser: "chrome", 	minVer: 18 },
        { browser: "ie", 		minVer: 9 },
        { browser: "firefox", 	minVer: 17 },
        { browser: "safari", 	minVer: 5 },
        { browser: "opera",		minVer: 12 }
    ];
    BrowserCompatibilityValidator.prototype.validate = function() {
        var validateResult = {
            isValid: false,
            message: ''
        };
        var thisBrowserInfo = this.getBrowserInfo();
//        alert('browser = ' + thisBrowserInfo.browser + ' version = ' + thisBrowserInfo.version);
        var isRegulatedBrowser = false;
        var i = 0; // use old school for loop (not forEach) since we don't know browser version yet
        for (; i < BrowserCompatibilityValidator.ACCEPTIBLE_BROWSERS.length; i++) {
            var compatibleBrowser = BrowserCompatibilityValidator.ACCEPTIBLE_BROWSERS[i];
            if (thisBrowserInfo.browser === compatibleBrowser.browser) {
                isRegulatedBrowser = true;
                if (thisBrowserInfo.version >= compatibleBrowser.minVer) {
                    validateResult.isValid = true;
                } else {
                    var errorMsg = "Please upgrade to version {0} of {1} to use Valloc.com.";
                    errorMsg = errorMsg.replace("{0}", compatibleBrowser.minVer);
                    errorMsg = errorMsg.replace("{1}", thisBrowserInfo.browserName);
                    validateResult.message = errorMsg;
                }
                break;
            }
        }
        // Here, if we do not recognize the browser, then just flag as valid.  This siuation could arise
        //      if it is some bot or crawler, multiple mobile versions (like vendor flavors of Android)
        if (!isRegulatedBrowser) {
            validateResult.isValid = true;
        }

        return validateResult;
    };
    BrowserCompatibilityValidator.prototype.getBrowserInfo = function() {
        var nAgt = navigator.userAgent;
        var browserName  = navigator.appName;
        var fullVersion  = '' + parseFloat(navigator.appVersion);
        var majorVersion = parseInt(navigator.appVersion, 10);
        var nameOffset, verOffset, ix;
        var browser = '';

        // In Opera, the true version is after "Opera" or after "Version"
        if ((verOffset=nAgt.indexOf("Opera"))!=-1) {
            browserName = "Opera";
            browser = "opera";
            fullVersion = nAgt.substring(verOffset+6);
            if ((verOffset=nAgt.indexOf("Version"))!=-1)
                fullVersion = nAgt.substring(verOffset+8);
        }
        // In MSIE, the true version is after "MSIE" in userAgent
        else if ((verOffset=nAgt.indexOf("MSIE"))!=-1) {
            browserName = "Microsoft Internet Explorer";
            browser = "ie";
            fullVersion = nAgt.substring(verOffset+5);
        }
        // In Chrome, the true version is after "Chrome"
        else if ((verOffset=nAgt.indexOf("Chrome"))!=-1) {
            browserName = "Chrome";
            browser = "chrome";
            fullVersion = nAgt.substring(verOffset+7);
        }
        // In Safari, the true version is after "Safari" or after "Version"
        else if ((verOffset=nAgt.indexOf("Safari"))!=-1) {
            browserName = "Safari";
            browser = "safari";
            fullVersion = nAgt.substring(verOffset+7);
            if ((verOffset=nAgt.indexOf("Version"))!=-1)
                fullVersion = nAgt.substring(verOffset+8);
        }
        // In Firefox, the true version is after "Firefox"
        else if ((verOffset=nAgt.indexOf("Firefox"))!=-1) {
            browserName = "Firefox";
            browser = "firefox";
            fullVersion = nAgt.substring(verOffset+8);
        }
        // In most other browsers, "name/version" is at the end of userAgent
        else if ( (nameOffset=nAgt.lastIndexOf(' ')+1) < (verOffset=nAgt.lastIndexOf('/')) ) {
            browserName = nAgt.substring(nameOffset,verOffset);
            fullVersion = nAgt.substring(verOffset+1);
            if (browserName.toLowerCase()==browserName.toUpperCase()) {
                browserName = navigator.appName;
            }
        }
        // trim the fullVersion string at semicolon/space if present
        if ((ix=fullVersion.indexOf(";"))!=-1) {
            fullVersion=fullVersion.substring(0,ix);
        }
        if ((ix=fullVersion.indexOf(" "))!=-1) {
            fullVersion=fullVersion.substring(0,ix);
        }
        majorVersion = parseInt(''+fullVersion,10);
        if (isNaN(majorVersion)) {
            fullVersion  = ''+parseFloat(navigator.appVersion);
            majorVersion = parseInt(navigator.appVersion,10);
        }

        return {
            browser: browser,
            version: majorVersion,
            browserName: browserName
        };
    };

    /*
     * LocalStorageValidator
     */
    var LocalStorageValidator = function LocalStorageValidator() {};
    LocalStorageValidator.prototype.constructor = LocalStorageValidator;
    LocalStorageValidator.prototype.validate = function() {
        var validateResult = {
            isValid: false,
            message: ''
        };
        try {
            // First check for objects to exist:
            //		IE and Firefox will remove these global objects entirely if disabled
            //		Chrome will keep these objects even if storage is disabled
            if (localStorage && sessionStorage) {
                // Second, for Chrome, make an attempt to set an item which will result
                //		in an error if it is disabled
                localStorage.setItem('vlc.test-item', '.');
                localStorage.removeItem('vlc.test-item');
                validateResult.isValid = true; //
            }
        } catch (e) { /* swallow - it choked on set because it is disabled */ };

        if (!validateResult.isValid) {
            validateResult.message = 'Please enable local and session storage in order to use Valloc.com.';
        }

        return validateResult;
    };

    var validators = new Array();
    // order matters here
    validators.push(new BrowserCompatibilityValidator());
    validators.push(new LocalStorageValidator());

    // immediately validate page/environment conditions
    var i = 0;
    var invalidMessages = [];
    for (; i < validators.length; i++) { // old school loop since we don't know version yet
        var validator = validators[i];
        var validateResult = validator.validate();
        if (!validateResult.isValid) {
            invalidMessages.push(validateResult.message);
        }
    }
    var isInvalidBrowser = invalidMessages.length > 0;
    if (isInvalidBrowser) {
        var displayHtml = '';
        for (i = 0; i < invalidMessages.length; i++) {
            displayHtml += invalidMessages[i] + '<br />';
        }
        // if not already on invalidbrowser page, then re-locate/GET to invalid browser page
        if (window.location.pathname.indexOf('invalidbrowser') == -1) {
            window.location = '/invalidbrowser';
        }
        return; // escape out of all initialization
    }
})();
