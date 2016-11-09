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
<link rel="stylesheet" href="resources/css/timesheet.css" type="text/css" >
<script src="resources/js/jquery.maskedinput-1.3.min.js"></script>
<script src="resources/js/formvalidator.js"></script>


<body  onclick="bodyClick();">
<section class="content-wraper">
 <form id="timesheet_form" name="timesheet_form" enctype="multipart/form-data" method="post">
 <div id="hideError" style="display:none;"></div>
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Timesheets</h2>
    </div>
    <div class="pull-right brud-crum"><a href="dashboard">Home</a> » TimeSheets
  </section>
    <div class="timesheet_out"> 
	<div class="timesheet_wrap">
				         <div id="dynamic"> 
				                    
				         </div> 
           
				         <!-- ####  this div is for dummy timesheets entries ( useful for add rows )   below   ####-->
				         <div id="divadttTd1" style="display:none;" >   
				             <div> 
				              <select id="REPLACEROWIDsubmissionsubmissionIdtask1Id" name="REPLACEROWIDsubmissionsubmissionIdtask1Id" class="existedProjectTaskId" onchange="getProjectTasksOnChange('REPLACEROWIDsubmissionsubmissionIdtask1Id','projectId')">  
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
				                <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment1Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment1Id" ></textarea></span>
				               </div>
				   		</div>
				        <div id="divadttTd3" style="display:none;">
								  <input type="text" onblur='checkHours()'  id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay2Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay2Id" class="inline_field" placeholder="HH:MM" />	
				                  <div class="timesheet_comm">
				                      <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment2Id');"></i>
				                  <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment2Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment2Id"></textarea></span></div>
				        </div>
				        <div id="divadttTd4" style="display:none;">
								  <input type="text" onblur='checkHours()'   id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay3Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay3Id" class="inline_field" placeholder="HH:MM" />	
				                  <div class="timesheet_comm">
				                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment3Id');"></i>
				                  <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment3Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment3Id"></textarea></span></div>
				        </div>
				        <div id="divadttTd5" style="display:none;">
								  <input type="text" onblur='checkHours()'   id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay4Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay4Id" class="inline_field" placeholder="HH:MM" />	
				                  <div class="timesheet_comm">
				                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment4Id');"></i>
				                  <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment4Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment4Id"></textarea></span></div>           
				        </div>
				        <div id="divadttTd6" style="display:none;">
								  <input type="text" onblur='checkHours()'   id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay5Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay5Id" class="inline_field" placeholder="HH:MM" />	
				                  <div class="timesheet_comm">
				                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment5Id');"></i>
				                  <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment5Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment5Id"></textarea></span></div>  
				    	 </div>
				         <div id="divadttTd7" style="display:none;">
							  <input type="text" onblur='checkHours()'   id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay6Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay6Id" class="inline_field" placeholder="HH:MM" />	
				                 <div class="timesheet_comm">
				                 <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment6Id');"></i>
				                 <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment6Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment6Id"></textarea></span></div>
				         </div>
				         <div id="divadttTd8" style="display:none;">
							  <input type="text" onblur='checkHours()'  id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay7Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay7Id" class="inline_field" placeholder="HH:MM" />	
				                 <div class="timesheet_comm">
				                 <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment7Id');"></i>
				                 <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment7Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment7Id"></textarea></span></div>            
				    	 </div>
						<!-- #### this div is for dummy timesheets entries ( useful for add rows ) above  ####-->      
	</div>
	</div>  
</form>   
</section>
<div class="time_pop" id="timeSheetErrorMsg"></div>
</body>
<jsp:include page="../masterFooter.jsp" />


<!-- this script is for timesheet screen  below -->
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
			$("#dynamic").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
			$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/newTimesheet?${_csrf.parameterName}=${_csrf.token}&navigation="+navigation+"&startDate="+startDate, 	   
				    success: function(data) {
				    	if( data.indexOf('Sign In') >= 0){
				    		location.replace(sourceleadUrl);
				    	}
	               		else if (data.indexOf('exception') >= 0) {
	               			 location.replace(sourceleadUrl+"/errorPage");
	               		}else{
				    	       if(data != '' && data != 'undefined' && data != null){
				    	    	   $("#dynamic").removeAttr("style");
				    	    	   $("#dynamic").empty();
							       $("#dynamic").append(data);
							       maskme();  
							       $("html").trigger('click');
				    	       }else{
				    	    	    $("#dynamic").removeAttr("style");
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
									$("#dynamic").empty();
								    $("#dynamic").append(result); 
				    	       }
				    }
					}	
		    });
}
</script>	

<!-- this script is for timesheet screen  above -->   

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


<script>
$(document).ready(function() {
    $(".initiateTimesheetMax").each(function(){
		        this.val("1");
    });
	
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

<!-- this script is for add row functionality at Timesheet screen -->
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
			divadttTd0Str = divadttTd0Str.replace(/projectId/g, (projectId)); 
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
	 
        
        function disableInput(divId){
        	$("#"+divId+" :input").attr("disabled", "disabled");
        }
        
       //get project tasks 
	   function getProjectTasks(projectTaskId,projectId){
		   var sourceleadUrl ='${sourceleadUrl}';
		   $("#"+projectTaskId).empty();
		   
		   var avilableProjectTaskIds = [];
		   $('.existedProjectTaskId').each(function() {
				 var localId = this.value;
				 avilableProjectTaskIds.push(localId);
		   }); 
			 
		   $.ajax({
   	          type : "POST",
 	      	  url: sourceleadUrl+"/projectTasksById?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&avilableProjectTaskIds="+avilableProjectTaskIds,		
 		      success :function(data) {
 		    	    var json = JSON.parse(data);
 		    	    $('.existedProjectTaskId').each(function() {
 			   			 var localId = this.id;
 			   			 var localValue = this.value;
	 			   		    var result = ""; 
	 			   		    if(localValue != null && localValue != '' && localValue!= 'undefined'){ 
	 			   			    $('option', this).not(':eq(0), :selected').remove();
	 			   		    }else{ 
	 			   		    	 $("#"+localId).empty();
	 			   		   		 result = result + "<option value=''>--select option--</option>";
	 			   		    }
	  		    	    	for (var i=0; i<json.length; i++)
	  					 	{
	  			        		var opt = json[i];
	  			        		result = result + "<option value='" +opt.Id+ "'>"+opt.taskName+"</option>";
	  						}
 				    	 	$("#"+localId).append(result);
 			   	   }); 
 		    	   
 		      }
 	      	  
 	       }); 
		   
	   }

	 
</script>
<script>
function getProjectTasksOnChange(projectTaskId,projectId){

	   var sourceleadUrl ='${sourceleadUrl}';
	   
	   var avilableProjectTaskIds = [];
	   $('.existedProjectTaskId').each(function() {
			 var localId = this.value;
			 avilableProjectTaskIds.push(localId);
	   }); 
		 
	   $.ajax({
          type : "POST",
    	  url: sourceleadUrl+"/projectTasksById?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&avilableProjectTaskIds="+avilableProjectTaskIds,		
	      success :function(data) {
	    	    var json = JSON.parse(data);
	    	    $('.existedProjectTaskId').each(function() {
		   			 var localId = this.id;
		   			 var localValue = this.value;
			   		    var result = ""; 
			   		    if(projectTaskId != localId){  
					   		    if(localValue != null && localValue != '' && localValue!= 'undefined'){ 
					   			    $('option', this).not(':eq(0), :selected').remove();
					   		    }else{  
					   		    	 $("#"+localId).empty();
					   		   		 result = result + "<option value=''>--select option--</option>";
					   		    }
				    	    	for (var i=0; i<json.length; i++)
							 	{
					        		var opt = json[i];
					        		result = result + "<option value='" +opt.Id+ "'>"+opt.taskName+"</option>";
								}
					    	 	$("#"+localId).append(result);
			   		    }
			   		   
		   	   }); 
	    	   
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
                
<!-- this script is for save Timesheet -->               
<script type="text/javascript">
function saveMyTimesheet(status,savingType){ 
	 checkHours();
	 var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	 }; 
	 if ($('#timesheet_form').validate(false, validationSettings)){ 
				 
	
               var frm = $('#timesheet_form').serializeFormJSON();
               
               var sourceleadUrl ='${sourceleadUrl}';
               var startdate;
           	   var navigation;
               
       		   var mydata = JSON.stringify(frm);
       		   mydata = mydata.replace(/[{}]/g, "");
       		   mydata = encodeURIComponent(mydata);
       		
       		   var form = $('#timesheet_form')[0];
       		   var formData = new FormData(form); 
       		   var submissionIds = $("#submissionId").val();
       		   $("#dynamic").empty();
       		   $("#dynamic").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
        	   $.ajax({
	      	          type : "POST",
	          	      data: formData,
	          	      contentType : 'application/json; charset=utf-8',
	          	      enctype: 'multipart/form-data',
	      	          processData: false,
	      	          contentType:false,
	          	      dataType : 'json',
        	      	  url: sourceleadUrl+"/saveTimesheet?${_csrf.parameterName}=${_csrf.token}&json="+mydata+"&status="+status+"&savingType="+savingType,		
        		      success :function(result) {
        		    	   dynamicTimesheet("left",result); 
        		    	  /* dynamicTimesheet(); */
        		      }
        	      	  
        	    }); 
        	   return true;
	 	}else{ 
			return false;
	 } 
}

$('body').on('blur', '#timesheet_form', function() {
	$("#timesheet_form").showHelpOnFocus();
	$("#timesheet_form").validateOnBlur(false, validationSettings);  
});
</script>
<script type="text/javascript">
function withdrawMyTimesheet(submissionId,startdate){ 
	/* var frm = $('#timesheet_form').serializeFormJSON(); */
	var sourceleadUrl ='${sourceleadUrl}';
	var navigation;
	   
/* 	var mydata = JSON.stringify(frm);
	mydata = mydata.replace(/[{}]/g, ""); */
	$.ajax({
	      type : "POST",
     	  url: sourceleadUrl+"/withDrawTimesheet?${_csrf.parameterName}=${_csrf.token}&submissionId="+submissionId,		
	      success :function(result) {
	    	  dynamicTimesheet(navigation,startdate); 
	      }
   }); 
}
</script>

<!-- this script is for delete document -->
<script type="text/javascript">
function deleteSubmissionDocument(submissionId){  
    var sourceleadUrl ='${sourceleadUrl}';
    var startdate;
	var navigation;
    $.ajax({
          type : "POST",
      	  url: sourceleadUrl+"/deleteSubmissionDocument?${_csrf.parameterName}=${_csrf.token}&submissionId="+submissionId,		
	      success :function(result) {
	    		dynamicTimesheet(navigation,startdate);  
	      }
      	  
    }); 
	   
}
</script>

   
<script type="text/javascript">	
/* for total hours */
/* $(document).click(function(){
		var submissionId = 1;
		var total;
		 for (var p = 1; p < 8; p++){
		 for (var i = 1; i < 8; i++){  
			 var bsum = 0;
			 var nbsum = 0;
			 var total = 0;
			 for (var j = 1; j < 8; j++){
				    var btemp = $("#"+i+"submission"+p+"timeSheetDay"+j+"Id").val();
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
			
		    $("#"+i+"submission"+p+"total1Id").val(total);
	 }  
}	
		
		
}); */


/* this script is for calculate total hours */
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
		if(!isNaN(hours) && !isNaN(minutes)){
			total = hours +":"+ minutes;
		}else{
			total =  "00:00";
		}
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
	 if(!isNaN(hours) && !isNaN(minutes)){
		 total = hours +":"+ minutes;
	 }else{
		 total = "00:00"; 
	 }
	
	 $("#grandTotal").val(total);
    
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

<!-- this script is for clear timesheet -->
<script>
function clearTimesheet(){ 
	for (var i = 1; i < 8; i++){
	 /* for (var p = 1; p < 200; p++){   */
	 $('.NEW').each(function() {
		 var p = this.id;
		 for (var j = 1; j < 10; j++){
			    $("#"+j+"submission"+p+"timeSheetDay"+i+"Id").val("00:00");
			    $("#"+j+"submission"+p+"textArea"+i+"Id").val("");  
		 }
    /*  } */
	 });  
	}
}
</script>

<!-- this script is for validation of 24 hours -->
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
		$("#timeSheetErrorMsg").attr('style','display:block;');
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
		$("#timeSheetErrorMsg").attr('style','display:none;');
	    $("#timeSheetErrorMsg").empty();
	}); 
	
}
</script>

<script>
	$(document).on("mouseover", ".rejectedHover", function() { 
		var localId = $(this).attr('id');
		$("#rejectedMessage"+localId).addClass('app_error_mes_active');
	});	
	$(document).on("mouseleave", ".rejectedHover", function() { 
		  var localId = $(this).attr('id');
		  $("#rejectedMessage"+localId).removeClass('app_error_mes_active');
	});	
</script>

