/**
 * Simple Jquery Form Builder (SJFB)
 * Copyright (c) 2015 Brandon Hoover, Hoover Web Development LLC (http://bhoover.com)
 * http://bhoover.com/simple-jquery-form-builder/
 * SJFB may be freely distributed under the included MIT license (license.txt).
 */

var idValue ="";
function customizeFields(vmsUrl,templateUuid){
	
    //If loading a saved form from your database, put the ID here. Example id is "1".
    //Adds new field with animation
    $('#add-field9'+idValue+' a').click(function(e) {
    	var idText =this.id.split("9")[1];
    	console.log(idText);
    	idValue = idText;
        e.preventDefault();
        $(addField($(this).data('type'),'Y','Y',vmsUrl,templateUuid)).appendTo('#form-fields'+idValue).hide().slideDown('fast');
        $('#form-field '+idValue).sortable();
    });

    //Removes fields and choices with animation
    $("#"+idValue).on("click", ".delete", function() {
    	
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

    $("#"+idValue).on("click", ".toggle-required", function() {
        requiredField($(this));
    });

    //Makes choices selected
    $("#"+idValue).on("click", ".toggle-selected", function() {
        selectedChoice($(this));
    });

    //Adds new choice to field with animation
    $("#"+idValue).on("click", ".add-choice", function() {
        $(addChoice('Y')).appendTo($(this).prev()).hide().slideDown('fast');
        $('.choices ul').sortable();
    });
    /*
    //Makes fields required
    $("#sjfb-wrap").on("click", ".toggle-required", function() {
        requiredField($(this));
    });

    //Makes choices selected
    $("#sjfb-wrap").on("click", ".toggle-selected", function() {
        selectedChoice($(this));
    });

    //Adds new choice to field with animation
    $("#sjfb-wrap").on("click", ".add-choice", function() {
        $(addChoice('Y')).appendTo($(this).prev()).hide().slideDown('fast');
        $('.choices ul').sortable();
    });*/

   //load saved form
   /* if(null != templateUuid && templateUuid != undefined){
    	 loadSavedForm(templateUuid,sourceleadUrl); // commented existing code.
    }*/
};


//Add field to builder
function addField(fieldType,value,labelValue,labelName,vmsUrl,templateId) {

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
    	DeleteButtion = '<button type="button"  class="delete btn btn-danger pull-right"><i class="glyphicon glyphicon-remove"></i></button>'
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
	   '<label class="col-md-5"><div class="col-md-3 lblHeight">Label:</div>' +
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
        includeStarHTML+
        includeScaleHTML+
        includeFileTypeHTML+
        includeChoicesHTML +
        ' </div> ' +
        '</div>'
  //      console.log("includeTextHTML")  ;
   //     console.log(includeTextHTML)  ;
   //     console.log("includeStarHTML")  ;
    //    console.log(includeStarHTML)  ;
}

//Make builder field required
function requiredField($this) {
	//console.log("inisde function call ???");
	//console.log($this);
    if (!$this.parents('.field').hasClass('required')) {
        //Field required
    	//console.log("adding required");
        $this.parents('.field').addClass('required');
        $this.attr('checked','checked');
    } else {
        //Field not required
    //	console.log("removing required");
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

function buildSavedComponentForm(formId){
	console.log("buildSavedComponentForm called for "+formId);
	  var selCompDivText = formId;
	 var result ="";
	result= result+'<div class="col-md-10" id ="'+formId+'">';
 	result= result+'  <div class="quetionwidget mb">';
 	result= result+' <div class="clearfix row">';
 	result= result+' <div class="col-md-2 col-md-offset-10">';
 	result= result+'     <div class="dropdown">';
 	result= result+'    <button class="btn btn-success dropdown-toggle btn-block" type="button" data-toggle="dropdown" id="addFiled-dropdown'+formId+'"  aria-expanded="false">Add Type';
 	result= result+'   <span class="caret"></span></button>';
 	result= result+'     <ul id="add-field9'+formId+'" class="dropdown-menu pull-right">';
 	result= result+'        <li><a id="add-text9'+formId+'" data-type="text" href="javascript:void(0)"><span class="fa fa-text-width">&nbsp;&nbsp;</span>Text Box</a>';
 	result= result+'         </li><li><a id="add-textarea9'+formId+'" data-type="textarea" href="javascript:void(0)"><span class="fa fa-text-width">&nbsp;&nbsp;</span>Text Area</a></li>';
 	result= result+'        <li><a id="add-select9'+formId+'" data-type="select" href="javascript:void(0)"><span class="fa fa-caret-down">&nbsp;&nbsp;&nbsp;</span>Select Box</a></li>';
 	result= result+'         <li><a id="add-checkbox9'+formId+'"  data-type="checkbox" href="javascript:void(0)"><span class="fa fa-check-square-o">&nbsp;&nbsp;</span>Check Box</a></li>';
 	result= result+'          <li><a id="add-radio9'+formId+'"  data-type="radio" href="javascript:void(0)"><span class="fa fa-check-circle-o">&nbsp;&nbsp;</span>Radio Button</a></li>';
 	result= result+'         <li><a id="add-comments9'+formId+'"  data-type="comments" href="javascript:void(0)"><span class="fa fa-comment-o">&nbsp;&nbsp;</span>Comments</a></li>';
 	result= result+'          <li><a id="add-star9'+formId+'" data-type="star" href="javascript:void(0)"><span class="fa fa-star-o">&nbsp;&nbsp;</span>Star</a></li>';
 	result= result+'          <li><a id="add-scale9'+formId+'"  data-type="scale" href="javascript:void(0)"><span class="fa fa-sliders">&nbsp;&nbsp;</span>Scale</a></li>';
 	result= result+'         <li><a id="add-calendar9'+formId+'"  data-type="calendar" href="javascript:void(0)"><span class="fa fa-calendar">&nbsp;&nbsp;</span>Calendar</a></li>';
 	result= result+'       <li><a id="add-file9'+formId+'"  data-type="file" href="javascript:void(0)"><span class="fa fa-cloud-upload">&nbsp;&nbsp;</span>File Upload</a></li>';
 	result= result+'     </ul>';
 	result= result+'    </div>';
 	result= result+'    </div>';
 	result= result+'  </div>';
   
 	result= result+'      <div class="checkbox-new">';
 	result= result+'         <label for="address" class="checkbox-custom-label"></label>';
 	result= result+' <h4>'+selCompDivText.replace("_"," ");+'</h4> ';
 	result= result+'      </div>';
 	
 	
 	result= result+ ' <div id="sjfb-wrap"> ';
 	result= result+ '<form id="'+formId+'" novalidate> ';
 	
 	result= result+'     <div class="clearfix"></div>';
	result= result+'     <div class="clearfix row">';
    
    result= result+'      <label class="col-md-5"><div class="col-md-3 lblHeight">Sent to:</div>';
 	result= result+'        <div class="col-md-9">';
 	result= result+'              <input type="text" id="sentTo'+formId+'" data-validation="validate_email" data-validation-optional="true" field-name="Sentto" class="field-label form-control input-sm">';
 	result= result+'      </div>';
 	result= result+'       </label>';
 	result= result+'     </div>';
 	
    result= result+'     <div id="form-fields'+formId+'"> ';
    
 	result= result+'     </div>';
 	result= result+'   </div>';
 	result= result+'   </form>';
 	result= result+'   </div>';
 	if($("#popUp").length == 0){
	    result= result+'  	<div id="popUp"></div>';
 	}
 	if($("#choicepopUp").length == 0){
	    result= result+'   <div id="choicepopUp"></div>';
 	}
 	$('#parent_bgc_div').prepend(result);
 	

    $("#"+formId).on("click", ".delete", function() {
    	
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
    idValue = formId; // registering event for adding elements through type button.
    customizeFields(vmsUrl,"");
}

function loadSavedBgcForm(templateId,formId) {
	
    $.ajax({
    	type: "GET",
        url: vmsUrl+"/bgc/getTemplateComponentJsonMap?templateId="+templateId+"&formId="+formId, 
        success: function(result) {
        	buildSavedComponentForm(formId);
             $("#sentTo"+formId).val(result.sentTo);
           /*  console.log("SUCESSS"+formId);
        	 console.log(result.sentTo);
        	 console.log("result.jsonString"+result.jsonString);*/
        	 var data = eval(result.jsonString);
        	 if (data) {
                 //go through each saved field object and render the builder
                 $.each( data, function( k, v ) {
                     //Add the field
                     $(addField(v['type'],v['isRemoved'],v['isLabelModified'],v['label'],vmsUrl,templateUuid,'N')).appendTo('#form-fields'+formId).hide().slideDown('fast');
                     var $currentField = $('#form-fields'+formId+' .field').last();

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

                 $('#form-fields'+formId).sortable();
                 $('.choices ul'+formId).sortable();
             }
        //	  alert("after success");
        },
      
    });
   
}






//Removes fields and choices with animation
$("#"+idValue).on("click", ".delete", function() {
	
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



//$('body').on('submit', '#sjfb', function(e){
 function saveComponentJsonMap(templateId){
	 $('#parent_bgc_div form').each(function( index ) {
		 var formId = this.id;
		// console.log("inside function call test222 "+formId);
	  if ($(formId).validate(false, validationSettings)){
        var frm = $('#'+formId).serializeFormJSON();
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
        $('#'+formId+' .field').each(function() {
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
	                dropdown : fieldDropDown,
	                req: fieldReq,
	                choices: choices,
	                isRemoved : isRemoved,
            });

        });
        
     /*   console.log("myArray");
        console.log(myArray);
        
        console.log("fields");
        console.log(fields);*/
        
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
        
        //Save form to database
        //Demo doesn't actually save. Download project files for save
        var con2 = JSON.stringify(fields);
        var jsonStringData = encodeURIComponent(con2);
     //   console.log("jsonStringData3333>   "+jsonStringData);
        var sentTo = $("#sentTo"+formId).val();
       // console.log(sentTo);
        if(submitTemplate){
        //	$("#save_button").attr("disabled", "disabled");
        //	$("#save_button" ).addClass('button').text('Processing..');
     //   console.log(vmsUrl + "/saveTemplateComponentJsonMap?templateId="+templateId+"&formId="+formId+"&jsonStringData="+jsonStringData)
      	  $.ajax({
      	    	type: "POST",
      	        url: vmsUrl +"/bgc/saveTemplateComponentJsonMap?templateId="+templateId+"&formId="+formId+"&sentTo="+sentTo+"&jsonStringData="+jsonStringData, 
      	       success: function(result) {
      	    	   result ="SUCCESS";
    	           if (result === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (result === "SUCCESS") {
		        		 console.log("Compnent Saved Successfullly");
		        		if(index == $('#parent_bgc_div form').length-1){
		        			location.replace(vmsUrl+"/bgc/bgcOrgTemplateList");	
		        		}
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
		
 }// close of saveComponentJsonMap>>>>>>>
    	
   	$('body').on('blur', '#sjfb-wrap', function() {
        $("#sjfb-wrap").showHelpOnFocus();
        $("#sjfb-wrap").validateOnBlur(false, validationSettings);  
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
   
    