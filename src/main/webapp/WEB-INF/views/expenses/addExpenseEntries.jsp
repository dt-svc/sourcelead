<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
function hideAndShowDetailsRow(rowId,type){
	$(".fa-plus-square-o").toggleClass("closExp");
	if ($('#add_expenses_form').validate(false, validationSettings)){
		var validation_required = "true";
		var entityId = parseInt(rowId-1);
		 var maxRecCount = $("#maxRecCount").val();
		 if(type != null && type != 'undefined' && type != '' && type == 'Edit'){
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
		if(validation_required == "true"){
			 $("#0docFile"+entityId).removeAttr('data-validation-optional');
			 $("#0docFile"+entityId).attr('data-validation','required');
		}
		else{
			$("#0docFile"+entityId).removeAttr('data-validation');
			 $("#0docFile"+entityId).attr('data-validation-optional','true');
		}
		
		if($("#divEntityTsRow"+rowId).is(':visible'))
	   {
			$("#divEntityTsRow"+rowId).removeAttr('style');
			$("#divEntityTsRow"+rowId).attr('style','display:none;');
	   }
		else{
			$("#divEntityTsRow"+rowId).removeAttr('style');
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
    <div id="categoryDateDivREPLACEROWID">--</div>
</div>
<div id="divAddRowTr1Td2" style="display: none; float: left;">
    <div id="categoryTypeDivREPLACEROWID">--</div>
</div>
<div id="divAddRowTr1Td3" style="display: none; float: left;">
   <div id="entryDesDivREPLACEROWID" class="tbldata-dotdot-me">--</div>
</div>
<div id="divAddRowTr1Td4" style="display: none; float: left;">
    <div id="expenseAmountDivREPLACEROWID">--</div>
</div>
<div id="divAddRowTr1Td5" style="display: none; float: left;">
    <div id="expenseRateTypeDivREPLACEROWID">--</div>
</div>
<div id="divAddRowTr1Td6" style="display: none; float: left;">
    <div id="paymentModeDivREPLACEROWID">--</div>
</div>

<div id="divAddRowTr1Td7" style="display: none; float: left;">
    <div id="paidByDivREPLACEROWID">--</div>
</div>

<div id="divAddRowTr1Td8" style="display: none; float: left;">
       <div id="hideAndShowDetailsDivREPLACEROWID" class="tbldata-dotdot_50">
			<span id="hideAndShowDetailsButtonREPLACEROWID" style="display:none;"> <i class="fa fa-pencil-square-o AddRow" onclick="hideAndShowDetailsRow(REPLACEDOWNROWID,'Edit');"></i></span>
			<i class="glyphicon glyphicon-trash Cred" onclick="removeExpenseRow(REPLACEROWID,REPLACEDOWNROWID);"></i>
		</div>
</div>
<div id="divAddRowTr2" style="display: none; float: left;">
         <div class="expDetails">
			          <div class="col-xs-12 col-sm-12 col-md-12 expAddRo myForm noPad clearfix">            
						<div class="col-md-4 minHeight chosen-custom">
						<label>Category Type<span class="text-danger">&nbsp;*</span></label>
							<select id="categoryTypeREPLACEROWID" name="expenseEntry[REPLACENAMEROWID].categoryType" class="form-control" field-name="Category Type" data-validation="required" onchange="getAmountValueCategory(REPLACEROWID);">
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
									                  <option  value="${currencyType}">${currencyType}</option>
									               </c:forEach>
											  </select>
								</div>
							         <input type="text" class="form-control allownumericwithdecimal"  placeholder="Amount"  name="expenseEntry[REPLACENAMEROWID].categoryAmount" id="expenseAmountREPLACEROWID" value="" field-name="Amount" data-validation="required validate_float validate_length length1-10" onkeyup="getAmountValue(REPLACEROWID);">
								</div>
	                 </div>
	                 
	                 
	                 <div class="col-md-4 minHeight">
							<label>Paid by<span class="text-danger">&nbsp;*</span></label>
							<select id="paidByREPLACEROWID" name="expenseEntry[REPLACENAMEROWID].paidBy" class="form-control" placeholder="Paid By" field-name="Paid By" data-validation="required">
							         <option value="" disabled>Select paid By</option>
							         <c:forEach var="paidByType" items="${expEntry.paidByType}">
							         			<option ${paidByType == 'Company' ? 'selected' : ''} value="${paidByType}">${paidByType}</option>
									 </c:forEach>
							</select> 
					</div>
	                 
	                 
					<div class="col-md-12 minHeight mb">
						<label>Description<span class='text-danger'>&nbsp;*</span></label>
						<textarea rows="3"  id="entryDesREPLACEROWID" name="expenseEntry[REPLACENAMEROWID].entryDescription" class="form-control" placeholder="Description" field-name="Description" data-validation="required validate_Space validate_length length1-5000" onblur="removeWhiteSpacesAdd(REPLACEROWID);"></textarea>
					</div>		
	                   <div id='attachmentSection' class='col-md-4 minHeight'>
	                   <label>Attachments<span class="text-danger">&nbsp;*</span></label>
	                   <i class='fa fa-info-circle' title='Allowed File Formats: ppt,pptx,pps,ppsx,doc,docx,pdf,rtf,odt,xls,xlsx,csv,  zip,txt,jpg,jpeg,bmp,png,gif,eps,tiff,TGA,psd,svg,msg,wpd,wps      Allowed Size : 2MB '></i>
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
	var el = document.getElementsByName("entryDes"+rowCount)[0];
	var val = el.value.replace(/(^\s*)|(\s*$)/gi,"")
	$("#entryDes"+rowCount).val(val);
}
</script>

<script>
function addExpenseRow(from){ 
	if ($('#add_expenses_form').validate(false, validationSettings)){
		
		var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateExpenseEntrie = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateExpenseEntrie = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateExpenseEntrie = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateExpenseEntrie = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateExpenseEntrie = 'yy-dd-mm'; 
		  }
		
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
			
			var cell0 = row.insertCell(0);
			var divRowTd1Str = document.getElementById("divAddRowTr1Td1").innerHTML;
			divRowTd1Str = divRowTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
			cell0.innerHTML = divRowTd1Str;
			
			
			var cell1 = row.insertCell(1);
			var divRowTd2Str = document.getElementById("divAddRowTr1Td2").innerHTML;
			divRowTd2Str = divRowTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
			cell1.innerHTML = divRowTd2Str;
			
			
			var cell2 = row.insertCell(2);
			var divRowTd3Str = document.getElementById("divAddRowTr1Td3").innerHTML;
			divRowTd3Str = divRowTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
			cell2.innerHTML = divRowTd3Str;
			
			
			var cell3 = row.insertCell(3);
			var divRowTd4Str = document.getElementById("divAddRowTr1Td4").innerHTML;
			divRowTd4Str = divRowTd4Str.replace(/REPLACEROWID/g, (maxRowNum));
			cell3.innerHTML = divRowTd4Str;
			
			var cell4 = row.insertCell(4);
			var divAttachmentTdStr = document.getElementById("divAddRowTr1Td5").innerHTML;
			divAttachmentTdStr = divAttachmentTdStr.replace(/REPLACEROWID/g, (maxRowNum));
			cell4.innerHTML = divAttachmentTdStr;
			
			
			var cell5 = row.insertCell(5);
			var divRowTd5Str = document.getElementById("divAddRowTr1Td6").innerHTML;
			divRowTd5Str = divRowTd5Str.replace(/REPLACEROWID/g, (maxRowNum));
			cell5.innerHTML = divRowTd5Str;
			
			var cell6 = row.insertCell(6);
			var divRowTd6Str = document.getElementById("divAddRowTr1Td7").innerHTML;
			divRowTd6Str = divRowTd6Str.replace(/REPLACEROWID/g, (maxRowNum));
			cell6.innerHTML = divRowTd6Str;
			
			var cell7 = row.insertCell(7);
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
	 $(".allownumericwithdecimal").on("keypress keyup blur",function (event) {
		    $(this).val($(this).val().replace(/[^0-9\.]/g,''));
		        if ((event.which != 46 || $(this).val().indexOf('.') != -1) && (event.which < 48 || event.which > 57)) {
		            event.preventDefault();
		        }
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
			var categoryDate = $("#categoryDate"+entityId).val();
			var receiptIssuedBy = $("#receiptIssuedBy"+entityId).val();
			var paymentMode = $("#paymentMode"+entityId).val();
			var expenseRateType = $("#expenseRateType"+entityId).val();
			var expenseAmount = parseFloat($("#expenseAmount"+entityId).val());
			
			var expenseRoundOffAmount = expenseAmount.toFixed(2);
			var descriptions = $("#entryDes"+entityId).val();
			var paidBy = $("#paidBy"+entityId).val();
			
			$("#categoryTypeDiv"+entityId).html(categoryTypeName);
			$("#categoryDateDiv"+entityId).html(categoryDate);
			$("#receiptIssuedByDiv"+entityId).html(receiptIssuedBy);
			$("#paymentModeDiv"+entityId).html(paymentMode);
			$("#expenseRateTypeDiv"+entityId).html(expenseRateType);
			$("#expenseAmountDiv"+entityId).html(expenseRoundOffAmount);
			$("#entryDesDiv"+entityId).html(descriptions);
			$("#entryDesDiv"+entityId).prop("title", descriptions);
			$("#paidByDiv"+entityId).html(paidBy);
			
			$("#hideAndShowDetailsButton"+entityId).removeAttr('style');
			var hideAndShowDetailsHtml =  $("#hideAndShowDetailsButton"+entityId).html();
			$("#hideAndShowDetailsButton"+entityId).remove();
			$("#hideAndShowDetailsDiv"+entityId).prepend(hideAndShowDetailsHtml);
			
			$("#addExpenseCategoryButton"+entityId).removeAttr('value');
			$("#addExpenseCategoryButton"+entityId).attr('value','Update');
			
			$("#cancelExpenseCategoryButton"+entityId).removeAttr('onclick');
			$("#cancelExpenseCategoryButton"+entityId).attr('onclick','hideAndShowDetailsRow('+parseInt(entityId+1)+'),"Edit"');
			
			
			$("#divEntityTsRow"+parseInt(entityId+1)).removeAttr('style');
			$("#divEntityTsRow"+parseInt(entityId+1)).attr('style','display:none;');
			
			return true;
		}else{
			return false;
		}
	}
</script>
 <script>
function displayCalendar(entityId){
	$("#categoryDate"+entityId).datepicker("destroy");
	var projectStartDate = $('#projectStartDateExpensesId').val();
	var projectEndDate =   $('#projectEndDateExpensesId').val();
	if((projectStartDate != null && projectStartDate != '' && projectStartDate != 'undefined') || 
		(projectEndDate != null && projectEndDate != '' && projectEndDate != 'undefined')){
			$('#categoryDate'+entityId).datepicker({
				dateFormat : 'dd-mm-yy',
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


