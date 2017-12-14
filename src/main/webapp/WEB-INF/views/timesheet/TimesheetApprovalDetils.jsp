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
			<option data-icon="fa fa-calendar" ${clientList.key == timesheetApprovalDTO.client ? 'selected' : ''} value="${clientList.key}">${clientList.value}</option>
		</c:forEach>
	</select>
</div>
<div class="timeNewSe timeNewSe_client">
	<select id="stage"	onchange="timesheetApprovalDetils('stage');" class="form-control chosen-select" style="display: block;">
		<c:forEach var="stages" items="${timesheetApprovalDTO.stagesList}">
			<option data-icon="fa fa-calendar" ${stages == timesheetApprovalDTO.stage ? 'selected' : ''} value="${stages}">${stages}</option>
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

<div class="remove_search">
       		<input type="text" placeholder="Search..." value="" id="dataTableSearchExpenses">
</div>

<div class="clearfix"></div>
</div>

<div class="datatable_filter overflow_inherint">
	 <div id="approverDataTimesheetDiv"> 
		<jsp:include page="timesheetApprovalSubmission.jsp"></jsp:include>
	</div> 
</div>
<!-- AddRow and Add Attachments Divs  ####-->
         <div id="divadttTd1" style="display:none;" >   
             <div> 
              <select id="REPLACEROWIDtask1Id" name="REPLACEROWIDtask1Id" class="existedProjectTaskIdprojectId"  field-name="Task" onchange="prepareProjectTaskDetails('ENTRYINDEX','REPLACEROWIDtask1Id')"  onclick="getProjectTasks('REPLACEROWIDtask1Id','projectId')">  
              <option value=''>--select task--</option>
              </select>
             <input type="button" value="" onClick="removeTimeSheetRow('REPLACEROWID')" class="addtaskfield removetaskfield"/>
             </div>
        </div>
        <div id="divadttTd2" style="display:none;">
	    	   <span id="REPLACEROWIDtask1IdBillableTypeId" name="REPLACEROWIDtask1IdBillableTypeId" ></span>	
   		</div>
        <div id="divadttTd3" style="display:none;">
               <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.id" />
			   <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskName" />
			   <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskType"/>
                  
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
                   
                 <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].dayOfTheDate" value="DAYOFTHEDATE"/>
                 <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].date" value="DATEVALUE"/>
	               
			     <input type="text" onblur='editMe(this);' id="REPLACEROWIDtimeSheetDay7Id" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].hours" class="inline_field" placeholder="HH:MM" />	
                 <div class="timesheet_comm">
                 <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment7Id');"></i>
                 <span class="teskcomment" id="REPLACEROWIDcomment7Id" ><textarea name="timesheetSubmission.timesheetEntries[ENTRYINDEX].comments" id="REPLACEROWIDtextArea7Id"></textarea></span></div>            
    	 </div>
		
		<!-- Add Attachment ####-->  
		<div id="divAttachmentTd1" style="display: none; float: left;">
				<div id='REPLACEROWIDdocFileuploadFileGroup'>
		  			<input type='file' name='REPLACEROWIDdocFile' id='REPLACEROWIDdocFile' class="timesheetAttachmentValidation" onchange='validateUploadFile(REPLACEROWID)'> 
		  			<i class="fa fa-times" onclick="removeAttachmentRow(REPLACEROWID);"></i>
		  		</div>
	    </div>
<script>
$("#dataTableSearchExpenses").keyup(function(event) {
    if (event.keyCode === 13) {
    	var searchData = $('#dataTableSearchExpenses').val();
    	timesheetApprovalDetils('',searchData)
    	$('#dataTableSearchExpenses').val(searchData);
    }
});
</script>
	

