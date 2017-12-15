<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="customFormDiv">
<c:forEach var="formSections" items="${SourceleadForm_Sections}">
	<c:forEach var="customForm" items="${formSections.customFieldsList}">
	<c:if test="${customForm.fieldType == 'textfield'}">
		<div class="form-group">
	        <label class="col-md-3">${customForm.labelName}<span class="text-danger">${customForm.isRequired == 1 ? '*':''}</span></label>
	    	 <div class="col-md-9">
	     	 	<input type="text" class="form-control" id="" name="${customForm.fieldName}" placeholder="${customForm.placeHolder}" value="" field-name="Email Id" data-validation="${customForm.isRequired == 1 ? 'Required':''}" data-validation-optional="${customForm.isRequired == 0 ? 'true':''}">
	    	</div>
	  </div>
	 </c:if>
	 
	 <c:if test="${customForm.fieldType == 'checkBox'}">
	   <div class="form-group">
		             <label class="col-md-3"></label>
		              <div class="col-md-9">
		                <div class="checkbox checkBox">
		                  <label><input type="checkbox" value="" name="${customForm.fieldName}"  ${customForm.isRequired == 1 ? 'checked':''}>
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
                              <input type="text" value="" name="${customForm.fieldName}" class="form-control calendar" id="${customForm.fieldName}" readonly="readonly">
                              <span class="input-group-addon" onClick="clearDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
                           </div>
                        </div>
                  </div>
	   </c:if>
	   
	</c:forEach>
</c:forEach>
</div>

<script>
  $( function() {
	  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormat = getJSDateFormat(locationDate);
	  //end
	  var dateId = $(".calendar").val();
	  alert(dateId);
	  $('#dateId').datepicker({
	      dateFormat: finalDateFormat,
	      changeMonth: true,
	  	  changeYear: true, 
	  });
	  
  } );
</script>

<script>
function getCustomDependentField(){
	 var actionId = $("#actionId").val();
	 $.ajax({
			type : "POST" , 
				 url: sourceleadUrl+"/getFormSectionsByActionId?actionId="+actionId+"&requestFrom=genericCustomPage",
				 success: function(data) {
		        	$('#customFormDiv').empty();
		        	$('#customFormDiv').append(data);
				 }
		  })
}
</script>