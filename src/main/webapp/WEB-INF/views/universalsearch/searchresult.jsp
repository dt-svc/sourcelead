<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
$(document).ready(function(){
   var searchType = "${searchType}";
   var searchValue = "${searchValue}";
   if(searchType == "Associate"){
	   var isAssociate = "Y";
	   var searchType = "Candidate";
   }else if(searchType == "Candidate"){
	   var isAssociate = "N";
   }
   
   $("#searchKey").val(searchValue);
   
   if(searchType=="All"){
	   getSearchResultsForAll(searchType,searchValue);
   }
   else{
	   getSearchResults(searchType,searchValue,isAssociate);
   }
});
</script>


<section class="content-wraper">
<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Universal Search Results</h2>
		</div>
		<div class="pull-right brud-crum hide"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='${appPath}/projectDashboard'/>">ProjectList</a> &raquo;Create Project
	</section>
	<div class="clearfix">
<div id="loading"></div>	
<div id ="searchList"></div>


	</div>
	</section>

<script type="text/javascript">
function getSearchResults(searchType,searchValue,isAssociate){
	
	  $.ajax({
	   	type: "GET",
	    url: sourceleadUrl+"/universalSearch?"+tokenParamName+"="+tokenValue+"&searchType="+searchType+"&searchValue="+searchValue+"&isAssociate="+isAssociate,	    	   
	    success: function(data) {
	    	var result='';
	    	var json = JSON.parse(data);
	    	var checkSize = json.length;
            if(checkSize>5){
                checkSize = 5;
            }
            else{
                checkSize = json.length;
            }
	    	result = result+'<div class="search-result-widget clearfix">';
	    	
	    	/* All,Users,Requirement,Candidate,Clients,Vendors,Projects*/
	    	
	    	 /*========== Universal Search list of Requirement ==========*/ 
	    	if(searchType == 'Jobs'){
	    		if(checkSize > 0){
	    			result = result+'   <h4>Jobs</h4>';
	   	    	 for (var i = 0; i < checkSize; i++) {
	   	             var report= json[i];
	   	             var location = "";
	   	             var exp = "";
	   			    	result = result+'  <div class="srchresult clearfix">';
	   			    	
		                result = result+' <figure class="image_parent">';
				    	result = result+' <i class="fa fa-briefcase fa-2x"></i>';
				    	result = result+' </figure>';
	   			    	
	   			    	result = result+'      <div class="srchresult-content col-xs-12 col-md-12">';
	   			    	if(report.jobTitle !=null && report.jobTitle != '' && report.jobTitle !="undefined" && report.jobType !=null && report.jobType != '' && report.jobType !="undefined"){
	   			    		result = result+'<p><label><strong><a href="/app/requirementSummary?requirementUUID='+report.requirementUUID+'">'+report.jobTitle+'</a></strong></label>, <label>'+report.jobType+'</label>';	
	   			    	}else{
	   			    		result = result+'<p><label><strong><a href="/app/requirementSummary?requirementUUID='+report.requirementUUID+'">'+report.jobTitle+'</a></strong></label></p>';	
	   			    	}
	   			    	if(report.city !=null && report.city != '' && report.city !="undefined"){
	   			    		result = result+'          <p><label>'+report.city+' , '+report.state+' , '+report.country+'</label>';
	   			    	}else if(report.state !=null && report.state != '' && report.state !="undefined"){
	   			    		result = result+'          <p><label>'+report.state+' , '+report.country+'</label>';
	   			    	}else if(report.country !=null && report.country != '' && report.country !="undefined"){
	   			    		result = result+'          <p><label>'+report.country+'</label>';
	   			    	}
	   			    	
	   			    	result = result+'          </p>';
	   			    	if(report.clientName !=null && report.clientName != '' && report.clientName !='undefined'){
	   			    		result = result+'          <p><label><strong>'+report.clientName+'</strong></label></p>';	
	   			    	}
	   			    	
	   			    	if(report.minExp !=null && report.minExp != '' && report.minExp !="undefined" && report.maxExp !=null && report.maxExp != '' && report.maxExp !="undefined"){
	   			    		result = result+'          <p><label class="fa fa-black-tie" title="Experience"> '+report.minExp+' - '+report.maxExp+' years</label></p>';
	   			    	}else if(report.minExp !=null && report.minExp != '' && report.minExp !="undefined"){
	   			    		result = result+'          <p><label class="fa fa-black-tie" title="Experience"> '+report.minExp+' years</label></p>';
	   			    	}else if(report.maxExp !=null && report.maxExp != '' && report.maxExp !="undefined"){
	   			    		result = result+'          <p><label class="fa fa-black-tie" title="Experience"> '+report.maxExp+' years</label></p>';
	   			    	}
	   			    	
	   			    	if(report.skills !=null && report.skills != '' && report.skills != 'undefined'){
	   			    		result = result+'<p><label>'+report.skills+'</label></p>';
	   			    	}
	   			    	result = result+'<span href="#" class="reqstatus-btn">'+report.requirementSubmissionStatus+'</span>';
	   			    	result = result+'      </div>';
	   			    	result = result+'  </div>';
	   			     
	   	   	    	 if(checkSize>=5 && i==4 ){
	   	   	    	    result = result+'  <div class="clearfix">';
	   	   		    	result = result+'      <a href="#" title="View More" class="pull-right btn btn-sm btn-default" onclick="loadRequirement();">View More</a>';
	   	   		    	result = result+'  </div>';
	   	   	    	 }
	   	    	 }
	   	    	
	    		}else{
	    			result = result+'<div class="noRecords"><img src="/resources/images/norecords.jpg" alt=""/></div>';
	    		}
	    	}
	    	
	    	 /*========== Universal Search list of Requirement End ==========*/ 
	    	
	    	 /*========== Universal Search list of Candidate ==========*/ 
	    	else if(searchType == 'Candidate'){
	    		if(checkSize > 0){
	    			if(isAssociate == "Y"){
	    				result = result+'   <h4>Associates</h4>';		
	    			}else{
	    				result = result+'   <h4>Candidates</h4>';
	    			}
	    		
		    	 for (var i = 0; i < checkSize; i++) {
		             var report= json[i];
		             var location = "";
		             var experience = "";
				    	result = result+'  <div class="srchresult clearfix">';
				    	
				    	
		                result = result+' <figure class="image_parent">';
				    	result = result+' <i class="fa fa-user-circle fa-2x"></i>';
				    	result = result+' </figure>';
				    	
				    	result = result+'      <div class="srchresult-content col-xs-12 col-md-12">';
				    	
				    	if(report.experienceYears !=null && report.experienceYears !='' && report.experienceYears !='undefined' && report.experienceMonths !=null && report.experienceMonths !='' && report.experienceMonths !='undefined'){
 				    		result = result+'<p><label><strong><a href="#" onclick="candidateSearchSideBar('+report.candidateId+',\''+isAssociate+'\')">'+report.candidateName+'</a></strong></label>, <label>'+report.designationType+'</label>, <label>'+report.experienceYears+' Years - '+report.experienceMonths+' Months</label>';
				    		 }else if(report.experienceYears !=null && report.experienceYears !='' && report.experienceYears !='undefined'){
				    		result = result+'<p><label><strong><a href="#" onclick="candidateSearchSideBar('+report.candidateId+',\''+isAssociate+'\')">'+report.candidateName+'</a></strong></label>, <label>'+report.designationType+'</label>, <label>'+report.experienceYears+' Years</label>';
				    		 }else if(report.experienceMonths !=null && report.experienceMonths !='' && report.experienceMonths !='undefined'){
				    		result = result+'<p><label><strong><a href="#" onclick="candidateSearchSideBar('+report.candidateId+',\''+isAssociate+'\')">'+report.candidateName+'</a></strong></label>, <label>'+report.designationType+'</label>, <label>'+report.experienceMonths+' Months</label>';
				    		}else{													
				    			result = result+'<p><label><strong><a href="#" onclick="candidateSearchSideBar('+report.candidateId+',\''+isAssociate+'\')">'+report.candidateName+'</a></strong></label>, <label>'+report.designationType+'</label>';
				    		}
					    	
					    	if(report.mobilePhone !=null && report.mobilePhone !='' && report.mobilePhone !='undefined'){
					    		if(report.city !=null && report.city != '' && report.city !="undefined"){
						    		result = result+'<p><label><strong>'+report.email+'</strong></label>, <label>'+report.mobilePhone+'</label>, <label>'+report.city+' , '+report.state+' , '+report.country+'</label>';
						    	}else if(report.state !=null && report.state != '' && report.state !="undefined"){
						    		result = result+'<p><label><strong>'+report.email+'</strong></label>, <label>'+report.mobilePhone+'</label>, <label>'+report.state+' , '+report.country+'</label>';
						    	}else if(report.country !=null && report.country != '' && report.country !="undefined"){
						    		result = result+'<p><label><strong>'+report.email+'</strong></label>, <label>'+report.mobilePhone+'</label>, <label>'+report.country+'</label>';
						    	}else{
						    		result = result+'<p><label><strong>'+report.email+'</strong></label>, <label>'+report.mobilePhone+'</label>';	
						    	}
					    	}else{
					    		if(report.city !=null && report.city != '' && report.city !="undefined"){
							    		result = result+'<p><label><strong>'+report.email+'</strong></label>,<label>'+report.city+' , '+report.state+' , '+report.country+'</label>';
							    	}else if(report.state !=null && report.state != '' && report.state !="undefined"){
							    		result = result+'<p><label><strong>'+report.email+'</strong></label>, <label>'+report.state+' , '+report.country+'</label>';
							    	}else if(report.country !=null && report.country != '' && report.country !="undefined"){
							    		result = result+'<p><label><strong>'+report.email+'</strong></label>, <label>'+report.country+'</label>';
							    	}else{
							    		result = result+'<p><label><strong>'+report.email+'</strong></label>';	
							    	}
					    	}
				    
				    	result = result+'          </p>';
				    	if(report.skillSet !=null && report.skillSet != '' && report.skillSet !="undefined"){
				    		result = result+'          <p><label>'+report.skillSet+'</label></p>';
				    	}
				    	result = result+'      </div>';
				    	result = result+'  </div>';
				    	if(checkSize>=5 && i==4 ){
				    	    result = result+'  <div class="clearfix">';
					    	result = result+'      <a href="#" title="View More" class="pull-right btn btn-sm btn-default" onclick="loadCandidate();">View More</a>';
					    	result = result+'  </div>';
				    	  }
	            	}
		    	 
	    		}else{
	    			result = result+'<div class="noRecords"><img src="/resources/images/norecords.jpg" alt=""/></div>';
	    		}
	    	}
	    	 /*========== Universal Search list of Candidate End ==========*/ 
	    	 
	    	 
	     /*========== Universal Search list of Client ==========*/ 	
	    	else if(searchType == 'Client'){
	    		if(checkSize > 0){
	    		result = result+'   <h4>Clients</h4>';
	    		for (var i = 0; i < checkSize; i++) {
		             var report= json[i];
		             var pattern = new RegExp(/["'"]/g);
			    	 var orgName = (report.orgName).replace(pattern, "\\'");
		             var name_location = "";
		                result = result+'  <div class="srchresult clearfix">';
		                
		                
		                result = result+' <figure class="image_parent">';
				    	result = result+' <i class="fa fa-user-plus fa-2x"></i>';
				    	result = result+' </figure>';
				    	
				    	
			    		
				    	result = result+'      <div class="srchresult-content col-xs-12 col-md-12">';
				    	if(report.orgName !=null && report.orgName != '' && report.orgName !="undefined"){
				    		result = result+'<p><label><strong><a href="#" onclick="clientSearchSideBar('+report.responderLocationId+','+report.orgRelationId+',\''+orgName+'\')">'+report.orgName+'</a></strong></label></p>';	

				    		//name_location = name_location + report.orgName+",";
				    	}
				    	
				    	if(report.city !=null && report.city != '' && report.city !="undefined"){
	   			    		result = result+'          <p><label>'+report.city+' , '+report.state+' , '+report.country+'</label>';
	   			    	}else if(report.state !=null && report.state != '' && report.state !="undefined"){
	   			    		result = result+'          <p><label>'+report.state+' , '+report.country+'</label>';
	   			    	}else if(report.country !=null && report.country != '' && report.country !="undefined"){
	   			    		result = result+'          <p><label>'+report.country+'</label>';
	   			    	}
				    	
				    	result = result+'          </p>';
				    	if(report.createdBy !=null && report.createdBy != '' && report.createdBy !="undefined"){
				    		result = result+'          <p><label>'+report.createdBy+'</label></p>';
				    	}
				    	result = result+'      </div>';
				    	result = result+'  </div>';
				    	if(checkSize>=5 && i==4 ){
				    	    result = result+'  <div class="clearfix">';
					    	result = result+'      <a href="#" onclick="loadClient();" title="View More" class="pull-right btn btn-sm btn-default">View More</a>';
					    	result = result+'  </div>';
			    		}
	    			}
		    		
	    		}else{
	    			result = result+'<div class="noRecords"><img src="/resources/images/norecords.jpg" alt=""/></div>';
	    		}
	    	}
	    	 /*========== Universal Search list of Client End ==========*/
	    	
	    	 
	    	  /*========== Universal Search list of Vendor ==========*/
	    	else if(searchType == 'Vendor'){
	    		if(checkSize > 0){
	    		result = result+'   <h4>Vendors</h4>';
	    		for (var i = 0; i < checkSize; i++) {
		             var report= json[i];
		             var pattern = new RegExp(/["'"]/g);
			    	 var orgName = (report.orgName).replace(pattern, "\\'");
		             var name_location = "";
		                result = result+'  <div class="srchresult clearfix">';
		                
		                result = result+' <figure class="image_parent">';
				    	result = result+' <i class="fa fa-suitcase fa-2x"></i>';
				    	result = result+' </figure>';
			    		
				    	result = result+'      <div class="srchresult-content col-xs-12 col-md-12">';
				    	if(report.orgName !=null && report.orgName != '' && report.orgName !="undefined"){
				    		result = result+'<p><label><strong><a href="#" onclick="vendorSearchSideBar('+report.responderLocationId+','+report.orgRelationId+',\''+orgName+'\')">'+report.orgName+'</a></strong></label></p>';	
				    	//	name_location = name_location + report.orgName+",";
				    	}
				    	
				    	if(report.city !=null && report.city != '' && report.city !="undefined"){
	   			    		result = result+'          <p><label>'+report.city+' , '+report.state+' , '+report.country+'</label>';
	   			    	}else if(report.state !=null && report.state != '' && report.state !="undefined"){
	   			    		result = result+'          <p><label>'+report.state+' , '+report.country+'</label>';
	   			    	}else if(report.country !=null && report.country != '' && report.country !="undefined"){
	   			    		result = result+'          <p><label>'+report.country+'</label>';
	   			    	}
				    	
				    	result = result+'          </p>';
				    	if(report.createdBy !=null && report.createdBy != '' && report.createdBy !="undefined"){
				    		result = result+'          <p><label>'+report.createdBy+'</label></p>';
				    	}
				    	result = result+'      </div>';
				    	result = result+'  </div>';
				    	 if(checkSize>=5 && i==4 ){
					    	    result = result+'  <div class="clearfix">';
						    	result = result+'      <a href="#" title="View More" class="pull-right btn btn-sm btn-default" onclick="loadVendor();">View More</a>';
						    	result = result+'  </div>';
				    		 }
	    		}
	    		
	    		}else{
	    			result = result+'<div class="noRecords"><img src="/resources/images/norecords.jpg" alt=""/></div>';
	    		}
	    	}
	    	 /*========== Universal Search list of Vendor End ==========*/
	    	 
	    	 /*========== Universal Search list of Projects ==========*/ 
	    	else if(searchType =='Projects'){
	    		if(checkSize > 0){
	    		result = result+'   <h4>Projects</h4>';
		    	 for (var i = 0; i < checkSize; i++) {
		             var report= json[i];
		             var manager = "";
		             var date = "";
		          // split the date format
		             	var projectStartdate = report.startDate;
		             	var projectEnddate = report.endDate;
		             	if(projectStartdate != null && projectStartdate !="" && projectStartdate !='undefined'){
		             	var array = new Array();
		             	array = projectStartdate.split('-');
		             	var newDate = (array[2] + "-" + array[1] + "-" + array[0]);
		             	}
		             	if(projectEnddate != null && projectEnddate !="" && projectEnddate !='undefined'){

		             	var array2 = new Array();
		             	array2 = projectEnddate.split('-');
		             	var newEndDate = (array2[2] + "-" + array2[1] + "-" + array2[0]);
		             	}
				    	result = result+'  <div class="srchresult clearfix">';
				    	
				    	result = result+' <figure class="image_parent">';
				    	result = result+' <i class="fa fa-bar-chart fa-2x"></i>';
				    	result = result+' </figure>';
				    	
				    	result = result+'      <div class="srchresult-content col-xs-12 col-md-12">';
				    	if(report.projectName !=null && report.projectName != '' && report.projectName !="undefined" && report.invoiceFrequency !=null && report.invoiceFrequency !='' && report.invoiceFrequency !='undefined'){
				    		result = result+'<p><label><strong><a href="#" onclick="projectSearchSideBar('+report.id+',\''+report.projectName+'\',\''+report.startDate+'\',\''+report.endDate+'\',\''+report.projectStatus+'\',\''+report.projectUUID+'\');">'+report.projectName+'</a></strong></label>, <label>'+report.invoiceFrequency+'</label>';
				    	}else{
				    		result = result+'<p><label><strong><a href="#" onclick="projectSearchSideBar('+report.id+',\''+report.projectName+'\',\''+report.startDate+'\',\''+report.endDate+'\',\''+report.projectStatus+'\',\''+report.projectUUID+'\');">'+report.projectName+'</a></strong></label>';
				    	}
				    	result = result+'</p>';
				    	if(report.clientName !=null && report.clientName != '' && report.clientName !="undefined"){
				    		result = result+'<p><label>'+report.clientName+'</label></p>';
				    	}
				    	if(report.level1ManagerName !=null && report.level1ManagerName != '' && report.level1ManagerName !="undefined"){
				    		manager = manager +report.level1ManagerName;
				    	}if(report.level1ManagerName !=null && report.level1ManagerName != '' && report.level1ManagerName !="undefined" && report.level2ManagerName !=null && report.level2ManagerName !='' && report.level2ManagerName !='undefined'){
				    		manager = manager+",";
				    	}if(report.level2ManagerName !=null && report.level2ManagerName !='' && report.level2ManagerName !='undefined'){
				    		manager = manager + report.level2ManagerName;
				    	}
				    	result = result+'<p><label>'+manager+'</label></p>';
				    	if(newDate !=null && newDate != '' && newDate !="undefined" && newEndDate !=null && newEndDate !='' && newEndDate !='undefined'){
  				    		result = result+'<p><label>'+newDate+'</label>To <label>'+newEndDate+'</label>';
  				    	}else{
  				    		result = result+'<p><label>'+newDate+'</label>';
  				    	}
				    	result = result+'<span href="#" class="reqstatus-btn">'+report.projectStatus+'</span>';
				    	result = result+'</div>';
				    	result = result+'</div>';
				    	if(checkSize>=5 && i==4 ){
					    	result = result+'<div class="clearfix">';
					    	result = result+'<a href="#" title="View More" class="pull-right btn btn-sm btn-default" onclick="loadProjects();">View More</a>';
					    	result = result+'</div>';
		    	 		}
		    	        }
		    	 		
	    		}else{
	    			result = result+'<div class="noRecords"><img src="/resources/images/norecords.jpg" alt=""/></div>';
	    		}
	    	}
	    	/*========== Universal Search list of Projects End ==========*/ 
	    	
	    	/*========== Universal Search list of Users ==========*/ 
	    	else if(searchType =='Users'){
	    		if(checkSize > 0){
	    		result = result+'   <h4>Associates</h4>';
		    	 for (var i = 0; i < checkSize; i++) {
		             var report= json[i];
				    	result = result+'  <div class="srchresult clearfix">';
				    	
				    	result = result+' <figure class="image_parent">';
				    	result = result+' <i class="fa fa-user-o fa-2x"></i>';
				    	result = result+' </figure>';
				    	
				    	
				    	result = result+'      <div class="srchresult-content col-xs-12 col-md-12">';
				    	if(report.firstName !=null && report.firstName != '' && report.firstName !="undefined" && report.lastName !=null && report.lastName !='' && report.lastName !='undefined'){
				    		result = result+'<p><label><strong><a href="#" onClick="userSummaryRedirect(\''+report.userCode+'\');">'+report.firstName+' '+report.lastName+'</a></strong></label>';
				    	}else{
				    		result = result+'<p><label><strong><a href="#" onClick="userSummaryRedirect(\''+report.userCode+'\');">'+report.firstName+'</a></strong></label>';
				    	}
				    	result = result+'</p>'; 
				    	if(report.email !=null && report.email != '' && report.email !="undefined" && report.mobilePhone !=null && report.mobilePhone != '' && report.mobilePhone !="undefined"){
				    		result = result+'<p><label>'+report.email+'</label>,<label>'+report.mobilePhone+'<lable></p>';
				    	}else{
				    		result = result+'<p><label>'+report.email+'</label></p>';
				    	}
				    	if(report.userType !=null && report.userType != '' && report.userType !='undefined'){
				    		result = result+'<p><label>'+report.userType+'</label></p>';
				    	}
				    	result = result+'<span href="#" class="reqstatus-btn">'+report.userStatus+'</span>';
				    	result = result+'</div>';
				    	result = result+'</div>';
				    	if(i==4&& checkSize>=5){
					    	 result = result+'  <div class="clearfix">';
			                 result = result+'      <a href="#" title="View More" class="pull-right btn btn-sm btn-default" onclick="loadUsers();">View More</a>';
			                 result = result+'  </div>';
			    		 }
		    	        }
		    		 
	    		}else{
	    			result = result+'<div class="noRecords"><img src="/resources/images/norecords.jpg" alt=""/></div>';
	    		}
	    	}
	    	
	    	/*========== Universal Search list of Users End==========*/ 
	    	 result = result+'</div>';
	    	 $("#searchList").empty();
	    	 $("#candidateList").empty();
	    	 $("#searchList").append(result);
	    	
	    }
   }); 
}
</script>


<script type="text/javascript">
function getSearchResultsForAll(searchType,searchValue){
	$("#loading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	
	  $.ajax({
	   	type: "GET",
	    url: sourceleadUrl+"/universalSearchForAll?"+tokenParamName+"="+tokenValue+"&searchType="+searchType+"&searchValue="+searchValue,	    	   
	    success: function(data) {
	    	var result='';
	    	var count=0;
	    	var json = JSON.parse(data);
	    	result = result+'<div class="search-result-widget clearfix">';
	    	if(json.length>0){
	    		
	    		
	    	/* All,Users,Requirement,Candidate,Clients,Vendors,Projects*/
			    	for (var i = 0; i < json.length; i++) {
			             var opt= json[i];
			             var listSize;
			             if(opt.length>5)
			            	 listSize = 5;
			             else
			            	 listSize = opt.length;
				             for (var j = 0; j < listSize; j++) {
				            	 var report=opt[j];
				            	 var location ="";
				            	 var exp="";
				            	 count++;
				            	 /*========== Universal Search list of Requirement ==========*/ 
				            	 if(report.searchType=="Requirement"){	 
						            	 if((report.searchType=="Requirement") && j == 0){
						            		 result = result+'  <div class="clearfix">';	 
						            	   		result = result+'   <h4>Jobs</h4>';
						            	   	 result = result+' </div>';
						            	 }
						            	 result = result+'  <div class="srchresult clearfix">';
						            	 
						            	 
							                result = result+' <figure class="image_parent">';
									    	result = result+' <i class="fa fa-briefcase fa-2x"></i>';
									    	result = result+' </figure>';
						            	 
									    	result = result+'      <div class="srchresult-content col-xs-12 col-md-12">';
									    	if(report.jobTitle !=null && report.jobTitle != '' && report.jobTitle !="undefined" && report.jobType !=null && report.jobType != '' && report.jobType !="undefined"){
									    		result = result+'<p><label><strong><a href="/app/requirementSummary?requirementUUID='+report.requirementUUID+'">'+report.jobTitle+'</a></strong></label>, <label>'+report.jobType+'</label>';	
									    	}else{
									    		result = result+'<p><label><strong><a href="/app/requirementSummary?requirementUUID='+report.requirementUUID+'">'+report.jobTitle+'</a></strong></label>';	
									    	}
									    	
									    	if(report.city !=null && report.city != '' && report.city !="undefined"){
						   			    		result = result+'          <p><label>'+report.city+' , '+report.state+' , '+report.country+'</label>';
						   			    	}else if(report.state !=null && report.state != '' && report.state !="undefined"){
						   			    		result = result+'          <p><label>'+report.state+' , '+report.country+'</label>';
						   			    	}else if(report.country !=null && report.country != '' && report.country !="undefined"){
						   			    		result = result+'          <p><label>'+report.country+'</label>';
						   			    	}
									    	
									    	result = result+'          </p>';
									    	result = result+'          </p>';
									    	if(report.clientName !=null && report.clientName !='' && report.clientName !='undefined'){
									    		result = result+'          <p><label><strong>'+report.clientName+'</strong></label></p>';	
									    	}
									    	
									    	if(report.minExp !=null && report.minExp != '' && report.minExp !="undefined" && report.maxExp !=null && report.maxExp != '' && report.maxExp !="undefined"){
						   			    		result = result+'          <p><label class="fa fa-black-tie" title="Experience"> '+report.minExp+' - '+report.maxExp+' years</label></p>';
						   			    	}else if(report.minExp !=null && report.minExp != '' && report.minExp !="undefined"){
						   			    		result = result+'          <p><label class="fa fa-black-tie" title="Experience"> '+report.minExp+' years</label></p>';
						   			    	}else if(report.maxExp !=null && report.maxExp != '' && report.maxExp !="undefined"){
						   			    		result = result+'          <p><label class="fa fa-black-tie" title="Experience"> '+report.maxExp+' years</label></p>';
						   			    	}
									    	
									    	if(report.skills !=null && report.skills != '' && report.skills != 'undefined'){
									    		result = result+'          <p><label>'+report.skills+'</label></p>';
									    	}
									    	result = result+'<span href="#" class="reqstatus-btn">'+report.requirementSubmissionStatus+'</span>';
									    	result = result+'      </div>';
									    	result = result+'  </div>';
									    	
									    	if(opt.length>5 && j==4){
								                result = result+'  <div class="clearfix">';
										    	result = result+'      <a href="/app/jobs" title="View More" class="pull-right btn btn-sm btn-default">View More</a>';
										    	result = result+'  </div>';
								             }
									    	
									    	
						             } 
				            	 /*========== Universal Search list of Requirement End ==========*/ 
				            	 
				            	/*========== Universal Search list of User ==========*/ 
				            	 if(report.searchType=="User"){
				            		 	if((report.searchType=="User") && j == 0){
				            		 		result = result+'  <div class="clearfix">';
						            		 result = result+'   <h4>Associates</h4>';
						            		result = result+'  </div>'; 
						            	 }
				            			result = result+'  <div class="srchresult clearfix">';
				            			
				            			
				            			
						                result = result+' <figure class="image_parent">';
								    	result = result+' <i class="fa fa-users fa-2x"></i>';
								    	result = result+' </figure>';
				            			
								    	result = result+'      <div class="srchresult-content col-xs-12 col-md-12">';
								    	if(report.firstName !=null && report.firstName != '' && report.firstName !="undefined" && report.lastName !=null && report.lastName !='' && report.lastName !='undefined'){
								    		result = result+'<p><label><strong><a href="#" onClick="userSummaryRedirect(\''+report.userCode+'\');">'+report.firstName+' '+report.lastName+'</a></strong></label>';
								    	}else{
								    		result = result+'<p><label><strong><a href="#" onClick="userSummaryRedirect(\''+report.userCode+'\');">'+report.firstName+'</a></strong></label>';
								    	}
								    	result = result+'</p>'; 
								    	if(report.email !=null && report.email != '' && report.email !="undefined" && report.mobilePhone !=null && report.mobilePhone != '' && report.mobilePhone !="undefined"){
								    		result = result+'<p><label>'+report.email+'</label>,<label>'+report.mobilePhone+'<lable></p>';
								    	}else{
								    		result = result+'<p><label>'+report.email+'</label></p>';
								    	}
								    	if(report.userType !=null && report.userType != '' && report.userType !='undefined'){
								    		result = result+'<p><label>'+report.userType+'</label></p>';
								    	}
								    	result = result+'<span href="#" class="reqstatus-btn">'+report.userStatus+'</span>';
								    	result = result+'</div>';
								    	result = result+'</div>';
								    	if(opt.length>5 && j==4){
							                result = result+'  <div class="clearfix">';
									    	result = result+'      <a href="#" title="View More" class="pull-right btn btn-sm btn-default" onclick="loadUsers();">View More</a>';
									    	result = result+'  </div>';
							             }
				            	 }
				            	 /*========== Universal Search list of User End ==========*/ 
				            	 
				            	 
				            	 //dont remove the code because projects are changing 
				            	 /*========== Universal Search list of Projects ==========*/ 
				            	/*  if(report.searchType=="Projects"){
				            		 if((report.searchType=="Projects") && j == 0){
				            			 result = result+'  <div class="clearfix">'; 
					            		 result = result+'   <h4>Projects</h4>';
					            		 result = result+'  </div>';
					            	 }
				            		   var manager = "";
				  		             	var date = "";
				  		           // split the date format
				  		             	var projectStartdate = report.startDate;
				  		             	var projectEnddate = report.endDate;
				  		             	if(projectStartdate != null && projectStartdate !="" && projectStartdate !='undefined'){
				  		             	var array = new Array();
				  		             	array = projectStartdate.split('-');
				  		             	var newDate = (array[2] + "-" + array[1] + "-" + array[0]);
				  		             	}
				  		             	if(projectEnddate != null && projectEnddate !="" && projectEnddate !='undefined'){

				  		             	var array2 = new Array();
				  		             	array2 = projectEnddate.split('-');
				  		             	var newEndDate = (array2[2] + "-" + array2[1] + "-" + array2[0]);
				  		             	}

				  				    	result = result+'  <div class="srchresult clearfix">';
				  				    	
						                result = result+' <figure class="image_parent">';
								    	result = result+' <i class="fa fa-bar-chart fa-2x"></i>';
								    	result = result+' </figure>';
				  				    	
				  				    	
				  				    	result = result+'      <div class="srchresult-content col-xs-12 col-md-12">';
				  				    	if(report.projectName !=null && report.projectName != '' && report.projectName !="undefined" && report.invoiceFrequency !=null && report.invoiceFrequency !='' && report.invoiceFrequency !='undefined'){
								    		result = result+'<p><label><strong><a href="#" onclick="projectSearchSideBar('+report.id+',\''+report.projectName+'\',\''+report.startDate+'\',\''+report.endDate+'\',\''+report.projectStatus+'\',\''+report.projectUUID+'\');">'+report.projectName+'</a></strong></label>';
								    		result = result+'<label>'+report.invoiceFrequency+'</label>';
				  				    	}else{
				  				    		result = result+'<p><label><strong><a href="#" onclick="projectSearchSideBar('+report.id+',\''+report.projectName+'\',\''+report.startDate+'\',\''+report.endDate+'\',\''+report.projectStatus+'\',\''+report.projectUUID+'\');">'+report.projectName+'</a></strong></label>';
				  				    	}
				  				    	result = result+'</p>';
				  				    	if(report.clientName !=null && report.clientName != '' && report.clientName !="undefined"){
				  				    		result = result+'<p><label>'+report.clientName+'</label></p>';
				  				    	}
				  				    	if(report.level1ManagerName !=null && report.level1ManagerName != '' && report.level1ManagerName !="undefined"){
				  				    		manager = manager +report.level1ManagerName;
				  				    	}if(report.level1ManagerName !=null && report.level1ManagerName != '' && report.level1ManagerName !="undefined" && report.level2ManagerName !=null && report.level2ManagerName !='' && report.level2ManagerName !='undefined'){
				  				    		manager = manager+",";
				  				    	}if(report.level2ManagerName !=null && report.level2ManagerName !='' && report.level2ManagerName !='undefined'){
				  				    		manager = manager + report.level2ManagerName;
				  				    	}
				  				    	result = result+'<p><label>'+manager+'</label></p>';
				  				    	if(newDate !=null && newDate != '' && newDate !="undefined" && newEndDate !=null && newEndDate !='' && newEndDate !='undefined'){
				  				    		result = result+'<p><label>'+newDate+'</label>To <label>'+newEndDate+'</label>';
				  				    	}else{
				  				    		result = result+'<p><label>'+newDate+'</label>';
				  				    	}
				  				    	result = result+'<span href="#" class="reqstatus-btn">'+report.projectStatus+'</span>';
				  				    	result = result+'</div>';
				  				    	result = result+'</div>';
				  				    	
				  				    	if(opt.length>5 && j==4){
							                result = result+'  <div class="ccountlearfix">';
							                result = result+'<a href="#" title="View More" class="pull-right btn btn-sm btn-default" onclick="loadProjects();">View More</a>';
									    	result = result+'  </div>';
							             }
				            	 } */
				            	 /*========== Universal Search list of Projects End ==========*/ 
				            	 
				            	 /*========== Universal Search list of Candidate ==========*/ 
				            	 if(report.searchType=="Candidate"){
				            		 var location = "";
						             var experience = "";
						             if((report.searchType=="Candidate") && j == 0){
						            	 result = result+'  <div class="clearfix">';
						            		 result = result+'   <h4>Candidates</h4>';
						            	 result = result+'  </div>';
						            	 }
								    	result = result+'  <div class="srchresult clearfix">';
								    	
						                result = result+' <figure class="image_parent">';
// 								    	result = result+' <i class="fa fa-user-circle fa-2x"></i>';
 										result = result+ '<img width="55" class="img-circle"  onerror="predefineProfileImage(this);" src="<c:url value="${sourceleadUrl}/displayCandidateImage/'+report.candidateId+'"/>">';
								    	result = result+' </figure>';
								    	
								    	
								    	
								    	result = result+'      <div class="srchresult-content col-xs-12 col-md-12">';
								    	
								    	if(report.designationType != null && report.designationType != '' && report.designationType !='undefined'){
								    		designationType=','+report.designationType;
								    	}else{
								    		designationType='';
								    	}
								    	
								    	if(report.experienceYears !=null && report.experienceYears !='' && report.experienceYears !='undefined' && report.experienceMonths !=null && report.experienceMonths !='' && report.experienceMonths !='undefined'){
			 				    		result = result+'<p><label><strong><a href="#" onclick="candidateSearchSideBar('+report.candidateId+')">'+report.candidateName+'</a></strong></label>, <label>'+report.designationType+'</label>, <label>'+report.experienceYears+' Years - '+report.experienceMonths+' Months</label>';
							    		 }else if(report.experienceYears !=null && report.experienceYears !='' && report.experienceYears !='undefined'){
							    		result = result+'<p><label><strong><a href="#" onclick="candidateSearchSideBar('+report.candidateId+')">'+report.candidateName+'</a></strong></label>, <label>'+report.designationType+'</label>, <label>'+report.experienceYears+' Years</label>';
							    		 }else if(report.experienceMonths !=null && report.experienceMonths !='' && report.experienceMonths !='undefined'){
							    		result = result+'<p><label><strong><a href="#" onclick="candidateSearchSideBar('+report.candidateId+')">'+report.candidateName+'</a></strong></label>, <label>'+report.designationType+'</label>, <label>'+report.experienceMonths+' Months</label>';
							    		}else{													
							    			result = result+'<p><label><strong><a href="#" onclick="candidateSearchSideBar('+report.candidateId+')">'+report.candidateName+'</a></strong></label>, <label>'+report.designationType+'</label>';
							    		}
								    	
								    	if(report.mobilePhone !=null && report.mobilePhone !='' && report.mobilePhone !='undefined'){
								    		if(report.city !=null && report.city != '' && report.city !="undefined"){
									    		result = result+'<p><label><strong>'+report.email+'</strong></label>, <label>'+report.mobilePhone+'</label>, <label>'+report.city+' , '+report.state+' , '+report.country+'</label>';
									    	}else if(report.state !=null && report.state != '' && report.state !="undefined"){
									    		result = result+'<p><label><strong>'+report.email+'</strong></label>, <label>'+report.mobilePhone+'</label>, <label>'+report.state+' , '+report.country+'</label>';
									    	}else if(report.country !=null && report.country != '' && report.country !="undefined"){
									    		result = result+'<p><label><strong>'+report.email+'</strong></label>, <label>'+report.mobilePhone+'</label>, <label>'+report.country+'</label>';
									    	}else{
									    		result = result+'<p><label><strong>'+report.email+'</strong></label>, <label>'+report.mobilePhone+'</label>';	
									    	}
								    	}else{
								    		if(report.city !=null && report.city != '' && report.city !="undefined"){
										    		result = result+'<p><label><strong>'+report.email+'</strong></label>,<label>'+report.city+' , '+report.state+' , '+report.country+'</label>';
										    	}else if(report.state !=null && report.state != '' && report.state !="undefined"){
										    		result = result+'<p><label><strong>'+report.email+'</strong></label>, <label>'+report.state+' , '+report.country+'</label>';
										    	}else if(report.country !=null && report.country != '' && report.country !="undefined"){
										    		result = result+'<p><label><strong>'+report.email+'</strong></label>, <label>'+report.country+'</label>';
										    	}else{
										    		result = result+'<p><label><strong>'+report.email+'</strong></label>';	
										    	}
								    	}
								    	
								    	result = result+'          </p>';
								    	if(report.skillSet !=null && report.skillSet != '' && report.skillSet !="undefined"){
								    		result = result+'          <p><label>'+report.skillSet+'</label></p>';
								    	}
								    	result = result+'      </div>';
								    	result = result+'  </div>';
								    	
								    	if(opt.length>5 && j==4){
							                result = result+'  <div class="clearfix">';
									    	result = result+'      <a href="#" title="View More" class="pull-right btn btn-sm btn-default" onclick="loadCandidate();">View More</a>';
									    	result = result+'  </div>';
							             }
					            	}
				            	 /*========== Universal Search list of Candidate End ==========*/ 
				            	 
				            	  /*========== Universal Search list of Client ==========*/ 
								 if(report.searchType=="Client"){
									 if((report.searchType=="Client") && j == 0){
										 result = result+'  <div class="clearfix">';
										 result = result+'   <h4>Clients</h4>';
										 result = result+'  </div>';
						            	 }
									 var pattern = new RegExp(/["'"]/g);
							    	 var orgName = (report.orgName).replace(pattern, "\\'");
									 var name_location = "";
								    	result = result+'  <div class="srchresult clearfix">';
								    	
						                result = result+' <figure class="image_parent">';
								    	/* result = result+' <i class="fa fa-user-plus fa-2x"></i>'; */
								    	result = result+ '<img width="55" class="img-circle"  onerror="predefineProfileImage(this);" src="<c:url value="${sourceleadUrl}/displayClientImage/'+report.orgRelationId+'"/>">';
								    	result = result+' </figure>';
								    	
								    	
								    	result = result+'      <div class="srchresult-content col-xs-12 col-md-12">';
								    	if(report.orgName !=null && report.orgName != '' && report.orgName !="undefined"){
								    		result = result+'<p><label><strong><a href="#" onclick="clientSearchSideBar('+report.responderLocationId+','+report.orgRelationId+',\''+orgName+'\')">'+report.orgName+'</a></strong></label></p>';
								    		//name_location = name_location + report.orgName+",";
								    	}
								    	
								    	if(report.city !=null && report.city != '' && report.city !="undefined"){
					   			    		result = result+'          <p><label>'+report.city+' , '+report.state+' , '+report.country+'</label>';
					   			    	}else if(report.state !=null && report.state != '' && report.state !="undefined"){
					   			    		result = result+'          <p><label>'+report.state+' , '+report.country+'</label>';
					   			    	}else if(report.country !=null && report.country != '' && report.country !="undefined"){
					   			    		result = result+'          <p><label>'+report.country+'</label>';
					   			    	}
								    	
								    	result = result+'          </p>';
								    	if(report.createdBy !=null && report.createdBy != '' && report.createdBy !="undefined"){
								    		result = result+'          <p><label>'+report.createdBy+'</label></p>';
								    	}
								    	result = result+'      </div>';
								    	result = result+'  </div>';
								    	
								    	if(opt.length>5 && j==4){
							                result = result+'  <div class="clearfix">';
										result = result+' <a href="#" onclick="loadClient();" title="View More" class="pull-right btn btn-sm btn-default">View More</a>';
									    	result = result+'  </div>';
							             }
				            	 }
								 /*========== Universal Search list of Client End  ==========*/ 
								 
								  /*========== Universal Search list of Vendor ==========*/ 
								 if(report.searchType=="Vendor"){
									 if((report.searchType=="Vendor") && j == 0){
										 result = result+'  <div class="clearfix">';
										 result = result+'   <h4>Vendors</h4>';
										 result = result+'  </div>';
						            	 }
									 var pattern = new RegExp(/["'"]/g);
							    	 var orgName = (report.orgName).replace(pattern, "\\'");
									 var name_location = "";
								    	result = result+'  <div class="srchresult clearfix">';
								    	
								    	
						                result = result+' <figure class="image_parent">';
								    	result = result+' <i class="fa fa-suitcase fa-2x"></i>';
								    	result = result+' </figure>';
								    	
								    	result = result+'      <div class="srchresult-content col-xs-12 col-md-12">';
								    	if(report.orgName !=null && report.orgName != '' && report.orgName !="undefined"){
								    		result = result+'<p><label><strong><a href="#" onclick="vendorSearchSideBar('+report.responderLocationId+','+report.orgRelationId+',\''+orgName+'\')">'+report.orgName+'</a></strong></label></p>';
								    		//name_location = name_location + report.orgName+",";
								    	}
								    	
								    	if(report.city !=null && report.city != '' && report.city !="undefined"){
					   			    		result = result+'          <p><label>'+report.city+' , '+report.state+' , '+report.country+'</label>';
					   			    	}else if(report.state !=null && report.state != '' && report.state !="undefined"){
					   			    		result = result+'          <p><label>'+report.state+' , '+report.country+'</label>';
					   			    	}else if(report.country !=null && report.country != '' && report.country !="undefined"){
					   			    		result = result+'          <p><label>'+report.country+'</label>';
					   			    	}
								    	
								    	result = result+'          </p>';
								    	if(report.createdBy !=null && report.createdBy != '' && report.createdBy !="undefined"){
								    		result = result+'          <p><label>'+report.createdBy+'</label></p>';
								    	}
								    	result = result+'      </div>';
								    	result = result+'  </div>';
								    	
								    	if(opt.length>5 && j==4){
							                result = result+'  <div class="clearfix">';
									    	result = result+'      <a href="#" onClick="loadVendor();" title="View More" class="pull-right btn btn-sm btn-default">View More</a>';
									    	result = result+'  </div>';
							             }
				            	 }
								 /*========== Universal Search list of Vendor End ==========*/ 
								    
				    	 }   
			    	 }
	    	}
	    	// to add no more records image if no records found in all universal search 
	    	if(count==0){
	    		result = result+'<div class="noRecords"><img src="/resources/images/norecords.jpg" alt=""/></div>';	
	    	}
	    	 result = result+'</div>';
	    	 $("#searchList").empty();
	    	 $("#searchList").append(result);
	    	 $("#loading").removeAttr("style");
	    	
	    }
   }); 
}
</script>


<!-- /candidateList -->
<script type="text/javascript">
function loadCandidate(){
	
	var searchResult = "universalCanddateList";
	var searchValue = "${searchValue}";
	window.location.href = sourceleadUrl + "/candidatesList?&searchResult="+searchResult+"&searchValue="+searchValue;
}
</script>

<!-- /requirementList -->
<script type="text/javascript">
function loadRequirement(){
	var searchResult = "universalRequirementList";
	var serachValue = "${searchValue}";
	window.location.href = sourceleadUrl + "/requirementList?&searchResult="+searchResult+"&serachValue="+serachValue;
}
</script>

<!-- /usersList -->
<script>
function loadUsers(){
	var searchResult = "searchresult";
	var searchValue = "${searchValue}";
	var searchType = "Associates";
	window.location.href = sourceleadUrl + "/usersList?&searchResult="+searchResult+"&searchValue="+searchValue+"&searchType="+searchType;

	//usersList
}
</script>

<script>
function loadVendor(){
	var searchResult = "universalVendorList";
	var searchValue = "${searchValue}";
	var searchType = "${searchType}";
	window.location.href = sourceleadUrl + "/vendorsList?&searchResult="+searchResult+"&searchValue="+searchValue+"&searchType="+searchType;

}

</script>

<script>
function loadClient(){
	var searchResult = "clientSearchResult";
	var searchValue = "${searchValue}";
	var searchType = "${searchType}";
	window.location.href = sourceleadUrl + "/clientDashboard?&searchResult="+searchResult+"&searchValue="+searchValue+"&searchType="+searchType;
}
</script>

<script>
function loadProjects(){
	var searchResult = "universalProjectsList";
	var searchValue = "${searchValue}";
	window.location.href = sourceleadUrl + "/projectDashboard?&searchResult="+searchResult+"&searchValue="+searchValue;
}
</script>

<script>
function vendorSearchSideBar(responderLocationId,orgRelationId,orgName){
	var vendorKeyword = "vendorSearch";
	var org = encodeURIComponent(orgName);
	window.location.href = sourceleadUrl + "/vendorsList?&responderLocationId="+responderLocationId+"&orgRelationId="+orgRelationId+"&orgName="+org+"&vendorKeyword="+vendorKeyword;

}
</script>


<script>
function clientSearchSideBar(responderLocationId,orgRelationId,orgName){
	var clientKeyword = "clientSearch";
	var org = encodeURIComponent(orgName);
	window.location.href = sourceleadUrl + "/clientDashboard?&responderLocationId="+responderLocationId+"&orgRelationId="+orgRelationId+"&orgName="+org+"&clientKeyword="+clientKeyword;
}
</script>

<script>
function candidateSearchSideBar(candidateIdSearchId,isAssociate){
	var candidateKeyword = "candidateSearch";
	if(isAssociate == "Y"){
		//Highlighting associate tab when search for associates through universal search
		window.location.href = sourceleadUrl + "/associatesList?&candidateIdSearchId="+candidateIdSearchId+"&candidateKeyword="+candidateKeyword;
	}else{
		window.location.href = sourceleadUrl + "/candidatesList?&candidateIdSearchId="+candidateIdSearchId+"&candidateKeyword="+candidateKeyword;
	}
}
</script>

<script>
function userSummaryRedirect(userCode){
	window.location.href = sourceleadUrl +"/userSummary/"+userCode;
}
</script>

<script>
function projectSearchSideBar(projectId,projectName,startDate,endDate, status,projectUUID){
	var projectKeyword = "projectSearch";
	window.location.href = sourceleadUrl + "/projectSummary?projectUUID="+projectUUID;
// 	window.location.href = sourceleadUrl + "/projectDashboard?&projectId="+projectId+"&projectName="+projectName+"&startDate="+startDate+"&endDate="+endDate+"&status="+status+"&projectKeyword="+projectKeyword;
}
</script>

<!-- <script>
//$(".uasrch-selectpicker .dropdown-menu ul li").css('backgroundColor','red');
$(".uasrch-selectpicker .dropdown-menu ul li").attr('id','srinu');
</script> -->

<jsp:include page="../masterFooter.jsp" />
