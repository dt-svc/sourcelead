<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <link href="resources/css/bootstrap-select.min.css" rel="stylesheet">
 <link href="resources/css/jquery-ui.css" rel="stylesheet">
 <script src="resources/js/bootstrap-select.min.js"></script>
<script src="resources/js/formvalidator.js"></script>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>

<link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/chosen.jquery.min.js"></script> 

<!-- <script src="resources/js/masklist.js" type="text/javascript"></script>
<script src="resources/js/jquery.inputmask.js" type="text/javascript"></script> 

<script type="text/javascript">
	$(document).ready(function(){
		masklist();
	});
</script>  -->

<script>
	$(document).ready(function() {
		document.getElementById("createUser_form").reset();
	});
</script>

  <!-- Content Area -->
  <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Create User</h2>
          </div>
          <div class="pull-right brud-crum"><a href="dashboard">Home</a> &raquo; <a href="usersList">Users List</a> &raquo; Create User </div>
        </section>
        <div class="content-area clearfix">
        <div id="loading"></div> 
          
        <div class="clearfix form-area form-horizontal toggle-div remove-div mt">
          <form:form id="createUser_form"  name="createUser_form" method="post">
            <div class="row">
              <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
                <div class="form-group" id="EmployeeIdDiv">
                  <label class="col-md-3">Employee Id</label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" id="EmployeeId" name="employeeId" data-validation="validate_Space validate_length length2-20" data-validation-optional="true" value="" onKeyUp="validateEmployeeId();">
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
                  <label class="col-md-3">Email Id<span class="text-danger">&nbsp;*</span></label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" id="EmailId" name="email" value="" field-name="Email Id" data-validation="required validate_Space validate_length length2-100 validate_email" onKeyUp="validateUserEmail();" onblur="getUserByUserEmail();">
                  </div>
                  </div>
                  
                <div class="form-group" id="FirstNameDiv">
                  <label class="col-md-3">First Name<span class="text-danger">&nbsp;*</span></label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" id="FirstName" name="firstName" value="" field-name="First Name" data-validation="required validate_Space validate_Alphanumeric validate_length length2-100">
                  </div>
                </div>
                
                <div class="form-group">
                  <label class="col-md-3">Last Name</label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" id="lastName" name="lastName" data-validation="validate_Space validate_Alphanumeric validate_length length2-100" data-validation-optional="true" value="">
                  </div>
                </div>
                
               
                 <div class="form-group">
                  <label class="col-md-3">Phone Number</label>
                   <div class="col-md-9">
                    <input type="text" class="form-control" id="phone" name="phoneNumber" value="" data-validation="validate_Space validate_phone validate_length length9-20" data-validation-optional="true">
                    <span class="hidden">Phone Number</span>
                     <input type="hidden" name="country" id="CountryAutocomplete" value ="${SourceLeadWeb_country}" />
                  </div>
                </div> 
                
                <div class="form-group">
                  <label class="col-md-3">Role <span class="text-danger">&nbsp; *</span></label>
                  <div class="col-md-9">
                         <select class="form-control select-bg" id="role" name="roleType" field-name="Role" data-validation="required" onchange="getReportingManager();">
		                        <option value="">-Select Role-</option>
		                        <c:forEach var="option" items="${rolesList}">
									<option value="${option.id}">${option.displayName}</option>
								</c:forEach>
			              </select>
                  </div>
                </div>
               
              <div class="form-group">
                  <label class="col-md-3">Manager</label>
                  <div class="col-md-9">
                      <select class="form-control chosen-select" name="reportingManager" id="reportingManager" field-name="Manager">
                        <option value="">-Select Manager-</option>
                      </select>
                  </div>
            </div>
            
            
                
                 <div class="form-group">
                     <label class="col-md-3">Rate</label>
                     <div class="col-md-9">
                         <div class="row">
		                        <div class="col-md-6">
				                    <input type="text" class="form-control" id="Rate"  name="rate" value="" data-validation="validate_Space validate_float validate_length length2-8" data-validation-optional="true">
				                    <span class="hidden">Rate</span>
		                        </div>
		                        <div class="col-md-6">
				                     <select class="form-control select-bg" id="RatePer" name="ratePer" field-name="Rate Per">
				                        <option value="">-Rate Per-</option>
				                        <option>Hourly</option>
				                        <option>Daily</option>
				                        <option>Weekly</option>
				                        <option>Bi-Weekly</option>
				                        <option>Semi-Monthly</option>
				                        <option>Monthly</option>
				                        <option>Yearly</option>
			                         </select>
		                       </div>
                          </div>
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
                  <input type="button" value="Cancel" onClick="javascript:location.href = '<c:url value='/usersList'/>'"  class="btn btn-default ml">
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
	 var Rate = document.getElementById("Rate").value; 
	 if(Rate != null && Rate != ""){
		 $('#RatePer').removeAttr('data-validation-optional');
		 $('#RatePer').attr("data-validation","required"); 
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
     		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
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
	 var EmployeeId = document.getElementById("EmployeeId").value; 
	 var sourceleadUrl ='${sourceleadUrl}';
	 $('#EmployeeIdDiv').find('p.jquery_form_error_message').remove();
	 if(EmployeeId != null && EmployeeId != ""){
		 $("#create_user").attr("disabled", "disabled");
         $("#create_user" ).addClass('button').val('Processing..');
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateEmployeeId?${_csrf.parameterName}=${_csrf.token}&EmployeeId="+EmployeeId, 
		        success: function(data) {
		        	if(data == true){
		        		  $('#EmployeeIdDiv').find('p.jquery_form_error_message').remove();
						  $("input#EmployeeId").after("<p class='jquery_form_error_message'>"+EmployeeId+" is Already Existed</span>");
						  document.getElementById("EmployeeId").setAttribute('record-exist','yes');
						  document.getElementById("EmployeeId").setAttribute('record-exist-errorMsg',EmployeeId+' is Already Existed'); 
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
				        	$("#FirstName").attr("disabled", true);
				        	$('#FirstNameDiv').find('p.jquery_form_error_message').remove();
			        		
			        		if(json.lastName != null && json.lastName != ''){
			        			$("#lastName").val(json.lastName);
					        	$("#lastName").attr("disabled", true);
			        		}else{
			        			 $("#lastName").attr("disabled", false);
			        			 $("#lastName").val('');
			        		}
		        		}else{
		        			 $("#FirstName").attr("disabled", false);
		        			 $("#FirstName").val('');
		        			 
		        			 $("#lastName").attr("disabled", false);
		        			 $("#lastName").val('');
		        		}
		        		
		        	}
		        },
		    });
	 }
 }
 </script>     
 
<!-- start script for datepicker -->
<script>
  $( function() {
	  
	  $('#DateofJoin').datepicker({
	      dateFormat: 'yy-mm-dd',
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  
  } );
</script>
<!-- end script for datepicker -->

 
<script type="text/javascript">
 function getReportingManager() {
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
	      
	       $('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
		  })
});
</script>
