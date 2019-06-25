<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<script src="/resources/js/jquery.mask.min.js"></script>
<script src="/resources/js/jquery.maskedinput-1.3.min.js"></script>
<c:set var="rowIndex" value="0" scope="page" /><!-- used for dynamic row index  rowindex starts from 0-->
<div class="wraper"> 
  
 <script>
 var vmsUrl ='${SourceLeadWeb_vmsUrl}';
 </script>
 
  <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Add Credit Note</h2>
      </div>
       <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; 
       <a href="${SourceLeadWeb_vmsUrl}/creditNoteList">Credit Note List</a> &raquo; Credit Note</div>

    </section>
    <div class="content-area content-new clearfix">
    <div id="creditDebitLoading"></div>
      <div class="clearfix mt formgroup-minheight">
      <form id="creditNote_form" name ="creditNote_form" method="Post">
      <input type = "hidden" name = "savedNoteId" value ="${creditDebitNote.savedNoteId}">
      <input type="hidden" name="clientCurrency" id="clientCurrency">
		<div class="col-xs-12 col-sm-6 col-md-6">
					<div class="form-group row">
					  <label class="col-md-3">Client<span class="text-danger">&nbsp;*</span></label>
					  <div class="col-md-9">
    	 						<select class="form-control chosen-select" id="orgRelationId" name="orgRelationId" field-name="Client" onchange="getSpocsList();getInvoiceList();" data-validation="required" >
		                        <option value="">-Select Client-</option>
		                        <c:forEach var="clients" items="${clientsList}">
								<option ${creditDebitNote.orgRelationId == clients.id ? 'selected': ''} value="${clients.id}">${clients.orgName}</option>
									
								</c:forEach>
			              </select>
    	 				</div>
					</div>
					</div>
		<div class="col-xs-12 col-sm-6 col-md-6">
		<div class="form-group row">
		<label class="col-md-3">Contact<span class="text-danger">&nbsp;*</span></label>
		<div class="col-md-9">
		 <select multiple="multiple" class="chosen-select form-control" id="spocUser" name="spocUser" data-validation="required" field-name="Client Contact"></select>
		</div>
		</div>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-6">
		<div class="form-group row">
		<label class="col-md-3 paddingRight">Ref. ID</label>
		<div class="col-md-9">
		<input type="text" class="form-control" value="${creditDebitNote.referenceNumber}" id="referenceNumber" name="referenceNumber" field-name="Ref. ID" data-validation-optional="true" data-validation="validate_length length2-100" placeholder="Ref. ID">
		</div> 
		</div>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-6">
		<div class="form-group row">
		<label class="col-md-3">Invoice Address</label>
		<div class="col-md-9">
		<input type="text" class="form-control" value="${creditDebitNote.billingAddress}" id="billingAddress" name="billingAddress" field-name="Billing Address" placeholder="Billing Address" data-validation="validate_length length2-100" data-validation-optional="true">
		</div>
		</div>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-6">
		<div class="form-group row">
		<label class="col-md-3 paddingRight">Shipping Address</label>
		<div class="col-md-9">
		<input type="text" class="form-control" value="${creditDebitNote.shippingAddress}" id="shippingAddress" name="shippingAddress" placeholder="Shipping Address" field-name="Shipping Address" data-validation="validate_lengrh length2-100" data-validation-optional="true">
		</div>
		</div>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-6">
		<div class="form-group row">
		<label class="col-md-3 paddingRight">Credit Note Date</label>
		<div class="col-md-9" id= "creditDebitNotedateDiv">
    	<input class="form-control calendar" placeholder="Credit Note Date" type="text" value = "${creditDebitNote.creditDebitNotedate}"  id="creditDebitNotedate" name="creditDebitNotedate" field-name="Credit Note Date" readonly>
    	</div>
		</div>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-6">
		<div class="form-group row">
		<label class="col-md-3 paddingRight">Invoice<span class="text-danger">&nbsp;*</span></label>
		<div class="col-md-9">
		<select multiple="multiple" class="chosen-select form-control" id="invoceId" name="invoceId" data-validation="required" field-name="Invoice"></select>
		</div>
		</div>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-6"> 
		<div class="form-group row">
		<label class="col-md-3 paddingRight">Reason</label>
		<div class="col-md-9">
		<textarea class="form-control" cols="2" id="reason" name="reason" field-name="Reason" placeholder="Reason" data-validation="validate_length length2-250" data-validation-optional="true">${creditDebitNote.reason}</textarea>
		</div>
		</div>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-6">
		<div class="form-group row" id="approversDivId" style="display: none;">
		<label class="col-md-3 paddingRight">Approvers<span class="text-danger">&nbsp;*</span></label>
		<div class="col-md-9">
		<select multiple="multiple" class="chosen-select form-control" value="" id="approvers" name="approvers" field-name="Approvers"  placeholder="Approvers">
		</select>
		</div>
		</div>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-6"> 
		<div class="form-group row">
        <label class="col-md-3 paddingRight">Payment Account</label>
        <div class="col-md-9">
          <select class="form-control sel" id="account"  field-name="paymentAccountId Name" name="account">
             <option value="">--Select Account--</option>
              <c:forEach items="${paymentAccountList}" var="paymentAccount">
                  <option value="${paymentAccount.id}" ${paymentAccount.id == creditDebitNote.account ? 'selected' : ""}>${paymentAccount.accountType == "WEPAY" ? paymentAccount.email.concat(" (WePay)") : paymentAccount.accountNumber.concat(" (").concat(paymentAccount.bankName).concat(")")}</option>
              </c:forEach>
          </select>
        </div>
      </div>
      </div>
		<div class="col-xs-12 col-sm-6 col-md-6">
		<div class="form-group">
              <label class="col-md-3">&nbsp;</label>
              <div class="col-md-9 checkBox">
              <input type="checkbox"  name="approverCheckBox" value="${creditDebitNote.approverCheckBox}" ${creditDebitNote.approverCheckBox == 'Y' ? 'checked' : ''} class="paddingRight" id="approverCheckBox" onclick="approverCheck()"> Approver </div>
            </div>
          </div>  
		<div class="clearfix mb"></div>
		 <!-- Table starts here-->
          <div class="project_wrap_table mt25 invoice_table" id="invoiceTable">
          <table width="100%">
            <thead>
              <tr>
              	<th>Date</th>
                <th>Description</th>
                <th>Associates</th>
                <th>Quantity</th>
                <th>Price</th>
                <th>Tax Type</th>
                <th>Tax Amount</th>
                <th>Amount</th>
                <th>Total Amount</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody id="tableId">
            <c:if test="${empty creditDebitNote.creditDebitNoteLineItems}">
              <tr>
               <input type="hidden" name="creditDebitMaxRowNumber"  id="creditDebitMaxRowNumber0" value="0"/>
              <td>
                	<div class="width150px">
                	<input class="form-control calendar" placeholder="Date" type="text" value = "" id="lineItemDate0" name="creditDebitNoteLineItems[0].lineItemDate" onclick="datePickerFunction(0);" field-name="Date" readonly>

                	</div>
                </td>
                <td>
                	<div class="width150px">
                		<textarea placeholder="Description" rows="1" class="form-control" id="inlineDescription0" name="creditDebitNoteLineItems[0].inlineDescription" field-name="Description" data-validation="required validate_length length2-250"></textarea>
                	</div>
                </td>
                <td>
                  <div class="width150px">
                  	<select class="sel" id="associates0" name="creditDebitNoteLineItems[0].associates">
                  	<option selected value="">Select Associate</option>
                  		<c:forEach var="approvers" items="${approversList}">
					<option value="${approvers.id}">${approvers.firstName}</option>	</c:forEach>
					</select>
                  </div>
                </td>
                <td>
                    <div class="width90px">
                    <input type="text" class="form-control" placeholder="Quantity" id="quantity0" name="creditDebitNoteLineItems[0].quantity" field-name="Quantity" onkeyup="totalCalculateAmount(0);validateNumber(this);" data-validation="required validate_length length1-10">
                  </div>
                </td>
                <td>
                  <div class="width100px">
                    <input type="text" class="form-control" placeholder="Price" id="price0" name="creditDebitNoteLineItems[0].price" field-name="Price" onkeyup="totalCalculateAmount(0);validateNumber(this);" data-validation="required validate_length length1-10">
                  </div>
                </td>
                <td>
                  <div class="width100px">
                    <select class="form-control sel" id="taxType0" name="creditDebitNoteLineItems[0].taxType" onchange="taxCalculatedAmount(0);">
                      <option value="Percentage">Percentage</option>
                      <option value="Flat">Flat</option>
                    </select>
                  </div>
                </td>
                <td>
                	<div class="width110px">
                    <input type="text" class="form-control" placeholder="Tax Amount" id="taxAmount0" name="creditDebitNoteLineItems[0].taxAmount" onkeyup="taxCalculatedAmount(0);validateNumber(this);">
                  </div>
                </td>
                <td>
                	<div class="width110px">
                    <input type="text" class="form-control" placeholder="Amount" id="rowTotal0" name="creditDebitNoteLineItems[0].rowTotal" readonly="readonly">
                  </div>
                </td>
                
                <td>
                	<div class="width110px">
                    <input type="text" class="form-control" placeholder="Total Amount" id="rowTotalAmount0" name="creditDebitNoteLineItems[0].rowTotalAmount" readonly="readonly">
                  </div>
                </td>
                <td><a class="text-danger"  title="Remove row"><i class=""></i></a></td>
              </tr>
             <!--setting rowIndex as 1 when default row is loaded -->
              <c:set var="rowIndex" value="${rowIndex + 1}" scope="page"/>
              </c:if>
              <c:if test="${!empty creditDebitNote.creditDebitNoteLineItems}">
              <c:forEach var="items" items="${creditDebitNote.creditDebitNoteLineItems}" varStatus="rows">
              
              <tr id ="dynamicAddrowDiv${rows.count-1}">
               <input type="hidden" name="creditDebitNoteLineItems[${rows.count-1}].lineItemId" value="${items.lineItemId}" id="lineItemId${rows.count-1}"/>
          
               <input type="hidden" name="creditDebitMaxRowNumber"  id="creditDebitMaxRowNumber${rows.count-1}" value="${rows.count-1}"/>
              <td>
                	<div class="width150px">
                	<input class="form-control calendar" placeholder="Date" type="text" value = "${items.lineItemDate}" id="lineItemDate${rows.count-1}" name="creditDebitNoteLineItems[${rows.count-1}].lineItemDate" onclick="datePickerFunction(${rows.count-1});" field-name="Date" readonly>

                	</div>
                </td>
                <td>
                	<div class="width150px">
                		<textarea placeholder="Description" rows="1" class="form-control" id="inlineDescription${rows.count-1}" name="creditDebitNoteLineItems[${rows.count-1}].inlineDescription" field-name="Description" data-validation="required validate_length length2-250">${items.inlineDescription}</textarea>
                	</div>
                </td>
                <td>
                  <div class="width150px">
                  	<select class="sel" id="associates${rows.count-1}" name="creditDebitNoteLineItems[${rows.count-1}].associates">
                  	<option selected value="">Select Associate</option>
                  		<c:forEach var="approvers" items="${approversList}">
                  		<option ${items.associates == approvers.id ? 'selected': ''} value="${approvers.id}">${approvers.firstName}</option>
					</c:forEach>
					</select>
                  </div>
                </td>
                <td>
                    <div class="width90px">
                    <input type="text" class="form-control" placeholder="Quantity" id="quantity${rows.count-1}" value = "${items.quantity}" name="creditDebitNoteLineItems[${rows.count-1}].quantity" field-name="Quantity" onkeyup="totalCalculateAmount(${rows.count-1});validateNumber(this);" data-validation="required validate_length length1-10">
                  </div>
                </td>
                <td>
                  <div class="width100px">
                    <input type="text" class="form-control" placeholder="Price" id="price${rows.count-1}" value = "${items.price}" name="creditDebitNoteLineItems[${rows.count-1}].price" field-name="Price" onkeyup="totalCalculateAmount(${rows.count-1});validateNumber(this);" data-validation="required validate_length length1-10">
                  </div>
                </td>
                <td>
                  <div class="width100px">
                    <select class="form-control sel" id="taxType${rows.count-1}" name="creditDebitNoteLineItems[${rows.count-1}].taxType" onchange="taxCalculatedAmount(${rows.count-1});">
                       <option  ${items.taxType  == 'Percentage' ? 'selected' : ''}>Percentage</option>
                         <option  ${items.taxType  == 'Flat' ? 'selected' : ''}>Flat</option>
                    </select>
                  </div>
                </td>
                <td>
                	<div class="width110px">
                    <input type="text" class="form-control" placeholder="Tax Amount" id="taxAmount${rows.count-1}" value = "${items.taxAmount}" name="creditDebitNoteLineItems[${rows.count-1}].taxAmount" onkeyup="taxCalculatedAmount(${rows.count-1});validateNumber(this);">
                  </div>
                </td>
                <td>
                	<div class="width110px">
                    <input type="text" class="form-control" placeholder="Amount" id="rowTotal${rows.count-1}" value = "${items.rowTotal}" name="creditDebitNoteLineItems[${rows.count-1}].rowTotal" readonly="readonly">
                  </div>
                </td>
                
                <td>
                	<div class="width110px">
                    <input type="text" class="form-control" placeholder="Total Amount" value = "${items.rowTotalAmount}" id="rowTotalAmount${rows.count-1}" name="creditDebitNoteLineItems[${rows.count-1}].rowTotalAmount" readonly="readonly">
                  </div>
                </td>
                <c:if test="${rows.count > 1}"> 
                <td><a class="text-danger"  title="Remove row"><i class="fa fa-remove" onclick="removeDynamicRowDiv(${rows.count-1},${items.lineItemId});"></i></a></td>
              </c:if>
              </tr>
              <!--increment the rowIndex  for dynamic rows after save or page navigation  -->
              <c:set var="rowIndex" value="${rowIndex + 1}" scope="page"/>
             </c:forEach>
              </c:if>
              
            </tbody>
          
          </table>
        </div>
        <!-- Adding Content Starts herer -->
          <div class="clearfix invoice_other">
          <div class="col-md-5">
            <div class="clearfix mb">
              <button type="button" class="btn btn-sm btn-primary mr" onClick="cloneRows()"><i class="fa fa-plus paddingRight"></i>Add</button>
            </div>
          </div>
          <div class="col-md-7">
            <div class="form-horizontal">
              <div class="form-group">
                <label class="control-label col-md-7"><strong>Sub Total</strong></label>
                <div class="col-md-5 text-right"><span class="pull-left"></span><input type="text" value = "${creditDebitNote.subTotalAmount}" id="subTotalAmount" name="subTotalAmount" class="ReadTextFli" readonly></div>
              </div>
              <!-- Discount UI Starts-->
              <div class="form-group">
                <div class="col-md-7 select-field">
                  <div class="row">
                    <div class="col-md-7 paddingRight">
                      <select class="form-control input-sm" id="disCount" name="discountType" onchange="calculateDisCount();">
                         <option  ${creditDebitNote.discountType  == 'Percentage' ? 'selected' : ''}>Percentage</option>
                         <option  ${creditDebitNote.discountType  == 'Flat' ? 'selected' : ''}>Flat</option>
                      </select>
                    </div>
                    <div class="col-md-5 paddingLeft" id="discountValueDiv">
                      <input class="form-control input-sm allownumericwithdecimal" id="discountValue" value = "${creditDebitNote.discountValue}" placeholder="Flat / Discount" name="discountValue" type="text" onkeyup="calculateDisCount();validateNumber(this);">
                    </div>
                  </div>
                </div>
                <div class="col-md-5 text-right">
                  <input class="form-control input-sm"  id="discountTotal" name="discountTotal" value = "${creditDebitNote.discountTotal}" type="text" placeholder="00.00" readonly>
                </div>

              </div>
              
              <!-- Discount UI Ends-->
               <div class="form-group mt">
                <label class="col-md-7 text-right"><strong>Total</strong></label>
                    <div class="col-md-5 text-right"><span class="pull-left"></span><input type="text" value = "${creditDebitNote.grandTotal}" id="grandTotal" name="grandTotal" class="ReadTextFli" readonly></div>
              </div>
              <div id="conversionId"  style="display: none"></div>
              </div>
              </div>
          </div>
		<div class="form-footer clearfix contrFoot">
        <div class="col-lg-12">
          <div class="pull-right">
          <input value="Draft" id="draftButtonId" onClick="saveCreditNote('Draft');" class="btn btn-warning" type="button">
            <input value="Send" id="sendButtonId" onClick="saveCreditNote('Send');" class="btn btn-success mr ml" type="button">
            <input value="Submit" id = "submitButtonId" onClick="saveCreditNote('Submit');" class="btn btn-primary" type="button">
            <input value="Cancel" class="btn btn-default ml" onClick="javascript:window.location.href = '${SourceLeadWeb_vmsUrl}/creditNoteList'" type="button">
          </div>
        </div>
      </div>
       </form>
        </div>
      
        </div>
   </section>     
      </div>
 
<script type="text/javascript">
$(document).ready(function(){
	getSpocsList();
	getInvoiceList();
	approverCheck();
	getApproversList();
	});

function saveCreditNote(saveType){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	if ($('#creditNote_form').validate(false, validationSettings)){  
	 	    var formData = getFormData1("creditNote_form");
	 	   
	        	  $("#creditDebitLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	        	  $.ajax({
	                  url: vmsUrl+"/saveCreditDebitNote?"+tokenParamName+"="+tokenValue+"&saveType="+saveType+"&noteType=Credit",
	                  type: "POST",
	                  data: formData,
	                  processData: false,
	                  contentType: false,
	                  success: function (result) {
	        		  if (result && result.response === "INVALID_DATA") {
	          		    showError(result.responseMessage);
	          		} else if (result.response === "AWKWARD") {
	          		    location.href = "/errorPage";
	          		} else if (result.response === "SUCCESS") {
		  					$("#creditDebitLoading").removeAttr("style");
    		     			location.replace(vmsUrl+"/creditNoteList");
    		     			$("#selector1").removeAttr("style");
				    		$("#selector1").css({ display: "block" });
			    		if(saveType == 'SENT'){
			    			$("#msgDiv").html("Successfully you have sent a Invoice..");
			    		}else if(saveType == 'DRAFT'){
			    			$("#msgDiv").html("Invoice saved Successfully..");
			    		}
		     			msgFadein();
	          		} else if (result.indexOf('Sign In') !== -1) {
	          			location.replace(vmsUrl);
	          		} else {
	          		    showError("Operation failed, please try again");
	          		}
	        		  $("#creditDebitLoading").hide();
	        	  }  
	  			   }); 
	        	  
	    	         return true; 
     }else{
    		return false;
    	  }
	
}
$('body').on('blur', '#creditNote_form', function() {
	$("#creditNote_form").showHelpOnFocus();
	$("#creditNote_form").validateOnBlur(false, validationSettings);  
}); 
</script> 

<!-- Add row functionality -->
<script type="text/javascript">

var rowId ='<c:out value ="${rowIndex}"/>'// set the value for dyamic row when clicked on add button in UI.;
function cloneRows(){
   $("#tableId").append(prepareDynamicRoweDiv(rowId++)); 
}

function prepareDynamicRoweDiv(rowId){
	var rowDiv ='';
   rowDiv =rowDiv+ '<tr id ="dynamicAddrowDiv'+rowId+'">';
   rowDiv =rowDiv+ '<input type="hidden" name="creditDebitMaxRowNumber"  id="creditDebitMaxRowNumber'+rowId+'" value="'+rowId+'"/>';
   rowDiv =rowDiv+ ' <td>';
   rowDiv =rowDiv+ '	<div class="width150px">';
   rowDiv =rowDiv+ '	<input class="form-control calendar" placeholder="Date" type="text" value = "" id="lineItemDate'+rowId+'" name="creditDebitNoteLineItems['+rowId+'].lineItemDate" field-name="Date" readonly onclick="datePickerFunction('+rowId+');">';
   rowDiv =rowDiv+ '	</div>';
   rowDiv =rowDiv+ '</td>';
   rowDiv =rowDiv+ '<td>';
   rowDiv =rowDiv+ '	<div class="width150px">';
   rowDiv =rowDiv+ '		<textarea placeholder="Description" rows="1" class="form-control" id="inlineDescription'+rowId+'" data-validation="required validate_length length2-250" name="creditDebitNoteLineItems['+rowId+'].inlineDescription" field-name="Description"></textarea>';
   rowDiv =rowDiv+ '	</div>';
   rowDiv =rowDiv+ '</td>';
   rowDiv =rowDiv+ '<td>';
   rowDiv =rowDiv+ '  <div class="width150px">';
   rowDiv =rowDiv+ '  	<select class="sel" id="associates'+rowId+'" name="creditDebitNoteLineItems['+rowId+'].associates">';
   rowDiv =rowDiv+ '  	<option selected value="">Select Associate</option>';
   rowDiv =rowDiv+ '  		<c:forEach var="approvers" items="${approversList}">';
   rowDiv =rowDiv+ '	<option value="${approvers.id}">${approvers.firstName}</option>	</c:forEach>';
   rowDiv =rowDiv+ '	</select>';
   rowDiv =rowDiv+ '  </div>';
   rowDiv =rowDiv+ '</td>';
   rowDiv =rowDiv+ '  <td>';
   rowDiv =rowDiv+ '      <div class="width90px">';
   rowDiv =rowDiv+ '      <input type="text" class="form-control" placeholder="Quantity" id="quantity'+rowId+'" name="creditDebitNoteLineItems['+rowId+'].quantity" field-name="Quantity" onkeyup="totalCalculateAmount('+rowId+');validateNumber(this);" data-validation="required validate_length length1-10">';
   rowDiv =rowDiv+ '    </div>';
   rowDiv =rowDiv+ '  </td>';
   rowDiv =rowDiv+ '  <td>';
   rowDiv =rowDiv+ '    <div class="width100px">';
   rowDiv =rowDiv+ '      <input type="text" class="form-control" placeholder="Price" id="price'+rowId+'" name="creditDebitNoteLineItems['+rowId+'].price" field-name="Price" onkeyup="totalCalculateAmount('+rowId+');validateNumber(this);" data-validation="required validate_length length1-10">';
   rowDiv =rowDiv+ '    </div>';
   rowDiv =rowDiv+ '  </td>';
   rowDiv =rowDiv+ '  <td>';
   rowDiv =rowDiv+ '    <div class="width100px">';
   rowDiv =rowDiv+ '      <select class="form-control sel" id="taxType'+rowId+'" name="creditDebitNoteLineItems['+rowId+'].taxType" onchange="taxCalculatedAmount('+rowId+');">';
   rowDiv =rowDiv+ '        <option value="Percentage">Percentage</option>';
   rowDiv =rowDiv+ '        <option value="Flat">Flat</option>';
   rowDiv =rowDiv+ '      </select>';
   rowDiv =rowDiv+ '    </div>';
   rowDiv =rowDiv+ '  </td>';
   rowDiv =rowDiv+ '  <td>';
   rowDiv =rowDiv+ '  	<div class="width110px">';
   rowDiv =rowDiv+ '      <input type="text" class="form-control" placeholder="Tax Amount" id="taxAmount'+rowId+'" name="creditDebitNoteLineItems['+rowId+'].taxAmount" onkeyup="taxCalculatedAmount('+rowId+');validateNumber(this);">';
   rowDiv =rowDiv+ '    </div>';
   rowDiv =rowDiv+ '  </td>';
   rowDiv =rowDiv+ '  <td>';
   rowDiv =rowDiv+ '  	<div class="width110px">';
   rowDiv =rowDiv+ '      <input type="text" class="form-control" placeholder="Amount" id="rowTotal'+rowId+'" name="creditDebitNoteLineItems['+rowId+'].rowTotal" readonly="readonly">';
   rowDiv =rowDiv+ '    </div>';
   rowDiv =rowDiv+ '  </td>';
   rowDiv =rowDiv+ '       <td>';
   rowDiv =rowDiv+ '  	<div class="width110px">';
   rowDiv =rowDiv+ '      <input type="text" class="form-control" placeholder="Total Amount" id="rowTotalAmount'+rowId+'" name="creditDebitNoteLineItems['+rowId+'].rowTotalAmount" readonly="readonly">';
   rowDiv =rowDiv+ '    </div>';
   rowDiv =rowDiv+ '  </td>';
   rowDiv =rowDiv+ '  <td><a class="text-danger"  title="Remove row"><i class="fa fa-remove" onclick="removeDynamicRowDiv('+rowId+');"></i></a></td>';
   rowDiv =rowDiv+ '   </tr>';
	return rowDiv; 
}
//var removedResidenceIds = [];
function removeDynamicRowDiv(id,lineItemId){
		
	var rowAmount = $("#rowTotalAmount"+id).val();
	   var subAmount = $("#subTotalAmount").val();
	   if((rowAmount >0) && (subAmount > 0)){
	   var substractAmount =  subAmount - rowAmount;
	   $("#subTotalAmount").val(substractAmount.toFixed(2));
	   $("#grandTotal").val(substractAmount.toFixed(2));
	   }  
	
	$("#dynamicAddrowDiv"+id).remove();
   
	totalCalculateAmount(rowId);
	if(lineItemId !=null && lineItemId !='' && lineItemId !='undefined'){
		deleteLinteItemEditMode(lineItemId);
		}
   rowId--;// decrement rowid when deleted in UI.
}

</script>

<script>

	function getSpocsList() {
		var vmsUrl ='${SourceLeadWeb_vmsUrl}';
		var spocIds = "${creditDebitNote.contactUser}";
		var orgRelationId = $("#orgRelationId").val();
		$("#spocUser").empty();
		$.ajax({
					type : "GET",
					url : vmsUrl + "/getClientContactsByOrgRelationId?"+ tokenParamName + "=" + tokenValue + "&orgRelationId=" + orgRelationId,
					success : function(json) {
						var output = "";
						if(json.data !=null){
						for (var i = 0; i < json.data.length; i++) {
							var opt = json.data[i];
							if(spocIds != ""){
								var arraySpocIds = spocIds.split(",");
								if(arraySpocIds.includes(opt.spocUserId.toString())){
									output = output	+ "<option value='" +opt.spocUserId+ "' selected >"+ opt.ownerName + "(" + opt.spocName+ ")</option>";
								}else{
									output = output	+ "<option value='" +opt.spocUserId+ "'>"+ opt.ownerName + "(" + opt.spocName+ ")</option>";
								}
							}else{
								output = output	+ "<option value='" +opt.spocUserId+ "'>"+ opt.ownerName + "(" + opt.spocName+ ")</option>";
							}
							$("#clientCurrency").val(opt.clientCurrency);
						}
						}
						$("#spocUser").append(output);
						$('#spocUser').trigger("chosen:updated");
						currencyConversionDiv();
					},
				});
		
	}
	
	function deleteLinteItemEditMode(id) {
		var vmsUrl ='${SourceLeadWeb_vmsUrl}';
		$.ajax({
			type : "POST",
			url : vmsUrl + "/deleteLineItemEditModeId?"+ tokenParamName + "=" + tokenValue + "&id=" + id,
			success: function(data) {
   		    },
		});
	}
</script>

<script>
	var checkVal = $("#approverCheckBox").val();
	if(checkVal == "N"){
		$("#submitButtonId").hide();	
	}
	function approverCheck() {
		var checked = document.getElementById("approverCheckBox").checked
		if (checked == true) {
			$("#approverCheckBox").val("Y");
			$("#approversDivId").show();
			$("#submitButtonId").show();
			$("#sendButtonId").hide();
			$("#draftButtonId").hide();
			$("#approvers").attr("data-validation","required");
		} else {
			$("#approverCheckBox").val("N");
			$("#approversDivId").hide();
			$("#submitButtonId").hide();
			$("#sendButtonId").show();
			$("#draftButtonId").show();
			$("#approvers").removeAttr('data-validation');
			$("#approvers").find("p.jquery_form_error_message").remove();
		}
	}

	function getInvoiceList() {
		var vmsUrl ='${SourceLeadWeb_vmsUrl}';
		var invoiceIds = "${creditDebitNote.invoceId}";
		var orgRelationId = $("#orgRelationId").val();
		$("#invoceId").empty();
		$.ajax({
			type : "GET",
			url : vmsUrl + "/getInvoiceListForCreditDebitNote?"+ tokenParamName + "=" + tokenValue + "&orgRelationId="+ orgRelationId + "&invoiceType=Receivable",
			success : function(json) {
				var output = "";
				if(json.data !=null){
				for (var i = 0; i < json.data.length; i++) {
					var opt = json.data[i];
					if(invoiceIds != ""){
						var arrayinvoiceIds = invoiceIds.split(",");
						if(arrayinvoiceIds.includes(opt.id.toString())){
							output = output	+ "<option value='" +opt.id+ "' selected >"+ opt.invoiceId + "</option>";
						}else{
							output = output	+ "<option value='" +opt.id+ "'>"+ opt.invoiceId + "</option>";
						}
					}else{
						output = output	+ "<option value='" +opt.id+ "'>"+ opt.invoiceId + "</option>";
					}
				}
				}
				$("#invoceId").append(output);
				$('#invoceId').trigger("chosen:updated");
			},
		});
	}
	
	function getApproversList() {
		var vmsUrl ='${SourceLeadWeb_vmsUrl}';
		var invoiceIds = "${creditDebitNote.approvers}";
		$("#approvers").empty();
		$.ajax({
			type : "GET",
			url : vmsUrl + "/getApproversListForCreditDebitNote?",
			success : function(json) {
				var output = "";
				for (var i = 0; i < json.data.length; i++) {
					var opt = json.data[i];
					if(invoiceIds != ""){
						var arrayinvoiceIds = invoiceIds.split(",");
						if(arrayinvoiceIds.includes(opt.id.toString())){
							output = output	+ "<option value='" +opt.id+ "' selected >"+ opt.fullName + "</option>";
						}else{
							output = output	+ "<option value='" +opt.id+ "'>"+ opt.fullName + "</option>";
						}
					}else{
						output = output	+ "<option value='" +opt.id+ "'>"+ opt.fullName + "</option>";
					}
				}
				$("#approvers").append(output);
				$('#approvers').trigger("chosen:updated");
			},
		});
	}
	
</script>

<script type="text/javascript">
var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
var finalDateFormat = getJSDateFormat(locationDate);
$('#creditDebitNotedate').datepicker({
    dateFormat: finalDateFormat,
    changeMonth: true,
	  changeYear: true, 
	  minDate:0,
    //minDate: 0  this line for to restrict selection of previous dates
});
// date picker for Add row
function datePickerFunction(id){
	 $("#lineItemDate"+id).datepicker({
	      dateFormat: finalDateFormat,
	      changeMonth: true,
	  	  changeYear: true, 
	      minDate:0,
	  }).datepicker("show");
}
</script>

<!-- calculating total for Add rows with quantity and rate -->
<script>
  function totalCalculateAmount(rowId){
	  var price = $("#price"+rowId).val();
	  var quantity = $("#quantity"+rowId).val();
	  calAddRowAmount(price,quantity,rowId); 
     	/*	conversion(); */
  }
  
  function calAddRowAmount(price,quantity,rowId){
	  var totlalAmount = (price * quantity);
	     $('#rowTotal'+rowId).empty().val(totlalAmount.toFixed(2));
	     $('#rowTotalAmount'+rowId).empty().val(totlalAmount.toFixed(2));
	     calculateSumSubTotal(rowId);
}
  
  function taxCalculatedAmount(rowId){
	  var taxType = $("#taxType"+rowId).val();
	  var taxAmount = $("#taxAmount"+rowId).val();
	  var rowTotal = $("#rowTotal"+rowId).val();
	  taxCalculatedTotalAmount(taxType,rowTotal,taxAmount,rowId);
  }
  
  function taxCalculatedTotalAmount(taxType,rowTotal,taxAmount,rowId) {
	  var rowTotalVal = parseFloat(rowTotal);
	  var taxAmountVal = parseFloat(taxAmount);
	  var totlalValue = "0.00";
	  if(taxType=="Flat"){
	  if(!isNaN(taxAmountVal)){
		  totlalValue = (rowTotalVal + taxAmountVal);
		}else{
			  totlalValue = rowTotalVal;  
		 }
	  }else{
		  if(!isNaN(taxAmountVal)){
		  var perCentValue = (rowTotalVal * taxAmountVal)/100;
			totlalValue = (rowTotalVal + perCentValue); 
		  }else{
			  totlalValue = rowTotalVal;  
		  }
	  }
	   $('#rowTotalAmount'+rowId).empty().val(totlalValue.toFixed(2));
	   calculateSumSubTotal(rowId);
	   /* var vvv = $("#rowTotalAmount"+rowId).val();
	   $("#subTotal").empty().text(vvv); */
		
}
  
  
  function calculateSumSubTotal(id){
		 var maxRowNum = $("input[id^='creditDebitMaxRowNumber']").length; // $("#creditDebitMaxRowNumber"+id).val();
		 var sum = 0;
		  for(var i=0;i < maxRowNum;i++){
			  if(document.getElementById("rowTotalAmount"+i)){
		    		 if(document.getElementById("rowTotalAmount"+i).value){
		    			var rowAmount = $('#rowTotalAmount'+i).val();
			 	    	sum = parseFloat(sum) + parseFloat(rowAmount);
		    		 }
		    		
		    	 } 
		 }  
		  $("#subTotalAmount").val(sum.toFixed(2));
		  $("#grandTotal").val(sum.toFixed(2));
		  $("#conversionFrom").val(sum.toFixed(2));
		  calculateDisCount();
		  conversionRate();
		  
  }		  
  
  
  function calculateDisCount(){
		var discountType = $("#disCount").val();
		var subTotal = $("#subTotalAmount").val();
		var discountValue = $("#discountValue").val();
		var total ='';
		if(discountType == 'Flat'){
			if(discountValue !=null && discountValue !='undefined'){
				total = subTotal-discountValue;
				$("#discountTotal").val(discountValue);
				$("#grandTotal").val(total.toFixed(2));
				$("#conversionFrom").val(total.toFixed(2));
			}else{
				$("#discountTotal").val("00.00");
				$("#grandTotal").val(subTotal.toFixed(2));
				$("#conversionFrom").val(subTotal.toFixed(2));
			}
			
		}
		if(discountType == 'Percentage'){
			if(discountValue !=null && discountValue !='undefined'){
				var perCentValue = (subTotal * discountValue)/100;
				total = subTotal - perCentValue;
				$("#discountTotal").val(perCentValue.toFixed(2));
				$("#grandTotal").val(total.toFixed(2));
				$("#conversionFrom").val(total.toFixed(2));
			}else{
				$("#discountTotal").val("00.00");
				$("#grandTotal").val(subTotal.toFixed(2));
				$("#conversionFrom").val(subTotal.toFixed(2));
			}
			
		}
		conversionRate();
	}
  
  // allow only numbers
  function validateNumber(object){ 
	   object.value = object.value.replace(/[^0-9\.]/g,'');
	} 
  </script>
  
  <!-- currency conversion script -->
  <script>
function currencyConversionDiv(){
	var clientCurrency = $("#clientCurrency").val();
	var currencyCode = '${SourceLeadWeb_currencyCode}';
	if(clientCurrency !=null && clientCurrency !='' && clientCurrency !='undefined'){
	if(clientCurrency != currencyCode){
		var result = "";
		result = result +' <div class="row">';
		result = result +' <div class="clearfix mt col-md-offset-2 col-md-10" style="padding-right:0;">';
		result = result +' <div class="clearfix"><label class="col-md-6 text-right"><strong>Conversion From</strong></label><div class="paddingLeft col-md-6"><span class="pull-left" id="currencyCode">${creditDebitNote.loginCurrencyCode} </span><div class="pull-right"><input type="text" id="conversionFrom" placeholder="00:00" value = "${creditDebitNote.grandTotal}" class="ReadTextFli" readonly="" type="text"/></div></div></div>';
		result = result +'  <div class="clearfix"><label class="col-md-6 text-right"><strong>Conversion To</strong></label><div  class="paddingLeft col-md-6"><span id="vendorCurrencyCode">${creditDebitNote.vendorCurrencyCode}</span><div class="pull-right"><input id="exchangeCurrencyRate" name="exchangeCurrencyRate" class="ReadTextFli" value = "${creditDebitNote.exchangeCurrencyRate}" readonly="" type="text"/></div></div></div>';
		result = result +' <div class="clearfix"><label class="col-md-6 text-right"><strong>Exchange Rate</strong></label><div  class="paddingLeft col-md-6"><span id="convertedCurrencyCode">${creditDebitNote.loginCurrencyCode} to ${creditDebitNote.vendorCurrencyCode}</span><div class="pull-right"><input id="afterExchangeGrandTotal" name="afterExchangeGrandTotal" value = "${creditDebitNote.afterExchangeGrandTotal}" class="ReadTextFli" readonly="" type="text"/></div></div></div>';
		result = result +'  </div>';
		result = result +'  </div>';
		document.getElementById("conversionId").style.display="block";
		$("#conversionId").empty();
		$("#conversionId").append(result);
	}else{
		document.getElementById("conversionId").style.display="none";
	}
	}
}

function  conversionRate(){
	   var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	   var orgRelationId = $("#orgRelationId").val();
	   var currencyCode = '${SourceLeadWeb_currencyCode}';
	   var grandTotal = $("#grandTotal").val();
	   if(grandTotal != '' && grandTotal != 'undefined'){
		   $.ajax({
				type : "GET",
				url: vmsUrl+"/getConversionExchangeRate?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId+"&grandTotal="+grandTotal,
				success :function(response) {
					if(response.object !='' && response.object != 'undefined' && response.object != null){
//						var json = JSON.parse(data);
						$("#afterExchangeGrandTotal").val((response.object.exchangeRate).toFixed(2));
						$("#exchangeCurrencyRate").val(response.object.exchangeCurrency);
						$("#vendorCurrencyCode").html(response.object.vendorCurrencyCode);
						$("#convertedCurrencyCode").html(currencyCode +' to '+ response.object.vendorCurrencyCode);
					}
				},
				});  
		   $("#currencyCode").html(currencyCode);
	   }
	  
		
}

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
      
<jsp:include page="../masterFooter.jsp" />