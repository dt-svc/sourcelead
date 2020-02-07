	
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
             <label class="col-md-4 mt10">Component Type</label>
            <div class="col-md-7 padding_right">
            <div class="dropdown">
              <button class="btn btn-default dropdown-toggle width100percnt" type="button" id="menu1" data-toggle="dropdown">-- Select Component Type --
              <span class="caret"></span></button>
              <ul class="dropdown-menu ml width100percnt" role="menu" aria-labelledby="menu1" id="ctBtn">
               <c:forEach var="component" items="${componentList}" varStatus="rows">
                <li>
                  <div class="checkbox-new clearfix ml mt5">
                    <input id="com${component.id}" class="checkbox-custom" name="${component.id}" type="checkbox">
                    <label for="com${component.id}" class="checkbox-custom-label"><span class="ml5">${component.name}</span></label>
                  </div>
                </li>
 			</c:forEach>
                <li role="Address">
                  <a id="addComponentType">
                    <i class="fa fa-plus paddingRight mr10 text-primary"></i>Add Component Type
                  </a>
                </li>
              </ul>
            </div>
          </div>
          <div class="col-md-1 text-center padding_left">
            <a class="fa fa-refresh btn btn-primary newRefreshbtn" onclick ="refreshOrgComponentList();"></a>
          </div>
     <script>
     var vmsUrl = '${SourceLeadWeb_vmsUrl}';
     var sourceleadUrl = '${SourceLeadWeb_vmsUrl}';
     
     function refreshOrgComponentList(){
    	 $.ajax({
				type : "GET",
				url : vmsUrl + "/bgc/refreshBgcComponentList?",
				success : function(reponse) {
					$("#orgComponentsDiv").html(reponse);
				},
			});
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
    						} else {
    							location.replace(vmsUrl+"/bgc/addBgcTemplate");	
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