<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- -email parser popup statrs here  -->
  <form id="email_parser_form"  name="email_parser_form" method="post">
<div class="clearfix">
		<div class="popupHead">Email Parsing<i class="closePop pull-right popClose" onclick="closeEmailNotifications();"></i></div>
			<div class="popupContent">		
			<div class="clearfix mb" id="emailNotify">
                <label class="noPad">Email <b class="valStar">*</b></label>
                <input class="form-control" value="" name="emailId" id="parserEmailId"  field-name="Email Id" data-validation="required validate_Space validate_length length2-100 validate_email" placeholder="Enter email" type="text" onkeyup = "validateParserEmail();" onblur = "validateParserEmail();">
            </div>
          <div class="clearfix mb" id="passwordsnotify">
             <label class="noPad">Password <b class="valStar">*</b></label>
         <input class="form-control" value="" name="EmailPassword" id="parserPasswordId"   field-name="Password" data-validation="required validate_Space" placeholder="Enter password" type="password">
          </div>
          
          <div class="clearfix mb" id="popnotify">
             <label class="noPad">IMAP Mail Server name <b class="valStar">*</b></label>
            <input class="form-control" value="" id="parserServerName" name="serverName" data-validation="required validate_Space"  field-name="Receive Server" placeholder="e.g.secure.emailsrvr.com" type="text">
          </div>
          
          <div class="clearfix mb" id="portnotify">
             <label class="noPad">Port <b class="valStar">*</b></label>
           <input class="form-control" value="" id="parserPortId" name="portNumber" data-validation="required validate_Space" field-name="Receive Port" placeholder="123" type="text">
          </div>
          
          <div class="clearfix mb">
             <label class="noPad">Encryption Type<b class="valStar">*</b></label>
            <select id="parserEncryptId" name="encryptType" field-name="Encryption Type" data-validation="required" class="sel">
				<option value='' selected>Select</option>
				<option value='TLS'>TLS</option>
				<option value='SSL'>SSL</option>
			</select>
          </div>
          
          <div class="clearfix mb">
             <label class="noPad">Type<b class="valStar">*</b></label>
           <select id="usageType" name="usageType" field-name="Type" data-validation="required" class="sel">
				<option value=''  selected>Select</option>
				<option value='resume_parsing' >Resume Parsing</option>
			</select>
          </div>
          
           <div class="clearfix bg-info">
             <label class="modal-body"><strong class="text-danger paddingRight">Note:</strong>To configure the Gmail account, enable the  <a href="https://mail.google.com/mail/u/0/#settings/fwdandpop" target="_blank">IMAP</a> and <a href="https://www.google.com/settings/security/lesssecureapps" target="_blank">Allow less secure</a></label>
           </div>
           
           <div class="clearfix bg-warning emailTerms">
             <p><strong class="text-danger paddingRight">Warning:</strong>All the <b>inbox</b> emails are moved to newly created <b>processed</b> folder.
             <span>
             	<label><input type="checkbox" id="agrementChk" onclick ="acceptAgreement();"/> I agree and accept terms and conditions mentioned above.</label>
             </span>
             </p>
           </div>
          
			</div>
			<div class="popupFoot text-right">
				<input type="hidden" name="emailUUID" id="parserEmailUUID" value="">
					<button type="button" id="save_email_parser" class="slBtn md colorBtn" onclick="saveEmailParsers();">Save</button>
					<button type="button" class="slBtn md popClose" onclick="closeEmailNotifications();">Cancel</button>
			</div>
		</div>
	</form>	
		<script>
		
function acceptAgreement(){
	if($("#agrementChk").is(":checked")){
		$("#save_email_parser").removeClass("disabled");
	}else{
		$("#save_email_parser").addClass("disabled");
	}
}
function saveEmailParsers(){
	if ($('#email_parser_form').validate(false, validationSettings)) {
		
			//$("#save").attr("disabled", "disabled");
			//$("#save_title").addClass('button').val('Processing..');
		//var formData = $($('#user_manuals_form')[0]).serialize();
		var formData = getFormData1("email_parser_form");
		$( "#save_email_parser" ).addClass('button').val('Processing..');
		$("#ConfigLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:10000;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	    
		$.ajax({
			 type : "POST",
			 data: formData,
		      processData: false,
		      contentType:false, 
			  url : sourceleadUrl+"/saveEmailParser?"+tokenParamName+"="+tokenValue,
			  	success : function(result) {
			  		if(result=="success"){
			  			$("#ConfigLoading").removeAttr("style");
			  			alert("Mail configured Successfully!")
			  			closeEmailNotifications();
			  			getParserEmailList();
			  		}else if(result=="failure"){
			  			$("#ConfigLoading").removeAttr("style");
			  			//closeEmailNotifications();
			  			alert("Please check your configuration details.")
			  			//$("#msgConfigDiv").html("Configured Mail is wrong.Please update the correct Mail");
			  		}
		    		
			},
		});
		
	}else {
		return false;
	}

}

</script>
<script>
$(document).ready(function () {
	allowOnlyConfigResumeEmailParserForOrg();
	getParserEmailList();
});

</script>
<script>
function allowOnlyConfigResumeEmailParserForOrg(){
	// Dont allow more than one email for each Org, if one email configured disable the "Add" button to add another email.
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/getResumeParserEmailCountForOrg?${_csrf.parameterName}=${_csrf.token}", 	   
		    success: function(response) {
		    	if(response >=1){
		    		// add button should be disable when count >=1
		    		$('#parserEmailAdd').addClass("disabled");
		    	}else{
		    		$('#parserEmailAdd').removeClass("disabled");
		    	}
		    },
     });
	
}
function getParserEmailList(){
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/getConfigParserMailList?${_csrf.parameterName}=${_csrf.token}", 	   
		    success: function(response) {
		    	var result = "";
		    	
		    	result  = result + '	 <div class="clearfix project_wrap_table project_tabs_inn_table_a">';
		    	result  = result + '	<table>								';
		    	result  = result + '<thead>									';
		    	result  = result + '<tr>									';
		    	result  = result + '<th>Email</th>							';
		    	result  = result + '<th>Type</th>				';
		    	result  = result + '<th>Actions</th>						';
		    	result  = result + '</tr>									';
		    	result  = result + '</thead>								';
		    	result  = result + '<tbody>									';
		    	 for (var i = 0; i < response.data.length; i++) { 
					 var mailrow = response.data[i];
				result  = result + '	 <tr>                                                                                                ';
     			result  = result + '	<td><div class="tbldata-dotdot">'+mailrow.emailId+'</div></td>                                ';
     			result  = result + '	<td><div class="tbldata-dotdot">'+mailrow.usageType+'</div></td>                                ';
     			result  = result + '	<td>                                                                                                 ';
     			result  = result + '	<div class="tbldata-dotdot">                                                                        ';
     			result  = result + '	<ul class="list-unstyled list-inline noMar">                                                               ';
     			result  = result + '	<li>                                                                                                 ';
				result  = result + '	<label class="switch" style="position:relative; top:5px;">                                                                          ';
				result  = result + '	<input onclick="changeDefaultParserEmailConfig(this, \''+mailrow.email_UUID+'\')" type="checkbox" '+(mailrow.isActive =="Y" ? 'checked' : '')+'/>                                                                    ';
				result  = result + '	 <span class="slider round"></span>                                                                  ';
				result  = result + '	</label>                                                                                             ';
     			result  = result + '	</li>                                                                                                ';
     		    result  = result + '	<li><a class="fa fa-pencil pull-left ml" title="Edit" onclick="editEmailParsing(\''+mailrow.email_UUID+'\');"></a> </li>';
     			result  = result + '	<li><a class="glyphicon glyphicon-trash text-danger" title="Delete" onclick="deleteEmail('+mailrow.id+');"></a></li>'; 
     			result  = result + '	</ul>                                                                                                ';
     			result  = result + '	</div>                                                                                               ';
     			result  = result + '	</td>                                                                                                ';
     			result  = result + '	</tr>                                                                                                ';
		    	 }
		    	
		    	 $("#emailparsingList").empty();
	        	 $("#emailparsingList").append(result);
	        	 allowOnlyConfigResumeEmailParserForOrg();// disables the add buttion for adding anohter email to the resume parsing. 
		    },
     });
	
}
</script>
<script type="text/javascript">
function editEmailParsing(emailUUID){
	$.ajax({	
		type : "POST",
		url : sourceleadUrl+ "/editEmailParsing?"+tokenParamName+"="+tokenValue+"&emailUUID="+emailUUID,
		success : function(data) {
			addEmailparsingPopUp();
			acceptAgreement();
			$('#parserEmailId').val(data.emailId);
			$('#parserPasswordId').val(data.emailPassword);
			$('#parserServerName').val(data.serverName);
			$('#parserPortId').val(data.portNumber);
			
			$('#parserEncryptId').removeAttr("selected");
			$('#parserEncryptId').val(data.encryptionType);
			$('#usageType').removeAttr("selected");
			$('#usageType').val(data.usageType);
			
			/* $('#parserEncryptId').removeAttr("selected");
			$('#parserEncryptId option[value='+data.encryptionType+']').attr("selected", "selected");
			$('#usageType').removeAttr("selected");
			$('#usageType option[value='+data.usageType+']').attr("selected", "selected"); */
			$('#parserEmailUUID').val(data.email_UUID); // using for upate the record. email
		}
	 }); 
}
</script>
<script>
function deleteEmail(emailId){ 
	var result = "";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p style='text-align: center;'>.Do you want to delete?</p>";
	result = result + ' <div id="reasonForEmailDiv"/>';
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Delete" onclick="deleteParserEmail('+emailId+')"><input class="u_can_color" type="button"  value="Cancel" onclick="cancelDeleteEMail()"></b>';
	result = result +"	</div>";
	$("#deleteParserEmailDiv").append(result);
	 $(".mask").show();
	$("#remove_user_pop").slideDown(200);
}
function cancelDeleteEMail(){
	$("#deleteParserEmailDiv").empty();
	 $(".mask").hide();
}
function deleteParserEmail(emailId){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
		type : "POST",
		url : sourceleadUrl+ "/deleteParserEmail?${_csrf.parameterName}=${_csrf.token}&emailId="+emailId,
		
		success : function(response) {
			
			if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
    		    showError(response.responseMessage);
    	  	} else if (response.response === "AWKWARD") {
    		    location.href = "/errorPage";
    		} else if (response.response === "SUCCESS") {
    			 $("#deleteParserEmailDiv").empty();
 			     $(".mask").hide();
 				getParserEmailList();
 				allowOnlyConfigResumeEmailParserForOrg();
    		}else if (response.indexOf('Sign In') !== -1) {
    			location.replace(sourceleadUrl);
    		} else {
    		    showError("Operation failed, please try again");
    		}
			
		}
	});
}

function validateParserEmail(){
	
	var emailId = $("#parserEmailId").val();
	var emailUUID = $("#parserEmailUUID").val();
	$.ajax({
	            type: "POST",
	            url: sourceleadUrl + "/validateParserEmail?"+ tokenParamName + "=" + tokenValue +"&parserEmailId=" + emailId+"&emailUUID="+emailUUID,
	            success: function (data) {
	            	if (data && data === true) {
			        		
	            		if ($('#emailNotify').find('p.jquery_form_error_message').length === 0)
	            			$("#parserEmailId").after("<p class='jquery_form_error_message'>" + emailId + " Already Exists</span>");
	            		$(".colorBtn").addClass("disableBtn");
	            		
			        	}else{
			        		
			        	$('#emailNotify').find('p.jquery_form_error_message').remove();
	            		 $(".colorBtn").removeClass("disableBtn");	            		
	            	}     
	                }
	 	        });
	 	   
	 	
}

function changeDefaultParserEmailConfig(ele, id) {
    $.post(sourceleadUrl+"/changeDefaultParserEmailConfig", {uuid: id, activeFlag: $(ele).is(":checked")}, function(data) {
    	getParserEmailList();
    });
}
</script>


