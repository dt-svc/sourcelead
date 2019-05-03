<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet" href="/resources/css/style.css?staticTime=${staticTime}" />
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/requirementQuestionnaireFormBuilder.js"></script>

<script>
var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
var templateUuid ='${templateUuid}';
$(document).ready(function(){
	customizeFields(sourceleadUrl,templateUuid);
});
</script>
<div class="wraper"> 
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Add Questionnaire</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home </a> &raquo; 
      <a href="${SourceLeadWeb_sourceleadUrl}/questionnaireList">Questionnaire List</a> &raquo;
      Add Questionnaire</div>
    </section>
    <div class="clearfix">
    <div class="tab-pane" >
              <div class="form-horizontal assignTemplate">
    
    <div id="sjfb-wrap">
	           <form id="sjfb" novalidate>
	           <input type="hidden" id="templateId">
     		      <div class="row mb">
		            <div class="col-xs-12 col-md-6" id="templateNameDiv">
		           <label>Name<span class="text-danger">&nbsp;*</label>
			    	<div class="mt10">
			    			<input type="text" class="form-control input-sm"  id="templateName" onchange="validateQuestionnaireName()";  name="templateName"  field-name="Name" data-validation="required validate_Space validate_length length2-100">
			    	</div>
			    	</div>
			    	
			    	<div class="col-xs-12 col-md-6">
			    	 <label>Description<span class="text-danger">&nbsp;*</label>
			    			 <textarea rows="3" id="templateDescription" name="description" field-name="Description" data-validation="required" class="mt10 form-control"></textarea>
			    	 </div>
			    	 
			    	 <div class="col-md-6">
					<div class="row">
						<div class="col-md-12">
							<div class="row">
							<label class="col-md-12 mb5">Time Limit</label>
							<div class="col-md-6">
							<input type="text" id ="minutes" class="form-control" placeholder="Minutes" data-validation="validate_Space validate_int validate_length length1-3" data-validation-optional="true">
							</div>
							<div class="col-md-6">
							<input type="text" id ="seconds" class="form-control" placeholder="Seconds" data-validation="validate_Space validate_int validate_length length1-2" data-validation-optional="true"> 
							</div>
								</div>
						</div>  
						  
					</div>
				</div>
			    	 
			    	 
			    	</div>
			    	
		    	
			    	<div class="clearfix mb mt">
			    	
			    	<div class="col-md-10">
			    	</div>
					    	<div class="col-md-2">
						        <div class="dropdown">
						        <button class="btn btn-success dropdown-toggle btn-block" type="button" data-toggle="dropdown" id="addFiled-dropdown">Add Question &nbsp;&nbsp;
						        <span class="caret"></span></button>
							        <ul id="add-field" class="dropdown-menu pull-right">
								        <li><a id="add-text" data-type="text"  href="javascript:void(0)"><span class="fa fa-text-width">&nbsp;&nbsp;</span>Text Box</a>
								        <li><a id="add-textarea" data-type="textarea" href="javascript:void(0)"><span class="fa fa-text-width">&nbsp;&nbsp;</span>Text Area</a></li>
								        <li><a id="add-select" data-type="select"   href="javascript:void(0)"><span class="fa fa-caret-down">&nbsp;&nbsp;&nbsp;</span>Select Box</a></li>
								        <li><a id="add-checkbox" data-type="checkbox"  href="javascript:void(0)"><span class="fa fa-check-square-o">&nbsp;&nbsp;</span>Check Box</a></li>
								        <li><a id="add-radio" data-type="radio" href="javascript:void(0)"><span class="fa fa-check-circle-o">&nbsp;&nbsp;</span>Radio Button</a></li>
								        <li><a id="add-comments" data-type="comments" href="javascript:void(0)"><span class="fa fa-comment-o">&nbsp;&nbsp;</span>Comments</a></li>
								        <li><a id="add-star" data-type="star" href="javascript:void(0)"><span class="fa fa-star-o">&nbsp;&nbsp;</span>Star</a></li>
								        <li><a id="add-scale" data-type="scale" href="javascript:void(0)"><span class="fa fa-sliders">&nbsp;&nbsp;</span>Scale</a></li>
								        <li><a id="add-calendar" data-type="calendar" href="javascript:void(0)"><span class="fa fa-calendar">&nbsp;&nbsp;</span>Calendar</a></li>
								        <li><a id="add-file" data-type="file" href="javascript:void(0)"><span class="fa fa-cloud-upload">&nbsp;&nbsp;</span>File Upload</a></li>
							        </ul>
						        </div>
					        </div>
			        </div>
		       
	           
	           <div id="form-fields">
	           </div>
	           <div class="form-footer clearfix mt">
	           <div class="clearfix"></div>
	           <div class="pull-right mr">
	           
	           
	           <button type="submit" class="slBtn md colorBtn" id="save_button"  onclick="">Save</button>
	           <button type="button" class="slBtn md popClose"  onClick="javascript:window.location.href = '<c:url value='${appPath}/questionnaireList'/>'"  onclick="" >Cancel</button>
	           
	           </div>

	           </div>
	           </form>
          </div>
	 </div>
	</div>   
	<div id="popUp"></div>
    <div id="choicepopUp"></div>
	
<div class="backtotop" style="display: none;"><img src="/resources/images/backtotop.png"></div>  

    
    
  </section>
</div>

<jsp:include page="../masterFooter.jsp" /> 


<script>
//Back To Top 
$(window).scroll(function() {
    if ($(this).scrollTop() > 200) {
        $('.backtotop').fadeIn('slow');
    } else {
        $('.backtotop').fadeOut('slow');
    }
});
$('.backtotop').click(function() {
    $("html, body").animate({scrollTop: 0}, 500);
    return false;
});

</script>
