<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="tabsMenu_tabs_inn project_wrap" style="display: block;">
<div id="informationDetailsLoading"></div>
 <form:form id="associate_informationDetails_form" name="associate_informationDetails_form" method="post">
	 	  <input type="hidden" name="associateUserId" value='${userId}' id="pfassociateUserId"/>
	      <input type="hidden" name="pfesiId" value="${pfesiDTO.pfesiId}" id="pfesiId"/>
	      
	      
	      <div class="clearfix  minHeightcol5">
	      <div class="clearfix"></div>
              <div class="col-md-12 mb">
              <div class="clearfix">
              	     <h4 class="col-md-12 noPad">PF</h4>
		                  <div class="mb checkbox-new clearfix">
							<input id="pf" class="checkbox-custom" type="checkbox" name="eligibleForPf"  ${pfesiDTO.eligibleForPf == 'Y' ? 'checked' : ''}   onClick="associatePfFunction()">
							<label for="pf" class="checkbox-custom-label"><span class="ml10">Eligible for PF</span></label>
		                    <div>
		                    </div>
		                  </div>              
                <div class="clearfix row mt">
		                  <div class="clearfix"></div>
		                  <div  id="pfEligible" ${pfesiDTO.eligibleForPf == 'Y' ? '' : 'style="display: none;"'} >
		                   <div class="col-md-5">
		                    <label>UAN Number<span class="text-danger">&nbsp;*</span></label>
		                    <div>
		                     <input type="text" class="form-control" name="uanNumber" id="pfUan" value="${pfesiDTO.uanNumber}" ${pfesiDTO.eligibleForPf == 'Y' ? 'data-validation="required validate_int fixed_length length12"' : ''}  field-name="UAN Number"  field-name="PF UAN">
		                    </div>
		                  </div>
		                  
		                  <div class="col-md-5">
		                    <label>PF Number<span class="text-danger">&nbsp;*</span></label>
		                    <div>
		                       <input type="text" class="form-control" name="pfNumber" value="${pfesiDTO.pfNumber}" id="pfNumber" ${pfesiDTO.eligibleForPf == 'Y' ? 'data-validation="required validate_AlphaNumber fixed_length length19"' : ''} field-name="PF Number" field-name="PF Number">
		                    </div>
		                  </div>
		                  <div class="col-md-5">
		                    <label>Enrolled Date</label>
		                    <div>
		                       <input type="text" class="form-control calendar" name="enroleDate" onmouseenter="getDatePicker();"  value="${pfesiDTO.enroleDate}"  id="enrolledDate" readonly="readonly">
		                    </div>
		                  </div>
		                  </div>
		        </div>  
		                 
                 <div class="clearfix mt"></div>
                 <h4 class="col-md-12 noPad">ESI</h4>
                   <div class="mb checkbox-new clearfix">
					<input id="esi" class="checkbox-custom" name="eligibleForEsi" type="checkbox" ${pfesiDTO.eligibleForEsi == 'Y' ? 'checked' : ''} onClick="associateEsiFunction()">
					<label for="esi" class="checkbox-custom-label"><span class="ml10">Eligible for ESI</span></label>
                    <div>
                    </div>
                  </div>
                  <div class="clearfix row">
                  
                  <div class="col-md-5" id="esiEligible" ${pfesiDTO.eligibleForEsi == 'Y' ? '' : 'style="display: none;"'}>
                    <label>ESI Number</label>
                    <div>
                      <input type="text" class="form-control" name="esinumber"  id="esiNumber" value="${pfesiDTO.esinumber}" data-validation="validate_int fixed_length length17" data-validation-optional="true" field-name="ESI Number">
                    </div>
                  </div>
               </div>
              
              
              
              <div class="clearfix"></div>
                  
                  <div class="col-md-6 mb checkbox-new noPad">
                   <c:if test="${associateNominee.nominee eq 'Y'}">
                             <input class="checkbox-custom" id="nomineeShow" name="nominee" type="checkbox" onClick="nomineeDetails()">
                   </c:if>
                   <c:if test="${empty associateNominee.nominee}">
                   			<input class="checkbox-custom" id="nomineeShow" name="nominee" type="checkbox" onClick="nomineeDetails()">
                   </c:if>
					<label for="nomineeShow" class="checkbox-custom-label" onClick="nomineeDetails()"><span class="ml10">Nominee</span></label>
                    <div>
                    </div>
           </div>
           
           
           <div class="clearfix">
					<div class="clearfix mb" id="nomineeDetaDiv" style="display: none;" >
		               <div class="pull-right">
		                 <input value="Add" id="addButton" class="btn btn-success btnSuccess" type="button" onclick="addNomineeDetails();">
		               </div>
		             </div>
		             <div id="nomineeDeta" style="display: none;" class="require_wrap_table datatable_filter">
		             </div>
			</div> 
                  
                  
<!--                 <div class="clearfix col-md-12"> <a href="javascript:void(0)" id="nomineeShow">Nominee</a> </div> -->
                <!-- <div class="clerfix project_wrap_table">
                  <table>
                    <thead>
                      <tr>
                        <th>Name</th>
                        <th>Relation</th>
                        <th>Nomination For</th>
                        <th>Nomination %</th>
                        <th>Action</th>
                      </tr>
                    </thead>
					  <tbody>
				<tr>
				<td><div class="ExpShow"><i class="fa fa-plus-squa<div id="informationDetailsLoading"></div>re-o" aria-hidden="true"></i>Category 1</div></td>
				<td><div>20/09/2017</div></td>
				<td><div>Cash</div></td>
				<td><div>USD</div></td>
				<td><div>5055</div></td>				
			</tr>
			<tr class="ExpDe">
				<td colspan="6">
					<div class="expDetails">
						<ul>
							<li><strong>Exp Category :</strong><span> Category 1</span><div class="clearfix"></div></li>
							<li><strong>Date :</strong><span> 20/09/2017</span><div class="clearfix"></div></li>
							<li><strong>Payment Mode :</strong><span> Cash</span><div class="clearfix"></div></li>
							<li><strong>Currency :</strong><span> USD</span><div class="clearfix"></div></li>
							<li><strong>Amount :</strong><span> 20,000</span><div class="clearfix"></div></li>
							<li><strong>Purpose :</strong><span> Purpose text</span><div class="clearfix"></div></li>
							<li><strong>Description :</strong><p> Description Description Description Description</p>
							<div class="clearfix"></div>
							</li>
							<div class="clearfix"></div>
						</ul>
					</div>
				</td>			
			</tr>
						  
					  </tbody>
                  </table>
                </div> -->
                
                
                <div class="clearfix"></div>
                
                  
                </div>
              </div>
              
              
  <script>
    function getNomineeDetailsList(){
    	var userId = '${userId}';
    	$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
    		  var sourceleadUrl ='${sourceleadUrl}';
    				  $.ajax({
    				    	type: "GET",
    				        url: sourceleadUrl+"/getNomineeDetails?${_csrf.parameterName}=${_csrf.token}&userId="+userId,
    				        success: function(json) {
    				        	$("#nomineeDeta").show();
    				        	var result = "";			
    	    						result = result + '<table id="nomineeDetailsListTable" class="display" cellspacing="0" width="100%" >';
    	    						result = result + '<thead>';
    	    						result = result + '<tr>';
    	    						result = result + '<th>Name</th>';
    	    						result = result + '<th>Relation</th>';
    	    						result = result + '<th>Nomination For</th>';
    	    						result = result + '<th>Nomination %</th>';
    	    						result = result + '<th>Action</th>';
    	    						result = result + '</tr>';
    	    						result = result + '</thead>	';
    	    						result = result + '<tbody>';
    	    						for (var i=0; i<json.data.length; i++)
    	    						{
    	    							var opt = json.data[i];
	    			        		    result = result + ' <tr>';
	    			        		    if(opt.name !=null && opt.name !='' && opt.name !='undefined'){
	    			        		    	result = result + '<td><div class="tblminwidth-75">'+opt.name+'</div></td>';
	    			        		    }else{
	    			        		    	result = result + '<td><div class="tblminwidth-75">---</div></td>';
	    			        		    }
	    			        		    if(opt.relation !=null && opt.relation !='' && opt.relation !='undefined'){
	    			        		    	result = result + '<td><div>'+opt.relation+'</div></td>';
	    			        		    }else{
	    			        		    	result = result + '<td><div>---</div></td>';
	    			        		    }
	    			        		    if(opt.nominationFor !=null && opt.nominationFor !='' && opt.nominationFor !='undefined'){
	    									result = result + ' <td><div class="tblminwidth-75">'+opt.nominationFor+'</div></td>';
	    								}else{
	    									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
	    								}
	    			        		    if(opt.nominationPercentage !=null && opt.nominationPercentage !='' && opt.nominationPercentage !='undefined'){
	    									result = result + ' <td><div class="tblminwidth-75">'+opt.nominationPercentage+'</div></td>';
	    								}else{
	    									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
	    								}
	    								
	    			        		    result = result + ' <td><div class="tblminwidth-75"><ul class="list-inline list-unstyled mb0"><li><i class="fa fa-pencil cursorPointer mr5" onclick="editNominee('+opt.id+','+opt.candidateId+');"></i></li><li><i class="cursorPointer fa fa-trash" onclick="deleteNominee('+opt.id+','+opt.candidateId+');"></i></li></div></td>';
	    								
	    								result = result + ' </tr>';
    	    						}
    	    						
    	    						result = result + '  </tbody>';
    	    						result = result + ' </table>';
    								$("#nomineeDeta").show();
    								$("#nomineeDetaDiv").show();
    								$("#nomineeDetails").hide();
    								
    	    						$("#nomineeDeta").empty();
    	    						$("#nomineeDeta").append(result);
    	    						$("#loading").removeAttr("style");
    	    						nomineeDetailsPagination();
    				        }
    				  });
    }
    
 </script>         
              
	      </div>
	      
 	  </form:form>
 	  
 	  <div class="clearfix"></div>
                <div class="clearfix mt minHeightcol5" id="nomineeDetails" style="display: none;">
                <form:form class="login-form clearfix"  id="nomineeDetails_form"  name="nomineeDetails_form" method="post" >
                <input type ="hidden" id="nomineeDetailsId" name="id">
                  <div class="col-md-12">
                  <h5>Family Details</h5>
                  </div>
                  <div class="clearfix"></div>
                  <div class="col-md-5">
                    <label>Name<span class="text-danger paddingLeft">*</span></label>
                    <div>
                      <input type="text" id="familyDetailsName" class="form-control" name="name" value="" field-name="Name" data-validation="required validate_length length1-250 validate_Alphabet">
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Date of Birth<span class="text-danger paddingLeft">*</span></label>
                    <div>
                      <input type="text" id="dateOfBirth" class="form-control calendar" name="dateOfBirth" value="" field-name="Date of Birth" data-validation="required">
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Gender</label>
                    <div>
                      <select class="sel" id="gender" name="gender">
                        <option value="">Select</option>
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                        <option value="Transgender">Transgender</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Blood Group</label>
                    <div>
                      <select class="sel" id="bloodGroup" name="bloodGroup">
                        <option selected>Select</option>
                        <option value="A+">A+</option>
                        <option value="B+">B+</option>
                        <option value="AB+">AB+</option>
                        <option value="A-">A-</option>
                        <option value="B-">B-</option>
                        <option value="AB-">AB-</option>
                        <option value="O+">O+</option>
                        <option value="O-">O-</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Relation<span class="text-danger paddingLeft">*</span></label>
                    <div>
                      <select class="sel" id="relation" name="relation" field-name="Relation" data-validation="required">
                        <option value="">Select</option>
                        <option value="Father">Father</option>
                        <option value="Mother">Mother</option>
                        <option value="Brother">Brother</option>
                        <option value="Sister">Sister</option>
                        <option value="Husband">Husband</option>
                        <option value="Wife">Wife</option>
                        <option value="Son">Son</option>
                        <option value="Daughter">Daughter</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Nomination For</label>
                    <div>
                      <select class="sel" id="nominationFor" name="nominationFor">
                        <option value="">Select</option>
                        <option value="PF">PF</option>
                        <option value="ESI">ESI</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Nomination %<span class="text-danger paddingLeft">*</span></label>
                    <div>
                      <input type="number" id="nominationPercentage" class="form-control" name="nominationPercentage" field-name="Nomination Percentage" onInput="return check(event,value)" min="0" max="100" step="0.01" data-validation="required">
                    
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Address</label>
                    <div>
                      <textarea rows="2" id="familyDetailsAdd" class="form-control" name="address" data-validation="validate_Space validate_length length1-500" data-validation-optional = "true" field-name="Address"></textarea>
                    </div>
                  </div>
                  <div class="col-md-5">
                    <div class="row">
                      <div class="col-md-4 mb checkbox-new">
                        <label>&nbsp;</label>
                        <div class="clearfix">
                          <input id="illness" class="checkbox-custom" name="illness" type="checkbox" onClick="illnessFunction()">
                          <label for="illness" class="checkbox-custom-label"><span class="ml10">Illness</span></label>
                        </div>
                      </div>
                      <div class="col-md-4 mb checkbox-new">
                        <label>&nbsp;</label>
                        <div class="clearfix">
                          <input id="minor" class="checkbox-custom" name="minor" type="checkbox" onClick="minorFunction()">
                          <label for="minor" class="checkbox-custom-label"><span class="ml10">Minor</span></label>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="clearfix mt"></div>
                  <div class="clearfix" id="illnessMinor" style="display: none;">
                  <h4 class="col-md-12 margin-top0 ml">Illness</h4>
                    <div class="col-md-5">
                      <label>Guardian Name<span class="text-danger paddingLeft">*</span></label>
                      <div>
                        <input type="text" id="illnessGuardianName" class="form-control" name="illnessGuardianName" field-name="Guardian Name" data-validation="required">
                      </div>
                    </div>
                    <div class="col-md-5">
                      <label>Relation<span class="text-danger paddingLeft">*</span></label>
                      <div>
                        <select class="sel" id="illnessGuardianRelationName" name="illnessGuardianRelationName" field-name="Relation" data-validation="required">
                          <option value="">Select</option>
                            <option value="Father">Father</option>
	                        <option value="Mother">Mother</option>
	                        <option value="Brother">Brother</option>
	                        <option value="Sister">Sister</option>
	                        <option value="Husband">Husband</option>
	                        <option value="Wife">Wife</option>
	                        <option value="Son">Son</option>
	                        <option value="Daughter">Daughter</option>
                        </select>
                      </div>
                    </div>
                    <div class="col-md-5">
                      <label>Address</label>
                      <div>
                        <textarea rows="2" id="illnessGuardianAddress" class="form-control" name="illnessGuardianAddress" field-name="Illness Guardian Address"></textarea>
                      </div>
                    </div>
                    <div class="col-md-5">
                      <label>Phone Number</label>
                      <div>
                        <input type="text" id="illnessGuardianPhoneNumber" class="form-control" name="illnessGuardianPhoneNumber" field-name="Illness Guardian PhoneNumber">
                      </div>
                    </div>
                    <div class="clearfix mt"></div>
                    <div class="col-md-5 mt">
                      <label>Email</label>
                      <div>
                        <input type="text" id="illnessGuardianEmail" class="form-control" name="illnessGuardianEmail" field-name="Illness Guardian Email">
                      </div>
                    </div>
                    <div class="clearfix"></div>
                  </div>
                  <!--  Minor Div -->
                  <div class="clearfix" id="minorDiv" style="display: none;">
                  <h4 class="col-md-12 margin-top0 ml">Minor</h4>
                    <div class="col-md-5">
                      <label>Guardian Name<span class="text-danger paddingLeft">*</span></label>
                      <div>
                        <input type="text" id="minorGuardianName" class="form-control" name="minorGuardianName" field-name="Guardian Name" data-validation="required">
                      </div>
                    </div>
                    <div class="col-md-5">
                      <label>Relation<span class="text-danger paddingLeft">*</span></label>
                      <div>
                        <select class="sel" id="minorGuardianRelationName" name="minorGuardianRelationName" field-name="Relation" data-validation="required">
                          <option value="">Select</option>
                          	<option value="Father">Father</option>
	                        <option value="Mother">Mother</option>
	                        <option value="Brother">Brother</option>
	                        <option value="Sister">Sister</option>
	                        <option value="Husband">Husband</option>
	                        <option value="Wife">Wife</option>
	                        <option value="Son">Son</option>
	                        <option value="Daughter">Daughter</option>
                        </select>
                      </div>
                    </div>
                    <div class="col-md-5">
                      <label>Address</label>
                      <div>
                        <textarea rows="2" id="minorGuardianAddress" class="form-control" name="minorGuardianAddress" field-name="Address"></textarea>
                      </div>
                    </div>
                    <div class="col-md-5">
                      <label>Phone Number</label>
                      <div>
                        <input type="text" id="minorGuardianPhoneNumber" class="form-control" name="minorGuardianPhoneNumber" field-name="Phone Number">
                      </div>
                    </div>
                    <div class="clearfix mt"></div>
                    <div class="col-md-5 mt">
                      <label>Email</label>
                      <div>
                        <input type="text" id="minorGuardianEmail" class="form-control" name="minorGuardianEmail" field-name="Email">
                      </div>
                    </div>
                    <div class="clearfix"></div>
                  </div>
                  
                  
                  <div class="clearfix footerstyelsNew">
			             <div class="col-md-6 col-md-offset-6">
			               <div class="pull-right">
			                 <input value="save" id="saveButton" class="btn btn-success btnSuccess" type="button" onclick="saveNomineeDetails();">
			                 <input value="cancel" id="cancelButton" class="btn btn-cancel btnCancel" type="button" onclick="cancelNomineeDetails();">
			               </div>
			             </div>
			     </div> 
			     
                </form:form>  
                </div>
                
                <div class="clearfix"></div>
	        <div class="modal-footer">
	          <button type="button" class="btn btn-success btnSuccess" id="savePaymentButton" onclick="saveAssociatePfEsiDetails();" >Save</button>
	        </div>
 	  
 	  
</div>



<script>
 function saveAssociatePfEsiDetails(){
	 var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
	 
	 if ($('#associate_informationDetails_form').validate(false, validationSettings)){ 
	        var formData = getFormData("associate_informationDetails_form");
	        $("#informationDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	         $.ajax({
		    	type: "POST",
		    	data: formData,
		    	 url: sourceleadUrl+"/saveOnboardingCandidatePfEsiDetails?${_csrf.parameterName}=${_csrf.token}",
		        success: function(response) {
		        	$("#informationDetailsLoading").removeAttr('style');
		        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
      	    		    showError(response.responseMessage);
      	    		}
		        	else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		 var esipf = response.object;
	                	 $("#pfesiId").val(esipf.id);
		        		
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    });  
	        return true;
	  } else {
		return false;
	  }
 }
 
 $('body').on('blur', '#associate_informationDetails_form', function() {
		$("#associate_informationDetails_form").showHelpOnFocus();
		$("#associate_informationDetails_form").validateOnBlur(false, validationSettings);  
	});
 </script>

<script>
//Pf Function
function associatePfFunction() {
    var checkBox = document.getElementById("pf");
    var pfel = document.getElementById("pfEligible");
    var val = checkBox.checked;
    if (checkBox.checked == true){
        pfel.style.display = "block";
        $("#pfUan").attr("data-validation", "required validate_int validate_length length1-12");
        $("#pfNumber").attr("data-validation", "required validate_AlphaNumber validate_length length1-19");
        $("#pf").val('Y');
    } else {
       pfel.style.display = "none";
    	$('#pfUan').removeAttr('data-validation');
    	$('#pfNumber').removeAttr('data-validation');
    	$("#uanError").find("p.jquery_form_error_message").remove();
    	$("#pfError").find("p.jquery_form_error_message").remove();
    	$("#pfUan").val('');
    	$("#pfNumber").val('');
    	$("#enrolledDate").val('');
    }
}
		//ESI Function
function associateEsiFunction() {
    var checkBox = document.getElementById("esi");
    var esiEl = document.getElementById("esiEligible");
    if (checkBox.checked == true){
       esiEl.style.display = "block";
       $("#esi").val('Y');
       $("#esiNumber").attr("data-validation", "validate_int fixed_length length17");
    } else {
       esiEl.style.display = "none";
       $("#esiNumber").val('');
       $('#esiNumber').removeAttr('data-validation');
    }
}
</script>

<script>
function getDatePicker(){
	var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormatProjectSave = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormatProjectSave = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormatProjectSave = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormatProjectSave = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormatProjectSave = 'yy-dd-mm'; 
	  }
	  $('#enrolledDate').datepicker({
			dateFormat : finalDateFormatProjectSave,
			changeMonth: true,
			changeYear: true, 
			 
		});
}
</script>

<script>
	//to set from date to current month
		$('#dateOfBirth').datepicker({
		dateFormat : 'dd-mm-yy',
		changeMonth: true,
		changeYear: true,
	});
</script>

<script>
	var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	var finalDateFormatProjectSave = '';
	if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormatProjectSave = 'yy-mm-dd';
	}if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormatProjectSave = 'dd-mm-yy'; 
	}if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormatProjectSave = 'mm-dd-yy'; 
	}if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormatProjectSave = 'yy-dd-mm'; 
	}

$("#illness").click(function() {
	$("#illnessMinor").toggle();
});
$("#nomineeShow").click(function() {
	$("#nomineeDetails").toggle();
	$("#nomineeDetails_form").show();
	getNomineeDetailsList();
});
$("#minor").click(function() {
	$("#minorDiv").toggle();
});
</script>


 <script>
 function saveNomineeDetails(){
	 illnessFunction();
	 minorFunction();
	 var userId = '${userId}';
	 if ($('#nomineeDetails_form').validate(false, validationSettings)){ 
	        var formData = getFormData("nomineeDetails_form");
	        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	         $.ajax({
		    	type: "POST",
		    	data: formData,
		    	 url: sourceleadUrl+"/saveNomineeDetails?${_csrf.parameterName}=${_csrf.token}&userId="+userId,
		        success: function(response) {
// 		        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
//       	    		    showError(response.responseMessage);
//       	    		    $("#loading").removeAttr('style');
//       	    		}
// 		        	else if (response.response === "AWKWARD") {
// 		        	    location.href = "/errorPage";
// 		        	} else if (response.response === "SUCCESS") {
		        		//location.replace(sourceleadUrl+"/onboardingCandidateTaxDetails"); 
// 		        		location.replace(sourceleadUrl+"/associateInformationDetails"); 
                        $("#nomineeDetails").hide();
                        getNomineeDetailsList();
// 		        		getNomineeDetails();
// 		        	} else if (response.indexOf('Sign In') !== -1) {
// 		        		location.replace(sourceleadUrl);
// 		        	} else {
// 		        	    showError("Operation failed, please try again");
// 		        	}
		        },
		    });  
	        return true;
	  } else {
		return false;
	  }
 }
 
 $('body').on('blur', '#nomineeDetails_form', function() {
		$("#nomineeDetails_form").showHelpOnFocus();
		$("#nomineeDetails_form").validateOnBlur(false, validationSettings);  
	});
 </script>
 
 
 <script>
function nomineeDetailsPagination(){ 
    $('#nomineeDetailsListTable').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $("#nomineeDetailsListTable").wrap("<div class='overflow_Auto  width100percnt'></div>");
    $('.dataTables_filter input').attr("placeholder", "Search");
}
</script>

<script>
 function addNomineeDetails(){
		$("#nomineeDetails").show();
		$("#nomineeDetails_form").show();
	    $("#familyDetailsName").val('');
	    $("#dateOfBirth").val('');
		$("#gender").val('');
		$("#bloodGroup").val('');
		$("#relation").val('');
		$("#nominationFor").val('');
		$("#nominationPercentage").val('');
		$("#familyDetailsAdd").val(data.address);
		$("#illnessGuardianName").val('');
		$("#illnessGuardianRelationName").val('');
		$("#illnessGuardianAddress").val('');
		$("#illnessGuardianPhoneNumber").val('');
		$("#illnessGuardianEmail").val('');
		$("#minorGuardianName").val('');
		$("#minorGuardianRelationName").val('');
		$("#minorGuardianPhoneNumber").val('');
		$("#minorGuardianEmail").val('');
		$("#nomineeDetailsId").val('');
 }
 </script>
 
 <script> 
function deleteNominee(nomineeId,candidateId){ 
    $.ajax({
  	  type: "POST",
      url: sourceleadUrl+"/deleteNominee?&id="+nomineeId+"&candidateId="+candidateId, 	  
 	  success: function(data) { 
 		 getNomineeDetailsList();
	  }, 
    });
	   
 } 

 </script>
 
 <script>
 function editNominee(nomineeId,candidateId){
	 $.ajax({
	  	  type: "POST",
	      url: sourceleadUrl+"/editNominee?&id="+nomineeId+"&candidateId="+candidateId, 	  
	 	  success: function(data) { 
	 		    $("#nomineeDetails_form").show();
	 		    $("#familyDetailsName").val(data.name);
	 		    $("#dateOfBirth").val(data.dob);
		 		$("#gender").val(data.gender);
		 		$("#bloodGroup").val(data.bloodGroup);
		 		$("#relation").val(data.relation);
		 		$("#nominationFor").val(data.nominationFor);
		 		$("#nominationPercentage").val(data.nominationPercentage);
		 		$("#familyDetailsAdd").val(data.address);
		 		$("#illnessGuardianName").val(data.illnessGuardianName);
		 		$("#illnessGuardianRelationName").val(data.illnessGuardianRelationName);
		 		$("#illnessGuardianAddress").val(data.illnessGuardianAddress);
		 		$("#illnessGuardianPhoneNumber").val(data.illnessGuardianPhoneNumber);
		 		$("#illnessGuardianEmail").val(data.illnessGuardianEmail);
		 		$("#minorGuardianName").val(data.minorGuardianName);
		 		$("#minorGuardianRelationName").val(data.minorGuardianRelationName);
		 		$("#minorGuardianPhoneNumber").val(data.minorGuardianPhoneNumber);
		 		$("#minorGuardianEmail").val(data.minorGuardianEmail);
		 		$("#nomineeDetails").show(data.nomineeDetails);
		 		
		 		$("#nomineeDetailsId").val(data.id);
// 	 		 getNomineeDetailsList();
		  }, 
	    });
 }
 </script>
 
 <script>
function cancelNomineeDetails(){
   $("#nomineeDetails").toggle();
   $("#nomineeDetails_form").hide();
   $('input[name=nominee]').attr('checked', false);
}
</script>

<script>
function illnessFunction(){
	var checkBox = document.getElementById("illness");
	if (checkBox.checked == true){
		$("#illness").show();
		$('#illnessGuardianName').attr('data-validation', "required validate_length length1-250");
		$('#illnessGuardianRelationName').attr('data-validation', "required");
		$('#illnessGuardianPhoneNumber').attr('data-validation-optional', "true");
		$('#illnessGuardianPhoneNumber').attr('data-validation', "validate_space validate_length length7-10");
	    } else {
		 $("#illness").hide();
		 $('#illnessGuardianName').removeAttr('data-validation', "required validate_length length1-250");
	     $('#illnessGuardianRelationName').removeAttr('data-validation', "required");
	     $('#illnessGuardianPhoneNumber').removeAttr('data-validation-optional', "true");
	     $('#illnessGuardianPhoneNumber').removeAttr('data-validation', "validate_space validate_length length7-10");
 }
	
}
</script>

<script>
function minorFunction(){
	var checkBox = document.getElementById("minor");
	if (checkBox.checked == true){
		$("#minor").show();
		$('#minorGuardianName').attr('data-validation', "required validate_length length1-250");
		$('#minorGuardianRelationName').attr('data-validation', "required");
		$('#minorGuardianPhoneNumber').attr('data-validation-optional', "true");
		$('#minorGuardianPhoneNumber').attr('data-validation', "validate_space validate_length length7-10");
	    } else {
		 $("#minor").hide();
		 $('#minorGuardianName').removeAttr('data-validation', "required validate_length length1-250");
	     $('#minorGuardianRelationName').removeAttr('data-validation', "required");
	     $('#minorGuardianPhoneNumber').removeAttr('data-validation-optional', "true");
	     $('#minorGuardianPhoneNumber').removeAttr('data-validation', "validate_space validate_length length7-10");
 }
	
}
</script>

<script>
 check = function (e,value){
		if (!e.target.validity.valid) {
   	e.target.value = value.substring(0,value.length - 1);
     return false;
   }
 	var idx = value.indexOf('.');
   if (idx >= 0) {
     if (value.length - idx > 3 ) {
       e.target.value = value.substring(0,value.length - 1);
       return false;
     }
   }
   return true;
 }
 
</script>
 
 
