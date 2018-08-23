<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
$(document).ready(function(){
   getPaySheetFrequencySummary();
});
</script>


<!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Payroll List Summary</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Payroll Summary</div>
    </section>
    
    <div class="clearfix"></div>
    <div class="candidate_wrap_table datatable_filter backgroundWhite">
    <div class="positionRelative timesheet_head minHeight clearfix"></div>
    	<div id="paySummaryDiv"></div>
    </div>
    <div class="clearfix"></div>
    </section>
    
    
    
    <div class="sideMask"></div>
<div class="modal usRunpayroll" style="display: none;" id="usRunpayroll">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close hidden" data-dismiss="modal"  onclick="closeusRunpayrol();">&times;</button>
        <h4 class="modal-title hidden">Pay Stub</h4>
        <div class="row">
          <div class="col-md-3">
            <label class="clearfix"><strong>Address</strong></label>
            <div class="clearfix">
              <p class="mb0" id="empAddress"></p>
            </div>
          </div>
          <div class="col-md-3">
            <label class="clearfix"><strong>Deposit Type</strong></label>
            <div class="clearfix">
              <select class="sel" title="Select Type" id="depositType" name="depositType">
                <option value="Direct Deposit">Direct Deposit</option>
                <option value = "By Check">By Check</option>
              </select>
              <input type="hidden" id="payRate" name="payRate"/>
               <input type="hidden" id="payrollId" name="payrollId"/>
                <input type="hidden" id="payrollUUID" name="payrollUUID"/>
            </div>
          </div>
          
          <div class="col-md-2 text-center">
            <label class="clearfix"><strong>No. Leaves</strong></label>
            <div class="clearfix">
              <p class="mt5 mb0">30</p>
            </div>
          </div>
        
        </div>
      </div>
      <div class="modal-body" style="min-height: 150px; max-height: 425px; overflow-y: auto; padding: 5px 15px;">
        <div class="row">
          <div class="col-md-12 tablePayslip" id="tablePayslip">
            <table width="100%" class="table">
              <tbody>
                <tr>
                  <td colspan="2">
                     <table width="100%" class="table table-bordered">
                      <thead>
                        <tr>
                          <th colspan="4">Hours &amp; Earnings</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <th>Description</th>
                          <th>No. of Hours</th>
                          <th>Earning</th>
                        </tr>
                        <tr>
                          <td>Regular</td>
                          <td><input type="text" id="regularHoursAmt" name="regularHoursAmt"  class="formControl" readOnly></td>
                        </tr>
                        <tr>
                          <td>Overtime</td>
                          <td><input type="text" id="overTimeHoursAmt" name="overTimeHoursAmt"  class="formControl" readOnly></td>
                        </tr>
                      </tbody>
                      <tfoot>
                        <tr>
                          <th>Total</th>
                          <td><span id="totalHours" >0</span></td>
                          <td><span id="totalEarnings">0</span></td>
                        </tr>
                      </tfoot>
                    </table>
                    </td>
                </tr>
                <tr>
                  <td colspan="2">
                  <div id="payItemDiv"></div>
                    </td>
                </tr>
                 
                 <tr>
                  <td>
                  <div id="postdedDiv"></div>
                    </td>
                  <td>
                  <div id="prededDiv"></div>
                    </td>
                </tr>
                <tr>
                  <td><table width="100%" class="table table-bordered">
                      <thead>
                        <tr>
                          <th colspan="2">Taxes</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <th>Description</th>
                          <th>Amount</th>
                        </tr>
                        <tr>
                          <td>Federal with holding</td>
                          <td><span id="federal">0</span></td>
                         <!--  <td><input type="text" id="federal" name="federal" value="" class="formControl" readOnly></td> -->
                        </tr>
                        <tr>
                          <td>Medicare</td>
                           <td><span id="medicare">0</span></td>
                         <!--  <td><input type="text" id="medicare" name="medicare" value="" class="formControl" readOnly></td> -->
                        </tr>
                        <tr>
                          <td>FICA</td>
                           <td><span id="fica">0</span></td>
                         <!-- <td><input type="text" id="fica" name="fica" value="" class="formControl" readOnly></td> -->
                        </tr>
                      
                        <tr>
                          <td>State Withholding</td>
                          <td><span id="stateWithHolding">0</span></td>
                         <!--  <td><input type="text" id="stateWithHolding" name="stateWithHolding" value="" class="formControl" readOnly></td> -->
                        </tr>
                          <tr>
                          <td>Advanced Earned Income Credit</td>
                           <td><span id="aeic">0</span></td>
                          <!-- <td><input type="text" id="aeic" name="aeic" value="124,783.00" class="formControl" readOnly></td> -->
                        </tr>
                        
                      </tbody>
                      <tfoot>
                        <tr>
                          <th>Total</th>
                          <td><span id="taxes">0</span></td>
                        </tr>
                      </tfoot>
                    </table>
                    </td>
                  <td>
                  <div id="employerPaidBenefitDiv"></div>
                    </td>
                </tr>
                <tr>
                  <td colspan="2"><table class="table table-bordered">
                      <thead>
                      <th>&nbsp;</th>
                        <th>Total Gross</th>
                        <th>Fed Taxable Gross</th>
                        <th>FICA Gross</th>
                        <th>Total Taxes</th>
                        <th>Total Deductions</th>
                        <th>Net Pay</th>
                          </thead>
                      <tbody>
                        <tr>
                          <td>Current</td>
                            <td><span id="gross">0</span></td>
                            <td><span id="federalGross">0</span></td>
                            <td><span id="ficaGross">0</span></td>
                            <td><span id="totalTaxes">0</span></td>
                            <td><span id="dedTotal">0</span></td>
                            <td><span id="netpay">0</span></td>
                        </tr>
                        <tr>
                          <td>YTD</td>
                          <td>xxxx</td>
                          <td>xxxx</td> 
                          <td>xxxx</td>
                          <td>xxxx</td>
                          <td>xxxx</td>
                          <td>xxxx</td>
                        </tr>
                      </tbody>
                    </table></td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" onClick="closeusRunpayrol();">Close</button>
      </div>
    </div>
  </div>
</div>
    
  
<script>
function getPaySheetFrequencySummary(){
	var sourceleadUrl = '${sourceleadUrl}';
	var uuid = '${paySheetFrequencyUUID}';
	var payPeriodNum = '${payPeriodNum}';
	 $.ajax({
		type : "GET",
		url : sourceleadUrl+ "/getPaySheetFrequencySummary?paySheetFrequencyuuid="+uuid+"&payPeriodNum="+payPeriodNum,
		success : function(data) {
			    var result = "";
	        	result = result +'<table id="paySheetSummary">';
		    	result = result +'<thead>';
		    	result = result +'	<tr>';
		    	result = result +'	<th>Emp Name</th>';
		    	result = result +'	<th>Rate</th>';
		    	result = result +'	<th>Regular Hours</th>';
		    	result = result +'	<th>Overtime Hours</th>';
		    	result = result +'	<th>Gross Pay</th>';
		    	result = result +'	<th>Pre-Tax Deduction</th>';
		    	result = result +'	<th>Post-Tax Deduction</th>';
		    	result = result +'	<th>Taxes</th>';
		    	result = result +'    <th>Net Pay</th>'; 
		    	result = result +'    <th>Last Pay</th>'; 
		    	result = result +'    <th>YTD</th>'; 
		    	result = result +'     <th>Action</th>';
		    	result = result +'    </tr>';
		    	result = result +'</thead>	';
		    	result = result +'<tbody>';
		    	var json = data.payrollDTO;
		    	for (var i=0; i<json.length; i++)
   				{
		    	var report = json[i];
		    	result = result +'<tr>																													';	
	            if(report.empName != null && report.empName != '' && report.empName != 'undefined'){
	            	result = result +'  <td><div class="tbldata-dotdot-vsm">'+report.empName+'</div></td>                                                      ';
	            }else{
	            	result = result +'  <td><div class="tbldata-dotdot-vsm">--</div></td>                                                      ';
	            }
	            
	            if(report.payRate != null && report.payRate != '' && report.payRate != 'undefined'){
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.payRate.toFixed(2)+'</div></td>                                                      ';
	            }else{
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">--</div></td>                                                      ';
	            }
	            
	            if(report.regularHours != null && report.regularHours != '' && report.regularHours != 'undefined'){
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.regularHours+'</div>                                                      ';
	            }else{
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">--</div>                                                      ';
	            }
	            
	            if(report.regularHrsTotalAmt != null && report.regularHrsTotalAmt != '' && report.regularHrsTotalAmt != 'undefined'){
	            	result = result +'<div class="tblminwidth-50 clearfix">$'+report.regularHrsTotalAmt.toFixed(2)+'</div></td>            ';
	            }else{
	            	result = result +'<div class="tblminwidth-50 clearfix">$0.0</div></td>            ';
	            }
	            
	            if(report.overtimeHours != null && report.overtimeHours != '' && report.overtimeHours != 'undefined'){
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.overtimeHours+'</div>';
	            }else{
	            	result = result +'	<td><div class="tblminwidth-50 clearfix">--</div>            ';
	            }
	            
	            if(report.otHoursTotalAmt != null && report.otHoursTotalAmt != '' && report.otHoursTotalAmt != 'undefined'){
	            	result = result +' <div class="tblminwidth-50 clearfix">$'+report.otHoursTotalAmt.toFixed(2)+'</div></td>            ';
	            }else{
	            	result = result +' <div class="tblminwidth-50 clearfix">$0.0</div></td>            ';
	            }
	            
	            if(report.grossPay != null && report.grossPay != '' && report.grossPay != 'undefined'){
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.grossPay.toFixed(2)+'</div></td>                                                      ';
	            }else{
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">0.0</div></td>                                                      ';
	            }
	            
	            if(report.preTaxDeduction != null && report.preTaxDeduction != '' && report.preTaxDeduction != 'undefined'){
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.preTaxDeduction.toFixed(2)+'</div></td>                                                      ';
	            }else{
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">0.0</div></td>                                                      ';
	            }
	            
	            if(report.postTaxDeduction != null && report.postTaxDeduction != '' && report.postTaxDeduction != 'undefined'){
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.postTaxDeduction.toFixed(2)+'</div></td>                                                      ';
	            }else{
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">0.0</div></td>                                                      ';
	            }
	            
	            if(report.taxes != null && report.taxes != '' && report.taxes != 'undefined'){
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.taxes.toFixed(2)+'</div></td>                                                      ';
	            }else{
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">0.0</div></td>                                                      ';
	            }
	            
	            if(report.netPay != null && report.netPay != '' && report.netPay != 'undefined'){
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.netPay.toFixed(2)+'</div></td>                                                      ';
	            }else{
	            	result = result +'  <td><div class="tblminwidth-50 clearfix">0.0</div></td>                                                      ';
	            }
	            
	            result = result +'  <td><div class="tblminwidth-50 clearfix">0.0</div></td>                                                            ';
	            result = result +'  <td><div class="tblminwidth-50 clearfix">0.0</div></td>                                                         ';
	            result = result +'  <td>                                                                                                                ';
				result = result +'	  <div class="tblminwidth-30 text-center">                                                                          ';
				result = result +'	  	<a href="javascript:void(0)" class="anchorBlack"><i class="fa fa-eye" onclick="payAdjustment(\''+report.empPayrollUUID+'\');"></i></a>                                  ';
				result = result +'	  </div>                                                                                                            ';
	            result = result +'  </td>                                                                                                               ';
	            result = result +'</tr>                                                                                                                 ';
   			}
		    	result = result +'</tbody>';
			    $("#paySummaryDiv").empty();
			    $("#paySummaryDiv").append(result);
			    paySummaryPagination();
		}
	});
}
</script>

 <script>
function paySummaryPagination(){  
    $('#paySheetSummary').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		"sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>


<jsp:include page="./viewPayRollSummary.jsp" /> 

<jsp:include page="../masterFooter.jsp" />


 		