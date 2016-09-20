<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
	result = result+'              <div class="col-lg-9">';
	result = result+'                <input type="text" id="orgName" name="orgName" class="form-control" value="" field-name="Client Name" data-validation="required validate_Space validate_length length2-250">';
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
	result = result+'            <div class="form-group">';
	result = result+'              <label class="col-lg-3">Role<span class="text-danger"> *</span></label>';
	result = result+'              <div class="col-lg-9">';
	result = result+'              <select class="chosen-select form-control select-bg" id="roleType" name="roleType" field-name="Client Role" data-validation="required">';
	result = result+'                	<option value="">-Select Role-</option>';
	result = result+'					<option value="1">Admin</option>';
	result = result+'					<option value="3">Approver/Project Manager</option>';
	result = result+'					<option value="5">HR Manager</option>';
	result = result+'					<option value="8">Recruiter</option>';
	result = result+'              </select>';
	result = result+'              </div>';
	result = result+'            </div>';
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
};
</script>


<script>
function addClientContactPopUp() {
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
	result = result+'        	<input type="text" class="form-control" id="vendorName" name="vendorName" data-validation-optional="true" style="display: none;">';
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
	result = result+'     <div class="form-group" id="contactEmailIdDiv">';
	result = result+'      <label class="col-lg-3">Email<span class="text-danger"> *</span></label>';
	result = result+'       <div class="col-lg-9">';
	result = result+'        <input type="text" class="form-control" id="email" name="email" field-name="Email Id" onKeyUp="validateUserEmail(contact);" data-validation="required validate_Space validate_email">';
	result = result+'     	</div>';
	result = result+'     </div>';
	result = result+'     <div class="form-group">';
	result = result+'        <label class="col-md-3">Owner<span class="text-danger">&nbsp;*</span></label>';
	result = result+'       <div class="col-md-9">';
	result = result+'        <select multiple class="chosen-select form-control" name="owner" id="owner" field-name="Owner"  data-validation="required">';
							  <c:forEach var="user" items="${spocOwnersList}"> 
						      			result = result +'<option value="${user.id}" ${user.id  == SourceLeadWeb_userId ? "selected" : ""} >${user.firstName} ${user.lastName}</option>';
						      </c:forEach> 
	result = result+'        </select>';
	result = result+'       </div>';
	result = result+'    </div>';
	result = result+'     <div class="form-group">';
	result = result+'      <label class="col-lg-3">Role<span class="text-danger"> *</span></label>';
	result = result+'       <div class="col-lg-9">';
	result = result+'       <select class="chosen-select form-control select-bg" id="role" name="roleType" field-name="Role" data-validation="required">';
	result = result+'           <option value="">-Select Role-</option>';
	result = result+'			<option value="1">Admin</option>';
	result = result+'			<option value="3">Approver/Project Manager</option>';
	result = result+'			<option value="5">HR Manager</option>';
	result = result+'			<option value="8">Recruiter</option>';
	result = result+'       </select>';
	result = result+'      </div>';
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
	result = result+' 	<input type="button" value="Save" class="btn btn-primary" id="save_clientContact" onclick="saveClientContact();"> ';
	result = result+'	<input data-dismiss="modal" type="button" value="Cancel" class="btn btn-default">';
	result = result+'  </div>';
	result = result+'</form>';
	result = result+'</div>';
	result = result+'</div>';
	$("#addContact").empty();
	$("#addContact").append(result);
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
							$("#addContactPlus").hide();
							$("#clientSubmissionForm").empty();
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

<script type="text/javascript">
function getContactList(local){
		var valueArray = local.value.split(",");
		if(valueArray[0] != ""){
			clientContactList(valueArray[0],valueArray[1]);
			$("#vendorName").attr('value', local.value);
			$("#addContactPlus").show();
		}else{
			$("#addContactPlus").hide();
		}
}
</script>

<script type="text/javascript">
function getClientSubmissionTemplates(local){
		var valueArray = local.value.split(",");
		if("" != valueArray[0] && "" != valueArray[1]){
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
					
					output = output + "<option value='"+opt.id+"'>"
							+opt.spocName+ "</option>";
				}
				$("#selectContact").empty();
				$("#selectContact").append(output);
				$('#selectContact').trigger("chosen:updated");
	        }
		});
	}
</script>


<script type="text/javascript">
	function saveClientContact() {
		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		if ($('#clientContacts_form').validate(false, validationSettings)) {
			var frm = $('#clientContacts_form').serializeFormJSON();
			var orgLocationId = document.getElementById("orgLocationId").value;
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			  //It will escape all the special characters
	        var jsonData = encodeURIComponent(con);
			var sourceleadUrl = '${sourceleadUrl}';
			$("#save_clientContact").attr("disabled", "disabled");
			/* $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;'); */
	 		$.ajax({
						type : "POST",
						url : sourceleadUrl+ "/saveClientContact?${_csrf.parameterName}=${_csrf.token}&json="+ jsonData+"&orgRelationId=''",
						success : function(data) {
							var json = JSON.parse(data);
							var output = "";
							output = output
									+ "<option value=''>-- Select Contact --</option>";
							for (var i = 0; i < json.length; i++) {
								var opt = json[i];
								
								output = output + "<option value='"+opt.id+"'>"
								+opt.spocName+ "</option>";
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

<script type="text/javascript">
 function validateUserEmail(emailType) {
	 alert(emailType);
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
 
<script>
function getIsClientVal(){
    if(document.getElementById('isClientJob').checked) {
        $("#isClientJob").val('Y');
    }else{
        $("#isClientJob").val('N');
    }
}
</script>
