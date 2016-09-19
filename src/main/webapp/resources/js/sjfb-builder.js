/**
 * Simple Jquery Form Builder (SJFB)
 * Copyright (c) 2015 Brandon Hoover, Hoover Web Development LLC (http://bhoover.com)
 * http://bhoover.com/simple-jquery-form-builder/
 * SJFB may be freely distributed under the included MIT license (license.txt).
 */

function customizeFields(clientLocationId,sourceleadUrl){

    //If loading a saved form from your database, put the ID here. Example id is "1".
    //Adds new field with animation
    $("#add-field a").click(function(e) {
        e.preventDefault();
        $(addField($(this).data('type'),'Y','Y')).appendTo('#form-fields').hide().slideDown('fast');
        $('#form-fields').sortable();
    });

    //Removes fields and choices with animation
    $("#sjfb").on("click", ".delete", function() {
    	
    	var $this = $(this);
    	var result = "";
    	result = result +"<div class='project_inn_mask'></div>";
    	result = result +"<div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
    	result = result +"<p>Are you sure ?</p>";
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
    loadForm(clientLocationId,sourceleadUrl);

};


//Add field to builder
function addField(fieldType,value,labelValue) {

    var hasRequired, hasChoices,hasAutocomplete,fieldName;
    var includeRequiredHTML = '';
    var includeChoicesHTML = '';
    var includeAutocompleteHTML = '';
    var includeTextHTML = '';
    var DeleteButtion = '';
    var readOnly = '';
    var checkbox = '';
    
    switch (fieldType) {
        case 'text':
            hasRequired = true;
            hasChoices = false;
            fieldName = "Text Box";
            break;
        case 'textarea':
            hasRequired = true;
            hasChoices = false;
            fieldName = "Text Area";
            break;
        case 'select':
            hasRequired = true;
            hasChoices = true;
            fieldName = "Select Box";
            break;
        case 'radio':
            hasRequired = true;
            hasChoices = true;
            fieldName = "Radio Button";
            break;
        case 'checkbox':
            hasRequired = false;
            hasChoices = true;
            fieldName = "Check Box";
            break;
        case 'calendar':
            hasRequired = true;
            hasChoices = false;
            fieldName = "Calendar";
            break;
        case 'autocomplete':
        	hasAutocomplete = true;
            hasRequired = true;
            hasChoices = false;
            fieldName = "Auto Complete";
            break;
            
    }

    if(value == 'Y' || value == 'undefined' || null == value || value == ''){
    	DeleteButtion = '<button type="button"  class="delete btn btn-danger"><i class="glyphicon glyphicon-remove"></i></button>'
    }else{
    	DeleteButtion = '<button type="button"  class="btnDisable btn btn-default disabled"><i class="glyphicon glyphicon-remove"></i></button>'
    	checkbox = 'disabled="disabled"'
    }
    
    if(labelValue == 'N' || labelValue == 'undefined' || null == labelValue || labelValue == ''){
    	readOnly = 'readonly="readonly"'
    }
   
    
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
    
    if(hasAutocomplete){
    	includeAutocompleteHTML = ''+ '<label class="col-md-5"><div class="col-md-2 lblHeight">Type:</div>' +
        ' <div class="col-md-10"> ' +
        '<select  class="field-label form-control input-sm" data-validation="required"  field-name="Autocomplete">'+
        '<option value="country">Country</option>      '+
        '<option value="countryState">Country-State</option>      '+
        '<option value="countryStateCity">Country-State-City</option>'+
        '</select>'+
        ' </div> ' +
        '</label>'
     }
    else{
    	 includeTextHTML = ''+
		   '<label class="col-md-5"><div class="col-md-2 lblHeight">Label:</div>' +
	       ' <div class="col-md-10"> ' +
	       '<input type="text" '+readOnly+' data-validation="required validate_Space" placeholder="Required *" field-name="Label" class="field-label form-control input-sm" >' +
	       ' </div> ' +
	       '</label>' 
    }

    return '' +
        '<div class="field" data-type="' + fieldType + '">' +
        DeleteButtion +
        ' <div class="clearfix"> ' + 
        '<h3>' + '<i class="fa fa-check-circle"></i>&nbsp;'  + fieldName + '</h3>' +
        ' </div> ' +
        ' <div class="row"> ' +
        includeAutocompleteHTML+
        includeTextHTML+
        includeRequiredHTML +
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
	var readOnly = '';
	  if(labelValue == 'N' || labelValue == 'undefined' || null == labelValue || labelValue == ''){
	    	readOnly = 'readonly="readonly"'
	    }
	  
    return '' +
        '<li>' +
        '<div class="row">' +
        '<label class="col-md-5 lblClass"> <div class="col-md-2 lblHeight">Option: </div> ' +
        '<div class="col-md-10">' +
        '<input type="text" '+readOnly+' data-validation="required validate_Space" placeholder="Required *" field-name="Option Label" class="choiceInput choice-label form-control input-sm">' +
        '</div>' +
        '</label>' +
        
        
        '<label class="col-md-2 lblClass"><div class="col-md-9 choice-padLeft lblHeight"> Selected? </div> ' +
        '<div class="col-md-3 choicePadLeft lblHeight"> ' +
        '<input class="toggle-selected" type="checkbox">' +
        '</div>' +
        
        '</label>' +
        '<button type="button" class="delete pull-left choicebtn-delete"><i class="fa fa-times text-danger"></i></button>' +
        '</div>' +
        '</div>' +
        '</li>'
}

//Loads a saved form from your database into the builder
function loadForm(clientLocationId,sourceleadUrl) {
        $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/loadSubmissionTemplate?clientLocationId="+clientLocationId, 
	        success: function(data) {
	        	 var data = eval(data);
	        	 if (data) {
	                 //go through each saved field object and render the builder
	                 $.each( data, function( k, v ) {
	                     //Add the field
	                     $(addField(v['type'],v['isRemoved'],v['isLabelModified'])).appendTo('#form-fields').hide().slideDown('fast');
	                     var $currentField = $('#form-fields .field').last();

	                     //Add the label
	                     $currentField.find('.field-label').val(v['label']);

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

