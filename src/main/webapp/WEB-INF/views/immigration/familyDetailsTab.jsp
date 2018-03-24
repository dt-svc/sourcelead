<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="immigraTabsHead">
		  	<a href="javascript:void(0)" class="immMenuClick">Add Family <i class="fa fa-plus"></i>
				<div class="immiSubMenu">
					<ul>
						<li id="addSpou"><i class="fa fa-plus"></i> Add Spouse</li>
						<li id="addChil"><i class="fa fa-plus"></i> Add Child</li>
						<li id="addFath"><i class="fa fa-plus"></i> Add Father</li>
						<li id="addMoth"><i class="fa fa-plus"></i> Add Mother</li>
					</ul>
				</div>
		  	</a> 
		  	<span><input class="immigraTabSerach" type="text"></span>
		</div>
	  	<div class="immigraTable">
		  <table>
			<tbody>
			  <tr>
				<th>Name</th>
				<th>Relationship</th>
				<th>Date of Birth</th>
				<th>Place of Birth</th>
				<th>Address</th>
				<th>&nbsp;</th>
			  </tr>
			  <c:forEach var="items" items="${familyList}" varStatus="rows">
			  <tr>
				<td><div class="tbldata-dotdot-me">${items.firstName}</div></td>
				<td><div class="tbldata-dotdot-me">${items.relation}</div></td>
				<td><div class="tbldata-dotdot-vsm"><fmt:formatDate value="${items.dateOfBirth}" pattern="MM/dd/yyyy" /></div></td>
				<td><div class="tbldata-dotdot-vsm">${items.placeOfBirth}</div></td>
				<td><div class="tbldata-dotdot-me">${items.currentAddress}</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr> 
			  </c:forEach>
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
              		<input type="text" id="firstName" name="firstName" class="form-control" placeholder="First Name" field-name="First Name" data-validation="required"/>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Middle Name</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="middleName" name="middleName" placeholder="Middle Name"class="form-control" />
				</div>
			</div> 
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Last Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="lastName" name="lastName" class="form-control" placeholder="last Name" field-name="Last Name" data-validation="required" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Place of Marriage<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="marriagePlace" name="marriagePlace" class="form-control" placeholder="Place Of Marriage" field-name="Place Of Marriage" data-validation="required"/>
				</div>
			</div>                           				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Date of Marriage<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="marraigeDate" name="marraigeDate" class="form-control calendar" placeholder="Date Of Marriage" field-name="Date Of Marriage" data-validation="required"/>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">End Date</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="marriageEndDate" name="marriageEndDate" placeholder="End Date" onclick="showMarraigeEndDatePicker();" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Date of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="dateOfBirthSpouse" name="dateOfBirth" class="form-control calendar" onclick ="showSpouseBirthDatePicker();" placeholder="Date Of Birth" field-name="Date Of Birth" data-validation="required"/>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Place of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="placeOfBirthSpouse" name="placeOfBirth"  class="form-control" placeholder="Place Of Birth" field-name="Place Of Birth" data-validation="required" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="currentAddress" name="currentAddress" class="form-control" placeholder="Address" field-name="Address" data-validation="required"/>
				</div>
			</div>																		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" onclick="immigration.saveCandidateFamilyDetails('Spouse');" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
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
              		<input type="text" id="firstName" name="firstName" class="form-control" placeholder="First Name" field-name="First Name" data-validation="required"/>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Middle Name</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="middleName" name="middleName" class="form-control" placeholder="Middle Name"  />
				</div>
			</div> 
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Last Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="lastName" name="lastName" class="form-control" placeholder="Last Name" field-name="Last Name" data-validation="required" />
				</div>
			</div>                         				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Date of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text"  id="dateOfBirthChild" name="dateOfBirth" class="form-control calendar" placeholder="Date Of Birth" field-name="Date Of Birth" data-validation="required"  />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Place of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="placeOfBirth" name="placeOfBirth" class="form-control" placeholder="Place Of Birth" field-name="Place Of Birth" data-validation="required"/>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="currentAddress" name="currentAddress" field-name="Address"  placeholder="Address" data-validation="required" class="form-control"  />
				</div>
			</div>																		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" onclick="immigration.saveCandidateFamilyDetails('Child');" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
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
              		<input type="text" class="form-control" id="firstName" name="firstName" class="form-control" placeholder="First Name" field-name="First Name" data-validation="required"/>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Middle Name</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" id="middleName" name="middleName" class="form-control" placeholder="Middle Name"  />
				</div>
			</div> 
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Last Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" id="lastName" name="lastName" class="form-control" placeholder="Last Name" field-name="Last Name" data-validation="required" />
				</div>
			</div>                         				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Date of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="dateOfBirthFather" name="dateOfBirth" class="form-control calendar" placeholder="Date Of Birth" field-name="Date Of Birth" data-validation="required"  />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Place of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" id="placeOfBirth" name="placeOfBirth" class="form-control" placeholder="Place Of Birth" field-name="Place Of Birth" data-validation="required"/>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" id="currentAddress" field-name="Address" name="currentAddress" placeholder="Address" data-validation="required" class="form-control" />
				</div>
			</div>																		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" onclick="immigration.saveCandidateFamilyDetails('Father');"class="" type="button">
            <input value="Cancel" class="popCan" type="button">
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
              		<input type="text"  class="form-control" id="firstName" name="firstName" class="form-control" placeholder="First Name" field-name="First Name" data-validation="required"/>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Middle Name</label>
              	<div class="col-md-8 padding_right">
              		<input type="text"  class="form-control" id="middleName" name="middleName" class="form-control" placeholder="Middle Name"  />
				</div>
			</div> 
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Last Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" id="lastName" name="lastName" class="form-control" placeholder="Last Name" field-name="Last Name" data-validation="required" />
				</div>
			</div>                         				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Date of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="dateOfBirthMother" name="dateOfBirth" class="form-control calendar" placeholder="Date Of Birth" field-name="Date Of Birth" data-validation="required"  />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Place of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" id="placeOfBirth" name="placeOfBirth" class="form-control" placeholder="Place Of Birth" field-name="Place Of Birth" data-validation="required"/>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" id="currentAddress" name="currentAddress" placeholder="Address" field-name="Address" data-validation="required" class="form-control" />
				</div>
			</div>																		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" onclick="immigration.saveCandidateFamilyDetails('Mother');"class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>	<!--mother popcode end  -->
	</form>
	<script>
			
			 // Add Spouse
			$("#addSpou").click(function(){
				$(".popMask").fadeIn(200);
				$("#spousePopView").fadeIn(100);
			});
			$("#addChil").click(function(){
				$(".popMask").fadeIn(200);
				$("#childPopView").fadeIn(100);
			});
			$("#addFath").click(function(){
				$(".popMask").fadeIn(200);
				$("#fathPopView").fadeIn(100);
			});
			$("#addMoth").click(function(){
				$(".popMask").fadeIn(200);
				$("#mothPopView").fadeIn(100);
			});	
			
            // cancel popup.
			$(".popCan").click(function(){
				$(".popMask").fadeOut(200);
				$("#spousePopView, #childPopView, #fathPopView, #mothPopView").fadeOut(100);
			});	
		</script>
	
	<script>	
	
	var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormatEduSave = getJSDateFormat(locationDate);
	// marriage start date and end dates
/* 	$('#marraigeDate').datepicker({
	      dateFormat: finalDateFormatEduSave,
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
	      dateFormat: finalDateFormatEduSave,
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
		function showSpouseBirthDatePicker(){
		   $('#dateOfBirthSpouse').datepicker({
		      dateFormat: finalDateFormatPetitionSave,
		      changeMonth: true,
		  	  changeYear: true, 
		  	  yearRange: "-100:+0",
		  	  maxDate:0  
		  }).datepicker("show");
		}  
		//
		// child birth date dateOfBirthChild
	
		   $('#dateOfBirthChild').datepicker({
		      dateFormat: finalDateFormatPetitionSave,
		      changeMonth: true,
		  	  changeYear: true, 
		  	  yearRange: "-100:+0",
		  	  maxDate:0  
		  }).datepicker("show");
		  
		// Father birth date dateOfBirthFather
		function showChildBirthDatePicker(){
		   $('#dateOfBirthFather').datepicker({
		      dateFormat: finalDateFormatPetitionSave,
		      changeMonth: true,
		  	  changeYear: true, 
		  	  yearRange: "-100:+0",
		  	  maxDate:0  
		  }).datepicker("show");
		}  
		
		// Mother birth date dateOfBirthMother
		function showChildBirthDatePicker(){
		   $('#dateOfBirthMother').datepicker({
		      dateFormat: finalDateFormatPetitionSave,
		      changeMonth: true,
		  	  changeYear: true, 
		  	  yearRange: "-100:+0",
		  	  maxDate:0  
		  }).datepicker("show");
		}  
		 */
	</script>