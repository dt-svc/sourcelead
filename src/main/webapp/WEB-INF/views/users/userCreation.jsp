<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
<link href="https://fonts.googleapis.com/css?family=Roboto:400,300italic,300,400italic,500,700,900" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/register-style.css" rel="stylesheet">
 <link href="resources/css/jquery-ui.css" rel="stylesheet">
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="resources/js/formvalidator.js"></script>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/register-style.css" rel="stylesheet">
  <!-- Content Area -->
  <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Create User</h2>
          </div>
          <div class="pull-right brud-crum">Home &raquo; General Form &raquo; Create User</div>
        </section>
        <div class="content-area clearfix">
          <section class="action-btns clearfix">
            <ul class="pull-right list-unstyled list-inline">
              <li><a href="#" class="faclose"><i aria-hidden="true" class="fa fa-close"></i></a></li>
              <li><a href="#" class="collpase-div"><i aria-hidden="true" class="fa fa-angle-down"></i></a></li>
            </ul>
          </section>
          
          
      <!-- modal dialog box start -->    
        <div class="modal fade" id="addProject" role="dialog">
          <div class="modal-dialog">
           <form:form id="addProject_form"  name="addProject_form" method="post">
             <div class="modal-content" id="newProject">
		          <div class="modal-header">
		            <button type="button" class="close" data-dismiss="modal"><i class="fa fa-close"></i></button>
		            <h4 class="modal-title">Add Project</h4>
		          </div>
          <div class="modal-body clearfix">
            <div class="form-horizontal custom-form clearfix">
              <div class="col-xs-12 col-md-12 col-lg-12">
                <div class="form-group" id="ProjectIdDiv">
                  <label class="col-lg-3">Project Id</label>
                  <div class="col-lg-9">
                    <input type="text" value="" id="ProjectSequeneNumber" name="ProjectSequeneNumber" data-validation="validate_length length2-50" data-validation-optional="true" class="form-control" onblur="validateProjectSequeneNumber();">
                  </div>
                </div>
                
                <div class="form-group">
                  <label class="col-lg-3">Project Name<span class="text-danger">&nbsp; *</span></label>
                  <div class="col-lg-9">
                    <input type="text" value="" id="ProjectName" name="ProjectName" class="form-control" field-name="Project Name" data-validation="required validate_length length2-250">
                  </div>
                </div>
                
                <div class="form-group">
                  <label class="col-md-3"> Date<span class="text-danger">&nbsp; *</span> </label>
		              <div class="col-md-9">
		                <div class="row">
		                  <div class="col-lg-6">
		                    <div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                      <input type="text" class="form-control" value="" id="StartDate" name="StartDate" field-name="Start Date" data-validation="required">
		                    </div>
		                  </div>
		                  <div class="col-lg-6">
		                    <div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                      <input type="text" class="form-control" value="" id="EndDate" name="EndDate" field-name="End Date" data-validation="required">
		                    </div>
		                  </div>
		                  <span class="hidden">Enter Project Id</span> </div>
		              </div>
                 </div>
                 
                <div class="form-group">
              <label class="col-md-3" for="Project Manager">Manager<span class="text-danger">&nbsp; *</span></label>
              <div class="col-md-9">
              <div class="form-icon-min">
                <select class="form-control select-bg" name="level1Manager" id="level1Manager" field-name="Select Manager" data-validation="required">
                  <option value="">-Select Manager-</option>
                      <c:forEach var="option" items="${user_List}">
	                     <option value="${option.id}">${option.firstName}</option>
	                  </c:forEach>
                </select>
                    <span class="btn btn-default formic1 addLevel">Add Level-II</span>
                    </div>
                    <span class="hidden">Select Project Manager</span>
                </div>
            </div>
            <div class="form-group level-second">
              <label class="col-md-3" for="Level-II">Level-II Manager</label>
              <div class="col-md-9">
                <select class="form-control select-bg"  name="level2Manager" id="level2Manager">
                  <option value="">-Select Manager-</option>
                      <c:forEach var="option" items="${user_List}">
	                     <option value="${option.id}">${option.firstName}</option>
	                  </c:forEach>
                </select>
                </div>
            </div>
                
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" id="save_project" class="btn btn-primary" data-dismiss="modal" onclick = "addProject();">Save</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
          </div>
        </div>
        </form:form>
      </div>
    </div>
<!-- modal dialog box end -->          
          
          
          
        <div class="clearfix form-area form-horizontal toggle-div remove-div">
          <form:form id="createUser_form"  name="createUser_form" method="post">
            <div class="row">
              <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                <div class="form-group" id="EmployeeIdDiv">
                  <label class="col-md-3">Employee Id</label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" id="EmployeeId" name="EmployeeId" data-validation="validate_length length2-100" data-validation-optional="true" value="" onblur="validateEmployeeId();">
                    <span class="hidden">Error Text</span></div>
                </div>
                
                <div class="form-group">
                  <label class="col-md-3">First Name<span class="text-danger">&nbsp;*</span></label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" name="FirstName" value="" field-name="First Name" data-validation="required validate_length length2-100">
                    <span class="hidden">First Name</span>
                  </div>
                </div>
                
                <div class="form-group">
                  <label class="col-md-3">Last Name</label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" name="LastName" data-validation="validate_length length2-100" data-validation-optional="true" value="">
                    <span class="hidden">Last Name</span>
                  </div>
                </div>
                
                <div class="form-group" id="EmailIdDiv">
                  <label class="col-md-3">Email Id<span class="text-danger">&nbsp;*</span></label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" id="EmailId" name="EmailId" value="" field-name="Email Id" data-validation="required validate_length length2-100 validate_email" onblur="validateUserEmail();">
                    <span class="hidden">Valid Email</span>
                  </div>
                  </div>
                  
                <div class="form-group">
                  <label class="col-md-3">Phone Number</label>
                   <div class="col-md-9">
                    <input type="text" class="form-control" name="PhoneNumber" value="" data-validation="validate_phone validate_length length9-20" data-validation-optional="true">
                    <span class="hidden">Phone Number</span>
                  </div>
                </div>
                
                 <div class="form-group">
                     <label class="col-md-3">Rate</label>
                     <div class="col-md-9">
                         <div class="row">
		                        <div class="col-md-6">
				                    <input type="text" class="form-control" id="Rate"  name="Rate" value="" data-validation="validate_float validate_length length2-8" data-validation-optional="true">
				                    <span class="hidden">Rate</span>
		                        </div>
		                        <div class="col-md-6">
				                     <select class="form-control select-bg" id="RatePer" name="RatePer" field-name="Rate Per">
				                        <option value="">-Rate Per-</option>
				                        <option>Hourly</option>
				                        <option>Daily</option>
				                        <option>Monthly</option>
				                        <option>Yearly</option>
			                         </select>
				                    <span class="hidden">Rate Per</span>
		                       </div>
                          </div>
                     </div>
                </div>
                
                <div class="form-group">
		             <label class="col-md-3"></label>
		              <div class="col-md-9">
		                <div class="checkbox">
		                  <label>
		                    <input type="checkbox" value="Y" name="isMailSent" checked="checked">
		                    Send Mail to User</label>
		                </div>
		                <span class="hidden">Select Check Box</span> </div>
               </div>
              </div>
              
              <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                  <div class="form-group">
                      <label class="col-md-3">Date of Join</label>
                         <div class="col-md-9">
                           <div class="input-group">
                              <input type="text" value="" name="DateofJoin" class="form-control" id="">
                              <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                           </div>
                        </div>
                  </div>
                <!-- <div class="form-group">
                  <label class="col-md-3">Department</label>
                  <div class="col-md-9">
                    <div class="form-icon">
                      <select class="form-control select-bg">
                        <option>Department</option>
                        <option>Department-2</option>
                        <option>Department-3</option>
                        <option>Department-4</option>
                      </select>
                      <i class="fa fa-plus btn btn-default formic1" data-toggle="modal" data-target="#addDepartment"></i> </div>
                    <span class="hidden">Enter Project Id</span>
                    <div class="modal fade" id="addDepartment" role="dialog">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><i class="fa fa-close"></i></button>
            <h4 class="modal-title">Add Department</h4>
          </div>
          <div class="modal-body clearfix">
            <div class="form-horizontal custom-form clearfix">
              <div class="col-xs-12 col-md-12 col-lg-12">
                <div class="form-group">
                  <label class="col-lg-3">Client Name</label>
                  <div class="col-lg-9">
                    <input type="text" value="" class="form-control">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-lg-3">Client Website</label>
                  <div class="col-lg-9">
                    <input type="text" value="" class="form-control">
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" data-dismiss="modal">Save</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
          </div>
        </div>
      </div>
    </div>
                    </div>
                </div> -->
                <div class="form-group">
                  <label class="col-md-3">Role <span class="text-danger">&nbsp; *</span></label>
                  <div class="col-md-9">
                    <label class="radio-inline">
                      <input type="radio" name="role" value="Admin"  onclick="roleTypeValidation(this);">Admin
                    </label>
                    <label class="radio-inline">
                      <input type="radio" name="role" value="Approver" onclick="roleTypeValidation(this);">Approver/Manager
                    </label>
                    <label class="radio-inline">
                      <input type="radio" name="role" value="Employee" onclick="roleTypeValidation(this);">Employee
                    </label>
                    <span class="hidden">Select Role</span> 
                    <input type="hidden" name="roleType" id="roleValue" field-name="Role" data-validation="required" />
                    
                    
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3">Project</label>
                  <div class="col-md-9">
                    <div class="form-icon">
                      <select class="form-control select-bg" id="proId" name="projectId" onchange="getManagerName();">
                          <option value="">-Select Project-</option>
                        <c:forEach var="option" items="${org_projectsList}">
  						   <option value="${option.id}">${option.projectName}</option>
				        </c:forEach>
                      </select>
                      <i class="fa fa-plus btn btn-default formic1" data-toggle="modal" onclick="flushProject();" data-target="#addProject"></i> </div>
                    <span class="hidden">Select Project</span>
                    
                    
         
        
                    </div>
                </div>
                  
                  <div class="form-group">
                  <label class="col-md-3">Manager</label>
                  <div class="col-md-9">
                      <input type="text" id="managerName" class="form-control" disabled>
              </div>
            </div>
                  <div class="form-group">
                  <label class="col-md-3">Invoice Frequency</label>
                  <div class="col-md-9">
                      <select class="form-control" name="InvoiceFrequency" field-name="Invoice Frequency">
                        <option value="">-Select Frequency-</option>
                        <option>Weekly</option>
                        <option>Bi-Weekly</option>
                        <option>Semi-Monthly</option>
                        <option>Monthly</option>
                      </select>
                      <span class="hidden">Invoice Frequency</span> 
              </div>
            </div>
                  <div class="form-group" id="DayHoursDiv">
                  <label class="col-md-3">Day Hours</label>
                  <div class="col-md-9">
                      <div class="input-group">
                          <input type="text" value="" name="DayHours" class="form-control" id="DayHours" data-validation="validate_int validate_length length1-2" data-validation-optional="true" onblur="validateDayHours();">
                          <span class="input-group-addon"><i class="fa fa-clock-o"></i></span>
                      </div>
                       <span class="hidden">Day Hours</span>
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
      <footer class="footer-area clearfix">
        <div class="copyright"> @ Copyright : Source Lead, All right reserved </div>
        <div class="powered-by"> Powered by: businessintelli.com </div>
      </footer>
      <!-- Footer Area --> 
      
      
<script type="text/javascript">
function roleTypeValidation(id){
	document.getElementById("roleValue").value = id.value;
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
function flushProject(){
	$('#ProjectSequeneNumber').val('');
	 $('#ProjectName').val('');
	 $('#level1Manager').val('');
	 $('#StartDate').val('');
	 $('#EndDate').val('');
	 $('#level2Manager').val('');
	 $(".error").removeClass("error");
	 $('#newProject').find('p.jquery_form_error_message').remove();
}
</script>


<script type="text/javascript">
 function addProject() {
	 
	 var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
		   }; 
	
	
	 /*  ('#save_project').attr('data-dismiss','modal'); */
	 if ($('#addProject_form').validate(false, validationSettings)){ 
		    var frm = $('#addProject_form').serializeFormJSON();
	        var con = JSON.stringify(frm);
	        var sourceleadUrl ='${sourceleadUrl}';
	        $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/addProject?${_csrf.parameterName}=${_csrf.token}&json="+con, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	output = output + "<option value=''>-- Select Project --</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.projectName+"</option>";
					}
		        	$("#proId").empty();
			        $("#proId").append(output);
		        },
		        error: function(e){
		            
		        }
		    });
	       return true;
	  }else{
		 $("#save_project").removeAttr('data-dismiss','modal');
		return false;
	  }
    }

    $('body').on('blur', '#addProject_form', function() {
		$("#addProject_form").showHelpOnFocus();;
		$("#addProject_form").validateOnBlur(false, validationSettings);  
	});
 
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
	 
	var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
		   }; 
	 
	   if ($('#createUser_form').validate(false, validationSettings)){ 
		 var frm = $('#createUser_form').serializeFormJSON();
	        var con = JSON.stringify(frm);
	        var sourceleadUrl ='${sourceleadUrl}';
	        $("#create_user").attr("disabled", "disabled");
     		$( "#create_user" ).addClass('button').val('Processing..');
	        $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/createUser?${_csrf.parameterName}=${_csrf.token}&json="+con, 
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
		$("#createUser_form").showHelpOnFocus();;
		$("#createUser_form").validateOnBlur(false, validationSettings);  
	});
 
</script>   

<script type="text/javascript">
 function getManagerName() {
	 var projectId = document.getElementById("proId").value; 
	 var sourceleadUrl ='${sourceleadUrl}';
	 $("#managerName").val('');
	 if(projectId != null && projectId != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getProjectManager?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	$("#managerName").val(json.approverUserId.firstName+" "+json.approverUserId.lastName);
			        
		        },
		    });
	 }
 }
</script> 

<script type="text/javascript">
 function validateDayHours() {
	 var DayHours = document.getElementById("DayHours").value; 
	 $('#DayHoursDiv').find('p.jquery_form_error_message').remove();
	if(DayHours > 24){
		 $("input#DayHours").after("<p class='jquery_form_error_message'>Day Hours should less than 24 Hours</span>");
		 document.getElementById("DayHours").setAttribute('record-exist','yes');
		 document.getElementById("DayHours").setAttribute('record-exist-errorMsg','Day Hours should less than 24 Hours');
	}else{
		 $('#DayHoursDiv').find('p.jquery_form_error_message').remove();
		 $('input#DayHours').removeAttr( "record-exist record-exist-errormsg");
	}
	
 }
</script>  


<script type="text/javascript">
 function validateEmployeeId() {
	 var EmployeeId = document.getElementById("EmployeeId").value; 
	 var sourceleadUrl ='${sourceleadUrl}';
	 $('#EmployeeIdDiv').find('p.jquery_form_error_message').remove();
	 if(EmployeeId != null && EmployeeId != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateEmployeeId?${_csrf.parameterName}=${_csrf.token}&EmployeeId="+EmployeeId, 
		        success: function(data) {
		        	if(data == true){
						  $("input#EmployeeId").after("<p class='jquery_form_error_message'>"+EmployeeId+" is Already Existed</span>");
						  document.getElementById("EmployeeId").setAttribute('record-exist','yes');
						  document.getElementById("EmployeeId").setAttribute('record-exist-errorMsg',EmployeeId+' is Already Existed'); 
		        	}
		        	else{
		        		 $('input#EmployeeId').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#EmployeeIdDiv').find('p.jquery_form_error_message').remove(); 
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
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateUserEmail?${_csrf.parameterName}=${_csrf.token}&EmailId="+EmailId, 
		        success: function(data) {
		        	if(data == 'true'){
						  $("input#EmailId").after("<p class='jquery_form_error_message'>"+EmailId+" is Already Existed</span>");
						  document.getElementById("EmailId").setAttribute('record-exist','yes');
						  document.getElementById("EmailId").setAttribute('record-exist-errorMsg',EmailId+' is Already Existed'); 
		        	}
		        	else{
		        		 $('input#EmailId').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#EmailIdDiv').find('p.jquery_form_error_message').remove(); 
		        	}
		        },
		    });
	 }
 }
 </script>     
 
 
 
 <script type="text/javascript">
 function validateProjectSequeneNumber() {
	 var ProjectSequeneNumber = document.getElementById("ProjectSequeneNumber").value; 
	 var sourceleadUrl ='${sourceleadUrl}';
	 $('#ProjectIdDiv').find('p.jquery_form_error_message').remove();
	 if(ProjectSequeneNumber != null && ProjectSequeneNumber != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateProjectSequeneNumber?${_csrf.parameterName}=${_csrf.token}&ProjectSequeneNumber="+ProjectSequeneNumber, 
		        success: function(data) {
		        	if(data == true){
						  $("input#ProjectSequeneNumber").after("<p class='jquery_form_error_message'>"+ProjectSequeneNumber+" is Already Existed</span>");
						  document.getElementById("ProjectSequeneNumber").setAttribute('record-exist','yes');
						  document.getElementById("ProjectSequeneNumber").setAttribute('record-exist-errorMsg',ProjectSequeneNumber+' is Already Existed'); 
		        	}
		        	else{
		        		 $('input#ProjectSequeneNumber').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#ProjectIdDiv').find('p.jquery_form_error_message').remove(); 
		        	} 
		        },
		    });
	 }
 }
 </script> 