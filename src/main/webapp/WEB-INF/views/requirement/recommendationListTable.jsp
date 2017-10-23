<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
<jsp:include page="addApplicant.jsp" />
<jsp:include page="compareCandidatesProfile.jsp"></jsp:include>

<script>
	function requirementRecommendations(requirementId){
	 	$("#actionsDiv" ).hide();
	 	$("#recommendationsLoading").removeAttr('style');
		$("#recommendationsLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		
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
		  getRecommendationList(requirementId,requirementRecommendationsDate);
	}
	
	

 function getRecommendationList(requirementId,requirementRecommendationsDate){
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var searchValue = $("#recommendationTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	var url = sourceleadUrl+"/getRequirementRecommendationsByRequirementId?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&orgLocationId="+orgLocationId+"&searchValue="+searchValue;
	prepareRecommendationList(url,requirementRecommendationsDate,requirementId);
}	
 
function prepareRecommendationList(url,requirementRecommendationsDate,requirementId) {
	var result = '';
	result = result +" <div class='clearfix'>";
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

	    result = result +'		   <div class="pull-left cand-advsrch">																							';
	    result = result +'		    <a href="javascript:void(0)" class="btn btn-primary btn-sm" title="" onClick="openPop('+requirementId+')">Customize Score </a>            ';
		    	result = result + '<input type="hidden"  id="compareCandListMap" name="compareCandListMap"/>';
		    	result = result +'			<a class="comTextLi" title="Compare" onclick="compareRecommendedUsers('+requirementId+');" >Compare</a>            ';
	    result = result +'		        </div>';
	    result = result +'                   <div id="customizeScoreDiv"></div>';
	    
	    result = result +'</div>';
	    result = result +'</div>';
	    result = result +'<div class="project_tabs_inn_table  clients_wrap_table">';
		
	    result = result +'<table id="recommendationTable" class="display" cellspacing="0" width="100%"">';
	    result = result +'<thead>';
	    result = result +'<tr>';
	    result = result +'<th></th>';
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
		$("#tab-2").empty(); 
	    $("#tab-3").empty();
	    $("#tab-4").empty();
	    $("#recommendationsLoading").removeAttr("style");
	    $("#status-div").empty();
	    $("#actionsDiv").hide();
	    $("#status-data").empty();
		$("#tab-3").append(result);
		
		recommendationpagination(url,requirementId);
		$("#tab-3").show();
		$(".project-tabs-menu a").click(function(event) {
		     event.preventDefault();
		     $(this).parent().addClass("current");
		     $(this).parent().siblings().removeClass("current");
		     var tab = $(this).attr("href");
		     $(".project_tabs_inn").not(tab).css("display", "none");
		     $(tab).fadeIn();
		 });
}


	
function recommendationpagination(url,requirementId){
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
	    	  			 if(report.candidateId+"" === compareCandidates[e]) {
	    	  				 flag = true;
	    	  				 break;
	    	  			 }
	    	  		 }
	    	  		 result = result + '<td><input'+(flag ? " checked='checked'" : "")+' onchange="compareCandidateCheck(this)" type="checkbox" value='+report.candidateId+' name="compareCandi" id="compareCandiId"  ></td>';
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

		    			  	 result = result +'<td class="text-center"><div><span title="Download"><a class="glyphicon glyphicon-download-alt" href=\"'+report.fileUrl+'\"></a>';
		    			  	 result = result +'<a class="fa fa-eye ml10" onclick="viewFile(\''+report.fileUrl+'\',\''+report.resumeName+'\',\''+report.fileContentType+'\');"></a></span></div></td>';

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
		    	  		result = result +'<td class="text-center"><div class="show-tooltip"><span>'+report.noOfReq+'</span>';
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
					    			    	result = result +'<td class="text-center"><div id="recommendedCandidate'+report.candidateId+'"><a href="javascript:void(0)" title="" onclick="submitRecomendedApplicant('+requirementId+','+report.candidateId+');" class="add-applicant">Add</a></div>';
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
		    				 map[td[i].candidateId] = td[i].firstName + td[i].lastName;
		    			 }
		    			 $("input[name='compareCandListMap']").val(JSON.stringify(map));
		    		 }
		    	 },
		    	"oLanguage": {
		     		"sSearch": "",
		     		 "sZeroRecords": "", "sEmptyTable": ""
		     		
		     }
		
	    } );
	    //$("#recommendationTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
	    $("input[type=search]").hide();
	    $("#recommendationTableSearch").keyup(function(e) {
	    	   if(e.which === 13) {
	    		   requirementRecommendations(requirementId);;
	    	   }
	    	});
	   
} 

var compareCandidates = [];
function compareCandidateCheck(ele) {
	if($(ele).is(":checked")) {
		if (compareCandidates.length < 4) {
	        compareCandidates[compareCandidates.length] = $(ele).val();
	    }else{
		   $(ele).prop('checked', false)
		   alert("Allowed only 4");
	    }
	} else {
		var d = [];
		var i = 0;
		for(var e in compareCandidates) {
			if(compareCandidates[e] === $(ele).val()) {
				continue;
			}
			d[i++] = compareCandidates[e];			
		}
		
		compareCandidates = d;
	}
}
</script>


