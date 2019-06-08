
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
		associateCandidateList();
	 };
</script>

 <!-- <script>
function getDayWiseReports(){
	var timePeriod = $('#reportTypeIdJobs').val(); 
	if(timePeriod != "custom"){
		jobsList(timePeriod);
	}
}
</script> -->
 
 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>${associateUser.fullName}</h2>
      </div>
       <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/performanceReportList'/>">Performance Reports</a> &raquo; <a href="<c:url value='${appPath}/recruitersReports'/>">Recruiter(s) Report</a> &raquo; Recruiter(s) Candidates Report</div>
    </section>
    
    <div class="clearfix vendor_wrap">
    
    <div class="reportHead">
    	<ul class="clearfix pull-right">
    		<li>
        <div class="dropdown-select positionRelative repSelWrap clearfix">
        	<select class="form-control repSelFiled" id="reportTypeIdJobs" onChange="flushCustomJobs(); custDatesJobs();">
			  <option ${timePeriod == 'today' ? 'selected' : ''} value="today">Today</option>
			  <option ${timePeriod == 'Last_7_days' ? 'selected' : ''} value="Last_7_days">Last 7 days</option>
			  <option ${timePeriod == 'Last_15_days' ? 'selected' : ''} value="Last_15_days">Last 15 days</option>
			  <option ${timePeriod == '1_month' ? 'selected' : ''} value="1_month">Last 1 month</option>
			  <option ${timePeriod == '3_months' ? 'selected' : ''} value="3_months">Last 3 months</option>
			  <option ${timePeriod == 'YTD' ? 'selected' : ''} value="YTD">YTD</option>
	          <option ${timePeriod == 'Last_Year' ? 'selected' : ''} value="Last_Year">Last Year</option>
			  <option ${timePeriod == 'custom' ? 'selected' : ''} value="custom">Custom</option> 
			</select>
		<div id="runButtonDivJobs" class="repSelBtn"><button type="button" class="btn btn-primary btn-sm" id="customize_btn" onclick="associateCandidateList();">Run</button></div> 
          
          <div class="clearfix customLeaves repCustomDate" id="customSearchCandidates">
              <div class="repCustomDateInn">
                <input type="text" class="form-control calendar" value="${customStartDate}" id="ReportStartDateJobs" name="StartDate" field-name="Start Date" data-validation="required" readonly="readonly" placeholder="Start Date *">
				  <p id="reportsStartDateJobs"></p>
              </div>
              
              <div class="repCustomDateInn">
               <input type="text"  class="form-control calendar" value="${customEndDate}" id="ReportEndDateJobs" name="EndDate" field-name="End Date" data-validation="required" readonly="readonly" placeholder="End Date *">
				 <p id="reportsEndDateJobs"></p>
              </div>
              
              <div class="repCustomDateBtn"><button type="button" class="btn btn-primary btn-sm" onclick="associateCandidateList();"><i class="fa fa-search"></i></button></div>
          </div>
          
        </div>    		
    		</li>
    		<li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="moreActionsCandidate();"></a>
					<div class="newActPop">
						<span>
							<a href="javascript:void(0)" onclick="canidateExportForRecruiters();" title="PDF">Export PDF</a>
						</span>
					</div>
				</li>
    		<li><a href="javascript:void(0)" class="btn btn-default btn-sm repFilIcon" title="Filters" onclick="openFilterPopJobs();"><i class="fa fa-filter faFilter"></i></a></li>
    		<li class="positionRelative"><input type="text" class="repSearchBar" placeholder='Search' id='jobsDataTableSearch'/><span class="repSearchIcon" onClick="associateCandidateList();"></span></li>
    	</ul>
    </div>
    
    	<div class="clearfix"></div>
    	 	<div class="candidate_wrap_table recruiterReports_table fixedColumntable minHeight positionRelative datatable_filter dashboard-widget clearfix">
              <div id="usersCandidateList" class="filter_position positionRelative"></div> 
          </div> 
    </div>
  </section>
 
 <!--  Popup  -->
 <div class="advancedsearch-mask"></div>   
 <div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search">
		       <h5 class="headerAdsrch">Filters
							<a href="javascript:void(0)" class="close-div" onClick="closeFilterPopJobs()"><i
								class="fa fa-close"></i></a>
						</h5>
						<div class="addInner">
							<div class="row">
							
							<div class="col-md-3 mb positionRelative">
									<div class="clearfix dynamic_search filterDynamicsearch">
										<input type="text" id="myTeamsInputJobs" onkeyup="myTeamsFunctionJobs()" placeholder="Teams" title="Type in a teams">
										<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
									</div>
									 <div class="clearfix filterSearch-ul">
										<ul class="list-unstyled positionRelative filterSearch mt5" id="myTeamsULJobs">
											<c:set var="teamsIds" value="${fn:split(orgTeams, ',')}" />
											<c:set var="checked" value="" />
											<c:forEach var="team" items="${teamsList}">
												<c:forEach var="teamArr" items="${teamsIds}">
													<c:if test="${teamArr eq team.id}">
													    <c:set var="checked" value="checked" />
													</c:if>
												</c:forEach>
											<li class="checkBox positionRelative"><input  type="checkbox" ${checked} name="orgTeams" value ="${team.id}" ><label>${team.name}</label></li>
											</c:forEach>
										</ul>
									 </div> 
								</div>
							</div>
							</div>
							<div class="clearfix advancedsearch-footer">
								<div class="pull-right mr">
									<input type="button" value="Apply" id="search_requiremet"
										onclick="closeFilterPopJobs();associateCandidateList();" class="btn btn-sm btn-success" />
									<input type="button" value="Reset" id="clear_search"
										onclick="resetFilterJobs();" class="btn btn-sm btn-primary" />
								</div>
							</div>
		       </div>
 
 
 <script type="text/javascript"> 
 
 function moreActionsCandidate(){
		$(".newActPop").slideToggle(200);
		$(".action-pop").slideUp(200);
	}
 
 function resetFilterJobs() {
	 $("ul#myTeamsULJobs > li input:checkbox").prop("checked",false);
}
 
 function myTeamsFunctionJobs() {
	    var input, filter, ul, li, a, i;
	    input = document.getElementById("myTeamsInputJobs");
	    filter = input.value.toUpperCase();
	    ul = document.getElementById("myTeamsULJobs");
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
 function openFilterPopJobs(){
		$("div.cand-advsrch-popup").toggle();
		 $(".advancedsearch-mask").fadeIn();
	}
 function closeFilterPopJobs(){
		$("div.cand-advsrch-popup").hide();
		$(".advancedsearch-mask").fadeOut();
	}
	function addValidClassJobs(){
		 var StartDate = document.getElementById("ReportStartDateJobs").value;
		 if(StartDate){
			 document.getElementById("reportsStartDateJobs").style.display="block";
		 }
		 
		 var EndDate = document.getElementById("ReportEndDateJobs").value;
		
		 if(EndDate){
			 document.getElementById("reportsEndDateJobs").style.display="block";
		 }
}
</script>

<script>
	$(function() {
		var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatRepo = getJSDateFormat(locationDate);
		  
		$('#ReportStartDateJobs').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatRepo,
			onSelect : function(selected) {
				$("#ReportEndDateJobs").datepicker("option", "minDate", selected);
				addValidClassJobs();
			}
		
		});
		
		$('#ReportEndDateJobs').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatRepo,
			beforeShow: function(){
		           this.value='';
		     },
			onSelect : function(selected) {
				var dt = new Date(selected);
				 $("#ReportStartDateJobs").datepicker("option", "maxDate", selected);
				addValidClassJobs();
			}
		});
		
	});
</script>	
 
<script>
function custDatesJobs(){
	var reportType = $("#reportTypeIdJobs").val();
	if(reportType=="custom"){
		 document.getElementById("customSearchCandidates").style.display="block";
		 document.getElementById("runButtonDivJobs").style.display="none";
	}
	if(reportType != "custom"){
	    document.getElementById("customSearchCandidates").style.display="none";
	    document.getElementById("reportsStartDateJobs").style.display="none";
		document.getElementById("reportsEndDateJobs").style.display="none";
		document.getElementById("runButtonDivJobs").style.display="block";
	}
}
</script>

<script type="text/javascript">
	function flushCustomJobs() {
		$('#ReportStartDateJobs').val('');
		$('#ReportEndDateJobs').val('');
		$.datepicker._clearDate('#ReportStartDateJobs');
		$.datepicker._clearDate('#ReportEndDateJobs'); 
	}
</script>


<script>
 function associateCandidateList(){
	    var timePeriod = $('#reportTypeIdJobs').val();
	    var sourceleadUrl ='${sourceleadUrl}';
	    var result = "";
	 	
	 	result = result + '<table id="usersListTable" class="display stripe row-border order-column" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +"<th><div class='small'>Candidate Name</div></th>";
        result = result +"<th><div class='small'>Email Id</div></th>";
        result = result +"<th><div class='small'>Phone Number</div></th>";
        result = result +"<th><div class='small'>Emoloyeement Type</div></th>";
        result = result +"<th><div class='small'>Location</div></th>";
        result = result +"<th><div class='small'>Rate</div></th>"; 
        result = result +"<th><div class='small'>Experience</div></th>"; 
        result = result +"<th><div class='small'>No of Jobs</div></th>"; 
        result = result +"<th><div class='small'>Resume</div></th>"; 
        result = result +"<th><div class='small'>Created On</div></th>"; 
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    $("#noRecruiters").val('');
	    $("#usersCandidateList").empty();
        $("#usersCandidateList").append(result); 
	    
        associateCandidateDataTableList();
 }
 </script>

<script>
function associateCandidateDataTableList(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var userCode = '${userCode}';
	 var searchValue = $("#jobsDataTableSearch").val();
	 var timePeriod = $('#reportTypeIdJobs').val();
	 var flag = true;
	 var startDate = '';
	 var endDate = '';
	 if(timePeriod == 'custom'){
		 custDatesJobs();
		  startDate = $("#ReportStartDateJobs").val();
		  endDate = $("#ReportEndDateJobs").val();
		  if(startDate == null || startDate == '' || endDate == null || endDate == ''){
			  if(startDate == null || startDate == ''){
				  document.getElementById("reportsStartDateJobs").style.display="block";
				  $("#reportsStartDateJobs").empty().append("Start Date is Required") ; 
			  }
			  
			  if(endDate == null || endDate == ''){
				  document.getElementById("reportsEndDateJobs").style.display="block";
				  $("#reportsEndDateJobs").empty().append("End Date is Required") ; 
			  }
			  flag = false;
		  }
	 }else{
		 document.getElementById("reportsStartDateJobs").style.display="none";
		 document.getElementById("reportsEndDateJobs").style.display="none";
	 }
	 var orgTeams = [];
		$('input:checkbox[name=orgTeams]:checked').each(function() 
			    {
			orgTeams.push($(this).val());
	     });
	 if(flag){
		 searchValue = searchValue ? searchValue : "";
		 var url = sourceleadUrl+"/getAllCandidateListForRecruiterReports?searchValue="+searchValue+"&timePeriod="+timePeriod+"&customStartDate="+startDate+"&customEndDate="+endDate+"&orgTeams="+orgTeams+"&userCode="+userCode;
		 associateCandidateListPagination(url,sourceleadUrl);
		 if(timePeriod == 'custom'){
		 $("#customSearchCandidates").hide();
		 }
	 }
	
}
</script>


<script>
function canidateExportForRecruiters(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var userCode = '${userCode}';
	 var searchValue = $("#jobsDataTableSearch").val();
	 var timePeriod = $('#reportTypeIdJobs').val();
	 var flag = true;
	 var startDate = '';
	 var endDate = '';
	 if(timePeriod == 'custom'){
		 custDatesJobs();
		  startDate = $("#ReportStartDateJobs").val();
		  endDate = $("#ReportEndDateJobs").val();
		  if(startDate == null || startDate == '' || endDate == null || endDate == ''){
			  if(startDate == null || startDate == ''){
				  document.getElementById("reportsStartDateJobs").style.display="block";
				  $("#reportsStartDateJobs").empty().append("Start Date is Required") ; 
			  }
			  
			  if(endDate == null || endDate == ''){
				  document.getElementById("reportsEndDateJobs").style.display="block";
				  $("#reportsEndDateJobs").empty().append("End Date is Required") ; 
			  }
			  flag = false;
		  }
	 }else{
		 document.getElementById("reportsStartDateJobs").style.display="none";
		 document.getElementById("reportsEndDateJobs").style.display="none";
	 }
	 var orgTeams = [];
		$('input:checkbox[name=orgTeams]:checked').each(function() 
			    {
			orgTeams.push($(this).val());
	     });
	 if(flag){
		 searchValue = searchValue ? searchValue : "";
		 var url = sourceleadUrl+"/getAllCandidateListForRecruiterReportsExport?searchValue="+searchValue+"&timePeriod="+timePeriod+"&customStartDate="+startDate+"&customEndDate="+endDate+"&orgTeams="+orgTeams+"&userCode="+userCode;
		 window.location.href = url;
	 }
	
}
</script>


<script>
function associateCandidateListPagination(url,sourceleadUrl){
	
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
            { "data": "candidateName" },
            { "data": "email"},
            { "data": "phoneNumber"},
            { "data": "empType"},
            { "data": "location" },
            { "data": "rate" },
            { "data": "experience" },
            { "data": "noOfJobs"},
            { "data": "resume"},
            { "data": "cretedOn"}
        ],
        "columnDefs": [ {
    	  	"targets": colIndex++,
    	  	"data": "candidateName",
    	  	"render": function(data, type, opt, meta) {
    	  		  var result = "";
    	  	   	 result = result +"<td>";
            	 result = result +"<div class='tblminwidth-100' title='"+opt.firstName+"'>"+opt.firstName+"</div>";
            	 result = result +"</td>";
	             return result;
    	  	}
      },{
    	  	"targets": colIndex++,
    	  	"data": "email",
    	  	"render": function(data, type, opt, meta) {
    	  		  var result = "";
    	  	   	 result = result +"<td>";
            	 result = result +"<div class='tbldata-dotdot-me' title='"+opt.email+"'>"+opt.email+"</div>";
            	 result = result +"</td>";
	             return result;
    	  	}
      },{
	    	  	"targets": colIndex++,
	    	  	"data": "phoneNumber",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  	   	 result = result +"<td>";
	    	  	   	 if(opt.mobilePhone !=null && opt.mobilePhone !="" && opt.mobilePhone !="undefined"){
	    	  	   	result = result +"<div class='tbldata-dotdot-me' title='"+opt.mobilePhone+"'>"+opt.mobilePhone+"</div>"; 
	    	  	   	 }else{
	    	  	   	result = result +"<div class='tbldata-dotdot'> <span>---</span></div>"; 	 
	    	  	   	 }
                	 
                	 result = result +"</td>";
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "empType",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
	    	  	   	 result = result +"<td>";
	    	  	   if(opt.designationType !=null && opt.designationType !="" && opt.designationType !="undefined"){
	    	  	   	result = result +"<div class='tbldata-dotdot-me' title='"+opt.designationType+"'>"+opt.designationType+"</div>"; 
	    	  	   	 }else{
	    	  	   	result = result +"<div class='tbldata-dotdot'> <span>---</span></div>"; 	 
	    	  	   	 }
             	 result = result +"</td>";
		             return result;
              		
              }
	      
	          },{
		    	  "targets": colIndex++,
	              "data": "location",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    
	            		 result = result +"<td>";
	  	    	  	   if(opt.location !=null && opt.location !="" && opt.location !="undefined"){
	  	    	  	   	result = result +"<div class='tbldata-dotdot-me' title='"+opt.location+"'>"+opt.location+"</div>"; 
	  	    	  	   	 }else{
	  	    	  	   	result = result +"<div class='tbldata-dotdot'> <span>---</span></div>"; 	 
	  	    	  	   	 }
	               	 result = result +"</td>";
	            	    
	            		 return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "rate",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    
	            	    result = result +"<td>";
	  	    	  	   if(opt.currentRate !=null && opt.currentRate !="" && opt.currentRate !="undefined"){
	  	    	  	   	result = result +"<div class='tbldata-dotdot-me' title='"+opt.currentRate+"'>"+opt.currentRate+"</div>"; 
	  	    	  	   	 }else{
	  	    	  	   	result = result +"<div class='tbldata-dotdot'> <span>---</span></div>"; 	 
	  	    	  	   	 }
		               	 result = result +"</td>";
	            	    
	            		return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "experience",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    result = result +"<td>";
	  	    	  	   if(opt.experienceYears !=null && opt.experienceYears !="" && opt.experienceYears !="undefined"){
	  	    	  	   	result = result +"<div class='tbldata-dotdot-me' title='"+opt.experienceYears+"'>"+opt.experienceYears+" Years</div>"; 
	  	    	  	   	 }else{
	  	    	  	   	result = result +"<div class='tbldata-dotdot'> <span>---</span></div>"; 	 
	  	    	  	   	 }
		               	 result = result +"</td>";
	            	    return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "noOfJobs",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    result = result +"<td>";
	            	    if(opt.candidateAssignedJobsCount != null && opt.candidateAssignedJobsCount !='' && opt.candidateAssignedJobsCount !='undefined'){
	            	    	result = result +"<div class='tbldata-dotdot-me'> <span>"+opt.candidateAssignedJobsCount+"</span></div>"; 
	            	    }else{
	            	    	result = result +"<div class='tbldata-dotdot-me'> <span>0</span></div>"; 
	            	    }
	            	    result = result +"</td>";
	            	    return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "resume",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.candidateResume != null && opt.candidateResume !='' && opt.candidateResume !='undefined'){
	            	    result = result +"<td><div class='tbldata-dotdot-me'> <a download class='glyphicon glyphicon-download-alt'  href=\'"+opt.candidateResume+"\' download title='Download'></a></div></td>"; 
	            	    }else{
	            	    result = result +"<td><div class='tbldata-dotdot'> <span>---</span></div></td>"; 	
	            	    }
	            	    return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "createdOn",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	   
	            	    	result = result +"<td><div class='tbldata-dotdot-me' title='"+opt.createdDateStr+"'>"+opt.createdDateStr+"</div></td>"; 
	            	   
	            	    return result;
	              }
		     }],
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	         "scrollX":        true,
	         "scrollCollapse": true,
	    	  "fixedColumns":  {
	            "leftColumns": 2,
	            "rightColumns":0
	        },
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    //$("#usersListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#jobsDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
   		    $('#jobsDataTableSearch').parent().find('p.jquery_form_error_message').remove();
   	    	var searchData = $('#jobsDataTableSearch').val();
   	    	var regex =  new RegExp(/[\\-]/g);
   	    	if(regex.test(searchData) == false){
   	    		associateCandidateList();
   	    	}else{
   	    		$("#jobsDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
   	    	}
   	    	$('#jobsDataTableSearch').val(searchData);
    	   }
    	});
}
</script>


