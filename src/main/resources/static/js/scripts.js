!(function (e) {
	"use strict";
	e(window).on("load", function () {
		e(".preloader-wrap").fadeOut(1e3);
	});

    //animation scroll js
    var html_body = $('html, body');
    $('nav a').on('click', function () {
    	if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') && location.hostname == this.hostname) {
    		var target = $(this.hash);
    		target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');
    		if (target.length) {
    			html_body.animate({
    				scrollTop: target.offset().top - 100
    			}, 1500);
    			return false;
    		}
    	}
    });

    //for sticky
    $(window).scroll(function () {
    	var scroll = $(this).scrollTop();
    	if (scroll >= 100) {
    		$("#full_nav").addClass("sticky");
    	} else {
    		$("#full_nav").removeClass("sticky");
    	}
    });

})(jQuery);
