<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script>
$(document).ready(function() {
	pagination();
    $(".glyphicon-comment").mouseenter(function(){
		$(".app_error_mes").addClass("app_error_mes_active");
	});
    $(".glyphicon-comment").mouseleave(function(){
		$(".app_error_mes").removeClass("app_error_mes_active");
	});	
});
</script>



<div id='rejectedPopup'></div>
<div class="mask_time"></div>
 <table id='example' class='display' cellspacing='0' width='100%' >
	<thead> 
    <tr> 
    <th>Employee</th>
	<th>Employment Type</th>
	<th>Project</th>
	<th>Client</th>
		<c:forEach var="weekFrequency" items="${timesheetApprovalDTO.dataTableWeekDateList}" >
			<th>${weekFrequency}</th>
		</c:forEach>
	<th>Total</th>
	<th>Stage</th>
	<th>Action</th>
    </tr> 
    </thead>
    <tbody>
    	 <jsp:include page="timesheetApprovalTableData.jsp"></jsp:include>
   </tbody>
  </table>
  

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
	 var rowHtmlContent =  $("#row"+projectMemberId).innerHTML;
	 var locationName =  '${SourceLeadWeb_orgLocations.orgLocationName}';
	 if(locationName == '' || locationName == 'undefined' || locationName == null ){
		 locationName = '';
	 }
	$("#row"+projectMemberId).empty();
	$("#row"+projectMemberId).html("<td colspan='"+tdSpanSize+"'  id='loading"+projectMemberId+"'></td>");
    $("#loading"+projectMemberId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat; '); 
    var rejectedReason = '';
    var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
     $.ajax({
    type: "POST",
    url: sourceleadUrl+"/saveSubmissionApprovalStatusByProjectMemberId?&projectMemberId="+projectMemberId+"&startDate="+startDate+"&endDate="+endDate+"&approvalStatus=APPROVED&rejectedReason="+rejectedReason,        
	    success: function(data) {
        	 if(from != null && from != '' && from != 'undefined' && from == 'dashboard'){
		    	 var table = $('#example').DataTable();
		    	 table.row('#row'+projectMemberId+startDate+'To'+endDate+'').remove().draw( false );
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
	     $("#loading"+projectMemberId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;'); 
	    $.ajax({
	    type: "POST",
	    url: sourceleadUrl+"/saveSubmissionApprovalStatusByProjectMemberId?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+projectMemberId+"&startDate="+startDate+"&endDate="+endDate+"&rejectedReason="+(encodeMsg.replace(/\n/g,'<br/>').replace(/\r/g,'<br/>'))+"&approvalStatus=REJECTED",       
	    success: function(data) { 
	    	
	   	   // approvalList();
	       if(from != null && from != '' && from != 'undefined' && from == 'dashboard'){
	    	   var table = $('#example').DataTable();
		   		 table.row('#row'+projectMemberId+startDate+'To'+endDate+'').remove().draw( false );
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

<script>
function exportTimeSheetToPdfOrExcel(exportType){
	 	var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
		var frequencyMonth = $("#frequencyMonth").val();
		var timesheetFrequency = $('#timesheetFrequency').val();
		var stage = $('#stage').val();
		var client = $('#client').val();
		var weekFrequency = $('#weekFrequency').val();
		if(weekFrequency != null && weekFrequency !='undefined'){
		var date = weekFrequency.split("To");
		var startDate = date[0];
		var endDate = date[1];
		}
		window.location.href = sourceleadUrl
				+ "/getExportTimeSheetForApprovalMechanizm?&startDate="
				+ startDate + "&endDate=" + endDate + "&client=" + client
				+ "&stage=" + stage + "&timesheetFrequency="
				+ timesheetFrequency +"&frequencyMonth="+frequencyMonth+"&weekFrequency="+weekFrequency+"&exportType=" + exportType;
	}
</script>

<script>
 function dropDown(){
	$("#exporrt").slideToggle(200);
} 
 $("html").click(function() {
     $("#exporrt").slideUp(100);
 });
 $("#exporrt, .dropexport").click(function(e) {
     e.stopPropagation();
 });
</script>  


<script>
function pagination(){ 
    $('#example').DataTable( {
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
    		"sSearch": "",
    		"sZeroRecords": "", "sEmptyTable": ""
    	}
    });
    $('#example').wrap("<div class='overflow_Auto width100percnt'></div>");
}
</script>
 
