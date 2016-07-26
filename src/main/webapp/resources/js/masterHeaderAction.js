	$(document).ready(function(e) {
		$('.side-menu').click(function(){
			$('.side-bar').toggleClass("side-bar-active");
			$('.user-panel').toggleClass("user-panel-active");
			$('.content-wraper').toggleClass("content-wraper-active");
			$('.footer-area').toggleClass("footer-area-active");
			$("div.side-nav ul li").find('ul.collapses, .tree-view').toggleClass("tree-view-active");
			$("div.side-nav ul li").find('.tree-view-new').toggleClass('tree-view-new-active');
			});
		$('.srch-btn-xs').click(function(){
		  $(".search-area").toggleClass("search-area-active");
	    });
		$('html').click(function(){
		  $(".search-area").removeClass("search-area-active");
	    });
		$('.srch-btn-xs, .search-area-txt').click(function(e){
		  e.stopPropagation();
	    });
		$('.login-user').click(function(){
		 $('.user-details').toggleClass('user-details-active');
		 $('.setting-area').removeClass('setting-area-active');
		 $('.alert-area').removeClass('alert-area-active');
		 $('.qlinks-area').removeClass('qlinks-area-active');
		 $('.note-area').removeClass('note-area-active');
	    });
		
		$("#notification").click(function(){
			$('.note-area').toggleClass('note-area-active');
			$('.user-details').removeClass('user-details-active');
			$('.setting-area').removeClass('setting-area-active');
			$('.alert-area').removeClass('alert-area-active');
			$('.qlinks-area').removeClass('qlinks-area-active');
		});
		$("#alert").click(function(){
			$('.alert-area').toggleClass('alert-area-active');
			$('.user-details').removeClass('user-details-active');
			$('.setting-area').removeClass('setting-area-active');
			$('.note-area').removeClass('note-area-active');
			$('.qlinks-area').removeClass('qlinks-area-active');
		});
		$("#qlinks").click(function(){
			$('.qlinks-area').toggleClass('qlinks-area-active');
			$('.alert-area').removeClass('alert-area-active');
			$('.user-details').removeClass('user-details-active');
			$('.setting-area').removeClass('setting-area-active');
			$('.note-area').removeClass('note-area-active');
		});
		$('html').click(function(){
		  $('.user-details').removeClass("user-details-active");
		  $('.setting-area').removeClass('setting-area-active');
		  $('.note-area').removeClass('note-area-active');
		  $('.alert-area').removeClass('alert-area-active');
		  $('.qlinks-area').removeClass('qlinks-area-active');
		  $('.timesheet-menu').removeClass('timesheet-menu-active');
	    });
		$('.header-nav .navbar, .custom-navbar, .user-details, .tree-view-new').click(function(e){
		  e.stopPropagation();
	    });
		$('.settings').click(function(){
		$('.setting-area').toggleClass('setting-area-active');
		$('.note-area').removeClass('note-area-active');
		$('.user-details').removeClass('user-details-active');
		$('.alert-area').removeClass('alert-area-active');
		$('.qlinks-area').removeClass('qlinks-area-active');
	    });
		$(".collpase-div").click(function(){
			$(".toggle-div").slideToggle(800);
		});
		$(".faclose").click(function(){
			$(".remove-div").addClass('hidden');
		});
		$(".bell").click(function(){
				$(this).toggleClass('bell-active');			
			});
       $(".addLevel").click(function(){
		   $(".level-second").toggleClass("level-second-active");
		});
       
       $(".tree-view-new").click(function(){
    	   $('.timesheet-menu').toggleClass('timesheet-menu-active');
    	   });
       
       $(".tree-view").click(function(){
    	   $(this).next('ul').slideToggle();
    	   });
    });   