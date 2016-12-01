<body onclick="bodyClick();">
      
  <!-- dummy divs above -->          
			  <div id="divadttTd1" style="display:none;" >   
			       <div> 
			        <select id="REPLACEROWIDsubmissionsubmissionIdtask1Id" name="REPLACEROWIDsubmissionsubmissionIdtask1Id" class="existedProjectTaskIdprojectId" onchange="getProjectTasksOnChange('REPLACEROWIDsubmissionsubmissionIdtask1Id','projectId')">
			                     <c:forEach items="${sourcelead_tasksList}" var="task" varStatus="t">
			                        	<option value="${task.id}">${task.taskName}</option>
			                     </c:forEach>
			       </select>
			       <input type="button" value="" onClick="removeTimeSheetRow('REPLACEROWID','submissionId')" class="addtaskfield removetaskfield"/>
			       </div>
			       
			  </div>
             <div  id="divadttTd2" style="display:none;">
				  <input type="text" onblur='checkHours()' id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay1Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay1Id" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
	                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment1Id');"></i>
	                  <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment1Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment1Id" id="REPLACEROWIDsubmissionsubmissionIdtextArea1Id" ></textarea></span>
                  </div>
	     	</div>
                  
             <div id="divadttTd3" style="display:none;">
				  <input type="text" onblur='checkHours()' id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay2Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay2Id" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                      <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment2Id');"></i>
                  <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment2Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment2Id"></textarea></span></div>
             </div>
             <div id="divadttTd4" style="display:none;">
				  <input type="text" onblur='checkHours()' id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay3Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay3Id" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment3Id');"></i>
                  <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment3Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment3Id"></textarea></span></div>
             </div>
             <div id="divadttTd5" style="display:none;">
				  <input type="text" onblur='checkHours()' id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay4Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay4Id" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment4Id');"></i>
                  <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment4Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment4Id"></textarea></span></div>           
             </div>
             <div id="divadttTd6" style="display:none;">
				  <input type="text" onblur='checkHours()' id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay5Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay5Id" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment5Id');"></i>
                  <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment5Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment5Id"></textarea></span></div>  
	    	 </div>
             <div id="divadttTd7" style="display:none;">
				  <input type="text" onblur='checkHours()' id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay6Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay6Id" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment6Id');"></i>
                  <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment6Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment6Id"></textarea></span></div>
              </div>
             <div id="divadttTd8" style="display:none;">
				  <input type="text" onblur='checkHours()' id="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay7Id" name="REPLACEROWIDsubmissionsubmissionIdtimeSheetDay7Id" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment7Id');"></i>
                  <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment7Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment7Id"></textarea></span></div>            
	    	 </div>
             <div  id="divadttTd9" style="display:none;">
				  <input type="text" onblur='checkHours()'  id="REPLACEROWIDsubmissionsubmissionIdtotal1Id" name="REPLACEROWIDsubmissionsubmissionIdtotal1Id" class="inline_field" placeholder="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDsubmissionsubmissionIdcomment8Id');"></i>
                  <span class="teskcomment" id="REPLACEROWIDsubmissionsubmissionIdcomment8Id" ><textarea name="REPLACEROWIDsubmissionsubmissionIdcomment8Id"></textarea></span></div>  
             </div>
<!-- dummy divs above -->            

	
<!--   Timesheet approval popup   -->
<div class="side_mask" onclick="removeTimesheetPopup();">
	 <div class="proj_close"></div> 
</div>

<div class="time_appr_side_wrap">
	   <div id="myId">
	   </div>
</div>
<div class="mask_time"></div>	

<!-- this script is for add files -->
<div id="divAttachmentTd1" style="display: none; float: left;">
	<div id='REPLACEROWIDdocFileuploadFileGroup'>
	  		<input type='file' name='REPLACEROWIDdocFile' id='REPLACEROWIDdocFile' class="timesheetAttachmentValidation"> 
	  		<i class="fa fa-times" onclick="removeAttachmentRow(REPLACEROWID);"></i>
	 </div>
</div>

	 
<!-- this script is for dynamicTimesheetpopUp when click on count -->	
<script>
function myTimesheet(local,startDate,endDate){
	var sourceleadUrl ='${sourceleadUrl}';
    $.ajax({
   	type: "POST",
    url: sourceleadUrl+"/dynamicTimesheetpopUp?${_csrf.parameterName}=${_csrf.token}&employeeId="+local+"&startDate="+startDate+"&endDate="+endDate, 	   
    success: function(data) {
	    	if( data.indexOf('Sign In') >= 0){
	    		location.replace(sourceleadUrl);
	    	}
	   		else if (data.indexOf('exception') >= 0) {
	   			 location.replace(sourceleadUrl+"/errorPage");
	   		}else{     
		    		$("#myId").empty();
		            $("#myId").append(data); 
		            $("html").trigger('click');
		            maskme(); 
		            timesheetAttachmentValidationFun();
		            
		    }
              
    }
    });
    $(".time_appr_side_wrap").addClass("time_appr_side_wrap_active");
    $(".side_mask").fadeIn(200); 
    $("body").css('overflow-y', 'hidden');

} 
function removeTimesheetPopup(){
	 $(".time_appr_side_wrap").removeClass("time_appr_side_wrap_active");
	 $(".side_mask").fadeOut(200); 
	 $("body").css('overflow-y', 'scrool');
}
</script>	

<script>
function myFun(){
	alert("hi");
}
</script>

<!-- this script is for validate size of attachments -->
<script type="text/javascript">
function timesheetAttachmentValidationFun(){
		       var deleteLink = document.querySelectorAll('.timesheetAttachmentValidation');
		       for (var i = 0; i < deleteLink.length; i++) {
		           deleteLink[i].addEventListener('change', function(event) {
		        	     	var attach_id = this.id;
		        	     	var size = $('#'+attach_id)[0].files[0].size;
		        	     	if(size > 2097152){
		        		    	  $("#"+attach_id).val('');
		        		    	  $("#"+attach_id+"uploadFileGroup").append("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </p>");
		        	     	}
		           });
		       }
}   
</script>

<!-- this script is for delete  document -->	
<script type="text/javascript">
function deleteSubmissionDocument(submissionDocumentId,employeeId,startDate,endDate){  
    var sourceleadUrl ='${sourceleadUrl}';
    $.ajax({
          type : "POST",
      	  url: sourceleadUrl+"/deleteSubmissionDocument?${_csrf.parameterName}=${_csrf.token}&submissionDocumentId="+submissionDocumentId,		
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
 /* this script is for get project tasks   */	     
function getProjectTasks(projectTaskId,projectId){  
	   var sourceleadUrl ='${sourceleadUrl}';
	   $("#"+projectTaskId).empty();
	   
	   var avilableProjectTaskIds = [];
	   $('.existedProjectTaskId'+projectId).each(function() {
			 var localId = this.value;
			 avilableProjectTaskIds.push(localId);
	   }); 
				   
	   $.ajax({
          type : "POST",
    	  url: sourceleadUrl+"/projectTasksById?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&avilableProjectTaskIds="+avilableProjectTaskIds,		
	      success :function(data) {
	    	    var json = JSON.parse(data);
	    	    /* var result = ""; 
	    	    for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		result = result + "<option value='" +opt.Id+ "'>"+opt.taskName+"</option>";
				}
	    	    
	    	    $('.existedProjectTaskId').each(function() {
	   			 var localId = this.id;
	   			 alert("localId"+localId);
	   			 $("#"+localId).empty();
		    	 $("#"+localId).append(result);
	   	  	    });  */
	   	  	    
	    	    $('.existedProjectTaskId'+projectId).each(function() {
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
	   $('.existedProjectTaskId'+projectId).each(function() {
			 var localId = this.value;
			 avilableProjectTaskIds.push(localId);
	   }); 
		 
	   $.ajax({
          type : "POST",
    	  url: sourceleadUrl+"/projectTasksById?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&avilableProjectTaskIds="+avilableProjectTaskIds,		
	      success :function(data) {
	    	    var json = JSON.parse(data);
	    	    $('.existedProjectTaskId'+projectId).each(function() {
		   			 var localId = this.id;
		   			 var localValue = this.value;
			   		    var result = ""; 
			   		    if(projectTaskId != localId){  
					   		    if(localValue != null && localValue != '' && localValue!= 'undefined'){ 
					   			    $('option', this).not(':eq(0), :selected').remove();
					   		    }else{  
					   		    	 $("#"+localId).empty();
					   		   		 result = result + "<option value=''>--select Task--</option>";
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
function saveMyTimesheet(status,savingType,startDate,empUserId,projectMemberId,empId){ 
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
        		    	     $(".time_appr_side_wrap").removeClass("time_appr_side_wrap_active");
        		    		 $(".side_mask").fadeOut(200); 
        	        		 $("body").css('overflow-y', 'scrool');
        	        		 $("#dynamic").removeAttr('style');
        	        		 dynamicTimesheet("left",startDate,empUserId,projectMemberId,empId);
        		    	  //approvalList();
        		      }
        	      	  
        	    }); 
        	   return true;
	 	}else{ 
			return false;
	   } 
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
function saveRejectSubmission(local,employeeId,userId,startDate,endDate){
	
		 var msg = $("#reject"+local+"message").val(); 
	     var sourceleadUrl ='${sourceleadUrl}';
	     var rowHtmlContent =  $("#row"+userId).html();
		 $("#row"+userId).empty();
		 $("#row"+userId).html("<td colspan='5'  id='loading"+userId+"'></td>");
	     $("#loading"+userId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;'); 
	    /*  
	     $("#timesheetApprList").empty(); 
	     $("#timesheetApprList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');   */
         $(".mask_time").fadeOut(200);
         $("#reject"+local+"panel").slideUp(200);
         $.ajax({
         type: "POST",
         url: sourceleadUrl+"/saveSubmissionApprovalStatus?${_csrf.parameterName}=${_csrf.token}&employeeId="+employeeId+"&startDate="+startDate+"&endDate="+endDate+"&rejectedReason="+msg+"&approvalStatus=REJECTED",       
         success: function(data) {    
        	   // approvalList();
        	  $("#row"+userId).empty();
	    	  $("#row"+userId).html(rowHtmlContent);
	    	  $("#status"+userId).html("REJECTED");
	    	  $("#action"+userId).empty();
	    	  $("#action"+userId).append(" <div><h3 class='reje_color'>Rejected</h3><i class='glyphicon glyphicon-comment' id='"+local+"' onclick='rejectedMessage(\"rejectedMessage"+local+"\");'><div class='app_error_mes' id='rejectedMessage"+local+"'>"+msg+"</div></i></div>");
           }
         }); 
	     
}

function cancelRejectSubmission(local){
		 $(".mask_time").fadeOut(200);
		 $("#reject"+local+"panel").slideUp(200);
}

function rejecteSubmission(local,employeeId,userId,startDate,endDate){  
        
        var result = "";
        result = result +'<div class="time_appr_rejct_pop" id="reject'+local+'panel" >';
		result = result +'<textarea placeholder="Enter comment here" id="reject'+local+'message" ></textarea>';
		result = result +'<a href="#" onClick="saveRejectSubmission(\''+local+'\',\''+employeeId+'\',\''+userId+'\',\''+startDate+'\',\''+endDate+'\');" >Submit</a><a class="close_color" href="#" onClick="cancelRejectSubmission(\''+local+'\');">Cancel</a>';
		result = result +'</div>';
		 
		$("#rejectedPopup").append(result);
	    $(".mask_time").fadeIn(200); 
		$("#reject"+local+"panel").slideToggle(200);
}
function approveSubmission(userId,employeeId,startDate,endDate){
		//$("#timesheetApprList").empty();
		var rowHtmlContent =  $("#row"+userId).html();
		$("#row"+userId).empty();
		$("#row"+userId).html("<td colspan='5'  id='loading"+userId+"'></td>");
	    $("#loading"+userId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat; '); 
	    var rejectedReason;
        var sourceleadUrl ='${sourceleadUrl}';
	     $.ajax({
	    type: "POST",
	    url: sourceleadUrl+"/saveSubmissionApprovalStatus?${_csrf.parameterName}=${_csrf.token}&employeeId="+employeeId+"&startDate="+startDate+"&endDate="+endDate+"&approvalStatus=APPROVED&rejectedReason="+rejectedReason,        
		    success: function(data) {
		    	 $("#row"+userId).empty();
		    	 $("#row"+userId).html(rowHtmlContent);
		    	 $("#status"+userId).html("APPROVED");
		    	 $("#action"+userId).html("<div><h3>Approved</h3></div>");
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
	 /* for (var p = 1; p < 200; p++){   */
		 $('.NEW').each(function() {
		 var p = this.id;
		 for (var j = 1; j < 50; j++){
			    $("#"+j+"submission"+p+"timeSheetDay"+i+"Id").val("00:00");
			    $("#"+j+"submission"+p+"textArea"+i+"Id").val(""); 
		 }
		 });  
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
			
		 if(!isNaN(hours) && !isNaN(minutes)){
			 total = hours +":"+ minutes;
		 }else{
			 total = "00:00"; 
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
    
   /*  
    $("#filter3ApprovePop").click(function(){
		$("#filter3ApprovedPop").slideToggle(200);
	});
    $("html").click(function() {
        $("#filter3ApprovedPop").slideUp(200);
    });
    $("#filter3ApprovedPop, #filter3ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
     */
    
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

<script>
	$(document).on("mouseover", ".glyphicon-comment", function() { 
		var localId = $(this).attr('id');
		$("#rejectedMessage"+localId).addClass('app_error_mes_active');
	});	
	$(document).on("mouseleave", ".glyphicon-comment", function() { 
		  var localId = $(this).attr('id');
		  $("#rejectedMessage"+localId).removeClass('app_error_mes_active');
	});	
</script>


<div class="time_pop time_pop_a" id="timeSheetErrorMsg">
</div> 

<!-- those scripts are belongs to attachments -->
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
			
			timesheetAttachmentValidationFun();
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