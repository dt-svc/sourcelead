<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

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
<script src="/resources/js/requirementQuestionnaireTemplate.js?staticTime=${staticTime}"></script>
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
var workflowStageUuid = '${workflowStageUuid}';
var requirementSubmissionId = '${requirementSubmissionId}';
var autoSubmission = '${auto}';
var manualSubmission = '${manual}';

window.onload = function () {
	qt.questionnaireTemplateForm();
 };
 
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
<div id ="popUp"></div>
<div id = "timeOutPopUp"></div>


<body>
<div class="clearfix"> 
  <!-- Header Area -->
  <header class="main-header">
   <span class="logo">
   	 	<a href="javascript:void(0)" title="Source Lead"> <i> <img src="/resources/images/SourceLead-logo.png" alt="Source Lead" class="img-responsive mt5"> </i>
    </a>
    </span>
    <nav class="header-nav navbar">
    </nav>
   </header>
   <div id="popUp"></div> 
   <div id="questionnaireTemplateFormDiv" class="container container-wraper container-publicFBT positionRelative paddingTop55" style="display: none;"> </div> 
   <div id="cancelFeedbackDiv" class="container paddingTop55 container-wraper container-publicFBT positionRelative" style="display: none;"> 
   <p><h3 class="col-md-5">
   You can submit the details later. Thank You!
   </h3></p>
   </div>
   
   
   <footer class="clearfix stickyfooterDiv">
   <div class="container">
    <div class="pull-left"> @ Copyright : Source Lead, All right reserved </div>
    <div class="pull-right"> Powered by: businessintelli.com </div>
    </div>
  </footer>
</div>
<script>
function saveQuestionnaireTemplate(){
	if ($('#questionnaireTemplate_form').validate(false, validationSettings)){ 
	      var frm = $('#questionnaireTemplate_form').serializeFormJSON();
		var qTemplateUuid = '${templateUuid}'; 
	      var form = $('#questionnaireTemplate_form')[0];
		    var formData = new FormData(form); 
		    
	        var con = JSON.stringify(frm);
	        con = con.replace(/[{}]/g, "");
	        //var jsonData = encodeURIComponent(con);
	        var isRemoved = '';
	        //Loop through fields and save field data to array
	        var fields = [];
	        var submitTemplate = true;
	        var fieldLabel = '';
	        var fieldhint = '';
	        var fieldDropDown = '';
	        var fileCount = 0;
	        var points = 0;
	        var attempts = 0;
	        var pointsArray = new Array();
	        var totalPointsArray = new Array();
	        var totalPoints = 0;
	        var submittedPoints = 0;
	        $('.sjfb-field').each(function() {
		    	  var $this = $(this); 
		    	  var divId = $(this).attr('id');  
		    	  var fieldLabel = $this.find('label:first').text().replace("*","").trim();
		    	  var inputValue = $this.find('input[type="radio"]:checked').val();
		    	  var inputtype = null;
		    	  var flag = 1;
		    	  
		    	  points = $('#pointId').html().trim();
		    	 
		    	  
		    	  inputtype = $(this).find('input').attr('field-type');
  			  if(inputtype != null || inputtype != '' || inputtype != 'undefined'){
  				  inputtype = $(this).find('input').attr('field-type');
  			  }
  			  
  			  if(inputtype == null || inputtype == '' || inputtype == 'undefined'){
  				  inputtype = $(this).find('input').attr('type');
  		    	  if(inputtype == null || inputtype == '' || inputtype == 'undefined'){
  		    		  if($(this).find('select').val() != null){
  		    			  inputtype = 'select';
  		    		  }else{
  		    			  inputtype = 'textarea';
  		    		  }
  		    	  }
  			  }
		    	 
		    	  
		    	  
		    	 if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
		  		   inputValue = $this.find('input[type="checkbox"]:checked').val();
		  		   if(inputValue != null){
		  			   var selected = [];
		             	$this.find('input[type="checkbox"]:checked').each(function() {
		    		        selected.push($(this).val()); 
		    		       });
		             	inputValue = selected.toString()
		  		   }
		  		   
		    	 }
		    	 
		    	
		    	  if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
		    		  inputValue = $this.find('input').val();
		    	  }
		    	  
		  	  
		   	      if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
		   	    	 inputValue = $this.find('select').val();
		   	      }
		   	      
		   	     if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
		   	    	 inputValue = $this.find('textarea').val();
		   	      }
		   	      
		   	    
		   	     
		   	      if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
		   	    	inputValue = '';
		   	      }
		   	      
		       	  var name = $this.find('input[type="radio"]:checked').attr('name');
		       	  var id = $this.find('input[type="radio"]:checked').attr('id');
		     	  if(name == null || name == '' || name == 'undefined'){
		     		  name = $this.find('input').attr('name');
		     		  id = $this.find('input').attr('id');
		     	  }
		     	  if(name == null || name == '' || name == 'undefined'){
		     		 name = $this.find('select').attr('name');
		     		 id = $this.find('select').attr('id');
	   	          }
		     	  
		     	  if(name == null || name == '' || name == 'undefined'){
		     		 name = $this.find('textarea').attr('name');
	     	      }
		     	  
		     	  if(inputtype == 'file'){
		     		 inputValue = $("input[name = 'uploadedFileInfo["+fileCount+"].fileURL']").val()
		     		 name = $("input[name = 'uploadedFileInfo["+fileCount+"].fileName']").val();
		     		 fileCount = fileCount+1;
		     	  }
		     	 totalPointsArray.push(points);
		     	  if(labelAndAttemptsMap.get(fieldLabel) != null){
		     		 attempts = labelAndAttemptsMap.get(fieldLabel);
		     	 	if(inputValue != null && inputValue != ""){
			     		 attempts = labelAndAttemptsMap.get(fieldLabel)-1;
			     		 if(attempts <= 0){
			     			attempts = 0;
			     		 }
			     		pointsArray.push(points);
			     		
			     	  }
		     	  }else{
		     		 attempts = $('#attemptId').html().trim();
			     	  if(inputValue != null && inputValue != ""){
			     		 attempts = attempts-1;
			     		 if(attempts <= 0){
			     			 attempts = 0;
			     		 }
			     		pointsArray.push(points);
			     		
			     	  }	
		     	  } 
		     	 
		     		fields.push({
			              label: fieldLabel,
			              type : inputtype,
			              id :id,
			              name :name,
			              value: inputValue,
			              attempts : attempts,
			              points : points
			          });  
		    	
		     });
	       
	        submissionType.set("submissionType", manualSubmission);
	        submittedPoints = qt.calculateTotalPoints(pointsArray);
	        totalPoints = qt.calculateTotalPoints(totalPointsArray);
	        var con2 = JSON.stringify(fields);
	        var jsonStringData = encodeURIComponent(con2);
	        if(submitTemplate){
	         $( "#template_save_button" ).addClass('button').val('Processing..');
	      	  $.ajax({
	      	    	type: "POST",
	      	    	data: formData,
			        contentType : 'application/json; charset=utf-8',
			        enctype: 'multipart/form-data',
			        processData: false,
			        contentType:false,
	      	    	dataType : 'json',
	      	        url: sourceleadUrl+"/saveQuestionnaireFeedbackTemplate?jsonStringData="+jsonStringData+"&questionnaireTemplateUuid="+qTemplateUuid+"&uuid="+savedQuestionnaireUuid.get("savedQuestionnaireUuid")+"&submittedPoints="+submittedPoints+"&totalPoints="+totalPoints+"&submissionsCount="+submissionsCount
	      	      			+"&workflowStageUuid="+workflowStageUuid+"&requirementSubmissionId="+requirementSubmissionId+"&submissionType="+submissionType.get("submissionType"), 
	      	        success: function(result) {
	      	           if (result == null) {
			        	    location.href = "/errorPage";
			        	} else if (result != null) {
			        		submissionsCount++;
			        		qt.generateQuestionnaireFeedbackTemplateForm(result, qTemplateUuid, workflowStageUuid, requirementSubmissionId);
			        		$(".mask, #modalTasktype").fadeOut();
			        	} else {
			        	    showError("Operation failed, please try again");
			        	}
	      	        },
	      	    });   
	              return true;
	        }else{
	        	alertMessage("Provide choice for "+fieldLabel)
	        }
	    }
	    else{
	    	return false;	
	       } 
}

function saveAutoSubmitQuestionnaireForm(){
	if ($('#questionnaireTemplate_form').validate(false, validationSettings)){ 
	      var frm = $('#questionnaireTemplate_form').serializeFormJSON();
		var qTemplateUuid = '${templateUuid}'; 
	      var form = $('#questionnaireTemplate_form')[0];
		    var formData = new FormData(form); 
		    
	        var con = JSON.stringify(frm);
	        con = con.replace(/[{}]/g, "");
	        //var jsonData = encodeURIComponent(con);
	        var isRemoved = '';
	        //Loop through fields and save field data to array
	        var fields = [];
	        var submitTemplate = true;
	        var fieldLabel = '';
	        var fieldhint = '';
	        var fieldDropDown = '';
	        var fileCount = 0;
	        var points = 0;
	        var attempts = 0;
	        var pointsArray = new Array();
	        var totalPointsArray = new Array();
	        var totalPoints = 0;
	        var submittedPoints = 0;
	        var myArray = new Array();
	        $('.sjfb-field').each(function() {
		    	  var $this = $(this); 
		    	  var divId = $(this).attr('id');  
		    	  var fieldLabel = $this.find('label:first').text().replace("*","").trim();
		    	  var inputValue = $this.find('input[type="radio"]:checked').val();
		    	  var inputtype = null;
		    	  var flag = 1;
		    	  
		    	  points = $('#pointId').html().trim();
		    	 
		    	  
		    	  inputtype = $(this).find('input').attr('field-type');
  			  if(inputtype != null || inputtype != '' || inputtype != 'undefined'){
  				  inputtype = $(this).find('input').attr('field-type');
  			  }
  			  
  			  if(inputtype == null || inputtype == '' || inputtype == 'undefined'){
  				  inputtype = $(this).find('input').attr('type');
  		    	  if(inputtype == null || inputtype == '' || inputtype == 'undefined'){
  		    		  if($(this).find('select').val() != null){
  		    			  inputtype = 'select';
  		    		  }else{
  		    			  inputtype = 'textarea';
  		    		  }
  		    	  }
  			  }
		    	 
		    	  
		    	  
		    	 if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
		  		   inputValue = $this.find('input[type="checkbox"]:checked').val();
		  		   if(inputValue != null){
		  			   var selected = [];
		             	$this.find('input[type="checkbox"]:checked').each(function() {
		    		        selected.push($(this).val()); 
		    		       });
		             	inputValue = selected.toString()
		  		   }
		  		   
		    	 }
		    	 
		    	
		    	  if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
		    		  inputValue = $this.find('input').val();
		    	  }
		    	  
		  	  
		   	      if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
		   	    	 inputValue = $this.find('select').val();
		   	      }
		   	      
		   	     if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
		   	    	 inputValue = $this.find('textarea').val();
		   	      }
		   	      
		   	    
		   	     
		   	      if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
		   	    	inputValue = '';
		   	      }
		   	      
		       	  var name = $this.find('input[type="radio"]:checked').attr('name');
		       	  var id = $this.find('input[type="radio"]:checked').attr('id');
		     	  if(name == null || name == '' || name == 'undefined'){
		     		  name = $this.find('input').attr('name');
		     		  id = $this.find('input').attr('id');
		     	  }
		     	  if(name == null || name == '' || name == 'undefined'){
		     		 name = $this.find('select').attr('name');
		     		 id = $this.find('select').attr('id');
	   	          }
		     	  
		     	  if(name == null || name == '' || name == 'undefined'){
		     		 name = $this.find('textarea').attr('name');
	     	      }
		     	  
		     	  if(inputtype == 'file'){
		     		 inputValue = $("input[name = 'uploadedFileInfo["+fileCount+"].fileURL']").val()
		     		 name = $("input[name = 'uploadedFileInfo["+fileCount+"].fileName']").val();
		     		 fileCount = fileCount+1;
		     	  }
		     	 totalPointsArray.push(points);
		     	  if(labelAndAttemptsMap.get(fieldLabel) != null){
		     		 attempts = labelAndAttemptsMap.get(fieldLabel);
		     	 	if(inputValue != null && inputValue != ""){
			     		pointsArray.push(points);
			     	  }
		     	  }else{
		     		 attempts = $('#attemptId').html().trim();
			     	  if(inputValue != null && inputValue != ""){
			     		pointsArray.push(points);
			     	  }	
		     	  } 
		     	 
		     		fields.push({
			              label: fieldLabel,
			              type : inputtype,
			              id :id,
			              name :name,
			              value: inputValue,
			              attempts : attempts,
			              points : points
			          });  
		    	
		     });
	       
	        submissionType.set("submissionType", autoSubmission);
	        submittedPoints = qt.calculateTotalPoints(pointsArray);
	        totalPoints = qt.calculateTotalPoints(totalPointsArray);
	        var con2 = JSON.stringify(fields);
	        var jsonStringData = encodeURIComponent(con2);
	        if(submitTemplate){
	         $( "#template_save_button" ).addClass('button').val('Processing..');
	      	  $.ajax({
	      	    	type: "POST",
	      	    	data: formData,
			        contentType : 'application/json; charset=utf-8',
			        enctype: 'multipart/form-data',
			        processData: false,
			        contentType:false,
	      	    	dataType : 'json',
	      	        url: sourceleadUrl+"/saveQuestionnaireFeedbackTemplate?jsonStringData="+jsonStringData+"&questionnaireTemplateUuid="+qTemplateUuid+"&uuid="+savedQuestionnaireUuid.get("savedQuestionnaireUuid")+"&submittedPoints="+submittedPoints+"&totalPoints="+totalPoints
	      	      			+"&workflowStageUuid="+workflowStageUuid+"&requirementSubmissionId="+requirementSubmissionId+"&submissionType="+submissionType.get("submissionType"), 
	      	        success: function(result) {
	      	           if (result == null) {
			        	    location.href = "/errorPage";
			        	} else if (result != null) {
			        		$(".mask, #modalTasktype").fadeOut();
			        	} else {
			        	    showError("Operation failed, please try again");
			        	}
	      	        },
	      	    });   
	              return true;
	        }else{
	        	alertMessage("Provide choice for "+fieldLabel)
	        }
	    }
	    else{
	    	return false;	
	       } 
}


</script>
</body>
</html>
