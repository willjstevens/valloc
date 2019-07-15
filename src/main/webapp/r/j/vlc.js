"use strict";


(function() {

    // setup Valloc namespaces and local vars
    var com = window.com || new Object();
    com.valloc = com.valloc || new Object();
    com.valloc.domain = new Object();
    com.valloc.library = new Object();
    com.valloc.service = new Object();
    com.valloc.vallocController = new Object();
    com.valloc.vallocDirective = new Object();
    var domain = com.valloc.domain;
    var lib = com.valloc.library;
    var service = com.valloc.service;
    var vallocController = com.valloc.vallocController;
    var vallocDirective = com.valloc.vallocDirective;

    /*
     * Config
     */
    var Config = function Config(category, key, value, description) {
        Object.defineProperty(this, 'category', {
            get: function () { return category; }
        });
        Object.defineProperty(this, 'key', {
            get: function () { return key; }
        });
        Object.defineProperty(this, 'value', {
            get: function () { return value; }
        });
        Object.defineProperty(this, 'description', {
            get: function () { return description; }
        });
    };
    Config.prototype.constructor = Config;
    domain.Config = Config;
    Config.prototype = {
        hasDescription: function () {
            return this.description !== undefined;
        }
    };
    Config.toDomainObject = function (plainObject) {
        return new Config(plainObject.category, plainObject.key, plainObject.value, plainObject.description);
    };

    /*
     * PersistentUserMessage
     */
    var UserMessage = function UserMessage(key, message, type, code) {
        Object.defineProperty(this, 'key', {
            get: function () { return key; }
        });
        Object.defineProperty(this, 'message', {
            get: function () { return message; }
        });
        Object.defineProperty(this, 'type', {
            get: function () { return type; }
        });
        Object.defineProperty(this, 'code', {
            get: function () { return code; }
        });
    };
    UserMessage.prototype.constructor = UserMessage;
    domain.UserMessage = UserMessage;
    UserMessage.toDomainObject = function (plainObject) {
        return new UserMessage(plainObject.key, plainObject.message, plainObject.type, plainObject.code);
    };

    /*
     * Spec
     */
    var Spec = function Spec(key, value) {
        Object.defineProperty(this, 'key', {
            get: function () { return key; }
        });
        Object.defineProperty(this, 'value', {
            get: function () { return value; }
        });
    };
    Spec.prototype.constructor = Spec;
    domain.Spec = Spec;
    Spec.toDomainObject = function (plainObject) {
        return new Spec(plainObject.key, plainObject.value);
    };

    /*
     * Account: A user's data relevant to the account form data; not necessarily the user data
     */
    var Account = function Account(username, email, firstName, lastName, gender, birthdate, password, passwordConfirmation, passwordQuestion, passwordAnswer) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.passwordQuestion = passwordQuestion;
        this.passwordAnswer = passwordAnswer;
    };
    Account.prototype.constructor = Account;
    domain.Account = Account;
    Account.toDomainObject = function (plainObject) {
        return new Account(plainObject.username,
            plainObject.email,
            plainObject.firstName,
            plainObject.lastName,
            plainObject.gender,
            plainObject.birthdate,
            plainObject.password,
            plainObject.passwordConfirmation,
            plainObject.passwordQuestion,
            plainObject.passwordAnswer);
    };

    /*
     * Column
     */
    var Column = function Column(sections) {
        Object.defineProperty(this, 'sections', {
            get: function () { return sections; }
        });
    };
    Column.prototype.constructor = Column;
    domain.Column = Column;
    Column.toDomainObject = function (plainObject) {
        var sectionDomainObjects = new Array();
        plainObject.sections.forEach(function (sectionPlainObject){
            var sectionDomainObject = Section.toDomainObject(sectionPlainObject);
            sectionDomainObjects.push(sectionDomainObject);
        });
        return new Column(sectionDomainObjects);
    };

    /*
     * Section
     */
    var Section = function Section(name, note, links) {
        Object.defineProperty(this, 'name', {
            get: function () { return name; }
        });
        Object.defineProperty(this, 'note', {
            get: function () { return note; }
        });
        Object.defineProperty(this, 'links', {
            get: function () { return links; }
        });
    };
    Section.prototype.constructor = Column;
    domain.Section = Section;
    Section.toDomainObject = function (plainObject) {
        var linkDomainObjects = new Array();
        plainObject.links.forEach(function (linkPlainObject){
            var linkDomainObject = Link.toDomainObject(linkPlainObject);
            linkDomainObjects.push(linkDomainObject);
        });
        return new Section(plainObject.name, plainObject.note, linkDomainObjects);
    };

    /*
     * Link
     */
    var Link = function Link(name, url, note) {
        Object.defineProperty(this, 'name', {
            get: function () { return name; }
        });
        Object.defineProperty(this, 'url', {
            get: function () { return url; }
        });
        Object.defineProperty(this, 'note', {
            get: function () { return note; }
        });
    };
    Link.prototype.constructor = Link;
    domain.Link = Link;
    Link.toDomainObject = function (plainObject) {
        return new Link(plainObject.name, plainObject.url, plainObject.note);
    };


    /*
     * Util
     */
    var Util = function Util() {};
    Util.prototype.constructor = Util;
    lib.Util = Util;
    /**
     * Util.subclass
     *
     * @param {Object} constructor
     * @param {Object} parentClass
     */
    Util.subclass = function (constructor, parentClass) {
        constructor.prototype = Object.create(parentClass.prototype);
        constructor.prototype.constructor = constructor;
    };
    Util.msg = function (key) {
        return Util.getUserMessageString(key);
    };
    Util.customMsg = function (key) {
        return Util.getCustomizedUserMessageString.apply(null, arguments);
    };
    Util.getUserMessageString = function (key) {
        var database = com.valloc.Database;
        return database.selectUserMessageString(key);
    };
    Util.getUserMessageObject = function (key) {
        var database = com.valloc.Database;
        return database.selectUserMessageObject(key);
    };
    Util.getCustomizedUserMessageString = function (key) {
        // verify minimum arguments: the first is the key, the additional are they replacement strings
        if (arguments.length <= 1) {
            throw new Error("Not enough arguments found for building a customized message. Found this many arguments: " + arguments.length)
        }
        var customizedString = Util.getUserMessageString(key);
        var i = 1; // skip over 0 which is the key
        for (; i < arguments.length; i++) {
            var replacementString = '{' + (i-1) + '}'; // replacement string indexes are zero based: "{0}.."
            customizedString = customizedString.replace(replacementString, arguments[i]);
        }
        return customizedString;
    };
    Util.shallowCopy = function (originalObject) {
        var newObject = {};
        for (var key in originalObject) {
            if (originalObject.hasOwnProperty(key)) {
                newObject[key] = originalObject[key];
            }
        }
        return newObject;
    };

    /**
     * LocalStorage wrapper
     *
     * Storage map under domain name "valloc.com":
     *
     *  - "vlc.appData", JSON Value:
     *  		- publishDate (string)
     *  		- configs (array)
     *  		- specs (array)
     *  		- userMessages (array)
     *
     *  - "vlc.userData", JSON Value:
     *  		- promos...
     *
     */
    var LocalStorage = function LocalStorage() {};
    LocalStorage.prototype.constructor = LocalStorage;
    lib.LocalStorage = LocalStorage;
    LocalStorage.PREFIX = "vlc.";
    LocalStorage.APP_DATA_KEY = LocalStorage.PREFIX + "appData";
    LocalStorage.USER_DATA_KEY = LocalStorage.PREFIX + "userData";
    LocalStorage.prototype.setAppDataObject = function (appDataObject) {
        var appDataJson = JSON.stringify(appDataObject);
        localStorage.setItem(LocalStorage.APP_DATA_KEY, appDataJson);
    };
    LocalStorage.prototype.getAppDataObject = function () {
        var appData = undefined;
        var appDataJson = localStorage.getItem(LocalStorage.APP_DATA_KEY);
        if (appDataJson) {
            appData = angular.fromJson(appDataJson);
        }
        return appData;
    };

    /*
     * Ajax
     */
    var Ajax = function Ajax(method, path) {
        this.path = path;
        this.settings = {
            type: method,
            url: path
        };

        var globalSettings = Ajax.GLOBAL_SETTINGS;
        for (var globalSetting in globalSettings) {
            this.settings[globalSetting] = globalSettings[globalSetting];
        };
    };
    lib.Ajax = Ajax;
    Ajax.prototype.constructor = Ajax;
    Ajax.GLOBAL_SETTINGS = {
        statusCode: {
            400: function () {
                console.log("Bad request.");
            },
            404: function () {
                console.log("Page not found.");
            },
            500: function () {
                console.log("Server error.");
            }
        }
    };
    Ajax.newGet = function (path) {
        return new Ajax('GET', path);
    };
    Ajax.newPost = function (path) {
        return new Ajax('POST', path);
    };
    Ajax.newTextGet = function (path) {
        var ajax = Ajax.newGet(path);
        ajax.settings.contentType = 'text/plain; charset=utf-8'; // request content type
        ajax.settings.dataType = 'text'; // response type
        return ajax;
    };
    Ajax.newHtmlGet = function (path) {
        var ajax = Ajax.newGet(path);
        ajax.settings.contentType = 'text/plain; charset=utf-8'; // request content type
        ajax.settings.dataType = 'html'; // response type
        return ajax;
    };
    Ajax.newJsonGet = function (path) {
        var ajax = Ajax.newGet(path);
        ajax.settings.dataType = 'json'; // always receiving JSON back on form post for this app
        return ajax;
    };
    Ajax.newJsonPost = function (path) {
        var ajax = Ajax.newPost(path);
        ajax.settings.dataType = 'json'; // always receiving JSON back on form post for this app
        return ajax;
    };
    Ajax.newFormPost = function (path) {
        var ajax = Ajax.newPost(path);
        ajax.settings.contentType = 'application/x-www-form-urlencoded; charset=utf-8'; // request content type
        return ajax;
    };
    Ajax.newJsonPostAndJsonResponse = function (path) {
        var ajax = Ajax.newPost(path);
        ajax.settings.contentType = 'application/json; charset=utf-8'; // request content type
        ajax.settings.dataType = 'json'; // always receiving JSON back on JSON post for this app
        return ajax;
    };
    Ajax.prototype.synchronous = function () {
        this.settings.async = false;
    };
    Ajax.prototype.addParam = function (name, value) {
        if (!this.paramData) {
            this.paramData = {};
        }
        this.paramData[name] = value;
    };
    Ajax.prototype.setJsonRequestObject = function (object) {
//    	this.settings.data = JSON.stringify(object);
        this.settings.data = angular.toJson(object);
    };
    Ajax.prototype.setDoneCallback = function (callback, context) {
        this.doneCallback = callback;
        if (context) {
            this.callbackContext = context;
        }
    };
    Ajax.prototype.setFailCallback = function (callback, overrideNative) {
        this.failCallback = callback;
        if (overrideNative) {
            this.overrideNativeFailCallback = true;
        }
    };
    Ajax.prototype.doneHandler = function (data) {
        if (this.doneCallback) {
            if (this.callbackContext) {
                this.doneCallback = this.doneCallback.bind(this.callbackContext);
            }
            this.doneCallback(data);
        }
    };
    Ajax.prototype.failHandler = function (jqXHR, textStatus, errorThrown) {
        var errorObj = JSON.parse(jqXHR.responseText);
        if (!this.overrideNativeFailCallback) {
            var msg = errorObj.title + '\n' + errorObj.message;
            console.log(msg);
        }
        if (this.failCallback) {
            this.failCallback(textStatus);
        }
    };
    Ajax.prototype.send = function () {
        if (this.paramData) {
            // to be convereted by JQuery into query string if GET or POST body if form
            this.settings.data = this.paramData;
        }

        var promise = $.ajax(this.settings);
        promise.done(this.doneHandler.bind(this));
        promise.fail(this.failHandler.bind(this));
    };

    /*
     * KeyMap
     */
    var KeyMap = function KeyMap() {
        this.keys = new Array();
        this.objects = new Array();
        this.isLocked = false;
    };
    KeyMap.prototype.constructor = KeyMap;
    lib.KeyMap = KeyMap;
    KeyMap.prototype.lock = function () {
        if (this.isLocked) {
            var msg = 'Cannot write - the map is locked.';
            console.log(msg);
            throw new Error(msg);
        }
        this.isLocked = true;
    };
    KeyMap.prototype.unlock = function () {
        this.isLocked = false;
    };
    KeyMap.prototype.get = function (key) {
        var retval = undefined, i = 0;
        this.lock();
        for (; i < this.keys.length; i++) {
            if (this.keys[i] === key) {
                retval = this.objects[i];
                break;
            }
        }
        this.unlock();
        return retval;
    };
    KeyMap.prototype.put = function (key, object) {
        this.lock();
        this.keys.push(key);
        this.objects.push(object);
        this.unlock();
    };
    KeyMap.prototype.remove = function (removeKey) {
        this.lock();
        var newKeys = new Array();
        var newObjects = new Array();
        var i = 0;
        // transfer values to new arrays
        for (; i < this.keys.length; i++) {
            var key = this.keys[i];
            if (key !== removeKey) {
                newKeys.push(key);
                newObjects.push(this.objects[i]);
            }
        }
        // clean out old arrays and values
        for (i=0; i < this.keys.length; i++) {
            delete this.key[i];
            delete this.objects[i];
        }
        // delete to release then re-assign
        delete this.keys;
        delete this.objects;
        this.keys = newKeys;
        this.objects = newObjects;
        this.unlock();
    };
    KeyMap.prototype.repetitiveRemove = function (removeKey) {
        this.lock();
        var i = 0;
        for (; i < this.keys.length; i++) {
            if (this.keys[i] === removeKey) {
                delete this.keys[i];
                delete this.objects[i];
                break;
            }
        }
        this.unlock();
    };
    KeyMap.prototype.repetitiveRemoveComplete = function () {
        this.lock();
        var newKeys = new Array();
        var newObjects = new Array();
        var i = 0;
        // transfer values to new arrays
        for (; i < this.keys.length; i++) {
            var key = this.keys[i];
            var object = this.objects[i];
            if (key && object) {
                newKeys.push(key);
                newObjects.push(object);
                delete this.key[i];
                delete this.objects[i];
            }
        }
        // delete to release then re-assign
        delete this.keys;
        delete this.objects;
        this.keys = newKeys;
        this.objects = newObjects;
        this.unlock();
    };

    /*
     * Below are common angular-related functions
     */
    var AngularFormSupport = function AngularFormSupport($scope) {
        this.$scope = $scope;
    };
    AngularFormSupport.prototype.constructor = AngularFormSupport;
    lib.AngularFormSupport = AngularFormSupport;
    AngularFormSupport.ERROR_SECTION_CLASSES = 'errorSection  ui-state-error';
    AngularFormSupport.hasError = function (obj) {
        var hasError = false;
        // this is how Angular defines error; if it has an error, the object is present and
        //		with an array loaded with an error object; if no error then the object is
        //		either undefined or reset to false
        if (obj && obj instanceof Array) {
            hasError = true;
        }
        return hasError;
    };
    AngularFormSupport.prototype.cse = function (code) {

        var $scope = this.$scope; // this, here, is executing from inside the angular $scope object
        var hasServerError = false;
        if ($scope.serverErrorMessages) {
            hasServerError = $scope.serverErrorMessages.some(function(e) {
                var candidateCode = e.code;
                if (code === candidateCode) {
                    return true;
                }
            });
        }
        return hasServerError;
    };
    AngularFormSupport.prototype.displayErrorSection = function () {
        var errorSectionClass = ''; // default to no class
        if (arguments.length == 0) {
            throw new Error("At least 1 form object must be passed to the function.");
        }

        var i=0, displayErrorSection;
        for (; i < arguments.length; i++) {
            displayErrorSection = this.formContainsErrors(arguments[i]);
            if (displayErrorSection === true) {
                break; // we have error so quit
            }
        }

        if (displayErrorSection) {
            errorSectionClass = AngularFormSupport.ERROR_SECTION_CLASSES;
        }

        return errorSectionClass;
    };
    AngularFormSupport.prototype.formContainsErrors = function (formObj) {
        var formContainsErrors = false;

        var $scope = this.$scope; // this, here, is executing from inside the angular $scope object
        var form = formObj;
        var error = form.$error;
        function hasError(obj) {
            return AngularFormSupport.hasError(obj);
        }
        if (hasError(error.email)) {
            formContainsErrors = true;
        }
        if (hasError(error.pattern)) {
            formContainsErrors = true;
        }
        if (hasError(error.match)) {
            // check first for password / confirmation password mismatching
            if (form.password.$dirty || form.passwordConfirmation.$dirty) {
                formContainsErrors = true;
            }
        }
        if ($scope.submitted && hasError(error.minlength)) {
            formContainsErrors = true;
        }
        if ($scope.submitted && hasError(error.maxlength)) {
            formContainsErrors = true;
        }
        if (hasError(error.required)) {
            if ($scope.submitted) {
                formContainsErrors = true;
            } else { // loop through to see if any required fields are dirty and set
                error.required.some(function(e) {
                    if (e.$dirty) {
                        formContainsErrors = true;
                        return true;
                    }
                });
            }
        }
        if ($scope.serverErrorMessages) {
            formContainsErrors = true;
        }

        return formContainsErrors;
    };

    AngularFormSupport.prototype.displayErrorSectionNEW = function () {
        var errorSectionClass = ''; // default to no class
        if (this.formContainsViolations()) {
            errorSectionClass = AngularFormSupport.ERROR_SECTION_CLASSES;
        }
        return errorSectionClass;
    };
    AngularFormSupport.prototype.formContainsViolations = function () {
        var formContainsErrors = false;
        for (var propName in this.$scope.violations) {
            var propVal = this.$scope.violations[propName];
            var isBoolean = typeof propVal === 'boolean';
            if (isBoolean && isBoolean === true && propVal === true) {
                formContainsErrors = true;
                break;
            }
        }
        return formContainsErrors;
    };

    var Database = function Database() {};
    Database.prototype.constructor = Database;
    com.valloc.Database = Database;
    Database.DATA = {
        configs: 		new lib.KeyMap(),
        userMessages: 	new lib.KeyMap(),
        specs:			new lib.KeyMap()
    };
    var database = Database;
    var data = database.DATA;

    /*
     * PersistentUserMessage operations
     */
    database.selectUserMessageObject = function (key) {
        return data.userMessages.get(key);
    };
    database.selectUserMessageString = function (key) {
        return database.selectUserMessageObject(key).message;
    };
    database.insertUserMessage = function (userMessage) {
        data.userMessages.put(userMessage.key, userMessage);
    };

    /*
     * Config operations
     */
    database.selectConfigObject = function (key) {
        return data.configs.get(key);
    };
    database.selectConfigValue = function (key) {
        return database.selectConfigObject(key).value;
    };
    database.insertConfig = function (config) {
        data.configs.put(config.key, config);
    };

    /*
     * Spec operations
     */
    database.selectSpecObject = function (key) {
        return data.specs.get(key);
    };
    database.selectSpecValue = function (key) {
        return database.selectSpecObject(key).value;
    };
    database.insertSpec = function (spec) {
        data.specs.put(spec.key, spec);
    };


    var ApplicationService = function ApplicationService() {
        this.localStorage = new lib.LocalStorage();
    };
    ApplicationService.prototype.constructor = ApplicationService;
    service.ApplicationService = ApplicationService;
    ApplicationService.prototype.loadAppData = function () {
        var appData = this.localStorage.getAppDataObject();
        var ajax = lib.Ajax.newJsonGet('/a/loadAppData');
        var publishDate = 'UNINITIALIZED';
        if (appData && appData.publishDate) {
            publishDate = appData.publishDate;
            loadFromStorage();
            // Note that local storage is found, then good, but still issue async request for update
        } else {
            // if no app data then this is a first-time load, and we need to block while retrieving
            ajax.synchronous();
        }
        ajax.addParam('publishDate', publishDate);
        var loadFromServer = function (responseObj) {
            if (responseObj) { // only present if newly updated from the server
                loadIntoDatabase(responseObj);
                // persist into local storage
                this.localStorage.setAppDataObject(responseObj);
            }
        };
        ajax.setDoneCallback(loadFromServer, this);
        ajax.send();
        function loadFromStorage() {
            loadIntoDatabase(appData);
        }
        function loadIntoDatabase(appData) {
            // load into database
            var configs = appData.configs;
            configs.forEach(function (config) {
                database.insertConfig(domain.Config.toDomainObject(config));
            });
            var userMessages = appData.userMessages;
            userMessages.forEach(function (userMessage) {
                database.insertUserMessage(domain.UserMessage.toDomainObject(userMessage));
            });
            var specs = appData.specs;
            for (var propName in specs) {
                var propVal = specs[propName];
                database.insertSpec(new domain.Spec(propName, propVal));
            }
        }
    };
    ApplicationService.prototype.saveFeedback = function (formParams, callback) {
        var ajax = lib.Ajax.newJsonPost('/a/feedback');
        for (var name in formParams) {
            var value = formParams[name];
            ajax.addParam(name, value);
        }
        var doneCallback = function (response) {
            callback(response);
        };
        ajax.setDoneCallback(doneCallback, this);
        ajax.send();
    };
    ApplicationService.prototype.saveContactMessage = function (formParams, callback) {
        var ajax = lib.Ajax.newJsonPost('/a/contactus');
        for (var name in formParams) {
            var value = formParams[name];
            ajax.addParam(name, value);
        }
        var doneCallback = function (response) {
            callback(response);
        };
        ajax.setDoneCallback(doneCallback, this);
        ajax.send();
    };
    ApplicationService.prototype.pinLink = function (formParams, callback) {
        var ajax = lib.Ajax.newJsonPost('/a/pinlink');
        for (var name in formParams) {
            var value = formParams[name];
            ajax.addParam(name, value);
        }
        var doneCallback = function (response) {
            callback(response);
        };
        ajax.setDoneCallback(doneCallback, this);
        ajax.send();
    };

    var AccountService = function AccountService() {};
    AccountService.prototype.constructor = AccountService;
    service.AccountService = AccountService;
    AccountService.prototype.create = function (formParams, callback) {
        var ajax = lib.Ajax.newJsonPost('/a/account/create');
        for (var name in formParams) {
            var value = formParams[name];
            ajax.addParam(name, value);
        }
        var doneCallback = function (response) {
            callback(response);
        };
        ajax.setDoneCallback(doneCallback, this);
        ajax.send();
    };
    AccountService.prototype.edit = function (formParams, callback) {
        var ajax = lib.Ajax.newJsonPost('/a/account/edit');
        for (var name in formParams) {
            var value = formParams[name];
            ajax.addParam(name, value);
        }
        var doneCallback = function (response) {
            callback(response);
        };
        ajax.setDoneCallback(doneCallback, this);
        ajax.send();
    };
    AccountService.prototype.login = function (username, password, callback) {
        var ajax = lib.Ajax.newFormPost('/a/login');
        ajax.addParam('username', username);
        ajax.addParam('password', password);
        ajax.setDoneCallback(callback);
        ajax.send();
    };

    var DashboardService = function DashboardService() {};
    DashboardService.prototype.constructor = DashboardService;
    service.DashboardService = DashboardService;

    var DesignerService = function DesignerService() {};
    DesignerService.prototype.constructor = DesignerService;
    service.DesignerService = DesignerService;
    DesignerService.prototype.savePage = function (designerData, callback) {
        var postPath;
        if (designerData.isOwnerEdit) {
            if (designerData.isNewPage) {
                postPath = '/a/page/new';
            } else {
                postPath = '/a/page/edit';
            }
        } else if (designerData.isGuestEdit) {
            var pageData = designerData.pageData;
            postPath = '/a/page/guestedit/' + pageData.username + '/' + pageData.path;
        }

        // here account info will be fetched server-side by the user's established session
        var ajax = lib.Ajax.newJsonPostAndJsonResponse(postPath);
        ajax.setJsonRequestObject(designerData);
        var doneCallback = function (response) {
            callback(response);
        };
        ajax.setDoneCallback(doneCallback, this);
        ajax.send();
    };
    DesignerService.prototype.deletePage = function (path, callback) {
        var postPath = '/a/page/remove/' + path;
        // here account info will be fetched server-side by the user's established session
        var ajax = lib.Ajax.newJsonPost(postPath);
        var doneCallback = function (response) {
            callback(response);
        };
        ajax.setDoneCallback(doneCallback, this);
        ajax.send();
    };
    DesignerService.prototype.addGuest = function (username, callback) {
        var postPath = '/a/searchForPageGuest';
        // here account info will be fetched server-side by the user's established session
        var ajax = lib.Ajax.newJsonPostAndJsonResponse(postPath);
        var pageGuestObject = {
            username: username
        };
        ajax.setJsonRequestObject(pageGuestObject);
        var doneCallback = function (response) {
            callback(response);
        };
        ajax.setDoneCallback(doneCallback, this);
        ajax.send();
    };




    var Util = lib.Util;

    var applicationService = new service.ApplicationService();
    applicationService.loadAppData();

    var vlcMod = angular.module('vlc', ['ui.bootstrap', 'ui.sortable']);
    // var vlcMod = angular.module('vlc', ['ui.bootstrap', 'ui.sortable', 'angular-carousel']);
    vlcMod.directive("vlcMatchTo", function() {
        return {
            require: "ngModel",
            scope: '=',
            link: function(scope, element, attributes, controller) {
                var thisFormElName = controller.$name; // could be 'password' or 'passwordConfirmation
                if (thisFormElName === 'password') {
                    controller.$parsers.push(function (thisValue) {
                        var passwordConfirmationController = scope.f.passwordConfirmation;
                        var bothMatch = thisValue === passwordConfirmationController.$viewValue;
                        var eitherDirty = controller.$dirty || passwordConfirmationController.$dirty;
                        var registeredAndMatched = (bothMatch && eitherDirty);
                        scope.f.password.$setValidity("match", registeredAndMatched);
                        scope.f.passwordConfirmation.$setValidity("match", registeredAndMatched);
                        return thisValue;
                    });
                } else {
                    controller.$parsers.push(function (thisValue) {
                        var passwordController = scope.f.password;
                        var bothMatch = thisValue === passwordController.$viewValue;
                        var eitherDirty = controller.$dirty || passwordController.$dirty;
                        var registeredAndMatched = (bothMatch && eitherDirty);
                        scope.f.passwordConfirmation.$setValidity("match", registeredAndMatched);
                        scope.f.password.$setValidity("match", registeredAndMatched);
                        return thisValue;
                    });
                }
            }
        };
    });

    /**
     *
     * @constructor
     */
    var Controller = function Controller() {};
    Controller.prototype.constructor = Controller;
    Controller.prototype.getPageLoadData = function () {
        return pld;
    };
    Controller.prototype.setScope = function (scope) {
        Object.defineProperty(this, 'scope', {
            get: function () { return scope; }
        });
        scope.controller = this;
        scope.alerts = this.alerts = [];
        scope.msg = this.msg;
        scope.customMsg = this.customMsg;
        // load formSupport object
        scope.formSupport = new lib.AngularFormSupport(scope);
        // scope functions
        scope.addAlert = this.addAlert;
        scope.closeAlert = this.closeAlert;
        scope.clearAlerts = this.clearAlerts;
    };
    Controller.prototype.msg = function (key) {
        return Util.msg(key);
    };
    Controller.prototype.customMsg = function (key) {
        return Util.customMsg.apply(null, arguments);
    };
    Controller.prototype.addAlert = function(type, message) {
        this.alerts.push({type: type, msg: message});
    };
    Controller.prototype.closeAlert = function(index) {
        this.alerts.splice(index, 1);
    };
    Controller.prototype.clearAlerts = function() {
        this.alerts.splice(0, this.alerts.length);
    };
    Controller.prototype.encodePageData = function (pageData) {
        pageData.columns.forEach(function (column) {
            column.sections.forEach(function (section) {
                section.name = encodeURIComponent(section.name);
                section.links.forEach(function (link) {
                    link.name = encodeURIComponent(link.name);
                    link.url = encodeURIComponent(link.url);
                });
            });
        });
    };
    Controller.prototype.decodePageData = function (pageData) {
        pageData.columns.forEach(function (column) {
            column.sections.forEach(function (section) {
                section.name = decodeURI(section.name);
                section.links.forEach(function (link) {
                    link.name = decodeURI(link.name);
                    link.url = decodeURIComponent(link.url);
                });
            });
        });
    };

    vallocController.AccountController = function ($scope) {
        this.setScope($scope);
        var scope = this.scope;
        this.accountService = new service.AccountService();
        var account = this.getPageLoadData();
        // now setup scope with account data
        if (!account) {
            // account is empty so this is a new/create
            this.isAccountNew = true;
            scope.isAccountNew = true;
            // set defaults:
            account = {};
//            account.genderDefaultSelected = true;
        } else {
            // if account is available and populated then we are in edit mode
            this.isAccountEdit = true;
            scope.isAccountEdit = true;
            // now setup model variables
//            switch (account.gender) {
//                case 'male': 	account.genderMaleSelected = true;		break;
//                case 'female': 	account.genderFemaleSelected = true;	break;
//                default: throw new Error('Unexpected gender option for edit.');
//            }
            account.inAgreement = true;
        }
        scope.account = account;
        scope.isValidAndSubmitted = false;
        // common model setup
//        $scope.genderList = [
//            {label: Util.msg('account_gender_male'), value: 'male'},
//            {label: Util.msg('account_gender_female'), value: 'female'}
//        ];
        // functions exposed directly on the scope
        scope.saveAccount = this.saveAccount;
    };
    vallocController.AccountController.prototype = Controller.prototype;
    vallocController.AccountController.prototype.constructor = vallocController.AccountController;
    vallocController.AccountController.$inject = ['$scope'];
    vallocController.AccountController.prototype.saveAccount = function () {
        var scope = this;
        scope.submitted = true;

        this.controller.loadAutofillValues(scope);

        var required = scope.f.$error.required;
        if (required && required instanceof Array) {
            required.forEach(function(e) {
                e.$dirty = true;
            });
        }

        var callback = function (response) {
            if (response.isSuccess) {
                var controller = scope.controller;
                if (controller.isAccountNew) {
                    window.location = '/account/create/complete';
                } else if (controller.isAccountEdit) {
                    controller.addAlert('success', response.userMessage.message);
                    scope.$apply();
                }
            } else {
                scope.$apply(function() {
                    scope.serverErrorMessages = response.supportingUserMessages;
                    scope.serverErrors = {};
                    response.supportingUserMessages.forEach(function (e) {
                        scope.serverErrors[e.key] = e.message;
                    });
                });
                // now that servers errors where applied to the model and style this property
                //		can be removed from the scope
                delete scope.serverErrorMessages;
                scope.isValidAndSubmitted = false;
            }
        };

        if (!scope.formSupport.formContainsErrors(scope.f)) {
            if (this.controller.isAccountNew) {
                scope.isValidAndSubmitted = true;
                this.controller.accountService.create(scope.account, callback);
            } else if (this.controller.isAccountEdit) {
                this.controller.accountService.edit(scope.account, callback);
            }
        }
    };
    vallocController.AccountController.prototype.loadAutofillValues = function (scope) {
        var account = scope.account;
        var form = scope.f;

        function modelHasNoValue(modelValue) {
            return !modelValue || modelValue == null;
        }

        function inputHasValue(inputValue) {
            var hasValue = false;
            if (inputValue != null) {
                inputValue = inputValue.trim();
                if (inputValue.length > 0) {
                    hasValue = true;
                }
            }
            return hasValue;
        }

        var email = $('#email').val();
        if (modelHasNoValue(account.email) && inputHasValue(email)) {
            form.email.$setViewValue(email);
        }
        var username = $('#username').val();
        if (modelHasNoValue(account.username) && inputHasValue(username)) {
            form.username.$setViewValue(username);
        }
        var firstName = $('#firstName').val();
        if (modelHasNoValue(account.firstName) && inputHasValue(firstName)) {
            form.firstName.$setViewValue(firstName);
        }
        var lastName = $('#lastName').val();
        if (modelHasNoValue(account.lastName) && inputHasValue(lastName)) {
            form.lastName.$setViewValue(lastName);
        }
//        var birthdate = $('#birthdate').val();
//        if (modelHasNoValue(account.birthdate) && inputHasValue(birthdate)) {
//            form.birthdate.$setViewValue(birthdate);
//        }
        var password = $('#password').val();
        if (modelHasNoValue(account.password) && inputHasValue(password)) {
            form.password.$setViewValue(password);
        }
        // var passwordConfirmation = $('#passwordConfirmation').val();
        // if (modelHasNoValue(account.passwordConfirmation) && inputHasValue(passwordConfirmation)) {
        //     form.passwordConfirmation.$setViewValue(passwordConfirmation);
        // }
        // var passwordQuestion = $('#passwordQuestion').val();
        // if (modelHasNoValue(account.passwordQuestion) && inputHasValue(passwordQuestion)) {
        //     form.passwordQuestion.$setViewValue(passwordQuestion);
        // }
        // var passwordAnswer = $('#passwordAnswer').val();
        // if (modelHasNoValue(account.passwordAnswer) && inputHasValue(passwordAnswer)) {
        //     form.passwordAnswer.$setViewValue(passwordAnswer);
        // }
    };


    vallocController.BuilderController = function ($scope) {
        this.setScope($scope);
        var scope = this.scope;

        var mySwiper = new Swiper ('.swiper-container', {
            loop: true
        });

    };
    vallocController.BuilderController.prototype = Controller.prototype;
    vallocController.BuilderController.prototype.constructor = vallocController.BuilderController;
    vallocController.BuilderController.$inject = ['$scope'];


    vallocController.LoginController = function ($scope) {
        this.setScope($scope);
        this.accountService = new service.AccountService();
        var scope = this.scope;
        scope.username = null;
        scope.password = null;

        // functions exposed directly on the scope
        scope.login = this.login;
    };
    vallocController.LoginController.prototype = Controller.prototype;
    vallocController.LoginController.prototype.constructor = vallocController.LoginController;
    vallocController.LoginController.$inject = ['$scope'];
    vallocController.LoginController.prototype.login = function () {
        var scope = this;
        scope.submitted = true;

        /*
         * HACK for autocomplete in browsers not firing event so Angular loads browser autocomplete values
         *      into the Angular model
         * See:
         *  - https://groups.google.com/forum/#!topic/angular/6NlucSskQjY
         *  - https://github.com/angular/angular.js/issues/1460
         */
        if (scope.username == null && scope.password == null) {
            scope.username = $('#username').val();
            scope.password = $('#password').val();
            scope.f.username.$setViewValue(scope.username);
            scope.f.password.$setViewValue(scope.password);
        }

        var required = scope.f.$error.required;
        if (required && required instanceof Array) {
            required.forEach(function(e) {
                e.$dirty = true;
            });
        }

        var callback = function (response) {
            if (response.isSuccess) {
                window.location = response.targetUrl;
            } else {
                scope.$apply(function() {
                    scope.serverErrorMessages = response.supportingUserMessages;
                    scope.serverErrors = {};
                    response.supportingUserMessages.forEach(function (e) {
                        scope.serverErrors[e.key] = e.message;
                    });
                });
                // now that servers errors where applied to the model and style this property
                //		can be removed from the scope
                delete scope.serverErrorMessages;
            }
        };

        if (!scope.formSupport.formContainsErrors(scope.f)) {
            this.controller.accountService.login(scope.username, scope.password, callback);
        }
    };


    vallocController.DashboardController = function ($scope) {
        this.setScope($scope);
        var scope = this.scope;
        var pageLoadData = this.getPageLoadData();
        // variables
        scope.pages = pageLoadData.pages;
        scope.homePath = pageLoadData.homePath;
        scope.pages.forEach(function (e) {
            if (e.isHome === true) {
                scope.home = e;
            }
        });
        // functions
    };
    vallocController.DashboardController.prototype = Controller.prototype;
    vallocController.DashboardController.prototype.constructor = vallocController.DashboardController;
    vallocController.DashboardController.$inject = ['$scope'];

    /*
     * DESIGNER
     *
     */
    vallocController.DesignerController = function ($scope, $dialog) {
        this.setScope($scope);
        this.dialogService = $dialog;
        var scope = this.scope;
        scope.controller = this;
        scope.violations = new Object();
        scope.sections = new Array();
        scope.saveAlerts = new Array();
        scope.saveAlertData = new Object();
        scope.showSavePageBtn = true;

        var designerData = this.getPageLoadData();
        this.setDesignerAndPageData(designerData);
        // now add all sections to the object
        scope.pageData.columns.forEach(function (column) {
            column.sections.forEach(function (section) {
                var sectionsObject = {
                    label: section.name,
                    value: section.name
                };
                scope.sections.push(sectionsObject);
            });
        });

        // functions exposed directly on the scope
        scope.savePage = this.savePage;
        scope.createSection = this.createSection;
        scope.createLink = this.createLink;
        scope.deleteSection = this.deleteSection;
        scope.deleteLink = this.deleteLink;
        scope.deletePage = this.deletePage;
        scope.isLinkFormPartiallyPristine = this.isLinkFormPartiallyPristine;
        scope.isSectionFormPartiallyPristine = this.isSectionFormPartiallyPristine;
        scope.openPageGuestPopup = this.openPageGuestPopup;
        scope.closeSaveAlert = this.closeSaveAlert;

        // register watches
        scope.$watch('pageData.name', this.validatePageName);
        scope.$watch('pageData.path', this.validatePath);
        scope.$watch('newSectionName', this.validateCreateSectionSectionName);
        scope.$watch('linkSection', this.validateCreateLinkSection);
        scope.$watch('newLinkName', this.validateCreateLinkLinkText);
        scope.$watch('newLinkUrl', this.validateCreateLinkLinkUrl);

        this.loadRelayMessage(designerData.relayMessage, designerData.attributes);
    };
    vallocController.DesignerController.prototype = Controller.prototype;
    vallocController.DesignerController.prototype.constructor = vallocController.DesignerController;
    vallocController.DesignerController.$inject = ['$scope', '$dialog'];
    vallocController.DesignerController.prototype.savePage = function () {
        this.clearAlerts(); // clear all previous alerts
        var parent = this;
        var pageForm = parent.pageForm;
        this.showSavePageBtn = false;

        var required = pageForm.$error.required;
        if (required && required instanceof Array) {
            required.forEach(function(e) {
                e.$dirty = true;
                e.$setViewValue(e.$viewValue);
            });
        }

        var callback = function (response) {
            parent.showSavePageBtn = true;
            if (response.isSuccess) {
                if (response.targetUrl) {
                    // if there is a target URL then we were from a /page/new URL and need to GET on /page/edit/xyz
                    window.location = response.targetUrl;
                } else {
                    // now reset page data and relay message
                    parent.controller.setDesignerAndPageData(response.object);
                    parent.controller.loadRelayMessage(response.relayMessage, response.attributes);
                    parent.$apply();
                }
            } else {
                response.supportingUserMessages.forEach(function (userMessage) {
                    parent.addAlert('error', userMessage.message);
                    parent.$apply();
                });
            }
        };

        if (!parent.formSupport.formContainsErrors(pageForm)) {
            var designerService = new service.DesignerService();
            designerService.savePage(this.designerData, callback);
            this.designerData.isNewPage = false; // after first save set to false
            this.closeSaveAlert();
        } else {
            parent.formSupport.displayErrorSectionNEW();
        }
    };
    vallocController.DesignerController.prototype.closeSaveAlert = function () {
        this.saveAlerts.splice(0, 1);
    };
    vallocController.DesignerController.prototype.setDesignerAndPageData = function (designerData) {
        this.scope.designerData = this.designerData = designerData;
        this.scope.pageData = this.pageData = designerData.pageData;
    };
    vallocController.DesignerController.prototype.loadRelayMessage = function (relayMessage, attributes) {
        if (relayMessage) {
            if (relayMessage.code === 1670) {
                this.scope.saveAlertData.hrefUrl = attributes.hrefUrl;
                this.scope.saveAlertData.displayUrl = attributes.displayUrl;
            }
            this.scope.saveAlerts.push({type: 'success', msg: relayMessage.message});
            // cleanup
            this.designerData.relayMessage = undefined;
            this.designerData.attributes = undefined;
        }
    };
    vallocController.DesignerController.prototype.createSection = function () {
        var sectionName = this.newSectionName;
        // append first to bottom of section list in the link fieldset then onto the model (and hence first column)
        var sectionListObject = {
            label: sectionName,
            value: sectionName
        };
        this.sections.push(sectionListObject);
        var sectionModelObject = {
            name: sectionName,
            links: new Array()
        };
        this.pageData.columns[0].sections.push(sectionModelObject);

        // section cleanup and reset
        this.newSectionName = undefined;
        this.sectionForm.$setDirty(false);
        this.sectionForm.$setPristine(true);
    };
    vallocController.DesignerController.prototype.createLink = function () {
        var targetSectionName = this.linkSection;
        var linkName = this.newLinkName;
        var linkUrl = this.newLinkUrl;
        var linkNote = this.newLinkNote;

        this.pageData.columns.some(function (column) {
            var retval = column.sections.some(function (section) {
                if (section.name === targetSectionName) {
                    var linkObject = {
                        name: linkName,
                        url: linkUrl,
                        linkNotes: new Array()
                    };
                    if (linkNote) {
                        var noteObj = {
                            note: linkNote,
                            firstName: Util.msg('designer_linkNote_justAddedByYou') // for displaying on UI
                        };
                        linkObject.linkNotes.push(noteObj);
                    }
                    section.links.push(linkObject);
                    return true; // done if here so return out of some
                }
            });
            return retval; // done if here so return out of some()
        });

        // link cleanup and reset
        this.newLinkName = undefined;
        this.newLinkUrl = undefined;
        this.newLinkNote = undefined;
        this.linkSection = 0;
        this.linkForm.$setDirty(false);
        this.linkForm.$setPristine(true);
    };
    vallocController.DesignerController.prototype.deleteSection = function (targetSectionName) {
        var controller = this.$parent.controller;
        var msgbox = controller.dialogService.messageBox(controller.msg('designer_deleteSection_title'),
            controller.msg('designer_deleteSection_message'),
            [{label: controller.msg('button_okay'), result: true},
                {label: controller.msg('button_cancel'), result: false}]);
        var callback = function (buttonResult) {
            if(buttonResult === true) {
                var columns = controller.pageData.columns;
                // first delete actual list item
                columns.some(function (column) {
                    var removedSection = false;
                    var i = 0;
                    for (; i < column.sections.length; i++) {
                        var sectionObject = column.sections[i];
                        if (sectionObject.name === targetSectionName) {
                            column.sections.splice(i, 1);
                            removedSection = true;
                            break;
                        }
                    }
                    return removedSection;
                });
                // delete from sections dropdown
                var i = 0;
                var sections = this.scope.sections;
                for (; i < sections.length; i++) {
                    if (sections[i].label === targetSectionName) {
                        sections.splice(i, 1);
                        break;
                    }
                }
            }
        };

        msgbox.open().then(callback.bind(controller));
    };
    vallocController.DesignerController.prototype.deleteLink = function (link) {
        var controller = this.$parent.controller;

        var msgbox = controller.dialogService.messageBox(controller.msg('designer_deleteLink_title'),
            controller.msg('designer_deleteLink_message'),
            [{label: controller.msg('button_okay'), result: true},
                {label: controller.msg('button_cancel'), result: false}]);

        var targetSectionName = this.$parent.section.name;
        var targetLinkName = this.link.name;
        var callback = function (buttonResult) {
            if(buttonResult === true) {
                // first delete link item
                this.pageData.columns.some(function (column) {
                    var retval = column.sections.some(function (section) {
                        if (section.name === targetSectionName) {
                            var i = 0;
                            for (; i < section.links.length; i++) {
                                var linkObject = section.links[i];
                                if (linkObject.name === targetLinkName) {
                                    section.links.splice(i, 1);
                                    break;
                                }
                            }
                            return true; // done if here so return out of some
                        }
                    });
                    return retval; // done if here so return out of some()
                });
            }
        };

        msgbox.open().then(callback.bind(controller));
    };
    vallocController.DesignerController.prototype.deletePage = function () {
        var controller = this.controller;
        var msgbox = controller.dialogService.messageBox(controller.msg('designer_deletePage_dialogTitle'),
            controller.msg('designer_deletePage_message'),
                [{label: controller.msg('button_okay'), result: true},
                {label: controller.msg('button_cancel'), result: false}]);
        var callback = function (buttonResult) {
            if(buttonResult === true) {

                var serviceCallback = function (response) {
                    if (response.isSuccess) {
                        window.location = response.targetUrl;
                    } else {
                        response.supportingUserMessages.forEach(function (userMessage) {
                            parent.addAlert('error', userMessage.message);
                            parent.$apply();
                        });
                    }
                };

                var designerService = new service.DesignerService();
                designerService.deletePage(controller.pageData.path, serviceCallback);


            }
        };

        msgbox.open().then(callback.bind(controller));
    };

    vallocController.DesignerController.prototype.openPageGuestPopup = function () {
        var parentScope = this;
        var controllerWrapper = function () {
            return parentScope;
        };
        var dialog = this.controller.dialogService.dialog({
            dialogFade: true,
            resolve: {designerScope: controllerWrapper},
            controller: 'PageGuestController',
            templateUrl: '/page/pageGuests/new'
        });

        dialog.open();
    };
    vallocController.DesignerController.prototype.validatePageName = function (newValue, oldValue, scope) {
        var nameFormField = scope.pageForm.pageName;
        scope.violations.pageNameRequired = nameFormField.$dirty && nameFormField.$error.required;
        scope.violations.pageNameLength =
            nameFormField.$dirty && (nameFormField.$error.minlength || nameFormField.$error.maxlength);
    };
    vallocController.DesignerController.prototype.validatePath = function (newValue, oldValue, scope) {
        var nameFormField = scope.pageForm.path;
        scope.violations.pathRequired = nameFormField.$dirty && nameFormField.$error.required;
        scope.violations.pathLength =
            nameFormField.$dirty && (nameFormField.$error.minlength || nameFormField.$error.maxlength);
        scope.violations.pathFormat = nameFormField.$dirty && nameFormField.$error.pattern;
    };
    vallocController.DesignerController.prototype.validateCreateSectionSectionName = function (newValue, oldValue, scope) {
        var nameFormField = scope.sectionForm.newSectionName;
        scope.violations.sectionNameRequired = nameFormField.$dirty && nameFormField.$error.required;
        scope.violations.sectionNameLength =
            nameFormField.$dirty && (nameFormField.$error.minlength || nameFormField.$error.maxlength);
    };
    vallocController.DesignerController.prototype.isSectionFormPartiallyPristine = function () {
        return this.sectionForm.newSectionName.$pristine;
    };
    vallocController.DesignerController.prototype.validateCreateLinkLinkText = function (newValue, oldValue, scope) {
        var nameFormField = scope.linkForm.newLinkName;
        scope.violations.linkNameRequired = nameFormField.$dirty && nameFormField.$error.required;
        scope.violations.linkNameLength =
            nameFormField.$dirty && (nameFormField.$error.minlength || nameFormField.$error.maxlength);
    };
    vallocController.DesignerController.prototype.validateCreateLinkLinkUrl = function (newValue, oldValue, scope) {
        var nameFormField = scope.linkForm.newLinkUrl;
        scope.violations.linkUrlRequired = nameFormField.$dirty && nameFormField.$error.required;
        scope.violations.linkUrlLength =
            nameFormField.$dirty && (nameFormField.$error.minlength || nameFormField.$error.maxlength);
    };
    vallocController.DesignerController.prototype.validateCreateLinkSection = function (newValue, oldValue, scope) {
        var nameFormField = scope.linkForm.linkSection;
        scope.violations.linkSectionRequired = nameFormField.$dirty && nameFormField.$error.required;
    };
    vallocController.DesignerController.prototype.isLinkFormPartiallyPristine = function () {
        return this.linkForm.newLinkName.$pristine ||
            this.linkForm.newLinkUrl.$pristine ||
            this.linkForm.linkSection.$pristine;
    };

    vallocController.PageGuestController = function ($scope, dialog, designerScope) {
        this.setScope($scope);
        this.designerScope = designerScope;
        // variables
        this.scope.violations = {};
        // functions
        this.scope.addGuest = this.addGuest;
        this.scope.deleteGuest = this.deleteGuest;

        // need this to make the variable present on the scope for the watch because
        //      the ng-model directive has not yet been hit
        this.scope.newPageGuestUsername = null;
        this.scope.validateNewPageGuestUsername = this.validateNewPageGuestUsername;
        this.scope.$watch('newPageGuestUsername', this.scope.validateNewPageGuestUsername);

        // register watches
        this.scope.pageData = designerScope.pageData;
        this.scope.close = function () {
            dialog.close();
        };
    };
    vallocController.PageGuestController.prototype = Controller.prototype;
    vallocController.PageGuestController.prototype.constructor = vallocController.PageGuestController;
    vallocController.PageGuestController.$inject = ['$scope', '$dialog'];
    vallocController.PageGuestController.prototype.validateNewPageGuestUsername = function (newValue, oldValue, scope) {
        if (scope.addGuestForm) {
            var formField = scope.addGuestForm.newPageGuestUsername;
            scope.violations.newPageGuestUsernameRequired = formField.$dirty && formField.$error.required;
            scope.violations.newPageGuestUsernameLength =
                formField.$dirty && (formField.$error.minlength || formField.$error.maxlength);
        }
    };
    vallocController.PageGuestController.prototype.isAddGuestFormPartiallyPristine = function () {
        return this.addGuestForm.newPageGuestUsername.$pristine;
    };
    vallocController.PageGuestController.prototype.addGuest = function () {
        var scope = this;
        var newPageGuestUsername = this.newPageGuestUsername;
        scope.clearAlerts();
        scope.newPageGuestUsernameSuccess = null;
        scope.newPageGuestUsernameErrors = null;

        var callback = function (response) {
            if (response.isSuccess) {
                scope.pageData.pageGuests.unshift(response.object);
                scope.newPageGuestUsernameSuccess = response.userMessage.message;
                scope.newPageGuestUsername = null; // reset
            } else {
                scope.newPageGuestUsernameErrors = response.userMessage.message;
            }
            scope.$apply();
        };
        var designerService = new service.DesignerService();
        designerService.addGuest(newPageGuestUsername, callback);
    };
    vallocController.PageGuestController.prototype.deleteGuest = function (targetPageGuest) {
        var pageGuests = this.pageData.pageGuests;
        var i = 0;
        for (; i < pageGuests.length; i++) {
            var pageGuest = pageGuests[i];
            if (pageGuest.username === targetPageGuest.username) {
                pageGuests.splice(i, 1);
                break;
            }
        }
    };

    vallocController.EditSectionController = function ($scope) {
        this.setScope($scope);
        var scope = this.scope;
        scope.controller = this;
        scope.violations = {};
        // register watches
        scope.$watch('sectionTitle', this.validateEditSectionSectionTitle);
        // direct scope methods
        scope.open = function () {
            scope.sectionTitle = scope.$parent.section.name;
            this.originalTitle = scope.sectionTitle;
            scope.shouldBeOpen = true;
        };
        scope.save = function () {
            var scopeParent = scope.$parent;
            var originalTitle = this.originalTitle;
            scope.$parent.section.name = scope.sectionTitle;
            // edit the link form sections dropdown
            var i = 0;
            var sections = scope.$parent.sections;
            for (; i < sections.length; i++) {
                if (sections[i].label === originalTitle) {

                    var sectionListObject = {
                        label: scope.sectionTitle,
                        value: scope.sectionTitle
                    };
                    sections[i] = sectionListObject;
                    break;
                }
            }
            this.originalTitle = scope.sectionTitle; // reset
            scope.shouldBeOpen = false;
        };
        scope.close = function () {
            scope.shouldBeOpen = false;
        };
        scope.opts = {
            backdropFade: true,
            dialogFade: false
        };
    };
    vallocController.EditSectionController.prototype = Controller.prototype;
    vallocController.EditSectionController.prototype.constructor = vallocController.EditSectionController;
    vallocController.EditSectionController.$inject = ['$scope'];
    vallocController.EditSectionController.prototype.validateEditSectionSectionTitle = function (newValue, oldValue, scope) {
        if (scope.editSectionForm) {
            var nameFormField = scope.editSectionForm.sectionTitle;
            scope.violations.sectionNameRequired = nameFormField.$dirty && nameFormField.$error.required;
            scope.violations.sectionNameLength =
                nameFormField.$dirty && (nameFormField.$error.minlength || nameFormField.$error.maxlength);
        }
    };
    vallocController.EditLinkController = function ($scope) {
        this.setScope($scope);
        var scope = this.scope;
        scope.controller = this;
        scope.violations = {};
        // register watches
        scope.$watch('linkText', this.validateEditLinkLinkText);
        scope.$watch('linkUrl', this.validateEditLinkLinkUrl);
        // direct scope methods
        scope.open = function () {
            scope.linkText = scope.$parent.link.name;
            scope.linkUrl = scope.$parent.link.url;
            scope.shouldBeOpen = true;
        };
        scope.save = function () {
            scope.$parent.link.name = scope.linkText;
            scope.$parent.link.url = scope.linkUrl;

            if (scope.linkNote) {
                var noteObj = {
                    note: scope.linkNote,
                    firstName: Util.msg('designer_linkNote_justAddedByYou') // for displaying on UI
                };
                scope.$parent.link.linkNotes.push(noteObj);
                scope.linkNote = undefined; // reset for next link note
            }

            scope.shouldBeOpen = false;
        };
        scope.close = function () {
            scope.shouldBeOpen = false;
        };
        scope.opts = {
            backdropFade: true,
            dialogFade: false
        };
    };
    vallocController.EditLinkController.prototype = Controller.prototype;
    vallocController.EditLinkController.prototype.constructor = vallocController.EditLinkController;
    vallocController.EditLinkController.$inject = ['$scope'];
    vallocController.EditLinkController.prototype.validateEditLinkLinkText = function (newValue, oldValue, scope) {
        if (scope.editLinkForm) {
            var nameFormField = scope.editLinkForm.linkText;
            scope.violations.linkNameRequired = nameFormField.$dirty && nameFormField.$error.required;
            scope.violations.linkNameLength =
                nameFormField.$dirty && (nameFormField.$error.minlength || nameFormField.$error.maxlength);
        }
    };
    vallocController.EditLinkController.prototype.validateEditLinkLinkUrl = function (newValue, oldValue, scope) {
        if (scope.editLinkForm) {
            var nameFormField = scope.editLinkForm.linkUrl;
            scope.violations.linkUrlRequired = nameFormField.$dirty && nameFormField.$error.required;
            scope.violations.linkUrlLength =
                nameFormField.$dirty && (nameFormField.$error.minlength || nameFormField.$error.maxlength);
        }
    };

    vallocController.FeedbackController = function ($scope) {
        this.setScope($scope);
        var scope = this.scope;
        scope.violations = new Object();
        this.applicationService = new service.ApplicationService();
        scope.feedbackParams = {
            name: null,
            email: null,
            comment: null
        };
        scope.saveFeedback = this.saveFeedback;
        scope.saveContactMessage = this.saveContactMessage;


        scope.$watch('feedbackParams.name', this.validateName);
        scope.$watch('feedbackParams.email', this.validateEmail);
        scope.$watch('feedbackParams.comment', this.validateComment);

        scope.master = {};
        scope.update = function(feedbackParams) {
            scope.master = angular.copy(feedbackParams);
        };

        $scope.isUnchanged = function(feedbackParams) {
            return angular.equals(feedbackParams, $scope.master);
        };

    };
    vallocController.FeedbackController.prototype = Controller.prototype;
    vallocController.FeedbackController.prototype.constructor = vallocController.FeedbackController;
    vallocController.FeedbackController.$inject = ['$scope'];
    vallocController.FeedbackController.prototype.saveFeedback = function () {
        var scope = this;
        scope.submitted = true;

        var callback = function (response) {
            if (response.isSuccess) {
                scope.controller.addAlert('success', response.userMessage.message);
                scope.$apply();
            } else {
                scope.$apply(function() {
                    scope.serverErrorMessages = response.supportingUserMessages;
                    scope.serverErrors = {};
                    response.supportingUserMessages.forEach(function (e) {
                        scope.serverErrors[e.key] = e.message;
                    });
                });
                // now that servers errors where applied to the model and style this property
                //		can be removed from the scope
                scope.isValidAndSubmitted = false;
            }
        };

        if (!scope.formSupport.formContainsErrors(scope.feedbackForm)) {
            scope.isValidAndSubmitted = true;
            this.controller.applicationService.saveFeedback(scope.feedbackParams, callback);
        }
    };
    vallocController.FeedbackController.prototype.saveContactMessage = function () {
        var scope = this;
        scope.submitted = true;

        var callback = function (response) {
            if (response.isSuccess) {
                scope.controller.addAlert('success', response.userMessage.message);
                scope.$apply();
            } else {
                scope.$apply(function() {
                    scope.serverErrorMessages = response.supportingUserMessages;
                    scope.serverErrors = {};
                    response.supportingUserMessages.forEach(function (e) {
                        scope.serverErrors[e.key] = e.message;
                    });
                });
                // now that servers errors where applied to the model and style this property
                //		can be removed from the scope
                scope.isValidAndSubmitted = false;
            }
        };

        if (!scope.formSupport.formContainsErrors(scope.feedbackForm)) {
            scope.isValidAndSubmitted = true;
            this.controller.applicationService.saveContactMessage(scope.feedbackParams, callback);
        }
    };

    vallocController.FeedbackController.prototype.validateName = function (newValue, oldValue, scope) {
        var formField = scope.feedbackForm.name;
        scope.violations.nameRequired = formField.$dirty && formField.$error.required;
    };
    vallocController.FeedbackController.prototype.validateEmail = function (newValue, oldValue, scope) {
        var formField = scope.feedbackForm.email;
        scope.violations.emailRequired = formField.$dirty && formField.$error.required;
        scope.violations.emailFormat = formField.$dirty && formField.$error.email;
    };
    vallocController.FeedbackController.prototype.validateComment = function (newValue, oldValue, scope) {
        var formField = scope.feedbackForm.comment;
        scope.violations.commentRequired = formField.$dirty && formField.$error.required;
    };

    vallocController.PinLinkController = function ($scope) {
        this.setScope($scope);
        var scope = this.scope;
        // data
        scope.alerts = new Array();
        scope.alertData = new Object();
        scope.pageData = this.getPageLoadData();
        if (scope.pageData.pages != null) {
            scope.linkTitle = scope.pageData.linkTitle;
            this.applicationService = new service.ApplicationService();
            scope.pages = new Array();
            scope.pageData.pages.forEach(function (page){
                var pageSelectData = {
                    label: page.name,
                    value: page.path
                };
                scope.pages.push(pageSelectData);
            });
        } else {
            scope.pinLink_validate_noPages = true;
        }

        // methods
        scope.pageSelected = this.pageSelected;
        scope.sectionSelected = this.sectionSelected;
        scope.closeAlerts = this.closeAlerts;
    };
    vallocController.PinLinkController.prototype = Controller.prototype;
    vallocController.PinLinkController.prototype.constructor = vallocController.PinLinkController;
    vallocController.PinLinkController.$inject = ['$scope'];
    vallocController.PinLinkController.prototype.pageSelected = function () {
        var scope = this;
        var pagePath = scope.pinLinkForm.pagePath.$viewValue;
        var page;
        scope.pageData.pages.forEach(function (pageCandidate) {
            if (pagePath === pageCandidate.path) {
                page = pageCandidate;
            }
        });
        scope.availableSections = [];
        page.columns.forEach(function (column) {
            column.sections.forEach(function (section) {
                var sectionSelectData = {
                    label: section.name,
                    value: section.name
                };
                scope.availableSections.push(sectionSelectData);
            });
        });
        scope.isPageSelected = true;
    };
    vallocController.PinLinkController.prototype.sectionSelected = function () {
        var scope = this;
        scope.isSubmitted = true;

        var callback = function (response) {
            if (response.isSuccess) {

//              if (relayMessage.code === 1670) {
                scope.alertData.pageName = response.attributes.pageName;
                scope.alertData.hrefUrl = response.attributes.hrefUrl;
                scope.alertData.displayUrl = response.attributes.displayUrl;
                scope.alerts.push({type: 'success', msg: 'VOID'});

                scope.$apply();
            } else {
                // FIXME not to share the same div as success
                this.scope.alerts.push({type: 'error', msg: response.userMessage.message});
                scope.isSubmitted = false;
                scope.$apply();
            }
        };

        var pinLinkParams = {
            pagePath: scope.pagePath,
            sectionName: scope.sectionName,
            linkTitle: scope.linkTitle,
            linkUrl: scope.pageData.linkUrl,
            linkNote: scope.linkNote
        };
        scope.isSubmitted = true;
        this.controller.applicationService.pinLink(pinLinkParams, callback);
    };



    vallocDirective.LinkNoteDirectiveFactory = function () {
        return {
            restrict: 'E',
            templateUrl: '/template/link-note-display'
        }
    };


    var vlcMod = angular.module('vlc');
    vlcMod.controller('LoginController',        ['$scope', com.valloc.vallocController.LoginController]);
    vlcMod.controller('AccountController',      ['$scope', com.valloc.vallocController.AccountController]);
    vlcMod.controller('BuilderController',      ['$scope', com.valloc.vallocController.BuilderController]);
    vlcMod.controller('DashboardController',    ['$scope', com.valloc.vallocController.DashboardController]);
    vlcMod.controller('DesignerController',     ['$scope', '$dialog', com.valloc.vallocController.DesignerController]);
    vlcMod.controller('EditSectionController',  ['$scope', com.valloc.vallocController.EditSectionController]);
    vlcMod.controller('EditLinkController',     ['$scope', com.valloc.vallocController.EditLinkController]);
    vlcMod.controller('PageGuestController',    ['$scope', 'dialog', 'designerScope', com.valloc.vallocController.PageGuestController]);
    vlcMod.controller('FeedbackController',     ['$scope', com.valloc.vallocController.FeedbackController]);
    vlcMod.controller('PinLinkController',      ['$scope', com.valloc.vallocController.PinLinkController]);
    vlcMod.directive('linkNoteDisplay',         com.valloc.vallocDirective.LinkNoteDirectiveFactory)

})();
