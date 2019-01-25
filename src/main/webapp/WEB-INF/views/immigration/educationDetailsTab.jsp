<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <c:set var ="educationListCount" value ="${fn:length(candidatesEducationList)}"/>
<div class="immigraTabsHead">
			<c:choose>
				    <c:when test="${myImmigrationPage == 'N'}">
				        <a href="javascript:void(0)" id="addEdu"><i class="glyphicon glyphicon-plus"></i> Add Education</a>
				    </c:when>
				    <c:otherwise>
				        <c:choose>
						    <c:when test="${curUscisMyImmi == 'Request to fill' || curUscisMyImmi == 'Request for modifications'}">
						        <a href="javascript:void(0)" id="addEdu"><i class="glyphicon glyphicon-plus"></i> Add Education</a>
						    </c:when>
						    <c:otherwise>
						    </c:otherwise>
						</c:choose>
				    </c:otherwise>
				</c:choose>
		  	<span><input class="immigraTabSerach" title="Education/Specialization/Institute/University" placeholder="Education/Specialization/Institute/University" id="educationSearchVal" type="text"></span>
		</div>
		
	  	<div class="immigraTable overflow_Auto">
		  <table id ="educationTbl">
			<tbody>
			  <tr>
				<th>Education</th>
				<th>Specialization</th>
				<th>Start date</th>
				<th>End date</th>
				<th>Score</th>
				<th>Score Type</th>
				<th>Institute/University</th>
				<th>Attachment</th>
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
			   <c:forEach var="eduItems" items="${candidatesEducationList}" varStatus="rows">
			  <tr>
				<td><div class="tbldata-dotdot-100" title = "${eduItems.name}">${eduItems.name}</div></td>
				<td><div class="tbldata-dotdot-100" title = "${eduItems.specialization}">${!empty eduItems.specialization ? eduItems.specialization : '---'}</div></td>
				<td><div class="tblminwidth-75" title = "${eduItems.startDateStr}">${!empty eduItems.startDateStr ? eduItems.startDateStr : '---'}</div></td>
				<td><div class="tblminwidth-75" title = "${eduItems.endDateStr}">${!empty eduItems.endDateStr ? eduItems.endDateStr : '---'}</div></td>
				<td><div class="tblminwidth-30" title = "${eduItems.grade}">${!empty eduItems.grade ? eduItems.grade : '---'}</div></td>
				<td><div class="tbldata-dotdot-80" title = "${eduItems.gradeType}">${!empty eduItems.gradeType ? eduItems.gradeType : '---'}</div></td>
				<td><div class="tbldata-dotdot-vsm" title = "${eduItems.institute}">${!empty eduItems.institute ? eduItems.institute : '---'}</div></td>
				<td>
				<c:if test="${! empty eduItems.educationFileName}">
				<div class="tblminwidth-75" title = "${eduItems.educationFileName}"><span class="tbldata-dotdot-100 fileName">${eduItems.educationFileName}</span>
					<a class="fa fa-eye" href="javascript:void(0)" onClick="viewFile('${eduItems.educationFileUrl}','${eduItems.educationFileName}','${eduItems.educationFileContentType}');" title="View"></a>
					<a class="glyphicon glyphicon-download-alt" download href="${eduItems.educationFileUrl}" title="Download"></a></div>
					</c:if>
					<c:if test="${empty eduItems.educationFileName}">
					<div class="tbldata-dotdot-100">---</div>
					</c:if>
					</td>
				<td><div class="tbldata-dotdot-100" title = "${eduItems.address}">${!empty eduItems.address ? eduItems.address : '---'}</div></td>
				<c:choose>
				    <c:when test="${myImmigrationPage == 'N'}">
				        <td><div><i id="edit" onclick="immigration.editEducationDEtailsByRowId(${eduItems.id});"
								class="glyphicon glyphicon-pencil cursorPointer"></i><i id="delete" 
								 onclick="deleteImmigrationRecord('immigration.deleteEDucationDetailsById',${eduItems.id});" 
								 class="glyphicon glyphicon-trash ml10 cursorPointer"></i></div></td>
				    </c:when>
				    <c:otherwise>
				        <c:choose>
						    <c:when test="${curUscisMyImmi == 'Request to fill' || curUscisMyImmi == 'Request for modifications'}">
						       <td><div><i id="edit" onclick="immigration.editEducationDEtailsByRowId(${eduItems.id});"
										class="glyphicon glyphicon-pencil cursorPointer"></i><i id="delete" 
										 onclick="deleteImmigrationRecord('immigration.deleteEDucationDetailsById',${eduItems.id});" 
										 class="glyphicon glyphicon-trash ml10 cursorPointer"></i></div></td>
						    </c:when>
						    <c:otherwise>
						    </c:otherwise>
						</c:choose>
				    </c:otherwise>
				</c:choose>
			  </tr>  			  			  
			   </c:forEach>
			   
			  <c:if test="${empty candidatesEducationList}">
				<tr>
				 <td colspan="10" valign="top" class="dataTables_empty"></td>
				</tr>
			 </c:if>
			  						
			</tbody>
		  </table>
		</div>
		
		<!--  <div class="immigraPagi clearfix">
			<div class="immiPagiText">Showing 1 to 6 of 6 entries</div>
			  <ul class="pagination">
				<li><a href="#"> << </a></li>
				<li><a href="#">2</a></li>
				<li><a href="#">3</a></li>
				<li class="disabled"><a href="#">6</a></li>
				<li data-page="last"><a href="#"> >> </a></li>
			  </ul>
		</div>  -->
		
		<!-- popup code start -->
		<form id="eduSave_Form" name = "eduSave_Form" method = "post">
		<div class="popUpOut" id="popEduView">
		<!--hidden id for edit -->
		<input type = "hidden" id="candidateEducationId"  name="candidateEduId">
		<div class="popupHead">
			<h2>Add Education</h2><a href="javascript:void(0)" class="fa fa-close popCan" onclick="closeEdupopup();"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Select Education</label>
              	<div class="col-md-8 padding_right" id="nameIdDiv">
					
					<select class="chosen-select form-control" name="name" id= "eduName" field-name="Education" data-validation="required" onchange="immigration.validateEduname();" onkeyup = "immigration.validateEduname();">
					<option value="" selected>--Select Education--</option>
					  <c:forEach var="edu" items="${educationsDropList}">
											<option value="${edu.name}">${edu.name}</option>
										</c:forEach>
					</select>
					
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Specialization</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" id = "specialization" name = "specialization" placeholder="Specialization" data-validation="required validate_AlphaNumber validate_Space validate_length length2-100" data-validation-optional="true"/>
				</div>
			</div>				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Start Date</label>
              	<div class="col-md-8 padding_right">
              	<div class="input-group">
              		<input type="text" class="form-control calendar" id="immigrationEduStartDate12" name = "startDate" placeholder="Start Date" field-name="Start Date" readonly="readonly"/>
                   	<span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('immigrationEduStartDate12');"></i></span>
              		</div>
				</div>
			</div>	
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">End Date</label>
              	<div class="col-md-8 padding_right">
              	<div class="input-group"> 
              		<input type="text" class="form-control calendar" id = "immigrationEduEndDate" name = "endDate" placeholder="End Date" field-name="End Date" readonly="readonly"/>
              		<span class="input-group-addon"><i class="fa fa-times text-danger cursorPointer" onclick="clearDateValue('immigrationEduEndDate');"></i></span>
              		
              		</div>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Score Type</label>
              	<div class="col-md-8 padding_right">
					<select class="chosen-select form-control" name="gradeType" id="scoreEducationType" onchange="educationSelectScoreType();clearSearchKeyEducation();">
					  <option value="" selected="selected">Score Type</option>
						 <option value="Percentage">Percentage</option>
	              <option value="CGPA">CGPA</option>
	           <option value="GRADE">GRADE</option>
					</select>
				</div>
			</div>	
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Score</label>
              	<div class="col-md-8 padding_right" id="candidateScoreDiv">
              		<input type="text" class="form-control" name="grade" onkeyup="educationSelectScoreType();educationscoreAndScoreType();" id="candidateEducationScore" placeholder="Score" placeholder="Score" data-validation="required validate_Space validate_length length1-4" data-validation-optional="true"/>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Institute/University</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" id = "institute" name = "institute" placeholder="Institute" data-validation="required validate_Space validate_AlphaNumber validate_length length2-100" data-validation-optional="true"/>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              <div class="col-md-8 padding_right">
				<input class="form-control positionRelative" id="addressEdu" name="address" onclick="initLocationAuto('addressEdu');" placeholder="Address"  type="text" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true"></input>
          				<i class="fa fa-refresh btn btn-primary refresh-icon" title="Refresh" onClick="refreshGoogleApiLocation('addressEdu');"></i>
         						</div> 
			</div>	
			
			<div class="col-sm-12 noPad mt clearfix" id="immigrationEduDocDiv">
				<label class="col-md-4 padding_left">File</label>
              	<div class="col-md-8 padding_right">
					<div class="input-group col-md-12 validation_p" style="margin:0 auto">
					<label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
					  <input type="file" style="display: none;" multiple id="uploadBtnEducation"  class="upload">
					  </span> </label>
					<input type="text" class="form-control" name="docFile" id ="uploadFileEducation" readonly placeholder="Attachment" field-name="Attachment" data-validation-optional = "true" data-validation = "validatepdf">
				  </div>
				</div>
			</div>	
			
			<div class="col-sm-12 noPad mt clearfix" id="immigrationEduDocDivEdit" style="display: none">
			<label class="col-md-4 padding_left">File</label>
			 <div class="col-md-8">
				  <label id="immigrationEduDocName" name="immigrationEduDocName" class="text-primary tbldata-dotdot-200 pull-left" title=""></label>&nbsp;&nbsp;<span id="delete_immiEduDocFile" onclick="removeImmiEduDocFile();"><i class="fa fa-trash removeIcon text-danger cursorPointer pull-right"></i></span>
				<input type="hidden" id="immigrationEduDocExisitngFile" name="immigrationEduDocExisitngFile" value="">
				  </div>			          	
			</div>										
									
		</div>
		
		<div class="clearfix"></div>
		<div class="immiPopFoot mt clearfix">
            <input value="Save" id= "eduSave_Button" class="" type="button" onclick = "immigration.saveEducationImmigration('save')">
            <input value="Cancel" class="popCan" type="button" onclick="closeEdupopup();">
          </div>		
	</div><!-- popup code end -->
	<div class="immFoot clearfix">
			<div class="pull-right text-right">
				<input type="button" value="Back" onclick="goToSummaryTab('#employersTab');" class="btn btn-default btn-sm">
             	<!-- <input type="button" value="Skip" onclick="goToSummaryTab('#familiesTab');" class="btn btn-default btn-sm">  -->
              	<input type="button" value="Continue" onclick="goToSummaryNextTab('#residencesTab');" class="btn btn-primary btn-sm ml10">
            </div>
		</div>
	</form>
		<script>
		function educationSelectScoreType(){
			var str = $("#candidateEducationScore").val();
			if (str == 'a' || str == 'b' || str == 'c' || str == 'd' || str == 'e') {
				var strToUpper = str.toUpperCase();
				$('#candidateEducationScore').val(strToUpper);
			}
			 var selectedText = document.getElementById("scoreEducationType").value;
			 var canScore = document.getElementById("candidateEducationScore").value;
			 $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove(); 
			 if(selectedText =='Percentage'){
				  var floatNum = /^(\d{0,2}(\.\d{0,2})?|100)$/
				  if(canScore.match(floatNum)){
					  $("#eduSave_Button").removeAttr("disabled");
					   return true;
				  }		  
				 else{
						$("input#candidateEducationScore").parent().after("<p class='jquery_form_error_message'><span>Enter your Valid Percentage</span>");
						 $("#eduSave_Button").attr("disabled", "disabled");
						return false; 
				 	 }
			  }
			 else if(selectedText =='CGPA'){
				  var cgpanum = /^(([\d][\.])?([\d])|10)?$/
					  if(canScore.match(cgpanum)){
						  $("#eduSave_Button").removeAttr("disabled");
						   return true;
					  }		  
					 else{
						 $("input#candidateEducationScore").parent().after("<p class='jquery_form_error_message'><span>Enter your Valid CGPA value</span>");
						 $("#eduSave_Button").attr("disabled", "disabled");
					return false; 
					 }
			  } 
			 else if(selectedText =='GRADE'){
				 var grades = /^([A-E][-+]{0,2})?$/
					 if (canScore.match(grades)) {
						 $("#eduSave_Button").removeAttr("disabled");
						 return true;
					}else{
						$("input#candidateEducationScore").parent().after("<p class='jquery_form_error_message'> Enter your Valid Grade </p>");
						 $("#eduSave_Button").attr("disabled", "disabled");
			    		return false;
			    		
					}
		   		}
				 }
		
		function educationscoreAndScoreType(){
			 var candidateScore = document.getElementById("candidateEducationScore").value;
			 var selectedText = document.getElementById("scoreEducationType").value;
			 var floatNum = /^(\d{0,2}(\.\d{0,2})?|100)$/
			 var cgpanum = /^(([\d][\.])?([\d])|10)?$/
			 var grades = /^([A-E][-+]{0,2})?$/
			 if(candidateScore.match(floatNum)){
				 $("#scoreEducationType").val('Percentage'); 
				 $('#scoreEducationType').trigger("chosen:updated");
				 $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove();
				 $("#eduSave_Button").removeAttr("disabled");
			 }
			 if(candidateScore.match(grades)){
				 $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove();
				 $("#scoreEducationType").val('GRADE'); 
				 $('#scoreEducationType').trigger("chosen:updated");
				 $("#eduSave_Button").removeAttr("disabled");
			 }
			 if(candidateScore.match(cgpanum)){
				 $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove();
				 $("#scoreEducationType").val('CGPA'); 
				 $('#scoreEducationType').trigger("chosen:updated");
				 $("#eduSave_Button").removeAttr("disabled");
			 }
			 if((selectedText == '') && (!candidateScore.match(floatNum)) && (!candidateScore.match( cgpanum)) && (!candidateScore.match(grades))){
				 $("input#candidateEducationScore").parent().after("<p class='jquery_form_error_message'> Enter your Valid Score </p>");
			 }
			 if(selectedText == '' && candidateScore == ''){
				 $('#candidateScoreDiv').parent().find('p.jquery_form_error_message').remove(); 
			 }
			 if(candidateScore == ''){
				 $("#scoreEducationType").val('');
			 }
		}
		$(document).on('change','#specialization, #institute', function () {
			if (this.value.match(/[^a-zA-Z0-9 ]/g)) {
				this.value = this.value.replace(/[^a-zA-Z0-9 ]/g, '');
			}
		});
		var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatImmiEduSave = getJSDateFormat(locationDate);
		$('#immigrationEduStartDate12').datepicker({
		      dateFormat: finalDateFormatImmiEduSave,
		      changeMonth: true,
		  	  changeYear: true, 
		  	  yearRange: "-100:+0",
		      onSelect: function (selected) {
		            //var dt = new Date(selected);
		            var dt = $(this).datepicker('getDate', '+1d');
		            dt.setDate(dt.getDate() + 1);
		            $("#immigrationEduEndDate").datepicker("option", "minDate", dt);
		        }
		  });
		  
		  $('#immigrationEduEndDate').datepicker({
		      dateFormat: finalDateFormatImmiEduSave,
		      changeMonth: true,
		  	  changeYear: true, 
		      onSelect: function (selected) {
		            //var dt = new Date(selected);
		            var dt = $(this).datepicker('getDate', '-1d');
		            dt.setDate(dt.getDate() - 1);
		            $("#immigrationEduStartDate12").datepicker("option", "maxDate", dt);
		        }
		  });
		
		
			// Add addEdu Div  
			$("#addEdu").click(function(){
				$(".popMask").fadeIn(200);
				$('#eduName option[value=""]').prop("selected", true);
    	        $('#eduName').trigger("chosen:updated");
				$("#popEduView").fadeIn(100);
			});
	function closeEdupopup(){
		$(".popMask").fadeOut(200);
		$("#popEduView").fadeOut(100);
		$("#eduSave_Form")[0].reset();
		$("#candidateEducationId").val('');
		$('#popEduView').find('p.jquery_form_error_message').remove();
		$("#eduSave_Button").removeAttr("disabled");
		$('#immigrationEduDocDivEdit').hide();
    	$('#immigrationEduDocDiv').show();
    	$('#immigrationEduDocName').text("");
		// getCandidateEducationList(candidateId);
	}
	
	var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
	
	$("#educationSearchVal").keyup(function (e) {
	    if (e.which === 13) {
	    	immigration.getCandidateEducationList(candidateId);
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
		
<script type="text/javascript">
$(function() {
	$("#uploadBtnEducation").change(function() {
	fileUpload("uploadBtnEducation", "candidateDocuments", "eduSave_Form");
});
});

document.getElementById("uploadBtnEducation").onchange = function () {
 	$('#uploadFileEducation').val($(this).val().replace(/C:\\fakepath\\/i, ''))

}; 
</script>

<script type="text/javascript">
function removeImmiEduDocFile(){
	var id = $("#candidateEducationId").val();
	$.ajax({
        url: sourceleadUrl + immiModulePath + "/deleteImmigrationEducationDocumentById?id="+id,
        type: "GET",
        success: function (result) {
        if(result=='success'){
        	$("#immigrationEduDocDivEdit").hide();
        	$("#immigrationEduDocDiv").show();
        	 
        	} 
        }
    });
}
</script>
<script>
  var educationListCount = '<c:out value ="${educationListCount}"/>';
  var initCount  = '<c:out value ="${requestScope.eduOnLoadCount}"/>';
  if(educationListCount>0 || initCount!= ""){
        $('#educationfilled').show() // show and hides circle icon.
    }
	else {
        $('#educationfilled').hide()
	}
</script>