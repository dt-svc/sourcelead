<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 <jsp:include page="addExpenseEntries.jsp"></jsp:include>
 
 <script type="text/javascript">
		window.onload = function () {
			var entrySize = '${expenses.expenseEntry.size()}';
			if(entrySize>0){
				$('#maxRecCount').val('${2*expenses.expenseEntry.size()+1}');
			}
			else{
				$('#maxRecCount').val('1');
			}
			calculateTotalamount();
			getClientBasedOnSelectedProject();
		 };
</script>

<div class="wraper"> 
<!-- View Content Area Starts-->
<c:if test = "${type == 'view'}">
  <jsp:include page="viewApprovalExpenses.jsp"></jsp:include>
 </c:if>
 <c:if test = "${type != 'view'}">
  <section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>${type != 'edit' ? 'Add' : 'Edit'} Expense</h2>
      </div>
      <c:choose>
	    <c:when test="${expenses.id !='' && expenses.id != null}">
	        <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='/addApprovalExpense?expenseId=${expenses.expenseAutoId}&expenseLocationCode=${expenses.expenseLocationCode}&type=view"'/>">View Expenses Approval</a> &raquo; Edit Expense</div> 
	    </c:when>    
	    <c:otherwise>
	       <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='/expensesApprovalList'/>">Expenses Approval</a> &raquo; Add Expense</div> 
	    </c:otherwise>
	</c:choose>
    </section>
    <form:form id="add_expenses_form" name="add_expenses_form">
	<div class="content-area content-new clearfix">
         
          <div class="clearfix form-horizontal inner-content">
            <div class="row">
              <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
                <div class="form-group">
					<label class="col-md-3">Select Project <span class="text-danger">&nbsp;*</span></label>
					<div class="col-md-9" id="errProject">
						<select class="chosen-select form-control" autocomplete="off" onchange="getClientBasedOnSelectedProject();" id="projectId" name="projectId" field-name="Project" >

							 <c:if test="${fn:length(expEntry.projectsList) gt 0}">
								<option value="">Select Project</option>
							</c:if>
							<c:forEach var="projects" items="${expEntry.projectsList}">
							       <option  ${expenses.projectId == projects.id ? 'selected' : '' } value="${projects.id}">${projects.projectName}</option>
							</c:forEach>
						</select>
					</div>
				</div> 
                <div class="form-group">
                  <label class="col-md-3">Purpose</label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" autocomplete="off" name="expensePurpose" value="${expenses.expensePurpose}" data-validation-optional="true" data-validation="validate_Space validate_length length1-100">
                  </div>
                </div> 
                
                
				<div class="form-group">
                  <label class="col-md-3">Expenses Id<span class="text-danger" >&nbsp;*</span></label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" id="expenseAutoId" name="expenseAutoId" value="${expenses != null ? expenses.expenseAutoId : ''}" readonly="readonly">
                    <input type="hidden"  class="form-control" name="id" value="${expenses.id}">
                  </div>
                </div>
                 
				<div class="form-group">
					<label class="col-md-3">Client</label>
	                  <div class="col-md-9">
	                    <input type="text" class="form-control" autocomplete="off" name="clientName" value="${expenses.clientName}" id="clientNameId" readonly="readonly">
	                  </div>
				</div> 
				
				<!--  removeErrProject();flushOnchange();removeErrProjectEmployee(); -->
				 <div class="form-group" id="errEmployeeRemove">
				<label class="col-md-3">Select Employee<span class="text-danger">&nbsp;*</span></label>
				<div class="col-md-9">
					<select class="chosen-select form-control" autocomplete="off" id="projectMemberId" name="projectMemberId"  field-name="Employee" data-validation="required" onchange="getProjectMemberStartDateAndEndDate();">
					</select>
				</div>
			   </div>              
	            <div class="form-group">
	              <label class="col-md-3">Start & End date<span class="text-danger">&nbsp;*</span></label>
	              <div class="col-md-9" >
						<div class="row">
						  <div class="col-lg-6 evDaPa" id="dateDiv">
							  <input type="text" autocomplete="off" class="form-control calendar" data-validation="required" value="${expenses.startDate}" field-name="Start Date" name="startDate" id="projectStartDateExpensesId" placeholder="Start Date" readonly="readonly">
						  </div>
						  <div class="col-lg-6" id="errEnd">
							  <input type="text" autocomplete="off" class="form-control calendar" data-validation="required" value="${expenses.endDate}" name="endDate"  field-name="End Date" id="projectEndDateExpensesId" placeholder="End Date" readonly="readonly">
						  </div>					  
						</div>
	              </div>
	            </div>
		</div>
	</div>
</div>
<div class="expensHead">
<div class="form-group col-md-6 mt5 mb5 noPad">
<span class="col-md-6 noPad">Expenses Amount Total:</span>
<div class="col-md-6">
<input type="text" id="expenseAmountDivId" name="expenseAmountDivId" value="00.00" class="ReadTextFli valid" readonly="readonly">
</div>
</div>
	<div class="pull-left" id="exceedSpan"></div>
	<a href="javascript:void(0)" class="btn btn-primary btn-sm AddRow" onclick='addExpenseRow("approver");'><i class="fa fa-plus"></i>&nbsp; Add Item</a>
	<div class="clearfix"></div>
</div>
<div class="project_wrap_table expensTableView">
 <input type="hidden"  name="maxRecCount" id="maxRecCount" value="${2*expenses.expenseEntry.size()+1}">
	<table id="categoryTable">
	    <thead>
			<tr id="divEntityTsRow0">
				<th>Date</th>
				<th>Exp Category</th>
				<th>Description</th>
				<th>Amount</th>
				<th>Currency</th>
				<th>Payment Mode</th>
				<th>Paid By</th>
				<th>Action</th>
			</tr>
		</thead>	
			<tbody>
			     <c:forEach var="entry" items="${expenses.expenseEntry}" varStatus="entryCount">
			        <tr id="divEntityTsRow${(2*(entryCount.count))-1}">
			           <td><div id="categoryDateDiv${entryCount.count}">${entry.dateOccurred}</div></td>
			           <td><div id="categoryTypeDiv${entryCount.count}" title="${entry.categoryName}">${entry.categoryName}</div></td>
			           <td><div id="entryDesDiv${entryCount.count}" class="tbldata-dotdot-me" title="${entry.entryDescription}">${entry.entryDescription}</div></td>
			           <td><div id="expenseAmountDiv${entryCount.count}">${entry.categoryAmount}</div></td>
			           <td><div id="expenseRateType${entryCount.count}">${entry.categoryAmountType}</div></td>
			           <td><div id="paymentModeDiv${entryCount.count}">${entry.paymentMode}</div></td>
			           <td><div id="paidByDiv${entryCount.count}">${entry.paidBy}</div></td>
			           <td><div id="hideAndShowDetailsDiv${entryCount.count}">
			                <i class="fa fa-pencil-square-o AddRow" title="Edit Expenses"  onclick="hideAndShowDetailsRow(${2*(entryCount.count)},'Edit');"></i>
			                <i class="glyphicon glyphicon-trash Cred" title="Delete Expenses"  onclick="removeExpenseRow(${(2*(entryCount.count))-1},${2*(entryCount.count)})"></i>
			              </div>
			          </td>
			        </tr>
			        <tr id="divEntityTsRow${2*(entryCount.count)}" style="display:none;">
			          <td colspan="8">
				         <div class="expDetails">
				         <input type="hidden" id="expenseEntryId${entryCount.count}" name="expenseEntry[${entryCount.index}].id" value='${entry.id}'/>
				          <div class="col-xs-12 col-sm-12 col-md-12 expAddRo myForm noPad clearfix">            
							<div class="col-md-4 mb10 chosen-custom">
							
															
						<label>Category Type<span class="text-danger">&nbsp;*</span></label>
						<select class="chosen-select form-control" id="categoryType${entryCount.count}" name="expenseEntry[${entryCount.index}].categoryType" field-name="Category Type" data-validation="required" onchange="getAmountValueCategory(${entryCount.count});">
						<option value="">Select Category Type</option>
						<c:forEach var="project" items="${expEntry.projectsList}">
						   <c:if test="${project.id == expenses.projectId}">
					 	    	 <c:forEach var="expCat" items="${project.expenseCategoryList}">
					 	    	      <option ${entry.categoryTypeId == expCat.id ? 'selected' : ''} value="${expCat.id},${expCat.allowedAmount}">${expCat.categoryName}</option>
					 	    	 </c:forEach>
						  </c:if>
						</c:forEach>
						</select>
					</div>
							<div class="col-md-4 mb10">
								<label>Date Occurred<span class="text-danger">&nbsp;*</span></label>
								<input type="text" id="categoryDate${entryCount.count}" name="expenseEntry[${entryCount.index}].dateOccurred" readonly="readonly" onclick="displayCalendar('${entryCount.count}')" class="form-control calendar" value="${entry.dateOccurred}" placeholder="Date" field-name="Date" data-validation="required"> 
							</div>
							<div class="col-md-4 mb10">
								<label>Receipt Issued by</label>
								<input type="text" id="receiptIssuedBy${entryCount.count}" name="expenseEntry[${entryCount.index}].receiptIssuedBy" class="form-control" value="${entry.receiptIssuedBy}"  placeholder="Receipt Issued By" field-name="Receipt Issued By" data-validation-optional="true" data-validation="validate_length length1-100"> 
							</div>
							<div class="col-md-4 mb10">
								<label>Payment Mode</label>
								<select id="paymentMode${entryCount.count}" name="expenseEntry[${entryCount.index}].paymentMode" class="form-control" field-name="Payment Mode" data-validation-optional="true">
								         <option value="">Select Mode</option>
								         <c:forEach var="paymentMode" items="${expEntry.paymentModes}">
							                  <option  ${entry.paymentMode == paymentMode ? 'selected' : ''} value="${paymentMode}">${paymentMode}</option>
							             </c:forEach>
								</select>
							</div>	
							<div class="col-md-4 mb10">
								<label>Paid by</label>
								<select class="chosen-select form-control" id="paidBy${entryCount.count}" name="expenseEntry[${entryCount.index}].paidBy" class="form-control" field-name="Payment Mode" data-validation-optional="true">
								         <option value="">Select Paid By</option>
								          <c:forEach var="expEntrypaidBy" items="${expEntry.paidByType}">
							                   <option  ${entry.paidBy == expEntrypaidBy ? 'selected' : ''} value="${expEntrypaidBy}">${expEntrypaidBy}</option>
							             </c:forEach>
								</select>
							</div>
							
		                  <div id="amountDiv${entryCount.count}" class='col-md-4 mb10 valid_ation'>
			                   <label>Amount<span class='text-danger'>&nbsp;*</span></label>
								  <div class='input-group positionRelative'>
										<div class='input-group-btn tblminwidth-75 select-field dropdown_inr' style='min-width: 65px !important;'>
											 <select class='btn btn-info' id='expenseRateType${entryCount.count}' name='expenseEntry[${entryCount.index}].categoryAmountType' field-name='Rate Type' field-name='Rate Type' data-validation='required'>
												   <c:forEach var="currencyType" items="${expEntry.currencyCodeList}">
									                  <option  ${entry.categoryAmountType == currencyType ? 'selected' : ''} value="${currencyType}">${currencyType}</option>
									              </c:forEach>
										  </select>
									</div>
								         <input type='text' class='form-control allownumericwithdecimal'  placeholder='Amount'  name='expenseEntry[${entryCount.index}].categoryAmount' id='expenseAmount${entryCount.count}' value='${entry.categoryAmount}' field-name='Amount' data-validation='required validate_float validate_length length1-10' onkeyup="getAmountValue(${entryCount.count});">
									</div>
		                 </div>
						<div class="col-md-4 mb10">
							<label>Description<span class='text-danger'>&nbsp;*</span></label>
							<textarea rows="2"  id="entryDes${entryCount.count}" name="expenseEntry[${entryCount.index}].entryDescription" class="form-control" placeholder="Description" field-name="Description" data-validation="required validate_Space validate_length length1-5000" onblur="removeWhiteSpacesMyExp(${entryCount.count});">${entry.entryDescription}</textarea>
						</div>		
		                    <div id='attachmentSection' class='col-md-4 mb10 minHe'>
		                   <div class="uploadFile timesheet_week_comm">
			                  <input type='hidden' name='dbAttachmentMaxRowNumber${(2*(entryCount.count))-1}'  id='dbAttachmentMaxRowNumber${(2*(entryCount.count))-1}' value='${entry.fileInfo.size()}' />
			                 <input type='hidden' name='attcahmentMaxRowNumber${(2*(entryCount.count))-1}'  id='attcahmentMaxRowNumber${(2*(entryCount.count))-1}' value='1' />
			                 <div id='timesheetDocumentsId' class=''>
			                       <c:forEach var="entryDoc" items="${entry.fileInfo}" varStatus="entryDocCount">
			                       <c:if test = '${entryDoc.status == 1}'>
			                         <strong class='proDown' id='${entryDocCount.index}document${(2*(entryCount.count))-1}'>
			                          <input type='hidden' name='${entryDocCount.index}DbdocFileId${(2*(entryCount.count))-1}' id='${entryDocCount.index}DbdocFileId${(2*(entryCount.count))-1}' value='${entryDoc.fileDbId}'/>
			                          <input type='hidden' name='${entryDocCount.index}DbdocFileIsDeleted${(2*(entryCount.count))-1}' id='${entryDocCount.index}DbdocFileIsDeleted${(2*(entryCount.count))-1}' value='N'/>
			                             <a class='downicon' href='${entryDoc.fileURL}'>
			                                 <i class='glyphicon glyphicon-save'></i>
								             <input type='button' value='${entryDoc.fileName}'/>
								        </a>
								        <input class='dele' type='button' onclick="removeDbAttachment(${entryDocCount.index},${entryCount.count});deleteAttchment(${entryDoc.fileDbId})" />
			                         </strong>
			                         </c:if>
			                       </c:forEach>
			                 </div> 
			                 <i class='fa fa-info-circle' title='Allowed File Formats: ppt,pptx,pps,ppsx,doc,docx,pdf,rtf,odt,xls,xlsx,csv,  zip,txt,jpg,jpeg,bmp,png,gif,eps,tiff,TGA,psd,svg,msg,wpd,wps      Allowed Size : 2MB '></i>
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
						      <div class="col-xs-12 col-sm-12 col-md-12 expAddRo myForm noPad clearfix">
							      <div class="pull-right  mr mt10">
							        <input type="button" value="Update" id="addExpenseCategoryButton${entryCount.count}" class="btn btn-primary ml" onclick="addExpenseCategory(${(2*(entryCount.count))-1});">
							        <input type="button" id="cancelExpenseCategoryButton${entryCount.count}" value="Cancel" class="btn btn-default ml" onclick="hideAndShowDetailsRow(${2*(entryCount.count)},'Cancel');">
							      </div>
						      </div>
							 <div class="clearfix" style="position: static;"></div>
			             </div>
			            </td>
			        </tr>
			     </c:forEach> 
		   </tbody>
	</table>
</div>
  <div class="form-footer clearfix mt">
		<div class="col-lg-12">
		
		
			<div class="pull-right">
			<!-- <input type="button" value="Save" class="btn btn-sm btn-primary"> -->
			<input type="button" id ="submitForApprovalExpensesId"value="Submit" class="btn btn-sm btn-primary" onclick="saveAddExpensesData('submitted');">
			<input type="button" id ="submitForApprovalExpensesId"value="Approve" class="btn btn-sm btn-success ml10" onclick="saveAddExpensesData('approved');">
			<input type="button" value="Cancel" id="cancelForApprovalExpensesId" class="btn btn-sm btn-default ml10" onclick="location.href='<c:url value='/addApprovalExpense?expenseId=${expenses.expenseAutoId}&expenseLocationCode=${expenses.expenseLocationCode}&type=view'/>' "  >
		</div>
   </div>
  </div>
  </div>
</form:form>
  </section>
</c:if>
</div>

<script>
function deleteAttchment(documentId){
$.ajax({
	type : "POST",
	url : sourceleadUrl+"/deleteDocumentByDocumentId?"+tokenParamName+"="+tokenValue+"&documentId="+documentId,
	success : function(data) {
		return true;
	},
});
}
</script>

<script>
$(".allownumericwithdecimal").on("keypress keyup blur",function (event) {
    $(this).val($(this).val().replace(/[^0-9\.]/g,''));
        if ((event.which != 46 || $(this).val().indexOf('.') != -1) && (event.which < 48 || event.which > 57)) {
            event.preventDefault();
        }
    });	
</script>

<script>
function acceptReopenExpenseBySuperUser(expenseId,reOpenedUserId){
	$("#acceptReopenExpenseBySuperUserDiv").empty();
	$("#acceptReopenExpenseBySuperUserDiv").prepend('<i class="fa fa-cog fa-spin"></i>');
	var userId = '${SourceLeadWeb_user.id}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var accept = 'accept';
	$.ajax({
		type : "POST",
		url : sourceleadUrl+"/acceptOrRejectReopenExpenseBySuperUser?"+tokenParamName+"="+tokenValue+"&expenseId="+expenseId+"&reOpenedUserId="+reOpenedUserId+"&type="+accept+"&userId="+userId+"&orgLocationId="+orgLocationId,
		success : function(data) {
			 location.replace(sourceleadUrl+"/addApprovalExpense?expenseId="+'${expenses.expenseAutoId}'+"&expenseLocationCode="+'${expenses.expenseLocationCode}'+"&type=view"); 
		},
	});
}
</script>
<script>
function rejectReopenExpenseBySuperUser(expenseId,reOpenedUserId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Re-open message here</p>";
	result = result +"		<textarea  id='reOpenRejectedReason' name='reOpenRejectedReason'  placeholder='Comments here' onkeydown='return (event.keyCode!=13);'></textarea>";
	result = result + ' <div id="reOpenRejectedReasonDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Submit' onclick='storeReopenRejectedReason("+expenseId+","+reOpenedUserId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelReOpenRejected();'></b>";
	result = result +"	</div>";
	$("#reOpenRejectedPopup").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>
<script>
function cancelReOpenRejected(){
	$("#reOpenRejectedPopup").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>
<script>
function storeReopenRejectedReason(expenseId,reOpenedUserId){
	var superUserId = '${SourceLeadWeb_superUserId}';
	var userId = '${SourceLeadWeb_user.id}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var reason = document.getElementById("reOpenRejectedReason").value;
	if(reason.match(/^[^\s-](.*)*$/) != null){
		$("#reOpenRejectedReasonDiv").empty();
		$.ajax({
		   	type: "POST",
		   	url : sourceleadUrl+"/acceptOrRejectReopenExpenseBySuperUser?"+tokenParamName+"="+tokenValue+"&expenseId="+expenseId+"&reOpenedUserId="+reOpenedUserId+"&type=reject&reason="+reason+"&userId="+userId+"&orgLocationId="+orgLocationId,	    	   
		    success: function(data) {
		    	 location.replace(sourceleadUrl+"/addApprovalExpense?expenseId="+'${expenses.expenseAutoId}'+"&expenseLocationCode="+'${expenses.expenseLocationCode}'+"&type=view"); 
		    }
	   });
	}else{
		if(reason){
			$("#reOpenRejectedReasonDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Must not Start With Space</font>") ;
		}else{
			$("#reOpenRejectedReasonDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
		}
	}
	  
}
</script>

<script type="text/javascript">
function reOpenMyExpense(expenseId){ 
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Re-open message here</p>";
	result = result +"		<textarea  id='reOpenReason' name='reOpenReason'  placeholder='Comments here' onkeydown='return (event.keyCode!=13);'></textarea>";
	result = result + ' <div id="reOpenReasonDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button'  value = 'Submit' onclick='notifyReopenExpense("+expenseId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelReOpen();'></b>";
	result = result +"	</div>";
	$("#reOpenPopup").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>



<script>
function notifyReopenExpense(expenseId){
	alert("hi "+expenseId);
	var reason = document.getElementById("reOpenReason").value;
	alert(reason);
	if(reason.match(/^[^\s-](.*)*$/) != null){
		alert("hi");
		$("#reOpenReasonDiv").empty();
		$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/notifyReopenExpense?"+tokenParamName+"="+tokenValue+"&expenseId="+expenseId+"&reason="+reason,	    	   
		    success: function(data) {
		    	 location.replace(sourceleadUrl+"/addApprovalExpense?expenseId="+'${expenses.expenseAutoId}'+"&expenseLocationCode="+'${expenses.expenseLocationCode}'+"&type=view"); 
		    }
	   });
	}else{
		if(reason){
			$("#reOpenReasonDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Must not Start With Space</font>") ;
		}else{
			$("#reOpenReasonDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
		}
	}
	  
}
</script>

<script>
function cancelReOpen(){
$("#reOpenPopup").empty();
$(".project_inn_mask").fadeOut(200);
}
</script>

<!-- <script>
function getMembersForSelectedProject(){
	var projectId = document.getElementById("projectId").value;
	$.ajax({
	      type : "GET",
	       url: sourceleadUrl+"/getMembersForSelectedProject?projectId="+projectId,       
	       success :function(json) {
	    	   if(json != null && json !='undefined' && json !=''){
	    	   /* var json = JSON.parse(data); */
	    	   var result = "";
	    	   var projectStartDate = '';
	  		   var projectEndDate = '';
	    	   result = result + '<option value="">Select Employee</option>';
			       	for (var i=0; i<json.length; i++){
				        var opt = json[i];
				        result = result + '<option  value="'+opt.id+'">'+opt.empName+'</option>';
				        projectStartDate = opt.projectAssignedDateStr;
			 			projectEndDate = opt.projectEndDateStr;
			 			expensesDatePickerStartDate(projectStartDate,projectEndDate);
			       	}
				$("#projectMemberId").empty();
				$("#projectMemberId").append(result);
				$("#projectMemberId").trigger("chosen:updated");
	    	   }
	       },
	});
}
</script> -->



<script>
function getClientBasedOnSelectedProject(){
	$('#clientNameId').val('');
	$('#expenseAutoId').val('');
	 var projectId = document.getElementById("projectId").value;
	 var result = "";
	 if(projectId != null && projectId !='undefined' && projectId !=''){
		 result = result + '<option value="">Select Employee</option>';
		 <c:forEach var="project" items="${expEntry.projectsList}">
		 if('${project.id}' == projectId){
			 $('#clientNameId').val('${project.client}');
			 $('#expenseAutoId').val('${project.expenseId}');
			 <c:forEach var="pm" items="${project.projectMembersList}">
			     result = result + '<option ${expenses.masterCandidateId == pm.masterCandidateId ? "selected" : " " }  value="${pm.id}">${pm.name}</option>';
			 </c:forEach>
		 }  
 	    </c:forEach>
	 }
	 $("#projectMemberId").empty();
	 $("#projectMemberId").append(result);
  	 $("#projectMemberId").trigger("chosen:updated");
}
</script>
<script>
function getProjectMemberStartDateAndEndDate(){
	var projectId =$("#projectId").val();
	var projectMemberId = $("#projectMemberId").val();
	 if(projectId != null && projectId !='undefined' && projectId !='' 
			 && projectMemberId != null && projectMemberId !='undefined' && projectMemberId !=''){
		 var projectStartDate = '';
		 var projectEndDate = '';
		 <c:forEach var="project" items="${expEntry.projectsList}">
		 if('${project.id}' == projectId){
			 <c:forEach var="pm" items="${project.projectMembersList}">
			 if('${pm.id}' == projectMemberId){
				   projectStartDate = '${pm.memberStartDate}';
	 			   projectEndDate = '${pm.memberEndDate}';
	 			  expensesDatePickerStartDate(projectStartDate,projectEndDate);
			 }  
			 </c:forEach>
		 }
 	    </c:forEach>
	 }
}
</script>


<script type="text/javascript">
function removeWhiteSpaces(rowCount){
	var el = document.getElementsByName("entryDes"+rowCount)[0];
	var val = el.value.replace(/(^\s*)|(\s*$)/gi,"")
	$("#entryDes"+rowCount).val(val);
}
</script>


<script>
	function saveAddExpensesData(status) {
		if ($('#add_expenses_form').validate(false, validationSettings)){
			var entriesCount = $('#categoryTable >thead >tr').length;
			var maxRecCount = $('#maxRecCount').val();
			var validation_required = "true";
			if(maxRecCount != null && maxRecCount != '' && maxRecCount != 'undefined'){
				for(var i = 1; i < maxRecCount; i++){
				   if($("#divEntityTsRow"+i).is(':visible'))
				   {
					   validation_required = "false";
				   }
				}
			}
			if(validation_required == "true"){
				$("#exceedSpan") .html( "<p class='textWarning small'><i class='fa fa-exclamation-triangle paddingRight mt5'></i> Minimum one category is required</p>");
				return false;
			}
			else{
				//$("#submitForApprovalExpensesId").prop('disabled', true);
				//$("#cancelForApprovalExpensesId").prop('disabled', true);
			/* var frm = $('#add_expenses_form').serializeFormJSON();
			var jsonData = JSON.stringify(frm);
			var form = $('#add_expenses_form')[0];
      		var formData = new FormData(form); 
      		formData.append("json",jsonData);
			var sourceleadUrl = '${sourceleadUrl}'; */
			/* var form = $('#add_expenses_form')[0];
      		var formData = new FormData(form);  */
      		 var formData = getFormData("add_expenses_form");
			$( "#submitForApprovalExpensesId" ).addClass('button').val('Processing..');
			$.ajax({
       	        type : "POST",
        	    data: formData,
				url : sourceleadUrl+"/storeAddExpensesData?"+tokenParamName+"="+tokenValue+"&expenseStatus="+status,
				success : function(data) {
					$( "#submitForApprovalExpensesId" ).addClass('button').val('Sub for Approval');
					window.location.replace(sourceleadUrl+'/expensesApprovalList');
				},
			});
			return true;
		}
		}else{
			return false;
		}
	}
</script>

<script>
	var validationSettings = {
		errorMessagePosition : 'element',
		borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd'
	};
</script>

<script>
	$(function() {
		$("#datepicker,#datepicker2").datepicker();
		$("#datepicker5").datepicker();
	});
</script>
<script type="text/javascript">
	var config = {
		'.chosen-select' : {},
		'.chosen-select-deselect' : {
			allow_single_deselect : true
		},
		'.chosen-select-no-single' : {
			disable_search_threshold : 10
		},
		'.chosen-select-no-results' : {
			no_results_text : 'Oops, nothing found!'
		},
		'.chosen-select-width' : {
			width : "95%"
		}
	}
	for ( var selector in config) {
		$(selector).chosen(config[selector]);
	}
	$('.chosen-select').each(function() {
		var $this = $(this);
		$this.next().css({
			'width' : '100%'
		});
	});
</script>




<script type="text/javascript">
function flushOnchange(){
	$('#projectId').val('');	
	
$('#expensePurpose').val('');
$('#projectStartDateExpensesId').val('');
$('#projectEndDateExpensesId').val('');
$("#projectMemberId").val('')
}
</script>

<script>
	function expensesDatePickerStartDate(projectStartDate, projectEndDate) {
		
		var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatEduUpdate = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatEduUpdate = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatEduUpdate = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatEduUpdate = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatEduUpdate = 'yy-dd-mm'; 
		  }
		$("#projectStartDateExpensesId").datepicker("destroy");
		$("#projectEndDateExpensesId").datepicker("destroy");
		$('#projectStartDateExpensesId').datepicker({
					dateFormat : finalDateFormatEduUpdate,
					changeMonth : true,
					changeYear : true,
					minDate : projectStartDate,
					maxDate : projectEndDate,
					onSelect : function(selected) {
						$("#projectEndDateExpensesId").datepicker("option","minDate", selected);
						removeErrStr();
					}
				});
		$('#projectEndDateExpensesId').datepicker({
					dateFormat : finalDateFormatEduUpdate,
					changeMonth : true,
					changeYear : true,
					minDate : projectStartDate,
					maxDate : projectEndDate,
					onSelect : function(selected) {
						$("#projectStartDateExpensesId").datepicker("option","maxDate", selected);
						removeErrEnd();
					}
				});
	}
</script>

<script>
function expensesDateCheck(){
	$('#dateError').remove();
	var startDate = document.getElementById("projectStartDateExpensesId").value;
	var endDate = document.getElementById("projectEndDateExpensesId").value;
	if(startDate != null && startDate != "" && startDate != 'undefined'){
		loadCategoryDiv(startDate,endDate,null);
	}else{
		$("#dateDiv").parent().after("<p id='dateError' class='jquery_form_error_message'> Project Start Date is mandatory </p>");
		$('#addExpenseEntity').modal({
		   backdrop: 'static',
		   keyboard: false
		});
	}
}
</script>

<script>
function removeErrStr(){
	$('#dateDiv').find('p.jquery_form_error_message').remove(); 
}
function removeErrEnd(){
	$('#errEnd').find('p.jquery_form_error_message').remove(); 
}
function removeErrProject(){
	$('#errProject').find('p.jquery_form_error_message').remove(); 
}
function removeErrProjectEmployee(){
	$('#errEmployeeRemove').find('p.jquery_form_error_message').remove(); 
}
</script>

<script>
function getAmountValue(rowId){
	var categoryAmount = document.getElementById("categoryType"+rowId).value;
	var expenseAmount = document.getElementById("expenseAmount"+rowId).value;
	var amountArray = categoryAmount.split("\,");
	if(amountArray[1] != parseFloat(0)){
	if(parseFloat(expenseAmount) > parseFloat(amountArray[1])){
		$("#expenseAmount"+rowId).after("<p class='jquery_form_error_message fa fa-exclamation-triangle' style='color:sandybrown'> Amount Exceeded than Allocated-(Allocated Amount:"+amountArray[1]+")");
	}else{
		$("#amountDiv"+rowId).find('p.jquery_form_error_message').remove();
	}
   }
}

</script>


<script>
function getAmountValueCategory(rowId){
	var categoryAmount = document.getElementById("categoryType"+rowId).value;
	var expenseAmount = document.getElementById("expenseAmount"+rowId).value;
	$("#amountDiv"+rowId).find('p.jquery_form_error_message').remove();
if(expenseAmount !=null && expenseAmount !='' && expenseAmount!='Undefined'){
	var amountArray = categoryAmount.split("\,");
	if(amountArray[1] != parseFloat(0)){
	if(parseFloat(expenseAmount) > parseFloat(amountArray[1])){
		$("#expenseAmount"+rowId).after("<p class='jquery_form_error_message fa fa-exclamation-triangle' style='color:sandybrown'> Amount Exceeded than Allocated-(Allocated Amount:"+amountArray[1]+")");
	}else{
		$("#amountDiv"+rowId).find('p.jquery_form_error_message').remove();
	}
   }	
  }
}
</script>

<jsp:include page="../masterFooter.jsp" />
