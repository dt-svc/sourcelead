<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<c:set var = "immigrationDTO" scope = "page" value = '<%= session.getAttribute("immigrationDTO")%>'/>
<script type="text/javascript" src="${SourceLeadWeb_vmsUrl}/resources/js/immigration.js?staticTime=${staticTime}"></script>
<section class="addImmiWrap">
	<div class="addImmiInnWrap">
		<!-- <div class="immiStepsHead clearfix"> removed steps process.
			<h2>1. Petition detail</h2>
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
		</div> -->
		
		<div class="addImmiContent clearfix">
			<form id="petitionDetailsForm" name="petitionDetailsForm">
			<c:if test="${empty immigrationDTO}">
			<input type = "hidden" id="editMode" name = "editMode" value = "false">
			</c:if>
			<c:if test="${!empty immigrationDTO}">
			<input type = "hidden" id="editMode" name = "editMode" value = "true">
			</c:if>
          <div class="col-md-12 col-lg-12 myForm margin-top0">
          	<input type="hidden" name="immigrationId" id="immigrationId" value="${immigrationDTO.immigrationId}">
          	<input type="hidden" name="candidateId"  value="${immigrationDTO.candidateId}">
          	<input type="hidden" name="templateId" value="${immigrationDTO.templateId}">
          	<input type="hidden" name="lcaId" id="currLcaId" value="${immigrationDTO.lcaId}">
          	<input type="hidden" name="prevLcaId" id="prevLcaId" value="${immigrationDTO.prevLcaId}">
          	
            <h4 class="col-md-12 mb clearfix">1. Petition detail</h4>
            <div class="clearfix"></div>         
         	<div class="form-group">
              <label class="col-md-4">Petition type<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-8 dropdown-select">
                  <select id="petitionType" name="petitionType" class="chosen-select form-control	" field-name="Petition type" data-validation="required">
                  </select>
              </div>
          </div> 
			<div class="form-group">
              <label class="col-md-4">Process type<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-8 dropdown-select">
                  <select id="processType" name="processType" class="form-control	" field-name="Process type" data-validation="required">
                  <option selected value="">--Select Type --</option>
                  <option ${immigrationDTO.processType == 'Normal' ? 'selected': ''} value='Normal' >Normal (Regular)</option>
                  <option ${immigrationDTO.processType == 'Premium' ? 'selected': ''} value='Premium' >Premium</option>
                  </select>
              </div>
          	</div> 
            <div class="form-group">
              <label class="col-md-4">Open date<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-8">
               <div class="input-group errorInptgroup">
                <input type="text" id ="openDate" class="form-control calendar" value="${immigrationDTO.openDate}" name="openDate" readonly='true' placeholder="Open Date" field-name="Open date" data-validation="required"/>
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('openDate');"></i></span>
              </div>
            </div>
            </div>
            
            
             <div class="form-group">
              <label class="col-md-4">Receipt No</label>
              <div class="col-md-8">
					<input type="text" class="form-control" value="${immigrationDTO.receiptNo}" name="receiptNo" id="receiptNo" data-validation="validate_Space validate_length length2-20" data-validation-optional="true">
              </div>
          </div>
            
            
            <div class="form-group">
              <label class="col-md-4">Status<span class="text-danger paddingLeft">*</span></label> <!-- renamed form Current USCIS as Status as per request change -->
              <div class="col-md-8 dropdown-select">
                <select id="currUscisStatus" name="currUscisStatus" onchange="onLoadStatusChange();" field-name="Status" data-validation="required" class="chosen-select form-control">
                    
                  </select>
              </div>
            </div>
		
            <div class="form-group">
              <label class="col-md-4">Date of arrival</label>
              <div class="col-md-8">
               <div class="input-group">
                <input type="text" id="arrivalDate" readonly='true' class="form-control calendar" value="${immigrationDTO.arrivalDate}" name="arrivalDate" placeholder="Date of arrival"/>
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('arrivalDate');"></i></span>
              </div>
              </div>
            </div>                        
            
            <div class="form-group">
          		<label class="col-md-4">Place of entry<span class="text-danger">&nbsp;*</span></label>
         			 <div class="col-md-8" id="locationField">
          			<div class="positionRelative">
          			<input class="form-control positionRelative" id="entryPlace" value="${immigrationDTO.entryPlace}" name="entryPlace" onclick="initLocationAuto('entryPlace');" placeholder="Enter your Location"  type="text" field-name="Place Of Entry" data-validation="required"></input>
          				<i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onclick="refreshGoogleApiLocation('entryPlace');"></i>
         				 </div>
        			  </div>
       			 </div>
            
            
            <div class="form-group">
              <label class="col-md-4">Choose Template<span class="text-danger paddingLeft">*</span></label>
              <c:if test="${empty immigrationDTO}">
              <div class="col-md-8" id="">
               <select class="chosen-select form-control" id="templateSelect" name="templateId" data-placeholder="Choose Template" field-name="Template"  data-validation="required">
               	
               	<option value="" selected>--Choose Template--</option>
							<c:forEach var="item" items="${templateList}">
											<option ${immigrationDTO.templateId == item.id ? 'selected': ''} value="${item.id}">${item.name}(${item.country})</option>
							</c:forEach>
               </select>
              </div>
              </c:if>
              <c:if test="${!empty immigrationDTO}">
              <c:if test="${immigrationDTO.orgloationId != SourceLeadWeb_orgLocations.id}">
               <div class="col-md-8">
              <input class="form-control" type="text" readonly name="templateId" id="templateId" value="${immigrationDTO.templateName}"/>
				<input type="hidden" id="templateId" name="templateId" value="${immigrationDTO.templateId}"/>
				</div>			
              </c:if>
              
               <c:if test="${immigrationDTO.orgloationId == SourceLeadWeb_orgLocations.id}">
              <c:if test="${immigrationDTO.isOwnTemplateId == 'Y'}">
              <div class="col-md-8" id="templateDIVId">
               <select class="chosen-select form-control" id="templateSelect" name="templateId" data-placeholder="Choose Template" field-name="Template"  data-validation="required">
               	
               	<option value="" selected>--Choose Template--</option>
							<c:forEach var="item" items="${templateList}">
											<option ${immigrationDTO.templateId == item.id ? 'selected': ''} value="${item.id}">${item.name}</option>
							</c:forEach>
               </select>
              </div>
              </c:if>
               <c:if test="${immigrationDTO.isOwnTemplateId == 'N'}">
               <div class="col-md-8">
               <input class="form-control" type="text" readonly name="templateId" id="templateId" value="${immigrationDTO.templateName}"/>
				<input type="hidden" id="templateId" name="templateId" value="${immigrationDTO.templateId}"/>
			</div>
               </c:if>
              </c:if>
              </c:if>
            </div>
          
            <div class="clearfix"></div>
            <c:if test="${!empty immigrationDTO}">
            <c:if test="${immigrationDTO.orgloationId != SourceLeadWeb_orgLocations.id}">
             <h4 class="col-md-12 mb">2. Company Details</h4>
            </c:if>
            <c:if test="${immigrationDTO.orgloationId == SourceLeadWeb_orgLocations.id}">
             <h4 class="col-md-12 mb">2. Attorney Details</h4>
            </c:if>
            </c:if>
            <c:if test="${empty immigrationDTO}">
            <c:choose>
				<c:when test="${SourceLeadWeb_orgLocations.isLawFirm == 'Y'}">
					 <h4 class="col-md-12 mb">2. Company Details</h4>
				</c:when>
				<c:otherwise>
					  <h4 class="col-md-12 mb">2. Attorney Details</h4>
				</c:otherwise>
			</c:choose>
           
            </c:if>
            <div class="clearfix"></div>
							<div class="form-group">
							 <c:if test="${!empty immigrationDTO}">
								<c:if test="${immigrationDTO.orgloationId != SourceLeadWeb_orgLocations.id}">
								<label class="col-md-4"><span class="pull-left">Company Name</span><span
									class="text-danger pull-left" id="man1">*</span></label>
								</c:if>
								<c:if test="${immigrationDTO.orgloationId == SourceLeadWeb_orgLocations.id}">
								<label class="col-md-4"><span class="pull-left">Attorney Name</span><span
									class="text-danger pull-left" id="man1">*</span></label>
								</c:if>
									</c:if>
									 <c:if test="${empty immigrationDTO}">
									 <c:choose>
									 <c:when test="${SourceLeadWeb_orgLocations.isLawFirm == 'Y'}">
									 <label class="col-md-4"><span class="pull-left">Company Name</span><span
									class="text-danger pull-left" id="man1">*</span></label>
									</c:when>
									<c:otherwise>
									 <label class="col-md-4"><span class="pull-left">Attorney Name</span><span
									class="text-danger pull-left" id="man1">*</span></label>
									</c:otherwise>
									</c:choose>
									
									 </c:if>
									 
								<div class="col-md-8 chooRadius" id="errorAttorney">
								
								
								<c:if test="${!empty immigrationDTO}">
								<c:if test="${immigrationDTO.orgloationId != SourceLeadWeb_orgLocations.id}">
									<input class="form-control" type="text" readonly name="attorneyId" id="attorneyId" value="${immigrationDTO.orgName}"/>
									<input type="hidden" id="attorneyId" name="attorneyId" value="${immigrationDTO.attorneyId}"/>
								</c:if>
								<c:if test="${immigrationDTO.orgloationId == SourceLeadWeb_orgLocations.id}">
								<div class="input-group col-md-10 pull-left chooRadius errorInptgroup">
									<select class="chosen-select form-control" name="attorneyId"
										id="attorneyId" placeholder="Select Attorney" field-name="Attorney Name" data-validation="required"
										onchange="getAttorneyContactList();removeValidationAttorney();">
									</select>
								 	<span class="input-group-addon cursorPointer bg-blue" onclick="getAllRelationssByOrgLocation();addCandiadateEmptyAttorneyContact();"><i class="fa fa-refresh "></i></span>
							 	</div>
							 	<span class="col-md-2 noPad clearfix">
							<a href="${SourceLeadWeb_vmsUrl}/addVendor" target="_blank"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a> 
							</span> 
								</c:if>
								</c:if>
								<c:if test="${empty immigrationDTO}">
								<div class="input-group col-md-10 pull-left chooRadius errorInptgroup">
								<select class="chosen-select form-control" name="attorneyId"
										id="attorneyId" placeholder="Select Attorney" field-name="Attorney Name" data-validation="required"
										onchange="getAttorneyContactList();removeValidationAttorney();">
									</select>
								<span class="input-group-addon cursorPointer bg-blue" onclick="getAllRelationssByOrgLocation();addCandiadateEmptyAttorneyContact();"><i class="fa fa-refresh "></i></span>
							</div>
							<span class="col-md-2 noPad clearfix">
							<a href="${SourceLeadWeb_vmsUrl}/addVendor" target="_blank"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a> 
							</span>
								</c:if>
								
							
							</div>
							</div>

							<div class="form-group">
							<c:if test="${!empty immigrationDTO}">
							<c:if test="${immigrationDTO.orgloationId != SourceLeadWeb_orgLocations.id}">
							<label class="col-md-4"><span class="pull-left">Company Contact</span><span class="text-danger pull-left" id="man2">*</span></label>
							
							</c:if>
							<c:if test="${immigrationDTO.orgloationId == SourceLeadWeb_orgLocations.id}">
							<label class="col-md-4"><span class="pull-left">Attorney Contact</span><span class="text-danger pull-left" id="man2">*</span></label>
							
							</c:if>
							</c:if>
							<c:if test="${empty immigrationDTO}">
							<c:choose>
								<c:when test="${SourceLeadWeb_orgLocations.isLawFirm == 'Y'}">
									 <label class="col-md-4"><span class="pull-left">Company Contact</span><span class="text-danger pull-left" id="man2">*</span></label>
								</c:when>
								<c:otherwise>
									  <label class="col-md-4"><span class="pull-left">Attorney Contact</span><span class="text-danger pull-left" id="man2">*</span></label>
								</c:otherwise>
							</c:choose>
							
							</c:if>
							
							<c:if test="${!empty immigrationDTO}">
							<c:if test="${immigrationDTO.orgloationId != SourceLeadWeb_orgLocations.id}">
							<div class="col-md-8">
							<input class="form-control" type="text" readonly name="attorneyContact" id="attorneyContact" value="${immigrationDTO.attorneyContactName}"/>
									<input type="hidden" id="attorneyContact" name="attorneyContact" value="${immigrationDTO.attorneyContact}"/>
							</div>
							</c:if>
							<c:if test="${immigrationDTO.orgloationId == SourceLeadWeb_orgLocations.id}">
								<div class="col-md-8 chooRadius" id="errorAttorneyContact">
								<div class="input-group col-md-10 pull-left chooRadius errorInptgroup">
									<select class="chosen-select form-control" name="attorneyContact" id="attorneyContact" placeholder="Select Attorney Contact" field-name="Attorney Contact"
										 data-validation="required" onchange="removeValidationAttorneyContact();">
									</select>
                  <span class="input-group-addon cursorPointer bg-blue" onclick="getAllRelationssByOrgLocation();addCandiadateEmptyAttorneyContact();"><i class="fa fa-refresh "></i></span>
			</div>		
								<span class="col-md-2 noPad clearfix" onclick="selectedAttorney();">
								<a href="${SourceLeadWeb_vmsUrl}/addVendorContact" target="_blank"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a>
							 </span>			
										
								</div>
							</c:if>
							</c:if>
							<c:if test="${empty immigrationDTO}">
								<div class="col-md-8 chooRadius" id="errorAttorneyContact">
								<div class="input-group col-md-10 pull-left chooRadius errorInptgroup">
									<select class="chosen-select form-control" name="attorneyContact" id="attorneyContact" placeholder="Select Attorney Contact" field-name="Attorney Contact"
										 data-validation="required" onchange="removeValidationAttorneyContact();">
									</select>
                  <span class="input-group-addon cursorPointer bg-blue" onclick="getAllRelationssByOrgLocation();addCandiadateEmptyAttorneyContact();"><i class="fa fa-refresh "></i></span>
			</div>		
								<span class="col-md-2 noPad clearfix" onclick="selectedAttorney();">
								<a href="${SourceLeadWeb_vmsUrl}/addVendorContact" target="_blank"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a>
							 </span>			
										
								</div>
								</c:if>
							</div>
              
           <div class="clearfix"></div>
            	<h4 class="col-md-12 mb margin-top0">3. Beneficiary  details</h4>
           <div class="clearfix"></div>
			<c:if test="${empty immigrationDTO}">
             <div class="form-group">
								<label class="col-md-4"><span class="pull-left">Associate (Beneficiary)</span><span
									class="text-danger pull-left" id="man1">*</span></label>
								<div class="col-md-8 chooRadius" id="errorAttorney">
								<div class="input-group col-md-10 pull-left chooRadius errorInptgroup uiwidgetCustom">
									<input type="text" class="form-control" name="candidateNameEmail" field-name="Associate" data-validation="required"
										id="candidateNameEmail" placeholder="Beneficiary Autofill Enabled For 2 Chars" value="${immigrationDTO.candidateNameEmail}"/>
									<input type="hidden" id="candidateId" name="candidateId" value="${immigrationDTO.candidateId}"/>
								</div>
							<span class="col-md-2 noPad clearfix">
							<a href="${SourceLeadWeb_atsUrl}/userCreation" target="_blank"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a> 
							</span>	
							</div>
							</div>
					</c:if>	
					
					<c:if test="${!empty immigrationDTO}">
					<c:if test="${immigrationDTO.orgloationId != SourceLeadWeb_orgLocations.id}">
					  <div class="form-group">
					  <label class="col-md-4"><span class="pull-left">Associate (Beneficiary)</span><span
									class="text-danger pull-left" id="man1">*</span></label>
									<div class="col-md-8">
									<input class="form-control" type="text" disabled="disabled" name="candidateNameEmail" id="candidateNameEmail" value="${immigrationDTO.candidateNameEmail}" />
									<input type="hidden" id="candidateId" name="candidateId" value="${immigrationDTO.candidateId}"/>
						
						</div>	
							
					  </div>
					</c:if>
					<c:if test="${immigrationDTO.orgloationId == SourceLeadWeb_orgLocations.id}">
					<div class="form-group">
								<label class="col-md-4"><span class="pull-left">Associate (Beneficiary)</span><span
									class="text-danger pull-left" id="man1">*</span></label>
								<div class="col-md-8 chooRadius" id="errorAttorney">
								<div class="input-group col-md-10 pull-left chooRadius errorInptgroup">
									<input type="text" class="form-control" name="candidateNameEmail" value="${immigrationDTO.candidateNameEmail}" field-name="Associate" data-validation="required"
										id="candidateNameEmail" placeholder="Select Beneficiary" disabled="disabled"/>
									<input type="hidden" id="candidateId" name="candidateId" value="${immigrationDTO.candidateId}"/>
								</div>
							<span class="col-md-2 noPad clearfix">
							<a href="${SourceLeadWeb_sourceleadUrl}/userCreation" target="_blank" hidden="true"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a> 
							</span>
							</div>
							</div>
							
					</c:if>
					
					</c:if>	
		<!--for  New  Beneficiary  details -->	
		 <c:if test="${empty immigrationDTO}">                                                                                                                                   
         <div class="form-group">
              <label class="col-md-4">First Name<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-8">
					<input type="text" class="form-control" id="firstName" name="firstName" field-name="First Name" data-validation="required"/>
              </div>
          </div>
          <div class="form-group">
              <label class="col-md-4">Middle Name</label>
              <div class="col-md-8">
					<input type="text" class="form-control"  name="middleName" id="middleName" data-validation="required validate_Space validate_length length1-100" data-validation-optional="true">
              </div>
          </div> 
          <div class="form-group">
              <label class="col-md-4">Last Name<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-8">
					<input type="text" class="form-control" name="lastName" id="lastName"  field-name="Last Name" data-validation="required">
              </div>
          </div>  
         <div class="form-group">
						<label class="col-md-4">Gender</label>
						<div class="col-md-8" id= "errorGenderMsg">
						<label class="paddingRight"><input type="radio" ${immigrationDTO.gender == 'Male'? 'checked': '' } value="Male"  name="gender"><span class="mr5"></span>Male</label>
						<label class="paddingLeft paddingRight"><input type="radio" ${immigrationDTO.gender == 'Female'? 'checked': '' } value="Female" name="gender"><span class="mr5"></span>Female</label>
						<label class="paddingRight"><input type="radio" ${immigrationDTO.gender == 'Transgender'? 'checked': '' } value="Transgender" name="gender" ><span class="mr5"></span>Transgender</label>
						<!-- <input type="hidden"  id="genderValue" name="genderValue"/> --> 		
			</div>
			</div>
          <div class="form-group">
              <label class="col-md-4">Email<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-8">
					<input type="text" class="form-control" name="email"  id="email" field-name="Email" data-validation="required validate_email">
              </div>
          </div>
                      	
          <div class="form-group">
              <label class="col-md-4">Mobile</label>
              <div class="col-md-8">
					<input type="text" class="form-control" name="mobile" id="mobile"  data-validation="required validate_Space validate_phone validate_length length2-100" data-validation-optional="true">
              </div>
          </div>
          
           <div class="form-group">
              <label class="col-md-4">Alternative Mobile</label>
              <div class="col-md-8">
					<input type="text" class="form-control" name="alternateMobile" id="alternateMobile"  data-validation="required validate_Space validate_length validate_phone length2-100" data-validation-optional="true">
              </div>
          </div> 
            
           <div class="form-group">
              <label class="col-md-4">DOB<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-8">
					<div class="input-group errorInptgroup">
                <input type="text" class="form-control calendar" readonly='true' name="birthDate" id="birthDate"  placeholder="Date Of Birth" field-name="Date Of Birth" data-validation="required">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onClick="clearDateValue('birthDate');"></i></span>
              </div>
              </div>
          </div> 
           <div class="form-group">
              <label class="col-md-4">Place of Birth</label>
              <div class="col-md-8">
                   <div class="positionRelative">
          			<input class="form-control positionRelative" id="birthPlace" name="birthPlace"  onclick="initLocationAuto('birthPlace');" type="text" field-name="Place of Birth"></input>
          				<i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('birthPlace');"></i>
         			</div>
              </div>
            </div>   
          
            <div class="form-group">
              <label class="col-md-4">Nationality</label>
              <div class="col-md-8">
                  <div class="positionRelative">
          			<input class="form-control positionRelative" id="nationality" onclick="editOrgLocationAuto('nationality');" name="nationality"  placeholder="Select Country"  type="text"></input>
          				<i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('nationality');"></i>
         			</div>
              </div>
            </div> 
            <div class="form-group">
              <label class="col-md-4">Marital status</label>
              <div class="col-md-8">
                  <label class="radioIput paddingRight"><input type="radio" ${immigrationDTO.maritalStatus == 'Single'? 'checked': ''} value="Single" name="maritalStatus">Single</label>
                  <label class="radioIput paddingLeft"><input type="radio"  ${immigrationDTO.maritalStatus == 'Married'? 'checked': ''} value="Married"  name="maritalStatus">Married</label>
                  <label class="radioIput paddingLeft"><input type="radio"  ${immigrationDTO.maritalStatus == 'Divorced'? 'checked': ''} value="Divorced"  name="maritalStatus">Divorced</label>
                  <label class="radioIput paddingLeft"><input type="radio"   ${immigrationDTO.maritalStatus == 'Separated'? 'checked': ''} value="Separated"  name="maritalStatus">Separated</label>
              </div>
            </div>  
             </c:if>			
          <!--for  Edit  Beneficiary  details -->
         <c:if test="${!empty immigrationDTO}">                                                                                                                                   
         <div class="form-group">
              <label class="col-md-4">First Name<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-8">
					<input type="text" class="form-control" id="firstName" value="${immigrationDTO.firstName}" name="firstName" field-name="First Name" data-validation="required" readonly="readonly"/>
              </div>
          </div>
          <div class="form-group">
              <label class="col-md-4">Middle Name</label>
              <div class="col-md-8">
					<input type="text" class="form-control" value="${immigrationDTO.middleName}" name="middleName" id="middleName" data-validation="required validate_Space validate_length length1-100" data-validation-optional="true" readonly="readonly">
              </div>
          </div> 
          <div class="form-group">
              <label class="col-md-4">Last Name<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-8">
					<input type="text" class="form-control" name="lastName" id="lastName" value="${immigrationDTO.lastName}" field-name="Last Name" data-validation="required" readonly="readonly">
              </div>
          </div>  
         <div class="form-group">
						<label class="col-md-4">Gender</label>
						<div class="col-md-8" id= "errorGenderMsg">
						<label class="paddingRight"><input type="radio" ${immigrationDTO.gender == 'Male'? 'checked': '' } value="Male"  name="gender" readonly="readonly"><span class="mr5"></span>Male</label>
						<label class="paddingLeft paddingRight"><input type="radio" ${immigrationDTO.gender == 'Female'? 'checked': '' } value="Female" name="gender" readonly="readonly"><span class="mr5"></span>Female</label>
						<label class="paddingRight"><input type="radio" ${immigrationDTO.gender == 'Transgender'? 'checked': '' } value="Transgender" name="gender" readonly="readonly"><span class="mr5"></span>Transgender</label>
						<!-- <input type="hidden"  id="genderValue" name="genderValue"/> --> 		
			</div>
			</div>
          <div class="form-group">
              <label class="col-md-4">Email<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-8">
					<input type="text" class="form-control" name="email" value="${immigrationDTO.email}" id="email" field-name="Email" data-validation="required validate_email" readonly="readonly">
              </div>
          </div>
                      	
          <div class="form-group">
              <label class="col-md-4">Mobile</label>
              <div class="col-md-8">
					<input type="text" class="form-control" name="mobile" id="mobile" value="${immigrationDTO.mobile}" data-validation="required validate_Space validate_phone validate_length length2-100" data-validation-optional="true" readonly="readonly">
              </div>
          </div>
          
           <div class="form-group">
              <label class="col-md-4">Alternative Mobile</label>
              <div class="col-md-8">
					<input type="text" class="form-control" name="alternateMobile" id="alternateMobile" value="${immigrationDTO.alternateMobile}" data-validation="required validate_Space validate_length validate_phone length2-100" data-validation-optional="true" readonly="readonly">
              </div>
          </div> 
            
           <div class="form-group">
              <label class="col-md-4">DOB<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-8">
					<div class="input-group errorInptgroup">
                <input type="text" class="form-control calendar" readonly='true' name="birthDate" id="birthDate" value="${immigrationDTO.birthDate}" placeholder="Date Of Birth" field-name="Date Of Birth" data-validation="required" readonly="readonly">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onClick="clearDateValue('birthDate');"></i></span>
              </div>
              </div>
          </div> 
           <div class="form-group">
              <label class="col-md-4">Place of Birth</label>
              <div class="col-md-8">
                   <div class="positionRelative">
          			<input class="form-control positionRelative" id="birthPlace" name="birthPlace" value="${immigrationDTO.birthPlace}" onclick="initLocationAuto('birthPlace');" type="text" field-name="Place of Birth" readonly="readonly"></input>
          				<i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('birthPlace');"></i>
         			</div>
              </div>
            </div>   
          
            <div class="form-group">
              <label class="col-md-4">Nationality</label>
              <div class="col-md-8">
                  <div class="positionRelative">
          			<input class="form-control positionRelative" id="nationality" onclick="editOrgLocationAuto('nationality');" name="nationality" value="${immigrationDTO.nationality}" placeholder="Select Country"  type="text" readonly="readonly"></input>
          				<i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('nationality');"></i>
         			</div>
              </div>
            </div> 
            <div class="form-group">
              <label class="col-md-4">Marital status</label>
              <div class="col-md-8">
                  <label class="radioIput paddingRight"><input type="radio" ${immigrationDTO.maritalStatus == 'Single'? 'checked': ''} value="Single" name="maritalStatus" readonly="readonly">Single</label>
                  <label class="radioIput paddingLeft"><input type="radio"  ${immigrationDTO.maritalStatus == 'Married'? 'checked': ''} value="Married"  name="maritalStatus" readonly="readonly">Married</label>
                  <label class="radioIput paddingLeft"><input type="radio"  ${immigrationDTO.maritalStatus == 'Divorced'? 'checked': ''} value="Divorced"  name="maritalStatus" readonly="readonly">Divorced</label>
                  <label class="radioIput paddingLeft"><input type="radio"   ${immigrationDTO.maritalStatus == 'Separated'? 'checked': ''} value="Separated"  name="maritalStatus" readonly="readonly">Separated</label>
              </div>
            </div>  
             </c:if>
             <div class="form-group">
              <label class="col-md-4">SSN</label>
              <div class="col-md-8">
                <input type="text" class="form-control" name="ssnNumber" id="ssnNumber" value="${immigrationDTO.ssnNumber}" placeholder="000-00-0000" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true">
              </div>
            </div>
             <div class="form-group">
              <label class="col-md-4">I-94 Number</label>
              <div class="col-md-8">
                <input type="text" class="form-control" name="i_94_number" id ="i_94_number" value="${immigrationDTO.i_94_number}" placeholder="XXXXXXXXX-XX" data-validation=" validate_Space validate_length length12" data-validation-optional="true">
              </div>
            </div>
           
             <div class="form-group">
              <label class="col-md-4">I-20 SEVIS Number If Student</label>
              <div class="col-md-8">
					<input type="text" class="form-control" name="i_20_number"   placeholder="N-XXXXXXXXXX" id="i_20_number" value="${immigrationDTO.i_20_number}" data-validation="" data-validation-optional="true">
              </div>
            </div>
                         
            <div class="form-group">
              <label class="col-md-4">EAD Number If Student</label>
              <div class="col-md-8">
                <input type="text" class="form-control" name="eadNumber" placeholder="000-000-000"  id="eadNumber" value="${immigrationDTO.eadNumber}" placeholder="" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true">
              </div>
            </div>
            
             <c:if test="${empty immigrationDTO.prevLcaNumber}">
            <div class="form-group">
              <label class="col-md-4">Previous LCA Number</label>
              <div class="col-md-8">
                <input type="text" class="form-control" name="prevLcaNumber" id="prevLcaNumber" value="${immigrationDTO.prevLcaNumber}" placeholder="Previous LCA Number" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true" >
              </div>
            </div>
            </c:if>
            
            <c:if test="${!empty immigrationDTO.prevLcaNumber}">
            <div class="form-group">
              <label class="col-md-4">Previous LCA Number</label>
              <div class="col-md-8">
                <input type="text" class="form-control" name="prevLcaNumber" id="prevLcaNumber" value="${immigrationDTO.prevLcaNumber}" placeholder="I-XXX-XXXXX-XXXXXX" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true" disabled="disabled">
              </div>
            </div>
            </c:if>
            
             <c:if test="${empty immigrationDTO.prevLcaStartDate}">
            <div class="form-group">
              <label class="col-md-4">Previous LCA Start Date</label>
              <div class="col-md-8">
               <div class="input-group">
                <input class="form-control calendar"  id="prevLcaStartDate" name="prevLcaStartDate" value="${immigrationDTO.prevLcaStartDate}" placeholder="Previous LCA Start Dates" type="text">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('prevLcaStartDate');"></i></span>
            </div>
              </div>
            </div>
           </c:if>
           
            <c:if test="${!empty immigrationDTO.prevLcaStartDate}">
            <div class="form-group">
              <label class="col-md-4">Previous LCA Start Date</label>
              <div class="col-md-8">
               <div class="input-group">
                <input class="form-control calendar"  id="prevLcaStartDate" name="prevLcaStartDate" value="${immigrationDTO.prevLcaStartDate}" placeholder="Previous LCA Start Dates" type="text" disabled="disabled">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('prevLcaStartDate');"></i></span>
            </div>
              </div>
            </div>
           </c:if>
           
            <c:if test="${empty immigrationDTO.prevLcaEndDate}">
           <div class="form-group">
              <label class="col-md-4">Previous LCA End date</label>
              <div class="col-md-8">
               <div class="input-group">
                <input class="form-control calendar"  id="prevLcaEndDate" name="prevLcaEndDate" value="${immigrationDTO.prevLcaEndDate}" placeholder="Previous LCA End date" type="text">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('prevLcaEndDate');"></i></span>
            </div>
              </div>
            </div>
            </c:if> 
            
            <c:if test="${!empty immigrationDTO.prevLcaEndDate}">
           <div class="form-group">
              <label class="col-md-4">Previous LCA End date</label>
              <div class="col-md-8">
               <div class="input-group">
                <input class="form-control calendar"  id="prevLcaEndDate" name="prevLcaEndDate" value="${immigrationDTO.prevLcaEndDate}" placeholder="Previous LCA End date" type="text" disabled="disabled">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('prevLcaEndDate');"></i></span>
            </div>
              </div>
            </div>
            </c:if> 
                                            
                                            
           <div class="form-group" >
              <label class="col-md-4">Upload Resume</label><!-- need to updated  -->
              <div class="col-md-3 mt10" id="docNameDiv" style="display: none;">
                    <label id="documentName" class="text-primary tbldata-dotdot-vsm pull-left" title=""></label>&nbsp;&nbsp;<span id="delete_file" onclick = "deleteResumeDoc()"><i class="fa fa-trash removeIcon"></i></span>
                   <input  type="hidden" id="generalExisitingFile" name = "" value="">
                   <a  class="glyphicon glyphicon-download-alt" id="downloadBrowseFile" href="" download title="Download"></a>
             </div>
               <div class="col-md-8"  id="resumeEmpty">
				<div class="input-group col-md-12 clearUploads" id="clearUploads" style="margin:0 auto">
					<label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
					  <input type="file" style="display: none;" id="uploadBtn" class="upload">
					  </span></label>
					<input type="text" class="form-control" name="docFile" readonly id="uploadFile" placeholder="Attachment">
				  </div>
              </div>
            </div>
             <div class="form-group">
              <label class="col-md-4">Experience</label>
              <div class="col-md-4 dropdown-select">
                <select id="experienceYears" name="experienceYears">
                	<option value="">--Select Year--</option>
                	<c:forEach begin="0" end="40" varStatus="loop">
				    	<option ${immigrationDTO.experienceYears == loop.index ? 'selected' : ''} value="${loop.index}" >${loop.index}</option>
					</c:forEach>
                	<%-- <option ${immigrationDTO.experienceYears == '40' ? 'selected' : ''} value="" >40</option> --%>
                </select>
              </div>
             <div class="col-md-4 dropdown-select">
                <select id="experienceMonths" name="experienceMonths">
                	<option value="">--Select Month--</option>
                	<c:forEach begin="0" end="11" varStatus="loop">
				    	<option value="${loop.index}"  ${immigrationDTO.experienceMonths == loop.index ? 'selected' : ''} >${loop.index}</option>
					</c:forEach>
                </select>
              </div>
            </div> 
              
           <div class="clearfix"></div>
            	<h4 class="col-md-12 mb margin-top0">4. Job Details</h4>
           <div class="clearfix"></div>
          
             <div class="form-group">
              <label class="col-md-4">Job Title<span class="text-danger paddingLeft">*</span></label>
              <c:if test="${empty immigrationDTO}">
              <div class="col-md-8">
               <select id="jobId" name="jobId" class="chosen-select form-control" field-name="Job Title" data-validation="required">
                  </select>
              </div>
              </c:if>
               <c:if test="${!empty immigrationDTO}">
               <c:if test="${immigrationDTO.orgloationId != SourceLeadWeb_orgLocations.id}">
                <div class="col-md-8">
               <input class="form-control" type="text" readonly name="jobId" id="jobId" value="${immigrationDTO.jobTitle}"/>
				<input type="hidden" id="jobId" name="jobId" value="${immigrationDTO.jobId}"/>
				</div>		
               </c:if>
                <c:if test="${immigrationDTO.orgloationId == SourceLeadWeb_orgLocations.id}">
               <div class="col-md-8">
               <select id="jobId" name="jobId" class="chosen-select form-control" field-name="Job Title" data-validation="required">
                  </select>
              </div>
               </c:if>
               </c:if>
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
                <input type="text"  class="form-control" value="${immigrationDTO.employer}" name="employer" id="employer" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true">
              </div>
            </div>
            
            <div class="form-group">
              <label class="col-md-4">Location</label>
              <div class="col-md-8">
                 <div class="positionRelative">
          			<input class="form-control positionRelative" id="jobLocation" value="${immigrationDTO.jobLocation}" name="jobLocation" onclick="initLocationAuto('jobLocation');" placeholder="Enter your Location"  type="text"></input>
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
										<option value="INR" ${immigrationDTO.rateCurrency == 'INR' ? 'selected' : ''}>INR</option>
										<option value="AUD" ${immigrationDTO.rateCurrency == 'AUD' ? 'selected' : ''}>AUD</option>
										<option value="USD" ${immigrationDTO.rateCurrency == 'USD' ? 'selected' : ''}>USD</option>
										<option value="GBP" ${immigrationDTO.rateCurrency == 'GBP' ? 'selected' : ''}>GBP</option>
										<option value="SGD" ${immigrationDTO.rateCurrency == 'SGD' ? 'selected' : ''}>SGD</option>
										<option value="EUR" ${immigrationDTO.rateCurrency == 'EUR' ? 'selected' : ''}>EUR</option> 
									</select>

								</div>
								<input type="text" id="rate" name="rate"
									field-name="Current Rate"
									data-validation="validate_Space validate_float validate_length length1-8"
									data-validation-optional="true" value="${immigrationDTO.rate}" class="form-control">
								<div class="input-group-btn tblminwidth-75 ratetype">
									<select class="selectpicker form-control" id="rateType"
										name="rateType" field-name="Rate Per">
										 <option ${immigrationDTO.rateType == 'Rate Per' ? 'selected' : ''} value="Rate Per">Rate Per</option>
										<option ${immigrationDTO.rateType == 'Hourly' ? 'selected' : ''} value="Hourly">Hourly</option>
										<option ${immigrationDTO.rateType == 'Weekly' ? 'selected' : ''} value="Weekly">Weekly</option>
										<option ${immigrationDTO.rateType == 'Bi-Weekly' ? 'selected' : ''} value="Bi-Weekly">Bi-Weekly</option>
										<option ${immigrationDTO.rateType == 'Semi-Monthly' ? 'selected' : ''} value="Semi-Monthly">Semi-Monthly</option>
										<option ${immigrationDTO.rateType == 'Monthly' ? 'selected' : ''} value="Monthly">Monthly</option>
										<option ${immigrationDTO.rateType == 'Bi-Monthly' ? 'selected' : ''} value="Bi-Monthly">Bi-Monthly</option>
										<option ${immigrationDTO.rateType == 'Yearly' ? 'selected' : ''} value="Yearly">Yearly</option> 
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
                <input type="text" class="form-control calendar" readonly='true' id="jobStartDate" value="${immigrationDTO.jobStartDate}" name="jobStartDate" placeholder="Start Date">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('jobStartDate');"></i></span>
            </div>
              </div>
            </div>
            
            <div class="form-group">
              <label class="col-md-4">End Date</label>
              <div class="col-md-8">
               <div class="input-group">
                <input type="text" class="form-control calendar" readonly='true' value="${immigrationDTO.jobEndDate}" name="jobEndDate" id="jobEndDate" placeholder="End Date">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('jobEndDate');"></i></span>
            </div>
              </div>
            </div>
            
             <c:if test="${empty immigrationDTO.lcaNumber}">
             <div class="form-group">
              <label class="col-md-4">LCA Number</label>
              <div class="col-md-8">
                 <input type="text" class="form-control"  name="lcaNumber" id="lcaNumber" value="${immigrationDTO.lcaNumber}" placeholder="LCA Number" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true" onkeypress="selectBeneficiary();">
              </div>
            </div>
            </c:if>
            
             <c:if test="${!empty immigrationDTO.lcaNumber}">
             <div class="form-group">
              <label class="col-md-4">LCA Number</label>
              <div class="col-md-8">
                 <input type="text" class="form-control" value="${immigrationDTO.lcaNumber}" name="lcaNumber" id="lcaNumber" placeholder="LCA Number" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true" onkeypress="selectBeneficiary();" disabled="disabled">
              </div>
            </div>
            </c:if>
            
             <c:if test="${empty immigrationDTO.lcaStartDate}">
            <div class="form-group">
              <label class="col-md-4">LCA Start date</label>
              <div class="col-md-8">
               <div class="input-group">
                <input type="text" class="form-control calendar" id="lcaStartDate"  name="lcaStartDate" placeholder="LCA Start date">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('lcaStartDate');"></i></span>
            </div>
              </div>
            </div>
            </c:if>
            
             <c:if test="${!empty immigrationDTO.lcaStartDate}">
            <div class="form-group">
              <label class="col-md-4">LCA Start date</label>
              <div class="col-md-8">
               <div class="input-group">
                <input type="text" class="form-control calendar" id="lcaStartDate" value="${immigrationDTO.lcaStartDate}" name="lcaStartDate" placeholder="LCA Start date" disabled="disabled">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('lcaStartDate');"></i></span>
            </div>
              </div>
            </div>
            </c:if>
            
             <c:if test="${empty immigrationDTO.lcaEndDate}">
            <div class="form-group">
              <label class="col-md-4">LCA End date</label>
              <div class="col-md-8">
               <div class="input-group">
                <input type="text" class="form-control calendar"   id="lcaEndDate" name="lcaEndDate" placeholder="LCA End date">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('lcaEndDate');"></i></span>
            </div>
              </div>
            </div>
            </c:if>
            
             <c:if test="${!empty immigrationDTO.lcaEndDate}">
            <div class="form-group">
              <label class="col-md-4">LCA End date</label>
              <div class="col-md-8">
               <div class="input-group">
                <input type="text" class="form-control calendar"  value="${immigrationDTO.lcaEndDate}" id="lcaEndDate" name="lcaEndDate" placeholder="LCA End date" disabled="disabled">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('lcaEndDate');"></i></span>
            </div>
              </div>
            </div>
            </c:if>
            
             <c:if test="${empty immigrationDTO.lcaCurrLocation}">
            <div class="form-group">
              <label class="col-md-4">LCA Current Location</label>
              <div class="col-md-8">
                  <div class="positionRelative">
								<input class="form-control positionRelative"
									id="lcaCurrLocation" name="lcaCurrLocation"  
									placeholder="Enter LCA Current Location" type="text" field-name="LCA Current Location" ></input> <i
									class="fa fa-refresh btn btn-primary refresh-icon"
									title="Refresh"
									></i>
					</div>
              </div>
            </div>
            </c:if>
            
             <c:if test="${!empty immigrationDTO.lcaCurrLocation}">
            <div class="form-group">
              <label class="col-md-4">LCA Current Location</label>
              <div class="col-md-8">
                  <div class="positionRelative">
								<input class="form-control positionRelative"
									id="lcaCurrLocation" name="lcaCurrLocation" value="${immigrationDTO.lcaCurrLocation}" 
									placeholder="Enter LCA Current Location" type="text" field-name="LCA Current Location"  disabled="disabled"></input> <i
									class="fa fa-refresh btn btn-primary refresh-icon"
									title="Refresh"
									></i>
					</div>
              </div>
            </div>
            </c:if>

           <div class="clearfix"></div>
           	<div class="col-md-12 mb margin-top0">
            	<h4 class="pull-left">5. Residence</h4>
            	<button type="button" class="btn btn-default pull-right" onclick="appendDynamicResidenceDiv();"><i class="fa fa-plus paddingRight"></i>Add Residence</button>
            	</div>
           <div class="clearfix"></div>
            
            <div class="form-group">
              <label class="col-md-4">Location<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-8">
                  <div class="positionRelative">
								<input class="form-control positionRelative"
									id="location" name="location" value="${immigrationDTO.location}" onclick="initLocationAuto('location','country','state','city','zipcode','','','','address');"
									placeholder="Enter Location" type="text" field-name="Location" data-validation="required"></input> <i
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
								name="address" value="${immigrationDTO.address}" field-name="Street Address" placeholder="Enter Street Addres"
								data-validation="validate_Space validate_length length2-250"
								data-validation-optional="true">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-4">City</label>
              <div class="col-md-8">
                  <input class="form-control" id="city" value="${immigrationDTO.city}" name="city" placeholder="City" type="text"></input>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-4">State</label>
              <div class="col-md-8">
                  <input class="form-control" id="state" name="state" value="${immigrationDTO.state}" placeholder="State" type="text" ></input>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-4">Country</label>
              <div class="col-md-8">
                  <input class="form-control" id="country" name="country" placeholder="Country" value="${loginCountryName == null ? immigrationDTO.country : loginCountryName}" type="text" ></input>
              </div>
            </div>                                                                                               
		    <div class="form-group">
              <label class="col-md-4">Zip Code</label>
              <div class="col-md-8">
                  <input class="form-control" value="${immigrationDTO.zipcode}" id="zipcode" name="zipcode"
								placeholder="Zip code" type="text" data-validation="validate_Space validate_length validate_zipcode length2-8" data-validation-optional="true"></input>
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
              <label class="col-md-4">Start Date<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-8">
					<div class="input-group errorInptgroup">
                <input type="text" class="form-control calendar" readonly='true' id="residenceStartDate" value="${immigrationDTO.residenceStartDate}" name="residenceStartDate" placeholder="Start Date" field-name="Start Date" data-validation="required">
                <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('residenceStartDate')"></i></span>
              </div>
              </div>
          </div>

         <div id ="dynamicResidenceGroupDiv" ><!--  dynamic div start -->
          
         </div> <!--  dynamic div end -->
		<div class="clearfix"></div>
		<div class="addImmiFoot">
		<div class="pull-right">
			<!--<a href="javascript:void(0)" class="pull-left">Prev</a>-->
			<input type="button" value="Save & Continue" id="savePageOneContinue" onclick="immigration.savePageAndContinue('saveContinue');" class="btn btn-primary">
			<input type="button" value="Send Request" id="sendRequestButton" onclick="immigration.savePageAndContinue('sendRequest');" class="btn btn-primary">
			<input type="button" value="Save As Draft" id="saveAsDraftButton" onclick="immigration.savePageAndContinue('save');" class="btn btn-primary">
			<div class="clearfix"></div>
		</div>
		</div>
	</div>
	</form>
   </div>
   </div>
   </section>
   
       <script type="text/javascript">
       var vmsUrl ='${SourceLeadWeb_vmsUrl}';
       var immiModulePath = "/immigration";
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				});
			
			$(document).on('change','#zipcode', function () {
				if (this.value.match(/[^a-zA-Z0-9 ]/g)) {
					this.value = this.value.replace(/[^a-zA-Z0-9 ]/g, '');
				}
			});
</script>

   
    <script type="text/javascript">
    //var isValidBeneficiary=false;
    function prepareDynamicResidenceDiv(rowId,residenceVal){
    	var prepareResidencediv ='';
	   prepareResidencediv =prepareResidencediv+ "<div id ='dynamicResidenceDiv"+rowId+"'>";
	   prepareResidencediv =prepareResidencediv+ "   <div class='clearfix'></div>";
	   prepareResidencediv =prepareResidencediv+ "     	<div class='col-md-12 mb margin-top0'>";
	   prepareResidencediv =prepareResidencediv+ "   <h4 class='pull-left'>Residence</h4> <button type='button' class='btn btn-default pull-right' onclick='removeDynamicResidenceDiv("+rowId+","+(typeof residenceVal[0]==="undefined"?"":residenceVal[0])+")'><i class='fa fa-remove text-danger paddingRight'></i>Remove Residence</button>";
	   prepareResidencediv =prepareResidencediv+ "   </div><input type='hidden' name='residenceList["+rowId+"].id' value='"+(typeof residenceVal[0]==="undefined"?"":residenceVal[0])+"'>";
	   prepareResidencediv =prepareResidencediv+ "   <div class='clearfix'></div>";
	   prepareResidencediv =prepareResidencediv+ " <div class='form-group'>";
	   prepareResidencediv =prepareResidencediv+ "     <label class='col-md-4'>Location<span class='text-danger paddingLeft'>*</span></label>";
	   prepareResidencediv =prepareResidencediv+ "       <div class='col-md-8'>";
	   prepareResidencediv =prepareResidencediv+ "           <div class='positionRelative'>";
	   prepareResidencediv =prepareResidencediv+ ' 						<input class="form-control positionRelative"';
	   prepareResidencediv =prepareResidencediv+ ' 						id="location'+rowId+'" name="residenceList['+rowId+'].location"';
	   prepareResidencediv =prepareResidencediv+ ' 							placeholder="Enter Location" value="'+(typeof residenceVal[1]==="undefined"?"":residenceVal[1])+'" field-name="Location" data-validation="required" type="text" onclick="initLocationAuto(\'location'+rowId+'\',\'country'+rowId+'\',\'state'+rowId+'\',\'city'+rowId+'\',\'zipcode'+rowId+'\',\'\',\'\',\'\',\'address'+rowId+'\');"></input> <i';
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
	   prepareResidencediv =prepareResidencediv+ " 						name='residenceList["+rowId+"].address' value='"+(typeof residenceVal[2]==="undefined"?"":residenceVal[2])+"' field-name='Street Address' placeholder='Enter Street Addres'";
	   prepareResidencediv =prepareResidencediv+ " 						data-validation='validate_Space validate_length length2-250'";
	   prepareResidencediv =prepareResidencediv+ " 						data-validation-optional='true'>";
	   prepareResidencediv =prepareResidencediv+ "       </div>";
	   prepareResidencediv =prepareResidencediv+ "     </div>";
	   prepareResidencediv =prepareResidencediv+ "     <div class='form-group'>";
	   prepareResidencediv =prepareResidencediv+ "       <label class='col-md-4'>City</label>";
	   prepareResidencediv =prepareResidencediv+ "        <div class='col-md-8'>";
	   prepareResidencediv =prepareResidencediv+ "           <input class='form-control' id='city"+rowId+"' name='residenceList["+rowId+"].city' value='"+(typeof residenceVal[3]==="undefined"?"":residenceVal[3])+"' placeholder='City' type='text'></input>";
	   prepareResidencediv =prepareResidencediv+ "       </div>";
	   prepareResidencediv =prepareResidencediv+ "      </div>";
	   prepareResidencediv =prepareResidencediv+ "     <div class='form-group'>";
	   prepareResidencediv =prepareResidencediv+ "       <label class='col-md-4'>State</label>";
	   prepareResidencediv =prepareResidencediv+ "       <div class='col-md-8'>";
	   prepareResidencediv =prepareResidencediv+ "           <input class='form-control' id='state"+rowId+"' name='residenceList["+rowId+"].state' value='"+(typeof residenceVal[4]==="undefined"?"":residenceVal[4])+"' placeholder='State' type='text' ></input>";
	   prepareResidencediv =prepareResidencediv+ "       </div>";
	   prepareResidencediv =prepareResidencediv+ "     </div>";
	   prepareResidencediv =prepareResidencediv+ "     <div class='form-group'>";
	   prepareResidencediv =prepareResidencediv+ "        <label class='col-md-4'>Country</label>";
	   prepareResidencediv =prepareResidencediv+ "       <div class='col-md-8'>";
	   prepareResidencediv =prepareResidencediv+ "           <input class='form-control' id='country"+rowId+"' name='residenceList["+rowId+"].country' value='"+(typeof residenceVal[5]==="undefined"?"":residenceVal[5])+"' placeholder='Country' value='${loginCountryName}' type='text' ></input>";
	   prepareResidencediv =prepareResidencediv+ "       </div>";
	   prepareResidencediv =prepareResidencediv+ "     </div> ";                                                                                              
	   prepareResidencediv =prepareResidencediv+ "     <div class='form-group'>";
	   prepareResidencediv =prepareResidencediv+ "       <label class='col-md-4'>Zip Code</label>";
	   prepareResidencediv =prepareResidencediv+ "       <div class='col-md-8'>";
	   prepareResidencediv =prepareResidencediv+ "          <input class='form-control' id='zipcode"+rowId+"' value='"+(typeof residenceVal[6]==="undefined"?"":residenceVal[6])+"' name='residenceList["+rowId+"].zipcode' data-validation='validate_Space validate_length validate_zipcode length2-8' data-validation-optional='true'";
	   prepareResidencediv =prepareResidencediv+ " 						placeholder='Zip code' type='text' ></input>";
	   prepareResidencediv =prepareResidencediv+ "       </div>";
	   prepareResidencediv =prepareResidencediv+ "     </div>";
	   prepareResidencediv =prepareResidencediv+ "    	<div class='clearfix'></div>";
	   prepareResidencediv =prepareResidencediv+ "  <div class='form-group'>";
	   prepareResidencediv =prepareResidencediv+ "       <label class='col-md-4'>Start Date<span class='text-danger paddingLeft'>*</span></label>";
	   prepareResidencediv =prepareResidencediv+ "       <div class='col-md-8'>";
	   prepareResidencediv =prepareResidencediv+ " 			<div class='input-group errorInptgroup'>";
	   prepareResidencediv =prepareResidencediv+ "         <input type='text' class='form-control calendar' readonly='true' id='residenceStartDate"+rowId+"' value='"+(typeof residenceVal[7]==="undefined"?"":residenceVal[7])+"' onClick='showStartDatePicker("+rowId+")' name='residenceList["+rowId+"].residenceStartDate' field-name='Start Date' data-validation='required' placeholder='Start Date'>";
	   prepareResidencediv =prepareResidencediv+ "         <span class='input-group-addon'><i class='fa fa-times text-danger cursorPointer' onclick='clearDateValue(\"residenceStartDate"+rowId+"\")'></i></span>";
	   prepareResidencediv =prepareResidencediv+ "       </div>";
	   prepareResidencediv =prepareResidencediv+ "       </div>";
	   prepareResidencediv =prepareResidencediv+ "  </div>";
	   prepareResidencediv =prepareResidencediv+ "  <div class='form-group'>";
	   prepareResidencediv =prepareResidencediv+ "       <label class='col-md-4'>End Date<span class='text-danger paddingLeft'>*</span></label>";
	   prepareResidencediv =prepareResidencediv+ "       <div class='col-md-8'>";
	   prepareResidencediv =prepareResidencediv+ " 			<div class='input-group errorInptgroup'>";
	   prepareResidencediv =prepareResidencediv+ "        <input type='text' class='form-control calendar' readonly='true' id='residenceEndDate"+rowId+"' value='"+(typeof residenceVal[8]==="undefined"?"":residenceVal[8])+"' onClick='showEndDatePicker("+rowId+")'  name='residenceList["+rowId+"].residenceEndDate' field-name='End Date' data-validation='required' placeholder='End Date'>";
	   prepareResidencediv =prepareResidencediv+ "        <span class='input-group-addon'><i class='fa fa-times text-danger cursorPointer' onclick='clearDateValue(\"residenceEndDate"+rowId+"\")'></i></span>";
	   prepareResidencediv =prepareResidencediv+ "      </div>";
	   prepareResidencediv =prepareResidencediv+ "       </div>";
	   prepareResidencediv =prepareResidencediv+ "   </div>";
	   prepareResidencediv =prepareResidencediv+ "   </div>";
		return prepareResidencediv; 
   }
  var rowId =0;
   function appendDynamicResidenceDiv(){
	   console.log("rowId value is --->"+rowId);
	   $("#dynamicResidenceGroupDiv").append(prepareDynamicResidenceDiv(rowId++,"")); 
   }
   var removedResidenceIds = [];
   function removeDynamicResidenceDiv(id,residenceId){
	   removedResidenceIds.push(residenceId);
	   $("#dynamicResidenceDiv"+id).remove();
	   rowId--;// decrement rowid when deleted in UI.
   } 
   	</script>
	
<script>
/* $("input[id='candidateNameEmail']").focusin(function(){
   isValidBeneficiary = false;
});
 */
 var locationDate ='${SourceLeadWeb_dateFormat}'; 
 var finalDateFormatOrg = getOrgDateFormat(locationDate);
 var orgLocationId = "";
 
$("input[id='candidateNameEmail']").focusout(function(){
	 var seletedVal= $("#candidateNameEmail").attr("selecedVal")
  if(typeof seletedVal =='undefined') {
	  resetBeneficiaryDetails();
  }else if (typeof seletedVal !='undefined' && $("#candidateNameEmail").attr("selecedVal")!=$("#candidateNameEmail").val().trim()){
	  resetBeneficiaryDetails();
  }
});

function resetBeneficiaryDetails(){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	$("#candidateNameEmail").val("");
	$("#firstName").val("");
	$("#lastName").val("");
	$("#middleName").val("");
	$("#email").val("");
	$("#mobile").val("");
	$("#alternateMobile").val("");
	$('input[name="gender"]').prop('checked', false); 
	$('input[name="maritalStatus"]').prop('checked', false); // reset the value 
	$("#country").val("");
	$("#state").val("");
	$("#city").val("");
	$("#address").val("");
	$("#zipcode").val("");
	$("#location").val("");
	$("#experienceYears").val("");
	$("#experienceMonths").val("");
	$("#birthDate").val("");
	$("#ssnNumber").val("");
}
	 $("#candidateNameEmail").autocomplete({ 
			minLength: 2, 
			source : function(request, response) {
				$.ajax({
					url :vmsUrl+immiModulePath+"/getAllActiveCandidatesByLocation",
					type : "GET",
					data : {
						name : request.term,
						locationIdVal : orgLocationId
					},
					dataType : "json",
					success : function(data) {
						response(data);
					}
				});
			},
			select: function( event, ui ) {
				$("#candidateNameEmail").val(ui.item.label);
				$("#candidateId").val(ui.item.value);
				$("#candidateNameEmail").attr("selecedVal", ui.item.label); //this value will be compared when refoces on input.
				 $.ajax({
				    	type: "GET",
				        url: vmsUrl+immiModulePath+"/getCandidateById?id="+ui.item.value, 
				        success: function(candidateObj) {
				        	$("#firstName").val(candidateObj.firstName);
				    		$("#lastName").val(candidateObj.lastName);
				    		$("#middleName").val(candidateObj.middelName);
				    		$("#email").val(candidateObj.email);
				    		$("#mobile").val(candidateObj.mobilePhone);
				    		$("#alternateMobile").val(candidateObj.alternateMobilePhone);
				    		$("#ssnNumber").val(candidateObj.ssnNumber);
				    		
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
				    		if(candidateObj.dateofBirthString != null && typeof  candidateObj.dateofBirthString != 'undefined' && candidateObj.dateofBirthString!=''){
				    		//	var dateOfBirth =  moment(candidateObj.dateOfBirth).format(finalDateFormatOrg);
				    		   $("#birthDate").val(candidateObj.dateofBirthString);
				    		}
				   
				        }
				 });
				return false;
			},
			focus: function(event, ui){
		          return false;
		    }
	 });
			
function deleteResumeDoc(){
	$("#docNameDiv").hide();
	$("#resumeEmpty").show();
	$("#documentName").html("");
	$("#downloadBrowseFile").attr("href","");
}


// to get the resume details for the candidate.
function getCandidatesResumeDetailsById(candidateId){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	 var urltest = sourceleadUrl+immiModulePath+"/getAllActiveCandidatesByLocation";
		 $.ajax({
		    	type: "GET",
		        url: vmsUrl+immiModulePath+"/getCandidatesResumeDetailsById?candidateId="+candidateId, 
		        /* data:, */
		        success: function(data) {
		        	if(data.fileName != null){
		        		$("#docNameDiv").show();
		        		$("#resumeEmpty").hide();
			        	$("#documentName").html(data.fileName);
			        	$("#downloadBrowseFile").attr("href",data.fileURL);
		        	}else{
		        		$("#docNameDiv").hide();
		        		$("#resumeEmpty").show();
		        	}
		            console.log("inside sucess function");
		            console.log(data);
		            
		   }
   });
}

$("#experienceValue").change(function () {
    if(this.value == '40'){
    	$("#expMonthValue").val("");
    	$("#expMonthValue").prop("disabled", true);
    }else{
    	$("#expMonthValue").prop("disabled", false);
    }
});
</script>
	<script>
	
	function getPetitionTypeDropDown(petitionType){
		$.ajax({
		    	type: "GET",
		        url: vmsUrl+immiModulePath+"/getPetitionTypeDropDown", 
		        success: function(data) {
		        	$('#petitionType').empty();
		        	$('#petitionType').append('<option selected value="">--Select Type --</option>');
		            for(var i=0;i<data.length;i++){
		            	  var opt = data[i];
		            	  if(opt.id == petitionType){
		            		  $('#petitionType').append('<option selected value='+opt.id+' >'+opt.name+'</option>');
		            	  }else{
		            		$('#petitionType').append('<option value='+opt.id+' >'+opt.name+'</option>');
		            	  }
		            }
		            $('#petitionType').trigger("chosen:updated");		        	
		        }
		 });
	}
	
	function getUscisTypeDropDown(status){
		var vmsUrl ='${SourceLeadWeb_vmsUrl}';
		$.ajax({
		    	type: "GET",
		        url: vmsUrl+immiModulePath+"/getUscisTypeDropDown", 
		        success: function(data) {
		        	$('#currUscisStatus').empty();
		        	$('#currUscisStatus').append('<option selected value="">--Select Type --</option>');
		            for(var i=0;i<data.length;i++){
		            	  var opt = data[i];
		            	  if(opt.id == status){
		            		$('#currUscisStatus').append('<option selected value='+opt.id+' >'+opt.name+'</option>');
		            	  }else{
		            		$('#currUscisStatus').append('<option value='+opt.id+' >'+opt.name+'</option>');
		            	  }
		            }
		            $('#currUscisStatus').trigger("chosen:updated");		        	
		        }
		 });
	}
	
	function employmentLevelDropDown(employmentLevelId){
		var vmsUrl ='${SourceLeadWeb_vmsUrl}'; 
		$.ajax({
		    	type: "GET",
		        url: vmsUrl+immiModulePath+"/getEmploymentLevelDropDown", 
		        success: function(data) {
		        	$('#employmentLevel').empty();
		        	$('#employmentLevel').append('<option value="">--Select Type --</option>');
		            for(var i=0;i<data.length;i++){
		            	  var opt = data[i];
		            	  if(opt.id == employmentLevelId){
		            		  $('#employmentLevel').append('<option selected value='+opt.id+' >'+opt.name+'</option>');
		            	  }else{
		            		$('#employmentLevel').append('<option value='+opt.id+' >'+opt.name+'</option>');
		            	  }
		            }
		            $('#employmentLevel').trigger("chosen:updated");		        	
		        }
		 });
	}
	
	function jobTitleDropDown(jobId){
		var vmsUrl ='${SourceLeadWeb_vmsUrl}'; 
		if('${SourceLeadWeb_orgLocations.isLawFirm}' == 'Y'){
			var locationId = responderLocationId;
			}else{
			var locationId ='${SourceLeadWeb_orgLocations.id}';
			}
		 $.ajax({
		    	type: "GET",
		        url: vmsUrl+immiModulePath+"/getJobTitleDropDown?locationId="+locationId, 
		        success: function(data) {
		        	$('#jobId').empty();
		        	$('#jobId').append('<option value="">--Select Type --</option>');
		            for(var i=0;i<data.length;i++){
		            	  var opt = data[i];
		            	  if(opt.id == jobId){
		            		  $('#jobId').append('<option selected value='+opt.id+' >'+opt.jobTitle+'</option>');
		            	  }else{
		            		$('#jobId').append('<option value='+opt.id+' >'+opt.jobTitle+'</option>');
		            	  }
		            }
		            $('#jobId').trigger("chosen:updated");		        	
		        }
		 });
	}
	
	
 function getAttorneysByOrgLocation(attorneyId){
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
		            	  if(opt.id == attorneyId){
		            		  $('#attorneyId').append('<option selected value='+opt.id+' >'+opt.orgName+'</option>');
		            	  }else{
		            		$('#attorneyId').append('<option value='+opt.id+' >'+opt.orgName+'</option>');
		            	  }
		            }
		            $('#attorneyId').trigger("chosen:updated");
		        }
		 });
 }

 function getAllRelationssByOrgLocation(attorneyId){
	 console.log(" before function calling ---->");
	var type = null ;
	if('${SourceLeadWeb_orgLocations.isLawFirm}' == 'Y'){
		type = "Client";
	}else{
		type = "Vendor";
	}
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getAllRelationsByOrgLocationAndType?"+tokenParamName+"="+tokenValue+"&type="+type, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	$('#attorneyId').empty();
		        	if('${SourceLeadWeb_orgLocations.isLawFirm}' == 'Y'){
		        		$('#attorneyId').append('<option value="">--Select Company --</option>');
		        	}else{
		        		$('#attorneyId').append('<option value="">--Select Attorney --</option>');
		        	}
		            for(var i=0;i<json.length;i++){
		            	  var opt = json[i];
		            	  if(opt.id == attorneyId){
		            		  $('#attorneyId').append('<option attrval='+opt.immigrationRelatedId+' selected value='+opt.id+' >'+opt.orgName+'</option>');
		            	  }else{
		            		$('#attorneyId').append('<option attrval='+opt.immigrationRelatedId+' value='+opt.id+' >'+opt.orgName+'</option>');
		            	  }
		            }
		            $('#attorneyId').trigger("chosen:updated");
		        }
		 });
 }
 var responderLocationId = null;
 function getAttorneyContactList(attorneyId,attorneyContactId){
	 var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	 var orgRelationID = null;
	 var reponsderLocId = $('option:selected', '#attorneyId').attr('attrval');
	 responderLocationId = reponsderLocId;
	 var attorneySelId = $('option:selected', '#attorneyId').val();
	 	if(attorneyId != null){
	 		orgRelationID = attorneyId;
	 	}else{
			var orgRelationID = $('#attorneyId').val();
	 	}
	 	
	 	if('${SourceLeadWeb_orgLocations.isLawFirm}' == 'Y'){
			type = "Client";
			orgLocationId = reponsderLocId;

			if(attorneySelId  == '' ||  typeof attorneySelId == 'undefined' ){
		 		<c:if test="${empty immigrationDTO}">
			 		$("#candidateNameEmail").attr("readonly",true);
			 		$("#candidateNameEmail").val("");
		 		</c:if>
			  }else{
				  $("#candidateNameEmail").attr("readonly",false);
				  $("#candidateNameEmail").val("");
			  }
		}else{
			type = "Vendor";
			orgLocationId = '${SourceLeadWeb_orgLocationId}';
		}
	 	
			 $.ajax({
				    type: "POST",
			        url: vmsUrl+"/getOrgSpocsList?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationID+"&type="+type,  
			        success: function(json) {
			        	$('#attorneyContact').empty();
			        	if('${SourceLeadWeb_orgLocations.isLawFirm}' == 'Y'){
			        		$('#attorneyContact').append('<option value="">--Select Company contact--</option>');
			    		}else{
			    			$('#attorneyContact').append('<option value="">--Select Attorney contact--</option>');
			    		}
			             for(var i=0;i<json.data.length;i++){
			            	 var opt = json.data[i];
			            	 if(opt.id == attorneyContactId){
			            		 $('#attorneyContact').append('<option selected value='+opt.id+' >'+opt.spocName+' ('+opt.ownerName+')</option>');
			            	 }else{
				            	 $('#attorneyContact').append('<option value='+opt.id+' >'+opt.spocName+' ('+opt.ownerName+')</option>');
			            	 }
			            } 
			            $('#attorneyContact').trigger("chosen:updated");
			            if(attorneySelId  == '' ||  typeof attorneySelId == 'undefined' ){ // reset the job dropdown chosen select.
			            	$('#jobId').empty();
				        	$('#jobId').append('<option value="">--Select Type --</option>'); 
				        	$('#jobId').trigger("chosen:updated");
			            }else{
			            jobTitleDropDown("${immigrationDTO.jobId}");
			            }
			        }
			 }); 
	 }
 </script>
   <script type="text/javascript">
   
   function onLoadStatusChange(status){
	   var uscisStatusVal = $("#currUscisStatus option:selected").text();
	   if(status != null){
		   uscisStatusVal = status;
	   }
	   if(uscisStatusVal == "Draft"){
		   $("#sendRequestButton").hide();
		   $("#savePageOneContinue").hide();
		   $("#saveAsDraftButton").show();
	   }else if(uscisStatusVal == "Request to fill"){
		   $("#sendRequestButton").show();
		   $("#savePageOneContinue").hide();
		   $("#saveAsDraftButton").hide();
	   }else{
		   $("#sendRequestButton").hide();
		   $("#savePageOneContinue").show();
		   $("#saveAsDraftButton").hide();
	   }
   }
   
	  $( document ).ready(function() {
		  $('#rateCurrency').selectpicker('refresh');  // manually refreshing select picker due to bootstrap css loading issue.
		  $('#rateType').selectpicker('refresh');
		  getAllRelationssByOrgLocation("${immigrationDTO.attorneyId}")
		  getAttorneyContactList("${immigrationDTO.attorneyId}","${immigrationDTO.attorneyContact}");
		  getPetitionTypeDropDown("${immigrationDTO.petitionType}");
		  getUscisTypeDropDown("${immigrationDTO.currUscisStatusId}");
		  onLoadStatusChange("${immigrationDTO.currUscisStatus}");
		  employmentLevelDropDown("${immigrationDTO.employmentLevel}");
		  jobTitleDropDown("${immigrationDTO.jobId}");
		  
		  if("${immigrationDTO.immigrationId}" == ""){
			  $('#templateSelect').prop('disabled', false).trigger("chosen:updated");
		  }else{
			  $('#templateSelect').attr('disabled', true).trigger("chosen:updated");
		  } 
		  var sizeOfResidence = "${fn:length(immigrationDTO.residenceList)}";
		  if(sizeOfResidence > 0){
		  	var count = 1;
		  	var residenceArr = [];
		  	<c:forEach var='edu' items="${immigrationDTO.residenceList}">
		  		if("${edu.isCurrResidence}" != 'Y'){
			  		residenceArr.push("${edu.id == 'undefined' ? '':edu.id}");
				  	residenceArr.push("${edu.location}");
				  	residenceArr.push("${edu.address}");
				  	residenceArr.push("${edu.city}");
				  	residenceArr.push("${edu.state}");
				  	residenceArr.push("${edu.country}");
				  	residenceArr.push("${edu.zipcode}");
				  	residenceArr.push("${edu.residenceStartDate}");
				  	residenceArr.push("${edu.residenceEndDate}");
				  	$("#dynamicResidenceGroupDiv").append(prepareDynamicResidenceDiv(count,residenceArr));
				  	residenceArr = [];
			  		count++;
		  		}
		  	</c:forEach>
		  	var fileNameVal = "${immigrationDTO.fileName}";
		  	if(fileNameVal != ""){
        		$("#docNameDiv").show();
        		$("#resumeEmpty").hide();
	        	$("#documentName").html("${immigrationDTO.fileName}");
	        	$("#downloadBrowseFile").attr("href","${immigrationDTO.fileUrl}");
        	}else{
        		$("#docNameDiv").hide();
        		$("#resumeEmpty").show();
        	}
		  }
		  
		  var locationDate ='${SourceLeadWeb_dateFormat}'; 
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
		  	  maxDate:0  
		  });
		  
		  $('#lcaEndDate').datepicker({
		      dateFormat: finalDateFormatPetitionSave,
		      changeMonth: true,
		  	  changeYear: true, 
		  	   minDate:1 
		  });
		  ///
		  
		// residence start date
		   $('#residenceStartDate').datepicker({
		      dateFormat: finalDateFormatPetitionSave,
		      changeMonth: true,
		  	  changeYear: true, 
		  	  yearRange: "-100:+0",
		  	  maxDate:0  
		  })
		  
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
		 var vid =  $('#attorneyId').val();
		 window.open(vmsUrl+"/addVendorContact?vid="+vid,'_blank');
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
	 $(function() {
			$("#uploadBtn").change(function() {
				fileUpload("uploadBtn", "resumes", "petitionDetailsForm","resumeInfo");
			});
		 });
		 document.getElementById("uploadBtn").onchange = function () {
		     $('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''));
		 };
	     
	     $("#clearUploads").click(function(){
	    	 $("#uploadFile").val("");
	     });
	     var locationDate ='${SourceLeadWeb_dateFormat}'; 
		 var finalDateFormatPetitionSave = getJSDateFormat(locationDate);
		function showStartDatePicker(id){
		  $("#residenceStartDate"+id).datepicker({
		      dateFormat: finalDateFormatPetitionSave,
		      changeMonth: true,
		  	  changeYear: true, 
		      onSelect: function (selected) {
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
		            var dt = $(this).datepicker('getDate', '-1d');
		            dt.setDate(dt.getDate() - 1);
		            $("#residenceStartDate"+id).datepicker("option", "maxDate", dt);
		        }
		  }).datepicker("show");
		}
		$(function() {
		    $("#experienceYears").change(function() {
		    	if($("#experienceYears").val() == '40'){
		    		$("#experienceMonths").prop("disabled", true);
		    	}else{
		    		$("#experienceMonths").prop("disabled", false);
		    	}
		    });
		});
	 </script>

<!-- LcaNumber AutoPopulate Ui script -->
<script type="text/javascript">
var vmsUrl ='${SourceLeadWeb_vmsUrl}';
var locationDate ='${SourceLeadWeb_dateFormat}'; 
var finalDateFormatOrg = getOrgDateFormat(locationDate);
 $("#lcaNumber").autocomplete({ 
	 minLength: 2, 
	 source : function(request, response) {
		 $.ajax({
			 url :vmsUrl+immiModulePath+ "/getLcaNumbersList",
			 type : "GET",
			 data : {
				 lcaNum : request.term,
				 expiryStatus:'1',
				 candidateId :$("#candidateId").val()
			 	},
			 dataType : "json",
			 success : function(data) {
				  
			 	  response(data);
			 }
		 });
	 },
	   select: function(event, ui ) {
		    $("#lcaNumber").val(ui.item.label);
		 	$("#currLcaId").val(ui.item.value);//currentLcaId for LcaHistory
		 	$("#lcaNumber").attr("selecedVal", ui.item.label);
		 	
			 $.ajax({
			    	type: "GET",
			        url: vmsUrl+immiModulePath+"/getLcaByLcaId?id="+ui.item.value, 
			        success: function(data) {
			        	
			        	if(data.lcaLocation != null && typeof data.lcaLocation != 'undefined' && data.lcaLocation !=''){
			    			 $("#lcaCurrLocation").val(data.lcaLocation);
			    			 $("#lcaCurrLocation" ).prop( "disabled", true);
			    		}
			    		// Job And LCA Locations Are Equal Or Not Show Alert msg.
			    		var jobLocation = $("#jobLocation").val();
			    		if(jobLocation !=''){
				    		if (jobLocation !== data.lcaLocation){
				    			alert("Job And LCA Locations are not matched");
				    		}
			    		}
			    		// setting the startDate,endDate for current Lca.
			    		
			    		 if(data.lcaStartDateStr != null && typeof  data.lcaStartDateStr != 'undefined' && data.lcaStartDateStr!=''){
			    			//var startDateStr =  moment(data.lcaStartDateStr).format(finalDateFormatOrg);
			    		   $("#lcaStartDate").val(data.lcaStartDateStr);
			    		   $("#lcaStartDate").datepicker('disable');
			    		}
			    		if(data.lcaEndDateStr != null && typeof  data.lcaEndDateStr != 'undefined' && data.lcaEndDateStr!=''){
			    		//	var lcaEndDateStr =  moment(data.lcaEndDateStr).format(finalDateFormatOrg);
			    		   $("#lcaEndDate").val(data.lcaEndDateStr);
			    		   $("#lcaEndDate").datepicker('disable');
			    		} 
			    		
			    		
			        }
			 });
		 	return false;
		 },
	 focus: function(event, ui){
	          return false;
	    }
	 });
 
 $("input[id='lcaNumber']").focusout(function(){
	 var seletedVal= $("#lcaNumber").attr("selecedVal");
	
  if(typeof seletedVal =='undefined') {
		  resetLcaDetails();
	  }else if (typeof seletedVal !='undefined' && $("#lcaNumber").attr("selecedVal")!=$("#lcaNumber").val().trim()){
		  resetLcaDetails();
	  } 
 });
 
 function resetLcaDetails(){
		$("#lcaNumber").val("");
		$("#lcaStartDate").val("");
		$("#lcaEndDate").val("");
		$("#lcaCurrLocation").val("");
		$("#lcaStartDate").datepicker('enable');
		$("#lcaEndDate").datepicker('enable');
	} 
 
</script> 

<script type="text/javascript">
var vmsUrl ='${SourceLeadWeb_vmsUrl}';
var locationDate ='${SourceLeadWeb_dateFormat}'; 
var finalDateFormatOrg = getOrgDateFormat(locationDate);
 $("#prevLcaNumber").autocomplete({ 
	 minLength: 2, 
	 source : function(request, response) {
		 $.ajax({
			 url :vmsUrl+immiModulePath+ "/getLcaNumbersList",
			 type : "GET",
			 data : {
				 lcaNum : request.term,
				 expiryStatus:'0'
			 	},
			 dataType : "json",
			 success : function(data) {
			 	response(data);
			 }
		 });
	 },
	   select: function(event, ui ) {
		    $("#prevLcaNumber").val(ui.item.label);
		 	$("#prevLcaId").val(ui.item.value);//currentLcaId for LcaHistory
		 	$("#prevLcaNumber").attr("selecedVal", ui.item.label);
			 $.ajax({
			    	type: "GET",
			        url: vmsUrl+immiModulePath+"/getLcaByLcaId?id="+ui.item.value, 
			        success: function(data) {
			    		// setting the startDate,endDate for current Lca.
			    		 if(data.lcaStartDateStr != null && typeof  data.lcaStartDateStr != 'undefined' && data.lcaStartDateStr!=''){
			    		//	var prevLcaStartDate =  moment(data.startDate).format(finalDateFormatOrg);
			    		   $("#prevLcaStartDate").val(data.lcaStartDateStr);
			    		   $("#prevLcaStartDate").datepicker('disable');
			    		}
			    		
			    		if(data.lcaEndDateStr != null && typeof  data.lcaEndDateStr != 'undefined' && data.lcaEndDateStr!=''){
			    			//var prevLcaEndDate =  moment(data.endDate).format(finalDateFormatOrg);
			    		   $("#prevLcaEndDate").val(data.lcaEndDateStr);
			    		   $("#prevLcaEndDate").datepicker('disable');
			    		} 
			        }
			 });
		 	return false;
		 },
	 focus: function(event, ui){
	          return false;
	    }
	 });
 
 $("input[id='prevLcaNumber']").focusout(function(){
	 var seletedVal= $("#prevLcaNumber").attr("selecedVal")
	  if(typeof seletedVal =='undefined') {
		  resetPreLcaDetails();
	  }else if (typeof seletedVal !='undefined' && $("#prevLcaNumber").attr("selecedVal")!=$("#prevLcaNumber").val().trim()){
		  resetPreLcaDetails();
	  }
 });
 
 function resetPreLcaDetails(){
		$("#prevLcaNumber").val("");
		$("#prevLcaStartDate").val("");
		$("#prevLcaEndDate").val("");
		$("#prevLcaStartDate").datepicker('enable');
		$("#prevLcaEndDate").datepicker('enable');
		
	} 
 
</script> 
<script>
function  selectBeneficiary() {
    if(($('#candidateNameEmail').val() == '' || $('#candidateNameEmail').val() == 'undefined' || $('#candidateNameEmail').val() == null )){
    	alert("Please Select Your Beneficiary ");
    }      
}
</script>

<script type="text/javascript">
$('#i_20_number').mask('N-9999999999', {
    translation: {
        '#': {
            pattern: "/N{1}[0-9]{10}/", optional: true
        }
    },
 placeholder: ""
});

$('#eadNumber').mask('999-999-999', {
    translation: {
        '#': {
            pattern: "/[0-9]{9}/", optional: true
        }
    },
   placeholder: ""
});

$('#ssnNumber').mask('000-00-0000', {
    translation: {
        '#': {
            pattern: "/[0-9]{9}/", optional: true
        }
    },
   placeholder: ""
});

$('#i_94_number').mask('000000000-00', {
    translation: {
        '#': {
            pattern: "/[0-9]{11}/", optional: true
        }
    },
   placeholder: ""
});
</script>