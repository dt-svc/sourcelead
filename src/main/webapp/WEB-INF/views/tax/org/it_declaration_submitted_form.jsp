<jsp:include page="../../masterHeader.jsp" />
<jsp:include page="../../masterSideNav.jsp" />
<%-- <%@taglib prefix="json" uri="http://www.atg.com/taglibs/json"%> --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="/resources/js/tax/it_declaration.js"></script>
<section class="content-wraper contentWraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>IT Declaration</h2>
		</div>
		<div class="pull-right brud-crum">Home &raquo; IT Declaration</div>
	</section>
	<jsp:include page="../org/formHeader.jsp"></jsp:include>
	<form action="" id="itDeclarationForm">
		<div class="contentareaInner-styles clearfix">
			<div class="clearfix form-horizontal minHeight_225 minHeightcol5">
				<div class="row min">
					<input type="hidden" value="${form.id}" name="id"/>
					<input type="hidden" value="${form.taxFormId}" name="taxFormId"/>
					<div class="col-md-6 col-md-offset-2">
						<div class="clearfix dropdown-select">
							<select class="selectpicker width100percnt" id="employeeSelect" data-live-search="true" 
								title="Select Employee" field-name="Employee" data-validation="required" name="userId">
								<c:forEach items="${userRoleList}" var="userRole">
									<option ${form.userId == userRole.user.id ? "selected" : ""} value="${userRole.user.id}" data-id="${userRole.user.id}" data-name="${userRole.user.firstName.concat(' ' ).concat(userRole.user.lastName)}">
										${userRole.user.firstName} ${userRole.user.lastName}
									</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="clearfix mt mb"></div>
					<div class="col-md-5">
						<label class="clearfix">Associate Name</label>
						<div class="clearfix">
							<input type="text" value="${form.userName}" class="form-control" readonly="readonly" id="associateName"
							 field-name="Associate Name" data-validation="required" placeholder="Associate Name">
						</div>
					</div>
					<div class="col-md-5">
						<label class="clearfix">Associate ID</label>
						<div class="clearfix">
							<input type="text" value="${form.userId}" class="form-control" readonly="readonly" id="associateId" placeholder="Associate Id">
						</div>
					</div>
					<div class="col-md-5">
						<label class="clearfix">Pan No</label>
						<div class="clearfix">
							<input type="text" class="form-control" readonly="readonly" id="associatePAN" placeholder="PAN No">
						</div>
					</div>
					<div class="col-md-5">
						<label class="clearfix">Select Year</label>
						<div class="clearfix">
							<select class="selectpicker width100percnt" data-live-search="true" title="Select Year"
							 field-name="Year" data-validation="required" name="financialYear">
								<c:forEach items="${yearList}" var="year">
									<option ${year.value==true || form.financialYear == year.key ? "selected=selected" : ""} value="${year.key}">${year.key}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="col-md-10">
					<c:set var="sectionCount" value="0"></c:set>
					<c:set var="sectionItemCount" value="0"></c:set>
					<c:forEach items="${form.submissionItems}" var="submissionItem">
						<table class="table table-itdeclaration table-bordered">
							<thead>
								<tr>
									<th colspan="4" class="text-info">${submissionItem.get("taxSectionName")}</th>
								</tr>
								<tr>
									<th>Items</th>
									<th>Max Limit</th>
									<th>Description</th>
									<th>Declared Amount</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach begin="0" end="${submissionItem.get('items').length() - 1}" var="index">
									<input type="hidden" name="submissionItemList[${sectionItemCount}].taxSectionId" value="${submissionItem.get('taxSectionId')}">
									<input type="hidden" name="submissionItemList[${sectionItemCount}].taxSectionName" value="${submissionItem.get('taxSectionName')}">
									<input type="hidden" name="submissionItemList[${sectionItemCount}].name" value="${submissionItem.get('items').getJSONObject(index).getString('name')}">
									<input type="hidden" name="submissionItemList[${sectionItemCount}].maxLimit" value="${submissionItem.get('items').getJSONObject(index).getString('maxLimit')}">
									<input type="hidden" name="submissionItemList[${sectionItemCount}].description" value="${submissionItem.get('items').getJSONObject(index).getString('description')}">
									<tr>
										<td>${submissionItem.get('items').getJSONObject(index).getString('name')}</td>
										<td>${submissionItem.get('items').getJSONObject(index).getString('maxLimit')}</td>
										<td>${submissionItem.get('items').getJSONObject(index).getString('description') != null && submissionItem.get('items').getJSONObject(index).getString('description') != '' ? submissionItem.get('items').getJSONObject(index).getString('description') : '--NA--'}</td>
										<td>
											<input type="text" class="form-control input-sm" name="submissionItemList[${sectionItemCount}].amount"
											placeholder='Enter Amount' field-name='Amount' value="${submissionItem.get('items').getJSONObject(index).getString('amount')}"
											data-validation='required validate_float'>
										</td>
									</tr>
									<c:set var="sectionItemCount" value="${sectionItemCount + 1}"></c:set>
								</c:forEach>
							</tbody>
						</table>
						<div class="clearfix"></div>
					</c:forEach>
						<%-- <c:forEach items="${form.submittedSectionList}" var="submittedSection">
							<c:if test="${submittedSection.submissionItemList.size() > 0}">
								<table class="table table-itdeclaration table-bordered">
									<thead>
										<tr>
											<th colspan="4" class="text-info">${submittedSection.name}</th>
										</tr>
										<tr>
											<th>Items</th>
											<th>Max Limit</th>
											<th>Description</th>
											<th>Declared Amount</th>
										</tr>
									</thead>
									<tbody>
										<c:set var="sectionItemCount" value="0"></c:set>
										<c:forEach items="${submittedSection.submissionItemList}" var="sectionItem">
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
													placeholder='Enter Amount' field-name='Amount' value="${sectionItem.amount}"
													data-validation='required validate_float'>
												</td>
											</tr>
											<c:set var="sectionItemCount" value="${sectionItemCount + 1}"></c:set>
										</c:forEach>
									</tbody>
								</table>
								<div class="clearfix"></div>
								<c:set var="sectionCount" value="${sectionCount + 1}"></c:set>
							</c:if>
						</c:forEach> --%>
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
								<c:set value="0" var="documentCount"></c:set>
								<c:forEach items="${documents}" var="document">
									<tr>
										<td>
											<div style="display: none;" class="attachments" data-index="0">
												<c:set value="0" var="attachmentCount"></c:set>
												<c:forEach items="${document.attachmentList}" var="attachment">
													<input name="documentList[${documentCount}].attachmentList[${attachmentCount}].attachment" data-name="documentFileNames" value="${attachment.attachment}" type="hidden">
													<c:set value="${attachmentCount + 1}" var="attachmentCount"></c:set>
												</c:forEach>
											</div>
											<input name="documentList[${documentCount}].name" data-name="documentName" type="hidden">
											<input name="documentList[${documentCount}].updateFile" data-name="updateFile" type="hidden" value="false"/>
											<%-- <input name="documentList[${documentCount}].fileNames" data-name="documentFileNames" type="hidden"> --%>
											<input value="${document.name}" data-name="docName" class="form-control input-sm" placeholder="Enter Document Name" field-name='Document Name' 
												data-validation='required' onchange="enableDisableRefresh(this);applyKeyToDocument(this);" onkeyup="enableDisableRefresh(this);applyKeyToDocument(this);" type="text" >
										</td>
										<td>
											<div class="clearfix attchments">
												<div class="input-group">
													<label class="input-group-btn">
														<span class="btn btn-primary">Browse File <input style="visibility: hidden; height: 0; width: 10px; overflow: hidden"
																multiple="multiple" id="uploadBtn0" class="upload"
																onchange="fileUploadEvent(this)" type="file">
														</span>
													</label>
													<input class="form-control" readonly="readonly"
														field-name='Document File' data-validation='required'
														id="uploadFile1" placeholder="Select File(s)" type="text" value="${document.attachmentList.size()} files selected">
												</div>
											</div>
											<div class="uploadedFiles">
												<c:set value="0" var="attachmentCount"></c:set>
												<c:forEach items="${document.attachmentList}" var="attachment">
													<div data-index="${attachmentCount}"><i onclick="removeAttachment();" class="text-danger fa fa-remove cursorPointer"></i>${attachment.attachment}</div>
													<c:set value="${attachmentCount + 1}" var="attachmentCount"></c:set>
												</c:forEach>
											</div>
										</td>
										<td>
											<div class="tblminwidth-75 text-center">
												<c:choose>
													<c:when test="${documentCount == 0}">
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
									<c:set value="${documentCount + 1}" var="documentCount"></c:set>
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