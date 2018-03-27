var immigration = new Immigration();
var immiModulePath = "/immigration";
var immTemplId = '';
var immigrationId = '';
var candidateId = '';
var familyMemberId = id;
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
    
    this.saveEducationImmigration = function (status) {
        if ($('#eduSave_Form').validate(false, validationSettings)) {
            var formData = getFormData("eduSave_Form");
            var candidateId = $('#candidateID').val();
            var immigrationId = $('#immigrationID').val();
            $.ajax({
                url: sourceleadUrl + immiModulePath + "/saveEducationImmigrationDedtails?" + tokenParamName + "=" + tokenValue + "&candidateId=" + candidateId+"&immigrationId="+immigrationId,
                type: "POST",
                data: formData,
                success: function (data) {
                    if (data == 'Success') {
                    	$('#popEduView').hide();
                    	$('.popMask').fadeOut();
                    	getCandidateEducationList(candidateId);

                    }
                }
            });
            return true;
        } else {
            return false;
        }
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
    
    this.savePassportVisaDetails= function(idType) {
    	var formId= "";
    	if(idType =="Passport"){
    			formId ="CandidatePassportform";
    		}
    	 	else if(idType =="Visa"){
      		formId ="CandidateVisaform";
      		 	  	}
    	if ($('#'+formId).validate(false, validationSettings)) {
    			var formData = getFormData(formId);
    		    console.log(" inside function formId"+formId);
    		 	console.log(formData);
    		 	var candidateId = $('#candidateID').val();
                var immigrationId = $('#immigrationId').val();
            $.ajax({
                url: sourceleadUrl + immiModulePath + "/savePassportVisaDetailsPage?" + tokenParamName + "=" + tokenValue + "&candidateId="+candidateId+"&immigrationId="+immigrationId+"&idType="+idType,
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
   
    this.savePassportVisaDetails= function(idType) {
    	var formId= "";
	if(idType =="Passport"){
			formId ="CandidatePassportform";
		}
	 	else if(idType =="Visa"){
  		formId ="CandidateVisaform";
  		 	  	}
	if ($('#'+formId).validate(false, validationSettings)) {
			var formData = getFormData(formId);
		    console.log(" inside function formId"+formId);
		 	console.log(formData);
		 	var candidateId = $('#candidateID').val();
            var immigrationId = $('#immigrationId').val();
            $.ajax({
            url: sourceleadUrl + immiModulePath + "/savePassportVisaDetailsPage?" + tokenParamName + "=" + tokenValue + "&candidateId="+candidateId+"&immigrationId="+immigrationId+"&idType="+idType,
            type : "POST",
			data : formData,
			success : function(data) {
							console.log(" inside success function"+idType);
							if (data == 'Success') {
								console.log("data successfully saved for the candidate id "+ immigrationId);
								if (idType == 'Passport') {
									$('#popPassView').hide();
									$('.popMask').fadeOut();
									immigration.displayPassportDetails(candidateId, idType);
								} else if (idType == 'Visa') {
									$("#popUpVisa").hide();
									$(".popMask").fadeOut(200); // close the popup code															// popup code
									immigration.displayVisaDetails(candidateId,idType);
								}
    					
				}
			}
		});
		return true;
	} else {
		return false;
	}
}

    
    
 this.displayPassportDetails=function(candidateId,Passport){
	console.log("inside displayPassportDetails function call  -->")
	var Passport=document.getElementById("Passport").value;
	    	$.ajax({
        url: sourceleadUrl + immiModulePath + "/getCandidatePassportVisaDetails?candidateId="+candidateId+ "&idType=" + Passport,
        type: "GET",
        success: function (result) {
            $("#passportsTab").html(result);
           
        }
    });
}	
this.displayVisaDetails=function(candidateId,Visa){
	console.log("inside displayVisaDetails function call  -->")
	var Visa=document.getElementById("Visa").value;
	$.ajax({
        url: sourceleadUrl + immiModulePath + "/getCandidatePassportVisaDetails?candidateId="+candidateId+ "&idType=" + Visa,
        type: "GET",
        success: function (result) {
            $("#visasTab").html(result);
           
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
			console.log(" inside function formId"+formId);
			console.log(formData);
			 var candidateId = $('#candidateID').val();
			$.ajax({
				url : sourceleadUrl + immiModulePath+ "/saveCandidateFamilyDetails?candidateId="+candidateId+"&relationType="+relationType+"&memberId="+familyMemberId,
				type : "POST",
				data : formData,
				success : function(data) {
					console.log(" inside success function");
					if (data == 'success') {
                        console.log("data successfully saved for the candidate id "+templateId);
                        $(".popMask").fadeOut(200); // close the popup code
        				$("#spousePopView").fadeOut(100);// close the popup code
        				immigration.displayFamilyDetails(candidateId);
					}
				}
			});

			return true;
		} else {
			return false;
		}
	}
    this.displayFamilyDetails=function(candidateId){
    	console.log("inside displayFamilyDetails function call  -->")
    	$.ajax({
            url: sourceleadUrl + immiModulePath + "/getCandidateFamilyDetails?candidateId="+candidateId,
            type: "GET",
            success: function (result) {
                $("#familiesTab").html(result);
                addfamilyPopupEventListener();
            }
        });
    }	
	this.deleteFamilyDetailsById = function(delId){
		console.log(" inside delete record  "+delId);
		$.ajax({
            url: sourceleadUrl + immiModulePath + "/deleteCandidateFamilyDetails?id="+delId,
            type: "GET",
            success: function (result) {
            	if(result=='success'){
            		console.log(" record successfully delete id  --> "+delId);
            	     immigration.displayFamilyDetails(candidateId) //candidateId global variable
            	     
            	}
            }
        });
	}
	
	this.editFamilyDetailsById = function(id, relationType){
		console.log(" inside edit record  "+id);
		familyMemberId = id; // update the variable with family member id in edit mode.
		$.ajax({
            url: sourceleadUrl + immiModulePath + "/getCandidateFamilyDetailsById?id="+id,
            type: "GET",
            success: function (data) {
            	console.log(" inside success test --->" );
            	var formObj='';
            	if(data!= null){
            		console.log(" record successfully fetched by id  --> "+id);
            		if(relationType =='Child'){
            			formObj = $('#childDetailsForm')[0]; // load the child form
            			var childPopNames=["firstName","middleName", "lastName","dateOfBirth","placeOfBirth","currentAddress"];
            			$("#addChil").trigger('click');
            			immigration.udpatePopupFormdata(formObj,childPopNames,data);
            		}else if(relationType =='Spouse'){
            			formObj = $('#spouseDetailsForm')[0]; // load the Spouse form
            			var spousePopNames=["firstName","middleName", "lastName","marriagePlace","marraigeDate", "marriageEndDate","dateOfBirth","placeOfBirth","currentAddress"];
            			$("#addSpou").trigger('click');
            			immigration.udpatePopupFormdata(formObj,spousePopNames,data);
            		}else if(relationType =='Father'){
            			formObj = $('#fatherDetailsForm')[0]; // load the Father form
            			var fatherPopNames=["firstName","middleName", "lastName","dateOfBirth","placeOfBirth","currentAddress"];
            			$("#addFath").trigger('click');
            			immigration.udpatePopupFormdata(formObj,fatherPopNames,data);
            			
            		}else if(relationType =='Mother'){
            			formObj = $('#motherDetailsForm')[0]; // load the mother form
            			var motherPopNames=["firstName","middleName", "lastName","dateOfBirth","placeOfBirth","currentAddress"];
            			$("#addMoth").trigger('click');
            			immigration.udpatePopupFormdata(formObj,motherPopNames,data);
            		}
            	   
            	}
            }
        });
	}
	this.udpatePopupFormdata = function(formObj, idsArray, dataObj) {
		  var formId = formObj.id;
		   $.each(formObj, function(key, value) {
			var formElemName = value.name;
			if (typeof formElemName!= 'undefined' && idsArray.indexOf(formElemName) > -1) {
				console.log(" inside ** formElemId:-> "+formElemName +",  dataObj.formElemId: ->"+dataObj[formElemName]);
				// below code added for date fields in forms based on name paroperty of the field. date should contain date string in filed name.
				if(formElemName.toLowerCase().indexOf("date")>-1 && dataObj[formElemName] != null && typeof  dataObj[formElemName] != 'undefined' && dataObj[formElemName]!=''){
					console.log(" inside date value---> ");
					var dateVal =  moment(dataObj[formElemName]).format("DD-MM-YYYY");
					$("#"+formId+" input[name='"+formElemName+"']").val(dateVal); // sets date values to the form.
				}// above code checks the date field names and set the formatted date value to the popup.
				else{
				 $("#"+formId+" input[name='"+formElemName+"']").val(dataObj[formElemName]); // setting non date values to the from
				}
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
	console.log(" inside edit record  "+id);
	 // update the variable with family member id in edit mode.
	var candidateId = $("#candidateID").val();
	$.ajax({
        url: sourceleadUrl + immiModulePath + "/getCandidateEducationDetailsById?id="+id+"&candidateId="+candidateId,
        type: "GET",
        success: function (data) {
        	console.log(" inside success test --->" );
        	var formObj='';
        	if(data!= null){
        		console.log(" record successfully fetched by id  --> "+id);
        		
        			formObj = $('#eduSave_Form')[0]; // load the child form
        			var fomValues=["name","specialization", "startDate","endDate","gradeType","grade","institute","address"];
        			$("#addEdu").trigger('click');
        			udpateEduFormdata(formObj,fomValues,data);
        			$('#eduName').val(data.name); // to select the dropdwon value based on id value.
        			$('#scoreEducationType').val(data.gradeType);
        			$('#candidateEducationId').val(data.id);
        	
        	   
        	}
        }
    });
}

function editEmpDetailsByRowId(id){
	console.log(" inside edit record  "+id);
	 // update the variable with family member id in edit mode.
	var candidateId = $("#candidateID").val();
	$.ajax({
        url: sourceleadUrl + immiModulePath + "/getCandidateEmpDetailsById?id="+id+"&candidateId="+candidateId,
        type: "GET",
        success: function (data) {
        	console.log(" inside success test --->" );
        	var formObj='';
        	if(data!= null){
        		console.log(" record successfully fetched by id  --> "+id);
        		
        			formObj = $('#employerDetails_form')[0]; // load the child form
        			var fomValues=["name","designation", "address","startMonth","startYear","endMonth","endYear"];
        			$("#addEmpl").trigger('click');
        			udpateEduFormdata(formObj,fomValues,data);
        			$('#startMonth').val(data.startMonth); // to select the dropdwon value based on id value.
        			$('#endMonth').val(data.endMonth);
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
			console.log(" inside ** formElemId:-> "+formElemName +",  dataObj.formElemId: ->"+dataObj[formElemName]);
			// below code added for date fields in forms based on name paroperty of the field. date should contain date string in filed name.
			if(formElemName.toLowerCase().indexOf("date")>-1 && dataObj[formElemName] != null && typeof  dataObj[formElemName] != 'undefined' && dataObj[formElemName]!=''){
				console.log(" inside date value---> ");
				var dateVal =  moment(dataObj[formElemName]).format("DD-MM-YYYY");
				$("#"+formId+" input[name='"+formElemName+"']").val(dateVal); // sets date values to the form.
			}// above code checks the date field names and set the formatted date value to the popup.
			else{
			 $("#"+formId+" input[name='"+formElemName+"']").val(dataObj[formElemName]); // setting non date values to the from
			}
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
                	$('#emploView').hide();
                	$('.popMask').fadeOut();
                	getCandidateEmployerList(candidateId);
                }
            }
        });

        return true;
    } else {
        return false;
    }
	
}

function getCandidateEducationList(candidateId){
	$.ajax({
        url: sourceleadUrl + immiModulePath + "/getCandidateEducationList?candidateId="+candidateId,
        type: "GET",
        success: function (result) {
            $("#educationsTab").html(result);
        }
    });
}

function getCandidateEmployerList(candidateId){
	$.ajax({
        url: sourceleadUrl + immiModulePath + "/getCandidateEmployerList?candidateId="+candidateId,
        type: "GET",
        success: function (result) {
            $("#employersTab").html(result);
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
            success: function (data) {
            	if (data === 'success') {
                	$('#popResiView').hide();
                	$('.popMask').fadeOut();
                	getCandidateResidenceList(candidateId);
                }
            }
        });

        return true;
    } else {
        return false;
    }
	
}

function getCandidateResidenceList(candidateId){
		console.log("inside displayResidenceDetails function call test -->")
		$.ajax({
	        url: sourceleadUrl + immiModulePath + "/getCandidateResidenceList?candidateId="+candidateId,
	       type: "GET",
	        success: function (result) {
	       	  console.log("Before  loading result---->");
	           $("#residencesTab").html(result);
	           console.log("After loading result---->");
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