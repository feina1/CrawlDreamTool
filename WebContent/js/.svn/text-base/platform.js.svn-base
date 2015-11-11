var ua = window.navigator.userAgent.toLowerCase(); //alert(ua);
window.platform = {
    isiphone:ua.match(/iphone/i) !== null,
    isAndroid: ua.match(/android/i) !== null,
    isAndroid23: ua.match(/android 2\.3/i) !== null,
    isAndroid404: ua.match(/android 4\.0\.4/i) !== null,
    isAndroid412: ua.match(/android 4\.1\.2/i) !== null,
    isBustedAndroid: ua.match(/android 2\.[12]/) !== null,
    isNexus: ua.match(/nexus/i) !== null,
    isDuos: ua.match(/gt\-s7562/i) !== null,
    isS7562: ua.match(/gt\-s7562/i) !== null,
    isS3: ua.match(/gt\-i9300/i) !== null,
    isI9300: ua.match(/gt\-i9300/i) !== null,
    isIE: /(msie|trident)/i.test(navigator.userAgent), //window.navigator.appName.indexOf("Microsoft") !== -1,
    isIE8: ua.match(/msie 8/) !== null,
    isChrome: ua.match(/Chrome/gi) !== null,
    isFirefox: ua.match(/firefox/gi) !== null,
    isWebkit: ua.match(/webkit/gi) !== null,
    isGecko: ua.match(/gecko/gi) !== null,
    isOpera: ua.match(/opera/gi) !== null,
    ltIE9 : $("html").hasClass("lt-ie9"),
    isMobile: navigator.userAgent.match(/Android|webOS|iPhone|iPod|BlackBerry|IEMobile/i) && navigator.userAgent.match(/Mobile/i) !== null,
    hasTouch: ('ontouchstart' in window),
    supportsSvg: !! document.createElementNS && !! document.createElementNS('http://www.w3.org/2000/svg', 'svg').createSVGRect
}

window.platform.isAndroidPad = platform.isAndroid && !platform.isMobile;
window.platform.isTablet = platform.isiPad || platform.isAndroidPad;
window.platform.isDesktop = !(platform.isMobile || platform.isTablet);
window.platform.isIOS = platform.isiPad || platform.isiPhone;


	var $right1 = $('#right');
		  var $rwidth = $(window).width(),
		      $rheight = $(window).height();
		      if($rwidth > 1100){
		    	  $right1.css('width',$rwidth - 350);
		      }

