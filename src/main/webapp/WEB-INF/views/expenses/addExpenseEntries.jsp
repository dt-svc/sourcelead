<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

 <script>
	function expensesDatePickerStartDate(projectStartDate, projectEndDate) {
		var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	    var finalDateFormataddExpense= getJSDateFormat(locationDate);
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
						var maxRecCount = $('#maxRecCount').val();
						if(maxRecCount != null && maxRecCount != '' && maxRecCount != 'undefined'){
							var expenseEndDate = $("#projectEndDateExpensesId").val();
							 for(var i = 2; i < maxRecCount; i=i+2){
								 if($("#divEntityTsRow"+i).is(':visible'))
								 {
									    var categoryId = $("#categoryDate"+(i-1)).attr("id");
									    if(categoryId != null && categoryId != '' && categoryId != 'undefined'){
								    	    $("#categoryDate"+(i-1)).datepicker("option","minDate", selected);
										    if(expenseEndDate != null && expenseEndDate != '' && expenseEndDate != 'undefined'){
										    	$("#categoryDate"+(i-1)).datepicker("option","maxDate", expenseEndDate);
										    }
									    }
							    	  
								 }
								else{
									 var categoryDate = $("#categoryDate"+(i-1)).val();
								     if(categoryDate != null && categoryDate != '' && categoryDate != 'undefined'){
								    	 var d1= getJSDate(selected,locationDate);
										 var d2= getJSDate(expenseEndDate,locationDate);
										 var d3= getJSDate(categoryDate,locationDate);
										 if(!(d1<=d3 && d3<=d2)){
										    	$("#categoryDateDiv"+(i-1)).addClass("ColRed");
										 }
										 else{
											 $("#categoryDateDiv"+(i-1)).removeClass("ColRed");
										 }
								    	
								     }
								 } 
							   
							}  
						}	    
							 
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
						var maxRecCount = $('#maxRecCount').val();
						if(maxRecCount != null && maxRecCount != '' && maxRecCount != 'undefined'){
							var expenseStartDate = $("#projectStartDateExpensesId").val();
							 for(var i = 2; i < maxRecCount; i=i+2){
								 if($("#divEntityTsRow"+i).is(':visible'))
								   {
									    var categoryId = $("#categoryDate"+(i-1)).attr("id");
									    if(categoryId != null && categoryId != '' && categoryId != 'undefined'){
									    	  $("#categoryDate"+(i-1)).datepicker("option","maxDate", selected);
										    	if(expenseStartDate != null && expenseStartDate != '' && expenseStartDate != 'undefined'){
											    	$("#categoryDate"+(i-1)).datepicker("option","minDate", expenseStartDate);
											    }
									    }
								   
								  }
								 else{
									 var categoryDate = $("#categoryDate"+(i-1)).val();
								     if(categoryDate != null && categoryDate != '' && categoryDate != 'undefined'){
								    	 var d1= getJSDate(expenseStartDate,locationDate);
										 var d2= getJSDate(selected,locationDate);
										 var d3= getJSDate(categoryDate,locationDate);
										 if(!(d1<=d3 && d3<=d2)){
										    	$("#categoryDateDiv"+(i-1)).addClass("ColRed");
										 }else{
											 $("#categoryDateDiv"+(i-1)).removeClass("ColRed");
										 }
								    	
								     }
								 }
							}  
						}	    
							 
					}
				});
	}
</script>

<script>
function hideAndShowDetailsRow(rowId,type){
	$(".fa-plus-square-o").toggleClass("closExp");
	var entityId = parseInt(rowId-1);
	var previousCategory = $("#categoryTypeDivInput"+entityId).val();
	var previousCategoryDate = $("#categoryDateDivInput"+entityId).val();
	var previousReceiptIssuedBy = $("#receiptIssuedByDivInput"+entityId).val();
	var previousPaymentmode = $("#paymentModeDivInput"+entityId).val();
	var previousRateType = $("#expenseRateTypeDivInput"+entityId).val();
	var previousAmount = $("#expenseAmountDivInput"+entityId).val();
	var previousDesc = $("#entryDesDivInput"+entityId).val();
	var previousPaidBy = $("#paidByDivInput"+entityId).val();
	var categoryType = $("#categoryType"+entityId).val();
	      if(previousCategory != null && previousCategory != '' && previousCategory != 'undefined' && categoryType != previousCategory){
		    $("#categoryType"+entityId).val(previousCategory).trigger("chosen:updated");
	}  
	var categoryDate = $("#categoryDate"+entityId).val();
           if(previousCategoryDate != null && previousCategoryDate != '' && previousCategoryDate != 'undefined' && categoryDate !=previousCategoryDate ){
    	    $("#categoryDate"+entityId).val(previousCategoryDate);
	}
	var receiptIssuedBy = $("#receiptIssuedBy"+entityId).val();
    if(previousReceiptIssuedBy != null && previousReceiptIssuedBy != '' && previousReceiptIssuedBy != 'undefined' && receiptIssuedBy != previousReceiptIssuedBy){
    	$("#receiptIssuedBy"+entityId).val(previousReceiptIssuedBy);
	}else if(previousReceiptIssuedBy == null || previousReceiptIssuedBy == '' || previousReceiptIssuedBy == 'undefined' ){
		$("#receiptIssuedBy"+entityId).val('');
	}
	var paymentMode = $("#paymentMode"+entityId).val();
    if(previousPaymentmode != null && previousPaymentmode != '' && previousPaymentmode != 'undefined' && paymentMode != previousPaymentmode){
    	$("#paymentMode"+entityId).val(previousPaymentmode);
	}
	var expenseRateType = $("#expenseRateType"+entityId).val();
    if(previousRateType != null && previousRateType != '' && previousRateType != 'undefined' &&  expenseRateType != previousRateType){
    	$("#expenseRateType"+entityId).val(previousRateType);
	}
	var expenseAmount = parseFloat($("#expenseAmount"+entityId).val());
    if(previousAmount != null && previousAmount != '' && previousAmount != 'undefined' && expenseAmount != previousAmount ){
    	$("#expenseAmount"+entityId).val(previousAmount);
	}

	var descriptions = $("#entryDes"+entityId).val();
    if(previousDesc != null && previousDesc != '' && previousDesc != 'undefined' &&   descriptions != previousDesc){
    	 $("#entryDes"+entityId).val(previousDesc);
	}
	
	var paidBy = $("#paidBy"+entityId).val();
    if(previousPaidBy != null && previousPaidBy != '' && previousPaidBy != 'undefined' &&  paidBy !=previousPaidBy){
    	$("#paidBy"+entityId).val(previousPaidBy).trigger("chosen:updated");
	}
	if ($('#add_expenses_form').validate(false, validationSettings)){
		var validation_required = "true";
		 var maxRecCount = $("#maxRecCount").val();
		 if(type != null && type != 'undefined' && type != '' && type == 'Edit'){
			 $("#addExpenseItemButton").removeAttr('href');
			 $("#addExpenseItemButton").removeClass("btn-primary");
			 $("#addExpenseItemButton").addClass("btn-default");
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
			   var from = $("#addExpenseItemButton").attr('data-id');
				if(from == null || from == '' || from == 'undefined'){
					from = "emp";
				}
				$("#addExpenseItemButton").attr('href','javascript:addExpenseRow("'+from+'");');
			    $("#addExpenseItemButton").removeClass("btn-default");
	    	    $("#addExpenseItemButton").addClass("btn-primary");
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
			
			var from = $("#addExpenseItemButton").attr('data-id');
			if(from == null || from == '' || from == 'undefined'){
				from = "emp";
			}
			$("#addExpenseItemButton").attr('href','javascript:addExpenseRow("'+from+'");');
		    $("#addExpenseItemButton").removeClass("btn-default");
    	    $("#addExpenseItemButton").addClass("btn-primary");
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
	    
		var from = $("#addExpenseItemButton").attr('data-id');
		if(from == null || from == '' || from == 'undefined'){
			from = "emp";
		}
		$("#addExpenseItemButton").attr('href','javascript:addExpenseRow("'+from+'");');
	    $("#addExpenseItemButton").removeClass("btn-default");
 	    $("#addExpenseItemButton").addClass("btn-primary");
		
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
<div id="divAddRowTr1Td1" style="display: none; float: left;">
    <input type='hidden' id="categoryDateDivInputREPLACEROWID"/>
    <div id="categoryDateDivREPLACEROWID">--</div>
</div>
<div id="divAddRowTr1Td2" style="display: none; float: left;">
    <input type='hidden' id="categoryTypeDivInputREPLACEROWID"/>
    <div id="categoryTypeDivREPLACEROWID">--</div>
</div>
<div id="divAddRowTr1Td3" style="display: none; float: left;">
    <input type='hidden' id="entryDesDivInputREPLACEROWID"/>
    <input type='hidden' id="receiptIssuedByDivInputREPLACEROWID"/>
   <div id="entryDesDivREPLACEROWID" class="tbldata-dotdot-me">--</div>
</div>
<div id="divAddRowTr1Td4" style="display: none; float: left;">
    <input type='hidden' id="expenseAmountDivInputREPLACEROWID"/>
    <div id="expenseAmountDivREPLACEROWID">--</div>
</div>

<div id="divAddRowTr1Td5" style="display: none; float: left;">
    <input type='hidden' id="expenseRateTypeDivInputREPLACEROWID"/>
    <div id="expenseRateTypeDivREPLACEROWID">--</div>
</div>
<div id="divAddRowTr1Td6" style="display: none; float: left;">
    <input type='hidden' id="paymentModeDivInputREPLACEROWID" />
    <div id="paymentModeDivREPLACEROWID">--</div>
</div>

 <div id="divAddRowTr1Td10" style="display: none; float: left;">
    <input type='hidden' id="expenseBillableTypeDivInputREPLACEROWID"/>
    <div id="expenseBillableTypeDivREPLACEROWID">--</div>
</div> 


<div id="divAddRowTr1Td7" style="display: none; float: left;">
    <input type='hidden' id="paidByDivInputREPLACEROWID" />
    <div id="paidByDivREPLACEROWID">--</div>
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

<div id="divAddRowTr2" style="display: none; float: left;">
         <div class="expDetails exp_details">
			          <div class="col-xs-12 col-sm-12 col-md-12 expAddRo myForm noPad clearfix">            
						<div class="col-md-4 minHeight chosen-custom chooRadius">
						<label>Category Type<span class="text-danger">&nbsp;*</span></label>
							<select id="categoryTypeREPLACEROWID" name="expenseEntry[REPLACENAMEROWID].categoryType" class="form-control" field-name="Category Type" data-validation="required" onchange="getAmountValueCategory(REPLACEROWID);getExpenseAttachmentStatus(REPLACEROWID);">
							   <option value="">Select Category Type</option>				 							   
							</select>
						
						</div>
						<div class="col-md-4 minHeight">
							<label>Date Occurred<span class="text-danger">&nbsp;*</span></label>
							<input type="text" id="categoryDateREPLACEROWID" name="expenseEntry[REPLACENAMEROWID].dateOccurred" readonly="readonly"  class="form-control calendar"  value="" placeholder="Date" field-name="Date" data-validation="required"> 
						</div>
						<div class="col-md-4 minHeight">
							<label>Receipt Issued by</label>
							<input type="text" id="receiptIssuedByREPLACEROWID" name="expenseEntry[REPLACENAMEROWID].receiptIssuedBy" class="form-control" value=""  placeholder="Receipt Issued By" field-name="Receipt Issued By" data-validation-optional="true" data-validation="validate_length length1-100"> 
						</div>

						<div class="col-md-4 minHeight chosen-custom">
							<label>Payment Mode<span class="text-danger">&nbsp;*</span></label>
							<select id="paymentModeREPLACEROWID" name="expenseEntry[REPLACENAMEROWID].paymentMode" class="form-control" field-name="Payment Mode" data-validation="required">
							         <option value="">Select Mode</option>
							          <c:forEach var="paymentMode" items="${expEntry.paymentModes}">
							                   <option ${paymentMode == 'Cash' ? 'selected' : ''} value="${paymentMode}">${paymentMode}</option>
							          </c:forEach>
							</select>
						</div>	
	                  <div id="amountDivREPLACEROWID" class="col-md-4 minHeight valid_ation">
		                   <label>Amount<span class="text-danger">&nbsp;*</span></label>
							  <div class="input-group positionRelative">
									<div class="input-group-btn tblminwidth-75 select-field dropdown_inr" style="min-width: 65px !important;">
											  <select class="btn btn-info" id="expenseRateTypeREPLACEROWID" name="expenseEntry[REPLACENAMEROWID].categoryAmountType" field-name="Rate Type" field-name="Rate Type" data-validation="required">
											  	   <c:forEach var="currencyType" items="${expEntry.currencyCodeList}">
									                 <%--  <option  value="${currencyType}">${currencyType}</option> --%>
									                  <option ${currencyType == expEntry.loginCurrencyCode ? 'selected' : ''} value="${currencyType}">${currencyType}</option>
									               </c:forEach>
											  </select>
								</div>
							         <input type="text" class="form-control allownumericwithdecimal"  placeholder="Amount"  name="expenseEntry[REPLACENAMEROWID].categoryAmount" id="expenseAmountREPLACEROWID" value="" field-name="Amount" data-validation="required validate_float validate_length length1-10" onkeyup="getAmountValue(REPLACEROWID);">
								</div>
	                 </div>
	                 
	                 
	                 <div class="col-md-4 minHeight">
							<label>Paid by<span class="text-danger">&nbsp;*</span></label>
							<select id="paidByREPLACEROWID" name="expenseEntry[REPLACENAMEROWID].paidBy" class="form-control" placeholder="Paid By" field-name="Paid By" data-validation="required">
							         <option value="">Select paid By</option>
							         <c:forEach var="paidByType" items="${expEntry.paidByType}">
							         			<option value="${paidByType}">${paidByType}</option>
									 </c:forEach>
							</select> 
					</div>
	                 
	                 
					<div class="col-md-12 minHeight mb">
						<label>Description<span class='text-danger'>&nbsp;*</span></label>
						<textarea rows="2"  id="entryDesREPLACEROWID" name="expenseEntry[REPLACENAMEROWID].entryDescription" class="form-control" placeholder="Description" field-name="Description" data-validation="required" onblur="removeWhiteSpacesAdd(REPLACEROWID);"></textarea>
					</div>		
	                   <div id='attachmentSection' class='col-md-4 minHeight'>
	                   <label>Attachments<span class="text-danger">&nbsp;*</span></label>
	                   <i class='fa fa-info-circle' title='Allowed File Formats: ppt,pptx,pps,ppsx,doc,docx,pdf,rtf,odt,xls,xlsx,csv,  zip,txt,jpg,jpeg,bmp,png,gif,eps,tiff,TGA,psd,svg,msg,wpd,wps      Allowed Size : 2MB '></i>
	                   <div class="uploadFile timesheet_week_comm width100percnt upload_file">
		                 <input type='hidden' name='attcahmentMaxRowNumberREPLACEROWID'  id='attcahmentMaxRowNumberREPLACEROWID' value='1' />
		                 <span> 
		                     <table class='timesheetAttachment' id='attachmentTableREPLACEROWID' cellpadding='0' cellspacing='0' width="100%">
		                       <tr id='REPLACEROWIDdivAttachmentTsRow0'>
		                           <td><div id='0docFileuploadFileGroupREPLACEROWID'><input type='file' name='0docFileREPLACEROWID' id='0docFileREPLACEROWID' field-name='Attachment' onchange='validateUploadFile(0,REPLACEROWID)' data-validation='required' class='timesheetAttachmentValidation'><i class='fa fa-plus' onClick='addAttachment(REPLACEROWID,"NEW")'></i></div></td> 
		                       </tr>
		                   </table>
		                 </span>
		             </div>
		             </div>   						  						  
					</div>
					      <div class="col-xs-12 col-sm-12 col-md-12 expAddRo myForm noPad clearfix">
						      <div class="pull-right  mr mt10">
						        <input type="button" value="Add" id="addExpenseCategoryButtonREPLACEROWID" class="btn btn-primary btn-sm mr10" onclick="addExpenseCategory(REPLACEROWID);calculateTotalamount(REPLACEROWID);">
						        <input type="button" id="cancelExpenseCategoryButtonREPLACEROWID" value="Cancel" class="btn btn-default btn-sm" onclick="calculateTotalamount(REPLACEROWID);removeExpenseRow(REPLACEROWID,REPLACEDOWNROWID);">
						      </div>
					      </div>
		 
		 <div class="clearfix" style="position: static;"></div>

		   </div>
</div>


<script type="text/javascript">
function removeWhiteSpacesAdd(rowCount){
	var el = $("#entryDes"+rowCount).val();
	var val = el.replace(/(^\s*)|(\s*$)|((\r?\n|\r)+\s)/gi,"");
	$("#entryDes"+rowCount).val(val);
	}
</script>
<script>
function addExpenseRow(from){ 
	if ($('#add_expenses_form').validate(false, validationSettings)){
		   var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		   var finalDateExpenseEntrie= getJSDateFormat(locationDate);
		   var maxRecCount = $("#maxRecCount").val();
		   for(var i = 2; i < maxRecCount; i=i+2){
			   if($("#divEntityTsRow"+i).is(':visible'))
			   {
					$("#divEntityTsRow"+i).removeAttr('style');
					$("#divEntityTsRow"+i).attr('style','display:none');
			   }
		   }
		 
			var tbl = document.getElementById("categoryTable");
			var maxRowNum = parseInt($("input[name = 'maxRecCount']").val());
			$("#maxRecCount").val(parseInt(maxRowNum + 2));
			
			
			var row = tbl.insertRow(maxRowNum);
			row.setAttribute("id", "divEntityTsRow" + maxRowNum);
			
			var cellCount = 0;
			
			var cell0 = row.insertCell(parseInt(cellCount));
			var divRowTd1Str = document.getElementById("divAddRowTr1Td1").innerHTML;
			divRowTd1Str = divRowTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
			cell0.innerHTML = divRowTd1Str;
			
			cellCount = parseInt(cellCount)+1;
			
			var cell1 = row.insertCell(parseInt(cellCount));
			var divRowTd2Str = document.getElementById("divAddRowTr1Td2").innerHTML;
			divRowTd2Str = divRowTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
			cell1.innerHTML = divRowTd2Str;
			cellCount = parseInt(cellCount)+1;
			
			
			var cell2 = row.insertCell(parseInt(cellCount));
			var divRowTd3Str = document.getElementById("divAddRowTr1Td3").innerHTML;
			divRowTd3Str = divRowTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
			cell2.innerHTML = divRowTd3Str;
			cellCount = parseInt(cellCount)+1;
			
			
			var cell3 = row.insertCell(parseInt(cellCount));
			var divRowTd4Str = document.getElementById("divAddRowTr1Td4").innerHTML;
			divRowTd4Str = divRowTd4Str.replace(/REPLACEROWID/g, (maxRowNum));
			cell3.innerHTML = divRowTd4Str;
			cellCount = parseInt(cellCount)+1;
			

			if(from != null && from != 'undefined' && from != '' && from == 'approver'){
				var cell9 = row.insertCell(parseInt(cellCount));
				var divRowTd9Str = document.getElementById("divAddRowTr1Td9").innerHTML;
				divRowTd9Str = divRowTd9Str.replace(/REPLACEROWID/g, (maxRowNum));
				cell9.innerHTML = divRowTd9Str;
				cellCount = parseInt(cellCount)+1;
			}
			
			var cell4 = row.insertCell(parseInt(cellCount));
			var divAttachmentTdStr = document.getElementById("divAddRowTr1Td5").innerHTML;
			divAttachmentTdStr = divAttachmentTdStr.replace(/REPLACEROWID/g, (maxRowNum));
			cell4.innerHTML = divAttachmentTdStr;
			cellCount = parseInt(cellCount)+1;
			
			var cell5 = row.insertCell(parseInt(cellCount));
			var divRowTd5Str = document.getElementById("divAddRowTr1Td6").innerHTML;
			divRowTd5Str = divRowTd5Str.replace(/REPLACEROWID/g, (maxRowNum));
			cell5.innerHTML = divRowTd5Str;
			cellCount = parseInt(cellCount)+1;

			if(from != null && from != 'undefined' && from != '' && from == 'approver'){
				var cell10 = row.insertCell(parseInt(cellCount));
				var divRowTd10Str = document.getElementById("divAddRowTr1Td10").innerHTML;
				divRowTd10Str = divRowTd10Str.replace(/REPLACEROWID/g, (maxRowNum));
				cell10.innerHTML = divRowTd10Str;
				cellCount = parseInt(cellCount)+1;
			}
			
			var cell6 = row.insertCell(parseInt(cellCount));
			var divRowTd6Str = document.getElementById("divAddRowTr1Td7").innerHTML;
			divRowTd6Str = divRowTd6Str.replace(/REPLACEROWID/g, (maxRowNum));
			cell6.innerHTML = divRowTd6Str;
			cellCount = parseInt(cellCount)+1;
			
			
			
			var cell7 = row.insertCell(parseInt(cellCount));
			var divRowTd7Str = document.getElementById("divAddRowTr1Td8").innerHTML;
			divRowTd7Str = divRowTd7Str.replace(/REPLACEROWID/g, (maxRowNum));
			divRowTd7Str = divRowTd7Str.replace(/REPLACEDOWNROWID/g, (parseInt(maxRowNum + 1)));
			cell7.innerHTML = divRowTd7Str;
			
			
			var detailsRow = parseInt(maxRowNum+1);
		    var row1 = tbl.insertRow(detailsRow);
		    row1.setAttribute("id", "divEntityTsRow" + detailsRow);
			
			
			var cell2_0 = row1.insertCell(0);
			var divRowTd2_0Str = document.getElementById("divAddRowTr2").innerHTML;
			divRowTd2_0Str = divRowTd2_0Str.replace(/REPLACEROWID/g, (maxRowNum));
			divRowTd2_0Str = divRowTd2_0Str.replace(/REPLACEDOWNROWID/g, (maxRowNum+1));
			divRowTd2_0Str = divRowTd2_0Str.replace(/REPLACENAMEROWID/g, ((maxRowNum+1)/2-1));
			cell2_0.innerHTML = divRowTd2_0Str;
			cell2_0.colSpan = 8;
			
			$("#categoryDate"+maxRowNum).datepicker("destroy");
			var projectStartDate = $('#projectStartDateExpensesId').val();
			var projectEndDate =   $('#projectEndDateExpensesId').val();
			if((projectStartDate != null && projectStartDate != '' && projectStartDate != 'undefined') || 
				(projectEndDate != null && projectEndDate != '' && projectEndDate != 'undefined')){
					$('#categoryDate'+maxRowNum).datepicker({
						dateFormat : finalDateExpenseEntrie,
						changeMonth : true,
						changeYear : true,
						minDate : projectStartDate,
						maxDate : projectEndDate,
			       }); 
			}
			else {
				alert("Please Select StartDate & EndDate")
			} 
			
		    var config = {
		    	    '.chosen-select'           : {},
		    	    '.chosen-select-deselect'  : {allow_single_deselect:true},
		    	    '.chosen-select-no-single' : {disable_search_threshold:10},
		    	    '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
		    	    '.chosen-select-width'     : {width:"95%"}
		    	    }
		    	    for (var selector in config) {
		    	    $(selector).chosen(config[selector]);
		    	    }
		    	    $('.chosen-select').each(function() {
		    	    var $this = $(this);
		    	    $this.next().css({'width': '100%'});
		    	    })
		    	  
		    	    
		    	    var output = '';
		    	    if(from == 'approver'){
		    	    	var projectId = $('#projectId').val();
			    	    output = output+'<option value="">Select Category Type</option>';
			    	    <c:forEach var="project" items="${expEntry.projectsList}">
			   		       if('${project.id}' == projectId){
					 	    	 <c:forEach var="expCat" items="${project.expenseCategoryList}">
					 	    	    output = output+'<option value="${expCat.id},${expCat.allowedAmount}">${expCat.categoryName}</option>';
					 	    	 </c:forEach>
							 }  
			            </c:forEach>
		    	    }
		    	    else{
		    	    	var projectMemberId = $('#projectMemberId').val();
			    	    output = output+'<option value="">Select Category Type</option>';
				 	    <c:forEach var="pm" items="${expEntry.projectMembersList}">
					 	     if('${pm.id}' == projectMemberId){
					 	    	 <c:forEach var="expCat" items="${pm.expenseCategoryList}">
					 	    	    output = output+'<option value="${expCat.id},${expCat.allowedAmount}">${expCat.categoryName}</option>';
					 	    	 </c:forEach>
							 }  
			            </c:forEach>
		    	    }
		    	    
		    	    
		    	    $("#addExpenseItemButton").removeAttr('href');
		    	    $("#addExpenseItemButton").removeClass("btn-primary");
		    	    $("#addExpenseItemButton").addClass("btn-default");
		        	$("#categoryType"+maxRowNum).empty();
		            $("#categoryType"+maxRowNum).append(output);
		            $('#categoryType'+maxRowNum).chosen();
		    	    $('#categoryType'+maxRowNum).trigger("chosen:updated");
		     
					//categoryTypeREPLACEROWID
	   }
	else{
		var maxRecCount = $("#maxRecCount").val();
		if(maxRecCount >1){
			   var rowId = parseInt(maxRecCount-1);
			   if($("#divEntityTsRow"+rowId).is(':hidden'))
			   {
					$("#divEntityTsRow"+rowId).removeAttr('style');
			   }
			
		}
	}
	 /* $(".allownumericwithdecimal").on("keypress keyup blur",function (event) {
		    $(this).val($(this).val().replace(/[^0-9\.]/g,''));
		        if ((event.which != 46 || $(this).val().indexOf('.') != -1) && (event.which < 48 || event.which > 57)) {
		            event.preventDefault();
		        }
		    });	 */
	 
		    $('.allownumericwithdecimal').keydown(function (event) {
	            if (event.shiftKey == true) {
	                event.preventDefault();
	            }
	            if ((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 96 && event.keyCode <= 105) || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 37 || event.keyCode == 39 || event.keyCode == 46 || event.keyCode == 190) {
	            } else {
	                event.preventDefault();
	            }
	            if($(this).val().indexOf('.') !== -1 && event.keyCode == 190)
	                event.preventDefault();
	        });

  }			
</script>
<script>
	function addExpenseCategory(entityId) {
		var dbAttachmentMaxRowNumber = $("#dbAttachmentMaxRowNumber"+entityId).val();
		if(dbAttachmentMaxRowNumber != null && dbAttachmentMaxRowNumber != '' && dbAttachmentMaxRowNumber != 'undefined'){
			for(var i = 0; i < dbAttachmentMaxRowNumber; i++){
			   if($("#"+i+"document"+entityId).is(':hidden'))
			   {
				   $("#"+i+"DbdocFileIsDeleted"+entityId).val('Y');
				   
			   }
			}
		}
		
		/* $("#"+documentId+"DbdocFileIsDeleted"+entityId).val('Y'); */
		if ($('#add_expenses_form').validate(false, validationSettings)){
			
			var categoryType = $("#categoryType"+entityId).val();
			var categoryTypeName = $("#categoryType"+entityId+" option[value='"+categoryType+"']").text();
			
			 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
			 var projectStartDate = $("#projectStartDateExpensesId").val();
			 var projectEndDate = $("#projectEndDateExpensesId").val();
			 var d1= getJSDate(projectStartDate,locationDate);
			 var d2= getJSDate(projectEndDate,locationDate);
			
			var categoryDate = $("#categoryDate"+entityId).val();
			var receiptIssuedBy = $("#receiptIssuedBy"+entityId).val();
			var paymentMode = $("#paymentMode"+entityId).val();
			var expenseRateType = $("#expenseRateType"+entityId).val();
			var expenseAmount = parseFloat($("#expenseAmount"+entityId).val());
			
			var expenseRoundOffAmount = expenseAmount.toFixed(2);
			var descriptions = $("#entryDes"+entityId).val();
			var paidBy = $("#paidBy"+entityId).val();
			
			var billableType = $("#billableType"+entityId).val();
			
			
			$("#categoryTypeDiv"+entityId).html(categoryTypeName);
			$("#categoryDateDiv"+entityId).html(categoryDate);
			$("#receiptIssuedByDiv"+entityId).html(receiptIssuedBy);
			$("#paymentModeDiv"+entityId).html(paymentMode);
			$("#expenseRateTypeDiv"+entityId).html(expenseRateType);
			$("#expenseAmountDiv"+entityId).html(expenseRoundOffAmount);
			$("#expenseApprovedAmountDiv"+entityId).html(expenseRoundOffAmount);
			
			$("#entryDesDiv"+entityId).html(descriptions);
			$("#entryDesDiv"+entityId).prop("title", descriptions);
			$("#paidByDiv"+entityId).html(paidBy);
			
			
			
			$("#categoryTypeDivInput"+entityId).val(categoryType);
			$("#categoryDateDivInput"+entityId).val(categoryDate);
			$("#receiptIssuedByDivInput"+entityId).val(receiptIssuedBy);
			$("#paymentModeDivInput"+entityId).val(paymentMode);
			$("#expenseRateTypeDivInput"+entityId).val(expenseRateType);
			$("#expenseAmountDivInput"+entityId).val(expenseAmount);
			$("#expenseApprovedAmountDivInput"+entityId).val(expenseAmount);
			$("#entryDesDivInput"+entityId).val(descriptions);
			$("#paidByDivInput"+entityId).val(paidBy);
			
			if(categoryDate != null && categoryDate != '' && categoryDate != 'undefined'){
				 var d3= getJSDate(categoryDate,locationDate);
			    if(!(d1<=d3 && d3<=d2)){
			    	$("#categoryDateDiv"+entityId).addClass("ColRed");
			    }
	        }
			
			$("#hideAndShowDetailsButton"+entityId).removeAttr('style');
			var hideAndShowDetailsHtml =  $("#hideAndShowDetailsButton"+entityId).html();
			$("#hideAndShowDetailsButton"+entityId).remove();
			$("#hideAndShowDetailsDiv"+entityId).prepend(hideAndShowDetailsHtml);
			
			
			$("#hideAndShowDeleteButton"+entityId).removeAttr('style');
			var hideAndShowDeleteHtml =  $("#hideAndShowDeleteButton"+entityId).html();
			$("#hideAndShowDeleteButton"+entityId).remove();
			$("#hideAndShowDetailsDiv"+entityId).prepend(hideAndShowDeleteHtml);
			
			
			
			$("#addExpenseCategoryButton"+entityId).removeAttr('value');
			$("#addExpenseCategoryButton"+entityId).attr('value','Update');
			
			$("#cancelExpenseCategoryButton"+entityId).removeAttr('onclick');
			$("#cancelExpenseCategoryButton"+entityId).attr('onclick','hideAndShowDetailsRow('+parseInt(entityId+1)+',"Edit")');
			
			
			$("#divEntityTsRow"+parseInt(entityId+1)).removeAttr('style');
			$("#divEntityTsRow"+parseInt(entityId+1)).attr('style','display:none;');
			
			var from = $("#addExpenseItemButton").attr('data-id');
    		if(from == null || from == '' || from == 'undefined'){
				from = "emp";
			}
			$("#addExpenseItemButton").attr('href','javascript:addExpenseRow("'+from+'");');
			$("#addExpenseItemButton").removeClass("btn-default");
	        $("#addExpenseItemButton").addClass("btn-primary");
	        if(billableType != null && billableType != '' && billableType != 'undefined'){
				if(billableType == 1){
					billableType = 'Billable';
				}else{
					billableType = 'Non-Billable';
				}
				$("#billableTypeDiv"+entityId).html(billableType);
				$("#billableTypeDivInput"+entityId).val(billableType);
			}
			return true;
		}else{
			return false;
		}
	}
</script>
 <script>
function displayCalendar(entityId){
if('${type}' !='view'){
	$("#categoryDate"+entityId).datepicker("destroy");
	var projectStartDate = $('#projectStartDateExpensesId').val();
	var projectEndDate =   $('#projectEndDateExpensesId').val();
	 var locationDateFormat ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	 var jsDateFormat= getJSDateFormat(locationDateFormat);
	if((projectStartDate != null && projectStartDate != '' && projectStartDate != 'undefined') || 
		(projectEndDate != null && projectEndDate != '' && projectEndDate != 'undefined')){
			$('#categoryDate'+entityId).datepicker({
				dateFormat : jsDateFormat,
				changeMonth : true,
				changeYear : true,
				minDate : projectStartDate,
				maxDate : projectEndDate,
	       }); 
	}
	else {
		alert("Please Select StartDate & EndDate")
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
 function validateUploadFile(id, entityId)
	{
		  var selectedFile = $('#'+id+'docFile'+entityId).val();
		  if(validate_fileupload(selectedFile)){
			      var docSize = $('#'+id+'docFile'+entityId)[0].files[0].size;
		           $("#"+id+"docFileuploadFileGroup"+entityId).find('p.jquery_form_error_message').remove();
		    	  $('input#'+id+'docFile'+entityId).removeAttr( "record-exist record-exist-errormsg");
	   		      $('#'+id+'docFileuploadFileGroup'+entityId).find('p.jquery_form_error_message').remove(); 
	   		      fileUpload(""+id+"docFile"+entityId+"", "expsesDocuments", "add_expenses_form","expenseEntry["+parseInt(((entityId+1)/2)-1)+"].fileInfo","","");
		  }
		  else{
			      $('#'+id+'docFileuploadFileGroup'+entityId).find('p.jquery_form_error_message').remove();
			      $('#'+id+'docFileuploadFileGroup'+entityId).find('i').after("<p class='jquery_form_error_message'>Invalid Format</span>");
				  $('#'+id+'docFile'+entityId).attr('record-exist','yes');
				  $('#'+id+'docFile'+entityId).attr('record-exist-errorMsg','Invalid Format'); 
		  }
		
		 
	 }
</script>

<script>
function calculateTotalamount(rowId){
	var maxRowNum = parseInt($("input[name = 'maxRecCount']").val());
    var sum = 0;
 	    for(var i=1; i< maxRowNum; i++){
 	    	 if(document.getElementById("expenseAmount"+i)){
 	    		 if(document.getElementById("expenseAmount"+i).value){
 	    			var rowAmount = $('#expenseAmount'+i).val();
		 	    	sum = parseFloat(sum) + parseFloat(rowAmount);
 	    		 }
 	    		
 	    	 }
 	    }
 	    var toFixed = sum.toFixed(2);
	$("#expenseAmountDivId").val(toFixed);
	
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
function getExpenseAttachmentStatus(rowId){
    $('#0docFileuploadFileGroup'+rowId).find('p.jquery_form_error_message').remove();
	$('#0docFile'+rowId).removeAttr('data-validation');
	var category = $('#categoryType'+rowId).val();
	if(category !=null && category !='' && category!='Undefined'){
		var idArray = category.split("\,")
		if(idArray[0] != null && idArray[0]!='' && idArray[0]!='undefined' && idArray[0] > 0){
			var categoryId = idArray[0];
			$.ajax({
				type : "POST",
				url : sourceleadUrl+"/getExpenseAttachmentStatus?&categoryId="+categoryId,
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
