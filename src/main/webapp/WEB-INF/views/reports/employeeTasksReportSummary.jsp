<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="/resources/amcharts/amcharts.js"></script>
<script src="/resources/amcharts/serial.js"></script>
<script src="/resources/amcharts/plugins/export/export.min.js"></script>
<link rel="stylesheet" href="/resources/amcharts/plugins/export/export.css" type="text/css" media="all" />

<!-- Content Area -->
<script>
 $(document).ready(function() {
	getDynamicEmployeeTaskSummaryRepotList();
 });
 
 </script>
  
 <script type="text/javascript">
 var hideColumns = [];
 var table = "";
 $(document).ready(function () {
     $('label.toggle-vis').on('click', function (e) {
         e.preventDefault();
         // Get the column API object
         var column = table.column($(this).attr('data-column'));
         $(this).toggleClass("active");
         // Toggle the visibility
         column.visible(!column.visible());
     });
});
</script>
  <script type="text/javascript">
	function columHide(){
		hideColumns=[]; // reset the array object.
		$(".toggle-vis").each(function() {
			columClose();
		   var chkdVal= $(this).prop('checked');
		   if($(this).attr('data-column')) {
			   var column = table.column($(this).attr('data-column'));
			  if(chkdVal == true){
			         column.visible(true);
			    }
			    else{
			      column.visible(false);
			      hideColumns.push($(this).attr('data-column')); // used for hiding the columns when user searched the data.
			  }
		   }
	    });
		$(".colPop").hide();
	   
	} 
	function checkAllColumns(obj){
		var id = obj.id;
		var chkdVal= $('.toggle-vis:input:checkbox').prop('checked');
		
		if(chkdVal == true){
		if(id =='columnsPickerCheckAll'){
			$('.toggle-vis:input:checkbox').prop('checked',true);
		  }
		}
		else{
			if(id =='columnsPickerCheckAll'){
				$('.toggle-vis:input:checkbox').prop('checked',false);
			}
		} 
			
	}
	$(document).on( 'init.dt.dth', function (e, settings, json) {
	    table = new $.fn.dataTable.Api( settings );
	    var body = $( table.table().body() );
	     
	} ); 
	</script>

  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Employee Tasks Summary</h2>
      </div>
            <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}"> Home </a> &raquo; <a href="${SourceLeadWeb_sourceleadUrl}/reportsDashboard"> Report Dashboard </a> &raquo; <a href="${SourceLeadWeb_sourceleadUrl}/employeeTasksReport"> Employee Tasks </a> &raquo; Employee Tasks List
     </div>
    </section>
    <div class="tdVmiddle clearfix project_wrap">
      <div class="" id="payrollRegister" style="display: block;">
        <div class="clearfix">
          <div class="positionRelative reportHead clearfix">
			  <span class="pull-left refreIcon mb5">
				  <a href="javascript:void(0)" class="refeIcon" title="Refresh" onclick="resetTaskRpoertFilter();"></a>
				 <a href="javascript:void(0)" onclick="columPop();" title="Column Chooser" class="columIcon"></a>
				  
				  
				  
				  
<div class="colPop" >
	<div class="colPopMid coldropdown-parent">
	<div class="clearfix">
		<div class="row">
				<div class="col-md-10 mb10 mt5 col-md-offset-2">
				<span class="checkbox-new pull-left">
					<input id="columnsPickerCheckAll" class="checkbox-custom toggle-vis" name="columnsPicker" onclick="checkAllColumns(this);"  type="checkbox">
					<label for="columnsPickerCheckAll" class="checkbox-custom-label" >Check All</label>
				</span>
			</div>
		</div>
	<div class="clearfix"></div>
		<div class="coldropdown-inner">
		<ul id="jobCustomColumns">
			<li>
				<span class="checkbox-new clearfix">
					<input id="b" class="checkbox-custom toggle-vis" name="paidComponent" data-column="1" type="checkbox" checked="" data-name="priority">
					<label for="b" class="checkbox-custom-label" >Priority</label>
				</span>
			</li>
			
			<li>
				<span class="checkbox-new clearfix">
					<input id="c" class="checkbox-custom toggle-vis" name="paidComponent" data-column="2" type="checkbox" checked="" data-name="projectName">
					<label for="c" class="checkbox-custom-label" >Project Name</label>
				</span>
			</li>
			
			<li>
				<span class="checkbox-new clearfix">
					<input id="d" class="checkbox-custom toggle-vis" name="paidComponent" data-column="3" type="checkbox" checked="" data-name="dueOn">
					<label for="d" class="checkbox-custom-label" >Due On</label>
				</span>
			</li>

			<li>
				<span class="checkbox-new clearfix">
					<input id="e" class="checkbox-custom toggle-vis" name="paidComponent" data-column="4" type="checkbox" checked="" data-name="completion">
					<label for="e" class="checkbox-custom-label" >Completion</label>
				</span>
			</li>
			
			<li>
				<span class="checkbox-new clearfix">
					<input id="f" class="checkbox-custom toggle-vis" name="paidComponent" data-column="8" type="checkbox" checked="" data-name="status">
					<label for="f" class="checkbox-custom-label" >Status</label>
				</span>
			</li>
			
			<li>
				<span class="checkbox-new clearfix">
					<input id="g" class="checkbox-custom toggle-vis" name="paidComponent" data-column="6" type="checkbox"  data-name="ownerName">
					<label for="g" class="checkbox-custom-label" >Owner</label>
				</span>
			</li>
			
		   <li>
				<span class="checkbox-new clearfix">
					<input id="h" class="checkbox-custom toggle-vis" name="paidComponent" data-column="7" type="checkbox"  data-name="estimatedTime">
					<label for="h" class="checkbox-custom-label" >Estimated Time</label>
				</span>
			</li>
			
			<li>
				<span class="checkbox-new clearfix">
					<input id="i" class="checkbox-custom toggle-vis" name="paidComponent" data-column="5" type="checkbox"  data-name="severity">
					<label for="i" class="checkbox-custom-label" >Severity</label>
				</span>
			</li>
			
		</div>
	</div>
	<div class="colPopFot">
		<a href="javascript:void(0)" onclick="columHide();">Apply</a>
		<a href="javascript:void(0)" onclick="columClose();">Cancel</a>
	</div>
</div>				
			</div> 
			
			
			  </span>
            <ul class="pull-right list-unstyled list-inline">
              <li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a></li>
              <li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
                <div class="newActPop" style=""> <span>
                <a href="javascript:void(0)" onclick="employeeTaskExport('pdf');" title="PDF">Export PDF</a> 
                <a href="javascript:void(0)" onclick="employeeTaskExport('excl');" title="Excel">Export Excel</a> </span> </div>
              </li>
             <li class="" id="assetSearch" style="display: block;"><input type="text" class="repSearchBar" placeholder='Search' id='assetDataTableSearch'/><span class="repSearchIcon" onClick="getDynamicEmployeeTaskSummaryRepotList();"></span></li>
            </ul>
          </div>
          <div class="datatable_filter dataSeaTop invoiceGridview" id="employeeReportListDivId"/>
           
        </div>
      </div>
    </div>
    
	<div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search" id="modalWallet">
	      <div class="popupHead"><span>Task Type</span><i class="closePop pull-right popClose" onclick="closePopUp();"></i></div>
	        <form id="#" name="" action="" method="post">
	        <div class="popupContent">
				<div class="addInner">
				
	          <div class="row">
	            <div class="col-md-2 mb positionRelative">
					<div class="clearfix dynamic_search filterDynamicsearch">
						<input type="text" id="myPriorityInput" onkeyup="myPriorityFunction()" placeholder="Priority" title="Type in a priority">
						<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
					</div>
					 <div class="clearfix filterSearch-ul">
						<ul class="list-unstyled positionRelative filterSearch mt5" id="myPriorityUL">
							<c:forEach var="priority" items="${taskPriorityList}"> 
							<li class="checkBox positionRelative clearfix"><input value="${priority.id}" name="priorityFilter"  type="checkbox" ><label>${priority.name}</label></li>
							</c:forEach>
						</ul>
					 </div> 
			   </div> 
			   
			   <div class="col-md-2 mb positionRelative">
					<div class="clearfix dynamic_search filterDynamicsearch">
						<input type="text" id="myTaskStatusInput" onkeyup="myTaskStatusFunction()" placeholder="Task Status" title="Type in a taskStatus">
						<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
					</div>
					 <div class="clearfix filterSearch-ul">
						<ul class="list-unstyled positionRelative filterSearch mt5" id="myTaskStatusUL">
							<c:forEach var="taskStatus" items="${taskStatusList}"> 
							<li class="checkBox positionRelative clearfix"><input value="${taskStatus.id}" name="taskStatusFilter"  type="checkbox" ><label>${taskStatus.name}</label></li>
							</c:forEach>
						</ul>
					 </div> 
			   </div> 
			   
			   <div class="col-md-2 mb positionRelative">
					<div class="clearfix dynamic_search filterDynamicsearch">
						<input type="text" id="mySeverityInput" onkeyup="mySeverityFunction()" placeholder="Severity" title="Type in a severity">
						<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
					</div>
					 <div class="clearfix filterSearch-ul">
						<ul class="list-unstyled positionRelative filterSearch mt5" id="mySeverityUL">
							<c:forEach var="severity" items="${taskSeverityList}"> 
							<li class="checkBox positionRelative clearfix"><input value="${severity.id}" name="severityFilter"  type="checkbox" ><label>${severity.name}</label></li>
							</c:forEach>
						</ul>
					 </div> 
			 </div> 
			 
			 <div class="col-md-3 mb positionRelative">
					<div class="clearfix dynamic_search filterDynamicsearch">
						<input type="text" id="myTaskTypeInput" onkeyup="myTaskTypeFunction()" placeholder="Task Type" title="Type in a taskType">
						<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
					</div>
					 <div class="clearfix filterSearch-ul">
						<ul class="list-unstyled positionRelative filterSearch mt5" id="myTaskTypeUL">
							<c:forEach var="taskType" items="${taskTypeList}"> 
							<li class="checkBox positionRelative clearfix"><input value="${taskType.id}" name="taskTypeFilter"  type="checkbox" ><label>${taskType.name}</label></li>
							</c:forEach>
						</ul>
					 </div> 
			 </div> 
			 
			 <div class="col-md-3 mb positionRelative">
					<div class="clearfix dynamic_search filterDynamicsearch">
						<input type="text" id="myProjectInput" onkeyup="myProjectFunction()" placeholder="Projects" title="Type in a project">
						<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
					</div>
					 <div class="clearfix filterSearch-ul">
						<ul class="list-unstyled positionRelative filterSearch mt5" id="myProjectUL">
							<c:forEach var="project" items="${projectList}"> 
							<li class="checkBox positionRelative clearfix"><input value="${project.id}" name="projectFilter"  type="checkbox" ><label>${project.projectName}</label></li>
							</c:forEach>
						</ul>
					 </div> 
			 </div> 
	        
	          
	        <div class="clearfix"></div>
			  <div class="clearfix" id="customDates" style="display: block;">
			<div class="col-md-12 positionRelative">
	          <div class="clearfix dynamic_search filterDynamicsearch">
	          <h5 class="margin-top0">Custom Dates</h5>
			  </div>
	          <div class="clearfix">
	            <ul class="list-unstyled positionRelative filterSearch mb0">
	              <li class="positionRelative">
	                <div class="row">
	                <div class="col-md-5">
	                	<input type="text" class="form-control calendar noMar" placeholder="From Date" name="fromDate" id="fromDate" readonly="readonly">
                         <p id="fromDateDiv" style="color: red; display: none;">From Date Required</p>
	                </div>
	                <div class="col-md-5">
	                	<input type="text" class="form-control calendar noMar" placeholder="To Date" name="toDate" id="toDate" readonly="readonly">
	                	<p id="toDateDiv" style="color: red; display: none;">To Date Required</p>
					</div>
					<span class="pull-left">
					  <a href="javascript:void(0)" class="refeIcon" title="Refresh" onclick="resetCalender();"></a>
				  </span>
	             </div>
	             
	              </li>
	            </ul>
	          </div>
	        </div>
		</div>
	      </div>
	    </div>			
			</div>
	        <div class="clearfix mb mr">
	        <div class="pull-right mt">
				<input type="button" value="Apply" id="search_requiremet" onclick="getDynamicEmployeeTaskSummaryRepotList();closePopUp();" class="btn btn-sm btn-success">
				<input type="button" value="Reset" id="clear_search" onclick="resetTaskRpoertFilter();" class="btn btn-sm btn-primary">
			</div>
	        </div>
	        <div>
	</div></form>
	  
  </section>
 
  
<script>
var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
var finalDateFormatProjectSave = '';
if(locationDate.match("yyyy-MM-dd")){
	  finalDateFormatProjectSave = 'yy-mm-dd';
}if(locationDate.match("dd-MM-yyyy")){
	  finalDateFormatProjectSave = 'dd-mm-yy'; 
}if(locationDate.match("MM-dd-yyyy")){
	  finalDateFormatProjectSave = 'mm-dd-yy'; 
}if(locationDate.match("yyyy-dd-MM")){
	  finalDateFormatProjectSave = 'yy-dd-mm'; 
}
$('#fromDate').datepicker({
	dateFormat : finalDateFormatProjectSave,
	changeMonth: true,
	changeYear: true, 
	onSelect : function(selected) {
		var dt = $(this).datepicker('getDate', '+1d');
		dt.setDate(dt.getDate());
		$("#toDate").datepicker("option", "minDate", dt);
		vaidateFromDate();
	}
});
$('#toDate').datepicker({
	dateFormat : finalDateFormatProjectSave,
	changeMonth: true,
	changeYear: true, 
	onSelect : function(selected) {
		var dt = $(this).datepicker('getDate', '-1d');
		dt.setDate(dt.getDate());
		$("#fromDate").datepicker("option", "maxDate", dt);
		validateToDate();
	}
});

function vaidateFromDate(){
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	if(fromDate != '' && toDate != ''){
		$("#search_requiremet").prop("disabled", false);
		document.getElementById("fromDateDiv").style.display="none";
	}else if(toDate == ''){
		 document.getElementById("toDateDiv").style.display="block";
		 $("#search_requiremet").prop("disabled", true);
	}else if(fromDate != ''){
		document.getElementById("fromDateDiv").style.display="none";
	}
}

function validateToDate(){
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	if(fromDate != '' && toDate != ''){
		$("#search_requiremet").prop("disabled", false);
		document.getElementById("toDateDiv").style.display="none";
	}else if(fromDate == ''){
		 document.getElementById("fromDateDiv").style.display="block";
		 $("#search_requiremet").prop("disabled", true);
	}else if(toDate != ''){
		document.getElementById("toDateDiv").style.display="none";
	}
}

function resetCalender(){
	$("#fromDate").val('');
	$("#toDate").val('');
	document.getElementById("fromDateDiv").style.display="none";
	document.getElementById("toDateDiv").style.display="none";
	$("#search_requiremet").prop("disabled", false);
}
function resetTaskRpoertFilter(){
	$("ul#myTaskTypeUL > li input:checkbox").prop("checked",false);
	$("ul#myTaskStatusUL > li input:checkbox").prop("checked",false);
	$("ul#myPriorityUL > li input:checkbox").prop("checked",false);
	$("ul#myProjectUL > li input:checkbox").prop("checked",false);
	
	resetCalender();
	getDynamicEmployeeTaskSummaryRepotList();
}

function mySeverityFunction(){
	 var input, filter, ul, li, a, i;
	    input = document.getElementById("mySeverityInput");
	    filter = input.value.toUpperCase();
	    ul = document.getElementById("mySeverityUL");
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

  function myTaskTypeFunction(){
	  var input, filter, ul, li, a, i;
	    input = document.getElementById("myTaskTypeInput");
	    filter = input.value.toUpperCase();
	    ul = document.getElementById("myTaskTypeUL");
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
  
 function myTaskStatusFunction(){
	   var input, filter, ul, li, a, i;
	    input = document.getElementById("myTaskStatusInput");
	    filter = input.value.toUpperCase();
	    ul = document.getElementById("myTaskStatusUL");
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
 function myPriorityFunction() {
	    var input, filter, ul, li, a, i;
	    input = document.getElementById("myPriorityInput");
	    filter = input.value.toUpperCase();
	    ul = document.getElementById("myPriorityUL");
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
 
 function myProjectFunction(){
	 var input, filter, ul, li, a, i;
	    input = document.getElementById("myProjectInput");
	    filter = input.value.toUpperCase();
	    ul = document.getElementById("myProjectUL");
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
  </script>
 <script>
 function openPop(){
		$("#modalWallet, .mask").fadeIn();
}
function closePopUp(){
	$("#modalWallet, .mask").fadeOut();
}
	 
	//Custom Dates
	$("#custmdateChkbx").click(function() {
	if($(this).is(":checked")){
		$("#customDates").show();
	}else {
		$("#customDates").hide();
	}
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
function getDynamicEmployeeTaskSummaryRepotList(){
	var candidateId = '${candidateId}';
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var taskType = '${taskStatus}';
	var priorityFilter = [];
	$('input:checkbox[name=priorityFilter]:checked').each(function() 
    {
		priorityFilter.push($(this).val());
	});
	
	var taskStatusFilter = [];
	$('input:checkbox[name=taskStatusFilter]:checked').each(function() 
    {
		taskStatusFilter.push($(this).val());
	});
	
	var taskTypeFilter = [];
	$('input:checkbox[name=taskTypeFilter]:checked').each(function() 
    {
		taskTypeFilter.push($(this).val());
	});
	
	var projectFilter = [];
	$('input:checkbox[name=projectFilter]:checked').each(function() 
    {
		projectFilter.push($(this).val());
	});
	
	var severityFilter = [];
	$('input:checkbox[name=severityFilter]:checked').each(function() 
    {
		severityFilter.push($(this).val());
	});
	
	
	var searchValue = $("#assetDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	var url = sourceleadUrl	+"/employeeTasksReportSummaryList?searchValue="+searchValue+"&candidateid="+candidateId+"&projectFilter="+projectFilter+"&taskTypeFilter="+taskTypeFilter+"&taskStatusFilter="+taskStatusFilter+"&priorityFilter="+priorityFilter+"&fromDate="+fromDate+"&toDate="+toDate+"&severityFilter="+severityFilter+"&taskType="+taskType;
	prepareDynamicEmployeeTaskList(url);	
}
function prepareDynamicEmployeeTaskList(url){
	var result = "";
    result = result +'<table id="employeeTaskPagination" class="table">';
	result = result +' <thead>';
	result = result +' <tr>';
	result = result +' <th>Task Title</th>';
    result = result +' <th>Priority</th>';
    result = result +' <th>Project</th>';
	result = result +' <th>Due On</th>';
    result = result +' <th>Completion</th>';
    result = result +' <th>Severity</th>';
    result = result +' <th>Owner Name</th>';
    result = result +' <th>Estimeted Time</th>';
    result = result +' <th>Status</th>';
	result = result +'   </tr>';
	result = result +' </thead>';
	result = result +'</table>';
	$("#employeeReportListDivId").empty();
	$("#employeeReportListDivId").append(result);
	projectEmployeeTaskPaginations(url);
}
function projectEmployeeTaskPaginations(url){
	var colIndex = 0;
    $('#employeeTaskPagination').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 10
        }),
        "columns": [
        	{ "data": "taskTitle" },
        	{ "data": "priority" },
        	{ "data": "projectName" },
        	{ "data": "dueOn" },
        	{ "data": "completion" },
        	{ "data": "severity" },
        	{ "data": "ownerName" },
        	{ "data": "estimatedTime" },
        	{ "data": "status" }    
        	   ],
        "columnDefs": [{
	  	  	"targets": colIndex++,
		  	"data": "taskTitle",
		  	"visible": true,
		  	"render": function(data, type, report) {
		  		var result = "";
			   	    result = result+'<td><div class="text-left">'+report.taskName+'</div></td>';
	             return result;
	  	      }
	  		},{
		  	  	"targets": colIndex++,
			  	"data": "priority",
			  	"visible": $("input[data-name='priority']").is(":checked"),
			  	"render": function(data, type, report) {
			  		var result = "";
			  		if(report.priority != null){
   	                    result = result +' <td><div class="width50px"><small class="colorBox pull-left" style="background:'+report.colorPic+'"></small>'+report.priority+'</div></td>';
   		    		}else{
   	                    result = result +' <td><div class="width150px">--</div></td>';
   		    		}
		             return result;
		  	      }
		  	},{
		  	  	"targets": colIndex++,
			  	"data": "projectName",
			  	"visible": $("input[data-name='projectName']").is(":checked"),
			  	"render": function(data, type, report) {
			  		var result = "";
                    result = result +' <td><div class="tbldata-dotdot">'+report.projctName+'</div></td>';
		             return result;
		  	      }
		  	},{
		  	  	"targets": colIndex++,
			  	"data": "dueOn",
			  	"visible": $("input[data-name='dueOn']").is(":checked"),
			  	"render": function(data, type, report) {
			  		var result = "";
			  		if(report.dueDate != null){
                        result = result +' <td><div class="width100px">'+report.dueDate+'</div></td>';
                    }else{
                        result = result +' <td><div class="width100px">--</div></td>';
                    }
		             return result;
		  	      }
		  	},{
		  	  	"targets": colIndex++,
			  	"data": "completion",
			  	"sortable": false,
			  	"visible": $("input[data-name='completion']").is(":checked"),
			  	"render": function(data, type, report) {
			  		var result = "";
				  		result = result +'  <td>';
	                	result = result +'	  <div class="width150px">';
		  				result = result +'	  <div class="single-chart" title="Profile Updated	">';
		  				result = result +'			<svg viewBox="0 0 36 36" class="circular-chart orange">';
		  				result = result +'			  <path class="circle-bg" d="M18 2.0845';
		  				result = result +'				  a 15.9155 15.9155 0 0 1 0 31.831';
		  				result = result +'				  a 15.9155 15.9155 0 0 1 0 -31.831" />';
		  				if(report.percentage != null){
		  					if(report.percentage > 0){
	    	  					result = result +'			  <path class="circle"';
		  					}else{
	    	  					result = result +'			  <path class="circle0"';
		  					}
	    	  				result = result +'				stroke-dasharray="'+report.percentage+', 100"';
	    	  				result = result +'				d="M18 2.0845';
	    	  				result = result +'				  a 15.9155 15.9155 0 0 1 0 31.831';
	    	  				result = result +'				  a 15.9155 15.9155 0 0 1 0 -31.831" />';
	    	  				result = result +'			  <text x="18" y="20.35" class="percentage">'+report.percentage+'%</text>';
		  				}else{
		  					result = result +'			  <path class="circle0"';
	    	  				result = result +'				stroke-dasharray="0, 100"';
	    	  				result = result +'				d="M18 2.0845';
	    	  				result = result +'				  a 15.9155 15.9155 0 0 1 0 31.831';
	    	  				result = result +'				  a 15.9155 15.9155 0 0 1 0 -31.831" />';
	    	  				result = result +'			  <text x="18" y="20.35" class="percentage">0%</text>';
	    	  				
		  				}
		  				
		  				result = result +'			</svg>';
		  				result = result +'		</div>';
		  				result = result +'	  </div>';
	  				    result = result +'	</td>';
		             return result;
		  	      }
		  	},{
		  	  	"targets": colIndex++,
			  	"data": "severity",
			  	"visible": $("input[data-name='severity']").is(":checked"),
			  	"render": function(data, type, report) {
			  		var result = "";
			  		var severity = "--";
			  		if(report.taskSeverity != null && report.taskSeverity != "" && report.taskSeverity != "undefined"){
			  			severity = report.taskSeverity;
			  		} 
	  				result = result +'<td><div class="width100px">'+severity+'</div></td>';
		             return result;
		  	      }
		  	},{
		  	  	"targets": colIndex++,
			  	"data": "ownerName",
			  	"sortable": false,
			  	"visible": $("input[data-name='ownerName']").is(":checked"),
			  	"render": function(data, type, report) {
			  		var result = "";
			  		var ownerName = "--";
			  		if(report.taskOwnerName != null && report.taskOwnerName != "" && report.taskOwnerName != "undefined"){
			  			ownerName = report.taskOwnerName;
			  		} 
	  				result = result +'<td><div class="tbldata-dotdot">'+ownerName+'</div></td>';
		             return result;
		  	      }
		  	},{
		  	  	"targets": colIndex++,
			  	"data": "estimatedTime",
			  	"sortable": false,
			  	"visible": $("input[data-name='estimatedTime']").is(":checked"),
			  	"render": function(data, type, report) {
			  		var result = "";
			  		var estimatedTime = "--";
			  		if(report.estimatedTime != null && report.estimatedTime != "" && report.estimatedTime != "undefined"){
			  			estimatedTime = report.estimatedTime;
			  		} 
	  				result = result +'<td><div class="width100px">'+estimatedTime+'</div></td>';
		             return result;
		  	      }
		  	},
		  	{
		  	  	"targets": colIndex++,
			  	"data": "status",
			  	"render": function(data, type, report) {
			  		var result = "";
	  				result = result +'<td><div class="width100px">'+report.taskStatus+'</div></td>';
		             return result;
		  	      }
		  	}
		  	
        ],
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": "", 
     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
     }
    });
    $("input[type=search]").hide();
}
$("#assetDataTableSearch").keyup(function(e) {
   if(e.which === 13) {
	   getDynamicEmployeeTaskSummaryRepotList();
   }
});
</script>
<script>
   function employeeTaskExport(type){
	   var candidateId = '${candidateId}';
	   if(type == "pdf"){
			exportType = "pdf";
		}else{
			exportType = "xls";
		}
	   var fromDate = $("#fromDate").val();
	   var toDate = $("#toDate").val();
		
	   var priorityFilter = [];
		$('input:checkbox[name=priorityFilter]:checked').each(function() 
	    {
			priorityFilter.push($(this).val());
		});
		
		var taskStatusFilter = [];
		$('input:checkbox[name=taskStatusFilter]:checked').each(function() 
	    {
			taskStatusFilter.push($(this).val());
		});
		
		var taskTypeFilter = [];
		$('input:checkbox[name=taskTypeFilter]:checked').each(function() 
	    {
			taskTypeFilter.push($(this).val());
		});
		
		var projectFilter = [];
		$('input:checkbox[name=projectFilter]:checked').each(function() 
	    {
			projectFilter.push($(this).val());
		});
		
		var severityFilter = [];
		$('input:checkbox[name=severityFilter]:checked').each(function() 
	    {
			severityFilter.push($(this).val());
		});
		
		
	   var searchValue = $("#assetDataTableSearch").val();
		 searchValue = searchValue ? searchValue : "";
		 window.location.href = sourceleadUrl+"/getEmployeeTaskExport?searchValue="+searchValue+"&candidateid="+candidateId+"&exportType="+exportType+"&projectFilter="+projectFilter+"&taskTypeFilter="+taskTypeFilter+"&taskStatusFilter="+taskStatusFilter+"&priorityFilter="+priorityFilter+"&fromDate="+fromDate+"&toDate="+toDate+"&severityFilter="+severityFilter;
   }
</script>
  <script>
  $("html").click(function(){
		$(".colPop").hide();
	});
	$(".colPop, .columIcon").click(function(e){
	 	e.stopPropagation();
	});
	function columPop(){
		$(".colPop").show();
	}
	function columClose(){
		$(".colPop").hide();
	}
  </script> 

<jsp:include page="userUsageReportsGraph.jsp" />
<jsp:include page="../masterFooter.jsp" />