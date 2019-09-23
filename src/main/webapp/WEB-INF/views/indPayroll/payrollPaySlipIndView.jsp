<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 <script type="text/javascript">
$(document).ready(function(){
	getIndPayslipList();
});
</script>

<section class="content-wraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Configure Payslip View</h2>
  </div>
  <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;  <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; <a href="<c:url value='${appPath}/payrollConfiguration'/>">Payroll Configure</a> &raquo; Payslip View</div>
</section>
 <div class="clearfix"></div>
 
 
 
 <div class="timesheet_out timesheet_background">
 <div class="positionRelative timesheet_head clearfix minHeight40"></div>
	<div class="clearfix">
	<div class="project_tabs_inn_table positionRelative">
	<div id="indPaySlipViewDivId" class="datatable_filter mt5"></div>
	</div>
	</div>
</div>

      
</section>
 
<script>
function getIndPayslipList(){
	 var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
			type : "POST",
			url : sourceleadUrl+ "/getPayrollPaySlipViewList?${_csrf.parameterName}=${_csrf.token}",
			success : function(json) {
				var result = "";
				result = result +'<table id="paySlipPagination">';
   		    	result = result +'<thead>';
   		    	result = result +'	<tr>';
   		    	result = result +'	<th>Payslip Name</th>';
   		    	result = result +'  <th>Description</th>'; 
   		    	result = result +'  <th>Default</th>';
   		    	result = result +'  <th>Action</th>';
   		    	result = result +'  </tr>';
   		    	result = result +'</thead>	';
   		    	result = result +'<tbody>';
   		    	for (var i=0; i<json.length; i++)
   				{
   		    		var report = json[i];
   		    		result = result +'<tr>';
   		    		result = result +'<td>'
   		    		result = result +'<div class="tbldata-dotdot-me" title="'+report.paySlipName+'">'+report.paySlipName+'</div>';
   		    		result = result +'</td>';
   		    		
   		    		result = result +'<td>'
   		    		result = result +'<div class="tbldata-dotdot-verybig" title="'+report.paySlipDescription+'">'+report.paySlipDescription+'</div>';
   		    		result = result +'</td>';
   		    		
   		    		result = result +'<td>'
   		    		result = result +'<div class="tbldata-dotdot-100">';
			   		result = result +'	<div class="toggle-button">';
				   	result = result +'		<label class="switch" style="top: 5px;">';
				   	if(report.isActive == "Y"){
					   	result = result +'		<input onclick="changeDefaultPayslip(\''+report.uuid+'\')" value="'+report.isActive+'"  id="'+report.id+'defaultPayslip"  type="checkbox" checked> ';
				   	}else{
					   	result = result +'		<input onclick="changeDefaultPayslip(\''+report.uuid+'\')" value="'+report.isActive+'" id="'+report.id+'defaultPayslip" type="checkbox" > ';
				   	}
				   	result = result +'		<div class="slider round" style="position:absolute !important;">';
				   	result = result +'		</div> ';
				   	result = result +'		</label> ';
				   	result = result +'		</div>';
			   		result = result +'	</div>';
   		    		result = result +'</td>';
   		    		
   		    		result = result +'<td>'
   		    		result = result +'  <div class="tbldata-dotdot-100 text-center">';
		    			result = result +'<a href="<c:url value="${appPath}/payrollPaySlip?paySlipUUID='+report.uuid+'"/>"  class="glyphicon glyphicon-edit" title="Edit"></a>';	
		    			if(report.deletePermission != "N"){
	   		    			result = result +'<a class="glyphicon glyphicon-trash text-danger ml" title="Delete" onclick="deletePayslipInd(\''+report.uuid+'\','+report.id+');"></a>';	
		    			}else{
	   		    			result = result +'<a class="fa fa-ban text-muted ml colorRed" title="Defalut payslip cant be deleted"></a>';	
 
		    			}
   		    		result = result +'</div>';
   		    		result = result +'</td>';
   		    		result = result +'</tr>';
   		    		
   				}
				$("#indPaySlipViewDivId").empty();
				$("#indPaySlipViewDivId").append(result);
				payslipPagination();
			}
		});
		
}
function payslipPagination(){
	$('#paySlipPagination').DataTable( { 
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

function deletePayslipInd(uuid,id){
	var sourceleadUrl = '${sourceleadUrl}';
	var defaultStatus = $("#"+id+"defaultPayslip").val();
	$.ajax({
		type : "POST",
		url : sourceleadUrl+ "/deletePayrollPaySlip?${_csrf.parameterName}=${_csrf.token}&uuid="+uuid+"&defaultStatus="+defaultStatus,
		success : function(json) {
			getIndPayslipList();
		}
	});
}
function changeDefaultPayslip(uuid){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
		type : "POST",
		url : sourceleadUrl+ "/changeDefaultPayslip?${_csrf.parameterName}=${_csrf.token}&uuid="+uuid,
		success : function(json) {
			getIndPayslipList();
		}
	});
	
}
</script>
  

<jsp:include page="../masterFooter.jsp" />