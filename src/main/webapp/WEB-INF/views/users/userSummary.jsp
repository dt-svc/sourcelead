<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet">
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link href="resources/css/sourcelead-main.css" rel="stylesheet">
<link href="resources/css/timesheet.css" rel="stylesheet">

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
				
				/* result = result + '<div class="useredit">';
				result = result + '<a href="javascript:void(0)"><i class="glyphicon glyphicon-pencil"></i>Edit</a>';
				result = result + '<a href="javascript:void(0)"><i class="glyphicon glyphicon-trash"></i>Remove</a>';
				result = result + '</div>'; */
				
				
				result = result + '  <div class="project_details">';
				result = result + '	 <div class="user_details_cta_img">';
				result = result + '		    <div class="user_change_img">';
				result = result + '			    <img src="resources/images/pratap-bis.jpg" title="Pratap Reddy" alt="Admin" class="img-circle">';
				result = result + ' 		    </div>';
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
	     
	     var result = "";
 
 
	         
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
   result = result + '    	<td><div>Business Intelli Intelli</div></td>';
   result = result + '<td><div>15/08/2016</div></td>';
   result = result + ' <td><div><input type="text" value="15/08/2016" class="project_inline_field"></div></td>';
   result = result + ' <td><div>45:50</div></td>';
   result = result + ' <td><div>45:50</div></td>';
   result = result + ' <td><div>45:50</div></td>';
   result = result + ' <td><div>Saved text</div></td>';
   result = result + ' <td><div><i class="glyphicon glyphicon-trash"></i></div></td>';
   result = result + '   </tr>';
   result = result + ' <tr>';
   result = result + '	<td><div>Business Intelli</div></td>';
   result = result + ' <td><div>15/08/2016</div></td>';
   result = result + ' <td><div><input type="text" value="15/08/2016" class="project_inline_field"></div></td>';
   result = result + ' <td><div>45:50</div></td>';
   result = result + ' <td><div>45:50</div></td>';
   result = result + '<td><div>45:50</div></td>';
   result = result + ' <td><div>Saved text</div></td>';
   result = result + '<td><div><i class="glyphicon glyphicon-trash"></i></div></td>';
   result = result + '           </tr>';
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
</script>







		
<!-- <div class="user_summ_wrap">
<div class="project_side_wrap_inn_tabs">
    <ul class="project-tabs-menu">
        <li class="current"><a href="#tab-1">Details</a></li>
        <li><a href="#tab-2">Projects</a></li>
        <div class="clearfix"></div>
    </ul>
    
    <div class="project_tabs">
        
        <div id="tab-1" class="project_tabs_inn">
            <div class="project_tabs_inn_table">
				<div class="project_details">
				
					<div class="user_details_cta_img">
						<div class="user_change_img">
						 <img src="resources/images/pratap-bis.jpg" title="Pratap Reddy" alt="Admin" class="img-circle">
							<a>Change</a>
							<input type="file" />
						</div>
						<span>Pratap Reddy</span>
						<b>pratap@businessintelli.com</b>
					</div>
				
					<div class="user_details_cta">
					<div class="project_details_cta">
						<strong>Employed Id:</strong>
						<span><input type="text" value="BIS1254614841W" placeholder="Project name" class="project_detfield"></span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>First Name:</strong>
						<span><input type="text" value="Srinivas" placeholder="First name" class="project_detfield"></span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Last Name:</strong>
						<span><input type="text" value="Mahesh" placeholder="Client" class="project_detfield"></span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Phone number:</strong>
						<span><input type="text" value="+91 9000 000 000" placeholder="DD/MM/YY" class="project_detfield"></span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Role:</strong>
						<span class="fix">Employee Role<u class="fa fa-edit"></u>	
						<div class="user_role">
							<select>
								<option>Option 1</option>
								<option>Option 2</option>
								<option>Option 3</option>
								<option>Option 4</option>
							</select></div>
						</span><div class="clearfix"></div>
					</div>

					<div class="project_details_cta">
						<strong>Invoice frequency:</strong>
						<span><input type="text" value="+91 9000 000 000" placeholder="DD/MM/YY" class="project_detfield"></span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>date of joining:</strong><span class="fix">15/05/2015</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Day hours:</strong>
						<span><input type="text" value="12" placeholder="DD/MM/YY" class="project_detfield hours"></span><div class="clearfix"></div>
					</div>					
					</div>
					<div class="clearfix"></div>
				</div>
            </div>
        </div>

		<div id="tab-2" class="project_tabs_inn">
	<div class="timesheet_head">
        	<ul>
                <li><i class="fa fa-filter"></i><span>All</span><i class="glyphicon glyphicon-play" id="appro-pop"></i>|
                	<div class="approved_pop">
                    	<ul>
                        	<li><input type="checkbox" value="None" id="c5"><label for="c5">All</label></li>
							<li><input type="checkbox" id="c6"><label for="c6">Approved</label></li>
							<li><input type="checkbox" id="c7"><label for="c7">Draft</label></li>
							<li><input type="checkbox" id="c8"><label for="c8">Rejected</label></li>
                        </ul>
                    </div>                	
                </li>
				<li><a href="javascript:void(0)" data-target="#addTeam" data-toggle="modal"><i class="fa fa-plus"></i>Assign Project</a></li>		
                <div class="clearfix"></div>
            </ul>
            <div class="clearfix"></div>
        </div>
            <div class="user_summ_wrap_table">
		<table>
        	<tbody>
            	<tr>
                	<th>Project Name</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Approved hrs</th>
                    <th>Rejected hrs</th>
                    <th>Wait for approval</th>
                    <th>Saved</th>
                    <th>Edit</th>
                </tr>
                <tr>
                	<td><div>Business Intelli Intelli</div></td>
                    <td><div>15/08/2016</div></td>
                    <td><div><input type="text" value="15/08/2016" class="project_inline_field"></div></td>
                    <td><div>45:50</div></td>
                    <td><div>45:50</div></td>
                    <td><div>45:50</div></td>
                    <td><div>Saved text</div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div>Business Intelli</div></td>
                    <td><div>15/08/2016</div></td>
                    <td><div><input type="text" value="15/08/2016" class="project_inline_field"></div></td>
                    <td><div>45:50</div></td>
                    <td><div>45:50</div></td>
                    <td><div>45:50</div></td>
                    <td><div>Saved text</div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
				                <tr>
                	<td><div>Business Intelli</div></td>
                    <td><div>15/08/2016</div></td>
                    <td><div><input type="text" value="15/08/2016" class="project_inline_field"></div></td>
                    <td><div>45:50</div></td>
                    <td><div>45:50</div></td>
                    <td><div>45:50</div></td>
                    <td><div>Saved text</div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div>Business Intelli</div></td>
                    <td><div>15/08/2016</div></td>
                    <td><div><input type="text" value="15/08/2016" class="project_inline_field"></div></td>
                    <td><div>45:50</div></td>
                    <td><div>45:50</div></td>
                    <td><div>45:50</div></td>
                    <td><div>Saved text</div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
				                <tr>
                	<td><div>Business Intelli</div></td>
                    <td><div>15/08/2016</div></td>
                    <td><div><input type="text" value="15/08/2016" class="project_inline_field"></div></td>
                    <td><div>45:50</div></td>
                    <td><div>45:50</div></td>
                    <td><div>45:50</div></td>
                    <td><div>Saved text</div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div>Business Intelli</div></td>
                    <td><div>15/08/2016</div></td>
                    <td><div><input type="text" value="15/08/2016" class="project_inline_field"></div></td>
                    <td><div>45:50</div></td>
                    <td><div>45:50</div></td>
                    <td><div>45:50</div></td>
                    <td><div>Saved text</div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
				                <tr>
                	<td><div>Business Intelli</div></td>
                    <td><div>15/08/2016</div></td>
                    <td><div><input type="text" value="15/08/2016" class="project_inline_field"></div></td>
                    <td><div>45:50</div></td>
                    <td><div>45:50</div></td>
                    <td><div>45:50</div></td>
                    <td><div>Saved text</div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div>Business Intelli</div></td>
                    <td><div>15/08/2016</div></td>
                    <td><div><input type="text" value="15/08/2016" class="project_inline_field"></div></td>
                    <td><div>45:50</div></td>
                    <td><div>45:50</div></td>
                    <td><div>45:50</div></td>
                    <td><div>Saved text</div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                <tr>
                	<td><div>Business Intelli</div></td>
                    <td><div>15/08/2016</div></td>
                    <td><div><input type="text" value="15/08/2016" class="project_inline_field"></div></td>
                    <td><div>45:50</div></td>
                    <td><div>45:50</div></td>
                    <td><div>45:50</div></td>
                    <td><div>Saved text</div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>				
                <tr>
                	<td><div>Business Intelli</div></td>
                    <td><div>15/08/2016</div></td>
                    <td><div><input type="text" value="15/08/2016" class="project_inline_field"></div></td>
                    <td><div>45:50</div></td>
                    <td><div>45:50</div></td>
                    <td><div>45:50</div></td>
                    <td><div>Saved text</div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
            </tbody>
        </table>            
            </div>
			
<nav>
<ul id="paging" class="pagination">
	<li><a href="#">Previous</a></li>
	<li><a href="#">2</a></li>
	<li><a href="#">3</a></li>
	<li><a href="#">4</a></li>
	<li><a href="#">5</a></li>
	<li class="disabled"><a href="#">6</a></li>
	<li data-page="last"><a href="#">Next</a></li>
	</ul>
	<div class="clearfix"></div>
</nav>			
			
        </div>
        
    </div>
</div> 

	<div class="project_inn_mask"></div>
	<div class="project_error">
		<p>User delete message here ....</p>
		<a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> 
	</div>

</div>	 -->	
		
		<!-- Timesheet -->
		
      </section>
      <!-- Content Area Ends--> 

    
    </div>

   
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
	
	
    </body>
	
<script>
function date(){
	$('#hireDate').datepicker({
	      dateFormat: 'yy-mm-dd',
	  });
}
</script>


	
<script>

// project side bar tabs
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




//Rate Per edit
$(document).ready(function() {
	$(".user_details_cta .fa-edit").click(function(){
		$(".user_role").slideToggle(200);
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




<!-- end project scripts -->