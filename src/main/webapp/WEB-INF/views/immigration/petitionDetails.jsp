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
			<form id="petitionDetailsForm" name="petitionDetailsForm">
          <div class="col-md-12 col-lg-12 myForm margin-top0">
          
            <h4 class="col-md-12 mb clearfix">1. Petition detail</h4>
            <div class="clearfix"></div>         
         	<div class="form-group">
              <label class="col-md-4">Petition type<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-8 dropdown-select">
                  <select id="petitionType" name="petitionType" class="form-control" field-name="Petition type" data-validation="required">
                  </select>
              </div>
          </div> 

            <div class="form-group">
              <label class="col-md-4">Open date<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-8">
               <div class="input-group">
                <input type="text" id ="openDate" class="form-control calendar" name="openDate" placeholder="Open Date" field-name="Open date" data-validation="required"/>
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('openDate');"></i></span>
              </div>
            </div>
            </div>
            
            <div class="form-group">
              <label class="col-md-4">Current USCIS Status</label>
              <div class="col-md-8 dropdown-select">
                <select id="currUscisStatus" name="currUscisStatus" class="form-control">
                    
                  </select>
              </div>
            </div>
		
            <div class="form-group">
              <label class="col-md-4">Date of arrival</label>
              <div class="col-md-8">
               <div class="input-group">
                <input type="text" id="arrivalDate" class="form-control calendar" name="arrivalDate" placeholder="Date of arrival"/>
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('arrivalDate');"></i></span>
              </div>
              </div>
            </div>                        
            
            <div class="form-group">
          		<label class="col-md-4">Place of entry<span class="text-danger">&nbsp;*</span></label>
         			 <div class="col-md-8" id="locationField">
          			<div class="positionRelative">
          			<input class="form-control positionRelative" id="entryPlace" name="entryPlace" onclick="initLocationAuto('entryPlace');" placeholder="Enter your Location"  type="text" field-name="Place Of Entry" data-validation="required"></input>
          				<i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onclick="refreshGoogleApiLocation('entryPlace');"></i>
         				 </div>
        			  </div>
       			 </div>
            
            
            <div class="form-group">
              <label class="col-md-4">Choose Template</label>
              <div class="col-md-8">
               <select class="chosen-select form-control" id="templateId" name="templateId" data-placeholder="Choose Template">
               	
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
								<label class="col-md-4"><span class="pull-left">Attorney Name</span><span
									class="text-danger pull-left" id="man1">*</span></label>
								<div class="col-md-8 chooRadius" id="errorAttorney">
								<div class="input-group col-md-10 pull-left chooRadius">
									<select class="chosen-select form-control" name="attorneyId"
										id="attorneyId" placeholder="Select Attorney" field-name="Attorney Name" data-validation="required"
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
								<label class="col-md-4"><span class="pull-left">Attorney Contact</span><span class="text-danger pull-left" id="man2">*</span></label>
								<div class="col-md-8 chooRadius" id="errorAttorneyContact">
								<div class="input-group col-md-10 pull-left chooRadius">
									<select class="chosen-select form-control" name="attorneyContact" id="attorneyContact" placeholder="Select Attorney Contact"
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
								<label class="col-md-4"><span class="pull-left">Associate (Beneficiary)</span><span
									class="text-danger pull-left" id="man1">*</span></label>
								<div class="col-md-8 chooRadius" id="errorAttorney">
								<div class="input-group col-md-10 pull-left chooRadius">
									<select class="chosen-select form-control" name="candidateId"
										id="candidateId" placeholder="Select Beneficiary" onchange="updateCandiateInfo($(this));">
									</select>
								 <span class="input-group-addon cursorPointer bg-blue" onclick="getCandidatesByOrgLocation();"><i class="fa fa-refresh "></i></span>
									
								</div>
							</div>
							</div>
                                                                                                                                             
         <div class="form-group">
              <label class="col-md-4">First Name<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-8">
					<input type="text" class="form-control" id="firstName" name="firstName" field-name="First Name" data-validation="required"/>
              </div>
          </div>
          <div class="form-group">
              <label class="col-md-4">Middle Name</label>
              <div class="col-md-8">
					<input type="text" class="form-control" name="middleName" id="middleName">
              </div>
          </div> 
          <div class="form-group">
              <label class="col-md-4">Last Name<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-8">
					<input type="text" class="form-control" name="lastName" id="lastName" field-name="Last Name" data-validation="required">
              </div>
          </div>  
         <div class="form-group">
						<label class="col-md-4">Gender</label>
						<div class="col-md-8" id= "errorGenderMsg">
						<label class="paddingRight"><input type="radio"  value="Male"  name="gender"><span class="mr5"></span>Male</label>
						<label class="paddingLeft paddingRight"><input type="radio"  value="Female" name="gender"><span class="mr5"></span>Female</label>
						<label class="paddingRight"><input type="radio"  value="Transgender" name="gender" ><span class="mr5"></span>Transgender</label>
						<!-- <input type="hidden"  id="genderValue" name="genderValue"/> --> 		
			</div>
			</div>
          <div class="form-group">
              <label class="col-md-4">Email<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-8">
					<input type="text" class="form-control" name="email" id="email" field-name="Email" data-validation="required">
              </div>
          </div>
                      	
          <div class="form-group">
              <label class="col-md-4">Mobile</label>
              <div class="col-md-8">
					<input type="text" class="form-control" name="mobile" id="mobile">
              </div>
          </div>
          
           <div class="form-group">
              <label class="col-md-4">Alternative Mobile</label>
              <div class="col-md-8">
					<input type="text" class="form-control" name="alternateMobile" id="alternateMobile">
              </div>
          </div> 
            
           <div class="form-group">
              <label class="col-md-4">DOB<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-8">
					<div class="input-group">
                <input type="text" class="form-control calendar" name="birthDate" id="birthDate" placeholder="Date Of Birth" field-name="Date Of Birth" data-validation="required">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onClick="clearDateValue('birthDate');"></i></span>
              </div>
              </div>
          </div> 
           <div class="form-group">
              <label class="col-md-4">Place of Birth</label>
              <div class="col-md-8">
                   <div class="positionRelative">
          			<input class="form-control positionRelative" id="birthPlace" name="birthPlace" onclick="initLocationAuto('birthPlace');" type="text" field-name="Place of Birth"></input>
          				<i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('birthPlace');"></i>
         			</div>
              </div>
            </div>   
          
            <div class="form-group">
              <label class="col-md-4">Nationality</label>
              <div class="col-md-8">
                  <div class="positionRelative">
          			<input class="form-control positionRelative" id="nationality" onclick="editOrgLocationAuto('nationality');" name="nationality" value="" placeholder="Select Country"  type="text"></input>
          				<i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('nationality');"></i>
         			</div>
              </div>
            </div> 
            <div class="form-group">
              <label class="col-md-4">Marital status</label>
              <div class="col-md-8">
                  <label class="radioIput paddingRight"><input type="radio" value="Single" name="maritalStatus">Single</label>
                  <label class="radioIput paddingLeft"><input type="radio" value="Married"  name="maritalStatus">Married</label>
                  <label class="radioIput paddingLeft"><input type="radio" value="Divorced"  name="maritalStatus">Divorced</label>
                  <label class="radioIput paddingLeft"><input type="radio" value="Separated"  name="maritalStatus">Separated</label>
              </div>
            </div>  
             <div class="form-group">
              <label class="col-md-4">SSN</label>
              <div class="col-md-8">
                <input type="text" class="form-control" name="ssnNumber" id="ssnNumber" placeholder="000-00-0000">
              </div>
            </div>
             <div class="form-group">
              <label class="col-md-4">I-94 Number</label>
              <div class="col-md-8">
                <input type="text" class="form-control" name="i_94_number" id ="i_94_number" placeholder="XXXXXXXXX-XX">
              </div>
            </div>
           
             <div class="form-group">
              <label class="col-md-4">I-20 SEVIS Number If Student</label>
              <div class="col-md-8">
					<input type="text" class="form-control" name="i_20_number"  id="i_20_number" placeholder="N-XXXXXXXXXX">
              </div>
            </div>
                         
            <div class="form-group">
              <label class="col-md-4">EAD Number If Student</label>
              <div class="col-md-8">
                <input type="text" class="form-control" name="eadNumber" id="eadNumber" placeholder="">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-4">Previous LCA Number</label>
              <div class="col-md-8">
                <input type="text" class="form-control" name="prevLcaNumber" id="prevLcaNumber" placeholder="I-XXX-XXXXX-XXXXXX">
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
            
           <div class="form-group"  id="resumeEmpty">
              <label class="col-md-4">Upload Resume</label><!-- need to updated  -->
               <div class="col-md-8">
				<div class="input-group col-md-12 clearUploads" id="clearUploads" style="margin:0 auto">
					<label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
					  <input type="file" style="display: none;" id="uploadBtn" class="upload">
					  </span></label>
					<input type="text" class="form-control" name="resumeInfo" readonly id="uploadFile" placeholder="Attachment">
				  </div>
              </div>
            </div>
            
            <div class="col-md-3 mt10" id="resumeDoc" style="display:none;">
			 <label id="documentName"  class="text-primary tbldata-dotdot-vsm pull-left">${item.fileName}</label>&nbsp;&nbsp;<span id="delete_file" onclick = "deleteDoc()"><i class="fa fa-trash removeIcon"></i></span>
			<input  type="hidden" id="generalExisitingFile" name = "generalExisitingFile" value="${item.fileName}">
			  <a  class="glyphicon glyphicon-download-alt"  href="${item.fileUrl}" download title="Download"></a>
			  </div>
            
            
            
             <div class="form-group">
              <label class="col-md-4">Experience</label>
              <div class="col-md-4 dropdown-select">
                <select id="experienceYears" name="experienceYears">
                	<option value="">--Select Year--</option>
                	<c:forEach begin="0" end="25" varStatus="loop">
				    	<option value="${loop.index}" >${loop.index}</option>
					</c:forEach>
                	<option value="25+" >25+</option>
                </select>
              </div>
             <div class="col-md-4 dropdown-select">
                <select id="experienceMonths" name="experienceMonths">
                	<option value="">--Select Month--</option>
                	<c:forEach begin="0" end="11" varStatus="loop">
				    	<option value="${loop.index}" >${loop.index}</option>
					</c:forEach>
                </select>
              </div>
            </div> 
              
           <div class="clearfix"></div>
            	<h4 class="col-md-12 mb margin-top0">4. Job Details</h4>
           <div class="clearfix"></div>
          
             <div class="form-group">
              <label class="col-md-4">Job Title</label>
              <div class="col-md-8">
               <select id="jobId" name="jobId" class="form-control">
                  </select>
              </div>
          </div>  
            
            

            <div class="form-group">
              <label class="col-md-4">Employment Level</label>
              <div class="col-md-8">
                <select id="employmentLevel"  name="employmentLevel" class="chosen-select form-control">
                  </select>
              </div>
            </div>
            
            
            
            <div class="form-group">
              <label class="col-md-4">Employer</label>
              <div class="col-md-8">
                <input type="text"  class="form-control" name="employer" id="employer">
              </div>
            </div>
            
            <div class="form-group">
              <label class="col-md-4">Location</label>
              <div class="col-md-8">
                 <div class="positionRelative">
          			<input class="form-control positionRelative" id="jobLocation" name="jobLocation" onclick="initLocationAuto('jobLocation');" placeholder="Enter your Location"  type="text"></input>
          				<i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('jobLocation');"></i>
         			</div>
              </div>
            </div>
            
            <div class="form-group">
						<label class="col-md-4">Wages</label>
						<div class="col-md-8">
							<div class="input-group er ratetype">
								<div class="input-group-btn tblminwidth-75">

									<select class="form-control selectpicker" id="rateCurrency"
										name="rateCurrency">

										<option>INR</option>
										<option>AUD</option>
										<option>USD</option>
										<option>GBP</option>
										<option>SGD</option>
										<option>EUR</option> 

									</select>

								</div>
								<input type="text" id="rate" name="rate"
									value="" field-name="Current Rate"
									data-validation="validate_Space validate_float validate_length length1-8"
									data-validation-optional="true" value="" class="form-control">
								<div class="input-group-btn tblminwidth-75 ratetype">
									<select class="selectpicker form-control" id="rateType"
										name="rateType" field-name="Rate Per">
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
                 <input type="text" class="form-control" name="lcaNumber" id="lcaNumber" placeholder="I-XXX-XXXXX-XXXXXX">
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
            	<button type="button" class="btn btn-default pull-right" onclick="appendDynamicResidenceDiv();"><i class="fa fa-plus paddingRight"></i>Add Residence</button>
            	</div>
           <div class="clearfix"></div>
            
            <div class="form-group">
              <label class="col-md-4">Location</label>
              <div class="col-md-8">
                  <div class="positionRelative">
								<input class="form-control positionRelative"
									id="location" name="location" onclick="initLocationAuto('location','country','state','city','zipcode','','','','address');"
									placeholder="Enter Location" type="text"></input> <i
									class="fa fa-refresh btn btn-primary refresh-icon"
									title="Refresh"
									onClick="refreshGoogleApiLocation('location','country','state','city','zipcode','address');"></i>
					</div>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-4">Street Address</label>
              <div class="col-md-8">
                  <input type="text" class="form-control" id="address"
								name="address" value="" field-name="Street Address" placeholder="Enter Street Addres"
								data-validation="validate_Space validate_length length2-250"
								data-validation-optional="true">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-4">City</label>
              <div class="col-md-8">
                  <input class="form-control" id="city" name="city" placeholder="City" type="text"></input>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-4">State</label>
              <div class="col-md-8">
                  <input class="form-control" id="state" name="state" placeholder="State" type="text" ></input>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-4">Country</label>
              <div class="col-md-8">
                  <input class="form-control" id="country" name="country" placeholder="Country" value="${loginCountryName}" type="text" ></input>
              </div>
            </div>                                                                                               
		    <div class="form-group">
              <label class="col-md-4">Zip Code</label>
              <div class="col-md-8">
                  <input class="form-control" id="zipcode" name="zipcode"
								placeholder="Zip code" type="text" ></input>
              </div>
            </div>
		    
            <div class="form-group">
              <label class="col-md-4">Is current residence</label>
              <div class="col-md-8">
                  <input type="checkbox" name="isCurrResidence" value="Y" id="isCurrResidence"  checked disabled>
              </div>
            </div>
			<div class="clearfix"></div>
         <div class="form-group">
              <label class="col-md-4">Start Date</label>
              <div class="col-md-8">
					<div class="input-group">
                <input type="text" class="form-control calendar" id="residenceStartDate"  name="residenceStartDate" placeholder="Start Date">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('residenceStartDate')"></i></span>
              </div>
              </div>
          </div>
          
          <div class="form-group">
              <label class="col-md-4">End Date</label>
              <div class="col-md-8">
					<div class="input-group">
                <input type="text" class="form-control calendar" id="residenceEndDate"  name="residenceEndDate" placeholder="End Date">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('residenceEndDate')"></i></span>
              </div>
              </div>
          </div>
         
         <div id ="dynamicResidenceGroupDiv" ><!--  dynamic div start -->
          
         </div> <!--  dynamic div end -->
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
	</form>
   </div>
   </div>
   </section>
    <script type="text/javascript">
    var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormatPetitionSave = getJSDateFormat(locationDate);
	function showStartDatePicker(id){
	  $("#residenceStartDate"+id).datepicker({
	      dateFormat: finalDateFormatPetitionSave,
	      changeMonth: true,
	  	  changeYear: true, 
	      onSelect: function (selected) {
	            //var dt = new Date(selected);
	            var dt = $(this).datepicker('getDate', '+1d');
	            dt.setDate(dt.getDate() + 1);
	            $("#residenceEndDate"+id).datepicker("option", "minDate", dt);
	        }
	  }).datepicker("show");
	} 
	function showEndDatePicker(id){
	  $("#residenceEndDate"+id).datepicker({
	      dateFormat: finalDateFormatPetitionSave,
	      changeMonth: true,
	  	  changeYear: true, 
	        onSelect: function (selected) {
	            //var dt = new Date(selected);
	            var dt = $(this).datepicker('getDate', '-1d');
	            dt.setDate(dt.getDate() - 1);
	            $("#residenceStartDate"+id).datepicker("option", "maxDate", dt);
	        }
	  }).datepicker("show");
	}
    function prepareDynamicResidenceDiv(rowId){
	   var prepareResidencediv =''; 
		   
		   
	   prepareResidencediv =prepareResidencediv+ "<div id ='dynamicResidenceDiv"+rowId+"'>";
	   prepareResidencediv =prepareResidencediv+ "   <div class='clearfix'></div>";
	   prepareResidencediv =prepareResidencediv+ "     	<div class='col-md-12 mb margin-top0'>";
	   prepareResidencediv =prepareResidencediv+ "   <h4 class='pull-left'>Residence</h4> <button type='button' class='btn btn-default pull-right' onclick='removeDynamicResidenceDiv("+rowId+")'><i class='fa fa-remove text-danger paddingRight'></i>Remove Residence</button>";
	   prepareResidencediv =prepareResidencediv+ "   </div>";
	   prepareResidencediv =prepareResidencediv+ "   <div class='clearfix'></div>";
	   prepareResidencediv =prepareResidencediv+ " <div class='form-group'>";
	   prepareResidencediv =prepareResidencediv+ "     <label class='col-md-4'>Location</label>";
	   prepareResidencediv =prepareResidencediv+ "       <div class='col-md-8'>";
	   prepareResidencediv =prepareResidencediv+ "           <div class='positionRelative'>";
	   prepareResidencediv =prepareResidencediv+ ' 						<input class="form-control positionRelative"';
	   prepareResidencediv =prepareResidencediv+ ' 						id="location'+rowId+'" name="residenceList['+rowId+'].location"';
	   prepareResidencediv =prepareResidencediv+ ' 							placeholder="Enter Location" type="text" onClick="initLocationAuto(\'location'+rowId+'\',\'country'+rowId+'\',\'state'+rowId+'\',\'city'+rowId+'\',\'zipcode'+rowId+'\',\'\',\'\',\'\',\'address'+rowId+'\'); refreshGoogleApiLocation(\'location'+rowId+'\',\'country'+rowId+'\',\'state'+rowId+'\',\'city'+rowId+'\',\'zipcode'+rowId+'\',\'\',\'\',\'\',\'address'+rowId+'\');"></input> <i';
	   prepareResidencediv =prepareResidencediv+ ' 							class="fa fa-refresh btn btn-primary refresh-icon"';
	   prepareResidencediv =prepareResidencediv+ ' 							title="Refresh"';
	   prepareResidencediv =prepareResidencediv+ ' 							onClick="refreshGoogleApiLocation(\'location'+rowId+'\',\'country'+rowId+'\',\'state'+rowId+'\',\'city'+rowId+'\',\'zipcode'+rowId+'\',\'\',\'\',\'\',\'address'+rowId+'\');"></i>';
	   prepareResidencediv =prepareResidencediv+ " 			</div>";
	   prepareResidencediv =prepareResidencediv+ "       </div>";
	   prepareResidencediv =prepareResidencediv+ "     </div>";
	   prepareResidencediv =prepareResidencediv+ "    <div class='form-group'>";
	   prepareResidencediv =prepareResidencediv+ "      <label class='col-md-4'>Street Address</label>";
	   prepareResidencediv =prepareResidencediv+ "      <div class='col-md-8'>";
	   prepareResidencediv =prepareResidencediv+ "           <input type='text' class='form-control' id='address"+rowId+"'";
	   prepareResidencediv =prepareResidencediv+ " 						name='residenceList["+rowId+"].address' value='${address}' field-name='Street Address' placeholder='Enter Street Addres'";
	   prepareResidencediv =prepareResidencediv+ " 						data-validation='validate_Space validate_length length2-250'";
	   prepareResidencediv =prepareResidencediv+ " 						data-validation-optional='true'>";
	   prepareResidencediv =prepareResidencediv+ "       </div>";
	   prepareResidencediv =prepareResidencediv+ "     </div>";
	   prepareResidencediv =prepareResidencediv+ "     <div class='form-group'>";
	   prepareResidencediv =prepareResidencediv+ "       <label class='col-md-4'>City</label>";
	   prepareResidencediv =prepareResidencediv+ "        <div class='col-md-8'>";
	   prepareResidencediv =prepareResidencediv+ "           <input class='form-control' id='city"+rowId+"' name='residenceList["+rowId+"].city' placeholder='City' type='text'></input>";
	   prepareResidencediv =prepareResidencediv+ "       </div>";
	   prepareResidencediv =prepareResidencediv+ "      </div>";
	   prepareResidencediv =prepareResidencediv+ "     <div class='form-group'>";
	   prepareResidencediv =prepareResidencediv+ "       <label class='col-md-4'>State</label>";
	   prepareResidencediv =prepareResidencediv+ "       <div class='col-md-8'>";
	   prepareResidencediv =prepareResidencediv+ "           <input class='form-control' id='state"+rowId+"' name='residenceList["+rowId+"].state' placeholder='State' type='text' ></input>";
	   prepareResidencediv =prepareResidencediv+ "       </div>";
	   prepareResidencediv =prepareResidencediv+ "     </div>";
	   prepareResidencediv =prepareResidencediv+ "     <div class='form-group'>";
	   prepareResidencediv =prepareResidencediv+ "        <label class='col-md-4'>Country</label>";
	   prepareResidencediv =prepareResidencediv+ "       <div class='col-md-8'>";
	   prepareResidencediv =prepareResidencediv+ "           <input class='form-control' id='country"+rowId+"' name='residenceList["+rowId+"].country' placeholder='Country' value='${loginCountryName}' type='text' ></input>";
	   prepareResidencediv =prepareResidencediv+ "       </div>";
	   prepareResidencediv =prepareResidencediv+ "     </div> ";                                                                                              
	   prepareResidencediv =prepareResidencediv+ "     <div class='form-group'>";
	   prepareResidencediv =prepareResidencediv+ "       <label class='col-md-4'>Zip Code</label>";
	   prepareResidencediv =prepareResidencediv+ "       <div class='col-md-8'>";
	   prepareResidencediv =prepareResidencediv+ "          <input class='form-control' id='zipcode"+rowId+"' name='residenceList["+rowId+"].zipcode'";
	   prepareResidencediv =prepareResidencediv+ " 						placeholder='Zip code' type='text' ></input>";
	   prepareResidencediv =prepareResidencediv+ "       </div>";
	   prepareResidencediv =prepareResidencediv+ "     </div>";
	   prepareResidencediv =prepareResidencediv+ "    	<div class='clearfix'></div>";
	   prepareResidencediv =prepareResidencediv+ "  <div class='form-group'>";
	   prepareResidencediv =prepareResidencediv+ "       <label class='col-md-4'>Start Date</label>";
	   prepareResidencediv =prepareResidencediv+ "       <div class='col-md-8'>";
	   prepareResidencediv =prepareResidencediv+ " 			<div class='input-group'>";
	   prepareResidencediv =prepareResidencediv+ "         <input type='text' class='form-control calendar' id='residenceStartDate"+rowId+"' onClick='showStartDatePicker("+rowId+")' name='residenceList["+rowId+"].residenceStartDate' placeholder='Start Date'>";
	   prepareResidencediv =prepareResidencediv+ "         <span class='input-group-addon'><i class='fa fa-times text-danger cursorPointer' onclick='clearDateValue(\"residenceStartDate"+rowId+"\")'></i></span>";
	   prepareResidencediv =prepareResidencediv+ "       </div>";
	   prepareResidencediv =prepareResidencediv+ "       </div>";
	   prepareResidencediv =prepareResidencediv+ "  </div>";
         
	   prepareResidencediv =prepareResidencediv+ "  <div class='form-group'>";
	   prepareResidencediv =prepareResidencediv+ "       <label class='col-md-4'>End Date</label>";
	   prepareResidencediv =prepareResidencediv+ "       <div class='col-md-8'>";
	   prepareResidencediv =prepareResidencediv+ " 			<div class='input-group'>";
	   prepareResidencediv =prepareResidencediv+ "        <input type='text' class='form-control calendar' id='residenceEndDate"+rowId+"' onClick='showEndDatePicker("+rowId+")'  name='residenceList["+rowId+"].residenceEndDate' placeholder='End Date'>";
	   prepareResidencediv =prepareResidencediv+ "        <span class='input-group-addon'><i class='fa fa-times text-danger cursorPointer' onclick='clearDateValue(\"residenceEndDate"+rowId+"\")'></i></span>";
	   prepareResidencediv =prepareResidencediv+ "      </div>";
	   prepareResidencediv =prepareResidencediv+ "       </div>";
	   prepareResidencediv =prepareResidencediv+ "   </div>";
	   prepareResidencediv =prepareResidencediv+ "   </div>";
	   
		return prepareResidencediv; 
   }
	
 //  var rowId ='<c:out value ="${rowIndex}"/>'// set the value for dyamic row when clicked on add button in UI.
  var rowId =0;
   function appendDynamicResidenceDiv(){
	   console.log("rowId value is --->"+rowId);
	   $("#dynamicResidenceGroupDiv").append(prepareDynamicResidenceDiv(rowId++)); 
   }
   //var deletedResicenceListIds = new Array();
   function removeDynamicResidenceDiv(id){
	   /* if($("#templateId"+id).val() !==  undefined ){
		   deletedResicenceListIds.push($("#templateId"+id).val());   
	   } */
	   $("#dynamicResidenceDiv"+id).remove();
	   rowId--;// decrement rowid when deleted in UI.
   } 
   	</script>
   <script type="text/javascript">
		window.onload = function () {
			//editOrgLocationAuto('nationality');
			//initLocationAuto('entryPlace');
 		//	initLocationAuto('jobLocation');
 			//initLocationAuto('birthPlace');
 			//initLocationAuto('location','country','state','city','zipcode','','','','address');
			
		 };
	</script>
	
<script>

var candidateByOrgObject = {};
function getCandidatesByOrgLocation(){
	 console.log(" before getCandidatesByOrgLocation function calling ---->");
	 $("#loadingPetitionDiv").attr('style','position:fixed; width:auto; height:100%; background-color:rgba(255,255,255,0.8); top:52px; left:220px; right:30px; z-index:10000; background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 urltest = sourceleadUrl+immiModulePath+"/getAllActiveCandidatesByLocation";
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+immiModulePath+"/getAllActiveCandidatesByLocation", 
		        success: function(data) {
		        	 $('#candidateId').empty();
		        	$('#candidateId').append('<option value="">--Select Beneficiary --</option>');
		            for(var i=0;i<data.length;i++){
		            	 var opt = data[i];
		            	 candidateByOrgObject[opt.id]= opt;
		            	$('#candidateId').append('<option value='+opt.id+' >'+opt.firstName+" "+opt.lastName+'</option>');
		            }
		            $('#candidateId').trigger("chosen:updated"); 
		           /*  updateCandiateInfo(); */
		            $("#loadingPetitionDiv").removeAttr("style"); 
		   }
   });
}

$("#experienceValue").change(function () {
    if(this.value == '25+'){
    	$("#expMonthValue").val("");
    	$("#expMonthValue").prop("disabled", true);
    }else{
    	$("#expMonthValue").prop("disabled", false);
    }
});
</script>
	<script>
	
	function getPetitionTypeDropDown(){
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+immiModulePath+"/getPetitionTypeDropDown", 
		        success: function(data) {
		        	$('#petitionType').empty();
		        	$('#petitionType').append('<option selected value="">--Select Type --</option>');
		            for(var i=0;i<data.length;i++){
		            	  var opt = data[i];
		            		$('#petitionType').append('<option value='+opt.id+' >'+opt.name+'</option>');
		            }
		            $('#petitionType').trigger("chosen:updated");		        	
		        }
		 });
	}
	
	function getUscisTypeDropDown(){
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+immiModulePath+"/getUscisTypeDropDown", 
		        success: function(data) {
		        	$('#currUscisStatus').empty();
		        	$('#currUscisStatus').append('<option value="">--Select Type --</option>');
		            for(var i=0;i<data.length;i++){
		            	  var opt = data[i];
		            		$('#currUscisStatus').append('<option value='+opt.id+' >'+opt.name+'</option>');
		            }
		            $('#currUscisStatus').trigger("chosen:updated");		        	
		        }
		 });
	}
	
	function employmentLevelDropDown(){
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+immiModulePath+"/getEmploymentLevelDropDown", 
		        success: function(data) {
		        	$('#employmentLevel').empty();
		        	$('#employmentLevel').append('<option value="">--Select Type --</option>');
		            for(var i=0;i<data.length;i++){
		            	  var opt = data[i];
		            		$('#employmentLevel').append('<option value='+opt.id+' >'+opt.name+'</option>');
		            }
		            $('#employmentLevel').trigger("chosen:updated");		        	
		        }
		 });
	}
	
	function jobTitleDropDown(){
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+immiModulePath+"/getJobTitleDropDown", 
		        success: function(data) {
		        	$('#jobId').empty();
		        	$('#jobId').append('<option value="">--Select Type --</option>');
		            for(var i=0;i<data.length;i++){
		            	  var opt = data[i];
		            		$('#jobId').append('<option value='+opt.id+' >'+opt.jobTitle+'</option>');
		            }
		            $('#jobId').trigger("chosen:updated");		        	
		        }
		 });
	}
	
	
 function getAttorneysByOrgLocation(){
	 console.log(" before function calling ---->");
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getVendorsByOrgLocation?", 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	$('#attorneyId').empty();
		        	$('#attorneyId').append('<option value="">--Select Attorney --</option>');
		            for(var i=0;i<json.length;i++){
		            	  var opt = json[i];
		            		$('#attorneyId').append('<option value='+opt.id+' >'+opt.orgName+'</option>');
		            }
		            $('#attorneyId').trigger("chosen:updated");
		            //getVendorContactList();
		            
		        }
		 });
 }
 
 function getAttorneyContactList(){
		var orgRelationID = $('#attorneyId').val();
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
		  $('#rateCurrency').selectpicker('refresh');  // manually refreshing select picker due to bootstrap css loading issue.
		  $('#rateType').selectpicker('refresh');
		  getAttorneysByOrgLocation();
		 getCandidatesByOrgLocation();
		  getPetitionTypeDropDown();
		  getUscisTypeDropDown();
		  employmentLevelDropDown();
		  jobTitleDropDown();
		  
		 
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
		  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatPetitionSave = getJSDateFormat(locationDate);
	
		  $("#residenceStartDate").datepicker({
		      dateFormat: finalDateFormatPetitionSave,
		      changeMonth: true,
		  	  changeYear: true, 
		      onSelect: function (selected) {
		            //var dt = new Date(selected);
		            var dt = $(this).datepicker('getDate', '+1d');
		            dt.setDate(dt.getDate() + 1);
		            $("#residenceEndDate"+id).datepicker("option", "minDate", dt);
		        }
		  })
		
	
		  $("#residenceEndDate").datepicker({
		      dateFormat: finalDateFormatPetitionSave,
		      changeMonth: true,
		  	  changeYear: true, 
		        onSelect: function (selected) {
		            //var dt = new Date(selected);
		            var dt = $(this).datepicker('getDate', '-1d');
		            dt.setDate(dt.getDate() - 1);
		            $("#residenceStartDate"+id).datepicker("option", "maxDate", dt);
		        }
		  })
		  // 
		  
		  /* $('#jobTitleValues').autocomplete({
				serviceUrl:  sourceleadUrl+immiModulePath+'/getJobTitleAutoComplete',
				paramName: "searchValue",
				delimiter: ",",
			   transformResult: function(response) {
				return {
				  //must convert json to javascript object before process
				  suggestions: $.map($.parseJSON(response), function(item) {
				      return { value: item.jobTitle, data: item.id };
				   })
				 };
		      }
		}); */
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
	 <script>
	/*  $(document).on("change", "#candidates", function() {
		alert(this.val()) 
	 }); */
	 //$( "#myselect option:selected" ).val()
	 function updateCandiateInfo(obj){
		var candidateObj=candidateByOrgObject[obj.val()];
		
		$("#firstName").val(candidateObj.firstName);
		$("#lastName").val(candidateObj.lastName);
		$("#middleName").val(candidateObj.middelName);
		$("#email").val(candidateObj.email);
		$("#mobile").val(candidateObj.mobilePhone);
		$("#alternateMobile").val(candidateObj.alternateMobilePhone);
		
		// gender radio button  
		$('input[name="gender"]').prop('checked', false); // reset the value 
		if(typeof candidateObj.gender !='undefined' && candidateObj.gender!=''){  // set the value if not empty
		$('input:radio[name=gender][value='+candidateObj.gender+']').click();
		}
		//  radio button  maritalStatus
		$('input[name="maritalStatus"]').prop('checked', false); // reset the value 
	
		if(typeof candidateObj.maritalStatus !='undefined' && candidateObj.maritalStatus!=''){ // set the value if not empty
		$('input:radio[name=maritalStatus][value='+candidateObj.maritalStatus+']').click();
		}
		// set the residenc details
		$("#country").val(candidateObj.country);
		$("#state").val(candidateObj.state);
		$("#city").val(candidateObj.city);
		$("#address").val(candidateObj.address);
		$("#zipcode").val(candidateObj.zipCode);
		$("#location").val(candidateObj.location);
		$("#experienceYears").val(candidateObj.experienceYears);
		$("#experienceMonths").val(candidateObj.experienceMonths);
		
		// setting the date for the date of birth field.
		if(typeof  candidateObj.dateOfBirth != 'undefined' && candidateObj.dateOfBirth!=''){
			var dateOfBirth =  moment(candidateObj.dateOfBirth).format("DD-MM-YYYY");
		   $("#birthDate").val(dateOfBirth);
		}
	 }
	 $(function() {
			$("#uploadBtn").change(function() {
				fileUpload("uploadBtn", "immigrationPetition", "petitionDetailsForm");
			});
		 });
		 document.getElementById("uploadBtn").onchange = function () {
		     $('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''));
		 };
	     
	     $("#clearUploads").click(function(){
	    	 $("#uploadFile").val("");
	     });
	 </script>
	