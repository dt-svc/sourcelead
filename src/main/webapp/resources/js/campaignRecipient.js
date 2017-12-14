

function campaignRecipient(ele,listId) {
		$.post(sourceleadUrl+"/campaignAddRecipient?listId="+listId+"", {}, function(data) {
			$(".popMask").fadeIn(200);
			$(".popUpReci").fadeIn(100);
			$("#campaignRecipient").html(data);
			var campaignType = $("#typeOfCampaign").val();
			if(campaignType == "1"){
				$("#jobRadio").prop("checked", true);
			}else{
				$("#hotlistRadio").prop("checked", true);	
			}
			// save List and Associated Recipients  details
			 $("#addList").click(function () {
				 if ($('#capn_add_Recipient_List').validate(false, validationSettings)) {
					  /*	var formData = $($('#capn_add_Recipient_List')[0]).serialize();*/
					  	 var formData = getFormData('capn_add_Recipient_List');
						$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
						$.post(sourceleadUrl+"/saveRecipientList?"+tokenParamName+"="+tokenValue, formData, function(data) {
							if(data == true){
								 recipientClose();
								 $("#selector1").removeAttr("style");
					  			 $("#selector1").css({ display: "block" });
					  			 $("#msgDiv").html("Successfullyy Created sender identity");
					  			 $("#popUp").empty();
					 			 $(".project_inn_mask").fadeOut(200);
					             msgFadein();	
					             location.replace(sourceleadUrl+ "/recipientsList");
							}else{
								alert("Something went wrong ..!");
								$("#loading").removeAttr("style");
								$("#errorMessage").text("List not created/updated please try again");
							}
						});
						return true;
					} else {
						return false;
					}
	            });
			 
			 $("#resetForm").click(function(){
				 $("#capn_add_Recipient_List")[0].reset();
				 $("#recipientEmail").empty();
				  $("#recipientEmail").trigger("chosen:updated");
			 });
			
	            
		});
           
}

$('body').on('blur', '#capn_add_Recipient_List', function() {
	$("#capn_add_Recipient_List").showHelpOnFocus();
	$("#capn_add_Recipient_List").validateOnBlur(false, validationSettings);
});

function recipientClose() {
	$(".popMask").fadeOut(200);
	$(".popUpReci").fadeOut(100);
};






