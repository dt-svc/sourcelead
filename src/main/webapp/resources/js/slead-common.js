
function tagIt(id, noResultText, func,type,placeholderName) {
	if(type == "emails"){
		var select, chosen;
	    select = $("#"+id);
	    select.chosen({ no_results_text: noResultText });
	    chosen = select.data('chosen');
	    chosen.search_field.bind(('keydown keyup'), function(e)
	    { 
	    	if(this.value === noResultText) {
	    		this.placeholder = this.value;
	    		this.value = "";
	    	} else {
	    		this.placeholder = "";
	    	}
	    	
	    	 var enterEmailArray = this.value.split(/[ ,]+/);
	    	 if(enterEmailArray.length > 1){
	    		 for (var i=0; i < enterEmailArray.length; i++) {
	    			 var option = $("<option>").val(enterEmailArray[i].replace(",","")).text(enterEmailArray[i].replace(",",""));
	    			 select.append(option);
			             select.find(option).prop('selected', true);
		        		 $("#"+id).trigger("chosen:updated");
	             }
	    	 }else{
		    	var newEmail = this.value.replace(","," ");
		        if ((newEmail != null  && newEmail != '' && newEmail != 'undefined') && window[func](e))
		        {
		        	preventTabkey(e); 
		        	var par = this.parent;
		        		 var option = $("<option>").val(newEmail.replace(",","")).text(newEmail.replace(",",""));
		        		 select.append(option);
				            select.find(option).prop('selected', true);
			        		$("#"+id).trigger("chosen:updated");
		        }
	    	 }
	    });
	}else if(type =="skills"){
	
	var select, chosen;
	    select = $("#"+id);
	    select.chosen({ no_results_text: noResultText });
	    chosen = select.data('chosen');
	    chosen.search_field.on('keydown keyup', function(e)
			    {
				    	if(this.value === placeholderName) {
				    		this.placeholder = this.value;
				    		this.value = "";
				    	} else {
				    		this.placeholder = "";
				    	}
					    	
			    	   var skillsArray = this.value.split(",");
				    	 if(skillsArray.length > 1){
			        		 for (var i=0; i < skillsArray.length; i++) {
			        			 if(skillsArray[i] == '' || skillsArray[i] == 'undefined' || skillsArray[i] == null)
        		        		     continue;
			        			 var option = $("<option>").val(skillsArray[i].replace(",","")).text(skillsArray[i].replace(",",""));
			        			 select.append(option);
			 		             select.find(option).prop('selected', true);
			 		             select.find(option).siblings("[value='"+ skillsArray[i].replace(",","")+"']").remove();
			 	        		 $("#"+id).trigger("chosen:updated");
			                 }
				    	 }else{
				    		var focussedSkills = $("ul.chosen-results").find("li.active-result.highlighted");
			    			var newSkill = this.value.replace(","," ");
			    			 if ((newSkill != null  && newSkill != '' && newSkill != 'undefined') && window[func](e))
			                 {
			    				 preventTabkey(e);
			                   // below code manually enter skills are taken
			    				  
			                   var isNewSkill = true;
								 $("#"+id+" option:selected").each(function(){
									 if(this.value.toUpperCase() === newSkill.toUpperCase() && this.value.toLowerCase() === newSkill.toLowerCase()){
										 isNewSkill = false;
										 return false;
									 }
			                    
			                   });
								
									if(focussedSkills.length > 0){
										var skillOption  = $("<option>").val(focussedSkills.first().text()).text(focussedSkills.first().text());
										select.append(skillOption);
										select.find(skillOption).prop('selected',true);
										select.find(skillOption).siblings("[value='"+ focussedSkills.first().text()+"']").remove();
										$("#"+id).trigger("chosen:updated");
								   }
								   else{
									   if(isNewSkill){
										var option = $("<option>").val(newSkill).text(newSkill);
										select.append(option);
										select.find(option).prop('selected', true);
									   }
								   }
								
				               $("#"+id).trigger("chosen:updated"); 
			                   
			                }
					       
		    	         }
	    	});
	}else if(type =="tags"){
		
		var select, chosen;
		    select = $("#"+id);
		    select.chosen({ no_results_text: noResultText });
		    chosen = select.data('chosen');
		    chosen.search_field.on('keydown keyup', function(e)
				    {
					    	if(this.value === placeholderName) {
					    		this.placeholder = this.value;
					    		this.value = "";
					    	} else {
					    		this.placeholder = "";
					    	}
						    	
				    	   var tagsArray = this.value.split(",");
					    	 if(tagsArray.length > 1){
				        		 for (var i=0; i < tagsArray.length; i++) {
				        			 var option = $("<option>").val(tagsArray[i].replace(",","")).text(tagsArray[i].replace(",",""));
				        			 select.append(option);
				 		             select.find(option).prop('selected', true);
				 		             select.find(option).siblings("[value='"+ tagsArray[i].replace(",","")+"']").remove();
				 	        		 $("#"+id).trigger("chosen:updated");
				                 }
					    	 }else{
					    		var focussedTags = $("ul.chosen-results").find("li.active-result.highlighted");
				    			var newTag = this.value.replace(","," ");
				    			 if ((newTag != null  && newTag != '' && newTag != 'undefined') && window[func](e))
				                 {
				    				 preventTabkey(e);
				                   // below code manually enter tag are taken
				    				  
				                   var isNewTag = true;
									 $("#"+id+" option:selected").each(function(){
										 if(this.value.toUpperCase() === newTag.toUpperCase() && this.value.toLowerCase() === newTag.toLowerCase()){
											 isNewTag = false;
											 return false;
										 }
				                    
				                   });
									
										if(focussedTags.length > 0){
											var tagOption  = $("<option>").val(focussedTags.first().text()).text(focussedTags.first().text());
											select.append(tagOption);
											select.find(tagOption).prop('selected',true);
											select.find(tagOption).siblings("[value='"+ focussedTags.first().text()+"']").remove();
											$("#"+id).trigger("chosen:updated");
									   }
									   else{
										   if(isNewTag){
											var option = $("<option>").val(newTag).text(newTag);
											select.append(option);
											select.find(option).prop('selected', true);
										   }
									   }
									
					               $("#"+id).trigger("chosen:updated"); 
				                   
				                }
						       
			    	         }
		    	});
		}
	
}

/*$(function() {

    for (var selector in config) {
		$(selector).chosen(config[selector]);
	}
	
    $('.chosen-select').each(function() {
		var $this = $(this);
		$this.next().css({'width': '100%'});
	});

});*/

function viewFile(fileURL, title, contentType){
	if(fileURL != null && fileURL != "" && fileURL != "undefined"){
		window.open(sourceleadUrl+'/viewFile?documentPath='+encodeURI(fileURL)+"&contentType="+contentType,title,"height=600,width=600");
	}  
}

function getFormData(formId) {
	var $form = $("#"+formId); 
	var disabled = $form.find(':input:disabled').removeAttr('disabled'); // Added by Srinivas Sabbu on 25-Feb-2019 for pushing disabled element values to form.
	var unindexed_array = $($form).serializeArray();
	var indexed_array = {};
	$.map(unindexed_array, function (n, i) {
		if(($("[name="+n[name]+"]").attr('type') && $("[name="+n[name]+"]").attr('type').toLowerCase() === "text") || $("[name="+n[name]+"]").is("textarea"))
			indexed_array[n['name']] = n['value'].trim().replace(/  +/g,' ');
		else if(n['name'])
			indexed_array[n['name']] = n['value'];
	});
	return indexed_array;
}

function getFormData1(formId) {
	var $form = $("#"+formId);
	var unindexed_array = $($form).serializeArray();
	var formData = new FormData();
	$.map(unindexed_array, function (n, i) {
		if(($("[name="+n[name]+"]").attr('type') && $("[name="+n[name]+"]").attr('type').toLowerCase() === "text") || $("[name="+n[name]+"]").is("textarea"))
			formData.append(n['name'], n['value'].trim().replace(/  +/g,' '));
		else if(n['name'])
			formData.append(n['name'], n['value']);
	});
	return formData;
}


function fileUpload(id, type, formId, name, orgId, orgLocationId,url) {
	$(".file_mask").show();
	if(name == null || name == 'undefined' || name == ''){
		name = 'fileInfo';
	}
	if(orgId == null || orgId == "undefined" || orgId == "")
		orgId = 0;
	if(orgLocationId == null || orgLocationId == "undefined" || orgLocationId == "")
		orgLocationId = 0;
	var data = new FormData();
	var files = document.getElementById(id).files;
	var invalidFiles = false;
	var count = 0;
	expr = /.zip/;
	ecpr1=/.rar/;
	for(var e in files) {
		if(!$.isNumeric(e))
			continue;
		//if(files[e].name.toLowerCase().endsWith(".zip") || files[e].name.toLowerCase().endsWith(".rar"))
		if(files[e].name.toLowerCase().match(expr) || files[e].name.toLowerCase().match(ecpr1))
			continue;
		data.append('file', files[e]);
		count ++;
	}
	if(count === 0) {
		$("#"+formId+" input[data-id*="+id+"]").remove();
		$(".file_mask").hide();
		alert("Please select valid file (zip files and rar files are not allowed)");
		return;
	}
	data.append('orgId', orgId);
	data.append('orgLocationId', orgLocationId);
	$.ajax({ 
		url: sourceleadUrl+(url ? url : "/ws/upload/"+type), 
		data: data, 
		processData: false, 
		type: 'POST', 
		contentType: false, 
		success: function (data) {
			if(data && data.length > 0) {
				if(type === "dataMigration"){
					$("#dataFilePath").val(data[0].fileURL);
				}
				applyData(data, formId, name, id);
				$(".file_mask").hide();
			}
		}, 
		error: function (err) { 
			 $(".file_mask").hide();
		} 
	});
}

function applyData(data, formId, name, id) {
	if($("#"+formId+" input[name*='"+name+"'][data-id="+id+"]").length > 0) {
		$("#"+formId+" input[name*='"+name+"'][data-id="+id+"]").remove();
	}
	if(name == 'uploadContractsFile' || name == 'uploadFormsFile'){
		var i = $("#"+formId+" input[type=hidden][name*='"+name+"']").length / 4;
	}
	else if(formId == 'vendorOnboardUploadedDocuments_form'){
		var i = name.split('@')[1];
		  name = name.split('@')[0];
	} else if(formId == 'timesheet_form'){
		if($("#"+formId+" input[data-id*='"+id+"']").length > 0){
			$("#"+formId+" input[data-id*='"+id+"']").remove();
		}
		if(name == 'timesheetSubmission.fileInfo'){
            var i = id.split("docFile")[0];
        }else{
            var i = id.split("docFile")[1];
        }
		
	}
	else{
		var i = $("#"+formId+" input[type=hidden][name*='"+name+"']").length / 3;
	}
	
	for(var e in data) {
		if(!$.isNumeric(e))
			continue;
		var data1 = data[e];
		if(data[0].fileInfo) {
			data1 = data[e].fileInfo;
		}
		var html = "<input type='hidden' name='"+name+"["+i+"].fileURL' data-id="+id+" value=\""+data1.fileURL+"\"/>";
		html += "<input type='hidden' name='"+name+"["+i+"].fileName' data-id="+id+" value=\""+data1.fileName+"\"/>";
		html += "<input type='hidden' name='"+name+"["+i+"].contentType' data-id="+id+" value='"+data1.contentType+"'/>";
		if(name == 'uploadContractsFile' || name == 'uploadFormsFile'){
			html += "<input type='hidden' name='"+name+"["+i+"].fileDbId' data-id="+id+" value='"+id.split(name)[1]+"'/>";
		}
		$("#"+formId).append(html);
		i ++;
	}
	if(data && data.length > 0 && data[e].fileInfo) {
		if(data[e].fileInfo.fileURL !== null && data[e].fileInfo.fileURL !== "") {
			for(var e in data) {
				applyResumeData(data[e]);
			}
		} else {
			alert("Please try again....");
			$("#loading").removeAttr("style");
		}
	}
}


function showError(error) {
    showMessage(error, 2);
}

function showAlert(message, redirectFlag) {
    showMessage(message, 1, redirectFlag);
}

var rFlag = false;
function showMessage(message, type, redirectFlag) {
    rFlag = redirectFlag ? redirectFlag : false;
    $(".alert" + (type === 1 ? ".alert-success" : ".alert-danger") + " span").html(message);
    $(".alert").show();
    $(".alert" + (type === 1 ? ".alert-success" : ".alert-danger")).addClass('bounceInUp animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
        $(".alert" + (type === 1 ? ".alert-success" : ".alert-danger")).removeClass('bounceInUp animated');
    });
}

function hideMessage(type) {
    $(".alert" + (type === 1 ? ".alert-success" : ".alert-danger")).addClass('bounceOutDown animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
         $(".alert" + (type === 1 ? ".alert-success" : ".alert-danger")).removeClass('bounceOutDown animated');
         $(".alert" + (type === 1 ? ".alert-success" : ".alert-danger")).hide();
    });
    if(rFlag) {
        location.href = "/";
    }
}

$(function() {
	$(".alert div.close").click(function () {
        hideMessage(parseInt($(this).data('type')));
    });
});

function saveCandidateImage(candidateId, formId, data){
	if ($('#'+formId).validate(false, validationSettings)){
          		var formData = getFormData(formId);
	            		
	            $.ajax({
	                	      type: "POST",
	                	      data: formData,
	                      	  url: atsUrl+"/saveCandidateProfile?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,
	                          success: function(result) {
	                        	  if(result){
	                        		  $("img[data-id=profileImage]").each(function(i) { $(this).attr('src', data[0].fileURL); });
	                        		  cancel(candidateId);
	                        		  candidateQuickViewProfile(candidateId);
	                        	  }
	                        	  else
	                        		  cancel(candidateId);
	    			          },  
	            });
	            return true;         
	}else{ 
		return false;
	 } 
}


function saveUserProfileImage(userId,formId,data){
    if ($('#'+formId).validate(false, validationSettings)){
          var formData = getFormData(formId);
        if(formId === "profileImage_form" || formId === "associate_Image_form"){    
        $.ajax({
              type: "POST",
              data: formData,
              url: sourceleadUrl+"/saveUserProfileImage?"+tokenParamName+"="+tokenValue+"&userId="+userId,
              success: function(result) {
                  if(result){
                      $("img[data-id=userProfileImage]").each(function(i) { $(this).attr('src', data[0].fileURL); });
                  if(userId !=null){
                	location.reload();  
                  }else{
                	  location.replace(sourceleadUrl+"/userProfile");  	  
                  }
                  }
              },  
        });
        } else{
            $.ajax({
                type: "POST",
                data: formData,
                url: sourceleadUrl+"/saveOrgImage?"+tokenParamName+"="+tokenValue,
                success: function(result) {
                    if(result)
                        $("img[data-id=orgLogoImage]").each(function(i) { $(this).attr('src', data[0].fileURL); });
                    location.reload();
                  },  
              });
        }
        return true;
     }else{ 
           return false;
     }
}

function uploadResumeOnSelect(formId) {
	$("#uploadBtn").change(function() {
		$("#uploadFile").focus();
     	      $("#uploadFileGroup").find('p.jquery_form_error_message').remove();
     	     $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
 		     fileUpload("uploadBtn", "resumes", formId, null, null, null, "/parse-resumes");
 		    $(".clearUpload").show();

	});
}

function applyResumeData(json) {
	//clearing previous values
		$("#firstName").val("");
		$("#middleName").val("");
		$("#lastName").val("");
		$("#email").val("");
		$("#mobilePhone").val("");
		$("#alternateMobilePhone").val("");
		$("#candidateLocation").val("");
		$("#address").val("");
		$("#candidateCity").val("");
		$("#candidateState").val("");
		$("#candidateCountry").val("");
		$("#candidateZipCode").val("");
		$("#dateofBirth").val("");

		$("input[value='Single']").prop("checked", false);
		$("input[value='Married']").prop("checked", false);
		$("input[value='Divorsed']").prop("checked", false);
		$("input[value='Separated']").prop("checked", false);
		
		$("input[value='Male']").prop("checked", false);
		$("input[value='Female']").prop("checked", false);
		$("input[value='Transgender']").prop("checked", false);
		
		$("#designation").val("");
		$("#experienceYears").val("");
		$("#experienceMonths").val("");
		$("#candidateEmployers").val("");
		$("#candidateSkills").html("");
		$("#candidateEducation").html("");
		$("#summary").val("");
		
	//updating fields with parsed values	
		$("#firstName").val($("#firstName").attr('readonly') ? $("#firstName").val() : json.firstName);
		$("#middleName").val($("#middleName").attr('readonly') ? $("#middleName").val() : json.middleName);
		$("#lastName").val($("#lastName").attr('readonly') ? $("#lastName").val() : json.lastName);
		if(json.email !== null) {
			$("#email").val($("#email").attr('readonly') ? $("#email").val() : json.email.split("<<>>")[0]);
		}
		if(json.phoneNumbers !== null) {
			for(var e in json.phoneNumbers) {
				$(e === "0" ? "#mobilePhone" : "#alternateMobilePhone").val(json.phoneNumbers[e]);
			}
		}

		if(!$("#email").attr('readonly'))
			validateCandidateEmail();
		
		var location = "";
		location += (json.addressLine1 === null || json.addressLine1 === "") ? "" : ((location === "" ? "" : ", ") + json.addressLine1);
		location += (json.city === null || json.city === "") ? "" : ((location === "" ? "" : ", ") + json.city);
		location += (json.state === null || json.state === "") ? "" : ((location === "" ? "" : ", ") + json.state);
		location += (json.zipcode === null || json.zipcode === "") ? "" : ((location === "" ? "" : ", ") + json.zipcode);
		$("#candidateLocation").val(location);
		
		$("#address").val(json.addressLine1 !== null && json.addressLine1 !== "" ? json.addressLine1 : "");
		$("#candidateCity").val(json.city !== null && json.city !== "" ? json.city : "");
		$("#candidateState").val(json.state !== null && json.state !== "" ? json.state : "");
		$("#candidateCountry").val(json.country !== null && json.country !== "" ? json.country : "");
		$("#candidateZipCode").val(json.zipcode !== null && json.zipcode !== "" ? json.zipcode : "");
		
		$("#dateofBirth").val(json.dob !== null && json.dob !== "" ? json.dob : "");
		
		if(json.maritalStatus !== null) {
			if(json.maritalStatus.toLowerCase() === "single") {
				$("input[value='Single']").prop("checked", true);
			} else if(json.maritalStatus.toLowerCase() === "married") {
				$("input[value='Married']").prop("checked", true);
			} else if(json.maritalStatus.toLowerCase() === "divorsed") {
				$("input[value='Divorsed']").prop("checked", true);
			} else if(json.maritalStatus.toLowerCase() === "separated") {
				$("input[value='Separated']").prop("checked", true);
			}
		}
		
		if(json.gender != null) {
			if(json.gender.toLowerCase() === "male") {
				$("input[value='Male']").prop("checked", true);
			} else if(json.gender.toLowerCase() === "female") {
				$("input[value='Female']").prop("checked", true);
			}
		}
		
		$("#designation").val(json.designation !== null && json.designation !== "" ? json.designation : "");
		
		if(json.totalYearsExp > 0) {
			$("#experienceYears").val(json.totalYearsExp);
		} else if(json.totalMonthsExp > 0) {
			$("#experienceYears").val(0);
		}
		$("#experienceYears").selectpicker('refresh');
		
		if(json.totalMonthsExp > 0) {
			$("#experienceMonths").val(json.totalMonthsExp);
		} else if(json.totalYearsExp > 0) {
			$("#experienceMonths").val(0);
		}
		$("#experienceMonths").selectpicker('refresh');
		
		$("#candidateEmployers").val(json.employeers);
		
		$("#candidateSkills").empty();
		
		var internalOutput = "";
		var skills = json.skills;
		if(skills != null && skills != 'undefined' && skills != ''){
			var str_array = skills;
		} else {
			str_array = [];
		}
		
	   for(var j = 0; j< skillList.length; j++){
		    var existed = 0;
			for(var i = 0; i < str_array.length; i++) {
			   str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
			   if(str_array[i] == skillList[j]){
				   existed = 1;
				   internalOutput = internalOutput+"<option value='"+str_array[i]+ "' selected>"+str_array[i]+"</option>";
				   break;
			   }
			}
			if(existed == 0){
				internalOutput = internalOutput+"<option value='"+skillList[j]+"'>"+skillList[j]+"</option>";
	        }
		 }
	   
	   for(var e in str_array) {
			internalOutput = internalOutput+"<option value='" + str_array[e] + "' selected>" + str_array[e] + "</option>";
		}
		
		$("#candidateSkills").html(internalOutput);
		$("#candidateSkills").trigger("chosen:updated");
		
		$("#candidateEducation").empty();
		
		 var internalOutput = "";
		 var edu = json.educations;
		 if(edu != null && edu != 'undefined' && edu != ''){
			var str_array = edu;
			var index = educationList.length;
			for(var i = 0; i < edu.length; i++) {
				if(educationList.indexOf(edu[i]) === -1)
					educationList[index++] = edu[i];
			}
		 }else{
			 str_array = [];
		 }
		 
		 for(var j = 0; j< educationList.length; j++){
			var existed = 0;
			for(var i = 0; i < str_array.length; i++) {
			   str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
			   if(str_array[i] == educationList[j]){
				   existed = 1;
				   internalOutput = internalOutput+"<option value='" +str_array[i]+ "' selected>"+str_array[i]+"</option>";
				   break;
			   }
			}
			if(existed == 0){
				internalOutput = internalOutput+"<option value='"+educationList[j]+"'>"+educationList[j]+"</option>";
	        }
		 }		

		$("#candidateEducation").append(internalOutput);
		$("#candidateEducation").trigger("chosen:updated");
		
		$("#summary").val(json.summary);
		$("#loading").removeAttr("style");
	}

//function applyResumeData(json) {
//	
//	var name = json.name;
//	var firstName = '';
//	var lastName = '';
//	if(name != null && name != 'undefined' && name != ''){
//		 firstName = name.split(".")[0]; 
//		 lastName = name.split(".")[1]; 
//		 if(firstName == null || firstName ==  'undefined' || firstName == '' ||
//				 lastName ==  null || lastName ==  'undefined'|| lastName ==  ''){
//			 firstName = name;
//		 }
//	}
//	
//	$("#firstName").val($("#firstName").attr('readonly') ? $("#firstName").val() : firstName);
//	$("#lastName").val($("#lastName").attr('readonly') ? $("#lastName").val() : lastName);
//	$("#email").val($("#email").attr('readonly') ? $("#email").val() : json.email);
//	$("#mobilePhone").val($("#mobilePhone").attr('readonly') ? $("#mobilePhone").val() : json.phoneNumbers);
//	if(!$("#email").attr('readonly'))
//		validateCandidateEmail();
//	$("#candidateSkills").empty();
//	
//	var internalOutput = "";
//	var skills = json.skills;
//	if(skills != null && skills != 'undefined' && skills != ''){
//		var str_array = skills.split(',');
//	} else {
//		str_array = [];
//	}
//	
//   for(var j = 0; j< skillList.length; j++){
//	    var existed = 0;
//		for(var i = 0; i < str_array.length; i++) {
//		   str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
//		   if(str_array[i] == skillList[j]){
//				 existed = 1;
//				 internalOutput = internalOutput+"<option value='"+str_array[i]+ "' selected>"+str_array[i]+"</option>";
//				 break;
//		  }
//		}
//		if(existed == 0){
//			internalOutput = internalOutput+"<option value='"+skillList[j]+"'>"+skillList[j]+"</option>";
//        }
//	 }
//	
//	$("#candidateSkills").html(internalOutput);
//	$("#candidateSkills").trigger("chosen:updated");
//	$("#candidateEducation").trigger("chosen:updated");
//	
//	$("#candidateEducation").empty();
//	
//	 var internalOutput = "";
//	 var edu = json.edu;
//	 if(edu != null && edu != 'undefined' && edu != ''){
//			var str_array = edu.split(',');
//	 }else{
//		 str_array = [];
//	 }
//	 for(var j = 0; j< educationList.length; j++){
//		var existed = 0;
//	 
//		for(var i = 0; i < str_array.length; i++) {
//		   str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
//		   if(str_array[i] == educationList[j]){
//				   existed = 1;
//				   internalOutput = internalOutput+"<option value='" +str_array[i]+ "' selected>"+str_array[i]+"</option>";
//				   break;
//		   }
//		}
//		if(existed == 0){
//			internalOutput = internalOutput+"<option value='"+educationList[j]+"'>"+educationList[j]+"</option>";
//        }
//		
//	 }		
//	$("#candidateEducation").append(internalOutput);
//	$("#loading").removeAttr("style");
//}

function getJSDate(date,dateFormat){
	 var start = date.split("-");
    if(dateFormat.match("yyyy-MM-dd")){
	   var jsDate = new Date(start[0], start[1] - 1, start[2]);
	    }if(dateFormat.match("dd-MM-yyyy")){
	    	var jsDate = new Date(start[2], start[1] - 1, start[0]);
	    }if(dateFormat.match("MM-dd-yyyy")){
	    	var jsDate = new Date(start[2], start[0] - 1, start[1]);
	    }if(dateFormat.match("yyyy-dd-MM")){
	    	var jsDate = new Date(start[0], start[2] - 1, start[1]);
	    }
	    return jsDate;
}


function getJSDateFormat(locationDate){
	 var jsDateFormat= '';
	  if(locationDate.match("yyyy-MM-dd")){
		  jsDateFormat = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  jsDateFormat = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  jsDateFormat = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  jsDateFormat = 'yy-dd-mm'; 
	  }
	  return jsDateFormat;
}

function getOrgDateFormat(locationList){
var reqListDate = '';
	if(locationList.match("yyyy-MM-dd")){
		  reqListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  reqListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  reqListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  reqListDate = 'YYYY-DD-MM'; 
	  } 
	  return reqListDate;
}
function removeEmptyLines(ele) { //removeEmptyLines($(".."))
	if($(ele).is(":visible")) {
		$(ele).val($(ele).val().trim().replace(/  +/g,' ').replace(/(^(\r\n|\n|\r))/gm, ''));
		if($(ele).val().match("\n")) {
			$(ele).val($(ele).val().substring(0, $(ele).val().lastIndexOf("\n")));
		} else if($(ele).val().match("\r")) {
			$(ele).val($(ele).val().substring(0, $(ele).val().lastIndexOf("\r")));
		}
	}
	 return ele;
}

//=========== getMultiple cites written by Laxman ==============
multicities = {};
function getcites(locatinId,cityId,type){
	if(type == "recommendation"){
		var countryCode = "";
	}else{
		 var countryName = $("#country").val().split(',');
		 var countryCode = countryName[1];
	}
	 initializeCities(locatinId,countryCode,cityId,type);
}
function initializeCities(locatinId,countryCode,cityId,type) {
	if(type == "recommendation"){
		autocomplete = new google.maps.places.Autocomplete((document.getElementById(locatinId)), {
			types: ['(cities)']
	       });
	}else{
		autocomplete = new google.maps.places.Autocomplete((document.getElementById(locatinId)), {
	   		types: ['geocode'],
	   		componentRestrictions: {
	        	country: countryCode
	        }
	       });
	}
   google.maps.event.addListener(autocomplete, 'place_changed', function() {
   place = autocomplete.getPlace();
   var address = {};
   for (var i = 0; i < place.address_components.length; i++) {
   var addressType = place.address_components[i].types[0];
   switch (addressType) {
      case "route":
   	 address.name=place.address_components[i].long_name;
   	 break;
     case "locality":
       address.city = place.address_components[i].long_name;
       break;
     case "administrative_area_level_1":
       address.state = place.address_components[i].long_name;
       break;
     case "country":
       address.country = place.address_components[i].long_name;
       address.countryShortName = place.address_components[i].short_name;
       
   }
 }

 $("#"+locatinId).val(address.city).change();
 multicities[address.city] = address.city;
 
 var option = $("<option>").val(address.city).text(address.city);
 $("#"+cityId).append(option);
 $("#"+cityId).find(option).prop('selected', true);
 $("#"+cityId).find(option).siblings("[value='"+ address.city+"']").remove();
 $("#"+cityId).trigger("chosen:updated");
	 
   });
   
}
function refreshGoogleApiCity(cityId){
	$("#"+cityId).val('');
}


function validateEmail(emailId) {
    var email = $("#" + emailId).val().trim();
    if (email === "") {
        showError("Please enter email id");
        return false;
    } else if (!/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i.test(email)) {
        showError("Please enter valid email id");
        return false;
    }
    return true;
}

function validateEmailForExistenceUniversally(email, divId, spanId) {
	var flag = null;
	/*$.ajax({
	    type: "GET",
	    url: sourceleadUrl + "/validateEmailForExistenceUniversally?email="+email,
	    async: false,
	    cache: false,
	    success: function (data) {
	    	if(data.object === "true"){
	    		flag =  true;
	    	}else if(data.object === "false"){
	    		flag= false;
	    	}else if(data.object === "invalidDomain"){
	    		flag= "invalidDomain";
	    	}else if(data.object === "noResponse"){
	    		flag = "noResponse";
	    	}
	    }
	});

	if(flag === true){
		$(spanId).find("i").remove();
		$(spanId).append('<i class="fa fa-check text-succes"></i>');
	}else if(flag === false){
		$(spanId).find("i").remove();
		$(spanId).append('<i class="fa fa-exclamation-triangle text-warning"></i>');
		$(divId).after("<p class='jquery_form_error_message textWarning'><i class='fa fa-exclamation-triangle' aria-hidden='true'></i>This email universally not existed</span>");
	}else if(flag === "invalidDomain"){
		$(spanId).find("i").remove();
		$(spanId).append('<i class="fa fa-ban text-danger"></i>');
		$(divId).after("<p class='jquery_form_error_message textWarning'><i class='fa fa-ban' aria-hidden='true'></i>This domain universally not existed</span>");
	}*/
return true;
}


function validate(id, label) {
    var val = $('#' + id).val().trim();
    if (val === "") {
        showError("Please enter " + label);
        return false;
    }
    if($('#' + id).data('min')) {
        var v = parseInt($('#' + id).data('min'));
        if(val.length < v) {
            showError(label+" should be minimum "+ v+" character length");
            return false;
        }
    }
    if($('#' + id).data('max')) {
        var v = parseInt($('#' + id).data('max'));
        if(val.length > v) {
            showError(label+" should be maxinum "+ v+" character length");
            return false;
        }
    }
    return true;
}
function validateSelect(id, label) {
    var val = $('#' + id).val().trim();
    if (val === "" || val === "0") {
        showError("please select " + label);
        return false;
    }
    return true;
}