

function campaignSender(ele,senderId, fromType) {
	/*fromProfile = fromProfile ? fromProfile : false;*/
	$.post(sourceleadUrl+"/campaignSender?senderId="+senderId+"", {}, function(data) {
			$(".popMask").fadeIn(200);
			$("#campaignSender").fadeIn(100);
			$("#campaignSender").html(data);
			$("#campaignSender").parent().append("<div class='mask' style='display:block;'></div>");
			// save sender details
			 $("#saveCampaignSender").click(function () {
				 if ($('#capn_add_sender').validate(false, validationSettings)) {
					 $('#saveCampaignSender').attr("disabled","disabled");
					  /*	var formData = $($('#capn_add_sender')[0]).serialize();*/
					 var formData = getFormData('capn_add_sender');
						$("#senderLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
						$.post(sourceleadUrl+"/saveCampaignSender?"+tokenParamName+"="+tokenValue, formData, function(data) {
							if(data == 'success'){
								senderClose();
								if(senderId != ''){
									 $("#campainSenderAlertMsg").removeAttr("style");
						  			 $("#campainSenderAlertMsg").css({ display: "block" });
						  			 $("#campainSenderAlertMsgDiv").html("Successfully Updated sender identity");
								}else{
									 $("#campainSenderAlertMsg").removeAttr("style");
						  			 $("#campainSenderAlertMsg").css({ display: "block" });
						  			 $("#campainSenderAlertMsgDiv").html("Successfully Created sender identity");
								}
					  			
								 location.replace(sourceleadUrl+ "/campaignSendersList");
								 $("#popUp").empty();
					  			 $(".mask").fadeOut(200);
								 campainSenderAlertMsgFadein();	
					             	
							}else if(data == '403Body'){
								$("#senderLoading").removeAttr("style");
								$('#nickNameDiv').find('p.jquery_form_error_message').remove();
								$("input#cpnNickName").after( "<p class='jquery_form_error_message'>You already have a sender identity with the same nickname.</span>");
							}else if(data == '400Body'){
								$("#senderLoading").removeAttr("style");
								maxAlertSenderPopUp();
							}
						});
						return true;
					} else {
						return false;
					}
	            });
			 
			 $("#resetForm").click(function(){
				 $('#cpnSenderNameDiv').find('p.jquery_form_error_message').remove();
				 $('#fromMailDiv').find('p.jquery_form_error_message').remove();
				 $('#replyMailDiv').find('p.jquery_form_error_message').remove();
				 $('#nickNameDiv').find('p.jquery_form_error_message').remove();
				 $("#capn_add_sender")[0].reset();
				 $("#cpnReplyMail").val('');
				 $("#cpnNickName").val('');
				 $("#cpnSenderFromMail").val('');
				 $("#cpnSenderName").val('');
				 $("#recipientEmail").trigger("chosen:updated");
				 $("#saveCampaignSender").removeAttr("disabled");
			 });
			
	            
		});
           
}

$('body').on('blur', '#capn_add_sender', function() {
	$("#capn_add_sender").showHelpOnFocus();
	$("#capn_add_sender").validateOnBlur(false, validationSettings);
});

function senderClose() {
	$(".mask").fadeOut(200);
	$("#campaignSender").fadeOut(100);
	$(".mask").remove();
};



function validateNickName(senderId){
	var nickName = document.getElementById("cpnNickName").value;
	if(nickName != null && nickName != ''){
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/validateNickName?"+tokenParamName+"="+tokenValue+"&nickName="+nickName+"&senderId="+senderId,
			success : function(data) {
				var result = data;
				if(result == true) {
					$('#nickNameDiv').find('p.jquery_form_error_message').remove();
					$("input#cpnNickName").after( "<p class='jquery_form_error_message'>You already have a sender identity with the same nickname.</span>");
					document.getElementById("cpnNickName").setAttribute( 'record-exist', 'yes');
					document.getElementById("cpnNickName").setAttribute( 'record-exist-errorMsg',"You already have a sender identity with the same nickname.");
					$('#saveCampaignSender').attr("disabled","disabled");
					
				}else if(result == false) {
					$('input#cpnNickName').removeAttr("class record-exist record-exist-errormsg");
					$('input#cpnNickName').attr('class','valid form-control');
					$('#nickNameDiv').find('p.jquery_form_error_message').remove();
					$("#saveCampaignSender").removeAttr("disabled", "disabled");
					
				}
			},

		});
	}
}




function validateFromMail(toWhom,id){
	var email = document.getElementById("cpnSenderFromMail").value;
	 if(email != null && email != null && email !='undefined' ){
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/validateFromMail?"+tokenParamName+"="+tokenValue+"&email="+email+"&toWhom="+toWhom+"&id="+id,
			success : function(data) {
				var result = data;
				 if(result == true) {
					$('#fromMailDiv').find('p.jquery_form_error_message').remove();
					$("input#cpnSenderFromMail").after( "<p class='jquery_form_error_message'>You already have a sender identity with the same email.</span>");
					document.getElementById("cpnSenderFromMail").setAttribute( 'record-exist', 'yes');
					document.getElementById("cpnSenderFromMail").setAttribute( 'record-exist-errorMsg',"You already have a sender identity with the same email.");
					
				}else if(result == false) {
					$('input#cpnSenderFromMail').removeAttr("class record-exist record-exist-errormsg");
					$('input#cpnSenderFromMail').attr('class','valid form-control');
					$('#fromMailDiv').find('p.jquery_form_error_message').remove();
					
					
				}
			},

		});
	}
	 
	
}


function validateReplyMail(toWhom,id){ 
	var email = document.getElementById("cpnReplyMail").value;
	if(email != null && email != null && email !='undefined' ){
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/validateFromMail?"+tokenParamName+"="+tokenValue+"&email="+email+"&toWhom="+toWhom+"&id="+id,
			success : function(data) {
				var result = data;
				if(result == true) {
					$('#replyMailDiv').find('p.jquery_form_error_message').remove();
					$("input#cpnReplyMail").after( "<p class='jquery_form_error_message'>You already have a sender identity with the same email.</span>");
					document.getElementById("cpnReplyMail").setAttribute( 'record-exist', 'yes');
					document.getElementById("cpnReplyMail").setAttribute( 'record-exist-errorMsg',"You already have a sender identity with the same email.");
					
				}else if(result == false) {
					$('input#cpnReplyMail').removeAttr("class record-exist record-exist-errormsg");
					$('input#cpnReplyMail').attr('class','valid form-control');
					$('#replyMailDiv').find('p.jquery_form_error_message').remove();
					
				}
			},

		});
	}
	
}

function maxAlertSenderPopUp(){
	var result = "";
	result = result +"	<div class='mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='zInPop remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>You have reached maximum number of sender List</p>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancelMaxAlert();' style='width:100%;'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".mask").fadeIn(200);
}


function cancelMaxAlert(){
	$("#popUp").empty();
	 $(".mask").fadeOut(200);
}

