<div class="immigraTabsHead">
		  	<a href="javascript:void(0)" class="immMenuClick">Add Family <i class="fa fa-plus"></i>
				<div class="immiSubMenu">
					<ul>
						<li id="addSpou"><i class="fa fa-plus"></i> Add Spouse</li>
						<li id="addChil"><i class="fa fa-plus"></i> Add Child</li>
						<li id="addFath"><i class="fa fa-plus"></i> Add Father</li>
						<li id="addMoth"><i class="fa fa-plus"></i> Add Mother</li>
					</ul>
				</div>
		  	</a> 
		  	<span><input class="immigraTabSerach" type="text"></span>
		</div>
	  	<div class="immigraTable">
		  <table>
			<tbody>
			  <tr>
				<th>Name</th>
				<th>Relationship</th>
				<th>Date of Birth</th>
				<th>Place of Birth</th>
				<th>Address</th>
				<th>&nbsp;</th>
			  </tr>
			  <tr>
				<td><div class="tbldata-dotdot-me">Mahesh Vannam</div></td>
				<td><div class="tbldata-dotdot-me">Brother</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-me">Hyderabad</div></td>
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
		<!--  -->
		<!--   Family Popup's  popcode start-->  
  <form name ="spouseDetailsForm" id ="spouseDetailsForm">
	<div class="popUpOut" id="spousePopView"><!--  spouse popup start -->
		<div class="popupHead">
			<h2>Add Spouse</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">First Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="firstName" name="firstName" class="form-control" placeholder="First Name" field-name="First Name" data-validation="required"/>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Middle Name</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="middleName" name="middleName" class="form-control" />
				</div>
			</div> 
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Last Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="lastName" name="lastName" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Place of Marriage<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="marriagePlace" name="marriagePlace" class="form-control" />
				</div>
			</div>                           				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Date of Marriage<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="marraigeDate" name="marraigeDate" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">End Date</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="marriageEndDate" name="marriageEndDate" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Date of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="dateOfBirth" name="dateOfBirth" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Place of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="placeOfBirth" name="placeOfBirth"  class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="currentAddress" name="currentAddress" class="form-control" />
				</div>
			</div>																		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" onclick="immigration.saveCandidateFamilyDetails('Spouse');" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>	<!--  spouse popup end -->
	</form>
	<div class="popUpOut" id="childPopView">
		<div class="popupHead">
			<h2>Add Child</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">First Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="firstName" name="firstName" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Middle Name</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="middleName" name="middleName" class="form-control" />
				</div>
			</div> 
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Last Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="lastName" name="lastName" class="form-control" />
				</div>
			</div>                         				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Date of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text"  id="dateOfBirth" name="dateOfBirth" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Place of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="placeOfBirth" name="placeOfBirth" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" id="currentAddress" name="currentAddress"class="form-control" />
				</div>
			</div>																		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>	
	
	<div class="popUpOut" id="fathPopView">
		<div class="popupHead">
			<h2>Add Father</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">First Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Middle Name</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div> 
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Last Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>                         				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Date of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Place of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>																		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>	
	
	<div class="popUpOut" id="mothPopView">
		<div class="popupHead">
			<h2>Add Mother</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">First Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Middle Name</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div> 
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Last Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>                         				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Date of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Place of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>																		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>	<!--popcode end  -->
	
	<script>
			
			 // Add Spouse
			$("#addSpou").click(function(){
				$(".popMask").fadeIn(200);
				$("#spousePopView").fadeIn(100);
			});
			$("#addChil").click(function(){
				$(".popMask").fadeIn(200);
				$("#childPopView").fadeIn(100);
			});
			$("#addFath").click(function(){
				$(".popMask").fadeIn(200);
				$("#fathPopView").fadeIn(100);
			});
			$("#addMoth").click(function(){
				$(".popMask").fadeIn(200);
				$("#mothPopView").fadeIn(100);
			});	
			
            // cancel popup.
			$(".popCan").click(function(){
				$(".popMask").fadeOut(200);
				$("#spousePopView, #childPopView, #fathPopView, #mothPopView").fadeOut(100);
			});	
		</script>
		