<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
 <script>
 $( document ).ready(function() {
	    getShiftList();
	});
 </script>
 
<script>
var pmsUrl ='${SourceLeadWeb_pmsUrl}';
</script>
 
 <!-- Content Area -->
  <section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Shift</h2>
      </div>
		<div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a>&raquo; 
		<a href="${SourceLeadWeb_sourceleadUrl}/settings">Settings</a>&raquo;
		<a href="${SourceLeadWeb_pmsUrl}/leaveConfiguration">Leave Configuration</a> &raquo; Shift</div>
    </section>
    
<div class="content-area content-new clearfix">

<div class="timesheet_out">
  <div class="clearfix">
    <div class="timesheet_head">
      <ul>
          <li>
               <a href="${SourceLeadWeb_pmsUrl}/addShift" title="Add Shift">
                <i class="fa fa-plus paddingRight"></i>Add Shift
              </a>
          </li>
        
        <div class="clearfix"></div>
      </ul>
      <div class="clearfix"></div>
    </div>
    <div class="clients_wrap_table datatable_filter">
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
        url: pmsUrl+"/getShiftList?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId,       
        success :function(json) {
                populategShiftList(json);
       },
        
   }); 
}
function populategShiftList(response){
	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
	    showError(response.responseMessage);
  	} else if (response.response === "AWKWARD") {
	    location.href = "/errorPage";
	} else if (response.response === "SUCCESS") {
       var result = '';
       result = result +' <table width="100%" width="100%" cellpadding="0" cellspacing="0" id="shiftTableId">';
   result = result +' <thead>';
   result = result +' <tr>';
   result = result +' <th>Name</th>';
   result = result +' <th>Time In</th>';
   result = result +' <th>Time Out</th>';
   result = result +' <th>Description</th>';
   result = result +' <th>Updated By</th>';
   result = result +' <th>Action</th>';
   result = result +' </tr>';
   result = result +' </thead>';
   result = result +' <tbody>';
   for (var i=0; i<response.data.length; i++){
           var report = response.data[i];
           result = result +'  <tr>';                                                                                                                  
           result = result +'  <td>';                                                                                                                  
           result = result +'    <div class="tbldata-dotdot-150">';                                                                          
           result = result +    '<div class="pull-left tbldata-dotdot" title="'+report.shiftName+'">'+report.shiftName+'</div>';
//            result = result +'        <i class="fa fa-binoculars pull-right"></i>';                                                                     
           result = result +'    </div>';                                                                                                              
           result = result +'  </td>';                                                                                                                 
           result = result +'  <td>';                                                                                                                  
           result = result +'      <div class="tbldata-dotdot-100">'+report.timeIn+'</div>';                                                           
           result = result +'  </td>';                                                                                                                 
           result = result +'  <td>';                                                                                                                  
           result = result +'      <div class="tbldata-dotdot-100">'+report.timeOut+'</div>';                                                          
           result = result +'  </td>';
           result = result +'  <td>';                                                                                                                  
           result = result +'    <div title="'+report.shiftDescription+'" class="tbldata-dotdot-big">'+report.shiftDescription+'</div>';
           result = result +'  </td>';                                                                                                                
           result = result +'  <td>';
           if(report.updatedBy != null && report.updatedBy !='undefined' && report.updatedBy != ""){
           result = result +    '<div class="pull-left tbldata-dotdot" title="'+report.updatedBy+'">'+report.updatedBy+'</div>';
           }else{
        	   result = result +    '<div class="pull-left tbldata-dotdot">--</div>';
           }
           result = result +'  </td>';
           result = result +'  <td>';
           result = result +'     <div class="tblminwidth-50">'    
           if(report.status == 1){
           result = result +'          <span title="Edit" class="paddingRight" onclick="editShift(\''+report.shiftUUID+'\');"><i class="fa fa-pencil text-primary cursorPointer"></i></span>';   
           if(report.defaultStatus != "Y")
               result = result +'          <span title="Delete" class="paddingLeft"><i onclick= "deleteShift(\''+report.shiftUUID+'\');" class="fa fa-trash text-danger cursorPointer"></i></span>';         
           }else{
        	   result = result +'          <span class="paddingRight" >NA</span>'; 
           }
           result = result +'      </div>';                                                                                                            
           result = result +'  </td>';                                                                                                                 
           result = result +'</tr>';                                                                                                                                                                                                                                        
           
   } 
   result = result +' </tbody>';
   result = result +' </table>';
   $("#shiftDev").empty();
   $("#shiftDev").append(result);
    shiftPagination();
	} else if (response.indexOf('Sign In') !== -1) {
		location.replace(pmsUrl);
	} else {
	    showError("Operation failed, please try again");
	}
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
	window.location.href = pmsUrl + "/editleaveshift?shiftUUID="+shiftId;
}
</script>

<script>
function deleteShift(shiftId){
	  var orgLocationId = '${SourceLeadWeb_orgLocationId}';
      $.ajax({
      type : "GET",
       url: pmsUrl+"/deleteShift?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&shiftUUID="+shiftId,       
       success :function(data) {
    	   location.replace(pmsUrl+ "/viewShift");
      }
       
  }); 

}
</script>


<jsp:include page="../masterFooter.jsp" />
