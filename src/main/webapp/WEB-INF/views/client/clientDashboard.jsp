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
			<div class="clients_wrap_table">
				<table>
					<tbody>
					<thead>
						<tr>
							<th>Client name</th>
							<th>Website</th>
							<th>Location</th>
							<th>Email</th>
							<th>Phone</th>
							<th>Projects</th>
						</tr>
					</thead>
					<c:forEach var="report" items="${clientsList}" varStatus="raId">
						<tr>
							<td><div class="proj_show"
									onclick="clientSideBar('${report.clientLocationId.id}');">${report.clientLocationId.org.orgName}<i
										class="fa fa-binoculars"></i>
								</div></td>

							<c:if test="${!empty report.clientLocationId.org.webSite}">
								<td><div>${report.clientLocationId.org.webSite}</div></td>
							</c:if>
							<c:if test="${empty report.clientLocationId.org.webSite}">
								<td><div>--</div></td>
							</c:if>

							<c:if test="${!empty report.clientLocationId.country}">
								<td><div>${report.clientLocationId.country}&nbsp;&nbsp;,${report.clientLocationId.state}&nbsp;&nbsp;,${report.clientLocationId.city}</div></td>
							</c:if>
							<c:if test="${empty report.clientLocationId.country }">
								<td><div>--</div></td>
							</c:if>
							<c:if test="${!empty report.clientLocationId.email}">
								<td><div>${report.clientLocationId.email}</div></td>
							</c:if>
							<c:if test="${empty report.clientLocationId.email}">
								<td><div>--</div></td>
							</c:if>
							<c:if test="${!empty report.clientLocationId.phone}">
								<td><div>${report.clientLocationId.phone}</div></td>
							</c:if>

							<c:if test="${empty report.clientLocationId.phone}">
								<td><div>--</div></td>
							</c:if>

							<c:if test="${!empty report.projectsCont}">
								<td><div>${report.projectsCont}</td>
							</c:if>
							<c:if test="${empty report.projectsCont}">
								<td><div>--</td>
							</c:if>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>

		</c:if>


		<div class="clients_side_wrap">
			<div class="clients_side_wrap_inn">
				<h1>Client name here</h1>
				<div class="proj_close">
					<i class="glyphicon glyphicon-share-alt"></i>
				</div>
				<div id="clientSideDiv"></div>
			</div>
		</div>
	</section>
</body>

<script>
	function clientSideBar(clientId) {

		var result = "";
		result = result + '<div class="project_side_wrap_inn_tabs">';
		result = result + '<ul class="project-tabs-menu">';
		result = result
				+ '<li class="current"><a href="#tab-1" onClick="clientProjectDetails('
				+ clientId + ');">Projects</a></li>';
		result = result + '<li><a href="#tab-2" onclick="clientDetails('
				+ clientId + ');">Details</a></li>';
		result = result + '<div class="clearfix"></div>'
		result = result + '</ul>';
		result = result + '<div class="project_tabs">';

		result = result + '<div id="tab-1" class="project_tabs_inn">';
		result = result + '</div>';

		result = result + '<div id="tab-2" class="project_tabs_inn">';
		result = result + '</div>';

		result = result + '</div>';
		result = result + '</div>'
		$("#clientSideDiv").empty();
		$("#clientSideDiv").append(result);

		clientProjectDetails(clientId);
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
						result = result + '<table>';
						result = result + '<tbody>';
						result = result + '<tr>';
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
							result = result + '<td><div><input type="text"  onBlur="inlineEditClientProjects('+opt.id+',\'projectName\');" id="projectName" value="'+opt.projectName+'" class="project_inline_field"></div></td>';
							result = result + '<td>'+opt.startDateStr+'</td>';
							result = result+ '<td><div><input type="text" value="'+opt.endDateStr+'"  id="endDate" class="project_inline_field" onclick="getEndDatePickerById('+opt.id+',\'endDate\');"></div></td>';
							result = result + ' <td><div><select id="clientId" onBlur="inlineEditClientProjects('+opt.id+',\'clientId\');">';
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
							result = result+ '<td><div>30</div></td>';
							result = result + '<td><div><select id="projectStatusId" onBlur="inlineEditClientProjects('+opt.id+',\'projectStatusId\');">';
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
						result = result + '	     </div>';
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
					},
				});
	}
</script>




<script type="text/javascript">
	function clientDetails(orgRelationID) {
		var sourceleadUrl = '${sourceleadUrl}';
		$
				.ajax({
					type : "POST",
					url : "http://localhost:8080/sourcelead/getClientDeatilsById?${_csrf.parameterName}=${_csrf.token}&clientId="
							+ orgRelationID,
					success : function(data) {
						var json = JSON.parse(data);
						var result = "";
						result = result
								+ '<div class="project_tabs_inn_table">';
						result = result + '<div class="project_details">';
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Client name:</strong>';
						result = result + '<span><input type="text" onBlur="inlineEditForProjects('+orgRelationID+',\'clientName\');" id="clientName" value="'+json.orgName+'" placeholder="Project name" class="project_detfield"></span>';
						result = result + '<div class="clearfix"></div>';
						result = result + '</div>';
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Website:</strong>';
						result = result
								+ '<span><input type="text" onBlur="inlineEditForProjects('+orgRelationID+',\'clientWebsite\');" id="clientWebsite"  value="'+json.webSite+'" placeholder="Project website" class="project_detfield"></span><div class="clearfix"></div>';
						result = result + '</div>';
					
					
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Client City:</strong>';
						result = result + '<span><input type="text" onBlur="inlineEditForProjects('+orgRelationID+',\'clientCity\');" id="clientCity" value="'+json.city+'" placeholder="Project city" class="project_detfield"></span><div class="clearfix"></div>';
						result = result + '</div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Client State:</strong>';
						result = result + '<span><input type="text" onBlur="inlineEditForProjects('+orgRelationID+',\'clientSate\');" id="clientSate" value="'+json.state+'" placeholder="Project state" class="project_detfield"></span><div class="clearfix"></div>';
						result = result + '</div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Client Country:</strong>';
						result = result + '<span><input type="text" onBlur="inlineEditForProjects('+orgRelationID+',\'clientCountry\');" id="clientCountry" value="'+json.country+'" placeholder="Project country" class="project_detfield"></span><div class="clearfix"></div>';
						result = result + '</div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Email:</strong>';
						result = result
								+ '<span><input type="text" onBlur="inlineEditForProjects('+orgRelationID+',\'clientEmail\');" id="clientEmail" value="'+json.email+'" placeholder="Project email" class="project_detfield"></span><div class="clearfix"></div>';
						result = result + '</div>';
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Phone number:</strong>';
						result = result
								+ '<span><input type="text" onBlur="inlineEditForProjects('+orgRelationID+',\'clientPhoneNumber\');" id="clientPhoneNumber" value="'+json.phone+'" placeholder="Project phone" class="project_detfield"></span><div class="clearfix"></div>';
						result = result + '</div>';
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Projects:</strong>';
						result = result
								+ '<span><input type="text" value="'+json.projectsCont+'" placeholder="Project count" class="project_detfield"></span><div class="clearfix"></div>';
						result = result + '</div>	';
						result = result + '<div class="clearfix"></div>';
						result = result + '</div>';
						result = result + '</div>';

						$("#tab-2").empty();
						$("#tab-2").append(result);

					}
				});
	}
</script>
<script> 
function  inlineEditClientProjects(projectId,localFieldName){
	var sourceleadUrl ='${sourceleadUrl}';
	var fieldName = localFieldName;
	var fieldValue = $("#"+localFieldName).val();
	
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
function getStartDatePickerById(projectId,localFieldName){
	 $('#startDate').datepicker({
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
function getEndDatePickerById(projectSequeneNumber,localFieldName){
	$('#endDate').datepicker({
	      dateFormat: 'yy-mm-dd',
	      onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() + 1);
	        },
	        onClose: function() {
	        	inlineEditClientProjects(projectSequeneNumber,localFieldName);
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
		});
		$(".proj_close").click(function() {
			$(".clients_side_wrap").removeClass("clients_side_wrap_active");
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