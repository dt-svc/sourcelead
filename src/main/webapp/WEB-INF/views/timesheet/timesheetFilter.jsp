<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<label><strong>Timesheet Week Frequency</strong></label>
	<div class="clearfix" id="weekFrequencyDiv">
		<ul class="list-unstyled" id="frequencyFilter">
			<c:forEach var="weekFrequencyList"
				items="${timesheetApprovalDTO.weekFrequencyList}">
				<li class="radioIput positionRelative">
					<input	id="weekFrequency" name="weekFrequency" type="radio" ${weekFrequencyList.key == timesheetApprovalDTO.weekFrequency ? 'checked' : ''} value="${weekFrequencyList.key}"><label>${weekFrequencyList.key}</label></li>
			</c:forEach>
		</ul>
</div>
