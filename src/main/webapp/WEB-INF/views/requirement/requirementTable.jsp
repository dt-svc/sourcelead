<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script>
function requirementListCheck(searchValue,universalRequirementSearchList,searchType,sourceleadUrl){
	 if(universalRequirementSearchList != null && universalRequirementSearchList != '' && universalRequirementSearchList != 'undefined' 
		 && universalRequirementSearchList=="universalRequirementList")	 {
		 $("#dataTableSearch").val(searchValue);
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
			var searchValue = $("#dataTableSearch").val();
			searchValue = searchValue ? searchValue : "";
			$("#requirementList").empty();
			$("#requirementList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		    url = sourceleadUrl+"/searchRequirement?searchType="+searchType+"&filterStatus="+filterStatus+"&filterClients="+filterClients+"&searchValue="+searchValue;
	    }
	    else if(searchType == 'advanceSearch'){
	    			 url = sourceleadUrl+"/searchRequirement?searchType="+searchType;
	    			url += "&"+$($('#requirement_form')[0]).serialize();
	    			closePop();
	    			clearAlldata();
	    }
	    prepareRequirementData(url,sourceleadUrl);
 } 

function prepareRequirementData(url,sourceleadUrl){
	var orgATS = '${SourceLeadWeb_ats}';
	var orgVMS = '${SourceLeadWeb_vms}';
	 $("#requirementList").removeAttr("style");
	 $("#requirementList").empty();
	 var result = "";
	 result = result +"<table id='requirementPagination' class='display' cellspacing='0' width='100%' >"; 
   result = result +"<thead>"; 
   result = result +"  <tr>"; 
   result = result +"    <th>Generated ID</th>"; 
   result = result +"    <th>ID</th>"; 
   result = result +"    <th>Title</th>"; 
   result = result +"    <th>Client</th>"; 
   result = result +"    <th>Type</th>"; 
   result = result +"    <th>Location</th>"; 
   result = result +"    <th>Applicants</th>"; 
   result = result +"    <th>Owner</th>"; 
   result = result +"    <th>Posted On</th>";
   result = result +"    <th>Status</th>"; 
   result = result +"<th>Actions</th>";
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
   //$("#requirementEmptyListMessage").val(json.length);
   /* for (var i = 0; i < json.length; i++) { 
     	 var opt = json[i];
     	statusvalidating(opt.id);
   } */
   jpagination(url,sourceleadUrl); 
   /* msgFadein1(); */ 
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
function clearSearch(){
	document.getElementById("requirement_form").reset();
	$('#requirementStatu').val('').trigger('chosen:updated');
	$('#updatedOn').val('').trigger('chosen:updated');
	$('#client').val('').trigger('chosen:updated');
	$('#state').val('').trigger('chosen:updated');
	$('#city').val('').trigger('chosen:updated');
	$('#noticePeriod').val('').trigger('chosen:updated');
	$('#requirementTitle').val('').trigger('chosen:updated');
	$('#jobId').val('').trigger('chosen:updated');
}
function jpagination(url,sourceleadUrl){ 
	var colIndex = 0;
   $('#requirementPagination').DataTable( { 
   		"processing": true,
       "serverSide": true,
       "searching": false,
       "ajax": $.fn.dataTable.pipeline( {
           url: url,
           method: 'POST',
           pages: 1
       }),
       "columns": [
    	   { "data": "generatedId"},
           { "data": "jobId" },
           { "data": "jobTitle"},
           { "data": "clientName"},
           { "data": "jobType" },
           { "data": "country"},
           { "data": "totalApplicants" },
           { "data": "manager" },
           { "data": "createdDate"},
           { "data": "requirementSubmissionStatus" },
           { "data": "requirementUUID" }
       ],
	       "columnDefs": [ {
	   	  	"targets": colIndex++,
		  	"data": "generatedId",
		  	"sortable": false,
		  	"render": function(data, type, opt, meta) {
		  		 var result = "";
		  		 result = result +"<td>";
		  	  	 if(opt.generatedId != '' && opt.generatedId != null){
		  	  		
		  		   	 result = result +"<div class='tbldata-dotdot_50' title='"+opt.generatedId+"'>"+opt.generatedId+"</span></div>";
		  	    	}else{
		  	  		 result = result +"<div>-----</div>";
		  	  	 }
		  		 result = result +"</td>";
	             return  result;
		  	}
	        },{
	    	  	"targets": colIndex++,
	    	  	"data": "jobId",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		 var result = "";
	    	  		 result = result +"<td>";
	    	  	  	 if(opt.jobId != '' && opt.jobId != null){
	    	  		   	 result = result +"<div class='tbldata-dotdot_50' title='"+opt.jobId+"'>"+opt.jobId+"</span></div>";
	    	  	    	}else{
	    	  	  		 result = result +"<div>-----</div>";
	    	  	  	 }
	    	  		 result = result +"</td>";
		             return  result;
	    	  	}
	      },{
           "targets": colIndex++,
           "data": "jobTitle",
           "render": function ( data, type, opt, meta ) {
        	   //for export pop-up
        	   $("#requirementEmptyListMessage").val(1);
           		var result = "";
           		 if(opt.jobId != '' && opt.jobId != null){
           		  	 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')">'; 
           		       	result = result +"<td><a href='<c:url value='/requirementSummary?requirementUUID="+opt.requirementUUID+"'/>'><div class='proj_show tbldata-dotdot_140'><span class='pull-left' title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span><i class='fa fa-binoculars'></i></div></a></td>";
           		       '</sec:authorize>';
           		       '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')">'; 
           		       	result = result +"<td><div class='proj_show tbldata-dotdot_140'><span class='pull-left' title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span></div></td>";
           		       '</sec:authorize>';
           		  	 }else{
           		  		 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_REQUIREMENT\',\'ROLE_VIEW_EDIT_REQUIREMENT\')">'; 
           		           	result = result +"<td><a href='<c:url value='/requirementSummary?requirementUUID="+opt.requirementUUID+"'/>'><div class='proj_show tbldata-dotdot_140'><span class='pull-left' title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span><i class='fa fa-binoculars'></i></div></a></td>";
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
	            "data": "clientName",
	            "render": function ( data, type, opt, meta ) {
	            		var result = "";
           		   if(opt.clientName != null && opt.clientName != '' && opt.clientName != 'undefined'){
           	      	 result = result +"<td><div title='"+opt.clientName+"' class='tbldata-dotdot-100'>"+opt.clientName+"</div></td>";
           	       }else{
           	      	 result = result +"<td><div>-----</div></td>";
           	       }
		                return result;
	             }
	        },
       	{
	            "targets": colIndex++,
	            "data": "jobType",
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
	            "render": function ( data, type, opt, meta ) {
	            		var result = "";
	            		if(opt.state!=null && opt.state !='' && opt.state !='undefined' &&  opt.city !='undefined' && opt.city !='' &&  opt.city!=null){
	            	           result = result +"<td><div class='tbldata-dotdot-100'><span title='"+opt.city+', '+opt.state+', '+opt.country+"'>"+opt.city+', '+opt.state+', '+opt.country+"</span></div></td>";
	            	    }else if(opt.state!=null && opt.state !='' && opt.state !='undefined'){
	            	           result = result +"<td><div class='tbldata-dotdot-100'><span title='"+opt.state+', '+opt.country+"'>"+opt.state+', '+opt.country+"</span></div></td>";
	            	    }else{
	            	           result = result +"<td><div><span title='"+opt.country+"'>"+opt.country+"</span></div></td>";
	            	    }
		                return result;
	             }
		    },
		 	{
		         "targets": colIndex++,
		         "data": "totalApplicants",
		     	 "sortable": false,
		         "render": function ( data, type, opt, meta ) {
		         		var result = "";
		         		if(opt.totalApplicants!=null && opt.totalApplicants!='' && opt.totalApplicants!='undefined' && opt.totalApplicants > 0){
		         			result = result +"<td><a href='<c:url value='/requirementSummary?type=applicants&requirementUUID="+opt.requirementUUID+"'/>'><div>"+opt.totalApplicants+"</div></a></td>";
		         		}
		         		else{
		         			result = result +"<td><div>"+opt.totalApplicants+"</div></td>";
		         		}
			            return result;
		          }
		 	 },
			 {
			         "targets": colIndex++,
			         "data": "manager",
			         "render": function ( data, type, opt, meta ) {
			         		var result = "";
			         		if(opt.manager!=null && opt.manager!='' && opt.manager!='undefined'){
			         	      	 result = result +"<td><div class='tbldata-dotdot-100' title='"+opt.manager+" "+opt.manager+"'>"+opt.manager+"</div></td>";
		         	        }else{
		         	      	 result = result +"<td><div>----</div></td>";
		         	        }
				            return result;
			          }
			 },
			 {
			         "targets": colIndex++,
			         "data": "createdDate",
			         "render": function (data, type, opt, meta ) {
		         		   var result = "";
		         		   if(opt.postedOnStr!=null && opt.postedOnStr !='' && opt.postedOnStr !='undefined'){
		                  	 result = result +"<td><div class='tblminwidth-75'>"+opt.postedOnStr+"</div></td>";
		                   }else{
		                  	 result = result +"<td><div class='tblminwidth-75'>----</div></td>";
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
			         		    var result = "";
			         		   result = result +"<td><div style='width:75px'>";
			         		   var userRoleName = '${SourceLeadWeb_loginUserRole}'; 
			         		   var userId = '${SourceLeadWeb_user.id}';
			         		   if(opt.managerUserId != null && opt.managerUserId != '' && opt.managerUserId != 'undefined'){
			         			   if(opt.managerUserId.id == userId){
			         				  contains = true;
			         			   }
			         		   }
			         		   
			         		   if(contains == true || userRoleName ==  'ROLE_ADMIN' || userRoleName == 'ROLE_SOURCELEAD_ADMIN' || opt.isLoginUserAsApprover == true){   
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
			         			}else if(opt.requirementSubmissionStatus == "CLOSED"){
			         				result = result +"<option disabled selected >CLOSED</option>";

			         			}
			         			else if(opt.requirementSubmissionStatus == "CANCEL"){
			         				result = result +"<option disabled selected >"+opt.requirementSubmissionStatus+"</option>";
			         				result = result +"<option value='OPEN'>OPEN</option>";

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
			         					'<sec:authorize access="hasAnyRole(\'ROLE_CANCEL_REQUIREMENT\')">';
			         						result = result +"<option value='CANCEL'>CANCEL</option>";
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
			         				
			         			}

			         			result = result +" </select>";	
			                    result = result +" </div></td>";
				                return result;
			          }
			 },
			 {
			         "targets": colIndex++,
			         "sortable": false,
			         "data": "requirementUUID",
			         "render": function ( data, type, opt, meta ) {
			         		   var result = "";
			         		  result = result +" <td><div class='widthIcon'>";
			      	        '<sec:authorize access="hasAnyRole(\'ROLE_ADD_APPLICANT\')">';
			      		        if(opt.requirementSubmissionStatus == 'APPROVED' || opt.requirementSubmissionStatus == 'OPEN' || opt.requirementSubmissionStatus == 'NEW'){
			      		        	result = result +'<div class="text-center"><a href="javascript:void(0)" title="Add Applicant" onclick="getAddApplicant('+opt.id+',\''+opt.reqCoolingOffPeriod+'\');" class="add-applicant"><i class="fa fa-user-plus"></i></a></div>';		        }
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
			      				    		 	result = result +"<div><a href='<c:url value='/releaseRequirementToClientOrVendors?requirementUUID="+opt.requirementUUID+"'/>' title='Release Job'><i class='fa fa-bullhorn success'></i></a></div>"; 
			      			
			      						   	 }else{	
			      								result = result +"<div><a href='<c:url value='/releaseRequirementToClientOrVendors?requirementUUID="+opt.requirementUUID+"'/>' title='Release Job'><i class='fa fa-bullhorn'></i></a></div>"; 
			      						   	 }
			      			       }
			      				   else{
			      			          result = result +"<div>--</div>";
			      				   } 
			                   '</sec:authorize>';    
			      		       result = result +"</div></td>";
				              return result;
			          }
			 }
           ],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[8,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
   } );
   $("#requirementPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
   $("input[type=search]").hide();
   $("#dataTableSearch").keyup(function(e) {
   	   if(e.which === 13) {
   		 requirementList('list',sourceleadUrl);
   	   }
   	});
 }
</script>