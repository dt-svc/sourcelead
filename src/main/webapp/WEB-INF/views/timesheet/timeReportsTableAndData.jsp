<!-- this is for mask -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="clients_wrap_table timesheetRepTab overflow_inherint">
   	<table id="timesheetReportsPageNation">
		<thead>
           	<tr>
           	   <th>Employee</th>
               <th>Project</th>
               <th>Frequency</th>
               <th>Rate</th>
               		<c:forEach var="dateFrequency" items="${timesheetReportsDTO.dataTableDateList}">
               			<th>${dateFrequency}</th>
               		</c:forEach>
               <th>Total</th>
               <th>Stage</th>
               <th>Project To Date</th>
               <th>Last Month</th>
            </tr>
		</thead>	
                <tbody id="tableData">
                	<jsp:include page="timeReportsSubmissionData.jsp"></jsp:include>
            	</tbody>
   </table>
</div>

      

    