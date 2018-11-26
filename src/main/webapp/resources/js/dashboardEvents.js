//Tested for the user email snigithaparuchuri@businessintelli.com from production dump
//all the details are hard coded belated to above userid

// timesheet counts 

//Tested for the user email snigithaparuchuri@businessintelli.com from production dump
//all the details are hard coded belated to above userid

// timesheet counts 

	
// timesheet end.
	


//contract list functions
// function requirementSubmissionsCount(daterange){
	function requirementSubmissionsCount(daterange){
	$("#dateActiveTab").val(daterange);
	$("#applicantsCountLoading, #submissionsCountLoading, #interviewsCountLoading, #offersCountLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:5; background-image:url("/resources/images/Spinner.gif"); background-position:center; background-repeat:no-repeat; background-size:60px;');
   // var sourceleadUrl ='${sourceleadUrl}';
   // var userId =  '${SourceLeadWeb_user.id}';
   // var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getRequirementSubmissionsCount?daterange="+daterange+"&userId="+userId+"&orgLocationId="+orgLocationId, 
		        success: function(data) {
		        	if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
			        	var json = data.object;
			        	$("#applicantsCountLoading, #submissionsCountLoading, #interviewsCountLoading, #offersCountLoading").removeAttr("style");
			        	 var  submissionsCount = json.submissionCount;
			        	 var  applicantsCount =json.applicantsCount;
			        	 var  interviewsCount =json.interviewsCount;
			        	 var  offersCount = json.offersCount; 
			        	 if(submissionsCount>0){
		                      $("#submissionsCount").text(submissionsCount);
		                      $('#submissionsCount').attr("onClick", "loadRequirementSubmissions(); return false;"); 
		                      $('#subDiv').attr("style", "cursor:pointer"); 
		                  }else{
		                      $("#submissionsCount").html("0");
		                      $("#submissionsCount").removeAttr("onclick");
		                      $("#subDiv").removeAttr("style");
		                  } 
			        	 if(applicantsCount>0){
		                      $("#applicantsCount").text(applicantsCount);
		                      $('#applicantsCount').attr("onClick", "loadRequirementApplicants(); return false;"); 
		                      $('#appDiv').attr("style", "cursor:pointer"); 
		                  }else{
		                      $("#applicantsCount").html("0");
		                      $("#applicantsCount").removeAttr("onclick");
		                      $("#appDiv").removeAttr("style");
		                  }
			        	if(interviewsCount>0){
		                      $("#interviewsCount").text(interviewsCount);
		                      $('#interviewsCount').attr("onClick", "loadRequirementInterviews(); return false;"); 
		                      $('#intDiv').attr("style", "cursor:pointer"); 
		                  }else{
		                      $("#interviewsCount").html("0");
		                      $("#interviewsCount").removeAttr("onclick");
		                      $("#intDiv").removeAttr("style");
		                  }
			        	if(offersCount>0){
		                      $("#offersCount").text(offersCount);
		                      $('#offersCount').attr("onClick", "loadRequirementOffers(); return false;"); 
		                      $('#offDiv').attr("style", "cursor:pointer"); 
		                  }else{
		                      $("#offersCount").html("0");
		                      $("#offersCount").removeAttr("onclick");
		                      $("#offDiv").removeAttr("style");
		                  } 
			    	}else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
		 		}
		  });
		 
		 if(daterange == "today"){
			 $("#jobsLink").attr("href", "<c:url value='/app/jobs'/>");
		 }else{
			 $("#jobsLink").attr("href", "<c:url value='/app/jobs'/>");
		 }
		 
	}// Jobs End
	// Expensive count start.
	function expensesCount(expenseType){
	    //var sourceleadUrl ='${sourceleadUrl}';
			 $.ajax({
			    	type: "GET",
			        url: sourceleadUrl+"/getExpensesCount?expenseType="+expenseType, 
			        success: function(json) {
			        	//var json = JSON.parse(data);
			        	 
			        	 $("#expensesDraftCount").empty();
		        		 $("#expensesSubmittedForApprovalCount").empty();
		        		 $("#expensesApprovedCount").empty();
		        		 $("#expensesRejectCount").empty();
		        		 
		        		 $("#dial1txt").empty();
		        		 $("#dial2txt").empty();
		        		 $("#dial3txt").empty();
		        		 $("#dial4txt").empty();
		        		 
		        		 var  expensesDraftCount = 0;
		        		 if(json.expensesDraftCount != null && json.expensesDraftCount != "" && json.expensesDraftCount != 'undefined'){
		        			 expensesDraftCount = json.expensesDraftCount;
		        		 }
		        		 
			        	 var  expensesSubmittedForApprovalCount = 0;
			        	 if(json.expensesSubmittedForApprovalCount != null && json.expensesSubmittedForApprovalCount != "" && json.expensesSubmittedForApprovalCount != 'undefined'){
			        		 expensesSubmittedForApprovalCount =json.expensesSubmittedForApprovalCount;
		        		 }
			        	 
			        	 var  expensesApprovedCount = 0;
			        	 if(json.expensesApprovedCount != null && json.expensesApprovedCount != "" && json.expensesApprovedCount != 'undefined'){
			        		 expensesApprovedCount =json.expensesApprovedCount;
		        		 }
			        	 
			        	 var  expensesRejectCount = 0;
			        	 if(json.expensesRejectCount != null && json.expensesRejectCount != "" && json.expensesRejectCount != 'undefined'){
			        		 expensesRejectCount = json.expensesRejectCount;
		        		 }
			        	 
			        	 var  empExpensesSubmittedForApprovalCount = 0;
			        	 if(json.empExpensesSubmittedForApprovalCount != null && json.empExpensesSubmittedForApprovalCount != "" && json.empExpensesSubmittedForApprovalCount != 'undefined'){
			        		 empExpensesSubmittedForApprovalCount = json.empExpensesSubmittedForApprovalCount;
		        		 }
			        	 
			        	 var  empExpensesApprovedCount = 0;
			        	 if(json.empExpensesApprovedCount != null && json.empExpensesApprovedCount != "" && json.empExpensesApprovedCount != 'undefined'){
			        		 empExpensesApprovedCount =json.empExpensesApprovedCount;
		        		 }
			        	 
			        	 var  empExpensesRejectedCount = 0;
			        	 if(json.empExpensesRejectedCount != null && json.empExpensesRejectedCount != "" && json.empExpensesRejectedCount != 'undefined'){
			        		 empExpensesRejectedCount =json.empExpensesRejectedCount;
		        		 }
			        	 
			        	// var  empExpensesReopenCount = json.empExpensesReopenCount;
			        	 
			        	 if(expenseType == "myExpenses"){
			        		 if(expensesDraftCount >0){
			        			 $("#dial1txt").html("<a onclick='myExpensesListByStage(\"draft\");'>Draft</a>");
			        			 $("#expensesDraftCount").html("<a onclick='myExpensesListByStage(\"draft\");'>"+expensesDraftCount+"</a>");
			        		   }else{
			        			   $("#dial1txt").html("Draft");
			        			   $("#expensesDraftCount").html(expensesDraftCount);
			        		   }
			        		 
		                  }else{
		                	  if(empExpensesSubmittedForApprovalCount >0){
			                	  $("#dial1txt").html("<a onclick='employeeExpensesListByStage(\"submitted\");'>Waiting</a>");
			                	  $("#expensesDraftCount").html("<a onclick='employeeExpensesListByStage(\"submitted\");'>"+empExpensesSubmittedForApprovalCount+"</a>");
		                	  }else{
		                		  $("#dial1txt").html("Waiting");
			                	  $("#expensesDraftCount").html(empExpensesSubmittedForApprovalCount); 
		                	  }
		                	  
		                  }
			        	 
			        	if(expenseType == "myExpenses"){
			        		$("#submittedId").show(); 
			        		if(expensesSubmittedForApprovalCount > 0){
			        			$("#dial2txt").html("<a onclick='myExpensesListByStage(\"submitted\");'>Submitted</a>");
				        		  $("#expensesSubmittedForApprovalCount").html("<a onclick='myExpensesListByStage(\"submitted\");'>"+expensesSubmittedForApprovalCount+"</a>");
			        		}else{
			        			 $("#dial2txt").html("Submitted");
				        		 $("#expensesSubmittedForApprovalCount").html(expensesSubmittedForApprovalCount);
			        		}
			        		
		                  }else{
		                	  $("#submittedId").hide();
		                  }
			        	
			        	if(expenseType == "myExpenses"){
			        		  if(expensesApprovedCount > 0){
				        		  $("#dial3txt").html("<a onclick='myExpensesListByStage(\"approved\");'>Approved</a>");
				        		  $("#expensesApprovedCount").html("<a onclick='myExpensesListByStage(\"approved\");'>"+expensesApprovedCount+"</a>");
			        		  }else{
			        			  $("#dial3txt").html("Approved");
				        		  $("#expensesApprovedCount").html(expensesApprovedCount);
			        		  }
			        		  
		                }else{
		                	  if(empExpensesApprovedCount > 0){
			                	  $("#dial3txt").html("<a onclick='employeeExpensesListByStage(\"approved\");'>Approved</a>");
			                	  $("#expensesApprovedCount").html("<a onclick='employeeExpensesListByStage(\"approved\");'>"+empExpensesApprovedCount+"</a>");
		                	  }else{
		                		  $("#dial3txt").html("Approved");
			                	  $("#expensesApprovedCount").html(empExpensesApprovedCount);
		                	  }
		                  }
			        	
			        	if(expenseType == "myExpenses"){
			        		  $("#dial4txtDial").show();
			        		  if(expensesRejectCount > 0){
			        		          $("#dial4txt").html("<a onclick='myExpensesListByStage(\"rejected\");'>Rejected</a>");
			        		          $("#expensesRejectCount").html("<a onclick='myExpensesListByStage(\"rejected\");'>"+expensesRejectCount+"</a>");
			        		  }else{
			        			  $("#dial4txt").html("Rejected");
		        		          $("#expensesRejectCount").html(expensesRejectCount);
			        		  }
		                 }else{
			                	if(empExpensesRejectedCount > 0){
				                	   $("#dial4txt").html("<a onclick='employeeExpensesListByStage(\"rejected\");'>Rejected</a>");
				                	   $("#expensesRejectCount").html("<a onclick='employeeExpensesListByStage(\"rejected\");'>"+empExpensesRejectedCount+"</a>");
				                	}else{
				                		$("#dial4txt").html("Rejected");
					                	$("#expensesRejectCount").html(empExpensesRejectedCount);
				               }
				           }
			        	
			        	  
			 		}
			  });
			 
			 if(expenseType == "myExpenses"){
				 $("#expensesLink").attr("href", "<c:url value='/app/expenses'/>");
			 }else{
				 $("#expensesLink").attr("href", "<c:url value='/app/expensesApprovalList'/>");
			 }
}// expensive count end.
//  requirement list starts here.
function requirementList(isWalkIn, walkInDay){  
		
		 // var sourceleadUrl ='${sourceleadUrl}';
		//  var orgATS = '${SourceLeadWeb_ats}';
		//  var orgVMS = '${SourceLeadWeb_vms}';
		  $("#requirementListActions").empty();
		  $("#requirementList").empty();
		  $("#requirementLoading").show();
		  $("#requirementLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		  $.ajax({
	           type : "POST",
	           url: sourceleadUrl+"/getAssignedRequirementListByOrgLocationIDAndUserId?"+ (isWalkIn ? "isWalkIn="+isWalkIn+"&walkInDay="+walkInDay : ""),    
	            success :function(data) {
	            	if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
	            	 var json = data.data;
	                 if(json != null && json != 'undefined' && json != "" && json != '[]'){
	                	 $("#requirementList").empty();
		                	 var result = "";
		                	 result = result +"<table id='requirementPagination' class='display' cellspacing='0' width='99.9%' >"; 
		                     result = result +"<thead>"; 
		                     result = result +"  <tr>"; 
		                     result = result +"    <th>Actions</th>";
		                     result = result +"    <th>Generated ID</th>"; 
		                     result = result +"    <th>Title</th>";
		                     if(isWalkIn) {
		                    	 result = result +"    <th>Drive Day(s)</th>";
		                     }
		                     if(orgATS == 'Y'){
		                     result = result +"    <th>Client</th>";
		                     }
		                     result = result +"    <th>Rate</th>"; 
		                     result = result +"    <th>Priority</th>"; 
		                     result = result +"    <th>Applicants</th>"; 
		                     result = result +"    <th>Shortlisted</th>"; 
		                     result = result +"    <th>Submitted</th>"; 
		                     result = result +"    <th>Interview</th>"; 
		                     result = result +"    <th>Offered</th>";
		                     result = result +"    <th>Joined</th>";
		                     result = result +"    <th>Status</th>";
//	 	                     result = result +"    <th>Release</th>"; 
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
		                 	 
		                 	var checkSize = json.length;
		                     if(checkSize>10){
		                         checkSize = 10;
		                     }
		                     else{
		                         checkSize = json.length;
		                     }
		                     for (var i = 0; i < checkSize; i++) { 
		                    	 var opt = json[i]; 
		                    	 
		                    	 result = result +"<tr>";
		                    	  result = result +"<td>";
				                     result = result + '<div class="tblminwidth-75">';
				                     
				         	        '<sec:authorize access="hasAnyRole(\'ROLE_ADD_APPLICANT\')">';
					         	       if(opt.requirementSubmissionStatus == 'APPROVED' || opt.requirementSubmissionStatus == 'OPEN' || opt.requirementSubmissionStatus == 'NEW'){
					         	    	  result = result +'<div class="text-center pull-left"><a href="javascript:void(0)" title="Add Applicant" onclick="getAddApplicant('+opt.id+',\''+opt.reqCoolingOffPeriod+'\');" class="add-applicant"><i class="fa fa-user-plus"></i></a></div>';
					         	       }else{
					         	    	  result = result +"<div class='text-center pull-left'>---</div>";
					         	       }
				         	        '</sec:authorize>';
				         	        
				         	       '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_REQUIREMENT\')">';
				         				result = result +'<div class="text-center pull-left ml10 mr10"><a href="javascript:void(0)" title="Assign To" data-toggle="modal" data-target="#myModal" onclick="getAssignedMembersDiv('+opt.id+');"><i class="fa fa-address-book '+(opt.assignedMembersCount == 0 ? "anchorBlack" : "")+'"></i></a></div>';
				         		   '</sec:authorize>';
				         	        
				         	       '<sec:authorize access="hasAnyRole(\'ROLE_RELEASE_REQUIREMENT\')">';
				                     if(opt.requirementSubmissionStatus == 'APPROVED' || opt.requirementSubmissionStatus == 'OPEN' || opt.requirementSubmissionStatus == 'NEW'){
				                    	 if(opt.isRequirementReleasedByuser == 'Y'){
				                       		 result = result +"<div class='pull-left'><a href='<c:url value='/app/releaseRequirementToClientOrVendors?requirementUUID="+opt.requirementUUID+"'/>'><i class='fa fa-bullhorn success' title='Release Job'></i></a></div>"; 
				                       	 }else{	
				                       	 result = result +"<div class='pull-left'><a href='<c:url value='/app/releaseRequirementToClientOrVendors?requirementUUID="+opt.requirementUUID+"'/>'><i class='fa fa-bullhorn' title='Release Job'></i></a></div>";
				                       	 }
				                     }else{
					                     result = result +"<div class='pull-left'>---</div>";
				                     } 
				                     '</sec:authorize>';
				                     result = result + '</div>';
				                     result = result +"</td>";
				                     
		                    	 if(opt.generatedId!=null && opt.generatedId !='' && opt.generatedId !='undefined'){
		                    		 result = result +"<td><div class=''><span title='"+opt.generatedId+"'>"+opt.generatedId+"</span></a></div></td>";
		                    	 }
		                    	 else{
		                    		 result = result +"<td><div>---</div></td>";
		                    	 }
		                    	 if(opt.jobId!=null && opt.jobId !='' && opt.jobId !='undefined'){
		                    		 result = result +"<td><div class='tbldata-dotdot-sm'> <a href='"+sourceleadUrl+"/requirementSummary?requirementUUID="+opt.requirementUUID+"'><span title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span></a></div></td>";
		                    	 }else{
			                    	 result = result +"<td><div class='tbldata-dotdot-sm'> <a href='"+sourceleadUrl+"/requirementSummary?requirementUUID="+opt.requirementUUID+"'><span title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span></a></div></td>";
			                     }
		                    	 if(isWalkIn) {
		                    		 result = result +"<td><div class=''><span title='"+opt.walkInDays+"'>"+opt.walkInDays+"</span></a></div></td>";
		                    	 }
			                     if(orgATS == 'Y'){
				                     if(opt.clientName != null && opt.clientName != '' && opt.clientName != 'undefined'){
				                    	
				                    	 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
				                    	  result = result + '<td><div  class="tbldata-dotdot-sm"><a href="'+sourceleadUrl+'/clientDashboard?orgRelationUUID='+opt.orgRelationUUID+'" title="'+opt.clientName+'">'+opt.clientName+'</a></div></td>';
					           	         '</sec:authorize>';
	           	         
						           	      '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
						     	            result = result + '<td><div  class="tbldata-dotdot-sm">'+opt.clientName+'</div></td>';
						     	          '</sec:authorize>';
				                    	 
				                    	 
				                     }else{
				                    	 result = result +"<td><div>---</div></td>";
				                     }
			                     }
			                     if(opt.rate != null && opt.rate != '' && opt.rate != 'undefined' && opt.maxRate != null && opt.maxRate != '' && opt.maxRate != 'undefined'){
				                     result = result +"<td><div><span  title='"+opt.currencyCode+"&nbsp;"+opt.rate+"-"+opt.maxRate+"&nbsp;"+opt.rateType+"'>"+opt.currencyCode+"&nbsp;"+opt.rate+"-"+opt.maxRate+"&nbsp;"+opt.rateType+"</span></div></td>";
			                     }else if(opt.rate != null && opt.rate != '' && opt.rate != 'undefined'){
				                     result = result +"<td><div><span  title='"+opt.currencyCode+"&nbsp;"+opt.rate+"&nbsp;"+opt.rateType+"'>"+opt.currencyCode+"&nbsp;"+opt.rate+"&nbsp;"+opt.rateType+"</span></div></td>";
		 
			                     }
			                     else{
				                     result = result +"<td><div><span><span>---</span></span></div></td>";
			                     }
			                     if(opt.priority != null && opt.priority != '' && opt.priority != 'undefined'){
			                    	 result = result +"<td><div>"+opt.priority+"</div></td>";
			                     }else{
			                    	 result = result +"<td><div>---</div></td>";
			                     }
			                     
			                     
//	 		                     if(opt.noOfApplicants != null && opt.noOfApplicants != '' && opt.noOfApplicants != 'undefined'){
		
		                    	 result = result +"<td><input type='hidden' class='requirementDashboardCount'  id='"+opt.id+"' /><a href='<c:url value='/app/requirementSummary?type=applicants&requirementUUID="+opt.requirementUUID+"'/>'><div id='"+opt.id+"applicantCount'><i class='fa fa-spinner fa-pulse'></i></div></a></td>";
		                    	 
//	 		                     }
//	 		                     if(opt.noOfShortlistedApplicants != null && opt.noOfShortlistedApplicants != '' && opt.noOfShortlistedApplicants != 'undefined'){
		
			                     result = result +"<td><a href='<c:url value='/app/requirementSummary?type=SHORTLIST&requirementUUID="+opt.requirementUUID+"'/>'><div id='"+opt.id+"shortlistedCount'><i class='fa fa-spinner fa-pulse'></i></div></a></td>";
			                     
//	 		                     }
//	 		                     if(opt.noOfSubmittedApplicants != null && opt.noOfSubmittedApplicants != '' && opt.noOfSubmittedApplicants != 'undefined'){
		
				                 result = result +"<td><a href='<c:url value='/app/requirementSummary?type=SUBMIT_TO_CLIENT&requirementUUID="+opt.requirementUUID+"'/>'><div id='"+opt.id+"submittedCount'><i class='fa fa-spinner fa-pulse'></i></div></a></td>";
				                 
//	 		                     }
//	 		                     if(opt.noOfInterviewedApplicants != null && opt.noOfInterviewedApplicants != '' && opt.noOfInterviewedApplicants != 'undefined'){
			                     result = result +"<td><a href='<c:url value='/app/requirementSummary?type=INTERVIEW&requirementUUID="+opt.requirementUUID+"'/>'><div id='"+opt.id+"interviewedCount'><i class='fa fa-spinner fa-pulse'></i></div></a></td>";
//	 		                     }
//	 		                     if(opt.noOfOfferedApplicants != null && opt.noOfOfferedApplicants != '' && opt.noOfOfferedApplicants != 'undefined'){
			                     result = result +"<td><a href='<c:url value='/app/requirementSummary?type=OFFERMADE&requirementUUID="+opt.requirementUUID+"'/>'><div id='"+opt.id+"offeredCount'><i class='fa fa-spinner fa-pulse'></i></div></a></td>";
//	 		                     }
//	 		                     if(opt.noOfJoinedApplicants != null && opt.noOfJoinedApplicants != '' && opt.noOfJoinedApplicants != 'undefined'){
				                     result = result +"<td><a href='<c:url value='/app/requirementSummary?type=JOINED&requirementUUID="+opt.requirementUUID+"'/>'><div id='"+opt.id+"joinedCount'><i class='fa fa-spinner fa-pulse'></i></div></a></td>";
//	 			                     }



			                     if(opt.requirementSubmissionStatus!=null && opt.requirementSubmissionStatus !='' && opt.requirementSubmissionStatus !='undefined'){
		                    		 result = result +"<td><div class=''><span title='"+opt.requirementSubmissionStatus+"'>"+opt.requirementSubmissionStatus+"</span></a></div></td>";
		                    	 }
		                    	 else{
		                    		 result = result +"<td><div>---</div></td>";
		                    	 }
			                     
			                   
			                    if(checkSize>=10 && i==9 ){
			                    	/* $("#viewmoresDiv").css("display", "block"); */
			                    	var text = "<div class='pull-right' id='viewmoresDiv'>";
			                    	text += "<a href='/app/jobs' class='btn btn-sm btn-primary pull-right' title='View more'> View More</a>";
			                    	text += "</div>";
			                    	$("#requirementListActions").append(text);
				 	   	   	    } 
			                     result = result +"</tr>";
			                     result = result +"</tr>";
		                     }
		                     result = result +" </tbody>";
		                     result = result +"</table>";
		                     
		                     if(isWalkIn) {
		                    	 $("#requirementListActions").append(getDynamicDayActionsForWalkInJobs(parseInt(data.responseMessage.split(":")[1]) - 1, data.responseMessage.split(":")[2], parseInt(data.responseMessage.split(":")[1]) + 1));
		                     }
		                     $("#requirementList").append(result);
		                     $("#requirementLoading").hide();
		                     /* $("#requirementLoading").hide(); */
		                     /* $("#assignedRequirementsDiv").css("display", "block"); */
		                     
		                	 $(".proj_close, .side_mask").click(function(){
									$(".clients_side_wrap").removeClass("clients_side_wrap_active");
									$(".side_mask").fadeOut(200);
									$("body").css('overflow-y', 'auto');
							 });	
		                     pagination(); 
		                     getDashboardJobCounts();
	                 	} else {
	                 		$("#requirementList").empty();
	                 		$("#requirementList").append("<table id='noResultsTable'><tr class='odd'><td colspan='7' class='dataTables_empty' valign='top'></td></tr></table>");
	                 		if(isWalkIn) {
		                 		$("#requirementListActions").append(getDynamicDayActionsForWalkInJobs(parseInt(data.responseMessage.split(":")[1]) - 1, data.responseMessage.split(":")[2], parseInt(data.responseMessage.split(":")[1]) + 1));
	                 		}
	                 	}
	                 	 $("#requirementLoading").hide();
	                 	 $("#requirementList").removeAttr("style");
		                 if(isWalkIn) {
		        	    	 $("#assigned").removeClass("current");
		        	    	 $("#drive").addClass("current"); 
		                 } else {
		        	    	 $("#assigned").addClass("current");
		        	    	 $("#drive").removeClass("current");
		                 }
			    	} else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
	            	
	          },
	            
	       }); 
	  }
function getDashboardJobCounts(){
	 $('.requirementDashboardCount').each(function() {
		 var reqId = this.id;
		 if(reqId != null &&  reqId !='undefined' && reqId != ''){
			 $.ajax({
		           type : "GET",
		           url: sourceleadUrl+"/dashboardPipelineJobsCount?requirementId="+reqId,    
		            success :function(data) {
		            	  $("#"+reqId+"applicantCount").empty();
		             	  $("#"+reqId+"applicantCount").html(data.applicantsCount);
		             	  
		             	  $("#"+reqId+"shortlistedCount").empty();
		             	  $("#"+reqId+"shortlistedCount").html(data.shortlistCount);
		             	  
		             	  $("#"+reqId+"interviewedCount").empty();
		             	  $("#"+reqId+"interviewedCount").html(data.interviewCount);
		             	  
		             	  $("#"+reqId+"submittedCount").empty();
		             	  $("#"+reqId+"submittedCount").html(data.submitToClientCount);
		             	  
		             	  $("#"+reqId+"offeredCount").empty();
		             	  $("#"+reqId+"offeredCount").html(data.offersCount);
		             	  
		             	  $("#"+reqId+"joinedCount").empty();
		             	  $("#"+reqId+"joinedCount").html(data.joinedCount);
		            }
			    });
		 }
			
  }); 
}
function getDynamicDayActionsForWalkInJobs(prevDay, selectedDay, nextDay){
	var text="<div class='walk_in_days pull-right mb10 mt10'>";
	 text += "<a  class='btn btn-default pull-left mr btn-sm' href='javascript:void(0)' onclick='requirementList(true," + prevDay + ")'>&lt;&lt;</a>";
	 text += "<div class='paginate_button pull-left'>" + selectedDay + "</div>";
	 text += "<a  class='btn btn-default pull-left ml btn-sm' href='javascript:void(0)' onclick='requirementList(true," + nextDay + ")'>&gt;&gt;</a>";
	 text += "</div>";
	 return text;
}
function pagination(){ 
    $('#requirementPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"bFilter": false,
    	"bPaginate":false,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    } );
}// requirement list ends here.
// on boarding canidate list starts here
function onboardingCandidatesList(){  
	 // var sourceleadUrl ='${sourceleadUrl}';
	  $("#onboardingLoading").attr('style','position:absolute; width:100%; height:150px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	//  var userId = '${SourceLeadWeb_userId}';
	//  var loginUserRole = '${SourceLeadWeb_loginUserRole}';
	 // var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  onBoardListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  onBoardListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  onBoardListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  onBoardListDate = 'YYYY-DD-MM'; 
	  }  
	  $.ajax({
         type : "POST",
         url: sourceleadUrl+"/getOnboardingCandidatesList",    
          success :function(response) {
          	if (response.response === "AWKWARD") {
          	    location.href = "/errorPage";
          	} else if (response.response === "SUCCESS") {
          		$("#onboardingLoading").removeAttr("style");
             	 $("#onboardingCandidatesList").empty();
             	 var result = "";
             	 result = result +"<table id='onboardingPagination' class='display' cellspacing='0' width='99.9%' >"; 
                  result = result +"<thead>"; 
                  result = result +"  <tr>"; 
                  result = result +"    <th>Candidate Name</th>";
                  result = result +"    <th>Email</th>";
                  result = result +"    <th>Title</th>";
                  result = result +"    <th>DOJ<br>(<small class='custom-small'>"+onBoardListDate+"</small>)</th>"; 
                  result = result +"    <th>Rate</th>"; 
                  result = result +"    <th>Position Type</th>";
                  result = result +"    <th>On-board Status</th>";
                  '<sec:authorize access="hasRole(\'ROLE_CREATE_ON_BOARD_USER\')">'
                 	 result = result +"<th>Actions</th>";
                  '</sec:authorize>';
                  result = result +"  </tr>"; 
                  result = result +"</thead>";
                  result = result +"<tbody>"; 
                  
                  var json = JSON.parse(response.object);
                  
                 	 for (var i = 0; i < json.length; i++) { 
                 	 var opt = json[i]; 
                 	 result = result +"<tr>";
                 		 result = result +"<td><div class='tbldata-dotdot-sm'><span title='"+opt.candidateName+"'>"+opt.candidateName+"</span></div></td>";
                 		 result = result +"<td><div><span title='"+opt.candidateEmail+"'>"+opt.candidateEmail+"</span></div></td>";
                 		 if(opt.reqManagerId == userId || opt.reqReleaseAllJobUserStatus == "Y" || loginUserRole == 'ROLE_ADMIN'){
                 		 result = result +"<td><div class='tbldata-dotdot-sm'> <a href='"+sourceleadUrl+"/requirementSummary?requirementUUID="+opt.requirementUUID+"'><span title='"+opt.requirementName+"'>"+opt.requirementName+"</span></a></div></td>";
                 		 }
                 		 else{
	                    		 result = result +"<td><div class='tbldata-dotdot-sm'><span title='"+opt.requirementName+"'>"+opt.requirementName+"</span></div></td>";
                 		 }
                 		 result = result +"<td><div><span>"+opt.offerJoinDate+"</span></div></td>";
                 		 
		                     if(opt.rate != null && opt.rate != '' && opt.rate != 'undefined'){
			                     result = result +"<td><div><span title='"+opt.paymentType+" "+opt.rate+" / "+opt.rateType+"'>"+opt.paymentType+" "+opt.rate+" / "+opt.rateType+"</span></div></td>";
		                     }else{
			                     result = result +"<td><div><span>-----</span></div></td>";
		                     }
	                     
		                     if(opt.positionType != null && opt.positionType != '' && opt.positionType != 'undefined'){
		                    	 result = result +"<td><div><span title='"+opt.positionType+"'>"+opt.positionType+"</span></div></td>";
		                     }else{
			                     result = result +"<td><div><span>-----</span></div></td>";
		                     }
		                     
		                     if(opt.onboardStatus != null && opt.onboardStatus != '' && opt.onboardStatus != 'undefined'){
		                    	 if(opt.onboardStatus == "Finished"){
		                    		 result = result +"<td><div><span title='Sent'>Sent</span></div></td>";
		                    	 }else{
		                    		 result = result +"<td><div><span title='"+opt.onboardStatus+"'>"+opt.onboardStatus+"</span></div></td>";
		                    	 }
		                    	
		                     }else{
			                     result = result +"<td><div><span>Ready to onboard</span></div></td>";
		                     }
		                     
		                     '<sec:authorize access="hasRole(\'ROLE_CREATE_ON_BOARD_USER\')">'
			                     if(opt.onboardUUId != null && opt.onboardUUId != '' && opt.onboardUUId != 'undefined'){
			                    	 if(opt.onboardStatus == 'Draft'){
			                    		 result = result +"<td><div class='tbldata-dotdot-sm'> <a href='"+sourceleadUrl+"/viewOnboardCandidateDetails?onboardUUID="+opt.onboardUUId+"'><span class='text-success'>View Onboard</span></a></div></td>";
			                    	 }else{
			                    		 result = result +"<td><div class='tbldata-dotdot-sm'> <a href='"+sourceleadUrl+"/viewOnboardCandidateSummary?onboardUUID="+opt.onboardUUId+"'> <span class='text-success'>View Onboard</span></i></a> ";
			                    		 if(opt.onboardStatus == 'Submitted'){
			                    			 result = result	+ '<span>&nbsp;&nbsp;|&nbsp;&nbsp;<a><i class="fa fa-check text-success" title="Accept" onclick="acceptOnboardingCandiate(\''+opt.onboardUUId+'\');"></i></a></span>&nbsp;&nbsp;&nbsp;<span><a><i class="fa fa-close text-danger" title="Reject" onclick="rejectedReason(\''+opt.onboardUUId+'\',\''+opt.candidateName+'\',\''+opt.candidateEmail+'\');"></i></a></span>';
			                    		 }
			                    		 
			                    	 }
			                    	 result = result +"</div></td>";
			                    	
			                    }else{
			                    	result = result +"<td><div class='tbldata-dotdot-sm'><a href='"+sourceleadUrl+"/candidateOnBoardingDetails?candidateUUId="+opt.candidateUUID+"&offerUUID="+opt.offerUUID+"'><span class='text-success'>Onboard</span></a></div></td>";
			                    }
		                     '</sec:authorize>';  

	                     
	                     result = result +"</tr>";
	                     result = result +"</tr>";
                  }
                  result = result +" </tbody>";
                  result = result +"</table>"; 
                  $("#onboardingLoading").removeAttr("style");
                  $("#onboardingCandidatesList").append(result); 
                  
                  onboardingPagenation(); 
          	} else if (response.indexOf('Sign In') !== -1) {
          		location.replace(sourceleadUrl);
          	} else {
          	    showError("Operation failed, please try again");
          	}
        },
     }); 
} 
function acceptOnboardingCandiate(onboardCandidateUUID){
	  //var sourceleadUrl ='${sourceleadUrl}';
	 	 $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/acceptOnboardingCandiate?onboardCandidateUUID="+onboardCandidateUUID, 
		        success: function(response) {
		        	if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		location.replace(sourceleadUrl+"/dashboard/locationId");
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    }); 
}
function rejectedReason(onboardCandidateUUID,candidateName,candidateEmail){
	
    var result = "";
	result = result + '	<div class="settPop">';
	result = result + '	<div id="loading"></div>';
	result = result + '<form id="rejectedReason_form" name="rejectedReason_form" method="post">';
	result = result + '<input type="hidden" name="onboardCandidateUUID" value="'+onboardCandidateUUID+'">';
	result = result + '	<h3>Onboard Rejected Reason<i class="fa fa-times" onclick="cancelRejectedReason();"></i></h3>  ';
	result = result + '	<div class="settPopInn"> ';
	
	result = result + '		<div class="clearfix">  ';
	result = result + '		<div class="col-md-12">  ';
	result = result + '		  <label class="col-md-2 paddingLeft" for="Candidate Name">Name :</label> ';
	result = result + '		  <label class="col-md-8 paddingRight">'+candidateName+'</label> ';
	result = result + '		</div>  ';

	result = result + '		<div class="col-md-12">  ';
	result = result + '		  <label class="col-md-2 paddingLeft" for="Email">Email :</label> ';
	result = result + '		  <label class="col-md-8 paddingRight">'+candidateEmail+'</label> ';
	result = result + '		</div>  ';
	
	
	result = result + '		  <label class="col-md-12 mt10" for="Project Id">Reason</label> ';
	result = result + '		  <div class="col-md-12 taskdesp mt10">   ';
	result = result + '			<textarea class="form-control" name="rejectedReason" id="rejectedReason" field-name="Reason" data-validation="required  validate_length length2-500" rows="2"></textarea>';
	result = result + '			<span class="hidden"></span> </div>  ';
	result = result + '		</div>';
	result = result + '		<div class="col-md-12 mt5 clearfix"> ';
	result = result + '		<div class="pull-right">  ';
	result = result + '		  <input type="submit" value="Save" id="submit_rejectedReason"  class="btn btn-primary" >';
	result = result + '		  <input type="button" value="Cancel" class="btn btn-default ml10" onclick="cancelRejectedReason();">';
	result = result + '		</div>';
	result = result + '		</div>';
	result = result + '		<div class="clearfix"></div>   ';
	result = result + '	</div>  ';
	result = result + '</form>';
	result = result + '	</div>';
    $("#rejectedReasonDiv").empty();
    $("#rejectedReasonDiv").append(result);
    
    $(".settPop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);
    
}
function onboardingPagenation(){ 
	 $('#onboardingPagination').DataTable( { 
	    	"scrollCollapse": false,
	    	"lengthMenu": [[5, 15, 20, -1], [2, 15, 20, "All"]],
	    	"bLengthChange": false,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	            "sSearch": "",
	            "sZeroRecords": "", "sEmptyTable": ""
	       }
	    } );
}//On boarding candidates list END.

// contracts starts here
function contractList(){  
	
	  $("#contractList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  if(locationList.match("yyyy-MM-dd")){
		  DashboardContractDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  DashboardContractDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  DashboardContractDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  DashboardContractDate = 'YYYY-DD-MM'; 
	  }  
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getDashboardContracts?locationId="+locationId,     
            success :function(data) {
			    	     $("#contractList").removeAttr("style");
	                	 $("#contractList").empty();
	                	 var result = "";
	                	 result = result +"<table id='contractListPagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Applicant Name</th>";
	                     result = result +"    <th>Job</th>";
	                     result = result +"    <th>Rate</th>"; 
	                     result = result +"    <th>Join Date<br>(<small class='custom-small'>"+DashboardContractDate+"</small>)</th>"; 
	                     result = result +"    <th>Vendor</th>"; 
	                     result = result +"    <th>Contact</th>"; 
	                     result = result +"    <th>Action</th>"; 
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     for (var i = 0; i < data.length; i++) { 
	                    	 var opt = data[i];
// 	                    	 if(opt.contractUUID != null && opt.contractUUID !='' && opt.contractUUID !='undefined'){
	                    		 result = result +"<tr>";
		                    	 if(opt.candidateName !=null && opt.candidateName !='' && opt.candidateName !='undefined'){
		                    		 result = result +"<td><div class=''><span title='"+opt.candidateName+"'>"+opt.candidateName+"</span></a></div></td>";
		                    	 }
		                    	 else{
		                    		 result = result +"<td><div>---</div></td>";
		                    	 }
		                    	 if(opt.jobTitle !=null && opt.jobTitle !='' && opt.jobTitle !='undefined'){
		                    		 result = result +"<td><div class='tbldata-dotdot-sm'><span title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span></div></td>";
		                    	 }else{
			                    	 result = result +"<td><div class='tbldata-dotdot-sm'>---</div></td>";
			                     }
		                    	 if(opt.amount != null && opt.amount != '' && opt.amount != 'undefined' && opt.paymentMode != null && opt.paymentMode != '' && opt.paymentMode != 'undefined'){
				                     result = result +"<td><div><span  title='"+opt.paymentType+"&nbsp;"+opt.amount+"/"+opt.paymentMode+"&nbsp;'>"+opt.paymentType+"&nbsp;"+opt.amount+"/"+opt.paymentMode+"</span></div></td>";
			                     }else if(opt.rate != null && opt.rate != '' && opt.rate != 'undefined'){
				                     result = result +"<td><div>---</div></td>";
			                     }
		                    	 if(opt.dateOfJoiningStr != null && opt.dateOfJoiningStr != '' && opt.dateOfJoiningStr != 'undefined'){
			                    	 result = result +"<td><div><span title='"+opt.dateOfJoiningStr+"'>"+opt.dateOfJoiningStr+"</span></div></td>";
			                     }else{
			                    	 result = result +"<td><div>---</div></td>";
			                     }
			                     if(opt.clientName != null && opt.clientName != '' && opt.clientName != 'undefined'){
			                    	 result = result +"<td><div><span title='"+opt.clientName+"'>"+opt.clientName+"</span></div></td>";
			                     }else{
			                    	 result = result +"<td><div>---</div></td>";
			                     }
			                     if(opt.vendorContact != null && opt.vendorContact != '' && opt.vendorContact != 'undefined'){
			                    	 result = result +"<td><div><span title='"+opt.vendorContact+"'>"+opt.vendorContact+"</span></div></td>";
			                     }else{
			                    	 result = result +"<td><div>---</div></td>";
			                     }
			                     result = result +"<td><div><a href='/app/addNewContract?contractUUID="+opt.contractUUID+"&orgRelationId="+opt.orgRelationId+"&spocUserCode="+opt.spocUserCode+"&type=Sow' title='Create Sow'>SOW</a>";
			                     if(opt.vendorMsaCreated != true){
			                    	 result = result +"<a href='/app/addNewContract?orgRelationId="+opt.orgRelationId+"&spocUserCode="+opt.spocUserCode+"&type=Msa' title='Create Msa'> / MSA</a>";
			                     }
			                     result = result +"</div></td>";
			                     
			                     result = result +"</tr>";
	                    	 }
// 	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#contractList").append(result); 
	                     $("#contractListDiv").css("display", "block");
	                	 contractListPagination(); 
          },
            
       }); 
  }   
 
 function contractListPagination(){ 
    $('#contractListPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"bFilter": false,
    	"bPaginate":false,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    } );
}