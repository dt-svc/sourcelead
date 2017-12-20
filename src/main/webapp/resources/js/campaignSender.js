

function campaignSender(ele,senderId) {
	/*fromProfile = fromProfile ? fromProfile : false;*/
		$.post(sourceleadUrl+"/campaignSender?senderId="+senderId+"", {}, function(data) {
			$(".popMask").fadeIn(200);
			$(".popUpOut").fadeIn(100);
			$("#campaignSender").html(data);
			// save sender details
			 $("#saveCampaignSender").click(function () {
				 if ($('#capn_add_sender').validate(false, validationSettings)) {
					  /*	var formData = $($('#capn_add_sender')[0]).serialize();*/
					 var formData = getFormData('capn_add_sender');
						$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
						$.post(sourceleadUrl+"/saveCampaignSender?"+tokenParamName+"="+tokenValue, formData, function(data) {
							if(data == true){
								senderClose();
								location.replace(sourceleadUrl+ "/campaignSendersList");
								 $("#selector1").removeAttr("style");
					  			 $("#selector1").css({ display: "block" });
					  			 $("#msgDiv").html("Successfullyy Created sender identity");
					  			 $("#popUp").empty();
					 			 $(".project_inn_mask").fadeOut(200);
					             msgFadein();	
							}
						});
						return true;
					} else {
						return false;
					}
	            });
			 
			 $("#resetForm").click(function(){
				 $("#capn_add_sender")[0].reset();
				 $("#recipientEmail").empty();
				  $("#recipientEmail").trigger("chosen:updated");
			 });
			
	            
		});
           
}

$('body').on('blur', '#capn_add_sender', function() {
	$("#capn_add_sender").showHelpOnFocus();
	$("#capn_add_sender").validateOnBlur(false, validationSettings);
});

function senderClose() {
	$(".popMask").fadeOut(200);
	$(".popUpOut").fadeOut(100);
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
					
				}else if(result == false) {
					$('input#cpnNickName').removeAttr("class record-exist record-exist-errormsg");
					$('input#cpnNickName').attr('class','valid form-control');
					$('#nickNameDiv').find('p.jquery_form_error_message').remove();
					
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
			url : sourceleadUrl + "/validateFromMail?"+tokenParamName+"="+tokenValue+"&email="+email+"&toWhom="+toWhom+"&id"+id,
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
			url : sourceleadUrl + "/validateFromMail?"+tokenParamName+"="+tokenValue+"&email="+email+"&toWhom="+toWhom+"&id"+id,
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
