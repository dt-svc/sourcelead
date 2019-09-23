<jsp:include page="../../masterHeader.jsp" />
<jsp:include page="../../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="/resources/js/tax/it_declaration.js"></script>
<section class="content-wraper contentWraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>IT Declaration</h2>
		</div>
		<div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> <%-- &raquo; <a href="<c:url value='${appPath}/myPayrollInd'/>">My Payroll</a>  --%>&raquo; IT Declaration</div>
	</section>
	<jsp:include page="../org/formHeader.jsp"></jsp:include>
	<form action="" id="itDeclarationForm">
		<div class="contentareaInner-styles clearfix">
			<div class="clearfix form-horizontal minHeight_225 minHeightcol5">
				<div class="row min">
					<input type="hidden" value="${form.id}" name="taxFormId"/>
					<c:if test="${autoSelectUser}">
						<input type="hidden" value="${user.id}" name="userId"/>
					</c:if>
					<div class="col-md-6 col-md-offset-2">
						<div class="clearfix dropdown-select">
							<select class="selectpicker width100percnt" id="employeeSelect" ${autoSelectUser ? "disabled='disabled'" : ""}
								data-live-search="true" title="Select Employee" field-name="Employee" data-validation="required" name="userId">
								<%-- <c:forEach items="${userRoleList}" var="userRole">
									<option ${user.id == userRole.user.id ? "selected='selected'" : ""} value="${userRole.user.id}" data-id="${userRole.user.id}" data-name="${userRole.user.firstName.concat(' ' ).concat(userRole.user.lastName)}">
										${userRole.user.firstName} ${userRole.user.lastName}
									</option>
								</c:forEach> --%>
								<c:forEach items="${employeeList}" var="employee">
									<option ${user.id == employee.user.id ? "selected='selected'" : ""} value="${employee.user.id}" data-id="${employee.user.id}" data-name="${employee.user.firstName.concat(' ' ).concat(employee.user.lastName)}" data-pan="${employee.panNumber != null ? employee.panNumber : ''}" data-dob="${employee.dateOfBirth != null ? employee.dateOfBirth : ''}" data-associate_id="${employee.employeeId != null ? employee.employeeId : ''}">
										${employee.user.firstName} ${employee.user.lastName}
									</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="clearfix mt mb"></div>
					<div class="col-md-5">
						<label class="clearfix">Associate Name</label>
						<div class="clearfix">
							<input type="text" class="form-control" readonly="readonly"
								id="associateName" field-name="Associate Name" value="${user.firstName} ${user.lastName}"
								data-validation="required" placeholder="Associate Name">
						</div>
					</div>
					<div class="col-md-5">
						<label class="clearfix">Associate ID</label>
						<div class="clearfix">
							<input type="text" class="form-control" readonly="readonly" value="${user.id}"
								id="associateId" placeholder="Associate Id">
						</div>
					</div>
					<div class="col-md-5">
						<label class="clearfix">Pan No</label>
						<div class="clearfix">
							<input type="text" class="form-control" readonly="readonly" 
								id="associatePAN" placeholder="PAN No">
						</div>
					</div>
					<div class="col-md-5">
						<label class="clearfix">Select Year</label>
						<div class="clearfix">
							<select class="selectpicker width100percnt"
								data-live-search="true" title="Select Year" field-name="Year"
								data-validation="required" name="financialYear">
								<c:forEach items="${yearList}" var="year">
									<option ${year.value==true?"selected=selected":""} value="${year.key}">${year.key}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-5">
						<label class="clearfix">Date of Birth</label>
						<div class="clearfix">
							<input type="text" class="form-control" readonly="readonly"
								id="associateDOB" placeholder="Date Of Birth">
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="col-md-11">
					<c:set var="sectionCount" value="0"></c:set>
					<c:set var="sectionItemCount" value="0"></c:set>
						<c:forEach items="${form.sourceleadTaxSectionList}" var="section">
							<c:if test="${section.sectionItemList.size() > 0}">
								<table class="table table-itdeclaration table-bordered">
									<thead>
										<tr>
											<th colspan="4" class="text-info">${section.name}</th>
										</tr>
										<tr>
											<th>Items</th>
											<th>Max Limit</th>
											<th>Description</th>
											<th>Declared Amount</th>
											<th>Comment</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${section.sectionItemList}" var="sectionItem">
											<input type="hidden" name="submissionItemList[${sectionItemCount}].taxSectionId" value="${sectionItem.taxSectionId}">
											<input type="hidden" name="submissionItemList[${sectionItemCount}].taxSectionName" value="${sectionItem.taxSectionName}">
											<input type="hidden" name="submissionItemList[${sectionItemCount}].name" value="${sectionItem.name}">
											<input type="hidden" name="submissionItemList[${sectionItemCount}].maxLimit" value="${sectionItem.maxLimit}">
											<input type="hidden" name="submissionItemList[${sectionItemCount}].description" value="${sectionItem.description}">
											<tr>
												<td>${sectionItem.name}</td>
												<td>${sectionItem.maxLimit}</td>
												<td>${sectionItem.description != null && sectionItem.description != "" ? sectionItem.description : "--NA--"}</td>
												<td>
													<input type="text" class="form-control input-sm" name="submissionItemList[${sectionItemCount}].amount"
													placeholder='Enter Amount' field-name='Amount' value="0"
													data-validation='required validate_float'>
												</td>
												<td>
													<input type="text" class="form-control input-sm" name="submissionItemList[${sectionItemCount}].comment"
													placeholder='Enter Comment' field-name='Comment'>
												</td>
											</tr>
											<c:set var="sectionItemCount" value="${sectionItemCount + 1}"></c:set>
										</c:forEach>
									</tbody>
								</table>
								<div class="clearfix"></div>
							</c:if>
							<c:set var="sectionCount" value="${sectionCount + 1}"></c:set>
						</c:forEach>

						<table class="table table-itdeclaration table-bordered">
							<thead>
								<tr>
									<th colspan="4" class="text-info">Upload Documents</th>
								</tr>
								<tr>
									<th>Document Name</th>
									<th>Attach Document</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody id="documentsTable">
								<tr data-index="0">
									<td>
										<div style="display: none;" class="attachments" data-index="0"></div>
										<input name="documentList[0].name" data-name="documentName" type="hidden">
										<input name="documentList[0].updateFile" data-name="updateFile" type="hidden" value="false"/>
										<input data-name="docName" class="form-control input-sm" placeholder="Enter Document Name" 
											onchange="enableDisableRefresh(this);applyKeyToDocument(this);" onkeyup="enableDisableRefresh(this);applyKeyToDocument(this);" type="text" >
									</td>
									<td>
										<div class="clearfix attchments">
											<div class="input-group">
												<label class="input-group-btn"> <span
													class="btn btn-primary">Browse File <input
														style="visibility: hidden; height: 0; width: 10px; overflow: hidden"
														multiple="multiple" id="uploadBtn0" class="upload"
														onchange="fileUploadEvent(this)" type="file" data-index="0">
												</span>
												</label> <input class="form-control" readonly="readonly" data-name='fileName'
													id="uploadFile0" placeholder="Select File(s)" type="text">
											</div>
										</div>
										<div class="uploadedFiles" data-id="0"></div>
									</td>
									<td>
										<div class="tblminwidth-75 text-center">
											<i class="fa fa-plus mr cursorPointer"
												onclick="addDocumentRow()"></i> <i
												class="fa fa-refresh cursorPointer disableBtn"
												onclick="refreshDocumentRow(this)"></i>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="clearfix footerstyelsNew">
			<div class="col-lg-12">
				<div class="pull-right">
					<input value="Save" class="btn btn-primary btn-primary"
						type="button" id="saveBtn"> <input value="Submit"
						class="btn btn-success btnSuccess mr10 ml10" type="button"
						id="submitBtn"> <input value="Cancel"
						class="btn btn-default" type="button" id="cancelBtn"
						onclick="history.back()">
				</div>
			</div>
		</div>
	</form>
	<!-- Content Area Ends-->
</section>
<script type="text/javascript">
	var userId = "${user.id}";
	$(document).ready(function() {
		/* $('#employeeSelect').val(12); */
		$('#employeeSelect').selectpicker('refresh');
	});
</script>