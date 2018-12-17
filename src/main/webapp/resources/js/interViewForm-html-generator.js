/**
 * Simple Jquery Form Builder (SJFB)
 * Copyright (c) 2015 Brandon Hoover, Hoover Web Development LLC (http://bhoover.com)
 * http://bhoover.com/simple-jquery-form-builder/
 * SJFB may be freely distributed under the included MIT license (license.txt).
 */

//generates the form HTML
function generateForm12(sourceleadUrl,templateId) {

    //empty out the preview area
    $("#interviewsjfb-fields").empty();
    
    
    $.ajax({
    	type: "GET",
        url: sourceleadUrl+"/loadSavedSubmissionTemplate?templateId="+templateId, 
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



// To Generate Org SubmissionForm

function generateInterviewFeedbackForm(sourceleadUrl,templateId) {
    //empty out the preview area
    $("#interviewsjfb-fields").empty();
    
    $.ajax({
    	type: "GET",
        url: sourceleadUrl+"/getInterviewFeedbackTemplate?templateId="+templateId, 
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
                         $currentField.addClass('required-field');
                     }

                 });
             }
        	 
        	 
        	 
        	  //HTML templates for rendering frontend form fields
             function addFieldHTML(fieldType,required,label,sourceleadUrl) {
            	 
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
                
            	 
                 switch (fieldType) {

                     case 'text':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
                             '<label for="text-' + uniqueID + '"></label>' +
                             '<input type="text" '+datavalidation+' '+onBlurEvent+' '+placeHolder+' '+novalidation+' value="" field-name=\''+label+'\' name=\''+label.replace(/\s+/g, '')+'\' id="text-' + uniqueID + '">' +
                             '</div>';

                     case 'textarea':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-textarea">' +
                             '<label for="textarea-' + uniqueID + '"></label>' +
                             '<textarea  rows="3" '+datavalidation+' '+novalidation+' name=\''+label.replace(/\s+/g, '')+'\' field-name=\''+label+'\' id="textarea-' + uniqueID + '"></textarea>' +
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


