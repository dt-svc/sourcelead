<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 <jsp:include page="addExpenseEntries.jsp"></jsp:include>

<div class="wraper"> 
<!-- View Content Area Starts-->
<c:if test = "${param.type == 'view'}">
 </c:if>
 <c:if test = "${param.type != 'view'}">
  <section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>${param.type != 'edit' ? 'Add' : 'Edit'} Expense</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; My Expenses &raquo; Add Expense</div>
    </section>
    <form:form id="add_expenses_form" name="add_expenses_form"  enctype='multipart/form-data'>
	<div class="content-area content-new clearfix">
         
          <div class="clearfix form-horizontal inner-content">
            <div class="row">
              <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
              
                <div class="form-group">
                  <label class="col-md-3">Expenses Id<span class="text-danger" >&nbsp;*</span></label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" name="ExpenseAutoId" value="${expenses != null ? expenses.expenseId : ExpensesId}" readonly="readonly">
                    <input type="hidden"  class="form-control" name="expenseId" value="${expenses.id}">
                  </div>
                </div>  
                <div class="form-group">
                  <label class="col-md-3">Purpose</label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" name="expensePurpose" value="${expenses.purpose}">
                  </div>
                </div>                                          
				<div class="form-group">
					<label class="col-md-3">Select Project <span class="text-danger">&nbsp;*</span></label>
					<div class="col-md-9">
						<select class="chosen-select form-control" onchange="getClientBasedOnSelectedProject(); getMembersForSelectedProject();" id="projectId" name="projectId">
							<c:forEach var="projects" items="${projectsList}">
							       <option  ${expenses.projectMembers.projects.id == projects.id ? 'selected' : '' } value="${projects.id}">${projects.projectName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-3">Client<span class="text-danger">&nbsp;*</span></label>
	                  <div class="col-md-9">
	                    <input type="text" class="form-control" name="clientNameExpense" value="" id="clientNameId" readonly="readonly">
	                  </div>
				</div>               
	            <div class="form-group">
	              <label class="col-md-3">Start & End date<span class="text-danger">&nbsp;*</span></label>
	              <div class="col-md-9" >
						<div class="row">
						  <div class="col-lg-6 evDaPa" id="dateDiv">
							  <input type="text" class="form-control calendar" data-validation="required" value="${expenses.startDateStr}" field-name="Start Date" name="startDateExpense" id="projectStartDateExpensesId" placeholder="Start Date" readonly="readonly">
						  </div>
						  <div class="col-lg-6">
							  <input type="text" class="form-control calendar" value="${expenses.endDateStr}" name="endDateExpense"  field-name="End Date" id="projectEndDateExpensesId" placeholder="End Date" readonly="readonly">
						  </div>					  
						</div>
	              </div>
	            </div>
	             <div class="form-group">
				<label class="col-md-3">Select Employee<span class="text-danger">&nbsp;*</span></label>
				<div class="col-md-9">
					<select class="chosen-select form-control" id="projectMemberId" name="projectMemberId"  field-name="Employee" data-validation="required">
					    <c:forEach var="projectMember" items="${projectMembers}">
							       <option  ${expenses.projectMembers.id == projectMember.id ? 'selected' : '' } value="${projectMember.id}">${projectMember.employee.user.firstName+"  "+projectMember.employee.user.lastName}</option>
					   </c:forEach>
					   
					</select>
				</div>
			   </div>
               
		</div>
	</div>
</div>
<div class="expensHead">
	<a href="#" class="btn btn-primary btn-sm AddRow" onclick='addExpenseRow();'><i class="fa fa-plus"></i>&nbsp; Add Row</a>
	<div class="clearfix"></div>
</div>
<div class="project_wrap_table expensTableView">
 <input type="hidden"  name="maxRecCount" id="maxRecCount" value="${2*expenses.expensesEntries.size()+1}">
	<table id="categoryTable">
	    <thead>
			<tr id="divEntityTsRow0">
				<th>Exp Category</th>
				<th>Date</th>
				<th>Payment Mode</th>
				<th>Currency</th>
				<th>Amount</th>
				<th>Description</th>
				<th>Action</th>
			</tr>
		</thead>	
			<tbody>
			     <c:forEach var="entry" items="${expenses.expensesEntries}" varStatus="entryCount">
			        <tr id="divEntityTsRow${(2*(entryCount.count))-1}">
			           <td><div id="categoryTypeDiv${entryCount.count}">${entry.expensesCategoryId.expensesName}</div></td>
			           <td><div id="categoryDateDiv${entryCount.count}">${entry.expenseDateStr}</div></td>
			           <td><div id="paymentModeDiv${entryCount.count}">${entry.paymentMode.value}</div></td>
			           <td><div id="expenseRateType${entryCount.count}">${entry.currencyType}</div></td>
			           <td><div id="expenseAmountDiv${entryCount.count}">${entry.amount}</div></td>
			           <td><div id="entryDesDiv${entryCount.count}" class="tbldata-dotdot_50">${entry.description}</div></td>
			           <td><div id="hideAndShowDetailsDiv${entryCount.count}">
			                <i class="fa fa-pencil-square-o AddRow"  onclick="hideAndShowDetailsRow(${2*(entryCount.count)});"></i>
			                <i class="glyphicon glyphicon-trash" onclick="removeExpenseRow(${(2*(entryCount.count))-1},${2*(entryCount.count)})"></i>
			              </div>
			          </td>
			        </tr>
			        <tr id="divEntityTsRow${2*(entryCount.count)}" style="display:none;">
			          <td colspan="7">
				         <div class="expDetails">
				         <input type="hidden" id="expenseEntryId${entryCount.count}" name="expenseEntryId${entryCount.count}" value='${entry.id}'/>
				          <div class="col-xs-12 col-sm-12 col-md-12 expAddRo myForm noPad clearfix">            
							<div class="col-md-4 mb10">
							<label>Category Type<span class="text-danger">&nbsp;*</span></label>
								<select id="categoryType${entryCount.count}" name="categoryType${entryCount.count}" class="form-control" field-name="Category Type" data-validation="required">
								   <option value="">Select Category Type</option>				 							   
								   <c:forEach var="expCat" items="${expensesCategoryList}">
						                 <option value="${expCat.id}" ${entry.expensesCategoryId.id == expCat.id ? 'selected' : ''}>${expCat.expensesName}</option>
		                	       </c:forEach>
								</select>
							</div>
							<div class="col-md-4 mb10">
								<label>Date<span class="text-danger">&nbsp;*</span></label>
								<input type="text" id="categoryDate${entryCount.count}" name="categoryDate${entryCount.count}" readonly="readonly" onclick="displayCalendar('${entryCount.count}')" class="form-control calendar" value="${entry.expenseDateStr}" placeholder="Date" field-name="Date" data-validation="required"> 
							</div>
							<div class="col-md-4 mb10">
								<label>Receipt Issued by<span class="text-danger">&nbsp;*</span></label>
								<input type="text" id="receiptIssuedBy${entryCount.count}" name="receiptIssuedBy${entryCount.count}" class="form-control" value="${entry.receiptIssuedBy}"  placeholder="Receipt Issued By" field-name="Receipt Issued By" data-validation="required"> 
							</div>
							<div class="col-md-4 mb10">
								<label>Payment Mode<span class="text-danger">&nbsp;*</span></label>
								<select id="paymentMode${entryCount.count}" name="paymentMode${entryCount.count}" class="form-control" field-name="Payment Mode" data-validation="required">
								         <option value="">Select Mode</option>
		                                 <option  value="Credit Card" ${entry.paymentMode.value == 'Credit Card' ? 'selected' : ''}>Credit Card</option>
		                                 <option  value="Debit Card" ${entry.paymentMode.value == 'Debit Card' ? 'selected' : ''}>Debit Card</option>
		                                 <option  value="Cash" ${entry.paymentMode.value == 'Cash' ? 'selected' : ''}>Cash</option>
		                                 <option  value="Cheque" ${entry.paymentMode.value == 'Cheque' ? 'selected' : ''}>Cheque</option>
		                                 <option  value="e-Wallet" ${entry.paymentMode.value == 'e-Wallet' ? 'selected' : ''}>e-Wallet</option>
								</select>
							</div>	
		                  <div class="col-md-4 mb10">
			                   <label>Amount<span class="text-danger">&nbsp;*</span></label>
								  <div class="input-group er ratetype">
										<div class="input-group-btn tblminwidth-75 select-field dropdown_inr" style="min-width: 65px !important;">
												  <select class="btn btn-info" id="expenseRateType${entryCount.count}" name="expenseRateType${entryCount.count}" field-name="Rate Type" field-name="Rate Type" data-validation="required">
												  	<option value="INR" ${entry.currencyType == 'INR' ? 'selected' : ''}>INR</option>
												  	<option value="AUD" ${entry.currencyType == 'AUD' ? 'selected' : ''}>AUD</option>
												  	<option value="USD" ${entry.currencyType == 'USD' ? 'selected' : ''}>USD</option>
												  	<option value="GBP" ${entry.currencyType == 'GBP' ? 'selected' : ''}>GBP</option>
												  	<option value="SGD" ${entry.currencyType == 'SGD' ? 'selected' : ''}>SGD</opton>
												  	<option value="EUR" ${entry.currencyType == 'EUR' ? 'selected' : ''}>EUR</option>
												  </select>
										 </div>
								         <input type="text" class="form-control"  placeholder="Amount"  name="expenseAmount${entryCount.count}" id="expenseAmount${entryCount.count}" value="${entry.amount}" field-name="Amount" data-validation="required validate_float">
									</div>
		                 </div>
						<div class="col-md-4 mb10">
							<label>Description</label>
							<textarea rows="2"  id="entryDes${entryCount.count}" name="entryDes${entryCount.count}" class="form-control" placeholder="Description">${entry.description}</textarea>
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
			                             <a class='downicon' href=''>
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
							        <input type="button" id="cancelExpenseCategoryButton${entryCount.count}" value="Cancel" class="btn btn-default ml" onclick="hideAndShowDetailsRow(${2*(entryCount.count)});">
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
			<!-- <input type="button" value="Save" class="btn btn-sm btn-primary"> -->
			<input type="button" id ="submitForApprovalExpensesId"value="Submit" class="btn btn-sm btn-primary" onclick="saveAddExpensesData('submitted');">
			<input type="button" id ="submitForApprovalExpensesId"value="Approve" class="btn btn-sm btn-success ml10" onclick="saveAddExpensesData('approved');">
			<input type="button" value="Cancel" class="btn btn-sm btn-default ml10">
		</div>
   </div>
  </div>
  </div>
</form:form>
  </section>
</c:if>
</div>
<script>
function getMembersForSelectedProject(){
	var sourceleadUrl ='${sourceleadUrl}';
	var projectId = document.getElementById("projectId").value;
	
	$.ajax({
	      type : "GET",
	       url: sourceleadUrl+"/getMembersForSelectedProject?projectId="+projectId,       
	       success :function(data) {
	    	   if(data != null && data !='undefined' && data !=''){
	    	   var json = JSON.parse(data);
	    	   var result = "";
	    	   result = result + '<option value="">Select Employee</option>';
			       	for (var i=0; i<json.length; i++){
				        var opt = json[i];
				        result = result + '<option  value="'+opt.id+'">'+opt.empName+'</option>';
			       	}
				$("#projectMemberId").empty();
				$("#projectMemberId").append(result);
				$("#projectMemberId").trigger("chosen:updated");
	    	   }
	       },
	});
}
</script>

<script>
	function saveAddExpensesData(status) {
		if ($('#add_expenses_form').validate(false, validationSettings)){
			var frm = $('#add_expenses_form').serializeFormJSON();
			var jsonData = JSON.stringify(frm);
			var form = $('#add_expenses_form')[0];
      		var formData = new FormData(form); 
      		formData.append("json",jsonData);
			var sourceleadUrl = '${sourceleadUrl}';
			$( "#submitForApprovalExpensesId" ).addClass('button').val('Processing..');
			window.location.replace(sourceleadUrl+'/expenses');
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
					$( "#submitForApprovalExpensesId" ).addClass('button').val('Sub for Approval');
				},
			});
			return true;
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
	var projectId = document.getElementById("projectId").value;
	$.ajax({
	      type : "GET",
	       url: sourceleadUrl+"/getClientByProjectId?projectId="+projectId,       
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
		$("#projectStartDateExpensesId").datepicker("destroy");
		$("#projectEndDateExpensesId").datepicker("destroy");
		$('#projectStartDateExpensesId').datepicker({
					dateFormat : 'dd-mm-yy',
					changeMonth : true,
					changeYear : true,
					minDate : projectStartDate,
					maxDate : projectEndDate,
					onSelect : function(selected) {
						$("#projectEndDateExpensesId").datepicker("option","minDate", selected);
					}
				});
		$('#projectEndDateExpensesId').datepicker({
					dateFormat : 'dd-mm-yy',
					changeMonth : true,
					changeYear : true,
					minDate : projectStartDate,
					maxDate : projectEndDate,
					onSelect : function(selected) {
						$("#projectStartDateExpensesId").datepicker("option","maxDate", selected);
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
<jsp:include page="../masterFooter.jsp" />
