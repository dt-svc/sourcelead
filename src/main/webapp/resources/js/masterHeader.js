
var masterHeader = new buildMasterHeaderPanel();
 function buildMasterHeaderPanel (){
	
	 this.masterHeaderPanel = function(){
	 //   var userType = 'Admin'
		var fileUrl = '/resources/images/userprofile-img.png'	
	    var masterNavHtml='';	
	    var searchValue = '';
	   // var loginOrgLocationName = "BusinessIntelli Solutions";
	    
	    
	    if(loginUserRole != "ROLE_CROWDSOURCING_PROFESSIONAL"  && loginUserRole != "ROLE_CROWDSOURCING_RECRUITER"){
	    	
	    	masterNavHtml = masterNavHtml+'<span class="logo">';
	    	masterNavHtml = masterNavHtml+'<div class="text-logo">';	
	    	masterNavHtml = masterNavHtml+'<span class="tbldata-dotdot" data-id="orgLogoImage" title="'+loginOrgLocationName+'">'+loginOrgLocationName+'</span>';	
	    	masterNavHtml = masterNavHtml+'</div>';	
	    	masterNavHtml = masterNavHtml+'</span>';
	    	
	    	masterNavHtml = masterNavHtml+'<div class="search-area searchArea_mrgn newHeadSea">';	
	    	masterNavHtml = masterNavHtml+'<form:form name ="search_form" id="search_form">';	
	    	masterNavHtml = masterNavHtml+'<div class="uasrch-selectpicker">';	
	    	masterNavHtml = masterNavHtml+'<select id="searchType" name="searchType" class="selectpicker uasrch pull-left show-menu-arrow" >';	
	    	masterNavHtml = masterNavHtml+'<option '+(searchType == "All" ?"selected":"")+' value="All">All</option>';	
	    	 if(tabAccessArray.indexOf("Associates")  > -1){
	    		 masterNavHtml = masterNavHtml+'<option  '+(searchType == "Users" ?"selected":"")+' value="Users">Associates</option>';	
	    	 }
	    	 
	    	 if(tabAccessArray.indexOf("Jobs")  > -1){
	    		 masterNavHtml = masterNavHtml+'<option  '+(searchType == "Requirement" ?"selected":"")+' value="Users">Requirement</option>';
	    	 }
	    	 
	    	 if(tabAccessArray.indexOf("Candidates")  > -1){
	    		 masterNavHtml = masterNavHtml+'<option  '+(searchType ==  "Candidate" ?"selected":"")+' value="Candidate">Candidates</option>';
	    	 }
	    	 
	    	 if(tabAccessArray.indexOf("Clients")  > -1){
	    		 masterNavHtml = masterNavHtml+'<option  '+(searchType  == "Client" ?"selected":"")+' value="Client">Clients</option>';
	    	 }
	    	 
	    	 if(tabAccessArray.indexOf("Vendors")  > -1){
	    		 masterNavHtml = masterNavHtml+'<option  '+(searchType  == "Vendor" ?"selected":"")+' value="Vendor">Vendors</option>';
	    	 }
	    	
	    	 masterNavHtml = masterNavHtml+'</select>';	
	    	 masterNavHtml = masterNavHtml+'</div>';	
					
	    	 masterNavHtml = masterNavHtml+'<div class="pull-left universalSrch">';	
	    	 masterNavHtml = masterNavHtml+'<input type="text" id="searchKey" name="searchKey" class="form-control input-sm" placeholder="Search" onkeypress="handle(event);" value="'+searchValue+'"/>';	
	    	 masterNavHtml = masterNavHtml+'<button type="button" class="srcbutton" value="searchValue" id="searchValue" onclick="masterHeader.universalSearch();" ></button>';	
	    	 masterNavHtml = masterNavHtml+'</div>';	
	    	 masterNavHtml = masterNavHtml+'</form:form>';	
	    	 masterNavHtml = masterNavHtml+'</div>';	
	    }
	    
			
	    masterNavHtml = masterNavHtml+'<div class="headProfile">';	
	    masterNavHtml = masterNavHtml+'<div class="headProfileIcon"><img class="img-circle" data-id= "userProfileImage" alt="'+userType+'" title="'+loginUser+'" onerror="masterHeader.predefineProfileImage(this);"  src="'+userImgUrl+'"> </span> <span class=" hidden pull-left login-user-xs tbldata-dotdot-vsm" title="'+loginUser+'"> '+loginUser+' </div>';
	    masterNavHtml = masterNavHtml+'<div class="headPop" id="profilePop">';
	    masterNavHtml = masterNavHtml+'<div class="headPopInn">';
	    masterNavHtml = masterNavHtml+'<a href="/app/userProfile" title="Profile">';
	    masterNavHtml = masterNavHtml+'<div class="headPopUser clearfix">';
	    masterNavHtml = masterNavHtml+'<div class="pull-left"><img class="img-circle" data-id="userProfileImage" title="'+loginUser+'" alt="'+userType+'" onerror="masterHeader.predefineProfileImage(this);" src="'+userImgUrl+'"></div>';
	    masterNavHtml = masterNavHtml+'<div class="pull-right">';
	    masterNavHtml = masterNavHtml+'<p>'+loginUser+'</p>';
	   
	    if(loginUserRole == "ROLE_CROWDSOURCING_PROFESSIONAL"){
	    	masterNavHtml = masterNavHtml+'<b>Job Seeker</b>';
	    }
	    else if(loginUserRole == "ROLE_CROWDSOURCING_RECRUITER"){
	    	masterNavHtml = masterNavHtml+'<b>Freelance Recruiter</b>';
	    }else{
	    	masterNavHtml = masterNavHtml+'<b>'+userType+'</b>';
	    }
	   
	    masterNavHtml = masterNavHtml+'</div>';
	    masterNavHtml = masterNavHtml+'</div>';
	    masterNavHtml = masterNavHtml+'</a>';
	    masterNavHtml = masterNavHtml+'<ul>';
	    masterNavHtml = masterNavHtml+'<li>';
	    masterNavHtml = masterNavHtml+'<a title="Connections"  href="'+sourceleadUrl+'/loadConnectedList?status=connected" id="connections"><i class="connectionIC"></i>Connections <span class="label label-warning pull-right">0</span></a>';
	    masterNavHtml = masterNavHtml+'</li>';
	    masterNavHtml = masterNavHtml+'<li><a title="Contacts" href="'+sourceleadUrl+'/referRequirementList"><i class="contactsIC"></i>Contacts</a></li>';
					
	    masterNavHtml = masterNavHtml+'<li><a title="Invite Company" data-toggle="modal" id="invitePop"><i class="invitecompanyIC"></i>Invite Company</a></li>';
					
	    if(loginUserRole != "ROLE_CROWDSOURCING_PROFESSIONAL" && loginUserRole != "ROLE_CROWDSOURCING_RECRUITER"){
	    	masterNavHtml = masterNavHtml+'<li><a href="javascript:void(0)" class="icon-addTask" title="Add Task"><i class="addtaskIC"></i>Add Task</a></li>';
	    }
		
	    if(roleActionsArray.indexOf("ROLE_SUPPORT") > -1){
	    	 masterNavHtml = masterNavHtml+'<li><a href="'+sourceleadUrl+'/sourceleadTicketList" title="Report Issue"><i class="reporttaskIC"></i>Report Issue</a></li>';
	    }
	   
		      			
	    masterNavHtml = masterNavHtml+'<li><a class="logColor" onclick="masterHeader.logoutFormSubmit();" title="Log Out"><i class="logOutIC"></i>Log Out</a></li>';
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
	    
	    if(tabAccessArray.indexOf("ROLE_ADD_TICKET")  > -1 || tabAccessArray.indexOf("ROLE_VIEW_TICKET")){
	    	masterNavHtml = masterNavHtml+'<li><a title="Ticket Management" href="'+sourceleadUrl+'/ticketManagementList"><i class="ticketMgIC"></i></a></li>';
	    }
	    if(tabAccessArray.indexOf("Reports")  > -1){
	    	masterNavHtml = masterNavHtml+'<li><a title="Reports" href="'+sourceleadUrl+'/reportsDashboard"><i class="headNavIcon reporIcon"></i></a></li>';
	    }
	    
	    if(roleActionsArray.indexOf("ROLE_VIEW_CART")  > -1){
	    	masterNavHtml = masterNavHtml+'<li>';
	    	masterNavHtml = masterNavHtml+'<a href="'+atsUrl+'/candidateMarketplaceCart" class="" href="javascript:void(0)">';
	    	masterNavHtml = masterNavHtml+'<b id="headerCartCount" class="headerCartCountShow mt2"></b><i class="shopping-cart" title="Cart"></i>';
	    	masterNavHtml = masterNavHtml+'</a>';
	    	masterNavHtml = masterNavHtml+'</li>';
	    }
	  
	    //dynamically need to get unread mails count
	    if(roleActionsArray.indexOf("ROLE_MAIL_BOX") > -1 || roleActionsArray.indexOf("ROLE_HOTLIST_CAMPAIGN") > -1){
	    	masterNavHtml = masterNavHtml+'<li><a href="'+atsUrl+'/mailBox/mails" title="Mailbox" id="mailbox"><i class="headNavIcon maiIcon"></i></a></li>';
	    }
	    
	    //dynamically need to get unread notifications count
	    masterNavHtml = masterNavHtml+'<li>';
	    masterNavHtml = masterNavHtml+'<a id="notiPopId" href="#" title="Alerts" onclick="masterHeader.getTopNotificationsByuserId();"><i class="headNavIcon notiIcon"></i></a>';
	    masterNavHtml = masterNavHtml+'<div class="headPop headPopnoti" id="notificationPopup"></div>';
	    masterNavHtml = masterNavHtml+'</li>';	
	    
	    masterNavHtml = masterNavHtml+'<li>';
	    masterNavHtml = masterNavHtml+'<li><a href="'+sourceleadUrl+'/settings" title="Settings"><i class="headNavIcon setiIcon"></i></a></li>';
	    masterNavHtml = masterNavHtml+'</li>';
	    
     
	    
	    masterNavHtml = masterNavHtml+'</ul>';
	    masterNavHtml = masterNavHtml+'</div>';
	    
	    masterNavHtml = masterNavHtml+'<ul class="responIcons clearfix">';
	    masterNavHtml = masterNavHtml+'<li><a><img src="/resources/images/'+country+'.gif" title="'+country+'"></a></li>';
	    masterNavHtml = masterNavHtml+'<li class="srch-btn-xs"> <a title="Search"><i class="fa fa-search"></i></a></li>';
	    masterNavHtml = masterNavHtml+'</ul>';
		
	    $("#masterHeaderNav").append(masterNavHtml);
	    $("#searchType").selectpicker('refresh');
	    sideNewMenuActionListener();
	 }
	 
	 this.getTopNotificationsByuserId = function(){
			$.get(sourceleadUrl+"/getNotificationsByuserId?&userId="+userId+"&locationCode="+orgLocationCode+"&orgCode="+orgCode+"&orgLocationId="+orgLocationId, function(json) {
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
					  result = result + '<a class="notiView" href="/app/notificationsList">View All</a>';
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
		
		 $.get(sourceleadUrl+"/getLoginUserMultiAccountsList?", function(data) {
				var result = "";
				result = result + '<div class="headPopInn">';
				result = result+'<div class="headPopSear"><input type="text" class="" id="orgsSearch" placeholder="Search"></div>';
				result = result + '<ul>';
				if(data !=null && data !='' && data !='undefined'){
					for(var i=0;i<data.length;i++){
						var opt = data[i];
						var designation = '';
						var orgAct = '';
						var city = '';
						
						if(opt.designation != null && opt.designation != '' && opt.designation != 'undefined'){
							designation = opt.designation;
						}else{
							if(opt.displayName != "Professional" && opt.displayName != "Independent Recruiter"){
								designation = opt.displayName;
							}else if(opt.displayName == "Professional"){
								designation = "Job Seeker"
							}else if(opt.displayName == "Independent Recruiter"){
								designation = "Freelance Recruiter"
							}
						}
						
						if(opt.city != null && opt.city != '' && opt.city != 'undefined'){
							city = opt.city;
						}
						
						if(opt.orgLocationId == orgLocationId && opt.userId == userId){
							orgAct = 'orgAct';
						}
						
						result = result + '<li>';
						if(opt.displayName != "Professional" && opt.displayName != "Independent Recruiter"){
							result = result + '<a onclick="masterHeader.checkLocationAccess('+opt.orgLocationId+','+opt.userRoleId+',\''+opt.orgLocationLoginSecurity+'\','+opt.userId+');" class="sopcSwith noPad">';
							result = result + '<div class="clearfix '+orgAct+'">';
							result = result + '<div class="orgProfile"><img onerror="masterHeader.predefineOrgImage(this);" data-id="orgLogoImage" src="'+opt.orgImgUrl+'"></div>';
							result = result + '<div class="orgDetails">';
							result = result + '<h3>'+opt.orgLocationName+'</h3>';
							result = result + '<h4><i	class="fa fa fa-map-marker"></i>'+city+' '+opt.country+'</h4>';
							result = result + '<h4><i	class="fa fa-user"></i>'+designation+'</h4>';
							result = result + '</div>';						                   
							result = result + '</div>';
							result = result + '</a>';
						}
						
						if(opt.displayName == "Professional" || opt.displayName == "Independent Recruiter"){
							result = result + '<a onclick="masterHeader.crowdSourceLocationAccess('+opt.orgLocationId+',\''+opt.userType+'\','+opt.userId+');"  class="sopcSwith noPad">';
							result = result + '<div class="clearfix '+orgAct+'">';
							result = result + '<div class="orgProfile"><img onerror="masterHeader.predefineOrgImage(this);" data-id="orgLogoImage" src="'+opt.orgImgUrl+'"></div>';
							result = result + '<div class="orgDetails">';
							result = result + '<h3>'+opt.orgLocationName+'</h3>';
							result = result + '<h4><i	class="fa fa-user"></i>'+designation+'</h4>';
							result = result + '</div>';						                   
							result = result + '</div>';
							result = result + '</a>';
						}
						
						result = result + '</li>';
					}
				} 
			      result = result + '</ul>';
			      result = result + '<div class="noDate noDateSm" style="display: none;"></div>';
		          result = result + ' </div>';
				  $("#orgLiksPopup").empty();
		          $("#orgLiksPopup").append(result); 
		          //To register Accout search event
		          masterHeader.searchAccout();
	             
					
		 });
		 
	 }
	 
   this.searchAccout = function(){
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
   }
	       
	 
	 this.predefineOrgImage = function(imageId){
			$(imageId).attr('src','/resources/images/office-image.jpg');
		}
	 
	this.crowdSourceLocationAccess = function(orgLocationId,userType,userId){
		 location.replace(sourceleadUrl+"/dashboard/"+orgLocationId+"/"+userType+"/"+userId);  
	}
	 
   this.checkLocationAccess = function(orgLocationId,roleId,orgSecurity,userId){
	   $.ajax({
		      type : "GET",
		       url: sourceleadUrl+"/ws/validateWhitelistIp?userRoleId="+roleId,        
		       success :function(result) {
		    	   if(result == true){
		    			 if((isAuthenticated == 'Y' && orgSecurity == 'N') || (isAuthenticated == 'Y' && orgSecurity == 'Y')){
		    				 location.replace(sourceleadUrl+"/switchLocation?userRoleId="+roleId);    
		    			 }else if(isAuthenticated == 'N' && orgSecurity == 'Y'){
		    				 $(".authenticationMask").fadeIn();
		    				 getUserDetailsbyroleId(roleId);
		    			 }else{
		    				 location.replace(sourceleadUrl+"/switchLocation?userRoleId="+roleId);    
		    			 }
		    		 }else{
		    			 $(".location-alert").fadeIn();
		    			 $(".location-alert").fadeOut(5000);
		    		 }
		    	   
		      },
		  });
	 }
   
   this.getUserDetailsbyroleId = function(roleId){
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
	  
	   this.universalSearch = function(){
	  	 var searchType = document.getElementById('searchType').value;
	  	 searchValue = document.getElementById('searchKey').value;
	  	 if(searchType == "Candidate") {
	  		 window.location.href = sourceleadUrl + "/candidatesList?&searchResult=universalCanddateList&searchValue="+searchValue;
	  	 } else if(searchType == "Associate") {
	  		 window.location.href = sourceleadUrl + "/associatesList?&searchResult=universalCanddateList&searchValue="+searchValue;
	  	 }
	  	 else if(searchType == "Requirement") {
	  		 window.location.href = sourceleadUrl + "/jobs?searchResult=universalRequirementSearchList&searchValue="+searchValue;
	  	 }
	  	 else {
	  		window.location.href = sourceleadUrl + "/universalSearchForm?&searchType="+searchType+"&searchValue="+searchValue;
	  	 }
	  }
	   
	   
	   this.logoutFormSubmit = function(){
		   window.location.href = sourceleadUrl+"/getActionHistoryList/"+userSessionId; 
	   }
	   
	   this.predefineProfileImage = function(imageId){
			$(imageId).attr('src','/resources/images/userprofile-img.png');
		}
		
}
//initLocationAuto  from master header
 
 function initLocationAuto(locationId,countryId,stateId,cityId,zipCodeId,latitudeId,longtitudeId,type,addressId) {
	    initialize(locationId,countryId,stateId,cityId,zipCodeId,type,addressId,latitudeId,longtitudeId);
	    geolocate(latitudeId,longtitudeId);
	}
	function initialize(locationId,countryId,stateId,cityId,zipCodeId,type,addressId,latitudeId,longtitudeId) {
	    if(type == "invite"){
	    	 var input = (document.getElementById(locationId));
	    	    var options = {
	    	    		types: ['geocode'],
	    	        componentRestrictions: {
	    	        	country: ["IN","US","AU","SG","GB","FR"]
	    	        }
	    	    };
	    	    autocomplete = new google.maps.places.Autocomplete(input, options);
	    }else{
	    	autocomplete = new google.maps.places.Autocomplete((document.getElementById(locationId)), {
	    		types: ['geocode'],
	      
	        });
	    }
	   
	  google.maps.event.addListener(autocomplete, 'place_changed', function() {
	  place = autocomplete.getPlace();
	  var address = {};
	  for (var i = 0; i < place.address_components.length; i++) {
	    var addressType = place.address_components[i].types[0];
	    switch (addressType) {
	       case "route":
	    	 address.name=place.address_components[i].long_name;
	    	 break;
	      case "locality":
	        address.city = place.address_components[i].long_name;
	        break;
	      case "administrative_area_level_1":
	        address.state = place.address_components[i].long_name;
	        break;
	      case "country":
	        address.country = place.address_components[i].long_name;
	        break;
	      case "postal_code":
	        address.zipcode = place.address_components[i].long_name;
	        
	    }
	  }
	 
	  $("#"+stateId).val(address.state).change();
	  $("#"+cityId).val(address.city).change();
	  $("#"+zipCodeId).val(address.zipcode).change();
	  $("#"+addressId).val($("#"+locationId).val().replace(", "+$("#"+cityId).val()+", "+$("#"+stateId).val()+", "+$("#"+countryId).val(), ""));
	  $("#"+countryId).val(address.country).change();
	  $("#"+countryId).trigger("chosen:updated");

	  $("#"+latitudeId).val(place.geometry.location.lat());
	  $("#"+longtitudeId).val(place.geometry.location.lng());
	  
	  if(type == "invite"){
		  getTimezonesList();
	  }
	  if(type != "editAssociate"){
	    zipcodeMask(address.country,zipCodeId);	
	  }
	});
	}
	function geolocate(latitudeId,longtitudeId) {
	    if (navigator.geolocation) {
	        navigator.geolocation.getCurrentPosition(function (position) {
	            var geolocation = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
	            var latitude = position.coords.latitude;
	            var longitude = position.coords.longitude;
	            if(latitudeId != null && latitudeId != '' && latitudeId !='undefined'){
	            	$("#"+latitudeId).val(latitude);
	            }
	            if(longtitudeId != null && longtitudeId != '' && longtitudeId != 'undefined'){
	                $("#"+longtitudeId).val(longitude);
	            }
	            autocomplete.setBounds(new google.maps.LatLngBounds(geolocation, geolocation));
	        });
	    }
	}
	
	//cooling offer period 
	
	function isCoolingOff(requirementId){
		var candidateEmail =   $("input[name=EmailId]").val();
		var sourceleadUrl ='${sourceleadUrl}';
		var reqCoolingOffPeriod = document.getElementById("reqCoolingOffPeriod").value;
		var orgLocationId ='${SourceLeadWeb_orgLocationId}';
		$.ajax({
			type: "POST",
		    url: atsUrl+"/verifyCoolingOffPeriod?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&candidateEmail="+candidateEmail+"&reqCoolingOffPeriod="+reqCoolingOffPeriod+"&orgLocationId="+orgLocationId,
		    success: function(response) {
		    	if (response.response === "AWKWARD") {
		    	    location.href = "/errorPage";
		    	} else if (response.response === "SUCCESS") {
		    		if(response.object === true){
			    		isCoolingOffAlert();
			    		return true;
			    	} else {
			    		return false;
			    	}
		    	} else if (response.indexOf('Sign In') !== -1) {
		    		location.replace(sourceleadUrl);
		    	} else {
		    	    showError("Operation failed, please try again");
		    	}
		    },
		});
	}
	 function tagItBykeyForEmails(e){
			if(e.which == 0 || e.which == 13 || e.which == 9 || e.which == 32 || e.which == 188){
				 $(document).click();
				 return true;
			}
		}


	 function tagItBykeyForSkills(e){
			if(e.which == 0 || e.which == 13 || e.which == 9 || e.which == 188){
				 $(document).click();
				 return true;
			}
		}
	 function preventTabkey(e){
		 if(e.which == 9){
			 e.preventDefault();
		 }
	}
 /*function linkReadAndUnread(uuId){
	 $.ajax({
	      type: "POST",
	  	  url: sourceleadUrl+"/notificationStatusRead?=&uuId="+uuId,
	  			  success :function(data) {
     			 }							     
	}); 
}

 function linkFormSubmit(orgLocationId) {
		document.getElementById("logoutForm").action="http://localhost:8080/app/dashboard/"+orgLocationId // need to be dynamic
		document.getElementById("logoutForm").submit();
	}*/