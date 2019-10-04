var locationName, userName;




function acceptOrRejectReopenExpenseBySuperUser(expenseId,tdSpanSize,from){
		 var msg = $("#reject"+expenseId+"message").val(); 
		 var encodeMsg = msg;
		 if(encodeMsg !== ""){
			 cancelRejectExpense(expenseId);
			 var encodeMsg = encodeURIComponent(msg);
			 $(".rejectReasonDiv").empty();
		     var rowHtmlContent =  $("#row"+expenseId).html();
		     
		     /*var locationName =  '${SourceLeadWeb_orgLocations.orgLocationName}';
			 if(locationName == '' || locationName == 'undefined' || locationName == null ){
				 locationName = '';
			 }*/
			 if(tdSpanSize === "-1") {
				 $(".dashboard_loading").show();
				 $(".mask_time").fadeIn(200);
			 } else {
				 $("#row"+expenseId).empty();
				 $("#row"+expenseId).html("<td colspan='"+tdSpanSize+"'  id='loading"+expenseId+"'></td>");
			     $("#loading"+expenseId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;'); 
			 }
		    $.ajax({
		    type: "POST",
		    url: pmsUrl+"/saveExpenseApprovalStatusByExpenseId?"+tokenParamName+"="+tokenValue+"&expenseId="+expenseId+"&approvalStatus=REJECTED"+"&rejectedReason="+(encodeMsg.replace(/\n/g,'<br/>').replace(/\r/g,'<br/>')),       
		    success: function(data) {
		    	if(data.response === "SESSION_TIMEDOUT") {
		    		location.replace(pmsUrl);
		    	 } else if (data.response === "AWKWARD") {
                        location.href = "/errorPage";
		    	 } else if (data.response === "SUCCESS"){ 
			    	if(from != null && from != '' && from != 'undefined' && from == 'dashboard'){
			    		var table = $('#example').DataTable();
				   		table.row('#row'+expenseId).remove().draw( false );
				   } else {
			   		  	  $("#row"+expenseId).empty();
			 	    	  $("#row"+expenseId).html(rowHtmlContent);
			 	    	  $("#status"+expenseId).html("REJECTED");
			 	    	  $("#action"+expenseId).empty();
			 	    	  $("#action"+expenseId).append("<h3 class='reje_color'>Rejected</h3><i class='glyphicon glyphicon-comment' id='"+expenseId+"' onclick='rejectedMessage(\"rejectedMessage"+expenseId+"\");'><div class='app_error_mes' id='rejectedMessage"+expenseId+"'>"+msg+"</div></i><b class='Rname'>By :"+locationName+"</b>");
			   	   }
		    	}else if (result.indexOf('Sign In') !== -1) {
  					location.replace(pmsUrl);
				 }
		      }
		    });
		 }
		 else{
			 if(msg){
					$(".rejectReasonDiv").empty().append("<font color='red' style='padding: 5px 10px;'>Must not Start With Space</font>") ;
				}else{
					$(".rejectReasonDiv").empty().append("<font color='red' style='padding: 5px 10px;'>Reason is required</font>") ; 
				}
			}
	    
	   
	}
    $(document).on("mouseover", ".glyphicon-comment", function() { 
		var localId = $(this).attr('id');
		$("#rejectedMessage"+localId).addClass('app_error_mes_active app_error_mes_full');
		});	
		$(document).on("mouseleave", ".glyphicon-comment", function() { 
		var localId = $(this).attr('id');
		$("#rejectedMessage"+localId).removeClass('app_error_mes_active  app_error_mes_full');
    });	
	function rejectedMessage(local){ 
	   $("#"+local).slideToggle(200);
	   $(".timesheet_appr_table .glyphicon strong, .timesheet_appr_table .glyphicon").click(function(e) {
	       e.stopPropagation();
	   });	
	   
	}
	
	function cancelRejectExpense(expenseId){
			 $(".mask_time").fadeOut(200);
			 $("#reject"+expenseId+"panel").slideUp(200);
			 window.location.reload();
	}
	function rejectExpenseByExpenseId(expenseId,tdSpanSize,from){ 
	    $(".rejectReasonDiv").empty();
	    $("#reject"+expenseId+"message").val(''); 
	    var result = "";
	    result = result +'<div class="clearfix" id="reject'+expenseId+'panel" style="top:25%">';
		result = result +'<textarea placeholder="Enter comment here" id="reject'+expenseId+'message" "></textarea>';
		result = result + ' <div id="rejectReasonDiv" class="rejectReasonDiv mb5"> </div>';
		result = result +'<a href="#" onClick="removeEmptyLines($(\'#reject'+expenseId+'message\'));acceptOrRejectReopenExpenseBySuperUser(\''+expenseId+'\','+tdSpanSize+',\''+from+'\');" >Submit</a><a class="close_color" href="#" onClick="cancelRejectExpense(\''+expenseId+'\');">Cancel</a>';
		result = result +'</div>';
		result = result +'</div>';
		$("#rejectedPopup").append(result);
	    $(".mask_time").fadeIn(200); 
	    $("#rejectedPopup").fadeIn(0);
	    $("#reject"+expenseId+"panel").fadeIn(0);
		
	}

	// close popup
	$(document).ready(function() {
	    $(".timesheet_appr_table .fa-times").click(function(){
			$(".mask_time").fadeIn(200);
			$(".time_appr_rejct_pop").slideDown(200);
		});
	    $(".time_appr_rejct_pop a").click(function(){
			$(".mask_time").fadeOut(200);
			$(".time_appr_rejct_pop").slideUp(200);
		});	
	});
   
  
	
	
  function approveExpenseByExpenseId(expenseId,tdSpanSize,from,source){
		$("#timesheetApprList").empty();
	    /*var locationName =  '${SourceLeadWeb_orgLocations.orgLocationName}';
		 if(locationName == '' || locationName == 'undefined' || locationName == null ){
			 locationName = '';
		 }*/
		if(tdSpanSize === "-1") {
			 $(".dashboard_loading").show();
		 } else {
			 var rowHtmlContent =  $("#row"+expenseId).html();
			 $("#row"+expenseId).empty();
			 $("#row"+expenseId).html("<td colspan='"+tdSpanSize+"'  id='loading"+expenseId+"'></td>");
			 $("#loading"+expenseId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat; ');
		 }
		
		 
	    var rejectedReason = '' ;
	     $.ajax({
	    type: "POST",
	    url: pmsUrl+"/saveExpenseApprovalStatusByExpenseId?"+tokenParamName+"="+tokenValue+"&expenseId="+expenseId+"&approvalStatus=APPROVED&rejectedReason="+rejectedReason,        
		    success: function(data) {
		    	if(data.response === "SESSION_TIMEDOUT") {
		    		location.replace(pmsUrl);
		    	 } else if (data.response === "AWKWARD") {
                        location.href = "/errorPage";
		    	 } else if (data.response === "SUCCESS"){
		    		 if(source === "list") {
			    		 if(from != null && from != '' && from != 'undefined' && from == 'dashboard'){
					    	 var table = $('#example').DataTable();
					    	 table.row('#row'+expenseId).remove().draw( false );
			        	 } else{
			        		 $("#row"+expenseId).empty();
					    	 $("#row"+expenseId).html(rowHtmlContent);
					    	 $("#status"+expenseId).html("APPROVED");
					    	 $("#action"+expenseId).html("<h3>Approved</h3><b class='Rname'>By :"+userName+"</b>"); 
			        	 }
		    		 } else {
		    			 location.reload();
		    		 }
		    	}else if (result.indexOf('Sign In') !== -1) {
  					location.replace(pmsUrl);
				 }
		    	 
		    }
	    });  
}
