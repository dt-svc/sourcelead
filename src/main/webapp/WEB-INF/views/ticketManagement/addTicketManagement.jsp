<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<jsp:include page="../ticketManagement/ticketPopups.jsp" flush="true"></jsp:include>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript" src="/resources/js/ticketManagement/ticket.js"></script>
<script src="/resources/js/summernote.js?staticTime=${staticTime}"></script>
<link href="/resources/css/summernote.css?staticTime=${staticTime}" rel="stylesheet" />
<script src="/resources/js/editor.js?staticTime=${staticTime}"></script>
<link href="/resources/css/editor.css?staticTime=${staticTime}" type="text/css" rel="stylesheet" />

<section class="content-wraper">
	<div class="success_pop" >
		<i class="fa fa-check-circle"></i><span></span>
	</div>
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Add Ticket Management</h2>
		</div>
		<div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/ticketManagementList'/>">Ticket List</a> &raquo; ${ticketSummary != null ? "Edit" : "Add"} Ticket</div>
s	</section>
	<form id="addTicketManagementForm">
		<input type="hidden" name="id" value="${ticketSummary != null ? ticketSummary.id : 0}"/>
		<div class="content-area content-new clearfix">
			<div class="row minHeightcol6 mt">
				<div class="col-md-6">
					<label class="col-md-3">Ticket Title <b class="valStar">*</b></label>
					<div class="col-md-9">
						<input type="text" class="form-control" name="title" value="${ticketSummary.title}" id ="ticketTitleId" field-name="Ticket Title" data-validation="required validate_length length1-100">
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="col-md-6" id = "ticketDivId">
					<label class="col-md-3">Ticket Id <b class="valStar">*</b></label>
					<div class="col-md-9">
						<input type="text" class="form-control" name="generatedId" value="${ticketSummary.generatedId}" id="ticketId" field-name="Ticket Id"  onblur="validateTicketId('${ticketSummary.id}');" data-validation="required validate_length length1-20">
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="col-md-6">
					<label class="col-md-3">Type <b class="valStar">*</b></label>
					<div class="col-md-9">
						<select class="chosen-select" name="ticketTypeId" field-name="Type" id="ticketType" data-validation="required">
							<option value="">Select Ticket Type</option>
							<c:forEach items="${ticketTypeList}" var="ticketType">
								<option value="${ticketType.id}" ${ticketSummary.ticketTypeId == ticketType.id ? "selected='selected'" : ""}>${ticketType.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="col-md-6">
					<label class="col-md-3">Category <b class="valStar">*</b></label>
					<div class="col-md-9">
						<select class="chosen-select" name="ticketCategoryId" field-name="Category" id="ticketCategory" data-validation="required" onchange="getCategoryAssignToUsers();">
							<option value="">Select Ticket Category</option>
							<c:forEach items="${ticketCategoryList}" var="ticketCategory">
								<option value="${ticketCategory.id}" ${ticketSummary.ticketCategoryId == ticketCategory.id ? "selected='selected'" : ""}>${ticketCategory.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="col-md-6">
					<label class="col-md-3">Priority <b class="valStar">*</b></label>
					<div class="col-md-9">
						<select class="chosen-select" name="ticketPriorityId" field-name="Priority" id="ticketPriority" data-validation="required">
							<option value="">Select Ticket Priority</option>
							<c:forEach items="${ticketPriorityList}" var="ticketPriority">
								<option value="${ticketPriority.id}" ${ticketSummary.ticketPriorityId == ticketPriority.id ? "selected='selected'" : ""}>${ticketPriority.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="col-md-6">
					<label class="col-md-3">Status <b class="valStar">*</b></label>
					<div class="col-md-9">
						<select class="chosen-select" name="ticketStatusId" field-name="Status" id="ticketStatus" data-validation="required">
							<option value="">Select Ticket Status</option>
							<c:forEach items="${ticketStatusList}" var="ticketStatus">
								<option value="${ticketStatus.id}" ${ticketSummary.ticketStatusId == ticketStatus.id ? "selected='selected'" : ""}>${ticketStatus.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="col-md-6">
					<label class="col-md-3">Assign To</label>
					<div class="col-md-9">
						<select multiple class="chosen-select form-control" name="assignedTo" field-name="Assign To" id="assignedTo">
							<c:forEach items="${userList}" var="user">
								<option value="${user.id}">${user.firstName}&nbsp;${user.lastName}</option>
							</c:forEach>
						</select>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="col-md-6">
					<label class="col-md-3">Notify To</label>
					<div class="col-md-9">
						<textarea class="form-control" rows="4" id="notifyTo" name="notifyTo" field-name="NotifyTo" placeholder="Enter multiple Email Id's separated by commas(,)">${ticketSummary.notifyTo}</textarea>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="clearfix"></div>
				<div class="clearfix mb col-md-12">
					<label class="col-md-3">Description <b class="valStar">*</b></label>
					<div class="col-md-12 txtEditor_p">
						<textarea id="txtEditor" name="description" field-name="Description" value="" class="form-control newContrtextarea" data-validation="required">${ticketSummary.description}</textarea>
					</div>
				</div>
				<div class="col-md-6">
			        <label class="col-md-3">Attachments</label>
			        <div class="col-md-9" id="attachmentsDiv">
				        <c:choose>
				        	<c:when test="${ticketSummary.attachmentList.size() > 0}">
				        		<c:set var="attachmentCount" value="0"></c:set>
				        		<c:set var="deleteFlag" value="false"></c:set>
				        		<c:forEach items="${ticketSummary.attachmentList}" var="attachment">
				        			<div class="row mb" data-index="${attachmentCount}">
							            <div class="input-group col-md-11 pull-left">
											<label class="input-group-btn">
												<span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
													<input type="file" style="display: none;" id="uploadFile${attachmentCount}" data-name="file" data-index="${attachmentCount}" class="upload" onchange='validateUploadFile(this, ${attachmentCount})' >
												</span>
											</label>
							              	<input type="text" class="form-control fileInput" readonly id="uploadFileInput${attachmentCount}" data-name="input" placeholder="Upload File ..." value="${attachment.fileName}">
							            </div>
							            	<c:choose>
								            	<c:when test="${deleteFlag}">
										            <div class="input-group col-md-1 colorRed text-center">
										             	<i class="fa fa-times cursorPointer mt10" data-name="delete" onclick="removeAttachmentRow(this, ${attachmentCount})"></i>
										            </div>
								            	</c:when>
								            	<c:otherwise>
													<div class="input-group col-md-1 text-primary text-center">
									             		<i class="fa fa-plus cursorPointer mt10" onclick="addAttachmentRow();"></i>
									             	</div>
								            	</c:otherwise>
							            	</c:choose>
									</div>
									<c:set var="attachmentCount" value="${attachmentCount + 1}"></c:set>
									<c:set var="deleteFlag" value="true"></c:set>
				        		</c:forEach>
				        	</c:when>
				        	<c:otherwise>
				        		<div class="row mb" data-index="0">
						            <div class="input-group col-md-11 pull-left">
										<label class="input-group-btn">
											<span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
												<input type="file" style="display: none;" id="uploadFile0" data-name="file" data-index="0" class="upload" onchange='validateUploadFile(this, 0)'>
											</span>
										</label>
						              	<input type="text" class="form-control fileInput" readonly id="uploadFileInput0" data-name="input" placeholder="Upload File ...">
						            </div>
						            <div class="input-group col-md-1 text-primary text-center">
						             	<i class="fa fa-plus cursorPointer mt10" onclick="addAttachmentRow();"></i>
						            </div>
								</div>
				        	</c:otherwise>
				        </c:choose>
			      	</div>
			      </div>
			</div>
		
		
		<c:set var="attachmentCount" value="0"></c:set>
		<c:forEach items="${ticketSummary.attachmentList}" var="attachment">
			<input type="hidden" name="fileInfo[${attachmentCount}].fileModified" data-id="uploadFile${attachmentCount}" value="false">
			<input type="hidden" name="fileInfo[${attachmentCount}].fileDbId" data-id="uploadFile${attachmentCount}" value="${attachment.uuid}">
			<input type="hidden" name="fileInfo[${attachmentCount}].fileURL" data-id="uploadFile${attachmentCount}" value="${attachment.fileUrl}">
			<input type="hidden" name="fileInfo[${attachmentCount}].fileName" data-id="uploadFile${attachmentCount}" value="${attachment.fileName}">
			<input type="hidden" name="fileInfo[${attachmentCount}].contentType" data-id="uploadFile${attachmentCount}" value="${attachment.fileType}">
			<c:set var="attachmentCount" value="${attachmentCount + 1}"></c:set>
		</c:forEach>
		<div class="form-footer clearfix mt">
			<div class="col-lg-12">
				<div class="pull-right">
					<input type="button" value="Save" class="btn btn-primary" id="save" onclick="saveTicket();"/>
					<input type="button" value="Cancel" class="btn btn-default ml" id="cancel"  onClick="javascript:window.location.href = '<c:url value='${appPath}/ticketManagementList'/>'"/>
				</div>
			</div>
		</div>
		
        </div>
	</form>
</section>
<script type="text/javascript">
	$(document).ready(function() {
		getTicketManagementDescription('${ticketSummary.uuid}');
		<c:forEach items="${ticketSummary.ticketAssignToList}" var="assignTo">
			$("#assignedTo option[value='${assignTo.userId}']").attr("selected", true);
	    </c:forEach>
		$("#assignedTo").chosen();
		$("#assignedTo").trigger("chosen:updated");
		$("#txtEditor").Editor();
	});
	var attachmentCount = ${ticketSummary.attachmentList.size() > 0 ? ticketSummary.attachmentList.size() : 1};
</script>

<jsp:include page="../masterFooter.jsp" />