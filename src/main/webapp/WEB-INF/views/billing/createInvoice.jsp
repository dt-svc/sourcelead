<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<script src="/resources/js/jquery.mask.min.js"></script>
<script src="/resources/js/jquery.maskedinput-1.3.min.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	var invoiceUUID = '${invoiceUUID}';
	if(invoiceUUID != null && invoiceUUID != '' && invoiceUUID != 'undefined'){
		loadInvoiceDetails(invoiceUUID);
	}else{
		getClients();
		$("#invoiceId").val("");
		$("#paymentFrequency").val("");
		$("#paymentFrequency").trigger("chosen:updated");
		$("#dueDate").val("");
	}

	
});
</script>
<script>
function errRemoveClient(){
	$("#errClient").find("p.jquery_form_error_message").remove();
}
function errRemoveClientContact(){
	$("#errClientContact").find("p.jquery_form_error_message").remove();
}

</script>


<div class="side_mask">
	<div class="proj_close"></div>
</div>

<div id="popUp"></div>
<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Client Invoice</h2>
		</div>
		<sec:authorize access="hasAnyRole('ROLE_ADD_NEW_CONTRACT')">
		<div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/invoiceList'/>">Invoice List</a> &raquo; Client Invoice</div>
	</sec:authorize>
	<div class="success_pop" id="selector1" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
       </div>
	</section>
	<div class="content-area content-new clearfix">
		<div id="invoiceLoading"></div>
		<form:form id="invoice_form" name="invoice_form" method="post">
			<input type="hidden" name="clientOrgRelation" value="createInvoice" />
			<!-- <input type="hidden" name="clientContact" value="createInvoice"/> -->
			<div class="clearfix form-horizontal inner-content">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
						<div class="form-group">
							<label class="col-md-3">Client<span class="text-danger">&nbsp;*</span></label>
							<div class="col-md-9 select-field chooRadius errorInptgroup"
								id="errClient">
								<div class="input-group col-md-10 pull-left">

									<select class="chosen-select form-control"
										id="clientOrgRelationId" data-Invoice
										name="clientOrgRelationId"
										onchange="getClientContacts();errRemoveClient();getPaymentTerms();getInvoiceTemplateList();"
										data-validation="required" field-name="clientName">
									</select> <span class="input-group-addon cursorPointer bg-blue"
										onclick="getClients();createInvoiceEmptyClientContact();"><i
										class="fa fa-refresh "></i></span>
								</div>
								<sec:authorize access="hasAnyRole('ROLE_CREATE_CLIENT')">
									<span class="col-md-2 noPad clearfix"><a
										href="<c:url value="/app/client" />" target="_blank"><i
											class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a> </span>
								</sec:authorize>
							</div>
							<input type="hidden" name="savedInvoiceId" id="savedInvoiceId">
							<input type="hidden" name="clientCurrency" id="clientCurrency">
						</div>
						<div class="form-group">
							<label class="col-md-3">Client Contact<span
								class="text-danger">*</span></label>
							<div class="col-md-9 chooRadius errorInptgroup chosenmbNone"
								id="errClientContact">
								<div class="col-md-9 padding_right padding_left">
									<select class="chosen-select form-control" multiple="multiple"
										id="clientContact" name="clientContact"
										data-validation="required" field-name="Client Contact"
										onchange="errRemoveClientContact();">
									</select>
								</div>
								<span class="col-md-3 noPad clearfix"> <span
									class="btn btn-primary" onclick="getClientContacts();"><i
										class="fa fa-refresh "></i></span> <sec:authorize
										access="hasAnyRole('ROLE_CREATE_CLIENT_CONTACT')">
										<a href="javascript:void(0)" onclick="selectedClient();"><i
											class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a>
									</sec:authorize>
								</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3">Invoice Id<span
								class="text-danger">&nbsp;*</span></label>
							<div class="col-md-9" id="invoiceIdDiv">
								<input type="text" class="form-control" id="invoiceId"
									onkeyup="validateInvoiceId();" name="invoiceId" value=""
									placeholder="Invoice Id"
									data-validation="required validate_Alphanumeric validate_length length2-100"
									field-name="Invoice Id">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3">Invoice Date<span
								class="text-danger">&nbsp;*</span></label>
							<div class="col-md-9 select-field" id="errInvoiceDate">
								<div class="input-group">
									<input type="text" class="form-control calendar"
										id="invoiceDate" name="invoiceDate" value="${currentDate}"
										placeholder="Invoice Date" id="invoiceDate" readonly
										data-validation="required" field-name="Invoice Date">
									<span class="input-group-addon" onClick="clearInvoiceDate();"><i
										class="colorRed fa fa-times cursorPointer mr6"></i></span>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3">Payment terms</label>
							<div class="col-md-9" id="errPayment">
								<select class="chosen-select form-control" id="paymentFrequency"
									name="paymentFrequency" onchange="getDueDateByFrequency();">
									<option value="">Select frequency</option>
									<option value="10 days">10 days</option>
									<option value="15 days">15 days</option>
									<option value="30 days">30 days</option>
									<option value="45 days">45 days</option>
									<option value="60 days">60 days</option>
								</select>

							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3">Due Date<span class="text-danger">&nbsp;*
							</span><i class='fa fa-info-circle'
								title='The deadline for the invoice payment'></i></label>
							<div class="col-md-9 " id="errDuedate">
								<div class="input-group errorInptgroup">
									<input type="text" class="form-control calendar" id="dueDate"
										name="dueDate" value="" placeholder="Due Date" readonly
										data-validation="required" field-name="Due Date"> <span
										class="input-group-addon" onClick="clearDueDate();"><i
										class="colorRed fa fa-times cursorPointer mr6"></i></span>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3">Payment Account</label>
							<div class="col-md-9" id="projectMemberIdDiv">
								<select class="sel width100percnt" id="paymentAccountId"
									field-name="paymentAccountId Name" name="paymentAccount">
									<option value="">--Select Account--</option>
									<c:forEach items="${paymentAccountList}" var="p">
										<option value="${p.id}"
											${p.defaultAccount ? 'selected="selected"' : ""}>${p.accountType == "WEPAY" ? p.email.concat(" (WePay)") : p.accountNumber.concat(" (").concat(p.bankName).concat(")")}</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3">Invoice Template</label>
							<div class="col-md-9" id="invoiceTemplateIdDiv">
								<select class="sel width100percnt" id="invoiceTemplateId"
									field-name="invoiceTemplateId Name" name="invoiceTemplateId">
									<option value="0">--Select Invoice Template--</option>
								</select>
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="form-group">
							<label class="col-md-3">Invoice Address</label>
							<div class="col-md-9">
								<textarea class="form-control" rows="4" id="billingAddress"
									field-name="Invoice Address" name="billingAddress"
									placeholder="Invoice Address"></textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3">Notify To Others</label>
							<div class="col-md-9">
								<textarea class="form-control" rows="4" id="notifyToOthers"
									name="notifyToOthers" field-name="NotifyTo"
									placeholder="Enter multiple Email Id's separated by commas(,)"></textarea>
							</div>
							<div class="clearfix"></div>
						</div>

						<!-- <div class="form-group"> -->
						<!-- <label class="col-md-3">Account Details</label> -->
						<!-- <div class="col-md-9"> -->
						<!-- <textarea class="form-control" rows="2" id="accountDetails" -->
						<!-- name="accountDetails" placeholder="Account Details"></textarea> -->
						<!-- </div> -->
						<!-- </div> -->

					</div>
				</div>

				<div class="clearfix mt"></div>
				<div id="lineItemAddRows"></div>

				<div id="lineItemPopUp"></div>


				<footer class="form-footer">
					<div class="pull-right mr">
						<button type="button" id="saveButtonId"
							class="btn btn-sm btn-primary" onclick="saveInvoice('DRAFT')">Save</button>
						<button type="button" id="sendButtonId"
							class="btn btn-sm btn-success ml" onclick="saveInvoice('SENT')">Send</button>
						<button type="button" class="btn btn-sm btn-default ml"
							onClick="javascript:window.location.href = '<c:url value='${appPath}/invoiceList'/>'">Cancel</button>
					</div>
				</footer>
			</div>
		</form:form>
	</div>
</section>



<!-- ********************************************* Add Rows Invoice Table functionality start  ************************************************************ -->
<div id="divTaskTd2" style="display: none; float: left;">
	<div class="tbldata-dotdot-vsm select-field">
		<input type="text" class="form-control input-sm" readonly="readonly"
			id="REPLACEROWIDcandidateName" name="lineItems[REPLACEROWID].candidateName">
		</inmput>
		<input type="hidden" class="form-control input-sm"
			id="REPLACEROWIDcandidateId" name="lineItems[REPLACEROWID].candidate">
		</inmput>
		<input type="hidden" class="form-control input-sm"
			id="REPLACEROWIDlineItemId" name="lineItems[REPLACEROWID].lineItemId">
		</inmput>
		<input type= "hidden" class="form-control input-sm" id="REPLACEROWIDsubmissionInvoiceId" name = "lineItems[REPLACEROWID].submissionInvoiceId">
	</div>
</div>

<div id="divTaskTd3" style="display: none; float: left;">
	<div class="tbldata-dotdot select-field validationTxt">
		<textarea type="text" class="form-control input-sm" placeholder="Description" id="REPLACEROWIDDescription" name="lineItems[REPLACEROWID].Description"
			data-validation="required validate_length length1-100" field-name="Description"></textarea>
	</div>
</div>

<div id="divTaskTd4" style="display: none; float: left;">
	<div class="tbldata-dotdot-100 select-field">
		<select class="form-control input-sm" id="REPLACEROWIDsow" name="lineItems[REPLACEROWID].sow" onchange="getSowRate(REPLACEROWID);">
			<option value=''>-Select sow-</option>
		</select>
	</div>
</div>

<div id="divTaskTd5" style="display: none; float: left;">
	<div class="tbldata-dotdot_80 validationTxt whiteSpace">
		<input type="text"
			class="form-control input-sm allownumericwithdecimal"
			placeholder="Rate" id="REPLACEROWIDrate" name="lineItems[REPLACEROWID].rate"
			onblur="totalHoursCalculateAmount(REPLACEROWID);" data-validation="required validate_float" field-name="Rate">
	</div>
</div>

<div id="divTaskTd6" style="display: none; float: left;">
	<div class="tbldata-dotdot-100">
		<input type="text" class="form-control input-sm inlineField" placeholder="HHH:MM" data-validation = "required" field-name = "Hours" id="REPLACEROWIDtotalHours" name="lineItems[REPLACEROWID].totalHours" onblur="totalHoursCalculateAmount(REPLACEROWID);validationHours(REPLACEROWID);">
			<input type="hidden" id="REPLACEROWIDhiddenHours" name="REPLACEROWIDhiddenHours">
<!-- 			<i class=" text-info fa fa-info-circle" title="Before Assinging Team Project Start Date &amp; Manager is Mandatory"></i> -->
			<u id="REPLACEROWIDerrMsg" style="display: none;"><i class=" text-info fa fa-info-circle" >Avg Hours</i></u>
	</div>
</div>

<div id="divTaskTd7" style="display: none; float: left;">
	<div class="tbldata-dotdot-vsm text-right">
		<input type="text" name="lineItems[REPLACEROWID].amount" id="REPLACEROWIDamount"
			placeholder="00:00" class="ReadTextFli" readonly>
	</div>
</div>

<div id="divTaskTd8" style="display: none; float: left;">
	<a class="text-danger tblminwidth-50" title="Remove row"
		id="REPLACEROWIDremoveLineItem" onclick="removeRow(REPLACEROWID)"><i
		class="fa fa-remove"></i></a>
</div>


<script>
	function buildLineItemAndTaxAddRows(){
		var clientCurrency = $("#clientCurrency").val(); 
		var currencyCode = '${SourceLeadWeb_currencyCode}';
		 var result = '';
		 result = result +'  <div class="project_wrap_table tableI mt25 invoice_table table_custompadding" id="invoiceTable">';
		 result = result +'  <input type="hidden" id="maxTsRowNum" name="maxTsRowNum" value="0" />';
		 result = result +'  <table width="100%">';
		 result = result +'  <thead id="addInvoiceTbl">';
		 result = result +'  <tr>';
		 result = result +'  <th>Date</th>';
		 result = result +'  <th>Associate</th>';
		 result = result +'  <th>Description</th>';
		 result = result +'  <th>SOW</th>';
		 result = result +'  <th>Rate Hourly</th>';
		 result = result +'  <th>Total Hours</th>';
		 result = result +'  <th>Amount</th>';
		 result = result +'  <th><i class="fa fa-remove"></i></th>';
		 result = result +'  </tr>';
		 result = result +'  </thead>';
           
		 result = result +'  <tfoot class="table_footer">';
		 result = result +'  <tr>';
		 result = result +'  <td colspan="5"></td>';
		 result = result +'  <td><strong>Sub Total</strong></td>';
		 result = result +'  <td><div class="tblminwidth-150"><span class="pull-left paddingRight">'+currencyCode+'</span><input type="text" value = "00.00" id="inoviceTotal" name="inoviceTotal" class="ReadTextFli" readonly></div>';
		 result = result +'  <input type="hidden" value = "" id="hiddenTotal">';
		 result = result +'  <td>&nbsp;</td>';
		 result = result +'  </tr>';
		 result = result +'  </tfoot>';
		 result = result +'  </table>';
		 result = result +'  </div>';
         
         result = result +'  <div class="clearfix invoice_other mt">';
         result = result +'  <div class="col-md-5">';
         result = result +'  <div class="clearfix mb">';
         result = result +'  <button type="button" class="btn btn-sm btn-primary mr" id="addAssociateBtn" onclick="getAllAssociates();"><i class="fa fa-plus paddingRight"></i>Add</button>';
         result = result +'  </div>';
           
           
         result = result +'  <div class="new_side_wrap">';
         result = result +'  <div class="new_side_wrap_inn">';
         result = result +'  <div class="new_side_wrap_cta">';
         result = result +'  <div id="associatesListDiv"></div>';
         result = result +'  </div>';
         result = result +'  </div>';
         result = result +'  </div>';
         
         result = result +'  <div class="clearfix mb">';
         result = result +'  <label><strong>Message</strong></label>';
         result = result +'  <textarea class="form-control" rows="2" id="comment" field-name="Message" name="comment"></textarea>';
         result = result +'  </div>';
           
        
         result = result +'  <div class="clearfix mb invoiceAttachment">';
         result = result +'  <input type="hidden" name="invoiceAttcahmentMaxRowNumber"  id="invoiceAttcahmentMaxRowNumber" value="1" />';
         result = result +'  <table width="100%" width="100%" cellpadding="0" cellspacing="0"  id="invoiceAttachmentTbl">';
         result = result +'  <tbody>';
         result = result +'  <tr id="divAttachmentTsRow0">';
         result = result +'  <td>';
         result = result +'  <label class="pull-left mb10"><strong>Attachments</strong></label>';
         
         result = result +' <div id="timesheetDocumentsId">';
         result = result +' <div class="timesheet_week_comm invoiceDocs width100percnt">';
         result = result +' <span>';
         result = result +' <div id="savedAttachments"></div>';
         result = result +' </span>';
         result = result +' </div>';
         result = result +' </div>';
         
         result = result +'  <div class="timesheet_week_comm invoiceDocs invoiceDocs_add width100percnt" >';
         result = result +'  <div id="0invoicedocFileuploadFileGroup"> ';
         result = result +'  <span>';
         result = result +'  <input type="file"  id="0invoicedocFile" field-name="Attachment"  class="timesheetAttachmentValidation" onchange="attachFile(0);"><i class="fa fa-times mr6" mouseOver="Clear" title="Clear" onClick="remove()"></i><i class="fa fa-plus" mouseOver="Add" title="Add" onClick="addInvoiceAttachment()"></i>';
         result = result +'  </span> </div>';
         result = result +'  </div>';
         result = result +'  </td>';
         result = result +'  </tr>';
         result = result +'  </tbody>';
         result = result +'  </table>';
           
         result = result +'  </div>';
         result = result +'  </div>';
         
         
         result = result +'  <div class="col-md-7">';
         result = result +'  <div class="form-horizontal">';
             
             
         result = result +'  <div class="form-group">';
         result = result +'  <div class="col-md-7 select-field">';
         result = result +'  <div class="row">';
         result = result +'  <div class="col-md-7 paddingRight">';
         result = result +'  <select class="form-control input-sm" id="disCount" name="disCount" onchange="calculateDiscount();validateDiscount();">';
         result = result +'  <option value="percentage">Discount %</option>';
         result = result +'  <option value="flat">Discount Value</option>';
         result = result +'  </select>';
         result = result +'  </div>';
         result = result +'  <div class="col-md-5 paddingLeft" id="discountValueDiv">';
         result = result +'  <input type="text" class="form-control input-sm allownumericwithdecimal" id="discountValue" disabled="disabled" name="discountValue" placeholder="Enter value" onblur="calculateDiscount();"  onkeyup="validateDiscount();">';
         result = result +'  </div>';
         result = result +'  </div>';
         result = result +'  </div>';
         result = result +'  <div class="col-md-5 text-right">';
         result = result +'  <input type="text" class="form-control input-sm" id="discountTotal" name="discountTotal" placeholder="00.00" readonly>';
         result = result +'  </div>';
         result = result +'  </div>';
         result = result +'  <div class="form-group">';
         result = result +'  <label class="col-md-7 text-right"><strong>After Discount Total</strong></label>';
         result = result +'  <div class="col-md-5 text-right"><span class="pull-left">'+currencyCode+'</span><input type="text"  id="afterDiscountTotal" name="afterDiscountTotal"  value="00.00" class="ReadTextFli" readonly="readonly"></div>';
         result = result +'  </div>';
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
                   result = result +'  <div class="form-group mt">';
                   result = result +'  <label class="col-md-7 text-right"><strong>Total</strong></label>';
                   result = result +'  <div class="col-md-5 text-right"><span class="pull-left">'+currencyCode+'</span><input type="text" value = "00.00" id="grandTotal" name="grandTotal" class="ReadTextFli" readonly></div>';
                   result = result +'  </div>';
                 if(clientCurrency != currencyCode){ 
                	 result = result +'  <div class="row">';
                     result = result +'  <div class="clearfix mt col-md-offset-2 col-md-10" style="padding-right:0;">';
                     
                     result = result +'  <div class="clearfix"><label class="col-md-6 text-right"><strong>Conversion From</strong></label><div class="paddingLeft col-md-6"><span id="loginCurrencyCode" class="pull-left">'+currencyCode+'</span><div class="pull-right"><input type="text" id="conversionFrom" placeholder="00:00" class="ReadTextFli" readonly="" type="text"></div></div></div>';
                     
                      result = result +'  <div class="clearfix"><label class="col-md-6 text-right"><strong>Conversion To</strong></label><div  class="paddingLeft col-md-6"><span id="vendorCurrencyCode">'+clientCurrency+'</span><div id="exchangeCurrency" class="pull-right"><input  class="ReadTextFli" readonly="" type="text"></div></div></div>';
  	                result = result +'  <div class="clearfix"><label class="col-md-6 text-right"><strong>Exchange Rate</strong></label><div  class="paddingLeft col-md-6"><span id="convertedCurrencyCode">'+currencyCode+' to '+clientCurrency+'</span><div id="exchangeRate" class="pull-right"><input class="ReadTextFli ReadTextFli90" readonly="" type="text"></div></div></div>';
  	                  
                     
                     result = result +'  </div>';
                     result = result +'  </div>'; 
                 }
                   
                   result = result +'  </div>';
                   result = result +'  </div>';
                   result = result +'  </div>';
                   $("#lineItemAddRows").empty();
                   $("#lineItemAddRows").append(result);
                   var invoiceUUID = '${invoiceUUID}';
                   if($("#clientOrgRelationId").find(':selected').attr("data-Invoice") == 'Individual' && invoiceUUID != null && invoiceUUID != "" && invoiceUUID !="undefined"){
              			$("#addAssociateBtn").prop("disabled", true);
              		}else{
              			$("#addAssociateBtn").prop('disabled', false);
              		}
                   maskme();
		
	}
	
	</script>
	
<script>
function remove(){
	$('#0invoicedocFile').val("");
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
		  $("#saveButtonId").removeAttr("disabled", "disabled");
		  return true;
		  }else{
		  $("#discountValueDiv").after("<p class='jquery_form_error_message'><span>Enter Valid Discount</span>");
		  $("#saveButtonId").attr("disabled", "disabled");
		  $("#saveButtonId").attr("disabled", "disabled");
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
	/* var floatNum = /^[0-9]*$/
		if(enteredValue.match(floatNum)){
			  $("#saveButtonId").attr("disabled", "disabled");
				$("#sendButtonId").attr("disabled", "disabled");
			   return false;
		  }
		else{ */
			$("#saveButtonId").removeAttr("disabled", "disabled");
			  $("#sendButtonId").removeAttr("disabled", "disabled");
// 		}
	  }		
	  
 
}
</script>

<script>
	function loadInvoiceDetails(invoiceUUID){
		 
		 $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/getInvoiceDetailsByInvoiceUUID?"+tokenParamName+"="+tokenValue+"&invoiceUUID="+invoiceUUID, 
		         success: function(data) {
		        	 var json = JSON.parse(data);
		        	 var result = '';
		        	 if(json.paymentAccountId > 0) {
		        		 $("#paymentAccountId").val(json.paymentAccountId);
		        	 }
		        	 
		        	 var selected = 'selected';
		        	 result = result +'<option disabled selected>--Select Client--</option>';
		        	 result = result +'<option value='+json['vendorOrgRelationId']+' '+selected+' data-Invoice ='+json['generateInvoice']+' data-responder_location_id= '+json['dataResponderLocationId']+'>'+json['orgName']+'</option>';
		        	 $("#clientOrgRelationId").empty();
		           	 $("#clientOrgRelationId").append(result); 
		           	 $('#clientOrgRelationId').trigger("chosen:updated"); 
		           	 $('#clientCurrency').val(json['clientCurrency']);
		           
		           	/* getClientContacts(json['vendorContactId']); */
		           	//alert(json['contactUsers']);
		           	//var clientContact = json['contactUsers'];
		           	getClientContacts(json['contactUsers']);
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
		           	
		           	/* if(json['accountDetails'] != null && json['accountDetails'] != '' && json['accountDetails'] != 'undefined'){
		           		$("#accountDetails").val(json['accountDetails']);
		           	} */
		           	buildLineItemAndTaxAddRows();
		           	
		           	editInvoice(json['invoiceLineItemsList']);
		        	$("#inoviceTotal").val(json['subTotal']);
		        	$("#disCount").val(json['discountType']);
		        	$("#discountValue").val(json['discountAmount']);
		        	$("#discountTotal").val(json['discountValue']);
		        	$("#comment").val(json['invoiceComment']);
		        	$("#afterDiscountTotal").val(json['afterDiscountValue']);
		        	
		        	editTax(json['invoiceTaxList'])
		        	
		        	getSavedAttachments(json['invoiceDocumentsList']);
		        	
		        	$("#grandTotal").val(json['total']);
		        	$("#conversionFrom").val(json['total']);
		        	 $("#exchangeCurrency").html(json['convertAmount']);
		        	 $("#exchangeRate").html(json['exchangeRate']);
		        	  
		        	 
		        	$("#savedInvoiceId").val(json['id']);
		        	
		        	/* if(json.customInvoiceId > 0 && json.customInvoiceName != "") {
		        		$("#invoiceTemplateId").empty();
		        		$("#invoiceTemplateId").append("<option value='0'>-- Select Invoice Template --</option>");
		    			$("#invoiceTemplateId").append("<option value='-1' selected>Default Template</option>");
		    			$("#invoiceTemplateId").append("<option value='" + json.customInvoiceId + "' selected>" + json.customInvoiceName + "</option>");
		        	} */
		        	
		        	getInvoiceTemplateList(json.customInvoiceId);
		        	
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
		 if($("#clientOrgRelationId").find(':selected').attr("data-Invoice") == 'Individual'){
  			$("#addAssociateBtn").prop('disabled', true);
  		}else{
  			$("#addAssociateBtn").prop('disabled', false);
  		}
		 
	}
	</script>


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
		}else{
			
			 <c:forEach var="doc" items="${documents}">
			 result = result +"<strong id='invoiceDocument${doc.id}'>";
    		 result = result +"<i class=''><a class='glyphicon glyphicon-save' href=\'${doc.fileUrl}\' title='download' download></a></i>";
			 result = result +"<input type='button' value='${doc.fileName}'>";
			 result = result +"</a>";
			 result = result +"</strong>";
     		</c:forEach>
     		
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
		result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteInvoiceDocument("+id+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelDelDoc();'></b>";
		result = result +"	</div>";
		$("#lineItemPopUp").append(result);
		$("#remove_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
   }
	
	
	function deleteInvoiceDocument(documentId){
		
		if(documentId){
			$.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/deleteInvoiceDocumentBydocumentId?"+tokenParamName+"="+tokenValue+"&documentId="+documentId, 
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
	function removeRow(rowId) {
		 var rate = $("#"+rowId+"rate").val();
		 var totalHours = $("#"+rowId+"totalHours").val();
		 var subTotal = $("#inoviceTotal").val();
		 if(totalHours != ''){
			 var split = totalHours.split(':');
		     var hours = split[0];
	 	     var minute = split[1];
			 var hourminits = hours * 60;
			 var finalmin = parseInt(hourminits) + parseInt(minute);
		     var perMinit = rate / 60;
		     var totlalAmount = (perMinit * finalmin);
		     var subtractTotal = subTotal - totlalAmount.toFixed()
		     $('#inoviceTotal').val(subtractTotal);
		     $("#hiddenTotal").val(subtractTotal);
		     
		 }
	    
	     //==============
	     var discountValue = $("#discountValue").val();
	     var discountType = $("#disCount").val();
	     var subtractValueFromRow = $("#hiddenTotal").val();
	    if(subtractValueFromRow != ''){
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
	
	
		    var count = 0;
		    var selectedAssociates = '';
			var maxTsRowNum = parseInt($("input[name = 'maxTsRowNum']").val());
			  if(maxTsRowNum > 1){
				  for(var i=1; i<maxTsRowNum; i++){
					  if(document.getElementById(i+"candidateId")){
						  var associateId = document.getElementById(i+"candidateId").value;
						  if(selectedAssociates == ''){
							  selectedAssociates = associateId;
							  count++;
						  }else{
							  selectedAssociates = selectedAssociates+","+associateId;
							  count++
						  } 
					  }
				  }
			  }
			  
			  if(count == 1){
				  $("#discountValue").val('');
				  $("#discountValue").attr("disabled", "disabled");
			  }
		document.getElementById('divTsRow' + rowId).style.display = 'none';
		var r = document.getElementById('addInvoiceTbl').rows[rowId+1];
		var count = r.cells.length;
		for (i = 0; i <= count; i++) {
			r.deleteCell(0);
			if($("#addInvoiceTbl").find("td").length == 0){
				$("#addAssociateBtn").prop('disabled', false);
			}
		}
	}
</script>


<script type="text/javascript">
	function editInvoice(invoiceLineItemsList) {
		 for(var i=0; i<invoiceLineItemsList.length; i++){
			 var opt = invoiceLineItemsList[i];
			 
			    var checkMaxTsRowNum = $("#maxTsRowNum").val();
				var tbl = document.getElementById("addInvoiceTbl");
				var maxRowNum = parseInt($("input[name = 'maxTsRowNum']").val());
				$("input[name = 'maxTsRowNum']").val(maxRowNum + 1);
				
				$("#discountValue").removeAttr("disabled");
				
				
				var row = tbl.insertRow(maxRowNum+1);
				row.setAttribute("id", "divTsRow" + maxRowNum);
				
				var text = "";
				var cell0 = row.insertCell(0);
				text = text+"<div  class='inputCustom'>";
				text = text+'<input type="text" class="calendar paddingRight" id="'+maxRowNum+'sdate-input" name="lineItems['+maxRowNum+'].invoiceStartDate" placeholder="Start Date">';
				text = text+'<input type="text" class="calendar paddingLeft" id="'+maxRowNum+'edate-input" name="lineItems['+maxRowNum+'].invoiceEndDate" placeholder="End Date">';
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
				
				$( "#"+maxRowNum+"sdate-input" ).datepicker({
					  changeMonth: true,
				      changeYear: true,
				      dateFormat : finalDateFormatInvoiceEdit,
				      minDate: 0  ,
					  onSelect : function(selected) {
						  getEstimatedHours(maxRowNum);
				 	  }
				    });
				
				$( "#"+maxRowNum+"edate-input" ).datepicker({
					  changeMonth: true,
				      changeYear: true,
				      dateFormat : finalDateFormatInvoiceEdit,
				      minDate: 0  ,
					  onSelect : function(selected) {
						  getEstimatedHours(maxRowNum);
				 	  }
				    });
				
				
				var cell1 = row.insertCell(1);
				var divTsTd2Str = document.getElementById("divTaskTd2").innerHTML;
				divTsTd2Str = divTsTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
				divTsTd2Str = divTsTd2Str.replace(/PRICECALCULATION/g,("")); 
				cell1.innerHTML = divTsTd2Str;
				
				var cell1 = row.insertCell(2);
				var divTsTd10Str = document.getElementById("divTaskTd3").innerHTML;
				divTsTd10Str = divTsTd10Str.replace(/REPLACEROWID/g, (maxRowNum));
				cell1.innerHTML = divTsTd10Str;
				
				var cell1 = row.insertCell(3);
				var divTsTd3Str = document.getElementById("divTaskTd4").innerHTML;
				divTsTd3Str = divTsTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
				cell1.innerHTML = divTsTd3Str;
				
				var cell1 = row.insertCell(4);
				var divTsTd4Str = document.getElementById("divTaskTd5").innerHTML;
				divTsTd4Str = divTsTd4Str.replace(/REPLACEROWID/g, (maxRowNum));
				cell1.innerHTML = divTsTd4Str;
				
				var cell1 = row.insertCell(5);
				var divTsTd5Str = document.getElementById("divTaskTd6").innerHTML;
				divTsTd5Str = divTsTd5Str.replace(/REPLACEROWID/g, (maxRowNum));
				cell1.innerHTML = divTsTd5Str;
				
				
				var cell1 = row.insertCell(6);
				var divTsTd6Str = document.getElementById("divTaskTd7").innerHTML;
				divTsTd6Str = divTsTd6Str.replace(/REPLACEROWID/g, (maxRowNum));
				cell1.innerHTML = divTsTd6Str;
				
				var cell1 = row.insertCell(7);
				var divTsTd7Str = document.getElementById("divTaskTd8").innerHTML;
				divTsTd7Str = divTsTd7Str.replace(/REPLACEROWID/g, (maxRowNum));
				cell1.innerHTML = divTsTd7Str;
				
				$('#'+maxRowNum+'sdate-input').val(opt.startDateStr);
				$('#'+maxRowNum+'edate-input').val(opt.endDateStr);
				
				$('#'+maxRowNum+'candidateId').val(opt.candidateId);
				$('#'+maxRowNum+'candidateName').val(opt.candidateName);
				$('#'+maxRowNum+'Description').val(opt.inlineDescription);
				$('#'+maxRowNum+'lineItemId').val(opt.id);
				
				$('#'+maxRowNum+'rate').val(opt.rate);
				$('#'+maxRowNum+'totalHours').val(opt.totalHours);
				$('#'+maxRowNum+'amount').val(opt.amount);
				
				editHoursValidations(opt.startDateStr,opt.endDateStr,maxRowNum);
	 			getSow(opt.candidateId,maxRowNum,opt.contractId);
				maskme();  
		 }
		
	}
  </script>


<script type="text/javascript">
function cancelDelDoc(){
	$("#lineItemPopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>



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
					maskme();
					
					$('#'+maxRowNum+'taxType').val(opt.taxName);
					$('#'+maxRowNum+'taxdiscountType').val(opt.taxType);
					$('#'+maxRowNum+'taxdiscountvalue').val(opt.taxValue);
					$('#'+maxRowNum+'taxTotal').val(opt.taxAmount);
					$('#'+maxRowNum+'invoiceTaxId').val(opt.id);
					
		}
	}			
</script>




<script type="text/javascript">
	function addInvoice() {
		
		var oTable = $('#invoiceAssociatesList').dataTable();
		var rowcollection =  oTable.$("input[name='associates[]']:checked", {"page": "all"});
		
		   var internalOutput = "";
		   rowcollection.each(function(index,elem){
			   
		   var checkbox_value = $(elem).val().split(",");
		   
			var checkMaxTsRowNum = $("#maxTsRowNum").val();
			var tbl = document.getElementById("addInvoiceTbl");
			var maxRowNum = parseInt($("input[name = 'maxTsRowNum']").val());
			$("input[name = 'maxTsRowNum']").val(maxRowNum + 1);
			
			$("#discountValue").removeAttr("disabled");
			
			
			var row = tbl.insertRow(maxRowNum+1);
			row.setAttribute("id", "divTsRow" + maxRowNum);
			
			var text = "";
			var cell0 = row.insertCell(0);
			text = text+"<div  class='inputCustom'>";
			text = text+'<input type="text" class="calendar paddingRight" id="'+maxRowNum+'sdate-input" name="lineItems['+maxRowNum+'].invoiceStartDate" placeholder="Start Date">';
			text = text+'<input type="text" class="calendar paddingLeft" id="'+maxRowNum+'edate-input" name="lineItems['+maxRowNum+'].invoiceEndDate" placeholder="End Date">';
			text = text+'</div>';
			cell0.innerHTML = text;
			
			var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
			   var finalDateFormatInvoiceSave = '';
			  if(locationDate.match("yyyy-MM-dd")){
				  finalDateFormatInvoiceSave = 'yy-mm-dd';
			  }if(locationDate.match("dd-MM-yyyy")){
				  finalDateFormatInvoiceSave = 'dd-mm-yy'; 
			  }if(locationDate.match("MM-dd-yyyy")){
				  finalDateFormatInvoiceSave = 'mm-dd-yy'; 
			  }if(locationDate.match("yyyy-dd-MM")){
				  finalDateFormatInvoiceSave = 'yy-dd-mm'; 
			  } 
			  
			$( "#"+maxRowNum+"sdate-input" ).datepicker({
				  changeMonth: true,
			      changeYear: true,
			      dateFormat : finalDateFormatInvoiceSave,
				  onSelect : function(selected) {
					  var dt = $(this).datepicker('getDate', '+1d');
			            dt.setDate(dt.getDate());
			            $("#"+maxRowNum+"edate-input").datepicker("option", "minDate", dt);
			            getEstimatedHours(maxRowNum);
			            
					  /* var dt = toDate("#"+maxRowNum+"sdate-input");
						dt.setDate(dt.getDate() + 1);
						$("#"+maxRowNum+"edate-input").datepicker("option", "minDate", dt); */
			 	  }
			    });
			
			$( "#"+maxRowNum+"edate-input" ).datepicker({
				  changeMonth: true,
			      changeYear: true,
			      dateFormat : finalDateFormatInvoiceSave,
				  onSelect : function(selected) {
					  
					  var dt = $(this).datepicker('getDate', '-1d');
			            dt.setDate(dt.getDate());
			            $("#"+maxRowNum+"sdate-input").datepicker("option", "maxDate", dt);
			            getEstimatedHours(maxRowNum);
					 /*  var dt = toDate("#"+maxRowNum+"edate-input");
						dt.setDate(dt.getDate() - 1);
						$("#"+maxRowNum+"sdate-input").date
						picker("option", "maxDate", dt); */
			 	  }
			    });
			
			
			var cell1 = row.insertCell(1);
			var divTsTd2Str = document.getElementById("divTaskTd2").innerHTML;
			divTsTd2Str = divTsTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
			divTsTd2Str = divTsTd2Str.replace(/PRICECALCULATION/g,("")); 
			cell1.innerHTML = divTsTd2Str;
			
			var cell1 = row.insertCell(2);
			var divTsTd10Str = document.getElementById("divTaskTd3").innerHTML;
			divTsTd10Str = divTsTd10Str.replace(/REPLACEROWID/g, (maxRowNum));
			cell1.innerHTML = divTsTd10Str;
			
			var cell1 = row.insertCell(3);
			var divTsTd3Str = document.getElementById("divTaskTd4").innerHTML;
			divTsTd3Str = divTsTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
			cell1.innerHTML = divTsTd3Str;
			
			var cell1 = row.insertCell(4);
			var divTsTd4Str = document.getElementById("divTaskTd5").innerHTML;
			divTsTd4Str = divTsTd4Str.replace(/REPLACEROWID/g, (maxRowNum));
			cell1.innerHTML = divTsTd4Str;
			
			var cell1 = row.insertCell(5);
			var divTsTd5Str = document.getElementById("divTaskTd6").innerHTML;
			divTsTd5Str = divTsTd5Str.replace(/REPLACEROWID/g, (maxRowNum));
			cell1.innerHTML = divTsTd5Str;
			
			
			var cell1 = row.insertCell(6);
			var divTsTd6Str = document.getElementById("divTaskTd7").innerHTML;
			divTsTd6Str = divTsTd6Str.replace(/REPLACEROWID/g, (maxRowNum));
			cell1.innerHTML = divTsTd6Str;
			
			var cell1 = row.insertCell(7);
			var divTsTd7Str = document.getElementById("divTaskTd8").innerHTML;
			divTsTd7Str = divTsTd7Str.replace(/REPLACEROWID/g, (maxRowNum));
			cell1.innerHTML = divTsTd7Str;
			

			$('#'+maxRowNum+'candidateId').val(checkbox_value[0]);
			$('#'+maxRowNum+'candidateName').val(checkbox_value[1]);
			getSow(checkbox_value[0],maxRowNum); 
			maskme(); 
			
		   });
		
		
	    $(".new_side_wrap").removeClass("new_side_wrap_active");
 		$(".side_mask").fadeOut(200);
 		$("body").css('overflow-y', 'auto')
 		if(($("#addInvoiceTbl").find("td").length != 0) && ($("#clientOrgRelationId").find(':selected').attr("data-Invoice") == 'Individual')){
 			$("#addAssociateBtn").prop('disabled', true);
 		}else{
 			$("#addAssociateBtn").prop('disabled', false);
 		}
 		
	}
  </script>
<div id="allowOneAssociatePopUp"></div>
<script>
function allowOneAssociate(){ 
	var result = "";
	$("#allowOneAssociatePopUp").empty();
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"<div class='modal-body'><p>Allow only One Associate</p></div>";
	result = result + ' <div id="allowOneAssociateDiv"/>';
	result = result +'		<b href="javascript:void(0)"><input class="u_can_color width100percnt" type="button"  value="Ok" onClick="cancelPopup();"></b>';
	result = result +"	</div>";
	$("#allowOneAssociatePopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function cancelPopup(){
	$("#allowOneAssociatePopUp").empty();
	 $(".project_inn_mask").fadeOut(200);
}
</script>
<script>

  function getAllAssociates(){
	  var maxTsRowNum = parseInt($("input[name = 'maxTsRowNum']").val());
	  var orgRelationId = $("#clientOrgRelationId").val();
	  var invoiceCreateType = $("#clientOrgRelationId").find(':selected').attr("data-Invoice");
	  if(invoiceCreateType == "Individual"){
		  allowOneAssociate();
	  }
	  var selectedAssociates = '';
	  if(maxTsRowNum > 0){
		  for(var i=0; i<maxTsRowNum; i++){
			  if(document.getElementById(i+"candidateId")){
				  var associateId = document.getElementById(i+"candidateId").value;
				  if(selectedAssociates == ''){
					  selectedAssociates = associateId;
				  }else{
					  selectedAssociates = selectedAssociates+","+associateId;
				  } 
			  }
		  }
	  }
	  
	  $('.action-pop').slideUp();
	   $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getInvoiceAssociatesList?"+tokenParamName+"="+tokenValue+"&selectedAssociates="+selectedAssociates+"&orgRelationId="+orgRelationId+"&contractType=clientSow",
	        success: function(json) {
	        	
	        	if(json.data != null && json.data != 'undefined' && json.data != "" && json.data != '[]'){
	        		
	        		
	        		    var result = '';
	        		    result = result +'     <h3>Associates List</h3>';
	             		result = result +'     <div class="modal-bodyCustom clearfix popupFilter clients_wrap_table addinvoiceTable">'; 
	             	    result = result +'       <table id="invoiceAssociatesList" class="table table-bordered table-striped  clearfix" cellspacing="0" width="100%" >';
	             		result = result +'           <thead>';
	             		result = result +'           <tr>';
	             		if(invoiceCreateType == "Bulk"){
	             		result = result +'             <th><input type="checkbox" id="checkAllAssociates" onclick="toggleAssociates(this);"></th>';
	             		}else if(invoiceCreateType == "Individual"){
		             		result = result +'             <th></th>';
	             		}
	             		result = result +'             <th>Associate Name</th>';
	             		result = result +'             <th>Email</th>';
	             		result = result +'             <th>Phone</th>';
	             		result = result +'           </tr>';
	             		result = result +'           </thead>';
	                    result = result +"<tbody>"; 
	                    
// 	                    var json = JSON.parse(data);
	                    
	                    for (var i = 0; i < json.data.length; i++) { 
	                    	 var opt = json.data[i]; 
	                    	 var mobilephone = '';
	                    	 var associateName
	                    	 
	                    	 if(opt.lastName != null && opt.lastName != '' && opt.lastName !='undefined'){
	                    		 associateName = opt.firstName+" "+opt.lastName;
	 		        		}else{
	 		        			associateName = opt.firstName;
	 		        		}
	                    	 
	                    	 if(opt.mobilePhone != null && opt.mobilePhone != 'undefined' && opt.mobilePhone != ""){
	                    		 mobilephone = opt.mobilePhone;
	                    	 }else{
	                    		 mobilephone = "&nbsp;&nbsp;-----------";
	                    	 }
	                    	 
	                    	 
	                    	 result = result +'<tr>';
	                    	 if(invoiceCreateType == "Bulk"){
	                    	 result = result +'<td><input id="'+opt.id+'" value="'+opt.id+','+associateName+'"  type="checkbox" name="associates[]" class="myCheckbox" >';
	                    	 }else if(invoiceCreateType == "Individual"){
		                     result = result +'<td><input id="'+opt.id+'" value="'+opt.id+','+associateName+'"  type="radio" name="associates[]" class="myCheckbox" >';
	                    	 }
	                    	 result = result +'<td>'+associateName+'</td>';
	                    	 result = result +'<td>'+opt.email+'</td>';
	                    	 result = result +'<td>'+mobilephone+'</td>';
	                    	 result = result +'</tr>';
	                    }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     result = result +'     </div>'; 
	                 	 result = result +'     <div class="clearfix">';
	                 	
	                 	 result = result +'       </div>'; 
	                 	 result = result +'   </div>'; 
	                 	 result = result +'   <div class="modal-footer">';
	                 	 result = result +'     <button data-dismiss="modal" class="btn btn-primary" type="button" onClick="this.disabled=true;addInvoice();$(this).blur();" >Add</button>';
	                 	 result = result +'   </div>'; 
	                 	 $("#associatesListDiv").empty(); 
	                     $("#associatesListDiv").append(result); 
	                     pagination(); 
	        		
	        	}else{
	        		
	        		var result ="";
	        		result = result +'     <h3>Associates List</h3>';
 	           		result = result +'     <div class="modal-bodyCustom clearfix popupFilter clients_wrap_table">'; 
 	           	    result = result +'       <table id="invoiceAssociatesList" class="table table-bordered table-striped  clearfix" cellspacing="0" width="100%" >';
 	           		result = result +'           <thead>';
 	           		result = result +'           <tr>';
 	           		result = result +'             <th><input type="checkbox" id="checkAll" onclick="toggle(this);"></th>';
	         		result = result +'             <th>Associate Name</th>';
	         		result = result +'             <th>Email</th>';
	         		result = result +'             <th>Phone</th>';
 	           		result = result +'           </tr>';
 	           		result = result +'           </thead>';
 	        		result = result +'           </table>';
 	        		result = result +'     </div>';
 	        		result = result +'     </div>';
 					$("#associatesListDiv").empty();
 				    $("#associatesListDiv").append(result); 
 				    pagination();  
	        	}
	        },
	        
	    }); 
	    
	  $(".new_side_wrap").addClass("new_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
  }
  </script>
  


<script>
  function alertMsg(){
	    var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='invoice_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Select Vendor</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
		result = result +"	</div>";
		$("#popUp").append(result);
		$("#invoice_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
  }
  </script>


<script>
	function toggleAssociates(source) {
	    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
	    for (var i = 0; i < checkboxes.length; i++) {
	        if (checkboxes[i] != source)
	            checkboxes[i].checked = source.checked;
	        
	    }
	$('input:checkbox:not(#checkAllAssociates)').click(function() {
	    if(!this.checked) {
	        $('#checkAllAssociates').removeAttr('checked');
	    }
	});
	}
</script>

<script>
    $(".addApplicant_close, .side_mask").click(function() {
    	$(".new_side_wrap").removeClass("new_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
</script>


<script>
function pagination(){
    $("#invoiceAssociatesList").DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10000, 15, 20, -1], [10000, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"aaSorting": [[1,'desc']],
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 0
			}],
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>


<script type="text/javascript">
 function validateInvoiceId() {
	 var invoiceId = document.getElementById("invoiceId").value; 
	
	 if(invoiceId != null && invoiceId != ""){
         //$("#create_user" ).addClass('button').val('Processing..');
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateInvoiceId?"+tokenParamName+"="+tokenValue+"&invoiceId="+invoiceId+"&invoiceType=Receivable", 
		        success: function(data) {
		        	 if(data == true){
		        		  $('#invoiceIdDiv').find('p.jquery_form_error_message').remove();
						  $("input#invoiceId").after("<p class='jquery_form_error_message'>"+invoiceId+"  exists. Please choose a different invoice id.</span>");
						  document.getElementById("invoiceId").setAttribute('record-exist','yes');
						  document.getElementById("invoiceId").setAttribute('record-exist-errorMsg',invoiceId+'  exists. Please choose a different invoice id.'); 
						  $("#saveButtonId").attr("disabled", "disabled");
						  $("#sendButtonId").attr("disabled", "disabled");
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


<!-- Get contract sow between client and the associate -->
  <script>
  function getSow(candidateId,id,contractId){
	  var orgRelationId = $("#clientOrgRelationId").val();
			  $.ajax({
			    	type: "GET",
			        url: sourceleadUrl+"/getSowcontractListByAssociateId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&orgRelationId="+orgRelationId+"&contractType=clientSow",
			        success: function(json) {
			        	var result = "";
			        	result = result + "<option value='' >select sow</option>"
			        	if(json.data != null && json.data != ''){
// 			        		var json = JSON.parse(data);
				        	 for (var i=0; i<json.data.length; i++)
							{
					        		var opt = json.data[i];
					        		 var selected = '';
					        		 if(contractId != null && contractId != "" && contractId !="undefined" && contractId == opt.id){
					        			selected = 'selected';
					        		} 
					        		 result = result +"<option value="+opt.id+" "+selected+">"+opt.contractTitle+"</option>";
							} 
			        	}
			        	 $('#'+id+'sow').empty();
			        	 $('#'+id+'sow').append(result);
			        },
			        
			    });
	 
	 
  }
  </script> 

<script>
  function totalHoursCalculateAmount(rowId){
	  var rate = $("#"+rowId+"rate").val();
	  var totalHours = $("#"+rowId+"totalHours").val();
	     var split = totalHours.split(':');
	     var hours = split[0];
 	     var minute = split[1];
 	     
 	     var hours1 = hours.charAt(0);
		 var hours2 = hours.charAt(1);
		 var hours3 = hours.charAt(2);
		 var min1 = minute.charAt(0);
		 var min2 = minute.charAt(1);
		
		 if(!isNaN(hours1) && isNaN(hours2) && isNaN(hours3)){
			 if(isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val("00"+hours1+":00");
				 calAddRowAmount("00"+hours1,"00",rowId); 
			 }
			 else if(!isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val("00"+hours1+":0"+min1);
				 calAddRowAmount("00"+hours1,"0"+min1,rowId);
			 }
			 else if(isNaN(min1) && !isNaN(min2)){
				 $("#"+rowId+"totalHours").val("00"+hours1+":0"+min2);
				 calAddRowAmount("00"+hours1,"0"+min2,rowId);
			 }
			 else{
				 $("#"+rowId+"totalHours").val("00"+hours1+":"+minute);
				 calAddRowAmount("00"+hours1,minute,rowId);
			 }
		 }
		 else if(isNaN(hours1) && !isNaN(hours2) && isNaN(hours3)){
			 if(isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val("00"+hours2+":00");
				 calAddRowAmount("00"+hours2,"00",rowId);
			 }
			 else if(!isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val("00"+hours2+":0"+min1);
				 calAddRowAmount("00"+hours2,"0"+min1,rowId);
			 }
			 else if(isNaN(min1) && !isNaN(min2)){
				 $("#"+rowId+"totalHours").val("00"+hours2+":0"+min2);
				 calAddRowAmount("00"+hours2,"0"+min2,rowId);
			 }
			 else{
				 $("#"+rowId+"totalHours").val("00"+hours2+":"+minute);
				 calAddRowAmount("00"+hours2,minute,rowId);
			 }
		 }
		 else if(isNaN(hours1) && isNaN(hours2) && !isNaN(hours3)){
			if(isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val("00"+hours3+":00");
				 calAddRowAmount("00"+hours3,"00",rowId);
			 }
			 else if(!isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val("00"+hours3+":0"+min1);
				 calAddRowAmount("00"+hours3,"0"+min1,rowId);
			 }
			 else if(isNaN(min1) && !isNaN(min2)){
				 $("#"+rowId+"totalHours").val("00"+hours3+":0"+min2);
				 calAddRowAmount("00"+hours3,"0"+min2,rowId);
			 }
			 else{
				 $("#"+rowId+"totalHours").val("00"+hours3+":"+minute);
				 calAddRowAmount("00"+hours3,minute,rowId);
			 }
		 }
		 else if(!isNaN(hours1) && !isNaN(hours2) && isNaN(hours3)){
			if(isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val("0"+hours1+hours2+":00");
				 calAddRowAmount("0"+hours1+hours2,"00",rowId);
			 }
			 else if(!isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val("0"+hours1+hours2+":0"+min1);
				 calAddRowAmount("0"+hours1+hours2,"0"+min1,rowId);
			 }
			 else if(isNaN(min1) && !isNaN(min2)){
				 $("#"+rowId+"totalHours").val("0"+hours1+hours2+":0"+min2);
				 calAddRowAmount("0"+hours1+hours2,"0"+min2,rowId);
			 }
			 else{
				 $("#"+rowId+"totalHours").val("0"+hours1+hours2+":"+minute);
				 calAddRowAmount("0"+hours1+hours2,minute,rowId);
			 }
			
		 }
		 else if(isNaN(hours1) && !isNaN(hours2) && !isNaN(hours3)){
			 if(isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val("0"+hours2+hours3+":00");
				 calAddRowAmount("0"+hours2+hours3,"00",rowId);
			 }
			 else if(!isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val("0"+hours2+hours3+":0"+min1);
				 calAddRowAmount("0"+hours2+hours3,"0"+min1,rowId);
			 }
			 else if(isNaN(min1) && !isNaN(min2)){
				 $("#"+rowId+"totalHours").val("0"+hours2+hours3+":0"+min2);
				 calAddRowAmount("0"+hours2+hours3,"0"+min2,rowId);
			 }
			 else{
				 $("#"+rowId+"totalHours").val("0"+hours2+hours3+":"+minute);
				 calAddRowAmount("0"+hours2+hours3,minute,rowId);
			 }
		 }
		 else if(!isNaN(hours1) && isNaN(hours2) && !isNaN(hours3)){
			 if(isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val("0"+hours1+hours3+":00");
				 calAddRowAmount("0"+hours1+hours3,"00",rowId);
			 }
			 else if(!isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val("0"+hours1+hours3+":0"+min1);
				 calAddRowAmount("0"+hours1+hours3,"0"+min1,rowId);
			 }
			 else if(isNaN(min1) && !isNaN(min2)){
				 $("#"+rowId+"totalHours").val("0"+hours1+hours3+":0"+min2);
				 calAddRowAmount("0"+hours1+hours3,"0"+min2,rowId);
			 }
			 else{
				 $("#"+rowId+"totalHours").val("0"+hours1+hours3+":"+minute);
				 calAddRowAmount("0"+hours1+hours3,minute,rowId);
			 }
		 }
		 else if(!isNaN(hours1) && !isNaN(hours2) && !isNaN(hours3)){
			 if(isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val(hours+":00");
				 calAddRowAmount(hours,"00",rowId);
			 }
			 else if(!isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val(hours+":0"+min1);
				 calAddRowAmount(hours,"0"+min1,rowId);
			 }
			 else if(isNaN(min1) && !isNaN(min2)){
				 $("#"+rowId+"totalHours").val(hours+":0"+min2);
				 calAddRowAmount(hours,"0"+min2,rowId);
			 }
			 else{
				 $("#"+rowId+"totalHours").val(hours+":"+minute);
				 calAddRowAmount(hours,minute,rowId);
			 }
		 }else{
			 calAddRowAmount("000","00",rowId);
		 }
		 conversion();
  }
  
  
  function  conversion(){
	   var orgRelationId = $("#clientOrgRelationId").val();
	   var currencyCode = '${SourceLeadWeb_currencyCode}';
	   var grandTotal = $("#grandTotal").val();
	   if(grandTotal != '' && grandTotal != 'undefined'){
		   $.ajax({
				type : "GET",
				url: sourceleadUrl+"/getConversionExchangeRate?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId+"&grandTotal="+grandTotal,
				success :function(response) {
					if(response != null && response.object != null && response.object != '' && response != 'undefined'){
// 						var json = JSON.parse(data);
						$("#exchangeRate").html(response.object.exchangeRate);
						$("#exchangeCurrency").html(response.object.exchangeCurrency);
						$("#vendorCurrencyCode").html(response.object.vendorCurrencyCode);
						$("#convertedCurrencyCode").html(currencyCode +' to '+ response.object.vendorCurrencyCode);
					}
				},
				});   
	   }
	  
		
  }
  
  function calAddRowAmount(hours,minute,rowId){
	  
	  var rate = $("#"+rowId+"rate").val();
	  var split = hours.split(':');
	  var splitHours = split[0];
	  var hourminits = splitHours * 60;
		 var finalmin = parseInt(hourminits) + parseInt(minute);
		 setHours(finalmin,rowId);
	     var perMinit = rate / 60;
	     var totlalAmount = (perMinit * finalmin);
	     $('#'+rowId+'amount').empty();
	     $('#'+rowId+'amount').val(totlalAmount.toFixed(2));
	     calTotalamount(rowId,totlalAmount);
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


<script>
  function  calTotalamount(rowId,totlalAmount){
	  
	  var hiddenTotal = +document.getElementById("hiddenTotal").value;
	    var grandTotal = parseFloat(hiddenTotal) + parseFloat(totlalAmount);
	    var arroundGrand = grandTotal.toFixed(3)
	    
	    
	    //--------------------------------
	    
	    var maxRowNum = parseInt($("input[name = 'maxTsRowNum']").val());
	    var sum = 0;
	 	    for(var i=0; i< maxRowNum; i++){
	 	    	 if(document.getElementById(i+"amount")){
	 	    		 if(document.getElementById(i+"amount").value){
	 	    			var rowAmount = $('#'+i+'amount').val();
			 	    	sum = parseFloat(sum) + parseFloat(rowAmount);
	 	    		 }
	 	    		
	 	    	 }
	 	    }
	    //-------------------------------------
	    
	    $("#inoviceTotal").val(sum.toFixed(3));
	    $("#hiddenTotal").val(sum.toFixed(3));
	    $("#grandTotal").val(sum.toFixed(3));
	    $("#conversionFrom").val(sum.toFixed(3));
	    $("#afterDiscountTotal").val(sum.toFixed(3));
	    
/*  After adding the rows calculate the discount start  */
   calculateDiscount();

/*  After adding the rows calculate the Tax discount start  */
	    calculateTaxDiscount();
	    
  }
  </script>

<script type="text/javascript">
function calculateDiscount(){
	var discountType = $("#disCount").val();
	var subTotal = $("#inoviceTotal").val();
	var discountValue = $("#discountValue").val();
	var total ='';
	if(discountType == 'flat'){
		if(discountValue && subTotal > 0){
			total = subTotal-discountValue;
			$("#discountTotal").val(discountValue);
			$("#grandTotal").val(total);
			$("#conversionFrom").val(total);
			$("#afterDiscountTotal").val(total.toFixed(3));
		}else{
			$("#discountTotal").val("00.000");
			$("#grandTotal").val(subTotal);
			$("#conversionFrom").val(subTotal);
			$("#afterDiscountTotal").val(subTotal);
		}
		
	}
	if(discountType == 'percentage'){
		if(discountValue && subTotal > 0){
			var perCentValue = (subTotal * discountValue)/100;
			total = subTotal - perCentValue;
			$("#discountTotal").val(perCentValue.toFixed(3));
			$("#grandTotal").val(total);
			$("#conversionFrom").val(total);
			$("#afterDiscountTotal").val(total.toFixed(3));
		}else{
			$("#discountTotal").val("00.000");
// 			$("#grandTotal").val(subTotal);
			$("#conversionFrom").val(subTotal);
			$("#afterDiscountTotal").val(subTotal);
		}
		
	}
	 calculateTaxDiscount();
	 conversion();
}
</script>



<!-- ********************************************* Add Rows Invoice Table functionality End  ************************************************************ -->


<!-- ********************************************* Add Rows resume Table functionality start  ************************************************************ -->

<div id="divInvoiceAttachmentTd1" style="display: none; float: left;">
	<div class="timesheet_week_comm col-md-12 invoiceDocs invoiceDocs_add width100percnt">
		<div class="form-group mb0" id="REPLACEROWIDinvoicedocFileuploadFileGroup">
			<span> <input type="file" name='REPLACEROWIDinvoicedocFile'
				id='REPLACEROWIDinvoicedocFile'
				onchange='attachFile(REPLACEROWID);validateContractsUploadFile(REPLACEROWID)'> <i
				class="fa fa-times mr6" mouseOver="Delete" title="Delete"
				onclick="removeInvoiceAttachmentRow(REPLACEROWID);"></i>
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

<!-- ********************************************* Add Rows resume Table functionality End  ************************************************************ -->




<!-- ********************************************* Add Rows AddTax Table functionality start  ************************************************************ -->

<div id="divTaxTd1" style="display: none; float: left;">
	<div class="select-field tblminwidth-75">
		<div class="tblminwidth-50">
			<input type="text" class="form-control input-sm" value=""
				placeholder="Tax type" name='invoiceTax[REPLACEROWID].taxType'
				id='REPLACEROWIDtaxType' data-validation="required validate_length length2-100" field-name="Tax Type"> <input type="hidden"
				name="invoiceTax[REPLACEROWID].invoiceTaxId" id="REPLACEROWIDinvoiceTaxId">
			<!-- 	<input type= "hidden" id="REPLACEROWIDsubmissionInvoiceId" name = "invoiceTax[REPLACEROWID].submissionInvoiceId"> -->
		</div>
	</div>
</div>

<div id="divTaxTd2" style="display: none; float: left;">
	<div class="btn-group tblminwidth-75">
		<select class="form-control input-sm" id="REPLACEROWIDtaxdiscountType" name="invoiceTax[REPLACEROWID].taxdiscountType" onchange="calculateTaxDiscount(REPLACEROWID);validateRowDiscount(REPLACEROWID);">
			<option value="percentage">Percentage</option>
			<option value="flat">Flat</option>
		</select>
	</div>
</div>

<div id="divTaxTd3" style="display: none; float: left;">
	<div class="tblminwidth-75" id="REPLACEROWIDdiscountRowDiv">
		<input type="text"
			class="form-control input-sm allownumericwithdecimal" value=""
			placeholder="Flat / Discount" name='invoiceTax[REPLACEROWID].taxdiscountvalue'
			id='REPLACEROWIDtaxdiscountvalue'
			onblur="calculateTaxDiscount(REPLACEROWID);" onkeyup="validateRowDiscount(REPLACEROWID);">
	</div>
</div>

<div id="divTaxTd4" style="display: none; float: left;">
	<div class="tblminwidth-75">
		<input type="text" class="form-control input-sm" value="00.00"
			placeholder="Total Value" name='invoiceTax[REPLACEROWID].taxTotal'
			id='REPLACEROWIDtaxTotal' readonly="readonly">
	</div>
</div>
<div id="divTaxTd5" style="display: none; float: left;">
	<a class="text-danger tblminwidth-50" title="Remove row"
		onclick="removeTaxRow(REPLACEROWID)"><i class="fa fa-remove"></i></a>
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

<script type="text/javascript">
	function removeTaxRow(rowId) {
		$("#divTaxRow"+rowId).css("display", "none");
		
		//After removing the row calculate the amount and append the grand total
		var grandTotal =  $("#grandTotal").val();
		var taxTotal = $("#"+rowId+"taxTotal").val(); 
		var first=parseFloat(grandTotal);
        var second=parseFloat(taxTotal);
        total = Math.round(first-second);
        $("#grandTotal").val(total);
        $("#conversionFrom").val(total);
       //=======================================
        conversion();
		
		var r = document.getElementById('addTaxTbl').rows[rowId+1];
		var count = r.cells.length;
		for (i = 0; i <= count; i++) {
			r.deleteCell(0);
		}
	}
</script>

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
		    	}else{
		    		$("#"+i+"taxTotal").val("00.00");
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
	 $("#grandTotal").val(grandTotal.toFixed(3));
	 $("#conversionFrom").val(grandTotal.toFixed(3));
	 conversion();
}
</script>


<!-- ********************************************* Add Rows AddTax Table functionality end  ************************************************************ -->
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
			var dt = $(this).datepicker('getDate', '+1d');
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
			var dt = $(this).datepicker('getDate', '-1d');
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
<script>
function errInvoiceDate(){
	$('#errInvoiceDate').find('p.jquery_form_error_message').remove(); 
}
function errDueDate(){
	$('#errDuedate').find('p.jquery_form_error_message').remove(); 
}

</script>

<script>
function getSowRate(id){
	var contractId = $("#"+id+'sow').val();
	if(contractId !='' && contractId != 'undefined' && contractId != null){
	$.ajax({
    	type: "GET",
    	url: sourceleadUrl+"/getSowRateByContractId?"+tokenParamName+"="+tokenValue+"&contractId="+contractId,
        success: function(json) {
        	 if(json.object != null && json.object != ''){
//         		var json = JSON.parse(data);
	        	 $ ('#'+id+'rate').val(json.object.billingAmount);
	        	 totalHoursCalculateAmount(id);
        	} 
        },
        
    });
}else{
	$ ('#'+id+'rate').val(0);
	totalHoursCalculateAmount(id);
}
}
</script>

<script>
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
	  
	  $( "#0sdate-input" ).datepicker({
		  changeMonth: true,
	      changeYear: true,
	      dateFormat : finalDateFormatEndDate,
	      minDate: 0  ,
		  onSelect : function(selected) {
	 	  }
	    });
	
	$( "#0edate-input" ).datepicker({
		  changeMonth: true,
	      changeYear: true,
	      dateFormat : finalDateFormatEndDate,
	      minDate: 0  ,
		  onSelect : function(selected) {
	 	  }
	    });
	  </script>
<script type="text/javascript">
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
	    $(document).ready(function(){
	    $("#cd").click(function(){
	    $(".clientDetails").toggleClass('hidden', 500);
	    });
	    });
	</script>
<script>
	$("#invoiceTable tbody").sortable({
	    items: "> tr:not(:first)",
	    appendTo: "parent",
	    helper: "clone"
	}).disableSelection();
	    </script>
<script>
	    function cloneTax(){
	    }
	    </script>



<!-- added by Rajashekar -->

<script>
function getClients(){
	var orgRelationId ='${orgRelationId}';
	
	$.ajax({
		type : "GET",
		url: sourceleadUrl+"/getClientList?"+tokenParamName+"="+tokenValue+"",
		success :function(json) {
// 		var json = JSON.parse(data);
		var output = "";
			output = output + "<option value=''>Select Client</option>";
		for (var i=0; i<json.data.length; i++)
		{
		var opt = json.data[i];
		if(orgRelationId == opt.id){
			var selected = 'selected';
			output = output + "<option value='" +opt.id+ "'"+selected+" data-Invoice = '"+opt.generateInvoice+"' data-responder_location_id=" + opt.responderId + ">"+opt.orgName+"</option>";
		}else{
			output = output + "<option value='" +opt.id+ "' data-Invoice = '"+opt.generateInvoice+"' data-responder_location_id=" + opt.responderId + ">"+opt.orgName+"</option>";
		}
		}
		
		$("#clientOrgRelationId").empty();
		$("#clientOrgRelationId").append(output);
		$('#clientOrgRelationId').trigger("chosen:updated");
		
		
		if(orgRelationId != null && orgRelationId != '' && orgRelationId != 'undefined'){
			getClientContacts();
			errRemoveClient();
			getPaymentTerms();
		}
	},
	});

	}
</script>


<script>
function getClientContacts(clientContactId){
	//alert(clientContactId);
	var orgRelationId = $("#clientOrgRelationId").val();
	$("#clientContact").empty();
	$.ajax({
		type : "GET",
		url: sourceleadUrl+"/getClientContactsByOrgRelationId?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId,
		success :function(json) {
			var output = "";
			for (var i=0; i<json.data.length; i++)
			{
				var opt = json.data[i];
				if(clientContactId != null && clientContactId != "" && clientContactId !="undefined"){
					var array = clientContactId.split(",");
					if(array.includes(opt.spocUserId.toString())){
						output = output + "<option value='"+opt.spocUserId+"' selected >"+opt.ownerName+"("+opt.spocName+")</option>";
					}else{
						output = output + "<option value='"+opt.spocUserId+"' >"+opt.ownerName+"("+opt.spocName+")</option>";
					}
				}else{
					output = output + "<option value='" +opt.spocUserId+ "'>"+opt.ownerName+"("+opt.spocName+")</option>";
				}
				$("#clientCurrency").val(opt.clientCurrency);
			}
			$("#clientContact").append(output);
			$('#clientContact').trigger("chosen:updated");
			if(clientContactId == null || clientContactId == '' || clientContactId == 'undefined'){
				buildLineItemAndTaxAddRows();
			}
		},
	});
	}
</script>


<script> 
function getPaymentTerms(){
	var orgRelationId = $("#clientOrgRelationId").val();
	$.ajax({
		type : "GET",
		url: sourceleadUrl+"/getPaymentTermsAndBillindAddress?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId,
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

function getInvoiceTemplateList(selectedId) {
	var orgRelationId = $("#clientOrgRelationId").val();
	var responderLocationId = $("#clientOrgRelationId :selected").data("responder_location_id");
	$("#invoiceTemplateId").empty("");
	$.ajax({
		type : "POST",
		url: sourceleadUrl+"/invoiceTemplateList?orgRelationId=" + orgRelationId + "&orgLocationId=" + responderLocationId,
		success : function(data) {
			$("#invoiceTemplateId").append("<option value='0'>-- Select Invoice Template --</option>");
			$("#invoiceTemplateId").append("<option value='-1' selected>Default Template</option>");
			if(data && data.data != null && data.data.length > 0) {
				for(var e in data.data) {
					$("#invoiceTemplateId").append("<option value=" + data.data[e].id + " "+ (selectedId && selectedId === data.data[e].id ? "selected" : "") +">" + data.data[e].name + "</option>");
				}
			}
		}
	});
}
</script>
<script>
function getDueDateByFrequency(){
	 var invoiceDate = document.getElementById("invoiceDate").value;
	 var paymentFrequency = document.getElementById("paymentFrequency").value;
	 var locationCode ='${SourceLeadWeb_locationCode}';
	 if(paymentFrequency && invoiceDate){
	    $("#invoiceLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	    
	    $.ajax({
			type : "GET",
			url: sourceleadUrl+"/getDueDateByFrequency?"+tokenParamName+"="+tokenValue+"&paymentFrequency="+paymentFrequency+"&invoiceDate="+invoiceDate+"&locationCode="+locationCode,
			success :function(dueDateStr) {
				
				$("#dueDate").val(dueDateStr);
				$("#invoiceLoading").removeAttr("style");
				errDueDate();
			}
		});
		
	}else{
		$("#dueDate").val('');
	}
}
</script> 

<script>
function saveInvoice(saveType){
	var maxTsRowNum = parseInt($("input[name = 'maxTsRowNum']").val());
	    var selectedAssociates = '';
		  if(maxTsRowNum > 0){
			  for(var i=0; i<maxTsRowNum; i++){
				  if(document.getElementById(i+"candidateId")){
					  var associateId = document.getElementById(i+"candidateId").value;
					  if(selectedAssociates == ''){
						  selectedAssociates = associateId;
					  }else{
						  selectedAssociates = selectedAssociates+","+associateId;
					  } 
				  }
			  }
		  }
		  if ($('#invoice_form').validate(false, validationSettings)){  
 	 	    var formData = getFormData1("invoice_form");
 	 	    
 	 	  if ($("#notifyToOthers").val() !== "") {
 	 		for (var e in $("#notifyToOthers").val().split(",")) {
 	 		if (!jQueryFormUtils.validateEmail($("#notifyToOthers").val().split(",")[e].replace(/\s+/g, ''))) {
 	 		flag = false;
 	 		$("#notifyToOthers").parent().append("<p class='jquery_form_error_message'>Enter valid email id</p>");
 	 		return;
 	 		}
 	 		}
 	 		}
         
 	 	    if(selectedAssociates == null || selectedAssociates == '' || selectedAssociates == 'undefined'){
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
		        		  url: sourceleadUrl+"/saveInvoice?"+tokenParamName+"="+tokenValue+"&saveType="+saveType+"&invoiceType=Receivable",
		        		  type: "POST",
		        		  data: formData,
		        		  processData: false,
		        		  contentType: false,
		        		  success: function (result) {
		        	  //$.post(sourceleadUrl+"/saveInvoice?"+tokenParamName+"="+tokenValue+"&saveType="+saveType+"&invoiceType=Receivable", formData, function(result) {
		        		  if (result && result.response === "INVALID_DATA") {
		          		    showError(result.responseMessage);
		          		} else if (result.response === "AWKWARD") {
		          		    location.href = "/errorPage";
		          		} else if (result.response === "SUCCESS") {
			  					$("#invoiceLoading").removeAttr("style");
	    		     			location.replace(sourceleadUrl+"/invoiceList");
	    		     			$("#selector1").removeAttr("style");
					    		$("#selector1").css({ display: "block" });
				    		if(saveType == 'SENT'){
				    			$("#msgDiv").html("Successfully you have sent a Invoice..");
				    		}else if(saveType == 'DRAFT'){
				    			$("#msgDiv").html("Invoice saved Successfully..");
				    		}
    		     			msgFadein();
		          		} else if (result.indexOf('Sign In') !== -1) {
		          			location.replace(sourceleadUrl);
		          		} else {
		          		    showError("Operation failed, please try again");
		          		}
		        		  $("#invoiceLoading").hide();
		        	  }
		  			   }); 
		        	  
		    	         return true; 
	        }
         }else{
	    		return false;
	    	  } 
	   
}

$('body').on('blur', '#invoice_form', function() {
	$("#invoice_form").showHelpOnFocus();
	$("#invoice_form").validateOnBlur(false, validationSettings);  
}); 

function msgFadein() {
	$("#selector1").fadeIn(200);
    $('#selector1').delay(2000).fadeOut('slow');
}

</script>

<!-- End by Rajashekar -->
<!-- <script>
$(document).ready(function(){
	$(".dateMask").mask('9999:99');
});
</script> -->
<script>
$(document).ready(function() {  
	maskme();    
 });
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
    $(".inlineField").mask("Hhh:Mm"); 
    
    $(".allownumericwithdecimal").on("keypress keyup blur",function (evt) {
    	
    	var charCode = (evt.which) ? evt.which : evt.keyCode;
    	if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57))
    	return false;

    	return true;
    	
    	
//     $(this).val($(this).val().replace(/[^0-9\.]/g,''));
//         if ((event.which != 46 || $(this).val().indexOf('.') != -1) && (event.which < 48 || event.which > 57)) {
//             event.preventDefault();
//         }
    });
}
</script>

<!-- <script>
$(".allownumericwithdecimal").on("keypress keyup blur",function (event) {
$(this).val($(this).val().replace(/[^0-9\.]/g,''));
    if ((event.which != 46 || $(this).val().indexOf('.') != -1) && (event.which < 48 || event.which > 57)) {
        event.preventDefault();
    }
});
</script> -->
<script type="text/javascript">
function cancel(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
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
function getEstimatedHours(rowId){
	var startDate = document.getElementById(rowId+"sdate-input").value;
	var endDate = document.getElementById(rowId+"edate-input").value;
	var locationId = '${SourceLeadWeb_orgLocationId}';
	if(startDate && endDate){
		 var averageWorkingHours = '${SourceLeadWeb_orgLocations.averageWorkingHours}';
		 $("#resourceListLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getTotalHosursForBilling?"+tokenParamName+"="+tokenValue+"&startDate="+startDate+"&endDate="+endDate+"&averageWorkingHours="+averageWorkingHours+"&locationId="+locationId,
		        success: function(estimatedHours) {
		        	$('#'+rowId+'totalHours').val(estimatedHours);
		        	$('#'+rowId+'hiddenHours').val(estimatedHours);
		        	totalHoursCalculateAmount(rowId);
		        	 $("#resourceListLoading").removeAttr("style"); 
		        	
		        }
		        
		 });
	}
}
function editHoursValidations(startDate,endDate,rowId){
	if(startDate && endDate){
		 var locationId = '${SourceLeadWeb_orgLocationId}';
		 var averageWorkingHours = '${SourceLeadWeb_orgLocations.averageWorkingHours}';
		 $("#resourceListLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getTotalHosursForBilling?"+tokenParamName+"="+tokenValue+"&startDate="+startDate+"&endDate="+endDate+"&averageWorkingHours="+averageWorkingHours+"&locationId="+locationId,
		        success: function(estimatedHours) {
		        	$('#'+rowId+'hiddenHours').val(estimatedHours);
		        	totalHoursCalculateAmount(rowId);
		        	 $("#resourceListLoading").removeAttr("style"); 
		        	
		        }
		        
		 });
	}
}
</script>
<script>
function validationHours(rowId){
	 var hiddenHours = $('#'+rowId+'hiddenHours').val();
	  var split1 = hiddenHours.split(':');
	  var splitHours1 = split1[0];
	  var minute1 = split1[1];
	  var hourminits1 = splitHours1 * 60;
	  var hiddenFinalmin1 = parseInt(hourminits1) + parseInt(minute1);
	    
	  var totalHours = $("#"+rowId+"totalHours").val();
	  var split2 = totalHours.split(':');
	  var splitHours2 = split2[0];
	  var minute2 = split2[1];
	  var hourminits2 = splitHours2 * 60;
	  var totalHoursFinalmin2 = parseInt(hourminits2) + parseInt(minute2);
	 
	 if(hiddenHours != null && hiddenHours != ""){
		 if(hiddenFinalmin1 < totalHoursFinalmin2){
			 document.getElementById(""+rowId+"errMsg").style.display="block";
		 }else{
			 document.getElementById(""+rowId+"errMsg").style.display="none";
		 }
	 }
	 
}

function attachFile(id){
	fileUpload(""+id+"invoicedocFile", "invoiceDocuments", "invoice_form","fileInfo");
}
 
</script>

<script>
function createInvoiceEmptyClientContact(){
	$("#clientContact").empty();
	 $('#clientContact').append('<option value="">--select Client contact--</option>');
    $('#clientContact').trigger("chosen:updated");
}
</script>
 <script>
	 function selectedClient(){
		 var cid = $("#clientOrgRelationId").val();
		 window.open(sourceleadUrl+"/addClientContact?cid="+cid,'_blank');
	 }
</script>
<!-- <script>
function clearDate(){
	$("#dueDate").val("");
	};
</script> -->
<script>
function clearInvoiceDate(){
	$("#invoiceDate").val("");
	};
	
function clearDueDate(){
	$("#dueDate").val("");
	};
</script>
<jsp:include page="../masterFooter.jsp" />