<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<script>
 window.onload = function() { 
	 viewRequirementTemplateDetails();
	
};
</script>

      	  <div class="content-wraper">
<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Job Template Details</h2> 
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>»<a href="<c:url value='${appPath}/template'/>">Job Template List </a>»
			View Job Template
		</div>
	</section>
		      <div class="content-area">
		      				<div id="viewReqTemplateDiv" class="mt clearfix">
						     </div>
						    <div class="modal-body pb">  
						    	<div id="formatedTextone" class="align-paragraph"></div>
						    </div>
		      </div>
          </div>
<script>
 function viewRequirementTemplateDetails(){  
	 var sourceleadUrl ='${sourceleadUrl}';
	 var requirementId= '${requirementId}';
	 $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getRequirementTemplateByRequirementId?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId, 
	        success: function(data) {
	        	 
			    var json = JSON.parse(data);
			    var result = "";
			    
			    var remove = "remove";
			    var reActive = "reActive";
			    
			    if(json.backgroundCheck != null && json.backgroundCheck != '' && json.backgroundCheck != 'undefined'){
					 if(json.backgroundCheck == "Y"){
						 backgroundCheck = "Yes" 
					 }else{
						 backgroundCheck = "No" 
					 }
				 }else{
					 backgroundCheck = "--";
				 }
				 
				 if(json.overTime != null && json.overTime != '' && json.overTime != 'undefined') {
					 if(json.overTime == "Y"){
						 overTime = "Yes";
					 }else{
						 overTime = "No"
					 }
				}else{
					overTime = "--";
				}
				 
				 if(json.medicalClearence != null && json.medicalClearence != '' && json.medicalClearence != 'undefined') {
					 if(json.medicalClearence == "Y"){
						 medicalClearence = "Yes";
					 }else{
						 medicalClearence = "No";
					 }
				 }else{
					 medicalClearence = "--";
				 }
				 
				 if(json.securityClearance != null && json.securityClearance != '' && json.securityClearance != 'undefined') {
					 if(json.securityClearance == "Y"){
						 securityClearance = "Yes";
					 }else{
						 securityClearance = "No";
					 }
				}else{
						securityClearance = "--";
				}

			    if(json.status != null && json.status != "" && json.status != '0'){ 
			    
			    result = result + ' <div class="useredit">';
			    result = result + ' <div class="pull-right mt">';
			    '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_REQUIREMENT_TEMPLATE\')">';
			        result = result + ' <a id="edit-user" href="<c:url value="/app/addTemplate?requirementId='+requirementId+'"/>"><i class="glyphicon glyphicon-pencil"></i>Edit</a>';
			    '</sec:authorize>';
			   /*   '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_DELETE_REQUIREMENT_TEMPLATE\')">';    
			        result = result + ' <a id="edit-user" href="#" onClick="deleteRequirementTemplate(\''+requirementId+'\',\''+remove+'\');"><i class="glyphicon glyphicon-trash"></i>Remove</a>';
		        '</sec:authorize>'; */
		        result = result + ' <div id="remove_user_id" class="remove_user_pop" >';
				result = result +'<textarea placeholder="Comments here" id="reason" name="reason"/></textarea>';
				result = result + ' <div />';
				result = result + ' <b><input  value="Submit" type="button"/><input class="u_can_color" onclick="cancelUser();" value="Cancel" type="button"></b>';
	            result = result + ' </div>';
			    }/* else{
			    
			    result = result + ' <div class="useredit">';
			    result = result + ' <div class="pull-right mt">';
		        result = result + ' <a id="edit-user" href="#" onClick="deleteRequirementTemplate(\''+requirementId+'\',\''+reActive+'\');"><i class="glyphicon glyphicon-trash"></i>Re-Active</a>';
		        
	            result = result + ' </div>';
	            result = result + ' </div>';
			    
			    }  */
		        
		        result = result + ' </div>'; 
		        result = result + ' </div>';
		        result = result + ' <div class="clearfix"></div>';
			    
				 result = result + '<div class="user_details_cta templatesdetails mt">';
				 result = result + '<div class="clearfix">';
				 if(json.jobTitle != null && json.jobTitle != "" && json.jobTitle != 'undefined'){
					result = result + '<div class="project_details_cta project_details_view">';
					result = result + '<strong>Job Title :</strong><span class="fix">'+json.jobTitle+'</span><div class="clearfix"></div>';
					result = result + ' </div>';
				 }
				 
				    result = result + '<div class="project_details_cta project_details_view clearfix">';

					result = result + '	<strong>Employment Type:</strong>';
					 if(null != json.jobType && json.jobType !='' && json.jobType !='undefined'){
                    	 if(json.jobType=='Other'){
                    		 if(json.otherJobType != null && json.otherJobType != '' && json.otherJobType != 'undefined'){
                    			 result = result +'<span class="fix">'+json.otherJobType+'</span>';
                    		 }else{
                    			 result = result +'<span class="fix">'+json.jobType+'</span>';
                    		 }
                    	 }else{
                    		 result = result +'<span class="fix">'+json.jobType+'</span>'
                    	 }
                     }					 
					result = result + '</div>';
					
					if((null != json.projectDurationYears && json.projectDurationYears !='' && json.projectDurationYears !='undefined') || (null != json.projectDurationMonths && json.projectDurationMonths !='' && json.projectDurationMonths !='undefined')){
						result = result + '<div class="project_details_cta clearfix">';
						result = result + '	<strong>Project Duration :</strong>';
						result = result + '	<span class="fix">';
						if(json.projectDurationYears != null && json.projectDurationYears != "" && json.projectDurationYears != 'undefined'){
							result = result + json.projectDurationYears+' years ';
						 }
						if((null != json.projectDurationYears && json.projectDurationYears !='' && json.projectDurationYears !='undefined') && (null != json.projectDurationMonths && json.projectDurationMonths !='' && json.projectDurationMonths !='undefined')){
							result = result + ' and ';
						}
						if(json.projectDurationMonths != null && json.projectDurationMonths != "" && json.projectDurationMonths != 'undefined'){
							result = result + json.projectDurationMonths+' months';
						 }
						result = result + '	</span>';
						result = result + '</div>';
					}
					
				 

						result = result + '<div class="project_details_cta clearfix">';
						result = result + '	<strong>Min Experience :</strong>';
						if(json.minExp != null && json.minExp != "" && json.minExp != 'undefined'){
						result = result + '	<span class="fix">'+json.minExp+'</span>';
						 }
						result = result + '</div>';
					   
				 
				
						result = result + '<div class="project_details_cta clearfix">';
						result = result + '<strong>Max Experience :</strong>';
						if(json.maxExp != null && json.maxExp != "" && json.maxExp != 'undefined'){
						result = result + '<span class="fix">'+json.maxExp+'</span>';
						 }
						result = result + '</div>';
					

				 
				
						result = result + '<div class="project_details_cta project_details_view clearfix">';
						result = result + '	<strong>Skills :</strong>';
						 if(json.skills != null && json.skills != "" && json.skills != 'undefined'){
						result = result + '<span class="fix" title="'+json.skills+'">'+json.skills+'</span>';
						 }
						result = result + ' </div>';
					
				 
				 
						result = result + '<div class="project_details_cta project_details_view clearfix">';
						result = result + '<strong>Education :</strong>';
						if(json.education != null && json.education != "" && json.education != 'undefined'){
						result = result + '<span class="fix" title="'+json.education+'">'+json.education+'</span>';
						}
						result = result + '</div>';
				 		result = result + '<div class="project_details_cta project_details_view clearfix">';
						result = result + '<strong>Medical Clearence:</strong>';
						result = result + '<span class="fix" title="'+medicalClearence+'">'+medicalClearence+'</span>';
						result = result + '</div>';
						
						result = result + '<div class="project_details_cta project_details_view clearfix">';
						result = result + '<strong>Security Clearance:</strong>';
						result = result + '<span class="fix" title="'+securityClearance+'">'+securityClearance+'</span>';
						result = result + '</div>';
						
						
						result = result + '<div class="project_details_cta project_details_view clearfix">';
						result = result + '<strong>Over Time:</strong>';
						result = result + '<span class="fix" title="'+overTime+'">'+overTime+'</span>';
						result = result + '</div>';
					 
					 
						result = result + '<div class="project_details_cta project_details_view clearfix">';
						result = result + '<strong>Background Check:</strong>';
						result = result + '<span class="fix" title="'+backgroundCheck+'">'+backgroundCheck+'</span>';
						result = result + '</div>';
						
						if(json.gender != null && json.gender != "" && json.gender != 'undefined'){
						result = result + '<div class="project_details_cta project_details_view clearfix">';
						result = result + '<strong>Gender :</strong>';
						result = result + '<span class="fix" title="'+json.gender+'">'+json.gender+'</span>';
						result = result + '</div>';
						}else{
								result = result + '<div class="project_details_cta project_details_view clearfix">';
								result = result + '<strong>Gender :</strong>';
								result = result + '<span class="fix" title="">--</span>';
								result = result + '</div>';
						 }
						
						if(json.experienceLevel != null && json.experienceLevel != "" && json.experienceLevel != 'undefined'){
								result = result + '<div class="project_details_cta project_details_view clearfix">';
								result = result + '<strong>Experience Level :</strong>';
								result = result + '<span class="fix" title="'+json.experienceLevel.name+'">'+json.experienceLevel.name+'</span>';
								result = result + '</div>';
						}else{
								result = result + '<div class="project_details_cta project_details_view clearfix">';
								result = result + '<strong>Experience Level :</strong>';
								result = result + '<span class="fix" title="">--</span>';
								result = result + '</div>';
						}
						result = result + '<div class="clearfix">';
						if(json.description != null && json.description != "" && json.description != 'undefined'){
							result = result + '<div class="project_details_cta project_details_view clearfix">';
							result = result + '<strong>Description :</strong>';
							result = result + '</div>';
						}
				 
				result = result + '	</div>';
				result = result + '</div>';
				$("#viewReqTemplateDiv").empty();  
   	    	    $("#viewReqTemplateDiv").append(result);
				
   	    	    $('#formatedTextone').html(json.description); 
   	    	 getEditRequirementDetails(requirementId);
	      },
	});
 }
</script>

<!-- <script>
function deleteRequirementTemplate(requirementId, status){
	var sourceleadUrl ='${sourceleadUrl}';
	var message = confirm("You are choosing to delete the Requirement Template");
	if (message == true) {
		$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/terminateRequirementTemplate?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&status="+status, 	   
   		    success: function(data) {
   		    	if( data.indexOf('Sign In') >= 0){
		    		location.replace(sourceleadUrl);
		    	 }
        		 else if (data.indexOf('exception') >= 0) {
        			 location.replace(sourceleadUrl+"/errorPage");
        		 }
        		 else{
        			 location.replace(sourceleadUrl+"/template");
        			 
        		 }
   		    }
	     });
	}
}
</script> -->

<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {

			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};
	})(jQuery);
</script>
<jsp:include page="../masterFooter.jsp" />