	
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
             <label class="col-md-4 mt10">Component Type</label>
            <div class="col-md-7 padding_right">
            <div class="dropdown">
              <button class="btn btn-default dropdown-toggle width100percnt tbldata-dotdot-200" type="button" id="componentList" data-toggle="dropdown">-- Select Component Type --
              <span class="caret"></span></button>
              <ul class="dropdown-menu ml width100percnt" role="menu" aria-labelledby="menu1" id="ctBtn">
               <c:forEach var="component" items="${componentList}" varStatus="rows">
                <li>
                  <div class="checkbox-new clearfix ml mt5">
                   <c:choose>
                    <c:when test="${not empty selComponentList && fn:contains(selComponentList, component.id)}">
                    <input id="com${component.id}" class="checkbox-custom componentGroup"  onclick="pushComponentIds();" componentId ="${component.id}" checked name="componentString" type="checkbox">
                    <label for="com${component.id}" class="checkbox-custom-label"><span class="ml5">${component.name}</span></label>
                  </div>
                   </c:when>
                   <c:otherwise>
                    <input id="com${component.id}" class="checkbox-custom componentGroup"  onclick="pushComponentIds();" componentId ="${component.id}" name="componentString" type="checkbox">
                    <label for="com${component.id}" class="checkbox-custom-label"><span class="ml5">${component.name}</span></label>
                    </c:otherwise>
                  </c:choose>
                </li>
 			</c:forEach>
                <li role="Address">
                  <a id="addComponentType">
                    <i class="fa fa-plus paddingRight mr10 text-primary"></i>Add Component Type
                  </a>
                </li>
              </ul>
            </div>
             <p id="componentValid" style="color: red; display:none">Component is required</p>
          </div>
          <div class="col-md-1 text-center padding_left">
            <a class="fa fa-refresh btn btn-primary newRefreshbtn" onclick ="refreshOrgComponentList();"></a>
          </div>
     <script>
     $(document).ready(function() {
    	 <c:if test="${not empty selComponentList}">
    	 pushComponentIds();
    	 </c:if>
     })
     </script>
     <script>
     var vmsUrl = '${SourceLeadWeb_vmsUrl}';
     var sourceleadUrl = '${SourceLeadWeb_vmsUrl}';
     var componentObj= [];
     var componentNames = [];
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
     function pushComponentIds() {
    	 componentObj= [];
    	 componentNames = [];
    	 //componentObj.toString();
    	 $('.componentGroup').each(function(index, value){
        	 if ($(value).is(":checked")){
        		 componentObj.push($(value).attr("componentId"));
        		 componentNames.push($(value).closest("li").text());
             }
         });
    	 if(componentObj.length==0){
    		 $('#componentValid').show();
    		 $('#componentList').text("-- Select Component --");
    		 return false;
    	 }else{
    		 $('#componentValid').hide();
    		 $('#componentList').text("");
    		 $('#componentList').text(componentNames.join(','));
    		 return true;
    	 }
    	 
     }
     </script>