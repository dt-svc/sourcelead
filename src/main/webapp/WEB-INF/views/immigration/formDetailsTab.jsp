<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="immigraTabsHead">			  	
		  	<a href="javascript:void(0)" id="addForm"><i class="glyphicon glyphicon-plus"></i> Add Form</a> 
		  	<span><input class="immigraTabSerach" type="text"></span>
		</div>
		
	  	<div class="immigraTable">
		  <table>
			<tbody>
			  <tr>
				<th>Form Name</th>
				<th>File</th>
				<th>Status</th>
				<th>&nbsp;</th>
			  </tr>
			  <c:forEach var="items" items="${formsList}" varStatus="rows">
			  <tr>
				<td><div class="tbldata-dotdot-me">${items.formName}</div></td>
				<td><div class="tbldata-dotdot-me"><span class="fileName">${items.fileName}</span>
					<a class="glyphicon glyphicon-download-alt" title="Download"></a></div>
				</td>
				<td><div class="tbldata-dotdot-big">${items.formStatus}</div></td>
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
		</div> -->
		<!--  popup code start-->
		<div class="popUpOut" id="viewFormpop">
		<div class="popupHead">
			<h2>Add Form</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Form name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<input type="text" class="form-control" />
				</div>
			</div>	         
          <div class="col-sm-12 noPad mt clearfixclearfix">
				<label class="col-md-4 padding_left">File<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<div class="input-group col-md-12" style="margin:0 auto">
					<label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
					  <input type="file" style="display: none;" multiple id="uploadBtn1"  class="upload">
					  </span> </label>
					<input type="text" class="form-control" readonly id="uploadFile1" placeholder="Upload Resume ...">
				  </div>
				</div>
			</div>					          	
		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>
	
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
	</div>	<!-- popup code end -->
		<script>
			// Add Form PopUp
			$("#addForm").click(function(){
				$(".popMask").fadeIn(200);
				$("#viewFormpop").fadeIn(100);
			});	
			  // upload Form PopUp
			$(".uploadForm").click(function(){
				$(".popMask").fadeIn(200);
				$("#uploadFormPop").fadeIn(100);
			});	
		</script>