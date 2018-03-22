<div class="immigraTabsHead">
		  	<a href="javascript:void(0)" id="addEmpl"><i class="glyphicon glyphicon-plus"></i> Add Employer</a> 
		  	<span><input class="immigraTabSerach" type="text"></span>
		</div>
		
	  	<div class="immigraTable">
		  <table>
			<tbody>
			  <tr>
				<th>Employer</th>
				<th>Tax Id</th>
				<th>Designation</th>
				<th>Address</th>
				<th>Start Date</th>
				<th>End Date</th>
				<th>&nbsp;</th>
			  </tr>
			  <tr>
				<td><div class="tbldata-dotdot-sm">Business Intelli</div></td>
				<td><div class="">DFDD551</div></td>
				<td><div class="tbldata-dotdot-sm">UI Developer</div></td>
				<td><div class="tbldata-dotdot-me">Address text</div></td>
				<td><div class="">MM/YYYY</div></td>
				<td><div class="">MM/YYYY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  			  			  
			  <tr>
				<td><div class="tbldata-dotdot-sm">Business Intelli</div></td>
				<td><div class="">DFDD551</div></td>
				<td><div class="tbldata-dotdot-sm">UI Developer</div></td>
				<td><div class="tbldata-dotdot-me">Address text</div></td>
				<td><div class="">MM/YYYY</div></td>
				<td><div class="">MM/YYYY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>   
			  						
			</tbody>
		  </table>
		</div>
		
		<div class="immigraPagi clearfix">
			<div class="immiPagiText">Showing 1 to 6 of 6 entries</div>
			  <ul class="pagination">
				<li><a href="#"> << </a></li>
				<li><a href="#">2</a></li>
				<li><a href="#">3</a></li>
				<li class="disabled"><a href="#">6</a></li>
				<li data-page="last"><a href="#"> >> </a></li>
			  </ul>
		</div>
		<!-- popup code start -->
		<form name = "employerDetails_form" id = "employerDetails_form" method = "post">
		<div class="popUpOut" id="emploView">
		<input type="hidden" id = "candidateID" value = "${candidateId}">
		<input type="hidden" id = "immigrationID" value = "${immigrationId}">
		<div class="popupHead">
			<h2>Add Employee</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Employer<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" placeholder="Employer" id = "name" name="name" data-validation="required validate_Space validate_length length2-100" field-name="Employer Name"/>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Designation</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" placeholder="Employer Designation" id ="designation" name="designation" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true" field-name="Employer Designation"  class="form-control" />
				</div>
			</div>				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" placeholder="Address" id = "address" name="address" data-validation="required validate_Space validate_length length2-250"  data-validation-optional="true" field-name="Address" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Start Date</label>
              	<div class="col-md-8 padding_right"> 
              		<input type="text" id= "startDate" name = "startDate" class="form-control calendar" />
				</div>
			</div>				
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">End Date</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id= "endDate" name = "endDate" class="form-control calendar" />
				</div>
			</div>			
		</div>
		
		<div class="immiPopFoot">
            <input value="Save"class="" type="button" id = "save_Buton" onclick="saveEmployerDetails('save');"> 
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div><!-- popup code end -->
	</form>
	
		
		<script>
		// Add Employee PopUp
		$("#addEmpl").click(function(){
			$(".popMask").fadeIn(200);
			$("#emploView").fadeIn(100);
		});
		
		</script>
		
		
