<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
$(document).ready(function(){
	$("#draftApproveDiv").addClass('disabled').removeAttr("onclick"); 
	$("#approveDiv").hide();
	getPayrollDraftSummary(); 
});
</script>


<script>
function reviewPayroll(){
	$("#approveDiv").hide();
	$("#reviewPayrollDiv").show();
	
	$("#cativeId").removeClass('current'); 
	$("#reviewId").addClass('current'); 
}

function approvePayrollTab(){
	 $("#reviewPayrollDiv").hide();
	$("#approveDiv").show(); 
	
	$("#reviewId").removeClass('current'); 
	$("#cativeId").addClass('current'); 
}
</script>

<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Review Payroll</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;  <a href="<c:url value='${appPath}/uSPayrollDashboard'/>">Payroll Dashboard</a> &raquo; Review Payroll</div>
    </section>
    
    <div class="tabsMenu clearfix">
   		<ul class="list-unstyled">
		   <li class="current" id="reviewId"><a onclick="reviewPayroll();">Review Payroll</a></li>
		   <c:if test="${SourceLeadWeb_isPayRollApprover == 'Y'}">
		   <li id="cativeId"><a id="draftApproveDiv" onclick="approvePayrollTab();">Approve Payroll</a></li>
		   </c:if>
	   </ul>
   </div>
   
   <%--  <div id="reviewPayrollDiv" class="candidate_wrap_table datatable_filter backgroundWhite">
   <form:form id="submit_for_approver" name="submit_for_approver" method="post">
    <div class="positionRelative timesheet_head minHeight clearfix"></div>
    	<div id="reviewDiv"></div>
    
    
            <nav class="clearfix">
          <ul class="pull-right mr list-unstyled list-inline">
<!--             <li><a href="#" class="btn btn-primary btnSuccess" onclick="sendPayrollForApproval();">Sent for Approve</a></li> -->
            <li><a href="#" class="btn btn-default ml mr" onclick="confirmPayroll();">Confirm</a></li>
            <li><a class="btn btn-default" href="<c:url value='${appPath}/payrollList'/>">Cancel</a></li>
          </ul>
          <div class="clearfix"></div>
        </nav>
    
    
    <div class="clearfix"></div>
    </form:form>
  </div> --%>
  
  
  <div id="reviewPayrollDiv">
<form:form id="submit_for_approver"  name="submit_for_approver" method="post">
 <div class="styleTables clearfix mt">
 <!-- <div id="USPayrollDiv"></div> -->
 <div id="reviewDiv" class="overflow_Auto"></div>
 <div id="draftSummaryDiv"></div>
</div> 

<div class="clearfix mt"></div>

<div class="clearfix">
<!-- <div class="col-md-2"><a href="javascript:void(0)" class="btn anchorBlue">Exclude</a></div> -->
	<div id="submitButtonDiv">
	<ul class="pull-right mr list-unstyled list-inline">
		<li><a href="#" class="btn btn-primary btnSuccess" id="Submit" onClick="sendPayrollForApprover();">Submit for Approve</a></li>
		<c:if test="${SourceLeadWeb_isPayRollApprover == 'Y'}">
		<li><a href="#" class="btn btn-default ml mr" id="Confirm" onclick="confirmPayroll('FROMDRAFT');">Confirm</a></li>
		</c:if>
		<li><a class="btn btn-default" href="<c:url value='${appPath}/payrollList?&redirect=draft'/>">Cancel</a></li>
	</ul>
	</div>
<div class="clearfix"></div>
</div>
</form:form>
</div>
  
  <jsp:include page="usPayrollApprove.jsp" />
  <jsp:include page="payrollAdjustment.jsp" />
  </section>
  <!-- Content Area Ends--> 
  
  <script>
  function getPayrollDraftSummary(){
	  var uuid = '${paySheetFrequencyUUID}';
	  $("#draftSummaryDiv").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 $.ajax({
		    	type: "GET",
		    	url : sourceleadUrl+ "/getPaySheetFrequencySummary?paySheetFrequencyuuid="+uuid+"&approverStatus=DRAFT",                
		        success: function(data) {
					var json = data.payrollDTO;
		        	var result = "";
		        	result = result +'<table id="reviewTableId">';
			    	result = result +'<thead>';
			    	result = result +'	<tr>';
			    	result = result +'	<th>Exclude</th>';
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
			    	result += '<input type="hidden" value='+data.id+' id="payrollFrequencyId" name="payrollFrequencyId" />';
		        	result += '<input type="hidden" value='+data.payrollFrequencyUUID+' id="payrollFrequencyUUID" name="payrollFrequencyUUID" />';
		        	result += '<input type="hidden" value='+data.payPeriodNum+' id="payPeriodNum" name="payPeriodNum" />';
			    	for (var i=0; i<json.length; i++)
	   				{
			    	var report = json[i];
			    	result = result +'<tr>																													';	
		            
			   	 	result += '<td>';
			   	 	if(json.length > 1){
		        	result += '<div class="checkbox-new pull-left ml5">';
		        	result += '<input id="'+i+'emp1" value="YES" class="checkbox-custom" name="uspayroll['+i+'].isExcept" type="checkbox">';
		        	result += '<label for="'+i+'emp1" class="checkbox-custom-label"></label>';
		        	result += '</div>';
			   	 	}
		        	result += '</td>'; 
		        	result += '<input type="hidden" value='+report.empPayrollId+' id="'+i+'empPayrollId" name="uspayroll['+i+'].empPayrollId" />';
			    	if(report.empId != null && report.empId != '' && report.empId != 'undefined'){
		            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.empId+'</div></td>                                                      ';
		            }else{
		            	result = result +'  <td><div class="tblminwidth-50 clearfix">--</div></td>                                                      ';
		            }		
			    	
			    	if(report.empName != null && report.empName != '' && report.empName != 'undefined'){
		            	result = result +'  <td><div class="tbldata-dotdot">'+report.empName+'</div></td>                                                      ';
		            }else{
		            	result = result +'  <td><div class="tbldata-dotdot">--</div></td>                                                      ';
		            }
		            
		            if(report.payRate != null && report.payRate != '' && report.payRate != 'undefined'){
		            	result = result +'  <td><div class="tblminwidth-125 clearfix">$ '+report.payRate.toFixed(2)+'</div></td>                                                      ';
		            }else{
		            	result = result +'  <td><div class="tblminwidth-125 clearfix">$ 0.0</div></td>                                                      ';
		            }
		            
		            if(report.regularHours != null && report.regularHours != '' && report.regularHours != 'undefined'){
		            	result = result +'  <td><div class="tblminwidth-50 clearfix"><i class="fa fa-clock-o paddingRight"></i>'+report.regularHours+'</div>                                                      ';
		            }else{
		            	result = result +'  <td><div class="tblminwidth-50 clearfix"><i class="fa fa-clock-o paddingRight"></i>00:00</div>                                                      ';
		            }
		            
		            if(report.regularHrsTotalAmt != null && report.regularHrsTotalAmt != '' && report.regularHrsTotalAmt != 'undefined'){
		            	result = result +'<div class="tblminwidth-125 clearfix">$ '+report.regularHrsTotalAmt.toFixed(2)+'</div></td>            ';
		            }else{
		            	result = result +'<div class="tblminwidth-125 clearfix">$ 0.0</div></td>            ';
		            }
		            
		            if(report.overtimeHours != null && report.overtimeHours != '' && report.overtimeHours != 'undefined'){
		            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.overtimeHours+'</div>';
		            }else{
		            	result = result +'	<td><div class="tblminwidth-50 clearfix mt5"><i class="fa fa-clock-o paddingRight"></i>00:00</div>            ';
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
// 		            	result = result +'  <td><div class="tblminwidth-50 clearfix">'+report.netPay.toFixed(2)+'</div></td>                                                      ';
		            	result += '<td><div class="tblminwidth-125 clearfix"><a onClick="payAdjustment(\''+report.empPayrollUUID+'\',\'DRAFT\');">$ '+report.netPay.toFixed(2)+'</a></div></td>';
		            }else{
		            	result = result +'  <td><div class="tblminwidth-125 clearfix">$ 0.0</div></td>                                                      ';
		            }
		            
		            result = result +'</tr>                                                                                                                 ';
	   			}
			    	result = result +'</tbody>';
				    $("#reviewDiv").empty();
				    $("#reviewDiv").append(result);
				    $("#draftSummaryDiv").removeAttr('style');
// 				    reviewPagination();
		        }
		    });
	  
  }
  </script>
  
  
  
<script type="text/javascript">
function sendPayrollForApprover(){
      var formData = $($('#submit_for_approver')[0]).serialize();
      $.post(sourceleadUrl+"/sendPayrollForApprover?"+tokenParamName+"="+tokenValue, formData, function(result) {
        if (result && result.response === "INVALID_DATA") {
              showError(result.responseMessage);
          } else if (result.response === "AWKWARD") {
              location.href = "/errorPage";
          } else if (result.response === "SUCCESS") {
        	  location.replace(sourceleadUrl+ "/payrollList?&redirect=history"); 
        	  $("#msgDiv").html("Submited Successfully");
          } else if (result.indexOf('Sign In') !== -1) {
            location.replace(sourceleadUrl);
          } else {
              showError("Operation failed, please try again");
          }
      });   
    
}
</script>
  
 <script>
function confirmPayroll(fromState){
	$("#cativeId").attr("onClick","approvePayrollTab();");
	$("#reviewId").removeClass('current'); 
	$("#draftApproveDiv").removeClass('disabled');
	$("#cativeId").addClass('current'); 
	
	$("#reviewPayrollDiv").hide();
	$("#approveDiv").show();
	 var confirmType = "DRAFT";
	 var formData = $($('#submit_for_approver')[0]).serialize();
	 calculateTotalPay(formData,confirmType,fromState);
}
</script>

<jsp:include page="../masterFooter.jsp" />
