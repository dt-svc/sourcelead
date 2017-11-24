<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="timesheetNewHead" id ="timeApprovaldiv">
<div class="timeNewSe timeNewSe_month addicon">
	<select id="frequencyMonth" name="frequencyMonth" class="form-control chosen-select" onchange="timesheetApprovalDetils('month');">
		<c:forEach var="frequencyMonth" items="${timesheetApprovalDTO.frequencyMonthList}">
			<option data-icon="fa fa-calendar" value="${frequencyMonth}"  ${frequencyMonth == timesheetApprovalDTO.frequencyMonth ? 'selected' : ''}>${frequencyMonth}</option>
		</c:forEach>
	</select>
</div>
<div id="timesheetFrequencyDiv" class="timeNewSe timeNewSe_week">
	<select id="timesheetFrequency" class="form-control chosen-select" onchange="timesheetApprovalDetils('frequency');">
		<c:forEach var="timesheetfrequency" items="${timesheetApprovalDTO.timesheetFrequencyList}">
			<option data-icon="fa fa-calendar" value="${timesheetfrequency}"  ${timesheetfrequency == timesheetApprovalDTO.timesheetFrequency ? 'selected' : ''}>${timesheetfrequency}</option>
		</c:forEach>
	</select>
</div>

<c:if test="${!empty timesheetApprovalDTO.timesheetFrequency  && timesheetApprovalDTO.timesheetFrequency != 'Monthly' }" >
<div id="weekFreTotalDiv">
	<div class="timeNewSe timeNewSe_week_date addicon" id="weekFrequencyDiv">
	<select id="weekFrequency" class="form-control chosen-select" onchange="timesheetApprovalDetils('week');" style="display: block;">
		<c:forEach var="weekFrequencyList" items="${timesheetApprovalDTO.weekFrequencyList}">
			<option data-icon="fa fa-calendar" ${weekFrequencyList.key == timesheetApprovalDTO.weekFrequency ? 'selected' : ''} value="${weekFrequencyList.key}">${weekFrequencyList.value}</option>
		</c:forEach>
	</select>
	</div>
</div>
</c:if>

<div class="timeNewSe timeNewSe_client">
	<select id="client"	onchange="timesheetApprovalDetils('client');" class="form-control chosen-select" style="display: block;">
		<option data-icon="fa fa-calendar" value="">Select Client</option>
		<c:forEach var="clientList" items="${timesheetApprovalDTO.clientList}">
			<option data-icon="fa fa-calendar" value="${clientList.key}">${clientList.value}</option>
		</c:forEach>
	</select>
</div>
<div class="timeNewSe timeNewSe_client">
	<select id="stage"	onchange="timesheetApprovalDetils('stage');" class="form-control chosen-select" style="display: block;">
		<c:forEach var="stages" items="${timesheetApprovalDTO.stagesList}">
			<option data-icon="fa fa-calendar" value="${stages}">${stages}</option>
		</c:forEach>
	</select>
</div>
<div class="export dropexport" onclick="dropDown();" id="exportHideId">
	<p data-toggle="tooltip" title="Export">
		<i class="glyphicon glyphicon-export"></i>
	</p>
	<ul id="exporrt">
		<li onclick="exportTimeSheetToPdfOrExcel('pdf');">
			<i class="fa fa-file-pdf-o text-danger"></i>&nbsp;PDF</li>
		<li onclick="exportTimeSheetToPdfOrExcel('excel');">
			<i class="fa fa-file-excel-o text-primary"></i>&nbsp;Excel</li>
	</ul>
</div>

<div class="clearfix"></div>
<div class="clearfix"></div>
</div>
<div class="timesheet_appr_table datatable_filter overflow_inherint">
	<div id="approverDataTimesheetDiv">
		<jsp:include page="timesheetApprovalSubmission.jsp"></jsp:include>
	</div>
</div>

<!-- -----------------------------------------------------script----------------------------------------------------- -->

<!-- @Author : NareshBolisetty,Shivayogi -->
<!-- when month is changed related weekFrequency will get appended -->
<!-- <script>
function calculateWeekFrequency(){
	var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
    var locationCode ='${SourceLeadWeb_locationCode}';
    var userId = '${SourceLeadWeb_user.id}';
    var frequencyMonth = $("#frequencyMonth").val();
    if(frequencyMonth == null || frequencyMonth == '' || frequencyMonth == 'undefined')
 	   frequencyMonth = '';
    
      var timesheetFrequency = $('#timesheetFrequency').val();
      if(timesheetFrequency == null || timesheetFrequency == '' || timesheetFrequency == 'undefined')
   	 timesheetFrequency = '';
      
      var weekFrequency = $('#weekFrequency').val();
    if((weekFrequency == null || weekFrequency == '' || weekFrequency == 'undefined'))
 	   weekFrequency = '';
    
    var stage = $('#stage').val();
    if(stage == null || stage == '' || stage == 'undefined')
 	   stage = '';
    
    var client = $('#client').val();
    if(client == null || client == '' || client == 'undefined')
 	   client = '';
    $("#weekFreTotalDiv").empty();
    $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	   $.ajax({
	          type : "POST",
	      	  url: sourceleadUrl+"/calculateWeekFrequency?&frequencyMonth="+frequencyMonth+"&timesheetFrequency="+timesheetFrequency+"&weekFrequency="+weekFrequency+"&client="+client+"&stage="+stage+"&locationCode="+locationCode+"&userId="+userId,		
		      success :function(data) {
		    	  $("#weekFreTotalDiv").empty();
		    	  $("#weekFreTotalDiv").append(data);
		    	  $("#loadTime").removeAttr('style');
		    	  prepareChosenScripts();  
		      }
	    }); 
}
</script> -->

