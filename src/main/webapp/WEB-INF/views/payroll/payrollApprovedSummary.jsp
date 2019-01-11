<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
$(document).ready(function(){
	var status = "${approverStatus}";
   getPaySheetFrequencySummary(status);
});
</script>


<!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Payroll List Summary</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/uSPayrollDashboard?&redirect=history'/>">Payroll Dashboard</a> &raquo; Payroll Summary</div>
    </section>
    
    <div class="clearfix"></div>
    <div class="candidate_wrap_table datatable_filter backgroundWhite">
    <div class="positionRelative timesheet_head minHeight clearfix"></div>
    	<div id="paySummaryDiv"></div>
    	<div id="approvedSummaryDiv"></div>
    </div>
    <div class="clearfix"></div>
    </section>
  
<script>
function getPaySheetFrequencySummary(status){
	var sourceleadUrl = '${sourceleadUrl}';
	var uuid = '${paySheetFrequencyUUID}';
	var payPeriodNum = '${payPeriodNum}';
	$("#approvedSummaryDiv").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
		type : "GET",
		url : sourceleadUrl+ "/getPaySheetFrequencySummary?paySheetFrequencyuuid="+uuid+"&payPeriodNum="+payPeriodNum,
		success : function(data) {
			    var result = "";
	        	result = result +'<table id="paySheetSummary">';
		    	result = result +'<thead>';
		    	result = result +'	<tr>';
		    	result = result +'	<th>Emp Name</th>';
		    	result = result +'	<th>Pay Per Salary</th>';
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
	            
	            if(report.payPerSal != null && report.payPerSal != '' && report.payPerSal != 'undefined'){
	            	result = result +'  <td><div class="tblminwidth-125 clearfix">$ '+report.payPerSal.toFixed(2)+'</div></td>                                                      ';
	            }else{
	            	result = result +'  <td><div class="tblminwidth-125 clearfix">$ 0.0</div></td>                                                      ';
	            }
	            
	            if(report.regularHours != null && report.regularHours != '' && report.regularHours != 'undefined'){
	            	result = result +'  <td><div class="tblminwidth-50 clearfix"><i class="fa fa-clock-o paddingRight"></i>'+report.regularHours+'</div>                                                      ';
	            }else{
	            	result = result +'  <td><div class="tblminwidth-50 clearfix"><i class="fa fa-clock-o paddingRight"></i>00:00</div>                                                      ';
	            }
	            
	            if(report.regularHrsTotalAmt != null && report.regularHrsTotalAmt != '' && report.regularHrsTotalAmt != 'undefined'){
	            	result = result +'<div class="tblminwidth-125 clearfix">$'+report.regularHrsTotalAmt.toFixed(2)+'</div></td>            ';
	            }else{
	            	result = result +'<div class="tblminwidth-125 clearfix">$ 0.0</div></td>            ';
	            }
	            
	            if(report.overtimeHours != null && report.overtimeHours != '' && report.overtimeHours != 'undefined'){
	            	result = result +'  <td><div class="tblminwidth-50 clearfix"><i class="fa fa-clock-o paddingRight"></i>'+report.overtimeHours+'</div>';
	            }else{
	            	result = result +'	<td><div class="tblminwidth-50 clearfix"><i class="fa fa-clock-o paddingRight"></i>00:00</div>            ';
	            }
	            
	            if(report.otHoursTotalAmt != null && report.otHoursTotalAmt != '' && report.otHoursTotalAmt != 'undefined'){
	            	result = result +' <div class="tblminwidth-125 clearfix">$ '+report.otHoursTotalAmt.toFixed(2)+'</div></td>            ';
	            }else{
	            	result = result +' <div class="tblminwidth-125 clearfix">$ 0.0</div></td>            ';
	            }
	            
	            if(report.grossPay != null && report.grossPay != '' && report.grossPay != 'undefined'){
	            	result = result +'  <td><div class="tblminwidth-125 clearfix">$ '+report.grossPay.toFixed(2)+'</div></td>                                                      ';
	            }else{
	            	result = result +'  <td><div class="tblminwidth-125 clearfix">$ 0.0</div></td>                                                      ';
	            }
	            
	            if(report.preTaxDeduction != null && report.preTaxDeduction != '' && report.preTaxDeduction != 'undefined'){
	            	result = result +'  <td><div class="tblminwidth-125 clearfix">$ '+report.preTaxDeduction.toFixed(2)+'</div></td>                                                      ';
	            }else{
	            	result = result +'  <td><div class="tblminwidth-125 clearfix">$ 0.0</div></td>                                                      ';
	            }
	            
	            if(report.postTaxDeduction != null && report.postTaxDeduction != '' && report.postTaxDeduction != 'undefined'){
	            	result = result +'  <td><div class="tblminwidth-125 clearfix">$ '+report.postTaxDeduction.toFixed(2)+'</div></td>                                                      ';
	            }else{
	            	result = result +'  <td><div class="tblminwidth-125 clearfix">$ 0.0</div></td>                                                      ';
	            }
	            
	            if(report.taxes != null && report.taxes != '' && report.taxes != 'undefined'){
	            	result = result +'  <td><div class="tblminwidth-125 clearfix">$ '+report.taxes.toFixed(2)+'</div></td>                                                      ';
	            }else{
	            	result = result +'  <td><div class="tblminwidth-125 clearfix">$ 0.0</div></td>                                                      ';
	            }
	            
	            if(report.netPay != null && report.netPay != '' && report.netPay != 'undefined'){
	            	result = result +'  <td><div class="tblminwidth-125 clearfix">$ '+report.netPay.toFixed(2)+'</div></td>                                                      ';
	            }else{
	            	result = result +'  <td><div class="tblminwidth-125 clearfix">$ 0.0</div></td>                                                      ';
	            }
	            

	            result = result +'  <td><div class="tblminwidth-50 clearfix">$ 0.0</div></td>                                                            ';
	            result = result +'  <td><div class="tblminwidth-50 clearfix">$ '+report.ytdDTO.ytdGross+'</div></td>                                                         ';

	            result = result +'  <td>                                                                                                                ';
				result = result +'	  <div class="tblminwidth-30 text-center">                                                                          ';
				result = result +'	  	<a class="anchorBlack"><i class="fa fa-eye" onclick="payAdjustment(\''+report.empPayrollUUID+'\',\''+status+'\');"></i></a>                                  ';
				result = result +'	  </div>                                                                                                            ';
	            result = result +'  </td>                                                                                                               ';
	            result = result +'</tr>                                                                                                                 ';
   			}
		    	result = result +'</tbody>';
			    $("#paySummaryDiv").empty();
			    $("#paySummaryDiv").append(result);
			    $("#approvedSummaryDiv").removeAttr('style');
			    paySummaryPagination();
		}
	});
}
</script>

 <script>
function paySummaryPagination(){  
    $('#paySheetSummary').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		"sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $('#paySheetSummary').wrap("<div class='overflow_Auto width100percnt'></div>")
}
</script>


<jsp:include page="./viewPayRollSummary.jsp" /> 

<jsp:include page="../masterFooter.jsp" />


 		