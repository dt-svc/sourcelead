<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style type="text/css">
.invoiceId_Anchor a {
color:#337ab7 !important;
}
.successBtn {
background-color: #1ab394;
border-color: #1ab394;
cursor: pointer;
    border: 1px solid;
    color: #fff;
    padding: 1px 5px;
    border-radius: 3px;
    font-size: 13px;
}

</style>

<script type="text/javascript">
$(document).ready(function(){
	getInvoiceList();
});
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Receivables</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Receivable List</div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="billing_dashboard">
        <div class="tabbable positionRelative">
          <div class="onboardcreateButtons"> <a href="javascript:void(0)" class="btn btn-primary btn-sm" title="Create invoice"  onclick="createBill();"> <i class="fa fa-plus paddingRight"></i>Create Invoice</a>
            <div class="pull-right select-field ml">
              <select class="form-control input-sm" id="dayWiseTypeId" onchange="getInvoiceList();customInvoiceList();">
                <option value="month">Last 30 days</option>
                <option value="twomonths">Last 60 Days</option>
                <option value="threemonths">Last 90 days</option>
                <option value="sixmonths">Last 180 days</option>
                <option value="oneyear">Last 365 days</option>
                <option value="custom">Custom</option>
              </select>
              
              <div class="custom_search">
              <div id="customDateDivId"></id>
            </div>
          </div>
            </div>
          </div>
         
         <ul class="nav nav-tabs" id="tab_bar">
            
               <li class="active">
                        <h4>Unbilled</h4>
                        <a href="#unbilled" data-toggle="tab" class="onboardCount billbg_orange" title="Draft Invoices">
                            <div class="pull-left">
                            <i class="fa fa-usd paddingRight fa-3x icon_fa"></i>
                            </div>
                            <div class="pull-right">
                            <div class="count" id="unbilledAmoumt"></div>
                                <h5>Draft Invoices<span class="paddingLeft" id="draftInvoiceCount"></span></h5>
                                </div>
                        </a>
                    </li>
                    <li>
                        <h4>Un Paid</h4>
                        <a href="#unpaid" data-toggle="tab" title="UnPaid Overdue Invoices" class="billingbg_red">
                        <div class="pull-left">
                            <i class="fa fa-usd paddingRight fa-3x icon_fa"></i>
                            </div>
                            <div class="pull-right">
                            <div class="count" id="dueAfterInvoiceAmount"></div>
                        <h5>Overdue Invoices<span class="paddingLeft" id="dueAfterInvoiceCount"></span></h5>
                            </div>
                        
                        </a>
                    </li>
                    <li>
                        <h4>&nbsp;</h4>
                        <a href="#unpaid" data-toggle="tab" title="UnPaid Open Invoices" class="billingbgRed">
                        <div class="pull-left">
                            <i class="fa fa-usd paddingRight fa-3x icon_fa"></i>
                            </div>
                            <div class="pull-right">
                            <div class="count" id="dueBeforeInvoiceAmount"></div>
                        <h5>Open Invoices<span class="paddingLeft" id="dueBeforeInvoiceCount"></span></h5>
                        </div>
                        </a>
                    </li>
                    <li class="billingbg_greens">
                        <h4>Paid</h4>
                        <a href="#paid" data-toggle="tab" title="Paid Invoices" class="billbg_green">
                        <div class="pull-left">
                            <i class="fa fa-usd paddingRight fa-3x icon_fa"></i>
                            </div>
                            <div class="pull-right">
                            <div class="count" id="paidInvoiceAmount"></div>
                        <h5>Paid Invoices<span class="paddingLeft" id="paidInvoiceCount"></span></h5>
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
            
            <!-- Employee Details -->
            <div class="tab-pane active" id="unbilled">
              <div class="timesheet_out mt">
                <div class="clearfix">
                  <div class="timesheet_head">
                    <ul>
                      <li id="invoiceID1"><i class="fa fa-filter"></i><span>Status</span><i class="glyphicon glyphicon-play" ></i>|
                        <div class="approved_pop" id="invoiceTabOne">
                          <ul>
                            <li>
                              <input type="checkbox" value="DRAFT" name="invoiceStatus" id="" onclick="getInvoiceList();" >
                              <label for="onboard1">Draft</label>
                            </li>
                            <li>
                              <input type="checkbox" id="" value="SENT" name="invoiceStatus" onclick="getInvoiceList();">
                              <label for="onboard2">Sent</label>
                            </li>
                            <li>
                              <input type="checkbox" value="RECEIVED" name="invoiceStatus" id="" onclick="getInvoiceList();" >
                              <label for="onboard1">Received</label>
                            </li>
                            <li>
                              <input type="checkbox" id="" value="ACCEPTED" name="invoiceStatus" onclick="getInvoiceList();">
                              <label for="onboard2">Accepted</label>
                            </li>
                            <li>
                              <input type="checkbox" id="" value="REJECTED" name="invoiceStatus" onclick="getInvoiceList();">
                              <label for="onboard2">Rejected</label>
                            </li>
                          </ul>
                        </div>
                      </li>
                      
                      <li id="paymentID1"><i class="fa fa-filter"></i><span>Payment</span><i class="glyphicon glyphicon-play"></i>|
                        <div class="approved_pop" id="paymentTabOne">
                          <ul>
                            <li>
                              <input type="checkbox" id="" value="Paid" name="paymentStatus" onclick="getInvoiceList();">
                              <label for="onboard3">Paid</label>
                            </li>
                            <li>
                              <input type="checkbox" id="" value="Partial Paid" name="paymentStatus" onclick="getInvoiceList();">
                              <label for="onboard2">Partially Paid</label>
                            </li>
                            <li>
                              <input type="checkbox" id="" value="OverDue" name="paymentStatus" onclick="getInvoiceList();">
                              <label for="onboard4">OverDue</label>
                            </li>
                            <li>
                              <input type="checkbox" value="Unpaid" name="paymentStatus" id="" onclick="getInvoiceList();">
                              <label for="onboard1">Unpaid</label>
                            </li>
                          </ul>
                        </div>
                      </li>                      
                      
                      <div class="clearfix"></div>
                    </ul>
                    <div class="clearfix"></div>
                     
                  </div>
                  
                  
                  <div class="candidate_wrap_table">
	                  <div id="invoiceList"></div>
                  </div>
                </div>
              </div>
            </div>
            
            
            
            <!-- TAB 2 -->
            <div class="tab-pane" id="unpaid">
              <div class="timesheet_out mt">
                <div class="clearfix">
                  <div class="timesheet_head">
                    <ul>
                      <li id="payment-ID1"><i class="fa fa-filter"></i><span>Filter</span><i class="glyphicon glyphicon-play"></i>|
                        <div class="approved_pop">
                          <ul>
                            <li>
                              <input type="checkbox" value="" name="offboard" id="offboard1">
                              <label for="offboard1">Sent</label>
                            </li>
                            <li>
                              <input type="checkbox" id="offboard2" name="offboard">
                              <label for="offboard2">In progress</label>
                            </li>
                            <li>
                              <input type="checkbox" id="offboard3" name="offboard">
                              <label for="offboard3">Completed</label>
                            </li>
                            <li>
                              <input type="checkbox" id="offboard4" name="offboard">
                              <label for="offboard4">Declain</label>
                            </li>
                            <li>
                              <input type="checkbox" id="offboard5" name="offboard">
                              <label for="offboard5">Waiting for approval</label>
                            </li>
                          </ul>
                        </div>
                      </li>
                      <div class="clearfix"></div>
                    </ul>
                    <div class="clearfix"></div>
                     
                  </div>
                  <div class="candidate_wrap_table">
                    <table>
                      <thead>
                        <tr>
                          <th>ID</th>
                          <th>Customer</th>
                          <th>Contact</th>
                          <th>Due date</th>
                          <th>Invoice</th>
                          <th>Total</th>
                          <th>Due</th>
                          <th>Status</th>
                          <th>Action</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <td>
                            <div class="tbldata-dotdot_80">1005</div>
                          </td>
                          <td>
                            <div class="tbldata-dotdot-vsm">Tata Consultancy Services Pvt Ltd</div>
                          </td>
                          <td>
                            <div class="tbldata-dotdot-vsm">Rahulkumar Jain</div>
                          </td>
                          <td>
                            <div class="tblminwidth-50">12/05/2017</div>
                          </td>
                          <td>
                            <div class="tblminwidth-50">12/05/2017</div>
                          </td>
                          <td>
                            <div class="tbldata-dotdot_80">2000000.00</div>
                          </td>
                          <td>
                            <div class="tbldata-dotdot_80">1050000.00</div>
                          </td>
                          <td>
                            <div class="tbldata-dotdot_100">Penaltiy Paid</div>
                          </td>
                          <td>
                            <div class="pull-right positionRelative menuWidget"><i class="fa fa-ellipsis-v" onclick="showMenu();"></i>
                              <ul class="list-inline list-unstyled menupop">
                                <li><i class="fa fa-bullhorn paddingRight"></i>Send review</li>
                                <li><i class="fa fa-print paddingLeft paddingRight"></i>Print</li>
                              </ul>
                            </div>
                            <span class="text-primary receiveBtn clearfix" onclick="receivePaymentPop();">Receive</span> </td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
            <div class="tab-pane" id="paid">
              <div class="timesheet_out mt">
                <div class="clearfix">
                  <div class="timesheet_head">
                    <h4 class="ml">Filter</h4>
                    <div class="remove_search pull-right hidden">
                      <input placeholder="search" type="text">
                    </div>
                  </div>
                  <div class="candidate_wrap_table">
                    <table>
                      <thead>
                        <tr>
                          <th>ID</th>
                          <th>Customer</th>
                          <th>Contact</th>
                          <th>Due date</th>
                          <th>Invoice</th>
                          <th>Total</th>
                          <th>Due</th>
                          <th>Status</th>
                          <th>Action</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <td>
                            <div class="tbldata-dotdot_80">1005</div>
                          </td>
                          <td>
                            <div class="tbldata-dotdot-vsm">Tata Consultancy Services Pvt Ltd</div>
                          </td>
                          <td>
                            <div class="tbldata-dotdot-vsm">Rahulkumar Jain</div>
                          </td>
                          <td>
                            <div class="tblminwidth-50">12/05/2017</div>
                          </td>
                          <td>
                            <div class="tblminwidth-50">12/05/2017</div>
                          </td>
                          <td>
                            <div class="tbldata-dotdot_80">2000000.00</div>
                          </td>
                          <td>
                            <div class="tbldata-dotdot_80">1050000.00</div>
                          </td>
                          <td>
                            <div class="tbldata-dotdot_100">Penaltiy Paid</div>
                          </td>
                          <td>
                            <div class="pull-right positionRelative menuWidget"><i class="fa fa-ellipsis-v" onclick="showMenu();"></i>
                              <ul class="list-inline list-unstyled menupop">
                                <li><i class="fa fa-bullhorn paddingRight"></i>Send reminder</li>
                                <li><i class="fa fa-print paddingLeft paddingRight"></i>Print</li>
                              </ul>
                            </div>
                            <span class="text-primary receiveBtn clearfix" onclick="receivePaymentPop();">Receive</span> </td>
                        </tr>
                      </tbody>
                    </table>
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
		      <div class="col-md-offset-2 col-md-10"> <a href="javascript:void(0)" class="close-popdiv" onClick="closeCandidate()"><i class="fa fa-close"></i></a>
		      <div class="col-md-5 mt25">
		        <div class="small-widget bgGreen">
		            <a  href="<c:url value='/createInvoice'/>" title="Create custom invoice">
		          <div class="clearfix mb10"> 
		              <img src="resources/images/invoice_icon.png" class="text-center" alt="create_receivables">
		          </div>
		          <strong class="clearfix">Custom</strong></a>
		          </div>
		      </div>
		
		        <div class="col-md-5 mt25">
		          <div class="small-widget bg-yellow"> 
		          <a href="<c:url value='/createInvoiceProjectBased'/>" title="Create project based invoice">
		            <div class="clearfix mb10">
		                <!--<i class="fa fa-usd fa-3x"></i>-->
		                <img src="resources/images/invoice_icon.png" class="text-center" alt="Invoice">
		            </div>
		            <strong class="clearfix">Project Based </strong> </a> </div>
		        </div>
		          
		          <!-- <div class="col-md-4 mt25">
		          <div class="small-widget bg-purple"> <a href="other_receivables.html" title="Create other invoice">
		            <div class="clearfix mb10">
		              <i class="fa fa-usd fa-3x"></i>             
		             <img src="resources/images/invoice_icon.png" class="text-center" alt="Invoice">
		              </div>
		            <strong class="clearfix">Other</strong> </a> </div>
		        </div> -->
		
		      
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
	result = result +'              <input class="form-control input-sm calendar" placeholder="Payment Date" type="text" id="paymentDate" name="paymentDate" data-validation="required" field-name="Payment Date"> ';
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
	result = result +'            <div class="col-md-6">                             ';
	result = result +'              <div class="row">                                ';
	result = result +'                <div class="col-md-12">                        ';
	result = result +'                  <label>Amount Received</label>               ';
	result = result +'                  <div class="row">                            ';
	result = result +'                    <div class="col-md-6">                     ';
	result = result +'                      <input   type="radio" name="paymentAmount" value="total" checked onclick="validMask();"> ';
	result = result +'                      Total Due:          ';
	result = result +'                      <input type="text" value="'+paymentAmount+'" placeholder="Total Due" class="form-control input-sm mt10" id="totalAmount" name="totalAmount" readonly> ';
	result = result +'                    </div>              ';
	result = result +'                    <div class="col-md-6">      ';
	result = result +'                      <input  type="radio" name="paymentAmount" value="custom" onclick="removeMask();" >     ';
	result = result +'                      Custom:                       ';
	result = result +'                      <input type="text" value="" placeholder="Custom Due" class="form-control input-sm mt10 allownumericwithdecimal" id="customeAmount" name="customeAmount" readonly onblur="amountCal();">       ';
	result = result +'                    </div>                      ';
	result = result +'                  </div>                        ';
	result = result +'                </div>                          ';
	result = result +'                <div class="col-md-12 mt">      ';
	result = result +'                  <label>Memo</label>           ';
	result = result +'                  <textarea rows="2" class="form-control" placeholder="Memo" id="paymentMemo" name="paymentMemo"></textarea>';
	result = result +'                </div>                                                            ';
	result = result +'              </div>                                                              ';
	result = result +'            </div>                                                                ';
	result = result +'            <div class="col-md-6">                                                ';
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

<!-- <script>
function saveInvoice(saveType){
	/* var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         };  */
	
	   if ($('#invoice_form').validate(false, validationSettings)){  
		    var frm = $('#invoice_form').serializeFormJSON();
	        var con = JSON.stringify(frm);
	        con = con.replace(/[{}]/g, "");
	        //It will escape all the special characters
	        var jsonData = encodeURIComponent(con);
	        
	        var form = $('#invoice_form')[0];
	 	   var formData = new FormData(form); 
	        var sourceleadUrl ='${sourceleadUrl}';
	        $("#invoiceLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	        $.ajax({
		    	type: "POST",
		    	 data: formData,
			      contentType : 'application/json; charset=utf-8',
			      enctype: 'multipart/form-data',
		        processData: false,
		        contentType:false,
			      dataType : 'json',
		    	 url: sourceleadUrl+"/saveInvoice?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&saveType="+saveType, 
		        success: function(result) {
		     		$("#invoiceLoading").removeAttr("style");
		     		location.replace(sourceleadUrl+"/invoiceList");
		     		
		        },
		    });  
	         return true; 
		
	   }else{
		return false;
	  } 
}

$('body').on('blur', '#invoice_form', function() {
	$("#invoice_form").showHelpOnFocus();
	$("#invoice_form").validateOnBlur(false, validationSettings);  
}); 
</script> -->
<script>
function getInvoiceList(){
	$(".custom_search").hide();
	var sourceleadUrl ='${sourceleadUrl}';
	var invoice = [];
	var payment = [];
	var dayWisetype = $("#dayWiseTypeId").val();
	
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
        url: sourceleadUrl+"/getInvoiceList?${_csrf.parameterName}=${_csrf.token}&invoiceStatus="+invoice+"&paymentStatus="+payment+"&dayWisetype="+dayWisetype+"&startDate="+startDate+"&endDate="+endDate+"&invoiceType=receivable",
        success: function(data) {
        	var json = JSON.parse(data);
        	
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
			result = result +'         <th>ID</th>                    ';
			result = result +'         <th>Client Name</th>           ';
			result = result +'         <th>Contact</th>               ';
			result = result +'         <th>Due date</th>              ';
			result = result +'         <th>Invoice Date</th>               ';
			result = result +'         <th>Total</th>                 ';
			result = result +'         <th>Due</th>                   ';
			result = result +'         <th>Status</th>                ';
			result = result +'         <th>Payment Status</th>                ';
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
        		if(report.projectId != null && report.projectId != '' && report.projectId != 'undefined'){
        			projectId = report.projectId;
        		}else{
        			projectId = "";
        		}
				result = result +'       <tr> ';
				result = result +'         <td>';

				if(report.invoiceStatus == "DRAFT" || report.invoiceStatus == "Rejected"){
					result = result +'           <div class="tbldata-dotdot_80 invoiceId_Anchor"><a href="'+sourceleadUrl+'/getReceivableInvoiceByUUID?invoiceUUID='+report.invoiceUUID+'&projectId='+report.projectId+'">'+report.invoiceId+'</a></div>   ';

				}
				if(report.invoiceStatus == "SENT" || report.invoiceStatus == "Received" || report.invoiceStatus == "Accepted" ){
	 				result = result +'           <div class="tbldata-dotdot_80 invoiceId_Anchor"><a href="<c:url value="/viewInvoice?invoiceUUID='+report.invoiceUUID+'&invoiceType=Receivable"/>">'+report.invoiceId+'</a></div>   ';				
				}
				result = result +'         </td>  ';
				result = result +'         <td>   ';
				result = result +'           <div class="tbldata-dotdot-vsm" title="'+report.clientName+'">'+report.clientName+'</div>';
				result = result +'         </td> ';
				result = result +'         <td>';
				result = result +'           <div class="tbldata-dotdot-vsm" title="'+report.clientContactName+'">'+report.clientContactName+'</div>  ';
				result = result +'         </td>';
				result = result +'         <td> ';
				result = result +'           <div class="tblminwidth-100">'+dueDateStr+'</div>';
				result = result +'         </td>';
				result = result +'         <td> ';
				result = result +'           <div class="tblminwidth-100">'+invoiceDateStr+'</div>';
				result = result +'         </td>';
				result = result +'         <td> ';
				result = result +'           <div class="tbldata-dotdot_80">'+report.total+'</div>';
				result = result +'         </td>';
				result = result +'         <td> ';
				result = result +'           <div class="tbldata-dotdot_80">'+report.dueAmount+'</div>';
				result = result +'         </td>';
				result = result +'         <td> ';
				result = result +'           <div class="tbldata-dotdot-100">'+report.invoiceStatus+'</div>';
				result = result +'         </td>';
				
				result = result +'         <td> ';
				result = result +'           <div class="tbldata-dotdot-100">'+report.paymentStatus+'</div> ';
				result = result +'         </td>';
				
				result = result +'         <td> ';
				result = result +'     <div class="tblminwidth-75">';
				/* result = result +'  <div class="pull-right positionRelative menuWidget" ><i class="fa fa-ellipsis-v" onClick="showPopUp('+report.id+');" ></i>  ';
				result = result +'  <div id="popupDiv'+report.id+'"></div>';
		        result = result +'  </div>                                                                                                      ';
		         */
				if(report.invoiceType=="Receivable" &&( report.invoiceParentId == null || report.invoiceParentId == '' || report.invoiceParentId == "undefined")){
					if(report.paymentStatus == "Paid" || report.invoiceStatus == "DRAFT"){
						result = result +'           <span class="text-primary successBtn primary pull-left" >Receive</span>   ';	
					}else{
						result = result +'           <span class="text-primary receiveBtn pull-left" onclick="receivePaymentPop1('+report.id+','+report.dueAmount+');">Receive</span>';
					}
				}else{
					if(report.invoiceStatus == "Accepted"){
						result = result +'           <span class="text-primary receiveBtn pull-left" onclick="receivePaymentPop1('+report.id+','+report.dueAmount+');">Pay</span> </td>';
					}else{
						if(report.invoiceStatus == "Rejected"){
							result = result +'           <span class="text-primary successBtn primary pull-left" >Rejected</span>           ';	
						}else{
							result = result +'           <span class="clearfix" ><a onclick="acceptedInvoice('+report.id+');" title="Success"><i class="fa fa-check text-success paddingRight"></i></a><a onclick="rejectInvoice('+report.id+');" title="Remove"><i class="paddingLeft fa fa-times text-danger"></i></a></span>';
						}
						
					}
				}

				if(report.invoiceType=="Payables" ){
					if(report.paymentStatus == "Paid" || report.invoiceStatus == "DRAFT"){
						result = result +'           <span class="text-primary successBtn primary pull-left" >Pay</span>           ';	
					}else{
						if(report.invoiceStatus == "Accepted"){
							result = result +'           <span class="text-primary receiveBtn pull-left" onclick="receivePaymentPop1('+report.id+','+report.dueAmount+');">Pay</span>';
						}else{
							if(report.invoiceStatus == "Rejected"){
								result = result +'           <span class="text-primary successBtn primary pull-left" >Rejected</span> </td>          ';	
							}else{
								result = result +'           <span class="clearfix" ><a onclick="acceptedInvoice('+report.id+');" title="Success"><i class="fa fa-check text-success paddingRight"></i></a><a onclick="rejectInvoice('+report.id+');" title="Remove"><i class="paddingLeft fa fa-times text-danger"></i></a></span> ';
								
							}
							
						}
					}
				}
				
				/* result = result +'	<td>                                                                                                        ';
		        result = result +'  <div class="pull-right positionRelative menuWidget"><i class="fa fa-ellipsis-v" onClick="showMenu();"></i>  ';
		        result = result +'    <ul class="list-inline list-unstyled menupop">                                                            ';
		        result = result +'      <li><i class="fa fa-bullhorn paddingRight"></i>Send reminder</li>                                       ';
		        result = result +'      <li><i class="fa fa-print paddingLeft paddingRight"></i>Print</li>                                      ';
		        result = result +'    </ul>                                                                                                     ';
		        result = result +'  </div>                                                                                                      ';
		        result = result +'  <span class="text-primary receiveBtn clearfix" onClick="receivePaymentPop();">Receive</span> '; */
		        result = result +'</div>';
		        result = result +'</td>';
                
				result = result +'       </tr>                                                                                                           ';

    		}
        	result = result +'     </tbody>                                                                                                          ';
			result = result +'   </table>                                                                                                            ';
			$("#invoiceList").empty();  
		    $("#invoiceList").append(result);
		    
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
    $('#invoiceListPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"aaSorting": [[0,'desc']],
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $("#invoiceListPagination").wrap("<div class='overflow_Auto'></div>");
}
</script>
<script>
function showPopUp(id){
	    result = "";
		 result = result +'    <ul class="list-inline list-unstyled menupop"> ';
	     result = result +'      <li><i class="fa fa-bullhorn paddingRight" title="Send reminder"></i>Send reminder</li> ';
	     result = result +'      <li><i class="fa fa-print paddingLeft paddingRight" title="Print"></i>Print</li>';
	     result = result +'      <li><i class="fa fa-download paddingLeft paddingRight" title="Download"></i>Download</li>';
	     result = result +'    </ul>';
	     $("#popupDiv"+id+"").empty();  
    	 $("#popupDiv"+id+"").append(result);
    	 showMenu();
    	
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
        	location.replace(sourceleadUrl+"/invoiceList");
     		
        },
    });  
}

</script>
<script>
function rejectInvoice(id){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
    	 type: "POST",
	     dataType : 'json',
	    	 url: sourceleadUrl+"/rejectInvoice?${_csrf.parameterName}=${_csrf.token}&invoiceId="+id, 
             success: function(result) {
        	location.replace(sourceleadUrl+"/invoiceList");
     		
        },
    }); 
}

</script>
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
/* function amountCal(){
	var totalAmount =  $("#totalAmount").val();
	var customAmount =  $("#customeAmount").val();
	if(totalAmount <= customAmount){
		var exceededAmount = customAmount - totalAmount
		alert("your payment is exceeded by "+exceededAmount.toFixed(2)); 
	}
} */
</script>

<script>
$(document).ready(function() {
    $("#invoiceID1").click(function(){
		$("#invoiceTabOne").slideToggle(0);
		$("#paymentTabOne").slideUp(200);
	});
    $("html").click(function() {
        $("#invoiceTabOne").slideUp(0);
    });
    $("#invoiceTabOne, #invoiceID1").click(function(e) {
        e.stopPropagation();
    });	
});


$(document).ready(function() {
    $("#paymentID1").click(function(){
		$("#paymentTabOne").slideToggle(0);
		$("#invoiceTabOne").slideUp(200);
	});
    $("html").click(function() {
        $("#paymentTabOne").slideUp(0);
    });
    $("#paymentTabOne, #paymentID1").click(function(e) {
        e.stopPropagation();
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
function customDate(){
	var result = "";                                                                                                                                  
	result = result +'	<div class="clearfix row"> <span class="pull-left ml">Custom Search</span>                                                     ';
	result = result +'   <div class="closeSearch pull-right mr" title="Close Div" onclick="closePopup();">X</div>                                                              ';
	result = result +'   <div class="clearfix mt10"></div>                                                                                             ';
	result = result +'   <div class="col-md-5 paddingRight" id="errStartDate">                                                                                           ';
	result = result +'     <input type="text" class="form-control input-sm calendar" id="billingStartDate" placeholder="Start Date">                   ';
	result = result +'                                                                                                                                 ';
	result = result +'   </div>                                                                                                                        ';
	result = result +'   <div class="col-md-5 paddingLeft" id="errEndDate">                                                                                            ';
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
		getInvoiceList();
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

 <jsp:include page="../masterFooter.jsp" />	