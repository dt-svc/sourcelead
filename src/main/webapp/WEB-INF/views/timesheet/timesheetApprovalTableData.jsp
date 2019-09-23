<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<tr style="display:none">
	       <input type="hidden" id="totalRows" name="totalRows" value="${timesheetApprovalDTO.totalRows}">
	       <input type="hidden" id="pageCount" name="totalRows" value="${timesheetApprovalDTO.pageCount}">
 </tr>
<c:forEach var="timesheetSubmissionDTO" items="${timesheetApprovalDTO.timesheetSubmission}" varStatus="submissionStatus" >
           <c:if test="${submissionStatus.index == 0}">
            <input type="hidden" id="tableFor" name="tableFor" value="${timesheetSubmissionDTO.tableFor}">
          </c:if>
    	<tr id="row${timesheetSubmissionDTO.projectMemberId}" role="row" class="odd">
    	   <c:set var="timesheetSubmission" value="${timesheetSubmissionDTO}" scope="request"></c:set>
		   <jsp:include page="timesheetApprovalTableRowData.jsp"></jsp:include>
       </tr>
</c:forEach>


<script>
  $(document).ready(function () {
	  if('${AWKWARD}' == "true"){
		   window.location.href =  "/errorPage";
	  }
	});
</script>