<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="/resources/js/indPayrollReports.js"></script>

<script>
$(document).ready(function(){
   getDepartmentList();
});
</script>
<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Salary Statement Report </h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/indPayrollReportList'/>">India Payroll Reports List</a> &raquo; Salary Statement Report</div>
    </section>
    
    <div class="contentareaInner-styles clearfix">
    <div id="payrollReportLoading"></div>
    <form:form id="payrollregister_form" name="apply_leave_form" method="post">
    <div class="clearfix minHeight">
        <div class="clearfix row">
        <div class="col-md-5">
        
          <div class="form-group">
              <label class="col-md-12 paddingRight">Financial Year<span class="text-danger">&nbsp;*</span></label>
				<div class="col-md-12">
					<select class="form-control sel" id="financialYear" name="financialYear">
					   <option value ="">Select Year</option>
				       <c:forEach var="year" items="${finYearList}">
					       <option value="${year}">${year}</option>
			            </c:forEach>
					</select>
				</div>
            </div>
            <div class="form-group">
              <label class="col-md-12 paddingRight">Select Department<span class="text-danger">&nbsp;*</span></label>
				<div class="col-md-12">
					<select class="form-control sel" id="payrollDepartment" name="departmentId" >
					</select>
				</div>
            </div>
             
            
             
        </div>
        
          <div class="col-md-5">
          <div class="form-group">
              <label class="col-md-12">Month<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-12">
                  <select class="form-control chosen-select" id="salaryMonthName" name="salaryMonthName">
                        <option>Select Month</option>
						<option>January</option>
						<option>Feburary</option>
						<option>March</option>
						<option>April</option>
						<option>May</option>
						<option>June</option>
						<option>July</option>
						<option>August</option>
						<option>September</option>
						<option>October</option>
						<option>November</option>
						<option>December</option>
				 </select>
              </div>
            </div>
            
            
			</div>
            
      </div>
	<div class="clearfix footerstyelsNew">
        <div class="col-lg-12">
          <div class="pull-right">
            <input value="Run" class="btn btn-success btnSuccess" type="button" onclick="runIndPayrollTYDReport();">
            <input value="Reset" class="btn btn-defualt ml10" type="button" onclick="resetForm();">
          </div>
        </div>
      </div>      
	</div>
    </form:form>
     <div class="clearfix"></div>
    	<div id="popUp"></div>	
  	<div class="timesheet_out" id="payrollRegister" style="display: none;">
      <div class="clearfix">
        <div class="positionRelative reportHead reportHeadDataTab clearfix minHeight40"> 
  			<ul class="pull-right list-unstyled list-inline">
				   
  			</ul>
		</div>
        <div class="require_wrap_table  overflow_inherint datatable_filter">
        	<div id="payrollYTDSummaryTableDiv" class="datatable_filter"></div>
        </div>
      </div>
    </div>
     		
      <!-- Content Area Ends overflow_inherint--> 
    </div>
    
  </section>
<script>
function getDepartmentList(){
	$.ajax({
    	type: "GET",
        url: sourceleadUrl+"/getDepartmentList?&departmentStatus=1", 
        success: function(json) {
        var output = "";
       output = output + "<option value=''>Select Department</option>";
		for (var i=0; i<json.data.length; i++)
		{
			var opt = json.data[i];
			output = output + "<option value='" +opt.id+ "'>"+opt.departmentName+"</option>";
		}
		$("#payrollDepartment").empty();
		$("#payrollDepartment").append(output);
		$('#payrollDepartment').trigger("chosen:updated"); 
        },
    });	
}

</script>       
<script>
function resetForm(){
	 $("#payrollRegister").hide();
	 $("#financialYear").val('');
	 $("#payrollDepartment").val('');
	 $("#payrollDepartment").trigger("chosen:updated"); 
	 
	 $("#salaryMonthName").val('');
	 $("#salaryMonthName").trigger("chosen:updated"); 
}
</script>

<script>
function payrollBankTransferExport(type){
	var searchValue = $("#payrollYTDSearch").val();
	var department = $('#payrollDepartment').val();
	var employeeId = $('#employeeId').val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
    window.location.href = sourceleadUrl+"/indPayrollYTDSummaryExport?&departmentId="+department+"&employeeIds="+employeeId+"&startDate="+startDate+"&endDate="+endDate+"&searchValue="+searchValue+"&exportType="+type;
}
</script>
<script type="text/javascript">
function runIndPayrollTYDReport(){
	var searchValue = $("#payrollYTDSearch").val();
	var department = $('#payrollDepartment').val();
	var financialYear = $("#financialYear").val();
	var month = $('#salaryMonthName').val();
	if(department != null && department != '' && department != 'undefined' && month != null && month != '' && month != 'undefined' && financialYear != null && financialYear != "" && financialYear !="undefined"){
		$("#payrollReportLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$("#payrollRegister").show();
		$("#payrollYTDSearch").val('');
		$.ajax({
			type : "POST",
			url : sourceleadUrl+ "/indPayrollSaleryStatementReport?${_csrf.parameterName}=${_csrf.token}&departmentId="+department+"&financialYear="+financialYear+"&salaryMonthName="+month+"&searchValue="+searchValue,
			success : function(json) {
				$("#payrollReportLoading").removeAttr("style");
				 $("#payrollYTDSummaryTableDiv").empty();
				 var result = "";
				result = result + '<table id="payrollRegisterTable" class="display" cellspacing="0" width="100%" >';
				result = result + '<thead>';
				result = result + '<tr>';
				result = result + '<th>Associate id</th>';
		        result = result + '<th>Associate Name</th>';
		        result = result + '<th>Date of Join</th>';
		        result = result + '<th>Department Name</th>';
		        result = result + '<th>Gross Pay</th>';
		        result = result + '<th>Total Deductions</th>';
		        result = result + '<th>Net Pay </th>';
		        for (var i=0; i<1; i++)
   				{
					var report = json[i];
					for(var k in report.payHeadMapList){
						result = result +'     <th>'+k+'</th>';
					}
   				}
				result = result + '</tr>';
				result = result + '</thead>	';
				
				result = result +' <tbody>';
				
				for (var i=0; i<json.length; i++)
   				{
   		    		var report = json[i];
   		    		
   		    		result = result +'<tr>';
   		    		result = result +'<td>'
   		    		if(report.employeeid != null && report.employeeid != "" && report.employeeid != "undefined"){
     		    		result = result +'<div class="tbldata-dotdot-vsm" title="'+report.employeeid+'">'+report.employeeid+'</div>';
   		    		}else{
     		    		result = result +'<div class="tbldata-dotdot-vsm" >--</div>';
   		    		}
   		    		result = result +'</td>';
   		    		
   		    		result = result +'<td>'
     		    		result = result +'<div class="tbldata-dotdot-vsm" title="'+report.employeeName+'">'+report.employeeName+'</div>';
   		    		result = result +'</td>';
   		    		
   		    		result = result +'<td>'
   		    		if(report.startDate != null && report.startDate != "" && report.startDate !="undefined"){
 		    		result = result +'<div class="tbldata-dotdot_80" title="'+report.startDate+'">'+report.startDate+'</div>';
   		    		}else{
 		    		result = result +'<div class="tbldata-dotdot_80">--</div>';
   		    		}
		    		result = result +'</td>';
   		    		
		    		result = result +'<td>'
 		    		result = result +'<div class="tbldata-dotdot-vsm" title="'+report.departmentName+'">'+report.departmentName+'</div>';
		    		result = result +'</td>';
		    		
		    		result = result +'<td>'
 		    		result = result +'<div class="tbldata-dotdot_80" >'+report.grossPay+'</div>';
		    		result = result +'</td>';
		    		
		    		result = result +'<td>'
 		    		result = result +'<div class="tbldata-dotdot_80" >'+report.deducations+'</div>';
		    		result = result +'</td>';
		    		
		    		result = result +'<td>'
 		    		result = result +'<div class="tbldata-dotdot_80" >'+report.netPay+'</div>';
		    		result = result +'</td>';
   		    		
   		    		
			        for(var k in report.payHeadMapList){
			        	result = result +' <td><div class="tbldata-dotdot-vsm">'+report.payHeadMapList[k]+'</div></td>';	
			        }
   		    		result = result +'</tr>';
				}
				result = result +' </tbody>';
				
		        result = result +"</table>";
		     $("#payrollYTDSummaryTableDiv").append(result);
		     indPayrollSalaryStatementPagination();
			}
		  });
		
	}else{
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Please select Month, Financial Year and Department</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='removePopUp();' style='background-color: #FF0000; width : 100%'></b>";
		result = result +"	</div>";
		$("#popUp").append(result);
		$("#remove_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
}
function removePopUp(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>


	
	
	
<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>

<script src="https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>
<script src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.html5.min.js"></script>

<script type="text/javascript">
function indPayrollSalaryStatementPagination(){
	$('#payrollRegisterTable').DataTable( { 
		"scrollX": true,
		lengthMenu: [
            [ 10, 25, 50 ],
            [ '10', '25', '50 ']
        ], 
		 dom: 'lBfrtip',
        buttons: [{
        	 extend: 'excelHtml5',
             title: 'Salary Statement export'
           }
         ],
         "oLanguage": {
      		"sSearch": "",
      		"sZeroRecords": "", "sEmptyTable": ""
      	},
	
    } );
}
   
</script>

<script>	
function getActions(){
	$(".action-pop").slideToggle(200);
	$(".newActPop").slideUp(200);
}	
$("html").click(function(){
	$(".action-pop").slideUp(200);
});
$(".action, .action-pop").click(function(e){
 	e.stopPropagation();
});	
function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
}
$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});
</script>
 

<jsp:include page="../masterFooter.jsp" />