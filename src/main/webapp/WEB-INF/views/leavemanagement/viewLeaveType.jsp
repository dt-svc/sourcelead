<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<script type="text/javascript">
$(document).ready(function(){
   getLeaveTypesList();
});
</script>

 <!-- Content Area -->
  <section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Leave Type</h2>
      </div>
		<div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo; <a href="<c:url value='/leaveConfiguration'/>">Leave Management</a> &raquo; Leave Type</div>
    </section>
    
<div class="content-area content-new clearfix">

<div class="timesheet_out">
  <div class="clearfix">
    <div class="timesheet_head">
      <ul>
          <li>
              <a href="<c:url value='/addLeaveType'/>" title="Add Leave Type">
                <i class="fa fa-plus paddingRight"></i>Add Leave Type
              </a>
          </li>
        <div class="clearfix"></div>
      </ul>
      <div class="clearfix"></div>
    </div>
    
    <div class="clients_wrap_table">
    <div id="loading"></div>
      <div id="leaveTypeListTableDiv"></div>
    </div>
    
  </div>
</div>

    </div>
    <!-- Content Area Ends--> 
  </section>
  <!-- Content Area Ends--> 
  
  
 <script>
 function getLeaveTypesList(){
	 var locationId = '${SourceLeadWeb_orgLocationId}';
	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:999;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.get( sourceleadUrl+ "/getLeaveTypesByLocationid?&orgLocationId="+locationId, function(data) {
	     if(data != null && data != '' && data != 'undefined'){
	    	 
	    	 var result = "";
	    	 result += '<table id="leaveTypeListTable" class="display" cellspacing="0" width="100%" >';
	         result += '<thead>';
	         result += '  <tr>';
	         result += '    <th>Name</th>';
	         result += '    <th>Duration</th>';
	         result += '    <th>Type</th>';
	         result += '    <th>No.of Leaves</th>';
	         result += '    <th>Status</th>';
	         result += '    <th>Action</th>';
	         result += '  </tr>';
	         result += '</thead>';
	         result += '<tbody>';
	         
	    	 for(i=0;i < data.length;i++){
	    		 var report = data[i];
	    		 result += '<tr>';
		         result += '    <td>';
		         result += '      <div class="proj_show tbldata-dotdot-big">';
		         if(report.name != null && report.name != '' && report.name != 'undefined'){
		        	 result += '          <div class="pull-left tbldata-dotdot-me" title="'+report.name+'">'+report.name+'</div>';
		         } 
		         result += '          <i class="fa fa-binoculars pull-right"></i>';
		         result += '      </div>';
		         result += '    </td>';
		         result += '    <td>';
		         if(report.duration != null && report.duration != '' && report.duration != 'undefined'){
		        	 result += '        <div title="'+report.duration+'" class="tblminwidth-75">'+report.duration+'</div>';
		         }
		         result += '    </td>';
		         result += '    <td>';
		         if(report.leaveType != null && report.leaveType != '' && report.leaveType != 'undefined'){
		        	 result += '      <div title="'+report.leaveType+'" class="tbldata-dotdot-100">'+report.leaveType+'</div>'; 
		         }
		         result += '    </td>';
		         result += '    <td>';
		         if(report.numbefrOfLeavesPeryear != null && report.numbefrOfLeavesPeryear != '' && report.numbefrOfLeavesPeryear != 'undefined'){
		        	 result += '        <div title="'+report.numbefrOfLeavesPeryear+'" class="tblminwidth-75">'+report.numbefrOfLeavesPeryear+'</div>'; 
		         }
		         result += '    </td>';
		         result += '    <td>';
		         if(report.status != null && report.status != '' && report.status != 'undefined'){
		        	 if(report.status == 1){
		        		 result += '        <div title="Active" class="tblminwidth-75"><b class="proj_open">Active</b></div>';
		        	 }else{
		        		 result += '        <div title="In Active" class="tblminwidth-75"><b class="proj_closed">In Active</b></div>';
		        	 }
		        	
		         }
		         result += '    </td>';
		         result += '    <td>';
		         result += '        <div class="tblminwidth-50">';
		         result += '            <span title="Edit" class="paddingRight"><a href="'+sourceleadUrl+'/editLeaveType?leaveTypeId='+report.id+'"><i class="fa fa-pencil text-primary cursorPointer"></i></a></span> ';
		         result += '            <span title="Delete" class="paddingLeft"><i class="fa fa-trash text-danger cursorPointer"></i></span>';
		         result += '        </div>';
		         result += '    </td>';
		         result += '  </tr>';
	    	 }
	    	
	         result += '  </tbody>                                                                                                         ';
       		 result += '</table> ';
       		 $("#leaveTypeListTableDiv").empty();
       		 $("#leaveTypeListTableDiv").append(result);
       		 $("#loading").removeAttr('style');
       		 listTypePagination();
	    	 
	     }
     });
 }
 </script>
  
 <script>
 function listTypePagination(){
		$('#leaveTypeListTable').DataTable({ 
			"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
	    	"bLengthChange": false,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": ""
	     	}
	    });
		$("#leaveTypeListTable").wrap("<div class='overflow_Auto' style='width:100%;'></div>");  

	}
 </script>
  
<jsp:include page="../masterFooter.jsp" />
