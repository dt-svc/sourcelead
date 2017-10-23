<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<!-- this is for mask -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script src="resources/js/jquery.maskedinput-1.3.min.js"></script>

<script>
// project side bar tabs
$(document).ready(function() {
	timesheetEntriesDiv();
});
</script>  
<script>
	$(document).on("mouseover", ".rejectedHover", function() { 
		var projectMemberId = $("#projectMemberId").val();
		$("#rejectedMessage"+projectMemberId).addClass('app_error_mes_active');
		$("#rejectedMessage1"+projectMemberId).addClass('app_error_mes_active');
	});	
	$(document).on("mouseleave", ".rejectedHover", function() { 
		 var projectMemberId = $("#projectMemberId").val();
		  $("#rejectedMessage"+projectMemberId).removeClass('app_error_mes_active');
		  $("#rejectedMessage1"+projectMemberId).removeClass('app_error_mes_active');
	});	
</script>

<script>
	function timesheetEntriesDiv(){ 
	               var sourceleadUrl ='${sourceleadUrl}';
	               $("#timesheetEntriesDiv").empty();
	               $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	        	   $.ajax({
		      	          type : "POST",
		          	      dataType : 'json',
	        	      	  url: sourceleadUrl+"/timesheetEntries?${_csrf.parameterName}=${_csrf.token}&from=user",		
	        		      success :function(data) {
	        		    	   if(data != '' && data != 'undefined' && data != null){ 
		        		    	    $("#loadTime").removeAttr("style");
		        		    		
		        		    	    $("#timesheetEntriesDiv").empty();
		        		    	  	$("#timesheetEntriesDiv").append(data);
		        		    	  	maskme();
		        		    	  	$("html").trigger('click');
		        		    	  	prepareChosenScripts();
		        		    	  	$("#tab-1").show()
		        		    	    $(".project-tabs-menu a").click(function(event) {
		        		    	        event.preventDefault();
		        		    	        $(this).parent().addClass("current");
		        		    	        $(this).parent().siblings().removeClass("current");
		        		    	        var tab = $(this).attr("href");
		        		    	        $(".project_tabs_inn").not(tab).css("display", "none");
		        		    	        $(tab).fadeIn();
		        		    	    }); 
	        		    	   }else{
				    	    	    $("#timesheetEntriesDiv").removeAttr("style");
				    	    	    $("#loadTime").removeAttr("style");
				    	    	    $("#loadTime").removeClass( "loadTime");
				    	    	    var result ="";
						    	     result = result+"   <div class='dataTables_empty'>"; 
									 /* result = result+"	<div class='col-lg-12'>"; 
									 result = result+"		<div class='content-area clearfix'>"; 
									 result = result+"			<div class='blank-pages'>";
									 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
									 result = result+"			        <p>No Timesheets Found</p>";
									 result = result+"			 </div>";
									 result = result+"		</div>"; 
									 result = result+"	</div>";  */
									 result = result+"  </div> "; 
									$("#timesheetEntriesDiv").empty();
								    $("#timesheetEntriesDiv").append(result); 
				    	       }
	        		    	  	
	        		      }
	        	      	  
	        	      	  
	        	      	  
	        	    }); 
	}
</script>



<script>
	function timesheetEntriesDivByProjectMemberId(){ 
	         var sourceleadUrl ='${sourceleadUrl}';
	         var projectMemberId = $("#projectMemberId").val();
	         var locationCode ='${SourceLeadWeb_locationCode}';
	         $("#timesheetEntriesDivByProjectMemberId").empty();
	         $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	         $.ajax({
     	          type : "POST",
         	      dataType : 'json',
   	      	  	  url: sourceleadUrl+"/timesheetEntriesDivByProjectMemberId?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+projectMemberId+"&from=user"+"&locationCode="+locationCode,		
   		          success :function(result) {
   		        	 $("#loadTime").removeAttr("style");
   		        	 $("#timesheetEntriesDivByProjectMemberId").empty();
   		        	 $("#timesheetEntriesDivByProjectMemberId").append(result);
   		        	 maskme();
   		        	$("html").trigger('click');
   		             prepareChosenScripts();
		    	  	 $("#tab-1").show();
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
<script>
	function timesheetDivByFrequency(){   
	         var sourceleadUrl ='${sourceleadUrl}';
	         var timesheetFrequency = $("#timesheetFrequency").val();
	         var projectMemberId = $("#projectMemberId").val();
	         var locationCode ='${SourceLeadWeb_locationCode}';
	         $("#timesheetDivByFrequency").empty();
	         $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	         $.ajax({
     	          type : "POST",
         	      dataType : 'json',
   	      	  	  url: sourceleadUrl+"/timesheetDivByFrequency?${_csrf.parameterName}=${_csrf.token}&timesheetFrequency="+timesheetFrequency+"&projectMemberId="+projectMemberId+"&from=user"+"&locationCode="+locationCode,		
   		          success :function(result) {
   		        	  		
   		        	      $("#loadTime").removeAttr("style");
	   		    	      $("#timesheetDivByFrequency").empty();
			    	  	  $("#timesheetDivByFrequency").append(result); 
			    	  	  
			    	  	 maskme();
			    	  	 $("html").trigger('click');
			    	  	 prepareChosenScripts();
			    	  	 $("#tab-1").show();
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

<script>
	function timesheetDivByTab(startDate,endDate,approvalStatus){ 
	         var sourceleadUrl ='${sourceleadUrl}';
	         var projectMemberId = $("#projectMemberId").val();
	         var timesheetFrequency = $("#timesheetFrequency").val();
	         var locationCode ='${SourceLeadWeb_locationCode}';
	         approvalStatus =$("#timesheetSubmissionStatus").val();
	         $("#timesheetDivByTab").empty();
	         $("#loadTime").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100; background-position:center; background-repeat:no-repeat; background-size:50px;');
	         $.ajax({
    	          type : "POST",
        	      dataType : 'json',
  	      	  	  url: sourceleadUrl+"/timesheetDivByTab?${_csrf.parameterName}=${_csrf.token}&startDate="+startDate+"&endDate="+endDate+"&projectMemberId="+projectMemberId+"&timesheetFrequency="+timesheetFrequency+"&approvalStatus="+approvalStatus+"&from=user"+"&locationCode="+locationCode,		
  		          success :function(result) {
  		        	   $("#loadTime").removeAttr("style");
  		    	       $("#timesheetDivByTab").empty();
		    	  	   $("#timesheetDivByTab").append(result);
		    	  	   maskme();
		    	  	   $("html").trigger('click');
			    	   $("#tab-1").show();
  		          }
  	         }); 
	}
</script>
<script>
	function timesheetDivAfterSaving(startDate,endDate,approvalStatus){ 
	         var sourceleadUrl ='${sourceleadUrl}';
	         var projectMemberId = $("#projectMemberId").val();
	         var timesheetFrequency = $("#timesheetFrequency").val();
	         var locationCode ='${SourceLeadWeb_locationCode}';
	         $("#timesheetDivByTab").empty();
	         $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	         $.ajax({
    	          type : "POST",
        	      dataType : 'json',
  	      	  	  url: sourceleadUrl+"/timesheetDivAfterSaving?${_csrf.parameterName}=${_csrf.token}&startDate="+startDate+"&endDate="+endDate+"&projectMemberId="+projectMemberId+"&timesheetFrequency="+timesheetFrequency+"&approvalStatus="+approvalStatus+"&from=user"+"&locationCode="+locationCode,		
  		          success :function(result) {
  		        	  $("#loadTime").removeAttr("style");
  		        	   $("#timesheetDivByTab").empty();
  		        	   if(result != null){
  		        		   var statusDivWidth = result["statusDivWidth"];
  		        		   $("#timesheetSubmissionStatusDiv").empty();
	  		    	  	   $("#timesheetSubmissionStatusDiv").append(result["StatusDiv"]); 
	  		    	  	   if(statusDivWidth != null && statusDivWidth != '' && statusDivWidth != 'undefined'){
	  		    	  		   $("#timesheetSubmissionStatusDiv").attr("style","width:244px;"); 
	  		    	  	   }
	  		    	  	   $("#timesheetDivByTab").append(result["TabDiv"]);
	  		    	  	   maskme();
	  		    	  	   $("html").trigger('click');
	  			     	   $("#tab-1").show();
	  			    	   approvalStatus =$("#timesheetSubmissionStatus").val();
	  			    	   var activeElement = $('.project-tabs-menu li.current');
	 		    	        if(approvalStatus != null && approvalStatus != '' && approvalStatus != 'undefined' && approvalStatus != 'new' && approvalStatus != 'draft'){
	 		    	        	activeElement.addClass("current subColor");
	 		    	        	activeElement.siblings().removeClass("current");
	 		    	        	//activeElement.siblings().addClass("subColor");
	 		    	        }
	 		    	        else if(approvalStatus != null && approvalStatus != '' && approvalStatus != 'undefined' && approvalStatus == 'draft'){
	 		    	        	activeElement.addClass("current subColor");
	 		    	        	activeElement.siblings().removeClass("current");
	 		    	        } 
	 		    	        else{
	 		    	        	activeElement.addClass("current");
	 		    	        	activeElement.siblings().removeClass("current");
	 		    	        }
  		        	   }
  		    	      
		    	  	   
  		          }
  	         }); 
	}
</script>
   
<script type="text/javascript">
function withdrawMyTimesheet(projectMemberId,timesheetFrequency){ 
	/* var frm = $('#timesheet_form').serializeFormJSON(); */
	var sourceleadUrl ='${sourceleadUrl}';
	var dateFormat ='${SourceLeadWeb_orgLocations.dateFormat}';
	  $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
	      type : "POST",
     	  url: sourceleadUrl+"/withDrawTimesheet?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+projectMemberId+"&timesheetFrequency="+timesheetFrequency+"&dateFormat="+dateFormat,		
	      success :function(result) {
	    	  $("#loadTime").removeAttr("style"); 
	    	  timesheetDivByFrequency();
	    	  $("html").trigger('click');
	    	  maskme();
	      }
   }); 
}
</script>


<!-- this script is for add row functionality at Timesheet screen -->
<script>
function addTimeSheetRow(projectId) {
	 $('#0docFile').removeAttr('data-validation');
	 $('#0docFile').attr("data-validation-optional","true"); 
	 /* $('#0docFile').removeClass('error'); */
	/*  $('#0docFileuploadFileGroup').find('p.jquery_form_error_message').remove(); */ 
	 if ($('#timesheet_form').validate(false, validationSettings)&&!$("#timeSheetErrorMsg").is(':visible')){      
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
			/* cell0.setAttribute("class", "pname");*/
			cell0.innerHTML = divadttTd0Str;
			
			var cell1 = row.insertCell(1);
			var divadttTd1Str = document.getElementById("divadttTd2").innerHTML;
			divadttTd1Str = divadttTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd1Str = divadttTd1Str.replace(/projectId/g, (projectId)); 
			cell1.innerHTML = divadttTd1Str;
			
			
		    var cell2 = row.insertCell(2);
			var isCell1Disabled = $("#1timeSheetDay1Id").prop('disabled');
			if(isCell1Disabled){
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
			var isCell2Disabled = $("#1timeSheetDay2Id").prop('disabled');
			if(isCell2Disabled){
				$("#divadttTd4 :input").attr('disabled','disabled');
				$('.timesheet_comm','#divadttTd4').find('i').attr('class','glyphicon');
			}
			else{
				$("#divadttTd4 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd4').find('i').attr('class','glyphicon glyphicon-comment');
			} 
			var divadttTd3Str = document.getElementById("divadttTd4").innerHTML;
			divadttTd3Str = divadttTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd2Str = divadttTd3Str.replace(/projectId/g, (projectId));
			cell3.innerHTML = divadttTd3Str;
			
			
			var cell4 = row.insertCell(4);
			var isCell3Disabled = $("#1timeSheetDay3Id").prop('disabled');
			if(isCell3Disabled){
				$("#divadttTd5 :input").attr('disabled','disabled');
				$('.timesheet_comm','#divadttTd5').find('i').attr('class','glyphicon');
			}
			else{
				$("#divadttTd5 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd5').find('i').attr('class','glyphicon glyphicon-comment');
			} 
			var divadttTd4Str = document.getElementById("divadttTd5").innerHTML;
			divadttTd4Str = divadttTd4Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd4Str = divadttTd4Str.replace(/projectId/g, (projectId));
			cell4.innerHTML = divadttTd4Str;
			
			
			var cell5= row.insertCell(5);
			 var isCell4Disabled = $("#1timeSheetDay4Id").prop('disabled');
			if(isCell4Disabled){
				$("#divadttTd6 :input").attr("disabled", "disabled");
				$('.timesheet_comm','#divadttTd6').find('i').attr('class','glyphicon');
			}else{
				$("#divadttTd6 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd6').find('i').attr('class','glyphicon glyphicon-comment');
			} 
			var divadttTd5Str = document.getElementById("divadttTd6").innerHTML;
			divadttTd5Str = divadttTd5Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd5Str = divadttTd5Str.replace(/projectId/g, (projectId)); 
			cell5.innerHTML = divadttTd5Str;
			
			var cell6 = row.insertCell(6);
			var isCell5Disabled = $("#1timeSheetDay5Id").prop('disabled'); 
			 if(isCell5Disabled){
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
			var isCell6Disabled = $("#1timeSheetDay6Id").prop('disabled');
			if(isCell6Disabled){
				$("#divadttTd8 :input").attr("disabled", "disabled");
				$('.timesheet_comm','#divadttTd8').find('i').attr('class','glyphicon');
			}
			else{
				$("#divadttTd8 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd8').find('i').attr('class','glyphicon glyphicon-comment');
			} 
			var divadttTd7Str = document.getElementById("divadttTd8").innerHTML;
			divadttTd7Str = divadttTd7Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd7Str = divadttTd7Str.replace(/projectId/g, (projectId));
			cell7.innerHTML = divadttTd7Str;
			
			var cell8 = row.insertCell(8);
		    var isCell7Disabled = $("#1timeSheetDay7Id").prop('disabled');
			if(isCell7Disabled){
				$("#divadttTd9 :input").attr("disabled", "disabled");
				$('.timesheet_comm','#divadttTd9').find('i').attr('class','glyphicon');
			}else{
				$("#divadttTd9 :input").removeAttr('disabled');
				$('.timesheet_comm','#divadttTd9').find('i').attr('class','glyphicon glyphicon-comment');
			} 
			var divadttTd8Str = document.getElementById("divadttTd9").innerHTML;
			divadttTd8Str = divadttTd8Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd8Str = divadttTd8Str.replace(/projectId/g, (projectId));
			cell8.innerHTML = divadttTd8Str;
			
		    maskme();
		    
			var projectTaskId = maxRowNum+"task1Id"; 
			
			getProjectTasks(projectTaskId,projectId); 
			
		   var isTimesheetAttachmentRequired = $("#isTimesheetAttachmentRequired").val();
		   if(isTimesheetAttachmentRequired != null && isTimesheetAttachmentRequired != "" && isTimesheetAttachmentRequired !="undefined" && isTimesheetAttachmentRequired == "Y"){
			 $('#0docFile').removeAttr('data-validation-optional');
			 $('#0docFile').attr("data-validation","required"); 
			 $(".time_btn").removeAttr("disabled");
			 $("#loadTime").removeAttr("style"); 
		   }
	    }	
	}	
        
        function disableInput(divId){
        	$("#"+divId+" :input").attr("disabled", "disabled");
        } 
       
	   //get project tasks 
	   function getProjectTasks(projectTaskId,projectId){
		   var sourceleadUrl ='${sourceleadUrl}';
		   
		   if($("#" + projectTaskId).val() != null && $("#" + projectTaskId).val() != "" && $("#" + projectTaskId).val() !="undefined"){
			   var taskId = $("#" + projectTaskId).val().split(",");
			   $("#" + projectTaskId + "BillableTypeId").html(taskId[1]);
		   }else{
			   $("#" + projectTaskId + "BillableTypeId").html(""); 
		   }
		   
		   var avilableProjectTaskIds = [];
		   $('.existedProjectTaskId'+projectId).each(function() {
				 var localId = this.value;
				 var taskIdArray = localId.split(",");
				 avilableProjectTaskIds.push(taskIdArray[0]);
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
	  			        		result = result + "<option value='" +opt.Id+","+opt.billableType+"'>"+opt.taskName+"</option>";
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

<!-- this script is for clear timesheet -->
<script>
function clearTimesheet(){ 
	  var startDate = $("#tabStartDate").val();
	  var endDate = $("#tabEndDate").val();
	if(startDate != null && startDate != '' && startDate != 'undefined' && 
			  endDate != null && endDate != '' && endDate != 'undefined'){
		   timesheetDivAfterSaving(startDate,endDate,"new");
	  }
	  else{
		  timesheetDivByFrequency();
	  }
}
</script>

<!-- this script is for validation of 24 hours -->
<script>

function editMe(local){
	 var id = local.id;
	 var inputValue =  $("#"+id).val();
	 var split = inputValue.split(':');
	 var hours = split[0];
	 var minutes = split[1];
	 var hours1 = hours.charAt(0);
	 var hours2 = hours.charAt(1);
	 var min1 = minutes.charAt(0);
	 var min2 = minutes.charAt(1);
	
	 if(!isNaN(hours1) && isNaN(hours2) && isNaN(min1) && isNaN(min2)){
		 $("#"+id).val("0"+hours1+":00");
	 }
	 if(isNaN(hours1) && !isNaN(hours2) && isNaN(min1) && isNaN(min2)){
		 $("#"+id).val("0"+hours2+":00");
	 }
	 else if(isNaN(hours)  && !isNaN(min1) && isNaN(min2)){
		 $("#"+id).val("00:"+min1+"0");
	 }
	 else if(isNaN(hours)  && isNaN(min1) && !isNaN(min2)){
		 $("#"+id).val("00:0"+min2);
	 }
	 else if(!isNaN(hours)  && isNaN(min1) && isNaN(min2)){
		 $("#"+id).val(hours+":00");
	 }
	 else if(!isNaN(hours)&& !isNaN(min1) && isNaN(min2)){
		 $("#"+id).val(hours+":"+"0"+min1);
	 }
	 else if(!isNaN(hours)&& isNaN(min1) && !isNaN(min2)){
		 $("#"+id).val(hours+":"+"0"+min2);
	 }
	 else if(!isNaN(hours)&& !isNaN(minutes)){
		
		  var hrmin= hours * 60;
		  var totalMin = parseInt(hrmin) + parseInt(minutes);
		  hours = Math.floor(totalMin / 60); 
		  minutes = totalMin % 60;
		  if(hours<=9){
			  hours = "0"+hours;
		  }
		  if(minutes<=9){
			  minutes = "0"+minutes;
		  }
		  $("#"+id).val(hours+":"+minutes);
	 }
	 setTotals();
	 checkHours();
}

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
	    		result = result+"Monday ,";
	    	}
	    	if(i == '2'){  
	    		result = result+"Tuesday ,";
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
		$("#timeSheetErrorMsg").removeAttr('style');
		$("#timeSheetErrorMsg").attr('style','display:block;');
		$("#timeSheetErrorMsg").empty();
		$("#timeSheetErrorMsg").append(outPut);
		var error = "<input  type='hidden' name='timesheetHiddenError' id='timesheetHiddenError'  data-validation='required' />";
		$("#hideError").empty();
		$("#hideError").append(error);
	 }else{
	    $("#timeSheetErrorMsg").removeAttr('style');
	    $("#timeSheetErrorMsg").attr('style','display:none;');
		$("#timeSheetErrorMsg").empty();
		$("#hideError").empty();
	} 
	
	$(".time_pop b").click(function(){
		$("#timeSheetErrorMsg").removeAttr('style');
		$("#timeSheetErrorMsg").attr('style','display:none;');
	    $("#timeSheetErrorMsg").empty();
	}); 
	
}
</script>

<script>
/* this script is for calculate total hours */
$(document).click(function(){
	setTotals();
});
		
function setTotals(){ 
	 var total;
	 var billableTotal;
	 var billableSum = 0;
	 var nonBillableTotal;
	 var nonBillableSum = 0;
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
				     
				     var billableType = $("#"+j+"task1IdBillableTypeId").text();
				     if (billableType !== undefined && billableType !=='####' && billableType !=='' && billableType == 'Billable') {
				    	 var billableHours = btemp;
				    	 var billableSplit = billableHours.split(':');
				    	 var billableFirst = billableSplit[0];
				    	 var billableSecond = billableSplit[1];
						 
						 var billableMinute = billableFirst * 60;
						 var billableMinute1 = billableSecond;
						 var billableFinalmin1 = parseInt(billableMinute) + parseInt(billableMinute1);
				    	 
						 billableSum = parseFloat(billableSum) + parseInt(billableFinalmin1);
				    }
				     
				     if (billableType !== undefined && billableType !=='####' && billableType !=='' && billableType == 'Non-Billable') {
				    	 var nonBillableHours = btemp;
				    	 var nonBillableSplit = nonBillableHours.split(':');
				    	 var nonBillableFirst = nonBillableSplit[0];
				    	 var nonBillableSecond = nonBillableSplit[1];
						 
						 var nonBillableMinute = nonBillableFirst * 60;
						 var nonBillableMinute1 = nonBillableSecond;
						 var nonBillableFinalmin1 = parseInt(nonBillableMinute) + parseInt(nonBillableMinute1);
				    	 
						 nonBillableSum = parseFloat(nonBillableSum) + parseInt(nonBillableFinalmin1);
				    }
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
	 
	   //for calculate billable count
	    var t1 = billableSum; 
		var billableHours = Math.floor(t1 / 60); 
		var billableMinutes = t1 % 60;
		
		if (billableMinutes < 10) {
			billableMinutes = '0' + billableMinutes;
		}
		if (billableHours < 10) {
			billableHours = '0' + billableHours;
		}
		if(!isNaN(billableHours) && !isNaN(billableMinutes)){
			billableTotal = billableHours +":"+ billableMinutes;
		}else{
			billableTotal =  "00:00";
		}
	    $("#grandBillableTotal").val(billableTotal);
	    
	    
	  //for calculate non-billable count
	    var t2 = nonBillableSum; 
		var nonBillableHours = Math.floor(t2 / 60); 
		var nonBillableMinutes = t2 % 60;
		
		if (nonBillableMinutes < 10) {
			nonBillableMinutes = '0' + nonBillableMinutes;
		}
		if (nonBillableHours < 10) {
			nonBillableHours = '0' + nonBillableHours;
		}
		if(!isNaN(nonBillableHours) && !isNaN(nonBillableMinutes)){
			nonBillableTotal = nonBillableHours +":"+ nonBillableMinutes;
		}else{
			nonBillableTotal =  "00:00";
		}
	    $("#grandNonBillableTotal").val(nonBillableTotal);
	    
	 grandTotal();		
 }

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
$(document).ready(function() {  
	maskme();    
 });
</script>

<!-- this script is for add row functionality at inline edit screen -->
<script>
function callinlineedit(local){     
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
<body  onclick="bodyClick();">
<section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Timesheets</h2>
          </div>
          <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Timesheets</div>
        </section>
         <div  id="loadTime"></div> 
         <div id="timesheetEntriesDiv"> 
           
         </div>
        
        
        <!-- <div id="timesheetEntriesDivByProjectMemberId">
        		 <div id="timesheetDivByFrequency">
        			 <div id="timesheetDivByTab">
        			 </div>
        		  </div>
        	</div> -->
        
        <!-- ####  this div is for dummy timesheets entries ( useful for add rows )   below   ####-->
				         <div id="divadttTd1" style="display:none;" >   
				             <div> 
				              <select id="REPLACEROWIDtask1Id" name="REPLACEROWIDtask1Id" class="existedProjectTaskIdprojectId" data-validation="required" field-name="Task" onchange="getProjectTasks('REPLACEROWIDtask1Id','projectId')">  
				                          <%--  <c:forEach items="${sourcelead_tasksList}" var="task" varStatus="t">
				                              	<option value="${task.id}">${task.taskName}</option>
				                           </c:forEach> --%>
				             </select>
				             <input type="button" value="" onClick="removeTimeSheetRow('REPLACEROWID')" class="addtaskfield removetaskfield"/>
				             </div>
				             
				        </div>
				        
				        <div id="divadttTd2" style="display:none;">
					    	   <span id="REPLACEROWIDtask1IdBillableTypeId" name="REPLACEROWIDtask1IdBillableTypeId" ></span>	
				   		</div>
				        
				        
				        <div id="divadttTd3" style="display:none;">
					    	   <input type="text" onblur='editMe(this);'   id="REPLACEROWIDtimeSheetDay1Id" name="REPLACEROWIDtimeSheetDay1Id" class="inline_field" placeholder="HH:MM" />	
				               <div class="timesheet_comm">
				                <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment1Id');"></i>
				                <span class="teskcomment" id="REPLACEROWIDcomment1Id" ><textarea name="REPLACEROWIDcomment1Id" id="REPLACEROWIDtextArea1Id" ></textarea></span>
				               </div>
				   		</div>
				        <div id="divadttTd4" style="display:none;">
								  <input type="text" onblur='editMe(this);'   id="REPLACEROWIDtimeSheetDay2Id" name="REPLACEROWIDtimeSheetDay2Id" class="inline_field" placeholder="HH:MM" />	
				                  <div class="timesheet_comm">
				                      <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment2Id');"></i>
				                  <span class="teskcomment" id="REPLACEROWIDcomment2Id" ><textarea name="REPLACEROWIDcomment2Id" id="REPLACEROWIDtextArea2Id" ></textarea></span></div>
				        </div>
				        <div id="divadttTd5" style="display:none;">
								  <input type="text" onblur='editMe(this);'    id="REPLACEROWIDtimeSheetDay3Id" name="REPLACEROWIDtimeSheetDay3Id" class="inline_field" placeholder="HH:MM" />	
				                  <div class="timesheet_comm">
				                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment3Id');"></i>
				                  <span class="teskcomment" id="REPLACEROWIDcomment3Id" ><textarea name="REPLACEROWIDcomment3Id" id="REPLACEROWIDtextArea3Id"></textarea></span></div>
				        </div>
				        <div id="divadttTd6" style="display:none;">
								  <input type="text" onblur='editMe(this);'    id="REPLACEROWIDtimeSheetDay4Id" name="REPLACEROWIDtimeSheetDay4Id" class="inline_field" placeholder="HH:MM" />	
				                  <div class="timesheet_comm">
				                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment4Id');"></i>
				                  <span class="teskcomment" id="REPLACEROWIDcomment4Id" ><textarea name="REPLACEROWIDcomment4Id" id="REPLACEROWIDtextArea4Id"></textarea></span></div>           
				        </div>
				        <div id="divadttTd7" style="display:none;">
								  <input type="text" onblur='editMe(this);'    id="REPLACEROWIDtimeSheetDay5Id" name="REPLACEROWIDtimeSheetDay5Id" class="inline_field" placeholder="HH:MM" />	
				                  <div class="timesheet_comm">
				                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment5Id');"></i>
				                  <span class="teskcomment" id="REPLACEROWIDcomment5Id" ><textarea name="REPLACEROWIDcomment5Id" id="REPLACEROWIDtextArea5Id"></textarea></span></div>  
				    	 </div>
				         <div id="divadttTd8" style="display:none;">
							  <input type="text" onblur='editMe(this);' id="REPLACEROWIDtimeSheetDay6Id" name="REPLACEROWIDtimeSheetDay6Id" class="inline_field" placeholder="HH:MM" />	
				                 <div class="timesheet_comm">
				                 <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment6Id');"></i>
				                 <span class="teskcomment" id="REPLACEROWIDcomment6Id" ><textarea name="REPLACEROWIDcomment6Id" id="REPLACEROWIDtextArea6Id"></textarea></span></div>
				         </div>
				         <div id="divadttTd9" style="display:none;">
							  <input type="text" onblur='editMe(this);' id="REPLACEROWIDtimeSheetDay7Id" name="REPLACEROWIDtimeSheetDay7Id" class="inline_field" placeholder="HH:MM" />	
				                 <div class="timesheet_comm">
				                 <i class="glyphicon glyphicon-comment" onclick="timesheetComment('REPLACEROWIDcomment7Id');"></i>
				                 <span class="teskcomment" id="REPLACEROWIDcomment7Id" ><textarea name="REPLACEROWIDcomment7Id" id="REPLACEROWIDtextArea7Id"></textarea></span></div>            
				    	 </div>
						<!-- #### this div is for dummy timesheets entries ( useful for add rows ) above  ####-->  
						
						<div id="divAttachmentTd1" style="display: none; float: left;">
								<div id='REPLACEROWIDdocFileuploadFileGroup'>
						  			<input type='file' name='REPLACEROWIDdocFile' id='REPLACEROWIDdocFile' class="timesheetAttachmentValidation" onchange='validateUploadFile(REPLACEROWID)'> 
						  			<i class="fa fa-times" onclick="removeAttachmentRow(REPLACEROWID);"></i>
						  		</div>
					    </div>
</section>
<!-- Content Area Ends--> 
<div class="time_pop" id="timeSheetErrorMsg"></div>
</body>

<script type="text/javascript">
function validate_fileupload(fileName)
{

    var allowed_extensions = new Array("ppt", "pptx", "pps", "ppsx", "doc", "docx", "pdf", "rtf", "odt", "xls", "xlsx", "csv", "zip", "txt", "jpg","jpeg","bmp","png","gif","eps","tiff","TGA","psd","svg","msg","wpd","wps");
    var file_extension = fileName.split('.').pop(); 

    for(var i = 0; i <= allowed_extensions.length; i++)
    {
        if(allowed_extensions[i] != null && allowed_extensions[i] != '' && allowed_extensions[i] != 'undefined' && allowed_extensions[i]==file_extension.toLowerCase())
        {
            return true; // valid file extension
        }
    }

    return false;
}
</script>

<script type="text/javascript">
function validateUploadFile(id)
{
	 var selectedFile = $('#'+id+'docFile').val();
	  if(validate_fileupload(selectedFile)){
		   var docSize = $('#'+id+'docFile')[0].files[0].size;
	       $("#"+id+"docFileuploadFileGroup").find('p.jquery_form_error_message').remove();
	      if(docSize > 2097152){
	    	  $('#'+id+'docFileuploadFileGroup').find('p.jquery_form_error_message').remove();
	    	  $('#'+id+'docFileuploadFileGroup').find('i').after("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </span>");
			  $('#'+id+'docFile').attr('record-exist','yes');
			  $('#'+id+'docFile').attr('record-exist-errorMsg','Limit is exceeded than 2MB'); 
	      }else{
	    	 $('input#'+id+'docFile').removeAttr( "record-exist record-exist-errormsg");
   		 	 $('#'+id+'docFileuploadFileGroup').find('p.jquery_form_error_message').remove();
   		 	 fileUpload(""+id+"docFile", "timesheetDocuments", "timesheet_form","","","");
	      }
	  }
	  else{
		      $('#'+id+'docFileuploadFileGroup').find('p.jquery_form_error_message').remove();
		      $('#'+id+'docFileuploadFileGroup').find('i').after("<p class='jquery_form_error_message'>Invalid Format</span>");
			  $('#'+id+'docFile').attr('record-exist','yes');
			  $('#'+id+'docFile').attr('record-exist-errorMsg','Invalid Format'); 
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
      
      
 <script type="text/javascript">
   function alertMessage(message,status,savingType){
   $("#timesheetEntriesDiv").append("<div id='choicepopUp'></div>");
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	  <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='timesheetDbSave(\""+status+"\",\""+savingType+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

  }
</script>

<script type="text/javascript">
function cancel(){
	$("#choicepopUp").remove(); 
	$(".project_inn_mask").fadeOut(200);
    $(".time_btn").removeAttr("disabled");
	$("#loadTime").removeAttr("style"); 
}
</script>
          
<!-- this script is for save Timesheet -->               
<script type="text/javascript">
function newSaveTimesheet(status,savingType){ 
	
	 $(".time_btn").attr("disabled", "disabled");
	 $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	 checkHours();
	 var isTimesheetAttachmentRequired = $("#isTimesheetAttachmentRequired").val();
	 if(isTimesheetAttachmentRequired != null && isTimesheetAttachmentRequired != "" && isTimesheetAttachmentRequired !="undefined" && isTimesheetAttachmentRequired == "Y"){
		 $('#0docFile').removeAttr('data-validation-optional');
		 $('#0docFile').attr("data-validation","required"); 
		 $(".time_btn").removeAttr("disabled");
		 $("#loadTime").removeAttr("style"); 
	 }
	 var unfilledDates = [];
	 $('ul.project-tabs-menu li').each(function() {
		 var attr = $(this).attr('class');
		 if (attr!= null && attr != "" && attr !='undefined' && attr != 'hidden') {}
		 else{
			 var currentInputValue = $(this).prev("input").val();
			 if(currentInputValue != null && currentInputValue !='undefined' && currentInputValue != ''){
				 unfilledDates.push(currentInputValue);
			 }
			
		 }	
     });
	 var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	 }; 
	 if ($('#timesheet_form').validate(false, validationSettings)&&!$("#timeSheetErrorMsg").is(':visible')){ 
		 
		     if(status != null && status != 'undefined' && status != '' && ((status == 'Submitted' && unfilledDates.length === 0) || (status == 'Draft'))){
		    	   timesheetDbSave(status,savingType)
	        	   return true;
		     }
		     else{
		    	 var message = "<b>Are you sure to submit the timesheets, the following frequencies haven't updated<b>";
		    	 for(var i=0;i<unfilledDates.length;i++){
		    		 message = message+"<br/>"+unfilledDates[i];
		    	 }
		    	 alertMessage(message,status,savingType);
		    	 return false;
		     }
              
	 	}else{ 
	 		 $(".time_btn").removeAttr("disabled");
	 		 $("#loadTime").removeAttr("style"); 
			return false;
	   } 
}

/* $('body').on('blur', '#timesheet_form', function() {
	$("#timesheet_form").showHelpOnFocus();
	$("#timesheet_form").validateOnBlur(false, validationSettings);  
}); */
</script>








<script>
 function timesheetDbSave(status,savingType){
	     $("#choicepopUp").empty(); 
	     $("#choicepopUp").remove(); 
		 $(".project_inn_mask").fadeOut(200);
	      var frm = $('#timesheet_form').serializeFormJSON();
          var sourceleadUrl ='${sourceleadUrl}';
		  var mydata = JSON.stringify(frm);
		  mydata = mydata.replace(/[{}]/g, "");
		  mydata = encodeURIComponent(mydata);
		
		   var form = $('#timesheet_form')[0];
		   var formData = new FormData(form); 
		   var submissionIds = $("#submissionId").val();
		   
	   $.ajax({
		    type : "POST",
   	        data: formData,
	        processData: false,
	        contentType:false,
	      	  url: sourceleadUrl+"/newSaveTimesheet?${_csrf.parameterName}=${_csrf.token}&json="+mydata+"&status="+status+"&savingType="+savingType,		
		      success :function(result) {
		    	  $(".time_btn").removeAttr("disabled");
		    	  $("#loadTime").removeAttr("style"); 
		    	  $("html").trigger('click');
		    	  maskme();
		    	  var startDate = $("#tabStartDate").val();
		    	  var endDate = $("#tabEndDate").val();
		    	  if(startDate != null && startDate != '' && startDate != 'undefined' && 
		    			  endDate != null && endDate != '' && endDate != 'undefined'){
		    		  timesheetDivAfterSaving(startDate,endDate,status);
		    	  }
		    	  else{
		    		  timesheetDivByFrequency();
		    	  }
		    	  
		      }
	      	  
	    });
 }
</script>


<!-- this script is for delete document -->
<script type="text/javascript">
function deleteSubmissionDocument(submissionDocumentId){  
    var sourceleadUrl ='${sourceleadUrl}';
    var projectExistingValue = $("#dbIsTimesheetAttachmentRequired").val();
    
    var strongTagCount = document.getElementById('timesheetDocumentsId').getElementsByTagName('strong').length;
    /*  var startdate; */
	var navigation;
    $.ajax({
          type : "POST",
      	  url: sourceleadUrl+"/deleteSubmissionDocument?${_csrf.parameterName}=${_csrf.token}&submissionDocumentId="+submissionDocumentId,		
	      success :function(result) {
	    	  $("#"+submissionDocumentId+"document").remove();
	    	  if(projectExistingValue != null && projectExistingValue != "" && projectExistingValue !="undefined" && projectExistingValue == "1"
	    			  && strongTagCount != null && strongTagCount != "" && strongTagCount !="undefined" && strongTagCount == 1){
	    		  $("#isTimesheetAttachmentRequired").val('Y');
	    	  }
	      }
      	  
    }); 
	   
}
</script>


<script type="text/javascript">
function viewSubmissionDocument(submissionDocumentId,title){
	  var sourceleadUrl ='${sourceleadUrl}';
		window.open(sourceleadUrl+'/viewDocument/'+title+'?submissionDocumentId='+submissionDocumentId,title,"height=600,width=600");
}


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
        			$('.chosen-select').each(function() {
        			var $this = $(this);
        			$this.next().css({'width': '100%'});
        			})
           };
         
            
				
</script> 

<script>
function dropDownExport(){
	$("#exporrtEntries").slideToggle();
}
</script>

<!-- this script is for Timesheet Entries Export -->
<script>
function timesheetEntriesExport(startDate,endDate,projectMemberId,exportType,submissionStatus){
	var sourceleadUrl ='${sourceleadUrl}';
	var status = $("#timesheetSubmissionStatus").val();
	if(status != null && status != 'undefined' && status != '' && status != 'NEW' && status != 'new'){
		window.location.href = sourceleadUrl+"/exportTimeSheet?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+projectMemberId+"&startDate="+startDate+"&endDate="+endDate+"&exportType="+exportType;
	}else{
		alert("Please fill the TimeSheets..");
	}
}
</script>
 <!-- this script is for save Timesheet -->  
<script type="text/javascript" src='resources/js/slead-common.js?staticTime=${staticTime}'></script>   
    