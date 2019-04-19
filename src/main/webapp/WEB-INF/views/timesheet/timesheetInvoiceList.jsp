<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="timesheetNewHead">
       <c:if test="${!empty employeeTimesheetEntryDetailsDTO.invoiceFrequencyList}">
       <div id="timesheetFrequencyDiv" class="timeNewSe">
       <!-- onchange="timesheetEmployeeDetils('frequency');" -->
		<select id="invoiceFrequency" name="invoiceFrequency" class="form-control chosen-select" onchange="timesheetEntriesDivByInvoiceFrequency()">
			<c:forEach var="invoiceFrequency" items="${employeeTimesheetEntryDetailsDTO.invoiceFrequencyList}">
				<option data-icon="fa fa-calendar" value="${invoiceFrequency}"  ${invoiceFrequency == employeeTimesheetEntryDetailsDTO.invoiceFrequency ? 'selected' : ''}>${invoiceFrequency}</option>
			</c:forEach>
		</select>
	  </div>
	  </c:if>
	  <div id='timesheetEntriesDivByInvoiceFrequency'>
	     <jsp:include page="timesheetWeekFrequencyList.jsp"></jsp:include>
	  </div>
	  
</div>
 <script>
	function timesheetEntriesDivByInvoiceFrequency(){ 
	               var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	               var invoiceFrequency = $("#invoiceFrequency").val();
	               var currentTimeFrequency = '';
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
	if('${AWKWARD}' == "true"){
		   window.location.href =  "/errorPage";
	 }
});
</script>

<script type="text/javascript"	src='/resources/js/slead-common.js?staticTime=${staticTime}'></script>
