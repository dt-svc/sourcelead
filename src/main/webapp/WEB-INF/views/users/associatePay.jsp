<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
var pmsUrl ='${SourceLeadWeb_pmsUrl}';
</script>

<div class="clearfix col-md-12 styleTables">
 <div class="project_wrap_table datatable_filter mt40">
</div>
</div>

<script>
function getData(candidateId){
	alert("hai")
	$.ajax({
	    	type: "GET",
	        url: pmsUrl+"/getMyPayrollList?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,
	        success: function(result) {
	        	var data = result.myPayroll;
	        	
	        	var result = "";
		        result = result +'<table id="myPayrollTableDiv" class="display" cellspacing="0" width="100%" >';
		        result = result +'<thead>';
		        result = result +'<tr>';
		        result = result +'<th>Pay Period</th>';
		        result = result +'<th>Pay Date</th>';
		        result = result +'<th>Account Type</th>';
		        result = result +'<th>Gross Pay</th>';
		        result = result +'<th>Taxes</th>';
		        result = result +'<th>Deductions</th>';
		        result = result +'<th>Net Pay</th>';
		        result = result +'<th>Action</th>';
		        result = result +'</tr>';
		        result = result +'</thead>';
		        result = result +'<tbody>';
		        if(data != null && data.length >0){
		        	for(var i =0 ; i<data.length;i++){
		        	var json = data[i];
// 		        	totalNetPay = totalNetPay + json.netPay; 
		        	result = result +'<tr>';
		 	        result = result +'<td>';
		 	        result = result +'<div class="tblminwidth-100 text-center">';
		 	        if(json.payPeriodStartDate != null && json.payPeriodStartDate != '' && json.payPeriodStartDate != 'undefined'){
		 	        	result = result +'<div class="clearfix">'+json.payPeriodStartDate+'</div>';
		 	        }else{
		 	        	result = result +'<div>--</div>';
		 	        }
		 	        if(json.payPeriodEndDate != null && json.payPeriodEndDate != '' && json.payPeriodEndDate != 'undefined'){
		 	    	  result = result +'<div class="clearfix">'+json.payPeriodEndDate+'</div>';
		 	        }else{
		 	        	result = result +'<div>--</div>';
		 	        }
		 	        result = result +'</div>';
		 	        result = result +'</td>';
		 	        result = result +'<td>';
		 	        result = result +'	<div class="tblminwidth-125">';
		 	       if(json.payDate != null && json.payDate != '' && json.payDate != 'undefined'){
		 	    	  result = result +'<div class="clearfix">'+json.payDate+'</div>';
			 	   }else{
			 	        result = result +'<div>--</div>';
			 	   }
		 	        result = result +'</div>';
		 	        result = result +'</td>';
		 	        result = result +'<td>';
		 	        result = result +'<div class="tblminwidth-125">';
		 	        if(json.accountType != null){
		 	        	result = result +'<div>'+json.accountType+'</div>';	
		 	        }else{
		 	        	result = result +'<div>--</div>';
		 	        }
		 	        result = result +'</div>';
		 	        result = result +'</td>';
		 	        result = result +'<td>';
		 	        result = result +'<div class="tblminwidth-50">';
		 	       if(json.grossPay != null && json.grossPay != '' && json.grossPay != 'undefined'){
		 	    	    result = result +'<div>$ '+json.grossPay+'</div>';
				   }else{
				 	    result = result +'<div>--</div>';
				   }
		 	        
		 	        result = result +'</div>';
		 	        result = result +'</td>';
		 	        result = result +'<td>';
		 	        result = result +'<div class="tblminwidth-50">';
		 	       if(json.taxes != null && json.taxes != '' && json.taxes != 'undefined'){
		 	    	  result = result +'<div>$ '+json.taxes+'</div>';
				   }else{
				 	    result = result +'<div>--</div>';
				   }
		 	        result = result +'</div>';
		 	        result = result +'</td>';
		 	        result = result +'<td>';
		 	        result = result +'<div class="tblminwidth-50">';
		 	       if(json.deductions != null && json.deductions != '' && json.deductions != 'undefined'){
		 	    	    result = result +'<div>$ '+json.deductions+'</div>';
					}else{
					 	result = result +'<div>--</div>';
				    }
		 	        
		 	        result = result +'</div>';
		 	        result = result +'</td>';
		 	        result = result +'<td>';
		 	        result = result +'<div class="tblminwidth-50">';
		 	        if(json.netPay != null && json.netPay != '' && json.netPay != 'undefined'){
		 	        	result = result +'<div>$ '+json.netPay+'</div>';
					}else{
					 	result = result +'<div>--</div>';
				    }
		 	        
		 	        result = result +'</div>';
		 	        result = result +'</td>';
		 	        result = result +'<td>';
		 	        result = result +'<div class="tblminwidth-30">';
		 	        result = result +'<a href="#" class="anchorBlack" title="View"><i class="fa fa-eye" onclick="payAdjustment(\''+json.payrollUUID+'\',\'APPROVED\');"></i></a>';
		 	        result = result +'<a download href="'+json.paySlipFileURL+'" class="anchorBlack ml5" title="Download"><i class="fa fa-download"></i></a>';
		 	        result = result +'</div>';
		 	        result = result +'</td>';
		 	        result = result +'</tr>';
		 	        
		        	}
		        }
		        
		        result = result +'</tbody>';
		        result = result +'</table>';
		        
		        
		  		$("#tab-17").empty(); 
          		$("#tab-18").empty();  
          	    $("#tab-19").empty();
          	    $("#tab-20").empty();
          	    $("#candidateSideDiv").empty();  
          	    $("#tab-29").empty();  
          	  	$("#tab-25").empty();
          	    $("#tab-31").empty();
          	    $("#tab-31").append(result);
		        
		        mayPayrollPagination();
		        
	        }
	    });
	
}
</script>

<script>
function mayPayrollPagination(){ 
    $('#myPayrollTableDiv').DataTable( { 
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
    $("#myPayrollTableDiv").wrap("<div class='overflow_Auto width100percnt'></div>");
    $('.dataTables_filter input').attr("placeholder", "Search");
}
</script>

<jsp:include page="./viewPayRollSummary.jsp" />
