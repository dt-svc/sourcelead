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


<style>
.edit_member_pop {
	top:12% !important; left: 35%; margin: 0px auto; z-index: 15500; width: 520px;
	position: fixed !important; background-color: #f9f9f9; box-shadow: 0px 0px 5px rgba(0,0,0,0.3); padding: 15px;
}
.edit_member_pop .footerBtns input {
    width: 50%; border: none; height: 30px; font-size: 14px; font-weight: 500; color: #FFF; background-color: #ff4b4b;
}
.edit_member_pop .footerBtns input.u_can_color {
    background-color: #375d81;
}
.edit_member_pop h4 {
background-color: #dcdcdc; padding: 10px; margin: 0; margin-bottom: 15px; font-size: 15px;
}
</style>


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

<style type="text/css">
.selectChosen .chosen-container {
width: 100% !important;
}
.icon1 {
right: 15px;
}
</style>

<div class="wraper"> 
	<section class="comLogBg">
			<section class="comLogInn">
				<div class="comLogHead">
				
	<c:if test="${!empty SourceLeadWeb_userSpocRolesList && SourceLeadWeb_prodSupportLoginUserRole != 'ROLE_PRODUCTION_SUPPORT'}">
		<div class="offOrg offOrgCompanyOnboard ">
             <a href="javascript:void(0)" title="Spoc Links" id="spoclinks" onclick="JavaScript: navtoggle(this);"> <i class="fa fa-globe"></i> </a>
	              <div id="spockLinksList" style="display: none;">
	              <ul class="list-unstyled">
	              <c:forEach var="links" items="${SourceLeadWeb_userSpocRolesList}">
		              <li>
			              <a href="<c:url value='${sourceleadUrl}/dashboard/${links.orgLocations.id}'/>" class="sopcSwith">
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
						  <li class="hoverLi">Banking details</li>
						  <li>Documents</li>
						  <li>Signatory</li>
						  <div class="clearfix"></div>
					  </ul>
				  <div class="clearfix"></div>
			  </div>
			  
			  <div class="comLogContent">
			  <div id='bankdetailsListLoading'></div>
			       <div class="row clearfix"> 
			       <div class="col-md-5">
			        <h2 class="pull-left">Organization Banking Details</h2>
			        	<a href="javascripit:void(0)" onclick="addNewBankingDetails();"  class="pull-left ml mt2"><i class="fa fa-plus paddingRight"></i>Add</a>
			        </div>
					</div>
					
					<div class="clearfix styleTables mt">
						<table width="100%">
							<thead>
								<tr>
								    <th>Bank Name</th>
								    <th>IFSC Code</th>
									<th>Account Name</th>
									<th>Account Number</th>
									<th>BSR Number</th>
									<th>MICR Code</th>
									<th>Address</th>
									<th>Actions</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${bankingDetailsDTOListSize > 0}">
									
										<c:forEach var="bankDetails" items="${bankingDetailsDTOList}">
										<tr>
												<td>${!empty bankDetails.bankName ? bankDetails.bankName : '---'}</td>
											    <td>${bankDetails.ifscCode}</td>
												<td>${bankDetails.accountName}</td>
												<td>${bankDetails.accountNumber}</td>
												<td>${!empty bankDetails.bsrNumber ? bankDetails.bsrNumber : '---'}</td>
												<td>${!empty bankDetails.micrCode ? bankDetails.micrCode : '---'}</td>
												<td class="tbldata-dotdot" title="${!empty bankDetails.bankAddress ? bankDetails.bankAddress : ''}">${!empty bankDetails.bankAddress ? bankDetails.bankAddress : '---'}</td>
												<td>
													<ul class="list-inline list-unstyled">
														<li><a href="javascript:void(0)" onclick="addNewBankingDetails('${bankDetails.uuid}');"><i class="fa fa-pencil"></i></a></li>
														<li><a href="javascript:void(0)" onclick="deleteBankingDetails('${bankDetails.uuid}');"><i class="fa fa-trash text-danger"></i></a></li>
													</ul>
													
												</td>
										</tr>
										</c:forEach>
										
									
									
								</c:if>
							</tbody>
						</table>
						<c:if test="${bankingDetailsDTOListSize == 0}">
						 <div class='row'> 
							 <div class='col-lg-12'> 
								 <div class='dataTables_empty'>
								 </div>
							 </div>
						 </div>
						</c:if>
					</div>
					<div class="welBtns welBtnsA">
									<c:if test="${SourceLeadWeb_orgLocations.country == 'United States'}">
									<input type="button" value="Prev" onClick="javascript:window.location.href = '<c:url value='/app/companyOnboardFederalAndStateTaxes'/>'">
									</c:if>
									
									<c:if test="${SourceLeadWeb_orgLocations.country == 'India'}">
									<input type="button" value="Prev" onClick="javascript:window.location.href = '<c:url value='/app/companyOnboardInformationDetails'/>'">
									</c:if>
									
									<c:if test="${SourceLeadWeb_orgLocations.country != 'India' && SourceLeadWeb_orgLocations.country != 'United States'}">
									<input type="button" value="Prev" onClick="javascript:window.location.href = '<c:url value='/app/companyOnboardLocations'/>'">
									</c:if>
									
									<input type="button" value="Skip this step" class="skipBtn" onClick="javascript:window.location.href = '<c:url value='/app/companyOnboardDocuments'/>'"></a>
									<div class="clearfix"></div>
								</div>	
				</div>	
			</section>
	</section>
</div>

<div id="bankingDetailsPopUp"></div>

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
 function addNewBankingDetails(uuid){
	 var result = "";
		result = result +"	<div class='project_inn_mask'></div>"; 
		result = result +" <div id='edit_member_pop' class='edit_member_pop'>";
		result = result +"	<div id='detailsLoading'></div>"; 
		result = result +"	<h4>Add Banking Details</h4>";
		result = result + '<div class="form-horizontal clearfix">';
		result = result + '<form id="bankingDetails_form" name="bankingDetails_form">';
		result = result + '<input type="hidden" id="uuid" name="uuid" value="">';
		
		
		result = result + '	    <div class="row">';
		
		result = result + '	    <div class="clearfix mb10 col-md-6">';
		result = result + '	      <label class="clearfix">Bank Name<span class="text-danger">&nbsp;*</span></label>';
		result = result + '	      <div class="">';
		result = result + '	        <input type="text" class="form-control inlineField"  id="bankName" name="bankName" field-name="Bank Name"  data-validation="required validate_Space validate_length length2-250">';
		result = result + '	      </div>';
		result = result + '	    </div>';
		
		result = result + '	    <div class="clearfix mb10 col-md-6">';
		result = result + '	      <label class="clearfix">IFSC Code<span class="text-danger">&nbsp;*</span></label>';
		result = result + '	      <div class="">';
		result = result + '	        <input type="text" class="form-control inlineField"  id="ifscCode" name="ifscCode" field-name="IFSC Code"  data-validation="required validate_Space validate_length length2-11" maxlength="11">';
		result = result + '	      </div>';
		result = result + '	    </div>';
		
		result = result + '	    </div>';
		
		
		result = result + '	    <div class="row">';
		
		result = result + '	    <div class="clearfix mb10 col-md-6">';
		result = result + '	      <label class="clearfix">Account Name<span class="text-danger">&nbsp;*</span></label>';
		result = result + '	      <div class="">';
		result = result + '	        <input type="text" class="form-control inlineField"  id="accountName" name="accountName" field-name="Account Name"  data-validation="required validate_Space validate_length length2-250">';
		result = result + '	      </div>';
		result = result + '	    </div>';
		
		result = result + '	    <div class="clearfix mb10 col-md-6">';
		result = result + '	      <label class="clearfix">Account Number<span class="text-danger">&nbsp;*</span></label>';
		result = result + '	      <div class="">';
		result = result + '	        <input type="text" class="form-control inlineField"  id="accountNumber" name="accountNumber" field-name="Account Number"  data-validation="required validate_Space validate_int validate_length length9-18">';
		result = result + '	      </div>';
		result = result + '	    </div>';
		
		result = result + '	    </div>';
		
		
		result = result + '	    <div class="row">';
		
		result = result + '	    <div class="clearfix mb10 col-md-6">';
		result = result + '	      <label class="clearfix">MICR Code</label>';
		result = result + '	      <div class="">';
		result = result + '	        <input type="text" class="form-control inlineField"  id="micrCode" name="micrCode" field-name="MICR Code" data-validation-optional="true"  data-validation="validate_space validate_length length2-9" maxlength="9">';
		result = result + '	      </div>';
		result = result + '	    </div>';
		
		result = result + '	    <div class="clearfix mb10 col-md-6">';
		result = result + '	      <label class="clearfix">BSR Number</label>';
		result = result + '	      <div class="">';
		result = result + '	        <input type="text" class="form-control inlineField"  id="bsrNumber" name="bsrNumber" field-name="BSR Number"  data-validation-optional="true" data-validation="validate_space validate_length length2-7" maxlength="7">';
		result = result + '	      </div>';
		result = result + '	    </div>';
		
		result = result + '	    </div>';
		
		
		
		result = result + '	    <div class="row">';
		
		result = result + '	    <div class="clearfix mb10 col-md-6">';
		result = result + '	      <label class="clearfix">Address<span class="text-danger">&nbsp;*</span></label>';
		result = result + '	      <div class="">';
		result = result + '	        <input type="text" class="form-control inlineField"  id="bankAddress" name="bankAddress" field-name="Address"  data-validation="required validate_space">';
		result = result + '	      </div>';
		result = result + '	    </div>';
		
		result = result + '	    </div>';
		
		
		result = result + ' <div id="reasonForProjectDiv" class="footerBtns">';
		result = result +'		<b href="javascript:void(0)"><input id="confirm-id" class="u_can_color" type="submit" value="Save"><input  type="button"  value="Cancel" onClick="cancelAddDetails();"></b>';
		result = result +"	</div>";
		result = result +"	</div>";
		
		result = result + '</form>';
		
		
		 $("#bankingDetailsPopUp").append(result);
         $("#edit_member_pop").slideDown(200);
         $(".project_inn_mask").fadeIn(200); 
         
         
		if(uuid != null && uuid != 'undefined' && uuid != ''){
			 $("#detailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			  setTimeout(function () {
				  var sourceleadUrl ='${sourceleadUrl}';
				  $.ajax({
				    	type: "GET",
				    	  url: sourceleadUrl+"/getOrgLocationsIndiaBankingDetailsByUUId?${_csrf.parameterName}=${_csrf.token}&uuid="+uuid,		
				        success: function(result) {
				        	
				        	$("#bankName").val(result.bankName);
				        	$("#bankName").val(result.bankName);
				        	$("#accountName").val(result.accountName);
				        	$("#bankAddress").val(result.bankAddress);
				        	$("#accountNumber").val(result.accountNumber);
				        	$("#ifscCode").val(result.ifscCode);
				        	
							if(result.bsrNumber != null && result.bsrNumber != '' && result.bsrNumber != 'undefined'){
								$("#bsrNumber").val(result.bsrNumber);
				        	}
							
							if(result.micrCode != null && result.micrCode != '' && result.micrCode != 'undefined'){
								$("#micrCode").val(result.micrCode);
				        	}
				        	
				        	$("#uuid").val(result.uuid);
				        	
				        },
				    });  
				  
				  $("#detailsLoading").removeAttr('style');
		 			}, 1000);
		}
 }
 
 
 
 
 function deleteBankingDetails(uuid){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $("#bankdetailsListLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	   $.ajax({
	    	type: "POST",
	    	  url: sourceleadUrl+"/deleteIndiaOnboardBankingDetails?${_csrf.parameterName}=${_csrf.token}&uuid="+uuid,		
	        success: function(result) {
	        	if (result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
  	    		    showError(result.responseMessage);
  	    		    $("#bankdetailsListLoading").removeAttr('style');
  	    		}
	        	else if (result.response === "AWKWARD") {
  	    		    location.href = "/errorPage";
  	    		}
            	else if(result.response === "SUCCESS"){
            		location.replace(sourceleadUrl+"/companyOnboardBankingDetails"); 
	        	}else if (result.indexOf('Sign In') !== -1) {
  					location.replace(sourceleadUrl);
  				}else {
  				    showError("Operation failed, please try again");
  				    $("#bankdetailsListLoading").removeAttr('style');
  				}
	        },
	    });  
 }
 
 
 function cancelAddDetails(){
		$("#bankingDetailsPopUp").empty();
		 $(".project_inn_mask").fadeOut(200);
		 $(".vendor_wrap_mask").fadeOut(200);
	}
 
 </script>
 
 
 <script type="text/javascript">
$('body').on('submit', '#bankingDetails_form', function(e){
	
	var validationSettings = {
	        errorMessagePosition : 'element',
	        borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         }; 
	
	if ($('#bankingDetails_form').validate(false, validationSettings)){
      e.preventDefault();
      var sourceleadUrl ='${sourceleadUrl}';
      var formData = getFormData("bankingDetails_form");
     
	 	$("#detailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	        $.ajax({
		    	type: "POST",
		    	data : formData,
		    	  url: sourceleadUrl+"/saveOrgLocationsIndiaBankingDetails?${_csrf.parameterName}=${_csrf.token}",		
		        success: function(result) {
		        	if (result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
      	    		    showError(result.responseMessage);
      	    		    $("#detailsLoading").removeAttr('style');
      	    		}
		        	else if (result.response === "AWKWARD") {
      	    		    location.href = "/errorPage";
      	    		}
                	else if(result.response === "SUCCESS"){
                		location.replace(sourceleadUrl+"/companyOnboardBankingDetails"); 
		        	}else if (result.indexOf('Sign In') !== -1) {
      					location.replace(sourceleadUrl);
      				}else {
      				    showError("Operation failed, please try again");
      				    $("#detailsLoading").removeAttr('style');
      				}
		        },
		    });     
      return true; 
    }
    else{
    	return false;	
       }
  });

	$('body').on('blur', '#bankingDetails_form', function() {
		$("#bankingDetails_form").showHelpOnFocus();
		$("#bankingDetails_form").validateOnBlur(false, validationSettings);
	});
	
	$(".loc-alertclose").click(function(){
		 $(".location-alert").fadeOut();
	})
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

<script>
		function logoutFormSubmit() {
			document.getElementById("logoutForm").submit();
		}
</script>


<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>