<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">
$(document).ready(function(){
	getAllPayrollBalanceReports();
});
</script>

 <section class="content-wraper" id="themeroller">
 <div class="success_pop" id="selector1" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
       </div>
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Balance Report</h2>
      </div>
		<%-- <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>"> Home</a> &raquo; Pending Leaves</div> --%>
    </section>
    
<div class="content-area content-new clearfix">

<div class="timesheet_out">
  <div class="timesheet_head clearfix seaHeight">
  		<div class='remove_search'><input type='text' placeholder='search' id='dataTableSearch'/></div> 
  </div>
    <div class="clients_wrap_table datatable_filter">
    		<div
			class="project_tabs_inn_table vendor_wrap_inn_table dataFilterNew pdtop">
			<!-- <div id="loading"></div> -->
			<div id="balanceListDiv" class="datatable_filter"></div>
		</div>
    </div>

</div>

    </div>
    <!-- Content Area Ends--> 
  </section>
  <!-- Content Area Ends--> 

<script>
function getAllPayrollBalanceReports(){
	        var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	  		var searchValue = $("#dataTableSearch").val();
	  		searchValue = searchValue ? searchValue : "";
	  		var url = sourceleadUrl+"/getAllPayrollBalanceReport?orgLocationId="+orgLocationId+"&searchValue="+searchValue;
	  		prepareClientList(url);
	  		
}	

	function prepareClientList(url) {
		/* $("#balanceListDiv").removeAttr("style"); */
		$("#balanceListDiv").empty();
		var result = "";
		result = result + "<table id='clientListPagination' class='display' cellspacing='0' width='100%' >";
		result = result + "<thead>";
		result = result + "  <tr>";
		result = result + "    <th>Employee Name</th>";
		result = result + "    <th>Direct Deposit</th>";
		result = result + "    <th>Processed with Check Option</th>";
		result = result + "    <th>Actual Checks Deposited</th>";
		result = result + "    <th>Manual Advance</th>";
		result = result + "    <th>General Expenses</th>";
		result = result + "    <th>Vendor Payment</th>"; 
		result = result + "    <th>Healthcare/Insurance</th>";  
		result = result + "    <th>Immigration</th>"; 
		result = result + "    <th>Direct Deposit Advance</th>"; 
		result = result + "    <th>Direct Deposit Deduction</th>";
		result = result + "    <th>Balance</th>";
		result = result + "  </tr>";
		result = result + "</thead>";
		result = result + "<tbody>";
		result = result + " </tbody>";
		result = result + "</table>";
		$("#balanceListDiv").append(result);
		clientpagination(url);
	}
	
	function clientpagination(url){
		var colIndex = 0;
		 $('#clientListPagination').DataTable( { 
	    	"processing": true,
	        "serverSide": true,
	        "searching": false,
	        "ajax": $.fn.dataTable.pipeline( {
	            url: url,
	            method: 'POST',
	            pages: 10
	        }),
	        "columns": [
	        	 { "data": "empName" },
	            { "data": "directDepositGross" },
	            { "data": "processedWithCheckOptionGross"},
	            { "data": "actualChecksDepositedNet" },
	            { "data": "manualAdvance"},
	            { "data": "generalExpenses" },
	            { "data": "vendorPayment" },
	            { "data": "healthcareInsurance" },
	            { "data": "immigration" },
	            { "data": "directDepositAdvanceNegetive" },
	            { "data": "directDepositDeductionPositive" },
	            { "data": "balance" }
	        ],
	        "columnDefs": [ {
		    	  	"targets": colIndex++,
		    	  	"data": "empName",
		    	  	"render": function(data, type, report, meta) {
		    	  		  var result = "";
		    	  		    result = result +' <td><div><span class="tbldata-dotdot-vsm">'+report.empName+'</span>';  
			              return  result;
		    	  	}
		      },{
		    	  	"targets": colIndex++,
		    	  	"data": "directDepositGross",
		    	  	"render": function(data, type, report, meta) {
		    	  		  var result = "";
		    	  		    result = result +' <td><div><span class="tbldata-dotdot-vsm">'+report.directDepositGross+'</span>';  
			              return  result;
		    	  	}
		      },{
	            "targets": colIndex++,
	            "data": "processedWithCheckOptionGross",
	        	"sortable": false,
	            "render": function ( data, type, report, meta ) {
	            	    var result = "";
	            	    result = result +' <td><div><span class="tbldata-dotdot-vsm">'+report.processedWithCheckOptionGross+'</span>'; 
		              return result;
	            }
	          }, {
	              "targets": colIndex++,
	              "data": "actualChecksDepositedNet",
	              "render": function ( data, type, report, meta ) {
	            	  var result = "";
	            	   result = result +' <td><div><span class="tbldata-dotdot-vsm">'+report.actualChecksDepositedNet+'</span>';
	  	              return result;
	              }
	            }, {
	                "targets": colIndex++,
	                "data": "manualAdvance",
	                "sortable": false,
	                "render": function ( data, type, report, meta ) {
	                	  var result = "";
	                		 result = result +' <td><div><span class="tbldata-dotdot-vsm">'+report.manualAdvance+'</span>'; 
	    	              return result;
	                }
	          },{
		    	  	"targets": colIndex++,
		    	  	"data": "vendorPayment",
		    	  	"render": function(data, type, report, meta) {
		    	  		var result = "";
		    	  		result = result +' <td><div><span class="tbldata-dotdot-vsm">'+report.vendorPayment+'</span>'; 
		    	  	   return result;
		    	  	}
		    	  },{
			    	  	"targets": colIndex++,
			    	  	"data": "healthcareInsurance",
			    	  	"render": function(data, type, report, meta) {
			    	  		var result = "";
			    	  		 result = result +' <td><div><span class="tbldata-dotdot-vsm">'+report.healthcareInsurance+'</span>'; 
			    	  	   return result;
			    	  	}
			    	  },{
			    	  	"targets": colIndex++,
			    	  	"data": "immigration",
			    	  	"render": function(data, type, report, meta) {
			    	  		var result = "";
			    	  		 result = result +' <td><div><span class="tbldata-dotdot-vsm">'+report.immigration+'</span>'; 
			    	  	   return result;
			    	  	}
			    	  },{
			    	  	"targets": colIndex++,
			    	  	"data": "directDepositAdvanceNegetive",
			    	  	"render": function(data, type, report, meta) {
			    	  		var result = "";
			    	  	 result = result +' <td><div><span class="tbldata-dotdot-vsm">'+report.directDepositAdvanceNegetive+'</span>'; 
			    	  	   return result;
			    	  	}
			    	  } ,{
			    	  	"targets": colIndex++,
			    	  	"data": "directDepositDeductionPositive",
			    	  	"render": function(data, type, report, meta) {
			    	  		var result = "";
			    	  		 result = result +' <td><div><span class="tbldata-dotdot-vsm">'+report.directDepositDeductionPositive+'</span>';
			    	  	   return result;
			    	  	}
			    	  },{
		    	  	"targets": colIndex++,
		    	  	"data": "balance",
		    	  	"render": function(data, type, report, meta) {
		    	  		var result = "";
		    	  		   /* result = result +' <td><div><span class="tbldata-dotdot-vsm">'+report.balance+'</span>'; */
		    	  		 return result;
		    	  	}
		    	  }],
		    	"scrollCollapse": false,
		    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
		    	"aaSorting": [[4,'desc']],
		    	"bLengthChange": true,
		    	"bDestroy": true,
		    	"ordering": true,
		    	"oLanguage": {
		     		"sSearch": "",
		     		 "sZeroRecords": "", "sEmptyTable": ""
		     		
		     }
	    } );
	    $("#clientListPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
	    $("input[type=search]").hide();
	    $("#dataTableSearch").keyup(function(e) {
	    	   if(e.which === 13) {
	    		   getClientList();
	    	   }
	    	});
}
</script>

<jsp:include page="../masterFooter.jsp" />