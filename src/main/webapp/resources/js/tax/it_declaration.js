/**
 * @author Mahaveer Manoj
 */
var uploadCount = 1;
$(document).ready(function() {
	$("#employeeSelect").change(function() {
		$("#associateName").val($("#employeeSelect :selected").data("name"));
		$("#associateId").val($(this).val());
		$("#associatePAN").val("");
	});
	$("#saveBtn, #submitBtn").click(function() {
		if ($('#itDeclarationForm').validate(false, validationSettings)) {
			var flag = true;
			if(flag) {
				$("#saveBtn").attr("disabled","disabled");
				$("#submitBtn").attr("disabled","disabled");
				$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
				var formData = getFormData("itDeclarationForm");
				$.ajax({
					type : "POST",
	        	    data: formData,
					url : sourceleadUrl+"/itDeclaration/save",
					success : function(response) {
						if (response.response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (response.response === "SUCCESS") {
			        		if (response.object > 0) {
			        			$(".success_pop").css("display","block");
				        		$(".success_pop span").html("ITDeclaration added successfully....");
				        		setTimeout(function() {
				        			location.href = sourceleadUrl + "/forms/list";					 			
						 		},100);
			        		}
			        	} else if (response.response === "INVALID_DATA") {
			        		showError(response.responseMessage);
			        	} else if (response.indexOf('Sign In') !== -1) {
			        		location.replace(sourceleadUrl);
			        	} else {
			        	    showError("Operation failed, please try again");
			        	}
						$("#saveBtn").removeAttr("disabled","disabled");
						$("#submitBtn").removeAttr("disabled","disabled");
						$("#loading").removeAttr("style");
					},
				});	
			}
			return true;
		} else
			return false;
	});
});
function fileUploadEvent(ele) {
	var id = $(ele).attr("id");
	var filesSize = document.getElementById(id).files.length;
	$(ele).parent().parent().next().val(filesSize > 1 ? (filesSize + " files selected") : $(ele).val());

	enableDisableRefresh(ele);
}
function enableDisableRefresh(ele) {
	var flag = true;
	if ($(ele).attr("type") === "file") {
		if (document.getElementById($(ele).attr("id")).files.length === 0)
			flag = false;
	} else {
		if ($(ele).val() === "")
			flag = false;
	}
	if (flag)
		$(ele).parents("tr").find(".fa-refresh").removeClass("disableBtn");
	else
		$(ele).parents("tr").find(".fa-refresh").addClass("disableBtn");
}
function addDocumentRow() {
	var row = "<tr><td><input type='text' class='form-control input-sm' placeholder='Enter Document Name' field-name='Document Name' data-validation='required' onchange='enableDisableRefresh(this)' onkeyup='enableDisableRefresh(this)'></td>";
	row += "<td>";
	row += "<div class='clearfix attchments'>";
	row += "<div class='input-group'>";
	row += "<label class='input-group-btn'>";
	row += "<span class='btn btn-primary'>Browse File ";
	row += "<input style='visibility: hidden; height: 0; width: 10px; overflow: hidden' multiple='multiple' id='uploadBtn"
			+ (++uploadCount)
			+ "' class='upload' type='file' onchange='fileUploadEvent(this)'>";
	row += "</span>";
	row += "</label>";
	row += "<input class='form-control' readonly='readonly' id='uploadFile' placeholder='Select File(s)' type='text' field-name='Document File' data-validation='required'>";
	row += "</div>";
	row += "</div>";

	row += "</td>";
	row += "<td>";
	row += "<div class='tblminwidth-75 text-center'>";
	row += "<i class='fa fa-trash text-danger mr cursorPointer' onclick='removeDocumentRow(this)'></i>";
	row += "<i class='fa fa-refresh cursorPointer disableBtn' onclick='refreshDocumentRow(this)'></i>";
	row += "</div>";
	row += "</td></tr>";
	$("#documentsTable").append(row);
}

function removeDocumentRow(ele) {
	$(ele).parents("tr").remove();
	uploadCount = 1;
	$(".upload").each(function() {
		$(this).attr("id", "uploadBtn" + (uploadCount++));
		document.getElementById($(this).attr("id")).value = "";
	});
}

function refreshDocumentRow(ele) {
	$(ele).parents("tr").find("input").val("");
	$(ele).parents("tr").find("p").remove();
	$(ele).parents("tr").find(".fa-refresh").addClass("disableBtn");
}