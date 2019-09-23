<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


    <c:if test="${!empty employeeTimesheetEntryDetailsDTO.weekFrequencyList}">
	 <div class='timeNewSe timeNewSe_a addicon'>
		<select id='currentTimeFrequency' name='currentTimeFrequency' class='form-control chosen-select' onchange="timesheetEntriesDivByTimeFrequency();">
			<c:forEach var="tf" items="${employeeTimesheetEntryDetailsDTO.weekFrequencyList}">
			    <option data-icon='fa fa-calendar' ${tf.key == employeeTimesheetEntryDetailsDTO.currentTimeFrequency ? "selected='selected'":"" } value="${tf.key}">${tf.value}</option>
			</c:forEach>
		</select>
	 </div>
	 </c:if> 
	 <div id="timesheetDivByWeekFrequency">
	    <jsp:include page="timesheetSubmission.jsp"></jsp:include>
	 </div> 

 <script>
	function timesheetEntriesDivByTimeFrequency(){ 
	               var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	               var invoiceFrequency = $("#invoiceFrequency").val();
	               var currentTimeFrequency = $("#currentTimeFrequency").val();
	               $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	        	   $.ajax({
		      	          type : "POST",
	        	      	  url: sourceleadUrl+"/getEmployeeTimesheetEntryDetails?invoiceFrequency="+invoiceFrequency+"&currentTimeFrequency="+currentTimeFrequency+"&timeSheetsFor=user",		
	        		      success :function(data) {
	        		    	  $("#timesheetEntriesDiv").empty();
	        		    	  $("#timesheetEntriesDiv").append(data);
	        		    	  $("#loadTime").removeAttr('style');
	        		    	  prepareChosenScripts();
	        		    	  setTotals();
	        		      }
	        	    }); 
	}
</script> 
<script>
$(document).ready(function(){
	if('${NORECORDSFOUND}' == "true"){
		$("#timesheetDivByWeekFrequency").empty();
  	  $("#timesheetDivByWeekFrequency").append('<div class="dataTables_empty">  <input type="hidden" id="hideExportApproval" value="EmptyList">  </div>');
	 }
});
</script>
<script type="text/javascript"	src='/resources/js/slead-common.js?staticTime=${staticTime}'></script>

