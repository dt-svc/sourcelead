var masterHeader = new buildMasterHeaderPanel();
 function buildMasterHeaderPanel (){
	
	 this.masterHeaderPanel = function(){
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
	   
	    if(tabAccessArray.indexOf("ROLE_CROWDSOURCING_PROFESSIONAL") > -1){
	    	masterNavHtml = masterNavHtml+'<b>Job Seeker</b>';
	    }
	    else if(tabAccessArray.indexOf("ROLE_CROWDSOURCING_RECRUITER") > -1){
	    	masterNavHtml = masterNavHtml+'<b>Freelance Recruiter</b>';
	    }else{
	    	masterNavHtml = masterNavHtml+'<b>Admin</b>';
	    }
	   
	    masterNavHtml = masterNavHtml+'</div>';
	    masterNavHtml = masterNavHtml+'</div>';
	    masterNavHtml = masterNavHtml+'</a>';
	    masterNavHtml = masterNavHtml+'<ul>';
	    masterNavHtml = masterNavHtml+'<li>';
	    masterNavHtml = masterNavHtml+'<a title="Connections"  href="/app/loadConnectedList?status=connected" id="connections"><i class="connectionIC"></i>Connections <span class="label label-warning pull-right">0</span></a>';
	    masterNavHtml = masterNavHtml+'</li>';
	    masterNavHtml = masterNavHtml+'<li><a title="Contacts" href="/app/referRequirementList"><i class="contactsIC"></i>Contacts</a></li>';
					
	    masterNavHtml = masterNavHtml+'<li><a title="Invite Company" data-toggle="modal" id="invitePop"><i class="invitecompanyIC"></i>Invite Company</a></li>';
					
	    if(tabAccessArray.indexOf("ROLE_CROWDSOURCING_PROFESSIONAL") == -1 && tabAccessArray.indexOf("ROLE_CROWDSOURCING_RECRUITER") == -1 ){
	    	masterNavHtml = masterNavHtml+'<li><a href="javascript:void(0)" class="icon-addTask" title="Add Task"><i class="addtaskIC"></i>Add Task</a></li>';
	    }
		
	    if(roleActionsArray.indexOf("ROLE_SUPPORT") > -1){
	    	 masterNavHtml = masterNavHtml+'<li><a href="/app/sourceleadTicketList" title="Report Issue"><i class="reporttaskIC"></i>Report Issue</a></li>';
	    }
	   
		      			
	    masterNavHtml = masterNavHtml+'<li><a class="logColor" href="/logout" title="Log Out"><i class="logOutIC"></i>Log Out</a></li>';
	    masterNavHtml = masterNavHtml+'</ul>';
	    masterNavHtml = masterNavHtml+'</div>';
	    masterNavHtml = masterNavHtml+'</div>';
	    masterNavHtml = masterNavHtml+'</div>';
	    
	    
	    masterNavHtml = masterNavHtml+'<div class="headNav">';
	    masterNavHtml = masterNavHtml+'<ul>';
	  
	    masterNavHtml = masterNavHtml+'<li><a id="orgPopId" onclick="masterHeader.getUserLinkedOrgsByuserId();" title="Locations"><i class="headNavIcon"></i></a>';
	    masterNavHtml = masterNavHtml+'<div class="headPop headPopnoti" id="orgPopView">';
	    masterNavHtml = masterNavHtml+'<div class="headPopInn">';
	    masterNavHtml = masterNavHtml+'<div id="orgLiksPopup"></div>';
	    masterNavHtml = masterNavHtml+'</div>';
	    masterNavHtml = masterNavHtml+'</div>';
	    masterNavHtml = masterNavHtml+'</li>';
	    
	    
	    if(tabAccessArray.indexOf("Reports")){
	    	masterNavHtml = masterNavHtml+'<li><a title="Reports" href="/app/reportsDashboard"><i class="headNavIcon reporIcon"></i></a></li>';
	    }
	    
	    if(roleActionsArray.indexOf("ROLE_VIEW_CART")){
	    	masterNavHtml = masterNavHtml+'<li>';
	    	masterNavHtml = masterNavHtml+'<a href="/app/candidateMarketplaceCheckout" class="" href="javascript:void(0)">';
	    	masterNavHtml = masterNavHtml+'<b id="headerCartCount" class="headerCartCountShow mt2"></b><i class="shopping-cart" title="Cart"></i>';
	    	masterNavHtml = masterNavHtml+'</a>';
	    	masterNavHtml = masterNavHtml+'</li>';
	    }
	  
	    //dynamically need to get unread mails count
	    if(roleActionsArray.indexOf("ROLE_MAIL_BOX") > -1 || roleActionsArray.indexOf("ROLE_HOTLIST_CAMPAIGN") > -1){
	    	masterNavHtml = masterNavHtml+'<li><a href="/app/mailBox/mails" title="Mailbox" id="mailbox"><i class="headNavIcon maiIcon"></i></a></li>';
	    }
	    
	    //dynamically need to get unread notifications count
	    masterNavHtml = masterNavHtml+'<li>';
	    masterNavHtml = masterNavHtml+'<a id="notiPopId" href="#" title="Alerts" onclick="masterHeader.getTopNotificationsByuserId();"><i class="headNavIcon notiIcon"></i></a>';
	    masterNavHtml = masterNavHtml+'<div class="headPop headPopnoti" id="notificationPopup"></div>';
	    masterNavHtml = masterNavHtml+'</li>';	
     
	    
	    masterNavHtml = masterNavHtml+'</ul>';
	    masterNavHtml = masterNavHtml+'</div>';
		
	    $("#masterHeaderNav").append(masterNavHtml);
	    sideNewMenuActionListener();
	 }
	 
	 this.getTopNotificationsByuserId = function(){
			$.get(sourceleadUrl+"/getNotificationsByuserId?&userId="+userId+"&locationCode="+orgLocationCode+"&orgCode="+orgCode+"&orgLocationId="+locationId, function(json) {
				var data = json.data
				var result = "";
				result = result + '<div class="headPopInn">';
				result = result + '<ul>';
				if(data !=null && data !='' && data !='undefined'){
					for(var i=0;i<data.length;i++){
						var opt = data[i];
						result = result + ' <li>';
						if((opt.actionLink != null && opt.actionLink != '' && opt.actionLink != 'undefined') && ( opt.spocUUID == null || opt.spocUUID == "" || opt.spocUUID == "undefined")){
							if(opt.mode == "read"){
								result = result +'<a class="visitedAnchor" href="'+opt.actionLink+'" title="'+opt.body+'" onclick="linkReadAndUnread(\''+opt.uuId+'\');"><p>'+opt.body+'</p>';
								result = result + ' <u>'+opt.calDays+'</u></a>';
							}else{
								result = result +'<a href="'+opt.actionLink+'" title="'+opt.body+'" onclick="linkReadAndUnread(\''+opt.uuId+'\');"><p>'+opt.body+'</p>';
								result = result + ' <u>'+opt.calDays+'</u></a> ';
							}
						}else if((opt.actionLink != null && opt.actionLink != '' && opt.actionLink != 'undefined') && ( opt.spocUUID != null && opt.spocUUID != "" && opt.spocUUID != "undefined") && (opt.spocInvitationStatus == "Accepted" || opt.spocInvitationStatus == "Rejected") && (opt.orgRelationUUID != null && opt.orgRelationUUID != "" && opt.orgRelationUUID != "undefined") && opt.responderType == "vendor"){
							if(opt.mode == "read"){
								result = result +'<a class="visitedAnchor" href="/app/vendorsList?orgRelationUUID='+opt.orgRelationUUID+'" title="'+opt.body+'"  onclick="linkReadAndUnread(\''+opt.uuId+'\');"><p>'+opt.body+'</p>';
								result = result + ' <u>'+opt.calDays+'</u></a>';
							}else{
								result = result +'<a href="/app/vendorsList?orgRelationUUID='+opt.orgRelationUUID+'" title="'+opt.body+'" onclick="linkReadAndUnread(\''+opt.uuId+'\');"><p>'+opt.body+'</p>';
								result = result + ' <u>'+opt.calDays+'</u></a> ';
							}
						}else if((opt.actionLink != null && opt.actionLink != '' && opt.actionLink != 'undefined') && ( opt.spocUUID != null && opt.spocUUID != "" && opt.spocUUID != "undefined") && (opt.spocInvitationStatus == "Accepted" || opt.spocInvitationStatus == "Rejected") && (opt.orgRelationUUID != null && opt.orgRelationUUID != "" && opt.orgRelationUUID != "undefined")&& opt.responderType == "client"){
							if(opt.mode == "read"){
								result = result +'<a class="visitedAnchor" href="/app/clientDashboard?orgRelationUUID='+opt.orgRelationUUID+'" title="'+opt.body+'" onclick="linkReadAndUnread(\''+opt.uuId+'\');"><p>'+opt.body+'</p>';
								result = result + ' <u>'+opt.calDays+'</u></a> ';
							}else{
								result = result +'<a href="/app/clientDashboard?orgRelationUUID='+opt.orgRelationUUID+'" title="'+opt.body+'" onclick="linkReadAndUnread(\''+opt.uuId+'\');"><p>'+opt.body+'</p>';
								result = result + ' <u>'+opt.calDays+'</u></a> ';
							}
							
						}else{
							if(opt.mode == "read"){
								result = result +'<a class="visitedAnchor" href="#" onclick="linkReadAndUnread(\''+opt.uuId+'\');" title="'+opt.body+'"><p>'+opt.body+'</p>';
								result = result + ' <u>'+opt.calDays+'</u> ';
							}else{
								result = result +'<a href="#"  title="'+opt.body+'" onclick="linkReadAndUnread(\''+opt.uuId+'\');"><p>'+opt.body+'</p>';
								result = result + ' <u>'+opt.calDays+'</u></a> ';
							}
							
						}
						
						result = result + ' </li>';
					} 
					  result = result + '<a class="notiView" href="<c:url value="/app/notificationsList"/>">View All</a> ';
				}else{
					result = result + '<div class="clearfix msg_blank">All Caught UP!!! </div>'
				}
		          result = result + '</ul>';
		          result = result + ' </div>';
		          
				$("#notificationPopup").empty();
		        $("#notificationPopup").append(result);
				
			});
		}
	 
	 this.getUserLinkedOrgsByuserId = function(){
		
		 $.get(sourceleadUrl+"/getLoginUserMultiAccountsList?&userId="+userId, function(data) {
				var result = "";
				result = result + '<div class="headPopInn">';
				result = result+'<div class="headPopSear"><input type="text" class="" id="orgsSearch" placeholder="Search"></div>';
				result = result + '<ul>';
				if(data !=null && data !='' && data !='undefined'){
					for(var i=0;i<data.length;i++){
						var opt = data[i];
						var designation = '';
						var orgAct = '';
						
						if(opt.designation != null && opt.designation != '' && opt.designation != 'undefined'){
							designation = opt.designation;
						}else{
							designation = opt.displayName;
						}
						
						if(opt.orgLocationId == locationId && opt.userId == userId){
							orgAct = 'orgAct';
						}
						
						result = result + '<li>';
						if(opt.displayName != "Professional" && opt.displayName != "Independent Recruiter"){
							result = result + '<a onclick="masterHeader.checkLocationAccess('+opt.orgLocationId+','+opt.id+','+opt.loginSecurity+','+opt.userId+');" class="sopcSwith noPad">';
							result = result + '<div class="clearfix '+orgAct+'">';
							result = result + '<div class="orgProfile"><img onerror="masterHeader.predefineOrgImage(this);" data-id="orgLogoImage" src="'+opt.fileUrl+'"></div>';
							result = result + '<div class="orgDetails">';
							result = result + '<h3>'+opt.orgLocationName+'</h3>';
							result = result + '<h4><i	class="fa fa fa-map-marker"></i>'+opt.country+'</h4>';
							result = result + '<h4><i	class="fa fa-user"></i>'+designation+'</h4>';
							result = result + '</div>';						                   
							result = result + '</div>';
							result = result + '</a>';
						}
						result = result + '</li>';
					}
				} 
			      result = result + '</ul>';
		          result = result + ' </div>';
					$("#orgLiksPopup").empty();
			        $("#orgLiksPopup").append(result); 
	             
					
		 });
		 
	 }
	 
	 this.predefineOrgImage = function(imageId){
			$(imageId).attr('src','/resources/images/office-image.jpg');
		}
	 
	 
	  this.checkLocationAccess = function(locationId,roleId,orgSecurity,userId){
		 $.ajax({
		      type : "GET",
		       url: sourceleadUrl+"/ws/validateWhitelistIp?userRoleId="+roleId,        
		       success :function(result) {
		    	   location.replace(sourceleadUrl+"/dashboard/"+locationId+"/"+userId);  
		    	   /*if(result == true){
		    			 if(orgSecurity == 'Y'){
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
		    		 }*/
		    	   
		      },
		  });
	 }
		
}


