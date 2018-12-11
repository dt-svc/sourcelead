var buildMasterHeaderPanel = function(){
	
	    var userType = 'Admin'
		var fileUrl = '/resources/images/userprofile-img.png'	
	    var masterNavHtml='';	
			
	    masterNavHtml = masterNavHtml+'<div class="headProfile">';	
	    masterNavHtml = masterNavHtml+'<div class="headProfileIcon"><img class="img-circle" data-id= "userProfileImage" alt="'+userType+'" title="'+loginUser+'" onerror="predefineProfileImage(this);"  src="'+fileUrl+'"> </span> <span class=" hidden pull-left login-user-xs tbldata-dotdot-vsm" title="'+loginUser+'"> '+loginUser+' </div>';
	    masterNavHtml = masterNavHtml+'<div class="headPop" id="profilePop">';
	    masterNavHtml = masterNavHtml+'<div class="headPopInn">';
	    masterNavHtml = masterNavHtml+'<a href="/app/userProfile" title="Profile">';
	    masterNavHtml = masterNavHtml+'<div class="headPopUser clearfix">';
	    masterNavHtml = masterNavHtml+'<div class="pull-left"><img class="img-circle" data-id="userProfileImage" title="'+loginUser+'" alt="'+userType+'" onerror="predefineProfileImage(this);" src="'+fileUrl+'"></div>';
	    masterNavHtml = masterNavHtml+'<div class="pull-right">';
	    masterNavHtml = masterNavHtml+'<p>'+loginUser+'</p>';
	    masterNavHtml = masterNavHtml+'<b>Admin</b>';
	    masterNavHtml = masterNavHtml+'</div>';
	    masterNavHtml = masterNavHtml+'</div>';
	    masterNavHtml = masterNavHtml+'</a>';
	    masterNavHtml = masterNavHtml+'<ul>';
	    masterNavHtml = masterNavHtml+'<li>';
	    masterNavHtml = masterNavHtml+'<a title="Connections"  href="/app/loadConnectedList?status=connected" id="connections"><i class="connectionIC"></i>Connections <span class="label label-warning pull-right">0</span></a>';
	    masterNavHtml = masterNavHtml+'</li>';
	    masterNavHtml = masterNavHtml+'<li><a title="Contacts" href="/app/referRequirementList"><i class="contactsIC"></i>Contacts</a></li>';
					
	    masterNavHtml = masterNavHtml+'<li><a title="Invite Company" data-toggle="modal" id="invitePop"><i class="invitecompanyIC"></i>Invite Company</a></li>';
					
	    masterNavHtml = masterNavHtml+'<li><a href="javascript:void(0)" class="icon-addTask" title="Add Task"><i class="addtaskIC"></i>Add Task</a></li>';
					
	    masterNavHtml = masterNavHtml+'<li><a href="/app/sourceleadTicketList" title="Report Issue"><i class="reporttaskIC"></i>Report Issue</a></li>';
		      			
	    masterNavHtml = masterNavHtml+'<li><a class="logColor" href="/logout" title="Log Out"><i class="logOutIC"></i>Log Out</a></li>';
	    masterNavHtml = masterNavHtml+'</ul>';
	    masterNavHtml = masterNavHtml+'</div>';
	    masterNavHtml = masterNavHtml+'</div>';
	    masterNavHtml = masterNavHtml+'</div>';
	    $("#masterHeaderNav").append(masterNavHtml);
	    sideNewMenuActionListener();
			

		function predefineOrgImage(imageId){
			$(imageId).attr('src','/resources/images/office-image.jpg');
		}

		function predefineProfileImage(imageId){
			$(imageId).attr('src','/resources/images/userprofile-img.png');
		}

		function logoutFormSubmit() {
			alert("hi");
				document.getElementById("logoutForm").submit();
		 }

		function linkFormSubmit(locationId) {
			document.getElementById("logoutForm").action="${sourceleadUrl}/dashboard/"+locationId
			document.getElementById("logoutForm").submit();
		}

		function checkAuth(locationId,roleId,orgSecurity,userId){
			checkLocationAccess(locationId,roleId,orgSecurity,userId);
			 
		}
		function otpClose(){
			 $(".authenticationMask").fadeOut(); 
		}
		$(".loc-alertclose").click(function(){
			 $(".location-alert").fadeOut();
		})

		function getUserDetailsbyroleId(roleId){
			 $.ajax({
				 type : "POST",
			       url: sourceleadUrl+"/ws/getUserDetailsByRoleId?id="+roleId,        
			       success :function(data) {
			    	   if(data != null){
			    		   $("#directtwoStepAuthDiv").empty();
			    		   $("#directtwoStepAuthDiv").append(data);
			    		   $("#gobackBtn").hide();
			    		   $(".otpBackNew").show();
			    		   $("#otpBackNewId").val("switchOtp");
			    		   $("#resendOTP").attr("style","display:none");
			    		   $("#sendOTP").attr("style","display:block");
			    	   }
			       },						     
			}); 
		}

		function checkLocationAccess(locationId,roleId,orgSecurity,userId){
			 $.ajax({
			      type : "GET",
			       url: sourceleadUrl+"/ws/validateWhitelistIp?userRoleId="+roleId,        
			       success :function(result) {
			    	   if(result == true){
			    			 if(('${SourceLeadWeb_isAuthenticated}' == 'Y' && orgSecurity == 'N') || ('${SourceLeadWeb_isAuthenticated}' == 'Y' && orgSecurity == 'Y')){
			    				 location.replace(sourceleadUrl+"/dashboard/"+locationId+"/"+userId);   
			    			 }else if('${SourceLeadWeb_isAuthenticated}' == 'N' && orgSecurity == 'Y'){
			    				 $(".authenticationMask").fadeIn();
			    				 getUserDetailsbyroleId(roleId);
			    			 }else{
			    				 location.replace(sourceleadUrl+"/dashboard/"+locationId+"/"+userId);    
			    			 }
			    		 }else{
			    			 $(".location-alert").fadeIn();
			    			 $(".location-alert").fadeOut(5000);
			    		 }
			    	   
			      },
			  });
		}

			$('.sopcSwith, .authenticationMask').each(function() {
				var $this = $(this);
				$this.on("contextmenu",function(e){
					  return false;
				});
				
				$(document).keydown(function (event) {
					if (event.keyCode == 123) { // Prevent F12
				        return false;
				    }else if (event.ctrlKey && event.shiftKey && event.keyCode == 73) { // Prevent Ctrl+Shift+I        
				        return false;
				    }
				});
				 
			})
			
			
			
		$(document).ready(function(){
			// Settings popup search
		    $("#settingSearch").keyup(function(){
		        var filter = $(this).val(), count = 0;
		        $(".headPopInn ul li").each(function(){
		            if ($(this).text().search(new RegExp(filter, "i")) < 0) {
		                $(this).hide();
		            } else {
		                $(this).show();
		                count++;
		            }
		        });
		        
		        if(count == 0) {
		            $(this).parent().parent().find(".noDate").show();
		        } else {
		            $(this).parent().parent().find(".noDate").hide();
		        }  
		    });
		    
		    // Orgs popup search
		    $("#orgsSearch").keyup(function(){
		        var filter = $(this).val(), count = 0;
		        $(".headPopInn ul li").each(function(){
		            if ($(this).text().search(new RegExp(filter, "i")) < 0) {
		                $(this).hide();
		            } else {
		                $(this).show();
		                count++;
		            }
		        });

		        if(count == 0) {
		            $(this).parent().parent().find(".noDate").show();
		        } else {
		            $(this).parent().parent().find(".noDate").hide();
		        } 
		    
		    });
		});
}


