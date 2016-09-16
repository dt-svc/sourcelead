<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

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
<script src="resources/js/sjfb-builder.js" type="text/javascript" ></script> <!-- form builder -->
<script src="resources/js/sjfb-html-generator.js" type="text/javascript" ></script> <!-- form generator -->



<script type="text/javascript">
//On document ready
$(function(){
	 customizeFields();
    //load saved form by ID
    generateForm();

    //Don't allow the sample preview form to be submitted
    $("#sjfb-sample").on("submit", function() {
        event.preventDefault();
        alert('Made it! You can now process your submission however you would like.')
    });

});
</script>

<section class="content-wraper">
  <div class="content-dashboard clearfix">
   <div id="loading"></div>
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Submission Template</h2>
      </div>
      
  <div id="sjfb-wrap">
  <div class="add-wrap">
        <h3>Add Field:</h3>
        <ul id="add-field">
            <li><a id="add-text" data-type="text" href="#">Text Box</a></li>
            <li><a id="add-textarea" data-type="textarea" href="#">Text Area</a></li>
            <li><a id="add-select" data-type="select" href="#">Select Box</a></li>
            <li><a id="add-radio" data-type="radio" href="#">Radio Button</a></li>
            <li><a id="add-checkbox" data-type="checkbox" href="#">Check Box</a></li>
           <!--  <li><a id="add-agree" data-type="agree" href="#">Agree Box</a></li> -->
        </ul>
    </div>

    <form id="sjfb" novalidate>
        <div id="form-fields">
        </div>
        <button type="submit" class="submit">Save Form</button>
    </form>

</div>
    </section>
      </div>
  </section>
  
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
  
<jsp:include page="../masterFooter.jsp" />