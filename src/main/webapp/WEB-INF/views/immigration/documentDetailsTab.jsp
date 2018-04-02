		<div class="immigraTabsHead">
		<!-- <a href="javascript:void(0)" id="immDocu"><i class="glyphicon glyphicon-plus"></i> Request Document</a> 		 -->	  	
		  	<a href="javascript:void(0)" id="addDocu"><i class="glyphicon glyphicon-plus"></i> Add Document</a> 
		  	<span><input class="immigraTabSerach" type="text"></span>
		</div>
		
	  	<div class="immigraTable">
		  <table>
			<tbody>
			  <tr>
				<th>Document Type</th>
				<th>Required</th>
				<th>Document</th>
				<th>Status</th>
				<th>Description</th>
				<th>&nbsp;</th>
			  </tr>
			  			  			  
			<tr>
				<td><div class="tbldata-dotdot-vsm">Pan Card</div></td>
				<td><div class="tbldata-dotdot-vsm">Yes</div></td>
				<td><div class="tbldata-dotdot-me"><span class="fileName">Pad.jpg</span>
					<a class="fa fa-eye" title="View"></a>
					<a class="glyphicon glyphicon-download-alt" title="View"></a></div>
				</td>
				<td><div class="tbldata-dotdot-vsm">Uploaded</div></td>
				<td><div class="tbldata-dotdot-big">Description text</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" title="Upload" class="fa fa-upload uploadForm"></a>
							<a href="javascript:void(0)" title="Remove" class="fa fa-trash"></a>
							<a href="javascript:void(0)" title="Edit" class="fa fa-pencil"></a>
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
		<!--  popup code start -->
		<form id= "candidate_Immigration_Doc" name = "candidate_Immigration_Doc">
		<div class="popUpOut" id="docuView">
	 	<input type = "hidden" id= "immigrationcandidateId" name = "immigrationcandidateId" value = "${candidateId}">
		<input type = "hidden" id= "immigrationId" name = "immigrationId" value = "${immigrationId}">
		<input type = "hidden" id= "immigrationTempplateId" name = "immigrationTempplateId" value = "${templateId}">
		<div class="popupHead">
			<h2>Add Document</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Document Type<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<select class="chosen-select form-control">
					  <option>Select Document Type</option>
					  <option>Document-1</option>
					  <option>Document-2</option>
					  <option>Document-3</option>
					</select>
				</div>
			</div>
          		          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Description</label>
              	<div class="col-md-8 padding_right">
					<textarea rows="2" class="form-control" id= "description" name = "description"></textarea>
				</div>
			</div>	
			
			<div class="col-md-4 noPad">
							<label class="switch mt5 text-center">
							<input id="requiredCheck" name="isRequired" title="Is Required" type="checkbox" value = "Y" onclick = "tempDocReq();">
							<div class="slider round"></div>
							</label>
						  </div>
						  
				  <div class="col-md-4">
				<div class="mt5 ml5 clearfix">
				  <input id="multipleCheck" title="Allow multiple Docs" name="multiple" value = "N" onclick = "tempDocCheckBox()" type="checkbox">
				  </div>
				 </div>	
			
			<div class="col-sm-12 noPad mt clearfixclearfix">
				<label class="col-md-4 padding_left">Document<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<div class="input-group col-md-12" style="margin:0 auto">
					<label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
					  <input type="file" style="display: none;" multiple id="uploadBtn"  class="upload">
					  </span> </label>
					<input type="text" class="form-control" readonly id="uploadFile" placeholder="Upload Resume ...">
				  </div>
				</div>
			</div>				
		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button" onclick="saveCandidateDocs();">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>
	</form>
	<!-- <div class="popUpOut popUpOutBig" id="docuImportView">
		<div class="popupHead">
			<h2>Request Document</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix mt">		
			<table class="table table-responsive table-bordered table-hover noMar">
				<tbody>
					<tr>
						<td width="200px">
							<select class="chosen-select form-control">
							  <option>Select Document Type</option>
							  <option>Document-1</option>
							  <option>Document-2</option>
							  <option>Document-3</option>
							</select>
						</td>
						<td width="auto"><textarea rows="2" placeholder="Description" class="form-control"></textarea></td>
						<td width="120px">
							<label class="switch mt10 ml10 pull-left"><input checked="" type="checkbox"><div class="slider round"></div></label>
							<div class="pull-right"><button type="button" class="btn btm-sm pull-right mt5"><i class="fa fa-plus text-primary"></i></button></div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button" onclick="saveCandidateDocs();">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div> -->	
	<div class="popUpOut" id="uploadFormPop">
		<div class="popupHead">
			<h2>Upload file</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid popupMidHeight clearfix">
         
          <div class="col-sm-12 noPad mt clearfixclearfix">
				<label class="col-md-4 padding_left">File<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<div class="input-group col-md-12" style="margin:0 auto">
					<label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
					  <input type="file" style="display: none;" multiple id="uploadBtn10"  class="upload">
					  </span> </label>
					<input type="text" class="form-control" readonly id="uploadFile10" placeholder="Upload Resume ...">
				  </div>
				</div>
			</div>					          	
		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>	<!--  popup code end  -->
	
		<script>
		
			 // Import Document PopUp
			$("#immDocu").click(function(){
				$(".popMask").fadeIn(200);
				$("#uploadFormPop").fadeIn(100);
			});
			 // Add Document PopUp
			$("#addDocu").click(function(){
				$(".popMask").fadeIn(200);
				$("#docuView").fadeIn(100);
			});
		</script>