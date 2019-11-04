<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <c:set var ="visaListCount" value ="${fn:length(visaList)}"/>
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
			  <c:forEach var="visadetails" items="${visaList}" varStatus="rows">
			  <tr>
				<td><div class="tbldata-dotdot-sm" title = "${visadetails.visaType}">${!empty visadetails.visaType ? visadetails.visaType : '---'}</div></td>
				<td><div class="tbldata-dotdot-sm" title = "${visadetails.visaNumber}">${!empty visadetails.visaNumber ? visadetails.visaNumber : '---'}</div></td>
				<td><div class="tbldata-dotdot-sm" title = "${visadetails.country}">${!empty visadetails.country ? visadetails.country : '---'}</div></td>
				<td><div class="tbldata-dotdot-sm" title = "${visadetails.validFrom}">${!empty visadetails.validFrom ? visadetails.validFrom : '---'}</div></td>
				<td><div class="tbldata-dotdot-sm" title = "${visadetails.validTo}">${!empty visadetails.validTo ? visadetails.validTo : '---'}</div></td>
				<td><div class="tbldata-dotdot-sm" title = "${visadetails.currentStatus}">${visadetails.currentStatus}</div></td>
				<c:choose>
				    <c:when test="${myImmigrationPage == 'N'}">
				    
				        <td>
				        <div class="width50px">
				           <c:if test ="${visadetails.currentStatus == 'Expired' && visadetails.status == 1}">
				        	<input type="text" class="calendar visaCalendarInput" id="visaExtendDate${visadetails.candidateVisaId}" readonly="readonly">
				        </c:if>
				         <c:if test ="${visadetails.currentStatus == 'Active' && visadetails.status == 1}">
						      <i id="edit" onclick="immigration.editVisaDetailsByRowId(${visadetails.id});"
						    
				class="glyphicon glyphicon-pencil cursorPointer"></i>
					</c:if>
				<i id="delete" 
				 onclick="deleteImmigrationRecord('immigration.deleteVisaDetails',${visadetails.id});" 
				 class="glyphicon glyphicon-trash pull-right cursorPointer"></i> </div></td>
				    </c:when>
				    <c:otherwise>
				        <c:choose>
						    <c:when test="${curUscisMyImmi == 'Request to fill' || curUscisMyImmi == 'Request for modifications'}">
						      <td><div class="width50px">
				           <c:if test ="${visadetails.currentStatus == 'Expired' && visadetails.status == 1}">
				        	<input type="text" class="calendar visaCalendarInput" id="visaExtendDate${visadetails.candidateVisaId}" readonly="readonly">
						       </c:if>
				        		 <c:if test ="${visadetails.currentStatus == 'Active' && visadetails.status == 1}">
						      <i id="edit" onclick="immigration.editVisaDetailsByRowId(${visadetails.id});"
						    
								class="glyphicon glyphicon-pencil cursorPointer"></i>
								</c:if>
								<i id="delete" 
							 onclick="deleteImmigrationRecord('immigration.deleteVisaDetails',${visadetails.id});" 
							 class="glyphicon glyphicon-trash pull-right cursorPointer"></i></div></td>
						    </c:when>
						    <c:otherwise>
						    </c:otherwise>
						</c:choose>
				    </c:otherwise>
				</c:choose> 
				
				
			  </tr>
			   </c:forEach>
			    <c:if test="${empty visaList}">
				<tr>
				 <td colspan="7" valign="top" class="dataTables_empty"></td>
				</tr>
			 </c:if>
			  </tbody>
		  </table>
		</div>
			 
		<!--popcode start  -->
		<form id="CandidateVisaform" name="CandidateVisaform" method="post"> 
		<div class="popUpOut" id="popUpVisa">
		<input type = "hidden" id="candidateVisaId"  name="candidateVisaId">
		 <input type = "hidden" id="visaHistoryId"  name="visaHistoryId">
		<div class="popupHead">
			<h2>Add Visa</h2><a href="javascript:void(0)" class="fa fa-close popCan" onClick="popFun();"></a>
		</div>
		
	<div class="clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Visa Type<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<select class="selectpicker width100percnt" name = "visaType" id = "visaType" field-name = "Visa Type"  data-validation = "required" data-live-search="true">
					  <option value="" selected>Select Visa Type</option>
					  <c:forEach var="visaType" items="${canVisaList}">
						<option value="${visaType}">${visaType}</option>
					</c:forEach>
					</select>
				</div>
			</div>
         
          <div class="col-sm-12 noPad mt clearfix" id="idNumberVisaDiv">
				<label class="col-md-4 padding_left">Visa Id<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" name ="visaNumber" id ="visaNumber" placeholder="Visa Id" field-name = "Visa Id" class="form-control"  	 onblur="immigration.validateCandidateVisaDetails();"  data-validation = "required validate_length length8-20 validate_AtLeastOneAlphaNumber"/>
				</div>
			</div>				          
          	<div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Country<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<select class="selectpicker width100percnt" name="country" id= "visaCountry"  onchange="immigration.validateCandidateVisaDetails();" field-name="Country" data-validation="required" data-live-search="true">
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
				 <span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer"  onBlur="isCurrentValidate();"onClick="clearDateValue('visaValidTo')"></i></span>
				</div>
				
			</div>	
			
			</div>	
			
			 <div class="clearfix"></div>
		 <!--  <div class="col-md-4 col-md-offset-4">
		   	<div class="mt5 clearfix">
			   <input id="isCurrent" title="Is Current" name="isCurrent"  type="checkbox" onclick="isCurrentValidate();" >
			   <input type="checkbox" id ="isname="isCurrent" value="N" onclick="isCurrentValidate();"/>
			   <label class="padding_left">Is Current</label>
			</div>
		   </div> -->
		   
	<!-- 	    <div class="clearfix"></div>
		  <div class="col-md-4 col-md-offset-4">
		   	<div class="mt5 clearfix">
			   <input id="isCurr" title="Is Current" name="isCurrent"  type="checkbox"  onclick="isCurrentValidate();" value="N" >
			   <label class="padding_left">Is Current</label>
			</div>
		   </div> -->
		   							
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button" id="visa_save_id" onclick="immigration.validateCandidateVisaAndSave()">
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
	 $(function(){
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatVisaSave = getJSDateFormat(locationDate);
		 <c:forEach var="visaId" items="${expiredVisaIdList}">
		    $('#visaExtendDate'+${visaId}).datepicker({
		      dateFormat: finalDateFormatVisaSave,
		      changeMonth: true,
		  	  changeYear: true, 
		  	  minDate:0,
		      onSelect: function (selected) {
		    	  var extensionDate = $(this).val();
		    	  immigration.addvisaExtensionDate(${visaId},extensionDate);
		         }
		    });
		  </c:forEach>
	});
	 
</script>
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
				$('#visaType').prop("disabled",false);
				$("#visaType").selectpicker("refresh");
				$('#visaCountry').prop("disabled",false);
				$("#visaCountry").selectpicker("refresh");
				$('#visaNumber').removeAttr("disabled");
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
			    	immigration.searchVisaValue();
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
		
<script type="text/javascript">
function isCurrentValidate(){
	
	var flag = document.getElementById("isCurr").checked;
	 if(flag == true){
		 $("#isCurr").val("Y"); 
	 }else{
		 $("#isCurr").val("N"); 
	 } 
	
	/* var ckVal=$("#multipleCheckBox123").val();
	var fullDate = new Date()
	var twoDigitMonth = ((fullDate.getMonth().length+1) === 1)? (fullDate.getMonth()+1) : '0' + (fullDate.getMonth()+1);
	var currentDate = twoDigitMonth + "-" + fullDate.getDate() + "-" + fullDate.getFullYear();
	alert("currentDate="+currentDate);
	var toDate=$("#visaValidTo").val();
	alert("toDate=="+toDate);
	
	if(toDate > currentDate){
		alert("Valid To  Should be greter than current date")
	}else{
		alert("Yfff");
	} */
	
}
</script>
		