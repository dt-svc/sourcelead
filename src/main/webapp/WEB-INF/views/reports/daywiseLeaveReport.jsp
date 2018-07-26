<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript">
$(document).ready(function(){
	getDaywiseLeaveReportList();
});
</script>


<!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
          <h2>Day Wise Report </h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/hrReportsList'/>">HR Reports</a> &raquo; DayWise Leave Report</div>
    </section>
     <div class="timesheet_out">
      <div class="vendor_wrap">
        <div class="timesheet_head table_head">
          <div class="pull-left ml mt5 mr">
            <button type="button" class="btn btn-primary dropdown-toggle btn-sm" onclick="resetCandidateFilter();" title="Refresh"> <span class="fa fa-refresh"></span> </button>
          </div>
          <div class="pull-left mt5"> <a href="javascript:void(0)" class="btn btn-default btn-sm anchorBlack text-center" title="Filters" onclick="filterPop();"><i class="fa fa-filter faFilter padding_right"></i> </a> </div>
          <div class="pull-left ml mt5">
            <div class="pull-left mr5 width150px">
              <input value="" name="fromDate" id="fromDate" placeholder="Date" class="form-control calendar input-sm margin-top0" type="text">
            <p id="fromDateDiv" class="ReError"></p>
            </div>
            <button type="button" class="btn btn-primary btn-sm"><i class="glyphicon glyphicon-search" onclick="searchThroughDates();"></i></button>
          </div>
          <div class="remove_search pull-right">
          <input type="text" placeholder="Search" id="daywiseReportDataTableSearch">
           <span class="cursorPointer" title="Search"><i onclick="getDaywiseLeaveReportList();"></i></span>    
<!--         <span class="cursorPointer" title="Search"><i class="fa fa-search" onclick="getDaywiseLeaveReportList();"></i></span>   -->
<!--             <input type="text" placeholder="Search"/> -->
          </div>
        </div>
        
          <div class="candidate_wrap_table datatable_filter">
	           <div id="daywiseLeaveReportsList"></div>
          </div>
        
       <!--  <div class="require_wrap_table tableFirstSecond clearfix">
          <table>
            <thead>
              <tr>
                <th>Employee Name</th>
                <th>Emp Type</th>
                <th>Leave Type</th>
                <th>Type</th>
                <th>Duration</th>
                <th>Leave Period</th>
                <th>Reason</th>
                <th>Approver</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td><div class="tbldata-dotdot-vsm"> Employee1 </div></td>
                <td><div class="tblminwidth-50">Fulltime</div></td>
                <td><div class="tblminwidth-50">Sick Leave</div></td>
                <td><div class="tblminwidth-50">Billable</div></td>
                <td><div class="tblminwidth-50">Full Day</div></td>
                <td><div class="tblminwidth-50">01/01/2008</div>
                  <div class="tblminwidth-50">01/03/2008</div></td>
                <td><div class="tbldata-dotdot"> Reason for Dailywise report </div></td>
                <td><div class="tbldata-dotdot-sm"> Johnsmith </div></td>
                <td><div class="tblminwidth-50">Approved</div></td>
              </tr>
            </tbody>
          </table>
        </div> -->
<!--         <nav>
          <ul id="paging" class="pagination">
            <li><a href="#">Previous</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li class="disabled"><a href="#">6</a></li>
            <li data-page="last"><a href="#">Next</a></li>
          </ul>
          <div class="clearfix"></div>
        </nav> -->
      </div>
    </div>
  </section>


<script>
function getDaywiseLeaveReportList(){
	var userRole = [];
	$('input:checkbox[name=userRole]:checked').each(function() 
    {
		userRole.push($(this).val());
    });
	var orgDepartments = [];
	$('input:checkbox[name=orgDepartments]:checked').each(function() 
		    {
		orgDepartments.push($(this).val());
     });
	var employmentType = [];
	$('input:checkbox[name=employmentType]:checked').each(function() 
		    {
		employmentType.push($(this).val());
     });
	var leaveTypeNames = [];
	$('input:checkbox[name=leaveTypeNames]:checked').each(function() 
		    {
		leaveTypeNames.push($(this).val());
     });
	var fromDate = $("#fromDate").val();
	var searchValue = $("#daywiseReportDataTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	var url = sourceleadUrl+"/getDaywiseLeaveReportList?"+tokenParamName+"="+tokenValue+"&searchValue="+searchValue+"&fromDate="+fromDate+"&userRole="+userRole+"&orgDepartments="+orgDepartments+"&employmentType="+employmentType+"&leaveTypeNames="+leaveTypeNames;
	prepareAllDaywiseLeaveReportList(url);
}
</script>	

<script>
function prepareAllDaywiseLeaveReportList(url){
	 	var result = "";
		result = result	+ '<div class="project_tabs_inn_table project_tabs_inn_table_a">';
		result = result + '<table id="dynamicDaywiseLeaveReportsListView" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Employee Name</th>';
		result = result + '<th>Department</th>';
		result = result + '<th>Emp Type</th>';
		result = result + '<th>Role</th>';
		result = result + '<th>Leave Type</th>';
		result = result + '<th>Type</th>';
		result = result + '<th>Duration</th>';
		result = result + '<th>From Date</th>';
		result = result + '<th>To Date</th>';
		result = result + '<th>Reason</th>';
		result = result + '<th>Approver</th>';
		result = result + '<th>Status</th>';
		result = result + '</tr>';
		result = result + '</thead>';
		result = result + '</table>';
		result = result	+ '</div>';
		$("#daywiseLeaveReportsList").empty();
		$("#daywiseLeaveReportsList").append(result);
		daywiseLeaveReportsListPagination(url);
}
</script>

<script>
var table = '';
function daywiseLeaveReportsListPagination(url){
	var colIndex = 0;
	table = $('#dynamicDaywiseLeaveReportsListView').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "searchHighlight": true,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'GET',
          pages: 10
        }),
        "columns": [
            { "data": "name" },
            { "data": "userDeptName" },
            { "data": "employmentType"},
            { "data": "userRoleName"},
            { "data": "leaveTypeName"},
            { "data": "type"},
            { "data": "duration"},
            { "data": "fromDate"},
            { "data": "toDate"},
            { "data": "reason"},
            { "data": "approver"},
            { "data": "status"}
        ],
		 "columnDefs": [ {
		    	  	"targets": colIndex++,
		    	  	"data": "name",
		    	  	"render": function(data, type, user, meta) {
			        	  var result = '';
			        	  result = result + ' <td>';
				          if(user.name !=null && user.name !='' && user.name !='undefined'){
								 result = result +'<div class="tbldata-dotdot" title="'+user.name+'">'+user.name+'</div>';
				          }else{
								result = result + ' <div class="tbldata-dotdot">--</div>';
							}
				          result = result + ' </td>';
				              return result;
				          		
				          }
		    	  	
		      },
		      {
			        "targets": colIndex++,
			        "data": "userDeptName",
			        "render": function ( data, type, user, meta ) {
			        	  var result = '';
			        	  result = result + ' <td>';
			        	  if(user.userDeptName !=null && user.userDeptName !='' && user.userDeptName !='undefined'){
								 result = result + '<div class="tblminwidth-75">'+user.userDeptName+'</div>';
				          }else{
								result = result + ' <div class="tblminwidth-75">--</div>';
							}
			        	  result = result + ' </td>';
			        	 return result;
			        }
			      },
			      {
		        "targets": colIndex++,
		        "data": "employmentType",
		        "render": function ( data, type, user, meta ) {
		        	  var result = '';
		        	  result = result + ' <td>';
		        	  if(user.employmentType !=null && user.employmentType !='' && user.employmentType !='undefined'){
							 result = result + '<div class="tblminwidth-75">'+user.employmentType+'</div>';
			          }else{
							result = result + ' <div class="tblminwidth-75">--</div>';
						}
		        	  result = result + ' </td>';
		        	 return result;
		        }
		      },
		      {
			        "targets": colIndex++,
			        "data": "userRoleName",
			        "render": function ( data, type, user, meta ) {
			        	  var result = '';
			        	  result = result + ' <td>';
			        	  if(user.userRoleName !=null && user.userRoleName !='' && user.userRoleName !='undefined'){
								 result = result + '<div class="tblminwidth-75">'+user.userRoleName+'</div>';
				          }else{
								result = result + ' <div class="tblminwidth-75">--</div>';
							}
			        	  result = result + ' </td>';
			        	 return result;
			        }
			      },
		      
		      {
		          "targets": colIndex++,
		          "data": "leaveTypeName",
		          "render": function ( data, type, user, meta ) {
		        	  var result = '';
		        	  result = result + ' <td>';
		        	  if(user.leaveTypeName !=null && user.leaveTypeName !='' && user.leaveTypeName !='undefined'){
							 result = result + '<div class="tbldata-dotdot-vsm" title="'+user.leaveTypeName+'">'+user.leaveTypeName+'</div>';
			          }else{
							result = result + ' <div class="tbldata-dotdot-vsm">--</div>';
						}
		        	  result = result + ' </td>';
		        	  return result;
		          }
		        }, {
		            "targets": colIndex++,
		            "data": "type",
		            "sortable": true,
		            "render": function ( data, type, user, meta ) {
		            	var result = '';
		            	result = result + ' <td>';
		            	 if(user.leaveTypeBillable !=null && user.leaveTypeBillable !='' && user.leaveTypeBillable !='undefined'){
							 result = result + '<div class="tblminwidth-75">'+user.leaveTypeBillable+'</div>';
			          }else{
							result = result + '<div class="tblminwidth-75">--</div>';
						}
		            	 result = result + ' </td>';
			              return result;
		            }
		      },
		      {
		          "targets": colIndex++,
		          "data": "duration",
		          "sortable": true,
		          "render": function ( data, type, user, meta ) {
		          	var result = '';
		          	result = result + ' <td>';
		          	if(user.duration !=null && user.duration !='' && user.duration !='undefined'){
						 result = result + '<div class="tblminwidth-75">'+user.duration+'</div>';
		          }else{
						result = result + '<div class="tblminwidth-75">--</div>';
					}
		          	 result = result + ' </td>';
			              return result;
		          }
		    },
		    {
		        "targets": colIndex++,
		        "data": "fromDate",
		        "sortable": true,
		        "render": function ( data, type, user, meta ) {
		        	var result = '';
		        	result = result + ' <td>';
		        	if(user.fromDateStr !=null && user.fromDateStr !='' && user.fromDateStr !='undefined'){
						 result = result + '<div class="tblminwidth-75">'+user.fromDateStr+'</div>';
		          }else{
						result = result + '<div class="tblminwidth-75">--</div>';
					}
		        	 result = result + ' </td>';
		              return result;
		        }
		    },
		    {
		        "targets": colIndex++,
		        "data": "toDate",
		        "sortable": true,
		        "render": function ( data, type, user, meta ) {
		        	var result = '';
		        	result = result + ' <td>';
		        	if(user.toDateStr !=null && user.toDateStr !='' && user.toDateStr !='undefined'){
						 result = result + '<div class="tblminwidth-75">'+user.toDateStr+'</div>';
		          }else{
						result = result + ' <div class="tblminwidth-75">--</div>';
					}
		        	 result = result + ' </td>';
		              return result;
		        }
		    },
		    {
			          "targets": colIndex++,
			          "data": "reason",
			          "sortable": true,
			          "render": function ( data, type, user, meta ) {
			          	var result = '';
			          	result = result + ' <td>';
			          	if(user.reason !=null && user.reason !='' && user.reason !='undefined'){
							 result = result + '<div class="tbldata-dotdot-big" title="'+user.reason+'">'+user.reason+'</div>';
			          }else{
							result = result + ' <div class="tbldata-dotdot-big">--</div>';
						}
			          	result = result + ' </td>';
				        return result;
			          }
			}, {
		          "targets": colIndex++,
		          "data": "approver",
		          "sortable": true,
		          "render": function ( data, type, user, meta ) {
		          	var result = '';
		          	result = result + ' <td>';
		          	if(user.approver !=null && user.approver !='' && user.approver !='undefined'){
						 result = result + '<div class="tbldata-dotdot-vsm" title="'+user.approver+'">'+user.approver+'</div>';
		          }else{
						result = result + '<div class="tbldata-dotdot-vsm">--</div>';
					}
		          	result = result + ' </td>';
		          	return result;
		          }
		},{
			          "targets": colIndex++,
			          "data": "status",
			          "sortable": true,
			          "render": function ( data, type, user, meta ) {
			          	var result = '';
			        	result = result + ' <td>';
			          	if(user.status !=null && user.status !='' && user.status !='undefined'){
							 result = result + '<div class="tblminwidth-50">'+user.status+'</div>';
			          }else{
							result = result + '<div class="tblminwidth-50">--</div>';
						}
			          	result = result + ' </td>';
				        return result;
			          }
		  }],
		    "scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     },
    } );
    $("#dynamicDaywiseLeaveReportsListView").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
}

$("#daywiseReportDataTableSearch").keydown(function(e) {
	   if(e.which === 13) {
		   getDaywiseLeaveReportList();
	   }
	});
</script>

<script>
function filterPop(){
	$("#dayWiseFilterId").toggle();
	$(".advancedsearch-mask").fadeIn();
};
function closeAssetFilterPop(){
	$("#dayWiseFilterId").hide();
	$(".advancedsearch-mask").fadeOut();

}
</script>

 <div class="advancedsearch-mask"></div>
          <div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search" id="dayWiseFilterId">
				<h5 class="headerAdsrch">Filter Search
					<a href="javascript:void(0)" class="close-div" onClick="closeAssetFilterPop()"><i
						class="fa fa-close"></i></a>
				</h5>
				<div class="addInner">
					<div class="row">
						<div class="col-md-3">
						<div class="clearfix dynamic_search filterDynamicsearch">
								<input type="text" id="myRolesInput" onkeyup="myRolesFunction()" placeholder="Roles" title="Type in a roles">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul">
								<ul class="list-unstyled positionRelative filterSearch mt5" id="myRolesUL">
									<c:forEach var="roles" items="${rolesList}">
									<li class="checkBox positionRelative"><input  type="checkbox" name="userRole" value ="${roles.id}" ><label>${roles.displayName}</label></li>
									</c:forEach>
								</ul>
							 </div> 
						</div>
						
						<c:if test="${departmentListSize > 0}">
						<div class="col-md-3 mb positionRelative">
							<div class="clearfix dynamic_search filterDynamicsearch">
								<input type="text" id="myDepartmentInput" onkeyup="myDepartmentFunction()" placeholder="Departments" title="Type in a departments">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul">
								<ul class="list-unstyled positionRelative filterSearch mt5" id="myDepartmentUL">
									<c:forEach var="dep" items="${departmentList}">
									<li class="checkBox positionRelative"><input  type="checkbox" name="orgDepartments" value ="${dep.id}"><label>${dep.departmentName}</label></li>
									</c:forEach>
								</ul>
							 </div> 
						</div>
						</c:if>
					   	 <div class="col-md-3 mb">
								<label><strong>Employment Type</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="employmentTypeFilter">
			                    	    <li class="checkBox positionRelative"><input   type="checkbox" name="employmentType"  value ="C2C"><label>&nbsp;C2C</label></li>
										<li class="checkBox positionRelative"><input   type="checkbox" name="employmentType"  value ="C2C-1099" ><label>&nbsp;C2C-1099</label></li>
										<li class="checkBox positionRelative"><input   type="checkbox" name="employmentType" value ="W-2" ><label>&nbsp;W-2</label></li>
			               				<li class="checkBox positionRelative"><input   type="checkbox" name="employmentType" value ="C2H"><label>&nbsp;C2H</label></li>
			               				<li class="checkBox positionRelative"><input   type="checkbox" name="employmentType" value ="C2H-1099"><label>&nbsp;C2H-1099</label></li>
			               				<li class="checkBox positionRelative"><input   type="checkbox" name="employmentType" value ="Offshore C2C"><label>&nbsp;Offshore C2C</label></li>
			               				<li class="checkBox positionRelative"><input   type="checkbox" name="employmentType" value ="Part Time"><label>&nbsp;Part Time</label></li>
			               				<li class="checkBox positionRelative"><input   type="checkbox" name="employmentType" value ="Full Time"><label>&nbsp;Full Time</label></li>
			               				<li class="checkBox positionRelative"><input   type="checkbox" name="employmentType" value ="Other"><label>&nbsp;Others</label></li>
			                        </ul>
								 </div>
							 </div> 
					 	<c:if test="${!empty leaveTypeNamesList}">
						<div class="col-md-3 mb positionRelative">
							<div class="clearfix dynamic_search filterDynamicsearch">
								<input type="text" id="leaveTypeInput" onkeyup="leaveTypeFunction()" placeholder="LeaveType" title="Leave Type">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul">
								<ul class="list-unstyled positionRelative filterSearch mt5" id="myDepartmentUL">
									<c:forEach var="dep" items="${leaveTypeNamesList}">
									<li class="checkBox positionRelative"><input  type="checkbox" name="leaveTypeNames" value ="${dep.id}"><label>${dep.name}</label></li>
									</c:forEach>
								</ul>
							 </div> 
						</div>
						</c:if> 
					   
						
					</div>
					</div>
					<div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Apply" id="search_requiremet" onclick="closeAssetFilterPop();getDaywiseLeaveReportList();" class="btn btn-sm btn-success" />
							<input type="button" value="Reset" id="clear_search" onclick="resetAssetFilter();" class="btn btn-sm btn-primary" />
						</div>
					</div>
				</div>
<script>
function viewCompleteTemplate(temp){
	var templateUUID = $("#empTemplateId").val();
	var toAddress = $("#toAddress").val();
	var employeeId = $("#hiddenUserId").val();
	var winPrint = window.open(sourceleadUrl+'/previewCompleteTemplateByTemplateUUID?&templateUUID='+templateUUID+'&toAddress='+toAddress+'&employeeId='+employeeId,'_newtab');
	if(temp == 'print')
		winPrint.print();
}
</script> 


<script>
 $( function() {
   /*  $("#fromDate").datepicker(
		{changeMonth: true});
	 $("#toDate").datepicker(
	 {changeMonth: true}); */
	 
	 
	 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	 var finalDateFormatEndDate = '';
	 if(locationDate.match("yyyy-MM-dd")){
	     finalDateFormatEndDate = 'yy-mm-dd';
	 }if(locationDate.match("dd-MM-yyyy")){
	     finalDateFormatEndDate = 'dd-mm-yy'; 
	 }if(locationDate.match("MM-dd-yyyy")){
	     finalDateFormatEndDate = 'mm-dd-yy'; 
	 }if(locationDate.match("yyyy-dd-MM")){
	     finalDateFormatEndDate = 'yy-dd-mm'; 
	 }
	 
	 $( "#fromDate" ).datepicker({
	     dateFormat : finalDateFormatEndDate,
	      changeMonth: true,
	      changeYear: true,
// 	      minDate: new Date() ,
	      onSelect : function(selected) {
	      var dt = $(this).datepicker('getDate', '+1d');
	      dt.setDate(dt.getDate());
	      $("#toDate").datepicker("option", "minDate", dt);
	       removeStartValid();
	      } 

	});
	 
	 
  });
 
</script> 
<script>
	function searchThroughDates(){
		alert("222##");
		var fromDate = $("#fromDate").val();
		
		$('#fromDateDiv').empty();
		
		if(fromDate == null || fromDate == '' || fromDate == 'undefined'){
			$("#fromDateDiv").append("Start Date is Required") ; 
		}
		
		if(fromDate != null && fromDate != '' && fromDate != 'undefined'){
			getDaywiseLeaveReportList(fromDate)
		}
	}
	
	
	function removeStartValid(){
		$('#fromDateDiv').empty();
	}
	

</script>

<script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
			})
</script>

<jsp:include page="../masterFooter.jsp" />