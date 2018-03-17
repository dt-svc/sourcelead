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
		$.ajax({
		       url: sourceleadUrl+immiModulePath+"/savePetitionTemplateDocDetails",
		       type : "POST",
		       data: formData,
		       success: function(data) {
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
		formData.immigrationFormDeletedListIds = deletedFormsListIds.join();
		$.ajax({
		       url: sourceleadUrl+immiModulePath+"/savePetitionTemplateFormDetails",
		       type : "POST",
		       data: formData,
		       success: function(data) {
		    	   if(data == 'success'){
		    		   if(status == 'saveContinue'){
			    	   $("#addNewPetitionTemplateDiv").empty();
			    	   $("#addNewPetitionTemplateDiv").load(sourceleadUrl+immiModulePath+"/gotoTemplatePreviousPage?status=taskList");
		    	   }else if(status == 'save'){
	    			   $("#addNewPetitionTemplateDiv").empty();
			    	   $("#addNewPetitionTemplateDiv").load(sourceleadUrl+immiModulePath+"/gotoTemplatePreviousPage?status=formDetails");
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
		$("#immiTempId").val(immTemplId);
		var formData = getFormData1("petitionTemplateTaskDetails");
		$.ajax({
		       url: sourceleadUrl+immiModulePath+"/savePetitionTemplateTaskDetails",
		       type : "POST",
		       data: formData,
		       processData: false,
			   contentType:false,
		       success: function(data) {
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
	
 this.editTemplateDetailsByRowId = function (templateId){
		immTemplId = templateId; //updating global variable "immTemplId" with templateId.
	    window.location.href =sourceleadUrl+immiModulePath+"/addNewPetitionTemplate?templateId="+immTemplId; // for editing mode  
  }

  this.deleteTempalteDetailsByRowId = function(templateId){
	  
	  $.ajax({
	       url: sourceleadUrl+immiModulePath+"/deleteImmigrationTemplateDetatils?deleteId="+templateId,
	       type : "GET",
	       data: templateId,
	       success: function(data) {
	    	  if(data == 'success'){
	    		  window.location.href =sourceleadUrl+immiModulePath+"/viewImmigrationTemplateList"; // redirect to tempalte list.
	    	   }
	       }
	});
	  
  }
	// immigration view navigation if don't required comment below code start here.
	this. savePageOneContinue = function (status){
		//$("#newPetitionTemplateDiv").empty();
		if(status == 'petitionDetails'){
			$("#newPetitionTemplateDiv").load("/app"+immiModulePath+"/loadNewPetitionPage?forward=petitionDetails");
		}else if(status == 'two'){
			if ($("#petitionDetailsForm").validate(false, validationSettings)) {
				var formData = getFormData("petitionDetailsForm");
				$.ajax({
				       url: sourceleadUrl+immiModulePath+"/savePetitionDetailsPage",
				       type : "POST",
				       data: formData,
				       success: function(data) {
				    	  /* console.log("inside savePetitionTemplateDocDetails success");
				    	   if(data == 'success'){
				    		   if(status == 'saveContinue'){
						    	   $("#addNewPetitionTemplateDiv").empty();
						    	   $("#addNewPetitionTemplateDiv").load(sourceleadUrl+immiModulePath+"/gotoTemplatePreviousPage?status=formDetails");
				    		   }
				    		   else if(status == 'save'){
				    			   $("#addNewPetitionTemplateDiv").empty();
						    	   $("#addNewPetitionTemplateDiv").load(sourceleadUrl+immiModulePath+"/gotoTemplatePreviousPage?status=docDetails");
				    		   }
				    	   }*/
				       }
				});
				return true;
				} else {
					return false;
				}
			$("#newPetitionTemplateDiv").load("/app"+immiModulePath+"/loadNewPetitionPage?forward=two");
		}else if(status == 'three'){
			$("#newPetitionTemplateDiv").load("/app"+immiModulePath+"/loadNewPetitionPage?forward=three");
		}else if(status == 'four'){
			$("#newPetitionTemplateDiv").load("/app"+immiModulePath+"/loadNewPetitionPage?forward=four");
		}else if(status == 'five'){
			$("#newPetitionTemplateDiv").load("/app"+immiModulePath+"/loadNewPetitionPage?forward=five");
		}else if(status == 'six'){
			$("#newPetitionTemplateDiv").load("/app"+immiModulePath+"/loadNewPetitionPage?forward=six");
		}else if(status == 'seven'){
			$("#newPetitionTemplateDiv").load("/app"+immiModulePath+"/loadNewPetitionPage?forward=seven");
		}
	}// immigration end if don't required comment above code end here.
	
	this.savePetitionTemplateDetailsValues = function(){
		 $.ajax({
		       url: sourceleadUrl+immiModulePath+"/savePetitionTemplateDetailsValues?templateId="+immTemplId,
		       type : "GET",
		       /*data: templateId,*/
		       success: function(data) {
		    	  if(data == 'success'){
		    		  if(data == "success"){
		    			  window.location.href =sourceleadUrl+immiModulePath+"/viewImmigrationTemplateList"; // redirect to tempalte list.
		    			  immTemplId = "";
		    		  }
		    	   }
		       }
		});
	}
}
