/**
 * Simple Jquery Form Builder (SJFB)
 * Copyright (c) 2015 Brandon Hoover, Hoover Web Development LLC (http://bhoover.com)
 * http://bhoover.com/simple-jquery-form-builder/
 * SJFB may be freely distributed under the included MIT license (license.txt).
 */

function customizeFields(sourceleadUrl,templateUuid){
	
    //If loading a saved form from your database, put the ID here. Example id is "1".
    //Adds new field with animation
    $("#add-field a").click(function(e) {
        e.preventDefault();
        $(addField($(this).data('type'),'Y','Y',sourceleadUrl,templateUuid)).appendTo('#form-fields').hide().slideDown('fast');
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
    if(null != templateUuid && templateUuid != undefined){
    	 loadSavedForm(templateUuid,sourceleadUrl); // commented existing code.
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
    
    dataValidation = 'data-validation="required validate_Space validate_Name';
   
    
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
       
       
       includePointsHTML = ''+
	   '<label class="col-md-5"><div class="col-md-3 lblHeight">Points:</div>' +
       ' <div class="col-md-9" id="points'+addField.i+'"> ' +
       '<input type="text" '+readOnly+'  id="points'+addField.i+'" '+dataValidation+' placeholder="Required *" field-name="Help Text" class="field-points form-control input-sm" >' +
       ' </div> ' +
       '</label>' +
	   '<div class="clearfix"> '
       
       includeAttemptHTML = ''+
	   '<label class="col-md-5 mt10"><div class="col-md-3 lblHeight">Attempt:</div>' +
       ' <div class="col-md-9" id="attempt'+addField.i+'"> ' +
       '<input type="text" '+readOnly+'  id="attempt'+addField.i+'" '+dataValidation+' placeholder="Required *" field-name="Help Text" class="field-attempt form-control input-sm" >' +
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
        includeStarHTML+
        includeScaleHTML+
        includeFileTypeHTML+
        includePointsHTML+
        includeAttemptHTML+
        includeChoicesHTML +
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


function loadSavedForm(templateUuid,sourceleadUrl) {
    $.ajax({
    	type: "GET",
        url: sourceleadUrl+"/loadSavedQuestionnaireTemplate?templateUuid="+templateUuid, 
        success: function(result) {
             $("#templateName").val(result.name);
        	 $("textarea[id='templateDescription']").text(result.description);
        	 $("#templateId").val(result.id);
        	 $("#minutes").val(result.timeLimitMinutes);
        	 $("#seconds").val(result.timeLimitSeconds);
        	 
        	 var data = eval(result.jsonString);
        	 if (data) {
                 //go through each saved field object and render the builder
                 $.each( data, function( k, v ) {
                     //Add the field
                     $(addField(v['type'],v['isRemoved'],v['isLabelModified'],v['label'],sourceleadUrl,templateUuid,'N')).appendTo('#form-fields').hide().slideDown('fast');
                     var $currentField = $('#form-fields .field').last();

                     //Add the label
                     $currentField.find('.field-label').val(v['label']);
                     
                    //Add the hint
                     $currentField.find('.field-hint').val(v['hint']);
                     
                     $currentField.find('.field-points').val(v['points']);
                     
                     $currentField.find('.field-attempt').val(v['attempt']);
                     
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



function validateQuestionnaireName(){
	var questionnaireName = $("#templateName").val();
	if(questionnaireName){
		$.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/validateQuestionnaireName?questionnaireName="+questionnaireName, 
	        success: function(result) {
	       	 if(result == 'Success'){
		    		$("#save_button").removeAttr("disabled");
		    		$('#templateName').removeAttr( "class record-exist record-exist-errormsg");
					$('#templateNameDiv').find('p.jquery_form_error_message').remove();
					$('#templateName').attr('class','field-label valid form-control');
					$( "#save_button" ).removeAttr("style");
					$( "#save_button" ).addClass('btn btn-primary mr').val('Save');
					
		    	}
		    	else if(result == 'Existed'){
	 		        $("#save_button").attr("disabled", "disabled");
		    		$('#templateName').find('p.jquery_form_error_message').remove(); 
		    		$('#templateName').attr('class','field-label error form-control');
		    		$('#templateName').after("<p class='jquery_form_error_message'>Duplicate Questionnare Name</p>");
		    		$('#templateName').attr('record-exist','yes');
		    		$('#templateName').attr('record-exist-errorMsg',' Duplicate Questionnare Name'); 
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



$('body').on('submit', '#sjfb', function(e){
	  if ($(this).validate(false, validationSettings)){
        e.preventDefault();
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
            
          //field point
            fieldPoints = $this.find('.field-points').val(); 
         
           //field attempt
            fieldAttempt = $this.find('.field-attempt').val(); 
            
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
	                points: fieldPoints,
	                attempt:fieldAttempt,
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
       var templateId = document.getElementById("templateId").value;
        if(templateId == 'undefined' || templateId == null || templateId == ''){
        	templateId = '';
        }
        var timeLimitMinutes = $("#minutes").val();
        var timeLimitSeconds = $("#seconds").val();
        //Save form to database
        //Demo doesn't actually save. Download project files for save
        var con2 = JSON.stringify(fields);
        var jsonStringData = encodeURIComponent(con2);
        if(submitTemplate){
        	$("#save_button").attr("disabled", "disabled");
        	$("#save_button" ).addClass('button').text('Processing..');
       
      	  $.ajax({
      	    	type: "POST",
      	        url: sourceleadUrl+"/saveQuestionnaireTemplate?templateId="+templateId+"&timeLimitMinutes="+timeLimitMinutes+"&timeLimitSeconds="+timeLimitSeconds+"&jsonData="+jsonData+"&jsonStringData="+jsonStringData, 
      	       success: function(result) {
    	           if (result === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (result === "SUCCESS") {
		        		//performanceManagement.getFeedbackList();
		        	    location.href = sourceleadUrl+"/questionnaireList"
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
    });
    	
   	$('body').on('blur', '#sjfb', function() {
        $("#sjfb").showHelpOnFocus();
        $("#sjfb").validateOnBlur(false, validationSettings);  
    });
   	  
    
    function alertMessage(message){
        var result = "";
    	result = result +"	<div class='project_inn_mask'></div>";
    	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
    	result = result +"		<p>"+message+"</p>";
    	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
    	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
    	result = result +"	</div>";
    	$("#choicepopUp").append(result);
    	$("#remove_user_pop").slideDown(200);
    	$(".project_inn_mask").fadeIn(200);
    }
    
    function cancel(){
    	$("#choicepopUp").empty();
    	$(".project_inn_mask").fadeOut(200);
    }
   
