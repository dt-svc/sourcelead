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
		   <li class="current"><a href="us-runpayroll-list.html">Review Payroll</a></li>
		   <li><a id="approvePayrollId" href="us-approvepayroll.html">Approve Payroll</a></li>
	   </ul>
   </div>
   
    <div class="candidate_wrap_table datatable_filter backgroundWhite">
    <div class="positionRelative timesheet_head minHeight clearfix"></div>
    	<div id="reviewDiv"></div>
    </div>
    
            <nav class="clearfix">
          <ul class="pull-right mr list-unstyled list-inline">
<!--             <li><a href="#" class="btn btn-primary btnSuccess" onclick="sendPayrollForApproval();">Sent for Approve</a></li> -->
            <li><a href="#" class="btn btn-default ml mr" onclick="sendPayrollForApproval();">Confirm</a></li>
            <li><a href="#" class="btn btn-default">Cancel</a></li>
          </ul>
          <div class="clearfix"></div>
        </nav>
    
    
    <div class="clearfix"></div>
<!--     <div class="timesheet_out">
      <div class="tablelist-wrap">
        <div class="timesheet_head minHeight">
          <div class="col-md-12 mt">
            <div class="row">
              <div class="col-md-2">
                  <select class="sel selheight30">
                    <option selected>Group</option>
                    <option>Group-1</option>
                    <option>Group-2</option>
                    <option>Group-3</option>
                  </select>
              </div>
              <div class="col-md-2">
                   <select class="sel selheight30">
                    <option selected>Pay Period</option>
                    <option>01/02/2018</option>
                    <option>01/02/2018</option>
                    <option title="09/02/2018 - 15/01/2018" value="09/02/2018 - 15/01/2018">09/02/2018 - 15/01/2018</option>
                  </select>
              </div>
              <div class="col-md-2">
                <input name="" class="form-control calendar input-sm" placeholder="Check Date" type="text">
              </div>
              <div class="col-md-2">
                   <select class="sel selheight30">
                    <option selected>Rate</option>
                    <option>LCA Wages</option>
                    <option>Salary</option>
                    <option>Project</option>
                  </select>
              </div>
              <div class="col-md-2">
                   <select class="sel selheight30">
                    <option selected>Payroll Type</option>
                    <option>Worksheet</option>
                    <option>General</option>
                  </select>
              </div>
              <div class="col-md-2">
              	<a href="run-payroll.html" class="btn btn-success btnSuccess btn-sm anchorWhite btn-block">Run Payroll</a>
              </div>
            </div>
          </div>
          <div class="clearfix"></div>
        </div>
        <div class="tableScroll styleTables">
         <div class="tableScroll styleTables">
          <div class="clearfix mt">
            <table id="example" class="stripe row-border order-column" style="width:100%">
              <thead>
                <tr>
                  <th>
                     <div class="checkbox-new pull-left">
                      <input id="all" class="checkbox-custom" name="paidComponent" type="checkbox">
                      <label for="all" class="checkbox-custom-label"></label>
                    </div>
                  </th>
                  <th>Emp ID</th>
                  <th>Emp Name</th>
                  <th>Regular
                    Hours</th>
                  <th>OT
                    Hours</th>
                  <th>Gross
                    Pay</th>
                  <th>Pretax
                    Deduction</th>
                  <th>Post
                    Deduction</th>
                  <th>Taxes</th>
                  <th>Net
                    Pay</th>
                </tr>
              </thead>
              <tbody>
					<tr>
					<td><div class="checkbox-new pull-left ml5">
					<input id="emp1" class="checkbox-custom" name="paidComponent" type="checkbox">
					<label for="emp1" class="checkbox-custom-label"></label>
					</div></td>
					<td><div class="tblminwidth-50"> 0022338790 </div></td>
					<td><div class="tbldata-dotdot-vsm"> Rajkumarvinnokota Animolkumar </div></td>
					<td><div class="tblminwidth-50"> 40Hrs </div>
					<div class="tblminwidth-50 clearfix text-danger"> 200$ </div></td>
					<td><div class="tblminwidth-50"> 3Hrs </div>
					<div class="tblminwidth-50 clearfix text-danger"> 100$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 300$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 50$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 50$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 100$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> <a href="javascript:void(0)" onClick="usRunpayroll();">200$</a> </div></td>
					</tr>
					<tr>
					<td><div class="checkbox-new pull-left ml5">
					<input id="emp2" class="checkbox-custom" name="paidComponent" type="checkbox">
					<label for="emp2" class="checkbox-custom-label"></label>
					</div></td>
					<td><div class="tblminwidth-50"> 0022338790 </div></td>
					<td><div class="tbldata-dotdot-vsm"> Rajkumarvinnokota Animolkumar </div></td>
					<td><div class="tblminwidth-50"> 40Hrs </div>
					<div class="tblminwidth-50 clearfix text-danger"> 200$ </div></td>
					<td><div class="tblminwidth-50"> 3Hrs </div>
					<div class="tblminwidth-50 clearfix text-danger"> 100$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 300$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 50$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 50$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 100$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> <a href="javascript:void(0)" onClick="usRunpayroll();">200$</a> </div></td>
					</tr>                
					<tr>
					<td><div class="checkbox-new pull-left ml5">
					<input id="emp3" class="checkbox-custom" name="paidComponent" type="checkbox">
					<label for="emp3" class="checkbox-custom-label"></label>
					</div></td>
					<td><div class="tblminwidth-50"> 0022338790 </div></td>
					<td><div class="tbldata-dotdot-vsm"> Rajkumarvinnokota Animolkumar </div></td>
					<td><div class="tblminwidth-50"> 40Hrs </div>
					<div class="tblminwidth-50 clearfix text-danger"> 200$ </div></td>
					<td><div class="tblminwidth-50"> 3Hrs </div>
					<div class="tblminwidth-50 clearfix text-danger"> 100$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 300$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 50$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 50$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 100$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> <a href="javascript:void(0)" onClick="usRunpayroll();">200$</a> </div></td>
					</tr>
					<tr>
					<td><div class="checkbox-new pull-left ml5">
					<input id="emp4" class="checkbox-custom" name="paidComponent" type="checkbox">
					<label for="emp4" class="checkbox-custom-label"></label>
					</div></td>
					<td><div class="tblminwidth-50"> 0022338790 </div></td>
					<td><div class="tbldata-dotdot-vsm"> Rajkumarvinnokota Animolkumar </div></td>
					<td><div class="tblminwidth-50"> 40Hrs </div>
					<div class="tblminwidth-50 clearfix text-danger"> 200$ </div></td>
					<td><div class="tblminwidth-50"> 3Hrs </div>
					<div class="tblminwidth-50 clearfix text-danger"> 100$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 300$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 50$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 50$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 100$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> <a href="javascript:void(0)" onClick="usRunpayroll();">200$</a> </div></td>
					</tr>					
					<tr>
					<td><div class="checkbox-new pull-left ml5">
					<input id="emp5" class="checkbox-custom" name="paidComponent" type="checkbox">
					<label for="emp5" class="checkbox-custom-label"></label>
					</div></td>
					<td><div class="tblminwidth-50"> 0022338790 </div></td>
					<td><div class="tbldata-dotdot-vsm"> Rajkumarvinnokota Animolkumar </div></td>
					<td><div class="tblminwidth-50"> 40Hrs </div>
					<div class="tblminwidth-50 clearfix text-danger"> 200$ </div></td>
					<td><div class="tblminwidth-50"> 3Hrs </div>
					<div class="tblminwidth-50 clearfix text-danger"> 100$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 300$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 50$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 50$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 100$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> <a href="javascript:void(0)" onClick="usRunpayroll();">200$</a> </div></td>
					</tr>
					<tr>
					<td><div class="checkbox-new pull-left ml5">
					<input id="emp6" class="checkbox-custom" name="paidComponent" type="checkbox">
					<label for="emp6" class="checkbox-custom-label"></label>
					</div></td>
					<td><div class="tblminwidth-50"> 0022338790 </div></td>
					<td><div class="tbldata-dotdot-vsm"> Rajkumarvinnokota Animolkumar </div></td>
					<td><div class="tblminwidth-50"> 40Hrs </div>
					<div class="tblminwidth-50 clearfix text-danger"> 200$ </div></td>
					<td><div class="tblminwidth-50"> 3Hrs </div>
					<div class="tblminwidth-50 clearfix text-danger"> 100$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 300$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 50$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 50$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 100$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> <a href="javascript:void(0)" onClick="usRunpayroll();">200$</a> </div></td>
					</tr>                
					<tr>
					<td><div class="checkbox-new pull-left ml5">
					<input id="emp7" class="checkbox-custom" name="paidComponent" type="checkbox">
					<label for="emp7" class="checkbox-custom-label"></label>
					</div></td>
					<td><div class="tblminwidth-50"> 0022338790 </div></td>
					<td><div class="tbldata-dotdot-vsm"> Rajkumarvinnokota Animolkumar </div></td>
					<td><div class="tblminwidth-50"> 40Hrs </div>
					<div class="tblminwidth-50 clearfix text-danger"> 200$ </div></td>
					<td><div class="tblminwidth-50"> 3Hrs </div>
					<div class="tblminwidth-50 clearfix text-danger"> 100$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 300$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 50$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 50$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 100$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> <a href="javascript:void(0)" onClick="usRunpayroll();">200$</a> </div></td>
					</tr>
					<tr>
					<td><div class="checkbox-new pull-left ml5">
					<input id="emp8" class="checkbox-custom" name="paidComponent" type="checkbox">
					<label for="emp8" class="checkbox-custom-label"></label>
					</div></td>
					<td><div class="tblminwidth-50"> 0022338790 </div></td>
					<td><div class="tbldata-dotdot-vsm"> Rajkumarvinnokota Animolkumar </div></td>
					<td><div class="tblminwidth-50"> 40Hrs </div>
					<div class="tblminwidth-50 clearfix text-danger"> 200$ </div></td>
					<td><div class="tblminwidth-50"> 3Hrs </div>
					<div class="tblminwidth-50 clearfix text-danger"> 100$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 300$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 50$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 50$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> 100$ </div></td>
					<td><div class="tblminwidth-50 clearfix"> <a href="javascript:void(0)" onClick="usRunpayroll();">200$</a> </div></td>
					</tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="clearfix mt"></div>
        <nav class="clearfix">
          <div class="col-md-2"> <a href="javascript:void(0)" class="btn anchorBlue">Exclude</a> </div>
          <ul class="pull-right mr list-unstyled list-inline">
            <li><a href="#" class="btn btn-primary btnSuccess">Sent for Approve</a></li>
            <li><a href="#" class="btn btn-default ml mr">Confirm</a></li>
            <li><a href="#" class="btn btn-default">Cancel</a></li>
          </ul>
          <div class="clearfix"></div>
        </nav>
      </div>
    </div> -->
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
			    	result = result +'	<th></th>';
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
		            
			   	 	result += '<td>';
		        	result += '<div class="checkbox-new pull-left ml5">';
		        	result += '<input id="'+i+'emp1" value="YES" class="checkbox-custom" name="uspayroll['+i+'].isExcept" type="checkbox">';
		        	result += '<label for="'+i+'emp1" class="checkbox-custom-label"></label>';
		        	result += '</div>';
		        	result += '</td>'; 
			    	
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
  
  
/*   function reviewPagination(){  
	    $('#reviewTableId').DataTable( { 
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
	} */
  </script>

<jsp:include page="../masterFooter.jsp" />