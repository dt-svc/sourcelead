<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet">
<link href="resources/css/timesheet.css" rel="stylesheet">
<script src="resources/js/formvalidator.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
<script src="resources/js/moment.min.js"></script>
<script src="resources/js/bootstrap-datetimepicker.min.js"></script> 
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="resources/js/jquery-fun.js"></script>	
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
						result = result + ' <a id="edit-user" href="javascript:editUserDetails();"><i class="glyphicon glyphicon-pencil"></i>Edit</a>';
						result = result + ' <a id="remove-user" href="javascript:removeUser();"><i class="glyphicon glyphicon-trash"></i>Remove</a>';
							result = result + ' <div class="remove_user_pop" >';
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
				result = result + ' 		    <b>'+json.user.email+'</b>';
				result = result + '		 </div>';
				
				
				if(json.employeeId != null && json.employeeId != "" && json.employeeId != 'undefined'){
					result = result + '	     <div class="user_details_cta">';
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
				
				result = result + ' <div class="user_summary_edit">';
				result = result + ' <form:form id="createUser_form"  name="createUser_form" method="post">';
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <label>First name</label>';
					result = result + ' <input type="text">';
					result = result + ' </div>';
					
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <label>Last name</label>';
					result = result + ' <input type="text">';
					result = result + ' </div>';
					
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <label>Employed Id</label>';
					result = result + ' <input type="text">';
					result = result + ' </div>';
					
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <label>Phone number</label>';
					result = result + ' <input type="text">';
					result = result + ' </div>';
					
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <label>Rate</label>';
					result = result + ' <input type="text" class="rate_filed">';
					result = result + ' <select class="rate_sele">';
					result = result + ' <option value="">-Rate Per-</option>';
					result = result + ' <option>Hourly</option>';
					result = result + ' <option>Daily</option>';
					result = result + ' <option>Weekly</option>';
					result = result + ' <option>Bi-Weekly</option>';
					result = result + ' <option>Semi-Monthly</option>';
					result = result + ' <option>Monthly</option>';
					result = result + ' <option>Yearly</option>';
					result = result + ' </select>';
					result = result + ' </div>';	
					
					
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <label>Date of join</label>';
					result = result + ' <input type="text">';
					result = result + ' </div>';	
					
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <label>Invoice frequency</label>';
					result = result + ' <input type="text">';
					result = result + ' </div>';
					
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <label>Day hours</label>';
					result = result + ' <input type="text">';
					result = result + ' </div>';
					
					result = result + ' <div class="clearfix"></div>';
					result = result + ' <div class="user_summary_edit_cta">';
					result = result + ' <input class="us-btn" value="Save" type="button"> <input class="us-btn us-btn_a" value="Cancel" onClick="editUserDetails();" type="button">';
					result = result + ' </div>';
					
					result = result + ' <div class="clearfix"></div>';
					result = result + ' </form:form>';
				result = result + ' </div>';				
				
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
			    var json = JSON.parse(data);
	    		var result = "";
	    		if(json.length > 0)
		   		{
		    		var result = "";
		    		;
		 			for(var i=0; i<json.length; i++){
			       	 
		 				var json = json[i];
	    		
						   result = result + '<div class="timesheet_head">';
						   result = result + '  	<ul>';
						   result = result + '          <li><i class="fa fa-filter"></i><span>All</span><i class="glyphicon glyphicon-play" id="appro-pop"></i>|';
						   result = result + '         	<div class="approved_pop">';
						   result = result + '            	<ul>';
						   result = result + '                	<li><input type="checkbox" value="None" id="c5"><label for="c5">All</label></li>';
						   result = result + ' 							<li><input type="checkbox" id="c6"><label for="c6">Active</label></li>';
						   result = result + ' 							<li><input type="checkbox" id="c7"><label for="c7">In-Active</label></li>';
						   result = result + '                         </ul>';
						   result = result + '                    </div>                	';
						   result = result + '                </li>';
						   result = result + '				<li><a href="javascript:void(0)" data-target="#addTeam" data-toggle="modal"><i class="fa fa-plus"></i>Assign Project</a></li>';		
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
						   
						   result = result + '       <tr>';
						   result = result + '    	<td><div>'+json.projects.projectName+'</div></td>';
						   result = result + '<td><div>'+json.startDate+'</div></td>';
						   result = result + ' <td><div><input type="text" value="'+json.endDate+'" class="project_inline_field"></div></td>';
						   result = result + ' <td><div>'+json.approvedHrs+'</div></td>';
						   result = result + ' <td><div>'+json.rejectedHrs+'</div></td>';
						   result = result + ' <td><div>'+json.draftHrs+'</div></td>';
						   result = result + ' <td><div>'+json.submittedHrs+'</div></td>';
						   result = result + ' <td><div><a href="javascript:void(0);" onclick="deleteProjectMemeber('+json.id+');"><i class="glyphicon glyphicon-trash"></i></div></td>';
						   result = result + '   </tr>';
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
						
						    result = result + '</div>	';
						    $("#tab-2").empty(); 
						   $("#tab-2").append(result);   
		 			  }
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
                <div role="dialog" id="addTeam" class="modal fade">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button data-dismiss="modal" class="close" type="button">X</button>
                        <h4 class="modal-title">Assign Project</h4>
                      </div>
                      <div class="modal-body clearfix">
                        <div class="form-horizontal custom-form clearfix">
                          <div class="col-xs-12 col-md-12 col-lg-12">
                            <div class="form-group">
                            <label class="col-lg-3">Select Project</label>
                            <div class="col-lg-9">
                              <select class="form-control">
                              	<option>Invoice-1</option>
                                <option>Invoice-2</option>
                                <option>Invoice-3</option>
                              </select>
                              </div>
                            </div>						  
                            <div class="form-group">
                            <label class="col-lg-3">Start date</label>
                            <div class="col-lg-9">
							<div class="input-group">
								  <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								  <input type="text" class="form-control" placeholder="DD/MM/YYYY">
								</div>
                              </div>
                            </div>
                            <div class="form-group">
                            <label class="col-lg-3">End date</label>
                            <div class="col-lg-9">
							<div class="input-group">
								  <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								  <input type="text" class="form-control" placeholder="DD/MM/YYYY">
								</div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="modal-footer">
                        <button data-dismiss="modal" class="btn btn-primary" type="button">Select</button>
                        <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- Add Team modal popup ends -->	


	<div class="project_inn_mask"></div>
	<div class="project_error">
		<p>Confirmation Message here</p>
		<a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> 
	</div>
	
	
    </body>
	
<script>
function date(){
	$('#hireDate').datepicker({
	      dateFormat: 'yy-mm-dd',
	  });
}
</script>


	
<script>
function editUserDetails(){
	$(".user_summary_edit").slideToggle(100);
	$(".remove_user_pop").slideUp(100);
}
</script>
<script>
function removeUser(){
	$("#reason").val('');
	$("#reasonDiv").empty();
	$(".remove_user_pop").slideToggle(100);
	$(".user_summary_edit").slideUp(100);
}
</script>
<script>
function cancelUser(){
	$(".remove_user_pop").slideUp(100);
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
		$("#reasonDiv").empty().append("<font class='tr_color'>Reason is required</font>");
	}
}

</script> 

<script>
function getConformation(userId,reason){ 
	alert(reason);
	 $(".project_error").slideDown(200);
	 $(".project_inn_mask").fadeIn(200);
}
</script> 

<!-- <script>
$(document).ready(function() { 

    $("html, #remove-user").click(function() {
        $(".user_summary_edit").slideUp(100);
    });
    $("#edit-user, .project_tabs_inn_table").click(function(e) {
        e.stopPropagation();
    });		
});
</script> -->
<!-- <script>
$(document).ready(function() {	
	$("#remove-user").click(function(){
		$(".remove_user_pop").slideToggle(100);
	});
    $("html, .remove_user_pop b").click(function() {
        $(".remove_user_pop").slideUp(100);
    });
    $("#remove-user, .remove_user_pop").click(function(e) {
        e.stopPropagation();
    });		
});
</script> -->



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
function deleteProjectMemeber(projectMemberId){   
	var sourceleadUrl = '${sourceleadUrl}';
	   $.ajax({
	      type: "POST",
	  	  url: "${sourceleadUrl}"+"/deleteProjectMemeber?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+projectMemberId,
	  			  success :function(data) {
	  				  if(data == 'success'){
		        		//location.replace(sourceleadUrl+"/userSummaryDetails?userId="+userId);  
		        		
		        	} 		
      			 }							     
	});    
}
</script>


<!-- end project scripts -->