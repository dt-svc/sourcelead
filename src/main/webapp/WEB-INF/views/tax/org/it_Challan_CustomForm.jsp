<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="popMask"></div>
<div class="cusFielPopup" >
	<div class="popupHead" >
		<h2>Edit Row</h2>
		<a href="javascript:void(0)" class="fa fa-close popCan" onclick="$('.popMask').hide(); $('.cusFielPopup').hide();"></a>
	</div>
	<div class="clearfix">
		<!-- <div class="col-md-8 col-md-offset-2"> -->
		<div class="col-md-3 labHeight">Field Type:</div>
			<div class="col-md-9">
			<select class="chosen-select form-control"  id = "fieldtype" onchange = "getSelectedFieldDetails();">
				<option value="textField">Text Field</option>
				<option value="dateField">Date Field</option>
				<option value="dropDown">Drop Down</option>
				<option value="checkBox">Check Box</option>
				<option value="radioButton">Radio Button</option>
			</select>
			</div>
		<!-- </div> -->
	</div>

	<div class="popupMid clearfix">
   <form action="" id="itChallanSectionItemForm">
   
		<div class="col-sm-12 noPad mt clearfix" id = "labelName">
			<div class="col-md-3 noPad labHeight">Label Name:</div>
			<div class="col-md-6">
				<input class="field-label form-control input-sm" type="text" id = "labelName" name = "labelName"   >
			</div>
		</div>
		
		<div class="col-sm-12 noPad mt clearfix">
			<div class="col-md-3 noPad labHeight">&nbsp;</div>
		<div class="col-md-3 lblHeight"> Required? <input class="toggle-required" id = "isRequired" type="checkbox">
		</div>
		</div>
		
            <div  id="selectFieldDiv" style="display:none; ">
				<div class="col-sm-12 noPad mt clearfix">
					   <div class="col-md-3 noPad labHeight"><p>Options</p></div>
						<c:if test="${not empty customFormPopDetails.optionData}">
							<div class="col-md-6">
							<c:forEach var="options" items="${customFormPopDetails.optionData}" varStatus="optionStatus">
								<input name="optionData[${optionStatus.index}].id" value="${options.id}" id ="selectOptionId" type="hidden">
								<input name="optionData[${optionStatus.index}].labelName" value="${options.option}" id ="selectOptionId" field-name="Data" class="field-label form-control input-sm mb" type="text" data-validation="required">
							</c:forEach>
							 </div>	
						</c:if>
						<c:if test="${empty customFormPopDetails.optionData}">
							<div class="col-md-6"><input name="optionData[0].labelName" field-name="Data" value="" id ="selectOptionId" class="field-label form-control input-sm" type="text" data-validation="required"> </div>
						</c:if>
						<div class="col-md-3 lblHeight"><span>&nbsp;</span>
					</div>
				</div>
				
				<input type="hidden" id="optionCounter" value=""/>
				<div id="optionsDiv"></div>	
				 <div class="col-sm-12 noPad mt clearfix">
				  <div class="col-md-3 noPad labHeight">&nbsp;</div>
				  <div class="col-md-6" id="addOptionDiv"><a href="#" onclick="addOption();"><p>Add Option</p></a></div>
				</div>			  
			</div>
		
			<div class="col-sm-12 noPad mt clearfix" id = "validateRangeDiv">
			<div class="col-md-3 noPad labHeight">Validation Range:</div>
			<div class="col-md-6 dropdown-select">
			<select name="validationRange" data-placeholder="Select Validation Range" class="form-control input-sm"  field-name="dataValidationRange" data-validation="required">
						  	<option value="" selected>Select Field Length Validation</option>
						  	<option>Length 2-250</option>
						  	<option >Length 2-500</option>
						  	<option>Length 500 and Above</option>
					  </select>
			</div>
			</div>
	
		    <div class="col-sm-12 noPad mt clearfix" id="validateTypeDiv"> 
			<div class="col-md-3 noPad labHeight">Validation Type:</div>
			<div class="col-md-6  dropdown-select">
			<select name="validationRange" data-placeholder="Select Validation Range" class="form-control input-sm"  field-name="dataValidationType" data-validation="required">
						  <option value="" selected>Select Field Type Validation</option>
						  	<option>Alphabet</option>
						  	<option>Number</option>
						  	<option>Alphanumeric</option>
					  </select>
			</div>
		    </div>
	
		
		<div class="col-sm-12 noPad mt clearfix">
			<div class="col-md-3 noPad labHeight">Discription:</div>
			<div class="col-md-6">
				<input class="field-label form-control input-sm" type="text" is = "description " name = "discription">
			</div>
		</div>
		<div class="col-sm-12 noPad mt clearfix">
			<div class="col-md-3 noPad labHeight">Placeholder Text:</div>
			<div class="col-md-6">
				<input class="field-label form-control input-sm" type="text" name = "placeHolder" id = "placeHolder">
			</div>
		</div>
		
		<div class="col-sm-12 noPad mt clearfix">
			  <div class="col-md-3 noPad clearfix">Status <span class="pull-right">:</span></div>
			  <div class="col-md-6 dropdown-select">
              <select name="validationRange" data-placeholder="Select status " class="form-control input-sm" id="status" field-name="status" data-validation="required">
						  	<option>Active</option>
						  	<option>InActive</option>
					  </select>

			  </div>
			</div>
		
	
		<div class="col-sm-12 noPad mt clearfix">
			<div class="col-md-3 noPad labHeight">Help text:</div>
			<div class="col-md-6">
				<textarea class="form-control input-sm"></textarea>
			</div>
		</div>
		    </form>
		
	    </div>

	<div class="immiPopFoot">
		<input value="Add" class="" onclick="addITChallanSectionItem()" id="addITChallanSectionItemBtn" type="button"> <input
			value="Cancel" class="popCan" type="button" id = "closePopup" onclick="$('.popMask').hide(); $('.cusFielPopup').hide();">
	</div>

</div>







 <script>
function getSelectedFieldDetails(){
	
	var optionCounter = $("#optionCounter").val();
	for(var i=optionCounter ; i >=0 ; i--){
		$("#optionDiv"+i).remove();
	}
	 var field = $('#fieldtype').val();
	 if(field == 'textField'){
		 $('#validateRangeDiv').show();
		 $('#validateTypeDiv').show();
	 }else{
		 $('#validateRangeDiv').hide();
		 $('#validateTypeDiv').hide();
		 
		 $('#validateRangeDiv').removeAttr("data-validation","required");
		 $('#validateRangeDiv').attr('data-validation-optional');
		 
		 $('#validateTypeDiv').removeAttr("data-validation","required");
		 $('#validateTypeDiv').attr('data-validation-optional');
		 
	 }
	 console.log(field);
	 if(field == 'dropDown' || field == 'checkBox' || field == 'radioButton'){
		 $('#selectFieldDiv').show();
		 $('#textBoxDiv').hide();
		 if(field == 'dropDown'){
			 $("#selectFieldDiv p:contains('Choices')").html("Options");
			 $("#addOptionDiv p:contains('Add Choice')").html("Add Option");
			 $('#placeHolder').show();
		 }else if(field == 'checkBox' || field == 'radioButton'){
			 $("#selectFieldDiv p:contains('Options')").html("Choices");
			 $("#addOptionDiv p:contains('Add Option')").html("Add Choice");
			 $('#placeHolder').hide();
		 }
		 $('#selectOptionId').removeAttr('data-validation-optional');
		 $('#selectOptionId').attr("data-validation","required");
		 
	 }else{
		 $('#selectFieldDiv').hide();
		 $('#textBoxDiv').show();
		 $('#selectFieldDiv').find('p.jquery_form_error_message').remove(); 
		 $('#selectOptionId').removeAttr('data-validation');
		 $('#selectOptionId').attr("data-validation-optional","true");
	 }
	 
	 if(field == 'dateField'){
		 $('#selectFieldDiv').hide();
		 $('#dateFieldDiv').show();
		 $('#placeHolder').show();
	 }else{
		 $('#dateFieldDiv').hide();
		 $('#dateFieldDiv').find('p.jquery_form_error_message').remove(); 
	 }
	 
	 
	 
 }
 </script>
 
 
 <script>
  function addOption(){
	  var optionCounter = $("#optionCounter").val();
	  if(optionCounter == 0){
		  optionCounter = +optionCounter + 1;
	  }
	  var divText = '<div id="optionDiv'+optionCounter+'" class="col-sm-12 noPad mt clearfix">'+
	                 '<div class="col-md-3 noPad labHeight">&nbsp;</div>'+
	                   '<div class="col-md-6"><input id = "selectOptionId" placeholder="" name="optionData['+optionCounter+'].labelName" class="field-label form-control input-sm" type="text"> </div>'+
	                   '<div class="col-md-3 lblHeight"><span class="OpRemove"><i onclick = "removeOption('+optionCounter+')" class="fa fa-close"></i></span>'+
		             '</div>'+
		             '</div>';
	  
      if(optionCounter >1){
    	  $("#optionCounter").val(parseInt(optionCounter)+1);
    	  $('#optionsDiv').append(divText);
      }else{
    	  $("#optionCounter").val(parseInt(optionCounter)+1);
    	  $('#optionsDiv').html('');
    	  $('#optionsDiv').append(divText);
      }
      
	}
    function removeOption(id)
	{
    	$("#optionDiv"+id).remove();
	}
 </script>
 

