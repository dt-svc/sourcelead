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
		<div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="restrictday.html">Leave Management</a> &raquo; Restrict Day</div>
    </section>
    
<div class="content-area content-new clearfix">

<div class="timesheet_out">
  <div class="clearfix">
    <div class="timesheet_head">
      <ul>
          <li>
              <a href="<c:url value='/addRestrictDay'/>" title="Add Restrict Day">
                <i class="fa fa-plus paddingRight"></i>Add Restrict Day
              </a>
          </li>
        
          <li class="dropdown">
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
        </li>
      </ul>
      <div class="clearfix"></div>
    </div>
    <div class="require_wrap_table datatable_filter">
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
function populateRestrictDayList(json){
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
    for (var i=0; i<json.length; i++){
    	var report = json[i];
    	result = result +'  <tr>                                                                                                                       ';
    	result = result +'  <td>                                                                                                                       ';
    	result = result +'    <div class="tbldata-dotdot-big">                                                                               ';
    	result = result +'        <div class="pull-left tbldata-dotdot-me" title="'+report.restrictName+'">'+report.restrictName+'</div>                              ';
    	result = result +'    </div>                                                                                                                   ';
    	result = result +'  </td>                                                                                                                      ';
    	result = result +'  <td>                                                                                                                       ';
    	result = result +'      <div class="tbldata-dotdot-100" title="'+report.startDateStr+' - '+report.endDateStr+'">'+report.startDateStr+' - '+report.endDateStr+'</div>                                                           ';
    	result = result +'  </td>                                                                                                                      ';
    	result = result +'  <td>                                                                                                                       ';
    	result = result +'    <div title="Reason" class="tbldata-dotdot-verybig">'+report.reason+'</div>                                                                      ';
    	result = result +'  </td>                                                                                                                      ';
    	result = result +'  <td>                                                                                                                       ';
    	result = result +'     <div class="tblminwidth-50">                                                                                            ';
    	result = result +'          <span title="Edit" class="paddingRight"><i class="fa fa-pencil text-primary cursorPointer"></i></span>             ';
    	result = result +'          <span title="Delete" class="paddingLeft"><i class="fa fa-trash text-danger cursorPointer"></i></span>              ';
    	result = result +'      </div>                                                                                                                 ';
    	result = result +'  </td>                                                                                                                      ';
    	result = result +'</tr>       																													';
    	
    } 
    result = result +' </tbody>';
    result = result +' </table>';
    $("#restricDayList").empty();
    $("#restricDayList").append(result);
    restrictDayPagination();
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
	    	"oLanguage": {
	            "sSearch": "",
	            "sZeroRecords": "", "sEmptyTable": ""
	       }
	    });
	    $("#restrictDayTableId").wrap("<div class='overflow_Auto'></div>")
}
</script>

<jsp:include page="../masterFooter.jsp" />
