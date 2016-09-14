/**
 * Simple Jquery Form Builder (SJFB)
 * Copyright (c) 2015 Brandon Hoover, Hoover Web Development LLC (http://bhoover.com)
 * http://bhoover.com/simple-jquery-form-builder/
 * SJFB may be freely distributed under the included MIT license (license.txt).
 */

function customizeFields(){
	

    //If loading a saved form from your database, put the ID here. Example id is "1".
    //Adds new field with animation
    $("#add-field a").click(function(e) {
        e.preventDefault();
        $(addField($(this).data('type'))).appendTo('#form-fields').hide().slideDown('fast');
        $('#form-fields').sortable();
    });

    //Removes fields and choices with animation
    $("#sjfb").on("click", ".delete", function() {
        if (confirm('Are you sure?')) {
            var $this = $(this);
            $this.parent().slideUp( "slow", function() {
                $this.parent().remove()
            });
        }
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
        $(addChoice()).appendTo($(this).prev()).hide().slideDown('fast');
        $('.choices ul').sortable();
    });

    

    //load saved form
    loadForm(formID);

};

//Add field to builder
function addField(fieldType) {

    var hasRequired, hasChoices;
    var includeRequiredHTML = '';
    var includeChoicesHTML = '';

    switch (fieldType) {
        case 'text':
            hasRequired = true;
            hasChoices = false;
            break;
        case 'textarea':
            hasRequired = true;
            hasChoices = false;
            break;
        case 'select':
            hasRequired = true;
            hasChoices = true;
            break;
        case 'radio':
            hasRequired = true;
            hasChoices = true;
            break;
        case 'checkbox':
            hasRequired = false;
            hasChoices = true;
            break;
        case 'agree':
            //required "agree to terms" checkbox
            hasRequired = false;
            hasChoices = false;
            break;
    }

    if (hasRequired) {
        includeRequiredHTML = '' +
            '<label class="col-md-5"><div class="col-md-3">Required? </div>' +
            ' <div class="col-md-9"> ' +
            '<input class="toggle-required" type="checkbox">' +
            ' </div> ' +
            '</label>'
    }

    if (hasChoices) {
        includeChoicesHTML = '' +
            '<div class="choices col-md-12">' +
            '<ul class="clearfix"></ul>' +
            '<button type="button" class="add-choice btn btn-primary"><i class="fa fa-plus-circle"></i>&nbsp;&nbsp;Add Choice</button>' +
            '</div>'
    }

    return '' +
        '<div class="field" data-type="' + fieldType + '">' +
        '<button type="button"  class="delete btn btn-danger"><i class="glyphicon glyphicon-remove"></i></button>' +
        ' <div class="clearfix mt mb"> ' + 
        '<h3 class="btn btn-success">' + '<i class="fa fa-check-circle"></i>&nbsp;'  + fieldType + '</h3>' +
        ' </div> ' +
        ' <div class="row"> ' +
        '<label class="col-md-5"><div class="col-md-2">Label:</div>' +
        ' <div class="col-md-10"> ' +
        '<input type="text" data-validation="required" field-name="Label" class="form-control input-sm" >' +
        ' </div> ' +
        '</label>' +
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
function addChoice() {
    return '' +
        '<li>' +
        '<div class="row">' +
        '<label class="col-md-5 lblClass"> <div class="col-md-2">Choice: </div> ' +
        '<div class="col-md-10 choiceInput">' +
        '<input type="text" data-validation="required" field-name="Choice Label" class="form-control input-sm">' +
        '</div>' +
        '</label>' +
        
        
        '<label class="col-md-2 lblClass"><div class="col-md-9 choice-padLeft"> Selected? </div> ' +
        '<div class="col-md-3 choicePadLeft"> ' +
        '<input class="toggle-selected" type="checkbox">' +
        '</div>' +
        
        '</label>' +
        '<button type="button" class="delete pull-left choicebtn-delete"><i class="fa fa-times text-danger"></i></button>' +
        '</div>' +
        '</div>' +
        '</li>'
}

//Loads a saved form from your database into the builder
function loadForm(formID) {
    $.getJSON('sjfb-load.php?form_id=' + formID, function(data) {
        if (data) {
            //go through each saved field object and render the builder
            $.each( data, function( k, v ) {
                //Add the field
                $(addField(v['type'])).appendTo('#form-fields').hide().slideDown('fast');
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
                        $currentField.find('.choices ul').append(addChoice());

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
    });
}