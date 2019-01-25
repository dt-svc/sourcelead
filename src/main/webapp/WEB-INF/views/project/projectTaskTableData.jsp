<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<tr style="display:none">
           <input type="hidden" id="searchInputValue"  value="${searchInputValue}">
	       <input type="hidden" id="totalRows" name="totalRows" value="${totalRows}">
	       <input type="hidden" id="pageCount" name="totalRowsPage" value="${pageCount}">
	      
 </tr>
<c:forEach var="timeDayEntry" items="${timeEntryDayListData}">
<tr>
	    <td><div class="tbldata-dotdot-100">${timeDayEntry.employeeName}</div></td>
	    <td><div class="tblminwidth-50">${timeDayEntry.timesheetDate}</div></td>
	    <td><div class="tblminwidth-30">${timeDayEntry.hours}</div></td>
	    <td><div class="tbldata-dotdot-sm" title="${timeDayEntry.comments}">${timeDayEntry.comments}</div></td>
	    <td><div class="tbldata-dotdot-100">${timeDayEntry.approvalStatus}</span></div></td>
	    
	    <c:if test="${timeDayEntry.projectMemberId eq projectMemID and timeDayEntry.approvalStatus ne 'APPROVED'}">
	    	<td><div class="fa fa-edit" id="editTimeDayEntry" onclick="editProjectTaskTimesheetDayEntry('${timeDayEntry.timesheetDate}','edit');"></div>|
	   <div class="fa fa-trash" style="color:red" id="deleteTimeDayEntry" onclick="deleteProjectTaskEntry('${timeDayEntry.timesheetEntryId}');"></div></td>
	    </c:if>
	    <c:if test="${timeDayEntry.projectMemberId eq projectMemID and timeDayEntry.approvalStatus eq 'APPROVED'}">
	    	<td><div>--</div></td>
	    </c:if>
	    <c:if test="${timeDayEntry.projectMemberId ne projectMemID}">
	    	<td><div>--</div></td>
	    </c:if>
</tr>
</c:forEach>