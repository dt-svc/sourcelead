/**
 * Simple Jquery Form Builder (SJFB)
 * Copyright (c) 2015 Brandon Hoover, Hoover Web Development LLC (http://bhoover.com)
 * http://bhoover.com/simple-jquery-form-builder/
 * SJFB may be freely distributed under the included MIT license (license.txt).
 */

//generates the form HTML
function generateForm(pmsUrl,templateUUID,finalDateFormatScript) {
    //empty out the preview area
    $("#sjfb-fields").empty();
    $.ajax({
    	type: "GET",
        url: pmsUrl+"/loadPerformanceFeedbackTemplate?templateUUID="+templateUUID, 
        success: function(result) {
        	 var data = eval(result.jsonString);
        	 
        	 if (data) {
                 //go through each saved field object and render the form HTML
                 $.each( data, function( k, v ) {

                     var fieldType = v['type'];
                     var labelTitle = v['label'];
                     var required =  v['req'];

                     //Add the field
                     $('#sjfb-fields').append(addFieldHTML(fieldType,v['req'],v['label'],v['hint'],v['dropdown']));
                     var $currentField = $('#sjfb-fields .sjfb-field').last();
                     
                     var reqSymbol = '';
                	 if(v['req'] == '1'){
                		reqSymbol = " * ";
                	 }
                     
                     //Add the label
                     if(v['label'] != 'location' ){
                    	 $currentField.find('label').text(v['label']+reqSymbol);
                     }
                     
                     
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
             function addFieldHTML(fieldType,required,label,hint,dropdown) {
            	 var uniqueID = Math.floor(Math.random()*999999)+1;
            	 var datavalidation = '';	
            	 if(required == '1'){
            		 datavalidation = 'data-validation="required"';
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
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label for="text-' + uniqueID + '"></label>' +
                             '<input type="text" field-name="'+label+'" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' id="text-' + uniqueID + '">' +
                             '</div>';

                     case 'textarea':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-textarea">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label for="textarea-' + uniqueID + '"></label>' +
                             '<textarea rows="3" field-name="'+label+'" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' id="textarea-' + uniqueID + '"></textarea>' +
                             '</div>';

                     case 'select':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-select">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label for="select-' + uniqueID + '"></label>' +
                             '<select id="select-' + uniqueID + '" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' field-name="'+label+'" class="choices choices-select"></select>' +
                             '</div>';

                     case 'radio':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-radio">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label></label>' +
                             '<div class="clearfix mt"></div>'+
                             '<div class="choices choices-radio"></div>' +
                             '</div>';

                     case 'checkbox':
                         return '' +
                             '<div id="sjfb-checkbox-' + uniqueID + '" class="sjfb-field sjfb-checkbox">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label class="sjfb-label"></label>' +
                             '<div class="clearfix mt"></div>'+
                             '<div class="choices choices-checkbox"></div>' +
                             '</div>';
                         

                     case 'calendar':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label for="calandar-' + uniqueID + '"></label>' +
                             '<input type="text" class="DatePicker dp" '+datavalidation+' field-name="'+label+'" name=\''+label.replace(/\s+/g, '')+'\' id="text-' + uniqueID + '">' +
                             '</div>';
                     
                     case 'scale':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label for="text-' + uniqueID + '"></label>' +
                             '<div class="slidecontainer mt clearfix">' +
                             '<span id="demo'+ uniqueID +'" class="col-md-4">0</span>' +
                             '<input type="hidden" field-type="scale" field-name="'+label+'" '+datavalidation+'  id="scaleValue'+ uniqueID +'"></span>' +
                             '<input type="range" min="0" max="'+dropdown+'" value="0" name=\''+label.replace(/\s+/g, '')+'\' onmouseup="evaluateSlider('+uniqueID+');" onclick="evaluateSlider('+uniqueID+')"  id="text-' + uniqueID + '" class="rangeslider">' +
                             '</div>'+
                             '</div>';
                         
                     case 'star':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text starclass">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label for="text-' + uniqueID + '"></label>' +
                             '<input type="hidden" field-type="star" field-name="'+label+'" '+datavalidation+' id="starValue'+ uniqueID +'" name=\''+label.replace(/\s+/g, '')+'\'></span>' +
                             '<ul class="list-unstyled list-inline text-muted" id="starRating-' + uniqueID + '">' +
                             '<div class="clearfix mt"></div>'+
                             StarHtml+
                             '</ul>' +
                             '</div>'; 
                         
                     case 'file':
                    	 return '' +
                         '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
                         '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                         '<label for="text-' + uniqueID + '"></label>' +
                         '<div class="clearfix mt"></div>'+
                         '<input type="file" id="newUploadBtn'+uniqueID+'" onchange="uploadFeedbackAttachments(\''+uniqueID+'\');" class="upload">' +
                         '<input type="text" class="form-control" style="display: none;"  name="docFile^'+label+'" id="newUploadFile'+uniqueID+'" '+datavalidation+' field-name="'+label+'"  >'+
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
        },
    });
    
}

function evaluateSlider(uniqueID){
	var slider = document.getElementById("text-"+uniqueID).value;
	$("#demo"+uniqueID).html(slider);
	if(slider == 0){
		$("#scaleValue"+uniqueID).val('');
	}else{
		$("#scaleValue"+uniqueID).val(slider);
	}
	
	
}

function fillStarValues(i,uniqueID){
	
	$("#starValue"+uniqueID).val(i);
	
	for(var k=1; k<=i; k++){
		$("#star-select"+k+uniqueID).removeClass('text-muted').addClass('textWarning');
	}
	
	for(var k=i+1; k<=6; k++){
		$("#star-select"+k+uniqueID).removeClass('textWarning').addClass('text-muted');
	}
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

