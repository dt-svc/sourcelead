function validateAlreadyGeneratedContract(){
	var startDate = $('#startDate').val();
	var endDate = $('#endDate').val();
	var orgRelationId = $("#orgRelationId").val();
	if(orgRelationId == "")
		orgRelationId = $("#clientOrgRelationId").val();
	var contractType = $("#contractType").val();
	var submissionOffersList = $("#submissionOffersList").val();
//	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var url = "";
	if((contractType == "Client MSA" || contractType == "Vendor MSA")  && (orgRelationId != "" && orgRelationId != 'undefined')){
		url = vmsUrl+"/validateAlreadyGeneratedContract?startDate="+startDate+"&endDate="+endDate+"&orgRelationId="+orgRelationId+"&contractType="+contractType;
		validateAlreadyGeneratedContractMainFunc(url,startDate,endDate,contractType);
	}else if(contractType == "Client SOW" || contractType == "Vendor SOW"){
		url = vmsUrl+"/validateAlreadyGeneratedContract?startDate="+startDate+"&endDate="+endDate+"&submissionOffersList="+submissionOffersList+"&contractType="+contractType;
		validateAlreadyGeneratedContractMainFunc(url,startDate,endDate,contractType);
	}
}

function validateAlreadyGeneratedContractMainFunc(url,startDate,endDate,contractType){
//	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	if(startDate != "" && startDate != 'undefined' && endDate != "" && endDate != 'undefined'){
		$.ajax({
		     type : "GET",
		      url: url,      
		      success :function(data) {
		      	    if(data != null){
		      	    	$(".validatePreexistingContact").fadeIn();
			      		$(".comMask").fadeIn();
			      		var result = "";
			      		result += '<ul class="ml">';
			      		for (var i=0; i<data.length; i++){
			      			var opt = data[i];
			      			if(opt.contractWith != null && opt.contractWith != '' && opt.contractWith != 'undefined'
			      				&& opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined'
			      					&& opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'
			      						&& opt.contractUUID != null && opt.contractUUID != '' && opt.contractUUID != 'undefined'
			      							&& opt.contractTitle != null && opt.contractTitle != '' && opt.contractTitle != 'undefined'){
			      				if(contractType == "Client SOW" || contractType == "Vendor SOW"){
			      					result += '<li class="mb10">'+contractType+' Contract generated for '+opt.applicantName+' with '+opt.contractWith+' from '+opt.startDateStr+' - '+opt.endDateStr+'. <strong>'+opt.autoGenId+' - <a href="'+vmsUrl+'/viewContract?contractUUID='+opt.contractUUID+'" target="_blank"> '+opt.contractTitle+'</a></strong> </li>';
			      				}else if(contractType == "Client MSA" || contractType == "Vendor MSA"){
			      					result += '<li class="mb10">'+contractType+' Contract generated with '+opt.contractWith+' from '+opt.startDateStr+' - '+opt.endDateStr+'. <strong>'+opt.autoGenId+' - <a href="'+vmsUrl+'/viewContract?contractUUID='+opt.contractUUID+'" target="_blank"> '+opt.contractTitle+'</a></strong> </li>';
			      				}
			      			}
			      		}
			      		result += '</ul>';
			      		$("#preExistContractMsg").empty();
			      		$("#preExistContractMsg").append(result);
		      	    }
		     },
		 });
	}
}

function reviseContract(contractUUID){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Reason to Revise the Contract</p>";
	result = result +"		<textarea  id='reviseContractId' name='reviseContractId'  placeholder='Reason here'></textarea>";
	result = result + ' <div id="reviseContractIdDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Submit' onclick='submitReviseContractReason(\""+contractUUID+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTermination();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function submitReviseContractReason(contractUUID){
	var reviseReason =  $("#reviseContractId").val();
//	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	if(reviseReason != null && reviseReason !='undefined' && reviseReason !=''){
		$("#reviseContractIdDiv").find('p.jquery_form_error_message').remove();
		$.get(vmsUrl+"/reviseContract?"+tokenParamName+"="+tokenValue+"&contractUUID="+contractUUID+"&revisedReason="+reviseReason, function(data) {
			if(data == true){
				cancelTermination();
				getTemplateList();
			}
		}); 
	}else{
		$("#reviseContractIdDiv").append("<p class='jquery_form_error_message'>Reason Is Required</p>");
	}
}



function getParallelApprovers(){
	$("#parallel i").addClass("apprverReq-active");
	$("#sequential i").removeClass("apprverReq-active");
	$("#parallelAppDiv").show();
	$("#sequentialAppDiv").hide();
	$("#sequentialOwner1").val('');
	$("#sequentialOwner2").val('');
// 	$("#parallelOwner").val('');
	$('#sequentialOwner1').trigger("chosen:updated");
	$('#sequentialOwner2').trigger("chosen:updated");
// 	$('#parallelOwner').trigger("chosen:updated");
	
	$('#parallelOwner').removeAttr('data-validation-optional');
	$('#parallelOwner').attr('data-validation',"required");
	$('#sequentialOwner2').removeAttr('data-validation');
	$('#sequentialOwner2').attr('data-validation-optional',"true");
	$('#sequentialOwner1').removeAttr('data-validation');
	$('#sequentialOwner1').attr('data-validation-optional',"true");
}

function getSequentialApprovers(){
		$("#parallel i").removeClass("apprverReq-active");
		$("#sequential i").addClass("apprverReq-active");
		$("#sequentialAppDiv").show();
		$("#parallelAppDiv").hide();
//	 	$("#sequentialOwner1").val('');
//	 	$("#sequentialOwner2").val('');
		$("#parallelOwner").val('');
		
//	 	$('#sequentialOwner1').trigger("chosen:updated");
//	 	$('#sequentialOwner2').trigger("chosen:updated");
		$('#parallelOwner').trigger("chosen:updated");
		$('#sequentialOwner1').removeAttr('data-validation-optional');
		$('#sequentialOwner1').attr('data-validation',"required");
		$('#sequentialOwner2').removeAttr('data-validation-optional');
		$('#sequentialOwner2').attr('data-validation',"required");
		$('#parallelOwner').removeAttr('data-validation');
		$('#parallelOwner').attr('data-validation-optional',"true");
}


