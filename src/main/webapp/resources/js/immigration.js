var immigration  = new Immigration();
var immiModulePath = "/immigration";
var immTemplId= '';
function Immigration(){
	
	this.gotoTemplatePreviousPage = function(temp){
		$("#addNewPetitionTemplateDiv").empty();
		if(temp == 'templateDetails'){
			$("#addNewPetitionTemplateDiv").load(sourceleadUrl+immiModulePath+"/gotoTemplatePreviousPage?status=templateDetails");
		}else if(temp == 'templateDocs'){
			$("#addNewPetitionTemplateDiv").load(sourceleadUrl+immiModulePath+"/addPetitionTemplate?forwardTo=docDetails");
		}
	}
	
	this.savePetitionTemplateAndContinue = function(status){
		if ($('#petitionTemplateDetails').validate(false, validationSettings)) {
		var formData = getFormData("petitionTemplateDetails");
		$.ajax({
		       url: sourceleadUrl+immiModulePath+"/savePetitionTemplateDetails?templateId="+immTemplId,
		       type : "POST",
		       data: formData,
		       success: function(data) {
		    	   console.log("inside success");
		    	   eval("jsonObj="+data);
		    	   if(jsonObj.status =='success'){
		    		   immTemplId=jsonObj.id;
		    		   if(status == 'saveContinue'){
				    	   $("#addNewPetitionTemplateDiv").empty();
				    	   $("#addNewPetitionTemplateDiv").load(sourceleadUrl+immiModulePath+"/addPetitionTemplate?forwardTo=docDetails");
		    		   }
		    	   }
		       }
		});
		return true;
		} else {
			return false;
		}
	}
	
	this.resetFormData = function(formId){
		  $("#"+formId)[0].reset();
	}
	
	this.savePetitionDoc = function(status){
		if ($('#PetitionTemplateDocDetails').validate(false, validationSettings)) {
		var formData = getFormData("PetitionTemplateDocDetails");
		formData.id = immTemplId;
		console.log(formData);
		$.ajax({
		       url: sourceleadUrl+immiModulePath+"/savePetitionTemplateDocDetails",
		       type : "POST",
		       data: formData,
		       success: function(data) {
		    	   console.log("inside savePetitionTemplateDocDetails success");
		    	   if(data == 'success'){
		    		   immTemplId=jsonObj.id;
		    		   if(status == 'saveContinue'){
				    	   $("#addNewPetitionTemplateDiv").empty();
				    	   $("#addNewPetitionTemplateDiv").load(sourceleadUrl+immiModulePath+"/addPetitionTemplate?forwardTo=formDetails");
		    		   }
		    	   }
		       }
		});
		return true;
		} else {
			return false;
		}
	}
	
	this.savePetitionFormDetails = function(status){
		if ($('#petitionTemplateFormDetails').validate(false, validationSettings)) {
		var formData = getFormData("petitionTemplateFormDetails");
		formData.id = immTemplId;
		alert("ffff"+status)
		$.ajax({
		       url: sourceleadUrl+immiModulePath+"/savePetitionTemplateFormDetails",
		       type : "POST",
		       data: formData,
		       success: function(data) {
		    	   console.log("inside savePetitionTemplateFormDetails success");
		    	   if(data == 'success'){
		    		   if(status == 'saveContinue'){
			    	   $("#addNewPetitionTemplateDiv").empty();
			    	   $("#addNewPetitionTemplateDiv").load(sourceleadUrl+immiModulePath+"/addPetitionTemplate?forwardTo=taskList");
		    	   }
		    	 }
		       }
		});
		
		return true;
	} else {
		return false;
	}
	}
	
	this.savePetitionTemplateTaskDetails= function(){
		var formData = getFormData("petitionTemplateTaskDetails");
		formData.id = immTemplId;
		$.ajax({
		       url: sourceleadUrl+immiModulePath+"/savePetitionTemplateTaskDetails",
		       type : "POST",
		       data: formData,
		       success: function(data) {
		    	   console.log("inside savePetitionTemplateTaskDetails success");
		    	   $(".popMask").fadeOut(200);
				   $("#taskViewPopup").fadeOut(100);
		    	   if(data == 'success'){
			    	   $("#addNewPetitionTemplateDiv").empty();
			    	   $("#addNewPetitionTemplateDiv").load(sourceleadUrl+immiModulePath+"/addPetitionTemplate?forwardTo=taskList");
			    	   
		    	   }
		       }
		});
	}
}
