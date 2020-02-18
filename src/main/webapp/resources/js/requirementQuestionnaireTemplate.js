
var qt = new RequirementQuestionnaireTemplate();
var timer;
var submissionsCount = 1;
var questionnaireMessage = "";
var labelAndAttemptsMap = new Map();
var savedQuestionnaireUuid = new Map();
var submissionType = new Map();
var infiniteAttemptsMap = new Map();

function RequirementQuestionnaireTemplate(){
	
	this.questionnaireFillingCheck = function(minutes, seconds){
		var result = "";
		result = result +"<div class='project_inn_mask'></div>";
		result = result +"<div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"<p><strong>Do You Want To Initiate / Continue The Timer And Start Filling The Questionnaire ?</strong></p>";
		result = result +"<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"<b href='javascript:void(0)'><input type='button' id='deleteCustomField'  value='Yes' onclick='qt.saveQuestionnareWithTimeLimit("+minutes+", "+seconds+");' style='background-color:#1ab394'/><input class='u_can_color' type='button' onclick='qt.cancelQuestionnaireFillingCheck();' value='No'></b>";
		result = result +"</div>";
		$("#popUp").empty().append(result);
		$("#remove_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
		
	}

	this.cancelQuestionnaireFillingCheck = function(){
		$("#remove_user_pop").slideUp(200);
		$(".project_inn_mask").hide();
		$("#questionnaireTemplateFormDiv").hide();
		$("#cancelFeedbackDiv").show();
	}

	this.saveQuestionnareWithTimeLimit = function(minutes, seconds){
		$("#remove_user_pop").slideUp(200);
		$(".project_inn_mask").hide();
		submissionType.set("submissionType", autoSubmission);
		$.ajax({
	    	type: "GET",
	        url:sourceleadUrl+"/saveQuestionnaireTemplateForSubmission?questionnaireTemplateUuid="+templateUuid+"&workflowStageUuid="+workflowStageUuid+"&requirementSubmissionId="+requirementSubmissionId+"&submissionType="+submissionType.get("submissionType"), 
	        success: function(data) {
	        	savedQuestionnaireUuid.set("savedQuestionnaireUuid", data.uuid);
	        	$("#questionnaireTemplateFormDiv").show();
	        	qt.updateFeedbackTimerPerSecond(templateUuid ,workflowStageUuid, requirementSubmissionId, data.timeLimitMinutes, data.timeLimitSeconds)
	        }
		});

	}
	
	this.questionnaireTemplateForm = function(){
		
		$("#questionnaireTemplateFormDiv").show();
		qt.generateQuestionnaireForm1(sourceleadUrl, templateUuid)
			var result = "";
			result = result + '<form id="questionnaireTemplate_form" name="questionnaireTemplate_form" enctype="multipart/form-data" method="post">';
			result = result + '<div class="row"><h3 class="col-md-3">Questionnaire Template</h3> <div class="col-md-9" id = "timerDiv"><i class="fa fa-clock-o mr10"></i><label class="textSuccess textBold" id="countdownTimerLbl">Starting</label></div></div>';
			result = result + "";
			
			
			result = result + '<div class="form-horizontal clearfix">';
			result = result + '<div id="questionnairesjfb-fields" class="viewTemplate view_templateDiv viewtemplate_paragraph questionary_template">';
			result = result + '</div>';
			result = result + '</div>';
			
			 result = result + '<div class="form-footer clearfix mb">';
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

	this.updateFeedbackQuestionnaireTimer = function(questTemplUuid,stageId,submissionId,minutes,seconds, distance){
		$.ajax({
	    	type: "POST",
	        url:sourceleadUrl+"/updateFeedbackQuestionnaireTimer?questTemplUuid="+questTemplUuid+"&stageId="+stageId+"&submissionId="+submissionId+"&minutes="+minutes+"&seconds="+seconds, 
	        success: function(response) {
	        	if(response == "Success"){
	        		//console.log("Successfully updated time in table..............>");
	        	}
	        	if(response == "Failed"){
	        		//console.log("Failed to updated in table......................>");
	        	}
	        }
	      });
	}
	
	
	this.updateFeedbackTimerPerSecond = function(questUUid ,stageId, submissionId,minutes, seconds) {
		var end = new Date();
		end.setMinutes(end.getMinutes() + minutes); 
		end.setSeconds(end.getSeconds() + seconds);
		var _second = 1000;
		var _minute = _second * 60;
		var _hour = _minute * 60;
		var _day = _hour * 24;
		 var timerDisplayText= "";
		function showRemaining() {
			var now = new Date();
			var distance = end - now;
			if (distance < 0) {
				clearInterval(window.timer);
				$('#countdownTimerLbl').text('Form Auto Submitted Successfully!');
				$("#submitButton").addClass("disabled");
				$('#countdownTimerLbl').toggleClass("textSuccess textBold");
				saveAutoSubmitQuestionnaireForm();
				return;
			}
			var days = Math.floor(distance / _day);
			var hours = Math.floor((distance % _day) / _hour);
			var minutes = Math.floor((distance % _hour) / _minute);
			var seconds = Math.floor((distance % _minute) / _second);
			if (minutes == 2 && seconds == 0){
				$('#countdownTimerLbl').toggleClass("textSuccess colorRed");//  Two minutes alert
			}
			     // time display label.
	        minutes = minutes < 10 ? "0" + minutes : minutes;
	        seconds = seconds < 10 ? "0" + seconds : seconds;
	        
			timerDisplayText=  minutes + ' mins : '+seconds + ' sec';
			$('#countdownTimerLbl').text(timerDisplayText);
			// each second we are updating timer in table
			
			// uncomment below function for updating time in back end.
			                                 //questUUid, stageId, submissionId, minutes,seconds
			qt.updateFeedbackQuestionnaireTimer(questUUid,stageId,submissionId ,minutes,seconds, distance); 
		}
		timer = setInterval(showRemaining, 1000);
	}

	
	

	this.calculateTotalPoints = function(pointsArray){
		var totalP = 0;
		var current = 0;
		for(var i = 0; i<pointsArray.length ; i++ )
		{
			current = parseInt(pointsArray[i]);
			totalP += current;
		}
		return totalP;
	}

	this.checkForInfiniteAttempts = function(){
		var result = false;
		for (var [key, value] of infiniteAttemptsMap) {
			if (value == true){
				result = true;
				break;
			}else{
				result = false;
			}
		}
		return result;
	}
	
	this.checkTotalAttemptsAndDisableSubmit = function(attemptsArray){
		var booleanCheck = qt.checkForInfiniteAttempts();
		var totalAttempts = 0;
		var current = 0;
		for(var i = 0; i<attemptsArray.length ; i++ )
		{
			current = parseInt(attemptsArray[i]);
			totalAttempts += current;
		}
		if(totalAttempts == 0 && booleanCheck != true){
			console.log("timer "+timer);
			clearInterval(timer);
			//$('#countdownTimerLbl').text('Form Submitted Successfully!');
			$('#countdownTimerLbl').toggleClass("textSuccess textBold");
			questionnaireMessage = 'Form Submitted Successfully!';
			qt.showQuestionnaireAlert(questionnaireMessage);
		}
	}

	this.showQuestionnaireAlert = function(questionnaireMessage){
		$("#submitButton").addClass("disabled");
		$(".project_inn_mask").remove();
		var result = "";
		result = result +" <div class='project_inn_mask'></div>";
		result = result +" <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +" <div class='modal-body'><p id='popupMsg'></p></div>";
		result = result +" <div class='clearfix'><input class='u_can_color width100percnt' type='button'  value='Ok' onClick='qt.cancelWorkflowPopup();'></div>";
		result = result +" </div>";
		$("#popUp").empty().append(result);
		$("#popupMsg").empty().append("<strong>"+questionnaireMessage+"</strong>");
		$("#remove_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
	this.cancelWorkflowPopup = function(){
		$("#remove_user_pop").fadeOut(200);
		$(".project_inn_mask").fadeOut(200);
	}
	

	this.generateQuestionnaireForm1 = function(sourceleadUrl, templateUuid) {
		var attemptsArray = new Array();
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
		  }else{
			  finalDateFormatScript = 'dd-mm-yy';
		  }
	    $.ajax({
	    	type: "GET",
	        url:sourceleadUrl+"/getQuestionnaireTemplateList?templateUuid="+templateUuid+"&workflowStageUuid="+workflowStageUuid+"&submissionId="+requirementSubmissionId, 
	        success: function(result) {
	        	if(result != null && result.jsonString != 'false'){
	        	if(result.timeLimitMinutes != null && result.timeLimitSeconds != null){
	        		$("#timerDiv").show();
	        		if(result.timeMinutesRemained == null && result.timeSecondsRemained == null){
	        			$("#questionnaireTemplateFormDiv").hide();
	        			qt.questionnaireFillingCheck(result.timeLimitMinutes, result.timeLimitSeconds);
	            	}else{
	            		qt.updateFeedbackTimerPerSecond(templateUuid, workflowStageUuid, requirementSubmissionId, result.timeMinutesRemained, result.timeSecondsRemained);
	        		}
	        		}else{
	        			$("#timerDiv").hide();
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
	        			 attemptsArray.push(v['attempts']);
	        		 });
	        		 qt.checkTotalAttemptsAndDisableSubmit(attemptsArray);
	        	}
	        	else{
	        		 $.each( data, function( k, v ) {
	        			 labelAndAttemptsMap.set(v['label'], v['attempt']);
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
	                     $('#questionnairesjfb-fields').append(addSavedFieldHTML1(fieldType,v['req'],v['label'],v['hint'],v['dropdown'],v['points'],v['attempt'],attachmentCount));
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
	                                             
	                                             var choiceHTML = '<span><input type="radio" ' + selected + ' name=\''+label.replace(/\s+/g, '')+'\'  onclick="qt.getRadioValue(this,\''+label.replace(/[^A-Z0-9]+/ig, '')+'\');"  value="' + v1['label'] + '">' + v1['label'] + '</span>';
	                                             if(required == 1 && flag == 0){
	                                            	 choiceHTML = choiceHTML+'<input type="hidden"  id="'+labelTitle.replace(/[^A-Z0-9]+/ig, '')+'radio" data-validation="required" field-name="'+labelTitle+'">';
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
	                                             
	                                             var choiceHTML = '<span class="inlineBlock mr"><input type="checkbox" ' + selected + ' name=\''+label.replace(/\s+/g, '')+'\' onclick="qt.getCheckBoxValue(this,\''+label.replace(/[^A-Z0-9]+/ig, '')+'\')"  value="' + v1['label'] + '">' + v1['label'] + '</span>';
	                                             if(required == 1 && flag == 0){
	                                            	 choiceHTML = choiceHTML+'<input type="hidden"  id="'+labelTitle.replace(/[^A-Z0-9]+/ig, '')+'checkbox" data-validation="required" field-name="'+labelTitle+'" >';
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
	    	                					 $('#textarea-'+ label.replace(/[^A-Z0-9]+/ig, '')).html(v3['value']);
	    	                				 }
	    	                			 }else if(fieldType == 'text'){
	    	                				 if(v3['value'] != null && v3['value'] != '' && v3['value'] != 'undefined'){
	    	                					 $('#text-'+ label.replace(/[^A-Z0-9]+/ig, '')).val(v3['value']);
	    	                				 }
	    	                				
	    	                			 }else if(fieldType == 'calendar'){
	    	                				 if(v3['value'] != null && v3['value'] != '' && v3['value'] != 'undefined'){
	    	                					 $('#calendar-'+ label.replace(/[^A-Z0-9]+/ig, '')).val(v3['value']);
	    	                				 }
	    	                			 }else if(fieldType == 'scale'){
	    	                				 if(v3['value'] != null && v3['value'] != '' && v3['value'] != 'undefined'){
	    	                					 qt.evaluatePostSlider(label.replace(/[^A-Z0-9]+/ig, ''),v3['value'])
	    	                				 }
	    	                			 }
	    	                			 else if(fieldType == 'star'){
	    	                				 if(v3['value'] != null && v3['value'] != '' && v3['value'] != 'undefined'){
	    	                					 qt.fillStarValues(v['dropdown'],label.replace(/[^A-Z0-9]+/ig, ''),v3['value'])
	    	                				 }
	    	                			 }
	    	                			 
	    	                			 else if(fieldType == 'file'){
	    	                					  var fileHtml = '';
	    	                					  fileHtml = fileHtml + "<input type='hidden' name='uploadedFileInfo["+fileCount+"].fileURL'  data-id='newUploadBtn"+label.replace(/[^A-Z0-9]+/ig, '')+"' value='"+v3['value']+"' />";
	    	                					  fileHtml = fileHtml + "<input type='hidden' name='uploadedFileInfo["+fileCount+"].fileName' data-id='newUploadBtn"+label.replace(/[^A-Z0-9]+/ig, '')+"' value='"+v3['name']+"' />";
	    	                					  fileHtml = fileHtml + "<input type='hidden' name='uploadedFileInfo["+fileCount+"].status' data-id='newUploadBtn"+label.replace(/[^A-Z0-9]+/ig, '')+"' value='1' />";
	    	                					  $("#addFeedbackTemplate_form").append(fileHtml);
		                					      fileCount = fileCount+1;
		                					      
		                					      if(v3['value'] != null && v3['value'] != '' && v3['value'] != 'undefined'){
		                					    	  $('#newUploadFile'+label.replace(/[^A-Z0-9]+/ig, '')).val(v3['name']);
			                					      $("#download-"+label.replace(/[^A-Z0-9]+/ig, '')).attr("href",v3['value']);
			                					      $("#attachmentTitle-"+label.replace(/[^A-Z0-9]+/ig, '')).html(v3['name']);
			                					      document.getElementById("download-"+label.replace(/[^A-Z0-9]+/ig, '')).style.display="";
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
	                                     var choiceHTML = '<span class="inlineBlock mr tblminwidth-50 mb10"><input type="radio" name=\''+label.replace(/\s+/g, '')+'\'  onclick="qt.getRadioValue(this,\''+label.replace(/[^A-Z0-9]+/ig, '')+'\');"  value="' + v['label'] + '">' + v['label'] + '</span>';
	                                     if(required == 1 && flag == 0){
	                                    	 choiceHTML = choiceHTML+'<input type="hidden"  id="'+labelTitle.replace(/[^A-Z0-9]+/ig, '')+'radio" data-validation="required" field-name="'+labelTitle+'">';
	                                     }
	                                     $currentField.find(".choices").append(choiceHTML);
	                                 }

	                                 else if (fieldType == 'checkbox') {
	                                     var selected = v['sel'] ? ' checked' : 'checked';
	                                     var choiceHTML = '<span class="inlineBlock mr tblminwidth-50 mb10"><input type="checkbox" name=\''+label.replace(/\s+/g, '')+'\' onclick="qt.getCheckBoxValue(this,\''+label.replace(/[^A-Z0-9]+/ig, '')+'\')"  value="' + v['label'] + '">' + v['label'] + '</span>';
	                                     if(required == 1 && flag == 0){
	                                    	 choiceHTML = choiceHTML+'<input type="hidden"  id="'+labelTitle.replace(/[^A-Z0-9]+/ig, '')+'checkbox" data-validation="required" field-name="'+labelTitle+'" >';
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
	                         else if (fieldType == 'calendar') { $currentField.find("calendar").prop('required',true).addClass('required-choice') }
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
	             function addSavedFieldHTML1(fieldType,required,label,hint,dropdown,points,attempt,attachmentCount) {
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
	                			 StarHtml = StarHtml+'<li class="fa fa-star cursorPointer text-muted"   id="star-select'+i+label.replace(/[^A-Z0-9]+/ig, '')+'"></li>';
	      					}
	                	 }else{
	                		 for(var i=1; i<=dropdown; i++){
	                			 StarHtml = StarHtml+'<li class="fa fa-star cursorPointer text-muted"  onclick="qt.fillStarValues('+i+',\''+label.replace(/[^A-Z0-9]+/ig, '')+'\');"  id="star-select'+i+label.replace(/[^A-Z0-9]+/ig, '')+'"></li>';
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
	                    	 var attemptsDiv = "";
	                    	 var pointsDiv = "";
	                    	 if(labelAndAttemptsMap.get(label) != null){
	                    		 attempt = labelAndAttemptsMap.get(label);
	                    	 }
	                    	 if(attempt != "" && attempt != null && attempt == 0){
	                    		 input = '<input type="text" field-name="'+label+'" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' id=\'text-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' disabled>' ;
	                    		 attemptsDiv = '<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' ;
	                    	 }else if(attempt != "" && attempt != null && attempt > 0){
	                    		 attemptsDiv = '<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' ;
	                    		 input =  '<input type="text" field-name="'+label+'" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' id=\'text-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' >' ;
	                    	 }else{
	                    		 input =  '<input type="text" field-name="'+label+'" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' id=\'text-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' >' ;
	                    		 infiniteAttemptsMap.set(label, true);
	                    	 }
	                    	 if(points != "" && points != null){
	                    		 pointsDiv = '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';
	                    	 }
	                         return '' +
	                         	'<div class="clearfix mb">'+
	                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
	                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
	                             '<label for="text-' + uniqueID + '"></label>' +
	                             input +
	                             '</div>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp' + attemptsDiv + pointsDiv +
	                             '</div>';

	                     case 'textarea':
	                    	 if(attempt == 0){
	                    		 input = '<textarea rows="3" field-name="'+label+'" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' id=\'textarea-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' disabled></textarea>' ;
	                    	 }else{
	                    		 input =  '<textarea rows="3" field-name="'+label+'" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' id=\'textarea-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' ></textarea>' ;
	                    	 }
	                    	 if(labelAndAttemptsMap.get(label) != null){
	                    		 attempt = labelAndAttemptsMap.get(label);
	                    	 }
	                         return '' +
	                         	'<div class="clearfix mb">'+
	                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-textarea">' +
	                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
	                             '<label for="textarea-' + uniqueID + '"></label>' +
	                             input +
	                             '</div>'+
	                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' +
	                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>'+
	                             '</div>';

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
	                         	'<div class="clearfix mb">'+
	                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-select">' +
	                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
	                             '<label for="select-' + uniqueID + '"></label>' +
	                             input +
	                             '</div>'+
	                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' +
	                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>'+
	                             '</div>';

	                     case 'radio':
	                    	 if(labelAndAttemptsMap.get(label) != null){
	                    		 attempt = labelAndAttemptsMap.get(label);
	                    	 }
	                         return '' +
	                         	'<div class="clearfix mb">'+
	                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-radio">' +
	                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
	                             '<label></label>' +
	                             '<div class="clearfix mt"></div>'+
	                             '<div class="choices choices-radio"></div>' +
	                             '</div>'+
	                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' +
	                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>'+
	                             '</div>';

	                     case 'checkbox':
	                    	 if(labelAndAttemptsMap.get(label) != null){
	                    		 attempt = labelAndAttemptsMap.get(label);
	                    	 }
	                         return '' +	
	                         	'<div class="clearfix mb">'+
	                             '<div id="sjfb-checkbox-' + uniqueID + '" class="sjfb-field sjfb-checkbox">' +
	                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
	                             '<label class="sjfb-label"></label>' +
	                             '<div class="clearfix mt"></div>'+
	                             '<div class="choices choices-checkbox"></div>' +
	                             '</div>'+
	                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' +
	                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>'+
	                             '</div>';
	                         

	                     case 'calendar':
	                    	 if(attempt == 0){
	                    		 input = '<input type="text" class="DatePicker dp" '+datavalidation+' field-name="'+label+'" name=\''+label.replace(/\s+/g, '')+'\' id=\'calendar-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' disabled>' ;
	                    	 }else{
	                    		 input =  '<input type="text" class="DatePicker dp" '+datavalidation+' field-name="'+label+'" name=\''+label.replace(/\s+/g, '')+'\' id=\'calendar-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' >' ;
	                    	 }
	                    	 if(labelAndAttemptsMap.get(label) != null){
	                    		 attempt = labelAndAttemptsMap.get(label);
	                    	 }
	                         return '' +
	                         	'<div class="clearfix mb">'+
	                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
	                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
	                             '<label for="calendar-' + uniqueID + '"></label>' +
	                             input +
	                             '</div>'+
	                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' +
	                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>'+
	                             '</div>';
	                     
	                     case 'scale':
	                    	 if(attempt == 0){
	                    		 input = '<input type="hidden" field-type="scale" field-name="'+label+'" '+datavalidation+'   id=\'scaleValue-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' ></span>' +
	                             '<input type="range" min="0" max="'+dropdown+'" value="0" name=\''+label.replace(/\s+/g, '')+'\' onmouseup="qt.evaluatePostSlider(\''+label.replace(/[^A-Z0-9]+/ig, '')+'\');" onclick="qt.evaluatePostSlider(\''+label.replace(/[^A-Z0-9]+/ig, '')+'\');"  id=\'scale-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' class="rangeslider" disabled>' ;
	                    	 }else{
	                    		 input =  '<input type="hidden" field-type="scale" field-name="'+label+'" '+datavalidation+'   id=\'scaleValue-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' ></span>' +
	                             '<input type="range" min="0" max="'+dropdown+'" value="0" name=\''+label.replace(/\s+/g, '')+'\' onmouseup="qt.evaluatePostSlider(\''+label.replace(/[^A-Z0-9]+/ig, '')+'\');" onclick="qt.evaluatePostSlider(\''+label.replace(/[^A-Z0-9]+/ig, '')+'\');"  id=\'scale-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' class="rangeslider">' ;
	                    	 }
	                    	 if(labelAndAttemptsMap.get(label) != null){
	                    		 attempt = labelAndAttemptsMap.get(label);
	                    	 }
	                         return '' +
	                         	'<div class="clearfix mb">'+
	                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
	                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
	                             '<label for="text-' + uniqueID + '"></label>' +
	                             '<div class="slidecontainer mt clearfix">' +
	                             '<span id=\'demo-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' class="col-md-4">0</span>' +
	                             input +
	                             '</div>'+
	                             '</div>'+
	                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' +
	                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>'+
	                             '</div>';
	                         
	                     case 'star':
	                    	 if(attempt == 0){
	                    		 input = '<input type="hidden" field-type="star" field-name="'+label+'" '+datavalidation+' id=\'starValue-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' name=\''+label.replace(/\s+/g, '')+'\'disabled></span>' ;
	                    	 }else{
	                    		 input =  '<input type="hidden" field-type="star" field-name="'+label+'" '+datavalidation+' id=\'starValue-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' name=\''+label.replace(/\s+/g, '')+'\'></span>' ;
	                    	 }
	                    	 if(labelAndAttemptsMap.get(label) != null){
	                    		 attempt = labelAndAttemptsMap.get(label);
	                    	 }
	                         return '' +
	                         	'<div class="clearfix mb">'+
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
	                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>'+
	                             '</div>';
	                         
	                     case 'file':
	                    	 if(attempt == 0){
	                    		 input = '<input type="file" id=\'newUploadBtn'+label.replace(/[^A-Z0-9]+/ig, '')+'\'  onchange="qt.uploadFeedbackAttachments(\''+label.replace(/\s+/g, '')+'\',\''+attachmentCount+'\');" class="upload" disabled>' +
	                             '<input type="text" class="form-control" style="display: none;" name=\'docFile'+label.replace(/\s+/g, '')+'\'  id=\'newUploadFile'+label.replace(/[^A-Z0-9]+/ig, '')+'\' '+attachmentDataValidationOptional+' '+attachmentDataValidation+' field-name="'+label+'" disabled>';
	                            }else{
	                    		 input =  '<input type="file" id=\'newUploadBtn'+label.replace(/[^A-Z0-9]+/ig, '')+'\'  onchange="qt.uploadFeedbackAttachments(\''+label.replace(/[^A-Z0-9]+/ig, '')+'\',\''+attachmentCount+'\');" class="upload">' +
	                             '<input type="text" class="form-control" style="display: none;" name=\'docFile'+label.replace(/\s+/g, '')+'\'  id=\'newUploadFile'+label.replace(/[^A-Z0-9]+/ig, '')+'\' '+attachmentDataValidationOptional+' '+attachmentDataValidation+' field-name="'+label+'"  >';
	                            }
	                    	 if(labelAndAttemptsMap.get(label) != null){
	                    		 attempt = labelAndAttemptsMap.get(label);
	                    	 }
	                    	 return '' +
	                    	 '<div class="clearfix mb">'+
	                         '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
	                         '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
	                         '<label for="text-' + uniqueID + '"></label>' +
	                         '<div class="clearfix mt"></div>'+
	                         input +
	                         '<a download="" class="glyphicon glyphicon-download-alt" style="display: none;" id=\'download-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' title="Download">&nbsp;<span id=\'attachmentTitle-'+label.replace(/[^A-Z0-9]+/ig, '')+'\'></span></a>'+
	                         '</div>'+
	                         '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' +
	                         '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>'+
	                         '</div>';
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
	        
	            
	        	}
	        	else{
	        		questionnaireMessage = "Questionnaire Not Availabe !";
	        		qt.showQuestionnaireAlert(questionnaireMessage);
	             }
	        	},
	    
	    
	        });
	}
	

	this.generateQuestionnaireFeedbackTemplateForm = function(resultUuid, qTemplateUuid, workflowStageUuid, requirementSubmissionId){
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
		  }else{
			  finalDateFormatScript = 'dd-mm-yy';
		  }
		
		 
			var result = "";
			result = result + '<form id="questionnaireTemplate_form" name="questionnaireTemplate_form" enctype="multipart/form-data" method="post">';
			result = result + '<div class="row"><h3 class="col-md-3">Questionnaire Template</h3> <div class="col-md-9" id = "timerDiv"><i class="fa fa-clock-o mr10"></i><label class="textSuccess textBold" id="countdownTimerLbl">Starting</label></div></div>';
			
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
			qt.generateSavedQuestionnaireTemplate(resultUuid, finalDateFormatScript, qTemplateUuid, workflowStageUuid, requirementSubmissionId);
	}

	this.generateSavedQuestionnaireTemplate = function(resultUuid, finalDateFormatScript, qTemplateUuid, workflowStageUuid, requirementSubmissionId){
	 var attemptsArray = new Array();
	    //empty out the preview area
	    $("#questionnairesjfb-fields").empty();
	    $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getQuestionnaireTemplateFeedbackList?templateUuid="+resultUuid, 
	        success: function(result) {
	        	if(result.timeMinutesRemained != null && result.timeSecondsRemained != null){
	        		$("#timerDiv").show();
	        		qt.updateFeedbackTimerPerSecond(qTemplateUuid, workflowStageUuid, requirementSubmissionId, result.timeMinutesRemained, result.timeSecondsRemained);
	        	}else{
	        		$("#timerDiv").hide();
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
	        			 attemptsArray.push(v['attempts']);
	        		 });
	        		 qt.checkTotalAttemptsAndDisableSubmit(attemptsArray);
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
	                     $('#questionnairesjfb-fields').append(addSavedFieldHTML2(fieldType,v['req'],v['label'],v['hint'],v['dropdown'],v['points'],v['attempt'],attachmentCount));
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
	                                             
	                                             var choiceHTML = '<span class="inlineBlock mr"><input type="radio" ' + selected + ' name=\''+label.replace(/\s+/g, '')+'\'  onclick="qt.getRadioValue(this,\''+label.replace(/[^A-Z0-9]+/ig, '')+'\');"  value="' + v1['label'] + '">' + v1['label'] + '</span>';
	                                             if(required == 1 && flag == 0){
	                                            	 choiceHTML = choiceHTML+'<input type="hidden"  id="'+labelTitle.replace(/[^A-Z0-9]+/ig, '')+'radio" data-validation="required" field-name="'+labelTitle+'">';
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
	                                             
	                                             var choiceHTML = '<span class="inlineBlock mr"><input type="checkbox" ' + selected + ' name=\''+label.replace(/\s+/g, '')+'\' onclick="qt.getCheckBoxValue(this,\''+label.replace(/[^A-Z0-9]+/ig, '')+'\')"  value="' + v1['label'] + '">' + v1['label'] + '</span>';
	                                             if(required == 1 && flag == 0){
	                                            	 choiceHTML = choiceHTML+'<input type="hidden"  id="'+labelTitle.replace(/[^A-Z0-9]+/ig, '')+'checkbox" data-validation="required" field-name="'+labelTitle+'" >';
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
	    	                					 $('#textarea-'+ label.replace(/[^A-Z0-9]+/ig, '').replace(/\s+/g, '')).html(v3['value']);
	    	                				 }
	    	                			 }else if(fieldType == 'text'){
	    	                				 if(v3['value'] != null && v3['value'] != '' && v3['value'] != 'undefined'){
	    	                					 $('#text-'+ label.replace(/[^A-Z0-9]+/ig, '').replace(/\s+/g, '')).val(v3['value']);
	    	                				 }
	    	                				
	    	                			 }else if(fieldType == 'calendar'){
	    	                				 if(v3['value'] != null && v3['value'] != '' && v3['value'] != 'undefined'){
	    	                					 $('#calendar-'+ label.replace(/[^A-Z0-9]+/ig, '')).val(v3['value']);
	    	                				 }
	    	                			 }else if(fieldType == 'scale'){
	    	                				 if(v3['value'] != null && v3['value'] != '' && v3['value'] != 'undefined'){
	    	                					 qt.evaluatePostSlider(label.replace(/[^A-Z0-9]+/ig, ''),v3['value'])
	    	                				 }
	    	                			 }
	    	                			 else if(fieldType == 'star'){
	    	                				 if(v3['value'] != null && v3['value'] != '' && v3['value'] != 'undefined'){
	    	                					 qt.fillStarValues(v['dropdown'],label.replace(/[^A-Z0-9]+/ig, ''),v3['value'])
	    	                				 }
	    	                			 }
	    	                			 
	    	                			 else if(fieldType == 'file'){
	    	                					  var fileHtml = '';
	    	                					  fileHtml = fileHtml + "<input type='hidden' name='uploadedFileInfo["+fileCount+"].fileURL'  data-id='newUploadBtn"+label.replace(/[^A-Z0-9]+/ig, '')+"' value='"+v3['value']+"' />";
	    	                					  fileHtml = fileHtml + "<input type='hidden' name='uploadedFileInfo["+fileCount+"].fileName' data-id='newUploadBtn"+label.replace(/[^A-Z0-9]+/ig, '')+"' value='"+v3['name']+"' />";
	    	                					  fileHtml = fileHtml + "<input type='hidden' name='uploadedFileInfo["+fileCount+"].status' data-id='newUploadBtn"+label.replace(/[^A-Z0-9]+/ig, '')+"' value='1' />";
	    	                					  $("#addFeedbackTemplate_form").append(fileHtml);
		                					      fileCount = fileCount+1;
		                					      
		                					      if(v3['value'] != null && v3['value'] != '' && v3['value'] != 'undefined'){
		                					    	  $('#newUploadFile'+label.replace(/[^A-Z0-9]+/ig, '')).val(v3['name']);
			                					      $("#download-"+label.replace(/[^A-Z0-9]+/ig, '')).attr("href",v3['value']);
			                					      $("#attachmentTitle-"+label.replace(/[^A-Z0-9]+/ig, '')).html(v3['name']);
			                					      document.getElementById("download-"+label.replace(/[^A-Z0-9]+/ig, '')).style.display="";
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
	                                     var choiceHTML = '<span class="inlineBlock mr tblminwidth-50 mb10"><input type="radio" name=\''+label.replace(/\s+/g, '')+'\'  onclick="qt.getRadioValue(this,\''+label.replace(/[^A-Z0-9]+/ig, '')+'\');"  value="' + v['label'] + '">' + v['label'] + '</span>';
	                                     if(required == 1 && flag == 0){
	                                    	 choiceHTML = choiceHTML+'<input type="hidden"  id="'+labelTitle.replace(/[^A-Z0-9]+/ig, '')+'radio" data-validation="required" field-name="'+labelTitle+'">';
	                                     }
	                                     $currentField.find(".choices").append(choiceHTML);
	                                 }

	                                 else if (fieldType == 'checkbox') {
	                                     var selected = v['sel'] ? ' checked' : 'checked';
	                                     var choiceHTML = '<span class="inlineBlock mr tblminwidth-50 mb10"><input type="checkbox" name=\''+label.replace(/\s+/g, '')+'\' onclick="qt.getCheckBoxValue(this,\''+label.replace(/[^A-Z0-9]+/ig, '')+'\')"  value="' + v['label'] + '">' + v['label'] + '</span>';
	                                     if(required == 1 && flag == 0){
	                                    	 choiceHTML = choiceHTML+'<input type="hidden"  id="'+labelTitle.replace(/[^A-Z0-9]+/ig, '')+'checkbox" data-validation="required" field-name="'+labelTitle+'" >';
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
	                         else if (fieldType == 'calendar') { $currentField.find("calendar").prop('required',true).addClass('required-choice') }
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
	             function addSavedFieldHTML2(fieldType,required,label,hint,dropdown,points,attempt,attachmentCount) {
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
	                			 StarHtml = StarHtml+'<li class="fa fa-star cursorPointer text-muted"   id="star-select'+i+label.replace(/[^A-Z0-9]+/ig, '')+'"></li>';
	      					}
	                	 }else{
	                		 for(var i=1; i<=dropdown; i++){
	                			 StarHtml = StarHtml+'<li class="fa fa-star cursorPointer text-muted"  onclick="qt.fillStarValues('+i+',\''+label.replace(/[^A-Z0-9]+/ig, '')+'\');"  id="star-select'+i+label.replace(/[^A-Z0-9]+/ig, '')+'"></li>';
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
	                    	 
	                    	 var attemptsDiv = "";
	                    	 var pointsDiv = "";
	                    	 var dynamicAttempts = labelAndAttemptsMap.get(label);
	                    	 if(attempt != "" && attempt != null){
	                    		 attemptsDiv = '<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+dynamicAttempts+'</strong> Attempt(s)</div>' ;
	                    	 }
	                    	 else{
	                    		 infiniteAttemptsMap.set(label, true);
	                    	 }
	                    	 if(points != "" && points != null){
	                    		 pointsDiv = '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';
	                    	 }
	                    	 
	                    	 
	                    	 if(labelAndAttemptsMap.get(label) == 0 && infiniteAttemptsMap.get(label) != true){
	                    		 input = '<input type="text" field-name="'+label+'" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' id=\'text-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' disabled>' ;
	                    	 }else{
	                    		 input =  '<input type="text" field-name="'+label+'" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' id=\'text-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' >' ;
	                    	 }
	                         return '' +
	                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
	                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
	                             '<label for="text-' + uniqueID + '"></label>' +
	                             input +
	                             '</div>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp' + attemptsDiv + pointsDiv ;

	                     case 'textarea':
	                    	 if(labelAndAttemptsMap.get(label) == 0){
	                    		 input = '<textarea rows="3" field-name="'+label+'" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' id=\'textarea-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' disabled></textarea>' ;
	                    	 }else{
	                    		 input =  '<textarea rows="3" field-name="'+label+'" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' id=\'textarea-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' ></textarea>' ;
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
	                    		 input = '<select id="select-' + uniqueID + '" '+datavalidation+' name=\''+label.replace(/[^A-Z0-9]+/ig, '')+'\' field-name="'+label+'" class="choices choices-select"></select>' ;
	                    	 }else{
	                    		 input =  '<select id="select-' + uniqueID + '" '+datavalidation+' name=\''+label.replace(/[^A-Z0-9]+/ig, '')+'\' field-name="'+label+'" class="choices choices-select"></select>' ;
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
	                    		 input = '<input type="text" class="DatePicker dp" '+datavalidation+' field-name="'+label+'" name=\''+label.replace(/\s+/g, '')+'\' id=\'calendar-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' disabled>' ;
	                    	 }else{
	                    		 input =  '<input type="text" class="DatePicker dp" '+datavalidation+' field-name="'+label+'" name=\''+label.replace(/\s+/g, '')+'\' id=\'calendar-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' >' ;
	                    	 }
	                         return '' +
	                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
	                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
	                             '<label for="calendar-' + uniqueID + '"></label>' +
	                             input +
	                             '</div>'+
	                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+labelAndAttemptsMap.get(label)+'</strong> Attempt(s)</div>' +
	                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';
	                     
	                     case 'scale':
	                    	 if(labelAndAttemptsMap.get(label) == 0){
	                    		 input = '<input type="hidden" field-type="scale" field-name="'+label+'" '+datavalidation+'   id=\'scaleValue-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' ></span>' +
	                             '<input type="range" min="0" max="'+dropdown+'" value="0" name=\''+label.replace(/\s+/g, '')+'\' onmouseup="qt.evaluatePostSlider(\''+label.replace(/[^A-Z0-9]+/ig, '')+'\');" onclick="qt.evaluatePostSlider(\''+label.replace(/[^A-Z0-9]+/ig, '')+'\');"  id=\'scale-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' class="rangeslider" disabled>' ;
	                    	 }else{
	                    		 input =  '<input type="hidden" field-type="scale" field-name="'+label+'" '+datavalidation+'   id=\'scaleValue-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' ></span>' +
	                             '<input type="range" min="0" max="'+dropdown+'" value="0" name=\''+label.replace(/\s+/g, '')+'\' onmouseup="qt.evaluatePostSlider(\''+label.replace(/[^A-Z0-9]+/ig, '')+'\');" onclick="qt.evaluatePostSlider(\''+label.replace(/[^A-Z0-9]+/ig, '')+'\');"  id=\'scale-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' class="rangeslider">' ;
	                    	 }
	                         return '' +
	                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
	                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
	                             '<label for="text-' + uniqueID + '"></label>' +
	                             '<div class="slidecontainer mt clearfix">' +
	                             '<span id=\'demo-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' class="col-md-4">0</span>' +
	                             input +
	                             '</div>'+
	                             '</div>'+
	                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+labelAndAttemptsMap.get(label)+'</strong> Attempt(s)</div>' +
	                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';
	                         
	                     case 'star':
	                    	 if(labelAndAttemptsMap.get(label) == 0){
	                    		 input = '<input type="hidden" field-type="star" field-name="'+label+'" '+datavalidation+' id=\'starValue-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' name=\''+label.replace(/\s+/g, '')+'\'disabled></span>' ;
	                    	 }else{
	                    		 input =  '<input type="hidden" field-type="star" field-name="'+label+'" '+datavalidation+' id=\'starValue-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' name=\''+label.replace(/\s+/g, '')+'\'></span>' ;
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
	                    		 input = '<input type="file" id=\'newUploadBtn'+label.replace(/[^A-Z0-9]+/ig, '')+'\'  onchange="qt.uploadFeedbackAttachments(\''+label.replace(/\s+/g, '')+'\',\''+attachmentCount+'\');" class="upload" disabled>' +
	                             '<input type="text" class="form-control" style="display: none;" name=\'docFile'+label.replace(/\s+/g, '')+'\'  id=\'newUploadFile'+label.replace(/[^A-Z0-9]+/ig, '')+'\' '+attachmentDataValidationOptional+' '+attachmentDataValidation+' field-name="'+label+'" disabled>';
	                            }else{
	                    		 input =  '<input type="file" id=\'newUploadBtn'+label.replace(/[^A-Z0-9]+/ig, '')+'\'  onchange="qt.uploadFeedbackAttachments(\''+label.replace(/[^A-Z0-9]+/ig, '')+'\',\''+attachmentCount+'\');" class="upload">' +
	                             '<input type="text" class="form-control" style="display: none;" name=\'docFile'+label.replace(/\s+/g, '')+'\'  id=\'newUploadFile'+label.replace(/[^A-Z0-9]+/ig, '')+'\' '+attachmentDataValidationOptional+' '+attachmentDataValidation+' field-name="'+label+'"  >';
	                            }
	                    	 return '' +
	                         '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
	                         '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
	                         '<label for="text-' + uniqueID + '"></label>' +
	                         '<div class="clearfix mt"></div>'+
	                         input +
	                         '<a download="" class="glyphicon glyphicon-download-alt" style="display: none;" id=\'download-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' title="Download">&nbsp;<span id=\'attachmentTitle-'+label.replace(/[^A-Z0-9]+/ig, '')+'\'></span></a>'+
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

	this.getRadioValue = function(id,name){
		   $('#'+name+"radio").val(id.value); 
	} 

	this.getCheckBoxValue = function(id,name){
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



	this.fillStarValues = function(i,label,value){
		
		
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
	this.evaluatePreSlider = function(uniqueID){
		var slider = document.getElementById("text-"+uniqueID).value;
		$("#demo"+uniqueID).html(slider);
		$("#scaleValue"+uniqueID).val(slider);
		
	}

	this.evaluatePostSlider = function(label, value){
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


	this.uploadFeedbackAttachments = function(id,attachmentCount){
		qt.questionnaireFeedbackFileUpload("newUploadBtn"+id, "QuestionnaireFeedbackAttachments", "questionnaireTemplate_form","uploadedFileInfo",attachmentCount);
		$('#newUploadFile'+id).val($("#newUploadBtn"+id).val().replace(/C:\\fakepath\\/i, ''));
		
	}
	this.questionnaireFeedbackFileUpload = function(id, type, formId, name,attachmentCount,orgId, orgLocationId,url) {
		$(".file_mask").show();
		if(name == null || name == 'undefined' || name == ''){
			name = 'fileInfo';
		}
		if(orgId == null || orgId == "undefined" || orgId == "")
			orgId = 0;
		if(orgLocationId == null || orgLocationId == "undefined" || orgLocationId == "")
			orgLocationId = 0;
		var data = new FormData();
		var files = document.getElementById(""+id).files;
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
					qt.applyFileData(data, formId, name, id,attachmentCount);
					$(".file_mask").hide();
				}
			}, 
			error: function (err) { 
				 $(".file_mask").hide();
			} 
		});
	}

	this.applyFileData = function(data, formId, name, id,attachmentCount) {
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
	
	
	


}