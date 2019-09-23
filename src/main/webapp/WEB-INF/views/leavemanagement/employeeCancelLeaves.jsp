<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
<script>
$(document).ready(function(){
   getCancelledEmployeesList();
});
</script>
 
 <!-- Content Area -->
  <section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Cancelled Leaves</h2>
      </div>
		<div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>"> Home</a> &raquo; <a href="<c:url value='${appPath}/leaveManagementReports'/>">Leave Management</a> &raquo; Cancelled Leaves</div>
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
                        <option value="/employeeRejectedLeaves">Rejected Leaves</option>
                        <option value="/employeeCancelLeaves" selected>Cancelled Leaves</option>
                    </select>
            </div>
        </li>
        <div class="clearfix"></div>
      </ul>
      <div class="clearfix"></div>
      </div>
    <div id="loading"></div> 
    <div class="clients_wrap_table datatable_filter">
    <div class='dynamic_search dynamic_search-new mt10'>
		 <input type='text' placeholder='Search' id='cancelledLeavesTableSearch'/>
		  <span class="cursorPointer" title="Search"><i class="fa fa-search" onclick="getCancelledEmployeesList();"></i></span>    
		 
		</div>
    <div id="cancelledLeavesDiv"></div>
  </div>
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
    
    <!-- Content Area Ends--> 
  </section>
  

  
<script>
function redirectlocation(location){
	window.location.href = sourceleadUrl + location;
}
</script>

<script>
  function getCancelledEmployeesList(){
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		var userId = '${SourceLeadWeb_userId}';
		var searchValue = $("#cancelledLeavesTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getEmployeeRequestedLeaves?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId+"&leaveStatus=CANCELLED&searchValue="+searchValue;
		prepareCancelledEmployeesList(url);
}
</script> 

<script>
	function prepareCancelledEmployeesList(url){
		 $("#cancelledLeavesDiv").removeAttr("style");
	 	 $("#cancelledLeavesDiv").empty();
	 	 var result = "";
	 	result = result +" <div class='remove_search'>";
	 	result = result +"</div>";
	 	result = result + '<table id="cancelledLeavesTable" class="display" cellspacing="0" width="100%" >';
	 	result = result + '<thead>';
			result = result + '<tr>';
			result = result + '<th>Employee Name</th>';
			result = result + '<th>Leave Type</th>';
			result = result + '<th>Duration</th>';
			result = result + '<th>No Of Days</th>';
// 			result = result + '<th>No Of Extra Days</th>';
			/* result = result + '<th>Leaves Taken</th>'; */
			result = result + '<th>Approved By</th>';
			result = result + '<th>Cancelled By</th>';
			result = result + '<th>Reason</th>';
			result = result + '</tr>';
			result = result + '</thead>	';
	        result = result +"</table>";
	      $("#cancelledLeavesDiv").append(result);
	      cancelledEmployeesPagination(url); 
	}
</script>

<script>
	function cancelledEmployeesPagination(url){
			var colIndex = 0;
		    $('#cancelledLeavesTable').DataTable( { 
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
	          /*   { "data": "leavesTaken"}, */
	            { "data": "approvedBy" },
	            { "data": "cancelledBy" },
	            { "data": "cancelReason" },
	            
	        ],
	        "columnDefs": [ {
		    	  	"targets": colIndex++,
		    	  	"data": "empName",
// 		    	  	"sortable":false,
		    	  	"render": function(data, type, opt, meta) {
		    	  		  var result = "";
		    	  		result = result + ' <div class="proj_show divmax-width">';
		    	  		result = result + '<div class="pull-left tbldata-dotdot cursorPointer" title="'+opt.empName+'('+opt.empType+')" onclick="getRequestedAproveSummary(\''+opt.leaveUUID+'\','+opt.orgLocationId+','+opt.totalBalLeaves+');">'+opt.empName+'('+opt.empType+')</div><i class="fa fa-binoculars pull-right" onclick="getRequestedAproveSummary(\''+opt.leaveUUID+'\','+opt.orgLocationId+','+opt.totalBalLeaves+');"></i>'; 
		    	  		result = result + ' </div>';
			              return result; 
		    	  	}
		      },{
		    	  "targets": colIndex++,
	              "data": "leaveType",
// 	              "sortable":false,
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
	            	 result = result + '   <div title="'+opt.duration+'" class="tbldata-dotdot-100">'+opt.duration+'</div>';
	            	return result; 
	            }
	          }, {
	              "targets": colIndex++,
	              "data": "noOfDays",
	              "sortable":false,
	              "render": function ( data, type, opt, meta ) {
	            	   var result = "";
	            	    result = result + '     <div class="tblminwidth-75">'+opt.appliedNoOfDays+'</div>';		 
	            	   return result; 
	              }
	            }, /* {
	                "targets": colIndex++,
	                "data": "noOfDays",
	                "render": function ( data, type, opt, meta ) {
	              	   var result = "";
	              	      result = result + '     <div class="tblminwidth-75">'+opt.extraLeavesTaken+'</div>';	 
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
		    	  	"data": "approvedBy",
// 		    	  	"sortable":false,
		    	  	"render": function(data, type, opt, meta) {
		    	  		var result = "";
		    	  		if(opt.approvedBy != null && opt.approvedBy !='' && opt.approvedBy !='undefined'){
		    	  		 result = result + '     <div title="'+opt.approvedBy+'" class="tblminwidth-75">'+opt.approvedBy+'</div>';
		    	  		}else{
		    	  			 result = result + '     <div class="tblminwidth-75">--</div>';
		    	  		}
	  	                return result; 
		    	  	}
		    	  },{
		    	  	"targets": colIndex++,
		    	  	"data": "cancelledBy",
// 		    	  	"sortable":false,
		    	  	"render": function(data, type, opt, meta) {
		    	  		var result = "";
		    	  		 result = result + ' <div title="'+opt.cancelledBy+'" class="tblminwidth-75">'+opt.cancelledBy+'</div>';	
		    	  		return result; 
		    	  	}
		    	  },{
			    	  	"targets": colIndex++,
			    	  	"data": "cancelReason",
			    	  	"sortable":false,
			    	  	"render": function(data, type, opt, meta) {
			    	  		var result = "";
			    	  		 result = result + ' <div title="'+opt.cancelReason+'" class="tblminwidth-75">'+opt.cancelReason+'</div>';	
			    	  		return result; 
			    	  	}
		    	  }], 
	                "scrollCollapse": false,
	            	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	            	"bLengthChange": true,
	            	"aaSorting": [[1,'desc']],
	            	"bDestroy": true,
	            	"ordering": true,
	            	"oLanguage": {
	                    "sSearch": "",
	                    "sZeroRecords": "", "sEmptyTable": ""
	               }
	            });
	        
	       $("#cancelledLeavesTable").wrap("<div class='overflow_Auto width100percnt'></div>")
	       $("input[type=search]").hide();
	       $("#cancelledLeavesTableSearch").keyup(function(e) {
	       	   if(e.which === 13) {
	       		getCancelledEmployeesList();
	       	   }
	       	});
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
  
  
<script>
function getRequestedAproveSummary(leaveUUID,orgLocationId,totalBalLeaves){
	   $.get(sourceleadUrl+"/getRequestedAproveSummary?"+tokenParamName+"="+tokenValue+"&leaveUUID="+leaveUUID+"&orgLocationId="+orgLocationId, function(response) {
		   if(response.response === "SUCCESS") {
			   if(response != null){
			$(".new_side_wrap").addClass("new_side_wrap_active");
			$(".side_mask").fadeIn(200);
			$("body").css('overflow-y', 'hidden');
			var data = response.object;
			 var result = "";	
			 result = result +' <div class="viewgrid-header pull-right zIndex positionRelative">';
// 			 result = result +' <span class="btn btn-primary btn-sm mr10">Approved</span>';
			 result = result +' <span class="btn btn-danger btn-sm mr10" title="CANCELLED" onClick="cancel()">CANCELLED</span>';
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
			 if(data.empName !=null && data.empName !='' && data.empName !='undefined'){
				 result = result +'  <span>'+data.empName+'</span>';	 
			 }else{
				 result = result +'  <span>--</span>';
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
			 if(data.empEmail !=null && data.empEmail !='' && data.empEmail !='undefined'){
				 result = result +'  <span>'+data.empEmail+'</span>';	 
			 }else{
				 result = result +'  <span>--</span>';
			 }
			 result = result +' </div>';
			 result = result +'</div>';
			 result = result +'<div class="col-md-12 mb">';
			 result = result +'<label class="col-md-2 control-label"><b>Role:</b></label>';
			 result = result +' <div class="col-md-8">';
			 if(data.empType !=null && data.empType !='' && data.empType !='undefined'){
				 result = result +'  <span>'+data.empType+'</span>';	 
			 }else{
				 result = result +'  <span>--</span>';
			 }
			 result = result +'</div>';
			 result = result +'</div>';
			 result = result +'<div class="col-md-12 mb">';
			 result = result +'<label class="col-md-2 control-label"><b>Leave Type:</b></label>';
			 result = result +'<div class="col-md-8">';
			 if(data.leaveType !=null && data.leaveType !='' && data.leaveType !='undefined'){
				 result = result +'  <span>'+data.leaveType+'</span>';	 
			 }else{
				 result = result +'  <span>--</span>';
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
			 if(data.duration !=null && data.duration !='' && data.duration !='undefined'){
				 result = result +'  <span>'+data.duration+'</span>';	 
			 }else{
				 result = result +'  <span>--</span>';
			 }
			 result = result +' </div>';
			 result = result +'</div>';
             result = result +' <div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>Reason:</b></label>';
             result = result +'<div class="col-md-8">';
        	 if(data.reason !=null && data.reason !='' && data.reason !='undefined'){
				 result = result +'  <span>'+data.reason+'</span>';	 
			 }else{
				 result = result +'  <span>--</span>';
			 }
             result = result +' </div>';
             result = result +' </div>';
            /*  result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>No of leaves taken:</b></label>';
             result = result +' <div class="col-md-8">';
             if(data.actualLeavesTaken !=null && data.actualLeavesTaken !='' && data.actualLeavesTaken !='undefined'){
				 result = result +'  <span>'+data.actualLeavesTaken+'</span>';	 
			 }else{
				 result = result +'  <span>--</span>';
			 }
             result = result +'</div>';
             result = result +'</div>'; */
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>This month:</b></label>';
             result = result +'<div class="col-md-8">';
             if(data.thisMonthLeavesTaken !=null && data.thisMonthLeavesTaken !='' && data.thisMonthLeavesTaken !='undefined'){
				 result = result +'  <span>'+data.thisMonthLeavesTaken+'</span>';	 
			 }else{
				 result = result +'  <span>0</span>';
			 }
             result = result +'</div>';
             result = result +'</div>';
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>Balance leaves:</b></label>';
             result = result +'<div class="col-md-8">';
             if(totalBalLeaves !=null && totalBalLeaves !='' && totalBalLeaves !='undefined'){
				 result = result +'  <span>'+totalBalLeaves+'</span>';	 
			 }else{
				 result = result +'  <span>0</span>';
			 }
             result = result +'</div>';
             result = result +'</div>';
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>Applied on:</b></label>';
             result = result +'<div class="col-md-8">';
             if(data.createdDate !=null && data.createdDate !='' && data.createdDate !='undefined'){
				 result = result +'  <span>'+data.createdDate+'</span>';	 
			 }else{
				 result = result +'  <span>--</span>';
			 }
             result = result +' </div>';
             result = result +'</div>';
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>Approvers:</b></label>';
             result = result +'<div class="col-md-8">';
             if(data.approverUsers !=null && data.approverUsers !='' && data.approverUsers !='undefined'){
				 result = result +'  <span>'+data.approverUsers+'</span>';	 
			 }else{
				 result = result +'  <span>--</span>';
			 }
             result = result +'</div>';
             result = result +'</div>';
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>Attachment:</b></label>';
             result = result +' <div class="col-md-8">';
//              result = result +' <span><a href="javascript:void(o)">demo.pdf<i class="fa fa-download padddingLeft cursorPointer"></i></a></span>';
             if(data.fileUrl != null && data.fileUrl != 'undefined' && data.fileUrl != ''){
            	 result = result+'<div class="tblicons_4"> <a download class="glyphicon glyphicon-download-alt" target="_blank" href=\"'+data.fileUrl+'\" title="Download"></a>&nbsp&nbsp';
            	 result = result + '<a href="#" title="Preview"><i class="fa fa-eye cursorPointer" onclick="viewFile(\''+data.fileUrl+'\', \''+data.fileName+'\', \''+data.fileContentType+'\')"></i></a></div>';
             }else{
            	 result = result +'<span>--</span>'; 
             }
             result = result +'</div>';
             result = result +' </div>';
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>Approved by:</b></label>';
             result = result +'<div class="col-md-8">';
             if(data.approvedBy !=null && data.approvedBy !='' && data.approvedBy !='undefined'){
				 result = result +'  <span>'+data.approvedBy+'</span>';	 
			 }else{
				 result = result +'  <span>--</span>';
			 }
             result = result +'</div>';
             result = result +'</div>';
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>Approved on:</b></label>';
             result = result +'<div class="col-md-8">';
             if(data.updatedDate !=null && data.updatedDate !='' && data.updatedDate !='undefined'){
				 result = result +'  <span>'+data.updatedDate+'</span>';	 
			 }else{
				 result = result +'  <span>--</span>';
			 }
             result = result +'</div>';
             result = result +'</div>';
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>Cancelled by:</b></label>';
             result = result +'<div class="col-md-8">';
             if(data.cancelledBy !=null && data.cancelledBy !='' && data.cancelledBy !='undefined'){
				 result = result +'  <span>'+data.cancelledBy+'</span>';	 
			 }else{
				 result = result +'  <span>--</span>';
			 }
             result = result +'</div>';
             result = result +'</div>';
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>Cancelled on:</b></label>';
             result = result +'<div class="col-md-8">';
             if(data.updatedDate !=null && data.updatedDate !='' && data.updatedDate !='undefined'){
				 result = result +'  <span>'+data.updatedDate+'</span>';	 
			 }else{
				 result = result +'  <span>--</span>';
			 }
             result = result +'</div>';
             result = result +'</div>';
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>Cancelled reason:</b></label>';
             result = result +'<div class="col-md-8">';
             if(data.deleteReason !=null && data.deleteReason !='' && data.deleteReason !='undefined'){
				 result = result +'  <span>'+data.deleteReason+'</span>';	 
			 }else{
				 result = result +'  <span>--</span>';
			 }
             result = result +'</div>';
             result = result +'</div>';
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
  
  
 <!--  <script>
function cancelledLeavesTable(){ 
    $('#cancelledLeavesTable').DataTable( { 
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
    $("#cancelledLeavesTable").wrap("<div class='overflow_Auto width100percnt'></div>")
}
</script> -->



<jsp:include page="../masterFooter.jsp" />
