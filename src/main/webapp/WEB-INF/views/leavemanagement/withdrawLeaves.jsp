<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">
$(document).ready(function(){
	getWithdrawLeavesList();
	/* if ('${notifyLink}' == "notifyLink") 
	{
	var leaveUUID = '${leaveUUID}';
	var orgLocationId = '${orgLocationId}';
	var status = '${status}';
	getWithdrawLeavesList(leaveUUID,orgLocationId,status);
	} */
});
</script>

 <section class="content-wraper" id="themeroller">
 <div class="success_pop" id="selector1" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
       </div>
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Withdraw</h2>
      </div>
		<div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>"> Home</a> &raquo; Withdraw Leaves</div>
    </section>
    
<div class="content-area content-new clearfix">

<div class="timesheet_out">
  <div class="clearfix">
    <div class="timesheet_head">
      <ul>
         <li class="dropdown">
                <div class="pull-left select-fild">
                        <select onchange="redirectlocation(this.value);">
                        <option value="/pendingLeaves">Pending Leaves</option>
                        <option value="/withdrawLeaves" selected>Withdraw Leaves</option>
                        <option  value="/approvedLeaves">Approved Leaves</option>
                        <option value="/rejectedLeaves">Rejected Leaves</option>
                        <option value="/cancelLeaves">Cancelled Leaves</option>
                    </select>
                </div>
         </li>
        <div class="clearfix"></div>
      </ul>
      <div class="clearfix"></div>
    </div>
    <div class="clients_wrap_table datatable_filter">
    	<div id="loading"></div>
    	<div id="withdrawListDiv"></div>
    </div>
  </div>
</div>

    </div>
    <!-- Content Area Ends--> 
  </section>
  <!-- Content Area Ends--> 

<script>
function redirectlocation(location){
	window.location.href = sourceleadUrl + location;
}
</script>


<script>
function getWithdrawLeavesList(){
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var userId = '${SourceLeadWeb_user.id}';
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:999;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.get(sourceleadUrl+"/getLeavesListByLocationIdAndStatus?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&status=WITHDRAW&userId="+userId, function(response) {
		if (response.response === "SUCCESS") {
		var result = "";				        	
				result = result + '<table id="withdrawListTable" class="display" cellspacing="0" width="100%" >';
				result = result + '<thead>';
				result = result + '<tr>';
				result = result +'<th>Leave Type</th>';
				result = result +'<th>Duration</th>';
				result = result +'<th>Applied On</th>';
				result = result +'<th>Withdraw On</th>';
				result = result + '<th>Reason</th>';
				result = result + '<th>Status</th>';
				result = result + '<th>Action</th>';
				result = result + '</tr>';
				result = result + '</thead>	';
				result = result + '<tbody>';
				//var data = result.data;
				if(response !=null && response !=''){
					 for (var i=0; i<response.data.length; i++)
						{
			        		 var opt = response.data[i];
			        		 result = result + ' <tr>';
			        		 result = result + ' <td>';
			        		 result = result + ' <div class="proj_show tbldata-dotdot-big">';
			        		 result = result + '     <div class="pull-left tbldata-dotdot-me" title="'+opt.leaveType+'" onclick="getWithdrawLeaveSummary(\''+opt.leaveUUID+'\','+orgLocationId+',\''+opt.status+'\');">'+opt.leaveType+' <i class="fa fa-binoculars pull-right" ></i></div>'; 
			        		 result = result + '  </div>';
			        		 result = result + ' </td>';
			        		 result = result + ' <td>';
				        		 result = result + ' <div title="'+opt.fromDate+' - '+opt.toDate+'" class="tblminwidth-75 mt2">';
					        		 result = result + ' <div class="width100percnt">'+opt.fromDate+'</div>';
					        		 result = result + ' <div class="width100percnt mt2">'+opt.toDate+'</div>';
				        		 result = result + ' </div>';
			        		 result = result + ' </td>';
			        		 result = result + ' <td>';
			        		 result = result + '   <div title="'+opt.createdDate+'" class="tbldata-dotdot-100">'+opt.createdDate+'</div>';
			        		 result = result + '</td>';
			        		 result = result + ' <td>';
			        		 result = result + '   <div title="'+opt.updatedDate+'" class="tbldata-dotdot-100">'+opt.updatedDate+'</div>';
			        		 result = result + '</td>';
			        		 result = result + ' <td>';
			        		 if(opt.reason !=null && opt.reason != 'undefined' && opt.reason != ''){
			        			 result = result + '     <div title="'+opt.reason+'" class="tbldata-dotdot-vsm">'+opt.reason+'</div>';	 
			        		 }else{
			        			 result = result + '     <div title="" class="tblminwidth-75">--</div>';	
			        		 }
			        		 result = result + '  </td>';
			        		 result = result + ' <td>';
			        		 result = result + '  <div title="" class="tblminwidth-75">';
			        		 result = result +' <b class="proj_process mt5 mb5" title="'+opt.status+'">Withdrawn</b> ';
			        		 result = result + '  </div>';
			        		 result = result + ' </td>';
			        		 result = result + ' <td>';
			        		 result = result + '   <a title="Reapply" class="tbldata-dotdot-100" href="'+sourceleadUrl+'/reApplyLeaveByUUID?leaveUUID='+opt.leaveUUID+'&currentStage=WITHDRAW">Reapply</a>';
			        		 result = result + '</td>';
			        		 result = result + '  </tr>';
						}
				}
	        	
				result = result + '  </tbody>';
				result = result + ' </table>';
				$("#withdrawListDiv").empty();
				$("#withdrawListDiv").append(result);
				$("#loading").removeAttr("style");
				withdrawListPagination();
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
				
		}else if (response.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
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
function getWithdrawLeaveSummary(leaveUUID,orgLocationId,status){
    $.get(sourceleadUrl+"/getRequestedAproveSummary?"+tokenParamName+"="+tokenValue+"&leaveUUID="+leaveUUID+"&orgLocationId="+orgLocationId, function(response) {
		   if(response.response === "SUCCESS") {
		   if(response != null){
			$(".new_side_wrap").addClass("new_side_wrap_active");
			$(".side_mask").fadeIn(200);
			$("body").css('overflow-y', 'hidden');
			var data = response.object;
			 var result = "";	
			 result = result +' <div class="row">';
			 result = result +'<div class="col-md-12 mb">';
			 result = result +'<label class="col-md-2 control-label"><b>Leave Type:</b></label>';
			 result = result +'<div class="col-md-8">';
			 if(data.leaveName != null && data.leaveName !='' && data.leaveName !='undefined'){
				 result = result +'<span>'+data.leaveName+'</span>';
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
             result = result +'<label class="col-md-2 control-label"><b>Type:</b></label>';
             result = result +'<div class="col-md-8">';
             if(data.isBillable != null && data.isBillable !='' && data.isBillable !='undefined'){
				 result = result +'<span>'+data.isBillable+'</span>';
			 }else{
	            	result = result +' <span>--</span>';
	            }
             result = result +' </div>';
             result = result +' </div>';
            /*  result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>No of leaves taken:</b></label>';
             result = result +' <div class="col-md-8">'; 
             if(data.actualLeavesTaken != null && data.actualLeavesTaken !='' && data.actualLeavesTaken !='undefined'){
				 result = result +'<span>'+data.actualLeavesTaken+'</span>';
			 }else{
	            	result = result +' <span>--</span>';
	            }
             result = result +'</div>';
             result = result +'</div>'; */
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
             result = result +'<label class="col-md-2 control-label"><b>Reason:</b></label>';
             result = result +'<div class="col-md-8">';
             if(data.withdrawReason != null && data.withdrawReason !='' && data.withdrawReason !='undefined'){
				 result = result +'<span>'+data.withdrawReason+'</span>';
			 }else{
	            	result = result +' <span>--</span>';
	            }
             result = result +'</div>';
             result = result +'</div>';
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>Attachment:</b></label>';
             result = result +' <div class="col-md-8">';
             if(data.fileUrl != null && data.fileUrl != 'undefined' && data.fileUrl != ''){
            	 result = result+'<td><div class="tblicons_4"> <a download class="glyphicon glyphicon-download-alt" target="_blank" href=\"'+data.fileUrl+'\" title="Download"></a>&nbsp&nbsp';
            	 result = result + '<a href="#" title="Preview"><i class="fa fa-eye cursorPointer" onclick="viewFile(\''+data.fileUrl+'\', \''+data.fileName+'\', \''+data.fileContentType+'\')"></i></a>';
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
				location.replace(sourceleadUrl);
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
function withdrawListPagination(){ 
    $('#withdrawListTable').DataTable( { 
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
    $("#withdrawListTable").wrap("<div class='overflow_Auto width100percnt'></div>")
}
</script>

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


<jsp:include page="../masterFooter.jsp" />