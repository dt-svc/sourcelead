var immigration = new Immigration();
var immiModulePath = "/immigration";
var immTemplId = '';
var immigrationId = '';
var candidateId = '';
var familyMemberId = '';
var templateId='';
var formNames = [];
function Immigration() {

    this.gotoTemplatePreviousPage = function (temp) {
        $("#addNewPetitionTemplateDiv").empty();
        if (temp == 'templateDetails') {
            $("#addNewPetitionTemplateDiv").load(sourceleadUrl + immiModulePath + "/gotoTemplatePreviousPage?status=templateDetails");
        } else if (temp == 'templateDocs') {
            $("#addNewPetitionTemplateDiv").load(sourceleadUrl + immiModulePath + "/gotoTemplatePreviousPage?status=docDetails");
        } else if (temp == 'formDetails') {
            $("#addNewPetitionTemplateDiv").load(sourceleadUrl + immiModulePath + "/gotoTemplatePreviousPage?status=formDetails");
        }
    }

    this.savePetitionTemplateAndContinue = function (status) {
        if ($('#petitionTemplateDetails').validate(false, validationSettings)) {
            var formData = getFormData("petitionTemplateDetails");
            $.ajax({
                url: sourceleadUrl + immiModulePath + "/savePetitionTemplateDetails?templateId=" + immTemplId,
                type: "POST",
                data: formData,
                success: function (result) {
                    if (result && result.response === "INVALID_DATA") {
                    	 showError(result.responseMessage);
                    }else if (result.response === "AWKWARD") {
                        location.href = "/errorPage";
                    }else if(result.response === "SUCCESS") {
                    	immTemplId = result.object;
                        if (status == 'saveContinue') {
                            $("#addNewPetitionTemplateDiv").empty();
                            $("#addNewPetitionTemplateDiv").load(sourceleadUrl + immiModulePath + "/gotoTemplatePreviousPage?status=docDetails");
                        }
		               
                    }else{
                    	 showError("Operation failed, please try again");
                    }
                }
            });
            return true;
        } else {
            return false;
        }
    }

    this.resetFormData = function (formId) {
        $("#" + formId)[0].reset();
    }
    
    this.saveEducationImmigration = function (status) {
    	var validationSettings = {
                errorMessagePosition: 'element',
                borderColorOnError: '',
                scrollToTopOnError: true,
                dateFormat: 'yyyy/mm/dd'
            };
    	if ($('#eduSave_Form').validate(false, validationSettings)) {
            var formData = getFormData("eduSave_Form");
            var candidateId = $('#candidateID').val();
            var immigrationId = $('#immigrationID').val();
            $.ajax({
                url: sourceleadUrl + immiModulePath + "/saveEducationImmigrationDedtails?" + tokenParamName + "=" + tokenValue + "&candidateId=" + candidateId+"&immigrationId="+immigrationId,
                type: "POST",
                data: formData,
                success: function (result) {
                	if (result && result.response === "INVALID_DATA") {
                   	 showError(result.responseMessage);
                   }else if (result.response === "AWKWARD") {
                       location.href = "/errorPage";
                   }else if(result.response === 'SUCCESS') {
	                	$('#popEduView').hide();
	                   	$('.popMask').fadeOut();
	                   	getCandidateEducationList(candidateId);
                   }else{
                   	 showError("Operation failed, please try again");
                   }
                }
            });
            return true;
        } else {
            return false;
        }
    }
    $('body').on('blur', '#eduSave_Form', function () {
        $("#eduSave_Form").showHelpOnFocus();
        $("#eduSave_Form").validateOnBlur(false, validationSettings);
    });

    this.savePetitionDoc = function (status) {
        if ($('#PetitionTemplateDocDetails').validate(false, validationSettings)) {
            var formData = getFormData("PetitionTemplateDocDetails");
            formData.immigrationDocDeletedListIds = deletedDocListIds.join();
            $.ajax({
                url: sourceleadUrl + immiModulePath + "/savePetitionTemplateDocDetails",
                type: "POST",
                data: formData,
                success: function (result) {
                    if (result && result.response === "INVALID_DATA") {
                    	showError(result.responseMessage);
                    }else if (result.response === "AWKWARD") {
                        location.href = "/errorPage";
                    }else if(result.response === 'SUCCESS') {
                    	if (status == 'saveContinue') {
                            $("#addNewPetitionTemplateDiv").empty();
                            $("#addNewPetitionTemplateDiv").load(sourceleadUrl + immiModulePath + "/gotoTemplatePreviousPage?status=formDetails");
                        } else if (status == 'save') {
                            $("#addNewPetitionTemplateDiv").empty();
                            $("#addNewPetitionTemplateDiv").load(sourceleadUrl + immiModulePath + "/gotoTemplatePreviousPage?status=docDetails");
                        } 
                    }else{
                    	 showError("Operation failed, please try again");
                    }
                }
            });
            return true;
        } else {
            return false;
        }
    }

    this.savePetitionFormDetails = function (status) {
        if ($('#petitionTemplateFormDetails').validate(false, validationSettings)) {
            var formData = getFormData("petitionTemplateFormDetails");
            formData.immigrationFormDeletedListIds = deletedFormsListIds.join();
            $.ajax({
                url: sourceleadUrl + immiModulePath + "/savePetitionTemplateFormDetails",
                type: "POST",
                data: formData,
                success: function (result) {
                    if ( result && result.response === "INVALID_DATA") {
                    	showError(result.responseMessage);
                    }else if (result.response === "AWKWARD") {
                        location.href = "/errorPage";
                    }else if(result.response === 'SUCCESS') {
                    	if (status == 'saveContinue') {
                            $("#addNewPetitionTemplateDiv").empty();
                            $("#addNewPetitionTemplateDiv").load(sourceleadUrl + immiModulePath + "/gotoTemplatePreviousPage?status=taskList");
                        } else if (status == 'save') {
                            $("#addNewPetitionTemplateDiv").empty();
                            $("#addNewPetitionTemplateDiv").load(sourceleadUrl + immiModulePath + "/gotoTemplatePreviousPage?status=formDetails");
                        }
                    }else{
                    	 showError("Operation failed, please try again");
                    }
                }
            });

            return true;
        } else {
            return false;
        }
    }

    this.savePetitionTemplateTaskDetails = function () {
        var validationSettings = {
            errorMessagePosition: 'element',
            borderColorOnError: '',
            scrollToTopOnError: true,
            dateFormat: 'yyyy/mm/dd'
        };

        if ($('#petitionTemplateTaskDetails').validate(false, validationSettings)) {
            $("#immiTempId").val(immTemplId);
            var formData = getFormData1("petitionTemplateTaskDetails");
            $.ajax({
                url: sourceleadUrl + immiModulePath + "/savePetitionTemplateTaskDetails",
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                success: function (result) {
                    $(".popMask").fadeOut(200);
                    $("#taskViewPopup").fadeOut(100);
                    if (result && result.response === "INVALID_DATA") {
                    	 showError(result.responseMessage);
                    }else if (result.response === "AWKWARD") {
                        location.href = "/errorPage";
                    }else if(result.response === 'SUCCESS') {
                    	$("#addNewPetitionTemplateDiv").empty();
                        $("#addNewPetitionTemplateDiv").load(sourceleadUrl + immiModulePath + "/gotoTemplatePreviousPage?status=taskList");
                    }else{
                    	 showError("Operation failed, please try again");
                    }
                }
            });
            return true;
        } else {
            return false;
        }
    }
    $('body').on('blur', '#petitionTemplateTaskDetails', function () {
        $("#petitionTemplateTaskDetails").showHelpOnFocus();
        $("#petitionTemplateTaskDetails").validateOnBlur(false, validationSettings);
    });

    this.editTemplateDetailsByRowId = function (templateId) {
        immTemplId = templateId; //updating global variable "immTemplId" with templateId.
        window.location.href = sourceleadUrl + immiModulePath + "/addNewPetitionTemplate?templateId=" + immTemplId; // for editing mode  
    }

    this.reactiveImmigrationTemplateById = function (templateId){
    	$.ajax({
            url: sourceleadUrl + immiModulePath + "/reactiveImmigrationTemplateDetailsById?templateId=" + templateId,
            type: "GET",
            data: templateId,
            success: function (data) {
                if (data == 'success') {
                    window.location.href = sourceleadUrl + immiModulePath + "/viewImmigrationTemplateList"; // redirect to tempalte list.
                }
            }
        });
    }
    
    this.deleteTempalteDetailsByRowId = function (templateId) {
        var reason = $("#reasonForTemplate").val();
        if(reason.match(/^[^\s-](.*)*$/) != null && reason !="" && reason.length <= 250){
        $("#reasonForTemplateDiv").empty();
    	$.ajax({
            url: sourceleadUrl + immiModulePath + "/deleteImmigrationTemplateDetatils?deleteId=" + templateId+"&reason="+reason,
            type: "GET",
            data: templateId,
            success: function (data) {
                if (data == 'success') {
                    window.location.href = sourceleadUrl + immiModulePath + "/viewImmigrationTemplateList"; // redirect to tempalte list.
                }
            }
        });
        }else if (reason.length >= 251){
        	$("#reasonForTemplateDiv").find('p.jquery_form_error_message').remove();
			$("#reasonForTemplateDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason Length 1-250</font>") ; 
        }else{
        	$("#reasonForTemplateDiv").find('p.jquery_form_error_message').remove();
			$("#reasonForTemplateDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
        }
    }
    // immigration view navigation if don't required comment below code start here.
    this.savePageAndContinue = function (status) {
    	$("#saveDraftButton").prop("disabled",true);
    	$("#savePageOneContinue").prop("disabled",true);
    	$("#sendRequestButton").prop("disabled",true);
        if (status == 'petitionDetails') {
            $("#newPetitionTemplateDiv").load("/app" + immiModulePath + "/loadNewPetitionPage?forward=petitionDetails");
        } else if (status == 'saveContinue' || status == 'sendRequest') {
        	
        	if ($("#petitionDetailsForm").validate(false, validationSettings)) {
                var formData = getFormData("petitionDetailsForm");
                $.ajax({
                    url: sourceleadUrl + immiModulePath + "/savePetitionDetailsPage?removedResidenceIds="+removedResidenceIds+"&status="+status,
                    type: "POST",
                    data: formData,
                    success: function (result) {
                    	if (result && result.response === "INVALID_DATA") {
                       	 showError(result.responseMessage);
                       }else if (result.response === "AWKWARD") {
                           location.href = "/errorPage";
                       }else if(result.response === 'SUCCESS') {
                    	   immigrationId = result.data[0];
                       	candidateId = result.data[1];
                       	templateId= result.data[2];
                            if(status == 'saveContinue'){// redirect to petition details tab page.
                            window.location.href = sourceleadUrl + immiModulePath + "/viewImmigration?immigrationId="+immigrationId+"&candidateId="+candidateId+"&templateId="+templateId;
                            }
                            if(status == 'sendRequest'){
                            	window.location.href = sourceleadUrl + immiModulePath + "/viewImmigrationList";
                            }
                       }else{
                       	 showError("Operation failed, please try again");
                       }
                        
                      }
                    
                });
                return true;
            } else {
                return false;
            }
            $("#newPetitionTemplateDiv").load("/app" + immiModulePath + "/loadNewPetitionPage?forward=two");
        }else if(status == 'save'){
        	
        	if ($("#petitionDetailsForm").validate(false, validationSettings)) {
                var formData = getFormData("petitionDetailsForm");
                $.ajax({
                    url: sourceleadUrl + immiModulePath + "/savePetitionDetailsPage?removedResidenceIds="+removedResidenceIds+"&status="+status,
                    type: "POST",
                    data: formData,
                    success: function (result) {
                    	if (result && result.response === "INVALID_DATA") {
                          	 showError(result.responseMessage);
                          }else if (result.response === "AWKWARD") {
                              location.href = "/errorPage";
                          }else if(result.response === 'SUCCESS') {
                        	  immigrationId = result.data[0];
                          	candidateId = result.data[1];
                          	templateId= result.data[2];
                          }else{
                          	 showError("Operation failed, please try again");
                          }
                    	
                      }
                    
                });
                return true;
            } else {
                return false;
            }
        }else if (status == 'three') {
            $("#newPetitionTemplateDiv").load("/app" + immiModulePath + "/loadNewPetitionPage?forward=three");
        } else if (status == 'four') {
            $("#newPetitionTemplateDiv").load("/app" + immiModulePath + "/loadNewPetitionPage?forward=four");
        } else if (status == 'five') {
            $("#newPetitionTemplateDiv").load("/app" + immiModulePath + "/loadNewPetitionPage?forward=five");
        } else if (status == 'six') {
            $("#newPetitionTemplateDiv").load("/app" + immiModulePath + "/loadNewPetitionPage?forward=six");
        } else if (status == 'seven') {
            $("#newPetitionTemplateDiv").load("/app" + immiModulePath + "/loadNewPetitionPage?forward=seven");
        }
    }// immigration end if don't required comment above code end here.
    
    this.displayPetitionDetails = function(immigrationId){
    	$.ajax({
            url: sourceleadUrl + immiModulePath + "/viewImmigrationById?immigrationId="+immigrationId,
            type: "GET",
            success: function (result) {
                $("#detailsTab").html(result);
            }
        });
    }

    this.savePetitionTemplateDetailsValues = function () {
        $.ajax({
            url: sourceleadUrl + immiModulePath + "/savePetitionTemplateDetailsValues?templateId=" + immTemplId,
            type: "GET",
            /*data: templateId,*/
            success: function (data) {
                if (data == 'success') {
                    if (data == "success") {
                        window.location.href = sourceleadUrl + immiModulePath + "/viewImmigrationTemplateList"; // redirect to tempalte list.
                        immTemplId = "";
                    }
                }
            }
        });
    }
    
    this.savePassportVisaDetails= function(idType) {
    	var formId= "";
	if(idType =="Passport"){
			formId ="CandidatePassportform";
			$("#passport_save_id").attr("disabled",true);
		}
	 	else if(idType =="Visa"){
  		formId ="CandidateVisaform";
  		$("#visa_save_id").attr("disabled",true);
  		 	  	}
	if ($('#'+formId).validate(false, validationSettings)) {
			
			var formData = getFormData(formId);
		 	var candidateId = $('#candidateID').val();
            var immigrationId = $('#immigrationId').val();
            $.ajax({
            url: sourceleadUrl + immiModulePath + "/savePassportVisaDetailsPage?" + tokenParamName + "=" + tokenValue + "&candidateId="+candidateId+"&immigrationId="+immigrationId+"&idType="+idType,
            type : "POST",
			data : formData,
			success : function(result) {
							if (result && result.response === "INVALID_DATA") {
		                    	 showError(result.responseMessage);
		                    }else if (result.response === "AWKWARD") {
		                        location.href = "/errorPage";
		                    }else if(result.response === 'SUCCESS') {
		                    	if (idType == 'Passport') {
									$('#popPassView').hide();
									$('.popMask').fadeOut();
									immigration.displayPassportDetails(candidateId, 'Passport');
								} else if (idType == 'Visa') {
									$("#popUpVisa").hide();
									$(".popMask").fadeOut(200); // close the popup code															// popup code
									immigration.displayVisaDetails(candidateId,'Visa');
								}
		                    }else{
		                    	 showError("Operation failed, please try again");
		                    }
							
			}
		});
		return true;
	} else {
		return false;
	}
}

    this.editPassportVisaDetailsByRowId=function(id,idType){
		$.ajax({
	        url: sourceleadUrl + immiModulePath + "/getCandidatePassportVisaDetailsById?id="+id,
	        type: "GET",
	        success : function(data) {
	        	if(data != null){
	        		if(idType=='Passport'){
	        		formObj = $('#CandidatePassportform')[0]; // load the child form
        			var childPopNames=["idNumber","country", "validFrom","validTo"];
        			$("#addPass").trigger('click');
        			immigration.udpatePopupFormdata(formObj,childPopNames,data);
        			$('#validFrom').val(data.validFromDateStr);
        			$('#validTo').val(data.validToDateStr);
        			$('#countryName').val(data.country);
        			$('#candidatePassportId').val(data.id);	
	        		}
                    else if(idType=='Visa'){
                    formObj = $('#CandidateVisaform')[0]; // load the child form
        			var childPopNames=["visaType","idNumber","country", "validFrom","validTo"];
        			$("#addVisa").trigger('click');
        			immigration.udpatePopupFormdata(formObj,childPopNames,data);
        			$('#visaType').val(data.visaType);
        			$('#visaCountry').val(data.country);
        			$('#visaValidFrom').val(data.validFromDateStr);
        			$('#visaValidTo').val(data.validToDateStr);
        			$('#candidateVisaId').val(data.id);	
                                }
	        		
	        	}
	        }
	    });
	}
 
    
    this.editImmigrationCandidateForm=function(id){
		$.ajax({
	        url: sourceleadUrl + immiModulePath + "/editImmigrationCandidateFormDetailsById?id="+id,
	        type: "GET",
	        success: function (data) {
	        	var formObj='';
	        	if(data!= null){
	        		
	        			formObj = $('#formSave_Form')[0]; // load the child form
	        			//var fomValues=["name","designation", "address","startMonth","startYear","endMonth","endYear"];
	        			$("#addForm").trigger('click');
	        			//udpateEduFormdata(formObj,fomValues,data);
	        			$('#formName').val(data.formName); // to select the dropdwon value based on id value.
	        			$('#formDescription').text(data.description);
	        			//$('#immigrationDocumentName').text(data.fileUrl); 
	        			$('#immigrationEditDocDiv').show();
	        			$('#immigrationDocDiv').hide();
	        			$('#uploadFileForm').removeAttr("data-validation");
	        			$('#immigrationDocumentName').text(data.fileName);
	        			if(data.isRequired == 'Y'){
	        				//$('#isRequired').prop("checked",true);	
	        				$('#formSave_Form input[name="isRequired"]').prop('checked', true);
	        			}
	        			$('#immigrationCandidateFormId').val(data.id);
	        	
	        	   
	        	}
	        }
	    });
	}
	this.updateImmigrationStatus = function(immigrationId) {


		$.ajax({
			url : sourceleadUrl + immiModulePath+"/updateImmigrationStatus?id=" + immigrationId+"&candidateId="+candidateId+"&templateId="+templateId,
			type : "GET",
			success : function(data) {
				eval("jsonObj=" + data);
				if (typeof jsonObj.errorMsg != 'undefined') {
					//alert(" error Msg"+jsonObj.errorMsg);
					var warningMsg="Below Required Tab(s) Data is Missing!...\n"+jsonObj.errorMsg;
					showWarningPopup("", warningMsg);
				} else {
					location.reload();
				}
			}
		});
	}
    this.displayPassportDetails=function(candidateId,Passport,searchValue){
   		$.ajax({
           url: sourceleadUrl + immiModulePath + "/getCandidatePassportVisaDetails?candidateId="+candidateId+ "&idType=" + Passport+ "&searchValue=" + searchValue,
           type: "GET",
           success: function (result) {
               $("#passportsTab").html(result);
               $("#searchValuePassport").val(searchValue);
           }
       });
   }	
    
    
   this.displayVisaDetails=function(candidateId,Visa,searchValue){
   		$.ajax({
           url: sourceleadUrl + immiModulePath + "/getCandidatePassportVisaDetails?candidateId="+candidateId+ "&idType=" + Visa+ "&searchValue=" + searchValue,
           type: "GET",
           success: function (result) {
               $("#visasTab").html(result);
               $("#searchValueVisa").val(searchValue);
              
           }
       });
   }	

this.deletePassportVisaDetailsById=function(id,idType){
	var candidateId = $("#candidateID").val();
	$.ajax({
        url: sourceleadUrl + immiModulePath + "/deleteCandidatePassportVisaDetails?id="+id+"&candidateId="+candidateId+ "&idType=" + idType,
        type: "GET",
        success: function (result) {
        	if(result=='success'){
        		if(idType=='Passport'){
        			immigration.displayPassportDetails(candidateId,idType);
        		}
        		else if (idType == 'Visa') {
        			immigration.displayVisaDetails(candidateId,idType);
        		}
        	}
        }
    });
}


    this.saveCandidateFamilyDetails= function(relationType) {
    	var formId= "";
    	if(relationType =="Spouse"){
    		formId ="spouseDetailsForm";
    	}
    	else if(relationType =="Child"){
    		formId ="childDetailsForm";
    	}
    	else if(relationType =="Father"){
    		formId ="fatherDetailsForm";
    	}
    	else if(relationType =="Mother"){
    		formId ="motherDetailsForm";
    	}
		if ($('#'+formId).validate(false, validationSettings)) {
			var formData = getFormData(formId);
			 var candidateId = $('#candidateID').val();
			 var immigrationId = $('#immigrationID').val();
			$.ajax({
				url : sourceleadUrl + immiModulePath+ "/saveCandidateFamilyDetails?candidateId="+candidateId+"&relationType="+relationType+"&memberId="+familyMemberId+"&immigrationId="+immigrationId,
				type : "POST",
				data : formData,
				success : function(result) {
					if (result && result.response === "INVALID_DATA") {
                   	 showError(result.responseMessage);
                   }else if (result.response === "AWKWARD") {
                       location.href = "/errorPage";
                   }else if(result.response === 'SUCCESS') {
                	   $(".popMask").fadeOut(200); // close the popup code
       				$("#spousePopView").fadeOut(100);// close the popup code
       				immigration.displayFamilyDetails(candidateId); 
       				}else{
                   	 showError("Operation failed, please try again");
                   }
				}
			});

			return true;
		} else {
			return false;
		}
	}
    this.saveCandidateForms= function () {
		var validationSettings = {
	            errorMessagePosition: 'element',
	            borderColorOnError: '',
	            scrollToTopOnError: true,
	            dateFormat: 'yyyy/mm/dd'
	        };
		if ($('#formSave_Form').validate(false, validationSettings)) {
			var formData = getFormData("formSave_Form");
			var candidateId = $('#candidateID').val();
			var templateId = $('#templateID').val();
			var immigrationId = $('#immigrationID').val();
	        $.ajax({
	            url: sourceleadUrl + immiModulePath + "/saveCandidateImmigrationForms?"+ tokenParamName + "=" + tokenValue + "&candidateId="+candidateId+"&immigrationId="+immigrationId+"&templateId="+templateId,
	            type: "POST",
	            data: formData,
	            success: function (data) {
	                if (data == 'success') {
	                	$('#viewFormpop').hide();
	                	$('.popMask').fadeOut();
	                	immigration.displayImmigrationCandidateForms(candidateId,templateId,immigrationId,'');
	                }
	            }
	        });

	        return true;
	    } else {
	        return false;
	    }
		
	}
    
    this.deleteImmigrationCandidateForm=function(id){
    	var candidateId = $("#candidateID").val();
    	var templateId = $('#templateID').val();
		var immigrationId = $('#immigrationID').val();
    	$.ajax({
            url: sourceleadUrl + immiModulePath + "/deleteImmigrationCandidateFormDetails?id="+id,
            type: "GET",
            success: function (result) {
            	if(result =='success'){
            		immigration.displayImmigrationCandidateForms(candidateId,templateId,immigrationId,'');
            	     
            	}
            }
        });
    }
    
    this.displayFamilyDetails=function(candidateId){
    	var searchVal= $("#familySerach").val();
    	$.ajax({
            url: sourceleadUrl + immiModulePath + "/getCandidateFamilyDetails?candidateId="+candidateId+"&searchVal="+searchVal,
            type: "GET",
            success: function (result) {
                $("#familiesTab").html(result);
                addfamilyPopupEventListener();
                $("#familySerach").val(searchVal);
            }
        });
    }	
	this.deleteFamilyDetailsById = function(delId){
		$.ajax({
            url: sourceleadUrl + immiModulePath + "/deleteCandidateFamilyDetails?id="+delId,
            type: "GET",
            success: function (result) {
            	if(result=='success'){
            	     immigration.displayFamilyDetails(candidateId) //candidateId global variable
            	     
            	}
            }
        });
	}
	
	
	this.editFamilyDetailsById = function(id, relationType){
		familyMemberId = id; // update the variable with family member id in edit mode.
		$.ajax({
            url: sourceleadUrl + immiModulePath + "/getCandidateFamilyDetailsById?id="+id,
            type: "GET",
            success: function (data) {
            	var formObj='';
            	if(data!= null){
            		if(relationType =='Child'){
            			formObj = $('#childDetailsForm')[0]; // load the child form
            			var childPopNames=["firstName","middleName", "lastName","placeOfBirth","currentAddress"];
            			$("#addChil").trigger('click');
            			immigration.udpatePopupFormdata(formObj,childPopNames,data);
            			$('#dateOfBirthChild').val(data.dateOfBirthStr);
            		}else if(relationType =='Spouse'){
            			formObj = $('#spouseDetailsForm')[0]; // load the Spouse form
            			var spousePopNames=["firstName","middleName", "lastName","marriagePlace","placeOfBirth","currentAddress"];
            			$("#addSpou").trigger('click');
            			immigration.udpatePopupFormdata(formObj,spousePopNames,data);
            			$('#marraigeDate').val(data.marraigeDateStr);
            			$('#marriageEndDate').val(data.marriageEndDateStr);
            			$('#dateOfBirthSpouse').val(data.dateOfBirthStr);
            		}else if(relationType =='Father'){
            			formObj = $('#fatherDetailsForm')[0]; // load the Father form
            			var fatherPopNames=["firstName","middleName", "lastName","placeOfBirth","currentAddress"];
            			$("#addFath").trigger('click');
            			immigration.udpatePopupFormdata(formObj,fatherPopNames,data);
            			$('#dateOfBirthFather').val(data.dateOfBirthStr);
            		}else if(relationType =='Mother'){
            			formObj = $('#motherDetailsForm')[0]; // load the mother form
            			var motherPopNames=["firstName","middleName", "lastName","placeOfBirth","currentAddress"];
            			$("#addMoth").trigger('click');
            			immigration.udpatePopupFormdata(formObj,motherPopNames,data);
            			$('#dateOfBirthMother').val(data.dateOfBirthStr);
            		}
            		//familyMemberId = ''// reset the value
            	}
            }
        });
	}
	
	this.displayImmigrationCandidateForms = function(candidateId,templateId, immigrationId,searchVal){
		$.ajax({
	        url: sourceleadUrl + immiModulePath + "/getCandidateImmigrationFormsList?candidateId="+candidateId+"&templateId="+templateId+"&immigrationId="+immigrationId+"&searchVal="+searchVal,
	        type: "GET",
	        success: function (result) {
	            $("#formsTab").html(result);
	            $("#immigraFormTabSerach").val(searchVal);
	        }
	    });
	}
	
	this.udpatePopupFormdata = function(formObj, idsArray, dataObj) {
		  var formId = formObj.id;
		   $.each(formObj, function(key, value) {
			var formElemName = value.name;
			if (typeof formElemName!= 'undefined' && idsArray.indexOf(formElemName) > -1) {
				// below code added for date fields in forms based on name paroperty of the field. date should contain date string in filed name.
				if(formElemName.toLowerCase().indexOf("date")>-1 && dataObj[formElemName] != null && typeof  dataObj[formElemName] != 'undefined' && dataObj[formElemName]!=''){
					var dateVal =  moment(dataObj[formElemName]).format("DD-MM-YYYY");
					$("#"+formId+" input[name='"+formElemName+"']").val(dateVal); // sets date values to the form.
				}// above code checks the date field names and set the formatted date value to the popup.
				else{
				 $("#"+formId+" input[name='"+formElemName+"']").val(dataObj[formElemName]); // setting non date values to the from
				}
			}

		});
	}
	
	this.deleteImmigrationDetailsByRowId = function(immigrationId){
		$.ajax({
	        url: sourceleadUrl + immiModulePath + "/deleteImmigrationDetailsById?immigrationId="+immigrationId,
	        type: "GET",
	        success: function (result) {
	        	$("#remove_immigration_pop").fadeOut(200);
	    		$(".project_inn_mask").fadeOut(200);
	    		window.location.href = sourceleadUrl + immiModulePath + "/viewImmigrationList";
	        }
		});
	}
	
}

function deleteEDucationDetailsById(id){
	var candidateId = $("#candidateID").val();
	$.ajax({
        url: sourceleadUrl + immiModulePath + "/deleteCandidateEduDetails?id="+id+"&candidateId="+candidateId,
        type: "GET",
        success: function (result) {
        	if(result=='success'){
        	  getCandidateEducationList(candidateId) //candidateId global variable
        	     
        	}
        }
    });
}

function editEducationDEtailsByRowId(id){
	 // update the variable with family member id in edit mode.
	var candidateId = $("#candidateID").val();
	$.ajax({
        url: sourceleadUrl + immiModulePath + "/getCandidateEducationDetailsById?id="+id+"&candidateId="+candidateId,
        type: "GET",
        success: function (data) {
        	var formObj='';
        	if(data!= null){
        		
        			formObj = $('#eduSave_Form')[0]; // load the child form
        			var fomValues=["name","specialization", "startDate","endDate","gradeType","grade","institute","address"];
        			$("#addEdu").trigger('click');
        			udpateEduFormdata(formObj,fomValues,data);
        			$('#eduName').val(data.name); // to select the dropdwon value based on id value.
        			$('#scoreEducationType').val(data.gradeType);
        			$('#immigrationEduStartDate12').val(data.startDateStr);
        			$('#immigrationEduEndDate').val(data.endDateStr);
        			if(data.educationFileName != null){
        			$('#immigrationEduDocDivEdit').show();
    	        	$('#immigrationEduDocDiv').hide();
        			$('#immigrationEduDocName').text(data.educationFileName);
        			}
        			$('#candidateEducationId').val(data.id);
        	
        	   
        	}
        }
    });
}

function editEmpDetailsByRowId(id){
	 // update the variable with family member id in edit mode.
	var candidateId = $("#candidateID").val();
	$.ajax({
        url: sourceleadUrl + immiModulePath + "/getCandidateEmpDetailsById?id="+id+"&candidateId="+candidateId,
        type: "GET",
        success: function (data) {
        	var formObj='';
        	if(data!= null){
        		
        			formObj = $('#employerDetails_form')[0]; // load the child form
        			var fomValues=["name","designation", "address","startMonth","startYear","endMonth","endYear"];
        			$("#addEmpl").trigger('click');
        			udpateEduFormdata(formObj,fomValues,data);
        			$('#startMonth').val(data.startMonth); // to select the dropdwon value based on id value.
        			$('#endMonth').val(data.endMonth);
        			if(data.employerFileName != null){
        			$('#immigrationEmpDocDivEdit').show();
	        		$('#immigrationEmpDocDiv').hide();
        			$('#immigrationEmpDocName').text(data.employerFileName);	
        			}
        			$('#candidateEmpId').val(data.id);
        	
        	   
        	}
        }
    });
}

function udpateEduFormdata (formObj, idsArray, dataObj) {
	  var formId = formObj.id;
	   $.each(formObj, function(key, value) {
		var formElemName = value.name;
		if (typeof formElemName!= 'undefined' && idsArray.indexOf(formElemName) > -1) {
			// below code added for date fields in forms based on name paroperty of the field. date should contain date string in filed name.
			/*if(formElemName.toLowerCase().indexOf("date")>-1 && dataObj[formElemName] != null && typeof  dataObj[formElemName] != 'undefined' && dataObj[formElemName]!=''){
				console.log(" inside date value---> ");
				var dateVal =  moment(dataObj[formElemName]).format("DD-MM-YYYY");
				$("#"+formId+" input[name='"+formElemName+"']").val(dateVal); // sets date values to the form.
			}// above code checks the date field names and set the formatted date value to the popup.
*/			
			 $("#"+formId+" input[name='"+formElemName+"']").val(dataObj[formElemName]); // setting non date values to the from
		}

	});
}

function deleteEmpDetailsById(id){
	var candidateId = $("#candidateID").val();
	$.ajax({
        url: sourceleadUrl + immiModulePath + "/deleteCandidateEmployerDetails?id="+id+"&candidateId="+candidateId,
        type: "GET",
        success: function (result) {
        	if(result=='success'){
        		getCandidateEmployerList(candidateId) //candidateId global variable
        	     
        	}
        }
    });
}



function validateTemplateName() {
    var templateName = document.getElementById("templateName").value.trim();
    if (templateName !== null && templateName !== "") {
        $.ajax({
            type: "POST",
            url: sourceleadUrl + immiModulePath + "/validateTemplateName?" + tokenParamName + "=" + tokenValue + "&templateName=" + templateName + "&templateId=" + immTemplId,
            success: function (data) {
                if (data && data === true) {
                    if ($('#templateNameDiv').find('p.jquery_form_error_message').length === 0)
                        $("#templateName").after("<p class='jquery_form_error_message'>" + templateName + " is Already Existed</span>");
                    $(".nextBtn").addClass("disableBtn");
                } else {
                    $('#templateNameDiv').find('p.jquery_form_error_message').remove();
                    $(".nextBtn").removeClass("disableBtn");
                }
            }
        });
    }
}
function checkFormNames() {
    formNames = [];
    var focusedElement;
    $("input[data-name='formName']").each(function () {
        if ($(this).is(':focus')) {
            focusedElement = $(this);
        } else {
            $(this).parent().find('p.jquery_form_error_message').remove();
            if (formNames.indexOf($(this).val().trim()) < 0) {
                formNames.push($(this).val().trim());
            } else {
                $(".nextBtn").addClass("disableBtn");
                if ($(this).find('p.jquery_form_error_message').length === 0)
                    $(this).after("<p class='jquery_form_error_message'>" + $(this).val().trim() + " is Already Existed</span>");
                return;
            }
        }
    });
    if (focusedElement) {
        focusedElement.parent().find('p.jquery_form_error_message').remove();
        if (formNames.indexOf(focusedElement.val().trim()) < 0) {
            formNames.push(focusedElement.val().trim());
        } else {
            $(".nextBtn").addClass("disableBtn");
            if (focusedElement.find('p.jquery_form_error_message').length === 0)
                focusedElement.after("<p class='jquery_form_error_message'>" + focusedElement.val().trim() + " is Already Existed</span>");
            return;
        }
    }
    if (formNames.length === $("input[data-name='formName']").length) {
        $(".nextBtn").removeClass("disableBtn");
        return true;
    }
    return false;
}
function checkTaskNames(newName) {
    var table = document.getElementById("templateTaskListTable");
    var rows = table.rows;
    for (var i = 1; i < rows.length; i++) {
        var name = rows[i].firstChild.textContent.trim();
        if (newName === name) {
            $("#taskName").after("<p class='jquery_form_error_message'>is Already Existed</span>");
            $("#addTaskBtn").addClass("disableBtn");
            return;
        } else {
            $("#taskName").parent().find('p.jquery_form_error_message').remove();
            $("#addTaskBtn").removeClass("disableBtn");
        }
    }
}

function saveEmployerDetails(status) {
	var validationSettings = {
            errorMessagePosition: 'element',
            borderColorOnError: '',
            scrollToTopOnError: true,
            dateFormat: 'yyyy/mm/dd'
        };
	if ($('#employerDetails_form').validate(false, validationSettings)) {
        var formData = getFormData("employerDetails_form");
        var candidateId = $('#candidateID').val();
        var immigrationId = $('#immigrationID').val();
        $.ajax({
            url: sourceleadUrl + immiModulePath + "/saveImmigrationEmployerDetails?" + tokenParamName + "=" + tokenValue + "&candidateId="+candidateId+"&immigrationId="+immigrationId,
            type: "POST",
            data: formData,
            success: function (result) {
				if (result && result.response === "INVALID_DATA") {
                	 showError(result.responseMessage);
                }else if (result.response === "AWKWARD") {
                    location.href = "/errorPage";
                }else if(result.response === 'SUCCESS') {
                	$('#emploView').hide();
                	$('.popMask').fadeOut();
                	getCandidateEmployerList(candidateId);
                }else{
                	 showError("Operation failed, please try again");
                }
            }
        });

        return true;
    } else {
        return false;
    }
	
}
$('body').on('blur', '#employerDetails_form', function () {
    $("#employerDetails_form").showHelpOnFocus();
    $("#employerDetails_form").validateOnBlur(false, validationSettings);
});


function saveCandidateDocs() {
	var validationSettings = {
            errorMessagePosition: 'element',
            borderColorOnError: '',
            scrollToTopOnError: true,
            dateFormat: 'yyyy/mm/dd'
        };
	if ($('#candidate_Immigration_Doc').validate(false, validationSettings)) {
		var formData = getFormData("candidate_Immigration_Doc");
		var candidateId = $('#candidateID').val();
		var templateId = $('#templateID').val();
		var immigrationId = $('#immigrationID').val();
        $.ajax({
            url: sourceleadUrl + immiModulePath + "/saveCandidateImmigrationDocuments?"+ tokenParamName + "=" + tokenValue + "&candidateId="+candidateId+"&immigrationId="+immigrationId+"&templateId="+templateId,
            type: "POST",
            data: formData,
            success: function (data) {
                if (data == 'success') {
                	$('#docuView').hide();
                	$('.popMask').fadeOut();
                	displayImmigrationCandidateDocs(candidateId,templateId,immigrationId,'');
                }
            }
        });

        return true;
    } else {
        return false;
    }
	
}
$('body').on('blur', '#candidate_Immigration_Doc', function () {
    $("#candidate_Immigration_Doc").showHelpOnFocus();
    $("#candidate_Immigration_Doc").validateOnBlur(false, validationSettings);
});


function displayImmigrationCandidateDocs(candidateId,templateid,immigrationId,searchVal){
	$.ajax({
        url: sourceleadUrl + immiModulePath + "/getCandidateImmigrationCandidateDocsList?candidateId="+candidateId+"&templateid="+templateid+"&immigrationId="+immigrationId+"&searchVal="+searchVal,
        type: "GET",
        success: function (result) {
            $("#docsTab").html(result);
            $("#immigraDocTabSerach").val(searchVal);
        }
    });
}


function getCandidateEducationList(candidateId){
	var searchVal = $("#educationSearchVal").val();
	$.ajax({
        url: sourceleadUrl + immiModulePath + "/getCandidateEducationList?candidateId="+candidateId+"&searchVal="+searchVal,
        type: "GET",
        success: function (result) {
            $("#educationsTab").html(result);
            $("#educationSearchVal").val(searchVal);
        }
    });
}

function getImmigrationTasks(immigrationId, status, searchVal){
	$.ajax({
        url: sourceleadUrl + immiModulePath + "/getImmigrationTasksList?immigrationId="+immigrationId+"&taskStatus="+status+"&searchVal="+searchVal,
        type: "GET",
        success: function (result) {
            $("#tasksTab").html(result);
            $("#tasksSearchVal").val(searchVal);
        }
    });
}

function getCandidateEmployerList(candidateId){
	var searchEmployerVal = $("#employersDataSearch").val();
	$.ajax({
        url: sourceleadUrl + immiModulePath + "/getCandidateEmployerList?candidateId="+candidateId+"&searchVal="+searchEmployerVal,
        type: "GET",
        success: function (result) {
            $("#employersTab").html(result);
            $("#employersDataSearch").val(searchEmployerVal);
        }
    });
}

function saveResidenceDetails(status) {
	if ($('#residenceDetails_form').validate(false, validationSettings)) {
        var formData = getFormData("residenceDetails_form");
        var candidateId = $('#candidateID').val();
        var immigrationId = $('#immigrationID').val();
        $.ajax({
            url: sourceleadUrl + immiModulePath + "/saveImmigrationResidenceDetails?" + tokenParamName + "=" + tokenValue + "&candidateId="+candidateId+"&immigrationId="+immigrationId,
            type: "POST",
            data: formData,
            success: function (result) {
            	if (result && result.response === "INVALID_DATA") {
               	 showError(result.responseMessage);
               }else if (result.response === "AWKWARD") {
                   location.href = "/errorPage";
               }else if(result.response === 'SUCCESS') {
            	   $('#popResiView').hide();
               	  $('.popMask').fadeOut();
               	  getCandidateResidenceList(candidateId);
               }else{
               	 showError("Operation failed, please try again");
               }
            	
            }
        });

        return true;
    } else {
        return false;
    }
	
}

function getCandidateResidenceList(candidateId){
	var searchVal= $("#residenceSearch").val();
	$.ajax({
        url: sourceleadUrl + immiModulePath + "/getCandidateResidenceList?candidateId="+candidateId+"&searchVal="+searchVal,
       type: "GET",
        success: function (result) {
           $("#residencesTab").html(result);
           $("#residenceSearch").val(searchVal);
       }
   });
}

function deleteResidenceDetailsById(id){
	var candidateId = $("#candidateID").val();
	$.ajax({
        url: sourceleadUrl + immiModulePath + "/deleteCandidateResidenceDetails?id="+id+"&candidateId="+candidateId,
        type: "GET",
        success: function (result) {
        	if(result=='success'){
        		getCandidateResidenceList(candidateId) //candidateId global variable
        	     
        	}
        }
    });
}

function editResidenceDEtailsByRowId(id, isCurr){
	 // update the variable with residence member id in edit mode.
	var candidateId = $("#candidateID").val();
	$.ajax({
     url: sourceleadUrl + immiModulePath + "/getCandidateResidenceDetailsById?id="+id+"&isCurr="+isCurr,
     type: "GET",
     success: function (data) {
     	var formObj='';
     	if(data!= null){
     		
     			formObj = $('#residenceDetails_form')[0]; // load the child form
     			var fomValues=["location","streetAddress", "country","city","state","zipcode","startDate","endDate"];
     			$("#addResi").trigger('click');
     			udpateEduFormdata(formObj,fomValues,data); 
     		/*	$('#eduName').val(data.name); // to select the dropdwon value based on id value.
     			$('#scoreEducationType').val(data.gradeType);*/
     			$('#residenceStartDate').val(data.startDateStr);
     			$('#residenceEndDate').val(data.endDateStr);
     			$('#residenceId').val(data.id);
     	        if(isCurr =='Y'){
     	        	$('#resiEnddatediv').hide(); // remove end date 
     	        	$('#isCurrResi').val('Y');
     	        	 $("#residenceEndDate").removeAttr("data-validation");
     	        }else{
     	        	$('#isCurrResi').val('N');
     	        	$('#resiEnddatediv').show();
     	        	 $("#residenceEndDate").attr("data-validation","required");
     	        }
     	   
     	}
     }
 });
}

function uploadRequestDoc(){
	if ($('#uploadRequestDoc_Form').validate(false, validationSettings)) {
	var formData = getFormData("uploadRequestDoc_Form");
	var candidateId = $("#candidateID").val();
	var immigrationId = $('#immigrationID').val();
	var id = $("#uploadIdSave").val();
	var templateId = $('#templateID').val();
	$.ajax({
        url: sourceleadUrl + immiModulePath + "/uploadRequstedDoc?id="+id+"&candidateId="+candidateId+"&immigrationId="+immigrationId,
        type: "POST",
        data: formData,
        success: function (result) {
        	if(result=='success'){
        		$('#uploadCanDocPop').hide();
            	$('.popMask').fadeOut();
            	displayImmigrationCandidateDocs(candidateId,templateId,immigrationId,'');
        	     
        	}
        }
    });
	}
}

function editDocumentDetailsByRowId(id){
	 // update the variable with residence member id in edit mode.
	var candidateId = $("#candidateID").val();
	$.ajax({
     url: sourceleadUrl + immiModulePath + "/editDocumentDetailsByRowId?id="+id,
     type: "GET",
     success: function (data) {
     	var formObj='';
     	if(data!= null){
     		
     			formObj = $('#candidate_Immigration_Doc')[0]; // load the child form
     			$("#addDocu").trigger('click');
     		/*	$('#eduName').val(data.name); // to select the dropdwon value based on id value.
     			$('#scoreEducationType').val(data.gradeType);*/
     			$('#documentType').val(data.documentType.id);
     			if(data.documentOtherType){
     			document.getElementById("documentOtherTypeDiv").style.display="block";
     			$('#documentOtherType').val(data.documentOtherType);
     			}else{
     			document.getElementById("documentOtherTypeDiv").style.display="none";
     			}
     			$('#description1').text(data.description);
     			if(data.allowMultiple == 'Y'){
     				$('#candidate_Immigration_Doc input[name="multiple"]').prop('checked', true);
     			}if(data.isRequired == 'Y'){
     				$('#candidate_Immigration_Doc input[name="isRequired"]').prop('checked', true);
     			}
     			$('#immigrationCandidateDocId').val(data.id);
     	
     	   
     	}
     }
 });
}

function deleteDocumentDetailsByRowId(id){
	var templateId = $('#templateID').val();
	var candidateId = $("#candidateID").val();
	var immigrationId = $('#immigrationID').val();
	$.ajax({
        url: sourceleadUrl + immiModulePath + "/deleteDocumentDetailsByRowId?id="+id,
        type: "GET",
        success: function (result) {
        	if(result=='success'){
        	displayImmigrationCandidateDocs(candidateId,templateId,immigrationId,'');
        	     
        	}
        }
    });
}

function uploadRequestForm(){
	if ($('#uploadRequestForm_Form').validate(false, validationSettings)) {
	var formData = getFormData("uploadRequestForm_Form");
	var candidateId = $("#candidateID").val();
	var immigrationId = $('#immigrationID').val();
	var id = $("#uploadFormIdSave").val();
	$.ajax({
        url: sourceleadUrl + immiModulePath + "/uploadRequstedForm?id="+id+"&candidateId="+candidateId+"&immigrationId="+immigrationId,
        type: "POST",
        data: formData,
        success: function (result) {
        	if(result=='success'){
        		$('#uploadFormPop').hide();
            	$('.popMask').fadeOut();
            	immigration.displayImmigrationCandidateForms(candidateId,templateId,immigrationId,''); 
        	}
        }
    });
	}
}
function validateEduname()  
{	var validationSettings = {
            errorMessagePosition: 'element',
            borderColorOnError: '',
            scrollToTopOnError: true,
            dateFormat: 'yyyy/mm/dd'
        };
	var candidateId = $("#candidateID").val();
	  var name = document.getElementById("eduName").value; 
	  $('#nameIdDiv').find('p.jquery_form_error_message').remove();
		 $.ajax({
		    	type: "POST",
		        url:sourceleadUrl + immiModulePath + "/validateImmiEduname?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&name="+name, 
		        success: function(data) {
		        	if(data == true){
		        		  $('#nameIdDiv').find('p.jquery_form_error_message').remove();
		        		  $("#eduName").after("<p class='jquery_form_error_message'>"+name+" is Already Existed</span>");
						  $("#eduSave_Button").attr('disabled','disabled');
						 // return false;
		        	}
		        	else{
		        		 $('#nameIdDiv').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#nameIdDiv').find('p.jquery_form_error_message').remove(); 
                       $("#eduSave_Button").removeAttr("disabled");
		        	}
		        },
		    });
}


function saveNotesDatails(status) {
	if ($('#notesDetails_form').validate(false, validationSettings)) {
        var formData = getFormData("notesDetails_form");
        var candidateId = $('#candidateID').val();
        var immigrationId = $('#immigrationID').val();
        $.ajax({
            url: sourceleadUrl + immiModulePath + "/saveImmigrationNotesDetails?" + tokenParamName + "=" + tokenValue + "&candidateId="+candidateId+"&immigrationId="+immigrationId,
            type: "POST",
            data: formData,
            success: function (data) {
            	if (data === 'success') {
            		$(".popMask").fadeOut(200);
    				$("#popNoteView").hide(100);
                	getNotesListByImmigration(candidateId);
                }
            }
        });

        return true;
    } else {
        return false;
    }
	
}

function getPetitionTypeOnCountries(selectedCountry, selectedCountryOption){
	var selectedValue = selectedCountry.val();
	$.ajax({
	    type: "GET",
        url: sourceleadUrl+immiModulePath+"/getPetitionTypeOnLocation?locationVal="+selectedValue,  
        success: function(json) {
        	$('#petitionTypeTemplate').empty();
        	$('#petitionTypeTemplate').append('<option value="">--Select Petition Type--</option>');
             for(var i=0;i<json.length;i++){
            	 var opt = json[i];
            	 $('#petitionTypeTemplate').append('<option value='+opt.id+'>'+opt.name+'</option>');
            } 
            $('#petitionTypeTemplate option[value="'+selectedCountryOption+'"]').prop("selected", true);
            $('#petitionTypeTemplate').trigger("chosen:updated");
        }
	}); 
}

function getNotesListByImmigration(candidateId){
	 var immigrationId = $('#immigrationID').val();
	$.ajax({
        url: sourceleadUrl + immiModulePath + "/getNotesListByImmigrationId?" + tokenParamName + "=" + tokenValue + "&candidateId="+candidateId+"&immigrationId="+immigrationId,
       type: "GET",
        success: function (result) {
           $("#notesTab").html(result);
       }
   });
}

function getImmigrationActivies(){
	 var immigrationId = $('#immigrationID').val();
	$.ajax({
        url: sourceleadUrl + immiModulePath + "/getImmigrationActiviesList?" + tokenParamName + "=" + tokenValue +"&immigrationId="+immigrationId,
       type: "GET",
        success: function (result) {
           $("#activitiesTab").html(result);
       }
   });
}

function searchPassportValue(){
	var validationSettings = {
            errorMessagePosition: 'element',
            borderColorOnError: '',
            scrollToTopOnError: true,
            dateFormat: 'yyyy/mm/dd'
        };
	var searchValue =document.getElementById("searchValuePassport").value; 
	var candidateId = $('#candidateID').val();
	immigration.displayPassportDetails(candidateId,'Passport',searchValue);
}

function searchVisaValue(){
	var validationSettings = {
            errorMessagePosition: 'element',
            borderColorOnError: '',
            scrollToTopOnError: true,
            dateFormat: 'yyyy/mm/dd'
        };
	var searchValue =document.getElementById("searchValueVisa").value; 
	var candidateId = $('#candidateID').val();
	immigration.displayVisaDetails(candidateId,'Visa',searchValue);
	
}



