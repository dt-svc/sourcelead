<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
	<script type="text/javascript" src="/resources/js/submissionFailedCandidatesList.js"></script>
	
<%-- <jsp:include page="addApplicant.jsp" />
<jsp:include page="compareCandidatesProfile.jsp"></jsp:include> --%>

<div id="popUp" ></div>
<div id="candidateRejectedDiv" class="project_tabs clearfix"></div>
<div id="candidateSubmittedJobsDiv" class="project_tabs clearfix"></div>  
<div id="popUpDeleteCart"></div>
<script>
function globalDataRecommendations(){
	//$("#actionsDiv" ).hide();
 	//$("#globalRecommendationsLoading").removeAttr('style');
	//$("#globalRecommendationsLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	getGlobalRecommendationList();
}
function getGlobalRecommendationList(){
	var result = '';
	result = result +" <div class='clearfix'>";
	    //--------------------------------
	    //result = result +'<div class="timesheet_head candidateHead recruitmentHead">';
	    result = result +" <div class='remove_search'>";
	 	result = result +" <input type='text' placeholder='Search' id='globalRecommendationTableSearch'/>";
	 	result = result +"</div>";
	    result = result +'<div class="clearfix timesheetHead">';
	    result = result +'<div class="rec_head">';
	   /* result = result +'		   <div class="pull-left cand-advsrch">																							';
	    result = result +'		    <a href="javascript:void(0)" class="btn btn-primary btn-sm" title="" onClick="openPop()">Customize Score <i class="fa fa-info-circle" title="Customise Score based on your requirement"></i>             ';
	    result = result +'</div>'; */
	    result = result +'</div>';
	    result = result +'</div>';
	    result = result +'<div class="project_tabs_inn_table  clients_wrap_table backgroundWhite">';
	    result = result +'<table id="globalRecommendationTable" class="display" cellspacing="0" width="100%"">';
	    result = result +'<thead>';
	    result = result +'<tr>';
	    result = result +'<th>Name</th>';
	    result = result +'<th>Designation</th>';
	    result = result +'<th>Skills</th>';
	    result = result +'<th>Current Rate</th>';
	    result = result +'<th>Expected Rate</th>';
	    result = result +'<th>Experience</th>';
	    result = result +'<th>Location</th>';
	    result = result +'<th>Profile Percentage</th>';
	    result = result +'<th>Price</th>';
	    result = result +'<th>Action</th>';
	    result = result +'</tr>';
	    result = result +'</thead>';
	    result = result + "</table>";
	    result = result +'</div>';
		$("#tab-2").empty(); 
	    $("#tab-3").empty();
	    $("#tab-4").empty();
	  	//$("#globalRecommendationsLoading").removeAttr("style");
	    $("#status-div").empty();
	    $("#actionsDiv").hide();
	    $("#status-data").empty();
		$("#tab-5").empty().append(result);
		globalRecommendationpagination();
}

function globalRecommendationpagination(){
	var searchValue = $("#globalRecommendationTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	//$("#globalRecommendationTableSearch").val(searchValue);
	var url = sourceleadUrl+"/getGlobalDataRecommendations?searchValue="+searchValue;
	var colIndex = 0;
	 var dt = $('#globalRecommendationTable').DataTable( { 
   	"processing": true,
       "serverSide": true,
       "searching": false,
       "ajax": $.fn.dataTable.pipeline( {
           url: url,
           method: 'POST',
           pages: 10
       }),
       "columns": [
           { "data": "name" },
           { "data": "designation"},
           { "data": "skills" },
           { "data": "currentRate"},
           { "data": "expectedRate" },
           { "data": "experience" },
           { "data": "location" },
           { "data": "profilePercentage" },
           { "data": "price" },
           { "data": "action" }
       ],
       "columnDefs": [{
	    	  	"targets": colIndex++,
	    	  	"data": "name",
	    	  	"render": function(data, type, report, meta) {
	    	  		var fullName = "";
	    	  		var result = "";
	    		    if(report.fullName != null && report.fullName != '' && report.fullName != 'undefiend'){
	    		    	fullName = report.fullName;
	    		    }else{
	    		    	fullName = "";
	    		    } 
	    		    result = result +'<tr>';
	    		    result = result + '<td><span title="'+fullName+'">'+fullName+'</span></i>';
	    		    result = result +' </div></td>';
		            return  result;
	      	}
       	},{
           "targets": colIndex++,
           "data": "designation",
           "render": function ( data, type, report, meta ) {
           	  var result = "";
           	    if(report.designation != null && report.designation !='' && report.designation !='undefined'){
       		    	result = result +'<td><div class="tbldata-dotdot-vsm"> <span title="'+report.designation+'">'+report.designation+'</span></div></td>';
       		    }else{
       		    	result = result +'<td><div><span>--</span></div></td>';
       		    }
	              return result; 
           }
         }, {
             "targets": colIndex++,
             "data": "skills",
             "render": function ( data, type, report, meta ) {
           	  var result = "";
           	   if(report.skillsName != null && report.skillsName !='' && report.skillsName !='undefined'){
      		    	result = result +'<td><div class="tbldata-dotdot-100"> <span title="'+report.skillsName+'">'+report.skillsName+'</span></div></td>';
      		    }else{
      		    	result = result +'<td><div><span>--</span></div></td>';
      		    }
 	              return result; 
             }
           }, {
               "targets": colIndex++,
               "data": "currentRate",
               "render": function ( data, type, report, meta ) {
              	  var result = "";
              	  var currentRateCurrency = "";
              	  var currentRate = "";
               	  if(report.currentRateCurrency != null && report.currentRateCurrency !='' && report.currentRateCurrency !='undefined'){
               			currentRateCurrency = report.currentRateCurrency;
         		    }else{
         		    	currentRateCurrency = '<span>--</span>';
         		    }
	               	if(report.currentRate != null && report.currentRate !='' && report.currentRate !='undefined'){
	               		currentRate = report.currentRate;
	     		    }else{
	     		    	currentRate = '<span>--</span>';
	     		    }
	               	result = result + '<td><div class="tbldata-dotdot-100"><span title="'+currentRateCurrency +" "+currentRate+'">'+currentRateCurrency +" "+currentRate+'</span></div></td>';
   	              return result; 
               }
         },{
	    	  	"targets": colIndex++,
	    	  	"data": "expectedRate",
	    	  	"render": function(data, type, report, meta) {
	    	  		var result = "";
	              	  var expectedRateCurrencey = "";
	              	  var expectedRate = "";
	               	  if(report.expectedRateCurrencey != null && report.expectedRateCurrencey !='' && report.expectedRateCurrencey !='undefined'){
	               		expectedRateCurrencey = report.expectedRateCurrencey;
	         		    }else{
	         		    	expectedRateCurrencey = '<span>--</span>';
	         		    }
		               	if(report.expectedRate != null && report.expectedRate !='' && report.expectedRate !='undefined'){
		               		expectedRate = report.expectedRate;
		     		    }else{
		     		    	expectedRate = '<span>--</span>';
		     		    }
		               	result = result + '<td><div class="tbldata-dotdot-100"> <span title="'+expectedRateCurrencey +" "+expectedRate+'">'+expectedRateCurrencey +" "+expectedRate+'</span></div></td>';
	   	              return result;  
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "experience",
	    	  	"render": function(data, type, report, meta) {
	    	  		var result = "";
	              	  var experienceYears = "";
	              	  var experienceMonths = "";
	               	  if(report.experienceYears != null && report.experienceYears !='' && report.experienceYears !='undefined'){
	               		experienceYears = report.experienceYears;
	         		    }else{
	         		    	experienceYears = '<span>--</span>';
	         		    }
		               	if(report.experienceMonths != null && report.experienceMonths !='' && report.experienceMonths !='undefined'){
		               		experienceMonths = report.experienceMonths;
		     		    }else{
		     		    	experienceMonths = '<span>--</span>';
		     		    }
		               	result = result + '<td><div class="tbldata-dotdot-100" <span title="'+experienceYears +"."+experienceMonths+'">'+experienceYears +"."+experienceMonths+'</span></div></td>';
	   	              return result; 
	    	  	}
	    	  },{
		    	  	"targets": colIndex++,
		    	  	"data": "location",
		    	  	"render": function(data, type, report, meta) {
		    	  	var result = "";
		    	  	  if(report.location != null && report.location !='' && report.location !='undefined'){
		  		    	result = result +'<td><div class="tblminwidth-100"><span>'+report.location+'</span></div></td>';
		  		    }else{
		  		    	result = result +'<td><div><span>--</span></div></td>';
		  		    }
		    	  		 return result; 
		    	  	}
		      },{
			    	  	"targets": colIndex++,
			    	  	"data": "profilePercentage",
			    	  	"render": function(data, type, report, meta) {
			    	  		var result = "";
				    	  	  if(report.profilePercentage != null && report.profilePercentage !='' && report.profilePercentage !='undefined'){
				  		    	result = result +'<td><div class="tblminwidth-100"><span title="'+report.profilePercentage+'">'+report.profilePercentage+'</span></div></td>';
				  		    }else{
				  		    	result = result +'<td><div><span>--</span></div></td>';
				  		    }
				    	  		 return result; 
				    	  	}
			   
			   },{
		    	  	"targets": colIndex++,
		    	  	"data": "price",
		    	  	"render": function(data, type, report, meta) {
		    	  		var result = "";
		              	  var marketRateCurrencey = "";
		              	  var marketPrice = "";
		               	  if(report.marketRateCurrencey != null && report.marketRateCurrencey !='' && report.marketRateCurrencey !='undefined'){
		               		marketRateCurrencey = report.marketRateCurrencey;
		         		    }else{
		         		    	marketRateCurrencey = '<span>--</span>';
		         		    }
			               	if(report.marketPrice != null && report.marketPrice !='' && report.marketPrice !='undefined'){
			               		marketPrice = report.marketPrice;
			     		    }else{
			     		    	marketPrice = '<span>--</span>';
			     		    }
			               	result = result + '<td><div class="tbldata-dotdot-100"><span title="'+marketRateCurrencey +" "+marketPrice+'">'+marketRateCurrencey +" "+marketPrice+'</span></div></td>';
		   	              return result;
		    	  	}
		      },{
				    	  	"targets": colIndex++,
				    	  	"data": "action",
				    	  	"sortable": false,
				    	  	"render": function(data, type, report, meta) {
				    	  		var result = '<td><div id="addCartDiv'+report.marketplaceUUID+'">';
				    	  		 if(report.marketCartStatus == "In_Cart"){
				    	  			
				    	  			 result = result + '<sec:authorize access="hasRole(\'ROLE_ADD_PROFILE\')">Added Cart <a href="javascript:void(0)" onclick="deleteCandidateMarketplaceCart(\''+(report.fullName == null ? "---" : report.fullName)+'\', \''+report.marketplaceUUID+'\', this);"> Delete </a></sec:authorize>';
				    	  		 }else{
				    	  			
				    	  			 result = result + '<sec:authorize access="hasRole(\'ROLE_ADD_PROFILE\')"> <a href="javascript:void(0)"  onclick="addCandidateToMarketCart(\''+(report.fullName == null ? "---" : report.fullName)+'\',\''+report.marketplaceUUID+'\', this)">Add Cart</a></sec:authorize>';
				    	  		 }
				    	  		 result = result + '</div></td>';
				    	  		 return result; 
				  }
				  }],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[7,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": ""
	     }
   });
	 $('#globalRecommendationTable').wrap("<div class='width100percnt overflow_Auto'></div>");
	 $("#globalRecommendationTableSearch").keyup(function(e) {
		   if(e.which === 13) {
			   globalRecommendationpagination();
		   }
		});
}

function deleteCandidateMarketplaceCart(candidateName, marketPlaceUUID, obj){ 
	 $(".project_inn_mask").remove();
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='remove_marketCart_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +" <div class='modal-body'><b>Do you want to remove "+candidateName+" from the cart!</b></div>";
		result = result + ' <div id="reasonForTemplateDiv"/>';
		result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onclick='deleteCandidateMarketCart(\""+candidateName+"\",\""+marketPlaceUUID+"\", \""+obj+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelMarketCartPopup();'></b>";
		result = result +"	</div>";
		$("#popUpDeleteCart").empty().append(result);
		$("#remove_marketCart_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
function cancelMarketCartPopup(){
	$("#remove_marketCart_pop").fadeOut(200);
	$(".project_inn_mask").fadeOut(200);
}

function deleteCandidateMarketCart(candidateName, candidateMarketUUID, obj){
	$.ajax({
		type : "GET",
		url : sourceleadUrl + "/deleteCandidateMarketCart?marketUUID="+candidateMarketUUID,
		success : function(result) {
			if (result.response === "AWKWARD") {
                location.href = "/errorPage";
            }else if(result.response === "SUCCESS") {
				//$(obj).closest('tr').find("div[id='addCartDiv"+candidateMarketUUID+"']").empty().append('<a href="javascript:void(0)"  onclick="addCandidateToMarketCart(\''+candidateName+'\',\''+candidateMarketUUID+'\', \''+obj+'\')">Add Cart</a>');
				globalRecommendationpagination();
				updateMarketplaceCartCountAndPrice();
				$("#remove_marketCart_pop").fadeOut(200);
				$(".project_inn_mask").fadeOut(200);
				}else{
	            	 showError("Operation failed, please try again");
	            }
				}
		}); 
	}

function addCandidateToMarketCart(candidateName, UUID, obj) {
	  $.ajax({
	        url: sourceleadUrl + "/addCandidateToMarketCart?id="+UUID,
	        type:'GET',
	        success: function (result) {
	        	 if (result.response === "AWKWARD") {
	                 location.href = "/errorPage";
	             }else if(result.response === "SUCCESS") {
	            	 updateMarketplaceCartCountAndPrice();
		        	$(obj).closest('tr').find("div[id='addCartDiv"+UUID+"']").empty().append('<sec:authorize access="hasRole(\'ROLE_ADD_PROFILE\')"> Added Cart <a href="javascript:void(0)" onclick="deleteCandidateMarketplaceCart(\''+candidateName+'\', \''+UUID+'\',\''+obj+'\');"> Delete </a></sec:authorize>');
	        	}else{
	            	 showError("Operation failed, please try again");
	            }
	        }
	    });
	}

	function requirementRecommendations(requirementId, requirementSubmissionStatus){
	 	$("#actionsDiv" ).hide();
	 	$("#recommendationsLoading").removeAttr('style');
		$("#recommendationsLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		
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
		    result = result +'		    <a href="javascript:void(0)" class="btn btn-primary btn-sm" title="" onClick="openPop('+requirementId+')">Customize Score <i class="fa fa-info-circle" title="Customise Score based on your requirement"></i>             ';
		    result = result + '<input type="hidden"  id="compareCandListMap" name="compareCandListMap"/>';
		    result = result +'			<a class="comTextLi" title="Compare" onclick="compareRecommendedUsers('+requirementId+');" >Compare <i class="fa fa-info-circle" title="Compare Recommended Resumes,Minimum 2 candidates should select to compare"></i> </a>            ';
		    result = result +'			<a class="comTextLi" title="Share" onclick="shareAllRecommendations('+requirementId+');" ><i class="fa fa-share-alt"></i></a>';
		    result = result +'			<a class="comTextLi" title="Submit" onclick="submitRecommendationCandidates('+requirementId+');" ><i class="fa fa-user-plus"></i></a>';
		    result = result +'<input type="hidden" value="N" name="candidateResume"  id="candidateResume">';
		    result = result +'		        </div>';
	    }
	    result = result +'                   <div id="customizeScoreDiv"></div>';
	    result = result + '<div id="attachResumeDiv"></div>';
	    
	    result = result +'</div>';
	    result = result +'</div>';
	    result = result +'<div class="project_tabs_inn_table  clients_wrap_table">';
		
	    result = result +'<table id="recommendationTable" class="display" cellspacing="0" width="100%"">';
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
		$("#tab-2").empty(); 
	    $("#tab-3").empty();
	    $("#tab-4").empty();
	    $("#recommendationsLoading").removeAttr("style");
	    $("#status-div").empty();
	    $("#actionsDiv").hide();
	    $("#status-data").empty();
		$("#tab-3").append(result);
		
		recommendationpagination(url,requirementId, requirementSubmissionStatus);
		compareCandidates = {};
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
					    	  		 var profilePercentage = '${requirementProfilePercentage}';
					    	  		 if(report.isCandidateSubmitted == 'N'){
					    	  			if(requirementSubmissionStatus != 'SUBMITTED' && requirementSubmissionStatus != 'CLOSED' && requirementSubmissionStatus != 'REJECTED' &&  requirementSubmissionStatus != 'CANCEL' && requirementSubmissionStatus != 'DRAFT' && requirementSubmissionStatus != 'ONHOLD' && requirementSubmissionStatus != 'FILLEDCLOSED'  && requirementSubmissionStatus != 'UNFILLEDCLOSED'){
					    	  				 if(report.candidateProfilePercentage >= profilePercentage){
					    	  					result = result +'<td class="tblminwidth-100"><div id="recommendedCandidate'+report.candidateId+'" class="pull-left"><a href="javascript:void(0)" title="Add"   onclick="submitRecomendedApplicant('+requirementId+','+report.candidateId+');" class="add-applicant fa fa-user-plus"></a></div>';
					    	  				 }
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
		    	"aaSorting": [[7,'desc']],
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

var compareCandidates = [];
function compareCandidateCheck(ele) {
	if($(ele).is(":checked")) {
        compareCandidates[compareCandidates.length] = $(ele).val();
		/* if (compareCandidates.length < 4) {
	        compareCandidates[compareCandidates.length] = $(ele).val();
	    }else{
		   $(ele).prop('checked', false)
		   maxAlertMessage();
	    } */
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
	            $("#recommendationsLoading").removeAttr("style");
	        	$("#selector4").removeAttr("style");
	   			$("#selector4").css({ display: "block" });
	   			$("#eduMsgDiv").html("You have successfully shared the job with selected candidate(s)");
	   			eduMsgFadeinFunction();
	   			toggleShareRecommendation();
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
		 $("#recommendationsLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 $.ajax({
	          type : "POST",
	          url: sourceleadUrl+"/submitRecommendationCandidates?"+tokenParamName+"="+tokenValue+"&candidateIds="+candidateIds+"&requirementId="+requirementId+"&candidateResume="+candidateResume,    
	           success :function(data) {
	        	   $("#recommendationsLoading").removeAttr("style");
	        	   if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
			    		
			    		
			    		candMsgFadein();
		       			cancelSubmitApplicant();
		       			requirementRecommendations(requirementId);
		       			
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
			$("#recommendationsLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 $.ajax({
	          type : "POST",
	          url: sourceleadUrl+"/shareAllRecommendations?"+tokenParamName+"="+tokenValue+"&candidateIds="+candidateIds+"&requirementId="+requirementId,    
	           success :function(data) {
	        	   $("#recommendationsLoading").removeAttr("style");
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
