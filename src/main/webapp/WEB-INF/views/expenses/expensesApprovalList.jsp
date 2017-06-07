<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

  <section class="content-header clearfix">
    <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Expenses Approval</h2>
          </div>
          <div class="pull-right brud-crum">Home &raquo; Expenses Approval</div>
        </section>
        
		<!-- Project -->
<div class="timesheet_out">
	
	<div class="timesheet_appr_wrap">
			
<div class="timesheetNewHead">
<!--			<div class="timeNewSe">
                <select class="form-control chosen-select">
                  <option>Select Project</option>
                  <option>Project 1</option>
                  <option>Project 2</option>
                  <option>Project 3</option>
                  <option>Project 4</option>
                </select>
            </div>           
			<div class="timeNewSe timeNewSe_a addicon">
                <select class="form-control chosen-select">
                  <option>Select Frequency</option>
                  <option>28/11/2016 to 04/12/2016</option>
                  <option>05/12/2016 to 11/12/2016</option>
                  <option>12/12/2016 to 18/12/2016</option>
                  <option>19/12/2016 to 25/12/2016</option>
                  <option>26/12/2016 to 01/01/2017</option>
                  <option>02/01/2017 to 08/01/2017</option>
                  <option>09/01/2017 to 15/01/2017</option>
                  <option>16/12/2017 to 22/12/2017</option>
                  <option>23/12/2016 to 29/12/2017</option>
                 
                  </select>
            </div>
			<div class="timeNewSe">
                <select class="form-control chosen-select">
                  <option>Select Client</option>
                  <option>Client 1</option>
                  <option>Client 2</option>
                  <option>Client 3</option>
                  <option>Client 4</option>
                </select>
            </div>             
			<div class="timeNewSe">
                <select class="form-control chosen-select">
                  <option>Stages</option>
                  <option>New</option>
                  <option>Submit for approval</option>
                  <option>Rejected</option>
                </select>
            </div>   -->     
	<div class="pull-right expBtn">
		<a href="<c:url value='/addApprovalExpense'/>"><i class="glyphicon glyphicon-plus"></i>&nbsp;Add Expense</a>
	</div>   
	<div class="remove_search">
		<input placeholder="search" type="text">
	</div>    
	<div class="clearfix"></div>          
</div>	
		
<div class="timesheet_appr_table">
    	<table id='expensesApprovalPagination'>
        	<tbody>
			<thead>
            	<tr>
                	<th>Employee</th>
                    <th>EmploymentType</th>
                    <th>Project</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>No of Exp</th>
					<th>Amount</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
			</thead>
				<c:forEach var="expApperoverHierarchy" items="${ExpApperoverHierarchy}">	
                <tr>
		            <td><div class="time_appr_user"><img  id='userImage' onerror='predefineProfileImage(this);' src="${sourceleadUrl}/displayUserImage/${expApperoverHierarchy.expenseId.projectMembers.employee.user.id}" alt='Profile Image' class='img-circle img-responsive'>
					<span>
					<b>${expApperoverHierarchy.expenseEmpUserFirstNameLastName}</b>
					<b>${expApperoverHierarchy.expenseEmpUserEmail}</b>
					<b>${expApperoverHierarchy.expensesEmployeeUserId}</b>
					</span></div>
					</td>
                   	<td><div><span>${expApperoverHierarchy.expenseEmpUserType}</span><br><span>${expApperoverHierarchy.userTypeVendorName}</span></div></td>
                   	<td><div><span>${expApperoverHierarchy.expensesProjectName}</span><br><span>${expApperoverHierarchy.expenseId.clientName}</span></div></td>
                    <td><div class="sm">${expApperoverHierarchy.expenseStartDateString}</div></td>
                    <td><div class="sm">${expApperoverHierarchy.expenseEndDateString}</div></td>
                    <td><div class="sm">${expApperoverHierarchy.expenseId.totalAmount}</div></td>
                    <td><div class="sm"><a href="javascript:void(0)" class="ExpAmount">${expApperoverHierarchy.expenseId.totalAmount}</a></div></td>
               		<td><div>Submit for Approval</div></td>
                    <td><div class="sm"><i class="fa fa-check" alt="Approve"></i>|<i class="fa fa-times" alt="Rejected"></i></div></td>               
                </tr>
                </c:forEach>
            </tbody>
        </table> 	
</div>
	</div>
	
</div>
			
		<!-- Project -->
		
      </section>
    </section>
    
 <!-- <script>
 $('#expensesApprovalPagination').DataTable( { 
}); 
function pagination(){ 
	$.fn.dataTable.moment( 'DD-MM-YYYY' );
    $('#expensesApprovalPagination').DataTable( { 
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
    } );
}
</script> -->

<jsp:include page="../masterFooter.jsp" />