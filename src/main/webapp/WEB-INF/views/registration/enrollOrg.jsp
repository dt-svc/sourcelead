<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="resources/js/formvalidator.js"></script>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>


<script>
	$(document).ready(function() {
		document.getElementById("createOrg_form").reset();
	});
</script>

  <!-- Content Area -->
  <section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Org Registration</h2>
    </div>
    <div class="pull-right brud-crum"><a href="dashboard">Home</a> &raquo; <a href="enrolledOrgsList">Orgs List</a> &raquo; Enroll Org </div>
  </section>
  <div class="content-area clearfix">
  <div id="loading"></div>
    <div class="clearfix form-area form-horizontal toggle-div remove-div mt">
      <form:form class="login-form clearfix"  id="createOrg_form"  name="createOrg_form" method="post" > 
      <div class="row">
          <div class="mb col-xs-12 col-sm-12 col-md-6 col-lg-6">
             
            <div class="form-group">
	            <label class="col-md-3">Company<span class="text-danger">&nbsp;*</span></label>
	            <div class="col-md-9">
	              <input type="text" name="companyName" id="companyName"  value="${companyName}" class="form-control" onblur="validateCompanyName()"  field-name="Company Name" data-validation="required validate_Space validate_length length2-250" />
	             <span class="hidden">Company</span>
	            </div>
            </div>
            
            <div class="form-group">
	            <label class="col-md-3">Website<span class="text-danger">&nbsp;*</span></label>
	            <div class="col-md-9">
	              <input type="text" name="website"  value="${website}"   id="companyWebsite"  onblur="validateWebsite();validateCompanyEmail();"  field-name="Company Website" data-validation="required validate_Space validate_domain validate_length length2-100"    class="form-control" />
	            <span class="hidden">Website</span>
	            </div>
            </div>
            
            
             
             <div class="form-group">
	            <label class="col-md-3">Country<span class="text-danger">&nbsp;*</span></label>
	            <div class="col-md-9">
	              <select class="form-control select-bg" name="country" field-name="Country" data-validation="required" value="${country}" >
	                <option value="" >Select Location</option>
	                <option value="Australia">Australia</option>
	                <option value="France">France</option>
	                <option value="India">India</option>
	                <option value="Singapore">Singapore</option>
	                <option value="United Kingdom">United Kingdom</option>
	                <option value="United States">United States</option>
	              </select>
	               <span class="hidden">Country</span>
	            </div>
            </div>
       
          <div class="form-group">
            <label class="col-md-3">Modules<span class="text-danger">&nbsp;*</label>
            <div class="col-md-9 checkbox">
             <ul class="list-unstyled pull-left">
            	<li>
                <label class="mr pull-left">
                <input type="checkbox" id="isRecruiting" onclick="getRecruitmentTypes()" onblur="moduleCheckValidation(this)">
                Recruitment &nbsp;
                </label>
                	<ul class="list-unstyled pull-left tree-checkbox" id="recrutmentTypes" style="display: none;">
                    	<li>
                            <label>
                            <input type="checkbox" id="ats" onblur="subModuleCheckValidation(this)">
                            ATS
                            </label>
		                </li>
                        
                    	<li>
                            <label class="mr">
                                <input type="checkbox" id="vms" onblur="subModuleCheckValidation(this)">
                                VMS
                            </label>
		                </li>
                    </ul>
                </li>
                <p id="subModule" style="display: none;"><font color="red">Sub Modules Required</font></p>
            </ul> 
              <label class="mr">
                <input type="checkbox" id="isTimeSheet" onblur="moduleCheckValidation(this)">
                Timesheet</label>
            </div>
            
            <input type="hidden"  id="moduleValue" name="module" value="${module}" field-name="Module" data-validation="required"/> 
          </div> 
          
          
          </div>
          
          <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
            
            
            <div class="form-group">
	            <label class="col-md-3">Email<span class="text-danger">&nbsp;*</span></label>
	            <div class="col-md-9">
	              <input type="text" name="email" id="email"   value="${email}" onblur="validateCompanyEmail();validateWebsite();getUserDetails();" field-name="Company Email" data-validation="required validate_Space validate_email validate_length length2-100" class="form-control"  />
	            <span class="hidden">Email</span>
	            </div>
            </div>
            
             <div class="form-group">
	            <label class="col-md-3">First Name<span class="text-danger">&nbsp;*</span></label>
	            <div class="col-md-9">
	              <input type="text" class="form-control" id="firstName" name="firstName" value="${firstName}" field-name="First Name" data-validation="required validate_Alphanumeric validate_Space validate_length length2-100" />
	              <span class="hidden">First Name</span>
	            </div>
            </div>
          
            <div class="form-group">
	            <label class="col-md-3">Last Name </label>
	            <div class="col-md-9">
	               <input type="text" name="lastName"  id="lastName"  value="${lastName}" class="form-control" field-name="Last Name" data-validation-optional="true" data-validation="validate_Space validate_Alphanumeric validate_length length2-100"  />
	             <span class="hidden">Last Name</span>
	            </div>
            </div>
          
          
</div>
      </div>
      
      <div class="form-footer clearfix row">
      <div class="col-lg-12">
        <div class="pull-right">
          <input type="submit" value="Register" class="btn btn-primary" id="register_button" onclick="createOrg();">
          <input type="button" value="Cancel" onClick="javascript:location.href = '<c:url value='/enrolledOrgsList'/>'"  class="btn btn-default ml">
        </div>
      </div>
    </div>
    </form:form>
    </div>
    
    <!-- Content Area Ends--> 
  </div>
</section>
      <!-- Content Area Ends--> 

      <jsp:include page="../masterFooter.jsp" />
      
      
      
  <script type="text/javascript">
	function moduleCheckValidation(id){
		if (document.getElementById('isRecruiting').checked || document.getElementById('isTimeSheet').checked) { 
		document.getElementById("moduleValue").value = id.value;
		}else{
			$('#moduleValue').val(''); 
		}
	} 
</script>


<script type="text/javascript">
	function subModuleCheckValidation(id){
		if (document.getElementById('ats').checked || document.getElementById('vms').checked) {
		    document.getElementById("subModule").style.display="none";
		}else{
			document.getElementById("subModule").style.display=""; 
		}
	} 
</script>

 <script type="text/javascript">
function getRecruitmentTypes(){
		if (document.getElementById('isRecruiting').checked) {
		 document.getElementById("recrutmentTypes").style.display="";
		}else{
			document.getElementById("recrutmentTypes").style.display="none";
			document.getElementById("subModule").style.display="none";
			document.getElementById("ats").checked = false;
			document.getElementById("vms").checked = false;
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
            	
        		var frm = $('#createOrg_form').serializeFormJSON();
            	var con = JSON.stringify(frm);
			    con = con.replace(/[{}]/g, ""); 
			    var jsonData = encodeURIComponent(con);
         		var sourceleadUrl ='${sourceleadUrl}';
         		
         		var modules = "";
            	if (document.getElementById('isRecruiting').checked) {
         			modules = modules + "Recruiting"+","
                } 
         		
         		if (document.getElementById('isTimeSheet').checked) {
         			modules = modules + "TimeSheet"+","
                }
         		
         		 var subModules = "";
         		 if (document.getElementById('ats').checked) {
         			subModules = subModules + "ATS"+","
                }
         		
         		if (document.getElementById('vms').checked) {
         			subModules = subModules + "VMS"+","
                }  
         		
         		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
         		 	$.ajax({
          	      type: "POST",
          	      contentType : 'application/json; charset=utf-8',
          	      dataType : 'json',
                	  url: sourceleadUrl+"/companyRegistration?&${_csrf.parameterName}=${_csrf.token}&modules="+modules+"&subModules="+subModules+"&json="+jsonData,
                			  
                			  
                  	success: function(result) {
			        	if((result == 'orgList')){
			        		location.replace(sourceleadUrl+"/enrolledOrgsList");  
			        	}
			        	else if((result == 'Invalid Sub Modules')){
			        		$("#loading").removeAttr("style");
			        		document.getElementById("subModule").style.display="";
			        	}
			        	
			        },  
              }); 
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         //$('#createOrg_form').showHelpOnFocus();
            
         $('body').on('blur', '#createOrg_form', function() {
     		$("#createOrg_form").showHelpOnFocus();
     		$("#createOrg_form").validateOnBlur(false, validationSettings);
     	});
     
	</script>



<script type="text/javascript">
function validateCompanyName(){
	var companyName = document.getElementById("companyName").value;
	var sourceleadUrl ='${sourceleadUrl}';
	 if(companyName != null && companyName != ''){
		 $("#register_button").attr("disabled", "disabled");
		 $( "#register_button" ).addClass('button').val('Processing..');
			 $.ajax({
				    type: "GET",
       	            url: sourceleadUrl+"/validateCompanyName?companyName="+companyName,
			        success: function(data) {
			        	 var result = data;
			        		if(result == "Company Duplicated") {
		        			//Existed company
		    	    		$('#companyNameDiv').find('p.jquery_form_error_message').remove(); 
		    	    		$('input#companyName').attr('class','error form-control');
		    	    		$("input#companyName").closest('b').after("<p class='jquery_form_error_message'> "+companyName+" "+result+"</p>");
		    				$('input#companyName').attr('record-exist','yes');
		    				$('input#companyName').attr('record-exist-errorMsg',companyName+' '+result+''); 
		    				$( "#register_button" ).addClass('sign_btn').val('Register');
		    				$("#register_button").removeAttr("disabled");
			        	}
			        	else{
			        		//New company
			        		$("#register_button").removeAttr("disabled");
				    		$('input#companyName').removeAttr( "class record-exist record-exist-errormsg");
							$('#companyNameDiv').find('p.jquery_form_error_message').remove();
							$('input#companyName').attr('class','valid form-control');
							$( "#register_button" ).removeAttr("style");
							$( "#register_button" ).addClass('sign_btn').val('Register');
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
function validateWebsite(){
      var website = document.getElementById("companyWebsite").value; 
     var sourceleadUrl ='${sourceleadUrl}';
     var sourceleadBLockedwebsites ='${sourceLeadBlockedSocialDomains}';
	 $('input#companyWebsite').removeAttr( "record-exist record-exist-errormsg");
	 if(website != null && website != ''){
		
		 $("#register_button").attr("disabled", "disabled");
		 $( "#register_button" ).addClass('button').val('Processing..');
			 $.ajax({
				    type: "GET",
       	            url: sourceleadUrl+"/ws/validateOrgWebsite?website="+website+"&sourceleadBLockedwebsites="+sourceleadBLockedwebsites,
			        success: function(data) {
			        	 var result = data;
			        		if (result == "Website Duplicated" || result == "Invalid Website") {
		        			//existed website
		    	    		$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove(); 
		    	    		$('input#companyWebsite').attr('class','error form-control');
		    	    		$("input#companyWebsite").closest('b').after("<p class='jquery_form_error_message'> "+website+" "+result+"</p>");
		    				$('input#companyWebsite').attr('record-exist','yes');
		    				$('input#companyWebsite').attr('record-exist-errorMsg',website+' '+result+''); 
		    				$( "#register_button" ).addClass('sign_btn').val('Register');
		    				$("#register_button").removeAttr("disabled");
			        	}
			        	else{
			        		//New WebSite
			        		$("#register_button").removeAttr("disabled");
				    		$('input#companyWebsite').removeAttr( "class record-exist record-exist-errormsg");
							$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
							$('input#companyWebsite').attr('class','valid form-control');
							$( "#register_button" ).removeAttr("style");
							$( "#register_button" ).addClass('sign_btn').val('Register');
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
	var email = document.getElementById("email").value;
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
	        			$("#firstName").attr("disabled", true);
	        		    $("#firstName").val(json[0]);
	        		 }else{
	        			 $("#firstName").attr("disabled", false);
	        			 $("#firstName").val('');
	        		 }
	        		 
	        		 if(json[1] != null && json[1] != ''){
		        			$("#lastName").attr("disabled", true);
		        		    $("#lastName").val(json[1]);
	        		 }else{
	        			    $("#lastName").attr("disabled", false);
	        			    $("#lastName").val('');
	        		 }
	        	}
			 },
	        error: function(e){
	            //alert('Ajax Error: ' + e.value);
	        }
	    });
	}else{
		$("#firstName").attr("disabled", false);
	    $("#firstName").val('');
	    
	    $("#lastName").attr("disabled", false);
	    $("#lastName").val('');
	    
	   
	}
}
</script>

<script type="text/javascript">
function validateCompanyEmail(){
    var websiteValue = document.getElementById("companyWebsite").value;
    var emailValue = document.getElementById("email").value;
    var sourceleadBLockedwebsites ='${sourceLeadBlockedSocialDomains}'; 
    var sourceleadUrl ='${sourceleadUrl}';
		if((websiteValue != null && websiteValue != '') && (emailValue != null && emailValue != '')){
		$.ajax({
		   	type: "GET",
		       url: sourceleadUrl+"/ws/emailAndWebsiteRegistrationValidation?website="+websiteValue+"&sourceleadBLockedwebsites="+sourceleadBLockedwebsites+"&email="+emailValue,
		       success: function(data) {
		    		if(data != null && data != ''){
					     if(data == 'Invalid Email'){
					    	    //Invalid Email
					    	    $('#emailDiv').find('p.jquery_form_error_message').remove(); 
			    	    		$('input#email').attr('class','error form-control');
			    	    		$("input#email").closest('b').after("<p class='jquery_form_error_message'> "+emailValue+" is Invalid</p>");
			    				$('input#email').attr('record-exist','yes');
			    				$('input#email').attr('record-exist-errorMsg',emailValue+' is Invalid'); 
			    				$( "#register_button" ).addClass('sign_btn').val('Register');
			    				$("#register_button").removeAttr("disabled");
					     } 
					     else if(data == 'Existed Email'){
					    	   //Existed Email
					    	    $('#emailDiv').find('p.jquery_form_error_message').remove(); 
			    	    		$('input#email').attr('class','error form-control');
			    	    		$("input#email").closest('b').after("<p class='jquery_form_error_message'> "+emailValue+" is Already Existed</p>");
			    				$('input#email').attr('record-exist','yes');
			    				$('input#email').attr('record-exist-errorMsg',emailValue+' is Already Existed'); 
			    				$( "#register_button" ).addClass('sign_btn').val('Register');
			    				$("#register_button").removeAttr("disabled");
					    	 
					     }
				       	else{
				       		//Valid Email
				       		$("#register_button").removeAttr("disabled");
				    		$('input#email').removeAttr( "class record-exist record-exist-errormsg");
							$('#emailDiv').find('p.jquery_form_error_message').remove();
							$('input#email').attr('class','valid form-control');
							$( "#register_button" ).removeAttr("style");
							$( "#register_button" ).addClass('sign_btn').val('Register');
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


