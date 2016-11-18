/**
 * Simple Jquery Form Builder (SJFB)
 * Copyright (c) 2015 Brandon Hoover, Hoover Web Development LLC (http://bhoover.com)
 * http://bhoover.com/simple-jquery-form-builder/
 * SJFB may be freely distributed under the included MIT license (license.txt).
 */

//generates the form HTML
function generateForm(sourceleadUrl,templateId) {

    //empty out the preview area
    $("#sjfb-fields").empty();
    
    
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
                     $('#sjfb-fields').append(addFieldHTML(fieldType,v['req'],v['label']));
                     var $currentField = $('#sjfb-fields .sjfb-field').last();
                     
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
                         

                     case 'calendar':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
                             '<label for="calandar-' + uniqueID + '"></label>' +
                             '<input type="text" id="text-' + uniqueID + '">' +
                             '</div>';
                         
                     case 'autocomplete':
                    	 if(label == 'location'){
                    		 return '' +
                             '<div id="sjfb-' + countryUniqueID + '" class="sjfb-field sjfb-text">' +
                             '<label>Country'+reqSymbol+'</label>' +
                             '<input type="text" id="text-' + countryUniqueID + '">' +
                             '</div>'+
                             '<div id="sjfb-' + stateUniqueID + '" class="sjfb-field sjfb-text">' +
                             '<label>State'+reqSymbol+'</label>' +
                             '<input type="text" id="text-' + stateUniqueID + '">' +
                             '</div>'+
                             '<div id="sjfb-' + cityUniqueID + '" class="sjfb-field sjfb-text">' +
                             '<label>City'+reqSymbol+'</label>' +
                             '<input type="text" id="text-' + cityUniqueID + '">' +
                             '</div>';
                    	 }
                    	 else{
                    		 return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
                             '<label for="autocomplete-' + uniqueID + '"></label>' +
                             '<input type="text" id="text-' + uniqueID + '">' +
                             '</div>';
                    	 }	 
                    		 
                        

                 }
             }
        },
    });
    
    
}



// To Generate Org SubmissionForm

function generateOrgSubmissionForm(sourceleadUrl,requirementId) {

    //empty out the preview area
    $("#sjfb-fields").empty();
    
    $.ajax({
    	type: "GET",
        url: sourceleadUrl+"/getRequirmentOrgSubmissionTemplate?requirementId="+requirementId, 
        success: function(data) {
        	 var data = eval(data);
        	 
        	 if (data) {
                 //go through each saved field object and render the form HTML
                 $.each( data, function( k, v ) {

                     var fieldType = v['type'];

                     //Add the field
                     $('#sjfb-fields').append(addFieldHTML(fieldType,v['req'],v['label'],sourceleadUrl));
                     var $currentField = $('#sjfb-fields .sjfb-field').last();
                     
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
            	 var datavalidation2 = '';
            	 
        		 if(required == 1){
        			 if(label == 'Email Id'){
        				 datavalidation = 'data-validation="required validate_email validate_Space validate_length length2-50"';
        				 
        			 }else if(label == 'Phone Number'){
        				 datavalidation = 'data-validation="required validate_phone validate_Space validate_length length9-20"';
        			 }
        			 else{
        				 datavalidation = 'data-validation="required validate_Space validate_length length2-50"';
        			 }
        			 
        			 if(label == 'Current CTC' || label == 'Expected CTC'){
            			 datavalidation = 'data-validation="required validate_Space validate_int length2-50"';
            		 }
            		 if(label == 'Designation' || label == 'Education'){
            			 datavalidation = 'data-validation="required validate_Space validate_Alphabet length2-50"';
            		 }
        			 
        			 datavalidation2 = 'data-validation="required"';
        		 }else{
        			 if(label == 'Alternate Number'){
        				 datavalidation = 'data-validation="validate_phone validate_Space validate_length length9-20"';
        			 }else{
        				 datavalidation = 'data-validation="validate_Space validate_length length2-50"';
        			 }
        			 
        			 if(label == 'Current CTC' || label == 'Expected CTC'){
            			 datavalidation = 'data-validation="validate_Space validate_int length2-50"';
            		 }
            		 if(label == 'Designation' || label == 'Education'){
            			 datavalidation = 'data-validation="validate_Space validate_Alphabet length2-50"';
            		 }
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
                             '<textarea  rows="3" '+datavalidation+' '+novalidation+' name=\''+label.replace(/\s+/g, '')+'\' field-name=\''+label+'\' id="textarea-' + uniqueID + '"></textarea>' +
                             '</div>';

                     case 'select':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-select">' +
                             '<label for="select-' + uniqueID + '"></label>' +
                             '<select id="select-' + uniqueID + '" '+datavalidation2+' '+novalidation+' name=\''+label.replace(/\s+/g, '')+'\' field-name=\''+label+'\' class="choices choices-select"></select>' +
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
                         

                     case 'calendar':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
                             '<label for="calandar-' + uniqueID + '"></label>' +
                             '<input type="text" class="DatePicker dp" '+datavalidation2+' name=\''+label.replace(/\s+/g, '')+'\' field-name=\''+label+'\' id=\''+label.replace(/\s+/g, '')+'\' readonly="readonly">' +
                             '</div>';
                         
                     case 'autocomplete':
                    	 if(label == 'location'){
                    		 return '' +
                             '<div id="sjfb-' + countryUniqueID + '" class="sjfb-field sjfb-text">' +
                             '<label>Country'+reqSymbol+'</label>' +
                             '<select class="chosen-select form-control select-bg"  '+datavalidation2+' '+novalidation+'  name="country" id="country" field-name="Country"  >'+
                             '<option>--Select--</option>'+
                             '</select>'+
                             '</div>'+
                             
                             '<div id="sjfb-' + stateUniqueID + '" class="sjfb-field sjfb-text">' +
                             '<label>State'+reqSymbol+'</label>' +
                             '<select class="chosen-select form-control select-bg"  '+datavalidation2+' '+novalidation+'  name="state" id="state" field-name="State"  >'+
                             '<option>--Select--</option>'+
                             '</select>'+
                             '</div>'+
                             
                             '<div id="sjfb-' + cityUniqueID + '" '+novalidation+' class="sjfb-field sjfb-text">' +
                             '<label>City'+reqSymbol+'</label>' +
                             '<select class="chosen-select form-control select-bg" '+datavalidation2+' '+novalidation+'  name="city" id="city" field-name="State"  >'+
                             '<option>--Select--</option>'+
                             '</select>'+
                             '</div>';
                    		 
                    		 
                    	 }
                    	 else{
                    		 return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
                             '<label for="autocomplete-' + uniqueID + '"></label>' +
                             '<input type="text" '+datavalidation2+' '+novalidation+' field-name=\''+label+'\' name=\''+label.replace(/\s+/g, '')+'\' id="text-' + uniqueID + '">' +
                             '</div>';
                    	 }	
                    	 
                    	 
                 }
             }
             init();
             var d = new Date();
             var year = d.getFullYear() - 18;
             d.setFullYear(year);
             $(".DatePicker").datepicker({
            	 dateFormat: 'yy-mm-dd',
            	 yearRange: "-70Y:-18Y" ,
            	 defaultDate: d,
            	 changeMonth: true,
                 changeYear: true
                 
             });
        },
    });
    
}


