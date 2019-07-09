<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="/resources/js/formvalidator.js"></script>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<script type="text/javascript">
var vmsUrl ='${SourceLeadWeb_vmsUrl}';
$(document).ready(function(){
	$(".ui-corner-all, .ui-corner-bottom, .ui-corner-right, .ui-corner-br").css({'z-index':'900000 !important'});
	getPayableInvoiceList();
});
</script>
<input type="hidden" id="emptyPayableInvoiceId"
	name="emptyPayableInvoiceId" />
<div class="success_pop" id="selector1" style="display: none;">
	<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
</div>
<section class="content-wraper">
	<section class="content-header clearfix">
		<c:if test="${!empty msg}">
			<div class="success_pop" id="selector" style="display: block;">
				<i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
		</c:if>
		<div class="pull-left">
			<h2>Bills</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a>&raquo; Bill List
		</div>
	</section>
	

	
	<div class="project_wrap clearfix">
	
	<div class="project_side_wrap_inn_tabs clearfix">
	    <ul class="project-tabs-menu">
		    <li class="current"><a href="${SourceLeadWeb_vmsUrl}/payablesList">Bill List</a></li>
		    <li class=""><a href="${SourceLeadWeb_vmsUrl}/debitNoteList">Debit Note List</a></li>
		    
	    </ul>
    </div>	
	
		<div class="billing_dashboard noPad">
			<div class="tabbable positionRelative">
				<ul class="nav nav-tabs pad15 invoiCount" id="tab_bar">


					<li class="active">
						<h4>Unbilled</h4> <a data-toggle="tab"
						class="onboardCount billbg_orange" title="Draft Bills">
							<div class="pull-left">
								<strong>${SourceLeadWeb_currencyCode}</strong>
							</div>
							<div class="pull-right ml10">
								<div class="count" id="unbilledAmoumt">0.0</div>
								<h5>Draft Bills<span class="paddingLeft" id="draftInvoiceCount">0.0</span></h5>
							</div>
					</a>
					</li>
					<li>
						<h4>Un Paid</h4> <a data-toggle="tab" title="UnPaid Overdue Bills"
						class="billingbg_red">
							<div class="pull-left">
								<strong>${SourceLeadWeb_currencyCode}</strong>
							</div>
							<div class="pull-right ml10">
								<div class="count" id="dueAfterInvoiceAmount">0.0</div>
								<h5>
									Overdue Bills<span class="paddingLeft" id="dueAfterInvoiceCount">0.0</span>
								</h5>
							</div>

					</a>
					</li>
					<li>
						<h4>&nbsp;</h4> <a data-toggle="tab" title="UnPaid Open Bills"
						class="billingbgRed">
							<div class="pull-left">
								<strong> ${SourceLeadWeb_currencyCode}</strong>
							</div>
							<div class="pull-right ml10">
								<div class="count" id="dueBeforeInvoiceAmount">0.0</div>
								<h5>
									Open Bills<span class="paddingLeft" id="dueBeforeInvoiceCount">0.0</span>
								</h5>
							</div>
					</a>
					</li>
					<li class="billingbg_greens">
						<h4>Paid</h4> <a data-toggle="tab" title="Paid Bills"
						class="billbg_green">
							<div class="pull-left">
								<strong> ${SourceLeadWeb_currencyCode}</strong>
							</div>
							<div class="pull-right ml10">
								<div class="count" id="paidInvoiceAmount">0.0</div>
								<h5>
									Paid Bills<span class="paddingLeft" id="paidInvoiceCount">0.0</span>
								</h5>
							</div>
					</a>
					</li>

					<div class="pull-right mt40 select-field hidden">
						<select class="form-control">
							<option class="disabled" selected="">--Select option--</option>
							<option>1 Month</option>
							<option>2 Months</option>
							<option>3 Months</option>
							<option>6 months</option>
							<option>1 Year</option>
							<option>Custom months</option>
						</select>
					</div>
				</ul>
				<div class="tab-content">
					<div id="invoiceLoading"></div>
					<!-- Employee Details -->
					<div class="tab-pane active" id="unbilled">
						<div class="timesheet_out">
							<div class="clearfix">
							
							
<div class="positionRelative reportHead clearfix">
      <span class="pull-left refreIcon" id="advanceSearchDiv">
      		<a href="javascript:void(0)" class="refeIcon" id="resetVendor" title="Refresh" onclick="resetBillsFilter();"></a>
      </span>
      
      <ul class="pull-right list-unstyled list-inline">
      
            <li>
				<a class="action newAdBtn mr5" onclick="getActions();" href="javascript:void(0)">+ Add <i class="fa fa-caret-down"></i></a>
				<div class="action-pop">
					<span> 
						<sec:authorize access="hasAnyRole('ROLE_ADD_BILL')">
							<!-- <a href="javascript:void(0)" title="Create Bill" onclick="createBill();">Create Bill </a> -->
					<a href="${SourceLeadWeb_vmsUrl}/createPayables" title="Create Vendor Based Bill">Vendor Based Bill</a>
					<a href="${SourceLeadWeb_vmsUrl}/createInvoiceProjectPayableBased" title="Create Project Based Bill">Project Based Bill</a>
		            <a href="${SourceLeadWeb_vmsUrl}/createPaybleForJobClosure" title="Create Job closure Based Bill">Job closure Based Bill</a>
		            <a href="${SourceLeadWeb_vmsUrl}/createExpensesPayableBill" title="Create Other Bill">Other Bill</a>
                           </sec:authorize>
						<a href="javascript:void(0)" title="Generate Automatic Bill" onclick="generateAutomaticInvoice();">Generate Automatic Bill</a>
						<a href="javascript:void(0)" title="Generate Automatic JobClosure Bill" onclick="generateAutomaticBillForJobClosure();">Generate Automatic JobClosure Bill</a>
						 <a href="${SourceLeadWeb_vmsUrl}/addPaymentBill">Add Payment</a>
					</span>
				</div>
			</li>
				<li id="vendorFiletPopup"><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openBillsFilterPop();"></a></li>
				
				<li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
					<div class="newActPop">
						<span>
						 	<a href="javascript:void(0)" onclick="exportToPayablePdf();" title="PDF">Export PDF</a>
							<a href="javascript:void(0)" onclick="exportToPayableExcel();" title="Excel">Export Excel</a>
						</span>
					</div>
				</li>
		    
		  <li class=""><input type="text" class="repSearchBar" placeholder='Search' id='payableDataTableSearch'/><span class="repSearchIcon" onClick="getPayableInvoiceList();"></span></li>
        </ul>
      </div> 							
								<div class="candidate_wrap_table datatable_filter">
									<div id="invoiceList"></div>
								</div>
							</div>
						</div>
					</div>

				</div>
				<!-- STYLED IN FORMS.CSS -->
			</div>
		</div>
	</div>
</section>

<!-- Add invoice Popup -->

<div class="pop_mask"></div>
<div id="recvPymntpopOnline" class="modal zIndexStyle" style="display: none;">
    
    <div class="modal-dialog modal-lg ">
        <div class="modal-content">                                                     
            <div class="modal-header">                                                    
                <button type="button" class="close" data-dismiss="modal" onclick="cancelbtn();">×</button>         
                <h4 class="modal-title">Payment</h4>                                
            </div>                                                                        
            <div class="modal-body clearfix">                                             
                <div id="wepay_checkout" class="col-md-12 clearfix" ></div>
                <!-- Add New Task Widget -->                                                
                <div class="col-md-12 clearfix" id="paymentInfoOnline">                                            
                    <form class="form-horizontal clearfix" id="receivePaymentOnline-form" name="receivePaymentOnline-form">                                     
                                
                        <div class="row mt">                                 
                            <div class="col-md-12">                             
                                <div class="row">                                
                                    <div class="col-md-6">                        
                                        <label>Amount</label>               
                                        <div class="row">                            
                                            <div class="col-md-6">                     
                                                <input   type="radio" name="paymentAmountOnline" value="total" checked onclick="validMask();"> 
                                                Total Due:          
                                                <input type="text" value="" placeholder="Total Due" class="form-control input-sm mt10" id="totalAmountOnline" name="totalAmountOnline" readonly> 
                                                <span class="addon-currency">$</span>
                                            </div>              
                                            <div class="col-md-6">      
                                                <input  type="radio" name="paymentAmountOnline" value="custom" onclick="removeMask();" >     
                                                Custom:
                                                <input type="text" value="" placeholder="Custom Due" class="form-control input-sm mt10 allownumericwithdecimal" id="customeAmountOnline" name="customeAmountOnline" readonly>       
                                                <span class="addon-currency">$</span>
                                            </div>                      
                                        </div>                        
                                    </div>                          
                                    <div class="col-md-6 mt">      
                                        <label>Memo</label>           
                                        <textarea rows="2" class="form-control" placeholder="Memo" id="paymentMemoOnline" name="paymentMemoOnline" data-validation="validate_length length2-500" data-validation-optional="true"></textarea>
                                    </div>                                                            
                                </div>                                                              
                            </div>                                                     
                        </div>  
                        
                        <div class="clearfix"></div>                                            
                        <div class="clearfix mt col-md-12 mb">                                  
                            <div class="pull-right">                                              
                                <sec:authorize access="hasAnyRole(\'ROLE_RECEIVE_PAYMENT\')">
                                    <input class="btn btn-primary btn-sm" value="Continue to Payment" type="button" id="save_paymentOnline" onclick="continuePaymentOnline();">   
                                </sec:authorize>
                                <input class="btn btn-default btn-sm ml10" value="Cancel" type="button" onclick="cancelbtn();">   
                            </div>                                                                                              
                        </div>                                                                                                
                    </form>                                                                                                 
                </div>                                                                                                    
            </div>                                                                                                      
        </div>                                                                                                        
    </div>
</div>
<script>
		    //addcandidate-popup script
		    function createBill(){
		        $('.addcandidate-popup').fadeIn(200);
		        $('.pop_mask').fadeIn(200);
		    };
		       function closeCandidate(){
		        $('.addcandidate-popup').fadeOut(200);
		        $('.pop_mask').fadeOut(200);
		       }
		    </script>

<script>

var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
var recievePaymentDate = '';
if(locationDate.match("yyyy-MM-dd")){
	recievePaymentDate = 'yy-mm-dd';
}if(locationDate.match("dd-MM-yyyy")){
	recievePaymentDate = 'dd-mm-yy'; 
}if(locationDate.match("MM-dd-yyyy")){
	recievePaymentDate = 'mm-dd-yy'; 
}if(locationDate.match("yyyy-dd-MM")){
	recievePaymentDate = 'yy-dd-mm'; 
}

function receivePaymentPop1(invoiceId,total){
	var currencyCode = '${SourceLeadWeb_currencyCode}';
	var paymentAmount = total.toFixed(3);
	 var result = "";
	result = result +'  <div class="modal-dialog modal-lg">';
	result = result +'    <div class="modal-content">                                                     ';
	result = result +'      <div class="modal-header">                                                    ';
	result = result +'        <button type="button" class="close" data-dismiss="modal" onclick="cancelbtn();">×</button>         ';
	result = result +'        <h4 class="modal-title">Payment</h4>                                ';
	result = result +'      </div>                                                                        ';
	result = result +'      <div class="modal-body clearfix">                                             ';

	result = result +'        <!-- Add New Task Widget -->                                                ';
	result = result +'        <div class="col-md-12 clearfix">                                            ';
	result = result +'        <form class="form-horizontal clearfix" id="receivePayment-form" name="receivePayment-form" enctype="multipart/form-data">                                     ';
	result = result +'          <div class="row mt">                                                      ';
	result = result +'            <div class="col-md-4" id="errPaymentDate">     ';
	result = result +'              <label>Payment Date<span class="text-danger paddingLeft">*</span></label>    ';
	result = result +'              <input class="form-control input-sm calendar" placeholder="Payment Date" type="text" id="paymentDate" name="paymentDate" data-validation="required" field-name="Payment Date" readonly> ';
	result = result +'            </div>';
	result = result +'            <div class="col-md-4 select-field" id="errPaymethod">';
	result = result +'              <label>Payment Method<span class="text-danger paddingLeft">*</span></label> ';
	result = result +'              <select class="form-control" id="paymentMethod" name="paymentMethod" data-validation="required" field-name="Payment Method" onchange="removeErr();"> ';
	result = result +'                <option selected="" disabled="">-- Select Method --</option>';
	result = result +'                <option value="Cash">Cash</option>   ';
	result = result +'                <option value="Cheque">Cheque</option> ';
	result = result +'                <option value="CreditCard">Credit Card</option> ';
	result = result +'              </select>   ';
	result = result +'            </div>  ';
	result = result +'            <div class="col-md-4">  ';
	result = result +'              <label>Reference No<span class="text-danger paddingLeft">*</span></label> ';
	result = result +'              <input class="form-control input-sm" placeholder="Reference No" type="text" id="referenceNumber" name="referenceNumber" data-validation="required validate_length length2-250" field-name="Reference No"> ';
	result = result +'            </div>        ';
	result = result +'          </div>          ';
	result = result +'          <div class="row mt">                                 ';
	result = result +'            <div class="col-md-12">                             ';
	result = result +'              <div class="row">                                ';
	result = result +'                <div class="col-md-6">                        ';
	result = result +'                  <label>Amount Received</label>               ';
	result = result +'                  <div class="row">                            ';
	result = result +'                    <div class="col-md-6">                     ';
	result = result +'                      <input   type="radio" name="paymentAmount" value="total" checked onclick="validMask();">           ';
	result = result +'                      Total Due:          ';
	result = result +'                      <input type="text" value="'+paymentAmount+'" placeholder="Total Due" class="form-control input-sm mt10" id="totalAmount" name="totalAmount" readonly> ';
	result = result +'                      <span class="addon-currency">'+currencyCode+'</span>';
	result = result +'                    </div>              ';
	result = result +'                    <div class="col-md-6">      ';
	result = result +'                      <input  type="radio" name="paymentAmount" value="custom" onclick="removeMask();">     ';
	result = result +'                      Custom:                       ';
	result = result +'                      <input type="text" value="" placeholder="Total Due" class="form-control input-sm mt10 allownumericwithdecimal" data-validation-optional="true" field-name="Custom Amount" id="customeAmount" name="customeAmount" readonly onblur="amountCal();">       ';
	result = result +'                      <span class="addon-currency">'+currencyCode+'</span>';
	result = result +'                    </div>                      ';
	result = result +'                  </div>                        ';
	result = result +'                </div>                          ';
	result = result +'                <div class="col-md-6 mt">      ';
	result = result +'                  <label>Memo</label>           ';
	result = result +'                  <textarea rows="2" class="form-control" placeholder="Memo" id="paymentMemo" name="paymentMemo" data-validation="validate_length length2-500" data-validation-optional="true"></textarea>';
	result = result +'                </div>                                                            ';
	result = result +'              </div>                                                              ';
	result = result +'            </div>                                                                ';
	result = result +'            <div class="col-md-6 mt">                                                ';
	result = result +'              <div class="clearfix mb invoiceAttachment">                         ';
	result = result +'              <label><strong>Attachment</strong></label>                         ';
	result = result +'              <div class="timesheet_week_comm col-md-12 width100percnt">                        ';
	result = result +'                <div class="form-group"> <span>                                   ';
	result = result +'                  <input type="file"  id="paymentAttachment" name="paymentAttachment" onchange="getfileUrl()">                                            ';
// 	result = result +'                  <i class="fa fa-plus"></i></span> </div>                        ';
	result = result +'              </div>                                                              ';
	result = result +'            </div>                                                                ';
	result = result +'            </div>                                                                ';
	result = result +'          </div>                                                                  ';
	result = result +'          <div class="clearfix"></div>                                            ';
	result = result +'          <div class="clearfix mt col-md-12 mb">                                  ';
	result = result +'            <div class="pull-right">                                              ';
	result = result +'              <input class="btn btn-primary btn-sm" value="Save" type="button" id="save_payment" onclick="saveReceivePayment('+invoiceId+');">   ';
	result = result +'              <input class="btn btn-default btn-sm ml10" value="Cancel" type="button" onclick="cancelbtn();">   ';
	result = result +'            </div>                                                                                              ';
	result = result +'          </div>                                                                                                ';
	result = result +'        </form>                                                                                                 ';
	result = result +'      </div>                                                                                                    ';
	result = result +'    </div>                                                                                                      ';
	result = result +'  </div>                                                                                                        ';
	result = result +'</div>                                                                                                          ';
	
	$("#receivePaymentId").empty();
	$("#receivePaymentId").append(result);
	
	$("#recvPymntpop").fadeIn(200);
    $('.modal-backdrop').fadeIn(200).addClass('in');
    
    $('#paymentDate').datepicker({
		dateFormat : recievePaymentDate,
		changeMonth: true,
		changeYear: true, 
		onSelect : function(selected) {
			removeValidate();
		}
	});
}

function getfileUrl(){
	fileUpload("paymentAttachment", "incoiceDocuments", "receivePayment-form");
}

</script>
<script>
function removeValidate(){
	$('#errPaymentDate').find('p.jquery_form_error_message').remove(); 
}
function removeErr(){
	$('#errPaymethod').find('p.jquery_form_error_message').remove(); 
}
function removeMask(){
	 $("#customeAmount, #customeAmountOnline").removeAttr("readonly");
	 $('#customeAmount').removeAttr('data-validation-optional');
	 $('#customeAmount').attr("data-validation","required"); 
	        
	 $("#customeAmount, #customeAmountOnline").keydown(function (e) {
	        // Allow: backspace, delete, tab, escape, enter and .
	        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
	             // Allow: Ctrl+A, Command+A
	            (e.keyCode === 65 && (e.ctrlKey === true || e.metaKey === true)) || 
	             // Allow: home, end, left, right, down, up
	            (e.keyCode >= 35 && e.keyCode <= 40)) {
	                 // let it happen, don't do anything
	                 return;
	        }
	        // Ensure that it is a number and stop the keypress
	        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
	            e.preventDefault();
	        }
	    });
}
function validMask(){
	  $('#customeAmount, #customeAmountOnline').attr("readonly","readonly");  
	  $('#customeAmount').attr("data-validation-optional","true"); 
}
 function amountCal(){
	var totalAmount =  $("#totalAmount").val();
	var customAmount =  $("#customeAmount").val();
	 var totalValue=0.00;
	 finalAmount = totalValue+parseFloat(customAmount);
	if(finalAmount > totalAmount){
		var exceededAmount = customAmount - totalAmount;
		alert("your payment is exceeded by"); 
		$('#customeAmount').removeAttr('data-validation-optional');
		 $('#customeAmount').attr("data-validation","required");
	}else{
		$('#customeAmount').removeAttr('data-validation-optional');
	}
}
</script>
<!-- modal pop -->
<div class="modal-backdrop" style="display: none"></div>
<div id="recvPymntpop" class="modal zIndexStyle" style="display: none;">
	<div id="receivePaymentId"></div>
</div>

<!-- modal pop -->
<div class="side_mask" style="display: none"></div>
<div class="modal modal_invoice datatable_filter" style="display: none;">
	<div id="linkPayblePopup" class="datatable_filter"></div>
</div>
<!-- modal pop -->



<script>
function cancelTermination(){
	$("#remove_user_pop, .project_inn_mask").fadeOut();
}

</script>
<!-- modal pop -->
<script>
function saveReceivePayment(invoiceId){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}'; 
	if ($('#receivePayment-form').validate(false, validationSettings)){  
		    var frm = $('#receivePayment-form').serializeFormJSON();
	        var con = JSON.stringify(frm);
	        con = con.replace(/[{}]/g, "");
	        //It will escape all the special characters
	        var jsonData = encodeURIComponent(con);
	        
	        var form = $('#receivePayment-form')[0];
	 	   var formData = new FormData(form); 
	        $("#save_payment").attr("disabled", "disabled");
     		$("#save_payment" ).addClass('button').val('Processing..');
	        $.ajax({
		    	type: "POST",
		    	 data: formData,
			      contentType : 'application/json; charset=utf-8',
			      enctype: 'multipart/form-data',
		        processData: false,
		        contentType:false,
			      dataType : 'json',
		    	 url: vmsUrl+"/saveInvoicePayment?"+tokenParamName+"="+tokenValue+"&json="+jsonData+"&invoiceId="+invoiceId, 
		        success: function(result) {
		        	if (result && result.response === "INVALID_DATA") {
	          		    showError(result.responseMessage);
	          		} else if (result.response === "AWKWARD") {
	          		    location.href = "/errorPage";
	          		} else if (result.response === "SUCCESS") {
	          			location.replace(vmsUrl+"/payablesList");
	          		} else if (result.indexOf('Sign In') !== -1) {
	          			location.replace(sourceleadUrl);
	          		} else {
	          		    showError("Operation failed, please try again");
	          		}
		     		
		        },
		    });  
	         return true; 
		
	   }else{
		return false;
	  } 
}
$('body').on('blur', '#receivePayment-form', function() {
	$("#receivePayment-form").showHelpOnFocus();
	$("#receivePayment-form").validateOnBlur(false, validationSettings);  
});
</script>


<script>
function getPayableInvoiceList(){
	var invoice = [];
	var payment = [];
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  orgLocationDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  orgLocationDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  orgLocationDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  orgLocationDate = 'YYYY-DD-MM'; 
	  } 
	  var startDate = null;
	var endDate = null;
	var dayWisetype = null;
	var returnStat = true;
	$('input:radio[name=invoiceCustomDays]:checked').each(function() {
		dayWisetype = $(this).val();
		if(dayWisetype == 'custom'){
			startDate = $("#billingStartDate").val();
			endDate = $("#billingEndDate").val();
			$("#errStartDate").find('p.jquery_form_error_message').remove();
			$("#errEndDate").find('p.jquery_form_error_message').remove();
			if(startDate == null || startDate == '' || startDate == 'undefined'){
				$("input#billingStartDate") .after( "<p class='jquery_form_error_message'>Start Date is Required</span>");
				returnStat =  false;
			}
			if(endDate == null || endDate == '' || endDate == 'undefined'){
				$("input#billingEndDate") .after( "<p class='jquery_form_error_message'>End Date is Required</span>");
				returnStat =  false;
			}
		}
	});
	
	$('input:checkbox[name=invoiceStatus]:checked').each(function() 
    {
		invoice.push($(this).val());
    });
	
	$('input:checkbox[name=paymentStatus]:checked').each(function() 
	 {
		payment.push($(this).val());
	 });
	
	
	var invoieDeleteStatus = null;
	
	$('input:radio[name=invoiceStatusDelete]:checked').each(function() {
		invoieDeleteStatus = $(this).val();
	});	
	
	var tags = [];
	$('input:checkbox[name=tags]:checked').each(function() 
     {
		tags.push($(this).val());
	 });
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var searchValue = $("#payableDataTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	if(returnStat){
		var url = vmsUrl+"/getInvoiceList?&invoiceStatus="+invoice+"&paymentStatus="+payment+"&dayWisetype="+dayWisetype+"&startDate="+startDate+"&endDate="+endDate+"&invoiceType=payables&searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&tags="+tags+"&invoiceDeleteStatus="+invoieDeleteStatus;
		preparePayableList(url,orgLocationDate);
		closeBillsFilterPop();
	}
	return returnStat;
}

</script>

<script>
function preparePayableList(url,orgLocationDate){
 	var result = "";
 	result = result + '<table id="payableListTable" class="display" cellspacing="0" width="100%" >';
	result = result + '<thead>';
	result = result +'       <tr>                             ';
	result = result +'         <th>Action</th>                ';
	result = result +'         <th>Reference Id</th>                    ';
	result = result +'         <th>Vendor Name</th>           ';
	result = result +'         <th>Contact</th>               ';
	result = result +'         <th>Project Name</th>          ';
	result = result +'         <th>Bill Date<br>(<small class="custom-small">'+orgLocationDate+'</small>)</th>             ';
	result = result +'         <th>Total</th>                 ';
	result = result +'         <th>Due</th>                   ';
	result = result +'         <th>Due Date<br>(<small class="custom-small">'+orgLocationDate+'</small>)</th>              ';
	result = result +'         <th>Status</th>                ';
	result = result +'         <th>Payment Status</th>        ';
	result = result +'       </tr> 							  ';
	result = result + '</thead>	';
    result = result +"</table>";
    $("#invoiceList").empty();  
    $("#invoiceList").append(result);
    payablePagination(url); 
	
}

</script>

<script>
function payablePagination(url){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}'; 
	var colIndex = 0;
    $('#payableListTable').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 10
        }),
        "columns": [
            { "data": "invoiceId" },
            { "data": "vendorName"},
            { "data": "vendorContact"},
            { "data": "projectName" },
            { "data": "invoiceDate" },
            { "data": "inoviceTotal"},
            { "data": "inoviceDue"},
            { "data": "dueDate"},
            { "data": "inoviceStatus"},
            { "data": "paymentStatus"},
            { "data": "action"}
        ],
        "columnDefs": [{
			            "targets": colIndex++,
			            "data": "action",
			            "sortable": false,
			            "render": function ( data, type, report, meta ) {
			            	var result = "";
							result = result +'         <td> ';
							result = result +'     <div class="tblminwidth-90" style="position: inherit;">';
							 result = result +'  <div class="pull-right positionRelative menuWidget"  style="position: inherit;">';
							if(report.invoiceStatus != "Received" && report.invoiceStatus != "Rejected"){
								 result = result +' <i class="fa fa-ellipsis-v divPadding" onClick="showPopUp(\''+report.id+'\',\''+report.invoiceUUID+'\',\''+report.invoiceStatus+'\',\''+report.invoiceParentId+'\',\''+report.total+'\',\''+report.fileUrl+'\',\''+report.paymentStatus+'\',\''+report.dueAmount+'\');"></i>  ';
									result = result +'  <div id="popupDiv'+report.invoiceUUID+'" class="invoicePopRg" style="position: inherit;"></div>';
							} 
							result = result +'  </div> ';  
							if(report.invoiceType=="Payables" && report.invoiceParentId != null && report.invoiceParentId != '' && report.invoiceParentId != "undefined"){
								if(report.paymentStatus == "Paid" || report.invoiceStatus == "DRAFT" || report.invoiceStatus == "Rejected"){
									if(report.paymentStatus == "Paid"){
										result = result +'           <span class="textSuccess pull-left" title="Paid" >Paid</span> </td>          ';
									}else if(report.invoiceStatus == "DRAFT"){
										result = result +'           <span class="textWarning pull-left" title="Send">Draft</span></td>';
									}else if(report.invoiceStatus == "Rejected"){
										result = result +'           <div class="colorRed pull-left">Rejected</div>   ';	
									}
										
								}else{
									if(report.invoiceStatus == "Accepted"){
                                      if(report.paymentAccountDTO != null && report.paymentAccountDTO.accountType == "WEPAY")
                                          result = result +'           <span class="receiveBtn receive_Btn pull-left" onclick="initPaymentOnline(\''+report.invoiceUUID+'\','+report.dueAmount+');" title="Pay">Pay</span> </td>';
                                      else
										result = result +'           <span class="textSuccess pull-left" title="Accepted">Accepted</span> </td>';
									}else if(report.invoiceStatus == "Linked"){
										result = result +' <a class="successBtn successBtn-custom primary pull-left cursorPointer" onClick="unLinkBill(\''+report.invoiceUUID+'\')">UnLink</a>  ';
									}else if(report.invoiceStatus == "Void"){
										result = result +'           <span class="colorRed pull-left">Void</span>   ';	
									}
									else{
										result = result +'           <span class="clearfix" ><a onclick="acceptedInvoice(\''+report.invoiceUUID+'\');" title="Accept"><i class="fa fa-check text-success paddingRight"></i></a><a onclick="rejectInvoice(\''+report.invoiceUUID+'\');" title="Reject"><i class="paddingLeft fa fa-times text-danger"></i></a></span> </td>';
									}
										
								}
								
							}else{
								if((report.paymentStatus == "Paid" && report.invoiceStatus != "Linked") || report.invoiceStatus == "DRAFT" || report.invoiceStatus == "Rejected" || report.invoiceStatus == "SENT"){
									if(report.paymentStatus == "Paid"){
										result = result +'           <span class="textSuccess pull-left" title="Paid" >Paid</span> </td>          ';	
									}else if(report.invoiceStatus == "DRAFT"){
										result = result +'           <span class="textWarning pull-left" title="Send">Draft</span></td>          ';
									}else if(report.invoiceStatus == "SENT"){
										result = result +'           <span class="text-primary pull-left" title="Sent" >Sent</span> </td>          ';
									}else if(report.invoiceStatus == "Rejected"){
										result = result +'           <div class="colorRed pull-left">Rejected</div>   ';
									}
										
								}else{
									if(report.invoiceStatus == "Linked"){
										result = result +' <a class="successBtn successBtn-custom primary pull-left" onClick="unLinkBill(\''+report.invoiceUUID+'\')">UnLink</a>  ';
									}else if(report.invoiceStatus == "Received"){
										result = result +'           <span class="clearfix" ><a onclick="acceptedInvoice(\''+report.invoiceUUID+'\');" title="Accept"><i class="fa fa-check text-success paddingRight"></i></a><a onclick="rejectInvoice(\''+report.invoiceUUID+'\');" title="Reject"><i class="paddingLeft fa fa-times text-danger"></i></a></span> </td>';
									}else if(report.invoiceStatus == "Void"){
										result = result +'           <span class="colorRed pull-left">Void</span>   ';	
									}
									else{
										result = result +'           <span class="textSuccess pull-left" title="Accepted">Accepted</span> </td>          ';
									}
								}
								
								
							}
							
					        result = result +'</div>';
					        result = result +'</td>';
			            	 return result;
			            }
			    } ,{
		    	  	"targets": colIndex++,
		    	  	"data": "invoiceId",
		    	  	"render": function(data, type, report, meta) {
		    	  		$("#emptyPayableInvoiceId").val(1);
		    	  		var result = "";
		    	  		
						if(report.dueBeforeInvoiceAmount != null && report.dueBeforeInvoiceAmount != '' && report.dueBeforeInvoiceAmount != 'undefined'){
	 						$("#dueBeforeInvoiceAmount").html(report.dueBeforeInvoiceAmount);
						}
		    	  		if(report.dueBeforeInvoiceCount != null && report.dueBeforeInvoiceCount != '' && report.dueBeforeInvoiceCount != 'undefined'){
		    	  			$("#dueBeforeInvoiceCount").html(report.dueBeforeInvoiceCount);
						}
		    	  		if(report.dueAfterInvoiceAmount != null && report.dueAfterInvoiceAmount != '' && report.dueAfterInvoiceAmount != 'undefined'){
		 					$("#dueAfterInvoiceAmount").html(report.dueAfterInvoiceAmount);
						}
			    	  	if(report.dueAfterInvoiceCount != null && report.dueAfterInvoiceCount != '' && report.dueAfterInvoiceCount != 'undefined'){
			    	  		$("#dueAfterInvoiceCount").html(report.dueAfterInvoiceCount);
						}
			    	  	if(report.draftInvoiceCount != null && report.draftInvoiceCount != '' && report.draftInvoiceCount != 'undefined'){
		 					$("#draftInvoiceCount").html(report.draftInvoiceCount);
						}
			    	  	if(report.draftInvoiceAmount != null && report.draftInvoiceAmount != '' && report.draftInvoiceAmount != 'undefined'){
			    	  		$("#unbilledAmoumt").html(report.draftInvoiceAmount);
						}
			    	  	if(report.paidInvoiceCount != null && report.paidInvoiceCount != '' && report.paidInvoiceCount != 'undefined'){
			 				$("#paidInvoiceCount").html(report.paidInvoiceCount);
						}
				    	if(report.paidInvoiceAmount != null && report.paidInvoiceAmount != '' && report.paidInvoiceAmount != 'undefined'){
				    	  	$("#paidInvoiceAmount").html(report.paidInvoiceAmount);
						}
		    		     
				    	result = result +'         <td>';
				    	if(report.invoiceStatus == "Rejected" && report.invoiceParentId != null){
							'<sec:authorize access="hasAnyRole(\'ROLE_BILL_PAYMENT_HISTORY\')">';
							result = result +'           <div class="tbldata-dotdot_80 invoiceId_Anchor invoiceId-anchor"><a href="'+vmsUrl+'/viewInvoice?invoiceUUID='+report.invoiceUUID+'&invoiceType=Payables" title = "'+report.invoiceId+'">'+report.invoiceId+'</a></div>   ';				
							'</sec:authorize>';
						}
				    	else if(report.invoiceStatus == "DRAFT" || (report.invoiceStatus == "Rejected" && report.invoiceParentId == null)){
							if(report.projectId != null && report.projectId != '' && report.projectId != 'undefined'){
								'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_BILL\')">';
								result = result +'           <div class="tbldata-dotdot_80 invoiceId_Anchor invoiceId-anchor"><a href="'+vmsUrl+'/getInvoiceByUUID?invoiceUUID='+report.invoiceUUID+'&projectId='+report.projectId+'" title = "'+report.invoiceId+'">'+report.invoiceId+'</a></div>   ';
								'</sec:authorize>';
							}else{
								result = result +'           <div class="tbldata-dotdot_80 invoiceId_Anchor invoiceId-anchor"><a href="'+vmsUrl+'/getInvoiceByUUID?invoiceUUID='+report.invoiceUUID+'" title = "'+report.invoiceId+'">'+report.invoiceId+'</a></div>   ';
							}
							
						}
				    	else if(report.invoiceStatus == "SENT" || report.invoiceStatus == "Received" || report.invoiceStatus == "Accepted" || report.invoiceStatus == "Linked"){
							'<sec:authorize access="hasAnyRole(\'ROLE_BILL_PAYMENT_HISTORY\')">';
							result = result +'           <div class="tbldata-dotdot_80 invoiceId_Anchor invoiceId-anchor"><a href="'+vmsUrl+'/viewInvoice?invoiceUUID='+report.invoiceUUID+'&invoiceType=Payables" title = "'+report.invoiceId+'">'+report.invoiceId+'</a></div>   ';				
							'</sec:authorize>';
						}else{
							'<sec:authorize access="hasAnyRole(\'ROLE_BILL_PAYMENT_HISTORY\')">';
							result = result +'           <div class="tbldata-dotdot_80 invoiceId_Anchor invoiceId-anchor"><a href="'+vmsUrl+'/viewInvoice?invoiceUUID='+report.invoiceUUID+'&invoiceType=Payables" title = "'+report.invoiceId+'">'+report.invoiceId+'</a></div>   ';				
							'</sec:authorize>';
						}
						
						result = result +'         </td>  ';
						
			             return result;
		    	  	}
		      },{
		    	  "targets": colIndex++,
	              "data": "vendorName",
	              "render": function ( data, type, report, meta ) {
	            	  var result = "";
	            	  result = result +'         <td>   ';
	            	 
	            	  '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_VENDOR\')">';
	            	   result = result +'<div class="tbldata-dotdot-vsm" title="'+report.clientName+'"><a href="'+vmsUrl+'/vendorsList?orgRelationUUID='+report.orgRelationUUID+'" title="'+report.clientName+'"> '+report.clientName+' </a></div>';
	            	  '</sec:authorize>';
	            	  
	            	  '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_VENDOR\')">';
		           	   result = result +' <div class="tbldata-dotdot-vsm" title="'+report.clientName+'">'+report.clientName+'</div>';
		           	  '</sec:authorize>';
	           	  
	            	  result = result +'         </td> ';
		              return result;
	              		
	              }
		          },{
	            "targets": colIndex++,
	            "data": "vendorContact",
	            "render": function ( data, type, report, meta ) {
	            	var result = "";
	            	result = result +'         <td>';
					if(report.clientContactName !='' && report.clientContactName != null && report.clientContactName != 'undefined'){
						result = result +'           <div class="tbldata-dotdot-vsm" title="'+report.clientContactName+'">'+report.clientContactName+'</div>  ';	
					}else{
						result = result +'<div class="tbldata-dotdot-vsm">--</div>  ';
					}
					result = result +'         </td>';
	            	 return result;
	            }
	          }, {
	              "targets": colIndex++,
	              "data": "projectName",
	              "render": function ( data, type, report, meta ) {
	            	  var result = "";
	            		result = result +'         <td> ';
	           			if(report.projectName != null && report.projectName != '' && report.projectName != 'undefined'){
	           				result = result +'<div class="tblminwidth-100">'+report.projectName+'</div>';
	            		}else{
	            			result = result +'<div class="tblminwidth-100">--</div>';
	            		}
	           			result = result +'         </td>';
	            	  return result;
	              }
	            }, {
	                "targets": colIndex++,
	                "data": "invoiceDate",
	                "render": function ( data, type, report, meta ) {
	                	var result = "";
	                	if(report.invoiceDateStr != null && report.invoiceDateStr != '' && report.invoiceDateStr != 'undefined'){
	                		result = result +'<div class="tblminwidth-100">'+report.invoiceDateStr+'</div>';
	            		}else{
	            			result = result +'<div class="tblminwidth-100">--</div>';
	            		}
	    	              return result;
	                }
	          },{
		    	  	"targets": colIndex++,
		    	  	"data": "inoviceTotal",
		    	  	"render": function(data, type, report, meta) {
		    	  		var result = "";
		    	  		result = result +'         <td> ';
						result = result +'           <div class="tbldata-dotdot_80" title="'+report.total+'">'+report.total+'</div>';
						result = result +'         </td>';
	  	              return result;
		    	  	}
		    	  },{
		    	  	"targets": colIndex++,
		    	  	"data": "inoviceDue",
		    	  	"render": function(data, type, report, meta) {
		    	  		var result = "";
		    	  		result = result +'         <td> ';
						result = result +'           <div class="tbldata-dotdot_80" title="'+report.dueAmount+'">'+report.dueAmount+'</div>';
						result = result +'         </td>';
		    	  		 return result;
		    	  	}
		    	  }, {
	                  "targets": colIndex++,
	                  "data": "dueDate",
	                  "render": function ( data, type, report, meta ) {
	                	  var result = "";
	                	  if(report.dueDateStr != null && report.dueDateStr != '' && report.dueDateStr !='undefined'){
	                		  result = result +'<div class="tblminwidth-100">'+report.dueDateStr+'</div>';
	              		}else{
	              		  result = result +'<div class="tblminwidth-100">--</div>';
	              		}
	                	  return result;
	                  }
	                },{
	                    "targets": colIndex++,
	                    "data": "inoviceStatus",
	                    "render": function ( data, type, report, meta ) {
	                    	var result = "";
	                    	var invoiceStatus = null;
	               			if(report.invoiceStatus == "SENT"){
	               				invoiceStatus = "Sent"
	               			}else if(report.invoiceStatus == "DRAFT"){
	               				invoiceStatus = "Draft"
	               			}else{
	               				invoiceStatus = report.invoiceStatus
	               			}
	                    	
	                    	result = result +'         <td> ';
	        				result = result +'           <div class="tbldata-dotdot_100">'+invoiceStatus+'</div>';
	        				if(report.invoiceStatus == "Rejected"){
	        			 		result = result + '    <i class="glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status"><p>'+report.rejectReason+'</p></div></i>';
	        					}
	        				result = result +'         </td>';
	                    	 return result;
	                    }
	                  },{
	                      "targets": colIndex++,
	                      "data": "paymentStatus",
	                      "render": function ( data, type, report, meta ) {
	                    	  var result = "";
	                    	  result = result +'         <td> ';
	          				result = result +'           <div class="tbldata-dotdot-100">'+report.paymentStatus+'</div> ';
	          				result = result +'         </td>';
	                    	  return result;
	                      }
                    }],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#payableListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#payableDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getPayableInvoiceList();
    	   }
    	});
}

</script>


<script>
function showPopUp(id,invoiceUUID,invoiceStatus,invoiceParentId,totalAmount,fileUrl,paymentStatus,dueAmount){
		 removePopup();
	     result = "";
	     if(invoiceParentId == 'undefined'){
	    	 result = result +'    <ul class="list-unstyled menupop"> ';
	     }else{
	    	 if(invoiceStatus == 'DRAFT'){
	    		  result = result +'    <ul class="list-unstyled menupop"> ';
	    	  }else if(invoiceParentId != 'null'){
	    		  result = result +'    <ul class="list-unstyled menupop"> ';
	    	  }else{
	    		  result = result +'    <ul class="list-unstyled menupop"> ';

	    	  }
	     }
		 result = result+' <i class="fa fa-close close-div closediv-small" onClick="removePopup();"></i>';
		 result = result +'    <input type="hidden" id="invoicePopupId" value="'+invoiceUUID+'"> ';
		 if((invoiceParentId == 'undefined' || invoiceParentId == 'null') && invoiceStatus != 'DRAFT' && paymentStatus != 'Paid' && invoiceStatus != 'Void'){
			 '<sec:authorize access="hasAnyRole(\'ROLE_SEND_BILL_REMINDER\')">';
			 result = result +'<li><a onclick="sendReminder(\''+invoiceUUID+'\');"><i class="fa fa-bullhorn paddingRight paddingLeft" title="Send reminder"></i>Send reminder</a> </li>';
			 '</sec:authorize>'
		 }
		 '<sec:authorize access="hasAnyRole(\'ROLE_PRINT_BILL\')">';
		 
		 if(invoiceStatus == "DRAFT"){
			 result = result +' <li><a onClick="deleteBill(\''+invoiceUUID+'\');"><i class="fa fa-trash paddingRight paddingLeft" title="Remove Bill"></i>Delete</a></li>';
 
		 }
		 if(invoiceStatus == "Accepted"){
			 if(paymentStatus != "Paid"){
				 result = result +' <li><a onclick="receivePaymentPop1('+id+','+dueAmount+');"><i class="fa fa-money paddingRight paddingLeft" title="Accept Bill"></i>Pay</a></li>';
				 result = result +' <li><a onclick="voidBill(\''+invoiceUUID+'\');"><i class="fa fa-trash paddingRight paddingLeft" title="Void Bill"></i>Void Bill</a></li>';

			 }
			 
		 }
		 if(invoiceStatus == "SENT"){
				 result = result +' <li><a onclick="receivePaymentPop1('+id+','+dueAmount+');"><i class="fa fa-money paddingRight paddingLeft" title="Accept Bill"></i>Pay</a></li>';
				 result = result +' <li><a onclick="voidBill(\''+invoiceUUID+'\');"><i class="fa fa-trash paddingRight paddingLeft" title="Void Bill"></i>Void Bill</a></li>';

		 }
// 	     result = result +'      <li onclick="getPrint('+id+');"><i class="fa fa-print paddingLeft paddingRight" title="Print"></i>Print</li>';
		result = result+' <li><a class="" href="'+fileUrl+'" title="Print" target="_blank"><i class="fa fa-print paddingLeft paddingRight"></i>Print</a></li>';
	     '</sec:authorize>'
	     
	     '<sec:authorize access="hasAnyRole(\'ROLE_DOWNLOAD_BILL\')">';
// 	     result = result +'      <li onclick="downloadInvoice('+id+');"><i class="fa fa-download paddingLeft paddingRight" title="Download"></i>Download</li>';
 		 result = result+' <li><a href="'+fileUrl+'" title="Download" download><i class="fa fa-download paddingLeft paddingRight"></i>Download</a></li>';
	     '</sec:authorize>'
	     if(invoiceStatus != 'DRAFT' && invoiceStatus != 'Void')
	     {
	     result = result +'  <li><a onclick="linkPayableInvoice(\''+invoiceUUID+'\',\''+totalAmount+'\');"><i class="fa fa-link paddingLeft paddingRight" title="Link Bill"></i>Link Bill</a></li>';
	     }
	     result = result +'    </ul>';
    	 $("#popupDiv"+invoiceUUID+"").append(result);
    	 showMenu();
}
</script>
<script>
function removePopup(){
	if( document.getElementById("invoicePopupId") != null &&  document.getElementById("invoicePopupId") != 'undefined'){
		var invoicePopupId = document.getElementById("invoicePopupId").value;
		  $("#popupDiv"+invoicePopupId+"").empty(); 
	}
	
}
</script>
<script>
$(".menupop, .pull-right, .positionRelative, .menuWidget, .fa-ellipsis-v, #unbilled").click(function(e) {
	e.stopPropagation();
});

$('html').click(function(){
	$(".menupop").hide();
});
</script>
<script>
function sendReminder(invoiceUUID){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}'; 
	$.ajax({
    	 type: "POST",
	     dataType : 'json',
	    	 url: vmsUrl+"/sendReminder?"+tokenParamName+"="+tokenValue+"&invoiceUUID="+invoiceUUID, 
             success: function(result) {
            	 if (result && result.response === "INVALID_DATA") {
	          		    showError(result.responseMessage);
	          		} else if (result.response === "AWKWARD") {
	          		    location.href = "/errorPage";
	          		} else if (result.response === "SUCCESS") {
	          			$("#msgDiv").html("Successfully you have sent a reminder Mail..");
	          			msgFadein();
	          		} else if (result.indexOf('Sign In') !== -1) {
	          			location.replace(sourceleadUrl);
	          		} else {
	          		    showError("Operation failed, please try again");
	          		}
            	 removePopup();
        },
    });  
}

</script>


<script>
function acceptedInvoice(invoiceUUID){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	$.ajax({
    	 type: "POST",
	     dataType : 'json',
	    	 url: vmsUrl+"/acceptedInvoice?"+tokenParamName+"="+tokenValue+"&invoiceUUID="+invoiceUUID, 
             success: function(result) {
            	 if (result && result.response === "INVALID_DATA") {
	          		    showError(result.responseMessage);
	          		} else if (result.response === "AWKWARD") {
	          		    location.href = "/errorPage";
	          		} else if (result.response === "SUCCESS") {
	          			location.replace(vmsUrl+"/payablesList");
	          		} else if (result.indexOf('Sign In') !== -1) {
	          			location.replace(sourceleadUrl);
	          		} else {
	          		    showError("Operation failed, please try again");
	          		}
            	 
        },
    });  
}

</script>

<div id="popUp"></div>
<script>
function rejectInvoice(invoiceUUID){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<textarea id='reasonForDelete' class='form-control' field-name='Reject Reason'  data-validation='required validate_Space validate_Description validate_length length2-250' name='reasonForSpoc' value='' placeholder='Reject Reason' rows='2'></textarea>";
	result = result + ' <div id="reasonForRejectDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Reject' onclick='saveReasonforPayable(\""+invoiceUUID+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTermination();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script>
function saveReasonforPayable(invoiceUUID){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var reason = document.getElementById("reasonForDelete").value;
	if(reason != null && reason != ''){
		 $("#reasonForRejectDiv").empty();
		if(reason.length > 250){
		$("#reasonForRejectDiv").empty().append("<font color='red' style='padding: 5px 10px; '>The Entered values should not be more than 250 characters</font>") ; 
		}else{	  
		$.ajax({
		   	type: "POST",
		   	url: vmsUrl+"/rejectInvoice?"+tokenParamName+"="+tokenValue+"&invoiceUUID="+invoiceUUID+"&reason="+reason,	    	   
		    success: function(result) {
		    	if (result && result.response === "INVALID_DATA") {
          		    showError(result.responseMessage);
          		} else if (result.response === "AWKWARD") {
          		    location.href = "/errorPage";
          		} else if (result.response === "SUCCESS") {
          			$("#popUp").empty();
   	 			    $(".project_inn_mask").fadeOut(200);
   		    	    location.replace(vmsUrl+"/payablesList");
          		} else if (result.indexOf('Sign In') !== -1) {
          			location.replace(sourceleadUrl);
          		} else {
          		    showError("Operation failed, please try again");
          		}
		    	
		    }
	   });
		}
	}else{
		$("#reasonForRejectDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
	}
	  
}
</script>

<script>
function cancelbtn(){
	  $("#recvPymntpop, #recvPymntpopOnline").fadeOut(200);
      $('.modal-backdrop').fadeOut(200).removeClass('in');
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
<script>
    // Script for tabs next pre button
    var $tabs = $('.tabbable li');

    $('#prevtab').on('click', function() {
        $tabs.filter('.active').prev('li').find('a[data-toggle="tab"]').tab('show');
    });

    $('#nexttab').on('click', function() {
        $tabs.filter('.active').next('li').find('a[data-toggle="tab"]').tab('show');
    });
</script>
<script>
    function showMenu() {
        $('.menupop').toggle();
        
    };
</script>

<script>
$(document).ready(function() {
    $("#invoiceID1").click(function(){
		$("#invoiceTabOne").slideToggle(0);
		$("#paymentTabOne, #exportPop").slideUp(200);
	});
    $("html").click(function() {
        $("#invoiceTabOne").slideUp(0);
    });
    $("#invoiceTabOne, #invoiceID1, .tab-pane, .billing_dashboard, .nav-tabs").click(function(e) {
        e.stopPropagation();
    });
    $('.tab-pane, .billing_dashboard, .nav-tabs').click(function(){
    	$("#invoiceTabOne").slideUp(200);
    });
});

$(document).ready(function() {
    $("#paymentID1").click(function(){
		$("#paymentTabOne").slideToggle(0);
		$("#invoiceTabOne, #exportPop").slideUp(200);
	});
    $("html").click(function() {
        $("#paymentTabOne").slideUp(0);
    });
    $("#paymentTabOne, #paymentID1, .tab-pane, .billing_dashboard, .nav-tabs").click(function(e) {
        e.stopPropagation();
    });
    $('.tab-pane, .billing_dashboard, .nav-tabs').click(function(){
    	$("#paymentTabOne").slideUp(200);
    });
    
});
</script>

<script>
    //search script
        function customInvoiceList() {
        	var selectVal = $('input[name=invoiceCustomDays]:checked').val();
            if (selectVal == "custom") {
            	var result = '';
            	result  = result + '<div class="col-md-6 paddingLeft" id="errStartDate">';                                                                       
            	result = result +'     <input type="text" class="form-control input-sm calendar" id="billingStartDate" readonly = "readonly" placeholder="Start Date"> ';
            	result = result +'   </div> ';
            	result = result +'   <div class="col-md-6 noPad" id="errEndDate">';
            	result = result +'     <input type="text" class="form-control input-sm calendar" id="billingEndDate" readonly = "readonly" placeholder="End Date">';
            	result = result +'   </div>';
                $(".custom_search").empty().append(result);
                $(".custom_search").show();
            }else {
                $(".custom_search").empty().hide();
            }
            
            var locationDate ='${SourceLeadWeb_dateFormat}'; 
        	  var finalDateFormatEduSave = getJSDateFormat(locationDate);
        	  
        	$('#billingStartDate').datepicker({
        	      dateFormat: finalDateFormatEduSave,
        	      changeMonth: true,
        	  	  changeYear: true, 
        	      onSelect: function (selected) {
        	            //var dt = new Date(selected);
        	            var dt = $(this).datepicker('getDate', '+1d');
        	            dt.setDate(dt.getDate() + 1);
        	            $("#billingEndDate").datepicker("option", "minDate", dt);
        	          removeStartdateval();
        	        }
        	  });
        	  
        	  $('#billingEndDate').datepicker({
        	      dateFormat: finalDateFormatEduSave,
        	      changeMonth: true,
        	  	  changeYear: true, 
        	        onSelect: function (selected) {
        	            //var dt = new Date(selected);
        	            var dt = $(this).datepicker('getDate', '-1d');
        	            dt.setDate(dt.getDate() - 1);
        	            $("#billingStartDate").datepicker("option", "maxDate", dt);
        	          removeEnddateval();
        	        }
        	  });
        }
       
</script>

<script>
function customDate(){
	var result = "";                                                                                                                                  
	result = result +'	<div class="clearfix row"> <span class="pull-left ml">Custom Search</span>                                                     ';
	result = result +'   <div class="closeSearch pull-right mr" title="Close Div" onclick="closePopup();">X</div>                                                              ';
	result = result +'   <div class="clearfix mt10"></div>                                                                                             ';
	result = result +'   <div class="col-md-5 paddingRight" id="errStartDate">                                                                                           ';
	result = result +'     <input type="text" class="form-control input-sm calendar" id="billingStartDate" placeholder="Start Date">                   ';
	result = result +'                                                                                                                                 ';
	result = result +'   </div>                                                                                                                        ';
	result = result +'   <div class="col-md-5 paddingLeft paddingRight" id="errEndDate">                                                                                            ';
	result = result +'     <input type="text" class="form-control input-sm calendar" id="billingEndDate" placeholder="End Date">                       ';
	result = result +'                                                                                                                                 ';
	result = result +'   </div>                                                                                                                        ';
	result = result +'   <div class="col-md-2 paddingLeft" onclick="searchResults();">                                                                ';
	result = result +'     <button type="submit" class="btn btn-sm btn-primary btn-block"><i class="fa fa-search paddingRight"></i></button>           ';
	result = result +'   </div>                                                                                                                        ';
	result = result +' </div>                                                                                                                          ';
	$("#customDateDivId").empty();
	$("#customDateDivId").append(result);
	
	 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormatPayableDate = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormatPayableDate = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormatPayableDate = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormatPayableDate = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormatPayableDate = 'yy-dd-mm'; 
	  }
	  
	
	
	$('#billingStartDate').datepicker({
		changeMonth: true,
		changeYear: true, 
		dateFormat : finalDateFormatPayableDate,
		onSelect : function(selected) {
		 var dt = $(this).datepicker('getDate', '+1d');
	      dt.setDate(dt.getDate() + 1);
			$("#billingEndDate").datepicker("option", "minDate", dt);
			removeStartdateval();
		}
	
	});
	
	$('#billingEndDate').datepicker({
		changeMonth: true,
		changeYear: true, 
		dateFormat : finalDateFormatPayableDate,
		beforeShow: function(){
	           this.value='';
	     },
		onSelect : function(selected) {
			 var dt = $(this).datepicker('getDate', '-1d');
	            dt.setDate(dt.getDate() - 1);
			$("#billingStartDate").datepicker("option", "maxDate", dt);
			removeEnddateval();
		}
	});
}
</script>

<script>
function toDate(selector) {
    var from = $(selector).val().split("-");
    return new Date(from[2], from[1] - 1, from[0]);
}
</script>
<script>
function closePopup() {
         $(".custom_search").toggle();
}
</script>

<script>
function removeStartdateval(){
	$("#errStartDate").find('p.jquery_form_error_message').remove();
}
function removeEnddateval(){
	$("#errEndDate").find('p.jquery_form_error_message').remove();
}

</script>

<script>

$('#mypop').click(function(){
		$("#exportPop").toggle();
		$("#filter2ApprovedPop, #filter1ApprovedPop, .action-pop, .approved_pop").slideUp();
 });
 $("html").click(function(){
	$("#exportPop, .ExpPop").hide(200);
 });
 $(".billing_dashboard, .nav-tabs").click(function(){
		$("#exportPop, .ExpPop").hide(200);
	 });
 $("#exportPop, #mypop, .nav-tabs, .billing_dashboard").click(function(e) {  
     e.stopPropagation();
 });
</script>

<script>
function exportToPayablePdf(){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	 var invoiceStatus = [];
	 var paymentStatus = [];
	 
		$('input:checkbox[name=invoiceStatus]:checked').each(function() 
	    {
			invoiceStatus.push($(this).val());
	    });
		
		$('input:checkbox[name=paymentStatus]:checked').each(function() 
		 {
			paymentStatus.push($(this).val());
		});
		var tags = [];
		$('input:checkbox[name=tags]:checked').each(function() 
	     {
			tags.push($(this).val());
		 });
		var emptyExport = $('#emptyPayableInvoiceId').val();
		
		var durationType = null;
		$('input:radio[name=invoiceCustomDays]:checked').each(function() {
		durationType=$(this).val();
		});
		
		var invoiceDeleteStatus = null;
		
		$('input:radio[name=invoiceStatusDelete]:checked').each(function() {
			invoiceDeleteStatus = $(this).val();
		});
		
		var exportStartDate = $("#billingStartDate").val();
		var exportEndDate = $("#billingEndDate").val();
		
		if(emptyExport > 0){
		window.location.href = vmsUrl+"/getBillingExportList?"+tokenParamName+"="+tokenValue+"&invoiceStatus="+invoiceStatus+"&paymentStatus="+paymentStatus+"&durationType="+durationType+"&exportStartDate="+exportStartDate+"&exportEndDate="+exportEndDate+"&tags="+tags+"&invoiceDeleteStatus="+invoiceDeleteStatus+"&exportType=pdf&invoiceType=Payables";
		}else{
				var result = "";
				result = result +"	<div class='project_inn_mask'></div>";
				result = result +"	   <div id='empty_requirement_pop' class='remove_user_pop remove_user_pop_a'>";
				result = result +"		<p>Payables List is Empty</p>";
				result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
				result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancelExport();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelExport();'></b>";
				result = result +"	</div>";
				$("#popUp").append(result);
				$("#empty_requirement_pop").slideDown(200);
				$(".project_inn_mask").fadeIn(200);
}
}
</script>

<script>
function exportToPayableExcel(){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	 var invoiceStatus = [];
	 var paymentStatus = [];
	 
		$('input:checkbox[name=invoiceStatus]:checked').each(function() 
	    {
			invoiceStatus.push($(this).val());
	    });
		
		$('input:checkbox[name=paymentStatus]:checked').each(function() 
		 {
			paymentStatus.push($(this).val());
		});
		
		var tags = [];
		$('input:checkbox[name=tags]:checked').each(function() 
	     {
			tags.push($(this).val());
		 });
		
		var emptyExport = $('#emptyPayableInvoiceId').val();
		
		var durationType = null;
		$('input:radio[name=invoiceCustomDays]:checked').each(function() {
		durationType=$(this).val();
		});
		
		var invoiceDeleteStatus = null;
		
		$('input:radio[name=invoiceStatusDelete]:checked').each(function() {
			invoiceDeleteStatus = $(this).val();
		});
		
		var exportStartDate = $("#billingStartDate").val();
		var exportEndDate = $("#billingEndDate").val();
		
		if(emptyExport > 0){
		window.location.href = vmsUrl+"/getBillingExportList?"+tokenParamName+"="+tokenValue+"&invoiceStatus="+invoiceStatus+"&paymentStatus="+paymentStatus+"&durationType="+durationType+"&exportStartDate="+exportStartDate+"&exportEndDate="+exportEndDate+"&tags="+tags+"&invoiceDeleteStatus="+invoiceDeleteStatus+"&exportType=xls&invoiceType=Payables";
		}else{	
				var result = "";
				result = result +"	<div class='project_inn_mask'></div>";
				result = result +"	   <div id='empty_requirement_pop' class='remove_user_pop remove_user_pop_a'>";
				result = result +"		<p>Jobs List is Empty</p>";
				result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
				result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancelExport();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelExport();'></b>";
				result = result +"	</div>";
				$("#popUp").append(result);
				$("#empty_requirement_pop").slideDown(200);
				$(".project_inn_mask").fadeIn(200);
}
}
</script>

<script type="text/javascript">
function linkPayableInvoice(invoiceUUID,totalAmount){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}'; 
	$.ajax({
    	 type: "POST",
	     dataType : 'json',
	     url : vmsUrl+"/getClientBasedInvoiceList?"+tokenParamName+"="+tokenValue+"&invoiceUUID="+invoiceUUID+"&invoiceType=payables",
			success: function(json) {
			var result = "";
			result = result +'  <div class="modal-dialog modal-lg">                                                                                         ';
			result = result +'    <div class="modal-content">                                                                                               ';
			result = result +' <input type="hidden" value='+invoiceUUID+' name="masterInvoiceId" id="masterInvoiceId">   ';
			result = result +'      <div class="modal-header">                                                                                              ';
			result = result +'        <button type="button" class="close" data-dismiss="modal" onClick="closeLinkInvoice();">×</button>                                                   ';
			result = result +'        <h4 class="modal-title">Link Bill</h4>                                                                             ';
			result = result +'	<span class="jquery_form_error_message" style="display:none; padding-left:0px !important; font-size:12px;">Select at least 1 Bill</span>';
			result = result +'      </div>                                                                                                                  ';
			result = result +'      <div class="modal-body clearfix">                                                                                       ';
			result = result +' <div class="timesheet_out clearfix">                                                                                         ';
			result = result +'      <div class="candidate_wrap_table">                                                                                      ';
			result = result +'      <table id="linkInvoicePayable">                                                                                                                 ';
			result = result +'        <thead>                                                                                                               ';
			result = result +'          <tr>                                                                                                                ';
			result = result +'            <th>&nbsp;                                                                                                        ';
			result = result +'                                                                                                                              ';
			result = result +'              </th>                                                                                                           ';
			result = result +'            <th>ID</th>                                                                                                       ';
			result = result +'            <th>Contact Person</th>                                                                                           ';
			result = result +'            <th>Due Date</th>                                                                                                 ';
			result = result +'            <th>Bill Date</th>                                                                                                ';
			result = result +'            <th>Total</th>                                                                                                    ';
			result = result +'            <th>Due Amount</th>                                                                                               ';
			result = result +'            <th>Status</th>                                                                                                   ';
			result = result +'          </tr>                                                                                                               ';
			result = result +'        </thead>                                                                                                              ';
			result = result +'        <tbody>                                                                                                               ';
			if(json.data != null && json.data != 'undefined' && json.data != ""){ 
// 			var json = JSON.parse(data);
			for (var i = 0; i < json.data.length; i++) { 
			var opt = json.data[i]; 
			result = result +'          <tr>                                                                                                                ';
			result = result +'            <td>                                                                                                              ';
			result = result +'                <input type="radio" value='+opt.id+' name="linkedInvoice">                                                                     ';
			result = result +'            </td>                                                                                                             ';
			result = result +'            <td>                                                                                                              ';
			result = result +'              <div class="tbldata-dotdot-100">'+opt.invoiceId+'</div>                                                                   ';
			result = result +'            </td>                                                                                                             ';
			result = result +'            <td>';
			if(opt.clientContactName !=null && opt.clientContactName != '' && opt.clientContactName !='undefined'){
				result = result +'              <div class="tbldata-dotdot-sm">'+opt.clientContactName+'</div>';
			}else{
				result = result +'<div class="tbldata-dotdot-sm">--</div>';
			}
			
			result = result +'            </td>                                                                                                             ';
			result = result +'            <td>';
			if(opt.dueDateStr !=null && opt.dueDateStr != '' && opt.dueDateStr !='undefined'){
				result = result +'<div class="tblminwidth-75">'+opt.dueDateStr+'</div> ';	
			}else{
				result = result +'<div class="tblminwidth-75">--</div> ';
			}
			result = result +'            </td>                                                                                                             ';
			result = result +'            <td>';
			if(opt.invoiceDateStr !=null && opt.invoiceDateStr !='' && opt.invoiceDateStr !='undefined'){
				result = result +'<div class="tblminwidth-75">'+opt.invoiceDateStr+'</div>';	
			}else{
				result = result +'<div class="tblminwidth-75">--</div>';
			}
			
			result = result +'            </td>                                                                                                             ';
			result = result +'            <td>                                                                                                              ';
			result = result +'              <div class="tbldata-dotdot_80" id="linkedPayableTotalId'+opt.id+'">'+opt.total+'</div>                                                                 ';
			result = result +'            </td>                                                                                                             ';
			result = result +'            <td>                                                                                                              ';
			result = result +'              <div class="tbldata-dotdot_80">'+opt.dueAmount+'</div>                                                                 ';
			result = result +'            </td>';
			var status = null;
			if(opt.invoiceStatus == 'DRAFT')
			  {
				status="Draft";
			  }
			else if(opt.invoiceStatus == 'SENT')
			  {
				status="Sent";
			  }
			else
			 {
				status=opt.invoiceStatus;
			 }
			result = result +'            <td>                                                                                                              ';
			result = result +'              <div class="tbldata-dotdot_100">'+status+'</div>                                                                    ';
			result = result +'            </td>                                                                                                             ';
			result = result +'          </tr>                                                                                                               ';
			}
			}else{
				result = result +' <div class="col-md-12" style="display:none">';
				result = result +' <div class="dataTables_empty"></div>';
				result = result +' </div>';
			}
			result = result +'        </tbody>                                                                                                              ';
			result = result +'      </table>                                                                                                                ';
			result = result +'    </div>                                                                                                                    ';
			result = result +'     <div class="clearfix mt10">                                                                                              ';
			result = result +'         <div class="pull-right">                                                                                             ';
			result = result +'             <button type="button" class="btn btn-primary" onClick="mergeInvoice(\''+totalAmount+'\');"><i class="fa fa-external-link paddingRight"></i>Link</button>      ';
			result = result +'         </div>                                                                                                               ';
			result = result +'     </div>                                                                                                                   ';
			result = result +'    </div>                                                                                                                    ';
			result = result +'    <!-- Pop Link Invoice Pop -->                                                                                             ';
			result = result +'  </div>                                                                                                                      ';
			result = result +'</div>                                                                                                                        ';
		
			$("#linkPayblePopup").empty();
			$("#linkPayblePopup").append(result);
			linkInvoicePayablePagination();
			$('.modal.modal_invoice').fadeIn(200);
	        $('.side_mask').fadeIn(200);
			   
			    },
	 }); 
}
</script>


<script>
function linkInvoicePayablePagination(){  

    $('#linkInvoicePayable').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [5, 15, 20, "All"]],
    	"bLengthChange": true,
    	"aaSorting": [[3,'desc']],
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 0
			}],
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $("#linkInvoicePayable").wrap("<div class='overflow_Auto'></div>");
}
</script>

<!-- Script for Linked Invoice pop -->
<script>
        $(".sendReminder").click(function(){
        $('.modal.modal_invoice').fadeIn(200);
        $('.side_mask').fadeIn(200);
        });
        
        $(".close, .cancel").click(function(){
        $('.modal.modal_invoice').fadeOut(200);
        $('.side_mask').fadeOut(200);
        });
        function closeLinkInvoice(){
            $('.modal.modal_invoice').fadeOut(200);
            $('.side_mask').fadeOut(200);
        }
    </script>
<!-- Script for Linked Invoice pop -->
<script>
function mergeInvoice(parentTotalAmount){
	var invoiceUUID = $('#masterInvoiceId').val();
	var currencyCode = '${SourceLeadWeb_currencyCode}';
	var linkRadio = $('input[name=linkedInvoice]');
	var checkVal = linkRadio.filter(':checked').val();
	var total = $('#linkedPayableTotalId'+checkVal+'').text();
	var calculatedAmount = 	parentTotalAmount - total;
	if(checkVal != '' && checkVal != 'undefined' && checkVal != null){
	showDiffAmountPopup(calculatedAmount,currencyCode,checkVal,invoiceUUID);
}else{
	$('span.jquery_form_error_message').show();
	} 
}
</script>

<script>
function showDiffAmountPopup(calculatedAmount,currencyCode,checkVal,invoiceUUID){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='payable_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Variation Amount  = "+calculatedAmount.toFixed(2)+" "+currencyCode+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input type='button' value='Proceed' onclick='proceedToMergeBill("+checkVal+",\""+invoiceUUID+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#payable_pop").fadeIn(200).css({"z-index":"999999"});
	$(".project_inn_mask").fadeIn(200).css({"z-index":"105000"});
	
}

</script>

<script>
function proceedToMergeBill(checkVal,invoiceUUID){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	$.ajax({
	   	 type: "POST",
		     dataType : 'json',
		    	 url: vmsUrl+"/linkInvoice?"+tokenParamName+"="+tokenValue+"&checkVal="+checkVal+"&invoiceType=payables&invoiceUUID="+invoiceUUID, 
	            success: function(json) {
	            	$("#popUp").empty();
	            	$(".project_inn_mask").fadeOut(200);
	            	location.replace(vmsUrl+"/payablesList");	
	       },
	   });  
}
</script>

<script type="text/javascript">
function cancel(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
	location.replace(vmsUrl+"/payablesList");	
}
</script>

<script type="text/javascript">
function cancelExport(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<script type="text/javascript">
function generateAutomaticInvoice(){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}'; 
	 $("#invoiceLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
    	 type: "POST",
	     dataType : 'json',
	    	 url: vmsUrl+"/generateAutomaticInvoice?"+tokenParamName+"="+tokenValue+"&invoiceType=payables&contractType=vendorSow", 
             success: function(json) {
            	 
            	 $("#invoiceLoading").removeAttr("style");
            	    $("#selector1").removeAttr("style");
	    			$("#selector1").css("display", "block");
	    			$("#msgDiv").html(json.object.count+"Bill(s) Generated Successfully.");
					msgFadein();
            	 location.replace(vmsUrl+"/payablesList");	
        },
    });  
}


function generateAutomaticBillForJobClosure(){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}'; 
	 $("#invoiceLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
   	 type: "POST",
	     dataType : 'json',
	    	 url: vmsUrl+"/generateAutomaticInvoiceForJobClosure?"+tokenParamName+"="+tokenValue+"&invoiceType=payables&contractType=vendorSow", 
            success: function(json) {
           	 
           	 $("#invoiceLoading").removeAttr("style");
           	    $("#selector1").removeAttr("style");
	    			$("#selector1").css("display", "block");
	    			$("#msgDiv").html(json.object.count+"Bill(s) Generated Successfully.");
					msgFadein();
           	 location.replace(vmsUrl+"/payablesList");	
       },
   });  
}
</script>

<script>
function unLinkBill(invoiceUUID){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}'; 
	$.ajax({
	   	 type: "POST",
		     dataType : 'json',
		    	 url: vmsUrl+"/unLinkInvoice?"+tokenParamName+"="+tokenValue+"&invoiceUUID="+invoiceUUID, 
	            success: function(data) {
	            	location.replace(vmsUrl+"/payablesList");	
	       },
	   });  
}
</script>

<script>
function deleteBill(invoiceUUID){ 
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";

	result = result +"		<p>Are you sure you want to delete ?</p>";
	result = result +"		<textarea  id='reasonForBill' name='reasonForBill'  placeholder='Comments here' ></textarea>";
	result = result + ' <div id="reasonForBillDiv"/>';
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Delete" onclick="saveBillRejectedReason(\''+invoiceUUID+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function voidBill(invoiceUUID){
	var result = "";
	$("#popUp").empty();
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";

	result = result +"		<p>Are you sure you want to Void Bill ?</p>";
	result = result + ' <div id="voidBillId"/>';
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="savevoidBill(\''+invoiceUUID+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function cancelTermination(){
	$("#popUp").empty();
	 $(".project_inn_mask").fadeOut(200);
}

function saveBillRejectedReason(invoiceUUID){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}'; 
	var reason = $("#reasonForBill").val();
	if(reason != null && reason != ''){
		 $("#reasonForBillDiv").empty();
		if(reason.length > 250){
		$("#reasonForBillDiv").empty().append("<font color='red' style='padding: 5px 10px; '>The Entered values should not be more than 250 characters</font>") ; 
		}else{
			 $.ajax({
				   	type: "POST",
				    url: vmsUrl+"/deleteInvoice?"+tokenParamName+"="+tokenValue+"&invoiceType=payables&invoiceUUID="+invoiceUUID+"&reason="+reason,
		   		    success: function(data) {
		   		    	$("#popUp").empty();
		   			 	$(".project_inn_mask").fadeOut(200);
		   			 	location.replace(vmsUrl+"/payablesList");	
		   		    }
			     });
		}
	}
	else{
			$("#reasonForBillDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
	}
	
}

function savevoidBill(invoiceUUID){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}'; 
			 $.ajax({
				   	type: "POST",
				    url: vmsUrl+"/voidInvoiceOrBill?"+tokenParamName+"="+tokenValue+"&invoiceUUID="+invoiceUUID,
				    success: function(result) {
		  		    	if (result && result.response === "INVALID_DATA") {
		          		    showError(result.responseMessage);
		          		} else if (result.response === "AWKWARD") {
		          		    location.href = "/errorPage";
		          		} else if (result.response === "SUCCESS") {
		          			$("#popUp").empty();
		   	 			    $(".project_inn_mask").fadeOut(200);
		   		    	    location.replace(vmsUrl+"/payablesList");
		          		} else if (result.indexOf('Sign In') !== -1) {
		          			location.replace(sourceleadUrl);
		          		} else {
		          		    showError("Operation failed, please try again");
		          		}
				    }
		  		    	
			     });
	
}

function continuePaymentOnline() {
	var vmsUrl ='${SourceLeadWeb_vmsUrl}'; 
        var amount = $("[name=paymentAmountOnline][value=custom]").is(":checked") ? $("#customeAmountOnline").val() : $("#totalAmountOnline").val();
    if(amount === "") {
        showError("Enter payment amount");
        return;
    }
    $("#save_paymentOnline").attr("disabled", "disabled");
    $.post(vmsUrl+"/wepay-iframe-payment", {notes: $("#paymentMemoOnline").val(), paymentType: $("[name=paymentAmountOnline][value=custom]").is(":checked") ? 0 : 1, invoiceUUID: $("#paymentInfoOnline").data('invoiceid'), amount: amount}, function(data) {
        if(data && data === "FAILED") {
            
        } else if(data) {
            $("#paymentInfoOnline").hide();
            WePay.iframe_checkout("wepay_checkout", data);
            $("#save_paymentOnline").removeAttr("disabled", "disabled");
        }
    });
}
function initPaymentOnline(invoiceId, dueAmount) {
    $("#totalAmountOnline").val(dueAmount);
    $("#paymentInfoOnline").data('invoiceid', invoiceId);
    $("#recvPymntpopOnline").fadeIn(200);
    $('.modal-backdrop').fadeIn(200).addClass('in');
}
</script>



<script>
	function msgFadein() {
		$("#selector1").fadeIn(200);
        $('#selector1').delay(2000).fadeOut('slow');
	}
</script>
<script>
  $("#selector").fadeIn(200);
  $('#selector').delay(2000).fadeOut('slow');
	
	 
</script>
<!-- ========================== Filter popup Written By Laxman ===============================  -->
<script>
function closeBillsFilterPop(){
	$("#assetFilterId").hide();
	$(".advancedsearch-mask").fadeOut();
}
function openBillsFilterPop(){
	 $("#assetFilterId").toggle();
	 $(".advancedsearch-mask").fadeIn();
}
function resetBillsFilter(){
	$("ul#paymnetFilter > li input:checkbox").prop("checked",false);
	$("ul#statusFilter > li input:checkbox").prop("checked",false);
	document.getElementById("invoiceDateRadio").checked = true;
	$(".custom_search").empty().hide();
	getPayableInvoiceList();
	
}
function resetingBillsFilter(){
	$("ul#paymnetFilter > li input:checkbox").prop("checked",false);
	$("ul#statusFilter > li input:checkbox").prop("checked",false);
	document.getElementById("invoiceDateRadio").checked = true;
	$(".custom_search").empty().hide();
	
}
</script>


<div class="advancedsearch-mask"></div>
          <div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search-small" id="assetFilterId">
				<h5 class="headerAdsrch">Filter Search
					<a href="javascript:void(0)" class="close-div" onClick="closeBillsFilterPop()"><i
						class="fa fa-close"></i></a>
				</h5>
				<div class="addInner">
					<div class="row">
					  <div class="col-md-4">
						<div class="row">
						    <div class="col-md-12 mb">
								<label><strong>Payment</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="paymnetFilter">
									<li class="checkBox positionRelative" ><input type="checkbox"  value="Paid" name="paymentStatus"><label for="onboard3">Paid</label></li>
	                                <li class="checkBox positionRelative" ><input type="checkbox"  value="Partial Paid" name="paymentStatus"><label for="onboard2">Partially Paid</label></li>
	                                <li class="checkBox positionRelative" ><input type="checkbox"  value="Overdue" name="paymentStatus"><label for="onboard4">Overdue</label></li>
	                                <li class="checkBox positionRelative" ><input type="checkbox" value="Unpaid" name="paymentStatus"><label for="onboard1">Unpaid</label></li>
			                        </ul>
							      </div>
						     </div>
						     
						    <div class="col-md-12 mb">
								<label><strong>Active Status</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="statusFilter">
                                    <li class="checkBox positionRelative" ><input type="radio" checked="checked" value="ACTIVE" name="invoiceStatusDelete"><label for="onboard2">Active</label></li>
	                                <li class="checkBox positionRelative" ><input type="radio" value="DELETED" name="invoiceStatusDelete"><label for="onboard2">Deleted</label></li>
			                        </ul>
							      </div>
						     </div> 
						  </div>
						</div>
						
						<div class="col-md-4">
						<div class="row">
						    <div class="col-md-12 mb">
								<label><strong>Status</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="statusFilter">
									<li class="checkBox positionRelative" ><input type="checkbox" value="DRAFT" name="invoiceStatus"><label for="onboard1">Draft</label></li>
                                    <li class="checkBox positionRelative" ><input type="checkbox" value="SENT" name="invoiceStatus"><label for="onboard2">Sent</label></li>
                                    <li class="checkBox positionRelative" ><input type="checkbox" value="RECEIVED" name="invoiceStatus"><label for="onboard1">Received</label></li>
	                                <li class="checkBox positionRelative" ><input type="checkbox"  value="ACCEPTED" name="invoiceStatus"><label for="onboard2">Accepted</label></li>
	                                <li class="checkBox positionRelative" ><input type="checkbox" value="REJECTED" name="invoiceStatus"><label for="onboard2">Rejected</label></li>
	                                <li class="checkBox positionRelative" ><input type="checkbox" value="LINKED" name="invoiceStatus"><label for="onboard2">Linked</label></li>
	                                <li class="checkBox positionRelative" ><input type="checkbox" value="VOID" name="invoiceStatus"><label for="onboard2">Void</label></li>
			                        </ul>
							      </div>
						     </div> 
						  </div>
						</div>
						
						<div class="col-md-4">
						<div class="row">
						    <div class="col-md-12 mb">
								<label><strong>Updated Status</strong></label>
								 <div class="clearfix positionRelative">
									<ul class="list-unstyled" id="statusFilter">
                                    <li class="checkBox positionRelative" ><input type="radio" onclick="customInvoiceList();" checked="checked" id="invoiceDateRadio" value="month" name="invoiceCustomDays"><label for="onboard2">Last 30 days</label></li>
	                                <li class="checkBox positionRelative" ><input type="radio" onclick="customInvoiceList();" value="twomonths" name="invoiceCustomDays"><label for="onboard2">Last 60 Days</label></li>
	                                <li class="checkBox positionRelative" ><input type="radio" onclick="customInvoiceList();" value="threemonths" name="invoiceCustomDays"><label for="onboard2">Last 90 days</label></li>
	                                <li class="checkBox positionRelative" ><input type="radio" onclick="customInvoiceList();" value="sixmonths" name="invoiceCustomDays"><label for="onboard2">Last 180 days</label></li>
	                                <li class="checkBox positionRelative" ><input type="radio" onclick="customInvoiceList();" value="oneyear" name="invoiceCustomDays"><label for="onboard2">Last 365 days</label></li>
	                                <li class="checkBox positionRelative" ><input type="radio" onclick="customInvoiceList();" value="custom" id="customRadio" name="invoiceCustomDays"><label for="onboard2">Custom</label></li>
			                        </ul>
						              <div class="custom_search popCusDate">
						            </div>
							      </div>
						     </div> 
						  </div>
						</div>
						
						
						<c:if test="${!empty billsTagsList}">
						<div class="col-md-4 positionRelative mb">
							<div class="clearfix dynamic_search filterDynamicsearch">
								<input type="text" id="myInvoiceInput" onkeyup="myInvoiceFunction()" placeholder="Tags" title="Type in a tag">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul">
								<ul class="list-unstyled positionRelative filterSearch mt5" id="myInvoiceUL">
									<c:forEach var="tag" items="${billsTagsList}">
									<li class="checkBox positionRelative"><input  type="checkbox" name="tags" value ="${tag}" ><label>${tag}</label></li>
									</c:forEach> 
								</ul>
							 </div> 
						</div>
						</c:if>
					  </div>
					  
					  
					  
					</div>
					<div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Apply" id="search_requiremet" onclick="getPayableInvoiceList();" class="btn btn-sm btn-success" />
							<input type="button" value="Reset" id="clear_search" onclick="resetingBillsFilter();" class="btn btn-sm btn-primary" />
						</div>
					</div>
				</div>


<script>
function getActions(){
	$(".action-pop").slideToggle(200);
	$(".newActPop").slideUp(200);
}	
$("html").click(function(){
	$(".action-pop").slideUp(200);
});
$(".action, .action-pop").click(function(e){
 	e.stopPropagation();
});	


function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
}
$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});	


</script>				
<jsp:include page="../masterFooter.jsp" />				
<script type="text/javascript" src="https://www.wepay.com/min/js/iframe.wepay.js" ></script>
