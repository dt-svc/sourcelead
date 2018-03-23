<div class="immigraTabsHead">
		  	<a href="javascript:void(0)" id="addResi"><i class="glyphicon glyphicon-plus"></i> Add Residence</a> 
		  	<span><input class="immigraTabSerach" type="text"></span>
		</div>
		
	  	<div class="immigraTable">
		  <table>
			<tbody>
			  <tr>
				<th>Residence</th>
				<th>Country</th>
				<th>From</th>
				<th>To</th>
				<th>&nbsp;</th>
			  </tr>
			  
			  <tr>
				<td><div class="tbldata-dotdot-big">Madhapur, Hyderabad</div></td>
				<td><div class="tbldata-dotdot-vsm">India</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
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
		<!-- popcode start -->
		<form id ="residenceDetails_form" name = "residenceDetails_form" method = "Post">
		<div class="popUpOut" id="popResiView">
		<div class="popupHead">
			<h2>Add Residence</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Location<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" name= "location" id= "location" field-name= "location" data-validation="required validate_Space validate_length length2-100" class="form-control" /> 
              		             		
				</div>
			</div>	
			<div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">StreetAddress</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" name= "streetAddress" id= "streetAddress" field-name= "streetAddress" class="form-control" /> 
              		             		
				</div>
			</div>
			
			<div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">City</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" name= "city" id= "city" field-name= "city" class="form-control"/> 
              		             		
				</div>
			</div>
					
			<div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">State</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" name= "state" id= "state" field-name= "state"  class="form-control" /> 
              		             		
				</div>
			</div>
			
						          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Country</label>
              	<div class="col-md-8 padding_right">
					<select class="chosen-select form-control" name="country" id= "country" field-name="Country"data-validation="required validate_Space validate_length length2-100" data-validation-optional="true">
					  <option>Select Country</option>
					  <option>India</option>
					  <option>USA</option>
					  <option>UK</option>
					</select>
				</div>
			</div>
			
			<div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Zipcode</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" name= "zipcode" id= "zipcode" field-name= "zipcode"  class="form-control" /> 
              		             		
				</div>
			</div>
			
           <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Start Date<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" name = "residenceStartDate" id = "residenceStartDate" field-name = "Start Date" data-validation="required" class="form-control calendar"/>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">End Date<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" name = "residenceEndDate" id = "residenceEndDate" field-Name = "End Date" data-validation="required" class="form-control calendar" />
				</div>
			</div>	 								
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button" id="save_Buton" onclick="saveResidenceDetails('save');">
            <input value="Cancel" class="popCan" type="button">
          </div>		
        
       
	</div>	<!-- popcode code End -->
		</form>
		
		<script type="text/javascript">
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatEduSave = getJSDateFormat(locationDate);
		  
		$('#residenceStartDate').datepicker({
		      dateFormat: finalDateFormatEduSave,
		      changeMonth: true,
		  	  changeYear: true, 
		      onSelect: function (selected) {
		            //var dt = new Date(selected);
		            var dt = $(this).datepicker('getDate', '+1d');
		            dt.setDate(dt.getDate() + 1);
		            $("#residenceEndDate").datepicker("option", "minDate", dt);
		        }
		  });
		  
		  $('#residenceEndDate').datepicker({
		      dateFormat: finalDateFormatEduSave,
		      changeMonth: true,
		  	  changeYear: true, 
		        onSelect: function (selected) {
		            //var dt = new Date(selected);
		            var dt = $(this).datepicker('getDate', '-1d');
		            dt.setDate(dt.getDate() - 1);
		            $("#residenceStartDate").datepicker("option", "maxDate", dt);
		        }
		  });
		
</script>
		
		
		<script>
			 // Add addResi Div  
			$("#addResi").click(function(){
				$(".popMask").fadeIn(200);
				$("#popResiView").fadeIn(100);
			});	
		</script>
		
		
		
		