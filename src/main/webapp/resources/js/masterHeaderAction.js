	$(document).ready(function(e) {
		$('.side-menu').click(function(){
			$('.side-bar').toggleClass("side-bar-active");
			$('.user-panel').toggleClass("user-panel-active");
			$('.content-wraper').toggleClass("content-wraper-active");
			$('.footer-area').toggleClass("footer-area-active");
			$('.theme_sideMenu').toggleClass('themeSideMenu_Active');
			$('.cusFieldHeadFixed').toggleClass('cusFieldHeadFixedAct');
			$(".collapses").hide();
			$('aside').toggleClass("asideAct");
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
       
       $(".addExpenseApproverLevel").click(function(){
		   $(".expenselevel-second").toggleClass("expenselevel-second-active");
		});
       
       
       $(".tree-view").click(function(){
    	   $(this).next('ul').slideToggle();
           $(".usersLi, .billingLi, .backofficeLi, .timesheetLi, .associatesLi, .expencesLi, .campaignsli, .resourcesli, .payrollli, .performanceli ").next('ul').slideUp();
           $(this).find("i.fa-fw").toggleClass("fa-caret-down fa-caret-up");
           $(".usersLi, .billingLi, .backofficeLi, .timesheetLi, .associatesLi, .expencesLi, .campaignsli, .resourcesli, .payrollli, .performanceli ").find("i.fa-fw").removeClass("fa-caret-up").addClass("fa-caret-down");
       });
       $(".backofficeLi").click(function(){
    	   $(this).next('ul').slideToggle();
           $(".tree-view, .billingLi, .associatesLi, .timesheetLi, .expencesLi, .campaignsli, .resourcesli, .performanceli").next('ul').slideUp();
           $(this).find("i.fa-fw").toggleClass("fa-caret-down fa-caret-up");
           $(".tree-view, .billingLi, .associatesLi, .timesheetLi, .expencesLi, .campaignsli, .resourcesli, .payrollli, .performanceli").find("i.fa-fw").removeClass("fa-caret-up").addClass("fa-caret-down");
       });
       $(".timesheetLi").click(function(){
    	   $(this).next('ul').slideToggle();
           $(".tree-view, .billingLi, .associatesLi, .backofficeLi, .expencesLi, .campaignsli, .resourcesli, .payrollli, .performanceli").next('ul').slideUp();
           $(this).find("i.fa-fw").toggleClass("fa-caret-down fa-caret-up");
           $(".tree-view, .billingLi, .associatesLi, .backofficeLi, .expencesLi, .campaignsli, .resourcesli, .payrollli, .performanceli").find("i.fa-fw").removeClass("fa-caret-up").addClass("fa-caret-down");
       });	
       $(".associatesLi").click(function(){
    	   $(this).next('ul').slideToggle();
           $(".tree-view, .billingLi, .timesheetLi, .backofficeLi, .expencesLi, .campaignsli, .resourcesli, .payrollli, .performanceli").next('ul').slideUp();
           $(this).find("i.fa-fw").toggleClass("fa-caret-down fa-caret-up");
           $(".tree-view, .billingLi, .timesheetLi, .backofficeLi, .expencesLi, .campaignsli, .resourcesli, .payrollli, .performanceli").find("i.fa-fw").removeClass("fa-caret-up").addClass("fa-caret-down");
       });
       $(".billingLi").click(function(){
    	   $(this).next('ul').slideToggle();
           $(".tree-view, .associatesLi, .timesheetLi, .backofficeLi, .expencesLi, .campaignsli, .resourcesli, .payrollli, .performanceli").next('ul').slideUp();
           $(this).find("i.fa-fw").toggleClass("fa-caret-down fa-caret-up");
           $(".tree-view, .associatesLi, .timesheetLi, .backofficeLi, .expencesLi, .campaignsli, .resourcesli, .payrollli, .performanceli").find("i.fa-fw").removeClass("fa-caret-up").addClass("fa-caret-down");
       });
       $(".expencesLi").click(function(){
    	   $(this).next('ul').slideToggle();
           $(".tree-view, .associatesLi, .timesheetLi, .backofficeLi, .billingLi, .campaignsli, .resourcesli, .payrollli, .performanceli").next('ul').slideUp();
           $(this).find("i.fa-fw").toggleClass("fa-caret-down fa-caret-up");
           $(".tree-view, .associatesLi, .timesheetLi, .backofficeLi, .billingLi, .campaignsli, .resourcesli, .payrollli, .performanceli").find("i.fa-fw").removeClass("fa-caret-up").addClass("fa-caret-down");
       });
       
       $(".campaignsli").click(function(){
    	   $(this).next('ul').slideToggle();
           $(".tree-view, .associatesLi, .timesheetLi, .backofficeLi, .billingLi, .expencesLi, .resourcesli, .payrollli, .performanceli").next('ul').slideUp();
           $(this).find("i.fa-fw").toggleClass("fa-caret-down fa-caret-up");
           $(".tree-view, .associatesLi, .timesheetLi, .backofficeLi, .billingLi, .expencesLi, .resourcesli, .payrollli, .performanceli").find("i.fa-fw").removeClass("fa-caret-up").addClass("fa-caret-down");
       });
       
       $(".resourcesli").click(function(){
    	   $(this).next('ul').slideToggle();
           $(".tree-view, .associatesLi, .timesheetLi, .backofficeLi, .billingLi, .expencesLi, .campaignsli, .payrollli, .performanceli").next('ul').slideUp();
           $(this).find("i.fa-fw").toggleClass("fa-caret-down fa-caret-up");
           $(".tree-view, .associatesLi, .timesheetLi, .backofficeLi, .billingLi, .expencesLi, .campaignsli, .payrollli, .performanceli").find("i.fa-fw").removeClass("fa-caret-up").addClass("fa-caret-down");
       });
       
       
       $(".performanceli").click(function(){
    	   $(this).next('ul').slideToggle();
           $(".tree-view, .associatesLi, .timesheetLi, .backofficeLi, .billingLi, .expencesLi, .resourcesli, .payrollli, .campaignsli").next('ul').slideUp();
           $(this).find("i.fa-fw").toggleClass("fa-caret-down fa-caret-up");
           $(".tree-view, .associatesLi, .timesheetLi, .backofficeLi, .billingLi, .expencesLi, .resourcesli, .payrollli, .campaignsli").find("i.fa-fw").removeClass("fa-caret-up").addClass("fa-caret-down");
       });
       
       
    });   
	
	
	
// New Menu Actions
	
	$(document).ready(function(e) {
		$('.menuIcon').click(function(){
			$('.side-bar').toggleClass("side-bar-active");
			$('.content-wraper').toggleClass("content-wraper-active");
			$('.footer-area').toggleClass("footer-area-active");
			$('.theme_sideMenu').toggleClass('themeSideMenu_Active');
			$('.cusFieldHeadFixed').toggleClass('cusFieldHeadFixedAct');
			$(".collapses").hide();
			$('aside').toggleClass("asideAct");
		});
		$('.headProfileIcon').click(function(){
			$("#profilePop").slideToggle(0);
			 $('#settingPopup, #notificationPopup, #orgPopView').hide();
		});
		$('#settingPopId').click(function(){
			$("#settingPopup").slideToggle(0);
			 $('#profilePop, #notificationPopup, #orgPopView').hide();
		});	
		$('#notiPopId').click(function(){
			$("#notificationPopup").slideToggle(0);
			 $('#profilePop, #settingPopup, #orgPopView').hide();
		});	
		$('#orgPopId').click(function(){
			$("#orgPopView").slideToggle(0);
			 $('#profilePop, #settingPopup, #notificationPopup').hide();
		});			
		$('html').click(function(){
			  $('#profilePop, #settingPopup, #notificationPopup, #orgPopView').hide();
		    });
		$('.headProfile, #settingPopId, #settingPopup, #notiPopId, #notificationPopup, #orgPopId, #orgPopView').click(function(e){
			e.stopPropagation();
		});		
		
	 }); 


