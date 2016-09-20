<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/bootstrap-select.min.js"></script>
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css">
<!-- these files for autocomplete with tagit -->
<script src="resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>




<script type="text/javascript">
function getOrgName(){
	var orgCode = '${SourceLeadWeb_orgCode}';
	var sourceleadUrl = '${sourceleadUrl}';
	
	
	$( "#orgName" ).val( '' );
	$( "#orgLocationId" ).val( '' );
	
	$("#webSite").attr("disabled", false);
	$("#webSite" ).val('');
	
	$("#role" ).val('');
	$("#role").attr("data-validation", "required");
	document.getElementById("roleDiv").style.display="";
	document.getElementById("userRoleDiv").style.display="none";
	
	$.ajax({                           
    	type: "POST",
    	url : sourceleadUrl+"/getOrgs?${_csrf.parameterName}=${_csrf.token}&orgCode="+orgCode,
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
           alert('Ajax Error: ' + e.value);
        }
    });
}
</script>

 <script>
	$(document).ready(function() {
		document.getElementById("client_form").reset();
	});
</script>

<script type="text/javascript">
function getOrgDetails()
{
	var orgName = document.getElementById("orgName").value;
	var orgLocationId = document.getElementById("orgLocationId").value;
	var sourceleadUrl = '${sourceleadUrl}';
	if(orgName && orgLocationId){
   	 $.ajax({
   	    	type: "POST",
   	    	url : sourceleadUrl+"/getOrgDetails?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId,
   	        contentType: "application/json; charset=utf-8",
   	        dataType: "json",
   	        success: function(data) {
   	        	if(data != null && data != ''){
   	        		var json = JSON.parse(data);
			      	if(json[3] != null && json[3] != ''){
			      		$("#webSite").val(json[3]);
			      		$("#webSite").attr("disabled", true);
			      	}
			      	 
   	        	}
   			 },
   	        error: function(e){
   	            alert('Ajax Error: ' + e.value);
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
                   <h4 class="modal-title">Select Client</h4>
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
                           <input type="text" placeholder="client website" id="webSite"  name="webSite" class="form-control" data-validation="validate_Space validate_length length2-100 validate_domain" data-validation-optional="true">
                         </div>
                       </div>
                       
                       <div class="form-group">
			              <label class="col-md-3">Level</label>
			              <div class="col-md-9">
			                  <select class="form-control selectpicker" id="level" name="level">
			                    <option value="Level-1">Level-1</option>
			                    <option value="Level-2">Level-2</option>
			                    <option value="Level-3">Level-3</option>
			                    <option value="Level-4">Level-4</option>
			                    <option selected="selected" value="Level-5">Level-5</option>
			                  </select>
			              </div>
                      </div>
                    </div>
                 </div>
              </div>
             <div class="modal-footer">
              <button  class="btn btn-primary" type="button" onclick="saveClient();">Save</button>
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
            <h2>Client Contact Details</h2>
          </div>
          <div class="pull-right brud-crum">Home &raquo; Vendor &raquo; Vendor Contact Details</div>
        </section>
        <div class="content-area content-new clearfix">
        <div id="loading"></div> 
        <form:form id="client_contact_form" name="client_contact_form" method="post">
          <div class="clearfix form-horizontal inner-content">
            <div class="row">
              <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
          <div class="form-group">
            <label class="col-md-3">Select Client <span class="text-danger">&nbsp;*</span></label>
            <div class="col-md-9">
              <div class="form-icon">
                <select class="select-bg form-control" id="vendorName" name="vendorName" field-name="client name" onchange="validateEmail();getSpocUserDetails();" data-validation="required">
                  <option value="">Select Client</option>
                   <c:forEach var="report" items="${clientContactList}">
                     <c:if test="${report.locationId.id == loginOrgLocId}">  
                    <option value="${report.id},${report.responderLocationId.id}">${report.responderLocationId.org.orgName}</option>
                   </c:if>  
                    <c:if test="${report.responderLocationId.id == loginOrgLocId}"> 
                  	 	<option value="${report.id},${report.locationId.id}">${report.locationId.org.orgName}</option>
                   </c:if>  
                 </c:forEach> 
                </select>
                <i data-target="#selectVendor" data-toggle="modal" class="fa fa-plus btn btn-default formic1-new" onclick="flusAddClint();"></i> </div>
            </div>
          </div>
          </div>
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
          
           <div class="form-group" id="EmailIdDiv">
					<label class="col-md-3" for="Project Manager">Email<span class="text-danger">&nbsp;*</span></label>
					
			        	<div class="col-md-9">
					  <input type="text" class="form-control" id="email" name="email" value="${emailId}" field-name="Email Id" data-validation="required validate_Space validate_length length2-100 validate_email" onblur="validateEmail();getSpocUserDetails();">
	                            <span class="hidden">Valid Email</span>
			     	</div>
					</div>
                <div class="form-group">
	              <label class="col-md-3">First Name<span class="text-danger">&nbsp;*</span></label>
	              <div class="col-md-9">
	                <input class="form-control error" id="firstName" name="firstName" value="" field-name="First Name" data-validation="required validate_Space validate_AlphaNumber validate_length length2-100" type="text">
	              </div>
	            </div>
                
                <div class="form-group">
	              <label class="col-md-3">Last Name</label>
	              <div class="col-md-9">
	                <input type="text" class="form-control" id="lastName" name="lastName" data-validation="validate_Space validate_AlphaNumber validate_length length2-100" data-validation-optional="true">
	              </div>
	            </div>
				
                  <div class="form-group">
	              <label class="col-md-3">Phone Number</label>
	              <div class="col-md-9">
	                <input type="text" class="form-control" name="phoneNumber" id="phoneNumber" data-validation="validate_NumberAlphabet validate_phone validate_length length2-20" data-validation-optional="true">
	              </div>
	            </div>
	            
	             <div class="form-group">
	              <label class="col-md-3">Owner<span class="text-danger">&nbsp;*</span></label>
	              <div class="col-md-9">
	                <select multiple class="chosen-select form-control" name="owner" id="owner" field-name="Owner"  data-validation="required">
	                <c:forEach var="user" items="${userList}">
		                     <option value="${user.id}" ${user.id  == SourceLeadWeb_userId ? 'selected' : ''} >${user.firstName} ${user.lastName}</option>
	                </c:forEach>
	                </select>
	              </div>
	            </div>
                <!-- These are the default sourelead roles we are providing for spoc in client creation -->
              <div class="form-group" id="roleDiv" style="display: block;">
                  <label class="col-md-3">Role <span class="text-danger">&nbsp; *</span></label>
                  <div class="col-md-9">
                         <select class="form-control select-bg" id="role" name="roleType" field-name="Role" data-validation="required">
		                        <option value="">-Select Role-</option>
									<option value="1">Admin</option>
									<option value="3">Approver/Project Manager</option>
									<option value="5">HR Manager</option>
									<option value="8">Recruiter</option>
			              </select>
                  </div>
                </div>
                
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
	                  <option value="New">New</option>
	                  <option value="Negotiation">Negotiation</option>
	                  <option value="Contracted">Contracted</option>
	                </select>
	              </div>
	            </div>
	            <div class="form-group">
                <label class="col-md-3">&nbsp;</label>
                  <div class="col-md-9 checkBox">
                      <label><input type="checkbox" value="Y"
							name="isMailSent" checked="checked">Send Invitation</label>
                  </div>
                </div>
                
                </div>
                </div>
                </div>
            <div class="form-footer clearfix">
              <div class="col-lg-12">
                <div class="pull-right">
                  <input type="button" value="Save" id="save_vendor_contact" onclick="saveClientContact();" class="btn btn-primary">
                  <input type="button" value="Cancel" onClick="javascript:location.href = '<c:url value='/clientDashboard'/>'" class="btn btn-default ml">
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


<script type="text/javascript">
	function saveClientContact() {
		var vendorName = document.getElementById("vendorName").value;
		var orgLocationId = vendorName.split(',');
		var orgRelationId = orgLocationId[0];
		alert("orgRelationId"+orgRelationId);
		var validationSettings = {
				errorMessagePosition : 'element',
				borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd'
			};
			if ($('#client_contact_form').validate(false, validationSettings)) {
				var frm = $('#client_contact_form').serializeFormJSON();
				var con = JSON.stringify(frm);
				con = con.replace(/[{}]/g, "");
		        var jsonData = encodeURIComponent(con);
				var sourceleadUrl = '${sourceleadUrl}';
				$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
				$.ajax({
							type : "POST",
							url : sourceleadUrl+ "/saveClinentAndVendorContact?${_csrf.parameterName}=${_csrf.token}&json="+ jsonData+"&orgRelationId="+orgRelationId+"&responderType=Client",
							success : function(result) {
								$("#loading").removeAttr("style");
								if (result == 'vendorsList') {
									location.replace(sourceleadUrl
											+ "/clientDashboard");
								}
							},
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
	var email = document.getElementById("email").value;
	var sourceleadUrl = '${sourceleadUrl}';
	if(email != null){
		$.ajax({
			type : "POST",
			url : sourceleadUrl + "/validateClientOrVendorEmail?${_csrf.parameterName}=${_csrf.token}&email="+email,
			success : function(data) {
				var result = data;
				if(result == 'Invalid Email') {
					$('#EmailIdDiv').find('p.jquery_form_error_message').remove();
					$("input#email").after( "<p class='jquery_form_error_message'>" + email + " should not your login email</span>");
					document.getElementById("email").setAttribute( 'record-exist', 'yes');
					document.getElementById("email").setAttribute( 'record-exist-errorMsg', email +"  should not your login email");
					
				}else if(result == 'Valid Email') {
					$('input#email').removeAttr("class record-exist record-exist-errormsg");
					$('input#email').attr('class','valid form-control');
					$('#EmailIdDiv').find('p.jquery_form_error_message').remove();
					
				}
			},

		});
	}
}
</script>


<script type="text/javascript">
 function validateClientEmail() {
	 var EmailId = document.getElementById("EmailId").value; 
	 $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(EmailId != null && EmailId != ""){
		/*  $("#save_client").attr("disabled", "disabled");
         $("#save_client" ).addClass('button').val('Processing..'); */
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getClientEmailVerification?${_csrf.parameterName}=${_csrf.token}&EmailId="+EmailId, 
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
		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		if ($('#client_form').validate(false, validationSettings)) {  
			var frm = $('#client_form').serializeFormJSON();
			var orgLocationId = document.getElementById("orgLocationId").value;
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			  //It will escape all the special characters
	        var jsonData = encodeURIComponent(con);
			var sourceleadUrl = '${sourceleadUrl}';
			$("#save_client").attr("disabled", "disabled");
			$("#save_client").addClass('button').val('Processing..');
			$.ajax({
						type : "POST",
						url : sourceleadUrl + "/saveClient?${_csrf.parameterName}=${_csrf.token}&json="+ jsonData+"&orgLocationId="+orgLocationId+"&responderType=Client",
						success : function(data) {
							location.replace(sourceleadUrl+ "/addClientContact");
						},
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
	var sourceleadUrl = '${sourceleadUrl}';
	var sourceleadBLockedwebsites = '${sourceLeadBlockedSocialDomains}';
	if(clientName.match(/^[^\s-](.*)*$/) != null){
		$('#clientNameDiv').find('p.jquery_form_error_message').remove();
	if (clientName != null && clientName != '') {
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/ws/isClientNameValid?${_csrf.parameterName}=${_csrf.token}&clientName="+clientName+"&responderType=Client",
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
	var email = document.getElementById("email").value;
	var sourceleadUrl = '${sourceleadUrl}';
	var vendorName = document.getElementById("vendorName").value;
	var orgLocationId = vendorName.split(',');
	if(email){
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/getSpocUserDetails?${_csrf.parameterName}=${_csrf.token}&email="+email+"&orgLocationId="+orgLocationId[1],
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
	        			 $("#firstName").val('');
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
	        			
	        			$("#role").removeAttr("data-validation");
	        			$("#role" ).val('');
        			    document.getElementById("roleDiv").style.display="none";
   	        			document.getElementById("userRoleDiv").style.display="";
   	        			$("#userRole").val(json[3]);
   	        			$("#userRoleText").val(json[4]);
	        		 }else{
	        			 $("#role").attr("data-validation", "required");
	        			 $("#userRole" ).val('');
	        			 document.getElementById("roleDiv").style.display="";
	        			 document.getElementById("userRoleDiv").style.display="none";
	        		 }
			      	 
	        	}
			 },
	        error: function(e){
	            alert('Ajax Error: ' + e.value);
	        }
	    });
	}else{
		$("#firstName").attr("disabled", false);
	    $("#firstName").val('');
	    
	    $("#lastName").attr("disabled", false);
	    $("#lastName").val('');
	    
	    $("#phoneNumber").attr("disabled", false);
	    $("#phoneNumber").val('');
	    
	    $("#role").attr("data-validation", "required");
	    $("#userRole" ).val('');
	    document.getElementById("roleDiv").style.display="";
		document.getElementById("userRoleDiv").style.display="none";
	   
	}
	
}
</script>
