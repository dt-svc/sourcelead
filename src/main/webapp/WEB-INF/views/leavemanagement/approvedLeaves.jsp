<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">
$(document).ready(function(){
	getApprovedLeavesList();
});
</script>

 <!-- Content Area -->
  <section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Approved</h2>
      </div>
		<div class="pull-right brud-crum"><a href="dashboard_us.html">Home</a> &raquo; <a href="dashboard-lavemanagement.html">Leave Management</a> &raquo; Approved</div>
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
                        <option value="/approvedLeaves" selected>Approved Leaves</option>
                        <option value="/rejectedLeaves">Rejected Leaves</option>
                        <option value="/cancelLeaves">Cancel Leaves</option>
                    </select>
                </div>
         </li>
        <div class="clearfix"></div>
      </ul>
      <div class="clearfix"></div>
      <div class="remove_search">
        <input placeholder="Search" type="text">
      </div>
    </div>
    <div class="require_wrap_table">
      <table>
        <thead>
          <tr>
            <th>Leave Type</th>
            <th>Duration</th>
            <th>Applied On</th>
            <th>Reason</th>
            <th>Approvers</th>
            <th>Approved By</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
        <tr>
            <td>
              <div class="proj_show tbldata-dotdot-big">
                  <div class="pull-left tbldata-dotdot-me" title="">Non Billable</div>
                  <i class="fa fa-binoculars pull-right"></i>
              </div>
            </td>
            <td>
                <div title="Duration" class="tblminwidth-75">
                   <div class="width100percnt">
                        09/10/2017
                    </div>
                    <div class="width100percnt mt2">
                        09/12/2017
                    </div>
                </div>
            </td>
            <td>
              <div title="" class="tbldata-dotdot-100">
                    08/20/2017
                </div>
            </td>
            <td>
                <div title="" class="tbldata-dotdot-100">
                    Personal
                </div>
            </td>
            <td>
                <div title="" class="tbldata-dotdot-100">
                    Approvers
                </div>
            </td>
            <td>
                <div title="" class="tbldata-dotdot-100">
                   Admin
                </div>
            </td>
            <td>
                <div title="10" class="tblminwidth-75">
                    <b class="proj_active">Approved</b>
                </div>
            </td>
            <td class="hidden">
                <div class="tblminwidth-50">
                    <span title="Edit" class="paddingRight"><i class="fa fa-pencil text-primary cursorPointer"></i></span>
                    <span title="Delete" class="paddingLeft"><i class="fa fa-trash text-danger cursorPointer"></i></span>
                </div>
            </td>
          </tr>
          </tbody>
      </table>
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
function getApprovedLeavesList(){
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var userId = '${SourceLeadWeb_user.id}';
	$.get(sourceleadUrl+"/getLeavesListByLocationIdAndStatus?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&status=PENDING&userId="+userId, function(data) {
		var result = "";				        	
				result = result + '<table id="approvedListTable" class="display" cellspacing="0" width="100%" >';
				result = result + '<thead>';
				result = result + '<tr>';
				result = result +'<th>Leave Type</th>';
				result = result +'<th>Duration</th>';
				result = result +'<th>Applied On</th>';
				result = result + '<th>Reason</th>';
				result = result + '<th>Approvers</th>';
				result = result + '<th>Approved By</th>';
				result = result + '<th>Status</th>';
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
			        		 if(opt.leaveTypeName !=null && opt.leaveTypeName !='' && opt.leaveTypeName !='undefined'){
			        			 result = result + '     <div class="pull-left tbldata-dotdot-me" title="'+opt.leaveTypeName+'">'+opt.leaveTypeName+'</div>'; 
			        		 }else{
			        			 result = result + '     <div class="pull-left tbldata-dotdot-me" title="">--</div>';
			        		 }
			        		 result = result + '  </div>';
			        		 result = result + ' </td>';
			        		 result = result + ' <td>';
				        		 result = result + ' <div title="'+opt.fromDateStr+' - '+opt.toDateStr+'" class="tblminwidth-75 mt2">';
					        		 result = result + ' <div class="width100percnt">'+opt.fromDateStr+'</div>';
					        		 result = result + ' <div class="width100percnt mt2">'+opt.toDateStr+'</div>';
				        		 result = result + ' </div>';
			        		 result = result + ' </td>';
			        		 result = result + ' <td>';
			        		 result = result + '   <div title="'+opt.createdDateStr+'" class="tbldata-dotdot-100">'+opt.createdDateStr+'</div>';
			        		 result = result + '</td>';
			        		 result = result + ' <td>';
			        		 if(opt.reason !=null && opt.reason != 'undefined' && opt.reason != ''){
			        			 result = result + '     <div title="'+opt.reason+'" class="tbldata-dotdot-vsm">'+opt.reason+'</div>';	 
			        		 }else{
			        			 result = result + '     <div title="" class="tblminwidth-75">--</div>';	
			        		 }
			        		 result = result + '  </td>';
			        		 result = result + ' <td>';
			        		 if(opt.approverUsers !=null && opt.approverUsers != 'undefined' && opt.approverUsers != ''){
			        			 
			        			 result = result + '     <div title="'+opt.approverUsers+'" class="tbldata-dotdot">'+opt.approverUsers+'</div>';	 
			        		 }else{
			        			 result = result + '     <div title="" class="tblminwidth-75">--</div>';	
			        		 }
			        		 result = result + '  </td>';
			        		 result = result + ' <td>';
			        		 if(opt.approverUsers !=null && opt.approverUsers != 'undefined' && opt.approverUsers != ''){
			        			 
			        			 result = result + '     <div title="'+opt.approverUsers+'" class="tbldata-dotdot">'+opt.approverUsers+'</div>';	 
			        		 }else{
			        			 result = result + '     <div title="" class="tblminwidth-75">--</div>';	
			        		 }
			        		 result = result + '  </td>';
			        		 result = result + ' <td>';
			        		 result = result + '  <div title="'+opt.status+'" class="tblminwidth-75">';
			        		 result = result +' <b class="proj_process">'+opt.status+'</b> ';
			        		 result = result + '  </div>';
			        		 result = result + ' </td>';
			        		 result = result + '  </tr>';
						}
				}
	        	
				result = result + '  </tbody>';
				result = result + ' </table>';
				$("#approvedListDiv").empty();
				$("#approvedListDiv").append(result);
				$("#loading").removeAttr("style");
				approvedListPagination();
		}); 
 }
</script>

<script>
function approvedListPagination(){ 
    $('#approvedListTable').DataTable( { 
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
    $("#approvedListTable").wrap("<div class='overflow_Auto width100percnt'></div>")
}
</script>




<jsp:include page="../masterFooter.jsp" />