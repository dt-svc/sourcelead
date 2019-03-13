<div class="row clearfix minHeightcol5 mb">
					
					<div class="col-md-5">
						<div class="clearfix">
							<label class="clearfix">Company Identification Number<span class="text-danger">&nbsp;*</span></label>
							<div class="clearfix">
							 <input  class="form-control" type="text" name="cin" id="cin" field-name="Company Identification Number" value="${informationDetails.cin}" data-validation="required validate_Space validate_length length1-21">
							</div>
						</div>
					</div>
					<div class="col-md-5">
						<div class="clearfix">
							<label class="clearfix">GSTIN<span class="text-danger">&nbsp;*</span></label>
							<div class="clearfix">
							  <input  class="form-control" type="text" name="gstin" id="gstin"  value="${informationDetails.gstin}" field-name="GSTIN" data-validation="required validate_Space validate_length length1-21">
							</div>
						</div>
					</div>
					
					<div class="col-md-5">
						<div class="clearfix">
							<label class="clearfix">Date of Incorporation<span class="text-danger">&nbsp;*</span></label>
							<div class="input-group" id="incorporationDateDiv">
                              <input type="text" name="incorporationDate" class="form-control calendar" value="${informationDetails.incorporationDateStr}" id="incorporationDate" data-validation="required" field-name="Date of Incorporation" readonly="readonly">
                              <span class="input-group-addon" onClick="clearDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
                           </div>
                           
						</div>
					</div>
					
					
			       </div>
			        
					<h4><strong>IT INFO</strong></h4>
			         <div class="row clearfix mb minHeightcol5"> 
						<div class="col-md-5">
							<div class="clearfix">
								<label class="clearfix">Pan<span class="text-danger">&nbsp;*</span></label>
								<div class="clearfix">
								 <input  class="form-control" type="text" name="pan" id="pan" field-name="PAN"  value="${informationDetails.pan}" data-validation="required validate_Space validate_length length1-10">
								</div>
							</div>
						</div>
						<div class="col-md-5">
							<div class="clearfix">
								<label class="clearfix">Tan<span class="text-danger">&nbsp;*</span></label>
								<div class="clearfix">
								 <input  class="form-control" type="text" name="tan" id="tan" field-name="TAN" value="${informationDetails.tan}" data-validation="required validate_Space validate_length length1-10">
								</div>
							</div>
						</div>
						
						<div class="col-md-5">
							<div class="clearfix">
								<label class="clearfix">TDS<span class="text-danger">&nbsp;*</span></label>
								<div class="clearfix">
								<input  class="form-control" type="text" name="tds" id="tds" field-name="TDS" value="${informationDetails.tds}" data-validation="required validate_Space validate_length length1-100">
								</div>
							</div>
						</div>									
					</div>
					        
					<h4><strong>PF INFO</strong></h4>
			         <div class="row clearfix mb minHeightcol5"> 
						<div class="col-md-5">
							<div class="clearfix">
								<label class="clearfix">Provident Fund Number<span class="text-danger">&nbsp;*</span></label>
								<div class="clearfix">
								 <input  class="form-control" type="text" name="pfNumber" id="pfNumber" value="${informationDetails.pfNumber}" field-name="PF Number" data-validation="required validate_Space validate_length length7-15">
								</div>
							</div>
						</div>
						<div class="col-md-5">
						    <div class="clearfix">
								<label class="clearfix">Registration Date<span class="text-danger">&nbsp;*</span></label>
									<div class="input-group" id="pfregistrationDateDiv">
		                              <input type="text" name="pfregistrationDate" class="form-control calendar" value="${informationDetails.pfRegistrationDateStr}" id="pfregistrationDate" data-validation="required" field-name="Registration Date" readonly="readonly">
		                              <span class="input-group-addon" onClick="clearPfRegDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
		                           </div>
							</div>
						</div>			
					</div>
			
			        <h4><strong>ESI INFO</strong></h4>
			         <div class="row clearfix mb minHeightcol5"> 
						<div class="col-md-5">
							<div class="clearfix">
								<label class="clearfix">ESI Number<span class="text-danger">&nbsp;*</span></label>
								<div class="clearfix">
								 <input  class="form-control" type="text" name="esiNumber" id="esiNumber" field-name="ESI Number" value="${informationDetails.esiNumber}" data-validation="required validate_Space validate_length length10-20">
								</div>
							</div>
						</div>
						<div class="col-md-5">
						    <div class="clearfix">
								<label class="clearfix">Registration Date<span class="text-danger">&nbsp;*</span></label>
									<div class="input-group" id="esiregistrationDateDiv">
		                              <input type="text" name="esiregistrationDate" class="form-control calendar" id="esiregistrationDate" value="${informationDetails.esiRegistrationDateStr}" data-validation="required" field-name="Registration Date" readonly="readonly">
		                              <span class="input-group-addon" onClick="clearEsiRegDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
		                           </div>
							</div>
						</div>		
					</div>
			       
					<h4><strong>PT INFO</strong></h4>
			         <div class="row clearfix mb minHeightcol5"> 
						<div class="col-md-5">
							<div class="clearfix">
								<label class="clearfix">Professional Tax Number<span class="text-danger">&nbsp;*</span></label>
								<div class="clearfix">
								 <input  class="form-control" type="text" name="ptNumber" id="ptNumber" field-name="PT Number" value="${informationDetails.ptNumber}" data-validation="required validate_Space validate_length length7-15">
								</div>
							</div>
						</div>
						<div class="col-md-5">
						    <div class="clearfix">
								<label class="clearfix">Registration Date<span class="text-danger">&nbsp;*</span></label>
									<div class="input-group" id="ptregistrationDateDiv">
		                              <input type="text" name="ptregistrationDate" class="form-control calendar" id="ptregistrationDate" value="${informationDetails.ptRegistrationDateStr}"  data-validation="required" field-name="Registration Date" readonly="readonly">
		                              <span class="input-group-addon" onClick="clearPtRegDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
		                           </div>
							</div>
						</div>			
					</div>
					
					
					
					