<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript">
$(document).ready(function(){
	getBalanceLeaveReportList();
});
</script>
<div id="emptyPopup"></div>
<input type="hidden" id="noLeaveBalanceList" name="noLeaveBalanceList"  value="0"/>  
<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Leave Balances</h2>
      </div>
<!--       <div class="pull-right brud-crum">Home &raquo; Leave Balances</div> -->
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/hrReportsList'/>">HR Reports</a> &raquo; Leave Balances</div>
    </section>
    <div class="timesheet_out">
      <div class="vendor_wrap">
        <div class="positionRelative reportHead clearfix">
<!--       <span class="pull-left refreIcon"><a href="javascript:void(0)" class="refeIcon" title="Refresh" onclick="resetFilter();"></a></span> -->
      <ul class="pull-right list-unstyled list-inline">
        <input type="hidden" name="usersOffset" id="usersOffset" value="0"/>
		  
			<sec:authorize access="hasAnyRole('ROLE_VIEW_ASSOCIATE_ADDITIONAL_DETAILS')"> 
				<li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a></li>
			</sec:authorize>
				
				<li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
					<div class="newActPop">
						<span>
						<sec:authorize access="hasAnyRole('ROLE_EXPORT_ASSOCIATE')"> 
							<a href="javascript:void(0)" onclick="exportToPdf();" title="PDF">Export PDF</a>
							<a href="javascript:void(0)" onclick="exportToExcel();" title="Excel">Export Excel</a>
						</sec:authorize>
						</span>
					</div>
				</li>
		    
<!-- 		  <li style="display: none;"><input type="text" class="repSearchBar" placeholder='Search' id='balanceLeavesTableSearch'/><span class="repSearchIcon" onClick="getBalanceLeaveReportList();"></span></li> -->
      		  <li class=""><input type="text" class="repSearchBar" placeholder='Search' id='leaveReportDataTableSearch'/><span class="repSearchIcon" onClick="getBalanceLeaveReportList();"></span></li>
      
        </ul>
      </div>
      
<div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search">
				<h5 class="headerAdsrch">Filters
					<a href="javascript:void(0)" class="close-div" onClick="closePop()"><i
						class="fa fa-close"></i></a>
				</h5>
				<div class="addInner">
					<div class="row">
							
						<div class="col-md-2">
						<div class="row">
							 
							 <div class="col-md-12 mb">
								<label><strong>Leave Type</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="employmentTypeFilter">
			                    	    <li class="checkBox positionRelative"><input   type="checkbox" name="leaveType"  value ="Billable"><label>&nbsp;Billable</label></li>
										<li class="checkBox positionRelative"><input   type="checkbox" name="leaveType"  value ="Non Billable" ><label>&nbsp;Non Billable</label></li>
			                        </ul>
								 </div>
							 </div> 
							 
						  </div>
						</div>
						
				
						
						<div class="col-md-2 positionRelative">
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
						
							<div class="col-md-2 positionRelative">
							<div class="clearfix dynamic_search filterDynamicsearch">
								<input type="text" id="leaveTypeInput" onkeyup="leaveTypeFunction()" placeholder="leaveType" title="Type in a leaveType">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul">
								<ul class="list-unstyled positionRelative filterSearch mt5" id="leaveTypeUL">
									<c:forEach var="leaveType" items="${leaveTypeList}">
									<li class="checkBox positionRelative"><input  type="checkbox" name="userleaveType" value ="${leaveType.id}" ><label>${leaveType.name}</label></li>
									</c:forEach>
								</ul>
							 </div> 
						</div>
						
						
						<div class="col-md-2">
						<div class="row">
						
					
						<div class="col-md-12 mb positionRelative">
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
						</div></div>
						
						
					</div>
					</div>
					<div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Apply" id="search_requiremet"
								onclick="closePop();getBalanceLeaveReportList();" class="btn btn-sm btn-success" />
							<input type="button" value="Reset" id="clear_search"
								onclick="resetFilter();" class="btn btn-sm btn-primary" />
						</div>
					</div>
				</div>


  <!--       <div class="require_wrap_table tableFirstSecond clearfix">
          <table>
            <thead>
              <tr>
                <th>Employee Name</th>
                <th>Emp Type</th>
                <th>Sick Leave</th>
                <th>Vacation Leave</th>
                <th>Total Balance Leaves</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td><div class="tbldata-dotdot">Employee1</div></td>
                <td><div class="tblminwidth-50">Fulltime</div></td>
                <td><div class="tblminwidth-50">4</div></td>
                <td><div class="tblminwidth-50">4</div></td>
                <td><div class="tblminwidth-50">8</div></td>
              </tr>
            </tbody>
          </table>
        </div> -->
          <div class="clients_wrap_table datatable_filter">
    <div id="balanceLeaveReportsList"></div>
  </div>
      </div>
    </div>
  <div id="loading"></div>
  </section>

<script>
function getBalanceLeaveReportList(){
	var fromDate = $("#fromDate").val();
	var searchValue = $("#leaveReportDataTableSearch").val();
	var  noLeaveBalanceList=$("#noLeaveBalanceList").val(0); 
	searchValue = searchValue ? searchValue : "";
	//var url = sourceleadUrl+"/getDaywiseLeaveReportList?"+tokenParamName+"="+tokenValue+"&fromDate="+fromDate;
	var url = sourceleadUrl+"/getLeaveBalanceReportList?"+tokenParamName+"="+tokenValue+"&searchValue="+searchValue;
	prepareBalanceLeaveReportList(url);
	
}
</script>	

<script>
function getBalanceLeaveReportList(){
	var searchValue = $("#leaveReportDataTableSearch").val();
	var topleave = "";
var MonthStartDate ="";
var MonthEndDate ="";
var loginUserRole = '${SourceLeadWeb_loginUserRole}';
var  noLeaveBalanceList=$("#noLeaveBalanceList").val(0); 
	
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
	var userleaveType = [];
	$('input:checkbox[name=userleaveType]:checked').each(function() 
		    {
		userleaveType.push($(this).val());
     });
	var leaveType = [];
	$('input:checkbox[name=leaveType]:checked').each(function() 
		    {
		leaveType.push($(this).val());
     });
	var url = sourceleadUrl+"/getLeaveBalanceReportList?"+tokenParamName+"="+tokenValue+"&userRole="+userRole+"&orgDepartments="+orgDepartments+"&userleaveType="+userleaveType+"&leaveType="+leaveType+"&topleave="+topleave+"&MonthStartDate="+MonthStartDate+"&MonthEndDate="+MonthEndDate+"&searchValue="+searchValue;
$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:999;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.get(url,function(response) {
							if (response.response === "AWKWARD") {
								location.href = "/errorPage";
							} else if (response.response === "SUCCESS") {
								var result = "";
								result = result	+ '<div class="project_tabs_inn_table project_tabs_inn_table_a">';
								result = result + '<table id="dynamicBalanceLeaveReportsListView" class="display" cellspacing="0" width="100%" >';
								result = result + '<thead>';
								result = result + '<tr>';
								result = result + '<th>Employee Name</th>';
							 	result = result + '<th>Emp Type</th>';
							 	var opt = response.object;	
							 	for (i = 0; i < opt.leaveTypeNames.length; i++) {
							 		for(j=0; j < opt.users.length; j++)
							 		var report = opt.users[j];
							 		for(var k in opt.map){
										var ltName = k.split("-");
										if(ltName[1] == report.userId && ltName[0] == opt.leaveTypeNames[i]){
											var v =ltName[0];
											
											result = result + '<th>'+v+'</th>';
							 	}
							 		}
							 	}
							 	
								result = result + '<th>Total Balance Leaves</th>';
								result = result + '</tr>';
								result = result + '</thead>';
								result = result + '<tbody>';
								
								for(j=0; j < opt.users.length; j++){
									$("#noLeaveBalanceList").val(1);
									var report = opt.users[j];
									result += "<tr>";
						        	  if(report.empName !=null && report.empName !='' && report.empName !='undefined'){
											result += "<td> "+report.empName+" </td>";
 
						        	  }
						        	  else{
											result += "<td>--</td>";

						        	  }
						        	  
						        	  if(report.empType !=null && report.empType !='' && report.empType !='undefined'){
						        		  result += "<td> "+report.empType+" </td>";

						        	  }
						        	  else{
											result += "<td>--</td>";

						        	  }

									var lo=0;
									for (i = 0; i < opt.leaveTypeNames.length; i++) {
										for(var k in opt.map){
											var ltName = k.split("-");
											if(ltName[1] == report.userId && ltName[0] == opt.leaveTypeNames[i]){
												var l = opt.map[k];
													result += "<td> "+l+" </td>";
											       lo = lo+l;
												}else if(ltName[0] == null &&opt.leaveTypeNames[i] == null)
													result += "<td> -- </td>";
												
											
										}
									}
									result += "<td> "+lo+" </td>";
									result += "</tr>"
								}
								
								
								
								result = result + '  </tbody>                                                                                                        ';
								result = result + '</table> ';
							
							$("#balanceLeaveReportsList").empty();
							$("#balanceLeaveReportsList").append(result);
							$("#loading").removeAttr("style");
							$("#balanceLeavesTableSearch").removeAttr('style');
							listTypePagination();
							$(".proj_show").click(
									function() {
										$(".new_side_wrap").addClass(
												"new_side_wrap_active");
										$(".side_mask").fadeIn(200);
										$("body").css('overflow-y',
												'hidden');
									});

						}else if (response.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
							showError("Operation failed, please try again");
						}
       });
}
					
</script>
<script>
	function listTypePagination() {
		$('#dynamicBalanceLeaveReportsListView').DataTable({
			"searching": false, 
		    "paging": true,
			"scrollCollapse" : false,
			"lengthMenu" : [ [ 10, 15, 20, -1 ], [ 10, 15, 20] ],
			"bLengthChange" : true,
			"bDestroy" : true,
			"ordering" : true,
			"mark" : true,
			"oLanguage" : {
				"sSearch" : "",
				"sZeroRecords" : "",
				"sEmptyTable" : "",
					"sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
			}
		});
		$("#dynamicBalanceLeaveReportsListView").wrap("<div class='overflow_Auto' style='width:100%;'></div>");

	}
	$("#leaveReportDataTableSearch").keydown(function(e) {
		   if(e.which === 13) {
			   getBalanceLeaveReportList();
		   }
		});

</script>
 
<script>
function viewCompleteTemplate(temp){
	var templateUUID = $("#empTemplateId").val();
	var toAddress = $("#toAddress").val();
	var employeeId = $("#hiddenreportId").val();
	var winPrint = window.open(sourceleadUrl+'/previewCompleteTemplateByTemplateUUID?&templateUUID='+templateUUID+'&toAddress='+toAddress+'&employeeId='+employeeId,'_newtab');
	if(temp == 'print')
		winPrint.print();
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
<script>
function exportToPdf(){
	var  noLeaveBalanceList=$("#noLeaveBalanceList").val(); 
	var exportType = "pdf";
	var loginUserRole = '${SourceLeadWeb_loginUserRole}';
    
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
	var userleaveType = [];
	$('input:checkbox[name=userleaveType]:checked').each(function() 
		    {
		userleaveType.push($(this).val());
     });
	var leaveType = [];
	$('input:checkbox[name=leaveType]:checked').each(function() 
		    {
		leaveType.push($(this).val());
     });
	var searchValue = $("#leaveReportDataTableSearch").val();
	if(noLeaveBalanceList != 0){
		window.location.href = sourceleadUrl + "/getExportReports?"+tokenParamName+"="+tokenValue+"&userRole="+userRole+"&orgDepartments="+orgDepartments+"&userleaveType="+userleaveType+"&leaveType="+leaveType+"&exportType="+exportType+"&searchValue="+searchValue;
	}else{
		var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='candidate_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>LeaveBalances List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='candidtaeCancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='candidtaeCancel();'></b>";
			result = result +"	</div>";
			$("#emptyPopup").append(result);
			$("#candidate_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	}
}
</script>
<script type="text/javascript">
function candidtaeCancel(){
	$("#emptyPopup").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>
<script>
function exportToExcel(){
	var  noLeaveBalanceList=$("#noLeaveBalanceList").val();
	var exportType = "excel";
	var loginUserRole = '${SourceLeadWeb_loginUserRole}';
    
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
	var userleaveType = [];
	$('input:checkbox[name=userleaveType]:checked').each(function() 
		    {
		userleaveType.push($(this).val());
     });
	var leaveType = [];
	$('input:checkbox[name=leaveType]:checked').each(function() 
		    {
		leaveType.push($(this).val());
     });
	var searchValue = $("#leaveReportDataTableSearch").val();
	if(noLeaveBalanceList != 0){
		window.location.href = sourceleadUrl + "/getExportReports?"+tokenParamName+"="+tokenValue+"&userRole="+userRole+"&orgDepartments="+orgDepartments+"&userleaveType="+userleaveType+"&leaveType="+leaveType+"&exportType="+exportType+"&searchValue="+searchValue;
	}else{
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='candidate_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>LeaveBalances List is Empty</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='candidtaeCancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='candidtaeCancel();'></b>";
		result = result +"	</div>";
		$("#emptyPopup").append(result);
		$("#candidate_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
}
</script>
<script>
function resetFilter(){
	$("ul#myRolesUL > li input:checkbox").prop("checked",false);
	$("ul#statusFilter > li input:checkbox").prop("checked",false);
	$("ul#myDepartmentUL > li input:checkbox").prop("checked",false);
	$("ul#leaveTypeUL > li input:checkbox").prop("checked",false);
	$("ul#employmentTypeFilter > li input:checkbox").prop("checked",false);
	$("#dynamicUserDataTableSearch").val('');
	$('#myRolesInput').val('');
	$('#leaveTypeInput').val('');
	$('#myDepartmentInput').val('');
	leaveTypeFunction();
	myRolesFunction();
	myDepartmentFunction();
	
	document.getElementById("activeStatus").checked = true;
	document.getElementById("notloggedInStatus").checked = true;
	
	getBalanceLeaveReportList();
}
</script>
<script>
function getActions(){
	$(".action-pop").slideToggle(200);
	$(".newActPop").slideUp(200);
}	
$("html").click(function(){
	$(".action-pop").slideUp(200);
});
$(".action, .action-pop").click(function(e){
 	e.stopPropagation();
});	


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
    ul = document.getElementById("leaveTypeUL");
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
	// Close Advanced Search
		function closePop(){
			$("div.cand-advsrch-popup").hide();
			$(".advancedsearch-mask").fadeOut();
		}
		function openPop(){
			$("div.cand-advsrch-popup").toggle();
			 $(".advancedsearch-mask").fadeIn();
		}
		
		function closeAdsrch(){
			$("div.cand-advsrch-popup").fadeOut();
			$(".advancedsearch-mask").fadeOut();
		}
</script>
 <div class="advancedsearch-mask" onClick="closeAdsrch()"></div>
<jsp:include page="../masterFooter.jsp" />