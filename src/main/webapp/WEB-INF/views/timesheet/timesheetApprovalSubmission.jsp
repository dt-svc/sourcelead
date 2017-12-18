<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script src="/resources/js/jquery.twbsPagination.js"></script>

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
	        <input type="hidden" id="currentTimeFrequency" name="currentTimeFrequency" value="${timesheetEntryDetailsDTO.masterCandidateId}"/>
            <input type="hidden" id="timesheetFor" name="timesheetFor" value="approver"/>
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
<div id="tableContent" class="timesheetRep-table timesheet_appr_table" style="overflow: auto;">
 <table id='exampleApprover' class='display'>
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
    <tbody id="approvalTableData">
    	 <jsp:include page="timesheetApprovalTableData.jsp"></jsp:include>
   </tbody>
  </table>
</div>
<div id= "paginationDiv" class="clearfix">
	<div class="col-md-6">
		<div class="pull-left mt5">
			<label class="pull-left"><span class="pull-left mt10 mr5">Show</span>
				<div class="dropdown-select pull-left mr5 tblminwidth-50">
					<select id="pageSize" onchange="getTableDataByPageSize()">
						<option value="5" >5</option>
						<option value="10" >10</option>
						<option value="15" >15</option>
						<option value="20" >20</option>
					</select>
				</div> <span class="pull-left mt10">entries</span> </label>
				 <span class="pull-left mt10 ml" id="pageMsg">Showing 1 to 1 of 1 entries</span>
		</div>
	</div>
	<div id="paginationholder" class="col-md-6 pagiPad">
		<ul id="pagination-demo" class="pagination-sm pull-right pagination"></ul>
	</div>
</div>


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
function timesheetApprovalPopUpByProjectMemberId(currentTimeFrequency,projectId,projectMemberId,masterCandidateId,from){
	 var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	 $("#projectMemberId").val('');
	 $("#projectMemberId").val(projectMemberId);
	 
	 $("#currentTimeFrequency").val('');
	 $("#currentTimeFrequency").val(currentTimeFrequency);
	 
	 $("#projectId").val('');
	 $("#projectId").val(projectId);
	 
	 $("#masterCandidateId").val('');
	 $("#masterCandidateId").val(masterCandidateId);
	  
     //var subFrParam = '';
    /*  if(currentTimeSubFrequency != null && currentTimeSubFrequency != '' && currentTimeSubFrequency != 'undefined'){
  	   subFrParam = "&currentTimeSubFrequency="+currentTimeSubFrequency;
     } */
    // $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
     $.ajax({
	          type : "POST",
	      	  url: sourceleadUrl+"/getTimesheetSubmissionDetails?projectMembersId="+projectMemberId+"&currentTimeFrequency="+currentTimeFrequency+"&timeSheetsFor="+from,		
		      success :function(data) {
		    	  $("#timesheetDivByFrequency").empty();
	 	    	     var closeElement = $('.proj_close');
	 	    	     closeElement.removeAttr('onclick');
	 	    	     closeElement.attr('onclick','timesheetApprovalTableRowByProjectMemberId("'+currentTimeFrequency+'","'+projectMemberId+'","'+from+'Close");'); 
				     $("#timesheetDivByFrequency").append(data);
				     if($("#timesheet_form input[data-id*=docFile]").length > 0) {
				 		$("#timesheet_form input[data-id*=docFile]").remove();
				 		}
				 	 setTotals();
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
		      }
	    }); 
} 
</script>

<!-- <script type="text/javascript">
function timesheetApprovalTableRowByProjectMemberId(timesheetFrequency,projectMemberId,from){ 
		 if(from != null && from != '' && from != 'undefined' && from == 'dashboard'){
			 $(".time_appr_side_wrap").removeClass("time_appr_side_wrap_active");
	    	 $(".side_mask").fadeOut(200); 
     		 $("body").css('overflow-y', 'scrool');
	    	 /* var table = $('#exampleApprover').DataTable();
			 table.row('#row'+projectMemberId+timesheetFrequency+'').remove().draw( false ); */
		 }
		 else if(from != null && from != '' && from != 'undefined' && (from == 'approver' || from == 'approverClose' ||  from == 'dashboardClose')){
			 /* var sourceleadUrl ='${sourceleadUrl}';
				$.ajax({
			   	type: "POST",
			    url: sourceleadUrl+"/timesheetApprovalTableRowByProjectMemberId?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+projectMemberId+"&timesheetFrequency="+timesheetFrequency, 	   
					    success: function(data) { */
						    	 $(".time_appr_side_wrap").removeClass("time_appr_side_wrap_active");
		     		    		 $(".side_mask").fadeOut(200); 
		     	        		 $("body").css('overflow-y', 'scrool');
		     	        		 $("#dynamic").removeAttr('style');
		     	        		 
		     	        		 /* if(from == 'approverClose' || from == 'approver' ){
		     	        			 $("#row"+projectMemberId).empty();
							         $("#row"+projectMemberId).html(data);
		     	        		 } */
					        
					/*     }
			    });  */
		 }
	 
	         
}
</script>
 -->
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
	$("#row"+projectMemberId).empty();
	$("#row"+projectMemberId).html("<td colspan='"+tdSpanSize+"'  id='loading"+projectMemberId+"'></td>");
    $("#loading"+projectMemberId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat; '); 
    var rejectedReason = '';
    var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
     $.ajax({
    type: "POST",
    url: sourceleadUrl+"/saveSubmissionApprovalStatusByProjectMemberId?&projectMemberId="+projectMemberId+"&startDate="+startDate+"&endDate="+endDate+"&approvalStatus=APPROVED&rejectedReason="+rejectedReason,        
	    success: function(data) {
        	 if(from != null && from != '' && from != 'undefined' && from == 'dashboard'){
		    	 var table = $('#exampleApprover').DataTable();
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
	     $("#loading"+projectMemberId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;'); 
	    $.ajax({
	    type: "POST",
	    url: sourceleadUrl+"/saveSubmissionApprovalStatusByProjectMemberId?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+projectMemberId+"&startDate="+startDate+"&endDate="+endDate+"&rejectedReason="+(encodeMsg.replace(/\n/g,'<br/>').replace(/\r/g,'<br/>'))+"&approvalStatus=REJECTED",       
	    success: function(data) { 
	    	
	   	   // approvalList();
	       if(from != null && from != '' && from != 'undefined' && from == 'dashboard'){
	    	   var table = $('#exampleApprover').DataTable();
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
      function getTableDataByPageSize(){
    	  $('#pagination-demo').twbsPagination('destroy'); 
    	  getTableData(1);
      }
	  function getTableData(pageNo) {
		  var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
          var locationCode ='${SourceLeadWeb_locationCode}';
          var userId = '${SourceLeadWeb_user.id}';
          var frequencyMonth = $("#frequencyMonth").val();
	      var timesheetFrequency = $('#timesheetFrequency').val();
	      var weekFrequency = $('#weekFrequency').val();
          var stage = $('#stage').val();
          var client = $('#client').val();
          var pageSize = $("#pageSize").val();
          var start = parseInt(pageNo*pageSize)-parseInt(pageSize);
          var frequencyStartDate = '${timesheetApprovalDTO.frequencyStartDate}';
          var frequencyEndDate = '${timesheetApprovalDTO.frequencyEndDate}';
          $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
		  $.ajax({
          	          type : "POST",
          	        url: sourceleadUrl+"/timesheetEmployeeSubmissionDetails?frequencyMonth="+frequencyMonth+"&timesheetFrequency="+timesheetFrequency+"&weekFrequency="+weekFrequency+"&client="+client+"&stage="+stage+"&frequencyStartDate="+frequencyStartDate+"&frequencyEndDate="+frequencyEndDate+"&locationCode="+locationCode+"&userId="+userId+"&pageSize="+pageSize+"&pageNo="+pageNo+"&start="+start+"&searchValue="+searchValue,		
        		      success :function(data) {
        		    	  $("#approvalTableData").empty();
        		    	  $("#approvalTableData").append(data);
        		    	  var totalRows =  $('#totalRows').val();
        		    		  $("#loadTime").removeAttr('style');
            		    	  preparePagination(pageNo);
        		    	 
        		      }
        	     });  
      }
	  function preparePagination(pageNo) {
		  var totalRows =  $('#totalRows').val();
		  var pageMsg = '';
		  var searchData = '${timesheetApprovalDTO.searchValue}';
		  if(totalRows !=null && totalRows != '' && totalRows != 'undefined'){
			  $('#paginationDiv').show();
			  var pageCount = $('#pageCount').val();
			  var pageSize = $("#pageSize").val();
			  $('#dataTableSearchExpenses').val('') ;
			  $('#dataTableSearchExpenses').val(searchData); 
			  var startingRecord = parseInt((pageNo*pageSize-pageSize)+1);
			  var endingRecord = parseInt(pageNo*pageSize);
			  if(endingRecord > totalRows){
				  endingRecord = totalRows;
			  }
			  var options = {
			              currentPage : pageNo,
			              totalPages: pageCount,
			              prev:'',
			              next:'',
			              initiateStartPageClick: false,
			              onPageClick: function (event, page) {
			              getTableData(""+page+"");
			              }
		      };
		      $("#pagination-demo").twbsPagination(options);
		      pageMsg = " Showing "+startingRecord+" to "+endingRecord+" of "+totalRows+" entries ";
		  }
		  else{
			  $("#tableContent").empty();
	    	  $("#tableContent").append('<div class="dataTables_empty">  <input type="hidden" id="hideExportApproval" value="EmptyList">  </div>');
			  $('#dataTableSearchExpenses').val('') ;
			  $('#dataTableSearchExpenses').val(searchData);
			  $('#paginationDiv').hide();
			  
			  
		  }
	      $('#pageMsg').html('');
	      $('#pageMsg').html(pageMsg);
	 }
  </script>  














 
