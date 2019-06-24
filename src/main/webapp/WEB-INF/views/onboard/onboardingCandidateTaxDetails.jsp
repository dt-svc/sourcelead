<!DOCTYPE html>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>
 
<link rel="icon" type="image/png" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/fav.png" />
<title>Welcome to Source Lead :: The Recruitment Application</title>
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<!-- Bootstrap -->

<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/bootstrap-select.css?staticTime=${staticTime}" rel="stylesheet">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/jquery.dataTables.min.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/jquery-ui.css?staticTime=${staticTime}">
<link rel="stylesheet" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/chosen.min.css?staticTime=${staticTime}">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet" id="myStyleSheet">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/timesheet.css?staticTime=${staticTime}" rel="stylesheet">


<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/jquery-1.11.1.min.js?staticTime=${staticTime}"></script>
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
<script src="https://maps.google.com/maps/api/js?libraries=places&v=3&key=${googleMapsLocationApiKey}" type="text/javascript"></script>

<script type="text/javascript">
		window.onload = function () {
			initLocationAuto('onBoardLocatin','onBoardCountry','onBoardState','onBoardCity','onBoardZipCode')
			getOnBoardCandidateCountry('${SourceLeadWeb_country}');
			getOnBoardCandidateStates('${SourceLeadWeb_country}','${SourceLeadWeb_state}');
			getOnBoardCandidateCities('${SourceLeadWeb_country}','${SourceLeadWeb_state}','${SourceLeadWeb_city}');
		 };
</script>

<script type="text/javascript">
/*  var sourceleadUrl ='${sourceleadUrl}'; */
 var atsUrl = '${SourceLeadWeb_atsUrl}'; 
 var pmsUrl = '${SourceLeadWeb_pmsUrl}';
</script>

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
				                     <img onerror='predefineOrgImage(this);' data-id="orgLogoImage" src="${links.orgLocations.org.fileUrl}">
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
				
				<c:if test="${!empty SourceLeadWeb_fileUrl}">
					<div class="welLogo">
					<img class="logo" data-id="orgLogoImage" src="${SourceLeadWeb_fileUrl}" title="${SourceLeadWeb_orgLocationName}" />
					</div>
				</c:if>
				
				<c:if test="${empty SourceLeadWeb_fileUrl}">
					<div class="welLogoText">
					<span  title="${SourceLeadWeb_orgLocationName}">${SourceLeadWeb_orgLocationName}</span>
					</div>
				</c:if>
				
				<div class="welSiO" onclick="logoutFormSubmit();" title="Sign Out"><i class="fa fa-sign-out"></i></div>
			  <ul>
				  <li class="actLi"><span class="fill">1</span><b>Welcome</b><i class="line"></i></li>
				  <li class="actLi"><span class="fill">2</span><b>Step two</b><i class="line"></i></li>
				  <li class="actLi"><span class="fill">3</span><b>Step three</b><i class="line"></i></li>
				  <li class="actLi"><span>4</span><b>Step four</b><i class="line"></i></li>
				  <li><span>5</span><b>Step five</b><i class="line"></i></li>
				  <li><span>6</span><b>Finish</b><i class="line"></i></li>
				  <div class="clearfix"></div>
			  </ul>
			  <div class="clearfix"></div>
		  </div>
		  
 <form:form class="login-form clearfix"  id="onboardCandidateTax_form"  name="onboardCandidateTax_form" method="post" >
 <div id="loading"></div>
<div class="welCo1">
<h5>Federal Tax</h5>
<div class="form-horizontal padBot">
<input type="hidden" id="id" name="id" value="${taxDeatails.id}">
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
			<div class="form-group">
			    <label class="col-md-4 control-label">Filling Status<span class="text-danger paddingLeft">*</span></label>
			     <div class="col-md-8">
					<select class="sel" id="federaltaxFillingStatus" name="federaltaxFillingStatus" data-validation="required" field-name="Filling Status">
                        <option value="">--Select--</option>
                        <option value="Single"  ${taxDeatails.federaltaxFillingStatus == 'Single' ? 'selected' : ''}>Single</option>
                        <option value="Married" ${taxDeatails.federaltaxFillingStatus == 'Married' ? 'selected' : ''}>Married</option>
                        <option value="Married_Single_Income" ${taxDeatails.federaltaxFillingStatus == 'Married_Single_Income' ? 'selected' : ''}>Married Single Income</option>
                        <option value="Married_Use_Single_Rate" ${taxDeatails.federaltaxFillingStatus == 'Married_Use_Single_Rate' ? 'selected' : ''}>Married Use Single Rate</option>
                        <option value="NonResident_Alien" ${taxDeatails.federaltaxFillingStatus == 'NonResident_Alien' ? 'selected' : ''}>Non Resident Alien</option>
                      </select>			 
                 </div>
			</div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">Allowances<span class="text-danger paddingLeft">*</span></label>
			  <div class="col-md-8">
                   <input type="text" class="form-control" name="federaltaxAllowances" value="${taxDeatails.federaltaxAllowances}" id="federaltaxAllowances" data-validation="required validate_Space validate_length length1-100" field-name="Allowances">
			  </div>
			</div>
		</div>
	</div>
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
			<div class="form-group">
			  <label class="col-md-4 control-label">Additional with holding<span class="text-danger paddingLeft">*</span></label>
			  <div class="col-md-8">
					 <input type="text" class="form-control" name="federaltaxAdditionalWithHolding" value="${taxDeatails.federaltaxAdditionalWithHolding}" id="federaltaxAdditionalWithHolding" data-validation="required validate_Space validate_length length1-100" field-name="Additional with holding">
                 </div>
			</div>
		</div>
	</div>
</div>
 
 <%-- <h5>State Tax</h5>
 <div class="form-horizontal">
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
			<div class="form-group">
			  <label class="col-md-4 control-label">Filling Status<span class="text-danger paddingLeft">*</span></label>
			  <div class="col-md-8">
					<select class="sel" name="stateTaxFillingStatus" id="stateTaxFillingStatus" data-validation="required" field-name="State Tax"> 
                        <option value="">--Select--</option>
                        <option value="Single" ${taxDeatails.stateTaxFillingStatus == 'Single' ? 'selected' : ''}>Single</option>
                        <option value="Married" ${taxDeatails.stateTaxFillingStatus == 'Married' ? 'selected' : ''}>Married</option>
                        <option value="Married_Single_Income" ${taxDeatails.stateTaxFillingStatus == 'Married_Single_Income' ? 'selected' : ''} >Married Single Income</option>
                        <option value="Married_Dual_Income" ${taxDeatails.stateTaxFillingStatus == 'Married_Dual_Income' ? 'selected' : ''} >Married Dual Income</option>
                        <option value="Head_of_Household" ${taxDeatails.stateTaxFillingStatus == 'Head_of_Household' ? 'selected' : ''}>Head of Household</option>
                      </select>		
                 </div>
			</div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">Allowances<span class="text-danger paddingLeft">*</span></label>
			  <div class="col-md-8">
                 <input type="text" class="form-control" name="stateTaxAllowances" id="stateTaxAllowances" value="${taxDeatails.stateTaxAllowances}" data-validation="required validate_Space validate_length length1-100" field-name="Allowances">
			  </div>
			</div>
		</div>
	</div>
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
			<div class="form-group">
                  <label class="col-md-4 control-label">Additional with holding<span class="text-danger paddingLeft">*</span></label>
                  <div class="col-md-8">
                      <input type="text" class="form-control" name="stateTaxAdditionalWithHolding" id="stateTaxAdditionalWithHolding" value="${taxDeatails.stateTaxAdditionalWithHolding}" data-validation="required validate_Space validate_length length1-100" field-name="Additional with holding">
                  </div>
                </div>
			</div>
	</div>
</div> --%>

<h5>Residence Location</h5>
 <div class="form-horizontal">
  <div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
			<div class="form-group">
			  <label class="col-md-4 control-label">Filling Status<span class="text-danger paddingLeft">*</span></label>
			  <div class="col-md-8">
					<select class="sel" name="residenceLocationFillingStatus" id="residenceLocationFillingStatus" data-validation="required" field-name="Filling Status"> 
                        <option value="">--Select--</option>
                        <option value="Single" ${taxDeatails.residenceLocationFillingStatus == 'Single' ? 'selected' : ''}>Single</option>
                        <option value="Married" ${taxDeatails.residenceLocationFillingStatus == 'Married' ? 'selected' : ''}>Married</option>
                        <option value="Married_Single_Income" ${taxDeatails.residenceLocationFillingStatus == 'Married_Single_Income' ? 'selected' : ''}>Married Single Income</option>
                        <option value="Married_Dual_Income" ${taxDeatails.residenceLocationFillingStatus == 'Married_Dual_Income' ? 'selected' : ''}>Married Dual Income</option>
                        <option value="Head_of_Household" ${taxDeatails.residenceLocationFillingStatus == 'Head_of_Household' ? 'selected' : ''}>Head of Household</option>
                      </select>	
                 </div>
			</div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">Allowances<span class="text-danger paddingLeft">*</span></label>
			  <div class="col-md-8">
                      <input type="text" class="form-control" name="residenceLocationAllowances" value="${taxDeatails.residenceLocationAllowances}" id="residenceLocationAllowances" data-validation="required validate_Space validate_length length1-100" field-name="Allowances">
			  </div>
			</div>
		</div>
	</div>
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
		     <div class="form-group">
                 <label class="col-md-4 control-label">Additional with holding<span class="text-danger paddingLeft">*</span></label>
                 <div class="col-md-8">
                     <input type="text" class="form-control" name="residenceLocationAdditionalWithHolding" value="${taxDeatails.residenceLocationAdditionalWithHolding}"  id="residenceLocationAdditionalWithHolding" data-validation="required validate_Space validate_length length1-100" field-name="Additional with holding">
                 </div>
               </div>
	    </div>
	    <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
		     <div class="form-group">
                 <label class="col-md-4 control-label">Supplement Type<span class="text-danger paddingLeft">*</span></label>
                 <div class="col-md-8">
					<select class="sel" id="residenceLocationSupplementType" name="residenceLocationSupplementType" data-validation="required" field-name="Supplement Type"> 
                        <option value="">--Select--</option>
                        <option value="BONUS" ${taxDeatails.residenceLocationSupplementType == 'BONUS' ? 'selected' : ''}>Bonus</option>
                        <option value="COMMISSION" ${taxDeatails.residenceLocationSupplementType == 'COMMISSION' ? 'selected' : ''}>Commission</option>
                        <option value="NONE" ${taxDeatails.residenceLocationSupplementType == 'NONE' ? 'selected' : ''}>None</option>
                      </select>            
                     </div>
               </div>
	    </div>
	</div>
 
     <div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
 		    <div class="form-group">
                <label class="col-md-4 control-label">Local tax</label>
                <div class="col-md-8">
					<select class="sel" id="localTax" name="localTax" data-validation="required" field-name="State Tax" onchange="isLocalTaxYes();"> 
                        <option value="">--Select--</option>
                        <option selected value="NO_TAX">No Tax</option>
<%--                         <option value='${location}'>${location}</option> --%>
                      </select>    
                  </div>
            </div>
 		</div>
 	</div> 
 </div>

<h5>Additional</h5>
 <div class="form-horizontal">
 <div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
			<div class="form-group">
			  <label class="col-md-4 control-label">Federal ${taxDeatails.federalExempt}</label>
			  <div class="col-md-4">
			  	   <c:if test="${taxDeatails.federalExempt eq 'YES'}">
			  	      <label class="radioIput mr"><input type="radio" name="federalExempt" id="federalExempt" checked value="true">Exempt</label>
			  		  <label class="radioIput"><input type="radio" name="federalExempt" id="federalExempt" value="false">Notexempt</label>
			  		</c:if>
			  		<c:if test="${taxDeatails.federalExempt eq 'NO'}">
			  	      <label class="radioIput mr"><input type="radio" name="federalExempt" id="federalExempt"  value="true">Exempt</label>
			  		  <label class="radioIput"><input type="radio" name="federalExempt" id="federalExempt" checked value="false">Notexempt</label>
			  		</c:if>
			  	   <c:if test="${empty taxDeatails.federalExempt}">
			  	    <label class="radioIput mr"><input type="radio" name="federalExempt" id="federalExempt"  value="true">Exempt</label>
			  		<label class="radioIput"><input type="radio" name="federalExempt" id="federalExempt" value="false">Notexempt</label>
			  		</c:if>
                 </div>
			</div>
		</div>
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">               
			<div class="form-group">
			  <label class="col-md-4 control-label">Mediacare</label>
			  <div class="col-md-4">
				  <c:if test="${taxDeatails.medicareExempt eq 'YES'}">
		               <label class="radioIput mr"><input type="radio" name="medicareExempt" id="medicareExempt" checked value="true">Exempt</label>
                       <label class="radioIput"> <input type="radio" name="medicareExempt" id="medicareExempt" value="false">Notexempt</label>
				  </c:if>
				   <c:if test="${taxDeatails.medicareExempt eq 'NO'}">
		               <label class="radioIput mr"><input type="radio" name="medicareExempt" id="medicareExempt" value="true">Exempt</label>
                       <label class="radioIput"> <input type="radio" name="medicareExempt" id="medicareExempt" checked value="false">Notexempt</label>
				  </c:if>
				   <c:if test="${empty taxDeatails.medicareExempt}">
		               <label class="radioIput mr"><input type="radio" name="medicareExempt" id="medicareExempt" value="true">Exempt</label>
                       <label class="radioIput"> <input type="radio" name="medicareExempt" id="medicareExempt" value="false">Notexempt</label>
				  </c:if>

                 </div>
			</div>
		</div>
	</div>
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
		     <div class="form-group">
                 <label class="col-md-4 control-label">Socail Security</label>
                 <div class="col-md-4">
	                 <c:if test="${taxDeatails.socailSecurityExempt eq 'YES'}">
	                         <label class="radioIput mr"><input type="radio" name="socailSecurityExempt" id="socailSecurityExempt" checked value="true">Exempt</label>
                             <label class="radioIput"><input type="radio" name="socailSecurityExempt" id="socailSecurityExempt" value="false">Notexempt</label>                 
	                 </c:if>
	                 <c:if test="${taxDeatails.socailSecurityExempt eq 'NO'}">
	                     <label class="radioIput mr"><input type="radio" name="socailSecurityExempt" id="socailSecurityExempt" value="true">Exempt</label>
	                      <label class="radioIput"><input type="radio" name="socailSecurityExempt" id="socailSecurityExempt" checked value="false">Notexempt</label>                 
                     </c:if>
                     <c:if test="${empty taxDeatails.socailSecurityExempt}">
                           <label class="radioIput mr"><input type="radio" name="socailSecurityExempt" id="socailSecurityExempt" value="true">Exempt</label>
	                      <label class="radioIput"><input type="radio" name="socailSecurityExempt" id="socailSecurityExempt" value="false">Notexempt</label>                 
                    
                     </c:if>
                   </div>
               </div>
	    </div>
	    <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
		     <div class="form-group">
                 <label class="col-md-4 control-label">FUTA</label>
                 <div class="col-md-4">
	                 <c:if test="${taxDeatails.futaExempt eq 'YES'}">
		                  <label class="radioIput mr"><input type="radio" name="futaExempt" id="futaExempt" checked value="true">Exempt</label>
	                      <label class="radioIput"><input type="radio" name="futaExempt" id="futaExempt" value="false">Notexempt</label>          
	                 </c:if>
	                  <c:if test="${taxDeatails.futaExempt eq 'NO'}">
		                  <label class="radioIput mr"><input type="radio" name="futaExempt" id="futaExempt" value="true">Exempt</label>
	                      <label class="radioIput"><input type="radio" name="futaExempt" id="futaExempt" checked value="false">Notexempt</label>          
	                 </c:if>
	                  <c:if test="${empty taxDeatails.futaExempt}">
		                  <label class="radioIput mr"><input type="radio" name="futaExempt" id="futaExempt" value="true">Exempt</label>
	                      <label class="radioIput"><input type="radio" name="futaExempt" id="futaExempt" value="false">Notexempt</label>          
	                 </c:if>
	                 
					
                     </div>
               </div>
	    </div>
	</div>
	
	<div class="clearfix">
 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
 		<div class="form-group">
                 <label class="col-md-4 control-label">SUTA</label>
                 <div class="col-md-4">
	                  <c:if test="${taxDeatails.sutaExempt eq 'YES'}">
		                  <label class="radioIput mr"><input type="radio" name="sutaExempt" id="sutaExempt" checked value="true">Exempt</label>
	                      <label class="radioIput"><input type="radio" name="sutaExempt" id="sutaExempt" value="false">Notexempt</label>
	                  </c:if>
	                  <c:if test="${taxDeatails.sutaExempt eq 'NO'}">
		                  <label class="radioIput mr"><input type="radio" name="sutaExempt" id="sutaExempt" value="true">Exempt</label>
	                      <label class="radioIput"><input type="radio" name="sutaExempt" id="sutaExempt" checked value="false">Notexempt</label>
	                  </c:if>
	                  <c:if test="${empty taxDeatails.sutaExempt}">
		                  <label class="radioIput mr"><input type="radio" name="sutaExempt" id="sutaExempt" value="true">Exempt</label>
	                      <label class="radioIput"><input type="radio" name="sutaExempt" id="sutaExempt" value="false">Notexempt</label>
	                  </c:if>
					           
                     </div>
               </div>
 		</div>
    </div>
 		
	
 </div>



</div>

		  
<div class="welBtns">
	<input type="button"  value="Previous" onClick="javascript:window.location.href = '${SourceLeadWeb_atsUrl}/onboardingCandidateBankingDetails'" >
	<input type="button"  value="Proceed to next step" id="save_button"  onclick="saveOnboardingCandidateTaxDetails();" class="Brig">
	<div class="clearfix"></div>
</div>
</form:form>
		  
		</section>
	</section>  
  </div>
  <!--   Welcome Screens   -->

  <!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
  
  				<div class="location-alert alert alert-danger alert-dismissable zInPop" id="locationAlert">
    <a href="#" class="loc-alertclose close">&times;</a>
    <strong>Alert !</strong> <span>Your are not able to Save Required data is Missing.</span>
  </div>
  		  
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
  <script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/jquery.dataTables.min.js?staticTime=${staticTime}"></script> 
  <script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/moment.min.js?staticTime=${staticTime}"></script> 
  <script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/jquery-ui.min.js?staticTime=${staticTime}"></script> 
  <script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/bootstrap-select.min.js?staticTime=${staticTime}"></script> 
  <script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/bootstrap.min.js?staticTime=${staticTime}"></script> 
  <script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/masterHeaderAction.js?staticTime=${staticTime}"></script> 
  <script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/chosen.jquery.min.js?staticTime=${staticTime}"></script> 
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
			  $(".loc-alertclose").click(function(){
					 $(".location-alert").fadeOut();
				})		 
</script> 

<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/formvalidator.js"></script>


 
<script>
 function saveOnboardingCandidateTaxDetails(){
	 var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
	 
	 if ($('#onboardCandidateTax_form').validate(false, validationSettings)){ 
		 var userId = '${userId}';
	        var formData = getFormData("onboardCandidateTax_form");
	        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	         $.ajax({
		    	type: "POST",
		    	data: formData,
		    	 url: pmsUrl+"/saveAssociateTax?${_csrf.parameterName}=${_csrf.token}&userId="+userId,
		        success: function(response) {
		        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
      	    		    showError(response.responseMessage);
      	    		    $("#loading").removeAttr('style');
      	    		}
		        	else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		location.replace(atsUrl+"/onboardingCandidateStep3");  
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    });  
	        return true;
	  } else {
		return false;
	  }
 }
 
 $('body').on('blur', '#onboardCandidateTax_form', function() {
		$("#onboardCandidateTax_form").showHelpOnFocus();
		$("#onboardCandidateTax_form").validateOnBlur(false, validationSettings);  
	});
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
		function logoutFormSubmit() {
			document.getElementById("logoutForm").submit();
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
		$(imageId).attr('src','/resources/images/office-image.jpg');
	}
</script>

<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/slead-common.js?staticTime=${staticTime}"></script>
