<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet" href="/resources/css/chosen.min.css">
<script src="/resources/js/chosen.jquery.min.js"></script> 
<script src="/resources/js/jquery-fun.js"></script> 
<script src="/resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

 <script> 
	$(document).ready(function(){
		onboardCandidateSummaryDetails();
	});
</script>

 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
          <h2 id="candidateName"></h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>  &raquo; <a href="<c:url value='${appPath}/candidateOnBoardingList'/>">Onboard & Offboard List</a> &raquo; Candidate Onboarding Summary </div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="applicant_accept">
        <div class="clearfix onboardBtns">
        </div>
        <div class="tabbable">
          <ul class="nav nav-tabs" id="tab_bar">
            <li class="active"> <a href="#details" data-toggle="tab" onclick="onboardCandidateSummaryDetails();"> <i class="fa fa-th-list paddingRight"></i> <span>Details</span> <br>
              <small>Employee details &amp; Position details</small> </a> </li>
            <li><a href="#documents" data-toggle="tab" onclick="getTemplateContractsDocumentsDetails();getTemplateFormsDocumentsDetails();getTemplatePolicyDocumentsDetails();getTemplateSupportingDocumentsDetails();"> <i class="fa fa-files-o paddingRight"></i> <span>Documents</span> <br>
              <small>Acceptance &amp; Policy Documents</small> </a> </li>

              <li onclick="getOnboardingTasksList();"> <a href="#tasks" data-toggle="tab" id="templateTasks"> <i class="fa fa-check-square-o paddingRight"></i> <span>Tasks</span> <br>
              <small>List of tasks</small> </a></li>
             
          </ul>
          
          
          <div class="tab-content"> 
            
            <!-- Employee Details -->
            <div class="tab-pane clearfix onboard_offboad_view active" id="details">
              <div id="onboardCandidateSummaryDetailsId"></div>
            </div>
            
            
            <!-- TAB 2 -->
            
            <div class="tab-pane clearfix emp_onboarddocs" id="documents">
              <div class="col-md-12 clearfix mt">
				  <div class="form-group">
				    <label class="clearfix"> <strong>Employment Contract</strong> </label>
				    <div id="employmentContractDivId"></div>
				  </div>
				  
				  <div class="form-group mt">
				    <label class="clearfix"> <strong>Forms</strong> </label>
				    <div id="formstDivId"></div>
				  </div>
				  
				  <div class="form-group mt">
				    <label class="clearfix"> <strong>Policies</strong> </label>
				    <div id="policiesDivId"></div>
				  </div>
				  
				  <div class="form-group mt">
				    <label class="clearfix"> <strong>Supporting Information</strong> </label>
				    <div id="supportedDivId"></div>
				  </div>
				  
			</div>
            </div>
            <!-- Keep as pending -->
              <div class="tab-panes" id="tasks"> 
              <div id="onboardTasksListViewId"></div>
            </div>
            </div>
        </div>
      </div>
    </div>
  </section>
 
 <script>
 function onboardCandidateSummaryDetails(){
	 var onBoardingUUID = '${onBoardingUUID}';
    var sourceleadUrl ='${sourceleadUrl}';
    var locationCode ='${SourceLeadWeb_locationCode}';
	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	    $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/loadOnboardingCandidateDetails?${_csrf.parameterName}=${_csrf.token}&onBoardingUUID="+onBoardingUUID+"&locationCode="+locationCode, 
	        success: function(response) {
	        	if (response.response === "AWKWARD") {
	        	    location.href = "/errorPage";
	        	} else if (response.response === "SUCCESS") {
	        		var json = response.object;
		        	if(json.zipCode != null && json.zipCode != '' && json.zipCode != 'undefined'){
		        		zipCode = json.zipCode;
		        	}else{
		        		zipCode = "";
		        	}
		        	 var result = "";
					 result = result +'	 <div class="col-md-12 onboardLabel onboard_Label">                                                                                          ';
					 result = result +'   <div class="form-horizontal">                                                                                   ';
					 result = result +'     <p><strong>Employee Details</strong></p>                                                                      ';
					 result = result +'     <div class="form-group">                                                                                      ';
					 result = result +'       <label class="col-md-3 control-label">First Name </label>';
					 result = result +'       <div class="col-md-6">                                                                                      ';
					 result = result +'           <label>'+json.firstName+'</label>                                                                      ';
					 result = result +'       </div>                                                                                                      ';
					 result = result +'     </div>                                                                                                        ';
					 
					 if(json.lastName != null && json.lastName != '' && json.lastName != 'undefiend'){
						 result = result +'     <div class="form-group">                                                                                      ';
						 result = result +'       <label class="col-md-3 control-label">Last Name </label>      ';
						 result = result +'       <div class="col-md-6">                                                                                      ';
						 result = result +'           <label>'+json.lastName+'</label>                                                                                ';
						 result = result +'       </div>                                                                                                      ';
						 result = result +'     </div>                                                                                                        ';
					 }
					 
					 var candidateName = '';
					 if(json.lastName != null && json.lastName != '' && json.lastName != 'undefiend'){
						 candidateName = json.firstName+" "+json.lastName;
					 }else{
						 candidateName = json.firstName;
					 }
					 
					 $("#candidateName").html(candidateName+" <span class='small'>onboard details</span>");
					
					 if(json.address1 != null && json.address1 != '' && json.address1 != 'undefined'){ 
						 result = result +'     <div class="form-group">                                                                                      ';
						 result = result +'       <label class="col-md-3 control-label">Street Address 1 </label>';
						 result = result +'       <div class="col-md-6">                                                                                      ';
						 result = result +'           <label>'+json.address1+'</label>                                      ';
						 result = result +'       </div>                                                                                                      ';
						 result = result +'     </div>                                                                                                        ';
					 }
					 
					 if(json.address2 != null && json.address2 != '' && json.address2 != 'undefined'){
						 result = result +'     <div class="form-group">                                                                                      ';
						 result = result +'       <label class="col-md-3 control-label">Street Address 2</label>';
						 result = result +'       <div class="col-md-6">    ';
						 result = result +'           <label>'+json.address2+'</label> ';
						 result = result +'       </div>                                                                                                      ';
						 result = result +'     </div>                                                                                                        ';
					 }
					 
					 result = result +'  <div class="form-group">                                                                    ';
					 result = result +'       <label class="col-md-3 control-label">Location </label>        ';
					 result = result +'       <div class="col-md-6">                                   ';
					 result = result +'           <label>'+json.location+'</label>                                 ';
					 result = result +'       </div>                                                   ';
					 result = result +'     </div>                                                     ';
					 
					 result = result +'  <div class="form-group">                                                                    ';
					 result = result +'       <label class="col-md-3 control-label">Country </label>        ';
					 result = result +'       <div class="col-md-6">                                   ';
					 result = result +'           <label>'+json.country+'</label>                                 ';
					 result = result +'       </div>                                                   ';
					 result = result +'     </div>                                                     ';
				    
					 if(json.state != null && json.state != '' && json.state != 'undefined' ){
						 result = result +'     <div class="form-group">                                   ';
						 result = result +'       <label class="col-md-3 control-label">State</label>      ';
						 result = result +'       <div class="col-md-6">                                   ';
						 result = result +'           <label>'+json.state+'</label>                             ';
						 result = result +'       </div>                                                   ';
						 result = result +'     </div>     ';
					 }
				                                                                                     
					 if(json.city != null && json.city != '' && json.city != 'undefined' ){
						 result = result +'     <div class="form-group">                                   ';
						 result = result +'       <label class="col-md-3 control-label">City</label>       ';
						 result = result +'       <div class="col-md-6">                                   ';
						 result = result +'           <label>'+json.city+'</label>                             ';
						 result = result +'       </div>                                                   ';
						 result = result +'     </div>                                                     ';
						
					 }
					 if(zipCode != ''){
						 result = result +'     <div class="form-group">                                   ';
						 result = result +'       <label class="col-md-3 control-label">Zipcode</label>    ';
						 result = result +'       <div class="col-md-6">                ';
						 result = result +'           <label>'+zipCode+'</label>   ';
						 result = result +'       </div>                                ';
						 result = result +'     </div>                                  ';
					 }
						 
					 
					 if(json.personalEmail != null && json.personalEmail != '' && json.personalEmail != 'undefined'){
						 result = result +'                                                                          ';
						 result = result +'     <div class="form-group">                                             ';
						 result = result +'       <label class="col-md-3 control-label">Personal Email </label> ';
						 result = result +'       <div class="col-md-6">                                                                                      ';
						 result = result +'           <label>'+json.personalEmail+'</label>                                                            ';
						 result = result +'       </div>                                                                                                      ';
						 result = result +'     </div>                                                                                                        ';
					 }
					 
					 if(json.companyEmail != null && json.companyEmail != '' && json.companyEmail != 'undefined'){
						 result = result +'                                                                          ';
						 result = result +'     <div class="form-group">                                             ';
						 result = result +'       <label class="col-md-3 control-label">Company Email </label> ';
						 result = result +'       <div class="col-md-6">                                                                                      ';
						 result = result +'           <label>'+json.companyEmail+'</label>                                                            ';
						 result = result +'       </div>                                                                                                      ';
						 result = result +'     </div>                                                                                                        ';
					 }
				    
					if(json.mobileNumber != null && json.mobileNumber != '' && json.mobileNumber != 'undefined'){
						 result = result +'     <div class="form-group">                                                                                      ';
						 result = result +'       <label class="col-md-3 control-label">Mobile number </label>  ';
						 result = result +'       <div class="col-md-6">                                                                                      ';
						 result = result +'           <label>'+json.mobileNumber+'</label>                                                                           ';
						 result = result +'       </div>                                                                                                      ';
						 result = result +'     </div>                                                                                                        ';
					}
					
					 if(json.roleName != null && json.roleName != '' && json.roleName != 'undefined'){
						 result = result +'   <div class="form-group">                                                                                        ';
						 result = result +'       <label class="col-md-3 control-label">Role </label>                                                          ';
						 result = result +'       <div class="col-md-6">                                                                                      ';
						 result = result +'           <label>'+json.roleName+'</label>                                                                                   ';
						 result = result +'       </div>                                                                                                      ';
						 result = result +'     </div>                                                                                                        ';
					 }
				     
					 
				     result = result +'       <div class="form-group">                                                                                    ';
					 result = result +'       <label class="col-md-3 control-label">Date Of Join</label>      ';
					 result = result +'       <div class="col-md-6">                                                                                      ';
					 result = result +'         <label>'+json.joiningDateStr+'                                                                                        ';
					 result = result +'       </label></div>                                                                                              ';
					 result = result +'     </div>                                                                                                        ';
					
					 if(json.endDateStr != null && json.endDateStr != '' && json.endDateStr != 'undefined'){
						 result = result +'       <div class="form-group">                                                                                    ';
						 result = result +'       <label class="col-md-3 control-label">End date</label>        ';
						 result = result +'       <div class="col-md-6">                                                                                      ';
						 result = result +'         <label>'+json.endDateStr+'</label>                                                                                 ';
						 result = result +'       </div>                                                                                                      ';
						 result = result +'     </div>                                                                                                        ';
					 }
					
					 if(json.paymentType != null && json.paymentType != '' && json.paymentType != 'undefined'){
					 result = result +'     <div class="form-group">                                                                                      ';
					 result = result +'       <label class="col-md-3 control-label">Rate / Salary </label>  ';
					 result = result +'       <div class="col-md-6">                                                                                      ';
					 result = result +'         <label>'+json.paymentType+' '+json.salaryRate+'/'+json.paymentMode+'</label>                                                                                  ';
					 result = result +'       </div>                                                                                                      ';
					 result = result +'     </div>                                                                                                        ';
					 }
					 
					 else{
						 result = result +'     <div class="form-group">                                                                                      ';
						 result = result +'       <label class="col-md-3 control-label">Rate / Salary </label>  ';
						 result = result +'       <div class="col-md-6">                                                                                      ';
						 result = result +'         <label>'+json.salaryRate+'</label>                                                                                  ';
						 result = result +'       </div>                                                                                                      ';
						 result = result +'     </div>                                                                                                        ';
						 }
					 if(json.managerName != null && json.managerName != '' && json.managerName != 'undefined'){
						 result = result +'     <div class="form-group">                                                                                      ';
						 result = result +'       <label class="col-md-3 control-label">Manager</label>                                                       ';
						 result = result +'       <div class="col-md-6">                                                                                      ';
						 result = result +'                 <label>'+json.managerName+'</label>                                                                          ';
						 result = result +'         </div>                                                                                                    ';
						 result = result +'     </div>                                                                                                        ';
					 }
					 
					 if(json.teamName != null && json.teamName != '' && json.teamName != 'undefined'){
						 result = result +'     <div class="form-group">                                                                                      ';
						 result = result +'       <label class="col-md-3 control-label">Team</label>                                                          ';
						 result = result +'       <div class="col-md-6">                                                                                      ';
						 result = result +'           <label>'+json.teamName+'</label>                                                                                     ';
						 result = result +'       </div>                                                                                                      ';
						 result = result +'     </div>                                                                                                        ';
					 }
					 
					if(json.employmentType != null && json.employmentType != '' && json.employmentType != 'undefined'){
						 result = result +'     <div class="form-group">                                                                                      ';
						 result = result +'       <label class="col-md-3 control-label">Employment Type</label>                                               ';
						 result = result +'       <div class="col-md-6">                                                                                      ';
						 result = result +'           <label>'+json.employmentType+'</label>                                                                        ';
						 result = result +'       </div>                                                                                                      ';
						 result = result +'     </div>                                                                                                        ';
					}
					
					 
					 if(json.probationNotice != null && json.probationNotice !='' && json.probationNotice != 'undefined'){
						 result = result +'       <div class="form-group">                                                                                    ';
						 result = result +'       <label class="col-md-3 control-label">Probation Notice</label>                                              ';
						 result = result +'       <div class="col-md-6">                                                                                      ';
						 result = result +'           <label>'+json.probationNotice+'</label>                                                                                 ';
						 result = result +'       </div>                                                                                                      ';
						 result = result +'     </div>                                                                                                        ';
					 }
					
					if(json.terminationNotice != null && json.terminationNotice != '' && json.terminationNotice != 'undefined'){
						result = result +'       <div class="form-group">                                                                                    ';
						 result = result +'       <label class="col-md-3 control-label">Termination Notice</label>                                            ';
						 result = result +'       <div class="col-md-6">                                                                                      ';
						 result = result +'           <label>'+json.terminationNotice+'</label>                                                                                  ';
						 result = result +'       </div>                                                                                                      ';
						 result = result +'     </div>                                                                                                        ';
					}
					 
					 if(json.dueDateStr != null && json.dueDateStr != '' && json.dueDateStr != 'undefined'){
						 result = result +'       <div class="form-group">                                                                                    ';
						 result = result +'       <label class="col-md-3 control-label">Due Date</label>                                                      ';
						 result = result +'       <div class="col-md-6">                                                                                      ';
						 result = result +'        <label>'+json.dueDateStr+'                                                                                          ';
						 result = result +'       </label></div>                                                                                              ';
						 result = result +'     </div>                                                                                                        ';
					 }
					 

					 if(json.specialConditions != null && json.specialConditions != '' && json.specialConditions != 'undefined'){
						  result = result +'       <div class="form-group">                                                                                    ';
							 result = result +'       <label class="col-md-3 control-label">Special Conditions</label>                                            ';
							 result = result +'       <div class="col-md-6">                                                                                      ';
							 result = result +'           <p>                                                                                                     ';
							 result = result +'           '+json.specialConditions+'                              ';
							 result = result +'           </p>                                                                                                    ';
							 result = result +'       </div>                                                                                                      ';
							 result = result +'     </div>                                                                                                        ';
					 }
					 
					 if(json.gender != null && json.gender != '' && json.gender != 'undefined'){
						  result = result +'       <div class="form-group">                                                                                    ';
							 result = result +'       <label class="col-md-3 control-label">Gender</label>                                            ';
							 result = result +'       <div class="col-md-6">                                                                                      ';
							 result = result +'           <p>                                                                                                     ';
							 result = result +'           '+json.gender+'                              ';
							 result = result +'           </p>                                                                                                    ';
							 result = result +'       </div>                                                                                                      ';
							 result = result +'     </div>                                                                                                        ';
					 }
					 
					 if(json.dateOfBirth != null && json.dateOfBirth != '' && json.dateOfBirth != 'undefined'){
						  result = result +'       <div class="form-group">                                                                                    ';
							 result = result +'       <label class="col-md-3 control-label">Date of Birth</label>                                            ';
							 result = result +'       <div class="col-md-6">                                                                                      ';
							 result = result +'           <p>                                                                                                     ';
							 result = result +'           '+json.dateOfBirth+'                              ';
							 result = result +'           </p>                                                                                                    ';
							 result = result +'       </div>                                                                                                      ';
							 result = result +'     </div>                                                                                                        ';
					 }
					 
					 if(json.employmentEligibility != null && json.employmentEligibility != '' && json.employmentEligibility != 'undefined'){
						  result = result +'       <div class="form-group">                                                                                    ';
							 result = result +'       <label class="col-md-3 control-label">Employment Eligibility</label>                                            ';
							 result = result +'       <div class="col-md-6">                                                                                      ';
							 result = result +'           <p>                                                                                                     ';
							 result = result +'           '+json.employmentEligibility+'                              ';
							 result = result +'           </p>                                                                                                    ';
							 result = result +'       </div>                                                                                                      ';
							 result = result +'     </div>                                                                                                        ';
					 }
					 if(json.uscisAlien != null && json.uscisAlien != '' && json.uscisAlien != 'undefined'){
						  result = result +'       <div class="form-group">                                                                                    ';
							 result = result +'       <label class="col-md-3 control-label">USCIS /Alien registration Number</label>                                            ';
							 result = result +'       <div class="col-md-6">                                                                                      ';
							 result = result +'           <p>                                                                                                     ';
							 result = result +'           '+json.uscisAlien+'                              ';
							 result = result +'           </p>                                                                                                    ';
							 result = result +'       </div>                                                                                                      ';
							 result = result +'     </div>                                                                                                        ';
					 }
					 if(json.authorizedWorkUntil != null && json.authorizedWorkUntil != '' && json.authorizedWorkUntil != 'undefined'){
						  result = result +'       <div class="form-group">                                                                                    ';
							 result = result +'       <label class="col-md-3 control-label">Authorized work until</label>                                            ';
							 result = result +'       <div class="col-md-6">                                                                                      ';
							 result = result +'           <p>                                                                                                     ';
							 result = result +'           '+json.authorizedWorkUntil+'                              ';
							 result = result +'           </p>                                                                                                    ';
							 result = result +'       </div>                                                                                                      ';
							 result = result +'     </div>                                                                                                        ';
					 }
				   
					 if(json.shift != null && json.shift != '' && json.shift != 'undefined'){
						  result = result +'       <div class="form-group">                                                                                    ';
							 result = result +'       <label class="col-md-3 control-label">Shift</label>                                            ';
							 result = result +'       <div class="col-md-6">                                                                                      ';
							 result = result +'           <p>                                                                                                     ';
							 result = result +'           '+json.shiftName+'                              ';
							 result = result +'           </p>                                                                                                    ';
							 result = result +'       </div>                                                                                                      ';
							 result = result +'     </div>                                                                                                        ';
					 }
					 
					
					/*  if(json.onboardTemplateId != null && json.onboardTemplateId != '' && json.onboardTemplateId != 'undefined'){
						 result = result +'        <div class="form-group">                                                                                   ';
						 result = result +'            <label class="col-md-3 control-label">Onboard Template </label> ';
						 result = result +'       <div class="col-md-6">                                                                                    ';
						 result = result +'             <p id="onboardingTemplate"></p>                                                                                  ';
						 result = result +'       </div>                                                                                                    ';
						 result = result +'     </div>                                                                                                      ';
					 } */
					 
					 result = result +'   </div>                                                                                                        ';
					 result = result +'     </div>';
		        	 
					 $("#onboardTasksListViewId").empty();
					 $("#onboardCandidateSummaryDetailsId").empty();
	            	 
	            	 $("#onboardCandidateSummaryDetailsId").append(result);
	            	 //loadOnBoardingTemplates(json.onboardTemplateId);
	        	} else if (response.indexOf('Sign In') !== -1) {
	        		location.replace(sourceleadUrl);
	        	} else {
	        	    showError("Operation failed, please try again");
	        	}
	        },
	    });
 }
 </script>
 
 <!-- <script>
function loadOnBoardingTemplates(onboardTemplateId){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/getOnboardingTemplatesForCandidateOnBoarding?${_csrf.parameterName}=${_csrf.token}",	    	   
	    success: function(data) {
	    	 var json = JSON.parse(data);
        	 var result = "";
        	
        	for (var i=0; i<json.length; i++)
			{
        		var report = json[i];
        			result = result + report.templateName +' , ';
        		
			}
        	 $("#onboardingTemplate").empty();
           	 $("#onboardingTemplate").append(result); 
	    }
   });
}
</script> -->





<script>
function getTemplateContractsDocumentsDetails(){
	var onBoardingUUID = '${onBoardingUUID}';
	var documentType = "Contracts"
		var sourceleadUrl ='${sourceleadUrl}';
	     $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/getCandidateOnboardTemplateDocumentByonBoardingUUID?${_csrf.parameterName}=${_csrf.token}&onBoardingUUID="+onBoardingUUID+"&documentType="+documentType, 
		        success: function(response) {
		        	if (response && response.response === "INVALID_USER") {
		        	    showError(response.responseMessage);
		        	} else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		var json = JSON.parse(response.object);
			        	var result = "";
			        	result = result +'	 <div class="row">                                                       ';
			        	if(json.length > 0){
				        	for (var i = 0; i < json.length; i++) { 
		               		 var report = json[i]; 
								result = result +'    <div class="col-md-2 text-center mb10">                                ';
								result = result +'      <a target="_blank" href=\"'+report.fileURL+'\"><div class="uploaded_file"> <i class="fa fa-file-text-o fa-4x"></i>  ';
								if(report.documentStatus != 'ACCEPTED'){
									result = result +'<p class="small mt tbldata-dotdot-100" title="'+report.fileName+'">'+report.fileName+'</p>';
								}else if(report.documentStatus == 'ACCEPTED'){
									result = result +'<p class="small mt tbldata-dotdot-100" title="'+report.uploadedDocFileName+'">'+report.uploadedDocFileName+'</p>';
									
								}
								if(report.documentStatus == 'NEW'){
									result = result +'<i class="fa fa-arrow-right text-info permission"></i></a>';
								}
								else if(report.documentStatus == 'PENDING'){
									result = result +'<i class="fa fa-clock-o text-info permission"></i></a>';
								}else if(report.documentStatus == 'ACCEPTED'){
									result = result +'<i class="fa fa-check text-success permission"></i></a>';
								}
								result = result +'    </div>                                                                 ';
								result = result +'    </div>                                                                 ';
				        	}
			        	}else{
			        		result = result +'    <div class="col-md-2 text-center mb10">  ';
			        		result = result +'<p>No Documents Found</p>';
			        		result = result +'    </div> ';
			        	}
						result = result +'    </div>                                                                 ';
						 $("#onboardTasksListViewId").empty();
	                	 $("#employmentContractDivId").empty();
	                	 $("#employmentContractDivId").append(result);
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
function getTemplateFormsDocumentsDetails(){
	var onBoardingUUID = '${onBoardingUUID}';
	var documentType = "Forms"
		var sourceleadUrl ='${sourceleadUrl}';
	     $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/getCandidateOnboardTemplateDocumentByonBoardingUUID?${_csrf.parameterName}=${_csrf.token}&onBoardingUUID="+onBoardingUUID+"&documentType="+documentType, 
		        success: function(response) {
		        	if (response && response.response === "INVALID_USER") {
		        	    showError(response.responseMessage);
		        	} else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		var json = JSON.parse(response.object);
			        	var result = "";
			        	result = result +'	 <div class="row">';
			        	if(json.length > 0){
				        	for (var i = 0; i < json.length; i++) { 
		               		 var report = json[i]; 
								result = result +'    <div class="col-md-2 text-center mb10">                                ';
								result = result +'      <a target="_blank" href=\"'+report.fileURL+'\"><div class="uploaded_file"> <i class="fa fa-file-text-o fa-4x"></i>  ';
								if(report.documentStatus != 'ACCEPTED'){
									result = result +'<p class="small mt tbldata-dotdot-100" title="'+report.fileName+'">'+report.fileName+'</p>';
								}else if(report.documentStatus == 'ACCEPTED'){
									result = result +'<p class="small mt tbldata-dotdot-100" title="'+report.uploadedDocFileName+'">'+report.uploadedDocFileName+'</p>';
								}
								if(report.documentStatus == 'NEW'){
									result = result +'<i class="fa fa-arrow-right text-info permission"></i></a>';
								}
								else if(report.documentStatus == 'PENDING'){
									result = result +'<i class="fa fa-clock-o text-info permission"></i></a>';
								}else if(report.documentStatus == 'ACCEPTED'){
									result = result +'<i class="fa fa-check text-success permission"></i></a>';
								}
								result = result +'    </div>';
								result = result +'    </div>';
				        	}
			        	}else{
			        		result = result +'    <div class="col-md-2 text-center mb10">  ';
			        		result = result +'<p>No Documents Found</p>';
			        		result = result +'    </div> ';
			        	}
						result = result +'    </div>';
						 $("#onboardTasksListViewId").empty();
	                	 $("#formstDivId").empty();
	                	 $("#formstDivId").append(result);
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
function getTemplatePolicyDocumentsDetails(){
	var onBoardingUUID = '${onBoardingUUID}';
	var documentType = "Policies"
		var sourceleadUrl ='${sourceleadUrl}';
	     $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/getCandidateOnboardTemplateDocumentByonBoardingUUID?${_csrf.parameterName}=${_csrf.token}&onBoardingUUID="+onBoardingUUID+"&documentType="+documentType, 
		        success: function(response) {
		        	if (response && response.response === "INVALID_USER") {
		        	    showError(response.responseMessage);
		        	} else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		var json = JSON.parse(response.object);
			        	var result = "";
			        	result = result +'	 <div class="row">                                                       ';
			        	if(json.length > 0){
			        		for (var i = 0; i < json.length; i++) { 
			               		 var report = json[i]; 
									result = result +'    <div class="col-md-2 text-center mb10">                                ';
									result = result +'      <a target="_blank" href=\"'+report.fileURL+'\"><div class="uploaded_file"> <i class="fa fa-file-text-o fa-4x"></i>  ';
									result = result +'        <p class="small mt tbldata-dotdot-100" title="'+report.fileName+'">'+report.fileName+'</p>                        ';
									if(report.documentStatus == 'NEW'){
										result = result +'<i class="fa fa-arrow-right text-info permission"></i></a>';
									}
									else if(report.documentStatus == 'PENDING'){
										result = result +'<i class="fa fa-clock-o text-info permission"></i></a>';
									}else if(report.documentStatus == 'ACCEPTED'){
										result = result +'<i class="fa fa-check text-success permission"></i></a>';
									}
									result = result +'    </div>                                                                 ';
									result = result +'    </div>                                                                 ';
					        	}
			        	} else {
			        		result = result +'    <div class="col-md-2 text-center mb10">  ';
			        		result = result +'<p>No Documents Found</p>';
			        		result = result +'    </div> ';
			        	}
						result = result +'    </div>                                                                 ';
						 $("#onboardTasksListViewId").empty();
	                	 $("#policiesDivId").empty();
	                	 $("#policiesDivId").append(result);
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
function getTemplateSupportingDocumentsDetails(){
	var onBoardingUUID = '${onBoardingUUID}';
	var documentType = "Supported"
		var sourceleadUrl ='${sourceleadUrl}';
	     $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/getCandidateOnboardTemplateDocumentByonBoardingUUID?${_csrf.parameterName}=${_csrf.token}&onBoardingUUID="+onBoardingUUID+"&documentType="+documentType, 
		        success: function(response) {
		        	if (response && response.response === "INVALID_USER") {
		        	    showError(response.responseMessage);
		        	} else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		var json = JSON.parse(response.object);
			        	var result = "";
			        	result = result +'	 <div class="row">                                                       ';
			        	if(json.length > 0){
			        	for (var i = 0; i < json.length; i++) { 
		               		 var report = json[i]; 
								result = result +'    <div class="col-md-2 text-center mb10">                                ';
								result = result +'      <a target="_blank" href=\"'+report.fileURL+'\"><div class="uploaded_file"> <i class="fa fa-file-text-o fa-4x"></i>  ';
								result = result +'        <p class="small mt tbldata-dotdot-100" title="'+report.fileName+'">'+report.fileName+'</p>                        ';
								result = result +'        <i class="fa fa-info-circle text-info permission"></i> </div></a>         ';
								result = result +'    </div>                                                                 ';
				        	}
			        	} else {
			        		result = result +'    <div class="col-md-2 text-center mb10">  ';
			        		result = result +'<p>No Documents Found</p>';
			        		result = result +'    </div> ';
			        	}
						result = result +'    </div>                                                                 ';
						 $("#onboardTasksListViewId").empty();
	                	 $("#supportedDivId").empty();
	                	 $("#supportedDivId").append(result);
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
function getOnboardingTasksList(){
	var onBoardingUUID = '${onBoardingUUID}';
	var sourceleadUrl = '${sourceleadUrl}';
	var locationId = '${SourceLeadWeb_orgLocationId}';
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
	   	type: "GET",
	    url: sourceleadUrl+"/viewCandidateOnboardTasksByOnboardingUUID?${_csrf.parameterName}=${_csrf.token}&onBoardingUUID="+onBoardingUUID+"&locationId="+locationId,	    	   
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
	         	}else{
	         		result = result +'<div><h4>No Tasks Found</h4></div>';
	         	}
	    		 $("#onboardTasksListViewId").empty();
	           	 $("#onboardTasksListViewId").append(result); 
	    	} else if (response.indexOf('Sign In') !== -1) {
	    		location.replace(sourceleadUrl);
	    	} else {
	    	    showError("Operation failed, please try again");
	    	}
	    }
   }); 
  $("#loading").removeAttr("style");
	
}
</script>
 <jsp:include page="../masterFooter.jsp" />