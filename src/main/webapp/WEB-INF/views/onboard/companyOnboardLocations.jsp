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
<script src="https://maps.google.com/maps/api/js?libraries=places&v=3&key=${googleMapsLocationApiKey}" type="text/javascript"></script>

 <script type="text/javascript" src='/resources/js/slead-common.js?staticTime=${staticTime}'></script>

<script type="text/javascript">
window.onload = function () {
	loadRegisteredOrgs();
	var isSuperAdmin = '${SourceLeadWeb_isSuperAdmin}';
	if(isSuperAdmin == 'Y'){
		document.getElementById("enrollNewOrgLocationDiv").style.display="";
	}
 };
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
						  <li class="hoverLi">Locations</li>
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
	   <div id="loading"></div> 
	         <div class="pull-left">
       		  <h2 class="clearfix locaPadding" id="locationOrgsName"></h2>
	        </div>
	        <div class="pull-right">
	        <button class="btn btn-default btm-sm" id="orgsList" data-toggle="tab" href="#orgRegisteredlocations" onclick="loadRegisteredOrgs();" style="display: none"><i class="fa fa-building"></i>
	                                         &nbsp;&nbsp;Locations List</button>
	       </div>
	       
	        <div class="pull-right" id="enrollNewOrgLocationDiv" style="display: none;">
	          <button class="btn btn-default btm-sm pull-right" onclick="createOrg();" id="enrollNewOrgLocation" ><i class="fa fa-map-marker"></i>&nbsp;Add Location</button>
	        </div>
	        
	        <div class="clearfix"></div>
	        <div class="clearfix compOnBorLoca mt10">
				<form:form class="login-form clearfix"  id="createOrg_form"  name="createOrg_form" method="post" >
               			<div id="registeredOrgs" class="clearfix"></div>
    			
    			<div class="welBtns welBtnsA">
					<input type="button" value="Prev" onClick="javascript:window.location.href = '<c:url value='/app/dashboard'/>'"><input type="submit" value="Save & Continue" class="Brig" id="register_button"  style="display: none;">
					<c:if test="${SourceLeadWeb_orgLocations.country == 'United States'}">
					<input type="button" value="Skip this step" class="skipBtn" onClick="javascript:window.location.href = '<c:url value='/app/companyOnboardFederalAndStateTaxes'/>'">
					</c:if>
					
					<c:if test="${SourceLeadWeb_orgLocations.country == 'India'}">
					<input type="button" value="Skip this step" class="skipBtn" onClick="javascript:window.location.href = '<c:url value='/app/companyOnboardInformationDetails'/>'"></a>
					</c:if>
					
					<c:if test="${SourceLeadWeb_orgLocations.country != 'India' && SourceLeadWeb_orgLocations.country != 'United States'}">
					<input type="button" value="Skip this step" class="skipBtn" onClick="javascript:window.location.href = '<c:url value='/app/companyOnboardBankingDetails'/>'"></a>
					</c:if>
					<div class="clearfix"></div>
				</div>
       			</form:form>
	        </div>
        </div>
			</section>
	</section>
	 <jsp:include page="../googleApiLocation.jsp" />
</div>
 <div class="location-alert alert alert-danger alert-dismissable zInPop" id="locationAlert">
    <a href="#" class="loc-alertclose close">&times;</a>
    <strong>Alert !</strong> <span>Your are not able to Save Required data is Missing.</span>
  </div>
	
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
 <script type="text/javascript" src="/resources/js/jquery-1.11.0.min.js?staticTime=${staticTime}"></script>
 <script type="text/javascript" src="/resources/js/jquery.liquidcarousel.pack.js?staticTime=${staticTime}"></script>	
	
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

<script src="/resources/js/formvalidator.js"></script>

<script>
function loadRegisteredOrgs(){
	document.getElementById("enrollNewOrgLocation").style.display="";
    document.getElementById("orgsList").style.display="none"; 
    document.getElementById("register_button").style.display="none"; 
     
     
     $("#locationOrgsName").html("Location List");
     $("#registeredOrgs").empty();
     /* $("#registeredOrgLocationSummary").empty();
     $("#editPrimaryUserContactDiv").empty();  */
     
     
     
     var sourceleadUrl = '${sourceleadUrl}';
     $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
     
     $.ajax({ 
	    	type: "POST",
	    	url: sourceleadUrl+"/getRegisteredOrgsLocationsList?${_csrf.parameterName}=${_csrf.token}",
	    			 
         success: function(data) {
	        var json = JSON.parse(data);
	        var result = "";
	        
	    	 if(json.length > 0)
	   		{
	    		
	 			for(var i=0; i<json.length; i++){
		       	 
	 				var orgLoc = json[i];
	 				var mobilePhone = "";
	 			    var email = "";
	 				
		    	    if(orgLoc.orglocationUserPhone != null && orgLoc.orglocationUserPhone != ''){
	 			    	mobilePhone = orgLoc.orglocationUserPhone;
	 			    }else{
	 			    	mobilePhone = "---"
	 			    }
	 				
	 				if(orgLoc.orglocationUserEmail != null && orgLoc.orglocationUserEmail != ''){
	 			    	email = orgLoc.orglocationUserEmail;
	 			    }else{
	 			    	email = "---"
	 			    }
	 				
	       	    	
	 				
	 				
	 				
	 		        
	 				result = "<div class='compOnBorLoca compOnBorCta'>";
	 				result = result +"<div class=''>";
	 				result = result +"<div class='user-profile'>";
	 				result = result +"<div class='col-xs-4 col-sm-4'>";
	 				result = result +"<div class='comLocaImg'>";
	 				result = result +"<img  onerror='predefineOrgImage(this);' class='m-t-xs' data-id='orgLogoImage' src='"+orgLoc.fileUrl+"' alt='image'>";
	 				result = result +"</div>";
	 				result = result +"</div>";
	 				result = result +"<div class='col-sm-8'>";
	 				result = result +"<h4 style='margin:0px 0px 5px 0px'><a href='javascript:void(0)' title='"+orgLoc.orgLocationName+"' onClick='getOrgLocationDetails("+orgLoc.id+")'>"+orgLoc.orgLocationName+" </a> </h4>";
	 				result = result +"<p><label><i class='fa fa-user'  aria-hidden='true' title='"+orgLoc.orglocationUserName+"'></i>&nbsp;&nbsp; "+orgLoc.orglocationUserName+" </label></p>"
	 				result = result +"<p><label><i class='fa fa-map-marker' aria-hidden='true'></i>&nbsp;&nbsp; "+orgLoc.country+" </lable></p>";
	       	    	result = result +"<p><label><i class='fa fa-envelope-o' aria-hidden='true'></i> "+email+" </lable></p>";
	 				result = result +"</div>";
	 				result = result +"<div class='clearfix'></div>";
	 				result = result +"</div>";
	 				result = result +"</div>";
	 				
	       	    	$("#loading").removeAttr("style"); 
	       	 	    $('#registeredOrgs').append(result);
				   }
	   		    }
	    	else{
	    		result = "<div class='content-area mt text-center text-muted mt40'><i class='mt fa fa-5x fa-building'></i><h3>Orgs List is Empty</h3></div>";
	    		$("#loading").removeAttr("style");
	    		$('#registeredOrgs').append(result);
	    	} 

			 },
	        error: function(e){
	        }
	    }); 
}
$(".loc-alertclose").click(function(){
	 $(".location-alert").fadeOut();
})
</script>




<script>
function createOrg(){
	$("#locationOrgsName").html("Add New Location");
	$("#registeredOrgs").empty();
	
	 document.getElementById("register_button").style.display=""; 
	 
	 var subscribedCountries = '${subscribedCountries}';
		var countries= [];
		var res = subscribedCountries.split(",");
		for(var i=0; i <res.length; i++){
			countries.push(res[i]);
		};
		
	 
	var result = '';
	
	result = result +'<div class="comLogContent">';
	result = result +'<div class="row clearfix compOnBorLoca">';
	
	
	result = result +'<div class="col-md-6">';
	
	result = result +'<div class="form-group clearfix" id="companyNameDiv">';
	result = result +'<label class="col-md-4 noPad">Company Name <span class="text-danger">*</span></label>';
	result = result +'<div class="col-md-8 padding_right">';
	result = result +'<input type="text" name="companyName" id="companyName"  class="form-control" onblur="validateCompanyName();"  field-name="Company Name" data-validation="required validate_Space validate_length length2-250" />';
	result = result +'<span class="hidden">Company</span>';
	result = result +'</div>';
	result = result +'</div>';
	
	result = result +'<input type="hidden" name="registeredOrgId" id="registeredOrgId"  value="${SourceLeadWeb_org.id}" />';

	
	result = result +'<div class="form-group clearfix">';
	result = result +'<label class="col-md-4 noPad">DBA</label>';
	result = result +'<div class="col-md-8 padding_right clearfix">';
	result = result +'<div class="col-md-10 noPad">';
	result = result +'<input id="dba" name="dba" class="form-control" type="text" field-name="DBA" data-validation="validate_Space validate_length length2-250" data-validation-optional="true">';
	result = result +'</div>';
	result = result +'<div class="col-md-2 dbaCheck dbaCheck-cs">';
             
	result = result +'<div class="mrleft8 mr mt5">';
	result = result +'<input id="dbaCheckbox" name="dbaCheckbox"  class="checkbox-custom" onclick="getDBAName();"  type="checkbox">';
	result = result +'<label for="dbaCheckbox" class="checkbox-custom-label" title="Make company name as dba"></label>';
	result = result +'</div>';
	result = result +'</div>';
	result = result +'</div>';
	result = result +'</div>';		
	
	result = result +'<div class="form-group clearfix">';
	result = result +'<label class="col-md-4 noPad">Website <span class="text-danger">*</span></label>';
	result = result +'<div class="col-md-8 padding_right">';
	result = result +'<input type="text" name="website"  value="${website}"   id="companyWebsite"  onblur="validateWebsite();validateCompanyEmail();"  field-name=" Website" data-validation="required validate_Space validate_domain validate_length length2-100"    class="form-control" />';
    result = result +'<span class="hidden">Website</span>';
	result = result +'</div>';
	result = result +'</div>';
	
	result = result +'<div class="form-group clearfix">';
	result = result +'<label class="col-md-4 noPad">Email <span class="text-danger">*</span></label>';
	result = result +'<div class="col-md-8 padding_right">';
	result = result +'<input type="text" name="email" id="email"   value="${email}" onblur="validateCompanyEmail();validateWebsite();getUserDetails();" field-name="Email" data-validation="required validate_Space validate_email validate_length length2-100" class="form-control"  />';
    result = result +'<span class="hidden">Email</span>';
	result = result +'</div>';
	result = result +'</div>';
	
	
	result = result +'<div class="form-group clearfix">';
	result = result +'<label class="col-md-4 noPad">First Name <span class="text-danger">*</span></label>';
	result = result +'<div class="col-md-8 padding_right">';
	result = result +'<input type="text" class="form-control" id="firstName" name="firstName" value="${firstName}" field-name="First Name" data-validation="required validate_Alphanumeric validate_Space validate_length length1-100" />';
    result = result +'<span class="hidden">First Name</span>';
	result = result +'</div>';
	result = result +'</div>';
	
	result = result +'<div class="form-group clearfix">';
	result = result +'<label class="col-md-4 noPad">Last Name</label>';
	result = result +'<div class="col-md-8 padding_right">';
	result = result +'<input type="text" name="lastName"  id="lastName"  value="${lastName}" class="form-control" field-name="Last Name" data-validation-optional="true" data-validation="validate_Space validate_Alphanumeric validate_length length1-100"  />';
    result = result +'<span class="hidden">Last Name</span>';
	result = result +'</div>';
	result = result +'</div>';
	
	result = result +'<div class="form-group clearfix">';
	result = result +'<label class="col-md-4 noPad">Phone Number</label>';
	result = result +'<div class="col-md-8 padding_right">';
	result = result +'<input type="text" name="mobilePhone"  id="mobilePhone"  value="${mobilePhone}" class="form-control" field-name="Phone Number" data-validation-optional="true" data-validation="validate_Space validate_length length9-20 validate_phone"  />';
    result = result +'<span class="hidden">Phone Number</span>';
	result = result +'</div>';
	result = result +'</div>';
	
	
	result = result +'<div class="form-group clearfix" >';
	result = result +'<label class="col-md-4 noPad">Org Short Code<span class="text-danger">*</span></label>';
	result = result +'<div class="col-md-8 padding_right">';
	result = result +'<div id="generatedUserIdDiv">';
	result = result +'<input type="text"  class="form-control" name="generatedUserId" id="generatedUserId" field-name="Org Short Code" style="text-transform:uppercase" data-validation="required validate_length length1-4"  onkeyup="validateOrgShortCode();" maxlength="4" />';
    result = result +'<span class="hidden">Short Code</span>';
	result = result +'</div>';
	result = result +'</div>';
	result = result +'</div>';
	
	
	result = result +'<div class="form-group clearfix">';
	result = result +'<label class="col-md-4 noPad">Max hours per day<span class="text-danger">*</span></label>';
	result = result +'<div class="col-md-8 padding_right">';
	result = result +'<input  type="text" class="form-control" maxlength="2" name="averageWorkingHours" id="averageWorkingHours" data-validation="required" field-name="Max hours per day" />';
    result = result +'<span class="hidden">Max hours per day</span>';
	result = result +'</div>';
	result = result +'</div>';
	

	result = result +'</div>';
	
	result = result +'<div class="col-md-6">';
	
	result = result +'<div class="form-group clearfix">';
	result = result +'<label class="col-md-4 noPad">Location</label>';
	result = result +'<div class="col-md-8 padding_right">';
	result = result +'<input class="form-control positionRelative" id="orgSetLocation" name="location" placeholder="Enter your Location"  type="text" field-name="Location" onclick="orgSubscriptionApiLocation(\'orgSetLocation\',\'orgSetCountry\',\'orgSetState\',\'orgSetCity\',\'orgSetZipCode\',\''+countries+'\',\'addressId\');" data-validation="required"></input>';
	result = result +'<i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshOrgEditGoogleApiLocation();"></i>';
	result = result +'</div>';
	result = result +'</div>';
	
	
	
	
	result = result +'<div class="form-group clearfix">';
	result = result +'<label class="col-md-4 noPad">Country<span class="text-danger">&nbsp;*</span></label>';
	result = result +'<div class="col-md-8 padding_right">';
	
	result = result + '   <select class="form-control chosen-select" name="country" id="orgSetCountry" field-name="Country" data-placeholder="Select Country" data-validation="required">';
 	result = result + '       <option value="">Select Country</option>';
 	result = result +'		<c:forEach var="subscibedCountries" items="${orgSubscribedCountriesList}">';
 	result = result + '       <option value="${subscibedCountries}">${subscibedCountries}</option>';
    result = result +'		</c:forEach>';
    result = result + '    </select>';
	
	result = result +'</div>';
	result = result +'</div>';
	
	
	
	
	result = result +'<div class="form-group clearfix">';
	result = result +'<label class="col-md-4 noPad">State<span class="text-danger">&nbsp;*</span></label>';
	result = result +'<div class="col-md-8 padding_right">';
	result = result +'<input class="form-control" id="orgSetState" name="state" placeholder="State" type="text" field-name="State" data-validation="required" ></input>';
	result = result +'</div>';
	result = result +'</div>';
	
	result = result +'<div class="form-group clearfix">';
	result = result +'<label class="col-md-4 noPad">City<span class="text-danger">&nbsp;*</span></label>';
	result = result +'<div class="col-md-8 padding_right">';
	result = result +'<input class="form-control" id="orgSetCity" name="city" placeholder="City" type="text" field-name="City" data-validation="required"  ></input>';
	result = result +'</div>';
	result = result +'</div>';
	
	
	result = result +'<div class="form-group clearfix">';
	result = result +'<label class="col-md-4 noPad">ZipCode</label>';
	result = result +'<div class="col-md-8 padding_right">';
	result = result +'<input class="form-control" id="orgSetZipCode" name="zipCode" placeholder="Zip code" field-name="Zip code" type="text"></input>';
	result = result +'</div>';
	result = result +'</div>';
	
	
	result = result +'<div class="form-group clearfix">';
	result = result +'<label class="col-md-4 noPad">Address</label>';
	result = result +'<div class="col-md-8 padding_right">';
	result = result +'<input type="text" name="address" id="address" field-name="Address" data-validation-optional="true" data-validation="validate_length length5-5000" class="form-control"/>';
	result = result +'</div>';
	result = result +'</div>';
	
	
	result = result +'<div class="form-group clearfix">';
	result = result +'<label class="col-md-4 noPad">Time Zone<span class="text-danger">&nbsp;*</span></label>';
	result = result +'<div class="col-md-8 padding_right">';
	result = result +'<select class="chosen-select form-control" data-placeholder="Time Zone" field-name="Time zone" data-validation="required" id="locationTimeZone" name="timeZone">';
    result = result +'</select>';
	result = result +'</div>';
	result = result +'</div>';
	
	result = result +'<div class="form-group clearfix">';
	result = result +'<label class="col-md-4 noPad">Date Format<span class="text-danger">&nbsp;*</span></label>';
	result = result +'<div class="col-md-8 padding_right">';
	result = result +'<select class="chosen-select form-control" data-placeholder="Date Format" field-name="Date Format" data-validation="required" id="dateFormat" name="dateFormat">';
    result = result +'</select>';
	result = result +'</div>';
	result = result +'</div>';
	
	result = result +'</div>';
                                 
	            
	            $("#registeredOrgs").append(result);
	            
	            
	            document.getElementById("enrollNewOrgLocation").style.display="none";
	            document.getElementById("orgsList").style.display="";
	            
	            
	            var config = {
	      			  '.chosen-select'           : {},
	      			  '.chosen-select-deselect'  : {allow_single_deselect:true},
	      			  '.chosen-select-no-single' : {disable_search_threshold:10},
	      			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
	      			  '.chosen-select-width'     : {width:"95%"}
	      			}
	           
	           $("#locationCountry").chosen();
	 	       $("#locationState").chosen();
	 	       $("#locationCity").chosen();
	 	       $("#locationTimeZone").chosen();
	 	       
	 	       getTimezonesList();
		      
		       $('.chosen-select').each(function() {
					var $this = $(this);
					$this.next().css({'width': '100%'});
			  })
	      	       
	
}
</script>

<script>
function getDBAName(){
	 if (document.getElementById('dbaCheckbox').checked) {
		 var companyName = document.getElementById("companyName").value;
		 if(companyName != null && companyName != '' && companyName != 'undefined'){
			$("#dba").val(companyName); 
		 }
     }else{
		//var dbaName = document.getElementById("dba").value;
		var dbaName = '${SourceLeadWeb_orgLocations.dba}';
		if(dbaName != null && dbaName != '' && dbaName != 'undefined'){
		$("#dba").val(dbaName);
		}else{
		$("#dba").val('');
		}
     } 
}
</script>
 
<script type="text/javascript">
	function moduleCheckValidation(id){
		
		 var modulesList = [];
 		 $("input[name='modulesList[]']:checked").each( function (i) {
 			modulesList[i] = $(this).val();
 		});
 		 
		if (modulesList.length > 0) { 
		document.getElementById("moduleValue").value = id.value;
		}else{
			$('#moduleValue').val(''); 
		}
	} 
</script>




 <script type="text/javascript">
function getRecruitmentTypes(id){
		if (document.getElementById('isRecruiting').checked) {
		 document.getElementById("recrutmentTypes").style.display="";
		}else{
			document.getElementById("recrutmentTypes").style.display="none";
			 $('.allsubmodules').each(function() {
	                this.checked = false;      
	            }); 
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
        	$("input#averageWorkingHours").parent().after("<p class='jquery_form_error_message'><span>Max hours per day should be less then or equal to 24</span>");
        }
    });
});

</script>


<script>
		function logoutFormSubmit() {
			document.getElementById("logoutForm").submit();
		}
</script>




<script>
function getTimezonesList(){
	var sourceleadUrl ='${sourceleadUrl}';
	var country = $('#orgSetCountry').val();
	$("#dateFormat").empty();
	if(country == "India"){
		$('#dateFormat').append("<option value='dd-MM-yyyy' selected>dd-MM-yyyy</option>");
		$('#dateFormat').append("<option value='MM-dd-yyyy'>MM-dd-yyyy</option>");
		$('#dateFormat').append("<option value='yyyy-dd-MM'>yyyy-dd-MM</option>");
		$('#dateFormat').append("<option value='yyyy-MM-dd'>yyyy-MM-dd</option>");
	}else{
		$('#dateFormat').append("<option value='MM-dd-yyyy' selected>MM-dd-yyyy</option>");
		$('#dateFormat').append("<option value='dd-MM-yyyy'>dd-MM-yyyy</option>");
		$('#dateFormat').append("<option value='yyyy-dd-MM'>yyyy-dd-MM</option>");
		$('#dateFormat').append("<option value='yyyy-MM-dd'>yyyy-MM-dd</option>");
	}
	 $.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/getTimezonesList?${_csrf.parameterName}=${_csrf.token}", 
	        success: function(data) {
	        var json = JSON.parse(data);
	        var output = "";
	        output = output + "<option value=''>--Select--</option>";
	        if(country == 'India'){
    			output = output + "<option value='246' selected> Asia/Kolkata </option>";
    		}else if(country == 'United States'){
    			output = output + "<option value='132' selected> America/Chicago </option>";
    		}else if(country == 'United Kingdom'){
    			output = output + "<option value='333' selected> Europe/London </option>";
    		}else if(country == 'Singapore'){
    			output = output + "<option value='272' selected> Asia/Singapore </option>";
    		}else if(country == 'France'){
    			output = output + "<option value='342' selected> Europe/Paris </option>";
    		}else if(country == 'Australia'){
    			output = output + "<option value='298' selected> Australia/Adelaide </option>";
    		}
	        	for (var i=0; i<json.length; i++){
	        		var opt = json[i];
	        		output = output + "<option value='" +opt.id+ "'>"+opt.timeZoneId+"</option>";
				}
	        	$("#locationTimeZone").empty();
	        	$("#locationTimeZone").append(output);
	        	$("#locationTimeZone").trigger("chosen:updated");
	        }
	 });
}
</script>


<script>
	function refreshOrgEditGoogleApiLocation(){
		$("#orgSetLocation").val('');
		$("#orgSetCountry").val('');
		$("#orgSetState").val('');
		$("#orgSetCity").val('');
		$("#orgSetZipCode").val('');
	}
</script>


<script type="text/javascript">
function validateCompanyName(){
	var con = document.getElementById("companyName").value;
	var company = document.getElementById("companyName").value;
	var companyName = encodeURIComponent(con);
	var sourceleadUrl ='${sourceleadUrl}';
	 if(companyName != null && companyName != ''){
		 $("#register_button").attr("disabled", "disabled");
		 $( "#register_button" ).addClass('button').val('Processing..');
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
		    				$( "#register_button" ).addClass('sign_btn').val('Register');
		    				$("#register_button").removeAttr("disabled");
			        	}
			        	else{
			        		//New company
			        		$("#register_button").removeAttr("disabled");
				    		$('input#companyName').removeAttr( "class record-exist record-exist-errormsg");
							$('#companyNameDiv').find('p.jquery_form_error_message').remove();
							$('input#companyName').attr('class','valid form-control');
							$( "#register_button" ).removeAttr("style");
							$( "#register_button" ).addClass('sign_btn').val('Register');
							$( "#register_button" ).click(function() {
							 if ($(this).validate(false, validationSettings)){
							 }
							});
			        	}  
			       },
			        
		   }); 
		 
	 }
	 getDBAName();
}
</script>


<script type="text/javascript">
function validateWebsite (){
      var website = document.getElementById("companyWebsite").value; 
     var sourceleadUrl ='${sourceleadUrl}';
     var sourceleadBLockedwebsites ='${sourceLeadBlockedSocialDomains}';
	 $('input#companyWebsite').removeAttr( "record-exist record-exist-errormsg");
	 if(website != null && website != ''){
		
		 $("#register_button").attr("disabled", "disabled");
		 $( "#register_button" ).addClass('button').val('Processing..');
			 $.ajax({
				    type: "GET",
       	            url: sourceleadUrl+"/validateOrgWebsite?website="+website+"&sourceleadBLockedwebsites="+sourceleadBLockedwebsites,
			        success: function(data) {
			        	 var result = data;
			        		if (result == "Website Duplicated" || result == "Invalid Website") {
		        			//existed website
		    	    		$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove(); 
		    	    		$('input#companyWebsite').attr('class','error form-control');
		    	    		$("input#companyWebsite").closest('b').after("<p class='jquery_form_error_message'> "+website+" "+result+"</p>");
		    				$('input#companyWebsite').attr('record-exist','yes');
		    				$('input#companyWebsite').attr('record-exist-errorMsg',website+' '+result+''); 
		    				$( "#register_button" ).addClass('sign_btn').val('Register');
		    				$("#register_button").removeAttr("disabled");
			        	}
			        	else{
			        		//New WebSite
			        		$("#register_button").removeAttr("disabled");
				    		$('input#companyWebsite').removeAttr( "class record-exist record-exist-errormsg");
							$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
							$('input#companyWebsite').attr('class','valid form-control');
							$( "#register_button" ).removeAttr("style");
							$( "#register_button" ).addClass('sign_btn').val('Register');
							$( "#register_button" ).click(function() {
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
function validateCompanyEmail(){
    var websiteValue = document.getElementById("companyWebsite").value;
    var emailValue = document.getElementById("email").value;
    var sourceleadBLockedwebsites ='${sourceLeadBlockedSocialDomains}'; 
    var sourceleadUrl ='${sourceleadUrl}';
		if((websiteValue != null && websiteValue != '') && (emailValue != null && emailValue != '')){
		$.ajax({
		   	type: "GET",
		       url: sourceleadUrl+"/emailAndWebsiteRegistrationValidation?website="+websiteValue+"&sourceleadBLockedwebsites="+sourceleadBLockedwebsites+"&email="+emailValue,
		       success: function(data) {
		    		if(data != null && data != ''){
					     if(data == 'Invalid Email'){
					    	    //Invalid Email
					    	    $('#emailDiv').find('p.jquery_form_error_message').remove(); 
			    	    		$('input#email').attr('class','error form-control');
			    	    		$("input#email").closest('b').after("<p class='jquery_form_error_message'> "+emailValue+" is Invalid</p>");
			    				$('input#email').attr('record-exist','yes');
			    				$('input#email').attr('record-exist-errorMsg',emailValue+' is Invalid'); 
			    				$( "#register_button" ).addClass('sign_btn').val('Register');
			    				$("#register_button").removeAttr("disabled");
					     } 
					     else if(data == 'Existed Email'){
					    	   //Existed Email
					    	    $('#emailDiv').find('p.jquery_form_error_message').remove(); 
			    	    		$('input#email').attr('class','error form-control');
			    	    		$("input#email").closest('b').after("<p class='jquery_form_error_message'> "+emailValue+" is Already Existed</p>");
			    				$('input#email').attr('record-exist','yes');
			    				$('input#email').attr('record-exist-errorMsg',emailValue+' is Already Existed'); 
			    				$( "#register_button" ).addClass('sign_btn').val('Register');
			    				$("#register_button").removeAttr("disabled");
					    	 
					     }
				       	else{
				       		//Valid Email
				       		$("#register_button").removeAttr("disabled");
				    		$('input#email').removeAttr( "class record-exist record-exist-errormsg");
							$('#emailDiv').find('p.jquery_form_error_message').remove();
							$('input#email').attr('class','valid form-control');
							$( "#register_button" ).removeAttr("style");
							$( "#register_button" ).addClass('sign_btn').val('Register');
							$( "#register_button" ).click(function() {
							 if ($(this).validate(false, validationSettings)){
							 }
							});
				       	}
			       	}
		    	   
		      },
		       
		}); 
	   }
}
</script>

<script type="text/javascript">
function getUserDetails(){
	var email = document.getElementById("email").value;
	var sourceleadUrl ='${sourceleadUrl}';
	if(email){
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/getUserDetails?${_csrf.parameterName}=${_csrf.token}&email="+email,
	        contentType: "application/json; charset=utf-8",
	        dataType: "json",
	        success: function(data) {
	        	if(data != null && data != ''){
	        		var json = JSON.parse(data);
	        		
	        		 if(json[0] != null && json[0] != ''){
	        			$("#firstName").attr("readonly", "readonly");
	        		    $("#firstName").val(json[0]);
	        		 }else{
	        			 $("#firstName").removeAttr("readonly", "readonly");
	        			 $("#firstName").val('');
	        		 }
	        		 
	        		 if(json[1] != null && json[1] != ''){
		        			$("#lastName").attr("readonly", "readonly");
		        		    $("#lastName").val(json[1]);
	        		 }else{
	        			    $("#lastName").removeAttr("readonly", "readonly");
	        			    $("#lastName").val('');
	        		 }
	        		
	        		 if(json[2] != null && json[2] != ''){
		        			$("#mobilePhone").attr("readonly", "readonly");
		        		    $("#mobilePhone").val(json[2]);
	        		 }else{
	        			    $("#mobilePhone").removeAttr("readonly", "readonly");
	        			    $("#mobilePhone").val('');
	        		 }
	        	}
			 },
	        error: function(e){
	            //alert('Ajax Error: ' + e.value);
	        }
	    });
	}else{
		$("#firstName").removeAttr("readonly");
	    $("#firstName").val('');
	    
	    $("#lastName").removeAttr("readonly");
	    $("#lastName").val('');
	    
	   
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
         $('body').on('submit', '#createOrg_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		  
        		e.preventDefault();
        		var formData = getFormData("createOrg_form");
         		var sourceleadUrl ='${sourceleadUrl}';
         		
         		
         		 
         		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
         	 	 	$.ajax({
          	      type: "POST",
          	      data: formData,
          		  url: sourceleadUrl+"/companyLocationRegistration?${_csrf.parameterName}=${_csrf.token}",		  
    			  
                	success: function(result) {
                		
               		if (result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
      	    		    showError(result.responseMessage);
      	    		    $("#loading").removeAttr('style');
      	    		}
                		else if (result.response === "AWKWARD") {
      	    		    location.href = "/errorPage";
      	    		}
                		else if(result.response === "SUCCESS"){
                		   $("#loading").removeAttr('style');
                			loadRegisteredOrgs();  
			        	    }
                		else if (result.indexOf('Sign In') !== -1) {
      					location.replace(sourceleadUrl);
      				} else {
      				    showError("Operation failed, please try again");
      				    $("#loading").removeAttr('style');
      				}
			        	
			        },  
               });    
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
            
         $('body').on('blur', '#createOrg_form', function() {
     		$("#createOrg_form").showHelpOnFocus();
     		$("#createOrg_form").validateOnBlur(false, validationSettings);
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





