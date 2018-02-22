var immigration  = new Immigration();
var immiModulePath = "/immigration";
var immTemplId= '';
function Immigration(){
	
	this.gotoTemplatePreviousPage = function(temp){
		$("#addNewPetitionTemplateDiv").empty();
		if(temp == 'templateDetails'){
			$("#addNewPetitionTemplateDiv").load(sourceleadUrl+immiModulePath+"/gotoTemplatePreviousPage?status=templateDetails");
		}else if(temp == 'templateDocs'){
			$("#addNewPetitionTemplateDiv").load(sourceleadUrl+immiModulePath+"/gotoTemplatePreviousPage?status=docDetails");
		}else if(temp == 'formDetails'){
			$("#addNewPetitionTemplateDiv").load(sourceleadUrl+immiModulePath+"/gotoTemplatePreviousPage?status=formDetails");
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
				    	   $("#addNewPetitionTemplateDiv").load(sourceleadUrl+immiModulePath+"/gotoTemplatePreviousPage?status=docDetails");
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
		formData.immigrationDocDeletedListIds = deletedDocListIds.join();
		console.log(formData);
		$.ajax({
		       url: sourceleadUrl+immiModulePath+"/savePetitionTemplateDocDetails",
		       type : "POST",
		       data: formData,
		       success: function(data) {
		    	   console.log("inside savePetitionTemplateDocDetails success");
		    	   if(data == 'success'){
		    		   if(status == 'saveContinue'){
				    	   $("#addNewPetitionTemplateDiv").empty();
				    	   $("#addNewPetitionTemplateDiv").load(sourceleadUrl+immiModulePath+"/gotoTemplatePreviousPage?status=formDetails");
		    		   }
		    		   else if(status == 'save'){
		    			   $("#addNewPetitionTemplateDiv").empty();
				    	   $("#addNewPetitionTemplateDiv").load(sourceleadUrl+immiModulePath+"/gotoTemplatePreviousPage?status=docDetails");
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
		$.ajax({
		       url: sourceleadUrl+immiModulePath+"/savePetitionTemplateFormDetails",
		       type : "POST",
		       data: formData,
		       success: function(data) {
		    	   console.log("inside savePetitionTemplateFormDetails success");
		    	   if(data == 'success'){
		    		   if(status == 'saveContinue'){
			    	   $("#addNewPetitionTemplateDiv").empty();
			    	   $("#addNewPetitionTemplateDiv").load(sourceleadUrl+immiModulePath+"/gotoTemplatePreviousPage?status=taskList");
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
		var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
		
		if ($('#petitionTemplateTaskDetails').validate(false, validationSettings)) {
		var formData = getFormData1("petitionTemplateTaskDetails");
		$.ajax({
		       url: sourceleadUrl+immiModulePath+"/savePetitionTemplateTaskDetails",
		       type : "POST",
		       data: formData,
		       processData: false,
			   contentType:false,
		       success: function(data) {
		    	   console.log("inside savePetitionTemplateTaskDetails success");
		    	   $(".popMask").fadeOut(200);
				   $("#taskViewPopup").fadeOut(100);
		    	   if(data == 'success'){
			    	   $("#addNewPetitionTemplateDiv").empty();
			    	   $("#addNewPetitionTemplateDiv").load(sourceleadUrl+immiModulePath+"/gotoTemplatePreviousPage?status=taskList");
			    	   
		    	   }
		       }
		});
		return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#petitionTemplateTaskDetails', function() {
	$("#petitionTemplateTaskDetails").showHelpOnFocus();
	$("#petitionTemplateTaskDetails").validateOnBlur(false, validationSettings);  
});
	// immigration view navigation if don't required comment below code start here.
	this. savePageOneContinue = function (status){
		$("#newPetitionTemplateDiv").empty();
		if(status == 'one'){
			$("#newPetitionTemplateDiv").load("/app"+immiModulePath+"/loadNewPetitionPage?status=one");
		}else if(status == 'two'){
			$("#newPetitionTemplateDiv").load("/app"+immiModulePath+"/loadNewPetitionPage?status=two");
		}else if(status == 'three'){
			$("#newPetitionTemplateDiv").load("/app"+immiModulePath+"/loadNewPetitionPage?status=three");
		}else if(status == 'four'){
			$("#newPetitionTemplateDiv").load("/app"+immiModulePath+"/loadNewPetitionPage?status=four");
		}else if(status == 'five'){
			$("#newPetitionTemplateDiv").load("/app"+immiModulePath+"/loadNewPetitionPage?status=five");
		}else if(status == 'six'){
			$("#newPetitionTemplateDiv").load("/app"+immiModulePath+"/loadNewPetitionPage?status=six");
		}else if(status == 'seven'){
			$("#newPetitionTemplateDiv").load("/app"+immiModulePath+"/loadNewPetitionPage?status=seven");
		}
	}// immigration end if don't required comment above code end here.
}
