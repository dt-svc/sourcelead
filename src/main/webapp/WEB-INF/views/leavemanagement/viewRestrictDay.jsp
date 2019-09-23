<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
 
 <script>
$(document).ready(function(){
	getRestrictDayList();
});
</script>

   <!-- Content Area -->
   <section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Restrict Day</h2>
      </div>
		<div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;  <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; <a href="<c:url value='${appPath}/leaveConfiguration'/>">Leave Configuration</a> &raquo; Restrict Day</div>
    </section>
    
<div class="content-area content-new clearfix">
<div id="popUp"></div>

<div class="timesheet_out">
  <div class="clearfix">
    <div class="timesheet_head">
      <ul>
          <li>
              <a href="<c:url value='${appPath}/addRestrictDay'/>" title="Add Restrict Day">
                <i class="fa fa-plus paddingRight"></i>Add Restrict Day
              </a>
          </li>
        
<!--           <li class="dropdown">
            <span class="dropdown-toggle" id="menu1" data-toggle="dropdown">
                <i class="fa fa-filter"></i><span>Type</span><i class="glyphicon glyphicon-play"></i></span>|
          
            <div class="dropdown-menu bootstrap_dropdownMenu dropdownPadding" role="menu" aria-labelledby="menu1">
            <ul>
              <li>
                <input value="" name="resourcerequest" id="all" type="checkbox">
                <label for="all">Full Day</label>
              </li>
              <li>
                <input id="hotlist" name="resourcerequest" type="checkbox">
                <label for="hotlist">Half Day</label>
              </li>
              <li>
                <input id="Assigned" name="resourcerequest" type="checkbox">
                <label for="Assigned">SickLeave</label>
              </li>
            </ul>
          </div>
        </li> -->
      </ul>
      <div class="clearfix"></div>
    </div>
    <div class="candidate_wrap_table datatable_filter">
       <div id="restricDayList"></div>
    </div>
  </div>
</div>

    </div>
  </section>
  <!-- Content Area Ends--> 

<script>
function getRestrictDayList(){
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	$.ajax({
        type : "GET",
         url: sourceleadUrl+"/getRestrictDayList?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId,  
         success :function(json) {
        	 populateRestrictDayList(json);
        },
         
    }); 
}
function populateRestrictDayList(response){
	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
	    showError(response.responseMessage);
  	} else if (response.response === "AWKWARD") {
	    location.href = "/errorPage";
	} else if (response.response === "SUCCESS") {
	var result = '';
	result = result +' <table width="100%" width="100%" cellpadding="0" cellspacing="0" id="restrictDayTableId">';
    result = result +' <thead>';
    result = result +' <tr>';
    result = result +' <th>Name</th>';
    result = result +' <th>Duration</th>';
    result = result +' <th>Reason</th>';
    result = result +' <th>Action</th>';
    result = result +' </tr>';
    result = result +' </thead>';
    result = result +' <tbody>';
    for (var i=0; i<response.data.length; i++){
    	var report = response.data[i];
    	result = result +'  <tr>                                                                                                                       ';
    	result = result +'  <td>                                                                                                                       ';
    	result = result +'    <div class="tbldata-dotdot-big">                                                                               ';
    	result = result +'        <div class="pull-left tbldata-dotdot-me" title="'+report.restrictName+'">'+report.restrictName+'</div>                              ';
    	result = result +'    </div>                                                                                                                   ';
    	result = result +'  </td>                                                                                                                      ';
    	result = result +'  <td>                                                                                                                       ';
    	result = result +'      <div class="tbldata-dotdot-big" title="'+report.startDate+' - '+report.endDate+'">'+report.startDate+' - '+report.endDate+'</div>                                                           ';
    	result = result +'  </td>                                                                                                                      ';
    	result = result +'  <td>                                                                                                                       ';
    	if(report.reason != null && report.reason != 'undefined' && report.reason != ''){
    		result = result +'    <div title="'+report.reason+'" class="tbldata-dotdot-verybig">'+report.reason+'</div> ';
    	}else{
    		result = result +'    <div title="'+report.reason+'" class="tbldata-dotdot-verybig">--</div> ';
    	}
    	result = result +'  </td>                                                                                                                      ';
    	result = result +'  <td>                                                                                                                       ';
    	result = result +'     <div class="tblminwidth-50">                                                                                            ';
    	result = result +'          <span title="Edit" class="paddingRight"><a href="<c:url value="/app/addRestrictDay?restrictUUID='+report.restrictUUID+'"/>"><i class="fa fa-pencil text-primary cursorPointer"></i></a></span>             ';
    	result = result +'          <span title="Delete" class="paddingLeft"><i class="fa fa-trash text-danger cursorPointer" onclick="deleteRestrictDay(\''+report.restrictUUID+'\');"></i></span>              ';
    	result = result +'      </div>                                                                                                                 ';
    	result = result +'  </td>                                                                                                                      ';
    	result = result +'</tr>       																													';
    	
    } 
    result = result +' </tbody>';
    result = result +' </table>';
    $("#restricDayList").empty();
    $("#restricDayList").append(result);
    restrictDayPagination();
	} else if (response.indexOf('Sign In') !== -1) {
		location.replace(sourceleadUrl);
	} else {
	    showError("Operation failed, please try again");
	}
}
</script>

<script>
function restrictDayPagination(){
	 $('#restrictDayTableId').DataTable( { 
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"targets": 'no-sort',
	    	"bSort": false,
	    	"order": [],
	    	"oLanguage": {
	            "sSearch": "",
	            "sZeroRecords": "", "sEmptyTable": ""
	       }
	    });
	    $("#restrictDayTableId").wrap("<div class='overflow_Auto'></div>")
}

</script>

<script>
function deleteRestrictDay(id){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";

	result = result +"		<p>Are you sure you want to delete ?</p>";
	result = result +"		<textarea  id='reasonForRestrict' name='reasonForRestrict'  placeholder='Comments here' ></textarea>";
	result = result + ' <div id="reasonForRestrictDiv"/>';
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Delete" onclick="saveDeletedReason(\''+id+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function cancelTermination(){
	$("#popUp").empty();
	 $(".project_inn_mask").fadeOut(200);
}

function saveDeletedReason(id){
	var reason = $("#reasonForRestrict").val();
	if(reason != null && reason != ''){
		 $("#reasonForRestrictDiv").empty();
		if(reason.length > 250){
		$("#reasonForRestrictDiv").empty().append("<font color='red' style='padding: 5px 10px; '>The Entered values should not be more than 250 characters</font>") ; 
		}else{
			 $.ajax({
				   	type: "POST",
				    url: sourceleadUrl+"/deleteRestrictedDay?"+tokenParamName+"="+tokenValue+"&restrictUUID="+id+"&reason="+reason,
		   		    success: function(data) {
		   		    	$("#popUp").empty();
		   			 	$(".project_inn_mask").fadeOut(200);
		   			 	location.replace(sourceleadUrl+"/viewRestrictDay");	
		   		    }
			     });
		}
	}
	else{
			$("#reasonForRestrictDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
	}
	
}

</script>

<jsp:include page="../masterFooter.jsp" />
