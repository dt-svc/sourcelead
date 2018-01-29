<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">
    var staticTime='${staticTime}'
    
    	var tokenParamName = "${_csrf.parameterName}";
    var tokenValue = "${_csrf.token}";
    var senderId =  "${sender.id}";
    if(senderId !=null && senderId != '' && senderId != 'undefined'){
    	$("#editSender").show();
    	$("#addSender").hide();
    }else{
    	$("#editSender").hide();
    	$("#addSender").show();
    }
 </script>

<script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
<!--   Popup Code  -->
 	<div class="success_pop" id="selector1" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
     </div>
		<form:form id="capn_add_sender" name="capn_add_sender" method="post">
		<div class="popupHead">
			<h2 id="addSender">Add Sender</h2><a onclick="senderClose();"  href="javascript:void(0)" class="fa fa-close"></a>
			<h2 id="editSender">Edit Sender</h2><a onclick="senderClose();"  href="javascript:void(0)" class="fa fa-close"></a>
		</div>
		<div id="loading"></div>
		<div class="popupMid clearfix">
          <div class="col-sm-12 noPad mt clearfix" id="cpnSenderNameDiv">
				<label class="col-md-3 padding_left">Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-9 padding_right">
                	<input type="text" class="form-control" id="cpnSenderName" name="cpnSenderName" value="${sender.fromName}" data-validation="required validate_length length1-100 validate_Space validate_AlphaNumber" field-name="Name">
				<input type="hidden" value="${sender.id}" name="senderId" id="senderId" >
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfixclearfix" id="fromMailDiv">
				<label class="col-md-3 padding_left">From Mail<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-9 padding_right">
                	<input type="text" class="form-control" id="cpnSenderFromMail"name="cpnSenderFromMail" value="${sender.fromMail}"   data-validation="required validate_length length5-100 validate_email" onblur="validateFromMail('from',${sender.id});" field-name="From Mail">
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix" id="replyMailDiv">
				<label class="col-md-3 padding_left">Reply Mail<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-9 padding_right">
                	<input type="text" class="form-control" id="cpnReplyMail" name="cpnReplyMail" value="${sender.replyTo}" onblur="validateReplyMail('reply',${sender.id});" data-validation="required validate_length length5-100 validate_email" field-name="Reply Mail" >
				</div>
			</div>
			<div class="col-sm-12 noPad mt clearfix" id="nickNameDiv">
				<label class="col-md-3 padding_left">Nickname<span
					class="text-danger">&nbsp;*</span></label>
				<div class="col-md-9 padding_right">
					<input type="text" class="form-control" field-name="Nick Name" id="cpnNickName" name="cpnNickName" value="${sender.nickName}"  onblur="validateNickName(${sender.id});"  data-validation="required validate_length length1-100 validate_Space validate_AlphaNumber">
				</div>
			</div>		
		</div>
		<div class="popupFoot">
            <input value="Save" class="btn btn-primary" id="saveCampaignSender" name="" type="button">
            <input value="Reset" class="btn btn-primary" type="button" id="resetForm">
            <input onclick="senderClose();" value="Cancel" class="btn btn-default ml" type="button">
          </div>		
		
		</form:form>
		
<div id="popUp"></div>
		
<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 
		  
 <script>
 function msgFadein() {
	$("#selector1").fadeIn(300);
    $('#selector1').delay(3000).fadeOut('slow');
}
</script>

<!--   Popup Code  -->  