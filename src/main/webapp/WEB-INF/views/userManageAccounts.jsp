<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<c:if test="${!empty SourceLeadWeb_userSpocRolesList && SourceLeadWeb_prodSupportLoginUserRole != 'ROLE_PRODUCTION_SUPPORT'}">
		<div class="offOrg offOrgCompanyOnboard ">
             <a href="javascript:void(0)" title="Spoc Links" id="spoclinks" onclick="JavaScript: navtoggle(this);"> <i class="fa fa-globe"></i> </a>
	              <div id="spockLinksList" style="display: none;">
	              <ul class="list-unstyled">
	              <c:forEach var="links" items="${SourceLeadWeb_userSpocRolesList}">
		              <li>
		              <a onclick="checkAuth(${links.orgLocations.id},${links.id},'${links.orgLocations.loginSecurity}','${links.user.id}');" class="sopcSwith">
				                <div class="clearfix">
				                   <div class="pull-left image-parent positionRelative">
				                   <div class="imageParent org-imagelink ${links.orgLocations.id == SourceLeadWeb_orgLocationId ? 'active-comp' : ''}">
				                   <c:if test="${!empty links.orgLocations.org.fileContentType}">
				                     <img onerror='predefineOrgImage(this);' data-id="orgLogoImage" src="${links.orgLocations.org.fileUrl}">
				                   </c:if>
				                   <c:if test="${empty links.orgLocations.org.fileContentType}">
				                     <img src="/resources/images/office-image.jpg">
				                   </c:if>
				                     
				                     </div>
				                   </div>
				                   <div class="pull-left mt5">
				                     <p class="tbldata-dotdot"><i class="fa fa-building"></i>&nbsp;${links.orgLocations.org.orgName}</p>
				                     <p class="tbldata-dotdot"><i class="fa fa-map-marker"></i>&nbsp;${!empty links.orgLocations.city ? links.orgLocations.city : ""} ${links.orgLocations.country}</p>
				                     <p><i class="fa fa-user"></i>&nbsp;${links.roles.displayName}</p>
				                   </div>
				                </div>
			                </a>
		              </li>
	              </c:forEach>
	              </ul>
	            </div>
	            </div>
          </c:if>
          
          
          <c:if test="${SourceLeadWeb_prodSupportLoginUserRole == 'ROLE_PRODUCTION_SUPPORT'}">
          	<div class="offOrg offOrgCompanyOnboard ">
             <a href="javascript:void(0)" title="Spoc Links" id="spoclinks" onclick="JavaScript: navtoggle(this);"> <i class="fa fa-globe"></i> </a>
	              <div id="spockLinksList" style="display: none;">
	              <ul class="list-unstyled">
		              <li>
		              <a href="<c:url value='${sourceleadUrl}/productionSupportdashboard/${SourceLeadWeb_prodSupportOrgLocationId}/${SourceLeadWeb_prodSupportOrgLocationId}'/>" class="sopcSwith">
					                <div class="clearfix">
					                   <div class="pull-left image-parent positionRelative">
						                   <div class="imageParent org-imagelink">
						                   <img src="/resources/images/office-image.jpg">
						                     </div>
						                   </div>
					                       <div class="pull-left">
						                     <p class="tbldata-dotdot"><i class="fa fa-building"></i>&nbsp;${SourceLeadWeb_prodSupportLoginOrgName}</p>
						                     <p><i class="fa fa-user"></i>&nbsp;${SourceLeadWeb_prodSupportLoginUserDisplayName}</p>
						                   </div>
					                </div>
				                </a>
		              </li>
	              </ul>
	            </div>
	            </div>		
		              			
              </c:if>
              
              
 <script type="text/javascript">
 var hasLocationAccess = "";
 function checkAuth(locationId,roleId,orgSecurity,userId){
	 checkLocationAccess(locationId,roleId,orgSecurity,userId);
	 
 }
 function otpClose(){
	 $(".authenticationMask").fadeOut(); 
 }
 $(".loc-alertclose").click(function(){
	 $(".location-alert").fadeOut();
 })

 function getUserDetailsbyroleId(roleId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
		 type : "POST",
	       url: sourceleadUrl+"/ws/getUserDetailsByRoleId?id="+roleId,        
	       success :function(data) {
	    	   if(data != null){
	    		   $("#directtwoStepAuthDiv").empty();
	    		   $("#directtwoStepAuthDiv").append(data);
	    		   $("#gobackBtn").hide();
	    		   $(".otpBackNew").show();
	    		   $("#otpBackNewId").val("switchOtp");
	    		   $("#resendOTP").attr("style","display:none");
	    		   $("#sendOTP").attr("style","display:block");
	    	   }
	       },						     
	}); 
 }
 
 function checkLocationAccess(locationId,roleId,orgSecurity,userId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
	      type : "GET",
	       url: sourceleadUrl+"/ws/validateWhitelistIp?userRoleId="+roleId,        
	       success :function(result) {
	    	   if(result == true){
	    			 if(('${SourceLeadWeb_isAuthenticated}' == 'Y' && orgSecurity == 'N') || ('${SourceLeadWeb_isAuthenticated}' == 'Y' && orgSecurity == 'Y')){
	    				 location.replace(sourceleadUrl+"/dashboard/"+locationId+"/"+userId);   
	    			 }else if('${SourceLeadWeb_isAuthenticated}' == 'N' && orgSecurity == 'Y'){
	    				 $(".authenticationMask").fadeIn();
	    				 getUserDetailsbyroleId(roleId);
	    			 }else{
	    				 location.replace(sourceleadUrl+"/dashboard/"+locationId+"/"+userId);    
	    			 }
	    		 }else{
	    			 $(".location-alert").fadeIn();
	    			 $(".location-alert").fadeOut(5000);
	    		 }
	    	   
	      },
	  });
 }
 </script>    
 
 
 <script>
		function logoutFormSubmit() {
			document.getElementById("logoutForm").submit();
		}
</script>


<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>         
              
