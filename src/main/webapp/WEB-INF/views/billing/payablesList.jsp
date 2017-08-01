<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png"
	href="resources/images/fav.png" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<script type="text/javascript">
$(document).ready(function(){
	getPayableInvoiceList();
});
</script>


<style type="text/css">
.invoiceId_Anchor a {
	color: #337ab7 !important;
}

.successBtn {
	background-color: #1ab394;
	border-color: #1ab394;
	border: 1px solid;
	color: #fff;
	padding: 1px 5px;
	border-radius: 3px;
	font-size: 13px;
	width: 60px;
}

.ui-corner-all, .ui-corner-bottom, .ui-corner-right, .ui-corner-br {
	z-index: 900000 !important;
}

.modal {
	z-index: 15000;
}

.remove_user_pop.remove_user_pop_a {
	z-index: 18000;
}
</style>

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
			<h2>Payables</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>
			&raquo; Payables List
		</div>
	</section>
	<div class="content-area content-new clearfix">
		<div class="billing_dashboard">
			<div class="tabbable positionRelative">
				<div class="onboardcreateButtons">
					<div class="pull-right select-field ml">
						<select class="form-control input-sm" id="dayWiseTypeId"
							onchange="getPayableInvoiceList();customInvoiceList();">
							<option value="month">Last 30 days</option>
							<option value="twomonths">Last 60 Days</option>
							<option value="threemonths">Last 90 days</option>
							<option value="sixmonths">Last 180 days</option>
							<option value="oneyear">Last 365 days</option>
							<option value="custom">Custom</option>
						</select>

						<div class="custom_search">
							<div id="customDateDivId">
								</id>
							</div>
						</div>
					</div>
				</div>
				<ul class="nav nav-tabs" id="tab_bar">


					<li class="active">
						<h4>Unbilled</h4> <a data-toggle="tab"
						class="onboardCount billbg_orange" title="Draft Bills">
							<div class="pull-left">
								<strong>${SourceLeadWeb_currencyCode}</strong>
							</div>
							<div class="pull-right ml10">
								<div class="count" id="unbilledAmoumt"></div>
								<h5>
									Draft Bills<span class="paddingLeft" id="draftInvoiceCount"></span>
								</h5>
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
								<div class="count" id="dueAfterInvoiceAmount"></div>
								<h5>
									Overdue Bills<span class="paddingLeft"
										id="dueAfterInvoiceCount"></span>
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
								<div class="count" id="dueBeforeInvoiceAmount"></div>
								<h5>
									Open Bills<span class="paddingLeft" id="dueBeforeInvoiceCount"></span>
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
								<div class="count" id="paidInvoiceAmount"></div>
								<h5>
									Paid Bills<span class="paddingLeft" id="paidInvoiceCount"></span>
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
						<div class="timesheet_out mt">
							<div class="clearfix">
								<div class="timesheet_head">
									<ul>

										<li title="Export" class="mrPOP" id="mypop"><i
											class="glyphicon glyphicon-export"></i>
											<div id="exportPop" class="ExpPop">
												<ul>
													<li onclick="exportToPayablePdf();"><i
														class="fa fa-file-pdf-o"></i>&nbsp;&nbsp;PDF</li>
													<li onclick="exportToPayableExcel();"><i
														class="fa fa-file-excel-o text-primary"></i>&nbsp;&nbsp;Excel</li>
												</ul>
											</div></li>

										<li id="invoiceID1"><i class="fa fa-filter"></i><span>Status</span><i
											class="glyphicon glyphicon-play"></i>|
											<div class="approved_pop" id="invoiceTabOne">
												<ul>
													<li><input type="checkbox" value="DRAFT"
														name="invoiceStatus" id=""
														onclick="getPayableInvoiceList();"> <label
														for="onboard1">Draft</label></li>
													<li><input type="checkbox" id="" value="SENT"
														name="invoiceStatus" onclick="getPayableInvoiceList();">
														<label for="onboard2">Sent</label></li>
													<li><input type="checkbox" id="" value="RECEIVED"
														name="invoiceStatus" onclick="getPayableInvoiceList();">
														<label for="onboard2">Received</label></li>
													<li><input type="checkbox" id="" value="ACCEPTED"
														name="invoiceStatus" onclick="getPayableInvoiceList();">
														<label for="onboard2">Accepted</label></li>
													<li><input type="checkbox" id="" value="REJECTED"
														name="invoiceStatus" onclick="getPayableInvoiceList();">
														<label for="onboard2">Rejected</label></li>
													<li><input type="checkbox" id="" value="LINKED"
														name="invoiceStatus" onclick="getPayableInvoiceList();">
														<label for="onboard2">Linked</label></li>
												</ul>
											</div></li>

										<li id="paymentID1"><i class="fa fa-filter"></i><span>Payment</span><i
											class="glyphicon glyphicon-play"></i>|
											<div class="approved_pop" id="paymentTabOne">
												<ul>
													<li><input type="checkbox" id="" value="Paid"
														name="paymentStatus" onclick="getPayableInvoiceList();">
														<label for="onboard3">Paid</label></li>
													<li><input type="checkbox" id="" value="Partial Paid"
														name="paymentStatus" onclick="getPayableInvoiceList();">
														<label for="onboard2">Partially Paid</label></li>
													<li><input type="checkbox" id="" value="OverDue"
														name="paymentStatus" onclick="getPayableInvoiceList();">
														<label for="onboard4">OverDue</label></li>
													<li><input type="checkbox" value="Unpaid"
														name="paymentStatus" id=""
														onclick="getPayableInvoiceList();"> <label
														for="onboard1">Unpaid</label></li>
												</ul>
											</div></li>
										<li><sec:authorize access="hasAnyRole('ROLE_ADD_BILL')">
												<a href="javascript:void(0)" class="anchorTag"
													title="Create Bill" onclick="createBill();"> <i
													class="fa fa-plus paddingRight"></i>Create Bill
												</a>&nbsp;&nbsp;&nbsp;|
			          </sec:authorize>
										<li><a href="javascript:void(0)" class=""
											title="Generate Automatic Bill"
											onclick="generateAutomaticInvoice();"> <i
												class="fa fa-plus paddingRight"></i>Generate Automatic Bill
										</a>&nbsp;&nbsp;&nbsp;|</li>
										</li>
										<div class="clearfix"></div>
									</ul>
									<div class="clearfix"></div>

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
<div class="addcandidate-popup">
	<div class="row">
		<div class="col-md-12">
			<a href="javascript:void(0)" class="close-popdiv"
				onClick="closeCandidate()">&times;</a>
			<div class="col-md-4 mt25">
				<div class="small-widget bgGreen">
					<a href="<c:url value='/createPayables'/>"
						title="Create custom invoice">
						<div class="clearfix mb10">
							<img src="resources/images/invoice_icon.png" class="text-center"
								alt="create_receivables">
						</div> <strong class="clearfix">Vendor Based</strong>
					</a>
				</div>
			</div>

			<div class="col-md-4 mt25">
				<div class="small-widget bg-yellow">
					<a href="<c:url value='/createInvoiceProjectPayableBased'/>"
						title="Create project based invoice">
						<div class="clearfix mb10">
							<!--<i class="fa fa-usd fa-3x"></i>-->
							<img src="resources/images/invoice_icon.png" class="text-center"
								alt="Invoice">
						</div> <strong class="clearfix">Project Based </strong>
					</a>
				</div>
			</div>

			 <div class="col-md-4 mt25">
		          <div class="small-widget bg-purple"> <a href="other_receivables.html" title="Create other invoice">
		            <a href="<c:url value='/createExpensesPayableBill'/>" title="Create project based invoice">
		            <div class="clearfix mb10">
		              <i class="fa fa-usd fa-3x"></i>             
		             <img src="resources/images/invoice_icon.png" class="text-center" alt="Invoice">
		              </div>
		            <strong class="clearfix">Other</strong> </a> </div>
		        </div>
		 

		</div>
	</div>
</div>
<div class="pop_mask"></div>
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
function receivePaymentPop1(invoiceId,total){
	var currencyCode = '${SourceLeadWeb_currencyCode}';
	var paymentAmount = total.toFixed(2);
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
	result = result +'                      <input type="text" value="" placeholder="Total Due" class="form-control input-sm mt10 allownumericwithdecimal" id="customeAmount" name="customeAmount" readonly onblur="amountCal();">       ';
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
	result = result +'              <label><strong>Attachments</strong></label>                         ';
	result = result +'              <div class="timesheet_week_comm col-md-12 ">                        ';
	result = result +'                <div class="form-group"> <span>                                   ';
	result = result +'                  <input type="file"  id="paymentAttachment" name="paymentAttachment">                                            ';
	result = result +'                  <i class="fa fa-plus"></i></span> </div>                        ';
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
		dateFormat : 'dd-mm-yy',
		changeMonth: true,
		changeYear: true, 
		onSelect : function(selected) {
			removeValidate();
		}
	});
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
	 $("#customeAmount").removeAttr("readonly");
	 $(".allownumericwithdecimal").on("keypress keyup blur",function (event) {
	        $(this).val($(this).val().replace(/[^0-9\.]/g,''));
	            if ((event.which != 46 || $(this).val().indexOf('.') != -1) && ((event.which) < 48 || (event.which) > 57)) {
	                event.preventDefault();
	            }
	        });
}
function validMask(){
	  $('#customeAmount').attr("readonly","readonly");  
}
/* function amountCal(){
	var totalAmount =  $("#totalAmount").val();
	var customAmount =  $("#customeAmount").val();
	if(totalAmount > customAmount){
		var exceededAmount = customAmount - totalAmount
		alert("your payment is exceeded by "+exceededAmount.toFixed(2)); 
	}
} */
</script>
<!-- modal pop -->
<div class="modal-backdrop" style="display: none"></div>
<div id="recvPymntpop" class="modal" style="display: none;">
	<div id="receivePaymentId"></div>
</div>

<!-- modal pop -->
<div class="side_mask" style="display: none"></div>
<div class="modal modal_invoice datatable_filter" style="display: none;">
	<div id="linkPayblePopup" class="datatable_filter"></div>
</div>
<!-- modal pop -->


<script>
$(".allownumericwithdecimal").on("keypress keyup blur",function (event) {
    $(this).val($(this).val().replace(/[^0-9\.]/g,''));
        if ((event.which != 46 || $(this).val().indexOf('.') != -1) && (event.which < 48 || event.which > 57)) {
            event.preventDefault();
        }
    });

</script>

<script>
function cancelTermination(){
	$("#remove_user_pop, .project_inn_mask").fadeOut();
}
</script>
<!-- modal pop -->
<script>
function saveReceivePayment(invoiceId){
	 if ($('#receivePayment-form').validate(false, validationSettings)){  
		    var frm = $('#receivePayment-form').serializeFormJSON();
	        var con = JSON.stringify(frm);
	        con = con.replace(/[{}]/g, "");
	        //It will escape all the special characters
	        var jsonData = encodeURIComponent(con);
	        
	        var form = $('#receivePayment-form')[0];
	 	   var formData = new FormData(form); 
	        var sourceleadUrl ='${sourceleadUrl}';
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
		    	 url: sourceleadUrl+"/saveInvoicePayment?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&invoiceId="+invoiceId, 
		        success: function(result) {
		     		location.replace(sourceleadUrl+"/payablesList");
		     		
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
	var sourceleadUrl ='${sourceleadUrl}';
	var invoice = [];
	var payment = [];
	var dayWisetype = $("#dayWiseTypeId").val();
	
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
	
	if(dayWisetype=="custom"){
		dayWisetype = "";
	}
	
	var startDate = $("#billingStartDate").val();
	var endDate = $("#billingEndDate").val();
	
	if(startDate == null || startDate == '' || startDate == 'undefined'){
		$("#invoiceStartDate").empty().append("Start Date is Required") ; 
	}
	if(endDate == null || endDate == '' || endDate == 'undefined'){
		$("#invoiceEndDate").empty().append("End Date is Required") ; 
	} 
	
	
	$('input:checkbox[name=invoiceStatus]:checked').each(function() 
    {
		invoice.push($(this).val());
    });
	
	$('input:checkbox[name=paymentStatus]:checked').each(function() 
	 {
		payment.push($(this).val());
	 });
	
	
	$.ajax({
    	type: "GET",
        url: sourceleadUrl+"/getInvoiceList?${_csrf.parameterName}=${_csrf.token}&invoiceType=payables&invoiceStatus="+invoice+"&paymentStatus="+payment+"&dayWisetype="+dayWisetype+"&startDate="+startDate+"&endDate="+endDate,
        success: function(data) {
        	var json = JSON.parse(data);
        	var result = "";
        	
        	var draftCount = 0;
        	var draftTotalAmount = 0;
        	
        	var paidCount = 0;
        	var paidTotalAmount = 0;
        	
        	var dueAfterInvoiceCount = 0;
        	var dueAfterInvoiceAmount = 0;
        	
        	var dueBeforeInvoiceCount = 0;
        	var dueBeforeInvoiceAmount = 0;
        	
			result = result +'	<table id="invoiceListPagination">                                                                                                         ';
			result = result +'     <thead>                            ';
			result = result +'       <tr>                             ';
			result = result +'         <th>ID</th>                    ';
			result = result +'         <th>Vendor Name</th>           ';
			result = result +'         <th>Contact</th>               ';
			result = result +'         <th>Project Name</th>          ';
			result = result +'         <th>Bill Date<br>(<small class="custom-small">'+orgLocationDate+'</small>)</th>             ';
			result = result +'         <th>Total</th>                 ';
			result = result +'         <th>Due</th>                   ';
			result = result +'         <th>Due Date<br>(<small class="custom-small">'+orgLocationDate+'</small>)</th>              ';
			result = result +'         <th>Status</th>                ';
			result = result +'         <th>Payment Status</th>        ';
			result = result +'         <th>Action</th>                ';
			result = result +'       </tr>                            ';
			result = result +'     </thead>                           ';
			result = result +'     <tbody>                            ';
			
        	for (var i=0; i<json.length; i++)
    		{  
        		var report = json[i];
        		
        		
        		
        		draftCount = parseInt(draftCount) + parseInt(report.draftInvoiceCount);
       			if(report.draftInvoiceAmount != null && report.draftInvoiceAmount != '' && report.draftInvoiceAmount != 'undefined'){
       				draftTotalAmount = parseFloat(draftTotalAmount) + parseFloat(report.draftInvoiceAmount);
       			}
       			
       			
				paidCount = parseInt(paidCount) + parseInt(report.paidInvoiceCount);
       			if(report.paidInvoiceAmount != null && report.paidInvoiceAmount != '' && report.paidInvoiceAmount != 'undefined'){
       				paidTotalAmount = parseFloat(paidTotalAmount) + parseFloat(report.paidInvoiceAmount);
       			}
       			
       			dueAfterInvoiceCount = parseInt(dueAfterInvoiceCount) + parseInt(report.dueAfterInvoiceCount);
       			if(report.dueAfterInvoiceAmount != null && report.dueAfterInvoiceAmount != '' && report.dueAfterInvoiceAmount != 'undefined'){
       				dueAfterInvoiceAmount = parseFloat(dueAfterInvoiceAmount) + parseFloat(report.dueAfterInvoiceAmount);
       			}
       			
       			dueBeforeInvoiceCount = parseInt(dueBeforeInvoiceCount) + parseInt(report.dueBeforeInvoiceCount);
       			if(report.dueBeforeInvoiceAmount != null && report.dueBeforeInvoiceAmount != '' && report.dueBeforeInvoiceAmount != 'undefined'){
       				dueBeforeInvoiceAmount = parseFloat(dueBeforeInvoiceAmount) + parseFloat(report.dueBeforeInvoiceAmount);
       			}
       			var invoiceStatus = null;
       			if(report.invoiceStatus == "SENT"){
       				invoiceStatus = "Sent"
       			}else if(report.invoiceStatus == "DRAFT"){
       				invoiceStatus = "Draft"
       			}else{
       				invoiceStatus = report.invoiceStatus
       			}
        		
        		if(report.dueDateStr != null && report.dueDateStr != '' && report.dueDateStr !='undefined'){
        			dueDateStr = report.dueDateStr;
        		}else{
        			dueDateStr = "";
        		}
        		
        		if(report.invoiceDateStr != null && report.invoiceDateStr != '' && report.invoiceDateStr != 'undefined'){
        			invoiceDateStr = report.invoiceDateStr;
        		}else{
        			invoiceDateStr = "";
        		}
        		var projectName = "";
       			if(report.projectName != null && report.projectName != '' && report.projectName != 'undefined'){
       				projectName = report.projectName;
        		}else{
        			projectName = "--";
        		}	
        		result = result +'       <tr> ';
				result = result +'         <td>';
				if(report.invoiceStatus == "DRAFT" || report.invoiceStatus == "Rejected"){
					if(report.projectId != null && report.projectId != '' && report.projectId != 'undefined'){
						'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_BILL\')">';
						result = result +'           <div class="tbldata-dotdot_80 invoiceId_Anchor"><a href="'+sourceleadUrl+'/getInvoiceByUUID?invoiceUUID='+report.invoiceUUID+'&projectId='+report.projectId+'" title = "'+report.invoiceId+'">'+report.invoiceId+'</a></div>   ';
						'</sec:authorize>';
					}else{
						result = result +'           <div class="tbldata-dotdot_80 invoiceId_Anchor"><a href="'+sourceleadUrl+'/getInvoiceByUUID?invoiceUUID='+report.invoiceUUID+'" title = "'+report.invoiceId+'">'+report.invoiceId+'</a></div>   ';
					}
					

				}
				if(report.invoiceStatus == "SENT" || report.invoiceStatus == "Received" || report.invoiceStatus == "Accepted" || report.invoiceStatus == "Linked"){
					'<sec:authorize access="hasAnyRole(\'ROLE_BILL_PAYMENT_HISTORY\')">';
					result = result +'           <div class="tbldata-dotdot_80 invoiceId_Anchor"><a href="<c:url value="/viewInvoice?invoiceUUID='+report.invoiceUUID+'&invoiceType=Payables"/>" title = "'+report.invoiceId+'">'+report.invoiceId+'</a></div>   ';				
					'</sec:authorize>';
				}
				
				result = result +'         </td>  ';
				result = result +'         <td>   ';
				result = result +'           <div class="tbldata-dotdot-vsm" title="'+report.clientName+'">'+report.clientName+'</div>';
				result = result +'         </td> ';
				result = result +'         <td>';
				if(report.clientContactName !='' && report.clientContactName != null && report.clientContactName != 'undefined'){
					result = result +'<div class="tbldata-dotdot-vsm" title="'+report.clientContactName+'">'+report.clientContactName+'</div>  ';	
				}else{
					result = result +'<div class="tbldata-dotdot-vsm" title="">--</div>  ';
				}
				result = result +'         </td>';
				result = result +'         <td> ';
					result = result +'<div class="tblminwidth-100">'+projectName+'</div>';
				result = result +'         </td>';
				result = result +'         <td> ';
				if(invoiceDateStr !=null && invoiceDateStr !='' && invoiceDateStr !='undefined'){
				result = result +'           <div class="tblminwidth-100">'+invoiceDateStr+'</div>';
				}else{
					result = result +'<div class="tblminwidth-100">--</div>';
				}
				result = result +'         </td>';
				result = result +'         <td> ';
				result = result +'           <div class="tbldata-dotdot_80" title="'+report.total+'">'+report.total+'</div>';
				result = result +'         </td>';
				result = result +'         <td> ';
				result = result +'           <div class="tbldata-dotdot_80" title="'+report.dueAmount+'">'+report.dueAmount+'</div>';
				result = result +'         </td>';
				result = result +'         <td> ';
				if(dueDateStr !=null && dueDateStr !='' && dueDateStr !='undefined'){
				result = result +'           <div class="tblminwidth-100">'+dueDateStr+'</div>';
				}else{
					result = result +'<div class="tblminwidth-100">--</div>';
				}
				result = result +'         </td>';
				result = result +'         <td> ';
				result = result +'           <div class="tbldata-dotdot_100">'+invoiceStatus+'</div>';
				if(report.invoiceStatus == "Rejected"){
			 		result = result + '    <i class="glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status"><p>'+report.rejectReason+'</p></div></i>';
					}
				result = result +'         </td>';
				
				result = result +'         <td> ';
				result = result +'           <div class="tbldata-dotdot_100">'+report.paymentStatus+'</div> ';
				result = result +'         </td>';
				
				result = result +'         <td> ';
				result = result +'     <div class="tblminwidth-90">';
				 result = result +'  <div class="pull-right positionRelative menuWidget" >';
				if(report.invoiceStatus == "SENT" || report.invoiceStatus == "Received" || report.invoiceStatus == "Accepted"){
					 result = result +' <i class="fa fa-ellipsis-v" onClick="showPopUp('+report.id+',\''+report.invoiceStatus+'\',\''+report.invoiceParentId+'\',\''+report.total+'\');" ></i>  ';
						result = result +'  <div id="popupDiv'+report.id+'"></div>';
				} 
				result = result +'  </div> ';  
				if(report.invoiceType=="Payables" && report.invoiceParentId != null && report.invoiceParentId != '' && report.invoiceParentId != "undefined"){
					if(report.paymentStatus == "Paid" || report.invoiceStatus == "DRAFT" || report.invoiceStatus == "Rejected"){
						if(report.paymentStatus == "Paid"){
							result = result +'           <span class="text-primary successBtn primary pull-left" title="Paid" >Paid</span> </td>          ';
						}else if(report.invoiceStatus == "DRAFT"){
							result = result +'           <span class="text-primary successBtn primary pull-left" title="Paid">Send</span><i class="fa fa-trash text-danger pull-right cursorPointer" title="Remove Bill" onClick="deleteBill('+report.id+');";></i></td>          ';
						}else if(report.invoiceStatus == "Rejected"){
							result = result +'           <span class="text-primary successBtn primary pull-left" title="Paid" >Send</span> </td>          ';
						}
							
					}else{
						if(report.invoiceStatus == "Accepted"){
							result = result +'           <span class="text-primary receiveBtn receive_Btn pull-left" onclick="receivePaymentPop1('+report.id+','+report.dueAmount+');" title="Pay">Pay</span> </td>';
						}else{
							if(report.invoiceStatus == "Rejected"){
								result = result +'           <span class="text-danger successBtn primary pull-left" >Rejected</span> </td>          ';	
							}else if(report.invoiceStatus == "Linked"){
								result = result +' <a class="text-primary successBtn primary pull-left cursorPointer" onClick="unLinkBill('+report.id+')">UnLink</a>  ';
							}else{
								result = result +'           <span class="clearfix" ><a onclick="acceptedInvoice('+report.id+');" title="Accept"><i class="fa fa-check text-success paddingRight"></i></a><a onclick="rejectInvoice('+report.id+');" title="Reject"><i class="paddingLeft fa fa-times text-danger"></i></a></span> </td>';
							}
							
						}
					}
					
				}else{
					if(report.paymentStatus == "Paid" || report.invoiceStatus == "DRAFT" || report.invoiceStatus == "Rejected"){
						if(report.paymentStatus == "Paid"){
							result = result +'           <span class="text-primary successBtn primary pull-left" title="Paid" >Paid</span> </td>          ';	
						}else if(report.invoiceStatus == "DRAFT"){
							result = result +'           <span class="text-primary successBtn primary pull-left" title="Paid">Send</span><i class="fa fa-trash text-danger pull-right cursorPointer" title="Remove Bill" onClick="deleteBill('+report.id+');";></i></td>          ';
						}else{
							result = result +'           <span class="text-primary successBtn primary pull-left" title="Paid" >Send</span> </td>          ';
						}
							
					}else{
						if(report.invoiceStatus == "Linked"){
							result = result +' <a class="text-primary successBtn primary pull-left" onClick="unLinkBill('+report.id+')">UnLink</a>  ';
						}else{
							result = result +'           <span class="text-primary receiveBtn receive_Btn pull-left" onclick="receivePaymentPop1('+report.id+','+report.dueAmount+');" title="Pay">Pay</span> </td>          ';
						}
					}
					
					
				}
				
				/* if(report.invoiceType=="Receivable"){
					if(report.paymentStatus == "Paid" || report.invoiceStatus == "DRAFT"){
						result = result +'           <span class="text-primary successBtn primary pull-left" >Receive</span> </td>          ';	
					}else{
						result = result +'           <span class="text-primary receiveBtn receive_Btn pull-left" onclick="receivePaymentPop1('+report.id+','+report.dueAmount+');">Receive</span> </td>          ';
					}
				} */
				 
				 result = result +'</div>';
				
				result = result +'       </tr>                                                                                                           ';

    		}
        	result = result +'     </tbody>                                                                                                          ';
			result = result +'   </table>                                                                                                            ';
			$("#invoiceList").empty();  
		    $("#invoiceList").append(result);
		    
		    $("#emptyPayableInvoiceId").val(json.length);
		    
		    $("#draftInvoiceCount").html(draftCount);
		    $("#unbilledAmoumt").html(draftTotalAmount.toFixed(2));
		    		    
		    $("#paidInvoiceCount").html(paidCount);
		    $("#paidInvoiceAmount").html(paidTotalAmount.toFixed(2));
		    
		    $("#dueAfterInvoiceCount").html(dueAfterInvoiceCount);
		    $("#dueAfterInvoiceAmount").html(dueAfterInvoiceAmount.toFixed(2));
		    
		    $("#dueBeforeInvoiceCount").html(dueBeforeInvoiceCount);
		    $("#dueBeforeInvoiceAmount").html(dueBeforeInvoiceAmount.toFixed(2));
		    
		    invoicePagination()
        },
        
    });
	
	
}
</script>
<script>
function invoicePagination(){ 
	$.fn.dataTable.moment( 'DD-MM-YYYY' );
    $('#invoiceListPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"aaSorting": [[3,'asc']],
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $('#invoiceListPagination').wrap("<div class='overflow_Auto'></div>");
}
</script>
<script>
function showPopUp(id,invoiceStatus,invoiceParentId,totalAmount){
		 removePopup();
	     result = "";
	     if(invoiceParentId == 'undefined'){
	    	 result = result +'    <ul class="list-inline list-unstyled menupop menupop_2"> ';
	     }else{
	    	 result = result +'    <ul class="list-inline list-unstyled menupop menupop_3"> ';
	     }
		 result = result+' <i class="fa fa-close close-div closediv-small" onClick="removePopup();"></i>';
		 result = result +'    <input type="hidden" id="invoicePopupId" value="'+id+'"> ';
		 if(invoiceParentId == 'undefined'){
			 '<sec:authorize access="hasAnyRole(\'ROLE_SEND_BILL_REMINDER\')">';
			 result = result +'<li onclick="sendReminder('+id+');"><i class="fa fa-bullhorn paddingRight" title="Send reminder"></i>Send reminder</li> ';
			 '</sec:authorize>'
		 }
		 '<sec:authorize access="hasAnyRole(\'ROLE_PRINT_BILL\')">';
	     result = result +'      <li onclick="getPrint('+id+');"><i class="fa fa-print paddingLeft paddingRight" title="Print"></i>Print</li>';
	     '</sec:authorize>'
	     
	     '<sec:authorize access="hasAnyRole(\'ROLE_DOWNLOAD_BILL\')">';
	     result = result +'      <li onclick="downloadInvoice('+id+');"><i class="fa fa-download paddingLeft paddingRight" title="Download"></i>Download</li>';
	     '</sec:authorize>'
	     result = result +'      <li onclick="linkPayableInvoice('+id+',\''+totalAmount+'\');"><i class="fa fa-download paddingLeft paddingRight" title="Link Bill"></i>Link Bill</li>';
	     result = result +'    </ul>';
    	 $("#popupDiv"+id+"").append(result);
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
function sendReminder(id){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
    	 type: "POST",
	     dataType : 'json',
	    	 url: sourceleadUrl+"/sendReminder?${_csrf.parameterName}=${_csrf.token}&invoiceId="+id, 
             success: function(result) {
            	 removePopup();
        },
    });  
}

</script>

<script type="text/javascript">
function getPrint(id) {
	var sourceleadUrl ='${sourceleadUrl}';
	window.open(sourceleadUrl+'/getPrint/'+id+'','_newtab');
}</script>

<script>
function downloadInvoice(id){
	var sourceleadUrl ='${sourceleadUrl}';
	window.location.href = sourceleadUrl+"/downloadInvoice?${_csrf.parameterName}=${_csrf.token}&invoiceId="+id;
}
</script>

<script>
function acceptedInvoice(id){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
    	 type: "POST",
	     dataType : 'json',
	    	 url: sourceleadUrl+"/acceptedInvoice?${_csrf.parameterName}=${_csrf.token}&invoiceId="+id, 
             success: function(result) {
        	location.replace(sourceleadUrl+"/payablesList");
     		
        },
    });  
}

</script>

<div id="popUp"></div>
<script>
function rejectInvoice(id){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	//result = result +"		<p>Do you want to delete this vendor contact?</p>";
	result = result +"		<textarea id='reasonForDelete' class='form-control' field-name='Reject Reason'  data-validation='required validate_Space validate_Description validate_length length2-250' name='reasonForSpoc' value='' placeholder='Terminate Reason' rows='2'></textarea>";
	result = result + ' <div id="reasonForRejectDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onclick='saveReasonforPayable("+id+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTermination();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script>
function saveReasonforPayable(id){
	var sourceleadUrl = '${sourceleadUrl}';
	var reason = document.getElementById("reasonForDelete").value;
	if(reason == null || reason == '' || reason == 'undefined'){
	      
		  $("#reasonForRejectDiv").find('p.jquery_form_error_message').remove();
	  	  $("#reasonForRejectDiv").append("<p class='jquery_form_error_message'>Reason Is Required</p>");
	  	  
		}else{
		  $("#reasonForRejectDiv").find('p.jquery_form_error_message').remove();
		$.ajax({
		   	type: "POST",
		   	url: sourceleadUrl+"/rejectInvoice?${_csrf.parameterName}=${_csrf.token}&invoiceId="+id+"&reason="+reason,	    	   
		    success: function(data) {
		    	$("#popUp").empty();
	 			 $(".project_inn_mask").fadeOut(200);
		    	location.replace(sourceleadUrl+"/payablesList");
		    }
	   });
	}
	  
}
</script>

<!-- <script>
function rejectInvoice(id){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
    	 type: "POST",
	     dataType : 'json',
	    	 url: sourceleadUrl+"/rejectInvoice?${_csrf.parameterName}=${_csrf.token}&invoiceId="+id, 
             success: function(result) {
        	location.replace(sourceleadUrl+"/payablesList");
     		
        },
    }); 
}

</script> -->
<script>
function cancelbtn(){
	  $("#recvPymntpop").fadeOut(200);
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
            var selectVal = $("#dayWiseTypeId").val();
            if (selectVal == "custom") {
                $(".custom_search").show();
                customDate();
            }else {
                $(".custom_search").hide();
            }
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
	
	$('#billingStartDate').datepicker({
		changeMonth: true,
		changeYear: true, 
		dateFormat : 'dd-mm-yy',
		onSelect : function(selected) {
			var dt = new Date(selected);
			var dt = toDate("#billingStartDate");
			dt.setDate(dt.getDate() + 1);
			$("#billingEndDate").datepicker("option", "minDate", dt);
			removeStartdateval();
		}
	
	});
	
	$('#billingEndDate').datepicker({
		changeMonth: true,
		changeYear: true, 
		dateFormat : 'dd-mm-yy',
		beforeShow: function(){
	           this.value='';
	     },
		onSelect : function(selected) {
			var dt = new Date(selected);
			var dt = toDate("#billingEndDate");
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
function searchResults(){
	$("#errStartDate").find('p.jquery_form_error_message').remove();
	$("#errEndDate").find('p.jquery_form_error_message').remove();
	var startDate =  $('#billingStartDate').val();
	var endDate =  $('#billingEndDate').val();
	if(startDate != null && startDate != '' && startDate != 'undefined' && endDate != null && endDate != '' && endDate != 'undefined'){
		getPayableInvoiceList();
	}else{
		if(startDate != null && startDate != '' && startDate != 'undefined'){
			$("input#billingEndDate") .after( "<p class='jquery_form_error_message'>End Date is Required</span>");
		}else if(endDate != null && endDate != '' && endDate != 'undefined'){
			$("input#billingStartDate") .after( "<p class='jquery_form_error_message'>Start Date is Required</span>");
		}else{
			$("input#billingStartDate") .after( "<p class='jquery_form_error_message'>Start Date is Required</span>");
			$("input#billingEndDate") .after( "<p class='jquery_form_error_message'>End Date is Required</span>");
		}
	} 
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
	
	 var sourceleadUrl ='${sourceleadUrl}';
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
		
		var emptyExport = $('#emptyPayableInvoiceId').val();
		var durationType = $('#dayWiseTypeId').val();
		
		var exportStartDate = $("#billingStartDate").val();
		var exportEndDate = $("#billingEndDate").val();
		
		if(emptyExport > 0){
		window.location.href = sourceleadUrl+"/getBillingExportList?${_csrf.parameterName}=${_csrf.token}&invoiceStatus="+invoiceStatus+"&paymentStatus="+paymentStatus+"&durationType="+durationType+"&exportStartDate="+exportStartDate+"&exportEndDate="+exportEndDate+"&exportType=pdf&invoiceType=Payables";
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
	
	 var sourceleadUrl ='${sourceleadUrl}';
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
		
		var emptyExport = $('#emptyPayableInvoiceId').val();
		var durationType = $('#dayWiseTypeId').val();
		
		var exportStartDate = $("#billingStartDate").val();
		var exportEndDate = $("#billingEndDate").val();
		
		if(emptyExport > 0){
		window.location.href = sourceleadUrl+"/getBillingExportList?${_csrf.parameterName}=${_csrf.token}&invoiceStatus="+invoiceStatus+"&paymentStatus="+paymentStatus+"&durationType="+durationType+"&exportStartDate="+exportStartDate+"&exportEndDate="+exportEndDate+"&exportType=xls&invoiceType=Payables";
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
function linkPayableInvoice(invoiceId,totalAmount){
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
    	 type: "POST",
	     dataType : 'json',
	     url : sourceleadUrl+"/getClientBasedInvoiceList?${_csrf.parameterName}=${_csrf.token}&invoiceId="+invoiceId+"&invoiceType=payables",
			success: function(data) {
			var result = "";
			result = result +'  <div class="modal-dialog modal-lg">                                                                                         ';
			result = result +'    <div class="modal-content">                                                                                               ';
			result = result +' <input type="hidden" value='+invoiceId+' name="masterInvoiceId" id="masterInvoiceId">   ';
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
			if(data != null && data != 'undefined' && data != ""){ 
			var json = JSON.parse(data);
			for (var i = 0; i < json.length; i++) { 
			var opt = json[i]; 
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
			result = result +'            </td>                                                                                                             ';
			result = result +'            <td>                                                                                                              ';
			result = result +'              <div class="tbldata-dotdot_100">'+opt.invoiceStatus+'</div>                                                                    ';
			result = result +'            </td>                                                                                                             ';
			result = result +'          </tr>                                                                                                               ';
			}
			}else{
				result = result +'          <tr>';
				result = result +'                <td valign="top" colspan="10" class="dataTables_empty"></td>';
				result = result +'          </tr>';
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
	 $.fn.dataTable.moment( 'DD-MM-YYYY' );
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
	var masterInvoiceId = $('#masterInvoiceId').val();
	var currencyCode = '${SourceLeadWeb_currencyCode}';
	var linkRadio = $('input[name=linkedInvoice]');
	var checkVal = linkRadio.filter(':checked').val();
	var sourceleadUrl ='${sourceleadUrl}';
	var total = $('#linkedPayableTotalId'+checkVal+'').text();
	var calculatedAmount = 	parentTotalAmount - total;
	if(checkVal != '' && checkVal != 'undefined' && checkVal != null){
	showDiffAmountPopup(calculatedAmount,currencyCode,checkVal,masterInvoiceId);
}else{
	$('span.jquery_form_error_message').show();
	} 
}
</script>


<script>
function showDiffAmountPopup(calculatedAmount,currencyCode,checkVal,masterInvoiceId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='payable_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Variation Amount  = "+calculatedAmount+" "+currencyCode+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input type='button' value='Proceed' onclick='proceedToMergeBill("+checkVal+","+masterInvoiceId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#payable_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
	
}

</script>

<script>
function proceedToMergeBill(checkVal,masterInvoiceId){
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	   	 type: "POST",
		     dataType : 'json',
		    	 url: sourceleadUrl+"/linkInvoice?${_csrf.parameterName}=${_csrf.token}&checkVal="+checkVal+"&invoiceType=payables&masterInvoiceId="+masterInvoiceId, 
	            success: function(data) {
	            	$("#popUp").empty();
	            	$(".project_inn_mask").fadeOut(200);
	            	location.replace(sourceleadUrl+"/payablesList");	
	       },
	   });  
}
</script>

<script type="text/javascript">
function cancel(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
	var sourceleadUrl ='${sourceleadUrl}';
	location.replace(sourceleadUrl+"/payablesList");	
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
	 var sourceleadUrl ='${sourceleadUrl}';
	 $("#invoiceLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
    	 type: "POST",
	     dataType : 'json',
	    	 url: sourceleadUrl+"/generateAutomaticInvoice?${_csrf.parameterName}=${_csrf.token}&invoiceType=payables&contractType=vendorSow", 
             success: function(data) {
            	 $("#invoiceLoading").removeAttr("style");
            	    $("#selector1").removeAttr("style");
	    			$("#selector1").css("display", "block");
	    			$("#msgDiv").html(data+" Bill(s) Generated Successfully.");
					msgFadein();
            	 location.replace(sourceleadUrl+"/payablesList");	
        },
    });  
}
</script>

<script>
function unLinkBill(invoiceId){
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	   	 type: "POST",
		     dataType : 'json',
		    	 url: sourceleadUrl+"/unLinkInvoice?${_csrf.parameterName}=${_csrf.token}&invoiceId="+invoiceId, 
	            success: function(data) {
	            	location.replace(sourceleadUrl+"/payablesList");	
	       },
	   });  
}
</script>

<script>
function deleteBill(billId){ 
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";

	result = result +"		<p>Are you sure you want to delete ?</p>";
	result = result +"		<textarea  id='reasonForBill' name='reasonForBill'  placeholder='Comments here' ></textarea>";
	result = result + ' <div id="reasonForBillDiv"/>';
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Delete" onclick="saveBillRejectedReason('+billId+');"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function cancelTermination(){
	$("#popUp").empty();
	 $(".project_inn_mask").fadeOut(200);
}

function saveBillRejectedReason(billId){
	var sourceleadUrl = '${sourceleadUrl}';
	var reason = $("#reasonForBill").val();
	if(reason != null && reason != ''){
		 $("#reasonForBillDiv").empty();
		if(reason.length > 250){
		$("#reasonForBillDiv").empty().append("<font color='red' style='padding: 5px 10px; '>The Entered values should not be more than 250 characters</font>") ; 
		}else{
			 $.ajax({
				   	type: "POST",
				    url: sourceleadUrl+"/deleteInvoice?${_csrf.parameterName}=${_csrf.token}&invoiceType=payables&invoiceId="+billId+"&reason="+reason,
		   		    success: function(data) {
		   		    	$("#popUp").empty();
		   			 	$(".project_inn_mask").fadeOut(200);
		   			 	location.replace(sourceleadUrl+"/payablesList");	
		   		    }
			     });
		}
	}
	else{
			$("#reasonForBillDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
	}
	
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
<jsp:include page="../masterFooter.jsp" />