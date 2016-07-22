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
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/register-style.css" rel="stylesheet">
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
 <div class="wraper"> 
      <!-- Content Area -->
      <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>User Summary</h2>
          </div>
          <div class="pull-right brud-crum">Home &raquo; General Form &raquo; Create User</div>
        </section>
        
		<!-- Timesheet -->
		
		
<script>
	function predefineProfileImage(imageId){
		$(imageId).attr('src','resources/images/userprofile-img.png');
	}
</script>
		
<script> 
$(document).ready(function(){
	 var userId = '${userId}';
	 getUserSummaryDetails(userId);
});
</script>		
 <div id="projectSideDiv">
		     </div>  
<script>
 function getUserSummaryDetails(userId){
	 
	 var result = "";
	 
	 
	 result = result + ' <div class="user_summ_wrap">';
	 result = result + '<div class="project_side_wrap_inn_tabs">';
	 result = result + '    <ul class="project-tabs-menu">';
	 result = result + '       <li class="current"><a href="#tab-1" onClick="userDetails('+userId+');" >Details</a></li>';
	 result = result + '        <li><a href="#tab-2" onClick="userProjectDetails('+userId+');">Projects</a></li>';
	 result = result + '        <div class="clearfix"></div>';
	 result = result + '   </ul>';
	 
	 
	 result = result + '<div class="project_tabs">';
	  	result = result + ' <div id="tab-1" class="project_tabs_inn">';
	  	result = result + '</div>';
	  	
	  	result = result + '<div id="tab-2" class="project_tabs_inn">';
	  	 result = result + '<div class="timesheet_head">';
	     result = result + '  	<ul>';
	     result = result + '          <li><i class="fa fa-filter"></i><span>All</span><i class="glyphicon glyphicon-play" id="appro-pop"></i>|';
	     result = result + '         	<div class="approved_pop">';
	     result = result + '            	<ul>';
	     result = result + '                	<li><input type="checkbox" value="None" id="c5"><label for="c5">All</label></li>';
	     result = result + ' 							<li><input type="checkbox" id="c6"><label for="c6">Approved</label></li>';
	     result = result + ' 							<li><input type="checkbox" id="c7"><label for="c7">Draft</label></li>';
	     result = result + ' 							<li><input type="checkbox" id="c8"><label for="c8">Rejected</label></li>';
	     result = result + '                         </ul>';
	     result = result + '                    </div>                	';
	     result = result + '                </li>';
	     result = result + '				<li><a href="javascript:void(0)" data-target="#addTeam" data-toggle="modal"><i class="fa fa-plus"></i>Assign Project</a></li>';		
	     result = result + '              <div class="clearfix"></div>';
	     result = result + '            </ul>';
	     result = result + '          <div class="clearfix"></div>';
	     result = result + '     </div>';
	  	
	  	result = result + '</div>';
	  	
	  	
	  	
	  	result = result + '</div>'; 
	  	$("#projectSideDiv").empty(); 
	    $("#projectSideDiv").append(result);  	
	  	
	  	userDetails(userId);
 }
</script> 
	     
<script>
 function userDetails(userId){    
	 var sourceleadUrl ='${sourceleadUrl}';
	 
	 $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getUserDetailsById?${_csrf.parameterName}=${_csrf.token}&userId="+userId, 
	        success: function(data) {
	        	
			    var json = JSON.parse(data);
			     
			    var result = "";
			    
				result = result + '<div class="project_tabs_inn_table">';
				
				
				
				result = result + '  <div class="project_details">';
				if(json.user.userState != null && json.user.userState != "" && json.user.userState != 'INACTIVE'){
				    result = result + ' <div class="useredit">';
						result = result + ' <a id="edit-user" href="javascript:editUserDetails('+userId+');"><i class="glyphicon glyphicon-pencil"></i>Edit</a>';
						result = result + ' <a id="remove-user" href="javascript:removeUser();"><i class="glyphicon glyphicon-trash"></i>Remove</a>';
							result = result + ' <div id="remove_user_id" class="remove_user_pop" >';
									result = result +'<textarea placeholder="Comments here" id="reason" name="reason"/></textarea>';
									result = result + ' <div id="reasonDiv"/>';
									result = result + ' <b><input id="save_reason" value="Submit" onClick="terminateUser('+userId+');" type="button"/><input class="u_can_color" onclick="cancelUser();" value="Cancel" type="button"></b>';
						     result = result + ' </div>';
				     result = result + ' </div>';
				}
				
				
				
				result = result + '	 <div class="user_details_cta_img">';
				result = result + '		       <div class="user_change_img">';
				result = result +                  "<img onerror='predefineProfileImage(this);' src='"+sourceleadUrl+"/displayUserImage/"+userId+"' alt='image'>";
				result = result + ' 		  </div>';
				result = result + '             <span>'+json.user.firstName+"&nbsp"+json.user.lastName+'</span>';
				result = result + ' 		    <b>'+json.user.userType+'</b>';
				result = result + ' 		    <b>'+json.user.email+'</b>';
				result = result + '		 </div>';
				
				result = result + '	     <div class="user_details_cta">';
				result = result + '	     <div id="viewUserDiv">';
				if(json.employeeId != null && json.employeeId != "" && json.employeeId != 'undefined'){
					result = result + ' 		<div class="project_details_cta">';
					result = result + '				 <strong>Employed Id:</strong><span class="fix">'+json.employeeId+'</span><div class="clearfix"></div>';
					result = result + ' 		</div>';
				}
				
				
				result = result + '			<div class="project_details_cta">';
				result = result + '				 <strong>First Name:</strong><span class="fix">'+json.user.firstName+'</span><div class="clearfix"></div>';
				result = result + ' 		</div>';
				
					
				
				
				if(json.user.lastName != null && json.user.lastName != "" && json.user.lastName != 'undefined'){
					result = result + ' 		<div class="project_details_cta">';
					result = result + '				 <strong>Last Name:</strong><span class="fix">'+json.user.lastName+'</span><div class="clearfix"></div>';
					result = result + ' 		</div>';
				}
				
				if(json.user.mobilePhone != null && json.user.mobilePhone != "" && json.user.mobilePhone != 'undefined'){
					result = result + '			<div class="project_details_cta">';
					result = result + '				 <strong>Phone Number:</strong><span class="fix">'+json.user.mobilePhone+'</span><div class="clearfix"></div>';
					result = result + ' 		</div>';
				}
				
				if(json.rate != null && json.rate != "" && json.rate != 'undefined'){
					result = result + '			<div class="project_details_cta">';
					result = result + '				 <strong>Rate:</strong><span class="fix">'+json.rate+'</span><div class="clearfix"></div>';
					result = result + ' 		</div>';
				}
				
				if(json.ratePer != null && json.ratePer != "" && json.ratePer != 'undefined'){
					result = result + ' 		<div class="project_details_cta">';
					result = result + '				 <strong>Rate Per:</strong><span class="fix">'+json.ratePer+'</span><div class="clearfix"></div>';
					result = result + '			</div>';
				}
				
				result = result + '			<div class="project_details_cta">';
				result = result + '					<strong>Role:</strong><span class="fix">'+json.user.userType+'</span><div class="clearfix"></div>';
				result = result + '			</div>';
				
				if(json.InvoiceFrequency != null && json.InvoiceFrequency != "" && json.InvoiceFrequency != 'undefined'){
					result = result + ' 		<div class="project_details_cta">';
					result = result + '				<strong>Invoice Frequency:</strong><span class="fix">'+json.InvoiceFrequency+'</span><div class="clearfix"></div>';
					result = result + '			</div>';
				}
				
				if(json.hireDateStr != null && json.hireDateStr != "" && json.hireDateStr != 'undefined'){
					result = result + '			<div class="project_details_cta">';
					result = result + '				<strong>Date of joining:</strong><span class="fix">'+json.hireDateStr+'</span><div class="clearfix"></div>';
					result = result + '			</div>';
				}
				
				if(json.dailyHours != null && json.dailyHours != "" && json.dailyHours != 'undefined'){
					result = result + '			<div class="project_details_cta">';
					result = result + '				<strong>Day hours:</strong><span class="fix">'+json.dailyHours+'</span><div class="clearfix"></div>';
					result = result + '			</div>';
				}
				
				if(json.user.userState != null && json.user.userState != "" && json.user.userState == 'INACTIVE'){
					result = result + '			<div class="project_details_cta">';
					result = result + '				<strong>Reason:</strong><span class="fix">'+json.user.terminateReason+'</span><div class="clearfix"></div>';
					result = result + '			</div>';
				}
				
				result = result + '	   </div>';				
				result = result + ' <div class="user_summary_edit" id="editUserDiv"></div>';
				result = result + '	  <div class="clearfix"></div>';
				result = result + '	   </div>';
				result = result + '	  <div class="clearfix"></div>';
				result = result + '	</div>';
				result = result + '</div>';
				
				$("#tab-1").empty(); 
			    $("#tab-1").append(result);  
	    
	      },
	});
 }
</script> 
 
<script>
 function userProjectDetails(userId){    
	    var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getProjectDetailsById?${_csrf.parameterName}=${_csrf.token}&userId="+userId, 
	        success: function(data) {
			    var jsonData = JSON.parse(data);
	    		var result = "";
	    		if(jsonData.length > 0)
		   		{
						   result = result + '<div class="timesheet_head">';
						   result = result + '  	<ul>';
						   result = result + '          <li><i class="fa fa-filter"></i><span>All</span><i class="glyphicon glyphicon-play" id="appro-pop"></i>|';
						   result = result + '         	<div class="approved_pop">';
						   result = result + '            	<ul>';
						   result = result + '                	<li><input type="checkbox" value="None" id="c5"><label for="c5">All</label></li>';
						   result = result + ' 							<li><input type="checkbox" id="c6"><label for="c6">Active</label></li>';
						   result = result + ' 							<li><input type="checkbox" id="c7"><label for="c7">In-Active</label></li>';
						   result = result + '                         </ul>';
						   result = result + '                    </div>';
						   result = result + '                </li>';
						   result = result + '				<li><a href="javascript:void(0)" data-target="#assignProject" data-toggle="modal"><i class="fa fa-plus"></i>Assign Project</a></li>';		
						   result = result + '              <div class="clearfix"></div>';
						   result = result + '            </ul>';
						   result = result + '          <div class="clearfix"></div>';
						   result = result + ' </div>';
						   result = result + '        <div class="user_summ_wrap_table">';
						   result = result + '	<table>';
						   result = result + '    	<tbody>';
						   result = result + '      	<tr>';
						   result = result + '         	<th>Project Name</th>';
						   result = result + '             <th>Start Date</th>';
						   result = result + '            <th>End Date</th>';
						   result = result + '             <th>Approved hrs</th>';
						   result = result + '              <th>Rejected hrs</th>';
						   result = result + '             <th>Wait for approval</th>';
						   result = result + '             <th>Saved</th>';
						   result = result + '                 <th>Edit</th>';
						   result = result + '         </tr>';
						   for(var i=0; i<jsonData.length; i++){
							   var json = jsonData[i];
							   var startDate = "";
							   var endDate = "";
							   var approvedHrs = "";
							   var rejectedHrs = "";
							   var draftHrs = "";
							   var submittedHrs = "";
						    if(json.startDate != null && json.startDate != '' && json.startDate != 'undefined'){
							 startDate = json.startDate ;
						   }else{
							   startDate = "--";
						   }
						   if(json.endDate  != null && json.endDate  != '' && json.endDate  != 'undefined'){
						    	endDate = json.endDate;
						   }else{
							   endDate = "--";
						   }
						   if(json.approvedHrs  != null && json.approvedHrs  != '' && json.approvedHrs  != 'undefined'){
							   approvedHrs = json.approvedHrs;
						   }else{
							   approvedHrs = "--";
						   }
						   if(json.rejectedHrs  != null && json.rejectedHrs  != '' && json.rejectedHrs  != 'undefined'){
							   rejectedHrs = json.rejectedHrs;
						   }else{
							   rejectedHrs = "--";
						   }
						   if(json.draftHrs  != null && json.draftHrs  != '' && json.draftHrs  != 'undefined'){
							   draftHrs = json.draftHrs;
						   }else{
							   draftHrs = "--";
						   }
						   if(json.submittedHrs  != null && json.submittedHrs  != '' && json.submittedHrs  != 'undefined'){
							   submittedHrs = json.submittedHrs;
						   }else{
							   submittedHrs = "--";
						   }
						   
						   result = result + '       <tr>';
						   result = result + '    	<td><div>'+json.projects.projectName+'</div></td>';
						  
						   result = result + '<td><div>'+startDate+'</div></td>';
						  // result = result + ' <td><div><input type="text" value="'+endDate+'" id = "endDate"  name ="endDate" class="project_inline_field" onBlur="inlineEditForEndDate(\''+json.id+'\',\'endDate\');"></div></td>';
						   result = result + ' <td><div><input type="text" value="'+endDate+'" id="endDate" name = "endDate" class="project_inline_field" onclick="getEndDatePickerById();" onBlur="inlineEditForEndDate(\''+json.id+'\');"/></div></td>';
						  result = result + ' <td><div>'+approvedHrs+'</div></td>';
						   result = result + ' <td><div>'+rejectedHrs+'</div></td>';
						   result = result + ' <td><div>'+draftHrs+'</div></td>';
						   result = result + ' <td><div>'+submittedHrs+'</div></td>';
						  result = result + '<td><div><i id="dele-user" class="glyphicon glyphicon-trash"  onClick="deleteProject(\''+json.id+'\',\''+userId+'\')"; ></i>';
							result = result + '</div></td> '; 
						   
						   result = result + '   </tr>';
						   }
						   result = result + '        </tbody>';
						   result = result + '    </table>            ';
						   result = result + '        </div>';
							 			
						    result = result + ' <nav>';
						    result = result + '<ul id="paging" class="pagination">';
						    result = result + '	<li><a href="#">Previous</a></li>';
						    result = result + '	<li><a href="#">3</a></li>';
						    result = result + '	<li><a href="#">4</a></li>';
						    result = result + '<li><a href="#">5</a></li>';
						    result = result + '<li class="disabled"><a href="#">6</a></li>';
						    result = result + '<li data-page="last"><a href="#">Next</a></li>';
						    result = result + '</ul>';
						    result = result + '<div class="clearfix"></div>';
						    result = result + '</nav>		';	
						
						    result = result + '   </div>';
						    
						    result = result + ' </div>';
						    result = result + '</div> ';
						
						    result = result + '<div class="project_inn_mask"></div>';
						    result = result + '<div class="project_error">';
						    result = result + '	<p>User delete message here ....</p>';
						    result = result + '	<a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> ';
						    result = result + '</div>';
						
						    result = result + '</div>';
						    $("#tab-2").empty(); 
							$("#tab-2").append(result);
		 			 
		   		}
	        },
		});
	 }
</script>
		<!-- Timesheet -->
		
      </section>
      <!-- Content Area Ends--> 

    
    </div>

	
                <!-- Modal content Add Team-->
                <div role="dialog" id="assignProject" class="modal fade">
                  <div class="modal-dialog">
                  <form:form id="assignProject_form"  name="assignProject_form" method="post">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button data-dismiss="modal" class="close" type="button">X</button>
                        <h4 class="modal-title">Assign Project</h4>
                      </div>
                      <div class="modal-body clearfix">
                        <div class="form-horizontal custom-form clearfix">
                          <div class="col-xs-12 col-md-12 col-lg-12">
                            <div class="form-group">
                            <label class="col-lg-3">Select Project <span class="text-danger">*</span></label>
                            <div class="col-lg-9">
                           <!--  <div class="col-lg-9">
                              <select class="form-control">
                              	<option>Invoice-1</option>
                                <option>Invoice-2</option>
                                <option>Invoice-3</option>
                              </select>
                              </div> -->

                               <select class="form-control" id="proId" name="projectId" field-name="Project Name" onchange="flushProject(); getStartandEndDate();" data-validation="required">
                       		   <option value="">-Select Project-</option>
                        		<c:forEach var="option" items="${org_projectsList}">
  						   			<option value="${option.id}">${option.projectName}</option>
				      			  </c:forEach>
                      			</select>
                      			</div>
                            </div> 						  
                            <div class="form-group">
                            <label class="col-lg-3">Start date</label>
                            <div class="col-lg-9">
							<div class="input-group">
								  <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								  <input type="text" class="form-control" readonly="readonly" placeholder="DD/MM/YYYY" id = "StartDate" name="StartDate" data-validation="required" field-name="Start Date">
								</div>
                              </div>
                            </div>
                            <div class="form-group">
                            <label class="col-lg-3">End date</label>
                            <div class="col-lg-9">
							<div class="input-group">
								  <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								  <input type="text" class="form-control" readonly="readonly" placeholder="DD/MM/YYYY" id ="EndDate" name ="EndDate" />
								</div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="modal-footer">
                        <button class="btn btn-primary" type="button" id = "saveAssignProject" onclick="assignProject();">Select</button>
                        <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                      </div>
                    </div>
                    </form:form>
                  </div>
                </div>
                <!-- Add Team modal popup ends -->	


	<!-- <div class="project_inn_mask"></div>
	   <div id="remove_user_pop" class="remove_user_pop remove_user_pop_a">
		<p>User delete message here</p>
		<textarea placeholder="Comments here"></textarea>
		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Submit" onclick=""><input class="u_can_color" type="button" value="Cancel"></b>
	</div> -->

	<div id="popUp" ></div>	
    </body>
	


      
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
 function validateEmployeeId(id,userId) {
	 var EmployeeId = document.getElementById("EmployeeId").value; 
	 var sourceleadUrl ='${sourceleadUrl}';
	 $('#EmployeeIdDiv').find('p.jquery_form_error_message').remove();
	 if(EmployeeId != null && EmployeeId != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateExistingEmployeeId?${_csrf.parameterName}=${_csrf.token}&id="+id+"&userId="+userId+"&EmployeeId="+EmployeeId, 
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
 function updateUser(userId) {
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
	  if ($('#editUser_form').validate(false, validationSettings)){ 
		    var frm = $('#editUser_form').serializeFormJSON();
	        var con = JSON.stringify(frm);
	        con = con.replace(/[{}]/g, "");
	        var sourceleadUrl ='${sourceleadUrl}';
	        $("#update_user").attr("disabled", "disabled");
     		$( "#update_user" ).addClass('button').val('Processing..');
	        $.ajax({
		    	type: "POST",
		    	url: sourceleadUrl+"/updateUser?${_csrf.parameterName}=${_csrf.token}&json="+con+"&userId="+userId, 
		        success: function(result) {
		        	userDetails(userId);
			        
		        },
		    });
	        return true;
		
	   }else{
		return false;
	  } 
    }
    $('body').on('blur', '#editUser_form', function() {
		$("#editUser_form").showHelpOnFocus();;
		$("#editUser_form").validateOnBlur(false, validationSettings);  
	});
</script>   

	
<!-- <script>
function cancelEditUserDetails(){
	$(".user_summary_edit").slideToggle(100);
	$("#remove_user_id").slideUp(100);
	$(".error").removeClass("error");
	$('#editUserDiv').find('p.jquery_form_error_message').remove();
}
</script> -->

<script>
function editUserDetails(userId){
 var sourceleadUrl ='${sourceleadUrl}';
	 
	 $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getUserDetailsById?${_csrf.parameterName}=${_csrf.token}&userId="+userId, 
	        success: function(data) {
	        	
			    var json = JSON.parse(data);
			    var result = "";
			    
			    var selected1 = '';
				var selected2 = '';
				var selected3 = '';
				var selected4 = '';
				var selected5 = '';
				var selected6 = '';
				var selected7 = '';
				
				if(json.ratePer == 'Hourly'){
						selected1 = 'selected';
					} 
				
				if(json.ratePer == 'Daily'){
						selected2 = 'selected';
					} 
				
				if(json.ratePer == 'Weekly'){
						selected3 = 'selected';
					}
				
				if(json.ratePer == 'Bi-Weekly'){
						selected4 = 'selected';
					} 
				
				if(json.ratePer == 'Semi-Monthly'){
						selected5 = 'selected';
					} 
				
				if(json.ratePer == 'Monthly'){
						selected6 = 'selected';
					} 
				
				if(json.ratePer == 'Yearly'){
						selected7 = 'selected';
					}
				
				var selected8 = '';
				var selected9 = '';
				var selected10 = '';
				var selected11 = '';
				
				if(json.InvoiceFrequency == 'Weekly'){
					selected8 = 'selected';
					} 
				
				if(json.InvoiceFrequency == 'Bi-Weekly'){
					selected9 = 'selected';
					} 
				
				if(json.InvoiceFrequency == 'Semi-Monthly'){
					selected10 = 'selected';
					}
				
				if(json.InvoiceFrequency == 'Monthly'){
					selected11 = 'selected';
					} 
				
				result = result + ' <form id="editUser_form"  name="editUser_form" method="post">';
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <label>First name</label>';
					result = result + ' <input type="text" value="'+json.user.firstName+'" name="FirstName" field-name="First Name" data-validation="required validate_length length2-100"/>';
					result = result + ' </div>';
					
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <label>Last name</label>';
					result = result + ' <input type="text" value="'+json.user.lastName+'" name="LastName" data-validation="validate_length length2-100" data-validation-optional="true">';
					result = result + ' </div>';
					
					result = result + ' <div class="user_summary_edit_cta" id="EmployeeIdDiv">';
					result = result + ' <label>Employed Id</label>';
					result = result + ' <input type="text" value="'+json.employeeId+'" name="EmployeeId" id="EmployeeId" data-validation="validate_length length2-100" data-validation-optional="true" onblur="validateEmployeeId('+json.id+','+json.user.id+');">';
					result = result + ' </div>';
					
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <label>Phone number</label>';
					result = result + ' <input type="text" value="'+json.user.mobilePhone+'" name="PhoneNumber" data-validation="validate_phone validate_length length9-20" data-validation-optional="true">';
					result = result + ' </div>';
					
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <label>Rate</label>';
					result = result + ' <input type="text" class="rate_filed" value="'+json.rate+'" id="Rate"  name="Rate" data-validation="validate_float validate_length length2-8" data-validation-optional="true">';
					result = result + ' <select class="rate_sele" id="RatePer" name="RatePer" field-name="Rate Per">';
					result = result + ' <option value="">-Rate Per-</option>';
					result = result + ' <option '+selected1+'>Hourly</option>';
					result = result + ' <option '+selected2+'>Daily</option>';
					result = result + ' <option '+selected3+'>Weekly</option>';
					result = result + ' <option '+selected4+'>Bi-Weekly</option>';
					result = result + ' <option '+selected5+'>Semi-Monthly</option>';
					result = result + ' <option '+selected6+'>Monthly</option>';
					result = result + ' <option '+selected7+'>Yearly</option>';
					result = result + ' </select>';
					result = result + ' </div>';	
					
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <label>Date of join</label>';
					if(json.hireDateStr != null && json.hireDateStr != "" && json.hireDateStr != "undefined"){
						result = result + ' <input type="text" value="'+json.hireDateStr+'" name="DateofJoin" id="DateofJoin" readonly="readonly">';
					}else{
						result = result + ' <input type="text" value="'+json.hireDateStr+'" name="DateofJoin" id="DateofJoin" onClick="getCalendar();" readonly="readonly">';
					}
					result = result + ' </div>';	
					
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <label>Invoice frequency</label>';
					result = result + ' <select id="InvoiceFrequency" name="InvoiceFrequency">';
					result = result + ' <option value="">-Select Frequency-</option>';
					result = result + ' <option '+selected8+'>Weekly</option>';
					result = result + ' <option '+selected9+'>Bi-Weekly</option>';
					result = result + ' <option '+selected10+'>Semi-Monthly</option>';
					result = result + ' <option '+selected11+'>Monthly</option>';
					result = result + ' </select>';
					result = result + ' </div>';
					
					result = result + ' <div class="user_summary_edit_cta" id="DayHoursDiv">';
					result = result + ' <label>Day hours</label>';
					result = result + ' <input type="text" value="'+json.dailyHours+'" name="DayHours" id="DayHours" data-validation="validate_int validate_length length1-2" data-validation-optional="true" onblur="validateDayHours();">';
					result = result + ' </div>';
					
					result = result + ' <div class="clearfix"></div>';
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <input class="us-btn" value="Save" id="update_user" type="button" onclick="updateUser('+userId+');" > <input class="us-btn us-btn_a" value="Cancel" onClick="editUserDetails('+userId+');" type="button">';
					result = result + ' </div>';
					
					result = result + ' <div class="clearfix"></div>';
					result = result + ' </form>';
				result = result + ' </div>';
			  
				$(".user_summary_edit").slideToggle(100);
				$("#remove_user_id").slideUp(100);
				$(".error").removeClass("error");
				$('#editUserDiv').find('p.jquery_form_error_message').remove();
			    $("#editUserDiv").empty(); 
			    $("#editUserDiv").append(result);  
	        },
		});
}
</script>

<!-- <script>
  $( function() {
	  $('#DateofJoin').datepicker({
	      dateFormat: 'yy-mm-dd',
	  });
  } );
</script>  -->

<script>
function getCalendar(){
	 $('#DateofJoin').datepicker({
	      dateFormat: 'yy-mm-dd',
	  });
}
</script>


<script>
function removeUser(){
	$("#reason").val('');
	$("#reasonDiv").empty();
	$("#remove_user_id").slideToggle(100);
	$(".user_summary_edit").slideUp(100);
}
</script>
<script>
function cancelUser(){
	$("#remove_user_id").slideUp(100);
}
</script>


<script>
function terminateUser(userId){   
	var sourceleadUrl = '${sourceleadUrl}';
	var reason = document.getElementById("reason").value;
	if(reason){
		$("#reasonDiv").empty();
		
		//getConformation(userId,reason);
		var r = confirm("Do you want terminate the user");
		if (r == true) {
		 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/terminateUser?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"&reason="+reason, 	   
   		    success: function(data) {
   		    	userDetails(userId);
   		    }
	   });
		}
	}else{
		$("#reasonDiv").empty().append("<font class='tr_color'>Reason is required</font>") ;
	}
}

</script> 

<script>
function getConformation(userId,reason){ 
	 $(".project_error").slideDown(200);
	 $(".project_inn_mask").fadeIn(200);
}
</script> 


<script>
// project error message
$(document).ready(function() {
    $("#save_reason").click(function(){
		$(".project_error").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	});
    $(".project_error a").click(function() {
        $(".project_error").slideUp(200);
		$(".project_inn_mask").fadeOut(200);
    });
});
</script>





<script>
//project side bar tabs
$(document).ready(function() {
	$("#tab-1").show()
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});



</script> 

<script type="text/javascript">
function flushProject(){
	$("#proId").removeAttr("disabled");
	$('#StartDate').val('');
	 $('#EndDate').val('');
	 $(".error").removeClass("error");
	 $('#proId').find('p.jquery_form_error_message').remove();
}
</script>
<!-- this script for inline edit -->
<!-- <script> 
function inlineEditForUsers(userId,localFieldName){
	
	var fieldName = localFieldName;
	var fieldValue = $("#"+localFieldName).val();
	 $.ajax({
		   	type: "POST",
		    url: "http://localhost:8080/sourcelead/updateUserById?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"&fieldName="+fieldName+"&fieldValue="+fieldValue, 	   
   		    success: function(data) {
   			   
   		    }
	 }); 
}
</script> -->
<!-- this script for inline edit -->



<!-- Scripts for project details -->


<script>
function getStartandEndDate(){
	
	var sourceleadUrl = '${sourceleadUrl}';
	var projectId = document.getElementById("proId").value;
	if(projectId != null && projectId != ""){
    $.ajax({
    	type: "POST",
        url: sourceleadUrl+"/getStartAndEndOfProject?${_csrf.parameterName}=${_csrf.token}&&projectId="+projectId, 
        success: function(data) {
        	var json = JSON.parse(data);
        	$("#StartDate").val(json.startDateStr);
        	$("#EndDate").val(json.endDateStr);
	        
        },
    });
	}

}
</script>
<script type="text/javascript">
 function assignProject() {
	 var userId = '${userId}';
	 var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
		   }; 
	 if ($('#assignProject_form').validate(false, validationSettings)){ 
	        var frm = $('#assignProject_form').serializeFormJSON();
	        var con = JSON.stringify(frm);
	        con = con.replace(/[{}]/g, "");
	        var sourceleadUrl ='${sourceleadUrl}';
	        $("#saveAssignProject").attr("disabled", "disabled");
     		$("#saveAssignProject" ).addClass('button').val('Processing..');
	        
	        $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/saveAssignProject?${_csrf.parameterName}=${_csrf.token}&json="+con+"&userId="+userId, 
		        success: function(data) {
		        	if(data="success"){
		        	 	$("#assignProject").modal('hide');
		        		userProjectDetails(userId);
		        	}
		        },
		        error: function(e){
		            
		        }
		    }); 
	       return true;
	  }else{
		 $("#saveAssignProject").removeAttr('data-dismiss','modal');
		return false;
	  }
    }

    $('body').on('blur', '#addProject_form', function() {
		$("#assignProject_form").showHelpOnFocus();;
		$("#assignProject_form").validateOnBlur(false, validationSettings);  
	});
 
</script>

<script>
  $( function() {
	  
	  /* $('#endDate').datepicker({
	      dateFormat: 'yy-mm-dd',
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	   */
	  $('#StartDate').datepicker({
	      dateFormat: 'yy-mm-dd',
	      onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() + 1);
	            $("#EndDate").datepicker("option", "minDate", dt);
	        }
	  });
	  
	  $('#EndDate').datepicker({
	      dateFormat: 'yy-mm-dd',
	        onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() - 1);
	            $("#StartDate").datepicker("option", "maxDate", dt);
	        }
	  });
	  
  } );
  
  function deleteProject(local,userId){ 
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>User delete message here</p>";
		result = result +"		<textarea  id='reasonForProject' name='reasonForProject'  placeholder='Comments here'></textarea>";
		result = result + ' <div id="reasonForProjectDiv"/>';
		result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Submit' onclick='saveReason("+local+","+userId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTermination();'></b>";
		result = result +"	</div>";
		$("#popUp").append(result);
		$("#remove_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
		
	}
  function cancelTermination(){
		$("#popUp").empty();
		 $(".project_inn_mask").fadeOut(200);
	}
  
</script>
<script> 
function saveReason(projectMemberId,userId){
	var sourceleadUrl = '${sourceleadUrl}';
	var reasonForProject = document.getElementById("reasonForProject").value;
	
	if(reasonForProject){
		$("#reasonForProjectDiv").empty();
		
		var r = confirm("Do you want terminate the user from Project");
		if (r == true) {
	
	 $.ajax({
		   	type: "POST",
		    url: "http://localhost:8080/sourcelead/saveReason?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+projectMemberId+"&reason="+reason, 	   
   		    success: function(data) {
   		    	$("#popUp").empty();
   			 	$(".project_inn_mask").fadeOut(200);
   		    	userProjectDetails(userId);
   		    }
	 }); 
		}
	}
	else{
		$("#reasonForProjectDiv").empty().append("<font class='tr_color'>Reason is required</font>") ;
	}
}
</script>
<script> 
function inlineEditForEndDate(projectId){
	 var endDate = document.getElementById("endDate");
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/updateEndDateByProjectId?${_csrf.parameterName}=${_csrf.token}&endDate="+endDate+"&projectId="+projectId, 	   
   		    success: function(data) {
   			   
   		    }
	 });  
}
</script> 
<script>
 function getEndDatePickerById() {
	 $('#endDate').datepicker({
	      dateFormat: 'yy-mm-dd',
	      onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() + 1);
	        }
	  });
  } 
</script>

<!-- end project scripts -->