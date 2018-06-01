<jsp:include page="../../masterHeader.jsp" />
<jsp:include page="../../masterSideNav.jsp" />
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
					<div class="col-md-6 col-md-offset-2">
						<div class="clearfix dropdown-select">
							<select class="selectpicker width100percnt" id="employeeSelect"
								data-live-search="true" title="Select Employee"
								field-name="Employee" data-validation="required"
								name="userId">
								<c:forEach items="${userRoleList}" var="userRole">
									<option value="${userRole.user.id}"
										data-id="${userRole.user.id}"
										data-name="${userRole.user.firstName.concat(' ' ).concat(userRole.user.lastName)}">${userRole.user.firstName}
										${userRole.user.lastName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="clearfix mt mb"></div>
					<div class="col-md-5">
						<label class="clearfix">Associate Name</label>
						<div class="clearfix">
							<input type="text" class="form-control" readonly="readonly"
								id="associateName" field-name="Associate Name"
								data-validation="required" placeholder="Associate Name">
						</div>
					</div>
					<div class="col-md-5">
						<label class="clearfix">Associate ID</label>
						<div class="clearfix">
							<input type="text" class="form-control" readonly="readonly"
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
								<option>2015-16</option>
								<option>2016-17</option>
								<option>2017-18</option>
								<option>2018-19</option>
								<option>2019-20</option>
								<option>2021-22</option>
							</select>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="col-md-10">
					<c:set var="sectionCount" value="0"></c:set>
						<c:forEach items="${form.sourceleadTaxSectionList}" var="section">
							<c:if test="${section.sourceleadTaxSectionItemList.size() > 0}">
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
										<c:set var="sectionItemCount" value="0"></c:set>
										<c:forEach items="${section.sourceleadTaxSectionItemList}"
											var="sectionItem">
											<input type="hidden" name="sourceleadTaxSectionItemList[${sectionItemCount}].name" value="${sectionItem.name}">
											<input type="hidden" name="sourceleadTaxSectionItemList[${sectionItemCount}].maxLimit" value="${sectionItem.maxLimit}">
											<input type="hidden" name="sourceleadTaxSectionItemList[${sectionItemCount}].description" value="${sectionItem.description}">
											<tr>
												<td>${sectionItem.name}</td>
												<td>${sectionItem.maxLimit}</td>
												<td>${sectionItem.description != null && sectionItem.description != "" ? sectionItem.description : "--NA--"}</td>
												<td>
													<input type="text" class="form-control input-sm" name="sourceleadTaxSectionItemList[${sectionItemCount}].amount"
													placeholder='Enter Amount' field-name='Amount' value="${sectionItemCount}"
													data-validation='required validate_float'>
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
								<tr>
									<td><input type="text" class="form-control input-sm"
										field-name='Document Name' data-validation='required'
										placeholder="Enter Document Name"
										onchange="enableDisableRefresh(this)"
										onkeyup="enableDisableRefresh(this)"></td>
									<td>
										<div class="clearfix attchments">
											<div class="input-group">
												<label class="input-group-btn"> <span
													class="btn btn-primary">Browse File <input
														style="visibility: hidden; height: 0; width: 10px; overflow: hidden"
														multiple="multiple" id="uploadBtn1" class="upload"
														onchange="fileUploadEvent(this)" type="file">
												</span>
												</label> <input class="form-control" readonly="readonly"
													field-name='Document File' data-validation='required'
													id="uploadFile1" placeholder="Select File(s)" type="text">
											</div>
										</div>

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