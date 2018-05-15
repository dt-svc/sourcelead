<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
<script>
$(document).ready(function(){
  viewOptions();
});
</script>

<!-- Content Area -->
  <section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Leave Options</h2>
      </div>
		<div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo; <a href="<c:url value='${appPath}/leaveConfiguration'/>">Leave Configuration</a> &raquo; Leave Options</div>
    </section>
    
<div class="content-area content-new clearfix">

<div class="timesheet_out">
  <div class="clearfix">
    <div class="timesheet_head">
      <ul>
          <li>
              <a href="<c:url value='${appPath}/addOptions'/>" title="Add Leave Type"><i class="fa fa-plus paddingRight"></i>Add Leave options</a>
          </li>
        <div class="clearfix"></div>
      </ul>
      <div class="clearfix"></div>
      <!-- <div class="remove_search">
        <input placeholder="Search" type="text">
      </div> -->
    </div>
        <div id="loading"></div> 
    <div class="candidate_wrap_table datatable_filter">
    <div id="viewOptinsDiv"></div>
    </div>
  </div>
</div>

    </div>
    <!-- Content Area Ends--> 
  </section>
  <!-- Content Area Ends--> 
  
  <script>
  function viewOptions(){
	    var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		var userId = '${SourceLeadWeb_userId}';
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.get(sourceleadUrl+"/viewLeaveOption?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId, function(response) {
			 if (response.response === "AWKWARD") {
			    location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
        	 var result = "";				        	
				result = result + '<table id="viewOptionsTable" class="display" cellspacing="0" width="100%" >';
				result = result + '<thead>';
				result = result + '<tr>';
				result = result + '<th>Department</th>';
				result = result + '<th>Calendar Month</th>';
				result = result + '<th>Leave Transfer</th>';
				result = result + '<th>Working Hours</th>';
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
			        		 if(opt.department !=null && opt.department !='' && opt.department !='undefined'){
			        			 result = result + '     <div class="pull-left tbldata-dotdot-me" title="Sick Leave">'+opt.department+'</div>'; 
			        		 }else{
			        			 result = result + '     <div class="pull-left tbldata-dotdot-me" title="Sick Leave">--</div>';
			        		 }
			        		 result = result + '  </div>';
			        		 result = result + ' </td>';
			        		 result = result + ' <td>';
			        		 result = result + '   <div title="Calendaer month" class="tblminwidth-75">'+opt.calendarMonth+'</div>';
			        		 result = result + ' </td>';
			        		 result = result + ' <td>';
			        		 result = result + '   <div title="Leave Transfer" class="tbldata-dotdot-100">'+opt.leaveTransfer+'</div>';
			        		 result = result + '</td>';
			        		 result = result + ' <td>';
			        		 if(opt.workingHours !=null && opt.workingHours != 'undefined' && opt.workingHours != ''){
			        			 result = result + '     <div title="2" class="tblminwidth-75">'+opt.workingHours+'</div>';	 
			        		 }else{
			        			 result = result + '     <div title="2" class="tblminwidth-75">--</div>';	
			        		 }
			        		 
			        		 result = result + '  </td>';
			        		 result = result + ' <td>';
			        		 result = result + ' <div class="tblminwidth-50">';
			        		 result = result + '      <span title="Edit" class="paddingRight"><a href="'+sourceleadUrl+'/editOption?optionUUID='+opt.optionUUID+'"><i class="fa fa-pencil text-primary cursorPointer"></i></a></span>';
			        		 result = result + '     <span title="Delete" class="paddingLeft"><i class="fa fa-trash text-danger cursorPointer" onclick="deleteoption(\''+opt.optionUUID+'\')"></i></span>';
			        		 result = result + '  </div>';
			        		 result = result + ' </td>';
			        		 result = result + '  </tr>';
						}
				}
	        	
				result = result + '  </tbody>';
				result = result + ' </table>';
				$("#viewOptinsDiv").empty();
				$("#viewOptinsDiv").append(result);
				$("#loading").removeAttr("style");
				viewOptionsTable();
         }else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
			    showError("Operation failed, please try again");
			}
		}); 
  }
  </script>
  
  <script>
function viewOptionsTable(){ 
    $('#viewOptionsTable').DataTable( { 
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
</script>
<div id="popUp"></div>
<script>
function deleteoption(optionId){
	 $(".project_inn_mask").remove();
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<textarea id='reason' class='form-control' field-name='Reject Reason'  data-validation='required validate_Space validate_Description validate_length length2-250' name='reason' value='' placeholder='Reason' rows='2' onkeypress='clientDashboardRemoveSpacesForRejectReason();'></textarea>";
	result = result + ' <div id="deleteLeaveOptinDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onclick='deleteOption(\""+optionId+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelRejectPopupClient();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200); 
}
</script>


<script>
function deleteOption(optionId){
	var reason = document.getElementById("reason").value;
	var reason1 = document.getElementById("reason").value;
	 reason = reason.replace(/\s/g, "")
	if(reason.match(/^[^\s-](.*)*$/) != null){
		$("#deleteLeaveOptinDiv").empty(); 
		$.ajax({
		   	type: "POST",
		   	url: sourceleadUrl+"/deleteLeaveOption?"+tokenParamName+"="+tokenValue+"&optionUUID="+optionId+"&reason="+reason,	    	   
		    success: function(data) {
	   			 $("#selector1").removeAttr("style");
	  			 $("#selector1").css({ display: "block" });
	  			 $("#msgDiv").html("Client contact removed");
	  			 $("#popUp").empty();
	 			 $(".project_inn_mask").fadeOut(200);
	             msgFadein();
	             if(data == true){
	 				location.replace(sourceleadUrl+ "/viewOptions");
	 			}
		    }
	   });
	}else{
		if(reason.length == 0 && reason1){
			$("#deleteLeaveOptinDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Must not Start With Space</font>") ;
		}else{
			$("#deleteLeaveOptinDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
		}
	}
}
</script>

<script>
   function clientDashboardRemoveSpacesForRejectReason() {
	    var output = document.getElementById("reason").value;
	    output = output.replace(/\s/g, "") 
	    if(output.length != 0){
	    $("#reasonForSpoc").removeAttr("data-validation");
	    }
	}
</script>

<script>
function cancelRejectPopupClient(){
	$("#remove_user_pop, .project_inn_mask").fadeOut();
	$("#reason").val('');
	$("#reason").find("p.jquery_form_error_message").remove();
	$("#reason").empty(); 

}
</script>

<script>
	function msgFadein() {
		$("#selector1").fadeIn(200);
        $('#selector1').delay(2000).fadeOut('slow');
	}
</script> 


<jsp:include page="../masterFooter.jsp" />
