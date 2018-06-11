<div class="popMask"></div>
<div class="cusFielPopup" id = "hi">
	<div class="popupHead" >
		<h2>Edit Row</h2>
		<a href="javascript:void(0)" class="fa fa-close popCan" onclick="$('.popMask').hide(); $('.cusFielPopup').hide();"></a>
	</div>
	<div class="popupMid clearfix">
		<div class="col-md-6 noPad">
		<div class="col-md-3 noPad labHeight">Field Type:</div>
			<select class="chosen-select form-control"  id = fieldtype onchange = "getSelectedFieldDetails();">
				<option>Choose Field</option>
				<option>Text field</option>
				<option>Date field</option>
				<option>Drop Down</option>
				<option>Check box</option>
				<option>Radio button</option>
			</select>
		</div>
	</div>

	<div class="popupMid clearfix">
   <form action="" id="itChallanSectionItemForm">
   
		<div class="col-sm-12 noPad mt clearfix">
			<div class="col-md-3 noPad labHeight">Label Name:</div>
			<div class="col-md-6">
				<input class="field-label form-control input-sm" type="text" name = "labelName" id = "labelName" >
			</div>
			
		</div>
		<div class="col-md-3 lblHeight"> Required? <input class="toggle-required" type="checkbox">
		</div>
		
	
		<div class="col-md-3 lblHeight"> Required? <input class="toggle-required" type="radiobutton">
		
		
		
		</div>
			<div class="col-sm-12 noPad mt clearfix">
			<div class="col-md-3 noPad labHeight">Validation Range:</div>
			<div class="col-md-6 dropdown-select">
			<select name="validationRange" data-placeholder="Select Validation Range" class="form-control input-sm" id="validateRangeDiv" field-name="validation Range" data-validation="required">
						  	<option value="" selected>Select Field Length Validation</option>
						  	<option>Length 2-250</option>
						  	<option >Length 2-500</option>
						  	<option>Length 500 and Above</option>
					  </select>
			</div>
		    </div>
		
		   	<div class="col-sm-12 noPad mt clearfix">
			<div class="col-md-3 noPad labHeight">Validation Type:</div>
			<div class="col-md-6 dropdown-select">
			<select name="validationRange" data-placeholder="Select Validation Range" class="form-control input-sm" id="validateTypeDiv" field-name="validation Range" data-validation="required">
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
				<input class="field-label form-control input-sm" type="text">
			</div>
		</div>
		<div class="col-sm-12 noPad mt clearfix">
			<div class="col-md-3 noPad labHeight">Placeholder Text:</div>
			<div class="col-md-6">
				<input class="field-label form-control input-sm" type="text" name = "placeHolder" id = "placeHolder">
			</div>
		</div>
		
		<div class="col-sm-12 noPad mt clearfix">
			  <div class="col-md-2 noPad clearfix">Status <span class="pull-right">:</span></div>
			  <div class="col-md-8 dropdown-select">

              <select name="validationRange" data-placeholder="Select status " class="form-control input-sm" id="status" field-name="status" data-validation="required">
						  	<option>Active</option>
						  	<option>InActive</option>
					  </select>

			  </div>
			</div>
		
		
		<div class="col-sm-12 noPad mt clearfix">
			<div class="col-md-3 noPad labHeight"> Min Date:</div>
			<div class="col-md-6">
				<input class="field-label form-control input-sm" type="text" name = "mindate "id ="mindate">
			</div>
		</div>
		
         <div class="col-sm-12 noPad mt clearfix">
			<div class="col-md-3 noPad labHeight">Max Date:</div>
			<div class="col-md-6">
				<input class="field-label form-control input-sm" type="text" name = "maxdate "id = "maxdate ">
			</div>
		</div>
		<div class="col-sm-12 noPad mt clearfix">
			<div class="col-md-3 noPad labHeight">Help text:</div>
			<div class="col-md-9">
				<textarea class="form-control input-sm"></textarea>
			</div>
		</div>
		    </form>
		</div>


	<div class="immiPopFoot">
		<input value="Add" class="" onclick="addITChallanSectionItem()" id="addITChallanSectionItemBtn type="button"> <input
			value="Cancel" class="popCan" type="button" id = "closePopup" onclick="$('.popMask').hide(); $('.cusFielPopup').hide();">
	</div>

</div>








 <script>
function getSelectedFieldDetails(){
	
	var optionCounter = $("#optionCounter").val();
	for(var i=optionCounter ; i >=0 ; i--){
		$("#optionDiv"+i).remove();
	}
	 var field = $('#fieldsDropDown').val();
	 if(field == 'TextBox'){
		 $('#validateRangeDiv').show();
		 $('#validateLengthDiv').show();
	 }else{
		 $('#validateRangeDiv').hide();
		 $('#validateLengthDiv').hide();
		 
		 $('#validationRange').removeAttr("data-validation","required");
		 $('#validationRange').attr('data-validation-optional');
		 
		 $('#validationType').removeAttr("data-validation","required");
		 $('#validationType').attr('data-validation-optional');
		 
	 }
	 if(field == 'selectBox' || field == 'CheckBox' || field == 'radioButton'){
		 $('#selectFieldDiv').show();
		 $('#textBoxDiv').hide();
		 if(field == 'selectBox'){
			 $("#selectFieldDiv p:contains('Choices')").html("Options");
			 $("#addOptionDiv p:contains('Add Choice')").html("Add Option");
			 $('#placeHolderDiv').show();
		 }else if(field == 'CheckBox' || field == 'radioButton'){
			 $("#selectFieldDiv p:contains('Options')").html("Choices");
			 $("#addOptionDiv p:contains('Add Option')").html("Add Choice");
			 $('#placeHolderDiv').hide();
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
	 
	 if(field == 'Date'){
		 $('#selectFieldDiv').hide();
		 $('#dateFieldDiv').show();
		 $('#placeHolderDiv').show();
	 }else{
		 $('#dateFieldDiv').hide();
		 $('#dateFieldDiv').find('p.jquery_form_error_message').remove(); 
	 }
	 
	 
	 
 }
 </script>
