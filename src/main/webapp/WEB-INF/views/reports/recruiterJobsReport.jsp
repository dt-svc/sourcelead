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

<style>
#billing_chartdiv {
  	width:100%; height:300px;
}
</style>
<script type="text/javascript">
	window.onload = function () {
		jobsList();
	 };
</script>

 <script>
function getDayWiseReports(){
	var timePeriod = $('#reportTypeId').val(); 
	if(timePeriod != "custom"){
		jobsList(timePeriod);
	}
}

</script>


 
 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>${recruiterUser.fullName}</h2>
      </div>
       <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/performanceReportList'/>">Performance Reports</a> &raquo; <a href="<c:url value='${appPath}/recruitersReports'/>">Recruiter(s) Report</a></div>
    </section>
    
    <div class="clearfix vendor_wrap">
    
    <div class="reportHead">
    	<ul class="clearfix pull-right">
    	<input hidden  value="${recruiterUser.id}" id="recruiterUserId" name="">
    		<li>
        <div class="dropdown-select positionRelative repSelWrap clearfix">
        	<select class="form-control repSelFiled" id="reportTypeId" onChange="flushCustom(); custDates();">
			  <option value="Last_7_days">Last 7 days</option>
			  <option value="Last_15_days">Last 15 days</option>
			  <option value="1_month">Last 1 month</option>
			  <option value="3_months">Last 3 months</option>
			  <option value="YTD">YTD</option>
	          <option value="Last_Year">Last Year</option>
			  <option value="custom">Custom</option> 
			</select>
		<div id="runButtonDiv" class="repSelBtn"><button type="button" class="btn btn-primary btn-sm" id="customize_btn" onclick="jobsDataTableList();">Run</button></div> 
          
          <div class="clearfix customLeaves repCustomDate" id="customSearch">
              <div class="repCustomDateInn">
                <input type="text" class="form-control calendar" value="" id="ReportStartDate" name="StartDate" field-name="Start Date" data-validation="required" readonly="readonly" placeholder="Start Date *">
				  <p id="reportsStartDate"></p>
              </div>
              
              <div class="repCustomDateInn">
               <input type="text"  class="form-control calendar" value="" id="ReportEndDate" name="EndDate" field-name="End Date" data-validation="required" readonly="readonly" placeholder="End Date *">
				 <p id="reportsEndDate"></p>
              </div>
              
              <div class="repCustomDateBtn"><button type="button" class="btn btn-primary btn-sm" onclick="jobsDataTableList();"><i class="fa fa-search"></i></button></div>
          </div>
          
        </div>    		
    		</li>
    		<li><a href="javascript:void(0)" class="btn btn-default btn-sm repFilIcon" title="Filters" onclick="openFilterPop();"><i class="fa fa-filter faFilter"></i></a></li>
    		<li class="positionRelative"><input type="text" class="repSearchBar" placeholder='Search' id='usersDataTableSearch'/><span class="repSearchIcon" onClick="jobsDataTableList();"></span></li>
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
										onclick="closeFilterPop();jobsList();" class="btn btn-sm btn-success" />
									<input type="button" value="Reset" id="clear_search"
										onclick="resetFilter();" class="btn btn-sm btn-primary" />
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
 function jobsList(){
	    var timePeriod = $('#reportTypeId').val();
	    var sourceleadUrl ='${sourceleadUrl}';
	    var result = "";
	 	
	 	result = result + '<table id="usersListTable" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +"<th><div class='small'>Generated ID</div></th>";
        result = result +"<th><div class='small'>Job Title</div></th>";
        result = result +"<th><div class='small'>Client</div></th>";
        result = result +"<th><div class='small'>Location</div></th>";
        result = result +"<th><div class='small'>Applicants</div></th>";
        result = result +"<th><div class='small'>Shortlist</div></th>"; 
        result = result +"<th><div class='small'>Submit to Client</div></th>"; 
        result = result +"<th><div class='small'>Interview</div></th>"; 
        result = result +"<th><div class='small'>Offer</div></th>"; 
        result = result +"<th><div class='small'>Rejected</div></th>";
        result = result +"<th><div class='small'>Joined</div></th>";
        result = result +"<th><div class='small'>Back Out</div></th>";
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    $("#noRecruiters").val('');
	    $("#usersList").empty();
        $("#usersList").append(result); 
	    
	    jobsDataTableList();
 }
 </script>

<script>
function jobsDataTableList(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var recruiterUserId = $('#recruiterUserId').val();
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
	 if(flag){
		 searchValue = searchValue ? searchValue : "";
		 var url = sourceleadUrl+"/getAllJobsByUserIdAndOrgLocationId?searchValue="+searchValue+"&timePeriod="+timePeriod+"&customStartDate="+startDate+"&customEndDate="+endDate+"&orgTeams="+orgTeams+"&recruiterUserId="+recruiterUserId;
		 jobsListPagination(url,sourceleadUrl);
	 }
	
}
</script>


<script>
function jobsListPagination(url,sourceleadUrl){
	
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
            { "data": "generatedId" },
            { "data": "jobTitle"},
            { "data": "client"},
            { "data": "location"},
            { "data": "applicants" },
            { "data": "shortlist" },
            { "data": "submitToClient" },
            { "data": "interview"},
            { "data": "offer"},
            { "data": "rejected"},
            { "data": "joined"},
            { "data": "backout"}
        ],
        "columnDefs": [ {
    	  	"targets": colIndex++,
    	  	"data": "generatedId",
    	  	"render": function(data, type, opt, meta) {
    	  		  var result = "";
    	  	   	 result = result +"<td>";
            	 result = result +"<div class='width300px'>";
            	 result = result +"<div class='tbldata-dotdot-me'><span title='"+opt.generatedId+"'>"+opt.generatedId+"</span></div>";
            	 result = result +"<div>";
            	 result = result +"</td>";
	             return result;
    	  	}
      },{
    	  	"targets": colIndex++,
    	  	"data": "jobTitle",
    	  	"render": function(data, type, opt, meta) {
    	  		  var result = "";
    	  	   	 result = result +"<td>";
            	 result = result +"<div class='width300px'>";
            	 result = result +"<div class='tbldata-dotdot-me'><span title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span></div>";
            	 result = result +"<div>";
            	 result = result +"</td>";
	             return result;
    	  	}
      },{
	    	  	"targets": colIndex++,
	    	  	"data": "client",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  	   	 result = result +"<td>";
                	 result = result +"<div class='width300px'>";
                	 
                	 if(opt.client !=null && opt.client !='' && opt.client !='undefined'){
            	    	 result = result +"<div class='tbldata-dotdot-me'> <span title='"+opt.client+"'>"+opt.client+"</span></div>";
            	    }else{
            	    	 result = result +"<div class='tbldata-dotdot-me'> <span>---</span></div>"; 
            	    }
                	 result = result +"<div>";
                	 result = result +"</td>";
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "location",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
	    	  	   	 result = result +"<td>";
             	 result = result +"<div class='width300px'>";
             	 result = result +"<div class='tbldata-dotdot-me'><span title='"+opt.locationsList+"'>"+opt.locationsList+"</span></a></div>";
             	 result = result +"<div>";
             	 result = result +"</td>";
		             return result;
              		
              }
	      
	          },{
		    	  "targets": colIndex++,
	              "data": "applicants",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.noOfApplicants !=null && opt.noOfApplicants !='' && opt.noOfApplicants !='undefined'){
	            	    	 result = result +"<td><div class='small'> <span title='"+opt.noOfApplicants+"'>"+opt.noOfApplicants+"</span></div></td>";
	            	    }else{
	            	    	 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	            	    }
	            	    
	            		 return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "shortlist",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.noOfShortlistApplicants !=null && opt.noOfShortlistApplicants !='' && opt.noOfShortlistApplicants !='undefined'){
	            	    	result = result +"<td><div class='small'> <span title='"+opt.noOfShortlistApplicants+"'>"+opt.noOfShortlistApplicants+"</span></div></td>";
	            	    }else{
	            	    	result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	            	    }
	            	    
	            		return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "submitToClient",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.noOfSubmittedApplicants !=null && opt.noOfSubmittedApplicants !='' && opt.noOfSubmittedApplicants !='undefined'){
	            	    	 result = result +"<td><div class='small'> <span title='"+opt.noOfSubmittedApplicants+"'>"+opt.noOfSubmittedApplicants+"</span></div></td>";
	            	    }else{
	            	    	result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	            	    }
	            	   
	            	    return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "interview",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.noOfInterviewedApplicants !=null && opt.noOfInterviewedApplicants !='' && opt.noOfInterviewedApplicants !='undefined'){
	            	    	result = result +"<td><div class='small'> <span title='"+opt.noOfInterviewedApplicants+"'>"+opt.noOfInterviewedApplicants+"</span></div></td>";
	            	    }else{
	            	    	result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	            	    }
	            	    
	            	    return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "offer",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.noOfOfferedApplicants !=null && opt.noOfOfferedApplicants !='' && opt.noOfOfferedApplicants !='undefined'){
	            	    	 result = result +"<td><div class='small'> <span title='"+opt.noOfOfferedApplicants+"'>"+opt.noOfOfferedApplicants+"</span></div></td>";
	            	    }else{
	            	    	result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	            	    }
	            	   
	            	    return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "rejected",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.noOfRejectedApplicants !=null && opt.noOfRejectedApplicants !='' && opt.noOfRejectedApplicants !='undefined'){
	            	    	result = result +"<td><div class='small'> <span title='"+opt.noOfRejectedApplicants+"'>"+opt.noOfRejectedApplicants+"</span></div></td>";
	            	    }else{
	            	    	result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	            	    }
	            	    
	            	    return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "joined",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.noOfJoinedApplicants !=null && opt.noOfJoinedApplicants !='' && opt.noOfJoinedApplicants !='undefined'){
	            	    	result = result +"<td><div class='small'> <span title='"+opt.noOfJoinedApplicants+"'>"+opt.noOfJoinedApplicants+"</span></div></td>";
	            	    }else{
	            	    	result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	            	    }
	            	    
	            	    return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "backout",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.noOfBackoutApplicants !=null && opt.noOfBackoutApplicants !='' && opt.noOfBackoutApplicants !='undefined'){
	            	    	 result = result +"<td><div class='small'> <span title='"+opt.noOfBackoutApplicants+"'>"+opt.noOfBackoutApplicants+"</span></div></td>";
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
   	    		jobsDataTableList();
   	    	}else{
   	    		$("#usersDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
   	    	}
   	    	$('#usersDataTableSearch').val(searchData);
    	   }
    	});
}
</script>

<script>
function reportpagination(){ 
    $('#reportPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script> 


<jsp:include page="../masterFooter.jsp" />