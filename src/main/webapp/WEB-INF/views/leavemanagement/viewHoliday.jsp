<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 

<script type="text/javascript">
		 $(document).ready(function(){
			 getHolidaysList();
			});
</script>


 <!-- Content Area -->
  <section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Holidays List</h2>
      </div>
		<div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo; <a href="<c:url value='/leaveConfiguration'/>">Leave Configuration</a> &raquo; Holiday</div>
    </section>
    
<div class="content-area content-new clearfix">

<div class="timesheet_out">
  <div class="clearfix">
    <div class="timesheet_head">
      <ul>
          <li>
              <a href="<c:url value='/addHoliday'/>" title="Add Holiday"><i class="fa fa-plus paddingRight"></i>Add Holiday</a>
          </li>
        <div class="clearfix"></div>
      </ul>
    </div>
        <div id="loading"></div> 
    <div class="clients_wrap_table datatable_filter">
    <div id="viewHolidaysDiv"></div>
    </div>
  </div>
</div>

    </div>
    <!-- Content Area Ends--> 
  </section>
  <!-- Content Area Ends-->  

<script>

function getHolidaysList(){
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
// 	var userId = '${SourceLeadWeb_userId}';
	$.get(sourceleadUrl+"/getHolidaysList?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId, function(data) {
		var result = "";	
		$("#viewHolidaysDiv").empty();
				result = result + '<table id="viewHolidaysTableId" class="display" cellspacing="0" width="100%" >';
				result = result + '<thead>';
				result = result + '<tr>';
				result = result +'<th>Holiday Name</th>';
				result = result +'<th>From Date</th>';
				result = result +'<th>To Date</th>';
				result = result + '<th>Description</th>';
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
			        		 if(opt.holidayName !=null && opt.holidayName !='' && opt.holidayName !='undefined'){
			        			 result = result + '     <div class="pull-left tbldata-dotdot-me" title="">'+opt.holidayName+'</div>'; 
			        		 }else{
			        			 result = result + '     <div class="pull-left tbldata-dotdot-me" title="">--</div>';
			        		 }
			        		 result = result + '  </div>';
			        		 result = result + ' </td>';
			        		 result = result + ' <td>';
			        		 result = result + '   <div title="From Date" class="tblminwidth-75">'+opt.fromDate+'</div>';
			        		 result = result + ' </td>';
			        		 result = result + ' <td>';
			        		 result = result + '   <div title="To Date" class="tbldata-dotdot-100">'+opt.toDate+'</div>';
			        		 result = result + '</td>';
			        		 result = result + ' <td>';
			        		 if(opt.description !=null && opt.description != 'undefined' && opt.description != ''){
			        			 result = result + '     <div title="'+opt.description+'" class="tblminwidth-75">'+opt.description+'</div>';	 
			        		 }else{
			        			 result = result + '     <div title="2" class="tblminwidth-75">--</div>';	
			        		 }
			        		 
			        		 result = result + '  </td>';
			        		 result = result + ' <td>';
			        		 result = result + ' <div class="tblminwidth-50">';
			        		 result = result +' <span title="Edit" class="paddingRight"><a href="<c:url value="/editHoliday?holidayId='+opt.id+'"/>"><i class="fa fa-pencil text-primary cursorPointer"></i></a></span> ';
			        		 result = result +' <span title="Delete" class="paddingLeft"><i class="fa fa-trash text-danger cursorPointer" onclick="deleteHoliday(\''+opt.id+'\');"></i></span> ';
			        		 result = result + '  </div>';
			        		 result = result + ' </td>';
			        		 result = result + '  </tr>';
						}
				}
	        	
				result = result + '  </tbody>';
				result = result + ' </table>';
				$("#viewHolidaysDiv").append(result);
				$("#loading").removeAttr("style");
				viewHolidaysTablePagination();
		}); 
 }
</script> 
<script>
function deleteHoliday(holidayId){
			 $.get(sourceleadUrl+"/deleteHoliday?"+tokenParamName+"="+tokenValue+"&holidayId="+holidayId, function(data) {
				 location.replace(sourceleadUrl+ "/viewHoliday");
			 }); 
		
}
</script>
<script>
function viewHolidaysTablePagination(){ 
    $('#viewHolidaysTableId').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $("#viewHolidaysTableId").wrap("<div class='overflow_Auto width100percnt'></div>")
}
</script>
	
<jsp:include page="../masterFooter.jsp" />
