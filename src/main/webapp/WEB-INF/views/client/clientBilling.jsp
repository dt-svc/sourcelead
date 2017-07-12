<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="resources/css/style.css" />
<style type="text/css">

.timesheet_head ul.nav-tabs, .timesheet_head ul.invoiceHdr  {
padding: 5px; float: none; clear: both;
}
.billing_dashboard {
padding: 0;
}
.billing_dashboard h4 {
text-align: left; padding-left: 10px;
}
.clients_side_wrap select {
min-height: 35px; padding: 1px 25px 1px 15px;
}
.billingArea {
padding: 10px;
}
.side_mask {
background-color: rgba(0,0,0,0.3);
}
.pop_mask {
z-index: 99999;
}
.addcandidate-popup {
z-index: 9999999;
}
.exlpdf_btns table tr td div i {
    color: #333;
}
</style>

<!-- these files for export -->
<link href="https://cdn.datatables.net/buttons/1.2.4/css/buttons.dataTables.min.css" rel="stylesheet">
<!-- <script src="//code.jquery.com/jquery-1.12.4.js" type="text/javascript" charset="utf-8"></script> -->
<script src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js" type="text/javascript" charset="utf-8"></script>
<script src="https://cdn.datatables.net/buttons/1.2.4/js/dataTables.buttons.min.js" type="text/javascript" charset="utf-8"></script>
<script src="//cdn.datatables.net/buttons/1.2.4/js/buttons.flash.min.js" type="text/javascript" charset="utf-8"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jszip/2.5.0/jszip.min.js" type="text/javascript" charset="utf-8"></script>
<script src="https://cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/pdfmake.min.js" type="text/javascript" charset="utf-8"></script>
<script src="https://cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/vfs_fonts.js" type="text/javascript" charset="utf-8"></script>
<script src="//cdn.datatables.net/buttons/1.2.4/js/buttons.html5.min.js" type="text/javascript" charset="utf-8"></script>
<script src="//cdn.datatables.net/buttons/1.2.4/js/buttons.print.min.js" type="text/javascript" charset="utf-8"></script>
<!-- ends files of export -->



<input type="hidden" id="emptyInvoiceId" name="emptyInvoiceId"/>
<script>
function getClientInvoiceList(orgRelationId,clientLocationId){
var result = "";
result = result  +'<div class="">';
result = result  +'    <div class="billingArea"> ';  
 result = result + '			    <div class="clearfix">																																				                   ';
 result = result + '			      <div class="billing_dashboard">                                                                                                                                                                             ';
 result = result + '			        <div class="tabbable positionRelative">                                                                                                                                                                    ';
 result = result + '			          <div class="onboardcreateButtons">                                                                                                                                                                       ';
 result = result + '			            <div class="pull-right select-field ml">                                                                                                                                                               ';
 result = result + '			              <select class="form-control input-sm" id="dayWiseTypeId" onchange="getInvoiceListBasedonClient('+orgRelationId+','+clientLocationId+');customInvoiceList('+orgRelationId+','+clientLocationId+');">                                                                                           ';
 result = result + '			                <option value="month">Last 30 days</option>                                                                                                                                                        ';
 result = result + '			                <option value="twomonths">Last 60 Days</option>                                                                                                                                                    ';
 result = result + '			                <option value="threemonths">Last 90 days</option>                                                                                                                                                  ';
 result = result + '			                <option value="sixmonths">Last 180 days</option>                                                                                                                                                   ';
 result = result + '			                <option value="oneyear">Last 365 days</option>                                                                                                                                                     ';
 result = result + '			                <option value="custom">Custom</option>                                                                                                                                                             ';
 result = result + '			              </select>                                                                                                                                                                                            ';
 result = result + '			                                                                                                                                                                                                                   ';
 result = result + '			              <div class="custom_search">                                                                                                                                                                          ';
 result = result + '			              <div id="customDateDivId"></div>                                                                                                                                                                     ';
 result = result + '			            </div>                                                                                                                                                                                                 ';
 result = result + '			          </div>                                                                                                                                                                                                   ';
 result = result + '			            </div>                                                                                                                                                                                                 ';
 result = result + '			          </div>                                                                                                                                                                                                   ';
 result = result + '			                                                                                                                                                                                                                   ';
 result = result + '			         <ul class="nav nav-tabs" id="tab_bar">                                                                                                                                                                    ';
 result = result + '			                                                                                                                                                                                                                   ';
 result = result + '			               <li class="active">                                                                                                                                                                                 ';
 result = result + '			                        <h4>Unbilled</h4>                                                                                                                                                                          ';
 result = result + '			                        <a  data-toggle="tab" class="onboardCount billbg_orange" title="Draft Invoices">                                                                                                           ';
 result = result + '			                            <div class="pull-left">                                                                                                                                                                ';
 result = result + '			                            <strong>${SourceLeadWeb_currencyCode}</strong>                                                                                                                                         ';
 result = result + '			                            </div>                                                                                                                                                                                 ';
 result = result + '			                            <div class="pull-right ml10">                                                                                                                                                          ';
 result = result + '			                            <div class="count" id="unbilledAmoumt"></div>                                                                                                                                          ';
 result = result + '			                                <h5>Draft Invoices <span class="paddingLeft" id="draftInvoiceCount"></span></h5>                                                                                                   ';
 result = result + '			                                </div>                                                                                                                                                                             ';
 result = result + '			                        </a>                                                                                                                                                                                       ';
 result = result + '			                    </li>                                                                                                                                                                                          ';
 result = result + '			                    <li>                                                                                                                                                                                           ';
 result = result + '			                        <h4>Un Paid</h4>                                                                                                                                                                           ';
 result = result + '			                        <a  data-toggle="tab" title="UnPaid Overdue Invoices" class="billingbg_red">                                                                                                               ';
 result = result + '			                        <div class="pull-left">                                                                                                                                                                    ';
 result = result + '			                         <strong>${SourceLeadWeb_currencyCode}</strong>                                                                                                                                            ';
 result = result + '			                            </div>                                                                                                                                                                                 ';
 result = result + '			                            <div class="pull-right ml10">                                                                                                                                                          ';
 result = result + '			                            <div class="count" id="dueAfterInvoiceAmount"></div>                                                                                                                                   ';
 result = result + '			                        <h5>Overdue Invoices<span class="paddingLeft" id="dueAfterInvoiceCount"></span></h5>                                                                                                       ';
 result = result + '			                            </div>                                                                                                                                                                                 ';
 result = result + '			                                                                                                                                                                                                                   ';
 result = result + '			                        </a>                                                                                                                                                                                       ';
 result = result + '			                    </li>                                                                                                                                                                                          ';
 result = result + '			                    <li>                                                                                                                                                                                           ';
 result = result + '			                        <h4>&nbsp;</h4>                                                                                                                                                                            ';
 result = result + '			                        <a  data-toggle="tab" title="UnPaid Open Invoices" class="billingbgRed">                                                                                                                   ';
 result = result + '			                        <div class="pull-left">                                                                                                                                                                    ';
 result = result + '			                            <strong>${SourceLeadWeb_currencyCode}</strong>                                                                                                                                         ';
 result = result + '			                            </div>                                                                                                                                                                                 ';
 result = result + '			                            <div class="pull-right ml10">                                                                                                                                                          ';
 result = result + '			                            <div class="count" id="dueBeforeInvoiceAmount"></div>                                                                                                                                  ';
 result = result + '			                        <h5>Open Invoices<span class="paddingLeft" id="dueBeforeInvoiceCount"></span></h5>                                                                                                         ';
 result = result + '			                        </div>                                                                                                                                                                                     ';
 result = result + '			                        </a>                                                                                                                                                                                       ';
 result = result + '			                    </li>                                                                                                                                                                                          ';
 result = result + '			                    <li class="billingbg_greens">                                                                                                                                                                  ';
 result = result + '			                        <h4>Paid</h4>                                                                                                                                                                              ';
 result = result + '			                        <a  data-toggle="tab" title="Paid Invoices" class="billbg_green">                                                                                                                          ';
 result = result + '			                        <div class="pull-left">                                                                                                                                                                    ';
 result = result + '			                           <strong>${SourceLeadWeb_currencyCode}</strong>                                                                                                                                          ';
 result = result + '			                            </div>                                                                                                                                                                                 ';
 result = result + '			                            <div class="pull-right ml10">                                                                                                                                                          ';
 result = result + '			                            <div class="count" id="paidInvoiceAmount"></div>                                                                                                                                       ';
 result = result + '			                        <h5>Paid Invoices<span class="paddingLeft" id="paidInvoiceCount"></span></h5>                                                                                                              ';
 result = result + '			                            </div>                                                                                                                                                                                 ';
 result = result + '			                        </a>                                                                                                                                                                                       ';
 result = result + '			                    </li>                                                                                                                                                                                          ';
 result = result + '			                                                                                                                                                                                                                   ';
 result = result + '			                                                                                                                                                                                                                   ';
 result = result + '			            <div class="pull-right mt40 select-field hidden">                                                                                                                                                      ';
 result = result + '			              <select class="form-control">                                                                                                                                                                        ';
 result = result + '			                <option class="disabled" selected="">--Select option--</option>                                                                                                                                    ';
 result = result + '			                <option>1 Month</option>                                                                                                                                                                           ';
 result = result + '			                <option>2 Months</option>                                                                                                                                                                          ';
 result = result + '			                <option>3 Months</option>                                                                                                                                                                          ';
 result = result + '			                <option>6 months</option>                                                                                                                                                                          ';
 result = result + '			                <option>1 Year</option>                                                                                                                                                                            ';
 result = result + '			                <option>Custom months</option>                                                                                                                                                                     ';
 result = result + '			              </select>                                                                                                                                                                                            ';
 result = result + '			            </div>                                                                                                                                                                                                 ';
 result = result + '			          </ul>                                                                                                                                                                                                    ';
 result = result + '			          <div class="tab-content">                                                                                                                                                                                ';
 result = result + '			                                                                                                                                                                                                                   ';
 result = result + '			            <!-- Employee Details -->                                                                                                                                                                              ';
 result = result + '			            <div class="tab-pane active" id="unbilled">                                                                                                                                                            ';
 result = result + '			              <div class="timesheet_out">                                                                                                                                                                       ';
 result = result + '			                <div class="clearfix">                                                                                                                                                                             ';
 result = result + '			                  <div class="timesheet_head timesheetHead">                                                                                                                                                                     ';
 result = result + '			                    <ul>                                                                                                                                                                                           ';
 result = result + '			                                                                                                                                                                                                                   ';
 result = result + '			                    	                                                                                                                                                                                           ';
 result = result + '			                      <li class="dropdown"><div class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-filter"></i><span>Status</span><i class="glyphicon glyphicon-play"></i></div>';
 result = result + '			                        <div class="dropdown-menu bootstrap_dropdownMenu">                                                                                                                                              ';
 result = result + '			                          <ul>                                                                                                                                                                                     ';
 result = result + '			                            <li>                                                                                                                                                                                   ';
 result = result + '			                              <input type="checkbox" value="DRAFT" name="invoiceStatus" id="" onclick="getInvoiceListBasedonClient('+orgRelationId+','+clientLocationId+');" >                                                                                        ';
 result = result + '			                              <label for="onboard1">Draft</label>                                                                                                                                                  ';
 result = result + '			                            </li>                                                                                                                                                                                  ';
 result = result + '			                            <li>                                                                                                                                                                                   ';
 result = result + '			                              <input type="checkbox" id="" value="SENT" name="invoiceStatus" onclick="getInvoiceListBasedonClient('+orgRelationId+','+clientLocationId+');">                                                                                          ';
 result = result + '			                              <label for="onboard2">Sent</label>                                                                                                                                                   ';
 result = result + '			                            </li>                                                                                                                                                                                  ';
 result = result + '			                            <li>                                                                                                                                                                                   ';
 result = result + '			                              <input type="checkbox" value="RECEIVED" name="invoiceStatus" id="" onclick="getInvoiceListBasedonClient('+orgRelationId+','+clientLocationId+');" >                                                                                     ';
 result = result + '			                              <label for="onboard1">Received</label>                                                                                                                                               ';
 result = result + '			                            </li>                                                                                                                                                                                  ';
 result = result + '			                            <li>                                                                                                                                                                                   ';
 result = result + '			                              <input type="checkbox" id="" value="ACCEPTED" name="invoiceStatus" onclick="getInvoiceListBasedonClient('+orgRelationId+','+clientLocationId+');">                                                                                      ';
 result = result + '			                              <label for="onboard2">Accepted</label>                                                                                                                                               ';
 result = result + '			                            </li>                                                                                                                                                                                  ';
 result = result + '			                            <li>                                                                                                                                                                                   ';
 result = result + '			                              <input type="checkbox" id="" value="REJECTED" name="invoiceStatus" onclick="getInvoiceListBasedonClient('+orgRelationId+','+clientLocationId+');">                                                                                      ';
 result = result + '			                              <label for="onboard2">Rejected</label>                                                                                                                                               ';
 result = result + '			                            </li>                                                                                                                                                                                  ';
 result = result + '			                            <li>                                                                                                                                                                                   ';
 result = result + '			                              <input type="checkbox" id="" value="LINKED" name="invoiceStatus" onclick="getInvoiceListBasedonClient('+orgRelationId+','+clientLocationId+');">                                                                                        ';
 result = result + '			                              <label for="onboard2">Linked</label>                                                                                                                                                 ';
 result = result + '			                            </li>                                                                                                                                                                                  ';
 result = result + '			                          </ul>                                                                                                                                                                                    ';
 result = result + '			                        </div>                                                                                                                                                                                     ';
 result = result + '			                      </li>                                                                                                                                                                                        ';
 result = result + '			                                                                                                                                                                                                                   ';
 result = result + '			                      <li class="dropdown"><div class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-filter"></i><span>Payment</span><i class="glyphicon glyphicon-play"></i></div>';
 result = result + '			                        <div class="dropdown-menu bootstrap_dropdownMenu">                                                                                                                                              ';
 result = result + '			                          <ul>                                                                                                                                                                                     ';
 result = result + '			                            <li>                                                                                                                                                                                   ';
 result = result + '			                              <input type="checkbox" id="" value="Paid" name="paymentStatus" onclick="getInvoiceListBasedonClient('+orgRelationId+','+clientLocationId+');">                                                                                          ';
 result = result + '			                              <label for="onboard3">Paid</label>                                                                                                                                                   ';
 result = result + '			                            </li>                                                                                                                                                                                  ';
 result = result + '			                            <li>                                                                                                                                                                                   ';
 result = result + '			                              <input type="checkbox" id="" value="Partial Paid" name="paymentStatus" onclick="getInvoiceListBasedonClient('+orgRelationId+','+clientLocationId+');">                                                                                  ';
 result = result + '			                              <label for="onboard2">Partially Paid</label>                                                                                                                                         ';
 result = result + '			                            </li>                                                                                                                                                                                  ';
 result = result + '			                            <li>                                                                                                                                                                                   ';
 result = result + '			                              <input type="checkbox" id="" value="OverDue" name="paymentStatus" onclick="getInvoiceListBasedonClient('+orgRelationId+','+clientLocationId+');">                                                                                       ';
 result = result + '			                              <label for="onboard4">OverDue</label>                                                                                                                                                ';
 result = result + '			                            </li>                                                                                                                                                                                  ';
 result = result + '			                            <li>                                                                                                                                                                                   ';
 result = result + '			                              <input type="checkbox" value="Unpaid" name="paymentStatus" id="" onclick="getInvoiceListBasedonClient('+orgRelationId+','+clientLocationId+');">                                                                                        ';
 result = result + '			                              <label for="onboard1">Unpaid</label>                                                                                                                                                 ';
 result = result + '			                            </li>                                                                                                                                                                                  ';
 result = result + '			                          </ul>                                                                                                                                                                                    ';
 result = result + '			                        </div>                                                                                                                                                                                     ';
 result = result + '			                      </li>                                                                                                                                                                                        ';
 													'<sec:authorize access="hasAnyRole(\'ROLE_ADD_INVOICE\')"> ';
 result = result + '			                      <li><a href="javascript:void(0)" class="anchorTag" title="Create invoice"  onclick="createBill('+orgRelationId+');"> <i class="fa fa-plus paddingRight"></i>Create Invoice</a>&nbsp;&nbsp;&nbsp;|</li>        ';
 													'</sec:authorize>';
 result = result + '			                      <li><a href="javascript:void(0)" class="" title="Generate Automatic Invoice" onclick="generateAutomaticInvoice();"> <i class="fa fa-plus paddingRight"></i>Generate Automatic Invoice</a>&nbsp;&nbsp;&nbsp;|</li>';
 result = result + '			                      <div class="clearfix"></div>                                                                                                                                                                 ';
 result = result + '			                    </ul>                                                                                                                                                                                          ';
 result = result + '			                    <div class="clearfix"></div>                                                                                                                                                                   ';
 result = result + '			                                                                                                                                                                                                                   ';
 result = result + '			                  </div>                                                                                                                                                                                           ';
 result = result + '			                                                                                                                                                                                                                   ';
 result = result + '			                                                                                                                                                                                                                   ';
 result = result + '			                  <div class="candidate_wrap_table">                                                                                                                                                               ';
 result = result + '				                  <div id="invoiceList"></div>                                                                                                                                                                 ';
 result = result + '			                  </div>                                                                                                                                                                                           ';
 result = result + '			                </div>                                                                                                                                                                                             ';
 result = result + '			              </div>                                                                                                                                                                                               ';
 result = result + '			            </div>                                                                                                                                                                                                 ';
 result = result + '			          </div>                                                                                                                                                                                                   ';
 result = result + '			          <!-- STYLED IN FORMS.CSS -->                                                                                                                                                                             ';
 result = result + '			        </div>                                                                                                                                                                                                     ';
 result = result + '			      </div>                   																																								                        ';                                                                                                                                                            
 result = result  +'  <div class="clearfix"></div> ';
 result = result + '</ul>';
 result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table exlpdf_btns">';
 result  = result + '   <div id="clientInvoiceList" ></div>';
 result  = result + '   </div>';
 result = result+'	    </div>';
 result = result+'	    </div>';

    $("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty();  
    $("#tab-4").empty();
    $("#tab-6").empty();
    $("#tab-6").append(result);  
	
    getInvoiceListBasedonClient(orgRelationId,clientLocationId);
    
}
</script>

<script type="text/javascript">
	function getInvoiceListBasedonClient(orgRelationId,clientLocationId) {
		var sourceleadUrl = '${sourceleadUrl}';
		
		var invoice = [];
		var payment = [];
		var dayWisetype = $("#dayWiseTypeId").val();
		
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
	    	type: "POST",
	    	url : sourceleadUrl+"/getInvoiceListBasedOnOrgrelationId?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationId+"&clientLocationId="+clientLocationId+"&dayWisetype="+dayWisetype+"&startDate="+startDate+"&endDate="+endDate+"&invoiceStatus="+invoice+"&paymentStatus="+payment+"&invoiceType=receivable", 
	        success: function(data) {
	        
	        var draftCount = 0;
        	var draftTotalAmount = 0;
        	
        	var paidCount = 0;
        	var paidTotalAmount = 0;
        	
        	var dueAfterInvoiceCount = 0;
        	var dueAfterInvoiceAmount = 0;
        	
        	var dueBeforeInvoiceCount = 0;
        	var dueBeforeInvoiceAmount = 0;
	        
			var result = "";
	      
			result = result +'	<table id="invoiceListPagination">                                                                                                         ';
			result = result +'     <thead>                            ';
			result = result +'       <tr>                             ';
			result = result +'         <th>Invoice ID</th>                    ';
			result = result +'         <th>Client Contact</th>               ';
			result = result +'         <th>Invoice Date</th>               ';
			result = result +'         <th>Total</th>                 ';
			result = result +'         <th>Due</th>                   ';
			result = result +'         <th>Due date</th>              ';
			result = result +'         <th>Status</th>                ';
			result = result +'         <th>Payment Status</th>                ';
			result = result +'         <th>Action</th>                ';
			result = result +'       </tr>                            ';
			result = result +'     </thead>                           ';
			result = result +'     <tbody>                            ';
			if(data != null && data != ''){
				var json = JSON.parse(data);
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
	        		
	        		result = result +'       <tr> ';
					result = result +'         <td>';
					if(report.invoiceStatus == "DRAFT" || report.invoiceStatus == "Rejected"){
						'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_INVOICE\')">';
						result = result +'           <div class="tbldata-dotdot_80 invoiceId_Anchor"><a href="'+sourceleadUrl+'/getReceivableInvoiceByUUID?invoiceUUID='+report.invoiceUUID+'&projectId='+report.projectId+'">'+report.invoiceId+'</a></div>   ';
						'</sec:authorize>' ;
					}
					if(report.invoiceStatus == "SENT" || report.invoiceStatus == "Received" || report.invoiceStatus == "Accepted" || report.invoiceStatus == "Linked" ){
						'<sec:authorize access="hasAnyRole(\'ROLE_INVOICE_PAYMENT_HISTORY\')">';
						result = result +'           <div class="tbldata-dotdot_80 invoiceId_Anchor"><a href="<c:url value="/viewInvoice?invoiceUUID='+report.invoiceUUID+'&invoiceType=Receivable"/>">'+report.invoiceId+'</a></div>   ';				
						'</sec:authorize>' ;
					}
					result = result +'         </td>  ';
					result = result +'         <td>';
					if(report.clientContactName !='' && report.clientContactName != null && report.clientContactName != 'undefined'){
						result = result +'           <div class="tbldata-dotdot-vsm" title="'+report.clientContactName+'">'+report.clientContactName+'</div>  ';	
					}else{
						result = result +'<div class="tbldata-dotdot-vsm">--</div>  ';
					}
					result = result +'         </td>';
					result = result +'         <td> ';
					if(report.invoiceDateStr !='' && report.invoiceDateStr != null && report.invoiceDateStr != 'undefined'){
					result = result +'           <div class="tblminwidth-100">'+report.invoiceDateStr+'</div>';
					}else{
						result = result +'<div class="tblminwidth-100">--</div>  ';
					}
					result = result +'         </td>';
					result = result +'         <td> ';
					result = result +'           <div class="tbldata-dotdot_80" title="'+report.total+'">'+report.total+'</div>';
					result = result +'         </td>';
					result = result +'         <td> ';
					result = result +'           <div class="tbldata-dotdot_80" title="'+report.dueAmount+'">'+report.dueAmount+'</div>';
					result = result +'         </td>';
					result = result +'         <td> ';
					if(report.dueDateStr !='' && report.dueDateStr != null && report.dueDateStr != 'undefined'){
					result = result +'           <div class="tblminwidth-100">'+report.dueDateStr+'</div>';
					}else{
						result = result +'<div class="tblminwidth-100">--</div>  ';
					}
					result = result +'         </td>';
					result = result +'         <td> ';
					result = result +'           <div class="tbldata-dotdot-100">'+report.invoiceStatus+'</div>';
					result = result +'         </td>';
					
					result = result +'         <td> ';
					result = result +'           <div class="tbldata-dotdot-100">'+report.paymentStatus+'</div> ';
					result = result +'         </td>';
					result = result +'         <td> ';
					result = result +'     <div class="tblminwidth-75">';
					 result = result +'  <div class="pull-right positionRelative menuWidget" >';
					 if(report.invoiceStatus == "SENT" || report.invoiceStatus == "Received" || report.invoiceStatus == "Accepted" ){
						result = result +' <i class="fa fa-ellipsis-v" onClick="showPopUp('+report.id+',\''+report.invoiceStatus+'\',\''+report.invoiceParentId+'\',\''+report.total+'\');" ></i>  ';
						result = result +'  <div id="popupDiv'+report.id+'"></div>';
					} 
					result = result +'  </div> ';                                                                                                    
					if(report.invoiceType == "Receivable" && (report.invoiceParentId == null || report.invoiceParentId == '' || report.invoiceParentId == "undefined")){
						if(report.paymentStatus == "Paid" || report.invoiceStatus == "DRAFT" || report.invoiceStatus == "Rejected"){
							if(report.paymentStatus == "Paid"){
							result = result +'           <span class="text-primary successBtn primary pull-left" >Receive</span>   ';	
						}else{
							result = result +'           <span class="text-primary successBtn primary pull-left" >Send</span>   ';	
						}
						}else{
							result = result +'           <span class="text-primary receiveBtn pull-left" onclick="receivePaymentPop1('+report.id+','+report.dueAmount+');">Receive</span>';
						}
					}else{
						if(report.invoiceStatus == "Accepted"){
							if(report.paymentStatus == "Paid"){
								result = result +'           <span class="text-primary successBtn primary pull-left" >Receive</span>   ';	
							}else{
								result = result +'           <span class="text-primary receiveBtn pull-left" onclick="receivePaymentPop1('+report.id+','+report.dueAmount+');">Receive</span> </td>';
							}
							
						}else{
							if(report.invoiceStatus == "Rejected"){
								result = result +'           <span class="text-primary successBtn primary pull-left" >Send</span>           ';	
							}else{
								result = result +'           <span class="clearfix" ><a onclick="acceptedInvoice('+report.id+');" title="Accept"><i class="fa fa-check text-success paddingRight"></i></a><a onclick="rejectInvoice('+report.id+');" title="Reject"><i class="paddingLeft fa fa-times text-danger"></i></a></span>';
							}
							
						}
					}
	 
			        result = result +'</div>';
			        result = result +'</td>';
	                
					result = result +'       </tr>                                                                                                           ';
				}
    		}else{
			 			result = result +'          <tr>';
						result = result +'                <td valign="top" colspan="10" class="dataTables_empty"></td>';
						result = result +'          </tr>';
				}
					
	        	result = result +'     </tbody>                                                                                                          ';
				result = result +'   </table>    '; 
				 $("#clientInvoiceList").empty();  
			     $("#clientInvoiceList").append(result);
			     
			     	$("#emptyInvoiceId").val(json.length);
					
				    $("#draftInvoiceCount").html(draftCount);
				    $("#unbilledAmoumt").html(draftTotalAmount.toFixed(2));
				    		    
				    $("#paidInvoiceCount").html(paidCount);
				    $("#paidInvoiceAmount").html(paidTotalAmount.toFixed(2));
				    
				    $("#dueAfterInvoiceCount").html(dueAfterInvoiceCount);
				    $("#dueAfterInvoiceAmount").html(dueAfterInvoiceAmount.toFixed(2));
				    
				    $("#dueBeforeInvoiceCount").html(dueBeforeInvoiceCount);
				    $("#dueBeforeInvoiceAmount").html(dueBeforeInvoiceAmount.toFixed(2));
	        	
    	   			clientInvoicePagination(); 
    	  
	        },
	    });
	}
</script>


<script>
    //search script
        function customInvoiceList(orgRelationId,clientLocationId) {
    	var selectVal = $("#dayWiseTypeId").val();
            if (selectVal == "custom") {
                $(".custom_search").show();
                customDate(orgRelationId,clientLocationId);
            }else {
                $(".custom_search").hide();
            }
        }
       
</script>

<script>
function customDate(orgRelationId,clientLocationId){
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
	result = result +'   <div class="col-md-2 paddingLeft" onclick="searchResults('+orgRelationId+','+clientLocationId+');">                                                                ';
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
function searchResults(orgRelationId,clientLocationId){
	$("#errStartDate").find('p.jquery_form_error_message').remove();
	$("#errEndDate").find('p.jquery_form_error_message').remove();
	var startDate =  $('#billingStartDate').val();
	var endDate =  $('#billingEndDate').val();
	if(startDate != null && startDate != '' && startDate != 'undefined' && endDate != null && endDate != '' && endDate != 'undefined'){
		getInvoiceListBasedonClient(orgRelationId,clientLocationId);
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
function clientInvoicePagination(){  
    $('#invoiceListPagination').DataTable( { 
    	dom: 'Bfrtip',
        buttons: [
        	{
                extend: 'excel',
                title: 'Client',
            },
            {
                extend: 'pdf',
                title: 'Client',
            }
        ],
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $(".vendor_wrap_inn_table").find('a.buttons-excel').prop("title", "Excel");
	$(".vendor_wrap_inn_table").find('a.buttons-pdf').prop("title", "PDF");
}
</script>

<!-- modal pop -->
<div class="side_mask" style="display: none"></div>
<div class="modal modal_invoice" style="display: none;">
   <div id="linkPopup" ></div>
</div>
<!-- modal pop -->


 <!-- Add invoice Popup -->
		  <div id="createInvoiceDiv"></div>
		  <div class="pop_mask"></div>
		  <script>
		    //addcandidate-popup script
		    function createBill(orgRelationId){
		        
		      var result = '';
		      result = result +'<div class="addcandidate-popup">';
			  result = result +'  <div class="row">';
			  result = result +'    <div class="col-md-offset-2 col-md-10"> <a href="javascript:void(0)" class="close-popdiv" onClick="closeCandidate()">&times;</a>';
			  result = result +'    <div class="col-md-5 mt25">';
			  result = result +'      <div class="small-widget bgGreen">';
			  result = result +'          <a onClick="createClientInvoice('+orgRelationId+',\'custom\');" title="Create custom invoice">';
			  result = result +'        <div class="clearfix mb10"> ';
			  result = result +'            <img src="resources/images/invoice_icon.png" class="text-center" alt="create_receivables">';
			  result = result +'        </div>';
			  result = result +'        <strong class="clearfix">Custom</strong></a>';
			  result = result +'        </div>';
			  result = result +'    </div>';
			  result = result +'      <div class="col-md-5 mt25">';
			  result = result +'        <div class="small-widget bg-yellow"> ';
			  result = result +'        <a onClick="createClientInvoice('+orgRelationId+',\'project\');" title="Create project based invoice">';
			  result = result +'          <div class="clearfix mb10">';
			  result = result +'              <!--<i class="fa fa-usd fa-3x"></i>-->';
			  result = result +'              <img src="resources/images/invoice_icon.png" class="text-center" alt="Invoice">';
			  result = result +'          </div>';
			  result = result +'          <strong class="clearfix">Project Based </strong> </a> </div>';
			  result = result +'      </div>';
			  result = result +'        </div>';
			  result = result +'  </div>';
			  result = result +'</div>';
		        $("#createInvoiceDiv").empty();
		        $("#createInvoiceDiv").append(result);
		        $('.addcandidate-popup').fadeIn(200);
		        $('.pop_mask').fadeIn(200);
		    };
		       function closeCandidate(){
		        $('.addcandidate-popup').fadeOut(200);
		        $('.pop_mask').fadeOut(200);
		       }
		       
		       function createClientInvoice(orgRelationId,type){
		    	   var sourceleadUrl = '${sourceleadUrl}';
		    	   if(type == 'project'){
		    			window.location.href = sourceleadUrl + "/createInvoiceProjectBased?&orgRelationId="+orgRelationId;
		    	   }else{
		    		   window.location.href = sourceleadUrl + "/createInvoice?&orgRelationId="+orgRelationId;
		    	   }
		       }
		    </script> 
  
   <!-- Add invoice Popup End -->
 
<script>
function receivePaymentPop1(invoiceId,total){
	var currencyCode = '${SourceLeadWeb_currencyCode}';
	var paymentAmount = total.toFixed(2);
	 var result = "";
	result = result +'  <div class="modal-dialog modal-lg">';
	result = result +'    <div class="modal-content">                                                     ';
	result = result +'      <div class="modal-header">                                                    ';
	result = result +'        <button type="button" class="close" data-dismiss="modal" onclick="cancelbtn();">×</button>         ';
	result = result +'        <h4 class="modal-title">Receive payment</h4>                                ';
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
	result = result +'              <input class="form-control input-sm" placeholder="Reference No" type="text" id="referenceNumber" name="referenceNumber" data-validation="required validate_length length2-250"  field-name="Reference No"> ';
	result = result +'            </div>        ';
	result = result +'          </div>          ';
	result = result +'          <div class="row mt">                                 ';
	result = result +'            <div class="col-md-12">                             ';
	result = result +'              <div class="row">                                ';
	result = result +'                <div class="col-md-6">                        ';
	result = result +'                  <label>Amount Received</label>               ';
	result = result +'                  <div class="row">                            ';
	result = result +'                    <div class="col-md-6">                     ';
	result = result +'                      <input   type="radio" name="paymentAmount" value="total" checked onclick="validMask();"> ';
	result = result +'                      Total Due:          ';
	result = result +'                      <input type="text" value="'+paymentAmount+'" placeholder="Total Due" class="form-control input-sm mt10" id="totalAmount" name="totalAmount" readonly> ';
	result = result +'                      <span class="addon-currency">'+currencyCode+'</span>';
	result = result +'                    </div>              ';
	result = result +'                    <div class="col-md-6">      ';
	result = result +'                      <input  type="radio" name="paymentAmount" value="custom" onclick="removeMask();" >     ';
	result = result +'                      Custom:';
	result = result +'                      <input type="text" value="" placeholder="Custom Due" class="form-control input-sm mt10 allownumericwithdecimal" id="customeAmount" name="customeAmount" readonly onblur="amountCal();">       ';
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
	result = result +'                  <input type="file"  id="paymentAttachment" name="paymentAttachment"> ';
	result = result +'                  <i class="fa fa-plus"></i></span> </div>                        ';
	result = result +'              </div>                                                              ';
	result = result +'            </div>                                                                ';
	result = result +'            </div>                                                                ';
	result = result +'          </div>                                                                  ';
	result = result +'          <div class="clearfix"></div>                                            ';
	result = result +'          <div class="clearfix mt col-md-12 mb">                                  ';
	result = result +'            <div class="pull-right">                                              ';
	'<sec:authorize access="hasAnyRole(\'ROLE_RECEIVE_PAYMENT\')">';
	result = result +'              <input class="btn btn-primary btn-sm" value="Save" type="button" onclick="saveReceivePayment('+invoiceId+');">   ';
	'</sec:authorize>';
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
		     		location.replace(sourceleadUrl+"/invoiceList");
		     		
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
function acceptedInvoice(id){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
    	 type: "POST",
	     dataType : 'json',
	    	 url: sourceleadUrl+"/acceptedInvoice?${_csrf.parameterName}=${_csrf.token}&invoiceId="+id, 
             success: function(result) {
        	location.replace(sourceleadUrl+"/invoiceList");
     		
        },
    });  
}

</script>

<script>
function rejectInvoice(id){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	//result = result +"		<p>Do you want to delete this vendor contact?</p>";
	result = result +"		<textarea id='reasonForDelete' class='form-control' field-name='Reject Reason'  data-validation='required validate_Space validate_Description validate_length length2-250' name='reasonForSpoc' value='' placeholder='Terminate Reason' rows='2'></textarea>";
	result = result + ' <div id="reasonForRejectDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onclick='saveReasonforReceivable("+id+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTermination();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script>
function saveReasonforReceivable(id){
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
		    	location.replace(sourceleadUrl+"/invoiceList");
		    }
	   });
	}
	  
}
</script>

<script>
function cancelTermination(){
	$("#remove_user_pop, .project_inn_mask").fadeOut();
}
</script>

<script>
function removeValidate(){
	$('#errPaymentDate').find('p.jquery_form_error_message').remove(); 
}
function removeErr(){
	$('#errPaymethod').find('p.jquery_form_error_message').remove(); 
}
</script>  
  <!-- modal pop -->
<div class="modal-backdrop" style="display: none"></div>
<div id="recvPymntpop" class="modal" style="display: none;">
   <div id="receivePaymentId" ></div>
</div>
<!-- modal pop --> 

<script>
function removeMask(){
	 $("#customeAmount").removeAttr("readonly");
	 $(".allownumericwithdecimal").on("keypress keyup blur",function (event) {
	        $(this).val($(this).val().replace(/[^0-9\.]/g,''));
	            if ((event.which != 46 || $(this).val().indexOf('.') != -1) && (event.which < 48 || event.which > 57)) {
	                event.preventDefault();
	            }
	        });
	 
	 
}
function validMask(){
	  $('#customeAmount').attr("readonly","readonly");  
	  
}
</script>
  
 <script>
function cancelbtn(){
	  $("#recvPymntpop").fadeOut(200);
      $('.modal-backdrop').fadeOut(200).removeClass('in');
}
</script>
  
<script>
function showPopUp(id,invoiceStatus,invoiceParentId,totalAmount){
		 removePopup();
	     result = "";
	     if(invoiceParentId == 'undefined'){
	    	 result = result +'    <ul class="list-inline list-unstyled menupop menupop_2"> ';
	     }else{
	    	 result = result +'    <ul class="list-inline list-unstyled menupop menupop_1"> ';
	     }
		 result = result+' <i class="fa fa-close close-div closediv-small" onClick="removePopup();"></i>';
		 result = result +'    <input type="hidden" id="invoicePopupId" value="'+id+'"> ';
		 if(invoiceParentId == 'undefined'){
			 '<sec:authorize access="hasAnyRole(\'ROLE_SEND_INVOICE_REMINDER\')">';
			 result = result +'      <li onclick="sendReminder('+id+');"><i class="fa fa-bullhorn paddingRight" title="Send reminder"></i>Send reminder</li> ';
			 '</sec:authorize>'
		 }
		 '<sec:authorize access="hasAnyRole(\'ROLE_PRINT_INVOICE\')">';
		 result = result +'      <li onclick="getPrint('+id+');"><i class="fa fa-print paddingLeft paddingRight" title="Print"></i>Print</li>';
		 '</sec:authorize>'
		
		 '<sec:authorize access="hasAnyRole(\'ROLE_DOWNLOAD_INVOICE\')">';
		 result = result +'      <li onclick="downloadInvoice('+id+');"><i class="fa fa-download paddingLeft paddingRight" title="Download"></i>Download</li>';
		 '</sec:authorize>'
		 
		 result = result +'      <li onclick="linkInvoice('+id+',\''+totalAmount+'\');"><i class="fa fa-download paddingLeft paddingRight" title="Link Invoice"></i>Link Invoice</li>';
	     result = result +'    </ul>';
    	 $("#popupDiv"+id+"").append(result);
    	 showMenu();
}
</script>

<script>
    function showMenu() {
    	$('.menupop').toggle();
        
    };
</script>

<!-- <script>
$(".menupop, .pull-right, .positionRelative, .menuWidget, .fa, .fa-ellipsis-v, #unbilled").click(function(e) {
	e.stopPropagation();
});

$('html').click(function(){
	$(".menupop").hide();
});
</script> -->

<script>
function removePopup(){
	if( document.getElementById("invoicePopupId") != null &&  document.getElementById("invoicePopupId") != 'undefined'){
		var invoicePopupId = document.getElementById("invoicePopupId").value;
		  $("#popupDiv"+invoicePopupId+"").empty(); 
	}
	
}
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
}
</script>

<script>
function downloadInvoice(id){
	var sourceleadUrl ='${sourceleadUrl}';
	window.location.href = sourceleadUrl+"/downloadInvoice?${_csrf.parameterName}=${_csrf.token}&invoiceId="+id;
}

</script>

<script type="text/javascript">
function linkInvoice(invoiceId,totalAmount){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
    	 type: "POST",
	     dataType : 'json',
	     url : sourceleadUrl+"/getClientBasedInvoiceList?${_csrf.parameterName}=${_csrf.token}&invoiceId="+invoiceId+"&invoiceType=receivable",
			success: function(data) {
			var result = "";
			result = result +'  <div class="modal-dialog modal-lg">                                                                                         ';
			result = result +'    <div class="modal-content">                                                                                               ';
			result = result +' <input type="hidden" value='+invoiceId+' name="masterInvoiceId" id="masterInvoiceId">   ';
			result = result +'      <div class="modal-header" id="clientInvoicelinkId">                                                                                              ';
			result = result +'        <button type="button" class="close" data-dismiss="modal" onClick="closeLinkInvoice();">×</button>                                                   ';
			result = result +'        <h4 class="modal-title">Link Invoice</h4>                                                                             ';
			result = result +'      </div>                                                                                                                  ';
			result = result +'      <div class="modal-body clearfix">                                                                                       ';
			result = result +' <div class="timesheet_out clearfix">                                                                                         ';
			result = result +'      <div class="candidate_wrap_table">                                                                                      ';
			result = result +'      <table>                                                                                                                 ';
			result = result +'        <thead>                                                                                                               ';
			result = result +'          <tr>                                                                                                                ';
			result = result +'            <th>&nbsp;                                                                                                        ';
			result = result +'                                                                                                                              ';
			result = result +'              </th>                                                                                                           ';
			result = result +'            <th>ID</th>                                                                                                       ';
			result = result +'            <th>Contact Person</th>                                                                                           ';
			result = result +'            <th>Due date</th>                                                                                                 ';
			result = result +'            <th>Bill date</th>                                                                                                ';
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
			result = result +'            <td>                                                                                                              ';
			result = result +'              <div class="tbldata-dotdot-sm">'+opt.clientContactName+'</div>                                                    ';
			result = result +'            </td>                                                                                                             ';
			result = result +'            <td>                                                                                                              ';
			result = result +'              <div class="tblminwidth-50">'+opt.dueDateStr+'</div>                                                                    ';
			result = result +'            </td>                                                                                                             ';
			result = result +'            <td>                                                                                                              ';
			result = result +'              <div class="tblminwidth-50">'+opt.invoiceDateStr+'</div>                                                                     ';
			result = result +'            </td>                                                                                                             ';
			result = result +'            <td>                                                                                                              ';
			result = result +'              <div class="tbldata-dotdot_80"  id="linkedReceivableTotalId'+opt.id+'" >'+opt.total+'</div>                                                                 ';
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
			result = result +'             <button type="button" class="btn btn-primary" onClick="mergeInvoice('+totalAmount+');"><i class="fa fa-external-link paddingRight"></i>Link</button>      ';
			result = result +'         </div>                                                                                                               ';
			result = result +'     </div>                                                                                                                   ';
			result = result +'    </div>                                                                                                                    ';
			result = result +'    <!-- Pop Link Invoice Pop -->                                                                                             ';
			result = result +'  </div>                                                                                                                      ';
			result = result +'</div>                                                                                                                        ';
		
			$("#linkPopup").empty();
			$("#linkPopup").append(result);
			$('.modal.modal_invoice').fadeIn(200);
	        $('.side_mask').fadeIn(200);
		},
	 }); 
}
</script>

<!-- Script for Linked Invoice pop -->
    <script>
        $(".sendReminder").click(function(){
        $('.modal.modal_invoice').fadeIn(200);
        //$('.side_mask').fadeIn(200);
        });
        
        $(".close, .cancel").click(function(){
        $('.modal.modal_invoice').fadeOut(200);
        //$('.side_mask').fadeOut(200);
        });
        function closeLinkInvoice(){
            $('.modal.modal_invoice').fadeOut(200);
            //$('.side_mask').fadeOut(200);
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
	var total = $('#linkedReceivableTotalId'+checkVal+'').text();
	var calculatedAmount = 	parentTotalAmount - total;
	
	if(checkVal != '' && checkVal != 'undefined' && checkVal != null){
	showDiffAmountPopup(calculatedAmount,currencyCode);
	 $.ajax({
   	 type: "POST",
	     dataType : 'json',
	    	 url: sourceleadUrl+"/linkInvoice?${_csrf.parameterName}=${_csrf.token}&checkVal="+checkVal+"&invoiceType=Receivable&masterInvoiceId="+masterInvoiceId, 
            success: function(data) {
           	
       },
   }); 
}else{
	$("#clientInvoicelinkId").after( "<p class='jquery_form_error_message'>Select atleast 1 Invoice</span>");
}
}
</script>


<script>
function showDiffAmountPopup(calculatedAmount,currencyCode){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='invoice_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Variation Amount  = "+calculatedAmount+" "+currencyCode+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#invoice_pop").slideDown(200);
}

</script>

<script type="text/javascript">
function cancel(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
	var sourceleadUrl ='${sourceleadUrl}';
	location.replace(sourceleadUrl+"/invoiceList");	
}
</script>


<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {
			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};
	})(jQuery);
</script>




