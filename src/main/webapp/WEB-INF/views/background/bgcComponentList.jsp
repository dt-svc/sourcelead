	
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
     var sourceleadUrl = '${SourceLeadWeb_vmsUrl}';
     var templateUuid ='${bgcTemplateDto.uuid}';
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