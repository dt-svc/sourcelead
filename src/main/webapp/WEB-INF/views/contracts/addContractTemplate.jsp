<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="resources/js/formvalidator.js"></script>
<!-- <script src="resources/js/chosen.jquery.min.js"></script> --> 


<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<!-- end files for autocomplete with tagit  -->

<!-- Content Area -->



<section class="content-wraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Add Contract Template</h2>
  </div>
  <div class="pull-right brud-crum">Home &raquo; Add Contract Template</div>
</section>

<!-- <section class="addcontrHead">
	<a href="#" class="active">Template</a>
	<a href="#">Preview</a>
</section> -->
	
<div class="content-area content-new clearfix">
      <div class="clearfix form-horizontal">
       <form:form id="template_form" name="template_form" method="post">
        <div class="row newContrWrap">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
           
            <div class="form-group">
              <label class="col-md-4">Template name<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8" id="templateDiv">
                <input type="text"  id="templateName" name="templateName" value="${contractsTemplate.templateName}"  field-name="Template Name" data-validation="required validate_Space" type="text" class="form-control">
              </div>
              <input type="hidden" name="templateId" id="templateId" value="${contractsTemplate.id}"/>
            </div>
            
            <div class="form-group">
              <label class="col-md-4">Template Type<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select class="chosen-select form-control" id="contractType" field-name="Template Type"  name="contractType" onchange="contractTemplateType(); validateContractTemplateName();">
                     <option value="">&nbsp;&nbsp;&nbsp;&nbsp;---Select Template Type---</option>
	                 <option  ${contractsTemplate.templateType  == 'Client MSA' ? 'selected' : ''}>Client MSA</option>
	                 <option  ${contractsTemplate.templateType  == 'Vendor MSA' ? 'selected' : ''}>Vendor MSA</option>
	                 <option  ${contractsTemplate.templateType  == 'Client SOW' ? 'selected' : ''}>Client SOW</option>
	                 <option  ${contractsTemplate.templateType  == 'Vendor SOW' ? 'selected' : ''}>Vendor SOW</option>
	                 <option  ${contractsTemplate.templateType  == 'NDA' ? 'selected' : ''}>NDA</option>
	                 <option  ${contractsTemplate.templateType  == 'NSA' ? 'selected' : ''}>NSA</option>
                   
                   <!--  <option value="Client MSA">Client MSA</option>
                    <option value="Vendor MSA">Vendor MSA</option>
                    <option value="Client SOW">Client SOW</option>
                    <option value="Vendor SOW">Vendor SOW</option>
                    <option value="NDA">NDA</option>
                    <option value="NSA">NSA</option> -->
                  </select>
            </div>
            </div>
             
          <div class="form-group">
              <label class="col-md-4">Letter head</label>
              <div class="col-md-8">
                  <select class="chosen-select form-control" id="letterHead" name="letterHead">
                    <option value="Letter head">Letter head</option>
                    <option value="Letter head-1">Letter head-1</option>
                  </select>
              </div>
          </div>             
                   
            <div class="form-group">
              <label class="col-md-4">Description</label>
              <div class="col-md-8">
					<textarea class="form-control" rows="3" id ="description"  name="description" data-validation-optional="true" data-validation="validate_Space validate_length length1-250">${contractsTemplate.description}</textarea>
              </div>
            </div>            
            
        </div>
      </div>
      
<div class="row newContrWrap">
	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
		<div class="col-md-9">
			<textarea id="txtEditor" name="templateContent"  class="form-control newContrtextarea"></textarea>
		</div>  
		
		
		<div class="col-md-3 contrCus">
			<h3>Custom Files</h3>
			<ul id="clientmsa" style="display:block">
                <li onclick="copyToClipboard('text_element1')"><i></i><p class="copyText" id="text_element1">[Client Name]</p></li>
                <li onclick="copyToClipboard('text_element2')"><i></i><p class="copyText" id="text_element2">[Client Contact]</p></li>
                <li onclick="copyToClipboard('text_element3')"><i></i><p class="copyText" id="text_element3">[Client location]</p></li>
                <li onclick="copyToClipboard('text_element4')"><i></i><p class="copyText" id="text_element4">[Company Location]</p></li>
                <li onclick="copyToClipboard('text_element5')"><i></i><p class="copyText" id="text_element5">[Company Name]</p></li>
                <li onclick="copyToClipboard('text_element6')"><i></i><p class="copyText" id="text_element6">[Start Date]</p></li>
                <li onclick="copyToClipboard('text_element7')"><i></i><p class="copyText" id="text_element7">[End date]</li>
                <li onclick="copyToClipboard('text_element8')"><i></i><p class="copyText" id="text_element8">[State Of Jurisdiction]</p></li>
                <li onclick="copyToClipboard('text_element9')"><i></i><p class="copyText" id="text_element9">[Offer Rate]</li>
				 <li onclick="copyToClipboard('text_element10')"><i></i><p class="copyText" id="text_element10">[Billing Rate]</li>
				 <li onclick="copyToClipboard('text_element11')"><i></i><p class="copyText" id="text_element11">[Overtime]</li>
				 <li onclick="copyToClipboard('text_element12')"><i></i><p class="copyText" id="text_element12">[Payment Term]</li>
            </ul>
            
            <ul id="vendormsa" style="display:none">
                <li onclick="copyToClipboard('text_element13')"><i></i><p class="copyText" id="text_element13">[Vendor Name]</p></li>
                <li onclick="copyToClipboard('text_element14')"><i></i><p class="copyText" id="text_element14">[Vendor Contact]</p></li>
                <li onclick="copyToClipboard('text_element15')"><i></i><p class="copyText" id="text_element15">[Vendor location]</p></li>
                <li onclick="copyToClipboard('text_element16')"><i></i><p class="copyText" id="text_element16">[Company Location]</p></li>
                <li onclick="copyToClipboard('text_element17')"><i></i><p class="copyText" id="text_element17">[Company Name]</p></li>
                <li onclick="copyToClipboard('text_element18')"><i></i><p class="copyText" id="text_element18">[Start Date]</p></li>
                <li onclick="copyToClipboard('text_element19')"><i></i><p class="copyText" id="text_element19">[End date]</li>
                <li onclick="copyToClipboard('text_element20')"><i></i><p class="copyText" id="text_element20">[State Of Jurisdiction]</p></li>
                <li onclick="copyToClipboard('text_element21')"><i></i><p class="copyText" id="text_element21">[Offer Rate]</li>
				 <li onclick="copyToClipboard('text_element22')"><i></i><p class="copyText" id="text_element22">[Billing Rate]</li>
				 <li onclick="copyToClipboard('text_element23')"><i></i><p class="copyText" id="text_element23">[Overtime]</li>
				 <li onclick="copyToClipboard('text_element24')"><i></i><p class="copyText" id="text_element24">[Payment Term]</li>
            </ul>
			<ul id="clientsow" style="display:none">
				<li onclick="copyToClipboard('text_element25')"><i></i><p class="copyText" id="text_element25">[Client Name]</p></li>
				 <li onclick="copyToClipboard('text_element26')"><i></i><p class="copyText" id="text_element26">[Client Contact]</li>
				 <li onclick="copyToClipboard('text_element27')"><i></i><p class="copyText" id="text_element27">[Associate Name]</li>
				 <li onclick="copyToClipboard('text_element28')"><i></i><p class="copyText" id="text_element28">[Client Location]</li>
				 <li onclick="copyToClipboard('text_element29')"><i></i><p class="copyText" id="text_element29">[Requirement Title]</li>
				 <li onclick="copyToClipboard('text_element31')"><i></i><p class="copyText" id="text_element31">[Offer Rate]</li>
				 <li onclick="copyToClipboard('text_element32')"><i></i><p class="copyText" id="text_element32">[Billing Rate]]</li>
				 <li onclick="copyToClipboard('text_element33')"><i></i><p class="copyText" id="text_element33">[Overtime]</li>
				 <li onclick="copyToClipboard('text_element34')"><i></i><p class="copyText" id="text_element34">[Payment Term]</li>
				 <li onclick="copyToClipboard('text_element35')"><i></i><p class="copyText" id="text_element35">[Start Date]</p></li>
				 <li onclick="copyToClipboard('text_element36')"><i></i><p class="copyText" id="text_element36">[End date]</li>
				 <li onclick="copyToClipboard('text_element37')"><i></i><p class="copyText" id="text_element37">[Company Location]</p></li>
                <li onclick="copyToClipboard('text_element38')"><i></i><p class="copyText" id="text_element38">[Company Name]</p></li>
			</ul>
			
			<ul id="vendorsow" style="display:none">
				 <li onclick="copyToClipboard('text_element39')"><i></i><p class="copyText" id="text_element39">[Vendor Name]</p></li>
				 <li onclick="copyToClipboard('text_element40')"><i></i><p class="copyText" id="text_element40">[Vendor Contact]</li>
				 <li onclick="copyToClipboard('text_element41')"><i></i><p class="copyText" id="text_element41">[Associate Name]</li>
				 <li onclick="copyToClipboard('text_element42')"><i></i><p class="copyText" id="text_element42">[Vendor Location]</li>
				 <li onclick="copyToClipboard('text_element43')"><i></i><p class="copyText" id="text_element43">[Requirement Title]</li>
				 <li onclick="copyToClipboard('text_element45')"><i></i><p class="copyText" id="text_element45">[Offer Rate]</li>
				 <li onclick="copyToClipboard('text_element46')"><i></i><p class="copyText" id="text_element46">[Billing Rate]</li>
				 <li onclick="copyToClipboard('text_element47')"><i></i><p class="copyText" id="text_element47">[Overtime]</li>
				 <li onclick="copyToClipboard('text_element48')"><i></i><p class="copyText" id="text_element48">[Payment Term]</li>
				 <li onclick="copyToClipboard('text_element49')"><i></i><p class="copyText" id="text_element49">[End date]</li>
				 <li onclick="copyToClipboard('text_element50')"><i></i><p class="copyText" id="text_element50">[Company Location]</p></li>
                <li onclick="copyToClipboard('text_element51')"><i></i><p class="copyText" id="text_element51">[Company Name]</p></li>
			</ul>
			<ul id="nda" style="display:none">
				 <li onclick="copyToClipboard('text_element52')"><i></i><p class="copyText" id="text_element52">[Vendor Name]</li>
				 <li onclick="copyToClipboard('text_element53')"><i></i><p class="copyText" id="text_element53">[Vendor Contact]</li>
				 <li onclick="copyToClipboard('text_element54')"><i></i><p class="copyText" id="text_element54">[Vendor Location]</li>
				 <li onclick="copyToClipboard('text_element55')"><i></i><p class="copyText" id="text_element55">[Company Location]</p></li>
                <li onclick="copyToClipboard('text_element56')"><i></i><p class="copyText" id="text_element56">[Company Name]</p></li>
                 <li onclick="copyToClipboard('text_element63')"><i></i><p class="copyText" id="text_element63">[State Of Jurisdiction]</p></li>
                
			</ul>
			<ul id="nsa" style="display:none">
				 <li onclick="copyToClipboard('text_element57')"><i></i><p class="copyText" id="text_element57">[Vendor Name]</li>
				 <li onclick="copyToClipboard('text_element58')"><i></i><p class="copyText" id="text_element58">[Vendor Contact]</li>
				 <li onclick="copyToClipboard('text_element59')"><i></i><p class="copyText" id="text_element59">[Vendor Location]</li>
				 <li onclick="copyToClipboard('text_element60')"><i></i><p class="copyText" id="text_element60">[Company Location]</p></li>
                <li onclick="copyToClipboard('text_element61')"><i></i><p class="copyText" id="text_element61">[Company Name]</p></li>
                 <li onclick="copyToClipboard('text_element62')"><i></i><p class="copyText" id="text_element62">[State Of Jurisdiction]</p></li>
			</ul>
		</div>  		      
	</div>
</div>      

      <div class="form-footer clearfix contrFoot">
        <div class="col-lg-12">
          <div class="pull-right">
          	<!-- <input type="button" value="Preview" id="save_Preview" class="btn btn-success" onclick="generatePDF();">  -->
            <input type="button" value="Save Template" id="save_template" onclick="saveTemplate(); " class="btn btn-primary"> 
          <!--  <input type="button" value="Back" class="btn btn-danger">
			<a href="add-contract-template-preview.html"><input type="button" value="Next" class="btn btn-primary ml10"></a> -->
          </div>
        </div>
      </div>
      
      

<!-- Content Area Ends-->
</form:form>
</div>
</div>
</section>
<div class="side_mask"></div>
<div class="prevPop">
	<div class="prevPopClose" onclick="previewclose();"><i class="fa fa-times"></i></div>
	<embed id="iFrame"  src="<c:url value='/testController'/>"></embed>
</div>

 <script src="resources/js/editor.js"></script>
    <link href="resources/css/editor.css" type="text/css" rel="stylesheet"/>
		<script>
			$(document).ready(function() {
				$("#txtEditor").Editor();
			});
</script> 

<script>
function previewshow(){
	$(".side_mask").fadeIn(200);
	$(".prevPop").fadeIn(100);
}
function previewclose(){
	$(".side_mask").fadeOut(200);
	$(".prevPop").fadeOut(100);
}
</script> 

<script>
function copyToClipboard(elementId) {
    var aux = document.createElement("input");
    aux.setAttribute("value", "");
    aux.setAttribute("value", document.getElementById(elementId).innerHTML);
    document.body.appendChild(aux);
    aux.select();
    document.execCommand("copy");
    document.body.removeChild(aux);
  }
  function log(){
      console.log('---')
  }
</script>


<script type="text/javascript">
function contractTemplateType(){
	var contractType = "";
	var type = $("#contractType").val();
	if(type != ''){
		contractType = type;
	}else{
		contractType = "${contractsTemplate.templateType}";
	}
	
	if(contractType == "Client MSA"){
		document.getElementById("vendormsa").style.display="none";
		document.getElementById("clientsow").style.display="none";
		document.getElementById("vendorsow").style.display="none";
		document.getElementById("nda").style.display="none";
		document.getElementById("nsa").style.display="none";
		document.getElementById("clientmsa").style.display="block";
	}
	if(contractType == "Vendor MSA"){
		document.getElementById("clientmsa").style.display="none";
		document.getElementById("clientsow").style.display="none";
		document.getElementById("vendorsow").style.display="none";
		document.getElementById("nda").style.display="none";
		document.getElementById("nsa").style.display="none";
		document.getElementById("vendormsa").style.display="block";
	}
	if(contractType == "Client SOW"){
		document.getElementById("clientmsa").style.display="none";
		document.getElementById("vendormsa").style.display="none";
		document.getElementById("vendorsow").style.display="none";
		document.getElementById("nda").style.display="none";
		document.getElementById("nsa").style.display="none";
		document.getElementById("clientsow").style.display="block";
	}
	
	if(contractType == "Vendor SOW"){
		document.getElementById("clientmsa").style.display="none";
		document.getElementById("vendormsa").style.display="none";
		document.getElementById("clientsow").style.display="none";
		document.getElementById("nda").style.display="none";
		document.getElementById("nsa").style.display="none";
		document.getElementById("vendorsow").style.display="block";
	}
	if(contractType == "NDA"){
		document.getElementById("clientmsa").style.display="none";
		document.getElementById("vendormsa").style.display="none";
		document.getElementById("clientsow").style.display="none";
		document.getElementById("vendorsow").style.display="none";
		document.getElementById("nsa").style.display="none";
		document.getElementById("nda").style.display="block";
	}
	if(contractType == "NSA"){
		document.getElementById("clientmsa").style.display="none";
		document.getElementById("vendormsa").style.display="none";
		document.getElementById("clientsow").style.display="none";
		document.getElementById("vendorsow").style.display="none";
		document.getElementById("nda").style.display="none";
		document.getElementById("nsa").style.display="block";
	}
}

</script>



<script type="text/javascript">
function generatePDF(id) {
	var ee = $("#txtEditor").val($('.Editor-editor').html())
	var frm = $('#template_form').serializeFormJSON();
	var con = JSON.stringify(frm);
	con = con.replace(/[{}]/g, "");
	  //It will escape all the special characters
   var jsonData = encodeURIComponent(con);
	window.open('http://localhost:8080/sourcelead/testController/'+id+'','_newtab');

}</script>

<script type="text/javascript">
	function saveTemplate() {
		 var ee = $("#txtEditor").val($('.Editor-editor').html()); 
		if ($('#template_form').validate(false, validationSettings)) {
			var frm = $('#template_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			  //It will escape all the special characters
	        var jsonData = encodeURIComponent(con);
			var sourceleadUrl = '${sourceleadUrl}';
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	 		$.ajax({
						type : "POST",
						url : sourceleadUrl
								+ "/saveTemplate?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
						success : function(result) {
							 /* generatePDF(result);  */
							location.replace(sourceleadUrl+ "/orgContractTemplate");
						},
					}); 
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#template_form', function() {
		$("#template_form").showHelpOnFocus();
		$("#template_form").validateOnBlur(false, validationSettings);
	});
</script>

<script type="text/javascript">
$(document).ready(function(){
	var templateId  = "${templateId}";
	editContractTemplate(templateId);
	
/* 	var contractsTemplate = "${templateContent}";
		$(".Editor-container").find('.Editor-editor').html(contractsTemplate);	 */
	
});
//$(".Editor-container").find('.Editor-editor').html("Rajashekar").css('border','1px solid red');
</script>

<script>
function editContractTemplate(templateId){
	
	contractTemplateType();
	var sourceleadUrl = '${sourceleadUrl}';
	if(templateId !=''){
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/getEditContractTemplateDetails?${_csrf.parameterName}=${_csrf.token}&templateId="+templateId,
			success : function(data) {
				var json = JSON.parse(data);
				$(".Editor-container").find('.Editor-editor').html(json['templateContent']);
			},
		}); 
	}
	
}



</script>
<script>
function validateContractTemplateName(){
	var templateName = $('#templateName').val();
	var contractType = $('#contractType').val(); 
	var sourceleadUrl ='${sourceleadUrl}';
	 if(templateName != null && templateName != "" && contractType != null && contractType != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateContractTemplateName?${_csrf.parameterName}=${_csrf.token}&templateName="+templateName+"&contractType="+contractType, 
		        success: function(data) {
		        	if(data == 'true'){
		        		
						  $("input#templateName").after("<p class='jquery_form_error_message'>"+templateName+" is Already Existed</span>");
						  $("#save_template").attr("disabled", "disabled");
						//  $("#save_template").addClass('button').val('Processing..');
		        	}
		        	else{
		        		 $('input#templateName').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#templateDiv').find('p.jquery_form_error_message').remove(); 
		        		 $("#save_template" ).addClass('button').val('Save Template');
                         $("#save_template").removeAttr("disabled");
		        	}
		        	}
		        });
	 }
}

</script>


<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->




