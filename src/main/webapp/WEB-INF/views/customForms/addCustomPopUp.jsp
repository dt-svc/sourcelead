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
	<c:if test="${not empty type and type ne 'summary' and type ne 'SourceleadSummary'}">
	 <div class="cusFielPopup cusFiel-popup " id="popUpDataCustom">
	 <form id="saveAddForm_form" name="saveAddForm_form">
	    <input name="id" id="customFieldId" value="${customFormPopDetails.id}" type="hidden"/>
	    <input type="hidden" value="${customFormPopDetails.customDependentField}" id="customDependndentFieldId">
	    <input type="hidden" value="${customFormPopDetails.slDependedntField}" id="slDependedntFieldId">
		<div class="popupHead">
			<h2>${customFormPopDetails.customEditType eq 'Edit' ? 'Edit Field':'Add New Field'} </h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
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
				  <option value ="TextBox" ${customFormPopDetails.fieldType =='textBox' ? 'selected':''} selected>Text Box</option>
				  <option value ="Date" ${customFormPopDetails.fieldType =='Date' ? 'selected':''} >Date Field</option>
				  <option value ="selectBox" ${customFormPopDetails.fieldType =='selectBox' ? 'selected':''} >Select Box</option>
				  <option value ="CheckBox" ${customFormPopDetails.fieldType =='CheckBox' ? 'selected':''} >Check Box</option>
				  <option value ="radioButton" ${customFormPopDetails.fieldType =='radioButton' ? 'selected':''} >Radio Button</option>
				   <option value ="textArea" ${customFormPopDetails.fieldType =='textArea' ? 'selected':''} >Text Area</option>
				    <option value ="email" ${customFormPopDetails.fieldType =='email' ? 'selected':''} >Email</option>
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
				
				<div class="col-sm-12 noPad mt clearfix" style="display: none;" id = "validateRangeDiv">
				  <div class="col-md-3 noPad labHeight">Validation Range<span class="text-danger paddingLeft">*</span></div>
				  <div class="col-md-6 dropdown-select">
					  <select name="validationRange" data-placeholder="Select Validation Range" class="form-control input-sm" id="validationRange" field-name="validation Range" data-validation="required">
						  	<option value="" selected>Select Field Length Validation</option>
						  	<option ${customFormPopDetails.validationRange == "Length 2-250" ? 'selected':''} value="Length2-250">Length 2-250</option>
						  	<option ${customFormPopDetails.validationRange == "Length 2-500" ? 'selected':''} value="Length2-500">Length 2-500</option>
						  	<option ${customFormPopDetails.validationRange == "Length 500 Above" ? 'selected':''} value="Length 500 Above">Length 500 and Above</option>
					  </select>
				  </div>
				</div>

				<div class="col-sm-12 noPad mt clearfix" style="display: none;" id="validateLengthDiv">
				  <div class="col-md-3 noPad labHeight">Validation Type<span class="text-danger paddingLeft">*</span></div>
				  <div class="col-md-6 dropdown-select">
					  <select name="validationType" data-placeholder="Select Validation Type" class="chosen-select form-control input-sm" id="validationType" field-name="validation Type" data-validation="required">
						  	<option value="" selected>Select Field Type Validation</option>
						  	<option ${customFormPopDetails.validationType == "Alphabets" ? 'selected':''} value="Alphabets">Alphabet</option>
						  	<option ${customFormPopDetails.validationType == "Numbers" ? 'selected':''} value="Numbers">Number</option>
						  	<option ${customFormPopDetails.validationType == "Aplhanumeric" ? 'selected':''} value="Aplhanumeric">Alphanumeric</option>
					  </select>
				  </div>
				</div>
			  
				<%-- <div class="clearfix"></div>
				<div  id="dateFieldDiv" style="display: none">
					<div class="col-sm-12 noPad mt clearfix">
					   <div class="col-md-3 noPad labHeight">Min Date</div>
						<div class="col-md-6"><input name="minDate" readonly id="minDate" value="${customFormPopDetails.minDate}" id = "minDate" class="field-label form-control input-sm calendar" type="text"> </div>
						<div class="col-md-3 lblHeight"><span>&nbsp;</span></div>
					</div>
					
					<div class="col-sm-12 noPad mt clearfix">
						   <div class="col-md-3 noPad labHeight">Max Date</div>
							<div class="col-md-6"><input name="maxDate" readonly id="maxDate" value="${customFormPopDetails.maxDate}" id = "maxDate" class="field-label form-control input-sm calendar" type="text"> </div>
							<div class="col-md-3 lblHeight"><span>&nbsp;</span></div>
					</div>
			  </div> --%>


            <div  id="selectFieldDiv" style="display: none">
				<div class="col-sm-12 noPad mt clearfix">
					   <div class="col-md-3 noPad labHeight"><p>Options</p></div>
						<c:if test="${not empty customFormPopDetails.optionData}">
							<div class="col-md-6">
							<c:forEach var="options" items="${customFormPopDetails.optionData}" varStatus="optionStatus">
								<input name="optionData[${optionStatus.index}].id" value="${options.id}" id ="selectOptionId" type="hidden">
								<input name="optionData[${optionStatus.index}].option" value="${options.option}" id ="selectOptionId" field-name="Data" class="field-label form-control input-sm mb" type="text" data-validation="required">
							</c:forEach>
							 </div>	
						</c:if>
						<c:if test="${empty customFormPopDetails.optionData}">
							<div class="col-md-6"><input name="optionData[0].option" field-name="Data" value="" id ="selectOptionId" class="field-label form-control input-sm" type="text" data-validation="required"> </div>
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
			  <div class="col-md-3 noPad labHeight">Help Text</div>
			  <div class="col-md-6"><input type="text" name="labelinfo" id="labelinfo" value="${customFormPopDetails.labelinfo}" class="field-label form-control input-sm" > </div>
			</div>
			
			<!-- <div class="col-sm-12 noPad mt clearfix">
			  <div class="col-md-3 noPad labHeight">Dependent Fields</div>
			  <div class="col-md-6 dropdown-select"><select name="slDependedntField" class="field-label form-control input-sm" id="dependentFieldDiv"> 
			  </select>
			  </div>
			</div>
			
			<div class="col-sm-12 noPad mt clearfix" id="customDependentFieldDiv">
			  <div class="col-md-3 noPad labHeight">Custom Dependent Fields</div>
			  <div class="col-md-6"><select name="customDependentField" class="field-label form-control input-sm" id="customDependentField"> </select></div>
			</div> -->
			
			</div>
		
		
		<div class="immiPopFoot">
            <input value="Add" class="" name="save" id="saveNewField"  type="button" onclick="saveAddFormField('${customFormPopDetails.id}');">
            <input value="Cancel" class="popCan" name = "cancel" type="button">
          </div>	
          </form>
          </div>	
	</c:if>
		<!-- </div> -->

<!--   Popup Code  -->


<c:if test="${not empty type and type eq 'summary' and type ne 'SourceleadSummary'}">
		
		<div class="cusFielPopup cusFiel-popup " id="popUpDataCustom">
		<input name="id" id="customFieldId" value="${customFormPopDetails.id}" type="hidden"/>
		<div class="popupHead">
			<h2>Summary </h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
	
		<div class="popupMid clearfix">

			<div class="col-sm-12 noPad mt clearfix">
			<c:if test="${!empty SourceleadForm_Sections}">
				<c:if test="${not empty disableFields}">
					<input type="hidden" id="addFieldSectionHidden" name="sectionId">
				</c:if>
			</c:if>
			<c:if test="${!empty SourceleadForm_Sections_pop}">
				<c:forEach var="section" items="${SourceleadForm_Sections_pop}" varStatus="sectionStatus">
				 	 <c:if test = "${section.id == customFormPopDetails.sectionId}">
				  	  <div class="col-md-4 noPad clearfix"><b>Section Name </b><span class="pull-right">:</span></div>
					  <div class="col-md-8"><b>${section.sectionName}</b></div>
				 	 </c:if>
				 </c:forEach>
			</c:if>
			</div>
				 <div class="col-sm-12 noPad mt clearfix">
				  	  <div class="col-md-4 noPad clearfix">Label <span class="pull-right">:</span></div>
					  <div class="col-md-8">${customFormPopDetails.labelName}</div>
				</div>
			  
			  	<div class="col-sm-12 noPad mt clearfix">
				  	  <div class="col-md-4 noPad clearfix">Is Required? <span class="pull-right">:</span></div>
					  <div class="col-md-8">${customFormPopDetails.isRequired == '1'?'Yes':'No'}</div>
				</div>
				
				<div class="col-sm-12 noPad mt clearfix">
				  <div class="col-md-4 noPad clearfix">Validation Range <span class="pull-right">:</span></div>
				  <div class="col-md-8 dropdown-select"> 
				  		${customFormPopDetails.validationRange == 'Length 2-250' ? 'Length 2-250':''}       		
						${customFormPopDetails.validationRange == 'Length 2-500' ? 'Length 2-500':''}       	  
				  		${customFormPopDetails.validationRange == 'Length 500 Above' ? 'Length 500 and Above':''}
				  </div>
				</div>

				<div class="col-sm-12 noPad mt clearfix">
				  <div class="col-md-4 noPad clearfix">Validation Type <span class="pull-right">:</span></div>
				  <div class="col-md-8 dropdown-select">
						  	${customFormPopDetails.validationType == "Alphabets" ? 'Alphabet':''}
						  	${customFormPopDetails.validationType == "Numbers" ? 'Number':''}
						  	${customFormPopDetails.validationType == "Aplhanumeric" ? 'Alphanumeric':''}
				  </div>
				</div>
			  
				<div class="clearfix"></div>
				<div  id="dateFieldDiv" style="display: none">
					<div class="col-sm-12 noPad mt clearfix">
					   <div class="col-md-4 noPad clearfix">Min Date <span class="pull-right">:</span></div>
						<div class="col-md-8 lblHeight">${customFormPopDetails.minDate}</div>
					</div>
					
					<div class="col-sm-12 noPad mt clearfix">
						   <div class="col-md-4 noPad clearfix">Max Date <span class="pull-right">:</span></div>
							<div class="col-md-8 lblHeight">${customFormPopDetails.maxDate}</div>
					</div>
			  </div>

            <div  id="selectFieldDiv" style="display: none">
				<div class="col-sm-12 noPad mt clearfix">
					   <div class="col-md-4 noPad clearfix"><p>Options <span class="pull-right">:</span></p></div>
						<c:if test="${not empty customFormPopDetails.optionData}">
							<div class="col-md-8">
							<c:forEach var="options" items="${customFormPopDetails.optionData}" varStatus="optionStatus">
								<input name="optionData[${optionStatus.index}].id" value="${options.id}" id ="selectOptionId" type="hidden">
									${options.option}<br>
							</c:forEach>
							 </div>	
						</c:if>
						<%-- <c:if test="${empty customFormPopDetails.optionData}">
							<div class="col-md-6"><input name="optionData[0].option" field-name="Data" value="" id ="selectOptionId" class="field-label form-control input-sm" type="text" data-validation="required"> </div>
						</c:if> --%>
				</div>
				
				<input type="hidden" id="optionCounter" value="${fn:length(customFormPopDetails.optionData)}"/>
				<div id="optionsDiv"></div>	
				 <div class="col-sm-12 noPad mt clearfix">
				  <div class="col-md-4 noPad clearfix">&nbsp;</div>
				 <!--  <div class="col-md-6" id="addOptionDiv"><a href="#" onclick="addOption();"><p>Add Option</p></a></div> -->
				</div>			  
			</div>
			
			<div class="col-sm-12 noPad mt clearfix" id="placeHolderDiv">
			  <div class="col-md-4 noPad clearfix">PlaceHolder <span class="pull-right">:</span></div>
			  <div class="col-md-8">${customFormPopDetails.placeHolder}</div>
			</div>
			
			<div class="col-sm-12 noPad mt clearfix">
			  <div class="col-md-4 noPad clearfix">Status <span class="pull-right">:</span></div>
			  <div class="col-md-8 dropdown-select">${customFormPopDetails.status == 1 ? 'Active':''}  ${customFormPopDetails.status == 0 ? 'In-Active':''}
			  </div>
			</div>
			
			<div class="col-sm-12 noPad mt clearfix" id="placeHolderDiv">
			  <div class="col-md-4 noPad clearfix">Description <span class="pull-right">:</span></div>
			  <div class="col-md-8">${customFormPopDetails.fieldDescription}</div>
			</div>
			
			<div class="col-sm-12 noPad mt clearfix" id="labelInfoDiv">
			  <div class="col-md-4 noPad clearfix">Help Text <span class="pull-right">:</span></div>
			  <div class="col-md-8">${customFormPopDetails.labelinfo}</div>
			</div>
			
		<%-- 	<div class="col-sm-12 noPad mt clearfix" id="labelInfoDiv">
			  <div class="col-md-4 noPad clearfix">Preview <span class="pull-right">:</span></div>
			  <div class="col-md-8">
			  	<div class="preImg">
				  <img src="${customFormPopDetails.imagePathUrl}" alt=""/>
				  <input type="hidden" name="fieldType" value="${customFormPopDetails.fieldType}">
				  </div>
			  </div>
			</div> --%>			
			
			<!-- <div class="col-sm-12 noPad mt clearfix">
			  <div class="col-md-4 noPad">Dependent Fields</div>
			  <div class="col-md-6 dropdown-select"><select name="slDependedntField" class="field-label form-control input-sm" id="dependentFieldDiv"> 
			  </select>
			  </div>
			</div>
			
			<div class="col-sm-12 noPad mt clearfix" id="customDependentFieldDiv">
			  <div class="col-md-4 noPad">Custom Dependent Fields</div>
			  <div class="col-md-6"><select name="customDependentField" class="field-label form-control input-sm" id="customDependentField"> </select></div>
			</div> -->
			
			</div>
		
		<div class="immiPopFoot">
            <input value="Close" class="popCan" name = "close" type="button">
          </div>	
      </div>
</c:if>
<c:if test="${not empty type and type eq 'SourceleadSummary'}">
<div class="cusFielPopup cusFiel-popup " id="popUpDataCustom">
			<div class="popupHead">
				<h2>Summary </h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
			</div>
		<div class="popupMid clearfix">
		
			<div class="col-sm-12 noPad mt clearfix">
				  	  <div class="col-md-4 noPad clearfix"><b>Section Name </b><span class="pull-right">:</span></div>
					  <div class="col-md-8"><b>${SourceleadFormViewData.sectionName}</b></div>
			</div>
			<c:forEach var="data" items="${SourceleadFormViewData.formFieldsList}">

			 <div class="col-sm-12 noPad mt clearfix">
			  	  <div class="col-md-4 noPad clearfix">Label <span class="pull-right">:</span></div>
				  <div class="col-md-8">${data.labelName}</div>
			</div>
		  
		  	<div class="col-sm-12 noPad mt clearfix">
			  	  <div class="col-md-4 noPad clearfix">Is Required? <span class="pull-right">:</span></div>
				  <div class="col-md-8">${data.isFieldRequired == '1'?'Yes':'No'}</div>
			</div>
			
			<div class="clearfix"></div>
			
			<div class="col-sm-12 noPad mt clearfix" id="placeHolderDiv">
			  <div class="col-md-4 noPad clearfix">PlaceHolder <span class="pull-right">:</span></div>
			  <div class="col-md-8">${data.placeHolder}</div>
			</div>
			
			<div class="col-sm-12 noPad mt clearfix">
			  <div class="col-md-4 noPad clearfix">Status <span class="pull-right">:</span></div>
			  <div class="col-md-8 dropdown-select">${data.fieldstatus == '0'? 'InActive':'Active'}
			  </div>
			</div>
			
			<div class="col-sm-12 noPad mt clearfix" id="placeHolderDiv">
			  <div class="col-md-4 noPad clearfix">Description <span class="pull-right">:</span></div>
			  <div class="col-md-8">${data.fieldDesc}</div>
			</div>
			
			<div class="col-sm-12 noPad mt clearfix" id="labelInfoDiv">
			  <div class="col-md-4 noPad clearfix">Help Text <span class="pull-right">:</span></div>
			  <div class="col-md-8">${data.labelInfo}</div>
			</div>
			
			<div class="col-sm-12 noPad mt clearfix" id="labelInfoDiv">
			  <div class="col-md-4 noPad clearfix">Preview <span class="pull-right">:</span></div>
			  <div class="col-md-8">
			  	<div class="preImg">
				  <img src="${data.imagePathUrl}" alt=""/>
				  <input type="hidden" name="fieldType" value="${data.fieldType}">
				  </div>
			  </div>
			</div>		
			</c:forEach>
			</div>	
			<div class="immiPopFoot">
            <input value="Close" class="popCan" name = "close" type="button">
          </div>
</div>          		
</c:if>





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