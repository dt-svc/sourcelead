<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<style type="text/css">
.proj_show span { padding: 0px 15px 0px 0px; }
.proj_show i { position: absolute; right: 0px; }
</style>
<script>
	// Close Advanced Search
		function closeFilterPop(){
			$("div.cand-advsrch-popup").hide();
			$(".advancedsearch-mask").fadeOut();
		}
		function openFilterPop(){
			$("div.cand-advsrch-popup").toggle();
			 $(".advancedsearch-mask").fadeIn();
		}
		
		 
		//Export filter
		function exportpop(){
		  $("#exportPop").toggle();
		}
	   $("#export-pop").click(function(){
	       $("#exportPop").toggle();
	   });
	   $("html").click(function() {
	       $("#exportPop").hide();
	   });
	   $("#exportPop, #export-pop").click(function(e) {
	       e.stopPropagation();
	   }); 
	</script>
	
      <!-- Content Area -->
       <section class="content-wraper">
        <section class="content-header clearfix">
        
        <c:if test="${!empty msg}">
	        <div class="success_pop" id="selector" style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
          <div class="pull-left">
            <h2>Projects</h2>
          </div>
          <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Projects</div>
        </section>

        
	   <div class="timesheet_out timesheet_background">
		
		
<div class="positionRelative reportHead clearfix">
      <span class="pull-left refreIcon" id="advanceSearchDiv">
      		<a href="javascript:void(0)" class="refeIcon" id="resetVendor" title="Refresh" onclick="resetProjectFilter();"></a>
      </span>
      
      <ul class="pull-right list-unstyled list-inline">
      
      	  <li><sec:authorize access="hasAnyRole('ROLE_CREATE_PROJECT')">
			<a class="action newAdBtn mr5" onclick="getActions();" href="javascript:void(0)">+ Add <i class="fa fa-caret-down"></i></a>
			<div class="action-pop">
				<span> 
					<a href="<c:url value='${appPath}/project'/>" > Internal Project</a>
                    <a href="<c:url value='${appPath}/clientProject'/>" >Client Project </a>	
                    <a href="<c:url value='${appPath}/project?projectType=sharedProject'/>" > Client Project(Managed) </a>			
				</span>
			</div>
			</sec:authorize>
			</li>
			
				<li id="vendorFiletPopup"><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openFilterPop();"></a></li>
				
				<li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
					<div class="newActPop">
						<span>
	                	<sec:authorize access="hasRole('ROLE_EXPORT_VENDORS')"> 
		           	   		<a href="javascript:void(0)" onclick="projectExport('Pdf');" title="PDF">Export PDF</a>
							<a href="javascript:void(0)" onclick="projectExport('Excel');" title="Excel">Export Excel</a>            
			          	</sec:authorize>
						</span>
					</div>
				</li>
		  <li class=""><input type="text" class="repSearchBar" placeholder='Search' id='projectsDataTableSearch'/><span class="repSearchIcon" onClick="filter();"></span></li>
        </ul>
      </div>		
			
	<div class="project_tabs_inn_table vendor_wrap_inn_table dataFilterNew">
    	<div id="projectFilterList" class="datatable_filter"></div>
	</div>	   
				
		</div>
		
		<div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search">
				<h5 class="headerAdsrch">Filters
					<a href="javascript:void(0)" class="close-div" onClick="closeFilterPop()"><i
						class="fa fa-close"></i></a>
				</h5>
				<div class="addInner">
					<div class="row">
						<div class="col-md-3">
						<div class="row">
						<div class="col-md-12 mb">
								<label><strong>Project Type</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="typeFilter">
										<li class="checkBox positionRelative" ><input id="c12" type="checkbox" name="projectType" value="Client" ><label for="c9">Client Project(s)</label></li>
			                    	    <li class="checkBox positionRelative" ><input id="c11" name="projectType" type="checkbox" value="Internal"><label for="c7">Internal Project(s)</label></li>
			                        </ul>
								 </div>
								 </div> 
								 
								 
						<div class="col-md-12 mb">
							<label><strong>Project Status</strong></label>
							 <div class="clearfix">
								<ul class="list-unstyled" id="statusFilter">
									<li class="checkBox positionRelative" ><input id="c8" type="checkbox"  name="projectStatus"   value ="Completed" ><label for="c8">Completed</label></li>
		               				<li class="checkBox positionRelative" ><input id="c10" type="checkbox"  name="projectStatus"   value ="Dropped" ><label for="c10">Dropped</label></li>
		                    	    <li class="checkBox positionRelative" ><input id="c7" name="projectStatus" type="checkbox" value ="In-Progress"  checked ><label for="c7">In-Progress</label></li>
									<li class="checkBox positionRelative" ><input id="c9" type="checkbox" name="projectStatus" value ="Not Started" ><label for="c9">Not Started</label></li>
		               				<li class="checkBox positionRelative" ><input id="c6" type="checkbox"  name="projectStatus"  value ="On-Hold"  checked ><label for="c6">On-Hold</label></li>
		               				
		                        </ul>
							 </div>
							 </div> 
							 
							 
					 	<div class="col-md-12 mb">
							<label><strong>Timesheet Frequency</strong></label>
							 <div class="clearfix">
								<ul class="list-unstyled" id="frequencyFilter">
									<li class="checkBox positionRelative" ><input  type="checkbox" name="invoiceFrequency" value ="Bi-Weekly" ><label for="c9">Bi-Weekly</label></li>
		               				<li class="checkBox positionRelative" ><input  type="checkbox"  name="invoiceFrequency"  value ="Monthly"><label for="c6">Monthly</label></li>
									<li class="checkBox positionRelative" ><input  type="checkbox"  name="invoiceFrequency"   value ="Semi-Monthly" ><label for="c8">Semi-Monthly</label></li>
		                    	    <li class="checkBox positionRelative" ><input  name="invoiceFrequency" type="checkbox" value ="Weekly"><label for="c7">Weekly</label></li>
		               				
		                        </ul>
							 </div>
						 </div> 
							 
							 
						  </div>
						</div>
						<c:if test="${clientListSize > 0}">
							 <div class="col-md-4 mb positionRelative">
									<div class="clearfix dynamic_search filterDynamicsearch">
										<input type="text" id="myClientsInput" onkeyup="myClientsFunction()" placeholder="Clients" title="Type in a clients">
										<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
									</div>
									 <div class="clearfix filterSearch-ul">
										<ul class="list-unstyled positionRelative filterSearch mt5" id="myClientsUL">
										    <c:forEach var="report" items="${add_client}">
											     <li class="checkBox positionRelative"><input  type="checkbox" name="clientNameById" value ="${report.orgRelId}" ><label>${report.clientName}</label></li>
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
										<ul class="list-unstyled positionRelative filterSearch mt5" id="myManagersUL">
											<c:forEach var="manager" items="${projectManagersList}">
											<li class="checkBox positionRelative"><input  type="checkbox" name="projectManagers" value ="${manager.id}" ><label>${manager.fullName}</label></li>
											</c:forEach>
										</ul>
									 </div> 
							 </div> 
							 </c:if>
							 
					   <c:if test="${prjectTagsListSize > 0}">
							 <div class="col-md-4 mb positionRelative">
									<div class="clearfix dynamic_search filterDynamicsearch">
										<input type="text" id="myTagsInput" onkeyup="myTagsFunction()" placeholder="Tags" title="Type in a tags">
										<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
									</div>
									 <div class="clearfix filterSearch-ul">
										<ul class="list-unstyled positionRelative filterSearch mt5" id="myTagsUL">
											<c:forEach var="tags" items="${prjectTagsList}">
											<li class="checkBox positionRelative"><input  type="checkbox" name="tags" value ="${tags}" ><label>${tags}</label></li>
											</c:forEach>
										</ul>
									 </div> 
							 </div> 
						</c:if>
						
					</div>
					</div>
					<div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Apply" id="search_requiremet"
								onclick="closeFilterPop();filter();" class="btn btn-sm btn-success" />
							<input type="button" value="Reset" id="clear_search"
								onclick="resetProjectFilter();" class="btn btn-sm btn-primary" />
						</div>
					</div>
				</div>
				
		<%-- </c:if> --%>
		<div class="advancedsearch-mask" onClick="closeAdsrch()"></div>
		<div class="side_mask"><div class="proj_close"><i></i></div></div>
		<div class="project_side_wrap">
			<div class="project_side_wrap_inn">
		    <h1 id="projectNameById"></h1> <div class="proj_close"><i></i></div>
		     <div id="projectSideDiv">
		     </div>   
			<div class="project_error">
				<p>Employee delete message here ....</p>
				<a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> 
			</div>	
		    </div>
		  </div> 
		   </div>		
	   </section>
	   
	   <div id="popUp"></div>
	   
      <div class="pop_mask"></div>
     
	   
	   
<!--   Popup's   -->

<script>
function getClientProjectPop(){
	 $('.addcandidate-popup').fadeIn(200);
     $('.pop_mask').fadeIn(200);
     $('#addmorepop').slideUp(200);
}

function closeClientProject(){
    $('.addcandidate-popup').fadeOut(200);
    $('.pop_mask').fadeOut(200);
   }
</script>
               
<script>
function resetProjectFilter(){
	$("ul#typeFilter > li input:checkbox").prop("checked",false);
	$("ul#statusFilter > li input:checkbox").prop("checked",false);
	$("ul#myClientsUL > li input:checkbox").prop("checked",false);
	$("ul#myManagersUL > li input:checkbox").prop("checked",false);
	$("ul#frequencyFilter > li input:checkbox").prop("checked",false);
	$("ul#myTagsUL > li input:checkbox").prop("checked",false);
	
	$("#projectsDataTableSearch").val('');
	document.getElementById("c6").checked = true;
	document.getElementById("c7").checked = true;
	filter();
	
}

function myClientsFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myClientsInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myClientsUL");
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


function myManagersFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myManagersInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myManagersUL");
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

function myTagsFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myTagsInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myTagsUL");
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
		  closeFilterPop();
	  }
	});

</script>
<script type="text/javascript">
window.onload = function () {
		projectsList("${searchValue}","${searchResult}","${projectId}","${projectName}","${startDate}","${endDate}","${status}","${projectKeyword}");
 };
</script>
<script type="text/javascript">
function projectsList(searchValue,searchresult,projectId,projectName,startDate,endDate,status,projectKeyword){ 
	if(searchresult=="universalProjectsList")	 {
		var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
	         type : "POST",
	          url: sourceleadUrl+"/getUniversalProjectsListByOrgLocationID?${_csrf.parameterName}=${_csrf.token}&searchValue="+searchValue,       
	          success :function(data) {
	        	  prepareProjectsList(data);
	         },
	          
	     }); 
	  }if(projectKeyword == "projectSearch"){
		  projectSideBar(projectId,projectName,startDate,endDate,status);
		  filter();
		  
	  }else{
		  	$('input[name=projectStatus]').attr('checked',false);
		  	$("#c6").prop("checked", true);
		  	$("#c7").prop("checked", true);
			$('input[name=clientNameById]').attr('checked',false);
			filter();
		/* searchProject(); */
		}
}
</script>

<script>
$(document).ready(function() {
    $("#filter1ApprovePop").click(function(){
		$("#filter1ApprovedPop").slideToggle(200);
		 $("#filter2ApprovedPop,.action-pop").slideUp(200);
	});
    $("html").click(function() {
        $("#filter1ApprovedPop").slideUp(200);
    });
    $("#filter1ApprovedPop, #filter1ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
    
    
    $("#filter2ApprovePop").click(function(){
		$("#filter2ApprovedPop").slideToggle(200);
		$("#filter1ApprovedPop,.action-pop").slideUp(200);
	});
    $("html").click(function() {
        $("#filter2ApprovedPop").slideUp(200);
    });
    $("#filter2ApprovedPop, #filter2ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
    
  
    
}); 

</script>


<script>
function filter(){
	var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  projectListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  projectListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  projectListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  projectListDate = 'YYYY-DD-MM'; 
	  }
	  
	  var result = "";
	 	result = result +" <div class='remove_search'>";
// 	 	result = result +" <input type='text' placeholder='Search' id='projectsDataTableSearch'/>";
	 	result = result +"</div>";
	 	
	 	result = result + '<table id="projectsListTable" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +'	<th>Id</th>';
		result = result +'	<th>Project Id</th>';
    	result = result +'    <th>Name</th>'; 
    	result = result +'     <th>Manager</th>';
    	result = result +'     <th>Timesheet Frequency</th>';
    	result = result +'     <th>Budget</th>';
    	result = result +'     <th>Date<br>(<small class="custom-small">'+projectListDate+'</small>)</th>';
    	result = result +'      <th>Client</th>';
    	result = result +'       <th>Status</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    $("#projectFilterList").empty();
	    $("#projectFilterList").append(result);
	    
	    projectDataTableList();  
}

</script>

<script>
function projectDataTableList(){
	var values = [];
	$('input:checkbox[name=projectStatus]:checked').each(function() {
			values.push($(this).val());
    });
	
	
	var projectType = [];
	$('input:checkbox[name=projectType]:checked').each(function() {
		projectType.push($(this).val());
    });
	
	
	
	var clients = [];
	
	$('input:checkbox[name=clientNameById]:checked').each(function() {
		clients.push($(this).val());
	});
	
	
	var projectManagers = [];
	$('input:checkbox[name=projectManagers]:checked').each(function() 
		    {
		projectManagers.push($(this).val());
     });
	
	var invoiceFrequency = [];
	$('input:checkbox[name=invoiceFrequency]:checked').each(function() 
		    {
		invoiceFrequency.push($(this).val());
     });
	
	var tags = [];
	$('input:checkbox[name=tags]:checked').each(function() 
    {
		tags.push($(this).val());
    });
	
	
	
	 var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#projectsDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/getProjectsByStatusAndClient?searchValue="+searchValue+"&values="+values+"&clients="+clients+"&projectType="+projectType+"&projectManagers="+projectManagers+"&invoiceFrequency="+invoiceFrequency+"&tags="+tags;
	 projectsPagination(url,sourceleadUrl);
}
</script>



<script>
function projectsPagination(url,sourceleadUrl){
	var colIndex = 0;
    $('#projectsListTable').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 1
        }),
        "columns": [
        	 { "data": "Id" },
            { "data": "projectSequeneNumber" },
            { "data": "projectName"},
            { "data": "projectManagers"},
            { "data": "invoiceFrequency" },
            { "data": "budget" },
            { "data": "projectDate" },
            { "data": "client"},
            { "data": "projectStatus"}
        ],
        "columnDefs": [ {
    	  	"targets": colIndex++,
    	  	"data": "projectSequeneNumber",
    	  	"render": function(data, type, opt, meta) {
    	  		  var result = "";
		         result = result + '<td data-title="Id"><div class="tblminwidth-75">'+opt.id+'</div></td>';
	             return result;
    	  	   }
           }, {
	    	  	"targets": colIndex++,
	    	  	"data": "projectSequeneNumber",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		  if(opt.projectSequeneNumber !=null && opt.projectSequeneNumber !='' && opt.projectSequeneNumber !='undefined'){
			        		    	result = result + '<td data-title="Id"><div class="tblminwidth-75">'+opt.projectSequeneNumber+'</div></td>';
			        		    }else{
			        		    	result = result + '<td data-title="Id"><div class="tblminwidth-75">--</div></td>';
			        		    }
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "projectName",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  
            	  '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\',\'ROLE_CREATE_PROJECT_TASKS\',\'ROLE_VIEW_EDIT_PROJECT_TASKS\',\'ROLE_VIEW_PROJECT_EMPLOYEES\',\'ROLE_VIEW_EDIT_PROJECT_EMPLOYEES\',\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\')">'
 		    		if(opt.projectMode != null && opt.projectMode == 'Draft'){
 		    			if(opt.isProjectShared == 'Y'){
 		    				var locationId ='${SourceLeadWeb_orgLocations.id}';	
 		    				if(locationId == opt.sharedClientOrgLocationId){
 		    					result = result +'<td data-title="Name"><div class="proj_show tbldata-dotdot_140"><span class="pull-left" title="'+opt.projectName+'">'+opt.projectName+'</span></div></td>';
 		    				}else{
 		    					result = result +'<td data-title="Name"><a href="'+sourceleadUrl+'/project?projectType=sharedProject&projectUUID='+opt.projectUUID+'" ><div class="proj_show tbldata-dotdot_140"><span class="pull-left" title="'+opt.projectName+'">'+opt.projectName+'</span><i class="fa fa-binoculars pull-right"></i></div></a></td>';
 		    				}
 		    			}else{
 		    				result = result +'<td data-title="Name"><a href="'+sourceleadUrl+'/project?projectUUID='+opt.projectUUID+'" ><div class="proj_show tbldata-dotdot_140"><span class="pull-left" title="'+opt.projectName+'">'+opt.projectName+'</span><i class="fa fa-binoculars pull-right"></i></div></a></td>';
 		    			}
 		    			
 		    		}else{
 		    			result = result +'<td data-title="Name"><a href="'+sourceleadUrl+'/projectSummary?projectUUID='+opt.projectUUID+'&projectClientUUID='+opt.projectClientUUID+'" ><div class="proj_show tbldata-dotdot_140"><span class="pull-left" title="'+opt.projectName+'">'+opt.projectName+'</span><i class="fa fa-binoculars pull-right"></i></div></a></td>';
 		    		}
	    		  '</sec:authorize>';
            	  
	              return result;
              		
              }
	      
	          },{
		    	  "targets": colIndex++,
	              "data": "projectManagers",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  var projectManagers = '';
	            	  
	            	  if(opt.managerLevel1 != null && opt.managerLevel1 != '' && opt.managerLevel1 != 'undefined'){
	            		  projectManagers = opt.managerLevel1;
   		    		     }
	            	  
	            	  if(opt.managerLevel2 != null && opt.managerLevel2 != '' && opt.managerLevel2 != 'undefined'){
   		    		    	 projectManagers = opt.managerLevel2;
	   		    		 }
	            	  
	            	  if(opt.managerLevel1 != null && opt.managerLevel1 != '' && opt.managerLevel1 != 'undefined' && opt.managerLevel2 != null && opt.managerLevel2 != '' && opt.managerLevel2 != 'undefined' ){
   		    		    	 projectManagers = opt.managerLevel1+","+opt.managerLevel2;
	   		    		 }
	            		
	            	  result = result + '<td><div class="tblminwidth-75"><span title="'+projectManagers+'">'+projectManagers+'</span></div></td>';
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "invoiceFrequency",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            		result = result + '<td><div class="tblminwidth-75">'+opt.invoiceFrequency+'</div></td>';
	            		return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "budget",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    var projectbudgetAmount = "";
	            	    var projectbudgetCurrency = "";
	            	    if(opt.projectbudgetAmount != null && opt.projectbudgetAmount != "" && opt.projectbudgetAmount != "undefined"){
	            	    	projectbudgetAmount = opt.projectbudgetAmount;
	            	    	projectbudgetCurrency = opt.projectbudgetCurrency;
	            	    }else{
	            	    	projectbudgetAmount = "--";
	            	    }
	            		result = result + '<td><div class="tblminwidth-75" title="'+projectbudgetCurrency+' '+projectbudgetAmount+'">  '+projectbudgetCurrency+' '+projectbudgetAmount+'</div></td>';
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "projectDate",
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  var projectDate = '';
	            	  
	            	    if(opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){
	            	    	projectDate = opt.startDateStr+" - "+opt.endDateStr;
   		    		     }else{
   		    		    	projectDate = opt.startDateStr;
	   		    		}
	            		result = result + '<td><div class="tblminwidth-75">'+projectDate+'</div></td>';
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "client",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  
	            	    if(opt.client != null && opt.client != '' && opt.client != 'undefined'){
	            	    	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
	            	    	result = result + '<td><div class="tblminwidth-75"><span title="'+opt.client+'"><a href="'+sourceleadUrl+'/clientDashboard?orgRelationUUID='+opt.orgRelationUUID+'" title="'+opt.client+'">'+opt.client+'</a></span></div></td>';
	            	    	'</sec:authorize>';
	            	    	
	            	    	'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
	            	    	result = result + '<td><div class="tblminwidth-75"><span title="'+opt.client+'">'+opt.client+'</span></div></td>';
	            	    	'</sec:authorize>';
   		    		     }else{
   		    		    	result = result + '<td><div class="tblminwidth-75">--</div></td>';
	   		    		}
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "projectStatus",
	              "sortable": true,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  var projectStatus = "";
	            	  
	            	  if(opt.projectStatus == 'INPROGRESS'){
	            		  projectStatus = 'In-Progress';
	  	    			}
	   		    		else if(opt.projectStatus == 'COMPLETED'){
	   		    			projectStatus = '<b class="proj_active">Completed</b>';
	  	    			}
	   		    		else if(opt.projectStatus == 'ONHOLD'){
	   		    			projectStatus = '<b class="proj_deactivate">On-Hold</b>';
	  	    			}
	   		    		else if(opt.projectStatus == 'NOTSTARTED'){
	   		    			projectStatus = '<b class="proj_deactivate">Not-Started</b>';
	   	    			}
	   		    		else if(opt.projectStatus == 'DROPPED'){
	   		    			projectStatus = '<b class="proj_deactivate">Dropped</b>';
	   	    			}
	            	  
	            	   result = result +'<td data-title="Status"><div class="tblminwidth-75">';
	            	  
	            	  if(opt.projectMode == 'Draft'){
	            		  result = result + projectStatus+'<br/>('+opt.projectMode+')';
	            	  }else{
	            		  result = result + projectStatus;
		   		    	  result = result +'</div></td>';
	            	  }
	            	  
	            	  return result;
	              }
		     }],
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[0,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#projectsListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $('#projectsListTable').DataTable().column(0).visible(false);
    $("#projectsDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   
    		   $('#projectsDataTableSearch').parent().find('p.jquery_form_error_message').remove();
    	    	var searchData = $('#projectsDataTableSearch').val();
    	    	
    	    	if(searchData.endsWith("-") || searchData.endsWith("\\") || searchData.endsWith("//") || searchData.endsWith("%")){
       	    		$("#projectsDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
       	    	}else{
       	    		projectDataTableList();
       	    	}
    	    	
    	   }
    	});
}

</script>

<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 

 <script type="text/javascript">
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
               }
               o[this.name].push(this.value || '');
           } else {
               o[this.name] = this.value || '';
           }
       });
       return o;
   };
})(jQuery);
</script>


<script>
  $('#selector').delay(2000).fadeOut('slow');
</script> 

 <script>
// Edit pop
$(document).ready(function() {	
	
    $("#edit-user, #btnClose").click(function() {
        $(".user_summary_edit").slideUp(100);
    });
		
});
</script> 
<script>
	// Close Advanced Search
	function closePop(){
		$("div.cand-advsrch-popup").hide();
	}
	function clearKeys(){
		clearSearchKeys();
	}
	function openPop(){
	  $("div.cand-advsrch-popup").toggle();
	  
	}
</script>
<script type="text/javascript">	
	function clearSearchKeys(){
	   /*  $("#anyOfKeyword").val('').trigger("chosen:updated");
	    $("#allKeywords").val('').trigger("chosen:updated");
	    $("#excludeKeyWord").val('').trigger("chosen:updated"); */
	    $("#projectIdAdvanced").val('').trigger("chosen:updated");
	    $("#projectNameAdvanced").val('').trigger("chosen:updated");
	    $("#projectManagerAdvanced").val('').trigger("chosen:updated");
	    $("#invoiceFrequencyAdvanced").val('').trigger("chosen:updated");
	    $("#clientsAdvanced").val('').trigger("chosen:updated");
	    $("#statusAdvanced").val('').trigger("chosen:updated");
	    $("#employeeNameAdvanced").val('').trigger("chosen:updated");
	    $("#employeeEmailAdvanced").val('').trigger("chosen:updated");
	    
	    $("#projectStartDateAdvanced").val('');
	    
	    /* $("#anyOfKeyword").empty();
	    $("#allKeywords").empty();
	    $("#excludeKeyWord").empty(); */
	    $("#projectIdAdvanced").empty();
	    $("#projectNameAdvanced").empty();
	    $("#projectManagerAdvanced").empty();
	    $("#clientsAdvanced").empty();
	    $("#employeeNameAdvanced").empty();
	    $("#employeeEmailAdvanced").empty();
	}
</script>

	 
<script>
	$('#projectStartDateAdvanced').datepicker({
		dateFormat : 'yy-mm-dd',
		//dateFormat : 'dd-mm-yy',
	      changeMonth: true,
	      changeYear: true,
	      showButtonPanel:true,
	        closeText:'Clear',
	        beforeShow: function( input ) {
	            setTimeout(function () {
	                $(input).datepicker("widget").find(".ui-datepicker-current").hide();
	                var clearButton = $(input ).datepicker( "widget" ).find( ".ui-datepicker-close");
	                clearButton.unbind("click").bind("click",function(){$.datepicker._clearDate( input );});
	            }, 1 );
	        },
		onSelect : function(selected) {
			var dt = new Date(selected);
			dt.setDate(dt.getDate() + 1);
		}
	});
	$('#projectStartDateAdvanced').attr('readonly', true);
</script> 
<script type="text/javascript">
	function searchProject() {
		var validationSettings = {
				errorMessagePosition : 'element',
				borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd'
			};
		
		 if ($('#projectlist_form').validate(false, validationSettings)){ 
			
			var sourceleadUrl = '${sourceleadUrl}';
			var frm = $('#projectlist_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, ""); 
		    var jsonData = encodeURIComponent(con);
			var form = $('#projectlist_form')[0];
      		var formData = new FormData(form); 
			//It will escape all the special characters
			$("#search_project").attr("disabled", "disabled");
			$("#search_project").addClass('button').val('Processing..');

			$.ajax({
						type : "GET",
						url : sourceleadUrl+"/searchProject?json="+jsonData,
						success : function(data) {
							$("#search_project").addClass('button').val('Search');
							$("#search_project").removeAttr("disabled");
							closePop();
							prepareProjectsList(data);
						},
					}); 
			return true;
		 }else{ 
				return false;
		 } 
	} 
	
	$('body').on('blur', '#projectlist_form', function() {
		$("#projectlist_form").showHelpOnFocus();
		$("#projectlist_form").validateOnBlur(false, validationSettings);
	});
</script>

<script>
  function prepareProjectsList(data){
	  if(data != null && data != 'undefined' && data != "" && data != '[]'){
		    	var json = JSON.parse(data);
	        	var result = "";
		    	result = result +'<thead>';
		    	result = result +'	<tr>';
		    	result = result +'	<th>Id</th>';
		    	result = result +'    <th>Name</th>'; 
		    	result = result +'     <th>Manager</th>';
		    	result = result +'     <th>Invoice Frequency</th>';
		    	result = result +'     <th>Date</th>';
		    	result = result +'      <th>Client</th>';
		    	result = result +'       <th>Status</th>';
		    	result = result +'    </tr>';
		    	result = result +'</thead>	';
		    	result = result +'<tbody>';
		    	for (var i=0; i<json.length; i++)
				{   
		    		var report = json[i];
		    		var projectSequeneNumber;
		    		if(report.projectSequeneNumber != null && report.projectSequeneNumber != '' && report.projectSequeneNumber !='undefined'){
		    				projectSequeneNumber = report.projectSequeneNumber;
		    		}else{
		    			projectSequeneNumber = "---";
		    		}
		    		result = result +'<tr>';
		    		result = result +'<td data-title="Id"><div><span title='+projectSequeneNumber+'>'+projectSequeneNumber+'</span></div></td>';
	
		    		
		    		'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\',\'ROLE_VIEW_PROJECT_TASKS\',\'ROLE_VIEW_EDIT_PROJECT_TASKS\',\'ROLE_VIEW_PROJECT_EMPLOYEES\',\'ROLE_VIEW_EDIT_PROJECT_EMPLOYEES\',\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\')">'
		    			result = result +'<td data-title="Name"><div class="proj_show" onclick="projectSideBar('+report.id+',\''+report.projectName+'\',\''+report.startDate+'\',\''+report.endDate+'\',\''+report.projectStatus+'\');"><span title="'+report.projectName+'">'+report.projectName+'</span><i class="fa fa-binoculars"></i></div></td>';
		    		'</sec:authorize>';
		    		
		    		'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\',\'ROLE_VIEW_PROJECT_TASKS\',\'ROLE_VIEW_EDIT_PROJECT_TASKS\',\'ROLE_VIEW_PROJECT_EMPLOYEES\',\'ROLE_VIEW_EDIT_PROJECT_EMPLOYEES\',\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\')">'
	    		  		result = result +'<td data-title="Name"><div class="proj_show"><span title="'+report.projectName+'">'+report.projectName+'</span></div></td>';
	    			'</sec:authorize>';
	
		    		result = result +'<td data-title="Manager">';
		    		result = result +'<div><span title="'+report.level1ManagerName+' , '+report.level2ManagerName+'">';
		    		if(report.level1ManagerName !=null && report.level1ManagerName !='' && report.level1ManagerName !='undefined'){
		    			result = result +report.level1ManagerName;
		    		}
		    		if(report.level1ManagerName !=null && report.level1ManagerName !='' && report.level1ManagerName !='undefined' && report.level2ManagerName!=null && report.level2ManagerName!='' && report.level2ManagerName !='undefined'){
		    			result = result + " , ";
		    		}
		    		if(report.level2ManagerName!=null && report.level2ManagerName!='' && report.level2ManagerName !='undefined'){
		    			result = result +report.level2ManagerName;
		    		}
		    		result = result +'</span></div>';
		    		result = result +'</td>';
		    		
		    		result = result +'<td data-title="Invoice Frequency">';
		    		result = result +'<div><span title="'+report.invoiceFrequency+'">';
		    		if(report.invoiceFrequency !=null && report.invoiceFrequency !='' && report.invoiceFrequency !='undefined'){
		    			result = result +report.invoiceFrequency;
		    		}
		    		
		    		result = result +'</span></div>';
		    		result = result +'</td>';
		    		
		    		result = result +'<td data-title="Date"><div><span title="'+report.startDate+'">'+report.startDate+'</span>';
		    		if(report.endDate != null && report.endDate != '' && report.endDate != 'undefined'){
	   		    		result = result +'- <span title="'+report.endDate+'">'+report.endDate+'</span> </div></td>';
		    		}
		    		if(report.clientName != null && report.clientName != '' && report.clientName != 'undefined'){
		    			result = result +'<td data-title="Client"><div><span title="'+report.clientName+'">'+report.clientName+'</span></div></td>';  
		    		}else{
		    			result = result +'<td data-title="Client"><div>---</div></td>';  
		    		}
		    		result = result +'<td data-title="Status"><div>';
		    		 
		    		if(report.projectStatus == 'INPROGRESS'){
		   			  result = result +'<b class="proj_process">'+report.projectStatus+'</b>';
		   			}
		    		if(report.projectStatus == 'COMPLETED'){
		   			  result = result +'<b class="proj_active">'+report.projectStatus+'</b>';
		   			}
		    		if(report.projectStatus == 'ONHOLD'){
		   			  result = result +'<b class="proj_deactivate">'+report.projectStatus+'</b>';
		   			}
		    		if(report.projectStatus == 'NOTSTARTED'){
		    			result = result +'<b class="proj_deactivate">'+report.projectStatus+'</b>';
		    		}
			    	if(report.projectStatus == 'DROPPED'){
			    		result = result +'<b class="proj_deactivate">'+report.projectStatus+'</b>';
		    		}
		    		result = result +'</div></td>';
		    		
	                result = result +'</tr>';
				}
	            result = result +'  </tbody>';
	            result = result +' </table>';
	            result = result +'</div>';
	            $("#example").empty();  
	    	    $("#example").append(result);
	    	    pagination();
	  }else{
     	 $("#example").removeAttr("style");
	         var result ="";
			 result = result+"			<div class='blank-pages'>";
			 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
			 result = result+"			        <p>No Projects Found</p>";
			 result = result+"			 </div>";
			$("#example").empty();
		    $("#example").append(result); 
		    $(".dataTables_info").empty();
		    $(".dataTables_paginate").empty();
      }
  }
</script>
</html>


<script>
function getActions(){
	$(".action-pop").slideToggle(200);
	$(" #filter2ApprovedPop, #filter1ApprovedPop").slideUp(200);
	
	$("html").click(function(){
		$(".action-pop").slideUp(200);
	});
	$(".action, .action-pop").click(function(e){
	 	e.stopPropagation();
 	});	
}
</script>

<!-- ============================================= Export related functionality  By Laxman   ====================================== -->
<script>
function projectExport(type){
	if(type == "Pdf"){
		exportType = "pdf";
	}else{
		exportType = "xls";
	}
	var values = [];
	$('input:checkbox[name=projectStatus]:checked').each(function() {
			values.push($(this).val());
    });
	
	var projectType = [];
	$('input:checkbox[name=projectType]:checked').each(function() {
		projectType.push($(this).val());
    });
	
	var clients = [];
	$('input:checkbox[name=clientNameById]:checked').each(function() {
		clients.push($(this).val());
	});
	
	var projectManagers = [];
	$('input:checkbox[name=projectManagers]:checked').each(function() 
		    {
		projectManagers.push($(this).val());
     });
	
	var invoiceFrequency = [];
	$('input:checkbox[name=invoiceFrequency]:checked').each(function() 
		    {
		invoiceFrequency.push($(this).val());
     });
	
	var tags = [];
	$('input:checkbox[name=tags]:checked').each(function() 
    {
		tags.push($(this).val());
     });
	
	 var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#projectsDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 window.location.href = sourceleadUrl + "/getExportProjects?&searchValue="+searchValue+"&values="+values+"&clients="+clients+"&projectType="+projectType+"&exportType="+exportType+"&projectManagers="+projectManagers+"&invoiceFrequency="+invoiceFrequency+"&tags="+tags;
	
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


<jsp:include page="../masterFooter.jsp" />	