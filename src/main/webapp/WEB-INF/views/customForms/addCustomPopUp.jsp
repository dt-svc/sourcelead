<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 <style>
	.mb {
	margin: 0px;
    margin-bottom: 15px !important;
	}
</style>
 
   <!-- <!--   Popup Code  -->
  <!-- <div class="popMaskCustom popMask-zindex"></div> -->
	<!-- <div class="cusFielPopup cusFiel-popup " id="popUpDataCustom"> -->
	 <form id="saveAddForm_form" name="saveAddForm_form">
	    <input name="id" id="customFieldId" value="${customFormPopDetails.id}" type="hidden"/>
	    <input type="hidden" value="${customFormPopDetails.customDependentField}" id="customDependndentFieldId">
	    <input type="hidden" value="${customFormPopDetails.slDependedntField}" id="slDependedntFieldId">
		<div class="popupHead">
			<h2>Add New Field</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		<div class="cuspopHead clearfix">
			<div class="col-md-6 padding_left">
			<c:if test="${!empty SourceleadForm_Sections}">
			<c:if test="${not empty disableFields}">
				<input type="hidden" id="addFieldSectionHidden" name="sectionId">
			</c:if>
			<select class="chosen-select form-control" id="addFieldSection" name="sectionId" onchange="getDependentFieldList();getCustomDependentFieldList();" ${disableFields}>
				 <c:forEach var="section" items="${SourceleadForm_Sections}" varStatus="sectionStatus">
				  <option value="${section.id}">${section.sectionName}</option>
				  </c:forEach>
			</select> 
			</c:if>
			<c:if test="${!empty SourceleadForm_Sections_pop}">
			<c:if test="${not empty disableFields}">
				<input type="hidden" id="addFieldSectionHidden" name="sectionId">
			</c:if>	
			<select class="chosen-select form-control" id="addFieldSection" name="sectionId" onchange="getDependentFieldList();getCustomDependentFieldList();" ${disableFields}>
				 <c:forEach var="section" items="${SourceleadForm_Sections_pop}" varStatus="sectionStatus">
				  <option ${section.id == customFormPopDetails.sectionId ?'selected':''} value="${section.id}">${section.sectionName}</option>
				  </c:forEach>
			</select> 
			</c:if>
			</div>
			<div class="col-md-6 noPad">
				<c:if test="${not empty disableFields}">
					<input type="hidden" id="fieldsDropDownHidden" name="fieldType">
				</c:if>
				<select class="chosen-select form-control" id="fieldsDropDown" name="fieldType"  onchange="getSelectedFieldDetails();" ${disableFields}>
				  <option value="">--Select Field Type--</option>
				  <option value ="textfield" ${customFormPopDetails.fieldType =='textfield' ? 'selected':''} selected>Text field</option>
				  <%-- <option value ="datefield" ${customFormPopDetails.fieldType =='datefield' ? 'selected':''} >Date field</option> --%>
				  <option value ="selectfield" ${customFormPopDetails.fieldType =='selectfield' ? 'selected':''} >Select field</option>
				  <%-- <option value ="checkBox" ${customFormPopDetails.fieldType =='checkBox' ? 'selected':''} >Check box</option> --%>
				  <%-- <option value ="radioButton" ${customFormPopDetails.fieldType =='radioButton' ? 'selected':''} >Radio button</option> --%>
				</select>				
			</div>	
				<input type="hidden" id="disableFieldsId" value="${disableFields == 'disabled' ? '1':'0'}">		
		</div>
		
		<div class="popupMid clearfix">

				 <div class="col-sm-12 noPad mt clearfix">
				  	  <div class="col-md-3 noPad labHeight">Label<span class="text-danger paddingLeft">*</span></div>
					  <div class="col-md-6"><input name="labelName" id="labelName" value="${customFormPopDetails.labelName}" field-name="Label Name" class="field-label form-control input-sm" data-validation="required validate_length length1-250"  type="text"></div>
				</div>
			  
			  <div class="col-sm-12 noPad mt clearfix">
				  	  <div class="col-md-3 noPad labHeight">Is Required? <span class="text-danger paddingLeft">*</span></div>
					  <div class="col-md-6"><input name="isRequired" value="${empty customFormPopDetails.isRequired ? '0' : customFormPopDetails.isRequired}" ${customFormPopDetails.isRequired == '1'?'checked':''} id="isRequired" class="toggle-required" field-name="isRequired" type="checkbox"></div>
				</div>
			  
				<div class="clearfix"></div>
				<div  id="dateFieldDiv" style="display: none">
					<div class="col-sm-12 noPad mt clearfix">
					   <div class="col-md-3 noPad labHeight">Min Date</div>
						<div class="col-md-6"><input name="minDate" id="minDate" value="${customFormPopDetails.minDate}" id = "minDate" class="field-label form-control input-sm calendar" type="text"> </div>
						<div class="col-md-3 lblHeight"><span>&nbsp;</span></div>
					</div>
					
					<div class="col-sm-12 noPad mt clearfix">
						   <div class="col-md-3 noPad labHeight">Max Date</div>
							<div class="col-md-6"><input name="maxDate" id="maxDate" value="${customFormPopDetails.maxDate}" id = "maxDate" class="field-label form-control input-sm calendar" type="text"> </div>
							<div class="col-md-3 lblHeight"><span>&nbsp;</span></div>
					</div>
			  </div>


            <div  id="selectFieldDiv" style="display: none">
				<div class="col-sm-12 noPad mt clearfix">
					   <div class="col-md-3 noPad labHeight"><p>Options</p></div>
						<c:if test="${not empty customFormPopDetails.optionData}">
							<div class="col-md-6">
							<c:forEach var="options" items="${customFormPopDetails.optionData}" varStatus="optionStatus">
								<input name="optionData[${optionStatus.index}].id" value="${options.id}" id ="selectOptionId" type="hidden">
								<input name="optionData[${optionStatus.index}].option" value="${options.option}" id ="selectOptionId" class="field-label form-control input-sm mb" type="text">
							</c:forEach>
							 </div>	
						</c:if>
						<c:if test="${empty customFormPopDetails.optionData}">
							<div class="col-md-6"><input name="optionData[0].option" value="" id ="selectOptionId" class="field-label form-control input-sm" type="text"> </div>
						</c:if>
						<div class="col-md-3 lblHeight"><span>&nbsp;</span>
					</div>
				</div>
				
				<input type="hidden" id="optionCounter" value="${fn:length(customFormPopDetails.optionData)}"/>
				<div id="optionsDiv"></div>	
				 <div class="col-sm-12 noPad mt clearfix">
				  <div class="col-md-3 noPad labHeight">&nbsp;</div>
				  <div class="col-md-6" id="addOptionDiv"><a href="#" onclick="addOption();"><p>Add Option</p></a></div>
				</div>			  
			</div>
			
			<div class="col-sm-12 noPad mt clearfix" id="placeHolderDiv">
			  <div class="col-md-3 noPad labHeight">PlaceHolder</div>
			  <div class="col-md-6"><input name="placeHolder" id="placeHolder" value="${customFormPopDetails.placeHolder}" class="field-label form-control input-sm" type="text"> </div>
			</div>
			
			<div class="col-sm-12 noPad mt clearfix">
			  <div class="col-md-3 noPad labHeight">Status<span class="text-danger paddingLeft">*</span></div>
			  <div class="col-md-6 dropdown-select">
			  <select name="status" class="field-label form-control input-sm" id="statusId" field-name="Status" data-validation="required">
			  	<option value="" selected>--Select Status--</option>
			  	<option ${customFormPopDetails.status == 1 ? 'selected':''} value="1">Active</option>
			  	<option ${customFormPopDetails.status == 0 ? 'selected':''} value="0">In-Active</option>
			  </select>
			  </div>
			</div>
			
			<div class="col-sm-12 noPad mt clearfix" id="placeHolderDiv">
			  <div class="col-md-3 noPad labHeight">Description</div>
			  <div class="col-md-6"><input name="fieldDescription" id="fieldDescription" value="${customFormPopDetails.fieldDescription}" class="field-label form-control input-sm" type="text"> </div>
			</div>
			
			<div class="col-sm-12 noPad mt clearfix" id="labelInfoDiv">
			  <div class="col-md-3 noPad labHeight">Tool Tip</div>
			  <div class="col-md-6"><input type="text" name="labelinfo" id="labelinfo" value="${customFormPopDetails.labelinfo}" class="field-label form-control input-sm" > </div>
			</div>
			
			<div class="col-sm-12 noPad mt clearfix">
			  <div class="col-md-3 noPad labHeight">Dependent Fields</div>
			  <div class="col-md-6 dropdown-select"><select name="slDependedntField" class="field-label form-control input-sm" id="dependentFieldDiv"> 
			  </select>
			  </div>
			</div>
			
			<div class="col-sm-12 noPad mt clearfix" id="customDependentFieldDiv">
			  <div class="col-md-3 noPad labHeight">Custom Dependent Fields</div>
			  <div class="col-md-6"><select name="customDependentField" class="field-label form-control input-sm" id="customDependentField"> </select></div>
			</div>
			
			</div>
		
		
		<div class="immiPopFoot">
            <input value="Add" class="" name="save" id="saveNewField"  type="button" onclick="saveAddFormField('${customFormPopDetails.id}');">
            <input value="Cancel" class="popCan" name = "cancel" type="button">
          </div>	
          </form>	
		<!-- </div> -->

<!--   Popup Code  -->


 <script>
 $('#isRequired').click(function() {
	        var value = $(this).val();
	        if( $( this ).attr( 'type' ) === 'checkbox' ) {
	            value = +$(this).is( ':checked' );
	            $('#isRequired').attr("value",value);
	        }
});	
 </script>

 <script>
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
		 $("#addFieldSection").chosen();
	    $("#fieldsDropDown").chosen();
	    $("#fieldsDropDown").trigger("chosen:updated");
	    $("#addFieldSection").trigger("chosen:updated");
	    
	}); 
 </script>