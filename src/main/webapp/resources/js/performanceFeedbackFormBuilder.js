/**
 * Simple Jquery Form Builder (SJFB)
 * Copyright (c) 2015 Brandon Hoover, Hoover Web Development LLC (http://bhoover.com)
 * http://bhoover.com/simple-jquery-form-builder/
 * SJFB may be freely distributed under the included MIT license (license.txt).
 */

function customizeFields(sourceleadUrl,templateId){
	
    //If loading a saved form from your database, put the ID here. Example id is "1".
    //Adds new field with animation
    $("#add-field a").click(function(e) {
        e.preventDefault();
        $(addField($(this).data('type'),'Y','Y',sourceleadUrl,templateId)).appendTo('#form-fields').hide().slideDown('fast');
        $('#form-fields').sortable();
    });

    //Removes fields and choices with animation
    $("#sjfb").on("click", ".delete", function() {
    	
    	var $this = $(this);
    	var result = "";
    	result = result +"<div class='project_inn_mask'></div>";
    	result = result +"<div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
    	result = result +"<p>Are you sure you want to delete?</p>";
    	result = result +"<div>&nbsp;&nbsp;&nbsp;</div>";
    	result = result +"<b href='javascript:void(0)'><input type='button' id='deleteCustomField'  value='Ok' /><input class='u_can_color' type='button' id='cancelDelete' value='Cancel'></b>";
    	result = result +"</div>";
    	$("#popUp").append(result);
    	$("#remove_user_pop").slideDown(200);
    	$(".project_inn_mask").fadeIn(200);
    	
    	$("#deleteCustomField").on("click", function() {
             $this.parent().slideUp( "slow", function() {
                 $this.parent().remove()
             });
            $("#popUp").empty();
         	$(".project_inn_mask").fadeOut(200);
    	});
    	
    	$("#cancelDelete").on("click", function() {
           $("#popUp").empty();
           $(".project_inn_mask").fadeOut(200);
   	});
   	
    	
      
    });

    //Makes fields required
    $("#sjfb").on("click", ".toggle-required", function() {
        requiredField($(this));
    });

    //Makes choices selected
    $("#sjfb").on("click", ".toggle-selected", function() {
        selectedChoice($(this));
    });

    //Adds new choice to field with animation
    $("#sjfb").on("click", ".add-choice", function() {
        $(addChoice('Y')).appendTo($(this).prev()).hide().slideDown('fast');
        $('.choices ul').sortable();
    });

    //load saved form
    if(null != templateId && templateId != undefined){
    	 loadSavedForm(templateId,sourceleadUrl);
    }
};


//Add field to builder
function addField(fieldType,value,labelValue,labelName,sourceleadUrl,templateId) {

    var hasRequired, hasChoices,fieldName,hasStar,hasScale,hasFileType;
    var includeRequiredHTML = '';
    var includeHintMsgHTML = '';
    var includeChoicesHTML = '';
    var includeStarHTML = '';
    var includeScaleHTML = '';
    var includeFileTypeHTML = '';
    var includeTextHTML = '';
    var DeleteButtion = '';
    var readOnly = '';
    var dataValidation = '';
    var checkbox = '';
    
    if(templateId == undefined){
    	templateId = 1;
    }
    
    if(addField.i == undefined){
    	 addField.i = 1;
    }else{
    	addField.i++;
    }
    
    switch (fieldType) {
        case 'text':
            hasRequired = true;
            hasChoices = false;
            fieldName = "Text Box";
            fieldImg = "fa fa-text-width"
            break;
        case 'textarea':
            hasRequired = true;
            hasChoices = false;
            fieldName = "Text Area";
            fieldImg = "fa fa-text-width"
            break;
        case 'select':
            hasRequired = true;
            hasChoices = true;
            fieldName = "Select Box";
            fieldImg = "fa fa-caret-down"
            break;
        case 'radio':
            hasRequired = true;
            hasChoices = true;
            fieldName = "Radio Button";
            fieldImg = "fa fa-check-circle-o"
            break;
        case 'checkbox':
            hasRequired = true;
            hasChoices = true;
            fieldName = "Check Box";
            fieldImg = "fa fa-check-square-o"
            break;
        case 'calendar':
            hasRequired = true;
            hasChoices = false;
            fieldName = "Calendar";
            fieldImg = "fa fa-calendar"
            break;
        case 'star':
        	hasStar = true;
            hasRequired = true;
            hasChoices = false;
            fieldName = "Star";
            fieldImg = "fa fa-star-o"
            break;
        case 'scale':
        	hasScale = true;
            hasRequired = true;
            hasChoices = false;
            fieldName = "Scale";
            fieldImg = "fa fa-sliders"
            break;
        case 'comments':
            hasRequired = true;
            hasChoices = false;
            fieldName = "Comments";
            fieldImg = "fa fa-comment-o"
            break;
        case 'file':
            hasRequired = true;
            hasChoices = false;
            hasFileType = true;
            fieldName = "File Upload";
            fieldImg = "fa fa-cloud-upload"
            break;
            
    }

    if(value == 'Y' || value == 'undefined' || null == value || value == ''){
    	DeleteButtion = '<button type="button"  class="delete btn btn-danger"><i class="glyphicon glyphicon-remove"></i></button>'
    }else{
    	DeleteButtion = '<button type="button"  class="btnDisable btn btn-default disabled"><i class="glyphicon glyphicon-remove"></i></button>'
    	
    }
    
    dataValidation = 'data-validation="required validate_Space validate_AlphaNumber';
   
    
    if (hasRequired) {
        includeRequiredHTML = '' +
            '<label class="col-md-5"><div class="col-md-3 lblHeight">Required? </div>' +
            ' <div class="col-md-9 lblHeight"> ' +
            '<input class="toggle-required" '+checkbox+' type="checkbox">' +
            ' </div> ' +
            '</label>'
    }

    if (hasChoices) {
        includeChoicesHTML = '' +
            '<div class="choices col-md-12">' +
            '<ul class="clearfix"></ul>' +
            '<button type="button" class="add-choice btn btn-primary"><i class="fa fa-plus-circle"></i>&nbsp;&nbsp;Add Option</button>' +
            '</div>'
    }
    
    if(hasStar){
    	includeStarHTML = ''+ '<label class="col-md-5"><div class="col-md-3 lblHeight">Type:</div>' +
        ' <div class="col-md-5"> ' +
        '<select  class="field-dropdown form-control input-sm" data-validation="required"  field-name="Star Type">'+
        '<option value="">--Select--</option>      '+
        '<option value="2">2</option>      '+
        '<option value="3">3</option>      '+
        '<option value="4">4</option>      '+
        '<option value="5">5</option>      '+
        '<option value="6">6</option>      '+
        '</select>'+
        ' </div> ' +
        '</label>'
     }
    
    if(hasScale){
    	includeScaleHTML = ''+ '<label class="col-md-5"><div class="col-md-3 lblHeight">Type:</div>' +
        ' <div class="col-md-5"> ' +
        '<select  class="field-dropdown form-control input-sm" data-validation="required"  field-name="Scale Type">'+
        '<option value="">--Select--</option>      '+
        '<option value="10">10</option>      '+
        '<option value="20">20</option>      '+
        '<option value="30">30</option>      '+
        '<option value="40">40</option>      '+
        '<option value="50">50</option>      '+
        '<option value="60">60</option>      '+
        '<option value="70">70</option>      '+
        '<option value="80">80</option>      '+
        '<option value="90">90</option>      '+
        '<option value="100">100</option>    '+
        '</select>'+
        ' </div> ' +
        '</label>'
     }
    
    if(hasFileType){
    	includeFileTypeHTML = ''+ '<label class="col-md-5"><div class="col-md-3 lblHeight">Type:</div>' +
        ' <div class="col-md-5"> ' +
        '<select  class="field-dropdown form-control input-sm" data-validation="required"  field-name="File Type">'+
        '<option value="">--Select--</option>      '+
        '<option value="docx">docx | doc | pdf | txt</option>'+
        '<option value="xls">csv | xls | xlsx</option>'+
        '<option value="png">jpg | jpeg | png | gif</option>'+
        '</select>'+
        '</div>' +
        '</label>'
    }
    
	   includeTextHTML = ''+
	   '<label class="col-md-5"><div class="col-md-3 lblHeight">Question:</div>' +
	   ' <div class="col-md-9" id="label'+addField.i+'"> ' +
	   '<input type="text" '+readOnly+'  id="textLabel'+addField.i+'" '+dataValidation+' placeholder="Required *" field-name="Question" class="field-label form-control input-sm" >' +
	   ' </div> ' +
	   '</label>' +
	   '<div class="clearfix mt10"> ' 
       
       
       includeHintMsgHTML = ''+
	   '<label class="col-md-5"><div class="col-md-3 lblHeight">Help Text:</div>' +
       ' <div class="col-md-9" id="hint'+addField.i+'"> ' +
       '<input type="text" '+readOnly+'  id="hintMsg'+addField.i+'" '+dataValidation+' placeholder="Required *" field-name="Help Text" class="field-hint form-control input-sm" >' +
       ' </div> ' +
       '</label>' +
	   '<div class="clearfix"> '

    return '' +
        '<div class="field" data-type="' + fieldType + '">' +
        DeleteButtion +
        ' <div class="clearfix"> ' + 
        '<h3>' + '<i class="'+fieldImg+'"></i>&nbsp;&nbsp;'  + fieldName + '</h3>' +
        ' </div> ' +
        ' <div class="row"> ' +
        includeTextHTML+
        includeRequiredHTML +
        includeHintMsgHTML +
        includeChoicesHTML +
        includeStarHTML+
        includeScaleHTML+
        includeFileTypeHTML+
        ' </div> ' +
        '</div>'
        
}

//Make builder field required
function requiredField($this) {
    if (!$this.parents('.field').hasClass('required')) {
        //Field required
        $this.parents('.field').addClass('required');
        $this.attr('checked','checked');
    } else {
        //Field not required
        $this.parents('.field').removeClass('required');
        $this.removeAttr('checked');
    }
}

function selectedChoice($this) {
    if (! $this.parents('li').hasClass('selected')) {

        //Only checkboxes can have more than one item selected at a time
        //If this is not a checkbox group, unselect the choices before selecting
        if ($this.parents('.field').data('type') != 'checkbox') {
            $this.parents('.choices').find('li').removeClass('selected');
            $this.parents('.choices').find('.toggle-selected').not($this).removeAttr('checked');
        }

        //Make selected
        $this.parents('li').addClass('selected');
        $this.attr('checked','checked');

    } else {

        //Unselect
        $this.parents('li').removeClass('selected');
        $this.removeAttr('checked');

    }
}

//Builder HTML for select, radio, and checkbox choices
function addChoice(labelValue) {
	  
    return '' +
        '<li>' +
        '<div class="row">' +
        '<label class="col-md-5 lblClass"> <div class="col-md-3 lblHeight">Option: </div> ' +
        '<div class="col-md-9">' +
        '<input type="text" data-validation="required validate_Space" placeholder="Required *" field-name="Option Label" class="choiceInput choice-label form-control input-sm">' +
        '</div>' +
        '</label>' +
        
        
       /* '<label class="col-md-2 lblClass"><div class="col-md-9 choice-padLeft lblHeight"> Selected? </div> ' +
        '<div class="col-md-3 choicePadLeft lblHeight"> ' +
        '<input class="toggle-selected" type="checkbox">' +
        '</div>' +*/
        
        '</label>' +
        '<button type="button" class="delete pull-left choicebtn-delete"><i class="fa fa-times text-danger"></i></button>' +
        '</div>' +
        '</div>' +
        '</li>'
}


function loadSavedForm(templateId,sourceleadUrl) {
	
    $.ajax({
    	type: "GET",
        url: sourceleadUrl+"/loadSavedPerformanceFeedbackTemplate?templateId="+templateId, 
        success: function(result) {
        	 $("#templateName").val(result.templateName);
        	 $("textarea[id='templateDescription']").text(result.description);
        	 $("#templateId").val(templateId);
        	 
        	 var data = eval(result.jsonString);
        	 if (data) {
                 //go through each saved field object and render the builder
                 $.each( data, function( k, v ) {
                     //Add the field
                     $(addField(v['type'],v['isRemoved'],v['isLabelModified'],v['label'],sourceleadUrl,templateId,'N')).appendTo('#form-fields').hide().slideDown('fast');
                     var $currentField = $('#form-fields .field').last();

                     //Add the label
                     $currentField.find('.field-label').val(v['label']);
                     
                    //Add the hint
                     $currentField.find('.field-hint').val(v['hint']);
                     
                    //Add the type dropdown
                     $currentField.find('.field-dropdown').val(v['dropdown']);
                     

                     //Is it required?
                     if (v['req']) {
                         requiredField($currentField.find('.toggle-required'));
                     }

                     //Any choices?
                     if (v['choices']) {
                         $.each( v['choices'], function( k, v ) {
                             //add the choices
                             $currentField.find('.choices ul').append(addChoice(v['isLabelModified']));

                             //Add the label
                             $currentField.find('.choice-label').last().val(v['label']);

                             //Is it selected?
                             if (v['sel']) {
                                 selectedChoice($currentField.find('.toggle-selected').last());
                             }
                         });
                     }

                 });

                 $('#form-fields').sortable();
                 $('.choices ul').sortable();
             }
        },
    });
   
}



function validateLabelName(i,templateId,sourceleadUrl,labelValidationCheck){
	var labelName = $("#textLabel"+i).val();
	if(labelName){
		$.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/validateSubmissionTemplateLabelName?templateId="+templateId+"&labelName="+labelName, 
	        success: function(result) {
	       	 if(result == 'Success'){
		    		$("#save_button").removeAttr("disabled");
		    		$('input#textLabel'+i).removeAttr( "class record-exist record-exist-errormsg");
					$('#label'+i).find('p.jquery_form_error_message').remove();
					$('input#textLabel'+i).attr('class','field-label valid form-control');
					$( "#save_button" ).removeAttr("style");
					$( "#save_button" ).addClass('btn btn-primary mr').val('Save');
					$( "#save_button" ).click(function() {
					});
					
		    	}
		    	else if(result == 'Existed'){
	 		        $("#save_button").attr("disabled", "disabled");
		    		$('#label'+i).find('p.jquery_form_error_message').remove(); 
		    		$('input#textLabel'+i).attr('class','field-label error form-control');
		    		$('input#textLabel'+i).after("<p class='jquery_form_error_message'>Duplicate Field Name</p>");
		    		$('input#textLabel'+i).attr('record-exist','yes');
		    		$('input#textLabel'+i).attr('record-exist-errorMsg',' Duplicate Field Name'); 
					$("#save_button" ).addClass('btn btn-primary mr').val('Save');
		    	} 
	        },
	    });
	}
}



//Removes fields and choices with animation
$("#sjfb").on("click", ".delete", function() {
	
	var $this = $(this);
	var result = "";
	result = result +"<div class='project_inn_mask'></div>";
	result = result +"<div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"<p>Are you sure you want to delete?</p>";
	result = result +"<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"<b href='javascript:void(0)'><input type='button' id='deleteCustomField'  value='Ok' /><input class='u_can_color' type='button' id='cancelDelete' value='Cancel'></b>";
	result = result +"</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
	
	$("#deleteCustomField").on("click", function() {
         $this.parent().slideUp( "slow", function() {
             $this.parent().remove()
         });
        $("#popUp").empty();
     	$(".project_inn_mask").fadeOut(200);
	});
	
	$("#cancelDelete").on("click", function() {
       $("#popUp").empty();
       $(".project_inn_mask").fadeOut(200);
	});
	
	
  
});

