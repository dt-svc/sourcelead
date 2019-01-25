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

function generateOrgSubmissionForm(sourceleadUrl,requirementId,finalDateFormatScript) {
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
                     var required =  v['req'];
                     var labelTitle = v['label'];

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
                         var flag = 0;
                         $.each( v['choices'], function( k, v ) {
                        	
                             if (fieldType == 'select') {
                                 var choiceHTML = '<option>' + v['label'] + '</option>';
                                 $currentField.find(".choices").append(choiceHTML);
                             }

                             else if (fieldType == 'radio') {
                                 var choiceHTML = '<label><input type="radio" name=\''+label.replace(/\s+/g, '')+'\'  onclick="getRadioValue(this,\''+label.replace(/\s+/g, '')+'\');"  value="' + v['label'] + '">' + v['label'] + '</label>';
                                 if(required == 1 && flag == 0){
                                	 choiceHTML = choiceHTML+'<input type="hidden"  id="'+labelTitle.replace(/\s+/g, '')+'radio" data-validation="required" field-name="'+labelTitle+'">';
                                 }
                                 $currentField.find(".choices").append(choiceHTML);
                             }

                             else if (fieldType == 'checkbox') {
                                 var selected = v['sel'] ? ' checked' : 'checked';
                                 var choiceHTML = '<label><input type="checkbox" name=\''+label.replace(/\s+/g, '')+'\' onclick="getCheckBoxValue(this,\''+label.replace(/\s+/g, '')+'\')"  value="' + v['label'] + '">' + v['label'] + '</label>';
                                 if(required == 1 && flag == 0){
                                	 choiceHTML = choiceHTML+'<input type="hidden"  id="'+labelTitle.replace(/\s+/g, '')+'checkbox" data-validation="required" field-name="'+labelTitle+'" >';
                                 }
                                 $currentField.find(".choices").append(choiceHTML);
                             }
                             
                             flag = flag+1;

                         });
                     }

                     //Is it required?
                     if (v['req']) {
                         if (fieldType == 'text') { $currentField.find("input").addClass('required-choice') }
                         else if (fieldType == 'calendar') { $currentField.find("calandar").addClass('required-choice') }
                         else if (fieldType == 'autocomplete') { $currentField.find("autocomplete").addClass('required-choice') }
                         else if (fieldType == 'textarea') { $currentField.find("textarea").addClass('required-choice') }
                         else if (fieldType == 'select') { $currentField.find("select").addClass('required-choice') }
                         $currentField.addClass('required-field');
                     }

                 });
             }
        	 
        	  //HTML templates for rendering frontend form fields
             function addFieldHTML(fieldType,required,label,sourceleadUrl) {
            	 
            	 var datavalidation = '';	
            	 var novalidation = '';
            	 var datavalidation2 = '';
            	 var onBlurEvent = '';
            	 var style = '';
            	 var months = '';
            	 var onclick='';
            	 var ratePer = '';
            	 var options = '';
            	 var onblur='';
            	 var rateType = '';
            	 var labelId = '';
            	 var educationPlaceHolder = '';
            	 
        		 if(required == 1){
        			 if(label == 'Email Id'){
        				 datavalidation = 'data-validation="required validate_email validate_Space validate_length length2-100"';
        				 onBlurEvent = 'onblur="validateSubmissionCandidateEmail();getUserByUserEmail();"';
        				 
        			 }else if(label == 'Phone Number'){
        				 datavalidation = 'data-validation="required validate_phone validate_Space validate_length length9-20"';
        			 }
        			 else if(label == 'Current CTC' || label == 'Expected CTC'){
            			 datavalidation = 'data-validation="required validate_Space validate_float validate_length length1-8"';
            		 }
        			 else if(label == 'Designation'){
            			 datavalidation = 'data-validation="required validate_Space validate_Name validate_length length2-250"';
            		 }
        			 else if(label == 'Education'){
            			 datavalidation = 'data-validation="required validate_Space validate_Name"';
            		 }
        			 else if(label == 'First Name' || label == 'Last Name'){
        				 datavalidation = 'data-validation="required validate_Space validate_AlphaNumber validate_length length1-100"';
        			 }else{
        				 datavalidation = 'data-validation="required validate_Space validate_length length1-100"';
        			 }
        			 
        			 datavalidation2 = 'data-validation="required"';
        		 }else{
        			 if(label == 'Alternate Number'){
        				 datavalidation = 'data-validation="validate_phone validate_Space validate_length length9-20"';
        			 }
        			 
        			 else if(label == 'Current CTC' || label == 'Expected CTC'){
        				 datavalidation = 'data-validation="validate_Space validate_float validate_length length1-8"';
            		 }
        			 else if(label == 'Designation'){
            			 datavalidation = 'data-validation="validate_Space validate_Name validate_length length2-250"';
            		 }
        			 else if(label == 'Education'){
            			 datavalidation = 'data-validation="validate_Space validate_Name"';
            		 }
        			 else if(label == 'Last Name'){
        				 datavalidation = 'data-validation="validate_Space validate_AlphaNumber validate_length length1-100"';
        			 }else{
        				 datavalidation = 'data-validation="validate_Space validate_length length1-100"';
        			 }
        			 novalidation = 'data-validation-optional="true"';
        		 }
        		 
        		
        		 
            	 if(label == 'location'){
            		 var reqSymbol = ''
            		 var datavalidation = '';	 
            		 if(required == 1){
            			 reqSymbol = '<span>&nbsp;*</span>'
            		 }
            		 
            		 var locationUniqueID = Math.floor(Math.random()*999999)+1;
            		 var countryUniqueID = Math.floor(Math.random()*999999)+1;
            		 var stateUniqueID = Math.floor(Math.random()*999999)+1;
            		 var cityUniqueID = Math.floor(Math.random()*999999)+1;
            		 var zipCodeUniqueID = Math.floor(Math.random()*999999)+1;
            		 var addreessUniqueID = Math.floor(Math.random()*999999)+1;
            	 }
            	 else{
            		 var uniqueID = Math.floor(Math.random()*999999)+1;
            	 }
            	 
            	 if(label == 'Work Experience'){
            		 
            		 style = 'style="width:160px !important; padding-right:5px;"';
            		 onclick = 'onclick="getExpYears();"'
            		 options = '<option value="">Years</option>';
            		 months = '&nbsp;<select  style="width:160px !important; padding-right:5px;" id="monthExp" name="monthExp" field-name="Months">'+
            		 '<option value="0">Months</option>'+
                     '<option value="1">1</option>'+
                     '<option value="2">2</option>'+
                     '<option value="3">3</option>'+
                     '<option value="4">4</option>'+
                     '<option value="5">5</option>'+
                     '<option value="6">6</option>'+
                     '<option value="7">7</option>'+
                     '<option value="8">8</option>'+
                     '<option value="9">9</option>'+
                     '<option value="10">10</option>'+
                     '<option value="11">11</option>'+
                     '</select>';
            	 }
            	 
            	 if(label == "Current CTC"){
            		 
            		 
            		 labelId = 'id="currentRate"';
            		 
            		 onblur = 'onblur="validateCurrentCTC();"';
            		 
            		 style = 'style="width:130px !important; padding-right:5px;"'; 
            		 
            		 ratePer = '&nbsp;<select style="width:115px !important; padding-right:5px;" onblur="validateCurrentCTCRatePer();"  id="currentCTCRatePer" name="currentCTCRatePer" field-name="Rate Per">'+
            		 '<option value="">Rate Per</option>'+
            		 '<option value="Yearly">Yearly</option>'+
            		 '<option value="Monthly">Monthly</option>'+
            		 '<option value="Semi-Monthly">Semi-Monthly</option>'+
            		 '<option value="Bi-Monthly">Bi-Monthly</option>'+
                     '<option value="Weekly">Weekly</option>'+
                     '<option value="Bi-weekly">Bi-weekly</option>'+
                     '<option value="Hourly">Hourly</option>'+
                     '</select>';
            		 
            		 rateType = '<select style="width:70px !important; padding-right:5px;" id="currentCTCRateType" name="currentCTCRateType" field-name="Rate Type">'+
            		 '<option value="INR">INR</option>'+
            		 '<option value="AUD">AUD</option>'+
            		 '<option value="USD">USD</option>'+
            		 '<option value="GBP">GBP</option>'+
            		 '<option value="SGD">SGD</option>'+
                     '<option value="EUR">EUR</option>'+
                     '</select>&nbsp;';
            	 }
            	 
            	 if(label == "Expected CTC"){
            		 style = 'style="width:130px !important; padding-right:5px;"'; 
            		 
            		
            		 labelId = 'id="expectedRate"';
            		 onblur = 'onblur="validateExpectedCTC();"';
            		 
            		 ratePer = '&nbsp;<select  style="width:115px !important; padding-right:5px;" onblur="validateExpectedCTCRatePer();"  id="expectedCTCRatePer" name="expectedCTCRatePer" field-name="Rate Per">'+
            		 '<option value="">Rate Per</option>'+
            		 '<option value="Yearly">Yearly</option>'+
            		 '<option value="Monthly">Monthly</option>'+
            		 '<option value="Semi-Monthly">Semi-Monthly</option>'+
            		 '<option value="Bi-Monthly">Bi-Monthly</option>'+
                     '<option value="Weekly">Weekly</option>'+
                     '<option value="Bi-weekly">Bi-weekly</option>'+
                     '<option value="Hourly">Hourly</option>'+
                     '</select>';
            		 
            		 rateType = '<select style="width:70px !important; padding-right:5px;" id="expectedCTCRateType" name="expectedCTCRateType" field-name="Rate Type">'+
            		 '<option value="INR">INR</option>'+
            		 '<option value="AUD">AUD</option>'+
            		 '<option value="USD">USD</option>'+
            		 '<option value="GBP">GBP</option>'+
            		 '<option value="SGD">SGD</option>'+
                     '<option value="EUR">EUR</option>'+
                     '</select>&nbsp;';
            	 }
            	 
            	 if(label == "Education"){
            		 educationPlaceHolder = 'placeholder="Enter education by comma(,) seperated"';
            	 }
            	 
            	 if(label == 'Notice Period'){
            		 options = '<option value="">--Select Notice Period--</option>';
            	 }
            	 
                

                 switch (fieldType) {

                     case 'text':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
                             '<label for="text-' + uniqueID + '" '+labelId+'></label>' +
                              rateType+
                             '<input type="text" '+onblur+' '+style+' '+onBlurEvent+' '+datavalidation+' '+novalidation+' '+educationPlaceHolder+' value="" field-name=\''+label+'\' name=\''+label.replace(/\s+/g, '')+'\' id="text-' + uniqueID + '">' +
                              ratePer+
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
                             '<select  '+style+' '+onclick+' id="select-' + uniqueID + '" '+datavalidation2+' '+novalidation+' name=\''+label.replace(/\s+/g, '')+'\' field-name=\''+label+'\' class="choices choices-select">'+options+'</select>' +
                               months+
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
                             
                    		 '<div id="sjfb-' + locationUniqueID + '" class="sjfb-field sjfb-text">' +
                             '<label>Location'+reqSymbol+'</label>' +
                             '<input type="text" class="form-control positionRelative"  '+datavalidation2+' '+novalidation+'  name="location" id="submissionLocation" field-name="Location"  />'+
                             '<i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation(\'submissionLocation\',\'submissionCountry\',\'submissionState\',\'submissionCity\',\'submissionZipCode\');"></i>'+
                             '</div>'+
                    		 
                    		 '<div id="sjfb-' + countryUniqueID + '" class="sjfb-field sjfb-text">' +
                             '<label>Country'+reqSymbol+'</label>' +
                             '<input  type="text" class="form-control"   '+datavalidation2+' '+novalidation+'  name="country" id="submissionCountry" field-name="Country" />'+
                             '</div>'+
                             
                             '<div id="sjfb-' + stateUniqueID + '" class="sjfb-field sjfb-text">' +
                             '<label>State'+reqSymbol+'</label>' +
                             '<input  type="text" class="form-control" '+datavalidation2+' '+novalidation+'  name="state" id="submissionState" field-name="State" />'+
                             '</div>'+
                             
                             '<div id="sjfb-' + cityUniqueID + '" '+novalidation+' class="sjfb-field sjfb-text">' +
                             '<label>City'+reqSymbol+'</label>' +
                             '<input  type="text" class="form-control" '+datavalidation2+' '+novalidation+'  name="city" id="submissionCity" field-name="City"/>'+
                             '</div>'+
                             
                             '<div id="sjfb-' + zipCodeUniqueID + '" class="sjfb-field sjfb-text">' +
                             '<label>ZipCode'+reqSymbol+'</label>' +
                             '<input type="text" class="form-control positionRelative"  '+datavalidation2+' '+novalidation+'  name="zipcode" id="submissionZipCode" field-name="Zipcode" />'+
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
//             changeRateLabels();
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

