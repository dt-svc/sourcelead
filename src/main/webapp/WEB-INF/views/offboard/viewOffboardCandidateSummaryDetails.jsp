<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/style.css" />
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/offboardCheckListBuilder.js" type="text/javascript" ></script> <!-- form builder -->
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/offboardCheckList-view-generator.js" type="text/javascript" ></script> <!-- form generator -->

<script> 
	$(document).ready(function(){
		offboardCandidateDetails();
	});
</script>

 
 
 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
          <h2 id="candidateName"></h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a>  &raquo; <a href="${SourceLeadWeb_atsUrl}/candidateOnBoardingList">Onboard & OffBoard List</a> &raquo; Candidate Offboard Summary </div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="applicant_accept">
        <div class="clearfix onboardBtns">
        </div>
        <div class="tabbable">
          <ul class="nav nav-tabs" id="tab_bar">
            <li class="active" onclick="offboardCandidateDetails();"> <a href="#details" data-toggle="tab"> <i class="fa fa-th-list paddingRight"></i> <span>Details</span> <br>
              <small>Employee details</small> </a> </li>
            
              <li onclick="getOffboardCandidateCheckList();"><a href="checkList" data-toggle="tab"> <i class="fa fa-check-square-o paddingRight"></i> <span>Check List</span> <br>
              <small>Check List</small> </a> </li>

              <li onclick="getOffboardingTasksList();"> <a href="#tasks" data-toggle="tab" id="templateTasks"> <i class="fa fa-check-square-o paddingRight"></i> <span>Tasks</span> <br>
              <small>List of tasks</small> </a></li>
              
              <li onclick="getOffboardDocuments();"><a href="#documents" data-toggle="tab" > <i class="fa fa-files-o paddingRight"></i> <span>Documents</span> <br>
              <small>Resignation / Termination &amp; Offboard Pack Documents</small> </a> </li>
              
              
             
          </ul>
          
          
          <div class="tab-content"> 
            <div id="loading"></div>
            <!-- Employee Details -->
            <div class="tab-pane clearfix onboard_offboad_view active" id="details">
              <div id="offboardCandidateDetails"></div>
            </div>
            
            
            <!-- TAB 2 -->
            
            
            <div class="tab-panes" id="checkList"> 
              <div id="offboardCheckListView"></div>
            </div>
            
            <div class="tab-pane clearfix emp_onboarddocs" id="documents">
              <div class="col-md-12 clearfix mt">
				  <!-- <div class="form-group">
				    <label class="clearfix"> <strong>Resignation / Termination Documents</strong> </label>
				    <div id="resignationDocumentsDiv"></div>
				  </div>
				  
				  <div class="form-group mt">
				    <label class="clearfix"> <strong>Offboard Pack Documents</strong> </label>
				    <div id="offboardPackDocumentsDiv"></div>
				  </div> -->
				  
				  <div id="offboardDocumentsDiv"></div>
			</div>
            </div>
            <!-- Keep as pending -->
              <div class="tab-panes" id="tasks"> 
              <div id="offboardTasksListView"></div>
            </div>
            </div>
        </div>
      </div>
    </div>
  </section>
  
  
   <jsp:include page="../masterFooter.jsp" />
   
   
   
   
   <script>
   function offboardCandidateDetails(){
	    var offboardUUID = '${offboardUUID}';
	    /* var sourceleadUrl ='${sourceleadUrl}'; */
	    var atsUrl ='${SourceLeadWeb_atsUrl}';
	    var locationCode='${SourceLeadWeb_locationCode}'; 
	   $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	    $.ajax({
	    	type: "POST",
	    	 url: atsUrl+"/loadOffboardingCandidateDetails?${_csrf.parameterName}=${_csrf.token}&offBoardingUUID="+offboardUUID+"&locationCode="+locationCode,
	        success: function(response) {
	        	if (response.response === "AWKWARD") {
	        	    location.href = "/errorPage";
	        	} else if (response.response === "SUCCESS") {
	        		 var json = response.object;
		        	 var result = "";
					 result = result +'	 <div class="col-md-12 onboardLabel onboard_Label"> ';                                                                                         
					 result = result +'   <div class="form-horizontal">';
					 result = result +'     <p><strong>Employee Details</strong></p>';
					 result = result +'     <div class="form-group">';
					 result = result +'       <label class="col-md-3 control-label">First Name</label>';
					 result = result +'       <div class="col-md-6">';
					 result = result +'           <label>'+json.firstName+'</label>';
					 result = result +'       </div>';
					 result = result +'     </div>'; 
					 if(json.lastName != null && json.lastName != '' && json.lastName != 'undefiend'){
						 result = result +'     <div class="form-group">';
						 result = result +'       <label class="col-md-3 control-label">Last Name </label>';
						 result = result +'       <div class="col-md-6">';
						 result = result +'           <label>'+json.lastName+'</label> ';
						 result = result +'       </div>';
						 result = result +'     </div>';
					 }
					 var candidateName = '';
					 if(json.lastName != null && json.lastName != '' && json.lastName != 'undefiend'){
						 candidateName = json.firstName+" "+json.lastName;
					 }else{
						 candidateName = json.firstName;
					 }
					 $("#candidateName").html(candidateName+" <span class='small'>offboard details</span>");
					 if(json.mobileNumber != null && json.mobileNumber != '' && json.mobileNumber != 'undefined'){
						result = result +'     <div class="form-group">';
						result = result +'       <label class="col-md-3 control-label">Mobile number </label> ';
						result = result +'       <div class="col-md-6">';
						result = result +'           <label>'+json.mobileNumber+'</label>';
						result = result +'       </div>';
						result = result +'     </div>';
					}
					result = result +'     <div class="form-group">';
					result = result +'       <label class="col-md-3 control-label">Email</label> ';
					result = result +'       <div class="col-md-6">';
					result = result +'           <label>'+json.email+'</label>';
					result = result +'       </div>';
					result = result +'     </div>';
					if(json.dateOfJoinStr != null && json.dateOfJoinStr != '' && json.dateOfJoinStr != 'undefined'){
						result = result +'     <div class="form-group">';
						result = result +'       <label class="col-md-3 control-label">Date Of Join</label> ';
						result = result +'       <div class="col-md-6">';
						result = result +'           <label>'+json.dateOfJoinStr+'</label>';
						result = result +'       </div>';
						result = result +'     </div>';
					}
					result = result +'     <div class="form-group">';
					result = result +'       <label class="col-md-3 control-label">Role</label> ';
					result = result +'       <div class="col-md-6">';
					result = result +'           <label>'+json.role+'</label>';
					result = result +'       </div>';
					result = result +'     </div>';
					if(json.employmentType != null && json.employmentType != '' && json.employmentType != 'undefined') {
					 	result = result +'     <div class="form-group">';
						result = result +'       <label class="col-md-3 control-label">Employment Type</label> ';
						result = result +'       <div class="col-md-6">';
						result = result +'           <label>'+json.employmentType+'</label>';
						result = result +'       </div>';
						result = result +'     </div>';
					 }
					result = result +'     <div class="form-group">';
					result = result +'       <label class="col-md-3 control-label">End Date</label> ';
					result = result +'       <div class="col-md-6">';
					result = result +'           <label>'+json.endDateStr+'</label>';
					result = result +'       </div>';
					result = result +'     </div>';
					 
					result = result +'     <div class="form-group">';
					result = result +'       <label class="col-md-3 control-label">Reason</label> ';
					result = result +'       <div class="col-md-6">';
					result = result +'           <label>'+json.reason+'</label>';
					result = result +'       </div>';
					result = result +'     </div>';
					if(json.explanation != null && json.explanation != '' && json.explanation != 'undefined'){
						result = result +'     <div class="form-group">';
						result = result +'       <label class="col-md-3 control-label">Explanation</label> ';
						result = result +'       <div class="col-md-9 minHeight75">';
						result = result +'         <textarea readonly rows="5">'+json.explanation+'</textarea>';
						result = result +'       </div>';
						result = result +'     </div>';
					}
					if(json.noticePeriod != null && json.noticePeriod != '' && json.noticePeriod != 'undefined'){
						result = result +'     <div class="form-group">';
						result = result +'       <label class="col-md-3 control-label">Notice Period</label> ';
						result = result +'       <div class="col-md-6">';
						result = result +'           <label>'+json.noticePeriod+'</label>';
						result = result +'       </div>';
						result = result +'     </div>';
					}
						result = result +'     <div class="form-group">';
						result = result +'       <label class="col-md-3 control-label">Effective Date</label> ';
						result = result +'       <div class="col-md-6">';
						result = result +'           <label>'+json.effectiveDateStr+'</label>';
						result = result +'       </div>';
						result = result +'     </div>';
						if(json.haveToWorkInNoticePeriod != null && json.haveToWorkInNoticePeriod != '' && json.haveToWorkInNoticePeriod != 'undefined'){
							result = result +'     <div class="form-group">';
							result = result +'       <label class="col-md-3 control-label">Have to work in notice period</label> ';
							result = result +'       <div class="col-md-6">';
							if(json.haveToWorkInNoticePeriod == "Y"){
								result = result +'           <label>Yes</label>';
							} else {
								result = result +'           <label>No</label>';
							}
							result = result +'       </div>';
							result = result +'     </div>';
						 }
						result = result +'     <p><strong>Survey</strong></p>';
						if(json.surveyReason != null && json.surveyReason != '' && json.surveyReason != 'undefined'){
							result = result +'     <div class="form-group">';
							result = result +'       <label class="col-md-3 control-label">Reason</label>';
							result = result +'       <div class="col-md-6">';
							result = result +'           <label>'+json.surveyReason+'</label>';
							result = result +'       </div>';
							result = result +'     </div>'; 
						}
						if(json.surveyExplanation != null && json.surveyExplanation != '' && json.surveyExplanation != 'undefined'){
							result = result +'     <div class="form-group">';
							result = result +'       <label class="col-md-3 control-label">Explanation</label>';
							result = result +'       <div class="col-md-6">';
							result = result +'           <label>'+json.surveyExplanation+'</label>';
							result = result +'       </div>';
							result = result +'     </div>'; 
						}
						if(json.rating != null && json.rating != "" && json.rating != "undefined"){
							result = result +'     <div class="form-group">';
							result = result +'       <label class="col-md-3 control-label">Rating</label>';
							result = result +'       <div class="col-md-6">';
							result = result +'           <label>'+json.rating+'</label>';
							result = result +'       </div>';
							result = result +'     </div>'; 
						}
						result = result +'</div>';
						result = result +'</div>'; 
					 $("#offboardCandidateDetails").empty();
					 $("#offboardDocumentsDiv").empty();
					 $("#offboardTasksListView").empty();
					 $("#offboardCheckListView").empty();
					 $("#offboardCandidateDetails").append(result); 
			    	 $("#loading").removeAttr("style");
	        	} else if (response.indexOf('Sign In') !== -1) {
	        		location.replace(sourceleadUrl);
	        	} else {
	        	    showError("Operation failed, please try again");
	        	}
	        },
	    });
   }
   </script>
   
   <script>
   function getOffboardCandidateCheckList(){
	   
	   
	   var atsUrl = '${SourceLeadWeb_atsUrl}';
	   var offboardUUID = '${offboardUUID}';
	  
	   $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	   
	    getOffboardCheckList(atsUrl,offboardUUID);
	   
	    var result = "";
		
	    result = result + '<div id="sjfb-fields" class="viewTemplate checklist">';
		
		result = result + '</div>';
		
		 $("#offboardCandidateDetails").empty();
		 $("#offboardDocumentsDiv").empty();
		 $("#offboardTasksListView").empty();
		 $("#offboardCheckListView").empty();
		
		 $("#offboardCheckListView").append(result); 
		 
		 $("#loading").removeAttr("style");
   }
   </script>
   
   
   <script>
   function getOffboardingTasksList(){
	   
	   var offboardUUID = '${offboardUUID}';
	    var atsUrl ='${SourceLeadWeb_atsUrl}'; 
	    var locationCode ='${SourceLeadWeb_locationCode}';
	    $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	    $.ajax({
	    	type: "GET",
	    	 url: atsUrl+"/getOffboardCandidateTasksByOffboardUUID?${_csrf.parameterName}=${_csrf.token}&offboardUUID="+offboardUUID+"&locationCode="+locationCode, 
	        success: function(response) {
	        	if (response.response === "AWKWARD") {
	        	    location.href = "/errorPage";
	        	} else if (response.response === "SUCCESS") {
	        		var json = JSON.parse(response.object);
		        	 var result = "";
		        	 if(json.length > 0){ 
		         		result = result +'	<div class="clearfix">';
		         		result = result +'      <h4 class="pull-left">Task List</h4>';
		         		result = result +'  </div>';
		 	        	for (var i=0; i<json.length; i++) {
		 	        		var report = json[i];
		 	        		var prority = '';
		 	        		var repeat = '';
		 	        		if(report.priority != null && report.priority != '' && report.priority != 'undefined'){
		 	        			prority = report.priority;
		 	        		}else{
		 	        			prority = '----';
		 	        		}
		 	        		if(report.taskRepeat != null && report.taskRepeat != '' && report.taskRepeat != 'undefined'){
		 	        			repeat = report.taskRepeat;
		 	        		}else{
		 	        			repeat = '----';
		 	        		}
		 	        		result = result +'<div class="clearfix taskwidget_inner" id="onboardTask'+report.id+'">';
		 	        		result = result +'<div class="media-body">';
		 	        		result = result +'<div class="clearfix taskheder">';
		 	        		result = result +"<label> <span class='paddingRight'>Subject:</span> <strong class='text-primary'>"+report.subject+"</strong></label>";
		 	        		result = result +'<label class="small pull-right text-muted"> <span>'+report.taskDateStr+'</span> </label>';
		 	        		result = result +'</div>';
		 	        		result = result +'<ul class="list-inline list-description mt10">';
		 	        		result = result +'<li><span>Assign To:</span>';
		 	        		result = result +'<label>'+report.assignedName+'</label>';
		 	        		result = result +'</li>';
		 	        		result = result +'<li><span>Priority:</span>'+prority+'</li>';
		 	        		result = result +'<li><span>Repeat:</span> '+repeat+'</li>';
		 	        		result = result +'<li><span>Status:</span> '+report.taskStatus+'</li>';
		 	        		result = result +'</ul>'; 
		 	        		result = result +'</div>';
		 	        		result = result +'</div>';
		 				}
		         	} else {
		         		result = result +'<div><h4>No Tasks Found</h4></div>';
		         	}
		        	 $("#offboardCandidateDetails").empty();
		        	 $("#offboardDocumentsDiv").empty();
		    		 $("#offboardTasksListView").empty();
		    		 $("#offboardCheckListView").empty();
		    		 $("#offboardTasksListView").append(result);
		    		 $("#loading").removeAttr("style");
	        	} else if (response.indexOf('Sign In') !== -1) {
	        		location.replace(sourceleadUrl);
	        	} else {
	        	    showError("Operation failed, please try again");
	        	}
	          }
	        });
   }
   </script>
   <script>
   function getOffboardDocuments(){
	   var offboardUUID = '${offboardUUID}';
	    var atsUrl ='${SourceLeadWeb_atsUrl}';  
	  // $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	    $.ajax({
	    	type: "GET",
	    	 url: atsUrl+"/getOffboardCandidateDocumentsByOffboardUUID?${_csrf.parameterName}=${_csrf.token}&offboardUUID="+offboardUUID, 
	        success: function(response) {
	        	if (response.response === "AWKWARD") {
	        	    location.href = "/errorPage";
	        	} else if (response.response === "SUCCESS") {
	        		var json = JSON.parse(response.object);
		        	 var result = "";
		        	 result = result +'<div class="form-group">';
		        	 result = result +'<label class="clearfix"> <strong>Resignation / Termination Documents</strong> </label>';
		        	 result = result +'	<div class="clearfix mt form-group">';
		        	 if(json.resignationDocumentsList.length > 0){ 
		         		result = result +'	<div class="clearfix">';
		         		result = result +'  </div>';
		         		result = result +'	 <div class="row">';
		         		for (var i=0; i<json.resignationDocumentsList.length; i++) {
		 	        		var report = json.resignationDocumentsList[i];
		 	        		result = result +'    <div class="col-md-2 text-center mb10">';
							result = result +'      <a target="_blank" href=\"'+report.fileURL+'\"><div class="uploaded_file"> <i class="fa fa-file-text-o fa-4x"></i>  ';
							result = result +'        <p class="small mt tbldata-dotdot-100" title="'+report.fileName+'">'+report.fileName+'</p>                        ';
							result = result +'    </div></a>';
							result = result +'    </div>';  
		 				}
			         } else {
			         	result = result +'<div><h4>No Documents Found</h4></div>';
			         } 
			        	 result = result +'</div>';       
			        	 result = result +'</div>';
			        	 result = result +'	<div class="clearfix">';
			        	 result = result +'<div class="form-group">';
			        	 result = result +'<label class="clearfix"> <strong>Offboard Pack Documents</strong> </label>';
			        	 result = result +'	<div class="clearfix mt form-group">';
			        	 if(json.offboardPackDocumentsList.length > 0){ 
			         		result = result +'	<div class="clearfix">';
			         		result = result +'  </div>';
			         		result = result +'	 <div class="row">';
			         		for (var i=0; i<json.offboardPackDocumentsList.length; i++) {
			 	        		var report = json.offboardPackDocumentsList[i];
			 	        		result = result +'    <div class="col-md-2 text-center mb10">';
			 	        		result = result +'      <a target="_blank" href=\"'+report.fileURL+'\"><div class="uploaded_file"> <i class="fa fa-file-text-o fa-4x"></i>  ';
								result = result +'        <p class="small mt tbldata-dotdot-100" title="'+report.fileName+'">'+report.fileName+'</p>                        ';
								result = result +'    </div></a>';
								result = result +'    </div>';  
			 				}
				         	}else{
				         		result = result +'<div><h4>No Documents Found</h4></div>';
				         	} 
				        	 result = result +'</div>';       
				        	 result = result +'</div>';
			        	 $("#offboardCandidateDetails").empty();
			        	 $("#offboardDocumentsDiv").empty();
			    		 $("#offboardTasksListView").empty();
			    		 $("#offboardCheckListView").empty();
			    		 $("#offboardDocumentsDiv").append(result);
			    		 $("#loading").removeAttr("style");
	        	} else if (response.indexOf('Sign In') !== -1) {
	        		location.replace(sourceleadUrl);
	        	} else {
	        	    showError("Operation failed, please try again");
	        	}
	          }
	        });
   }
   </script>
   
   
   
   