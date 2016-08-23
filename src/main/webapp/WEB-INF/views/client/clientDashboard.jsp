<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
 
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="resources/js/formvalidator.js"></script>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>

<body>
	<!-- Content Area -->
	<section class="content-wraper">
		<section class="content-header clearfix">
		<c:if test="${not empty msg}">
	        <div class="success_pop" id="selector">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
			<div class="pull-left">
				<h2>Clients</h2>
			</div>

			<div class="pull-right brud-crum"><a href="dashboard">Home</a>&raquo;Client List</div>

		</section>

		<!-- Project -->
		<div class="timesheet_out">
			<div class="clients_wrap">
				<div class="timesheet_head">
					<ul>

						<!--  <li><i class="fa fa-filter"></i><span>Client</span><i class="glyphicon glyphicon-play" id="appro-pop"></i>|
                	<div class="approved_pop">
                    	<ul>
                        	<li><input type="checkbox" value="None" id="c1"><label for="c1">All</label></li>
							<li><input type="checkbox" id="c2"><label for="c2">Approved</label></li>
							<li><input type="checkbox" id="c3"><label for="c3">Draft</label></li>
							<li><input type="checkbox" id="c4"><label for="c4">Rejected</label></li>
                        </ul>
                    </div>                	
                </li> -->
						<li><a href="<c:url value='/client'/>"><i
								class="fa fa-plus"></i>Create client</a></li>
						<div class="clearfix"></div>
					</ul>
					<div class="clearfix"></div>
				</div>
			</div>
			<c:if test="${empty clientsList}">
				<div class="row mt">
					<div class="col-lg-12">
						<div class="content-area clearfix">
							<div class="blank-pages">
								<p>
									<i class="fa fa-5x fa-users"></i>
								</p>
								<p>No Client for organisation... Add Clients</p>
							</div>
						</div>
					</div>
				</div>
			</c:if>
		</div>

		<c:if test="${!empty clientsList}">
			<div class="clients_wrap_table ">
				<table>
					<tbody>
					<thead>
						<tr>
							<th>Client name</th>
							<th>Website</th>
							<th>Location</th>
							<th>Email</th>
							<th>Phone</th>
							<!-- <th>Projects</th> -->
						</tr>
					</thead>
					<c:forEach var="report" items="${clientsList}" varStatus="raId">
						<tr>
							<td><div class="proj_show"
									onclick="clientSideBar('${report.clientLocationId.id}','${report.clientLocationId.org.orgName}');"><span class="tbldata-dotdot-vsm" title="${report.clientLocationId.org.orgName}">${report.clientLocationId.org.orgName}</span><i
										class="fa fa-binoculars"></i>
								</div></td>

							<c:if test="${!empty report.clientLocationId.org.webSite}">
								<td><div><span class="tbldata-dotdot-sm" title="${report.clientLocationId.org.webSite}">${report.clientLocationId.org.webSite}</span></div></td>
							</c:if>
							<c:if test="${empty report.clientLocationId.org.webSite}">
								<td><div>--</div></td>
							</c:if>

							<c:if test="${!empty report.clientLocationId.country}">
								<td><div><span class="tbldata-dotdot-me" title="${report.clientLocationId.country}&nbsp;&nbsp;,${report.clientLocationId.state}&nbsp;&nbsp;,${report.clientLocationId.city}">${report.clientLocationId.country}&nbsp;&nbsp;,${report.clientLocationId.state}&nbsp;&nbsp;,${report.clientLocationId.city}</span></div></td>
							</c:if>
							<c:if test="${empty report.clientLocationId.country }">
								<td><div>--</div></td>
							</c:if>
							<c:if test="${!empty report.clientLocationId.email}">
								<td><div><span class="tbldata-dotdot-vsm" title="${report.clientLocationId.email}">${report.clientLocationId.email}</span></div></td>
							</c:if>
							<c:if test="${empty report.clientLocationId.email}">
								<td><div>--</div></td>
							</c:if>
							<c:if test="${!empty report.clientLocationId.phone}">
								<td><div><span class="tblminwidth-125">${report.clientLocationId.phone}</span></div></td>
							</c:if>

							<c:if test="${empty report.clientLocationId.phone}">
								<td><div>--</div></td>
							</c:if>

							<%-- <c:if test="${!empty report.projectsCont}">
								<td><div><span class="tblminwidth-50">${report.projectsCont}</span></div></td>
							</c:if>
							<c:if test="${empty report.projectsCont}">
								<td><div>--</div></td>
							</c:if> --%>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>

		</c:if>

       <div class="side_mask"><div class="proj_close"></div></div>
       
		<div class="clients_side_wrap">
			<div class="clients_side_wrap_inn">
				<h1 id="clientNameDiv"></h1>
				<div class="proj_close">
					<i></i>
				</div>
				<div id="clientSideDiv"></div>
			</div>
		</div>
	</section>
</body>

<script>
	function clientSideBar(clientId,orgName) {

		var result = "";
		result = result + '<div class="project_side_wrap_inn_tabs">';
		result = result + '<ul class="project-tabs-menu">';
		result = result
				+ '<li class="current"><a href="#tab-1" onClick="clientDetails('
				+ clientId + ');">Details</a></li>';
		/* result = result + '<li><a href="#tab-2" onclick="clientProjectDetails('
				+ clientId + ');">Projects</a></li>'; */
		result = result + '<div class="clearfix"></div>'
		result = result + '</ul>';
		result = result + '<div class="project_tabs">';

		result = result + '<div id="tab-1" class="project_tabs_inn">';
		result = result + '</div>';

		/* result = result + '<div id="tab-2" class="project_tabs_inn">';
		result = result + '</div>'; */

		result = result + '</div>';
		result = result + '</div>'
		$("#clientSideDiv").empty();
		$("#clientSideDiv").append(result);
		
		$("#clientNameDiv").empty();
		$("#clientNameDiv").append(orgName);
		

		clientDetails(clientId);
	}
</script>

<script>
	function clientProjectDetails(clientId) {
		var sourceleadUrl = '${sourceleadUrl}';
		$
				.ajax({
					type : "POST",
					url : sourceleadUrl
							+ "/getAllClientProjectsByOrgRelationId?${_csrf.parameterName}=${_csrf.token}&orgRelationId="
							+ clientId,
					success : function(data) {

						var json = JSON.parse(data);
						var result = "";
						result = result+ '<div class="project_tabs_inn_head">';
						/* result = result + '					<a href="create-project.html"><i class="glyphicon glyphicon-plus"></i>Add project</a>'; */
						result = result + '</div>';
						result = result+ '<div class="project_tabs_inn_table">';
						if(json.length!=null && json.length!=''){
						result = result + '<table>';
						result = result + '<tbody>';
						result = result + '<tr>';
						result = result + '<th>Project Id</th>';
						result = result + '<th>Projects name</th>';
						result = result + '<th>Start Date</th>';
						result = result + '<th>End Date</th>';
						result = result + '<th>Manager</th>';
						result = result + '<th>Team</th>';
						result = result + '<th>Status</th>';
						result = result + '</tr>';
						result = result + '<tr>';
						for (var i = 0; i < json.length; i++) {
							var opt = json[i];
							var endDateStr ="";
							if(opt.endDateStr != null && opt.endDateStr != '' & opt.endDateStr != 'undefined'){
								endDateStr = opt.endDateStr;
							}else{
								endDateStr = "NO DATA";
							}
							result = result + '<td><div class="tbldata-dotdot-sm" title="'+opt.id+'"><input type="text"  onBlur="inlineEditClientProjects('+opt.id+',\'id\');" id="id'+opt.id+'"  value="'+opt.id+'" class="project_inline_field"></div></td>';
							 result = result + '<td><div class="tbldata-dotdot-sm" title="'+opt.projectName+'"><input type="text"  onBlur="inlineEditClientProjects('+opt.id+',\'projectName\');" id="projectName'+opt.id+'"  value="'+opt.projectName+'" class="project_inline_field"></div></td>'; 
							/* result = result + '<td><div><a href="<c:url value="/projectDashboard/'+opt.id+'#tab-3"/>""> '+opt.projectName+' </a></div></td>'; */
							result = result + '<td>'+opt.startDateStr+'</td>';
							
							result = result+ '<td><div><input type="text" value="'+endDateStr+'"  id="endDate'+opt.id+'" class="project_inline_field" readonly="readonly"></div></td>';
							result = result + ' <td><div><select id="clientId'+opt.id+'" onBlur="inlineEditClientProjects('+opt.id+',\'clientId\');">';
									
							for (var j = 0; j < opt.projectManagerName.length; j++) {
										
										var managers = opt.projectManagerName[j];
										var projrctmanager = managers.split('&');
								    	var name = projrctmanager[0];
								    	var id = projrctmanager[1];
								    	var selected = '';
								    	
					   		 	    	 if(opt.relationId==id){
					   		 	    		 selected = 'selected';
					   		 	    		
					   		 	    		result = result+"<option value="+id+" "+selected+" >"+name+"</option>";
					   		 	    	    
					   		 	    	 }else{
					   		 	    		result = result+"<option value="+id+" >"+name+"</option>";
					   		 	    	 }
					   		 	    	 
									} 
							
							result = result + '	</select></div></td>';
							result = result+ '<td><div>'+opt.assignedEmpCount+'</div></td>';
							result = result + '<td><div><select id="projectStatusId'+opt.id+'" onBlur="inlineEditClientProjects('+opt.id+',\'projectStatusId\');">';
							if(opt.projectStatus == 'STARTED'){
								result = result+'<option selected>Started</option>';
							}else{
								result = result+'<option>Started</option>';
							}
							
							if(opt.projectStatus == 'INPROGRESS'){
								result = result+'<option selected>In-Progress</option>';
							}else{
								result = result+'<option>In-Progress</option>';
							}
							
							if(opt.projectStatus == 'COMPLETED'){
								result = result+'<option selected>Completed</option>';
							}else{
								result = result+'<option>Completed</option>';
							}
						
							if(opt.projectStatus == 'ONHOLD'){
								result = result+'<option selected>On-Hold</option>';
							}else{
								result = result+'<option>On-Hold</option>';
							}
							result = result + '</select></div></td>';
							result = result + '</tr>';
						}
						rresult = result + '	     </tbody>';
						result = result + '	 </table>   ';
						}else{
				    		
			    			 result = result + '<div class="table_nodata">';
			    			 result = result + '<div class="table_nodata_inn">';
			    			 result = result + '<img src="resources/images/no-data.png" />';
			    			 result = result + '<span>Nothing is here to show ';
			    			 result = result + '</span>';
			    			 result = result + '<div class="clearfix"></div>';
			    			 result = result + '</div>';
			    			 result = result + '</div>';
			    		 } 
						result = result + '	     </div>';
						$("#tab-2").empty();
						$("#tab-2").append(result);

						
						if(json.length > 0)
				   		{
			    			 for(var i=0; i<json.length; i++){
								 var jsonData = json[i];
								 
								 //Assign date of project member
								 var selected = jsonData.startDateStr;
								 var dt = new Date(selected);
			 		             dt.setDate(dt.getDate());
			 		             
								 $('#endDate'+jsonData.id).datepicker({
						    		      dateFormat: 'yy-mm-dd',
						    		      minDate: dt,
						    		      onClose: function() {
						    		        	  var id = $(this).attr('id');
												 var projectMemberId = $(this).attr('id').split('endDate')[1];
												 inlineEditClientProjects(jsonData.id,'endDate'); 
						    		      }
						    		  }); 
			    			 }
				   		}
					},
				});
	}
</script>





<script> 
function  inlineEditClientProjects(projectId,localFieldName){
	var sourceleadUrl ='${sourceleadUrl}';
	var fieldName = localFieldName;
	var fieldValue = $("#"+localFieldName+""+projectId).val();
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/inlineEditClientProjects?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&fieldName="+fieldName+"&fieldValue="+fieldValue, 	   
   		    success: function(data) {
   		    }
	 }); 
}
</script> 

<script> 
function  inlineEditForProjects(orgRelationId,localFieldName){
	var sourceleadUrl ='${sourceleadUrl}';
	var fieldName = localFieldName;
	var fieldValue = $("#"+localFieldName).val();
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/updateClientProjectById?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationId+"&fieldName="+fieldName+"&fieldValue="+fieldValue, 	   
   		    success: function(data) {
   		    }
	 }); 
}
</script> 

<script>
function getEndDatePickerById(projectId,localFieldName){
	$('#endDate'+projectId).datepicker({
	      dateFormat: 'yy-mm-dd',
	      onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() + 1);
	        },
	        onClose: function() {
	        	inlineEditClientProjects(projectId,localFieldName);
	     }
	  });
}
</script>

<script>
	// Approved Popup
	$(document).ready(function() {
		$("#appro-pop").click(function() {
			$(".approved_pop").slideToggle(200);
		});
		$("html").click(function() {
			$(".approved_pop").slideUp(200);
		});
		$(".approved_pop, #appro-pop").click(function(e) {
			e.stopPropagation();
		});
	});
	// project side bar
	$(document).ready(function() {
		$(".proj_show").click(function() {
			$(".clients_side_wrap").addClass("clients_side_wrap_active");
			$(".side_mask").fadeIn(200);
			$("body").css('overflow-y', 'hidden');
		});
		$(".proj_close, .side_mask").click(function() {
			$(".clients_side_wrap").removeClass("clients_side_wrap_active");
			$(".side_mask").fadeOut(200);
			$("body").css('overflow-y', 'auto');
		});
	});

	//project side bar tabs
	$(document).ready(function() {
		$("#tab-1").show();
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


<!-- Show success message after form submission script -->
<script>
  $('#selector').delay(2000).fadeOut('slow');
</script> 
<!-- End -->





<!-- client details View starts-->
<script type="text/javascript">
	function clientDetails(orgRelationID) {
		var sourceleadUrl = '${sourceleadUrl}';
		$
				.ajax({
					type : "POST",
					url : sourceleadUrl+"/getClientDeatilsById?${_csrf.parameterName}=${_csrf.token}&clientId="+ orgRelationID,
					success : function(data) {
						var json = JSON.parse(data);
						var result = "";
						var city = "";
						var state = "";
						var country = "";
						
						result = result+ '<div class="project_tabs_inn_table">';
                        
						result = result + '<div class="project_details">';
						result = result + ' <div class="useredit useredit_a">';
						result = result + '<a id="edit-client" href="javascript:editClientDetails('+orgRelationID+')"><i class="glyphicon glyphicon-pencil"></i>Edit</a>';
						result = result + ' </div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Client name:</strong>';
						
						if(json.orgName !=null && json.orgName !='' && json.orgName !='undefined'){
						result = result + '<span>'+json.orgName+'</span>';
						}
						
						result = result + '<div class="clearfix"></div>';
						result = result + '</div>';
						result = result + '<div class="project_details_cta">';
						
						result = result + '<strong>Website:</strong>';
						if(json.webSite !=null && json.webSite !='' && json.webSite !='undefined'){
						result = result + '<span>'+json.webSite+'</span><div class="clearfix"></div>';
						}
						result = result + '</div>';
					
					
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Client City:</strong>';
						if(json.city != null && json.city !='' && json.city !='undefined'){
							result = result + '<span>'+json.city+'</span><div class="clearfix"></div>';
						}else{
							result = result + '<span>No Data</span><div class="clearfix"></div>';
						}
						result = result + '</div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Client State:</strong>';
						if(json.state != null && json.state !='' && json.state !='undefined'){
							result = result + '<span>'+json.state+'</span><div class="clearfix"></div>';
						}else{
							result = result + '<span>No Data</span><div class="clearfix"></div>';
						}
						result = result + '</div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Client Country:</strong>';
						if(json.country != null && json.country !='' && json.country !='undefined'){
							result = result + '<span>'+json.country+'</span><div class="clearfix"></div>';
						}else{
							result = result + '<span>No Data</span><div class="clearfix"></div>';
						}
						result = result + '</div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Email:</strong>';
						if(json.email != null && json.email !='' && json.email !='undefined'){
						result = result + '<span>'+json.email+'</span><div class="clearfix"></div>';
						}else{
							
							result = result + '<span>No Data</span><div class="clearfix"></div>';	
						}
						result = result + '</div>';
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Phone number:</strong>';
						if(json.phone != null && json.phone !='' && json.phone !='undefined'){
						result = result + '<span>'+json.phone+'</span><div class="clearfix"></div>';
						}else{
							
							result = result + '<span>No Data</span><div class="clearfix"></div>';	
						}
						result = result + '</div>';
						/* result = result + '<div class="project_details_cta">';
						result = result + '<strong>Projects:</strong>';
						if(json.projectsCont !=null && json.projectsCont !='' && json.projectsCont !='undefined'){
						result = result + '<span>'+json.projectsCont+'</span><div class="clearfix"></div>';
						} */
						result = result + '<div class="clearfix"></div>';
						result = result + ' <div class="user_summary_edit user_summary_edit_a" id="editClientDetailsDiv"></div>';
						result = result + '</div>';
						result = result + '<div class="clearfix"></div>';
						result = result + '</div>';
						result = result + '</div>';
						result = result + '</div>';
						$("#tab-1").empty();
						$("#tab-1").append(result);
						$("#tab-1").show();
						$(".project-tabs-menu a").click(
								function(event) {
									event.preventDefault();
									$(this).parent().addClass("current");
									$(this).parent().siblings().removeClass(
											"current");
									var tab = $(this).attr("href");
									$(".project_tabs_inn").not(tab).css(
											"display", "none");
									$(tab).fadeIn();
								});

					}
				});
	}
</script>
<!--  client details ends -->



<script type="text/javascript">
	function editClientDetails(orgRelationID) {
		var sourceleadUrl = '${sourceleadUrl}';
		      $.ajax({
						type : "POST",
						url : sourceleadUrl+"/getClientDeatilsById?${_csrf.parameterName}=${_csrf.token}&clientId="+ orgRelationID,
						success : function(data) {
						var json = JSON.parse(data);
						
						var result = "";
						result = result + ' <form id="editClient_form"  name="editClient_form" method="post">';
						result = result + '<div class="user_summary_edit_cta">';
						result = result + '<label>Client Name </label>';
						result = result + '<input type="text"  id="clientName" name="clientName" value="'+json.orgName+'" placeholder="client name" >';
						result = result + '</div>';
						
						result = result + '<div class="user_summary_edit_cta" id="companyWebsiteDiv">';
						result = result + '<label>Website</label>';
						result = result + '<input type="text"  id="companyWebsite" name="companyWebsite" onblur="validateWebsite();" disabled="disabled" field-name="Website" data-validation="validate_Space validate_length length2-100 validate_domain" data-validation-optional="true"  value="'+json.webSite+'" placeholder="client website" ></div>';
						result = result + '</div>';
						
						result = result + '<div class="user_summary_edit_cta">';
						result = result + '<label>City</label>';
						if(json.city != null && json.city !='' && json.city !='undefined'){
							result = result + '<input type="text"  id="clientCity" name="clientCity" value="'+json.city+'" placeholder="client city" ></div>';
						}else{
							result = result + '<input type="text"  id="clientCity" name="clientCity"  placeholder="client city" ></div>';
						}
						result = result + '</div> ';
						
						result = result + '<div class="user_summary_edit_cta">';
						result = result + '<label>State</label>';
						if(json.state != null && json.state !='' && json.state !='undefined'){
							result = result + '<input type="text"  id="clientState" id="clientState" name="clientState" value="'+json.state+'" placeholder="client state" ></div>';
						}else{
							result = result + '<input type="text"  id="clientState" id="clientState" name="clientState" placeholder="client state" ></div>';
						}
						result = result + '</div> ';
						
						result = result + '<div class="user_summary_edit_cta">';
						result = result + '<label>Country</label>';
						if(json.country != null && json.country !='' && json.country !='undefined'){
							result = result + '<input type="text"  id="clientCountry" name="clientCountry" value="'+json.country+'" placeholder="client country" ></div>';
						}else{
							result = result + '<input type="text"  id="clientCountry" name="clientCountry"  placeholder="client country" ></div>';
						}
						result = result + '</div>  ';
						
						result = result + '<div class="user_summary_edit_cta" id="EmailIdDiv">';
						result = result + '	<label>Email</label>';
						if(json.email != null && json.email !='' && json.email !='undefined'){
							result = result + '<input type="text"  id="EmailId" name="EmailId" field-name="Email Id" data-validation="required validate_Space validate_email" onKeyUp="validateClientEmail();"  value="'+json.email+'" placeholder="client email" c></div>';
						}else{
							result = result + '<input type="text"  id="EmailId" name="EmailId" field-name="Email Id" data-validation="required validate_Space validate_email" onKeyUp="validateClientEmail();"  placeholder="client email" c></div>';
						}
						result = result + '</div> ';
						
						result = result + '<div class="user_summary_edit_cta">';
						result = result + '<label>Phone</label>';
						if(json.phone != null && json.phone !='' && json.phone !='undefined'){
							result = result + '<input type="text"  id="clientPhoneNumber" name="clientPhoneNumber" value="'+json.phone+'" data-validation="validate_Space validate_length length10-20 validate_phone" data-validation-optional="true" placeholder="client phone" ></div>';
						}else{
							result = result + '<input type="text"  id="clientPhoneNumber" name="clientPhoneNumber"  data-validation="validate_Space validate_length length10-20 validate_phone" data-validation-optional="true" placeholder="client phone" ></div>';
						}
						result = result + '</div> ';   
						
						result = result + '<div class="clearfix"></div>';
						result = result + '<div class="user_summary_edit_cta">';
						result = result + '<input class="us-btn" id="save_client" type="button" value="Save" onclick="storeClientDetails('+orgRelationID+')"/ /> <input class="us-btn us-btn_a" type="button" value="Cancel" onclick="clientDetails('+orgRelationID+')"/>';
						result = result + '</div>';		
						result = result + '<div class="clearfix"></div>';
						result = result + ' </form>';
						
						$(".user_summary_edit").slideToggle(100);
						$("#editClientDetailsDiv").empty();
				        $("#editClientDetailsDiv").append(result);
					}
				});
	}
</script>

<script type="text/javascript">
	function storeClientDetails(orgRelationId) {
         if ($('#editClient_form').validate(false, validationSettings)){
    		 var frm = $('#editClient_form').serializeFormJSON();
             var con = JSON.stringify(frm);
             con = con.replace(/[{}]/g, "");
             //It will escape all the special characters
             var jsonData = encodeURIComponent(con);
             var sourceleadUrl ='${sourceleadUrl}';
	 	 $.ajax({
			   	type: "POST",
			    url: sourceleadUrl+"/clientDetailsUpdateUsingId?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&orgRelationId="+orgRelationId, 	   
	   		    success: function(data) {
	   		    	clientDetails(orgRelationId);
	   		    }
		 }); 
	     	return true;
         }else{
             return false;
           }
		
    }
	$('body').on('blur', '#editClient_form', function() {
        $("#editClient_form").showHelpOnFocus();
        $("#editClient_form").validateOnBlur(false, validationSettings);  
    });
</script>

<script type="text/javascript">
	function validateWebsite() {
		var website = document.getElementById("companyWebsite").value;
		var sourceleadUrl = '${sourceleadUrl}';
		if(website.match(/^[^\s-](.*)*$/) != null){
		var sourceleadBLockedwebsites = '${sourceLeadBlockedSocialDomains}';
		$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
		
		if (website != null && website != '') {
			//$("#save_client").attr("disabled", "disabled");
			// $( "#save_client" ).addClass('button').val('Processing..');
			$.ajax({
				type : "GET",
				url : sourceleadUrl + "/ws/validateClientWebsite?website="+ website + "&sourceleadBLockedwebsites="+ sourceleadBLockedwebsites,
				success : function(data) {
					var result = data;
					if (result == "Website Duplicated" || result == "Invalid Website") {
						//existed website
						$("input#companyWebsite").after("<p class='jquery_form_error_message'>"+ website+ " is "+result+"</span>");
						document.getElementById("companyWebsite").setAttribute('record-exist', 'yes');
						document.getElementById("companyWebsite").setAttribute('record-exist-errorMsg',website + ' is '+result);
						$('input#companyWebsite').removeAttr( "record-exist record-exist-errormsg");
						$("#save_client").attr("disabled", "disabled");
						
					} else {
						//New WebSite class="form-control"
						 $("#save_client").removeAttr("disabled");
						$('input#companyWebsite').removeAttr("class record-exist record-exist-errormsg");
						$('input#companyWebsite').attr('class','valid form-control');
						$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
				
					}
				},

			});
		  }
		}
	}
</script>

<script type="text/javascript">
 function validateClientEmail() {
	 var EmailId = document.getElementById("EmailId").value; 
	 $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(EmailId != null && EmailId != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getClientEmailVerification?${_csrf.parameterName}=${_csrf.token}&EmailId="+EmailId, 
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
		        	}
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
<script>
// Edit pop
$(document).ready(function() {	
	
    $("#edit-client, #btnClose").click(function() {
        $(".user_summary_edit").slideUp(100);
    });
		
});
</script> 