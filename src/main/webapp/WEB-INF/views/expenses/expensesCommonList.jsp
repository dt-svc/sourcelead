<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
function myExpensesListByStage(expenseStatus){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
	    type : "GET",
	    url: sourceleadUrl+"/myExpensesListByStage?expenseStatus="+expenseStatus,      
	    success :function(data) {
	    	 var result = "";
				result = result +" <table id='expensesPagination'>";
				result = result +"<thead>";
				result = result +"<tr>";
				result = result +"<th>Exp Id</th>";
				result = result +"<th>Purpose</th>";
				result = result +"<th>Project</th>";
				result = result +"<th>Client</th>";
				result = result +"<th>Start Date</th>";
				result = result +"<th>End Date</th>";
				result = result +"<th>No of Items</th>";
				result = result +"<th>Amount</th>";
				result = result +"<th>Status</th>";
				result = result +"</tr>";
				result = result +"</thead>";
				result = result +"<tbody>";
				result = result+' <input type="text" hidden value="'+expenseStatus+'" id="countType" readonly="readonly">';
				 if(data !=''){
					 var json = JSON.parse(data);
		        	  for (var i = 0; i < json.length; i++) { 
		        		  var report = json[i]; 
		        		  if(report.expenseId != null && report.expenseId != '' && report.expenseId != 'undefined'){
		        			  expenseId = report.expenseId;
		        		  }else{
		        			  expenseId = "--";
		        		  }
		        		  if(report.purpose != null && report.purpose != '' && report.purpose != 'undefined'){
		        			  purpose = report.purpose;
		        		  }else{
		        			  purpose = "--";
		        		  }
		        		  if(report.projectMemberId != null && report.projectMemberId != '' && report.projectMemberId != 'undefined'){
		        			  projectMemberId = report.projectMemberId;
		        		  }else{
		        			  projectMemberId = "--";
		        		  }
		        		  if(report.clientName != null && report.clientName != '' && report.clientName != 'undefined'){
		        			  clientName = report.clientName;
		        		  }else{
		        			  clientName = "--";
		        		  }
		        		  if(report.startDateStr != null && report.startDateStr != '' && report.startDateStr != 'undefined'){
		        			  startDateStr = report.startDateStr;
		        		  }else{
		        			  startDateStr = "--";
		        		  }
		        		  if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
		        			  endDateStr = report.endDateStr;
		        		  }else{
		        			  endDateStr = "--";
		        		  }
		        		  if(report.noOfExpenses != null && report.noOfExpenses != '' && report.noOfExpenses != 'undefined'){
		        			  noOfExpenses = report.noOfExpenses;
		        		  }else{
		        			  noOfExpenses = "--";
		        		  }
		        		  if(report.totalAmount != null && report.totalAmount != '' && report.totalAmount != 'undefined'){
		        			  totalAmount = report.totalAmount;
		        		  }else{
		        			  totalAmount = "--";
		        		  }
							 result = result +"<tr>";
							 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_MY_EXPENSES\')">'
							 result = result +"<td><a href='"+sourceleadUrl+"/addExpense?expenseId="+expenseId+"&type=view'><div class='proj_show'><span>"+expenseId+"</span><i class='fa fa-binoculars'></i></div></a></td>";
							 '</sec:authorize>';
							 '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_EDIT_MY_EXPENSES\')">'
							 result = result +"<td><div class='proj_show'><span>"+expenseId+"</span></div></td>";
							 '</sec:authorize>';
							 result = result +"<td><div title="+purpose+">"+purpose+"</div></td>";
							 result = result +"<td><div title="+projectMemberId+">"+projectMemberId+"</div></td>";
							 result = result +"<td><div>"+clientName+"</div></td>";
							 result = result +"<td><div>"+startDateStr+"</div></td>";
							 result = result +"<td><div>"+endDateStr+"</div></td>";
							 result = result +"<td><div>"+noOfExpenses+"</div></td>";
							 result = result +"<td><div>"+totalAmount+"</div></td>";
							 result = result +"<td> <div>"+report.expenseStatus+"</div></td>";
							 result = result +"</tr>";
		        	  }
	        		  $("#temp").val("temp");
	        	  }
 	  		result = result +" </tbody>";
 	  		result = result +" </table>";
 	  		$("#expensesListDiv").empty();
       		expensesPagination();
       		localStorage.setItem("myExpensesListGrid",result);
       		window.location.replace(sourceleadUrl+'/expenses');
	     }
	});	
}

function employeeExpensesListByStage(expenseStatus){
	
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
	    type : "GET",
	    url: sourceleadUrl+"/employeeExpensesListByStage?expenseStatus="+expenseStatus,
	    success :function(data) {
	    	 var result = "";
	    		result = result+' <table id="example">';
	    		result = result+' <thead>';
	    		result = result+' <tr>';
	    		result = result+' <th>Employee</th>';
	    		result = result+' <th>EmploymentType</th>';
	    		result = result+' <th>Project</th>';
	    		result = result+' <th>Start Date</th>';
	    		result = result+' <th>End Date</th>';
	    		result = result+' <th>No of Items</th>';
	    		result = result+' <th>Amount</th>';
	    		result = result+' <th>Status</th>';
	    		result = result+' <th>Action</th>';
	    		result = result+' </tr>';
	    		result = result+' </thead>';
	    		result = result+' <tbody>';
	    		result = result+' <input type="text" hidden value="'+expenseStatus+'" id="countType" readonly="readonly">';
	    		if(data !=''){
	    		var json = JSON.parse(data);
	    		 for (var i = 0; i < json.length; i++) { 
	    		 var data = json[i];
	    		result = result+' <tr id = "row'+data.expenseId.id+'">';
	    		result = result+' <td><div class="time_appr_user"><img  id="userImage" onerror="predefineProfileImage(this);" src="'+sourceleadUrl+'/displayUserImage/'+data.expensesUserid+'" alt="Profile Image" class="img-circle img-responsive">';
	    		result = result+' <span>';
	    		result = result+' <b title='+data.expenseEmpUserFirstNameLastName+'>'+data.expenseEmpUserFirstNameLastName+'</b>';
	    		result = result+' <b title='+data.expenseEmpUserEmail+'>'+data.expenseEmpUserEmail+'</b>';
	    		result = result+' <b title='+data.expensesEmployeeUserId+'>'+data.expensesEmployeeUserId+'</b>';
	    		result = result+' </span></div>';
	    		result = result+' </td>';
	    		if(data.userTypeVendorName != null && data.userTypeVendorName != '' && data.userTypeVendorName !='undefined'){
	    			result = result+' <td><div><span title='+data.expenseEmpUserType+'>'+data.expenseEmpUserType+'</span><br><span title='+data.userTypeVendorName+'>'+data.userTypeVendorName+'</span></div></td>';	
	    		}else{
	    			result = result+' <td><div><span title='+data.expenseEmpUserType+'>'+data.expenseEmpUserType+'</span></div></td>';	
	    		}
	    		
	    		if(data.expenseId.clientName != null && data.expenseId.clientName != '' && data.expenseId.clientName !='undefined'){
	    			result = result+' <td><div><span title="'+data.expensesProjectName+'">'+data.expensesProjectName+'</span><br><span title='+data.expenseId.clientName+'>'+data.expenseId.clientName+'</span></div></td>';	
	    		}else{
	    			result = result+' <td><div><span title='+data.expensesProjectName+'>'+data.expensesProjectName+'</span></div></td>';	
	    		}
	    		result = result+' <td><div class="sm" title='+data.expenseStartDateString+'>'+data.expenseStartDateString+'</div></td>';
	    		
	    		if(data.expenseEndDateString != null && data.expenseEndDateString != '' && data.expenseEndDateString !='undefined'){
	    			result = result+' <td><div class="sm" title='+data.expenseEndDateString+'>'+data.expenseEndDateString+'</div></td>';	
	    		}else{
	    			result = result+' <td><div class="sm">--</div></td>';
	    		}
	    		result = result+' <td><div class="sm" title='+data.expenseId.noOfExpenses+'>'+data.expenseId.noOfExpenses+'</div></td>';
	    		result = result+' <td><div class="sm">';
	    		'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES\')">'
	    		result = result+'<a  href="'+sourceleadUrl+'/addApprovalExpense?expenseId='+data.expenseId.expenseId+'&type=view" class="ExpAmount" title='+data.expenseId.totalAmount+'>';
	    		'</sec:authorize>';
	    		
	    		result = result+ data.expenseId.totalAmount;
	    		
	    		'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES\')">'
	    		result = result+'</a>';
	    		'</sec:authorize>';
	    		result = result+'</div></td>';
	    		result = result+' <td><div id="status'+data.expenseId.id+'" title='+data.expenseApprovalStatus+'>'+data.expenseApprovalStatus+'</div></td>';
	    		
	    		result = result+' <td>';
	    		var tdSpanLength = 9;
	    		 if(data.expenseApprovalStatus !=null && data.expenseApprovalStatus == 'NEW'){
	    			 result = result+'<div class="sm text-center">--</div>';
	    	     }
	    	     if(data.expenseApprovalStatus !=null && data.expenseApprovalStatus == 'SUBMITTEDFORAPPROVAL'){
	    	        result = result+"<div class='sm text-center'id='action"+data.expenseId.id+"'>"
	    	        '<sec:authorize access="hasAnyRole(\'ROLE_APPROVE_EMPLOYEE_EXPENSES\')">' 
	    	        result = result+ " <i class='fa fa-check' title='Approve' alt='Approve'  onclick='approveExpenseByExpenseId(\""+data.expenseId.id+"\","+tdSpanLength+ ",\""+"approval"+"\");'></i>"
	    	        result = result+ "|<i class='fa fa-times' title='Reject'  alt='Rejected' onclick='rejectExpenseByExpenseId(\""+data.expenseId.id+"\","+tdSpanLength+ ",\""+"approval"+"\");'></i>"
	    	        '</sec:authorize>';
	    	        result = result+"</div>"; 
	    	 	                    
	    	    }
	    	    if(data.expenseApprovalStatus !=null && data.expenseApprovalStatus  == 'REJECTED'){
	    	    	  result = result+" <div class='sm text-center'><h3 class='reje_color'>Rejected</h3>";
	    	    	 result = result+"  <i class='glyphicon glyphicon-comment' id='"+data.expenseId.id+"' onclick='rejectedMessage(\"rejectedMessage"+data.expenseId.id+"\");'>";
	    		 	 result = result+" <div class='app_error_mes' id='rejectedMessage"+data.expenseId.id+"'>"+data.rejectedReason+"</div>";
	    		 	 result = result+" </i></br><b title='"+data.actionPerformedBy+"'>By : "+data.actionPerformedBy+"</b>";
	    		 	 result = result+" </div>";
	    	    }
	    	    if(data.expenseApprovalStatus !=null && data.expenseApprovalStatus  == 'APPROVED'){
	    	         result = result+" <div class='sm text-center' title='"+data.actionPerformedBy+"'> <h3>Approved</h3><br>By : "+data.actionPerformedBy+"</div>"; 
	    	    }
	    	    if(data.expenseApprovalStatus !=null && data.expenseApprovalStatus  == 'REOPEN'){
	    	    	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES\')">'
	    	    	result = result+'<a  href="'+sourceleadUrl+'/addApprovalExpense?expenseId='+data.expenseId.expenseId+'&type=view" class="ExpAmount">';
	    	    	'</sec:authorize>'; 
	    	   }
	    	     result = result+' </td>'; 
	    		 result = result+' </tr>';
	    		 }
	    		 $("#temp").val("temp");
	    		  }
	    		result = result+' </tbody>';
	    		result = result+' </table>';
	    		$("#approverDataExpensesDiv").empty();
	    		pagination();
	    		localStorage.setItem("empExpensesListGrid",result);
	    		window.location.replace(sourceleadUrl+'/expensesApprovalList');
	     }
	});
	
	
	
}

</script>

<script>
function expensesPagination(){ 
    $('#expensesPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 0
			}],
		"aaSorting": [[0,'desc']],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
    $("#expensesPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
}
</script>

<script>
 function pagination(){ 
    $('#example').DataTable({ 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"aaSorting": [[7,'desc']],
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $("#example").wrap("<div class='overflow_Auto'></div>");
}
</script>
