// JavaScript Document

 
$(function(){
  $(".icon-theme").click(function () {
        $(".theme-color").toggleClass("theme-color-active");
  });
  $('html').click(function(){
	  $(".theme-color").removeClass("theme-color-active");
  });
  
  $('.icon-theme, .theme-color').click(function(event){
	  event.stopPropagation();
  });
    if (typeof (Storage) !== "undefined") {
    	
        var themeColor = localStorage.getItem("themeColor");
    if(themeColor == 'dark-blue'){
		 $('head').append('<link rel="stylesheet" href="/resources/css/sourcelead-theme-one.css" type="text/css" />');
		 $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-two.css"]').remove();
		 $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-three.css"]').remove();
		 $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-four.css"]').remove();
	     $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-default.css"]').remove();
		 $('.footer').append('<script src="js/theme-one-script.js">');
    }
    else if(themeColor == 'flat-green'){
		$('head').append('<link rel="stylesheet" href="/resources/css/sourcelead-theme-two.css" type="text/css" />');
		$('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-one.css"]').remove();
		$('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-three.css"]').remove();
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-four.css"]').remove();
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-default.css"]').remove();
        $('<script src="js/theme-one-script.js">').remove();
    }
   else if(themeColor == 'hdr-blue'){
	   
       $('head').append('<link rel="stylesheet" href="/resources/css/sourcelead-main.css" type="text/css" />');
		 $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-one.css"]').remove();
		 $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-two.css"]').remove();
		 $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-three.css"]').remove();
         $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-four.css"]').remove();
         $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-default.css"]').remove();
         $('<script src="js/theme-one-script.js">').remove();
	}
    else if(themeColor == 'hdr-grey'){
		$('head').append('<link rel="stylesheet" href="/resources/css/sourcelead-theme-three.css" type="text/css" />');
		$('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-two.css"]').remove();
		$('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-one.css"]').remove();
		$('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-four.css"]').remove();
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-default.css"]').remove();
		$('<script src="js/theme-one-script.js">').remove();
	}
    else if(themeColor == 'hdr-greydrk'){
		$('head').append('<link rel="stylesheet" href="/resources/css/sourcelead-theme-four.css" type="text/css" />');
		$('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-two.css"]').remove();
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-three.css"]').remove();
		$('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-one.css"]').remove();
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-default.css"]').remove();
		$('<script src="js/theme-one-script.js">').remove();
	}
    
    else if(themeColor == 'default-redTheme'){
		$('head').append('<link rel="stylesheet" href="/resources/css//sourcelead-theme-default.css" type="text/css" />');
		$('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-two.css"]').remove();
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-three.css"]').remove();
		$('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-one.css"]').remove();
		$('link[rel=stylesheet][href~="/resources/css/ sourcelead-theme-four.css"]').remove();
		$('<script src="js/theme-one-script.js">').remove();
	}
    } else {
        window.alert('Please use a modern browser to properly view this template!');
    }
});

$(document).ready(function (e) {
    $("#btn1").click(function () {
        if (typeof (Storage) !== "undefined") {
            localStorage.setItem("themeColor", "dark-blue");
        } else {
            window.alert('Please use a modern browser to properly view this template!');
        }

        $('head').append('<link rel="stylesheet" href="/resources/css/sourcelead-theme-one.css" type="text/css" />');
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-two.css"]').remove();
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-three.css"]').remove();
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-four.css"]').remove();
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-default.css"]').remove();
        $('.footer').append('<script src="js/theme-one-script.js">');
    });
    $("#btn2").click(function () {
        if (typeof (Storage) !== "undefined") {
            localStorage.setItem("themeColor", "flat-green");
        } else {
            window.alert('Please use a modern browser to properly view this template!');
        }
        $('head').append('<link rel="stylesheet" href="/resources/css/sourcelead-theme-two.css" type="text/css" />');
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-one.css"]').remove();
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-three.css"]').remove();
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-default.css"]').remove();
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-four.css"]').remove();
        $('<script src="js/theme-one-script.js">').remove();
    });
    $("#btn3").click(function () {
        if (typeof (Storage) !== "undefined") {
            localStorage.setItem("themeColor", "hdr-blue");
        } else {
            window.alert('Please use a modern browser to properly view this template!');
        }
        $('head').append('<link rel="stylesheet" href="/resources/css/sourcelead-main.css" type="text/css" />');
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-one.css"]').remove();
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-four.css"]').remove();
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-two.css"]').remove();
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-three.css"]').remove();
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-default.css"]').remove();
        $('<script src="js/theme-one-script.js">').remove();
    });
    $("#btn4").click(function () {

        if (typeof (Storage) !== "undefined") {

            localStorage.setItem("themeColor", "hdr-grey");
        } else {
            window.alert('Please use a modern browser to properly view this template!');
        }

        $('head').append('<link rel="stylesheet" href="/resources/css/sourcelead-theme-three.css" type="text/css" />');
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-two.css"]').remove();
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-one.css"]').remove();
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-four.css"]').remove();
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-default.css"]').remove();
        $('<script src="js/theme-one-script.js">').remove();
    });
    $("#btn5").click(function () {
        if (typeof (Storage) !== "undefined") {
            localStorage.setItem("themeColor", "hdr-greydrk");
        } else {
            window.alert('Please use a modern browser to properly view this template!');
        }
        $('head').append('<link rel="stylesheet" href="/resources/css/sourcelead-theme-four.css" type="text/css" />');
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-two.css"]').remove();
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-one.css"]').remove();
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-three.css"]').remove();
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-default.css"]').remove();
        $('<script src="js/theme-one-script.js">').remove();
    });
    $("#btn6").click(function () {
        if (typeof (Storage) !== "undefined") {
            localStorage.setItem("themeColor", "default-redTheme");
        } else {
            window.alert('Please use a modern browser to properly view this template!');
        }
        $('head').append('<link rel="stylesheet" href="/resources/css/sourcelead-theme-default.css" type="text/css" />');
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-two.css"]').remove();
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-one.css"]').remove();
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-three.css"]').remove();
        $('link[rel=stylesheet][href~="/resources/css/sourcelead-theme-four.css"]').remove();
        $('<script src="js/theme-one-script.js">').remove();
    });
    
});