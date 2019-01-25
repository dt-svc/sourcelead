<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Job Fair Summary</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="/app/dashboard/3">Home</a> »<a href="/app/jobs?viewTab=jobFair">Job Fairs</a>»Job Fair Details
		</div>
	</section>
	<div class="timesheet_out">
		<div class="clients_side_wrap_inn no_pad">
			<div id="requirementSideDiv">
				<!-- Modal -->
				<div class="proj_close"></div>
				<div class="project_side_wrap_inn_tabs">
					<ul class="project-tabs-menu">
						<li class="current" id="detailsTab">
							<a href="javascript:void(0)">Details</a>
						</li>
						<li id="jobsTab">
							<a href="javascript:void(0)">Jobs</a>
						</li>
						
					</ul>
					<button class="btn btn-primary pull-right btn-sm mr" onclick="editJobFair();" type="button">Edit</button>
					<div class="clearfix"></div>
					<div class="project_tabs">
						<div id="detailsDiv" class="require_wrap_details">
							<div class="clearfix row">
								<div class="col-md-12 divskill divsAssigned pull-left">
									<div class="clearfix">
									</div>
									<div class="clearfix"></div>
									<div class="require_one">
										<div class="require_wrap_details_cta">
											<b>Name:</b><span>${jobFair.name}</span>
										</div>
										<div class="clearfix"></div>
										<div class="require_wrap_details_cta">
											<b>Venue:</b><span>${jobFair.venue != null && jobFair.venue !="" ? jobFair.venue : "--------"}</span>
										</div>
										<div class="clearfix"></div>
										<div class="require_wrap_details_cta">
											<b>Start Date:</b><span>${jobFair.startDateStr}</span>
										</div>
										<div class="clearfix"></div>
										<div class="require_wrap_details_cta">
											<b>End Date:</b><span>${jobFair.endDateStr}</span>
										</div>
										<div class="clearfix"></div>
										<div class="require_wrap_details_cta">
											<b>Budget:</b><span>${jobFair.budget != null && jobFair.budget !="" ? jobFair.budget : "--------"}</span>
										</div>
										<div class="clearfix"></div>
										<div class="require_wrap_details_cta">
											<b>Description:</b><span>${jobFair.description != null && jobFair.description != "" ? jobFair.description : "--------"}</span>
										</div>
										<div class="clearfix"></div>
										<div class="require_wrap_details_cta">
											<b>Notify To:</b><span>${jobFair.notifyTo != null && jobFair.notifyTo != "" ? jobFair.notifyTo : "--------"}</span>
										</div>
										<div class="clearfix"></div>
									</div>
									<div class="clearfix"></div>
								</div>
								<div class="clearfix"></div>
							</div>
						</div>
						<div id="jobsDiv" style="display: none;">
							<div class="clearfix row">
								<div class="col-md-12 divskill divsAssigned pull-left">
									<div class="">
										<div class="clearfix require_wrap_table dashboard-data filter_position positionRelative" style="min-height: 200px;">
											<div id="requirementList">
										    </div>
									    </div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="loading"></div>
	<div class='project_inn_mask'></div>
	<div id="fairDatesPopup" class="remove_user_pop remove_user_pop_a">
		<div>FairDates for jobFair</div>
		<p></p>
		<b>
			<input id="cancelStatusPopup" class="u_can_color" value="Close" onclick="closeFairDatesPop();" type="button">
		</b>
	</div>
	<div class="side_mask">
	  <div class="proj_close" onClick="activeTab();"></div>
	</div>
</section>
<jsp:include page="addApplicantFromRequirementList.jsp" />
<jsp:include page="requirementAssignedTo.jsp"/>
<script type="text/javascript">
	var sourceleadURL = '${sourceleadUrl}';
	var orgATS = '${SourceLeadWeb_ats}';
	var jobFairId = '${jobFair.id}';
	function test() {
		for(var e in requirements) {
			alert(requirements[e].id);
		}
	}
	$(document).ready(function() {
		getJobFairRequirements();
		$("#detailsTab").click(function () {
			$("#detailsTab").addClass("current");
			$("#jobsTab").removeClass("current");
			
			$("#detailsDiv").show();
			$("#jobsDiv").hide();
		});
		$("#jobsTab").click(function () {
			$("#jobsTab").addClass("current");
			$("#detailsTab").removeClass("current");
			
			$("#jobsDiv").show();
			$("#detailsDiv").hide();
		});
	});
	function editJobFair() {
		location.href = sourceleadURL + "/editJobFair?uuid=${jobFair.uuid}";
	}
	function getJobFairRequirements() {
		$.ajax({
	           type : "POST",
	           url: sourceleadUrl + "/getRequirementsByJobFair?jobFairId=" + jobFairId,    
	            success :function(data) {
	            	if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
			    		var json = data.data;
			            if(json != null && json != 'undefined' && json != "" && json != '[]'){
			           	 	$("#requirementList").empty();
			               	var result = "";
			               	result = result +"<table id='requirementPagination' class='display mt10' cellspacing='0' width='99.9%' >"; 
			                result = result +"<thead>"; 
			                result = result +"  <tr>"; 
			                result = result +"    <th>Actions</th>";
			                result = result +"    <th>Generated ID</th>"; 
			                result = result +"    <th>Title</th>";
			                if(orgATS == 'Y'){
			                	result = result +"    <th>Client</th>";
			                }
			                result = result +"    <th>Fair Date(s)</th>";
			                result = result +"    <th>Rate</th>"; 
			                result = result +"    <th>Priority</th>"; 
			                result = result +"    <th>Applicants</th>"; 
			                result = result +"    <th>Shortlisted</th>"; 
			                result = result +"    <th>Submitted</th>"; 
			                result = result +"    <th>Interview</th>"; 
			                result = result +"    <th>Offered</th>";
			                result = result +"    <th>Joined</th>";
			                result = result +"    <th>Status</th>";
//			                     result = result +"    <th>Release</th>"; 
			                result = result +"  </tr>"; 
			                result = result +"</thead>";
			                result = result +"<tbody>"; 
			                    
			                result = result + '<div class="new_side_wrap">';
			                result = result + '<div class="new_side_wrap_inn">';
			                result = result + '<div class="new_side_wrap_cta">';
			                result = result +'<div id="addApplicantDiv"></div>';
			                result = result +'</div>';
			                result = result +'</div>';
			                result = result +'</div>';
			                	 
			                var checkSize = json.length;
			                if(checkSize>10) {
			                	checkSize = 10;
							} else {
								checkSize = json.length;
							}
			                for (var i = 0; i < checkSize; i++) { 
			                	var opt = json[i]; 
			                   	result = result +"<tr>";
			                   	result = result +"<td>";
			    	            result = result + '<div class="tblminwidth-75">';
			    	                     
			    	         	'<sec:authorize access="hasAnyRole(\'ROLE_ADD_APPLICANT\')">';
				    		        if(opt.requirementSubmissionStatus == 'APPROVED' || opt.requirementSubmissionStatus == 'OPEN' || opt.requirementSubmissionStatus == 'NEW'){
				    		        	result = result +'<div class="text-center pull-left"><a href="javascript:void(0)" title="Add Applicant" onclick="getAddApplicant('+opt.id+',\''+opt.reqCoolingOffPeriod+'\');" class="add-applicant"><i class="fa fa-user-plus"></i></a></div>';
				    		        } else {
				    		         	result = result +"<div class='text-center pull-left'>---</div>";
				    		        }
			    	         	'</sec:authorize>';
			    	         	        
			    	         	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_REQUIREMENT\')">';
			    	         		result = result +'<div class="text-center pull-left ml10 mr10"><a href="javascript:void(0)" title="Assign To" data-toggle="modal" data-target="#myModal" onclick="getAssignedMembersDiv('+opt.id+');"><i class="fa fa-address-book '+(opt.assignedMembersCount == 0 ? "anchorBlack" : "")+'"></i></a></div>';
			    	         	'</sec:authorize>';
			    	         	        
			    	         	'<sec:authorize access="hasAnyRole(\'ROLE_RELEASE_REQUIREMENT\')">';
			    	            	if(opt.requirementSubmissionStatus == 'APPROVED' || opt.requirementSubmissionStatus == 'OPEN' || opt.requirementSubmissionStatus == 'NEW'){
			    	                	if(opt.isRequirementReleasedByuser == 'Y') {
			    	                    	result = result +"<div class='pull-left'><a href='<c:url value='/app/releaseRequirementToClientOrVendors?requirementUUID="+opt.requirementUUID+"'/>'><i class='fa fa-bullhorn success' title='Release Job'></i></a></div>"; 
			    	                    } else {	
			    	                    	result = result +"<div class='pull-left'><a href='<c:url value='/app/releaseRequirementToClientOrVendors?requirementUUID="+opt.requirementUUID+"'/>'><i class='fa fa-bullhorn' title='Release Job'></i></a></div>";
			    	                    }
			    					} else {
			    						result = result +"<div class='pull-left'>---</div>";
			    					} 
			    	             '</sec:authorize>';
			    	             result = result + '</div>';
			    	             result = result +"</td>";
			    	                     
			                   	 if(opt.generatedId!=null && opt.generatedId !='' && opt.generatedId !='undefined') {
			                   		result = result +"<td><div class=''><span title='"+opt.generatedId+"'>"+opt.generatedId+"</span></a></div></td>";
			                   	 } else {
			                   		result = result +"<td><div>---</div></td>";
			                   	 }
			                   	 if(opt.jobId!=null && opt.jobId !='' && opt.jobId !='undefined') {
			                   		result = result + "<td><a href='"+sourceleadUrl+"/requirementSummary?requirementUUID="+opt.requirementUUID+"'><div class='proj_show tbldata-dotdot_140'><span class='pull-left' title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span><i class='fa fa-binoculars'></i></div></a></td>";
			                   	 } else {
			                   		result = result + "<td><a href='"+sourceleadUrl+"/requirementSummary?requirementUUID="+opt.requirementUUID+"'><div class='proj_show tbldata-dotdot_140'><span class='pull-left' title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span><i class='fa fa-binoculars'></i></div></a></td>";
			                     }
			                     if(orgATS == 'Y') {
			    	             	if(opt.clientName != null && opt.clientName != '' && opt.clientName != 'undefined') {
			    	                    '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
			    	                    	if(opt.clientId != null) {
				    	                    	result = result + '<td><div  class="tbldata-dotdot-sm"><a href="'+sourceleadUrl+'/clientDashboard?orgRelationUUID='+opt.clientId.uuid+'" title="'+opt.clientName+'">'+opt.clientName+'</a></div></td>';
			    	                    	} else {
				    	                    	result = result + '<td><div  class="tbldata-dotdot-sm">'+opt.clientName+'</div></td>';
			    	                    	}
			    		           	    '</sec:authorize>';
			      	         
			    			           	'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
			    			     	    	result = result + '<td><div  class="tbldata-dotdot-sm">'+opt.clientName+'</div></td>';
			    			     		'</sec:authorize>';
				    				} else {
				    					result = result +"<td><div>---</div></td>";
				    	            }
								}
			                    if(opt.jobFairDates!=null && opt.jobFairDates !='' && opt.jobFairDates !='undefined') {
			                    	result = result + "<td><a href='javascript:void(0)' onclick=\"showFairDatesPop('"+ opt.jobTitle + "','" + opt.jobFairDates + "');\"><span class='pull-left' title='" + opt.jobFairDates + "'>" + opt.jobFairDates + "</span></a></td>";
			                    	/* result = result + "<td><a href='javascript:void(0)'><span class='pull-left' title='" + opt.jobFairDates + "'>" + opt.jobFairDates + "</span></td>"; */
				                } else {
				                	result = result +"<td><div>---</div></td>";
				                }
			                    if(opt.rate != null && opt.rate != '' && opt.rate != 'undefined' && opt.maxRate != null && opt.maxRate != '' && opt.maxRate != 'undefined') {
			    	            	result = result +"<td><div><span  title='"+opt.currencyCode+"&nbsp;"+opt.rate+"-"+opt.maxRate+"&nbsp;"+opt.rateType+"'>"+opt.currencyCode+"&nbsp;"+opt.rate+"-"+opt.maxRate+"&nbsp;"+opt.rateType+"</span></div></td>";
								} else if(opt.rate != null && opt.rate != '' && opt.rate != 'undefined') {
			    	            	result = result +"<td><div><span  title='"+opt.currencyCode+"&nbsp;"+opt.rate+"&nbsp;"+opt.rateType+"'>"+opt.currencyCode+"&nbsp;"+opt.rate+"&nbsp;"+opt.rateType+"</span></div></td>";
								} else {
			    					result = result +"<td><div><span><span>---</span></span></div></td>";
								}
			                    
			                    if(opt.priority != null && opt.priority != '' && opt.priority != 'undefined') {
			                    	result = result +"<td><div>"+opt.priority+"</div></td>";
			                    } else {
			                    	result = result +"<td><div>---</div></td>";
			                    }
			                    
			                    if(opt.noOfApplicants != null && opt.noOfApplicants != '' && opt.noOfApplicants != 'undefined') {		                     
			                   		result = result +"<td><a href='<c:url value='/app/requirementSummary?type=applicants&requirementUUID="+opt.requirementUUID+"'/>'><div>"+opt.noOfApplicants+"</div></a></td>";
			                    } else {
			                    	result = result +"<td><div>---</div></td>";
			                    }
			                    
			                    if(opt.noOfShortlistedApplicants != null && opt.noOfShortlistedApplicants != '' && opt.noOfShortlistedApplicants != 'undefined') {
			                    	result = result +"<td><a href='<c:url value='/app/requirementSummary?type=SHORTLIST&requirementUUID="+opt.requirementUUID+"'/>'><div>"+opt.noOfShortlistedApplicants+"</div></a></td>";
			                    } else {
			                    	result = result +"<td><div>---</div></td>";
			                    }
			                    
			                    if(opt.noOfSubmittedApplicants != null && opt.noOfSubmittedApplicants != '' && opt.noOfSubmittedApplicants != 'undefined') {
			    	            	result = result +"<td><a href='<c:url value='/app/requirementSummary?type=SUBMIT_TO_CLIENT&requirementUUID="+opt.requirementUUID+"'/>'><div>"+opt.noOfSubmittedApplicants+"</div></a></td>";
			                    } else {
			                    	result = result +"<td><div>---</div></td>";
			                    }
			                    
			                    if(opt.noOfInterviewedApplicants != null && opt.noOfInterviewedApplicants != '' && opt.noOfInterviewedApplicants != 'undefined') {
			                    	result = result +"<td><a href='<c:url value='/app/requirementSummary?type=INTERVIEW&requirementUUID="+opt.requirementUUID+"'/>'><div>"+opt.noOfInterviewedApplicants+"</div></a></td>";
			                    } else {
			                    	result = result +"<td><div>---</div></td>";
			                    }
			                    
			                    if(opt.noOfOfferedApplicants != null && opt.noOfOfferedApplicants != '' && opt.noOfOfferedApplicants != 'undefined') {
			                    	result = result +"<td><a href='<c:url value='/app/requirementSummary?type=OFFERMADE&requirementUUID="+opt.requirementUUID+"'/>'><div>"+opt.noOfOfferedApplicants+"</div></a></td>";
			                    } else {
			                    	result = result +"<td><div>---</div></td>";
			                    }
			                    
			                    if(opt.noOfJoinedApplicants != null && opt.noOfJoinedApplicants != '' && opt.noOfJoinedApplicants != 'undefined') {
			    	            	result = result +"<td><a href='<c:url value='/app/requirementSummary?type=JOINED&requirementUUID="+opt.requirementUUID+"'/>'><div>"+opt.noOfJoinedApplicants+"</div></a></td>";
			    	            } else {
			                    	result = result +"<td><div>---</div></td>";
			                    }
			                    
			                    if(opt.requirementSubmissionStatus!=null && opt.requirementSubmissionStatus !='' && opt.requirementSubmissionStatus !='undefined') {
			                   		result = result +"<td><div class=''><span title='"+opt.requirementSubmissionStatus+"'>"+opt.requirementSubmissionStatus+"</span></a></div></td>";
			                   	} else {
			                   		result = result +"<td><div>---</div></td>";
			                   	}
			                      
			                    if(checkSize>=10 && i==9) {
			                       	/* $("#viewmoresDiv").css("display", "block"); */
			                    	var text = "<div class='pull-right jobMoreBtn' id='viewmoresDiv'>";
			                    	text += "<a href='/app/jobs' class='btn btn-sm btn-primary pull-right' title='View more'> View More</a>";
			                       	text += "</div>";
			                       	$("#requirementListActions").append(text);
			    	 	   	   	}
			                    result = result +"</tr>";
			                    result = result +"</tr>";
			                    }
			                    result = result +" </tbody>";
			                    result = result +"</table>";
			                    $("#requirementList").append(result);
			                    /* $("#requirementLoading").hide(); */
			                    /* $("#assignedRequirementsDiv").css("display", "block"); */
			                    
			               	 	$(".proj_close, .side_mask").click(function() {
			    					$(".clients_side_wrap").removeClass("clients_side_wrap_active");
			    					$(".side_mask").fadeOut(200);
			    					$("body").css('overflow-y', 'auto');
			    				});	
			                    /* pagination(); */
			    	           	$('#requirementPagination').DataTable({ 
			    	            	"scrollCollapse": false,
			    	            	"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
			    	            	"bLengthChange": false,
			    	            	"bDestroy": true,
			    	            	"bFilter": false,
			    	            	"bPaginate":false,
			    	            	"ordering": true,
			    	            	"oLanguage": {
			    	                    "sSearch": "",
			    	                    "sZeroRecords": "", "sEmptyTable": ""
			    	               }
			    	            });
			            	} else {
			            		$("#requirementList").empty();
			            		$("#requirementList").append("<table id='noResultsTable'><tr class='odd'><td colspan='7' class='dataTables_empty' valign='top'></td></tr></table>");
			            	}
			    	} else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
	          },
	       }); 
	}
	function showFairDatesPop(jobTitle, dates) {
		$("#fairDatesPopup div").html("FairDates for " + jobTitle);
		$("#fairDatesPopup p").html(dates);
		$("#fairDatesPopup").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
	function closeFairDatesPop() {
		$("#fairDatesPopup").slideUp(200);
		$(".project_inn_mask").fadeOut(200);
	}
	function getAssignedMembersDiv(reqId){
		$("#summaryButtonsDiv").hide();
		$("#listButtonsDiv").show();
		$('#listSaveButton').removeAttr('onclick');
		$('#listSaveButton').attr('onClick', 'editAssignedTeamsOrUsers('+reqId+');');
		getAssignedMembersListForEdit(reqId);
		
	}
</script>