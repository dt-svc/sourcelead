<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="/resources/css/summernote-new.css" rel="stylesheet" type="text/css">
<script src="/resources/js/summernote.min.js"></script>
<script>
$(function() {
	$('#summernote').summernote();
    $('.note-editable').find('p').remove();
    $('.note-editable').attr("data-validation","required"); 
 	$('.note-editor').css({"min-height":"200px"});
	$('.note-editable').css({"min-height":"200px", "max-height":"200px"});
});
</script>
<script>
function validateReleaseNoteName(releaseUUID){
	var ReleaseNoteName = document.getElementById("releaseNoteName").value.trim();
	if (ReleaseNoteName != null && ReleaseNoteName != "") {
		$.ajax({
					type : "POST",
					url : sourceleadUrl+ "/validateReleaseNoteName?ReleaseNoteName="+ReleaseNoteName+"&releaseUUID="+releaseUUID,
					success : function(data) {
						 if (data == true) {
							$("input#releaseNoteName").after("<p class='jquery_form_error_message'>"+ ReleaseNoteName+ " is Already Existed</span>");
							document.getElementById("releaseNoteName").setAttribute('record-exist', 'yes');
							document.getElementById("releaseNoteName").setAttribute('record-exist-errorMsg',ReleaseNoteName+ ' is Already Existed');
							$('input#releaseNoteName').removeAttr( "record-exist record-exist-errorMsg");
							$("#save_release").attr("disabled", "disabled");
						  }else {
							$("#save_release").removeAttr("disabled");
							$('input#releaseNoteName').removeAttr("record-exist record-exist-errormsg");
						} 
					},
				});
	}

}



</script>



<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left"><h2>Release Note</h2></div>
      <div class="pull-right brud-crum"><a href="<c:url value='/app/dashboard'/>"> Home </a> &raquo; <a href="<c:url value='/app/releaseNote'/>">Release Note</a></div>
    </section>
    
    <div class="project_wrap">
    <form:form id="releaseNote_form" name="releaseNote_form" method="post">
    <input type="hidden" name="releaseUUID" value="${releaseNote.releaseUUID}">
	 <div class="clearfix ">
	    <div class="form-horizontal mt myForm">
	      <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt"> 
	        <div class="form-group">
	          <label class="col-md-3">Name<span class="text-danger paddingLeft">&nbsp;*</span></label>
	          <div class="col-md-9">
	            <input type="text" class="form-control" name="releaseName" placeholder="Name" id="releaseNoteName" value="${releaseNote.releaseName}" data-validation="required validate_length length2-100" field-name="Name" onblur="validateReleaseNoteName('${releaseNote.releaseUUID}');">
	          </div>
	        </div>
	        <div class="form-group">
	          <label class="col-md-3">Release Date<span class="text-danger paddingLeft">&nbsp;*</span></label>
	          <div class="col-md-9" id="releaseErr">
	            <input type="text" class="form-control calendar"  placeholder="Release Date" readonly value="${releaseNote.releaseDateStr}" id="releaseDate" name="releaseDate" data-validation="required" field-name="Release Date">
	          </div>
	        </div>
	                <div class="form-group">
	          <label class="col-md-3">Publish Date<span class="text-danger paddingLeft">&nbsp;*</span></label>
	          <div class="col-md-9" id="publishErr">
	            <input type="text" class="form-control calendar" placeholder="Publish Date" readonly value="${releaseNote.publishDateStr}" id="publishDate" name="publishDate" data-validation="required" field-name="Publish Date">
	          </div>
	        </div>
	        <div class="form-group">
	          <label class="col-md-3">Short Description<span class="text-danger paddingLeft">&nbsp;*</span></label>
	          <div class="col-md-9">
	            <textarea placeholder="Short Description" class="form-control" cols="2" value="${releaseNote.shortDescrption}" id="shortDescription" name="releaseShortDescription" data-validation="required" field-name="Short Description">${releaseNote.shortDescrption}</textarea>
	          </div>
	        </div>   
	        <div class="form-group">
	          <label class="col-md-3">Note</label>
	          <div class="col-md-9">
	            <input type="text" class="form-control" name="releaseNotes" placeholder="Note" value="${releaseNote.releaseNotes}" data-validation="validate_length length2-100" field-name="Note" data-validation-optional="true">
	          </div>
	        </div>
	            
	        </div>
	        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
	            <label class="col-md-12">Description</label>
	            <div class="col-md-12">
	                <textarea placeholder="Description" class="form-control" cols="2" id="summernote"  name="releaseDescrption">${releaseNote.releaseDescrption}</textarea>
	            </div>
	        </div>
	    </div>
	    
	    </div>
	    
	    <div class="footerstyelsNew clearfix">
	        <div class="pull-right mr">
	          <input value="Save" id="save_release" class="btn btn-primary" type="button" onclick="saveReleaseNote();">
	          <input value="Cancel" class="btn btn-default ml" type="button" onClick="javascript:window.location.href = '<c:url value='${appPath}/releaseNote'/>'">
	        </div>
	    </div>
	    </form:form>            
	    </div>
	  </section>

<script>
var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
var finalDateFormatProjectSave = '';
if(locationDate.match("yyyy-MM-dd")){
	  finalDateFormatProjectSave = 'yy-mm-dd';
}if(locationDate.match("dd-MM-yyyy")){
	  finalDateFormatProjectSave = 'dd-mm-yy'; 
}if(locationDate.match("MM-dd-yyyy")){
	  finalDateFormatProjectSave = 'mm-dd-yy'; 
}if(locationDate.match("yyyy-dd-MM")){
	  finalDateFormatProjectSave = 'yy-dd-mm'; 
}
$('#releaseDate').datepicker({
	dateFormat : finalDateFormatProjectSave,
	changeMonth: true,
	changeYear: true, 
	onSelect : function(selected) {
		releaseErr();
	}
});

$('#publishDate').datepicker({
	dateFormat : finalDateFormatProjectSave,
	changeMonth: true,
	changeYear: true, 
	onSelect : function(selected) {
		publishErr();
	}
});
function releaseErr(){
	$('#releaseErr').find('p.jquery_form_error_message').remove(); 
}
function publishErr(){
	$('#publishErr').find('p.jquery_form_error_message').remove(); 
}
</script>
<script>
function saveReleaseNote(){
	var ee = $("#summernote").val($('.note-editable').html()); 
	if ($('#releaseNote_form').validate(false, validationSettings)) {
		var sourceleadUrl = '${sourceleadUrl}';
		var formData = getFormData("releaseNote_form");
	 	$.ajax({
			type: "POST",
			data: formData,
			url : sourceleadUrl+ "/storeReleaseNote?${_csrf.parameterName}=${_csrf.token}",
			success : function(data) {
				if (data && (data.response === "INVALID_DATA" || data.response === "INVALID_USER")) {
		        		showError(data.responseMessage);
  	    		}else if (data.response === "AWKWARD") {
            			location.href = "/errorPage";
  	    		}else if(data.response === "SUCCESS"){
        				 location.replace(sourceleadUrl+ "/releaseNote");
		        }else if (data.indexOf('Sign In') !== -1) {
            			location.replace(sourceleadUrl);
  				}else {
  					showError("Operation failed, please try again");
  				}
			},
		}); 
		return true;
	}else {
		return false;
	}
}
$('body').on('blur', '#releaseNote_form', function() {
	$("#releaseNote_form").showHelpOnFocus();
	$("#releaseNote_form").validateOnBlur(false, validationSettings);
});
</script>

<jsp:include page="../masterFooter.jsp" />