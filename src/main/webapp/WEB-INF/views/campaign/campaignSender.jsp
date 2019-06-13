<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/campaignSender.js"></script>

<script type="text/javascript">
    var staticTime='${staticTime}';
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

<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
<!--   Popup Code  -->
 	<div class="success_pop" id="selector1" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
     </div>
     <div id="senderLoading"></div>
		<form:form id="capn_add_sender" name="capn_add_sender" method="post">
		<div class="popupHead">
			<span id="addSender">Add Sender</span><span id="editSender">Edit Sender</span>
			<a onclick="senderClose();" href="javascript:void(0)" class="closePop pull-right popClose"></a>
			
		</div>
		<div id="loading"></div>
		<div class="popupContent">
          <div class="clearfix mb" id="cpnSenderNameDiv">
				<label class="noPad">Name <b class="valStar">*</b></label>
              	<div class="clearfix">
                	<input type="text" class="form-control" id="cpnSenderName" name="cpnSenderName" value="${sender.fromName}" data-validation="required validate_length length1-100 validate_Space validate_AlphaNumber" field-name="Name">
				<input type="hidden" value="${sender.id}" name="senderId" id="senderId" >
				</div>
			</div>
          <div class="clearfix mb" id="fromMailDiv">
				<label class="noPad">From Mail <b class="valStar">*</b></label>
              	<div class="clearfix">
                	<input type="text" class="form-control" id="cpnSenderFromMail"name="cpnSenderFromMail" value="${sender.fromMail}"   data-validation="required validate_length length5-100 validate_email" onblur="validateFromMail('from',${sender.id});" field-name="From Mail">
				</div>
			</div>
          <div class="clearfix mb" id="replyMailDiv">
				<label class="noPad">Reply Mail <b class="valStar">*</b></label>
              	<div class="clearfix">
                	<input type="text" class="form-control" id="cpnReplyMail" name="cpnReplyMail" value="${sender.replyTo}" onblur="validateReplyMail('reply',${sender.id});" data-validation="required validate_length length5-100 validate_email" field-name="Reply Mail" >
				</div>
			</div>
			<div class="clearfix mb" id="nickNameDiv">
				<label class="noPad">Nickname <b class="valStar">*</b></label>
				<div class="clearfix">
					<input type="text" class="form-control" field-name="Nick Name" id="cpnNickName" name="cpnNickName" value="${sender.nickName}"  onblur="validateNickName(${sender.id});"  data-validation="required validate_length length1-100 validate_Space validate_AlphaNumber">
				</div>
			</div>		
		</div>
		
		<div class="popupFoot text-right">
            <input value="Save" class="slBtn md colorBtn" id="saveCampaignSender" name="" type="button">
            <input value="Reset" class="slBtn md popClose" type="button" id="resetForm">
            <input onclick="senderClose();" value="Cancel" class="slBtn md popClose" type="button">
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