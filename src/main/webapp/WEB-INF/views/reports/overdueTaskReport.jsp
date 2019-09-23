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
	getOverDueTaskRepotList();
 });
 
 </script>
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Overdue Task Report</h2>
      </div>
            <div class="pull-right brud-crum">
	            <a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>"> Home </a>
	             &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>"> Report Dashboard </a> 
	             &raquo; <a href="<c:url value='${appPath}/projectReportList'/>">Project Report </a> 
	             &raquo; Overdue Task Report List
          </div>
    </section>
    <div class="invoiceGridview tdVmiddle clearfix project_wrap">
      <div class="" id="payrollRegister" style="display: block;">
        <div class="clearfix">
          <div class="positionRelative reportHead clearfix">
			  <span class="pull-left refreIcon mb5">
				  <a href="javascript:void(0)" class="refeIcon" title="Refresh" onclick="resetTaskRpoertFilter();"></a>
			  </span>
            <ul class="pull-right list-unstyled list-inline">
              <li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a></li>
              <li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
                <div class="newActPop" style=""> <span>
                <a href="javascript:void(0)" onclick="overdueTaskExport('pdf');" title="PDF">Export PDF</a> 
                <a href="javascript:void(0)" onclick="overdueTaskExport('excl');" title="Excel">Export Excel</a> </span> </div>
              </li>
             <li class="" id="assetSearch" style="display: block;"><input type="text" class="repSearchBar" placeholder='Search' id='overdueDataTableSearch'/><span class="repSearchIcon" onClick="getOverDueTaskRepotList();"></span></li>
            </ul>
          </div>
          <div class="datatable_filter dataSeaTop" id="ovedueReportListDivId"/>
           
        </div>
      </div>
    </div>
    
	 <div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search-small" id="modalWallet">
	      <div class="popupHead"><span>Over due</span><i class="closePop pull-right popClose" onclick="closePopUp();"></i></div>
	        <form id="#" name="" action="" method="post">
	        <div class="popupContent">
				<div class="addInner">
				
	          <div class="row">
	            <div class="col-md-3 mb positionRelative">
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
			 
			 <div class="col-md-4 mb positionRelative">
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
			 
			 <div class="col-md-5 mb positionRelative">
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
				<input type="button" value="Apply" id="search_requiremet" onclick="getOverDueTaskRepotList();closePopUp();" class="btn btn-sm btn-success">
				<input type="button" value="Reset" id="clear_search" onclick="resetTaskRpoertFilter();" class="btn btn-sm btn-primary">
			</div>
	        </div>
	        <div>
	</div></form>
	  </div> 
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
	$("ul#myPriorityUL > li input:checkbox").prop("checked",false);
	$("ul#myProjectUL > li input:checkbox").prop("checked",false);
	
	resetCalender();
	getOverDueTaskRepotList();
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
function getOverDueTaskRepotList(){
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();

	var priorityFilter = [];
	$('input:checkbox[name=priorityFilter]:checked').each(function() 
    {
		priorityFilter.push($(this).val());
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
	
	
	var searchValue = $("#overdueDataTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	var url = sourceleadUrl	+"/getOverDueTaskReportList?searchValue="+searchValue+"&priorityFilter="+priorityFilter+"&taskTypeFilter="+taskTypeFilter+"&projectFilter="+projectFilter+"&fromDate="+fromDate+"&toDate="+toDate;
	prepareDynamicEmployeeTaskList(url);	
}
function prepareDynamicEmployeeTaskList(url){
	var result = "";
    result = result +'<table id="overDueTaskPagination" class="table">';
	result = result +' <thead>';
	result = result +' <tr>';
	result = result +' <th>Task Title</th>';
    result = result +' <th>Priority</th>';
    result = result +' <th>Task Type</th>';
	result = result +' <th>Assign To </th>';
    result = result +' <th>Project Name</th>';
    result = result +' <th>Due Date</th>';
    result = result +' <th>Completion</th>';
	result = result +'   </tr>';
	result = result +' </thead>';
	result = result +'</table>';
	$("#ovedueReportListDivId").empty();
	$("#ovedueReportListDivId").append(result);
	projectEmployeeTaskPaginations(url);
}
function projectEmployeeTaskPaginations(url){
	var colIndex = 0;
    $('#overDueTaskPagination').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 10
        }),
        
        "columnDefs": [ 
        	{
    	  	  	"targets": colIndex++,
    		  	"data": "",
    		  	"render": function(data, type, report) {
    		  		var result = "";
    			   	    result = result+'<td><div class="tbldata-dotdot-me" title="'+report.taskName+'">'+report.taskName+'</div></td>';
    	             return result;
    	  	      }
    	  		},{
        	  	  	"targets": colIndex++,
        		  	"data": "",
        		  	"render": function(data, type, report) {
        		  		var result = "";
        		  		var taskPriority = "--";
	        		  		if(report.taskPriority != null && report.taskPriority != "" && report.taskPriority != "undefined"){
	        		  			taskPriority = report.taskPriority;
	        		  		}
        			   	    result = result+'<td><div class="ttblminwidth-50">'+taskPriority+'</div></td>';
        	             return result;
        	  	      }
        	  	  },{
	           	  	  	"targets": colIndex++,
	           		  	"data": "",
	           		  	"render": function(data, type, report) {
	           		  		var result = "";
	           			   	    result = result+'<td><div class="tbldata-dotdot">'+report.taskType+'</div></td>';
	           	             return result;
	           	  	      }
        	       },{
            	  	  	"targets": colIndex++,
            		  	"data": "",
            		  	"sortable": false,
            		  	"render": function(data, type, report) {
            		  		var result = "";
            		  		    result = result+'<td>';
            		  		    if(report.assignedTaskUsers.length > 0){
                		  		    result = result+'<div class="clearfix width150px">';
                		  		    	var names = "";
                		  		    	for (var i=0; i<report.assignedTaskUsers.length; i++)
                		   				{
                		  		    		var option = report.assignedTaskUsers[i];
                		  		    		if(i < 5){
                		  		    			result = result+'  	  <i class="assignedImages mr10 mb10">';
                				      			if(option.assignedUserUrl  != null && option.assignedUserUrl != "" && option.assignedUserUrl != "undefined"){
                    				      			result = result+'	  		<img src='+option.assignedUserUrl+' alt="assignedImage" onerror="predefineProfileImage(this);" title="'+option.assignedUserName+'" >';
                				      			}else{
                    				      			result = result+'	  		<img src="images/client-img.png" alt="assignedImage" onerror="predefineProfileImage(this);" title="'+option.assignedUserName+'" >';
                				      			}
                				      			result = result+'	  </i>';
                		  		    		}
            				      				 if(i > 4){
            				      					 if(i > 5 && i < report.assignedTaskUsers.length){
            				      						names = names+", ";
            				      					 }
            				      					names = names+option.assignedUserName;
            				      				 }
            				      				
                		   				}
                		  		    	if( report.assignedTaskUsers.length > 4){
	           				      			result = result+'<b class="assignedImages mr10 mb10" title="'+names+'">more...</b>';
	       				      			} 
            		  		    	result = result+'</div>';	
            		  		    }else{
            		  		    	result = result+'<div class="tblminwidth-50 mt10 mb5 clearfix">--</dv>';
            		  		    }
            		  		  
				      			result = result+'</td>';
            	             return result;
            	  	      }
            	  	},{
            	  	  	"targets": colIndex++,
            		  	"data": "",
            		  	"render": function(data, type, report) {
            		  		var result = "";
        			   	      result = result+'<td><div class="tbldata-dotdot" title="'+report.projectName+'">'+report.projectName+'</div></td>';
            	             return result;
            	  	      }
            	  	},{
            	  	  	"targets": colIndex++,
            		  	"data": "",
            		  	"render": function(data, type, report) {
            		  		var result = "";
            		  		var dueDate = "--";
            		  		if(report.dueDate != null && report.dueDate != "" && report.dueDate != "undefined"){
            		  			dueDate = report.dueDate;
            		  		}
            			   	    result = result+'<td><div class="tbldata-dotdot">'+dueDate+'</div></td>';
            	             return result;
            	  	      }
            	  	},{
        		  	  	"targets": colIndex++,
        			  	"data": "",
        			  	"sortable": false,
        			  	"render": function(data, type, report) {
        			  		var result = "";
        				  		result = result +'  <td>';
        	                	result = result +'	  <div class="width150px">';
        		  				result = result +'	  <div class="single-chart" title="Profile Updated	">';
        		  				result = result +'			<svg viewBox="0 0 36 36" class="circular-chart orange">';
        		  				result = result +'			  <path class="circle-bg" d="M18 2.0845';
        		  				result = result +'				  a 15.9155 15.9155 0 0 1 0 31.831';
        		  				result = result +'				  a 15.9155 15.9155 0 0 1 0 -31.831" />';
        		  				if(report.completion != null){
        		  					if(report.completion > 0){
        	    	  					result = result +'			  <path class="circle"';
        		  					}else{
        	    	  					result = result +'			  <path class="circle0"';
        		  					}
        	    	  				result = result +'				stroke-dasharray="'+report.completion+', 100"';
        	    	  				result = result +'				d="M18 2.0845';
        	    	  				result = result +'				  a 15.9155 15.9155 0 0 1 0 31.831';
        	    	  				result = result +'				  a 15.9155 15.9155 0 0 1 0 -31.831" />';
        	    	  				result = result +'			  <text x="18" y="20.35" class="percentage">'+report.completion+'%</text>';
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
$("#overdueDataTableSearch").keyup(function(e) {
   if(e.which === 13) {
	   getOverDueTaskRepotList();
   }
});
</script>
<script>
   function overdueTaskExport(type){
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
		
		
	   var searchValue = $("#overdueDataTableSearch").val();
		 searchValue = searchValue ? searchValue : "";
		 window.location.href = sourceleadUrl+"/getOverdueTaskExport?searchValue="+searchValue+"&exportType="+exportType+"&projectFilter="+projectFilter+"&taskTypeFilter="+taskTypeFilter+"&priorityFilter="+priorityFilter+"&fromDate="+fromDate+"&toDate="+toDate;
   }
</script> 
   

<jsp:include page="userUsageReportsGraph.jsp" />
<jsp:include page="../masterFooter.jsp" />