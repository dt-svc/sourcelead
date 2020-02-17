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
       '<input type="text" '+readOnly+'  id="points'+addField.i+'" '+dataValidation+' placeholder="Required *" field-name="Points" class="field-points form-control input-sm" >' +
       ' </div> ' +
       '</label>' +
	   '<div class="clearfix"> '
       
       includeAttemptHTML = ''+
	   '<label class="col-md-5 mt10"><div class="col-md-3 lblHeight">Attempt:</div>' +
       ' <div class="col-md-9" id="attempt'+addField.i+'"> ' +
       '<input type="text" '+readOnly+'  id="attempt'+addField.i+'" '+dataValidation+' placeholder="Required *" field-name="Attempt" class="field-attempt form-control input-sm" >' +
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
       var templateName = $("#templateName").val();
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
      	        url: sourceleadUrl+"/saveQuestionnaireTemplate?templateId="+templateId+"&timeLimitMinutes="+timeLimitMinutes+"&timeLimitSeconds="+timeLimitSeconds+"&jsonData="+jsonData+"&jsonStringData="+jsonStringData+"&templateName="+templateName, 
      	       success: function(result) {
    	           if (result === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (result === "SUCCESS") {
		        		//performanceManagement.getFeedbackList();
		        	    location.href = sourceleadUrl+"/questionnaireList?templateName="+templateName,
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
   
    function viewQuestionnaireTemplateForm(){
		
		$("#viewQuestionnaireTemplateFormDiv").show();
		generateViewQuestionnaireForm1(sourceleadUrl, templateUuid)
			var result = "";
			result = result + '<form id="questionnaireTemplate_form" name="questionnaireTemplate_form" enctype="multipart/form-data" method="post">';
			result = result + '<div class="row"><h3 class="col-md-3" id = "questionnaireName">Questionnaire Template</h3> <div class="col-md-9" id = "timerDiv"><i class="fa fa-clock-o mr10"></i><label class="textSuccess textBold" id="countdownTimerLbl">Starting</label></div></div>';
			result = result + "";
			
			
			result = result + '<div class="form-horizontal clearfix">';
			result = result + '<div id="viewQuestionnairesjfb-fields" class="viewTemplate view_templateDiv viewtemplate_paragraph">';
			result = result + '</div>';
			result = result + '</div>';
			
			 result = result + '<div class="form-footer clearfix">';
			result = result + '<div class="pull-right mr">';
			result = result + '<button type="button" id = "submitButton" class="btn btn-sm btn-primary" onClick="cancelViewQuestionnaire();">Cancel</button>';
			result = result + '</div>';
			result = result + '</div>';
			result = result + '</div>';  
			result = result + '</form>';
			 
			$("#viewQuestionnaireTemplateFormDiv").empty();
			$("#viewQuestionnaireTemplateFormDiv").append(result);
		
	}
    
    function generateViewQuestionnaireForm1(sourceleadUrl, templateUuid) {
		var attemptsArray = new Array();
	    //empty out the preview area
	    $("#viewQuestionnairesjfb-fields").empty();
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
	        url:sourceleadUrl+"/loadViewQuestionnaire?templateUuid="+templateUuid, 
	        success: function(result) {
	        	if(result != null && result.jsonString != 'false'){
	        		$("#questionnaireName").html(result.name);
	        	
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
	                     $('#viewQuestionnairesjfb-fields').append(addSavedFieldHTML1(fieldType,v['req'],v['label'],v['hint'],v['dropdown'],v['points'],v['attempt'],attachmentCount));
	                     var $currentField = $('#viewQuestionnairesjfb-fields .sjfb-field').last();
	                     
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
	                                             
	                                             var choiceHTML = '<span><input type="radio" ' + selected + ' name=\''+label.replace(/\s+/g, '')+'\'  onclick="getRadioValue(this,\''+label.replace(/[^A-Z0-9]+/ig, '')+'\');"  value="' + v1['label'] + '">' + v1['label'] + '</span>';
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
	                                             
	                                             var choiceHTML = '<span><input type="checkbox" ' + selected + ' name=\''+label.replace(/\s+/g, '')+'\' onclick="getCheckBoxValue(this,\''+label.replace(/[^A-Z0-9]+/ig, '')+'\')"  value="' + v1['label'] + '">' + v1['label'] + '</span>';
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
	    	                					 evaluatePostSlider(label.replace(/[^A-Z0-9]+/ig, ''),v3['value'])
	    	                				 }
	    	                			 }
	    	                			 else if(fieldType == 'star'){
	    	                				 if(v3['value'] != null && v3['value'] != '' && v3['value'] != 'undefined'){
	    	                					 fillStarValues(v['dropdown'],label.replace(/[^A-Z0-9]+/ig, ''),v3['value'])
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
	                                     var choiceHTML = '<span><input type="radio" name=\''+label.replace(/\s+/g, '')+'\'  onclick="getRadioValue(this,\''+label.replace(/[^A-Z0-9]+/ig, '')+'\');"  value="' + v['label'] + '">' + v['label'] + '</span>';
	                                     if(required == 1 && flag == 0){
	                                    	 choiceHTML = choiceHTML+'<input type="hidden"  id="'+labelTitle.replace(/[^A-Z0-9]+/ig, '')+'radio" data-validation="required" field-name="'+labelTitle+'">';
	                                     }
	                                     $currentField.find(".choices").append(choiceHTML);
	                                 }

	                                 else if (fieldType == 'checkbox') {
	                                     var selected = v['sel'] ? ' checked' : 'checked';
	                                     var choiceHTML = '<span><input type="checkbox" name=\''+label.replace(/\s+/g, '')+'\' onclick="getCheckBoxValue(this,\''+label.replace(/[^A-Z0-9]+/ig, '')+'\')"  value="' + v['label'] + '">' + v['label'] + '</span>';
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
	                			 StarHtml = StarHtml+'<li class="fa fa-star cursorPointer text-muted"  onclick="fillStarValues('+i+',\''+label.replace(/[^A-Z0-9]+/ig, '')+'\');"  id="star-select'+i+label.replace(/[^A-Z0-9]+/ig, '')+'"></li>';
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
	                    	 if(attempt == 0){
	                    		 input = '<input type="text" field-name="'+label+'" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' id=\'text-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' disabled>' ;
	                    	 }else{
	                    		 input =  '<input type="text" field-name="'+label+'" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' id=\'text-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' >' ;
	                    	 }
	                         return '' +
	                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
	                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
	                             '<label for="text-' + uniqueID + '"></label>' +
	                             input +
	                             '</div>' +
	                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' +
	                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';

	                     case 'textarea':
	                    	 if(attempt == 0){
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
	                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' +
	                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';

	                     case 'select':
	                    	 if(attempt == 0){
	                    		 input = '<select id="select-' + uniqueID + '" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' field-name="'+label+'" class="choices choices-select"></select>' ;
	                    	 }else{
	                    		 input =  '<select id="select-' + uniqueID + '" '+datavalidation+' name=\''+label.replace(/\s+/g, '')+'\' field-name="'+label+'" class="choices choices-select"></select>' ;
	                    	 }
	                         return '' +
	                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-select">' +
	                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
	                             '<label for="select-' + uniqueID + '"></label>' +
	                             input +
	                             '</div>'+
	                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' +
	                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';

	                     case 'radio':
	                         return '' +
	                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-radio">' +
	                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
	                             '<label></label>' +
	                             '<div class="clearfix mt"></div>'+
	                             '<div class="choices choices-radio"></div>' +
	                             '</div>'+
	                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' +
	                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';

	                     case 'checkbox':
	                         return '' +
	                             '<div id="sjfb-checkbox-' + uniqueID + '" class="sjfb-field sjfb-checkbox">' +
	                             '<i class="fa fa-info-circle pull-left" title="'+hint+'"></i>'+
	                             '<label class="sjfb-label"></label>' +
	                             '<div class="clearfix mt"></div>'+
	                             '<div class="choices choices-checkbox"></div>' +
	                             '</div>'+
	                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' +
	                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';
	                         

	                     case 'calendar':
	                    	 if(attempt == 0){
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
	                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' +
	                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';
	                     
	                     case 'scale':
	                    	 if(attempt == 0){
	                    		 input = '<input type="hidden" field-type="scale" field-name="'+label+'" '+datavalidation+'   id=\'scaleValue-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' ></span>' +
	                             '<input type="range" min="0" max="'+dropdown+'" value="0" name=\''+label.replace(/\s+/g, '')+'\' onmouseup="evaluatePostSlider(\''+label.replace(/[^A-Z0-9]+/ig, '')+'\');" onclick="evaluatePostSlider(\''+label.replace(/[^A-Z0-9]+/ig, '')+'\');"  id=\'scale-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' class="rangeslider" disabled>' ;
	                    	 }else{
	                    		 input =  '<input type="hidden" field-type="scale" field-name="'+label+'" '+datavalidation+'   id=\'scaleValue-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' ></span>' +
	                             '<input type="range" min="0" max="'+dropdown+'" value="0" name=\''+label.replace(/\s+/g, '')+'\' onmouseup="evaluatePostSlider(\''+label.replace(/[^A-Z0-9]+/ig, '')+'\');" onclick="evaluatePostSlider(\''+label.replace(/[^A-Z0-9]+/ig, '')+'\');"  id=\'scale-'+label.replace(/[^A-Z0-9]+/ig, '')+'\' class="rangeslider">' ;
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
	                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' +
	                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';
	                         
	                     case 'star':
	                    	 if(attempt == 0){
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
	                             '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' +
	                             '<div class="clearfix" style="display:inline-block;"> ,&nbsp&nbsp<strong id="pointId">'+points+'</strong> Point(s)</div>';
	                         
	                     case 'file':
	                    	 if(attempt == 0){
	                    		 input = '<input type="file" id=\'newUploadBtn'+label.replace(/[^A-Z0-9]+/ig, '')+'\'  onchange="uploadFeedbackAttachments(\''+label.replace(/\s+/g, '')+'\',\''+attachmentCount+'\');" class="upload" disabled>' +
	                             '<input type="text" class="form-control" style="display: none;" name=\'docFile'+label.replace(/\s+/g, '')+'\'  id=\'newUploadFile'+label.replace(/[^A-Z0-9]+/ig, '')+'\' '+attachmentDataValidationOptional+' '+attachmentDataValidation+' field-name="'+label+'" disabled>';
	                            }else{
	                    		 input =  '<input type="file" id=\'newUploadBtn'+label.replace(/[^A-Z0-9]+/ig, '')+'\'  onchange="uploadFeedbackAttachments(\''+label.replace(/[^A-Z0-9]+/ig, '')+'\',\''+attachmentCount+'\');" class="upload">' +
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
	                         '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<div class="clearfix" style="display:inline-block;"> :&nbsp&nbsp<strong id = "attemptId">'+attempt+'</strong> Attempt(s)</div>' +
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
	        
	            
	        	}
	        	else{
	        		questionnaireMessage = "Questionnaire Not Availabe !";
	             }
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
	function evaluatePreSlider(uniqueID){
		var slider = document.getElementById("text-"+uniqueID).value;
		$("#demo"+uniqueID).html(slider);
		$("#scaleValue"+uniqueID).val(slider);
		
	}

	function evaluatePostSlider(label, value){
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

	function cancelViewQuestionnaire(){
		location.href = sourceleadUrl+ "/questionnaireList";
	}
    
