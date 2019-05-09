<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!--################################## in-line style   ##################################-->
<style>
.timeNewSe_week_date .chosen-drop{
   width : 225px !important;
} 
.timeNewSe_client .chosen-drop{
   width: 190px !important;
}
</style>


<!--################################## Content div Starts ##################################-->
<div class="positionRelative reportHead clearfix">
        <!-- show/hide accept and reject buttons,when SelectAll check-box is clicked   -->
		<div class="pull-left ml newAccBtn" id="hedderAcceptRejectButton" style="display: none">
			<a href="javascript:void(0)" onclick="acceptTimeSheetSubmissionBySubmissionId('approver');" title="Accept"><i class="fa fa-check" ></i></a> 
			<a href="javascript:void(0)" onclick="rejectTimeSheetSubmissionBySubmissionId('approver');"><i class="fa fa-times"></i></a>
		</div>

        <!-- Reset Button,which will reset all filtered values  -->
        <span class="pull-left refreIcon" id="advanceSearchDiv">
      		<a href="javascript:void(0)" class="refeIcon"  title="Refresh" onclick="resetProjectFilter();"></a>
        </span>
      
        <!-- Export Button,which will export the data -->
        <ul class="pull-right list-unstyled list-inline">
				<li id="vendorFiletPopup"><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="timesheetApprovalAdvanceFilter();"></a></li>
				<li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
					<div class="newActPop">
						<span>
						<c:if test="${!empty timesheetApprovalDTO.timesheetSubmission}">
						 	<a href="javascript:void(0)" onclick="exportTimeSheetToPdfOrExcel('pdf');" title="PDF">Export PDF</a>
							<a href="javascript:void(0)" onclick="exportTimeSheetToPdfOrExcel('excel');" title="Excel">Export Excel</a>	
						</c:if>		           	   		
						</span>
					</div>
				</li>
		   <!-- Search textbox -->
		  <li class=""><input type="text" class="repSearchBar" placeholder='Search' id='dataTableSearchTimesheets'/><span class="repSearchIcon" onClick="dataTableSearchTime();"></span></li>
        </ul>
</div>
<!-- list of assigned projectmembers for to fill/approve/reject their timesheets  -->
<div class="datatable_filter overflow_inherint">
	 <div id="approverDataTimesheetDiv"> 
		<jsp:include page="timesheetApprovalTable.jsp"></jsp:include>
	</div> 
</div>
<!--################################## Content div Ends ##################################-->



<!--################################################### AdvanceFilter Pop-up div ###################################################-->
<div class="advancedsearch-mask" id="searchMask" onclick="" style="display: none;"></div>
<div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search" style="display: none;" id="popUpFilterTimesheet">
                <h5 class="headerAdsrch">Filters
					<a href="javascript:void(0)" class="close-div" onclick="closeFilterPop()"><i class="fa fa-close"></i></a>
				</h5>
				<form id="timesheetFilterForm" name="timesheetFilterForm">
				<div class="addInner">
					<div class="row">
						<div class="col-md-2">
						<div class="row">
						<div class="col-md-12 mb">
								<label><strong>Frequency Month</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled">
										<c:forEach var="frequencyMonth" items="${timesheetApprovalDTO.frequencyMonthList}">
													<li class="radioIput positionRelative"><input data-icon="fa fa-calendar" onclick="getTimesheetFrequencyForFilter();" value="${frequencyMonth}"  ${frequencyMonth == timesheetApprovalDTO.frequencyMonth ? 'checked' : ''} id="frequencyMonth" name="frequencyMonth" type="radio">${frequencyMonth}
										</c:forEach>
			                        </ul>
								 </div>
						</div> 
						<div class="col-md-12 mb">		 
						<div class="clearfix mb">
							<label><strong>Timesheet Frequency</strong></label>
							 <div class="clearfix">
								<ul class="list-unstyled">
		                    	    <c:forEach var="timesheetfrequency" items="${timesheetApprovalDTO.timesheetFrequencyList}">
		                    	    	<li class="radioIput positionRelative"><input id="timesheetFrequency" name="timesheetFrequency" onclick="getTimesheetFrequencyForFilter();" type="radio" value="${timesheetfrequency}"  ${timesheetfrequency == timesheetApprovalDTO.timesheetFrequency ? 'checked' : ''}><label for="${timesheetfrequency}">${timesheetfrequency}</label></li>
		                        	</c:forEach>
		                        </ul>
							 </div>
						</div>
						</div>
						</div>
						</div>
						<div class="col-md-3">
						<div class="row">
						<c:if test="${!empty timesheetApprovalDTO.timesheetFrequency  && timesheetApprovalDTO.timesheetFrequency != 'Monthly' }" >	 
						 		<div class="col-md-12 mb" id="timesheetWeekFrequencyFilter">
						 			<jsp:include page="timesheetApprovalWeekFrequencyFilter.jsp"></jsp:include>
						 		</div>
						</c:if>	
						  <div class="col-md-12 mb">
							<label><strong>Stage</strong></label>
							 <div class="clearfix">
								<ul class="list-unstyled" id="Stage">
		                    	    <c:forEach var="stages" items="${timesheetApprovalDTO.stagesList}">
		                    	    	<li class="radioIput positionRelative"><input id="stage" name="timesheetStatus" type="radio" value="${stages}"  ${stages == timesheetApprovalDTO.stage ? 'checked' : ''}><label for="${stages}">${stages}</label></li>
		                        	</c:forEach>
		                        </ul>
							 </div>
							</div>
						  </div>
						</div>
							 <div class="col-md-4 mb positionRelative">
									<div class="clearfix dynamic_search filterDynamicsearch">
										<input type="text" id="projectSearchInput" onkeyup="searchProject()" placeholder="Projects" title="Projects">
										<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
									</div>
									 <div class="clearfix filterSearch-ul">
										<ul class="list-unstyled positionRelative filterSearch mt5" id="myProjectUL">
													<c:forEach var="projects" items="${timesheetApprovalDTO.projectList}">
													 	<li class="radioIput positionRelative"><input type="radio" id="project" name="project" ${projects.key == timesheetApprovalDTO.project ? 'checked' : ''} data-value="${projects.value}" onclick="getProjectnameByProjectId();" value="${projects.key}"><label>${projects.value}</label></li>
													</c:forEach>
													<input type="hidden" id="projectName" name="projectName">
										</ul>
									 </div> 
							 </div> 
							 <div class="col-md-3 mb positionRelative">
									<div class="clearfix dynamic_search filterDynamicsearch">
										<input type="text" id="searchClientInput" onkeyup="searchClient()" placeholder="Client" title="Type in a client">
										<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
									</div>
									 <div class="clearfix filterSearch-ul">
										<ul class="list-unstyled positionRelative filterSearch mt5" id="myClientUL">
											<c:forEach var="clientList" items="${timesheetApprovalDTO.clientList}">
											<li class="radioIput positionRelative"><input type="radio" ${clientList.key == timesheetApprovalDTO.client ? 'checked' : ''} value="${clientList.key}" name="client" id="client" value="30"><label>${clientList.value}</label></li>
											</c:forEach>
										</ul>
									 </div> 
							 </div> 
					</div>
					</div>
					</form>
					<div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Apply" id="search_requiremet" onclick="getTimesheetFilterData();" class="btn btn-sm btn-success">
							<input type="button" value="Reset" id="clear_search" onclick="resetProjectFilter();closeFilterPop();" class="btn btn-sm btn-primary">
						</div>
					</div>
				
 </div>

<!--################################## AddRow div's For TimesheetEntries #######################################################-->
         <div id="divadttTd1" style="display:none;">   
             <div class="taskDropDown"> 
              <select id="REPLACEROWIDtask1Id" name="REPLACEROWIDtask1Id" class="existedProjectTaskIdprojectId timSelWid"  field-name="Task" onchange="prepareProjectTaskDetails('ENTRYINDEX','REPLACEROWIDtask1Id')">  
              </select>
             <input type="button" value="" onClick="removeTimeSheetRow('REPLACEROWID')" class="addtaskfield removetaskfield"/>
             </div>
        </div>
        <div id="divadttTd2" style="display:none;">
             <div class="taskDropDown">
               <select id="REPLACEROWIDtask1IdBillableTypeId" name="REPLACEROWIDtask1IdBillableTypeId"  field-name="Billble Type" onchange="prepareBillableDetails('ENTRYINDEX','REPLACEROWIDtask1IdBillableTypeId')">  
                  <option value='Billable'>Billable</option>
                  <option value='Non-Billable'>Non-Billable</option>
               </select>
             </div>
	    	 <!--   <span id="" name="" ></span>	 -->
   		</div>
   		 <div id="divadttTd10" style="display:none;">
		    	  <div><input type="text" id="REPLACEROWIDticketNumber1Id" name="REPLACEROWIDticketNumber1Id" onblur="setTicketNumber('ENTRYINDEX','REPLACEROWIDticketNumber1Id');" class="timeTick" field-name=" "></div>	
	   </div>
	   <div id="divadttTd12" style="display:none;">
	             <div>
	               <select id="REPLACEROWIDhourlyType1Id" name="REPLACEROWIDhourlyType1Id"  field-name="Hourly Type" onchange="setHourlyType('ENTRYINDEX','REPLACEROWIDhourlyType1Id');">  
	                      <option value='Regular'>Regular</option>
		                  <option value='Overtime'>Overtime</option>
	               </select>
	             </div>
   		</div>
        <div id="divadttTd3" style="display:none;">
               <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.id" />
			   <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskName" />
			   <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskType" />
               <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].ticketNumber"/>  
               <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hourlyType" value="Regular"/>
               <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].dayOfTheDate" value="DAYOFTHEDATE"/>
               <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].date" value="DATEVALUE"/>
              
               
	    	   <input type="text" onblur='editMe(this);'   id="REPLACEROWIDtimeSheetDay1Id" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hours" class="inline_field" placeholder="HH:MM" />	
               <div class="timesheet_comm">
                <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment1Id');"></i>
                <span class="teskcomment" id="REPLACEROWIDcomment1Id" ><textarea name="timesheetSubmission.timesheetEntries[ENTRYINDEX].comments" id="REPLACEROWIDtextArea1Id" ></textarea></span>
               </div>
   		</div>
        <div id="divadttTd4" style="display:none;">
	               <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.id" />
				   <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskName" />
				   <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskType"/>
                    <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].ticketNumber"/>
                    <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hourlyType" value="Regular"/> 
	               <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].dayOfTheDate" value="DAYOFTHEDATE"/>
	               <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].date" value="DATEVALUE"/>
               
				  <input type="text" onblur='editMe(this);'   id="REPLACEROWIDtimeSheetDay2Id" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hours" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                      <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment2Id');"></i>
                  <span class="teskcomment" id="REPLACEROWIDcomment2Id" ><textarea name="timesheetSubmission.timesheetEntries[ENTRYINDEX].comments" id="REPLACEROWIDtextArea2Id" ></textarea></span></div>
        </div>
        <div id="divadttTd5" style="display:none;">
                   <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.id" />
				   <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskName" />
				   <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskType"/>
                    <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].ticketNumber"/> 
                    <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hourlyType" value="Regular"/>
	               <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].dayOfTheDate" value="DAYOFTHEDATE"/>
	               <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].date" value="DATEVALUE"/>
               
				  <input type="text" onblur='editMe(this);'    id="REPLACEROWIDtimeSheetDay3Id" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hours" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment3Id');"></i>
                  <span class="teskcomment" id="REPLACEROWIDcomment3Id" ><textarea name="timesheetSubmission.timesheetEntries[ENTRYINDEX].comments" id="REPLACEROWIDtextArea3Id"></textarea></span></div>
        </div>
        <div id="divadttTd6" style="display:none;">
                   <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.id" />
				   <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskName" />
				   <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskType"/>
                    <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].ticketNumber"/> 
                    <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hourlyType" value="Regular"/>
	               <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].dayOfTheDate" value="DAYOFTHEDATE"/>
	               <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].date" value="DATEVALUE"/>
	               
				  <input type="text" onblur='editMe(this);'    id="REPLACEROWIDtimeSheetDay4Id" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hours" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment4Id');"></i>
                  <span class="teskcomment" id="REPLACEROWIDcomment4Id" ><textarea name="timesheetSubmission.timesheetEntries[ENTRYINDEX].comments" id="REPLACEROWIDtextArea4Id"></textarea></span></div>           
        </div>
        <div id="divadttTd7" style="display:none;">
                   <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.id" />
				   <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskName" />
				   <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskType"/>
                    <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].ticketNumber"/> 
                    <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hourlyType" value="Regular"/>
	               <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].dayOfTheDate" value="DAYOFTHEDATE"/>
	               <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].date" value="DATEVALUE"/>
	               
				  <input type="text" onblur='editMe(this);'    id="REPLACEROWIDtimeSheetDay5Id" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hours" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment5Id');"></i>
                  <span class="teskcomment" id="REPLACEROWIDcomment5Id" ><textarea name="timesheetSubmission.timesheetEntries[ENTRYINDEX].comments" id="REPLACEROWIDtextArea5Id"></textarea></span></div>  
    	 </div>
         <div id="divadttTd8" style="display:none;">
                   <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.id" />
				   <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskName" />
				   <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskType"/>
                    <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].ticketNumber"/> 
                    <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hourlyType" value="Regular"/>
	               <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].dayOfTheDate" value="DAYOFTHEDATE"/>
	               <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].date" value="DATEVALUE"/>
	               
			     <input type="text" onblur='editMe(this);' id="REPLACEROWIDtimeSheetDay6Id" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hours" class="inline_field" placeholder="HH:MM" />	
                 <div class="timesheet_comm">
                 <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment6Id');"></i>
                 <span class="teskcomment" id="REPLACEROWIDcomment6Id" ><textarea name="timesheetSubmission.timesheetEntries[ENTRYINDEX].comments" id="REPLACEROWIDtextArea6Id"></textarea></span></div>
         </div>
         <div id="divadttTd9" style="display:none;">
                 <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.id" />
				 <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskName" />
				 <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskType"/>
                 <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].ticketNumber"/> 
                 <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hourlyType" value="Regular"/>
                 <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].dayOfTheDate" value="DAYOFTHEDATE"/>
                 <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].date" value="DATEVALUE"/>
	               
			     <input type="text" onblur='editMe(this);' id="REPLACEROWIDtimeSheetDay7Id" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hours" class="inline_field" placeholder="HH:MM" />	
                 <div class="timesheet_comm">
                 <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment7Id');"></i>
                 <span class="teskcomment" id="REPLACEROWIDcomment7Id" ><textarea name="timesheetSubmission.timesheetEntries[ENTRYINDEX].comments" id="REPLACEROWIDtextArea7Id"></textarea></span></div>            
    	 </div>

		
<!--################################## AddAttachment div's For TimesheetEntries ######################################-->  
		<div id="divAttachmentTd1" style="display: none; float: left;">
				<div id='REPLACEROWIDdocFileuploadFileGroup'>
		  			<input type='file' name='REPLACEROWIDdocFile' id='REPLACEROWIDdocFile' class="timesheetAttachmentValidation" onchange='validateUploadFile(REPLACEROWID)'> 
		  			<i class="fa fa-times" onclick="removeAttachmentRow(REPLACEROWID);"></i>
		  		</div>
	    </div>
 


<script>
 var selectedRecords = {};
  $(document).ready(function () {
	  if('${AWKWARD}' == "true"){
		   window.location.href =  "/errorPage";
	  }else{
		  $("#selectAllTimeSheetCheckBoxForSubmitForApproval").click(function () {
		        $(".checkBoxForSubmitForApproval").prop('checked', $(this).prop('checked'));
		        $(".checkBoxForSubmitForApproval").each(function() {
		        	selectedRecords[$(this).val()] = $(this).is(":checked");
		        });
		        displayAcceptRejectButtonOnHedder();
		    });
	  }
	   
	});
 </script>

<!--################# datatable search scripts starts  #################-->
<script>
/* works based on enter-button */
$("#dataTableSearchTimesheets").keyup(function(event) {
	if (event.keyCode === 13) {
    	$('#dataTableSearchTimesheets').parent().find('p.jquery_form_error_message').remove();
    	var searchData = $('#dataTableSearchTimesheets').val();
    	var regex =  new RegExp(/[\\]/g);
    	if(regex.test(searchData) == false){
    		timesheetApprovalDetils('',searchData)
    	}else{
    		$("#dataTableSearchTimesheets").after("<p class='jquery_form_error_message'><span>Enter valid Search Data</span>");
    	}
    	$('#dataTableSearchTimesheets').val(searchData);
    }
});

/* works based on onclick funtion */
function dataTableSearchTime(){
	$('#dataTableSearchTimesheets').parent().find('p.jquery_form_error_message').remove();
	var searchData = $('#dataTableSearchTimesheets').val();
	var regex =  new RegExp(/[\\]/g);
	if(regex.test(searchData) == false){
		timesheetApprovalDetils('',searchData)
	}else{
		$("#dataTableSearchTimesheets").after("<p class='jquery_form_error_message'><span>Enter valid Search Data</span>");
	}
	$('#dataTableSearchTimesheets').val(searchData);
 }
</script>
<!--################# datatable search scripts ends ################# -->



<!--################# Advance-search scripts starts  #################-->
<script>
    /* open advance-search filter */
	function timesheetApprovalAdvanceFilter() {
		$('#searchMask').show();
		$('#popUpFilterTimesheet').show();
	}

	/* reset advance-search filter */
	function resetProjectFilter(){
		location.reload(true);	
	}

	/* close advance-search filter */
	function closeFilterPop(){
		$('#searchMask').hide();
		$('#popUpFilterTimesheet').hide();
	}

</script>
<script>
/* setting projectname value on selection of project in advance-search filter ##### */
function getProjectnameByProjectId(){
	var project = $('input[id=project]:checked').val();
	if(project != null && project != '' && project !='undefined'){
		var projectname = $('input[id=project]:checked').data('value');
		console.log(projectname);
		$('#projectName').attr("value",projectname);
	}
}
</script>

<script>
/*#####  getting week frequency based on month-selection in advance-search filter  #####*/
function getTimesheetFrequencyForFilter(){
	var frequencyMonth = $('input[id=frequencyMonth]:checked').val();
	var timesheetFrequency = $('input[id=timesheetFrequency]:checked').val();
	  $.ajax({
	          type : "POST",
      	  	  url: sourceleadUrl+"/getTimesheetFrequencyForFilter?&frequencyMonth="+frequencyMonth+"&timesheetFrequency="+timesheetFrequency,		
	      	  success :function(data) {
		    	  $("#timesheetWeekFrequencyFilter").empty();
		    	  $("#timesheetWeekFrequencyFilter").append(data);
	      }
    }); 

}
</script>


<script type="text/javascript">
/* #####  getting results based on filtered data  ##### */
function getTimesheetFilterData(){
	var formData = getFormData("timesheetFilterForm");
	if('frequencyMonth' in formData) {    
		$("#frequencyMonth").val(formData['frequencyMonth']); 
	}
	if('timesheetFrequency' in formData){
		$("#timesheetFrequency").val(formData['timesheetFrequency']); 
	}
	if('weekFrequency' in formData){
		$("#weekFrequency").val(formData['weekFrequency']); 
	}
	if('timesheetStatus' in formData){
		$("#stage").val(formData['timesheetStatus']); 
	}
	if('client' in formData){
		if(formData['client'] != null && formData['client'] != '' && formData['client'] !='undefined'){
			$("#client").val(formData['client']); 
		}else{
			$("#client").val('');
		}
	}
	if('project' in formData){
		$("#project").val(formData['project']); 
	}
	
	var selectedProjectValue = $("input[id='project']:checked").data('value');
	if(selectedProjectValue != null && selectedProjectValue !='' && selectedProjectValue !='undefined'){
		$("#projectName").val(selectedProjectValue); 
	}
	timesheetApprovalDetils();
}



/*search project with in selected projectlist of advancesearch filter  */
function searchProject() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("projectSearchInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myProjectUL");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("label")[0];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";

        }
    }
}

/*search client with in selected clientlist of advancesearch filter  */
function searchClient() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("searchClientInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myClientUL");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("label")[0];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";

        }
    }
}

</script>
<!--################# Advance-search scripts Ends  #################-->
<script>
function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
	}
</script>

