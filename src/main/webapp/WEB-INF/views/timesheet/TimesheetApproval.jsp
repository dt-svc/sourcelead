<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />


<!-- this is for mask -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- <link href="resources/css/jquery-ui.css" rel="stylesheet"> -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.11.3/jquery-ui.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/jquery-fun.js"></script>
<link rel="stylesheet" href="resources/css/timesheet.css" type="text/css" >
<script src="resources/js/jquery.maskedinput-1.3.min.js"></script>
<script src="resources/js/formvalidator.js"></script>

<link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/chosen.jquery.min.js"></script> 

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> 

<style type="text/css">
.glyphicon-none:before {
    content: "\2122";
    color: transparent !important;
}
.timesheet-content {
background: #fff;
min-height: 100px;
clear:both;
display: inline-block;
width:100%;
}
.timesheet-content #example_filter {
position: relative !important;
top:0px !important;
}
.timesheet_appr_table.overflowVisible {
overflow: visible !important;
margin-top: 45px;
}
.timesheet_appr_table.overflow_Visible {
overflow: visible !important;
margin-top: 3px;
}
</style>



<%-- <jsp:include page="TimesheetScripts.jsp" /> --%>

<!-- this script is for Timesheet Approval popup -->
<script>
function timesheetApprovalPopUpByProjectMemberId(timesheetFrequency,projectMemberId){
		var sourceleadUrl ='${sourceleadUrl}';
	    $.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/timesheetDivByFrequency?${_csrf.parameterName}=${_csrf.token}&timesheetFrequency="+timesheetFrequency+"&projectMemberId="+projectMemberId+"&from=timesheetApproval", 	   
	    success: function(data) {
	    		 //$("#timesheetPopUp").removeAttr("style");
 	    	     $("#timesheetPopUp").empty();
			     $("#timesheetPopUp").append(data);
			     maskme();
			     $(".time_appr_side_wrap").addClass("time_appr_side_wrap_active");
			     $(".side_mask").fadeIn(200); 
			     $("body").css('overflow-y', 'hidden');
			     
			     
			     $("html").trigger('click');
	    	  	 $("#tab-1").show();
		    	 $(".project-tabs-menu a").click(function(event) {
		    	        event.preventDefault();
		    	        $(this).parent().addClass("current");
		    	        $(this).parent().siblings().removeClass("current");
		    	        var tab = $(this).attr("href");
		    	        $(".project_tabs_inn").not(tab).css("display", "none");
		    	        $(tab).fadeIn();
		    	  });
		    	     
			    /*  maskme();  
			     $("html").trigger('click'); */
	    }
	    });
} 
</script>	

 <!-- this script is for save Timesheet -->               
 

  



<!-- this script is for add row functionality at Timesheet screen -->
<script>
function addTimeSheetRow(projectId) {
	          
		    var tbl = document.getElementById("timesheet");
			var rowNum = $("#maxRowNumber").val();
			var maxRowNum = parseInt(rowNum);
			maxRowNum = maxRowNum +1;
			$("#maxRowNumber").val(maxRowNum); 
			var row = tbl.insertRow(maxRowNum);
			row.setAttribute("id", "timesheetRow"+maxRowNum);
			
			  
		    var previousMaxRowNum =parseInt(rowNum-1);
			var cell0 = row.insertCell(0);
			var divadttTd0Str = document.getElementById("divadttTd1").innerHTML;
			divadttTd0Str = divadttTd0Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd0Str = divadttTd0Str.replace(/projectId/g, (projectId)); 
			cell0.setAttribute("class", "pname");  
			cell0.innerHTML = divadttTd0Str;
			
		    var cell1 = row.insertCell(1);
			var isCell1Disabled = $("#1timeSheetDay1Id").prop('disabled');
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
            divadttTd1Str = divadttTd1Str.replace(/projectId/g, (projectId));
			cell1.innerHTML = divadttTd1Str;
			
			
			var cell2 = row.insertCell(2);
			var isCell2Disabled = $("#1timeSheetDay2Id").prop('disabled');
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
			divadttTd2Str = divadttTd2Str.replace(/projectId/g, (projectId));
			cell2.innerHTML = divadttTd2Str;
			
			
			var cell3 = row.insertCell(3);
			var isCell3Disabled = $("#1timeSheetDay3Id").prop('disabled');
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
			divadttTd3Str = divadttTd3Str.replace(/projectId/g, (projectId));
			cell3.innerHTML = divadttTd3Str;
			
			
			var cell4= row.insertCell(4);
			 var isCell4Disabled = $("#1timeSheetDay4Id").prop('disabled');
			if(isCell4Disabled){
				$("#divadttTd5 :input").attr("disabled", "disabled");
				$('.timesheet_comm','#divadttTd5').find('i').attr('class','glyphicon');
			}else{
				$("#divadttTd5 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd5').find('i').attr('class','glyphicon glyphicon-comment');
			} 
			var divadttTd4Str = document.getElementById("divadttTd5").innerHTML;
			divadttTd4Str = divadttTd4Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd4Str = divadttTd4Str.replace(/projectId/g, (projectId)); 
			cell4.innerHTML = divadttTd4Str;
			
			var cell5 = row.insertCell(5);
			var isCell5Disabled = $("#1timeSheetDay5Id").prop('disabled'); 
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
			divadttTd5Str = divadttTd5Str.replace(/projectId/g, (projectId)); 
			cell5.innerHTML = divadttTd5Str;
			
			var cell6 = row.insertCell(6);
			var isCell6Disabled = $("#1timeSheetDay6Id").prop('disabled');
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
			divadttTd6Str = divadttTd6Str.replace(/projectId/g, (projectId));
			cell6.innerHTML = divadttTd6Str;
			
			var cell7 = row.insertCell(7);
		    var isCell7Disabled = $("#1timeSheetDay7Id").prop('disabled');
			if(isCell7Disabled){
				$("#divadttTd8 :input").attr("disabled", "disabled");
				$('.timesheet_comm','#divadttTd8').find('i').attr('class','glyphicon');
			}else{
				$("#divadttTd8 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd8').find('i').attr('class','glyphicon glyphicon-comment');
			} 
			var divadttTd7Str = document.getElementById("divadttTd8").innerHTML;
			divadttTd7Str = divadttTd7Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd7Str = divadttTd7Str.replace(/projectId/g, (projectId));
			cell7.innerHTML = divadttTd7Str;
			
		    maskme();
		    
			var projectTaskId = maxRowNum+"task1Id"; 
			
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

<!-- this script is for remove timesheet -->
<script type="text/javascript">	

function removeTimeSheetRow(rowId) { 
	
			var rowNum = $("#maxRowNumber").val();
			var maxRowNum = parseInt(rowNum);
			/* $("#maxRowNumber"+submissionId).val(maxRowNum - 1);  */
			
			 document.getElementById("timesheetRow"+rowId).style.display = 'none';
			 document.getElementById("timesheetRow"+rowId).value= '';
			 var  rId = "timesheetRow"+rowId;
			  var r = document.getElementById("timesheet").rows[rId];
	          var count = 2*(r.cells.length);
			    for(i=0;i<=count;i++){
			        r.deleteCell(0);
			    } 
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


<!-- this script is for save Timesheet -->               
<script type="text/javascript">
function newSaveTimesheet(status,savingType){ 
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
       		   $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/load1.svg"); background-position:60% center; background-repeat:no-repeat; background-size:50px;');
        	   $.ajax({
	      	          type : "POST",
	          	      data: formData,
	          	      contentType : 'application/json; charset=utf-8',
	          	      enctype: 'multipart/form-data',
	      	          processData: false,
	      	          contentType:false,
	          	      dataType : 'json',
        	      	  url: sourceleadUrl+"/newSaveTimesheet?${_csrf.parameterName}=${_csrf.token}&json="+mydata+"&status="+status+"&savingType="+savingType,		
        		      success :function(result) {
        		    	  
        		    	 $(".time_appr_side_wrap").removeClass("time_appr_side_wrap_active");
     		    		 $(".side_mask").fadeOut(200); 
     	        		 $("body").css('overflow-y', 'scrool');
     	        		 $("#dynamic").removeAttr('style');
     	        		 
        		    	  /* $("#loadTime").removeAttr("style");  */
        		    	  
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


<!-- this script is for delete document -->
<script type="text/javascript">
function deleteSubmissionDocument(submissionDocumentId){  
    var sourceleadUrl ='${sourceleadUrl}';
    /*  var startdate; */
	var navigation;
    $.ajax({
          type : "POST",
      	  url: sourceleadUrl+"/deleteSubmissionDocument?${_csrf.parameterName}=${_csrf.token}&submissionDocumentId="+submissionDocumentId,		
	      success :function(result) {
	    	  $("#"+submissionDocumentId+"document").remove();
	      }
      	  
    }); 
	   
}
</script>
	
	
	<!-- Content Area Ends--> 
	<div class="time_pop" id="timeSheetErrorMsg"></div>
	
	<!-- addrow divs -->
	 <div id="divadttTd1" style="display:none;" >   
	      <div> 
	       <select id="REPLACEROWIDtaskId" name="REPLACEROWIDtaskId" class="existedProjectTaskIdprojectId" onchange="getProjectTasks('REPLACEROWIDtask1Id','projectId')">  
	                   <%--  <c:forEach items="${sourcelead_tasksList}" var="task" varStatus="t">
	                       	<option value="${task.id}">${task.taskName}</option>
	                    </c:forEach> --%>
	      </select>
	      <input type="button" value="" onClick="removeTimeSheetRow('REPLACEROWID')" class="addtaskfield removetaskfield"/>
	      </div>
	      
	 </div>
	 <div id="divadttTd2" style="display:none;">
	  <input type="text" onblur='checkHours()'  id="REPLACEROWIDtimeSheetDay1Id" name="REPLACEROWIDtimeSheetDay1Id" class="inline_field" placeholder="HH:MM" />	
	        <div class="timesheet_comm">
	         <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment1Id');"></i>
	           <span class="teskcomment" id="REPLACEROWIDcomment1Id" ><textarea name="REPLACEROWIDcomment1Id" id="REPLACEROWIDtextArea1Id" ></textarea></span>
	          </div>
	</div>
	<div id="divadttTd3" style="display:none;">
	<input type="text" onblur='checkHours()'  id="REPLACEROWIDtimeSheetDay2Id" name="REPLACEROWIDtimeSheetDay2Id" class="inline_field" placeholder="HH:MM" />	
	           <div class="timesheet_comm">
	               <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment2Id');"></i>
	           <span class="teskcomment" id="REPLACEROWIDcomment2Id" ><textarea name="REPLACEROWIDcomment2Id" id="REPLACEROWIDtextArea2Id" ></textarea></span></div>
	 </div>
 	<div id="divadttTd4" style="display:none;">
		<input type="text" onblur='checkHours()'   id="REPLACEROWIDtimeSheetDay3Id" name="REPLACEROWIDtimeSheetDay3Id" class="inline_field" placeholder="HH:MM" />	
	           <div class="timesheet_comm">
	           <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment3Id');"></i>
	           <span class="teskcomment" id="REPLACEROWIDcomment3Id" ><textarea name="REPLACEROWIDcomment3Id" id="REPLACEROWIDtextArea3Id"></textarea></span></div>
	 </div>
	 <div id="divadttTd5" style="display:none;">
	<input type="text" onblur='checkHours()'   id="REPLACEROWIDtimeSheetDay4Id" name="REPLACEROWIDtimeSheetDay4Id" class="inline_field" placeholder="HH:MM" />	
	           <div class="timesheet_comm">
	           <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment4Id');"></i>
	           <span class="teskcomment" id="REPLACEROWIDcomment4Id" ><textarea name="REPLACEROWIDcomment4Id" id="REPLACEROWIDtextArea4Id"></textarea></span></div>           
	 </div>
	 <div id="divadttTd6" style="display:none;">
	<input type="text" onblur='checkHours()'   id="REPLACEROWIDtimeSheetDay5Id" name="REPLACEROWIDtimeSheetDay5Id" class="inline_field" placeholder="HH:MM" />	
	           <div class="timesheet_comm">
	           <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment5Id');"></i>
	            <span class="teskcomment" id="REPLACEROWIDcomment5Id" ><textarea name="REPLACEROWIDcomment5Id" id="REPLACEROWIDtextArea5Id"></textarea></span></div>  
	</div>
	<div id="divadttTd7" style="display:none;">
	   <input type="text" onblur='checkHours()'   id="REPLACEROWIDtimeSheetDay6Id" name="REPLACEROWIDtimeSheetDay6Id" class="inline_field" placeholder="HH:MM" />	
	          <div class="timesheet_comm">
	          <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment6Id');"></i>
	          <span class="teskcomment" id="REPLACEROWIDcomment6Id" ><textarea name="REPLACEROWIDcomment6Id" id="REPLACEROWIDtextArea6Id"></textarea></span></div>
	</div>
	<div id="divadttTd8" style="display:none;">
		<input type="text" onblur='checkHours()'  id="REPLACEROWIDtimeSheetDay7Id" name="REPLACEROWIDtimeSheetDay7Id" class="inline_field" placeholder="HH:MM" />	
	          <div class="timesheet_comm">
	          <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment7Id');"></i>
	           <span class="teskcomment" id="REPLACEROWIDcomment7Id" ><textarea name="REPLACEROWIDcomment7Id" id="REPLACEROWIDtextArea7Id"></textarea></span></div>            
	</div>
	<!-- #### this div is for dummy timesheets entries ( useful for add rows ) above  ####-->  
	
	<div id="divAttachmentTd1" style="display: none; float: left;">
	<div id='REPLACEROWIDdocFileuploadFileGroup'>
				<input type='file' name='REPLACEROWIDdocFile' id='REPLACEROWIDdocFile' class="timesheetAttachmentValidation"> 
				<i class="fa fa-times" onclick="removeAttachmentRow(REPLACEROWID);"></i>
		</div>
	</div>
 
 
 
 
 <!-- this script is for clear timesheet -->
<script>
function clearTimesheet(){ 
	for (var i = 1; i < 8; i++){
		 for (var j = 1; j < 50; j++){
			    $("#"+j+"timeSheetDay"+i+"Id").val("00:00");
			    $("#"+j+"textArea"+i+"Id").val("");  
		 }
	}
	$("#weekComment").val('');
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
		 for (var j = 1; j < 50; j++){  
			    var btemp = $("#"+j+"timeSheetDay"+i+"Id").val();
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
		$("#timeSheetErrorMsg").attr('style','display:block;z-index: 10000000;top: 5px !important;');
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
/* this script is for calculate total hours */
$(document).click(function(){ 
	 var total;
	 for (var i = 1; i < 8; i++){
		 var bsum = 0;
		 var nbsum = 0;
		 var total = 0;
		 for (var j = 1; j < 50; j++){
			    var btemp = $("#"+j+"timeSheetDay"+i+"Id").val();
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



<script>
// project side bar tabs
$(document).ready(function() {
	timesheetApprovalDiv();
});
</script>  
<script>
	function predefineProfileImage(imageId,userId){
			$(imageId).attr('src','resources/images/userprofile-img.png');
	}
</script>
<script>  
	function timesheetApprovalDiv(filter){ 
		
               var sourceleadUrl ='${sourceleadUrl}';
               var frequencyMonth = $("#frequencyMonth").val();
               if(frequencyMonth == null || frequencyMonth == '' || frequencyMonth == 'undefined')
            	   frequencyMonth = '';
               
  	           var timesheetFrequency = $('#timesheetFrequency').val();
  	           if(timesheetFrequency == null || timesheetFrequency == '' || timesheetFrequency == 'undefined')
  	        	 timesheetFrequency = '';
  	           
  	           var weekFrequency = $('#weekFrequency').val();
	           if((weekFrequency == null || weekFrequency == '' || weekFrequency == 'undefined') || (filter == 'month')  || (filter == 'frequency'))
	        	   weekFrequency = '';
  	         
	           var stage = $('#stage').val();
	           if(timesheetFrequency == null || timesheetFrequency == '' || timesheetFrequency == 'undefined')
	        	   stage = '';
	           
	           var client = $('#client').val();
	           if(client == null || client == '' || client == 'undefined')
	        	   client = '';
	           
               $("#timesheetApprovalDiv").empty();
               $("#timesheetApprovalDiv").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
        	   $.ajax({
	      	          type : "POST",
	          	      dataType : 'json',
        	      	  url: sourceleadUrl+"/timesheetApprovalDiv?${_csrf.parameterName}=${_csrf.token}&frequencyMonth="+frequencyMonth+"&timesheetFrequency="+timesheetFrequency+"&weekFrequency="+weekFrequency+"&client="+client+"&stage="+stage,		
        		      success :function(result) {
        		    	    $("#timesheetApprovalDiv").removeAttr("style");
        		    	    $("#timesheetApprovalDiv").empty();
        		    	  	$("#timesheetApprovalDiv").append(result);
        		    	  	prepareChosenScripts();
        		    	  	pagination();
        		      }
        	    }); 
	}
</script>


<script>
	function timesheetApprovalDivByMonthAndTimesheetFrequency(){ 
	         var sourceleadUrl ='${sourceleadUrl}';
	         var frequencyMonth = $("#frequencyMonth").val();
	         var timesheetFrequency = $('#timesheetFrequency').val();
	         $("#timesheetApprovalDivByMonthAndTimesheetFrequency").empty();
	         $("#timesheetApprovalDivByMonthAndTimesheetFrequency").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	         $.ajax({
     	          type : "POST",
         	      dataType : 'json',
   	      	  	  url: sourceleadUrl+"/timesheetApprovalDivByMonthAndTimesheetFrequency?${_csrf.parameterName}=${_csrf.token}&frequencyMonth="+frequencyMonth+"&timesheetFrequency="+timesheetFrequency,		
   		          success :function(result) {
   		        	 $("#timesheetApprovalDivByMonthAndTimesheetFrequency").removeAttr("style");
   		             $("#timesheetApprovalDivByMonthAndTimesheetFrequency").html(result);  
   		      	     prepareChosenScripts();
   		          }
   	         }); 
	}
</script>	

   

<script>
	function timesheetApprovalDivByFrequency(frequency){   
	         var sourceleadUrl ='${sourceleadUrl}';
	         $("#timesheetApprovalDivByFrequency").empty();
	         $("#timesheetApprovalDivByFrequency").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	         $.ajax({
     	          type : "POST",
         	      dataType : 'json',
   	      	  	  url: sourceleadUrl+"/timesheetApprovalDivByFrequency?${_csrf.parameterName}=${_csrf.token}",		
   		          success :function(result) {
   		        	      $("#timesheetApprovalDivByFrequency").removeAttr("style");
	   		    	      $("#timesheetApprovalDivByFrequency").empty();
			    	  	  $("#timesheetApprovalDivByFrequency").append(result); 
   		          }
   	         }); 
	}
</script> 

<script>
	function timesheetApprovalDivByStatus(frequency){   
				
	         var sourceleadUrl ='${sourceleadUrl}';
	         $("#timesheetApprovalDivByStatus").empty();
	         $("#timesheetApprovalDivByStatus").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	         $.ajax({
     	          type : "POST",
         	      dataType : 'json',
   	      	  	  url: sourceleadUrl+"/timesheetApprovalDivByStatus?${_csrf.parameterName}=${_csrf.token}",		
   		          success :function(result) {
   		        	  		
   		        	      $("#timesheetApprovalDivByStatus").removeAttr("style");
	   		    	      $("#timesheetApprovalDivByStatus").empty();
			    	  	  $("#timesheetApprovalDivByStatus").append(result); 
			    	  	  
   		          }
   	         }); 
	}
</script> 


<script>
	function timesheetApprovalPopup(startDate,endDate){ 
	         var sourceleadUrl ='${sourceleadUrl}';
	         $.ajax({
    	          type : "POST",
        	      dataType : 'json',
  	      	  	  url: sourceleadUrl+"/timesheetDivByFrequency?${_csrf.parameterName}=${_csrf.token}",		
  		          success :function(result) {
  		    	       
  		    	       $("#timesheetPopUp").empty();
		    	  	   $("#timesheetPopUp").append(result); 
		    	  	   
		    	  	  
		    	 		$(".time_appr_side_wrap").addClass("time_appr_side_wrap_active");
		    	 		$(".side_mask").fadeIn(200);
		    	 		$("body").css('overflow-y', 'hidden');
		    	 	 
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
<body>
      <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Timesheet approval</h2>
          </div>
            <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; TimeSheet List</div>
        </section>
        
		<!-- Project -->
<!-- put id here -->
		<div id="timesheetApprovalDiv"></div>


</section>
      
</div>


<div class="side_mask"><div class="proj_close"></div></div>	
<div class="time_appr_side_wrap">
	<div class="time_appr_side_wrap_inn">
	 	<div id="timesheetPopUp"></div>
	</div>
</div>	
<div class="mask_time"></div>	
<div class="time_appr_rejct_pop">
	<textarea placeholder="Enter comment here"></textarea>
	<a href="javascript:void(0)">Submit</a><a class="close_color" href="javascript:void(0)">Cancel</a>
</div>	

<div class="time_pop time_pop_a"><p>Monday hours should not be more than 24<b>X</b></p></div>
<!--   Timesheet approval popup   -->
	</body>
    
<script>
$(document).ready(function() {
    $(".timesheet_appr_table .glyphicon").click(function(){
		$(".timesheet_appr_table .glyphicon strong").slideToggle(200);
	});
    $("html").click(function() {
        $(".timesheet_appr_table .glyphicon strong").slideUp(200);
    });
    $(".timesheet_appr_table .glyphicon strong, .timesheet_appr_table .glyphicon").click(function(e) {
        e.stopPropagation();
    });	
});


// Timesheet his pop
$(document).ready(function() {
    $(".time_newhead h2").click(function(){
		$(".time_newhead_pop").slideToggle(100);
	});
    $("html").click(function() {
        $(".time_newhead_pop").slideUp(100);
    });
    $(".time_newhead_pop, .time_newhead h2").click(function(e) {
        e.stopPropagation();
    });	
});


// close popup
$(document).ready(function() {
    $(".timesheet_appr_table .fa-times").click(function(){
		$(".mask_time").fadeIn(200);
		$(".time_appr_rejct_pop").slideToggle(200);
	});
    $(".time_appr_rejct_pop a").click(function(){
		$(".mask_time").fadeOut(200);
		$(".time_appr_rejct_pop").slideUp(200);
	});	
});

//  Timesheet approval side bar
$(document).ready(function() {
   /*  $(".timesheet_appr_table a").click(function(){
		$(".time_appr_side_wrap").addClass("time_appr_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
	}); */
    $(".proj_close, .time_cancel").click(function(){
		$(".time_appr_side_wrap").removeClass("time_appr_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
});

$(document).ready(function() {
    $(".glyphicon-comment").mouseenter(function(){
		$(".app_error_mes").addClass("app_error_mes_active");
	});
    $(".glyphicon-comment").mouseleave(function(){
		$(".app_error_mes").removeClass("app_error_mes_active");
	});	
});

</script>	
<script type="text/javascript">
         function prepareChosenScripts(){ 
        	      var config = {
        			  '.chosen-select'           : {},
        			  '.chosen-select-deselect'  : {allow_single_deselect:true},
        			  '.chosen-select-no-single' : {disable_search_threshold:10},
        			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
        			  '.chosen-select-width'     : {width:"95%"}
        			};
        	      
        		   for (var selector in config) {
        			  $(selector).chosen(config[selector]);
        			};
           };
				
</script> 
<script>
function saveRejectSubmissionByProjectMemberId(projectMemberId,startDate,endDate){
	
	 var msg = $("#reject"+projectMemberId+"message").val(); 
     var sourceleadUrl ='${sourceleadUrl}';
     var rowHtmlContent =  $("#row"+projectMemberId).html();
	 $("#row"+projectMemberId).empty();
	 $("#row"+projectMemberId).html("<td colspan='5'  id='loading"+projectMemberId+"'></td>");
     $("#loading"+projectMemberId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;'); 
   /*  
    $("#timesheetApprList").empty(); 
    $("#timesheetApprList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');   */
    $(".mask_time").fadeOut(200);
    $("#reject"+projectMemberId+"panel").slideUp(200);
    $.ajax({
    type: "POST",
    url: sourceleadUrl+"/saveSubmissionApprovalStatusByProjectMemberId?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+projectMemberId+"&startDate="+startDate+"&endDate="+endDate+"&rejectedReason="+msg+"&approvalStatus=REJECTED",       
    success: function(data) {    
   	   // approvalList();
   	   var uniqueSepartor = startDate.replace('-','').replace('-','');
   	   if(projectMemberId.indexOf(uniqueSepartor) >= 0){
   		   var table = $('#example').DataTable();
   		   table.row('#row'+projectMemberId+'').remove().draw( false );
   	   }
   	   else{
   		  $("#row"+projectMemberId).empty();
 	    	  $("#row"+projectMemberId).html(rowHtmlContent);
 	    	  $("#status"+projectMemberId).html("REJECTED");
 	    	  $("#action"+projectMemberId).empty();
 	    	  $("#action"+projectMemberId).append(" <div><h3 class='reje_color'>Rejected</h3><i class='glyphicon glyphicon-comment' id='"+projectMemberId+"' onclick='rejectedMessage(\"rejectedMessage"+projectMemberId+"\");'><div class='app_error_mes' id='rejectedMessage"+projectMemberId+"'>"+msg+"</div></i></div>");  
   	   }
   	  pagination();
      }
    }); 
    
}

function rejectedMessage(local){ 
    $("#"+local).slideToggle(200);
    $(".timesheet_appr_table .glyphicon strong, .timesheet_appr_table .glyphicon").click(function(e) {
        e.stopPropagation();
    });	
    
}

function cancelRejectSubmission(projectMemberId){
		 $(".mask_time").fadeOut(200);
		 $("#reject"+projectMemberId+"panel").slideUp(200);
}

function rejecteSubmissionByProjectMemberId(projectMemberId,startDate,endDate){  
        
        var result = "";
        result = result +'<div class="time_appr_rejct_pop" id="reject'+projectMemberId+'panel" >';
		result = result +'<textarea placeholder="Enter comment here" id="reject'+projectMemberId+'message" ></textarea>';
		result = result +'<a href="#" onClick="saveRejectSubmissionByProjectMemberId(\''+projectMemberId+'\',\''+startDate+'\',\''+endDate+'\');" >Submit</a><a class="close_color" href="#" onClick="cancelRejectSubmission(\''+projectMemberId+'\');">Cancel</a>';
		result = result +'</div>';
		 
		$("#rejectedPopup").append(result);
	    $(".mask_time").fadeIn(200); 
		$("#reject"+projectMemberId+"panel").slideToggle(200);
}
function approveSubmissionByProjectMemberId(projectMemberId,startDate,endDate){
		//$("#timesheetApprList").empty();
		var rowHtmlContent =  $("#row"+projectMemberId).html();
		$("#row"+projectMemberId).empty();
		$("#row"+projectMemberId).html("<td colspan='5'  id='loading"+projectMemberId+"'></td>");
	    $("#loading"+projectMemberId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat; '); 
	    var rejectedReason;
        var sourceleadUrl ='${sourceleadUrl}';
	     $.ajax({
	    type: "POST",
	    url: sourceleadUrl+"/saveSubmissionApprovalStatusByProjectMemberId?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+projectMemberId+"&startDate="+startDate+"&endDate="+endDate+"&approvalStatus=APPROVED&rejectedReason="+rejectedReason,        
		    success: function(data) {
		    	 var uniqueSepartor = startDate.replace('-','').replace('-','');
	        	 if(projectMemberId.indexOf(uniqueSepartor) >= 0){
			    	 var table = $('#example').DataTable();
	        		 table.row('#row'+projectMemberId+'').remove().draw( false );
	        		   
	        	 }
	        	 else{
	        		 $("#row"+projectMemberId).empty();
			    	 $("#row"+projectMemberId).html(rowHtmlContent);
			    	 $("#status"+projectMemberId).html("APPROVED");
			    	 $("#action"+projectMemberId).html("<div><h3>Approved</h3></div>"); 
	        	   }
		    	 
		    }
	    });   
}
</script>

<script>
function pagination(){ 
    $('#example').DataTable( { 
    	"scrollCollapse": false,
    	"scrollX": true,
    	"lengthMenu": [[5, 15, 20, -1], [5, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
    		"sSearch": ""
    	}
    } );
}
</script>

<jsp:include page="../masterFooter.jsp" />
 
      
    