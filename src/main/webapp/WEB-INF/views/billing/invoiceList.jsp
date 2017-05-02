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
  
  
  <!-- modal pop -->
<div class="modal-backdrop" style="display: none"></div>
<div id="recvPymntpop" class="modal" style="display: none;">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h4 class="modal-title">Receive payment</h4>
      </div>
      <div class="modal-body clearfix"> 
        
        <!-- Add New Task Widget -->
        <div class="col-md-12 clearfix">
        <form class="form-horizontal clearfix">
          <div class="row mt">
            <div class="col-md-4">
              <label>Payment Date<span class="text-danger paddingLeft">*</span></label>
              <input class="form-control input-sm calendar" placeholder="Payment Date" type="text">
            </div>
            <div class="col-md-4 select-field">
              <label>Payment Method</label>
              <select class="form-control">
                <option selected="" disabled="">-- Select Method --</option>
                <option>Cash</option>
                <option>Cheque</option>
                <option>Credit Card</option>
              </select>
            </div>
            <div class="col-md-4">
              <label>Reference No</label>
              <input class="form-control input-sm" placeholder="Reference No" type="text">
            </div>
          </div>
          <div class="row mt">
            <div class="col-md-6">
              <div class="row">
                <div class="col-md-12">
                  <label>Amount Received</label>
                  <div class="row">
                    <div class="col-md-6">
                      <input name="ar" type="radio">
                      Total Due:
                      <input type="text" value="200000" placeholder="Total Due" class="form-control input-sm mt10" readonly>
                    </div>
                    <div class="col-md-6">
                      <input name="ar" type="radio">
                      Custom:
                      <input type="text" value="" placeholder="Total Due" class="form-control input-sm mt10">
                    </div>
                  </div>
                </div>
                <div class="col-md-12 mt">
                  <label>Memo</label>
                  <textarea rows="2" class="form-control" placeholder="Memo">
                        </textarea>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="clearfix mb invoiceAttachment">
              <label><strong>Attachments</strong></label>
              <div class="timesheet_week_comm col-md-12 ">
                <div class="form-group"> <span>
                  <input type="file" >
                  <i class="fa fa-plus"></i></span> </div>
              </div>
            </div>
            </div>
          </div>
          <div class="clearfix"></div>
          <div class="clearfix mt col-md-12 mb">
            <div class="pull-right">
              <input class="btn btn-primary btn-sm" value="Save" type="button">
              <input class="btn btn-default btn-sm ml10" value="Cancel" type="button" onclick="cancelbtn();">
            </div>
          </div>
        </form>
      </div>
    </div>
    <!-- Add New Task Widget --> 
  </div>
</div>
</div>
<!-- modal pop --> 

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
			result = result +'     <thead>                                                                                                           ';
			result = result +'       <tr>                                                                                                            ';
			result = result +'         <th>ID</th>                                                                                                   ';
			result = result +'         <th>Client Name</th>                                                                                          ';
			result = result +'         <th>Contact</th>                                                                                              ';
			result = result +'         <th>Due date</th>                                                                                             ';
			result = result +'         <th>Invoice</th>                                                                                              ';
			result = result +'         <th>Total</th>                                                                                                ';
			result = result +'         <th>Due</th>                                                                                                  ';
			result = result +'         <th>Status</th>                                                                                               ';
			result = result +'         <th>Action</th>                                                                                               ';
			result = result +'       </tr>                                                                                                           ';
			result = result +'     </thead>                                                                                                          ';
			result = result +'     <tbody>                                                                                                           ';
			
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
        		
				result = result +'       <tr>                                                                                                            ';
				result = result +'         <td>                                                                                                          ';
				result = result +'           <div class="tbldata-dotdot_80">'+report.invoiceId+'</div>                                                                   ';
				result = result +'         </td>                                                                                                         ';
				result = result +'         <td>                                                                                                          ';
				result = result +'           <div class="tbldata-dotdot-vsm" title="'+report.clientName+'">'+report.clientName+'</div>                                     ';
				result = result +'         </td>                                                                                                         ';
				result = result +'         <td>                                                                                                          ';
				result = result +'           <div class="tbldata-dotdot-vsm" title="'+report.clientContactName+'">'+report.clientContactName+'</div>                                                       ';
				result = result +'         </td>                                                                                                         ';
				result = result +'         <td>                                                                                                          ';
				result = result +'           <div class="tblminwidth-100">'+dueDateStr+'</div>                                                                ';
				result = result +'         </td>                                                                                                         ';
				result = result +'         <td>                                                                                                          ';
				result = result +'           <div class="tblminwidth-100">'+invoiceDateStr+'</div>                                                                ';
				result = result +'         </td>                                                                                                         ';
				result = result +'         <td>                                                                                                          ';
				result = result +'           <div class="tbldata-dotdot_80">2000000.00</div>                                                             ';
				result = result +'         </td>                                                                                                         ';
				result = result +'         <td>                                                                                                          ';
				result = result +'           <div class="tbldata-dotdot_80">1050000.00</div>                                                             ';
				result = result +'         </td>                                                                                                         ';
				result = result +'         <td>                                                                                                          ';
				result = result +'           <div class="tbldata-dotdot_100">Penaltiy Paid</div>                                                         ';
				result = result +'         </td>                                                                                                         ';
				result = result +'         <td>                                                                                                          ';
				result = result +'           <div class="pull-right positionRelative menuWidget"><i class="fa fa-ellipsis-v" onclick="showMenu();"></i>  ';
				result = result +'             <ul class="list-inline list-unstyled menupop">                                                            ';
				result = result +'               <li><i class="fa fa-bullhorn paddingRight"></i>Send review</li>                                         ';
				result = result +'               <li><i class="fa fa-print paddingLeft paddingRight"></i>Print</li>                                      ';
				result = result +'             </ul>                                                                                                     ';
				result = result +'           </div>                                                                                                      ';
				result = result +'           <span class="text-primary receiveBtn clearfix" onclick="receivePaymentPop();">Receive</span> </td>          ';
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
    function receivePaymentPop(){
       $("#recvPymntpop").fadeIn(200);
        $('.modal-backdrop').fadeIn(200).addClass('in');
    }
    $(".close").click(function(){
       $("#recvPymntpop").fadeOut(200);
        $('.modal-backdrop').fadeOut(200).removeClass('in');
    });
</script>

 <jsp:include page="../masterFooter.jsp" />	