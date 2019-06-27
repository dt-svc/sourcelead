

function appraisalFeedbackFileUpload(id, type, formId, name,attachmentCount,orgId, orgLocationId,url) {
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
				applyFileData(data, formId, name, id,attachmentCount);
				$(".file_mask").hide();
			}
		}, 
		error: function (err) { 
			 $(".file_mask").hide();
		} 
	});
}

function applyFileData(data, formId, name, id,attachmentCount) {
	if($("#"+formId+" input[name*='"+name+"'][data-id="+id+"]").length > 0) {
		$("#"+formId+" input[name*='"+name+"'][data-id="+id+"]").remove();
	}
	
	for(var e in data) {
		if(!$.isNumeric(e))
			continue;
		var data1 = data[e];
		if(data[0].fileInfo) {
			data1 = data[e].fileInfo;
		}
		var html = "<input type='hidden' name='"+name+"["+attachmentCount+"].fileURL' data-id="+id+" value=\""+data1.fileURL+"\"/>";
		html += "<input type='hidden' name='"+name+"["+attachmentCount+"].fileName' data-id="+id+" value=\""+data1.fileName+"\"/>";
		html += "<input type='hidden' name='"+name+"["+attachmentCount+"].status' data-id="+id+" value='0'/>";
		$("#"+formId).append(html);
	}
	
}