<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="/resources/js/jquery.mask.min.js"></script>
<script src="/resources/js/jquery.maskedinput-1.3.min.js"></script>

<script>
$(document).ready(function(){
	populateInlineItemsGrid();
});
</script>

<script>
function checkBox(){
	var invoiceforexpenses = $("#invoiceforexpenses").val();
	if(invoiceforexpenses == 'Y'){
		$('#projectIdDiv').find('p.jquery_form_error_message').remove();
		$('#projectMemberIdDiv').find('p.jquery_form_error_message').remove();
		$('#expensesIdDiv').find('p.jquery_form_error_message').remove();
		$('#projectId').removeAttr('data-validation');
		$('#projectMemberId').removeAttr('data-validation');
		$('#expensesId').removeAttr('data-validation');
		$('#projectId').attr('data-validation-optional',"true");
		$('#projectMemberId').attr('data-validation-optional',"true");
		$('#expensesId').attr('data-validation-optional',"true");
		$("#invoiceforexpenses").val("N");
		$("#addbutton").show();
		populateInlineItemsGrid();
	}else{
		$('#projectIdDiv').find('p.jquery_form_error_message').remove();
		$('#projectMemberIdDiv').find('p.jquery_form_error_message').remove();
		$('#expensesIdDiv').find('p.jquery_form_error_message').remove();
		$('#projectId').attr("data-validation","required");
		$('#projectMemberId').attr("data-validation","required");
		$('#expensesId').attr("data-validation","required");
		$("#invoiceforexpenses").val("Y");
		$("#addbutton").hide();
		populateExpensesEntriesGrid("");
		 
	}
}
</script>

<div id="popUp"></div>
<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Create Bill</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; 
      <a href="${SourceLeadWeb_vmsUrl}/payablesList">Bill List</a> &raquo; Create Bill</div>
    <div class="success_pop" id="selector1" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
       </div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="clearfix form-horizontal inner-content">
       <div id="invoiceLoading"></div>
       <div id="expenseAlertPopUp"></div>
       <form:form id="project_payable_invoice_form"  name="project_payable_invoice_form" method="post"  enctype='multipart/form-data'> 
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
            <div class="form-group">
              <label class="col-md-3">Vendor<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9 chooRadius errorInptgroup">
              <div class="input-group col-md-10 pull-left">
			<select class="chosen-select form-control" data-Bill id="vendorOrgRelationId" name="vendorOrgRelationId" onchange="getVendorContacts();flushValues();getPaymentTerms();getPaymentAccountList();"
		         	data-validation="required" field-name="Vendor">
		      </select>
		       <span class="input-group-addon cursorPointer bg-blue" onclick="getVendors();creatExpPayBilsEmptyVendorContact();"><i class="fa fa-refresh "></i></span>
			</div>
			 <sec:authorize access="hasAnyRole('ROLE_CREATE_VENDOR_CONTACT')"> 
			<span class="col-md-2 noPad clearfix"><a href="${SourceLeadWeb_vmsUrl}/addVendor" target="_blank"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a></span> 
			</sec:authorize>
               <input type="hidden" name="savedInvoiceId" id="savedInvoiceId">
               <input type="hidden" name="vendorCurrency" id="vendorCurrency">
            </div>
                   </div>
            <div class="form-group">
              <label class="col-md-3">Vendor Contact<span class="text-danger">*</span></label>
              <div class="col-md-9 chooRadius errorInptgroup chosenmbNone">
              <div class="col-md-9 padding_left padding_right">
                <select class="chosen-select form-control" multiple="multiple"  id="vendorContact" name="vendorContact" data-validation="required" field-name="Vendor Contact">
                </select>
              </div>
              <span class="col-md-3 noPad clearfix" >
                   <span class="btn btn-primary btn-sm" onclick="getVendorContacts();"><i class="fa fa-refresh "></i></span>
			        <sec:authorize access="hasAnyRole('ROLE_CREATE_VENDOR_CONTACT')"> 
			         <a href="javascript:void(0)" onclick="selectedVendor();"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a>
			       </sec:authorize>
		     </span>
            </div>
                   </div> 
            <div class="form-group">
              <label class="col-md-3">Bill Id<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9" id="invoiceIdDiv">
                <input class="form-control" id="invoiceId" name="invoiceId" placeholder="Bill Id" type="text" onkeyup="validateBillId();" data-validation="required validate_Alphanumeric validate_length length2-100" field-name="Bill Id">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Bill Date<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9 select-field" id="errInvoiceDate">
              <div class="input-group">
                <input class="form-control calendar" id="invoiceDate" name="invoiceDate" placeholder="Bill Date" value="${currentDate}"  type="text" data-validation="required" field-name="Bill Date" readonly>
                <span class="input-group-addon" onClick="clearInvoiceDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
              </div>
              </div>
            </div>
              
           <div class="form-group">
              <label class="col-md-3">Payment Term</label>
              <div class="col-md-9 select-field">
                <select class="chosen-select form-control" id="paymentFrequency" name="paymentFrequency" onchange="getDueDateByFrequency();">
                  <option value=''>--Select Payment Term--</option>
                  <option>10 days</option>
                  <option>15 days</option>
                  <option>20 days</option>
                  <option>30 days</option>
                  <option>60 days</option>
                  </select>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Due Date<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9 errorInptgroup" id="errDuedate">
              <div class="input-group">
                <input class="form-control calendar" placeholder="Due Date" id="dueDate" name="dueDate" type="text" data-validation="required" field-name="Due Date" readonly>
                <span class="input-group-addon" onClick="clearDueDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
              </div>
              </div>
            </div>
              
            
             <div class="form-group">
	              <label class="col-md-3">Payment Account</label>
	              <div class="col-md-9" id="projectMemberIdDiv">
	                <select class="sel width100percnt" id="paymentAccountId"  field-name="paymentAccount Name" name="paymentAccount">
	                   <option value="">--Select Account--</option>
	                </select>
	              </div>
	            </div>
	            
	            <div class="clearfix"></div>
	        <div class="form-group">
              <label class="col-md-3">Billing Address</label>
              <div class="col-md-9">
                <textarea class="form-control" rows="4" placeholder="Billing Address" id="billingAddress" name="billingAddress"></textarea>
              </div>
            </div>
	            
	            <div class="form-group">
							<label class="col-md-3">Notify To Others</label>
							<div class="col-md-9">
								<textarea class="form-control" rows="4" id="notifyToOthers" name="notifyToOthers" field-name="NotifyTo" placeholder="Enter multiple Email Id's separated by commas(,)"></textarea>
							</div>
							<div class="clearfix"></div>
						</div>
	            
			
			<div class="clearfix mb"></div>
			<div class="form-group">
              <label class="col-md-3">&nbsp;</label>
              <div class="col-md-9 checkBox">
                  <input type="checkbox"  name="invoiceforexpenses" value="N" class="paddingRight" id="invoiceforexpenses" onclick="checkBox()">
                  Bill for Expenses
              </div>
            </div>
            
              <div id="associate-expenses" style="display: none">
              <div class="form-group">
              <label class="col-md-3">Project<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9 select-field"> 
                <select class="chosen-select form-control" id="projectId" name="projectId"  onchange="getProjectMemberByVendor();" field-name="Project Name"> 
                  </select>
              </div>
            </div>
                  <div class="form-group">
              <label class="col-md-3">Associate<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9 select-field" id="projectMemberIdDiv">
                <select class="chosen-select form-control" id="projectMemberId" name="projectMemberId" onchange="getExpenses();" field-name="Associate">
                </select>
              </div>
            </div>
                  <div class="form-group">
              <label class="col-md-3">Expenses<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9 select-field" id="expensesIdDiv">
                <select class="chosen-select form-control" id="expensesId" name="expensesId" onchange="getExpensesDetails();"  field-name="Expense Name">
                </select> 
              </div>
            </div>
              </div>
            
          </div>
        </div>
        <div class="clearfix"></div>
        <!-- Drap and Drop Table Row -->
        <!-- Table starts here-->
          <div class="project_wrap_table mt25 invoice_table table_custompadding" id="invoiceTable">
            <div id="tableBody"></div>
            
            <div class="clearfix"></div>
            <div class="clearfix" id="addbutton">
              <button type="button" class="btn btn-sm btn-primary mr" id="addExpense_Button" style="display: none;" onClick="addExpenseInvoiceRow()"><i class="fa fa-plus paddingRight"></i>Add</button>
            </div>
        </div>
        <!-- Table Ends here-->
          
           <div id="bottomGrid" style="display: none;" class="mt">
          <div class="clearfix invoice_other">
          <div class="col-md-5">
           
            <div class="clearfix mb">
              <label><strong>Message</strong></label>
              <textarea class="form-control" rows="2" id="comment" name="comment"></textarea>
            </div>
            <div class="clearfix mb invoiceAttachment">
            <input type='hidden' name='invoiceAttcahmentMaxRowNumber'  id='invoiceAttcahmentMaxRowNumber' value='1' />
            <table width="100%" width="100%" cellpadding="0" cellspacing="0"  id="invoiceAttachmentTbl">
            <tbody>
              <tr id='divAttachmentTsRow0'>
            	<td>
              	<label class="pull-left"><strong>Attachments</strong></label>
              	<div id="timesheetDocumentsId"><div class="timesheet_week_comm invoiceDocs width100percnt">
                <span><div id="savedAttachments"></div></span></div> </div>
	              <div class="timesheet_week_comm col-md-12 invoiceDocs invoiceDocs_add width100percnt" >
	                <div class="form-group" id='0invoicedocFileuploadFileGroup'> 
	                <span>
	                  <input type='file' name='0invoicedocFile' id='0invoicedocFile' field-name='Attachment'  class='timesheetAttachmentValidation' onchange="attachFile(0);"><i class='fa fa-times mt5' mouseOver="Clear" title="Clear" onClick='remove()'></i><i class='fa fa-plus' mouseOver="Add" title="Add" onClick='addInvoiceAttachment()'></i>
	                 </span> </div>
	              </div>
            	</td>
            </tr>
            </tbody>
            </table>
            
            </div>
          </div>
          <div class="col-md-7">
            <div class="form-horizontal">
              <div class="form-group hidden">
                <label class="control-label col-md-7"><strong>Subtotal</strong></label>
                <div class="col-md-5 text-right"><input type="text" value = "00:00" id="subTotal" name="subTotal" class="ReadTextFli"></div>
              </div>
              <!-- Discount UI -->
              <div class="form-group">
                <div class="col-md-7 select-field">
                  <div class="row">
                    <div class="col-md-7 paddingRight">
                      <select class="form-control input-sm" id="disCount" name="disCount" onchange="calculateDisCount();validateDiscount();">
                        <option value="percentage">Discount %</option>
                        <option value="flat">Discount Value</option>
                      </select>
                    </div>
                    <div class="col-md-5 paddingLeft" id="discountValueDiv">
                      <input class="form-control input-sm allownumericwithdecimal" id="discountValue" name="discountValue" placeholder="Flat / Discount" type="text" onblur="calculateDisCount();" onkeyup="validateDiscount();">
                    </div>
                  </div>
                </div>
                <div class="col-md-5 text-right">
                  <input class="form-control input-sm"  id="discountTotal" name="discountTotal" type="text" placeholder="00.00" readonly>
                </div>

              </div>
                 <div class="form-group">
                <label class="col-md-7 text-right"><strong>After Discount Total</strong></label>
                <div class="col-md-5 text-right" ><span class="pull-left">${SourceLeadWeb_currencyCode}</span><input type="text"  id="afterDiscountTotal" name="afterDiscountTotal"  value="00.00" class="ReadTextFli" readonly></div>
              </div>              
             
             <div id="lineItemAddRows"></div>
                <div class="form-group mt">
                <label class="col-md-7 text-right"><strong>Total</strong></label>
                    <div class="col-md-5 text-right"><span class="pull-left">${SourceLeadWeb_currencyCode}</span><input type="text" value = "00:00" id="grandTotal" name="grandTotal" class="ReadTextFli" readonly></span></div>
              </div>
              
               <div id="conversionId"></div>
              
              </div>
          </div>
        </div>
        </div> 
        <!-- Adding Content Starts herer -->
        <div id="lineItemPopUp"></div>
          <!-- Drap and Drop Table Row --> 
       <div id="payableAlertPopUp"></div>
      
          <footer class="form-footer">
              <div class="pull-right mr">
                  <button type="button" class="btn btn-sm btn-primary" id="saveButtonId" onclick="saveInvoice('DRAFT');">Save</button>
                  <button type="button" class="btn btn-sm btn-success ml" id="sendButtonId" onclick="saveInvoice('SENT')">Send</button>
                 <button type="button" class="btn btn-sm btn-default ml" onClick="javascript:window.location.href = '${SourceLeadWeb_vmsUrl}/payablesList'">Cancel</button>
              </div>
          </footer>
          </form:form>
     </div>
    </div>
  </section>
  
  <script>
  function remove(){
	  $('#0invoicedocFile').val("");
  }
  </script>
    
<!-- Diplays client contacts details when clien selected -->
<script>
function getProjectsList(projectName){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var orgRelationId = $("#vendorOrgRelationId").val();
	$.ajax({
	type : "GET",
	url: vmsUrl+"/getClientProjectsByOrgRelationId?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId,
	success :function(json) {
	var output = "";
	output = output + "<option value=''>--Select Project--</option>";
	for (var i=0; i<json.data.length; i++){
	var opt = json.data[i];
	var selected = "";
	if(projectName != null && projectName != "" && projectName !="undefined" && projectName == opt.projectName){
		selected = 'selected';
	}
	output = output + "<option value='" +opt.id+ "' "+selected+">"+opt.projectName+"</option>";
	}
	$("#projectId").empty();
	$("#projectId").append(output);
	$('#projectId').trigger("chosen:updated");
	},
	});

}
</script>




<!-- Get all assigned members for the project based on project id -->
 <script type="text/javascript">
function getProjectMemberByVendor(pid,projectMemberName){
	var projectId = $("#projectId").val();
	var orgRelationId = $("#vendorOrgRelationId").val();
	var userId = '${SourceLeadWeb_user.id}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	if(pid != null && pid!='undefined'){
		projectId =pid;
	}
	$.ajax({
	type : "GET",
	url: vmsUrl+"/getVendorAssignedMembersForProjects?"+tokenParamName+"="+tokenValue+"&projectId="+projectId+"&orgRelationId="+orgRelationId+"&userId="+userId+"&orgLocationId="+orgLocationId,
	success :function(json) {
		var output = "";
		output = output + "<option value=''>--Select Associate--</option>";
		if(json.data !=null){
			for (var i=0; i<json.data.length; i++)
			{
			var opt = json.data[i];
			var selected = "";
			  if(projectMemberName == opt.empName && projectMemberName != null && projectMemberName !='' && projectMemberName !="undefined" ){
				 selected = 'selected';
			}  
			output = output + "<option value='" +opt.id+ "' "+selected+">"+opt.empName+"</option>";
			}	
		}
		$("#projectMemberId").empty();
		$("#projectMemberId").append(output);
		$('#projectMemberId').trigger("chosen:updated");
	},
	}); 
 
}
</script> 


<script type="text/javascript">
function addConversionDiv(){
	var result = "";
	var vendorCurrency = $("#vendorCurrency").val();
	var currencyCode = '${SourceLeadWeb_currencyCode}';
	$("#conversionId").empty();
	if(vendorCurrency != currencyCode){
		result = result +'<div class="row">';
		result = result +'<div class="clearfix mt col-md-offset-2 col-md-10" style="padding-right:0;">';
		result = result +'<div class="clearfix"><label class="col-md-6 text-right"><strong>Conversion From</strong></label><div class="paddingLeft col-md-6"><span class="pull-left" id="currencyCode">'+currencyCode+'</span><div class="pull-right"><input type="text" id="conversionFrom" placeholder="00:00" class="ReadTextFli" readonly="" type="text"></div></div></div>';
		result = result +' <div class="clearfix"><label class="col-md-6 text-right"><strong>Conversion To</strong></label><div  class="paddingLeft col-md-6"><span id="vendorCurrencyCode">'+vendorCurrency+'</span><div id="exchangeCurrency" class="pull-right"><input  class="ReadTextFli" readonly="" type="text"></div></div></div>';
		result = result +'  <div class="clearfix"><label class="col-md-6 text-right"><strong>Exchange Rate</strong></label><div  class="paddingLeft col-md-6"><span id="convertedCurrencyCode">'+currencyCode+' to '+vendorCurrency+'</span><div id="exchangeRate" class="pull-right"><input class="ReadTextFli ReadTextFli90" readonly="" type="text"></div></div></div>';
		result = result +'</div>';
		result = result +'</div>';
		$("#conversionId").empty();
		$("#conversionId").append(result);
	}
}
</script>

<script type="text/javascript">
function alertMessageInPayable(){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>No Members Found</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancelPayableAlert();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelPayableAlert();'></b>";
	result = result +"	</div>";
	$("#payableAlertPopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script type="text/javascript">
function cancelPayableAlert(){
	$("#payableAlertPopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>


  <!-- calculating total expenses with quantity and rate -->
  <script>
  function totalHoursCalculateAmount(rowId){
	  var rate = $("#"+rowId+"rate").val();
	  var quantity = $("#"+rowId+"quantity").val();
      calAddRowAmount(rate,quantity,rowId); 
     conversion();
  }
  </script>
  
  
  <script>
  function  conversion(){
	   var orgRelationId = $("#vendorOrgRelationId").val();
	   var currencyCode = '${SourceLeadWeb_currencyCode}';
	   var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	   var grandTotal = $("#grandTotal").val();
	   if(grandTotal != '' && grandTotal != 'undefined'){
		   $.ajax({
				type : "GET",
				url: vmsUrl+"/getConversionExchangeRate?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId+"&grandTotal="+grandTotal,
				success :function(response) {
					if(response.object !='' && response.object != 'undefined' && response.object != null){
						$("#exchangeRate").html(response.object.exchangeRate);
						$("#exchangeCurrency").html(response.object.exchangeCurrency);
						$("#vendorCurrencyCode").html(response.object.vendorCurrencyCode);
						$("#convertedCurrencyCode").html(currencyCode +' to '+ response.object.vendorCurrencyCode);
					}
				},
				});   
	   }
	  
		
}
  
  function calAddRowAmount(rate,quantity,rowId){
	  var totlalAmount = (rate * quantity);
	     $('#'+rowId+'total').empty();
	     $('#'+rowId+'total').val(totlalAmount.toFixed(2));
	     calculateSum();
  }
  </script>


<!-- Converting minutes to hours if minutes >60 -->
<script>
function setHours(finalmin,rowId){
	 var actualHours = Math.floor(finalmin / 60); 
     var actualMinutes = finalmin % 60;
     var actualTotal = 0;
     if (actualHours >= 10 && actualHours < 100) {
			actualHours = '0' + actualHours;
	  }
     if (actualHours < 10) {
			actualHours = '00' + actualHours;
	  }
	 if (actualMinutes < 10) {
		actualMinutes = '0' + actualMinutes;
	 }
	 if(!isNaN(actualHours) && !isNaN(actualMinutes)){
		 actualTotal = actualHours +":"+ actualMinutes;
	 }else{
		 actualTotal =  "000:00";
	 }
	 $('#'+rowId+'totalHours').empty();
     $('#'+rowId+'totalHours').val(actualTotal);
}
</script>

<!-- calculating sub total with all row total amount -->
<script>
function calculateSum(){
	 var maxRowNum = $("#invoiceExpMaxRowNumber").val();
	 var sum = 0;
	  for(var i=0;i < maxRowNum;i++){
		  if(document.getElementById(i+"total")){
	    		 if(document.getElementById(i+"total").value){
	    			var rowAmount = $('#'+i+'total').val();
		 	    	sum = parseFloat(sum) + parseFloat(rowAmount);
	    		 }
	    		
	    	 } 
	 }  
	  $("#inoviceTotal").val(sum.toFixed(3));
	  $("#subTotal").val(sum.toFixed(3));
	  $("#grandTotal").val(sum.toFixed(3));
	  $("#conversionFrom").val(sum.toFixed(3));
	  $("#afterDiscountTotal").val(sum.toFixed(3));
	  calculateDisCount();
	  calculateTaxDiscount();
}
</script>
  
 <!-- Calculationg Total Discount With Sub Total --> 
<script type="text/javascript">
function calculateDisCount(){
	
	var discountType = $("#disCount").val();
	var subTotal = $("#subTotal").val();
	var discountValue = $("#discountValue").val();
	var total ='';
	if(discountType == 'flat'){
		if(discountValue && subTotal > 0){
			total = subTotal-discountValue;
			$("#discountTotal").val(discountValue);
			$("#grandTotal").val(total);
			$("#conversionFrom").val(total);
			$("#afterDiscountTotal").val(total.toFixed(2));
		}else{
			$("#discountTotal").val("00.00");
			$("#grandTotal").val(subTotal);
			$("#conversionFrom").val(subTotal);
			$("#afterDiscountTotal").val(subTotal);
		}
		
	}
	if(discountType == 'percentage'){
		if(discountValue && subTotal > 0){
			var perCentValue = (subTotal * discountValue)/100;
			total = subTotal - perCentValue;
			$("#discountTotal").val(perCentValue.toFixed(2));
			$("#grandTotal").val(total);
			$("#conversionFrom").val(total);
			$("#afterDiscountTotal").val(total.toFixed(2));
		}else{
			$("#discountTotal").val("00.00");
			$("#conversionFrom").val(subTotal);
			$("#afterDiscountTotal").val(subTotal);
		}
		
	}
	 calculateTaxDiscount();
	 conversion();
}
</script>

<!-- Adding Dynamic Tax Div Ids -->
<!-- ********************************************* Add Rows AddTax Table functionality start  ************************************************************ -->	
  
  <div id="divTaxTd1" style="display: none; float: left;">
	<div class="select-field tblminwidth-75">
		  <div class="tblminwidth-50">
		        <input type="text" class="form-control input-sm" value="" placeholder="Tax type" name='invoiceTax[REPLACEROWID].taxType' id='REPLACEROWIDtaxType' data-validation="required validate_length length2-100" field-name="Tax Type">
		        <input type="hidden" name="REPLACEROWIDinvoiceTaxId" id="REPLACEROWIDinvoiceTaxId">
		  
		  </div>
	 </div>
</div>

  <div id="divTaxTd2" style="display: none; float: left;">
	 <div class="btn-group tblminwidth-75">
	 <select class="form-control input-sm" id="REPLACEROWIDtaxdiscountType" name="invoiceTax[REPLACEROWID].taxdiscountType" onchange="calculateTaxDiscount();validateRowDiscount(REPLACEROWID);">
		 <option value="percentage">Percentage</option>
		 <option value="flat">Flat</option>
 		  </select>
	  </div>
  </div>
  
  <div id="divTaxTd3" style="display: none; float: left;">
     <div class="tblminwidth-75" id="REPLACEROWIDdiscountRowDiv">
        <input type="text" class="form-control input-sm allownumericwithdecimal" value="" placeholder="Flat / Discount" name='invoiceTax[REPLACEROWID].taxdiscountvalue' id='REPLACEROWIDtaxdiscountvalue' onblur="calculateTaxDiscount();" onkeyup="validateRowDiscount(REPLACEROWID)">
    </div>
  </div>
  
  <div id="divTaxTd4" style="display: none; float: left;">
	  <div class="tblminwidth-75">  
	       <input type="text" class="form-control input-sm" value ="00.00" placeholder="Total Value" name='invoiceTax[REPLACEROWID].taxTotal' id='REPLACEROWIDtaxTotal' readonly="readonly">
	   </div>
  </div>
  <div id="divTaxTd5" style="display: none; float: left;">
         <a class="text-danger tblminwidth-50" title="Remove row" onclick="removeTaxRow(REPLACEROWID)"><i class="fa fa-remove"></i></a>
  </div>
 <script>
	function addTax(){  
				var checkMaxTsRowNum = $("#maxTaxRowNum").val();
				var tbl = document.getElementById("addTaxTbl");
				var maxRowNum = parseInt($("input[name = 'maxTaxRowNum']").val());
				$("input[name = 'maxTaxRowNum']").val(maxRowNum + 1);
				
				var row = tbl.insertRow(maxRowNum+1);
				row.setAttribute("id", "divTaxRow" + maxRowNum);
				
				var cell0 = row.insertCell(0);
				var divTaxTdStr = document.getElementById("divTaxTd1").innerHTML;
				divTaxTdStr = divTaxTdStr.replace(/REPLACEROWID/g, (maxRowNum));
				cell0.innerHTML = divTaxTdStr;
				
				var cell1 = row.insertCell(1);
				var divTaxTdStr1 = document.getElementById("divTaxTd2").innerHTML;
				divTaxTdStr1 = divTaxTdStr1.replace(/REPLACEROWID/g, (maxRowNum));
				cell1.innerHTML = divTaxTdStr1;
				
				var cell2 = row.insertCell(2);
				var divTaxTdStr2 = document.getElementById("divTaxTd3").innerHTML;
				divTaxTdStr2 = divTaxTdStr2.replace(/REPLACEROWID/g, (maxRowNum));
				cell2.innerHTML = divTaxTdStr2;
				
				
				var cell3 = row.insertCell(3);
				var divTaxTdStr3 = document.getElementById("divTaxTd4").innerHTML;
				divTaxTdStr3 = divTaxTdStr3.replace(/REPLACEROWID/g, (maxRowNum));
				cell3.innerHTML = divTaxTdStr3;
				
				var cell4 = row.insertCell(4);
				var divTaxTdStr4 = document.getElementById("divTaxTd5").innerHTML;
				divTaxTdStr4 = divTaxTdStr4.replace(/REPLACEROWID/g, (maxRowNum));
				cell4.innerHTML = divTaxTdStr4;
				 maskme();
				
	}			
</script>
<!-- Removing Tax Rows -->
<script type="text/javascript">
	function removeTaxRow(rowId) {
		
		//After removing the row calculate the amount and append the grand total
		var grandTotal =  $("#grandTotal").val();
		var taxTotal = $("#"+rowId+"taxTotal").val(); 
		var first=parseFloat(grandTotal);
        var second=parseFloat(taxTotal);
        total = Math.round(first-second);
        $("#grandTotal").val(total); 
        $("#conversionFrom").val(total);
        conversion();
		document.getElementById('divTaxRow' + rowId).style.display = 'none';
		var r = document.getElementById('addTaxTbl').rows[rowId+1];
		var count = 2 * (r.cells.length);
		for (i = 0; i <= count; i++) {
			r.deleteCell(0);
		}
		
	}
</script>

<script type="text/javascript">
function singleRowTaxDiscount(){
	var taxType = $("#0taxdiscountType").val();
    var taxValue = $("#0taxdiscountvalue").val();
    var afterDiscountTotal = $("#afterDiscountTotal").val();
     var total ='';
    if(taxType == 'flat'){
        total = parseInt(afterDiscountTotal) + parseInt(taxValue);
        $("#0taxTotal").val(taxValue);
        $("#grandTotal").val(total);
        $("#conversionFrom").val(total);
    }
    if(taxType == 'percentage'){
        var perCentValue = (afterDiscountTotal * taxValue)/100;
        var first=parseFloat(perCentValue.toFixed(2));
        var second=parseFloat(afterDiscountTotal);
        total = (first+second).toFixed(2);
        $("#0taxTotal").val(first);
        $("#grandTotal").val(total);
        $("#conversionFrom").val(total);
    }   
}
</script>

<!-- Calculating Tax discount -->
 <script type="text/javascript">
function calculateTaxDiscount(){
	
	var maxTaxRowNum = $("#maxTaxRowNum").val();
	var afterDiscountTotal = $("#afterDiscountTotal").val();
	
	for(var i=0; i<maxTaxRowNum; i++){
		if($("#"+i+"taxdiscountType").val() != '' && $("#"+i+"taxdiscountType").val() != null && $("#"+i+"taxdiscountType").val() != 'undefined'){
			var taxType = $("#"+i+"taxdiscountType").val();
		    var taxValue = $("#"+i+"taxdiscountvalue").val();
		   
		    if(taxType == 'flat'){
		    	if(taxValue){
		    		first = parseFloat(taxValue);
			        $("#"+i+"taxTotal").val(first.toFixed(2));
		    	}
		    	
		    }
		    
		    else if(taxType == 'percentage'){
		        var perCentValue = (afterDiscountTotal * taxValue)/100;
		        first = parseFloat(perCentValue);
		        $("#"+i+"taxTotal").val(first.toFixed(2));
		    }   
		}
	}
	
	var taxGrandTotal = 0;
	for(var i=0; i<maxTaxRowNum; i++){
		if($("#"+i+"taxTotal").val() != null && $("#"+i+"taxTotal").val() != '' && $("#"+i+"taxTotal").val() != 'undefined'){
			taxGrandTotal = parseFloat(taxGrandTotal + parseFloat($("#"+i+"taxTotal").val()));			
		}
	}
	
	var grandTotal = parseFloat(afterDiscountTotal) + parseFloat(taxGrandTotal)
	 $("#grandTotal").val(grandTotal.toFixed(2));
   	 $("#conversionFrom").val(grandTotal.toFixed(2)); 
   	conversion();
}
</script>


<!-- Adding Attachment Div Ids -->

<!-- ********************************************* Add Rows resume Table functionality start  ************************************************************ -->
  
  <div id="divInvoiceAttachmentTd1" style="display: none; float: left;">
  			<div class="timesheet_week_comm col-md-12 invoiceDocs invoiceDocs_add width100percnt">
                <div class="form-group" id="REPLACEROWIDinvoicedocFileuploadFileGroup"> 
               		 <span> 
               		 	<input type="file" name='REPLACEROWIDinvoicedocFile' id='REPLACEROWIDinvoicedocFile' onchange='attachFile(REPLACEROWID);validateContractsUploadFile(REPLACEROWID)'>
               		    <i class="fa fa-times mt5" mouseOver="Delete" title="Delete" onclick="removeInvoiceAttachmentRow(REPLACEROWID);"></i>
               		   </span>
                </div>
             </div>
</div>


<script>
	function addInvoiceAttachment(){  
				var checkMaxTsRowNum = $("#invoiceAttcahmentMaxRowNumber").val();
				var tbl = document.getElementById("invoiceAttachmentTbl");
				var maxRowNum = parseInt($("input[name = 'invoiceAttcahmentMaxRowNumber']").val());
				$("input[name = 'invoiceAttcahmentMaxRowNumber']").val(maxRowNum + 1);
				
				var row = tbl.insertRow(maxRowNum);
				row.setAttribute("id", "divAttachmentContractsRow" + maxRowNum);
				
				var cell0 = row.insertCell(0);
				var divAttachmentTdStr = document.getElementById("divInvoiceAttachmentTd1").innerHTML;
				divAttachmentTdStr = divAttachmentTdStr.replace(/REPLACEROWID/g, (maxRowNum));
				cell0.innerHTML = divAttachmentTdStr;
				
	}			
</script>

<!-- Remove attachment Div Ids -->
<script type="text/javascript">
	function removeInvoiceAttachmentRow(rowId) {
		 document.getElementById("divAttachmentContractsRow"+rowId).style.display = 'none';
		 document.getElementById("divAttachmentContractsRow"+rowId).value= '';
		 var  rId = "divAttachmentContractsRow"+rowId;
		  var r = document.getElementById("invoiceAttachmentTbl").rows[rId];
          var count = 2*(r.cells.length);
		    for(i=0;i<=count;i++){
		        r.deleteCell(0);
		    } 
		 
	}
</script>

<script type="text/javascript">
 function validateBillId() {
	 var invoiceId = document.getElementById("invoiceId").value; 
	 var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	 if(invoiceId != null && invoiceId != ""){
		 $.ajax({
		    	type: "POST",
		        url: vmsUrl+"/validateInvoiceId?"+tokenParamName+"="+tokenValue+"&invoiceId="+invoiceId+"&invoiceType=payables", 
		        success: function(data) {
		        	 if(data == true){
		        		 $("#saveButtonId").attr("disabled", "disabled");
						 $("#sendButtonId").attr("disabled", "disabled");
		        		  $('#invoiceIdDiv').find('p.jquery_form_error_message').remove();
						  $("input#invoiceId").after("<p class='jquery_form_error_message'>"+invoiceId+" is Already Existed</span>");
						  document.getElementById("invoiceId").setAttribute('record-exist','yes');
						  document.getElementById("invoiceId").setAttribute('record-exist-errorMsg',invoiceId+' is Already Existed'); 
		        	}
		        	else{
		        		 $('input#invoiceId').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#invoiceIdDiv').find('p.jquery_form_error_message').remove(); 
		        		 $("#saveButtonId" ).addClass('us-btn').val('Save');
                         $("#saveButtonId").removeAttr("disabled");
                         $("#sendButtonId" ).addClass('us-btn').val('Send');
                         $("#sendButtonId").removeAttr("disabled");
		        	}
		        },
		    });
	 }
 }
 </script>  


<script>
function saveInvoice(saveType){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var userId = '${SourceLeadWeb_user.id}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var currencyCode = '${SourceLeadWeb_currencyCode}';
	var maxTsRowNum = parseInt($("input[name = 'invoiceExpMaxRowNumber']").val());
         if ($('#project_payable_invoice_form').validate(false, validationSettings)){  
 	 	    var formData = getFormData1("project_payable_invoice_form");
 	 	    
 	 	  if ($("#notifyToOthers").val() !== "") {
				for (var e in $("#notifyToOthers").val().split(",")) {
					if (!jQueryFormUtils.validateEmail($("#notifyToOthers").val().split(",")[e].replace(/\s+/g, ''))) {
						flag = false;
						$("#notifyToOthers").parent().append("<p class='jquery_form_error_message'>Enter valid email id</p>");
						return;
					}
				}
			}
 	 	  if(maxTsRowNum==0){
	        	var result = "";
	     		result = result +"	<div class='project_inn_mask'></div>";
	     		result = result +"	   <div id='invoice_pop' class='remove_user_pop remove_user_pop_a'>";
	     		result = result +"		<p>At least one Add column Receivables Line Item</p>";
	     		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	     		result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	     		result = result +"	</div>";
	     		$("#popUp").append(result);
	     		$("#invoice_pop").slideDown(200);
	     		$(".project_inn_mask").fadeIn(200);
		        }else{
		        	  $("#invoiceLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		        	  $.ajax({
		        		  url: vmsUrl+"/saveExpensesInvoice?"+tokenParamName+"="+tokenValue+"&userId="+userId+"&orgLocationId="+orgLocationId+"&saveType="+saveType+"&currencyCode="+currencyCode+"&invoiceType=payable&type=expenses",
		        		  type: "POST",
		        		  data: formData,
		        		  processData: false,
		        		  contentType: false,
		        		  success: function (result) {
				  					$("#invoiceLoading").removeAttr("style");
		    		     			location.replace(vmsUrl+"/payablesList");
		    		     			$("#selector1").removeAttr("style");
						    		$("#selector1").css({ display: "block" });
						    	    $("#msgDiv").html("Successfully you have created a Invoice..");
		    		     			msgFadein();
		        		  }
			  			
			  			   }); 
		    	         return true; 
		        }
         }else{
	    		return false;
	    	  } 
	   
}

$('body').on('blur', '#project_payable_invoice_form', function() {
	$("#project_payable_invoice_form").showHelpOnFocus();
	$("#project_payable_invoice_form").validateOnBlur(false, validationSettings);  
}); 

function msgFadein() {
	$("#selector1").fadeIn(200);
    $('#selector1').delay(2000).fadeOut('slow');
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
 
 <!-- Adding Date Pickers -->
  <script>
	$(function() {
		var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatEndDate = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatEndDate = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatEndDate = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatEndDate = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatEndDate = 'yy-dd-mm'; 
		  }

		$('#invoiceDate').datepicker({
			dateFormat : finalDateFormatEndDate,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				var dt = toDate("#invoiceDate");
				dt.setDate(dt.getDate() + 1);
				$("#dueDate").datepicker("option", "minDate", dt);
						errInvoiceDate();
						getDueDateByFrequency();
			}
		});
	  $('#dueDate').datepicker({
			dateFormat : finalDateFormatEndDate,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				var dt = toDate("#dueDate");
				dt.setDate(dt.getDate() - 1);
				$("#invoiceDate").datepicker("option", "maxDate", dt);
					errDueDate();
			}
		});
		
	});
</script>
<script>
function toDate(selector) {
    var from = $(selector).val().split("-");
    return new Date(from[2], from[1] - 1, from[0]);
}
</script>
<!-- To Get the Existed invoice details to Edit -->
<script>
	function loadInvoiceDetails(invoiceUUID){
		var vmsUrl ='${SourceLeadWeb_vmsUrl}';
		$.ajax({
		    	type: "POST",
		    	 url: vmsUrl+"/getInvoiceDetailsByInvoiceUUID?"+tokenParamName+"="+tokenValue+"&invoiceUUID="+invoiceUUID, 
		         success: function(data) {
		        	 var json = JSON.parse(data);
		        	 var result = '';
		        	 if(json.paymentAccountId > 0) {
		        		 getPaymentAccountList(json.vendorOrgRelationId,json.paymentAccountId)
		        	 }
		        	 
		        	 var selected = 'selected';
		        	 result = result +'<option disabled selected>--Select Vendor--</option>';
		        	 result = result +'<option value='+json['vendorOrgRelationId']+' '+selected+' data-Bill='+json['generateBill']+'>'+json['orgName']+'</option>';
		        	 $("#vendorOrgRelationId").empty();
		           	 $("#vendorOrgRelationId").append(result); 
		           	 $('#vendorOrgRelationId').trigger("chosen:updated");
		           	$('#vendorCurrency').val(json['vendorCurrency']);
		           	var invoiceCreateType = $("#vendorOrgRelationId").find(':selected').attr("data-Bill");
					 if(invoiceCreateType == "Individual"){
						  $("#addExpense_Button").prop("disabled",true);
					  }else{
						  $("#addExpense_Button").prop("disabled",false); 
					  }
		           	//getVendorContacts(json['vendorContactId']);
		           	getVendorContacts(json['contactUsers']);
		           	if(json['expensesInvoices'] !=null && json['expensesInvoices'] =='Y'){
		           		checkedvalue(json['expensesInvoices']);
		           		getProjectsList(json['projectName']);
		           		getProjectMemberByVendor(json['projectId'],json['projectMemberName']); 
		           		getExpenses(json['memberId'],json['expensesFrequency'],json['expId']);
		           		editExpensesDetailsEdit(json['invoiceLineItemsList']); 
		           		
		           	}else{
		           	 	editExpensesInlineItems(json['invoiceLineItemsList']);
		           	}
		           
		           	$("#invoiceId").val(json['invoiceId']);
		           	
		           	if(json['paymentFrequency'] != null && json['paymentFrequency'] != '' && json['paymentFrequency'] != 'undefined'){
		           		$("#paymentFrequency").val(json['paymentFrequency']);
		           	    $('#paymentFrequency').trigger("chosen:updated");
		           	}
		           	
		           	$("#invoiceDate").val(json['invoiceDateStr']);
		           	$("#dueDate").val(json['dueDateStr']);
		           	
		           	if(json['billingAddress'] != null && json['billingAddress'] != '' && json['billingAddress'] != 'undefined'){
		           		$("#billingAddress").val(json['billingAddress']);
		           	}
		           	
		           	if(json['notifyToOthers'] != null && json['notifyToOthers'] != '' && json['notifyToOthers'] != 'undefined'){
		           		$("#notifyToOthers").text(json['notifyToOthers']);
		           	}
		           	
		        	if(json['accountDetails'] != null && json['accountDetails'] != '' && json['accountDetails'] != 'undefined'){
		           		$("#accountDetails").val(json['accountDetails']);
		           	}	
		           	/* buildLineItemAndTaxAddRows(); */
		           	
		           	/* editInvoice(json['invoiceLineItemsList']);*/
		        	$("#inoviceTotal").val(json['subTotal']);
		        	$("#subTotal").val(json['subTotal']);
		        	$("#disCount").val(json['discountType']);
		        	$("#discountValue").val(json['discountAmount']);
		        	$("#discountTotal").val(json['discountValue']);
		        	$("#comment").val(json['invoiceComment']);
		        	$("#afterDiscountTotal").val(json['afterDiscountValue']);http://localhost:8080/app/getReceivableInvoiceByUUID?invoiceUUID=sTljRS5n&projectId=15
		        	
		        	editTax(json['invoiceTaxList'])
		        	
		        	getSavedAttachments(json['invoiceDocumentsList']);
		        	
		        	$("#grandTotal").val(json['total']);
		        	$("#conversionFrom").val(json['total']); 
		        	 $("#exchangeCurrency").html(json['convertAmount']);
		        	 $("#exchangeRate").html(json['exchangeRate']);
		        	
		        	$("#savedInvoiceId").val(json['id']); 
		        	
		        	var test = json['invoiceParentId'];
		        	if(json['invoiceStatus'] == "Rejected" && test != null){
			   			 document.getElementById("sendButtonId").style.display="none";
			   			 document.getElementById("saveButtonId").style.display="none";
			       	}else{
			       		document.getElementById("sendButtonId").style.display="inline-block";
			   			document.getElementById("saveButtonId").style.display="inline-block";
			       	}
		        	
		        } 
    		});
	}
	</script>
	
<script type="text/javascript">
$(document).ready(function(){
	var invoiceUUID = '${invoiceUUID}';
	if(invoiceUUID != null && invoiceUUID != '' && invoiceUUID != 'undefined'){
		loadInvoiceDetails(invoiceUUID);
	}else{
		getVendors();
		$("#invoiceId").val("");
		$("#paymentFrequency").val("");
		$("#paymentFrequency").trigger("chosen:updated");
		$("#dueDate").val("");
	}
	
});
</script>
  



<script>
function getVendors(){
	var orgRelationId ='${orgRelationId}';
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	$.ajax({
		type : "GET",
		url: vmsUrl+"/getPayableVendorsList?"+tokenParamName+"="+tokenValue+"",
		success :function(data) {
		var json = JSON.parse(data);
		var output = "";
		output = output + "<option value=''>Select Vendor</option>";
		for (var i=0; i<json.length; i++)
		{
		var opt = json[i];
		if(orgRelationId == opt.id){
			var selected = 'selected';
			output = output + "<option value='" +opt.id+ "'"+selected+" data-Bill = '"+opt.generateBill+"'>"+opt.orgName+"</option>";
		}else{
			output = output + "<option value='" +opt.id+ "' data-Bill = '"+opt.generateBill+"'>"+opt.orgName+"</option>";
		}
		}
		$("#vendorOrgRelationId").empty();
		$("#vendorOrgRelationId").append(output);
		$('#vendorOrgRelationId').trigger("chosen:updated");
		
		if(orgRelationId != null && orgRelationId != '' && orgRelationId != 'undefined'){
			getVendorContacts();
			getPaymentTerms();
		}
	},
	});

	}
</script>

 <!--show tax values in Edit mode  -->
<script>
	function editTax(invoiceTaxList){  
		for(var i=0; i<invoiceTaxList.length; i++){
			var opt = invoiceTaxList[i];
				 
				 var checkMaxTsRowNum = $("#maxTaxRowNum").val();
					var tbl = document.getElementById("addTaxTbl");
					var maxRowNum = parseInt($("input[name = 'maxTaxRowNum']").val());
					$("input[name = 'maxTaxRowNum']").val(maxRowNum + 1);
					
					var row = tbl.insertRow(maxRowNum+1);
					row.setAttribute("id", "divTaxRow" + maxRowNum);
					
					var cell0 = row.insertCell(0);
					var divTaxTdStr = document.getElementById("divTaxTd1").innerHTML;
					divTaxTdStr = divTaxTdStr.replace(/REPLACEROWID/g, (maxRowNum));
					cell0.innerHTML = divTaxTdStr;
					
					var cell1 = row.insertCell(1);
					var divTaxTdStr1 = document.getElementById("divTaxTd2").innerHTML;
					divTaxTdStr1 = divTaxTdStr1.replace(/REPLACEROWID/g, (maxRowNum));
					cell1.innerHTML = divTaxTdStr1;
					
					var cell2 = row.insertCell(2);
					var divTaxTdStr2 = document.getElementById("divTaxTd3").innerHTML;
					divTaxTdStr2 = divTaxTdStr2.replace(/REPLACEROWID/g, (maxRowNum));
					cell2.innerHTML = divTaxTdStr2;
					
					
					var cell3 = row.insertCell(3);
					var divTaxTdStr3 = document.getElementById("divTaxTd4").innerHTML;
					divTaxTdStr3 = divTaxTdStr3.replace(/REPLACEROWID/g, (maxRowNum));
					cell3.innerHTML = divTaxTdStr3;
					
					var cell4 = row.insertCell(4);
					var divTaxTdStr4 = document.getElementById("divTaxTd5").innerHTML;
					divTaxTdStr4 = divTaxTdStr4.replace(/REPLACEROWID/g, (maxRowNum));
					cell4.innerHTML = divTaxTdStr4;
					/* maskme(); */
					
					$('#'+maxRowNum+'taxType').val(opt.taxName);
					$('#'+maxRowNum+'taxdiscountType').val(opt.taxType);
					$('#'+maxRowNum+'taxdiscountvalue').val(opt.taxValue);
					$('#'+maxRowNum+'taxTotal').val(opt.taxAmount);
					$('#'+maxRowNum+'invoiceTaxId').val(opt.id);
					
		}
	}			
</script>
<!-- show existed docs in edit mode -->
<script>
	function getSavedAttachments(invoiceDocumentsList){
		var result = '';
		if(invoiceDocumentsList.length > 0){
			
			 for (var i = 0; i < invoiceDocumentsList.length; i++) { 
				 var opt = invoiceDocumentsList[i]; 
	    		
				 result = result +"<strong id='invoiceDocument"+opt.id+"'>";
	    		 result = result +"<i class=''><a class='glyphicon glyphicon-save' href=\'"+opt.fileUrl+"\' title='download' download></a></i>";
					 result = result +"<input type='button' value='"+opt.fileName+"'>";
					 result = result +"</a>";
					 result = result +"<input class='dele' type='button' onClick='deleteDocument("+opt.id+");' >";
					 result = result +"</strong>";
					
	    	 }
			 
			 $("#savedAttachments").empty();
			 $("#savedAttachments").append(result);
		}
    	
	}
	
     function deleteDocument(id){
	    var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Are you sure to delete Attachment</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteInvoiceDocument("+id+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
		result = result +"	</div>";
		$("#lineItemPopUp").append(result);
		$("#remove_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
   }
	
	
	function deleteInvoiceDocument(documentId){
		var vmsUrl ='${SourceLeadWeb_vmsUrl}';
		if(documentId){
			$.ajax({
		    	type: "POST",
		    	 url: vmsUrl+"/deleteInvoiceDocumentBydocumentId?"+tokenParamName+"="+tokenValue+"&documentId="+documentId, 
		         success: function(data) {
		        	 document.getElementById("invoiceDocument"+documentId).remove();
		        	 $("#lineItemPopUp").empty();
		        	 $(".project_inn_mask").fadeOut(200);
		         }
			});
			
	     }
	  }
	
	</script>
	
	<script type="text/javascript">
function cancel(){
	$("#popUp").empty();
	$("#lineItemPopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>


<script>
    $("#invoiceforexpenses").click(function(){
         if ($(this).is(':checked')) {
        document.getElementById("associate-expenses").style.display="block";
    } else {
        document.getElementById("associate-expenses").style.display="none";
    }
    });
</script>

<script>
function addTaxDivs(){
	var result = "";
	result = result +'  <div class="addtax_table">';
    result = result +'  <input type="hidden" id="maxTaxRowNum" name="maxTaxRowNum" value="0" />';
    result = result +'  <table width="100%" cellpadding="0" cellspacing="0" class="table-bordered table-striped table-responsive">';
    result = result +'  <thead id="addTaxTbl">';
    result = result +'  <tr>';
    result = result +'  <th colspan="5">';
    result = result +'  Add Tax';
    result = result +'  <button type="button" class="btn btn-sm btn-primary buttonCustom" title="Add Tax" onclick="addTax()">';
    result = result +'  <i class="fa fa-plus"></i></button>';
    result = result +'  </th>';
    result = result +'  </tr>';
    result = result +'  </thead>';
    result = result +'  <tbody>';
    result = result +'  <tr id="divTaxTsRow0">';
    result = result +'  </tr>';
    result = result +'  </tbody>';
    result = result +'  </table>';
    result = result +'  </div>';
    $("#lineItemAddRows").empty();
    $("#lineItemAddRows").append(result);
}
</script>

<script> 
function getPaymentTerms(){
	var orgRelationId = $("#vendorOrgRelationId").val();
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	$.ajax({
		type : "GET",
		url: vmsUrl+"/getPaymentTermsAndBillindAddress?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId,
		success :function(data) {
			 var json = JSON.parse(data);
			 for (var i=0; i<json.length; i++)
				{
				var repot = json[i];
				 	if(repot.paymentTerms != null && repot.paymentTerms != '' && repot.paymentTerms != 'undefined'){
		           		$("#paymentFrequency").val(repot.paymentTerms);
		           	    $('#paymentFrequency').trigger("chosen:updated");
		           	  getDueDateByFrequency();
		           	}else{
		           		$("#paymentFrequency").val('');
		           	    $('#paymentFrequency').trigger("chosen:updated");
		           	}
		           	
		           	if(repot.billingAddress != null && repot.billingAddress != '' && repot.billingAddress != 'undefined'){
		           		$("#billingAddress").val(repot.billingAddress);
		           	}else{
		           		$("#billingAddress").val('');
		           	}
				}
			
		}
	});
}
</script>
<script>
function getDueDateByFrequency(){
	 var invoiceDate = document.getElementById("invoiceDate").value;
	 var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	 var paymentFrequency = document.getElementById("paymentFrequency").value;
	 var locationCode ='${SourceLeadWeb_locationCode}';
	 if(paymentFrequency && invoiceDate){
	    $("#invoiceLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	    
	    $.ajax({
			type : "GET",
			url: vmsUrl+"/getDueDateByFrequency?"+tokenParamName+"="+tokenValue+"&paymentFrequency="+paymentFrequency+"&invoiceDate="+invoiceDate+"&locationCode="+locationCode,
			success :function(dueDateStr) {
				$("#dueDate").val(dueDateStr);
				$("#invoiceLoading").removeAttr("style");
			}
		});
		
	}else{
		$("#dueDate").val('');
	}
}
</script> 


<script>
function flushValues(){
	$("#invoiceId").val('');
	//$("#invoiceDate").val('');
	$("#dueDate").val('');
	$("#paymentFrequency").val('');
	$("#frequency").val('').trigger("chosen:updated");
	$("#billingAddress").val('');
	$("#projectId").val('').trigger("chosen:updated");
	$("#comment").val('');
	$("#discountValue").val('');
	$("#discountTotal").val('');
	$("#subTotal").val('0.0');
	$("#afterDiscountTotal").val('0.0');
	$("#grandTotal").val('0.0');
	$("#conversionFrom").val('0.0'); 
	
}
function errDueDate(){
	$('#errDuedate').find('p.jquery_form_error_message').remove();
}
function errInvoiceDate(){
	$('#errInvoiceDate').find('p.jquery_form_error_message').remove();
} 
</script>

<!-- Get Vendor contacts based on vendor -->
<script>
function getVendorContacts(vendorContactId){
	var orgRelationId = $("#vendorOrgRelationId").val();
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	$("#vendorContact").empty();
	$.ajax({
		type : "GET",
		url: vmsUrl+"/getPayableVendorContactsByOrgRelationId?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId,
		success :function(json) {
		var output = "";
		for (var i=0; i<json.data.length; i++)
		{
			var opt = json.data[i];
			if(vendorContactId != null && vendorContactId != "" && vendorContactId !="undefined"){
				var array = vendorContactId.split(",");
				if(array.includes(opt.spocUserId.toString())){
					output = output + "<option value='"+opt.spocUserId+"' selected >"+opt.ownerName+"("+opt.spocName+")</option>";
				}else{
					output = output + "<option value='"+opt.spocUserId+"' >"+opt.ownerName+"("+opt.spocName+")</option>";
				}
			}else{
				output = output + "<option value='" +opt.spocUserId+ "'>"+opt.ownerName+"("+opt.spocName+")</option>";
			}
			$("#vendorCurrency").val(opt.vendorCurrency);
		}
		$("#vendorContact").empty();
		$("#vendorContact").append(output);
		$('#vendorContact').trigger("chosen:updated");
		if(vendorContactId == null || vendorContactId == '' || vendorContactId == 'undefined'){
		addConversionDiv();
		getProjectsList();
		}
	},
	});
	if(orgRelationId !=null && orgRelationId !='' && orgRelationId !='undefined'){
		$("#addExpense_Button").show();
		}else{
		$("#addExpense_Button").hide();	
		}
	}
</script>

<!-- Added By rajashekar -->

<div id="divInvoiceExpTd1" style="display: none; float: left;">
	<div class="tbldata-dotdot-big">        
	 <textarea placeholder="Description" class="form-control" rows="1" name="expenseInvoice[REPLACEROWID].description" id='REPLACEROWIDdescription' data-validation="required validate_length length1-100" field-name="Description"></textarea>     
	</div>
</div>

<div id="divInvoiceExpTd2" style="display: none; float: left;">
	<div class="tbldata-dotdot-100">     
	   <input type="text" class="form-control input-sm valid allownumericwithdecimal" placeholder="Quantity" name="expenseInvoice[REPLACEROWID].quantity" id='REPLACEROWIDquantity' onblur="totalHoursCalculateAmount(REPLACEROWID);" data-validation="required" field-name="Quantity">  
	 </div>
</div>

<div id="divInvoiceExpTd3" style="display: none; float: left;">
	<div class="tbldata-dotdot-100">     
	   <input type="text" class="form-control input-sm valid allownumericwithdecimal" placeholder="Rate" name="expenseInvoice[REPLACEROWID].rate" id='REPLACEROWIDrate' onblur="totalHoursCalculateAmount(REPLACEROWID);" data-validation="required" field-name="Rate">  
	 </div>
</div>

<div id="divInvoiceExpTd4" style="display: none; float: left;">
	<div class="tbldata-dotdot-vsm">  
	     <input type="text" class="form-control input-sm valid" placeholder="Total" name="expenseInvoice[REPLACEROWID].total" id='REPLACEROWIDtotal' readonly="readonly">   
     </div>
</div>

<div id="divInvoiceExpTd5" style="display: none; float: left;">
	<a class="text-danger" title="Remove row" onclick="removeExpenseInvoiceRow(REPLACEROWID);"><i class="fa fa-remove"></i></a>
</div>

<script>
	function addExpenseInvoiceRow(){  
				var checkMaxTsRowNum = $("#invoiceExpMaxRowNumber").val();
				var tbl = document.getElementById("invoiceExpTbl");
				var invoiceCreateType = $("#vendorOrgRelationId").find(':selected').attr("data-Bill");
				var maxRowNum = parseInt($("input[name = 'invoiceExpMaxRowNumber']").val());
				$("input[name = 'invoiceExpMaxRowNumber']").val(maxRowNum + 1);
				
				var row = tbl.insertRow(maxRowNum+1);
				row.setAttribute("id", "divInvoiceExpTsRow" + maxRowNum);
				
				var text = "";
				var cell0 = row.insertCell(0);
				text = text+"<div class='tbldata-dotdot-100'> ";
				text = text+"<input type='text' class='calendar form-control input-sm valid' id='"+maxRowNum+"edate'  name='expenseInvoice["+maxRowNum+"].edate'  placeholder='Date'>";   
				text = text+"</div>";
				cell0.innerHTML = text;
				
				var cell1 = row.insertCell(1);
				var divAttachmentTdStr = document.getElementById("divInvoiceExpTd1").innerHTML;
				divAttachmentTdStr = divAttachmentTdStr.replace(/REPLACEROWID/g, (maxRowNum));
				cell1.innerHTML = divAttachmentTdStr;
				
				var cell2 = row.insertCell(2);
				var divAttachmentTdStr = document.getElementById("divInvoiceExpTd2").innerHTML;
				divAttachmentTdStr = divAttachmentTdStr.replace(/REPLACEROWID/g, (maxRowNum));
				cell2.innerHTML = divAttachmentTdStr;
				
				var cell3 = row.insertCell(3);
				var divAttachmentTdStr = document.getElementById("divInvoiceExpTd3").innerHTML;
				divAttachmentTdStr = divAttachmentTdStr.replace(/REPLACEROWID/g, (maxRowNum));
				cell3.innerHTML = divAttachmentTdStr;
				
				var cell4 = row.insertCell(4);
				var divAttachmentTdStr = document.getElementById("divInvoiceExpTd4").innerHTML;
				divAttachmentTdStr = divAttachmentTdStr.replace(/REPLACEROWID/g, (maxRowNum));
				cell4.innerHTML = divAttachmentTdStr;
				
				var cell5 = row.insertCell(5);
				var divAttachmentTdStr = document.getElementById("divInvoiceExpTd5").innerHTML;
				divAttachmentTdStr = divAttachmentTdStr.replace(/REPLACEROWID/g, (maxRowNum));
				cell5.innerHTML = divAttachmentTdStr;
				
				
				$("#"+maxRowNum+"edate").datepicker({
					
					  changeMonth: true,
				      changeYear: true,
				      dateFormat : 'dd-mm-yy',
				      minDate: 0  ,
					  onSelect : function(selected) {
				 	  }
				    });
				
				$(".allownumericwithdecimal").on("keypress keyup blur",function (event) {
					 var v = this.value;
					   if($.isNumeric(v) === false) {
					        //chop off the last char entered
					        this.value = this.value.slice(0,-1);
					   }
					   $('input.quantity').bind('cut copy paste', function (e) {
						    e.preventDefault(); //disable cut,copy,paste
						});
					    });
				if(invoiceCreateType == "Individual"){
					  $("#addExpense_Button").prop("disabled",true);
				  }else{
					  $("#addExpense_Button").prop("disabled",false); 
				  }
	}			
</script>






<script>
function populateInlineItemsGrid(){
	var currencyCode = '${SourceLeadWeb_currencyCode}';
           var result = '';
           result = result +'<input type="hidden" name="invoiceExpMaxRowNumber"  id="invoiceExpMaxRowNumber" value="0" />';
            result = result +' <table width="100%" width="100%" cellpadding="0" cellspacing="0"  id="invoiceExpTbl">';
            result = result +' <thead>';
            result = result +' <tr>';
            result = result +' <th>Date</th>';
            result = result +' <th>Description</th>';
            result = result +' <th>Quantity</th>';
            result = result +' <th>Rate/Price</th>';
            result = result +' <th>Total</th>';
            result = result +' <th>Action</th>';
            result = result +' </tr>';
            result = result +' </thead>';
            result = result +' <tbody>';
            
		      result = result +'   <tr id="divInvoiceExpTsRow0">';
		      result = result +' </tr>';
          
        result = result +' <tfoot class="table_footer">';
        result = result +' <tr>';
        result = result +' <td colspan="4"></td>';
        result = result +'<td><strong>Sub Total</strong></td>';
        result = result +' <td><div class="tblminwidth-150"><span class="pull-left paddingRight">'+currencyCode+'</span> <input type="text" value = "00.00" id="inoviceTotal" name="inoviceTotal" class="ReadTextFli"></div></td>';
        result = result +'  <input type="hidden" value = "" id="hiddenTotal">';
        result = result +' </tr>';
        result = result +' </tfoot> ';
        result = result +' </tbody>';
        result = result +' </table>';
        $("#tableBody").empty();
        $("#tableBody").append(result);
        $("#currencyCode").html(currencyCode);
         $("#addbutton").show(); 
         document.getElementById("bottomGrid").style.display="block";
         document.getElementById("bottomGrid").style.display="block";
         document.getElementById("lineItemAddRows").style.display="block";
         document.getElementById("tableBody").style.display="block";
         addTaxDivs();  
}
</script>


<!-- Remove attachment Div Ids -->
<script type="text/javascript">
	function removeExpenseInvoiceRow(rowId) {
		if(rowId!=0)
		{
	$("input[name = 'invoiceExpMaxRowNumber']").val(rowId-1);
		 }
	     var rate = $("#"+rowId+"rate").val();
		 var quantity = $("#"+rowId+"quantity").val();
		 var subTotal = $("#inoviceTotal").val();
		 if(quantity != ''){
		     var totlalAmount = parseFloat(rate) * parseFloat(quantity);
		     var subtractTotal = subTotal - totlalAmount.toFixed(2)
		     $('#inoviceTotal').val(subtractTotal);
		     $("#hiddenTotal").val(subtractTotal);
		     
		 }
	    
	     var discountValue = $("#discountValue").val();
	     var discountType = $("#disCount").val();
	     var subtractValueFromRow = $("#hiddenTotal").val();
	    if(subtractValueFromRow != '' && subtractValueFromRow !='undefined'){
	    	 var total ='';
		 	 if(discountType == 'flat'){
		 		total = subtractValueFromRow-discountValue;
		 		$("#discountTotal").val(discountValue);
		 		$("#grandTotal").val(total);
		 		$("#conversionFrom").val(total);
		 		$("#afterDiscountTotal").val(total);
		 		
		 	  }
		 	  if(discountType == 'percentage'){
		 		var perCentValue = (subtractValueFromRow * discountValue)/100;
		 		total = subtractValueFromRow - perCentValue;
		 		$("#discountTotal").val(perCentValue);
		 		$("#grandTotal").val(total);
		 		$("#conversionFrom").val(total);
		 		$("#afterDiscountTotal").val(total);
		 	 }	 	
	    }
	       	 
	calculateTaxDiscount();
	
		 document.getElementById("divInvoiceExpTsRow"+rowId).style.display = 'none';
		 document.getElementById("divInvoiceExpTsRow"+rowId).value= '';
		 var  rId = "divInvoiceExpTsRow"+rowId;
		  var r = document.getElementById("invoiceExpTbl").rows[rId];
          var count = 2*(r.cells.length);
		    for(i=0;i<=count;i++){
		        r.deleteCell(0);
		        $("#addExpense_Button").prop('disabled', false);
		    } 
	}
</script>


<script>
function getExpenses(projectMemberId,expensesFrequency,expId){
	var memberId = $("#projectMemberId").val();
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	if(projectMemberId !=null && projectMemberId !='undefined' && expensesFrequency !=null && expensesFrequency !='undefined'){
		memberId = projectMemberId
	}
	var expenseId = 0;
	if(expId !=null && expId !='undefined' && expensesFrequency !=null && expensesFrequency !='undefined'){
		expenseId = expId;
	}
	$.ajax({
		type : "GET",
		url: vmsUrl+"/getExpensesByProjectMemberId?"+tokenParamName+"="+tokenValue+"&memberId="+memberId+"&expenseId="+expenseId+"&invoiceType=payable",
		success :function(json) {
			var output = "";
			output = output + "<option value=''>--Select Expenses--</option>";
			if(json.data !=null){
				for (var i=0; i<json.data.length; i++)
				{
				var opt = json.data[i];
				var selected = "";
				 if(expensesFrequency == opt.dateFrequency && expensesFrequency !=null && expensesFrequency != '' && expensesFrequency !='undefined'){
					selected = 'selected';
				} 
				output = output + "<option value='" +opt.id+ "' "+selected+">"+opt.dateFrequency+"</option>";
				}
			}
			$("#expensesId").empty();
			$("#expensesId").append(output);
			$('#expensesId').trigger("chosen:updated");
		},
		});	
}
</script>


<script>
function getExpensesDetails(expId){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var expensesId = $("#expensesId").val();
	if(expId !=null && expId !='undefined'){
		expensesId = expId
	}
	$.ajax({
		type : "GET",
		url: vmsUrl+"/getExpensesEntries?"+tokenParamName+"="+tokenValue+"&expensesId="+expensesId,
		success :function(json) {
			if(json.data !=null && json.data !=''){
			populateExpensesEntriesGrid(json.data);
			}else{
                alertMessageInExpense();
          }
		},
		});	
}
</script>



<script>
function populateExpensesEntriesGrid(data){
  var currencyCode = '${SourceLeadWeb_currencyCode}';
           var result = '';
           var subTotal = 0;
            result = result +'<input type="hidden" name="invoiceExpMaxRowNumber"  id="invoiceExpMaxRowNumber" value='+data.length+' />';
            result = result +' <table width="100%" width="100%" cellpadding="0" cellspacing="0"  id="invoiceExpTbl">';
            result = result +' <thead>';
            result = result +' <tr>';
            result = result +' <th>S.No</th>';
            result = result +' <th>Date</th>';
            result = result +' <th>Category</th>';
            result = result +' <th>Description/Price</th>';
            result = result +' <th>Amount</th>';
            result = result +' </tr>';
            result = result +' </thead>';
            result = result +' <tbody>';
            if(data !=null && data !=''){
            	 for(var i=0;i < data.length;i++){
                 	  var opt = data[i];
                 	  subTotal += opt.amount;
                 	  result = result +'   <tr>';
         		      result = result +'   <td>';
         		      result = result +'     <div class="tbldata-dotdot-100">'+i+'</div>';
         		      result = result +'   </td>';
         		      
         		      result = result +'   <td>';
         		      result = result +'     <div class="tbldata-dotdot-100">';
         		      if(opt.dateOccurred !=null && opt.dateOccurred != ''){
         		    	  result = result +'       <input type="text" class="form-control input-sm inpTextFil" value="'+opt.dateOccurred+'" id="'+i+'edate" name="expenseInvoice['+i+'].edate" placeholder="S.No">';	  
         		      }
         		      result = result +'     </div>';
         		      result = result +'   </td>';
         		      result = result +'   <td>';
         		      result = result +'     <div class="tbldata-dotdot-100">';
         		      if(opt.categoryName !=null && opt.categoryName != ''){
         		    	  result = result +'       <input type="text" class="form-control input-sm inpTextFil" value="'+opt.categoryName+'" id="'+i+'category" name="expenseInvoice['+i+'].category" placeholder="S.No">';	  
         		      }
         		      result = result +'     </div>';
         		      result = result +'   </td>';
         		      result = result +'   <td>';
         		      result = result +'     <div class="tbldata-dotdot-100">';
         		      if(opt.entryDescription !=null && opt.entryDescription != ''){
         		    	  result = result +'       <input type="text" class="form-control input-sm inpTextFil" value="'+opt.entryDescription+'" id="'+i+'description" name="expenseInvoice['+i+'].description" placeholder="S.No">';	  
         		      }
         		      result = result +'     </div>';
         		      result = result +'   </td>';
         		      
         		      result = result +'   <td>';
         		      result = result +'     <div class="tbldata-dotdot-100">';
         		      if(opt.categoryAmount !=null && opt.categoryAmount != ''){
         		    	  result = result +'       <input type="text" class="form-control input-sm inpTextFil" readonly="readonly" value="'+opt.categoryAmount+'" id="'+i+'total" name="expenseInvoice['+i+'].total" placeholder="S.No">';	  
         		      }
         		      result = result +'     </div>';
         		      result = result +'   </td>';
         		      
         		      result = result +' </tr>';
         		      
                 }	
            }
           
            
        result = result +' <tfoot class="table_footer">';
        result = result +' <tr>';
        result = result +' <td colspan="3"></td>';
        result = result +'<td><strong>Sub Total</strong></td>';
        result = result +' <td><div class="tblminwidth-150"><span class="pull-left paddingRight">'+currencyCode+'</span> <input type="text" value = '+subTotal+' id="inoviceTotal" name="inoviceTotal" class="ReadTextFli" readonly="readonly"></div></td>';
        result = result +'  <input type="hidden" value = "" id="hiddenTotal">';
        result = result +' </tr>';
        result = result +' </tfoot> ';
        result = result +' </tbody>';
        result = result +' </table>';
        $("#subTotal").val(subTotal);
        $("#grandTotal").val(subTotal);
        $("#tableBody").empty();
        $("#tableBody").append(result);
        maskme();
        $("#currencyCode").html(currencyCode);
         $("#addbutton").hide();
        document.getElementById("tableBody").style.display="block";
        addTaxDivs();   
        addConversionDiv();
        calculateDisCount();
}
</script>

<script type="text/javascript">
function alertMessageInExpense(){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>No Members Found</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancelPayableAlert();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelPayableAlert();'></b>";
	result = result +"	</div>";
	$("#expenseAlertPopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script type="text/javascript">
function cancelPayableAlert(){
	$("#expenseAlertPopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<script>
    function checkedvalue(status){
    	$("#invoiceforexpenses").prop("checked", true);
    	 document.getElementById("associate-expenses").style.display="block";
    	 $("#invoiceforexpenses").val('Y');
    	 document.getElementById("invoiceforexpenses").disabled = true;
    }
</script>

<!-- editExpensesInlineItems -->
<script>
function editExpensesInlineItems(invoiceLineItemsList){
	
	 for(var i=0; i<invoiceLineItemsList.length; i++){
		 var opt = invoiceLineItemsList[i];
		 
		    var checkMaxTsRowNum = $("#invoiceExpMaxRowNumber").val();
			var tbl = document.getElementById("invoiceExpTbl");
			var maxRowNum = parseInt($("input[name = 'invoiceExpMaxRowNumber']").val());
			$("input[name = 'invoiceExpMaxRowNumber']").val(maxRowNum + 1);
			
			$("#discountValue").removeAttr("disabled");
			
			
			var row = tbl.insertRow(maxRowNum+1);
			row.setAttribute("id", "divTsRow" + maxRowNum);
			
			var text = "";
			var cell0 = row.insertCell(0);
			text = text+"<div  class='inputCustom'>";
			text = text+"<input type='text' class='calendar form-control input-sm valid' id='"+maxRowNum+"edate'  name='expenseInvoice["+maxRowNum+"].edate'  placeholder='Date'>";   
			text = text+'</div>';
			cell0.innerHTML = text;
			
			var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
			   var finalDateFormatInvoiceEdit = '';
			  if(locationDate.match("yyyy-MM-dd")){
				  finalDateFormatInvoiceEdit = 'yy-mm-dd';
			  }if(locationDate.match("dd-MM-yyyy")){
				  finalDateFormatInvoiceEdit = 'dd-mm-yy'; 
			  }if(locationDate.match("MM-dd-yyyy")){
				  finalDateFormatInvoiceEdit = 'mm-dd-yy'; 
			  }if(locationDate.match("yyyy-dd-MM")){
				  finalDateFormatInvoiceEdit = 'yy-dd-mm'; 
			  } 
			
			$( "#"+maxRowNum+"edate" ).datepicker({
				  changeMonth: true,
			      changeYear: true,
			      dateFormat : finalDateFormatInvoiceEdit,
			      minDate: 0  ,
				  onSelect : function(selected) {
			 	  }
			    });
			
			$( "#"+maxRowNum+"edate" ).datepicker({
				  changeMonth: true,
			      changeYear: true,
			      dateFormat : finalDateFormatInvoiceEdit,
			      minDate: 0  ,
				  onSelect : function(selected) {
			 	  }
			    });
			
			
			var cell1 = row.insertCell(1);
			var divTsTd1Str = document.getElementById("divInvoiceExpTd1").innerHTML;
			divTsTd1Str = divTsTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
			divTsTd1Str = divTsTd1Str.replace(/PRICECALCULATION/g,("")); 
			cell1.innerHTML = divTsTd1Str;
			
			var cell1 = row.insertCell(2);
			var divTsTd2Str = document.getElementById("divInvoiceExpTd2").innerHTML;
			divTsTd2Str = divTsTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
			cell1.innerHTML = divTsTd2Str;
			
			var cell1 = row.insertCell(3);
			var divTsTd3Str = document.getElementById("divInvoiceExpTd3").innerHTML;
			divTsTd3Str = divTsTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
			cell1.innerHTML = divTsTd3Str;
			
			var cell1 = row.insertCell(4);
			var divTsTd4Str = document.getElementById("divInvoiceExpTd4").innerHTML;
			divTsTd4Str = divTsTd4Str.replace(/REPLACEROWID/g, (maxRowNum));
			cell1.innerHTML = divTsTd4Str;
			
			var cell1 = row.insertCell(5);
			var divTsTd5Str = document.getElementById("divInvoiceExpTd5").innerHTML;
			divTsTd5Str = divTsTd5Str.replace(/REPLACEROWID/g, (maxRowNum));
			cell1.innerHTML = divTsTd5Str;
			
		
			$('#'+maxRowNum+'edate').val(opt.startDateStr);
			
			$('#'+maxRowNum+'description').val(opt.inlineDescription);
			$('#'+maxRowNum+'quantity').val(opt.quantity);
			$('#'+maxRowNum+'rate').val(opt.rate);
			$('#'+maxRowNum+'total').val(opt.amount);
			
	 }

	 addConversionDiv();
}
</script>


<script>
function editExpensesDetailsEdit(data){
		   var currencyCode = '${SourceLeadWeb_currencyCode}';
           var result = '';
           var subTotal = '0'; 
            result = result +'<input type="hidden" name="invoiceExpMaxRowNumber"  id="invoiceExpMaxRowNumber" value='+data.length+' />';
            result = result +' <table width="100%" width="100%" cellpadding="0" cellspacing="0"  id="invoiceExpTbl">';
            result = result +' <thead>';
            result = result +' <tr>';
            result = result +' <th>S.No</th>';
            result = result +' <th>Date</th>';
            result = result +' <th>Category</th>';
            result = result +' <th>Description/Price</th>';
            result = result +' <th>Amount</th>';
            result = result +' </tr>';
            result = result +' </thead>';
            result = result +' <tbody>';
            if(data !=null && data !=''){
            	 for(var i=0;i < data.length;i++){
                 	  var opt = data[i];
                 	  result = result +'   <tr>';
                 	 result = result +' <input type="hidden" value='+opt.id+' id="'+i+'lineItemId"   name="'+(i+1)+'lineItemId">';
         		      result = result +'   <td>';
         		      result = result +'     <div class="tbldata-dotdot-100">'+(i+1)+'</div>';
         		      result = result +'   </td>';
         		      
         		      result = result +'   <td>';
         		      result = result +'     <div class="tbldata-dotdot-100">';
         		      if(opt.startDateStr !=null && opt.startDateStr != ''){
         		    	  result = result +'       <input type="text" class="form-control input-sm inpTextFil" value="'+opt.startDateStr+'" id="'+i+'edate" name="expenseInvoice['+i+'].edate" placeholder="S.No">';	  
         		      }
         		      result = result +'     </div>';
         		      result = result +'   </td>';
         		      result = result +'   <td>';
         		      result = result +'     <div class="tbldata-dotdot-100">';
         		      if(opt.expenseCategory !=null && opt.expenseCategory != ''){
         		    	  result = result +'       <input type="text" class="form-control input-sm inpTextFil" value="'+opt.expenseCategory+'" id="'+i+'category" name="expenseInvoice['+i+'].category" placeholder="S.No">';	  
         		      }
         		      result = result +'     </div>';
         		      result = result +'   </td>';
         		      result = result +'   <td>';
         		      result = result +'     <div class="tbldata-dotdot-100">';
         		      if(opt.inlineDescription !=null && opt.inlineDescription != ''){
         		    	  result = result +'       <input type="text" class="form-control input-sm inpTextFil" value="'+opt.inlineDescription+'" id="'+i+'description" name="expenseInvoice['+i+'].description" placeholder="S.No">';	  
         		      }
         		      result = result +'     </div>';
         		      result = result +'   </td>';
         		      result = result +'   <td>';
         		      result = result +'     <div class="tbldata-dotdot-100">';
         		      if(opt.amount !=null && opt.amount != ''){
         		    	  result = result +'       <input type="text" class="form-control input-sm inpTextFil" readonly="readonly" value="'+opt.amount+'" id="'+i+'total" name="expenseInvoice['+i+'].total" placeholder="S.No">';	  
         		      }
         		      result = result +'     </div>';
         		      result = result +'   </td>';
         		      
         		      result = result +' </tr>';
         		      
                 }	
            }
           
            
        result = result +' <tfoot class="table_footer">';
        result = result +' <tr>';
        result = result +' <td colspan="3"></td>';
        result = result +'<td><strong>Sub Total</strong></td>';
        result = result +' <td><div class="tblminwidth-150"><span class="pull-left paddingRight">'+currencyCode+'</span> <input type="text" id="inoviceTotal" name="inoviceTotal" class="ReadTextFli"></div></td>';
        result = result +'  <input type="hidden" value = "" id="hiddenTotal">';
        result = result +' </tr>';
        result = result +' </tfoot> ';
        result = result +' </tbody>';
        result = result +' </table>';
        $("#subTotal").val(subTotal);
        $("#tableBody").empty();
        $("#tableBody").append(result);
        $("#currencyCode").html(currencyCode);
         $("#addbutton").hide();
        document.getElementById("tableBody").style.display="block";
        addTaxDivs(); 
        addConversionDiv();
}
</script>

<script type="text/javascript">
function attachFile(id){
	fileUpload(""+id+"invoicedocFile", "invoiceDocuments", "project_payable_invoice_form","fileInfo");
}
</script>

<script>
function maskme(){

	$.mask.definitions['H'] = "[0-9]";
    $.mask.definitions['h'] = "[0-9]";
    $.mask.definitions['h'] = "[0-9]";
    $.mask.definitions['M'] = "[0-9]";
    $.mask.definitions['m'] = "[0-9]";
    $.mask.definitions['P'] = "[AaPp]";
    $.mask.definitions['p'] = "[Mm]";
    $(".inline_field").mask("Hhh:Mm"); 
	
}
</script>

<script>
$(document).ready(function() {  
	maskme();    
 });
</script>

<script>

function creatExpPayBilsEmptyVendorContact(){
	$("#vendorContact").empty();
	 $('#vendorContact').append('<option value="">--select Vendor contact--</option>');
   $('#vendorContact').trigger("chosen:updated");
}
</script>
<script>
	 function selectedVendor(){
		 var vmsUrl ='${SourceLeadWeb_vmsUrl}';
		 var vid =   $("#vendorOrgRelationId").val();
		 window.open(vmsUrl+"/addVendorContact?vid="+vid,'_blank');
	 }
</script>


<script>
function validateDiscount() {
	$('#discountValueDiv').parent().find('p.jquery_form_error_message').remove(); 
	 var selectedTextInvoice = document.getElementById("disCount").value;
	 var enteredValue = document.getElementById("discountValue").value;
	 
	 if(selectedTextInvoice =='percentage'){
		  var floatNum = /^(\d{0,2}(\.\d{0,2})?|100)$/
		  if(enteredValue.match(floatNum)){
			  $("#saveButtonId").removeAttr("disabled", "disabled");
				$("#sendButtonId").removeAttr("disabled", "disabled");
			   return true;
		  }		  
		 else{
				$("#discountValueDiv").after("<p class='jquery_form_error_message'><span>Enter Valid Percentage</span>");
				$("#saveButtonId").attr("disabled", "disabled");
				$("#sendButtonId").attr("disabled", "disabled");
				return false; 
		 	 }
	  }if(selectedTextInvoice =='flat'){
		  var numaricNum = /^[0-9]*$/
			  if(enteredValue.match(numaricNum)){
				  $("#saveButtonId").removeAttr("disabled", "disabled");
				  $("#sendButtonId").removeAttr("disabled", "disabled");
				  return true;
			  }else{
				  $("#discountValueDiv").after("<p class='jquery_form_error_message'><span>Enter Valid Discount</span>");
					$("#saveButtonId").attr("disabled", "disabled");
					$("#sendButtonId").attr("disabled", "disabled");
					return false;  
			  }
				  
			  }	  
}
</script>

<script>
function validateRowDiscount(maxRowNum) {
$('#'+maxRowNum+'discountRowDiv').parent().find('p.jquery_form_error_message').remove();
var selectedTextInvoice = $("#"+maxRowNum+"taxdiscountType").val();
var enteredValue = $("#"+maxRowNum+"taxdiscountvalue").val();

if(selectedTextInvoice =='percentage'){
	  var floatNum = /^(\d{0,2}(\.\d{0,2})?|100)$/
	  if(enteredValue.match(floatNum)){
		  $("#saveButtonId").removeAttr("disabled", "disabled");
			$("#sendButtonId").removeAttr("disabled", "disabled");
		   return true;
	  }		  
	 else{
			$("#"+maxRowNum+"discountRowDiv").after("<p class='jquery_form_error_message'><span>Enter Valid Percentage</span>");
			$("#saveButtonId").attr("disabled", "disabled");
			$("#sendButtonId").attr("disabled", "disabled");
			return false; 
	 	 }
}if(selectedTextInvoice =='flat'){
			$("#saveButtonId").removeAttr("disabled", "disabled");
			  $("#sendButtonId").removeAttr("disabled", "disabled");
	  }		
	  
 
}
</script>
<script>
function getPaymentAccountList(dbOrgRelationId,paymentAccoutId){
	var	orgRelationId = $("#vendorOrgRelationId").val();
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	if(orgRelationId == null){
		orgRelationId = dbOrgRelationId;
	}
	$.ajax({
		type : "GET",
		url: vmsUrl+"/getAllPaymentAccountList?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId,
		success :function(json) {
			var result = "";
			   result = result +"<option value=''>-- Select Account --</option>";
			   for (var i=0; i<json.length; i++)
				{
				  var report = json[i];
				  var selected = '';
					if((paymentAccoutId != null && paymentAccoutId != "" && paymentAccoutId !="undefined" && paymentAccoutId == report.id) || (report.defaultAccount == 1)){
						selected = 'selected';
					}
					if(report.accountType === 'WEPAY'){
						  result = result +'<option value='+report.id+' '+selected+'> '+report.email+'</option>';
					  }else{
						  result = result +'<option value='+report.id+' '+selected+'>'+report.accountNumber+' ('+report.bankName+')</option>';
					  }
				}
			   
			     $("#paymentAccountId").empty();
		         $("#paymentAccountId").append(result);
		}
	});
}
</script>

<script>
function clearInvoiceDate(){
	$("#invoiceDate").val("");
	};
	
function clearDueDate(){
	$("#dueDate").val("");
	};
</script>

 <jsp:include page="../masterFooter.jsp" />	