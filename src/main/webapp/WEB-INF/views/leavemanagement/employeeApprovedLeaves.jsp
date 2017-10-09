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
		<div class='remove_search mt10'>
		 <input type='text' placeholder='Search' id='approvedLeavesTableSearch'/>
		</div>
    <div id="approvedLeavesDiv"></div>
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
		var searchValue = $("#approvedLeavesTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getEmployeeRequestedLeaves?"+tokenParamName+"="+tokenValue+"&searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&userId="+userId+"&leaveStatus=APPROVED";
		prepareApprovedEmployeesList(url);
}
</script> 

<script>
function prepareApprovedEmployeesList(url){
	 $("#approvedLeavesDiv").removeAttr("style");
 	 $("#approvedLeavesDiv").empty();
 	 var result = "";
 	result = result +" <div class='remove_search'>";
 	result = result +"</div>";
 	result = result + '<table id="approvedLeavesTable" class="display" cellspacing="0" width="100%" >';
 	result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Employee Name</th>';
		result = result + '<th>Leave Type</th>';
		result = result + '<th>Duration</th>';
		result = result + '<th>No Of Days</th>';
		result = result + '<th>Leaves Taken</th>';
		result = result + '<th>Approved By</th>';
		result = result + '<th>Approved On</th>';
		result = result + '<th>Action</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
        result = result +"</table>";
      $("#approvedLeavesDiv").append(result);
      approvedEmployeesPagination(url); 
}
</script>

<script>
function approvedEmployeesPagination(url){
		var colIndex = 0;
	    $('#approvedLeavesTable').DataTable( { 
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
            { "data": "approvedBy" },
            { "data": "updatedDate" },
            { "data": "action"},
            
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "empName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		  result = result + ' <div class="pull-left tbldata-dotdot-me cursorPointer" title="Sick Leave" onclick="getRequestedAproveSummary(\''+opt.id+'\','+opt.orgLocationId+');">'+opt.empName+'('+opt.empType+')</div><i class="fa fa-binoculars pull-right" ></i>'; 
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
            	 result = result + '   <div title="Leave Transfer" class="tbldata-dotdot-100">'+opt.duration+'</div>';
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
	    	  	"data": "approvedBy",
	    	  	"render": function(data, type, opt, meta) {
	    	  		 var result = "";
	    	  		result = result + '     <div title="2" class="tblminwidth-75">'+opt.approvedBy+'</div>';
  	                return result; 
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "updatedDate",
	    	  	"render": function(data, type, opt, meta) {
	    	  		 var result = "";
	    	  		result = result + ' <div title="2" class="tblminwidth-75">'+opt.updatedDate+'</div>';	
	    	  		return result; 
	    	  	}
	    	  }, {
                  "targets": colIndex++,
                  "data": "action",
                  "render": function ( data, type, opt, meta ) {
                	   var result = "";
                	   result = result + '<a href="javascript:void(0)" onclick="CancelLeave(\''+opt.id+'\');"><b class="proj_closed">Cancel</b></a>';
                	    return result; 
                  }
                }],
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
       $("input[type=search]").hide();
       $("#approvedLeavesTableSearch").keyup(function(e) {
       	   if(e.which === 13) {
       		getApprovedEmployeesList();
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
  
  <!-- <script>
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
				        		 result = result + '     <div title="2" class="tblminwidth-75">'+opt.leavesTaken+'</div>';	 
				        		 result = result + '  </td>';
				        		 
				        		 result = result + ' <td>';
				        		 result = result + '     <div title="2" class="tblminwidth-75">'+opt.approvedBy+'</div>';	 
				        		 result = result + '  </td>';
				        		 
				        		 result = result + ' <td>';
				        		 result = result + ' <div title="2" class="tblminwidth-75">'+opt.updatedDate+'</div>';	 
				        		 result = result + ' </td>';
				        		 
				        		 result = result + ' <td>';
// 				        		 '<sec:authorize access="hasAnyRole(\'ROLE_APPROVE_LEAVE\')">';
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
  </script> -->
<script>
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
</script>

<script>
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
</script>

<script>
function cancelTermination(){
	$("#popUp").empty();
	 $(".project_inn_mask").fadeOut(200);
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
			 result = result +' <span class="btn btn-primary btn-sm mr10">Approved</span>';
			 result = result +' <span class="btn btn-default btn-sm" onClick="CancelLeave(\''+leaveId+'\')">Cancel</span>';
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
			 result = result +'<span>Contract to Hire</span>';
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
             result = result +'<span>XXXXXXX</span>';
             result = result +'</div>';
             result = result +'</div>';
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>This month:</b></label>';
             result = result +'<div class="col-md-8">';
             result = result +' <span>XXXXX</span>';
             result = result +'</div>';
             result = result +'</div>';
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>Balance leaves:</b></label>';
             result = result +'<div class="col-md-8">';
             result = result +' <span>'+data.balanceleaves+'</span>';
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
             result = result +' <span>Srinivas Vemula</span>';
             result = result +'</div>';
             result = result +'</div>';
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>Attachment:</b></label>';
             result = result +' <div class="col-md-8">';
//              result = result +' <span><a href="javascript:void(o)">demo.pdf<i class="fa fa-download padddingLeft cursorPointer"></i></a></span>';
             if(data.fileUrl != null && data.fileUrl != 'undefined' && data.fileUrl != ''){
            	 result = result +'<span><a href="'+data.fileUrl+'">'+data.fileName+'<i class="fa fa-download padddingLeft cursorPointer"></i></a></span>';
             }else{
            	 result = result +'<span>--</span>'; 
             }
             result = result +'</div>';
             result = result +' </div>';
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>Approved by:</b></label>';
             result = result +'<div class="col-md-8">';
             result = result +' <span>'+data.approvedBy+'</span>';
             result = result +'</div>';
             result = result +'</div>';
             result = result +'<div class="col-md-12 mb">';
             result = result +'<label class="col-md-2 control-label"><b>Approved on:</b></label>';
             result = result +'<div class="col-md-8">';
             result = result +' <span>'+data.updatedDate+'</span>';
             result = result +'</div>';
             result = result +'</div>';
             result = result +'<div class="clearfix"></div>';
             result = result +'</div>';
             $("#leaveSummaryId").empty();
             $("#leaveSummaryId").append(result);
            
		}	
		 
	});  
}
</script>
  
<!-- <script>
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
</script> -->

  
<jsp:include page="../masterFooter.jsp" />
