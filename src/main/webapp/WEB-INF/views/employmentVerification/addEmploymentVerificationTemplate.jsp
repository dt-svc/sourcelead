<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<script src="/resources/js/summernote.js?staticTime=${staticTime}"></script>
<link href="/resources/css/summernote.css?staticTime=${staticTime}"
	rel="stylesheet" />


<!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Employment Verification Template</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${SourceLeadWeb_sourceleadUrl}/orgTemplates"> Templates</a> &raquo; Employment Verification Template</div>
    </section>
<div class="content-area content-new clearfix">
      <div class="clearfix form-horizontal">
       <form:form id="employment_template_form" name="employment_template_form" method="post">
        <div class="newContrWrap">
          <div class="col-xs-12 col-sm-6 col-md-6">
            <div class="form-group">
              <label class="col-md-4">Name<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                <input type="text" class="form-control" name="templateName" field-name="Name" value="${empTemplate.templateName}" data-validation="required validate_length length1-250">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-4">Choose Letterhead<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select class="form-control selectpicker" data-live-search="true" title="Select Letterhead" name="letterHead" field-name="Letterhead" data-validation="required">
                    <c:forEach var="letter" items="${letterHeadList}">
                    <c:if test = "${letter.id == empTemplate.letterHead.id}">
				         <option selected value="${letter.letterHeadUUID}">${letter.letterHeadName}</option>
				    </c:if>
				    <c:if test = "${letter.id != empTemplate.letterHead.id}">
				         <option value="${letter.letterHeadUUID}">${letter.letterHeadName}</option>
				         </c:if>
                    </c:forEach>
                  </select>
            </div>
            </div>
                         <div class="form-group">
              <label class="col-md-4">Description</label>
              <div class="col-md-8">
					<textarea class="form-control" rows="3" name="description">${empTemplate.description}</textarea>
              </div>
            </div> 
        </div>
        <div class="col-xs-12 col-sm-6 col-md-6">
                    <div class="form-group">
              <label class="col-md-4">Signatory<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select class="form-control selectpicker" data-live-search="true" title="Select Signatory" name="signatory" field-name="Signatory" data-validation="required">
                    <c:forEach var="title" items="${signatoryList}">
					<c:if test = "${title.associateId == empTemplate.signatory.id}">
				         <option selected value="${title.associateId}">${title.firstName} ${title.lastName}</option>
				    </c:if>
				    <c:if test = "${title.associateId != empTemplate.signatory.id}">
				         <option value="${title.associateId}">${title.firstName} ${title.lastName}</option>
				    </c:if>
                     </c:forEach>   
                  </select>
            </div>
            </div>
        
                     <div class="form-group">
              <label class="col-md-4">Choose Signature Style</label>
              <div class="col-md-8">
                  <select class="form-control selectpicker" data-live-search="true">
                    <option>Style-1</option>
                    <option>Style-2</option>
                    <option>Style-3</option>
                  </select>
            </div>
             <div class="clearfix mt10 mb10 text-center col-md-8 col-md-offset-4">--- OR ---</div>
              <label class="col-md-4">Upload Signature</label>
              <div class="col-md-8">

            <div class="clearfix">
			<div class="input-group positionRelative">
			<label class="input-group-btn">
			<span class="btn btn-primary">Browse<input id="uploadBtn" style="display: none;" multiple type="file">
			</span>
			</label>
			<input type="text" class="form-control" readonly id="imageFile" name="imageFile" value="${empTemplate.fileName}" placeholder="Upload Sign Image."  data-validation-optional="true" data-validation="validate_Space validateimage">
			<i class="refreshinputfile"></i>
			</div>

			</div>
           </div>
            </div>
        </div>
      </div>
      
<div class="row newContrWrap">
	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
		<div class="col-md-9">
			<textarea name="templateContent" id="summernote">${empTemplate.templateContent}</textarea>
		</div>  
		
		
		<div class="col-md-3 contrCus allfields">
			<h3>Class Fields</h3>
            
            <ul id="allfields">
								<li onclick="copyToClipboard('text_element1')"><i></i>
									<p class="copyText" id="text_element1">[To_Whom_It_May_Concern]</p></li>
								<li onclick="copyToClipboard('text_element2')"><i></i>
									<p class="copyText" id="text_element2">[Company_Name]</p></li>
								<li onclick="copyToClipboard('text_element3')"><i></i>
									<p class="copyText" id="text_element3">[Company_Location]</p></li>
								<li onclick="copyToClipboard('text_element4')"><i></i>
									<p class="copyText" id="text_element4">[Company_Email]</p></li>
								<li onclick="copyToClipboard('text_element5')"><i></i>
									<p class="copyText" id="text_element5">[Company_Contact]</p></li>
								<li onclick="copyToClipboard('text_element6')"><i></i>
									<p class="copyText" id="text_element6">[Issued_Date]</p></li>
								<li onclick="copyToClipboard('text_element7')"><i></i>
									<p class="copyText" id="text_element7">[Associate_Name]</li>
								<li onclick="copyToClipboard('text_element8')"><i></i>
									<p class="copyText" id="text_element8">[Associate_Designation_Role]</p></li>
								<li onclick="copyToClipboard('text_element9')"><i></i>
									<p class="copyText" id="text_element9">[Associate_Employment_Type]</p></li>
								<c:choose>
								<c:when test="${SourceLeadWeb_currencyCode=='USD'}">
								<li onclick="copyToClipboard('text_element10')"><i></i>
								<p class="copyText" id="text_element10">[SSN]</p></li>
								</c:when>
								<c:otherwise>
								<li onclick="copyToClipboard('text_element10')"><i></i>
								<p class="copyText" id="text_element10">[PAN]</p></li>
								</c:otherwise>
								</c:choose>
								<li onclick="copyToClipboard('text_element11')"><i></i>
								<p class="copyText" id="text_element11">[Pay_Rate]</p></li>
								<li onclick="copyToClipboard('text_element12')"><i></i>
								<p class="copyText" id="text_element12">[Start_Date]</p></li>
								<li onclick="copyToClipboard('text_element13')"><i></i>
								<p class="copyText" id="text_element13">[End_Date]</p></li>
								<li onclick="copyToClipboard('text_element14')"><i></i>
								<p class="copyText" id="text_element14">[Signatory_Name]</p></li>
								<li onclick="copyToClipboard('text_element15')"><i></i>
								<p class="copyText" id="text_element15">[Signatory_Designation]</p></li>
								<li onclick="copyToClipboard('text_element16')"><i></i>
								<p class="copyText" id="text_element16">[Signatory_Email]</p></li>
								<li onclick="copyToClipboard('text_element17')"><i></i>
								<p class="copyText" id="text_element17">[Signatory_Contact]</p></li>
								<li onclick="copyToClipboard('text_element18')"><i></i>
								<p class="copyText" id="text_element18">[Signature]</p></li>
							</ul>
            
            
		</div>  		      
	</div>
</div>      

      <div class="form-footer clearfix contrFoot">
        <div class="col-lg-12">
          <div class="pull-right">
            <input type="button" id="save_employment_template" value="Add" class="btn btn-primary" onclick="saveEmploymentVerificationTemplate();">
			<input type="button" value="Cancel" class="btn btn-default ml10" onClick="javascript:window.location.href = '${SourceLeadWeb_sourceleadUrl}/employmentVerificationTemplateList'">
          </div>
        </div>
      </div>
      
      <!-- Content Area Ends--> 
      </form:form>
    </div>
	</div>
    </section>


<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };

        
</script> 

<script>
function saveEmploymentVerificationTemplate(){
	var templateUUID = '${templateUUID}';
	if ($('#employment_template_form').validate(false, validationSettings)) {
		$("#save_employment_template").addClass('button').val('Processing..');
		var formData = getFormData("employment_template_form");
		$.post(sourceleadUrl+"/saveEmploymentverificationTemplate?${_csrf.parameterName}=${_csrf.token}&templateUUID="+templateUUID,formData, function(data) {
					if (data != null) {
						location.replace(sourceleadUrl+"/employmentVerificationTemplateList");
					}
				});
		return true;
	}else{
		return false;
	}
}

$('body').on('blur', '#employment_template_form', function() {
	$("#employment_template_form").showHelpOnFocus();
	$("#employment_template_form").validateOnBlur(false, validationSettings);
});
</script>

<script>
	function copyToClipboard(elementId) {
		var aux = document.createElement("input");
		aux.setAttribute("value", "");
		aux.setAttribute("value", document.getElementById(elementId).innerHTML);
		document.body.appendChild(aux);
		aux.select();
		document.execCommand("copy");
		document.body.removeChild(aux);
	}
	function log() {
		console.log('---')
	}
</script>


<script>
$(function() {
	$("#uploadBtn").change(function() {
		fileUpload("uploadBtn", "EmploymentVerifyDocs", "employment_template_form");
	});
	
	 document.getElementById("uploadBtn").onchange = function () {
 	    document.getElementById("imageFile").value = this.value.split( '\\' ).pop();
 	    };
	
 });
</script>

<script>
	$('#summernote').summernote();
</script> 


<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->

