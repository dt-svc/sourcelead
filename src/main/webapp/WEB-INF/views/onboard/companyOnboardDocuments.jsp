<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>

<link rel="icon" type="image/png" href="/resources/images/fav.png" />
<title>Welcome to Source Lead :: The Recruitment Application</title>
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">


<link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/bootstrap-select.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/jquery.dataTables.min.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="/resources/css/jquery-ui.css?staticTime=${staticTime}">
<link rel="stylesheet" href="/resources/css/chosen.min.css?staticTime=${staticTime}">
<link href="/resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet" id="myStyleSheet">
<link href="/resources/css/timesheet.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="/resources/css/bootstrap-select.min.css?staticTime=${staticTime}">
<link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet" type="text/css">
<script type="text/javascript" src='/resources/js/slead-common.js?staticTime=${staticTime}'></script>

<script>
var sourceleadUrl = '${sourceleadUrl}';
</script>



<script>
  function navtoggle(elementRef){
	  
	 if('${SourceLeadWeb_prodSupportLoginUserRole}' != 'ROLE_PRODUCTION_SUPPORT'){
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
	 
	 if('${SourceLeadWeb_prodSupportLoginUserRole}' == 'ROLE_PRODUCTION_SUPPORT'){
		 document.getElementById("spockLinksList").removeAttribute("class")
		 document.getElementById('spockLinksList').className = 'spoclinks-area'
	     document.getElementById("spockLinksList").style.display="block";
	 }
	 
	  
  }
  </script>

 <script>
	function predefineOrgImage(imageId){
		$(imageId).attr('src','/resources/images/office-image.jpg');
	}
</script>

<script type="text/javascript">
$(function(){
  $('.crsl-items').carousel({
    visible: 3,
    itemMinWidth: 180,
    itemEqualHeight: 370,
    itemMargin: 9,
  });
  
  $("a[href=#]").on('click', function(e) {
    e.preventDefault();
  });
  
});
</script>

<div class="wraper"> 
	<section class="comLogBg">
		<section class="comLogInn">
			<div class="comLogHead">
			
			<jsp:include page="../userManageAccounts.jsp" />
          
				 <div class="comLogLogoText">${SourceLeadWeb_orgName}</div>
				  <div class="welSiO onboard-logoutbtn" onclick="logoutFormSubmit();" title="Sign Out"><i class="fa fa-sign-out mt5"></i></div>
				       <ul>
						  <li class="actLi">Organization details<i class="fa fa-check-circle"></i></li>
						  <li class="actLi">Locations<i class="fa fa-check-circle"></i></li>
						  <c:if test="${SourceLeadWeb_orgLocations.country == 'India'}">
						  <li class="actLi">Information<i class="fa fa-check-circle"></i></li>
						  </c:if>
						  <c:if test="${SourceLeadWeb_orgLocations.country == 'United States'}">
						  <li class="actLi">Federal & state taxes<i class="fa fa-check-circle"></i></li>
						  </c:if>
						   <li class="actLi">Banking details<i class="fa fa-check-circle"></i></li>
						  <li class="hoverLi">Documents</li>
						  <li>Signatory</li>
						  <div class="clearfix"></div>
					  </ul>
				  <div class="clearfix"></div>
			  </div>
			  
			   <form:form id="documents_form" name="documents_form"  enctype='multipart/form-data'  method="post">
			    <div class="comLogContent">
			      <div id="popUp" ></div>	
			      <div id="loading"></div> 
			      <h2>Organization Documents</h2>
			      <div class="row clearfix"> 
				      <div class="col-md-6">
				      	<div class="form-group clearfix">
						<label class="col-md-4 noPad">Business License<span class="text-danger">&nbsp;*</span></label>
							<div class="col-md-8 padding_right">
							
							<c:if test="${!empty businessLicenseDoc}">	
								<div class="clearfix">
									<div class="pull-left fileContainer" title="${businessLicenseDocName}">
									${businessLicenseDocName}
									</div>
									<div class="pull-left mt5">
										<a target="_blank" href="${businessLicenseDoc}" class="anchor-black"><i class="fa fa-download" title="Download"></i></a>
										<a href="javascript:void(0)" onclick="viewFile('${businessLicenseDoc}','${businessLicenseDocName}','${businessLicenseDocfileContentType}')"; class="paddingLeft PaddingRight anchor-black"><i class="fa fa-eye" title="View"></i></a>
										<a href="javascript:void(0)" onclick="deleteDocument('${businessLicenseDocfileUUID}','Business License')" class="anchor-black"><i class="fa fa-trash" title="Delete"></i></a>
									</div>
								</div>
							</c:if>
							
							<c:if test="${empty businessLicenseDoc}">
							<div class="input-group col-md-12 errorInptgroup" id="uploadFileGroup"
									style="margin: 0 auto">
									<label class="input-group-btn"> 
									<b class="btn btn-primary" onchange="getBusinessLicense();"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File <input type="file" style="display: none;" id="uploadBusinessLicenseBtn" class="upload"> </b>
									</label> 
									<input type="text" class="form-control" readonly id="orglocbusinessLicense"  name="orglocbusinessLicense" placeholder="Upload File ..." field-name="Business License"
										data-validation="required validatedoc">
								</div>
							</c:if>
							</div>
						</div>
						
						<div class="form-group clearfix">
						<label class="col-md-4 noPad">Bank Void Cheque<span class="text-danger">&nbsp;*</span></label>
						<div class="col-md-8 padding_right">
						<c:if test="${!empty bankChequeDoc}">	
								<div class="clearfix">
									<div class="pull-left fileContainer" title="${bankChequeDocName}">
									${bankChequeDocName}
									</div>
									<div class="pull-left mt5">
										<a target="_blank" href="${bankChequeDoc}" class="anchor-black"><i class="fa fa-download" title="Download"></i></a>
										<a href="javascript:void(0)" onclick="viewFile('${bankChequeDoc}','${bankChequeDocName}','${bankChequefileContentType}')"; class="paddingLeft PaddingRight anchor-black"><i class="fa fa-eye" title="View"></i></a>
										<a href="javascript:void(0)" onclick="deleteDocument('${bankChequefileUUID}','Bank Cheque')" class="anchor-black"><i class="fa fa-trash" title="Delete"></i></a>
									</div>
								</div>
						</c:if>
						
						<c:if test="${empty bankChequeDoc}">
							<div class="input-group col-md-12 errorInptgroup" id="uploadFileGroup"
									style="margin: 0 auto">
									<label class="input-group-btn"> 
									<span class="btn btn-primary" onchange="getBankCheque();"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File <input type="file" style="display: none;" id="uploadBankChequeBtn" class="upload"> </span>
									</label> 
									<input type="text" class="form-control" readonly id="orglocbankCheque" name="orglocbankCheque" placeholder="Upload File ..." field-name="Bank Cheque"
										data-validation="required validatedoc" >
								</div>
						</c:if>
						</div>
						</div>
						
						
						<div class="form-group clearfix">
						<label class="col-md-4 noPad">Registration or Incorporation of recording<span class="text-danger">&nbsp;*</span></label>
							<div class="col-md-8 padding_right">
							
							<c:if test="${!empty registrationDoc}">
							<div class="clearfix">
									<div class="pull-left fileContainer" title="${registrationDocName}">
									${registrationDocName}
									</div>
									<div class="pull-left mt5">
										<a target="_blank" href="${registrationDoc}" class="anchor-black"><i class="fa fa-download" title="Download"></i></a>
										<a href="javascript:void(0)" onclick="viewFile('${registrationDoc}','${registrationDocName}','${registrationDocfileContentType}')"; class="paddingLeft PaddingRight anchor-black"><i class="fa fa-eye" title="View"></i></a>
										<a href="javascript:void(0)" onclick="deleteDocument('${registrationDocUUID}','Registration')" class="anchor-black"><i class="fa fa-trash" title="Delete"></i></a>
									</div>
								</div>
							</c:if>
							
							<c:if test="${empty registrationDoc}">
							     <div class="input-group col-md-12 errorInptgroup" id="uploadFileGroup"
									style="margin: 0 auto">
									<label class="input-group-btn"> 
									<span class="btn btn-primary" onchange="getRegistrationDoc();"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File <input type="file" style="display: none;" id="uploadRegistrationBtn" class="upload"> </span>
									</label> 
									<input type="text" class="form-control" readonly id="orglocregistrationDocument" name="orglocregistrationDocument" placeholder="Upload File ..." field-name="Registration or Incorporation of recording"
										data-validation="required validatedoc">
								</div>
							</c:if>
							
							
							
							
							
							</div>
						</div>
						
						<div class="form-group clearfix">
						<label class="col-md-4 noPad">Annual Report Business License<span class="text-danger">&nbsp;*</span></label>
							<div class="col-md-8 padding_right">
							
							<c:if test="${!empty annulReportBusinessDoc}">
								<div class="clearfix">
									<div class="pull-left fileContainer" title="${annulReportBusinessDocName}">
									${annulReportBusinessDocName}
									</div>
									<div class="pull-left mt5">
										<a target="_blank" href="${annulReportBusinessDoc}" class="anchor-black"><i class="fa fa-download" title="Download"></i></a>
										<a href="javascript:void(0)" onclick="viewFile('${annulReportBusinessDoc}','${annulReportBusinessDocName}','${annulReportBusinessDocfileContentType}')"; class="paddingLeft PaddingRight anchor-black"><i class="fa fa-eye" title="View"></i></a>
										<a href="javascript:void(0)" onclick="deleteDocument('${annulReportBusinessDocUUID}','Annual Report Business License')" class="anchor-black"><i class="fa fa-trash" title="Delete"></i></a>
									</div>
								</div>
							</c:if>
							
							<c:if test="${empty annulReportBusinessDoc}">
							<div class="input-group col-md-12 errorInptgroup" id="uploadFileGroup"
									style="margin: 0 auto">
									<label class="input-group-btn"> 
									<span class="btn btn-primary" onchange="getAnnulBusinessLincense();"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File <input type="file" style="display: none;" id="uploadAnnualBusinessLicenseBtn" class="upload"> </span>
									</label> 
									<input type="text" class="form-control" readonly id="orglocannualBusinessLicense" name="orglocannualBusinessLicense" placeholder="Upload File ..." field-name="Annual Report Business License"
										data-validation="required validatedoc">
								</div>
							</c:if>
							</div>
						</div>
						
						<div class="form-group clearfix">
						<label class="col-md-4 noPad">W-9,W-8 Ben-E<span class="text-danger">&nbsp;*</span></label>
							<div class="col-md-8 padding_right">
							
							
							<c:if test="${!empty w9Doc}">
								<div class="clearfix">
									<div class="pull-left fileContainer" title="${w9DocName}">
									${w9DocName}
									</div>
									<div class="pull-left mt5">
										<a target="_blank" href="${w9Doc}" class="anchor-black"><i class="fa fa-download" title="Download"></i></a>
										<a href="javascript:void(0)" onclick="viewFile('${w9Doc}','${w9DocName}','${w9DocfileContentType}')"; class="paddingLeft PaddingRight anchor-black"><i class="fa fa-eye" title="View"></i></a>
										<a href="javascript:void(0)" onclick="deleteDocument('${w9DocUUID}','W-9,W-8 Ben-E')" class="anchor-black"><i class="fa fa-trash" title="Delete"></i></a>
									</div>
								</div>
							</c:if>
							
							<c:if test="${empty w9Doc}">
							<div class="input-group col-md-12 errorInptgroup" id="uploadFileGroup"
									style="margin: 0 auto">
									<label class="input-group-btn"> 
									<span class="btn btn-primary" onchange="getW9Doc();"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File <input type="file" style="display: none;" id="uploadW9Btn" class="upload"> </span>
									</label> 
									<input type="text" class="form-control" readonly id="orglocw9Document" name="orglocw9Document" placeholder="Upload File ..." field-name="W-9,W-8 Ben-E"
										data-validation="required validatedoc">
								</div>
							</c:if>	
								
								
							</div>
						</div>
						
						
				      </div>
			      </div>
			    
			        <div class="welBtns welBtnsA">
						<input type="button" value="Prev" onClick="javascript:window.location.href = '<c:url value='/app/companyOnboardBankingDetails'/>'"><input type="button" onclick="saveDocuments();" value="Save & Continue" class="Brig" id="save_button">
						<input type="button" value="Skip this step" class="skipBtn" onClick="javascript:window.location.href = '<c:url value='/app/companyOnboardSignatory'/>'"></a>
						<div class="clearfix"></div>
					</div>
			    </div>
			   </form:form>
		</section>
	</section>
</div>
					<div class="location-alert alert alert-danger alert-dismissable zInPop" id="locationAlert">
    <a href="#" class="loc-alertclose close">&times;</a>
    <strong>Alert !</strong> <span>Your are not able to Save Required data is Missing.</span>
  </div>
  
 <script type="text/javascript" src="/resources/js/jquery-1.11.0.min.js?staticTime=${staticTime}"></script>
 <script type="text/javascript" src="/resources/js/jquery.liquidcarousel.pack.js?staticTime=${staticTime}"></script>	
	
  <script src="/resources/js/jquery.dataTables.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/moment.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/jquery-ui.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/bootstrap-select.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/bootstrap.min.js?staticTime=${staticTime}"></script> 
  <script src="/resources/js/masterHeaderAction.js?staticTime=${staticTime}"></script> 
 <script src="/resources/js/chosen.jquery.min.js?staticTime=${staticTime}"></script> 
 <script src="/resources/js/formvalidator.js"></script>
 
 <script>

if(document.getElementById("uploadBusinessLicenseBtn") != null){
	document.getElementById("uploadBusinessLicenseBtn").onchange = function () {
	  	$('#orglocbusinessLicense').val($(this).val().replace(/C:\\fakepath\\/i, ''));
	  };
}
 
 if(document.getElementById("uploadBankChequeBtn") != null){
	 document.getElementById("uploadBankChequeBtn").onchange = function () {
		  	$('#orglocbankCheque').val($(this).val().replace(/C:\\fakepath\\/i, ''));
	  };
 }
  
  
  if(document.getElementById("uploadRegistrationBtn") != null){
	  document.getElementById("uploadRegistrationBtn").onchange = function () {
		  	$('#orglocregistrationDocument').val($(this).val().replace(/C:\\fakepath\\/i, ''));
	  };  
  }
  
  if(document.getElementById("uploadAnnualBusinessLicenseBtn") != null){
	  document.getElementById("uploadAnnualBusinessLicenseBtn").onchange = function () {
		  	$('#orglocannualBusinessLicense').val($(this).val().replace(/C:\\fakepath\\/i, ''));
	  };
  }
  
  if(document.getElementById("uploadW9Btn") != null){
	  document.getElementById("uploadW9Btn").onchange = function () {
		  	$('#orglocw9Document').val($(this).val().replace(/C:\\fakepath\\/i, ''));
	  };
  }

  $(".loc-alertclose").click(function(){
		 $(".location-alert").fadeOut();
	})  
 </script>
 
 
 <script>
 function getBusinessLicense(){
	 fileUpload("uploadBusinessLicenseBtn", "CompanyOnboardDocs", "documents_form","businessLicense");
 }
 
 function getBankCheque(){
	 fileUpload("uploadBankChequeBtn", "CompanyOnboardDocs", "documents_form","bankCheque");
 }
 
 function getRegistrationDoc(){
	 fileUpload("uploadRegistrationBtn", "CompanyOnboardDocs", "documents_form","registrationDocument");
 }
 
 function getAnnulBusinessLincense(){
	 fileUpload("uploadAnnualBusinessLicenseBtn", "CompanyOnboardDocs", "documents_form","annualBusinessLicense");
 }
 
 function getW9Doc(){
	 fileUpload("uploadW9Btn", "CompanyOnboardDocs", "documents_form","w9Document");
 }
 </script>
 
 
 <script type="text/javascript">
	function saveDocuments(){
			var validationSettings = {
			        errorMessagePosition : 'element',
			        borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
		         }; 
			 if ($('#documents_form').validate(false, validationSettings)){
	      	    var formData = getFormData("documents_form");
	      		var sourceleadUrl = '${sourceleadUrl}';
	      		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	      		$.ajax({
						type : "POST",
						 data: formData,
						 url: sourceleadUrl+"/saveOrgLocationDocuments",
								success : function(result) {
									if (result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
				      	    		    showError(result.responseMessage);
				      	    		    $("#loading").removeAttr('style');
				      	    		}
						        	else if (result.response === "AWKWARD") {
				      	    		    location.href = "/errorPage";
				      	    		}
				                	else if(result.response === "SUCCESS"){
				                		 $("#loading").removeAttr('style');
				                		location.replace(sourceleadUrl+"/companyOnboardSignatory"); 
				                	}else if (result.indexOf('Sign In') !== -1) {
				      					location.replace(sourceleadUrl);
				      				}else {
				      				    showError("Operation failed, please try again");
				      				    $("#loading").removeAttr('style');
				      				}
						
						},
					}); 
					return true;
			 }else{ 
					return false;
			 } 
		}
		$('body').on('blur', '#documents_form', function() {
		$("#documents_form").showHelpOnFocus();
		$("#documents_form").validateOnBlur(false, validationSettings);
	});
</script>


<script>
function deleteDocument(uuid,documentType){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are your sure to delete <strong>"+documentType+"</strong> document</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onClick='deleteDoc(\""+uuid+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelPopUp();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>


<script>
function deleteDoc(uuid){
	var sourceleadUrl = '${sourceleadUrl}';
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	   $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/deleteOnboardDocument?${_csrf.parameterName}=${_csrf.token}&uuid="+uuid, 
	        success: function(data) {
	        	if(data == 'success'){
	        		location.replace(sourceleadUrl+"/companyOnboardDocuments"); 
	        	}
	        },
	    });
	 
}
</script>

<script type="text/javascript">
function cancelPopUp(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<script>
  $(document).ready(function(){
  	  $('html').click(function(){
	  $('#spockLinksList').hide();
  });
  
  $(".offOrg").click(function( event ) {
	  event.stopPropagation();
	});
  });
</script>

