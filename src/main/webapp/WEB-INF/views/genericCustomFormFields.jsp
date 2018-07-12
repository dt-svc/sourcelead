<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

	<c:forEach var="customForm" items="${customFieldsList}" varStatus="customFormIndex">
	<c:if test="${customForm.fieldType == 'TextBox'}">
		<c:set var="dataValidationConditionTextBox" value=""></c:set>
		<div class="form-group clearfix">
		  <c:if test="${not empty dataMapList}">
		        <c:forEach var="dataMap" items="${dataMapList}">
			        <c:if test="${customForm.id eq dataMap.key}">
				         <label class="col-md-3">${customForm.labelName}<span class="text-danger">${customForm.isRequired == 1 ? '*':''}</span>
				         <c:if test="${not empty customForm.labelinfo}">
				         		<i class="fa fa-info-circle" title="${customForm.labelinfo}"></i>
				         </c:if>
				         </label>
				    	 <div class="col-md-9">
				    		<input type="hidden" value="${customForm.id}" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldId">
				    		<input type="hidden" value="${dataMap.value[2]}" name="customFormData[${currentIndex+customFormIndex.index}].entityId">
			    		 	<c:choose>
							   <c:when test="${customForm.isRequired == 1}">
							    	<c:set var="dataValidationConditionTextBox" value="data-validation='required ${customForm.validation}'"/>
							  </c:when>
							 <c:otherwise>
							   		<c:set var="dataValidationConditionTextBox" value="data-validation-optional='true' data-validation='${customForm.validation}'"/>
							 </c:otherwise>
							</c:choose>
				     	 	<input type="text" class="form-control" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldData" placeholder="${customForm.placeHolder}" value="${dataMap.value[1]}" field-name="${customForm.labelName}" ${dataValidationConditionTextBox} ${customForm.isRequired == 1 ? 'data-check=true':'false'}>
				    	</div>
			        </c:if>
		        </c:forEach>
	        </c:if>
	       <c:if test="${empty dataMapList}">
		        <label class="col-md-3">${customForm.labelName}<span class="text-danger">${customForm.isRequired == 1 ? '*':''}</span>
		         <c:if test="${not empty customForm.labelinfo}">
				         		<i class="fa fa-info-circle" title="${customForm.labelinfo}"></i>
				</c:if>
				</label>
		    	 <div class="col-md-9">
		    		<input type="hidden" value="${customForm.id}" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldId">
		    		 <c:choose>
					   <c:when test="${customForm.isRequired == 1}">
					    <c:set var="dataValidationConditionTextBox" value="data-validation='required ${customForm.validation}'"/>
					  </c:when>
					 <c:otherwise>
					   <c:set var="dataValidationConditionTextBox" value="data-validation-optional='true' data-validation='${customForm.validation}'"/>
					 </c:otherwise>
					</c:choose>
		     	 	<input type="text" class="form-control" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldData" placeholder="${customForm.placeHolder}" value="" field-name="${customForm.labelName}"  ${dataValidationConditionTextBox} ${customForm.isRequired == 1 ? 'data-check=true':'false'}>
		    	</div>
	    	</c:if>
	    	
	  </div>
	 </c:if>
	 
	 
	 <c:if test="${customForm.fieldType == 'selectBox'}">
		<div class="form-group">
		<c:if test="${not empty dataMapList}">
		<c:forEach var="dataMap" items="${dataMapList}"> 
		    <c:if test="${customForm.id eq dataMap.key}">
			   <label class="col-md-3">${customForm.labelName}<span class="text-danger">${customForm.isRequired == 1 ? '*':''}</span>
			    <c:if test="${not empty customForm.labelinfo}">
				         		<i class="fa fa-info-circle" title="${customForm.labelinfo}"></i>
				</c:if>
			    </label>
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
		        <label class="col-md-3">${customForm.labelName}<span class="text-danger">${customForm.isRequired == 1 ? '*':''}</span>
		         <c:if test="${not empty customForm.labelinfo}">
				         		<i class="fa fa-info-circle" title="${customForm.labelinfo}"></i>
				</c:if>
		        </label>
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
	 
	  <c:if test="${customForm.fieldType == 'CheckBox'}">
	   <div class="form-group">
	   	  <c:if test="${not empty dataMapList}">
	   	  		<c:forEach var="dataMap" items="${dataMapList}">
	   	  			<c:if test="${customForm.id eq dataMap.key}">
	   	  			<label class="col-md-3">${customForm.labelName}<span class="text-danger">${customForm.isRequired == 1 ? '*':''}</span>
	   	  			 <c:if test="${not empty customForm.labelinfo}">
				         		<i class="fa fa-info-circle" title="${customForm.labelinfo}"></i>
				     </c:if>
	   	  			 </label>
		              <div class="col-md-9">
		                <div class="checkBox">
		                  <div class="checkboxLable">
		                  <input type="hidden" value="${dataMap.value[2]}" name="customFormData[${currentIndex+customFormIndex.index}].entityId">
		                  <input type="hidden" value="${customForm.id}" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldId">
		                    <c:forEach var="optionValue" items="${customForm.optionData}">
		     	 				<span class="mr">
		     	 				<input type="checkbox"  onclick="validateCheck('${customForm.id}')" class='customCheck${customForm.id}' value="${optionValue.option}" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldData" value="${optionValue.option}" ${fn:contains(dataMap.value[1],optionValue.option) ? 'checked="checked"':''}>${optionValue.option}
		     	 			</span>
		     	 			</c:forEach>
		     	 			<input type="hidden" value="${not empty dataMap.value[1] ? 'Y':''}" id="customCheckErrorMsg${customForm.id}" ${customForm.isRequired == 1 ? 'data-validation="required"':''} field-name="${customForm.labelName}" />
		                    </div>
		                </div>
		             </div>
		            </c:if>
		        </c:forEach>
	   	  </c:if>
	   	  <c:if test="${empty dataMapList}">
	   	  	<label class="col-md-3">${customForm.labelName}<span class="text-danger">${customForm.isRequired == 1 ? '*':''}</span>
	   	  	 <c:if test="${not empty customForm.labelinfo}">
				         		<i class="fa fa-info-circle" title="${customForm.labelinfo}"></i>
				</c:if>
	   	  	</label>
		              <div class="col-md-9 mb10">
		                <div class="checkBox">
		                  <input type="hidden" value="${customForm.id}" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldId">
		                    <c:forEach var="optionValue" items="${customForm.optionData}">
			     	 		<div class="clearfix">
			     	 			<input type="checkbox" class='customCheck${customForm.id}' value="${optionValue.option}" onclick="validateCheck('${customForm.id}')" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldData" >${optionValue.option}<br>
			     	 		</div>
			     	 		</c:forEach>
			     	 		<input type="hidden" id="customCheckErrorMsg${customForm.id}" ${customForm.isRequired == 1 ? 'data-validation="required"':''} field-name="${customForm.labelName}" />
		                </div>
		             </div>
	   	  </c:if>
       </div>
	 </c:if>
	   
	     <c:if test="${customForm.fieldType == 'radioButton'}">
	   <div class="form-group">
		  <c:if test="${not empty dataMapList}">
		        <c:forEach var="dataMap" items="${dataMapList}">
			        <c:if test="${customForm.id eq dataMap.key}">
				         <label class="col-md-3">${customForm.labelName}<span class="text-danger">${customForm.isRequired == 1 ? '*':''}</span>
				         </label>
				    	 <div class="col-md-9 radioIput">
				    	 <div class="mr">
				    		<input type="hidden" value="${customForm.id}" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldId">
				    		<input type="hidden" value="${dataMap.value[2]}" name="customFormData[${currentIndex+customFormIndex.index}].entityId">
				     	 	<c:forEach var="optionValue" items="${customForm.optionData}">
				     	 		<c:if test="${not empty optionValue.option}">
			     	 				<input type="radio" value="${optionValue.option}" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldData"   ${fn:contains(dataMap.value[1],optionValue.option) ? 'checked="checked"':''} field-name="${customForm.labelName}">&nbsp;&nbsp;${optionValue.option}
			     	 			</c:if>
			     	 		</c:forEach>
			     	 		</div>
				    	</div>
			        </c:if>
		        </c:forEach>
	        </c:if>
	       <c:if test="${empty dataMapList}">
		        <label class="col-md-3">${customForm.labelName}<span class="text-danger">${customForm.isRequired == 1 ? '*':''}</span>
		         <c:if test="${not empty customForm.labelinfo}">
				         		<i class="fa fa-info-circle" title="${customForm.labelinfo}"></i>
				</c:if>
		        </label>
		    	 <div class="col-md-9 radioIput">
		    		<input type="hidden" value="${customForm.id}" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldId">
		     	 	<c:forEach var="optionValue" items="${customForm.optionData}">
			     	 			<span class="mr">
			     	 			<input type="radio" value="${optionValue.option}" checked="checked" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldData" field-name="${customForm.labelName}">${optionValue.option}
			     	 			</span> 
			     	</c:forEach>
		    	</div>
	    	</c:if>
	  </div>
	   </c:if> 
	   
	   <c:if test="${customForm.fieldType == 'Date'}">
 			 <c:if test="${not empty dataMapList}">
	 			 <c:forEach var="dataMap" items="${dataMapList}">
	 			 	<c:if test="${customForm.id eq dataMap.key}">
		 			 <div class="form-group" id="errorDate${customForm.id}">
		                     <label class="col-md-3">${customForm.labelName}<span class="text-danger">${customForm.isRequired == 1 ? '*':''}</span>
		                     <c:if test="${not empty customForm.labelinfo}">
				         		<i class="fa fa-info-circle" title="${customForm.labelinfo}"></i>
				             </c:if>
		                     </label>
		                        <div class="col-md-9">
		                          <div class="input-group errorInptgroup">
		                             <input type="hidden" value="${customForm.id}" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldId">
		                             <input type="text"  id = "date${customForm.id}" readonly value="${dataMap.value[1]}" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldData" placeholder="${customForm.placeHolder}" field-name="${customForm.labelName}" class="form-control calendar customDate" onclick="dateScriptsCustomDate('${customForm.id}');" id="${customForm.fieldName}" ${customForm.isRequired == 1 ? 'data-validation="required"':''}>
		                             <span class="input-group-addon" onClick="clearDate(${customForm.id});"><i class="colorRed fa fa-times cursorPointer"></i></span>
		                          </div>
		                       </div>
		                 </div>
	                 </c:if>
	 			 </c:forEach>
 			 </c:if>
 			 <c:if test="${empty dataMapList}">
  			<div class="form-group" id="errorDate${customForm.id}">
                     <label class="col-md-3">${customForm.labelName}<span class="text-danger">${customForm.isRequired == 1 ? '*':''}</span></label>
                        <div class="col-md-9">
                          <div class="input-group errorInptgroup">
                             <input type="hidden" value="${customForm.id}" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldId">
                             <input type="text" id ="date${customForm.id}" readonly value="" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldData" placeholder="${customForm.placeHolder}" field-name="${customForm.labelName}" class="form-control calendar customDate" onclick="dateScriptsCustomDate('${customForm.id}');" id="${customForm.fieldName}" ${customForm.isRequired == 1 ? 'data-validation="required"':''}>
                             <span class="input-group-addon" onClick="clearDate(${customForm.id});"><i class="colorRed fa fa-times cursorPointer"></i></span>
                          </div>
                       </div>
                 </div>
                </c:if>
	   </c:if>
	 
	 <c:if test="${customForm.fieldType == 'textArea'}">
		<c:set var="dataValidationConditiontextArea" value=""></c:set>
		<div class="form-group clearfix">
		  <c:if test="${not empty dataMapList}">
		        <c:forEach var="dataMap" items="${dataMapList}">
			        <c:if test="${customForm.id eq dataMap.key}">
				         <label class="col-md-3">${customForm.labelName}<span class="text-danger">${customForm.isRequired == 1 ? '*':''}</span></label>
				    	 <div class="col-md-9">
				    		<input type="hidden" value="${customForm.id}" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldId">
				    		<input type="hidden" value="${dataMap.value[2]}" name="customFormData[${currentIndex+customFormIndex.index}].entityId">
				     	 	
				     	 	<c:choose>
							   <c:when test="${customForm.isRequired == 1}">
							    	<c:set var="dataValidationConditiontextArea" value="data-validation='required ${customForm.validation}'"/>
							  </c:when>
							 <c:otherwise>
							   		<c:set var="dataValidationConditiontextArea" value="data-validation-optional='true' data-validation='${customForm.validation}'"/>
							 </c:otherwise>
							</c:choose>
				     	 	
				     	 	<textarea rows="2" cols="50" class="form-control" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldData" placeholder="${customForm.placeHolder}" field-name="${customForm.labelName}" ${dataValidationConditiontextArea}  ${customForm.isRequired == 1 ? 'data-check=true':'false'}>${dataMap.value[1]}</textarea>
				    	</div>
			        </c:if>
		        </c:forEach>
	        </c:if>
	       <c:if test="${empty dataMapList}">
		        <label class="col-md-3">${customForm.labelName}<span class="text-danger">${customForm.isRequired == 1 ? '*':''}</span></label>
		    	 <div class="col-md-9">
		    		<input type="hidden" value="${customForm.id}" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldId">
				     	 	<c:choose>
							   <c:when test="${customForm.isRequired == 1}">
							    	<c:set var="dataValidationConditiontextArea" value="data-validation='required ${customForm.validation}'"/>
							  </c:when>
							 <c:otherwise>
							   		<c:set var="dataValidationConditiontextArea" value="data-validation-optional='true' data-validation='${customForm.validation}'"/>
							 </c:otherwise>
							</c:choose>
		     	 	<textarea rows="4" cols="50" class="form-control" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldData" placeholder="${customForm.placeHolder}" value="" field-name="${customForm.labelName}" ${dataValidationConditiontextArea} ${customForm.isRequired == 1 ? 'data-check=true':'false'}></textarea>
		    	</div>
	    	</c:if>
	    	
	  </div>
	 </c:if>
	 
	 <c:if test="${customForm.fieldType == 'email'}">
	 <c:set var="dataValidationConditionEmail" value=""></c:set>
		<div class="form-group clearfix">
		  <c:if test="${not empty dataMapList}">
		        <c:forEach var="dataMap" items="${dataMapList}">
			        <c:if test="${customForm.id eq dataMap.key}">
				         <label class="col-md-3">${customForm.labelName}<span class="text-danger">${customForm.isRequired == 1 ? '*':''}</span></label>
				    	 <div class="col-md-9 emailDiv">
				    		<input type="hidden" value="${customForm.id}" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldId">
				    		<input type="hidden" value="${dataMap.value[2]}" name="customFormData[${currentIndex+customFormIndex.index}].entityId">
				     	 	<c:choose>
							   <c:when test="${customForm.isRequired == 1}">
							    	<c:set var="dataValidationConditionEmail" value="data-validation='required ${customForm.validation}'"/>
							  </c:when>
							 <c:otherwise>
							   		<c:set var="dataValidationConditionEmail" value="data-validation-optional='true' data-validation='${customForm.validation}'"/>
							 </c:otherwise>
							</c:choose>
				     	 	<input type="text" class="form-control email" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldData" placeholder="${customForm.placeHolder}" value="${dataMap.value[1]}" field-name="${customForm.labelName}" ${dataValidationConditionEmail} ${customForm.isRequired == 1 ? 'data-check=true':'false'}>
				    	</div>
			        </c:if>
		        </c:forEach>
	        </c:if>
	       <c:if test="${empty dataMapList}">
		        <label class="col-md-3">${customForm.labelName}<span class="text-danger">${customForm.isRequired == 1 ? '*':''}</span></label>
		    	 <div class="col-md-9 emailDiv">
		    		<input type="hidden" value="${customForm.id}" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldId">
		     	 			<c:choose>
							   <c:when test="${customForm.isRequired == 1}">
							    	<c:set var="dataValidationConditionEmail" value="data-validation='required ${customForm.validation}'"/>
							  </c:when>
							 <c:otherwise>
							   		<c:set var="dataValidationConditionEmail" value="data-validation-optional='true' data-validation='${customForm.validation}'"/>
							 </c:otherwise>
							</c:choose>
		     	 	<input type="text" class="form-control email" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldData" placeholder="${customForm.placeHolder}" value="" field-name="${customForm.labelName}" ${dataValidationConditionEmail} ${customForm.isRequired == 1 ? 'data-check=true':'false'}>
		    	</div>
	    	</c:if>
	    	
	  </div>
	 </c:if>
	 
	 
	   
	</c:forEach>

<script>
$('.email').blur(function() {
    var testEmail = /^[A-Z0-9._%+-]+@([A-Z0-9-]+\.)+[A-Z]{2,4}$/i;
    if (this.value != null && this.value !='' && this.value !='undefined' && testEmail.test(this.value)){
    	$('.email').removeAttr("data-validation","validate_email");
    } else if(this.value != null && this.value !='' && this.value !='undefined' && !testEmail.test(this.value)){
    	$('.email').attr("data-validation","validate_email");
    } else if(this.value ==''){
    	$('.emailDiv').parent().find('p.jquery_form_error_message').remove();
    }
});
</script>



<script>
	function dateScriptsCustomDate(customDateId) {
// 		 $('#date'+customDateId).datepicker('destroy');
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}';
		 var finalDateFormat= getJSDateFormat(locationDate);
		 $('#date'+customDateId).datepicker({
		 dateFormat: finalDateFormat,
		 changeMonth: true,
		 changeYear: true, 
		 onSelect : function(selected) {
			 removeErrorMsgDate(customDateId);
		 }
		 });
		 $('#date'+customDateId).datepicker("show");
	 }
function removeErrorMsgDate(customDateId){
	$('#errorDate'+customDateId).find('p.jquery_form_error_message').remove(); 
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

 <script>
function clearDate(customId){
	$('#date'+customId).val('');
}

function validateCheck(custId){
	var isChecked = "false";
	$('.customCheck'+custId).each( function () {
	    if($(this).is(':checked'))
	    	isChecked = "true";
	});
	if(isChecked == "true"){
		$('#customCheckErrorMsg'+custId).val('Y');
    }
    else {
    	$('#customCheckErrorMsg'+custId).val('');
    }
}

</script> 