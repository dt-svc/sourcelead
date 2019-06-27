/**
 * Simple Jquery Form Builder (SJFB)
 * Copyright (c) 2015 Brandon Hoover, Hoover Web Development LLC (http://bhoover.com)
 * http://bhoover.com/simple-jquery-form-builder/
 * SJFB may be freely distributed under the included MIT license (license.txt).
 */

//generates the form HTML
function generateForm(pmsUrl,templateUUID,assignedUUID,finalDateFormatScript) {
    //empty out the preview area
    $("#sjfb-fields").empty();
    $.ajax({
    	type: "GET",
        url: pmsUrl+"/loadPerformanceFeedbackTemplate?templateUUID="+templateUUID+"&assignedUUID="+assignedUUID, 
        success: function(result) {
        	var data = null;
        	if(result.submittedJsonString != null){
        		data = eval(result.submittedJsonString);
        	}else{
        		data = eval(result.jsonString);
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
                     $('#sjfb-fields').append(addFieldHTML(fieldType,v['req'],v['label'],v['hint'],v['dropdown'],attachmentCount));
                     var $currentField = $('#sjfb-fields .sjfb-field').last();
                     
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
                     
                    
                     if(result.submittedJsonStringValues != null && result.submittedJsonStringValues != '' && result.submittedJsonStringValues != 'undefined'){
                    	 if (v['choices']) {
                    		 var selectChoice = 1;
                        	 var selectChoiceHTML = '<option value="">----Select-----</option>';
                             var uniqueID = Math.floor(Math.random()*999999)+1;
                             $.each( v['choices'], function( k1, v1 ) {
                            	 var label = v1['label'];
                                if (fieldType == 'radio') {
                            	  var details = eval(result.submittedJsonStringValues);
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
                                	 var details = eval(result.submittedJsonStringValues);
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
                                	 var details = eval(result.submittedJsonStringValues);
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
    	                   	 var details = eval(result.submittedJsonStringValues);
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
    	                					 evaluateSlider(label.replace(/\s+/g, ''),v3['value'])
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
             function addFieldHTML(fieldType,required,label,hint,dropdown,attachmentCount) {
            	 var uniqueID = Math.floor(Math.random()*999999)+1;
            	 var datavalidation = '';	
            	 var attachmentDataValidation = '';
            	 var attachmentDataValidationOptional = ''
            	 if(required == '1'){
            		 datavalidation = 'data-validation="required"';
            	 }
            	 
            	 if(fieldType == "star"){
            		 var StarHtml = '';
            		 for(var i=1; i<=dropdown; i++){
            			 StarHtml = StarHtml+'<li class="fa fa-star cursorPointer text-muted"  onclick="fillStarValues('+i+',\''+label.replace(/\s+/g, '')+'\');"  id="star-select'+i+label.replace(/\s+/g, '')+'"></li>';
  					}
            	 }
            	 
            	 if(fieldType == "file"){
            		 if(dropdown == 'docx'){
            			 if(required == '1'){
            				 attachmentDataValidation = 'data-validation="required validatedoc"'
            			 }else{
            				 attachmentDataValidationOptional = 'data-validation-optional="true"'
            				 attachmentDataValidation = 'data-validation="validatedoc"'
            			 }
            			 
            		 }else if(dropdown == 'xls'){
            			 
            			 if(required == '1'){
            				 attachmentDataValidation = 'data-validation="required validate_importFile"'
            			 }else{
            				 attachmentDataValidationOptional = 'data-validation-optional="true"'
            				 attachmentDataValidation = 'data-validation="validate_importFile"'
            			 }
            			 
            		 }else if(dropdown == 'png'){
            			 if(required == '1'){
            				 attachmentDataValidation = 'data-validation="required validateimage"'
            			 }else{
            				 attachmentDataValidationOptional = 'data-validation-optional="true"'
            				 attachmentDataValidation = 'data-validation="validateimage"'
            			 }
            		 }
            	 }
                

                 switch (fieldType) {

                     case 'text':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label for="text-' + uniqueID + '"></label>' +
                             '<input type="text" field-name="'+label+'" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' id=\'text-'+label.replace(/\s+/g, '')+'\' >' +
                             '</div>';

                     case 'textarea':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-textarea">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label for="textarea-' + uniqueID + '"></label>' +
                             '<textarea rows="3" field-name="'+label+'" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' id=\'textarea-'+label.replace(/\s+/g, '')+'\' ></textarea>' +
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
                             '<input type="text" class="DatePicker dp" '+datavalidation+' field-name="'+label+'" name=\''+label.replace(/\s+/g, '')+'\' id=\'calendar-'+label.replace(/\s+/g, '')+'\' >' +
                             '</div>';
                     
                     case 'scale':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label for="text-' + uniqueID + '"></label>' +
                             '<div class="slidecontainer mt clearfix">' +
                             '<span id=\'demo-'+label.replace(/\s+/g, '')+'\' class="col-md-4">0</span>' +
                             '<input type="hidden" field-type="scale" field-name="'+label+'" '+datavalidation+'   id=\'scaleValue-'+label.replace(/\s+/g, '')+'\' ></span>' +
                             '<input type="range" min="0" max="'+dropdown+'" value="0" name=\''+label.replace(/\s+/g, '')+'\' onmouseup="evaluateSlider(\''+label.replace(/\s+/g, '')+'\');" onclick="evaluateSlider(\''+label.replace(/\s+/g, '')+'\');"  id=\'scale-'+label.replace(/\s+/g, '')+'\' class="rangeslider">' +
                             '</div>'+
                             '</div>';
                         
                     case 'star':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text starclass">' +
                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
                             '<label for="text-' + uniqueID + '"></label>' +
                             '<input type="hidden" field-type="star" field-name="'+label+'" '+datavalidation+' id=\'starValue-'+label.replace(/\s+/g, '')+'\' name=\''+label.replace(/\s+/g, '')+'\'></span>' +
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
                         '<input type="file" id=\'newUploadBtn'+label.replace(/\s+/g, '')+'\'  onchange="uploadFeedbackAttachments(\''+label.replace(/\s+/g, '')+'\',\''+attachmentCount+'\');" class="upload">' +
                         '<input type="text" class="form-control" style="display: none;" name=\'docFile'+label.replace(/\s+/g, '')+'\'  id=\'newUploadFile'+label.replace(/\s+/g, '')+'\' '+attachmentDataValidationOptional+' '+attachmentDataValidation+' field-name="'+label+'"  >'+
                         '<a download="" class="glyphicon glyphicon-download-alt" style="display: none;" id=\'download-'+label.replace(/\s+/g, '')+'\' title="Download">&nbsp;<span id=\'attachmentTitle-'+label.replace(/\s+/g, '')+'\'></span></a>'+
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

function evaluateSlider(label,value){
	var slider = '';
	
	if(value != null && value != '' && value != 'undefined'){
		slider = value;
	}else{
		 slider = document.getElementById("scale-"+label).value;
	}
	 $("#scale-"+label).val(slider);
	$("#demo-"+label).html(slider);
	if(slider == 0){
		$("#scaleValue-"+label).val('');
	}else{
		$("#scaleValue-"+label).val(slider);
	}
	
	
}

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

