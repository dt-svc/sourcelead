<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

 <link rel="stylesheet" href="/resources/css/style.css?staticTime=${staticTime}" />
 <script src="/resources/js/submissionFormBuilder.js?staticTime=${staticTime}" type="text/javascript" ></script> <!-- form builder -->
 <script src="/resources/js/submissionForm-html-generator.js?staticTime=${staticTime}" type="text/javascript" ></script> <!-- form generator -->

<script type="text/javascript">
//On document ready
$(function(){
	submissionTemplateList();
	if('${type}' != 'undefined' && '${type}' != '' && '${type}' != null && '${type}' == 'addTemplate'){
		$("#add-tm").click();
	}
	
});
</script>



<script type="text/javascript">
	function submissionTemplateList() {
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/getOrgSubmissionTemplatesList?${_csrf.parameterName}=${_csrf.token}", 
	        success: function(data) {
	        var json = JSON.parse(data);
			var result = "";
	      
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
	                result = result + '<td><div><span class="mid-span" title="'+opt.templateName+'">'+opt.templateName+'</span></a></div></td>';
	                result = result + '<td><div class="tbldata-dotdot" title="'+opt.description+'"><span class="mid-span">'+opt.description+'</span></div></td>';
	                result = result + '<td><div class="tblicons">';
	                    result = result + '<a href="javascript:void(0)" class="mr10" title="View" onclick="viewSubmissionTemplate('+opt.id+',\''+opt.templateName+'\')"><i class="fa fa-eye"></i></a>';
                    '<sec:authorize access="hasAnyRole(\'ROLE_CREATE_APPLICANT_TEMPLATE\',\'ROLE_VIEW_EDIT_APPLICANT_TEMPLATE\')">'
	                    result = result + '<a href="javascript:void(0)" class="" title="Edit" onclick="addSubmissionTemplateFields('+opt.id+',\''+opt.templateName+'\')"><i class="fa fa-pencil"></i></a>';
	                    result = result + '<input type="hidden" name="description" id="description'+opt.id+'" value="'+opt.description+'" >';
                    '</sec:authorize>';
	                result = result +'</div></td>';
	                result = result + '	 </tr>';
				}  
	        		
	        	result = result +'</tbody>';
	        	result = result +"</table>";
    	       $("#clientSubmissionTemplateList").append(result); 
    	       templatepagination();
    	  
	        },
	    });
	}
</script>

<script type="text/javascript">
	function viewSubmissionTemplate(templateId,templateName) {
		
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
			    		result = result  +'<textarea rows="2" name="description" field-name="Description" data-validation="required" class="form-control"></textarea>';
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
			        result = result + '<li><a id="calendar" data-type="calendar" onclick="scrollToBottom();"  href="javascript:void(0)">Calendar</a></li>';
			        /* result = result + '<li><a id="add-autocomplete" data-type="autocomplete" href="javascript:void(0)">Auto Complete</a></li>'; */
		            result = result + '</ul>';
			        result = result + '</div>';
			        result = result + '</div>';
			        
			        result = result + '</div>';
		           
		           result = result + '<div id="form-fields">';
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
		           
		            $("#templateList").empty(); 
		           $("#templateList").append(result); 
		          customizeFields(templateId,sourceleadUrl);
		  
	}
</script>	

<script type="text/javascript">
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
</script>

<script type="text/javascript">
function cancel(){
	$("#choicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
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
            
            
            
            if(fieldLabel == 'First Name'  || fieldLabel == 'Phone Number'  
        		|| fieldLabel == 'Email Id' || fieldLabel == 'DOB' || fieldLabel == 'Current / Last Company' || fieldLabel == 'Designation' 
        		|| fieldLabel == 'Education' || fieldLabel == 'Work Experience' || fieldLabel == 'Current CTC' || fieldLabel == 'Expected CTC' 
        		|| fieldLabel == 'Notice Period'){
        	
            	isRemoved = 'N';
            }else{
            	isRemoved = 'Y';
            }
            
            fields.push({
            		type: fieldType,
	                label: fieldLabel,
	                req: fieldReq,
	                choices: choices,
	                isRemoved : isRemoved,
	                isLabelModified : isLabelModified
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
        var con2 = JSON.stringify(fields);
        var jsonStringData = encodeURIComponent(con2);
        
        if(submitTemplate){
        	$( "#save_button" ).addClass('button').val('Processing..');
        	  $.ajax({
      	    	type: "POST",
      	        url: sourceleadUrl+"/saveOrgSubmissionTemplate?${_csrf.parameterName}=${_csrf.token}&templateId="+templateId+"&jsonData="+jsonData+"&jsonStringData="+jsonStringData, 
      	        success: function(result) {
      	        	if((result == 'home')){
      	        		location.replace(sourceleadUrl+"/orgSubmissionTemplate"); 
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
	    	url : sourceleadUrl+"/validateTemplateNameByOrg?${_csrf.parameterName}=${_csrf.token}&templateName="+templateName+"&templateId="+templateId, 
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
  	<c:if test="${!empty msg}"> 
		<div class="success_pop" id="selector" style="display: block;"> <i class="fa fa-check-circle"></i><span>${msg}</span> </div> 
	</c:if> 
    <div class="pull-left"><h2 class="pull-left">Submission Templates</h2></div>
    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a> &raquo;  <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; <a href="<c:url value='${appPath}/orgTemplates'/>"> Templates</a> &raquo; Submission Templates List</div>
  </section>
  
  <div class="timesheet_out">
    <div class="vendor_wrap">
    
<div class="reportHead reportHeadDataTab clearfix" id="addTemplate">
      <ul class="pull-right list-unstyled list-inline">
      
	         <sec:authorize access="hasRole('ROLE_CREATE_APPLICANT_TEMPLATE')"> 
	          <li><a onClick="addSubmissionTemplateFields();" id="add-tm" class="newAdBtn">+ Template</a></li>
	          </sec:authorize>      	
        </ul>
      </div>     
	      
	       <div class="project_wrap_table">
	          <div id="clientSubmissionTemplateList" class="datatable_filter dataSeaTop"></div>
	       </div> 
	       
	       <div id="templateList"></div>
	            
       </div>
  </div>
  
</section>
<script> 
$("#selector").fadeIn(200); 
$('#selector').delay(2000).fadeOut('slow'); 

</script> 
<script>
$("#add-tm").click(function(){
	$("#clientSubmissionTemplateList").hide();
	$("#templateList").show();
});

</script>

<script>
function cancelTemplate(){
	var sourceleadUrl = '${sourceleadUrl}';
	location.replace(sourceleadUrl+"/orgSubmissionTemplate");  
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


<jsp:include page="masterFooter.jsp" />
<div class="backtotop" style="display: none;"><img src="/resources/images/backtotop.png"></div>  
</html>


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

