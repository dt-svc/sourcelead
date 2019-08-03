<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div id="orglocationDocuments" class="tab-pane fade form-horizontal">
  <div class="accountsettings-tab">
	<div class="tab-content panel-body noBor">
	
		<form:form id="documents_form" name="documents_form"  enctype='multipart/form-data'  method="post">
			    <div class="comLogContent">
			      <div id="docPopUp" ></div>	
			      <div id="loading"></div> 
			      <div class="clearfix"> 
				      <div class="col-md-6">
				      	<div class="form-group clearfix">
						<label class="col-md-4 noPad">Business License<span class="text-danger">&nbsp;*</span></label>
							<div class="col-md-8 padding_right">
							
							<c:if test="${!empty businessLicenseDoc.fileUrl}">	
								<div class="clearfix">
									<div class="pull-left fileContainer" title="${businessLicenseDoc.fileName}">
									${businessLicenseDoc.fileName}
									</div>
									<div class="pull-left mt5">
										<a target="_blank" href="${businessLicenseDoc.fileUrl}" class="anchor-black"><i class="fa fa-download" title="Download"></i></a>
										<a href="javascript:void(0)" onclick="viewFile('${businessLicenseDoc.fileUrl}','${businessLicenseDoc.fileName}','${businessLicenseDoc.fileContentType}')"; class="paddingLeft PaddingRight anchor-black"><i class="fa fa-eye" title="View"></i></a>
										<a href="javascript:void(0)" onclick="deleteDocument('${businessLicenseDoc.uuid}','Business License')" class="anchor-black"><i class="fa fa-trash" title="Delete"></i></a>
									</div>
								</div>
							</c:if>
							
							<c:if test="${empty businessLicenseDoc.fileUrl}">
							<div class="input-group col-md-12 errorInptgroup" id="uploadFileGroup"
									style="margin: 0 auto">
									<label class="input-group-btn"> 
									<b class="btn btn-primary" onchange="getBusinessLicense();"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File <input type="file" style="display: none;" id="uploadBusinessLicenseBtn" class="upload"> </b>
									</label> 
									<input type="text" class="form-control" readonly id="orglocbusinessLicense"  name="orglocbusinessLicense" placeholder="Upload File ..." field-name="Business License"
										data-validation="required validatedoc">
								</div>
							</c:if>
							</div>
						</div>
						
						<div class="form-group clearfix">
						<label class="col-md-4 noPad">Void Bank Cheque<span class="text-danger">&nbsp;*</span></label>
						<div class="col-md-8 padding_right">
			 			<c:if test="${!empty bankChequeDoc.fileUrl}">	
								<div class="clearfix">
									<div class="pull-left fileContainer" title="${bankChequeDoc.fileName}">
									${bankChequeDoc.fileName}
									</div>
									<div class="pull-left mt5">
										<a target="_blank" href="${bankChequeDoc.fileUrl}" class="anchor-black"><i class="fa fa-download" title="Download"></i></a>
										<a href="javascript:void(0)" onclick="viewFile('${bankChequeDoc.fileUrl}','${bankChequeDoc.fileName}','${bankChequeDoc.fileContentType}')"; class="paddingLeft PaddingRight anchor-black"><i class="fa fa-eye" title="View"></i></a>
										<a href="javascript:void(0)" onclick="deleteDocument('${bankChequeDoc.uuid}','Bank Cheque')" class="anchor-black"><i class="fa fa-trash" title="Delete"></i></a>
									</div>
								</div>
						</c:if>
						
						<c:if test="${empty bankChequeDoc.fileUrl}">
							<div class="input-group col-md-12 errorInptgroup" id="uploadFileGroup1"
									style="margin: 0 auto">
									<label class="input-group-btn"> 
									<span class="btn btn-primary" onchange="getBankCheque();"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File <input type="file" style="display: none;" id="uploadBankChequeBtn" class="upload"> </span>
									</label> 
									<input type="text" class="form-control" readonly id="orglocbankCheque" name="orglocbankCheque" placeholder="Upload File ..." field-name="Bank Cheque"
										data-validation="required validatedoc" >
								</div>
						</c:if>
						</div>
						</div>
						
						
						<div class="form-group clearfix">
						<label class="col-md-4 noPad">Registration or Incorporation of recording<span class="text-danger">&nbsp;*</span></label>
							<div class="col-md-8 padding_right">
							
							<c:if test="${!empty registrationDoc.fileUrl}">
							<div class="clearfix">
									<div class="pull-left fileContainer" title="${registrationDoc.fileName}">
									${registrationDoc.fileName}
									</div>
									<div class="pull-left mt5">
										<a target="_blank" href="${registrationDoc.fileUrl}" class="anchor-black"><i class="fa fa-download" title="Download"></i></a>
										<a href="javascript:void(0)" onclick="viewFile('${registrationDoc.fileUrl}','${registrationDoc.fileName}','${registrationDoc.fileContentType}')"; class="paddingLeft PaddingRight anchor-black"><i class="fa fa-eye" title="View"></i></a>
										<a href="javascript:void(0)" onclick="deleteDocument('${registrationDoc.uuid}','Registration')" class="anchor-black"><i class="fa fa-trash" title="Delete"></i></a>
									</div>
								</div>
							</c:if>
							
							<c:if test="${empty registrationDoc.fileUrl}">
							     <div class="input-group col-md-12 errorInptgroup" id="uploadFileGroup2"
									style="margin: 0 auto">
									<label class="input-group-btn"> 
									<span class="btn btn-primary" onchange="getRegistrationDoc();"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File <input type="file" style="display: none;" id="uploadRegistrationBtn" class="upload"> </span>
									</label> 
									<input type="text" class="form-control" readonly id="orglocregistrationDocument" name="orglocregistrationDocument" placeholder="Upload File ..." field-name="Registration or Incorporation of recording"
										data-validation="required validatedoc">
								</div>
							</c:if>
							
							
							
							
							
							</div>
						</div>
						
						<div class="form-group clearfix">
						<label class="col-md-4 noPad">Annual Report Business License<span class="text-danger">&nbsp;*</span></label>
							<div class="col-md-8 padding_right">
							
							<c:if test="${!empty annulReportBusinessDoc.fileUrl}">
								<div class="clearfix">
									<div class="pull-left fileContainer" title="${annulReportBusinessDoc.fileName}">
									${annulReportBusinessDoc.fileName}
									</div>
									<div class="pull-left mt5">
										<a target="_blank" href="${annulReportBusinessDoc.fileUrl}" class="anchor-black"><i class="fa fa-download" title="Download"></i></a>
										<a href="javascript:void(0)" onclick="viewFile('${annulReportBusinessDoc.fileUrl}','${annulReportBusinessDoc.fileName}','${annulReportBusinessDoc.fileContentType}')"; class="paddingLeft PaddingRight anchor-black"><i class="fa fa-eye" title="View"></i></a>
										<a href="javascript:void(0)" onclick="deleteDocument('${annulReportBusinessDoc.uuid}','Annual Report Business License')" class="anchor-black"><i class="fa fa-trash" title="Delete"></i></a>
									</div>
								</div>
							</c:if>
							
							<c:if test="${empty annulReportBusinessDoc.fileUrl}">
							<div class="input-group col-md-12 errorInptgroup" id="uploadFileGroup3"
									style="margin: 0 auto">
									<label class="input-group-btn"> 
									<span class="btn btn-primary" onchange="getAnnulBusinessLincense();"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File <input type="file" style="display: none;" id="uploadAnnualBusinessLicenseBtn" class="upload"> </span>
									</label> 
									<input type="text" class="form-control" readonly id="orglocannualBusinessLicense" name="orglocannualBusinessLicense" placeholder="Upload File ..." field-name="Annual Report Business License"
										data-validation="required validatedoc">
								</div>
							</c:if>
							</div>
						</div>
						
						<div class="form-group clearfix">
						<label class="col-md-4 noPad">W-9,W-8 Ben-E<span class="text-danger">&nbsp;*</span></label>
							<div class="col-md-8 padding_right">
							
							
							<c:if test="${!empty w9Doc.fileUrl}">
								<div class="clearfix">
									<div class="pull-left fileContainer" title="${w9Doc.fileName}">
									${w9Doc.fileName}
									</div>
									<div class="pull-left mt5">
										<a target="_blank" href="${w9Doc.fileUrl}" class="anchor-black"><i class="fa fa-download" title="Download"></i></a>
										<a href="javascript:void(0)" onclick="viewFile('${w9Doc.fileUrl}','${w9Doc.fileName}','${w9Doc.fileContentType}')"; class="paddingLeft PaddingRight anchor-black"><i class="fa fa-eye" title="View"></i></a>
										<a href="javascript:void(0)" onclick="deleteDocument('${w9Doc.uuid}','W-9,W-8 Ben-E')" class="anchor-black"><i class="fa fa-trash" title="Delete"></i></a>
									</div>
								</div>
							</c:if>
							
							<c:if test="${empty w9Doc.fileUrl}">
							<div class="input-group col-md-12 errorInptgroup" id="uploadFileGroup4"
									style="margin: 0 auto">
									<label class="input-group-btn"> 
									<span class="btn btn-primary" onchange="getW9Doc();"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File <input type="file" style="display: none;" id="uploadW9Btn" class="upload"> </span>
									</label> 
									<input type="text" class="form-control" readonly id="orglocw9Document" name="orglocw9Document" placeholder="Upload File ..." field-name="W-9,W-8 Ben-E"
										data-validation="required validatedoc">
								</div>
							</c:if>	
								
								
							</div>
						</div>
						
						
				      </div>
			      </div>
			      
			      <div class="clearfix">
			      		<div class="pull-right">
						<input type="button" value="Cancel" class="btn btn-default" onClick="self.location='/app/settings'">
						</div>
						<div class="pull-right">
						<input  type="button" onclick="saveDocuments();"  value="Save" id="save_documents_button" class="btn btn-primary mr">
						</div>
					</div>
			        
			    </div>
			   </form:form>
		
	</div>
  </div>
</div>


<script>

if(document.getElementById("uploadBusinessLicenseBtn") != null){
	document.getElementById("uploadBusinessLicenseBtn").onchange = function () {
	  	$('#orglocbusinessLicense').val($(this).val().replace(/C:\\fakepath\\/i, ''));
	  };
}
 
 if(document.getElementById("uploadBankChequeBtn") != null){
	 document.getElementById("uploadBankChequeBtn").onchange = function () {
		  	$('#orglocbankCheque').val($(this).val().replace(/C:\\fakepath\\/i, ''));
	  };
 }
  
  
  if(document.getElementById("uploadRegistrationBtn") != null){
	  document.getElementById("uploadRegistrationBtn").onchange = function () {
		  	$('#orglocregistrationDocument').val($(this).val().replace(/C:\\fakepath\\/i, ''));
	  };  
  }
  
  if(document.getElementById("uploadAnnualBusinessLicenseBtn") != null){
	  document.getElementById("uploadAnnualBusinessLicenseBtn").onchange = function () {
		  	$('#orglocannualBusinessLicense').val($(this).val().replace(/C:\\fakepath\\/i, ''));
	  };
  }
  
  if(document.getElementById("uploadW9Btn") != null){
	  document.getElementById("uploadW9Btn").onchange = function () {
		  	$('#orglocw9Document').val($(this).val().replace(/C:\\fakepath\\/i, ''));
	  };
  }
	  
 </script>
 
 
 <script>
 function getBusinessLicense(){
	 fileUpload("uploadBusinessLicenseBtn", "CompanyOnboardDocs", "documents_form","businessLicense");
 }
 
 function getBankCheque(){
	 fileUpload("uploadBankChequeBtn", "CompanyOnboardDocs", "documents_form","bankCheque");
 }
 
 function getRegistrationDoc(){
	 fileUpload("uploadRegistrationBtn", "CompanyOnboardDocs", "documents_form","registrationDocument");
 }
 
 function getAnnulBusinessLincense(){
	 fileUpload("uploadAnnualBusinessLicenseBtn", "CompanyOnboardDocs", "documents_form","annualBusinessLicense");
 }
 
 function getW9Doc(){
	 fileUpload("uploadW9Btn", "CompanyOnboardDocs", "documents_form","w9Document");
 }
 </script>
 
 
 <script type="text/javascript">
	function saveDocuments(){
			var validationSettings = {
			        errorMessagePosition : 'element',
			        borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
		         }; 
			 if ($('#documents_form').validate(false, validationSettings)){
	      	    var formData = getFormData("documents_form");
	      		var sourceleadUrl = '${sourceleadUrl}';
	      		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	      		$.ajax({
						type : "POST",
						 data: formData,
						 url: sourceleadUrl+"/saveOrgLocationDocuments",
								success : function(response) {
									if(response.response == 'SUCCESS'){
										location.replace(sourceleadUrl+"/orgSettings?&tabType=documents");
						        	}else{
						        		$("#loading").removeAttr("style"); 
						        	}
						
						},
					}); 
					return true;
			 }else{ 
					return false;
			 } 
		}
		$('body').on('blur', '#documents_form', function() {
		$("#documents_form").showHelpOnFocus();
		$("#documents_form").validateOnBlur(false, validationSettings);
	});
</script>


<script>
function deleteDocument(uuid,documentType){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are your sure to delete <strong>"+documentType+"</strong> document</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onClick='deleteDoc(\""+uuid+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelPopUp();'></b>";
	result = result +"	</div>";
	$("#docPopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>


<script>
function deleteDoc(uuid){
	var sourceleadUrl = '${sourceleadUrl}';
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	   $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/deleteOnboardDocument?${_csrf.parameterName}=${_csrf.token}&uuid="+uuid, 
	        success: function(data) {
	        	if(data == 'success'){
	        		location.replace(sourceleadUrl+"/orgSettings?&tabType=documents");
	        	}
	        },
	    });
	 
}
</script>

<script type="text/javascript">
function cancelPopUp(){
	$("#docPopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

	