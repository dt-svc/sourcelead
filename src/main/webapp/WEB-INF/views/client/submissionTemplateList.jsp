<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/chosen.min.css">
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css">
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
<script src="resources/js/bootstrap-select.min.js"></script>
<script src="resources/js/chosen.jquery.min.js"></script> 	
<script src="resources/js/jquery-ui.min.js" ></script>

<link rel="stylesheet" href="resources/css/style.css" />


<style type="text/css">
.collapse-div-adduser select{
width: 100% !important;
    padding-right: 10px !important;
    padding-left: 10px !important;
    border:1px solid #ccc !important
}
</style>

<script type="text/javascript">
//On document ready
$(function(){
    generateForm();
});
</script>
				

<!-- client details View starts-->
<script type="text/javascript">
	function submissionTemplateList(clientLocationId) {
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/getSubmissionTemplatesList?${_csrf.parameterName}=${_csrf.token}&clientLocationId="+clientLocationId, 
	        success: function(data) {
	        	
	        var json = JSON.parse(data);
	        
    		var res = "";
			res = res + '<div class="timesheet_head">';
			res = res + '<ul>';
			res = res + '<li><a href="javascript:void(0)" onClick="addSubmissionTemplateFields('+clientLocationId+');" class="addcon-pop">';
			res = res + '<i class="glyphicon glyphicon-plus"></i>Add Template</a>';
			res = res + '<div class="clearfix"></div>';
			res = res + '</ul>';
			res = res + '<div id="popUpDivContact"> ';
			res = res + '</div>  ';
			res = res + '</div>'; 
			var result = "";
	      
    		 if(json.length != null && json.length != ''){
    	   		
				var result = "";
	        	result = result +'<thead>';
	        	result = result +'<tr>';
	        	result = result + '<th>Template Name</th>';
	            result = result + '<th>Description</th>';
	            result = result + '<th>Action</th>';
	        	result = result +'</tr>';
	        	result = result +'</thead>';
	        	result = result +'<tbody>';

	        	for (var i=0; i<json.length; i++)
				  {	        		
	        		var opt = json[i];
	        		
	        		
	        		result = result + '<tr>';
	                result = result + '<td><div>'+opt.templateName+'</a></div></td>';
	                result = result + '<td><div class="tbldata-dotdot">'+opt.description+'</div></td>';
	                result = result + '<td><div>';
	                result = result + '<a href="javascript:void(0)" onclick="viewSubmissionTemplate('+clientLocationId+','+opt.id+',\''+opt.templateName+'\')">View</a>';
      	            result = result +'</div></td>';
	                result = result + '	 </tr>';
				} 
	        		
	        	
	        	result = result +'</tbody>';
	        	
    		}else{
      			 result = result + '<div class="table_nodata">';
       			 result = result + '<div class="table_nodata_inn">';
       			 result = result + '<img src="resources/images/no-data.png" />';
       			 result = result + '<span>Nothing is here to show </b>';
       			 result = result + '</span>';
       			 result = result + '<div class="clearfix"></div>';
       			 result = result + '</div>';
       			 result = result + '</div>';
       		 } 
    		 
       		$("#tab-2-addContact").empty();
       		$("#tab-2").empty(); 
    		$("#tab-4-addTemplate").empty();  
    	    $("#tab-4-addTemplate").append(res);
    		$("#tab-4").empty();  
    	    $("#tab-4").append(result);  
    	    $("#testTemplateDiv").show();
    	    
    	    $(".project-tabs-menu a").click(
					function(event) {
						event.preventDefault();
						$(this).parent().addClass("current");
						$(this).parent().siblings().removeClass(
								"current");
						var tab = $(this).attr("href");
						$(".project_tabs_inn").not(tab).css(
								"display", "none");
						$(tab).fadeIn();
					});
	        },
	    });
	}
</script>




<script type="text/javascript">
	function addSubmissionTemplateFields(clientLocationId) {
		
		var sourceleadUrl ='${sourceleadUrl}';
		var result = "";
		
		result = result + '<section class="clearfix createTemplate">';
		
		result = result + '<div class="clearfix">';
		result = result + '<h2>Template Details</h2>';
		        result = result + '</div>';
		        result = result + '<div id="sjfb-wrap">';

		           result = result + '<form id="sjfb" novalidate>';
		           result = result + '<input type="hidden" id="clientLocationId" value="'+clientLocationId+'">';
		           
		           result = result  +'	<div class="row mb">';
		            result = result  +'	<div class="col-xs-12 col-md-6" id="templateNameDiv">';
		            result = result  +'<label>Template Name<span class="text-danger">&nbsp;*</label>';
			    	result = result  +'	<div class="mt10">';
			    	result = result  +'	<input type="text" class="form-control input-sm" value="" onblur="validateTemplateName('+clientLocationId+')" id="templateName" name="templateName" field-name="Template Name" data-validation="required validate_Space validate_length length2-100">';
			    	result = result  +'	</div>';
			    	result = result  +'	</div>';
			    	
			    	result = result  +'	<div class="col-xs-12 col-md-6">';
			    	 result = result  +'<label>Description<span class="text-danger">&nbsp;*</label>';
			    	result = result  +'	  <div class="mt10">';
			    	result = result  +'	     <textarea rows="2" name="description" field-name="Description" data-validation="required" class="form-control"></textarea>';
			    	result = result  +'	   </div>';
			    	result = result  +'	</div>';
			    	result = result  +'	</div>';
			    	
			    	result = result + '<div class="clearfix mb row">';
			    	
			    	result = result + '<div class="col-md-10">';
			    	result = result + '<h2>Template Fields</h2>';
			    	result = result + '</div>';
			    	
			    	result = result + '<div class="col-md-2">';
			        result = result + '<div class="dropdown">';
			        result = result + '<button class="btn btn-success dropdown-toggle btn-block" type="button" data-toggle="dropdown" id="addFiled-dropdown">Add Field &nbsp;&nbsp;';
			        result = result + '<span class="caret"></span></button>';
			        result = result + '<ul id="add-field" class="dropdown-menu pull-right">';
			        result = result + '<li><a id="add-text" data-type="text" href="javascript:void(0)">Text Box</a></li>';
			        result = result + '<li><a id="add-textarea" data-type="textarea" href="javascript:void(0)">Text Area</a></li>';
			        result = result + '<li><a id="add-select" data-type="select" href="javascript:void(0)">Select Box</a></li>';
			        result = result + '<li><a id="add-radio" data-type="radio" href="javascript:void(0)">Radio Button</a></li>';
			        result = result + ' <li><a id="add-checkbox" data-type="checkbox" href="javascript:void(0)">Check Box</a></li>';
			        result = result + '<li><a id="calendar" data-type="calendar" href="javascript:void(0)">Calendar</a></li>';
			        result = result + '<li><a id="add-autocomplete" data-type="autocomplete" href="javascript:void(0)">Auto Complete</a></li>';
		            result = result + '</ul>';
			        result = result + '</div>';
			        result = result + '</div>';
			        
			        result = result + '</div>';
		           
		           result = result + '<div id="form-fields">';
		           result = result + '</div>';
		           result = result + '<div class="form-footer clearfix row mt">';
		           
		           result = result + '<div class="pull-right mr">';
		           result = result + ' <input type="submit" value="Save" id="save_button" class="btn btn-primary mr">';
		           result = result + ' <input type="button" value="Cancel" onclick="submissionTemplateList('+clientLocationId+')" class="btn btn-default" >';
		           result = result + '</div>';

		           result = result + '</div>';
		           result = result + '</form>';

		      
		           result = result + '</section>';
		           
		           result = result + '<div id="popUp"></div>'; 
		           
		           
		           $("#tab-4").empty();  
		           $("#tab-4-addTemplate").empty();
		           $("#tab-4").append(result); 
		           customizeFields(clientLocationId,sourceleadUrl);
		  
	}
</script>	


<script type="text/javascript">
	function viewSubmissionTemplate(clientLocationId,templateId,templateName) {
		
		var sourceleadUrl ='${sourceleadUrl}';
		generateForm(sourceleadUrl,templateId);
		
		var result = "";
		result = result + '<div class="clearfix templateName">';
		result = result + '<h2>'+templateName+'</h2>';
        result = result + '</div>';

        result = result + '<div id="sjfb-fields" class="viewTemplate">';
        
        result = result + '</div>';
        result = result + '<div class="form-footer clearfix">';
        result = result + '<div class="pull-right mr">';
        result = result + ' <input type="button" value="Cancel" onclick="submissionTemplateList('+clientLocationId+')" class="btn btn-default" >';
        result = result + '</div>';
        result = result + '</div>';
        
        $("#tab-4").empty();  
        $("#tab-4-addTemplate").empty();
        $("#tab-4").append(result); 
        
	}
</script>

<script type="text/javascript">
function validateTemplateName(clientLocationId){
	var templateName = document.getElementById("templateName").value; 
	if(templateName){
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/validateTemplateName?${_csrf.parameterName}=${_csrf.token}&clientLocationId="+clientLocationId+"&templateName="+templateName, 
	        success: function(result) {
	        	if (result == 'invalid') {
	        		$("#save_button").attr("disabled", "disabled");
	        		$("input#templateName").after( "<p class='jquery_form_error_message'>" + templateName + " is Already Existed</span>");
					document.getElementById("templateName").setAttribute( 'record-exist', 'yes');
					document.getElementById("templateName").setAttribute( 'record-exist-errorMsg', templateName +"  already existed");
	        	}else{
	        		$('input#templateName').removeAttr("class record-exist record-exist-errormsg");
					$('input#templateName').attr('class','valid form-control');
					$('#templateNameDiv').find('p.jquery_form_error_message').remove();
					
	        		$("#save_button").removeAttr("disabled")
	        	}
	        }
		 });
	}
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
        var con2 = JSON.stringify(fields);
        var jsonStringData = encodeURIComponent(con2);
   	    $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/saveClientSubmissionTemplate?${_csrf.parameterName}=${_csrf.token}&clientLocationId="+clientLocationId+"&jsonData="+jsonData+"&jsonStringData="+jsonStringData, 
	        success: function(result) {
	        	if((result == 'home')){
	        		submissionTemplateList(clientLocationId);  
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
  


 
 
 <script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 


<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {
			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};
	})(jQuery);
</script>

<jsp:include page="submissionTemplate.jsp" />	



