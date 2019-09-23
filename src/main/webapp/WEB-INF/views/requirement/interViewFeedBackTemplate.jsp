<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>

<script src="/resources/js/InterViewFormBuilder.js?staticTime=${staticTime}" type="text/javascript" ></script>
<script src="/resources/js/interViewForm-html-generator.js?staticTime=${staticTime}" type="text/javWascript" ></script> 
<link rel="stylesheet" href="/resources/css/style.css?staticTime=${staticTime}" />

<script type="text/javascript">
//On document ready
$(function(){
	interViewTemplateList();
	if('${type}' != 'undefined' && '${type}' != '' && '${type}' != null && '${type}' == 'addInterViewFeedBackTemplate'){
		$("#add-tm").click();
	}
});
</script>

<style>
.choices input[type="checkbox"], .choices input[type="radio"] {
	float: left; position: absolute; left:0;
}
.viewTemplate.viewTemplate_interviewfbt .sjfb-field select {
 	-webkit-appearance: none; -moz-appearance: none; -ms-appearance: none; -o-appearance: none;
}
</style>


<script type="text/javascript">
	function interViewTemplateList() {
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/getInterviewFeedbackTemplatesList?"+tokenParamName+"="+tokenValue+"", 
	        success: function(data) {
	        	if (data.response === "FAILED") {
  		    		showError(data.responseMessage);
  		    	} else if (data.response === "AWKWARD") {
  		    		location.href = "/errorPage";
  		    	} else if (data.response === "SUCCESS") {
  		    		var json = data.data;
					var result = "";
					result = result +"<table id='templatePagination' class='display' cellspacing='0' width='100%' >"; 
		        	result = result +'<thead>';
		        	result = result +'<tr>';
		        	result = result + '<th>Template Name</th>';
		            result = result + '<th>Description</th>';
		            result = result + '<th style="width:100px;">Action</th>';
		        	result = result +'</tr>';
		        	result = result +'</thead>';
		        	result = result +'<tbody>';
	
		        	for (var i=0; i<json.length; i++)
					  {	        		
		        		var opt = json[i];
		        		result = result + '<tr>';
		                result = result + '<td><div><span class="mid-span">'+opt.templateName+'</span></a></div></td>';
		                result = result + '<td><div class="tbldata-dotdot" title="'+opt.description+'"><span class="mid-span">'+opt.description+'</span></div></td>';
		                
		                result = result + '<td><div>';
		                 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_INTEVIEW_FEEDBACK_TEMPLATE\')">' 
		                    result = result + '<a href="javascript:void(0)" onclick="viewInterviewFeedbackTemplate('+opt.id+',\''+opt.templateName+'\')" title="View" class="mr10"><i class="fa fa-eye text-primary"></i></a>';
		                 '</sec:authorize>';
		                 
		                 '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_INTEVIEW_FEEDBACK_TEMPLATE\')">'
		                    result = result + '<a href="javascript:void(0)" title="View" class="mr10"><i class="fa fa-eye text-primary"></i></a>';
		                 '</sec:authorize>'; 
		                    
	                    '<sec:authorize access="hasAnyRole(\'ROLE_EDIT_INTEVIEW_FEEDBACK_TEMPLATE\')">' 
		                    result = result + '&nbsp;<a href="javascript:void(0)" onclick="addSubmissionTemplateFields('+opt.id+',\''+opt.templateName+'\')" title="Edit"><i class="fa fa-pencil"></i></a>';
		                    result = result + '<input type="hidden" name="description" id="description'+opt.id+'" value="'+opt.description+'" >';
	                    '</sec:authorize>';
	                    
	                    '<sec:authorize access="!hasAnyRole(\'ROLE_EDIT_INTEVIEW_FEEDBACK_TEMPLATE\')">'
	                    result = result + '&nbsp;<a href="javascript:void(0)" title="Edit"><i class="fa fa-pencil"></i></a>';
	                  '</sec:authorize>'; 
	                  
		                result = result +'</div></td>';
		                result = result + '	 </tr>';
					}  
		        		
		        	result = result +'</tbody>';
		        	result = result +"</table>";
	    	       $("#clientSubmissionTemplateList").append(result); 
	    	       templatepagination();
  		    	}else {
		    		showError("Operation failed, please try again");
		    	}
	        },
	    });
	}
</script>

<script type="text/javascript">
	function viewInterviewFeedbackTemplate(templateId,templateName) {
		var sourceleadUrl ='${sourceleadUrl}';
		generateTemplateForm(sourceleadUrl,templateId);
		var result = "";
		result = result + '<div class="clearfix templateName">';
		result = result + '<h2>'+templateName+'</h2>';
        result = result + '</div>';

        result = result + '<div id="interviewsjfb-fields" class="viewTemplate viewTemplate_interviewfbt">';
        
        result = result + '</div>';
        result = result + '<div class="form-footer clearfix">';
        result = result + '<div class="pull-right mr">';
        result = result + ' <input type="button" value="Cancel" onclick="cancelTemplate()" class="btn btn-default" >';
        result = result + '</div>';
        result = result + '</div>';
        $("#clientSubmissionTemplateList").empty();
        $("#addTemplate").hide();
        $("#clientSubmissionTemplateList").append(result); 
        
	}
</script>

<script>
function templatepagination(){  
    $('#templatePagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>
<script type="text/javascript">
	function addSubmissionTemplateFields(templateId,templateName) {
		var description =  $("#description"+templateId+"").val();
		var sourceleadUrl ='${sourceleadUrl}';
		 $("#clientSubmissionTemplateList").empty();
	     $("#addTemplate").hide();
		var result = "";
		result = result + '<section class="clearfix createTemplate">';
		
		result = result + '<div class="close-btn2" onclick="cancelTemplate()"><i class="fa fa-close"></i></div>';
		
		result = result + '<div class="clearfix">';
		result = result + '<h2>Template Details</h2>';
		        result = result + '</div>';
		        result = result + '<div id="sjfb-wrap">';

		           result = result + '<form id="sjfb" novalidate>';
		           result = result + '<input type="hidden" id="templateId" value="'+templateId+'">';
		           
		           result = result  +'	<div class="row mb">';
		            result = result  +'	<div class="col-xs-12 col-md-6" id="templateNameDiv">';
		            result = result  +'<label>Template Name<span class="text-danger">&nbsp;*</label>';
			    	result = result  +'	<div class="mt10">';
			    	if(templateName){
			    		result = result  +'	<input type="text" class="form-control input-sm" value="'+templateName+'" onblur="validateTemplateName()" id="templateName" name="templateName" field-name="Template Name" data-validation="required validate_Space validate_length length2-100">';	
			    	}else{
			    		result = result  +'	<input type="text" class="form-control input-sm"  onblur="validateTemplateName()" id="templateName" name="templateName" field-name="Template Name" data-validation="required validate_Space validate_length length2-100">';
			    	}
			    	
			    	result = result  +'	</div>';
			    	result = result  +'	</div>';
			    	
			    	result = result  +'	<div class="col-xs-12 col-md-6">';
			    	 result = result  +'<label>Description<span class="text-danger">&nbsp;*</label>';
			    	result = result  +'	  <div class="mt10">';
			    	if(description){
			    		result = result  +'<textarea rows="2" name="description" field-name="Description" data-validation="required" class="form-control">'+description+'</textarea>';
			    	}else{
			    		result = result  +'<textarea rows="2" name="description" field-name="Description" data-validation="required validate_length length10-500" class="form-control"></textarea>';
			    	}
			    	
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
			        result = result + '<li><a id="add-text" data-type="text" onclick="scrollToBottom();" href="javascript:void(0)">Text Box</a></li>';
			        result = result + '<li><a id="add-textarea" data-type="textarea" onclick="scrollToBottom();"  href="javascript:void(0)">Text Area</a></li>';
			        result = result + '<li><a id="add-select" data-type="select" onclick="scrollToBottom();"  href="javascript:void(0)">Select Box</a></li>';
			        result = result + '<li><a id="add-radio" data-type="radio" onclick="scrollToBottom();"  href="javascript:void(0)">Radio Button</a></li>';
			        result = result + ' <li><a id="add-checkbox" data-type="checkbox" onclick="scrollToBottom();"  href="javascript:void(0)">Check Box</a></li>';
			        //result = result + '<li><a id="calendar" data-type="calendar" onclick="scrollToBottom();"  href="javascript:void(0)">Calendar</a></li>';
			        /* result = result + '<li><a id="add-autocomplete" data-type="autocomplete" href="javascript:void(0)">Auto Complete</a></li>'; */
		            result = result + '</ul>';
			        result = result + '</div>';
			        result = result + '</div>';
			        
			        result = result + '</div>';
		           
		           result = result + '<div id="interViewform-fields">';
		           result = result + '</div>';
		           result = result + '<div class="form-footer clearfix row mt">';
		           
		           result = result + '<div class="pull-right mr">';
		           result = result + ' <input type="submit" value="Save" id="save_button" class="btn btn-primary mr">';
		           result = result + ' <input type="button" value="Cancel" onclick="cancelTemplate()" class="btn btn-default" >';
		           result = result + '</div>';

		           result = result + '</div>';
		           result = result + '</form>';

		      
		           result = result + '</section>';
		           
		           result = result + '<div id="popUp"></div>';
		           result = result + '<div id="choicepopUp"></div>';
		           result = result +"	<div class='project_inn_mask'></div>";
		           
		            $("#templateList").empty(); 
		           $("#templateList").append(result); 
		          customizeFields(templateId,sourceleadUrl);
		  
	}
</script>	

<script type="text/javascript">
function alertMessage(message){
	var result = "";
	//result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancelPop();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelPop();'></b>";
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script type="text/javascript">
function cancelPop(){
	$("#choicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<script type="text/javascript">
   	$('body').on('submit', '#sjfb', function(e){
	  if ($(this).validate(false, validationSettings)){
        e.preventDefault();
        var frm = $('#sjfb').serializeFormJSON();
        var jsonData = JSON.stringify(frm);
        
        var form = $('#sjfb')[0];
	    var formData = new FormData(form); 
        var isRemoved = '';
        var isLabelModified = 'N';
        //Loop through fields and save field data to array
        var fields = [];
        var submitTemplate = true;
        var fieldLabel = '';
        var myArray = new Array();
        $('.field').each(function() {

            var $this = $(this);

            //field type
            var fieldType = $this.data('type');

            //field label
            fieldLabel = $this.find('.field-label').val();

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
                req: fieldReq,
                choices: choices,
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
        if(templateId == 'undefined'){
        	templateId = '';
        }
        var sourceleadUrl ='${sourceleadUrl}';
        //Save form to database
        //Demo doesn't actually save. Download project files for save
        var jsonStringData = JSON.stringify(fields);
        formData.append("jsonStringData",jsonStringData);
	      formData.append("jsonData",jsonData); 
        if( myArray.length > 0){
            if(submitTemplate){
            	$("#save_button").attr("disabled", "disabled");
            	$( "#save_button" ).addClass('button').val('Processing..');
            	  $.ajax({
          	    	type: "POST",
          	    	data: formData,
          	    	 processData: false,
 	    	        contentType:false,
          	        url: sourceleadUrl+"/saveInterviewFeedBackTemplate?"+tokenParamName+"="+tokenValue+"&templateId="+templateId, 
          	        success: function(result) {
          	        	if((result == 'interViewFeedBackTemplate')){
          	        		location.replace(sourceleadUrl+"/interViewFeedBackTemplate"); 
          		        } 
          	        },
          	    });  
                  return true;
            }else{
            	alertMessage("Provide choice for "+fieldLabel)
            }
        }else{
        	var result = "";
    		result = result +"	<div class='project_inn_mask'></div>";
    		result = result +"	   <div id='template_pop' class='remove_user_pop remove_user_pop_a'>";
    		result = result +"		<p>At least one Add column</p>";
    		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
    		result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
    		result = result +"	</div>";
    		$("#popUp").append(result);
    		$("#template_pop").slideDown(200);
    		$(".project_inn_mask").fadeIn(200);
        }
      }
      else{
      	return false;	
         }
    });
    	
   	$('#sjfb').showHelpOnFocus();
    $('#sjfb').validateOnBlur(false, validationSettings); 
  
  </script>

  <script type="text/javascript">
function cancel(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<script type="text/javascript">
function validateTemplateName(){
	var templateName = document.getElementById("templateName").value.trim(); 
	var templateId = $("#templateId").val();
	if(templateId == 'undefined' || templateId == null){
		templateId = '';
	}
	if(templateName){
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/validateTemplateNameByOrg?"+tokenParamName+"="+tokenValue+"&templateName="+templateName+"&templateId="+templateId, 
	        success: function(result) {
	        	if (result == 'invalid') {
	        		$("#save_button").attr("disabled", "disabled");
	        		$('#templateNameDiv').find('p.jquery_form_error_message').remove();
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

<body>

<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Interview Templates</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;  <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; <a href="<c:url value='${appPath}/orgTemplates'/>"> Templates</a> &raquo; Interview Templates</div>
  </section>
  
    <div class="vendor_wrap">
    
	 <div class="reportHead reportHeadDataTab clearfix" id="addTemplate">
	    <ul class="pull-right list-unstyled list-inline">
	        <sec:authorize access="hasAnyRole('ROLE_CREATE_INTEVIEW_FEEDBACK_TEMPLATE')">
	          <li><a onClick="addSubmissionTemplateFields();" id="add-tm" class="newAdBtn">+ Interview Template</a></li>
	        </sec:authorize>         
		</ul>
	</div>	    
	      
      <div class="project_tabs_inn_table">
         <div id="clientSubmissionTemplateList" class="datatable_filter dataSeaTop"></div>
      </div> 
      
      <div id="templateList"></div>
           
</div>
</section>

<script>
$("#add-tm").click(function(){
	$("#clientSubmissionTemplateList").hide();
	$("#templateList").show();
});

</script>

<script>
function cancelTemplate(){
	var sourceleadUrl = '${sourceleadUrl}';
	location.replace(sourceleadUrl+"/interViewFeedBackTemplate");  
}
</script>

<script>
function scrollToBottom(){
	var documentHeight = document.documentElement.offsetHeight;
	var viewportHeight = window.innerHeight;
	window.scrollTo(0,documentHeight-viewportHeight);
}
</script>


</body>


<div class="backtotop" style="display: none;"><img src="/resources/images/backtotop.png"></div>  
</html>
<%-- <jsp:include page="masterFooter.jsp" /> --%>

<script>
// Back To Top 
    $(window).scroll(function() {
        if ($(this).scrollTop() > 200) {
            $('.backtotop').fadeIn('slow');
        } else {
            $('.backtotop').fadeOut('slow');
        }
    });
    $('.backtotop').click(function() {
        $("html, body").animate({scrollTop: 0}, 500);
        return false;
    });
</script>


<script>
function generateTemplateForm(sourceleadUrl,templateId) {
    //empty out the preview area
    $("#interviewsjfb-fields").empty();
    
    
    $.ajax({
    	type: "GET",
        url: sourceleadUrl+"/loadSavedSubmissionTemplate?templateId="+templateId, 
        success: function(data) {
        	 var data = eval(data);
        	 
        	 if (data) {
                 //go through each saved field object and render the form HTML
                 $.each( data, function( k, v ) {

                     var fieldType = v['type'];

                     //Add the field
                     $('#interviewsjfb-fields').append(addFieldHTML(fieldType,v['req'],v['label']));
                     var $currentField = $('#interviewsjfb-fields .sjfb-field').last();
                     
                     var reqSymbol = '';
                	 if(v['req'] == '1'){
                		reqSymbol = " *";
                	 }
                     
                     //Add the label
                     if(v['label'] != 'location' ){
                    	 $currentField.find('label').text(v['label']+reqSymbol);
                     }
                     
                     
                     //Any choices?
                     if (v['choices']) {

                         var uniqueID = Math.floor(Math.random()*999999)+1;

                         $.each( v['choices'], function( k, v ) {

                             if (fieldType == 'select') {
                                 var selected = v['sel'] ? ' selected' : '';
                                 var choiceHTML = '<option' + selected + '>' + v['label'] + '</option>';
                                 $currentField.find(".choices").append(choiceHTML);
                             }

                             else if (fieldType == 'radio') {
                                 var selected = v['sel'] ? ' checked' : '';
                                 var choiceHTML = '<label><input type="radio" name="radio-' + uniqueID + '"' + selected + ' value="' + v['label'] + '">' + v['label'] + '</label>';
                                 $currentField.find(".choices").append(choiceHTML);
                             }

                             else if (fieldType == 'checkbox') {
                                 var selected = v['sel'] ? ' checked' : '';
                                 var choiceHTML = '<label><input type="checkbox" name="checkbox-' + uniqueID + '[]"' + selected + ' value="' + v['label'] + '">' + v['label'] + '</label>';
                                 $currentField.find(".choices").append(choiceHTML);
                             }

                         });
                     }

                     //Is it required?
                     if (v['req']) {
                         if (fieldType == 'text') { $currentField.find("input").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'calendar') { $currentField.find("calandar").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'autocomplete') { $currentField.find("autocomplete").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'textarea') { $currentField.find("textarea").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'select') { $currentField.find("select").prop('required',true).addClass('required-choice') }
                         else if (fieldType == 'radio') { $currentField.find("input").prop('required',true).addClass('required-choice') }
                         $currentField.addClass('required-field');
                     }

                 });
             }
        	 
        	 
        	 
        	  //HTML templates for rendering frontend form fields
             function addFieldHTML(fieldType,required,label) {
            	 
            	 if(label == 'location'){
            		 var reqSymbol = ''
            		 if(required == 1){
            			 reqSymbol = '<span>&nbsp;*</span>'
            		 }
            		 var countryUniqueID = Math.floor(Math.random()*999999)+1;
            		 var stateUniqueID = Math.floor(Math.random()*999999)+1;
            		 var cityUniqueID = Math.floor(Math.random()*999999)+1;
            	 }
            	 else{
            		 var uniqueID = Math.floor(Math.random()*999999)+1;
            	 }
                

                 switch (fieldType) {

                     case 'text':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-text">' +
                             '<label for="text-' + uniqueID + '"></label>' +
                             '<input type="text" id="text-' + uniqueID + '">' +
                             '</div>';

                     case 'textarea':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-textarea">' +
                             '<label for="textarea-' + uniqueID + '"></label>' +
                             '<textarea rows="3" id="textarea-' + uniqueID + '"></textarea>' +
                             '</div>';

                     case 'select':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-select">' +
                             '<label for="select-' + uniqueID + '"></label>' +
                             '<select id="select-' + uniqueID + '" class="choices choices-select"></select>' +
                             '</div>';

                     case 'radio':
                         return '' +
                             '<div id="sjfb-' + uniqueID + '" class="sjfb-field sjfb-radio sjfbRadio">' +
                             '<label></label>' +
                             '<div class="choices choices-radio"></div>' +
                             '</div>';

                     case 'checkbox':
                         return '' +
                             '<div id="sjfb-checkbox-' + uniqueID + '" class="sjfb-field sjfb-checkbox sjfbCheckbox">' +
                             '<label class="sjfb-label"></label>' +
                             '<div class="choices choices-checkbox"></div>' +
                             '</div>';

                 }
             }
        },
    });
    
    
}
</script>
<jsp:include page="../masterFooter.jsp" />	
