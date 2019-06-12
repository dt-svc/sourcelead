<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>

<link rel="icon" type="image/png" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/fav.png" />
<title>Welcome to Source Lead :: The Recruitment Application</title>
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">


<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/bootstrap-select.css?staticTime=${staticTime}" rel="stylesheet">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/jquery.dataTables.min.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/jquery-ui.css?staticTime=${staticTime}">
<link rel="stylesheet" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/chosen.min.css?staticTime=${staticTime}">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet" id="myStyleSheet">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/timesheet.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/bootstrap-select.min.css?staticTime=${staticTime}">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet" type="text/css">
<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/login.css?staticTime=${staticTime}" rel="stylesheet">
<script src="https://maps.google.com/maps/api/js?libraries=places&v=3&key=${googleMapsLocationApiKey}" type="text/javascript"></script>


<script type="text/javascript">
window.onload = function () {
	editOrgLocationAuto('orgEditState','orgEditCity','orgEditZipCode','address');
	getBusinessTypeValue();
 };
 var tokenParamName = "${_csrf.parameterName}";
 var tokenValue = "${_csrf.token}";
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
  <!--   Welcome Screens   -->
  
	<section class="comLogBg">
		<section class="comLogInn">
		  <div class="comLogHead">
	
	     <jsp:include page="../userManageAccounts.jsp" />
				
			<div class="comLogLogoText">${SourceLeadWeb_orgName}</div>
			  <div class="welSiO onboard-logoutbtn" onclick="logoutFormSubmit();" title="Sign Out"><i class="fa fa-sign-out mt5"></i></div>
			  <ul>
				  <li class="hoverLi">Organization details</li>
				  <li>Locations</li>
				  <c:if test="${SourceLeadWeb_orgLocations.country == 'India'}">
				  <li>Information</li>
				  </c:if>
				  <c:if test="${SourceLeadWeb_orgLocations.country == 'United States'}">
				  <li>Federal & state taxes</li>
				  </c:if>
				  <li>Banking details</li>
				  <li>Documents</li>
				  <li>Signatory</li>
				  <div class="clearfix"></div>
			  </ul>
			  <div class="clearfix"></div>
		  </div>
	
	 	  
	<div class="comLogContent">
        <h2>Organization Details</h2>
         <div class="row clearfix"> 
          <div id="loading"></div>
          
          <div class="col-xs-12 col-sm-3 col-md-3">
          <form:form class="login-form clearfix"  id="orgImage1_form"  name="orgImage1_form" method="post" >
				 <div class="well well-sm text-center">
				  <div class="profile-img-container" style="position:relative;">
				   <c:if test="${!empty SourceLeadWeb_org.fileContentType}">
				   <img id="orgImage" data-id="orgLogoImage" src="${SourceLeadWeb_org.fileUrl}" onerror="predefineOrgImage(this);" alt="Org Image" class="comp-image img-responsive">
				   </c:if>
				   <c:if test="${empty SourceLeadWeb_org.fileContentType}">
				   <img id="orgImage" data-id="orgLogoImage" onerror="predefineOrgImage(this);" src="<c:url value='/resources/images/office-image.jpg'/>" alt="Org Image" class="comp-image img-responsive">
				   </c:if>
				  <div class="btn-group uploadfile-onboard">
					  <button type="button" class="btn btn-primary">
					  <i class="fa fa-upload"></i></button>
					   <label class="btn btn-sm btn-primary" style="padding:4px 5px;">
	                    Upload / Change<input type="file" name="file" id="uploadedImage"  accept="image/*" onchange="cropImage(this, 'orgImage1_form', '', true);" style="visibility:hidden;height:0;width:10px;overflow:hidden" >
	                  </label>
	                  <c:if test="${!empty SourceLeadWeb_org.fileContentType}">
	                   <button type="button" class="btn btn-danger" onclick="deleteOnboardImage()">
				  	 	<i class="fa fa-trash"></i></button>
	                  </c:if>
	                 
				  </div> 
				  </div>
				  </div>
				  <p class="text-danger small" style="font-size: 11px; display: none;" id="errorImage" >Invalid Image/Size</p>
				  <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;png, svg and gif images are not allowed</p>
				  <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;Image size should not more than 200 KB</p>
			  </form:form>
			   </div>
 <div id="popUp" ></div>
<div class="col-md-9 noPad">
<form:form class="login-form clearfix"  id="orgSettings_form"  name="orgSettings_form" method="post" > 
	<div class="col-md-6 clearfix mb">
		<label class="col-md-3 noPad">Company<span class="text-danger">&nbsp;*</span></label>
		<div class="col-md-9 padding_right" id="companyNameDiv">
		<input type="text" id="orgLocationCompanyName" name="companyName" class="form-control"  field-name="Company" data-validation="required validate_Space validate_length length2-250" onblur="validateCompanyName();"  value="${SourceLeadWeb_orgName}" />
		</div>
		</div>
		
		
        <div class="col-md-6 clearfix mb">
            <label class="col-md-3 noPad">DBA<span class="text-danger">&nbsp;*</span></label>
            <div class="col-md-9 padding_right clearfix">
                <div class="col-md-10 noPad">
                    <input id="dba" name="dba" class="form-control" type="text" value="${SourceLeadWeb_orgLocations.dba}" field-name="DBA" data-validation="required validate_Space validate_length length1-250">
                </div>
                <div class="col-md-2 dbaCheck dbaCheck-cs">
                     
                   <div class="mrleft8 mr mt5">
		              			<input id="dbaCheckbox" name="dbaCheckbox"  class="checkbox-custom" onclick="getDBAName();"  type="checkbox">
		              			<label for="dbaCheckbox" class="checkbox-custom-label" title="Make company name as dba"></label>
          			</div>
                </div>
            </div>
        </div>		

		<div class="col-md-6 clearfix mb">
			<label class="col-md-3 noPad">Country<span class="text-danger">&nbsp;*</span></label>
			<div class="col-md-9 padding_right">
			 <input type="text" class=" form-control" name="country" value="${SourceLeadWeb_country}" id="orgEditCountry" field-name="Country" data-validation="required" readonly="readonly" >
			</div>
		</div>

		<div class="col-md-6 clearfix mb">
			<label class="col-md-3 noPad">City<span class="text-danger">&nbsp;*</span></label>
			<div class="col-md-9 padding_right">
			<input type="text" class=" form-control" name="city" value="${SourceLeadWeb_orgLocations.city}" onClick="addOrgRefreshGoogleApiLocation();" id="orgEditCity" field-name="City" data-validation="required">
								 <i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshOrgEditGoogleApiLocation('orgEditState','orgEditCity','orgEditZipCode');"></i>
			</div>
		</div>
		<div class="col-md-6 clearfix mb">
			<label class="col-md-3 noPad">State<span class="text-danger">&nbsp;*</span></label>
			<div class="col-md-9 padding_right">
			 <input type="text" class=" form-control" name="state" value="${SourceLeadWeb_orgLocations.state}" id="orgEditState" field-name="State" data-validation="required" >
			</div>
		</div>
		

		<div class="col-md-6 clearfix mb">
			<label class="col-md-3 noPad">ZipCode</label>
			<div class="col-md-9 padding_right">
			<input type="text"  class="form-control" name="zipCode" id="orgEditZipCode" field-name="Zip" data-validation="validate_Space validate_length length1-6 validate_int"  data-validation-optional="true" value="${SourceLeadWeb_orgLocations.zipCode}" />
			</div>
		</div>

		<div class="col-md-6 clearfix mb">
		<label class="col-md-3 noPad">Address</label>
		<div class="col-md-9 padding_right">
		<input type="text" id="address" name="address" class="form-control"  field-name="Address" data-validation="validate_Space" data-validation-optional="true" value="${SourceLeadWeb_address}" />
		</div>
		</div>

		<div class="col-md-6 clearfix mb">
		<label class="col-md-3 noPad">Time Zone<span class="text-danger">&nbsp;*</span></label>
			<div class="col-md-9 padding_right">
			 <select class="chosen-select form-control" data-placeholder="Time Zone" field-name="Timezone" data-validation="required" id="timeZone" name="timeZone">
				 <option value="" >Select Timezone</option>
				 <c:forEach items="${timezoneList}" var="timezone">
	                         <c:choose>
	                             <c:when test="${timezone.timeZoneId eq SourceLeadWeb_orgLocations.timezone.timeZoneId}">
	                                 <option value= "${timezone.id}" selected="selected">${timezone.timeZoneId}</option>
	                             </c:when>
	                             <c:otherwise>
	                                 <option value="${timezone.id}">${timezone.timeZoneId}</option>
	                             </c:otherwise>
	                         </c:choose>
	                      </c:forEach>
	                  </select>
            <span class="hidden">Time Zone</span>
			</div>
		</div>

		<div class="col-md-6 clearfix mb">
		<label class="col-md-3 noPad">Date Format</label>
			<div class="col-md-9 padding_right">
			 <select class="chosen-select form-control" id="dateFormat1" name="dateFormat1">
				 <option ${SourceLeadWeb_orgLocations.dateFormat  == 'dd-MM-yyyy' ? 'selected' : ''} value="dd-MM-yyyy">dd-MM-yyyy</option>
				 <option ${SourceLeadWeb_orgLocations.dateFormat  == 'MM-dd-yyyy' ? 'selected' : ''} value="MM-dd-yyyy">MM-dd-yyyy</option>
				 <option ${SourceLeadWeb_orgLocations.dateFormat  == 'yyyy-dd-MM' ? 'selected' : ''} value="yyyy-dd-MM">yyyy-dd-MM</option>
				 <option ${SourceLeadWeb_orgLocations.dateFormat  == 'yyyy-MM-dd' ? 'selected' : ''} value="yyyy-MM-dd">yyyy-MM-dd</option>
			 </select>
			</div>
		</div>
		
		<div class="col-md-6 clearfix mb">
			<label class="col-md-3 noPad">Website<span class="text-danger">&nbsp;*</span></label>
			<div class="col-md-9 padding_right" id="websiteDiv">
				<c:if test="${SourceLeadWeb_website == null}">
				   <input type="text"  class="form-control"  onblur="validateOrgLocationWebsite();" data-validation="required" field-name="Website" id="website"  name="website" value="${SourceLeadWeb_website}">
				</c:if>
				
				<c:if test="${SourceLeadWeb_website != null}">
				   <input type="text" readonly="readonly" class="form-control" name="website" value="${SourceLeadWeb_website}">
				</c:if>
							
			</div>
		</div>
		<div class="col-md-6 clearfix mb">
			<label class="col-md-3 noPad">Phone</label>
			<div class="col-md-9 padding_right">
			<input type="text"  class="form-control" name="phone" field-name="phone" data-validation="validate_phone validate_Space validate_length length9-20" data-validation-optional="true" value="${SourceLeadWeb_orgLocations.phone}" />
			</div>
		</div>
		
		<div class="col-md-6 clearfix mb">
			<label class="col-md-3 noPad">Fax</label>
			<div class="col-md-9 padding_right">
			<input type="text"  class="form-control" name="fax" id="faxId" field-name="fax" data-validation="validate_phone validate_Space validate_length length9-20" data-validation-optional="true" value="${SourceLeadWeb_orgLocations.fax}" />
			</div>
		</div>
		
		<div class="col-md-6 clearfix mb">
			<label class="col-md-3 noPad">Org Short Code<span class="text-danger">&nbsp;*</span></label>
			<div class="col-md-9 padding_right">
			
			<div id="generatedUserIdDiv">
			<input type="text"  class="form-control" name="generatedUserId" id="generatedUserId" field-name="Org Short Code" style="text-transform:uppercase" data-validation="required validate_length length1-4"  onkeyup="validateOrgShortCode();" maxlength="4" value="${SourceLeadWeb_orgLocations.generatedUserId}" />
			</div>
			
			</div>
			
		</div>
		
		<div class="col-md-6 clearfix mb">
		<label class="col-md-3 noPad">Week Off Days</label>
			<div class="col-md-9 padding_right">
			 <select multiple class="chosen-select form-control" id="weekOffDays" name="weekOffDays" field-name="Week Off Days" data-validation="required">
					 <option  value='Sun' ${fn:contains(SourceLeadWeb_weekOffDaysList, 'Sun') ? 'selected' : ''}>Sunday</option>
					 <option  value='Mon' ${fn:contains(SourceLeadWeb_weekOffDaysList, 'Mon') ? 'selected' : ''}>Monday</option>
					 <option  value='Tue' ${fn:contains(SourceLeadWeb_weekOffDaysList, 'Tue') ? 'selected' : ''}>Tuesday</option>
					 <option  value='Wed' ${fn:contains(SourceLeadWeb_weekOffDaysList, 'Wed') ? 'selected' : ''}>Wednesday</option>
					 <option  value='Thu' ${fn:contains(SourceLeadWeb_weekOffDaysList, 'Thu') ? 'selected' : ''}>Thursday</option>
					 <option  value='Fri' ${fn:contains(SourceLeadWeb_weekOffDaysList, 'Fri') ? 'selected' : ''}>Friday</option>
					 <option  value='Sat' ${fn:contains(SourceLeadWeb_weekOffDaysList, 'Sat') ? 'selected' : ''}>Saturday</option>
			 </select>
			</div>
		</div>
		
		<div class="col-md-6 clearfix mb" id="averageWorkingHoursDiv">
			<label class="col-md-3 noPad">Working Hours<span class="text-danger">&nbsp;*</span></label>
			<div class="col-md-9 padding_right">
			<div id="averageWorkingHoursDiv">
			<input type="text" class="form-control" maxlength="2" name="averageWorkingHours" id="averageWorkingHours" data-validation="required" field-name="Average Working Hours" value="${SourceLeadWeb_orgLocations.averageWorkingHours}"/>
			</div>
			</div>
		</div>
		
		<div class="col-md-6 clearfix mb">
				<label class="col-md-3 noPad"></label>
				<div class="col-md-9">
					<div class="checkBox">
						<label><input type="checkbox" value="Y" ${SourceLeadWeb_orgLocations.isTimesheetAvgHoursRequired == 'Y' ? 'checked' : ''} name="isTimesheetAvgHoursRequired" id="isTimesheetAvgHoursRequired"> Average Hours Validation Is Required</label> 
					</div>
				</div>
	    </div>
		
		<div class="col-md-6 clearfix mb">
		<label class="col-md-3 noPad">Business type<span class="text-danger">&nbsp;*</span></label>
			<div class="col-md-9 padding_right">
			<select class="chosen-select" id="businessType" name="businessType" data-validation="required" onchange="getBusinessTypeValue();"  field-Name="Business Type">
				<option value="">--Select Business type--</option>
				<c:forEach var="bt" items="${bussinessTypesList}">
			      <c:choose>
	                      <c:when test="${bt.id eq SourceLeadWeb_orgLocations.businessType.id}">
	                          <option value= "${bt.id}" selected="selected">${bt.name}</option>
	                      </c:when>
	                      <c:otherwise>
	                         <option value="${bt.id}">${bt.name}</option>
	                      </c:otherwise>
	              </c:choose>
                </c:forEach>  
                
                <c:if test="${empty SourceLeadWeb_orgLocations.businessType.id && !empty SourceLeadWeb_orgLocations.otherBusinessType}">
					<option value="Other" selected="selected">Other</option>
				</c:if>
				 <c:if test="${empty SourceLeadWeb_orgLocations.otherBusinessType}">
					<option value="Other">Other</option>
				</c:if>
			  </select>
			</div>
		</div>	
		
		
		<div class="col-md-6 clearfix mb" id="otherBusinessTypeDiv" style="display: none;">
			<label class="col-md-3 noPad">Other Business Type</label>
			<div class="col-md-9 padding_right">
			<input type="text"  class="form-control" name="otherBusinessType" id="otherBusinessType" value="${SourceLeadWeb_orgLocations.otherBusinessType}" field-name="Other Business Type"  data-validation-optional="true" value="${SourceLeadWeb_orgLocations.zipCode}" />
			</div>
		</div>
		
		<div class="col-md-6 clearfix mb">
			<label class="col-md-3 noPad">Email regarding Po</label>
			<div class="col-md-9 padding_right">
			<input type="text"  class="form-control" name="poEmail" field-name="po Email" data-validation="validate_email validate_Space" data-validation-optional="true" value="${SourceLeadWeb_orgLocations.poEmail}" />
			</div>
		</div>
		
		<div class="col-md-6 clearfix mb">
			<label class="col-md-3 noPad">Email for Remittance</label>
			<div class="col-md-9 padding_right">
			<input type="text"  class="form-control" name="remittanceEmail" field-name="Remittance Email" data-validation="validate_email validate_Space" data-validation-optional="true" value="${SourceLeadWeb_orgLocations.remittanceEmail}" />
			</div>
		</div>
			
	  <div class="col-md-12 comp-textarea clearfix mb mt">
		  <label class="">About</label>
			<div class="col-md-12 noPad">
			   <textarea rows="5" name="aboutCompany" field-name="About" data-validation-optional="true" data-validation="validate_length length5-5000" class="form-control">${SourceLeadWeb_orgLocations.orgDescription}</textarea>
			</div>
		</div>
			<div class="clearfix"></div>
			<div class="col-md-12">	
		<div class="welBtns welBtnsA">
	<input type="submit" value="Save & Continue"  id="save_button" class="Brig">
	<div class="clearfix"></div>
	</div>
</div>
			</form:form>			
		</div>
		</div>
	</div>			  
		  


 </section>
</section> 
</div>

<div class="authenticationMask" >
  <div class="signWrapOtp">
  	<div id="directtwoStepAuthDiv">
  		<jsp:include page="../otpVerification.jsp"/>
  	</div>
  </div>
  </div>
  
  <!--  Alert message for Switching Locaction -->
 <div class="location-alert alert alert-danger alert-dismissable zInPop" id="locationAlert">
    <a href="#" class="loc-alertclose close">&times;</a>
    <strong>Alert !</strong> <span>Your are not able to Save Required data is Missing.</span>
  </div>
	
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
 <script type="text/javascript" src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/jquery-1.11.0.min.js?staticTime=${staticTime}"></script>
 <script type="text/javascript" src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/jquery.liquidcarousel.pack.js?staticTime=${staticTime}"></script>	
	
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
</script> 
 
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/formvalidator.js"></script>


  
 <!--  <script>
	$('.sopcSwith, .authenticationMask').each(function() {
		var $this = $(this);
		$this.on("contextmenu",function(e){
			  return false;
		});
		
		$(document).keydown(function (event) {
			if (event.keyCode == 123) { // Prevent F12
		        return false;
		    }else if (event.ctrlKey && event.shiftKey && event.keyCode == 73) { // Prevent Ctrl+Shift+I        
		        return false;
		    }
		});
		 
	})
  </script> -->

<script>
function getBusinessTypeValue(){
	var businessType = document.getElementById("businessType").value;
	if(businessType && businessType == 'Other'){
		 document.getElementById("otherBusinessTypeDiv").style.display="";
		 $("#otherBusinessType").removeAttr("data-validation-optional");
		 $("#otherBusinessType").attr("data-validation","required validate_Space validate_AlphaNumber validate_length length1-100");
	}else{
		    document.getElementById("otherBusinessTypeDiv").style.display="none";
			$("#otherBusinessType").removeAttr("data-validation-optional");
			 $("#otherBusinessType").attr("data-validation-optional","true");
			 $("#otherBusinessType").removeAttr("data-validation");
			 $("#otherBusinessType").attr("data-validation","required validate_Space validate_AlphaNumber validate_length length1-100");
		}
}
</script>

<script>
function validateOrgShortCode(){
	var sourceleadUrl ='${sourceleadUrl}';
	var orgShortId = $("#generatedUserId").val();
	$('#generatedUserIdDiv').parent().find('p.jquery_form_error_message').remove();
		  $.ajax({
		    	type: "POST",
		    	url : sourceleadUrl+"/validateOrgShortCode?${_csrf.parameterName}=${_csrf.token}&orgShortId="+orgShortId, 
		        success: function(data) {
		        	if(data == true){
		        		$('#generatedUserIdDiv').parent().find('p.jquery_form_error_message').remove();
		        		$("input#generatedUserId").parent().after("<p class='jquery_form_error_message'><span>Org Short Code Already Exists !!!</span>");
		        		$("#saveOrgConfiguration").attr("disabled","disabled");
		        		
		        	}else{
		        		 $('#generatedUserIdDiv').parent().find('p.jquery_form_error_message').remove();
		        		 $("#saveOrgConfiguration").removeAttr("disabled","disabled");
		        	}
		        }
		 }); 
}
$(function(){
	$("#generatedUserId").keypress(function (event) {
        return event.key.length > 1 || event.ctrlKey || !!event.key.match(/[a-zA-Z]/);
    });
});
$(function() {
	  $('#averageWorkingHoursDiv').on('keydown', '#averageWorkingHours', function(e){-1!==$.inArray(e.keyCode,[46,8,9,27,13,110,190])||/65|67|86|88/.test(e.keyCode)&&(!0===e.ctrlKey||!0===e.metaKey)||35<=e.keyCode&&40>=e.keyCode||(e.shiftKey||48>e.keyCode||57<e.keyCode)&&(96>e.keyCode||105<e.keyCode)&&e.preventDefault()});
});
$(function(){
    $("#averageWorkingHours").keyup(function(event){
        var ew = $("#averageWorkingHours").val();
        $('#averageWorkingHoursDiv').parent().find('p.jquery_form_error_message').remove(); 
        if(ew > 24){
        	$("#averageWorkingHours").val('');
        	$("input#averageWorkingHours").parent().after("<p class='jquery_form_error_message'><span>Average Working Hours should be less then or equal to 24</span>");
        }
    });
});

</script>


<script>
function getDBAName(){
	 if (document.getElementById('dbaCheckbox').checked) {
		 var companyName = document.getElementById("orgLocationCompanyName").value;
		 if(companyName != null && companyName != '' && companyName != 'undefined'){
			$("#dba").val(companyName); 
		 }
     }else{
    	 $("#dba").val('');
     } 
}
</script>


<script type="text/javascript">
  var loadFile = function(event) {
    var output = document.getElementById('orgImage');
    
    var uploadedImage = document.getElementById('uploadedImage').value;
    var sizeinbytes = document.getElementById('uploadedImage').files[0].size;
    document.getElementById("errorImage").style.display="none";
    var extension = uploadedImage.substring(uploadedImage.lastIndexOf(".")+1).toLowerCase();
    
    if(extension != 'jpg'){
    	output.src = URL.createObjectURL(event.target.files[0]);
    	var extensionName = uploadedImage.substring(uploadedImage.lastIndexOf(".")+1);
 		var msg = "."+extensionName+" extension not allowed";
 		alertMessage(msg);
 		document.getElementById("errorImage").style.display=""; 
 		$("#save_button").attr("disabled", "disabled");
    	
    }else if(sizeinbytes > 205126){
    	output.src = URL.createObjectURL(event.target.files[0]);
 		var msg = "Image size should be less than 200 KB";
 		alertMessage(msg);
 		document.getElementById("errorImage").style.display=""; 
 		$("#save_button").attr("disabled", "disabled");
    	
    }else{
    	output.src = URL.createObjectURL(event.target.files[0]);
    	$("#save_button").removeAttr("disabled")
    }
    
  };
</script>


<script type="text/javascript">
function alertMessage(message){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancelOrgImg();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelOrgImg();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}

</script>

<script type="text/javascript">
function deleteImage(){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure you want to delete this image ?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='deleteOrgImage();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelOrgImg();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script type="text/javascript">
function deleteOrgImage(){
	var sourceleadUrl ='${sourceleadUrl}';
	
	 $.ajax({
		    type: "GET",
            url: sourceleadUrl+"/deleteOrgImage",
	        success: function(data) {
	        	 var result = data;
     		if(result == "success"){
     			location.replace(sourceleadUrl+"/companyOnboard"); 
        	 }
	        
	       },
	        
     }); 
}
</script>

<script type="text/javascript">
var sourceleadUrl ='${sourceleadUrl}';
function cancelOrgImg(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<script>
function refreshOrgEditGoogleApiLocation(eidtStateId,editCityId,editZipCodeId,addressId){
	$("#orgEditState").val('');
	$("#orgEditCity").val('');
	$("#orgEditZipCode").val('');
}


</script>


<script type="text/javascript">
function validateCompanyName(){
	var con = document.getElementById("orgLocationCompanyName").value;
	var company = document.getElementById("orgLocationCompanyName").value;
	var companyName = encodeURIComponent(con);
	var sourceleadUrl ='${sourceleadUrl}';
	 if(companyName != null && companyName != ''){
		 $("#save_button").attr("disabled", "disabled");
		 $( "#save_button" ).addClass('button').val('Processing..');
			 $.ajax({
				    type: "GET",
       	            url: sourceleadUrl+"/validateCompanyName?companyName="+companyName,
			        success: function(data) {
			        	 var result = data;
			        		if(result == "Company Duplicated") {
		        			//Existed company
		    	    		$('#companyNameDiv').find('p.jquery_form_error_message').remove(); 
		    	    		$('input#companyName').attr('class','error form-control');
		    	    		$("input#companyName").closest('b').after("<p class='jquery_form_error_message'> "+company+" "+result+"</p>");
		    				$('input#companyName').attr('record-exist','yes');
		    				$('input#companyName').attr('record-exist-errorMsg',company+' '+result+''); 
		    				$( "#save_button" ).addClass('btn btn-primary mr').val('Save');
		    				$("#save_button").removeAttr("disabled");
			        	}
			        	else{
			        		//New company
			        		$("#save_button").removeAttr("disabled");
				    		$('input#companyName').removeAttr( "class record-exist record-exist-errormsg");
							$('#companyNameDiv').find('p.jquery_form_error_message').remove();
							$('input#companyName').attr('class','valid form-control');
							$( "#save_button" ).removeAttr("style");
							$( "#save_button" ).addClass('btn btn-primary mr').val('Save');
							$( "#save_button" ).click(function() {
							 if ($(this).validate(false, validationSettings)){
							 }
							});
			        	}  
			       },
			        
		   }); 
		 
	 }
}
</script>


<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#orgSettings_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		    e.preventDefault(); 
	          		var sourceleadUrl ='${sourceleadUrl}';
	          		
	           		var formData = $($('#orgSettings_form')[0]).serialize();
	           		var uploadedImage = document.getElementById('uploadedImage').value;
             		var imageExtension = uploadedImage.substring(uploadedImage.lastIndexOf(".")+1);
             		if(imageExtension == 'gif' || imageExtension == 'png' || imageExtension == 'svg'){
		        		document.getElementById("errorImage").style.display=""; 
             		}else{
             		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
             		$.ajax({
	              	      type: "POST",
	              	      data: formData,
	                    	  url: sourceleadUrl+"/saveOrgSettings?"+tokenParamName+"="+tokenValue+"",
	                      	success: function(result) {
	                      		if (result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
	            	    		    showError(result.responseMessage);
	            	    		    $("#loading").removeAttr('style');
	            	    		}
	                      		else if (result.response === "AWKWARD") {
	            	    		    location.href = "/errorPage";
	            	    		}
	                      		else if (result.response === "SUCCESS") {
	                      		location.replace(sourceleadUrl+"/companyOnboardLocations");	
	                      		}
	                      		else if (result.indexOf('Sign In') !== -1) {
	            					location.replace(sourceleadUrl);
	            				} else {
	            				    showError("Operation failed, please try again");
	            				    $("#loading").removeAttr('style');
	            				}
	  			        },  
	                  }); 
             		}
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         $('#orgSettings_form').showHelpOnFocus();
         $('#orgSettings_form').validateOnBlur(false, validationSettings); 
            
     
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


<script>
function editOrgLocationAuto(orgStateId,orgCityId,orgZipCodeId,addressId) {
	editOrgInitialize(orgStateId,orgCityId,orgZipCodeId,addressId);
}
function editOrgInitialize(orgStateId,orgCityId,orgZipCodeId,addressId) {
	var country = '${SourceLeadWeb_country}';
	if(country == "India"){
		var input = (document.getElementById(orgCityId));
	    var options = {
	        types: ['geocode'],
	        componentRestrictions: {
	        	country: 'IN'
	        }
	    };
	    autocomplete = new google.maps.places.Autocomplete(input, options);
	}else if(country == "United States"){
		var input = (document.getElementById(orgCityId));
	    var options = {
	        types: ['geocode'],
	        componentRestrictions: {
	        	country: 'US'
	        }
	    };
	    autocomplete = new google.maps.places.Autocomplete(input, options);
	}else if(country == "Australia"){
		var input = (document.getElementById(orgCityId));
	    var options = {
	        types: ['geocode'],
	        componentRestrictions: {
	        	country: 'AU'
	        }
	    };
	    autocomplete = new google.maps.places.Autocomplete(input, options);
	}else if(country == "France"){
		var input = (document.getElementById(orgCityId));
	    var options = {
	        types: ['geocode'],
	        componentRestrictions: {
	        	country: 'FR'
	        }
	    };
	    autocomplete = new google.maps.places.Autocomplete(input, options);
	}else if(country == "Singapore"){
		var input = (document.getElementById(orgCityId));
	    var options = {
	        types: ['geocode'],
	        componentRestrictions: {
	        	country: 'SG'
	        }
	    };
	    autocomplete = new google.maps.places.Autocomplete(input, options);
	}else if(country == "United kingdom"){
		var input = (document.getElementById(orgCityId));
	    var options = {
	        types: ['geocode'],
	        componentRestrictions: {
	        	country: 'GB'
	        }
	    };
	    autocomplete = new google.maps.places.Autocomplete(input, options);
	}
    	 
  google.maps.event.addListener(autocomplete, 'place_changed', function() {
  place = autocomplete.getPlace();
  var address = {};
  for (var i = 0; i < place.address_components.length; i++) {
    var addressType = place.address_components[i].types[0];
    switch (addressType) {
    case "route":
   	 address.name=place.address_components[i].long_name;
   	 break;
      case "locality":
        address.city = place.address_components[i].long_name;
        break;
      case "administrative_area_level_1":
        address.state = place.address_components[i].long_name;
        break;
      case "postal_code":
        address.zipcode = place.address_components[i].long_name;
        
    }
  }
  
  $("#"+orgStateId).val(address.state).change();
  $("#"+orgCityId).val(address.city).change();
  $("#"+orgZipCodeId).val(address.zipcode).change();
});
}
</script>
  
  
  <script type="text/javascript">
$(function(){
  $('.crsl-items').carousel({
    visible: 3,
    itemMinWidth: 180,
    itemEqualHeight: 370,
    itemMargin: 9,
	 loop: false,
  });
  
  $("a[href=#]").on('click', function(e) {
    e.preventDefault();
  });
});
</script>

	<script type="text/javascript" src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/jquery.liquidcarousel.pack.js?staticTime=${staticTime}"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#liquid1').liquidcarousel({height:129, duration:100, hidearrows:false});
		});
	
	</script>
	

<script type="text/javascript">
function deleteOnboardImage(){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure you want to delete this image ?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='deleteProfileImage();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script type="text/javascript">
function deleteProfileImage(){
	var sourceleadUrl ='${sourceleadUrl}';
	
	 $.ajax({
		    type: "GET",
            url: sourceleadUrl+"/deleteOrgImage",
	        success: function(data) {
	        	 var result = data;
     		if(result == "success"){
     			location.reload();
        	 }
	        
	       },
	        
     }); 
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
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/cropper.min.js?staticTime=${staticTime}"></script>
<link rel="stylesheet" href="/resources/css/cropper.min.csshref="${SourceLeadWeb_sourceleadDomainUrl}/resources" />
<script type="text/javascript" src='/resources/js/slead-common.js?staticTime=${staticTime}'></script>

<div id='imagecrop' class='post_popup'></div>