<!DOCTYPE html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" type="image/png" href="resources/images/fav.png" />
<title>Welcome to Source Lead :: The Recruitment Application</title>
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/bootstrap-select.css" rel="stylesheet">
<link href="resources/css/jquery.dataTables.min.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/jquery-ui.css">
<link rel="stylesheet" href="resources/css/chosen.min.css">
<link href="resources/css/font-awesome.min.css" rel="stylesheet">
<link href="resources/css/sourcelead-main.css" rel="stylesheet" id="myStyleSheet">
<link href="resources/css/timesheet.css" rel="stylesheet">

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="resources/js/jquery-1.11.1.min.js"></script>
<style type="text/css">
    .require_wrap_table.overflowVisible{
        overflow: visible !important;
    }
    </style>
</head>


<div class="wraper"> 

  
  <!--   Welcome Screens   -->
  
	<section class="welcoBg">
		<section class="welcoInn">
		  <div class="welcoHead">
				
			<c:if test="${!empty SourceLeadWeb_userSpocRolesList}">
			<div class="offOrg">
	             <a href="javascript:void(0)" title="Spoc Links" id="spoclinks" onclick="JavaScript: navtoggle(this);"> <i class="fa fa-globe"></i> </a>
		              <div id="spockLinksList" style="display: none;">
		              <ul class="list-unstyled">
		              <c:forEach var="links" items="${SourceLeadWeb_userSpocRolesList}">
			              <li>
				              <a href="<c:url value='${sourceleadUrl}/dashboard/${links.orgLocations.id}'/>" class="sopcSwith">
					                <div class="clearfix">
					                   <div class="pull-left image-parent positionRelative">
					                   <div class="imageParent org-imagelink ${links.orgLocations.id == SourceLeadWeb_orgLocationId ? 'active-comp' : ''}">
					                     <img onerror='predefineOrgImage(this);' src="${sourceleadUrl}/displayOrgImage/${links.orgLocations.org.orgCode}">
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
				
				<c:if test="${!empty SourceLeadWeb_org.fileContentType}">
					<div class="welLogo">
					<img class="logo" src="<c:url value='/displayOrgImage/${SourceLeadWeb_orgCode}'/>" title="${SourceLeadWeb_orgName}" />
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
				  <li class="actLi"><span>3</span><b>Step three</b><i class="line"></i></li>
				  <li><span>4</span><b>Finish</b><i class="line"></i></li>
				  <div class="clearfix"></div>
			  </ul>
			  <div class="clearfix"></div>
		  </div>
	
	<form:form class="login-form clearfix"  id="onboardCandidateDocs_form"  name="onboardCandidateDocs_form" enctype='multipart/form-data' >	  
		<div id="loading"></div>
		<div class="welCo1">
        <input type="hidden" name="candidateOnboardId" id="candidateOnboardId" value="${candidateOnboardId}"/>
			<div class="step3Wrap">
			<h2>Employment Contract</h2>
				<c:if test="${contractDocsListSize > 0}">
				<c:forEach var="contractDocs" items="${contractDocsList}">
				<div class="step3">
					<span><a href="${sourceleadUrl}/downloadTemplateDocument?documentUUID=${contractDocs.documentUUID}" class="fa fa-file-text"></a></span>
					<strong>
						
						<c:if test="${contractDocs.documentStatus != 'ACCEPTED'}">
						   <p>${contractDocs.fileName}</p>
						</c:if>
						
						<c:if test="${contractDocs.documentStatus == 'ACCEPTED'}">
						   <p>${contractDocs.uploadedDocFileName}</p>
						</c:if>
						
						<c:if test="${contractDocs.documentStatus != 'ACCEPTED'}">
						<input type="file"  class="form-control welFile" readonly id="uploadContractsFile${contractDocs.id}" name="docFile${contractDocs.id}"   placeholder="Upload Resume ..." field-name="Upload Resume" data-validation="validatedoc" data-validation-optional="true" >
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
					<span><a href="${sourceleadUrl}/downloadTemplateDocument?documentUUID=${formsDocs.documentUUID}" class="fa fa-file-text"></a></span>
					<strong>
						<c:if test="${formsDocs.documentStatus != 'ACCEPTED'}">
						   <p>${formsDocs.fileName}</p>
						</c:if>
						
						<c:if test="${formsDocs.documentStatus == 'ACCEPTED'}">
						   <p>${formsDocs.uploadedDocFileName}</p>
						</c:if>
						
						
						<c:if test="${formsDocs.documentStatus != 'ACCEPTED'}">
						<input  type="file" class="form-control welFile" readonly id="uploadFormsFile${formsDocs.id}"  name="docFile${formsDocs.id}"  placeholder="Upload Resume ..." field-name="Upload Resume" data-validation="validatedoc" data-validation-optional="true" >
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
					<span><a href="${sourceleadUrl}/downloadTemplateDocument?documentUUID=${policiesDocs.documentUUID}" class="fa fa-file-text"></a></span>
					<strong>
						<p>${policiesDocs.fileName}</p>
				
					<div>
	                    <input id="policyCheck${policiesDocs.id}" class="checkbox-custom" ${policiesDocs.documentStatus  == 'ACCEPTED' ? 'checked' : ''}  onclick="acceptPolicy('${policiesDocs.documentUUID}')" type="checkbox" checked="checked">
	                    
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
	<input type="button"  value="Previous" onClick="javascript:window.location.href = '<c:url value='/onboardingCandidateSetp2'/>'" >
	<input type="button"  value="Proceed to next step" id="save_button"  onclick="saveOnboardingCandidateDocs();" class="Brig">
	<div class="clearfix"></div>
</div>
 </form:form>.
		</section>
	</section>  
  
  </div>

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
  <script src="resources/js/jquery.dataTables.min.js"></script> 
  <script src="resources/js/moment.min.js"></script> 
  <script src="resources/js/jquery-ui.min.js"></script> 
  <script src="resources/js/bootstrap-select.min.js"></script> 
  <script src="resources/js/bootstrap.min.js"></script> 
  <script src="resources/js/masterHeaderAction.js"></script> 
  <script src="resources/js/chosen.jquery.min.js"></script> 
  
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
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/deleteOnboardCandidateUploadedDocument?${_csrf.parameterName}=${_csrf.token}&documentUUID="+documentUUID, 
	        success: function(result) {
	        	location.replace(sourceleadUrl+"/onboardingCandidateSetp3");  
	        },
	    });
}
</script>


<script>
function acceptPolicy(documentUUID){
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/acceptCandidatePolicyDocument?${_csrf.parameterName}=${_csrf.token}&documentUUID="+documentUUID, 
	        success: function(result) {
	        	$("#loading").removeAttr("style"); 
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
		  var frm = $('#onboardCandidateDocs_form').serializeFormJSON();
	      var con = JSON.stringify(frm);
	      con = con.replace(/[{}]/g, "");
	      
	      //It will escape all the special characters
	      var jsonData = encodeURIComponent(con);
	      
		   var form = $('#onboardCandidateDocs_form')[0];
		   var formData = new FormData(form); 
		
	   $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
	        type : "POST",
		      data: formData,
		      contentType : 'application/json; charset=utf-8',
		      enctype: 'multipart/form-data',
	          processData: false,
	          contentType:false,
		      dataType : 'json',
	    	  url: sourceleadUrl+"/storeOnboardingCandidateDocsStep3?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,		
		      success :function(result) {
		    	  location.replace(sourceleadUrl+"/onboardingCandidateSetp4");  
	      }
	    	  
	  });
		return true;
	  }else{
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
		$(imageId).attr('src','resources/images/office-image.jpg');
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

<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<script src="resources/js/formvalidator.js"></script>