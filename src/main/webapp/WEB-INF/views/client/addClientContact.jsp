<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script type="text/javascript">
var vmsUrl ='${SourceLeadWeb_vmsUrl}';
</script>

<script type="text/javascript">
function getOrgName(){
	var orgCode = '${SourceLeadWeb_orgCode}';
	var vmsUrl = '${SourceLeadWeb_vmsUrl}';
	
	
	$( "#orgName" ).val( '' );
	$( "#orgLocationId" ).val( '' );
	
	$("#webSite").attr("disabled", false);
	$("#webSite" ).val('');
	
	/* $("#role" ).val('');
	$("#role").attr("data-validation", "required");
	document.getElementById("roleDiv").style.display=""; */
	document.getElementById("userRoleDiv").style.display="none";
	
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
                appendTo: "#org_dialog",
                focus: function( event, ui ) {
                       $( "#orgName" ).val( ui.item.label );
                       $( "#orgLocationId" ).val( ui.item.value );
                       return false;
                   },
                select: function( event, ui ) {
                    return false;
                }  
             })
        },
        error: function(e){
        }
    });
}
</script>

 <script>
	$(document).ready(function() {
		document.getElementById("client_form").reset();
		document.getElementById("emailId").value="";
		document.getElementById("firstName").value="";
		document.getElementById("lastName").value="";
		document.getElementById("phoneNumber").value="";
		<c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
		  var sectionId = '${section.id}';
		  getCustomDependentField(sectionId);
		 </c:forEach>
		 
		
	});
</script>

<script type="text/javascript">
function getOrgDetails()
{
	var orgName = document.getElementById("orgName").value;
	var orgLocationId = document.getElementById("orgLocationId").value;
	var vmsUrl = '${SourceLeadWeb_vmsUrl}';
	if(orgName && orgLocationId){
   	 $.ajax({
   	    	type: "POST",
   	    	url : vmsUrl+"/getOrgDetails?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId,
   	        contentType: "application/json; charset=utf-8",
   	        dataType: "json",
   	        success: function(data) {
   	        	if(data != null && data != ''){
   	        		var json = JSON.parse(data);
			      	if(json['website'] != null && json['website'] != ''){
			      		$("#webSite").val(json['website']);
			      		$("#webSite").attr("disabled", true);
			      	}
			      	 
   	        	}
   			 },
   	        error: function(e){
   	        }
   	    });
 	getSpocUserDetails();
	}
	
}
</script>

 <!-- Save client Model popup  Start-->
         <div role="dialog" id="selectVendor" class="modal fade">
         <form:form id="client_form" name="client_form" method="post">
             <div class="modal-dialog">
               <div class="modal-content">
                 <div class="modal-header">
                   <button data-dismiss="modal" class="close" type="button"><i class="fa fa-close"></i></button>
                   <h4 class="modal-title">Client Name</h4>
                 </div>
                 <div class="modal-body clearfix">
                   <div class="form-horizontal custom-form clearfix">
                     <div class="col-xs-12 col-md-12 col-lg-12">
                       <div class="form-group" id="org_dialog">
                         <label class="col-lg-3">Client Name <span class="text-danger">&nbsp;*</span></label>
                         <div class="col-lg-9" id="clientNameDiv">
                           <input type="text" class="form-control" id="orgName" name="orgName" value="${orgName}" onclick="getOrgName();"  onblur="getOrgDetails();validateClientName();" field-name="Client Name" data-validation="required validate_Space validate_length length2-250" value="">
                           <input type="hidden" id="orgLocationId" />
                         </div>
                       </div>
                       <div class="form-group">
                         <label class="col-lg-3">Website</label>
                         <div class="col-lg-9">
                           <input type="text" placeholder="client website" id="webSite"  name="webSite" class="form-control" data-validation="validate_Space validate_domain validate_length length2-100 " data-validation-optional="true">
                         </div>
                       </div>
                       
                       <div class="form-group">
			              <label class="col-md-3">Tier</label>
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
                    </div>
                 </div>
              </div>
             <div class="modal-footer">
              <button  class="btn btn-primary" type="button"  id="save_client" onclick="saveClient();">Save</button>
              <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
             </div>
          </div>
        </div>
        </form:form>
      </div>
 <!-- Save client Model popup  End-->             
              
 <!-- Content Area -->
      <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Add Client Contact</h2>
          </div>
              <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo;<a href="${SourceLeadWeb_vmsUrl}/clientDashboard"> Clients List</a> &raquo; Add Client Contact</div>
        </section>
        <div class="content-area content-new clearfix">
        <div id="loading"></div> 
        <form:form id="client_contact_form" name="client_contact_form" method="post">
          <div class="clearfix form-horizontal inner-content">
              <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
          <div class="form-group">
            <label class="col-md-3">Client Name <span class="text-danger">&nbsp;*</span><i class='fa fa-info-circle' title='Select the exisitng client name from the list. The new contact will be added to this client name'></i></label>
            <div class="col-md-9 chooRadius">
            <div class="input-group col-md-10 pull-left">
                <select class="chosen-select form-control" id="vendorName" name="vendorName" field-name="client name" onchange="validateEmail();getSpocUserDetails();getRolesList();" data-validation="required">
                  <option value="">Select Client</option>
                   <c:forEach var="report" items="${clientContactList}">
                     <c:if test="${report.locationId.id == loginOrgLocId}">  
                    <option ${report.id == cid ? 'selected':''}  value="${report.id},${report.responderLocationId.id}">${report.responderLocationId.orgLocationName}</option>
                   </c:if>  
                    <c:if test="${report.responderLocationId.id == loginOrgLocId}"> 
                  	 	<option  ${report.id == cid ? 'selected':''} value="${report.id},${report.locationId.id}">${report.locationId.orgLocationName}</option>
                   </c:if>  
                 </c:forEach> 
                </select>
                 <span class="input-group-addon cursorPointer bg-blue" onclick="refreshClientList()"><i class="fa fa-refresh "></i></span>
                <!-- Temporarily Commented.. because contact user doesnt exists, so issue occurs in enrolled org list -->
                <!-- <i data-target="#selectVendor" data-toggle="modal" class="fa fa-plus btn btn-default formic1-new" onclick="flusAddClint();" style="display: none"></i> -->
            </div>
            <sec:authorize access="hasAnyRole('ROLE_CREATE_VENDOR')"> 
			 <span class="col-md-2 noPad clearfix"><a href="${SourceLeadWeb_vmsUrl}/client" target="_blank"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a> </span>
			</sec:authorize>
			</div>            
            	</div>  
          </div>
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
          
           <div class="form-group" id="EmailIdDiv">
					<label class="col-md-3" for="Project Manager">Email<span class="text-danger">&nbsp;*</span></label>
			       <div class="col-md-9">
			       <div class="input-group errorInptgroup">
					  <input type="text" class="form-control" id="emailId" name="email" value="${emailId}" field-name="Email" data-validation="required validate_Space validate_length length2-100 validate_email" onblur="validateEmail();getSpocUserDetails();getOwnersList();">
	                   <span class="hidden">Valid Email</span>
	                   <span id="emailStatusIconDiv" class="input-group-addon">
	                    	<i class="fa fa-check text-muted" style="color:#f6f6f6"></i>
	                  	</span>
			     	</div>
			     	</div>
			</div>
                <div class="form-group">
	              <label class="col-md-3">First Name<span class="text-danger">&nbsp;*</span></label>
	              <div class="col-md-9">
	                <input class="form-control error" id="firstName" name="firstName" value="" field-name="First Name" data-validation="required validate_Space validate_AlphaNumber validate_length length1-100" type="text">
	              </div>
	            </div>
                
                <div class="form-group">
	              <label class="col-md-3">Last Name</label>
	              <div class="col-md-9">
	                <input type="text" class="form-control" id="lastName" name="lastName" data-validation="validate_Space validate_Alphanumeric validate_length length1-100" data-validation-optional="true">
	              </div>
	            </div>
				
                  <div class="form-group">
	              <label class="col-md-3">Phone Number</label>
	              <div class="col-md-9">
	               <div class="row">
	                <div class="col-md-6 paddingRight">
	                <input type="text" class="form-control" name="phoneNumber" id="phoneNumber" data-validation="validate_phone validate_length length9-20" data-validation-optional="true">
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
	              <label class="col-md-3">Owner Name<span class="text-danger">&nbsp;*</span></label>
	              <div class="col-md-9">
	                <select multiple data-placeholder="Select Owner" class="chosen-select form-control" name="owner" id="owner" field-name="Owner Name"  data-validation="required">
	                <c:forEach var="user" items="${userList}">
		                       <option value="${user.id}" ${user.id  == SourceLeadWeb_userId ? 'selected' : ''} >${user.fullName}</option>
	                </c:forEach>
	                </select>
	              </div>
	            </div>
                <!-- These are the default sourelead roles we are providing for spoc in client creation -->
              <!-- <div class="form-group" id="roleDiv" style="display: block;">
                  <label class="col-md-3">Role <span class="text-danger">&nbsp; *</span></label>
                  <div class="col-md-9">
                         <select class="form-control select-bg" id="role" name="roleType" field-name="Role" data-validation="required">
		                        <option value="">-Select Role-</option>
			              </select>
                  </div>
                </div> -->
                
                <div class="form-group" id="userRoleDiv" style="display: none;">
					<label class="col-md-3">Role <span class="text-danger">&nbsp; *</span></label>
					<div class="col-md-9">
					<input type="text" class="form-control" id="userRoleText"  readonly="readonly">
					<input type="hidden" id="userRole" name="roleTypeId" value="" />
					
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
	            
	            <div class="clearfix mb"></div>
	            <c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
	               <c:if test="${sectionOrder.index == 1}">
	                  <div id="customDiv${section.id}"></div>
	               </c:if>
	            </c:forEach>
	            
	            <div class="form-group">
                <label class="col-md-3">&nbsp;</label>
                  <div class="col-md-9 checkBox">
                      <label><input type="checkbox" value="Y"
							name="isMailSent">Send Invitation</label>
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
                
                </div>
                </div>

            <div class="form-footer clearfix">
              <div class="col-lg-12">
                <div class="pull-right">
                  <input type="button" value="Save" id="save_vendor_contact" onclick="saveClientContact();" class="btn btn-primary">
                  <input type="button" value="Cancel" onClick="javascript:window.location.href = '${SourceLeadWeb_vmsUrl}/clientDashboard'" class="btn btn-default ml">
                </div>
              </div>
            </div>
          
      <!-- Content Area Ends--> 
      </form:form>
	  </div>
      </section>

<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->


<script type="text/javascript">

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

	function saveClientContact() {
		var vendorName = document.getElementById("vendorName").value;
		var orgLocationId = vendorName.split(',');
		var orgRelationId = orgLocationId[0];
		var validationSettings = {
				errorMessagePosition : 'element',
				borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd'
			};
		
			if ($('#client_contact_form').validate(false, validationSettings)) {
		        var formData = $($('#client_contact_form')[0]).serialize();
				var vmsUrl = '${SourceLeadWeb_vmsUrl}';
				$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
				$.post(vmsUrl+"/saveClinentAndVendorContact?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId+"&responderType=Client", formData, function(data) {
					location.replace(vmsUrl+ "/clientDashboard");
				});
				return true;
			} else {
				return false;
			}
		}
		$('body').on('blur', '#client_contact_form', function() {
			$("#client_contact_form").showHelpOnFocus();
			$("#client_contact_form").validateOnBlur(false, validationSettings);
		}); 
	
	
</script>


<script type="text/javascript">
function validateEmail(){
	var vmsUrl = '${SourceLeadWeb_vmsUrl}';
	var email = document.getElementById("emailId").value;
	var vendorName = document.getElementById("vendorName").value;
	if(email != null  && email != ''){
		$.ajax({
			type : "POST",
			url : vmsUrl + "/validateClientOrVendorEmails?"+tokenParamName+"="+tokenValue+"&email="+email,
			success : function(data) {
				var result = data;
				if(result == 'Invalid Email') {
					$('#EmailIdDiv').find('p.jquery_form_error_message').remove();
					$("input#emailId").after( "<p class='jquery_form_error_message'>Email  should not your org users email</span>");
					//document.getElementById("emailId").setAttribute( 'record-exist', 'yes');
					//document.getElementById("emailId").setAttribute( 'record-exist-errorMsg', "Email should not your org users email");
					
				}else if(result == 'Valid Email') {
					$('#EmailIdDiv').find('p.jquery_form_error_message').remove();
					$('input#emailId').removeAttr("class record-exist record-exist-errormsg");
					$('input#emailId').attr('class','valid form-control');
					 validateEmailForExistenceUniversally(emailId, "input#emailId");
					
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
 function validateClientEmail() {
	 var EmailId = document.getElementById("EmailId").value; 
	 $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
	 var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	 if(EmailId != null && EmailId != ""){
		/*  $("#save_client").attr("disabled", "disabled");
         $("#save_client" ).addClass('button').val('Processing..'); */
		 $.ajax({
		    	type: "POST",
		        url: vmsUrl+"/getClientEmailVerification?"+tokenParamName+"="+tokenValue+"&EmailId="+EmailId, 
		        success: function(data) {
		        	if(data == 'true'){
		        		  $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
						  $("input#EmailId").after("<p class='jquery_form_error_message'>"+EmailId+" is Already Existed</span>");
						  document.getElementById("EmailId").setAttribute('record-exist','yes');
						  document.getElementById("EmailId").setAttribute('record-exist-errorMsg',EmailId+' is Already Existed'); 
		        	}
		        	else{
		        		 $('input#EmailId').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#EmailIdDiv').find('p.jquery_form_error_message').remove(); 
		        		 $("#save_vendor" ).addClass('button').val('Save');
                         $("#save_vendor").removeAttr("disabled");
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
</script> 


<script type="text/javascript">
	function saveClient() { 
		var vmsUrl = '${SourceLeadWeb_vmsUrl}';
		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		if ($('#client_form').validate(false, validationSettings)) {  
			var orgLocationId = document.getElementById("orgLocationId").value;
			var formData = $($('#client_form')[0]).serialize();
			var vmsUrl = '${SourceLeadWeb_vmsUrl}';
					$.post(vmsUrl+"/saveClient?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&responderType=Client", formData, function(data) {
						location.replace(vmsUrl+ "/addClientContact");
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
function validateClientName(){
	var clientName = document.getElementById("orgName").value;
	var vmsUrl = '${SourceLeadWeb_vmsUrl}';
	var sourceleadBLockedwebsites = '${sourceLeadBlockedSocialDomains}';
	if(clientName.match(/^[^\s-](.*)*$/) != null){
		$('#clientNameDiv').find('p.jquery_form_error_message').remove();
	if (clientName != null && clientName != '') {
	/* 	$("#save_client").attr("disabled", "disabled"); */
		$.ajax({
			type : "GET",
			url : vmsUrl + "/isClientNameValid?"+tokenParamName+"="+tokenValue+"&clientName="+clientName+"&responderType=Client",
			success : function(data) {
				var result = data;
				if (result == true) {
					//duplicate client
					$("input#orgName").after( "<p class='jquery_form_error_message'>" + clientName + " is already existed as client</span>");
					document.getElementById("orgName").setAttribute( 'record-exist', 'yes');
					document.getElementById("orgName").setAttribute( 'record-exist-errorMsg', clientName +"  already existed as client");
					/* <input type ="hidden" name ="isDuplicateClient" value = "isDuplicateClient"/> */
					
				}else {
					$('input#orgName').removeAttr("class record-exist record-exist-errormsg");
					$('input#orgName').attr('class','valid form-control');
					$('#clientNameDiv').find('p.jquery_form_error_message').remove();
				}
			},

		});
	}
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

<script>
function flusAddClint(){
	$("#orgName").val('');
	$("#webSite").val('');
}
</script>


<script type="text/javascript">
function getSpocUserDetails(){
	var email = document.getElementById("emailId").value;
	var vmsUrl = '${SourceLeadWeb_vmsUrl}';
	var vendorName = document.getElementById("vendorName").value;
	var orgLocationId = vendorName.split(',');
	if(email){
		$.ajax({
	    	type: "POST",
	    	url : vmsUrl+"/getSpocUserDetails?"+tokenParamName+"="+tokenValue+"&email="+email+"&orgLocationId="+orgLocationId[1],
	        contentType: "application/json; charset=utf-8",
	        dataType: "json",
	        success: function(data) {
	        	if(data != null && data != ''){
	        		var json = JSON.parse(data);
	        		
	        		 if(json[0] != null && json[0] != ''){
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
	        			    //$("#lastName").val('');
	        		 }
		        		
	        		 if(json[2] != null && json[2] != ''){
		        			$("#phoneNumber").attr("disabled", true);
		        		    $("#phoneNumber").val(json[2]);
	        		 }else{
	        			    $("#phoneNumber").attr("disabled", false);
	        			    //$("#phoneNumber").val('');
	        		 }
	       
	        		 
	        		 if(json[3] != null && json[3] != ''){
	        			
	        			/* $("#role").removeAttr("data-validation");
	        			$("#role" ).val('');
        			    document.getElementById("roleDiv").style.display="none"; */
   	        			document.getElementById("userRoleDiv").style.display="";
   	        			$("#userRole").val(json[3]);
   	        			$("#userRoleText").val(json[4]);
	        		 }else{
	        			/*  $("#role").attr("data-validation", "required");*/
	        			 $("#userRole" ).val('');
	        			 /* document.getElementById("roleDiv").style.display=""; */
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
	    
	   /*  $("#role").attr("data-validation", "required");*/
	    $("#userRole" ).val('');
	   /*  document.getElementById("roleDiv").style.display="";  */
		document.getElementById("userRoleDiv").style.display="none";
	   
	}
	
}
</script>



<script type="text/javascript">
function getRolesList(){
	var vendorName = document.getElementById("vendorName").value;
	var array = vendorName.split(',');
	var orgLocationId = array[1];
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	
	if(vendorName){
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
 function getOwners() {
	 var email = document.getElementById("emailId").value; 
	 var vendorName = document.getElementById("vendorName").value;
	 var orgLocationId = vendorName.split(',');
	 var orgRelationId = orgLocationId[0];
	 var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	 var SourceLeadWeb_userId= '${SourceLeadWeb_userId}';
	
	 if(email != null && email != "" && email != 'undefined'){
		 $.ajax({
		    	type: "POST",
		        url: vmsUrl+"/getAllOwners?"+tokenParamName+"="+tokenValue+"&email="+email+"&orgRelationId="+orgRelationId+"&type=client",  
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	var flag=false;
		        	for (var i=0; i<json.length; i++)
	        		{
	        		var check = json[i];
		        		if(check.id == SourceLeadWeb_userId){
		        			flag=true;
		        		}
	        		}
		        	if(flag){
				        	for (var i=0; i<json.length; i++)
							{
					        	var opt = json[i];
					        	
					        		if(opt.id == SourceLeadWeb_userId){
					        			output = output + "<option value='" +opt.id+ "' selected>"+opt.firstName+" "+opt.lastName+"</option>";
					        		}
					        		else{
					        			output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"</option>";
					        		}
			                }
		        	}
		        	if(!flag){
	                	for (var j=0; j<json.length; j++)
						{
				        	var opt1 = json[j];
				        	output = output + "<option value='" +opt1.id+ "'>"+opt1.firstName+" "+opt1.lastName+"</option>";
						}
           		 	}
		        	$("#owner").empty();
			        $("#owner").append(output);
			        $('#owner').trigger("chosen:updated");
		        },
		    });
	 }/* else{
		 var output = "";
     	 $("#owner").empty();
	     $("#owner").append(output);
	     $('#owner').trigger("chosen:updated");
	 } */
	
 }
</script>
<script type="text/javascript">
 function getOwnersList() {
	 var email = document.getElementById("emailId").value; 
	 var SourceLeadWeb_userId= '${SourceLeadWeb_userId}';
	 var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	 if(email != null && email != "" && email != 'undefined'){
		 $.ajax({
		    	type: "POST",
		        url: vmsUrl+"/getOwnersList?"+tokenParamName+"="+tokenValue+"&email="+email+"&type=Client", 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	for (var i=0; i<json.length; i++)
					{
			        		 var opt = json[i];
		        		if(opt.id == SourceLeadWeb_userId){
		        			if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefined'){
		        				output = output + "<option value='" +opt.id+ "' selected>"+opt.firstName+" "+opt.lastName+"</option>";		        				
		        			}
		        			else{
		        				output = output + "<option value='" +opt.id+ "' selected>"+opt.firstName+"</option>";		        				
		        			}
		        		}
		        		else{
		        			if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefined'){
		        				output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"</option>";		        				
		        			}
		        			else{
		        				output = output + "<option value='" +opt.id+ "'>"+opt.firstName+"</option>";		        				
		        			}
		        		}
					}
		        	$("#owner").empty();
			        $("#owner").append(output);
			        $('#owner').trigger("chosen:updated");
		        },
		    });
	 }else{
		 var output = "";
     	 output = output + "<option value=''>-- Select Owner --</option>"; 
     	 $("#owner").empty();
	     $("#owner").append(output);
	     $('#owner').trigger("chosen:updated");
	 }
	
 }
</script>


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
				})
</script> 
<script>
function refreshClientList(){
		 var vmsUrl ='${SourceLeadWeb_vmsUrl}';
		 var selectedOrgId = '${contract.orgRelations.id}';
		 $.ajax({
	      type : "POST",
	       url: vmsUrl+"/refreshRequirementClientList",       
	       success: function(result) {
	    	   	  if (result.response === "SESSION_TIMEDOUT") {
	  				 location.replace(vmsUrl);
	              } else if (result.response === "AWKWARD") {
	                  location.href = "/errorPage";
	              } else if (result.response === "SUCCESS") {
	           	    if(result.data != null && result.data != "" && result.data != 'undefined'){
	           	    	var json = result.data;
				        	var output = "";
				        	output = output + "<option value=''>Select Client</option>"; 
				        	for (var i=0; i<json.length; i++)
							{
				        		var opt = json[i];
				        		output = output + "<option value='"+opt.id+","+opt.locationID+"' >"+opt.orgName+"</option>";
							}
	           	    }
	           	 	$("#vendorName").empty();
			        $("#vendorName").append(output);
			        $('#vendorName').trigger("chosen:updated");
	              } else if (result.indexOf('Sign In') !== -1) {
						location.replace(vmsUrl);
				  } else {
	                  showError("Operation failed, please try again");
	              }
	        }
		 });
}
</script>
<script>
function getCustomDependentField(sectionId){
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
