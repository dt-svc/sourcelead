<!-- this is for mask -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="resources/js/jquery.twbsPagination.js"></script>
<div class="clients_wrap_table timesheetRepTab">
   	<table class="table">
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
<div class="clearfix">
  <div class="col-md-6">
  <div class="pull-left mt">
  <label class="pull-left"><span class="pull-left mt10 mr5">Show</span>
  <div class="dropdown-select pull-left mr5">
  <select>
  	<option value="10">10</option>
  	<option value="15">15</option>
  	<option value="20">20</option>
  </select>
  </div>
  <span class="pull-left mt10">entries</span>
  </label> 
  <span class="pull-left mt10 ml">Show page number</span>
  </div>
  </div>
  <div class="col-md-6">
  <ul id="pagination-demo" class="pagination-sm pull-right"></ul>
  </div>
</div> 
  <script>
  var options = {
		  totalPages: 50,
		  visiblePages: 5
		  };
  $('#pagination-demo').twbsPagination(options);
  
  </script>
      

    