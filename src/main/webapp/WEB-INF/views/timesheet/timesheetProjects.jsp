<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="timesheetNewHead">
       <div class="timeNewSe">
		<select id='projectMemberId' name='projectMemberId' class='form-control chosen-select' onChange='timesheetEntriesDivByProjectMemberId()'>
			<c:forEach var="pm" items="${timesheetEntryDetailsDTO.projectMembersList}">
			    <option ${pm.id == timesheetEntryDetailsDTO.projectMemberId ? "selected='selected'":"" } value="${pm.id}">${pm.projectName}</option>
			</c:forEach>
		</select>
	  </div>
	  <div id='timesheetEntriesDivByProjectMemberId'>
	     <jsp:include page="timesheetfrequencyList.jsp"></jsp:include>
	  </div>
</div>

	
<script>
  $(document).ready(function () {
	  if('${AWKWARD}' == "true"){
		   window.location.href =  "/errorPage";
	  }
	});
</script>
<script>
	function timesheetEntriesDivByProjectMemberId(){ 
	               var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	               var projectMemberId = $("#projectMemberId").val();
	               $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	               $.ajax({
		      	          type : "POST",
	        	      	  url: sourceleadUrl+"/getTimesheetfrequencyList?projectMembersId="+projectMemberId+"&timeSheetsFor=user",		
	        		      success :function(data) {
	        		    	  $("#timesheetEntriesDivByProjectMemberId").empty();
	        		    	  $("#timesheetEntriesDivByProjectMemberId").append(data);
	        		    	  $("#loadTime").removeAttr('style');
	        		    	  prepareChosenScripts();
	        		    	  setTotals();
	        		      }
	        	    }); 
	}
</script> 


<script type="text/javascript"	src='/resources/js/slead-common.js?staticTime=${staticTime}'></script>
