
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
		        result = result + '<div class="add-wrap">';
		        result = result + ' <h3>Add Field:</h3>';
		        result = result + '<ul id="add-field">';
		        result = result + '<li><a id="add-text" data-type="text" href="javascript:void(0)">Text Box</a></li>';
		        result = result + '<li><a id="add-textarea" data-type="textarea" href="javascript:void(0)">Text Area</a></li>';
		        result = result + '<li><a id="add-select" data-type="select" href="javascript:void(0)">Select Box</a></li>';
		        result = result + '<li><a id="add-radio" data-type="radio" href="javascript:void(0)">Radio Button</a></li>';
		        result = result + ' <li><a id="add-checkbox" data-type="checkbox" href="javascript:void(0)">Check Box</a></li>';
		           result = result + '</ul>';
		           result = result + '</div>';

		           result = result + '<form id="sjfb" novalidate>';
		           result = result + '<div id="form-fields">';
		           result = result + '</div>';
		           result = result + '<button type="submit" class="submit">Save Form</button>';
		           result = result + '</form>';

		           result = result + '</div>';
		           result = result + '</section>';
		           
		           
		           $("#tab-3").empty();  
		           $("#tab-3-addTemplate").empty();
		           $("#tab-3").append(result); 
		           customizeFields();
		  
	}
</script>	

  <script>
  //Saving form
 //Saving form
    $("#sjfb").submit(function(event) {
        event.preventDefault();

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

        //Save form to database
        //Demo doesn't actually save. Download project files for save
        var data = JSON.stringify([{"name":"formFields","value":fields}]);
        alert("data>>>"+data);
        $.ajax({
            method: "POST",
            url: "sjfb-save.php",
            data: data,
            dataType: 'json',
            success: function (msg) {
                console.log(msg);
                $('.alert').removeClass('hide');
                $("html, body").animate({ scrollTop: 0 }, "fast");

                //Demo only
                $('.alert textarea').val(JSON.stringify(fields));
            }
        });
    });
  
  
  </script>
  
