<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script src="resources/js/jquery.maskedinput-1.3.min.js"></script>
<script>
	function predefineProfileImage(imageId){
		$(imageId).attr('src','resources/images/userprofile-img.png');
	}
</script>

<script>
$(document).ready(function() {
      approvalList();
});
</script>

<script>
  function approvalList(){
	   $.ajax({
           type : "POST",
            url: "http://localhost:8080/sourcelead/getArrovalSubmissions?${_csrf.parameterName}=${_csrf.token}",        
          success :function(data) {
              
                 var json = JSON.parse(data);
                 var output = "";
                 
                 output = output+' <table>';
                 output = output+' <tbody>';
                 output = output+' <thead>';
                 output = output+'     <tr>';
                 output = output+'         <th>Employee</th>';
                 output = output+'         <th>Frequency</th>';
                 output = output+'         <th>Total hours</th>';
                 output = output+'         <th>Stage</th>';
                 output = output+'         <th>Action</th>';
                 output = output+'     </tr>';
                 output = output+' </thead>';
                 
                 
                 for (var i=0; i<json.length; i++)
                 {
                         var approval = json[i];
                         output = output+'<tr>';
                         output = output+'<td><div class="time_appr_user">';
                         output = output+'<img  id="userImage" onerror="predefineProfileImage(this);" src="<c:url value="/displayUserImage/'+approval.projectMembers.employee.user.id+'"/>" alt="Profile Image" class="img-circle img-responsive">';
                         output = output+    '<span><b>'+approval.fullName+'</b></span></div></div>';
                         output = output+'</td>';
                         output = output+'<td><div> '+approval.startDateStr+'  To  ';
                         output = output+         ''+approval.endDateStr+'';
                         output = output+     '</div>';
                         output = output+ '</td>';
                         output = output+ '<td><div><a    onclick="myTimesheet('+approval.Id+','+approval.startDateStr+','+approval.endDateStr+');"  id="timesheetPopup'+approval.Id+'"> '+approval.combinedTotalHours+'</a></div></td>';
                         output = output+'<td><div>'+approval.combinedStatus+'</div></td>';
                         output = output+'<td>';
                           if(approval.submissionApprovalStatus == 'NEW'){
                               output = output+'<div><i class="fa fa-check" alt="Approve"  onclick="approveSubmission('+approval.Id+');"></i>|<i class="fa fa-times" alt="Rejected" onclick="rejecteSubmission('+approval.Id+');"></i></div>';
                               output = output+'<div id="rejectedPopup"></div>';
                           }
                         if(approval.submissionApprovalStatus=='REJECTED'){
                             output = output+'<div><h3 class="reje_color">Rejected</h3>';
                             output = output+'<i class="glyphicon glyphicon-comment" onclick="rejectedMessage(rejectedMessage'+approval.Id+');">';
                             output = output+  '<strong id="rejectedMessage'+approval.Id+'"><p>Rejected message here</p></strong>';
                             output = output+ '</i>';
                             output = output+'</div>';
                         }
                         if(approval.submissionApprovalStatus=='APPROVED'){
                             output = output+'<div><h3>Approved</h3></div>';
                         }  
                          output = output+'</td>' ;
                          output = output+ '</tr>';
                 }
               
                 output = output+' </tbody>';
                 output = output+' </table>';
                 
                 $("#timesheetApprList").empty();
                 $("#timesheetApprList").append(output);
          }
            
       }); 
  } 
  </script>
<body onclick="bodyClick();">
<!-- Content Area -->
      <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Timesheet approval</h2>
          </div>
          <div class="pull-right brud-crum">Home &raquo; General Form &raquo; Create User</div>
        </section>
        
		<!-- Project -->
<div class="timesheet_out">

	<div class="timesheet_appr_wrap">
		<div class="timesheet_appr_wrap">
		<div class="timesheet_head">
        	<ul>
                <li><i class="fa fa-filter"></i><span>Client</span><i class="glyphicon glyphicon-play" id="filter1ApprovePop"></i>|
                	<div id="filter1ApprovedPop" class="approved_pop">  
                    	<ul>
                        	<li><input type="checkbox" value="None" id="c1"><label for="c1">client1</label></li>
							<li><input type="checkbox" id="c2"><label for="c2">client2</label></li>
							<li><input type="checkbox" id="c3"><label for="c3">client3</label></li>
							<li><input type="checkbox" id="c4"><label for="c4">client4</label></li>
                        </ul>
                    </div>                	
                </li>
                <li><i class="fa fa-filter"></i><span>Project</span><i class="glyphicon glyphicon-play" id="filter2ApprovePop"></i>|
                	<div id="filter2ApprovedPop" class="approved_pop">
                    	<ul>
                        	<li><input type="checkbox" value="None" id="c5"><label for="c5">project1</label></li>
							<li><input type="checkbox" id="c6"><label for="c6">project2</label></li>
							<li><input type="checkbox" id="c7"><label for="c7">project3</label></li>
							<li><input type="checkbox" id="c8"><label for="c8">project4</label></li>
                        </ul>
                    </div>                	
                </li>				
                <li><i class="fa fa-filter"></i><span>Employee</span><i class="glyphicon glyphicon-play" id="filter3ApprovePop"></i>|
                	<div id="filter3ApprovedPop" class="approved_pop">
                    	<ul>
                        	<li><input type="checkbox" value="None" id="c9"><label for="c9">employee1</label></li>
							<li><input type="checkbox" id="c10"><label for="c10">employee2</label></li>
							<li><input type="checkbox" id="c11"><label for="c11">employee3</label></li>
							<li><input type="checkbox" id="c12"><label for="c12">employee4</label></li>
                        </ul>
                    </div>                	
                </li>				
                <li><i class="fa fa-filter"></i><span>Approved<b>30</b></span><i class="glyphicon glyphicon-play" id="filter4ApprovePop"></i>|
                	<div id="filter4ApprovedPop" class="approved_pop" >
                    	<ul>
                        	<li><input type="checkbox" value="None" id="c13"><label for="c13">All</label></li>
							<li><input type="checkbox" id="c14"><label for="c14">Approved</label></li>
							<li><input type="checkbox" id="c15"><label for="c15">Draft</label></li>
							<li><input type="checkbox" id="c16"><label for="c16">Rejected</label></li>
                        </ul>
                    </div>                	
                </li>				
                <div class="clearfix"></div>
            </ul>
            <div class="clearfix"></div>
        </div>
		
		<div class="timesheet_appr_table">
		<div id="timesheetApprList">   </div>
	    <%-- <table>
        	<tbody>
			<thead>
            	<tr>
                	<th>Employee</th>
                    <th>Frequency</th>
                    <th>Total hours</th>
                    <th>Stage</th>
                    <th>Action</th>
                </tr>
			</thead>	
		    <c:forEach items="${sourcelead_timesheetSubmissionsList}" var="approval" varStatus="p">
                <tr>
                	<td><div class="time_appr_user">
                	<img  id="userImage" onerror="predefineProfileImage(this);" src="<c:url value='/displayUserImage/${approval.projectMembers.employee.user.id}'/>" alt="Profile Image" class="img-circle img-responsive">
						<span><b>${approval.projectMembers.employee.user.firstName}</b><b>Admin</b></span></div></div>
					</td>
                    <td><div> <fmt:formatDate type="date" value="${approval.startDate}" />  To
                             <fmt:formatDate type="date" value="${approval.endDate}" />
						 </div>
					</td>
                    <td><div><a    onclick="myTimesheet('${approval.id}','12-02-2016', '12-02-2016');"  id="timesheetPopup${approval.id}"> ${approval.totalHours}/40</a></div></td>
                    <td><div>${approval.submissionApprovalStatus}</div></td>
					<td>
                      <c:if test="${approval.submissionApprovalStatus == 'NEW'}">
                              <div><i class="fa fa-check" alt="Approve"  onclick="approveSubmission('${approval.id}');"></i>|<i class="fa fa-times" alt="Rejected" onclick="rejecteSubmission('${approval.id}');"></i></div>
                              <div id="rejectedPopup"></div>
                     </c:if> 
                    <c:if test="${approval.submissionApprovalStatus=='REJECTED'}">
                              <div><h3 class="reje_color">Rejected</h3>
								    <i class="glyphicon glyphicon-comment" onclick="rejectedMessage('rejectedMessage${approval.id}');"> 
										<strong id="rejectedMessage${approval.id}"><p>${approval.rejectedReason}</p></strong>
									</i>
							  </div>
                     </c:if>  
                      <c:if test="${approval.submissionApprovalStatus=='APPROVED'}">
                            <div><h3>Approved</h3></div>
                     </c:if>  
                    </td> 
                </tr>
            </c:forEach>
            
            			
            </tbody>
        </table>   --%>
       
	</div>
		
	</div>
	
</div>

		
		<!-- Project -->
		
</section>
      <!-- Content Area Ends--> 
      
      
<!-- dummy divs above -->          
             <div id="divadttTd1" style="display:none;" >   
                  <div> 
                   <select id="REPLACEROWIDsubmissionsubmissionIdtask1Id" name="REPLACEROWIDsubmissionsubmissionIdtask1Id" >
                                <c:forEach items="${sourcelead_tasksList}" var="task" varStatus="t">
                                   	<option value="${task.id}">${task.taskName}</option>
                                </c:forEach>
                  </select>
                  <input type="button" value="" onClick="removeTimeSheetRow('REPLACEROWID','submissionId')" class="addtaskfield removetaskfield"/>
                  </div>
                  
             </div>
             <div id="divadttTd2" style="display:none;">
				  <input type="text" id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay1Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay1Id" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
	                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment1Id');"></i>
	                  <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment1Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment1Id" ></textarea></span>
                  </div>
	     	</div>
                  
             <div id="divadttTd3" style="display:none;">
				  <input type="text" id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay2Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay2Id" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                      <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment2Id');"></i>
                  <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment2Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment2Id"></textarea></span></div>
             </div>
             <div id="divadttTd4" style="display:none;">
				  <input type="text" id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay3Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay3Id" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment3Id');"></i>
                  <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment3Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment3Id"></textarea></span></div>
             </div>
             <div id="divadttTd5" style="display:none;">
				  <input type="text" id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay4Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay4Id" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment4Id');"></i>
                  <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment4Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment4Id"></textarea></span></div>           
             </div>
             <div id="divadttTd6" style="display:none;">
				  <input type="text" id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay5Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay5Id" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment5Id');"></i>
                  <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment5Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment5Id"></textarea></span></div>  
	    	 </div>
             <div id="divadttTd7" style="display:none;">
				  <input type="text" id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay6Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay6Id" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment6Id');"></i>
                  <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment6Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment6Id"></textarea></span></div>
              </div>
             <div id="divadttTd8" style="display:none;">
				  <input type="text" id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay7Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay7Id" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment7Id');"></i>
                  <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment7Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment7Id"></textarea></span></div>            
	    	 </div>
             <div id="divadttTd9" style="display:none;">
				  <input type="text"  id="REPLACEROWIDsubmissionsubmissionIdtotal1Id" name="REPLACEROWIDsubmissionsubmissionIdtotal1Id" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment8Id');"></i>
                  <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment8Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment8Id"></textarea></span></div>  
             </div>
<!-- dummy divs above -->            

	
<!--   Timesheet approval popup   -->
	
<div class="time_appr_side_wrap">
   <div id="myId">
	<div class="time_appr_side_wrap_inn">
	<div class="proj_close"><i class="glyphicon glyphicon-share-alt"></i></div>
        <div class="timesheet_table">
        	<table id="timesheet" cellpadding="0" cellspacing="0">
                
            	<tbody>
                	<tr>
                    	<th>Projects</th>
                        <th> 
                         <table id="1" cellpadding="0" cellspacing="0">
                             <th>Tasks </th>
                        <th>Mon<i>15-06-2016</i></th>
                        <th>Mon<i>15-06-2016</i></th>
                        <th>Mon<i>15-06-2016</i></th>
                        <th>Mon<i>15-06-2016</i></th>
                        <th>Mon<i>15-06-2016</i></th>
                        <th>Mon<i>15-06-2016</i></th>
                        <th>Mon<i>15-06-2016</i></th>
                        <th>Total Hours</th>
                         </table>
                        </th> 
                        
                    </tr>
                    
                    <tr id="timesheetRow1">
                         <td class="pname"> <div>Sourcelead</div> </td>
                         <td>
                                 <input type="hidden" name="maxRowNumber1"  class="initiateTimesheetMax" id="maxRowNumber1"  value="1" />
                                 <table id="table1" cellpadding="0" cellspacing="0">
                                         <td>
                                              <div> 
                                                <select>
                                                  <option value="volvo">Task1</option>
                                                  <option value="saab">Task2</option>
                                                  <option value="mercedes">Task3</option>
                                                  <option value="audi">Task4</option>
                                                </select>
                                                <input type="button" value="" onClick="addTimeSheetRow('1')" class="addtaskfield"/>
                                              </div>
                                         </td> 
										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment com_active"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment com_active"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
 										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
     										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>                                         
                                 
                                 
                                 </table>
                       </td>   
                    </tr>
                    
					<tr id="timesheetRow2">
                         <td class="pname"> <div>Sourcelead</div> </td>
                         <td>
                                 <input type="hidden" name="maxRowNumber2" id="maxRowNumber2" class="initiateTimesheetMax"  value="1" /> 
                                 <table id="table2" cellpadding="0" cellspacing="0"> 
                                         <td>
                                              <div> 
                                                <select>
                                                  <option value="volvo">Task1</option>
                                                  <option value="saab">Task2</option>
                                                  <option value="mercedes">Task3</option>
                                                  <option value="audi">Task4</option>
                                                </select>
                                                <input type="button" value="" onClick="addTimeSheetRow('2')" class="addtaskfield"/>
                                              </div>
                                         </td> 
                                         										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
 										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
     										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>                  
                                         
                                 </table>
                         </td>
                    </tr>                    
                    
					<tr class="last_child">
                    	<td></td>
                        <td> 
                         <table id="1" cellpadding="0" cellspacing="0">
                             <td class="total_field">Total</td>
                        <td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
                                         <td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
                                         <td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
                                         <td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
                                        <td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
                                         <td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
                                         <td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
                                         <td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>                 
                                         
                                 </table>
                         </td> 
                        
                    </tr>  
                                                                                    
                </tbody>
            </table>
            
            
             <!--<div id="divadttTd1" style="display:none;"  > <div id="projectName">Sourcelead </div></div>-->
             <div id="divadttTd1" style="display:none;" > 
                  <div> 
                         <select>
                          <option value="volvo">Task1</option>
                          <option value="saab">Task2</option>
                          <option value="mercedes">Task3</option>
                          <option value="audi">Task4</option>
                        </select>
                        <input type="button" value="" onClick="addTimeSheetRow(REPLACEROWID)" class="addtaskfield removetaskfield"/>
                  </div>
                  
             </div>
             <div id="divadttTd2" style="display:none;">
				  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
	     	</div>
             <div id="divadttTd3" style="display:none;">
				  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
             </div>
             <div id="divadttTd4" style="display:none;">
				  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
             </div>
             <div id="divadttTd5" style="display:none;">
				  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>           
             </div>
             <div id="divadttTd6" style="display:none;">
				  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>  
	    	 </div>
             <div id="divadttTd7" style="display:none;">
				  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
              </div>
             <div id="divadttTd8" style="display:none;">
				  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>            
	    	 </div>
             <div id="divadttTd9" style="display:none;">
				  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" placeholder="" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>  
             </div>
                        
        </div>	
	</div>
	</div>
</div>	
	
	<!--   Timesheet approval popup   -->
	
	
	<!--   Timesheet approval popup   -->

<div class="mask_time"></div>	

<!-- <div class="time_appr_rejct_pop">
	<textarea placeholder="Enter comment here"></textarea>
	<a href="javascript:void(0)">Submit</a><a class="close_color" href="javascript:void(0)">Cancel</a>
</div> -->	
	
	<!--   Timesheet approval popup   -->

	<!--   Timesheet approval seript files   -->
<!-- sarath custom -->	
<script>
function myTimesheet(local,startDate,endDate){      
    alert("endDate"+endDate);
    $.ajax({
   	type: "POST",
    url: "http://localhost:8080/sourcelead/dynamicTimesheetpopUp?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+local+"&startDate="+startDate+"&endDate="+endDate, 	   
    success: function(data) {
              $("#myId").empty();
              $("#myId").append(data); 
              maskme();   
    }
    });
    $(".time_appr_side_wrap").addClass("time_appr_side_wrap_active");
    
	/* $(".timesheet_appr_table a").click(function(){ 
		$(".time_appr_side_wrap").addClass("time_appr_side_wrap_active");
	}); */
    /* $("body.proj_close").click(function(){  alert("hii");
		$(".time_appr_side_wrap").removeClass("time_appr_side_wrap_active");
	});	 */
} 
function removeTimesheetPopup(){
	 $(".time_appr_side_wrap").removeClass("time_appr_side_wrap_active");
}
</script>	

<script>
 
function addTimeSheetRow(submissionId) {
	          
		    var tbl = document.getElementById("table"+submissionId);
			var rowNum = $("#maxRowNumber"+submissionId).val();
			var maxRowNum = parseInt(rowNum);
			$("#maxRowNumber"+submissionId).val(maxRowNum + 1); 
			var row = tbl.insertRow(maxRowNum-1);
			row.setAttribute("id", "timesheetRow"+maxRowNum+"submission"+submissionId);
			
			  
			var cell0 = row.insertCell(0);
			var divadttTd0Str = document.getElementById("divadttTd1").innerHTML;
			divadttTd0Str = divadttTd0Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd0Str = divadttTd0Str.replace(/submissionId/g, (submissionId));
			cell0.setAttribute("class", "pname");  
			cell0.innerHTML = divadttTd0Str;
			
			
			
			 var cell1 = row.insertCell(1);
			var divadttTd1Str = document.getElementById("divadttTd2").innerHTML;
			divadttTd1Str = divadttTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
            divadttTd1Str = divadttTd1Str.replace(/submissionId/g, (submissionId));
			cell1.innerHTML = divadttTd1Str;
			
			
			var cell2 = row.insertCell(2);
			var divadttTd2Str = document.getElementById("divadttTd3").innerHTML;
			divadttTd2Str = divadttTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd2Str = divadttTd2Str.replace(/submissionId/g, (submissionId));
			cell2.innerHTML = divadttTd2Str;
			
			
			var cell3 = row.insertCell(3);
			var divadttTd3Str = document.getElementById("divadttTd4").innerHTML;
			divadttTd3Str = divadttTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd3Str = divadttTd3Str.replace(/submissionId/g, (submissionId));
			cell3.innerHTML = divadttTd3Str;
			
			var cell4= row.insertCell(4);
			var divadttTd4Str = document.getElementById("divadttTd5").innerHTML;
			divadttTd4Str = divadttTd4Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd4Str = divadttTd4Str.replace(/submissionId/g, (submissionId)); 
			cell4.innerHTML = divadttTd4Str;
			
			var cell5 = row.insertCell(5);
			var divadttTd5Str = document.getElementById("divadttTd6").innerHTML;
			divadttTd5Str = divadttTd5Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd5Str = divadttTd5Str.replace(/submissionId/g, (submissionId)); 
			cell5.innerHTML = divadttTd5Str;
			
			var cell6 = row.insertCell(6);
			var divadttTd6Str = document.getElementById("divadttTd7").innerHTML;
			divadttTd6Str = divadttTd6Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd6Str = divadttTd6Str.replace(/submissionId/g, (submissionId));
			cell6.innerHTML = divadttTd6Str;
			
			var cell7 = row.insertCell(7);
			var divadttTd7Str = document.getElementById("divadttTd8").innerHTML;
			divadttTd7Str = divadttTd7Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd7Str = divadttTd7Str.replace(/submissionId/g, (submissionId));
			cell7.innerHTML = divadttTd7Str;

			var cell8 = row.insertCell(8);
			var divadttTd8Str = document.getElementById("divadttTd9").innerHTML;
			divadttTd8Str = divadttTd8Str.replace(/REPLACEROWID/g, (maxRowNum));
                        divadttTd8Str = divadttTd8Str.replace(/submissionId/g,  (submissionId));
			cell8.innerHTML = divadttTd8Str;
			
			maskme();   
			
     }	
</script>

<script>
function maskme(){   
	$.mask.definitions['H'] = "[0-9]";
    $.mask.definitions['h'] = "[0-9]";
    $.mask.definitions['M'] = "[0-9]";
    $.mask.definitions['m'] = "[0-9]";
    $.mask.definitions['P'] = "[AaPp]";
    $.mask.definitions['p'] = "[Mm]";
    $(".inline_field").mask("Hh:Mm"); 
}
</script>

<script type="text/javascript">
		function bodyClick(){
		    $(".teskcomment").slideUp(200);   
		    $("html .timesheet_comm").click(function(event){
		        event.stopPropagation();
		    }); 
		 }
</script>
<script type="text/javascript">
	function timesheetComment(local){    
		$('.teskcomment').each(function() {
		    var currentId = this.id;
		    var localId = local;
		    if(localId.trim() == currentId.trim()){
	       	 		$("#"+local).slideToggle(200); 
	        }else{
	       	    $("#"+currentId).slideUp(200); 
	        }
		});
	} 
</script>


<script type="text/javascript">
function saveMyTimesheet(){
			    
               var frm = $('#timesheet_form').serializeFormJSON();
       		   var mydata = JSON.stringify(frm);
       		   mydata = mydata.replace(/[{}]/g, "");
       		
       		   var form = $('#timesheet_form')[0];
       		   var formData = new FormData(form); 
       		   
       		   var submissionIds = $("#submissionId").val();
       		    
       		   
       		   $.ajax({
	      	          type : "POST",
	          	      data: formData,
	          	      contentType : 'application/json; charset=utf-8',
	          	      enctype: 'multipart/form-data',
	      	          processData: false,
	      	          contentType:false,
	          	      dataType : 'json',
        	      	  url: "http://localhost:8080/sourcelead/saveTimesheet?${_csrf.parameterName}=${_csrf.token}&json="+mydata,		
        		      success :function(result) {
        		    	  
        		      }
        	      	  
        	    }); 
       		   
}
</script>

<script type="text/javascript">
(function ($) {
   $.fn.serializeFormJSON = function () {    

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
               }
               o[this.name].push(this.value || '');
           } else {
               o[this.name] = this.value || '';
           }
       });
       return o;
   };
})(jQuery);
</script>


<script>
function saveRejectSubmission(local){

         var msg = $("#reject"+local+"message").val(); 
            
         $(".mask_time").fadeOut(200);
         $("#reject"+local+"panel").slideUp(200);
         $.ajax({
         type: "POST",
         url: "http://localhost:8080/sourcelead/saveSubmissionApprovalStatus?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+local+"&rejectedReason="+msg+"&approvalStatus=REJECTED",       
         success: function(data) {
         			
         }
         });
	     
}

function cancelRejectSubmission(local){
		 $(".mask_time").fadeOut(200);
		 $("#reject"+local+"panel").slideUp(200);
}

function rejecteSubmission(local){
        
        var result = "";
        result = result +'<div class="time_appr_rejct_pop" id="reject'+local+'panel" >';
		result = result +'<textarea placeholder="Enter comment here" id="reject'+local+'message" ></textarea>';
		result = result +'<a href="#" onClick="saveRejectSubmission(\''+local+'\');" >Submit</a><a class="close_color" href="#" onClick="cancelRejectSubmission(\''+local+'\');">Cancel</a>';
		result = result +'</div>';
		 
		$("#rejectedPopup").append(result);
	    $(".mask_time").fadeIn(200); 
		$("#reject"+local+"panel").slideToggle(200);
}
function approveSubmission(local){
        var rejectedReason;
        alert("data"+local);
	    $.ajax({
	    type: "POST",
	    url: "http://localhost:8080/sourcelead/saveSubmissionApprovalStatus?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+local+"&approvalStatus=APPROVED&rejectedReason="+rejectedReason,        
	    success: function(data) {
	    
					alert("data");	              
	              
	    }
	    });
}
</script>
 
 	
<!-- this script is for reject msg pop up -->

<script>
function rejectedMessage(local){
    $("#"+local).slideToggle(200);
}

$(document).ready(function() {
    /* $(".timesheet_appr_table .glyphicon").click(function(){ 
		$(".timesheet_appr_table .glyphicon strong").slideToggle(200);
	}); */
    $("html").click(function() {
        $(".timesheet_appr_table .glyphicon strong").slideUp(200);
    });
    $(".timesheet_appr_table .glyphicon strong, .timesheet_appr_table .glyphicon").click(function(e) {
        e.stopPropagation();
    });	
});


// close popup for close
$(document).ready(function() {
    /* $(".timesheet_appr_table .fa-times").click(function(){
		$(".mask_time").fadeIn(200); 
		$(".time_appr_rejct_pop").slideToggle(200);
	}); */
    $(".time_appr_rejct_pop a").click(function(){
		$(".mask_time").fadeOut(200);
		$(".time_appr_rejct_pop").slideUp(200);
	});	
});

//  Timesheet approval side bar
/* $(document).ready(function() {
    $(".timesheet_appr_table a").click(function(){
		$(".time_appr_side_wrap").addClass("time_appr_side_wrap_active");
	});
    $(".proj_close").click(function(){
		$(".time_appr_side_wrap").removeClass("time_appr_side_wrap_active");
	});	
}); */

/* $(document).ready(function() {
    $("#appro-pop").click(function(){
		$(".approved_pop").slideToggle(200);
	});
    $("html").click(function() {
        $(".approved_pop").slideUp(200);
    });
    $(".approved_pop, #appro-pop").click(function(e) {
        e.stopPropagation();
    });	
}); */

$(document).ready(function() {
    $("#filter1ApprovePop").click(function(){
		$("#filter1ApprovedPop").slideToggle(200);
	});
    $("html").click(function() {
        $("#filter1ApprovedPop").slideUp(200);
    });
    $("#filter1ApprovedPop, #filter1ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
    
    
    $("#filter2ApprovePop").click(function(){
		$("#filter2ApprovedPop").slideToggle(200);
	});
    $("html").click(function() {
        $("#filter2ApprovedPop").slideUp(200);
    });
    $("#filter2ApprovedPop, #filter2ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
    
    
    $("#filter3ApprovePop").click(function(){
		$("#filter3ApprovedPop").slideToggle(200);
	});
    $("html").click(function() {
        $("#filter3ApprovedPop").slideUp(200);
    });
    $("#filter3ApprovedPop, #filter3ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
    
    
     $("#filter4ApprovePop").click(function(){
		$("#filter4ApprovedPop").slideToggle(200);
	});
    $("html").click(function() {
        $("#filter4ApprovedPop").slideUp(200);
    });
    $("#filter4ApprovedPop, #filter4ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
    
});


  


$(document).ready(function() {
    $("#appro-pop").click(function(){
		$(".approved_pop").slideToggle(200);
	});
    $("html").click(function() {
        $(".approved_pop").slideUp(200);
    });
    $(".approved_pop, #appro-pop").click(function(e) {
        e.stopPropagation();
    });	
});

//Export Popup
$(document).ready(function() {
    $("#export-pop").click(function(){
		$(".export_pop").slideToggle(200);
	});
    $("html").click(function() {
        $(".export_pop").slideUp(200);
    });
    $(".export_pop, #export-pop").click(function(e) {
        e.stopPropagation();
    });	
});	
	



</script>	



</body>
<jsp:include page="../masterFooter.jsp" />	