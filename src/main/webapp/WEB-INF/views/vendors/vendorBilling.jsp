<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="/resources/css/style.css" />    
<input type="hidden" id="emptyPayableInvoiceId" name="emptyPayableInvoiceId"/>
<div class="success_pop" id="selector13" style="display: none;">
		<i class="fa fa-check-circle"></i><span id="msgdiv1"></span>
  </div>
<script>
function getVendorInvoiceList(orgRelationId,vendorLocationId){
var result = "";
result = result  +'<div class="">';
result = result  +'    <div class="billingArea"> ';  
 result = result + '			    <div class="clearfix">																																				                   ';
 result = result + '			      <div class="billing_dashboard noPad billing_dashboardH4">                                                                                                                                                                              ';
 result = result + '			        <div class="tabbable positionRelative">                                                                                                                                                                    ';
 result = result + '			          <div class="onboardcreateButtons">                                                                                                                                                                       ';
 result = result + '			            <div class="pull-right select-field ml">                                                                                                                                                               ';
 result = result + '			              <select class="form-control input-sm" id="dayWiseTypeId" onchange="getInvoiceListBasedonVendor('+orgRelationId+','+vendorLocationId+');customInvoiceList('+orgRelationId+','+vendorLocationId+');">                                                                                           ';
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
 result = result + '			                        <a  data-toggle="tab" class="onboardCount billbg_orange" title="Draft Bills">                                                                                                           ';
 result = result + '			                            <div class="pull-left">                                                                                                                                                                ';
 result = result + '			                            <strong>${SourceLeadWeb_currencyCode}</strong>                                                                                                                                         ';
 result = result + '			                            </div>                                                                                                                                                                                 ';
 result = result + '			                            <div class="pull-right ml10">                                                                                                                                                          ';
 result = result + '			                            <div class="count" id="unbilledAmoumt"></div>                                                                                                                                          ';
 result = result + '			                                <h5>Draft Bills <span class="paddingLeft" id="draftInvoiceCount"></span></h5>                                                                                                   ';
 result = result + '			                                </div>                                                                                                                                                                             ';
 result = result + '			                        </a>                                                                                                                                                                                       ';
 result = result + '			                    </li>                                                                                                                                                                                          ';
 result = result + '			                    <li>                                                                                                                                                                                           ';
 result = result + '			                        <h4>Un Paid</h4>                                                                                                                                                                           ';
 result = result + '			                        <a  data-toggle="tab" title="UnPaid Overdue Bills" class="billingbg_red">                                                                                                               ';
 result = result + '			                        <div class="pull-left">                                                                                                                                                                    ';
 result = result + '			                         <strong>${SourceLeadWeb_currencyCode}</strong>                                                                                                                                            ';
 result = result + '			                            </div>                                                                                                                                                                                 ';
 result = result + '			                            <div class="pull-right ml10">                                                                                                                                                          ';
 result = result + '			                            <div class="count" id="dueAfterInvoiceAmount"></div>                                                                                                                                   ';
 result = result + '			                        <h5>Overdue Bills<span class="paddingLeft" id="dueAfterInvoiceCount"></span></h5>                                                                                                       ';
 result = result + '			                            </div>                                                                                                                                                                                 ';
 result = result + '			                                                                                                                                                                                                                   ';
 result = result + '			                        </a>                                                                                                                                                                                       ';
 result = result + '			                    </li>                                                                                                                                                                                          ';
 result = result + '			                    <li>                                                                                                                                                                                           ';
 result = result + '			                        <h4>&nbsp;</h4>                                                                                                                                                                            ';
 result = result + '			                        <a  data-toggle="tab" title="UnPaid Open Bills" class="billingbgRed">                                                                                                                   ';
 result = result + '			                        <div class="pull-left">                                                                                                                                                                    ';
 result = result + '			                            <strong>${SourceLeadWeb_currencyCode}</strong>                                                                                                                                         ';
 result = result + '			                            </div>                                                                                                                                                                                 ';
 result = result + '			                            <div class="pull-right ml10">                                                                                                                                                          ';
 result = result + '			                            <div class="count" id="dueBeforeInvoiceAmount"></div>                                                                                                                                  ';
 result = result + '			                        <h5>Open Bills<span class="paddingLeft" id="dueBeforeInvoiceCount"></span></h5>                                                                                                         ';
 result = result + '			                        </div>                                                                                                                                                                                     ';
 result = result + '			                        </a>                                                                                                                                                                                       ';
 result = result + '			                    </li>                                                                                                                                                                                          ';
 result = result + '			                    <li class="billingbg_greens">                                                                                                                                                                  ';
 result = result + '			                        <h4>Paid</h4>                                                                                                                                                                              ';
 result = result + '			                        <a  data-toggle="tab" title="Paid Bills" class="billbg_green">                                                                                                                          ';
 result = result + '			                        <div class="pull-left">                                                                                                                                                                    ';
 result = result + '			                           <strong>${SourceLeadWeb_currencyCode}</strong>                                                                                                                                          ';
 result = result + '			                            </div>                                                                                                                                                                                 ';
 result = result + '			                            <div class="pull-right ml10">                                                                                                                                                          ';
 result = result + '			                            <div class="count" id="paidInvoiceAmount"></div>                                                                                                                                       ';
 result = result + '			                        <h5>Paid Bills<span class="paddingLeft" id="paidInvoiceCount"></span></h5>                                                                                                              ';
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
 result = result + '			           <div id="invoiceLoading"></div>                                                                                                                                                                                                        ';
 result = result + '			            <!-- Employee Details -->                                                                                                                                                                              ';
 result = result + '			            <div class="tab-pane active" id="unbilled">                                                                                                                                                            ';
 result = result + '			              <div class="timesheet_out">                                                                                                                                                                       ';
 result = result + '			                <div class="clearfix">                                                                                                                                                                             ';
 result = result + '			                  <div class="timesheet_head timesheetHead-UL timesheet_head_nav_tabs">                                                                                                                                                                     ';

 result = result + ' <div class="pull-left cand-advsrch advsrch_chosen">';
 result = result + ' <button type="button" class="btn btn-primary dropdown-toggle btn-sm mr" onclick="resetBillFilter('+orgRelationId+','+vendorLocationId+');" title="Refresh">';
 result = result + '    <span class="fa fa-refresh"></span>';
 result = result + '</button>';
 result = result + '<a href="javascript:void(0)" class="btn btn-default btn-sm anchorBlack text-center" title="Filter Search" onclick="openBillFilterPop('+orgRelationId+','+vendorLocationId+');"><i class="fa fa-filter faFilter padding_right" style="color: #909090!important"></i>';
 result = result + '</a>';
 result = result + '</div>';
 
 result = result + '			                    <ul>   ';
 
 result = result + ' <li>';
 result = result + '	<a id="actionDiv" class="action mr" href="javascript:void(0)"><i class="fa fa-user-plus"></i>&nbsp;Add<i class="fa fa-caret-down"></i></a>';
 result = result + '	<div class="action-pop">';	
 result = result + '		<span style="width:225px;">';
	'<sec:authorize access="hasAnyRole(\'ROLE_ADD_BILL\')"> ';
 result = result + '           <a href="javascript:void(0)" class="anchorTag" title="Create Bill"  onclick="createBill('+orgRelationId+');"> <i class="fa fa-plus paddingRight"></i>Create Bill</a>';
      '</sec:authorize>';
 result = result + '        <a href="javascript:void(0)" class="" title="Generate Automatic Bill" onclick="generateAutomaticInvoice();"> <i class="fa fa-gears paddingRight"></i>Generate Automatic Bill</a>';
 result = result + '		<div class="clearfix"></div>';
 result = result + '		</span>';
 result = result + '	</div>';
 result = result + '</li>';
 result = result + '			                  </div>                                                                                                                                                                                           ';
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
 result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table exlpdf_btns exlpdf_btns_btns">';
 result  = result + '   <div id="vendorInvoiceList" ></div>';
 result  = result + '   </div>';
 result = result+'	    </div>';
 result = result+'	    </div>';

    $("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty();  
    $("#tab-6").empty();  
    $("#tab-7").empty();
    $("#tab-8").empty();
    $("#tab-4").empty();
    $("#tab-4").append(result);  
	
    
    $("#actionDiv").click(function(){
		$(".action-pop").slideToggle(200);
	});
 	$("html").click(function(){
		$(".action-pop").slideUp(200);
	});
	$(".action, .action-pop, #actionDiv").click(function(e){
	 	e.stopPropagation();
 	});
    getInvoiceListBasedonVendor(orgRelationId,vendorLocationId);
    
}
</script>

<script type="text/javascript">
	function getInvoiceListBasedonVendor(orgRelationId,vendorLocationId) {
		var sourceleadUrl = '${sourceleadUrl}';
		var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  	  if(locationList.match("yyyy-MM-dd")){
	  		  billingVendorDatePattern = 'YYYY-MM-DD';
	  	  }if(locationList.match("dd-MM-yyyy")){
	  		billingVendorDatePattern = 'DD-MM-YYYY'; 
	  	  }if(locationList.match("MM-dd-yyyy")){
	  		billingVendorDatePattern = 'MM-DD-YYYY'; 
	  	  }if(locationList.match("yyyy-dd-MM")){
	  		billingVendorDatePattern = 'YYYY-DD-MM'; 
	  	  }
		
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
	    	url : sourceleadUrl+"/getInvoiceListBasedOnOrgrelationId?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId+"&clientLocationId="+vendorLocationId+"&dayWisetype="+dayWisetype+"&startDate="+startDate+"&endDate="+endDate+"&invoiceStatus="+invoice+"&paymentStatus="+payment+"&invoiceType=payables", 
	        success: function(json) {
	        
	        var draftCount = 0;
        	var draftTotalAmount = 0;
        	
        	var paidCount = 0;
        	var paidTotalAmount = 0;
        	
        	var dueAfterInvoiceCount = 0;
        	var dueAfterInvoiceAmount = 0;
        	
        	var dueBeforeInvoiceCount = 0;
        	var dueBeforeInvoiceAmount = 0;
	        
			var result = "";
	      
			result = result +'	<table id="vendorInvoiceListPagination">                                                                                                         ';
			result = result +'     <thead>                            ';
			result = result +'       <tr>                             ';
			result = result +'         <th>Action</th>                ';
			result = result +'         <th>Reference ID</th>                    ';
			result = result +'         <th>Vendor Contact</th>               ';
			result = result +'         <th>Invoice Date <br>(<small class="custom-small">'+billingVendorDatePattern+'</small>)</th>               ';
			result = result +'         <th>Total</th>                 ';
			result = result +'         <th>Due</th>                   ';
			result = result +'         <th>Due date <br>(<small class="custom-small">'+billingVendorDatePattern+'</small>)</th>              ';
			result = result +'         <th>Status</th>                ';
			result = result +'         <th>Payment Status</th>                ';
			result = result +'       </tr>                            ';
			result = result +'     </thead>                           ';
			result = result +'     <tbody>                            ';
			if(json.data != null && json.data != ''){
// 				var json = JSON.parse(data);
	        	for (var i=0; i<json.data.length; i++)
				  {	        		
	        		var report = json.data[i];
	        		
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
	       			
	       			var invoiceStatus = null;
	       			if(report.invoiceStatus == "SENT"){
	       				invoiceStatus = "Sent"
	       			}else if(report.invoiceStatus == "DRAFT"){
	       				invoiceStatus = "Draft"
	       			}else{
	       				invoiceStatus = report.invoiceStatus
	       			}
	        		
	        		result = result +'       <tr> ';
	        		result = result +'         <td> ';
					result = result +'     <div class="tblminwidth-90" style="position: inherit;">';
					 result = result +'  <div class="pull-right menuWidget" style="position: inherit;">';
					 if(report.invoiceStatus != "Received" && report.invoiceStatus != "Rejected"){
						 result = result +' <i class="fa fa-ellipsis-v divPadding" onClick="showPopUp(\''+report.id+'\',\''+report.invoiceUUID+'\',\''+report.invoiceStatus+'\',\''+report.invoiceParentId+'\',\''+report.total+'\',\''+report.paymentStatus+'\',\''+report.fileUrl+'\',\''+report.dueAmount+'\');" ></i>  ';
							result = result +'  <div id="popupDiv'+report.invoiceUUID+'" style="position: inherit;"></div>';
					} 
					result = result +'  </div> ';  
					
					if(report.invoiceType=="Payables" && report.invoiceParentId != null && report.invoiceParentId != '' && report.invoiceParentId != "undefined"){
						if(report.paymentStatus == "Paid" || report.invoiceStatus == "DRAFT" || report.invoiceStatus == "Rejected"){
							if(report.paymentStatus == "Paid"){
								result = result +'           <span class="text-primary primary pull-left" title="Paid" >Paid</span>         ';
							}else if(report.invoiceStatus == "DRAFT"){
								result = result +'           <span class="textWarning pull-left" title="Draft">Draft</span>          ';
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
									result = result +' <a class="successBtn primary pull-left cursorPointer" onClick="unLinkBill(\''+report.invoiceUUID+'\')">UnLink</a>  ';
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
								result = result +'           <span class="textWarning pull-left" title="Draft">Draft</span></td>          ';
							}else if(report.invoiceStatus == "SENT"){
								result = result +'           <span class="text-primary pull-left" title="Sent" >Sent</span> </td>          ';
							}else if(report.invoiceStatus == "Rejected"){
								result = result +'           <div class="colorRed pull-left">Rejected</div>   ';
							}
								
						}else{
							if(report.invoiceStatus == "Linked"){
								result = result +' <a class="successBtn primary pull-left" onClick="unLinkBill(\''+report.invoiceUUID+'\')">UnLink</a>  ';
							}else if(report.invoiceStatus == "Received"){
								result = result +'           <span class="clearfix" ><a onclick="acceptedInvoice(\''+report.invoiceUUID+'\');" title="Accept"><i class="fa fa-check text-success paddingRight"></i></a><a onclick="rejectInvoice(\''+report.invoiceUUID+'\');" title="Reject"><i class="paddingLeft fa fa-times text-danger"></i></a></span> </td>';
							}else if(report.invoiceStatus == "Void"){
								result = result +'           <span class="colorRed pull-left">Void</span>   ';	
							}
							else{
								result = result +'           <span class="receiveBtn receive_Btn pull-left" onclick="receivePaymentPop1('+report.id+','+report.dueAmount+');" title="Pay">Pay</span> </td>          ';
							}
						}
						
						
					}
					
	 
			        result = result +'</div>';
			        result = result +'</td>';
					result = result +'         <td>';
					if(report.invoiceStatus == "DRAFT" || report.invoiceStatus == "Rejected"){
						
						if(report.projectId != null && report.projectId != '' && report.projectId != 'undefined'){
							'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_BILL\')">'
							result = result +'           <div class="tbldata-dotdot_80 invoiceId_Anchor"><a href="'+sourceleadUrl+'/getInvoiceByUUID?invoiceUUID='+report.invoiceUUID+'&projectId='+report.projectId+'" title = "'+report.invoiceId+'">'+report.invoiceId+'</a></div>   ';
							'</sec:authorize>';
						}else{
							'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_BILL\')">';
							result = result +'           <div class="tbldata-dotdot_80 invoiceId_Anchor"><a href="'+sourceleadUrl+'/getInvoiceByUUID?invoiceUUID='+report.invoiceUUID+'" title = "'+report.invoiceId+'">'+report.invoiceId+'</a></div>   ';
							'</sec:authorize>';
						}
						

					}
					if(report.invoiceStatus == "SENT" || report.invoiceStatus == "Received" || report.invoiceStatus == "Accepted" || report.invoiceStatus == "Linked"){
						'<sec:authorize access="hasAnyRole(\'ROLE_BILL_PAYMENT_HISTORY\')">';
						result = result +'           <div class="tbldata-dotdot_80 invoiceId_Anchor"><a href="<c:url value="/app/viewInvoice?invoiceUUID='+report.invoiceUUID+'&invoiceType=Payables"/>" title = "'+report.invoiceId+'">'+report.invoiceId+'</a></div>   ';				
						'</sec:authorize>';
					}
					
					result = result +'         </td>  ';
					result = result +'         <td>';
					if(report.clientContactName !='' && report.clientContactName != null && report.clientContactName != 'undefined'){
						result = result +'<div class="tbldata-dotdot-vsm" title="'+report.clientContactName+'">'+report.clientContactName+'</div>  ';	
					}else{
						result = result +'<div class="tbldata-dotdot-vsm" title="">--</div>  ';
					}
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
					result = result +'         </td>';
					
					result = result +'         <td> ';
					result = result +'           <div class="tbldata-dotdot_100">'+report.paymentStatus+'</div> ';
					result = result +'         </td>';
					
					                
					result = result +'       </tr>                                                                                                           ';
				}
    		}else{
			 			result = result +'          <tr>';
						result = result +'                <td valign="top" colspan="10" class="dataTables_empty"></td>';
						result = result +'          </tr>';
				}
					
	        	result = result +'     </tbody>                                                                                                          ';
				result = result +'   </table>    '; 
				 $("#vendorInvoiceList").empty();  
			     $("#vendorInvoiceList").append(result);
			     
			     	$("#emptyPayableInvoiceId").val(json.length);
					
				    $("#draftInvoiceCount").html(draftCount);
				    $("#unbilledAmoumt").html(draftTotalAmount.toFixed(2));
				    		    
				    $("#paidInvoiceCount").html(paidCount);
				    $("#paidInvoiceAmount").html(paidTotalAmount.toFixed(2));
				    
				    $("#dueAfterInvoiceCount").html(dueAfterInvoiceCount);
				    $("#dueAfterInvoiceAmount").html(dueAfterInvoiceAmount.toFixed(2));
				    
				    $("#dueBeforeInvoiceCount").html(dueBeforeInvoiceCount);
				    $("#dueBeforeInvoiceAmount").html(dueBeforeInvoiceAmount.toFixed(2));
	        	
    	   			 vendorInvoicePagination(); 
    	  
	        },
	    });
	}
</script>

<script>
function customDate(orgRelationId,vendorLocationId){
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
	result = result +'   <div class="col-md-2 paddingLeft" onclick="searchResults('+orgRelationId+','+vendorLocationId+');">                                                                ';
	result = result +'     <button type="submit" class="btn btn-sm btn-primary btn-block"><i class="fa fa-search paddingRight"></i></button>           ';
	result = result +'   </div>                                                                                                                        ';
	result = result +' </div>                                                                                                                          ';
	$("#customDateDivId").empty();
	$("#customDateDivId").append(result);
	
	 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormatVendorBillingStartDate = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormatVendorBillingStartDate = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormatVendorBillingStartDate = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormatVendorBillingStartDate = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormatVendorBillingStartDate = 'yy-dd-mm'; 
	  }
	
	
	$('#billingStartDate').datepicker({
		changeMonth: true,
		changeYear: true, 
		dateFormat : finalDateFormatVendorBillingStartDate,
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
		dateFormat :finalDateFormatVendorBillingStartDate,
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
function searchResults(orgRelationId,vendorLocationId){
	$("#errStartDate").find('p.jquery_form_error_message').remove();
	$("#errEndDate").find('p.jquery_form_error_message').remove();
	var startDate =  $('#billingStartDate').val();
	var endDate =  $('#billingEndDate').val();
	if(startDate != null && startDate != '' && startDate != 'undefined' && endDate != null && endDate != '' && endDate != 'undefined'){
		getInvoiceListBasedonVendor(orgRelationId,vendorLocationId);
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
function removeStartdateval(){
	$("#errStartDate").find('p.jquery_form_error_message').remove();
}
function removeEnddateval(){
	$("#errEndDate").find('p.jquery_form_error_message').remove();
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
    //search script
        function customInvoiceList(orgRelationId,vendorLocationId) {
    		var selectVal = $("#dayWiseTypeId").val();
            if (selectVal == "custom") {
                $(".custom_search").show();
                customDate(orgRelationId,vendorLocationId);
            }else {
                $(".custom_search").hide();
            }
        }
       
</script>




<script>
function vendorInvoicePagination(){  
    $('#vendorInvoiceListPagination').DataTable( { 
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
    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 8
			}],
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $(".vendor_wrap_inn_table").find('a.buttons-excel').prop("title", "Excel");
	$(".vendor_wrap_inn_table").find('a.buttons-pdf').prop("title", "PDF");
}
</script>

<div id="popUp"></div>

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

function cancelTermination(){
	$("#popUp").empty();
	 $(".project_inn_mask").fadeOut(200);
}

function saveBillRejectedReason(invoiceUUID){
	var reason = $("#reasonForBill").val();
	if(reason != null && reason != ''){
		 $("#reasonForBillDiv").empty();
		if(reason.length > 250){
		$("#reasonForBillDiv").empty().append("<font color='red' style='padding: 5px 10px; '>The Entered values should not be more than 250 characters</font>") ; 
		}else{
			 $.ajax({
				   	type: "POST",
				    url: sourceleadUrl+"/deleteInvoice?"+tokenParamName+"="+tokenValue+"&invoiceType=payables&invoiceUUID="+invoiceUUID+"&reason="+reason,
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


<!-- modal pop -->
<div class="side_mask" style="display: none; background-color: rgba(0,0,0,0.3);"></div>
<div class="modal modal_invoice datatable_filter" style="display: none;">
   <div id="linkPayblePopup" ></div>
</div>
<!-- modal pop -->


 <!-- Add invoice Popup -->
		  <%--  <div class="addcandidate-popup">
		    <div class="row">
		      <div class="col-md-offset-2 col-md-10"> <a href="javascript:void(0)" class="close-popdiv" onClick="closeCandidate()">&times;</a>
		      <div class="col-md-5 mt25">
		        <div class="small-widget bgGreen">
		            <a  href="<c:url value='/createPayables'/>" title="Create custom invoice">
		          <div class="clearfix mb10"> 
		              <img src="resources/images/invoice_icon.png" class="text-center" alt="create_receivables">
		          </div>
		          <strong class="clearfix">Custom</strong></a>
		          </div>
		      </div>
		
		        <div class="col-md-5 mt25">
		          <div class="small-widget bg-yellow"> 
		          <a href="<c:url value='/createInvoiceProjectPayableBased'/>" title="Create project based invoice">
		            <div class="clearfix mb10">
		                <!--<i class="fa fa-usd fa-3x"></i>-->
		                <img src="resources/images/invoice_icon.png" class="text-center" alt="Invoice">
		            </div>
		            <strong class="clearfix">Project Based </strong> </a> </div>
		        </div>
		          </div>
		    </div>
		  </div> --%>
		   <div id="createBillDiv"></div>
		  <div class="pop_mask" style="z-index: 99999;"></div>
		  <script>
		    //addcandidate-popup script
		    function createBill(orgRelationId){
		    	
		    	 var result = '';
			        result = result +'<div class="addcandidate-popup">';
				  result = result +'  <div class="row">';
				  result = result +'    <div class="col-md-offset-0 col-md-12"> <a href="javascript:void(0)" class="close-popdiv" onClick="closeCandidate()">&times;</a>';
				  result = result +'    <div class="col-md-4 mt25">';
				  result = result +'      <div class="small-widget bgGreen">';
				  result = result +'          <a onClick="createVendorInvoice('+orgRelationId+',\'custom\');" title="Create custom bill">';
				  result = result +'        <div class="clearfix mb10"> ';
				  result = result +'            <img src="/resources/images/invoice_icon.png" class="text-center" alt="create_receivables">';
				  result = result +'        </div>';
				  result = result +'        <strong class="clearfix">Vendor Based</strong></a>';
				  result = result +'        </div>';
				  result = result +'    </div>';
				  result = result +'      <div class="col-md-4 mt25">';
				  result = result +'        <div class="small-widget bg-yellow"> ';
				  result = result +'        <a onClick="createVendorInvoice('+orgRelationId+',\'project\');" title="Create project based bill">';
				  result = result +'          <div class="clearfix mb10">';
				  result = result +'              <!--<i class="fa fa-usd fa-3x"></i>-->';
				  result = result +'              <img src="/resources/images/invoice_icon.png" class="text-center" alt="Invoice">';
				  result = result +'          </div>';
				  result = result +'          <strong class="clearfix">Project Based </strong> </a> </div>';
				  result = result +'      </div>';
				  result = result +'      <div class="col-md-4 mt25">';
				  result = result +'        <div class="small-widget bg-purple"> ';
				  result = result +'        <a onClick="createVendorInvoice('+orgRelationId+',\'other\');" title="Create Other Bill">';
				  result = result +'          <div class="clearfix mb10">';
				  result = result +'              <!--<i class="fa fa-usd fa-3x"></i>-->';
				  result = result +'              <img src="/resources/images/invoice_icon.png" class="text-center" alt="Invoice">';
				  result = result +'          </div>';
				  result = result +'          <strong class="clearfix">Other </strong> </a> </div>';
				  result = result +'      </div>';
				  result = result +'        </div>';
				  result = result +'  </div>';
				  result = result +'</div>';
				  
			        $("#createBillDiv").empty();
			        $("#createBillDiv").append(result);
			        $('.addcandidate-popup').fadeIn(200).css({"z-index":"9999999"});
			        $('.pop_mask').fadeIn(200);
			    };
		       function closeCandidate(){
		        $('.addcandidate-popup').fadeOut(200);
		        $('.pop_mask').fadeOut(200);
		       }
		       
		       function createVendorInvoice(orgRelationId,type){
		    	   var sourceleadUrl = '${sourceleadUrl}';
		    	   if(type == 'project'){
		    			window.location.href = sourceleadUrl + "/createInvoiceProjectPayableBased?&orgRelationId="+orgRelationId;
		    	   }else if(type == 'other'){
		    			window.location.href = sourceleadUrl + "/createExpensesPayableBill?&orgRelationId="+orgRelationId;
		    	   }else{
		    		   window.location.href = sourceleadUrl + "/createPayables?&orgRelationId="+orgRelationId;
		    	   }
		       }
		    </script> 
   <!-- Add invoice Popup End-->
  
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
	result = result +'                  <input type="file"  id="paymentAttachment" name="paymentAttachment" onchange="getfileUrl()"> ';
	result = result +'                  <i class="fa fa-plus"></i></span> </div>                        ';
	result = result +'              </div>                                                              ';
	result = result +'            </div>                                                                ';
	result = result +'            </div>                                                                ';
	result = result +'          </div>                                                                  ';
	result = result +'          <div class="clearfix"></div>                                            ';
	result = result +'          <div class="clearfix mt col-md-12 mb">                                  ';
	result = result +'            <div class="pull-right">                                              ';
	result = result +'              <input class="btn btn-primary btn-sm" value="Save" type="button" onclick="saveReceivePayment('+invoiceId+');">   ';
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
		    	 url: sourceleadUrl+"/saveInvoicePayment?"+tokenParamName+"="+tokenValue+"&json="+jsonData+"&invoiceId="+invoiceId, 
		        success: function(result) {
		        	if (result && result.response === "INVALID_DATA") {
	          		    showError(result.responseMessage);
	          		} else if (result.response === "AWKWARD") {
	          		    location.href = "/errorPage";
	          		} else if (result.response === "SUCCESS") {
	          			location.replace(sourceleadUrl+"/payablesList");
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
function acceptedInvoice(invoiceUUID){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
    	 type: "POST",
	     dataType : 'json',
	    	 url: sourceleadUrl+"/acceptedInvoice?"+tokenParamName+"="+tokenValue+"&invoiceUUID="+invoiceUUID, 
             success: function(result) {
            	 if (result && result.response === "INVALID_DATA") {
	          		    showError(result.responseMessage);
	          		} else if (result.response === "AWKWARD") {
	          		    location.href = "/errorPage";
	          		} else if (result.response === "SUCCESS") {
	          			location.replace(sourceleadUrl+"/payablesList");
	          		} else if (result.indexOf('Sign In') !== -1) {
	          			location.replace(sourceleadUrl);
	          		} else {
	          		    showError("Operation failed, please try again");
	          		}
            	 
        },
    });  
}

</script>

<script>
function rejectInvoice(invoiceUUID){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	//result = result +"		<p>Do you want to delete this vendor contact?</p>";
	result = result +"		<textarea id='reasonForDelete' class='form-control' field-name='Reject Reason'  data-validation='required validate_Space validate_Description validate_length length2-250' name='reasonForSpoc' value='' placeholder='Terminate Reason' rows='2'></textarea>";
	result = result + ' <div id="reasonForRejectDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onclick='saveReasonforReceivable(\""+invoiceUUID+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTermination();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script type="text/javascript">
function generateAutomaticInvoice(){
	 $("#invoiceLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
    	 type: "POST",
	     dataType : 'json',
	    	 url: sourceleadUrl+"/generateAutomaticInvoice?"+tokenParamName+"="+tokenValue+"&invoiceType=payables&contractType=vendorSow", 
             success: function(json) {
            	  $("#invoiceLoading").removeAttr("style");
					$("#selector13").removeAttr("style");
	    			$("#selector13").css("display", "block");
	    			$("#msgdiv1").html(json.object.count+" Bill(s) Generated Successfully.");
					msgFadein();
            	 location.replace(sourceleadUrl+"/payablesList");	            	 
        },
    });  
}
</script>
<script>
	function msgFadein() {
		$("#selector13").fadeIn(200);
        $('#selector13').delay(2000).fadeOut('slow');
	}
</script>
<script>
function saveReasonforReceivable(invoiceUUID){
	var sourceleadUrl = '${sourceleadUrl}';
	var reason = document.getElementById("reasonForDelete").value;
	if(reason == null || reason == '' || reason == 'undefined'){
	      
		  $("#reasonForRejectDiv").find('p.jquery_form_error_message').remove();
	  	  $("#reasonForRejectDiv").append("<p class='jquery_form_error_message'>Reason Is Required</p>");
	  	  
		}else{
		  $("#reasonForRejectDiv").find('p.jquery_form_error_message').remove();
		$.ajax({
		   	type: "POST",
		   	url: sourceleadUrl+"/rejectInvoice?"+tokenParamName+"="+tokenValue+"&invoiceUUID="+invoiceUUID+"&reason="+reason,	    	   
		    success: function(result) {
		    	if (result && result.response === "INVALID_DATA") {
          		    showError(result.responseMessage);
          		} else if (result.response === "AWKWARD") {
          		    location.href = "/errorPage";
          		} else if (result.response === "SUCCESS") {
          			$("#popUp").empty();
   	 			    $(".project_inn_mask").fadeOut(200);
   		    	    location.replace(sourceleadUrl+"/payablesList");
          		} else if (result.indexOf('Sign In') !== -1) {
          			location.replace(sourceleadUrl);
          		} else {
          		    showError("Operation failed, please try again");
          		}
		    	
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
function getfileUrl(){
	fileUpload("paymentAttachment", "incoiceDocuments", "receivePayment-form");
}
function removeValidate(){
	$('#errPaymentDate').find('p.jquery_form_error_message').remove(); 
}
function removeErr(){
	$('#errPaymethod').find('p.jquery_form_error_message').remove(); 
}
</script>  
  <!-- modal pop -->
<div class="modal-backdrop" style="display: none"></div>
<div id="recvPymntpop" class="modal zInd" style="display: none;">
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
function showPopUp(id,invoiceUUID,invoiceStatus,invoiceParentId,totalAmount,paymentStatus,fileUrl,dueAmount){
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
		 if((invoiceParentId == 'undefined' || invoiceParentId == 'null') && invoiceStatus != 'DRAFT' && paymentStatus != 'Paid'){
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

			 }
			 
		 }if(invoiceStatus == "SENT"){
			 result = result +' <li><a onclick="receivePaymentPop1('+id+','+dueAmount+');"><i class="fa fa-money paddingRight paddingLeft" title="Accept Bill"></i>Pay</a></li>';
	 
		 }
// 	     result = result +'      <li onclick="getPrint('+id+');"><i class="fa fa-print paddingLeft paddingRight" title="Print"></i>Print</li>';
		result = result+' <li><a class="" href="'+fileUrl+'" title="Print" target="_blank"><i class="fa fa-print paddingLeft paddingRight"></i>Print</a></li>';
	     '</sec:authorize>'
	     
	     '<sec:authorize access="hasAnyRole(\'ROLE_DOWNLOAD_BILL\')">';
// 	     result = result +'      <li onclick="downloadInvoice('+id+');"><i class="fa fa-download paddingLeft paddingRight" title="Download"></i>Download</li>';
 		 result = result+' <li><a href="'+fileUrl+'" title="Download" download><i class="glyphicon glyphicon-save paddingLeft paddingRight"></i>Download</a></li>';
	     '</sec:authorize>'
	     if(invoiceStatus != 'DRAFT')
	     {
	     result = result +'  <li><a onclick="linkPayableInvoice(\''+invoiceUUID+'\',\''+totalAmount+'\');"><i class="fa fa-link paddingLeft paddingRight" title="Link Bill"></i>Link Bill</a></li>';
	     }
	     result = result +'    </ul>';
    	 $("#popupDiv"+invoiceUUID+"").append(result);
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
function sendReminder(invoiceUUID){
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
    	 type: "POST",
	     dataType : 'json',
	    	 url: sourceleadUrl+"/sendReminder?"+tokenParamName+"="+tokenValue+"&invoiceUUID="+invoiceUUID, 
             success: function(result) {
            	 if (result && result.response === "INVALID_DATA") {
           		    showError(result.responseMessage);
           		} else if (result.response === "AWKWARD") {
           		    location.href = "/errorPage";
           		} else if (result.response === "SUCCESS") {
           			
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

<script type="text/javascript">
function getPrint(invoiceUUID) {
	var sourceleadUrl ='${sourceleadUrl}';
	window.open(sourceleadUrl+'/getPrint/'+invoiceUUID+'','_newtab');
}
</script>

<script>
function downloadInvoice(invoiceUUID){
	var sourceleadUrl ='${sourceleadUrl}';
	window.location.href = sourceleadUrl+"/downloadInvoice?"+tokenParamName+"="+tokenValue+"&invoiceUUID="+invoiceUUID;
}

</script>

<script type="text/javascript">
function linkPayableInvoice(invoiceUUID,totalAmount){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
    	 type: "POST",
	     dataType : 'json',
	     url : sourceleadUrl+"/getClientBasedInvoiceList?"+tokenParamName+"="+tokenValue+"&invoiceUUID="+invoiceUUID+"&invoiceType=payables",
			success: function(json) {
			var result = "";
			result = result +'  <div class="modal-dialog modal-lg">                                                                                         ';
			result = result +'    <div class="modal-content">                                                                                               ';
			result = result +' <input type="hidden" value='+invoiceUUID+' name="masterInvoiceId" id="masterInvoiceId">   ';
			result = result +'      <div class="modal-header" id="vendorBilllinkId">                                                                                              ';
			result = result +'        <button type="button" class="close" data-dismiss="modal" onClick="closeLinkInvoice();">×</button>                                                   ';
			result = result +'        <h4 class="modal-title">Link Bill</h4>                                                                             ';
			result = result +'      </div>                                                                                                                  ';
			result = result +'      <div class="modal-body clearfix">                                                                                       ';
			result = result +' <div class="timesheet_out clearfix">                                                                                         ';
			result = result +'      <div class="candidate_wrap_table">                                                                                      ';
			result = result +'      <table id="linkInvoiceVendorPaginationId">                                                                                                                 ';
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
			result = result +'             <button type="button" class="btn btn-primary" onClick="mergeInvoice('+totalAmount+');"><i class="fa fa-external-link paddingRight"></i>Link</button>      ';
			result = result +'         </div>                                                                                                               ';
			result = result +'     </div>                                                                                                                   ';
			result = result +'    </div>                                                                                                                    ';
			result = result +'    <!-- Pop Link Invoice Pop -->                                                                                             ';
			result = result +'  </div>                                                                                                                      ';
			result = result +'</div>                                                                                                                        ';
		
			$("#linkPayblePopup").empty();
			$("#linkPayblePopup").append(result);
			LinkInvoiceVendorPagination();
			$('.modal.modal_invoice').fadeIn(200);
	        $('.side_mask').fadeIn(200);
			   
			    },
	 }); 
}
</script>


<script>
function LinkInvoiceVendorPagination(){  
	// $.fn.dataTable.moment( 'DD-MM-YYYY' );
    $('#linkInvoiceVendorPaginationId').DataTable( { 
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
    $("#linkInvoiceVendorPaginationId").wrap("<div class='overflow_Auto'></div>");
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
	$('#vendorBilllinkId').parent().find('p.jquery_form_error_message').remove();
	var invoiceUUID = $('#masterInvoiceId').val();
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
	    	 url: sourceleadUrl+"/linkInvoice?"+tokenParamName+"="+tokenValue+"&checkVal="+checkVal+"&invoiceType=Receivable&invoiceUUID="+invoiceUUID, 
            success: function(json) {
           	
       },
   });
}else{
	$("#vendorBilllinkId").after( "<p class='jquery_form_error_message'>Select atleast 1 Bill</span>");
	}
}
</script>


<script>
function showDiffAmountPopup(calculatedAmount,currencyCode){
	var result = "";
	result = result +"	<div class='project_inn_mask zInMask'></div>";
	result = result +"	   <div id='invoice_pop' class='remove_user_pop remove_user_pop_a zInPop'>";
	result = result +"		<p>Variation Amount  = "+calculatedAmount+" "+currencyCode+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='amountPopupOk();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#invoice_pop").slideDown(200);
	$(".project_inn_mask").show();
}

</script>

<script type="text/javascript">
function amountPopupOk(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
	var sourceleadUrl ='${sourceleadUrl}';
	location.replace(sourceleadUrl+"/payablesList");	
}
</script>

<script>
function cancel(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
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

<!-- ========================== Filter popup Written By Laxman ===============================  -->
<script>
function closeBillFilterPop(){
	$("#invoiceFilterId").hide();
	$(".comMask").fadeOut();
}
function openBillFilterPop(orgRelationId,vendorLocationId){
	 $("#invoiceFilterId").toggle();
	 $('#search_requiremet').attr("onclick","getInvoiceListBasedonVendor("+orgRelationId+","+vendorLocationId+");closeBillFilterPop();");
	 $('#clear_search').attr("onclick","resetBillFilter("+orgRelationId+","+vendorLocationId+");closeBillFilterPop();");
	 $(".comMask").fadeIn();
}
function resetBillFilter(orgRelationId,vendorLocationId){
	$("ul#paymnetFilter > li input:checkbox").prop("checked",false);
	$("ul#statusFilter > li input:checkbox").prop("checked",false);
	getInvoiceListBasedonVendor(orgRelationId,vendorLocationId)
}
</script>

<div class="comMask" style="z-index:99999"></div>
          <div class="filter-popup advancedsearch-popup filterDynamic-Search-medium" id="invoiceFilterId" style="display: none">
				<h5 class="headerAdsrch">Filter Search
					<a href="javascript:void(0)" class="close-div" onClick="closeBillFilterPop()"><i
						class="fa fa-close"></i></a>
				</h5>
				<div class="addInner">
					<div class="row">
					  <div class="col-md-6">
						<div class="row">
						    <div class="col-md-12 mb">
								<label><strong>Payment</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="paymnetFilter">
									<li class="checkBox positionRelative" ><input type="checkbox"  class="mb0" value="Paid" name="paymentStatus"><label for="onboard3">Paid</label></li>
	                                <li class="checkBox positionRelative" ><input type="checkbox"  class="mb0" value="Partial Paid" name="paymentStatus"><label for="onboard2">Partially Paid</label></li>
	                                <li class="checkBox positionRelative" ><input type="checkbox" class="mb0"  value="OverDue" name="paymentStatus"><label for="onboard4">Overdue</label></li>
	                                <li class="checkBox positionRelative" ><input type="checkbox" class="mb0" value="Unpaid" name="paymentStatus"><label for="onboard1">Unpaid</label></li>
			                        </ul>
							      </div>
						     </div> 
						  </div>
						</div>
						
						<div class="col-md-6">
						<div class="row">
						    <div class="col-md-12 mb">
								<label><strong>Status</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="statusFilter">
									<li class="checkBox positionRelative" ><input type="checkbox" class="mb0" value="DRAFT" name="invoiceStatus"><label for="onboard1">Draft</label></li>
                                    <li class="checkBox positionRelative" ><input type="checkbox" class="mb0" value="SENT" name="invoiceStatus"><label for="onboard2">Sent</label></li>
                                    <li class="checkBox positionRelative" ><input type="checkbox" class="mb0" value="RECEIVED" name="invoiceStatus"><label for="onboard1">Received</label></li>
	                                <li class="checkBox positionRelative" ><input type="checkbox" class="mb0"  value="ACCEPTED" name="invoiceStatus"><label for="onboard2">Accepted</label></li>
	                                <li class="checkBox positionRelative" ><input type="checkbox" class="mb0" value="REJECTED" name="invoiceStatus"><label for="onboard2">Rejected</label></li>
	                                <li class="checkBox positionRelative" ><input type="checkbox" class="mb0" value="LINKED" name="invoiceStatus"><label for="onboard2">Linked</label></li>
			                        </ul>
							      </div>
						     </div> 
						  </div>
						</div>
					  </div>
					  
					  
					  
					</div>
					<div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Apply" id="search_requiremet"  class="btn btn-sm btn-success" />
							<input type="button" value="Reset" id="clear_search" class="btn btn-sm btn-primary" />
						</div>
					</div>
				</div>



