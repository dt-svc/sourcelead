<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 <div class='timeNewSe timeNewSe_a addicon'>
   <!--  <input type="hidden" name="timeFrequencies" value="timesheetEntryDetailsDTO.timeFrequencies"/> -->
    <input type="hidden" id="projectId" name="projectId" value="${timesheetEntryDetailsDTO.projectId}"/>
    <input type="hidden" id="masterCandidateId" name="masterCandidateId" value="${timesheetEntryDetailsDTO.masterCandidateId}"/>
    <input type="hidden" id="timesheetFor" name="timesheetFor" value="user"/>
	<select id='currentTimeFrequency' name='currentTimeFrequency' class='form-control chosen-select' onChange='timesheetDivByFrequency()' >
		<c:forEach var="tf" items="${timesheetEntryDetailsDTO.timeFrequencies}">
		    <option data-icon='fa fa-calendar' ${tf.key == timesheetEntryDetailsDTO.currentTimeFrequency ? "selected='selected'":"" } value="${tf.key}">${tf.value}</option>
		</c:forEach>
	</select>
 </div>
 <div id="timesheetDivByFrequency">
    <jsp:include page="timeSheetSubmission.jsp"></jsp:include>
 </div>
 <script>
	function timesheetDivByFrequency(currentTimeSubFrequency){ 
	               var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	               var projectMemberId = $("#projectMemberId").val();
	               var currentTimeFrequency = $("#currentTimeFrequency").val();
	               var subFrParam = '';
	               if(currentTimeSubFrequency != null && currentTimeSubFrequency != '' && currentTimeSubFrequency != 'undefined'){
	            	   subFrParam = "&currentTimeSubFrequency="+currentTimeSubFrequency;
	               }
	               $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	               $.ajax({
		      	          type : "POST",
	        	      	  url: sourceleadUrl+"/getTimesheetSubmissionDetails?projectMembersId="+projectMemberId+"&currentTimeFrequency="+currentTimeFrequency+"&timeSheetsFor=user"+subFrParam,		
	        		      success :function(data) {
	        		    	  $("#timesheetDivByFrequency").empty();
	        		    	  $("#timesheetDivByFrequency").append(data);
	        		    	  if(currentTimeSubFrequency != null && currentTimeSubFrequency != '' && currentTimeSubFrequency != 'undefined'){
	        		    		  $("#currentTimeSubFrequency").val(currentTimeSubFrequency)
		   	            	      $("#timeSubFrequency").val(currentTimeSubFrequency)
	        		    	  }
	        		    		setTotals();
	        		    	  $("#loadTime").removeAttr('style');
	        		      }
	        	    }); 
	}
</script> 
 
 
<script>
  $(document).ready(function () {
	  if('${AWKWARD}' == "true"){
		   window.location.href =  "/errorPage";
	  }
	});
</script>
 
 
<script type="text/javascript"	src='/resources/js/slead-common.js?staticTime=${staticTime}'></script>

