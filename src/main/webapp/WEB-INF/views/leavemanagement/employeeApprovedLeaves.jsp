<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
 
<script>
$(document).ready(function(){
   getApprovedEmployeesList();
});
</script>

 <!-- Content Area -->
  <section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Employee Approved Count</h2>
      </div>
		<div class="pull-right brud-crum"><a href="dashboard_us.html">Home</a> &raquo; <a href="dashboard-lavemanagement.html">Leave Management</a> &raquo; Approved Count</div>
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
                        <option value="/employeeRequestedLeaves">Requested Leaves</option>
                        <option  value="/employeeApprovedLeaves" selected>Approved Leaves</option>
                        <option value="/employeeRejectedLeaves">Rejected Leaves</option>
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
    <div id="approvedLeavesDiv"></div>
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
  function getApprovedEmployeesList(){
	  var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	  var userId = '${SourceLeadWeb_userId}';
	  $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.get(sourceleadUrl+"/getEmployeeRequestedLeaves?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId+"&leaveStatus=APPROVED", function(data) {
				 var result = "";				        	
					result = result + '<table id="approvedLeavesTable" class="display" cellspacing="0" width="100%" >';
					result = result + '<thead>';
					result = result + '<tr>';
					result = result + '<th>Employee Name</th>';
					result = result + '<th>Leave Type</th>';
					result = result + '<th>Duration</th>';
					result = result + '<th>No Of Days</th>';
					result = result + '<th>Leaves Taken</th>';
					result = result + '<th>Approved By</th>';
					result = result + '<th>Approved on</th>';
					result = result + '<th>Action</th>';
					result = result + '</tr>';
					result = result + '</thead>	';
					result = result + '<tbody>';
					
					if(data !=null && data !=''){
						 for (var i=0; i<data.length; i++)
							{
				        		 var opt = data[i];
				        		 result = result + ' <tr>';
				        		 result = result + ' <td>';
				        		 result = result + ' <div class="proj_show tbldata-dotdot-big">';
				        		 result = result + '     <div class="pull-left tbldata-dotdot-me" title="Sick Leave" onclick="getRequestedAproveSummary(\''+opt.id+'\','+orgLocationId+');">'+opt.empName+'('+opt.empType+')</div><i class="fa fa-binoculars pull-right" ></i>'; 
				        		 result = result + '  </div>';
				        		 result = result + ' </td>';
				        		 
				        		 result = result + ' <td>';
				        		 result = result + '   <div title="Calendaer month" class="tblminwidth-75">'+opt.leaveType+'</div>';
				        		 result = result + ' </td>';
				        		 
				        		 result = result + ' <td>';
				        		 result = result + '   <div title="Leave Transfer" class="tbldata-dotdot-100">'+opt.duration+'</div>';
				        		 result = result + '</td>';
				        		 
				        		 result = result + ' <td>';
				        		 result = result + '     <div title="2" class="tblminwidth-75">'+opt.noOfDays+'</div>';	 
				        		 result = result + '  </td>';
				        		 


				        		 result = result + ' <td>';
				        		 result = result + '     <div title="2" class="tblminwidth-75">Leaves taken NONE</div>';	 
				        		 result = result + '  </td>';
				        		 
				        		 result = result + ' <td>';
				        		 result = result + '     <div title="2" class="tblminwidth-75">'+opt.approvedBy+'</div>';	 
				        		 result = result + '  </td>';
				        		 
				        		 result = result + ' <td>';
				        		 result = result + ' <div title="2" class="tblminwidth-75">'+opt.updatedDate+'</div>';	 
				        		 result = result + ' </td>';
				        		 
				        		 result = result + ' <td>';
				        		 result = result + '  <div class="tblminwidth-50">';
				        		 result = result + '<a href="javascript:void(0)" onclick="CancelLeave(\''+opt.id+'\');"><b class="proj_closed">Cancel</b></a>';
				        		 result = result + ' </div>';
				        		 result = result + '</td>';
				        		 
				        		 result = result + '  </tr>';
							}
					}
					result = result + '  </tbody>';
					result = result + ' </table>';
					$("#approvedLeavesDiv").empty();
					$("#approvedLeavesDiv").append(result);
					$("#loading").removeAttr("style");
					approvedLeavesTable();
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
		}); 
  }
  
function CancelLeave(id){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";

	result = result +"		<p>Are you sure you want to Cancel ?</p>";
	result = result +"		<textarea  id='reasonForCancel' name='reasonForCancel'  placeholder='Comments here' ></textarea>";
	result = result + ' <div id="reasonForCancelDiv"/>';
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="confirmCancel(\''+id+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
	
}

function confirmCancel(id){
	var reason = $("#reasonForCancel").val();
	var userId = '${SourceLeadWeb_userId}';
	if(reason != null && reason != ''){
		 $("#reasonForCancelDiv").empty();
		if(reason.length > 250){
		$("#reasonForCancelDiv").empty().append("<font color='red' style='padding: 5px 10px; '>The Entered values should not be more than 250 characters</font>") ; 
		}else{
			 $.ajax({
				   	type: "POST",
				    url: sourceleadUrl+"/storeCancelReason?"+tokenParamName+"="+tokenValue+"&id="+id+"&reason="+reason+"&userId="+userId,
		   		    success: function(data) {
		   		    	$("#popUp").empty();
		   			 	$(".project_inn_mask").fadeOut(200);
		   			 	location.replace(sourceleadUrl+"/employeeCancelLeaves");	
		   		    }
			     });
		}
	}
	else{
			$("#reasonForCancelDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
	}
}

function cancelTermination(){
	$("#popUp").empty();
	 $(".project_inn_mask").fadeOut(200);
}

</script>
  
<script>
function approvedLeavesTable(){ 
    $('#approvedLeavesTable').DataTable( { 
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
    $("#approvedLeavesTable").wrap("<div class='overflow_Auto width100percnt'></div>")
}
</script>
  
  
<jsp:include page="../masterFooter.jsp" />
