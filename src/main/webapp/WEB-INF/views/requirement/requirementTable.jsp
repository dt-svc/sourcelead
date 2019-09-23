<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
<script>
function jobFairListCheck(){
	viewTab = "jobFair";
	$("#filtersDiv").addClass("hide_filters");
	$("#jobsTab").removeClass("current");
    $("#jobFairTab").addClass("current");
	
	$("#jobsTabContent").hide();
	$("#jobFairTabContent").show();
	$("#dataTableSearch").val('');
	
	$("#jobFairList").empty();
	$("#jobFairList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  var canEduDatePattern = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  var canEduDatePattern = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  var canEduDatePattern = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  var canEduDatePattern = 'YYYY-DD-MM'; 
	  }
	var orgATS = '${SourceLeadWeb_ats}';
	var orgVMS = '${SourceLeadWeb_vms}';
	 $("#jobFairList").removeAttr("style");
	 $("#jobFairList").empty();
	var result = "";
	result = result +"<table id='jobFairPagination' class='display' cellspacing='0' width='100%' >"; 
 	result = result +"<thead>"; 
 	result = result +"	<tr>";
 	result = result +"		<th>Generated ID</th>";
 	result = result +"		<th>Title</th>";
 	result = result +"		<th>Start Date</th>";
 	result = result +"		<th>End Date</th>";
 	result = result +"		<th>No.of Jobs</th>";
 	result = result +"		<th>Applicants</th>";
 	result = result +"		<th>Offered</th>";
 	result = result +"		<th>Venue</th>"; 
 	result = result +"		<th>Status</th>";
 	result = result +"		<th>Action(s)</th>";
	result = result +"	</tr>"; 
 	result = result +"</thead>";
 	result = result +"<tbody>"; 
     
  	result = result + '<div class="new_side_wrap">';
	result = result + '<div class="new_side_wrap_inn">';
	result = result + '<div class="new_side_wrap_cta">';
	result = result +'<div id="addApplicantDiv"></div>';
	result = result +'</div>';
	result = result +'</div>';
	result = result +'</div>';
 	result = result +" </tbody>";
 	result = result +"</table>"; 
 	$("#noReq").val('');
 	$("#jobFairList").append(result); 
 	var url = sourceleadUrl+"/searchJobFairs?includeInactive=true&searchValue="+$("#jobFairDataTableSearch").val();
 	jpaginationForJobFairs(url); 
 
	 /* to call requirement conformation saving message */
 	msgFadein1();
}
function requirementListCheck(searchValue,universalRequirementSearchList,searchType,sourceleadUrl){
	$("#jobFairDataTableSearch").val('');
	viewTab = "jobs";
	$("#filtersDiv").removeClass("hide_filters");
	if(universalRequirementSearchList != null && universalRequirementSearchList != '' && universalRequirementSearchList != 'undefined' 
		 && universalRequirementSearchList=="universalRequirementList")	 {
		 $("#dataTableSearch").val(searchValue);
		 $("#searchKey").val(searchValue);
		 if(searchType == null || searchType == '' || searchType == 'undefined'){
			    $("#c6").prop("checked", true);
			  	$("#c8").prop("checked", true);
			 	$("#c9").prop("checked", true);
		 }
		 requirementList(searchType,sourceleadUrl);
	  }
	  else if(searchType != null && searchType != '' && searchType != 'undefined'
		   && searchType=="advanceSearch")	 {
		   $("#c6").prop("checked", false);
		   $("#c8").prop("checked", false);
		   $("#c9").prop("checked", false);
		   requirementList(searchType,sourceleadUrl);
	 }
	 else{
		  	$("#c6").prop("checked", true);
		  	$("#c8").prop("checked", true);
		 	$("#c9").prop("checked", true);
		 	requirementList('list',sourceleadUrl);
	} 
}
function requirementList(searchType,sourceleadUrl){  
	    var url = '';
	    if(searchType == 'list' || searchType == 'universalSearch'){
	    	var filterStatus = [];
			$('input:checkbox[name=requirementStatus]:checked').each(function() 
		    {
				filterStatus.push($(this).val());
		    });
		    var filterClients = [];
			$('input:checkbox[name=clientNameById]:checked').each(function() 
		    {
				filterClients.push($(this).val());
		    });
			
			var filterPriority = [];
			$('input:checkbox[name=requirementPriority]:checked').each(function() 
		    {
				filterPriority.push($(this).val());
		    });
			
			var filterJobType = [];
			$('input:checkbox[name=requirementType]:checked').each(function() 
		    {
				filterJobType.push($(this).val());
		    });
			
			var filterAssignTo = [];
			$('input:radio[name=requirementAssignTo]:checked').each(function() 
		    {
				filterAssignTo.push($(this).val());
		    });
			
			var filterSpecJobs = [];
			$('input:checkbox[name=requirementSpecJobs]:checked').each(function() 
		    {
				filterSpecJobs.push($(this).val());
		    });
			
			var filterProjects = [];
			$('input:checkbox[name=projectNameById]:checked').each(function() 
		    {
				filterProjects.push($(this).val());
		    });
			
			var city = [];
			$('input:checkbox[name=cityName]:checked').each(function() 
		    {
				city.push($(this).val());
		    });
			
			var frm = $('#requirement_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, ""); 
		    var jsonData = encodeURIComponent(con);
			var form = $('#requirement_form')[0];
	  		var formData = new FormData(form); 
	  		var searchValue = $("#dataTableSearch").val();
	  		searchValue = searchValue ? searchValue : "";
	  		$("#requirementList").empty();
			$("#requirementList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  		 url = sourceleadUrl+"/searchRequirement?searchType="+searchType+"&filterStatus="+filterStatus+"&filterClients="+filterClients+"&searchValue="+searchValue+"&filterPriority="+filterPriority+"&filterJobType="+filterJobType+"&filterAssignTo="+filterAssignTo+"&filterSpecJobs="+filterSpecJobs+"&filterProjectId="+filterProjects+"&city="+city;
	  		 url += "&"+$($('#requirement_form')[0]).serialize();
			
			
			
			/* var searchValue = $("#dataTableSearch").val();
			searchValue = searchValue ? searchValue : "";
			$("#requirementList").empty();
			$("#requirementList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		    url = sourceleadUrl+"/searchRequirement?searchType="+searchType+"&filterStatus="+filterStatus+"&filterClients="+filterClients+"&searchValue="+searchValue; */
	    }
	    else if(searchType == 'advanceSearch'){
	    			 url = sourceleadUrl+"/searchRequirement?searchType="+searchType;
	    			url += "&"+$($('#requirement_form')[0]).serialize();
	    			closePop();
	    			//clearAlldata();
	    }
	    $('#dataTableSearch').removeAttr('disabled');
	    $('#dataTableSearch').attr('disabled','disabled');
	    
	    $("#jobsTab").addClass("current");
	    $("#jobFairTab").removeClass("current");
	    $(".advsrch_chosen").show();
	    $("#jobsTabContent").show();
	    $("#jobFairTabContent").hide();
	    
	    prepareRequirementData(url,sourceleadUrl);
 } 

function prepareRequirementData(url,sourceleadUrl){
	
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  var canEduDatePattern = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  var canEduDatePattern = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  var canEduDatePattern = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  var canEduDatePattern = 'YYYY-DD-MM'; 
	  }
	var orgATS = '${SourceLeadWeb_ats}';
	var orgVMS = '${SourceLeadWeb_vms}';
	 $("#requirementList").removeAttr("style");
	 $("#requirementList").empty();
	 var result = "";
	 result = result +"<table id='requirementPagination' class='display' cellspacing='0' width='100%' >"; 
   result = result +"<thead>"; 
   result = result +"  <tr>"; 
   result = result +"    <th>requirementId</th>";
   result = result +"    <th>Action</th>"; 
   result = result +"    <th>Generated ID</th>"; 
   result = result +"    <th>Title</th>";
   result = result +"    <th>Process</th>";
   result = result +"    <th>Client</th>";
   result = result +"    <th>Pay Rate</th>";
   result = result +"    <th>Bill Rate</th>";
   result = result +"    <th>Type</th>"; 
   result = result +"    <th>Location</th>"; 
   result = result +"    <th>Applicants</th>";
   result = result +"    <th>Shortlisted</th>";
   result = result +"    <th>Submit to Client</th>";
   result = result +"    <th>Interview</th>";
   result = result +"    <th>Offer</th>";
   result = result +"    <th>Onhold</th>";
   result = result +"    <th>No Response</th>";
   result = result +"    <th>Joined</th>";
   result = result +"    <th>Backout</th>";
   result = result +"    <th>Rejected</th>";
   result = result +"    <th>Owner</th>"; 
   result = result +"    <th>Posted On<br>(<small class='custom-small'>"+canEduDatePattern+"</small>)</th>";
   result = result +"    <th>Experience</th>";
   result = result +"    <th>noOfpositions</th>";
   result = result +"    <th>Industry Name</th>";
   result = result +"    <th>Status</th>"; 
//    result = result +"<th>Actions</th>";
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
   result = result +" </tbody>";
   result = result +"</table>"; 
   $("#noReq").val('');
   $("#requirementList").append(result); 
   jpagination(url,sourceleadUrl); 
   
   /* to call requirement conformation saving message */
   msgFadein1();
}

function clearAlldata(){
   $("#requirementStatu").val('').trigger("chosen:updated");
   $("#updatedOn").val('').trigger("chosen:updated");
   $("#client").val('').trigger("chosen:updated");
   $("#state").val('').trigger("chosen:updated");
   $("#statusAdvanced").val('').trigger("chosen:updated");
   $("#invitationAdvanced").val('').trigger("chosen:updated");
   $("#contactNameAdvanced").val('').trigger("chosen:updated");
   $("#emailAdvanced").val('').trigger("chosen:updated");
   $("#orgNameAdvanced").val('').trigger("chosen:updated");
   $("#noticePeriod").val('').trigger("chosen:updated");
 
   $("#requirementTitle").empty();
   $("#jobId").empty();
 
}
/* function clearSearch(){
	document.getElementById("requirement_form").reset();
	$('#requirementStatu').val('').trigger('chosen:updated');
	$('#updatedOn').val('').trigger('chosen:updated');
	$('#client').val('').trigger('chosen:updated');
	$('#state').val('').trigger('chosen:updated');
	$('#city').val('').trigger('chosen:updated');
	$('#noticePeriod').val('').trigger('chosen:updated');
	$('#requirementTitle').val('').trigger('chosen:updated');
	$('#jobId').val('').trigger('chosen:updated');
} */
var reqTable = "";
function jpagination(url,sourceleadUrl){ 
	var selectedColumns = "";
	$("#jobCustomColumns").each(function () {
		
	});
	var colIndex = 0;
	reqTable =  $('#requirementPagination').DataTable( { 
   		"processing": true,
       "serverSide": true,
       "searching": false,
       "searchHighlight": true,
       "ajax": $.fn.dataTable.pipeline( {
           url: url,
           method: 'POST',
           pages: 1
       }),
       "columns": [
    	   { "data": "requirementId" },
    	   { "data": "requirementUUID" },
    	   { "data": "generatedId"},
           { "data": "jobTitle"},
           { "data": "process"},
           { "data": "clientName"},
           { "data": "rate"},
           { "data": "billRate"},
           { "data": "jobType" },
           { "data": "country"},
           { "data": "totalApplicants" },
           { "data": "shortListApplicantsCount" },
           { "data": "submitToClientsCount" },
           { "data": "interviewCount" },
           { "data": "offerCount" },
           { "data": "onHoldCount" },
           { "data": "noResponseCount" },
           { "data": "joinedCount" },
           { "data": "backOutCount" },
           { "data": "rejectedCount" },
           { "data": "manager" },
           { "data": "createdDate"},
           { "data": "experience" },
           { "data": "positions" },
           { "data": "industryName" },
           { "data": "requirementSubmissionStatus" }
          
       ],
	       "columnDefs": [ 
	    	   {
	   	   	  	"targets": colIndex++,
	   		  	"data": "requirementId",
	   		  	"sortable": true,
	   		    "visible": false,
	   		  	"render": function(data, type, opt, meta) {
	   		  		 var result = "";
	   		  		 result = result +"<td>";
	   		  	  	 if(opt.id != '' && opt.id != null){
	   		  		   	 result = result +"<div class='tbldata-dotdot_50 tblminwidth-75' title='"+opt.id+"'>"+opt.id+"</span></div>";
	   		  	    	}else{
	   		  	  		 result = result +"<div>-----</div>";
	   		  	  	 }
	   		  		 result = result +"</td>";
	   	             return  result;
	   		  	}
	   	        },{
		         "targets": colIndex++,
		         "sortable": false,
		         "data": "requirementUUID",
		         "render": function ( data, type, opt, meta ) {
	         		   var result = "";
	         		   var trashActive = "";
	         		  result = result +" <td><div class=' widthIcon widthIcon-four'>";
	         		
	         		 '<sec:authorize access="hasAnyRole(\'ROLE_CREATE_REQUIREMENT\',\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')">';
	         		 if((opt.isRequirementReleased != 'Y' && !opt.totalApplicants > 0) && ('${SourceLeadWeb_loginUserRole}' ==  'ROLE_ADMIN' || '${SourceLeadWeb_loginUserRole}' == 'ROLE_SOURCELEAD_ADMIN')){
	         			if(jQuery.inArray(opt.requirementUUID, reqUUIDArr) !== -1){
	         				trashActive = "trashActive";
	         			}else{
	         				trashActive = "trashIcon";
	         			}
	         		 		result = result +'<div class="text-center"><a href="javascript:void(0)" title="Select for Delete Job" class="add-applicant anchorBlack"><i class="fa fa-trash '+trashActive+'" id="'+opt.requirementUUID+'" onclick="selectDeleteJob(this);" ></i></a></div>';
	         		 }else{
	         				result = result +'<div class="text-center"><i class="fa fa-trash permissionDenied"></i></div>';
	         		 }
	         		'</sec:authorize>';
	         		
	         		
	         		  '<sec:authorize access="hasAnyRole(\'ROLE_ADD_APPLICANT\')">';
	      		        if(opt.requirementSubmissionStatus == 'APPROVED' || opt.requirementSubmissionStatus == 'OPEN' || opt.requirementSubmissionStatus == 'NEW'){
	      		        	result = result +'<div class="text-center"><a href="javascript:void(0)" title="Add Applicant" onclick="getAddApplicant('+opt.id+',\''+opt.reqCoolingOffPeriod+'\');" class="add-applicant"><i class="fa fa-user-plus"></i></a></div>';		        
	      		        }else{
	      		        	result = result +'<div class="text-center"><i class="fa fa-user-plus permissionDenied"></i></div>';
	      		        }
	      	        '</sec:authorize>';
		      	      '<sec:authorize access="!hasAnyRole(\'ROLE_ADD_APPLICANT\')">';
		      	    		result = result +'<div class="text-center"><i class="fa fa-user-plus permissionDenied"></i></div>';
	      	        '</sec:authorize>';
	      	        
	      	      	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_REQUIREMENT\')">';
       				result = result +'<div class="text-center"><a href="javascript:void(0)" title="Assign To" data-toggle="modal" data-target="#myModal" onclick="getAssignedMembersDiv('+opt.id+');"><i class="fa fa-address-book '+(opt.assignedMembersCount == 0 ? "anchorBlack" : "")+' "></i></a></div>';
       		 	'</sec:authorize>';
       		 	'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_EDIT_REQUIREMENT\')">';
       				result = result +'<div class="text-center"><i class="fa fa-address-book permissionDenied"></i></div>';
       		 	'</sec:authorize>';
	      	        
	      	        '<sec:authorize access="hasAnyRole(\'ROLE_RELEASE_REQUIREMENT\')">';
	      					if(opt.requirementSubmissionStatus == 'APPROVED' || opt.requirementSubmissionStatus == 'OPEN' || opt.requirementSubmissionStatus == 'NEW'){
	      						   var userRoleName = '${SourceLeadWeb_loginUserRole}'; 
	      						   var userId = '${SourceLeadWeb_user.id}';
	      						   var contains = false;
	      						   if(opt.requirementReleasedUserIds != null && opt.requirementReleasedUserIds != '' && opt.requirementReleasedUserIds != 'undefined'){
	      							   for(var k = 0;k< opt.requirementReleasedUserIds.length ; k++){
	      								  if(userId == opt.requirementReleasedUserIds[k]){
	      									  var contains = true;
	      								  }
	      							   }
	      						   }
	      						   if((opt.isRequirementReleased != null && opt.isRequirementReleased != '' && opt.isRequirementReleased != 'undefined'  && opt.isRequirementReleased == 'Y') 
	      								   && (contains == true || userRoleName ==  'ROLE_ADMIN' || userRoleName == 'ROLE_SOURCELEAD_ADMIN')){
	      				    		 	result = result +"<div><a href='<c:url value='/app/releaseRequirementToClientOrVendors?requirementUUID="+opt.requirementUUID+"'/>' title='Release Job'><i class='fa fa-bullhorn success'></i></a></div>"; 
	      			
	      						   	 }else{	
	      								result = result +"<div><a href='<c:url value='/app/releaseRequirementToClientOrVendors?requirementUUID="+opt.requirementUUID+"'/>' title='Release Job'><i class='fa fa-bullhorn'></i></a></div>"; 
	      						   	 }
	      			       }
	      				   else{
	      					 result = result +'<div><i class="fa fa-bullhorn permissionDenied"></i></div>';
	      				   } 
	                   '</sec:authorize>'; 
	                   '<sec:authorize access="!hasAnyRole(\'ROLE_RELEASE_REQUIREMENT\')">';
	                   		result = result +'<div><i class="fa fa-bullhorn permissionDenied"></i></div>';
	                   '</sec:authorize>';
	      		       result = result +"</div></td>";
		              return result;
	          }
		 },{
	   	  	"targets": colIndex++,
		  	"data": "generatedId",
		  	"sortable": true,
		  	"visible": $("input[data-name='generatedId']").is(":checked"),
		  	"render": function(data, type, opt, meta) {
		  		 var result = "";
		  		 result = result +"<td>";
		  	  	 if(opt.generatedId != '' && opt.generatedId != null){
		  	  		
		  		   	 result = result +"<div class='tbldata-dotdot_50 tblminwidth-75' title='"+opt.generatedId+"'>"+opt.generatedId+"</span></div>";
		  	    	}else{
		  	  		 result = result +"<div>-----</div>";
		  	  	 }
		  		 result = result +"</td>";
	             return  result;
		  	}
	        } ,{
           "targets": colIndex++,
           "data": "jobTitle",
           "render": function ( data, type, opt, meta ) {
        	   //for export pop-up
        	   $("#requirementEmptyListMessage").val(1);
           		var result = "";
           	 if(opt.jobId != '' && opt.jobId != null){
       		  	 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')">'; 
       		       	result = result +"<td><a href='<c:url value='/app/requirementSummary?requirementUUID="+opt.requirementUUID+"'/>'><div class='proj_show tbldata-dotdot_140'><span class='pull-left' title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span><i class='fa fa-binoculars'></i></div></a></td>";
       		       '</sec:authorize>';
       		       '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')">'; 
       		       	result = result +"<td><div class='proj_show tbldata-dotdot_140'><span class='pull-left' title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span></div></td>";
       		       '</sec:authorize>';
       		  	 }else{
       		  		 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')">'; 
       		           	result = result +"<td><a href='<c:url value='/app/requirementSummary?requirementUUID="+opt.requirementUUID+"'/>'><div class='proj_show tbldata-dotdot_140'><span class='pull-left' title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span><i class='fa fa-binoculars'></i></div></a></td>";
       		           '</sec:authorize>';
       		           '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')">'; 
       		           	result = result +"<td><div class='proj_show tbldata-dotdot_140'><span class='pull-left' title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span></div></td>";
       		           '</sec:authorize>';
       		  	 }
	                return result;
            }
           },
           {
	            "targets": colIndex++,
	            "data": "process",
	            "sortable": false,
	            "visible": $("input[data-name='process']").is(":checked"),
	            "render": function ( data, type, opt, meta ) {
	            		var result = "";
	       		       	result = result +"<td><div class='tbldata-dotdot_140' " + (opt.process != null && opt.process != "" ? "title='" + opt.process + "'" : "") + ">" + (opt.process != null && opt.process != "" ? opt.process : "------") + "</div></td>";
		                return result;
	             }
	        },
	        {
	            "targets": colIndex++,
	            "data": "clientName",
	            "visible": $("input[data-name='clientName']").is(":checked"),
	            "render": function ( data, type, opt, meta ) {
	            		var result = "";
           		   if(opt.clientName != null && opt.clientName != '' && opt.clientName != 'undefined'){
           	      		
           			 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
           	            result = result + '<td><div  class="tbldata-dotdot-sm"><a href="'+sourceleadUrl+'/clientDashboard?orgRelationUUID='+opt.orgRelationUUID+'" title="'+opt.clientName+'">'+opt.clientName+'</a></div>';
           	         if(opt.clientContactName != null && opt.clientContactName != '' && opt.clientContactName != 'undefined'){
	     	        	  result = result + '\n<div  class="tbldata-dotdot-sm">'+opt.clientContactName+'</div>';
	     	           }
	     	           result = result + '</td>';
           	         '</sec:authorize>';
           	         
	           	      '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
	     	            result = result + '<td><div  class="tbldata-dotdot-sm">'+opt.clientName+'</div>';
	     	           if(opt.clientContactName != null && opt.clientContactName != '' && opt.clientContactName != 'undefined'){
	     	        	  result = result + '\n<div  class="tbldata-dotdot-sm">'+opt.clientContactName+'</div>';
	     	           }
	     	           result = result + '</td>';
	     	         '</sec:authorize>';
           	         
           		   }else{
						result = result +"<td><div>-----</div></td>";
           	       }
		                return result;
	             }
	        },
	        {
	            "targets": colIndex++,
	            "data": "rate",
	            "sortable": false,
	            "visible": $("input[data-name='payRate']").is(":checked"),
	            "render": function ( data, type, opt, meta ) {
	            		var result = "";
           		   if((opt.rate != null && opt.rate != '' && opt.rate != 'undefined') || (opt.maxRate != null && opt.maxRate != '' && opt.maxRate != 'undefined')){
           				result = result +"<div class='tbldata-dotdot'>"+opt.currencyCode+" ";
           				if(opt.rate != null && opt.rate != '' && opt.rate != 'undefined'){
           					result = result + opt.rate;
           				}
           				if((opt.rate != null && opt.rate != '' && opt.rate != 'undefined') && (opt.maxRate != null && opt.maxRate != '' && opt.maxRate != 'undefined')){
           					result = result + " - ";
           				}
						if(opt.maxRate != null && opt.maxRate != '' && opt.maxRate != 'undefined'){
							result = result + opt.maxRate;
           				}
           				result = result +" / "+opt.rateType+"</span></div>";
           		   }else{
						result = result +"<td><div>-----</div></td>";
           	       }
		                return result;
	             }
	        },
	        {
	            "targets": colIndex++,
	            "data": "billRate",
	            "sortable": false,
	            "visible": $("input[data-name='billRate']").is(":checked"),
	            "render": function ( data, type, opt, meta ) {
	            		var result = "";
	            		if((opt.clientRate != null && opt.clientRate != '' && opt.clientRate != 'undefined') || (opt.clientMaxRate != null && opt.clientMaxRate != '' && opt.clientMaxRate != 'undefined')){
	           				result = result +"<div class='tbldata-dotdot'>"+opt.clientCurrencyCode+" ";
	           				if(opt.clientRate != null && opt.clientRate != '' && opt.clientRate != 'undefined'){
	           					result = result + opt.clientRate;
	           				}
	           				if((opt.clientRate != null && opt.clientRate != '' && opt.clientRate != 'undefined') && (opt.clientMaxRate != null && opt.clientMaxRate != '' && opt.clientMaxRate != 'undefined')){
	           					result = result + " - ";
	           				}
							if(opt.clientMaxRate != null && opt.clientMaxRate != '' && opt.clientMaxRate != 'undefined'){
								result = result + opt.clientMaxRate;
	           				}
	           				result = result +" / "+opt.clientRateType+"</span></div>";
	           		   }else{
						result = result +"<td><div>-----</div></td>";
           	       }
		                return result;
	             }
	        },
       	{
	            "targets": colIndex++,
	            "data": "jobType",
	            "visible": $("input[data-name='jobType']").is(":checked"),
	            "render": function ( data, type, opt, meta ) {
	            		var result = "";
	            		 if(opt.jobType != null && opt.jobType != '' && opt.jobType != "undefined")
	            	   	 {
	            	  	  if(opt.otherJobType !=null && opt.otherJobType !='' && opt.otherJobType !="undefined"){
	            	         result = result +"  <td><div>"+opt.otherJobType+"</div></td>";
	            	   	 }else{
	            	   		result = result +"  <td><div class='tblminwidth-75'>"+opt.jobType+"</div></td>"; 
	            	   	 }
	            	   	 }else{
	            	   		 result = result +"  <td><div class='tblminwidth-75'>----</div></td>"; 
	            	   	 }
		                return result;
	             }
       	},
        	{
	            "targets": colIndex++,
	            "data": "country",
	            "visible": $("input[data-name='location']").is(":checked"),
	            "render": function ( data, type, opt, meta ) {
	            		var result = "";
	            		
		            	    result = result +'<td>';
		   		        	result = result +'<div class="positionRelative">';
			   		         for(var k=0; k<1; k++){
			   		        	result = result + '<p class="tbldata-dotdot-me pull-left">'+opt.locationsList[k]+'</p>';
			   		         }
		   		    	
			   		        result = result +'<div class="show_Empdetails showjoblist">';
		   		        	result = result +'<i class="fa fa-ellipsis-v"></i>';
	
		   		        	result = result +'<div class="emailvalues emailvaluesNew">';
		   		        	
 							for(var k=0; k<opt.locationsList.length; k++){
 								result = result +'<p><i class="fa fa-map-marker"></i>'+opt.locationsList[k]+'</p>';
 			   		        	
		   		    	 	}
		   		        	result = result +'</div>';
		   		        	result = result +'</div>';
		   

	   		        	result = result +'</div>';
	   		        	result = result +'</td>';
	            		
	            		
	       /*      		if(opt.state!=null && opt.state !='' && opt.state !='undefined' &&  opt.city !='undefined' && opt.city !='' &&  opt.city!=null){
	            	           result = result +"<td><div class='tbldata-dotdot-100'><span title='"+opt.city+', '+opt.state+', '+opt.country+"'>"+opt.city+', '+opt.state+', '+opt.country+"</span></div></td>";
	            	    }else if(opt.state!=null && opt.state !='' && opt.state !='undefined'){
	            	           result = result +"<td><div class='tbldata-dotdot-100'><span title='"+opt.state+', '+opt.country+"'>"+opt.state+', '+opt.country+"</span></div></td>";
	            	    }else if(opt.country!=null && opt.country !='' && opt.country !='undefined'){
	            	           result = result +"<td><div><span title='"+opt.country+"'>"+opt.country+"</span></div></td>";
	            	    }else if(opt.location!=null && opt.location !='' && opt.location !='undefined'){
	            	    		result = result +"<td><div><span title='"+opt.location+"'>"+opt.location+"</span></div></td>";
	            	    }else{
	            	   		 result = result +"  <td><div class='tblminwidth-75'>----</div></td>"; 
	            	   	 }  */
		                return result;
	             }
		    },
		 	{
		         "targets": colIndex++,
		         "data": "totalApplicants",
		     	 "sortable": false,
		     	"visible": $("input[data-name='totalApplicants']").is(":checked"),
		         "render": function ( data, type, opt, meta ) {
		         		var result = "";
		         		if(opt.totalApplicants!=null && opt.totalApplicants!='' && opt.totalApplicants!='undefined' && opt.totalApplicants > 0){
		         			result = result +"<td><a href='<c:url value='/app/requirementSummary?type=applicants&requirementUUID="+opt.requirementUUID+"'/>'><div>"+opt.totalApplicants+"</div></a></td>";
		         		}
		         		else{
		         			result = result +"<td><div>"+opt.totalApplicants+"</div></td>";
		         		}
			            return result;
		          }
		 	 },
			 	{
		         "targets": colIndex++,
		         "data": "shortListApplicantsCount",
		     	 "sortable": false,
		     	"visible": $("input[data-name='Shortlisted']").is(":checked"),
		         "render": function ( data, type, opt, meta ) {
		         		var result = "";
		         		if(opt.shortlistCount!=null && opt.shortlistCount!='' && opt.shortlistCount!='undefined' && opt.shortlistCount > 0){
		                     result = result +"<td><a href='<c:url value='/app/requirementSummary?type=SHORTLIST&requirementUUID="+opt.requirementUUID+"'/>'><div>"+opt.totalApplicants+"</div></a></td>";
// 		         			result = result +"<td><div>"+opt.shortlistCount+"</div></td>";
		         		}
		         		else{
		         			result = result +"<td><div>0</div></td>";
		         		}
			            return result;
		          }
		 	 },
			 	{
		         "targets": colIndex++,
		         "data": "submitToClientsCount",
		     	 "sortable": false,
		     	 "visible": $("input[data-name='submitToClient']").is(":checked"),
		         "render": function ( data, type, opt, meta ) {
		         		var result = "";
		         		if(opt.submitToClientCount!=null && opt.submitToClientCount!='' && opt.submitToClientCount!='undefined' && opt.submitToClientCount > 0){
		              
		         			result = result +"<td><a href='<c:url value='/app/requirementSummary?type=SUBMIT_TO_CLIENT&requirementUUID="+opt.requirementUUID+"'/>'><div>"+opt.submitToClientCount+"</div></a></td>";

		         			// 		         			result = result +"<td><div>"+opt.submitToClientCount+"</div></td>";
		         		}
		         		else{
		         			result = result +"<td><div>0</div></td>";
		         		}
			            return result;
		          }
		 	 },
			 	{
		         "targets": colIndex++,
		         "data": "interviewCount",
		     	 "sortable": false,
		     	 "visible": $("input[data-name='interview']").is(":checked"),
		         "render": function ( data, type, opt, meta ) {
		         		var result = "";
		         		if(opt.interviewCount!=null && opt.interviewCount!='' && opt.interviewCount!='undefined' && opt.interviewCount > 0){
		         			
		         			result = result +"<td><a href='<c:url value='/app/requirementSummary?type=INTERVIEW&requirementUUID="+opt.requirementUUID+"'/>'><div>"+opt.interviewCount+"</div></a></td>";

// 		         			result = result +"<td><div>"+opt.interviewCount+"</div></td>";
		         		}
		         		else{
		         			result = result +"<td><div>0</div></td>";
		         		}
			            return result;
		          }
		 	 },
			  	{
		         "targets": colIndex++,
		         "data": "offerCount",
		     	 "sortable": false,
		     	 "visible": $("input[data-name='offer']").is(":checked"),
		         "render": function ( data, type, opt, meta ) {
		         		var result = "";
		         		if(opt.offersCount!=null && opt.offersCount!='' && opt.offersCount!='undefined' && opt.offersCount > 0){
		         			
		         			result = result +"<td><a href='<c:url value='/app/requirementSummary?type=OFFERMADE&requirementUUID="+opt.requirementUUID+"'/>'><div>"+opt.offersCount+"</div></a></td>";

// 		         			result = result +"<td><div>"+opt.offersCount+"</div></td>";
		         		}
		         		else{
		         			result = result +"<td><div>0</div></td>";
		         		}
			            return result;
		          }
		 	 },
		 	 
			  	{
		         "targets": colIndex++,
		         "data": "onHoldCount",
		     	 "sortable": false,
		     	 "visible": $("input[data-name='onHold']").is(":checked"),
		         "render": function ( data, type, opt, meta ) {
		         		var result = "";
		         		if(opt.onHoldCount!=null && opt.onHoldCount!='' && opt.onHoldCount!='undefined'){
		         			result = result +"<td><div>"+opt.onHoldCount+"</div></td>";
		         		}
		         		else{
		         			result = result +"<td><div>0</div></td>";
		         		}
			            return result;
		          }
		 	 },
		 	 
			 	{
		         "targets": colIndex++,
		         "data": "noResponseCount",
		     	 "sortable": false,
		     	 "visible": $("input[data-name='noResponse']").is(":checked"),
		         "render": function ( data, type, opt, meta ) {
		         		var result = "";
		         		if(opt.noResponseCount!=null && opt.noResponseCount!='' && opt.noResponseCount!='undefined'){
		         			result = result +"<td><div>"+opt.noResponseCount+"</div></td>";
		         		}
		         		else{
		         			result = result +"<td><div>0</div></td>";
		         		}
			            return result;
		          }
		 	 },
		 	 
			 	{
		         "targets": colIndex++,
		         "data": "joinedCount",
		     	 "sortable": false,
		     	 "visible": $("input[data-name='joined']").is(":checked"),
		         "render": function ( data, type, opt, meta ) {
		         		var result = "";
		         		if(opt.joinedCount!=null && opt.joinedCount!='' && opt.joinedCount!='undefined' && opt.joinedCount > 0){
		         			
		         			result = result +"<td><a href='<c:url value='/app/requirementSummary?type=JOINED&requirementUUID="+opt.requirementUUID+"'/>'><div>"+opt.joinedCount+"</div></a></td>";

// 		         			result = result +"<td><div>"+opt.joinedCount+"</div></td>";
		         		}
		         		else{
		         			result = result +"<td><div>0</div></td>";
		         		}
			            return result;
		          }
		 	 },
		 	 
			 	{
		         "targets": colIndex++,
		         "data": "backOutCount",
		     	 "sortable": false,
		     	 "visible": $("input[data-name='backOut']").is(":checked"),
		         "render": function ( data, type, opt, meta ) {
		         		var result = "";
		         		if(opt.backoutCount!=null && opt.backoutCount!='' && opt.backoutCount!='undefined' && opt.backoutCount > 0){
		         			
		         			result = result +"<td><a href='<c:url value='/app/requirementSummary?type=BACKOUT&requirementUUID="+opt.requirementUUID+"'/>'><div>"+opt.backoutCount+"</div></a></td>";

// 		         			result = result +"<td><div>"+opt.backoutCount+"</div></td>";
		         		}
		         		else{
		         			result = result +"<td><div>0</div></td>";
		         		}
			            return result;
		          }
		 	 },
			 	{
		         "targets": colIndex++,
		         "data": "rejectedCount",
		     	 "sortable": false,
		     	 "visible": $("input[data-name='rejected']").is(":checked"),
		         "render": function ( data, type, opt, meta ) {
		         		var result = "";
		         		if(opt.rejectedCount!=null && opt.rejectedCount!='' && opt.rejectedCount!='undefined' && opt.joinedCount > 0){
		         			
		         			result = result +"<td><a href='<c:url value='/app/requirementSummary?type=REJECTED&requirementUUID="+opt.requirementUUID+"'/>'><div>"+opt.rejectedCount+"</div></a></td>";

// 		         			result = result +"<td><div>"+opt.rejectedCount+"</div></td>";
		         		}
		         		else{
		         			result = result +"<td><div>0</div></td>";
		         		}
			            return result;
		          }
		 	 },
		 	 
			 {
		         "targets": colIndex++,
		         "data": "manager",
		         "visible": $("input[data-name='owner']").is(":checked"),
		         "render": function ( data, type, opt, meta ) {
		         		var result = "";
		         		if(opt.manager!=null && opt.manager!='' && opt.manager!='undefined'){
		         	      	 result = result +"<td><div class='tbldata-dotdot-100' title='"+opt.manager+"'>"+opt.manager+"</div></td>";
	         	        }else{
	         	      	 result = result +"<td><div>----</div></td>";
	         	        }
			            return result;
		          }
		 },
		 {
	         "targets": colIndex++,
	         "data": "createdDate",
	         "visible": $("input[data-name='postedOn']").is(":checked"),
	         "render": function (data, type, opt, meta ) {
         		   var result = "";
         		   if(opt.postedOnStr!=null && opt.postedOnStr !='' && opt.postedOnStr !='undefined'){
                  	 result = result +"<td><div class='tblminwidth-125'>"+opt.postedOnStr+"</div></td>";
                   }else{
                  	 result = result +"<td><div class='tblminwidth-125'>----</div></td>";
                   }
	               return result;
	          }
	 },
			 	{
		         "targets": colIndex++,
		         "data": "experience",
		     	 "sortable": false,
		     	 "visible": $("input[data-name='experience']").is(":checked"),
		         "render": function ( data, type, opt, meta ) {
		         		var result = "";
		         		if(opt.minExp!=null && opt.minExp!='' && opt.minExp!='undefined' && opt.maxExp == null){
		         			result = result +"<td><div>min Exp - "+opt.minExp+"</div></td>";
		         		}
		         		else if(opt.maxExp!=null && opt.maxExp!='' && opt.maxExp!='undefined' && opt.maxExp == null){
		         			result = result +"<td><div>max Exp - "+opt.maxExp+"</div></td>";
		         		}
		         		else if((opt.maxExp!=null && opt.maxExp!='' && opt.maxExp!='undefined') && (opt.minExp!=null && opt.minExp!='' && opt.minExp!='undefined')){
		         			result = result +"<td><div>min Exp- "+opt.minExp+"- max Exp - "+opt.maxExp+"</div></td>";
		         		}
		         		else{
		         			result = result +"<td><div>0</div></td>";
		         		}
			            return result;
		          }
		 	 },
			 	{
		         "targets": colIndex++,
		         "data": "positions",
		     	 "sortable": false,
		     	 "visible": $("input[data-name='noOfPositions']").is(":checked"),
		         "render": function ( data, type, opt, meta ) {
		         		var result = "";
		         		if(opt.positions!=null && opt.positions!='' && opt.positions!='undefined'){
		         			result = result +"<td><div>"+opt.positions+"</div></td>";
		         		}
		         		else{
		         			result = result +"<td><div>0</div></td>";
		         		}
			            return result;
		          }
		 	 },
		 	{
		         "targets": colIndex++,
		         "data": "industryName",
		     	 "sortable": true,
		     	 "visible": $("input[data-name='industryName']").is(":checked"),
		         "render": function ( data, type, opt, meta ) {
		         		var result = "";
		         		if(opt.industryName!=null && opt.industryName!='' && opt.industryName!='undefined'){
		         			result = result +"<td><div>"+opt.industryName+"</div></td>";
		         		}
		         		else{
		         			result = result +"<td><div>--</div></td>";
		         		}
			            return result;
		          }
		 	 },
			 {
			         "targets": colIndex++,
			         "sortable": false,
			         "data": "requirementSubmissionStatus",
			         "render": function (data, type, opt, meta) {
			        	        var contains = false;
			        	        var isAllow = false;
			        	        var approver = false;
			         		    var result = "";
			         		   result = result +"<td><div style='width:75px'>";
			         		   var userRoleName = '${SourceLeadWeb_loginUserRole}'; 
			         		   var userId = '${SourceLeadWeb_user.id}';
			         		  if((opt.level1ApproverUserId != null && opt.level1ApproverUserId != '' && opt.level1ApproverUserId != 'undefined') || (opt.level2ApproverUserId != null && opt.level2ApproverUserId !='' && opt.level2ApproverUserId != 'undefined')){ 
			         		 if(opt.level2ApproverUserId != null && opt.level2ApproverUserId !=''){
			         			 if(userId == opt.level2ApproverUserId){
									   approver = true;
								  } 
			         		 }else{
			         			  for(var a = 0;a< opt.level1ApproverUserId.length ; a++){
 								  if(userId == opt.level1ApproverUserId[a]){
 									   approver = true;
 								  }
 							   }
			         		 }
			         		  }
			         		   if(opt.isLoginUserAsManager != null && opt.isLoginUserAsManager != '' && opt.isLoginUserAsManager != 'undefined'){
			         			   if(opt.isLoginUserAsManager == userId){
			         				   contains = true;
			         			   }
			         		   }
			         		   
			         		   if(approver == true || contains == true || userRoleName ==  'ROLE_ADMIN' || userRoleName == 'ROLE_SOURCELEAD_ADMIN' || opt.isLoginUserAsApprover == true){   
			         			  isAllow = true;
			         		   }
			         		  
			         		   if(isAllow == true){
			         			  result = result +"<select name='changeStatusId"+opt.id+"' value= '' onchange='changeStatus("+opt.id+");'  id='changeStatusId"+opt.id+"'>";
			         		   }
			         		   else{
			         			  result = result +"<select name='changeStatusId"+opt.id+"' disabled='true'  value= '' onchange='changeStatus("+opt.id+");'  id='changeStatusId"+opt.id+"'>";
			         		   }
			                   if(opt.requirementSubmissionStatus == "DRAFT"){
			         				result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
			         				'<sec:authorize access="hasAnyRole(\'ROLE_ONHOLD_REQUIREMENT\')">';
			         	
			         				result = result +"<option value='ONHOLD'>ONHOLD</option>";
			         				'</sec:authorize>';
			         				'<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">';
			         					result = result +"<option value='CANCEL'>CANCEL</option>";
			         				'</sec:authorize>';
			         			}
			                   else if(opt.requirementSubmissionStatus == "CLOSED"){
			         				result = result +"<option disabled selected >CLOSED</option>";
			         				result = result +"<option value='OPEN'>OPEN</option>";
			         			}
			         			else if(opt.requirementSubmissionStatus == "CANCEL"){
			         				result = result +"<option disabled selected >"+opt.requirementSubmissionStatus+"</option>";
			         			

			         			}else if(opt.requirementSubmissionStatus == "OPEN"){
			         				result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
			         				'<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_FILLED_REQUIREMENT\')">';
			         					result = result +"<option value='FILLEDCLOSED'>FILLEDCLOSED</option>";
			         				'</sec:authorize>';
			         				'<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_UN_FILLED_REQUIREMENT\')">';
			         					result = result +"<option value='UNFILLEDCLOSED'>UNFILLEDCLOSED</option>";
			         				'</sec:authorize>';
			         				'<sec:authorize access="hasAnyRole(\'ROLE_ONHOLD_REQUIREMENT\')">';
			         					result = result +"<option value='ONHOLD'>ONHOLD</option>";
			         				'</sec:authorize>';
			         				'<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">';
			         					result = result +"<option value='CANCEL'>CANCEL</option>";
			         				'</sec:authorize>';
			         			}
			         			else if(opt.requirementSubmissionStatus == "NEW"){
			         				result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
			         				result = result +"<option value='OPEN'>OPEN</option>";
			         				'<sec:authorize access="hasAnyRole(\'ROLE_ONHOLD_REQUIREMENT\')">';
			         					result = result +"<option value='ONHOLD'>ONHOLD</option>";
			         				'</sec:authorize>';
			         				'<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_FILLED_REQUIREMENT\')">';
			         				result = result +"<option value='FILLEDCLOSED'>FILLEDCLOSED</option>";
			         			'</sec:authorize>';
			         			'<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_UN_FILLED_REQUIREMENT\')">';
			         				result = result +"<option value='UNFILLEDCLOSED'>UNFILLEDCLOSED</option>";
			         			'</sec:authorize>';
			         				'<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">';
			         					result = result +"<option value='CANCEL'>CANCEL</option>";
			         				'</sec:authorize>';
			         			}else if(opt.requirementSubmissionStatus == "APPROVED"){
			         				result = result +"<option disabled selected >"+opt.requirementSubmissionStatus+"</option>";
			         				
			         			}
			         			else if(opt.requirementSubmissionStatus == "REJECTED"){
			         				result = result +"<option disabled selected >"+opt.requirementSubmissionStatus+"</option>";
			         				
			         			}
			         			
			         			else if(opt.requirementSubmissionStatus == "SUBMITTED"){
			         				result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
			         				'<sec:authorize access="hasAnyRole(\'ROLE_ONHOLD_REQUIREMENT\')">';
			         					result = result +"<option value='ONHOLD'>ONHOLD</option>";
			         				'</sec:authorize>';
			         				'<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">';
			         					result = result +"<option value='CANCEL'>CANCEL</option>";
			         				'</sec:authorize>';
			         				
			         			}else if(opt.requirementSubmissionStatus == "ONHOLD"){
			         				
			         				if(opt.requirementSubmissionPreviousStatus == "OPEN" || opt.requirementSubmissionPreviousStatus == null || opt.requirementSubmissionPreviousStatus == '' || opt.requirementSubmissionPreviousStatus == 'undefined' ){
			         					result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
			         					result = result +"<option value='OPEN'>OPEN</option>";
 			         					'<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_FILLED_REQUIREMENT\')">';
			         						result = result +"<option value='FILLEDCLOSED'>FILLEDCLOSED</option>";
			         					'</sec:authorize>';
			         					'<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_UN_FILLED_REQUIREMENT\')">';
			         						result = result +"<option value='UNFILLEDCLOSED'>UNFILLEDCLOSED</option>";
			         					'</sec:authorize>';
			         					 
			         				}
			         					if(opt.requirementSubmissionPreviousStatus == "DRAFT"){
			         					result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
			         					result = result +"<option value='DRAFT'>DRAFT</option>";
			         					'<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">';
			         						result = result +"<option value='CANCEL'>CANCEL</option>";
			         					'</sec:authorize>';
			         				}
			         				if(opt.requirementSubmissionPreviousStatus == "SUBMITTED"){
			         					result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
			         					result = result +"<option value='SUBMITTED'>SUBMITTED</option>";
			         					'<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">';
			         						result = result +"<option value='CANCEL'>CANCEL</option>";
			         					'</sec:authorize>';
			         				}if(opt.requirementSubmissionPreviousStatus == "NEW"){
			         					result = result +"<option disabled selected>"+opt.requirementSubmissionStatus+"</option>";
			         					result = result +"<option value='OPEN'>OPEN</option>";
			         					'<sec:authorize access="hasAnyRole(\'ROLE_ONHOLD_REQUIREMENT\')">';
			         						result = result +"<option value='ONHOLD'>ONHOLD</option>";
			         					'</sec:authorize>';
			         					'<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_FILLED_REQUIREMENT\')">';
			         					result = result +"<option value='FILLEDCLOSED'>FILLEDCLOSED</option>";
			         				'</sec:authorize>';
			         				'<sec:authorize access="hasAnyRole(\'ROLE_CLOSE_UN_FILLED_REQUIREMENT\')">';
			         					result = result +"<option value='UNFILLEDCLOSED'>UNFILLEDCLOSED</option>";
			         				'</sec:authorize>';
			         					'<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">';
			         						result = result +"<option value='CANCEL'>CANCEL</option>";
			         					'</sec:authorize>';
			         				}
			         				 
			         			}else if(opt.requirementSubmissionStatus == "UNFILLEDCLOSED"){
			         				result = result +"<option disabled selected hidden>"+opt.requirementSubmissionStatus+"</option>";
			         				result = result +"<option value='OPEN'>OPEN</option>";
			         				
			         			}else if(opt.requirementSubmissionStatus == "FILLEDCLOSED"){
			         				result = result +"<option disabled selected >"+opt.requirementSubmissionStatus+"</option>";
			         				result = result +"<option value='OPEN'>OPEN</option>";
			         			}

			         			result = result +" </select>";	
			                    result = result +" </div></td>";
				                return result;
			          }
			 }
			 
			 
           ],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[0,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"createdRow": function ( row, data, index ) {
	    		row.setAttribute('id',"job_"+data.id);
    	    },
	    	"initComplete": function( settings, json ) {
	    		 $('#dataTableSearch').removeAttr('disabled');
	    	  },
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     },
	     drawCallback: function(){
   	      $('a.paginate_button', this.api().table().container()).on('click', function(){
   	    	jobSearchHighlight();
   	         }); 
   	   }
  
   });
   
  
   $("#requirementPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
   $("input[type=search]").hide();
   $('#requirementPagination').DataTable().column(0).visible(false);
   $("#dataTableSearch").keyup(function(e) {
   	   if(e.which === 13) {
   		 requirementList('list',sourceleadUrl);
   		jobSearchHighlight();
   	 for (var i=0; i<hideColumns.length; i++) {
		 table.column(hideColumns[i]).visible(false); // hide the columns after filters also.
		}
   	   }
   	});
 }
 
 
function jpaginationForJobFairs(url){ 
	var colIndex = 0;
    $('#jobFairPagination').DataTable( {
   		"processing": true,
       "serverSide": true,
       "searching": false,
       "searchHighlight": true,
       "ajax": $.fn.dataTable.pipeline( {
           url: url,
           method: 'POST',
           pages: 1
       }),
       "columns": [
    	   {"data": "generatedId"},
           {"data": "name"},
           {"data": "startDate"},
           {"data": "endDate"},
           {"data": "requirementsList"},
           {"data": "applicantCount"},
           {"data": "offeredCount"},
           {"data": "venue"},
           {"data": "status"},
           {"data": "actions" }
       	],
	       "columnDefs": [
    	   {
	            "targets": colIndex++,
	            "data": "generatedId",
	            "render": function ( data, type, opt, meta ) {
	            		var result = "";
           		   if(opt.generatedId != null && opt.generatedId != '' && opt.generatedId != 'undefined') {
	     	        	result = result + '<td><div  class="tbldata-dotdot-sm">'+opt.generatedId+'</div></td>';
           		   } else {
						result = result +"<td><div>-----</div></td>";
           	       }
		           		return result;
	             }
	        },
	    	{
	           "targets": colIndex++,
	           "data": "name",
	           "render": function ( data, type, opt, meta ) {
	        	   $("#requirementEmptyListMessage").val(2);
	           		var result = "";
	       		  	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')">'; 
	       		    	result = result +"<td><a href='<c:url value='/app/jobFairSummary?uuid="+opt.uuid+"'/>'><div class='proj_show tbldata-dotdot_140'><span class='pull-left' title='"+opt.name+"'>"+opt.name+"</span><i class='fa fa-binoculars'></i></div></a></td>";
	       		    '</sec:authorize>';
		            return result;
	            }
            },
            {
	            "targets": colIndex++,
	            "data": "startDate",
	            "render": function ( data, type, opt, meta ) {
	            		var result = "";
           		   if(opt.startDate != null && opt.startDate != '' && opt.startDate != 'undefined') {
	     	        	result = result + '<td><div  class="tbldata-dotdot-sm">'+opt.startDate+'</div></td>';
           		   } else {
						result = result +"<td><div>-----</div></td>";
           	       }
		           		return result;
	             }
	        },
	        {
	            "targets": colIndex++,
	            "data": "endDate",
	            "render": function ( data, type, opt, meta ) {
	            		var result = "";
           		   if(opt.endDate != null && opt.endDate != '' && opt.endDate != 'undefined'){
	     	        	result = result + '<td><div  class="tbldata-dotdot-sm">'+opt.endDate+'</div></td>';
           		   } else {
						result = result +"<td><div>-----</div></td>";
           	       }
		                return result;
	             }
	        },
			{
	            "targets": colIndex++,
	            "data": "requirementsList",
	            "sortable": false,
	            "render": function ( data, type, opt, meta ) {
	            		var result = "";
           		   if(opt.jobFairRequirementList != null && opt.requirementsLjobFairRequirementListist != '' && opt.jobFairRequirementList != 'undefined') {
	     	        	result = result + '<td><div  class="tbldata-dotdot-sm">'+opt.jobFairRequirementList.length +'</div></td>';
           		   } else {
						result = result +"<td><div>-----</div></td>";
           	       }
		           		return result;
	             }
	        },
	        {
	            "targets": colIndex++,
	            "data": "applicantCount",
	            "sortable": false,
	            "render": function ( data, type, opt, meta ) {
	            		var result = "";
	     	        	result = result + '<td><div  class="tbldata-dotdot-sm">'+opt.applicantCount+'</div></td>';
		                return result;
	             }
	        },
	        {
	            "targets": colIndex++,
	            "data": "offeredCount",
	            "sortable": false,
	            "render": function ( data, type, opt, meta ) {
	            		var result = "";
	     	        	result = result + '<td><div  class="tbldata-dotdot-sm">'+opt.offeredCount+'</div></td>';
		                return result;
	             }
	        },
	        {
	            "targets": colIndex++,
	            "data": "venue",
	            "sortable": false,
	            "render": function ( data, type, opt, meta ) {
	            		var result = "";
           		   if(opt.venue != null && opt.venue != '' && opt.venue != 'undefined'){
	     	        	result = result + '<td><div  class="tbldata-dotdot-sm">'+opt.venue+'</div></td>';
           		   } else {
						result = result +"<td><div>-----</div></td>";
           	       }
		                return result;
	             }
	        },
	        {
	            "targets": colIndex++,
	            "data": "status",
	            "render": function ( data, type, opt, meta ) {
            		var result = "";
     	        	result = result + '<td><div  class="tbldata-dotdot-sm">';
     	        	result += '<select class="fairStatus">';
     	        	result += '<option data-id='+opt.id+' value="1" ' + (opt.status == 1?'selected=""':'') + '>OPEN</option>';
     	        	result += '<option data-id='+opt.id+' value="0" ' + (opt.status == 0?'selected=""':'') + '>CLOSE</option>';
     	        	result += '</select>';
     	        	result += '</div></td>';
	                return result;
	             }
	        },
	        {
	            "targets": colIndex++,
	            "data": "actions",
	            "sortable": false,
	            "render": function ( data, type, opt, meta ) {
	            	var result = '<div class=" widthIcon widthIcon-three">';
	            	result +='	<div class="text-center">';
	            	result +='		<a href="' + sourceleadUrl + '/editJobFair?uuid=' + opt.uuid + '" title="Edit Fair" onclick="" class="add-applicant">';
	            	result +='			<i class="fa fa-edit"></i>';
	            	result +='		</a>';
	            	result +='	</div>';
	            	result +='	<div class="text-center">';
	            	result +='		<a href="javascript:void(0)" title="Delete Fair" onclick="showChangeJobFairStatusPopup(' + opt.id + ', -1)">';
	            	result +='		<i class="fa fa-trash-o"></i>';
	            	result +='		</a>';
	            	result +='	</div>';
	            	result +='</div>';
           	        return result;
	             }
	        }
           ],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[6,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"createdRow": function ( row, data, index ) {
	    		row.setAttribute('id',"jobFair_"+data.id);
    	    },
	    	"initComplete": function( settings, json ) {
	    		 $('#jobFairDataTableSearch').removeAttr('disabled');
	    	},
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     },
	     drawCallback: function(){
   	      $('a.paginate_button', this.api().table().container()).on('click', function(){
   	    	jobFairSearchHighlight();
   	      });
   	   		updateFairStatusEvents();
   	   	}
   });
   
  
   $("#jobFairPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
   $("input[type=search]").hide();
   
   $("#jobFairDataTableSearch").keyup(function(e) {
   	   if(e.which === 13) {
   		jobFairListCheck();
   	   }
   	});
 }
function updateFairStatusEvents() {
	$(".fairStatus").each(function() {
		$(this).on('focusin', function(){
		    $(this).data('prev_value', $(this).val());
		}).on('change', function(){
			var jobFairId = $(this).find("option:selected").data("id");
		    var prev = $(this).data("prev_value");
		    showChangeJobFairStatusPopup(jobFairId, $(this).val(), prev, $(this));
		});
	});
}
</script>

<script>
 function jobSearchHighlight(){
	 setTimeout(function () {
 		var body = $( table.table().body() );
	        body.unhighlight();
	        body.highlight( $("#dataTableSearch").val() );   
			
			}, 1000); 
 }
 function jobFairSearchHighlight(){
	 setTimeout(function () {
 		var body = $( table.table().body() );
	        body.unhighlight();
	        body.highlight( $("#jobFairDataTableSearch").val() );   
			
			}, 1000); 
 }
 </script>
 <script>
	$(document).on( 'init.dt.dth', function (e, settings, json) {
    table = new $.fn.dataTable.Api( settings );
    var body = $( table.table().body() );
    if ( $( table.table().node() ).hasClass( 'searchHighlight' ) || settings.oInit.searchHighlight ||  $.fn.dataTable.defaults.searchHighlight ) {
	
	}
} );
	</script>

<script>
function getAssignedMembersDiv(reqId){
	$("#summaryButtonsDiv").hide();
	$("#listButtonsDiv").show();
	$('#listSaveButton').removeAttr('onclick');
	$('#listSaveButton').attr('onClick', 'editAssignedTeamsOrUsers('+reqId+');');
	$('#resetAssignedTo').removeAttr('onclick');
	$('#resetAssignedTo').attr('onClick', 'clearAssignedTeamsOrUsers();');
	getAssignedMembersListForEdit(reqId);
	
}
</script>
<script>
var reqUUIDArr = new Array();
function selectDeleteJob(event) {
	$(event).toggleClass("trashIcon trashActive");
	var x = $(event).attr('id');
	reqUUIDArr.push(x);
	if($(event).attr('class') == "fa fa-trash trashIcon"){
		reqUUIDArr = jQuery.grep(reqUUIDArr, function(value) {
			  return value != x;
			});
	}
	if (reqUUIDArr != "") {
	 $("#forceDeleteBtn").show();	
	}else {
		$("#forceDeleteBtn").hide();
	}
}

function confirmForceDeleteBtn(){
	$("#requirementList").empty();
	$("#requirementList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
		type: "POST",
	    url: sourceleadUrl+"/forceDeleteForSelectedJobs?"+tokenParamName+"="+tokenValue+"&reqUUIDArr="+reqUUIDArr, 
	    success: function(result) {
	    	if(result.object === 'MissingData'){
	  			showError("Invalid data");
	  		}else{
	  			if (result.response === "SESSION_TIMEDOUT") {
	  				location.replace(sourceleadUrl);
                } else if (result.response === "AWKWARD") {
                    location.href = "/errorPage";
                } else if (result.response === "SUCCESS") {
                	location.reload();
                } else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
                    showError("Operation failed, please try again");
                }
	  		}
	    },
	});
	$("#remove_user_pop, .project_inn_mask").fadeOut();
}

function jobForceDelete(){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>You are sure to want to Delete?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirmForceDeleteBtn' type='button' value='Ok' onClick='confirmForceDeleteBtn();'><input class='u_can_color' type='button'  value='Cancel' onClick='closeJobDeleteConformation();'></b>";
	result = result +"	</div>";
	$("#deleteJobPopUpDiv").empty();
	$("#deleteJobPopUpDiv").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function closeJobDeleteConformation(){
	$("#remove_user_pop, .project_inn_mask").fadeOut();
}
</script>
<div id="deleteJobPopUpDiv"></div>