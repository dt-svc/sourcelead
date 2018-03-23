var immigration = new Immigration();
var immiModulePath = "/immigration";
var immTemplId = '';
var immigrationId = '';
var candidateId = '';
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
                success: function (data) {
                    eval("jsonObj=" + data);
                    if (jsonObj.status == 'success') {
                        immTemplId = jsonObj.id;
                        if (status == 'saveContinue') {
                            $("#addNewPetitionTemplateDiv").empty();
                            $("#addNewPetitionTemplateDiv").load(sourceleadUrl + immiModulePath + "/gotoTemplatePreviousPage?status=docDetails");
                        }

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

    this.savePetitionDoc = function (status) {
        if ($('#PetitionTemplateDocDetails').validate(false, validationSettings)) {
            var formData = getFormData("PetitionTemplateDocDetails");
            formData.immigrationDocDeletedListIds = deletedDocListIds.join();
            $.ajax({
                url: sourceleadUrl + immiModulePath + "/savePetitionTemplateDocDetails",
                type: "POST",
                data: formData,
                success: function (data) {
                    if (data == 'success') {
                        if (status == 'saveContinue') {
                            $("#addNewPetitionTemplateDiv").empty();
                            $("#addNewPetitionTemplateDiv").load(sourceleadUrl + immiModulePath + "/gotoTemplatePreviousPage?status=formDetails");
                        } else if (status == 'save') {
                            $("#addNewPetitionTemplateDiv").empty();
                            $("#addNewPetitionTemplateDiv").load(sourceleadUrl + immiModulePath + "/gotoTemplatePreviousPage?status=docDetails");
                        }
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
                success: function (data) {
                    if (data == 'success') {
                        if (status == 'saveContinue') {
                            $("#addNewPetitionTemplateDiv").empty();
                            $("#addNewPetitionTemplateDiv").load(sourceleadUrl + immiModulePath + "/gotoTemplatePreviousPage?status=taskList");
                        } else if (status == 'save') {
                            $("#addNewPetitionTemplateDiv").empty();
                            $("#addNewPetitionTemplateDiv").load(sourceleadUrl + immiModulePath + "/gotoTemplatePreviousPage?status=formDetails");
                        }
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
                success: function (data) {
                    $(".popMask").fadeOut(200);
                    $("#taskViewPopup").fadeOut(100);
                    if (data == 'success') {
                        $("#addNewPetitionTemplateDiv").empty();
                        $("#addNewPetitionTemplateDiv").load(sourceleadUrl + immiModulePath + "/gotoTemplatePreviousPage?status=taskList");
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

    this.deleteTempalteDetailsByRowId = function (templateId) {

        $.ajax({
            url: sourceleadUrl + immiModulePath + "/deleteImmigrationTemplateDetatils?deleteId=" + templateId,
            type: "GET",
            data: templateId,
            success: function (data) {
                if (data == 'success') {
                    window.location.href = sourceleadUrl + immiModulePath + "/viewImmigrationTemplateList"; // redirect to tempalte list.
                }
            }
        });

    }
    // immigration view navigation if don't required comment below code start here.
    this.savePageAndContinue = function (status) {
        //$("#newPetitionTemplateDiv").empty();
        if (status == 'petitionDetails') {
            $("#newPetitionTemplateDiv").load("/app" + immiModulePath + "/loadNewPetitionPage?forward=petitionDetails");
        } else if (status == 'saveContinue') {
            if ($("#petitionDetailsForm").validate(false, validationSettings)) {
                var formData = getFormData("petitionDetailsForm");
                $.ajax({
                    url: sourceleadUrl + immiModulePath + "/savePetitionDetailsPage",
                    type: "POST",
                    data: formData,
                    success: function (data) {
                    	eval("dataObj="+data)
                    	immigrationId = dataObj.id;
                    	candidateId = dataObj.candidateId;
                    	templateId= dataObj.immTemplateId;
                    	
                         if(status == 'saveContinue'){// redirect to petition details tab page.
                         window.location.href = sourceleadUrl + immiModulePath + "/viewImmigration?immigrationId="+immigrationId+"&candidateId="+candidateId+"&templateId="+templateId;
                         }
                        
                      }
                    
                });
                return true;
            } else {
                return false;
            }
            $("#newPetitionTemplateDiv").load("/app" + immiModulePath + "/loadNewPetitionPage?forward=two");
        } else if (status == 'three') {
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
    
    this.viewImmigrationPageList = function(immigrationId){
    	 $.ajax({
             url: sourceleadUrl + immiModulePath + "/viewImmigration?immigrationId="+immigrationId,
             type: "POST",
             /*data: formData,*/
             success: function (data) {
            	 
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
    this.saveCandidateFamilyDetails= function(relationType) {
		if ($('#spouseDetailsForm').validate(false, validationSettings)) {
			var formData = getFormData("spouseDetailsForm");
			console.log(" inside function");
			console.log(formData);
			 var candidateId = $('#candidateID').val();
			$.ajax({
				url : sourceleadUrl + immiModulePath+ "/saveCandidateFamilyDetails?candidateId="+candidateId+"&relationType="+relationType,
				type : "POST",
				data : formData,
				success : function(data) {
					console.log(" inside success function");
					if (data == 'success') {
                        console.log("data successfully saved for the candidate id "+templateId);
					}
				}
			});

			return true;
		} else {
			return false;
		}
	}
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
	if ($('#employerDetails_form').validate(false, validationSettings)) {
        var formData = getFormData("employerDetails_form");
        var candidateId = $('#candidateID').val();
        var immigrationId = $('#immigrationID').val();
        $.ajax({
            url: sourceleadUrl + immiModulePath + "/saveImmigrationEmployerDetails?" + tokenParamName + "=" + tokenValue + "&candidateId="+candidateId+"&immigrationId="+immigrationId,
            type: "POST",
            data: formData,
            success: function (data) {
                if (data == 'success') {
                    if (status == 'saveContinue') {
                        $("#addNewPetitionTemplateDiv").empty();
                        $("#addNewPetitionTemplateDiv").load(sourceleadUrl + immiModulePath + "/gotoTemplatePreviousPage?status=taskList");
                    } else if (status == 'save') {
                        $("#addNewPetitionTemplateDiv").empty();
                        $("#addNewPetitionTemplateDiv").load(sourceleadUrl + immiModulePath + "/gotoTemplatePreviousPage?status=formDetails");
                    }
                }
            }
        });

        return true;
    } else {
        return false;
    }
	
}

function savePassportDetails(status) {
	if ($('#Candidate_Passport_form').validate(false, validationSettings)) {
        var formData = getFormData("Candidate_Passport_form");
        var candidateId = $('#candidateId').val();
        var immigrationId = $('#immigrationId').val();
        $.ajax({
            url: sourceleadUrl + immiModulePath + "/savePassportDetailsPage?" + tokenParamName + "=" + tokenValue + "&candidateId="+candidateId+"&immigrationId="+immigrationId,
            type: "POST",
            data: formData,
            success: function (data) {
                if (data == 'success') {
                    if (status == 'saveContinue') {
                        $("#addNewPetitionTemplateDiv").empty();
                        $("#addNewPetitionTemplateDiv").load(sourceleadUrl + immiModulePath + "/gotoTemplatePreviousPage?status=taskList");
                    } else if (status == 'save') {
                        $("#addNewPetitionTemplateDiv").empty();
                        $("#addNewPetitionTemplateDiv").load(sourceleadUrl + immiModulePath + "/gotoTemplatePreviousPage?status=formDetails");
                    }
                }
            }
        });

        return true;
    } else {
        return false;
    }
}
