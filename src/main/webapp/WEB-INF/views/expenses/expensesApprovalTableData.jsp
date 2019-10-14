<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
 
 <script type="text/javascript" src='/pms/resources/js/expenseApproveOrReject.js'></script>
 
 
<tr style="display: none;">
		   <input type="hidden" id="totalRows" name="totalRows" value="${totalRows}">
	       <input type="hidden" id="pageCount" name="totalRowsPage" value="${pageCount}">
</tr>

<c:forEach var="expApproverHierarchy" items="${expApperoverHierarchydto}">
 <tr id="row${expApproverHierarchy.expId}">
 
 <td>
	 <div class="time_appr_user"><img  id="userImage" onerror="predefineProfileImage(this);" src="${expApproverHierarchy.userFileUrl}" alt="Profile Image" class="img-circle img-responsive">
		 <span>
			 <b title="${expApproverHierarchy.expenseEmpUserFirstNameLastName}">${expApproverHierarchy.expenseEmpUserFirstNameLastName}</b>
			 <b title="${expApproverHierarchy.expenseEmpUserEmail}">${expApproverHierarchy.expenseEmpUserEmail}</b>
		 </span>
	 </div>
 </td>
 
 <td>
		 <span>
			 <b title="${expApproverHierarchy.expenseId}">${expApproverHierarchy.expenseId}</b>
		 </span>
 </td>
 
 <td>
 	<div class="tblminwidth-100">
 		<span title="${expApproverHierarchy.expenseEmpUserType}">${expApproverHierarchy.expenseEmpUserType}</span><br>
 		<span title="${expApproverHierarchy.userTypeVendorName}">${expApproverHierarchy.userTypeVendorName}</span>
 	</div>
 </td>
 	
<c:if test="${not empty expApproverHierarchy.clientName}">
	 <td>
		 <div class="tbldata-dotdot-100"><span title="${expApproverHierarchy.expensesProjectName}">${expApproverHierarchy.expensesProjectName}</span><br>
			 <sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">
				 <c:if test="${not empty expApproverHierarchy.orgRelationUUID}">
				 	<span title="${expApproverHierarchy.clientName}"> <a href="${SourceLeadWeb_vmsUrl}/clientDashboard?orgRelationUUID=${expApproverHierarchy.orgRelationUUID}"  class="aLink"> ${expApproverHierarchy.clientName}</a></span>
				 </c:if>
			 </sec:authorize>
				
		 </div>
	 </td>
</c:if>

<c:if test="${empty expApproverHierarchy.clientName}">
 	<td>
 		<div class="tbldata-dotdot-100">
 			<span title="${expApproverHierarchy.expensesProjectName}">${expApproverHierarchy.expensesProjectName}</span>
 		</div>
 	</td>	
</c:if>
	  		
<c:if test="${not empty expApproverHierarchy.expensesApproverHierarchyPurpose}">
	 <td>
	 	<div class="tbldata-dotdot-200">
	 		<span title="${expApproverHierarchy.expensesApproverHierarchyPurpose}">${expApproverHierarchy.expensesApproverHierarchyPurpose}</span>
	 	</div>
	 </td>	
</c:if>

<c:if test="${empty expApproverHierarchy.expensesApproverHierarchyPurpose}">
	 <td>
	 	<div class="tbldata-dotdot-200 sm" title="--">--</div>
	 </td>	
</c:if>

<td>
	<div class="sm" title="${expApproverHierarchy.totalAmount}">
	<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES\',\'ROLE_VIEW_EMPLOYEE_EXPENSES\')">
		<a  href="/pms/addApprovalExpense?expenseId=${expApproverHierarchy.expenseId}&expenseLocationCode=${expApproverHierarchy.locationsCode}&type=view" onclick="autoRefreshStatus('${expApproverHierarchy.expenseId}');" class="ExpAmount">${expApproverHierarchy.totalAmount}</a>
	</sec:authorize>
	<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES\',\'ROLE_VIEW_EMPLOYEE_EXPENSES\')">
		<span>${expApproverHierarchy.totalAmount}</span>
	</sec:authorize>
	</div>
</td>
	    	  			
<td>
	<div style="text-transform: capitalize;" id="status${expApproverHierarchy.expId}" title="${expApproverHierarchy.expensesStatus}">${expApproverHierarchy.expensesStatus}</div>
</td>

 <td>
	 <c:if test="${not empty expApproverHierarchy.expenseApprovalStatus && expApproverHierarchy.expenseApprovalStatus == 'NEW'}" >
		 <div class="sm text-center">--</div>
     </c:if>
      <c:if test="${not empty expApproverHierarchy.expenseApprovalStatus && expApproverHierarchy.expenseApprovalStatus == 'SUBMITTEDFORAPPROVAL'}" >
          <div class='sm text-center'id="action${expApproverHierarchy.expId}">
	           <sec:authorize access="hasAnyRole(\'ROLE_APPROVE_EMPLOYEE_EXPENSES\')"> 
		           <i class='fa fa-check' title='Approve' alt='Approve'  onclick='approveExpenseByExpenseId("${expApproverHierarchy.expId}","${expApproverHierarchy.tdSpanLength}","approval","list");'></i>
		          |<i class='fa fa-times' title='Reject'  alt='Rejected' onclick='rejectExpenseByExpenseId("${expApproverHierarchy.expId}","${expApproverHierarchy.tdSpanLength}","approval","list");'></i>
	           </sec:authorize>
         </div> 
      </c:if>
      <c:if test="${not empty expApproverHierarchy.expenseApprovalStatus && expApproverHierarchy.expenseApprovalStatus == 'REJECTED'}">
       	  <div class='sm text-center' ><h3 class='reje_color'>Rejected</h3>
	       	  <i class='glyphicon glyphicon-comment' id="${expApproverHierarchy.expId}" onclick='rejectedMessage(\"rejectedMessage"${expApproverHierarchy.expId}"\");'></i>
	       	 	<div class='app_error_mes' id="rejectedMessage${expApproverHierarchy.expId}">${expApproverHierarchy.rejectedReason}</div>
	   	 		 <br><p title="${expApproverHierarchy.actionPerformedBy}">By :${expApproverHierarchy.actionPerformedBy}</p>
   	 	 </div>
      </c:if>
      
      
       <c:if test="${not empty expApproverHierarchy.expenseApprovalStatus && expApproverHierarchy.expenseApprovalStatus == 'REOPENED'}">
       	  <div class='sm text-center' ><h3 class='bg-blue'>Reopened</h3>
	       	 <%--  <i class='glyphicon glyphicon-comment' id="${expApproverHierarchy.expId}" onclick='rejectedMessage(\"rejectedMessage"${expApproverHierarchy.expId}"\");'></i>
	       	 	<div class='app_error_mes' id="rejectedMessage${expApproverHierarchy.expId}">${expApproverHierarchy.reopenReason}</div> --%>
	   	 		 <br><p title="${expApproverHierarchy.actionPerformedBy}">By :${expApproverHierarchy.actionPerformedBy}</p>
   	 	 </div>
      </c:if>
      
      
      <c:if test="${not empty expApproverHierarchy.expenseApprovalStatus && expApproverHierarchy.expenseApprovalStatus ==  'APPROVED'}">
      	<c:if test="${not empty expApproverHierarchy.reopenRejectedReason}">
      		<!--  <div class='sm text-center'title="Rejected"><h3 class='reje_color'>Rejected</h3></div> -->

      		 <%--  	<i class='glyphicon glyphicon-comment' id="${expApproverHierarchy.expId}" onclick='rejectedMessage(\"rejectedMessage"${expApproverHierarchy.expId}"\");'></i> --%>
      		<%--  <div class='app_error_mes' id="rejectedMessage${expApproverHierarchy.expId}">${expApproverHierarchy.reopenRejectedReason}</div> --%
      		  	<%-- <i class='glyphicon glyphicon-comment' id="${expApproverHierarchy.expId}" onclick='rejectedMessage(\"rejectedMessage"${expApproverHierarchy.expId}"\");'></i> --%>
      		 <div class='app_error_mes' id="rejectedMessage${expApproverHierarchy.expId}">${expApproverHierarchy.reopenRejectedReason}</div>

      	</c:if>
      	<c:if test="${not empty expApproverHierarchy.actionPerformedBy}">
      		 <div class='sm text-center' > <h3>Approved</h3>
      		 <br><p title = "${expApproverHierarchy.actionPerformedBy}">By : ${expApproverHierarchy.actionPerformedBy}</p> 
      		 </div>
      	</c:if>
      </c:if>
      <c:if test="${not empty expApproverHierarchy.expenseApprovalStatus && expApproverHierarchy.expenseApprovalStatus == 'REOPEN'}">
    	<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES\')">
    		<a  href="/addApprovalExpense?expenseId=${expApproverHierarchy.expId}&expenseLocationCode=${expApproverHierarchy.locationsCode}&type=view" class="ExpAmount"></a>
    	</sec:authorize> 
   	  </c:if>
</td>
	  		
<td>
	<div class="sm" title="${expApproverHierarchy.expenseStartDateString}">${expApproverHierarchy.expenseStartDateString}</div>
</td>
	    	  		
<td>
	<div class="sm" title="${expApproverHierarchy.expenseEndDateString}">${expApproverHierarchy.expenseEndDateString}</div>
</td>	

<c:if test="${empty expApproverHierarchy.expensesApproverHierarchyPurpose}">
	 	<td><div class="sm" title="--">--</div></td>
</c:if>
	    	  		
<td>
	<div class="sm">${expApproverHierarchy.noOfExpenses}</div>
</td>
	
<td>
    <div style="width:75px" class="require_wrap_table">
      <select onchange="changeBillingStatus(${expApproverHierarchy.expId}, this);">
       <c:if test="${expApproverHierarchy.billingStatus == null}">
          <option value="" data-name="select">Select</option>
       </c:if>
          <option value="paid" ${expApproverHierarchy.billingStatus == "paid" ? "selected" : ""}>Paid</option>
          <option value="unpaid" ${expApproverHierarchy.billingStatus == "unpaid" ? "selected" : ""}>Un-paid</option>
     </select>
  </div>
</td>  		
 
</tr>
</c:forEach>		              