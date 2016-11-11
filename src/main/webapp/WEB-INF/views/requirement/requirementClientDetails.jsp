<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 
     @author : Venkat Kesav
     this script is for add client popup window
     1.usage     : onclick
--%>
<script>
function addClientPopUp() {
	var result = "";
	result = result+'<div class="modal-dialog">';
	result = result+'<div class="modal-content">';
	result = result+'  <div class="modal-header">';
	result = result+'    <button data-dismiss="modal" class="close" type="button"><i class="fa fa-close"></i></button>';
	result = result+'    <h4 class="modal-title">Add Client Details</h4>';
	result = result+'  </div>';
	result = result+'  <form id="clientDetails_form" name="clientDetails_form" method="GET">';
	result = result+'      <div class="modal-body clearfix">';
	result = result+'        <div class="form-horizontal custom-form clearfix">';
	result = result+'          <div class="col-xs-12 col-md-12 col-lg-12">';
	result = result+'            <div class="form-group">';
	result = result+'              <label class="col-lg-3">Client Name<span class="text-danger">*</span></label>';
	result = result+'              <div class="col-lg-9" id="clientNameDiv" >';
	result = result+'                <input type="text" id="orgName" name="orgName" class="form-control" value="" field-name="Client Name" data-validation="required validate_Space validate_length length2-250" onblur="validateClientName();">';
	result = result+'                <input type="hidden" id="orgLocationId" />';
	result = result+'              </div>';
	result = result+'            </div>';
	result = result+'            <div class="form-group">';
	result = result+'              <label class="col-lg-3">Website</label>';
	result = result+'              <div class="col-lg-9">';
	result = result+'                <input type="text" id="webSite" name="webSite" class="form-control" value="" field-name="Client Website" data-validation="validate_Space validate_domain validate_length length2-100" data-validation-optional="true">';
	result = result+'              </div>';
	result = result+'            </div>';
	result = result+'            <div class="form-group">';
	result = result+'              <label class="col-lg-3">First Name<span class="text-danger"> *</span></label>';
	result = result+'              <div class="col-lg-9">';
	result = result+'                <input type="text" id="firstName" name="firstName" class="form-control" value="" field-name="First Name" data-validation="required validate_Space validate_AlphaNumber validate_length length2-100">';
	result = result+'              </div>';
	result = result+'            </div>';
	result = result+'            <div class="form-group">';
	result = result+'              <label class="col-lg-3">Last Name</label>';
	result = result+'              <div class="col-lg-9">';
	result = result+'                <input type="text" id="lastName" name="lastName" class="form-control" value="" data-validation="validate_Space validate_AlphaNumber validate_length length2-100" data-validation-optional="true">';
	result = result+'              </div>';
	result = result+'            </div>';
	result = result+'             <div class="form-group" id="clientEmailIdDiv">';
	result = result+'              <label class="col-lg-3">Email<span class="text-danger"> *</span></label>';
	result = result+'              <div class="col-lg-9">';
	result = result+'                <input type="text" id="email" name="email" class="form-control" value="" field-name="Client Email" data-validation="required validate_Space validate_email" onKeyUp="validateUserEmail("client");">';
	result = result+'              </div>';
	result = result+'            </div>';
	result = result+'			<div class="form-group">';
	result = result+'				<label class="col-md-3">Owner<span class="text-danger">&nbsp;*</span></label>';
	result = result+'					<div class="col-md-9">';
	result = result+'						<select multiple  class="chosen-select form-control"   name="owner" id="c1owner" field-name="Owner"  data-validation="required">';
											<c:forEach var="user" items="${spocOwnersList}">
														result = result+'  <option value="${user.id}" ${user.id  == SourceLeadWeb_userId ? "selected" : ""} >${user.firstName} ${user.lastName}</option>';
											</c:forEach>
	result = result+' 						</select>';
	result = result+' 					</div>';
	result = result+'			</div>';
	result = result+'            <div class="form-group">';
	result = result+'              <label class="col-lg-3">Role<span class="text-danger"> *</span></label>';
	result = result+'              <div class="col-lg-9">';
	result = result+'              <select class="chosen-select form-control select-bg" id="roleType" name="roleType" field-name="Client Role" data-validation="required">';
	result = result+'                	<option value="">-Select Role-</option>';
	result = result+'					<option value="1">Admin</option>';
	result = result+'					<option value="3">Approver/Project Manager</option>';
	result = result+'					<option value="5">HR Manager</option>';
	result = result+'					<option value="7">Sales Manager</option>';
	result = result+'					<option value="8">Recruiter</option>';
	result = result+'              </select>';
	result = result+'              </div>';
	result = result+'            </div>';
	result = result+'    <div class="form-group">';
	result = result+'      <label class="col-lg-3"></label>';
	result = result+'      <div class="col-lg-9">';
	result = result+'      <label class="check-box checkBox">';
	result = result+'       <input type="checkbox" value="Y" name="isMailSent" checked="checked"> Send Invitation';
	result = result+'      </label>';
	result = result+'       </div>';
	result = result+'          </div>';
	result = result+'        </div>';
	result = result+'      </div>';
	result = result+'      <div class="modal-footer">';
	result = result+'      	<input type="button" value="Save" class="btn btn-primary" id="save_client" onclick="saveClient();"> ';
	result = result+'			<input data-dismiss="modal" type="button" value="Cancel" class="btn btn-default">';
	result = result+'      </div>';
	result = result+'   </form>';
	result = result+'</div>';
	result = result+'</div>';
	$("#addClient").empty();
	$("#addClient").append(result);
	
	$("#c1owner").trigger("chosen:updated");
	$(function(){
	    var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
	    $("#c1owner").chosen();
	    $('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
		  })
	});
}
</script>


<%-- 
     @author : Venkat Kesav
     this script is for add client contact popup
     1.usage     : onclick
     2.parameters : orgRelationID,clientLocationId.
--%>
<script>
function addClientContactPopUp(orgRelationID,clientLocationId) {
	var result = "";
	result = result+'<div class="modal-dialog">';
	result = result+'<div class="modal-content">';
	result = result+'<div class="modal-header">';
	result = result+'  <button data-dismiss="modal" class="close" type="button"><i class="fa fa-close"></i></button>';
	result = result+'  <h4 class="modal-title">Add Client Contact</h4>';
	result = result+'</div>';
	result = result+'<form id="clientContacts_form" name="clientContacts_form" method="POST">';
	result = result+'    <div class="modal-body clearfix">';
	result = result+'      <div class="form-horizontal custom-form clearfix">';
	result = result+'        <div class="col-xs-12 col-md-12 col-lg-12">';
	result = result+'        	<input type="text" class="form-control" value="'+orgRelationID+','+clientLocationId+'" id="vendorName" name="vendorName" data-validation-optional="true" style="display: none;">';
	
	result = result+'     <div class="form-group" id="contactEmailIdDiv">';
	result = result+'      <label class="col-lg-3">Email<span class="text-danger"> *</span></label>';
	result = result+'       <div class="col-lg-9">';
	result = result+'        <input type="text" class="form-control" id="email" name="email" field-name="Email Id" onblur="validateEmail();getSpocUserDetails();" data-validation="required validate_Space validate_email">';
	result = result+'			<span class="hidden">Valid Email</span>';
	result = result+'     	</div>';
	result = result+'     </div>';
	
	result = result+'         <div class="form-group">';
	result = result+'           <label class="col-lg-3">First Name<span class="text-danger"> *</span></label>';
	result = result+'          <div class="col-lg-9">';
	result = result+'          <input type="text" class="form-control" id="firstName" name="firstName" value="" field-name="First Name" data-validation="required validate_Space validate_AlphaNumber validate_length length2-100">';
	result = result+'        </div>';
	result = result+'     </div>';
	result = result+'     <div class="form-group">';
	result = result+'       <label class="col-lg-3">Last Name</label>';
	result = result+'      <div class="col-lg-9">';
	result = result+'       <input type="text" class="form-control" value="" id="lastName" name="lastName" data-validation="validate_Space validate_AlphaNumber validate_length length2-100" data-validation-optional="true">';
	result = result+'      </div>';
	result = result+'    </div>';
	
	result = result+'     <div class="form-group">';
	result = result+'        <label class="col-md-3">Owner<span class="text-danger">&nbsp;*</span></label>';
	result = result+'       <div class="col-md-9">';
	result = result+'        <select multiple class="chosen-select form-control" name="owner" id="c2owner" field-name="Owner"  data-validation="required">';
							  <c:forEach var="user" items="${spocOwnersList}"> 
						      			result = result +'<option value="${user.id}" ${user.id  == SourceLeadWeb_userId ? "selected" : ""} >${user.firstName} ${user.lastName}</option>';
						      </c:forEach> 
	result = result+'        </select>';
	result = result+'       </div>';
	result = result+'    </div>';
	result = result+'     <div class="form-group" id="roleDiv" style="display: block;">';
	result = result+'      <label class="col-lg-3">Role<span class="text-danger"> *</span></label>';
	result = result+'       <div class="col-lg-9">';
	result = result+'       <select class="chosen-select form-control select-bg" id="role" name="roleType" field-name="Role" data-validation="required">';
	result = result+'           <option value="">-Select Role-</option>';
	result = result+'			<option value="1">Admin</option>';
	result = result+'			<option value="3">Approver/Project Manager</option>';
	result = result+'			<option value="5">HR Manager</option>';
	result = result+'			<option value="7">Sales Manager</option>';
	result = result+'			<option value="8">Recruiter</option>';
	result = result+'       </select>';
	result = result+'      </div>';
	result = result+'     </div>';
	
	result = result+'     <div class="form-group" id="userRoleDiv" style="display: none;">';
	result = result+'     	<label class="col-md-3">Role <span class="text-danger">&nbsp; *</span></label>';
	result = result+'     	<div class="col-md-9">';
	result = result+'     	<input type="text" class="form-control" id="userRoleText"  readonly="readonly">';
	result = result+'     	<input type="hidden" id="userRole" name="roleTypeId" value="" />';
	result = result+'        </div>';
	result = result+'     </div>';
	
	result = result+'    <div class="form-group">';
	result = result+'      <label class="col-lg-3"></label>';
	result = result+'      <div class="col-lg-9">';
	result = result+'      <label class="check-box checkBox">';
	result = result+'       <input type="checkbox" value="Y" name="isMailSent" checked="checked"> Send Invitation';
	result = result+'      </label>';
	result = result+'       </div>';
	result = result+'     </div>';
	result = result+'   </div>';
	result = result+'   </div>';
	result = result+'  </div>';
	result = result+'  <div class="modal-footer">';
	result = result+' 	<input type="button" value="Save" class="btn btn-primary" id="save_clientContact" onclick="saveClientContact('+orgRelationID+');"> ';
	result = result+'	<input data-dismiss="modal" type="button" value="Cancel" class="btn btn-default">';
	result = result+'  </div>';
	result = result+'</form>';
	result = result+'</div>';
	result = result+'</div>';
	$("#addContact").empty();
	$("#addContact").append(result);
	
	$("#c2owner").trigger("chosen:updated");
	$(function(){
	    var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
	    $("#c2owner").chosen();
	    $('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
		  })
	});
}
</script>


<%-- 
     @author : Venkat Kesav
     this script is for save Client
     1.usage     : onclick
     2.ajax url implementation in OrgRelationsRestController.
--%>
<script type="text/javascript">
	function saveClient() {
		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		if ($('#clientDetails_form').validate(false, validationSettings)) {
			var frm = $('#clientDetails_form').serializeFormJSON();
			var orgLocationId = document.getElementById("orgLocationId").value;
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			  //It will escape all the special characters
	        var jsonData = encodeURIComponent(con);
			var sourceleadUrl = '${sourceleadUrl}';
			$("#save_client").attr("disabled", "disabled");
			/* $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;'); */
	 		$.ajax({
						type : "GET",
						url : sourceleadUrl
								+ "/saveClientFromProject?json="+jsonData+"&orgLocationId="+orgLocationId+"&responderType=Client",
						success : function(data) {
							var json = JSON.parse(data);
							var output = "";
							output = output
									+ "<option value=''>-- Select Client --</option>";
							for (var i = 0; i < json.length; i++) {
								var opt = json[i];
								
								output = output + "<option value='" +opt.id+","+opt.responderLocationId.id+ "'>"
										+ opt.orgName + "</option>";
							}
							$("#addClientId").empty();
							$("#addClientId").append(output);
							$('#addClientId').trigger("chosen:updated");
							$('#addClient').modal('hide');
							$("#selectContact").empty();
							$("#selectContact").append("<option value=''>-- Select Contact --</option>");
							$('#selectContact').trigger("chosen:updated");
							$("#addContactPlus").hide();
							$("#clientSubmissionForm").empty();
							$("#clientSubmissionForm").append("<option value=''>-- Select Submission Form --</option>");
							$('#clientSubmissionForm').trigger("chosen:updated");
						},
					});
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#clientDetails_form', function() {
		$("#clientDetails_form").showHelpOnFocus();
		$("#clientDetails_form").validateOnBlur(false, validationSettings);
	});
</script>

<%-- 
     @author : Venkat Kesav
     this script is for Contact List
     1.usage     : onclick
     2.parameters :local
--%>
<script type="text/javascript">
function getContactList(local){
		var valueArray = local.value.split(",");
		if(valueArray[0] != ""){
			clientContactList(valueArray[0],valueArray[1]);
			$("#addContactPlus").show();
			$("#addContactPlus").attr("onclick", "addClientContactPopUp("+valueArray[0]+","+valueArray[1]+");");
			//   $("#vendorName").attr('value', local.value);
		}else{
			$("#addContactPlus").hide();
			$("#selectContact").empty();
			$("#selectContact").append("<option value=''>-- Select Contact --</option>");
			$('#selectContact').trigger("chosen:updated");
			$("#clientSubmissionForm").empty();
			$("#clientSubmissionForm").append("<option value=''>-- Select Submission Form --</option>");
			$('#clientSubmissionForm').trigger("chosen:updated");
		}
	}
</script>

<%-- 
     @author : Venkat Kesav
     this script is for Client Submission Templates
     1.usage     : onclick
     2.parameters :local
     3.ajax url implementation in ClientRestController.
--%>
<script type="text/javascript">
function getClientSubmissionTemplates(local){
		if("" != local.value){
			var valueArray = local.value.split(",");
			var clientLocationId = valueArray[1];
			var sourceleadUrl = '${sourceleadUrl}';
			$.ajax({
		    	type: "POST",
		    	url : sourceleadUrl+"/getSubmissionTemplatesList?${_csrf.parameterName}=${_csrf.token}&clientLocationId="+clientLocationId, 
		        success: function(data) {
		        	var json = JSON.parse(data);
					var output = "";
					output = output
							+ "<option value=''>-- Select Submission Form --</option>";
					for (var i = 0; i < json.length; i++) {
						var opt = json[i];
						
						output = output + "<option value='"+opt.id+"'>"
								+opt.templateName+ "</option>";
					}
					$("#clientSubmissionForm").empty();
					$("#clientSubmissionForm").append(output);
					$('#clientSubmissionForm').trigger("chosen:updated");
		        }
			});
		}
	}
</script>

<%-- 
     @author : Venkat Kesav
     this script is for clientContactList
     1.usage     : onclick
     2.parameters :orgRelationID, clientLocationId
     3.ajax url implementation in ClientRestController.
--%>
<script type="text/javascript">
	function clientContactList(orgRelationID,clientLocationId) {
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/getOrgSpocsList?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationID, 
	        success: function(data) {
	        	var json = JSON.parse(data);
				var output = "";
				output = output
						+ "<option value=''>-- Select Contact --</option>";
				for (var i = 0; i < json.length; i++) {
					var opt = json[i];
					if("" != opt.spocStatus && opt.spocStatus != "In-Active"){
						output = output + "<option value='"+opt.id+"'>"+opt.spocName+ " ("+opt.ownerName+")</option>";
					}
				}
				$("#selectContact").empty();
				$("#selectContact").append(output);
				$('#selectContact').trigger("chosen:updated");
	        }
		});
	}
</script>

<%-- 
     @author : Venkat Kesav
     this script is for save Client Contact
     1.usage     : onclick
     2.ajax url implementation in RequirementRestController.
--%>
<script type="text/javascript">
	function saveClientContact(orgRelationId) {
		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		if ($('#clientContacts_form').validate(false, validationSettings)) {
			var frm = $('#clientContacts_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			  //It will escape all the special characters
	        var jsonData = encodeURIComponent(con);
			var sourceleadUrl = '${sourceleadUrl}';
			$("#save_clientContact").attr("disabled", "disabled");
			/* $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;'); */
	 		$.ajax({
						type : "POST",
						url : sourceleadUrl+ "/saveClientContact?${_csrf.parameterName}=${_csrf.token}&json="+ jsonData+"&orgRelationId="+orgRelationId,
						success : function(data) {
							var json = JSON.parse(data);
							var output = "";
							output = output
									+ "<option value=''>-- Select Contact --</option>";
							for (var i = 0; i < json.length; i++) {
								var opt = json[i];
								
								output = output + "<option value='"+opt.id+"'>"
								+opt.spocName+" ("+opt.ownerName+")</option>";
							}
							$("#selectContact").empty();
							$("#selectContact").append(output);
							$('#selectContact').trigger("chosen:updated");
							$('#addContact').modal('hide');
							 

						},
					});
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#clientContacts_form', function() {
		$("#clientContacts_form").showHelpOnFocus();
		$("#clientContacts_form").validateOnBlur(false, validationSettings);
	});
</script>

<%-- 
     @author : Venkat Kesav
     this script is for validate User Email
     1.usage     : onclick
     2.parameters : emailType
     3.ajax url implementation in UserRestController.
--%>
<script type="text/javascript">
 function validateUserEmail(emailType) {
	 var EmailId = document.getElementById("email").value; 
	 if(emailType == "client"){
		 $('#clientEmailIdDiv').find('p.jquery_form_error_message').remove();
	 }
	 if(emailType == "contact"){
		 $('#contactEmailIdDiv').find('p.jquery_form_error_message').remove();
	 }
	 
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(EmailId != null && EmailId != ""){
		 if(emailType == "client"){
			 $("#save_client").attr("disabled", "disabled");
		 }
		 if(emailType == "contact"){
			 $("#save_clientContact").attr("disabled", "disabled");
		 }
		 
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateUserEmail?${_csrf.parameterName}=${_csrf.token}&EmailId="+EmailId, 
		        success: function(data) {
		        	if(data == 'true'){
		        		if(emailType == "client"){
		        			 $('#clientEmailIdDiv').find('p.jquery_form_error_message').remove();
		        		 }
		        		 if(emailType == "contact"){
		        			 $('#contactEmailIdDiv').find('p.jquery_form_error_message').remove();
		        		 }
						  $("input#email").after("<p class='jquery_form_error_message'>"+EmailId+" is Already Existed</span>");
						  document.getElementById("email").setAttribute('record-exist','yes');
						  document.getElementById("email").setAttribute('record-exist-errorMsg',EmailId+' is Already Existed'); 
		        	}
		        	else{
		        		 $('input#email').removeAttr( "record-exist record-exist-errormsg");
		        		 if(emailType == "client"){
		        			 $('#clientEmailIdDiv').find('p.jquery_form_error_message').remove();
		        			 $("#save_client" ).addClass('us-btn').val('Save');
		        			 $("#save_client").removeAttr("disabled");
		        		 }
		        		 if(emailType == "contact"){
		        			 $('#contactEmailIdDiv').find('p.jquery_form_error_message').remove();
		        			 $("#save_clientContact" ).addClass('us-btn').val('Save');
		        			 $("#save_clientContact").removeAttr("disabled");
		        		 }
		        	}
		        },
		    });
	 }
 }
 </script>
 
 
 
 <script type="text/javascript">
function validateClientName(){
	var clientName = document.getElementById("orgName").value;
	var sourceleadUrl = '${sourceleadUrl}';
	var sourceleadBLockedwebsites = '${sourceLeadBlockedSocialDomains}';
	if(clientName.match(/^[^\s-](.*)*$/) != null){
		$('#clientNameDiv').find('p.jquery_form_error_message').remove();
	if (clientName != null && clientName != '') {
		//$("#save_client").attr("disabled", "disabled");
		// $( "#save_client" ).addClass('button').val('Processing..');
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
					
				}else {
					//New WebSite class="form-control"
					//$("#save_client").removeAttr("disabled");
					$('input#orgName').removeAttr("class record-exist record-exist-errormsg");
					$('input#orgName').attr('class','valid form-control');
					$('#clientNameDiv').find('p.jquery_form_error_message').remove();
					$("#save_client").click(function() {
						
					});
				}
			},

		});
	}
  }
}
</script>   


<%-- 
     @author : Venkat Kesav
     this script is for getIsClientVal
     1.usage     : onclick
--%>
<script>
function getIsClientVal(){
    if(document.getElementById('isClientJob').checked) {
        $("#isClientJob").val('Y');
    }else{
        $("#isClientJob").val('N');
    }
}


</script>

<!-- ============================================================================ -->



<script type="text/javascript">
function validateEmail(){
	 getOwners();
	var email = document.getElementById("email").value;
	var sourceleadUrl = '${sourceleadUrl}';
	if(email != null){
		$.ajax({
			type : "POST",
			url : sourceleadUrl + "/validateClientOrVendorEmail?${_csrf.parameterName}=${_csrf.token}&email="+email,
			success : function(data) {
				var result = data;
				if(result == 'Invalid Email') {
					$('#contactEmailIdDiv').find('p.jquery_form_error_message').remove();
					$("input#email").after( "<p class='jquery_form_error_message'>" + email + " should not your org users email</span>");
					document.getElementById("email").setAttribute( 'record-exist', 'yes');
					document.getElementById("email").setAttribute( 'record-exist-errorMsg', email +"  should not your org users email");
					
				}else if(result == 'Valid Email') {
					$('input#email').removeAttr("class record-exist record-exist-errormsg");
					$('input#email').attr('class','valid form-control');
					$('#contactEmailIdDiv').find('p.jquery_form_error_message').remove();
					
				}
			},

		});
	}
}
</script>

<script type="text/javascript">
 function getOwners() {
	 var email = document.getElementById("email").value; 
	 var vendorName = document.getElementById("vendorName").value;
	 var orgLocationId = vendorName.split(',');
	 var orgRelationId = orgLocationId[0];
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(email != null && email != "" && email != 'undefined'){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getAllOwners?${_csrf.parameterName}=${_csrf.token}&email="+email+"&orgRelationId="+orgRelationId, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		if(i == 0){
			        			output = output + "<option value='" +opt.id+ "' selected>"+opt.firstName+" "+opt.lastName+"</option>";
			        		}else{
			        			output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"</option>";
			        		}
					}
		        	$("#c2owner").empty();
			        $("#c2owner").append(output);
			        $('#c2owner').trigger("chosen:updated");
		        },
		    });
	 }
	
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
	        }
	    });
	}else{
		$("#firstName").attr("disabled", false);
	    $("#firstName").val('');
	    
	    $("#lastName").attr("disabled", false);
	    $("#lastName").val('');
	    
	    $("#role").attr("data-validation", "required");
	    $("#userRole" ).val('');
	    document.getElementById("roleDiv").style.display="";
		document.getElementById("userRoleDiv").style.display="none";
	   
	}
	
}
</script>
