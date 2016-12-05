<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>::Org Details</title>
<style type="text/css">
.comp-textarea textarea {
min-height: 90px;
} 
#loading {
z-index: 9999999999 !important;
}
</style>
</head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


 <link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="resources/js/formvalidator.js"></script>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>

<body>

<!-- Content Area -->
  <!-- Tab for Profile setting-area starts -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Org Details</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Account Settings</div>
    </section>
    <div class="content-area clearfix">
      <div class="clearfix form-area form-horizontal toggle-div remove-div mt">
      <div id="loading"></div> 
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		    <!-- Tab for Profile setting-area -->
		  <div class="accountsettings-tab org-settings-area">
		  <div class="tab-content panel-body">
			<div class="clearfix">
			  <form:form class="login-form clearfix"  id="update_module_form"  name="update_module_form" method="post" > 
			  
			  <div class="col-xs-12 col-sm-3 col-md-3">
				 <div class="well well-sm text-center">
				  <div class="profile-img-container" style="position:relative;">
				   <img id="orgImage" onerror="predefineOrgImage(this);" src="<c:url value='/displayOrgImage/${orgloc.org.orgCode}'/>" alt="Org Image" class="comp-image img-responsive">
				  </div>
				  </div>
			  </div>
				<div class="col-xs-12 col-sm-9 col-md-9">
					<div class="row">
						<div class="col-md-7">
								
								<div class="form-group">
								<label class="col-md-4">Company :</label>
								<p>${orgloc.org.orgName}</p>
								</div>
								
								<div class="form-group">
								<label class="col-md-4">Website :</label>
								<p>${orgloc.org.webSite}</p>
								</div>
								
								<div class="form-group">
								<label class="col-md-4">Location :</label>
								<p>${orgloc.country}</p>
								</div>
								
								<div class="form-group">
								<label class="col-md-4">Enrolled Date :</label>
								<p><fmt:formatDate type="date"  value="${orgloc.createdDate}" /></p></p>
								</div>
								<div class="form-group">
						            <label class="col-md-3">Modules :</label>
						            <div class="col-md-14 checkbox">
							         <c:forEach var="module" items="${registeredModulesList}">
										<ul class="list-unstyled pull-left">
				               				 <li>
										    <c:if test="${module.id == '1'}">
									         <label class="mr pull-left">
							           		     <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${module.moduleName}</p>
							           		  </label>
							           		   <ul class="list-unstyled pull-left tree-checkbox">
							                    	 <c:forEach var="submodules" items="${registeredSubModulesList}">
							                    	  <label>
							                    	    <c:if test="${submodules.isModuleConfigured  == 'Y'}">
							                    		  <input type="checkbox" class="allsubmodules" id="submodules"  ${submodules.isModuleConfigured  == 'Y' ? 'checked' : ''} ${submodules.isModuleConfigured  == 'Y' ? 'disabled' : 'true'}  >
							                              ${submodules.moduleName}
							                             </c:if>
							                             <c:if test="${submodules.isModuleConfigured  != 'Y'}">
							                    		  <input type="checkbox" class="allsubmodules" id="submodules" value="${submodules.id}"  ${submodules.isModuleConfigured  == 'Y' ? 'checked' : ''} ${submodules.isModuleConfigured  == 'Y' ? 'disabled' : 'true'} name="submodulesList[]" >
							                              ${submodules.moduleName}
							                             </c:if>
							                         </label><br/>
							                    	 </c:forEach>
							                    </ul> 
							                </c:if> 
								          </li>
								          <li>
								           <label class="mr pull-left">      
											    <c:if test="${module.id != '1' && module.isModuleConfigured  == 'Y'}">
									           		 <input type="checkbox" id="isRecruiting" ${module.isModuleConfigured  == 'Y' ? 'checked' : ''} ${module.isModuleConfigured  == 'Y' ? 'disabled' : 'true'}  >&nbsp;${module.moduleName}
								                </c:if>  
								                <c:if test="${module.id != '1' && module.isModuleConfigured  != 'Y'}">
									           		 <input type="checkbox" id="isRecruiting" value="${module.id}"  ${module.isModuleConfigured  == 'Y' ? 'checked' : ''} ${module.isModuleConfigured  == 'Y' ? 'disabled' : 'true'} name="modulesList[]" >&nbsp;${module.moduleName}
								                </c:if>  
								             </label>
								             </li>
						                </ul>
									</c:forEach>
						            </div>
					            </div> 
	                  </div>
				
					
					</div>
					<div class="clearfix">
					<div class="pull-left">
					<input type="button" value="Upgrade" onclick="updateModules();" id="save_button" class="btn btn-primary mr">
					<input type="button" value="Cancel" class="btn btn-default" onClick="self.location='/sourcelead/enrolledOrgsList'">
					</div>
					</div>
					</div>
					</form:form>
				</div>

			
			</div>
			</div>
		   </div>
		</div>
			<!-- Tab for Profile setting-area ends -->
		  </div>
		  </div>
  </section>
  <!-- Tab for Profile setting-area starts Ends -->
  <!-- Content Area Ends-->

</body>




<script type="text/javascript">
	function updateModules() {
		
		var roleId = '${roleId}';
		var sourceleadUrl = '${sourceleadUrl}';
			var sourceleadUrl ='${sourceleadUrl}'; 
			var orgId = '${orgId}';
			
			 var modulesList = [];
			 $("input[name='modulesList[]']:checked").each( function (i) {
				modulesList[i] = $(this).val();
			});
			 
			
			 var submodulesList = [];
			 $("input[name='submodulesList[]']:checked").each( function (i) {
				submodulesList[i] = $(this).val();
			});
			 
		 
			 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
			$.ajax({ 
		    	type: "POST",
		    	 url: sourceleadUrl+"/updateOrgModules?${_csrf.parameterName}=${_csrf.token}&modulesList="+modulesList+"&submodulesList="+submodulesList+"&orgId="+orgId,
		        success: function(result) {
		        	if((result == 'success')){
		        		location.replace(sourceleadUrl+"/enrolledOrgSummary?orgId="+orgId);
			        } 
		        },
		 });  
		 
	}

</script>
    


	
	



<jsp:include page="../masterFooter.jsp" />