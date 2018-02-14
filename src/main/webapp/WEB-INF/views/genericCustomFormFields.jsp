<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

	<c:forEach var="customForm" items="${customFieldsList}" varStatus="customFormIndex">
	<c:if test="${customForm.fieldType == 'textfield'}">
		<div class="form-group">
		  <c:if test="${not empty dataMapList}">
		        <c:forEach var="dataMap" items="${dataMapList}">
			        <c:if test="${customForm.id eq dataMap.key}">
				         <label class="col-md-3">${customForm.labelName}<span class="text-danger">${customForm.isRequired == 1 ? '*':''}</span></label>
				    	 <div class="col-md-9">
				    		<input type="hidden" value="${customForm.id}" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldId">
				    		<input type="hidden" value="${dataMap.value[2]}" name="customFormData[${currentIndex+customFormIndex.index}].entityId">
				     	 	<input type="text" class="form-control" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldData" placeholder="${customForm.placeHolder}" value="${dataMap.value[1]}" field-name="${customForm.labelName}" ${customForm.isRequired == 1 ? 'data-validation="required"':''} ${customForm.isRequired == 0 ? 'data-validation-optional="true"':''}>
				    	</div>
			        </c:if>
		        </c:forEach>
	        </c:if>
	       <c:if test="${empty dataMapList}">
		        <label class="col-md-3">${customForm.labelName}<span class="text-danger">${customForm.isRequired == 1 ? '*':''}</span></label>
		    	 <div class="col-md-9">
		    		<input type="hidden" value="${customForm.id}" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldId">
		     	 	<input type="text" class="form-control" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldData" placeholder="${customForm.placeHolder}" value="" field-name="${customForm.labelName}" ${customForm.isRequired == 1 ? 'data-validation="required"':''} ${customForm.isRequired == 0 ? 'data-validation-optional="true"':''}>
		    	</div>
	    	</c:if>
	    	
	  </div>
	 </c:if>
	 
	 
	 <c:if test="${customForm.fieldType == 'selectfield'}">
		<div class="form-group">
		<c:if test="${not empty dataMapList}">
		<c:forEach var="dataMap" items="${dataMapList}"> 
		    <c:if test="${customForm.id eq dataMap.key}">
			   <label class="col-md-3">${customForm.labelName}<span class="text-danger">${customForm.isRequired == 1 ? '*':''}</span></label>
		    	 <div class="col-md-9">
		    	 <input type="hidden" value="${customForm.id}" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldId">
		    	 <input type="hidden" value="${dataMap.value[2]}" name="customFormData[${currentIndex+customFormIndex.index}].entityId">
		     	 	<select class="chosen-select form-control" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldData" placeholder="${customForm.placeHolder}" field-name="${customForm.labelName}" ${customForm.isRequired == 1 ? 'data-validation="required"':''} ${customForm.isRequired == 0 ? 'data-validation-optional="true"':''}>
		     	 		<option value="">-select option-</option>
		     	 		<c:forEach var="optionValue" items="${customForm.optionData}">
		     	 			<option value="${optionValue.option}" ${dataMap.value[1] == optionValue.option ? 'selected="selected"':''}>${optionValue.option}</option>
		     	 		</c:forEach>
		     	 	</select>
		    	</div>
		    </c:if>
		  </c:forEach>
		</c:if>
			<c:if test="${empty dataMapList}">
		        <label class="col-md-3">${customForm.labelName}<span class="text-danger">${customForm.isRequired == 1 ? '*':''}</span></label>
		    	 <div class="col-md-9">
		    	 <input type="hidden" value="${customForm.id}" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldId">
		     	 	<select class="chosen-select form-control" id="" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldData" placeholder="${customForm.placeHolder}" field-name="${customForm.labelName}" ${customForm.isRequired == 1 ? 'data-validation="required"':''} ${customForm.isRequired == 0 ? 'data-validation-optional="true"':''}>
		     	 		<option value="" selected disabled>-select option-</option>
			     	 		<c:forEach var="optionValue" items="${customForm.optionData}">
			     	 			<option value="${optionValue.option}">${optionValue.option}</option>
			     	 		</c:forEach>
		     	 	</select>
		    	</div>
		    </c:if>
	  </div>
	 </c:if> 
	 
	  <c:if test="${customForm.fieldType == 'checkBox'}">
	   <div class="form-group">
	   	  <c:if test="${not empty dataMapList}">
	   	  		<c:forEach var="dataMap" items="${dataMapList}">
	   	  			<c:if test="${customForm.id eq dataMap.key}">
	   	  			<label class="col-md-3">${customForm.labelName}</label>
		              <div class="col-md-9">
		                <div class="checkbox checkBox">
		                  <label>
		                  <input type="hidden" value="${dataMap.value[2]}" name="customFormData[${currentIndex+customFormIndex.index}].entityId">
		                  <input type="hidden" value="${customForm.id}" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldId">
		                    <c:forEach var="optionValue" items="${customForm.optionData}">
		     	 				<input type="checkbox" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldData" value="${optionValue.option}" ${dataMap.value[1] eq optionValue.option ? 'checked="checked"':''}>${optionValue.option}<br>
		     	 			</c:forEach>
		                    </label>
		                </div>
		             </div>
		            </c:if>
		        </c:forEach>
	   	  </c:if>
	   	  <c:if test="${empty dataMapList}">
	   	  	<label class="col-md-3">${customForm.labelName}</label><br>
		              <div class="col-md-9 mb10">
		                <div class="checkbox checkBox">
		                <label>
		                  <input type="hidden" value="${customForm.id}" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldId">
		                    <c:forEach var="optionValue" items="${customForm.optionData}">
			     	 			<input type="checkbox" value="${optionValue.option}" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldData"  ${customForm.isRequired == 1 ? 'checked':''}>${optionValue.option}<br>
			     	 		</c:forEach>
			     	 		 </label>
		                </div>
		             </div>
	   	  </c:if>
       </div>
	 </c:if>
	   
	   <%--<c:if test="${customForm.fieldType == 'datefield'}">
	  <div class="form-group">
                      <label class="col-md-3">${customForm.labelName}</label>
                         <div class="col-md-9">
                           <div class="input-group">
                              <input type="hidden" value="${customForm.id}" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldId">
                              <input type="text" value="" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldData" class="form-control calendar customDate" onclick="dateScriptsCustomDate();" id="${customForm.fieldName}" readonly="readonly">
                              <span class="input-group-addon" onClick="clearDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
                           </div>
                        </div>
                  </div>
	   </c:if>
	    --%>
	</c:forEach>


<script>
function seletedDataForSelectBox(){
	
}
</script>

<script>
   function dateScriptsCustomDate() {
	  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormat = getJSDateFormat(locationDate);
	  $('.customDate').datepicker({
	      dateFormat: finalDateFormat,
	      changeMonth: true,
	  	  changeYear: true, 
	  });
  } 
</script>

<script type="text/javascript">
$(function(){
    var config = {
		  '.chosen-select'           : {},
		  '.chosen-select-deselect'  : {allow_single_deselect:true},
		  '.chosen-select-no-single' : {disable_search_threshold:10},
		  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
		  '.chosen-select-width'     : {width:"95%"}
		}
       
    $('.chosen-select').each(function() {
			var $this = $(this);
			$this.next().css({'width': '100%'});
	  })
});
</script>
