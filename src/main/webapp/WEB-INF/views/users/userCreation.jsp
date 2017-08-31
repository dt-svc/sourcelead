<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="resources/js/jquery.maskedinput-1.3.min.js"></script>

<!-- <script src="resources/js/masklist.js" type="text/javascript"></script>
<script src="resources/js/jquery.inputmask.js" type="text/javascript"></script> 

<script type="text/javascript">
	$(document).ready(function(){
		masklist();
	});
</script>  -->
<script type="text/javascript">
		window.onload = function () {
			getAssociateList();
			getDepartmentsList();
			maskme();
		 };
	</script>
<script>
	$(document).ready(function() {
		document.getElementById("createUser_form").reset();
	});
</script>
<style type="text/css">
.ratetype p {
    left: 0 !important;
    position: absolute;
    bottom: -14px;
}
</style>

  <!-- Content Area -->
  <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Create User</h2>
          </div>
          <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='/usersList'/>">Users List</a> &raquo; Create User </div>
        </section>
        <div class="content-area clearfix">
        <div id="loading"></div> 
          
        <div class="clearfix form-area form-horizontal toggle-div remove-div mt">
          <form:form id="createUser_form"  name="createUser_form" method="post">
            <div class="row">
              <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
              
                <div class="col-md-12 clearfix">
        <div class="well clearfix">
          
          <div class="col-md-6 er selectUser col-md-offset-3">
          <div class="row">
          <div class="col-md-4 paddingRight">
             <select class="selectpicker pull-left form-control" id="employmentDesignationType" name="employmentDesignationType" onChange="getDesignationType(this);">
                <option value="">All</option>
                 <option>C2C</option>
                 <option>C2C-1099</option>
                 <option>W-2</option>
                 <option>C2H</option>
                 <option>C2H -1099</option>
                 <option>Part Time</option>
                 <option>Full Time</option>
                 <option>Other</option>
             </select>
             </div>
             <div class="col-md-8 paddingLeft">
           <select class="chosen-select pull-left" id="employeementType" name="employeementType" field-name="EmployeementType" onchange="getAssociateUserDetails();" data-validation-optional="true" >
	          <!--   <option value="">-Select Employment Type-</option> -->
	            <option value=" " selected>-Select Associate-</option>
	            <c:forEach var="option" items="${associateList}">
					<option value="${option.id}">${option.displayName}</option>
				</c:forEach>
           </select>
          </div> </div>
          </div>
           <input type="hidden" id="associateId" name="associateId"/>
          </div>
        </div>
              
                <div class="form-group" id="EmployeeIdDiv">
                  <label class="col-md-3">Employee Id</label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" id="EmployeeId" name="employeeId" data-validation="validate_Space validate_length length2-20" data-validation-optional="true" value="" onKeyUp="validateEmployeeId();" onblur="validateEmployeeId();">
                    </div>
                </div>
                <div class="form-group">
                      <label class="col-md-3">Date of Join</label>
                         <div class="col-md-9">
                           <div class="input-group">
                              <input type="text" value="" name="dateofJoin" class="form-control" id="DateofJoin" readonly="readonly">
                              <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                           </div>
                        </div>
                  </div>
                  
                 <div class="form-group" id="EmailIdDiv">
                  <label class="col-md-3">Email Id<span class="text-danger">*</span></label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" id="EmailId" name="email" value="" field-name="Email Id" data-validation="required validate_Space validate_length length2-100 validate_email"  onblur="getUserByUserEmail(); validateUserEmail();" onKeyUp="validateUserEmail();" >
                  </div>
                  </div>
                  
                <div class="form-group" id="FirstNameDiv">
                  <label class="col-md-3">First Name<span class="text-danger">*</span></label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" id="FirstName" name="firstName" pattern="[A-Za-z\s]*" maxlength="100" required>
                  </div>
                </div>
                
                <div class="form-group">
                  <label class="col-md-3">Last Name</label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" id="lastName" name="lastName" pattern="[A-Za-z\s]*" maxlength="100" required>
                  </div>
                </div>
                
               
                 <div class="form-group">
                  <label class="col-md-3">Phone Number</label>
                   <div class="col-md-9">
                    <input type="text" class="form-control" id="phone" name="phoneNumber" data-validation="validate_Space validate_phone validate_length length9-20" data-validation-optional="true">
                    <span class="hidden">Phone Number</span>
                     <input type="hidden" name="country" id="CountryAutocomplete" value ="${SourceLeadWeb_country}" />
                  </div>
                </div> 
                
               <div class="form-group">
                  <label class="col-md-3">Department </label>
                  <div class="col-md-9" id="chosen-role">
                         <select class="form-control chosen-select" id="department" name="department" field-name="Department" onchange="getReportingManager()" data-validation-optional="true" >
		                        
			              </select>
                  </div>
                </div> 
                
                <div class="form-group">
         <label class="col-md-3">Employment type<span class="text-danger"></span></label>
         <div class="col-md-9">
             <select class="selectpicker form-control" id="designationType" name="designationType" onChange="getEmployeementType(this);" field-name="Employment type" >
                <option value="">--Select Employment type--</option>
                 <option>C2C</option>
                 <option>C2C-1099</option>
                 <option>W-2</option>
                 <option>C2H</option>
                 <option>C2H -1099</option>
                 <option>Part Time</option>
                 <option>Full Time</option>
                 <option>Other</option>
             </select>
         </div>
       </div>
                
                <div class="form-group">
                  <label class="col-md-3">Role<span class="text-danger">*</span></label>
                  <div class="col-md-9" id="chosen-role">
                         <select class="form-control select-bg" id="role" name="roleType" field-name="Role" onchange="getReportingManager()" data-validation="required" >
		                        <option value="">-Select Role-</option>
		                        <c:forEach var="option" items="${rolesList}">
									<option value="${option.id}">${option.displayName}</option>
								</c:forEach>
			              </select>
                  </div>
                </div>
               
               
               <div class="form-group designationTypeDiv" id="designationTypeDiv" style="display:none">
            <label class="col-md-3">Employment Other Type</label>
            <div class="col-md-9">
              <input type="text" class="form-control" id="designationOtherType" name="designationOtherType"   field-name="Job Type" value="">
            </div>
          </div>
       
       <span id="hideVendor" style="display:none">
       <div class="form-group">
			<label class="col-md-3" ><span class="pull-left">Vendor</span><span class="text-danger pull-left" id="man1">*</span></label>
			<div class="col-md-9" id="errorvendor">
			     <select  class="chosen-select form-control" name="vendors" id="vendors" placeholder="select Vendor" field-name="Vendor" onchange="getVendorContactList();removeValiationVendor();">
                 </select>
			</div>
		</div>
		
		 <div class="form-group">
			<label class="col-md-3 paddingRight" ><span class="pull-left">Vendor Contact</span><span class="text-danger pull-left" id="man2">*</span></label>
			<div class="col-md-9" id="errorvendorContact">
			     <select  class="chosen-select form-control" name="vendorcontact" id="vendorcontact" field-name="Vendor Contact"  placeholder="select Vendor Contact" onchange="removeValiationVendorContact();">
                 </select>
			</div>
		</div>
		</span>
               
              <div class="form-group">
                  <label class="col-md-3">Manager</label>
                  <div class="col-md-9">
                      <select class="form-control chosen-select" name="reportingManager" id="reportingManager" field-name="Manager">
                        <option value="">-Select Manager-</option>
                        <%-- <c:forEach var="option" items="${reportingManagersList}">
								<option value="${option.id}">${option.firstName} ${option.lastName} (${option.userType})</option>
						</c:forEach> --%>
                      </select>
                  </div>
            </div>
            
            
                
                 <div class="form-group">
                     <label class="col-md-3">Rate</label>
                     <div class="col-md-9">
                         <div class="input-group er ratetype">
							<div class="input-group-btn tblminwidth-75 displyBlock">
							 <select class="selectpicker" id="currentCurrency" name="currentCurrency">
								<option ${currencyCode  == 'INR' ? 'selected' : ''}>INR</option>
								<option ${currencyCode  == 'AUD' ? 'selected' : ''}>AUD</option>
								<option ${currencyCode  == 'USD' ? 'selected' : ''}>USD</option>
								<option ${currencyCode  == 'GBP' ? 'selected' : ''}>GBP</option>
								<option ${currencyCode  == 'SGD' ? 'selected' : ''}>SGD</option>
								<option ${currencyCode  == 'EUR' ? 'selected' : ''}>EUR</option>
							</select>
							</div>
				                    <input type="text" class="form-control" id="Rate" field-name="Rate" name="rate" value="" data-validation="validate_Space validate_float validate_length length1-8" data-validation-optional="true">
				                    <span class="hidden">Rate</span>
		                        <div class="input-group-btn tblminwidth-75 ratetype">
				                     <select class="selectpicker" id="RatePer" name="RatePer" field-name="Rate Per">
				                        <option value="">-Rate Per-</option>
				                        <option>Hourly</option>
				                        <option>Daily</option>
				                        <option>Weekly</option>
				                        <option>Bi-Weekly</option>
				                        <option>Semi-Monthly</option>
				                        <option>Bi-Monthly</option>
				                        <option>Monthly</option>
				                        <option>Yearly</option>
			                         </select>
		                       </div>
                          </div>
                     </div>
                </div>
                
            <div class="form-group " id = "DayHoursDiv">
                  <label class="col-md-3">Day hours</label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" id="DayHours" placeholder="HH:MM" name="DayHours" data-validation="validate_Space validate_dayHours validate_length length1-9" data-validation-optional="true" value="" onblur="editMe(this);validateDayHours();">
                  </div>
                </div>
            
            <div class="form-group">
		             <label class="col-md-3"></label>
		              <div class="col-md-9">
		                <div class="checkbox checkBox">
		                  <label>
		                    <input type="checkbox" value="Y" name="isMailSent" checked="checked">
		                    Send Mail to User</label>
		                </div>
		                 </div>
               </div>
            </div>
            </div>
                
            <div class="form-footer clearfix row">
              <div class="col-lg-12">
                <div class="pull-right">
                  <input type="button" value="Save" id="create_user" onclick="createUser();" class="btn btn-primary">
                  <input type="button" value="Cancel" onClick="javascript:window.location.href = '<c:url value='/usersList'/>'"  class="btn btn-default ml">
                </div>
              </div>
            </div>
            </form:form>
          </div>
        </div>
      </section>
      <!-- Content Area Ends--> 

      <!-- Footer Area -->
      <jsp:include page="../masterFooter.jsp" />
      <!-- Footer Area -->


<script>
 function employementType(type){
if(type == 'C2C' || type == 'C2C-1099'){
	 document.getElementById("hideVendor").style.display="block";
	 $('#vendors').attr("data-validation","required"); 
	 $('#vendorcontact').attr("data-validation","required"); 
	// $("#vendors").attr("data-validation", "true");
	 // getVendorsByOrgLocationId();
}else{
	 document.getElementById("hideVendor").style.display="none";
	 $('#vendors').removeAttr("data-validation","required"); 
	 $('#vendorcontact').removeAttr("data-validation","required"); 
}
 }
 </script>

<script>
 function getVendorsByOrgLocationId(){
	 var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getVendorsByOrgLocationId?", 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	$('#vendors').empty();
		        	$('#vendors').append('<option value="">--Select Vendor --</option>');
		            for(var i=0;i<json.length;i++){
		            	  var opt = json[i];
		            		$('#vendors').append('<option value='+opt.id+' >'+opt.orgName+'</option>');
		            }
		            $('#vendors').trigger("chosen:updated");
		            
		            
		        }
		 });
 }
 </script>
 <script>
 function getVendorContactList(){
	 var orgRelationID = $('#vendors').val();
	 var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
			    type: "POST",
		        url: sourceleadUrl+"/getOrgSpocsList?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationID+"&type=Vendor",  
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	$('#vendorcontact').empty();
		        	$('#vendorcontact').append('<option value="">--select Vendor contact--</option>');
		             for(var i=0;i<json.length;i++){
		            	 var opt = json[i];
		            	 $('#vendorcontact').append('<option value='+opt.id+' >'+opt.spocName+'</option>');
		            } 
		            $('#vendorcontact').trigger("chosen:updated");
		        }
		 }); 
 }
 </script>

<script>
function getEmployeementType(designationType){
	var existingDesignationType = '${requirement.designationType}';
	if(designationType.value == 'Other'){
		 document.getElementById("designationTypeDiv").style.display="block";
		  $("#designationOtherType").attr("data-validation-optional", "true");
		  $("#designationOtherType").attr("data-validation", "validate_Space validate_length length2-250");
	}else{
		document.getElementById("designationTypeDiv").style.display="none";
		  $("#designationOtherType").attr("data-validation-optional", "true");
		  $("#designationTypeDiv").find('p.jquery_form_error_message').remove();
		  if(existingDesignationType ==null || existingDesignationType == "" || existingDesignationType == "undefined" || existingDesignationType != 'Other'){
			  $("#designationOtherType").val('');
		  }
	}
	
	if(designationType.value == 'C2C'){
		 document.getElementById("hideVendor").style.display="block";
		 $('#vendors').attr("data-validation","required"); 
		 $('#vendorcontact').attr("data-validation","required"); 
		 
		 document.getElementById("man1").style.display="block";
		 document.getElementById("man2").style.display="block";
		 getVendorsByOrgLocationId();
	}else{
		 document.getElementById("hideVendor").style.display="none";
		 $('#vendors').removeAttr("data-validation","required"); 
		 $('#vendorcontact').removeAttr("data-validation","required"); 
	}
	
	 
	if(designationType.value == 'C2C-1099'){
		 document.getElementById("hideVendor").style.display="block";
		 document.getElementById("man1").style.display="none";
		 document.getElementById("man2").style.display="none";
		 $('#vendors').attr("data-validation-optional", "true"); 
		 $('#vendorcontact').attr("data-validation-optional", "true");  
		 $("#errorvendor").find("p.jquery_form_error_message").remove();
			$("#errorvendorContact").find("p.jquery_form_error_message").remove();
			getVendorsByOrgLocationId();
	}else{
		// document.getElementById("hideVendor").style.display="none";
		 $('#vendors').removeAttr("data-validation-optional", "true"); 
		 $('#vendorcontact').removeAttr("data-validation-optional", "true");  
	} 
	
	$('#designationType + p').remove();
}
</script>
      
 <script type="text/javascript">
 function validateDayHours() {
	 var DayHours = document.getElementById("DayHours").value; 
	 $('#DayHoursDiv').find('p.jquery_form_error_message').remove();
	 var military = /^\s*([01]?\d|2[0-3]):[0-5]\d\s*$/i;
     var standard = /^\s*(0?\d|1[0-2]):[0-5]\d(\s+(AM|PM))?\s*$/i;
	
	if(DayHours.match(military) || DayHours.match(standard)){
		
		 $('#DayHoursDiv').find('p.jquery_form_error_message').remove();
		 $('input#DayHours').removeAttr( "record-exist record-exist-errormsg"); 
	/* 	$("input#DayHours").after("<p class='jquery_form_error_message'>Day Hours should less than 24 Hours</span>");
		 document.getElementById("DayHours").setAttribute('record-exist','yes');
		 document.getElementById("DayHours").setAttribute('record-exist-errorMsg','Day Hours should less than 24 Hours');  */
	}else{
		
		$("input#DayHours").after("<p class='jquery_form_error_message'>Day Hours should less than 24 Hours</span>");
		 document.getElementById("DayHours").setAttribute('record-exist','yes');
		 document.getElementById("DayHours").setAttribute('record-exist-errorMsg','Day Hours should less than 24 Hours'); 
	
		/*  $('#DayHoursDiv').find('p.jquery_form_error_message').remove();
		 $('input#DayHours').removeAttr( "record-exist record-exist-errormsg");  */
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
 function createUser() {
	/*  var Rate = document.getElementById("Rate").value; 
	 var RatePer = document.getElementById("RatePer").value; 
	 if(Rate != null && Rate != ""){
		 $('#RatePer').removeAttr('data-validation-optional');
		 $('#RatePer').attr("data-validation","required"); 
	 }else{
		 $('#RatePer').removeAttr('data-validation');
		 $('#RatePer').attr("data-validation-optional","true"); 
	 } if(RatePer != null && RatePer != ""){
		 $('#Rate').removeAttr('data-validation-optional');
		 $('#Rate').attr("data-validation","required"); 
	 }else{
		 $('#Rate').removeAttr('data-validation');
		 $('#Rate').attr("data-validation-optional","true"); 
	 } */
	 
	 	var RatePer = document.getElementById("RatePer").value; 
		if(RatePer != null && RatePer != ""){
			$('#Rate').removeAttr('data-validation-optional');
			$('#Rate').attr("data-validation","required validate_Space validate_float validate_length length1-8"); 
		}else{
			$('#Rate').removeAttr('data-validation');
			$('#Rate').attr("data-validation-optional","true"); 
		}
		 
		var Rate = document.getElementById("Rate").value; 
		if(Rate != null && Rate != ""){
			$('#RatePer').removeAttr('data-validation-optional');
			$('#RatePer').attr("data-validation","required");
			$('#Rate').attr("data-validation","required validate_Space validate_float validate_length length1-8");
		}else{
			$('#RatePer').removeAttr('data-validation');
			$('#RatePer').attr("data-validation-optional","true"); 
		}
	 
	 
	   if ($('#createUser_form').validate(false, validationSettings)){ 
		    var frm = $('#createUser_form').serializeFormJSON();
	        var con = JSON.stringify(frm);
	        con = con.replace(/[{}]/g, "");
	        
	        //It will escape all the special characters
	        var jsonData = encodeURIComponent(con);
	        
	        var sourceleadUrl ='${sourceleadUrl}';
	        $("#create_user").attr("disabled", "disabled");
     		$( "#create_user" ).addClass('button').val('Processing..');
     		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	        $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/createUser?${_csrf.parameterName}=${_csrf.token}&json="+jsonData, 
		        success: function(result) {
		        	if((result == 'usersList')){
		        		location.replace(sourceleadUrl+"/usersList");  
		        	}
			        
		        },
		    });
	        return true;
		
	  }else{
		return false;
	  }
    }
 
   $('body').on('blur', '#createUser_form', function() {
		$("#createUser_form").showHelpOnFocus();
		$("#createUser_form").validateOnBlur(false, validationSettings);  
   });
  
 
</script>  

<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };

        
</script> 


<script type="text/javascript">
 function validateEmployeeId() {
     var EmpId = document.getElementById("EmployeeId").value;
     var sourceleadUrl ='${sourceleadUrl}';
     $('#EmployeeIdDiv').find('p.jquery_form_error_message').remove();
     var con = document.getElementById("EmployeeId").value;
     var EmployeeId = encodeURIComponent(con);
     if(EmployeeId != null && EmployeeId != ""){
         $("#create_user").attr("disabled", "disabled");
        // $("#create_user" ).addClass('button').val('Processing..');
         $.ajax({
                type: "POST",
                url: sourceleadUrl+"/validateEmployeeId?${_csrf.parameterName}=${_csrf.token}&EmployeeId="+EmployeeId,
                success: function(data) {
                    if(data == true){
                          $('#EmployeeIdDiv').find('p.jquery_form_error_message').remove();
                          $("input#EmployeeId").after("<p class='jquery_form_error_message'>"+EmpId+" is Already Existed</span>");
                          document.getElementById("EmployeeId").setAttribute('record-exist','yes');
                          document.getElementById("EmployeeId").setAttribute('record-exist-errorMsg',EmpId+' is Already Existed');
                    }
                    else{
                         $('input#EmployeeId').removeAttr( "record-exist record-exist-errormsg");
                         $('#EmployeeIdDiv').find('p.jquery_form_error_message').remove();
                         $("#create_user" ).addClass('us-btn').val('Save');
                         $("#create_user").removeAttr("disabled");
                    }
                },
            });
     }
 }
 </script> 
 
<script type="text/javascript">
 function validateUserEmail() {
	 var EmailId = document.getElementById("EmailId").value; 
	 $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(EmailId != null && EmailId != ""){
		 $("#create_user").attr("disabled", "disabled");
         //$("#create_user" ).addClass('button').val('Processing..');
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateUserEmail?${_csrf.parameterName}=${_csrf.token}&EmailId="+EmailId, 
		        success: function(data) {
		        	if(data == 'true'){
		        		  $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
						  $("input#EmailId").after("<p class='jquery_form_error_message'>"+EmailId+" is Already Existed</span>");
						  document.getElementById("EmailId").setAttribute('record-exist','yes');
						  document.getElementById("EmailId").setAttribute('record-exist-errorMsg',EmailId+' is Already Existed'); 
		        	}
		        	else{
		        		 $('input#EmailId').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#EmailIdDiv').find('p.jquery_form_error_message').remove(); 
		        		 $("#create_user" ).addClass('us-btn').val('Save');
                         $("#create_user").removeAttr("disabled");
		        	}
		        },
		    });
	 }
 }
 </script>  
 
 <script type="text/javascript">
 function getUserByUserEmail() {
	 var EmailId = document.getElementById("EmailId").value; 
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(EmailId != null && EmailId != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getUserByUserEmail?${_csrf.parameterName}=${_csrf.token}&EmailId="+EmailId, 
		        success: function(data) {
		        	if(data != null && data != ''){
		        		var json = JSON.parse(data);
		        		
		        		if(json.firstName != null && json.firstName != ''){
		        			$("#FirstName").val(json.firstName);
				        	$("#FirstName").attr("readonly", "readonly");
				        	$('#FirstNameDiv').find('p.jquery_form_error_message').remove();
		        		}else{
		        			 $("#FirstName").removeAttr("readonly", "readonly");
		        			 //$("#FirstName").val('');
		        		}
		        		
		        		if(json.lastName != null && json.lastName != ''){
		        			$("#lastName").attr("readonly", "readonly");
		        			$("#lastName").val(json.lastName);
		        		}else{
		        			 $("#lastName").removeAttr("readonly", "readonly");
		        			 //$("#lastName").val('');
		        		}
		        		
		        		if(json.mobilePhone != null && json.mobilePhone != ''){
		        			$("#phone").val(json.mobilePhone);
				        	$("#phone").attr("readonly", "readonly");
		        		}else{
		        			 $("#phone").removeAttr("readonly", "readonly");
		        			 //$("#phone").val('');
		        		}
		        		
		        	}
		        },
		    });
	 }else{
		 $("#FirstName").attr("disabled", false);
		 $("#FirstName").val('');
		 
		 $("#lastName").attr("disabled", false);
		 $("#lastName").val('');
		 
		 $("#phone").attr("disabled", false);
		 $("#phone").val('');
		 
		
		 $("#role").val('');
		 $("#role").trigger("chosen:updated");
		 
		  
		 $("#reportingManager").val('');
		 $("#reportingManager").trigger("chosen:updated");
		 
		 $("#employeementType").val('');
	     $('#employeementType').trigger("chosen:updated");
	     
		 
	 }
 }
 </script>   
 
 <script>
function getAssociateUserDetails(){
	var role = document.getElementById("role").value;
	 var reportingManager = document.getElementById("reportingManager").value;  
	var sourceleadUrl ='${sourceleadUrl}';
	var employeementType = document.getElementById("employeementType").value;
	
	if(employeementType == null || employeementType == ''){
        $("#designationType").val('');
		 $("#designationType").selectpicker('refresh');
		 document.getElementById("hideVendor").style.display="none";
		 document.getElementById("createUser_form").reset();
		
	}
		$.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getActiveAssociateDetails?${_csrf.parameterName}=${_csrf.token}&candidateId="+employeementType, 
	        /* contentType: "application/json; charset=utf-8",
	        dataType: "json", */
	        
	        success: function(data) {
	        	if(data != null && data != ''){
	        		var json = JSON.parse(data);
	        		  $("#associateId").val(json['id']);
	        		  $("#FirstName").val(json['firstName']);
	        		  $("#lastName").val(json['lastName']);
	        		  $("#EmailId").val(json['email']);
	        		  $("#phone").val(json['mobilePhone']);
	        		  $("#designationType").val(json['designationType']);
	        		  if(json['currentRate'] != null && json['currentRate'] !='undefined' && json['currentRate'] !=''){
	        		  $("#Rate").val(json['currentRate']);
// 	        		  $("#RatePer").val(json['currentRateType']);
	        		  $("select[name=RatePer]").val(json['currentRateType']);
		        	  $("#RatePer").selectpicker('refresh');
		        	  
	        		  }else{
	        			  $("#Rate").val('');
	        		  }
	        		  
	        		  $("select[name=currentCurrency]").val(json['currentRateCurrency']);
		        	  $("#currentCurrency").selectpicker('refresh');
		        	  
		        	  
// 	        		  $("#currentCurrency").val(json['currentRateCurrency']);
	        		  
	        		  $("#designationType").selectpicker('refresh');
	        		  employementType(json['designationType']);
	        		  if(json['vendorOrgRelationId'] != null && json['vendorOrgRelationId'] !='undefined' && json['vendorOrgRelationId'] !=''){
	        		  getVendors(json['vendorName'],json['vendorOrgRelationId']);
	        		  vendorContactList(json['vendorContactName'],json['vendorOrgRelationId'],json['vendorSpocId']);
	        		  }
	        		  
	        		  if(json['designationOtherType'] != null && json['designationOtherType'] !='undefined' && json['designationOtherType'] !=''){
	        		  $("#designationOtherType").val(json['designationOtherType']);
	        		  otherType(json['designationType']);
	        		  }
	        		  $("#role").val('');
		        		 $('#role').trigger("chosen:updated");
		        		 $("#reportingManager").val('');
			        	 $('#reportingManager').trigger("chosen:updated");
	        		  /* (val(json['designationType'])); */
	        		  validateUserEmail();
	        	}
			 },
	        error: function(e){
	        }
	    });

	
}
</script>
 <script>
 function otherType(type){
	 if(type == 'Other'){
		 document.getElementById("designationTypeDiv").style.display="block";
		  $("#designationOtherType").attr("data-validation-optional", "true");
		  $("#designationOtherType").attr("data-validation", "validate_Space validate_length length2-250");
	}else{
		document.getElementById("designationTypeDiv").style.display="none";
		  $("#designationOtherType").attr("data-validation-optional", "true");
		  $("#designationTypeDiv").find('p.jquery_form_error_message').remove();
		  if(existingDesignationType ==null || existingDesignationType == "" || existingDesignationType == "undefined" || existingDesignationType != 'Other'){
			  $("#designationOtherType").val('');
		  }
	}
 }
 </script>
 <script>
 function getVendors(vendorName,vendorOrgRelationId){
	 var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getVendorsByOrgLocation?", 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	$('#vendors').empty();
		        	$('#vendors').append('<option value="">--select Vendor --</option>');
		            for(var i=0;i<json.length;i++){
		            	  var opt = json[i];
		            	  if(vendorName != null && vendorName != 'undefined' && vendorName  != ''){
		            		  $('#vendors').append('<option '+ (opt.id == vendorOrgRelationId ? "selected" : '') +' value="'+opt.id+'">'+opt.orgName+'</option>');
			            	}else{
			            		$('#vendors').append('<option value='+opt.id+' >'+opt.orgName+'</option>');	
			            	}
		            }
		            $('#vendors').trigger("chosen:updated");
		            
		        }
		 });
 }
 </script>
 <script>
 function vendorContactList(vendorContactName,vendorOrgRelationId,vendorSpocId){
	 var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
			    type: "POST",
		        url: sourceleadUrl+"/getOrgSpocsList?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+vendorOrgRelationId+"&type=Vendor",  
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	$('#vendorcontact').empty();
		        	$('#vendorcontact').append('<option value="">--select Vendor contact--</option>');
		             for(var i=0;i<json.length;i++){
		            	 var opt = json[i];
		            	 if(vendorContactName != null && vendorContactName != 'undefined' && vendorContactName  != ''){
		            		 $('#vendorcontact').append('<option '+ (opt.id == vendorSpocId ? "selected" : '') +' value="'+opt.id+'">'+opt.spocName+'</option>');
			            	}else{
			            		$('#vendorcontact').append('<option value='+opt.id+' >'+opt.spocName+'</option>');
			            	}
		            	 
		            } 
		            $('#vendorcontact').trigger("chosen:updated");
		        }
		 }); 
 }
 </script>
 
 <script>
 function getAssociateList(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var employeementType = document.getElementById("employeementType").value;
	 $.ajax({
	    	type: "POST",
	    	url: sourceleadUrl+"/getActiveAssociateList?${_csrf.parameterName}=${_csrf.token}&employeementType="+employeementType,
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var output = "";
	        	output = output + "<option value=''>-- Select Associate --</option>"; 
	        	for (var i=0; i<json.length; i++)
				{
	        		var opt = json[i];
	        		var name = '';
	        		if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefined'){
	        			name = opt.firstName+" "+opt.lastName;
	        		}else{
	        			name = opt.firstName;
	        		}
		        		
	        		output = output + "<option value='" +opt.id+ "'>"+name+"</option>";
				}
	        	$("#employeementType").empty();
		        $("#employeementType").append(output);
		        $('#employeementType').trigger("chosen:updated");
	        },
	    });
 }
 
 </script>  
 
 
 <script>
 function getDepartmentsList(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
	    	type: "POST",
	    	url: sourceleadUrl+"/getDepartmentsList?${_csrf.parameterName}=${_csrf.token}",
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var output = "";
	        	output = output + "<option value=''>-- Select Department --</option>"; 
	        	for (var i=0; i<json.length; i++)
				{
	        		var opt = json[i];
	        		var name = '';
	        		name = opt.departmentName;
	        		output = output + "<option value='" +opt.id+ "'>"+name+"</option>";
				}
	        	$("#department").empty();
		        $("#department").append(output);
		        $('#department').trigger("chosen:updated");
	        },
	    });
 }
 
 </script>  
 
 <script>
 function getDesignationType(){
	 var employmentDesignationType = $("#employmentDesignationType").val();
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
	    	type: "POST",
	    	url: sourceleadUrl+"/getActiveAssociateList?${_csrf.parameterName}=${_csrf.token}&employeementType="+employmentDesignationType,
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var output = "";
	        	output = output + "<option value=''>-- Select Associate --</option>"; 
	        	for (var i=0; i<json.length; i++)
				{
	        		var opt = json[i];
	        		var name = '';
	        		if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefined'){
	        			name = opt.firstName+" "+opt.lastName;
	        		}else{
	        			name = opt.firstName;
	        		}
		        		
	        		output = output + "<option value='" +opt.id+ "'>"+name+"</option>";
				}
	        	$("#employeementType").empty();
		        $("#employeementType").append(output);
		        $('#employeementType').trigger("chosen:updated");
	        },
	    });
	 
 }
 
 
 </script>
 
 
<!-- start script for datepicker -->
<script>
  $( function() {
	  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormat = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormat = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormat = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormat = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormat = 'yy-dd-mm'; 
	  }
	  $('#DateofJoin').datepicker({
	      dateFormat: finalDateFormat,
	      changeMonth: true,
	  	  changeYear: true, 
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  
  } );
</script>
<!-- end script for datepicker -->

 
<script type="text/javascript">
 function getReportingManager() {
	 $('#chosen-role').find('p.jquery_form_error_message').remove();
	 var role = document.getElementById("role").value; 
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(role != null && role != "" && role != 'undefined'){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getReportingManager?${_csrf.parameterName}=${_csrf.token}&role="+role, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	output = output + "<option value=''>-- Select Manager --</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"&nbsp;("+opt.userType+")</option>";
					}
		        	$("#reportingManager").empty();
			        $("#reportingManager").append(output);
			        $('#reportingManager').trigger("chosen:updated");
		        },
		    });
	 }else{
		 var output = "";
     	 output = output + "<option value=''>-- Select Manager --</option>"; 
     	 $("#reportingManager").empty();
	     $("#reportingManager").append(output);
	     $('#reportingManager').trigger("chosen:updated");
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
	       $("#reportingManager").chosen();
	       $("#role").chosen();
	       $("#invoiceFrequency").chosen();
	       
	      
	       $('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
		  })
});
</script>
 

<script>
function maskme(){
	$.mask.definitions['H'] = "[0-9]";
    $.mask.definitions['h'] = "[0-9]";
    $.mask.definitions['M'] = "[0-9]";
    $.mask.definitions['m'] = "[0-9]";
    $.mask.definitions['P'] = "[AaPp]";
    $.mask.definitions['p'] = "[Mm]";
    $("#DayHours").mask("Hh:Mm"); 
}
</script>

<script>
function editMe(local){
	
 var id = local.id;
	 var inputValue =  $("#"+id).val();
	 var split = inputValue.split(':');
	 var hours = split[0];
	 var minutes = split[1];
	 var hours1 = hours.charAt(0);
	 var hours2 = hours.charAt(1);
	 var min1 = minutes.charAt(0);
	 var min2 = minutes.charAt(1);
	
	 if(!isNaN(hours1) && isNaN(hours2) && isNaN(min1) && isNaN(min2)){
		 $("#"+id).val("0"+hours1+":00");
	 }
	 if(isNaN(hours1) && !isNaN(hours2) && isNaN(min1) && isNaN(min2)){
		 $("#"+id).val("0"+hours2+":00");
	 }
	 else if(isNaN(hours)  && !isNaN(min1) && isNaN(min2)){
		 $("#"+id).val("00:"+min1+"0");
	 }
	 else if(isNaN(hours)  && isNaN(min1) && !isNaN(min2)){
		 $("#"+id).val("00:0"+min2);
	 }
	 else if(!isNaN(hours)  && isNaN(min1) && isNaN(min2)){
		 $("#"+id).val(hours+":00");
	 }
	 else if(!isNaN(hours)&& !isNaN(min1) && isNaN(min2)){
		 $("#"+id).val(hours+":"+"0"+min1);
	 }
	 else if(!isNaN(hours)&& isNaN(min1) && !isNaN(min2)){
		 $("#"+id).val(hours+":"+"0"+min2);
	 }
	 else if(!isNaN(hours)&& !isNaN(minutes)){
		
		  var hrmin= hours * 60;
		  var totalMin = parseInt(hrmin) + parseInt(minutes);
		  hours = Math.floor(totalMin / 60); 
		  minutes = totalMin % 60;
		  if(hours<=9){
			  hours = "0"+hours;
		  }
		  if(minutes<=9){
			  minutes = "0"+minutes;
		  }
		  $("#"+id).val(hours+":"+minutes);
	 }
	 }
	 </script>

