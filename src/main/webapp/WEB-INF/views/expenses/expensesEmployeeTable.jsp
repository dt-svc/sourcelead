<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 <script>
function getExpensesList(sourceleadUrl){

	var url = '';
    var searchValue = $("#dataTableSearchExpensesEmp").val();
	searchValue = searchValue ? searchValue : "";
	var startDate = $("#filterStartDate").val();
	var endDate = $("#filterEndDate").val();
	var expenseStatus = [];
	$('input:checkbox[name=expenseStatus]:checked').each(function() 
	{
		expenseStatus.push($(this).val());
	});
	var userId = '${SourceLeadWeb_user.id}';
	var locationCode = '${SourceLeadWeb_orgLocations.locationCode}'
	$("#expensesListDiv").empty();
	url = sourceleadUrl+"/getExpensesList?expenseStatus="+expenseStatus+"&startDate="+startDate+"&endDate="+endDate+"&searchValue="+searchValue+"&userId="+userId+"&locationCode="+locationCode;
	
	$('#dataTableSearchExpensesEmp').removeAttr('disabled');
    $('#dataTableSearchExpensesEmp').attr('disabled','disabled');
     prepareExpensesApprovalDataEmp(url,sourceleadUrl); 
} 



 function prepareExpensesApprovalDataEmp(url,sourceleadUrl){
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  var expensesReqListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  var expensesReqListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  var expensesReqListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  var expensesReqListDate = 'YYYY-DD-MM'; 
	  }
	  
	    var result = "";
	    result = result +"<table id='example' class='display' cellspacing='0' width='100%' >"; 
	    result = result +"<thead>"; 
	    result = result+" <tr>"; 
	    result = result +"<th>Exp Id</th>";
		result = result +"<th>Purpose</th>";
		result = result +"<th>Project</th>";
		result = result +"<th>Client</th>";
		result = result +"<th>Start Date<br>(<small class='custom-small'>"+expensesReqListDate+"</small>)</th>";
		result = result +"<th>End Date<br>(<small class='custom-small'>"+expensesReqListDate+"</small>)</th>";
		result = result +"<th>No of Items</th>";
		result = result +"<th>Amount</th>";
		result = result +"<th>Status</th>";
	    result = result+" </tr>"; 
	    result = result+" </thead>";
	    result = result+" <tbody>"; 
	    
	    result = result+" </tbody>";
	    result = result+" </table>"; 
	    
   $("#expensesListDiv").append(result); 
   ApprovalpaginationEmp(url,sourceleadUrl); 
   
   /* to call requirement conformation saving message */
}

function ApprovalpaginationEmp(url,sourceleadUrl){
	var colIndex = 0;
	 	   $('#example').DataTable( {
	   	   "processing": true,
	       "serverSide": true,
	       "searching": false,
	       "ajax": $.fn.dataTable.pipeline({
	           url: url,
	           method: 'POST',
	           pages: 1
	       }),
	       "columns": [
	    	   { "data": "ExpId"},
	           { "data": "Purpose" },
	           { "data": "Project"},
	           { "data": "client" },
	           { "data": "Start Date" },
	           { "data": "End Date"},
	           { "data": "No of Items"},
	           { "data": "Amount" },
	           { "data": "Status"}
	           
	       ],
	       "columnDefs": [ {
		   	  	"targets": colIndex++,
			  	"data": "ExpId",
			  	"sortable": false,
			  	"render": function(data, type, opt) {
			  		var result = "";
			  		if(opt.expenseAutoId != null && opt.expenseAutoId != '' && opt.expenseAutoId != 'undefined'){
	        			  expenseId = opt.expenseAutoId;
	        		  }else{
	        			  expenseId = "--";
	        		  }
			  		 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_MY_EXPENSES\')">'
					 result = result +"<td><a href='"+sourceleadUrl+"/addExpense?expenseId="+expenseId+"&expenseLocationCode="+opt.expenseLocationCode+" &type=view'><div class='proj_show'><span>"+expenseId+"</span><i class='fa fa-binoculars'></i></div></a></td>";
					 '</sec:authorize>';
					 '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_EDIT_MY_EXPENSES\')">'
					 result = result +"<td><div class='proj_show'><span>"+expenseId+"</span></div></td>";
					 '</sec:authorize>';
		             return  result;
			  	}
		    },{
	    	  	"targets": colIndex++,
	    	  	"data": "Purpose",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt) {
	    	  		 var result = "";
	    	  		  if(opt.expensePurpose != null && opt.expensePurpose != '' && opt.expensePurpose != 'undefined'){
	        			  purpose = opt.expensePurpose;
	        		  }else{
	        			  purpose = "--";
	        		  }
	    	  		  
	    	  		  result = result +"<td><div title="+purpose+">"+purpose+"</div></td>";
		             return  result;
	    	  	}
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "Project",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt) {
	    	  		var result = "";
	    	  		  if(opt.projectName != null && opt.projectName != '' && opt.projectName != 'undefined'){
	        			  projectName = opt.projectName;
	        		  }else{
	        			  projectName = "--";
	        		  }
	    	  		 result = result + '<td><div  class="tbldata-dotdot-sm"><a href="'+sourceleadUrl+'/projectSummary?projectUUID='+opt.projectUUID+'&projectClientUUID='+opt.projectClientUUID+'" title="'+projectName+'">'+projectName+'</a></div></td>';
		            return  result;
	    	  	}
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "client",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt) {
	    	  		var result = "";
	    	  		if(opt.clientName != null && opt.clientName != '' && opt.clientName != 'undefined'){
	        			  clientName = opt.clientName;
	        		  }else{
	        			  clientName = "--";
	        		  }
	    	  		'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
	    	  		  result = result +'<td><div title="+clientName+"><a href="'+sourceleadUrl+'/clientDashboard?orgRelationUUID='+opt.orgRelationUUID+'" title="'+clientName+'"> '+clientName+' </a></div></td>';
	    	  		 '</sec:authorize>';
	    	  		 
	    	  		'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
	    	  		  result = result +'<td><div title="+clientName+">'+clientName+'</div></td>';
	    	  		 '</sec:authorize>';
	    	  		 
	    	  		return  result;
	    	  	}
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "Start Date",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt) {
	    	  		var result = "";
	    	  		if(opt.startDate != null && opt.startDate != '' && opt.startDate != 'undefined'){
	        			  startDateStr = opt.startDate;
	        		  }else{
	        			  startDateStr = "--";
	        		  }
	    	  		 result = result +"<td><div title="+startDateStr+">"+startDateStr+"</div></td>";
		            return  result;
	    	  	}
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "End Date",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt) {
	    	  		var result = "";
	    	  		if(opt.endDate != null && opt.endDate != '' && opt.endDate != 'undefined'){
	        			  endDateStr = opt.endDate;
	        		  }else{
	        			  endDateStr = "--";
	        		  }
	    	  		  result = result +"<td><div title="+endDateStr+">"+endDateStr+"</div></td>";
		            return  result;
	    	  	}
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "No of Items",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt) {
	    	  		var result = "";
	    	  		if(opt.noOfExpenses != null && opt.noOfExpenses != '' && opt.noOfExpenses != 'undefined'){
	        			  noOfExpenses = opt.noOfExpenses;
	        		  }else{
	        			  noOfExpenses = "--";
	        		  }
	    	  		result = result +"<td><div title="+noOfExpenses+">"+noOfExpenses+"</div></td>";
		            return  result;
	    	  	}
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "Amount",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt) {
	    	  		var result = "";
	    	  		if(opt.totalAmount != null && opt.totalAmount != '' && opt.totalAmount != 'undefined'){
	        			  var  numb = opt.totalAmount.toFixed(2);
	        			  totalAmount = numb;
	        		  }else{
	        			  totalAmount = "--";
	        		  }
	    	  		  result = result +"<td><div title="+totalAmount+">"+totalAmount+"</div></td>";
		            return  result;
	    	  	}
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "Status",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt) {
	    	  		var result = "";
	    	  		result = result +"<td> <div>"+opt.expenseStatus+"</div></td>";
		            return  result;
	    	  	}
	      }
		 ],
		 	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[8,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"initComplete": function( settings, json ) {
	    		 $('#dataTableSearchExpensesEmp').removeAttr('disabled');
	    	  },
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
	 } );
		 $("#example").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
		   $("input[type=search]").hide();
		  
		   $("#dataTableSearchExpensesEmp").keyup(function(e) {
		   	   if(e.which === 13) {
		   		getExpensesList(sourceleadUrl);
		   	   }
		   	});	
		   $("#temp").val("temp");
	
}
</script>
 