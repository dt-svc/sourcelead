function viewFile(sourceleadUrl, fileURL, title, contentType){
	if(fileURL != null && fileURL != ""){
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


function fileUpload(sourceleadUrl, id, type, formId, name, orgId, orgLocationId) {
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
		url: sourceleadUrl+"/upload/"+type, 
		data: data, 
		processData: false, 
		type: 'POST', 
		contentType: false, 
		success: function (data) {
			if(data && data.length > 0) {
				applyData(data, formId, name);
			}
		}, 
		error: function (err) { 
			 showError(err); 
		} 
	});
}

function applyData(data, formId, name) {
	if($("#"+formId+" input[name*="+name+"][data-id="+id+"]").length > 0) {
		$("#"+formId+" input[name*="+name+"][data-id="+id+"]").remove();
	}
	var i = $("#"+formId+" input[type=hidden][name*="+name+"]").length / 3;
	for(var e in data) {
		var html = "<input type='hidden' name='"+name+"["+i+"].fileURL' data-id="+id+" value='"+data[e].fileURL+"'/>";
		html += "<input type='hidden' name='"+name+"["+i+"].fileName' data-id="+id+" value='"+data[e].fileName+"'/>";
		html += "<input type='hidden' name='"+name+"["+i+"].contentType' data-id="+id+" value='"+data[e].contentType+"'/>";
		$("#"+formId).append(html);
		i ++;
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

function saveCandidateImage(candidateId, formId){
	if ($('#'+formId).validate(false, validationSettings)){
          		var formData = getFormData(formId);
	            		
	            $.ajax({
	                	      type: "POST",
	                	      data: formData,
	                      	  url: sourceleadUrl+"/saveCandidateProfile?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,
	                          success: function(result) {
	                        	  cancel(candidateId);
	    			          },  
	            });
	            return true;         
	}else{ 
		return false;
	 } 
}