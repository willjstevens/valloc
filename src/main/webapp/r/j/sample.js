"use strict";

(function() {

    // setup namespaces and local vars
    var com = window.com || {};
    com.pruitt = com.pruitt || {};


    /*
     * PARENT Class
     */
    // Your base class constructor
    var PruittParentClass = function PruittParentClass() {
        this.baseVar = 'Hello';
    };
    // set a reference in your namespace
    com.pruitt.PruittParentClass = PruittParentClass;
    // point the base class contructor property to your defined constructor
    PruittParentClass.prototype.constructor = PruittParentClass;
    // this is your method to override or shared logic -
    //      Note: the method name is DIFFERENT because there is no super variable
    PruittParentClass.prototype.doSomethingBASE = function () {
        // do some logic...
    };
    PruittParentClass.prototype.setTitle = function (title) {
        this.title = title;
    }
    PruittParentClass.prototype.getTitle = function () {
        return this.title;
    }

    /*
     *  CHILD class for an iPad implementation
     */
    var IPadChildClass = function () {
        this.ipadName = 'iPad title jajaja';
    };
    // put on namespace so you can invoke with:
    //
    //      var myImpl = new IPadChildClass();
    com.pruitt.IPadChildClass = IPadChildClass;
    // here you are pointing this class's prototype to the parent -> PruittParentClass
    IPadChildClass.prototype = com.pruitt.PruittParentClass.prototype;
    IPadChildClass.prototype.constructor = IPadChildClass;
    // Here is your common method to provide in all implementations
    IPadChildClass.prototype.doCalculateScreenSize = function () {
        // do some special logic
        var iPadScreenSize = 768;

        // do some COMMON logic shared in all implementations that is defined in the parent
        this.doSomethingBASE();

        // .... do side work;

        return iPadScreenSize;
    };


    /*
     *  CHILD class for an Galaxy S4 implementation
     */
    var GalaxyS4ChildClass = function () {
        this.galaxyName = 'Galaxy phones rock.';
    };
    // put on namespace so you can invoke with:
    //
    //      var myImpl = new GalaxyS4ChildClass();
    com.pruitt.GalaxyS4ChildClass = GalaxyS4ChildClass;
    // here you are pointing this class's prototype to the parent -> PruittParentClass
    GalaxyS4ChildClass.prototype = com.pruitt.PruittParentClass.prototype;
    GalaxyS4ChildClass.prototype.constructor = GalaxyS4ChildClass;
    // Here is your common method to provide in all implementations
    GalaxyS4ChildClass.prototype.doCalculateScreenSize = function () {
        // do some special logic
        var galaxyScreenSize = 400;

        // do some COMMON logic shared in all implementations that is defined in the parent
        this.doSomethingBASE();

        // .... do side work;

        return galaxyScreenSize;
    };


    /*
     *  Invoke sample CLIENT code
     */
    (function() {

        var deviceImpl = undefined;

        // This method getDeviceTypeFromSomewhere() just illustrates the FLAG that indicates which implementation to use:
        var userDeviceType = getDeviceTypeFromSomewhere();

        switch (userDeviceType) {
            case 'iPad':    deviceImpl = new com.pruitt.IPadChildClass();           break;
            case 'galaxy':  deviceImpl = new com.pruitt.GalaxyS4ChildClass();       break;
            default: throw new Error('Unrecognized device.');
        }

        // this method is the COMMON method in ALL implementations
        var deviceScreenSize = deviceImpl.doCalculateScreenSize();

        console.log("DEVICE SCREEN SIZE: " + deviceScreenSize);
    })();

})();
