<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 <div class='timeNewSe timeNewSe_a addicon'>
   <!--  <input type="hidden" name="timeFrequencies" value="timesheetEntryDetailsDTO.timeFrequencies"/> -->
    <input type="hidden" id="projectId" name="projectId" value="${timesheetEntryDetailsDTO.projectId}"/>
    <input type="hidden" id="masterCandidateId" name="masterCandidateId" value="${timesheetEntryDetailsDTO.masterCandidateId}"/>
    <input type="hidden" id="timesheetFor" name="timesheetFor" value="user"/>
	<select id='timesheetFrequency' name='currentTimeFrequency' class='form-control chosen-select' onChange='timesheetDivByFrequency()' >
		<c:forEach var="tf" items="${timesheetEntryDetailsDTO.timeFrequencies}">
		    <option data-icon='fa fa-calendar' ${tf.key == timesheetEntryDetailsDTO.currentTimeFrequency ? "selected='selected'":"" } value="${tf.key}">${tf.value}</option>
		</c:forEach>
	</select>
 </div>
 <div id="timesheetDivByFrequency">
    <jsp:include page="timeSheetSubmission.jsp"></jsp:include>
 </div>
<script type="text/javascript"	src='resources/js/slead-common.js?staticTime=${staticTime}'></script>
