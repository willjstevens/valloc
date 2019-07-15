"use strict";

(function() {

    $(document).ready(function (){
        $(function(){
            $("#hiw-carousel").carousel();
        });
        function addDivEffects(scrolltop, div) {
            $('html, body').animate({
                scrollTop: scrolltop.offset().top
            }, 1500);
            cleanupActiveClasses();
            div.addClass("featurette-selected active").delay(2000).queue(function(next){
                $(this).removeClass("featurette-selected");
                div.addClass("featurette-selected-out");
                next();
            });
        }
        function cleanupActiveClasses() {
            var div = $("div.list-group > a.active");
            div.removeClass("active");
            div = $("div.featurette-selected-out");
            div.removeClass("featurette-selected-out");
        }
        function addListGroupActiveClass(a) {
            a.addClass("active");
        }
        function loadSummary() {
            $('html, body').animate({
                scrollTop: $("#scrolltop-summary").offset().top
            }, 500);
        }
        function loadInformationFeaturette() {
            addDivEffects($("#scrolltop-informational"), $("#div-informational"));
            addListGroupActiveClass($("#btn-informational"));
        }
        function loadPageDesignerFeaturette() {
            addDivEffects($("#scrolltop-page-designer"), $("#div-page-designer"));
            addListGroupActiveClass($("#btn-page-designer"));
        }
        function loadMobilityFeaturette() {
            addDivEffects($("#scrolltop-mobility"), $("#div-mobility"));
            addListGroupActiveClass($("#btn-mobility"));
        }
        function loadCollaborateFeaturette() {
            addDivEffects($("#scrolltop-collaborate"), $("#div-collaborate"));
            addListGroupActiveClass($("#btn-collaborate"));
        }
        function loadGetDataInFeaturette() {
            addDivEffects($("#scrolltop-get-data-in"), $("#div-get-data-in"));
            addListGroupActiveClass($("#btn-get-data-in"));
        }
        $("#btn-informational").click(function (e){
            e.preventDefault();
            loadInformationFeaturette();
        });
        $("#btn-page-designer").click(function (e){
            e.preventDefault();
            loadPageDesignerFeaturette();
        });
        $("#btn-mobility").click(function (e){
            e.preventDefault();
            loadMobilityFeaturette();
        });
        $("#btn-collaborate").click(function (e){
            e.preventDefault();
            loadCollaborateFeaturette();
        });
        $("#btn-get-data-in").click(function (e){
            e.preventDefault();
            loadGetDataInFeaturette();
        });

        $('a.gallery-informational').colorbox();
        $('a.gallery-page-designer').colorbox();
        $('a.gallery-mobility').colorbox();
        $('a.gallery-collaborate').colorbox();
        $('a.gallery-get-data-in').colorbox();


        $(".summary").click(function (){
            loadSummary();
        });
        $(".scroll-btn").click(function (){
            loadSummary();
        });
        $("#carousel-logo").click(function (){
            loadSummary();
        });
        $("#carousel-informational").click(function (){
            loadInformationFeaturette();
        });
        $("#carousel-page-designer").click(function (e){
            e.preventDefault();
            loadPageDesignerFeaturette();
        });
        $("#carousel-mobility").click(function (){
            loadMobilityFeaturette();
        });
        $("#carousel-collaborate").click(function (){
            loadCollaborateFeaturette();
        });
        $("#carousel-get-data-in").click(function (){
            loadGetDataInFeaturette();
        });

    });




})();
