<jsp:include page="../../masterHeader.jsp" />
<jsp:include page="../../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="/resources/js/tax/it_declaration.js"></script>
<c:set var="uploadCount" value="0"></c:set>
<section class="content-wraper contentWraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>IT Declaration</h2>
		</div>
		<div class="pull-right brud-crum">Home &raquo; IT Declaration</div>
	</section>
	<jsp:include page="../org/formHeader.jsp"></jsp:include>
	<form id="itDeclarationForm">
		<input type="hidden" name="id" value="${form.id}"> <input
			type="hidden" name="taxFormId" value="${form.taxFormId}">
		<div class="contentareaInner-styles clearfix">
			<div class="clearfix form-horizontal minHeight_225 minHeightcol5">
				<div class="row min">

					<c:if test="${!isEditable}">
						<div class="col-md-12 text-center">
							<a href="editForm?uuid=${uuid}&formUUID=${form.uuid}"
								title="Edit Form"
								class="pull-right btn btn-success btnSuccess mb"> <i
								class="fa fa-plus"></i> Edit
							</a>
						</div>
					</c:if>

					<div class="col-md-6 col-md-offset-2">
						<div class="clearfix dropdown-select">
							<select class="selectpicker width100percnt" id="employeeSelect"
								data-live-search="true" title="Select Employee"
								field-name="Employee" data-validation="required" name="userId">
								<option value="${form.userId}" data-id="${form.userId}"
									data-name="${form.userName}" selected="selected">${form.userName}</option>
							</select>
						</div>
					</div>
					<div class="clearfix mt mb"></div>
					<div class="col-md-5">
						<label class="clearfix">Associate Name</label>
						<div class="clearfix">
							<input type="text" class="form-control" readonly="readonly"
								id="associateName" field-name="Associate Name"
								value="${form.userName}" data-validation="required"
								placeholder="Associate Name">
						</div>
					</div>
					<div class="col-md-5">
						<label class="clearfix">Associate ID</label>
						<div class="clearfix">
							<input type="text" class="form-control" readonly="readonly"
								value="${form.userId}" id="associateId"
								placeholder="Associate Id">
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
								<option selected="selected" value="${form.financialYear}">${form.financialYear}</option>
							</select>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="col-md-10">
						<c:set var="submissionItemCount" value="0"></c:set>
						<c:forEach items="${form.submittedSectionList}" var="section">
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
									</tr>
								</thead>
								<tbody>
									<%-- <c:set var="submissionItemCount" value="0"></c:set> --%>
									<c:forEach items="${section.submissionItemList}"
										var="sectionItem">
										<input type="hidden"
											name="submissionItemList[${submissionItemCount}].id"
											value="${sectionItem.id}">
										<input type="hidden"
											name="submissionItemList[${submissionItemCount}].taxSectionId"
											value="${sectionItem.taxSectionId}">
										<input type="hidden"
											name="submissionItemList[${submissionItemCount}].name"
											value="${sectionItem.name}">
										<input type="hidden"
											name="submissionItemList[${submissionItemCount}].maxLimit"
											value="${sectionItem.maxLimit}">
										<input type="hidden"
											name="submissionItemList[${submissionItemCount}].description"
											value="${sectionItem.description}">
										<tr>
											<td>${sectionItem.name}</td>
											<td>${sectionItem.maxLimit}</td>
											<td>${sectionItem.description != null && sectionItem.description != "" ? sectionItem.description : "--NA--"}</td>
											<c:if test="${isEditable}">
												<td><input type="text" class="form-control input-sm"
													name="submissionItemList[${submissionItemCount}].amount"
													placeholder='Enter Amount' field-name='Amount'
													value="${sectionItem.amount}"
													data-validation='required validate_float'></td>
											</c:if>
											<c:if test="${!isEditable}">
												<td>${sectionItem.amount}</td>
											</c:if>
										</tr>
										<c:set var="submissionItemCount"
											value="${submissionItemCount + 1}"></c:set>
									</c:forEach>
								</tbody>
							</table>
							<div class="clearfix"></div>
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
							<c:forEach items="${documents}" var="document">
								<tr>
									<td>
										<div style="display: none;" class="attachments" data-index="${uploadCount}"></div>
										<input type="hidden" name="documentList[${uploadCount}].id" data-name="documentId" value="${document.id}"/>
										<input type="hidden" name="documentList[${uploadCount}].name" data-name="documentName" value="${document.name}"/>
										<input type="hidden" name="documentList[${uploadCount}].fileNames" data-name="documentFileNames" />
										<input data-name='docName' type="text" value="${document.name}" 
										class="form-control input-sm" field-name='Document Name'
										data-validation='required' placeholder="Enter Document Name"
										onchange="enableDisableRefresh(this);applyKeyToDocument(this);"
										onkeyup="enableDisableRefresh(this);applyKeyToDocument(this);">
									</td>
									<td>
										<div class="clearfix attchments">
											<div class="input-group">
												<label class="input-group-btn">
													<span class="btn btn-primary">Browse File <input
														data-name='docFiles'
														style="visibility: hidden; height: 0; width: 10px; overflow: hidden"
														multiple="multiple" id="uploadBtn1" class="upload"
														onchange="fileUploadEvent(this)" type="file">
													</span>
												</label>
												<input class="form-control" readonly="readonly"
													field-name='Document File' data-validation='required'
													id="uploadFile1" placeholder="Select File(s)" type="text" value="${document.attachmentList.size()} file(s) selected">
											</div>
										</div>
										<div class="uploadedFiles">
											<c:set var="attachmentCount" value="0"></c:set>
											<c:forEach items="${document.attachmentList}" var="attachment">
												<input name='documentList[${uploadCount}].attachmentList[${attachmentCount}].attachment' data-name='documentFileNames' type='hidden' value='" + fileURL + "'>
												<div data-index="${attachmentCount}">
													<i onclick="$(this).parent().remove();" class="text-danger fa fa-remove cursorPointer"></i>
												${attachment.attachment}</div>
												<c:set var="attachmentCount" value="${attachmentCount + 1}"></c:set>
											</c:forEach>
										</div>
									</td>
									<td>
										<div class="tblminwidth-75 text-center">
											<c:choose>
												<c:when test="${uploadCount == 0}">
													<i class="fa fa-plus mr cursorPointer" onclick="addDocumentRow()"></i>	
												</c:when>
												<c:otherwise>
													<i class="fa fa-trash text-danger mr cursorPointer" onclick="removeDocumentRow(this)"></i>	
												</c:otherwise>
											</c:choose>
											<i class="fa fa-refresh cursorPointer disableBtn" onclick="refreshDocumentRow(this)"></i>
										</div>
									</td>
								</tr>
								<c:set var="uploadCount" value="${uploadCount+1}"></c:set>
									<script type="text/javascript">
										var uploadCount = ${uploadCount};
									</script>
							</c:forEach>
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