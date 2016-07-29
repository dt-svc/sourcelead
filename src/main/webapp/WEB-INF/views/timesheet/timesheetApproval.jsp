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
	  var sourceleadUrl ='${sourceleadUrl}';
	  $("#timesheetApprList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;'); 
	  $.ajax({
           type : "POST",
            url: sourceleadUrl+"/getArrovalSubmissions?${_csrf.parameterName}=${_csrf.token}",        
            success :function(data) {
                 if(data != null && data != 'undefined' && data != ""){
                	 $("#timesheetApprList").removeAttr("style");
                	 $("#timesheetApprList").empty();
                     $("#timesheetApprList").append(data); 
                 }else{
                	 $("#timesheetApprList").removeAttr("style");
  	    	         var result ="";
		    	     result = result+"   <div class='row'>"; 
					 result = result+"	<div class='col-lg-12'>"; 
					 result = result+"		<div class='content-area clearfix'>"; 
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No Timesheets Found</p>";
					 result = result+"			 </div>";
					 result = result+"		</div>"; 
					 result = result+"	</div>"; 
					 result = result+"  </div> "; 
					$("#timesheetApprList").empty();
				    $("#timesheetApprList").append(result); 
                 }
                 /* var json = JSON.parse(data); */
                 /* if(json.length > 0){
                	 
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
                         output = output+'<img  id="userImage" onerror="predefineProfileImage(this);" src="<c:url value="/displayUserImage/'+approval.userId+'"/>" alt="Profile Image" class="img-circle img-responsive">'; 
                         output = output+    '<span><b>'+approval.fullName+'</b></span></div></div>';
                         output = output+'</td>';
                         output = output+'<td><div> '+approval.startDateStr+'  To  ';
                         output = output+         ''+approval.endDateStr+'';
                         output = output+     '</div>';
                         output = output+ '</td>';
                         output = output+ '<td><div><a onclick="myTimesheet(\''+approval.employeeId+'\',\''+approval.startDateStr+'\',\''+approval.endDateStr+'\');"  id="timesheetPopup'+approval.Id+'"> '+approval.combinedTotalHours+'</a></div></td>';
                         output = output+'<td><div>'+approval.combinedStatus+'</div></td>';
                         output = output+'<td>';
                           if(approval.combinedStatus == 'NEW' || approval.combinedStatus == 'SUBMITTED FOR APPROVAL'){
                               output = output+'<div><i class="fa fa-check" alt="Approve"  onclick="approveSubmission(\''+approval.employeeId+'\',\''+approval.startDateStr+'\',\''+approval.endDateStr+'\');"></i>|<i class="fa fa-times" alt="Rejected" onclick="rejecteSubmission(\''+approval.Id+'\',\''+approval.employeeId+'\',\''+approval.startDateStr+'\',\''+approval.endDateStr+'\');"></i></div>';
                               output = output+'<div id="rejectedPopup"></div>';
                           }
                         if(approval.combinedStatus =='REJECTED'){
                             output = output+'<div><h3 class="reje_color">Rejected</h3>';
                             output = output+'<i class="glyphicon glyphicon-comment" onclick="rejectedMessage(\'rejectedMessage'+approval.Id+'\');">';
                             output = output+  '<strong id="rejectedMessage'+approval.Id+'"><p>'+approval.rejectedReason+'</p></strong>';
                             output = output+ '</i>';
                             output = output+'</div>';
                         }
                         if(approval.combinedStatus =='APPROVED'){
                             output = output+'<div><h3>Approved</h3></div>';
                         }  
                          output = output+'</td>' ;
                          output = output+ '</tr>';
                 }
               
                 output = output+' </tbody>';
                 output = output+' </table>';
                 
                 $("#timesheetApprList").empty();
                 $("#timesheetApprList").append(output);
              }else{
            	     $("#timesheetApprList").removeAttr("style");
  	    	         var result ="";
		    	     result = result+"   <div class='row'>"; 
					 result = result+"	<div class='col-lg-12'>"; 
					 result = result+"		<div class='content-area clearfix'>"; 
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No Timesheets Found</p>";
					 result = result+"			 </div>";
					 result = result+"		</div>"; 
					 result = result+"	</div>"; 
					 result = result+"  </div> "; 
					$("#timesheetApprList").empty();
				    $("#timesheetApprList").append(result); 
              }   */
          }
            
       }); 
  } 
</script>

<script type="text/javascript">
function dynamicTimesheet(navigation , startDate,memberId,submissionId){    
			var sourceleadUrl ='${sourceleadUrl}';  
			$("#dynamic").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
			$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/dynamicTimesheet?${_csrf.parameterName}=${_csrf.token}&navigation="+navigation+"&startDate="+startDate+"&memberId="+memberId, 	   
				    success: function(data) {
				       /*  $("row"+submissionId).empty();	  */ 
				        $("#row"+submissionId).empty();	 
				        $("#row"+submissionId).append(data);	
				        
				    }
		    });
}
</script>	

<body onclick="bodyClick();">
      <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Timesheet approval</h2>
          </div>
           <div class="pull-right brud-crum"><a href="dashboard">Home</a> » TimeSheet List</div>
        </section>
        
<div class="timesheet_out">
	<div class="timesheet_appr_wrap" >
		<div class="timesheet_appr_wrap">
		<!-- <div class="timesheet_head">
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
        </div> -->
		
		<div class="timesheet_appr_table">
				<div id="timesheetApprList">   </div>
	    </div>
	</div>
	
</div>
</div>
		
</section>
      
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
	   </div>
</div>
<div class="mask_time"></div>	

	 
<!-- this script is for dynamicTimesheetpopUp when click on count -->	
<script>
function myTimesheet(local,startDate,endDate){
	var sourceleadUrl ='${sourceleadUrl}';
    $.ajax({
   	type: "POST",
    url: sourceleadUrl+"/dynamicTimesheetpopUp?${_csrf.parameterName}=${_csrf.token}&employeeId="+local+"&startDate="+startDate+"&endDate="+endDate, 	   
    success: function(data) {
              $("#myId").empty();
              $("#myId").append(data); 
              maskme();   
              $("html").trigger('click');
    }
    });
    $(".time_appr_side_wrap").addClass("time_appr_side_wrap_active");
} 
function removeTimesheetPopup(){
	 $(".time_appr_side_wrap").removeClass("time_appr_side_wrap_active");
}
</script>	

<!-- this script is for delete  document -->	
<script type="text/javascript">
function deleteSubmissionDocument(submissionId,employeeId,startDate,endDate){  
    var sourceleadUrl ='${sourceleadUrl}';
    $.ajax({
          type : "POST",
      	  url: sourceleadUrl+"/deleteSubmissionDocument?${_csrf.parameterName}=${_csrf.token}&submissionId="+submissionId,		
	      success :function(result) {
	    		myTimesheet(employeeId,startDate,endDate)
	      }
      	  
    }); 
	   
}
</script>

<!-- this script is for addTimeSheetRow -->	
<script>
function addTimeSheetRow(submissionId,projectId) {
	          
		    var tbl = document.getElementById("table"+submissionId);
			var rowNum = $("#maxRowNumber"+submissionId).val();
			var maxRowNum = parseInt(rowNum);
			$("#maxRowNumber"+submissionId).val(maxRowNum + 1); 
			var row = tbl.insertRow(maxRowNum-1);
			row.setAttribute("id", "timesheetRow"+maxRowNum+"submission"+submissionId);
			
			var previousMaxRowNum =parseInt(rowNum-1);  
			var cell0 = row.insertCell(0);
			var divadttTd0Str = document.getElementById("divadttTd1").innerHTML;
			divadttTd0Str = divadttTd0Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd0Str = divadttTd0Str.replace(/submissionId/g, (submissionId));
			cell0.setAttribute("class", "pname");  
			cell0.innerHTML = divadttTd0Str;
			
			
			
			var cell1 = row.insertCell(1);
			var isCell1Disabled = $("#1submission"+submissionId+"timeSheetDay1Id").prop('disabled');
			if(isCell1Disabled){
				$("#divadttTd2 :input").attr('disabled','disabled');
				$('.timesheet_comm','#divadttTd2').find('i').attr('class','glyphicon');
				
			}
			else{
				$("#divadttTd2 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd2').find('i').attr('class','glyphicon glyphicon-comment');
			}
			var divadttTd1Str = document.getElementById("divadttTd2").innerHTML;
			divadttTd1Str = divadttTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
            divadttTd1Str = divadttTd1Str.replace(/submissionId/g, (submissionId));
			cell1.innerHTML = divadttTd1Str;
			
			
			var cell2 = row.insertCell(2);
			var isCell2Disabled = $("#1submission"+submissionId+"timeSheetDay2Id").prop('disabled');
			if(isCell2Disabled){
				$("#divadttTd3 :input").attr('disabled','disabled');
				$('.timesheet_comm','#divadttTd3').find('i').attr('class','glyphicon');
			}
			else{
				$("#divadttTd3 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd3').find('i').attr('class','glyphicon glyphicon-comment');
			}
			var divadttTd2Str = document.getElementById("divadttTd3").innerHTML;
			divadttTd2Str = divadttTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd2Str = divadttTd2Str.replace(/submissionId/g, (submissionId));
			cell2.innerHTML = divadttTd2Str;
			
			
			var cell3 = row.insertCell(3);
			var isCell3Disabled = $("#1submission"+submissionId+"timeSheetDay3Id").prop('disabled');
			if(isCell3Disabled){
				$("#divadttTd4 :input").attr('disabled','disabled');
				$('.timesheet_comm','#divadttTd4').find('i').attr('class','glyphicon');
			}
			else{
				$("#divadttTd4 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd4').find('i').attr('class','glyphicon glyphicon-comment');
			}
			var divadttTd3Str = document.getElementById("divadttTd4").innerHTML;
			divadttTd3Str = divadttTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd3Str = divadttTd3Str.replace(/submissionId/g, (submissionId));
			cell3.innerHTML = divadttTd3Str;
			
			var cell4= row.insertCell(4);
			var isCell4Disabled = $("#1submission"+submissionId+"timeSheetDay4Id").prop('disabled');
			if(isCell4Disabled){
				$("#divadttTd5 :input").attr("disabled", "disabled");
				$('.timesheet_comm','#divadttTd5').find('i').attr('class','glyphicon');
			}else{
				$("#divadttTd5 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd5').find('i').attr('class','glyphicon glyphicon-comment');
			}
			
			var divadttTd4Str = document.getElementById("divadttTd5").innerHTML;
			divadttTd4Str = divadttTd4Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd4Str = divadttTd4Str.replace(/submissionId/g, (submissionId)); 
			cell4.innerHTML = divadttTd4Str;
			
			var cell5 = row.insertCell(5);
			var isCell5Disabled = $("#1submission"+submissionId+"timeSheetDay5Id").prop('disabled');
			if(isCell5Disabled){
				$("#divadttTd6 :input").attr("disabled", "disabled");
				$('.timesheet_comm','#divadttTd6').find('i').attr('class','glyphicon');
			}
			else{
				$("#divadttTd6 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd6').find('i').attr('class','glyphicon glyphicon-comment');
			}
			var divadttTd5Str = document.getElementById("divadttTd6").innerHTML;
			divadttTd5Str = divadttTd5Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd5Str = divadttTd5Str.replace(/submissionId/g, (submissionId)); 
			cell5.innerHTML = divadttTd5Str;
			
			var cell6 = row.insertCell(6);
			var isCell6Disabled = $("#1submission"+submissionId+"timeSheetDay6Id").prop('disabled');
			if(isCell6Disabled){
				$("#divadttTd7 :input").attr("disabled", "disabled");
				$('.timesheet_comm','#divadttTd7').find('i').attr('class','glyphicon');
			}
			else{
				$("#divadttTd7 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd7').find('i').attr('class','glyphicon glyphicon-comment');
			}
			var divadttTd6Str = document.getElementById("divadttTd7").innerHTML;
			divadttTd6Str = divadttTd6Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd6Str = divadttTd6Str.replace(/submissionId/g, (submissionId));
			cell6.innerHTML = divadttTd6Str;
			
			var cell7 = row.insertCell(7);
			var isCell7Disabled = $("#1submission"+submissionId+"timeSheetDay7Id").prop('disabled');
			if(isCell7Disabled){
				$("#divadttTd8 :input").attr("disabled", "disabled");
				$('.timesheet_comm','#divadttTd8').find('i').attr('class','glyphicon');
			}else{
				$("#divadttTd8 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd8').find('i').attr('class','glyphicon glyphicon-comment');
			}
			var divadttTd7Str = document.getElementById("divadttTd8").innerHTML;
			divadttTd7Str = divadttTd7Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd7Str = divadttTd7Str.replace(/submissionId/g, (submissionId));
			cell7.innerHTML = divadttTd7Str;
			
			maskme();   
		    
			var projectTaskId = maxRowNum+"submission"+submissionId+"task1Id"; 
			getProjectTasks(projectTaskId,projectId); 
			
}	
 /* this script is for get project tasks   */	     
function getProjectTasks(projectTaskId,projectId){    
	   var sourceleadUrl ='${sourceleadUrl}';
	   $.ajax({
          type : "POST",
    	  url: sourceleadUrl+"/projectTasksById?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId,		
	      success :function(data) {
	    	    var json = JSON.parse(data);
	    	    var result = ""; 
	    	    for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		result = result + "<option value='" +opt.Id+ "'>"+opt.taskName+"</option>";
				}
	    	    $("#"+projectTaskId).empty();
	    	    $("#"+projectTaskId).append(result);
	      }
    	  
     }); 
	   
}
</script>

<!-- this script is for remove timesheet -->
<script type="text/javascript">	

function removeTimeSheetRow(rowId,submissionId) { 
	
			var rowNum = $("#maxRowNumber"+submissionId).val();
			var maxRowNum = parseInt(rowNum);
			/* $("#maxRowNumber"+submissionId).val(maxRowNum - 1);  */
			
			 document.getElementById("timesheetRow"+rowId+"submission"+submissionId).style.display = 'none';
			 document.getElementById("timesheetRow"+rowId+"submission"+submissionId).value= '';
			 var  rId = "timesheetRow"+rowId+"submission"+submissionId;
			  var r = document.getElementById("table"+submissionId).rows[rId];
	          var count = 2*(r.cells.length);
			    for(i=0;i<=count;i++){
			        r.deleteCell(0);
			    } 
		}
		
</script>	


<!-- this script is for masks for each entry  -->	  
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

<!-- this script is for comments for each entry -->	  
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

<!-- this script is for save timesheets -->	 
<script type="text/javascript">
function saveMyTimesheet(status){   
               var frm = $('#timesheet_form').serializeFormJSON();
               
               var sourceleadUrl ='${sourceleadUrl}';
               var startdate;
           	   var navigation;
               
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
        	      	  url: sourceleadUrl+"/saveTimesheet?${_csrf.parameterName}=${_csrf.token}&json="+mydata+"&status="+status,		
        		      success :function(result) {
        		    	  $(".time_appr_side_wrap").removeClass("time_appr_side_wrap_active");
        		    	  approvalList();
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

<!-- this script is for save Reject Submission and approve submission reason -->
<script>
function saveRejectSubmission(local,employeeId,startDate,endDate){

         var msg = $("#reject"+local+"message").val(); 
         var sourceleadUrl ='${sourceleadUrl}';
         
         $(".mask_time").fadeOut(200);
         $("#reject"+local+"panel").slideUp(200);
         $.ajax({
         type: "POST",
         url: sourceleadUrl+"/saveSubmissionApprovalStatus?${_csrf.parameterName}=${_csrf.token}&employeeId="+employeeId+"&startDate="+startDate+"&endDate="+endDate+"&rejectedReason="+msg+"&approvalStatus=REJECTED",       
         success: function(data) {    
        	    approvalList();
         }
         });
	     
}

function cancelRejectSubmission(local){
		 $(".mask_time").fadeOut(200);
		 $("#reject"+local+"panel").slideUp(200);
}

function rejecteSubmission(local,employeeId,startDate,endDate){  
        
        var result = "";
        result = result +'<div class="time_appr_rejct_pop" id="reject'+local+'panel" >';
		result = result +'<textarea placeholder="Enter comment here" id="reject'+local+'message" ></textarea>';
		result = result +'<a href="#" onClick="saveRejectSubmission(\''+local+'\',\''+employeeId+'\',\''+startDate+'\',\''+endDate+'\');" >Submit</a><a class="close_color" href="#" onClick="cancelRejectSubmission(\''+local+'\');">Cancel</a>';
		result = result +'</div>';
		 
		$("#rejectedPopup").append(result);
	    $(".mask_time").fadeIn(200); 
		$("#reject"+local+"panel").slideToggle(200);
}
function approveSubmission(employeeId,startDate,endDate){
        var rejectedReason;
        var sourceleadUrl ='${sourceleadUrl}';
	    $.ajax({
	    type: "POST",
	    url: sourceleadUrl+"/saveSubmissionApprovalStatus?${_csrf.parameterName}=${_csrf.token}&employeeId="+employeeId+"&startDate="+startDate+"&endDate="+endDate+"&approvalStatus=APPROVED&rejectedReason="+rejectedReason,        
	    success: function(data) {
	    	  approvalList();
	    }
	    });
}
</script>
 
 	
<!-- this script is for reject msg pop up -->

<script>
function rejectedMessage(local){ 
    $("#"+local).slideToggle(200);
    $(".timesheet_appr_table .glyphicon strong, .timesheet_appr_table .glyphicon").click(function(e) {
        e.stopPropagation();
    });	
    
}

$(document).ready(function() {
    $("html").click(function() {
        $(".timesheet_appr_table .glyphicon strong").slideUp(200);
    });
    $(".timesheet_appr_table .glyphicon strong, .timesheet_appr_table .glyphicon").click(function(e) {
        e.stopPropagation();
    });	
});


// close popup for close
$(document).ready(function() {
    $(".time_appr_rejct_pop a").click(function(){
		$(".mask_time").fadeOut(200);
		$(".time_appr_rejct_pop").slideUp(200);
	});	
});

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
	
<!-- this script is for clear timesheet -->
<script>
function clearTimesheet(){ 
	for (var i = 1; i < 8; i++){
	 for (var p = 1; p < 200; p++){  
		 for (var j = 1; j < 10; j++){
			    $("#"+j+"submission"+p+"timeSheetDay"+i+"Id").val("00:00");
		 }
     }
	}
}
</script>


  <script type="text/javascript">	
$(document).click(function(){    
	 var total;
	 for (var i = 1; i < 8; i++){
		 var bsum = 0;
		 var nbsum = 0;
		 var total = 0;
		 $('.timesheetrProject').each(function() {
		 var p = this.id;
		 for (var j = 1; j < 50; j++){
			    var btemp = $("#"+j+"submission"+p+"timeSheetDay"+i+"Id").val();
			    if (btemp !== undefined && btemp !=='####' && btemp !=='') {
			    	 var hours = btemp;
			    	 var split = hours.split(':');
			    	 var first = split[0];
			    	 var second = split[1];
					 
					 var minute = first * 60;
					 var minute1 = second;
					 var finalmin = parseInt(minute) + parseInt(minute1);
			    	 
				     bsum = parseFloat(bsum) + parseInt(finalmin);
				}
			    
		 }
		 
	    var t = bsum; 
		var hours = Math.floor(t / 60); 
		var minutes = t % 60;
		
		 if (minutes < 10) {
			minutes = '0' + minutes;
		 }
		 if (hours < 10) {
			hours = '0' + hours;
		 }
			 
		total = hours +":"+ minutes;
	 });  
	 $("#rowTotal"+i+"Id").val(total);
}	
	 
grandTotal();	 
});

function grandTotal(){
	     var total;
	     var bsum = 0;
	     for(var i=1 ;i<8;i++){
	    	 var hours = $("#rowTotal"+i+"Id").val();

	    	 var split = hours.split(':');
	    	 var first = split[0];
	    	 var second = split[1];
			 
			 var minute = first * 60;
			 var minute1 = second;
			 var finalmin = parseInt(minute) + parseInt(minute1);
	    	 
		     bsum = parseFloat(bsum) + parseInt(finalmin);
	    	 
	     }
	     var t = bsum; 
		 var hours = Math.floor(t / 60); 
		 var minutes = t % 60;
		 if (minutes < 10) {
			minutes = '0' + minutes;
		 }
		 if (hours < 10) {
			hours = '0' + hours;
		 }
			 
		 total = hours +":"+ minutes;
		 $("#grandTotal").val(total);
	     
}

</script>

<script>
function checkHours(){
	 var result = "";		
	 var total;
	 for (var i = 1; i < 8; i++){
		 var bsum = 0;
		 var nbsum = 0;
		 var total = 0;
		 $('.timesheetrProject').each(function() {
		 var p = this.id;
		 for (var j = 1; j < 10; j++){
			    var btemp = $("#"+j+"submission"+p+"timeSheetDay"+i+"Id").val();
			    if (btemp !== undefined && btemp !=='####' && btemp !=='') {
			    	 var hours = btemp;
			    	 var split = hours.split(':');
			    	 var first = split[0];
			    	 var second = split[1];
					 
					 var minute = first * 60;
					 var minute1 = second;
					 var finalmin = parseInt(minute) + parseInt(minute1);
			    	 
				     bsum = parseFloat(bsum) + parseInt(finalmin);
				}
			    
		 }
	    var t = bsum; 
		var hours = Math.floor(t / 60); 
		var minutes = t % 60;
		total = hours +":"+ minutes;
     });  
	 var time = total.split(':');
	 if(!isNaN(time[0])){
	 if(time.length === 2 && parseInt(time[0],10)>=0 && parseInt(time[0],10)<=23  && parseInt(time[1],10)>=0  && parseInt(time[1],10)<=59){
	    	 
	 }else{
	    	if(i == '1'){
	    		result = result+"MonDay ,";
	    	}
	    	if(i == '2'){  
	    		result = result+"TuesDay ,";
	    	}
	    	if(i == '3'){  
	    		result = result+"Wednesday ,";
	    	}
	    	if(i == '4'){  
	    		result = result+"Thursday ,";
	    	}
	    	if(i == '5'){
	    		result = result+"Friday ,";
	    	}
	    	if(i == '6'){
	    		result = result+"Saturday ,";
	    	}
	    	if(i == '7'){
	    		result = result+"Sunday ,";
	    	}
	    	
	 }
	 }
}   
	
	if(result != "" && result != null && result != 'undefined'){   
		var outPut = "<p>"+result+" hours should not be more than 24<b>X</b></p>";
		$("#timeSheetErrorMsg").empty();
		$("#timeSheetErrorMsg").append(outPut);
		var error = "<input  type='hidden' name='timesheetHiddenError' id='timesheetHiddenError'  data-validation='required' />";
		$("#hideError").empty();
		$("#hideError").append(error);
	 }else{
		$("#timeSheetErrorMsg").empty();
		$("#hideError").empty();
	} 
	
	$(".time_pop b").click(function(){
	    $("#timeSheetErrorMsg").empty();
	}); 
	
}
</script> 

<script>
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

</script>

<div class="time_pop time_pop_a" id="timeSheetErrorMsg">
</div> 

</body>
<jsp:include page="../masterFooter.jsp" />	