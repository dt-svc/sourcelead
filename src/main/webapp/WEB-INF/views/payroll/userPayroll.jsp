<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
window.onload = function () {
	getUserPayrollList();
}
</script>

  <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>User Payroll</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;User Payroll</div>
    </section>

<div class="project_side_wrap_inn_tabs clearfix">
	<ul class="project-tabs-menu pull-left">
		<li class="current"><a href="#tab-1" onclick="getUserPayrollList();">My Payroll</a></li>
		<li><a href="#tab-2" onclick="getInvoiceData();">Invoice Data</a></li>
		<li><a href="#tab-3" onclick="getWagesTaxSummaryByEmpId();">Balance report</a></li>
		<input type="hidden" id="empId" value="${employeeId}"/>
	</ul>
	<%-- <div class="pull-left tabBt">
		<a href="${appPath}/payrollReport?&fromType=Employee">Balance report</a>
	</div>	 --%>
	<div class="clearfix"></div>
	
	<div class="project_tabs">
		<div id="tab-1" class="project_tabs_inn" style="display: block;">
<div class="clearfix"></div>
	<div class="timesheet_head">
		<div class="payRoDate clearfix">
		<div class="col-md-2">
			<h5>My Payroll</h5>
		</div>
			<div class="col-md-2 dropdown-select col-md-offset-5">
				<select class="form-control input-sm" id="userPayId" onchange="getUserPayrollList();getYearsDropdown();">
                	<option selected value="">Select Year</option>
              </select>
            </div>
		</div>    	           
        </div>
        <div id="loading"></div>
    <div class="project_wrap_table datatable_filter">
    <div id="userPayrollListDiv"></div>
   </div>
   </div>
		<div id="tab-2" class="project_tabs_inn">
		<div class="timesheet_head minHeight"></div>
			<div class="project_wrap_table datatable_filter">
				<div id="invoiceDataDiv"></div>
			</div>
		</div>
		
		<div id="tab-3" class="project_tabs_inn">
				<div class="backWhite">        
					<div class="clearfix bttabHeighlite">
				      <ul class="nav nav-tabs" id="menuli">
					        <li class="active" id="wtsli" onclick="getWagesTaxSummaryByEmpId(); highlight(this);"><a id="wtsId" >Wages & Tax Summary</a></li>
					        <li onclick="getIncomeData(); highlight(this);"><a>Income</a></li>
					        <li onclick="getChecks(); highlight(this);"><a>Manual checks</a></li>
					        <li onclick="getAccontDeductions(); highlight(this);"><a>Account Deductions</a></li>
					        <li onclick="getHRDeductions(); highlight(this);"><a>HR Deductions</a></li>
					        <li onclick="finalBalance(); highlight(this);"><a>Balance</a></li>
				        	<div class="clearfix"></div>
				      </ul>
					</div>
				      
				      <div id="loading"></div>
				        
				      <div class="project_tabs clearfix pdtop">
					        <div id="innertab-1" class="require_wrap_table datatable_filter filter_position" style="display: block"></div>
					        <div id="innertab-2" class="require_wrap_table datatable_filter filter_position"></div>
					        <div id="innertab-3" class="require_wrap_table datatable_filter filter_position"></div>
					      	<div id="innertab-4" class="require_wrap_table datatable_filter filter_position"></div>
					        <div id="innertab-5" class="require_wrap_table datatable_filter filter_position"></div>
					        <div id="innertab-6" class="clearfix"></div>          
				      </div>
				</div>
		</div>
	</div>
	</div>
      <!-- Content Area Ends--> 
 
  </section>

<script>
function getUserPayrollList(){
	var userId = '${SourceLeadWeb_user.id}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var selectedYear = $("#userPayId").val();
	
	$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	var sourceleadUrl ='${sourceleadUrl}';
				  $.ajax({
				    	type: "GET",
				        url: sourceleadUrl+"/getUserPayrollList?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"&orgLocationId="+orgLocationId+"&selectedYear="+selectedYear,
				        success: function(json) {
				        var result = "";				        	
						result = result + '<table id="userPayrollList" class="display" cellspacing="0" width="100%" >';
						result = result + '<thead>';
						result = result + '<tr>';
						result = result + '<th>Pay Cycle Start Date</th>';
						result = result + '<th>Pay Cycle End Date</th>';
						result = result + '<th>Amount</th>';
						result = result + '</tr>';
						result = result + '</thead>	';
						result = result + '<tbody>';
			        	  for (var i=0; i<json.length; i++)
						{
			        		 var opt = json[i];
			        		    result = result + ' <tr>';
			        		    if(opt.payrollStartDateStr !=null && opt.payrollStartDateStr !='' && opt.payrollStartDateStr !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+opt.payrollStartDateStr+'</div></td>';
								}else{
									result = result + ' <td><div>--</div></td>';
								}
			        		    
			        		    if(opt.payrollEndDateStr !=null && opt.payrollEndDateStr !='' && opt.payrollEndDateStr !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+opt.payrollEndDateStr+'</div></td>';
								}else{
									result = result + ' <td><div>--</div></td>';
								}
								result = result + '<td><div class="tblminwidth-75">'+opt.amount+'</div></td>';
								result = result + ' </tr>';
						} 
						result = result + '  </tbody>';
						result = result + ' </table>';
						$("#userPayrollListDiv").empty();
						$("#userPayrollListDiv").append(result);
						$("#loading").removeAttr("style");
						userPayrollListPagination();
				        	
				        },
				        
				    });
		 
}
</script>

<script>
function userPayrollListPagination(){ 
    $('#userPayrollList').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $("#WagesAndTaxListTable").wrap("<div class='overflow_Auto'></div>")
}
</script>
<script>
function getInvoiceData(){
	var userId = '${SourceLeadWeb_user.id}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getPayrollInvoiceDataList?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"&orgLocationId="+orgLocationId,
	        success: function(json) {
	        var result = "";				        	
			result = result + '<table id="invoiceDataListTable" class="display" cellspacing="0" width="100%" >';
			result = result + '<thead>';
			result = result + '<tr>';
			result = result + '<th>SKU</th>';
			result = result + '<th>Product/Service</th>';
			result = result + '<th>Name</th>';
			result = result + '<th>Num</th>';
			result = result + '<th>Memo/Description</th>';
			result = result + '<th>Date</th>';
			result = result + '<th>Last Modified</th>';
			result = result + '<th>Due Date</th>';
			result = result + '<th>Pay Rate</th>';
			result = result + '<th>Qty</th>';
			result = result + '<th>Amount</th>';
			result = result + '<th>A/R Paid</th>';
			result = result + '<th>Old/New</th>';
			result = result + '</tr>';
			result = result + '</thead>	';
			result = result + '<tbody>';
	       	for (var i=0; i<json.data.length; i++){
	       		var opt = json.data[i];
       		    result = result + ' <tr>';
	       		if(opt.sku !=null && opt.sku !='' && opt.sku !='undefined'){
			    	  result = result + '<td><div class="tblminwidth-75">'+opt.sku+'</div></td>';
			    }else{
			    	  result = result + '<td><div class="tblminwidth-75">---</div></td>';
			    }
       		    
	       		if(opt.productService !=null && opt.productService !='' && opt.productService !='undefined'){
  		    	    result = result + '<td><div title="'+opt.productService+'">'+opt.productService+'</div></td>';
  		      	}else{
  		    	    result = result + '<td><div>---</div></td>';
  		      	}

	       		if(opt.empName !=null && opt.empName !='' && opt.empName !='undefined'){
					result = result + ' <td><div class="tblminwidth-75" title="'+opt.empName+'">'+opt.empName+'</div></td>';
				}else{
					result = result + ' <td><div class="tblminwidth-75">--</div></td>';
				}
	       		 
	       		var numberTotal = opt.number
            	var	twoPlacedFloat = parseFloat(numberTotal).toFixed(0);
	       		if(opt.number !=null && opt.number !='' && opt.number !='undefined'){
					 result = result + ' <td><div class="tblminwidth-75">'+twoPlacedFloat+'</div></td>';
				}else{
					 result = result + ' <td><div class="tblminwidth-75">--</div></td>';
				}
	       		
        		if(opt.memoDescription !=null && opt.memoDescription !='' && opt.memoDescription !='undefined'){
					  result = result + ' <td><div class="tblminwidth-75" title="'+opt.memoDescription+'">'+opt.memoDescription+'</div></td>';
				}else{
					  result = result + ' <td><div class="tblminwidth-75">--</div></td>';
				}
	       		
        		if(opt.dateStr !=null && opt.dateStr !='' && opt.dateStr !='undefined'){
					  result = result + ' <td><div class="tblminwidth-75">'+opt.dateStr+'</div></td>';
				}else{
					  result = result + ' <td><div class="tblminwidth-75">--</div></td>';
				}
	       		
        		if(opt.lastModifiedStr !=null && opt.lastModifiedStr !='' && opt.lastModifiedStr !='undefined'){
					result = result + '<td><div class="tblminwidth-75">'+opt.lastModifiedStr+'</div></td>';
				}else{
					result = result + ' <td><div class="tblminwidth-75">--</div></td>';
				}
	       		
        		if(opt.dueDateStr !=null && opt.dueDateStr !='' && opt.dueDateStr !='undefined'){
					result = result + '<td><div class="tblminwidth-75">'+opt.dueDateStr+'</div></td>';
				}else{
					result = result + ' <td><div class="tblminwidth-75">--</div></td>';
				}
	       		
        		var rateTotal = opt.payRate;
              	var twoPlacedFloat = parseFloat(rateTotal).toFixed(2);
              	if(opt.payRate !=null && opt.payRate !='' && opt.payRate !='undefined'){
					result = result + '<td><div class="tblminwidth-75">$ '+twoPlacedFloat+'</div></td>';
				}else{
					result = result + ' <td><div class="tblminwidth-75">0.00</div></td>';
				}
	       		
              	var qtytotal = opt.qty;
           	  	var twoPlacedFloat = parseFloat(qtytotal).toFixed(0);
           	 	if(opt.qty !=null && opt.qty !='' && opt.qty !='undefined'){
					result = result + '<td><div class="tblminwidth-75"> '+twoPlacedFloat+'</div></td>';
				}else{
					result = result + ' <td><div class="tblminwidth-75"> 0.00</div></td>';
				}
	       		
	           	var amountTotal = opt.amount;
	        	var twoPlacedFloat = parseFloat(amountTotal).toFixed(2); 
	        	if(opt.amount !=null && opt.amount !='' && opt.amount !='undefined'){
					result = result + '<td><div class="tblminwidth-75">$ '+twoPlacedFloat+'</div></td>';
				}else{
					result = result + ' <td><div class="tblminwidth-75">$ 0.00</div></td>';
				}
	       		
	        	if(opt.arPaid !=null && opt.arPaid !='' && opt.arPaid !='undefined'){
					result = result + '<td><div class="tblminwidth-75">'+opt.arPaid+'</div></td>';
				}else{
					result = result + ' <td><div class="tblminwidth-75">--</div></td>';
				}
	       		
	        	if(opt.oldNew !=null && opt.oldNew !='' && opt.oldNew !='undefined'){
					result = result + '<td><div class="tblminwidth-75">'+opt.oldNew+'</div></td>';
				}else{
					result = result + ' <td><div class="tblminwidth-75">--</div></td>';
				}
	       		
				result = result + ' </tr>';
			} 
			result = result + '  </tbody>';
			result = result + ' </table>';
			$("#invoiceDataDiv").empty();
			$("#invoiceDataDiv").append(result);
			$("#loading").removeAttr("style");
			invoiceDataListPagination();
	        	
	        },
	        
	    });
}
function invoiceDataListPagination(){ 
    $('#invoiceDataListTable').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $("#invoiceDataListTable").wrap("<div class='overflow_Auto'></div>")
}
</script>
<script>
$(function getYearsDropdown() {
	  var start_year = new Date().getFullYear();
	  for (var i = start_year; i > start_year - 118; i--) {
	    $('select').append('<option value="' + i + '">' + i + '</option>');
	  }
	});
</script>

<script>
//project side bar tabs
$(document).ready(function() {
	$("#tab-1").show()
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});
</script>
<jsp:include page="employeePayrollReport.jsp" />
<jsp:include page="../masterFooter.jsp" />