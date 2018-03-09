<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<section class="addImmiWrap">
	<div class="addImmiInnWrap">
		<div class="immiStepsHead clearfix">
			<!--<h2>1. Petition detail</h2>-->
			<ul class="addImmiSteps">
				<li class="active">1</li>
				<li>2</li>
				<li>3</li>
				<li>4</li>
				<li>5</li>
				<li>6</li>
				<li>7</li>
				<b></b>
				<div class="clearfix"></div>
			</ul>
		</div>
		
		<div class="addImmiContent clearfix">

          <div class="col-md-12 col-lg-12 myForm margin-top0">
            <h4 class="col-md-12 mb clearfix">1. Petition detail</h4>
            <div class="clearfix"></div>         
         	<div class="form-group">
              <label class="col-md-4">Petition type<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-8 dropdown-select">
                  <select class="form-control">
                    <option>--Select Petition--</option>
                    <option>H1B Filing (Initial Cap filing) or H1 Transfer</option>
                    <option>H1B Filing (Amendment)</option>
                    <option>H1B Filing (Extension)</option>
                    <option>Green Card</option>
                  </select>
              </div>
          </div> 

            <div class="form-group">
              <label class="col-md-4">Open date<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-8">
               <div class="input-group">
                <input type="text" id ="openDate" class="form-control calendar" name="" placeholder="Open Date">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('openDate');"></i></span>
              </div>
            </div>
            </div>
            
            <div class="form-group">
              <label class="col-md-4">Current USCIS Status</label>
              <div class="col-md-8 dropdown-select">
                <select class="form-control">
                    <option>--Select Petition--</option>
                    <option>H1B Filing (Initial Cap filing) or H1 Transfer</option>
                    <option>H1B Filing (Amendment)</option>
                    <option>H1B Filing (Extension)</option>
                    <option>Green Card</option>
                  </select>
              </div>
            </div>

            <div class="form-group">
              <label class="col-md-4">Date of arrival</label>
              <div class="col-md-8">
               <div class="input-group">
                <input type="text" id="arrivalDate" class="form-control calendar" name="" placeholder="Date of arrival">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('arrivalDate');"></i></span>
              </div>
              </div>
            </div>                        
            
            <div class="form-group">
          		<label class="col-md-4">Place of entry<span class="text-danger">&nbsp;*</span></label>
         			 <div class="col-md-8" id="locationField">
          			<div class="positionRelative">
          			<input class="form-control positionRelative" id="petitionerPlace" name="petitionerPlace" placeholder="Enter your Location"  type="text" field-name="Place Of Entry" data-validation="required"></input>
          				<i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('petitionerPlace');"></i>
         				 </div>
        			  </div>
       			 </div>
            
            
            <div class="form-group">
              <label class="col-md-4">Choose Template</label>
              <div class="col-md-8">
               <select class="chosen-select form-control" data-placeholder="Choose Template">
               	
               	<option value="" selected>--Choose Template--</option>
							<c:forEach var="item" items="${templateList}">
											<option value="${item.id}">${item.name}</option>
							</c:forEach>
               </select>
              </div>
            </div>
            
            <div class="clearfix"></div>
            <h4 class="col-md-12 mb">2. Attorney Details</h4>
            <div class="clearfix"></div>
							<div class="form-group">
								<label class="col-md-3"><span class="pull-left">Attorney Name</span><span
									class="text-danger pull-left" id="man1">*</span></label>
								<div class="col-md-9 chooRadius" id="errorAttorney">
								<div class="input-group col-md-10 pull-left chooRadius">
									<select class="chosen-select form-control" name="attorneys"
										id="attorneys" placeholder="Select Attorney" field-name="Attorney Name" data-validation="required"
										onchange="getAttorneyContactList();removeValidationAttorney();">
									</select>
								 <span class="input-group-addon cursorPointer bg-blue" onclick="getAttorneysByOrgLocation();addCandiadateEmptyAttorneyContact();"><i class="fa fa-refresh "></i></span>
									
								</div>
								<span class="col-md-2 noPad clearfix">
							<a href="<c:url value="/app/addVendor" />" target="_blank"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a> 
							</span>
							</div>
							</div>

							<div class="form-group">
								<label class="col-md-3"><span class="pull-left">Attorney Contact</span><span class="text-danger pull-left" id="man2">*</span></label>
								<div class="col-md-9 chooRadius" id="errorAttorneyContact">
								<div class="input-group col-md-10 pull-left chooRadius">
									<select class="chosen-select form-control" name="attorneyContact" data-validation="required"
										id="attorneyContact" field-name="Attorney Contact"
										placeholder="Select Attorney Contact"
										onchange="removeValidationAttorneyContact();">
									</select>
                  <span class="input-group-addon cursorPointer bg-blue" onclick="getAttorneysByOrgLocation();addCandiadateEmptyAttorneyContact();"><i class="fa fa-refresh "></i></span>
			</div>		
								<span class="col-md-2 noPad clearfix" onclick="selectedAttorney();">
								<a href="javascript:void(0)"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a>
							 </span>			
										
								</div>
								
							</div>
            
           <div class="clearfix"></div>
            	<h4 class="col-md-12 mb margin-top0">3. Beneficiary  details</h4>
           <div class="clearfix"></div>

            <div class="form-group">
              <label class="col-md-4">Assocaite (Beneficiary)</label>
              <div class="col-md-8">
                <select class="form-control chosen-select">
                    <option>--Select Beneficiary--</option>
                    <option>Beneficiary-1</option>
                    <option>Beneficiary-2</option>
                    <option>Beneficiary-3</option>
                 </select>
              </div>
            </div>                                                                                                                                       
         <div class="form-group">
              <label class="col-md-4">First Name<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-8">
					<input type="text" class="form-control" name="" value="">
              </div>
          </div>
          <div class="form-group">
              <label class="col-md-4">Middle Name</label>
              <div class="col-md-8">
					<input type="text" class="form-control" name="" value="">
              </div>
          </div> 
          <div class="form-group">
              <label class="col-md-4">Last Name<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-8">
					<input type="text" class="form-control" name="" value="">
              </div>
          </div>  

          <div class="form-group">
              <label class="col-md-4">Email<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-8">
					<input type="text" class="form-control" name="" value="">
              </div>
          </div>
                      	
          <div class="form-group">
              <label class="col-md-4">Mobile</label>
              <div class="col-md-8">
					<input type="text" class="form-control" name="" value="">
              </div>
          </div>
          
           <div class="form-group">
              <label class="col-md-4">Alternative Mobile</label>
              <div class="col-md-8">
					<input type="text" class="form-control" name="" value="">
              </div>
          </div> 
          
           <div class="form-group">
              <label class="col-md-4">DOB<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-8">
					<div class="input-group">
                <input type="text" class="form-control calendar" name="birthDate" id="birthDate" placeholder="Date Of Birth">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onClick="clearDateValue('birthDate');"></i></span>
              </div>
              </div>
          </div> 
           <div class="form-group">
              <label class="col-md-4">Place of Birth</label>
              <div class="col-md-8">
                   <div class="positionRelative">
          			<input class="form-control positionRelative" id="petitionerBirthPlace" name="petitionerBirthPlace" placeholder="Place of Birth"  type="text" field-name="Place of Birth" data-validation="required"></input>
          				<i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('petitionerBirthPlace');"></i>
         			</div>
              </div>
            </div>   
            
            <div class="form-group">
              <label class="col-md-4">Nationality</label>
              <div class="col-md-8">
                  <div class="positionRelative">
          			<input class="form-control positionRelative" id="petitionerNationality" name="petitionerNationality" value="${loginCountryName}" placeholder="Select Country"  type="text" field-name="Nationality" data-validation="required"></input>
          				<i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('petitionerNationality');"></i>
         			</div>
              </div>
            </div> 
            <div class="form-group">
              <label class="col-md-4">Marital status</label>
              <div class="col-md-8">
                  <label class="radioIput paddingRight"><input type="radio" name="ms">Single</label>
                  <label class="radioIput paddingLeft"><input type="radio" name="ms">Married</label>
                  <label class="radioIput paddingLeft"><input type="radio" name="ms">Divorced</label>
                  <label class="radioIput paddingLeft"><input type="radio" name="ms">Separated</label>
              </div>
            </div>  
             <div class="form-group">
              <label class="col-md-4">SSN</label>
              <div class="col-md-8">
                <input type="text" class="form-control" name="" value="" placeholder="000-00-0000">
              </div>
            </div>
             <div class="form-group">
              <label class="col-md-4">I-94 Number</label>
              <div class="col-md-8">
                <input type="text" class="form-control" name="" value="" placeholder="XXXXXXXXX-XX">
              </div>
            </div>
           
             <div class="form-group">
              <label class="col-md-4">I-20 SEVIS Number If Student</label>
              <div class="col-md-8">
<input type="text" class="form-control" name="" value="" placeholder="N-XXXXXXXXXX">
              </div>
            </div>
                         
            <div class="form-group">
              <label class="col-md-4">EAD Number If Student</label>
              <div class="col-md-8">
                <input type="text" class="form-control" name="" value="" placeholder="">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-4">Previous LCA Number</label>
              <div class="col-md-8">
                <input type="text" class="form-control" name="" value="" placeholder="I-XXX-XXXXX-XXXXXX">
              </div>
            </div>
            
            <div class="form-group">
              <label class="col-md-4">Previous LCA Start Date</label>
              <div class="col-md-8">
               <div class="input-group">
                <input class="form-control calendar" id="prevLcaStartDate" name="prevLcaStartDate" placeholder="Previous LCA Start Dates" type="text">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('prevLcaStartDate');"></i></span>
            </div>
              </div>
            </div>

                        <div class="form-group">
              <label class="col-md-4">Previous LCA End date</label>
              <div class="col-md-8">
               <div class="input-group">
                <input class="form-control calendar" id="prevLcaEndDate" name="prevLcaEndDate" placeholder="Previous LCA End date" type="text">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('prevLcaEndDate');"></i></span>
            </div>
              </div>
            </div>
            
           <div class="form-group">
              <label class="col-md-4">Upload Resume</label>
               <div class="col-md-8">
				<div class="input-group col-md-12 clearUploads" style="margin:0 auto">
					<label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
					  <input type="file" style="display: none;" multiple id="uploadBtn" class="upload">
					  </span></label>
					<input type="text" class="form-control" readonly id="uploadFile" placeholder="Attachment">
				  </div>
              </div>
            </div>
            
            
            

            
             <div class="form-group">
              <label class="col-md-4">Experience</label>
              <div class="col-md-4 dropdown-select">
                <select>
                	<option>--Select Year--</option>
                	<option>Year-1</option>
                	<option>Year-2</option>
                	<option>Year-3</option>
                </select>
              </div>
             <div class="col-md-4 dropdown-select">
                <select>
                	<option>--Select Month--</option>
                	<option>0</option>
                	<option>1</option>
                	<option>2</option>
                	<option>3</option>
                	<option>4</option>
                	<option>5</option>
                	<option>6</option>
                	<option>7</option>
                	<option>8</option>
                	<option>9</option>
                	<option>10</option>
                	<option>11</option>
                </select>
              </div>
            </div> 
              
           <div class="clearfix"></div>
            	<h4 class="col-md-12 mb margin-top0">4. Job Details</h4>
           <div class="clearfix"></div>
          
             <div class="form-group">
              <label class="col-md-4">Job Title</label>
              <div class="col-md-8">
                 <input type="text" class="form-control" name="" value="">
              </div>
          </div>  
            
            

            <div class="form-group">
              <label class="col-md-4">Employment Level</label>
              <div class="col-md-8">
                <select class="chosen-select form-control">
                    <option>Level 1</option>
                    <option>Level 2</option>
                    <option>Level 3</option>
                  </select>
              </div>
            </div>
            
            
            
            <div class="form-group">
              <label class="col-md-4">Employer</label>
              <div class="col-md-8">
                <input type="text" class="form-control" name="" value="">
              </div>
            </div>
            
            <div class="form-group">
              <label class="col-md-4">Location</label>
              <div class="col-md-8">
                 <div class="positionRelative">
          			<input class="form-control positionRelative" id="petitionerJobLocation" name="petitionerJobLocation" placeholder="Enter your Location"  type="text" field-name="Location" data-validation="required"></input>
          				<i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('petitionerJobLocation');"></i>
         			</div>
              </div>
            </div>
            
            <div class="form-group">
						<label class="col-md-4">Wages</label>
						<div class="col-md-8">
							<div class="input-group er ratetype">
								<div class="input-group-btn tblminwidth-75">

									<select class="selectpicker" id="currentRateCurrency"
										name="currentRateCurrency">

										<option>INR</option>
										<option>AUD</option>
										<option>USD</option>
										<option>GBP</option>
										<option>SGD</option>
										<option>EUR</option> 

									</select>

								</div>
								<input type="text" id="currentRate" name="currentRate"
									value="" field-name="Current Rate"
									data-validation="validate_Space validate_float validate_length length1-8"
									data-validation-optional="true" value="" class="form-control">
								<div class="input-group-btn tblminwidth-75 ratetype">
									<select class="selectpicker" id="currentRateType"
										name="currentRateType" field-name="Rate Per">
										 <option value="">Rate Per</option>
										<option>Hourly</option>
										<option>Weekly</option>
										<option >Bi-weekly</option>
										<option >Semi-Monthly</option>
										<option>Monthly</option>
										<option>Bi-Monthly</option>
										<option>Yearly</option> 
									</select>
								</div>
								<div class="clearfix"></div>
								<div class="clearfix"></div>
							</div>
						</div>
					</div>
            
            <div class="form-group">
              <label class="col-md-4">Start Date</label>
              <div class="col-md-8">
               <div class="input-group">
                <input type="text" class="form-control calendar" id="jobStartDate" name="jobStartDate" placeholder="Start Date">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('jobStartDate');"></i></span>
            </div>
              </div>
            </div>
            
            <div class="form-group">
              <label class="col-md-4">End Date</label>
              <div class="col-md-8">
               <div class="input-group">
                <input type="text" class="form-control calendar" name="jobEndDate" id="jobEndDate" placeholder="Start Date">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('jobEndDate');"></i></span>
            </div>
              </div>
            </div>
            
             <div class="form-group">
              <label class="col-md-4">LCA Number</label>
              <div class="col-md-8">
                 <input type="text" class="form-control" name="" value="" placeholder="I-XXX-XXXXX-XXXXXX">
              </div>
          </div>
            
            <div class="form-group">
              <label class="col-md-4">LCA Start dates</label>
              <div class="col-md-8">
               <div class="input-group">
                <input type="text" class="form-control calendar" id="lcaStartDate" name="lcaStartDate" placeholder="LCA Start dates">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('lcaStartDate');"></i></span>
            </div>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-4">LCA End date</label>
              <div class="col-md-8">
               <div class="input-group">
                <input type="text" class="form-control calendar" id="lcaEndDate" name="lcaEndDate" placeholder="LCA End date">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('lcaEndDate');"></i></span>
            </div>
              </div>
            </div>
   
           <div class="clearfix"></div>
           	<div class="col-md-12 mb margin-top0">
            	<h4 class="pull-left">5. Residence</h4>
            	<button type="button" class="btn btn-default pull-right"><i class="fa fa-plus paddingRight"></i>Add Residence</button>
            	</div>
           <div class="clearfix"></div>
            
            <div class="form-group">
              <label class="col-md-4">Location</label>
              <div class="col-md-8">
                  <div class="positionRelative">
								<input class="form-control positionRelative"
									id="petitionerLocation" name="petitionerLocation"
									placeholder="Enter Location" type="text"
									field-name="Address" data-validation="required"></input> <i
									class="fa fa-refresh btn btn-primary refresh-icon"
									title="Refresh"
									onClick="refreshGoogleApiLocation('petitionerLocation','petitionerCountry','petitionerState','petitionerCity','petitionerZipCode','address');"></i>
					</div>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-4">Street Address</label>
              <div class="col-md-8">
                  <input type="text" class="form-control" id="address"
								name="address" value="${address}" field-name="Street Address" placeholder="Enter Street Addres"
								data-validation="validate_Space validate_length length2-250"
								data-validation-optional="true">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-4">City</label>
              <div class="col-md-8">
                  <input class="form-control" id="petitionerCity" name="city" placeholder="City" type="text"></input>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-4">State</label>
              <div class="col-md-8">
                  <input class="form-control" id="petitionerState" name="state" placeholder="State" type="text" ></input>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-4">Country</label>
              <div class="col-md-8">
                  <input class="form-control" id="petitionerCountry" name="country" placeholder="Country" field-name="Country"  data-validation="required"  value="${loginCountryName}" type="text" ></input>
              </div>
            </div>                                                                                               
		    <div class="form-group">
              <label class="col-md-4">Zip Code</label>
              <div class="col-md-8">
                  <input class="form-control" id="petitionerZipCode" name="zipcode"
								placeholder="Zip code" type="text" ></input>
              </div>
            </div>
		    
            <div class="form-group">
              <label class="col-md-4">Is current residence</label>
              <div class="col-md-8">
                  <input type="checkbox" name="currentResidence">
              </div>
            </div>
			<div class="clearfix"></div>
         <div class="form-group">
              <label class="col-md-4">Start Date</label>
              <div class="col-md-8">
					<div class="input-group">
                <input type="text" class="form-control calendar" id="resiStartDate" name="resiStartDate" placeholder="Start Date">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('resiStartDate')"></i></span>
              </div>
              </div>
          </div>
          
          <div class="form-group">
              <label class="col-md-4">End Date</label>
              <div class="col-md-8">
					<div class="input-group">
                <input type="text" class="form-control calendar" id="resiEndDate" name="resiEndDate" placeholder="End Date">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('resiEndDate')"></i></span>
              </div>
              </div>
          </div>

		<div class="clearfix"></div>
		<div class="addImmiFoot">
			<!--<a href="javascript:void(0)" class="pull-left">Prev</a>-->
			<a href="javascript:void(0)" onclick="immigration.savePageOneContinue('two')" id="savePageOneContinue" class="nextBtn">Save &amp; Continue</a>
			<a href="javascript:void(0)" class="nextBtn">Save</a>
<!--
			<a href="new-petition-step2.html" class="nextBtn">Send Request</a>
-->
			<a href="javascript:void(0)">Skip</a>
			<div class="clearfix"></div>
		</div>
	</div>

   </div>
   </div>
   </section>
   <script type="text/javascript">
		window.onload = function () {
			editOrgLocationAuto('petitionerNationality');
			initLocationAuto('petitionerPlace');
 			initLocationAuto('petitionerJobLocation');
 			initLocationAuto('petitionerBirthPlace');
 			initLocationAuto('petitionerLocation','petitionerCountry','petitionerState','petitionerCity','petitionerZipCode','','','','address');
			
		 };
	</script>
	<script>
 function getAttorneysByOrgLocation(){
	 console.log(" before function calling ---->");
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getVendorsByOrgLocation?", 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	console.log(" inside success ---->");
		        	$('#attorneys').empty();
		        	$('#attorneys').append('<option value="">--Select Attorney --</option>');
		            for(var i=0;i<json.length;i++){
		            	  var opt = json[i];
		            		$('#attorneys').append('<option value='+opt.id+' >'+opt.orgName+'</option>');
		            }
		            $('#attorneys').trigger("chosen:updated");
		            //getVendorContactList();
		            
		        }
		 });
 }
 
 function getAttorneyContactList(){
		var orgRelationID = $('#attorneys').val();
			 $.ajax({
				    type: "POST",
			        url: sourceleadUrl+"/getOrgSpocsList?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationID+"&type=Vendor",  
			        success: function(json) {
			        	//var json = JSON.parse(data);
			        	$('#attorneyContact').empty();
			        	$('#attorneyContact').append('<option value="">--Select Attorney contact--</option>');
			             for(var i=0;i<json.data.length;i++){
			            	 var opt = json.data[i];
			            	 $('#attorneyContact').append('<option value='+opt.id+' >'+opt.spocName+' ('+opt.ownerName+')</option>');
			            } 
			            $('#attorneyContact').trigger("chosen:updated");
			        }
			 }); 
	 }
 </script>
   <script type="text/javascript">
	  $( document ).ready(function() {	
		  
		  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatPetitionSave = getJSDateFormat(locationDate);
		  $('#openDate').datepicker({
		      dateFormat: finalDateFormatPetitionSave,
		      changeMonth: true,
		  	  changeYear: true, 
		      
		  });
		  
		  $('#arrivalDate').datepicker({
	      dateFormat: finalDateFormatPetitionSave,
	      changeMonth: true,
	  	  changeYear: true, 
	  	  minDate: 1 
	  });

		  $('#birthDate').datepicker({
		      dateFormat: finalDateFormatPetitionSave,
		      changeMonth: true,
		  	  changeYear: true, 
		  	  yearRange: "-100:+0",
		  	  maxDate:0  
		  })
// previou lca start and end date 
		  $('#prevLcaStartDate').datepicker({
		      dateFormat: finalDateFormatPetitionSave,
		      changeMonth: true,
		  	  changeYear: true, 
		      onSelect: function (selected) {
		            //var dt = new Date(selected);
		            var dt = $(this).datepicker('getDate', '+1d');
		            dt.setDate(dt.getDate() + 1);
		            $("#prevLcaEndDate").datepicker("option", "minDate", dt);
		        }
		  });
		  
		  $('#prevLcaEndDate').datepicker({
		      dateFormat: finalDateFormatPetitionSave,
		      changeMonth: true,
		  	  changeYear: true, 
		        onSelect: function (selected) {
		            //var dt = new Date(selected);
		            var dt = $(this).datepicker('getDate', '-1d');
		            dt.setDate(dt.getDate() - 1);
		            $("#prevLcaStartDate").datepicker("option", "maxDate", dt);
		        }
		  });
	// job start date and end date 
		// previou lca start and end date 
		  $('#jobStartDate').datepicker({
		      dateFormat: finalDateFormatPetitionSave,
		      changeMonth: true,
		  	  changeYear: true, 
		      onSelect: function (selected) {
		            //var dt = new Date(selected);
		            var dt = $(this).datepicker('getDate', '+1d');
		            dt.setDate(dt.getDate() + 1);
		            $("#jobEndDate").datepicker("option", "minDate", dt);
		        }
		  });
		  
		  $('#jobEndDate').datepicker({
		      dateFormat: finalDateFormatPetitionSave,
		      changeMonth: true,
		  	  changeYear: true, 
		        onSelect: function (selected) {
		            //var dt = new Date(selected);
		            var dt = $(this).datepicker('getDate', '-1d');
		            dt.setDate(dt.getDate() - 1);
		            $("#jobStartDate").datepicker("option", "maxDate", dt);
		        }
		  });
		// lca staert date and end date  
		  $('#lcaStartDate').datepicker({
		      dateFormat: finalDateFormatPetitionSave,
		      changeMonth: true,
		  	  changeYear: true, 
		      onSelect: function (selected) {
		            //var dt = new Date(selected);
		            var dt = $(this).datepicker('getDate', '+1d');
		            dt.setDate(dt.getDate() + 1);
		            $("#lcaEndDate").datepicker("option", "minDate", dt);
		        }
		  });
		  
		  $('#lcaEndDate').datepicker({
		      dateFormat: finalDateFormatPetitionSave,
		      changeMonth: true,
		  	  changeYear: true, 
		        onSelect: function (selected) {
		            //var dt = new Date(selected);
		            var dt = $(this).datepicker('getDate', '-1d');
		            dt.setDate(dt.getDate() - 1);
		            $("#lcaStartDate").datepicker("option", "maxDate", dt);
		        }
		  });
		  ///
		  
		// residence start date and end date  
		  $('#resiStartDate').datepicker({
		      dateFormat: finalDateFormatPetitionSave,
		      changeMonth: true,
		  	  changeYear: true, 
		      onSelect: function (selected) {
		            //var dt = new Date(selected);
		            var dt = $(this).datepicker('getDate', '+1d');
		            dt.setDate(dt.getDate() + 1);
		            $("#resiEndDate").datepicker("option", "minDate", dt);
		        }
		  });
		  
		  $('#resiEndDate').datepicker({
		      dateFormat: finalDateFormatPetitionSave,
		      changeMonth: true,
		  	  changeYear: true, 
		        onSelect: function (selected) {
		            //var dt = new Date(selected);
		            var dt = $(this).datepicker('getDate', '-1d');
		            dt.setDate(dt.getDate() - 1);
		            $("#resiStartDate").datepicker("option", "maxDate", dt);
		        }
		  });
		  // 
		  
		  getAttorneysByOrgLocation();
 });
   </script>
   <script>
	
   function clearDateValue(id){
	   $("#"+id).val(""); 
   }
   </script>
   

 
 <script>
function addCandiadateEmptyAttorneyContact(){
	$("#attorneyContact").empty();
	 $('#attorneyContact').append('<option value="">--select Attorney contact--</option>');
   $('#attorneyContact').trigger("chosen:updated");
}
</script>
<script>
 function  removeValidationAttorney() {
	$("#errorAttorney").find("p.jquery_form_error_message").remove();
}
</script>
<script>
function  removeValidationAttorneyContact() {
	 $("#errorAttorneyContact").find("p.jquery_form_error_message").remove();
}
</script>
<script>
	 function selectedAttorney(){
		 var vid =  $('#attorneys').val();
		 window.open(sourceleadUrl+"/addVendorContact?vid="+vid,'_blank');
	 }
	 </script>
	 
	 <script>
	 function editOrgLocationAuto(nationalityId) {
	 	editOrgInitialize(nationalityId);
	 }
	 function editOrgInitialize(nationalityId) {
	 		var input = (document.getElementById(nationalityId));
	 	    var options = {
	 	        types: ['geocode'],
	 	    };
	 	    autocomplete = new google.maps.places.Autocomplete(input, options);
	     	 
	   google.maps.event.addListener(autocomplete, 'place_changed', function() {
	   place = autocomplete.getPlace();
	   var address = {};
	   for (var i = 0; i < place.address_components.length; i++) {
	     var addressType = place.address_components[i].types[0];
	     switch (addressType) {
	       case "country":
	         address.country = place.address_components[i].long_name;
	         
	     }
	   }
	   
	   $("#"+nationalityId).val(address.country).change();
	 });
	 }
	 </script>