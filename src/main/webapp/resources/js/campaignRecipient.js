

function campaignRecipient(ele,listId, fromType) {
		$.post(sourceleadUrl+"/campaignAddRecipient?listId="+listId+"", {}, function(data) {
//			$(".mask").css({ display: "block" });
			$("#campaignRecipient").fadeIn(100);
			$("#campaignRecipient").html(data);
			$("#campaignRecipient").parent().append("<div class='mask' style='display:block;'></div>");
			var campaignType = $("#typeOfCampaign").val();
			if(campaignType == "1"){
				$("#jobRadio").prop("checked", true);
			}else{
				$("#hotlistRadio").prop("checked", true);	
			}
			// save List and Associated Recipients  details
			 $("#addList").click(function () {
				 if ($('#capn_add_Recipient_List').validate(false, validationSettings)) {
					  	var formData = $($('#capn_add_Recipient_List')[0]).serialize();
					  	/* var formData = getFormData('capn_add_Recipient_List');*/
						$("#loadingRecipient").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
						$.post(sourceleadUrl+"/saveRecipientList?"+tokenParamName+"="+tokenValue, formData, function(data) {
							
							if(data == 'success'){
								 recipientClose();
								 
								 if(listId != ''){
									 $("#selector1").removeAttr("style");
						  			 $("#selector1").css({ display: "block" });
						  			 $("#msgDiv").html("Successfully Updated recipient");
								}else{
									 $("#selector1").removeAttr("style");
						  			 $("#selector1").css({ display: "block" });
						  			 $("#msgDiv").html("Successfully Created recipient");
								}
					  			
					             if(fromType == "hotList" || fromType == "jobList"){
					            	 if(fromType == "hotList"){
					            		 location.replace(sourceleadUrl+ "/hotlistMail");
					            	 }else if(fromType == "jobList"){
					            		 location.replace(sourceleadUrl+ "/joblistMail");
					            	 }
					             }else{
					            	 location.replace(sourceleadUrl+ "/recipientsList");
					             }
					             $("#popUp").empty();
					 			 $(".mask").hide();
					             msgFadein();
					             
							}else if(data == '400Body'){
								$("#loadingRecipient").removeAttr("style");
								var name = $("#cpnListName").val();
								$('#cpnListNameDiv').find('p.jquery_form_error_message').remove();
				          		$("#addList").removeAttr("disabled");
						        $("#addList").attr("disabled", "disabled");
								$("input#cpnListName").after( "<p class='jquery_form_error_message'>" + name + " is already existed</span>");
							}else if(data == '403Body'){
								$("#loadingRecipient").removeAttr("style");
								$("#campaignRecipient").hide();
								maxAlertPopUp();
							}
						});
						return true;
					} else {
						return false;
					}
	            });
			 
			 $("#resetForm").click(function(){
				 $('#recipientEmailDiv').find('p.jquery_form_error_message').remove();
				 $('#cpnListNameDiv').find('p.jquery_form_error_message').remove();
				// $("#capn_add_Recipient_List")[0].reset();
				 $("#cpnListName").val("");
				 $("#recipientEmail").empty();
				  $("#recipientEmail").trigger("chosen:updated");
			 });
			
	            
		});
           
}

$('body').on('blur', '#capn_add_Recipient_List', function() {
	$("#capn_add_Recipient_List").showHelpOnFocus();
	$("#capn_add_Recipient_List").validateOnBlur(false, validationSettings);
});

function refreshPage()
{
//	location.reload();

}
function recipientClose() {
	refreshPage();
	$(".mask").hide();
	$(".popupWrap").hide();
};


function maxAlertPopUp(){
	var result = "";
	result = result +"	<div class='mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>You have reached maximum number of recipient List</p>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancelMaxAlert();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelMaxAlert();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".mask").hide();
}


function cancelMaxAlert(){
	$("#popUp").empty();
	 $(".mask").hide();
}




