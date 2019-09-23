<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 <script>
function expensesApprovalList(sourceleadUrl){
    var url = '';
    var searchValue = $("#dataTableSearchExpenses").val();
	searchValue = searchValue ? searchValue : "";
	var startDate = $("#filterStartDate").val();
	var endDate = $("#filterEndDate").val();
	var expenseStatus = [];
	$('input:checkbox[name=expenseStatus]:checked').each(function() 
	{
		expenseStatus.push($(this).val());
	});

	$("#approverDataExpensesDiv").empty();
	
	/* $("#approverDataExpensesDiv").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;'); */
	url = sourceleadUrl+"/getExpensesApprovalList?expenseStatus="+expenseStatus+"&startDate="+startDate+"&endDate="+endDate+"&searchValue="+searchValue;

	$('#dataTableSearchExpenses').removeAttr('disabled');
    $('#dataTableSearchExpenses').attr('disabled','disabled');
     prepareExpensesApprovalData(url,sourceleadUrl); 
} 



 function prepareExpensesApprovalData(url,sourceleadUrl){
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  var expensesApprvListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  var expensesApprvListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  var expensesApprvListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  var expensesApprvListDate = 'YYYY-DD-MM'; 
	  }
	  
	    var result = "";
	    result = result +"<table id='example' class='display' cellspacing='0' width='100%' >"; 
	    result = result +"<thead>"; 
	    result = result+" <tr>"; 
	    result = result+' <th>Employee</th>';
		result = result+' <th>EmploymentType</th>';
		result = result+' <th>Project</th>';
		result = result+' <th>Purpose</th>';
		result = result+' <th>Start Date<br>(<small class="custom-small">'+expensesApprvListDate+'</small>)</th>';
		result = result+' <th>End Date<br>(<small class="custom-small">'+expensesApprvListDate+'</small>)</th>';
		result = result+' <th>No of Items</th>';
		result = result+' <th>Amount</th>';
		result = result+' <th>Status</th>';
		result = result+' <th>Action</th>';
	    result = result+" </tr>"; 
	    result = result+" </thead>";
	    result = result+" <tbody>"; 
	    
	    result = result+" </tbody>";
	    result = result+" </table>"; 
	    
   $("#approverDataExpensesDiv").append(result); 
   Approvalpagination(url,sourceleadUrl); 
   
   /* to call requirement conformation saving message */
}

function Approvalpagination(url,sourceleadUrl){
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
	    	   { "data": "Employee"},
	           { "data": "EmploymentType" },
	           { "data": "Project"},
	           { "data": "Purpose"},
	           { "data": "Start Date" },
	           { "data": "End Date"},
	           { "data": "No of Items" },
	           { "data": "Amount" },
	           { "data": "Status"},
	           { "data": "actions"}
	       ],
	       "columnDefs": [ {
		   	  	"targets": colIndex++,
			  	"data": "Employee",
			  	"sortable": false,
			  	"render": function(data, type, opt) {
			  		var result = "";
			  		result = result+' <td><div class="time_appr_user"><img  id="userImage" onerror="predefineProfileImage(this);" src="'+opt.userFileUrl+'" alt="Profile Image" class="img-circle img-responsive">';
			  		result = result+' <span>';
			  		result = result+' <b title="'+opt.expenseEmpUserFirstNameLastName+'">'+opt.expenseEmpUserFirstNameLastName+'</b>';
			  		result = result+' <b title="'+opt.expenseEmpUserEmail+'">'+opt.expenseEmpUserEmail+'</b>';
			  		result = result+' </span></div>';
			  		result = result+' </td>';
		            return  result;
			  	}
		    },{
	    	  	"targets": colIndex++,
	    	  	"data": "EmploymentType",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt) {
	    	  		 var result = "";
	    	  		 if(opt.userTypeVendorName != null && opt.userTypeVendorName != '' && opt.userTypeVendorName !='undefined'){
	    	  			result = result+' <td><div><span title="'+opt.expenseEmpUserType+'">'+opt.expenseEmpUserType+'</span><br><span title="'+opt.userTypeVendorName+'">'+opt.userTypeVendorName+'</span></div></td>';	
	    	  		 }else{
	    	  			result = result+' <td><div><span title="'+opt.expenseEmpUserType+'">'+opt.expenseEmpUserType+'</span></div></td>';	
	    	  		 }
		             return  result;
	    	  	}
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "Project",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt) {
	    	  		var result = "";
	    	  		if(opt.expenseId.clientName != null && opt.expenseId.clientName != '' && opt.expenseId.clientName !='undefined'){
	    	  			result = result+' <td><div><span title="'+opt.expensesProjectName+'">'+opt.expensesProjectName+'</span><br><span title="'+opt.expenseId.clientName+'">'+opt.expenseId.clientName+'</span></div></td>';	
	    	  		}else{
	    	  			result = result+' <td><div><span title="'+opt.expensesProjectName+'">'+opt.expensesProjectName+'</span></div></td>';	
	    	  		}
		            return  result;
	    	  	}
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "Purpose",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt) {
	    	  		var result = "";
	    	  		if(opt.expensesApproverHierarchyPurpose != null && opt.expensesApproverHierarchyPurpose != '' && opt.expensesApproverHierarchyPurpose !='undefined'){
	    	  			result = result+' <td><div><span title="'+opt.expensesApproverHierarchyPurpose+'">'+opt.expensesApproverHierarchyPurpose+'</span></div></td>';	
	    	  		}else{
	    	  			result = result+' <td><div class="sm" title="--">--</div></td>';	
	    	  		}
		            return  result;
	    	  	}
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "Start Date",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt) {
	    	  		var result = "";
	    	  		result = result+' <td><div class="sm" title="'+opt.expenseStartDateString+'">'+opt.expenseStartDateString+'</div></td>';
		            return  result;
	    	  	}
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "End Date",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt) {
	    	  		var result = "";
	    	  		if(opt.expenseEndDateString != null && opt.expenseEndDateString != '' && opt.expenseEndDateString !='undefined'){
	    	  			result = result+' <td><div class="sm" title="'+opt.expenseEndDateString+'">'+opt.expenseEndDateString+'</div></td>';	
	    	  		}else{
	    	  			result = result+' <td><div class="sm" title="--">--</div></td>';
	    	  		}
		            return  result;
	    	  	}
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "No of Items",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt) {
	    	  		var result = "";
	    	  		    result = result+' <td><div class="sm">'+opt.expenseId.noOfExpenses+'</div></td>';
		            return  result;
	    	  	}
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "Amount",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt) {
	    	  		var result = "";
	    	  		    result = result+' <td><div class="sm" title="'+opt.expenseId.totalAmount.toFixed(2)+'">';
	    	  		  '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES\')">'
	    	  			result = result+'<a  href="'+sourceleadUrl+'/addApprovalExpense?expenseId='+opt.expenseId.expenseId+'&expenseLocationCode='+opt.expenseId.locationsCode+'&type=view" class="ExpAmount">';
	    	  			'</sec:authorize>';
	    	  			 var  numb = opt.expenseId.totalAmount.toFixed(2);
	    	  			result = result+numb;
	    	  			'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES\')">'
	    	  			result = result+'</a>';
	    	  			'</sec:authorize>';
	    	  			result = result+'</div></td>';
		            return  result;
	    	  	}
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "Status",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt) {
	    	  		var result = "";
	    	  			result = result+' <td><div id="status'+opt.expenseId.id+'" title="'+opt.expenseStatus+'">'+opt.expenseStatus+'</div></td>';
		            return  result;
	    	  	}
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "actions",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt) {
	    	  		var result = "";
	    	  		result = result+' <td>';
		        	var tdSpanLength = 9;
		        	 if(opt.expenseApprovalStatus !=null && opt.expenseApprovalStatus == 'NEW'){
		        		 result = result+'<div class="sm text-center">--</div>';
		             }
		             if(opt.expenseApprovalStatus !=null && opt.expenseApprovalStatus == 'SUBMITTEDFORAPPROVAL'){
		                result = result+"<div class='sm text-center'id='action"+opt.expenseId.id+"'>"
		                '<sec:authorize access="hasAnyRole(\'ROLE_APPROVE_EMPLOYEE_EXPENSES\')">' 
		                result = result+ " <i class='fa fa-check' title='Approve' alt='Approve'  onclick='approveExpenseByExpenseId(\""+opt.expenseId.id+"\","+tdSpanLength+ ",\""+"approval"+"\");'></i>"
		                result = result+ "|<i class='fa fa-times' title='Reject'  alt='Rejected' onclick='rejectExpenseByExpenseId(\""+opt.expenseId.id+"\","+tdSpanLength+ ",\""+"approval"+"\");'></i>"
		                '</sec:authorize>';
		                result = result+"</div>"; 
		         	                    
		            }
		            if(opt.expenseApprovalStatus !=null && opt.expenseApprovalStatus  == 'REJECTED'){
		            	  result = result+" <div class='sm text-center'><h3 class='reje_color'>Rejected</h3>";
		            	 result = result+"  <i class='glyphicon glyphicon-comment' id='"+opt.expenseId.id+"' onclick='rejectedMessage(\"rejectedMessage"+opt.expenseId.id+"\");'>";
		            	 result = result+" <div class='app_error_mes' id='rejectedMessage"+opt.expenseId.id+"'>"+opt.rejectedReason+"</div>";
		        	 	 result = result+" </i><br><p title='"+opt.actionPerformedBy+"'>By : "+opt.actionPerformedBy+"</p>";
		        	 	 result = result+" </div>";
		            }
		            if(opt.expenseApprovalStatus !=null && opt.expenseApprovalStatus  == 'APPROVED'){
		            	
		            	if(opt.reopenRejectedReason != null && opt.reopenRejectedReason != '' && opt.reopenRejectedReason !='undefined'){
		            		 result = result+" <div class='sm text-center'><h3 class='reje_color'>Rejected</h3>";
		            		 result = result+"  <i class='glyphicon glyphicon-comment' id='"+opt.expenseId.id+"' onclick='rejectedMessage(\"rejectedMessage"+opt.expenseId.id+"\");'>";
		            		 result = result+" <div class='app_error_mes' id='rejectedMessage"+opt.expenseId.id+"'>"+opt.reopenRejectedReason+"</div>";
		            	}
		            	else{
		            		result = result+" <div class='sm text-center' title='"+opt.actionPerformedBy+"'> <h3>Approved</h3><br>By : "+opt.actionPerformedBy+"</div>"; 
		            	}
		                 
		                
		                 
		            }
		            if(opt.expenseApprovalStatus !=null && opt.expenseApprovalStatus  == 'REOPEN'){
		            	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES\')">'
		            	result = result+'<a  href="'+sourceleadUrl+'/addApprovalExpense?expenseId='+opt.expenseId.expenseId+'&expenseLocationCode='+opt.expenseId.locationsCode+'&type=view" class="ExpAmount">';
		            	'</sec:authorize>'; 
		           }
		             result = result+' </td>'; 
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
	    		 $('#dataTableSearchExpenses').removeAttr('disabled');
	    	  },
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
	 } );
		 $("#example").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
		   $("input[type=search]").hide();
		  
		   $("#dataTableSearchExpenses").keyup(function(e) {
		   	   if(e.which === 13) {
		   		expensesApprovalList(sourceleadUrl);
		   	   }
		   	});	 
		   $("#temp").val("temp");
}
</script>
 