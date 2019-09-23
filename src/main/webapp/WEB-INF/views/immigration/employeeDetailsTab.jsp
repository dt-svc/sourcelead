<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<section class="content-header clearfix">
      <div class="pull-left">
        <h2></h2>
      </div>
       
    </section>
    
	  	<div class="immigraTable overflow_Auto">
		  <table id ="visaTbl">
			<tbody>
			  <tr>
				<th>Petition Number</th>
				<th>Employee</th>
				<th>Petition Type</th>
				<th>Attorney</th>
				<th>Filed on</th>
				<th>Status</th>
			  </tr>
			
			 <c:forEach var="lcaEmp" items="${empList}" varStatus="rows">
			  <tr>
				<td><div class="tbldata-dotdot-sm" title = ""></div><a href=/app/immigration/viewImmigration?immigrationUUID=${lcaEmp.immigrationUUID}&candidateUUID=${lcaEmp.candidateUUID}&immiTemplateUUID=${lcaEmp.immiTemplateUUID}&myImmigrationPage=N>${lcaEmp.petitionId}</a></td>
				<td><div class="tbldata-dotdot-sm" title = ""></div>${lcaEmp.empName}</td>
				<td><div class="tbldata-dotdot-sm" title = ""></div>${lcaEmp.petitionType}</td>
				<td><div class="tbldata-dotdot-sm" title = ""></div>${lcaEmp.attorneyName}</td>
				<td><div class="tbldata-dotdot-sm" title = ""></div>${lcaEmp.filedOn}</td>
				<td><div class="tbldata-dotdot-sm" title = ""></div>${lcaEmp.status}</td>
			 </tr>
			
			</c:forEach>
			 <c:if test="${empty empList}">
				<tr>
				 <td colspan="5" class="dataTables_empty" valign="top"></td>
				</tr>
			 </c:if>
			
		</tbody>
		</table>
</div>
