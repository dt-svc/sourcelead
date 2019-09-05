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
</head>
 
 
<script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
<link rel="stylesheet" href="/resources/css/style.css?staticTime=${staticTime}" />
<script src="/resources/js/jquery-ui.js?staticTime=${staticTime}"></script>
<link rel="stylesheet" href="/resources/css/jquery-ui.css?staticTime=${staticTime}" type="text/css" >

<style type="text/css">

.choices label {
width: auto !important; padding-right: 15px;
}
.choices input[type="checkbox"], .choices input[type="radio"]{
 margin-right: 10px; position: relative; top: 1px;
}
</style>

<script type="text/javascript">
var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
var labelAndAttemptsMap = new Map();
var savedQuestionnaireUuid = new Map();
var submissionType = new Map();
var templateUuid = '${templateUuid}';
var workflowStageId = '${workflowStageId}';
var requirementSubmissionId = '${requirementSubmissionId}';
var autoSubmission = '${auto}';
var manualSubmission = '${manual}';
var submissionsCount = 1;
window.onload = function () {
	questionnaireTemplateForm();
 };
</script>
<div id ="popUp"></div>
<script>
function questionnaireFillingCheck(minutes, seconds){
	var result = "";
	result = result +"<div class='project_inn_mask'></div>";
	result = result +"<div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"<p><strong>Do You Want To Initiate / Continue The Timer And Start Filling The Questionnaire ?</strong></p>";
	result = result +"<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"<b href='javascript:void(0)'><input type='button' id='deleteCustomField'  value='Yes' onclick='saveQuestionnareWithTimeLimit("+minutes+", "+seconds+");' style='background-color:#1ab394'/><input class='u_can_color' type='button' onclick='cancelQuestionnaireFillingCheck();' value='No'></b>";
	result = result +"</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
	
}

function cancelQuestionnaireFillingCheck(){
	$("#remove_user_pop").slideUp(200);
	$(".project_inn_mask").hide();
	$("#questionnaireTemplateFormDiv").hide();
	$("#cancelFeedbackDiv").show();
}

function saveQuestionnareWithTimeLimit(minutes, seconds){
	$("#remove_user_pop").slideUp(200);
	$(".project_inn_mask").hide();
	submissionType.set("submissionType", autoSubmission);
	$.ajax({
    	type: "GET",
        url:sourceleadUrl+"/saveQuestionnaireTemplateForSubmission?questionnaireTemplateUuid="+templateUuid+"&workflowStageId="+workflowStageId+"&requirementSubmissionId="+requirementSubmissionId+"&submissionType="+submissionType.get("submissionType"), 
        success: function(data) {
        	savedQuestionnaireUuid.set("savedQuestionnaireUuid", data.uuid);
        	updateFeedbackTimerPerSecond(templateUuid ,workflowStageId, requirementSubmissionId, data.timeLimitMinutes, data.timeLimitSeconds)
        }
	});

}
</script>

<script>
function questionnaireTemplateForm(){
	
	$("#questionnaireTemplateFormDiv").show();
		generateQuestionnaireForm1(sourceleadUrl, templateUuid)
		var result = "";
		result = result + '<form id="questionnaireTemplate_form" name="questionnaireTemplate_form" enctype="multipart/form-data" method="post">';
		result = result + '<div class="row"><h3 class="col-md-3">Questionnaire Template</h3> <div class="col-md-9"><i class="fa fa-clock-o mr10"></i><label class="textSuccess textBold" id="countdownTimerLbl">Starting</label></div></div>';
		result = result + "";
		
		
		result = result + '<div class="form-horizontal clearfix">';
		result = result + '<div id="questionnairesjfb-fields" class="viewTemplate view_templateDiv viewtemplate_paragraph">';
		result = result + '</div>';
		result = result + '</div>';
		
		 result = result + '<div class="form-footer clearfix">';
		result = result + '<div class="pull-right mr">';
		result = result + '<button type="button" id = "submitButton" class="btn btn-sm btn-primary" onclick="saveQuestionnaireTemplate();">Submit</button>';
		//result = result + '<button type="button"  class="btn btn-sm btn-warning ml">Cancel</button>';
		result = result + '</div>';
		result = result + '</div>';
		result = result + '</div>';  
		result = result + '</form>';
		 
		$("#questionnaireTemplateFormDiv").empty();
		$("#questionnaireTemplateFormDiv").append(result);
	
}

function updateFeedbackQuestionnaireTimer(questTemplUuid,stageId,submissionId,minutes,seconds, distance){
	$.ajax({
    	type: "POST",
        url:sourceleadUrl+"/updateFeedbackQuestionnaireTimer?questTemplUuid="+questTemplUuid+"&stageId="+stageId+"&submissionId="+submissionId+"&minutes="+minutes+"&seconds="+seconds, 
        success: function(response) {
        	if(response == "Success"){
        		console.log("Successfully updated time in table..............>");
        	}
        	if(response == "Failed"){
        		console.log("Failed to updated in table......................>");
        	}
        }
      });
}
</script>
<script>
function updateFeedbackTimerPerSecond(questUUid ,stageId, submissionId,minutes, seconds) {
	var end = new Date();
	end.setMinutes(end.getMinutes() + minutes); 
	end.setSeconds(end.getSeconds() + seconds);
	var _second = 1000;
	var _minute = _second * 60;
	var _hour = _minute * 60;
	var _day = _hour * 24;
	var timer;
	 var timerDisplayText= "";
	function showRemaining() {
		var now = new Date();
		var distance = end - now;
		if (distance < 0) {
			clearInterval(timer);
			$('#countdownTimerLbl').text(' EXPIRED!');
			$("#submitButton").addClass("disabled");
			saveAutoSubmitQuestionnaireForm();
			return;
		}
		var days = Math.floor(distance / _day);
		var hours = Math.floor((distance % _day) / _hour);
		var minutes = Math.floor((distance % _hour) / _minute);
		var seconds = Math.floor((distance % _minute) / _second);
		if (minutes == 2 && seconds == 0) //  Two minutes alert
			console.error("Time Expires in " + minutes);
		else if (minutes == 1 && seconds == 0){ // 1 minutes alert
			console.error("Time Expires in " + minutes);
			$('#countdownTimerLbl').toggleClass("textSuccess colorRed");
		}
		     // time display label.
        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;
        
		timerDisplayText=  minutes + ' mins : '+seconds + ' sec';
		$('#countdownTimerLbl').text(timerDisplayText);
		// each second we are updating timer in table
		
		// uncomment below function for updating time in back end.
		                                 //questUUid, stageId, submissionId, minutes,seconds
		updateFeedbackQuestionnaireTimer(questUUid,stageId,submissionId ,minutes,seconds, distance); 
	}
	timer = setInterval(showRemaining, 1000);
}

</script>
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
	        submittedPoints = calculateTotalPoints(pointsArray);
	        totalPoints = calculateTotalPoints(totalPointsArray);
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
	      	      			+"&workflowStageId="+workflowStageId+"&requirementSubmissionId="+requirementSubmissionId+"&submissionType="+submissionType.get("submissionType"), 
	      	        success: function(result) {
	      	           if (result == null) {
			        	    location.href = "/errorPage";
			        	} else if (result != null) {
			        		submissionsCount++;
			        		generateQuestionnaireFeedbackTemplateForm(result, qTemplateUuid, workflowStageId, requirementSubmissionId);
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
$('body').on('blur', '#questionnaireTemplate_form', function() {
	$("#questionnaireTemplate_form").showHelpOnFocus();
	$("#questionnaireTemplate_form").validateOnBlur(false, validationSettings);
});

function calculateTotalPoints(pointsArray){
	var totalP = 0;
	var current = 0
	for(var i = 0; i<pointsArray.length ; i++ )
	{
		current = parseInt(pointsArray[i]);
		totalP += current;
	}
	return totalP;
}

</script>
<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 
<script type="text/javascript">
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
   <div id="questionnaireTemplateFormDiv" class="container container-wraper container-publicFBT positionRelative paddingTop55" style="display: none;"> </div> 
   <div id="cancelFeedbackDiv" class="container paddingTop55 container-wraper container-publicFBT positionRelative" style="display: none;"> 
   <p><h3 class="col-md-3">
   Thank You !
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
function generateQuestionnaireForm1(sourceleadUrl, templateUuid) {
	
    //empty out the preview area
    $("#questionnairesjfb-fields").empty();
    var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormatScript = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormatScript = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormatScript = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormatScript = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormatScript = 'yy-dd-mm'; 
	  }
    $.ajax({
    	type: "GET",
        url:sourceleadUrl+"/getQuestionnaireTemplateList?templateUuid="+templateUuid+"&stageId="+workflowStageId+"&submissionId="+requirementSubmissionId, 
        success: function(result) {
        	if(result.timeLimitMinutes != null && result.timeLimitSeconds != null){
        		if(result.timeMinutesRemained == null && result.timeSecondsRemained == null){
        			questionnaireFillingCheck(result.timeLimitMinutes, result.timeLimitSeconds);
            	}else{
            		updateFeedbackTimerPerSecond(templateUuid, workflowStageId, requirementSubmissionId, result.timeMinutesRemained, result.timeSecondsRemained);
        		}
        		}
        	
        	var data = null;
        	if(result.jsonString != null){
        		data = eval(result.jsonString);
        	}else{
        		data = eval(result.jsonString);
        	}
        	
        	//setting values to labelAndAttemptsMap.
        	var valuesData = null;
        	if(result.jsonString != null){
        		valuesData = eval(result.jsonStringValues);
        	}else{
        		valuesData = eval(result.jsonStringValues);
        	}
        	if(valuesData){
        		 $.each( valuesData, function( k, v ) {
        			 labelAndAttemptsMap.set(v['label'], v['attempts']);
        		 });
        	}
        	
        	if(result.uuid != null){
        	savedQuestionnaireUuid.set("savedQuestionnaireUuid", result.uuid);
        	}
        	var attachmentCount = 0;
        	var fileCount = 0;
        	 if (data) {
                 //go through each saved field object and render the form HTML
                 $.each( data, function( k, v ) {
					
                     var fieldType = v['type'];
                     var labelTitle = v['label'];
                     var required =  v['req'];
                     //Add the field
                     $('#questionnairesjfb-fields').append(addSavedFieldHTML(fieldType,v['req'],v['label'],v['hint'],v['dropdown'],v['points'],v['attempt'],attachmentCount));
                     var $currentField = $('#questionnairesjfb-fields .sjfb-field').last();
                     
                     if(fieldType == "file"){
                    	 attachmentCount = attachmentCount+1;
                     }
                     
                     var reqSymbol = '';
                	 if(v['req'] == '1'){
                		reqSymbol = " * ";
                	 }
                     
                     //Add the label
                     if(v['label'] != 'location' ){
                    	 $currentField.find('label').text(v['label']+reqSymbol);
                     }
                     
                    
                     if(result.jsonStringValues != null && result.jsonStringValues != '' && result.jsonStringValues != 'undefined'){
                    	 if (v['choices']) {
                    		 var selectChoice = 1;
                        	 var selectChoiceHTML = '<option value="">----Select-----</option>';
                             var uniqueID = Math.floor(Math.random()*999999)+1;
                             $.each( v['choices'], function( k1, v1 ) {
                            	 var label = v1['label'];
                                if (fieldType == 'radio') {
                            	  var details = eval(result.jsonStringValues);
                              	   $.each(details, function( k2, v2 ) {
                              		   if(v['label'] == v2['label']){
                              			   var values = v2['value'].split(",");
                              			   for(var i=0; i<values.length; i++){
                              				 var selected = '';
                              				   if(values[i] == v1['label']){
                              					 selected = v1['sel'] ? ' checked' : 'checked';
                              				   }else{
                              					 selected = v1['sel'] ? ' checked' : '';
                              				   }
                                             
                                             var choiceHTML = '<span><input type="radio" ' + selected + ' name=\''+label.replace(/\s+/g, '')+'\'  onclick="getRadioValue(this,\''+label.replace(/\s+/g, '')+'\');"  value="' + v1['label'] + '">' + v1['label'] + '</span>';
                                             if(required == 1 && flag == 0){
                                            	 choiceHTML = choiceHTML+'<input type="hidden"  id="'+labelTitle.replace(/\s+/g, '')+'radio" data-validation="required" field-name="'+labelTitle+'">';
                                             }
                                             $currentField.find(".choices").append(choiceHTML);
                              			   }
                              		   }
                              	   });
                                 }
                                 else if (fieldType == 'checkbox') {
                                	 var details = eval(result.jsonStringValues);
                                	 var selected = '';
                                	 $.each(details, function( k3, v3 ) {
                                		 if(v['label'] == v3['label']){
                                			 var values = v3['value'].split(",");
                                			 for(var i=0; i<values.length; i++){
                                				 if(values[i] == v1['label']){
                                  					 selected = "checked";
                              				     }
                                			 }
                                             
                                             var choiceHTML = '<span><input type="checkbox" ' + selected + ' name=\''+label.replace(/\s+/g, '')+'\' onclick="getCheckBoxValue(this,\''+label.replace(/\s+/g, '')+'\')"  value="' + v1['label'] + '">' + v1['label'] + '</span>';
                                             if(required == 1 && flag == 0){
                                            	 choiceHTML = choiceHTML+'<input type="hidden"  id="'+labelTitle.replace(/\s+/g, '')+'checkbox" data-validation="required" field-name="'+labelTitle+'" >';
                                             }
                                             $currentField.find(".choices").append(choiceHTML);
                                		 }
                                	 });
                                 }else if (fieldType == 'select') {
                                	 var details = eval(result.jsonStringValues);
                                	 var selected = '';
                                	 $.each(details, function( k4, v4 ) {
                                		 if(v['label'] == v4['label']){
                                				 if(v4['value'] == v1['label']){
                                  					 selected = "selected";
                              				     }
                                				 if(selectChoice > 1){
                                               		 selectChoiceHTML = '';
                                               	 }
                                             
                                				 var choiceHTML = '<option value="'+v1['label']+'" '+selected+'>' + v1['label'] + '</option>';
                                                 $currentField.find(".choices").append(selectChoiceHTML+choiceHTML);
                                                 selectChoice = selectChoice+1;
                                		 }
                                	 });
                                 }
                             });
                         }
                    	 
                    	 if(fieldType == 'textarea' || fieldType == 'text' || fieldType == 'calendar' || fieldType == 'scale' || fieldType == 'star' || fieldType == 'file'){
    	                   	 var details = eval(result.jsonStringValues);
    	                   	 var label = v['label'];
    	                	 $.each(details, function( k3, v3 ) {
    	                		 if(label == v3['label']){
    	                			 if(fieldType == 'textarea'){
    	                				 if(v3['value'] != null && v3['value'] != '' && v3['value'] != 'undefined'){
    	                					 $('#textarea-'+ label.replace(/\s+/g, '')).html(v3['value']);
    	                				 }
    	                			 }else if(fieldType == 'text'){
    	                				 if(v3['value'] != null && v3['value'] != '' && v3['value'] != 'undefined'){
    	                					 $('#text-'+ label.replace(/\s+/g, '')).val(v3['value']);
    	                				 }
    	                				
    	                			 }else if(fieldType == 'calendar'){
    	                				 if(v3['value'] != null && v3['value'] != '' && v3['value'] != 'undefined'){
    	                					 $('#calendar-'+ label.replace(/\s+/g, '')).val(v3['value']);
    	                				 }
    	                			 }else if(fieldType == 'scale'){
    	                				 if(v3['value'] != null && v3['value'] != '' && v3['value'] != 'undefined'){
    	                					 evaluatePostSlider(label.replace(/\s+/g, ''),v3['value'])
    	                				 }
    	                			 }
    	                			 else if(fieldType == 'star'){
    	                				 if(v3['value'] != null && v3['value'] != '' && v3['value'] != 'undefined'){
    	                					 fillStarValues(v['dropdown'],label.replace(/\s+/g, ''),v3['value'])
    	                				 }
    	                			 }
    	                			 
    	                			 else if(fieldType == 'file'){
    	                					  var fileHtml = '';
    	                					  fileHtml = fileHtml + "<input type='hidden' name='uploadedFileInfo["+fileCount+"].fileURL'  data-id='newUploadBtn"+label.replace(/\s+/g, '')+"' value='"+v3['value']+"' />";
    	                					  fileHtml = fileHtml + "<input type='hidden' name='uploadedFileInfo["+fileCount+"].fileName' data-id='newUploadBtn"+label.replace(/\s+/g, '')+"' value='"+v3['name']+"' />";
    	                					  fileHtml = fileHtml + "<input type='hidden' name='uploadedFileInfo["+fileCount+"].status' data-id='newUploadBtn"+label.replace(/\s+/g, '')+"' value='1' />";
    	                					  $("#addFeedbackTemplate_form").append(fileHtml);
	                					      fileCount = fileCount+1;
	                					      
	                					      if(v3['value'] != null && v3['value'] != '' && v3['value'] != 'undefined'){
	                					    	  $('#newUploadFile'+label.replace(/\s+/g, '')).val(v3['name']);
		                					      $("#download-"+label.replace(/\s+/g, '')).attr("href",v3['value']);
		                					      $("#attachmentTitle-"+label.replace(/\s+/g, '')).html(v3['name']);
		                					      document.getElementById("download-"+label.replace(/\s+/g, '')).style.display="";
	                					      }
    	                			 }
    	                			
    	                		 }
    	                	 });
    	                  }
                    	 
                     }else{
                    	 
                    	 //Any choices?
                         if (v['choices']) {
                        	 var label = v['label'];
                             var uniqueID = Math.floor(Math.random()*999999)+1;
                             var selectChoice = 1;
                             var flag = 0;
                             var selectChoiceHTML = '<option value="">----Select-----</option>';
                             $.each( v['choices'], function( k, v ) {
                             	
                            	    if (fieldType == 'select') {
                                   	 if(selectChoice > 1){
                                   		 selectChoiceHTML = '';
                                   	 }
                                        var choiceHTML = '<option value="'+v['label']+'">' + v['label'] + '</option>';
                                        $currentField.find(".choices").append(selectChoiceHTML+choiceHTML);
                                        selectChoice = selectChoice+1;
                                    }

                                 else if (fieldType == 'radio') {
                                     var choiceHTML = '<span><input type="radio" name=\''+label.replace(/\s+/g, '')+'\'  onclick="getRadioValue(this,\''+label.replace(/\s+/g, '')+'\');"  value="' + v['label'] + '">' + v['label'] + '</span>';
                                     if(required == 1 && flag == 0){
                                    	 choiceHTML = choiceHTML+'<input type="hidden"  id="'+labelTitle.replace(/\s+/g, '')+'radio" data-validation="required" field-name="'+labelTitle+'">';
                                     }
                                     $currentField.find(".choices").append(choiceHTML);
                                 }

                                 else if (fieldType == 'checkbox') {
                                     var selected = v['sel'] ? ' checked' : 'checked';
                                     var choiceHTML = '<span><input type="checkbox" name=\''+label.replace(/\s+/g, '')+'\' onclick="getCheckBoxValue(this,\''+label.replace(/\s+/g, '')+'\')"  value="' + v['label'] + '">' + v['label'] + '</span>';
                                     if(required == 1 && flag == 0){
                                    	 choiceHTML = choiceHTML+'<input type="hidden"  id="'+labelTitle.replace(/\s+/g, '')+'checkbox" data-validation="required" field-name="'+labelTitle+'" >';
                                     }
                                     $currentField.find(".choices").append(choiceHTML);
                                 }
                                 
                                 flag = flag+1;

                             });
                         }
                     }
                     
                     //Is it required?
                     if (v['req']) {
                         if (fieldType == 'text') { $currentField.find("input").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'calendar') { $currentField.find("calandar").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'autocomplete') { $currentField.find("autocomplete").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'textarea') { $currentField.find("textarea").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'select') { $currentField.find("select").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'radio') { $currentField.find("input").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'scale') { $currentField.find("scale").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'star') { $currentField.find("star").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'file') { $currentField.find("file").prop('required',true).addClass('required-choice') }
                         $currentField.addClass('required-field');
                     }

                 });
             }
        	 
        	 
        	 
        	  //HTML templates for rendering frontend form fields
             function addSavedFieldHTML(fieldType,required,label,hint,dropdown,points,attempt,attachmentCount) {
            	 var uniqueID = Math.floor(Math.random()*999999)+1;
            	 var datavalidation = '';	
            	 var attachmentDataValidation = '';
            	 var attachmentDataValidationOptional = ''
            	 if(required == '1'){
            		 datavalidation = 'data-validation="required"';
            	 }
            	 
            	 if(fieldType == "star"){
            		 var StarHtml = '';
            		 if(attempt == 0){
            			 for(var i=1; i<=dropdown; i++){
                			 StarHtml = StarHtml+'<li class="fa fa-star cursorPointer text-muted"   id="star-select'+i+label.replace(/\s+/g, '')+'"></li>';
      					}
                	 }else{
                		 for(var i=1; i<=dropdown; i++){
                			 StarHtml = StarHtml+'<li class="fa fa-star cursorPointer text-muted"  onclick="fillStarValues('+i+',\''+label.replace(/\s+/g, '')+'\');"  id="star-select'+i+label.replace(/\s+/g, '')+'"></li>';
      					}}
            		 
            	 }
            	 
            	 if(fieldType == "file"){
            		 if(dropdown == 'docx'){
            			 if(required == '1'){
            				 attachmentDataValidation = 'data-validation="required validatedoc"';
            			 }else{
            				 attachmentDataValidationOptional = 'data-validation-optional="true"';
            				 attachmentDataValidation = 'data-validation="validatedoc"';
            			 }
            			 
            		 }else if(dropdown == 'xls'){
            			 
            			 if(required == '1'){
            				 attachmentDataValidation = 'data-validation="required validate_importFile"';
            			 }else{
            				 attachmentDataValidationOptional = 'data-validation-optional="true"';
            				 attachmentDataValidation = 'data-validation="validate_importFile"';
            			 }
            			 
            		 }else if(dropdown == 'png'){
            			 if(required == '1'){
            				 attachmentDataValidation = 'data-validation="required validateimage"';
            			 }else{
            				 attachmentDataValidationOptional = 'data-validation-optional="true"';
            				 attachmentDataValidation = 'data-validation="validateimage"';
            			 }
            		 }
            	 }
                
				var input = "";	
                 switch (fieldType) {
                     case 'text':
                    	 if(attempt == 0){
                    		 input = '<input type="text" field-name="'+label+'" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' id=\'text-'+label.replace(/\s+/g, '')+'\' disabled>' ;
                    	 }else{
                    		 input =  '<input type="text" field-name="'+label+'" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' id=\'text-'+label.replace(/\s+/g, '')+'\' >' ;
                    	 }
                    	 if(labelAndAttemptsMap.get(label) != null){
                    		 attempt = labelAndAttemptsMap.get(label);
                    	 }
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label for="text-' + uniqueID + '"></label>' +
                             input +
                             '</div>' +
                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' +
                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';

                     case 'textarea':
                    	 if(attempt == 0){
                    		 input = '<textarea rows="3" field-name="'+label+'" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' id=\'textarea-'+label.replace(/\s+/g, '')+'\' disabled></textarea>' ;
                    	 }else{
                    		 input =  '<textarea rows="3" field-name="'+label+'" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' id=\'textarea-'+label.replace(/\s+/g, '')+'\' ></textarea>' ;
                    	 }
                    	 if(labelAndAttemptsMap.get(label) != null){
                    		 attempt = labelAndAttemptsMap.get(label);
                    	 }
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-textarea">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label for="textarea-' + uniqueID + '"></label>' +
                             input +
                             '</div>'+
                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' +
                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';

                     case 'select':
                    	 if(attempt == 0){
                    		 input = '<select id="select-' + uniqueID + '" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' field-name="'+label+'" class="choices choices-select"></select>' ;
                    	 }else{
                    		 input =  '<select id="select-' + uniqueID + '" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' field-name="'+label+'" class="choices choices-select"></select>' ;
                    	 }
                    	 if(labelAndAttemptsMap.get(label) != null){
                    		 attempt = labelAndAttemptsMap.get(label);
                    	 }
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-select">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label for="select-' + uniqueID + '"></label>' +
                             input +
                             '</div>'+
                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' +
                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';

                     case 'radio':
                    	 if(labelAndAttemptsMap.get(label) != null){
                    		 attempt = labelAndAttemptsMap.get(label);
                    	 }
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-radio">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label></label>' +
                             '<div class="clearfix mt"></div>'+
                             '<div class="choices choices-radio"></div>' +
                             '</div>'+
                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' +
                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';

                     case 'checkbox':
                    	 if(labelAndAttemptsMap.get(label) != null){
                    		 attempt = labelAndAttemptsMap.get(label);
                    	 }
                         return '' +
                             '<div id="sjfb-checkbox-' + uniqueID + '" class="sjfb-field sjfb-checkbox">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label class="sjfb-label"></label>' +
                             '<div class="clearfix mt"></div>'+
                             '<div class="choices choices-checkbox"></div>' +
                             '</div>'+
                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' +
                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';
                         

                     case 'calendar':
                    	 if(attempt == 0){
                    		 input = '<input type="text" class="DatePicker dp" '+datavalidation+' field-name="'+label+'" name=\''+label.replace(/\s+/g, '')+'\' id=\'calendar-'+label.replace(/\s+/g, '')+'\' disabled>' ;
                    	 }else{
                    		 input =  '<input type="text" class="DatePicker dp" '+datavalidation+' field-name="'+label+'" name=\''+label.replace(/\s+/g, '')+'\' id=\'calendar-'+label.replace(/\s+/g, '')+'\' >' ;
                    	 }
                    	 if(labelAndAttemptsMap.get(label) != null){
                    		 attempt = labelAndAttemptsMap.get(label);
                    	 }
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label for="calandar-' + uniqueID + '"></label>' +
                             input +
                             '</div>'+
                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' +
                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';
                     
                     case 'scale':
                    	 if(attempt == 0){
                    		 input = '<input type="hidden" field-type="scale" field-name="'+label+'" '+datavalidation+'   id=\'scaleValue-'+label.replace(/\s+/g, '')+'\' ></span>' +
                             '<input type="range" min="0" max="'+dropdown+'" value="0" name=\''+label.replace(/\s+/g, '')+'\' onmouseup="evaluatePostSlider(\''+label.replace(/\s+/g, '')+'\');" onclick="evaluatePostSlider(\''+label.replace(/\s+/g, '')+'\');"  id=\'scale-'+label.replace(/\s+/g, '')+'\' class="rangeslider" disabled>' ;
                    	 }else{
                    		 input =  '<input type="hidden" field-type="scale" field-name="'+label+'" '+datavalidation+'   id=\'scaleValue-'+label.replace(/\s+/g, '')+'\' ></span>' +
                             '<input type="range" min="0" max="'+dropdown+'" value="0" name=\''+label.replace(/\s+/g, '')+'\' onmouseup="evaluatePostSlider(\''+label.replace(/\s+/g, '')+'\');" onclick="evaluatePostSlider(\''+label.replace(/\s+/g, '')+'\');"  id=\'scale-'+label.replace(/\s+/g, '')+'\' class="rangeslider">' ;
                    	 }
                    	 if(labelAndAttemptsMap.get(label) != null){
                    		 attempt = labelAndAttemptsMap.get(label);
                    	 }
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label for="text-' + uniqueID + '"></label>' +
                             '<div class="slidecontainer mt clearfix">' +
                             '<span id=\'demo-'+label.replace(/\s+/g, '')+'\' class="col-md-4">0</span>' +
                             input +
                             '</div>'+
                             '</div>'+
                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' +
                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';
                         
                     case 'star':
                    	 if(attempt == 0){
                    		 input = '<input type="hidden" field-type="star" field-name="'+label+'" '+datavalidation+' id=\'starValue-'+label.replace(/\s+/g, '')+'\' name=\''+label.replace(/\s+/g, '')+'\'disabled></span>' ;
                    	 }else{
                    		 input =  '<input type="hidden" field-type="star" field-name="'+label+'" '+datavalidation+' id=\'starValue-'+label.replace(/\s+/g, '')+'\' name=\''+label.replace(/\s+/g, '')+'\'></span>' ;
                    	 }
                    	 if(labelAndAttemptsMap.get(label) != null){
                    		 attempt = labelAndAttemptsMap.get(label);
                    	 }
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text starclass">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label for="text-' + uniqueID + '"></label>' +
                             input +
                             '<ul class="list-unstyled list-inline text-muted" id="starRating-' + uniqueID + '">' +
                             '<div class="clearfix mt"></div>'+
                             StarHtml+
                             '</ul>' +
                             '</div>'+
                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' +
                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';
                         
                     case 'file':
                    	 if(attempt == 0){
                    		 input = '<input type="file" id=\'newUploadBtn'+label.replace(/\s+/g, '')+'\'  onchange="uploadFeedbackAttachments(\''+label.replace(/\s+/g, '')+'\',\''+attachmentCount+'\');" class="upload" disabled>' +
                             '<input type="text" class="form-control" style="display: none;" name=\'docFile'+label.replace(/\s+/g, '')+'\'  id=\'newUploadFile'+label.replace(/\s+/g, '')+'\' '+attachmentDataValidationOptional+' '+attachmentDataValidation+' field-name="'+label+'" disabled>';
                            }else{
                    		 input =  '<input type="file" id=\'newUploadBtn'+label.replace(/\s+/g, '')+'\'  onchange="uploadFeedbackAttachments(\''+label.replace(/\s+/g, '')+'\',\''+attachmentCount+'\');" class="upload">' +
                             '<input type="text" class="form-control" style="display: none;" name=\'docFile'+label.replace(/\s+/g, '')+'\'  id=\'newUploadFile'+label.replace(/\s+/g, '')+'\' '+attachmentDataValidationOptional+' '+attachmentDataValidation+' field-name="'+label+'"  >';
                            }
                    	 if(labelAndAttemptsMap.get(label) != null){
                    		 attempt = labelAndAttemptsMap.get(label);
                    	 }
                    	 return '' +
                         '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
                         '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                         '<label for="text-' + uniqueID + '"></label>' +
                         '<div class="clearfix mt"></div>'+
                         input +
                         '<a download="" class="glyphicon glyphicon-download-alt" style="display: none;" id=\'download-'+label.replace(/\s+/g, '')+'\' title="Download">&nbsp;<span id=\'attachmentTitle-'+label.replace(/\s+/g, '')+'\'></span></a>'+
                         '</div>'+
                         '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' +
                         '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';
                 }
                 
             }
             
             
             
             var d = new Date();
             var year = d.getFullYear() - 10;
             d.setFullYear(year);
             $(".DatePicker").datepicker({
            	 dateFormat: finalDateFormatScript,
            	 changeMonth: true,
                 changeYear: true,
                 maxDate:0,
                 showOn: 'focus',
                 showButtonPanel: true,
                 closeText: 'Clear',
                 onClose : function(selected) {
                	  var event = arguments.callee.caller.caller.arguments[0];
                     if ($(event.delegateTarget).hasClass('ui-datepicker-close')) {
                         $(this).val('');
                     }
                	 $('p.jquery_form_error_message').remove();
                 }
                 
             });
        },
    });
}
</script>
<script>
function generateQuestionnaireFeedbackTemplateForm(resultUuid, qTemplateUuid, workflowStageId, requirementSubmissionId){
	$("#questionnaireTemplateFormDiv").show();
	var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormatScript = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormatScript = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormatScript = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormatScript = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormatScript = 'yy-dd-mm'; 
	  }
	
	 
		var result = "";
		result = result + '<form id="questionnaireTemplate_form" name="questionnaireTemplate_form" enctype="multipart/form-data" method="post">';
		result = result + '<div class="row"><h3 class="col-md-3">Questionnaire Template</h3> <div class="col-md-9"><i class="fa fa-clock-o mr10"></i><label class="textSuccess textBold" id="countdownTimerLbl">Starting</label></div></div>';
		
		result = result + '<div class="form-horizontal clearfix">';
		result = result + '<div id="questionnairesjfb-fields" class="viewTemplate view_templateDiv viewtemplate_paragraph">';
		result = result + '</div>';
		result = result + '</div>';
		
		 result = result + '<div class="form-footer clearfix">';
		result = result + '<div class="pull-right mr">';
		result = result + '<button type="button" id = "submitButton" class="btn btn-sm btn-primary" onclick="saveQuestionnaireTemplate();">Submit</button>';
		result = result + '</div>';
		result = result + '</div>';
		result = result + '</div>';  
		result = result + '</form>';
		 
		$("#questionnaireTemplateFormDiv").empty();
		$("#questionnaireTemplateFormDiv").append(result);
		 generateSavedQuestionnaireTemplate(resultUuid, finalDateFormatScript, qTemplateUuid, workflowStageId, requirementSubmissionId);
}

function generateSavedQuestionnaireTemplate(resultUuid, finalDateFormatScript, qTemplateUuid, workflowStageId, requirementSubmissionId){

    //empty out the preview area
    $("#questionnairesjfb-fields").empty();
    $.ajax({
    	type: "GET",
        url: sourceleadUrl+"/getQuestionnaireTemplateFeedbackList?templateUuid="+resultUuid, 
        success: function(result) {
        	if(result.timeMinutesRemained != null && result.timeSecondsRemained != null){
        		updateFeedbackTimerPerSecond(qTemplateUuid, workflowStageId, requirementSubmissionId, result.timeMinutesRemained, result.timeSecondsRemained);
        	}
        	var data = null;
        	if(result.jsonString != null){
        		data = eval(result.jsonString);
        	}else{
        		data = eval(result.jsonString);
        	}
        	
        	//setting values to labelAndAttemptsMap.
        	var valuesData = null;
        	if(result.jsonString != null){
        		valuesData = eval(result.jsonStringValues);
        	}else{
        		valuesData = eval(result.jsonStringValues);
        	}
        	if(valuesData){
        		 $.each( valuesData, function( k, v ) {
        			 labelAndAttemptsMap.set(v['label'], v['attempts']);
        		 });
        	}
        	
        	if(savedQuestionnaireUuid.get("savedQuestionnaireUuid") == null){
        		savedQuestionnaireUuid.set("savedQuestionnaireUuid", resultUuid);
        	}
        	var attachmentCount = 0;
        	var fileCount = 0;
        	 if (data) {
                 //go through each saved field object and render the form HTML
                 $.each( data, function( k, v ) {

                     var fieldType = v['type'];
                     var labelTitle = v['label'];
                     var required =  v['req'];
                     //Add the field
                     $('#questionnairesjfb-fields').append(addSavedFieldHTML(fieldType,v['req'],v['label'],v['hint'],v['dropdown'],v['points'],v['attempt'],attachmentCount));
                     var $currentField = $('#questionnairesjfb-fields .sjfb-field').last();
                     
                     if(fieldType == "file"){
                    	 attachmentCount = attachmentCount+1;
                     }
                     
                     var reqSymbol = '';
                	 if(v['req'] == '1'){
                		reqSymbol = " * ";
                	 }
                     
                     //Add the label
                     if(v['label'] != 'location' ){
                    	 $currentField.find('label').text(v['label']+reqSymbol);
                     }
                     
                    
                     if(result.jsonStringValues != null && result.jsonStringValues != '' && result.jsonStringValues != 'undefined'){
                    	 if (v['choices']) {
                    		 var selectChoice = 1;
                        	 var selectChoiceHTML = '<option value="">----Select-----</option>';
                             var uniqueID = Math.floor(Math.random()*999999)+1;
                             $.each( v['choices'], function( k1, v1 ) {
                            	 var label = v1['label'];
                                if (fieldType == 'radio') {
                            	  var details = eval(result.jsonStringValues);
                              	   $.each(details, function( k2, v2 ) {
                              		   if(v['label'] == v2['label']){
                              			   var values = v2['value'].split(",");
                              			   for(var i=0; i<values.length; i++){
                              				 var selected = '';
                              				   if(values[i] == v1['label']){
                              					 selected = v1['sel'] ? ' checked' : 'checked';
                              				   }else{
                              					 selected = v1['sel'] ? ' checked' : '';
                              				   }
                                             
                                             var choiceHTML = '<span><input type="radio" ' + selected + ' name=\''+label.replace(/\s+/g, '')+'\'  onclick="getRadioValue(this,\''+label.replace(/\s+/g, '')+'\');"  value="' + v1['label'] + '">' + v1['label'] + '</span>';
                                             if(required == 1 && flag == 0){
                                            	 choiceHTML = choiceHTML+'<input type="hidden"  id="'+labelTitle.replace(/\s+/g, '')+'radio" data-validation="required" field-name="'+labelTitle+'">';
                                             }
                                             $currentField.find(".choices").append(choiceHTML);
                              			   }
                              		   }
                              	   });
                                 }
                                 else if (fieldType == 'checkbox') {
                                	 var details = eval(result.jsonStringValues);
                                	 var selected = '';
                                	 $.each(details, function( k3, v3 ) {
                                		 if(v['label'] == v3['label']){
                                			 var values = v3['value'].split(",");
                                			 for(var i=0; i<values.length; i++){
                                				 if(values[i] == v1['label']){
                                  					 selected = "checked";
                              				     }
                                			 }
                                             
                                             var choiceHTML = '<span><input type="checkbox" ' + selected + ' name=\''+label.replace(/\s+/g, '')+'\' onclick="getCheckBoxValue(this,\''+label.replace(/\s+/g, '')+'\')"  value="' + v1['label'] + '">' + v1['label'] + '</span>';
                                             if(required == 1 && flag == 0){
                                            	 choiceHTML = choiceHTML+'<input type="hidden"  id="'+labelTitle.replace(/\s+/g, '')+'checkbox" data-validation="required" field-name="'+labelTitle+'" >';
                                             }
                                             $currentField.find(".choices").append(choiceHTML);
                                		 }
                                	 });
                                 }else if (fieldType == 'select') {
                                	 var details = eval(result.jsonStringValues);
                                	 var selected = '';
                                	 $.each(details, function( k4, v4 ) {
                                		 if(v['label'] == v4['label']){
                                				 if(v4['value'] == v1['label']){
                                  					 selected = "selected";
                              				     }
                                				 if(selectChoice > 1){
                                               		 selectChoiceHTML = '';
                                               	 }
                                             
                                				 var choiceHTML = '<option value="'+v1['label']+'" '+selected+'>' + v1['label'] + '</option>';
                                                 $currentField.find(".choices").append(selectChoiceHTML+choiceHTML);
                                                 selectChoice = selectChoice+1;
                                		 }
                                	 });
                                 }
                             });
                         }
                    	 
                    	 if(fieldType == 'textarea' || fieldType == 'text' || fieldType == 'calendar' || fieldType == 'scale' || fieldType == 'star' || fieldType == 'file'){
    	                   	 var details = eval(result.jsonStringValues);
    	                   	 var label = v['label'];
    	                	 $.each(details, function( k3, v3 ) {
    	                		 if(label == v3['label']){
    	                			 if(fieldType == 'textarea'){
    	                				 if(v3['value'] != null && v3['value'] != '' && v3['value'] != 'undefined'){
    	                					 $('#textarea-'+ label.replace(/\s+/g, '')).html(v3['value']);
    	                				 }
    	                			 }else if(fieldType == 'text'){
    	                				 if(v3['value'] != null && v3['value'] != '' && v3['value'] != 'undefined'){
    	                					 $('#text-'+ label.replace(/\s+/g, '')).val(v3['value']);
    	                				 }
    	                				
    	                			 }else if(fieldType == 'calendar'){
    	                				 if(v3['value'] != null && v3['value'] != '' && v3['value'] != 'undefined'){
    	                					 $('#calendar-'+ label.replace(/\s+/g, '')).val(v3['value']);
    	                				 }
    	                			 }else if(fieldType == 'scale'){
    	                				 if(v3['value'] != null && v3['value'] != '' && v3['value'] != 'undefined'){
    	                					 evaluatePostSlider(label.replace(/\s+/g, ''),v3['value'])
    	                				 }
    	                			 }
    	                			 else if(fieldType == 'star'){
    	                				 if(v3['value'] != null && v3['value'] != '' && v3['value'] != 'undefined'){
    	                					 fillStarValues(v['dropdown'],label.replace(/\s+/g, ''),v3['value'])
    	                				 }
    	                			 }
    	                			 
    	                			 else if(fieldType == 'file'){
    	                					  var fileHtml = '';
    	                					  fileHtml = fileHtml + "<input type='hidden' name='uploadedFileInfo["+fileCount+"].fileURL'  data-id='newUploadBtn"+label.replace(/\s+/g, '')+"' value='"+v3['value']+"' />";
    	                					  fileHtml = fileHtml + "<input type='hidden' name='uploadedFileInfo["+fileCount+"].fileName' data-id='newUploadBtn"+label.replace(/\s+/g, '')+"' value='"+v3['name']+"' />";
    	                					  fileHtml = fileHtml + "<input type='hidden' name='uploadedFileInfo["+fileCount+"].status' data-id='newUploadBtn"+label.replace(/\s+/g, '')+"' value='1' />";
    	                					  $("#addFeedbackTemplate_form").append(fileHtml);
	                					      fileCount = fileCount+1;
	                					      
	                					      if(v3['value'] != null && v3['value'] != '' && v3['value'] != 'undefined'){
	                					    	  $('#newUploadFile'+label.replace(/\s+/g, '')).val(v3['name']);
		                					      $("#download-"+label.replace(/\s+/g, '')).attr("href",v3['value']);
		                					      $("#attachmentTitle-"+label.replace(/\s+/g, '')).html(v3['name']);
		                					      document.getElementById("download-"+label.replace(/\s+/g, '')).style.display="";
	                					      }
    	                			 }
    	                			
    	                		 }
    	                	 });
    	                  }
                    	 
                     }else{
                    	 
                    	 //Any choices?
                         if (v['choices']) {
                        	 var label = v['label'];
                             var uniqueID = Math.floor(Math.random()*999999)+1;
                             var selectChoice = 1;
                             var flag = 0;
                             var selectChoiceHTML = '<option value="">----Select-----</option>';
                             $.each( v['choices'], function( k, v ) {
                             	
                            	    if (fieldType == 'select') {
                                   	 if(selectChoice > 1){
                                   		 selectChoiceHTML = '';
                                   	 }
                                        var choiceHTML = '<option value="'+v['label']+'">' + v['label'] + '</option>';
                                        $currentField.find(".choices").append(selectChoiceHTML+choiceHTML);
                                        selectChoice = selectChoice+1;
                                    }

                                 else if (fieldType == 'radio') {
                                     var choiceHTML = '<span><input type="radio" name=\''+label.replace(/\s+/g, '')+'\'  onclick="getRadioValue(this,\''+label.replace(/\s+/g, '')+'\');"  value="' + v['label'] + '">' + v['label'] + '</span>';
                                     if(required == 1 && flag == 0){
                                    	 choiceHTML = choiceHTML+'<input type="hidden"  id="'+labelTitle.replace(/\s+/g, '')+'radio" data-validation="required" field-name="'+labelTitle+'">';
                                     }
                                     $currentField.find(".choices").append(choiceHTML);
                                 }

                                 else if (fieldType == 'checkbox') {
                                     var selected = v['sel'] ? ' checked' : 'checked';
                                     var choiceHTML = '<span><input type="checkbox" name=\''+label.replace(/\s+/g, '')+'\' onclick="getCheckBoxValue(this,\''+label.replace(/\s+/g, '')+'\')"  value="' + v['label'] + '">' + v['label'] + '</span>';
                                     if(required == 1 && flag == 0){
                                    	 choiceHTML = choiceHTML+'<input type="hidden"  id="'+labelTitle.replace(/\s+/g, '')+'checkbox" data-validation="required" field-name="'+labelTitle+'" >';
                                     }
                                     $currentField.find(".choices").append(choiceHTML);
                                 }
                                 
                                 flag = flag+1;

                             });
                         }
                     }
                     
                     //Is it required?
                     if (v['req']) {
                         if (fieldType == 'text') { $currentField.find("input").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'calendar') { $currentField.find("calandar").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'autocomplete') { $currentField.find("autocomplete").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'textarea') { $currentField.find("textarea").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'select') { $currentField.find("select").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'radio') { $currentField.find("input").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'scale') { $currentField.find("scale").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'star') { $currentField.find("star").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'file') { $currentField.find("file").prop('required',true).addClass('required-choice') }
                         $currentField.addClass('required-field');
                     }

                 });
             }
        	 
        	 
        	 
        	  //HTML templates for rendering frontend form fields
             function addSavedFieldHTML(fieldType,required,label,hint,dropdown,points,attempt,attachmentCount) {
            	 var uniqueID = Math.floor(Math.random()*999999)+1;
            	 var datavalidation = '';	
            	 var attachmentDataValidation = '';
            	 var attachmentDataValidationOptional = ''
            	 if(required == '1'){
            		 datavalidation = 'data-validation="required"';
            	 }
            	 
            	 if(fieldType == "star"){
            		 var StarHtml = '';
            		 if(labelAndAttemptsMap.get(label) == 0){
            			 for(var i=1; i<=dropdown; i++){
                			 StarHtml = StarHtml+'<li class="fa fa-star cursorPointer text-muted"   id="star-select'+i+label.replace(/\s+/g, '')+'"></li>';
      					}
                	 }else{
                		 for(var i=1; i<=dropdown; i++){
                			 StarHtml = StarHtml+'<li class="fa fa-star cursorPointer text-muted"  onclick="fillStarValues('+i+',\''+label.replace(/\s+/g, '')+'\');"  id="star-select'+i+label.replace(/\s+/g, '')+'"></li>';
      					}}
            		 
            	 }
            	 
            	 if(fieldType == "file"){
            		 if(dropdown == 'docx'){
            			 if(required == '1'){
            				 attachmentDataValidation = 'data-validation="required validatedoc"';
            			 }else{
            				 attachmentDataValidationOptional = 'data-validation-optional="true"';
            				 attachmentDataValidation = 'data-validation="validatedoc"';
            			 }
            			 
            		 }else if(dropdown == 'xls'){
            			 
            			 if(required == '1'){
            				 attachmentDataValidation = 'data-validation="required validate_importFile"';
            			 }else{
            				 attachmentDataValidationOptional = 'data-validation-optional="true"';
            				 attachmentDataValidation = 'data-validation="validate_importFile"';
            			 }
            			 
            		 }else if(dropdown == 'png'){
            			 if(required == '1'){
            				 attachmentDataValidation = 'data-validation="required validateimage"';
            			 }else{
            				 attachmentDataValidationOptional = 'data-validation-optional="true"';
            				 attachmentDataValidation = 'data-validation="validateimage"';
            			 }
            		 }
            	 }
                
				var input = "";	
                 switch (fieldType) {

                     case 'text':
                    	 if(labelAndAttemptsMap.get(label) == 0){
                    		 input = '<input type="text" field-name="'+label+'" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' id=\'text-'+label.replace(/\s+/g, '')+'\' disabled>' ;
                    	 }else{
                    		 input =  '<input type="text" field-name="'+label+'" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' id=\'text-'+label.replace(/\s+/g, '')+'\' >' ;
                    	 }
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label for="text-' + uniqueID + '"></label>' +
                             input +
                             '</div>' +
                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+labelAndAttemptsMap.get(label)+'</strong> Attempt(s)</div>' +
                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';

                     case 'textarea':
                    	 if(labelAndAttemptsMap.get(label) == 0){
                    		 input = '<textarea rows="3" field-name="'+label+'" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' id=\'textarea-'+label.replace(/\s+/g, '')+'\' disabled></textarea>' ;
                    	 }else{
                    		 input =  '<textarea rows="3" field-name="'+label+'" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' id=\'textarea-'+label.replace(/\s+/g, '')+'\' ></textarea>' ;
                    	 }
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-textarea">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label for="textarea-' + uniqueID + '"></label>' +
                             input +
                             '</div>'+
                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+labelAndAttemptsMap.get(label)+'</strong> Attempt(s)</div>' +
                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';

                     case 'select':
                    	 if(labelAndAttemptsMap.get(label) == 0){
                    		 input = '<select id="select-' + uniqueID + '" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' field-name="'+label+'" class="choices choices-select"></select>' ;
                    	 }else{
                    		 input =  '<select id="select-' + uniqueID + '" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' field-name="'+label+'" class="choices choices-select"></select>' ;
                    	 }
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-select">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label for="select-' + uniqueID + '"></label>' +
                             input +
                             '</div>'+
                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+labelAndAttemptsMap.get(label)+'</strong> Attempt(s)</div>' +
                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';

                     case 'radio':
                    	 
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-radio">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label></label>' +
                             '<div class="clearfix mt"></div>'+
                             '<div class="choices choices-radio"></div>' +
                             '</div>'+
                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+labelAndAttemptsMap.get(label)+'</strong> Attempt(s)</div>' +
                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';

                     case 'checkbox':
                         return '' +
                             '<div id="sjfb-checkbox-' + uniqueID + '" class="sjfb-field sjfb-checkbox">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label class="sjfb-label"></label>' +
                             '<div class="clearfix mt"></div>'+
                             '<div class="choices choices-checkbox"></div>' +
                             '</div>'+
                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+labelAndAttemptsMap.get(label)+'</strong> Attempt(s)</div>' +
                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';
                         

                     case 'calendar':
                    	 if(labelAndAttemptsMap.get(label) == 0){
                    		 input = '<input type="text" class="DatePicker dp" '+datavalidation+' field-name="'+label+'" name=\''+label.replace(/\s+/g, '')+'\' id=\'calendar-'+label.replace(/\s+/g, '')+'\' disabled>' ;
                    	 }else{
                    		 input =  '<input type="text" class="DatePicker dp" '+datavalidation+' field-name="'+label+'" name=\''+label.replace(/\s+/g, '')+'\' id=\'calendar-'+label.replace(/\s+/g, '')+'\' >' ;
                    	 }
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label for="calandar-' + uniqueID + '"></label>' +
                             input +
                             '</div>'+
                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+labelAndAttemptsMap.get(label)+'</strong> Attempt(s)</div>' +
                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';
                     
                     case 'scale':
                    	 if(labelAndAttemptsMap.get(label) == 0){
                    		 input = '<input type="hidden" field-type="scale" field-name="'+label+'" '+datavalidation+'   id=\'scaleValue-'+label.replace(/\s+/g, '')+'\' ></span>' +
                             '<input type="range" min="0" max="'+dropdown+'" value="0" name=\''+label.replace(/\s+/g, '')+'\' onmouseup="evaluatePostSlider(\''+label.replace(/\s+/g, '')+'\');" onclick="evaluatePostSlider(\''+label.replace(/\s+/g, '')+'\');"  id=\'scale-'+label.replace(/\s+/g, '')+'\' class="rangeslider" disabled>' ;
                    	 }else{
                    		 input =  '<input type="hidden" field-type="scale" field-name="'+label+'" '+datavalidation+'   id=\'scaleValue-'+label.replace(/\s+/g, '')+'\' ></span>' +
                             '<input type="range" min="0" max="'+dropdown+'" value="0" name=\''+label.replace(/\s+/g, '')+'\' onmouseup="evaluatePostSlider(\''+label.replace(/\s+/g, '')+'\');" onclick="evaluatePostSlider(\''+label.replace(/\s+/g, '')+'\');"  id=\'scale-'+label.replace(/\s+/g, '')+'\' class="rangeslider">' ;
                    	 }
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label for="text-' + uniqueID + '"></label>' +
                             '<div class="slidecontainer mt clearfix">' +
                             '<span id=\'demo-'+label.replace(/\s+/g, '')+'\' class="col-md-4">0</span>' +
                             input +
                             '</div>'+
                             '</div>'+
                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+labelAndAttemptsMap.get(label)+'</strong> Attempt(s)</div>' +
                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';
                         
                     case 'star':
                    	 if(labelAndAttemptsMap.get(label) == 0){
                    		 input = '<input type="hidden" field-type="star" field-name="'+label+'" '+datavalidation+' id=\'starValue-'+label.replace(/\s+/g, '')+'\' name=\''+label.replace(/\s+/g, '')+'\'disabled></span>' ;
                    	 }else{
                    		 input =  '<input type="hidden" field-type="star" field-name="'+label+'" '+datavalidation+' id=\'starValue-'+label.replace(/\s+/g, '')+'\' name=\''+label.replace(/\s+/g, '')+'\'></span>' ;
                    	 }
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text starclass">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label for="text-' + uniqueID + '"></label>' +
                             input +
                             '<ul class="list-unstyled list-inline text-muted" id="starRating-' + uniqueID + '">' +
                             '<div class="clearfix mt"></div>'+
                             StarHtml+
                             '</ul>' +
                             '</div>'+
                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+labelAndAttemptsMap.get(label)+'</strong> Attempt(s)</div>' +
                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';
                         
                     case 'file':
                    	 if(labelAndAttemptsMap.get(label) == 0){
                    		 input = '<input type="file" id=\'newUploadBtn'+label.replace(/\s+/g, '')+'\'  onchange="uploadFeedbackAttachments(\''+label.replace(/\s+/g, '')+'\',\''+attachmentCount+'\');" class="upload" disabled>' +
                             '<input type="text" class="form-control" style="display: none;" name=\'docFile'+label.replace(/\s+/g, '')+'\'  id=\'newUploadFile'+label.replace(/\s+/g, '')+'\' '+attachmentDataValidationOptional+' '+attachmentDataValidation+' field-name="'+label+'" disabled>';
                            }else{
                    		 input =  '<input type="file" id=\'newUploadBtn'+label.replace(/\s+/g, '')+'\'  onchange="uploadFeedbackAttachments(\''+label.replace(/\s+/g, '')+'\',\''+attachmentCount+'\');" class="upload">' +
                             '<input type="text" class="form-control" style="display: none;" name=\'docFile'+label.replace(/\s+/g, '')+'\'  id=\'newUploadFile'+label.replace(/\s+/g, '')+'\' '+attachmentDataValidationOptional+' '+attachmentDataValidation+' field-name="'+label+'"  >';
                            }
                    	 return '' +
                         '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
                         '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                         '<label for="text-' + uniqueID + '"></label>' +
                         '<div class="clearfix mt"></div>'+
                         input +
                         '<a download="" class="glyphicon glyphicon-download-alt" style="display: none;" id=\'download-'+label.replace(/\s+/g, '')+'\' title="Download">&nbsp;<span id=\'attachmentTitle-'+label.replace(/\s+/g, '')+'\'></span></a>'+
                         '</div>'+
                         '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+labelAndAttemptsMap.get(label)+'</strong> Attempt(s)</div>' +
                         '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';
                 }
                 
             }
             
             
             
             var d = new Date();
             var year = d.getFullYear() - 10;
             d.setFullYear(year);
             $(".DatePicker").datepicker({
            	 dateFormat: finalDateFormatScript,
            	 changeMonth: true,
                 changeYear: true,
                 maxDate:0,
                 showOn: 'focus',
                 showButtonPanel: true,
                 closeText: 'Clear',
                 onClose : function(selected) {
                	  var event = arguments.callee.caller.caller.arguments[0];
                     if ($(event.delegateTarget).hasClass('ui-datepicker-close')) {
                         $(this).val('');
                     }
                	 $('p.jquery_form_error_message').remove();
                 }
                 
             });
        },
    });
	
}

function getRadioValue(id,name){
	   $('#'+name+"radio").val(id.value); 
} 

function getCheckBoxValue(id,name){
	 var checkList = [];
		 $("input[name='"+name+"']:checked").each( function (i) {
			 checkList[i] = $(this).val();
		});
		 
		 
	if (checkList.length > 0) { 
	  $('#'+name+"checkbox").val(id.value); 
	}else{
	   $('#'+name+"checkbox").val(''); 
	}
}

</script>

<script>

function fillStarValues(i,label,value){
	
	
	if(value != null && value != 'undefined' && value != ''){
		i = value;
	}
	$("#starValue-"+label).val(i);
	
	for(var k=1; k<=i; k++){
		$("#star-select"+k+label).removeClass('text-muted').addClass('textWarning');
	}
	
	for(var k=i+1; k<=6; k++){
		$("#star-select"+k+label).removeClass('textWarning').addClass('text-muted');
	}
}
function evaluatePreSlider(uniqueID){
	var slider = document.getElementById("text-"+uniqueID).value;
	$("#demo"+uniqueID).html(slider);
	$("#scaleValue"+uniqueID).val(slider);
	
}

function evaluatePostSlider(label, value){
	var slider = 0;
	if(value == null){
	slider = document.getElementById("scale-"+label).value;
	}else{
		slider = value;
	}
	$("#demo-"+label).html(slider);
	$("#scaleValue-"+label).val(slider);
	$("#scale-"+label).val(slider);
}

</script>
<script>
function uploadFeedbackAttachments(id,attachmentCount){
	questionnaireFeedbackFileUpload("newUploadBtn"+id, "QuestionnaireFeedbackAttachments", "questionnaireTemplate_form","uploadedFileInfo",attachmentCount);
	$('#newUploadFile'+id).val($("#newUploadBtn"+id).val().replace(/C:\\fakepath\\/i, ''));
	
}
function questionnaireFeedbackFileUpload(id, type, formId, name,attachmentCount,orgId, orgLocationId,url) {
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
</script>
<script>
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
			     		/* attempts = labelAndAttemptsMap.get(fieldLabel)-1;
			     		 if(attempts <= 0){
			     			attempts = 0;
			     		 } */
			     		pointsArray.push(points);
			     	  }
		     	  }else{
		     		 attempts = $('#attemptId').html().trim();
			     	  if(inputValue != null && inputValue != ""){
			     		 /* attempts = attempts-1;
			     		 if(attempts <= 0){
			     			 attempts = 0;
			     		 } */
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
	        submittedPoints = calculateTotalPoints(pointsArray);
	        totalPoints = calculateTotalPoints(totalPointsArray);
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
	      	      			+"&workflowStageId="+workflowStageId+"&requirementSubmissionId="+requirementSubmissionId+"&submissionType="+submissionType.get("submissionType"), 
	      	        success: function(result) {
	      	           if (result == null) {
			        	    location.href = "/errorPage";
			        	} else if (result != null) {
			        		submissionsCount++;
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
$('body').on('blur', '#questionnaireTemplate_form', function() {
	$("#questionnaireTemplate_form").showHelpOnFocus();
	$("#questionnaireTemplate_form").validateOnBlur(false, validationSettings);
});

function calculateTotalPoints(pointsArray){
	var totalP = 0;
	var current = 0
	for(var i = 0; i<pointsArray.length ; i++ )
	{
		current = parseInt(pointsArray[i]);
		totalP += current;
	}
	return totalP;
}

</script>
</body>
</html>
