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
        <h2>Employment Verfication Template</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Employment Verfication Template</div>
    </section>
<div class="content-area content-new clearfix">
      <div class="clearfix form-horizontal">
       <form:form id="employment_template_form" name="employment_template_form" method="post">
        <div class="row newContrWrap">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
           
            <div class="form-group">
              <label class="col-md-4">Name<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                <input type="text" class="form-control" name="templateName" value="">
              </div>
            </div>
            
            <div class="form-group">
              <label class="col-md-4">Signatory<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select class="form-control selectpicker" data-live-search="true" title="Select Signatory">
                    <option>Signatory-1</option>
                    <option>Signatory-2</option>
                    <option>Signatory-3</option>
                  </select>
            </div>
            </div>
             <div class="form-group">
              <label class="col-md-4">Description</label>
              <div class="col-md-8">
					<textarea class="form-control" rows="3" name="description" ></textarea>
              </div>
            </div> 
            
             <div class="form-group">
              <label class="col-md-4">Choose Signature</label>
              <div class="col-md-8">
                  <select class="form-control selectpicker" data-live-search="true" title="Select Signatory">
                    <option>Signatory-1</option>
                    <option>Signatory-2</option>
                    <option>Signatory-3</option>
                  </select>
            </div>
            
             <div class="clearfix mt10 mb10 text-center col-md-8 col-md-offset-4">--- OR ---</div>
              <label class="col-md-4">Upload Signature</label>
              <div class="col-md-8">

            <div class="clearfix">
			<div class="input-group positionRelative">
			<label class="input-group-btn">
			<span class="btn btn-primary">
			Browse<input style="display: none;" multiple type="file">
			</span>
			</label>
			<input class="form-control input-custom" readonly type="text">
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
			<textarea name="templateContent" id="summernote" ></textarea>
		</div>  
		
		
		<div class="col-md-3 contrCus allfields">
			<h3>Class Fields</h3>
            
            <ul id="allfields">
								<li onclick="copyToClipboard('text_element1')"><i></i>
									<p class="copyText" id="text_element1">[To Whom It May Concern]</p></li>
								<li onclick="copyToClipboard('text_element2')"><i></i>
									<p class="copyText" id="text_element2">[Company Name]</p></li>
								<li onclick="copyToClipboard('text_element3')"><i></i>
									<p class="copyText" id="text_element3">[Company Location]</p></li>
								<li onclick="copyToClipboard('text_element4')"><i></i>
									<p class="copyText" id="text_element4">[Company Email]</p></li>
								<li onclick="copyToClipboard('text_element5')"><i></i>
									<p class="copyText" id="text_element5">[Company Contact]</p></li>
								<li onclick="copyToClipboard('text_element6')"><i></i>
									<p class="copyText" id="text_element6">[Issued Date]</p></li>
								<li onclick="copyToClipboard('text_element7')"><i></i>
									<p class="copyText" id="text_element7">[Associate Name]</li>
								<li onclick="copyToClipboard('text_element8')"><i></i>
									<p class="copyText" id="text_element8">[Associate Designation/Role]</p></li>
								<li onclick="copyToClipboard('text_element8')"><i></i>
								<p class="copyText" id="text_element8">[Associate Employment Type]${SourceLeadWeb_orgCode}</p></li>
								<c:if test="${SourceLeadWeb_orgCode} = 'USA'"></c:if>
								<li onclick="copyToClipboard('text_element8')"><i></i>
								<p class="copyText" id="text_element8">[SSN]</p></li>
								<li onclick="copyToClipboard('text_element8')"><i></i>
								<p class="copyText" id="text_element8">[PAN]</p></li>
								<li onclick="copyToClipboard('text_element8')"><i></i>
								<p class="copyText" id="text_element8">[Pay Rate]</p></li>
								<li onclick="copyToClipboard('text_element8')"><i></i>
								<p class="copyText" id="text_element8">[Start Date]</p></li>
								<li onclick="copyToClipboard('text_element8')"><i></i>
								<p class="copyText" id="text_element8">[End date]</p></li>
								<li onclick="copyToClipboard('text_element8')"><i></i>
								<p class="copyText" id="text_element8">[Signatory Name]</p></li>
								<li onclick="copyToClipboard('text_element8')"><i></i>
								<p class="copyText" id="text_element8">[Signatory Designation]</p></li>
								<li onclick="copyToClipboard('text_element8')"><i></i>
								<p class="copyText" id="text_element8">[Signatory Email]</p></li>
								<li onclick="copyToClipboard('text_element8')"><i></i>
								<p class="copyText" id="text_element8">[Signatory Contact]</p></li>
								<li onclick="copyToClipboard('text_element8')"><i></i>
								<p class="copyText" id="text_element8">[Signature]</p></li>
							</ul>
            
            
		</div>  		      
	</div>
</div>      

      <div class="form-footer clearfix contrFoot">
        <div class="col-lg-12">
          <div class="pull-right">
            <input type="button" id="save_employment_template" value="Add" class="btn btn-primary" onclick="saveEmploymentVerificationTemplate();">
			<input type="button" value="Cancel" class="btn btn-default ml10">
          </div>
        </div>
      </div>
      
      <!-- Content Area Ends--> 
      </form:form>
    </div>
	</div>
    </section>


<script>
function saveEmploymentVerificationTemplate(){
	if ($('#employment_template_form').validate(false, validationSettings)) {
		$("#save_employment_template").addClass('button').val('Processing..');
		var formData = getFormData("employment_template_form");
		$.post(sourceleadUrl+"/saveEmploymentverificationTemplate?${_csrf.parameterName}=${_csrf.token}",formData, function(data) {
					if (data != null) {
						location.replace(sourceleadUrl+"/employmentVerificationTemplateList");
					}
				});
		return true;
	}else{
		return false;
	}
}
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
	$('#summernote').summernote();
</script> 


<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->

