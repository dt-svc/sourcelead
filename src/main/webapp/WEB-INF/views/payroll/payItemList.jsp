<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
$(document).ready(function(){
   getPayItemList();
});
</script>
  
   <!-- Content Area -->
<section class="content-wraper contentWraper">
<section class="content-header clearfix">
<div class="pull-left">
<h2>Pay Item list</h2>
</div>
        <div class="pull-right brud-crum">
                <a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;<a
				href="<c:url value='${appPath}/payrollConfiguration'/>"> Payroll Configuration</a>&raquo; Pay Item list
				</div>
</section>

<!-- Timesheet -->

<div class="timesheet_out">

<div class="tablelist-wrap">
<div class="timesheetHead minHeight40 mt minHeight40 removPad">
<div class="pull-right">
<ul class="list-unstyled list-inline">
<li>
<!-- <div class="dynamic_search">
<input placeholder="Search" type="text">
<i class="fa fa-search"></i>
</div>  -->             
</li>
<li>
	<a href="<c:url value='${appPath}/addPayItem'/>" class="addBtn">+ Create</a>
</li>
</ul>
</div>
</div>

<div class="success_pop" id="payItemMsgDiv" style="display: none;">
	<i class="fa fa-check-circle"></i><span id="payItemMsg"></span>
</div>

<div id="loading"></div>
<div class="project_wrap_table datatable_filter" >
<div id="payItemDiv"></div>
</div>
</div>
</div>  
</section>
<!-- Content Area Ends--> 


 <script>
  function getPayItemList(){
	   $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.get(sourceleadUrl+"/getPayItemList?"+tokenParamName+"="+tokenValue+"", function(response) {
			  if (response.response === "AWKWARD") {
			    location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				var result = "";				        	
				result = result + '<table id="payItemTable" class="display" cellspacing="0" width="100%" >';
				result = result + '<thead>';
				result = result + '<tr>';
				result = result + '<th>Name</th>';
				result = result + '<th>Description</th>';
				result = result + '<th>Override</th>';
				result = result + '<th>Emp Type</th>';
				result = result + '<th>Schedule</th>';
				result = result + '<th>Action</th>';
				result = result + '</tr>';
				result = result + '</thead>	';
				result = result + '<tbody>';
				
				if(response !=null && response !=''){
					 for (var i=0; i<response.data.length; i++)
						{
			        		 var opt = response.data[i];
			        		 result = result + ' <tr>';
			        		 result = result + ' <td>';
			        		 result = result + ' <div class="proj_show tbldata-dotdot-big">';
			        		 if(opt.payItemName !=null && opt.payItemName !='' && opt.payItemName !='undefined'){
			        			 result = result + '     <div class="pull-left tbldata-dotdot-me" title="'+opt.payItemName+'">'+opt.payItemName+'</div>'; 
			        		 }else{
			        			 result = result + '     <div class="pull-left tbldata-dotdot-me" title="Sick Leave">--</div>';
			        		 }
			        		 result = result + '  </div>';
			        		 result = result + ' </td>';
			        		 result = result + ' <td>';
			        		 result = result + '   <div title="'+opt.description+'" class="tblminwidth-75">'+opt.description+'</div>';
			        		 result = result + ' </td>';
			        		 result = result + ' <td>';
			        		 if(opt.allowOverTime != null && opt.allowOverTime != '' && opt.allowOverTime != 'undefined'){
			        			 result = result + '   <div title="'+opt.allowOverTime+'" class="tblminwidth-75">'+opt.allowOverTime+'</div>';
			        		 }else{
			        			 result = result + '   <div class="tblminwidth-75">--</div>';
			        		 }
			        		 result = result + ' </td>';
			        		 result = result + ' <td>';
			        		 result = result + '   <div title="'+opt.empType+'" class="tbldata-dotdot-100">'+opt.empType+'</div>';
			        		 result = result + '</td>';
			        		 result = result + ' <td>';
			        		 if(opt.paySchedule !=null && opt.paySchedule != 'undefined' && opt.paySchedule != ''){
			        			 if(opt.paySchedule == 'EVERY_PAY'){
			        				 result = result + '     <div title="Every Pay" class="tblminwidth-75">Every Pay</div>';
			        			 }else if(opt.paySchedule == 'MONTHLY'){
			        				 result = result + '     <div title="Monthly" class="tblminwidth-75">Monthly</div>';
			        			 }else if(opt.paySchedule == 'QUARTERLY'){
			        				 result = result + '     <div title="Quarterly" class="tblminwidth-75">Quarterly</div>';
			        			 }else if(opt.paySchedule == 'ANNUALLY'){
			        				 result = result + '     <div title="Annually" class="tblminwidth-75">Annually</div>';
			        			 }
			        		 }else{
			        			 result = result + '     <div title="2" class="tblminwidth-75">--</div>';	
			        		 }
			        		 
			        		 result = result + '  </td>';
			        		 result = result + '<td>';
			        		 result = result + '<ul class="list-unstyled list-inline mb0">';
			        		 result = result + '<li><a onclick="redirectPayItem(\''+opt.payItemUUID+'\');" title="Edit" class="anchor-black"><i class="fa fa-pencil"></i></a></li>';
			        		 result = result + '<li><a onclick="deletePayItem(\''+opt.payItemUUID+'\');" title="Delete" class="text-danger"><i class="fa fa-trash"></i></a></li>';
			        		 result = result + '</ul>';
			        		 result = result + '</td>';
			        		 result = result + '  </tr>';
						}
				}
	        	
				result = result + '  </tbody>';
				result = result + ' </table>';
				$("#payItemDiv").empty();
				$("#payItemDiv").append(result);
				$("#loading").removeAttr("style");
				viewPayItemPagination();
         }else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
			    showError("Operation failed, please try again");
			} 
		}); 
  }
  </script>
  
  <script>
function viewPayItemPagination(){ 
    $('#payItemTable').DataTable( { 
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
    $("#viewOptionsTable").wrap("<div class='overflow_Auto width100percnt'></div>")
}

function redirectPayItem(payItemUUID){
	var sourceleadUrl = '${sourceleadUrl}';
	window.location.href = sourceleadUrl + "/editPayItem?&payItemUUID="+payItemUUID;
}

function deletePayItem(payItemUUID){
	var sourceleadUrl = '${sourceleadUrl}';
	$.get(sourceleadUrl+"/deletePayItem?"+tokenParamName+"="+tokenValue+"&payItemUUID="+payItemUUID,  function(result) {
		 if (result.response === "AWKWARD") {
            location.href = "/errorPage";
        } else if (result.response === "SUCCESS") {
        	getPayItemList();
        	$("#payItemMsgDiv").removeAttr("style");
 			$("#payItemMsgDiv").css({ display: "block" });
 			$("#payItemMsg").html("Payitem deleted Successfully..!!");
 			payItemMsgFadein();
        }
	});
}
function payItemMsgFadein() {
	$("#payItemMsgDiv").fadeIn(200);
    $('#payItemMsgDiv').delay(2000).fadeOut('slow');
}
</script>
<jsp:include page="../masterFooter.jsp" />


 		