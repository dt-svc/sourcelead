<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="/resources/js/tax/it_declaration.js"></script>
<%-- <jsp:include page="../tax/org/it_Challan_CustomForm.jsp"></jsp:include> --%>
<div id="itChallanCustomPopup"></div>
<script type="text/javascript">
       var pmsUrl ='${SourceLeadWeb_pmsUrl}';
</script>
<div>
	<div class="tablelist-wrap">

		<div class="col-md-12">
		<c:if test="${isEditable}">
	<div class="col-md-12 text-center">
		<a href="javascript:void(0)" title="Add New Section"
			onClick="addSection();"
			class="pull-right btn btn-success btnSuccess mb"> <i
			class="fa fa-plus"></i> Add Section
		</a>
	</div>
</c:if>
		
		</div>


		<div class="clearfix"></div>

		<!-- section fields -->
		<div class="clearfix"></div>
		<div class="col-md-12">
			<c:forEach items="${form.sourceleadTaxChallanSectionList}" var="section">
				<table class="table table-itdeclaration table-challan table-bordered">
				<thead>
					<tr>
						<th colspan="4">${section.name}
							<c:choose>
								<c:when test="${section.active == '1'}">
								   <span class="ml fa fa-plus text-muted" data-toggle="popover" data-placement="right" data-content="Section is deactived, please activate !."></span>
									<div class="pull-right mr25">
									<div class="mr25">
										<a class="fa fa-toggle-off anchorBlack mr10 mt2 pull-left" title="Activate" onclick="changeChallanSectionStatus(${section.id});"></a>
										<span title="Activate Section" class="fa fa-edit anchorBlack"  id="itChallanSectionItemForm"></span>
									</div>
									</div>
								</c:when>
										<c:otherwise>
											<span class="ml fa fa-plus text-primary cursorPointer" title="Add Section" onClick="addChallaItem(${section.id},'${section.name}');"></span>
										<div class="pull-right mr25">
											<div class="mr25">
												<a class="fa fa-toggle-on mr10 mt2 pull-left text-success" title="InActivate" onclick = "changeChallanSectionStatus(${section.id});"></a>
												<a title="Edit Section" class="fa fa-edit"  id="itChallanSectionItemForm" onclick="editITChallanSection(${section.taxFormId}, ${section.id}, '${section.name}', '${section.description}');"></a>
											</div>
										</div>
										</c:otherwise>
							</c:choose>
							
						</th>
					</tr>
					<tr>
						<th>Items</th>
						<th>Description</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
						<c:forEach items="${section.sourceleadTaxChallanSectionItemList}" var="sectionItems">
							<tr>
								<td>
									<div class="parentDiv">
										<div class="editElementDiv">
											<span class="edit-element">${sectionItems.labelName}</span>
										</div>
										<div class="row saveElementDiv">
											<div class="col-md-11 paddigRight">
												<input type="text" class="form-control input-sm"
													placeholder="Enter">
											</div>
											<div class="col-md-1 text-left paddingLeft">
												<a class="fa fa-floppy-o mt5 saveElement" title="Save"></a>
											</div>
										</div>
									</div>
								</td>
								<td><textarea class="form-control" rows="2"
										placeholder="Enter"></textarea></td>
								<td>
									<ul class="list-unstyled list-inline mt5 text-center">
									            <c:choose>
													<c:when test="${sectionItems.active == '1'}">
														<a class="fa fa-toggle-off anchorBlack mr5 mt2" title="Activate" id = "inactiveStatus"
															 onclick = "changeChallanItemStatus(${sectionItems.id});"></a>
															 <li><a title="EditItem" class="fa fa-pencil "  id="itChallanSectionItemForm"></a></li>
													</c:when>
													<c:otherwise>
														<a class="fa fa-toggle-on mr5 mt2 text-success" title="InActivate" id = "activeStatus"
															 onclick = "changeChallanItemStatus(${sectionItems.id});"></a>
															 <li><a title="EditItem" class="fa fa-pencil"  id="itChallanSectionItemForm" onclick="editItemDetails(${sectionItems.id});"></a></li>
													</c:otherwise>
												</c:choose>
										
									</ul>
								</td>
							</tr>
						</c:forEach>
							
				</tbody>
			</table>
			<div class="clearfix"></div>
			</c:forEach>
			
		</div>
		<!-- section fields ends -->
		<div class="clearfix"></div>
	<!-- 	<div class="clearfix footerstyelsNew">
			<div class="col-lg-12">
				<div class="pull-right">
					<input value="Save" class="btn btn-success btnSuccess"
						type="button"> <input value="Cancel"
						class="btn btn-default ml10" type="button">
				</div>
			</div>
		</div> -->

	</div>

<div class="comMask"></div>
<div class="modal section">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" onClick="modalClose();">&times;</button>
				<h4 class="modal-title">Add Section</h4>
			</div>
			<form action="" id="itChallanSectionForm">
				<input type="hidden" name="id" id="sectionId" value="0">
				<input type="hidden" name="taxFormId" id="taxFormId" value="${form.id}">
				<div class="modal-body">
					<div class="form-group">
						<label>Section Name</label>
						<div class="clearfix">
							<input type="text" id="sectionName" class="form-control" name="name" field-name="SectionName" data-validation="required" >
						</div>
					</div>
					<div class="form-group">
						<label>Description</label>
						<div class="clearfix">
							<textarea class="form-control" id="sectionDescription" rows="2" name="description" field-name="Description" data-validation="required"></textarea>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="pull-right">
						<button type="button" class="btn btn-default pull-left mr"
							onClick="modalClose();">Close</button>
						<button type="button" class="btn btn-success btnSuccess pull-left"
							onclick="addITChallanSection()"
							id="addITChallanSectionBtn">Save</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

</div>

<div class="modal addSectionChallanItemPopup">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" onClick="modalClose();">&times;</button>
				<h4 class="modal-title">IT Challan</h4>
			</div>
			<form action="" id="AddItChallanSectionItemForm">
				<input type="hidden" value="0" name="taxChallanId" id="taxChallanId">
				<div class="modal-body">
					<div class="form-group">
						<label>Items</label>
						<div class="clearfix">
							<input type="text" class="form-control" name="labelName" id = "labelName"
								field-name="Items" data-validation="required">
						</div>
					</div>
					<div class="form-group">
						<label>Description</label>
						<div class="clearfix">
							<input type="text" class="form-control" name="discription" id = "discription"
								field-name="Description" data-validation="required">
						</div>
					</div>
					
				</div>
			</form>
			<div class="modal-footer">
				<div class="pull-right">
					<button type="button" class="btn btn-default pull-left mr"
						onClick="modalClose();">Close</button>
					<button type="button" class="btn btn-success btnSuccess pull-left"
						onclick="addITChallanSectionItem()"
						id="addITChallanSectionItemBtn">Save</button>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
$(document).ready(function(){
    $('[data-toggle="popover"]').popover();
});
</script>