<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="immigraTabsHead">
		  	<a href="javascript:void(0)" id="addEdu"><i class="glyphicon glyphicon-plus"></i> Add Education</a> 
		  	<span><input class="immigraTabSerach" type="text"></span>
		</div>
		
	  	<div class="immigraTable">
		  <table>
			<tbody>
			  <tr>
				<th>Education</th>
				<th>Specialization</th>
				<th>Start date</th>
				<th>End date</th>
				<th>Score</th>
				<th>Score Type</th>
				<th>Institute/University</th>
				<th>Address</th>
				<th>Action</th>
			  </tr>
			   <c:forEach var="eduItems" items="${candidatesEducationList}" varStatus="rows">
			  <tr>
				<td><div class="tbldata-dotdot-vsm">${eduItems.name}</div></td>
				<td><div class="tbldata-dotdot-vsm">${eduItems.specialization}</div></td>
				<td><div class="tbldata-dotdot_80">${eduItems.startDateStr}</div></td>
				<td><div class="tbldata-dotdot-80">${eduItems.endDateStr}</div></td>
				<td><div class="tbldata-dotdot-80">${eduItems.grade}</div></td>
				<td><div class="tbldata-dotdot-80">${eduItems.gradeType}</div></td>
				<td><div class="tbldata-dotdot-vsm">${eduItems.institute}</div></td>
				<td><div class="tbldata-dotdot-vsm">${eduItems.address}</div></td>
				<td><div><i id="edit" onclick="editEducationDEtailsByRowId(${eduItems.id});"
				class="glyphicon glyphicon-pencil"></i><i id="delete" 
				 onclick="deleteEDucationDetailsById(${eduItems.id});" 
				 class="glyphicon glyphicon-trash ml10"></i></div></td>
			  </tr>  			  			  
			   </c:forEach>
			  						
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
		<div class="popupHead">
			<h2>Add Education</h2><a href="javascript:void(0)" class="fa fa-close popCan" onclick="closeEdupopup();"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Select Education</label>
              	<div class="col-md-8 padding_right">
					<select class="chosen-select form-control" name="name" id= "eduName" field-name="Education" data-validation="required">
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
              		<input type="text" class="form-control" id = "specialization" name = "specialization"/>
				</div>
			</div>				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Start Date</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" id="immigrationEduStartDate12" name = "startDate"/>
				</div>
			</div>	
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">End Date</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" id = "immigrationEduEndDate" name = "endDate"/>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Score Type</label>
              	<div class="col-md-8 padding_right">
					<select class="chosen-select form-control" name="gradeType" id="scoreEducationType">
					  <option value="" selected="selected">Score Type</option>
						 <option value="Percentage">Percentage</option>
	              <option value="CGPA">CGPA</option>
	           <option value="GRADE">GRADE</option>
					</select>
				</div>
			</div>	
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Score</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" id = "grade" name="grade"/>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Institute/University</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" id = "institute" name = "institute"/>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" id = "address" name = "address"/>
				</div>
			</div>											
									
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" id= "eduSave_Button" class="" type="button" onclick = "immigration.saveEducationImmigration('save')">
            <input value="Cancel" class="popCan" type="button" onclick="closeEdupopup();">
          </div>		
	</div><!-- popup code end -->
	</form>
		<script>
		var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatEduSave = getJSDateFormat(locationDate);
		$('#immigrationEduStartDate12').datepicker({
		      dateFormat: finalDateFormatEduSave,
		      changeMonth: true,
		  	  changeYear: true, 
		      onSelect: function (selected) {
		            //var dt = new Date(selected);
		            var dt = $(this).datepicker('getDate', '+1d');
		            dt.setDate(dt.getDate() + 1);
		            $("#immigrationEduEndDate").datepicker("option", "minDate", dt);
		        }
		  });
		  
		  $('#immigrationEduEndDate').datepicker({
		      dateFormat: finalDateFormatEduSave,
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
				$("#popEduView").fadeIn(100);
			});
	function closeEdupopup(){
		$(".popMask").fadeOut(200);
		$("#popEduView").fadeOut(100);
		$("#eduSave_Form")[0].reset();
	}
		</script>