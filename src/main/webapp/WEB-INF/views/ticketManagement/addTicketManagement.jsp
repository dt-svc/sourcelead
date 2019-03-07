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

<style type="text/css">
  .dropdown_inr select {
 height: 34px; color: #fff;
 }
 .uploadFile {
 width: 100%;
 }
 .uploadFile span {
 max-width: 100% !important; width: 100% !important;
 }
 .uploadFile span input {
 width: 90% !important;
 }
 .uploadFile table i {
 margin-left:10px;
 }
  .expAddRo, .expDetails {
 background-color: #f5f5f5;
 } 
 .expDetails {
 padding: 15px 0;
 }
 .expensTableView table tr td {
 padding: 0 !important;
 }
 .valid_ation p.jquery_form_error_message {
 position: absolute; bottom: -13px; left: 0;
 }
 .valid_ation .positionRelative{
 position: relative !important;
 }
 </style>

<script>
$(document).ready(function() {
	getTicketManagementDescription('${ticketSummary.uuid}');
	<c:forEach items="${ticketSummary.ticketAssignToList}" var="assignTo">
		$("#assignedTo option[value='${assignTo.userId}']").attr("selected", true);
    </c:forEach>
	$("#assignedTo").chosen();
	$("#assignedTo").trigger("chosen:updated");
	$("#txtEditor").Editor();
	
});
</script>
<section class="content-wraper">
<div class="success_pop" >
		<i class="fa fa-check-circle"></i><span></span>
	</div>
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
		<input type="hidden" name="id" value="${ticketSummary != null ? ticketSummary.id : 0}"/>
		<div class="content-area content-new clearfix">
			<div class="row minHeightcol6 mt mb">
				<div class="col-md-6">
					<label class="col-md-3">Ticket Title</label>
					<div class="col-md-9">
						<input type="text" class="form-control" name="title" value="${ticketSummary.title}" id ="ticketTitleId" field-name="Ticket Title" data-validation="required validate_length length1-100">
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="col-md-6" id = "ticketDivId">
					<label class="col-md-3">Ticket Id</label>
					<div class="col-md-9">
						<input type="text" class="form-control" name="generatedId" value="${ticketSummary.generatedId}" id="ticketId" field-name="Ticket Id"  onblur="validateTicketId('${ticketSummary.id}');" data-validation="required validate_length length1-100">
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="col-md-6">
					<label class="col-md-3">Type</label>
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
					<label class="col-md-3">Category</label>
					<div class="col-md-9">
						<select class="chosen-select" name="ticketCategoryId" field-name="Category" id="ticketCategory" data-validation="required">
							<option value="">Select Ticket Category</option>
							<c:forEach items="${ticketCategoryList}" var="ticketCategory">
								<option value="${ticketCategory.id}" ${ticketSummary.ticketCategoryId == ticketCategory.id ? "selected='selected'" : ""}>${ticketCategory.name}</option>
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
								<option value="${ticketPriority.id}" ${ticketSummary.ticketPriorityId == ticketPriority.id ? "selected='selected'" : ""}>${ticketPriority.name}</option>
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
								<option value="${ticketStatus.id}" ${ticketSummary.ticketStatusId == ticketStatus.id ? "selected='selected'" : ""}>${ticketStatus.name}</option>
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
					<label class="col-md-3">Notify To</label>
					<div class="col-md-9">
						<textarea class="form-control" rows="4" id="notifyTo" name="notifyTo" field-name="NotifyTo"  placeholder="Enter multiple Email Id's separated by commas(,)" data-validation="required">${ticketSummary.notifyTo}</textarea>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="clearfix"></div>
				<div class="clearfix mb col-md-12">
					<label class="col-md-3">Description</label>
					<div class="col-md-12 txtEditor_p">
						<textarea id="txtEditor" name="description"  field-name="Description" value="" class="form-control newContrtextarea" data-validation="required">${ticketSummary.description}</textarea>
					</div>
				</div>
	
				
	                  <!--  <i class='fa fa-info-circle' title='Allowed File Formats: ppt,pptx,pps,ppsx,doc,docx,pdf,rtf,odt,xls,xlsx,csv,  zip,txt,jpg,jpeg,bmp,png,gif,eps,tiff,TGA,psd,svg,msg,wpd,wps      Allowed Size : 2MB '></i>
	                   <div class="uploadFile timesheet_week_comm">
		                 <input type='hidden' name='attcahmentMaxRowNumberREPLACEROWID'  id='attcahmentMaxRowNumberREPLACEROWID' value='1' />
		                 <span> 
		                     <table class='timesheetAttachment' id='attachmentTableREPLACEROWID' cellpadding='0' cellspacing='0' width="100%">
		                       <tr id='REPLACEROWIDdivAttachmentTsRow0'>
		                           <td><div id='0docFileuploadFileGroupREPLACEROWID'><input type='file' name='0docFileREPLACEROWID' id='0docFileREPLACEROWID' field-name='Attachment' onchange='validateUploadFile(0,REPLACEROWID)' data-validation='required' class='timesheetAttachmentValidation'><i class='fa fa-plus' onClick='addAttachment(REPLACEROWID,"NEW")'></i></div></td> 
		                       </tr>
		                   </table>
		                 </span>
		             </div>
		             </div>   		 -->
					 
					 <div id='attachmentSection' class='col-md-4 minHeight'>
	                   <label>Attachments<span class="text-danger">&nbsp;*</span></label>
	                   <i class='fa fa-info-circle' title='Allowed File Formats: ppt,pptx,pps,ppsx,doc,docx,pdf,rtf,odt,xls,xlsx,csv,  zip,txt,jpg,jpeg,bmp,png,gif,eps,tiff,TGA,psd,svg,msg,wpd,wps      Allowed Size : 2MB '></i>
		                   <div class="uploadFile timesheet_week_comm edit-Expense-upload">
			                  <input type='hidden' name='dbAttachmentMaxRowNumber${(2*(entryCount.count))-1}'  id='dbAttachmentMaxRowNumber${(2*(entryCount.count))-1}' value='${entry.fileInfo.size()}' />
			                 <input type='hidden' name='attcahmentMaxRowNumber${(2*(entryCount.count))-1}'  id='attcahmentMaxRowNumber${(2*(entryCount.count))-1}' value='1' />
			                 <div id='ticketManagementDocumentsId' class=''>
			                       <c:forEach var="entryDoc" items="${entry.fileInfo}" varStatus="entryDocCount">
			                       <c:if test = '${entryDoc.status == 1}'>
			                         <strong class='proDown' id='${entryDocCount.index}document${(2*(entryCount.count))-1}'>
			                          <input type='hidden' name='${entryDocCount.index}DbdocFileId${(2*(entryCount.count))-1}' id='${entryDocCount.index}DbdocFileId${(2*(entryCount.count))-1}' value='${entryDoc.fileDbId}'/>
			                          <input type='hidden' name='${entryDocCount.index}DbdocFileIsDeleted${(2*(entryCount.count))-1}' id='${entryDocCount.index}DbdocFileIsDeleted${(2*(entryCount.count))-1}' value='N'/>
			                             <a class='downicon' href='${entryDoc.fileURL}' download>
			                                 <i class='glyphicon glyphicon-save'></i>
								            <input type='button' value='${entryDoc.fileName}'/>
					      </a>
						<input class='dele' type='button' onclick="removeDbAttachment(${entryDocCount.index},${(2*(entryCount.count))-1});deleteAttchment(${entryDoc.fileDbId})"/>
			                         </strong>
			                         </c:if>
			                       </c:forEach>
			                 </div> 
			                 <span> 
			                     <table class='timesheetAttachment' id='attachmentTable${(2*(entryCount.count))-1}' cellpadding='0' cellspacing='0' width="100%">
			                       <tr id='${(2*(entryCount.count))-1}divAttachmentTsRow0'>
			                           <td><div id='0docFileuploadFileGroup${(2*(entryCount.count))-1}'><input type='file' name='0docFile${(2*(entryCount.count))-1}' id='0docFile${(2*(entryCount.count))-1}' field-name='Attachment' onchange='validateUploadFile(0,${(2*(entryCount.count))-1})' class='timesheetAttachmentValidation'><i class='fa fa-plus' onClick='addAttachment(${(2*(entryCount.count))-1},"EDIT")'></i></div></td> 
			                       </tr>
			                   </table>
			                 </span>
			             </div>
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
<div id='changeStatusPopup' class='remove_user_pop remove_user_pop_a'>
	<p id='changeStatusHeading' data-name="Ticket"></p>
	<div>&nbsp;&nbsp;&nbsp;</div>
	<b>
		<input id='confirmId' type='button' value='Ok' onclick='changeTicketManagementStatus()' class="pull-left">
		<input id="cancelStatusPopup" class='u_can_color' type='button' value='Cancel' onClick='closeDeletePopup()'>
	</b>
</div>


<script>
function getTicketManagementDescription(ticketUUID){
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getTicketManagementDescription?${_csrf.parameterName}=${_csrf.token}&uuid="+ticketUUID, 	   
			    success: function(result) {
			    	$('#txtEditor').html(result.description);
			    	$('.Editor-editor').html(result.description);
			    }
			    	 
	     });
}
</script>

					<!-- =================== saving the addTicketManagement ================================ -->
<script>
	
function validateTicketId(ticketId) {
	 var generatedId = $("#ticketId").val(); 
	 if(ticketId !=null && ticketId !='' && ticketId !='undefined'){
		 generatedId = ticketId;
	 }
	 if(generatedId != null && generatedId != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateTicketId?generatedId="+generatedId, 
		        success: function(data) {
		        	if(data == true){
		        		     $('#ticketDivId').find('p.jquery_form_error_message').remove();
		        			  $("input#ticketId").after ("<p class='jquery_form_error_message'>"+generatedId+" is Already Existed</span>");
							  document.getElementById("ticketId").setAttribute('record-exist','yes');
							  document.getElementById("ticketId").setAttribute('record-exist-errorMsg',generatedId+' is Already Existed'); 
		        		  
		        	}else{
	        			 $('input#ticketId').removeAttr( "record-exist record-exist-errormsg"); 
		        		 $('#ticketDivId').find('p.jquery_form_error_message').remove(); 
                       $("#ticketId").removeAttr("disabled");
		        	}
		        },
		    });
	 } 
}
	
	
	function saveTicket() {
		var ee = $("#txtEditor").val($('.Editor-editor').html()); 
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
			var formData = getFormData1("addTicketManagementForm");
			$.ajax({
				 type : "POST",
				 data: formData,
			     processData: false,
			     contentType:false, 
			     url : sourceleadUrl+"/saveTicket",
				 success : function(response) {
			  		if (response && response.response === "INVALID_DATA") {
						showError(response.responseMessage);
					} else if (response.response === "AWKWARD") {
						location.href = "/errorPage";
					} else if (response.response === "SUCCESS") {
						if(response.object === true) {
							$(".success_pop span").html("TicketList saved successfully...");
							$(".success_pop").show();
							cancelTicketCategory();
							setTimeout(function() {
							getTicketCategoryList();
							$(".success_pop").hide();
					 		}, 1000);
						}
					} else if (response.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
						showError("Operation failed, please try again");
					}
				},
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

										<!-- Ticket Attachment -->
<script>
function getExpenseAttachmentStatus(rowId){
    $('#0docFileuploadFileGroup'+rowId).find('p.jquery_form_error_message').remove();
	$('#0docFile'+rowId).removeAttr('data-validation');
	var category = $('#categoryType'+rowId).val();
	if(category !=null && category !='' && category!='Undefined'){
		var idArray = category.split("\,")
		if(idArray[0] != null && idArray[0]!='' && idArray[0]!='undefined' && idArray[0] > 0){
			var ticketId = idArray[0];
			$.ajax({
				type : "POST",
				url : sourceleadUrl+"/getTicketAttachmentStatus?&ticketId="+ticketId,
				success : function(data) {
					
					if(data == 'Y'){
						$('#0docFile'+rowId).attr('data-validation','required');
					}
				},
			});
		}
	}
}
</script>
<script type="text/javascript">
	function removeDbAttachment(documentId,entityId) {
		var validation_required = "true";
		$("#"+documentId+"document"+entityId).attr('style','display:none');
		var dbAttachmentMaxRowNumber = $("#dbAttachmentMaxRowNumber"+entityId).val();
		if(dbAttachmentMaxRowNumber != null && dbAttachmentMaxRowNumber != '' && dbAttachmentMaxRowNumber != 'undefined'){
			for(var i = 0; i < dbAttachmentMaxRowNumber; i++){
			   if($("#"+i+"document"+entityId).is(':visible'))
			   {
				   validation_required = "false";
				   
			   }
			}
		}
		
		if(validation_required == "true"){
			 $("#0docFile"+entityId).removeAttr('data-validation-optional');
			 $("#0docFile"+entityId).attr('data-validation','required');
		}
	}
</script>
<script>
function addAttachment(entityRowId,from){ 
	      
			var checkMaxTsRowNum = $("#attcahmentMaxRowNumber"+entityRowId).val();
			var tbl = document.getElementById("attachmentTable"+entityRowId);
			var maxRowNum = parseInt($("input[name = 'attcahmentMaxRowNumber"+entityRowId+"']").val());
			$("input[name = 'attcahmentMaxRowNumber"+entityRowId+"']").val(maxRowNum + 1);
			
			var row = tbl.insertRow(maxRowNum);
			row.setAttribute("id", entityRowId+"divAttachmentTsRow" + maxRowNum);
			
			
			var cell0 = row.insertCell(0);
			var divAttachmentTdStr = document.getElementById("divAttachmentTd1").innerHTML;
			divAttachmentTdStr = divAttachmentTdStr.replace(/REPLACEROWID/g, (maxRowNum));
			divAttachmentTdStr = divAttachmentTdStr.replace(/ENTITYROWID/g, (entityRowId));
			if(from != null && from != '' && from !='undefined' && from == 'EDIT'){
				divAttachmentTdStr = divAttachmentTdStr.replace(/ENTITYROWID/g, "data-validation-optional='true'");
			}
			else{
				divAttachmentTdStr = divAttachmentTdStr.replace(/ENTITYROWID/g, "data-validation='required'");
			}
			cell0.innerHTML = divAttachmentTdStr;
			
			/* timesheetAttachmentValidationFun(); */
			
  }			
</script>
<script type="text/javascript">
	function removeExpenseRow(rowId,detailsRowId) {
		 document.getElementById("divEntityTsRow"+rowId).style.display = 'none';
		 document.getElementById("divEntityTsRow"+rowId).value= '';
		 var  rId = "divEntityTsRow"+rowId;
		 var r = document.getElementById("categoryTable").rows[rId];
	    document.getElementById("divEntityTsRow"+detailsRowId).style.display = 'none';
		document.getElementById("divEntityTsRow"+detailsRowId).value= '';
		var  rId1 ="divEntityTsRow"+detailsRowId;
		var r1 = document.getElementById("categoryTable").rows[rId1];
	    
		var from = $("#addTicketItemButton").attr('data-id');
		if(from == null || from == '' || from == 'undefined'){
			from = "emp";
		}
		$("#addTicketItemButton").attr('href','javascript:addExpenseRow("'+from+'");');
	    $("#addTicketItemButton").removeClass("btn-default");
 	    $("#addTicketItemButton").addClass("btn-primary");
		
		//subtract row amount
		var totlaMount = $("#expenseAmountDivId").val();
		var expenseAmount = $("#expenseAmount"+rowId).val();
		var sub = totlaMount - expenseAmount; 
		var toFixed = sub.toFixed(2);
		$("#expenseAmountDivId").val(toFixed);
		
	    var count = r.cells.length;
	     for(i=0;i<count;i++){
	        if(i==0){
	    		 r1.deleteCell(0);
	    	}
	    	
	        r.deleteCell(0);
	     } 
	     
	     
	}
</script>

<div id="divAttachmentTd1" style="display: none; float: left;">
	   <div id='REPLACEROWIDdocFileuploadFileGroupENTITYROWID'>
 			<input type='file' name='REPLACEROWIDdocFileENTITYROWID' id='REPLACEROWIDdocFileENTITYROWID' VALIDATIONCONDITION  field-name="Attachment" class="timesheetAttachmentValidation" onchange='validateUploadFile(REPLACEROWID,ENTITYROWID)'> 
 			<i class="fa fa-times Cred" onclick="removeAttachmentRow(REPLACEROWID,ENTITYROWID);"></i>
 		</div>
 </div>

<div id="divAddRowTr1Td8" style="display: none; float: left;">
       <div id="hideAndShowDetailsDivREPLACEROWID" class="tbldata-dotdot_50">
			<span id="hideAndShowDetailsButtonREPLACEROWID" style="display:none;"> <i class="fa fa-pencil-square-o AddRow" onclick="hideAndShowDetailsRow(REPLACEDOWNROWID,'Edit');"></i></span>
			<span id="hideAndShowDeleteButtonREPLACEROWID"  style="display:none;" ><i class="glyphicon glyphicon-trash Cred" onclick="removeExpenseRow(REPLACEROWID,REPLACEDOWNROWID);"></i></span>
		</div>
</div>

<div id="divAddRowTr1Td9" style="display: none; float: left;">
    <input type='hidden' id="expenseApprovedAmountDivInputREPLACEROWID"/>
    <div id="expenseApprovedAmountDivREPLACEROWID">--</div>
</div>
 <script>
function deleteAttchment(documentId){
	if($("#ticketManagementDocumentsId input[name*='removedDocumentIds'][data-id=removedDocumentIds]").length > 0) {
		$("#ticketManagementDocumentsId input[name*='removedDocumentIds'][data-id=removedDocumentIds]").remove();
	}
	var i = $("#ticketManagementDocumentsId input[type=hidden][name*='removedDocumentIds']").length;
	var html = "<input type='hidden' name='removedDocumentIds["+i+"]' data-id='removedDocument' value='"+documentId+"'/>";
	$("#ticketManagementDocumentsId").append(html);
	
/* $.ajax({
	type : "POST",
	url : sourceleadUrl+"/deleteDocumentByDocumentId?"+tokenParamName+"="+tokenValue+"&documentId="+documentId,
	success : function(data) {
		return true;
	},
}); */
}
</script>
<script type="text/javascript">
	function removeAttachmentRow(rowId,entryRowId) {
		 document.getElementById(entryRowId+"divAttachmentTsRow"+rowId).style.display = 'none';
		 document.getElementById(entryRowId+"divAttachmentTsRow"+rowId).value= '';
		 var  rId = entryRowId+"divAttachmentTsRow"+rowId;
		  var r = document.getElementById("attachmentTable"+entryRowId).rows[rId];
          var count = 2*(r.cells.length);
		    for(i=0;i<=count;i++){
		        r.deleteCell(0);
		    } 
		 
	}
</script>
<script>
function hideAndShowDetailsRow(rowId,type){
	$(".fa-plus-square-o").toggleClass("closExp");
	var entityId = parseInt(rowId-1);
	if ($('#add_expenses_form').validate(false, validationSettings)){
		var validation_required = "true";
		 var maxRecCount = $("#maxRecCount").val();
		 if(type != null && type != 'undefined' && type != '' && type == 'Edit'){
			 $("#addTicketItemButton").removeAttr('href');
			 $("#addTicketItemButton").removeClass("btn-primary");
			 $("#addTicketItemButton").addClass("btn-default");
			 for(var i = 2; i < maxRecCount; i=i+2){
				 if(rowId != i){
					 if($("#divEntityTsRow"+i).is(':visible'))
					   {
							$("#divEntityTsRow"+i).removeAttr('style');
							$("#divEntityTsRow"+i).attr('style','display:none');
					   }
				 }
			  } 
		 }
		 else{
			   var from = $("#addTicketItemButton").attr('data-id');
				if(from == null || from == '' || from == 'undefined'){
					from = "emp";
				}
				$("#addTicketItemButton").attr('href','javascript:addExpenseRow("'+from+'");');
			    $("#addTicketItemButton").removeClass("btn-default");
	    	    $("#addTicketItemButton").addClass("btn-primary");
		 }
		
		var dbAttachmentMaxRowNumber = $("#dbAttachmentMaxRowNumber"+entityId).val();
		if(dbAttachmentMaxRowNumber != null && dbAttachmentMaxRowNumber != '' && dbAttachmentMaxRowNumber != 'undefined'){
			for(var i = 0; i < dbAttachmentMaxRowNumber; i++){
			   if($("#"+i+"DbdocFileIsDeleted"+entityId).val() == 'N')
			   {
				   $("#"+i+"document"+entityId).removeAttr('style');
				   validation_required = "false";
				   
			   }
			}
		}
		 $("#0docFile"+entityId).removeAttr('data-validation');
		 $("#0docFile"+entityId).removeAttr('data-validation-optional');
		displayCalendar(entityId);
		if($("#divEntityTsRow"+rowId).is(':visible'))
	   {
			
			$("#divEntityTsRow"+rowId).removeAttr('style');
			$("#divEntityTsRow"+rowId).attr('style','display:none;');
			
			var from = $("#addTicketItemButton").attr('data-id');
			if(from == null || from == '' || from == 'undefined'){
				from = "emp";
			}
			$("#addTicketItemButton").attr('href','javascript:addExpenseRow("'+from+'");');
		    $("#addTicketItemButton").removeClass("btn-default");
    	    $("#addTicketItemButton").addClass("btn-primary");
	   }
		else{
			$("#divEntityTsRow"+rowId).removeAttr('style');
			$("#categoryDateDiv"+entityId).removeClass("ColRed");   
			if(validation_required == "true"){
				 $("#0docFile"+entityId).removeAttr('data-validation-optional');
				 getExpenseAttachmentStatus(entityId);
			}
		}
	}
}
</script>	
<script type="text/javascript">
function validate_fileupload(fileName)
{

    var allowed_extensions = new Array("ppt", "pptx", "pps", "ppsx", "doc", "docx", "pdf", "rtf", "odt", "xls", "xlsx", "csv", "zip", "txt", "jpg","jpeg","bmp","png","gif","eps","tiff","TGA","psd","svg","msg","wpd","wps");
    var file_extension = fileName.split('.').pop(); 
    for(var i = 0; i <= allowed_extensions.length; i++)
    {
        if(allowed_extensions[i] != null && allowed_extensions[i] != '' && allowed_extensions[i] != 'undefined' && allowed_extensions[i]==file_extension.toLowerCase())
        {
            return true; // valid file extension
        }
    }

    return false;
}
</script>
<script type="text/javascript">
 function validateUploadFile(id,entityId)
	{
	      var selectedFile = $('#'+id+'docFile'+entityId).val();
		  if(validate_fileupload(selectedFile)){
			      var docSize = $('#'+id+'docFile'+entityId)[0].files[0].size;
		           $("#"+id+"docFileuploadFileGroup"+entityId).find('p.jquery_form_error_message').remove();
		    	  $('input#'+id+'docFile'+entityId).removeAttr( "record-exist record-exist-errormsg");
	   		      $('#'+id+'docFileuploadFileGroup'+entityId).find('p.jquery_form_error_message').remove(); 
	   		      fileUpload(""+id+"docFile"+entityId+"", "ticket", "addTicketManagementForm","expenseEntry["+parseInt(((entityId+1)/2)-1)+"].fileInfo","","");
		  }
		  else {
			      $('#'+id+'docFileuploadFileGroup'+entityId).find('p.jquery_form_error_message').remove();
			      $('#'+id+'docFileuploadFileGroup'+entityId).find('i').after("<p class='jquery_form_error_message'>Invalid Format</span>");
				  $('#'+id+'docFile'+entityId).attr('record-exist','yes');
				  $('#'+id+'docFile'+entityId).attr('record-exist-errorMsg','Invalid Format'); 
		  }
		
		 
	 }
</script>

<jsp:include page="../masterFooter.jsp" />