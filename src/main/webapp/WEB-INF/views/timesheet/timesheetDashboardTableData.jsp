<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<tr style="display:none">
	       <input type="hidden" id="totalRows" name="totalRows" value="${timesheetApprovalDTO.totalRows}">
	       <input type="hidden" id="pageCount" name="totalRows" value="${timesheetApprovalDTO.pageCount}">
	       <input type="hidden" id="searchData" name="searchData" value="${timesheetApprovalDTO.searchValue}">
 </tr>
<c:forEach var="timesheetSubmissionDTO" items="${timesheetApprovalDTO.timesheetSubmission}" varStatus="submissionStatus" >
          <c:if test="${submissionStatus.index == 0}">
            <input type="hidden" id="tableFor" name="tableFor" value="${timesheetSubmissionDTO.tableFor}">
          </c:if>
    	<tr id="row${timesheetSubmissionDTO.projectMemberId}${timesheetSubmissionDTO.currentTimeFrequency}" role="row" class="odd">
    	   <c:set var="timesheetSubmission" value="${timesheetSubmissionDTO}" scope="request"></c:set>
    	   
    	   <!--###############################  innerJsp for data  ############################### S-->
		   <jsp:include page="timesheetApprovalTableRowData.jsp"></jsp:include>
       </tr>
</c:forEach>
<!-- AddRow and Add Attachments Divs  ####-->
         <div id="divadttTd1" style="display:none;"  class="taskDropDown">   
             <div> 
              <select id="REPLACEROWIDtask1Id" name="REPLACEROWIDtask1Id" class="existedProjectTaskIdprojectId timSelWid"  field-name="Task" onchange="prepareProjectTaskDetails('ENTRYINDEX','REPLACEROWIDtask1Id')" >  
              <option value=''>--select task--</option>
              </select>
             <input type="button" value="" onClick="removeTimeSheetRow('REPLACEROWID')" class="addtaskfield removetaskfield"/>
             </div>
        </div>
        <div id="divadttTd2" style="display:none;">
             <div>
               <select id="REPLACEROWIDtask1IdBillableTypeId" name="REPLACEROWIDtask1IdBillableTypeId"  field-name="Billble Type" onchange="prepareBillableDetails('ENTRYINDEX','REPLACEROWIDtask1IdBillableTypeId')">  
                  <option value=''>--select BillableType--</option>
                  <option value='Billable'>Billable</option>
                  <option value='Non-Billable'>Non-Billable</option>
               </select>
             </div>
	    	 <!--   <span id="" name="" ></span>	 -->
   		</div>
   		 <div id="divadttTd10" style="display:none;">
		    	  <div><input type="text" id="REPLACEROWIDticketNumber1Id" name="REPLACEROWIDticketNumber1Id" onblur="setTicketNumber('ENTRYINDEX','REPLACEROWIDticketNumber1Id');" class="timeTick" field-name="Ref/Ticket No"></div>	
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
			   <input type="hidden" name="timesheetSubmission.timesheetEntries[ENTRYINDEX].timesheetTaskDTO.taskType"/>
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
		
		<!-- Add Attachment ####-->  
		<div id="divAttachmentTd1" style="display: none; float: left;">
				<div id='REPLACEROWIDdocFileuploadFileGroup'>
		  			<input type='file' name='REPLACEROWIDdocFile' id='REPLACEROWIDdocFile' class="timesheetAttachmentValidation" onchange='validateUploadFile(REPLACEROWID)'> 
		  			<i class="fa fa-times" onclick="removeAttachmentRow(REPLACEROWID);"></i>
		  		</div>
	    </div>


<script>
  $(document).ready(function () {
	  if('${AWKWARD}' == "true"){
		   window.location.href =  "/errorPage";
	  }
	});
</script>