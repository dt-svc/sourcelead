// Background
$(function(){
	$('.slider li').hide().filter(':first').show();
	setInterval(slideshow, 8000);
});

function slideshow() {
	$('.slider li:first').fadeOut('slow').next().fadeIn('slow').end().appendTo('.slider');
}

// Filters
$(".filterCta h3").click(function(){
	$(this).find("i").toggleClass("arrAc");
	$(this).next().slideToggle(200);
});

// Filters Responsive
$(".resFil").click(function(){
	$(".filter").addClass("filAc");
	$(".asMask").fadeIn(200);
});

// Resoinsive header search
$("#resSe").click(function(){
	$(".innerPageSearch").slideToggle(200);
	$(this).toggleClass("click");
});

// Fliter remove
$(".fa-remove").click(function(){
	$(this).parents('span').hide();
});

// Loading data
$(window).load(function() {
    setTimeout(function() {
        $(".filLoad").fadeOut();
    }, 600);
});

// Adve Search
$("#ADS").click(function(){
	$(".asMask").fadeIn(200);
	$(".adSearch").fadeIn(100);
});

// Remove or hide functions
$(".asMask, .close-div").click(function(){
	$(".filter").removeClass("filAc");
	$(".asMask").fadeOut(200);
	$(".adSearch").fadeOut(100);
});


