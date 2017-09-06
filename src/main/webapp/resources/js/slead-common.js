function viewFile(sourceleadUrl, fileURL, title, contentType){
	if(fileURL != null && fileURL != ""){
		window.open(sourceleadUrl+'/viewFile?documentPath='+encodeURI(fileURL)+"&contentType="+contentType,title,"height=600,width=600");
	}  
}

function fileUpload(sourceleadUrl, id, type, formId) {
	var data = new FormData();
	var files = document.getElementById(id).files;
	for(var e in files) {
		data.append('file', files[e]);
	}
	$.ajax({ 
		url: sourceleadUrl+"/upload/"+type, 
		data: data, 
		processData: false, 
		type: 'POST', 
		contentType: false, 
		success: function (data) {
			if(data && data.length > 0) {
				if($("#"+formId+" input[name*=fileInfo][data-id="+id+"]").length > 0) {
					$("#"+formId+" input[name*=fileInfo][data-id="+id+"]").remove();
				}
				var i = 0;
				for(var e in data) {
					var html = "<input type='hidden' name='fileInfo["+i+"][fileURL]' data-id="+id+" value='"+data[e].fileURL+"'/>";
					html += "<input type='hidden' name='fileInfo["+i+"][fileName]' data-id="+id+" value='"+data[e].fileName+"'/>";
					html += "<input type='hidden' name='fileInfo["+i+"][contentType]' data-id="+id+" value='"+data[e].contentType+"'/>";
					$("#"+formId).append(html);
					i ++;
				}
			} else if(data && data.length === 0) {
				showError("Unable to upload the file");
			}
		}, 
		error: function (err) { 
			 showError(err); 
		} 
	});
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