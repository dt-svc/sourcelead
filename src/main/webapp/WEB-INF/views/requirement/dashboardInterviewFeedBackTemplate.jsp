<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style type="text/css">

.choices, .form-control {
 width: 75%;
 }

.choices label {
width: auto !important; padding-right: 15px;
}
.choices input[type="checkbox"], .choices input[type="radio"]{
 margin-right: 10px; position: relative; top: 1px;
}

.required-field {
position: relative;
}
p.jquery_form_error_message {
	width: 100%; position: absolute; bottom: -15px; left: 24%;
    }
</style>

<script type="text/javascript">
window.onload = function () {
	var requirementInterviewid = '${requirementInterviewid}';
	var previousInterviewId = '${afterRequirementInterviewid}';
	var feedbackStatus = '${feedbackStatus}';
		if(requirementInterviewid == previousInterviewId){
		if(feedbackStatus == "N"){
			viewTemplateNo();
		}else{
			viewTemplate();
		}
	}else{
		after();
	}
	
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
	var requirementName = '${requirementName}';
	var applicantName = '${applicantName}';
	$("#remove_user_pop").slideUp(200);
	$(".project_inn_mask").hide();
	var result = "";
	
	result = result + '<h3>Interview Feedback</h3>';
	result = result + '<div class="row clearfix">';
/* 	result = result + '<div col-md-6 ">';
		result = result + '<div class="col-md-6">';
		result = result + '<label class="col-md-4">Job Name</label>';
		result = result + '<span class="col-md-8">'+requirementName+'</span>';
		result = result + '</div>';
	
		result = result + '<div class="col-md-6">';
		result = result + '<label class="col-md-4">Applicant Name</label>';
		result = result + '<span class="col-md-8">'+applicantName+'</span>';
		result = result + '</div>';
	result = result + '</div>&nbsp;&nbsp;'; */
	result = result + '<form id="cancelFunction_form" name="cancelFunction_form" enctype="multipart/form-data" method="post">';
	result = result + '<div class=" clearfix mp">';
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
		    	     result = result + '<div class="row clearfix">';
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
	var templateId = '${templateId}';
	var sourceleadUrl ='${sourceleadUrl}';
	var requirementInterviewid = '${requirementInterviewid}';
	var previousInterviewId = '${afterRequirementInterviewid}';
	var requirementName = '${requirementName}';
	var applicantName = '${applicantName}';
	$("#remove_user_pop").slideUp(200);
	$(".project_inn_mask").hide();
	$("#interviewTemplateFormDiv").show();
	if(requirementInterviewid = previousInterviewId){
		viewTemplate()
	}else{
		generateInterviewFeedbackForm1(sourceleadUrl,templateId)
		var result = "";
		
		
		result = result + '<h3>Interview Feedback</h3>';
		
		/* result = result + '<div class="viewTemplate feedbackTemplate col-md-12">';
			result = result + '<div class="sjfb-field sjfb-text">';
			result = result + '<label>Job Name</label>';
			result = result + '<span>'+requirementName+'</span>';
			result = result + '</div>';

			result = result + '<div class="sjfb-field sjfb-text">';
			result = result + '<label>Applicant Name</label>';
			result = result + '<span>'+applicantName+'</span>';
			result = result + '</div>';
		result = result + '</div>'; */
		
		result = result + '<form id="interviewTemplate_form" name="interviewTemplate_form" enctype="multipart/form-data" method="post">';
		result = result + '<div class="form-horizontal clearfix">';
		result = result + '<div id="interviewsjfb-fields" class="viewTemplate view_templateDiv">';
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
	
	
}
</script>


<script>
function saveInterviewTemplate(){
	if ($('#interviewTemplate_form').validate(false, validationSettings)){ 
	      var templateId = '${templateId}';
	  	  var requirementInterviewid = '${requirementInterviewid}';
	      var sourceleadUrl ='${sourceleadUrl}';
	      var frm = $('#interviewTemplate_form').serializeFormJSON();
	      var jsonData = JSON.stringify(frm);
	      var form = $('#interviewTemplate_form')[0];
	      var formData = new FormData(form); 
	      //Loop through fields and save field data to array
	      var fields = [];
	      $('.sjfb-field').each(function() { 
	      	  var $this = $(this); 
	      	  var divId = $(this).attr('id');  
	      	  var fieldLabel = $this.find('label:first').text().replace("*","");
	      	  var inputValue = $this.find('input[type="radio"]:checked').val();
	      	  
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
		      		inputValue = $this.find('textarea').val();
		      	  }
	     	      if(inputValue == null || inputValue == '' || inputValue == 'undefined'){
	     	    	 inputValue = $this.find('select').val();
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
	      	  fields.push({
	                label: fieldLabel,
	                id :id,
	                name :name,
	                value: inputValue,
	            });
	       });
	      var con = JSON.stringify(fields);
	      formData.append("jsonStringData",con);
	      formData.append("jsonData",jsonData); 
	       $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  	     $.ajax({
		    	type: "POST",
		    	data: formData,
		    	  processData: false,
	    	        contentType:false,
	 	    	url : sourceleadUrl+"/ws/savePublicInterviewFeddbackTemlate?"+tokenParamName+"="+tokenValue+"&templateId="+templateId+"&requirementInterviewid="+requirementInterviewid,
		        success: function(result) {
		        	$("#interviewTemplateFormDiv").hide();
		        	viewTemplate(sourceleadUrl,templateId)
		        },
		    }); 
	      return true; 
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

<section class="content-wraper clearfix">
  <!-- Header Area -->
   <div id="interviewTemplateFormDiv" class="container container-wraper containerWrraper positionRelative interFeedback" style="display: none;"> </div> 
   <div id="cancelFeedbackDiv" class="container container-wraper containerWrraper positionRelative interFeedback" style="display: none;"> </div>
   <div id="viewInterviewTemplateList" class="container interFeedback"></div>
   <div id="viewInterviewTemplateNo" class="container interFeedback"></div>
 </section>  


<script>

function generateInterviewFeedbackForm1(sourceleadUrl,templateId) {
    //empty out the preview area
    $("#interviewsjfb-fields").empty();
  
    $.ajax({
    	type: "GET",
        url:"/getInterviewrsViewFeedbackList?templateId="+templateId, 
        success: function(data) {
        	 var data = eval(data);
        	 
        	 if (data) {
                 //go through each saved field object and render the form HTML
                 $.each( data, function( k, v ) {

                     var fieldType = v['type'];

                     //Add the field
                     $('#interviewsjfb-fields').append(addFieldHTML(fieldType,v['req'],v['label'],sourceleadUrl));
                     var $currentField = $('#interviewsjfb-fields .sjfb-field').last();
                     
                     var reqSymbol = '';
                	 if(v['req'] == '1'){
                		reqSymbol = "<span class='text-danger paddingLeft'> *</span>";
                	 }
                     
                     //Add the label
                     if(v['label'] != 'location' ){
                    	 $currentField.find('label').html(v['label']+reqSymbol);
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
                         if (fieldType == 'text') { $currentField.find("input").addClass('form-control required-choice') }
                         else if (fieldType == 'calendar') { $currentField.find("calandar").addClass('required-choice') }
                         else if (fieldType == 'autocomplete') { $currentField.find("autocomplete").addClass('form-control required-choice') }
                         else if (fieldType == 'textarea') { $currentField.find("textarea").addClass('required-choice form-control') }
                         else if (fieldType == 'select') { $currentField.find("select").addClass('required-choice form-control') }
                         else if (fieldType == 'radio') { $currentField.find("input").addClass('required-choice') }
                         $currentField.addClass('required-field');
                     }

                 });
             }
        	 
        	 
        	 
        	  //HTML templates for rendering frontend form fields
             function addFieldHTML(fieldType,required,label,sourceleadUrl) {
            	 
            	 var datavalidation = '';	
            	 var novalidation = '';
            	 
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
                

                 switch (fieldType) {

                     case 'text':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
                             '<label for="text-' + uniqueID + '"></label>' +
                             '<input type="text" '+datavalidation+' '+novalidation+' value="" field-name=\''+label+'\' name=\''+label.replace(/\s+/g, '')+'\' id="text-' + uniqueID + '">' +
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
                             '<select id="select-' + uniqueID + '" '+datavalidation+' '+novalidation+' name=\''+label.replace(/\s+/g, '')+'\' field-name=\''+label+'\' class="choices choices-select"></select>' +
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
                    	 
                    	 
                 }
             }
        },
    });
    
}
</script>


<script type="text/javascript">
	function viewTemplate(sourceleadUrl,templateId) {
		var templateId = '${templateId}';
		var sourceleadUrl ='${sourceleadUrl}';
		var requirementInterviewid = '${requirementInterviewid}';
		var orgLocations = '${orgLocations}';
		var requirementName = '${requirementName}';
		var applicantName = '${applicantName}';
		
		$("#viewInterviewTemplateNo").hide();
		$("#interviewTemplateFormDiv").hide();
		$.ajax({
	    	type: "GET",
	    	url: sourceleadUrl+"/ws/getPublicInterviewFeedbackTemplate?templateId="+templateId+"&requirementInterviewid="+requirementInterviewid, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	 var submissionDetails = "";
		    	 var details = eval(json.interviewTemplateJsonString);
		    	   $.each(details, function( k, v ) {
		    		   var value = '';
		    		   if(v['value'] == null || v['value'] == '' || v['value'] == 'undefined'){
		    			   var value = 'NO DATA';
		    		   }else{
		    			   var value = v['value'];
		    		   }
		    		   submissionDetails = submissionDetails + '<div class="clearfix formgroup">';
		    		   submissionDetails = submissionDetails + '<div class="col-md-5">'+v['label'];
		    		   submissionDetails = submissionDetails + '</div>';
		    		   submissionDetails = submissionDetails +'<div class="col-md-7">'+value;
		    		   submissionDetails = submissionDetails + '</div>';
		    		   submissionDetails = submissionDetails + '<div class="clearfix">';
		    		   submissionDetails = submissionDetails + '</div>';
		    		   submissionDetails = submissionDetails + '</div>';
		    	   }); 
		    	   
		    	     var result = "";
		    	     result = result + '<div class="clearfix row">';
		  			 result = result + '<div class="release-jobs submissionDetails">';
				     result = result + '<div class="clearfix sub-details">';
				     result = result + ' <h2 class="active clearfix intfd_dashbord"><span class="col-md-3">Interview Feedback Details</span> <span class="col-md-4">Job Name:<lable class="paddingLeft" title="'+requirementName+'">'+requirementName+'</label></span><span class="col-md-4">Appli. Name:<lable class="paddingLeft" title="'+applicantName+'">'+applicantName+'</label></span></h2>';
				     result = result + ' <div class="content clearfix">'; 
				     result = result + submissionDetails;
				     result = result + ' </div>';
	       	         result = result +"<h5><a class='btn btn-primary btn-sm' href='${SourceLeadWeb_sourceleadUrl}/dashboard/"+orgLocations+"'> <span title='Back'><i class='fa fa-angle-double-left paddingRight'></i>Back</span> </a> </h5>";
				     result = result + ' </div>';
	                 result = result + ' </div>';
                     result = result + ' </div>'; 
                   
                   $("#viewInterviewTemplateList").empty();
                   $("#viewInterviewTemplateList").append(result);
	        }
		});
	}
</script>
<script>
function dashBoard(sourceleadUrl) {
	
location.replace(sourceleadUrl+"/dashboard");
}
</script>
<script>
function generateViewpublicpage(sourceleadUrl,templateId) {
    //empty out the preview area
    $("#interviewsjfb-fields").empty();
    $.ajax({
    	type: "GET",
        url: sourceleadUrl+"/getPublicInterviewFeedbackTemplate?templateId="+templateId, 
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
</script>




