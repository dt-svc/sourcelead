<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<section class="addImmiWrap">
	<div class="addImmiInnWrap">
		<div class="immiStepsHead clearfix">
			<h2>2. Employer Details</h2>
			<ul class="addImmiSteps">
				<li class="active">1</li>
				<li class="active">2</li>
				<li>3</li>
				<li>4</li>
				<li>5</li>
				<li>6</li>
				<li>7</li>
				<b></b>
				<div class="clearfix"></div>
			</ul>
		</div>
		
		<div id="employmentParentDiv" class="EmplWrap clearfix">
		
			<!-- <div class="EmplWrapCta">
				<div class="immigration-head clearfix">
					<h4 class="pull-left">Employer 1</h4>
					<button type="button" class="btn btn-default pull-right mt2"><i class="fa fa-plus paddingRight"></i>Add Employer</button>
				</div>
				<div class="clearfix">
					<div class="mt clearfix"></div>
					<div class="col-md-4 minHeight80">
					     <label>Employer Full Name</label>
						<input type="text" class="form-control" value="" placeholder="Employer Full Name">
					</div>
					
					<div class="col-md-4 minHeight80">
							<label>Address</label>
							<input type="text" class="form-control" value="" placeholder="Address">
					</div>
					<div class="col-md-4 minHeight80">
						<label>Tax Id</label>
						<input type="text" class="form-control" value="" placeholder="Tax Id">
					</div>
					
					<div class="col-md-4 minHeight80">
						<label>Job Title</label>
						<input type="text" class="form-control" value="" placeholder="Job Title">
					</div>
					
					
					<div class="col-md-4 minHeight80">
						<label>Employment Type</label>
						<div class="dropdown-select">
						<select class="form-control">
							<option>C2C</option>
							<option>W2</option>
							<option>Full Time</option>
							<option>Part Time</option>
							<option>1099</option>
							<option>C2C-1099</option>
							<option>C2H</option>
							<option>C2H-1099 &amp; Others </option>
						</select>
						</div>
					</div>

					<div class="col-md-4 minHeight80">
					<label>&nbsp;</label>
					<div class="checkBox">
					<input type="checkbox" value="">Is Current Job ?
					</div>
					</div>
					
					<div class="col-md-4 minHeight80">
							<div class="row">
							<div class="col-md-6 padding_right">
								<label>From</label>
									<input type="text" class="form-control calendar" value="" placeholder="From">
							</div>
							<div class="col-md-6 paddingLeft">
								<label>To</label>
									<input type="text" class="form-control calendar" value="" placeholder="To">
								</div>
							</div>
					</div>
					</div>
				</div>
			<div class="EmplWrapCta">
				<div class="immigration-head clearfix">
					<h4 class="pull-left">Employer 2</h4>
					<button type="button" class="btn btn-default pull-right mt2"><i class="fa fa-plus paddingRight"></i>Add Employer</button>
				</div>
				<div class="clearfix">
					<div class="mt clearfix"></div>
					<div class="col-md-4 minHeight80">
					     <label>Employer Full Name</label>
						<input type="text" class="form-control" value="" placeholder="Employer Full Name">
					</div>
					
					<div class="col-md-4 minHeight80">
							<label>Address</label>
							<input type="text" class="form-control" value="" placeholder="Address">
					</div>
					<div class="col-md-4 minHeight80">
						<label>Tax Id</label>
						<input type="text" class="form-control" value="" placeholder="Tax Id">
					</div>
					<div class="col-md-4 minHeight80">
						<label>Job Title</label>
						<input type="text" class="form-control" value="" placeholder="Job Title">
					</div>
<div class="col-md-4 minHeight80">
						<label>Employment Type</label>
						<div class="dropdown-select">
						<select class="form-control">
							<option>C2C</option>
							<option>W2</option>
							<option>Full Time</option>
							<option>Part Time</option>
							<option>1099</option>
							<option>C2C-1099</option>
							<option>C2H</option>
							<option>C2H-1099 &amp; Others </option>
						</select>
						</div>
					</div>
					<div class="col-md-4 minHeight80">
					<label>&nbsp;</label>
					<div class="checkBox">
					<input type="checkbox" value="">Is Current Job ?
					</div>
					</div>
					
					<div class="col-md-4 minHeight80">
							<div class="row">
							<div class="col-md-6 padding_right">
								<label>From</label>
									<input type="text" class="form-control calendar" value="" placeholder="From">
							</div>
							<div class="col-md-6 paddingLeft">
								<label>To</label>
									<input type="text" class="form-control calendar" value="" placeholder="To">
								</div>
							</div>
					</div>
					</div>
			</div> -->       	      
     	      
      	</div>			
		
		<div class="EmplWrap clearfix">
			<div class="clearfix">
				<div class="immigration-head clearfix">
					<h4 class="pull-left">Servered in Military</h4>
				</div>
				<div class="clearfix">
					<div class="mt clearfix"></div>
	
				<div class="col-md-4 minHeight80">
							<label>Country</label>
							<div class="dropdown-select">
								<select class="chosen-select form-control" data-placeholder="Select Country">
								<option>--Select Country--</option>
									<option>Country-1</option>
									<option>Country-2</option>
									<option>Country-3</option>
								</select>
							</div>
					</div>
					<div class="col-md-4 minHeight80">
							<div class="row">
							<div class="col-md-6 padding_right">
								<label>From</label>
									<input class="form-control calendar" value="" placeholder="From" type="text">
							</div>
							<div class="col-md-6 paddingLeft">
								<label>To</label>
									<input class="form-control calendar" value="" placeholder="To" type="text">
								</div>
							</div>
					</div>
					<div class="col-md-4 minHeight80">
						<label>Branch</label>
						<input type="text" class="form-control" value="" id="idValue" onClick="refreshGoogleApiLocation('idValue')">
					</div>
					<div class="col-md-4 minHeight80">
						<label>Rank</label>
						<input type="text" class="form-control" value="" placeholder="Rank">
					</div>

				</div>
			</div>       	      
      	</div>
		
		<div class="addImmiFoot">
			<a href="javascript:void(0)" onclick="immigration.savePageOneContinue('petitionDetails')" class="pull-left">Prev</a>
			<a href="javascript:void(0)" onclick="immigration.savePageOneContinue('three')" class="nextBtn">Save &amp; Continue</a>
			<a href="javascript:void(0)" class="nextBtn">Save</a>
			<a href="javascript:void(0)">Skip</a>
			<div class="clearfix"></div>
		</div>
	</div>
</section> 
<script type="text/javascript">

	function showEndDatePicker(id){
		var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		var finalDateFormat = getJSDateFormat(locationDate);  
		$('#endDate'+id).datepicker({
			dateFormat: finalDateFormat,
		    changeMonth: true,
			changeYear: true, 
		 	maxDate: '0'
		}).datepicker("show");
	}
	
	function showStartDatePicker(id){
		var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		var finalDateFormat = getJSDateFormat(locationDate);  
		$('#startDate'+id).datepicker({
			dateFormat: finalDateFormat,
		    changeMonth: true,
			changeYear: true, 
		 	maxDate: '0'
		}).datepicker("show");
	}
	
	 $(document).ready(function () {
		$.ajax({
		       url: sourceleadUrl+immiModulePath+"/getPetitionTemplateEmployers",
		       type : "GET",
		       success: function(data) {
					for(var i=0;i<data.length;i++){
						if(i>0){
							$("#employmentParentDiv").append(addEmployment(i+1,data[i],'dynamic'));	
						}else{
							$("#employmentParentDiv").append(addEmployment(i+1,data[i],'default'));
						}
					}		    	   
		       }
		});
	});
	
	function addEmployment(empCount,dataValues,dynamicStatus){
		var dynamicStatusValue = "";
		if(dynamicStatus == 'default'){
			dynamicStatusValue = '<button type="button" onclick="addEmploymentDynamic('+empCount+')" class="btn btn-default pull-right mt2"><i class="fa fa-plus paddingRight"></i>Add Employer</button>';
		}else if(dynamicStatus == 'dynamic'){
			dynamicStatusValue = '<button type="button" onclick="removeEmploymentDynamic('+empCount+')" class="btn btn-default pull-right mt2"><i class="fa fa-remove text-danger"></i>Remove Employer</button>';
		}
		var name = (dataValues.name == null) ? "" : dataValues.name;
		var address = (dataValues.address == null) ? "" : dataValues.address;
		var taxId = (dataValues.taxId == null) ? "" : dataValues.taxId;
		var jobTitle = (dataValues.jobTitle == null) ? "" : dataValues.jobTitle;
		var employmentType = (dataValues.employmentType == null) ? "" : dataValues.employmentType;
		if(dataValues.isCurrentPosition == 'Y'){
			$("#isCurrentPosition"+empCount).prop('checked', true);
		}else if(dataValues.isCurrentPosition == 'N'){
			$("#isCurrentPosition"+empCount).prop('checked', false);
		}
		//var isCurrentPosition = (dataValues.isCurrentPosition == null) ? "" : dataValues.isCurrentPosition;
		var startDate = (dataValues.startDate == null) ? "" : dataValues.startDate;
		var endDate = (dataValues.endDate == null) ? "" : dataValues.endDate;
		divValue = "";
		divValue = divValue + '<div id="employeeChildDiv'+empCount+'" class="EmplWrapCta"><div class="immigration-head clearfix"><h4 class="pull-left">Employer '+empCount+'</h4>'+dynamicStatusValue+'</div>';
		divValue = divValue + '<div class="clearfix"><div class="mt clearfix"></div><div class="col-md-4 minHeight80"><label>Employer Full Name</label><input type="text" class="form-control" value="'+name+'" placeholder="Employer Full Name"></div>';
		divValue = divValue + '<div class="col-md-4 minHeight80"><label>Address</label><input type="text" class="form-control" value="'+address+'" id="address'+empCount+'" placeholder="Address"></div>';
		divValue = divValue + '<div class="col-md-4 minHeight80"><label>Tax Id</label><input type="text" class="form-control" value="'+taxId+'" placeholder="Tax Id"></div>';
		divValue = divValue + '<div class="col-md-4 minHeight80"><label>Job Title</label><input type="text" class="form-control" value="'+jobTitle+'" placeholder="Job Title"></div>';
		divValue = divValue + '<div class="col-md-4 minHeight80"><label>Employment Type</label><div class="dropdown-select"><select id="employmentTypeId'+empCount+'" class="form-control" value="'+employmentType+'">';
		getEmploymentTypeList(empCount);
		divValue = divValue + ' </select></div></div>';
		divValue = divValue + '<div class="col-md-4 minHeight80"><label>&nbsp;</label><div class="checkBox"><input type="checkbox" id="isCurrentPosition'+empCount+'" value="">Is Current Job ?</div></div>';
		divValue = divValue + '<div class="col-md-4 minHeight80"><div class="row"><div class="col-md-6 padding_right"><label>From</label><input type="text" id="startDate'+empCount+'" class="form-control calendar" onclick="showStartDatePicker('+empCount+');" value="'+startDate+'" placeholder="From"></div>';
		divValue = divValue + '<div class="col-md-6 paddingLeft"><label>To</label><input type="text" id="endDate'+empCount+'" class="form-control calendar" value="'+endDate+'" onclick="showEndDatePicker('+empCount+');" placeholder="To"></div></div></div></div></div>';
		return divValue;
	}

	var empCountValue = 1;
	
	function addEmploymentDynamic(){
		$("#employmentParentDiv").append(addEmployment(++empCountValue,'dynamic'));
	}
	function removeEmploymentDynamic(empCount){
		$("#employeeChildDiv"+empCount).remove();
		empCountValue--;
	}
	/* function callGoogleApiMethod2(id){
		window.onload = function () {
			initLocationAuto(id);
		 };
	} */
	window.onload = function () {
		initLocationAuto('idValue');
	 };
	 
	 function getEmploymentTypeList(id){
			$.ajax({
			   	type: "GET",
			    url: sourceleadUrl+immiModulePath+"/getEmploymentTypeList?${_csrf.parameterName}=${_csrf.token}",	    	   
			    success: function(json) {
			    	 var result = "";
			    	 $('#employmentTypeId'+id)
	                 .append($("<option></option>")
	                            .attr("value","")
	                            .attr("selected","selected")
	                            .text("--Select Option--"));
		        	 for (var i=0; i<json.length; i++){
		        		 var report = json[i];
		        		 $('#employmentTypeId'+id)
		                 .append($("<option></option>")
		                            .attr("value",report.id)
		                            .text(report.name));
		        		 $('#employmentTypeId'+id).trigger("chosen:updated");
		        	 }
			    }
			});
	 }
	/* $( function() {
		var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		// written common method in slead-common.js for date formats start
		var finalDateFormat = getJSDateFormat(locationDate);
		$("#startDate").datepicker();
	}); */
</script>
