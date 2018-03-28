<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="immigraTabsHead">			  	
		  	<a href="javascript:void(0)" id="addPass"><i class="glyphicon glyphicon-plus"></i> Add Passport</a> 
		  	<span><input class="immigraTabSerach" type="text"></span>
		</div>
		
	  	<div class="immigraTable">
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
				<td><div class="tbldata-dotdot-big">${!empty passportdetails.idNumber ? passportdetails.idNumber : '---'}</div></td>
				<td><div class="tbldata-dotdot-me">${!empty passportdetails.country ? passportdetails.country : '---'}</div></td>
				<td><div class="tbldata-dotdot-me">${!empty passportdetails.validFromDateStr ? passportdetails.validFromDateStr : '---'}</div></td>
				<td><div class="tbldata-dotdot-me">${!empty passportdetails.validToDateStr ? passportdetails.validToDateStr : '---'}</div></td>
				<td><div><i id="edit" onclick="immigration.editPassportVisaDetailsByRowId(${passportdetails.id},'${passportdetails.idType}');"
				class="glyphicon glyphicon-pencil"></i><i id="delete" 
				 onclick="immigration.deletePassportVisaDetailsById(${passportdetails.id},'${passportdetails.idType}');" 
				 class="glyphicon glyphicon-trash ml10"></i></div></td>
				 
				<!-- <td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>immMenuClick
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td> -->
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
              		<input type="text" name = "idNumber" id = "idNumber" field-name = "Passport Id" class="form-control" data-validation = "required"/>
				</div>
			</div>				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Country</label>
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
				<label class="col-md-4 padding_left">Valid From</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" value="" name = "validFrom" id = "validFrom" field-name = "Valid From" data-validation="required"/>
				    <div class="clearDate" onClick="clearCandidateCertStartDate();"></div>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Valid To</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" value="" name = "validTo" id = "validTo" field-name = "Valid To" data-validation="required"/>
				<div class="clearDate" onClick="clearCandidateCertEndDate();"></div>
				</div>
			</div>									
		</div>
		
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button" id="passport_save_id" onclick="immigration.savePassportVisaDetails('Passport')">
            <input value="Cancel" class="popCan" type="button" onClick="popFun();">
          </div>		
	</div><!--popcode end  -->
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
			}
		</script>
		<script>
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatCerSave = getJSDateFormat(locationDate);
		 $('#validFrom').datepicker({
		      dateFormat: finalDateFormatCerSave,
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
		      dateFormat: finalDateFormatCerSave,
		      changeMonth: true,
		  	  changeYear: true, 
		        onSelect: function (selected) {
		            //var dt = new Date(selected);
		            var dt = 	  $(this).datepicker('getDate', '-1d');
		            dt.setDate(dt.getDate() - 1);
		            $("#validFrom").datepicker("option", "maxDate", dt);
		        }
		  });
		</script>
		<script>
		<script>
		function clearCandidateCertStartDate(){
			$("#validFrom").val("");
			};
		function clearCandidateCertEndDate(){
			$("#validTo").val("");
			};
		</script>   
<script>
function toDate(selector) {
    var from = $(selector).val().split("-");
    return new Date(from[2], from[1] - 1, from[0]);
}
</script>
		
		
		