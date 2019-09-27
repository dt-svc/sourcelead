<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
#scheduleDateValidation {
	padding: 0px 0px 0px 0px !important; margin: 0px; font-size: 11px; color: #f12929; font-weight: 500; text-align: center;
}
</style>
<script>
var vmsUrl ='${SourceLeadWeb_vmsUrl}';
var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
</script>
<script type="text/javascript">
    var invoiceType = '${invoiceType}';
$(document).ready(function(){
	getViewInvoice();
	if(invoiceType === "Receivable"){
		document.getElementById("invoiceIdHidden").style.display="block";
		document.getElementById("BillIdHidden").style.display="none";
		
		document.getElementById("invoiceIdBr").style.display="block";
		document.getElementById("BillIdBr").style.display="none";
		getInvoiceTagsList();
	}
	if(invoiceType === "Payables"){
		document.getElementById("BillIdHidden").style.display="block";
		document.getElementById("invoiceIdHidden").style.display="none";
		
		document.getElementById("invoiceIdBr").style.display="none";
		document.getElementById("BillIdBr").style.display="block";
		getBillsTagsList();
		
	}
	
});
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
     <div class="pull-right brud-crum" style="display:none" id="invoiceIdBr"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo; <a href="<c:url value='${SourceLeadWeb_vmsUrl}/invoiceList'/>">Receivable List</a> » Invoice View</div>
      <div class="pull-right brud-crum" style="display:none" id="BillIdBr" ><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo; <a href="<c:url value='${SourceLeadWeb_vmsUrl}/payablesList'/>">Payable List</a> » Bill View</div>
      
      <div class="" id="invoiceIdHidden" style="display:none">
          <h2 class="mb">Invoice Id: <span id="invoiceIdDiv"></span></h2>
<!--      =================== Tags related html code written by Laxman Start ============================ -->
			  <div class="newTagsWrap">
				    <ul class="clearfix tagsWrap tagsWrapBor">
					<select multiple class="chosen-select form-control" name="invoiceTagName" id="invoiceTagName" data-placeholder="Add Tags" data-validation="validate_Space" data-validation-optional="true">
					</select>
					</ul>
					<div class="clearfix"></div>
			  </div>
		   <div class="success_pop tagStyles" id="addInvoiceTagAlert">
				<i class="fa fa-check-circle"></i>
		   		<span></span>
		   </div>
		   <div class="remove_pop tagStyles" id="deleteInvoiceTagAlert">
				<i class="fa fa-times"></i>
		   		<span></span>
		   </div>
<!--      =================== Tags related html code written by Laxman Ends ============================ -->
		  
      </div>
      
      <div class="" id="BillIdHidden" style="display:none">
          <h2>Bill Id: <span id="billIdDiv"></span></h2>
 <!--      =================== Tags related html code written by Laxman Start ============================ -->
          <div class="cearfix">
			  <div class="newTagsWrap">
				    <ul class="clearfix tagsWrap tagsWrapBor">
					<select multiple class="chosen-select form-control" name="billstagName" id="billingTagName" data-placeholder="Add Tags" data-validation="validate_Space" data-validation-optional="true">
					</select>
					</ul>
					<div class="clearfix"></div>
			  </div>
		 </div>
		   <div class="success_pop tagStyles" id="addBillingTagAlert">
				<i class="fa fa-check-circle"></i>
		   		<span></span>
		   </div>
		   <div class="remove_pop tagStyles" id="deleteBillingTagAlert">
				<i class="fa fa-times"></i>
		   		<span></span>
		   </div>
<!--      =================== Tags related html code written by Laxman Ends ============================ -->
      </div>
      
     
    </section>
    
    
    <div class="content-area content-new clearfix">
      <div class="client_invoice">
        <div class="tabbable positionRelative">
          
          <ul class="nav nav-tabs" id="tab_bar">
            <li class="active">
              <a href="#bill" data-toggle="tab" title="Details" aria-expanded="true">
                <h5>Details</h5>
              </a>
            </li>
            <li class="">
            <sec:authorize access="hasAnyRole(\'ROLE_BILL_PAYMENT_HISTORY\',\'ROLE_VIEW_EDIT_INVOICE\')">
              <a href="#paymentHistory" data-toggle="tab" title="Payment History" aria-expanded="false" onclick="paymentHistroy();">
                <h5>Payment History</h5>
              </a>
               </sec:authorize>
            </li>
            
            
              <li class="">
<%--             <sec:authorize access="hasAnyRole(\'ROLE_BILL_PAYMENT_HISTORY\')"> --%>
              <a href="#notesList" data-toggle="tab" title="Notes" aria-expanded="false" onclick="invoiceNotesList();">
<%--               </sec:authorize> --%>
                <h5>Notes</h5>
              </a>
            </li>
            
             <li class="">
              <a href="#invoiceTimeline" data-toggle="tab" title="Notes" aria-expanded="false" onclick="timelineHistoryFunction('', 'desc', null, null, null);">
                <h5>Timeline</h5>
              </a>
            </li>        
            
          </ul>
          <div class="tab-content"> 
            
            <!-- Employee Details -->
            <div class="tab-pane active in" id="bill">
              <div id="viewInvoiceId"></div>
              </div>
          
            <div class="tab-pane fade positionRelative" id="paymentHistory">
                <div class="invoiceview_area clearfix candidate_wrap_table filter_position">
                  <div id="paymentHistorytId"></div>
                    
              </div>
            </div>
            
            <div class="tab-pane fade positionRelative" id="notesList">
                <div class="invoiceview_area clearfix candidate_wrap_table filter_position">
                  <div id="notesListId"></div>
                    
              </div>
            </div>
            
            <div class="tab-pane fade positionRelative" id="invoiceTimeline">
				<div class="appliWrap">
				<div class="timelineOut">
					<div class="clearfix">
						 <div class="headActions">
							<ul class="clearfix">
								<li><a href="javascript:void(0)" class="sortIcon" id="sortTimeline"></a></li>
								<li><a href="javascript:void(0)"><i class="filtIcon" onclick="openPop();"></i></a></li>
								<li><input class="innSeaFld" id="invoiceTimelineSearch" placeholder="Status/Reason" type="text"></li>	
							</ul>
						</div> 
					</div>
					<div id="dynamicTimelineHistoryAppend" class="timelineWrap">
					</div>
				</div>
				</div>
				</div>
          </div>
        </div>
      </div>
    </div>
  </section>
  
  <div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search">
				<h5 class="headerAdsrch">Filters
					<a href="javascript:void(0)" class="close-div" onClick="closePop()"><i
						class="fa fa-close"></i></a>
				</h5>
				<div class="addInner newOver">
					<div class="row">
							
						<div class="col-md-7">
						<div class="row">
							 <div class="col-md-6 positionRelative mb">
									<div class="clearfix dynamic_search filterDynamicsearch">
										<input type="text" id="timelineStatusSearch" onkeyup="myManagersFunction()" placeholder="Timeline Status" title="Timeline Status">
										<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
									</div>
									 <div class="clearfix filterSearch-ul">
									<ul class="list-unstyled" id="timelineStatusFilter">
										<li class="checkBox positionRelative"><label class="paddingLeft mb5"><input name="timelineStatusCheck" value="Created" type="checkbox"> Created</label></li>
			                    	    <li class="checkBox positionRelative"><label class="paddingLeft mb5"><input name="timelineStatusCheck" value="Accepted" type="checkbox"> Accepted</label></li>
										<li class="checkBox positionRelative"><label class="paddingLeft mb5"><input name="timelineStatusCheck" value="Rejected" type="checkbox"> Rejected</label></li>
										<li class="checkBox positionRelative"><label class="paddingLeft mb5"><input name="timelineStatusCheck" value="Sent" type="checkbox"> Sent</label></li>
			               				<li class="checkBox positionRelative"><label class="paddingLeft mb5"><input name="timelineStatusCheck" value="Received" type="checkbox"> Received</label></li>
			               				<li class="checkBox positionRelative"><label class="paddingLeft mb5"><input name="timelineStatusCheck" value="Updated" type="checkbox"> Updated</label></li>
			               				<li class="checkBox positionRelative"><label class="paddingLeft mb5"><input name="timelineStatusCheck" value="Linked" type="checkbox"> Linked</label></li>
			               				<li class="checkBox positionRelative"><label class="paddingLeft mb5"><input name="timelineStatusCheck" value="Payment" type="checkbox"> Paid</label></li>
			               				<li class="checkBox positionRelative"><label class="paddingLeft mb5"><input name="timelineStatusCheck" value="Reminder" type="checkbox"> Reminder</label></li>
			               				<li class="checkBox positionRelative"><label class="paddingLeft mb5"><input name="timelineStatusCheck" value="Deleted" type="checkbox"> Deleted</label></li>
			               				
			                        </ul>
									 </div> 
							 </div> 
						
						<div class="col-md-6 positionRelative mb">
							<div class="clearfix dynamic_search filterDynamicsearch">
								<input type="text" id="timelineUsearsSearch" onkeyup="timelineUsearsFilter()" placeholder="Users" title="Users">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul">
									<ul class="list-unstyled" id="userNameDiv">
			                        </ul>
							 </div> 
						</div>

						
						</div>
						
						</div>
						
						<div class="col-md-3">
						<div class="col-md-12 mb">
							<label class="mb"><strong>Custom Date</strong></label>
							 <div class="clearfix"><input type="text" id="timelineFromDate" class="form-control calendar mb10" placeholder="from date"></div>
							  <div class="clearfix"><input type="text" id="timelineToDate" class="form-control calendar mb10" placeholder="to date"></div>
							 </div> 
						</div>						
						
						
					</div>
					</div>
					<div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Apply" id="search_requiremet"
								onclick="closePop();submitFilterActions();" class="btn btn-sm btn-success" />
							<input type="button" value="Reset" id="clear_search"
								onclick="restTimelineFileter();" class="btn btn-sm btn-primary" />
						</div>
					</div>
				</div>
<div class="modal-backdrop" style="display: none"></div>
<div id="recvPymntpopOnline" class="modal" style="display: none;">
    
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
var finalDateFormatEduUpdate = '';
if(locationDate.match("yyyy-MM-dd")){
    finalDateFormatEduUpdate = 'yy-mm-dd';
} else if(locationDate.match("dd-MM-yyyy")){
    finalDateFormatEduUpdate = 'dd-mm-yy'; 
} else if(locationDate.match("MM-dd-yyyy")){
    finalDateFormatEduUpdate = 'mm-dd-yy'; 
} else if(locationDate.match("yyyy-dd-MM")){
    finalDateFormatEduUpdate = 'yy-dd-mm'; 
}
function cancelbtn(){
    $("#recvPymntpopOnline").fadeOut(200);
    $('.modal-backdrop').fadeOut(200).removeClass('in');
}

function continuePaymentOnline() {
        var amount = $("[name=paymentAmountOnline][value=custom]").is(":checked") ? $("#customeAmountOnline").val() : $("#totalAmountOnline").val();
    if(amount === "") {
        showError("Enter payment amount");
        return;
    }
    $("#save_paymentOnline").attr("disabled", "disabled");
    $.post(sourceleadUrl+"/wepay-iframe-payment", {notes: $("#paymentMemoOnline").val(), paymentType: $("[name=paymentAmountOnline][value=custom]").is(":checked") ? 0 : 1, invoiceUUID: $("#paymentInfoOnline").data('invoiceid'), amount: amount}, function(data) {
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

$("#invoiceTimelineSearch").keypress(function(e) {
    if(e.which == 13) {
    	timelineHistoryFunction($("#invoiceTimelineSearch").val(),'desc',null, null, null);
    }
});
var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
var finalDateFormatPetitionSave = getJSDateFormat(locationDate);


$(document).ready(function(){
	    $("#timelineFromDate").datepicker({
	        numberOfMonths: 1,
	        dateFormat: finalDateFormatPetitionSave,
	        onSelect: function(selected) {
	          $("#timelineToDate").datepicker("option","minDate", selected)
	        }
	    });
	    $("#timelineToDate").datepicker({
	        numberOfMonths: 1,
	        dateFormat: finalDateFormatPetitionSave,
	        onSelect: function(selected) {
	           $("#timelineFromDate").datepicker("option","maxDate", selected)
	        }
	    }); 
});
 



function restTimelineFileter(){
	//$('#timelineFromDate, #timelineToDate').val("");
	$.datepicker._clearDate('#timelineFromDate');
	$.datepicker._clearDate('#timelineToDate');
	$('input[name="timelineStatusCheck"]:checkbox').attr('checked',false);
	timelineHistoryFunction('', 'desc', null, null,null);
	$('input[name="userNameCheck"]:checkbox').attr('checked',false);
}

function submitFilterActions(){
	var checkedStatus = [];
	$('input[name="timelineStatusCheck"]:checked').each(function() {
		   checkedStatus.push(this.value);
	});
	var fromDate = $("#timelineFromDate").val();
	var toDate = $("#timelineToDate").val();
	var dateRange = []; 
	dateRange[0] = fromDate;	
	dateRange[1] = toDate;
	var checkedNames = [];
	$('input[name="userNameCheck"]:checked').each(function() {
		checkedNames.push(this.value);
	});
	timelineHistoryFunction("",'desc',dateRange, checkedStatus,checkedNames);
}

function timelineHistoryFunction(searchVal, order,dateRange,checkedStatus,namesStatus){
	var invoiceUUID ='${invoiceUUID}';
	var locationId = '${SourceLeadWeb_orgLocationId}';
	$.ajax({
	  	type: "GET",
	    url: vmsUrl+"/getInvoiceTimelineHistory?"+tokenParamName+"="+tokenValue+"&locationId="+locationId+"&invoiceUUID="+invoiceUUID+"&searchVal="+searchVal+"&orderVal="+order+"&dateRange="+dateRange+"&checkedStatus="+checkedStatus+"&namesStatus="+namesStatus,
	    success: function(json) {
			var resultDiv = "";
			$("#dynamicTimelineHistoryAppend").empty();
			if(json.length != 0){
				var ownerNames = [];
				$("#dynamicTimelineHistoryAppend").empty();
				for(var i = 0;i<json.length;i++){
					var dayVal = json[i].dateValue;
					for(var j = i; j<json.length; j++,i++){
						var statusClass = (json[i].status == 'Draft' ? 'creatcolor' : json[i].status == 'Sent' ? 'sentcolor' : json[i].status == 'Received' ? 'creatcolor' : json[i].status == 'Updated' ? 'updacolor' : json[i].status == 'Accepted' ? 'sentcolor' : json[i].status == 'Rejected' ? 'colorRed' : json[i].status == 'Created' ? 'updacolor' : json[i].status == 'Reminder' ? 'sentcolor' : json[i].status == 'Payment updated' ? 'updacolor' : '');
						if(j==0){
							resultDiv = resultDiv + '<ul id="ulId"><div class="timeLiDate">'+json[i].dateValue+'</div>';
						}
						if(dayVal != json[j].dateValue){
							resultDiv = resultDiv + '</ul>';
							resultDiv = resultDiv + '<ul id="ulId"><div class="timeLiDate">'+json[i].dateValue+'</div>';
							dayVal = json[j].dateValue;
						}
				    	resultDiv = resultDiv + '<li>';
				    	resultDiv = resultDiv + '<div class="pull-left timeLiHead">';
				    	if(json[j].invoiceUUID != null){
				    		resultDiv = resultDiv + '<span class="'+statusClass+'">'+json[j].status+' <a href="<c:url value="/app/viewInvoice?invoiceUUID='+json[j].invoiceUUID+'&invoiceType=Receivable"/>" title="'+json[j].invoiceId+'"> '+json[j].invoiceId+'</a> by '+'</span><span>'+json[j].statusValue+'</span>';
				    	}else if(json[j].status == "Deleted"){
				    		resultDiv = resultDiv + '<span class="'+statusClass+'">'+json[j].status+'</span><span>'+json[j].statusBy+'</span><span>'+json[j].statusValue+'</span>';
				    		resultDiv = resultDiv + '<p class="timRes"> Reason :'+json[j].reason+'</p>';
				    	}else if(json[j].status == "Rejected"){
				    		resultDiv = resultDiv + '<span class="'+statusClass+'">'+json[j].status+'</span><span>'+json[j].statusBy+'</span><span>'+json[j].statusValue+'</span>';
				    		resultDiv = resultDiv + '<p class="timRes"> Reason : '+json[j].reason+'</p>';
				    	}else{
				    		resultDiv = resultDiv + '<span class="'+statusClass+'">'+json[j].status+'</span><span>'+json[j].statusBy+'</span><span>'+json[j].statusValue+'</span>';
				    	}
				    	resultDiv = resultDiv + '</div>';
				    	resultDiv = resultDiv + '<div class="timeLeft">'+json[j].timeValue+'</div>';
				    	if(json[j].invoicePaymentReminderStatus != null || json[j].status == 'Sent' || json[j].status == 'Received'){
				    		resultDiv = resultDiv + '<div class="timeLibtn pull-right clearfix" id="timeLineBtn">';
				    		if(json[j].invoicePaymentReminderStatus == "Paid" || json[j].invoicePaymentReminderStatus == "Reminder" || json[j].invoicePaymentReminderStatus == "Partial Paid"){
				    			if(json[j].emailAttachment != null){
				    				resultDiv = resultDiv + '<i class="attaIcon pull-left"></i>';		
				    			}
				    		}
				    		resultDiv = resultDiv + '<i class="arrDn pull-left ml5"></i>';
				    		resultDiv = resultDiv + '</div>';
				    		resultDiv = resultDiv + '<div class="clearfix"></div>';
				    		if(json[j].invoicePaymentReminderStatus == "Paid"){
				    			resultDiv = resultDiv + '<div class="timelineInn">';
				    			resultDiv = resultDiv + '<div class="mesText">';
				    			resultDiv = resultDiv + '<p>Payment Reference Number : '+json[j].paymentReferenceNum+'</p>';
				    			resultDiv = resultDiv + '<p>Payment On : '+json[j].paymentDate+'</p>';
				    			resultDiv = resultDiv + '<p>Payment Method : '+json[j].paymentMethod+'</p>';
				    			resultDiv = resultDiv + '<p>Payment Amount : '+json[j].paymentCurrency+' '+json[j].paymentAmount+'</p>';
				    			resultDiv = resultDiv + '<p>Payment Memo : '+(json[j].paymentComment != null ? json[j].paymentComment : "---")+'</p>';
				    			resultDiv = resultDiv + '</div>';
				    			if(json[j].emailAttachment != null){
					    			resultDiv = resultDiv + '<div class="attaWrap">';
					    			resultDiv = resultDiv + '<h2>Attachment</h2>';
					    			resultDiv = resultDiv + '<p class="pull-left" title="'+json[j].emailAttachment.name+'">'+json[j].emailAttachment.name+'</p>';
					    			resultDiv = resultDiv + "<span class='pull-right'> <i class=''><a class='glyphicon glyphicon-save' href=\'"+json[j].emailAttachment.fileUrl+"\' title='download' download></a><a class='fa fa-eye ml10' onClick='viewFile(\""+json[j].emailAttachment.fileUrl+"\",\""+json[j].emailAttachment.name+"\",\""+json[j].emailAttachment.content+"\");'title='preview'></a></i>";
					    			resultDiv = resultDiv + '</div>';
				    			}
				    			resultDiv = resultDiv + '</div>';
				    		}
				    		if(json[j].invoicePaymentReminderStatus == "Partial Paid"){
				    			resultDiv = resultDiv + '<div class="timelineInn">';
				    			resultDiv = resultDiv + '<div class="mesText">';
				    			resultDiv = resultDiv + '<p>Payment Reference Number : '+json[j].paymentReferenceNum+'</p>';
				    			resultDiv = resultDiv + '<p>Payment On : '+json[j].paymentDate+'</p>';
				    			resultDiv = resultDiv + '<p>Payment Method : '+json[j].paymentMethod+'</p>';
				    			resultDiv = resultDiv + '<p>Payment Amount : '+json[j].paymentCurrency+' '+json[j].paymentAmount+'</p>';
				    			resultDiv = resultDiv + '<p>Payment Memo : '+(json[j].paymentComment != null ? json[j].paymentComment : "---")+'</p>';
				    			resultDiv = resultDiv + '</div>';
				    			if(json[j].emailAttachment != null){
					    			resultDiv = resultDiv + '<div class="attaWrap">';
					    			resultDiv = resultDiv + '<h2>Attachment</h2>';
					    			resultDiv = resultDiv + '<p class="pull-left" title="'+json[j].emailAttachment.name+'">'+json[j].emailAttachment.name+'</p>';
					    			resultDiv = resultDiv + "<span class='pull-right'> <i class=''><a class='glyphicon glyphicon-save' href=\'"+json[j].emailAttachment.fileUrl+"\' title='download' download></a><a class='fa fa-eye ml10' onClick='viewFile(\""+json[j].emailAttachment.fileUrl+"\",\""+json[j].emailAttachment.name+"\",\""+json[j].emailAttachment.content+"\");'title='preview'></a></i>";
					    			resultDiv = resultDiv + '</div>';
				    			}
				    			resultDiv = resultDiv + '</div>';
				    		}
				    		if(json[j].invoicePaymentReminderStatus == "Reminder"){
				    			resultDiv = resultDiv + '<div class="timelineInn">';
				    			resultDiv = resultDiv + '<div class="mesText">';
				    			resultDiv = resultDiv + '<p> To : '+json[j].invoiceReminderEmailTo+'</p>';
				    			resultDiv = resultDiv + '<p> From : '+json[j].invoiceReminderEmailFrom+'</p>';
				    			resultDiv = resultDiv + '<p> Subject : '+json[j].invoiceReminderEmailSubject+'</p>';
				    			resultDiv = resultDiv + '<p>'+json[j].invoiceReminderEmailBody+'</p>';
				    			resultDiv = resultDiv + '</div>';
				    			if(json[j].emailAttachment != null){
					    			resultDiv = resultDiv + '<div class="attaWrap">';
					    			resultDiv = resultDiv + '<h2>Attachment</h2>';
					    			resultDiv = resultDiv + '<p class="pull-left" title="'+json[j].emailAttachment.name+'">'+json[j].emailAttachment.name+'</p>';
					    			resultDiv = resultDiv + "<span class='pull-right'> <i class=''><a class='glyphicon glyphicon-save' href=\'"+json[j].emailAttachment.fileUrl+"\' title='download' download></a><a class='fa fa-eye ml10' onClick='viewFile(\""+json[j].emailAttachment.fileUrl+"\",\""+json[j].emailAttachment.name+"\",\""+json[j].emailAttachment.content+"\");'title='preview'></a></i>";
					    			resultDiv = resultDiv + '</div>';
				    			}
				    			resultDiv = resultDiv + '</div>';
				    		}
				    		if(json[j].status == "Sent"){
				    			resultDiv = resultDiv + '<div class="timelineInn">';
				    			resultDiv = resultDiv + '<div class="mesText">';
				    			resultDiv = resultDiv + '<p> To : '+json[j].invoiceReminderEmailTo+'</p>';
				    			resultDiv = resultDiv + '<p> From : '+json[j].invoiceReminderEmailFrom+'</p>';
				    			resultDiv = resultDiv + '<p> Subject : '+json[j].invoiceReminderEmailSubject+'</p>';
				    			resultDiv = resultDiv + '<p>'+json[j].invoiceReminderEmailBody+'</p>';
				    			resultDiv = resultDiv + '</div>';
				    			resultDiv = resultDiv + '</div>';
				    		}
				    		if(json[j].status == "Received"){
				    			resultDiv = resultDiv + '<div class="timelineInn">';
				    			resultDiv = resultDiv + '<div class="mesText">';
				    			resultDiv = resultDiv + '<p> To : '+json[j].invoiceReminderEmailTo+'</p>';
				    			resultDiv = resultDiv + '<p> From : '+json[j].invoiceReminderEmailFrom+'</p>';
				    			resultDiv = resultDiv + '<p> Subject : '+json[j].invoiceReminderEmailSubject+'</p>';
				    			resultDiv = resultDiv + '<p>'+json[j].invoiceReminderEmailBody+'</p>';
				    			resultDiv = resultDiv + '</div>';
				    			resultDiv = resultDiv + '</div>';
				    		}
				    	}
				    	resultDiv = resultDiv + '<div class="clearfix"></div>';
				    	resultDiv = resultDiv + '</div>';
						resultDiv = resultDiv + '</li>';
						/* if(json[j].ownerName != null){
							json[j].statusValue = json[j].ownerName;
						} */
						ownerNames.push(json[j].statusValue);
					}
				}
				resultDiv = resultDiv + '</ul>';
				$("#dynamicTimelineHistoryAppend").append(resultDiv);
				var uniqueNames = [];
				var userNameValue = "";
				if(searchVal=='' && order=='desc' && dateRange == null && checkedStatus == null && namesStatus == null){
					$.each(ownerNames, function(i, el){
					    if($.inArray(el, uniqueNames) === -1){
					    	 uniqueNames.push(el);
					    	 userNameValue = userNameValue + '<li class="checkBox positionRelative"><input name="userNameCheck" value="'+el+'" type="checkbox">'+el+'</label></li>';
					    }
					});
					$("#userNameDiv").empty().append(userNameValue);
				}
			}
	      }
	});
}


function removeMask(){
	 $("#customeAmountOnline").removeAttr("readonly");
	/*  $(".allownumericwithdecimal").on("keypress keyup blur",function (event) {
	        $(this).val($(this).val().replace(/[^0-9\.]/g,''));
	            if ((event.which != 46 || $(this).val().indexOf('.') != -1) && (event.which < 48 || event.which > 57)) {
	                event.preventDefault();
	            }
	        }); */
	        
	 $("#customeAmountOnline").keydown(function (e) {
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
    $('#customeAmountOnline').attr("readonly","readonly");    
}
function getViewInvoice(){
	  var invoiceUUID ='${invoiceUUID}';
	  var invoiceType = '${invoiceType}';
	  var currencyCode = '${SourceLeadWeb_currencyCode}';
	  var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
		  if(locationList.match("yyyy-MM-dd")){
			  viewInvoiceDate = 'YYYY-MM-DD';
		  }if(locationList.match("dd-MM-yyyy")){
			  viewInvoiceDate = 'DD-MM-YYYY'; 
		  }if(locationList.match("MM-dd-yyyy")){
			  viewInvoiceDate = 'MM-DD-YYYY'; 
		  }if(locationList.match("yyyy-dd-MM")){
			  viewInvoiceDate = 'YYYY-DD-MM'; 
		  }
	  $.ajax({
	    	type: "GET",
	        url: vmsUrl+"/getViewInvoice?"+tokenParamName+"="+tokenValue+"&invoiceUUID="+invoiceUUID,
	        success: function(json) {
// 	        	var json = JSON.parse(data);
	        	var result = "";
	        	for (var i=0; i<json.data.length; i++)
	    		{  
	        		var report = json.data[i];
	        		$("#invoiceIdDiv").text(report.invoiceId)
	        		$("#billIdDiv").text(report.invoiceId)
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
	        		if(report.paymentFrequency != null && report.paymentFrequency != '' && report.paymentFrequency != 'undefined'){
	        			paymentFrequency = report.paymentFrequency;
	        		}else{
	        			paymentFrequency = "";
	        		}
	        		if(report.billingAddress != null && report.billingAddress != '' && report.billingAddress != 'undefined'){
	        			billingAddress = report.billingAddress
	        		}else{
	        			billingAddress = "";
	        		}
	        		if(report.clientContactEmail != null && report.clientContactEmail != '' && report.clientContactEmail != 'undefined'){
	        			clientContactEmail = report.clientContactEmail;
	        		}else{
	        			clientContactEmail = "";
	        		}
	        		if(report.convertAmount != null && report.convertAmount != '' && report.convertAmount != 'undefined'){
	        			convertAmount = report.convertAmount;
	        		}else{
	        			convertAmount = "--";
	        		}
                                if(report.dueAmount != null && report.dueAmount != '' && report.dueAmount != 'undefined' && parseFloat(report.dueAmount) > 0){
	        			dueAmount = parseFloat(report.dueAmount);
	        		}else{
	        			dueAmount = "0.0";
	        		}
	        		
	        		if(report.convertCurrencyCode != null && report.convertCurrencyCode != '' && report.convertCurrencyCode != 'undefined'){
	        			convertCurrencyCode = report.convertCurrencyCode;
	        		}else{
	        			convertCurrencyCode = "";
	        		}
	        		
	        		if(report.exchangeRate != null && report.exchangeRate != '' && report.exchangeRate != 'undefined'){
	        			exchangeRate = report.exchangeRate;
	        		}else{
	        			exchangeRate = "--";
	        		}
	        		
	        		if(report.currencyCode != null && report.currencyCode != '' && report.currencyCode != 'undefined'){
	        			currencyCode = report.currencyCode;
	        		}else{
	        			currencyCode = "--";
	        		}
					result = result +'<div class="invoiceview_area clearfix">';
					result = result +'<div class="form-horizontal row">';
					
					if(report.orgType == 'Guest'){
						if(invoiceType === "Receivable"){
					result = result +'<div class="col-md-3 col-md-offset-9">';
					result = result +'<a href="'+vmsUrl+'/getReceivableInvoiceByUUID?invoiceUUID='+report.invoiceUUID+'&projectId='+report.projectId+'"><i title="Edit details" class="fa fa-pencil cursorPointer pull-right mr"></i></a>';
					result = result +'</div>';
					}else if(invoiceType === "Payables"){
						result = result +'<div class="col-md-3 col-md-offset-9">';
						if(report.projectId != null && report.projectId != '' && report.projectId != 'undefined'){
						result = result +'<a href="'+vmsUrl+'/getInvoiceByUUID?invoiceUUID='+report.invoiceUUID+'&projectId='+report.projectId+'"><i title="Edit details" class="fa fa-pencil cursorPointer pull-right mr"></i></a>';
						}else{
							result = result +'<a href="'+vmsUrl+'/getInvoiceByUUID?invoiceUUID='+report.invoiceUUID+'"><i title="Edit details" class="fa fa-pencil cursorPointer pull-right mr"></i></a>';
						}
						result = result +'</div>';
					}
					}
					result = result +'<div class="clearfix"></div>';
					
					result = result +'<div class="col-md-4"> ';	
					result = result +'<div class="col-md-12"> ';
					result = result +'<div class="row"> ';
					if(invoiceType == "Receivable"){
						result = result +' <label class="col-md-4 control-label">Client :</label>';
					}
					if(invoiceType == "Payables"){
						result = result +'<label class="col-md-4 control-label">Vendor :</label>                ';
					}
					result = result +'<div class="col-md-8"> ';
					if(invoiceType == "Receivable"){
					'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT\')">';
					result = result +'<label><strong> <a href="'+vmsUrl+'/clientDashboard?orgRelationUUID='+report.orgRelationUUID+'" title="'+report.clientName+'">'+report.clientName+' </a></strong></label>  ';
					'</sec:authorize>';
					}
					
					if(invoiceType == "Payables"){
					'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_VENDOR\')">';
					result = result +'<label><strong> <a href="'+vmsUrl+'/vendorsList?orgRelationUUID='+report.orgRelationUUID+'" title="'+report.clientName+'">'+report.clientName+' </a></strong></label>  ';
					'</sec:authorize>';
					}
					'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_VENDOR\')">';
					result = result +'<label><strong> '+report.clientName+'</strong></label>  ';
					'</sec:authorize>';
					
					result = result +'</div>                                                               ';
					result = result +'</div>';
					result = result +'</div>                                                                 ';
					
					if(report.invoiceStatus == "Rejected"){
					result = result +'            <div class="col-md-12">';
					result = result +'            <div class="row">';
					result = result +'              <label class="col-md-4 control-label paddig">Invoice Status :</label>        ';
					result = result +'              <div class="col-md-8 addressInvoice">                                ';
					result = result +'                <p>'+report.invoiceStatus+'</p>';
					result = result +'              </div>';
					result = result +'              </div>';
					result = result +'            </div>';
					}
					result = result +'<div class="col-md-12 mt5">                                                 ';
					result = result +'            <div class="row">';
					result = result +'<label class="col-md-4 control-label">Contact :</label>         ';
					result = result +'<div class="col-md-8">                                               ';
					result = result +'<label>'+report.clientContactName+'</label>';
					result = result +'              </div>                                                               ';
					result = result +'            </div>';
					result = result +'            </div>';
					result = result +'            </div>';
					/* result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Contact Email :</label>           ';
					result = result +'              <div class="col-md-8">                                               ';
					result = result +'                <label>'+clientContactEmail+'</label>                         ';
					result = result +'              </div>                                                               ';
					result = result +'            </div>                                                                 '; */
					result = result +'            <div class="col-md-3">';
					if(invoiceType == "Payables"){
						result = result +'            <div class="row">';
						result = result +'              <label class="col-md-4 control-label">Bill Id :</label>             ';
						result = result +'              <div class="col-md-8">                                               ';
						result = result +'                <label>'+report.invoiceId+'</label>                                          ';
						result = result +'              </div>                                                               ';
						result = result +'            </div>                                                                 ';
						result = result +'            <div class="row  mt5 mb5">                                                 ';
						result = result +'              <label class="col-md-4 control-label">Bill Date :</label>           ';
						result = result +'              <div class="col-md-8">                                               ';
						result = result +'                <label>'+invoiceDateStr+'</label>                                          ';
						result = result +'              </div>                                                               ';
						result = result +'            </div>                                                                 ';
					}else{
						result = result +'            <div class="row">                                                 ';
						result = result +'              <label class="col-md-5 control-label">Invoice Id :</label>             ';
						result = result +'              <div class="col-md-7">                                               ';
						result = result +'                <label>'+report.invoiceId+'</label>                                          ';
						result = result +'              </div>                                                               ';
						result = result +'            </div>                                                                 ';
						result = result +'            <div class="row  mt5 mb5">                                                 ';
						result = result +'              <label class="col-md-5 control-label">Invoice Date :</label>           ';
						result = result +'              <div class="col-md-7">                                               ';
						result = result +'                <label>'+invoiceDateStr+'</label>                                          ';
						result = result +'              </div>                                                               ';
						result = result +'            </div>                                                                 ';
					}
					
					result = result +'            <div class="row">                                                 ';
					result = result +'              <label class="col-md-5 control-label">Payment Term :</label>           ';
					result = result +'              <div class="col-md-7">                                               ';
					result = result +'                <label>'+paymentFrequency+'</label>                                             ';
					result = result +'              </div>                                                               ';
					result = result +'            </div>';
					
					result = result +'            </div>';
					
					result = result +'            <div class="col-md-5">';
					
					result = result +'            <div class="row">';
					result = result +'              <label class="col-md-5 control-label">Due Date :</label>               ';
					result = result +'              <div class="col-md-7">                                               ';
					result = result +'                <label>'+dueDateStr+'</label>                                          ';
					result = result +'              </div>                                                               ';
					result = result +'            </div>                                                                 ';
                                        if(report.invoiceCategory != "expenses" || invoiceType == "Payables"){
                                            result = result +'            <div class="row mt5 mb5">                                                 ';
                                            result = result +'              <label class="col-md-5 control-label">Due Amount :</label>               ';
                                            result = result +'              <div class="col-md-7">';
                                            result = result +'                <label>'+( typeof dueAmount === 'number' ? dueAmount.toFixed(2) : dueAmount)+'</label>';
                                            result = result +'                <label>'+(report.invoiceStatus == "Accepted" && report.paymentAccountDTO != null && typeof dueAmount === 'number' && report.paymentAccountDTO.accountType == "WEPAY" && invoiceType === "Payables" ? "<span class='receiveBtn pull-left' onclick='initPaymentOnline(\""+invoiceUUID+"\", "+dueAmount+");'>Pay</span>" : "")+'</label>';
                                            result = result +'              </div>                                                               ';
                                            result = result +'            </div>';
                                        }
					if(invoiceType == "Payables"){
						result = result +'            <div class="row">                                                 ';
						result = result +'              <label class="col-md-5 control-label">Billing Address :</label>        ';
						result = result +'              <div class="col-md-7 addressInvoice">                                ';
						result = result +'                <p>'+billingAddress+'</p>';
						result = result +'              </div>';
						result = result +'            </div>';
					}else{
						result = result +'            <div class="row  clearfix mt5 mb5">';
						result = result +'              <label class="col-md-5 control-label">Invoice Address :</label>        ';
						result = result +'              <div class="col-md-7 addressInvoice">                                ';
						result = result +'                <p>'+billingAddress+'</p>';
						result = result +'              </div>';
						result = result +'            </div>';
					}
					if(report.notifyToOthers != null && report.notifyToOthers !='' && report.notifyToOthers !='undefined'){
					result = result +'            <div class="row">                                                 ';
					result = result +'              <label class="col-md-5 control-label">Notify to Others :</label>        ';
					result = result +'              <div class="col-md-7 addressInvoice">                                ';
					
					result = result +'              <div class="notifyMails">';
					result = result +'                <p class="mr10 lineHeight">'+report.notifyToOthers+'</p>';
					result = result +'              </div>';
					result = result +'              </div>';
					result = result +'            </div>';
					}
					
					
					result = result +'          </div>';
					result = result +'          </div>';
					result = result +'      </div>';
					
					if(report.paymentAccountDTO != null){
					result = result +  '<h4 class="accDetHead">Account Details</h4>';
					result = result +'<div class="invoiceview_area clearfix">';
					result = result +'<div class="form-horizontal row">';
					
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Account Type :</label>        ';
					result = result +'              <div class="col-md-8 addressInvoice">                                ';
					result = result +'                <p>'+report.paymentAccountDTO.accountType+'</p>';
					result = result +'              </div>';
					result = result +'            </div>';
					if(report.paymentAccountDTO.accountType == "WEPAY"){
						result = result +'            <div class="col-md-6">                                                 ';
						result = result +'              <label class="col-md-4 control-label">Email :</label>        ';
						result = result +'              <div class="col-md-8 addressInvoice">                                ';
						result = result +'                <p>'+report.paymentAccountDTO.email+'</p>';
						result = result +'              </div>';
						result = result +'            </div>';
					}else{
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Bank Name :</label>        ';
					result = result +'              <div class="col-md-8 addressInvoice">                                ';
					result = result +'                <p>'+report.paymentAccountDTO.bankName+'</p>';
					result = result +'              </div>';
					result = result +'            </div>';
					
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Account Number :</label>        ';
					result = result +'              <div class="col-md-8 addressInvoice">                                ';
					result = result +'                <p>'+report.paymentAccountDTO.accountNumber+'</p>';
					result = result +'              </div>';
					result = result +'            </div>';
					
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">IFSC Code :</label>        ';
					result = result +'              <div class="col-md-8 addressInvoice">                                ';
					result = result +'                <p>'+report.paymentAccountDTO.routingNumber+'</p>';
					result = result +'              </div>';
					result = result +'            </div>';	
						
					}
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">First Name :</label>        ';
					result = result +'              <div class="col-md-8 addressInvoice">                                ';
					result = result +'                <p>'+report.paymentAccountDTO.firstName+'</p>';
					result = result +'              </div>';
					result = result +'            </div>';					
					
					
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Last Name :</label>        ';
					result = result +'              <div class="col-md-8 addressInvoice">                                ';
					result = result +'                <p>'+report.paymentAccountDTO.lastName+'</p>';
					result = result +'              </div>';
					result = result +'            </div>';					
					
					
					result = result +'          </div>';
					result = result +'      </div>';
					}
					
					/* ======================== InvoiceItem List start============================ */
					result = result +'	     <div class="invoiceGridview clearfix">                                  ';
					result = result +'         <table width="100%" cellpadding="0" cellspacing="0">                  ';
					result = result +'             <thead>                                                           ';
					result = result +'                 <tr>                                                          ';
					if(report.invoiceCategory != null && report.invoiceCategory != '' && report.invoiceCategory != 'undefined' && report.invoiceCategory == "expenses"){
                        result = result +'<th>Date</th>';
                        result = result +'<th>Category</th>';
                        result = result +'<th>Description</th>';
                        result = result +'<th>Quantity</th>';
                        result = result +'<th>Rate</th>';
                        result = result +'<th>Amount</th>';
                    }else{
                        result = result +'                     <th>Id</th> ';
                        result = result +'                     <th>Associates</th>';
                        if(report.isJobClosure == 'Y'){
                        result = result +'                     <th>Job Name</th>';	
                        }
                        result = result +'                     <th>Description</th>';
                        result = result +'                     <th>SOW/PO</th>                                          ';
                        if(report.isJobClosure == null && report.isJobClosure == '' && report.isJobClosure == 'undefined'){
                        result = result +'                     <th>Date</th>                                             ';
                        }if(report.isJobClosure == 'Y'){
                        	result = result +'                     <th>Offer Rate</th>                                       ';
                            result = result +'                     <th>Bill Rate</th>                                      ';		
                        }else{
                        	result = result +'                     <th>Rate Hourly</th>                                       ';
                            result = result +'                     <th>Total Hours</th>                                      ';	
                        }
                        result = result +'                     <th>Total Amount</th>                                     ';
                    }
					result = result +'                 </tr>                                                         ';
					result = result +'             </thead>                                                          ';
					result = result +'             <tbody>                                                           ';
					for (var j=0; j<report.invoiceLineItemsList.length; j++)
		    		{
						var items = report.invoiceLineItemsList[j]; 
						if(items.candidateName != null && items.candidateName != '' && items.candidateName != 'undefined'){
							candidateName = items.candidateName;
						}else{
							candidateName = "--";
						}
						if(items.inlineDescription != null && items.inlineDescription != '' && items.inlineDescription != 'undefined'){
                        	inlineDescription = items.inlineDescription;
                        }else{
                        	inlineDescription = "--";
                        }
						if(items.sowName != null && items.sowName != '' && items.sowName != 'undefined'){
							sowName = items.sowName;
						}else{
							sowName = "--";
						}
						if(items.startDateStr != null && items.startDateStr != '' && items.startDateStr != 'undefined'){
							startDateStr = items.startDateStr;
						}else{
							startDateStr = "-";
						}
						if(items.endDateStr != null && items.endDateStr != '' && items.endDateStr != 'undefined'){
							endDateStr = 'TO ' +items.endDateStr;
						}else{
							endDateStr = "-";
						}
						if(items.rate != null && items.rate != '' && items.rate != 'undefined'){
							rate = items.rate;
						}else{
							rate = "--";
						}
						if(items.totalHours != null && items.totalHours != '' && items.totalHours != 'undefined'){
							totalHours = items.totalHours;
						}else{
							totalHours = "--";
						}
						if(items.amount != null && items.amount != '' && items.amount != 'undefined'){
							amount = items.amount;
						}else{
							amount = "--";
						}
						if(items.employeeId != null && items.employeeId != '' && items.employeeId != 'undefined'){
							employeeId = items.employeeId;
						}else{
							employeeId = "--";
						}
						
						if(items.expenseCategory != null && items.expenseCategory != '' && items.expenseCategory != 'undefined'){
                            expenseCategory = items.expenseCategory;
                        }else{
                            expenseCategory = "--";
                        }
                        
                        if(items.quantity != null && items.quantity != '' && items.quantity != 'undefined'){
                            quantity = items.quantity;
                        }else{
                            quantity = "--";
                        }
                        result = result +'                 <tr>                                                          ';
                        
                        if(report.invoiceCategory != null && report.invoiceCategory != '' && report.invoiceCategory != 'undefined' && report.invoiceCategory == "expenses"){
                            result = result +'<td>';
                            result = result +'<div title="'+startDateStr+'">'+startDateStr+'</div>';
                            result = result +'</td>';
                            
                            result = result +'<td>';
                            result = result +'<div class="tbldata-dotdot_50" title="'+expenseCategory+'">'+expenseCategory+'</div>';
                            result = result +'</td>';
                        }else{
                            result = result +'                     <td>                                                      ';
                            result = result +'                         <div class="tbldata-dotdot_50" title="'+employeeId+'">'+employeeId+'</div>       ';
                            result = result +'                     </td>                                                     ';
                            result = result +'                     <td>                                                      ';
                            result = result +'                         <div class="tbldata-dotdot-sm" title="'+candidateName+'">'+candidateName+'</div>       ';
                            result = result +'                     </td>                                                     ';
                            if(report.isJobClosure == 'Y'){
                            	result = result +'                     <td>                                                      ';
                                result = result +'                         <div class="tbldata-dotdot-sm" title="'+items.requirementName+'">'+items.requirementName+'</div>       ';
                                result = result +'                     </td>                                                     ';	
                            }
                        }
                        result = result +'<td>';
                        result = result +'<div class="tbldata-dotdot_50" title="'+inlineDescription+'">'+inlineDescription+'</div>';
                        result = result +'</td>';
//                         result = result +'                     <td>                                                     ';
//                         result = result +'                         <div class="tbldata-dotdot_80" title="'+quantity+'">'+quantity+'</div>       ';
//                         result = result +'                     </td>                                                     ';
                        
                        if(report.invoiceCategory != null && report.invoiceCategory != '' && report.invoiceCategory != 'undefined' && report.invoiceCategory == "expenses"){
                        	 result = result +'                     <td>                                                      ';
                             result = result +'                         <div class="tbldata-dotdot_50" title="'+quantity+'">'+quantity+'</div>       ';
                             result = result +'                     </td>                                                     ';
                             
                        	result = result +'                     <td>                                                      ';
                            result = result +'                         <div class="tbldata-dotdot_50" title="'+rate+'">'+rate+'</div>       ';
                            result = result +'                     </td>                                                     ';
                            
                            result = result +'                     <td>                                                      ';
                            result = result +'                         <div class="tbldata-dotdot_80" title="'+amount+'">'+amount+'</div>       ';
                            result = result +'                     </td>                                                     ';
                        }else{
                            result = result +'                     <td>                                                      ';
                            result = result +'                         <div class="tbldata-dotdot_80" title="'+sowName+'">'+sowName+'</div>       ';
                            result = result +'                     </td>                                                     ';
                            if(report.isJobClosure == null && report.isJobClosure == '' && report.isJobClosure == 'undefined'){
                            result = result +'                     <td>                                                      ';
                            result = result +'                         <div class="tbldata-dotdot_80" title="'+startDateStr+'  '+endDateStr+'" >'+startDateStr+' &nbsp; '+ endDateStr+'</div>       ';
                            result = result +'                     </td>                                                     ';
                            }
                            result = result +'                     <td>                                                      ';
                            result = result +'                         <div class="tbldata-dotdot_50" title="'+rate+'">'+rate+'</div>       ';
                            result = result +'                     </td>                                                     ';
                            if(report.isJobClosure == 'Y'){
                           	result = result +'                     <td>                                                      ';
                            result = result +'                         <div class="tbldata-dotdot_80">'+quantity+'</div>       ';
                            result = result +'                         <div class="tbldata-dotdot_80">'+items.billRateType+'</div>       ';
                            result = result +'                     </td>                                                     '; 	
                            }else{
                            result = result +'                     <td>                                                      ';
                            result = result +'                         <div class="tbldata-dotdot_80">'+totalHours+'</div>       ';
                            result = result +'                     </td>                                                     ';
                            }
                            result = result +'                     <td>                                                      ';
                            result = result +'                         <div class="tbldata-dotdot_80" title="'+amount+'">'+amount+'</div>       ';
                            result = result +'                     </td>                                                     ';
                        }
                        result = result +'                 </tr>                                                         ';
						
						
						
	    		}
				result = result +'</tbody>';
				result = result +'</table>';
				result = result +' </div>';
				/* ======================== InvoiceItem List End============================ */
				
				if(report.invoiceComment != null && report.invoiceComment != '' && report.invoiceComment != 'undefine'){
					invoiceComment = report.invoiceComment;
				}else{
					invoiceComment = "--";
				}
				if(report.discountAmount != null && report.discountAmount != '' && report.discountAmount != 'undefine'){
					discountAmount = report.discountAmount;
				}else{
					discountAmount = "0";
				}
				
				if(report.discountValue != null && report.discountValue != '' && report.discountValue != 'undefine'){
					discountValue = '-'+report.discountValue;
				}else{
					discountValue = "0";
				}
				result = result +'			<div class="row">';
				result = result +'	          <div class="col-md-5">';
				result = result +'	              <div class="col-md-12 mt">';
				result = result +'	                  <label><strong>Message</strong></label>  ';
				result = result +'	                  <div class="clearfix">                   ';
				result = result +'	                      <p>'+invoiceComment+'</p>   ';
				result = result +'	                  </div>                                                                                  ';
				result = result +'	              </div>                                                                                      ';
				result = result +'	              <div class="col-md-12 mt">                                                                  ';
				result = result +'	                <label><strong>Attachment:</strong></label>                                               ';
				if(report.invoiceDocumentsList != null && report.invoiceDocumentsList != '' && report.invoiceDocumentsList != 'undefined'){
					for (var j=0; j<report.invoiceDocumentsList.length; j++)
		    		{
						var doc = report.invoiceDocumentsList[j]; 
						result = result +'	                  <div class="clearfix attachfile_invoice">                                               ';
						result = result +'	                      <p class="pull-left" title="'+doc.fileName+'">'+doc.fileName+'</p>                                         ';
						result = result +" <span class='pull-right'> <i class=''><a class='glyphicon glyphicon-save' href=\'"+doc.fileUrl+"\' title='download' download></a><a class='fa fa-eye ml10' onClick='viewFile(\""+doc.fileUrl+"\",\""+doc.fileName+"\",\""+doc.fileContentType+"\");'title='preview'></a></i>";
						result = result +'	                  </div>                                                                                  ';
		    		}
				}
				
				result = result +'	              </div>                                                                                      ';
				result = result +'	          </div>                                                                                          ';
				result = result +'	                                                                                                          ';
				result = result +'	        <div class="col-md-7">                                                                            ';
				result = result +'	            <div class="form-horizontal billsubTotal">                                                    ';
				result = result +'	              <div class="form-group">                                                                    ';
				result = result +'	                <label class="col-md-7 text-right"><strong>Sub Total</strong></label>                   ';
				result = result +'	                <div class="col-md-5 text-right"><span class="pull-left">'+currencyCode+'</span><span><strong>'+report.subTotal+'</strong></span></div>                             ';
				result = result +'	              </div>                                                                                      ';
				result = result +'	              <!-- Discount UI -->                                                                        ';
				result = result +'	              <div class="form-group">                                                                    ';
				result = result +'	                <div class="col-md-7 select-field">                                                       ';
				result = result +'	                  <div class="row">                                                                       ';
				result = result +'	                    <div class="col-md-7 paddingRight">                                                   ';
				if(report.discountType=="percentage"){
					result = result +'	                        <label><strong>Discount %</strong></label>                                        ';
					result = result +'	                    </div>                                                                                ';
					result = result +'	                    <div class="col-md-5 paddingLeft">                                                    ';
					result = result +'	                      <label>'+discountAmount+'%</label>                                                                   ';
					result = result +'	                    </div>                                                                                ';
					result = result +'	                  </div>                                                                                  ';
				}else{
					result = result +'	                        <label><strong>Discount Value</strong></label>                                        ';
					result = result +'	                    </div>                                                                                ';
					result = result +'	                    <div class="col-md-5 paddingLeft">                                                    ';
					result = result +'	                      <label>'+discountAmount+'</label>                                                                   ';
					result = result +'	                    </div>                                                                                ';
					result = result +'	                  </div>                                                                                  ';
				}
				
				
				result = result +'	                </div>                                                                                    ';
				result = result +'	                <div class="col-md-5 text-right">                                                         ';
				result = result +'	                  <label>'+discountValue+'</label>                                                               ';
				result = result +'	                </div>                                                                                    ';
				result = result +'	              </div>                                                                                      ';
				
				
				if(report.invoiceTaxList != null && report.invoiceTaxList != '' && report.invoiceTaxList != 'undefined' ){
				
				result = result +'	                <div class="addtax_table">';
				result = result +'	                    <table class="table-bordered table-striped table-responsive" width="100%" cellspacing="0" cellpadding="0">';
				
				result = result +'	                    <thead>';
				result = result +'	                   <tr>                                                                   ';
				result = result +'                    	 <th class="text-center">Service Type</th>                                               ';
				result = result +'                       <th class="text-center">Flat / Percentage</th> ';
				result = result +'                     	 <th class="text-center">Value</th>           ';
				result = result +'                    	 <th class="text-center">Amount</th>          ';
				result = result +'	                  </tr>';
				result = result +'	                        </thead>';
				result = result +'	                        <tbody>';
				
				for (var k=0; k<report.invoiceTaxList.length; k++)
	    		{
					var tax = report.invoiceTaxList[k]; 
					if(tax.taxName != null && tax.taxName != '' && tax.taxName != 'undefined'){
						taxName = tax.taxName;
					}else{
						taxName = "--";
					}
					if(tax.taxValue != null && tax.taxValue !='' && tax.taxValue != 'undefined'){
						taxValue = tax.taxValue;
					}else{
						taxValue = "0"
					}
					
					if(tax.taxAmount != null && tax.taxAmount !='' && tax.taxAmount != 'undefined'){
						taxAmount = tax.taxAmount;
					}else{
						taxAmount = "0"
					}
					
					result = result +'	                            <tr>                                                                   ';
					result = result +'	                                <td>                                                               ';
					result = result +'	                                   <div class="tbldata-dotdot" title="'+taxName+'">                       ';
					result = result +'	                                        '+taxName+'                                               ';
					result = result +'	                                   </div>                                                          ';
					result = result +'	                                </td>                                                              ';
					result = result +'	                                <td>                                                               ';
					result = result +'	                                 <div class="btn-group tblminwidth-75">                            ';
					if(tax.taxType =="percentage"){
						result = result +'	                                        <label>Percentage</label>                               ';
					}else{
						result = result +'	                                        <label>Flat</label>                               ';
					}
					result = result +'	                                </div>                                                             ';
					result = result +'	                                </td>                                                              ';
					result = result +'	                                <td>                                                               ';
					result = result +'	                                    <div class="tblminwidth-75">                                   ';
					result = result +'	                                        <label>'+taxValue+'</label>                               ';
					result = result +'	                                    </div>                                                         ';
					result = result +'	                                </td>                                                              ';
					result = result +'	                                <td>                                                               ';
					result = result +'	                                    <div class="tblminwidth-75">                                   ';
					result = result +'	                                         <label>'+taxAmount+'</label>                                ';
					result = result +'	                                    </div>                                                         ';
					result = result +'	                                </td>                                                              ';
					result = result +'	                            </tr>                                                                  ';
	    		}
				result = result +'	                        </tbody>                                                                   ';
				result = result +'	                    </table>                                                                       ';
				result = result +'	            </div>                                                                                 ';
				
				}
				
				result = result +'	                <div class="form-group mt">                                                        ';
				result = result +'	                <label class="col-md-7 text-right"><strong>Total</strong></label>                  ';
				result = result +'	                    <div class="col-md-5 text-right"><span class="pull-left">'+currencyCode+'</span><span><strong>'+report.total+'</strong></span></div> ';
				result = result +'	              </div>                                                                               ';
				if(convertCurrencyCode != null && convertCurrencyCode != '' && convertCurrencyCode != 'undefined'){
					 result = result +'  <div class="row">';
	                  result = result +'  <div class="clearfix mt col-md-offset-2 col-md-10" style="padding-right:0;">';
	                  
	                  result = result +'  <div class="clearfix"><label class="col-md-6 text-right"><strong>Conversion From</strong></label><div class="paddingLeft col-md-6"><span>'+currencyCode+'</span><div class="pull-right"><input class="ReadTextFli" readonly="" type="text">'+report.total+'</div></div></div>';
	                  result = result +'  <div class="clearfix"><label class="col-md-6 text-right"><strong>Conversion To</strong></label><div class="paddingLeft col-md-6"><span>'+convertCurrencyCode+'</span><div class="pull-right"><input  class="ReadTextFli" readonly="" type="text">'+convertAmount+'</div></div></div>';
	                  result = result +'  <div class="clearfix"><label class="col-md-6 text-right"><strong>Exchange Rate</strong></label><div class="paddingLeft col-md-6"><span>'+currencyCode+' to '+convertCurrencyCode+' </span><div class="pull-right"><input  class="ReadTextFli ReadTextFli90" readonly="" type="text">'+exchangeRate+'</div></div></div>';
	                  
	                  result = result +'  </div>';
	                  result = result +'  </div>'; 	
				}
				result = result +'	              </div>                                                                               ';
				
                  
				result = result +'	          </div>                                                                                   ';
				result = result +'	            </div>   ';
					
				 
	    		}
				$("#viewInvoiceId").empty();  
			    $("#viewInvoiceId").append(result);
			    $(".notifyMails").css({
			    	"min-height":"75px", "max-height":"120px", "overflow":"auto"
			    });
	        },
	        
	    });
}

</script>


<script>
function viewInvoiceDocView(documentId,title){
	if(documentId != null && documentId != ""){
		window.open(vmsUrl+'/viewInvoiceDocView/'+title+'?documentId='+documentId,title,"height=600,width=600");
	}  
}
</script>


<script>
function invoiceNotesList(){
	var result = "";
	result = result  +'<div class="timesheet_head">';
	result = result  +'    <div class="project_tabs_inn_head addUser-org"> '; 
	result  = result + '<div class="success_pop" id="vendorDocSelector" style="display: none;">';
    result  = result + '<i class="fa fa-check-circle"></i><span id="vendorDocMsgDiv"></span>';
    result  = result + '</div>';
	    result = result + '<ul class="padding_right">';
	   result = result  +'    	<a href="javascript:void(0)" onClick="addNotes();" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Note</a>                                                            ';
		result = result  +'     <div id="popUpDiv" class="edit-details col-md-4"> ';
		result = result  +'     </div>  ';
		
		result = result + '</ul>';
		result = result  +'  <div class="clearfix"></div> ';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		result  = result + '   <div id="vendorNotesListDiv" ></div>';
		result  = result + '   </div>';
	result = result+'	    </div>';
	result = result+'	    </div>';
	
	$("#notesListId").empty();  
    $("#notesListId").append(result)
    invoiceNotes();
    
}

</script>
<script>
function invoiceNotes(){
	var urlForm = "";
	  var invoiceUUID ='${invoiceUUID}';
    	urlForm = "/getNotesInvoice?"+tokenParamName+"="+tokenValue+"&invoiceUUID="+invoiceUUID;
	
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+urlForm,        
         success :function(data) {
	var output = "";
    if(data != null && data != 'undefined' && data != "" && data != '[]'){
    var json = JSON.parse(data);
//     '${SourceLeadWeb_user.id}' 

	 output  = output + '   <div class="project_tabs_inn_head addUser-org"> ';
	  output  = output + '              <div class="project_tabs_inn_table vendor_wrap_inn_table">';
     for(var i=0;i<json.length;i++){
       	var report = json[i];
       	if(report.checkBox == 'Y' || (report.checkBox == 'N' && '${SourceLeadWeb_user.id}' == report.userIdStr)){
   	  	output = output+'<div class="comments-div comments-inner commentsNotes clearfix">';
   	    output = output+'    <div class="commentsimg">';
   	    output = output+'    <i class="fa fa-comments"></i>';
   	    output = output+'        <div class="commentsimg-inner">';
   	    if(report.commentAuthorImage != null && report.commentAuthorImage != '' && report.commentAuthorImage != 'undefined'){
   	    output = output+'        	<img data-id= "userProfileImage" onerror="predefineProfileImage(this);" src="'+report.commentAuthorImage+'"/>';
   	    }else{
   	   	output = output+'        	<img data-id= "userProfileImage" onerror="predefineProfileImage(this);" src="/resources/images/userprofileImg.jpg"/>';
   	    }
   	    output = output+'        </div>';
   	    output = output+'    </div>';
   	    output = output+'     <div class="comments-content">';
   	    output = output+'     <div class="commentsNotes_inner">';
   	    output = output+'         <p class="comment-by">';
   	    output = output+'            By <span class="text-danger" title = "'+report.userName+'">'+report.userName+'</span>';
   	 
   	    output = output+'            <span class="small pull-right">'+report.createdDateString+'</span></p>';
   	    output = output+'         <p class="comment_text pull-left">'+report.comment+'</p>';
   	    output = output+'     </div>';
   	    output = output+'     </div>';
   	    output = output+'</div>';
    	}
     }
}else{
     var output ="";
    output = output+"   <div class='dataTables_empty'>"; 
	 output = output+"  </div> ";
}
output  = output + '              </div>';
output  = output + '        </div>';
	$("#vendorNotesListDiv").empty(); 
    $("#vendorNotesListDiv").append(output);
 },
         
      });
}
</script>

<script>
function closeDocPopup(){
	 $("#popUpDiv").removeClass("edit-details-active");
}
</script>

<script>
function addNotes() {	
	 var invoiceUUID ='${invoiceUUID}';
	var result = "";
	result = result  +'<!-- Popup Div -->';
	result = result  +'          <form role="form"  id="invoiceNotes_form" name="invoiceNotes_form" method="post" > ';
	result = result  +'          <input type="hidden" id="invoicdeId" name="invoiceUUID" value="'+invoiceUUID+'"> ';
	result = result  +'          <input type="hidden" id="noteType" name="noteType" value="Billing"> ';
	result = result  +'          <i class="fa fa-close close-div"  onClick="closeDocPopup();"></i> ';
	result = result  +'                <div class="form-horizontal clearfix"> ';
	result = result+'	                  <div class="clearfix">';
	 result = result +" <div class='col-md-12 inputOther inputOther'>";
    
	
     result = result +"    <div class='clearfix'>";
     result = result +"    <div class='dropdown-select mb10' id='clientCategoryContactTypeListDivDiv'>";
     result = result +"    </div>";
 	result = result  +'                <div class="form-horizontal clearfix"> ';
	result = result+'	                  <div class="clearfix">';
	result = result+'	                      <textarea rows="2" placeholder="Note" value="" id="noteDescription" name="noteDescription" data-validation="required validate_length length1-5000"  field-name="Note" class="form-control"></textarea>';
	result = result+'	                  </div>';
	result = result  +'                  </div>';
	
	result = result+' <div class="row clearfix mt5"> ';
	result = result+' <div class="col-md-9 checkBox"> ';
	result = result+'   <input type="checkbox" value= "Y" id="allowAll" class="pull-left" name = "allowAll" checked="checked">';
	result = result + '	                    <p class="pull-left mt5">Allow all users</p>';
	result = result+'	                  </div>';
	
	result = result  +'                  <div class="col-md-3" id="docAddButton"> ';
	result = result  +'                  <div class="pull-right"> ';
    result = result +'                     <input type="button" value="Add" name="btnAddPr" id="save_note" class="btn btn-sm btn-primary" onClick ="storeInvoiceNotes();" />'; 
  /*   result = result +'                     <input type="button" value="Cancel" name="btnAddPr" id="save_note" class="btn btn-sm btn-default" onClick ="storeClientNotes('+orgRelationId+');" />'; */ 
	result = result  +'                  </div> ';
	result = result  +'                  </div> ';
	result = result  +'                  </div> ';
	
	
	
	result = result  +'                </div>';
	
	result = result  +'                </form>';
	
	$("#popUpDiv").empty();
	$("#popUpDiv").append(result);
	
	  $(".edit-details").toggleClass("edit-details-active");
		 $(".close-div").click(function(){ 
			 $(this).parent("div.edit-details").removeClass("edit-details-active");
			 });
		 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
			 e.stopPropagation();
		 });
		 
				
				
	}
</script>
<script type="text/javascript">
	function storeInvoiceNotes(){
		if ($('#invoiceNotes_form').validate(false, validationSettings)){ 
				
				var form = $('#invoiceNotes_form')[0];
	      		var formData = new FormData(form);
	      		var userId = '${SourceLeadWeb_user.id}';
	      		formData.append("userId",userId);
	      	    var locationCode ='${SourceLeadWeb_locationCode}'; 
	      	    formData.append("locationCode",locationCode);
	      			
				$("#save_note").attr("disabled", "disabled");
         		$("#save_note" ).addClass('button').val('Processing..');
				$.ajax({
						type : "POST",
		        	    data: formData,
		    	        processData: false,
		    	        contentType:false,
						url : sourceleadUrl+"/storeNotes?"+tokenParamName+"="+tokenValue+"&userId="+userId+"locationCode="+locationCode,
						success : function(result) {
							
							if (result && result.response === "INVALID_DATA") {
							    showError(result.responseMessage);
							} else if (result.response === "AWKWARD") {
							    location.href = "/errorPage";
							} else if (result.response === "SUCCESS") {
								closeDocPopup();
								invoiceNotes();
							} else if (result.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
							    showError("Operation failed, please try again");
							}
							
						},
					});
					$('.add-comment').toggle(200);
					return true;
			 
		}else {
			return false;	
		}
		}
		$('body').on('blur', '#invoiceNotes_form', function() {
		$("#invoiceNotes_form").showHelpOnFocus();
		$("#invoiceNotes_form").validateOnBlur(false, validationSettings);
		
	});
</script>
<script>
function paymentHistroy(){
	  var invoiceUUID ='${invoiceUUID}';
	  var locationId = '${SourceLeadWeb_orgLocationId}';
	  $.ajax({
	    	type: "GET",
	        url: vmsUrl+"/getViewPaymentHistory?"+tokenParamName+"="+tokenValue+"&invoiceUUID="+invoiceUUID+"&locationId="+locationId,
	        success: function(json) {
// 	        	var json = JSON.parse(data);
	        	var result = "";                                                                                                
				result = result +'       	<table width="100%" cellpadding="0" cellspacing="0" id="paymentHistoryPagination"> ';
				result = result +'           <thead>                                ';
				result = result +'               <tr>                               ';
				result = result +'                   <th> Date</th>                 ';
				result = result +'                   <th>  Payment Method </th>     ';
				result = result +'                   <th> Reference No </th>        ';
				result = result +'                   <th> Amount Received </th>     ';
				result = result +'                   <th> Attachment  </th>         ';
				result = result +'                   <th> Memo </th>                ';
				result = result +'                   <th> Created By </th>                ';
				result = result +'               </tr>                              ';
				result = result +'           </thead>                               ';
				result = result +'           <tbody>                                ';
				for (var i=0; i<json.data.length; i++)
	    		{  
	        		var report = json.data[i];
	        		if(report.paymentAmount != null && report.paymentAmount !='' && report.paymentAmount != 'undefined'){
	        			paymentAmount = report.paymentAmount;
	        		}else{
	        			paymentAmount = "0"
	        		}
	        		if(report.fileName != null && report.fileName != '' && report.fileName != 'undefined'){
	        			fileName = report.fileName;
	        		}else{
	        			fileName = "--"
	        		}
	        		if(report.paymentComment != null && report.paymentComment != '' && report.paymentComment != 'undefined'){
	        			paymentComment = report.paymentComment;
	        		}else{
	        			paymentComment = "--";
	        		}
					result = result +'           <tr>   ';
					result = result +'              <td><div class="tbldata-dotdot-100">'+report.paymentDateStr+' </div></td>     ';
					result = result +'              <td><div class="tbldata-dotdot-50">'+report.paymentMethod+'</div></td>            ';
					result = result +'              <td><div class="tbldata-dotdot-50" title="'+report.paymentReferenceNumber+'">'+report.paymentReferenceNumber+'</div>              ';
					result = result +'              </td><td><div class="tbldata-dotdot-50" title="'+report.currencyCode+' '+paymentAmount+'">'+report.currencyCode+' &nbsp;&nbsp; '+paymentAmount+'</div></td> ';
					result = result +'              <td>                                                          ';
					result = result +'                <a class="tbldata-dotdot-vsm download_attachment" title="Download Attachment">      ';
// 					result = result +'                <p class="pull-left">'+fileName+'</p>                           ';
					if(report.fileURL != null && report.fileURL != '' && report.fileURL != 'undefined'){
// 					result = result +" <span class='pull-right'> <i class=''><a class='glyphicon glyphicon-save' href=\""+sourceleadUrl+"/downloadInvoicePaymentDocument?paymentHistoryId="+report.id+"\"></a></i>";
					result = result+' <a download class="glyphicon glyphicon-download-alt" href="'+report.fileURL+'" title="Download"></a>';
					result = result+' <a class="fa fa-eye" href="javascript:void(0)" onClick="viewFile(\''+report.fileURL+'\',\''+report.fileName+'\',\''+report.fileContentType+'\');"title="preview"></a>';		           						          
					}else{
						result = result +" --";	
					}
					result = result +'                </a>                                ';
					result = result +'              </td>                                 ';
					result = result +'              <td><div class="tbldata-dotdot-me">   ';
					result = result +'                      <a class="memo_text">         ';
					result = result +'                          <p class="pull-left" title="'+paymentComment+'">'+paymentComment+'</p>';
					result = result +'                         <i class="fa fa-comments pull-right" title="Memo"></i>  ';
					result = result +'                      </a> ';
					result = result +'                  </div>   ';
					result = result +'              </td>        ';
					
					result = result +'              <td><div class="tbldata-dotdot-100">'+report.createdByName+' </div></td>     ';
					result = result +'            </tr>          '; 
	    		}
				result = result +'           </tbody>        ';
				result = result +'       </table>  ';     
				$("#paymentHistorytId").empty();  
			    $("#paymentHistorytId").append(result);
			    paymentHistoryPagination()
	        }
	  });
}

</script>
<script>
function paymentHistoryPagination(){  
    $('#paymentHistoryPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>

<script>


$(document).ready(function(){
	$(document).on("click","#timeLineBtn",function() {	
		$(this).parent().find(".timelineInn").slideToggle(100);
	});	
});	


/* $(document).ready(function(){
	$("#timeLineBtn").click(function(){
		$(".timelineInn").slideUp(100); 
 		alert("fdfsd");
		$(this).parents().find(".timelineInn").slideToggle(100);
	});	
}); */

</script>

<script type="text/javascript" src="https://www.wepay.com/min/js/iframe.wepay.js">



<!--  ============================= Invoice tags related scripts written by Laxman Start ================================ -->
<script>

</script>
<script>
var selectOne, chosenOne;
selectOne = $("#invoiceTagName");
selectOne.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
chosenOne = selectOne.data('chosen');
chosenOne.search_field.on('keyup', function(e)
{

	var anyKeyword = this.value.replace(",","");
    if ((e.which == 13 || e.which == 188) && (anyKeyword != null  && anyKeyword != '' && anyKeyword != 'undefined'))
    {
    	
    	var par = this.parent;
        var option = $("<option>").val(anyKeyword).text(anyKeyword);
        selectOne.append(option);
        selectOne.find(option).prop('selected', true);
        $("#invoiceTagName option").each(function(){
              $(this).siblings("[value='"+ this.value+"']").remove();
        });
		$("#invoiceTagName").trigger("chosen:updated");
		saveInvoiceTags("add");
    }
});

$('.chosen-select').chosen().on('change', function(evt, params) {
	if (params.selected && params.selected != undefined) {
		saveInvoiceTags("add");
	}
	if (params.deselected && params.deselected != undefined) {
		saveInvoiceTags("delete");
	}
	
});

$("#invoiceTagName_chosen").find('ul.chosen-choices > li input').attr("placeholder", "Add Tag");

function getInvoiceTagsList(){
	var invoiceUUID ='${invoiceUUID}';
	$.ajax({  
        type : "POST",
        url: sourceleadUrl+"/getInvoiceTags?"+tokenParamName+"="+tokenValue+"&invoiceUUID="+invoiceUUID,        
        success :function(json) {
        	var result = "";
        	if(json.tagsList !=null && json.tagsList !='' && json.tagsList !='undefined'){
			$.each( json.tagsList, function( i, tags ) {
			  var existed = 0;
                if(json.invoiceTags != null && json.invoiceTags != 'undefined' && json.invoiceTags != '') {
	                var res = [];
	                res = json.invoiceTags.split(",");
	                res.forEach(function(item) {
	                	if(tags == item){
	                		existed = 1;
	                		result = result + ' <option selected value="'+tags+'">'+tags+'</option>';
	                	}
	                });
                }
                if(existed == 0){
                	result = result + ' <option value="'+tags+'">'+tags+'</option>';
                }
			});
        	}
            $("#invoiceTagName").empty();
            $("#invoiceTagName").append(result);
            $("#invoiceTagName").chosen().trigger("chosen:updated")

        },
	});
}
</script>
<script>
function saveInvoiceTags(type){
	var invoiceUUID ='${invoiceUUID}';
	 var tagName = $("#invoiceTagName").val();
	 var jsonData = encodeURIComponent(tagName);
	 var loginUserId = '${SourceLeadWeb_userId}';
	 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 if(tagName != null && tagName != '' && tagName !='undefined') {
		 Urlcheck = sourceleadUrl+"/saveOrUpdateTagsByInvoiceUUID?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&invoiceUUID="+invoiceUUID+"&tagName="+jsonData
	 } else {
		 Urlcheck = sourceleadUrl+"/saveOrUpdateTagsByInvoiceUUID?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&invoiceUUID="+invoiceUUID+"&tagName="+""
	 }
	
	 $.ajax({  
         type : "POST",
         url: Urlcheck,        
         success :function(result) {
        	$("#editRoleLoading").removeAttr('style');
			if(result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
        		showError(result.responseMessage);
        	} else if (result.response === "AWKWARD") {
        	    location.href = "/errorPage";
        	} else if (result.response === "SUCCESS") {
        		if(type == "add"){
        			showSuccessStatusPopup("Tags added successfully");
        		}else if(type == "delete"){
        			showDeleteStatusPopup("Tags deleted successfully");
        		}
        		getInvoiceTagsList(invoiceUUID);
        	} else if (result.indexOf('Sign In') !== -1) {
        		location.replace(sourceleadUrl);
        	} else {
        	    showError("Operation failed, please try again");
        	}
       },
    }); 
}
function showSuccessStatusPopup(status) {
	$("#addInvoiceTagAlert").css("display","block");
	$("#addInvoiceTagAlert span").html(status);
    $('#addInvoiceTagAlert').delay(2000).fadeOut('slow');
}
function showDeleteStatusPopup(status) {
	$("#deleteInvoiceTagAlert").css("display","block");
	$("#deleteInvoiceTagAlert span").html(status);
    $('#deleteInvoiceTagAlert').delay(2000).fadeOut('slow');
}
</script>
<!--  ============================= Invoice tags related scripts written by Laxman Ends ================================ -->




<!--  ============================= Bills tags related scripts written by Laxman Start ================================ -->
<script>
var select, chosen;
select = $("#billingTagName");
select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
chosen = select.data('chosen');
chosen.search_field.on('keyup', function(e)
{
	var anyKeyword = this.value.replace(",","");
    if ((e.which == 13 || e.which == 188) && (anyKeyword != null  && anyKeyword != '' && anyKeyword != 'undefined'))
    {
    	
    	var par = this.parent;
        var option = $("<option>").val(anyKeyword).text(anyKeyword);
        select.append(option);
        select.find(option).prop('selected', true);
        $("#billingTagName option").each(function(){
              $(this).siblings("[value='"+ this.value+"']").remove();
        });
		$("#billingTagName").trigger("chosen:updated");
		saveBillsTags("add");
    }
});

$('.chosen-select').chosen().on('change', function(evt, params) {
	if (params.selected && params.selected != undefined) {
		saveBillsTags("add");
	}
	if (params.deselected && params.deselected != undefined) {
		saveBillsTags("delete");
	}
	
});
$("#billingTagName_chosen").find('ul.chosen-choices > li input').attr("placeholder", "Add Tag")
</script>
<script>
function getBillsTagsList(){
	var invoiceUUID ='${invoiceUUID}';
	$.ajax({  
        type : "POST",
        url: sourceleadUrl+"/getBillsTags?"+tokenParamName+"="+tokenValue+"&invoiceUUID="+invoiceUUID,        
        success :function(json) {
        	var result = "";
			$.each( json.tagsList, function( i, tags ) {
			  var existed = 0;
                if(json.billsTags != null && json.billsTags != 'undefined' && json.billsTags != '') {
	                var res = [];
	                res = json.billsTags.split(",");
	                res.forEach(function(item) {
	                	if(tags == item){
	                		existed = 1;
	                		result = result + ' <option selected value="'+tags+'">'+tags+'</option>';
	                	}
	                });
                }
                if(existed == 0){
                	result = result + ' <option value="'+tags+'">'+tags+'</option>';
                }
			});
            $("#billingTagName").empty();
            $("#billingTagName").append(result);
            $("#billingTagName").chosen().trigger("chosen:updated")

        },
	});
}
</script>
<script>
function saveBillsTags(type){
	var invoiceUUID ='${invoiceUUID}';
	 var tagName = $("#billingTagName").val();
	 var jsonData = encodeURIComponent(tagName);
	 var loginUserId = '${SourceLeadWeb_userId}';
	 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 if(tagName != null && tagName != '' && tagName !='undefined') {
		 Urlcheck = sourceleadUrl+"/saveOrUpdateTagsByBillsId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&invoiceUUID="+invoiceUUID+"&tagName="+jsonData
	 } else {
		 Urlcheck = sourceleadUrl+"/saveOrUpdateTagsByBillsId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&invoiceUUID="+invoiceUUID+"&tagName="+""
	 }
	
	 $.ajax({  
         type : "POST",
         url: Urlcheck,        
         success :function(result) {
        	$("#editRoleLoading").removeAttr('style');
			if(result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
        		showError(result.responseMessage);
        	} else if (result.response === "AWKWARD") {
        	    location.href = "/errorPage";
        	} else if (result.response === "SUCCESS") {
        		if(type == "add"){
        			showSuccessStatusPopup("Tags added successfully");
        		}else if(type == "delete"){
        			showDeleteStatusPopup("Tags deleted successfully");
        		}
        		getBillsTagsList(invoiceUUID);
        	} else if (result.indexOf('Sign In') !== -1) {
        		location.replace(sourceleadUrl);
        	} else {
        	    showError("Operation failed, please try again");
        	}
       },
    }); 
}
function showSuccessStatusPopup(status) {
	$("#addBillingTagAlert").css("display","block");
	$("#addBillingTagAlert span").html(status);
    $('#addBillingTagAlert').delay(2000).fadeOut('slow');
}
function showDeleteStatusPopup(status) {
	$("#deleteBillingTagAlert").css("display","block");
	$("#deleteBillingTagAlert span").html(status);
    $('#deleteBillingTagAlert').delay(2000).fadeOut('slow');
}
</script>
<!--  ============================= Bills tags related scripts written by Laxman Ends ================================ -->


<script>
function newActions(){
	$(".newActFilter").slideToggle(100);
}
$("html").click(function(){
	$(".newActFilter").slideUp(100);
});
$(".filtIcon, .newActFilter").click(function(e){
 	e.stopPropagation();
});	
</script>

<script>
	// Close Advanced Search
		function closePop(){
			$("div.cand-advsrch-popup").hide();
			$(".mask").fadeOut();
		}
		function openPop(){
			$("div.cand-advsrch-popup").toggle();
			 $(".mask").fadeIn();
		}
		
		function closeAdsrch(){
			$("div.cand-advsrch-popup").fadeOut();
			$(".mask").fadeOut();
		}
		
		function myManagersFunction() {
		    var input, filter, ul, li, a, i;
		    input = document.getElementById("timelineStatusSearch");
		    filter = input.value.toUpperCase();
		    ul = document.getElementById("timelineStatusFilter");
		    li = ul.getElementsByTagName("li");
		    for (i = 0; i < li.length; i++) {
		        a = li[i].getElementsByTagName("label")[0];
		        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
		            li[i].style.display = "";
		        } else {
		            li[i].style.display = "none";

		        }
		    }
		}
		function timelineUsearsFilter() {
		    var input, filter, ul, li, a, i;
		    input = document.getElementById("timelineUsearsSearch");
		    filter = input.value.toUpperCase();
		    ul = document.getElementById("userNameDiv");
		    li = ul.getElementsByTagName("li");
		    for (i = 0; i < li.length; i++) {
		        a = li[i].getElementsByTagName("label")[0];
		        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
		            li[i].style.display = "";
		        } else {
		            li[i].style.display = "none";

		        }
		    }
		}
		
	</script>

<script>
function ascFunctionCall(){
	timelineHistoryFunction('', 'asc', null, null, null);
}

function descFunctionCall(){
	timelineHistoryFunction('', 'desc', null, null, null);
}

$('#sortTimeline').click(function(){
	  $(this).toggleClass('sortUp');
	  return $(this).hasClass('sortUp') ? ascFunctionCall() : descFunctionCall();
});
</script>


<jsp:include page="../masterFooter.jsp" />	