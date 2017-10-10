<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
$(document).ready(function(){
   getRequestedEmployeesList();
});
</script>
 
<!-- Content Area -->
  <section class="content-wraper" id="themeroller">
  <div class="success_pop" id="selector1" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
       </div>
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Requested Count</h2>
      </div>
		<div class="pull-right brud-crum"><a href="dashboard_us.html">Home</a> &raquo; <a href="dashboard-lavemanagement.html">Leave Management</a> &raquo; Requested Count</div>
    </section>
    <div id="popUp"></div>
    
<div class="content-area content-new clearfix">

<div class="timesheet_out">
  <div class="clearfix">
    <div class="timesheet_head">
      <ul>
          <li class="dropdown">
            <div class="pull-left select-fild">
                    <select onchange="redirectlocation(this.value);">
                        <option value="/employeeRequestedLeaves" selected>Requested Leaves</option>
                        <option  value="/employeeApprovedLeaves">Approved Leaves</option>
                        <option value="/employeeRejectedLeaves" >Rejected Leaves</option>
                        <option value="/employeeCancelLeaves">Cancel Leaves</option>
                    </select>
            </div>
        </li>
        <div class="clearfix"></div>
      </ul>
      <div class="clearfix"></div>
    </div>
     <div id="loading"></div> 
    <div class="clients_wrap_table datatable_filter">
    <div class='remove_search mt10'>
		 <input type='text' placeholder='Search' id='requestedEmployeesTableSearch'/>
		</div>
    <div id="requestedLeavesDiv"></div>
    
  </div>
</div>

    </div>
    
    
    <div class="side_mask">
    <div class="proj_close"></div>
</div>
<div class="new_side_wrap">
<div class="clients_side_wrap_inn">
      <div class="clerfix showDetails">
      <div class="project_tabs minHeight_225 form-horizontal modal-body">
      <div id="leaveSummaryId"></div>
     </div>
      </div>
  </div>
</div>
  </section>
  <!-- Content Area Ends--> 
  
 <script>
function redirectlocation(location){
	window.location.href = sourceleadUrl + location;
}
</script> 
  <script>
  function getRequestedEmployeesList(){
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		var userId = '${SourceLeadWeb_userId}';
		var searchValue = $("#requestedEmployeesTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getEmployeeRequestedLeaves?"+tokenParamName+"="+tokenValue+"&searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&userId="+userId+"&leaveStatus=PENDING";
	    prepareRequestedEmployeesList(url);
}
</script> 
  
  <script>
function prepareRequestedEmployeesList(url){
	 $("#requestedLeavesDiv").removeAttr("style");
 	 $("#requestedLeavesDiv").empty();
 	 var result = "";
 	result = result +" <div class='remove_search'>";
 	result = result +"</div>";
 	result = result + '<table id="requestedleavesTable" class="display" cellspacing="0" width="100%" >';
 	result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Employee Name</th>';
		result = result + '<th>Leave Type</th>';
		result = result + '<th>Duration</th>';
		result = result + '<th>No Of Days</th>';
		result = result + '<th>Leaves Taken</th>';
		result = result + '<th>This Month</th>';
		result = result + '<th>Balance leaves</th>';
		result = result + '<th>Action</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
        result = result +"</table>";
      $("#requestedLeavesDiv").append(result);
      requestedEmployeesPagination(url); 
}
</script>

<script>
function requestedEmployeesPagination(url){
	var colIndex = 0;
    $('#requestedleavesTable').DataTable( { 
    	"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'GET',
            pages: 10
        }),
        "columns": [
            { "data": "empName" },
            { "data": "leaveType"},
            { "data": "duration"},
            { "data": "noOfDays" },
            { "data": "leavesTaken"},
            { "data": "thisMonthLeaves" },
            { "data": "balanceleaves" },
            { "data": "action"},
            
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "empName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		    var result = "";
		    	  		if(opt.empType !=null && opt.empType != '' && opt.empType !='undefined'){
		    	  			result = result + '     <div class="pull-left tbldata-dotdot-me cursorPointer" title="Sick Leave" onclick="getRequestedAproveSummary(\''+opt.id+'\','+opt.orgLocationId+');">'+opt.empName+'('+opt.empType+')</div><i class="fa fa-binoculars pull-right"></i>';	
		        		}else{
		        			result = result + '     <div class="pull-left tbldata-dotdot-me cursorPointer" title="Sick Leave" onclick="getRequestedAproveSummary(\''+opt.id+'\','+opt.orgLocationId+');">'+opt.empName+'</div><i class="fa fa-binoculars pull-right"></i>';
		        		}
		              return result; 
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "leaveType",
              "render": function ( data, type, opt, meta ) {
            	   var result = "";
            	  result = result + '   <div title="Calendaer month" class="tblminwidth-75">'+opt.leaveType+'</div>';
	              return result; 
              		
              }
	          },{
            "targets": colIndex++,
            "data": "duration",
            "render": function ( data, type, opt, meta ) {
            	 var result = "";
            	result = result + '   <div title="Duration" class="">'+opt.duration+'</div>';
            	return result; 
            }
          }, {
              "targets": colIndex++,
              "data": "noOfDays",
              "render": function ( data, type, opt, meta ) {
            	   var result = "";
            	  result = result + '     <div title="2" class="tblminwidth-75">'+opt.noOfDays+'</div>';	 
            	    return result; 
              }
            }, {
                "targets": colIndex++,
                "data": "leavesTaken",
                "render": function ( data, type, opt, meta ) {
                		 var result = "";
                		result = result + '     <div title="2" class="tblminwidth-75">'+opt.leavesTaken+'</div>';
    	                return result; 
                }
          },{
	    	  	"targets": colIndex++,
	    	  	"data": "thisMonthLeaves",
	    	  	"render": function(data, type, opt, meta) {
	    	  		 var result = "";
	    	  		result = result + '     <div title="2" class="tblminwidth-75">'+opt.thisMonthLeaves+'</div>';
  	                return result; 
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "balanceleaves",
	    	  	"render": function(data, type, opt, meta) {
	    	  		 var result = "";
	    	  		result = result + ' <div title="2" class="tblminwidth-75">'+opt.balanceleaves+'</div>';
	    	  		return result; 
	    	  	}
	    	  }, {
                  "targets": colIndex++,
                  "data": "action",
                  "render": function ( data, type, opt, meta ) {
                	   var result = "";
                	  if(opt.status == 'APPROVED'){
		        			 result = result + ' <b title="Accept" class="paddingRight proj_active">APPROVED</b>';
		        		 }else  if(opt.status == 'REJECTED'){
		        			 result = result + ' <b title="Accept" class="paddingRight proj_closed">REJECTED</b>';
		        		 }else{
		        			 '<sec:authorize access="hasAnyRole(\'ROLE_APPROVE_LEAVE\')">';
                             result = result + ' <span title="Accept" class="paddingRight"><i class="fa fa-check text-success cursorPointer" onclick="approveLeavePopup(\''+opt.id+'\',\''+opt.leaveTypeId+'\',\'APPROVED\',\''+opt.noOfDays+'\');"></i></span>';
                             result = result + '  <span title="Reject" class="paddingLeft"><i class="fa fa-times text-danger cursorPointer" onclick="rejectLeave(\''+opt.id+'\',\''+opt.leaveTypeId+'\',\''+opt.noOfDays+'\',\'REJECTED\');"></i></span>';
                            '</sec:authorize>';

		        		 }
                	    return result; 
                  }
                }],
                "scrollCollapse": false,
    	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
    	    	"aaSorting": [[1,'desc']],
    	    	"bLengthChange": true,
    	    	"bDestroy": true,
    	    	"ordering": true,
    	    	"oLanguage": {
    	     		"sSearch": "",
    	     		 "sZeroRecords": "", "sEmptyTable": "", 
    	     		 "sProcessing": "<img src='resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
    	     }
                });
       $("#requestedleavesTable").wrap("<div class='overflow_Auto width100percnt'></div>")
       $("input[type=search]").hide();
       $("#requestedEmployeesTableSearch").keyup(function(e) {
       	   if(e.which === 13) {
       		getRequestedEmployeesList();
       	   }
       	});
}
</script>

 
<script>
function approveLeavePopup(leaveId,leaveTypeId,approveStatus,numberOfDays){
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	$.get(sourceleadUrl+"/validateRestrictDate?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&leaveId="+leaveId, function(data) {
		if(data == true){
			var result = "";
			result = result +"	<div class='project_inn_mask'></div>"; 
			result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>This Employee applied leave on restricted day</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="approveLeave(\''+leaveId+'\',\''+leaveTypeId+'\',\''+approveStatus+'\',\''+numberOfDays+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="approverPopupCancel();"></b>';
			result = result +"	</div>";
			$("#popUp").append(result);
			$("#remove_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
		}else{
			approveLeave(leaveId,leaveTypeId,approveStatus,numberOfDays);
		}
	}); 
}
</script>


<script>
function approveLeave(leaveId,leaveTypeId,approveStatus,numberOfDays){
	 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 var userId = '${SourceLeadWeb_userId}';
	
	 if(approveStatus == "REJECTED"){
		 var reason = document.getElementById("leaveReasonForDelete").value;
		 if(reason == null || reason == '' || reason == 'undefined'){
			 $("#rejectReasonDiv").find('p.jquery_form_error_message').remove();
		  	 $("#rejectReasonDiv").append("<p class='jquery_form_error_message'>Reason Is Required</p>");
		 }else{
			 $("#rejectReasonDiv").find('p.jquery_form_error_message').remove();
			  $.get(sourceleadUrl+"/approveRequestedLeave?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&leaveId="+leaveId+"&leaveTypeId="+leaveTypeId+"&approveStatus="+approveStatus+"&userId="+userId+"&numberOfDays="+numberOfDays+"&reason="+reason, function(data) {
					if(data == true){
						$("#popUp").empty();
			 			$("#remove_user_pop, .side_mask").fadeOut();
			 			$("#selector1").removeAttr("style");
			    		 $("#selector1").css({ display: "block" });
			    	     $("#msgDiv").html("Rejected Successfully..");
			    	     msgFadein();
						getRequestedEmployeesList();
					}
				});
		 }
	 }else{
		  $.get(sourceleadUrl+"/approveRequestedLeave?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&leaveId="+leaveId+"&leaveTypeId="+leaveTypeId+"&approveStatus="+approveStatus+"&userId="+userId+"&numberOfDays="+numberOfDays, function(data) {
				if(data == true){
					$("#popUp").empty();
		 			$("#remove_user_pop, .project_inn_mask").fadeOut();
                         $("#selector1").removeAttr("style");
			    		 $("#selector1").css({ display: "block" });
			    	     $("#msgDiv").html("Approved Successfully..");
			    	     msgFadein();
					getRequestedEmployeesList();
				}else{
					$("#selector1").removeAttr("style");
		    		 $("#selector1").css({ display: "block" });
		    	     $("#msgDiv").html("Something Went Wrong");
		    	     msgFadein();
				getRequestedEmployeesList();
				}
			});
	 }
}
</script>

<script>
function getRequestedAproveSummary(leaveId,orgLocationId){
	   $.get(sourceleadUrl+"/getRequestedAproveSummary?"+tokenParamName+"="+tokenValue+"&leaveId="+leaveId+"&orgLocationId="+orgLocationId, function(data) {
		if(data != null){
			$(".new_side_wrap").addClass("new_side_wrap_active");
			$(".side_mask").fadeIn(200);
			$("body").css('overflow-y', 'hidden');
			 var result = "";	
			 result = result +' <div class="viewgrid-header pull-right zIndex positionRelative">';
			 result = result +'<span class="btn btn-primary btn-sm mr10" onclick="approveLeavePopup(\''+data.id+'\',\''+data.leaveTypeId+'\',\'APPROVED\',\''+data.noOfDays+'\');">Approve</span>';
			 result = result +'<span class="btn btn-danger btn-sm mr10" onclick="rejectLeave(\''+data.id+'\',\''+data.leaveTypeId+'\',\''+data.noOfDays+'\',\'REJECTED\');">Reject</span>';
			 result = result +'<div class="clearfix"></div>';
			 result = result +'<div class="cancel-reason" id="cancelReason">';
			 result = result +'<div class="clearfix">';
			 result = result +' <textarea rows="1" class="form-control" placeholder="Cancel Reason..." id="textarea"></textarea>';
			 result = result +'</div>';
			 result = result +'<div class="clearfix mt5">';
			 result = result +'</div>';
			 result = result +'</div>';
			 result = result +' </div> ';
			 result = result +' <div class="row">';
			 result = result +'<div class="col-md-12 mb">';
			 result = result +' <label class="col-md-2 control-label"><b>Employee Name:</b></label>';
			 result = result +' <div class="col-md-8">';
			 result = result +' <span>'+data.empName+'</span>';
			 result = result +' </div>';
			 result = result +'</div>';
			 result = result +'<div class="col-md-12 mb">';
			 result = result +'<label class="col-md-2 control-label"><b>Employee ID:</b></label>';
			 result = result +'<div class="col-md-8">';
			 if(data.empid !=null){
				 result = result +'  <span>'+data.empid+'</span>';	 
			 }else{
				 result = result +'  <span>--</span>';
			 }
			
			 result = result +'</div>';
			 result = result +'</div>';
			 result = result +'<div class="col-md-12 mb">';
			 result = result +'<label class="col-md-2 control-label"><b>Employee Email:</b></label>';
			 result = result +' <div class="col-md-8">';
			 result = result +' <span>'+data.empEmail+'</span>';
			 result = result +' </div>';
			 result = result +'</div>';
			 result = result +'<div class="col-md-12 mb">';
			 result = result +'<label class="col-md-2 control-label"><b>Employee Type:</b></label>';
			 result = result +' <div class="col-md-8">';
			 result = result +'<span>'+data.empType+'</span>';
			 result = result +'</div>';
			 result = result +'</div>';
			 result = result +'<div class="col-md-12 mb">';
			 result = result +'<label class="col-md-2 control-label"><b>Leave Type:</b></label>';
			 result = result +'<div class="col-md-8">';
			 result = result +'<span>'+data.leaveType+'</span>';
			 result = result +'</div>';
			 result = result +'</div>';
			 result = result +'<div class="col-md-12 mb">';
			 result = result +'<label class="col-md-2 control-label"><b>Duration:</b></label>';
			 result = result +'<div class="col-md-8">';
			 result = result +' <span>'+data.duration+'</span>';
			 result = result +' </div>';
			 result = result +'</div>';
             result = result +' <div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>Reason:</b></label>';
             result = result +'<div class="col-md-8">';
             result = result +'<span>'+data.reason+'</span>';
             result = result +' </div>';
             result = result +' </div>';
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>No of leaves taken:</b></label>';
             result = result +' <div class="col-md-8">';
             result = result +'<span>'+data.leavesTaken+'</span>';
             result = result +'</div>';
             result = result +'</div>';
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>This month:</b></label>';
             result = result +'<div class="col-md-8">';
             result = result +' <span>'+data.thisMonthLeaves+'</span>';
             result = result +'</div>';
             result = result +'</div>';
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>Balance leaves:</b></label>';
             result = result +'<div class="col-md-8">';
             result = result +' <span>'+data.balanceLeavesCount+'</span>';
             result = result +'</div>';
             result = result +'</div>';
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>Applied on:</b></label>';
             result = result +'<div class="col-md-8">';
             result = result +'<span>'+data.createdDate+'</span>';
             result = result +' </div>';
             result = result +'</div>';
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>Approvers:</b></label>';
             result = result +'<div class="col-md-8">';
             result = result +' <span>'+data.approverUsers+'</span>';
             result = result +'</div>';
             result = result +'</div>';
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>Attachment:</b></label>';
             result = result +' <div class="col-md-8">';
             if(data.fileUrl != null && data.fileUrl != 'undefined' && data.fileUrl != ''){
            	 result = result + '<a href="#" title="Preview"><i class="fa fa-eye cursorPointer" onclick="viewFile(\''+data.fileUrl+'\', \''+data.fileName+'\', \''+data.fileType+'\')"></i></a>';
             }else{
            	 result = result +'<span>--</span>'; 
             }
             result = result +'</div>';
             result = result +' </div>';
             result = result +'<div class="clearfix"></div>';
             result = result +'</div>';
             $("#leaveSummaryId").empty();
             $("#leaveSummaryId").append(result);
            
		}	
		 
	});  
}
</script>

<script>
function rejectLeave(id, leaveTypeId, noOfDays){
	var result = "";
	result = result +"	<div class='comMask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	//result = result +"		<p>Do you want to delete this vendor contact?</p>";
	result = result +"		<textarea id='withdrawReason' class='form-control' field-name='Reject Reason'  data-validation='required validate_Space validate_Description validate_length length2-250' name='reasonForSpoc' value='' placeholder='Terminate Reason' rows='2'></textarea>";
	result = result + ' <div id="withdrawReasonDiv"/>';
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Delete" onclick="approveLeave(\''+id+'\',\''+leaveTypeId+'\',\'REJECTED\',\''+noOfDays+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".comMask").fadeIn(200);
}
</script>


<script>
function cancelTermination(){
	$(".comMask").remove();
	$("#remove_user_pop, .sideMask").remove();
}
</script>

<script>
function approverPopupCancel(){
	$("#popUp").empty();
	 $(".project_inn_mask").fadeOut(200);
}

</script>

<script>
	function msgFadein() {
		$("#selector1").fadeIn(200);
        $('#selector1').delay(2000).fadeOut('slow');
	}
</script> 

<script>
$(".proj_show").click(function(){
		$(".new_side_wrap").addClass("new_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
});
$(".proj_close, .side_mask").click(function(){
	$(".new_side_wrap").removeClass("new_side_wrap_active");
	$(".side_mask").fadeOut(200);
	$("body").css('overflow-y', 'auto');
    $('.addcandidate-popup').fadeOut(200);
}); 
</script>
<jsp:include page="../masterFooter.jsp" />
