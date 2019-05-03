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
		<div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;  <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; <a href="<c:url value='${appPath}/leaveConfiguration'/>">Leave Configuration</a> &raquo; Holiday</div>
    </section>
    
<div class="content-area content-new clearfix">

<div class="timesheet_out">
  <div class="clearfix">
  
  <div class="clearfix"></div>
<div class="col-md-10 col-md-offset-1 listof-holidays datatable_filter">
                        <div class="row">
                            </div>
                        
                    </div>
    <div class="timesheet_head">
    				<div class="col-md-3 noPad mt2">
                             <label class="col-md-6"><strong class="leaves-heading">List of Holidays</strong></label>
                                 <div class="col-md-6 mt5">
                                 <select class="sel mt2" id="reportYear" name="reportYear" onChange="getHolidaysList(this);">
                                    <option value="">--Select Year--</option>
                    			<c:forEach var="year" items="${yearsList}">
	                    			<option value="${year}">${year}</option>
                     			</c:forEach> 
                                </select>
                                </div> 
                            </div>
      <ul>
          <li>
              <a href="<c:url value='${appPath}/addHoliday'/>" title="Add Holiday"><i class="fa fa-plus paddingRight"></i>Add Holiday</a>
          </li>
	      <li id="filterShiftTypePop"><i class="fa fa-filter"></i><span>Shift</span><i class="glyphicon glyphicon-play"></i>
		           	<div class="statusFiler-1 approved_pop">
						<ul class="list-unstyled">
							<c:forEach var="shift" items="${shiftList}">
									<li><input  type="checkbox" name="shift" value ="${shift.id}" onclick="getHolidaysList()"><label>${shift.shiftName}</label></li>
							</c:forEach>
						</ul>
					</div>	                	
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
	var shiftIds = [];
	$('input:checkbox[name=shift]:checked').each(function() 
		    {
		shiftIds.push($(this).val());
     });
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var reportYear = "";
	if($("#reportYear").val() !=null && $("#reportYear").val() != 'undefined' && $("#reportYear").val() != ''){
		reportYear = $("#reportYear").val();
	}
// 	var userId = '${SourceLeadWeb_userId}';
	$.get(sourceleadUrl+"/getHolidaysList?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&reportYear="+reportYear+"&shiftIds="+shiftIds, function(response) {
		 if (response.response === "AWKWARD") {
		    location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
		$("#viewHolidaysDiv").empty();
		var result = "";
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
				if(response !=null && response !=''){
					 for (var i=0; i<response.data.length; i++)
						{
			        		 var opt = response.data[i];
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
			        			 result = result + '     <div title="'+opt.description+'" class="tbldata-dotdot-big">'+opt.description+'</div>';	 
			        		 }else{
			        			 result = result + '     <div title="2" class="tblminwidth-75">--</div>';	
			        		 }
			        		 
			        		 result = result + '  </td>';
			        		 result = result + ' <td>';
			        		 result = result + ' <div class="tblminwidth-50">';
			        		 result = result +' <span title="Edit" class="paddingRight"><a href="<c:url value="/app/editHoliday?holidayUUID='+opt.holidayUUID+'"/>"><i class="fa fa-pencil text-primary cursorPointer"></i></a></span> ';
			        		 result = result +' <span title="Delete" class="paddingLeft"><i class="fa fa-trash text-danger cursorPointer" onclick="deleteHoliday(\''+opt.holidayUUID+'\');"></i></span> ';
			        		 result = result + '  </div>';
			        		 result = result + ' </td>';
			        		 result = result + '  </tr>';
						}
				}
	        	
				result = result + '  </tbody>';
				result = result + ' </table>';
				$("#viewHolidaysDiv").append(result);
				$("#loading").removeAttr("style");
				$("#holidayListDiv").empty();
				$("#holidayListDiv").append(result);
				viewHolidaysTablePagination();
				
		} else if (response.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
		} else {
		    showError("Operation failed, please try again");
		}
		}); 
 }
</script> 
<script>
function deleteHoliday(holidayId){
			 $.get(sourceleadUrl+"/deleteHoliday?"+tokenParamName+"="+tokenValue+"&holidayUUID="+holidayId, function(data) {
				 location.replace(sourceleadUrl+ "/viewHoliday");
			 }); 
		
}
</script>
<script>
function viewHolidaysTablePagination(){ 
    $('#viewHolidaysTableId').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
    	"columnDefs": [ { orderable: false, targets: [1,2]}],
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
<script>
	$("#filterShiftTypePop").click(function(){
		$(".statusFiler-1").toggle();
	});
	 
    $("#filterShiftTypePop").click(function(e) {
        e.stopPropagation();
    });	

</script>
<jsp:include page="../masterFooter.jsp" />
