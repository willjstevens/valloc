/*
 * Custom v1.0
 * www.designorbital.com
 *
 * Copyright (c) 2013 DesignOrbital.com
 *
 * License: GNU General Public License, GPLv3
 * http://www.gnu.org/licenses/gpl-3.0.html
 *
 */

(function($){

    var kamn = {

        readyInit: function() {

            /** Hash Scroll */
            /** Portfolio Filter */
            $( '#navbar-spy' ).off( 'click' ).on( 'click', 'a', function( e ) {

                var elmHash = $( this ).attr( 'href' );

                if (elmHash.indexOf('login') >= 0) {
                    return;
                }

                e.preventDefault();

//                var elmHash = $( this ).attr( 'href' );
                var elmOffsetTop = Math.ceil( $( this.hash ).offset().top );
                var windowOffsetTop = Math.ceil( $(window).scrollTop() );

                if( elmOffsetTop != 0 ) {
                    elmOffsetTop = elmOffsetTop - 70;
                    if( windowOffsetTop == 0 ) {
                        elmOffsetTop = elmOffsetTop - 70;
                    }
                }

                //console.log( $( this ).attr( 'href' ) );
                $( 'html:not(:animated), body:not(:animated)' ).animate({ scrollTop: elmOffsetTop }, 1100 );

                // theres a bug with scrollspy so explicitly remove on both li and a tags
                $('#nav-ul a').removeClass('active');
                $('#nav-ul li').removeClass('active');
            });

            /** Initialise Stellar */
            $.stellar({
                horizontalScrolling: false
            });

            /** Nice Scroll */
            $( 'html' ).niceScroll({
                cursorcolor: '#2d3032',
                cursorwidth: '10px',
                cursoropacitymax: 0.5,
                scrollspeed: 300,
                zindex: 1060
            });

            /** prettyPhoto */
            $( "a[data-rel^='prettyPhoto']" ).prettyPhoto({
                deeplinking: false,
                show_title: false,
                social_tools: ''
            });

            /** ToolTip */
            $( 'a[data-toggle="tooltip"]' ).tooltip();

            /** Navbar Animation */
            kamn.navbarAnimationInit();

        },

        loadInit: function() {

            /** #carousel-hero */
            $( '#carousel-1' ).carousel({
                interval: 5000
            })

            /** Portfolio */
            kamn.portfolioInit();
        },

        scrollInit: function() {
        },

        smartresizeInit: function() {
            /** Portfolio */
            kamn.portfolioInit();
        },

        spyRefreshInit: function() {
            $('[data-spy="scroll"]').each(function () {
                var $spy = $(this).scrollspy('refresh')
            });
        },

        navbarAnimationInit: function() {
            /** Navbar Animation */
            $( '#navbar-section' ).waypoint( function( direction ) {
                if( direction == 'down' ) {
                    $( '#navbar-section' ).removeClass( 'navbar-static-top animated fadeInUp' );
                    $( '#navbar-section' ).addClass( 'navbar-fixed-top animated fadeInDown' );
                } else {
                    $( '#navbar-section' ).removeClass( 'navbar-fixed-top animated fadeInDown' );
                    $( '#navbar-section' ).addClass( 'navbar-static-top animated fadeInUp' );
                }
            }, {
                offset: -80
            });

        },

        portfolioInit: function() {

            /** Portfolio */
            var $container = $( '#projects' );
            var container_width = $container.width();

            /** Masonry Columns */
            var masonryColumns = 4;
            if( Modernizr.mq( 'only screen and (max-width: 959px)' ) ) {
                /** Smaller than standard 960 (devices and browsers) */
                masonryColumns = 3;
            }
            if( Modernizr.mq( 'only screen and (max-width: 767px)' ) ) {
                /** All Mobile Sizes (devices and browser) */
                masonryColumns = 1;
            }

            /** Masonry Column Width */
            var columnWidth = Math.floor( container_width / masonryColumns );
            $( '#projects > .element' ).width( columnWidth );

            $container.isotope({
                masonry: {
                    columnWidth: columnWidth,
                    gutterWidth: 0
                },

                resizable: false,
                animationEngine: 'best-available',
                animationOptions: {
                    duration: 300,
                    easing: 'easeInOutQuad',
                    queue: false
                }

            }, kamn.portfolioCallbackInit( 300 ));

            /** Portfolio Filter */
            $( '#filters' ).off( 'click' ).on( 'click', 'a', function( e ) {

                e.preventDefault();

                var filter = $(this).attr( 'data-option-value' );
                $container.isotope({ filter: filter }, kamn.portfolioCallbackInit( 1200 ));
                $( '#filters a' ).removeClass( 'active' );
                $(this).addClass( 'active' );
            });
        },

        portfolioCallbackInit: function( delay ) {
            setTimeout( function() { kamn.spyRefreshInit() }, delay );
        }
    }

    /** Document Ready */
    $(document).ready(function(){
        kamn.readyInit();
    });

    /** Windows Load */
    $(window).load(function(){
        kamn.loadInit();
    });

    /** Windows Scroll */
    $(window).scroll(function(){
        kamn.scrollInit();
    });

    /** Windows Smartresize */
    $(window).smartresize(function(){
        kamn.smartresizeInit();
    });

})(jQuery);