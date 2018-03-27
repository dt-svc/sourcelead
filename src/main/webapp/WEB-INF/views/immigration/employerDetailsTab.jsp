<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="immigraTabsHead">
		  	<a href="javascript:void(0)" id="addEmpl"><i class="glyphicon glyphicon-plus"></i> Add Employer</a> 
		  	<span><input class="immigraTabSerach" type="text"></span>
		</div>
		
	  	<div class="immigraTable">
		  <table>
			<tbody>
			  <tr>
				<th>Employer</th>
				<th>Designation</th>
				<th>Address</th>
				<th>Start Date</th>
				<th>End Date</th>
				<th>Action</th>
			  </tr>
			<c:forEach var="empItems" items="${candidatesEmployersList}" varStatus="rows">
			  <tr>
				<td><div class="tbldata-dotdot-sm">${empItems.name}</div></td>
				<td><div class="tbldata-dotdot-sm">${!empty empItems.designation ? empItems.designation : '---' }</div></td>
				<td><div class="tbldata-dotdot-me">${!empty empItems.address ? empItems.address : '---'}</div></td>
				<td><div class="">${empItems.startMonth} ${empItems.startYear}</div></td>
				<td><div class="">${empItems.endMonth} ${empItems.endYear}</div></td>
				<td><div><i id="edit" onclick="editEmpDetailsByRowId(${empItems.id});"
				class="glyphicon glyphicon-pencil"></i><i id="delete" 
				 onclick="deleteEmpDetailsById(${empItems.id});" 
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
		
		<!-- <div class="immigraPagi clearfix">
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
		<form name = "employerDetails_form" id = "employerDetails_form" method = "post">
		<div class="popUpOut" id="emploView">
		
		<div class="popupHead">
			<h2>Add Employee</h2><a href="javascript:void(0)" class="fa fa-close popCan" onClick="popFun();"></a>
		</div>
		
		<div class="popupMid clearfix overflow_inherint">

          <div class="col-sm-12 noPad mt clearfix">
				<div class="row">
				<label class="col-md-4">Employer<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8">
              		<input type="text" class="form-control" placeholder="Employer" id = "name" name="name" data-validation="required validate_Space validate_length length2-100" field-name="Employer Name"/>
				</div>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<div class="row">
				<label class="col-md-4">Designation</label>
              	<div class="col-md-8">
              		<input type="text" placeholder="Employer Designation" id ="designation" name="designation" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true" field-name="Employer Designation"  class="form-control" />
				</div>
				</div>
			</div>				          
          <div class="col-sm-12 noPad mt clearfix">
				<div class="row">
				<label class="col-md-4">Address</label>
              	<div class="col-md-8">
              		<input type="text" placeholder="Address" id = "address" name="address" data-validation="required validate_Space validate_length length2-250"  data-validation-optional="true" field-name="Address" class="form-control" />
				</div>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<div class="row">
				<label class="col-md-4">Start Date</label>
              	<div class="col-md-4"> 
              		<select class="newSelect"  name="startMonth" id="startMonth" onChange="monthIfYearRequired();">';
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
					<input type="text" max class="form-control input-sm" value="" id="startYear" name="startYear" placeholder="Enter Year"
						field-name="Start Year"
						data-validation="validate_Space validate_extno validate_length length4-4"
						data-validation-optional="true">
				</div>
				</div>
			</div>				
          <div class="col-sm-12 noPad mt clearfix">
				<div class="row">
				<label class="col-md-4">End Date</label>
              	<div class="col-md-4"> 
              		<select class="newSelect"  name="endMonth" id="endMonth" onChange="monthIfYearRequired();">';
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
					<input type="text" max class="form-control input-sm" value="" id="endYear" name="endYear" placeholder="Enter Year"
						field-name="End Year"
						data-validation="validate_Space validate_extno validate_length length4-4"
						data-validation-optional="true">
				</div>
				</div>
			</div>			
		</div>
		
		<div class="immiPopFoot">
            <input value="Save"class="" type="button" id = "save_Buton" onclick="saveEmployerDetails('save');"> 
            <input value="Cancel" id = "cancel_button" class="popCan" type="button" onClick="popFun();">
          </div>		
	</div><!-- popup code end -->
	</form>
	
		
<script>
	// Add Employee PopUp
	$("#addEmpl").click(function(){
		$(".popMask").fadeIn(200);
		$("#emploView").fadeIn(100);
	});

	function popFun() {
		$(".popMask").fadeOut(200);
		$("#emploView").fadeOut(200);
		$("#employerDetails_form")[0].reset();
	}
</script>	
		
