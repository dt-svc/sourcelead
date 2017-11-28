<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">
    var staticTime='${staticTime}'
    
    	var tokenParamName = "${_csrf.parameterName}";
    var tokenValue = "${_csrf.token}";
 </script>

<script src="resources/js/formvalidator.js?staticTime=${staticTime}"></script>
<!--   Popup Code  -->
		<form:form id="capn_add_sender" name="capn_add_sender" method="post">
		<div class="popupHead">
			<h2>Add Sender</h2><a onclick="senderClose();" href="javascript:void(0)" class="fa fa-close"></a>
		</div>
		<div id="loading"></div>
		<div class="popupMid clearfix">
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-3 padding_left">Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-9 padding_right">
                	<input type="text" class="form-control" id="cpnSenderName"name="cpnSenderName" value="${sender.fromName}" data-validation="required validate_length length1-250" field-name="Name">
				<input type="hidden" value="${sender.id}" name="senderId" id="senderId" >
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfixclearfix">
				<label class="col-md-3 padding_left">From Mail<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-9 padding_right">
                	<input type="text" class="form-control" id="cpnSenderFromMail"name="cpnSenderFromMail" value="${sender.fromMail}" data-validation="required validate_length length1-250 validate_email" field-name="From Mail">
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix" >
				<label class="col-md-3 padding_left">Replay Mail<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-9 padding_right">
                	<input type="text" class="form-control" id="cpnReplyMail" name="cpnReplyMail" value="${sender.replyTo}" data-validation="required validate_length length1-250 validate_email" field-name="Reply Mail">
				</div>
			</div>
		<div class="form-group" id="nickNameDiv">
			<div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-3 padding_left">Nickname<span
					class="text-danger">&nbsp;*</span></label>
				<div class="col-md-9 padding_right">
					<input type="text" class="form-control" id="cpnNickName"
						name="cpnNickName" value="${sender.nickName}"
						data-validation="required validate_length length1-250"
						field-name="Nick Name" onblur="validateNickName(${sender.id});">
				</div>
			</div>
		</div>
		<div class="popupFoot">
            <input value="Save" class="btn btn-primary" id="saveCampaignSender" name="" type="button">
            <input onclick="senderClose();" value="Cancel" class="btn btn-default ml" type="button">
          </div>		
		</div>
		</form:form>
		
		
<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 
		

<!-- <script type="text/javascript">
function validateNickName(){
	var nickName = document.getElementById("nickNameDiv").value;
	if(nickName != null){
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/validateClientOrVendorEmail?"+tokenParamName+"="+tokenValue+"&email="+email,
			success : function(data) {
				var result = data;
				if(result == 'Invalid Email') {
					$('#nickNameDiv').find('p.jquery_form_error_message').remove();
					$("input#cpnNickName").after( "<p class='jquery_form_error_message'>You already have a sender identity with the same nickname.</span>");
					document.getElementById("cpnNickName").setAttribute( 'record-exist', 'yes');
					document.getElementById("cpnNickName").setAttribute( 'record-exist-errorMsg',"You already have a sender identity with the same nickname.");
					
				}else if(result == 'Valid Email') {
					$('input#cpnNickName').removeAttr("class record-exist record-exist-errormsg");
					$('input#cpnNickName').attr('class','valid form-control');
					$('#nickNameDiv').find('p.jquery_form_error_message').remove();
					
				}
			},

		});
	}
}
</script> -->

<!--   Popup Code  -->  