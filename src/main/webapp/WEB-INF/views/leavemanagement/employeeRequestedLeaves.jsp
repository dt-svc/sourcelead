<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
$(document).ready(function(){
   getRequestedEmployeesList();
   if ('${notifyLink}' == "notifyLink") 
	{
	var leaveUUID = '${leaveUUID}';
	var orgLocationId = '${orgLocationId}';
    getRequestedAproveSummary(leaveUUID,orgLocationId);
	}
});
</script>

<script>
var pmsUrl ='${SourceLeadWeb_pmsUrl}';
</script>
 
<!-- Content Area -->
  <section class="content-wraper" id="themeroller">
  <div class="success_pop" id="selector1" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
       </div>
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Requested Leaves</h2>
      </div>
		<div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}"> Home</a> &raquo;
		<a href="${SourceLeadWeb_pmsUrl}/leaveManagementReports"> Leave Management</a> &raquo;Requested Leaves</div>
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
                        <option value="/employeeCancelLeaves">Cancelled Leaves</option>
                    </select>
            </div>
        </li>
        <div class="clearfix"></div>
      </ul>
      <div class="clearfix"></div>
    </div>
     <div id="appRejLoading"></div>
    <div class="clients_wrap_table datatable_filter">
    <div class='dynamic_search dynamic_search-new mt10'>
		 <input type='text' placeholder='Search' id='requestedEmployeesTableSearch'/>
		  <span class="cursorPointer" title="Search"><i class="fa fa-search" onclick="getRequestedEmployeesList();"></i></span>    
		 
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
	window.location.href = pmsUrl + location;
}
</script> 
  <script>
  function getRequestedEmployeesList(){
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		var userId = '${SourceLeadWeb_userId}';
		var searchValue = $("#requestedEmployeesTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = pmsUrl+"/getEmployeeRequestedLeaves?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId+"&leaveStatus=PENDING&searchValue="+searchValue;
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
// 		result = result + '<th>No Of Extra Days</th>';
		/* result = result + '<th>Leaves Taken</th>'; */
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
            method: 'POST',
            pages: 10
        }),
        "columns": [
            { "data": "empName" },
            { "data": "leaveType"},
            { "data": "duration"},
            { "data": "noOfDays" },
            /* { "data": "leavesTaken"}, */
            { "data": "thisMonthLeaves" },
            { "data": "balanceleaves" },
            { "data": "action"},
            
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "empName",
// 	    	  	"sortable":false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		 var result = "";
		    	  		if(opt.empType !=null && opt.empType != '' && opt.empType !='undefined'){
		    	  			result = result + '     <div class="proj_show divmax-width">';
		    	  			result = result + '     <div class="pull-left tbldata-dotdot cursorPointer" title="'+opt.empName+'('+opt.empType+')" onclick="getRequestedAproveSummary(\''+opt.leaveUUID+'\','+opt.orgLocationId+','+opt.totalBalLeaves+');">'+opt.empName+'('+opt.empType+')</div><i class="fa fa-binoculars pull-right" onclick="getRequestedAproveSummary(\''+opt.leaveUUID+'\','+opt.orgLocationId+','+opt.totalBalLeaves+');"></i>';
		    	  			result = result + '     </div>';
		        		}else{
		        			result = result + '     <div class="proj_show divmax-width">';
		        			result = result + '     <div class="pull-left tbldata-dotdot cursorPointer" title="'+opt.empName+'" onclick="getRequestedAproveSummary(\''+opt.leaveUUID+'\','+opt.orgLocationId+','+opt.totalBalLeaves+');">'+opt.empName+'<i class="fa fa-binoculars pull-right"></i></div>';
		        			result = result + '     </div>';
		        		}
		              return result; 
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "leaveType",
//               "sortable":false,
              "render": function ( data, type, opt, meta ) {
            	   var result = "";
            	  result = result + '   <div title="'+opt.leaveType+'" class="tblminwidth-75">'+opt.leaveType+'</div>';
	              return result; 
              		
              }
	          },{
            "targets": colIndex++,
            "data": "duration",
            "sortable":false,
            "render": function ( data, type, opt, meta ) {
            	 var result = "";
            	result = result + '   <div title="'+opt.duration+'" class="">'+opt.duration+'</div>';
            	return result; 
            }
          }, {
              "targets": colIndex++,
              "data": "noOfDays",
              "sortable":false,
              "render": function ( data, type, opt, meta ) {
            	   var result = "";
            	      result = result + '     <div class="tblminwidth-75">'+opt.appliedNoOfDays+' day(s)</div>';	 
            	    return result; 
              }
            },/* {
                "targets": colIndex++,
                "data": "noOfDays",
                "render": function ( data, type, opt, meta ) {
              	   var result = "";
              	      result = result + '     <div class="tblminwidth-75">'+opt.extraLeavesTaken+' day(s)</div>';	 
              	    return result; 
                }
              }, *//* {
                "targets": colIndex++,
                "data": "leavesTaken",
                "render": function ( data, type, opt, meta ) {
                		 var result = "";
                		result = result + '     <div class="tblminwidth-75">'+opt.actualLeavesTaken+'</div>';
    	                return result; 
                }
          }, */{
	    	  	"targets": colIndex++,
	    	  	"data": "thisMonthLeaves",
	    	  	"sortable":false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		 var result = "";
	    	  		result = result + '     <div class="tblminwidth-75">'+opt.thisMonthLeavesTaken+'</div>';
  	                return result; 
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "balanceleaves",
	    	  	"sortable":false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		 var result = "";
	    	  		result = result + ' <div class="tblminwidth-75">'+opt.totalBalLeaves+'</div>';
	    	  		return result; 
	    	  	}
	    	  }, {
                  "targets": colIndex++,
                  "data": "action",
                  "sortable":false,
                  "render": function ( data, type, opt, meta ) {
                	   var result = "";
                	  if(opt.status == 'APPROVED'){
		        			 result = result + ' <b title="Approve" class="paddingRight proj_active">APPROVED</b>';
		        		 }else  if(opt.status == 'REJECTED'){
		        			 result = result + ' <b title="Reject" class="paddingRight proj_closed">REJECTED</b>';
		        		 }else{
		        			 '<sec:authorize access="hasAnyRole(\'ROLE_APPROVE_LEAVE\')">';
		        			 result = result + '<div class="tblminwidth-75 pull-left text-center ml10">';
                             result = result + ' <span title="APPROVE" class="paddingRight pull-left"><i class="fa fa-check text-success cursorPointer" onclick="approveLeavePopup(\''+opt.leaveUUID+'\',\''+opt.leaveTypeId+'\',\'APPROVED\',\''+opt.noOfDays+'\');"></i></span>';
                             result = result + '  <span title="REJECT" class="paddingLeft  pull-left"><i class="fa fa-times text-danger cursorPointer" onclick="rejectLeave(\''+opt.leaveUUID+'\',\''+opt.leaveTypeId+'\',\''+opt.noOfDays+'\',\'REJECTED\');"></i></span>';
                             result = result + '</div>';
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
    	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
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
function approveLeavePopup(leaveUUID,leaveTypeId,approveStatus,numberOfDays){
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	$.get(pmsUrl+"/validateRestrictDate?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&leaveUUID="+leaveUUID, function(data) {
		if(data == true){
			var result = "";
			result = result +"	<div class='project_inn_mask'></div>"; 
			result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>This Employee applied leave on restricted day</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="approveLeave(\''+leaveUUID+'\',\''+leaveTypeId+'\',\''+approveStatus+'\',\''+numberOfDays+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="approverPopupCancel();"></b>';
			result = result +"	</div>";
			$("#popUp").append(result);
			$("#remove_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
		}else{
			approveLeave(leaveUUID,leaveTypeId,approveStatus,numberOfDays);
		}
	}); 
}
</script>


<script>
function approveLeave(leaveUUID,leaveTypeId,approveStatus,numberOfDays){
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 var userId = '${SourceLeadWeb_userId}';
	 if(approveStatus == 'REJECTED'){
		 var reason = document.getElementById("leaveReasonForDelete").value;
	 }    
	 if((reason != null && reason != '' && reason != 'undefined') || (approveStatus == 'APPROVED')) {
			 $("#rejectReasonDiv").find('p.jquery_form_error_message').remove();
			 $("#appRejLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			 $.get(pmsUrl+"/approveRequestedLeave?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&leaveUUID="+leaveUUID+"&leaveTypeId="+leaveTypeId+"&approveStatus="+approveStatus+"&userId="+userId+"&numberOfDays="+numberOfDays+"&reason="+reason, function(response) {
				 if (response && response.response === "INVALID_DATA") {
		    		    showError(response.responseMessage);
		    		} else if (response.response === "AWKWARD") {
		    		    location.href = "/errorPage";
		    		} else if (response.response === "SUCCESS") {
						$("#popUp").empty();
						$("#appRejLoading").removeAttr("style");
			 			$("#remove_user_pop, .side_mask").fadeOut();
			 			$("#selector1").removeAttr("style");
			    		 $("#selector1").css({ display: "block" });
			    		 if(approveStatus == 'REJECTED'){
			    		 	$("#msgDiv").html("Rejected Successfully..");
			    		 }else{
			    			 $("#msgDiv").html("Approved Successfully..");
			    		 }
			    	     msgFadein();
			    	     $(".new_side_wrap").removeClass("new_side_wrap_active");
			    	 	$(".side_mask").fadeOut(200);
			    	 	$("body").css('overflow-y', 'auto');
						getRequestedEmployeesList();
					} else if (response.indexOf('Sign In') !== -1) {
					location.replace(pmsUrl);
				 } else {
				    showError("Operation failed, please try again");
				 }
				});
 }else{
	 $("#rejectReasonDiv").find('p.jquery_form_error_message').remove();
 	 $("#rejectReasonDiv").append("<p class='jquery_form_error_message'>Reason Is Required</p>");
  }
}
</script>

<script>
function getRequestedAproveSummary(leaveUUID,orgLocationId,totalBalLeaves){
	   $.get(pmsUrl+"/getRequestedAproveSummary?"+tokenParamName+"="+tokenValue+"&leaveUUID="+leaveUUID+"&orgLocationId="+orgLocationId, function(response) {
		   if(response.response === "SUCCESS") {
		   if(response != null){
			$(".new_side_wrap").addClass("new_side_wrap_active");
			$(".side_mask").fadeIn(200);
			$("body").css('overflow-y', 'hidden');
			var data = response.object;
			 var result = "";	
			 result = result +' <div class="viewgrid-header pull-right zIndex positionRelative">';
			 result = result +'<span class="btn btn-primary btn-sm mr10" title="APPROVE" onclick="approveLeavePopup(\''+data.leaveUUID+'\',\''+data.leaveTypeId+'\',\'APPROVED\',\''+data.noOfDays+'\');">APPROVE</span>';
			 result = result +'<span class="btn btn-danger btn-sm mr10" title="REJECT" onclick="rejectLeave(\''+data.leaveUUID+'\',\''+data.leaveTypeId+'\',\''+data.noOfDays+'\',\'REJECTED\');">REJECT</span>';
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
			 if(data.empName != null && data.empName !='' && data.empName !='undefined'){
				 result = result +'<span>'+data.empName+'</span>';
			 }else{
	            	result = result +' <span>--</span>';
	            }
			 result = result +' </div>';
			 result = result +'</div>';
			 result = result +'<div class="col-md-12 mb">';
			 result = result +'<label class="col-md-2 control-label"><b>Employee ID:</b></label>';
			 result = result +'<div class="col-md-8">';
			 if(data.empid !=null && data.empid !='' && data.empid !='undefined'){
				 result = result +'  <span>'+data.empid+'</span>';	 
			 }else{
				 result = result +'  <span>--</span>';
			 }
			
			 result = result +'</div>';
			 result = result +'</div>';
			 result = result +'<div class="col-md-12 mb">';
			 result = result +'<label class="col-md-2 control-label"><b>Employee Email:</b></label>';
			 result = result +' <div class="col-md-8">';
			 if(data.empEmail != null && data.empEmail !='' && data.empEmail !='undefined'){
				 result = result +'<span>'+data.empEmail+'</span>';
			 }else{
	            	result = result +' <span>--</span>';
	            }
			 result = result +' </div>';
			 result = result +'</div>';
			 result = result +'<div class="col-md-12 mb">';
			 result = result +'<label class="col-md-2 control-label"><b>Role:</b></label>';
			 result = result +' <div class="col-md-8">';
			 if(data.empType != null && data.empType !='' && data.empType !='undefined'){
				 result = result +'<span>'+data.empType+'</span>';
			 }else{
	            	result = result +' <span>--</span>';
	            }
			 result = result +'</div>';
			 result = result +'</div>';
			 result = result +'<div class="col-md-12 mb">';
			 result = result +'<label class="col-md-2 control-label"><b>Leave Type:</b></label>';
			 result = result +'<div class="col-md-8">';
			 if(data.leaveType != null && data.leaveType !='' && data.leaveType !='undefined'){
				 result = result +'<span>'+data.leaveType+'</span>';
			 }else{
	            	result = result +' <span>--</span>';
	            }
			 result = result +'</div>';
			 result = result +'</div>';
			 result = result +'<div class="col-md-12 mb">';
			 result = result +'<label class="col-md-2 control-label"><b>Type:</b></label>';
			 result = result +'<div class="col-md-8">';
			 if(data.type != null && data.type !='' && data.type !='undefined'){
				 result = result +'<span>'+data.type+'</span>';
			 }else{
	            	result = result +' <span>--</span>';
	            }
			 result = result +'</div>';
			 result = result +'</div>';
			 result = result +'<div class="col-md-12 mb">';
			 result = result +'<label class="col-md-2 control-label"><b>Duration:</b></label>';
			 result = result +'<div class="col-md-8">';
			 if(data.duration != null && data.duration !='' && data.duration !='undefined'){
				 result = result +'<span>'+data.duration+'</span>';
			 }else{
	            	result = result +' <span>--</span>';
	            }
			 result = result +' </div>';
			 result = result +'</div>';
             result = result +' <div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>Reason:</b></label>';
             result = result +'<div class="col-md-8">';
        	 if(data.reason != null && data.reason !='' && data.reason !='undefined'){
				 result = result +'<span>'+data.reason+'</span>';
			 }else{
	            	result = result +' <span>--</span>';
	            }
             result = result +' </div>';
             result = result +' </div>';
         /*     result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>No of leaves taken:</b></label>';
             result = result +' <div class="col-md-8">';
        	 if(data.actualLeavesTaken != null && data.actualLeavesTaken !='' && data.actualLeavesTaken !='undefined'){
				 result = result +'<span>'+data.actualLeavesTaken+'</span>';
			 }else{
	            	result = result +' <span>--</span>';
	            }
             result = result +'</div>';
             result = result +'</div>' */;
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>This month:</b></label>';
             result = result +'<div class="col-md-8">';
             if(data.thisMonthLeavesTaken != null && data.thisMonthLeavesTaken !='' && data.thisMonthLeavesTaken !='undefined'){
				 result = result +'<span>'+data.thisMonthLeavesTaken+'</span>';
			 }else{
	            	result = result +' <span>0</span>';
	            }
             result = result +'</div>';
             result = result +'</div>';
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>Balance leaves:</b></label>';
             result = result +'<div class="col-md-8">';
             if(data.totalBalLeaves != null && data.totalBalLeaves !='' && data.totalBalLeaves !='undefined'){
				 result = result +'<span>'+data.totalBalLeaves+'</span>';
			 }else{
	            	result = result +' <span>0</span>';
	            }
             result = result +'</div>';
             result = result +'</div>';
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>Applied on:</b></label>';
             result = result +'<div class="col-md-8">';
             if(data.createdDate != null && data.createdDate !='' && data.createdDate !='undefined'){
				 result = result +'<span>'+data.createdDate+'</span>';
			 }else{
	            	result = result +' <span>--</span>';
	            }
             result = result +' </div>';
             result = result +'</div>';
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>Approvers:</b></label>';
             result = result +'<div class="col-md-8">';
             if(data.approverUsers != null && data.approverUsers !='' && data.approverUsers !='undefined'){
				 result = result +'<span>'+data.approverUsers+'</span>';
			 }else{
	            	result = result +' <span>--</span>';
	            }
             result = result +'</div>';
             result = result +'</div>';
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>Attachment:</b></label>';
             result = result +' <div class="col-md-8">';
             if(data.fileUrl != null && data.fileUrl != 'undefined' && data.fileUrl != ''){
				 result = result+'<div class="tblicons_4"> <a download class="glyphicon glyphicon-download-alt" target="_blank" href=\"'+data.fileUrl+'\" title="Download"></a>&nbsp&nbsp';
            	 result = result + '<a href="#" title="Preview"><i class="fa fa-eye cursorPointer" onclick="viewFile(\''+data.fileUrl+'\', \''+data.fileName+'\', \''+data.fileContentType+'\')"></i></a></div>';
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
		   
		   } else if (response && response.response === "INVALID_DATA") {
				//$(".new_side_wrap").removeClass("new_side_wrap_active");
 		    showError(response.responseMessage);
		   } else if (response.indexOf('Sign In') !== -1) {
				location.replace(pmsUrl);
		    } else if (response.response === "AWKWARD") {
			    location.href = "/errorPage";
		     }
			else {
			    showError("Operation failed, please try again");
			}	   
		 
		 
	});  
}
</script>

<script>
function rejectLeave(leaveUUID, leaveTypeId, noOfDays){
	var result = "";
	result = result +"	<div class='comMask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	//result = result +"		<p>Do you want to delete this vendor contact?</p>";
	result = result +"		<textarea id='leaveReasonForDelete' class='form-control' field-name='Reject Reason'  data-validation='required validate_Space validate_Description validate_length length2-250' name='reasonForSpoc' value='' placeholder='Rejected Reason' rows='2'></textarea>";
	result = result + ' <div id="rejectReasonDiv"/>';
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Save" onclick="approveLeave(\''+leaveUUID+'\',\''+leaveTypeId+'\',\'REJECTED\',\''+noOfDays+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
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
