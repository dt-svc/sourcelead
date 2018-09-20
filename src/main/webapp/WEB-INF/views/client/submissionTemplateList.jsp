<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
</c:if>

<link rel="stylesheet" href="/resources/css/style.css?staticTime=${staticTime}" />
<script src="/resources/js/submissionFormBuilder.js?staticTime=${staticTime}" type="text/javascript" ></script> <!-- form builder -->
<script src="/resources/js/submissionForm-html-generator.js?staticTime=${staticTime}" type="text/javascript" ></script> <!-- form generator -->


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
    //generateForm();
});
</script>



<script>
function clientSubmissionTemplate(clientLocationId){
	
	
	var result = "";
	result = result  +'<div class="timesheet_head">';
	result = result  +'    <div class="project_tabs_inn_head addUser-org"> ';  
	    result = result + '<ul>';
	    '<sec:authorize access="hasAnyRole(\'ROLE_CREATE_SUBMISSION_TEMPLATE\')">';
		result = result  +'    	<a href="javascript:void(0)" onClick="addSubmissionTemplateFields('+clientLocationId+');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Template</a>                                                            ';
		'</sec:authorize>';
		result = result  +'     <div id="popUpDivContact" class="edit-details col-md-4"> ';
		result = result  +'     </div>  ';
		result = result  +'  <div class="clearfix"></div> ';
		result = result + '</ul>';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		result  = result + '   <div id="clientSubmissionTemplateList" class="datatable_filter"></div>';
		result  = result + '   </div>';
	result = result+'	    </div>';
	result = result+'	    </div>';

    $("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty();  
    $("#tab-4").empty();  
    $("#tab-5").empty();  
    $("#tab-4").append(result);  
	
    submissionTemplateList(clientLocationId);
    
}
</script>
				

<!-- client details View starts-->
<script type="text/javascript">
	function submissionTemplateList(clientLocationId) {
		
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/getSubmissionTemplatesList?"+tokenParamName+"="+tokenValue+"&clientLocationId="+clientLocationId, 
	        success: function(data) {
	        	
	        var json = JSON.parse(data);
	        
    	
			var result = "";
	      
    		 if(json.length != null && json.length != ''){
    	   		
    			$("#clientSubmissionTemplateList").empty();
				var result = "";
				
				result = result +"<table id='templatePagination' class='display' cellspacing='0' width='100%' >"; 
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
	                result = result + '<td><div><span title="'+opt.templateName+'">'+opt.templateName+'</span></a></div></td>';
	                result = result + '<td><div class="tbldata-dotdot"><span title="'+opt.description+'">'+opt.description+'</span</div></td>';
	                result = result + '<td><div>';
	                '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_SUBMISSION_TEMPLATE\')">';
	                result = result + '<a href="javascript:void(0)" onclick="viewSubmissionTemplate('+clientLocationId+','+opt.id+',\''+opt.templateName+'\')">View</a>&nbsp;';
	                '</sec:authorize>';
	                '<sec:authorize access="hasAnyRole(\'ROLE_EDIT_SUBMISSION_TEMPLATE\')">';
	                result = result + '|&nbsp;<a href="javascript:void(0)" onclick="addSubmissionTemplateFields('+clientLocationId+','+opt.id+',\''+opt.templateName+'\')">Edit</a>';
	                result = result + '<input type="hidden" name="description" id="descriptionText'+opt.id+'" value="'+opt.description+'">';
	                '</sec:authorize>';
	                
	                '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_SUBMISSION_TEMPLATE\',\'ROLE_EDIT_SUBMISSION_TEMPLATE\')">';
	               	 result = result + '--';
	                '</sec:authorize>';
	                result = result +'</div></td>';
	                result = result + '	 </tr>';
				} 
	        		
	        	
	        	result = result +'</tbody>';
	        	result = result +"</table>";
	        	
    		}else{
      			 result = result + '<div class="dataTables_empty">';
       			/*  result = result + '<div class="table_nodata_inn">';
       			 result = result + '<img src="/resources/images/no-data.png" />';
       			 result = result + '<span>Nothing is here to show </b>';
       			 result = result + '</span>';
       			 result = result + '<div class="clearfix"></div>';
       			 result = result + '</div>'; */
       			 result = result + '</div>';
       		 } 
    		 
       		
    	    $("#clientSubmissionTemplateList").append(result); 
    	    templatepagination(); 
    	  
	        },
	    });
	}
</script>


<script>
function templatepagination(){  
    $('#templatePagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 2
			}],
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>



<script type="text/javascript">
	function addSubmissionTemplateFields(clientLocationId,templateId,templateName) {
		
		var sourceleadUrl ='${sourceleadUrl}';
		var description =  $("#descriptionText"+templateId+"").val();
		var result = "";
		
		result = result + '<section class="clearfix createTemplate">';
		result = result + '<div class="close-btn2" onclick="clientSubmissionTemplate('+clientLocationId+')"><i class="fa fa-close"></i></div>';
		
		result = result + '<div class="clearfix">';
		result = result + '<h2>Template Details</h2>';
		        result = result + '</div>';
		        result = result + '<div id="sjfb-wrap">';

		           result = result + '<form id="sjfb" novalidate>';
		           result = result + '<input type="hidden" id="clientLocationId" value="'+clientLocationId+'">';
		           result = result + '<input type="hidden" id="templateId" value="'+templateId+'">';
		           
		           result = result  +'	<div class="row mb">';
		            result = result  +'	<div class="col-xs-12 col-md-6" id="templateNameDiv">';
		            result = result  +'<label>Template Name<span class="text-danger">&nbsp;*</label>';
			    	result = result  +'	<div class="mt10">';
			    	if(templateName){
			    		result = result  +'	<input type="text" class="form-control input-sm" value="'+templateName+'"  onblur="validateTemplateName('+clientLocationId+')" id="templateName" name="templateName" field-name="Template Name" data-validation="required validate_Space validate_length length2-100">';	
			    	}else{
			    		result = result  +'	<input type="text" class="form-control input-sm"  onblur="validateTemplateName('+clientLocationId+')" id="templateName" name="templateName" field-name="Template Name" data-validation="required validate_Space validate_length length2-100">';
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
			        result = result + '<li><a id="add-text" data-type="text" href="javascript:void(0)">Text Box</a></li>';
			        result = result + '<li><a id="add-textarea" data-type="textarea" href="javascript:void(0)">Text Area</a></li>';
			        result = result + '<li><a id="add-select" data-type="select" href="javascript:void(0)">Select Box</a></li>';
			        result = result + '<li><a id="add-radio" data-type="radio" href="javascript:void(0)">Radio Button</a></li>';
			        result = result + ' <li><a id="add-checkbox" data-type="checkbox" href="javascript:void(0)">Check Box</a></li>';
			        result = result + '<li><a id="calendar" data-type="calendar" href="javascript:void(0)">Calendar</a></li>';
			        /* result = result + '<li><a id="add-autocomplete" data-type="autocomplete" href="javascript:void(0)">Auto Complete</a></li>'; */
		            result = result + '</ul>';
			        result = result + '</div>';
			        result = result + '</div>';
			        
			        result = result + '</div>';
		           
		           result = result + '<div id="form-fields">';
		           result = result + '</div>';
		           result = result + '<div class="form-footer clearfix row mt">';
		           
		           result = result + '<div class="pull-right mr">';
		           result = result + ' <input type="button" value="Save" id="save_button" onclick="submitClientSubmissionTemplate();" class="btn btn-primary mr">';
		           result = result + ' <input type="button" value="Cancel" onclick="clientSubmissionTemplate('+clientLocationId+')" class="btn btn-default" >';
		           result = result + '</div>';

		           result = result + '</div>';
		           result = result + '</form>';

		      
		           result = result + '</section>';
		           
		           result = result + '<div id="popUp"></div>'; 
		           result = result + '<div id="choicepopUp"></div>'; 
		           
		           $("#tab-4").empty();  
		           $("#tab-4-addTemplate").empty();
		           $("#tab-4").append(result); 
		           customizeFields(templateId,sourceleadUrl);
		  
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
        result = result + ' <input type="button" value="Cancel" onclick="clientSubmissionTemplate('+clientLocationId+')" class="btn btn-default" >';
        result = result + '</div>';
        result = result + '</div>';
        
        $("#tab-4").empty();  
        $("#tab-4-addTemplate").empty();
        $("#tab-4").append(result); 
        
	}
</script>

<script type="text/javascript">
function validateTemplateName(clientLocationId){
	var templateName = document.getElementById("templateName").value.trim(); 
	var templateId = $("#templateId").val();
	if(templateId == 'undefined' || templateId == null){
		templateId = '';
	}
	if(templateName){
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/validateTemplateName?"+tokenParamName+"="+tokenValue+"&clientLocationId="+clientLocationId+"&templateName="+templateName+"&templateId="+templateId, 
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
  function submitClientSubmissionTemplate(){
	  if ($("#sjfb").validate(false, validationSettings)){
// 	        e.preventDefault();
	        var frm = $('#sjfb').serializeFormJSON();
	        var con = JSON.stringify(frm);
	        con = con.replace(/[{}]/g, "");
	        var jsonData = encodeURIComponent(con);
	        var isRemoved = '';
	        var isLabelModified = 'N';
	        var submitTemplate = true;
	        var fieldLabel = '';
	        var myArray = new Array();

	        //Loop through fields and save field data to array
	        var fields = [];

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
	            
	           /*  if(fieldLabel == 'First Name' || fieldLabel == 'Last Name' || fieldLabel == 'Phone Number'  || fieldLabel == 'Alternate Number' 
	            		|| fieldLabel == 'Email Id' || fieldLabel == 'DOB' || fieldLabel == 'Current / Last Company' || fieldLabel == 'Designation' 
	            		|| fieldLabel == 'Education' || fieldLabel == 'Work Experience' || fieldLabel == 'Current CTC' || fieldLabel == 'Expected CTC' 
	            		|| fieldLabel == 'Notice Period'){
	            	
	            	isLabelModified = 'N';
	            }else{
	            	isLabelModified = 'Y';
	            }
	 			*/
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
	        var clientLocationId = document.getElementById("clientLocationId").value;
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
	     	        url: sourceleadUrl+"/saveClientSubmissionTemplate?"+tokenParamName+"="+tokenValue+"&clientLocationId="+clientLocationId+"&templateId="+templateId+"&jsonData="+jsonData+"&jsonStringData="+jsonStringData, 
	     	        success: function(result) {
	     	        	if((result == 'home')){
	     	        		clientSubmissionTemplate(clientLocationId);  
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
  }
   /* 	$('body').on('submit', '#sjfb', function(e){
	  
    }); */
    	
  $('body').on('blur', '#sjfb', function() {
   	$('#sjfb').showHelpOnFocus();
    $('#sjfb').validateOnBlur(false, validationSettings); 
	});
  </script>
  


<script type="text/javascript">
function alertMessage(message){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancelClientSubmissionTemplate();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelClientSubmissionTemplate();'></b>";
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script type="text/javascript">
function cancelClientSubmissionTemplate(){
	$("#choicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
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




