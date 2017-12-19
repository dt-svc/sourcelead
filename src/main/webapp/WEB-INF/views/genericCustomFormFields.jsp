<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

	<c:set var="currentIndex" value="0"></c:set>
	<c:forEach var="customForm" items="${customFieldsList}" varStatus="customFormIndex">
	<c:if test="${customForm.fieldType == 'textfield'}">
		<div class="form-group">
	        <label class="col-md-3">${customForm.labelName}<span class="text-danger">${customForm.isRequired == 1 ? '*':''}</span></label>
	    	 <div class="col-md-9">
	    		<input type="hidden" value="${customForm.id}" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldId">
	     	 	<input type="text" class="form-control" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldData" placeholder="${customForm.placeHolder}" value="" field-name="Email Id" data-validation="${customForm.isRequired == 1 ? 'Required':''}" data-validation-optional="${customForm.isRequired == 0 ? 'true':''}">
	    	</div>
	  </div>
	 </c:if>
	 
	 <c:if test="${customForm.fieldType == 'checkBox'}">
	   <div class="form-group">
		             <label class="col-md-3"></label>
		              <div class="col-md-9">
		                <div class="checkbox checkBox">
		                  <label>
		                  <input type="hidden" value="${customForm.id}" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldId">
		                  <input type="checkbox" value="" name="customFormData[${currentIndex+customFormIndex.index}].customFormFieldData"  ${customForm.isRequired == 1 ? 'checked':''}>
		                    ${customForm.labelName}</label>
		                </div>
		             </div>
               </div>
	   </c:if>
	   
	   <c:if test="${customForm.fieldType == 'datefield'}">
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
	   
	   <%-- <c:if test="${customForm.fieldType == 'selectfield'}">
		<div class="form-group">
	        <label class="col-md-3">${customForm.labelName}<span class="text-danger">${customForm.isRequired == 1 ? '*':''}</span></label>
	    	 <div class="col-md-9">
	     	 	<select class="chosen-select form-control" id="" name="${customForm.fieldName}" placeholder="${customForm.placeHolder}" field-name="${customForm.labelName}" data-validation="${customForm.isRequired == 1 ? 'Required':''}" data-validation-optional="${customForm.isRequired == 0 ? 'true':''}">
	     	 		<option value="">1</option>
	     	 		<option value="">2</option>
	     	 	</select>
	    	</div>
	  </div>
	 </c:if> --%>
	</c:forEach>


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
 
<!-- 
<script>
function getCustomDependentField(){
	 var actionId = $("#actionId").val();
	 alert(actionId);
	 $.ajax({
			type : "POST" , 
				 url: sourceleadUrl+"/getAllSectionByActionId?actionId="+actionId,
				 success: function(data) {
				 alert(data);
		        	$('#customFormDiv').empty();
		        	$('#customFormDiv').append(data);
				 }
		  })
}
</script> -->


