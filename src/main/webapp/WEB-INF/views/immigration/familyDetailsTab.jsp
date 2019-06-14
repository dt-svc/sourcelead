<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <c:set var ="familyListCount" value ="${fn:length(familyList)}"/>
<div class="immigraTabsHead">
				<c:choose>
				    <c:when test="${myImmigrationPage == 'N'}">
				        <a href="javascript:void(0)" class="immMenuClick"><i class="glyphicon glyphicon-plus"></i> Add Family 
							<div class="immiSubMenu">
								<ul>
									<li id="addSpou"><i class="fa fa-plus"></i> Add Spouse</li>
									<li id="addChil"><i class="fa fa-plus"></i> Add Child</li>
									<li id="addFath"><i class="fa fa-plus"></i> Add Father</li>
									<li id="addMoth"><i class="fa fa-plus"></i> Add Mother</li>
								</ul>
							</div>
					  	</a>
				    </c:when>
				    <c:otherwise>
				        <c:choose>
						    <c:when test="${curUscisMyImmi == 'Request to fill' || curUscisMyImmi == 'Request for modifications'}">
						        <a href="javascript:void(0)" class="immMenuClick"><i class="glyphicon glyphicon-plus"></i> Add Family 
									<div class="immiSubMenu">
										<ul>
											<li id="addSpou"><i class="fa fa-plus"></i> Add Spouse</li>
											<li id="addChil"><i class="fa fa-plus"></i> Add Child</li>
											<li id="addFath"><i class="fa fa-plus"></i> Add Father</li>
											<li id="addMoth"><i class="fa fa-plus"></i> Add Mother</li>
										</ul>
									</div>
							  	</a>
						    </c:when>
						    <c:otherwise>
						    </c:otherwise>
						</c:choose>
				    </c:otherwise>
				</c:choose>
		  	<span><input class="immigraTabSerach" id="familySerach" type="text" placeholder="Name/Relationship"></span>
		</div>
	  	<div class="immigraTable overflow_Auto">
		  <table>
			<tbody>
			  <tr>
				<th>Name</th>
				<th>Relationship</th>
				<th>Date of Birth</th>
				<th>Place of Birth</th>
				<th>Address</th>
				<c:choose>
				    <c:when test="${myImmigrationPage == 'N'}">
				        <th>Action</th>
				    </c:when>
				    <c:otherwise>
				        <c:choose>
						    <c:when test="${curUscisMyImmi == 'Request to fill' || curUscisMyImmi == 'Request for modifications'}">
						        <th>Action</th>
						    </c:when>
						    <c:otherwise>
						    </c:otherwise>
						</c:choose>
				    </c:otherwise>
				</c:choose>
			  </tr>
			  <c:forEach var="items" items="${familyList}" varStatus="rows">
			  <tr>
				<td><div class="tbldata-dotdot inlineBlock" title = "${items.firstName}">${items.firstName}</div></td>
				<td><div class="tbldata-dotdot-100 inlineBlock" title = "${items.relation}">${items.relation}</div></td>
				<td><div class="tblminwidth-75" title = "${items.dateOfBirthStr}">${items.dateOfBirthStr}</div></td>
				<td><div class="tbldata-dotdot-vsm" title = "${items.placeOfBirth}">${!empty items.placeOfBirth ? items.placeOfBirth : '---'}</div></td>
				<td><div class="tbldata-dotdot-me" title = "${items.currentAddress}">${!empty items.currentAddress ? items.currentAddress : '---'}</div></td>
				<c:choose>
				    <c:when test="${myImmigrationPage == 'N'}">
				        <td><div><i id="edit" class="glyphicon glyphicon-pencil cursorPointer"onclick="immigration.editFamilyDetailsById(${items.id},'${items.relation}');"
							></i><i id="delete" class="glyphicon glyphicon-trash ml10 cursorPointer"
							 onclick="deleteImmigrationRecord('immigration.deleteFamilyDetailsById',${items.id});" 
							 ></i></div></td>
				    </c:when>
				    <c:otherwise>
				        <c:choose>
						    <c:when test="${curUscisMyImmi == 'Request to fill' || curUscisMyImmi == 'Request for modifications'}">
						        <td><div><i id="edit" class="glyphicon glyphicon-pencil cursorPointer"onclick="immigration.editFamilyDetailsById(${items.id},'${items.relation}');"
									></i><i id="delete" class="glyphicon glyphicon-trash ml10 cursorPointer"
									 onclick="deleteImmigrationRecord('immigration.deleteFamilyDetailsById',${items.id});" 
									 ></i></div></td>
						    </c:when>
						    <c:otherwise>
						    </c:otherwise>
						</c:choose>
				    </c:otherwise>
				</c:choose>
			  </tr> 
			  </c:forEach>
			   <c:if test="${empty familyList}">
				<tr>
				 <td colspan="5" class="dataTables_empty" valign="top"></td>
				</tr>
			 </c:if>		
			 </tbody>
		  </table>
		</div>
		
		<!-- <div class="immigraPagi clearfix">
			<div class="immiPagiText">Showing 1 to 6 of 6 entries</div>
			  <ul class="pagination">
				<li><a href="#"> << </a></li>
				<li><a href="#">2</a></li>
				<li><a href="#">3</a></li>
				<li class="disabled"><a href="#">6</a></li>
				<li data-page="last"><a href="#"> >> </a></li>
			  </ul>
		</div> -->
		
		<!--   Family Popup's  popcode start-->  
  <form name ="spouseDetailsForm" id ="spouseDetailsForm">
	<div class="popUpOut" id="spousePopView"><!--  spouse popup start -->
		<div class="popupHead">
			<h2>Add Spouse</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">First Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="firstName" name="firstName" class="form-control" placeholder="First Name" field-name="First Name" data-validation="required validate_Space validate_AlphaNumber validate_length length1-100"/>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Middle Name</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="middleName" name="middleName" placeholder="Middle Name"class="form-control" data-validation="required validate_Space validate_AlphaNumber validate_length length1-100" data-validation-optional="true"/>
				</div>
			</div> 
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Last Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="lastName" name="lastName" class="form-control" placeholder="last Name" field-name="Last Name" data-validation="required validate_Space validate_AlphaNumber validate_length length1-100" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Place of Marriage<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="marriagePlace" name="marriagePlace" class="form-control" placeholder="Place Of Marriage" field-name="Place Of Marriage" data-validation="required validate_Space validate_AlphaNumber validate_length length1-100"/>
				</div>
			</div>                           				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Date of Marriage<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              	<div class="input-group errorInptgroup">
              		<input type="text" id="marraigeDate" name="marraigeDate" class="form-control calendar" placeholder="Date Of Marriage" field-name="Date Of Marriage" readonly="readonly" data-validation="required"/>
              		<span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('marraigeDate');"></i></span>
				</div>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">End Date</label>
              	<div class="col-md-8 padding_right">
              	<div class="input-group">
              		<input type="text" id="marriageEndDate" name="marriageEndDate" placeholder="End Date" readonly="readonly" class="form-control calendar" />
              		<span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('marriageEndDate');"></i></span>
				</div>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Date of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              	<div class="input-group errorInptgroup">
              		<input type="text" id="dateOfBirthSpouse" name="dateOfBirth" class="form-control calendar"  placeholder="Date Of Birth" field-name="Date Of Birth" readonly="readonly" data-validation="required"/>
              		<span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('dateOfBirthSpouse');"></i></span>
				</div>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Place of Birth</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="placeOfBirthSpouse" name="placeOfBirth"  class="form-control" placeholder="Place Of Birth" data-validation="required validate_Space validate_AlphaNumber validate_length length2-100" data-validation-optional="true"/>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              	<div class="col-md-8 padding_right">
              	<input class="form-control positionRelative" id="currentAddress" name="currentAddress" onclick="initLocationAuto('currentAddress');" placeholder="Address"  type="text" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true"></input>
          				<i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('currentAddress');"></i>
				</div>
			</div>																		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" id= "family_SaveButton" onclick="immigration.saveCandidateFamilyDetails('Spouse');" class="" type="button">
            <input value="Cancel" class="popCan" type="button" onClick="popFun();">
          </div>		
	</div>	<!--  spouse popup end -->
	</form>.
	<form name ="childDetailsForm" id ="childDetailsForm"><!--  child popup start -->
	<div class="popUpOut" id="childPopView">
		<div class="popupHead">
			<h2>Add Child</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">First Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="firstName" name="firstName" class="form-control" placeholder="First Name" field-name="First Name" data-validation="required validate_Space validate_AlphaNumber validate_length length1-100"/>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Middle Name</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="middleName" name="middleName" class="form-control" placeholder="Middle Name"  data-validation="required validate_Space validate_AlphaNumber validate_length length1-100" data-validation-optional="true"/>
				</div>
			</div> 
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Last Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="lastName" name="lastName" class="form-control" placeholder="Last Name" field-name="Last Name" data-validation="required validate_Space validate_AlphaNumber validate_length length1-100" />
				</div>
			</div>                         				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Date of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              	<div class="input-group errorInptgroup">
              		<input type="text"  id="dateOfBirthChild" name="dateOfBirth" class="form-control calendar" placeholder="Date Of Birth" field-name="Date Of Birth" readonly="readonly" data-validation="required"  />
              		<span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('dateOfBirthChild');"></i></span>
				</div>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Place of Birth</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="placeOfBirth" name="placeOfBirth" class="form-control" placeholder="Place Of Birth" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true"/>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              	<div class="col-md-8 padding_right">
              	<input class="form-control positionRelative" id="currentAddressChild" name="currentAddress" onclick="initLocationAuto('currentAddressChild');" placeholder="Address"  type="text" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true"></input>
          				<i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('currentAddressChild');"></i>
				
				</div>
			</div>																		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" id= "family_SaveButton" onclick="immigration.saveCandidateFamilyDetails('Child');" class="" type="button">
            <input value="Cancel" class="popCan" type="button" onClick="popFun();">
          </div>		
	</div><!--  child popup end -->	
	</form>
	<form name ="fatherDetailsForm" id ="fatherDetailsForm"><!--  Father popup start -->
	<div class="popUpOut" id="fathPopView">
		<div class="popupHead">
			<h2>Add Father</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">First Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" id="firstName" name="firstName" class="form-control" placeholder="First Name" field-name="First Name" data-validation="required validate_Space validate_AlphaNumber validate_length length1-100"/>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Middle Name</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" id="middleName" name="middleName" class="form-control" placeholder="Middle Name"  data-validation="required validate_Space validate_AlphaNumber validate_length length1-100" data-validation-optional="true"/>
				</div>
			</div> 
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Last Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" id="lastName" name="lastName" class="form-control" placeholder="Last Name" field-name="Last Name" data-validation="required validate_Space validate_AlphaNumber validate_length length1-100" />
				</div>
			</div>                         				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Date of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              	<div class="input-group errorInptgroup">
              		<input type="text" id="dateOfBirthFather" name="dateOfBirth" class="form-control calendar" placeholder="Date Of Birth" field-name="Date Of Birth" readonly="readonly" data-validation="required"  />
              		<span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('dateOfBirthFather');"></i></span>
				</div>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Place of Birth</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" id="placeOfBirth" name="placeOfBirth" class="form-control" placeholder="Place Of Birth" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true"/>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              	<div class="col-md-8 padding_right">
              	<input class="form-control positionRelative" id="currentAddressFather" name="currentAddress" onclick="initLocationAuto('currentAddressFather');" placeholder="Address"  type="text" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true"></input>
          				<i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('currentAddressFather');"></i>
				
				</div>
			</div>																		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" id= "family_SaveButton" onclick="immigration.saveCandidateFamilyDetails('Father');"class="" type="button">
            <input value="Cancel" class="popCan" type="button" onClick="popFun();">
          </div>		
	</div>	<!--  Father popup start -->
	</form>
	<form name ="motherDetailsForm" id ="motherDetailsForm"><!--  mother popup start -->
	<div class="popUpOut" id="mothPopView">
		<div class="popupHead">
			<h2>Add Mother</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">First Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text"  class="form-control" id="firstName" name="firstName" class="form-control" placeholder="First Name" field-name="First Name" data-validation="required validate_Space validate_AlphaNumber validate_length length1-100"/>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Middle Name</label>
              	<div class="col-md-8 padding_right">
              		<input type="text"  class="form-control" id="middleName" name="middleName" class="form-control" placeholder="Middle Name"  data-validation="required validate_Space validate_AlphaNumber validate_length length1-100" data-validation-optional="true"/>
				</div>
			</div> 
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Last Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" id="lastName" name="lastName" class="form-control" placeholder="Last Name" field-name="Last Name" data-validation="required validate_Space validate_AlphaNumber validate_length length1-100" />
				</div>
			</div>                         				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Date of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              	<div class="input-group errorInptgroup">
              		<input type="text" id="dateOfBirthMother" name="dateOfBirth" class="form-control calendar" placeholder="Date Of Birth" field-name="Date Of Birth" readonly="readonly" data-validation="required"  />
              		<span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('dateOfBirthMother');"></i></span>
				</div>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Place of Birth</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" id="placeOfBirth" name="placeOfBirth" class="form-control" placeholder="Place Of Birth" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true"/>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              	<div class="col-md-8 padding_right">
              	<input class="form-control positionRelative" id="currentAddressMother" name="currentAddress" onclick="initLocationAuto('currentAddressMother');" placeholder="Address"  type="text" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true"></input>
          				<i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('currentAddressMother');"></i>
				
				</div>
			</div>																		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" id= "family_SaveButton" onclick="immigration.saveCandidateFamilyDetails('Mother');"class="" type="button">
            <input value="Cancel" class="popCan" type="button" onClick="popFun();">
          </div>		
	</div>	<!--mother popcode end  -->
	<div class="immFoot clearfix">
			<div class="pull-right text-right">
				<input type="button" value="Back" onclick="goToSummaryTab('#residencesTab');" class="btn btn-default btn-sm">
             	<input type="button" value="Skip" onclick="goToSummaryNextTab('#formsTab');" class="btn btn-default btn-sm"> 
              	<input type="button" value="Continue" onclick="goToSummaryNextTab('#formsTab');" class="btn btn-primary btn-sm ml10">
            </div>
		</div>
	</form>
	
	
	<script>
	
	$(document).on('change','#placeOfBirthSpouse, #placeOfBirth', function () {
		if (this.value.match(/[^a-zA-Z0-9 ]/g)) {
			this.value = this.value.replace(/[^a-zA-Z0-9 ]/g, '');
		}
	});
	   function resetFormData(id){
		   $('#'+id)[0].reset() // reset the form data.
	   }	
	
			 // Add Spouse
			$("#addSpou").click(function(){
				$(".popMask").fadeIn(200);
				$("#spousePopView").fadeIn(100);
				resetFormData("spouseDetailsForm");
			});
			$("#addChil").click(function(){
				$(".popMask").fadeIn(200);
				$("#childPopView").fadeIn(100);
				resetFormData("childDetailsForm");
			});
			$("#addFath").click(function(){
				$(".popMask").fadeIn(200);
				$("#fathPopView").fadeIn(100);
				resetFormData("fatherDetailsForm");
			});
			$("#addMoth").click(function(){
				$(".popMask").fadeIn(200);
				$("#mothPopView").fadeIn(100);
				resetFormData("motherDetailsForm"); 
			});	
			
            // cancel popup.
			$(".popCan").click(function(){
				$(".popMask").fadeOut(200);
				$("#spousePopView, #childPopView, #fathPopView, #mothPopView").fadeOut(100);
				familyMemberId =""; 
			});	
            
            
			function popFun() {
				$('#spousePopView').find('p.jquery_form_error_message').remove();
				$('#childPopView').find('p.jquery_form_error_message').remove();
				$('#fathPopView').find('p.jquery_form_error_message').remove();
				$('#mothPopView').find('p.jquery_form_error_message').remove();
				$("#family_SaveButton").removeAttr("disabled");
			}
		
		
		</script>
	
	<script>	
	familyMemberId ="" // empty the value of familyMemberId which is used for edit mode. 
	
	var locationDate ='${SourceLeadWeb_dateFormat}'; 
	  var finalDateFormatFamilySave = getJSDateFormat(locationDate);
	</script>
	<script>
	$(document).ready(function() {
	// marriage start date and end dates
 	$('#marraigeDate').datepicker({
	      dateFormat: finalDateFormatFamilySave,
	      changeMonth: true,
	  	  changeYear: true, 
	      onSelect: function (selected) {
	            //var dt = new Date(selected);
	            var dt = $(this).datepicker('getDate', '+1d');
	            dt.setDate(dt.getDate() + 1);
	            $("#marriageEndDate").datepicker("option", "minDate", dt);
	        }
	  });
	  
	  $('#marriageEndDate').datepicker({
	      dateFormat: finalDateFormatFamilySave,
	      changeMonth: true,
	  	  changeYear: true, 
	        onSelect: function (selected) {
	            //var dt = new Date(selected);
	            var dt = $(this).datepicker('getDate', '-1d');
	            dt.setDate(dt.getDate() - 1);
	            $("#marraigeDate").datepicker("option", "maxDate", dt);
	        }
	  });
	
		// spouse birth date dateOfBirthSpouse 
		   $('#dateOfBirthSpouse').datepicker({
		      dateFormat: finalDateFormatFamilySave,
		      changeMonth: true,
		  	  changeYear: true, 
		  	  yearRange: "-100:+0",
		  	  maxDate:0  
		  });
		
		// child birth date  dateOfBirthChild
		   $('#dateOfBirthChild').datepicker({
		      dateFormat: finalDateFormatFamilySave,
		      changeMonth: true,
		  	  changeYear: true, 
		  	  yearRange: "-100:+0",
		  	  maxDate:0  
		  })
		  
		// Father birth date  dateOfBirthFather
		   $('#dateOfBirthFather').datepicker({
		      dateFormat: finalDateFormatFamilySave,
		      changeMonth: true,
		  	  changeYear: true, 
		  	  yearRange: "-100:+0",
		  	  maxDate:0  
		  });
		
		// Mother birth date  dateOfBirthMother
		   $('#dateOfBirthMother').datepicker({
		      dateFormat: finalDateFormatFamilySave,
		      changeMonth: true,
		  	  changeYear: true, 
		  	  yearRange: "-100:+0",
		  	  maxDate:0  
		  });
	 });
	
	$("#familySerach").keyup(function (e) {
        if (e.which === 13) {
        	immigration.displayFamilyDetails(candidateId,'${curUscisMyImmi}');
        }
    });
	</script>
	<script>
  var familyListCount = '<c:out value ="${familyListCount}"/>';
  var initCount  = '<c:out value ="${requestScope.familyOnLoadCount}"/>';
  if(familyListCount>0 || initCount!= ""){
        $('#familyfilled').show() // show and hides circle icon.
    }
	else {
        $('#familyfilled').hide()
	}
</script>