<!DOCTYPE html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>
 
<link rel="icon" type="image/png" href="/resources/images/fav.png" />
<title>Welcome to Source Lead :: The Recruitment Application</title>
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<!-- Bootstrap -->
<link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/bootstrap-select.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/jquery.dataTables.min.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="/resources/css/jquery-ui.css?staticTime=${staticTime}">
<link rel="stylesheet" href="/resources/css/chosen.min.css?staticTime=${staticTime}">
<link href="/resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet" id="myStyleSheet">
<link href="/resources/css/timesheet.css?staticTime=${staticTime}" rel="stylesheet">
<script src="/resources/js/jquery-1.11.1.min.js"></script>

</head>

<script>
 var sourceleadUrl ='${sourceleadUrl}';
</script>
<div class="wraper"> 

  
  <!--   Welcome Screens   -->
  
	<section class="welcoBg">
		<section class="welcoInn">
		  <div class="welcoHead">
				
			 <jsp:include page="../userManageAccounts.jsp" />
				
				<c:if test="${!empty SourceLeadWeb_org.fileContentType}">
					<div class="welLogo">
					<img class="logo" data-id="orgLogoImage" src="${SourceLeadWeb_org.fileUrl}" title="${SourceLeadWeb_orgName}" />
					</div>
				</c:if>
				
				<c:if test="${empty SourceLeadWeb_org.fileContentType}">
					<div class="welLogoText">
					<span  title="${SourceLeadWeb_orgName}">${SourceLeadWeb_orgName}</span>
					</div>
				</c:if>
				
				<div class="welSiO" onclick="logoutFormSubmit();" title="Sign Out"><i class="fa fa-sign-out"></i></div>
			  <ul>
				  <li class="actLi"><span class="fill">1</span><b>Welcome</b><i class="line"></i></li>
				  <li class="actLi"><span class="fill">2</span><b>Step two</b><i class="line"></i></li>
				  <li class="actLi"><span class="fill">3</span><b>Step three</b><i class="line"></i></li>
				  <li class="actLi"><span class="fill">4</span><b>Step four</b><i class="line"></i></li>
				  <li class="actLi"><span>5</span><b>Step five</b><i class="line"></i></li>
				  <li><span>6</span><b>Finish</b><i class="line"></i></li>
				  
				  <div class="clearfix"></div>
			  </ul>
			  <div class="clearfix"></div>
		  </div>
	
	<form:form class="login-form clearfix"  id="onboardCandidateDocs_form"  name="onboardCandidateDocs_form">	  
		<div id="loading"></div>
		<div class="welCo1">
        <input type="hidden" name="candidateOnboardId" id="candidateOnboardId" value="${candidateOnboardId}"/>
			<div class="step3Wrap">
			<h2>Employment Contract</h2>
				<c:if test="${contractDocsListSize > 0}">
				<c:forEach var="contractDocs" items="${contractDocsList}">
				<div class="step3">
				<c:if test="${!empty contractDocs.uploadFileURL && !empty contractDocs.fileURL}">
				<span><a href="${contractDocs.uploadFileURL}"  class="fa fa-file-text"></a></span>
				</c:if>
				<c:if test="${!empty contractDocs.fileURL && empty contractDocs.uploadFileURL}">
				<span><a href="${contractDocs.fileURL}"  class="fa fa-file-text"></a></span>
				</c:if>
					<strong>
						
						<c:if test="${contractDocs.documentStatus != 'ACCEPTED'}">
						   <p>${contractDocs.fileName}</p>
						</c:if>
						
						<c:if test="${contractDocs.documentStatus == 'ACCEPTED'}">
						   <p>${contractDocs.uploadedDocFileName}</p>
						</c:if>
						
						<c:if test="${contractDocs.documentStatus != 'ACCEPTED'}">
						<input type="file"  class="form-control welFile" readonly id="uploadContractsFile${contractDocs.id}" placeholder="Upload Resume ..." field-name="Upload Resume" data-validation="validatedoc"  onChange="uploadContract(${contractDocs.id});"   data-validation-optional="true" >
						</c:if>
						
						<c:if test="${contractDocs.documentStatus == 'ACCEPTED'}">
						<p class="deleDocu" onclick="deleteDocument('${contractDocs.documentUUID}');"><i class="fa fa-trash"></i>Delete document</p>
						</c:if>
						
					</strong>
					<div class="clearfix"></div>
				</div>
				</c:forEach>
				</c:if>
				<c:if test="${contractDocsListSize == 0}">
				       <p>No Contract Files Found</p>
				</c:if>
				<div class="clearfix"></div>			
			</div>	
			
			
			
		
			<div class="step3Wrap">
			<h2 class="padTop">Forms</h2>
				<c:if test="${formsDocsListSize > 0}">
				<c:forEach var="formsDocs" items="${formsDocsList}">
				<div class="step3">
					<c:if test="${!empty formsDocs.uploadFileURL && !empty formsDocs.fileURL}">
						<span><a href="${formsDocs.uploadFileURL}"  class="fa fa-file-text"></a></span>
					</c:if>
					<c:if test="${!empty formsDocs.fileURL && empty formsDocs.uploadFileURL}">
						<span><a href="${formsDocs.fileURL}"  class="fa fa-file-text"></a></span>
					</c:if>
					<strong>
						<c:if test="${formsDocs.documentStatus != 'ACCEPTED'}">
						   <p>${formsDocs.fileName}</p>
						</c:if>
						
						<c:if test="${formsDocs.documentStatus == 'ACCEPTED'}">
						   <p>${formsDocs.uploadedDocFileName}</p>
						</c:if>
						
						
						<c:if test="${formsDocs.documentStatus != 'ACCEPTED'}">
						<input  type="file" class="form-control welFile" readonly id="uploadFormsFile${formsDocs.id}"   placeholder="Upload Resume ..." field-name="Upload Resume" onChange="uploadForms(${formsDocs.id})" data-validation="validatedoc" data-validation-optional="true" >
						</c:if>
						
						<c:if test="${formsDocs.documentStatus == 'ACCEPTED'}">
						<p class="deleDocu" onclick="deleteDocument('${formsDocs.documentUUID}');"><i class="fa fa-trash"></i>Delete document</p>
						</c:if>
						
						
					</strong>
					<div class="clearfix"></div>
				</div>
				</c:forEach>
				</c:if>	
				
				<c:if test="${formsDocsListSize == 0}">
				  <p>No Forms Found</p>
				</c:if>
				
				<div class="clearfix"></div>			
			</div>	
		
		
		
			
			<div class="step3Wrap">
			<h2 class="padTop">Policies</h2>
				<c:if test="${policiesDocsListSize > 0}">
				<c:forEach var="policiesDocs" items="${policiesDocsList}">
				<div class="step3">
					<span><a href="${policiesDocs.fileURL}"  class="fa fa-file-text"></a></span>
					<strong>
						<p>${policiesDocs.fileName}</p>
				
					<div>
	                    <input id="policyCheck${policiesDocs.id}" class="checkbox-custom" ${policiesDocs.documentStatus  == 'ACCEPTED' ? 'checked' : ''}  onclick="acceptPolicy('${policiesDocs.documentUUID}')" type="checkbox">
	                    
	                    <label for="policyCheck${policiesDocs.id}" class="checkbox-custom-label">&nbsp;&nbsp;<b>Accept Policy</b></label>
	                </div>
						
						
					</strong>
					<div class="clearfix"></div>
				</div>
				</c:forEach>
				</c:if>	
				
				<c:if test="${policiesDocsListSize == 0}">
					<p>No Policies Found</p>
				</c:if>
				<div class="clearfix"></div>			
			</div>	
									
  </div>		  
		  
<div class="welBtns">

	<input type="button"  value="Previous" onClick="javascript:window.location.href = '<c:url value='${sourceleadUrl}/onboardingCandidateTaxDetails'/>'" >
	<input type="button"  value="Proceed to next step" id="save_button"  onclick="saveOnboardingCandidateDocs();" class="Brig">
	<div class="clearfix"></div>
</div>
 </form:form>.
		</section>
	</section>  
  
  </div>

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/jquery.dataTables.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/moment.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/jquery-ui.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/bootstrap-select.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/bootstrap.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/masterHeaderAction.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/chosen.jquery.min.js?staticTime=${staticTime}"></script> 
  
  <script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});

});
</script> 


<script>
function deleteDocument(documentUUID){
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/deleteOnboardCandidateUploadedDocument?${_csrf.parameterName}=${_csrf.token}&documentUUID="+documentUUID, 
	        success: function(response) {
	        	if (response && response.response === "INVALID_DATA") {
	        	    showError(response.responseMessage);
	        	} else if (response.response === "AWKWARD") {
	        	    location.href = "/errorPage";
	        	} else if (response.response === "SUCCESS") {
	        		location.replace(sourceleadUrl+"/onboardingCandidateSetp3");  
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
function acceptPolicy(documentUUID){
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/acceptCandidatePolicyDocument?${_csrf.parameterName}=${_csrf.token}&documentUUID="+documentUUID, 
	        success: function(response) {
	        	if (response.response === "AWKWARD") {
	        	    location.href = "/errorPage";
	        	} else if (response.response === "SUCCESS") {
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
function saveOnboardingCandidateDocs(saveType){
	var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         }; 
	
	  var sourceleadUrl ='${sourceleadUrl}';
	  if ($('#onboardCandidateDocs_form').validate(false, validationSettings)){ 
		 var formData = getFormData1("onboardCandidateDocs_form");
	   $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	   $.ajax({
	        type : "POST",
		      data: formData,
	          processData: false,
	          contentType:false,
	    	  url: sourceleadUrl+"/storeOnboardingCandidateDocsStep3?${_csrf.parameterName}=${_csrf.token}",		
		      success :function(response) {
		    	  if (response.response === "AWKWARD") {
		    		    location.href = "/errorPage";
		    		} else if (response.response === "SUCCESS") {
		    			 location.replace(sourceleadUrl+"/onboardingCandidateStep4");  
		    		} else if (response.indexOf('Sign In') !== -1) {
		    			location.replace(sourceleadUrl);
		    		} else {
		    		    showError("Operation failed, please try again");
		    		}
	      	}
	  });
		return true;
	  } else {
		  return false;
	  }
}
</script>


<script type="text/javascript">
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
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
 
 
 <script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };

        
</script>  


<script>


function uploadContract(id){ 
	fileUpload("uploadContractsFile"+id+"", "Welcome-Onboarding-Contract-Documents", "onboardCandidateDocs_form","uploadContractsFile");
}
function uploadForms(id){
	fileUpload("uploadFormsFile"+id+"", "Welcome-Onboarding-Forms-Documents", "onboardCandidateDocs_form","uploadFormsFile");
}
 
function uploadFormFile(id){
	 $("#uploadFormsFile"+id+"").val($("#uploadFormsBtn"+id+"").val().replace(/C:\\fakepath\\/i, ''));
}

function uploadContractFile(id){
	
	$("#uploadContractsFile"+id+"").val($("#uploadContractsBtn"+id+"").val().replace(/C:\\fakepath\\/i, ''));
}

function uploadPoliciesFile(id){
	
	$("#uploadPoliciesFile"+id+"").val($("#uploadPoliciesBtn"+id+"").val().replace(/C:\\fakepath\\/i, ''));
}
	 
</script>
<script src="/resources/js/slead-common.js"></script>
<script>
  function navtoggle(elementRef){
	  
	 if('${SourceLeadWeb_userSpocRolesListCount}' < 6){
		 document.getElementById("spockLinksList").removeAttribute("class")
		 document.getElementById('spockLinksList').className = 'spoclinks-area'
	     document.getElementById("spockLinksList").style.display="block";
	 }else{
		 document.getElementById("spockLinksList").removeAttribute("class")
		 document.getElementById('spockLinksList').className = 'spoclinks-area spoclinksMore'
		 document.getElementById("spockLinksList").style.display="block";
	 }
  }
  $('html').click(function(){
	  $('#spockLinksList').hide();
  });
  
  $(".offOrg").click(function( event ) {
	  event.stopPropagation();
	});
  </script>
  
<script>
	function predefineOrgImage(imageId){
		$(imageId).attr('src','/resources/images/office-image.jpg');
	}
</script>




<script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
