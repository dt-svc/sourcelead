<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <c:if test="!${includedPage}">
	<jsp:include page="../masterHeader.jsp" />
	<jsp:include page="../masterSideNav.jsp" />
</c:if> --%>

<div class="clearfix"></div>
<!-- section fields -->
<c:if test="${isEditable}">
	<div class="col-md-12 text-center">
		<a href="javascript:void(0)" title="Add New Section"
			onClick="addSection();"
			class="pull-right btn btn-success btnSuccess mb"> <i
			class="fa fa-plus"></i> Section
		</a>
	</div>
</c:if>
<c:if test="${!isEditable}">
	<div class="col-md-12 text-center">
		<a href="create?uuid=${form.uuid}" title="Edit Form"
			class="pull-right btn btn-success btnSuccess mb"> <i
			class="fa fa-plus"></i> Edit
		</a>
	</div>
</c:if>
<div class="col-md-12">
	<div class="clearfix"></div>
	<c:forEach items="${form.sourceleadTaxSectionList}" var="section">
		<table class="table table-itdeclaration table-bordered">
			<thead>
				<tr>
					<th colspan="4" class="text-info noPad">
						<div class="row">
							<div class="col-md-10 padding_right">
								<div class="parentDiv">
									<div class="editElementDiv secHead clearfix">
										<span class="edit-element">${section.name}</span>
										<span class="edit-element limit">${section.maxLimit}</span>
										<c:if test="${isEditable}">
											<div class="faPencil">
												<a class="secHeadBtn ${section.active ? '' : 'disableBtn'}"
													title="Edit Section Name" onclick="editSection(${section.id}, ${section.taxFormId}, '${section.name}', '${section.maxLimit}', '${section.description}')">Edit</a>
												<c:choose>
													<c:when test="${section.active}">
														<a class="secHeadBtn" title="Delete Section"
															onclick="tax.showChangeStatusPopup(${section.id}, 3, 1)">Inactive</a>
													</c:when>
													<c:otherwise>
														<a class="secHeadBtn" title="Activate Section"
															onclick="tax.showChangeStatusPopup(${section.id}, 3, 0)">Active</a>
													</c:otherwise>
												</c:choose>
											</div>
										</c:if>
									</div>

									<%-- <div class="row saveElementDiv">
										<form id="section_${section.id}_name_form">
											<div class="col-md-10 paddigRight">
												<input type="hidden" value="${section.id}" name="id">
												<input type="hidden" value="${section.taxFormId}"
													name="taxFormId"> <input
													class="form-control input-sm" placeholder="Enter"
													name="name" type="text" value="${section.name}"
													data-old_value="${section.name}">
												<input class="form-control input-sm" placeholder="Max Limit" data-validation="required validate_float validate_length length1-10"
													name="maxLimit" type="text" value="${section.maxLimit}"
													data-old_value="${section.maxLimit}">
											</div>
											<div class="col-md-2 text-left paddingLeft">
												<a
													class="fa fa-remove mt5 mr10 text-danger cancelSectionNameEdit"
													title="Cancel Edit"></a> <a
													class="fa fa-floppy-o mt5 saveSectionName"
													title="Save Name"></a>
											</div>
										</form>
									</div> --%>

								</div>
							</div>
							<c:if test="${isEditable}">
								<div class="col-md-2 padding_left ${section.active ? '' :'disableBtn'}">
								<div class="secHead">
									<a href="javascript:void(0)" class="secHeadBtn2" title="Add Custom Item" onClick="addSectionItem(${section.id},'${section.name}');">
									<i class="fa fa-plus paddingRight"></i>Section Item</a>
								</div>
								</div>
							</c:if>
						</div>

					</th>
				</tr>
				<c:if test="${section.sectionItemList.size() > 0}">
					<tr class="tabHeadColor" ${section.active ? '' : "class='disableBtn'"}>
						<th>Items</th>
						<th>Max Limit</th>
						<th>Description</th>
						<c:if test="${isEditable}">
							<th>Action</th>
						</c:if>
					</tr>
				</c:if>
			</thead>
			<tbody ${section.active ? '' : "class='disableBtn'"}>
				<c:choose>
					<c:when test="${section.sectionItemList.size() > 0}">
						<c:forEach items="${section.sectionItemList}"
							var="sectionItem">
							<tr>
								<td>
									<div class="parentDiv">
										<div class="editElementDiv">
											<span class="edit-element">${sectionItem.name}</span>
										</div>
										<div class="row saveElementDiv">
											<form id="sectionItem_${sectionItem.id}_name_form">
												<input type="hidden" value="${sectionItem.id}" name="id">
												<input type="hidden" value="${sectionItem.taxSectionId}"
													name="taxSectionId">
												<div class="col-md-11 paddigRight">
													<input type="text" class="form-control input-sm"
														field-name="name" data-validation="required validate_length length10-250" 
														name="name" value="${sectionItem.name}"
														data-old_value="${sectionItem.name}"
														placeholder="Item Name">
												</div>
											</form>
										</div>
									</div>
								</td>
								<td>
									<div class="editElementDiv">${sectionItem.maxLimit}</div>
									<div class="saveElementDiv">
										<form id="sectionItem_${sectionItem.id}_maxLimit_form">
											<input type="text" class="form-control input-sm"
												field-name="Max Limit"
												data-validation="required validate_float validate_length length1-20" name="maxLimit"
												value="${sectionItem.maxLimit}"
												data-old_value="${sectionItem.maxLimit}"
												placeholder="Maximum Amount Limit">
										</form>
									</div>
								</td>
								<td>
									<div class="tbldata-dotdot-big" style="margin: 0px;">
										<div class="editElementDiv">${sectionItem.description != null && sectionItem.description != "" ? sectionItem.description : "--NA--"}</div>
										<div class="saveElementDiv">
											<form id="sectionItem_${sectionItem.id}_description_form">
												<textarea class="form-control" rows="2" name="description"
													data-old_value="${sectionItem.description}" data-validation="validate_length length0-500"
													placeholder="Short Description">${sectionItem.description}</textarea>
											</form>
										</div>
									</div>
								</td>
								<c:if test="${isEditable}">
									<td>
										<ul class="list-unstyled list-inline mt5 text-center">
											<li><a title="Edit Row"
												class="fa fa-pencil anchor-black editRow ${sectionItem.active ? '' : 'disableBtn'}"></a></li>
											<c:choose>
												<c:when test="${sectionItem.active}">
													<li><a title="Inactive Row"
														data-id="${sectionItem.id}"
														class="fa fa-trash text-danger deleteRow"
														onclick="tax.showChangeStatusPopup(${sectionItem.id}, 4, 1)"></a></li>
												</c:when>
												<c:otherwise>
													<li><a title="Active Row" data-id="${sectionItem.id}"
														class="fa fa-edit text-success "
														onclick="tax.showChangeStatusPopup(${sectionItem.id}, 4, 0)"></a></li>
												</c:otherwise>
											</c:choose>


											<li style="display: none;"><a title="Cancel Edit"
												class="fa fa-remove text-danger cancelEditRow"></a></li>
											<li style="display: none;"><a title="Save Row"
												data-id="${sectionItem.id}"
												class="fa fa fa-floppy-o text-primary saveRow"></a></li>
										</ul>
									</td>
								</c:if>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td>No items available, add some item(s) to this section</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<div class="clearfix"></div>
	</c:forEach>
</div>
<!-- section fields ends -->

<div class="clearfix"></div>
<!-- <div class="clearfix footerstyelsNew">
	<div class="col-lg-12">
		<div class="pull-right">
			<input value="Save" class="btn btn-success btnSuccess" type="button">
			<input value="Cancel" class="btn btn-default ml10" type="button">
		</div>
	</div>
</div> -->


<!-- Popups -->
<div class="comMask"></div>
<div class="modal section">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" onClick="modalClose();">&times;</button>
				<h4 class="modal-title" id="taxFormTitle">Section</h4>
			</div>
			<form action="" id="itDeclarationSectionForm">
				<input type="hidden" value="0" name="id" id="sectionId">
				<input type="hidden" value="${form.id}" name="taxFormId" id="sectionTaxFormId">
				<div class="modal-body">
					<div class="form-group">
						<label>Section Name</label>
						<div class="clearfix">
							<input type="text" class="form-control" name="name" id="sectionName"
								field-name="SectionName" data-validation="required">
						</div>
					</div>
					<div class="form-group">
						<label>Max Limit</label>
						<div class="clearfix">
							<input type="text" class="form-control" name="maxLimit" id="sectionMaxLimit"
								field-name="Max Limit" data-validation="required validate_float validate_length length1-20">
						</div>
					</div>
					<div class="form-group">
						<label>Description</label>
						<div class="clearfix">
							<textarea class="form-control" rows="2" name="description" id="sectionDescription"
								field-name="Description" data-validation="required"></textarea>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="pull-right">
						<button type="button" class="btn btn-default pull-left mr"
							onClick="modalClose();">Close</button>
						<button type="button" class="btn btn-success btnSuccess pull-left"
							onclick="addITDeclarationSection()"
							id="addITDeclarationSectionBtn">Save</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

<div class="modal addSectionItemPopup">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" onClick="modalClose();">&times;</button>
				<h4 class="modal-title">Deduction U/S 10</h4>
			</div>
			<form action="" id="itDeclarationSectionItemForm">
				<input type="hidden" value="0" name="taxSectionId" id="taxSectionId">
				<div class="modal-body">
					<div class="form-group">
						<label>Item Name</label>
						<div class="clearfix">
							<input type="text" class="form-control" name="name"
								field-name="Item Name" data-validation="required">
						</div>
					</div>
					<div class="form-group">
						<label>Max Limit</label>
						<div class="clearfix">
							<input type="text" class="form-control" name="maxLimit"
								field-name="Max Limit" data-validation="required validate_float validate_length length1-20">
						</div>
					</div>
					<div class="form-group">
						<label>Description</label>
						<div class="clearfix">
							<textarea class="form-control" rows="1" name="description"
								field-name="Description" data-validation="required"></textarea>
						</div>
					</div>
				</div>
			</form>
			<div class="modal-footer">
				<div class="pull-right">
					<button type="button" class="btn btn-default pull-left mr"
						onClick="modalClose();">Close</button>
					<button type="button" class="btn btn-success btnSuccess pull-left"
						onclick="addITDeclarationSectionItem()"
						id="addITDeclarationSectionItemBtn">Save</button>
				</div>
			</div>
		</div>
	</div>
</div>