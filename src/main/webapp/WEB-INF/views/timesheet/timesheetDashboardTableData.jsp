<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<tr style="display:none">
	       <input type="hidden" id="totalRows" name="totalRows" value="${timesheetApprovalDTO.totalRows}">
	       <input type="hidden" id="pageCount" name="totalRows" value="${timesheetApprovalDTO.pageCount}">
 </tr>
<c:forEach var="timesheetSubmissionDTO" items="${timesheetApprovalDTO.timesheetSubmission}" varStatus="submissionStatus" >
    	<tr id="row${timesheetSubmissionDTO.dashboardDateList}" role="row" class="odd">
    	   <c:set var="submission" value="${timesheetSubmissionDTO}" scope="request"></c:set>
		   <jsp:include page="timesheetDashbordpTableRowData.jsp"></jsp:include>
       </tr>
</c:forEach>