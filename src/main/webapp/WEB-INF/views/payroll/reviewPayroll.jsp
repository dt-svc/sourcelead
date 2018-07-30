<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
window.onload = function () {
	$("#approvePayrollId").addClass('disabled').removeAttr("href");
	getReviewPayroll();
}
</script>


<script>
function reviewPayroll(){
	$("#reviewPayrollDiv").show();
}

function approvePayroll(){
	$("#reviewPayrollDiv").hide();
}
</script>

<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Review Payroll</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Review Payroll</div>
    </section>
    
    <div class="tabsMenu clearfix">
   		<ul class="list-unstyled">
		   <li class="current"><a onclick="reviewPayroll();">Review Payroll</a></li>
		   <li><a id="approvePayrollId" onclick="approvePayroll();">Approve Payroll</a></li>
	   </ul>
   </div>
   
    <div id="reviewPayrollDiv" class="candidate_wrap_table datatable_filter backgroundWhite">
    <div class="positionRelative timesheet_head minHeight clearfix"></div>
    	<div id="reviewDiv"></div>
    
    
            <nav class="clearfix">
          <ul class="pull-right mr list-unstyled list-inline">
<!--             <li><a href="#" class="btn btn-primary btnSuccess" onclick="sendPayrollForApproval();">Sent for Approve</a></li> -->
            <li><a href="#" class="btn btn-default ml mr" onclick="sendPayrollForApproval();">Confirm</a></li>
            <li><a href="#" class="btn btn-default">Cancel</a></li>
          </ul>
          <div class="clearfix"></div>
        </nav>
    
    
    <div class="clearfix"></div>
  </div>
  </section>
  <!-- Content Area Ends--> 
  
  <script>
  function getReviewPayroll(){
	  var uuid = '${paySheetFrequencyUUID}';
	  var payPeriodNum = '${payPeriodNum}';
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getPaySheetFrequencySummary?paySheetFrequencyuuid="+uuid+"&payPeriodNum="+payPeriodNum,                 
		        success: function(data) {
					var result = "";
		        	result = result +'<table id="reviewTableId">';
			    	result = result +'<thead>';
			    	result = result +'	<tr>';
			    	/* result = result +'	<th></th>'; */
			    	result = result +'	<th>Emp Id</th>';
			    	result = result +'	<th>Emp Name</th>';
			    	result = result +'	<th>Rate</th>';
			    	result = result +'	<th>Regular Hours</th>';
			    	result = result +'	<th>Overtime Hours</th>';
			    	result = result +'	<th>Gross Pay</th>';
			    	result = result +'	<th>Pre-Tax Deduction</th>';
			    	result = result +'	<th>Post-Tax Deduction</th>';
			    	result = result +'	<th>Taxes</th>';
			    	result = result +'    <th>Net Pay</th>'; 
			    	result = result +'    </tr>';
			    	result = result +'</thead>	';
			    	result = result +'<tbody>';
			    	
			    	for (var i=0; i<data.length; i++)
	   				{
			    	var report = data[i];
			    	result = result +'<tr>																													';	
		            
			   	 	/* result += '<td>';
		        	result += '<div class="checkbox-new pull-left ml5">';
		        	result += '<input id="'+i+'emp1" value="YES" class="checkbox-custom" name="uspayroll['+i+'].isExcept" type="checkbox">';
		        	result += '<label for="'+i+'emp1" class="checkbox-custom-label"></label>';
		        	result += '</div>';
		        	result += '</td>';  */
			    	
			    	if(report.empId != null && report.empId != '' && report.empId != 'undefined'){
		            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.empId+'</div></td>                                                      ';
		            }else{
		            	result = result +'  <td><div class="tblminwidth-50 clearfix">--</div></td>                                                      ';
		            }		
			    	
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
		            
		            result = result +'</tr>                                                                                                                 ';
	   			}
			    	result = result +'</tbody>';
				    $("#reviewDiv").empty();
				    $("#reviewDiv").append(result);
// 				    reviewPagination();
		        }
		    });
	  
  }
  
  

  </script>

<jsp:include page="../masterFooter.jsp" />