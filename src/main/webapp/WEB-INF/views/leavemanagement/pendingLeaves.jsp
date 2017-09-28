<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">
$(document).ready(function(){
	getPendingLeavesList();
});
</script>

 <section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Pending</h2>
      </div>
		<div class="pull-right brud-crum"><a href="dashboard_us.html">Home</a> &raquo; <a href="dashboard-lavemanagement.html">Pending </a> &raquo; Leave Type</div>
    </section>
    
<div class="content-area content-new clearfix">

<div class="timesheet_out">
  <div class="clearfix">
    <div class="timesheet_head">
      <ul>
         <li class="dropdown">
                <div class="pull-left select-fild">
                        <select onchange="redirectlocation(this.value);">
                        <option value="/pendingLeaves" selected>Pending Leaves</option>
                        <option  value="/approvedLeaves">Approved Leaves</option>
                        <option value="/rejectedLeaves">Rejected Leaves</option>
                        <option value="/cancelLeaves">Cancel Leaves</option>
                    </select>
                </div>
         </li>
        <div class="clearfix"></div>
      </ul>
      <div class="clearfix"></div>
    </div>
    <div class="clients_wrap_table datatable_filter">
    	<div id="pendingListDiv"></div>
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
function getPendingLeavesList(){
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var userId = '${SourceLeadWeb_user.id}';
	$.get(sourceleadUrl+"/getLeavesListByLocationIdAndStatus?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&status=PENDING&userId="+userId, function(data) {
		var result = "";				        	
				result = result + '<table id="pendingListTable" class="display" cellspacing="0" width="100%" >';
				result = result + '<thead>';
				result = result + '<tr>';
				result = result +'<th>Leave Type</th>';
				result = result +'<th>Duration</th>';
				result = result +'<th>Applied On</th>';
				result = result + '<th>Reason</th>';
				result = result + '<th>Approvers</th>';
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
			        		 result = result + '  <div title="'+opt.status+'" class="tblminwidth-75">';
			        		 result = result +' <b class="proj_process">'+opt.status+'</b> ';
			        		 result = result + '  </div>';
			        		 result = result + ' </td>';
			        		 result = result + '  </tr>';
						}
				}
	        	
				result = result + '  </tbody>';
				result = result + ' </table>';
				$("#pendingListDiv").empty();
				$("#pendingListDiv").append(result);
				$("#loading").removeAttr("style");
				pendingListPagination();
		}); 
 }
</script>

<script>
function pendingListPagination(){ 
    $('#pendingListTable').DataTable( { 
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
    $("#pendingListTable").wrap("<div class='overflow_Auto width100percnt'></div>")
}
</script>





<jsp:include page="../masterFooter.jsp" />