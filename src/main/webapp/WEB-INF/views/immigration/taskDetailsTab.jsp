<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="immigraTabsHead">
		  	<!-- <a href="javascript:void(0)" id="addTask"><i class="glyphicon glyphicon-plus"></i> Add Tasks</a>  -->
		  	<span><input class="immigraTabSerach" placeholder="Subject" title="Subject" id="tasksSearchVal" type="text"></span>
		</div>
	  	<div class="immigraTable overflow_Auto">	
		  <table>
			<tbody>
			  <tr>
				<th>Subject</th>
				<th>Date & Time</th>
				<th>Owner</th>
				<th>Assigned to</th>
				<th>Description</th>
				<th>Mode</th>
				<th>Status</th>
			  </tr>
			 <c:forEach var="items" items="${tasksList}" varStatus="rows">
			  <tr>
				<td><div class="tbldata-dotdot-me">${items.subject}</div></td>
				<td><div class="tbldata-dotdot-me">${items.taskDateStr}</div></td>
				<td><div class="tbldata-dotdot-vsm">${items.taskOwnerName}</div></td>
				<td><div class="tbldata-dotdot-vsm">${items.assignedName}</div></td>
				<td><div class="tbldata-dotdot-vsm">${items.description}</div></td>
				<td><div class="tbldata-dotdot-vsm">${items.alertType}</div></td>
				<td><div class="tbldata-dotdot-vsm">${items.taskStatus}</div></td>
			  </tr> 
			  </c:forEach>
			 </tbody>
			
		  </table>
		</div>
		<script>
$("#tasksSearchVal").keyup(function (e) {
    if (e.which === 13) {
    	var searchVal = $("#tasksSearchVal").val();
    	getImmigrationTasks(immigrationId,"${myImmigrationPage}",searchVal);
    }
});
</script>