<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
 <script>
 $( document ).ready(function() {
	    getShiftList();
	});
 </script>
 
 <!-- Content Area -->
  <section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Shift</h2>
      </div>
		<div class="pull-right brud-crum"><a href="dashboard_us.html">Home</a> &raquo; <a href="/viewShift">Leave Management</a> &raquo; Shift</div>
    </section>
    
<div class="content-area content-new clearfix">

<div class="timesheet_out">
  <div class="clearfix">
    <div class="timesheet_head">
      <ul>
          <li>
               <a href="<c:url value='/addShift'/>" title="Add Shift">
                <i class="fa fa-plus paddingRight"></i>Add Shift
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
        <div class="clearfix"></div>
      </ul>
      <div class="clearfix"></div>
      <div class="remove_search">
        <input placeholder="Search" type="text">
      </div>
    </div>
    <div class="require_wrap_table datatable_filter">
      <div id= "shiftDev"></div> 
    </div>
  </div>
</div>

    </div>
    <!-- Content Area Ends--> 
  </section>
  <!-- Content Area Ends--> 

<script>
function getShiftList(){
       var orgLocationId = '${SourceLeadWeb_orgLocationId}';
       $.ajax({
       type : "GET",
        url: sourceleadUrl+"/getShiftList?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId,       
        success :function(json) {
                populategShiftList(json);
       },
        
   }); 
}
function populategShiftList(json){
       var result = '';
       result = result +' <table width="100%" width="100%" cellpadding="0" cellspacing="0" id="shiftTableId">';
   result = result +' <thead>';
   result = result +' <tr>';
   result = result +' <th>Name</th>';
   result = result +' <th>Time In</th>';
   result = result +' <th>Time Out</th>';
   result = result +' <th>Description</th>';
   result = result +' <th>Updated By</th>';
   result = result +' <th>Status</th>';
   result = result +' <th>Action</th>';
   result = result +' </tr>';
   result = result +' </thead>';
   result = result +' <tbody>';
   for (var i=0; i<json.length; i++){
           var report = json[i];
           result = result +'  <tr>';                                                                                                                  
           result = result +'  <td>';                                                                                                                  
           result = result +'    <div class="tbldata-dotdot-150">';                                                                          
           result = result +    '<div class="pull-left tbldata-dotdot" title="'+report.shiftName+'">'+report.shiftName+'</div>';
           result = result +'        <i class="fa fa-binoculars pull-right"></i>';                                                                     
           result = result +'    </div>';                                                                                                              
           result = result +'  </td>';                                                                                                                 
           result = result +'  <td>';                                                                                                                  
           result = result +'      <div class="tbldata-dotdot-100">'+report.timeIn+'</div>';                                                           
           result = result +'  </td>';                                                                                                                 
           result = result +'  <td>';                                                                                                                  
           result = result +'      <div class="tbldata-dotdot-100">'+report.timeOut+'</div>';                                                          
           result = result +'  </td>';
           result = result +'  <td>';                                                                                                                  
           result = result +'    <div title="TimeOut" class="tbldata-dotdot-big">'+report.shiftDescription+'</div>';
           result = result +'  </td>';                                                                                                                
           result = result +'  <td>';                                                                                                                  
           result = result +    '<div class="pull-left tbldata-dotdot" title="'+report.updatedBy+'">'+report.updatedBy+'</div>';
           result = result +'  </td>';
           result = result +'  <td>';  
           if(report.status == 1){
        	   result = result + '<div class="pull-left tbldata-dotdot" title="Active">Active</div>';
           }else{
        	   result = result + '<div class="pull-left tbldata-dotdot" title="InActive">InActive</div>';
           }
           result = result +'  </td>';
           result = result +'  <td>';
           result = result +'     <div class="tblminwidth-50">'                                                                                         
           result = result +'          <span title="Edit" class="paddingRight" onclick="editShift(\''+report.id+'\');"><i class="fa fa-pencil text-primary cursorPointer"></i></span>';       
           result = result +'          <span title="Delete" class="paddingLeft"><i onclick= "deleteShift();" class="fa fa-trash text-danger cursorPointer"></i></span>';         
           result = result +'      </div>';                                                                                                            
           result = result +'  </td>';                                                                                                                 
           result = result +'</tr>';                                                                                                                                                                                                                                        
           
   } 
   result = result +' </tbody>';
   result = result +' </table>';
   $("#shiftDev").empty();
   $("#shiftDev").append(result);
    shiftPagination();
}
</script>

<script>
function shiftPagination(){
        $('#shiftTableId').DataTable( { 
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
           $("#shiftTableId").wrap("<div class='overflow_Auto'></div>")
}
</script>




<script type="text/javascript">
function editShift(shiftId) {
	alert("fsdf")
	window.location.href = sourceleadUrl + "/editleaveshift?&shiftId="+shiftId;
}
</script>

<script>
function deleteShift(){
	alert("hai1");
}
</script>


<jsp:include page="../masterFooter.jsp" />
