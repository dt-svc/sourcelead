<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<script src="resources/js/jquery.mask.min.js"></script>
<script src="resources/js/jquery.maskedinput-1.3.min.js"></script>


<style type="text/css">
.inline_field, .inline_field:focus {
	width:100px !important; text-align:center;
}
.timesheet_week_comm .form-group {
margin-bottom: 0;
}
.timesheet_week_comm.invoiceDocs_add span .fa-times {
    margin: 6px;
}
</style>


<script type="text/javascript">
$(document).ready(function(){
	var invoiceUUID = '${invoiceUUID}';
	if(invoiceUUID != null && invoiceUUID != '' && invoiceUUID != 'undefined'){
		loadInvoiceDetails(invoiceUUID);
	}else{
		getVendors();
	}
	
});
</script>
<script>
function errRemoveVendor(){
	$("#errVendor").find("p.jquery_form_error_message").remove();
}
function errRemoveVendorContact(){
	$("#errVendorContact").find("p.jquery_form_error_message").remove();
}

</script>

<div class="side_mask">
	<div class="proj_close"></div>
</div>

<div id="popUp"></div>
<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Custom Bill</h2>
		</div>
		<div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='/payablesList'/>">Bills List</a> &raquo; Custom Based Bill</div>
	</section>
	<div class="content-area content-new clearfix">
		<div id="invoiceLoading"></div>
		<form:form id="invoice_form" name="invoice_form" method="post">
			<div class="clearfix form-horizontal inner-content">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
						<div class="form-group">
							<label class="col-md-3">Vendor<span class="text-danger">&nbsp;*</span></label>
							<div class="col-md-9 select-field" id="errVendor">
								<select class="chosen-select form-control"
									id="vendorOrgRelationId" name="vendorOrgRelationId"
									onchange="getVendorContacts();errRemoveVendor();getPaymentTerms();" data-validation="required" field-name="Vendor">
								</select>
							</div>
							<input type="hidden" name="savedInvoiceId" id="savedInvoiceId">
							<input type="hidden" name="vendorCurrency" id="vendorCurrency">
						</div>
						<div class="form-group">
							<label class="col-md-3">Vendor Contact<span
								class="text-danger">*</span></label>
							<div class="col-md-9" id="errVendorContact">
								<select class="chosen-select form-control" id="vendorContact"
									name="vendorContact" data-validation="required"
									field-name="Vendor Contact"
									onchange="errRemoveVendorContact();">
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3">Bill Id<span
								class="text-danger">&nbsp;*</span></label>
							<div class="col-md-9">
								<input type="text" class="form-control" id="invoiceId"
									name="invoiceId" value="" placeholder="Bill Id"
									data-validation="required" field-name="Bill Id">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3">Bill Date<span
								class="text-danger">&nbsp;*</span></label>
							<div class="col-md-9 select-field" id="errInvoiceDate">
								<input type="text" class="form-control calendar"
									id="invoiceDate" name="invoiceDate" value="${currentDate}"
									placeholder="Bill Date" id="invoiceDate" readonly
									data-validation="required" field-name="Bill Date">
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
							<label class="col-md-3">Due Date<span class="text-danger">&nbsp;*</span></label>
							<div class="col-md-9" id="errDuedate">
								<input type="text" class="form-control calendar" id="dueDate"
									name="dueDate" value="" placeholder="Due Date" id="dueDate"
									readonly data-validation="required" field-name="Due Date">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3">Billing Address</label>
							<div class="col-md-9">
								<textarea class="form-control" rows="2" id="billingAddress"
									name="billingAddress" placeholder="Billing Address" data-validation="validate_length length1-100" field-name="Billing Address"></textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3">Account Details</label>
							<div class="col-md-9">
								<textarea class="form-control" rows="2" id="accountDetails"
									 name="accountDetails" placeholder="Account Details" data-validation="validate_length length1-100" field-name="Account Details"></textarea>
							</div>
						</div>
						
					</div>
				</div>

				<div class="clearfix mt"></div>
				<div id="lineItemAddRows"></div>

				<div id="lineItemPopUp"></div>


				<footer class="form-footer">
					<div class="pull-right mr mt25">
						<button type="button" class="btn btn-sm btn-primary pull-left" id="saveInvoiceId" style="display:block;"
							onclick="saveInvoice('DRAFT')">Save</button>
						<button type="button" class="btn btn-sm btn-success ml pull-left" id="sendInvoiceId" style="display:block;"
							onclick="saveInvoice('SENT')">Send</button>
						<button type="button" class="btn btn-sm btn-default ml pull-left"
							onClick="javascript:window.location.href = '<c:url value='/payablesList'/>'">Cancel</button>
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
			id="REPLACEROWIDcandidateName" id="lineItems[REPLACEROWID].candidateName">
		</inmput>
		<input type="hidden" class="form-control input-sm"
			id="REPLACEROWIDcandidateId" name="lineItems[REPLACEROWID].candidate">
		</inmput>
		<input type="hidden" class="form-control input-sm"
			id="REPLACEROWIDlineItemId" name="lineItems[REPLACEROWID].lineItemId">
		</inmput>
	</div>
</div>

<div id="divTaskTd3" style="display: none; float: left;">
	<div class="tbldata-dotdot select-field">
		<textarea type="text" class="form-control input-sm" placeholder="Description" id="REPLACEROWIDDescription" name="lineItems[REPLACEROWID].Description"
			data-validation="validate_length length1-100" field-name="Description"></textarea>
	</div>
</div>

<div id="divTaskTd4" style="display: none; float: left;">
	<div class="tbldata-dotdot-100 select-field">
		<select class="form-control input-sm" id="REPLACEROWIDsow" name="lineItems[REPLACEROWID].sow"  onchange="getSowRate(REPLACEROWID);">
			<option value=''>-Select sow-</option>
		</select>
	</div>
</div>

<div id="divTaskTd5" style="display: none; float: left;">
	<div class="tbldata-dotdot_80">
		<input type="text"
			class="form-control input-sm allownumericwithdecimal"
			placeholder="Rate" id="REPLACEROWIDrate" name="lineItems[REPLACEROWID].rate"
			onblur="totalHoursCalculateAmount(REPLACEROWID);" data-validation="required" field-name="Rate">
	</div>
</div>

<div id="divTaskTd6" style="display: none; float: left;">
	<div class="tbldata-dotdot-100">
		<input type="text" class="form-control input-sm inline_field"
			placeholder="HHH:MM" id="REPLACEROWIDtotalHours"
			name="lineItems[REPLACEROWID].totalHours"
			onblur="totalHoursCalculateAmount(REPLACEROWID);validationHours(REPLACEROWID);">
			<input type="hidden" id="REPLACEROWIDhiddenHours" name="lineItems[REPLACEROWID].hiddenHours">
			<u id="REPLACEROWIDerrMsg" style="display: none;"><i class=" text-info fa fa-info-circle" >Avg Hours</i></u>
	</div>
</div>

<div id="divTaskTd7" style="display: none; float: left;">
	<div class="tbldata-dotdot-vsm text-right">	
		<input type="text" name="lineItems[REPLACEROWID].amount" id="REPLACEROWIDamount"
			placeholder="00:00" class="ReadTextFli" readonly="readonly">
	</div>
</div>

<div id="divTaskTd8" style="display: none; float: left;">
	<a class="text-danger tblminwidth-50" title="Remove row"
		id="REPLACEROWIDremoveLineItem" onclick="removeRow(REPLACEROWID)"><i
		class="fa fa-remove"></i></a>
</div>


<script>
	function buildLineItemAndTaxAddRows(){
	 	var vendorCurrency = $("#vendorCurrency").val(); 
		var currencyCode = '${SourceLeadWeb_currencyCode}';
		 var result = '';
		 result = result +'  <div class="project_wrap_table mt25 invoice_table table_custompadding" id="invoiceTable">';
		 result = result +'  <input type="hidden" id="maxTsRowNum" name="maxTsRowNum" value="0" />';
		 result = result +'  <table width="100%">';
		 result = result +'  <thead id="addInvoiceTbl">';
		 result = result +'  <tr id="divTsRow0">';
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
		 result = result +'  <td><strong>Subtotal</strong></td>';
		 result = result +'  <td><div class="tblminwidth-150"><span class="pull-left paddingRight">'+currencyCode+'</span><input type="text" value = "00.00" id="inoviceTotal" name="inoviceTotal" class="ReadTextFli" readonly="readonly"></div>';
		 result = result +'  <input type="hidden" value = "" id="hiddenTotal">';
		 result = result +'  <td>&nbsp;</td>';
		 result = result +'  </tr>';
		 result = result +'  </tfoot>';
		 result = result +'  </table>';
		 result = result +'  </div>';
         
         result = result +'  <div class="clearfix invoice_other mt">';
         result = result +'  <div class="col-md-5">';
         result = result +'  <div class="clearfix mb">';
         result = result +'  <button type="button" class="btn btn-sm btn-primary mr" onclick="getAllVendorAssociates();"><i class="fa fa-plus paddingRight"></i>Add</button>';
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
         result = result +'  <textarea class="form-control" rows="2" id="comment" name="comment"></textarea>';
         result = result +'  </div>';
           
        
         result = result +'  <div class="clearfix mb invoiceAttachment">';
         result = result +'  <input type="hidden" name="invoiceAttcahmentMaxRowNumber"  id="invoiceAttcahmentMaxRowNumber" value="1" />';
         result = result +'  <table width="100%" width="100%" cellpadding="0" cellspacing="0"  id="invoiceAttachmentTbl">';
         result = result +'  <tbody>';
         result = result +'  <tr id="divAttachmentTsRow0">';
         result = result +'  <td>';
         result = result +'  <label class="pull-left mb10"><strong>Attachments</strong></label>';
         
         result = result +' <div id="timesheetDocumentsId">';
         result = result +' <div class="timesheet_week_comm invoiceDocs">';
         result = result +' <span>';
         result = result +' <div id="savedAttachments"></div>';
         result = result +' </span>';
         result = result +' </div>';
         result = result +' </div>';
         
         result = result +'  <div class="timesheet_week_comm invoiceDocs invoiceDocs_add" >';
         result = result +'  <div id="0invoicedocFileuploadFileGroup"> ';
         result = result +'  <span>';
         result = result +'  <input type="file" id="0invoicedocFile" field-name="Attachment"  class="timesheetAttachmentValidation" onchange="attachFile(0);"><i class="fa fa-times" mouseOver="Clear" title="Clear" onClick="remove()"></i><i class="fa fa-plus" mouseOvere="Add" title="Add" onClick="addInvoiceAttachment()"></i>';
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
         result = result +'  <select class="form-control input-sm" id="disCount" name="disCount" onchange="calculateDiscount();">';
         result = result +'  <option value="percentage">Discount %</option>';
         result = result +'  <option value="flat">Discount Value</option>';
         result = result +'  </select>';
         result = result +'  </div>';
         result = result +'  <div class="col-md-5 paddingLeft">';
         result = result +'  <input type="text" class="form-control input-sm allownumericwithdecimal" id="discountValue" disabled="disabled" name="discountValue" placeholder="Enter value" onblur="calculateDiscount();">';
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
         result = result +'  <button type="button" class="btn btn-sm btn-primary buttonCustom" title="Add Tax" onclick="addTax()" id="addTaxDiv">';
         result = result +'  <i class="fa fa-plus"></i></button>';
         result = result +'  </th>';
         result = result +'  </tr>';
         result = result +'  </thead>';
         result = result +'  <tbody>';
         result = result +'  </tr>';
                         
         result = result +'  </tbody>';
         result = result +'  </table>';
                   result = result +'  </div>';
                   result = result +'  <div class="form-group mt">';
                   result = result +'  <label class="col-md-7 text-right"><strong>Total</strong></label>';
                   result = result +'  <div class="col-md-5 text-right"><span class="pull-left">'+currencyCode+'</span><input type="text" value = "00.00" id="grandTotal" name="grandTotal" class="ReadTextFli" readonly="readonly"></div>';
                   result = result +'  </div>';
                   
                    if(vendorCurrency != currencyCode){ 
                	 //New Div for currency conversion: srinivas l
                       result = result +'  <div class="row">';
                       result = result +'  <div class="clearfix mt col-md-offset-2 col-md-10" style="padding-right:0;">';
                       
                       result = result +'  <div class="clearfix"><label class="col-md-6 text-right"><strong>Conversion From</strong></label><div class="paddingLeft col-md-6"><span class="pull-left">'+currencyCode+'</span><div class="pull-right"><input type="text" id="conversionFrom" placeholder="00:00" class="ReadTextFli" readonly="" type="text"></div></div></div>';
                       
                        result = result +'  <div class="clearfix"><label class="col-md-6 text-right"><strong>Conversion To</strong></label><div  class="paddingLeft col-md-6"><span id="vendorCurrencyCode"></span><div id="exchangeCurrency" class="pull-right"><input  class="ReadTextFli" readonly="" type="text"></div></div></div>';
    	                result = result +'  <div class="clearfix"><label class="col-md-6 text-right"><strong>Exchange Rate</strong></label><div  class="paddingLeft col-md-6"><span id="convertedCurrencyCode"></span><div id="exchangeRate" class="pull-right"><input class="ReadTextFli ReadTextFli90" readonly="" type="text"></div></div></div>';
    	                  
                       
                       result = result +'  </div>';
                       result = result +'  </div>'; 
                     //New Div for currency conversion: srinivas l
                  } 
                   result = result +'  </div>';
                   result = result +'  </div>';
                   result = result +'  </div>';
                   $("#lineItemAddRows").empty();
                   $("#lineItemAddRows").append(result);
		
	}
	
	</script>
<script>
function remove(){
	$('#0invoicedocFile').val("");
}
</script>


<script>
	function loadInvoiceDetails(invoiceUUID){
		 var sourceleadUrl ='${sourceleadUrl}';
		 
		 $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/getInvoiceDetailsByInvoiceUUID?${_csrf.parameterName}=${_csrf.token}&invoiceUUID="+invoiceUUID, 
		         success: function(data) {
		        	 var json = JSON.parse(data);
		        	 
		        	 var result = '';
		        	 var selected = 'selected';
		        	 result = result +'<option disabled selected>--Select Vendor--</option>';
		        	 result = result +'<option value='+json['vendorOrgRelationId']+' '+selected+' >'+json['orgName']+'</option>';
		        	 $("#vendorOrgRelationId").empty();
		           	 $("#vendorOrgRelationId").append(result); 
		           	 $('#vendorOrgRelationId').trigger("chosen:updated");
		           	$('#vendorCurrency').val(json['vendorCurrency']);
		           	getVendorContacts(json['vendorContactId']);
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
		        	if(json['accountDetails'] != null && json['accountDetails'] != '' && json['accountDetails'] != 'undefined'){
		           		$("#accountDetails").val(json['accountDetails']);
		           	}
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
		        	if("Rejected" == json['invoiceStatus']){
			   			 document.getElementById("sendInvoiceId").style.display="none";
			   			 document.getElementById("saveInvoiceId").style.display="none";
			       	}
		        	
		        	
		        }
    		});
	}
	</script>


<script>
	function getSavedAttachments(invoiceDocumentsList){
		var result = '';
		var sourceleadUrl = '${sourceleadUrl}';
		if(invoiceDocumentsList.length > 0){
			
			 for (var i = 0; i < invoiceDocumentsList.length; i++) { 
				 var opt = invoiceDocumentsList[i]; 
	    		
				 result = result +"<strong id='invoiceDocument"+opt.id+"'>";
	    		 result = result +"<i class=''><a class='glyphicon glyphicon-save' href=\""+sourceleadUrl+"/downloadInvoiceDocument?documentId="+opt.id+"\"></a></i>";
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
		
		if(documentId){
			var sourceleadUrl = '${sourceleadUrl}';
			$.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/deleteInvoiceDocumentBydocumentId?${_csrf.parameterName}=${_csrf.token}&documentId="+documentId, 
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
		     var subtractTotal = subTotal - totlalAmount.toFixed(2)
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
	       	 
	    	 
	    //calculate Tax Discount method 
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
				  $("#addTaxDiv").attr("disabled", "disabled");
				  
			  }
			  
		
		document.getElementById('divTsRow' + rowId).style.display = 'none';
		var r = document.getElementById('addInvoiceTbl').rows[rowId];
		var count = 2 * (r.cells.length);
		for (i = 0; i <= count; i++) {
			r.deleteCell(0);
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
				$("#addTaxDiv").removeAttr("disabled");
				
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
				   var finalDateFormatpayableEdit = '';
				  if(locationDate.match("yyyy-MM-dd")){
					  finalDateFormatpayableEdit = 'yy-mm-dd';
				  }if(locationDate.match("dd-MM-yyyy")){
					  finalDateFormatpayableEdit = 'dd-mm-yy'; 
				  }if(locationDate.match("MM-dd-yyyy")){
					  finalDateFormatpayableEdit = 'mm-dd-yy'; 
				  }if(locationDate.match("yyyy-dd-MM")){
					  finalDateFormatpayableEdit = 'yy-dd-mm'; 
				  }
				
				$( "#"+maxRowNum+"sdate-input" ).datepicker({
					  changeMonth: true,
				      changeYear: true,
				      dateFormat : finalDateFormatpayableEdit,
				      minDate: 0  ,
					  onSelect : function(selected) {
				 	  }
				    });
				
				$( "#"+maxRowNum+"edate-input" ).datepicker({
					  changeMonth: true,
				      changeYear: true,
				      dateFormat : finalDateFormatpayableEdit,
				      minDate: 0  ,
					  onSelect : function(selected) {
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
				
				
				/* getSow(opt.candidateId,maxRowNum,opt.contractId);
				maskme(); */  
				
				editHoursValidations(opt.startDateStr,opt.endDateStr,maxRowNum);
	 			getSow(opt.candidateId,maxRowNum,opt.contractId);
				maskme();
		 }
		
	}
  </script>


<script type="text/javascript">
function cancel(){
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
			$("#addTaxDiv").removeAttr("disabled");
			
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
						dt.setDate(dt.getDate() + 1);
						$("#"+maxRowNum+"edate-input").datepicker("option", "minDate", dt);
						getEstimatedHours(maxRowNum);
			 	  }
			    });
			
			$( "#"+maxRowNum+"edate-input" ).datepicker({
				  changeMonth: true,
			      changeYear: true,
			      dateFormat : finalDateFormatInvoiceSave,
				  onSelect : function(selected) {
					  var dt = $(this).datepicker('getDate', '-1d');
						dt.setDate(dt.getDate() - 1);
						$("#"+maxRowNum+"sdate-input").datepicker("option", "maxDate", dt);
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
			
			
			$('#'+maxRowNum+'candidateId').val(checkbox_value[0]);
			$('#'+maxRowNum+'candidateName').val(checkbox_value[1]);
			getSow(checkbox_value[0],maxRowNum);
			maskme(); 
			
		   });
		
		
	    $(".new_side_wrap").removeClass("new_side_wrap_active");
 		$(".side_mask").fadeOut(200);
 		$("body").css('overflow-y', 'auto')
	}
  </script>






<script>
  function getAllVendorAssociates(){
	  var maxTsRowNum = parseInt($("input[name = 'maxTsRowNum']").val());
	  
	  var selectedAssociates = '';
	  if(maxTsRowNum > 1){
		  for(var i=1; i<maxTsRowNum; i++){
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
	  var sourceleadUrl ='${sourceleadUrl}';
	  var vendorOrgRelationId = $("#vendorOrgRelationId").val();
	  
	  if(vendorOrgRelationId){
	  
	   $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getVendorInvoiceAssociatesList?${_csrf.parameterName}=${_csrf.token}&selectedAssociates="+selectedAssociates+"&vendorOrgRelationId="+vendorOrgRelationId,
	        success: function(data) {
	        	
	        	if(data != null && data != 'undefined' && data != "" && data != '[]'){
	        		
	        		
	        		    var result = '';
	        		    result = result +'     <h3>Associates List</h3>';
	             		result = result +'     <div class="modal-bodyCustom clearfix popupFilter clients_wrap_table addinvoiceTable">'; 
	             	    result = result +'       <table id="invoiceAssociatesList" class="table table-bordered table-striped  clearfix" cellspacing="0" width="100%" >';
	             		result = result +'           <thead>';
	             		result = result +'           <tr>';
	             		result = result +'             <th><input type="checkbox" id="checkAllAssociates" onclick="toggleAssociates(this);"></th>';
	             		result = result +'             <th>Associate Name</th>';
	             		result = result +'             <th>Email</th>';
	             		result = result +'             <th>Phone</th>';
	             		result = result +'           </tr>';
	             		result = result +'           </thead>';
	                    result = result +"<tbody>"; 
	                    
	                    var json = JSON.parse(data);
	                    
	                    for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
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
	                    	 result = result +'<td><input id="'+opt.id+'" value="'+opt.id+','+associateName+'"  type="checkbox" name="associates[]" class="myCheckbox" >';
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
	                 	 result = result +'     <button data-dismiss="modal" class="btn btn-primary" type="button" onClick="addInvoice();$(this).blur();">Add</button>';
	                 	 result = result +'   </div>'; 
	                 	 $("#associatesListDiv").empty(); 
	                     $("#associatesListDiv").append(result); 
	                     pagination(); 
	        		
	        	}else{
	        		
	        		var result ="";
	        		result = result +'     <h3>Associates List</h3>';
	        		result = result +'     <div class="modal-bodyCustom clearfix popupFilter clients_wrap_table addinvoiceTable">'; 
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
		
	  }else{
		  alertMsg();
	  }
		
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



 <script>
  function getSow(candidateId,id,contractId){
	  var orgRelationId = $("#vendorOrgRelationId").val();
	  var sourceleadUrl ='${sourceleadUrl}';
			  $.ajax({
			    	type: "GET",
			        url: sourceleadUrl+"/getSowcontractListByAssociateId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&orgRelationId="+orgRelationId+"&contractType=vendorSow",
			        success: function(data) {
			        	var result = "";
			        	result = result + "<option value='' >select sow</option>"
			        	if(data != null && data != ''){
			        		var json = JSON.parse(data);
				        	 for (var i=0; i<json.length; i++)
							{
					        		var opt = json[i];
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
	   var orgRelationId = $("#vendorOrgRelationId").val();
	   var currencyCode = '${SourceLeadWeb_currencyCode}';
	   var grandTotal = $("#grandTotal").val();
	   var sourceleadUrl ='${sourceleadUrl}';
	   if(grandTotal != '' && grandTotal != 'undefined'){
		   $.ajax({
				type : "GET",
				url: sourceleadUrl+"/getConversionExchangeRate?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationId+"&grandTotal="+grandTotal,
				success :function(data) {
					if(data !='' && data != 'undefined' && data != null){
						var json = JSON.parse(data);
						$("#exchangeRate").html(json.exchangeRate);
						$("#exchangeCurrency").html(json.exchangeCurrency);
						$("#vendorCurrencyCode").html(json.vendorCurrencyCode);
						$("#convertedCurrencyCode").html(currencyCode +' to '+ json.vendorCurrencyCode);
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
	    var arroundGrand = grandTotal.toFixed(2)
	    
	    
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
	    
	    $("#inoviceTotal").val(sum.toFixed(2));
	    $("#hiddenTotal").val(sum.toFixed(2));
	    $("#grandTotal").val(sum.toFixed(2));
	    $("#conversionFrom").val(sum.toFixed(2));
	    $("#afterDiscountTotal").val(sum.toFixed(2));
	    
/*  After adding the rows calculate the discount start*/
    calculateDiscount();
    
/* After adding the rows calculate the tax discount start */
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
			$("#afterDiscountTotal").val(total);
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
			$("#discountTotal").val(perCentValue);
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
	 
	calculateTaxDiscount();
	conversion();
}
</script>

<!-- ********************************************* Add Rows Invoice Table functionality End  ************************************************************ -->


<!-- ********************************************* Add Rows resume Table functionality start  ************************************************************ -->

<div id="divInvoiceAttachmentTd1" style="display: none; float: left;">
	<div class="timesheet_week_comm col-md-12 invoiceDocs invoiceDocs_add">
		<div class="form-group" id="REPLACEROWIDinvoicedocFileuploadFileGroup">
			<span> <input type="file" name='REPLACEROWIDinvoicedocFile'
				id='REPLACEROWIDinvoicedocFile'
				onchange='attachFile(REPLACEROWID);validateContractsUploadFile(REPLACEROWID)'> <i
				class="fa fa-times" mouseOver="Delete" title="Delete"
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
				id='REPLACEROWIDtaxType'> <input type="hidden"
				name="REPLACEROWIDinvoiceTaxId" id="REPLACEROWIDinvoiceTaxId">
		</div>
	</div>
</div>

<div id="divTaxTd2" style="display: none; float: left;">
	<div class="btn-group tblminwidth-75">
		<select class="form-control input-sm" id="REPLACEROWIDtaxdiscountType" name="invoiceTax[REPLACEROWID].taxdiscountType" onchange="calculateTaxDiscount(REPLACEROWID);">
			<option value="percentage">Percentage</option>
			<option value="flat">Flat</option>
		</select>
	</div>
</div>

<div id="divTaxTd3" style="display: none; float: left;">
	<div class="tblminwidth-75">
		<input type="text"
			class="form-control input-sm allownumericwithdecimal" value=""
			placeholder="Flat / Discount" name='invoiceTax[REPLACEROWID].taxdiscountvalue'
			id='REPLACEROWIDtaxdiscountvalue'
			onblur="calculateTaxDiscount(REPLACEROWID);">
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
		
		//After removing the row calculate the amount and append the grand total
		var grandTotal =  $("#grandTotal").val();
		var taxTotal = $("#"+rowId+"taxTotal").val(); 
		var first=parseFloat(grandTotal);
        var second=parseFloat(taxTotal);
        total = (first-second);
        $("#grandTotal").val(total);
       //=======================================
        conversion();
		document.getElementById('divTaxRow' + rowId).style.display = 'none';
		var r = document.getElementById('addTaxTbl').rows[rowId];
		var count = 2 * (r.cells.length);
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



<!-- ********************************************* Add Rows AddTax Table functionality end  ************************************************************ -->
<script>
	$(function() {
		var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}';  
		var finalDateFormatinvoiceDate = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatpayableDate = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatpayableDate = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatpayableDate = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatpayableDate = 'yy-dd-mm'; 
		  }
		$('#invoiceDate').datepicker({
			dateFormat : finalDateFormatpayableDate,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '+1d');
			dt.setDate(dt.getDate() + 1);
			$("#dueDate").datepicker("option", "minDate", dt);
					errInvoiceDate();
					getDueDateByFrequency();
					getEstimatedHours
			}
		});
	  $('#dueDate').datepicker({
			dateFormat : finalDateFormatpayableDate,
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
	 /*  $(function() {
	    $( "#invoiceDate, #dueDate" ).datepicker({
	    	dateFormat : 'dd-mm-yy',
		  changeMonth: true,
	      changeYear: true,
	    });
	  }); */
	  
	  $( "#0sdate-input" ).datepicker({
		  changeMonth: true,
	      changeYear: true,
	      dateFormat : 'dd-mm-yy',
	      minDate: 0  ,
	      onSelect : function(selected) {
			  
			  var dt = $(this).datepicker('getDate', '-1d');
	            dt.setDate(dt.getDate() - 1);
	            $("#"+maxRowNum+"sdate-input").datepicker("option", "maxDate", dt);
	            getEstimatedHours(maxRowNum);
			 /*  var dt = toDate("#"+maxRowNum+"edate-input");
				dt.setDate(dt.getDate() - 1);
				$("#"+maxRowNum+"sdate-input").datepicker("option", "maxDate", dt); */
	 	  }
	    });
	
	$( "#0edate-input" ).datepicker({
		  changeMonth: true,
	      changeYear: true,
	      dateFormat : 'dd-mm-yy',
	      minDate: 0  ,
	      onSelect : function(selected) {
			  
			  var dt = $(this).datepicker('getDate', '-1d');
	            dt.setDate(dt.getDate() - 1);
	            $("#"+maxRowNum+"sdate-input").datepicker("option", "maxDate", dt);
	            getEstimatedHours(maxRowNum);
			 /*  var dt = toDate("#"+maxRowNum+"edate-input");
				dt.setDate(dt.getDate() - 1);
				$("#"+maxRowNum+"sdate-input").datepicker("option", "maxDate", dt); */
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
function getVendors(){
	var sourceleadUrl ='${sourceleadUrl}';
	var orgRelationId ='${orgRelationId}';
	$.ajax({
		type : "GET",
		url: sourceleadUrl+"/getPayableVendorsList?${_csrf.parameterName}=${_csrf.token}",
		success :function(data) {
		var json = JSON.parse(data);
		var output = "";
		output = output + "<option value=''>Select Vendor</option>";
		for (var i=0; i<json.length; i++)
		{
		var opt = json[i];
		if(orgRelationId == opt.id){
			var selected = 'selected';
			output = output + "<option value='" +opt.id+ "'"+selected+">"+opt.orgName+"</option>";
		}else{
			output = output + "<option value='" +opt.id+ "'>"+opt.orgName+"</option>";
		}
		}
		$("#vendorOrgRelationId").empty();
		$("#vendorOrgRelationId").append(output);
		$('#vendorOrgRelationId').trigger("chosen:updated");
		
		if(orgRelationId != null && orgRelationId != '' && orgRelationId != 'undefined'){
			getVendorContacts();
			errRemoveVendor();
			getPaymentTerms();
		}
	},
	});

	}
</script>


<script>
function getVendorContacts(vendorContactId){
	var orgRelationId = $("#vendorOrgRelationId").val();
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
		type : "GET",
		url: sourceleadUrl+"/getPayableVendorContactsByOrgRelationId?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationId,
		success :function(data) {
		var json = JSON.parse(data);
		var output = "";
		output = output + "<option value=''>Select Vendor Contact</option>";
		for (var i=0; i<json.length; i++)
		{
		var opt = json[i];
		
		var selected = '';
		if(vendorContactId != null && vendorContactId != "" && vendorContactId !="undefined" && vendorContactId == opt.spocUserId){
			selected = 'selected';
		}
		output = output + "<option value='" +opt.spocUserId+ "' "+selected+">"+opt.ownerName+"("+opt.spocName+")</option>";
		$("#vendorCurrency").val(opt.vendorCurrency);
		}
		$("#vendorContact").empty();
		$("#vendorContact").append(output);
		$('#vendorContact').trigger("chosen:updated");
		if(vendorContactId == null || vendorContactId == '' || vendorContactId == 'undefined'){
			buildLineItemAndTaxAddRows();
		}
	},
	});

	}
</script>

<script> 
function getPaymentTerms(){
	var orgRelationId = $("#vendorOrgRelationId").val();
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
		type : "GET",
		url: sourceleadUrl+"/getPaymentTermsAndBillindAddress?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationId,
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
 	 	    var sourceleadUrl ='${sourceleadUrl}';
 	 	  var formData = getFormData("invoice_form");
         
 	 	    if(selectedAssociates == null || selectedAssociates == '' || selectedAssociates == 'undefined'){
	        	var result = "";
	     		result = result +"	<div class='project_inn_mask'></div>";
	     		result = result +"	   <div id='invoice_pop' class='remove_user_pop remove_user_pop_a'>";
	     		result = result +"		<p>At least one Add column Payable Line Item</p>";
	     		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	     		result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	     		result = result +"	</div>";
	     		$("#popUp").append(result);
	     		$("#invoice_pop").slideDown(200);
	     		$(".project_inn_mask").fadeIn(200);
		        }else{
		        	
		    	        
		        	  $("#invoiceLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		        	  $.post(sourceleadUrl+"/saveInvoice?${_csrf.parameterName}=${_csrf.token}&saveType="+saveType+"&invoiceType=payable", formData, function(data) {
			  				if(data == "success"){
			  					$("#invoiceLoading").removeAttr("style");
	    		     			location.replace(sourceleadUrl+"/invoiceList");
			  				}
			  			   }); 
		        	  /*       $.ajax({
		    		    	 type: "POST",
		    		    	 data: formData,
		    			     contentType : 'application/json; charset=utf-8',
		    			     enctype: 'multipart/form-data',
		    		         processData: false,
		    		         contentType:false,
		    			     dataType : 'json',
		     		    	 url: sourceleadUrl+"/saveInvoice?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&saveType="+saveType+"&invoiceType=payable", 
		    		        success: function(result) {
		    		     		$("#invoiceLoading").removeAttr("style");
		    		     			location.replace(sourceleadUrl+"/payablesList");
		    		        },
		    		    });  */
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
    $(".inline_field").mask("Hhh:Mm"); 
    
    $(".allownumericwithdecimal").on("keypress keyup blur",function (event) {
    $(this).val($(this).val().replace(/[^0-9\.]/g,''));
        if ((event.which != 46 || $(this).val().indexOf('.') != -1) && (event.which < 48 || event.which > 57)) {
            event.preventDefault();
        }
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
function getSowRate(id){
	var contractId = $("#"+id+'sow').val();
	var sourceleadUrl ='${sourceleadUrl}';
	if(contractId !='' && contractId != 'undefined' && contractId != null){
	$.ajax({
    	type: "GET",
    	url: sourceleadUrl+"/getSowRateByContractId?${_csrf.parameterName}=${_csrf.token}&contractId="+contractId,
        success: function(data) {
        	 if(data != null && data != ''){
        		var json = JSON.parse(data);
	        	 $ ('#'+id+'rate').val(json.billingAmount);
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
function getDueDateByFrequency(){
	 var invoiceDate = document.getElementById("invoiceDate").value;
	 var paymentFrequency = document.getElementById("paymentFrequency").value;
	 var locationCode ='${SourceLeadWeb_locationCode}';
	
	 if(paymentFrequency){
	    var sourceleadUrl ='${sourceleadUrl}';
	    $("#invoiceLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	    
	    $.ajax({
			type : "GET",
			url: sourceleadUrl+"/getDueDateByFrequency?${_csrf.parameterName}=${_csrf.token}&paymentFrequency="+paymentFrequency+"&invoiceDate="+invoiceDate+"&locationCode="+locationCode,
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
		 var sourceleadUrl ='${sourceleadUrl}';
		 var averageWorkingHours = '${SourceLeadWeb_orgLocations.averageWorkingHours}';
		 $("#resourceListLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getTotalHosursForPayable?${_csrf.parameterName}=${_csrf.token}&startDate="+startDate+"&endDate="+endDate+"&averageWorkingHours="+averageWorkingHours+"&locationId="+locationId,
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
		 var sourceleadUrl ='${sourceleadUrl}';
		 var locationId = '${SourceLeadWeb_orgLocationId}';
		 var averageWorkingHours = '${SourceLeadWeb_orgLocations.averageWorkingHours}';
		 $("#resourceListLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getTotalHosursForPayable?${_csrf.parameterName}=${_csrf.token}&startDate="+startDate+"&endDate="+endDate+"&averageWorkingHours="+averageWorkingHours+"&locationId="+locationId,
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

</script>

<script type="text/javascript">
 function attachFile(id){
	var sourceleadUrl ='${sourceleadUrl}';
	fileUpload(sourceleadUrl, ""+id+"invoicedocFile", "incoiceDocuments", "invoice_form","fileInfo");
}
</script>

<jsp:include page="../masterFooter.jsp" />