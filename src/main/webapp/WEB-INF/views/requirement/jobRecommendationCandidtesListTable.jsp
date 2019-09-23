<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
<script type="text/javascript" src="/resources/js/submissionFailedCandidatesList.js"></script>

<div id="popUp" ></div>
<div id="customizePopUp"></div>
<script type="text/javascript">
		window.onload = function () {
			var requirementId = '${requirementId}';
			var requirementSubmissionStatus = '${requirementSubmissionStatus}';
			requirementRecommendations(requirementId,requirementSubmissionStatus);
		 };
</script> 
<div id="recommendedList" ></div>
<div id="candidateRejectedDiv" class="project_tabs clearfix"></div> 
<div class="success_pop" id="candSelector" style="display: none;"> 
    	 <i class="fa fa-check-circle"></i><span id="candMsgDiv"></span> 
   		</div>
<div id="candidateSubmittedJobsDiv" class="project_tabs clearfix"></div>  

 <script>
	   function candMsgFadein() {
			$("#candSelector").fadeIn(200);
		    $('#candSelector').delay(2000).fadeOut('slow');
		}
   </script>
   
<script>
	function requirementRecommendations(requirementId, requirementSubmissionStatus){
	 	$("#actionsDiv" ).hide();
	 	$("#recommendationsListLoading").removeAttr('style');
		$("#recommendationsListLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		
		 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
		  if(locationList.match("yyyy-MM-dd")){
			  requirementRecommendationsDate = 'YYYY-MM-DD';
		  }if(locationList.match("dd-MM-yyyy")){
			  requirementRecommendationsDate = 'DD-MM-YYYY'; 
		  }if(locationList.match("MM-dd-yyyy")){
			  requirementRecommendationsDate = 'MM-DD-YYYY'; 
		  }if(locationList.match("yyyy-dd-MM")){
			  requirementRecommendationsDate = 'YYYY-DD-MM'; 
		  }
		  getRecommendationList(requirementId,requirementRecommendationsDate, requirementSubmissionStatus);
	}
	
	

 function getRecommendationList(requirementId,requirementRecommendationsDate, requirementSubmissionStatus){
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var searchValue = $("#recommendationTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	var url = sourceleadUrl+"/getRequirementRecommendationsByRequirementId?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&orgLocationId="+orgLocationId+"&searchValue="+searchValue;
	prepareRecommendationList(url,requirementRecommendationsDate,requirementId, requirementSubmissionStatus);
}	
 
function prepareRecommendationList(url,requirementRecommendationsDate,requirementId, requirementSubmissionStatus) {
	var result = '';
	result = result +" <div class='clearfix'>";
	result  = result + '<div class="success_pop" id="selector4" style="display: none;">';
    result  = result + '<i class="fa fa-check-circle"></i><span id="eduMsgDiv"></span>';
    result  = result + '</div>';
    
 	result = result +" <div class='remove_search'>";
 	result = result +" <input type='text' placeholder='Search' id='recommendationTableSearch'/>";
 	result = result +"</div>";
 	result = result +"</div>";
		 result = result +' <div class="clients_side_wrap">';
		 result = result +'<div class="clients_side_wrap_inn quickviewTabs">';
		 result = result +'<div id="candidateCompare" ></div>';
		 result = result + '<ul class="nav nav-tabs">';
		 result = result + '<input type="hidden"  id="requirementId" name="requirementId" value="'+requirementId+'"/>';
		 result = result +' <li id="quickviewTabsqv" class="active"><a data-toggle="tab" href="#quickresume_view">Quickview</a></li>';
		 result = result +'<li id="quickviewTabsdetails"><a data-toggle="tab" href="#detailrecommondations">Detailed view</a></li>';
		 result = result +'</ul>';
	     result = result +'<div class="tab-content">';
		 
		 result = result +'<div id="quickresume_view" class="tab-pane fade in active">';
		 
		 result = result +'<div id="recapplicantQuickViewDiv"></div>';
		 
		 result = result +'</div>';
		 
		 result = result +'<div id="detailrecommondations" class="tab-pane fade">';			 
		 
		 result = result +' <div class="project_side_wrap_inn_tabs"> ';
		 result = result + '<div id="candidateSideDiv"></div>';
		 result = result +'</div>';
		 result = result +'</div>';
		 result = result +'</div>';

		 result = result +'</div>';
		 result = result +'</div>';
		 
	    //--------------------------------
	    //result = result +'<div class="timesheet_head candidateHead recruitmentHead">';
	    result = result +'<div class="clearfix timesheetHead">';
	    result = result +'<div class="rec_head">';
	    if(requirementSubmissionStatus != 'SUBMITTED' && requirementSubmissionStatus != 'CLOSED' && requirementSubmissionStatus != 'REJECTED' &&  requirementSubmissionStatus != 'CANCEL' && requirementSubmissionStatus != 'DRAFT' && requirementSubmissionStatus != 'ONHOLD' && requirementSubmissionStatus != 'FILLEDCLOSED'  && requirementSubmissionStatus != 'UNFILLEDCLOSED'){
		    result = result +'		   <div class="pull-left cand-advsrch">																							';
		    result = result +'		    <a href="javascript:void(0)" class="btn btn-primary btn-sm" title="" onClick="openPop('+requirementId+')">Customize Score </a>            ';
		    result = result + '<input type="hidden"  id="compareCandListMap" name="compareCandListMap"/>';
		    result = result +'			<a class="comTextLi" title="Share" onclick="shareAllRecommendations('+requirementId+');" ><i class="fa fa-share-alt"></i></a>            ';
		    result = result +'			<a class="comTextLi" title="Submit" onclick="submitRecommendationCandidates('+requirementId+');" ><i class="fa fa-user-plus"></i></a>';
		    result = result +'<input type="hidden" value="N" name="candidateResume"  id="candidateResume">';
		    result = result +'		        </div>';
	    }
	    result = result +'                   <div id="customizeScoreDiv"></div>';
	    result = result + '<div id="attachResumeDiv"></div>';
	    
	    result = result +'</div>';
	    result = result +'</div>';
	    result = result +'<div class="project_tabs_inn_table  clients_wrap_table datatable_filter">';
	    result = result +'<table id="recommendationTable" class="display" cellspacing="0" width="100%"">';
	    result = result +'<div id="recommendationsListLoading">';
	    result = result +'<thead>';
	    result = result +'<tr>';
	    result = result +'<th class="text-center"><input type="checkbox" id="sharedcheckbox" onclick="toggleShareRecommendation(this);"/></th>'; 
	    result = result +'<th>Name</th>';
	    result = result +'<th>Email</th>';
	    result = result +'<th>City</th>';
	    result = result +'<th>Phone</th>';
	    result = result +'<th style="width:50px;">Resume</th>';
	    result = result +'<th>Number Of Jobs</th>';
	    result = result +'<th>Updated Date <br>(<small class="custom-small">'+requirementRecommendationsDate+'</small>)</th>';
	    result = result +'<th>Score</th>';
	    result = result +'<th>Action</th>';
	    result = result +'</tr>';
	    result = result +'</thead>';
	    result = result + "</table>";
	    result = result +'</div>';
	    $("#recommendedList").empty();
		$("#recommendedList").append(result);
		
		recommendationpagination(url,requirementId, requirementSubmissionStatus);
		  $("#recommendationsListLoading").removeAttr("style");
		compareCandidates = {};

}


	
function recommendationpagination(url,requirementId, requirementSubmissionStatus){
		var colIndex = 0;
		 var dt = $('#recommendationTable').DataTable( { 
	    	"processing": true,
	        "serverSide": true,
	        "searching": false,
	        "ajax": $.fn.dataTable.pipeline( {
	            url: url,
	            method: 'POST',
	            pages: 10
	        }),
	        "columns": [
	        	{ "data": "" },
	            { "data": "firstName" },
	            { "data": "email"},
	            { "data": "city" },
	            { "data": "mobilePhone"},
	            { "data": "currentResumeId" },
	            { "data": "noOfReq" },
	            { "data": "updatedDate" },
	            { "data": "finalScore" },
	            { "data": "action" }
	        ],
	        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"sortable": false,
	    	  	"data": "",
	    	  	"render": function(data, type, report, meta) {
	    	  		 var result = "";
	    	  		 var flag = false;
	    	  		
	        			 
   	        		 for(var e in compareCandidates) {
	   	     	    	   if(!e || !compareCandidates[e] || compareCandidates[e] === "")
	   	     				   continue;
	   	     	    	   if(e === report.candidateId+"") {
	   	     	    		    if(!$("#sharedcheckbox").is(":checked"))
	   	     	    		 	checkOrUncheckParent("recommendationTable",compareCandidates, "sharedcheckbox"); 
	   	     	    	   	flag = true;
	   	     	    	   	break;
	   	     	    	   }
	   	        		 }
	   	        		 if(!flag)
	   	        			$("#sharedcheckbox").removeAttr('checked');
	   	        		 
	   	        		 if(report.isCandidateSubmitted == 'N'){
	   	        			result = result +'  <td><input'+(flag ? " checked='checked'" : "")+' class="contactscheckbox" name="sharedcheckbox"  type="checkbox" id="'+report.candidateId+'" data-id="'+report.candidateId+'" value="'+report.candidateId+'" onchange="checkShareRecommendation(this)"/></td>';
	   	        		 }else{
	   	        			result = result +'  <td>--</td>';
	   	        		 }
   	        			
	    	  		 return result; 
	    	  	}
	        	},{
		    	  	"targets": colIndex++,
		    	  	"data": "firstName",
		    	  	"render": function(data, type, report, meta) {
		    	  		var result = "";
		    		    if(report.firstName != null && report.firstName != '' && report.firstName != 'undefiend'){
		    		    	firstName = report.firstName;
		    		    }else{
		    		    	firstName = "";
		    		    } 
		    		    if(report.lastName != null && report.lastName != '' && report.lastName != 'undefiend'){
		    		    	lastName = report.lastName;
		    		    }else{
		    		    	lastName = "";
		    		    } 
		    		    result = result +'<tr>';
		    		    result = result + '<td><div onclick="recomendcandidateQuickViewProfile('+report.candidateId+','+report.finalScore+'); clientSideBar('+report.candidateId+','+report.finalScore+');" class="proj_show tbldata-dotdot"><span title="'+firstName+" "+lastName+'">'+firstName+" "+lastName+'</span><i class="fa fa-binoculars"></i>';
		    		    if(report.finalScore != null && report.finalScore != '' && report.finalScore != 'undefined'){
		    				  if(report.finalScore >=50){ 
		    				 	 result = result +'<small class="valid-profile validProfile fa fa-check-square text-success"></small>';        
		    				  }
		    			  } 
		    		    result = result +' </div></td>';
			              return  result;
		      	}
	        	},{
	            "targets": colIndex++,
	            "data": "email",
	            "render": function ( data, type, report, meta ) {
	            	  var result = "";
	            	    if(report.email != null && report.email !='' && report.email !='undefined'){
	        		    	result = result +'<td><div title='+report.email+' class="tbldata-dotdot-vsm">'+report.email+'</div></td>';
	        		    }else{
	        		    	result = result +'<td><div><span>--</span></div></td>';
	        		    }
		              return result; 
	            }
	          }, {
	              "targets": colIndex++,
	              "data": "city",
	              "render": function ( data, type, report, meta ) {
	            	  var result = "";
	            	   if(report.city != null && report.city !='' && report.city !='undefined'){
	       		    	result = result +'<td><div class="tbldata-dotdot-100">'+report.city+'</div></td>';
	       		    }else{
	       		    	result = result +'<td><div><span>--</span></div></td>';
	       		    }
	  	              return result; 
	              }
	            }, {
	                "targets": colIndex++,
	                "data": "mobilePhone",
	                "render": function ( data, type, report, meta ) {
	               	  var result = "";
	                	  if(report.mobilePhone != null && report.mobilePhone !='' && report.mobilePhone !='undefined'){
	          		    	result = result +'<td><div class="tbldata-dotdot-100">'+report.mobilePhone+'</div></td>';
	          		    }else{
	          		    	result = result +'<td><div><span>--</span></div></td>';
	          		    }
	    	              return result; 
	                }
	          },{
		    	  	"targets": colIndex++,
		    	  	"data": "currentResumeId",
		    	  	"sortable": false,
		    	  	"render": function(data, type, report, meta) {
		    	  	 	var result = "";
		    	  		if(report.currentResumeId != null && report.currentResumeId !='' && report.currentResumeId !='undefined' ){

		    			  	 result = result +'<td class="text-center"><div><span title="Download"><a download class="glyphicon glyphicon-download-alt" title="Download" href=\"'+report.fileUrl+'\"></a>';
		    			  	 result = result +'<a class="fa fa-eye ml10"title="View" onclick="viewFile(\''+report.fileUrl+'\',\''+report.resumeName+'\',\''+report.fileContentType+'\');"></a></span></div></td>';

		    				  }else{
		    					result = result +'<td class="text-center"><div><span>--</span></div></td>';
		    				} 
		    	  	   return result; 
		    	  	}
		    	  },{
		    	  	"targets": colIndex++,
		    	  	"data": "noOfReq",
		    	  	"render": function(data, type, report, meta) {
		    	  		var result = "";
		    	  		
		    	  		if(report.noOfReq == 0){
		    	  			result = result +'<td class="text-center"><div class="show-tooltip"><span>'+report.noOfReq+'</span>';
		    	  		}else{
		    	  			result = result +'<td class="text-center"><div class="show-tooltip"><a href="#" onclick="getSubmittedJobsList('+report.candidateId+')">'+report.noOfReq+'</div></a>';
		    	  		}
		    	  		
		    		    result = result +' </div></td>';
		    	  		 return result; 
		    	  	}
		    	  },{
			    	  	"targets": colIndex++,
			    	  	"data": "updatedDate",
			    	  	"render": function(data, type, report, meta) {
			    	  	var result = "";
			    	  	  if(report.updatedDate != null && report.updatedDate !='' && report.updatedDate !='undefined'){
			  		    	result = result +'<td><div class="tblminwidth-100"><span>'+report.updatedDate+'</span></div></td>';
			  		    }else{
			  		    	result = result +'<td><div><span>--</span></div></td>';
			  		    }
			    	  		 return result; 
			    	  	}
			      },{
				    	  	"targets": colIndex++,
				    	  	"data": "finalScore",
				    	  	"sortable": false,
				    	  	"render": function(data, type, report, meta) {
				    	  		 var result = "";
				    	  		 result = result +'<td class="text-center"><div class="show_scroe"><span>'+report.finalScore+'%</span>';
				    			    result = result +'<div class="scroe">';
				    			    
				    			    result = result +'<div class="clearfix row">';
				    			    
				    			    result = result +'<div class="col-md-6 paddingRight">';
				    			    result = result +'<label>Skills</label>';
				    			    result = result +'<div class="progress">';
				    			    var skillGraph = (100 * report.skillPercentage)/report.totalSkillPercentage;
				    			    result = result +'<div class="progress-bar progress-bar-success" role="progressbar"  aria-valuemin="0" aria-valuemax="100" style="width:'+skillGraph+'%"> '+report.skillPercentage+'%';
				    	   	        result = result +'</div>';
				    	   	        result = result +'</div>';
				    			    result = result +' </div>';
				    			    
				    			    result = result +'<div class="col-md-6 paddingLeft">';
				    			    result = result +'<label>Location</label>';
				    			    result = result +'<div class="progress">';
				    			    var locationGraph = (100 * report.locationPercentage)/report.totalLocationPercentage;
				    			    result = result +'<div class="progress-bar progress-bar-warning" role="progressbar"  aria-valuemin="0" aria-valuemax="100" style="width:'+locationGraph+'%"> '+report.locationPercentage+'% ';
				    			    result = result +'</div>';
				    			    result = result +'</div>';
				    			    result = result +' </div>';
				    			    
				    			    result = result +' </div>';
				    			    
				    			    result = result +'<div class="clearfix row">';
				    			    
				    			    result = result +'<div class="col-md-6 paddingRight">';
				    			    result = result +'<label>Experience</label>';
				    			    result = result +'<div class="progress">';
				    			    var exGraph = (100 * report.expPercentage)/report.totalExpPercentage;
				    			    result = result +'<div class="progress-bar progress-bar-info" role="progressbar"  aria-valuemin="0" aria-valuemax="100" style="width:'+exGraph+'%"> '+report.expPercentage+'% ';
				    	   	        result = result +'</div>';
				    			    result = result +' </div>';
				    			    result = result +' </div>';
				    			    
				    			    result = result +'<div class="col-md-6 paddingLeft">';
				    			    result = result +'<label>Rate</label>';
				    			    result = result +'<div class="progress">';
				    			    var rateGraph =  (100 * report.ratePercentage)/report.totalRatePercentage;
				    			    result = result +'<div class="progress-bar progress-bar-warning" role="progressbar"  aria-valuemin="0" aria-valuemax="100" style="width:'+rateGraph+'%"> '+report.ratePercentage+'% ';
				    	   	        result = result +'</div>';
				    			    result = result +' </div>';
				    			    result = result +' </div>';
				    			    
				    			    result = result +' </div>';
				    			    
				    			    result = result +'<div class="clearfix row">';
				    			    
				    			    result = result +'<div class="col-md-6 paddingRight">';
				    			    result = result +'<label>Notice Period</label>';
				    			    result = result +'<div class="progress">';
				    			    var noticePeriodGraph = (100 * report.noticePeriodPercentage)/report.totalNoticePeriodPercentage;
				    			    result = result +'<div class="progress-bar progress-bar-danger" role="progressbar"  aria-valuemin="0" aria-valuemax="100" style="width:'+noticePeriodGraph+'%"> '+report.noticePeriodPercentage+'% ';
				    	   	        result = result +'</div>';
				    			    result = result +' </div>';
				    			    result = result +' </div>';
				    			    
				    			    result = result +'<div class="col-md-6 paddingLeft">';
				    			    result = result +'<label>Keyword</label>';
				    			    result = result +'<div class="progress">';
				    			    var keywordGraph = (100 * report.totalKeyWordPerPercentage)/report.keyWordPerPercentage;
				    			    result = result +'<div class="progress-bar progress-bar-primary" role="progressbar"  aria-valuemin="0" aria-valuemax="100" style="width:'+keywordGraph+'%"> '+report.totalKeyWordPerPercentage+'% ';
				    	   	        result = result +'</div>';
				    			    result = result +' </div>';
				    			    result = result +' </div>';
				    			    
				    			    result = result +' </div>';
				    			    
				    	 			result = result +'<div class="clearfix row">';
				    			    
				    			    result = result +'<div class="col-md-6 paddingRight">';
				    			    result = result +'<label>Visa Type</label>';
				    			    result = result +'<div class="progress">';
				    			    var visaTypeGraph = (100 * report.visaTypePercentage)/report.totalVisaTypePercentage;
				    			    result = result +'<div class="progress-bar progress-bar-success" role="progressbar"  aria-valuemin="0" aria-valuemax="100" style="width:'+visaTypeGraph+'%"> '+report.visaTypePercentage+'% ';
				    	   	        result = result +'</div>';
				    			    result = result +' </div>';
				    			    result = result +' </div>';
				    			    
				    			    result = result +'<div class="col-md-6 paddingLeft">';
				    			    result = result +'<label>Education</label>';
				    			    result = result +'<div class="progress">';
				    			    var educationGraph = (100 * report.educationPercentage)/report.totalEducationPercentage;
				    			    result = result +'<div class="progress-bar progress-bar-primary" role="progressbar"  aria-valuemin="0" aria-valuemax="100" style="width:'+educationGraph+'%"> '+report.educationPercentage+'% ';
				    	   	        result = result +'</div>';
				    			    result = result +' </div>';
				    			    result = result +' </div>';
				    			    
				    			    result = result +' </div>';
				    			  
				    			    result = result +'</div>';		    
				    			    result = result +'</div></td>';
				    	  		 return result; 
				    	  	}
				   },{
					    	  	"targets": colIndex++,
					    	  	"data": "action",
					    	  	"sortable": false,
					    	  	"render": function(data, type, report, meta) {
					    	  		 var result = "";
					    	  		 if(report.isCandidateSubmitted == 'N'){
					    	  			if(requirementSubmissionStatus != 'SUBMITTED' && requirementSubmissionStatus != 'CLOSED' && requirementSubmissionStatus != 'REJECTED' &&  requirementSubmissionStatus != 'CANCEL' && requirementSubmissionStatus != 'DRAFT' && requirementSubmissionStatus != 'ONHOLD' && requirementSubmissionStatus != 'FILLEDCLOSED'  && requirementSubmissionStatus != 'UNFILLEDCLOSED'){
					    			    	result = result +'<div class="pull-left ml5"><a href="javascript:void(0)" title="Share" onclick="shareRecomendedApplicant('+requirementId+','+report.candidateId+');" class="add-applicant fa fa-share-alt"></a></div>';
					    			    	result = result +'</td>';
					    	  			}
					    			 }else{
					    			    	result = result +'<td class="text-center"><div class="text-success">Added</div>';
					    			 }
					    	  		 result = result +'</tr>';
					    	  		 return result; 
					  }
					  }],
		    	"scrollCollapse": false,
		    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
		    	"aaSorting": [[1,'desc']],
		    	"bLengthChange": true,
		    	"bDestroy": true,
		    	"ordering": true,
		    	"initComplete": function( settings,json ) {
		    		 if(cacheLastJson != null && cacheLastJson !=  '' && cacheLastJson !=  'undefined' && cacheLastJson.data.length > 0){
		    			 var map = new Object();
		    			 var td = cacheLastJson.data;
		    			 for(var i in td)
		    			 {
		    				 if(td[i].lastName != null && td[i].lastName !='' && td[i].lastName !='undefined'){
		    				     map[td[i].candidateId] = td[i].firstName +" "+ td[i].lastName;
		    				 }else{
		    					 map[td[i].candidateId] = td[i].firstName;
		    				 }
		    			 }
		    			 $("input[name='compareCandListMap']").val(JSON.stringify(map));
		    		 }
		    	 },
		    	"oLanguage": {
		     		"sSearch": "",
		     		 "sZeroRecords": "", "sEmptyTable": ""
		     		
		     }
		
	    } );
	    $("#recommendationTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
	    $("input[type=search]").hide();
	    $("#recommendationTableSearch").keyup(function(e) {
	    	   if(e.which === 13) {
	    		   requirementRecommendations(requirementId);;
	    	   }
	    	});
	   
} 


</script>

<script>
function getSubmittedJobsList(candidateId){
	$.ajax({
		  type: "GET",
		    url: sourceleadUrl+"/getCandidateSubmittedJobsList?candidateId="+candidateId,
		     success :function(response) {
		    	    $("#candidateSubmittedJobsDiv").empty();  
		    	    $("#candidateSubmittedJobsDiv").append(response);
		    	    $(".modal.paymentaccount-modal").fadeIn();
		    	    $(".comMask").fadeIn();
				},
			});
}
</script>

<script>
function maxAlertMessage(){ 
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Allowed only 4</p>";
	result = result + ' <div id="reasonForInvoiceDiv"/>';
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="cancelTermination();"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function cancelTermination(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}

function shareRecomendedApplicant(requirementId,candidateId){
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
    	type: "POST",
        url: sourceleadUrl+"/shareRecomendedApplicant?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&candidateId="+candidateId, 
        success: function(data) {
        	if (data.response === "FAILED") {
	    		showError(data.responseMessage);
	    	} else if (data.response === "AWKWARD") {
	    		location.href = "/errorPage";
	    	} else if (data.response === "SUCCESS") {
	        	$("#selector4").removeAttr("style");
	   			$("#selector4").css({ display: "block" });
	   			$("#eduMsgDiv").html("You have successfully shared the job with selected candidate(s)");
	   			eduMsgFadeinFunction();
	    	}else if (data.indexOf('Sign In') !== -1) {
	    		location.replace(sourceleadUrl);
	    	} else {
	    		showError("Operation failed, please try again");
	    	}
        },
    });
}
function toggleShareRecommendation(source){
	  var checkboxes = document.querySelectorAll('input[class="contactscheckbox"]');
	  if($(source).is(":checked"))
		  $('input[class="contactscheckbox"]').prop('checked', 'checked');
	  else 
		  $('input[class="contactscheckbox"]').removeProp('checked');
	  
	  $('input[class="contactscheckbox"]').each(function(i) {
		  if($(this).is(":checked")) {
		      compareCandidates[$(this).data("id")] = $(this).val();
		  } else {
			  compareCandidates[$(this).data("id")] = "";
		  }
	  });
}

function checkOrUncheckParent(tableId, selectedItems, checkboxId) {
	$("#"+checkboxId).removeProp("checked");
	   var count = 0;
	   var data = $("#"+tableId).DataTable().data();
	 for(var e in selectedItems) {
	   if(!e || !selectedItems[e] || selectedItems[e] === "")
		   continue;
	   
	   for(var f in data) {
   	   if(e === data[f].id+"") {
   		count ++;
   	   }
		}
	 }
	 if(count == data.count()) {
		$("#"+checkboxId).prop("checked", "checked");
	 }
}
</script>
<script>
var compareCandidates = {};
function checkShareRecommendation(elem) {
	if($(elem).is(":checked")) {
		compareCandidates[$(elem).data("id")] = $(elem).val();
	} else{
		$("#sharedcheckbox").removeProp('checked');
		compareCandidates[$(elem).data("id")] = "";
	}
}
</script>

<script>
function submitRecommendationCandidates(requirementId){
	
	var candidateIds = new Array();
	 for(var e in compareCandidates) {
		 if(!e || !compareCandidates[e] || compareCandidates[e] === "")
			   continue;
		 candidateIds.push(compareCandidates[e]);
	 }
	 if(candidateIds.length > 0){ 
		var result = "";
		result = result +" <div class='comMask'></div>";
		result = result +" <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
		 result = result +" <div class='mr pull-left mt5'>";
		 result = result +'<label class="switch mr">';
		 result = result +'<input type="checkbox" onclick="checkResume();" value="N" id="applicantResume">';
		 result = result +'&nbsp;&nbsp;<div class="slider round" title="Attach Applicants Resume"></div>';
		 result = result +'</label>';
		 result = result +"<span class='paddingLeft mt2'>Attach Resume</span></div>";
		 result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		 result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='submitRecommendationCandidatesToJob("+requirementId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelSubmitApplicant();'></b>";
		 result = result +"	</div>";
		 $("#attachResumeDiv").append(result);
		 $("#remove_user_pop").slideDown(200);
		 $(".comMask").fadeIn(200);
	 
	 
}else{
	    var result = "";
		result = result +"	<div class='project_inn_mask'></div>"; 
		result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Please select at least one Candidate</p>";
		result = result + ' <div id="reasonForInvoiceDiv"/>';
		result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="cancelTermination();"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
		result = result +"	</div>";
		$("#popUp").append(result);
		$("#remove_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
}
}
</script>

<script type="text/javascript">
function cancelSubmitApplicant(){
	$("#attachResumeDiv").empty();
	$(".comMask").fadeOut(200);
}
</script>

<script>
function checkResume(){
	 var flag = document.getElementById("applicantResume").checked;
	 if(flag == true){
		 $("#candidateResume").val('Y'); 
	 }else{
		 $("#candidateResume").val('N'); 
	 }
}
</script>

<script>
function submitRecommendationCandidatesToJob(requirementId){
	var sourceleadUrl ='${sourceleadUrl}';
	var candidateIds = new Array();
	 for(var e in compareCandidates) {
		 if(!e || !compareCandidates[e] || compareCandidates[e] === "")
			   continue;
		 candidateIds.push(compareCandidates[e]);
	 }
	 
	 var candidateResume = $("#candidateResume").val(); 
	 if(candidateIds.length > 0){
		 $("#recommendationsListLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		  $.ajax({
	          type : "POST",
	          url: sourceleadUrl+"/submitRecommendationCandidates?"+tokenParamName+"="+tokenValue+"&candidateIds="+candidateIds+"&requirementId="+requirementId+"&candidateResume="+candidateResume,    
	           success :function(data) {
	        	   $("#recommendationsListLoading").removeAttr("style");
	        	   if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
			    		
			    		candMsgFadein();
			    		var requirementId = '${requirementId}';
						var requirementSubmissionStatus = '${requirementSubmissionStatus}';
						requirementRecommendations(requirementId,requirementSubmissionStatus);
						
			    		var submittedlength = '';
		       			if(data.responseObject.length > 0){
		       				 submittedlength = candidateIds.length - data.responseObject.length;
		       				failedCandidates.getFailuredCandidates(data.responseObject);
		       			}else{
		       				 submittedlength = candidateIds.length;
		       			}
		       			
		       			if(submittedlength > 0){
		       				$("#candMsgDiv").html(submittedlength+" Candidate(s) Submitted Successfully...");
		       			}
			    		
			    	}else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
	           },
	   }); 
	 }
}
</script>

<script>
function shareAllRecommendations(requirementId){
	var sourceleadUrl ='${sourceleadUrl}';
	var candidateIds = new Array();
	 for(var e in compareCandidates) {
		 if(!e || !compareCandidates[e] || compareCandidates[e] === "")
			   continue;
		 candidateIds.push(compareCandidates[e]);
	 }
	 if(candidateIds.length > 0){
			$("#recommendationsListLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 $.ajax({
	          type : "POST",
	          url: sourceleadUrl+"/shareAllRecommendations?"+tokenParamName+"="+tokenValue+"&candidateIds="+candidateIds+"&requirementId="+requirementId,    
	           success :function(data) {
	        	   $("#recommendationsListLoading").removeAttr("style");
	        	   if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
		        	    $("#selector4").removeAttr("style");
		       			$("#selector4").css({ display: "block" });
		       			$("#eduMsgDiv").html("Great ! you have successfully shared this job");
		       			eduMsgFadeinFunction();
		       			toggleShareRecommendation();
			    	}else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
	           },
	   });  
	 }else{
		 var result = "";
			result = result +"	<div class='project_inn_mask'></div>"; 
			result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Please select at least one Candidate</p>";
			result = result + ' <div id="reasonForInvoiceDiv"/>';
			result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="cancelTermination();"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
			result = result +"	</div>";
			$("#popUp").append(result);
			$("#remove_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	 }
}
</script>
<script>
	function eduMsgFadeinFunction() {
		$("#selector4").fadeIn(200);
        $('#selector4').delay(2000).fadeOut('slow');
	}
</script>


<script>
	// Close Advanced Search
		function closePop(){
			$("div.cand-advsrch-popup").hide();
			$("#customizeScoreDiv").hide();
		}
		function openPop(requirementId){
			customizeScore(requirementId);
		}
	</script> 
	
	<script type="text/javascript">
function customizeScore(requirementId){
	$("#customizeScoreDiv").show();
	multicities = {}; 
	var loc = "apiLocation";
	 $.ajax({
		   	type: "GET",
		    url: sourceleadUrl+"/getCustomizeScore?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId, 	   
		    success: function(data) {
		    	var json = data.object;
		    	if (data.response === "FAILED") {
		    		showError(data.responseMessage);
		    	} else if (data.response === "AWKWARD") {
		    		location.href = "/errorPage";
		    	} else if (data.response === "SUCCESS") {
		    	if(json.customizeMinExperience != null && json.customizeMinExperience != '' && json.customizeMinExperience != 'undefiend'){
		    		customizeMinExperience = json.customizeMinExperience;
		    	}else{
		    		customizeMinExperience = "";
		    	}
		    	
		    	if(json.customizeMaxExperience != null && json.customizeMaxExperience != '' && json.customizeMaxExperience != 'undefiend'){
		    		customizeMaxExperience = json.customizeMaxExperience;
		    	}else{
		    		customizeMaxExperience = "";
		    	}
		    	
		    	if(json.customizeRate != null && json.customizeRate != '' && json.customizeRate != 'undefiend'){
		    		customizeRate = json.customizeRate;
		    	}else{
		    		customizeRate = "";
		    	}
		    	
		    	if(json.customizeCountry != null && json.customizeCountry !='' && json.customizeCountry !='undefined'){
		    		var customizeCountry = json.customizeCountry;
				}
		    	
				if(json.customizeState != null && json.customizeState !='' && json.customizeState !='undefined'){
					var customizeState = json.customizeState;
				}
				if(json.customizeCity != null && json.customizeCity !='' && json.customizeCity !='undefined'){
					var customizeCity = json.customizeCity;
				}
		    	
		    	//lookupDB skills
		    	var lookupSkills = json.dbSkills;
		    	var dbskills = new Array();
		    	dbskills = lookupSkills.split(",");
		    	
		    	
		    	var result = "";
		    	//result = result +'		          <div class="cand-advsrch-popup customizescore-pop customscorePop">  ';
		    	
		    	result = result +'		          <div class="sideMask" style="display:block;"></div>';
		    	result = result +'		          <div class="modal" style="display:block; z-index:9999">  ';
		    	result = result +'		          <div class="modal-dialog modal-lg">  ';
		    	result = result +'		          <div class="modal-content">  ';
		    	result = result	+ ' <form id="updateCustomize_form"  name="updateCustomize_form" method="post">';
		    	result = result +'		            <div class="row clearfix modal-body"> ';
		        result = result +'		                <div class="clearfix mt">  ';
		        
		        result = result +'		                <div class="col-md-10 lblup paddingRight skills-chosen">    ';
		        result = result +'		                   <label>Skills</label>';
		        result = result +'		                    <select class="chosen-select" data-placeholder="Skills" multiple id="customizeSkills" name="customizeSkills"> ';
		        
		      //requitrement skills
		    	if(json.customizeSkills != null  && json.customizeSkills != '' && json.customizeSkills != 'undefiend'){
		    		var requirmentSkills = json.customizeSkills;
			    	var customizeSkills = new Array();
			    	customizeSkills = requirmentSkills.split(",");
			        for (var i = 0; i < customizeSkills.length; i++) { 
			        	 var customSkill = customizeSkills[i];
			        	 result = result +'	<option selected value=\''+customSkill+'\'>'+customSkill+'</option> ';
			        }
		    	}
		         for (var i = 0; i < dbskills.length; i++) { 
		        	 var skill1 = dbskills[i];
		        	 result = result +'	<option  value=\''+skill1+'\'>'+skill1+'</option> ';
		        } 
		        result = result +'		                    </select> ';
		        result = result +'		                  </div>   ';
		        
		        result = result +'		                   <div class="col-md-2">';
		        result = result +'		                    <select class="chosen-select" data-placeholder="Skills" id="skillScore" name="skillScore"> ';
		        result = result +'		                      <option disabled>Skills</option>  ';
		        result = result +'		                      <option '+ (json.customizeSkillScore == "0" ? "selected" : '') +' value="0">0%</option> ';
		        result = result +'		                      <option '+ (json.customizeSkillScore == "10" ? "selected" : '') +' value="10">10%</option> ';
		        result = result +'		                      <option '+ (json.customizeSkillScore == "20" ? "selected" : '') +' value="20">20%</option> ';
		        result = result +'		                      <option '+ (json.customizeSkillScore == "30" ? "selected" : '') +' value="30">30%</option> ';
		        result = result +'		                      <option '+ (json.customizeSkillScore == "40" ? "selected" : '') +' value="40">40%</option> ';
		        result = result +'		                      <option '+ (json.customizeSkillScore == "50" ? "selected" : '') +' value="50">50%</option> ';
		        result = result +'		                      <option '+ (json.customizeSkillScore == "60" ? "selected" : '') +' value="60">60%</option> ';
		        result = result +'		                      <option '+ (json.customizeSkillScore == "70" ? "selected" : '') +' value="70">70%</option> ';
		        result = result +'		                      <option '+ (json.customizeSkillScore == "80" ? "selected" : '') +' value="80">80%</option> ';
		        result = result +'		                      <option '+ (json.customizeSkillScore == "90" ? "selected" : '') +' value="90">90%</option> ';
		        result = result +'		                      <option '+ (json.customizeSkillScore == "100" ? "selected" : '') +' value="100">100%</option>';
		        result = result +'		                    </select>              ';
		        result = result +'		                  </div>                   ';
		        result = result +'		           <div class="clearfix"></div>';
		        //result = result +'		                </div> ';
		        
		        result = result +'		                <div class="clearfix mt">   ';
		        /* result = result +'		                  <div class="col-md-3 paddingRight lblup">    ';
		        result = result +'		                   <label>Location</label>';
		        result = result +'		                    <select class="chosen-select" data-placeholder="Country" id="country" name="customizeCountry" value="'+customizeCountry+'" readonly="readonly"> ';
		        result = result +'<option value="">--select Country--</option>';
		        <c:forEach var="cntry" items="${countryList}">
		        if('${cntry.name}' == customizeCountry){
		        	result = result +'		<option value="${cntry.name},${cntry.code2}" selected >${cntry.name}</option>';
		        }else{
		        	result = result +'		<option value="${cntry.name},${cntry.code2}">${cntry.name}</option>';
		        }
		        </c:forEach>
		        result = result +'		                    </select>                ';
		        result = result +'		                  </div>                     ';
		         */
		         
		        result = result +'		                    <div class="col-md-3 paddingRight lblup">   ';
		        result = result +'		                   <label>Choose City</label>';
		        result = result +'                              <input type="text" class="form-control positionRelative"  multiple data-placeholder="select city" multiple name="apiLocation"  id="apiLocation">';
		        result = result +'                              <i class="fa fa-refresh btn btn-primary refresh-icon mr2 ref-top" title="Refresh" onClick="refreshGoogleApiCity(\''+loc+'\');"></i>';
		        result = result +'		                  </div>  ';
		        
		       
		        result = result +'		                  <div class="col-md-7  paddingRight lblup"> '; 
		        result = result +'		                   <label>Selected City</label>';
		        result = result +'		                    <select class="chosen-select" multiple data-placeholder="City" name="customizedCity" id="city" value="'+customizeCity+'" readonly="readonly">    ';
		     	if(json.customizeCity != null  && json.customizeCity != '' && json.customizeCity != 'undefiend'){
		    		var cites = json.customizeCity;
			    	var customizeCites = new Array();
			    	customizeCites = cites.split(",");
			        for (var i = 0; i < customizeCites.length; i++) { 
			        	 var customCity = customizeCites[i];
			        	 result = result +'	<option selected value=\''+customCity+'\'>'+customCity+'</option> ';
			        }
		    	}
		        result = result +'		                    </select>                  ';
		        result = result +'		                  </div>                       ';
		        
		       
		        result = result +'                      <div class="col-md-2"> ';
		        result = result +'		                    <select class="chosen-select" data-placeholder="Location" id="locationScore" name="locationScore">';
		        result = result +'		                      <option '+ (json.customizeLocationScore == "0" ? "selected" : '') +' value="0">0%</option> ';
		        result = result +'		                      <option '+ (json.customizeLocationScore == "10" ? "selected" : '') +' value="10">10%</option> ';
		        result = result +'		                      <option '+ (json.customizeLocationScore == "20" ? "selected" : '') +' value="20">20%</option> ';
		        result = result +'		                      <option '+ (json.customizeLocationScore == "30" ? "selected" : '') +' value="30">30%</option> ';
		        result = result +'		                      <option '+ (json.customizeLocationScore == "40" ? "selected" : '') +' value="40">40%</option> ';
		        result = result +'		                      <option '+ (json.customizeLocationScore == "50" ? "selected" : '') +' value="50">50%</option> ';
		        result = result +'		                      <option '+ (json.customizeLocationScore == "60" ? "selected" : '') +' value="60">60%</option> ';
		        result = result +'		                      <option '+ (json.customizeLocationScore == "70" ? "selected" : '') +' value="70">70%</option> ';
		        result = result +'		                      <option '+ (json.customizeLocationScore == "80" ? "selected" : '') +' value="80">80%</option> ';
		        result = result +'		                      <option '+ (json.customizeLocationScore == "90" ? "selected" : '') +' value="90">90%</option> ';
		        result = result +'		                      <option '+ (json.customizeLocationScore == "100" ? "selected" : '') +' value="100">100%</option>';
		        result = result +'		                    </select>                 ';
		        result = result +'		                  </div>                      ';
		        result = result +'		           <div class="clearfix"></div>';
// 		        result = result +'		                </div>                        ';
		        
		        result = result +'				        <div class="clearfix mt"> ';
		        result = result +'		                  <div class="col-md-10 lblup paddingRight">    ';
		        result = result +'		                   <label>Notice Period</label>';
		        result = result +'		                      <select class="chosen-select" data-placeholder="Notice" multiple id="noticeperiod" name="noticeperiod">';
		      //split the noticeperiod
		      var noticeperiod = new Array();
		    	if(json.customizeNoticeperiod != null && json.customizeNoticeperiod != '' && json.customizeNoticeperiod != 'undefined'){
		    		var customizeNoticeperiod = json.customizeNoticeperiod;
			    	noticeperiod =  customizeNoticeperiod.split(",");
		    	}
	        	 result = result  +' <option value="Immediately Available" '+(noticeperiod.includes("Immediately Available") ? "selected" : '') +'>Immediately available</option>';
	        	 result = result  +' <option value="15 Days" '+(noticeperiod.includes("15 Days") ? "selected" : '') +'>15 Days</option>';
	        	 result = result  +' <option value="1 Month" '+(noticeperiod.includes("1 Month") ? "selected" : '') +'>1 Month<s/option>';
	        	 result = result  +' <option value="2 Months" '+(noticeperiod.includes("2 Months") ? "selected" : '') +'>2 Months</option>';
	        	 result = result  +' <option value="3 Months" '+(noticeperiod.includes( "3 Months") ? "selected" : '') +'>3 Months</option>';
	        	 $("#noticeperiod").empty();
	        	 $("#noticeperiod").append(result);
   			     $("#noticeperiod").trigger('chosen:update');
		        result = result +'		                    </select> ';
		        result = result +'		                    </div>  ';
		        
		        result = result +'		                    <div class="col-md-2">  ';
		        result = result +'		                    <select class="chosen-select" data-placeholder="Rate" id="noticeperiodScore" name="noticeperiodScore">';
		        result = result +'		                      <option '+ (json.customizeNoticeperiodScore == "0" ? "selected" : '') +' value="0">0%</option> ';
		        result = result +'		                      <option '+ (json.customizeNoticeperiodScore == "10" ? "selected" : '') +' value="10">10%</option> ';
		        result = result +'		                      <option '+ (json.customizeNoticeperiodScore == "20" ? "selected" : '') +' value="20">20%</option> ';
		        result = result +'		                      <option '+ (json.customizeNoticeperiodScore == "30" ? "selected" : '') +' value="30">30%</option> ';
		        result = result +'		                      <option '+ (json.customizeNoticeperiodScore == "40" ? "selected" : '') +' value="40">40%</option> ';
		        result = result +'		                      <option '+ (json.customizeNoticeperiodScore == "50" ? "selected" : '') +' value="50">50%</option> ';
		        result = result +'		                      <option '+ (json.customizeNoticeperiodScore == "60" ? "selected" : '') +' value="60">60%</option> ';
		        result = result +'		                      <option '+ (json.customizeNoticeperiodScore == "70" ? "selected" : '') +' value="70">70%</option> ';
		        result = result +'		                      <option '+ (json.customizeNoticeperiodScore == "80" ? "selected" : '') +' value="80">80%</option> ';
		        result = result +'		                      <option '+ (json.customizeNoticeperiodScore == "90" ? "selected" : '') +' value="90">90%</option> ';
		        result = result +'		                      <option '+ (json.customizeNoticeperiodScore == "100" ? "selected" : '') +' value="100">100%</option>';
		        result = result +'		                    </select>               ';
		        result = result +'		                  </div>                    ';
		        result = result +'		                </div>                      ';
		        
		        result = result +'		                <div class="clearfix mt">';
		        result = result +'		                  <div class="col-md-5 lblup"> ';
		        result = result +'		                   <label>Experience</label>';
		        result = result + '<select  class="chosen-select input-sm form-control" data-placeholder="Min Experience" name="minExperience"  id="minExperience" onblur="minMaxExperience();" field-name="Experience Years">';
				result = result + '<option value="">Min Experience</option>';
				<c:forEach var="i" begin="0" end="24" >
					result = result  +'<option value="${i}" '  + (customizeMinExperience == "${i}" ? "selected" : '') +  '>${i}</option>';
				</c:forEach>
				result = result + '</select>';
		        result = result +'		                    </div> ';
		        
		        
		        result = result +'		                  <div class="col-md-5 paddingRight"> ';
		        result = result + '<select  class="chosen-select input-sm form-control" data-placeholder="Max Experience" name="maxExperience"  onblur="minMaxExperience();" id="maxExperience" field-name="Experience Years">';
				result = result + '<option value="">Max Experience</option>';
				<c:forEach var="i" begin="0" end="25" >
					result = result  +'<option value="${i}" '  + (customizeMaxExperience == "${i}" ? "selected" : '') +  '>${i}</option>';
				</c:forEach>
				result = result + '</select>';
		        result = result +'		                    </div>  ';
		        
		        result = result +'		                    <div class="col-md-2"> ';
		        result = result +'		                    <select class="chosen-select" data-placeholder="Experience" id="experienceScore" name="experienceScore"> ';
		        result = result +'		                      <option '+ (json.customizeExperienceScore == "0" ? "selected" : '') +' value="0">0%</option> ';
		        result = result +'		                      <option '+ (json.customizeExperienceScore == "10" ? "selected" : '') +' value="10">10%</option> ';
		        result = result +'		                      <option '+ (json.customizeExperienceScore == "20" ? "selected" : '') +' value="20">20%</option> ';
		        result = result +'		                      <option '+ (json.customizeExperienceScore == "30" ? "selected" : '') +' value="30">30%</option> ';
		        result = result +'		                      <option '+ (json.customizeExperienceScore == "40" ? "selected" : '') +' value="40">40%</option> ';
		        result = result +'		                      <option '+ (json.customizeExperienceScore == "50" ? "selected" : '') +' value="50">50%</option> ';
		        result = result +'		                      <option '+ (json.customizeExperienceScore == "60" ? "selected" : '') +' value="60">60%</option> ';
		        result = result +'		                      <option '+ (json.customizeExperienceScore == "70" ? "selected" : '') +' value="70">70%</option> ';
		        result = result +'		                      <option '+ (json.customizeExperienceScore == "80" ? "selected" : '') +' value="80">80%</option> ';
		        result = result +'		                      <option '+ (json.customizeExperienceScore == "90" ? "selected" : '') +' value="90">90%</option> ';
		        result = result +'		                      <option '+ (json.customizeExperienceScore == "100" ? "selected" : '') +' value="100">100%</option>';
		        result = result +'		                    </select>              ';
		        result = result +'		                  </div>                   ';
		        result = result +'<p class="jquery_form_error_message" id="experiencevalidation"> </p>';
		        
		        result = result +'<div id="expId">';
		        result = result +'<input type="hidden" name="expValidation" id="expValidation">';
		        result = result +'</div>';		        
		        result = result +'		                </div>                     ';
		        
		        
		        result = result +'		                  <div class="clearfix mt"> ';
		        result = result +'		                  <div class="col-md-10 lblup paddingRight">   ';
		        result = result +'		                   <label>Visa Type</label>';
		        result = result +'		                     <select class="chosen-select" data-placeholder="Visa Type" multiple id="visaType" name="visaType">';
		        if(json.customizeVisatype != null  && json.customizeVisatype != '' && json.customizeVisatype != 'undefiend'){
		    		var visaType = json.customizeVisatype;
			    	var customizeVisaType = new Array();
			    	customizeVisaType = visaType.split(",");
			        for (var i = 0; i < customizeVisaType.length; i++) { 
			        	 var customizeVisaTypes = customizeVisaType[i];
			        	 result = result +'	<option selected value=\''+customizeVisaTypes+'\'>'+customizeVisaTypes+'</option> ';
			        }
		    	}
		        
		        var lookupVisaType = json.dbVisaType;
		    	var dbVisaType = new Array();
		    	dbVisaType = lookupVisaType.split(",");
		    	
		    	 for (var i = 0; i < dbVisaType.length; i++) { 
		        	 var visatype = dbVisaType[i];
		        	 result = result +'	<option  value=\''+visatype+'\'>'+visatype+'</option> ';
		        } 
		        result = result +'	</select> ';
		  
		        result = result +'		                    </div>  ';
		        result = result +'		                    <div class="col-md-2">  ';
		        result = result +'		                    <select class="chosen-select" data-placeholder="Visa Type Score" id="visaTypeScore" name="visaTypeScore" > ';
		        result = result +'		                      <option '+ (json.customizeVisatypecore == "0" ? "selected" : '') +' value="0">0%</option> ';
		        result = result +'		                      <option '+ (json.customizeVisatypecore == "10" ? "selected" : '') +' value="10">10%</option> ';
		        result = result +'		                      <option '+ (json.customizeVisatypecore == "20" ? "selected" : '') +' value="20">20%</option> ';
		        result = result +'		                      <option '+ (json.customizeVisatypecore == "30" ? "selected" : '') +' value="30">30%</option> ';
		        result = result +'		                      <option '+ (json.customizeVisatypecore == "40" ? "selected" : '') +' value="40">40%</option> ';
		        result = result +'		                      <option '+ (json.customizeVisatypecore == "50" ? "selected" : '') +' value="50">50%</option> ';
		        result = result +'		                      <option '+ (json.customizeVisatypecore == "60" ? "selected" : '') +' value="60">60%</option> ';
		        result = result +'		                      <option '+ (json.customizeVisatypecore == "70" ? "selected" : '') +' value="70">70%</option> ';
		        result = result +'		                      <option '+ (json.customizeVisatypecore == "80" ? "selected" : '') +' value="80">80%</option> ';
		        result = result +'		                      <option '+ (json.customizeVisatypecore == "90" ? "selected" : '') +' value="90">90%</option> ';
		        result = result +'		                      <option '+ (json.customizeVisatypecore == "100" ? "selected" : '') +' value="100">100%</option>';
		        result = result +'		                    </select> ';
		        result = result +'		                  </div>  ';
		        result = result +'		                </div>  ';
		      
		        
		        
		        
		        result = result +'		                  <div class="clearfix mt"> ';
		        result = result +'		                  <div class="col-md-10 lblup paddingRight">   ';
		        result = result +'		                   <label>Education</label>';
		        result = result +'		                     <select class="chosen-select" data-placeholder="Education" multiple id="education" name="education">';
		        if(json.customizeEducation != null  && json.customizeEducation != '' && json.customizeEducation != 'undefiend'){
		    		var education = json.customizeEducation;
			    	var customizeEducation = new Array();
			    	customizeEducation = education.split(",");
			        for (var i = 0; i < customizeEducation.length; i++) { 
			        	 var customizeEducations = customizeEducation[i];
			        	 result = result +'	<option selected value=\''+customizeEducations+'\'>'+customizeEducations+'</option> ';
			        }
		    	}
		        
		        var lookupEducation = json.dbEducation;
		    	var dbEducation = new Array();
		    	dbEducation = lookupEducation.split(",");
		    	
		    	 for (var i = 0; i < dbEducation.length; i++) { 
		        	 var education = dbEducation[i];
		        	 result = result +'	<option  value=\''+education+'\'>'+education+'</option> ';
		        } 
		        result = result +'		                    </select> ';
		        result = result +'		                    </div>  ';
		        result = result +'		                    <div class="col-md-2">  ';
		        result = result +'		                    <select class="chosen-select" data-placeholder="Education" id="educationScore" name="educationScore" > ';
		        result = result +'		                      <option '+ (json.customizeEducationScore == "0" ? "selected" : '') +' value="0">0%</option> ';
		        result = result +'		                      <option '+ (json.customizeEducationScore == "10" ? "selected" : '') +' value="10">10%</option> ';
		        result = result +'		                      <option '+ (json.customizeEducationScore == "20" ? "selected" : '') +' value="20">20%</option> ';
		        result = result +'		                      <option '+ (json.customizeEducationScore == "30" ? "selected" : '') +' value="30">30%</option> ';
		        result = result +'		                      <option '+ (json.customizeEducationScore == "40" ? "selected" : '') +' value="40">40%</option> ';
		        result = result +'		                      <option '+ (json.customizeEducationScore == "50" ? "selected" : '') +' value="50">50%</option> ';
		        result = result +'		                      <option '+ (json.customizeEducationScore == "60" ? "selected" : '') +' value="60">60%</option> ';
		        result = result +'		                      <option '+ (json.customizeEducationScore == "70" ? "selected" : '') +' value="70">70%</option> ';
		        result = result +'		                      <option '+ (json.customizeEducationScore == "80" ? "selected" : '') +' value="80">80%</option> ';
		        result = result +'		                      <option '+ (json.customizeEducationScore == "90" ? "selected" : '') +' value="90">90%</option> ';
		        result = result +'		                      <option '+ (json.customizeEducationScore == "100" ? "selected" : '') +' value="100">100%</option>';
		        result = result +'		                    </select> ';
		        result = result +'		                  </div>  ';
		        result = result +'		                </div>  ';
		      
		        
		        result = result +'		           <div class="clearfix mt">     ';
		        
		        
		        result = result +'		                  <div class="col-md-5 lblup">   ';
		        result = result +'		                   <label>Rate</label>';
		        result = result +'		                    <input type="text" placeholder="Rate" class="form-control input-sm" value="'+customizeRate+'" id="customizeRate" name="customizeRate" data-validation="validate_Space validate_float validate_length length1-8" data-validation-optional="true">  ';
		        result = result +'		                    </div>   ';
		        
		        result = result +' <div class="col-md-5 paddingRight">   ';
		        result = result + '<select  class="input-sm form-control" name="currentRateType"  id="currentRateType" field-name="Rate Type">';
				result = result + '<option value="">Rate Per</option>';
				result = result + ' <option '+(json.customizeRatetype == "Hourly" ? "selected" : '') +'>Hourly</option>';
				result = result + '<option  '+ (json.customizeRatetype == "Weekly" ? "selected" : '') +'>Weekly</option>';
				result = result + '<option  '+(json.customizeRatetype == "Bi-weekly" ? "selected" : '') +'>Bi-weekly</option>';
				result = result + '<option  '+(json.customizeRatetype == "Semi-Monthly" ? "selected" : '') +'>Semi-Monthly</option>';
				result = result + '<option  '+(json.customizeRatetype == "Monthly" ? "selected" : '') +'>Monthly</option>';
				result = result + '<option  '+(json.customizeRatetype == "Bi-Monthly" ? "selected" : '') +'>Bi-Monthly</option>';
				result = result + '<option '+(json.customizeRatetype == "Yearly" ? "selected" : '') +'>Yearly</option>';
				result = result + ' </select>';
		        result = result +'	  </div>   ';
		        
		        result = result +'		                    <div class="col-md-2">      ';
		        result = result +'		                    <select class="chosen-select" data-placeholder="Rate" id="rateScore" name="rateScore"> ';
		        result = result +'		                      <option '+ (json.customizeRateScore == "0" ? "selected" : '') +' value="0">0%</option> ';
		        result = result +'		                      <option '+ (json.customizeRateScore == "10" ? "selected" : '') +' value="10">10%</option> ';
		        result = result +'		                      <option '+ (json.customizeRateScore == "20" ? "selected" : '') +' value="20">20%</option> ';
		        result = result +'		                      <option '+ (json.customizeRateScore == "30" ? "selected" : '') +' value="30">30%</option> ';
		        result = result +'		                      <option '+ (json.customizeRateScore == "40" ? "selected" : '') +' value="40">40%</option> ';
		        result = result +'		                      <option '+ (json.customizeRateScore == "50" ? "selected" : '') +' value="50">50%</option> ';
		        result = result +'		                      <option '+ (json.customizeRateScore == "60" ? "selected" : '') +' value="60">60%</option> ';
		        result = result +'		                      <option '+ (json.customizeRateScore == "70" ? "selected" : '') +' value="70">70%</option> ';
		        result = result +'		                      <option '+ (json.customizeRateScore == "80" ? "selected" : '') +' value="80">80%</option> ';
		        result = result +'		                      <option '+ (json.customizeRateScore == "90" ? "selected" : '') +' value="90">90%</option> ';
		        result = result +'		                      <option '+ (json.customizeRateScore == "100" ? "selected" : '') +' value="100">100%</option>';
		        result = result +'		                    </select>               ';
		        result = result +'		                  </div>                    ';
		        result = result +'		                </div>                      ';
		       
		        result = result +'		                  <div class="clearfix mt"> ';
		        result = result +'		                  <div class="col-md-10 lblup paddingRight">   ';
		        result = result +'		                   <label>Keywords</label>';
		        result = result +'		                     <select class="chosen-select" data-placeholder="Keywords" multiple id="anyKeyWord" name="anyKeyWord">';
		      //Split the keyWords
		    	if(json.customizeKeywords != null && json.customizeKeywords != '' && json.customizeKeywords != 'undefined'){
		    		var customizeKeywords = json.customizeKeywords;
			    	var Keywords = new Array();
			    	Keywords =  customizeKeywords.split(",");
			        for (var i = 0; i < Keywords.length; i++) { 
			        	 var customKeywords = Keywords[i];
			        	 result = result +'	<option selected value='+customKeywords+'>'+customKeywords+'</option> ';
			        }
		    	}
		        result = result +'		                    </select> ';
		        result = result +'		                    </div>  ';
		        result = result +'		                    <div class="col-md-2">  ';
		        result = result +'		                    <select class="chosen-select" data-placeholder="KeyWords" id="keywordScore" name="keywordScore" > ';
		        result = result +'		                      <option '+ (json.customizeKeywordsScore == "0" ? "selected" : '') +' value="0">0%</option> ';
		        result = result +'		                      <option '+ (json.customizeKeywordsScore == "10" ? "selected" : '') +' value="10">10%</option> ';
		        result = result +'		                      <option '+ (json.customizeKeywordsScore == "20" ? "selected" : '') +' value="20">20%</option> ';
		        result = result +'		                      <option '+ (json.customizeKeywordsScore == "30" ? "selected" : '') +' value="30">30%</option> ';
		        result = result +'		                      <option '+ (json.customizeKeywordsScore == "40" ? "selected" : '') +' value="40">40%</option> ';
		        result = result +'		                      <option '+ (json.customizeKeywordsScore == "50" ? "selected" : '') +' value="50">50%</option> ';
		        result = result +'		                      <option '+ (json.customizeKeywordsScore == "60" ? "selected" : '') +' value="60">60%</option> ';
		        result = result +'		                      <option '+ (json.customizeKeywordsScore == "70" ? "selected" : '') +' value="70">70%</option> ';
		        result = result +'		                      <option '+ (json.customizeKeywordsScore == "80" ? "selected" : '') +' value="80">80%</option> ';
		        result = result +'		                      <option '+ (json.customizeKeywordsScore == "90" ? "selected" : '') +' value="90">90%</option> ';
		        result = result +'		                      <option '+ (json.customizeKeywordsScore == "100" ? "selected" : '') +' value="100">100%</option>';
		        result = result +'		                    </select> ';
		        result = result +'		                  </div>  ';
		        result = result +'		                </div>  ';
		        result = result +'		                </div>  ';
		        result = result +'		                </div>  ';
		      
		        
		        
		        
// 		        =========================================================================================================================
		        
		        
		        result = result +'		              </div>                          ';
		        
		        result = result +'		            <div class="form-footer clearfix">                                              ';
		        result = result +'		              <div class="pull-right mr"> <a href="javascript:void(0)" title="Submit" class="btn btn-sm btn-primary" onclick="updateCutomize('+requirementId+');">Apply</a> <a href="javascript:void(0)" title="Cancel" class="btn btn-sm btn-default ml10" onclick="closePop();">Cancel</a> </div>';
		        result = result +'		            </div>'; 
		        result = result	+ '               <form>';
		        result = result +'		            </div>'; 
		        
		        $("#customizeScoreDiv").empty();
		    	$("#customizeScoreDiv").append(result);
// 		    	getAllCountries();
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
		    	
		    	    $("#country").chosen();
		            $("#state").chosen();
		            $("#city").chosen();
		            
		            /* $("#country").on('change', function (e) {
		            	getcites('apiLocation','city');
				    	  $("#apiLocation").val('');
				    	  $("#city").val('').trigger("chosen:updated");
				    	  getStates();
		            }); */
		            getcites('apiLocation','city','recommendation');
		            
		    			$('.chosen-select').each(function() {
		    				var $this = $(this);
		    				$this.next().css({'width': '100%'});
		    		   });

		    			 // here call experience validation
		 		       $("#maxExperience").on('change', function (e) {
		 		    	   minMaxExperience();
		 		       });
		 		       
		 		       $("#minExperience").on('change', function (e) {
		 		    	   minMaxExperience();
		 		       });
		 		       
			          //Adding Customized Skills Keyword to the list 
		 		      tagIt("customizeSkills", 'Press Enter-Key Or Comma(,) To Add New Skill:', 'tagItBykeyForSkills','skills','Skills');
			          
		    		  //Adding Customized Educaton to the list 
		 		      tagIt("education", 'Press Enter-Key Or Comma(,) Or Tab To Add New Education:', 'tagItBykeyForSkills','skills','Education');

		 		       
		    		  //Adding Customized Any Of Keyword to the list 
		    		  tagIt("anyKeyWord", 'Press Enter-Key Or Comma(,) To Add Keyword:', 'tagItBykeyForSkills','skills','Keywords');
		    		     
			   		  tagIt("city", 'Press Enter-Key Or Comma(,) Or Space To Add New Cites:', 'tagItBykeyForSkills','skills','sletected cites');
		    	}else if (data.indexOf('Sign In') !== -1) {
		    		location.replace(sourceleadUrl);
		    	} else {
		    		showError("Operation failed, please try again");
		    	}
		    }
	 });
	
}
</script>

<script>
//Function to validate if maximum exp value is more the minimum exp value
function minMaxExperience(){
    var maxExp = ($('#maxExperience').val());
    var minExp = ($('#minExperience').val());
    if (parseFloat(maxExp) <= parseFloat(minExp)){
        $('#expId').find('p.jquery_form_error_message').remove(); 
		$('input#expValidation').attr('class','error signin_fild');
		$("input#expValidation").after("<p class='jquery_form_error_message'>Max Experience should be greater than Min Experience</p>");
		$('input#expValidation').attr('record-exist','yes');
		$('input#expValidation').attr('record-exist-errorMsg','Max Experience should be greater than Min Experience'); 
    }  else{
    	$('#expId').find('p.jquery_form_error_message').remove(); 
    	$('input#expValidation').removeAttr( "class record-exist record-exist-errormsg");
		$('#expId').find('p.jquery_form_error_message').remove();
    }
}

</script>


<script>
function updateCutomize(requirementId){
	minMaxExperience();
    var skillPerc = parseInt($("#skillScore").val());
    var expPerc = parseInt($("#experienceScore").val());
    var ratePerc = parseInt($("#rateScore").val());
    var noticePerc = parseInt($("#noticeperiodScore").val());
    var kewordsPerc = parseInt($("#keywordScore").val());
    var locationScore = parseInt($("#locationScore").val());
    var educationScore = parseInt($("#educationScore").val());
    var visaTypwScore = parseInt($("#visaTypeScore").val());
    var totalscore = (skillPerc+expPerc+noticePerc+ratePerc+locationScore+kewordsPerc+educationScore+visaTypwScore);
    
    
    if(totalscore>100){
        var result = "";
		//result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='greater_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Score is greater than 100</p>";
		result = result +"		<p><strong>Score must be equal to 100</strong></p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b><input type='button' value='Ok' onclick='cancelCustomize();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelCustomize();'></b>";
		result = result +"	</div>";
		$("#customizePopUp").append(result);
		$("#customizePopUp").show();
		$("#greater_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
		
        }else if(totalscore<100){
        	var result = "";
    		//result = result +"	<div class='project_inn_mask'></div>";
    		result = result +"	   <div id='less_user_pop' class='remove_user_pop remove_user_pop_a'>";
    		result = result +"		<p>Score is less than 100</p>";
    		result = result +"		<p><strong>Score must be equal to 100</strong></p>";
    		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
    		result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancelCustomize();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelCustomize();'></b>";
    		result = result +"	</div>";
    		$("#customizePopUp").append(result);
    		$("#customizePopUp").show();
    		$("#less_user_pop").slideDown(200);
    		$(".project_inn_mask").fadeIn(200);
        }else if(totalscore=100){
           	if ($('#updateCustomize_form').validate(false, validationSettings)){
        	var frm = $('#updateCustomize_form').serializeFormJSON();
    		var con = JSON.stringify(frm);
    		con = con.replace(/[{}]/g, "");
    		//It will escape all the special characters
    		var jsonData = encodeURIComponent(con);
    		 $.ajax({
    					type : "POST",
    					url : sourceleadUrl + "/updateCustomizeScore?"+tokenParamName+"="+tokenValue+"&json="+ jsonData + "&requirementId=" + requirementId,
    					success : function(data) {
    						if (data.response === "FAILED") {
    				    		showError(data.responseMessage);
    				    	} else if (data.response === "AWKWARD") {
    				    		location.href = "/errorPage";
    				    	} else if (data.response === "SUCCESS") {
	    						if(data.object == "success"){
        							requirementRecommendations(requirementId);	
        							closePop();
	    						}
    				    	}else if (data.indexOf('Sign In') !== -1) {
    				    		location.replace(sourceleadUrl);
    				    	} else {
    				    		showError("Operation failed, please try again");
    				    	}
    					}
    				}); 
    		 return true;
       	  }else{ 
			return false;
		 } 
       } 
	
}
$('body').on('blur', '#updateCustomize_form', function() {
	$("#updateCustomize_form").showHelpOnFocus();
	$("#updateCustomize_form").validateOnBlur(false, validationSettings);
});
</script>
<script>
function cancelCustomize(){
	$(".project_inn_mask").fadeOut();
	$("#customizePopUp").toggle();
}
</script>
