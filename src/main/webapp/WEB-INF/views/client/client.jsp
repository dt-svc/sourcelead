<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">
window.onload = function () {
	initLocationAuto('clientLocation','clientCountry','clientState','clientCity','clientZipCode','clientLatitude','clientLongitude','invite','addressId');
 };
</script>
 <script>
 var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	$(document).ready(function() {
		document.getElementById("client_form").reset();
		<c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
		  var sectionId = '${section.id}';
		  getCustomDependentField(sectionId);
		 </c:forEach>
	});
</script>

<!-- Content Area -->
<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Create Client</h2>
		</div>
		<div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo;<a href="${SourceLeadWeb_vmsUrl}/clientDashboard"> Clients List</a>
			&raquo; Add Client</div>
	</section>
	
	<div class="content-area content-new clearfix">
	<div id="loading"></div> 
	<form:form id="client_form" name="client_form" method="post">
	<input type = "hidden" id="isLawFirm" title="Is law firm" name="isLawFirm"  type="checkbox" value = "N">
	
	<input type = "hidden" id="isAccountingFirm" title="Is law firm" name="isAccountingFirm"  type="checkbox" value = "N">
	
	<input type="hidden" id="actionId" value="${actionId}">
      <div class="clearfix form-horizontal inner-content">
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
           <h3>Client Details</h3>
            <div class="form-group" ><label class="col-md-3" for="Project Id">Client Name<span class="text-danger">&nbsp;*</span></label>
				<div class="col-md-9" id="clientNameDiv">
					<input type="text" class="form-control" id="orgName" name="orgName" value="${orgName}" onclick="getOrgName();" onblur="getOrgDetails(); validateOrgName();" field-name="Client Name" data-validation="required validate_length length2-100" value="">
					<input type="hidden" id="orgLocationId" />
					<span class="hidden">Enter Client Name</span>
				</div>
			</div>
            
			<div class="form-group " id="companyWebsiteDiv">
				<label class="col-md-3">Web Url</label>
				<div class="col-md-9">
				<input type="text" class="form-control" id="webSite" name="webSite" value="" field-name="Client Website"  onblur="validateWebsite();" data-validation="validate_Space validate_domain validate_length length2-100" data-validation-optional="true">
				</div>
			</div>
						
			
			<div class="form-group" id="locationDiv" style="display: block;">
	          <label class="col-md-3">Address<span class="text-danger">&nbsp;*</span></label>
	          <div class="col-md-9" id="locationField">
	          <div class="positionRelative">
	          <input class="form-control positionRelative" id="clientLocation" name="location" placeholder="Enter Address"  type="text" field-name="Address" data-validation="required"></input>
	          <i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('clientLocation','clientCountry','clientState','clientCity','clientZipCode','addressId');"></i>
	          </div>
	          </div>
	        </div>
		
		   <div class="form-group" id="orgLocationDiv" style="display: none;">
	          <label class="col-md-3">Address</label>
	          <div class="col-md-9">
	          <div class="positionRelative">
	          <input class="form-control positionRelative" id="orgLocation" name="orgLocation" placeholder="Enter your address"  type="text" field-name="Address" readonly></input>
	          </div>
	          </div>
	        </div>
		
		  <div class="form-group ">
				<label class="col-md-3">Street address</label>
				<div class="col-md-9">
				<input type="text" id="addressId" name="address"   field-name="Street Address" class="form-control" placeholder="Enter Street Address"  data-validation="validate_length length2-250" data-validation-optional="true"></input>
				</div>
			</div>
			
			<div class="form-group" id="cityDiv" style="display: block;">
	          <label class="col-md-3">City</label>
	          <div class="col-md-9">
	          <input class="form-control" id="clientCity" name="city" placeholder="Enter City" type="text"></input>
	          </div>
	        </div>
	        
	        
		  <div class="form-group" id="orgCityDiv" style="display: none;">
				<label class="col-md-3">City</label>
				<div class="col-md-9">
				<input type="text" class="form-control" id="orgCity" placeholder="Enter your City" name="orgCity" value="" readonly="readonly">
				</div>
		   </div>
		   
		   <div class="form-group" id="stateDiv" style="display: block;">
	          <label class="col-md-3">State</label>
	          <div class="col-md-9">
	          <input class="form-control" id="clientState" name="state" placeholder="Enter State" type="text"></input>
	          </div>
	        </div>
	        
	        	 
			 <div class="form-group" id="orgStateDiv" style="display: none;">
				<label class="col-md-3">State</label>
				<div class="col-md-9">
				<input type="text" class="form-control" id="orgState" placeholder="Enter your State" name="orgState" value="" readonly="readonly">
				</div>
		 	</div>
		 	
		 	 <div class="form-group" id="zipCodeDiv" style="display: block;">
	          <label class="col-md-3">ZipCode</label>
	          <div class="col-md-9">
	          <input class="form-control" id="clientZipCode" name="zipcode" placeholder="Enter Zip code" type="text"  onclick="zipcodeMask('${loginCountryName}','clientZipCode');"></input>
	          </div>
	        </div>
	        
	        <div class="form-group" id="orgZipCodeDiv" style="display: none;">
	          <label class="col-md-3">ZipCode</label>
	          <div class="col-md-9">
	          <input class="form-control" id="orgZipCode" name="orgZipcode" placeholder="Enter your Zip code" type="text" readonly></input>
	          </div>
	  		  </div>	
	  		  
			<div class="form-group" id="countryDiv" style="display: block;">
	          <label class="col-md-3">Country<span class="text-danger">&nbsp;*</span></label>
	          <div class="col-md-9">
	          <select class="form-control chosen-select" name="country" id="clientCountry" field-name="Country" data-validation="required" >
	                   <option  ${loginCountryName  == 'Australia' ? 'selected' : ''}>Australia</option>
	                   <option  ${loginCountryName  == 'France' ? 'selected' : ''}>France</option>
	                   <option  ${loginCountryName  == 'India' ? 'selected' : ''}>India</option>
	                   <option  ${loginCountryName  == 'Singapore' ? 'selected' : ''}>Singapore</option>
	                   <option  ${loginCountryName  == 'United Kingdom' ? 'selected' : ''}>United Kingdom</option>
	                   <option  ${loginCountryName  == 'United States' ? 'selected' : ''}>United States</option>
                 </select>
                  
	          </div>
	        </div>
	        
	        <div class="form-group" id="orgCountryDiv" style="display: none;">
				<label class="col-md-3">Country</label>
				<div class="col-md-9">
				<input type="text" class="form-control" id="orgCountry" name="orgCountry" value="" readonly="readonly">
				</div>
			</div>
	        
			
			<div class="form-group hidden">
	          <label class="col-md-3">Lat</label>
	          <div class="col-md-9">
	          <input class="form-control" id="clientLatitude" name="latitude" type="text"></input>
	          </div>
	        </div>
	        
	        <div class="form-group hidden" >
	          <label class="col-md-3">Long</label>
	          <div class="col-md-9">
	          <input class="form-control" id="clientLongitude" name="longitude"  type="text"></input>
	          </div>
	        </div> 
         
            <div class="form-group">
            
            <div class="clearfix mb">
              <label class="col-md-3">Tier <i class='fa fa-info-circle' title='Classify the clients basing on their performance'></i></label>
              <div class="col-md-9">
                  <select class="form-control selectpicker" id="level" name="level">
                    <option value="Tier-1">Tier-1</option>
                    <option value="Tier-2">Tier-2</option>
                    <option value="Tier-3">Tier-3</option>
                    <option value="Tier-4">Tier-4</option>
                    <option selected="selected" value="Tier-5">Tier-5</option>
                  </select>
              </div>
              </div>
              
              <div class="clearfix width100Percnt">
              <label class="col-md-3">Payment Frequency /Tenure</label>
              <div class="col-md-9">
                  <select class="form-control selectpicker" id="PaymentTermId" name="paymentTerms">
                  <option value="">Select Payment Term</option>
                    <option value="10 days">10 days</option>
                    <option value="15 days">15 days</option>
                    <option value="30 days">30 days</option>
                    <option value="45 days">45 days</option>
                    <option value="60 days">60 days</option>
                    <option value="90 days">90 days</option>
                  </select>
              </div>
            </div>
              
              
              
            </div>
            
            
            
			
			<div class="form-group ">
				<label class="col-md-3">Billing Address</label>
				<div class="col-md-9">
				<textarea id="billingAddressId" rows="4" name="billingAddress" field-name="Billing Address" class="form-control" data-validation="validate_length length2-250" data-validation-optional="true"></textarea>
				</div>
			</div>
			
			<div class="form-group ">
				<label class="col-md-3">Generate Invoice </label>
				<div class="col-md-9">
				<select class="form-control selectpicker" id="generateInvoice" name="generateInvoice">
                    <option value="Bulk">Bulk</option>
                    <option value="Individual">Individual</option>
                  </select>				
                  </div>
			</div>
			
            <div class="clearfix mb"></div>
            <c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
               <c:if test="${sectionOrder.index == 0}">
                  <div id="customDiv${section.id}"></div>
               </c:if>
            </c:forEach>
             
             
            
          </div>
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
            <h3>Contact Details</h3>
            
            <div class="form-group" id="EmailIdDiv">
				<label class="col-md-3" for="Project Manager">Email<span class="text-danger">&nbsp;*</span></label>
				<div class="col-md-9">
				<div class="input-group errorInptgroup">
					<input type="text" class="form-control" id="email" name="email" value="${emailId}" field-name="Email" data-validation="required validate_Space validate_length length2-100 validate_email" onblur="validateEmail();getSpocUserDetails();">
                      <span class="hidden">Valid Email</span>
                      <span id="emailStatusIconDiv" class="input-group-addon">
                    	<i class="fa fa-check text-muted" style="color:#f6f6f6"></i>
                   	</span>
				</div>
				</div>
			</div>
            
            <div class="form-group">
              <label class="col-md-3">First Name<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9" id="firstNameDiv">
                <input class="form-control" type="text" id="firstName" name="firstName" pattern="[A-Za-z\s]*" maxlength="100" field-name="First Name" data-validation="required validate_AlphaNumber validate_length length1-100">
              </div>
            </div>
            
            <div class="form-group">
              <label class="col-md-3">Last Name</label>
              <div class="col-md-9">
                <input type="text" class="form-control" id="lastName"
									name="lastName" pattern="[A-Za-z\s]*" maxlength="100" required>
              </div>
            </div>
			
            <div class="form-group">
	              <label class="col-md-3">Phone Number</label>
	              <div class="col-md-9">
	              <div class="row">
	              <div class="col-md-6 paddingRight">
	                <input type="text" class="form-control" name="phoneNumber" id="phoneNumber" data-validation="validate_Space validate_length length9-20 validate_phone" data-validation-optional="true">
	              </div>
             <div class="col-md-6 paddingLeft erMesMid">
	             <div class="row">
	             <label class="col-md-5">Extension </label>
	             <div class="col-md-7 posSta">
	             <input type="text" class="form-control" id="extensionNumber" name="extensionNumber" data-validation="validate_extno validate_length length1-5" data-validation-optional="true">
	             </div>
	             </div>
             </div>
             
              </div>
              </div>
            </div>
            
            <div class="form-group">
              <label class="col-md-3">Status</label>
              <div class="col-md-9">
                <select class="form-control selectpicker" id="spocStatus" name="spocStatus">
                  <option value="Active">Active</option>
                  <option value="In-Active">In-Active</option>
                 <!--  <option value="New">New</option>
                  <option value="Negotiation">Negotiation</option>
                  <option value="Contract">Contract</option> -->
                </select>
              </div>
            </div>
            
            <div class="form-group">
              <label class="col-md-3">Owner Name<span class="text-danger">&nbsp;* </span><i class='fa fa-info-circle' title='The internal point of contact to the client. A single contact can have multiple owners'></i></label>
              <div class="col-md-9 candAdvsrch">
                <select multiple data-placeholder="select owner" class="chosen-select form-control" name="owner" id="owner" field-name="Owner Name"  data-validation="required">
                <c:forEach var="user" items="${userList}">
	                     <option value="${user.id}" ${user.id  == SourceLeadWeb_userId ? 'selected' : ''} >${user.firstName} ${user.lastName}</option>
                </c:forEach>
                </select>
              </div>
            </div>
            
            </div>
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
            <div class="form-group">
              <label class="col-md-3"></label>
              <div class="col-md-9 checkBox">
                 <label> <input type="checkbox" value="Y" name="isMailSent"> Send Invitation
				</label>
              </div>
            </div>
            
            <div class="form-group">
              <label class="col-md-3"></label>
              <div class="col-md-9 checkBox">
                 <label> <input type="checkbox" value="N" name="invoiceOrBillSent" id= "invoiceOrBillSent" onclick = "allowInvoiceEmailSent();"> Invoice Email
				</label>
              </div>
            </div>
            
            <div class="form-group">
              <label class="col-md-3"></label>
              <div class="col-md-9 checkBox">
                 <label> <input type="checkbox" value="N" name="apOrArSent" id="apOrArSent" onclick = "allowApEmailSent();"> AP Email 
				</label>
              </div>
            </div>
            
            <!-- These are the default sourelead roles we are providing for spoc in client creation -->
           <!--    <div class="form-group" id="roleDiv" style="display: block;">
                  <label class="col-md-3">Role <span class="text-danger">&nbsp; *</span></label>
                  <div class="col-md-9">
                         <select class="form-control select-bg" id="role" name="roleType" field-name="Role" data-validation="required">
		                        <option value="">-Select Role-</option>
			              </select>
                  </div>
                </div>-->
                
                <div class="form-group" id="userRoleDiv" style="display: none;">
					<label class="col-md-3">Role <span class="text-danger">&nbsp; *</span></label>
					<div class="col-md-9">
					<input type="text" class="form-control" id="userRoleText"  name="userRoleText" readonly="readonly">
					<input type="hidden" id="userRole" name="roleTypeId" value="" />
					
				   </div>
		        </div> 
		        <c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
               <c:if test="${sectionOrder.index == 1}">
                  <div id="customDiv${section.id}"></div>
               </c:if>
            </c:forEach>
		 
          </div>
        </div>
      </div>
      <div class="form-footer clearfix">
        <div class="col-lg-12">
          <div class="pull-right">
            <input type="button" value="Save" id="save_client" onclick="saveClient(); flushSaveClient()" class="btn btn-primary"> 
			<input type="button" value="Cancel" onClick="javascript:window.location.href = '${SourceLeadWeb_vmsUrl}/clientDashboard'" class="btn btn-default ml">
          </div>
        </div>
      </div>
      </form:form>
      </div>
      
	
</section>
<!-- Content Area Ends-->

<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->



<script>

function allowApEmailSent(){
	var checked = document.getElementById("apOrArSent").checked
	if(checked == true){
		$("#apOrArSent").val("Y");
	}else{
		$("#apOrArSent").val("N");
	}
}


function allowInvoiceEmailSent(){
	var checked = document.getElementById("invoiceOrBillSent").checked
	if(checked == true){
		$("#invoiceOrBillSent").val("Y");
	}else{
		$("#invoiceOrBillSent").val("N");
	}
}

function getCustomDependentField(sectionId){
	var sourceleadUrl = '${sourceleadUrl}';
	 $.ajax({
			type : "POST" , 
				 url: sourceleadUrl+"/getAllASectionCustomFields?sectionId="+sectionId,
				 success: function(data) {
		        	$('#customDiv'+sectionId).empty();
		        	$('#customDiv'+sectionId).append(data);
				 }
		  });
}
</script>


<script type="text/javascript">
function getOrgName(){
	var orgCode = '${SourceLeadWeb_orgCode}';
	
	$.ajax({                           
    	type: "POST",
    	url : vmsUrl+"/getOrgs?"+tokenParamName+"="+tokenValue+"&orgCode="+orgCode,
        success: function(data) {
        	var json = JSON.parse(data);
        	var org = [];
        	for(var i=0;i<json.length;i++){
       		 var label = json[i][0]; 
       		 var value = json[i][1];
       	     tmp = {
       	        'label': label,
       	        'value': value
       	      };
       	  	org.push(tmp);
       		}
        	$("#orgName").autocomplete({
                
                minLength: 0,
                source: org,
                focus: function( event, ui ) {
                       $( "#orgName" ).val( ui.item.label );
                       $( "#orgLocationId" ).val( ui.item.value );
                       return false;
                   },
                select: function( event, ui ) {
                	 $( "#orgName" ).val( ui.item.label );
                     $( "#orgLocationId" ).val( ui.item.value );
 			         $("#save_client").attr("disabled", "disabled");
                     $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
                     getOrgDetails(); 
                     validateOrgName();
                     $("input[name=address]").val('');
                     $("#loading").removeAttr("style"); 
                     $("#save_client").removeAttr("disabled");
                    return false;
                }  
             })
        },
        error: function(e){
        }
    });
}
</script>


<script type="text/javascript">
function getOrgDetails()
{
	
$( "#orgLocationId" ).val( '' );
	
	$("#webSite").attr("disabled", false);
	$("#webSite" ).val('');
	
	$("#orgCountry").val('');
	$("#orgState").val('');
	$("#orgCity").val('');
	$("#orgLocation").val('');
	$("#orgZipCode").val('');
	
	document.getElementById("countryDiv").style.display="";
	document.getElementById("orgCountryDiv").style.display="none";
	
	document.getElementById("stateDiv").style.display="";
	document.getElementById("orgStateDiv").style.display="none";
	
	document.getElementById("cityDiv").style.display="";
	document.getElementById("orgCityDiv").style.display="none";
	
	document.getElementById("locationDiv").style.display="";
	document.getElementById("orgLocationDiv").style.display="none";
	
	document.getElementById("zipCodeDiv").style.display="";
	document.getElementById("orgZipCodeDiv").style.display="none";
	
	
	document.getElementById("userRoleDiv").style.display="none"; 
	
	
	var orgName = document.getElementById("orgName").value;
	orgName = encodeURIComponent(orgName);
 	var orgLocationId = document.getElementById("orgLocationId").value; 
	if(orgName){
   	 $.ajax({
   	    	type: "POST",
   	    	url : vmsUrl+"/getOrgDetails?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&orgName="+orgName,
   	        contentType: "application/json; charset=utf-8",
   	        dataType: "json",
   	        success: function(data) {
   	        	if(data != null && data != ''){
   	        		var json = JSON.parse(data);
   	        		if(json['country'] != null && json['country'] != ''){
   	        			document.getElementById("countryDiv").style.display="none";
   	        			document.getElementById("orgCountryDiv").style.display="";
   	        		    $("#orgCountry").val(json['country'] );
   	        		}
   	        		
   	        		if(json['state'] != null && json['state'] != ''){
        			document.getElementById("stateDiv").style.display="none";
        			document.getElementById("orgStateDiv").style.display="";
  	        		    $("#orgState").val(json['state']);
  	        		}
   	        		if(json['city'] != null && json['city'] != ''){
	       			document.getElementById("cityDiv").style.display="none";
	       			document.getElementById("orgCityDiv").style.display="";
   	        		    $("#orgCity").val(json['city']);
   	        		}
		   	        
			      	if(json['website'] != null && json['website'] != ''){
			      		$("#webSite").val("www."+json['website']);
			      		$("#webSite").attr("disabled", true);
			      	}
			      	
			      	if(json['location'] != null && json['location'] != ''){
			      	$('#clientLocation').attr("data-validation-optional", "true");  
			      	document.getElementById("locationDiv").style.display="none";
   	        		document.getElementById("orgLocationDiv").style.display="";
   	        		    $("#orgLocation").val(json['location']);
			      	}
			      	
			      	if(json['zipCode'] != null && json['zipCode'] != ''){
			      	document.getElementById("zipCodeDiv").style.display="none";
   	        		document.getElementById("orgZipCodeDiv").style.display="";
			      	}
   	        	}
   			 },
   	        error: function(e){
   	        }
   	    });
   	 
   /* validateEmail(); 
   getSpocUserDetails(); */
	
	}
	
}
</script>


<script type="text/javascript">
function getRolesList(){
	var orgName = document.getElementById("orgName").value;
	var orgLocationId = document.getElementById("orgLocationId").value;
	
	if(orgName){
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
	    	type: "POST",
	        url: vmsUrl+"/getRolesList?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&moduleType=VMS", 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var output = "";
	        	output = output + "<option value=''>-- Select Role --</option>"; 
	        	for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		output = output + "<option value='" +opt.id+ "'>"+opt.displayName+"</option>";
				}
	        	$("#role").empty();
		        $("#role").append(output);
		        $('#role').trigger("chosen:updated");
		        $("#loading").removeAttr("style"); 
	        },
	    });
	}else{
		var output = "";
    	 output = output + "<option value=''>-- Select Role --</option>"; 
    	 $("#role").empty();
	     $("#role").append(output);
	     $('#role').trigger("chosen:updated");
	}
	 
}
</script>


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

<script type="text/javascript">
	function saveClient() {
		if ($('#client_form').validate(false, validationSettings)) {
			var orgLocationId = document.getElementById("orgLocationId").value;
		  	var formData = $($('#client_form')[0]).serialize();
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.post(vmsUrl+"/saveClient?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&responderType=Client", formData, function(data) {
				location.replace(vmsUrl+ "/clientDashboard");
			});
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#client_form', function() {
		$("#client_form").showHelpOnFocus();
		$("#client_form").validateOnBlur(false, validationSettings);
	});
</script>

<script type="text/javascript">
function validateEmail(){
	var email = document.getElementById("email").value;
	if(email != null && email != ''){
		$.ajax({
			type : "POST",
			url : vmsUrl + "/validateClientOrVendorEmails?"+tokenParamName+"="+tokenValue+"&email="+email,
			success : function(data) {
				var result = data;
				if(result == 'Invalid Email') {
					$('#EmailIdDiv').find('p.jquery_form_error_message').remove();
					$("input#email").after( "<p class='jquery_form_error_message'>Email should not your org user email</span>");
					document.getElementById("email").setAttribute( 'record-exist', 'yes');
					document.getElementById("email").setAttribute( 'record-exist-errorMsg',"Email should not your org user email");
					
				}else if(result == 'Valid Email') {
					$('input#email').removeAttr("class record-exist record-exist-errormsg");
					$('input#email').attr('class','valid form-control');
					$('#EmailIdDiv').find('p.jquery_form_error_message').remove();
					validateEmailForExistenceUniversally(email, "input#email");
				}
			},

		});
	}else{
		 $("#emailStatusIconDiv").find("i").remove();
   		 $("#emailStatusIconDiv").append('<i class="fa fa-check text-muted" style="color:#f6f6f6"></i>');
	}
}
</script>

 
<script type="text/javascript">
function getSpocUserDetails(){
	var email = document.getElementById("email").value;
	var orgLocationId = document.getElementById("orgLocationId").value;
	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	if(email){
		$.ajax({
	    	type: "POST",
	    	url : vmsUrl+"/getSpocUserDetails?"+tokenParamName+"="+tokenValue+"&email="+email+"&orgLocationId="+orgLocationId,
	        contentType: "application/json; charset=utf-8",
	        dataType: "json",
	        success: function(data) {
	        	if(data != null && data != ''){
	        		var json = JSON.parse(data);
	        		
	        		
	        		 if(json[0] != null && json[0] != ''){
	        			$('#firstNameDiv').find('p.jquery_form_error_message').remove();
	        			$("#firstName").attr("disabled", true);
	        		    $("#firstName").val(json[0]);
	        		 }else{
	        			 $("#firstName").attr("disabled", false);
	        			 //$("#firstName").val('');
	        		 }
	        		 
	        		 if(json[1] != null && json[1] != ''){
		        			$("#lastName").attr("disabled", true);
		        		    $("#lastName").val(json[1]);
	        		 }else{
	        			    $("#lastName").attr("disabled", false);
	        			    $("#lastName").val('');
	        		 }
		        		
	        		 if(json[2] != null && json[2] != ''){
		        			$("#phoneNumber").attr("disabled", true);
		        		    $("#phoneNumber").val(json[2]);
	        		 }else{
	        			    $("#phoneNumber").attr("disabled", false);
	        			    $("#phoneNumber").val('');
	        		 }
	        		 
	        		 
	        		 if(json[3] != null && json[3] != ''){
	   	        			document.getElementById("userRoleDiv").style.display="";
	   	        			$("#userRole").val(json[3]);
	   	        			$("#userRoleText").val(json[4]);
		        		 }else{
		        			 $("#userRole" ).val('');
		        			 document.getElementById("userRoleDiv").style.display="none";
		        		 }
	        	}
			 },
	        error: function(e){
	        }
	    });
	}else{
		$("#firstName").attr("disabled", false);
	    $("#firstName").val('');
	    
	    $("#lastName").attr("disabled", false);
	    $("#lastName").val('');
	    
	    $("#phoneNumber").attr("disabled", false);
	    $("#phoneNumber").val('');
	    
	    $("#userRole" ).val('');
		document.getElementById("userRoleDiv").style.display="none";
	   
	}
	 $("#loading").removeAttr("style"); 
}
</script>
 
   <script type="text/javascript">
function validateClientName(){
	var clientName = (document.getElementById("orgName").value).replace(/[{}]/g, "");
	var client = encodeURIComponent(clientName);
	
	var sourceleadBLockedwebsites = '${sourceLeadBlockedSocialDomains}';
	if(client.match(/^[^\s-](.*)*$/) != null){
		$('#clientNameDiv').find('p.jquery_form_error_message').remove();
	if (client != null && client != '') {
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
			type : "GET",
			url : vmsUrl + "/isClientNameValid?"+tokenParamName+"="+tokenValue+"&clientName="+client+"&responderType=Client",
			success : function(data) {
				var result = data;
				if (result == 'true') {
					//duplicate client
					$("#save_client").removeAttr("disabled");
			        $("#save_client").attr("disabled", "disabled");
					$("input#orgName").after( "<p class='jquery_form_error_message'>" + clientName + " is already existed as client</span>");
					document.getElementById("orgName").setAttribute( 'record-exist', 'yes');
					document.getElementById("orgName").setAttribute( 'record-exist-errorMsg', clientName +"  already existed as client");
					$('input#orgName').removeAttr( "record-exist record-exist-errorMsg");
					
				}else if(result == 'sameorg') {
					//duplicate client
					$("#save_client").removeAttr("disabled");
			        $("#save_client").attr("disabled", "disabled");
					$("input#orgName").after( "<p class='jquery_form_error_message'>" + clientName + " is already existed as sameorg</span>");
					document.getElementById("orgName").setAttribute( 'record-exist', 'yes');
					document.getElementById("orgName").setAttribute( 'record-exist-errorMsg', clientName +"  already existed as sameorg");
					$('input#orgName').removeAttr( "record-exist record-exist-errorMsg");
					
				}else {
					//New WebSite class="form-control"
					//$("#save_client").removeAttr("disabled");
					$("#save_client").removeAttr("disabled");
					$('input#orgName').removeAttr("class record-exist record-exist-errormsg");
					$('input#orgName').attr('class','valid form-control');
					$('#clientNameDiv').find('p.jquery_form_error_message').remove();
					$("#save_client").click(function() {
						
					});
				}
				
				$("#loading").removeAttr("style");
			},

		});
	}
  }
}
</script>   





<script type="text/javascript">
function validateOrgName(){
	
	var clientName = (document.getElementById("orgName").value).replace(/[{}]/g, "");
	var client = encodeURIComponent(clientName);
	if(client.match(/^[^\s-](.*)*$/) != null){
		$('#clientNameDiv').find('p.jquery_form_error_message').remove();
	if (client != null && client != '') {
		
		$.ajax({
			type : "GET",
			url : vmsUrl + "/isInvitedClientOrVendorOrgValid?"+tokenParamName+"="+tokenValue+"&orgName="+client,
			success : function(data) {
				var result = data;
				if (result == 'invalid') {
					$("#save_vendor").removeAttr("disabled");
			        $("#save_vendor").attr("disabled", "disabled");
					$("input#orgName").after( "<p class='jquery_form_error_message'>" + clientName + " is invalid</span>");
					document.getElementById("orgName").setAttribute( 'record-exist', 'yes');
					document.getElementById("orgName").setAttribute( 'record-exist-errorMsg', clientName +"  is invalid");
					$('input#orgName').removeAttr( "record-exist record-exist-errorMsg");
					
				}else {
					$("#save_vendor").removeAttr("disabled");
					$('input#orgName').removeAttr("class record-exist record-exist-errormsg");
					$('input#orgName').attr('class','valid form-control');
					$('#clientNameDiv').find('p.jquery_form_error_message').remove();
					$("#save_vendor").click(function() {
					});
					
					validateClientName();
				}
				
			},

		});
	}
  }
}
</script>

 
 <script type="text/javascript">
	function validateWebsite() {
		var website = document.getElementById("webSite").value;
		if(website.match(/^[^\s-](.*)*$/) !== null){
		var sourceleadBLockedwebsites = '${sourceLeadBlockedSocialDomains}';
		$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
		
		if (website != null && website != '') {

			$.ajax({
				type : "GET",
				url : vmsUrl + "/validateClientWebsite?website="+ website + "&sourceleadBLockedwebsites="+ sourceleadBLockedwebsites,
				success : function(data) {
					var result = data;
					if (result == "Website Duplicated" || result == "Invalid Website") {
						//existed website
						$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
						$("input#webSite").after("<p class='jquery_form_error_message'>"+ website+ " is "+result+"</span>");
						document.getElementById("webSite").setAttribute('record-exist', 'yes');
						document.getElementById("webSite").setAttribute('record-exist-errorMsg',website + ' is '+result);
						$('input#webSite').removeAttr( "record-exist record-exist-errormsg");
						
					} else {
						//New WebSite class="form-control"
						$('input#webSite').removeAttr("class record-exist record-exist-errormsg");
						$('input#webSite').attr('class','valid form-control');
						$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
				
					}
				},

			});
		}
	  }
	}
</script>

<script type="text/javascript">
	function flushSaveClient() {
		$("#save_client").removeAttr("disabled");
	}
</script>

<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 
 
<script type="text/javascript">
$(function(){
	       var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
	        
	       $("#owner").chosen();
	      
	       $('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
		  })
});
</script>
