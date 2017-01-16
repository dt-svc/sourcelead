<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<!-- this is for mask -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.11.3/jquery-ui.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/jquery-fun.js"></script>
<link rel="stylesheet" href="resources/css/timesheet.css" type="text/css" >
<script src="resources/js/jquery.maskedinput-1.3.min.js"></script>
<script src="resources/js/formvalidator.js"></script>

<script>
// project side bar tabs
$(document).ready(function() {
	newTimesheetDummy();
});
</script>  


<script>
	function newTimesheetDummy(){ 
	               var sourceleadUrl ='${sourceleadUrl}';
	        	   $.ajax({
		      	          type : "POST",
		          	      dataType : 'json',
	        	      	  url: sourceleadUrl+"/newTimesheetDummy?${_csrf.parameterName}=${_csrf.token}",		
	        		      success :function(result) {
	        		    	  	$("#timesheetDynamicDiv").append(result);
	        		    	  	maskme();
	        		    	  	
	        		    	  	$("#tab-1").show()
	        		    	    $(".project-tabs-menu a").click(function(event) {
	        		    	        event.preventDefault();
	        		    	        $(this).parent().addClass("current");
	        		    	        $(this).parent().siblings().removeClass("current");
	        		    	        var tab = $(this).attr("href");
	        		    	        $(".project_tabs_inn").not(tab).css("display", "none");
	        		    	        $(tab).fadeIn();
	        		    	    });
	        		      }
	        	      	  
	        	    }); 
	}
</script>


<!-- this script is for add row functionality at Timesheet screen -->
<script>
function addTimeSheetRow(projectId) {
	          
		    var tbl = document.getElementById("timesheet");
			var rowNum = $("#maxRowNumber").val();
			var maxRowNum = parseInt(rowNum);
			$("#maxRowNumber").val(maxRowNum + 1); 
			var row = tbl.insertRow(maxRowNum+1);
			row.setAttribute("id", "timesheetRow"+maxRowNum);
			
			  
		    var previousMaxRowNum =parseInt(rowNum-1);
			var cell0 = row.insertCell(0);
			var divadttTd0Str = document.getElementById("divadttTd1").innerHTML;
			divadttTd0Str = divadttTd0Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd0Str = divadttTd0Str.replace(/projectId/g, (projectId)); 
			cell0.setAttribute("class", "pname");  
			cell0.innerHTML = divadttTd0Str;
			
		    var cell1 = row.insertCell(1);
			/* var isCell1Disabled = $("#1submission"+submissionId+"timeSheetDay1Id").prop('disabled');
			if(isCell1Disabled){
				$("#divadttTd2 :input").attr('disabled','disabled');
				$('.timesheet_comm','#divadttTd2').find('i').attr('class','glyphicon');
				
			}
			else{
				$("#divadttTd2 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd2').find('i').attr('class','glyphicon glyphicon-comment');
			} */
			var divadttTd1Str = document.getElementById("divadttTd2").innerHTML;
			divadttTd1Str = divadttTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
            divadttTd1Str = divadttTd1Str.replace(/projectId/g, (projectId));
			cell1.innerHTML = divadttTd1Str;
			
			
			var cell2 = row.insertCell(2);
			/* var isCell2Disabled = $("#1submission"+submissionId+"timeSheetDay2Id").prop('disabled');
			if(isCell2Disabled){
				$("#divadttTd3 :input").attr('disabled','disabled');
				$('.timesheet_comm','#divadttTd3').find('i').attr('class','glyphicon');
			}
			else{
				$("#divadttTd3 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd3').find('i').attr('class','glyphicon glyphicon-comment');
			} */
			var divadttTd2Str = document.getElementById("divadttTd3").innerHTML;
			divadttTd2Str = divadttTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd2Str = divadttTd2Str.replace(/projectId/g, (projectId));
			cell2.innerHTML = divadttTd2Str;
			
			
			var cell3 = row.insertCell(3);
			/* var isCell3Disabled = $("#1submission"+submissionId+"timeSheetDay3Id").prop('disabled');
			if(isCell3Disabled){
				$("#divadttTd4 :input").attr('disabled','disabled');
				$('.timesheet_comm','#divadttTd4').find('i').attr('class','glyphicon');
			}
			else{
				$("#divadttTd4 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd4').find('i').attr('class','glyphicon glyphicon-comment');
			} */
			var divadttTd3Str = document.getElementById("divadttTd4").innerHTML;
			divadttTd3Str = divadttTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd3Str = divadttTd3Str.replace(/projectId/g, (projectId));
			cell3.innerHTML = divadttTd3Str;
			
			
			var cell4= row.insertCell(4);
			/* var isCell4Disabled = $("#1submission"+submissionId+"timeSheetDay4Id").prop('disabled');
			if(isCell4Disabled){
				$("#divadttTd5 :input").attr("disabled", "disabled");
				$('.timesheet_comm','#divadttTd5').find('i').attr('class','glyphicon');
			}else{
				$("#divadttTd5 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd5').find('i').attr('class','glyphicon glyphicon-comment');
			} */
			var divadttTd4Str = document.getElementById("divadttTd5").innerHTML;
			divadttTd4Str = divadttTd4Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd4Str = divadttTd4Str.replace(/projectId/g, (projectId)); 
			cell4.innerHTML = divadttTd4Str;
			
			var cell5 = row.insertCell(5);
			/* var isCell5Disabled = $("#1submission"+submissionId+"timeSheetDay5Id").prop('disabled'); */
			/* if(isCell5Disabled){
				$("#divadttTd6 :input").attr("disabled", "disabled");
				$('.timesheet_comm','#divadttTd6').find('i').attr('class','glyphicon');
			}
			else{
				$("#divadttTd6 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd6').find('i').attr('class','glyphicon glyphicon-comment');
			}*/
			var divadttTd5Str = document.getElementById("divadttTd6").innerHTML; 
			divadttTd5Str = divadttTd5Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd5Str = divadttTd5Str.replace(/projectId/g, (projectId)); 
			cell5.innerHTML = divadttTd5Str;
			
			var cell6 = row.insertCell(6);
			/* var isCell6Disabled = $("#1submission"+submissionId+"timeSheetDay6Id").prop('disabled');
			if(isCell6Disabled){
				$("#divadttTd7 :input").attr("disabled", "disabled");
				$('.timesheet_comm','#divadttTd7').find('i').attr('class','glyphicon');
			}
			else{
				$("#divadttTd7 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd7').find('i').attr('class','glyphicon glyphicon-comment');
			} */
			var divadttTd6Str = document.getElementById("divadttTd7").innerHTML;
			divadttTd6Str = divadttTd6Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd6Str = divadttTd6Str.replace(/projectId/g, (projectId));
			cell6.innerHTML = divadttTd6Str;
			
			var cell7 = row.insertCell(7);
			/* var isCell7Disabled = $("#1submission"+submissionId+"timeSheetDay7Id").prop('disabled');
			if(isCell7Disabled){
				$("#divadttTd8 :input").attr("disabled", "disabled");
				$('.timesheet_comm','#divadttTd8').find('i').attr('class','glyphicon');
			}else{
				$("#divadttTd8 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd8').find('i').attr('class','glyphicon glyphicon-comment');
			} */
			var divadttTd7Str = document.getElementById("divadttTd8").innerHTML;
			divadttTd7Str = divadttTd7Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd7Str = divadttTd7Str.replace(/projectId/g, (projectId));
			cell7.innerHTML = divadttTd7Str;
			
		    maskme();
		    
		    getProjectTasks(maxRowNum,projectId); 
			
		  /*   
			var projectTaskId = maxRowNum+"submission"+submissionId+"task1Id"; */
			
			/* getProjectTasks(projectTaskId,projectId);  */ 
		  
			
		}	
	 
        
       /*  function disableInput(divId){
        	$("#"+divId+" :input").attr("disabled", "disabled");
        } */
        
       //get project tasks 
	   function getProjectTasks(maxRowNum,projectId){ 
		   var sourceleadUrl ='${sourceleadUrl}';
		   $("#"+projectId).empty();
		   $.ajax({
   	          type : "POST",
 	      	  url: sourceleadUrl+"/projectTasksById?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&avilableProjectTaskIds=null",		
 		      success :function(data) {
 		    	        	alert("data"+data);
 		    	   			var json = JSON.parse(data);
	 			   		   	$("#"+maxRowNum+"taskId").empty();
	 			   		   	var result = "";
	 			   		    result = result + "<option value=''>--select option--</option>";
	  		    	    	for (var i=0; i<json.length; i++)
	  					 	{
	  			        		var opt = json[i];
	  			        		result = result + "<option value='" +opt.Id+ "'>"+opt.taskName+"</option>";
	  						}
	  		    	    	alert("maxRowNum"+maxRowNum);
 				    	 	$("#"+maxRowNum+"taskId").append(result);
 		      }
 	      	  
 	       }); 
		   
	   } 

	 
</script>

<!-- this script is for mask for each entity  -->	
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

<!-- this script is for add row functionality at inline edit screen -->
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

	
<!-- this script is for timesheet comment  shown and hide  -->	      
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
		function bodyClick(){
		    $(".teskcomment").slideUp(200);   
		    $("html .timesheet_comm").click(function(event){
		        event.stopPropagation();
		    }); 
		 }
</script>	


<script>
function addAttachment(){  
			var checkMaxTsRowNum = $("#attcahmentMaxRowNumber").val();
			var tbl = document.getElementById("attachmentTable");
			var maxRowNum = parseInt($("input[name = 'attcahmentMaxRowNumber']").val());
			$("input[name = 'attcahmentMaxRowNumber']").val(maxRowNum + 1);
			
			
			var row = tbl.insertRow(maxRowNum);
			row.setAttribute("id", "divAttachmentTsRow" + maxRowNum);
			
			
			var cell0 = row.insertCell(0);
			var divAttachmentTdStr = document.getElementById("divAttachmentTd1").innerHTML;
			divAttachmentTdStr = divAttachmentTdStr.replace(/REPLACEROWID/g, (maxRowNum));
			cell0.innerHTML = divAttachmentTdStr;
			
			/* timesheetAttachmentValidationFun(); */
			
}			
</script>

<script type="text/javascript">
	function removeAttachmentRow(rowId) {
		var rowNum = $("#divAttachmentTsRow"+rowId).val();
		var maxRowNum = parseInt(rowNum);
		 document.getElementById("divAttachmentTsRow"+rowId).style.display = 'none';
		 document.getElementById("divAttachmentTsRow"+rowId).value= '';
		 var  rId = "divAttachmentTsRow"+rowId;
		  var r = document.getElementById("attachmentTable").rows[rId];
          var count = 2*(r.cells.length);
		    for(i=0;i<=count;i++){
		        r.deleteCell(0);
		    } 
	}
</script>

	

<!-- Content Area -->
<section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Timesheets</h2>
          </div>
          <div class="pull-right brud-crum">Home &raquo; General Form &raquo; Create User</div>
        </section>
        
        <div id="timesheetDynamicDiv"></div>
        
        <!-- ####  this div is for dummy timesheets entries ( useful for add rows )   below   ####-->
				         <div id="divadttTd1" style="display:none;" >   
				             <div> 
				              <select id="REPLACEROWIDtaskId" name="REPLACEROWIDtaskId" class="existedProjectTaskIdprojectId" onchange="getProjectTasksOnChange('REPLACEROWIDsubmissionsubmissionIdtask1Id','projectId')">  
				                          <%--  <c:forEach items="${sourcelead_tasksList}" var="task" varStatus="t">
				                              	<option value="${task.id}">${task.taskName}</option>
				                           </c:forEach> --%>
				             </select>
				             <input type="button" value="" onClick="removeTimeSheetRow('REPLACEROWID','submissionId')" class="addtaskfield removetaskfield"/>
				             </div>
				             
				        </div>
				        <div id="divadttTd2" style="display:none;">
					    	   <input type="text" onblur='checkHours()'  id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay1Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay1Id" class="inline_field" placeholder="HH:MM" />	
				               <div class="timesheet_comm">
				                <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment1Id');"></i>
				                <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment1Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment1Id" id="REPLACEROWIDsubmissionsubmissionIdtextArea1Id" ></textarea></span>
				               </div>
				   		</div>
				        <div id="divadttTd3" style="display:none;">
								  <input type="text" onblur='checkHours()'  id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay2Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay2Id" class="inline_field" placeholder="HH:MM" />	
				                  <div class="timesheet_comm">
				                      <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment2Id');"></i>
				                  <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment2Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment2Id" id="REPLACEROWIDsubmissionsubmissionIdtextArea2Id" ></textarea></span></div>
				        </div>
				        <div id="divadttTd4" style="display:none;">
								  <input type="text" onblur='checkHours()'   id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay3Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay3Id" class="inline_field" placeholder="HH:MM" />	
				                  <div class="timesheet_comm">
				                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment3Id');"></i>
				                  <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment3Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment3Id" id="REPLACEROWIDsubmissionsubmissionIdtextArea3Id"></textarea></span></div>
				        </div>
				        <div id="divadttTd5" style="display:none;">
								  <input type="text" onblur='checkHours()'   id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay4Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay4Id" class="inline_field" placeholder="HH:MM" />	
				                  <div class="timesheet_comm">
				                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment4Id');"></i>
				                  <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment4Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment4Id" id="REPLACEROWIDsubmissionsubmissionIdtextArea4Id"></textarea></span></div>           
				        </div>
				        <div id="divadttTd6" style="display:none;">
								  <input type="text" onblur='checkHours()'   id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay5Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay5Id" class="inline_field" placeholder="HH:MM" />	
				                  <div class="timesheet_comm">
				                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment5Id');"></i>
				                  <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment5Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment5Id" id="REPLACEROWIDsubmissionsubmissionIdtextArea5Id"></textarea></span></div>  
				    	 </div>
				         <div id="divadttTd7" style="display:none;">
							  <input type="text" onblur='checkHours()'   id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay6Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay6Id" class="inline_field" placeholder="HH:MM" />	
				                 <div class="timesheet_comm">
				                 <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment6Id');"></i>
				                 <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment6Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment6Id" id="REPLACEROWIDsubmissionsubmissionIdtextArea6Id"></textarea></span></div>
				         </div>
				         <div id="divadttTd8" style="display:none;">
							  <input type="text" onblur='checkHours()'  id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay7Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay7Id" class="inline_field" placeholder="HH:MM" />	
				                 <div class="timesheet_comm">
				                 <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment7Id');"></i>
				                 <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment7Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment7Id" id="REPLACEROWIDsubmissionsubmissionIdtextArea7Id"></textarea></span></div>            
				    	 </div>
						<!-- #### this div is for dummy timesheets entries ( useful for add rows ) above  ####-->  
						
						<div id="divAttachmentTd1" style="display: none; float: left;">
								<div id='REPLACEROWIDdocFileuploadFileGroup'>
						  			<input type='file' name='REPLACEROWIDdocFile' id='REPLACEROWIDdocFile' class="timesheetAttachmentValidation"> 
						  			<i class="fa fa-times" onclick="removeAttachmentRow(REPLACEROWID);"></i>
						  		</div>
					    </div>
    
		<!-- Timesheet -->
<!-- <form id="timesheet_form" name="timesheet_form">

 
<div class="timesheet_out">
	<div class="timesheet_wrap">
	<div class="data_loading"></div>
       
		<div class="timesheetNewHead">
			<div class="timeNewSe">
                <select class="form-control chosen-select">
                  <option>Project 1</option>
                  <option>Project 2</option>
                  <option>Project 3</option>
                  <option>Project 4</option>
                  <option>Project 5</option>
                </select>
            </div>
			<div class="timeNewSe">
                <select class="form-control chosen-select">
                  <option>January - 2017</option>
                  <option>February - 2017</option>
                  <option>March - 2017</option>
                  <option>April - 2017</option>
                  <option>May - 2017</option>
                  <option>June - 2017</option>
                  <option>July - 2017</option>
                  <option>August - 2017</option>
                  <option>September - 2017</option>
                  <option>October - 2017</option>
                  <option>November - 2017</option>
                  <option>December - 2017</option>
                </select>
            </div> 
			<div class="timeNewSe">
				<b class="proj_active">Active</b><i class="glyphicon glyphicon-comment com_active" >
					<div class="timeNewSeMess"><p>Rejected message here Rejected message here Rejected message here</p></div>
				</i>
			</div>
			<div class="clearfix"></div>          
		</div>  
       
<div class="timesheetNewTabs">
<div class="project_side_wrap_inn_tabs">
    <ul class="project-tabs-menu">
        <li class="current"><a href="#tab-1">Week 1</a></li>
        <li><a href="#tab-2">Week 2</a></li>
        <li><a href="#tab-3">Week 3</a></li>
        <li><a href="#tab-4">Week 4</a></li>
        <li><a href="#tab-5">Week 5</a></li>
		<div class="export">
			<p><i class="glyphicon glyphicon-export"></i>Export<i class="fa fa-caret-down"></i></p>
				<ul>
                	<li>PDF<i class="fa fa-file-pdf-o"></i></li>
                    <li>Excel<i class="fa fa-file-excel-o"></i></li>
                    <li>Word<i class="fa fa-file-word-o"></i></li>
				</ul>			
		</div>
        <div class="clearfix"></div>
    </ul>
    
    <div class="project_tabs">
        <div id="tab-1" class="project_tabs_inn">
 <div class="timesheet_tableNew">
        	<table id="timesheet" cellpadding="0" cellspacing="0">
                
            	<tbody>
                	<tr>
                        <th> 
                         <table id="1" cellpadding="0" cellspacing="0">
                             <th>Tasks </th>
                        <th>Mon<i>09-01-2017</i></th>
                        <th>Tue<i>11-01-2017</i></th>
                        <th>Wed<i>12-01-2017</i></th>
                        <th>Thu<i>13-01-2017</i></th>
                        <th>Fri<i>14-01-2017</i></th>
                        <th>Sat<i>15-01-2017</i></th>
                        <th>Sun<i>16-01-2017</i></th>
                         </table>
                        </th>                   
                    </tr>
                    
                    <tr id="timesheetRow1">
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
                                 </table>
                       </td>   
                        
                    </tr>                                                        
                </tbody>
            </table>
                        
        </div>        	
        </div>
        
        <div id="tab-2" class="project_tabs_inn">
 <div class="timesheet_tableNew">
        	<table id="timesheet" cellpadding="0" cellspacing="0">
                
            	<tbody>
                	<tr>
                        <th> 
                         <table id="1" cellpadding="0" cellspacing="0">
                             <th>Tasks </th>
                        <th>Mon<i>09-01-2017</i></th>
                        <th>Tue<i>11-01-2017</i></th>
                        <th>Wed<i>12-01-2017</i></th>
                        <th>Thu<i>13-01-2017</i></th>
                        <th>Fri<i>14-01-2017</i></th>
                        <th>Sat<i>15-01-2017</i></th>
                        <th>Sun<i>16-01-2017</i></th>
                         </table>
                        </th>                   
                    </tr>
                    
                    <tr id="timesheetRow2">
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
                                 </table>
                       </td>   
                        
                    </tr>                                                        
                </tbody>
            </table>
                        
        </div>        	
        </div> 
                      
        <div id="tab-3" class="project_tabs_inn">
 <div class="timesheet_tableNew">
        	<table id="timesheet" cellpadding="0" cellspacing="0">
                
            	<tbody>
                	<tr>
                        <th> 
                         <table id="1" cellpadding="0" cellspacing="0">
                             <th>Tasks </th>
                        <th>Mon<i>09-01-2017</i></th>
                        <th>Tue<i>11-01-2017</i></th>
                        <th>Wed<i>12-01-2017</i></th>
                        <th>Thu<i>13-01-2017</i></th>
                        <th>Fri<i>14-01-2017</i></th>
                        <th>Sat<i>15-01-2017</i></th>
                        <th>Sun<i>16-01-2017</i></th>
                         </table>
                        </th>                   
                    </tr>
                    
                    <tr id="timesheetRow3">
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
                                 </table>
                       </td>   
                        
                    </tr>                                                        
                </tbody>
            </table>   
        </div>        	
        </div>             
               
        <div id="tab-4" class="project_tabs_inn">
 <div class="timesheet_tableNew">
        	<table id="timesheet" cellpadding="0" cellspacing="0">
                
            	<tbody>
                	<tr>
                        <th> 
                         <table id="1" cellpadding="0" cellspacing="0">
                             <th>Tasks </th>
                        <th>Mon<i>09-01-2017</i></th>
                        <th>Tue<i>11-01-2017</i></th>
                        <th>Wed<i>12-01-2017</i></th>
                        <th>Thu<i>13-01-2017</i></th>
                        <th>Fri<i>14-01-2017</i></th>
                        <th>Sat<i>15-01-2017</i></th>
                        <th>Sun<i>16-01-2017</i></th>
                         </table>
                        </th>                   
                    </tr>
                    
                    <tr id="timesheetRow4">
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
                                 </table>
                       </td>   
                        
                    </tr>                                                        
                </tbody>
            </table>
        </div>        	
        </div> 
        
        <div id="tab-5" class="project_tabs_inn">
 <div class="timesheet_tableNew">
        	<table id="timesheet" cellpadding="0" cellspacing="0">
                
            	<tbody>
                	<tr>
                        <th> 
                         <table id="1" cellpadding="0" cellspacing="0">
                             <th>Tasks </th>
                        <th>Mon<i>09-01-2017</i></th>
                        <th>Tue<i>11-01-2017</i></th>
                        <th>Wed<i>12-01-2017</i></th>
                        <th>Thu<i>13-01-2017</i></th>
                        <th>Fri<i>14-01-2017</i></th>
                        <th>Sat<i>15-01-2017</i></th>
                        <th>Sun<i>16-01-2017</i></th>
                         </table>
                        </th>                   
                    </tr>
                    
                    <tr id="timesheetRow5">
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
                                 </table>
                       </td>   
                        
                    </tr>                                                        
                </tbody>
            </table>
        </div>        	
        </div>         
        
    </div>
    
    
  <div id="divadttTd1" style="display:none;"  > <div id="projectName">Sourcelead </div></div>
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

    
</div>	
</div>       
       
        
        
    <div class="timesheet_bot">
    	<div class="timesheet_week_comm">
            <div class="commentFile">
            	<span><input type="file" ><i class="fa fa-times"></i></span>
                <span><input type="file" ><i class="fa fa-plus"></i></span>
            </div>    
            <div class="commentTime"><textarea placeholder="comments here..."></textarea></div>
            <div class="clearfix"></div>
        </div>
   	<div class="timesheet_sub_other">
        	Submit to others <a href="javascript:void(0)"><i class="fa fa-envelope"></i> Email</a>
            <a href="javascript:void(0)"><i class="fa fa-users"></i> Users</a>
        </div>
    	<div class="clearfix"></div>
        
    	<div class="timesheet_button">
        	<input type="button" value="Draft" class="time_btn time_dra" />
            <input type="button" value="Submit" class="time_btn" />
            <input type="button" value="Clear" class="time_btn time_cancel" />
        </div>        
        
    </div>  
    
<div class="res_total_hours">
	<i></i>
	<p>Total hours</p>
    <h2>45:30</h2>
    <span>This Week</span>
</div>          
        
    </div>
    	
	
	
</div>  

 </form> 		 -->
		<!-- Timesheet -->
		
      </section>
      <!-- Content Area Ends--> 

 
      
    