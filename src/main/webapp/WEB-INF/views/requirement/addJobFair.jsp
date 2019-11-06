<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Job Fair</h2>
		</div>
		<div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo;<a href="${SourceLeadWeb_atsUrl}/jobsList?viewTab=jobFair"> Job Fairs </a> &raquo; JobFair</div>
	</section>
	
	
	<div class="content-area content-new clearfix">
	
	<form id="jobFairForm" method="post">
		<div class="contentareaInner-styles clearfix">
			<div class="clearfix form-horizontal minHeight_225 minHeightcol5">
				<div class="min">
					<input type="hidden" id="id" value="${jobFair!=null ? jobFair.id : 0}"/>
					<div class="col-md-5">
						<label class="clearfix">Name<span class="text-danger">&nbsp;*</span></label>
						<div class="clearfix">
							<input type="text" id="fairName" value="${jobFair.name}" class="form-control" name="name" field-name="Name" data-validation="required validate_length length1-100">
						</div>
					</div>
					<div class="col-md-5">
						<label class="clearfix">Venue</label>
						<div class="clearfix positionRelative">
							<input type="text" id="venue" value="${jobFair.venue}" name="venue" class="form-control" data-validation="validate_length length0-200">
							<div class="fa fa-refresh btn btn-primary refresh-icon" title="Clear" onclick="refreshLocation();"></div>
						</div>
					</div>
					<div class="col-md-5">
						<div class="row">
							<div class="col-md-6">
								<label class="clearfix">Start Date<span class="text-danger">&nbsp;*</span></label>
								<div class="clearfix">
									<input type="text" class="form-control calendar" id="startDate" value="${jobFair.startDateStr}"
										name="startDate" field-name="Start Date"
										placeholder="Start Date" data-validation="required"
										readonly="readonly">
								</div>
							</div>
							<div class="col-md-6">
								<label class="clearfix">End Date<span class="text-danger">&nbsp;*</span></label>
								<div class="clearfix">
									<input type="text" class="form-control calendar" id="endDate" value="${jobFair.endDateStr}"
										name="endDate" field-name="End Date" placeholder="End Date"
										data-validation="required" readonly="readonly">
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-5">
						<label class="clearfix">Budget / Cost<span class="text-danger">&nbsp;*</span></label>
						<div class="clearfix">
							<input type="text" class="form-control" id="budget" value="${jobFair != null ? jobFair.budget : 0}" name="budget" placeholder="Expected cost of JobFair" field-name="Budget" data-validation="required validate_length length1-8">
						</div>
					</div>
					
					<div class="col-md-5">
						<label class="clearfix">Description</label>
						<div class="clearfix positionRelative">
							<textarea class="form-control" rows="4" id="jobFairDescription" placeholder="Short note about the fair">${jobFair.description}</textarea>
						</div>
					</div>
					
					<div class="col-md-5">
						<label class="clearfix">Notify to</label>
						<div class="clearfix positionRelative">
							<textarea class="form-control" rows="4" id="notifyTo" placeholder="Enter multiple Email Id's separated by commas(,)" data-validation="validate_length length0-500">${jobFair.notifyTo}</textarea>
						</div>
					</div>
					
					<div class="col-md-5 noPad mt">
						<div class="col-md-4">
							<label class="clearfix">Posted on ${jobFair.postedFrom}<span class="text-danger">&nbsp;*</span></label>
							<div class="clearfix dropdown-select">
								<select class="form-control" id="requirementsTimeRange" field-name="Posted From" data-validation="required"
									onchange="getRequirements();" ${jobFair.id == 0 ? "disabled='disabled'" : ""}>
									<option value="today" ${jobFair.postedFrom eq 'today' ? "selected='selected'" : ""}>Today</option>
									<option value="yesterday" ${jobFair.postedFrom eq 'yesterday' ? "selected='selected'" : ""}>Yesterday</option>									
									<option value="week" ${jobFair == null || jobFair.postedFrom eq 'week' ? "selected='selected'" : ""}>1 week</option>
									<option value="twoweeks" ${jobFair.postedFrom eq 'twoweeks' ? "selected='selected'" : ""}>2 weeks</option>
									<option value="month" ${jobFair.postedFrom eq 'month' ? "selected='selected'" : ""}>1 month</option>
									<option value="twomonths" ${jobFair.postedFrom eq 'twomonths' ? "selected='selected'" : ""}>2 months</option>
									<option value="all" ${jobFair.postedFrom eq 'all' ? "selected='selected'":""}>All</option>
								</select>
							</div>
						</div>
						<div class="col-md-8">
							<label class="clearfix">Select Client</label>
							<div class="clearfix dropdown-select">
								<div class="row">
									<div class="col-md-12">
										<select data-placerholder="Select Client" multiple class="chosen-select" name="clientList" field-name="Client" 
											id="clientList" ${jobFair.id == 0 ? "disabled='disabled'" : ""}>
												<c:choose>
													<c:when test="${jobFair.selectedClient != null && jobFair.selectedClient != ''}">
														<c:forEach var="team" items="${add_clientList}">
															<c:if test="${team.locationId.id == loginOrgLocId}">
																<c:set var="selected" value="false"></c:set>
																<c:forEach items="${jobFair.selectedClient.split(',')}" var="clientName">
																	<c:if test="${clientName eq team.responderLocationId.org.orgName}">
																		<c:set var="selected" value="true"></c:set>
																	</c:if>
																</c:forEach>
																<option value='${team.responderLocationId.org.orgName}' ${selected?"selected='selected'":""}>${team.responderLocationId.org.orgName}</option>
															</c:if>
															
															<c:if test="${team.responderLocationId.id == loginOrgLocId}">
																<c:set var="selected" value="false"></c:set>
																<c:forEach items="${jobFair.selectedClient.split(',')}" var="clientName">
																	<c:if test="${clientName eq team.locationId.org.orgName}">
																		<c:set var="selected" value="true"></c:set>
																	</c:if>
																</c:forEach>
																<option value='${team.locationId.org.orgName}' ${selected?"selected='selected'":""}>${team.locationId.org.orgName}</option>
															</c:if>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:forEach var="team" items="${add_clientList}">
															<c:if test="${team.locationId.id == loginOrgLocId}">
																
																<option value='${team.responderLocationId.org.orgName}'>${team.responderLocationId.org.orgName}</option>
															</c:if>
															
															<c:if test="${team.responderLocationId.id == loginOrgLocId}">
																<option value='${team.locationId.org.orgName}'>${team.locationId.org.orgName}</option>
															</c:if>
														</c:forEach>
													</c:otherwise>
												</c:choose>
										</select>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-5 mt">
						<label class="clearfix">Select Job</label>
						<div class="clearfix">
							<div class="row">
								<div class="col-md-12">
									<select multiple class="chosen-select form-control" name="requirementsList" field-name="Job" 
										id="requirementsList" ${jobFair.id == 0 ? "disabled='disabled'" : ""}>
										<c:if test="${!empty requirementString}">
										<c:forEach items="${requirementStringrequirementString.split(',')}" var="req">
											<c:set var="selected" value="false"></c:set>
											<c:forEach items="${jobFair.jobFairRequirementList}" var="jobFairRequirement">
												<c:if test="${jobFairRequirement.requirementId == req.split(':')[0]}">
													<c:set var="selected" value="true"></c:set>
												</c:if>
											</c:forEach>
											<option value='${req.split(":")[0]}' ${selected?"selected='selected'":""}>${req.split(":")[1]}</option>
										</c:forEach>
									</c:if>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="col-md-5" id="selectedJobsParentDiv" style="${jobFair.jobFairRequirementList.size() > 0 ? '': 'display:none;'}">
						<label class="clearfix">Selected Job(s)</label>
						<div class="clearfix" id="selectedJobsDiv">

							<c:forEach items="${jobFair.jobFairRequirementList}" var="requirement">
								<div class="multiJob positionRelative" data-id="${requirement.id}" data-requirement_id="${requirement.requirementId}">
									<i class='fa fa-close close_multi_job' onclick='closeSelectedJobDiv(this)'></i>
									<div class="multiJobTitle clearfix">
										<h2 class="pull-left">${requirement.requirementTitle}</h2>
										<span class="pull-right mr">
											<input class="form-control calendar jobFairCalendar calendarIcon" readonly="readonly" type="text">
										</span>
									</div>
									
									<div class="multiJobDate clearfix"
										data-id="${requirement.requirementId}" data-name="${requirement.requirementTitle}">
										<c:choose>
											<c:when test="${requirement.fairDates != null && requirement.fairDates != ''}">
												<c:forEach items="${requirement.fairDates.split(',')}"
													var="date">
													<span class="reqDate" data-value="${date}">${date} <i
														class="fa fa-times" onclick="removeJobFairDate($(this))"></i>
													</span>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<div class="multiJobDate clearfix" data-id="${requirement.requirementId}" data-name="${requirement.requirementTitle}">
													<div>Select optional JobFair date(s) </div>
												</div>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
		<div class="clearfix footerstyelsNew">
			<div class="col-lg-12">
				<div class="pull-right">
					<input value="Save" class="btn btn-success btnSuccess" type="button" onclick="addJobFair();">
					<input value="Cancel" class="btn btn-default ml10" type="button" onclick="redirectToJobFairsList();">
				</div>
			</div>
		</div>
	</form>
	</div>
	<!-- Content Area Ends-->
</section>

<script type="text/javascript">
	var dateFormat = '';
	$(document).ready(function() {
		$("#clientList").trigger('chosen:update');
		$("#clientList").chosen();
		/* $('#clientList').find('option:first-child').prop('selected', false).end().trigger('chosen:updated'); */
		
		$("#requirementsList").trigger('chosen:update');
		/* $('#requirementsList').find('option:first-child').prop('selected', false).end().trigger('chosen:updated'); */
	});
	$(document).on('change', '#requirementsList', function(evt, params) {
		if (params.selected != undefined) {
			if($(".multiJob[data-requirement_id='" + params.selected + "']").length === 0)
				updateSelectedJobRows(params.selected, false);
		}
		if (params.deselected != undefined) {
			updateSelectedJobRows(params.deselected, true);
		}
	});
	$("#clientList").change(function() {
		getRequirements();
	});
	
	function closeSelectedJobDiv(ele) {
		$(ele).parent().remove();
		var id = $(ele).parent().data("requirement_id");
		$('#requirementsList').find("option[value='" + id + "']").prop("selected",false).end().trigger('chosen:updated');
	}
	
	function updateSelectedJobRows(id, removedFlag) {
		if(id===0 && removedFlag){
			$("#selectedJobsDiv").empty();
			$("#selectedJobsParentDiv").hide();
		} else {		
			$("#requirementsList option").each(function () {
				if($(this).val() === id) {
					if(removedFlag) {
						$("#selectedJobsDiv").find("div[data-requirement_id=" + $(this).val() + "]").remove();
						if($(".multiJob").length === 0) {
							$("#selectedJobsParentDiv").hide();
						}
						return;
					} else {
						var text="<div class='multiJob positionRelative' data-requirement_id=" + $(this).val() + ">";
						text += "<i class='fa fa-close close_multi_job' onclick='closeSelectedJobDiv(this)'></i>";
						text += "<div class='multiJobTitle clearfix'>";
						text += "<h2 class='pull-left'>" + $(this).text() + "</h2>";
						text += "<span class='pull-right mr'><input type='text' class='form-control calendar jobFairCalendar calendarIcon' readonly='readonly'></span>";
						text += "</div>";
						text += "<div class='multiJobDate clearfix' data-id=" + $(this).val() + " data-name='" + $(this).text() + "'>";
						text += "<div>Select optional JobFair date(s) </div>";
						text += "</div>";
						text += "</div>";
						$("#selectedJobsDiv").append(text);
						$("#selectedJobsParentDiv").show();
						return;
					}
				}
			});
			initializeDatePicker();
		}
	}
	
	function initializeDatePicker() {
		$('.jobFairCalendar').removeClass('hasDatepicker').datepicker({
			dateFormat : dateFormat,
			changeMonth : true,
			changeYear : true,
			minDate : $("#startDate").val(),
			maxDate : $("#endDate").val(),
			onSelect : function(selected) {
				var parentDiv = $(this).parents(".multiJob").find(".multiJobDate");
				parentDiv.find("div").remove();
				if(parentDiv.find("span[data-value='" + $(this).val() + "']").length === 0) {
					var selectedDate = $(this).val();
					var text = "<span class='reqDate' data-value='" + selectedDate + "'>" + selectedDate + "<i class='fa fa-times' onclick='removeJobFairDate($(this))'></i></span>";
					parentDiv.append(text);
				}
			}
		});
	}
	
	function removeJobFairDate(ele) {
		var parentDiv = $(ele).parents(".multiJobDate");
		$(ele).parent().remove();
		if(parentDiv.find("span").length > 0){
			parentDiv.find("div").remove();
		} else {
			parentDiv.append("<div>Select optional JobFair date(s) </div>");
		}
	}
	
	function checkSelectedDatesRange() {
		var minDate = $("#startDate").val();
		var maxDate = $("#endDate").val();
		$(".multiJobDate").each(function() {
			var length = $(this).find("span").length;
			$(this).find("span").each(function() {
				var spanValue = $(this).data("value"); 
				if((spanValue < minDate) || (spanValue > maxDate)) {
					$(this).remove();
					length--;
				}
			});
			if(length <= 0 && $(this).find("div").length === 0) {
				$(this).append("<div>Select optional JobFair date(s) </div>");
			}
		});
	}

	$(document).ready(
			function() {
				var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
				if(locationDate.match("yyyy-MM-dd")){
					dateFormat = 'yy-mm-dd';
				}if(locationDate.match("dd-MM-yyyy")){
					dateFormat = 'dd-mm-yy'; 
				}if(locationDate.match("MM-dd-yyyy")){
					dateFormat = 'mm-dd-yy'; 
				}if(locationDate.match("yyyy-dd-MM")){
					dateFormat = 'yy-dd-mm'; 
				}
				
				$('#startDate').datepicker({
					createButton:false,
	                displayClose:true,
	                closeOnSelect:false,
	                selectMultiple:true,
					dateFormat : dateFormat,
					changeMonth : true,
					changeYear : true,
					onSelect : function(selected) {
						var dt = $(this).datepicker('getDate', '');
						dt.setDate(dt.getDate());
						$("#endDate").datepicker("option", "minDate", dt);
						addValidClass();
						initializeDatePicker();
						checkSelectedDatesRange();
					}
				});
				$('#endDate').datepicker({
					dateFormat : dateFormat,
					changeMonth : true,
					changeYear : true,
					minDate : $("#startDate").val(),
					onSelect : function(selected) {
						addValidClass();
						initializeDatePicker();
						checkSelectedDatesRange();
					}
				});
				function addValidClass() {
					if($("#startDate").val() !== "" & $("#endDate").val() !== "") {
						$("#requirementsTimeRange").prop('disabled', false);
						$("#requirementsList").prop('disabled', false).trigger("chosen:updated");
					} else {
						$("#requirementsTimeRange").prop('disabled', true);
						$("#requirementsList").prop('disabled', true).trigger("chosen:updated");
					}
					
					var startDate = document.getElementById("startDate");
					if (startDate.value !== "") {
						$(startDate).parent().find('p.jquery_form_error_message').remove();
					}

					var endDate = document.getElementById("endDate");
					if (endDate.value !== "") {
						$(endDate).parent().find('p.jquery_form_error_message').remove();
					}
				}
				if("${jobFair}" == null)					
					getRequirements();
				else
					initializeDatePicker();
					
				initializeMap();
				
				$("#requirementsList").chosen();
				$("#requirementsList").trigger('chosen:update');
				/* $('#requirementsList').find('option:first-child').prop('selected', false).end().trigger('chosen:updated'); */
			});

	function getRequirements() {
		var atsUrl = '${SourceLeadWeb_atsUrl}';
		var postedDateRange = $("#requirementsTimeRange").val();
		var selectedClients = $("#clientList").val() !== null ? $("#clientList").val() : "";
		$.ajax({
			type : "GET",
			url : atsUrl
					+ "/getRequirementListByPostedDateAndSelectedClients?postedDateRange=" + postedDateRange + "&selectedClients=" + selectedClients,
			success : function(data) {
				if (data != null && data != 'undefined' && data != "" && data != '[]') {
					var json = JSON.parse(data);
					var result = "";
					for (var i = 0; i < json.length; i++) {
						var opt = json[i];
						var jobId = "";
						if (null != opt.jobId && "" != opt.jobId
								&& 'undefined' != opt.jobId) {
							jobId = " - " + opt.jobId;
						}
						result = result + '<option value="'+opt.id+'">' + opt.jobTitle + '' + jobId + '</option>';
					}
					$("#requirementsList").empty();
					$("#requirementsList").append(result);
					$("#requirementsList").chosen();
					$("#requirementsList").trigger('chosen:update');
					$('#requirementsList').find('option:first-child').prop('selected', false).end().trigger('chosen:updated');
					updateSelectedJobRows();
				} else {
					var result = "";
					$("#requirementsList").empty();
					$("#requirementsList").append(result);
					$("#requirementsList").chosen();
					$("#requirementsList").trigger('chosen:update');
					$('#requirementsList').find('option:first-child').prop('selected', true).end().trigger('chosen:updated');
					$("#requirementsList_chosen").find("input").val("No Jobs Found")
				}
			}
		});
	}

	function initializeMap() {
		autocomplete = new google.maps.places.Autocomplete((document
				.getElementById("venue")), {
			types : [ 'geocode' ],
		});

		google.maps.event.addListener(autocomplete, 'place_changed', function() { place = autocomplete.getPlace(); });
	}

	function refreshLocation() {
		$("#venue").val("");
	}

	var validationSettings = {
		errorMessagePosition : 'element',
		borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd'
	};
	function addJobFair() {
		var atsUrl = '${SourceLeadWeb_atsUrl}';
		if ($('#jobFairForm').validate(false, validationSettings)) {
			var flag = true;
			if($("#budget").val() !== "") {
				if(!jQueryFormUtils.validateFloat($("#budget").val())) {
					flag = false;
					$("#budget").parent().append("<p class='jquery_form_error_message'>Enter valid number</p>");
					return;	
				}
			}
			if ($("#notifyTo").val() !== "") {
				for (var e in $("#notifyTo").val().split(",")) {
					if (!jQueryFormUtils.validateEmail($("#notifyTo").val().split(",")[e].replace(/\s+/g, ''))) {
						flag = false;
						$("#notifyTo").parent().append("<p class='jquery_form_error_message'>Enter valid email id</p>");
						return;
					}
				}
			}
			if (flag) {
				var id = $("#id").val();
				$("#notifyTo").parent().find("p").remove();
				/* var formData = $($('#jobFairForm')[0]).serialize(); */
				var requirements = [];
				$(".multiJob").each(function() {				
					var fairDates = "";
					$(this).find(".reqDate").each(function() {
						fairDates += (fairDates === "" ? "" : ",") + $(this).data("value");
					});
					requirements.push({
						id : $(this).data("id") ? $(this).data("id") : 0,
						requirementId : $(this).data("requirement_id") ? $(this).data("requirement_id") : 0,
						fairDates : fairDates
					});
				});
				var selectedClients = "";
				var clients = $("#clientList").val();
				for(var e in clients) {
					/* clients.push({name : selectedClients[e]}); */
					selectedClients += (selectedClients !== "" ? "," : "") + clients[e];
				}
				var data = {
						id: id,
						name : $("#fairName").val(),
						budget : $("#budget").val(),
						description : $("#jobFairDescription").val(),
						venue : $("#venue").val(),
						startDate : $("#startDate").val(),
						endDate : $("#endDate").val(),
						postedFrom : $("#requirementsTimeRange").val(),
						selectedClient : selectedClients,
						notifyTo : $("#notifyTo").val(),
						requirementJSONData : JSON.stringify(requirements)
				};
				$.post(atsUrl + "/storeFairJob?" + tokenParamName + "=" + tokenValue + "", data, function(result) {
					if (result.response === "SESSION_TIMEDOUT") {
						location.replace(sourceleadUrl);
					} else if (result.response === "AWKWARD") {
						location.href = "/errorPage";
					} else if (result.response === "DUPLICATE_NAME" || result.response === "INVALID_DATA") {
						showError(result.responseMessage);
					}  else if (result.response === "SUCCESS") {
						$(".success_pop span").html("JobFair " + (id > 0 ? "updated":"created") + " successfully...");
						$(".success_pop").show();
						setTimeout(function() {
							redirectToJobFairsList();
				 		}, 500);
					} else if (result.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
						showError("Operation failed, please try again");
					}
				});
			}
			return true;
		} else {
			return false;
		}
	}

	function clearJobFairForm() {

	}
	
	function redirectToJobFairsList() {
		location.href = "${SourceLeadWeb_atsUrl}/jobsList?viewTab=jobFair";
	}
</script>