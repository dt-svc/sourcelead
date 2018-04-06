///////////////////// Back To Top 
 $(window).scroll(function() {
        if ($(this).scrollTop() > 5) {
            $('#navHeader-scroll').addClass('topNav-active navbar-fixed-toptopNav-active navbar-fixed-top');
			
        } else {
			$('#navHeader-scroll').removeClass('topNav-active navbar-fixed-top');
        }
    });
$(window).scroll(function() {
        if ($(this).scrollTop() > 150) {
            $('.backtotop').fadeIn('slow');
			
        } else {
            $('.backtotop').fadeOut('slow');
        }
    });
    $('.backtotop').click(function() {
        $("html, body").animate({scrollTop: 0}, 500);
        return false;
    });

/*	$(function(){
		$("li.dropdown").click(function(){
			$(this).find('span').toggleClass('fa-angle-down fa-angle-up').sibilings().toggleClass('fa-angle-up fa-angle-down')
		});
	})

$(".dropdown-featureshome").click(function() {
	$("#featureshome").slideToggle();
	$("#featureshome").slideToggle();
})*/

//Smooth Transaction
$(document).ready(function(){
  // Add smooth scrolling to all links
  $(".thim-click-to-bottom a").on('click', function(event) {
   if (this.hash !== "") {
      event.preventDefault();
      var hash = this.hash;
      $('html, body').animate({
        scrollTop: $(hash).offset().top
      }, 800, function(){
        window.location.hash = hash;
      });
    }
  });
});
//owal
	/*$(document).ready(function() {
		$(".owl-item").css({"width":"100"});
		var owl = $('.owl-carousel');
		owl.owlCarousel({
		items:6,
		loop:true,
		margin:10,
		autoplay:true,
		autoplayTimeout:1000,
		autoplayHoverPause:true,
		});
		$('.play').on('click',function(){
		owl.trigger('play.owl.autoplay',[1000])
		});
		$('.stop').on('click',function(){
		owl.trigger('stop.owl.autoplay')
		});
	});*/
$(document).ready(function() {
	$('.owl-carousel').owlCarousel({
		loop:true,
		margin:10,
		loop:true,
		autoplay:true,
		autoplayTimeout:1000,
		responsiveClass:true,
		responsive:{
		0:{
		items:1,
		nav:true
		},
		600:{
		items:3,
		nav:true
		},
		1000:{
		items:5,
		nav:true,
		loop:true
		}
		}
	});
});
 
	 $(document).ready(function() {
    $(".clPop").click(function(){
		$("#freeTrial").removeClass("activePop");
		$("#requestDemo").removeClass("activePop");
		$(".clPop").removeClass("clPopA");
		$("body").css("overflow", "auto");
		$(".backshadow").fadeOut(200);
	});	
});
	function requestDemo(){
		$("#freeTrial").addClass("activePop");
		$(".clPop").addClass("clPopA");
		$("body").css("overflow", "hidden");
		$(".backshadow").fadeIn(200);
	}

// Responsive Menu bar
function mobileNavfun() {
    $("#mobileNav").toggleClass("change");
	$(".backshadow").fadeIn(200);
	$(".customNavBar-inner").addClass("menuAct");
}
function closeNav() {
    $("#mobileNav").toggleClass("change");
	$(".backshadow").fadeOut(200);
	$(".customNavBar-inner").removeClass("menuAct");
}

$(document).ready(function(){
	$("area[rel^='prettyPhoto']").prettyPhoto();
	$(".gallery:first a[rel^='prettyPhoto']").prettyPhoto({animation_speed:'normal',theme:'light_square',slideshow:3000, autoplay_slideshow: false});
	$(".gallery:gt(0) a[rel^='prettyPhoto']").prettyPhoto({animation_speed:'fast',slideshow:10000, hideflash: true});
});

