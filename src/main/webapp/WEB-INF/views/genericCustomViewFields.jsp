<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

	<c:forEach var="customForm" items="${customFieldsList}" varStatus="customFormIndex">
	<c:if test="${customForm.fieldType == 'TextBox'}">
		  <c:if test="${not empty dataMapList}">
		        <c:forEach var="dataMap" items="${dataMapList}">
			        <c:if test="${customForm.id eq dataMap.key}">
				        <c:if test="${not empty dataMap.value[1]}">
				        	<div class="require_wrap_details_cta"> <b>${customForm.labelName}:</b><span>${dataMap.value[1]}</span>
						            	<div class="clearfix"></div> 
						    </div>
				        </c:if>
			        </c:if>
		        </c:forEach>
	        </c:if>
	         <c:if test="${not empty dataMapList}">
	         		<div class="clearfix"></div> 
	         </c:if>
	 </c:if>
	 
	 
	 
	  <c:if test="${customForm.fieldType == 'selectBox'}">
		<c:if test="${not empty dataMapList}">
			<c:forEach var="dataMap" items="${dataMapList}"> 
		    <c:if test="${customForm.id eq dataMap.key}">
    	 		<c:if test="${not empty dataMap.value[1]}">
				        	<div class="require_wrap_details_cta"> <b>${customForm.labelName}:</b><span>${dataMap.value[1]}</span>
						            	<div class="clearfix"></div> 
						    </div>
				</c:if>
		    </c:if>
		  </c:forEach>
		</c:if>
		 <c:if test="${not empty dataMapList}">
	         		<div class="clearfix"></div> 
	     </c:if>
	 </c:if> 
	 
	  <c:if test="${customForm.fieldType == 'CheckBox'}">
	   	  <c:if test="${not empty dataMapList}">
	   	  		<c:forEach var="dataMap" items="${dataMapList}">
	   	  			<c:if test="${customForm.id eq dataMap.key}">
		             	<c:if test="${not empty dataMap.value[1]}">
				        	<div class="require_wrap_details_cta"> <b>${customForm.labelName}:</b><span>${dataMap.value[1]}</span>
						            	<div class="clearfix"></div> 
						    </div>
				        </c:if>
		            </c:if>
		        </c:forEach>
	   	  </c:if>
   	   	  <c:if test="${not empty dataMapList}">
         		<div class="clearfix"></div> 
         </c:if>
	 </c:if>
	   
	 <c:if test="${customForm.fieldType == 'radioButton'}">
		  <c:if test="${not empty dataMapList}">
		        <c:forEach var="dataMap" items="${dataMapList}">
			        <c:if test="${customForm.id eq dataMap.key}">
				    	<c:if test="${not empty dataMap.value[1]}">
				        	<div class="require_wrap_details_cta"> <b>${customForm.labelName}:</b><span>${dataMap.value[1]}</span>
						            	<div class="clearfix"></div> 
						    </div>
				        </c:if>
			        </c:if>
		        </c:forEach>
	        </c:if>
	         <c:if test="${not empty dataMapList}">
	         		<div class="clearfix"></div> 
	         </c:if>
	</c:if> 
	   
	   <c:if test="${customForm.fieldType == 'Date'}">
 			 <c:if test="${not empty dataMapList}">
	 			 <c:forEach var="dataMap" items="${dataMapList}">
	 			 	<c:if test="${customForm.id eq dataMap.key}">
	 			 	 	<c:if test="${not empty dataMap.value[1]}">
				        	<div class="require_wrap_details_cta"> <b>${customForm.labelName}:</b><span>${dataMap.value[1]}</span>
						            	<div class="clearfix"></div> 
						    </div>
				        </c:if>
	                 </c:if>
	 			 </c:forEach>
 			 </c:if>
 			  <c:if test="${not empty dataMapList}">
	         		<div class="clearfix"></div> 
	         </c:if>
	   </c:if>
	 
	 <c:if test="${customForm.fieldType == 'textArea'}">
		<c:set var="dataValidationConditiontextArea" value=""></c:set>
		  <c:if test="${not empty dataMapList}">
		        <c:forEach var="dataMap" items="${dataMapList}">
			        <c:if test="${customForm.id eq dataMap.key}">
			       	    <c:if test="${not empty dataMap.value[1]}">
				        	<div class="require_wrap_details_cta"> <b>${customForm.labelName}:</b><span>${dataMap.value[1]}</span>
						            	<div class="clearfix"></div> 
						    </div>
				        </c:if>
			        </c:if>
		        </c:forEach>
	        </c:if>
	         <c:if test="${not empty dataMapList}">
	         		<div class="clearfix"></div> 
	         </c:if>
	 </c:if>
	 
	 <c:if test="${customForm.fieldType == 'email'}">
		  <c:if test="${not empty dataMapList}">
		        <c:forEach var="dataMap" items="${dataMapList}">
			        <c:if test="${customForm.id eq dataMap.key}">
				        <c:if test="${not empty dataMap.value[1]}">
				        	<div class="require_wrap_details_cta"> <b>${customForm.labelName}:</b><span>${dataMap.value[1]}</span>
						            	<div class="clearfix"></div> 
						    </div>
				        </c:if>
			        </c:if>
		        </c:forEach>
	        </c:if>
	         <c:if test="${not empty dataMapList}">
	         		<div class="clearfix"></div> 
	         </c:if>
	 </c:if>
	 
	</c:forEach>