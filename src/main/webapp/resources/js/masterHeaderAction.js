	$(document).ready(function(e) {
		$('.side-menu').click(function(){
			$('.side-bar').toggleClass("side-bar-active");
			$('.user-panel').toggleClass("user-panel-active");
			$('.content-wraper').toggleClass("content-wraper-active");
			$('.footer-area').toggleClass("footer-area-active");
			$("div.side-nav ul li").find('ul.collapses, .tree-view, .associatesLi, .timesheetLi, .billingLi, .backofficeLi').toggleClass("tree-view-active");
			$("div.side-nav ul li").find('.tree-view-new').toggleClass('tree-view-new-active');
			$("div.side-nav ul li").find('.tree-view-associates').toggleClass('tree-view-associates-active');
			$("div.side-nav ul li").find('.tree-view-billing').toggleClass('tree-view-billing-active');
			$("div.side-nav ul li").find('.tree-view-backoffice').toggleClass('tree-view-backoffice-active');
			//Theme one side bar
			$('.theme_sideMenu').toggleClass('themeSideMenu_Active');
			$(".collapses").hide();
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
		 $('.spoclinks-area').removeClass('spoclinks-area-active');
	    });
		
		$("#notification").click(function(){
			$('.note-area').toggleClass('note-area-active');
			$('.user-details').removeClass('user-details-active');
			$('.setting-area').removeClass('setting-area-active');
			$('.alert-area').removeClass('alert-area-active');
			$('.qlinks-area').removeClass('qlinks-area-active');
			$('.spoclinks-area').removeClass('spoclinks-area-active');
		});
		$("#alert").click(function(){
			$('.alert-area').toggleClass('alert-area-active');
			$('.user-details').removeClass('user-details-active');
			$('.setting-area').removeClass('setting-area-active');
			$('.note-area').removeClass('note-area-active');
			$('.qlinks-area').removeClass('qlinks-area-active');
			$('.spoclinks-area').removeClass('spoclinks-area-active');
		});
		$("#qlinks").click(function(){
			$('.qlinks-area').toggleClass('qlinks-area-active');
			$('.alert-area').removeClass('alert-area-active');
			$('.user-details').removeClass('user-details-active');
			$('.setting-area').removeClass('setting-area-active');
			$('.note-area').removeClass('note-area-active');
			$('.spoclinks-area').removeClass('spoclinks-area-active');
		});
		$("#spoclinks").click(function(){
			
			$('.spoclinks-area').toggleClass('spoclinks-area-active');
			$('.qlinks-area').removeClass('qlinks-area-active');
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
		  $('.spoclinks-area').removeClass('spoclinks-area-active');
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
		$('.spoclinks-area').removeClass('spoclinks-area-active');
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
    	   $('.billing-menu').removeClass('billing-menu-active');
    	   $('.associates-menu').removeClass('associates-menu-active');
    	   $('.backoffice-menu').removeClass('timesheet-menu-active');
       });
       $(".tree-view-associates").click(function(){
    	   $('.associates-menu').toggleClass('associates-menu-active');
    	   $('.timesheet-menu').removeClass('timesheet-menu-active');
    	   $('.billing-menu').removeClass('billing-menu-active');
    	   $('.backoffice-menu').removeClass('backoffice-menu-active');
       });
       $(".tree-view-billing").click(function(){
    	   $('.billing-menu').toggleClass('billing-menu-active');
    	   $('.timesheet-menu').removeClass('timesheet-menu-active');
    	   $('.associates-menu').removeClass('associates-menu-active');
    	   $('.backoffice-menu').removeClass('backoffice-menu-active');
       });
       $(".tree-view-backoffice").click(function(){
    	   $('.backoffice-menu').toggleClass('backoffice-menu-active');
    	   $('.timesheet-menu').removeClass('timesheet-menu-active');
    	   $('.associates-menu').removeClass('associates-menu-active');
    	   $('.billing-menu').removeClass('billing-menu-active');
       });
       
       /*$(".tree-view").click(function(){
    	   $(this).next('ul').slideToggle();
       });*/
       
       $(".tree-view").click(function(){
    	   $(this).next('ul').slideToggle();
           $(".usersLi, .billingLi").next('ul').slideUp();
           $(this).find("i.fa-fw").toggleClass("fa-caret-down fa-caret-up");
           $(".usersLi, .billingLi").find("i.fa-fw").removeClass("fa-caret-up").addClass("fa-caret-down");
       });
       $(".backofficeLi").click(function(){
    	   $(this).next('ul').slideToggle();
           $(".tree-view, .billingLi, .associatesLi, .timesheetLi").next('ul').slideUp();
           $(this).find("i.fa-fw").toggleClass("fa-caret-down fa-caret-up");
           $(".tree-view, .billingLi, .associatesLi, .timesheetLi").find("i.fa-fw").removeClass("fa-caret-up").addClass("fa-caret-down");
       });
       $(".timesheetLi").click(function(){
    	   $(this).next('ul').slideToggle();
           $(".tree-view, .billingLi, .associatesLi, .backofficeLi").next('ul').slideUp();
           $(this).find("i.fa-fw").toggleClass("fa-caret-down fa-caret-up");
           $(".tree-view, .billingLi, .associatesLi, .backofficeLi").find("i.fa-fw").removeClass("fa-caret-up").addClass("fa-caret-down");
       });	
       $(".associatesLi").click(function(){
    	   $(this).next('ul').slideToggle();
           $(".tree-view, .billingLi, .timesheetLi, .backofficeLi").next('ul').slideUp();
           $(this).find("i.fa-fw").toggleClass("fa-caret-down fa-caret-up");
           $(".tree-view, .billingLi, .timesheetLi, .backofficeLi").find("i.fa-fw").removeClass("fa-caret-up").addClass("fa-caret-down");
       });
       $(".billingLi").click(function(){
    	   $(this).next('ul').slideToggle();
           $(".tree-view, .associatesLi, .timesheetLi, .backofficeLi").next('ul').slideUp();
           $(this).find("i.fa-fw").toggleClass("fa-caret-down fa-caret-up");
           $(".tree-view, .associatesLi, .timesheetLi, .backofficeLi").find("i.fa-fw").removeClass("fa-caret-up").addClass("fa-caret-down");
       });
       
    });   