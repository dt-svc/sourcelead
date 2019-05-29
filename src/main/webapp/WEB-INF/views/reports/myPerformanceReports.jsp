<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />


<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link href="/resources/css/jquery-tagit.css" rel="stylesheet">
<script src="/resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<script src="/resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="/resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>
<!-- end files for autocomplete with tagit -->


<script type="text/javascript">
	window.onload = function () {
		usersList();
	 };
</script>

 <script>
function getDayWiseReports(){
	var timePeriod = $('#reportTypeId').val(); 
	if(timePeriod != "custom"){
		usersList(timePeriod);
	}
}

</script>

<div id="reportsResultDiv">
 
 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>My Performance Report</h2>
      </div>
       <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/performanceReportList'/>">Performance Reports</a> &raquo; My Performance Report
  </div>
    </section>
    
    <div class="clearfix vendor_wrap">
    <div class="reportHead">
    	<ul class="clearfix pull-right">
    		<li>
        <div class="dropdown-select positionRelative repSelWrap clearfix">
          <select class="form-control repSelFiled" id="reportTypeId" onChange="flushCustom(); custDates();">
			  <!-- <option value="today">Today</option>
			  <option value="yesterday">Yesterday</option> -->
			  <option value="today">Today</option>
			  <option value="Last_7_days">Last 7 days</option>
			  <option value="Last_15_days">Last 15 days</option>
			  <option value="1_month">Last 1 month</option>
			  <option value="3_months">Last 3 months</option>
			  <option value='YTD' >YTD</option>
	          <option value='Last_Year'>Last Year</option>
			  <option value="custom">Custom</option> 
			</select>
		<div id="runButtonDiv" class="repSelBtn"><button type="button" class="btn btn-primary btn-sm" id="customize_btn" onclick="userDataTableList();">Run</button></div> 
          
          <div class="clearfix customLeaves repCustomDate" id="customSearch">
              <div class="repCustomDateInn">
                <input type="text" class="form-control calendar" value="" id="ReportStartDate" name="StartDate" field-name="Start Date" data-validation="required" readonly="readonly" placeholder="Start Date *">
				  <p id="reportsStartDate"></p>
              </div>
              
              <div class="repCustomDateInn">
               <input type="text"  class="form-control calendar" value="" id="ReportEndDate" name="EndDate" field-name="End Date" data-validation="required" readonly="readonly" placeholder="End Date *">
				 <p id="reportsEndDate"></p>
              </div>
              
              <div class="repCustomDateBtn"><button type="button" class="btn btn-primary btn-sm" onclick="userDataTableList();"><i class="fa fa-search"></i></button></div>
          </div>
          
        </div>    		
    		</li>
    		<!-- <li><a href="javascript:void(0)" class="btn btn-default btn-sm repFilIcon" title="Filters" onclick="openFilterPop();"><i class="fa fa-filter faFilter"></i></a></li>
    		<li class="positionRelative"><input type="text" class="repSearchBar" placeholder='Search' id='usersDataTableSearch'/><span class="repSearchIcon" onClick="userDataTableList();"></span></li> -->
    	</ul>
    </div>
    
    	<div class="clearfix"></div>
    	 	<div class="candidate_wrap_table recruiterReports_table minHeight positionRelative datatable_filter dashboard-widget clearfix">
              <div id="usersList" class="filter_position positionRelative"></div> 
          </div> 
    </div>
  </section>
 
 <!--  Popup  -->
 <div class="advancedsearch-mask"></div>   
 <div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search">
		       <h5 class="headerAdsrch">Filters
							<a href="javascript:void(0)" class="close-div" onClick="closeFilterPop()"><i
								class="fa fa-close"></i></a>
						</h5>
						<div class="addInner">
							<div class="row">
							
							<div class="col-md-3 mb positionRelative">
									<div class="clearfix dynamic_search filterDynamicsearch">
										<input type="text" id="myTeamsInput" onkeyup="myTeamsFunction()" placeholder="Teams" title="Type in a teams">
										<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
									</div>
									 <div class="clearfix filterSearch-ul">
										<ul class="list-unstyled positionRelative filterSearch mt5" id="myTeamsUL">
											<c:forEach var="team" items="${teamsList}">
											<li class="checkBox positionRelative"><input  type="checkbox" name="orgTeams" value ="${team.id}" ><label>${team.name}</label></li>
											</c:forEach>
										</ul>
									 </div> 
								</div>
							</div>
							</div>
							<div class="clearfix advancedsearch-footer">
								<div class="pull-right mr">
									<input type="button" value="Apply" id="search_requiremet"
										onclick="closeFilterPop();usersList();" class="btn btn-sm btn-success" />
									<input type="button" value="Reset" id="clear_search"
										onclick="resetFilter();" class="btn btn-sm btn-primary" />
								</div>
							</div>
		       </div>
 
 </div>
 <script type="text/javascript"> 
 
 function resetFilter() {
	 $("ul#myTeamsUL > li input:checkbox").prop("checked",false);
}
 
 function myTeamsFunction() {
	    var input, filter, ul, li, a, i;
	    input = document.getElementById("myTeamsInput");
	    filter = input.value.toUpperCase();
	    ul = document.getElementById("myTeamsUL");
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
 function openFilterPop(){
		$("div.cand-advsrch-popup").toggle();
		 $(".advancedsearch-mask").fadeIn();
	}
 function closeFilterPop(){
		$("div.cand-advsrch-popup").hide();
		$(".advancedsearch-mask").fadeOut();
	}
	function addValidClass(){
		 var StartDate = document.getElementById("ReportStartDate").value;
		 if(StartDate){
			 document.getElementById("reportsStartDate").style.display="block";
		 }
		 
		 var EndDate = document.getElementById("ReportEndDate").value;
		
		 if(EndDate){
			 document.getElementById("reportsEndDate").style.display="block";
		 }
}
</script>

<script>
	$(function() {
		var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatRepo = getJSDateFormat(locationDate);
		  
		$('#ReportStartDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatRepo,
			onSelect : function(selected) {
				/* var dt = new Date(selected);
				//var dt = toDate("#ReportStartDate");
				dt.setDate(dt.getDate() + 1);
				$("#ReportEndDate").datepicker("option", "minDate", dt); */
				$("#ReportEndDate").datepicker("option", "minDate", selected);
				addValidClass();
			}
		
		});
		
		$('#ReportEndDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatRepo,
			beforeShow: function(){
		           this.value='';
		     },
			onSelect : function(selected) {
				var dt = new Date(selected);
				/* //var dt = toDate("#ReportEndDate");
				dt.setDate(dt.getDate() - 1);
				$("#ReportStartDate").datepicker("option", "maxDate", dt);
				 $( "#ReportStartDate" ).datepicker({  maxDate: new Date() }); */
				 $("#ReportStartDate").datepicker("option", "maxDate", selected);
				addValidClass();
			}
		});
		
	});
</script>	
 
<script>
function custDates(){
	var reportType = $("#reportTypeId").val();
	if(reportType=="custom"){
		 document.getElementById("customSearch").style.display="block";
		 document.getElementById("runButtonDiv").style.display="none";
	}
	if(reportType != "custom"){
	    document.getElementById("customSearch").style.display="none";
	    document.getElementById("reportsStartDate").style.display="none";
		document.getElementById("reportsEndDate").style.display="none";
		document.getElementById("runButtonDiv").style.display="block";
	}
}
</script>

<script type="text/javascript">
	function flushCustom() {
		$('#ReportStartDate').val('');
		$('#ReportEndDate').val('');
		$.datepicker._clearDate('#ReportStartDate');
		$.datepicker._clearDate('#ReportEndDate'); 
	}
</script>


<script>
 function usersList(){
	 
	    var timePeriod = $('#reportTypeId').val();
		/* $("#reportsStartDate").empty();
		$("#reportsEndDate").empty(); */
	 
	  var sourceleadUrl ='${sourceleadUrl}';
	  
	    var result = "";
	 	
	 	result = result + '<table id="usersListTable" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +"<th><i class='fa fa-address-book-o fa-2x'></i> <div class='small'>Name</div></th>";
        result = result +"<th><i class='fa fa-briefcase fa-2x'></i><div class='small'>Jobs</div></th>";
        result = result +"<th><i class='fa fa-user-circle fa-2x'></i><div class='small'>Candidates</div></th>";
        result = result +"<th><i class='fa fa-users fa-2x'></i><div class='small'>Applicants</div></th>"; 
        result = result +"<th><i class='fa fa-list-ul fa-2x'></i><div class='small'>Shortlisted</div></th>"; 
        result = result +"<th><i class='fa fa-send fa-2x'></i><div class='small'>Submitted</div></th>"; 
        result = result +"<th><i class='fa fa-handshake-o fa-2x'></i><div class='small'>Interviewed</div></th>"; 
        result = result +"<th><i class='fa fa-envelope-open-o fa-2x'></i><div class='small'>Offered</div></th>"; 
        result = result +"<th><i class='fa fa-ban fa-2x'></i><div class='small'>Rejected</div></th>";
        result = result +"<th><i class='fa fa-id-badge'></i><div class='small'>Joined</div></th>";
        result = result +"<th><i class='fa fa-arrow-circle-left fa-2x'></i> <div class='small'>Back-Out</div></th>";
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    $("#noRecruiters").val('');
	    $("#usersList").empty();
        $("#usersList").append(result); 
	    
	    userDataTableList();
 }
 </script>

<script>
function userDataTableList(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#usersDataTableSearch").val();
	 var timePeriod = $('#reportTypeId').val();
	 var flag = true;
	 var startDate = '';
	 var endDate = '';
	 var type="myPerformanceReports"
	 if(timePeriod == 'custom'){
		  startDate = $("#ReportStartDate").val();
		  endDate = $("#ReportEndDate").val();
		  if(startDate == null || startDate == '' || endDate == null || endDate == ''){
			  if(startDate == null || startDate == ''){
				  document.getElementById("reportsStartDate").style.display="block";
				  $("#reportsStartDate").empty().append("Start Date is Required") ; 
			  }
			  
			  if(endDate == null || endDate == ''){
				  document.getElementById("reportsEndDate").style.display="block";
				  $("#reportsEndDate").empty().append("End Date is Required") ; 
			  }
			  flag = false;
		  }
	 }else{
		 document.getElementById("reportsStartDate").style.display="none";
		 document.getElementById("reportsEndDate").style.display="none";
	 }
	 var orgTeams = [];
		$('input:checkbox[name=orgTeams]:checked').each(function() 
			    {
			orgTeams.push($(this).val());
	     });
	 if(flag){
		 searchValue = searchValue ? searchValue : "";
		 var url = sourceleadUrl+"/getAllUsersByOrgLocationsId?searchValue="+searchValue+"&timePeriod="+timePeriod+"&customStartDate="+startDate+"&customEndDate="+endDate+"&orgTeams="+orgTeams+"&type="+type;
		 usersListPagination(url,sourceleadUrl);
	 }
	
}
</script>


<script>
function usersListPagination(url,sourceleadUrl){
	
	var colIndex = 0;
    $('#usersListTable').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 1
        }),
        "columns": [
            { "data": "firstName" },
            { "data": "jobs"},
            { "data": "candidates"},
            { "data": "applicants" },
            { "data": "shortlisted" },
            { "data": "Interviewed"},
            { "data": "offered"},
            { "data": "rejected"},
            { "data": "joined"},
            { "data": "backout"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "firstName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  	   	 result = result +"<td>";
                	 result = result +"<div class='width300px'>";
                	 result = result +"<div class='tbldata-dotdot-me'><span title='"+opt.fullName+"'>"+opt.fullName+"</span></a></div>";
                	 result = result +"<div>";
                	 result = result +"</td>";
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "jobs",
              "sortable": false,
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.userRequirementsCount !=null && opt.userRequirementsCount !='' && opt.userRequirementsCount !='undefined'){
            		  result = result +"<td> <a onclick='getRecruiterJobs(\""+opt.userCode+"\")'><div class='small'><span title='"+opt.userRequirementsCount+"'>"+opt.userRequirementsCount+"</span></div> </a> </td>";
            	  }else{
         	    	 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
          	      }
            	  
	             return result;
              		
              }
	      
	          },{
		    	  "targets": colIndex++,
	              "data": "candidates",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  if(opt.userCandidatesCount !=null && opt.userCandidatesCount !='' && opt.userCandidatesCount !='undefined'){
	            		  result = result +"<td><div class='small'> <span title='"+opt.userCandidatesCount+"'>"+opt.userCandidatesCount+"</span></div></td>";
	            	  }else{
	          	    	 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	          	      }
	            	  return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "applicants",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.userApplicantsCount !=null && opt.userApplicantsCount !='' && opt.userApplicantsCount !='undefined'){
	            	    	 result = result +"<td><div class='small'> <span title='"+opt.userApplicantsCount+"'>"+opt.userApplicantsCount+"</span></div></td>";
	            	    }else{
	            	    	 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	            	    }
	            	    
	            		 return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "shortlisted",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.userShortlistedApplicantsCount !=null && opt.userShortlistedApplicantsCount !='' && opt.userShortlistedApplicantsCount !='undefined'){
	            	    	result = result +"<td><div class='small'> <span title='"+opt.userShortlistedApplicantsCount+"'>"+opt.userShortlistedApplicantsCount+"</span></div></td>";
	            	    }else{
	            	    	result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	            	    }
	            	    
	            		return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "submitted",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.userSubmittedApplicantsCount !=null && opt.userSubmittedApplicantsCount !='' && opt.userSubmittedApplicantsCount !='undefined'){
	            	    	 result = result +"<td><div class='small'> <span title='"+opt.userSubmittedApplicantsCount+"'>"+opt.userSubmittedApplicantsCount+"</span></div></td>";
	            	    }else{
	            	    	result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	            	    }
	            	   
	            	    return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "Interviewed",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.userInterviewedApplicantsCount !=null && opt.userInterviewedApplicantsCount !='' && opt.userInterviewedApplicantsCount !='undefined'){
	            	    	result = result +"<td><div class='small'> <span title='"+opt.userInterviewedApplicantsCount+"'>"+opt.userInterviewedApplicantsCount+"</span></div></td>";
	            	    }else{
	            	    	result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	            	    }
	            	    
	            	    return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "offered",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.userOfferedApplicantsCount !=null && opt.userOfferedApplicantsCount !='' && opt.userOfferedApplicantsCount !='undefined'){
	            	    	 result = result +"<td><div class='small'> <span title='"+opt.userOfferedApplicantsCount+"'>"+opt.userOfferedApplicantsCount+"</span></div></td>";
	            	    }else{
	            	    	result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	            	    }
	            	   
	            	    return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "rejected",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.userRejectedApplicantsCount !=null && opt.userRejectedApplicantsCount !='' && opt.userRejectedApplicantsCount !='undefined'){
	            	    	result = result +"<td><div class='small'> <span title='"+opt.userRejectedApplicantsCount+"'>"+opt.userRejectedApplicantsCount+"</span></div></td>";
	            	    }else{
	            	    	result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	            	    }
	            	    
	            	    return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "joined",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.userApplicantsJoinedCount !=null && opt.userApplicantsJoinedCount !='' && opt.userApplicantsJoinedCount !='undefined'){
	            	    	result = result +"<td><div class='small'> <span title='"+opt.userApplicantsJoinedCount+"'>"+opt.userApplicantsJoinedCount+"</span></div></td>";
	            	    }else{
	            	    	result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	            	    }
	            	    
	            	    return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "backout",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.userApplicantsBackOutCount !=null && opt.userApplicantsBackOutCount !='' && opt.userApplicantsBackOutCount !='undefined'){
	            	    	 result = result +"<td><div class='small'> <span title='"+opt.userApplicantsBackOutCount+"'>"+opt.userApplicantsBackOutCount+"</span></div></td>";
	            	    }else{
	            	    	result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	            	    }
	            	   
	            	    return result;
	              }
		     }],
		     
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
    } );
    $("#usersListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#usersDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
   		    $('#usersDataTableSearch').parent().find('p.jquery_form_error_message').remove();
   	    	var searchData = $('#usersDataTableSearch').val();
   	    	var regex =  new RegExp(/[\\-]/g);
   	    	if(regex.test(searchData) == false){
   	    		userDataTableList();
   	    	}else{
   	    		$("#usersDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
   	    	}
   	    	$('#usersDataTableSearch').val(searchData);
    	   }
    	});
}
</script>

<script>
function getRecruiterJobs(userCode){
	var searchValue = $("#usersDataTableSearch").val();
	 var timePeriod = $('#reportTypeId').val();
	 var flag = true;
	 var startDate = '';
	 var endDate = '';
	 if(timePeriod == 'custom'){
		  startDate = $("#ReportStartDate").val();
		  endDate = $("#ReportEndDate").val();
		  if(startDate == null || startDate == '' || endDate == null || endDate == ''){
			  if(startDate == null || startDate == ''){
				  document.getElementById("reportsStartDate").style.display="block";
				  $("#reportsStartDate").empty().append("Start Date is Required") ; 
			  }
			  
			  if(endDate == null || endDate == ''){
				  document.getElementById("reportsEndDate").style.display="block";
				  $("#reportsEndDate").empty().append("End Date is Required") ; 
			  }
			  flag = false;
		  }
	 }else{
		 document.getElementById("reportsStartDate").style.display="none";
		 document.getElementById("reportsEndDate").style.display="none";
	 }
	 var orgTeams = [];
		$('input:checkbox[name=orgTeams]:checked').each(function() 
			    {
			orgTeams.push($(this).val());
	     });
	$.ajax({
        type : "POST",
   	 	url: sourceleadUrl+"/recruiterJobsReport?"+tokenParamName+"="+tokenValue+"&userCode="+userCode+"&searchValue="+searchValue+"&timePeriod="+timePeriod+"&customStartDate="+startDate+"&customEndDate="+endDate+"&orgTeams="+orgTeams,
       	success :function(result) {
       		$('#reportsResultDiv').empty();
       		$('#reportsResultDiv').append(result);
       		jobsList();
        }
   	});
}
</script>

<jsp:include page="../masterFooter.jsp" />