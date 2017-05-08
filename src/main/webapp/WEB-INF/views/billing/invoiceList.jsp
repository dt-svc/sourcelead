<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
$(document).ready(function(){
	getInvoiceList();
});
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Billing</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Invoice ListHome » Billing</div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="onboardDashboard_counts">
        <div class="tabbable positionRelative">
          <div class="onboardcreateButtons"> <a href="<c:url value='/createInvoice'/>"  class="btn btn-primary btn-sm" title="Create invoice"> <i class="fa fa-plus paddingRight"></i>Create Invoice</a>
            <div class="pull-right select-field ml">
              <select class="form-control input-sm">
                <option class="disabled" selected="">--Select option--</option>
                <option>1 Month</option>
                <option>2 Months</option>
                <option>3 Months</option>
                <option>6 Months</option>
                <option>1 Year</option>
                <option>Custom ... </option>
              </select>
            </div>
          </div>
          <ul class="nav nav-tabs" id="tab_bar">
            <li class="active"> <a href="#unbilled" data-toggle="tab" class="onboardCount" title="Current onboard count" aria-expanded="true">
              <div class="count"> 1 </div>
              <h4>Unbilled invoice</h4>
              </a> </li>
            <li class=""> <a href="#unpaid" data-toggle="tab" title="Current offboard count" aria-expanded="false">
              <div class="count"> 10 </div>
              <h4>Unpaid invoice</h4>
              </a> </li>
            <li class=""> <a href="#paid" data-toggle="tab" title="Incomplete Tasks count" aria-expanded="false">
              <div class="count"> 100 </div>
              <h4>Paid invoice</h4>
              </a> </li>
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
                      <li><span id="onboard-pop"><i class="fa fa-filter"></i><span>Filter</span><i class="glyphicon glyphicon-play"></i></span>|
                        <div class="approved_pop">
                          <ul>
                            <li>
                              <input type="checkbox" value="" name="onboard" id="onboard1" checked="">
                              <label for="onboard1">Sent</label>
                            </li>
                            <li>
                              <input type="checkbox" id="onboard2" name="onboard" checked="">
                              <label for="onboard2">In progress</label>
                            </li>
                            <li>
                              <input type="checkbox" id="onboard3" name="onboard" checked="">
                              <label for="onboard3">Completed</label>
                            </li>
                            <li>
                              <input type="checkbox" id="onboard4" name="onboard">
                              <label for="onboard4">Declain</label>
                            </li>
                            <li>
                              <input type="checkbox" id="onboard5" name="onboard">
                              <label for="onboard5">Waiting for approval</label>
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
                      <li><span id="offboard-pop"><i class="fa fa-filter"></i><span>Filter</span><i class="glyphicon glyphicon-play"></i></span>|
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
                    <div class="remove_search pull-right">
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
	result = result +'              <input class="form-control input-sm" placeholder="Reference No" type="text" id="referenceNumber" name="referenceNumber" data-validation="required" field-name="Reference No"> ';
	result = result +'            </div>        ';
	result = result +'          </div>          ';
	result = result +'          <div class="row mt">                                 ';
	result = result +'            <div class="col-md-6">                             ';
	result = result +'              <div class="row">                                ';
	result = result +'                <div class="col-md-12">                        ';
	result = result +'                  <label>Amount Received</label>               ';
	result = result +'                  <div class="row">                            ';
	result = result +'                    <div class="col-md-6">                     ';
	result = result +'                      <input   type="radio" name="paymentAmount" value="total" checked>           ';
	result = result +'                      Total Due:          ';
	result = result +'                      <input type="text" value="'+paymentAmount+'" placeholder="Total Due" class="form-control input-sm mt10" id="totalAmount" name="totalAmount" readonly> ';
	result = result +'                    </div>              ';
	result = result +'                    <div class="col-md-6">      ';
	result = result +'                      <input  type="radio" name="paymentAmount" value="custom">     ';
	result = result +'                      Custom:                       ';
	result = result +'                      <input type="text" value="" placeholder="Total Due" class="form-control input-sm mt10" id="customeAmount" name="customeAmount" >       ';
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
	result = result +'                  <input type="file"  id="paymentAttachment" name="paymentAttachment">                                            ';
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
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
    	type: "GET",
        url: sourceleadUrl+"/getInvoiceList?${_csrf.parameterName}=${_csrf.token}",
        success: function(data) {
        	var json = JSON.parse(data);
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
        		
				result = result +'       <tr> ';
				result = result +'         <td>';
				result = result +'           <div class="tbldata-dotdot_80">'+report.invoiceId+'</div>   ';
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
				result = result +'           <div class="tbldata-dotdot_100">'+report.invoiceStatus+'</div>';
				result = result +'         </td>';
				
				result = result +'         <td> ';
				result = result +'           <div class="tbldata-dotdot_100">'+report.paymentStatus+'</div> ';
				result = result +'         </td>';
				
				result = result +'         <td> ';
				 
				result = result +'           <span class="text-primary receiveBtn clearfix" onclick="receivePaymentPop1('+report.id+','+report.dueAmount+');">Receive</span> </td>          ';
				result = result +'       </tr>                                                                                                           ';

    		}
        	result = result +'     </tbody>                                                                                                          ';
			result = result +'   </table>                                                                                                            ';
			$("#invoiceList").empty();  
		    $("#invoiceList").append(result)
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
   /*  function receivePaymentPop(){
       $("#recvPymntpop").fadeIn(200);
        $('.modal-backdrop').fadeIn(200).addClass('in');
    } */
   /*  $(".close").click(function(){
       $("#recvPymntpop").fadeOut(200);
        $('.modal-backdrop').fadeOut(200).removeClass('in');
    }); */
</script>

 <jsp:include page="../masterFooter.jsp" />	