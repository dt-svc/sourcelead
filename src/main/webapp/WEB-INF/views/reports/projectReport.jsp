<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Project Report</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Project Report</div>
    </section>

     
  <div class="vendor_wrap">
<div class="clearfix reportHead reports-head">

		<span class="refreIcon pull-left"><a href="javascript:void(0)" class="pull-left refeIcon" id ="dropdownlist" title="Refresh" onclick="projectReportDataTableList(true);"></a></span>
	<div class="col-md-5 pull-left mt5 noPad">
			<select class="chosen-select" name ="projects" id = "projectsId" onchange = "projectReportDataTableList(false);"data-placeholder="Select Project" multiple>
			<c:forEach var="option" items="${projectList}">
				<option value="${option.id}">${option.projectName}</option>
			</c:forEach>
		  </select>
		</div>
		 
		<ul class="pull-right list-unstyled list-inline">
              
              <li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a></li>
              
              <li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
					<div class="newActPop">
						<span>
		           	   		<a href="javascript:void(0)" onclick="projectReportExport('Pdf');" title="PDF">Export PDF</a>
							<a href="javascript:void(0)" onclick="projectReportExport('Excel');" title="Excel">Export Excel</a>            
						</span>
					</div>
				</li>
              
              
              <li class="">
                <input class="repSearchBar" placeholder="Search" id="projectReportDataTableSearch" type="text">
                <span class="repSearchIcon" onclick="projectReportDataTableList(false);"></span></li>
            </ul>
	 </div>
	 
	 <div class="project_tabs_inn_table vendor_wrap_inn_table dataFilterNew">
    	<div id="projectFilterList" class="datatable_filter"></div>
	</div>
	 
  <div class="tableStyles">
	 <div class="require_wrap_table">
	 <div id="projectReporttableList"></div>
	</div>
  </div>
  
	
	  <div class="advancedsearch-mask" onclick="closeAdsrch()"></div>
  <div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search">
    <h5 class="headerAdsrch">Filters <a href="javascript:void(0)" class="close-div" onclick="closePop()"><i class="fa fa-close"></i></a> </h5>
    <div class="addInner">
      <div class="row">
        <div class="col-md-3 mb positionRelative">
          <div class="clearfix filterSearch-ul">
            <ul class="list-unstyled positionRelative filterSearch mt5" id="projectStatus">
            
              <li class="checkBox positionRelative">
                <input id ="projectStatus1" name="projectStatus" value="NOTSTARTED" type="checkbox">
                <label>Not Started</label>
              </li>
              <li class="checkBox positionRelative">
                <input id="projectStatus2" name="projectStatus"  value="INPROGRESS" type="checkbox">
                <label>In-Progress</label>
              </li>
             <li class="checkBox positionRelative">
                <input  id="projectStatus3" name="projectStatus"  value="COMPLETED" type="checkbox">
                <label>Completed</label>
              </li>
              <li class="checkBox positionRelative">
                <input  id="projectStatus5" name="projectStatus"  value="ONHOLD" type="checkbox">
                <label>On-Hold</label>
              </li>
              <li class="checkBox positionRelative">
                <input id="projectStatus6" name="projectStatus"  value="DROPPED" type="checkbox">
                <label>Dropped</label>
              </li>
             
            </ul>
          </div>
        </div>
        
        
        
        <c:if test="${clientListSize > 0}">
							 <div class="col-md-4 mb positionRelative">
									<div class="clearfix dynamic_search filterDynamicsearch">
										<input type="text" id="myClientsInput" onkeyup="myClientsFunction()" placeholder="Clients" title="Type in a clients">
										<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
									</div>
									 <div class="clearfix filterSearch-ul">
										<ul class="list-unstyled positionRelative filterSearch mt5" id="clientsUL">
										    <c:forEach var="report" items="${add_client}">
											     <li class="checkBox positionRelative"><input  type="checkbox" name="clientName" value ="${report.orgRelId}" ><label>${report.clientName}</label></li>
											</c:forEach>
										</ul>
									 </div> 
							 </div> 
							 </c:if>
							 
							 <c:if test="${projectManagersListSize > 0}">
							 <div class="col-md-4 mb positionRelative">
									<div class="clearfix dynamic_search filterDynamicsearch">
										<input type="text" id="myManagersInput" onkeyup="myManagersFunction()" placeholder="Managers" title="Type in a managers">
										<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
									</div>
									 <div class="clearfix filterSearch-ul">
										<ul class="list-unstyled positionRelative filterSearch mt5" id="managersUL">
											<c:forEach var="manager" items="${projectManagersList}">
											<li class="checkBox positionRelative"><input  type="checkbox" name="projectManagers" value ="${manager.id}" ><label>${manager.fullName}</label></li>
											</c:forEach>
										</ul>
									 </div> 
							 </div> 
							 </c:if>
        
      
        <div class="clearfix"></div>
      </div>
    </div>
<div class="clearfix"></div>
    <div class="clearfix advancedsearch-footer">
      <div class="pull-right mr">
        <input value="Apply" id="search_requiremet" onclick="closePop();projectReportDataTableList(false);" class="btn btn-sm btn-success" type="button">
        <input value="Reset" id="clear_search" onclick="resetFilter();" class="btn btn-sm btn-primary" type="button">
      </div>
    </div>
  </div>
</div>
</section>
<div class="loading" style="display:none; position:fixed; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url('/resources/images/loading.gif'); background-position:center; background-repeat:no-repeat; background-size:75px;"></div>
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
	$(document).ready(function(){
	$("#cd").click(function(){
	$(".clientDetails").toggleClass('hidden', 500);
	});

	});
</script>
<script>
	// Close Advanced Search
	function closePop(){
		$("div.cand-advsrch-popup").hide();
		$(".advancedsearch-mask").hide();
	}
	function openPop(){
		$(".advancedsearch-mask").show();
		$("div.cand-advsrch-popup").toggle();
	}	
</script>
<script>

function resetFilter(){

	$("ul#projectStatus > li input:checkbox").prop("checked",false);
	$("ul#managersUL > li input:checkbox").prop("checked",false);
	$("ul#clientsUL > li input:checkbox").prop("checked",false);
	
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

<script>
$(document).ready(function(){
	projectReportDataTableList(true);
});
function projectReportDataTableList(refreshFlag){
	$(".loading").show();
	var projectIds = [];
	var projectStatus = [];
    var clients = [];
    var projectManagers = [];
    var searchValue = "";
	
    if(refreshFlag) {
    	$("#projectsId").val("").trigger('chosen:updated');
    	$("#projectReportDataTableSearch").val("");
    	resetFilter();
	} else {
		var projectsId = $("#projectsId").val();
		if(projectsId != null && projectsId!="" && projectsId!= "undefined"){
			projectIds = projectsId;
		}
		$('input:checkbox[name=projectStatus]:checked').each(function(){
			projectStatus.push($(this).val());
		});
	    $('input:checkbox[name=clientName]:checked').each(function() {
		    clients.push($(this).val());
	    });
	    $('input:checkbox[name=projectManagers]:checked').each(function(){
			projectManagers.push($(this).val());
	    });
	    searchValue = $("#projectReportDataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
	}
     $.ajax({
			type : "POST",
			url : sourceleadUrl+"/getProjectReport?&projectClients="+clients+"&projectManagers="+projectManagers+"&projectIds="+projectIds+"&projectStatus="+projectStatus+"&searchValue="+searchValue,
			success : function(result) {
				if(result && result.response === "SUCCESS") {
					var text = "";
					if(result.data.length > 0) {     
						text += "  <table class='table' id='projectReportTableId'>";
						text += " 	<thead>";
						text += " 	  <tr>";
						text += " 	   	<th>ProjectId</th>";
						text += " 	   	<th>Project Name</th>";
						text += " 		<th>Project Status</th>";
						text += " 		<th>Project Manager</th>";
						text += " 		<th>Project StartDate</th>";
						text += " 		<th>Project EndDate</th>";
						text += " 		<th>Team Member</th>";
						text += " 		<th><br>TeamMember StartDate</th>";
						text += " 		<th><br>TeamMember End Date</th>";
						text += " 		<th>TeamMember Status</th>";
						text += " 	  </tr>";
						text += " 	</thead>";
						text += " 	<tbody>";
						  
						for(var e in result.data) {
							var report = result.data[e];
							text += "    <tr>";
							text += "		<td><div class='tbldata-dotdot'>" + (report.projectSeqNo === null ? "--" : report.projectSeqNo ) + "</div></td>";
							text += "		<td><div class='tbldata-dotdot'>" + report.projectName + "</div></td>";
							text += "		<td><div class='tbldata-dotdot-vsm'>" + report.projectStatus + "</div></td>";
							text += "		<td><div class='tbldata-dotdot-100' title='" + report.projectManagerNames + "'>" + report.projectManagerNames + "</div></td>";
							text += "		<td><div class='tblminwidth-75'>" + (report.projectStartDate !== null ? report.projectStartDate : "----") + "</div></td>";
							text += "		<td><div class='tblminwidth-75'>" + (report.projectEndDate !== null ? report.projectEndDate : "----") + "</div></td>";
							text += "		<td><div class='tbldata-dotdot-100'>" + (report.projectMembers !== null ? report.projectMembers : "----") + "</div></td>";
							text += "		<td><div class='tblminwidth-75'>" +(report.memberStartDate !== null ? report.memberStartDate : "----") + "</div></td>";
							text += "		<td><div class='tblminwidth-75'>" + (report.memberEndDate !== null ? report.memberEndDate : "----") + "</div></td>";
							text += "		<td><div class='tbldata-dotdot-100'>" + (report.memberStatus == '1' ? "Active" : "In-Active")  + "</div></td>";
							text += "	</tr>";
						}
						
						text += " 	</tbody>";
						text += "   </table>";
						$("#projectReporttableList").empty();
						$("#projectReporttableList").append(text);
						$("#projectReportDataTableSearch").empty();
						
						dynamicProjectReportPagination();  
					}
				} else 
					alert("Please try again....");
				$(".loading").hide();
			}
		})
	}
</script>
<script>

$("#projectReportDataTableSearch").keydown(function(e) {
	   if(e.which === 13) {
		   projectReportDataTableList(false);
	   }
	});

</script>


<script>
function dynamicProjectReportPagination(){  
    $('#projectReportTableId').DataTable( {
    	"dom": 'Bfrtip',
    	"responsive": true,
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"buttons": ['excel', 'pdf'],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		"sZeroRecords": "", "sEmptyTable": ""
     	}
    });
     $("#pendingForApprovalTimesheetDiv").find('a.buttons-excel').prop("title", "Export to Excel");
	$("#pendingForApprovalTimesheetDiv").find('a.buttons-pdf').prop("title", "Export to PDF"); 
}
</script>


<script>
function projectReportExport(type){

	var projectIds = [];
	var projectsId = $("#projectsId").val();
	if(projectsId != null && projectsId!="" && projectsId!= "undefined"){
		projectIds = projectsId;
	}
	
	var projectStatus = [];
	     $('input:checkbox[name=projectStatus]:checked').each(function(){
	    	 projectStatus.push($(this).val());
	});
    var clients = [];
          $('input:checkbox[name=clientName]:checked').each(function() {
	     clients.push($(this).val());
    });
    var projectManagers = [];
          $('input:checkbox[name=projectManagers]:checked').each(function(){
	      projectManagers.push($(this).val());
     });
     
     var searchValue = $("#projectReportDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
          window.location.href =  sourceleadUrl+"/getProjectReportExport?&projectClients="+clients+"&projectManagers="+projectManagers+"&projectIds="+projectIds+"&projectStatus="+projectStatus+"&searchValue="+searchValue+"&type="+type;
         
}


</script>