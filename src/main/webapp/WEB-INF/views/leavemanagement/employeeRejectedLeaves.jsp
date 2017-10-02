<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
<script>
$(document).ready(function(){
   getRejectedEmployeesList();
});
</script>

<!-- Content Area -->
  <section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Rejected</h2>
      </div>
		<div class="pull-right brud-crum"><a href="dashboard_us.html">Home</a> &raquo; <a href="dashboard-lavemanagement.html">Leave Management</a> &raquo; Rejected</div>
    </section>
    
<div class="content-area content-new clearfix">

<div class="timesheet_out">
  <div class="clearfix">
    <div class="timesheet_head">
      <ul>
          <li class="dropdown">
          <div class="pull-left select-fild">
                    <select onchange="redirectlocation(this.value);">
                        <option value="/employeeRequestedLeaves">Requested Leaves</option>
                        <option  value="/employeeApprovedLeaves">Approved Leaves</option>
                        <option value="/employeeRejectedLeaves" selected>Rejected Leaves</option>
                        <option value="/employeeCancelLeaves">Cancel Leaves</option>
                    </select>
            </div>
          </li>
        <div class="clearfix"></div>
      </ul>
      <div class="clearfix"></div>
      <div id="loading"></div> 
    <div class="clients_wrap_table datatable_filter">
    <div id="rejectedLeavesDiv"></div>
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
  function getRejectedEmployeesList(){
	  var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	  var userId = '${SourceLeadWeb_userId}';
	  var leaveStatus = "REJECTED";
	  $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.get(sourceleadUrl+"/getEmployeeRequestedLeaves?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId+"&leaveStatus=REJECTED", function(data) {
				 var result = "";				        	
					result = result + '<table id="rejectedLeavesTable" class="display" cellspacing="0" width="100%" >';
					result = result + '<thead>';
					result = result + '<tr>';
					result = result + '<th>Employee Name</th>';
					result = result + '<th>Leave Type</th>';
					result = result + '<th>Duration</th>';
					result = result + '<th>No Of Days</th>';
					result = result + '<th>Leaves Taken</th>';
					result = result + '<th>Rejected By</th>';
					result = result + '<th>Reason</th>';
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
				        		 result = result + ' <div title="2" class="tblminwidth-75">'+opt.rejectedBy+'</div>';	 
				        		 result = result + ' </td>';
				        		 
				        		 result = result + ' <td>';
				        		 result = result + ' <div title="2" class="tblminwidth-75">'+opt.reason+'</div>';	 
				        		 result = result + ' </td>';
				        		 
				        		 result = result + '  </tr>';
							}
					}
					result = result + '  </tbody>';
					result = result + ' </table>';
					$("#rejectedLeavesDiv").empty();
					$("#rejectedLeavesDiv").append(result);
					$("#loading").removeAttr("style");
					rejectedLeavesTable();
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
  </script>
  
  <script>
function rejectedLeavesTable(){ 
    $('#rejectedLeavesTable').DataTable( { 
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
    $("#rejectedLeavesTable").wrap("<div class='overflow_Auto width100percnt'></div>")
}
</script>

<jsp:include page="../masterFooter.jsp" />
