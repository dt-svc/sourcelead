<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">
		window.onload = function () {
			getExpensesApprovalDataList();
		 };
</script>


<section class="content-header clearfix">
	<section class="content-wraper">
		<section class="content-header clearfix">
			<div class="pull-left">
				<h2>Expenses Approval</h2>
			</div>
			<div class="pull-right brud-crum">Home &raquo; ExpensesApproval</div>
		</section>
		<!-- Project -->
		<div class="timesheet_out">
			<div class="timesheet_appr_wrap">
				<div class="timesheetNewHead">
					<div class="pull-right expBtn">
						<a href="<c:url value='/addApprovalExpense'/>"><i
							class="glyphicon glyphicon-plus"></i>&nbsp;Add Expense</a>
					</div>
					<!-- <div class="remove_search">
						<input placeholder="search" type="text">
					</div> -->
					<div class="clearfix"></div>
				</div>
				<div class="timesheet_appr_table">
					<div id="approverDataExpensesDiv"></div>

				</div>
			</div>
		</div>
		<!-- Project -->
	</section>
</section>



<script type="text/javascript">
function getExpensesApprovalDataList(){
var sourceleadUrl = '${sourceleadUrl}';
$.ajax({
    type : "POST",
     url: sourceleadUrl+"/getExpensesApprovalList?${_csrf.parameterName}=${_csrf.token}",       
     success :function(data) {
    var result = "";
	result = result+' <table id="expensesApprovalPagination">';
	result = result+' <thead>';
	result = result+' <tr>';
	result = result+' <th>Employee</th>';
	result = result+' <th>EmploymentType</th>';
	result = result+' <th>Project</th>';
	result = result+' <th>Start Date</th>';
	result = result+' <th>End Date</th>';
	result = result+' <th>No of Exp</th>';
	result = result+' <th>Amount</th>';
	result = result+' <th>Status</th>';
	result = result+' <th>Action</th>';
	result = result+' </tr>';
	result = result+' </thead>';
	result = result+' <tbody>';
	var json = JSON.parse(data);
	 for (var i = 0; i < json.length; i++) { 
	 var data = json[i];
	result = result+' <tr>';
	result = result+' <td><div class="time_appr_user"><img  id="userImage" onerror="predefineProfileImage(this);" src="'+sourceleadUrl+'/displayUserImage/'+data.expensesUserid+'" alt="Profile Image" class="img-circle img-responsive">';
	result = result+' <span>';
	result = result+' <b>'+data.expenseEmpUserFirstNameLastName+'</b>';
	result = result+' <b>'+data.expenseEmpUserEmail+'</b>';
	result = result+' <b>'+data.expensesEmployeeUserId+'</b>';
	result = result+' </span></div>';
	result = result+' </td>';
	result = result+' <td><div><span>'+data.expenseEmpUserType+'</span><br><span>'+data.userTypeVendorName+'</span></div></td>';
	result = result+' <td><div><span>'+data.expensesProjectName+'</span><br><span>'+data.expenseId.clientName+'</span></div></td>';
	result = result+' <td><div class="sm">'+data.expenseStartDateString+'</div></td>';
	result = result+' <td><div class="sm">'+data.expenseEndDateString+'</div></td>';
	result = result+' <td><div class="sm">'+data.expenseId.totalAmount+'</div></td>';
	result = result+' <td><div class="sm"><a href="javascript:void(0)" class="ExpAmount">'+data.expenseId.totalAmount+'</a></div></td>';
	result = result+' <td><div>'+data.expenseApprovalStatus+'</div></td>';
	result = result+' <td><div class="sm"><i class="fa fa-check" alt="Approve"></i>|<i class="fa fa-times" alt="Rejected"></i></div></td>';              
	result = result+' </tr>';
	 }
	result = result+' </tbody>';
	result = result+' </table>';
	$("#approverDataExpensesDiv").append(result);
	pagination();
  }
});
}
</script>
   
   
    
 <script>
function pagination(){ 
    $('#expensesApprovalPagination').DataTable({ 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [2, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"aaSorting": [[7,'desc']],
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
}
</script>

<jsp:include page="../masterFooter.jsp" />