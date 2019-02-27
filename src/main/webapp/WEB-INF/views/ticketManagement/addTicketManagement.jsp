<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript" src="/resources/js/ticketManagement/ticket_configuration.js"></script>
<script src="/resources/js/summernote.js?staticTime=${staticTime}"></script>
<link href="/resources/css/summernote.css?staticTime=${staticTime}" rel="stylesheet" />
<script src="/resources/js/editor.js?staticTime=${staticTime}"></script>
<link href="/resources/css/editor.css?staticTime=${staticTime}" type="text/css" rel="stylesheet" />
<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Add Ticket Management</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">
				Home </a> &raquo; <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo;
			<a href="<c:url value='${appPath}/ticketManagementList'/>">Ticket
				Management</a>&raquo; Add Ticket Management
		</div>
	</section>
	<form id="addTicketManagementForm">
		<div class="content-area content-new clearfix">
			<div class="row minHeightcol6 mt mb">
				<div class="col-md-6">
					<label class="col-md-3">Type</label>
					<div class="col-md-9">
						<select class="chosen-select" name="ticketTypeId" field-name="Type" id="ticketType" data-validation="required">
							<option value="">Select Ticket Type</option>
							<c:forEach items="${ticketTypeList}" var="ticketType">
								<option value="${ticketType.id}">${ticketType.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="col-md-6">
					<label class="col-md-3">Category</label>
					<div class="col-md-9">
						<select class="chosen-select" name="ticketCategoryId" field-name="Category" id="ticketCategory" data-validation="required">
							<option value="">Select Ticket Category</option>
							<c:forEach items="${ticketCategoryList}" var="ticketCategory">
								<option value="${ticketCategory.id}">${ticketCategory.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="clearfix"></div>
				</div>
	
				<div class="col-md-6">
					<label class="col-md-3">Priority</label>
					<div class="col-md-9">
						<select class="chosen-select" name="ticketPriorityId" field-name="Priority" id="ticketPriority" data-validation="required">
							<option value="">Select Ticket Priority</option>
							<c:forEach items="${ticketPriorityList}" var="ticketPriority">
								<option value="${ticketPriority.id}">${ticketPriority.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="clearfix"></div>
				</div>
	
				<div class="col-md-6">
					<label class="col-md-3">Status</label>
					<div class="col-md-9">
						<select class="chosen-select" name="ticketStatusId" field-name="Status" id="ticketStatus" data-validation="required">
							<option value="">Select Ticket Status</option>
							<c:forEach items="${ticketStatusList}" var="ticketStatus">
								<option value="${ticketStatus.id}">${ticketStatus.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="clearfix"></div>
				</div>
	
				<div class="col-md-6">
					<label class="col-md-3">Assign To</label>
					<div class="col-md-9">
						<select multiple class="chosen-select form-control" name="assignedTo" field-name="Assign To" id="assignedTo" data-validation="required">
							<c:forEach items="${userList}" var="user">
								<option value="${user.id}">${user.firstName}&nbsp;${user.lastName}</option>
							</c:forEach>
						</select>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="col-md-6">
					<label class="col-md-3">Ticket Id</label>
					<div class="col-md-9">
						<input type="text" class="form-control" name="generatedId" field-name="Ticket Id" data-validation="required validate_length length1-100">
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="col-md-6">
					<label class="col-md-3">Notify To</label>
					<div class="col-md-9">
						<textarea class="form-control" rows="4" id="notifyTo" name="notifyTo"
							placeholder="Enter multiple Email Id's separated by commas(,)" data-validation="validate_length length0-500">
						</textarea>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="clearfix"></div>
				<div class="clearfix mb col-md-12">
					<label class="col-md-3">Description</label>
					<div class="col-md-12 txtEditor_p">
						<textarea id="txtEditor" name="description" value="${requirement.description}" data-validation="validate_length length1-500">
							class="form-control newContrtextarea">${requirement.description}
						</textarea>
					</div>
				</div>
	
				<div class="row mb">
						<div class="input-group col-md-11 pull-left">
							<label class="input-group-btn"> <span
								class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse
									File <input type="file" style="display: none;" multiple
									id="uploadBtn" class="upload"> </span>
							</label> <input type="text" class="form-control" readonly id="uploadFile"
								placeholder="Upload Resume ...">
						</div>
						<div class="input-group col-md-1 colorRed text-center">
							<i class="fa fa-times cursorPointer mt10"></i>
						</div>
					</div>
			</div>
		</div>
		<div class="form-footer clearfix mt">
			<div class="col-lg-12">
				<div class="pull-right">
					<input type="button" value="Save" class="btn btn-primary" id="save" onclick="saveTicket();">
					<input type="button" value="Cancel" class="btn btn-default ml" id="cancel" onclick="cancelTicket();">
				</div>
			</div>
		</div>
	</form>
</section>
					<!-- =================== saving the addTicketManagement ================================ -->
<script>
	$(document).ready(function() {
		$("#txtEditor").Editor();
	});
	function saveTicket() {
		if ($('#addTicketManagementForm').validate(false, validationSettings)) {
			if ($("#notifyTo").val() !== "") {
				for (var e in $("#notifyTo").val().split(",")) {
					if (!jQueryFormUtils.validateEmail($("#notifyTo").val().split(",")[e].replace(/\s+/g, ''))) {
						flag = false;
						$("#notifyTo").parent().append("<p class='jquery_form_error_message'>Enter valid email id</p>");
						return;
					}
				}
			}
			var formData = getFormData("addTicketManagementForm");
			$.post(sourceleadUrl + "/saveTicket", formData, function(response) {
				if (response && response.response === "INVALID_DATA") {
					showError(response.responseMessage);
				} else if (response.response === "AWKWARD") {
					location.href = "/errorPage";
				} else if (response.response === "SUCCESS") {
					if(response.object === true) {
						alert(response.responseMessage);
					}
				} else if (response.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
					showError("Operation failed, please try again");
				}
			});
			return true;
		} else {
			return false;
		}
	}
	function cancelTicket() {
		if(document.getElementById("addTicketManagementForm") !== null)
			document.getElementById("addTicketManagementForm").reset();
		$(".mask, #modalTasktype").fadeOut();
	}
</script>
<jsp:include page="../masterFooter.jsp" />