<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section class="content-wraper"
	onclick="popClose(); piplineexportClose();">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Job Fair Summary</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="/app/dashboard/3">Home</a> »<a
				href="/app/jobs?viewTab=jobFair">Jobs Fairs</a>»Job Fair Details
		</div>
	</section>
	<div class="timesheet_out">
		<div class="clients_side_wrap_inn no_pad">
			<div id="requirementSideDiv">
				<!-- Modal -->
				<div class="proj_close"></div>
				<div class="project_side_wrap_inn_tabs">
					<div class="clearfix"></div>
					<div class="project_tabs">
						<div id="details-div" class="require_wrap_details">
							<div class="clearfix">
								<div class="clearfix row">
									<div class="col-md-12 divskill divsAssigned pull-left">
									<div class="clearfix">
										<h2 class="pull-left noPad mt5">Job Fair Details:</h2>
										<button class="btn btn-primary pull-right btn-sm mb10" onclick="editJobFair();" type="button">Edit</button>
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
										<h1>Fair Job(s):</h1>
										<div class="clearfix" id="selectedJobsDiv">
											<c:forEach items="${jobFair.jobFairRequirementList}" var="req">
												<div class="require_one">
													<div class="multiJobTitle clearfix">
														<h2 class="pull-left">${req.requirementTitle}</h2>
													</div>
													<div class="multiJobDate clearfix">
														<c:choose>
															<c:when test="${req.fairDates != null && req.fairDates != ''}">
																<c:forEach items="${req.fairDates.split(',')}" var="date">
																<span class="reqDate">${date}</span>
																</c:forEach>
															</c:when>
															<c:otherwise>
																No Date(s) selected
															</c:otherwise>
														</c:choose>
													</div>
												</div>
											</c:forEach>
										</div>
										<div class="clearfix"></div>
									</div>
									<div class="clearfix"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="loading"></div>
</section>
<script type="text/javascript">
	var sourceleadURL = '${sourceleadUrl}';
	function editJobFair() {
		location.href = sourceleadURL + "/editJobFair?uuid=${jobFair.uuid}";
	}
</script>