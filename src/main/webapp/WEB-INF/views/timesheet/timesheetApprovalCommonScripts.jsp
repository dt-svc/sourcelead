<input type="hidden" id="timesheetApproverCurrentPageNumber">
<!-- Hidden Div for Timesheet Entry Details popup from timesheet approver side (it can be invoke either from TimesheetApproval list or Dashboardcount list)  -->
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
                <!-- inner jsp for timesheetentry details   -->
	 	        <jsp:include page="timesheetApprovalSubmission.jsp"></jsp:include>
	 	    </div>
	 	  </form>
	 	
	</div>
</div>	
<!-- Hidden div's for loading   -->
<div id="loadTime"></div>
<div id="loadTime1"></div>

<!-- Hidden div for reject reason popup -->
<div id='rejectedPopup'></div>

<!-- Hidden div for reopen popup -->
<div id='reOpenPopup'></div>

<!-- Hidden div for reopen-reject reason popup -->
<div id='reOpenRejectedPopup'></div>

<!-- Hidden div timesheet entries popup close button -->
<div class="side_mask"><div class="proj_close" onclick="timesheetApprovalTableRowByProjectMemberId('${timesheetApprovalDTO.weekFrequency}','${timesheetSubmission.projectMemberId}','approverClose');"></div></div>	
<div class="mask_time"></div>


<script>
$(document).ready(function() {
    $(".glyphicon-comment").mouseenter(function(){
		$("#rejectedMessage${timesheetEntryDetailsDTO.projectMemberId}").addClass("app_error_mes_active");
	});
    $(".glyphicon-comment").mouseleave(function(){
    	$("#rejectedMessage${timesheetEntryDetailsDTO.projectMemberId}").removeClass("app_error_mes_active");
	});	
});
</script>



<!-- Getting Timesheet Entry Details from timesheet approver side (it can be invoke either from TimesheetApproval list or Dashboardcount list)  -->
<script>
function timesheetApprovalPopUpByProjectMemberId(currentTimeFrequency,projectId,projectMemberId,masterCandidateId,totalHours,from,requestFrom){
	 $('#hedderAcceptRejectButton').hide();
	 $('#selectAllTimeSheetCheckBoxForSubmitForApproval').removeProp('checked');
     $(".checkBoxForSubmitForApproval").each(function () {
    	$(this).removeProp('checked');
     }); 
	if(requestFrom == "notifyLink"){
		  tableFor = from;
	 }else{
		  tableFor = $("#tableFor").val();
	 }
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
	      	  url: sourceleadUrl+"/getTimesheetSubmissionDetails?projectMembersId="+projectMemberId+"&currentTimeFrequency="+currentTimeFrequency+"&redirectionRequestFrom="+requestFrom+"&timeSheetsFor=approver",		
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
				     setTotals();
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
     	selectedRecords = {};
		$(".checkBoxForSubmitForApproval").click(function () {
	    	selectedRecords[$(this).val()] = $(this).is(":checked");
	    });

		$('.selectpicker').selectpicker('refresh');
		$('.existedProjectTaskId'+projectId).selectpicker('refresh');
}	
</script>



<!-- Approving submitted timesheet from timesheet approver side (it can be invoke either from TimesheetApproval list or Dashboardcount list)  -->  
<script>
function approveSubmissionByProjectMemberId(projectMemberId,startDate,endDate,tdSpanSize,from){
	 var rowHtmlContent =  $("#row"+projectMemberId).html();
	 var userName =  '${SourceLeadWeb_userName}';
	 var locationName =  '${SourceLeadWeb_orgName}';
	 var actionPerfomedBy = '';
	 if(userName == '' || userName == 'undefined' || userName == null ){
		 userName = '';
	 }else if(locationName == '' || locationName == 'undefined' || locationName == null ){
		 actionPerfomedBy = userName;
	 }else{
		 actionPerfomedBy = userName+'('+locationName+')';
	 }
	 var tableFor = $("#tableFor").val();
	$("#row"+projectMemberId).empty();
	$("#row"+projectMemberId).html("<td colspan='"+tdSpanSize+"'  id='loading"+projectMemberId+"'></td>");
    $("#loading"+projectMemberId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat; '); 
    var rejectedReason = '';
    var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
    $('#hedderAcceptRejectButton').hide();
    $('#selectAllTimeSheetCheckBoxForSubmitForApproval').removeProp('checked');
     $.ajax({
    type: "POST",
    url: sourceleadUrl+"/saveSubmissionApprovalStatusByProjectMemberId?&projectMemberId="+projectMemberId+"&startDate="+startDate+"&endDate="+endDate+"&approvalStatus=APPROVED&rejectedReason="+rejectedReason,        
	    success: function(data) {
	    	 if (data.response === "AWKWARD") {
                    location.href = "/errorPage";
	    	 } else if (data.response === "SUCCESS") {
	    		 if(tableFor != null && tableFor != '' && tableFor != 'undefined' && tableFor == 'dashboard' && from != null && from != '' && from != 'undefined' && from != 'approvalSummary'){
	        		 var currentPageNumber =  $("#currentPageNumber").val();
	        		 getStatusListData(""+currentPageNumber+"",0,"");
	        	 } else if(from != null && from != '' && from != 'undefined' && from == 'approvalSummary'){
	        		 var currentTimeFrequency = startDate+"To"+endDate;
	        		 timesheetApprovalTableRowByProjectMemberId(currentTimeFrequency,projectMemberId,tableFor);
	        	 }
	        	 else{
	        		 $("#row"+projectMemberId).empty();
			    	 $("#row"+projectMemberId).html(rowHtmlContent);
			    	 $("#status"+projectMemberId).html("Approved");
			    	 $("#action"+projectMemberId).html("<h3>Approved</h3><b class='Rname'>By :"+actionPerfomedBy+"</b>");
			    	 $("#row"+projectMemberId).each(function() {
			    		   $(this).find('input:checkbox:first').remove();
			    		   var banIcon = '<small class="positionRelative"><i class="fa fa-ban text-danger"></i></small>';
			    		   $(this).find('td:first-child').html(banIcon);
         	        });
	        	   }
	    	 }else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
			 }
        	 
	    	 
	    }
    });   
}
</script>

<!-- Rejecting submitted timesheet from timesheet approver side (it can be invoke either from TimesheetApproval list or Dashboardcount list)  -->
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
function saveRejectSubmissionByProjectMemberId(projectMemberId,startDate,endDate,tdSpanSize,from,reason){
	 var msg = ''; 
	if(reason != null && reason != 'undefined' && reason != ''){
		 msg = reason;
	 }else {
		 msg = $("#reject"+projectMemberId+"message").val();
	 }
	 var tableFor = $("#tableFor").val();
	 var encodeMsg = msg;
	 if(encodeMsg != null && encodeMsg != '' && encodeMsg != 'undefined'){
		 var encodeMsg = encodeURIComponent(msg);
		 $("#rejectReasonDiv").empty();
		 var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	     var rowHtmlContent =  $("#row"+projectMemberId).html();
	     
	     var userName =  '${SourceLeadWeb_userName}';
		 var locationName =  '${SourceLeadWeb_orgName}';
		 var actionPerfomedBy = '';
		 if(userName == '' || userName == 'undefined' || userName == null ){
			 userName = '';
		 }else if(locationName == '' || locationName == 'undefined' || locationName == null ){
			 actionPerfomedBy = userName;
		 }else{
			 actionPerfomedBy = userName+'('+locationName+')';
		 }
		 $("#row"+projectMemberId).empty();
		 $("#row"+projectMemberId).html("<td colspan='"+tdSpanSize+"'  id='loading"+projectMemberId+"'></td>");
		 $('#hedderAcceptRejectButton').hide();
		 $('#selectAllTimeSheetCheckBoxForSubmitForApproval').removeProp('checked');
		 $("#loading"+projectMemberId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;'); 
	    $.ajax({
	    type: "POST",
	    url: sourceleadUrl+"/saveSubmissionApprovalStatusByProjectMemberId?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+projectMemberId+"&startDate="+startDate+"&endDate="+endDate+"&rejectedReason="+(encodeMsg.replace(/\n/g,'<br/>').replace(/\r/g,'<br/>'))+"&approvalStatus=REJECTED",       
	    success: function(data) { 
	    	 if (data.response === "AWKWARD") {
                   location.href = "/errorPage";
	    	 } else if (data.response === "SUCCESS") {
	  	        if(tableFor != null && tableFor != '' && tableFor != 'undefined' && tableFor == 'dashboard'){
	  	    	     var currentPageNumber =  $("#currentPageNumber").val();
	        		     getStatusListData(""+currentPageNumber+"",0,"");
	  		    }
	  	   	    else{
	  	   		  	  $("#row"+projectMemberId).empty();
	  	 	    	  $("#row"+projectMemberId).html(rowHtmlContent);
	  	 	    	  $("#status"+projectMemberId).html("Rejected");
	  	 	    	  $("#action"+projectMemberId).empty();
	  	 	    	  $("#action"+projectMemberId).append("<h3 class='reje_color'>Rejected</h3><i class='glyphicon glyphicon-comment' id='"+projectMemberId+"' onclick='rejectedMessage(\"rejectedMessage"+projectMemberId+"\");'><div class='app_error_mes' id='rejectedMessage"+projectMemberId+"'>"+msg+"</div></i><b class='Rname'>By :"+actionPerfomedBy+"</b>");
	  	 	    	  $("#row"+projectMemberId).each(function() {
			    		   $(this).find('input:checkbox:first').remove();
			    		   var banIcon = '<small class="positionRelative"><i class="fa fa-ban text-danger"></i></small>';
			    		   $(this).find('td:first-child').html(banIcon);
      	        	  });
	  	   	     }
	  	   	     cancelRejectSubmission(projectMemberId);
	  	   	    pagination();
	    	 }else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
			 }
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

<!-- Showing reject reason's on mouseover -->
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


<!-- Bulk Approving submitted timesheets from timesheet approver side (it can be invoke either from TimesheetApproval list or Dashboardcount list)  -->  
<script>
function acceptTimeSheetSubmissionBySubmissionId(tableFor){
	var submissionIDs = [];
	for(var e in selectedRecords) {
		if(selectedRecords[e]){
			submissionIDs.push(e);
		}
	}
	var data = submissionIDs.toString().split(',');
	if(data.length > 0){
	for(var i=0; i<=data.length-1;i++){
		var data2 = data[i].toString().split('!');
		var projectMemberId = data2[0];
		var startDate = data2[1];
		var endDate = data2[2];
		var rejectedReason = '';
		var rowHtmlContent =  $("#row"+projectMemberId).html();
		 var userName =  '${SourceLeadWeb_userName}';
		 var locationName =  '${SourceLeadWeb_orgName}';
		 var actionPerfomedBy = '';
		 if(userName == '' || userName == 'undefined' || userName == null ){
			 userName = '';
		 }else if(locationName == '' || locationName == 'undefined' || locationName == null ){
			 actionPerfomedBy = userName;
		 }else{
			 actionPerfomedBy = userName+'('+locationName+')';
		 }
		 $("#loadingCotentDiv").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
		     $.ajax({
		    type: "POST",
		    url: sourceleadUrl+"/saveSubmissionApprovalStatusByProjectMemberId?&projectMemberId="+projectMemberId+"&startDate="+startDate+"&endDate="+endDate+"&approvalStatus=APPROVED&rejectedReason="+rejectedReason,        
			    success: function(data) {
			    	 if (data.response === "AWKWARD") {
		                    location.href = "/errorPage";
			    	 } else if (data.response === "SUCCESS") {
			    	if(data != null && data !='' && data !='undefined'){
		    			if(tableFor != null && tableFor != '' && tableFor != 'undefined' && tableFor == 'dashboard'){
		    				 var currentPageNumber =  $("#currentPageNumber").val();
		    				 getStatusListData(""+currentPageNumber+"",0,"");
		    				 $('#hedderAcceptRejectButton').attr('style','display:none');
		    			 }else{
		    				 var currentPageNumberApprover =  $("#timesheetApproverCurrentPageNumber").val();
		    				 getTableData(currentPageNumberApprover,0);
		    			   	$("#loadingCotentDiv").removeAttr('style');
		    			   	$('#hedderAcceptRejectButton').attr('style','display:none');
			    		}
		    			selectedRecords = {};
		    			$(".checkBoxForSubmitForApproval").click(function () {
	             	    	selectedRecords[$(this).val()] = $(this).is(":checked");
	             	    });
			    	}
			    }else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				 }
			  }
		  });  
		
	}
 }
}
</script>

<!-- Bulk Rejecting submitted timesheets from timesheet approver side (it can be invoke either from TimesheetApproval list or Dashboardcount list)  -->  
<script>
function rejectTimeSheetSubmissionBySubmissionId(tableFor){
	$("#rejectReasonDiv").empty();
    $("#rejectmessage").val(''); 
    var result = "";
    result = result +'<div class="time_appr_rejct_pop" id="rejectpanel" style="top:25%">';
	result = result +'<textarea placeholder="Enter comment here" id="rejectmessage"></textarea>';
	result = result +'<div id="rejectReasonDiv"/>';
	result = result +'<a href="#" onClick="saveRejectSubmissionByProjectMemberIdDashboard(\''+tableFor+'\');" >Submit</a><a class="close_color" href="#" onClick="closepopUpRejectReason();">Cancel</a>';
	
	result = result +'</div>';
	result = result +'</div>';
	$("#rejectedPopup").append(result);
    $(".mask_time").fadeIn(200); 
	$("#rejectpanel").show();
	
	
}

function saveRejectSubmissionByProjectMemberIdDashboard(tableFor){
	var submissionIDs = [];
	for(var e in selectedRecords) {
		if(selectedRecords[e]){
			submissionIDs.push(e);
		}
	}
	var dataOne = submissionIDs.toString().split(',');
	if(dataOne.length > 0){
	for(var i=0; i<=dataOne.length-1;i++){
		var dataTwo = dataOne[i].split('!');
		var projectMemberId = dataTwo[0];
		var startDate = dataTwo[1];
		var endDate = dataTwo[2];
		var tdSpanSize =dataTwo[3];
		var from =dataTwo[4];
	 	var msg = $("#rejectmessage").val(); 
		var encodeMsg = msg;
	 	if(encodeMsg !== ""){
		var encodeMsg = encodeURIComponent(msg);
		$("#rejectReasonDiv").empty();
		var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	    var rowHtmlContent =  $("#row"+projectMemberId).html();
	    var userName =  '${SourceLeadWeb_userName}';
		 var locationName =  '${SourceLeadWeb_orgName}';
		 var actionPerfomedBy = '';
		 if(userName == '' || userName == 'undefined' || userName == null ){
			 userName = '';
		 }else if(locationName == '' || locationName == 'undefined' || locationName == null ){
			 actionPerfomedBy = userName;
		 }else{
			 actionPerfomedBy = userName+'('+locationName+')';
		 }
		$("#loadingCotentDiv").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;'); 
	    $.ajax({
	    type: "POST",
	    url: sourceleadUrl+"/saveSubmissionApprovalStatusByProjectMemberId?&projectMemberId="+projectMemberId+"&startDate="+startDate+"&endDate="+endDate+"&rejectedReason="+(encodeMsg.replace(/\n/g,'<br/>').replace(/\r/g,'<br/>'))+"&approvalStatus=REJECTED",       
	    success: function(data) { 
	    	 if (data.response === "AWKWARD") {
                 location.href = "/errorPage";
	    	 } else if (data.response === "SUCCESS") {   
		    	if(tableFor != null && tableFor != '' && tableFor != 'undefined' && tableFor == 'dashboard'){
			    	     var currentPageNumber =  $("#currentPageNumber").val();
		      		     getStatusListData(""+currentPageNumber+"",0,"");
		      		   	 $(".mask_time").fadeOut(200);
		      		     $("#rejectpanel").hide();
		      		  	 $('#hedderAcceptRejectButton').attr('style','display:none');
		      		   	 
				   }else{
						  var currentPageNumberApproverReject =  $("#timesheetApproverCurrentPageNumber").val();
						  getTableData(currentPageNumberApproverReject);
		    			  $("#loadingCotentDiv").removeAttr('style');
			 	    	  $(".mask_time").fadeOut(200);
		      		      $("#rejectpanel").hide();
		      		      $('#hedderAcceptRejectButton').attr('style','display:none');
			   	   }
			       selectedRecords = {};
	   				$(".checkBoxForSubmitForApproval").click(function () {
	        	    	selectedRecords[$(this).val()] = $(this).is(":checked");
	        	    });
		      }else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
			  }
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
  }
}
</script>
<script>
function closepopUpRejectReason(){
	$("#rejectmessage").val('');
	$("#rejectpanel").hide();
	$(".mask_time").fadeOut(200);
}
</script>
