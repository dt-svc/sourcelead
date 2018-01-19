<script>
$(document).ready(function() {
    $(".glyphicon-comment").mouseenter(function(){
		$(".app_error_mes").addClass("app_error_mes_active");
	});
    $(".glyphicon-comment").mouseleave(function(){
		$(".app_error_mes").removeClass("app_error_mes_active");
	});	
});
</script>
<div class="time_appr_side_wrap">
	<div class="time_appr_side_wrap_inn">
	 	 <form id="timesheet_form" name="timesheet_form"> 
		 	<input type="hidden" id="projectMemberId" name="projectMemberId" value="${timesheetEntryDetailsDTO.projectMemberId}"/>
	        <input type="hidden" id="projectId" name="projectId" value="${timesheetEntryDetailsDTO.projectId}"/>
	        <input type="hidden" id="masterCandidateId" name="masterCandidateId" value="${timesheetEntryDetailsDTO.masterCandidateId}"/>
	        <input type="hidden" id="currentTimeFrequency" name="currentTimeFrequency" value="${timesheetEntryDetailsDTO.currentTimeFrequency}"/>
            <input type="hidden" id="timesheetFor" name="timesheetFor" value="approver"/>
            <input type="hidden" name="totalHours" id="totalHours" >
            <div id="timesheetDivByFrequency">
	 	        <jsp:include page="timeSheetSubmission.jsp"></jsp:include>
	 	    </div>
	 	  </form>
	 	
	</div>
</div>	
<div id="loadTime"></div>
<div id='rejectedPopup'></div>
<div id='reOpenPopup'></div>
<div id='reOpenRejectedPopup'></div>
<div class="side_mask"><div class="proj_close" onclick="timesheetApprovalTableRowByProjectMemberId('${timesheetApprovalDTO.weekFrequency}','${timesheetSubmission.projectMemberId}','approverClose');"></div></div>	
<div class="mask_time"></div>
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
<script>
function timesheetApprovalPopUpByProjectMemberId(currentTimeFrequency,projectId,projectMemberId,masterCandidateId,totalHours,from){
	  var tableFor = $("#tableFor").val();
	 var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	 $("#projectMemberId").val('');
	 $("#projectMemberId").val(projectMemberId);
	 
	 $("#currentTimeFrequency").val('');
	 $("#currentTimeFrequency").val(currentTimeFrequency);
	 
	 $("#projectId").val('');
	 $("#projectId").val(projectId);
	 
	 $("#masterCandidateId").val('');
	 $("#masterCandidateId").val(masterCandidateId);
	 
	 $("#totalHours").val('');
	 $("#totalHours").val(totalHours);
     $.ajax({
	          type : "POST",
	      	  url: sourceleadUrl+"/getTimesheetSubmissionDetails?projectMembersId="+projectMemberId+"&currentTimeFrequency="+currentTimeFrequency+"&timeSheetsFor=approver",		
		      success :function(data) {
		    	  $("#timesheetDivByFrequency").empty();
	 	    	     var closeElement = $('.proj_close');
	 	    	     closeElement.removeAttr('onclick');
	 	    	     closeElement.attr('onclick','timesheetApprovalTableRowByProjectMemberId("'+currentTimeFrequency+'","'+projectMemberId+'","'+tableFor+'Close");'); 
				     $("#timesheetDivByFrequency").append(data);
				     if($("#timesheet_form input[data-id*=docFile]").length > 0) {
				 		$("#timesheet_form input[data-id*=docFile]").remove();
				 	 }
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
				     maskme();
		      }
	    }); 
} 
</script>
<script>
function rejecteSubmissionByProjectMemberId(projectMemberId,startDate,endDate,tdSpanSize,from){ 
    $("#rejectReasonDiv").empty();
    $("#reject"+projectMemberId+"message").val(''); 
    var result = "";
    result = result +'<div class="time_appr_rejct_pop" id="reject'+projectMemberId+'panel" style="top:25%">';
	result = result +'<textarea placeholder="Enter comment here" id="reject'+projectMemberId+'message" ></textarea>';
	result = result +'<div id="rejectReasonDiv"/>';
	result = result +'<a href="#" onClick="removeEmptyLines($(\'#reject'+projectMemberId+'message\'));saveRejectSubmissionByProjectMemberId(\''+projectMemberId+'\',\''+startDate+'\',\''+endDate+'\','+tdSpanSize+',\''+from+'\');" >Submit</a><a class="close_color" href="#" onClick="cancelRejectSubmission(\''+projectMemberId+'\');">Cancel</a>';
	result = result +'</div>';
	result = result +'</div>';
	$("#rejectedPopup").append(result);
   $(".mask_time").fadeIn(200); 
$("#reject"+projectMemberId+"panel").slideToggle(200);
	
}
function approveSubmissionByProjectMemberId(projectMemberId,startDate,endDate,tdSpanSize,from){
	 var rowHtmlContent =  $("#row"+projectMemberId).html();
	 var locationName =  '${SourceLeadWeb_orgLocations.orgLocationName}';
	 if(locationName == '' || locationName == 'undefined' || locationName == null ){
		 locationName = '';
	 }
	 var tableFor = $("#tableFor").val();
	$("#row"+projectMemberId).empty();
	$("#row"+projectMemberId).html("<td colspan='"+tdSpanSize+"'  id='loading"+projectMemberId+"'></td>");
    $("#loading"+projectMemberId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat; '); 
    var rejectedReason = '';
    var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
     $.ajax({
    type: "POST",
    url: sourceleadUrl+"/saveSubmissionApprovalStatusByProjectMemberId?&projectMemberId="+projectMemberId+"&startDate="+startDate+"&endDate="+endDate+"&approvalStatus=APPROVED&rejectedReason="+rejectedReason,        
	    success: function(data) {
        	 if(tableFor != null && tableFor != '' && tableFor != 'undefined' && tableFor == 'dashboard'){
        		 var currentPageNumber =  $("#currentPageNumber").val();
        		 getStatusListData(""+currentPageNumber+"","");
        	 }
        	 else{
        		 $("#row"+projectMemberId).empty();
		    	 $("#row"+projectMemberId).html(rowHtmlContent);
		    	 $("#status"+projectMemberId).html("Approved");
		    	 $("#action"+projectMemberId).html("<h3>Approved</h3><b class='Rname'>By :"+locationName+"</b>"); 
        	   }
	    	 
	    }
    });   
}
</script>

<script>
function saveRejectSubmissionByProjectMemberId(projectMemberId,startDate,endDate,tdSpanSize,from){
	 var msg = $("#reject"+projectMemberId+"message").val(); 
	 var tableFor = $("#tableFor").val();
	 var encodeMsg = msg;
	 if(encodeMsg !== ""){
		 var encodeMsg = encodeURIComponent(msg);
		 $("#rejectReasonDiv").empty();
		 var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	     var rowHtmlContent =  $("#row"+projectMemberId).html();
	     
	     var locationName =  '${SourceLeadWeb_orgLocations.orgLocationName}';
		 if(locationName == '' || locationName == 'undefined' || locationName == null ){
			 locationName = '';
		 }
		 $("#row"+projectMemberId).empty();
		 $("#row"+projectMemberId).html("<td colspan='"+tdSpanSize+"'  id='loading"+projectMemberId+"'></td>");
	     $("#loading"+projectMemberId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;'); 
	    $.ajax({
	    type: "POST",
	    url: sourceleadUrl+"/saveSubmissionApprovalStatusByProjectMemberId?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+projectMemberId+"&startDate="+startDate+"&endDate="+endDate+"&rejectedReason="+(encodeMsg.replace(/\n/g,'<br/>').replace(/\r/g,'<br/>'))+"&approvalStatus=REJECTED",       
	    success: function(data) { 
	    	
	   	   // approvalList();
	       if(tableFor != null && tableFor != '' && tableFor != 'undefined' && tableFor == 'dashboard'){
	    	     var currentPageNumber =  $("#currentPageNumber").val();
      		     getStatusListData(""+currentPageNumber+"","");
		   }
	   	   else{
	   		  	  $("#row"+projectMemberId).empty();
	 	    	  $("#row"+projectMemberId).html(rowHtmlContent);
	 	    	  $("#status"+projectMemberId).html("Rejected");
	 	    	  $("#action"+projectMemberId).empty();
	 	    	  $("#action"+projectMemberId).append("<h3 class='reje_color'>Rejected</h3><i class='glyphicon glyphicon-comment' id='"+projectMemberId+"' onclick='rejectedMessage(\"rejectedMessage"+projectMemberId+"\");'><div class='app_error_mes' id='rejectedMessage"+projectMemberId+"'>"+msg+"</div></i><b class='Rname'>By :"+locationName+"</b>");
	   	   }
	   	   cancelRejectSubmission(projectMemberId);
	   	    pagination();
	   	   
	      }
	    }); 
	 }else{
		 if(msg){
				$("#rejectReasonDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Must not Start With Space</font>") ;
			}else{
				$("#rejectReasonDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
			}
		}
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
</script>