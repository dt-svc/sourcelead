<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<!-- these files for autocomplete with tagit -->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
function requirementHistory(candidateId){
	var result = "";
	result = result  +'    <div class="project_tabs_inn_head addUser-org">  ';
		result = result  +'<span class="pad-sea"></span>';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		result  = result + '   <div class="reqPop" id="requirementHistoryPopDiv" ></div>';
		result  = result + '   <div id="requirementHistoryDiv" class="datatable_filter"></div>';
		result  = result + '   </div>';
	result = result+'	    </div>';
	$("#tab-1").empty();
	$("#tab-2").empty();
	$("#tab-3").empty();
	$("#tab-4").empty();
	$("#tab-5").empty();
	$("#tab-6").empty();
	$("#tab-7").empty();
	$("#tab-8").empty();
    $("#tab-8").append(result);  
	
    requirementHistoryList(candidateId);
}
</script>


<script>
function requirementHistoryList(candidateId){
	
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
         type : "POST",
         url: sourceleadUrl+"/getRequirementHistoryListByCandidateId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
         success :function(data) {
        	 var json = JSON.parse(data);
        	 var result = "";
     		result = result  +'	    <div class="project_tabs_inn_head">';
     	    result = result  +'	    <div class="project_tabs_inn_table">';
     	    result = result  +'	      <table id="requirementHistoryPagination" class="display" cellspacing="0" width="100%">';
     		result = result  +'	        <thead>';
     		result = result  +'	          <tr>';
     		result = result  +'	            <th>Title</th>';
     		result = result  +'	            <th>Emp Type</th>';
     		result = result  +'	            <th>Location</th>';
     		result = result  +'	            <th>Rate</th>';
     		result = result  +'	            <th>Owner</th>';
     		result = result  +'	            <th>Client</th>';
     		result = result  +'	            <th>Submitted On</th>';
     		result = result  +'	            <th>Stage</th>';
     		result = result  +'	          </tr>';
     		result = result  +'          </thead>';
     		result = result  +'         <tbody> '; 
     		for (var i = 0; i < json.length; i++) { 
           	 var report = json[i];  
           	 if(report.requirement.jobType != null && report.requirement.jobType != '' && report.requirement.jobType != 'undefined'){
           		jobType = report.requirement.jobType;
           	 }else{
           		jobType = "--";
           	 }
           	 if(report.rate != null && report.rate != '' && report.rate != 'undefined'){
           		rate = report.rate;
           	 }else{
           		rate = "--";
           	 }
           	 if(report.clientName != null && report.clientName != '' && report.clientName != 'undefined'){
           		clientName  = report.clientName;
           	 }else{
           		clientName = "--"
           	 }
	           	result = result  +'	          <tr>';
	    		result = result  +'	            <td><div><span class="mid-span ReqClick" onclick="requirementHistoryDetails('+candidateId+',\''+report.positionType+'\',\''+report.requirement.id+'\',\''+report.requirement.requirementUUID+'\',\''+report.id+'\');">'+report.positionType+'</span></div></td>';
	    		result = result  +'	            <td><div><span class="mid-span">'+jobType+'</span></div></td>';
	    		result = result  +'	            <td><div><span class="mid-span">'+report.country+'</span></div></td>';
	    		result = result  +'	            <td><div><span class="mid-span">'+rate+'</span></div></td>';
	    		result = result  +'	            <td><div><span class="mid-span">'+report.ownerName+'</span></div></td>';
	    		result = result  +'	            <td><div><span class="mid-span">'+clientName+'</span></div></td>';
	    		result = result  +'	            <td><div><span class="mid-span">'+report.createdDateStr+'</span></div></td>';
	    		result = result  +'	            <td><div><span class="mid-span">'+report.submissionStatus+'</span></div></td>';
	    		result = result  +'	          </tr>';
           	 
     		}
     		result = result  +'	        </tbody>';
    		result = result  +'	      </table>';
    		result = result  +'	    </div>';
    		result = result  +'	    </div>';
    	 
    	 $("#requirementHistoryDiv").empty();
    	 $("#requirementHistoryDiv").append(result);
    	 requirementHistoryPagination();
     		
         }
	 });
}
</script>


<script>
function requirementHistoryPagination(){  
    $('#requirementHistoryPagination').DataTable( { 
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
<script>
	function predefineProfileImage(imageId){   
		$(imageId).attr('src','resources/images/userprofile-img.png');
	}
</script>

<script>
function requirementHistoryDetails(candidateId,positionType,requirementId,requirementUUID,submissionId){
	var loginUserId = '${SourceLeadWeb_userId}';
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
        type : "POST",
        url: sourceleadUrl+"/getRequirementHistoryDetailsByCandidateId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&requirementId="+requirementId+"&submissionId="+submissionId,        
        success :function(data) {
        	var json = JSON.parse(data);
        	
        	var result = "";
     		result = result  +'	<div class="reqPopInn reqHistTitle">';
     		result = result  +'		<div class="reqClose">';
     		result = result  +'			<span><i class="fa fa-angle-double-left"></i>Go Back</span><b class="fa fa-close"></b>';
     		result = result  +'		</div>';
     		result = result  +'		<h3 title="'+positionType+'"><a href="'+sourceleadUrl+'/requirementSummary?requirementUUID='+requirementUUID+'">'+positionType+'</a><span onclick="getRateListDiv('+candidateId+',\''+submissionId+'\');">Rate</span><i class="ratepop" id="ratepop"></i><div class="clearfix"></div></h3>';
     		result = result  +'		<ul>';
     		   
     		    var report2 = json[0];
     		    if(report2.requirementOfferHistoryList != null && report2.requirementOfferHistoryList != "undefined" && report2.requirementOfferHistoryList.length > 0){
     			   for(var k=0;k<report2.requirementOfferHistoryList.length;k++){
	          			var port = report2.requirementOfferHistoryList[k];
	          			if(port.offerSourceType == "CLIENT_OFFERMADE"){
	          				result = result  +'			<li>';
		            		result = result  +'	   <img onerror="predefineProfileImage(this);" src="'+sourceleadUrl+'/displayUserImage/'+loginUserId+'"/>';
		            		result = result  +'<span>Issued offer by '+port.clientName+' <b>'+port.createDateStr+'</b></span>';
		            		result = result  +'<a href=\"'+sourceleadUrl+'/offerDownloadDocument?resumeId='+port.offerResumeId+'\"><i class="fa fa-paperclip"></i></a><div class="clearfix"></div>';
		            		result = result  +'<div class="clearfix"></div>';
		            		result = result  +'			</li>';
	          			}
	          			
	          		 }
     		    }
	     		   
     		   var report1 = json[0]; 
     		   if(report1.requirementInterviewHistoryList != null && report1.requirementInterviewHistoryList != "undefined" && report1.requirementInterviewHistoryList.length > 0){
		     	   if(report1.requirementInterviewHistoryList.length > 0){
		     		   for(var j=0;j<report1.requirementInterviewHistoryList.length;j++){
		          			var sub = report1.requirementInterviewHistoryList[j];
		          			if(sub.endTimeStr != null && sub.endTimeStr != '' && sub.endTimeStr != 'undefined'){
		          				endTimeStr = sub.endTimeStr;
		          			}else{
		          				endTimeStr = "";
		          			}
		          			if(sub.interviewSourceType == "CLIENT_INTERVIEW"){
		          				result = result  +'			<li>';
			            		result = result  +'	   <img onerror="predefineProfileImage(this);" src="'+sourceleadUrl+'/displayUserImage/'+loginUserId+'"/>';
			            		result = result  +'<span>Interview scheduled by '+sub.clientName+' <b>'+sub.interviewHistoryCreatedDate+'</b> <h6>Interview Type : '+sub.interviewType+'</h6> <h6>Interview Date : '+sub.dateOfInterviewStr+'</h6><h6>Interview Time : '+sub.startTimeStr+' : '+endTimeStr+'</h6></span>';
			            		result = result  +'<div class="clearfix"></div>';
			            		result = result  +'			</li>';
		          			}
		          			
		          		 }
		     	   }
     		   }
     
	        	 for (var i=0; i<json.length; i++)
					{  
		        		var report = json[i];
		        		if(report.interviewEndTime != null && report.interviewEndTime != '' && report.interviewEndTime != 'undefined'){
		        			interviewEndTime = report.interviewEndTime;
		        		}else{
		        			interviewEndTime = "";
		        		}
		        		result = result  +'			<li>';
		        		result = result  +'	   <img onerror="predefineProfileImage(this);" src="'+sourceleadUrl+'/displayUserImage/'+loginUserId+'"/>';
		        		if(report.toStage == "SUBMISSION"){
			        		 result = result  +'<span>Added by  '+report.loginUserName+' <b>'+report.createdDateStr+'</b></span>';
		        			 result = result  +'<a href=\"'+sourceleadUrl+'/downloadResume?resumeUUID='+report.resumeid+'\"><i class="fa fa-paperclip"></i></a><a class="fa fa-eye ml10" onclick="getHistoryCandiResume(\''+report.resumeid+'\',\''+report.fileName+'\');"></a><div class="clearfix"></div>';
		        		}else if(report.toStage == "OFFERMADE"){
		        			result = result  +'<span>Issued offer by '+report.loginUserName+' <b>'+report.createdDateStr+'</b></span>';
		        			result = result  +'<a href=\"'+sourceleadUrl+'/offerDownloadDocument?resumeId='+report.offerResumeid+'\"><i class="fa fa-paperclip"></i></a><div class="clearfix"></div>';
		        		}else if(report.toStage == "INTERVIEW"){
		        			result = result  +'<span>'+report.loginUserName+' Scheduled interview <b>'+report.createdDateStr+'</b><h6>Interview Type : '+report.interviewType+'</h6><h6>Interview Date : '+report.interviewDateStr+'</h6><h6>Interview Time : '+report.interviewStartTime+' : '+interviewEndTime+'</h6> </span>';
		        		}else if(report.toStage == "SHORTLIST"){
		        			result = result  +'<span>Shortlisted by  '+report.loginUserName+' <b>'+report.createdDateStr+'</b></span>';
		        		} else if(report.toStage == "REQUEST_SENT"){
		        			result = result  +'<span>Added & Request Sent by  '+report.loginUserName+' <b>'+report.createdDateStr+'</b></span>';
		        			result = result  +'<a href=\"'+sourceleadUrl+'/downloadResume?resumeUUID='+report.resumeid+'\"><i class="fa fa-paperclip"></i></a><a class="fa fa-eye ml10" onclick="getHistoryCandiResume(\''+report.resumeid+'\',\''+report.fileName+'\');"></a><div class="clearfix"></div>';
		        		} else if(report.toStage == "REJECTED"){
		        			 result = result  +'<span>Request Rejected By '+report.userName+' <b>'+report.createdDateStr+'</b><h7>Reject Reson : '+report.requirementReson+'</h7></span>';
		        		} else if(report.toStage == "SUBMISSION_ACCEPT"){
		        			result = result  +'<span>Request Accepted By '+report.userName+' <b>'+report.createdDateStr+'</b></span>';
		        		} else if(report.toStage == "OFFERACCEPTED"){
		        			result = result  +'<span>'+report.userName+' accepted offer <b>'+report.createdDateStr+'</b></span>';
		        		}else if(report.toStage == "OFFERREJECTED"){
		        			result = result  +'<span>'+report.userName+' rejected offer <b>'+report.createdDateStr+'</b></span>';
		        		}else if(report.toStage == "INTERVIEW_ACCEPT"){
		        			result = result  +'<span>'+report.userName+' accepted interview <b>'+report.createdDateStr+'</b></span>';
		        		}else if(report.toStage == "INTERVIEW_REJECT"){
		        			result = result  +'<span>'+report.userName+' rejected interview <b>'+report.createdDateStr+'</b></span>';
		        		}else if(report.toStage == "INTERVIEW_TENTATIVE"){
		        			result = result  +'<span>'+report.userName+' tentative interview <b>'+report.createdDateStr+'</b></span>';
		        		}else if(report.toStage == "FEEDBACK"){
		        			result = result  +'<span>Feedback by '+report.interviewerName+' <b>'+report.createdDateStr+'</b></span>';
		        		}else if(report.toStage == "SUBMIT_TO_CLIENT"){
		        			result = result  +'<span>Submit to Client '+report.loginUserName+' <b>'+report.createdDateStr+'</b></span>';
		        			result = result  +'<a href=\"'+sourceleadUrl+'/downloadResume?resumeUUID='+report.resumeid+'\"><i class="fa fa-paperclip"></i></a><a class="fa fa-eye ml10" onclick="getHistoryCandiResume(\''+report.resumeid+'\',\''+report.fileName+'\');"></a><div class="clearfix"></div>';
		        		}else{
		        			result = result  +'<div class="clearfix"></div>';
		        		}
		        		result = result  +'			</li>';
		        		
					}
        	 
        	 result = result  +'		</ul>';
     		 result = result  +'</div>';
	     	 $("#requirementHistoryPopDiv").empty();
	     	 $("#requirementHistoryPopDiv").append(result);
	     	 $(".reqPop").fadeIn(100);
	     	 $(".reqClose").click(function(){
	     			$(".reqPop").fadeOut(100);
	         });
        	 
        }
	 }); 
}

</script>

<script>
function getHistoryCandiResume(uuid,title){
	var sourceleadUrl = '${sourceleadUrl}';
		if (uuid != null && uuid != "") {
			window.open(sourceleadUrl + '/viewResume/' + title + '?resumeUUID='+ uuid, title, "height=600,width=600");
		}
	}
</script>

          
          <script>
function getRateListDiv(candidateId,submissionId){
	  var sourceleadUrl ='${sourceleadUrl}';
	  var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getRateHistoryBySubmissionId?${_csrf.parameterName}=${_csrf.token}&submissionId="+submissionId+"&candidateId="+candidateId,        
         success :function(data) {
        	 if(data != null && data != 'undefined' && data != "" && data != '[]'){
            	 var json = JSON.parse(data);
          		 if( data.indexOf('Sign In') >= 0){
		    		location.replace(sourceleadUrl);
		    	 }
          		 else if (data.indexOf('exception') >= 0) {
          			 location.replace(sourceleadUrl+"/errorPage");
          		 }else{
          			     var result = "";
      			         result = result + '<i class="fa fa-times text-danger close-ratepop" title="closediv" onClick="closeratediv();"></i>';
      			         result = result + '<p><strong>Rate Negotiation History</strong></p>';
         			     result = result + '<section class="tbl-scroll require_wrap_table">';
	                     result = result + '  <table>';
	                     result = result + '    <thead>';
	                     result = result + '      <tr>';
	                     result = result + '        <th>By</th>';
	                     result = result + '        <th>Rate</th>';
	                     result = result + '        <th>Comment</th>';
	                     result = result + '        <th>Date</th>';
	                     result = result + '        <th>Status</th>';
	                     result = result + '      </tr>';
	                     result = result + '    </thead>';
	                     result = result + '    <tbody>';
	                     var divState = "";
	                     var isLastNegotiator = "";
	                     for (var i=0; i<json.length; i++)
			   				{
					    	 var report = json[i];
					    	 if(i == json.length-1){
		                    	 divState = report.rateInitiatorType;
		                    	 if((report.rateInitiatorType != "ORG" && report.rateInitiatorType != "CANDIDATE") && (report.initiatorOrgLocationIdStr==orgLocationId)){
		                    		 isLastNegotiator = "true";
		                    	 }else{
		                    		 isLastNegotiator = "false";
		                    	 }
	                    	 }
		                     result = result + '<tr>';
		                     if(report.proposeByName != null && report.proposeByName != '' && report.proposeByName != 'undefined'){
		                     result = result + '<td><div class="tbldata-dotdot-sm">'+report.proposeByName+'</div></td>';
		                     }
		                     else{
		                    	 result = result + ' <td><div class="tbldata-dotdot-sm">----</div></td>'; 
		                     }
		                    
		                     if(report.rateAndType != null && report.rateAndType != '' && report.rateAndType != 'undefined'){
		                         result = result + '<td><div class="tblminwidth-75">'+report.rateAndType+'</div></td>';
		                     } 
		                     else{
		                    	 result = result + ' <td><div class="tblminwidth-75">----</div></td>'; 
		                     }
		                     
		                     if(report.rateReason != null && report.rateReason != '' && report.rateReason != 'undefined'){
		                         result = result + '<td><div class="tbldata-dotdot">'+report.rateReason+'</div></td>';
		                     }
		                     else{
		                         result = result + '<td><div class="tbldata-dotdot">----</div></td>';
		                     }
		                     
		                     if(report.createdDateStr != null && report.createdDateStr != '' && report.createdDateStr != 'undefined'){
		                         result = result + '<td><div class="tblminwidth-75">'+report.createdDateStr+'</div></td>';
		                     }
		                     else{
		                         result = result + '<td><div class="tblminwidth-75">----</div></td>'; 
		                     }
		                     
		                     if(report.rateStage == 'ACCEPTED'){
		                    	 result = result + ' <td><div class="tblminwidth-75">Accepted</div></td>';
		                     	}else if(report.rateStage == 'REJECTED'){
		                     		result = result + ' <td><div class="tblminwidth-75">Rejected</div></td>';
		                     	}else{
		                     		result = result + ' <td><div class="tblminwidth-75">----</div></td>';
		                     	}
		                     
		                      
		                     result = result + '      </tr>';
			   				}
	                     result = result + '    </tbody>';
	                     result = result + '  </table>';
	                     result = result + '</section>';
	                     result = result + '</i>';
	                     $("#ratepop").empty();  
	      	 		     $("#ratepop").append(result); 
	      	 		 	 $("#ratepop").fadeIn(); 
          		 	}
        	 	}else{
        	 		$("#ratepop").removeAttr("style");
		    	     var result ="";
		    	     result = result + '<i class="fa fa-times text-danger close-ratepop" title="closediv" onClick="closeratediv();"></i>';
		    	     result = result + '<p><strong>Rate Negotiation History</strong></p>';
		    	     result = result+"   <div class='row'>"; 
					 result = result+"	<div class='col-lg-12'>"; 
					 result = result+"		<div class='clearfix'>"; 
					 result = result+"			<div class='blank-data'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No rate history found</p>";
					 result = result+"			 </div>";
					 result = result+"		</div>"; 
					 result = result+"	</div>"; 
					 result = result+"  </div> ";
                     result = result + '</i>';
                     $("#ratepop").empty();  
	      	 		 $("#ratepop").append(result); 
	      	 		 $("#ratepop").fadeIn(); 
            	}
         	},
      	}); 
	};
</script>

<script>
 $('.showRate').click(function(){
	$(this).next().fadeIn(200);
});
function closeratediv(){
  $("#ratepop").fadeOut();
}
</script>