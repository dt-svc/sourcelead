<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
	$(document).ready(function() {
		
		var locationCode = '${locationCode}';
		if(locationCode == null || locationCode == '' || locationCode == 'undefined'){
			var assignedMembers = document.getElementById("orgModules");
			if(orgModules){
				orgModules.href = "#";
				orgModules.className = "disabledLink";
			} 
		}else{
			
        	$("#orgLocationId").val('${orgLocationId}');
       		$("#orgLinceseSubscriptionId").val('${orgLicenseSubscriptionId}');
       		$("#registeredOrgId").val('${registeredOrgId}');
       		
            getCompanyDetails();
		}
	 	
		
		initLocationAuto('orgLocation','orgCountry','orgState','orgCity','orgZipCode','orgLatitude','orgLongitude','invite','address');
		document.getElementById("createOrg_form").reset();
		getTimezonesList();
		$("#tab-a").show()
	    $(".newPrTabsMenu a").click(function(event) {
	        event.preventDefault();
	        $(this).parent().addClass("current");
	        $(this).parent().siblings().removeClass("current");
	        var tab = $(this).attr("href");
	        $(".newPrTabs_inn").not(tab).css("display", "none");
	        $(tab).fadeIn();
	    });
		
		
		
	});
</script>
<script>
function changeCountry(){
	 $("#errCountryMsg").find("p.jquery_form_error_message").remove();
}

</script>
  <!-- Content Area -->
  <section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Org Registration</h2>
    </div>
    <div class="pull-right brud-crum"><a href="${appPath}/dashboard">Home</a> &raquo; <a href="${appPath}/enrolledOrgsList">Orgs List</a> &raquo; Org Registration </div>
  </section>
  
  
  
  <div class="content-area clearfix">
   <div id="loading"></div>
   <div id="enrollOrgLoading"></div>
	   <div class="newPrDetails newPrDetailsPMO"> 
		   <div class="newPrTabs">
		      <ul class="newPrTabsMenu">
				<li class="current"><a href="#tab-a" data-toggle="tab" onclick="getCompanyDetails();">Company Details</a></li>
				<li><a href="#tab-b" data-toggle="tab" id="orgModules" onclick="getOrgModules();">Modules</a></li>
				<div class="clearfix mb"></div>
			  </ul>
			  <div class="clearfix"></div>
			  
			  <div class="project_tabs">
			  <div class="clearfix form-area form-horizontal toggle-div remove-div  newPrTabs_inn mt" id="tab-a">
			  	<form:form class="login-form clearfix"  id="createOrg_form"  name="createOrg_form" method="post" > 
      <div class="row">
          <div class="mb col-xs-12 col-sm-12 col-md-6 col-lg-6">
           <input type="hidden" name="orgLocationId" id="orgLocationId" value=""/>
           <input type="hidden" name="orgLinceseSubscriptionId" id="orgLinceseSubscriptionId" value=""/>
           <input type="hidden" name="registeredOrgId" id="registeredOrgId" value=""/>
           
            <div class="form-group">
	            <label class="col-md-3">Company Name<span class="text-danger">&nbsp;*</span></label>
	            <div class="col-md-9">
	              <input type="text" name="companyName" id="companyName"  value="${companyName}" class="form-control" onblur="validateCompanyName();"  field-name="Company Name" data-validation="required validate_Space validate_length length2-250" />
	             <span class="hidden">Company</span>
	            </div>
            </div>
            
            <div class="form-group">
	            <label class="col-md-3">Website<span class="text-danger">&nbsp;*</span></label>
	            <div class="col-md-9">
	              <input type="text" name="website"  value="${website}"   id="companyWebsite"  onblur="validateWebsite();validateCompanyEmail();"  field-name=" Website" data-validation="required validate_Space validate_domain validate_length length2-100"    class="form-control" />
	            <span class="hidden">Website</span>
	            </div>
            </div>
            
            <div class="form-group">
	            <label class="col-md-3">Email<span class="text-danger">&nbsp;*</span></label>
	            <div class="col-md-9">
	              <input type="text" name="email" id="companyEmail"   value="${email}" onblur="validateCompanyEmail();validateWebsite();getUserDetails();" field-name="Email" data-validation="required validate_Space validate_email validate_length length2-100" class="form-control"  />
	            <span class="hidden">Email</span>
	            </div>
            </div>
            
             <div class="form-group">
	            <label class="col-md-3">First Name<span class="text-danger">&nbsp;*</span></label>
	            <div class="col-md-9">
	              <input type="text" class="form-control" id="firstName" name="firstName" value="${firstName}" field-name="First Name" data-validation="required validate_Alphanumeric validate_Space validate_length length1-100" />
	              <span class="hidden">First Name</span>
	            </div>
            </div>
          
            <div class="form-group">
	            <label class="col-md-3">Last Name </label>
	            <div class="col-md-9">
	               <input type="text" name="lastName"  id="lastName"  value="${lastName}" class="form-control" field-name="Last Name" data-validation-optional="true" data-validation="validate_Space validate_Alphanumeric validate_length length1-100"  />
	             <span class="hidden">Last Name</span>
	            </div>
            </div>
            
            <div class="form-group">
	            <label class="col-md-3">Phone Number </label>
	            <div class="col-md-9">
	               <input type="text" name="mobilePhone"  id="mobilePhone"  value="${mobilePhone}" class="form-control" field-name="Phone Number" data-validation-optional="true" data-validation="validate_Space validate_length length9-20 validate_phone"  />
	             <span class="hidden">Phone Number</span>
	            </div>
            </div>
          
           <div class="form-group">
	          <label class="col-md-3">Org Enrollment<span class="text-danger">&nbsp;*</span></label>
	          <div class="col-md-9">
	               <select class="form-control" name="orgEnrollment" id="orgEnrollment" field-name="Org Enrollment" data-validation="required" >
	                    <option value="">Select Enrollment Type</option>
	                    <c:forEach var="subscription" items="${subscriptionTypeList}">
							<option value="${subscription.id}">${subscription.name}</option>
			  			</c:forEach>
		          </select>
	          </div>
	        </div>
	        
	        <div class="form-group">
	          <label class="col-md-3">No.Of Users<span class="text-danger">&nbsp;*</span></label>
	          <div class="col-md-9">
	               <select class="form-control" name="userSubscription" id="userSubscription" field-name="No.Of Users" data-validation="required" >
	                    <option value="">Select Users</option>
	                    <c:forEach var="userSubscription" items="${userSubscriptionTypeList}">
							<option value="${userSubscription.id}">${userSubscription.name}</option>
			  			</c:forEach>
		          </select>
	          </div>
	        </div>
	        
	        <div class="form-group">
	          <label class="col-md-3">Data Storage<span class="text-danger">&nbsp;*</span></label>
	          <div class="col-md-9">
	               <select class="form-control" name="dataStorage" id="dataStorage" field-name="Data Storage" data-validation="required" >
	                    <option value="">Select Data Storage</option>
	                    <c:forEach var="dataStorage" items="${dataStorageTypeList}">
							<option value="${dataStorage.id}">${dataStorage.name}</option>
			  			</c:forEach>
		          </select>
	          </div>
	        </div>
	        
	        <div class="form-group">
                      <label class="col-md-3">Subscription Date<span class="text-danger">&nbsp;*</span></label>
                         <div class="col-md-9">
                           <div class="input-group" id="subscriptionDateDiv">
                              <input type="text" name="subscriptionDate" class="form-control calendar" id="subscriptionDate" data-validation="required" field-name="Subscription Date" readonly="readonly">
                              <span class="input-group-addon" onClick="clearDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
                           </div>
                        </div>
                  </div>
             
          </div>
          
          <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
            
            <div class="form-group">
				<label class="col-md-3">Address<span class="text-danger">&nbsp;*</span></label>
				 <div class="col-md-9" id="locationField">
			          <div class="positionRelative">
				          <input class="form-control positionRelative" id="orgLocation" name="location" placeholder="Enter your Address"  type="text" field-name="Address" data-validation="required"></input>
				          <i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('orgLocation','orgCountry','orgState','orgCity','orgZipCode','address');resetdate();"></i>
				        </div>
		          </div>
			 </div>
			 
	        <div class="form-group">
			  <label class="col-md-3">Street Address</label>
				<div class="col-md-9">
				 <input type="text" name="address" id="address" field-name="Street Address" data-validation-optional="true" placeholder="Enter your Street Addres" data-validation="validate_length length5-5000" class="form-control"/>
				</div>
			 </div>
			 
			  <div class="form-group">
	          <label class="col-md-3">City<span class="text-danger">&nbsp;*</span></label>
	          <div class="col-md-9">
	          <input class="form-control" id="orgCity" name="city" placeholder="City" field-name="City" type="text" data-validation="required" ></input>
	          </div>
	        </div>
	        
	         <div class="form-group">
	          <label class="col-md-3">State<span class="text-danger">&nbsp;*</span></label>
	          <div class="col-md-9">
	          <input class="form-control" id="orgState" name="state" placeholder="State" type="text" field-name="State" data-validation="required" ></input>
	          </div>
	        </div>
	        
	        <div class="form-group">
	          <label class="col-md-3">ZipCode</label>
	          <div class="col-md-9">
	          <input class="form-control" id="orgZipCode" name="zipCode" placeholder="Enter your Zip code" type="text"></input>
	          </div>
	        </div>
	        
	        <div class="form-group" id="errCountryMsg">
	          <label class="col-md-3">Country<span class="text-danger">&nbsp;*</span></label>
	          <div class="col-md-9">
	               <select class="form-control" name="country" id="orgCountry" field-name="Country" data-validation="required" onchange="changeCountry(); getTimezonesList();" >
		                    <option value="">Select Country</option>
		                    <option value="Australia">Australia</option>
			                <option value="France">France</option>
			                <option value="India">India</option>
			                <option value="Singapore">Singapore</option>
			                <option value="United Kingdom">United Kingdom</option>
			                <option value="United States">United States</option>
		          </select>
	          </div>
	        </div>
	        
	     
			
			
               <div class="form-group">
	            <label class="col-md-3">Time Zone<span class="text-danger">&nbsp;*</span></label>
	            <div class="col-md-9">
					 <select class="chosen-select form-control" data-placeholder="Time Zone" field-name="Time zone" data-validation="required" id="timeZone" name="timeZone">
                     </select>
                     <span class="hidden">Time Zone</span>
				</div>
				</div>
				
				<div class="form-group">
	            <label class="col-md-3">Date Format<span class="text-danger">&nbsp;*</span></label>
	            <div class="col-md-9 dropdown-select">
					<select  id="dateFormat" name="dateFormat" data-validation="required" field-name="Date Format">
						<option value=''>--Select Date Format--</option>
						<option value='dd-MM-yyyy'>dd-MM-yyyy</option>
						<option value='MM-dd-yyyy'>MM-dd-yyyy</option>
						<option value='yyyy-dd-MM'>yyyy-dd-MM</option>
						<option value='yyyy-MM-dd'>yyyy-MM-dd</option>
					</select>
				</div>
		 </div>
		</div>
      </div>
      
      <div class="form-footer clearfix row">
      <div class="col-lg-12">
        <div class="pull-right">
          <input type="submit" value="Save & Continue" class="btn btn-primary" id="register_button" onclick="createOrg();">
          <input type="button" value="Cancel" onClick="javascript:window.location.href = '<c:url value='${appPath}/enrolledOrgsList'/>'"  class="btn btn-default ml">
        </div>
      </div>
    </div>
    </form:form>
			  </div>
			  <jsp:include page="orgModules.jsp" />
			  </div>
			  
		   </div>
	   </div>
  </div>
  
</section>
      <!-- Content Area Ends--> 

<jsp:include page="../masterFooter.jsp" />

<script>
function getCompanyDetails(){
	var orgLocationId = document.getElementById("orgLocationId").value;
	if(orgLocationId){
		$("#enrollOrgLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:1000;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var sourceleadUrl ='${sourceleadUrl}';
		
		  $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/getcompanyRegisteredDetails?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId, 
		        success: function(json) {
		        	
		        	 $("input[name=companyName]").val(json.orgName);
		        	 $("input[name=website]").val(json.website);
		        	 $("input[name=firstName]").val(json.firstName);
		        	 if(json.lastName != null && json.lastName != '' && json.lastName != 'undefined'){
		        		 $("input[name=lastName]").val(json.lastName);
		        	 }
		        	 
		        	 $("input[name=email]").val(json.email);
		        	 
		        	 if(json.phoneNumber != null && json.phoneNumber != '' && json.phoneNumber != 'undefined'){
		        		 $("input[name=mobilePhone]").val(json.phoneNumber);
		        	 }
		        	 
		        	 $("input[name=location]").val(json.location);
		        	 
		        	 if(json.orgLocationAddress != null && json.orgLocationAddress != '' && json.orgLocationAddress != 'undefined'){
		        		 $("input[name=address]").val(json.orgLocationAddress);
		        	 }
		        	
		        	 $("input[name=city]").val(json.city);
		        	 $("input[name=state]").val(json.state);
	      			   
		        	 if(json.zipCode != null && json.zipCode != '' && json.zipCode != 'undefined'){
		        		 $("input[name=zipCode]").val(json.zipCode);
		        	 }
	      			 
		        	 $("#enrollOrgLoading").removeAttr("style");
		        	 $("select[name=country]").val(json.country);
		        	 getTimezonesList();
		        	 $("select[name=timeZone]").val(json.timeZone);
		        	 if(json.dateFormat != null && json.dateFormat != '' && json.dateFormat != 'undefined'){
		        		 $("select[name=dateFormat]").val(json.dateFormat);
		        	 }
		        	 
		        	 $("select[name=orgEnrollment]").val(json.subscriptionType);
		        	 $("select[name=userSubscription]").val(json.userSubscriptionType);
		        	 $("select[name=dataStorage]").val(json.storageSubscriptionType);
		        	 $("input[name=subscriptionDate]").val(json.subscriptionDate);
		        	 $("#subscriptionRate").html(json.subscriptionRate);
		        	 $("#invoiceAmount").val(json.subscriptionRate);
		        	
		        },
		    }); 
	}
}
</script>
    

<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#createOrg_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		e.preventDefault();
        		var formData = getFormData("createOrg_form");
         		var sourceleadUrl ='${sourceleadUrl}';
         		
         		$("#enrollOrgLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
         		 	
          		$.ajax({
          	      type: "POST",
          	    	data: formData,
                	  url: sourceleadUrl+"/companyRegistration?${_csrf.parameterName}=${_csrf.token}",
                  	success: function(orglocations) {
			         	if(orglocations.registrationStatusStr == 'orgList'){
			        		$("#orgLocationId").val(orglocations.id);
			        		$("#orgLinceseSubscriptionId").val(orglocations.orgLinceseSubscriptionId);
			        		$("#registeredOrgId").val(orglocations.orgId);
			        		$("#subscriptionRate").html(orglocations.subscriptionRate);
			        		 $("#invoiceAmount").val(orglocations.subscriptionRate);
			        		
			        		 var orgModules = document.getElementById("orgModules");
							 document.getElementById("orgModules").removeAttribute("class");
							 orgModules.href="#tab-b"; 
							 $("#orgModules").click();
							 $("#enrollOrgLoading").removeAttr('style');
			        	}
			        	else if(orglocations.registrationStatusStr == 'DataMissing'){
			        		showError("Invalid data");
			        		$("#enrollOrgLoading").removeAttr('style');
			        	} 
			        	
			        },  
               });   
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
            
         $('body').on('blur', '#createOrg_form', function() {
     		$("#createOrg_form").showHelpOnFocus();
     		$("#createOrg_form").validateOnBlur(false, validationSettings);
     	});
     
	</script>



<script type="text/javascript">
function validateCompanyName(){
	var con = document.getElementById("companyName").value;
	var company = document.getElementById("companyName").value;
	var companyName = encodeURIComponent(con);
	var sourceleadUrl ='${sourceleadUrl}';
	var orgLocationId = document.getElementById("orgLocationId").value;
	 if(companyName != null && companyName != ''){
		 $("#register_button").attr("disabled", "disabled");
			 $.ajax({
				    type: "GET",
       	            url: sourceleadUrl+"/validateCompanyName?companyName="+companyName+"&orgLocationId="+orgLocationId,
			        success: function(data) {
			        	 var result = data;
			        		if(result == "Company Duplicated") {
		        			//Existed company
		    	    		$('#companyNameDiv').find('p.jquery_form_error_message').remove(); 
		    	    		$('input#companyName').attr('class','error form-control');
		    	    		$("input#companyName").closest('b').after("<p class='jquery_form_error_message'> "+company+" "+result+"</p>");
		    				$('input#companyName').attr('record-exist','yes');
		    				$('input#companyName').attr('record-exist-errorMsg',company+' '+result+''); 
		    				$("#register_button").removeAttr("disabled");
			        	}
			        	else{
			        		//New company
			        		$("#register_button").removeAttr("disabled");
				    		$('input#companyName').removeAttr( "class record-exist record-exist-errormsg");
							$('#companyNameDiv').find('p.jquery_form_error_message').remove();
							$('input#companyName').attr('class','valid form-control');
							$( "#register_button" ).removeAttr("style");
							$( "#register_button" ).click(function() {
							 if ($(this).validate(false, validationSettings)){
							 }
							});
			        	}  
			       },
			        
		   }); 
		 
	 }
}
</script>

<script type="text/javascript">
function validateWebsite (){
      var website = document.getElementById("companyWebsite").value; 
     var sourceleadUrl ='${sourceleadUrl}';
     var sourceleadBLockedwebsites ='${sourceLeadBlockedSocialDomains}';
	 $('input#companyWebsite').removeAttr( "record-exist record-exist-errormsg");
	 if(website != null && website != ''){
		
		 $("#register_button").attr("disabled", "disabled");
			 $.ajax({
				    type: "GET",
       	            url: sourceleadUrl+"/validateOrgWebsite?website="+website+"&sourceleadBLockedwebsites="+sourceleadBLockedwebsites,
			        success: function(data) {
			        	 var result = data;
			        		if (result == "Website Duplicated" || result == "Invalid Website") {
		        			//existed website
		    	    		$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove(); 
		    	    		$('input#companyWebsite').attr('class','error form-control');
		    	    		$("input#companyWebsite").closest('b').after("<p class='jquery_form_error_message'> "+website+" "+result+"</p>");
		    				$('input#companyWebsite').attr('record-exist','yes');
		    				$('input#companyWebsite').attr('record-exist-errorMsg',website+' '+result+''); 
		    				$("#register_button").removeAttr("disabled");
			        	}
			        	else{
			        		//New WebSite
			        		$("#register_button").removeAttr("disabled");
				    		$('input#companyWebsite').removeAttr( "class record-exist record-exist-errormsg");
							$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
							$('input#companyWebsite').attr('class','valid form-control');
							$( "#register_button" ).removeAttr("style");
							$( "#register_button" ).click(function() {
							 if ($(this).validate(false, validationSettings)){
							 }
							});
			        	}  
			       },
			        
		   }); 
	}
}
</script>


<script type="text/javascript">
function getUserDetails(){
	var email = document.getElementById("companyEmail").value;
	var sourceleadUrl ='${sourceleadUrl}';
	if(email){
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/getUserDetails?${_csrf.parameterName}=${_csrf.token}&email="+email,
	        contentType: "application/json; charset=utf-8",
	        dataType: "json",
	        success: function(data) {
	        	if(data != null && data != ''){
	        		var json = JSON.parse(data);
	        		
	        		 if(json[0] != null && json[0] != ''){
	        			$("#firstName").attr("readonly", "readonly");
	        		    $("#firstName").val(json[0]);
	        		 }else{
	        			 $("#firstName").removeAttr("readonly", "readonly");
	        			 $("#firstName").val('');
	        		 }
	        		 
	        		 if(json[1] != null && json[1] != ''){
		        			$("#lastName").attr("readonly", "readonly");
		        		    $("#lastName").val(json[1]);
	        		 }else{
	        			    $("#lastName").removeAttr("readonly", "readonly");
	        			    $("#lastName").val('');
	        		 }
	        		
	        		 if(json[2] != null && json[2] != ''){
		        			$("#mobilePhone").attr("readonly", "readonly");
		        		    $("#mobilePhone").val(json[2]);
	        		 }else{
	        			    $("#mobilePhone").removeAttr("readonly", "readonly");
	        			    $("#mobilePhone").val('');
	        		 }
	        	}
			 },
	        error: function(e){
	            //alert('Ajax Error: ' + e.value);
	        }
	    });
	}else{
		$("#firstName").removeAttr("readonly");
	    $("#firstName").val('');
	    
	    $("#lastName").removeAttr("readonly");
	    $("#lastName").val('');
	    
	   
	}
}
</script>

<script type="text/javascript">
function validateCompanyEmail(){
    var websiteValue = document.getElementById("companyWebsite").value;
    var emailValue = document.getElementById("companyEmail").value;
    var sourceleadBLockedwebsites ='${sourceLeadBlockedSocialDomains}'; 
    var sourceleadUrl ='${sourceleadUrl}';
		if((websiteValue != null && websiteValue != '') && (emailValue != null && emailValue != '')){
		$.ajax({
		   	type: "GET",
		       url: sourceleadUrl+"/emailAndWebsiteRegistrationValidation?website="+websiteValue+"&sourceleadBLockedwebsites="+sourceleadBLockedwebsites+"&email="+emailValue,
		       success: function(data) {
		    		if(data != null && data != ''){
					     if(data == 'Invalid Email'){
					    	    //Invalid Email
					    	    $('#emailDiv').find('p.jquery_form_error_message').remove(); 
			    	    		$('input#companyEmail').attr('class','error form-control');
			    	    		$("input#companyEmail").closest('b').after("<p class='jquery_form_error_message'> "+emailValue+" is Invalid</p>");
			    				$('input#companyEmail').attr('record-exist','yes');
			    				$('input#companyEmail').attr('record-exist-errorMsg',emailValue+' is Invalid'); 
			    				$("#register_button").removeAttr("disabled");
					     } 
					     else if(data == 'Existed Email'){
					    	   //Existed Email
					    	    $('#emailDiv').find('p.jquery_form_error_message').remove(); 
			    	    		$('input#companyEmail').attr('class','error form-control');
			    	    		$("input#companyEmail").closest('b').after("<p class='jquery_form_error_message'> "+emailValue+" is Already Existed</p>");
			    				$('input#companyEmail').attr('record-exist','yes');
			    				$('input#companyEmail').attr('record-exist-errorMsg',emailValue+' is Already Existed'); 
			    				$("#register_button").removeAttr("disabled");
					    	 
					     }
				       	else{
				       		//Valid Email
				       		$("#register_button").removeAttr("disabled");
				    		$('input#companyEmail').removeAttr( "class record-exist record-exist-errormsg");
							$('#emailDiv').find('p.jquery_form_error_message').remove();
							$('input#companyEmail').attr('class','valid form-control');
							$( "#register_button" ).removeAttr("style");
							$( "#register_button" ).click(function() {
							 if ($(this).validate(false, validationSettings)){
							 }
							});
				       	}
			       	}
		    	   
		      },
		       
		}); 
	   }
}
</script>
<script type="text/javascript">
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
               }
               o[this.name].push(this.value || '');
           } else {
               o[this.name] = this.value || '';
           }
       });
       return o;
   };
})(jQuery);
</script>

<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };

        
</script>
<script>

function resetdate() {
	$("select[name = 'dateFormat']").val('');
}
</script> 
<script>
function getTimezonesList(){
	var sourceleadUrl ='${sourceleadUrl}';
	var country = $('#orgCountry').val();
	if(country){
		if(country == "India"){
			$("select[name = 'dateFormat']").val('dd-MM-yyyy');
		}else{
			$("select[name = 'dateFormat']").val('MM-dd-yyyy');
		}
		 $.ajax({
		    	type: "POST",
		    	url : sourceleadUrl+"/getTimezonesList?${_csrf.parameterName}=${_csrf.token}", 
		        success: function(data) {
		        var json = JSON.parse(data);
		        var output = "";
		        output = output + "<option value=''>--Select--</option>";
		        if(country == 'India'){
	    			output = output + "<option value='246' selected> Asia/Kolkata </option>";
	    		}else if(country == 'United States'){
	    			output = output + "<option value='132' selected> America/Chicago </option>";
	    		}else if(country == 'United Kingdom'){
	    			output = output + "<option value='333' selected> Europe/London </option>";
	    		}else if(country == 'Singapore'){
	    			output = output + "<option value='272' selected> Asia/Singapore </option>";
	    		}else if(country == 'France'){
	    			output = output + "<option value='342' selected> Europe/Paris </option>";
	    		}else if(country == 'Australia'){
	    			output = output + "<option value='298' selected> Australia/Adelaide </option>";
	    		}
		        	for (var i=0; i<json.length; i++){
		        		var opt = json[i];
		        		output = output + "<option value='" +opt.id+ "'>"+opt.timeZoneId+"</option>";
					}
		        	$("#timeZone").empty();
		        	$("#timeZone").append(output);
		        	$("#timeZone").trigger("chosen:updated");
		        }
		 });
	}

}
</script>

<script type="text/javascript">
$(function(){
	       var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
	    
           
	       $('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
		  })
		  
		  
		  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	 	  var finalDateFormat = getJSDateFormat(locationDate);
	 	  $('#subscriptionDate').datepicker({
	 	      dateFormat: finalDateFormat,
	 	      changeMonth: true,
	 	  	  changeYear: true, 
	 	  	  minDate : 0,
	 	  	 onClose : function(selected) {
                removeValidation();
			}
	 	  });
});
</script>

<script>
function removeValidation(){
	$('#subscriptionDateDiv').find('p.jquery_form_error_message').remove();
}

function clearDate(){
	$("#subscriptionDate").val("");
	};
</script>

