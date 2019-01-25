<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- Content Area -->

<div class="modal-dialog">
	<div class="modal-content">
		<form:form class="login-form clearfix"  id="nomineeDetails_form"  name="nomineeDetails_form" method="post" >
                <input type ="hidden" id="nomineeDetailsId" name="id">
                  <div class="col-md-12">
                  <h5>Family Details</h5>
                  </div>
                  <div class="clearfix"></div>
                  <div class="col-md-5">
                  <input type="hidden" id="id" name="id" value="${nominee.id}">
                    <label>Name<span class="text-danger paddingLeft">*</span></label>
                    <div>
                      <input type="text" id="familyDetailsName" class="form-control" name="name" value="${nominee.name}" field-name="Name" data-validation="required validate_length length1-250 validate_Alphabet">
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Date of Birth<span class="text-danger paddingLeft">*</span></label>
                    <div>
                      <input type="text" id="dateOfBirth" class="form-control calendar" name="dateOfBirth"  value="${nominee.dateOfBirth}" field-name="Date of Birth" data-validation="required">
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Gender</label>
                    <div>
                      <select class="sel" id="gender" name="gender">
                        <option value="">Select</option>
                        <option  ${nominee.gender == 'Male' ? 'selected' : ''}>Male</option>
                        <option  ${nominee.gender == 'Female' ? 'selected' : ''}>Female</option>
                        <option  ${nominee.gender == 'Transgender' ? 'selected' : ''}>Transgender</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Blood Group</label>
                    <div>
                      <select class="sel" id="bloodGroup" name="bloodGroup">
                        <option selected>Select</option>
                        <option  ${nominee.bloodGroup  == 'A+' ? 'selected' : ''} >A+</option>
                        <option  ${nominee.bloodGroup  == 'B+' ? 'selected' : ''} >B+</option>
                        <option  ${nominee.bloodGroup  == 'AB+' ? 'selected' : ''} >AB+</option>
                        <option  ${nominee.bloodGroup  == 'A-' ? 'selected' : ''} >A-</option>
                        <option  ${nominee.bloodGroup  == 'B-' ? 'selected' : ''} >B-</option>
                        <option  ${nominee.bloodGroup  == 'AB-' ? 'selected' : ''} >AB-</option>
                        <option  ${nominee.bloodGroup  == 'O+' ? 'selected' : ''} >O+</option>
                        <option  ${nominee.bloodGroup  == 'O-' ? 'selected' : ''} >O-</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Relation<span class="text-danger paddingLeft">*</span></label>
                    <div>
                      <select class="sel" id="relation" name="relation" field-name="Relation" data-validation="required">
                        <option value="">Select</option>
                        <option ${nominee.relation  == 'Father' ? 'selected' : ''} value="Father">Father</option>
                        <option ${nominee.relation  == 'Mother' ? 'selected' : ''} value="Mother">Mother</option>
                        <option ${nominee.relation  == 'Brother' ? 'selected' : ''} value="Brother">Brother</option>
                        <option ${nominee.relation  == 'Sister' ? 'selected' : ''} value="Sister">Sister</option>
                        <option ${nominee.relation  == 'Husband' ? 'selected' : ''} value="Husband">Husband</option>
                        <option ${nominee.relation  == 'Wife' ? 'selected' : ''} value="Wife">Wife</option>
                        <option ${nominee.relation  == 'Son' ? 'selected' : ''} value="Son">Son</option>
                        <option ${nominee.relation  == 'Daughter' ? 'selected' : ''} value="Daughter">Daughter</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Nomination For</label>
                    <div>
                      <select class="sel" id="nominationFor" name="nominationFor">
                        <option value="">Select</option>
                        <option ${nominee.nominationFor  == 'PF' ? 'selected' : ''}>PF</option>
                        <option ${nominee.nominationFor  == 'ESI' ? 'selected' : ''}>ESI</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Nomination %<span class="text-danger paddingLeft">*</span></label>
                    <div>
                      <input type="number" id="nominationPercentage" class="form-control" name="nominationPercentage" value="${nominee.nominationPercentage}" field-name="Nomination Percentage" onInput="return check(event,value)" min="0" max="100" step="0.01" data-validation="required">
                    
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Address</label>
                    <div>
                      <textarea rows="2" id="familyDetailsAdd" class="form-control" name="address" data-validation="validate_Space validate_length length1-500" data-validation-optional = "true" value="" field-name="Address">${nominee.address}</textarea>
                    </div>
                  </div>
                  <div class="col-md-5">
                    <div class="row">
                      <div class="col-md-4 mb checkbox-new">
                        <label>&nbsp;</label>
                        <div class="clearfix">
                          <input id="illness" class="checkbox-custom" name="illness" type="checkbox" ${nominee.illnessGuardianName  != '' && nominee.illnessGuardianName  != null && nominee.illnessGuardianName  != 'undefined' ? 'checked' : ''} onClick="illnessFunction()">
                          <label for="illness" class="checkbox-custom-label"><span class="ml10">Illness</span></label>
                        </div>
                      </div>
                      <div class="col-md-4 mb checkbox-new">
                        <label>&nbsp;</label>
                        <div class="clearfix">
                          <input id="minor" class="checkbox-custom" name="minor" type="checkbox" ${nominee.minorGuardianName  != '' && nominee.minorGuardianName  != null && nominee.minorGuardianName  != 'undefined' ? 'checked' : ''} onClick="minorFunction()">
                          <label for="minor" class="checkbox-custom-label"><span class="ml10">Minor</span></label>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="clearfix mt"></div>
                  <div class="clearfix" id="illnessMinor" ${nominee.illnessGuardianName  != '' && nominee.illnessGuardianName  != null && nominee.illnessGuardianName  != 'undefined' ? 'style=""' : 'style="display: none;"'}>
                  <h4 class="col-md-12 margin-top0 ml">Illness</h4>
                    <div class="col-md-5">
                      <label>Guardian Name<span class="text-danger paddingLeft">*</span></label>
                      <div>
                        <input type="text" id="illnessGuardianName" class="form-control" name="illnessGuardianName" value="${nominee.illnessGuardianName}" field-name="Guardian Name" data-validation="required">
                      </div>
                      
                    </div>
                    <div class="col-md-5">
                      <label>Relation<span class="text-danger paddingLeft">*</span></label>
                      <div>
                        <select class="sel" id="illnessGuardianRelationName" name="illnessGuardianRelationName" field-name="Relation" data-validation="required">
                          <option value="">Select</option>
                          <option ${nominee.relation  == 'Father' ? 'selected' : ''} value="Father">Father</option>
                          <option ${nominee.relation  == 'Mother' ? 'selected' : ''} value="Mother">Mother</option>
                          <option ${nominee.relation  == 'Brother' ? 'selected' : ''} value="Brother">Brother</option>
                          <option ${nominee.relation  == 'Sister' ? 'selected' : ''} value="Sister">Sister</option>
                          <option ${nominee.relation  == 'Husband' ? 'selected' : ''} value="Husband">Husband</option>
                          <option ${nominee.relation  == 'Wife' ? 'selected' : ''} value="Wife">Wife</option>
                          <option ${nominee.relation  == 'Son' ? 'selected' : ''} value="Son">Son</option>
                          <option ${nominee.relation  == 'Daughter' ? 'selected' : ''} value="Daughter">Daughter</option>
                        </select>                        
                      </div>
                    </div>
                    <div class="col-md-5">
                      <label>Address</label>
                      <div>
                        <textarea rows="2" id="illnessGuardianAddress" class="form-control" name="illnessGuardianAddress" value="" field-name="Illness Guardian Address">${nominee.illnessGuardianAddress}</textarea>
                      </div>
                    </div>
                    <div class="col-md-5">
                      <label>Phone Number</label>
                      <div>
                        <input type="text" id="illnessGuardianPhoneNumber" class="form-control" name="illnessGuardianPhoneNumber" value="${nominee.illnessGuardianPhoneNumber}" field-name="Illness Guardian PhoneNumber">
                      </div>
                    </div>
                    <div class="clearfix mt"></div>
                    <div class="col-md-5 mt">
                      <label>Email</label>
                      <div>
                        <input type="text" id="illnessGuardianEmail" class="form-control" name="illnessGuardianEmail" value="${nominee.illnessGuardianEmail}" field-name="Illness Guardian Email">
                      </div>
                    </div>
                    <div class="clearfix"></div>
                  </div>
                  <!--  Minor Div -->
                  <div class="clearfix" id="minorDiv" ${nominee.minorGuardianName  != '' && nominee.minorGuardianName  != null && nominee.minorGuardianName  != 'undefined' ? 'style=""' : 'style="display: none;"'} >
                  <h4 class="col-md-12 margin-top0 ml">Minor</h4>
                    <div class="col-md-5">
                      <label>Guardian Name<span class="text-danger paddingLeft">*</span></label>
                      <div>
                        <input type="text" id="minorGuardianName" class="form-control" name="minorGuardianName" value="${nominee.minorGuardianName}" field-name="Guardian Name" data-validation="required">
                      </div>
                    </div>
                    <div class="col-md-5">
                      <label>Relation<span class="text-danger paddingLeft">*</span></label>
                      <div>
                        <select class="sel" id="minorGuardianRelationName" name="minorGuardianRelationName" field-name="Relation" data-validation="required">
                          <option value="">Select</option>
                          	<option ${nominee.relation  == 'Father' ? 'selected' : ''} value="Father">Father</option>
                            <option ${nominee.relation  == 'Mother' ? 'selected' : ''} value="Mother">Mother</option>
                            <option ${nominee.relation  == 'Brother' ? 'selected' : ''} value="Brother">Brother</option>
                            <option ${nominee.relation  == 'Sister' ? 'selected' : ''} value="Sister">Sister</option>
                            <option ${nominee.relation  == 'Husband' ? 'selected' : ''} value="Husband">Husband</option>
                            <option ${nominee.relation  == 'Wife' ? 'selected' : ''} value="Wife">Wife</option>
                            <option ${nominee.relation  == 'Son' ? 'selected' : ''} value="Son">Son</option>
                            <option ${nominee.relation  == 'Daughter' ? 'selected' : ''} value="Daughter">Daughter</option>
                        </select>
                      </div>
                    </div>
                    <div class="col-md-5">
                      <label>Address</label>
                      <div>
                        <textarea rows="2" id="minorGuardianAddress" class="form-control" name="minorGuardianAddress" value="" field-name="Address">${nominee.minorGuardianAddress}</textarea>
                      </div>
                    </div>
                    <div class="col-md-5">
                      <label>Phone Number</label>
                      <div>
                        <input type="text" id="minorGuardianPhoneNumber" class="form-control" name="minorGuardianPhoneNumber" value="${nominee.minorGuardianPhoneNumber}" field-name="Phone Number">
                      </div>
                    </div>
                    <div class="clearfix mt"></div>
                    <div class="col-md-5 mt">
                      <label>Email</label>
                      <div>
                        <input type="text" id="minorGuardianEmail" class="form-control" name="minorGuardianEmail" value="${nominee.minorGuardianEmail}" field-name="Email">
                      </div>
                    </div>
                    <div class="clearfix"></div>
                  </div>
                  
                  
                  <div class="clearfix footerstyelsNew">
			             <div class="col-md-6 col-md-offset-6">
			               <div class="pull-right">
			                 <input value="save" id="saveButton" class="btn btn-success btnSuccess" type="button" onclick="saveNomineeDetails();">
			                 <input value="cancel" id="cancelButton" class="btn btn-cancel btnCancel" type="button" onclick="closeNomineePopUp();">
			               </div>
			             </div>
			     </div> 
			     
                </form:form>
	</div>
</div>

 
 <script>
	//to set from date to current month
		$('#dateOfBirth').datepicker({
		dateFormat : 'dd-mm-yy',
		changeMonth: true,
		changeYear: true,
		maxDate : 0,
	});
 </script>
<script>
$("#illness").click(function() {
$("#illnessMinor").toggle();
});
     
$("#minor").click(function() {
$("#minorDiv").toggle();
});
 </script>
