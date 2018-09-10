<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <c:set var ="visaListCount" value ="${fn:length(PassportList)}"/>
<div class="immigraTabsHead">
			<c:choose>
				    <c:when test="${myImmigrationPage == 'N'}">
				        <a href="javascript:void(0)" id="addVisa"><i class="glyphicon glyphicon-plus"></i> Add Visa</a>
				    </c:when>
				    <c:otherwise>
				        <c:choose>
						    <c:when test="${curUscisMyImmi == 'Request to fill' || curUscisMyImmi == 'Request for modifications'}">
						        <a href="javascript:void(0)" id="addVisa"><i class="glyphicon glyphicon-plus"></i> Add Visa</a>
						    </c:when>
						    <c:otherwise>
						    </c:otherwise>
						</c:choose>
				    </c:otherwise>
				</c:choose>			  	
		  	<span><input class="immigraTabSerach"  id = "searchValueVisa" placeholder="Visa Type/Visa Id/Country" type="text" ></span>
		</div>
		
	  	<div class="immigraTable">
		  <table id ="visaTbl">
			<tbody>
			  <tr>
				<th>Visa Type</th>
				<th>Visa Id</th>
				<th>Country</th>
				<th>Valid From</th>
				<th>Valid To</th>
				<th>Status</th>
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
			  <c:forEach var="visadetails" items="${PassportList}" varStatus="rows">
			  <tr>
				<td><div class="tbldata-dotdot-sm" title = "${visadetails.visaType}">${!empty visadetails.visaType ? visadetails.visaType : '---'}</div></td>
				<td><div class="tbldata-dotdot-sm" title = "${visadetails.idNumber}">${!empty visadetails.idNumber ? visadetails.idNumber : '---'}</div></td>
				<td><div class="tbldata-dotdot-sm" title = "${visadetails.country}">${!empty visadetails.country ? visadetails.country : '---'}</div></td>
				<td><div class="tbldata-dotdot-sm" title = "${visadetails.validFromDateStr}">${!empty visadetails.validFromDateStr ? visadetails.validFromDateStr : '---'}</div></td>
				<td><div class="tbldata-dotdot-sm" title = "${visadetails.validToDateStr}">${!empty visadetails.validToDateStr ? visadetails.validToDateStr : '---'}</div></td>
				<td><div class="tbldata-dotdot-sm" title = "${visadetails.currentStatus}">${visadetails.currentStatus}</div></td>
				<c:choose>
				    <c:when test="${myImmigrationPage == 'N'}">
				        <td><div><i id="edit" onclick="immigration.editPassportVisaDetailsByRowId(${visadetails.id},'${visadetails.idType}');"
				class="glyphicon glyphicon-pencil cursorPointer"></i><i id="delete" 
				 onclick="deleteImmigrationRecord('immigration.deletePassportVisaDetails',${visadetails.id},'${visadetails.idType}');" 
				 class="glyphicon glyphicon-trash ml10 cursorPointer"></i></div></td>
				    </c:when>
				    <c:otherwise>
				        <c:choose>
						    <c:when test="${curUscisMyImmi == 'Request to fill' || curUscisMyImmi == 'Request for modifications'}">
						      <td><div><i id="edit" onclick="immigration.editPassportVisaDetailsByRowId(${visadetails.id},'${visadetails.idType}');"
				class="glyphicon glyphicon-pencil cursorPointer"></i><i id="delete" 
				 onclick="deleteImmigrationRecord('immigration.deletePassportVisaDetails',${visadetails.id},'${visadetails.idType}');" 
				 class="glyphicon glyphicon-trash ml10 cursorPointer"></i></div></td>
						    </c:when>
						    <c:otherwise>
						    </c:otherwise>
						</c:choose>
				    </c:otherwise>
				</c:choose>
				
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
		<input type = "hidden" id="candidateVisaId"  name="candidatePassportId">
		<div class="popupHead">
			<h2>Add Visa</h2><a href="javascript:void(0)" class="fa fa-close popCan" onClick="popFun();"></a>
		</div>
		
	<div class="clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Visa Type<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<select class="selectpicker width100percnt" name = "visaType" id = "visaType" field-name = "Visa Type"  data-validation = "required" data-live-search="true">
					  <option value="" selected>Select Visa Type</option>
					  <c:forEach var="visaType" items="${visaList}">
						<option value="${visaType}">${visaType}</option>
					</c:forEach>
					</select>
				</div>
			</div>
         
          <div class="col-sm-12 noPad mt clearfix" id="idNumberVisaDiv">
				<label class="col-md-4 padding_left">Visa Id<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" name = "idNumber" id = "idNumber" placeholder="Visa Id" field-name = "Visa Id" class="form-control" data-validation = "required validate_length length8-20 validate_AtLeastOneAlphaNumber" onkeyup = "validateCandidatePassportVisa('Visa');"/>
				</div>
			</div>				          
          	<div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Country<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<select class="selectpicker width100percnt" name="country" id= "visaCountry"  field-name="Country" data-validation="required" data-live-search="true">
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
              		<input type="text" class="form-control calendar"  placeholder="Valid From" value="" name = "validFrom" id = "visaValidFrom" field-name = "Valid From" data-validation="required"/>
				    <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onClick="clearDateValue('visaValidFrom');"></i></span>
				</div></div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Valid To<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              	<div class="input-group errorInptgroup">
              		<input type="text" class="form-control calendar" value="" name = "validTo" placeholder="Valid To" id = "visaValidTo" field-name = "Valid To" data-validation="required"/>
				 <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onClick="clearDateValue('visaValidTo');"></i></span>
				</div>
			</div>	</div>								
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button" id="visa_save_id" onclick="immigration.savePassportVisaDetails('Visa')">
            <input value="Cancel" class="popCan" type="button" onClick="popFun();">
          </div>		
	</div><!--popcode end  -->	
	<div class="immFoot clearfix">
			<div class="pull-right text-right">
				<input type="button" value="Back" onclick="goToSummaryTab('#passportsTab');" class="btn btn-default btn-sm">
             	<input type="button" value="Skip" onclick="goToSummaryNextTab('#lcaTab');" class="btn btn-default btn-sm"> 
              	<input type="button" value="Continue" onclick="goToSummaryNextTab('#lcaTab');" class="btn btn-primary btn-sm ml10">
            </div>
		</div>	
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
				$("#CandidateVisaform")[0].reset();
				$("#visaType").selectpicker("refresh");
				$("#visaCountry").selectpicker("refresh");
				$("#candidateVisaId").val('');
				$('#popUpVisa').find('p.jquery_form_error_message').remove();
				$("#visa_save_id").removeAttr("disabled");
			}
		   	
		</script>
		<script>
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatVisaSave = getJSDateFormat(locationDate);
		 $('#visaValidFrom').datepicker({
		      dateFormat: finalDateFormatVisaSave,
		      changeMonth: true,
		  	  changeYear: true, 
		      onSelect: function (selected) {
		           // var dt = new Date(selected);
		           var dt = 	  $(this).datepicker('getDate', '+1d'); 
		           dt.setDate(dt.getDate() + 1);
		            $("#visaValidTo").datepicker("option", "minDate", dt);
		        }
		  });
		  
		  $('#visaValidTo').datepicker({
		      dateFormat: finalDateFormatVisaSave,
		      changeMonth: true,
		  	  changeYear: true, 
		        onSelect: function (selected) {
		            //var dt = new Date(selected);
		            var dt = 	  $(this).datepicker('getDate', '-1d');
		            dt.setDate(dt.getDate() - 1);
		            $("#visaValidFrom").datepicker("option", "maxDate", dt);
		        }
		  });
		 
		  var config = {
				  '.chosen-select'           : {},
				  '.chosen-select-deselect'  : {allow_single_deselect:true},
				  '.chosen-select-no-single' : {disable_search_threshold:10},
				  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
				  '.chosen-select-width'     : {width:"95%"}
				}
		  $("#searchValueVisa").keyup(function (e) {
			    if (e.which === 13) {
			    	searchVisaValue();
			    }
			});
		 
		  
		  for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
			})
		</script>
<script>
  var visaListCount = '<c:out value ="${visaListCount}"/>';
  var initCount  = '<c:out value ="${requestScope.visaOnLoadCount}"/>';
  if(visaListCount>0 || initCount!= ""){
        $('#visafilled').show() // show and hides circle icon.
    }
	else{
        $('#visafilled').hide()
	}
  $('#visaType').selectpicker('refresh');
  $('#visaCountry').selectpicker('refresh');
</script>
		

		