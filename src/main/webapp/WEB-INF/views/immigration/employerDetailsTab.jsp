<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <c:set var ="employersListCount" value ="${fn:length(candidatesEmployersList)}"/>
<div class="immigraTabsHead">
			<c:choose>
				    <c:when test="${myImmigrationPage == 'N'}">
				        <a href="javascript:void(0)" id="addEmpl"><i class="glyphicon glyphicon-plus"></i> Add Employer</a>
				    </c:when>
				    <c:otherwise>
				        <c:choose>
						    <c:when test="${curUscisMyImmi == 'Request to fill' || curUscisMyImmi == 'Request for modifications'}">
						        <a href="javascript:void(0)" id="addEmpl"><i class="glyphicon glyphicon-plus"></i> Add Employer</a>
						    </c:when>
						    <c:otherwise>
						    </c:otherwise>
						</c:choose>
				    </c:otherwise>
				</c:choose>	
		  	<span><input class="immigraTabSerach" title="Name/Designation" placeholder="Name/Designation" id="employersDataSearch" type="text"></span>
		</div>
		
	  	<div class="immigraTable">
		  <table id="employerTbl">
			<tbody>
			  <tr>
				<th>Employer</th>
				<th>Designation</th>
				<th>Address</th>
				<th>Attachment</th>
				<th>Start Date</th>
				<th>End Date</th>
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
			<c:forEach var="empItems" items="${candidatesEmployersList}" varStatus="rows">
			  <tr>
				<td><div class="tbldata-dotdot-sm" title = "${empItems.name}">${empItems.name}</div></td>
				<td><div class="tbldata-dotdot-sm" title = "${empItems.designation}">${!empty empItems.designation ? empItems.designation : '---' }</div></td>
				<td><div class="tbldata-dotdot-me" title = "${empItems.address}">${!empty empItems.address ? empItems.address : '---'}</div></td>
				<td>
				<c:if test="${! empty empItems.employerFileName}">
				<div class="tbldata-dotdot-me" title = "${empItems.employerFileName}"><span class="fileName">${empItems.employerFileName}</span>
					<a class="fa fa-eye" href="javascript:void(0)" onClick="viewFile('${empItems.employerFileUrl}','${empItems.employerFileName}','${empItems.employerFileContentType}');" title="View"></a>
					<a class="glyphicon glyphicon-download-alt" download href="${empItems.employerFileUrl}" title="Download"></a></div>
					</c:if>
					<c:if test="${empty empItems.employerFileName}">
					<div class="tbldata-dotdot-me">---</div>
					</c:if>
					</td>
				<td><div class="">${empItems.startMonth} ${empItems.startYear}</div></td>
				<td><div class="">${empItems.endMonth} ${empItems.endYear}</div></td>
				<c:choose>
				    <c:when test="${myImmigrationPage == 'N'}">
				        <td><div><i id="edit" onclick="immigration.editEmpDetailsByRowId(${empItems.id});"
								class="glyphicon glyphicon-pencil cursorPointer"></i><i id="delete" 
								 onclick="deleteImmigrationRecord('immigration.deleteEmpDetails',${empItems.id});" 
								 class="glyphicon glyphicon-trash ml10 cursorPointer"></i></div></td>
				    </c:when>
				    <c:otherwise>
				        <c:choose>
						    <c:when test="${curUscisMyImmi == 'Request to fill' || curUscisMyImmi == 'Request for modifications'}">
						      <td><div><i id="edit" onclick="immigration.editEmpDetailsByRowId(${empItems.id});"
										class="glyphicon glyphicon-pencil cursorPointer"></i><i id="delete" 
										 onclick="deleteImmigrationRecord('immigration.deleteEmpDetails',${empItems.id});" 
										 class="glyphicon glyphicon-trash ml10 cursorPointer"></i></div></td>
						    </c:when>
						    <c:otherwise>
						    </c:otherwise>
						</c:choose>
				    </c:otherwise>
				</c:choose>
				
				
			  </tr>  			  			  
			  	</c:forEach>
			  <c:if test="${empty candidatesEmployersList}">
				<tr>
				 <td colspan="7" class="dataTables_empty" valign="top"></td>
				</tr>
			 </c:if>						
			</tbody>
		  </table>
		</div>
		
		
		<!-- popup code start -->
		<form name = "employerDetails_form" id = "employerDetails_form" method = "post">
		<div class="popUpOut" id="emploView">
		<!--hidden id for edit -->
		<input type = "hidden" id="candidateEmpId"  name="candidateEmployeeId">
		
		<div class="popupHead">
			<h2>Add Employer</h2><a href="javascript:void(0)" class="fa fa-close popCan" onClick="popFunEmployer();"></a>
		</div>
		
		<div class="popupMid clearfix overflow_inherint">

          <div class="col-sm-12 noPad mt clearfix">
				<div class="row">
				<label class="col-md-4">Employer<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8">
              		<input type="text" class="form-control" placeholder="Employer" id = "name" name="name" data-validation="required validate_AlphaNumber validate_Space validate_length length2-100" field-name="Employer Name"/>
				</div>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<div class="row">
				<label class="col-md-4">Designation</label>
              	<div class="col-md-8">
              		<input type="text" placeholder="Designation" id ="designation" name="designation" data-validation="required validate_AlphaNumber validate_Space validate_length length2-100" data-validation-optional="true" field-name="Designation"  class="form-control" />
				</div>
				</div>
			</div>				          
          <div class="col-sm-12 noPad mt clearfix">
				<div class="row">
				<label class="col-md-4">Address</label>
              	<div class="col-md-8">
              	<input class="form-control positionRelative" id="address" name="address" onclick="initLocationAuto('address');" placeholder="Address"  type="text" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true"></input>
          				<i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('address');"></i>
				</div>
				</div>
			</div>
			
			 <div class="col-sm-12 noPad mt clearfix">
		             <div class="clearfix checkbox">
		       <label> <input type="checkbox" id="isCurrentPosition" value="Y" onClick ="employercurrentPosition();"name="isCurrentPosition"> Is Current Position</label>
	        </div>
		           </div>
          <div class="col-sm-12 noPad mt clearfix">
				<div class="row">
				<label class="col-md-4">Start Date</label>
              	<div class="col-md-4"> 
              		<select class="newSelect"  name="startMonth" id="startMonth" onChange="monthIfYearRequired();">
		                      <option value="">Select Month</option>
							   <option value="1&January">January</option>
		                      <option value="2&February">February</option>
		                      <option value="3&March">March</option>
		                      <option value="4&April">April</option>
		                      <option value="5&May">May</option>
		                      <option value="6&June">June</option> 
		                      <option value="7&July">July</option>
		                      <option value="8&August">August</option>
		                      <option value="9&September">September</option>
		                      <option value="10&October">October</option>
		                      <option value="11&November">November</option>
		                      <option value="12&December">December</option>
		                  </select>
				</div>
				<div class="col-md-4" id="startYearDiv">
					<input type="text" max class="form-control input-sm" value="" maxlength="4" id="startYear" name="startYear" placeholder="Enter Year"
						field-name="Start Year"
						data-validation="validate_Space validate_extno validate_length length4-4"
						data-validation-optional="true">
						
				</div>
				<p id="startYearValidation" class="colorRed small pull-right mr mt5 mb0" style="display: none"></p>
				</div>
			</div>				
          <div class="col-sm-12 noPad mt clearfix" id = "endDateDivId">
				<div class="row">
				<label class="col-md-4">End Date</label>
              	<div class="col-md-4"> 
              		<select class="newSelect"  name="endMonth" id="endMonth" onChange="monthIfYearRequired();">
		                      <option value="">Select Month</option>
							   <option value="1&January">January</option>
		                      <option value="2&February">February</option>
		                      <option value="3&March">March</option>
		                      <option value="4&April">April</option>
		                      <option value="5&May">May</option>
		                      <option value="6&June">June</option> 
		                      <option value="7&July">July</option>
		                      <option value="8&August">August</option>
		                      <option value="9&September">September</option>
		                      <option value="10&October">October</option>
		                      <option value="11&November">November</option>
		                      <option value="12&December">December</option>
		                  </select>
				</div>
				<div class="col-md-4" id="startYearDiv">
					<input type="text" max class="form-control input-sm" value="" maxlength="4" id="endYear"  name="endYear" placeholder="Enter Year"
						field-name="End Year"
						data-validation="validate_Space validate_extno validate_length length4-4"
						data-validation-optional="true" onChange="monthIfYearRequired();" onkeyup="monthIfYearRequired();">
						
				</div>
				<p id="endYearValidation" class="colorRed small pull-right mr mt5 mb0" style="display: none"></p>
				
				</div>
			</div>	
			<div class="col-sm-12 noPad mt clearfix" id="immigrationEmpDocDiv">
				<label class="col-md-4 padding_left">File</label> 
              	<div class="col-md-8 padding_right">
					<div class="input-group col-md-12 validation_p" style="margin:0 auto">
					<label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
					  <input type="file" style="display: none;" multiple id="uploadBtnEmployer"  class="upload">
					  </span> </label>
					<input type="text" class="form-control" name="docFile" id ="uploadFileEmployer" readonly placeholder="Attachment" field-name="Attachment" data-validation-optional = "true" data-validation = "validatepdf">
				  </div>
				</div>
			</div>	
			
			<div class="col-sm-12 noPad mt clearfix" id="immigrationEmpDocDivEdit" style="display: none">
			<label class="col-md-4 padding_left">File</label>
			 <div class="col-md-8">
				  <label id="immigrationEmpDocName" name="immigrationEmpDocName" class="text-primary tbldata-dotdot-200 pull-left" title=""></label>&nbsp;&nbsp;<span id="delete_immiEmpDocFile" onclick="removeImmiEmpDocFile();"><i class="fa fa-trash removeIcon text-danger cursorPointer pull-right"></i></span>
				<input type="hidden" id="immigrationEmpDocNameExisitngFile" name="immigrationEmpDocNameExisitngFile" value="">
				  </div>			          	
			</div>
					
		</div>
		
		<div class="clearfix"></div>
		<div class="immiPopFoot mt clearfix">
            <input value="Save"class="" type="button" id="employerSave_Buton" onclick="immigration.saveEmployerDetails('save');"> 
            <input value="Cancel" id = "cancel_button" class="popCan" type="button" onClick="popFunEmployer();">
          </div>		
	</div><!-- popup code end -->
	<div class="immFoot clearfix">
			<div class="pull-right text-right">
				<input type="button" value="Back" onclick="goToSummaryTab('#lcaTab');" class="btn btn-default btn-sm">
             <!-- 	<input type="button" value="Skip" onclick="goToSummaryTab('#residencesTab');" class="btn btn-default btn-sm">  -->
              	<input type="button" value="Continue" onclick="goToSummaryNextTab('#educationsTab');" class="btn btn-primary btn-sm ml10">
            </div>
		</div>
	</form>
	
<script>
	function employercurrentPosition() {
		var currentposition = document.getElementById("isCurrentPosition").checked;
		if (currentposition) {
			document.getElementById("endDateDivId").style.display = 'none';
			$('#endYear').removeAttr('data-validation');
			$('#endMonth').removeAttr('data-validation');
			$("#endMonth").val("");
			$('#endYear').val("");
			document.getElementById("endYearValidation").style.display="none";
			endYearFlag = true;
		} else if (document.getElementById("endDateDivId").style.display == 'none') {
			document.getElementById("endDateDivId").style.display = 'block';
		}
	}
</script>	
    
<script type="text/javascript">
	var startYearFlag = true ,endYearFlag = true;
	function applyKeyEventsToYears() {
		$("#startYear").keyup(function() {
			if($(this).val() > new Date().getFullYear()) {
				$("#startYearValidation").html("Start year shouldn't be greater than Current year");
				$("#startYearValidation").show();
				startYearFlag = false;
			} else {
				$("#startYearValidation").hide();
				startYearFlag = true;
			}
		});
		$("#endYear").keyup(function() {
			if($(this).val() > new Date().getFullYear()) {
				$("#endYearValidation").html("End year shouldn't be greater than Current year");
				$("#endYearValidation").show();
				endYearFlag = false;
			} else {
				$("#endYearValidation").hide();
				endYearFlag = true;
			} 
		});
	}
	(function($) {
		$.fn.serializeFormJSON = function() {

			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};
	})(jQuery);
</script>
<script>
	// Add Employee PopUp
	$("#addEmpl").click(function(){
		$(".popMask").fadeIn(200);
		$("#emploView").fadeIn(100);
	});

	function popFunEmployer() {
		$(".popMask").fadeOut(200);
		$("#emploView").fadeOut(200);
		$("#employerDetails_form")[0].reset();
		$("#candidateEmpId").val('');
		$('#emploView').find('p.jquery_form_error_message').remove();
		$('#startYearDiv').find('p.jquery_form_error_message').remove();
		$('#endDateDivId').find('p.jquery_form_error_message').remove();
		$("#startYearValidation").find('p.jquery_form_error_message').remove();
		$("#endYearValidation").find('p.jquery_form_error_message').remove();
		$("#endYearValidation").empty();
		$("#endMonth").find('p.jquery_form_error_message').remove();
		$('#startYearValidation').removeAttr('style'); 
	}
	
	$("#employersDataSearch").keyup(function (e) {
	    if (e.which === 13) {
	    	immigration.getCandidateEmployerList(candidateId);
	    }
	});
	
</script>	

<script type="text/javascript">
	$(function() {
		$("#uploadBtnEmployer").change(function() {
		fileUpload("uploadBtnEmployer", "candidateDocuments", "employerDetails_form");
	});
	});
	
	document.getElementById("uploadBtnEmployer").onchange = function () {
	 	$('#uploadFileEmployer').val($(this).val().replace(/C:\\fakepath\\/i, ''))
	
	}; 
</script>

<script type="text/javascript">
function removeImmiEmpDocFile(){
	var id = $("#candidateEmpId").val();
	$.ajax({
        url: sourceleadUrl + immiModulePath + "/deleteImmigrationEmployerDocumentById?id="+id,
        type: "GET",
        success: function (result) {
        if(result=='success'){
        	$("#immigrationEmpDocDivEdit").hide();
        	$("#immigrationEmpDocDiv").show();
        	 
        	} 
        }
    });
}
</script>
		
<script>
  var employersListCount = '<c:out value ="${employersListCount}"/>';
  var empInitCount2  = '<c:out value="${requestScope.empOnLoadCount}"/>';
  if(employersListCount>0 || empInitCount2!= ""){
        $('#employersfilled').show() // show and hides circle icon.
    }
	else {
        $('#employersfilled').hide()
	}
</script>
<script type="text/javascript">
function monthIfYearRequired(){
	
	var startMonth= document.getElementById("startMonth").value;
	if(startMonth != null && startMonth != "" && startMonth != "undefined"){
		 $('#startYear').removeAttr('data-validation-optional');
	     $('#startYear').attr("data-validation","required validate_Space validate_extno validate_length length4-4"); 
	}else{
		 $('#startYear').removeAttr('data-validation');
		 $('#startYear').attr("data-validation-optional","true"); 
	}
	var endMonth= document.getElementById("endMonth").value;
	if(endMonth != null && endMonth != "" && endMonth != "undefined"){
			 $('#endYear').removeAttr('data-validation-optional');
		     $('#endYear').attr("data-validation","required validate_Space validate_extno validate_length length4-4"); 
		}else{
			 $('#endYear').removeAttr('data-validation');
			 $('#endYear').attr("data-validation-optional","true"); 
		}
	
	var startYear= document.getElementById("startYear").value;
	var SMdata = startMonth.split('&');
	var sMonthNumber = SMdata[0];
	var endYear= document.getElementById("endYear").value;
	var EMdata = endMonth.split('&');
	var eMonthNumber = EMdata[0];
	
	if((startYear != " " && startYear != null && startYear != "undefined" ) && (endYear != " " && endYear != null && endYear != "undefined")){
		if(( parseInt(sMonthNumber) > parseInt(eMonthNumber)) && (startYear == endYear) ){
			 $("#err").empty();
			 $("#startYear").parent().after("<p class='jquery_form_error_message' id='err'><span>Start Month Should be less than end Month</span></p>");
			}
		if(parseInt(sMonthNumber) < parseInt(eMonthNumber))
			 {
			 $('#startYearDiv').parent().find('p.jquery_form_error_message').remove(); 
			 }
	}
}
</script>
