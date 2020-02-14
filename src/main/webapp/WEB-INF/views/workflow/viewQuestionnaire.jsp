

<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>
 
<link rel="icon" type="image/png" href="/resources/images/fav.png" />
<title>Welcome to Source Lead :: Questionnaire Template</title>
<!-- Bootstrap -->
<link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/timesheet.css?staticTime=${staticTime}" rel="stylesheet">
<script src="/resources/js/InterViewFormBuilder.js?staticTime=${staticTime}" type="text/javascript" ></script>
<script src="/resources/js/interViewForm-html-generator.js?staticTime=${staticTime}" type="text/javWascript" ></script> 
<script src="/resources/js/jquery-1.11.1.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/bootstrap.min.js?staticTime=${staticTime}"></script> 
<script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
<link rel="stylesheet" href="/resources/css/style.css?staticTime=${staticTime}" />
<script src="/resources/js/jquery-ui.js?staticTime=${staticTime}"></script>
<link rel="stylesheet" href="/resources/css/jquery-ui.css?staticTime=${staticTime}" type="text/css" >
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/requirementQuestionnaireFormBuilder.js"></script>
</head>
 
<style type="text/css">

.choices label {
width: auto !important; padding-right: 15px;
}
.choices input[type="checkbox"], .choices input[type="radio"]{
 margin-right: 10px; position: relative; top: 1px;
}
</style>

<script type="text/javascript">
var sessionId = '${SourceLeadWeb_sessionId}';
var sourceleadUrl = null;
if(sessionId == null || sessionId == "null" || sessionId == "" || sessionId == "undefined"){
	sourceleadUrl = '${sourceleadUrl}'+'/ws';
}else{
	sourceleadUrl = '${sourceleadUrl}';
}

var templateUuid = '${templateUuid}';

$(document).ready(function(){
	viewQuestionnaireTemplateForm();
 });
 
 $('body').on('blur', '#questionnaireTemplate_form', function() {
		$("#questionnaireTemplate_form").showHelpOnFocus();
		$("#questionnaireTemplate_form").validateOnBlur(false, validationSettings);
	});
 
 var validationSettings = {
         errorMessagePosition : 'element',
         borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         };
 
 (function($) {
		$.fn.serializeFormJSON = function() {
			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};
	})(jQuery);
</script>
<div class="wraper"> 
<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>View Questionnaire</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home </a> &raquo; 
      <a href="${SourceLeadWeb_sourceleadUrl}/questionnaireList">Questionnaire List</a> &raquo;
      View Questionnaire</div>
    </section>

<div class="clearfix"> 
   <div id="popUp"></div> 
   <div id="viewQuestionnaireTemplateFormDiv" class="container container-wraper container-publicFBT positionRelative paddingTop55" style="display: none;"> </div> 
   
</div>
</section>
</div>


<jsp:include page="../masterFooter.jsp" /> 