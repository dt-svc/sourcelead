<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <c:set var ="passListCount" value ="${fn:length(PassportList)}"/>
 
<div class="immigraTabsHead">
			<c:choose>
				    <c:when test="${myImmigrationPage == 'N'}">
				        <a href="javascript:void(0)" id="addPass"><i class="glyphicon glyphicon-plus"></i> Add Passport</a>
				    </c:when>
				    <c:otherwise>
				        <c:choose>
						    <c:when test="${curUscisMyImmi == 'Request to fill' || curUscisMyImmi == 'Request for modifications'}">
						        <a href="javascript:void(0)" id="addPass"><i class="glyphicon glyphicon-plus"></i> Add Passport</a>
						    </c:when>
						    <c:otherwise>
						        
						    </c:otherwise>
						</c:choose>
				    </c:otherwise>
				</c:choose>	
		  	
		  	<span><input class="immigraTabSerach"  id = "searchValuePassport" placeholder="Passport Id/Country" type="text"></span>
		</div>
		
	  	<div class="immigraTable overflow_Auto">
		  <table id="passportTbl">
			<tbody>
			  <tr>
				<th>Passport Id</th>
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
			  <c:forEach var="passportdetails" items="${PassportList}" varStatus="rows">
			  <tr>
				<td><div class="tbldata-dotdot-custom" title = "${passportdetails.passportNumber}">${!empty passportdetails.passportNumber ? passportdetails.passportNumber : '---'}</div></td>
				<td><div class="tbldata-dotdot-me" title = "${passportdetails.country}">${!empty passportdetails.country ? passportdetails.country : '---'}</div></td>
				<td><div class="tbldata-dotdot-me" title = "${passportdetails.validFromDateStr}">${!empty passportdetails.validFromDateStr ? passportdetails.validFromDateStr : '---'}</div></td>
				<td><div class="tbldata-dotdot-me" title = "${passportdetails.validToDateStr}">${!empty passportdetails.validToDateStr ? passportdetails.validToDateStr : '---'}</div></td>
				<td><div class="tbldata-dotdot-me" title = "${passportdetails.currentStatus}">${passportdetails.currentStatus}</div></td>
				 <c:choose> 
				     <c:when test="${myImmigrationPage == 'N'}">
				        <td><div><i id="edit" onclick="immigration.editPassportDetailsByRowId(${passportdetails.id});"
				class="glyphicon glyphicon-pencil cursorPointer"></i><i id="delete" 
				 onclick="deleteImmigrationRecord('immigration.deletePassportDetails',${passportdetails.id});" 
				 class="glyphicon glyphicon-trash ml10 cursorPointer"></i></div></td>
				    </c:when>
				    <c:otherwise>
				        <c:choose>
						    <c:when test="${curUscisMyImmi == 'Request to fill' || curUscisMyImmi == 'Request for modifications'}">
						       <td><div><i id="edit" onclick="immigration.editPassportDetailsByRowId(${passportdetails.id});"
				class="glyphicon glyphicon-pencil cursorPointer"></i><i id="delete" 
				 onclick="deleteImmigrationRecord('immigration.deletePassportDetails',${passportdetails.id});" 
				 class="glyphicon glyphicon-trash ml10 cursorPointer"></i></div></td>
						    </c:when>
						    <c:otherwise>
						        
						    </c:otherwise>
						</c:choose>
				    </c:otherwise> 
				 </c:choose> 
			  </tr>  
			  </c:forEach>
			   <c:if test="${empty PassportList}">
				<tr>
				 <td colspan="7" valign="top" class="dataTables_empty"></td>
				</tr>
			</c:if>			 				
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
		
		<div class="popupMid clearfix overflow_inherint">
        
          <div class="col-sm-12 noPad mt clearfix" id="idNumberPassportDiv">
				<label class="col-md-4 padding_left">Passport Id<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right" >
              		<input type="text" name ="passportNumber" id ="passportNumber" placeholder="Passport Id" field-name = "Passport Id" class="form-control" data-validation ="required validate_length length8-20 validate_AtLeastOneAlphaNumber" onchange="immigration.validateCandidatePassport();" onkeyup = "immigration.validateCandidatePassport();"/>
				</div>
			</div>				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Country<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<select class="selectpicker width100percnt" name="country" id="countryName" field-name="Country" data-validation="required" data-live-search="true">
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
			</div>		
			<div class="clearfix"></div>							
		<div class="immiPopFoot">
            <input value="Save" class="" type="button" id="passport_save_id" onclick="immigration.savePassportVisaDetails()">
            <input value="Cancel" class="popCan" type="button" onClick="popFun();">
          </div>		
	
	</div>
	
	
		<div class="immFoot clearfix">
			<div class="pull-right text-right">
				<input type="button" value="Back" onclick="goToSummaryTab('#detailsTab');" class="btn btn-default btn-sm">
             	<!-- <input type="button" value="Skip" onclick="goToSummaryTab('#visasTab');" class="btn btn-default btn-sm">  -->
              	<input type="button" value="Continue" onclick="goToSummaryNextTab('#visasTab');" class="btn btn-primary btn-sm ml10">
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
				$("#countryName").selectpicker("refresh");
				$("#candidatePassportId").val('');
				$('#popPassView').find('p.jquery_form_error_message').remove();
				$("#passport_save_id").removeAttr("disabled");
			}
		</script>
		<script>
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatPassSave = getJSDateFormat(locationDate);
		 $('#validFrom').datepicker({
		      dateFormat: finalDateFormatPassSave,
		      changeMonth: true,
		  	  changeYear: true, 
		  	  maxDate:0,
		      onSelect: function (selected) {
		           // var dt = new Date(selected);
		           var dt = $(this).datepicker('getDate', '+1d'); 
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
		  
			  var config = {
					  '.chosen-select'           : {},
					  '.chosen-select-deselect'  : {allow_single_deselect:true},
					  '.chosen-select-no-single' : {disable_search_threshold:10},
					  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
					  '.chosen-select-width'     : {width:"70%"}
					}
			
			  $("#searchValuePassport").keyup(function (e) {
				    if (e.which === 13) {
				    	immigration.searchPassportValue();
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
  var passListCount = '<c:out value ="${passListCount}"/>';
  var initCount  = '<c:out value ="${requestScope.passOnLoadCount}"/>';
  if(passListCount>0 || initCount!= ""){
        $('#passportfilled').show() // show and hides circle icon.
    }
	else {
        $('#passportfilled').hide()
	}
  
  $('#countryName').selectpicker('refresh');
</script>

