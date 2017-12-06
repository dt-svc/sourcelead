<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script src="resources/js/jquery.maskedinput-1.3.min.js"></script>
<script src="resources/js/formvalidator.js"></script>

<c:forEach var="subDetails" items="${timesheetReportsDTO.timeReportsSubmissionDetails}">
<tr id="row859" role="row" class="odd">
	<td class="sorting_1">
		<div class="lg clearfix">
			<div class="uimg">
				<img id="userImage" onerror="predefineProfileImage(this);" src="${subDetails.fileUrl}" alt="Profile Image" class="img-circle img-responsive">
			</div>
			<div class="Utext">
				<i title="${subDetails.fullName}">${subDetails.fullName}</i>
				<i title="Emp Type ${subDetails.designationType}">${subDetails.designationType}</i>
			</div>
			<div></div>
		</div>
	</td>
	<td><div class="md">
			<i title="${subDetails.projectName},${subDetails.vendorName}">${subDetails.projectName}<br>${subDetails.vendorName}</i></div></td>
	<td><div class="sm">${subDetails.frequrncy}</div></td>
	<td><div class="sm">
			<i title="${subDetails.rate}">${subDetails.rate}</i>
		</div></td>
	
	<c:forEach var="dateFrequency" items="${timesheetReportsDTO.dataTableDateList}"> 
	  <c:forEach var="reportTime" items="${subDetails.reportsTimeMap}" >
	  <c:if test="${reportTime.key == dateFrequency}">
			<td>
				<div class="week">
					<span class="draft"><d title="week-Total-Hours:${reportTime.value.weekTotalHours}">${reportTime.value.weekTotalHours}</d></span>
					<div class="progress">
						<div
							class="progress-bar progress-bar-striped active progress-bar-info"
							role="progressbar" style="width: 60%">
							<d title="Billable:${reportTime.value.weekBillabeHours}">${reportTime.value.weekBillabeHours}</d>
						</div>
						<div
							class="progress-bar progress-bar-striped active progress-bar-warning"
							role="progressbar" style="width: 40%">
							<d title="Non-Billable:${reportTime.value.weekNonBillableHours}">${reportTime.value.weekNonBillableHours}</d>
						</div>
					</div>
				</div>
			</td>
		</c:if>
		</c:forEach>
	</c:forEach> 
	<td>
		<div class="week">
			<span class="draft"><d title="Month-Total-Hours:${subDetails.monthTotalHours}">${subDetails.monthTotalHours}</d></span>
			<div class="progress">
				<div
					class="progress-bar progress-bar-striped active progress-bar-info"
					role="progressbar" style="width: 60%">
					<d title="Month-Billable-Hours:${subDetails.monthBillabeHours}">${subDetails.monthBillabeHours}</d>
				</div>
				<div
					class="progress-bar progress-bar-striped active progress-bar-warning"
					role="progressbar" style="width: 40%">
					<d title="Month-NonBillable-Hours:${subDetails.monthNonBillableHours}">${subDetails.monthNonBillableHours}</d>
				</div>
			</div>
		</div>
	</td>
	<td><div class="sm">
		<c:if test="${subDetails.approvalStatus == 'Action Required'}">
			<b class="action" title="ACTION REQUIRED">ACTION REQUIRED</b>
		</c:if>
		<c:if test="${subDetails.approvalStatus == 'NEW'}">
			<b class="bgGReen" title="NEW">NEW</b>
		</c:if>
		<c:if test="${subDetails.approvalStatus == 'approved'}">
			<b class="proj_active" title="APPROVED">APPROVED</b>
		</c:if>
		<c:if test="${subDetails.approvalStatus == 'rejected'}">
			<b class="proj_closed" title="REJECTED">REJECTED</b>
		</c:if>
		<c:if test="${subDetails.approvalStatus == 'submitted for approval'}">
			<b class="proj_process" title="SUBMITTED FOR APPROVAL">SUBMITTED FOR APPROVAL</b>
		</c:if>
		</div></td>
	<td>
		<div class="week">
			<span class="draft"><d title="ProjectToDate-Total-Hours:${subDetails.projectToDateTotal}">${subDetails.projectToDateTotal}</d></span>
			<div class="progress">
				<div
					class="progress-bar progress-bar-striped active progress-bar-info"
					role="progressbar" style="width: 60%">
					<d title="ProjectToDate-Billable-Hours:${subDetails.projectToDateBillableTotal}">${subDetails.projectToDateBillableTotal}</d>
				</div>
				<div
					class="progress-bar progress-bar-striped active progress-bar-warning"
					role="progressbar" style="width: 40%">
					<d title="ProjectToDate-NonBillable-Hours:${subDetails.projectToDateNonBillableTotal}">${subDetails.projectToDateNonBillableTotal}</d>
				</div>
			</div>
		</div>
	</td>
	<td>
		<div class="week">
			<span class="draft"><d title="PreMonth-Total-Hours:${subDetails.lastMonthTotal}">${subDetails.lastMonthTotal}</d></span>
			<div class="progress">
				<div
					class="progress-bar progress-bar-striped active progress-bar-info"
					role="progressbar" style="width: 60%">
					<d title="PreMonth-Billable-Hours:${subDetails.lastMonthBillableTotal}">${subDetails.lastMonthBillableTotal}</d>
				</div>
				<div
					class="progress-bar progress-bar-striped active progress-bar-warning"
					role="progressbar" style="width: 40%">
					<d title="PreMonth-NonBillable-Hours:${subDetails.lastMonthNonBillableTotal}">${subDetails.lastMonthNonBillableTotal}</d>
				</div>
			</div>
		</div>
	</td>
</tr>
</c:forEach>

<script>
function pagination(){ 
    $('#timesheetReportsPageNation').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $('#timesheetReportsPageNation').wrap('<div class="width100percnt overflow_Auto"></div>');
}
</script>

<!-- <script>
$(document).ready(function() {
	reportslList();
});
</script>

<script>
function reportslList(){
    var url = '';
    var sourceleadUrl = '${SourceLeadWeb_sourceleadUrl}';
    var frequencyYear = $("#frequencyYear").val();
    if(frequencyYear == null || frequencyYear == '' || frequencyYear == 'undefined')
 	   frequencyYear = '';
      var frequencyMonth = null;
      if(frequencyYear!=null && frequencyYear!='')
      frequencyMonth = $(".time_months[data-year="+frequencyYear+"]").find("select").val();
      if(frequencyMonth == null || frequencyMonth == '' || frequencyMonth == 'undefined')
   	 frequencyMonth = '';
	 url = sourceleadUrl+"/prepareTimeReportsSubmissionDetails?&frequencyYear="+frequencyYear+"&frequencyMonth="+frequencyMonth;
     prepareReportsList(url,sourceleadUrl); 
} 

function prepareReportsList(url1,sourceleadUrl){
	
	 $.ajax({
	          type : "POST",
     	  url: url1,		
	      success :function(data) {
	    	  	  $("#tableData").empty();
		    	  $("#tableData").append(data);
		    	  pagination();
	      }
   }); 
	 
	/* var colIndex = 0;
	   $('#timesheetReportsPageNation').DataTable( {
	  	"processing": true,
    	"serverSide": true,
    	"searching": false,
    	"ajax": $.fn.dataTable.pipeline({
         	url: url,
        	method: 'GET',
        	pages: 1
     }),
    
    "scrollCollapse": false,
	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	"aaSorting": [[8,'desc']],
	"bLengthChange": true,
	"bDestroy": true,
	"ordering": true,
 });  */
}

</script>
 -->





