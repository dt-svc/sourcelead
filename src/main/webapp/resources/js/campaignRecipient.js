

function campaignRecipient(ele,senderId) {
	/*fromProfile = fromProfile ? fromProfile : false;*/
		$.post(sourceleadUrl+"/campaignAddRecipient", {}, function(data) {
			$(".popMask").fadeIn(200);
			$(".popUpReci").fadeIn(100);
			$("#campaignRecipient").html(data);
			// save List and Associated Recipients  details
			 $("#addList").click(function () {
				 if ($('#capn_add_Recipient_List').validate(false, validationSettings)) {
					  	var formData = $($('#capn_add_Recipient_List')[0]).serialize();
						$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
						$.post(sourceleadUrl+"/saveRecipientList?"+tokenParamName+"="+tokenValue, formData, function(data) {
							if(data == true){
								recipientClose();
							}else{
								alert("Hai");
								$("#loading").removeAttr("style");
								$("#errorMessage").text("List not created please try again");
							}
						});
						return true;
					} else {
						return false;
					}
	            });
			
	            
		});
           
}

/*$('body').on('blur', '#capn_add_sender', function() {
	$("#capn_add_sender").showHelpOnFocus();
	$("#capn_add_sender").validateOnBlur(false, validationSettings);
});*/

function recipientClose() {
	$(".popMask").fadeOut(200);
	$(".popUpReci").fadeOut(100);
};






