
function viewFile(fileURL, title, contentType){
	if(fileURL != null && fileURL != "" && fileURL != "undefined"){
		window.open(sourceleadUrl+'/viewFile?documentPath='+encodeURI(fileURL)+"&contentType="+contentType,title,"height=600,width=600");
	}  
}

function getFormData(formId) {
	var $form = $("#"+formId);
	var unindexed_array = $($form).serializeArray();
	var indexed_array = {};
	$.map(unindexed_array, function (n, i) {
	indexed_array[n['name']] = n['value'];
	});
	return indexed_array;
}

function getFormData1(formId) {
	var $form = $("#"+formId);
	var unindexed_array = $($form).serializeArray();
	var formData = new FormData();
	$.map(unindexed_array, function (n, i) {
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
	for(var e in files) {
		data.append('file', files[e]);
	}
	data.append('orgId', orgId);
	data.append('orgLocationId', orgLocationId);
	$.ajax({ 
		url: sourceleadUrl+(url ? url : "/upload/"+type), 
		data: data, 
		processData: false, 
		type: 'POST', 
		contentType: false, 
		success: function (data) {
			if(data && data.length > 0) {
				applyData(data, formId, name, id);
				$(".file_mask").hide();
			}
		}, 
		error: function (err) { 
			 alert(err); 
			 $(".file_mask").hide();
		} 
	});
}

function applyData(data, formId, name, id) {
	if($("#"+formId+" input[name*="+name+"][data-id="+id+"]").length > 0) {
		$("#"+formId+" input[name*="+name+"][data-id="+id+"]").remove();
	}
	var i = $("#"+formId+" input[type=hidden][name*="+name+"]").length / 3;
	
	for(var e in data) {
		var data1 = data[e];
		if(data[0].fileInfo) {
			data1 = data[e].fileInfo;
		}
		var html = "<input type='hidden' name='"+name+"["+i+"].fileURL' data-id="+id+" value='"+data1.fileURL+"'/>";
		html += "<input type='hidden' name='"+name+"["+i+"].fileName' data-id="+id+" value='"+data1.fileName+"'/>";
		html += "<input type='hidden' name='"+name+"["+i+"].contentType' data-id="+id+" value='"+data1.contentType+"'/>";
		$("#"+formId).append(html);
		i ++;
	}
	
	if(data && data.length > 0 && data[e].fileInfo) {
		for(var e in data) {
			applyResumeData(data[e]);
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
    $(".alert" + (type === 1 ? ".alert-success" : ".alert-danger")).show();
    $(".alert" + (type === 1 ? ".alert-success" : ".alert-danger")).addClass('bounceInUp animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
        $(".alert" + (type === 1 ? ".alert-success" : ".alert-danger")).removeClass('bounceInUp animated');
    });
     $(".mask").fadeIn(400);
}

function hideMessage(type) {
    $(".alert" + (type === 1 ? ".alert-success" : ".alert-danger")).addClass('bounceOutDown animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
         $(".alert" + (type === 1 ? ".alert-success" : ".alert-danger")).removeClass('bounceOutDown animated');
         $(".alert" + (type === 1 ? ".alert-success" : ".alert-danger")).hide();
    });
    $(".mask").fadeOut(400);
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
	                      	  url: sourceleadUrl+"/saveCandidateProfile?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,
	                          success: function(result) {
	                        	  if(result){
	                        		  $("img[data-id=profileImage]").each(function(i) { $(this).attr('src', data[0].fileURL); });
	                        		  cancel(candidateId);
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


function saveUserProfileImage(formId,data){
	
	if ($('#'+formId).validate(false, validationSettings)){
  		var formData = getFormData(formId);
        		
        $.ajax({
            	      type: "POST",
            	      data: formData,
            	      url: sourceleadUrl+"/saveUserProfileImage?"+tokenParamName+"="+tokenValue,
                      success: function(result) {
                    	  if(result)
                    		  $("img[data-id=userProfileImage]").each(function(i) { $(this).attr('src', data[0].fileURL); });
//                   		  location.replace(sourceleadUrl+"/userProfile");  
			          },  
        });
        return true;         
}else{ 
return false;
}
	
}

function uploadResumeOnSelect(formId) {
	$("#uploadBtn").change(function() {
		$("#uploadFile").focus();
      	 if(validate_fileupload(this.value)){
      		 var docSize = this.files[0].size;
     	      $("#uploadFileGroup").find('p.jquery_form_error_message').remove();
     	      if(docSize > 2097152){
     	    	  	document.getElementById("uploadFile").value = '';
     	    	  	$('#uploadFileGroup').find('p.jquery_form_error_message').remove();
     			$("input#uploadFile").after("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </span>");
     			document.getElementById("uploadFile").setAttribute('record-exist','yes');
     			document.getElementById("uploadFile").setAttribute('record-exist-errorMsg','Limit is exceeded than 2MB'); 
     	      }else{
	     	    	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	     	    	 $('input#uploadFile').removeAttr( "record-exist record-exist-errormsg");
	        		 $('#uploadFileGroup').find('p.jquery_form_error_message').remove(); 
        		     fileUpload("uploadBtn", "resumes", formId, null, null, null, "/parse-resumes");
     	      }
      	 }
	});
}

function applyResumeData(json) {
	var name = json.name;
	var firstName = '';
	var lastName = '';
	if(name != null && name != 'undefined' && name != ''){
		 firstName = name.split(".")[0]; 
		 lastName = name.split(".")[1]; 
		 if(firstName == null || firstName ==  'undefined' || firstName == '' ||
				 lastName ==  null || lastName ==  'undefined'|| lastName ==  ''){
			 firstName = name;
		 }
	}
	
	$("#firstName").val($("#firstName").attr('readonly') ? $("#firstName").val() : firstName);
	$("#lastName").val($("#lastName").attr('readonly') ? $("#lastName").val() : lastName);
	$("#email").val($("#email").attr('readonly') ? $("#email").val() : json.email);
	$("#mobilePhone").val($("#mobilePhone").attr('readonly') ? $("#mobilePhone").val() : json.phoneNumbers);
	if(!$("#email").attr('readonly'))
		validateCandidateEmail();
	$("#candidateSkills").empty();
	 var internalOutput = "";
	 
	var skills = json.skills;
	if(skills != null && skills != 'undefined' && skills != ''){
		var str_array = skills.split(',');
		for(var i = 0; i < str_array.length; i++) {
		   str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
		   internalOutput = internalOutput+"<option value='" +str_array[i]+ "' selected>"+str_array[i]+"</option>";
		}
	}
	$("#candidateSkills").append(internalOutput);
	$("#candidateSkills").trigger("chosen:updated");
	$("#candidateEducation").trigger("chosen:updated");
	
	$("#candidateEducation").empty();
	 var internalOutput = "";
	var edu = json.edu;
	if(edu != null && edu != 'undefined' && edu != ''){
		var str_array = edu.split(',');
		for(var i = 0; i < str_array.length; i++) {
		   str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
		   internalOutput = internalOutput+"<option value='" +str_array[i]+ "' selected>"+str_array[i]+"</option>";
		}
	}
	$("#candidateEducation").append(internalOutput);
	$("#loading").removeAttr("style");
}