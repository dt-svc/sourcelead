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
<title>Welcome to Source Lead :: Interview Template</title>
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

<script type="text/javascript">
var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';

var atsUrl = '${SourceLeadWeb_atsUrl}';
</script>
<style type="text/css">

.choices label {
width: auto !important; padding-right: 15px;
}
.choices input[type="checkbox"], .choices input[type="radio"]{
 margin-right: 10px; position: relative; top: 1px;
}
</style>

<script type="text/javascript">
window.onload = function () {
		after();
	$("#interviewTemplateFormDiv").hide();
 };
</script>
<div id ="popUp"></div>
<script type="text/javascript">
function after(){
	
	var result = "";
	result = result +"<div class='project_inn_mask'></div>";
	result = result +"<div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"<p><strong>Applicant attend Interview ?</strong></p>";
	result = result +"<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"<b href='javascript:void(0)'><input type='button' id='deleteCustomField'  value='Yes' onclick='interviewTemplateForm();' style='background-color:#1ab394'/><input class='u_can_color' type='button' onclick='feedback();' value='No'></b>";
	result = result +"</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script type="text/javascript">
function feedback(){
	var requirementInterviewid = '${requirementInterviewid}';
	var previousInterviewId = '${afterRequirementInterviewid}';
	if(requirementInterviewid = previousInterviewId){
		viewTemplateNo();
	}else{
		$("#remove_user_pop").slideUp(200);
		$(".project_inn_mask").hide();
		cancelFunction();
	}
}

</script>
<script type="text/javascript">
function cancelFunction(){
	$("#remove_user_pop").slideUp(200);
	$(".project_inn_mask").hide();
	var result = "";
	result = result + '<form id="cancelFunction_form" name="cancelFunction_form" enctype="multipart/form-data" method="post">';
	result = result + '<h3 class="mt ml">Questionnaire Template</h3>';
	
	result = result + '<div class="row clearfix">';
	result = result + '<div class="col-md-6">';
	result = result + '<label class="col-md-4">Comment <span class="text-danger">*</span></label>';
	result = result + '<div class="col-md-8">';
	result = result + '<textarea rows="2" placeholder="Comment" class="form-control" name="interviewComment" id="interviewComment" field-name="Comment" data-validation="required"></textarea>';
	result = result + '</div>';
	result = result + '</div>';
	
	result = result + '<div class="col-md-6">';
	result = result + '<label class="col-md-4">Selected for Interview</label>';
	result = result + '<div class="col-md-8">';
	result = result + '<label class="radio-inline"><input type="radio" value="Yes" name="feedback">Yes</label>';
	result = result + '<label class="radio-inline"><input type="radio" value="No" name="feedback">No</label>';
	result = result + '</div>';
	result = result + '</div>';
	
	result = result + '</div>'; 
	
	result = result + '<div class="clearfix"></div>';
	result = result + '<div class="form-footer clearfix mt">';
	result = result + '<div class="pull-right mr">';
	result = result + '<button type="button"  class="btn btn-sm btn-primary" onclick="saveFeedbackNo();">Submit</button>';
	//result = result + '<button type="button"  class="btn btn-sm btn-warning ml">Cancel</button>';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>';  
	result = result + '</form>';
	
	$("#cancelFeedbackDiv").empty();
	$("#cancelFeedbackDiv").append(result).show();
	
}
</script>

<script type="text/javascript">
function saveFeedbackNo(){
	if ($('#cancelFunction_form').validate(false, validationSettings)) {
		var templateId = '${templateId}';
		var sourceleadUrl ='${sourceleadUrl}';
		var requirementInterviewid = '${requirementInterviewid}';
		var frm = $('#cancelFunction_form').serializeFormJSON();
		var con = JSON.stringify(frm);
		con = con.replace(/[{}]/g, "");
    	//It will escape all the special characters
	    var jsonData = encodeURIComponent(con);
		$.ajax({
			type : "GET",
			url :sourceleadUrl+"/ws/savePublicInterviewFeddbackTemlateReason?jsonData="+jsonData+"&templateId="+templateId+"&requirementInterviewid="+requirementInterviewid,
			success : function(data) {
				$("#cancelFeedbackDiv").hide();
				viewTemplateNo();
			},
		});
		return true;
	} else {
		return false;
	}
}
$('body').on('blur', '#cancelFunction_form', function() {
	$("#cancelFunction_form").showHelpOnFocus();
	$("#cancelFunction_form").validateOnBlur(false, validationSettings);
});
</script>


<script type="text/javascript">
	function viewTemplateNo() {
		var templateId = '${templateId}';
		var sourceleadUrl ='${sourceleadUrl}';
		var requirementInterviewid = '${requirementInterviewid}';
		$(".project_inn_mask").hide();
		$(".remove_user_pop").hide();
		$("#cancelFeedbackDiv").hide();
		$("#interviewTemplateFormDiv").hide();
		$.ajax({
	    	type: "GET",
	    	url: sourceleadUrl+"/ws/getPublicInterviewFeedbackTemplate?templateId="+templateId+"&requirementInterviewid="+requirementInterviewid, 
	        success: function(data) {
	        	var json = JSON.parse(data);
		        	if(json.comment != null && json.comment != '' && json.comment != 'undefined'){
		        		comment = json.comment;
		        	}else{
		        		comment = "";
		        	}
		        	if(json.interviewStatus != null && json.interviewStatus != '' && json.interviewStatus != 'undefined'){
		        		interviewStatus = json.interviewStatus;
		        	}else{
		        		interviewStatus = "";
		        	}
		        	
		    	     var result = "";
		    	     result = result + '<div class="col-md-8 col-md-offset-2">';
		  			 result = result + '<div class="release-jobs submissionDetails">';
				     result = result + '<div class="clearfix sub-details">';
				     result = result + ' <h2 class="active">Interview Feedback Details</h2>';
				     result = result + ' <div class="content clearfix">'; 
				     result = result +  '&nbsp &nbsp Comment :- &nbsp;'+comment;
				     result = result + ' </div>';
				     result = result + ' <div class="content clearfix">'; 
				     result = result +  '&nbsp &nbsp Selected for Interview :- &nbsp;'+interviewStatus;
				     result = result + ' </div>';
				     result = result + ' </div>';
	                 result = result + ' </div>';
                     result = result + ' </div>'; 
                   
                   $("#viewInterviewTemplateNo").empty();
                   $("#viewInterviewTemplateNo").append(result);
	        }
		});
	}
</script>







<script>
function interviewTemplateForm(){
	var templateUuid = '${templateUuid}';
	var sourceleadUrl ='${sourceleadUrl}';
	$("#remove_user_pop").slideUp(200);
	$(".project_inn_mask").hide();
	$("#interviewTemplateFormDiv").show();
		generateInterviewFeedbackForm1(sourceleadUrl, templateUuid)
		var result = "";
		result = result + '<form id="interviewTemplate_form" name="interviewTemplate_form" enctype="multipart/form-data" method="post">';
		result = result + '<h3 class="mt ml">Questionnaire Template</h3>';
		/* result = result + '<div class="viewTemplate feedbackTemplate col-md-12">';
		result = result + '<div class="sjfb-field sjfb-text">';
		result = result + '<label>Comment <span class="text-danger">*</span></label>';
		result = result + '<textarea rows="2" placeholder="Comment" class="form-control" name="interviewComment" field-name="Comment" data-validation="required"></textarea>';
		result = result + '</div>';
		result = result + '<div class="sjfb-field sjfb-text">';
		result = result + '<label>Selected for Interview</label>';
		result = result + '<span class="radio-inline"><input type="radio" value="Yes" name="feedback">Yes</span>';
		result = result + '<span class="radio-inline"><input type="radio" value="No" name="feedback">No</span>';
		result = result + '</div>';
		result = result + '</div>'; */
		
		result = result + '<div class="form-horizontal clearfix">';
		result = result + '<div id="interviewsjfb-fields" class="viewTemplate view_templateDiv viewtemplate_paragraph">';
		result = result + '</div>';
		result = result + '</div>';
		
		 result = result + '<div class="form-footer clearfix">';
		result = result + '<div class="pull-right mr">';
		result = result + '<button type="button"  class="btn btn-sm btn-primary" onclick="saveInterviewTemplate();">Submit</button>';
		//result = result + '<button type="button"  class="btn btn-sm btn-warning ml">Cancel</button>';
		result = result + '</div>';
		result = result + '</div>';
		result = result + '</div>';  
		result = result + '</form>';
		 
		$("#interviewTemplateFormDiv").empty();
		$("#interviewTemplateFormDiv").append(result);
	
}
</script>

<script>
function saveInterviewTemplate(){
	if ($('#interviewTemplate_form').validate(false, validationSettings)){ 
	      var frm = $('#interviewTemplate_form').serializeFormJSON();
	      //var templateId = '${templateId}';
	      var sourceleadUrl ='${sourceleadUrl}';
	      var frm = $('#sjfb').serializeFormJSON();
	        var con = JSON.stringify(frm);
	        con = con.replace(/[{}]/g, "");
	        var jsonData = encodeURIComponent(con);
	        var isRemoved = '';
	        //Loop through fields and save field data to array
	        var fields = [];
	        var submitTemplate = true;
	        var fieldLabel = '';
	        var fieldhint = '';
	        var fieldDropDown = '';
	        var myArray = new Array();
	        $('.field').each(function() {
	            var $this = $(this);

	            //field type
	            var fieldType = $this.data('type');
	            
	            if(fieldType == 'comments'){
	            	fieldType = 'textarea';
	            }

	            //field label
	            fieldLabel = $this.find('.field-label').val();
	            
	           //field hint
	            fieldhint = $this.find('.field-hint').val(); 
	            
	            //field type dropdown
	            fieldDropDown = $this.find('.field-dropdown').val(); 

	            //field required
	            var fieldReq = $this.hasClass('required') ? 1 : 0;
	            
	            
	            if(fieldType == 'checkbox' || fieldType == 'radio' ||  fieldType == 'select'){
	            	if($this.find('.choices li').length == 0) {
	            		submitTemplate = false;
	            		alertMessage("Provide choice for "+fieldLabel)
	            	}
	            }
	            
	            myArray.push(fieldLabel);

	            //check if this field has choices
	            if($this.find('.choices li').length >= 1) {

	                var choices = [];

	                $this.find('.choices li').each(function() {

	                    var $thisChoice = $(this);

	                    //choice label
	                    var choiceLabel = $thisChoice.find('.choice-label').val();

	                    //choice selected
	                    var choiceSel = $thisChoice.hasClass('selected') ? 1 : 0;

	                    if($thisChoice.find('.choice-label').val() != null && $thisChoice.find('.choice-label').val() != '' && $thisChoice.find('.choice-label').val() != 'undefined'){
		                    choices.push({
		                        label: choiceLabel,
		                        sel: choiceSel
		                    });
	                    }

	                });
	            }
	            
	            fields.push({
	            		type: fieldType,
		                label: fieldLabel,
		                hint: fieldhint,
		                dropdown : fieldDropDown,
		                req: fieldReq,
		                choices: choices,
		                isRemoved : isRemoved,
	            });

	        });
	        
	        for (var i = 0; i < myArray.length; i++) 
	        {
	            for (var j = 0; j < myArray.length; j++) 
	            {
	                if (i != j) 
	                {
	                    if (myArray[i].toLowerCase() == myArray[j].toLowerCase()) 
	                    {
	                    	submitTemplate = false;
	                    	alertMessage(myArray[i]+" label duplicated")
	                    }
	                }
	            }
	        }
	        
	        
	        var frontEndFormHTML = '';
	      /*  var templateId = document.getElementById("templateId").value;
	        if(templateId == 'undefined' || templateId == null || templateId == ''){
	        	templateId = '';
	        } */
	        //Save form to database
	        //Demo doesn't actually save. Download project files for save
	        var con2 = JSON.stringify(fields);
	        var jsonStringData = encodeURIComponent(con2);
	        
	        
	        if(submitTemplate){
	         $( "#template_save_button" ).addClass('button').val('Processing..');
	      	  $.ajax({
	      	    	type: "POST",
	      	        url: sourceleadUrl+"/saveQuestionnaireTemplate?templateId="+templateId+"&jsonData="+jsonData+"&jsonStringData="+jsonStringData, 
	      	        success: function(result) {
	      	           if (result === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (result === "SUCCESS") {
			        		performanceManagement.getFeedbackList();
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
$('body').on('blur', '#interviewTemplate_form', function() {
	$("#interviewTemplate_form").showHelpOnFocus();
	$("#interviewTemplate_form").validateOnBlur(false, validationSettings);
});

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
   <div id="interviewTemplateFormDiv" class="container container-wraper container-publicFBT positionRelative paddingTop55" style="display: none;"> </div> 
   <div id="cancelFeedbackDiv" class="container paddingTop55 container-wraper container-publicFBT positionRelative" style="display: none;"> </div>
   <div id="viewInterviewTemplateList" class="container paddingTop55"></div>
   <div id="viewInterviewTemplateNo" class="container paddingTop55"></div>
   
   
   <!-- <footer class="clearfix stickyfooterDiv">
   <div class="container">
    <div class="pull-left"> @ Copyright : Source Lead, All right reserved </div>
    <div class="pull-right"> Powered by: businessintelli.com </div>
    </div>
  </footer> -->
</div>



<script>

function generateInterviewFeedbackForm1(sourceleadUrl, templateUuid) {
    //empty out the preview area
    $("#interviewsjfb-fields").empty();
    $.ajax({
    	type: "GET",
        url:sourceleadUrl+"/getQuestionnaireTemplateList?templateUuid="+templateUuid, 
        success: function(data) {
        	console.log(data);
        	 var data = eval(data);
        	 
        	 if (data) {
                 //go through each saved field object and render the form HTML
                 $.each( data, function( k, v ) {

                     var fieldType = v['type'];

                     //Add the field
                     $('#interviewsjfb-fields').append(addFieldHTML(fieldType,v['req'],v['label'],v['hint'],v['dropdown'],sourceleadUrl));
                     var $currentField = $('#interviewsjfb-fields .sjfb-field').last();
                     
                     var reqSymbol = '';
                	 if(v['req'] == '1'){
                		reqSymbol = " *";
                	 }
                     
                     //Add the label
                     if(v['label'] != 'location' ){
                    	 $currentField.find('label').text(v['label']+reqSymbol);
                     }
                     
                     //Any choices?
                     if (v['choices']) {
                    	 
                    	 var label = v['label'];
                         var uniqueID = Math.floor(Math.random()*999999)+1;

                         $.each( v['choices'], function( k, v ) {

                             if (fieldType == 'select') {
                                 var selected = v['sel'] ? ' selected' : '';
                                 var choiceHTML = '<option' + selected + '>' + v['label'] + '</option>';
                                 $currentField.find(".choices").append(choiceHTML);
                             }

                             else if (fieldType == 'radio') {
                                 var selected = v['sel'] ? ' checked' : 'checked';
                                 var choiceHTML = '<label><input type="radio" name="'+label+'"' + selected + ' value="' + v['label'] + '">' + v['label'] + '</label>';
                                 $currentField.find(".choices").append(choiceHTML);
                             }

                             else if (fieldType == 'checkbox') {
                                 var selected = v['sel'] ? ' checked' : 'checked';
                                 var choiceHTML = '<label><input type="checkbox" name="'+label+'"' + selected + ' value="' + v['label'] + '">' + v['label'] + '</label>';
                                 $currentField.find(".choices").append(choiceHTML);
                             }
                             

                         });
                     }

                     //Is it required?
                     if (v['req']) {
                         if (fieldType == 'text') { $currentField.find("input").addClass('required-choice') }
                         else if (fieldType == 'calendar') { $currentField.find("calandar").addClass('required-choice') }
                         else if (fieldType == 'autocomplete') { $currentField.find("autocomplete").addClass('required-choice') }
                         else if (fieldType == 'textarea') { $currentField.find("textarea").addClass('required-choice') }
                         else if (fieldType == 'select') { $currentField.find("select").addClass('required-choice') }
                         else if (fieldType == 'radio') { $currentField.find("input").addClass('required-choice') }
                         else if (fieldType == 'star') { $currentField.find("star").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'scale') { $currentField.find("scale").prop('required',true).addClass('required-choice') }
                         $currentField.addClass('required-field');
                     }

                 });
             }
        	 
        	 
        	 
        	  //HTML templates for rendering frontend form fields
             function addFieldHTML(fieldType,required,label,hint,dropdown,sourceleadUrl) {
            	 
            	 var datavalidation = '';	
            	 var novalidation = '';
            	 var onBlurEvent = '';
            	 var placeHolder = '';
            	 
        		 if(required == 1){
        			 
        			 datavalidation = 'data-validation="required"';
        		 }else{
        			
        			 novalidation = 'data-validation-optional="true"';
        		 }
        		 
        		
        		 
            	 if(label == 'location'){
            		 var reqSymbol = ''
            		 var datavalidation = '';	 
            		 if(required == 1){
            			 reqSymbol = '<span>&nbsp;*</span>'
            		 }
            		 
            		 var countryUniqueID = Math.floor(Math.random()*999999)+1;
            		 var stateUniqueID = Math.floor(Math.random()*999999)+1;
            		 var cityUniqueID = Math.floor(Math.random()*999999)+1;
            	 }
            	 else{
            		 var uniqueID = Math.floor(Math.random()*999999)+1;
            	 }
            	 
            	 if(fieldType == 'select'){
            		 options = '<option value="">--Select Rating--</option>';
        		 }
            	 if(fieldType == "star"){
            		 var StarHtml = '';
            		 for(var i=1; i<=dropdown; i++){
            			 StarHtml = StarHtml+'<li class="fa fa-star cursorPointer text-muted"  onclick="fillStarValues('+i+','+uniqueID+')" id="star-select'+i+uniqueID+'"></li>';
  					}
            	 }
            	 
                 switch (fieldType) {
                     case 'text':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
                             '<label for="text-' + uniqueID + '"></label>' +
                             '<input type="text" '+datavalidation+'  '+novalidation+' value="" field-name=\''+label+'\' name=\''+label.replace(/\s+/g, '')+'\' id="text-' + uniqueID + '">' +
                             '</div>';

                     case 'calendar':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label for="calandar-' + uniqueID + '"></label>' +
                             '<input type="text" id="text-' + uniqueID + '">' +
                             '</div>';
                             
                     case 'textarea':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-textarea">' +
                             '<label for="textarea-' + uniqueID + '"></label>' +
                             '<textarea  rows="2" '+datavalidation+' '+novalidation+' name=\''+label.replace(/\s+/g, '')+'\' field-name=\''+label+'\' id="textarea-' + uniqueID + '"></textarea>' +
                             '</div>';

                     case 'select':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-select">' +
                             '<label for="select-' + uniqueID + '"></label>' +
                             '<select id="select-' + uniqueID + '" '+datavalidation+' '+novalidation+' name=\''+label.replace(/\s+/g, '')+'\' field-name=\''+label+'\' class="choices choices-select">'+options+'</select>' +
                             '</div>';

                     case 'radio':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '"  class="sjfb-field sjfb-radio">' +
                             '<label for="radio-' + uniqueID + '"></label>' +
                             '<div class="choices choices-radio"></div>' +
                             '</div>';

                     case 'checkbox':
                         return '' +
                             '<div id="sjfb-checkbox-' + uniqueID + '" class="sjfb-field sjfb-checkbox">' +
                             '<label for="checkbox-' + uniqueID + '"></label>' +
                             '<div class="choices choices-checkbox"></div>' +
                             '</div>';
                             
                     case 'star':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label for="text-' + uniqueID + '"></label>' +
                             '<input type="hidden" id="starValue'+ uniqueID +'"></span>' +
                             '<ul class="list-unstyled list-inline text-muted" id="starRating-' + uniqueID + '">' +
                             '<div class="clearfix mt"></div>'+
                             StarHtml+
                             '</ul>' +
                             '</div>'; 
                             
                     case 'scale':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label for="text-' + uniqueID + '"></label>' +
                             '<div class="slidecontainer mt clearfix">' +
                             '<span id="demo'+ uniqueID +'" class="col-md-4">0</span>' +
                             '<input type="hidden" id="scaleValue'+ uniqueID +'"></span>' +
                             '<input type="range" min="0" max="'+dropdown+'" value="0" onmouseup="evaluateSlider('+uniqueID+');" onclick="evaluateSlider('+uniqueID+')"  id="text-' + uniqueID + '" class="rangeslider">' +
                             '</div>'+
                             '</div>';
                    	 
                    	 
                 }
             }
        },
    });
    
}
</script>





<!-- <script>
function generateViewpublicpage(sourceleadUrl,templateId) {
    //empty out the preview area
    $("#interviewsjfb-fields").empty();
    $.ajax({
    	type: "GET",
        url: sourceleadUrl+"/ws/getPublicInterviewFeedbackTemplate?templateId="+templateId, 
        success: function(data) {
        	 var data = eval(data);
        	 
        	 if (data) {
                 //go through each saved field object and render the form HTML
                 $.each( data, function( k, v ) {

                     var fieldType = v['type'];

                     //Add the field
                     $('#interviewsjfb-fields').append(addFieldHTML(fieldType,v['req'],v['label']));
                     var $currentField = $('#interviewsjfb-fields .sjfb-field').last();
                     
                     var reqSymbol = '';
                	 if(v['req'] == '1'){
                		reqSymbol = " *";
                	 }
                     
                     //Add the label
                     if(v['label'] != 'location' ){
                    	 $currentField.find('label').text(v['label']+reqSymbol);
                     }
                     
                     
                     //Any choices?
                     if (v['choices']) {

                         var uniqueID = Math.floor(Math.random()*999999)+1;

                         $.each( v['choices'], function( k, v ) {

                             if (fieldType == 'select') {
                                 var selected = v['sel'] ? ' selected' : '';
                                 var choiceHTML = '<option' + selected + '>' + v['label'] + '</option>';
                                 $currentField.find(".choices").append(choiceHTML);
                             }

                             else if (fieldType == 'radio') {
                                 var selected = v['sel'] ? ' checked' : '';
                                 var choiceHTML = '<label><input type="radio" name="radio-' + uniqueID + '"' + selected + ' value="' + v['label'] + '">' + v['label'] + '</label>';
                                 $currentField.find(".choices").append(choiceHTML);
                             }

                             else if (fieldType == 'checkbox') {
                                 var selected = v['sel'] ? ' checked' : '';
                                 var choiceHTML = '<label><input type="checkbox" name="checkbox-' + uniqueID + '[]"' + selected + ' value="' + v['label'] + '">' + v['label'] + '</label>';
                                 $currentField.find(".choices").append(choiceHTML);
                             }

                         });
                     }

                     //Is it required?
                     if (v['req']) {
                         if (fieldType == 'text') { $currentField.find("input").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'calendar') { $currentField.find("calandar").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'autocomplete') { $currentField.find("autocomplete").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'textarea') { $currentField.find("textarea").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'select') { $currentField.find("select").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'radio') { $currentField.find("input").prop('required',true).addClass('required-choice') }
                         $currentField.addClass('required-field');
                     }

                 });
             }
        	 
        	 
        	  //HTML templates for rendering frontend form fields
             function addFieldHTML(fieldType,required,label) {
            	 if(label == 'location'){
            		 var reqSymbol = ''
            		 if(required == 1){
            			 reqSymbol = '<span>&nbsp;*</span>'
            		 }
            		 var countryUniqueID = Math.floor(Math.random()*999999)+1;
            		 var stateUniqueID = Math.floor(Math.random()*999999)+1;
            		 var cityUniqueID = Math.floor(Math.random()*999999)+1;
            	 }
            	 else{
            		 var uniqueID = Math.floor(Math.random()*999999)+1;
            	 }

                 switch (fieldType) {

                     case 'text':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
                             '<label for="text-' + uniqueID + '"></label>' +
                             '<input type="text" id="text-' + uniqueID + '">' +
                             '</div>';

                     case 'textarea':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-textarea">' +
                             '<label for="textarea-' + uniqueID + '"></label>' +
                             '<textarea rows="3" id="textarea-' + uniqueID + '"></textarea>' +
                             '</div>';

                     case 'select':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-select">' +
                             '<label for="select-' + uniqueID + '"></label>' +
                             '<select id="select-' + uniqueID + '" class="choices choices-select"></select>' +
                             '</div>';

                     case 'radio':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-radio">' +
                             '<label></label>' +
                             '<div class="choices choices-radio"></div>' +
                             '</div>';

                     case 'checkbox':
                         return '' +
                             '<div id="sjfb-checkbox-' + uniqueID + '" class="sjfb-field sjfb-checkbox">' +
                             '<label class="sjfb-label"></label>' +
                             '<div class="choices choices-checkbox"></div>' +
                             '</div>';

                 }
             }
        },
    });
    
    
}
</script> -->
<script>
function fillStarValues(i,uniqueID){
	
	$("#starValue"+uniqueID).val(i);
	
	for(var k=1; k<=i; k++){
		$("#star-select"+k+uniqueID).removeClass('text-muted').addClass('textWarning');
	}
	
	for(var k=i+1; k<=6; k++){
		$("#star-select"+k+uniqueID).removeClass('textWarning').addClass('text-muted');
	}
}

function evaluateSlider(uniqueID){
	var slider = document.getElementById("text-"+uniqueID).value;
	$("#demo"+uniqueID).html(slider);
	$("#scaleValue"+uniqueID).val(slider);
	
}
</script>
</body>
</html>
