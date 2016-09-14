
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/chosen.min.css">
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css">
<link rel="stylesheet" href="resources/css/style.css" />
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/bootstrap-select.min.js"></script>
<script src="resources/js/jquery-ui.min.js" ></script>



<script type="text/javascript">
	function addSubmissionTemplateFields(clientLocationId) {
		
		var result = "";
		
		result = result + '<section class="content-wraper">';
		result = result + '<div class="content-dashboard clearfix">';
		result = result + '<div id="loading"></div>';
		result = result + '<section class="content-header clearfix">';
		result = result + '<div class="pull-left">';
		result = result + '<h2>Submission Template</h2>';
		        result = result + '</div>';
		        result = result + '<div id="sjfb-wrap">';

		           result = result + '<form id="sjfb" novalidate>';
		           result = result + '<input type="hidden" id="clientLocationId" value="'+clientLocationId+'">';
		           
		            result = result  +'	<div class="clearfix mb10">';
		            result = result  +'<label class="col-md-3">Template Name<span class="text-danger">&nbsp;*</label>';
			    	result = result  +'	<div class="col-md-12">';
			    	result = result  +'	<input type="text" class="form-control input-sm" value="" id="templateName" name="templateName" field-name="Template Name" data-validation="required validate_Space validate_length length2-100">';
			    	result = result  +'	</div>';
			    	result = result  +'	</div>';
			    	
			    	result = result  +'	<div class="clearfix mb10">';
			    	 result = result  +'<label class="col-md-3">Description<span class="text-danger">&nbsp;*</label>';
			    	result = result  +'	  <div class="col-md-12">';
			    	result = result  +'	     <textarea rows="5" name="description" field-name="Description" data-validation="required" placeholder="Description *" class="form-control"></textarea>';
			    	result = result  +'	   </div>';
			    	result = result  +'	</div>';
			    	
			    	result = result + '<div class="add-wrap">';
			        result = result + '<h3>Add Field:</h3>';
			        result = result + '<ul id="add-field">';
			        result = result + '<li><a id="add-text" data-type="text" href="javascript:void(0)">Text Box</a></li>';
			        result = result + '<li><a id="add-textarea" data-type="textarea" href="javascript:void(0)">Text Area</a></li>';
			        result = result + '<li><a id="add-select" data-type="select" href="javascript:void(0)">Select Box</a></li>';
			        result = result + '<li><a id="add-radio" data-type="radio" href="javascript:void(0)">Radio Button</a></li>';
			        result = result + ' <li><a id="add-checkbox" data-type="checkbox" href="javascript:void(0)">Check Box</a></li>';
		            result = result + '</ul>';
			        result = result + '</div>';
		           
		           result = result + '<div id="form-fields">';
		           result = result + '</div>';
		           result = result + ' <input type="submit" value="Save" id="save_button" class="btn btn-primary mr">';
		           result = result + '</form>';

		           result = result + '</div>';
		           result = result + '</section>';
		           
		           
		           $("#tab-3").empty();  
		           $("#tab-3-addTemplate").empty();
		           $("#tab-3").append(result); 
		           customizeFields();
		  
	}
</script>	

  <script type="text/javascript">
   	$('body').on('submit', '#sjfb', function(e){
	  if ($(this).validate(false, validationSettings)){
        e.preventDefault();
        var frm = $('#sjfb').serializeFormJSON();
        var con = JSON.stringify(frm);
        con = con.replace(/[{}]/g, "");
        var jsonData = encodeURIComponent(con);

        //Loop through fields and save field data to array
        var fields = [];

        $('.field').each(function() {

            var $this = $(this);

            //field type
            var fieldType = $this.data('type');

            //field label
            var fieldLabel = $this.find('.field-label').val();

            //field required
            var fieldReq = $this.hasClass('required') ? 1 : 0;

            //check if this field has choices
            if($this.find('.choices li').length >= 1) {

                var choices = [];

                $this.find('.choices li').each(function() {

                    var $thisChoice = $(this);

                    //choice label
                    var choiceLabel = $thisChoice.find('.choice-label').val();

                    //choice selected
                    var choiceSel = $thisChoice.hasClass('selected') ? 1 : 0;

                    choices.push({
                        label: choiceLabel,
                        sel: choiceSel
                    });

                });
            }

            fields.push({
                type: fieldType,
                label: fieldLabel,
                req: fieldReq,
                choices: choices
            });

        });

        var frontEndFormHTML = '';
        var clientLocationId = document.getElementById("clientLocationId").value;
        var sourceleadUrl ='${sourceleadUrl}';
        //Save form to database
        //Demo doesn't actually save. Download project files for save
        var con2 = JSON.stringify([{"name":"formFields","value":fields}]);
        con2 = con2.replace(/[{}]/g, "");
        var jsonStringData = encodeURIComponent(con2);
        alert(jsonStringData)
   	    $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/saveClientSubmissionTemplate?${_csrf.parameterName}=${_csrf.token}&clientLocationId="+clientLocationId+"&jsonData="+jsonData+"&jsonStringData="+jsonStringData, 
	        success: function(result) {
	        	if((result == 'home')){
		        		location.replace(sourceleadUrl+"/clientDashboard");  
		        } 
	        },
	    });
        return true;
      }
      else{
      	return false;	
         }
    });
    	
   	$('#sjfb').showHelpOnFocus();
    $('#sjfb').validateOnBlur(false, validationSettings); 
  
  </script>
  
