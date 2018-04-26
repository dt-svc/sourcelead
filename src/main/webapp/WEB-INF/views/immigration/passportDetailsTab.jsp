<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="immigraTabsHead">			  	
		  	<a href="javascript:void(0)" id="addPass"><i class="glyphicon glyphicon-plus"></i> Add Passport</a> 
		  	<span><input class="immigraTabSerach"  id = "searchValuePassport" placeholder="Passport Id/Country" type="text"></span>
		</div>
		
	  	<div class="immigraTable overflow_Auto">
		  <table>
			<tbody>
			  <tr>
				<th>Passport Id</th>
				<th>Country</th>
				<th>Valid From</th>
				<th>Valid To</th>
				<th>Action</th>
			  </tr>
			  <c:forEach var="passportdetails" items="${PassportList}" varStatus="rows">
			  <tr>
				<td><div class="tbldata-dotdot-custom">${!empty passportdetails.idNumber ? passportdetails.idNumber : '---'}</div></td>
				<td><div class="tbldata-dotdot-me">${!empty passportdetails.country ? passportdetails.country : '---'}</div></td>
				<td><div class="tbldata-dotdot-me">${!empty passportdetails.validFromDateStr ? passportdetails.validFromDateStr : '---'}</div></td>
				<td><div class="tbldata-dotdot-me">${!empty passportdetails.validToDateStr ? passportdetails.validToDateStr : '---'}</div></td>
         		<td><div><i id="edit" onclick="immigration.editPassportVisaDetailsByRowId(${passportdetails.id},'${passportdetails.idType}');"
				class="glyphicon glyphicon-pencil cursorPointer"></i><i id="delete" 
				 onclick="immigration.deletePassportVisaDetailsById(${passportdetails.id},'${passportdetails.idType}');" 
				 class="glyphicon glyphicon-trash ml10 cursorPointer"></i></div></td>
			  </tr>  
			  </c:forEach>			 				
			</tbody>
		  </table>
		</div>
		
		<!--popup code start -->
		<form id="CandidatePassportform" name="CandidatePassportform" method="post"> 
		<input type="hidden" id="Passport" name="Passport" value="Passport" >
		<div class="popUpOut" id="popPassView">
		<input type = "hidden" id="candidatePassportId"  name="candidatePassportId">
		<div class="popupHead">
			<h2>Add Passport</h2><a href="javascript:void(0)" class="fa fa-close popCan" onClick="popFun();"></a>
		</div>
		
		<div class="popupMid clearfix">
        
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Passport Id<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" name = "idNumber" id = "idNumber" placeholder="Passport Id" field-name = "Passport Id" class="form-control" data-validation = "required"/>
				</div>
			</div>				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Country<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<select class="chosen-select form-control" name="country" id= "countryName" field-name="Country" data-validation="required">
					<option value="" selected>--Select Country--</option>
					  <c:forEach var="country" items="${countryList}">
						<option value="${country.name}">${country.name}</option>
					</c:forEach>
					</select>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Valid From<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              	<div class="input-group errorInptgroup">
              	<input type="text" class="form-control calendar" value=""  placeholder="Valid From" name = "validFrom" id = "validFrom" field-name = "Valid From" data-validation="required"/>
				   <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onClick="clearDateValue('validFrom');"></i></span>
				</div>
              		
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Valid To<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              	<div class="input-group errorInptgroup">
              	<input type="text" class="form-control calendar" value="" name = "validTo" id = "validTo" placeholder="Valid To" field-name = "Valid To" data-validation="required"/>
				<span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onClick="clearDateValue('validTo');"></i></span>
				</div>
				</div>
			</div>		
			<div class="clearfix"></div>							
		<div class="immiPopFoot mt clearfix">
            <input value="Save" class="" type="button" id="passport_save_id" onclick="immigration.savePassportVisaDetails('Passport')">
            <input value="Cancel" class="popCan" type="button" onClick="popFun();">
          </div>		
	</div>
	</div>
	
	
		<div class="immFoot clearfix">
			<div class="pull-right text-right">
				<input type="button" value="Back" onclick="goToSummaryTab('#detailsTab');" class="btn btn-default btn-sm">
             	<input type="button" value="Skip" onclick="goToSummaryTab('#visasTab');" class="btn btn-default btn-sm"> 
              	<input type="button" value="Continue" onclick="goToSummaryTab('#visasTab');" class="btn btn-primary btn-sm ml10">
            </div>
		</div>	
	
	<!--popcode end  -->
		</form>
		
		<script>
		 // Add addPass Div  
			$("#addPass").click(function(){
				$(".popMask").fadeIn(200);
				$("#popPassView").fadeIn(100);
			});	
			function popFun() {
				$(".popMask").fadeOut(200);
				$("#popPassView").fadeOut(200);
				$("#CandidatePassportform")[0].reset();
				$("#candidatePassportId").val('');
			}
		</script>
		<script>
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatPassSave = getJSDateFormat(locationDate);
		 $('#validFrom').datepicker({
		      dateFormat: finalDateFormatPassSave,
		      changeMonth: true,
		  	  changeYear: true, 
		      onSelect: function (selected) {
		           // var dt = new Date(selected);
		           var dt = 	  $(this).datepicker('getDate', '+1d'); 
		           dt.setDate(dt.getDate() + 1);
		            $("#validTo").datepicker("option", "minDate", dt);
		        }
		  });
		  
		  $('#validTo').datepicker({
		      dateFormat: finalDateFormatPassSave,
		      changeMonth: true,
		  	  changeYear: true, 
		        onSelect: function (selected) {
		            //var dt = new Date(selected);
		            var dt = 	  $(this).datepicker('getDate', '-1d');
		            dt.setDate(dt.getDate() - 1);
		            $("#validFrom").datepicker("option", "maxDate", dt);
		        }
		  });
		  $("#searchValuePassport").keyup(function (e) {
			    if (e.which === 13) {
			    	searchPassportValue();
			    }
			});
		</script>
		
		
		
		