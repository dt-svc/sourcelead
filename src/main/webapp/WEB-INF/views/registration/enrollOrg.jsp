<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
	$(document).ready(function() {
		initLocationAuto('orgLocation','orgCountry','orgState','orgCity','orgZipCode','orgLatitude','orgLongitude','invite');
		document.getElementById("createOrg_form").reset();
		getTimezonesList();
	});
</script>

  <!-- Content Area -->
  <section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Org Registration</h2>
    </div>
    <div class="pull-right brud-crum"><a href="dashboard">Home</a> &raquo; <a href="enrolledOrgsList">Orgs List</a> &raquo; Org Registration </div>
  </section>
  <div class="content-area clearfix">
  <div id="loading"></div>
    <div class="clearfix form-area form-horizontal toggle-div remove-div mt">
      <form:form class="login-form clearfix"  id="createOrg_form"  name="createOrg_form" method="post" > 
      <div class="row">
          <div class="mb col-xs-12 col-sm-12 col-md-6 col-lg-6">
             
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
	              <input type="text" name="email" id="email"   value="${email}" onblur="validateCompanyEmail();validateWebsite();getUserDetails();" field-name="Email" data-validation="required validate_Space validate_email validate_length length2-100" class="form-control"  />
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
            <label class="col-md-3">Modules<span class="text-danger">&nbsp;*</label>
            <div class="col-md-9 checkBox">
            <div class="clearfix reMar">
	            <c:forEach var="module" items="${modulesList}">
	            <ul class="list-unstyled pull-left">
	                <li>
	                 <label class="mr pull-left">
						<input type="checkbox" id="isRecruiting" value="${module.id}" onclick="getRecruitmentTypes(this)" name="modulesList[]" onblur="moduleCheckValidation(this)">${module.moduleName}
					  </label>
					   <ul class="list-unstyled pull-left tree-checkbox" id="recrutmentTypes" style="display: none;">
                    	<li>
                    	 <c:forEach var="submodules" items="${requirementSubModulesList}">
                    	  <label>
                    		 <input type="checkbox" class="allsubmodules" id="submodules" value="${submodules.id}" name="submodulesList[]" >
                              ${submodules.moduleName}
                         </label><br/>
                    	 </c:forEach>
		                </li>
                    </ul> 
					 </li>
				</ul>
			    </c:forEach>
			    </div>
            <p id="subModule" style="display: none;"><font color="red">Sub Modules Required</font></p>
            <input type="hidden"  id="moduleValue" name="module" value="${module}" field-name="Module" data-validation="required"/> 			    
            </div>
          </div> 
             
          </div>
          
          <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
            
            <div class="form-group">
				<label class="col-md-3">Location<span class="text-danger">&nbsp;*</span></label>
				 <div class="col-md-9" id="locationField">
			          <div class="positionRelative">
				          <input class="form-control positionRelative" id="orgLocation" name="location" placeholder="Enter your Location"  type="text" field-name="Location" data-validation="required"></input>
				          <i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('orgLocation','orgCountry','orgState','orgCity','orgZipCode');resetdate();"></i>
				        </div>
		          </div>
			 </div>
			 
	        
	        <div class="form-group">
	          <label class="col-md-3">Country<span class="text-danger">&nbsp;*</span></label>
	          <div class="col-md-9">
	          <input class="form-control" id="orgCountry" name="country" placeholder="Country" field-name="Country" value ="${country}" type="text" data-validation="required" ></input>
	          </div>
	        </div>
	        
	        <div class="form-group">
	          <label class="col-md-3">State<span class="text-danger">&nbsp;*</span></label>
	          <div class="col-md-9">
	          <input class="form-control" id="orgState" name="state" placeholder="State" type="text" field-name="State" data-validation="required" ></input>
	          </div>
	        </div>
	        
	        <div class="form-group">
	          <label class="col-md-3">City<span class="text-danger">&nbsp;*</span></label>
	          <div class="col-md-9">
	          <input class="form-control" id="orgCity" name="city" placeholder="City" field-name="City" type="text" data-validation="required" ></input>
	          </div>
	        </div>
	        
	        <div class="form-group">
	          <label class="col-md-3">ZipCode</label>
	          <div class="col-md-9">
	          <input class="form-control" id="orgZipCode" name="zipCode" placeholder="Enter your Zip code" type="text"></input>
	          </div>
	        </div>
			
			<div class="form-group">
			  <label class="col-md-3">Address</label>
				<div class="col-md-9">
				 <input type="text" name="address" id="address" field-name="Address" data-validation-optional="true" data-validation="validate_length length5-5000" class="form-control"/>
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
	            <label class="col-md-3">Date Format</label>
	            <div class="col-md-9 dropdown-select">
					<select  id="dateFormat" name="dateFormat">
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
          <input type="submit" value="Register" class="btn btn-primary" id="register_button" onclick="createOrg();">
          <input type="button" value="Cancel" onClick="javascript:window.location.href = '<c:url value='/enrolledOrgsList'/>'"  class="btn btn-default ml">
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
		
		 var modulesList = [];
 		 $("input[name='modulesList[]']:checked").each( function (i) {
 			modulesList[i] = $(this).val();
 		});
 		 
		if (modulesList.length > 0) { 
		document.getElementById("moduleValue").value = id.value;
		}else{
			$('#moduleValue').val(''); 
		}
	} 
</script>



 <script type="text/javascript">
function getRecruitmentTypes(id){
		if (document.getElementById('isRecruiting').checked) {
		 document.getElementById("recrutmentTypes").style.display="";
		}else{
			document.getElementById("recrutmentTypes").style.display="none";
			 $('.allsubmodules').each(function() {
	                this.checked = false;      
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
            	
        		var frm = $('#createOrg_form').serializeFormJSON();
            	var con = JSON.stringify(frm);
			    con = con.replace(/[{}]/g, ""); 
			    var jsonData = encodeURIComponent(con);
         		var sourceleadUrl ='${sourceleadUrl}';
         		
         		 var modulesList = [];
         		 $("input[name='modulesList[]']:checked").each( function (i) {
         			modulesList[i] = $(this).val();
         		});
         		 
         		
         		 var submodulesList = [];
         		 $("input[name='submodulesList[]']:checked").each( function (i) {
         			submodulesList[i] = $(this).val();
         		});
         		
         		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
         		 	$.ajax({
          	      type: "POST",
          	      contentType : 'application/json; charset=utf-8',
          	      dataType : 'json',
                	  url: sourceleadUrl+"/companyRegistration?&${_csrf.parameterName}=${_csrf.token}&modulesList="+modulesList+"&submodulesList="+submodulesList+"&json="+jsonData,
                			  
                			  
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
	var con = document.getElementById("companyName").value;
	var company = document.getElementById("companyName").value;
	var companyName = encodeURIComponent(con);
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
		    	    		$("input#companyName").closest('b').after("<p class='jquery_form_error_message'> "+company+" "+result+"</p>");
		    				$('input#companyName').attr('record-exist','yes');
		    				$('input#companyName').attr('record-exist-errorMsg',company+' '+result+''); 
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
function validateWebsite (){
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
});
</script>
