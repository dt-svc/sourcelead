<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>



<tr style="display:none">
	       <input type="hidden" id="totalRows" name="totalRows" value="${totalRows}">
	       <input type="hidden" id="pageCount" name="totalRowsPage" value="${pageCount}">
 </tr>
<c:forEach var="expensesDTO" items="${expensesDTOList}">
<tr id="row${expensesDTO.id}">

<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_MY_EXPENSES\')">
	<td><a href='${SourceLeadWeb_pmsUrl}/addExpense?expenseId=${expensesDTO.expenseAutoId}&expenseLocationCode=${expensesDTO.expenseLocationCode}&type=view'><div class='proj_show'><span>${expensesDTO.expenseAutoId}</span><i class='fa fa-binoculars'></i></div></a></td>
</sec:authorize>
<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_EDIT_MY_EXPENSES\')">
	<td><div class='proj_show tblminwidth-75'><span>${expensesDTO.expenseId}</span></div></td>
</sec:authorize>

<c:if test="${not empty expensesDTO.expensePurpose}">
	<td><div title="${expensesDTO.expensePurpose}" class="tbldata-dotdot">${expensesDTO.expensePurpose}</div></td>
</c:if>
<c:if test="${empty expensesDTO.expensePurpose}">
	<td><div title="--"  class="tbldata-dotdot">--</div></td>
</c:if>


<sec:authorize access="hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\')">                                                                                                                                            
	<c:if test="${not empty expensesDTO.projectUUID and  not empty expensesDTO.projectClientUUID and not empty expensesDTO.projectName and expensesDTO.projectStatus ne 'COMPLETED'}">
		<td>
			<div class="tbldata-dotdot-vsm">
				<a class="btnNoBg" href="${SourceLeadWeb_pmsUrl}/projectSummary?projectUUID=${expensesDTO.projectUUID}&projectClientUUID=${expensesDTO.projectClientUUID}" title="${expensesDTO.projectName}">${expensesDTO.projectName}</a>
			</div>
		</td>
	</c:if>
	<c:if test="${empty expensesDTO.projectUUID or empty expensesDTO.projectClientUUID or expensesDTO.projectStatus eq 'COMPLETED'}">
		<td>
			<div class="tbldata-dotdot-vsm">
				<a class="btnNoBg" title="${expensesDTO.projectName}">${expensesDTO.projectName}</a>
			</div>
		</td>
	</c:if>
</sec:authorize>                                                                                                                                                                                                                
                                                                                                                                                                                                                                    
<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\')">                                                                                                                                           
	<td><div title='${expensesDTO.projectName}' class="tbldata-dotdot-vsm">${expensesDTO.projectName}</div></td>                                                                                                                                                     
</sec:authorize>                                                                                                                                                                                                                
  
                                                                                                                                                                                                                                    
<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">                                                                                         
 <c:if test="${not empty expensesDTO.orgRelationUUID and not empty expensesDTO.clientName}">
 <td>
 	<div title="" class="tbldata-dotdot-100">
 		<a class="btnNoBg" href="${SourceLeadWeb_vmsUrl}/clientDashboard?orgRelationUUID=${expensesDTO.orgRelationUUID}" title="${expensesDTO.clientName}">${expensesDTO.clientName}</a>
 	</div>
 </td>
 </c:if>
 <c:if test="${empty expensesDTO.orgRelationUUID or empty expensesDTO.clientName}">
	<td>
	 	<div title="--" class="tbldata-dotdot-100">--</div>
	</td> 
</c:if>
 
</sec:authorize>                                                                                                                                                           

<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">                                                                                        
<c:if test="${not empty expensesDTO.clientName}">
	<td>
		<div title="${expensesDTO.clientName}" class="tbldata-dotdot-100">${expensesDTO.clientName}</div>
	</td>
</c:if>
<c:if test="${empty expensesDTO.clientName}">
	<td>
	 	<div title="--" class="tbldata-dotdot-100">--</div>
	</td> 
</c:if>
</sec:authorize> 


<c:if test="${not empty expensesDTO.startDate}">
	<td><div title="${expensesDTO.startDate}"  class="tblminwidth-75">${expensesDTO.startDate}</div></td>
</c:if>
<c:if test="${empty expensesDTO.startDate}">
	<td>
	 	<div title="--" class="tblminwidth-100">--</div>
	</td> 
</c:if>

<c:if test="${not empty expensesDTO.endDate}">
	 <td><div title="${expensesDTO.endDate}"  class="tblminwidth-75">${expensesDTO.endDate}</div></td>
</c:if>
<c:if test="${empty expensesDTO.endDate}">
	<td>
	 	<div title="--" class="tblminwidth-100">--</div>
	</td> 
</c:if>

<c:if test="${not empty expensesDTO.noOfExpenses}">
	 <td><div title="${expensesDTO.noOfExpenses}">${expensesDTO.noOfExpenses}</div></td>
</c:if>
<c:if test="${empty expensesDTO.noOfExpenses}">
	<td>
	 	<div title="--">--</div>
	</td> 
</c:if>

<c:if test="${not empty expensesDTO.totalAmount}">
	 <td><div title="${expensesDTO.totalAmount}">${expensesDTO.totalAmount}</div></td>
</c:if>
<c:if test="${empty expensesDTO.totalAmount}">
	<td>
	 	<div title="--">--</div>
	</td> 
</c:if>

<c:if test="${not empty expensesDTO.expenseStatus}">
	 <td> <div>${expensesDTO.expenseStatus}</div></td>
</c:if>
<c:if test="${empty expensesDTO.expenseStatus}">
	<td>
	 	<div title="--">--</div>
	</td> 
</c:if>
<c:if test="${not empty expensesDTO.billingStatus}">
     <td> <div>${expensesDTO.billingStatus == "paid" ? "Paid" :"Un-paid"}</div></td>
</c:if>
<c:if test="${empty expensesDTO.billingStatus}">
    <td>
         <div title="--">--</div>
    </td> 
</c:if>

</tr>
</c:forEach>




                                                                                                                                                           