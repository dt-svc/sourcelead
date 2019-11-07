<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<link rel="stylesheet" href="/resources/css/bootstrap-multiselect.css?staticTime=${staticTime}" type="text/css">
<link rel="stylesheet" href="/resources/css/bootstrap3-wysihtml5.min.css?staticTime=${staticTime}" type="text/css">

<!--  New Popup  -->
<div class="newPopOut">
	<div class="newPopWrap">
		<form:form id="email_settings_form" name="email_settings_form" method="post">
			<div class="popupHead">
				<span>Mail Configuration</span>
				<i class="closePop pull-right popClose" onclick="closeConfigurePopup();"></i>
			</div>
			<input type="hidden" name="id" value="${emailAccount.id}"/>
			<div class="popupContent clearfix">
				<div class="clearfix mb">
					<label class="noPad">Email <b class="valStar">*</b></label>
					<div class="clearfix">
						<input class="form-control error" name="email" id="email"
							field-name="Email Id" value="${emailAccount.email}"
							data-validation="required validate_Space validate_length length2-100 validate_email"
							placeholder="Enter Email" type="text">
					</div>
				</div>
				<div class="clearfix mb">
					<label class="noPad">Password <b class="valStar">*</b></label>
					<div class="clearfix">
						<input class="form-control error" name="password" value="${emailAccount.password}"
							field-name="Password" data-validation="required validate_Space"
							placeholder="Enter Password" type="password">
					</div>
				</div>
				<div class="clearfix row">
					<h3>
						Receiving emails <span>(IMAP)</span>
					</h3>
					<div class="col-md-9 mb10">
						<label class="noPad">Incoming/Receiving Server
						<b class="valStar"> *</b></label>
						<div class="clearfix">
							<input class="form-control" value="${emailAccount.incomingServer}"
								name="incomingServer"
								data-validation="required validate_Space"
								field-name="Incoming server" placeholder="example ${incomingServer}"
								type="text">
						</div>
					</div>
					<div class="col-md-3 paddingLeft mb10">
						<label class="noPad">Port <b class="valStar"> *</b></label>
						<div class="clearfix">
							<input class="form-control" value="${emailAccount.incomingPort}"
								name="incomingPort" data-validation="required validate_Space"
								field-name="port" placeholder="123" type="text">
						</div>
					</div>

					<div class="col-md-12 clearfix mb10">
						<label class="noPad"><input type="checkbox" name="incomingSSL" ${emailAccount.incomingSSL == true ? "checked='checked'" : ''}/>
						<span>This server requires a secure connection (SSL)</span></label>
					</div>
				</div>

				<div class="clearfix row">
					<h3>
						Sending emails <span>(SMTP)</span>
					</h3>
					<div class="col-md-9 mb10">
						<label class="noPad">Outgoing/Sending Server
						<b class="valStar"> *</b></label>
						<div class="clearfix">
							<input class="form-control" value="${emailAccount.outgoingServer}"
								name="outgoingServer" data-validation="required validate_Space"
								field-name="Outgoing server" placeholder="example ${emailAccount.outgoingServer}"
								type="text">
						</div>
					</div>
					<div class="col-md-3 paddingLeft mb10">
						<label class="noPad">Port <b class="valStar"> *</b></label>
						<div class="clearfix">
							<input class="form-control" value="${emailAccount.outgoingPort}"
								name="outgoingPort" data-validation="required validate_Space"
								field-name="port" placeholder="123" type="text">
						</div>
					</div>

					<div class="col-md-12 clearfix mb10">
						<label class="noPad"><input type="checkbox" name="outgoingSSL" ${emailAccount.outgoingSSL == true ? "checked='checked'" : ''}/>
						<span>This server requires a secure connection (SSL)</span></label>
					</div>
				</div>

			</div>
			<div class="popupFoot text-right">
				<button type="button" class="slBtn md colorBtn" onclick="addEmailAccount();">${emailAccount.id == 0 ? 'Add' : 'Update'}</button>
				<button type="button" class="slBtn md popClose" onclick="closeConfigurePopup();">Cancel</button>
			</div>
		</form:form>
	</div>
</div>
<script type="text/javascript">
	$("#email").change(function() {
		if (jQueryFormUtils.validateEmail($("#email").val())) {
			$("#recieveEmail").val($("#email").val());
			$("#sendEmail").val($("#email").val());
		} else {
			$("#recieveEmail").val("");
			$("#sendEmail").val("");
		}
	});
	function addEmailAccount() {
		if ($('#email_settings_form').validate(false, validationSettings)) {
			var formData = getFormData("email_settings_form");
			$("#email_settings_form").attr("disabled", "disabled");
			$("#loading").attr('style', 'position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:1000000;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$("#email_settings_form").addClass('button').val('Processing..');
			$.post(sourceleadUrl
					+ "/addEmailAccount?${_csrf.parameterName}=${_csrf.token}",
					formData, function(data) {
						if (data && data.response === "SUCCESS")
							location.replace(sourceleadUrl + "/mailBox/mails");
						else{
						     alert(data.responseMessage);
							 $("#loading").removeAttr('style');
						}
					});
			return true;
		} else {
			return false;
		}
	}
	function closeConfigurePopup() {
		$(".newPopOut").hide();
	}
	$('body').on('blur', '#email_settings_form', function() {
		$("#email_settings_form").showHelpOnFocus();
		$("#email_settings_form").validateOnBlur(false, validationSettings);
	});
</script>
