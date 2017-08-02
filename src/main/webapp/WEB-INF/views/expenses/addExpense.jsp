<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 <jsp:include page="addExpenseEntries.jsp"></jsp:include>
 
 <style type="text/css">
.ReadTextFli {
	 text-align: -webkit-left;
}
</style>

 <script type="text/javascript">
		window.onload = function () {
			getClientBasedOnSelectedProject();
			
			var entrySize = '${expenses.expensesEntries.size()}';
			if(entrySize>0){
				$('#maxRecCount').val('${2*expenses.expensesEntries.size()+1}');
			}
			else{
				$('#maxRecCount').val('1');
			}
		 };
</script>
<!-- View Content Area Starts-->
<c:if test = "${type == 'view'}">
   <jsp:include page="viewExpenses.jsp"></jsp:include>
 </c:if>
<!-- View Content Area Ends-->
  <!-- Content Area -->
  <c:if test = "${type != 'view'}">
  <section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>${type != 'edit' ? 'Add' : 'Edit'} Expense</h2>
      </div>
    
	 <c:choose>
	    <c:when test="${expenses.id !='' && expenses.id != null}">
	        <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='/addExpense?expenseId=${expenses.expenseId}&type=view"'/>">View Expenses</a> &raquo; Edit Expense</div> 
	    </c:when>    
	    <c:otherwise>
	       <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='/expenses'/>">My Expenses</a> &raquo; Add Expense</div> 
	    </c:otherwise>
	</c:choose>
    
    </section>
    <form:form id="add_expenses_form" name="add_expenses_form"  enctype='multipart/form-data'>
	<div class="content-area content-new clearfix">
         
          <div class="clearfix form-horizontal inner-content">
            <div class="row">
              <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
              
                <div class="form-group">
                  <label class="col-md-3">Expenses Id<span class="text-danger" >&nbsp;*</span></label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" name="ExpenseAutoId" value="${expenses != null ? expenses.expenseId : expEntry.expenseId}" readonly="readonly">
                    <input type="hidden"  class="form-control" name="expenseId" value="${expenses.id}">
                  </div>
                </div>  
                <div class="form-group">
                  <label class="col-md-3">Purpose</label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" autocomplete="off" name="expensePurpose" value="${expenses.purpose}" data-validation-optional="true" data-validation="validate_Space validate_length length1-100">
                  </div>
                </div>                                          
				<div class="form-group">
					<label class="col-md-3">Select Project <span class="text-danger">&nbsp;*</span></label>
					<div class="col-md-9" id="errProject">

						<select class="chosen-select form-control" onchange="getClientBasedOnSelectedProject();removeErrProject();" id="projectMemberId" name="projectMemberId" field-name="Project" data-validation="required">
							<c:if test="${fn:length(expEntry.projectMembersList) gt 1}">
								<option value="">Select Project</option>
							</c:if>
							<c:forEach var="projMembers" items="${expEntry.projectMembersList}">
							       <option  ${expenses.projectMemberId == projMembers.id ? 'selected' : '' } value="${projMembers.id}">${projMembers.projectName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-3">Client</label>
	                  <div class="col-md-9">
	                    <input type="text" class="form-control" autocomplete="off" name="clientNameExpense" value="${expenses.clientName}" id="clientNameId" readonly="readonly">
	                  </div>
				</div>               
	            <div class="form-group">
	              <label class="col-md-3">Start & End date<span class="text-danger">&nbsp;*</span></label>
	              <div class="col-md-9" >
						<div class="row">
						  <div class="col-lg-6 evDaPa" id="dateDiv">
							  <input type="text" class="form-control calendar" data-validation="required" value="${expenses.startDate}" field-name="Start Date" name="startDateExpense" id="projectStartDateExpensesId" placeholder="Start Date" readonly="readonly">
						  </div>
						  <div class="col-lg-6" id="errEnd">
							  <input type="text" class="form-control calendar" data-validation="required" value="${expenses.endDate}" name="endDateExpense"  field-name="End Date" id="projectEndDateExpensesId" placeholder="End Date" readonly="readonly">
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
<input type="text" id="expenseAmountDivId" name="expenseAmountDivId" value="${expenses.totalAmount > 0 ? expenses.totalAmount : 00.00 }" class="ReadTextFli valid" readonly="readonly">
</div>
</div>
	<div class="pull-left" id="exceedSpan"></div>
	<a href="javascript:void(0)" class="btn btn-primary btn-sm AddRow" onclick='addExpenseRow();'><i class="fa fa-plus"></i>&nbsp; Add Item</a>
	<div class="clearfix"></div>
</div>
<div class="project_wrap_table expensTableView">

 <input type="hidden"  name="maxRecCount" id="maxRecCount" value="${2*expenses.expensesEntries.size()+1}">
	<table id="categoryTable">
	    <thead>
			<tr id="divEntityTsRow0">
				<th>Date</th>
				<th>Exp Category</th>
				<th>Description</th>
				<th>Amount</th>
				<th>Currency</th>
				<th>Payment Mode</th>
				<th>Action</th>
			</tr>
		</thead>	
			<tbody>
			     <c:forEach var="entry" items="${expenses.expensesEntries}" varStatus="entryCount">
			        <tr id="divEntityTsRow${(2*(entryCount.count))-1}">
			           <td><div id="categoryDateDiv${entryCount.count}">${entry.expenseDate}</div></td>
			           <td><div id="categoryTypeDiv${entryCount.count}" title="${entry.expensesCategoryId.expensesName}">${entry.expensesCategoryId.expensesName}</div></td>
			           <td><div id="entryDesDiv${entryCount.count}" class="tbldata-dotdot-me" title="${entry.description}">${entry.description}</div></td>
			           <td><div id="expenseAmountDiv${entryCount.count}">${entry.amount}</div></td>
			           <td><div id="expenseRateType${entryCount.count}">${entry.currencyType}</div></td>
			           <td><div id="paymentModeDiv${entryCount.count}">${entry.paymentMode.value}</div></td>
			           <td><div id="hideAndShowDetailsDiv${entryCount.count}">
			                <i class="fa fa-pencil-square-o AddRow" title="Edit Expenses"  onclick="hideAndShowDetailsRow(${2*(entryCount.count)},'Edit');"></i>
			                <i class="glyphicon glyphicon-trash Cred" title="Delete Expenses"  onclick="removeExpenseRow(${(2*(entryCount.count))-1},${2*(entryCount.count)})"></i>
			              </div>
			          </td>
			        </tr>
			        <tr id="divEntityTsRow${2*(entryCount.count)}" style="display:none;">
			          <td colspan="7">
				         <div class="expDetails">
				         <input type="hidden" id="expenseEntryId${entryCount.count}" name="expenseEntryId${entryCount.count}" value='${entry.id}'/>
				          <div class="col-xs-12 col-sm-12 col-md-12 expAddRo myForm noPad clearfix">            
							<div class="col-md-4 mb10 chosen-custom">
							
															
						<label>Category Type<span class="text-danger">&nbsp;*</span></label>
						<select class="chosen-select form-control" id="categoryType${entryCount.count}" name="categoryType${entryCount.count}" field-name="Category Type" data-validation="required" onchange="getAmountValueCategory(${entryCount.count});">
						<option value="">Select Category Type</option>
							<c:forEach var="expCat" items="${expEntry.expenseCategoryList}">
							       <option  ${entry.expensesCategoryId.id == expCat.id ? 'selected' : ''} value="${expCat.id},${expCat.allowedAmount}">${expCat.expensesName}</option>
							</c:forEach>
						</select>
					</div>
							<div class="col-md-4 mb10">
								<label>Date<span class="text-danger">&nbsp;*</span></label>
								<input type="text" id="categoryDate${entryCount.count}" name="categoryDate${entryCount.count}" readonly="readonly" onclick="displayCalendar('${entryCount.count}')" class="form-control calendar" value="${entry.expenseDate}" placeholder="Date" field-name="Date" data-validation="required"> 
							</div>
							<div class="col-md-4 mb10">
								<label>Receipt Issued by</label>
								<input type="text" id="receiptIssuedBy${entryCount.count}" name="receiptIssuedBy${entryCount.count}" class="form-control" value="${entry.receiptIssuedBy}"  placeholder="Receipt Issued By" field-name="Receipt Issued By" data-validation-optional="true" data-validation="validate_length length1-100"> 
							</div>
							<div class="col-md-4 mb10">
								<label>Payment Mode</label>
								<select id="paymentMode${entryCount.count}" name="paymentMode${entryCount.count}" class="form-control" field-name="Payment Mode" data-validation-optional="true">
								         <option value="">Select Mode</option>
								         <c:forEach var="paymentMode" items="${expEntry.paymentModes}">
							                  <option  ${entry.paymentMode.value == paymentMode ? 'selected' : ''} value="${paymentMode}">${paymentMode}</option>
							             </c:forEach>
								</select>
							</div>	
		                  <div id="amountDiv${entryCount.count}" class='col-md-4 mb10 valid_ation'>
			                   <label>Amount<span class='text-danger'>&nbsp;*</span></label>
								  <div class='input-group positionRelative'>
										<div class='input-group-btn tblminwidth-75 select-field dropdown_inr' style='min-width: 65px !important;'>
											 <select class='btn btn-info' id='expenseRateType${entryCount.count}' name='expenseRateType${entryCount.count}' field-name='Rate Type' field-name='Rate Type' data-validation='required'>
												   <c:forEach var="currencyType" items="${expEntry.currencyCodeList}">
									                  <option  ${entry.currencyType == currencyType ? 'selected' : ''} value="${currencyType}">${currencyType}</option>
									              </c:forEach>
												  	<%-- <option value='INR' ${entry.currencyType == 'INR' ? 'selected' : ''}>INR</option>
												  	<option value='AUD' ${entry.currencyType == 'AUD' ? 'selected' : ''}>AUD</option>
												  	<option value='USD' ${entry.currencyType == 'USD' ? 'selected' : ''}>USD</option>
												  	<option value='GBP' ${entry.currencyType == 'GBP' ? 'selected' : ''}>GBP</option>
												  	<option value='SGD' ${entry.currencyType == 'SGD' ? 'selected' : ''}>SGD</option>
												  	<option value='EUR' ${entry.currencyType == 'EUR' ? 'selected' : ''}>EUR</option> --%>
										  </select>
									</div>
								         <input type='text' class='form-control allownumericwithdecimal'  placeholder='Amount'  name='expenseAmount${entryCount.count}' id='expenseAmount${entryCount.count}' value='${entry.amount}' field-name='Amount' data-validation='required validate_float validate_length length1-10' onkeyup="getAmountValue(${entryCount.count});">
									</div>
		                 </div>
						<div class="col-md-4 mb10">
							<label>Description<span class='text-danger'>&nbsp;*</span></label>
							<textarea rows="2"  id="entryDes${entryCount.count}" name="entryDes${entryCount.count}" class="form-control" placeholder="Description" field-name="Description" data-validation="required validate_Space validate_length length1-5000">${entry.description}</textarea>
						</div>		
		                   <div id='attachmentSection' class='col-md-4 mb10'>
		                   <div class="uploadFile timesheet_week_comm">
		                     <input type='hidden' name='dbAttachmentMaxRowNumber${entryCount.count}'  id='dbAttachmentMaxRowNumber${entryCount.count}' value='${entry.expensesEntryDocuments.size()}' />
			                 <input type='hidden' name='attcahmentMaxRowNumber${entryCount.count}'  id='attcahmentMaxRowNumber${entryCount.count}' value='1' />
			                 <div id='timesheetDocumentsId' class=''>
			                       <c:forEach var="entryDoc" items="${entry.expensesEntryDocuments}" varStatus="entryDocCount">
			                         <strong class='proDown' id='${entryDocCount.index}document${entryCount.count}'>
			                          <input type='hidden' name='${entryDocCount.index}DbdocFileId${entryCount.count}' id='${entryDocCount.index}DbdocFileId${entryCount.count}' value='${entryDoc.id}'/>
			                          <input type='hidden' name='${entryDocCount.index}DbdocFileIsDeleted${entryCount.count}' id='${entryDocCount.index}DbdocFileIsDeleted${entryCount.count}' value='N'/>
			                             <a class='downicon' href='<c:url value="/downloadExpensesEntryDocument?entryDocumentId=${entryDoc.id}"/>'>
			                                 <i class='glyphicon glyphicon-save'></i>
											 <input type='button' value='${entryDoc.fileName}'/>
								        </a>
								        <input class='dele' type='button' onclick="removeDbAttachment(${entryDocCount.index},${entryCount.count})" />
			                         </strong>
			                       </c:forEach>
			                 </div> 
			                 <i class='fa fa-info-circle' title='Allowed File Formats: ppt,pptx,pps,ppsx,doc,docx,pdf,rtf,odt,xls,xlsx,csv,  zip,txt,jpg,jpeg,bmp,png,gif,eps,tiff,TGA,psd,svg,msg,wpd,wps      Allowed Size : 2MB '></i>
			                 <span> 
			                     <table class='timesheetAttachment' id='attachmentTable${entryCount.count}' cellpadding='0' cellspacing='0' width="100%">
			                       <tr id='${entryCount.count}divAttachmentTsRow0'>
			                           <td><div id='0docFileuploadFileGroup${entryCount.count}'><input type='file' name='0docFile${entryCount.count}' id='0docFile${entryCount.count}' field-name='Attachment' onchange='validateUploadFile(0,${entryCount.count})' class='timesheetAttachmentValidation'><i class='fa fa-plus' onClick='addAttachment(${entryCount.count},"EDIT")'></i></div></td> 
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
							 <div class="clearfix"></div>
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
			  <input type="button" value="Save" id ="draftForApprovalExpensesId" class="btn btn-sm btn-primary" onclick="saveAddExpensesData('draft');">
			  <input type="button" id ="submitForApprovalExpensesId"value="Submit for Approval" class="btn btn-sm btn-success ml10" onclick="saveAddExpensesData('submitted');">
			 <input type="button" value="Cancel" id ="cancelForApprovalExpensesId"  class="btn btn-sm btn-default ml10" onclick="location.href='<c:url value='/addExpense?expenseId=${expenses.expenseId}&type=view'/>' "  >
			</div>
		  </div>
		</div>
                
          
      <!-- Content Area Ends--> 
	  </div>
</form:form>
  </section>
</c:if>
<script>
	function saveAddExpensesData(status) {
		if ($('#add_expenses_form').validate(false, validationSettings)){
			$("#submitForApprovalExpensesId").prop('disabled', true);
			$("#draftForApprovalExpensesId").prop('disabled', true);
			$("#cancelForApprovalExpensesId").prop('disabled', true);
			
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
				var frm = $('#add_expenses_form').serializeFormJSON();
				var jsonData = JSON.stringify(frm);
				var form = $('#add_expenses_form')[0];
	      		var formData = new FormData(form); 
	      		formData.append("json",jsonData);
				var sourceleadUrl = '${sourceleadUrl}';
				$( "#submitForApprovalExpensesId" ).addClass('button').val('Processing..');
	     		$.ajax({
	       	        type : "POST",
	        	    data: formData,
	        	    contentType : 'application/json; charset=utf-8',
	        	    enctype: 'multipart/form-data',
	    	        processData: false,
	    	        contentType:false,
	        	    dataType : 'json',
					url : sourceleadUrl+"/storeAddExpensesData?${_csrf.parameterName}=${_csrf.token}&status="+status,
					success : function(data) {
						$( "#submitForApprovalExpensesId" ).addClass('button').val('Submit for Approval');
						window.location.replace(sourceleadUrl+'/expenses');
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


<script>
function getClientBasedOnSelectedProject(){
	var sourceleadUrl ='${sourceleadUrl}';
	var projectMemberId = document.getElementById("projectMemberId").value;
	$.ajax({
	      type : "GET",
	       url: sourceleadUrl+"/getClientByProjectMemberId?projectMemberId="+projectMemberId,       
	       success :function(data) {
	    	   if(data != null && data !='undefined' && data !=''){
	    	   var json = JSON.parse(data);
		    	  if(json.expensesClientName != null && json.expensesClientName !='undefined' && json.expensesClientName !=''){
		    	   		$('#clientNameId').val(json.expensesClientName);
		    	   }else{
		    	   		$('#clientNameId').val('');   
		    	   }
		    	  if(json.startDateStr != null && json.startDateStr !='undefined' && json.startDateStr !='' && json.endDateStr != null && json.endDateStr !='undefined' && json.endDateStr !=''){
		    		  	var projectStartDate = json.startDateStr;
		    			var projectEndDate = json.endDateStr;
		    			expensesDatePickerStartDate(projectStartDate,projectEndDate);
		    	  }else{
					  var projectStartDate = json.startDateStr;
					  var projectEndDate = json.endDateStr;
					  expensesDatePickerStartDate(projectStartDate,projectEndDate);
		    	  }		
	    	   }
	       },
	});
}
</script>

<script>
	function expensesDatePickerStartDate(projectStartDate, projectEndDate) {
		
		var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormataddExpense= '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormataddExpense = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormataddExpense = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormataddExpense = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormataddExpense = 'yy-dd-mm'; 
		  }
		
		$("#projectStartDateExpensesId").datepicker("destroy");
		$("#projectEndDateExpensesId").datepicker("destroy");
		$('#projectStartDateExpensesId').datepicker({
					dateFormat : finalDateFormataddExpense,
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
					dateFormat : finalDateFormataddExpense,
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
function removeErrStr(){
	$('#dateDiv').find('p.jquery_form_error_message').remove(); 
}
function removeErrEnd(){
	$('#errEnd').find('p.jquery_form_error_message').remove(); 
}
function removeErrProject(){
	$('#errProject').find('p.jquery_form_error_message').remove(); 
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
function withdrawMyExpense(id){
	var sourceleadUrl ='${sourceleadUrl}';
	$("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
	      type : "POST",
   	       url: sourceleadUrl+"/withDrawExpense?${_csrf.parameterName}=${_csrf.token}&expenseId="+'${expenses.id}',		
	      success :function(result) {
	    	  location.replace(sourceleadUrl+"/addExpense?expenseId="+'${expenses.expenseId}'+"&type=view"); 
	      }
 }); 
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

<script>
$(".allownumericwithdecimal").on("keypress keyup blur",function (event) {
    $(this).val($(this).val().replace(/[^0-9\.]/g,''));
        if ((event.which != 46 || $(this).val().indexOf('.') != -1) && (event.which < 48 || event.which > 57)) {
            event.preventDefault();
        }
    });	
</script>

<jsp:include page="../masterFooter.jsp" />
