<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="immigraTabsHead">			  	
		  	<a href="javascript:void(0)" id="addVisa"><i class="glyphicon glyphicon-plus"></i> Add Visa</a> 
		  	<span><input class="immigraTabSerach" type="text"></span>
		</div>
		
	  	<div class="immigraTable">
		  <table>
			<tbody>
			  <tr>
				<th>Visa Type</th>
				<th>Visa Id</th>
				<th>Country</th>
				<th>Valid From</th>
				<th>Valid To</th>
				<th>Action</th>
			  </tr>
			  <c:forEach var="visadetails" items="${PassportList}" varStatus="rows">
			  <tr>
				<td><div class="tbldata-dotdot-sm">${visadetails.visaType}</div></td>
				<td><div class="tbldata-dotdot-sm">${visadetails.idNumber}</div></td>
				<td><div class="tbldata-dotdot-sm">${visadetails.country}</div></td>
				<td><div class="tbldata-dotdot-sm">${visadetails.validFromDate}</div></td>
				<td><div class="tbldata-dotdot-sm">${visadetails.validToDate}</div></td>
				<td><div><i id="edit" onclick="immigration.editPassportVisaDetailsByRowId(${visadetails.id});"
				class="glyphicon glyphicon-pencil"></i><i id="delete" 
				 onclick="immigration.deletePassportVisaDetailsById(${visadetails.id},'${visadetails.idType}');" 
				 class="glyphicon glyphicon-trash ml10"></i></div></td>
				<!-- <td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
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
			 
		<!--popcode start  -->
		<form id="CandidateVisaform" name="CandidateVisaform" method="post"> 
		 <input type="hidden" id="Visa" name="Visa" value="Visa">
		<div class="popUpOut" id="popUpVisa">
		<input type = "hidden" id="candidatePassportId"  name="candidatePassportId">
		<div class="popupHead">
			<h2>Add Visa</h2><a href="javascript:void(0)" class="fa fa-close popCan" onClick="popFun();"></a>
		</div>
		
	<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Visa Type</label>
              	<div class="col-md-8 padding_right">
					<select class="chosen-select form-control" name = "visaType" id = "visaType" field-name = "Visa Type"  data-validation = "required" >
					  <option>Select Visa Type</option>
					  <option>H1B</option>
					  <option>H2B</option>
					</select>
				</div>
			</div>
         
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Visa Id<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" name = "idNumber" id = "idNumber" field-name = "Visa Id" class="form-control" data-validation = "required"/>
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
              		<input type="text" class="form-control calendar" value="" name = "validFrom" id = "validfrom" field-name = "Valid From" data-validation="required"/>
				    <div class="clearDate" onClick="clearCandidateCertStartDate();"></div>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Valid To</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" value="" name = "validTo" id = "validto" field-name = "Valid To" data-validation="required"/>
				<div class="clearDate" onClick="clearCandidateCertEndDate();"></div>
				</div>
			</div>									
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button" id="visa_save_id" onclick="immigration.savePassportVisaDetails('Visa')">
            <input value="Cancel" class="popCan" type="button" onClick="popFun();">
          </div>		
	</div><!--popcode end  -->	
	</form>
	<script>
		 // Add addVisa Div  
			$("#addVisa").click(function(){
				$(".popMask").fadeIn(200);
				$("#popUpVisa").fadeIn(100);
			});	
			function popFun() {
				$(".popMask").fadeOut(200);
				$("#popUpVisa").fadeOut(200);
			}
		   	
		</script>
		<script>
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatCerSave = getJSDateFormat(locationDate);
		 $('#validfrom').datepicker({
		      dateFormat: finalDateFormatCerSave,
		      changeMonth: true,
		  	  changeYear: true, 
		      onSelect: function (selected) {
		           // var dt = new Date(selected);
		           var dt = 	  $(this).datepicker('getDate', '+1d'); 
		           dt.setDate(dt.getDate() + 1);
		            $("#validto").datepicker("option", "minDate", dt);
		        }
		  });
		  
		  $('#validto').datepicker({
		      dateFormat: finalDateFormatCerSave,
		      changeMonth: true,
		  	  changeYear: true, 
		        onSelect: function (selected) {
		            //var dt = new Date(selected);
		            var dt = 	  $(this).datepicker('getDate', '-1d');
		            dt.setDate(dt.getDate() - 1);
		            $("#validfrom").datepicker("option", "maxDate", dt);
		        }
		  });
		</script>
		<script>
		<script>
		function clearCandidateCertStartDate(){
			$("#validfrom").val("");
			};
		function clearCandidateCertEndDate(){
			$("#validto").val("");
			};
		</script>   
<script>
function toDate(selector) {
    var from = $(selector).val().split("-");
    return new Date(from[2], from[1] - 1, from[0]);
}
</script>
		
		