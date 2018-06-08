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


<section class="content-wraper">

<section class="content-header clearfix">
			<div class="pull-left">
				<h2>Recruiters Report </h2>
			</div>
          <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; » Recruiters Reports  </div>
</section>

<div class="vendor_wrap">
<div class="timesheet_head clearfix">
<div class="pull-left reportBtns reportBtns_custom mt5">
<h4 class="pull-left"></h4>
		  <div class="reportBtn reportBtn_custom_btn select-field  pull-left ml">
			<select class="form-control" id="reportTypeId" onChange="flushCustom(); getDayWiseReports(); custDates();">
			  <option value="today">Today</option>
			  <option value="yesterday">Yesterday</option>
			  <option value="1_week">1 Week ago</option>
			  <option value="2_weeks">2 Weeks ago</option>
			  <option value="1_month">1 Month ago</option>
			  <option value="2_months">2 Months ago</option>
			  <option value="custom">Custom</option> 
			</select>
		 </div>	
		 <span class="dashboard-datepkr cusShow">
		 <input type="text" class="form-control" value="" id="ReportStartDate" name="StartDate" field-name="Start Date" data-validation="required" readonly="readonly" placeholder="Start Date"> <i class="fa fa-calendar"></i>
		 <p id="reportsStartDate" class="ReError" style="display: none"></p>
		 </span>
		<span class="dashboard-datepkr cusShow">
		<input type="text"  class="form-control" value="" id="ReportEndDate" name="EndDate" field-name="End Date" data-validation="required" readonly="readonly" placeholder="End Date"><i class="fa fa-calendar"></i>
		<p id="reportsEndDate" class="ReError" style="display: none"></p>
		</span>
		<span class="cusShow"><input type="button" value="Submit" class="btn btn-primary repBtn" onclick="userDataTableList();" /></span>
		<div class="clearfix"></div>
  </div>
     </div> 
      
      <div class="candidate_wrap_table recruiterReports_table minHeight positionRelative datatable_filter">
      <div class="positionRelative table_head section-action-btns section-action-btns-associates clearfix">
      <div id="usersListLoader"></div>
      <ul class="pull-right list-unstyled list-inline">
		  <li class="positionRelative">
			  <div class="dynamic_search">
	   			<input type='text' placeholder='Search' id='usersDataTableSearch'/>
	   			<span class="cursorPointer" title="Search"><i class="fa fa-search" onClick="userDataTableList();"></i></span>
			  </div>
		  </li>
        </ul>
      </div>
              <div id="usersList" class="filter_position positionRelative"></div> 
          </div>  
 </div>
 </section>
 
 <script type="text/javascript"> 
	function addValidClass(){
		 var StartDate = document.getElementById("ReportStartDate").value;
		 if(StartDate){
			 $('#reportsStartDate').empty() 
		 }
		 
		 var EndDate = document.getElementById("ReportEndDate").value;
		
		 if(EndDate){
			 $('#reportsEndDate').empty(); 
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
	    $(".cusShow").show();
	}
	if(reportType!="custom"){
	    $(".cusShow").hide();
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
		$("#reportsStartDate").empty();
		$("#reportsEndDate").empty();
		document.getElementById("reportsStartDate").style.display="none";
		document.getElementById("reportsEndDate").style.display="none";
	 
	  var sourceleadUrl ='${sourceleadUrl}';
	  $("#usersListLoader").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  
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
        result = result +"<th><i class='fa fa-arrow-circle-left fa-2x'></i> <div class='small'>Back-Out</div></th>";
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    $("#noRecruiters").val('');
	    $("#usersList").empty();
        $("#usersList").append(result); 
        $("#usersList").removeAttr('style');
        $("#usersListLoader").removeAttr('style');
	    
	    userDataTableList();
 }
 </script>

<script>
function userDataTableList(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#usersDataTableSearch").val();
	 var timePeriod = $('#reportTypeId').val();
	 var startDate = $("#ReportStartDate").val();
	 var endDate = $("#ReportEndDate").val();
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/getAllUsersByOrgLocationsId?searchValue="+searchValue+"&timePeriod="+timePeriod+"&customStartDate="+startDate+"&customEndDate="+endDate;
	 usersListPagination(url,sourceleadUrl);
	 $("#usersListLoader").removeAttr("style");
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
            		  result = result +"<td><div class='small'><span title='"+opt.userRequirementsCount+"'>"+opt.userRequirementsCount+"</span></div></td>";
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
	            	  if(opt.userRequirementsCount !=null && opt.userRequirementsCount !='' && opt.userRequirementsCount !='undefined'){
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
function userDataTableList1(){ 
	var timePeriod = $('#reportTypeId').val();
	$("#reportsStartDate").empty();
	$("#reportsEndDate").empty();
	document.getElementById("reportsStartDate").style.display="none";
	document.getElementById("reportsEndDate").style.display="none";
	$("#usersListLoader").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getAllUsersByOrgLocationsId?"+tokenParamName+"="+tokenValue+"&timePeriod="+timePeriod,    
            success :function(data) {
                 if(data != null && data != 'undefined' && data != "" && data != '[]'){
	                	
                	     $("#usersList").empty();
                	     
	                	 var result = "";
	                	 result = result +"<table id='reportPagination' class='display' cellspacing='0' width='99.9%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"<th><i class='fa fa-address-book-o fa-2x'></i> <div class='small'>Name</div></th>";
	                     result = result +"<th><i class='fa fa-briefcase fa-2x'></i><div class='small'>Jobs</div></th>";
	                     result = result +"<th><i class='fa fa-user-circle fa-2x'></i><div class='small'>Candidates</div></th>";
	                     result = result +"<th><i class='fa fa-users fa-2x'></i><div class='small'>Applicants</div></th>"; 
	                     result = result +"<th><i class='fa fa-list-ul fa-2x'></i><div class='small'>Shortlisted</div></th>"; 
	                     result = result +"<th><i class='fa fa-send fa-2x'></i><div class='small'>Submitted</div></th>"; 
	                     result = result +"<th><i class='fa fa-handshake-o fa-2x'></i><div class='small'>Interviewed</div></th>"; 
	                     result = result +"<th><i class='fa fa-envelope-open-o fa-2x'></i><div class='small'>Offered</div></th>"; 
	                     result = result +"<th><i class='fa fa-ban fa-2x'></i><div class='small'>Rejected</div></th>";
	                     result = result +"<th><i class='fa fa-arrow-circle-left fa-2x'></i> <div class='small'>Back-Out</div></th>";
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     result = result + '<div class="new_side_wrap">';
	                 	 result = result + '<div class="new_side_wrap_inn">';
	                 	 result = result + '<div class="new_side_wrap_cta">';
	                 	 result = result +'<div id="addApplicantDiv"></div>';
	                 	 result = result +'</div>';
	                 	 result = result +'</div>';
	                 	 result = result +'</div>';
	                 	 
	                 	var json = JSON.parse(data);
	                 	
	                 	
	                 	var checkSize = json.length;
	                    
	                     for (var i = 0; i < checkSize; i++) { 
	                    	 var opt = json[i]; 
	                    	 
	                    	 if(opt.lastName != null && opt.lastName !='' && opt.lastName != 'undefined'){
	          			 		lastName = opt.lastName;
		          			 	}else{
		          			 		lastName = "";
		          			 	}
	                    	 result = result +"<tr>";
	                    	 
	                    	 if(opt.firstName !=null && opt.firstName !='' && opt.firstName !='undefined'){
	                    		 result = result +"<td><div class='tbldata-dotdot-me'><span title='"+opt.firstName+"'>"+opt.firstName+" "+lastName+"</span></a></div></td>";
	                    	 }else{
	                    		 result = result +"<td><div> <span>----</span></div></td>"; 
	                    	 }
	                    	 
	                    	 if(opt.userRequirementsCount !=null && opt.userRequirementsCount !='' && opt.userRequirementsCount !='undefined'){
	                    		  result = result +"<td><div class='small'><span title='"+opt.userRequirementsCount+"'>"+opt.userRequirementsCount+"</span></div></td>";
	                    	 }else{
	                    		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	                    	 }
		                    
	                    	 if(opt.userCandidatesCount !=null && opt.userCandidatesCount !='' && opt.userCandidatesCount !='undefined'){
	                    		  result = result +"<td><div class='small'> <span title='"+opt.userCandidatesCount+"'>"+opt.userCandidatesCount+"</span></div></td>";
	                    	 }else{
	                    		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	                    	 }
	                    	 
	                    	 if(opt.userApplicantsCount !=null && opt.userApplicantsCount !='' && opt.userApplicantsCount !='undefined'){
	                    		  result = result +"<td><div class='small'> <span title='"+opt.userApplicantsCount+"'>"+opt.userApplicantsCount+"</span></div></td>";
	                    	 }else{
	                    		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	                    	 }
	                    	 
	                    	 
	                    	 if(opt.userShortlisteApplicantsCount !=null && opt.userShortlisteApplicantsCount !='' && opt.userShortlisteApplicantsCount !='undefined'){
	                    		  result = result +"<td><div class='small'> <span title='"+opt.userShortlisteApplicantsCount+"'>"+opt.userShortlisteApplicantsCount+"</span></div></td>";
	                    	 }else{
	                    		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	                    	 }
	                    	 
	                    	 if(opt.userSubmittedApplicantsCount !=null && opt.userSubmittedApplicantsCount !='' && opt.userSubmittedApplicantsCount !='undefined'){
	                    		  result = result +"<td><div class='small'> <span title='"+opt.userSubmittedApplicantsCount+"'>"+opt.userSubmittedApplicantsCount+"</span></div></td>";
	                    	 }else{
	                    		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	                    	 }
	                    	 
	                    	 if(opt.userInterviewedApplicantsCount !=null && opt.userInterviewedApplicantsCount !='' && opt.userInterviewedApplicantsCount !='undefined'){
	                    		  result = result +"<td><div class='small'> <span title='"+opt.userInterviewedApplicantsCount+"'>"+opt.userInterviewedApplicantsCount+"</span></div></td>";
	                    	 }else{
	                    		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	                    	 }
	                    	 
	                    	 if(opt.userOfferedApplicantsCount !=null && opt.userOfferedApplicantsCount !='' && opt.userOfferedApplicantsCount !='undefined'){
	                    		  result = result +"<td><div class='small'> <span title='"+opt.userOfferedApplicantsCount+"'>"+opt.userOfferedApplicantsCount+"</span></div></td>";
	                    	 }else{
	                    		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	                    	 }
	                    	 
	                    	 if(opt.userRejectedApplicantsCount !=null && opt.userRejectedApplicantsCount !='' && opt.userRejectedApplicantsCount !='undefined'){
	                    		  result = result +"<td><div class='small'> <span title='"+opt.userRejectedApplicantsCount+"'>"+opt.userRejectedApplicantsCount+"</span></div></td>";
	                    	 }else{
	                    		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	                    	 }
	                    	 
	                    	 if(opt.userApplicantsBackOutCount !=null && opt.userApplicantsBackOutCount !='' && opt.userApplicantsBackOutCount !='undefined'){
	                    		  result = result +"<td><div class='small'> <span title='"+opt.userApplicantsBackOutCount+"'>"+opt.userApplicantsBackOutCount+"</span></div></td>";
	                    	 }else{
	                    		 result = result +"<td><div class='small'> <span>0</span></div></td>"; 
	                    	 }
	                    	 
	                    	 
		                    
		                     result = result +"</tr>";
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#noRecruiters").val('');
	                     $("#usersList").append(result); 
	                     $("#usersList").removeAttr('style');
	                     $("#usersListLoader").empty();
	                     $("#usersListLoader").removeAttr('style');
	                     
	                     reportpagination();
			 			 
	                	
                 }
          },
            
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