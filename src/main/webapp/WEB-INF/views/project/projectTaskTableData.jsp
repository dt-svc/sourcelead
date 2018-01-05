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
	    <td><div><span>${timeDayEntry.employeeName}</span></div></td>
	    <td><div class="tbldata-dotdot-100">${timeDayEntry.timesheetDate}</div></td>
	    <td><div><span>${timeDayEntry.hours}</span></div></td>
	    <td><div class="tbldata-dotdot-me">${timeDayEntry.comments}</div></td>
	    <td><div><span>${timeDayEntry.approvalStatus}</span></div></td>
</tr>
</c:forEach>