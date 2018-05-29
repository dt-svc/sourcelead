/**
 * Simple Jquery Form Builder (SJFB)
 * Copyright (c) 2015 Brandon Hoover, Hoover Web Development LLC (http://bhoover.com)
 * http://bhoover.com/simple-jquery-form-builder/
 * SJFB may be freely distributed under the included MIT license (license.txt).
 */



// To Generate Checklist 


function getCheckList(sourceleadUrl,offboardId) {
    //empty out the preview area
    $("#sjfb-fields").empty();
    $.ajax({
    	type: "GET",
        url: sourceleadUrl+"/getOffboardCheckListByOffBoardId?offboardId="+offboardId, 
        success: function(response) {
        	if (response.response === "AWKWARD") {
        	    location.href = "/errorPage";
        	} else if (response.response === "SUCCESS") {
        		var json = JSON.parse(response.object);
            	var jsonStringData = eval(json['checkListJsonString']);
            	if(jsonStringData != null && jsonStringData != '' && jsonStringData != 'undefined'){
            	if (jsonStringData) {
                     //go through each saved field object and render the form HTML
                     $.each( jsonStringData, function( k, v ) {
                         var fieldType = v['type'];
                         //Add the field
                         $('#sjfb-fields').append(addFieldHTML(fieldType,v['req'],v['label']));
                         var $currentField = $('#sjfb-fields .sjfb-field').last();
                         $currentField.find('label').text(v['label']);
                         if(json['checkListJsonValueString'] != null && json['checkListJsonValueString'] != '' && json['checkListJsonValueString'] != 'undefined'){
                        	 if (v['choices']) {
                                 var uniqueID = Math.floor(Math.random()*999999)+1;
                                 $.each( v['choices'], function( k1, v1 ) {
                                    if (fieldType == 'radio') {
                                	  var details = eval(json['checkListJsonValueString']);
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
                                  				 var choiceHTML = '<label><input type="radio" name="radio-' + uniqueID + '"' + selected + ' value="' + v1['label'] + '">' + v1['label'] + '</label>';
                                                 $currentField.find(".choices").append(choiceHTML);
                                  			   }
                                  		   }
                                  	   });
                                     }
                                     else if (fieldType == 'checkbox') {
                                    	 var details = eval(json['checkListJsonValueString']);
                                    	 var selected = '';
                                    	 $.each(details, function( k3, v3 ) {
                                    		 if(v['label'] == v3['label']){
                                    			 var values = v3['value'].split(",");
                                    			 for(var i=0; i<values.length; i++){
                                    				 if(values[i] == v1['label']){
                                      					 selected = "checked";
                                  				     }
                                    			 }
                                    			 var choiceHTML = '<label><input type="checkbox" onclick="validateCheckBox(this);" name="checkbox-' + uniqueID + '[]"' + selected + ' value="' + v1['label'] + '">' + v1['label'] + '</label>';
                                                 $currentField.find(".choices").append(choiceHTML);
                                    		 }
                                    	 });
                                     }
                                 });
                             }
                         }else{
                        	 //Any choices?
                             if (v['choices']) {
                                 var uniqueID = Math.floor(Math.random()*999999)+1;
                                 $.each( v['choices'], function( k, v ) {
                                   if (fieldType == 'radio') {
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
                         }
                         //Is it required?
                         if (v['req']) {
                             if (fieldType == 'textarea') { $currentField.find("textarea").prop('required',true).addClass('required-choice') }
                             $currentField.addClass('required-field');
                         }
                         if(fieldType == 'textarea'){
                     	    getContent(v['label']);
                         }
                     });
                 }
            	}else{
            		 $('#sjfb-fields').append("<h4>No Check List Found</h4>");
            	}
            	  //HTML templates for rendering frontend form fields
                 function addFieldHTML(fieldType,required,label) {
                	 var uniqueID = Math.floor(Math.random()*999999)+1;
                     switch (fieldType) {
                         case 'textarea':
                             return '' +
                                 '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-textarea">' +
                                 '<label for="textarea-' + uniqueID + '"></label>' +
                                 '<textarea rows="3" id="textarea-' + label + '"  ></textarea>' +
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
                 function getContent(label){
                	 var details = eval(json['checkListJsonValueString']);
                	 $.each(details, function( k3, v3 ) {
                		 if(label == v3['label']){
                			 $('#textarea-'+ label).html(v3['value']);
                		 }
                	 });
                 }
        	} else if (response.indexOf('Sign In') !== -1) {
        		location.replace(sourceleadUrl);
        	} else {
        	    showError("Operation failed, please try again");
        	}
        },
    });
}

function validateCheckBox(obj){
	if(obj.checked == false){
		obj.removeAttribute('checked');
    }
}