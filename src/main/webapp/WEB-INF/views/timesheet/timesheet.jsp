<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

    <link href="resources/css/jquery-ui.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://code.jquery.com/ui/1.11.3/jquery-ui.js"></script>
    <script src="resources/js/bootstrap.min.js"></script>
    <script src="resources/js/jquery-fun.js"></script>
	<!-- <link rel="stylesheet" href="resources/css/bootstrap.min.css"> -->
	<link rel="stylesheet" href="resources/css/timesheet.css" type="text/css" >
	<link rel="stylesheet" href="resources/css/login-styles.css" type="text/css" >
  
    <script src="resources/js/jquery.maskedinput-1.3.min.js"></script>


<script>
$(document).ready(function() {
	var startdate;
	var navigation;
	dynamicTimesheet(navigation,startdate);  
});
</script>

<script type="text/javascript">
function dynamicTimesheet(navigation , startDate){    
			var sourceleadUrl ='${sourceleadUrl}';
	        $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/newTimesheet?${_csrf.parameterName}=${_csrf.token}&navigation="+navigation+"&startDate="+startDate, 	   
				    success: function(data) {
				               $("#dynamic").empty();
						       $("#dynamic").append(data);
						       maskme();  
						       $("html").trigger('click');
				    }
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
	
	<!--  <script type="text/javascript">
		$(document).ready(function() {  
		    $("html").click(function() {    alert("one");    
		               $(".teskcomment").slideUp(200);   
		    });
		    $("html .timesheet_comm").click(function(event){   alert("two");
		        event.stopPropagation();
		    }); 
		});	 
		
    </script>	
 -->
 
<script type="text/javascript">
		function bodyClick(){
		    $(".teskcomment").slideUp(200);   
		    $("html .timesheet_comm").click(function(event){
		        event.stopPropagation();
		    }); 
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

    <script>
	$(document).ready(function() {  
		maskme();    
	 });
    </script>
 
<!--   $("html").click(function() { 
			          /*  $(".teskcomment").slideUp(200);  */
			           $('.teskcomment').each(function() {
					         alert( this.id );
					    });
			           
			    });
			        -->
<!-- 
     $(".timesheet_comm i").click(function(){
		$("#teskcomment").slideToggle(200);
	}); 
    $("html").click(function() {  alert("hiii");
         $(".teskcomment").slideUp(200); 
    });
    $(".timesheet_comm i, #teskcomment").click(function(e) {
        e.stopPropagation();
    });	 -->
    
    
 

<body  onclick="bodyClick();">

<section class="content-wraper">
 <form id="timesheet_form" name="timesheet_form" enctype="multipart/form-data" method="post">
     <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Timesheets</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; General Form &raquo; Form</div>
    </section>

 
<div class="timesheet_out">   
<!--  <input class="timepicker" type="text" size="10" placeholder="hh:mm" />
<input class="timepicker" type="text" size="10" placeholder="hh:mm" />  -->
	<div class="timesheet_wrap">
    	<!-- <div class="timesheet_head">
        	<ul>
            	<li><i class="glyphicon glyphicon-print"></i></li>
                <li><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop"></i>|
                	<div class="export_pop">
                    	<ul>
                        	<li>PDF<i class="fa fa-file-pdf-o"></i></li>
                            <li>Excel<i class="fa fa-file-excel-o"></i></li>
                            <li>Word<i class="fa fa-file-word-o"></i></li>
                        </ul>
                    </div>
                </li>
            	<li><i class="glyphicon glyphicon-chevron-left" onclick="dynamicTimesheet();"></i><span class="res_date"><i class="fa fa-calendar"></i>15/06 - 21/06</span>
                <i class="glyphicon glyphicon-chevron-right"></i><abbr>|</abbr></li>                
                <div class="clearfix"></div>
            </ul>
            <div class="clearfix"></div>
        </div> -->
         <div id="dynamic"> 
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
                    <c:forEach items="${sourcelead_timesheetSubmissionsList}" var="submission" varStatus="p">
                   <%--      ${submission.id}   hai    ${submission.projectMembers.id}  hiii  ${submission.projectMembers.projects.id}
                    </c:forEach>
                   <c:forEach items="${sourcelead_projectsList}" var="project" varStatus="p">  --%>
                    <input type="hidden" name="submissionId"  value="${submission.id}"/>
                    <tr id="timesheetRow1">
                         <td class="pname"> <div>${submission.projectMembers.projects.projectName}</div> </td>
                         <td>
                               <input type="hidden" name="maxRowNumber${submission.id}"  class="initiateTimesheetMax" id="maxRowNumber${submission.id}"  value="2" />
                               <table id="table${submission.id}" cellpadding="0" cellspacing="0">
                               <tr id="timesheetRow1submission${submission.id}">
                                         <td>
                                              <div> 
                                               <select id="1submission${submission.id}task1Id" name="1submission${submission.id}task1Id" >
		                                               <c:forEach items="${submission.projectTasksList}" var="task" varStatus="t">
		                                                  	<option value="${task.id}">${task.taskName}</option>
		                                               </c:forEach>
                                               </select>     
                                                <input type="button" value="" onClick="addTimeSheetRow(${submission.id})" class="addtaskfield"/>
                                              </div>
                                         </td> 
										<td> <div>    
                  <input type="text" onkeypress="return isNumber(event)" id="1submission${submission.id}timeSheetDay1Id" name="1submission${submission.id}timeSheetDay1Id" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment com_active" onclick="timesheetComment('1submission${submission.id}comment1Id');"></i>
                  <span class="teskcomment" id="1submission${submission.id}comment1Id"><textarea name="1submission${submission.id}comment1Id" ></textarea></span></div>
                  </div> </td>
                  
				  <td> <div>
                  <input type="text" onkeypress="return isNumber(event)" id="1submission${submission.id}timeSheetDay2Id" name="1submission${submission.id}timeSheetDay2Id" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment com_active" onclick="timesheetComment('1submission${submission.id}comment2Id');"></i>
                  <span class="teskcomment" id="1submission${submission.id}comment2Id"><textarea name="1submission${submission.id}comment2Id"></textarea></span></div>
                  </div> </td>
                  
 			      <td> <div>
                  <input type="text" onkeypress="return isNumber(event)" id="1submission${submission.id}timeSheetDay3Id" name="1submission${submission.id}timeSheetDay3Id" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('1submission${submission.id}comment3Id');"></i>
                  <span class="teskcomment" id="1submission${submission.id}comment3Id"><textarea name="1submission${submission.id}comment3Id"></textarea></span></div>
                  </div> 
                  </td>
                  
     			  <td> <div>
                  <input type="text" onkeypress="return isNumber(event)" id="1submission${submission.id}timeSheetDay4Id" name="1submission${submission.id}timeSheetDay4Id" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('1submission${submission.id}comment4Id');"></i>
                  <span class="teskcomment" id="1submission${submission.id}comment4Id" ><textarea name="1submission${submission.id}comment4Id"></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" onkeypress="return isNumber(event)" id="1submission${submission.id}timeSheetDay5Id" name="1submission${submission.id}timeSheetDay5Id" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('1submission${submission.id}comment5Id');" ></i>
                  <span class="teskcomment" id="1submission${submission.id}comment5Id"><textarea name="1submission${submission.id}comment5Id"></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" onkeypress="return isNumber(event)" id="1submission${submission.id}timeSheetDay6Id" name="1submission${submission.id}timeSheetDay6Id" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('1submission${submission.id}comment6Id');"></i>
                  <span class="teskcomment" id="1submission${submission.id}comment6Id" ><textarea name="1submission${submission.id}comment6Id"></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" onkeypress="return isNumber(event)" id="1submission${submission.id}timeSheetDay7Id" name="1submission${submission.id}timeSheetDay7Id" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('1submission${submission.id}comment7Id');"></i>
                  <span class="teskcomment" id="1submission${submission.id}comment7Id" ><textarea name="1submission${submission.id}comment7Id" ></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" onkeypress="return isNumber(event)" id="1submission${submission.id}total1Id" name="1submission${submission.id}total1Id" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('1submission${submission.id}comment8Id');"></i>
                  <span class="teskcomment" id="1submission${submission.id}comment8Id"><textarea name="1submission${submission.id}comment8Id"></textarea></span></div>
                  </div> </td>                                         
                  </tr>              
                                 </table>
                  </td>   
                    </tr>
                    </c:forEach>   
					<tr class="last_child">
                    	<td></td>
                        <td> 
                         <table id="1" cellpadding="0" cellspacing="0">
                             <td class="total_field">Total</td>
                  <td> <div>
                  <input type="text" id="rowTotal1Id" name="rowTotal1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
                  <td> <div>
                  <input type="text" id="rowTotal2Id" name="rowTotal2Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
                                         <td> <div>
                  <input type="text" id="rowTotal3Id" name="rowTotal3Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
                                         <td> <div>
                  <input type="text" id="rowTotal4Id" name="rowTotal4Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
                                        <td> <div>
                  <input type="text" id="rowTotal5Id" name="rowTotal5Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
                                         <td> <div>
                  <input type="text" id="rowTotal6Id" name="rowTotal6Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
                                         <td> <div>
                  <input type="text" id="rowTotal7Id" name="rowTotal7Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
                                         <td> <div>
                  <input type="text" id="rowTotal8Id" name="rowTotal8Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>                 
                                         
                                 </table>
                         </td> 
                        
                    </tr>  
                                                                                    
                </tbody>
            </table>
          
             <!--<div id="divadttTd1" style="display:none;"  > <div id="projectName">Sourcelead </div></div>-->
        </div>
        <div class="timesheet_bot">
    	<div class="timesheet_week_comm">
        	<textarea name="weekComment" placeholder="comments here..."></textarea>
            <span>
            	<input type="file" name="docFile" ><label><i class="glyphicon glyphicon-save"></i>Choose file</label>
            </span>
            <div class="clearfix"></div>
        </div>
<!--    	<div class="timesheet_sub_other">
        	Submit to others <a href="javascript:void(0)"><i class="fa fa-envelope"></i> Email</a>
            <a href="javascript:void(0)"><i class="fa fa-users"></i> Users</a>
        </div>-->
    	<div class="timesheet_button">
    	    <input type="submit" name="Draft" vaue="Draft" class="time_btn time_dra" >
        	<!-- <input type="button" value="Draft" class="time_btn time_dra" /> -->
            <input type="button" value="Submit" class="time_btn" />
            <input type="button" value="Clear" class="time_btn time_cancel" />
        </div>
    	<div class="clearfix"></div>
     </div>        
     </div>   
        
              <div id="divadttTd1" style="display:none;" >   
                  <div> 
                   <select id="REPLACEROWIDsubmissionsubmissionIdtask1Id" name="REPLACEROWIDsubmissionsubmissionIdtask1Id" >
                                <c:forEach items="${sourcelead_tasksList}" var="task" varStatus="t">
                                   	<option value="${task.id}">${task.taskName}</option>
                                </c:forEach>
                  </select>
                                               
                       <!--   <select>
                          <option value="volvo">Task1</option>
                          <option value="saab">Task2</option>
                          <option value="mercedes">Task3</option>
                          <option value="audi">Task4</option>
                        </select> -->
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
             <!-- <div id="divadttTd9" style="display:none;">
				  <input type="text"  id="REPLACEROWIDsubmissionsubmissionIdtotal1Id" name="REPLACEROWIDsubmissionsubmissionIdtotal1Id" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment8Id');"></i>
                  <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment8Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment8Id"></textarea></span></div>  
             </div> -->
      
      
<!--   Responsive  --> 
<div class="timesheet_table_res">
<div id="accordion">
    <h2>Product one</h2>
    <div class="time_cont_res">
    	<table cellpadding="0" cellspacing="0">
        	<tbody>
            	<tr>
                	<th>Task 1</th>
                    <th><div><input type="button" value="" onClick="addTimeSheetRow('2')"/><span><i class="fa fa-plus"></i>Add</span></div></th>
                </tr>
                <tr>
                	<td>Mon 15-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment com_active"></i>
                          <span id="teskcomment_res"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Tue 16-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Wed 17-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Thu 18-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Fri 19-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Sat 20-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Sun 21-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Total hours </td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>                                                                                                                
            </tbody>
        </table>
    </div>
    <h2>Product two</h2>
	<div class="time_cont_res">
    	<table cellpadding="0" cellspacing="0">
        	<tbody>
            	<tr>
                	<th>Task 1</th>
                    <th><div><input type="button" value="" onClick="addTimeSheetRow('2')"/><span><i class="fa fa-plus"></i>Add</span></div></th>
                </tr>
                <tr>
                	<td>Mon 15-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Tue 16-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Wed 17-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Thu 18-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Fri 19-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Sat 20-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Sun 21-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Total hours </td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>                                                                                                                
            </tbody>
        </table>
    </div>
    <h2>Product three</h2>
	<div class="time_cont_res">
    	<table cellpadding="0" cellspacing="0">
        	<tbody>
            	<tr>
                	<th>Task 1</th>
                    <th><div><input type="button" value="" onClick="addTimeSheetRow('2')"/><span><i class="fa fa-plus"></i>Add</span></div></th>
                </tr>
                <tr>
                	<td>Mon 15-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Tue 16-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Wed 17-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Thu 18-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Fri 19-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Sat 20-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Sun 21-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Total hours </td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>                                                                                                                
            </tbody>
        </table>
    </div>
</div>

</div>
        
 <!--   Responsive  --> 
 <div class="res_total_hours">
	<i></i>
	<p>Total hours</p>
    <h2>45:30</h2>
    <span>This Week</span>
</div>  

    </div>
    
</div>  

 </form>   
 </section>
</body>

<jsp:include page="../masterFooter.jsp" />

<script>

$(document).ready(function() {
    $(".initiateTimesheetMax").each(function(){
		        this.val("1");
    });
	
});	
</script> 
<script>
 
function callInlineEdit(local){     
       var id = local.id;
	   $("#"+id).addClass("inline_field");
       $("#"+id).editInPlace({
		
		saving_animation_color: "#ECF2F8",
		callback: function(idOfEditor, enteredText, orinalHTMLContent, settingsParams, animationCallbacks) {
			return enteredText;
		}
	});
}
</script>


<script>
 
function addTimeSheetRow(submissionId,projectId) {
	          
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

			/* var cell8 = row.insertCell(8);
			var divadttTd8Str = document.getElementById("divadttTd9").innerHTML;
			divadttTd8Str = divadttTd8Str.replace(/REPLACEROWID/g, (maxRowNum));
            divadttTd8Str = divadttTd8Str.replace(/submissionId/g,  (submissionId));
			cell8.innerHTML = divadttTd8Str; */
			
			
			maskme();   
		    
			var projectTaskId = maxRowNum+"submission"+submissionId+"task1Id"; 
			getProjectTasks(projectTaskId,projectId); 
		  
			
		}	
	 
        
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
        		    	  dynamicTimesheet();
        		      }
        	      	  
        	    }); 
       	  /*   }); */
}    
   </script>

   
   <script type="text/javascript">	
/* for total hours */
$(document).click(function(){
		var submissionId = 1;
		var total;
		 for (var p = 1; p < 8; p++){
		 for (var i = 1; i < 8; i++){  
			 var bsum = 0;
			 var nbsum = 0;
			 var total = 0;
			 for (var j = 1; j < 8; j++){
				    var btemp = $("#"+i+"submission"+p+"timeSheetDay"+j+"Id").val();
				    /* var btemp = $("#"+j+"day"+i+"Hours").val(); */
				    if (btemp !== undefined && btemp !=='####' && btemp !=='') {
				    	/* bsum = parseFloat(bsum) + parseFloat(btemp); */
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
			
		    $("#"+i+"submission"+p+"total1Id").val(total);
	 }  
}	
		
		
});



$(document).click(function(){ 
	var total;
	 for (var i = 1; i < 8; i++){
		 var bsum = 0;
		 var nbsum = 0;
		 var total = 0;
	 for (var p = 1; p < 8; p++){  
		 for (var j = 1; j < 8; j++){
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
 }  
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
	 total = hours +":"+ minutes;
	 $("#grandTotal").val(total);
    
}

</script>


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

<!-- dynamic timesheet -->
<!-- <script>	
function dynamicTimesheet(){    
	   $.ajax({
	          type : "GET",
	      	  url: "http://localhost:8080/sourcelead/dynamicTimesheet",		
		      success :function(data) {
		    	  $("#dynamic").empty();
		    	  $("#dynamic").append(data);
		    	  maskme();   
		      }
	    }); 
}
</script> -->	


<!-- <script>	
function dateFormat(){    
	   $.ajax({
	          type : "GET",
	      	  url: "http://localhost:8080/sourcelead/dateFormat",		
		      success :function(data) {
		    	  
		      }
	    }); 
}
</script>	 -->
		