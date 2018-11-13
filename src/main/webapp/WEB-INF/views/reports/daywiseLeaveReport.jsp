<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript">
$(document).ready(function(){
	getDaywiseLeaveReportList();
});
</script>
<input type="hidden" id="noReports" name="noReports" value="0"/>
<!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
          <h2>Day Wise Report </h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/hrReportsList'/>">HR Reports</a> &raquo; DayWise Leave Report</div>
    <div id="popUp"></div>
    </section>
     <div class="timesheet_out">
      <div class="vendor_wrap">
        <div class="positionRelative reportHead clearfix">
<!--       <span class="pull-left refreIcon"><a href="javascript:void(0)" class="refeIcon" title="Refresh" onclick="resetFilter();"></a></span> -->
         
      <ul class="pull-right list-unstyled list-inline">
        <input type="hidden" name="usersOffset" id="usersOffset" value="0"/>
		   <li>
		   <div class="pull-left mr">
            <div class="pull-left mr5 width150px">
              <input value="${fromDate}" name="fromDate" id="fromDate" placeholder="Date" class="form-control calendar margin-top0" type="text">
            <p id="fromDateDiv" class="ReError"></p>
            </div>
            <button type="button" class="btn btn-primary" onclick="searchThroughDates();"><i class="glyphicon glyphicon-search"></i></button>
          </div>
		   </li>
<!-- 			<sec:authorize access="hasAnyRole('ROLE_VIEW_ASSOCIATE_ADDITIONAL_DETAILS')">  -->
				<li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="filterPop();"></a></li>
<!-- 			</sec:authorize> -->
				
				<li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
					<div class="newActPop">
						<span>
<!-- 						<sec:authorize access="hasAnyRole('ROLE_EXPORT_ASSOCIATE')">  -->
							<a href="javascript:void(0)" onclick="exportToPdf();" title="PDF">Export PDF</a>
							<a href="javascript:void(0)" onclick="exportUserData();" title="Excel">Export Excel</a>
<!-- 						</sec:authorize> -->
						</span>
					</div>
				</li>
		    
		  <li class=""><input type="text" class="repSearchBar" placeholder='Search' id='daywiseReportDataTableSearch'/><span class="repSearchIcon" onClick="getDaywiseLeaveReportList();"></span></li>
        </ul>
      </div>
        
        
        
          <div class="candidate_wrap_table datatable_filter">
	           <div id="daywiseLeaveReportsList"></div>
          </div>
      </div>
    </div>
  </section>


<script>
function getDaywiseLeaveReportList(){
	var  noReports=$("#noReports").val(0); 
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
	var leaveTypeBillable = [];
	$('input:checkbox[name=leaveTypeBillable]:checked').each(function() 
		    {
		leaveTypeBillable.push($(this).val());
     });
	var fromDate = $("#fromDate").val();
	var searchValue = $("#daywiseReportDataTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	var url = sourceleadUrl+"/getDaywiseLeaveReportList?"+tokenParamName+"="+tokenValue+"&searchValue="+searchValue+"&fromDate="+fromDate+"&userRole="+userRole+"&orgDepartments="+orgDepartments+"&employmentType="+employmentType+"&leaveTypeName="+leaveTypeNames+"&leaveTypeBillable="+leaveTypeBillable;
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
            { "data": "leaveTypeBillable"},
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
			        	  $("#noReports").val("1");

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
		            "data": "leaveTypeBillable",
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
function myRolesFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myRolesInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myRolesUL");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("label")[0];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";

        }
    }
}
function myDepartmentFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myDepartmentInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myDepartmentUL");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("label")[0];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";

        }
    }
}
function leaveTypeFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("leaveTypeInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myLeaveTypeUL");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("label")[0];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";

        }
    }
}
$(document).keyup(function(e) {
	  if (e.keyCode === 27) {
		  closePop();
	  }
	});
</script>
<script>
function resetFilter(){
	$("ul#myRolesUL > li input:checkbox").prop("checked",false);
	$("ul#myLeaveTypeUL > li input:checkbox").prop("checked",false);
	$("ul#myDepartmentUL > li input:checkbox").prop("checked",false);
	$("ul#employmentTypeFilter > li input:checkbox").prop("checked",false);
	$("#daywiseReportDataTableSearch").val('');
	getDaywiseLeaveReportList();}
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
							 	   	 <div class="col-md-3 mb">
								<label><strong>Leave Type</strong></label>
								 <div class="clearfix">
							<ul class="list-unstyled" id="employmentTypeFilter">
			                    	    <li class="checkBox positionRelative"><input   type="checkbox" name="leaveTypeBillable" checked="checked" value ="Billable"><label>&nbsp;Billable</label></li>
										<li class="checkBox positionRelative"><input   type="checkbox" name="leaveTypeBillable"  value ="Non Billable" ><label>&nbsp;Non Billable</label></li>
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
								<ul class="list-unstyled positionRelative filterSearch mt5" id="myLeaveTypeUL">
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
							<input type="button" value="Reset" id="clear_search" onclick="resetFilter();" class="btn btn-sm btn-primary" />
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
<script>
function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
}
$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});	
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
<script>
function exportUserData(){
	var ReportsListEmpty = $("#noReports").val();
	var exportType = "xls";
	var loginUserRole = '${SourceLeadWeb_loginUserRole}';
	var fromDate = $("#fromDate").val();
	var searchValue = $("#daywiseReportDataTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	
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
	var leaveTypeBillable = [];
	$('input:checkbox[name=leaveTypeBillable]:checked').each(function() 
		    {
		leaveTypeBillable.push($(this).val());
     });
	if(ReportsListEmpty != 0){
		window.location.href = sourceleadUrl + "/getExportDailyWiseLeaveReport?"+tokenParamName+"="+tokenValue+"&searchValue="+searchValue+"&exportType="+exportType+"&fromDate="+fromDate+"&userRole="+userRole+"&orgDepartments="+orgDepartments+"&employmentType="+employmentType+"&leaveTypeNames="+leaveTypeNames+"&leaveTypeBillable="+leaveTypeBillable;

	}else{
		 var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='empty_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>DailyWise Leave Report List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
			result = result +"	</div>";
			$("#popUp").append(result);
			$("#empty_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	}
	

}
</script> 
<script>
function exportToPdf(){
	var ReportsListEmpty = $("#noReports").val();
	var exportType = "pdf";
	var loginUserRole = '${SourceLeadWeb_loginUserRole}';
	var fromDate = $("#fromDate").val();
	var searchValue = $("#daywiseReportDataTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	
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
	var leaveTypeBillable = [];
	$('input:checkbox[name=leaveTypeBillable]:checked').each(function() 
		    {
		leaveTypeBillable.push($(this).val());
     });
	if(ReportsListEmpty != 0){
		window.location.href = sourceleadUrl + "/getExportDailyWiseLeaveReport?"+tokenParamName+"="+tokenValue+"&searchValue="+searchValue+"&exportType="+exportType+"&fromDate="+fromDate+"&userRole="+userRole+"&orgDepartments="+orgDepartments+"&employmentType="+employmentType+"&leaveTypeNames="+leaveTypeNames+"&leaveTypeBillable="+leaveTypeBillable;

// 		window.location.href = sourceleadUrl + "/getExportDailyWiseLeaveReport?"+tokenParamName+"="+tokenValue+"&fromDate="+fromDate+"&exportType="+exportType+"&searchValue="+searchValue;
	}else{
		var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='empty_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>DailyWise Leave Report List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
			result = result +"	</div>";
			$("#popUp").append(result);
			$("#empty_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	}
	
}
</script>
<script type="text/javascript">
function cancel(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>
<jsp:include page="../masterFooter.jsp" />