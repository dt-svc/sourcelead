	
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" href="/resources/css/style.css?staticTime=${staticTime}" />
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <script src="${SourceLeadWeb_vmsUrl}/resources/js/bgcTemplateComponentFormBuilder.js?staticTime=${staticTime}"></script>

             <label class="col-md-4 mt10">Component Type<span class="colorRed ml5">*</label>
            <div class="col-md-6 padding_right">
	            <select multiple class="chosen-select" data-placeholder="Select Component" id="componentString" name="componentString" field-name="Component Type"  data-validation="required">
			<c:forEach var="component" items="${componentList}" varStatus="rows">
				<c:choose>
					<c:when
						test="${not empty selComponentList && fn:contains(selComponentList, component.id)}">
						<option selected value="${component.id}">${component.name}</option>
					</c:when>
					<c:otherwise>
						<option value="${component.id}">${component.name}</option>
					</c:otherwise>
				</c:choose>
				</li>
			 </c:forEach>
		     </select>
	      </div>
          <div class="col-md-2 text-center padding_left">
          <a id="addComponentType" title="Add Component Type">
                    <i class="fa fa-plus btn btn-primary newRefreshbtn"></i>
          </a>
            <a class="fa fa-refresh btn btn-primary newRefreshbtn" onclick ="refreshOrgComponentList();"></a>
          </div>
      
     <script>
     var vmsUrl = '${SourceLeadWeb_vmsUrl}';
     var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
     var templateUuid ='${bgcTemplateDto.uuid}';
     var templateId = '${bgcTemplateDto.id}'; // used for edit mode to show forms.
     function refreshOrgComponentList(){
    	 $.ajax({
				type : "GET",
				url : vmsUrl + "/bgc/refreshBgcComponentList?templateUuid="+templateUuid,
				success : function(reponse) {
					$("#orgComponentsDiv").html(reponse);
					$("#componentString").chosen();
				},
			});
     }
     $("#addComponentType").click(function() {
    	  $("#componenttypepop, .mask").fadeIn();
    });
     
     var result =""
     var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
     var templateUuid =' ';
	  var idValue ="";
     $('#componentString').chosen().change(function(e, params){
         var result =""  	
          if (params.selected) {
        	  var selCompDivText =  $('#componentString [value='+params.selected+']').text();
        	   idValue= selCompDivText.replace(/ /g, "_");
		       	result= result+'<div class="col-md-10" id ="'+idValue+'">';
		     	result= result+'  <div class="quetionwidget mb">';
		     	result= result+' <div class="clearfix row">';
		     	result= result+' <div class="col-md-2 col-md-offset-10">';
		     	result= result+'     <div class="dropdown">';
		     	result= result+'    <button class="btn btn-success dropdown-toggle btn-block" type="button" data-toggle="dropdown" id="addFiled-dropdown'+idValue+'"  aria-expanded="false">Add Type';
		     	result= result+'   <span class="caret"></span></button>';
		     	result= result+'     <ul id="add-field9'+idValue+'" class="dropdown-menu pull-right">';
		     	result= result+'        <li><a id="add-text9'+idValue+'" data-type="text" href="javascript:void(0)"><span class="fa fa-text-width">&nbsp;&nbsp;</span>Text Box</a>';
		     	result= result+'         </li><li><a id="add-textarea9'+idValue+'" data-type="textarea" href="javascript:void(0)"><span class="fa fa-text-width">&nbsp;&nbsp;</span>Text Area</a></li>';
		     	result= result+'        <li><a id="add-select9'+idValue+'" data-type="select" href="javascript:void(0)"><span class="fa fa-caret-down">&nbsp;&nbsp;&nbsp;</span>Select Box</a></li>';
		     	result= result+'         <li><a id="add-checkbox9'+idValue+'"  data-type="checkbox" href="javascript:void(0)"><span class="fa fa-check-square-o">&nbsp;&nbsp;</span>Check Box</a></li>';
		     	result= result+'          <li><a id="add-radio9'+idValue+'"  data-type="radio" href="javascript:void(0)"><span class="fa fa-check-circle-o">&nbsp;&nbsp;</span>Radio Button</a></li>';
		     	result= result+'         <li><a id="add-comments9'+idValue+'"  data-type="comments" href="javascript:void(0)"><span class="fa fa-comment-o">&nbsp;&nbsp;</span>Comments</a></li>';
		     	result= result+'          <li><a id="add-star9'+idValue+'" data-type="star" href="javascript:void(0)"><span class="fa fa-star-o">&nbsp;&nbsp;</span>Star</a></li>';
		     	result= result+'          <li><a id="add-scale9'+idValue+'"  data-type="scale" href="javascript:void(0)"><span class="fa fa-sliders">&nbsp;&nbsp;</span>Scale</a></li>';
		     	result= result+'         <li><a id="add-calendar9'+idValue+'"  data-type="calendar" href="javascript:void(0)"><span class="fa fa-calendar">&nbsp;&nbsp;</span>Calendar</a></li>';
		     	result= result+'       <li><a id="add-file9'+idValue+'"  data-type="file" href="javascript:void(0)"><span class="fa fa-cloud-upload">&nbsp;&nbsp;</span>File Upload</a></li>';
		     	result= result+'     </ul>';
		     	result= result+'    </div>';
		     	result= result+'    </div>';
		     	result= result+'  </div>';
		       
		     	result= result+'      <div class="checkbox-new">';
		     	result= result+'         <label for="address" class="checkbox-custom-label"></label>';
		     	result= result+' <h4>'+selCompDivText+'</h4> ';
		     	result= result+'      </div>';
		     	
		     	
		     	result= result+ ' <div id="sjfb-wrap"> ';
		     	result= result+ '<form id="'+idValue+'" novalidate> ';
		     	
		     	result= result+'     <div class="clearfix"></div>';
				result= result+'     <div class="clearfix row">';
		        
		        result= result+'      <label class="col-md-5"><div class="col-md-3 lblHeight">Sent to:</div>';
		     	result= result+'        <div class="col-md-9">';
		     	result= result+'              <input type="text" id="sentTo'+idValue+'" field-name="Sentto" class="field-label form-control input-sm">';
		     	result= result+'      </div>';
		     	result= result+'       </label>';
		     	result= result+'     </div>';
		     	
		     	
		     	
		     	
		     	
		        result= result+'     <div id="form-fields'+idValue+'"> ';
		        
		        
		     	
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
        }else{
        	var removeCompDivId =  $('#componentString [value='+params.deselected+']').text().replace(/ /g, "_");
        	$('#'+removeCompDivId).remove();
        }
         customizeFields(sourceleadUrl,templateUuid);
     });	
    
     $(document).ready(function() {
    	 buildComponetJsonForm('${bgcTemplateDto.id}'); // or use templateId
     })
     
     function buildComponetJsonForm(templateId){
    	 if(typeof templateId!= "undefined" && templateId != null && templateId !='' && templateId != 'null'){
    	 $("#componentString  option:selected").each(function(index) {
    		 var formId=this.text;
    		 var formId = formId.replace(/ /g, "_");
    		 console.log(" formId is "+formId);
    		 loadSavedBgcForm(templateId , formId);
         });
       }
     }

     function  addBgcComponent() {
    		if ($('#bgcComponentFrm').validate(false, validationSettings)) {
    			var formData = getFormData("bgcComponentFrm");
    			// below is for edit functionality of the status
    			  $("#saveBgcComponentBtn").text('Processing..'); 
    	          $("#saveBgcComponentBtn").addClass("disabled");
    			$.ajax({
    				type : "POST",
    				data : formData,
    				url : vmsUrl + "/bgc/SaveOrUpdateBgcComponent",
    				success : function(result) {
    					if (result.response == "AWKWARD") {
    						location.href = "/errorPage";
    					} else if (result.response == "SUCCESS") {
    						if (result.object == "DataMissing") {
    							$("#saveBgcComponentBtn").removeClass("disabled");
    							$("#saveBgcComponentBtn").text('Save');
    							showError("Invalid data");
    						}else{
    							$(".popClose").trigger("click");
    						}
    						
    					} else if (result.indexOf('Sign In') != -1) {
    						location.replace(sourceleadUrl);
    					} else {
    						showError("Operation failed, please try again");
    					}

    				}
    			});
    		}
    	}
   
     </script>
     
     