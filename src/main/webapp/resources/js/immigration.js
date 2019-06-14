var immigration = new Immigration();
var immiModulePath = "/immigration";
var immTemplId = '';
var immigrationId = '';
var candidateId = '';
var idNumber = '';
var familyMemberId = '';
var templateId='';
var formNames = [];
var myImmigrationPage ="";
var curUscisMyImmi ="";
function Immigration() {
    this.gotoTemplatePreviousPage = function (temp) {
        $("#addNewPetitionTemplateDiv").empty();
        var cc=$("#countryName").val();
        var pp= $("#petitionTypeTemplate").val();
        if (temp == 'templateDetails') {
            $("#addNewPetitionTemplateDiv").load(vmsUrl + immiModulePath + "/gotoTemplatePreviousPage?status=templateDetails&templateId="+immTemplId);
        } else if (temp == 'templateDocs') {
            $("#addNewPetitionTemplateDiv").load(vmsUrl + immiModulePath + "/gotoTemplatePreviousPage?status=docDetails&templateId="+immTemplId);
        } else if (temp == 'formDetails') {
            $("#addNewPetitionTemplateDiv").load(vmsUrl + immiModulePath + "/gotoTemplatePreviousPage?status=formDetails&templateId="+immTemplId);
        }
    }

    this.savePetitionTemplateAndContinue = function (status) {
        if ($('#petitionTemplateDetails').validate(false, validationSettings)) {
            var formData = getFormData("petitionTemplateDetails");
            $.ajax({
                url: vmsUrl + immiModulePath + "/savePetitionTemplateDetails?templateId=" + immTemplId,
                type: "POST",
                data: formData,
                success: function (result) {
                    if (result && result.response === "INVALID_DATA") {
                    	 showError(result.responseMessage);
                    }else if (result.response === "AWKWARD") {
                        location.href = "/errorPage";
                    }else if(result.response === "SUCCESS") {
                    	showTemplateMsg("Successfully Saved Template Details.");
                    	immTemplId = result.object;
                        if (status == 'saveContinue') {
                        	showTemplateMsg("Successfully Saved Template Details.");
                        	$("#addNewPetitionTemplateDiv").empty();
                            $("#addNewPetitionTemplateDiv").load(vmsUrl + immiModulePath + "/gotoTemplatePreviousPage?status=docDetails&templateId="+immTemplId);
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
        $("#" + formId).closest('form').find("input[type=text], textarea, .form-control,.selectpicker, .chosen-select").val("").trigger('chosen:updated');
        $("#" + formId).closest('form').find(".selectpicker, .chosen-select option[value='']").attr('selected', true).trigger('change');
    }
    
    this.saveEducationImmigration = function (status) {
    	var validationSettings = {
                errorMessagePosition: 'element',
                borderColorOnError: '',
                scrollToTopOnError: true,
                dateFormat: 'yyyy/mm/dd'
            };
    	$("#eduSave_Button").attr("disabled",true);
    	if ($('#eduSave_Form').validate(false, validationSettings)) {
            var formData = getFormData("eduSave_Form");
            var candidateId = $('#candidateID').val();
            var immigrationId = $('#immigrationID').val();
            $.ajax({
                url: vmsUrl + immiModulePath + "/saveEducationImmigrationDedtails?" + tokenParamName + "=" + tokenValue + "&candidateId=" + candidateId+"&immigrationId="+immigrationId,
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
	                   	if($('#candidateEducationId').val().trim()!= ''){
							showSuccessMsg("Successfully updated Education Details.");
		                	}else{
		                		showSuccessMsg("Successfully added New Education Details.");
			                }
		                immigration.getCandidateEducationList(candidateId);
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
    	var validationSettings = {
                errorMessagePosition: 'element',
                borderColorOnError: '',
                scrollToTopOnError: true,
                dateFormat: 'yyyy/mm/dd'
            };
        if ($('#PetitionTemplateDocDetails').validate(false, validationSettings)) {
            var formData = getFormData("PetitionTemplateDocDetails");
            var returnType = immigration.CheckDuplicateDocsByCategory();
           
            if(returnType == false){
            	return false;
            }
            formData.immigrationDocDeletedListIds = deletedDocListIds.join();
            $.ajax({
                url: vmsUrl + immiModulePath + "/savePetitionTemplateDocDetails",
                type: "POST",
                data: formData,
                success: function (result) {
                    if (result && result.response === "INVALID_DATA") {
                    	showError(result.responseMessage);
                    }else if (result.response === "AWKWARD") {
                        location.href = "/errorPage";
                    }else if(result.response === 'SUCCESS') {
                    	if (status == 'saveContinue') {
                    		showTemplateMsg("Successfully Saved Document Details.");
                            $("#addNewPetitionTemplateDiv").empty();
                            $("#addNewPetitionTemplateDiv").load(vmsUrl + immiModulePath + "/gotoTemplatePreviousPage?status=formDetails&templateId="+immTemplId);
                        } else if (status == 'save') {
                        	showTemplateMsg("Successfully Saved Document Details.");
                            $("#addNewPetitionTemplateDiv").empty();
                            $("#addNewPetitionTemplateDiv").load(vmsUrl + immiModulePath + "/gotoTemplatePreviousPage?status=docDetails&templateId="+immTemplId);
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
                url: vmsUrl + immiModulePath + "/savePetitionTemplateFormDetails",
                type: "POST",
                data: formData,
                success: function (result) {
                    if ( result && result.response === "INVALID_DATA") {
                    	showError(result.responseMessage);
                    }else if (result.response === "AWKWARD") {
                        location.href = "/errorPage";
                    }else if(result.response === 'SUCCESS') {
                    	if (status == 'saveContinue') {
                    		showTemplateMsg("Successfully Saved Form Details.");
                            $("#addNewPetitionTemplateDiv").empty();
                            $("#addNewPetitionTemplateDiv").load(vmsUrl + immiModulePath + "/gotoTemplatePreviousPage?status=taskList");
                        } else if (status == 'save') {
                        	showTemplateMsg("Successfully Saved Form Details.");
                            $("#addNewPetitionTemplateDiv").empty();
                            $("#addNewPetitionTemplateDiv").load(vmsUrl + immiModulePath + "/gotoTemplatePreviousPage?status=formDetails");
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
                url: vmsUrl + immiModulePath + "/savePetitionTemplateTaskDetails",
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
                    	if($('#templateId').val().trim()!= ''){
                    	showTemplateMsg("Successfully Updated Task Details.");
                    	}else{
                    		showTemplateMsg("Successfully Saved Task Details.");
                        	}
                    	$("#addNewPetitionTemplateDiv").empty();
                        $("#addNewPetitionTemplateDiv").load(vmsUrl + immiModulePath + "/gotoTemplatePreviousPage?status=taskList");
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
    
    this.removeFormValidationErrors = function(formId){
    	$("#"+formId).find('p.jquery_form_error_message').remove();
    }

    this.editTemplateDetailsByRowId = function (templateUuid) {
        immTemplId = templateUuid; //updating global variable "immTemplId" with templateId.
        window.location.href = vmsUrl + immiModulePath + "/addNewPetitionTemplate?templateUuid=" + templateUuid; // for editing mode  
    }

    this.reactiveImmigrationTemplateById = function (templateUuid){
    	$.ajax({
            url: vmsUrl + immiModulePath + "/reactiveImmigrationTemplateDetailsById?templateUuid="+templateUuid,
            type: "GET",
            data: templateId,
            success: function (data) {
                if (data == 'success') {
                    window.location.href = vmsUrl + immiModulePath + "/viewImmigrationTemplateList"; // redirect to tempalte list.
                }
            }
        });
    }
    
    this.deleteTempalteDetailsByRowId = function (temlateUuid) {
        var reason = $("#reasonForTemplate").val();
        if(reason.match(/^[^\s-](.*)*$/) != null && reason !="" && reason.length <= 250){
        $("#reasonForTemplateDiv").empty();
    	$.ajax({
            url: vmsUrl + immiModulePath + "/deleteImmigrationTemplateDetatils?temlateUuid="+temlateUuid+"&reason="+reason,
            type: "GET",
            data: templateId,
            success: function (data) {
                if (data == 'success') {
                    window.location.href = vmsUrl + immiModulePath + "/viewImmigrationTemplateList"; // redirect to tempalte list.
                }
            }
        });
        }else if (reason.length >= 251){
        	$("#reasonForTemplateDiv").find('p.jquery_form_error_message').remove();
			$("#reasonForTemplateDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason Length 1-250 characters</font>") ; 
        }else{
        	$("#reasonForTemplateDiv").find('p.jquery_form_error_message').remove();
			$("#reasonForTemplateDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
        }
    }
    // immigration view navigation if don't required comment below code start here.
    this.savePageAndContinue = function (status) {
        //$("#newPetitionTemplateDiv").empty();
        if (status == 'petitionDetails') {
            $("#newPetitionTemplateDiv").load("/vms" + immiModulePath + "/loadNewPetitionPage?forward=petitionDetails");
        } else if (status == 'saveContinue' || status == 'sendRequest') {
        	if ($("#petitionDetailsForm").validate(false, validationSettings)) {
        	/*	$("#saveAsDraftButton").prop("disabled",true);
            	$("#savePageOneContinue").prop("disabled",true);
            	$("#sendRequestButton").prop("disabled",true);*/
                var formData = getFormData("petitionDetailsForm");
                $("#sendRequestButton").attr("disabled","disabled");
           	    $("#savePageOneContinue").attr("disabled","disabled");
           	    $("#saveAsDraftButton").attr("disabled","disabled");
                $.ajax({
                    url: vmsUrl + immiModulePath + "/savePetitionDetailsPage?removedResidenceIds="+removedResidenceIds+"&status="+status,
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
                       	immigrationUUID = result.data[3];
                       	candidateUUID = result.data[4];
                       	immiTemplateUUID = result.data[5];
                            if(status == 'saveContinue'){// redirect to petition details tab page.
                            window.location.href = vmsUrl + immiModulePath + "/viewImmigration?immigrationUUID="+immigrationUUID+"&candidateUUID="+candidateUUID+"&immiTemplateUUID="+immiTemplateUUID+"&myImmigrationPage=N";
                            }
                            if(status == 'sendRequest'){
                            	window.location.href = vmsUrl + immiModulePath + "/viewImmigrationList";
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
            $("#newPetitionTemplateDiv").load("/vms" + immiModulePath + "/loadNewPetitionPage?forward=two");
        }else if(status == 'save'){
        	if ($("#petitionDetailsForm").validate(false, validationSettings)) {
        		/*$("#saveAsDraftButton").prop("disabled",true);*/
            	//$("#savePageOneContinue").prop("disabled",true);
            	//$("#sendRequestButton").prop("disabled",true);
                var formData = getFormData("petitionDetailsForm");
                $("#saveAsDraftButton").attr("disabled","disabled");
                $.ajax({
                    url: vmsUrl + immiModulePath + "/savePetitionDetailsPage?removedResidenceIds="+removedResidenceIds+"&status="+status,
                    type: "POST",
                    data: formData,
                    success: function (result) {
                    	if (result && result.response === "INVALID_DATA") {
                          	 showError(result.responseMessage);
                          }else if (result.response === "AWKWARD") {
                              location.href = "/errorPage";
                          }else if(result.response === 'SUCCESS') {
                        	  window.location.href = vmsUrl + immiModulePath + "/viewImmigrationList";
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
            $("#newPetitionTemplateDiv").load("/vms" + immiModulePath + "/loadNewPetitionPage?forward=three");
        } else if (status == 'four') {
            $("#newPetitionTemplateDiv").load("/vms" + immiModulePath + "/loadNewPetitionPage?forward=four");
        } else if (status == 'five') {
            $("#newPetitionTemplateDiv").load("/vms" + immiModulePath + "/loadNewPetitionPage?forward=five");
        } else if (status == 'six') {
            $("#newPetitionTemplateDiv").load("/vms" + immiModulePath + "/loadNewPetitionPage?forward=six");
        } else if (status == 'seven') {
            $("#newPetitionTemplateDiv").load("/vms" + immiModulePath + "/loadNewPetitionPage?forward=seven");
        }
    }// immigration end if don't required comment above code end here.
    
    this.displayPetitionDetails = function(immigrationId){
    	$.ajax({
            url: vmsUrl + immiModulePath + "/viewImmigrationById?immigrationId="+immigrationId,
            type: "GET",
            success: function (result) {
                $("#detailsTab").html(result);
            }
        });
    }

    this.savePetitionTemplateDetailsValues = function () {
        $.ajax({
            url: vmsUrl + immiModulePath + "/savePetitionTemplateDetailsValues?templateId=" + immTemplId,
            type: "GET",
            /*data: templateId,*/
            success: function (data) {
                if (data == 'success') {
                    	showTemplateMsg("Successfully New Template Activated.");
                        window.location.href = vmsUrl + immiModulePath + "/viewImmigrationTemplateList"; // redirect to tempalte list.
                        immTemplId = "";
                    }
            }
        });
    }
    

    this.saveVisaDetails= function() {
    	
    	if ($("#CandidateVisaform").validate(false, validationSettings)) {
    		$("#visa_save_id").attr("disabled",true);
    			var formData = getFormData("CandidateVisaform");
    		 	var candidateId = $('#candidateID').val();
                var immigrationId = $('#immigrationId').val();
                 var visaHistoryId = $('#visaHistoryId').val();
                $.ajax({
                url: vmsUrl + immiModulePath + "/saveVisaDetailsPage?candidateId="+candidateId+"&immigrationId="+immigrationId+"&visaHistoryId="+visaHistoryId,
                type : "POST",
    			data : formData,
    			success : function(result) {
    							if (result && result.response === "INVALID_DATA") {
    		                    	 showError(result.responseMessage);
    		                    }else if (result.response === "AWKWARD") {
    		                        location.href = "/errorPage";
    		                    }else if(result.response === 'SUCCESS') {
    		                    	
    									$("#popUpVisa").hide();
    									$(".popMask").fadeOut(200); // close the popup code															// popup code
    									if($('#candidateVisaId').val().trim() != ''){
    										showSuccessMsg("Successfully Updated Visa Details.");
    			                    	}else{
    			                    		showSuccessMsg("Successfully New Visa Details Added.");
    			                    	}
    									immigration.displayVisaDetails(candidateId,curUscisMyImmi);
    								
    		                    }else{
    		                    	 showError("Operation failed, please try again");
    		                    }
    							$("#passport_save_id").removeAttr("disabled");
    							$("#visa_save_id").removeAttr("disabled");
    			}
    		});
    		return true;
    	} else {
    		return false;
    	}
    }
    
    this.savePassportVisaDetails= function() {
    	
    	if ($("#CandidatePassportform").validate(false, validationSettings)) {
    		$("#passport_save_id").attr("disabled",true);
    			var formData = getFormData("CandidatePassportform");
    		 	var candidateId = $('#candidateID').val();
                var immigrationId = $('#immigrationId').val();
               
                $.ajax({
                url: vmsUrl + immiModulePath + "/savePassportDetailsPage?" + tokenParamName + "=" + tokenValue + "&candidateId="+candidateId+"&immigrationId="+immigrationId,
                type : "POST",
    			data : formData,
    			success : function(result) {
    				
    							if (result && result.response === "INVALID_DATA") {
    		                    	 showError(result.responseMessage);
    		                    }else if (result.response === "AWKWARD") {
    		                        location.href = "/errorPage";
    		                    }else if(result.response === 'SUCCESS') {
    		                    	
    									$('#popPassView').hide();
    									$('.popMask').fadeOut();
    									if($('#candidatePassportId').val().trim() != ''){
    										showSuccessMsg("Successfully Updated Passport Details.");
    			                    	}else{
    			                    		showSuccessMsg("Successfully New Passport Details Added.");
    			                    	}
    										
    									immigration.displayPassportDetails(candidateId,curUscisMyImmi);
    								
    		                    }else{
    		                    	 showError("Operation failed, please try again");
    		                    }
    							$("#passport_save_id").removeAttr("disabled");
    			}
    		});
    		return true;
    	} else {
    		return false;
    	}
    }


    this.editVisaDetailsByRowId=function(id){
		$.ajax({
	        url: vmsUrl + immiModulePath + "/getCandidateVisaDetailsByVisaHistoryId?id="+id,
	        type: "GET",
	        success : function(data) {
	        	if(data != null){
                    formObj = $('#CandidateVisaform')[0]; // load the child form
        	    	var childPopNames=["visaNumber", "validFrom","validTo"];
        			$("#addVisa").trigger('click');
        			immigration.udpatePopupFormdata(formObj,childPopNames,data);
        		    $('#visaType').val(data.visaType);
        			$('#visaType').selectpicker('refresh');
        			$('#visaType').prop("disabled",true);
        			$('#visaCountry').val(data.country);
        			$('#visaCountry').selectpicker('refresh');
        			$('#visaCountry').prop("disabled",true);
        			$('#visaNumber').val(data.visaNumber);
        			$('#visaNumber').attr("disabled","disabled");
        			$('#candidateVisaId').val(data.candidateVisaId);
        			$('#visaHistoryId').val(data.id);
	        	}
	        }
	    });
	}

    this.editPassportDetailsByRowId=function(id){
    	$("passNum").val(id);
		$.ajax({
	        url: vmsUrl + immiModulePath + "/getCandidatePassportDetailsById?id="+id,
	        type: "GET",
	        success : function(data) {
	        	if(data != null){
	        		formObj = $('#CandidatePassportform')[0]; // load the child form
        			var childPopNames=["passportNumber","country", "validFrom","validTo"];
        			$("#addPass").trigger('click');
        			immigration.udpatePopupFormdata(formObj,childPopNames,data);
        			$('#validFrom').val(data.validFromDateStr);
        			$('#passNum').val(data.id);
        			$('#validTo').val(data.validToDateStr);
        			
        			$('#countryName').val(data.country);
        			$('#countryName').selectpicker('refresh');
        			$('#candidatePassportId').val(data.id);	
	        		
	        		
	        	}
	        }
	    });
	}

    this.displayPassportDetails=function(candidateId,curUscisMyImmi,searchValue){
    	if(searchValue) searchValue =searchValue.trim();
   		$.ajax({
           url: vmsUrl + immiModulePath + "/getCandidatePassportDetails?candidateId="+candidateId+ "&searchValue=" + searchValue+"&myImmigrationPage="+myImmigrationPage+"&curUscisMyImmi="+curUscisMyImmi,
           type: "GET",
           success: function (result) {
               $("#passportsTab").html(result);
               $("#searchValuePassport").val(searchValue);
           }
       });
   }
    
    this.displayVisaDetails=function(candidateId,curUscisMyImmi,searchValue){
    	if(searchValue) searchValue =searchValue.trim();
   		$.ajax({
           url: vmsUrl + immiModulePath + "/getCandidateVisaDetails?candidateId="+candidateId+ "&searchValue=" + searchValue+"&myImmigrationPage="+myImmigrationPage+"&curUscisMyImmi="+curUscisMyImmi,
           type: "GET",
           success: function (result) {
               $("#visasTab").html(result);
               $("#searchValueVisa").val(searchValue);
              
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
					$("#"+formId+" input[name='"+formElemName+"']").val(dataObj[formElemName]); // sets date values to the form.
				}// above code checks the date field names and set the formatted date value to the popup.
				else{
				 $("#"+formId+" input[name='"+formElemName+"']").val(dataObj[formElemName]); // setting non date values to the from
				}
			}

		});
	}   

    
    this.editImmigrationCandidateForm=function(id){
		$.ajax({
	        url: vmsUrl + immiModulePath + "/editImmigrationCandidateFormDetailsById?id="+id,
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
	        			}else{
	        				$('#formSave_Form input[name="isRequired"]').prop('checked', false);	
	        			}
	        			$('#immigrationCandidateFormId').val(data.id);
	        	
	        	   
	        	}
	        }
	    });
	}
	this.updateImmigrationStatus = function(immigrationId) {


		$.ajax({
			url : vmsUrl + immiModulePath+"/updateImmigrationStatus?id=" + immigrationId+"&candidateId="+candidateId+"&templateId="+templateId,
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
    
   this.getLcaDetailsInfo=function(immigrationId,curUscisMyImmi){
  		$.ajax({
          url: vmsUrl + immiModulePath +"/getLcaDetails?immigrationId="+immigrationId+"&myImmigrationPage="+myImmigrationPage,
          type: "GET",
          success: function (result) {
              $("#lcaTab").html(result);
             
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
			 $("#family_SaveButton").attr("disabled",true);
			$.ajax({
				url : vmsUrl + immiModulePath+ "/saveCandidateFamilyDetails?candidateId="+candidateId+"&relationType="+relationType+"&memberId="+familyMemberId+"&immigrationId="+immigrationId,
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
       				if($('#familyMemberId').val()!= ''){
						showSuccessMsg("Successfully Updated Family Details.");
                	}else{
                		showSuccessMsg("Successfully New Family Details Added.");
                	}
       				immigration.displayFamilyDetails(candidateId); 
       				}else{
                   	 showError("Operation failed, please try again");
                   }
					
					$("#family_SaveButton").removeAttr("disabled");
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
			$("#canFormSave_Button").attr("disabled",true);
	        $.ajax({
	            url: vmsUrl + immiModulePath + "/saveCandidateImmigrationForms?"+ tokenParamName + "=" + tokenValue + "&candidateId="+candidateId+"&immigrationId="+immigrationId+"&templateId="+templateId,
	            type: "POST",
	            data: formData,
	            success: function (data) {
	                if (data == 'success') {
	                	$('#viewFormpop').hide();
	                	$('.popMask').fadeOut();
	                	if($('#immigrationCandidateFormId').val()!= ''){
							showSuccessMsg("Successfully Updated Form Details.");
	                	}else{
	                		showSuccessMsg("Successfully New Form Details Added.");
	                	}
	                	immigration.displayImmigrationCandidateForms(candidateId,templateId,immigrationId,'');
	                }
	                $("#canFormSave_Button").removeAttr("disabled");
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
            url: vmsUrl + immiModulePath + "/deleteImmigrationCandidateFormDetails?id="+id,
            type: "GET",
            success: function (result) {
            	if(result =='success'){
            		showSuccessMsg("Successfully Record Deleted.");
            		immigration.displayImmigrationCandidateForms(candidateId,templateId,immigrationId,'');
            		deleteImmigrationPopupDiv();
            	}
            }
        });
    }
    
    this.displayFamilyDetails=function(candidateId){
    	var searchVal= $("#familySerach").val();
    	$.ajax({
            url: vmsUrl + immiModulePath + "/getCandidateFamilyDetails?candidateId="+candidateId+"&searchVal="+searchVal+"&myImmigrationPage="+myImmigrationPage,
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
            url: vmsUrl + immiModulePath + "/deleteCandidateFamilyDetails?id="+delId,
            type: "GET",
            success: function (result) {
            	if(result=='success'){
            		showSuccessMsg("Successfully Record Deleted.");
            	     immigration.displayFamilyDetails(candidateId) //candidateId global variable
            	     deleteImmigrationPopupDiv();
            	}
            }
        });
	}
	
	
	this.editFamilyDetailsById = function(id, relationType){
		familyMemberId = id; // update the variable with family member id in edit mode.
		$.ajax({
            url: vmsUrl + immiModulePath + "/getCandidateFamilyDetailsById?id="+id,
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
	
	this.displayImmigrationCandidateForms = function(candidateId,templateId, immigrationId,searchVal,myImmigrationStatus){
		$.ajax({
	        url: vmsUrl + immiModulePath + "/getCandidateImmigrationFormsList?candidateId="+candidateId+"&templateId="+templateId+"&immigrationId="+immigrationId+
	        "&searchVal="+searchVal+"&myImmigration="+myImmigrationStatus+"&myImmigrationPage="+myImmigrationPage,
	        type: "GET",
	        success: function (result) {
	        	console.info(result);
	            $("#formsTab").html(result);
	            $("#immigraFormTabSerach").val(searchVal);
	        }
	    });
	}
	
	
	this.deleteImmigrationDetailsByRowId = function(immigrationUUID){
		$.ajax({
	        url: vmsUrl + immiModulePath + "/deleteImmigrationDetailsById?immigrationUUID="+immigrationUUID,
	        type: "GET",
	        success: function (result) {
	        	$("#remove_immigration_pop").fadeOut(200);
	    		$(".project_inn_mask").fadeOut(200);
	    		window.location.href = vmsUrl + immiModulePath + "/viewImmigrationList";
	        }
		});
	}
	
	 this.saveCandidateDocs= function () {
			
		 var validationSettings = {
		            errorMessagePosition: 'element',
		            borderColorOnError: '',
		            scrollToTopOnError: true,
		            dateFormat: 'yyyy/mm/dd'
		        };
		 
		
			if ($('#candidate_Immigration_document_form').validate(false, validationSettings)) {
		 
				var formData = getFormData("candidate_Immigration_document_form");
				var candidateId = $('#candidateID').val();
				var templateId = $('#templateID').val();
				var immigrationId = $('#immigrationID').val();

	        $.ajax({
	            url: vmsUrl + immiModulePath + "/saveCandidateImmigrationDocuments?"+ tokenParamName + "=" + tokenValue + "&candidateId="+candidateId+"&immigrationId="+immigrationId+"&templateId="+templateId,
	            type: "POST",
	            data: formData,
	            success: function (data) {
	                if (data == 'success') {
	                	$('#docuView').hide();
	                	$('.popMask').fadeOut();
	                	if($('#immigrationCandidateDocId').val()!= ''){
	    					
		                	showSuccessMsg("Successfully Updated Document Details.");
	                	}else{
	                		showSuccessMsg("Successfully New Document Details Added.");
	                	}
	                	immigration.displayImmigrationCandidateDocs(candidateId,templateId,immigrationId,'');
	                }
	                $("#canDocSave_Button1").removeAttr("disabled");
	            }
	        });

	        return true;
	  } else {
	        return false;
	    }
		
	}
	$('body').on('blur', '#candidate_Immigration_document_form', function () {
	    $("#candidate_Immigration_document_form").showHelpOnFocus();
	    $("#candidate_Immigration_document_form").validateOnBlur(false, validationSettings);
	});
	

	this.editEmpDetailsByRowId = function(id){
		 // update the variable with family member id in edit mode.
		var candidateId = $("#candidateID").val();
		var startYear = document.getElementById("startYear").value;
		var endYear = document.getElementById("endYear").value;
		var startMonth= document.getElementById("startMonth").value;
		var endMonth= document.getElementById("endMonth").value;
		
		var SMdata = startMonth.split('&');
		var sMonthNumber = SMdata[0];
		
		var EMdata = endMonth.split('&');
		var eMonthNumber = EMdata[0];
		
		var startYearFlag = true;
		var endYearFlag = true;
		
		 if((parseInt(sMonthNumber) > parseInt(eMonthNumber)) && (startYear==endYear) ){
			 $("#startYear").parent().after("<p class='jquery_form_error_message' id='err'><span>Start Month Should be less than end Month</span></p>");
		} else if((startYear > endYear ) && (endYear != '' && endYear != null && endYear != 'undefined')) {
			document.getElementById("endYearValidation").style.display="block";
			$("#endYearValidation").empty().append("End year should be greater than Start year") ; 
		} else if(startYearFlag && endYearFlag) {
		$.ajax({
	        url: vmsUrl + immiModulePath + "/getCandidateEmpDetailsById?id="+id+"&candidateId="+candidateId,
	        type: "GET",
	        success: function (data) {
	        	var formObj='';
	        	if(data!= null){
	        		
	        			formObj = $('#employerDetails_form')[0]; // load the child form
	        			var fomValues=["name","designation", "address","startMonth","startYear","endMonth","endYear"];
	        			$("#addEmpl").trigger('click');
	        			immigration.udpateEduFormdata(formObj,fomValues,data);
	        			$('#startMonth').val(data.startMonth); // to select the dropdwon value based on id value.
	        			$('#endMonth').val(data.endMonth);
	        			if(data.isCurrentPosition == 'Y'){
	        				$('#isCurrentPosition').attr("checked",true);
	        				document.getElementById("endDateDivId").style.display = 'none';
	        			}else{
	        				$('#isCurrentPosition').attr("checked",false);
	        				document.getElementById("endDateDivId").style.display = 'block';
	        			}
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
	}


	this.deleteEmpDetails = function(id){
		var candidateId = $("#candidateID").val();
		
		$.ajax({
	        url: vmsUrl + immiModulePath + "/deleteCandidateEmployerDetails?id="+id+"&candidateId="+candidateId,
	        type: "GET",
	        success: function (result) {
	        	if(result=='success'){
	        		showSuccessMsg("Successfully Record Deleted.");
	        		immigration.getCandidateEmployerList(candidateId,curUscisMyImmiVal) //candidateId global variable
	        		deleteImmigrationPopupDiv();
	        	}
	        }
	    });
	}

	

	
	this.saveEmployerDetails = function(status) {
		applyKeyEventsToYears();
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
	        
	        var formData = getFormData("employerDetails_form");
			var startYear = document.getElementById("startYear").value;
			var endYear = document.getElementById("endYear").value;
			
			var startMonth= document.getElementById("startMonth").value;
			var endMonth= document.getElementById("endMonth").value;
			
			var SMdata = startMonth.split('&');
			var sMonthNumber = SMdata[0];
			
			var EMdata = endMonth.split('&');
			var eMonthNumber = EMdata[0];
			
			var startYearFlag = true;
			var endYearFlag = true;
			
			var currentYear = new Date().getFullYear();
			if(( parseInt(sMonthNumber) > parseInt(eMonthNumber)) && (startYear == endYear)) {
				 $("#startYear").parent().after("<p class='jquery_form_error_message' id='err'><span>Start Month Should be less than end Month</span></p>");
			} else if((startYear > endYear ) && (endYear != '' && endYear != null && endYear != 'undefined')) {
				document.getElementById("endYearValidation").style.display="block";
				$("#endYearValidation").empty().append("End year should be greater than Start year") ; 
			} else if(startYearFlag && endYearFlag) {
				document.getElementById("endYearValidation").style.display="none";
	        
	        $("#employerSave_Buton").attr("disabled",true);
	        $.ajax({
	            url: vmsUrl + immiModulePath + "/saveImmigrationEmployerDetails?" + tokenParamName + "=" + tokenValue + "&candidateId="+candidateId+"&immigrationId="+immigrationId,
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
	                	if($('#candidateEmpId').val()!= ''){
	                		showSuccessMsg("Successfully updated Employee Details.");
	                    }else{
	                    	showSuccessMsg("Successfully new Employees Details.");
	                    	}
	                	immigration.getCandidateEmployerList(candidateId,curUscisMyImmi);
	                }else{
	                	 showError("Operation failed, please try again");
	                }
					$("#employerSave_Buton").removeAttr("disabled");
	            }
	        
	        });
			}
	        return true;
	    } else {
	        return false;
	    }
		
	}
	$('body').on('blur', '#employerDetails_form', function () {
	    $("#employerDetails_form").showHelpOnFocus();
	    $("#employerDetails_form").validateOnBlur(false, validationSettings);
	});


	
	this.getCandidateEmployerList = function(candidateId, curUscisMyImmi){
		var searchEmployerVal = $("#employersDataSearch").val();
		if(searchEmployerVal) searchEmployerVal = searchEmployerVal.trim();
		$.ajax({
	        url: vmsUrl + immiModulePath + "/getCandidateEmployerList?candidateId="+candidateId+"&searchVal="+searchEmployerVal+"&myImmigrationPage="+myImmigrationPage+"&curUscisMyImmi="+curUscisMyImmi,
	        type: "GET",
	        success: function (result) {
	            $("#employersTab").html(result);
	            $("#employersDataSearch").val(searchEmployerVal);
	        }
	    });
	}
	
	
	
	this.displayImmigrationCandidateDocs = function(candidateId,templateid,immigrationId,searchVal,myImmigrationStatus){
		$.ajax({
	        url: vmsUrl + immiModulePath + "/getCandidateImmigrationCandidateDocsList?candidateId="+candidateId+"&templateid="+templateid+"&immigrationId="+immigrationId+"&searchVal="+searchVal+
	        "&myImmigration="+myImmigrationStatus+"&myImmigrationPage="+myImmigrationPage,
	        type: "GET",
	        success: function (result) {
	            $("#docsTab").html(result);
	            $("#immigraDocTabSerach").val(searchVal);
	        }
	    });
	}

	
	this.deleteEDucationDetailsById = function(id){
		var candidateId = $("#candidateID").val();
		$.ajax({
	        url: vmsUrl + immiModulePath + "/deleteCandidateEduDetails?id="+id+"&candidateId="+candidateId,
	        type: "GET",
	        success: function (result) {
	        	if(result=='success'){
	        		showSuccessMsg("Successfully Record Deleted.");
	        	  immigration.getCandidateEducationList(candidateId) //candidateId global variable
	        	  deleteImmigrationPopupDiv();
	        	}
	        }
	    });
	}

	this.editEducationDEtailsByRowId = function(id){
		 // update the variable with family member id in edit mode.
		var candidateId = $("#candidateID").val();
		$.ajax({
	        url: vmsUrl + immiModulePath + "/getCandidateEducationDetailsById?id="+id+"&candidateId="+candidateId,
	        type: "GET",
	        success: function (data) {
	        	var formObj='';
	        	if(data!= null){
	        		
	        			formObj = $('#eduSave_Form')[0]; // load the child form
	        			var fomValues=["name","specialization", "startDate","endDate","gradeType","grade","institute","address"];
	        			$("#addEdu").trigger('click');
	        			immigration.udpateEduFormdata(formObj,fomValues,data);
	        			//$('#eduName').val(data.name); // to select the dropdwon value based on id value.
	        			$('#eduName option[value="'+data.name+'"]').prop("selected", true);
	        	        $('#eduName').trigger("chosen:updated");
	        			$('#scoreEducationType').val(data.gradeType);
	        			 $('#scoreEducationType').trigger("chosen:updated");
	        			$('#immigrationEduStartDate12').val(data.startDateStr);
	        			$('#immigrationEduEndDate').val(data.endDateStr);
	        			if(data.educationFileName != null){
	        			$('#immigrationEduDocDivEdit').show();
	    	        	$('#immigrationEduDocDiv').hide();
	        			$('#immigrationEduDocName').text(data.educationFileName);
	        			}else{
	        				$('#immigrationEduDocDivEdit').hide();
	        	        	$('#immigrationEduDocDiv').show();
	        	        	$('#immigrationEduDocName').text("");
	        			}
	        			$('#candidateEducationId').val(data.id);
	        	
	        	   
	        	}
	        }
	    });
	}


	this.getCandidateEducationList = function(candidateId){
		var searchVal = $("#educationSearchVal").val();
		$.ajax({
	        url: vmsUrl + immiModulePath + "/getCandidateEducationList?candidateId="+candidateId+"&searchVal="+searchVal+"&myImmigrationPage="+myImmigrationPage,
	        type: "GET",
	        success: function (result) {
	            $("#educationsTab").html(result);
	            $("#educationSearchVal").val(searchVal);
	        }
	    });
	}
	
	
	this.udpateEduFormdata = function(formObj, idsArray, dataObj) {
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


	
	this.saveResidenceDetails = function(status) {
		
		if ($('#residenceDetails_form').validate(false, validationSettings)) {
	        var formData = getFormData("residenceDetails_form");
	        var candidateId = $('#candidateID').val();
	        var immigrationId = $('#immigrationID').val();
	        $("#residence_SaveButon").attr("disabled",true);
	        $.ajax({
	            url: vmsUrl + immiModulePath + "/saveImmigrationResidenceDetails?" + tokenParamName + "=" + tokenValue + "&candidateId="+candidateId+"&immigrationId="+immigrationId,
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
	               	if($('#residenceId').val()!= ''){
						showSuccessMsg("Successfully updated Residence Details.");
	                	}else{
	                		showSuccessMsg("Successfully added New Residence Details.");
		                }
	               	
	               	  immigration.getCandidateResidenceList(candidateId);
	               }else{
	               	 showError("Operation failed, please try again");
	               }
	              	$("#residence_SaveButon").removeAttr("disabled");
	            }
	        });

	        return true;
	    } else {
	        return false;
	    }
		
	}

	this.getCandidateResidenceList = function(candidateId){
		var searchVal= $("#residenceSearch").val();
		$.ajax({
	        url: vmsUrl + immiModulePath + "/getCandidateResidenceList?candidateId="+candidateId+"&searchVal="+searchVal+"&myImmigrationPage="+myImmigrationPage,
	       type: "GET",
	        success: function (result) {
	           $("#residencesTab").html(result);
	           $("#residenceSearch").val(searchVal);
	       }
	   });
	}

	this.deleteResidenceDetailsById = function(id){
		var candidateId = $("#candidateID").val();
		$.ajax({
	        url: vmsUrl + immiModulePath + "/deleteCandidateResidenceDetails?id="+id+"&candidateId="+candidateId,
	        type: "GET",
	        success: function (result) {
	        	if(result=='success'){
	        		showSuccessMsg("Successfully Record Deleted.");
	        		immigration.getCandidateResidenceList(candidateId) //candidateId global variable
	        		deleteImmigrationPopupDiv();
	        	     
	        	}
	        }
	    });
	}

	this.editResidenceDEtailsByRowId = function(id, isCurr){
		 // update the variable with residence member id in edit mode.
		var candidateId = $("#candidateID").val();
		$.ajax({
	     url: vmsUrl + immiModulePath + "/getCandidateResidenceDetailsById?id="+id+"&isCurr="+isCurr,
	     type: "GET",
	     success: function (data) {
	     	var formObj='';
	     	if(data!= null){
	     		
	     			formObj = $('#residenceDetails_form')[0]; // load the child form
	     			var fomValues=["location","streetAddress", "country","city","state","zipcode","startDate","endDate"];
	     			$("#addResi").trigger('click');
	     			immigration.udpateEduFormdata(formObj,fomValues,data); 
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


	 this.deleteVisaDetails = function(visaHistoryId){
			$.ajax({
		        url: vmsUrl + immiModulePath + "/deleteCandidateVisaDetails?visaHistoryId="+visaHistoryId,
		        type: "GET",
		        success: function (result) {
		        	if(result=='success'){
		        			showSuccessMsg("Successfully Record Deleted.");
		        			immigration.displayVisaDetails(candidateId,curUscisMyImmi);
		            	    deleteImmigrationPopupDiv(); // removes the existing added delete popup div in html code.
		        	}
		        	
		        }
		    });
		}
	 
	 this.deletePassportDetails = function(id){
			var candidateId = $("#candidateID").val();
			$.ajax({
		        url: vmsUrl + immiModulePath + "/deleteCandidatePassportDetails?id="+id+"&candidateId="+candidateId,
		        type: "GET",
		        success: function (result) {
		        	if(result=='success'){
		        		
		        			showSuccessMsg("Successfully Record Deleted.");
		        			immigration.displayPassportDetails(candidateId,curUscisMyImmi);
		        		
		            	    deleteImmigrationPopupDiv(); // removes the existing added delete popup div in html code.
		        	}
		        	
		        }
		    });
		}

		this.searchPassportValue = function(){
			var validationSettings = {
		            errorMessagePosition: 'element',
		            borderColorOnError: '',
		            scrollToTopOnError: true,
		            dateFormat: 'yyyy/mm/dd'
		        };
			var searchValue =document.getElementById("searchValuePassport").value; 
			var candidateId = $('#candidateID').val();
			immigration.displayPassportDetails(candidateId,curUscisMyImmi,searchValue);
		}

		this.searchVisaValue = function(){
			var validationSettings = {
		            errorMessagePosition: 'element',
		            borderColorOnError: '',
		            scrollToTopOnError: true,
		            dateFormat: 'yyyy/mm/dd'
		        };
			var searchValue =document.getElementById("searchValueVisa").value; 
			var candidateId = $('#candidateID').val();
			immigration.displayVisaDetails(candidateId,curUscisMyImmi,searchValue);
			
		}
	 
		this.validateCandidateVisaAndSave = function() {
			if ($("#CandidateVisaform").validate(false, validationSettings)) {
			 var  country =   $('#visaCountry').val();
			 if(country == '') return;
			 var candidateId = $("#candidateID").val();
			 
			  var idNumberVal = null;
			  var id = null;

			  
			id = $("#candidateVisaId").val();
		    visaNumber =$('#CandidateVisaform input[name="visaNumber"]').val().trim();  
			  
			  $('#idNumberDiv').find('p.jquery_form_error_message').remove();
				 $.ajax({
				    	type: "GET",
				    	 url:vmsUrl + immiModulePath + "/validateCandidateVisa?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&visaNumber="+visaNumber+"&country="+country+"&id="+id, 
				        success: function(data) {
				        	if(data == true){
				        		   $("input#visaNumber").after("<p class='jquery_form_error_message'>"+visaNumber+" is Already Existed With Active Details.</span>");
								   $("#visa_save_id").attr('disabled','disabled');
				        	}
				        	else{
				        		  $('input#visaNumber').removeAttr( "record-exist record-exist-errormsg");
				        		  $('#idNumberVisaDiv').find('p.jquery_form_error_message').remove(); 
		                          $("#visa_save_id").removeAttr("disabled");
		                            immigration.saveVisaDetails(); // saving the visa details once validation is 
				        	}
				        
				        },
				    });
				
			} else {
				return false;
			}
		}
	
	this.addvisaExtensionDate = function(visaId, extensionDate){
		 var candidateId = $("#candidateID").val();
		$.ajax({
	    	type: "GET",
	    	 url:vmsUrl + immiModulePath + "/addVisaExtensionDate?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId
	    	                       +"&immigrationId="+immigrationId+"&extensionDate="+extensionDate+"&visaId="+visaId, 
	        success: function(visaDetails) {
	        	showSuccessMsg("Successfully Visa Date updated.");
	        	immigration.displayVisaDetails(candidateId,curUscisMyImmi);
	        }
		});
		
		
	}

	this.validateCandidatePassport = function() {
			 var validationSettings = {
		            errorMessagePosition: 'element',
		            borderColorOnError: '',
		            scrollToTopOnError: true,
		            dateFormat: 'yyyy/mm/dd'
		        };
			  var candidateId = $("#candidateID").val();
			 
			  var idNumberVal = null;
			  var id = null;
			  id = $("#candidatePassportId").val();
			  idNumberVal =$('#CandidatePassportform input[name="passportNumber"]').val().trim();
		 
		      $('#idNumberDiv').find('p.jquery_form_error_message').remove();
			 $.ajax({
			    	type: "GET",
			    	 url:vmsUrl + immiModulePath + "/validateCandidatePassport?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&idNumberVal="+idNumberVal+"&id="+id, 
			        success: function(data) {
			        	
			        	if(data == true){
			        		  $('#idNumberPassportDiv').find('p.jquery_form_error_message').remove();
			        		  $("input#passportNumber").after("<p class='jquery_form_error_message'>"+idNumberVal+" is Already Existed</span>");
							  $("#passport_save_id").attr('disabled','disabled');
			        	}
			        	else{
			        		 $('input#passportNumber').removeAttr( "record-exist record-exist-errormsg");
			        		 $('#idNumberPassportDiv').find('p.jquery_form_error_message').remove(); 
	                        $("#passport_save_id").removeAttr("disabled");
			        	}
			       
			        },
			    });
	}

	
	this.editDocumentDetailsByRowId = function(id){
		 // update the variable with residence member id in edit mode.
		var candidateId = $("#candidateID").val();
		$.ajax({
	     url: vmsUrl + immiModulePath + "/editDocumentDetailsByRowId?id="+id,
	     type: "GET",
	     success: function (data) {
	     	var formObj='';
	     	if(data != null){
	     			formObj = $('#candidate_Immigration_document_form')[0]; // load the child form
	     			$("#addDocu").trigger('click');
	     			$('#documentType').val(data.documentType.id);
	     			$('#documentType').selectpicker('refresh');
	     			if(data.documentOtherType){
	     			document.getElementById("documentOtherTypeDiv").style.display="block";
	     			$('#documentOtherType').val(data.documentOtherType);
	     			}else{
	     			document.getElementById("documentOtherTypeDiv").style.display="none";
	     			}
	     			$('#description1').text(data.description);
	     			if(data.allowMultiple == 'Y'){
	     				$('#candidate_Immigration_document_form input[name="multiple"]').prop('checked', true);
	     			}if(data.isRequired == 'Y'){
	     				$('#candidate_Immigration_document_form input[name="isRequired"]').prop('checked', true);
	     			}
	     			$('#immigrationCandidateDocId').val(data.id);
	     	
	     	   
	     	}
	     }
	 });
	}

	this.deleteDocumentDetailsByRowId = function(id){
		var templateId = $('#templateID').val();
		var candidateId = $("#candidateID").val();
		var immigrationId = $('#immigrationID').val();
		$.ajax({
	        url: vmsUrl + immiModulePath + "/deleteDocumentDetailsByRowId?id="+id,
	        type: "GET",
	        success: function (result) {
	        	if(result=='success'){
	        	showSuccessMsg("Successfully Record Deleted.");
	            immigration.displayImmigrationCandidateDocs(candidateId,templateId,immigrationId,'');
	        	deleteImmigrationPopupDiv();
	        	}
	        }
	    });
	}

	

	this.uploadRequestDoc = function(){
		if ($('#uploadRequestDoc_Form').validate(false, validationSettings)) {
			$("#uploadDocSave_Button").attr("disabled",true);
		var formData = getFormData("uploadRequestDoc_Form");
		var candidateId = $("#candidateID").val();
		var immigrationId = $('#immigrationID').val();
		var id = $("#uploadIdSave").val();
		var templateId = $('#templateID').val();
		$.ajax({
	        url: vmsUrl + immiModulePath + "/uploadRequstedDoc?id="+id+"&candidateId="+candidateId+"&immigrationId="+immigrationId,
	        type: "POST",
	        data: formData,
	        success: function (result) {
	        	if(result=='success'){
	        		$('#uploadCanDocPop').hide();
	            	$('.popMask').fadeOut();
	            	showSuccessMsg("Successfully Uploaded File.");
	            	immigration.displayImmigrationCandidateDocs(candidateId,templateId,immigrationId,'');
	        	     
	        	}
	        }
	    });
		}
	}


	this.uploadRequestForm = function(){
		if ($('#uploadRequestForm_Form').validate(false, validationSettings)) {
			$("#uploadFormSave_Button").attr("disabled",true);
		var formData = getFormData("uploadRequestForm_Form");
		var candidateId = $("#candidateID").val();
		var immigrationId = $('#immigrationID').val();
		var id = $("#uploadFormIdSave").val();
		$.ajax({
	        url: vmsUrl + immiModulePath + "/uploadRequstedForm?id="+id+"&candidateId="+candidateId+"&immigrationId="+immigrationId,
	        type: "POST",
	        data: formData,
	        success: function (result) {
	        	if(result=='success'){
	        		$('#uploadFormPop').hide();
	            	$('.popMask').fadeOut();
	            	showSuccessMsg("Successfully Uploaded File.");
	            	immigration.displayImmigrationCandidateForms(candidateId,templateId,immigrationId,''); 
	        	}
	        }
	    });
		}
	}

	
	this.applyKeyEventsToYears = function() {
		$("#startYear").keyup(function() {
			if($(this).val() > new Date().getFullYear()) {
				$("#startYearValidation").html("Start year shouldn't be greater than Current year");
				$("#startYearValidation").show();
				startYearFlag = false;
			} else {
				$("#startYearValidation").hide();
				startYearFlag = true;
			}
		});
		$("#endYear").keyup(function() {
			if($(this).val() > new Date().getFullYear()) {
				$("#endYearValidation").html("End year shouldn't be greater than Current year");
				$("#endYearValidation").show();
				endYearFlag = false;
			} else {
				$("#endYearValidation").hide();
				endYearFlag = true;
			} 
		});
	}
	
	



	this.validateTemplateName = function() {
	    var templateName = document.getElementById("templateName").value.trim();
	    if (templateName !== null && templateName !== "") {
	        $.ajax({
	            type: "POST",
	            url: vmsUrl + immiModulePath + "/validateTemplateName?" + tokenParamName + "=" + tokenValue + "&templateName=" + templateName + "&templateId=" + immTemplId,
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

	this.checkTaskNames = function(newName, immiTaskId) {
	    if (newName !== null && newName !== "") {
	        $.ajax({
	            type: "POST",
	            url: vmsUrl + immiModulePath + "/validateTemplateTaskName?" + tokenParamName + "=" + tokenValue + "&templateTaskName=" + newName + "&templateId=" + immTemplId+"&immiTaskId="+immiTaskId,
	            success: function (data) {
	                if (data && data === true) {
	                    if ($('#taskNameDiv').find('p.jquery_form_error_message').length === 0){
	                        $("input#taskName").after("<p class='jquery_form_error_message'>" + newName + " is Already Existed</span>");
	                    }
	                    $("#addTaskBtn").addClass("disableBtn");
	                } else {
	                	 $('input#taskName').removeAttr( "record-exist record-exist-errormsg");
	        			 $('#taskNameDiv').find('p.jquery_form_error_message').remove(); 
	        			 $("#addTaskBtn").removeClass("disableBtn");
	                }
	            }
	        });
	    }
	}

	this.getImmigrationTasks = function(immigrationId, status, searchVal){
		$.ajax({
	        url: vmsUrl + immiModulePath + "/getImmigrationTasksList?immigrationId="+immigrationId+"&taskStatus="+status+"&searchVal="+searchVal,
	        type: "GET",
	        success: function (result) {
	            $("#tasksTab").html(result);
	            $("#tasksSearchVal").val(searchVal);
	        }
	    });
	}



	this.validateEduname = function()  
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
			        url:vmsUrl + immiModulePath + "/validateImmiEduname?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&name="+name, 
			        success: function(data) {
			        	if(data == true){
			        		  $('#nameIdDiv').find('p.jquery_form_error_message').remove();
			        		  $("#eduName").after("<p class='jquery_form_error_message'>"+name+" is Already Existed</span>");
							  $("#eduSave_Button").attr('disabled','disabled');
							 // return false;
			        	}
			        	else{
			        		 $('#eduName').removeAttr( "record-exist record-exist-errormsg");
			        		 $('#nameIdDiv').find('p.jquery_form_error_message').remove(); 
	                       $("#eduSave_Button").removeAttr("disabled");
			        	}
			        	
			        },
			    });
	}

	


	this.saveNotesDatails = function(status) {
		
		if ($('#notesDetails_form').validate(false, validationSettings)) {
	        var formData = getFormData("notesDetails_form");
	        var candidateId = $('#candidateID').val();
	        var immigrationId = $('#immigrationID').val();
	        $("#notesSave_Button").attr("disabled",true);
	        $.ajax({
	            url: vmsUrl + immiModulePath + "/saveImmigrationNotesDetails?" + tokenParamName + "=" + tokenValue + "&candidateId="+candidateId+"&immigrationId="+immigrationId,
	            type: "POST",
	            data: formData,
	            success: function (data) {
	            	if (data === 'success') {
	            		$(".popMask").fadeOut(200);
	    				$("#popNoteView").hide(100);
	    				showSuccessMsg("Successfully New Notes Added.");
	    				immigration.getNotesListByImmigration(candidateId);
	                }
	            	$("#notesSave_Button").removeAttr("disabled");
	            }
	        });

	        return true;
	    } else {
	        return false;
	    }
		
	}

	this.getPetitionTypeOnCountries = function(selectedCountry, selectedCountryOption){
		var selectedValue = selectedCountry.val();
		$.ajax({
		    type: "GET",
	        url: vmsUrl+immiModulePath+"/getPetitionTypeOnLocation?locationVal="+selectedValue,  
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

	this.getNotesListByImmigration = function(candidateId){
		 var immigrationId = $('#immigrationID').val();
		$.ajax({
	        url: vmsUrl + immiModulePath + "/getNotesListByImmigrationId?" + tokenParamName + "=" + tokenValue + "&candidateId="+candidateId+"&immigrationId="+immigrationId,
	       type: "GET",
	        success: function (result) {
	           $("#notesTab").html(result);
	       }
	   });
	}

	this.getImmigrationActivies = function(){
		 var immigrationId = $('#immigrationID').val();
		$.ajax({
	        url: vmsUrl + immiModulePath + "/getImmigrationActiviesList?" + tokenParamName + "=" + tokenValue +"&immigrationId="+immigrationId,
	       type: "GET",
	        success: function (result) {
	           $("#activitiesTab").html(result);
	       }
	   });
	}

	this.validateImmigrationCandidateForms = function() {
		 var validationSettings = {
	           errorMessagePosition: 'element',
	           borderColorOnError: '',
	           scrollToTopOnError: true,
	           dateFormat: 'yyyy/mm/dd'
	       };
		 
		  var candidateId = $("#candidateID").val();
		  var immigrationId = $('#immigrationID').val();
		  var id = $("#immigrationCandidateFormId").val();
		  var formName = document.getElementById("formName").value; 
		      formName = formName.trim();
		  
		  $('#errformNameDiv').find('p.jquery_form_error_message').remove();
			 $.ajax({
			    	type: "GET",
			    	 url:vmsUrl + immiModulePath + "/validateImmigrationCandidateForms?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&immigrationId="+immigrationId+"&id="+id+"&formName="+formName, 
			        success: function(data) {
			        	
			        	if(data == true){
			        		
			        		  $('#errformNameDiv').find('p.jquery_form_error_message').remove();
			        		  $("input#formName").after("<p class='jquery_form_error_message'>"+formName+" is Already Existed</span>");
							  $("#canFormSave_Button").attr('disabled','disabled');
			        	}
			        	else{
			        		 $('input#formName').removeAttr( "record-exist record-exist-errormsg");
			        		 $('#errformNameDiv').find('p.jquery_form_error_message').remove(); 
	                         $("#canFormSave_Button").removeAttr("disabled");
			        	}
			        	
			        }
			       
			    });
	}

	this.CheckDuplicateDocsByCategory = function(){
		var petitionArr = [];
		var attorneyArr = [];
		var benefArr = [];
		var returnVal = true;
		$("select[id^=documentCategory]").each(	function() {

					var dropRowId = this.id.slice(-1);
					console.log("dropRowId "+dropRowId)
					var docCat = $(this).val();
					console.log("docCat "+docCat);

					docId = $('#candidateEducation' + dropRowId).val();
					docName = $('#candidateEducation' + dropRowId + ' :selected').text();
					if (docCat == 'PetitionerCategory') {
						if (petitionArr.indexOf(docId) == -1) {
							petitionArr.push(docId)
						} else {
							immigration.showPopupMessage(" duplicate " + docName+ " document found for the petitioner");
							returnVal = false;
							return false;
						}
					} else if (docCat == 'Document Type') {
						if (benefArr.indexOf(docId) == -1) {
							benefArr.push(docId)
						} else {
							immigration.showPopupMessage(" duplicate " + docName+ " document found for the Beneficiary");
							returnVal = false;
							return false;
						}
					} else if (docCat == 'AttorneyCategory') {
						if (attorneyArr.indexOf(docId) == -1) {
							attorneyArr.push(docId)
						} else {
							immigration.showPopupMessage(" duplicate " + docName+ " document found for the Attorney");
							returnVal = false;
							return false;
						}
					}
				});
		return returnVal;
		}

		this.showPopupMessage = function(message){ // only stauts updated with reason. active 1 , deactive 0.
			 $(".project_inn_mask").remove();
				var result = "";
				result = result +"	<div class='project_inn_mask'></div>";
				result = result +"	   <div id='remove_immigration_pop' class='remove_user_pop remove_user_pop_a padEv'>";
				result = result +" <b>"+message+"</b>";
				result = result + ' <div id="reasonForTemplateDiv"/>';
				result = result +"		<b href='javascript:void(0)'><button id='confirm-id' class='btn btn-primary btn-block mt' type='button' value='Ok' onclick='immigration.hidePopupMessage();'>OK</button></b>";
				result = result +"	</div>";
				$("#popUp").empty();
				$("#popUp").append(result);
				$("#remove_immigration_pop").slideDown(200);
				$(".project_inn_mask").fadeIn(200);
			}
		
		this.hidePopupMessage = function(){
			$("#remove_immigration_pop").fadeOut(200);
			$(".project_inn_mask").fadeOut(200);
		}
		
		//Create LCA From Immigration Summary (Click LCA TAB and Create  LCA)
		this.saveLcaFromImmigration = function() {
			     var validationSettings = {
			            errorMessagePosition: 'element',
			            borderColorOnError: '',
			            scrollToTopOnError: true,
			            dateFormat: 'yyyy/mm/dd'
			        };
			       
				    if ($('#lcaSave_Form').validate(false, validationSettings)) {
						  var formData = getFormData("lcaSave_Form");
							var candidateId = $('#candidateID').val();
				            var immigrationId = $('#immigrationId').val();
					 $.ajax({
				            type: "POST",
				            data : formData,
				            url: vmsUrl + immiModulePath + "/saveLCADetails?&candidateId="+candidateId+"&immigrationId="+immigrationId,
				            success: function (result) {
				            	
				            	if (result && result.response === "INVALID_DATA") {
				                   	 showError(result.responseMessage);
				                   }else if (result.response === "AWKWARD") {
				                       location.href = "/errorPage";
				                   }else if(result.response === 'SUCCESS') {
				                	    $(".popMask").fadeOut(200);
					    				$("#popLcaView").hide(100);
					                   	immigration.getLcaDetailsInfo(immigrationId,curUscisMyImmi);
				                   }else{
				                   	 showError("Operation failed, please try again");
				                   }
				            },
				        });
				 }
			    }
			    $('body').on('blur', '#lcaSave_Form', function () {
			        $("#lcaSave_Form").showHelpOnFocus();
			        $("#lcaSave_Form").validateOnBlur(false, validationSettings);
			    });
		
}
