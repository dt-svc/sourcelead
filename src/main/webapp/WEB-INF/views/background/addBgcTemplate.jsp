	
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<script src="${SourceLeadWeb_vmsUrl}/resources/js/bgcCandidate.js?staticTime=${staticTime}"></script>
 
       <!-- Content Area -->
    <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Add BGC Template</h2>
      </div>
      <div class="pull-right brud-crum">Settings &raquo; Background Check &raquo; Add Template</div>
    </section>
    <div class="timesheet_out clearfix minHeightcol5 box-shadow-large rounded-2 clearfix">
      <div class="clearfix">
      <form id="bgcTemplateFrm" name="bgcTemplateFrm" action="" method="post">
        <div class="mt25 clearfix"></div>
        <div class="col-md-6">
          <label class="col-md-4 mt5">Template Name<span class="colorRed ml5">*</label>
          <div class="col-md-8">
           <input class="form-control" placeholder="Name" type="text"  id ="templateName" name="name" field-name="Name" data-validation="required" data-validation="required validate_length length2-100 validate_AlphaNumber">
          </div>
        </div>
        <div class="clearfix"></div>
        <div class="col-md-6">
          <label class="col-md-4 mt5">Description<span class="colorRed ml5">*</label>
          <div class="col-md-8">
            <textarea placeholder="Description" id="description" name="description" class="form-control" field-name="Description" rows="3" data-validation="required validate_length length2-500"></textarea>
          </div>
        </div>
        <div class="clearfix"></div>

        <div class="col-md-6 mt25">
          <div class="clearfix">
           <div id ="orgStatusDiv">
           	<jsp:include page= "bgcStatusList.jsp"/>
          </div>
          </div>
        </div>
        <div class="clearfix"></div>
        <div class="clearfix">
        <div class="col-md-6">
          <div class="clearfix">
            <div id ="orgComponentsDiv">
           		<jsp:include page= "bgcComponentList.jsp"/>
          </div>
          </div>
        </div>
      </div>
        <div class="clearfix mt mb"></div>
       
       <div id="parent_bgc_div">
        
      
          </div>
          
          </div>
          
         <div class="clearfix"></div>
         <div class="quetionwidgetFooter clearfix mt col-md-10">
           <div class="pull-right">
             <button type="button" class="btn btn-default mr">Cancel</button>
             <button type="button" id ="saveBgcTemplateBtn" class="btn btn-primary" onclick="saveBgcTemplate();" >Save</button>
           </div>
         </div>
		</form>
      </div>
    </div>
  </section>
  
        <!-- Content Area Ends-->
<jsp:include page="../masterFooter.jsp" />
<div class="mask"></div>
<div class="popupWrap" id="componenttypepop">
  <div class="popupHead"><span>Add Component Type</span><i class="closePop pull-right popClose"></i></div>
  <form id="bgcComponentFrm" name="bgcComonentFrm" action="" method="post">
    <div class="popupContent">
        <div class="clearfix mb">
              <input type="hidden" id ="id" name ="id" val=""/>
            <label class="noPad">Component Name <b class="valStar">*</b></label>
            <div class="clearfix">
               <input class="form-control" placeholder="Name" type="text"id ="componentName" name="name" field-name="Name" data-validation="required" data-validation="required validate_length length2-100 validate_AlphaNumber">
            </div>
        </div>
        <div class="clearfix mb">
            <label class="noPad">Description</label>
            <div class="clearfix">
         <textarea placeholder="Description" id="description" name="description" class="form-control" field-name="Description" rows="3" data-validation="required validate_length length2-500"></textarea>
            </div>
        </div>

    </div>
    
    <div class="popupFoot text-right">
      <button type="button" class="slBtn md colorBtn"  id="saveBgcComponentBtn" onclick ="addBgcComponent();">Save</button>
      <button type="button" class="slBtn md popClose" onclick="flushComponent();"> Cancel</button>
    </div>
    
    <div>
</div></form>
</div>
<script>
var vmsUrl = '${SourceLeadWeb_vmsUrl}';
var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';

$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});	



</script>


<script>
function  saveBgcTemplate() {
	if ($('#bgcTemplateFrm').validate(false, validationSettings)) {
		var formData = getFormData("bgcTemplateFrm");
		if($('#statusString').val() !=  null ){
			  delete formData ["statusString"];
			  formData["statusString"] = $('#statusString').val().toString();
  	    }
		if($('#componentString').val() !=  null ){
			  delete formData ["componentString"];
			  formData["componentString"] = $('#componentString').val().toString();
	    }
		// below is for edit functionality of the status
		  $("#saveBgcTemplateBtn").text('Processing..'); 
          $("#saveBgcTemplateBtn").addClass("disabled");
		$.ajax({
			type : "POST",
			data : formData,
			url : vmsUrl + "/bgc/SaveOrUpdateBgcTemplate",
			success : function(result) {
			
				if (result.response == "AWKWARD") {
					location.href = "/errorPage";
				} else if (result.response == "SUCCESS") {
					//console.log(result.object.id);
					saveComponentJsonMap(result.object.id);
					if (result.object == "DataMissing") {
						$("#saveBgcTemplateBtn").removeClass("disabled");
						$("#saveBgcTemplateBtn").text('Save');
						showError("Invalid data");
					} else {
						location.replace(vmsUrl+"/bgc/bgcOrgTemplateList");	
					}
				} else if (result.indexOf('Sign In') != -1) {
					location.replace(sourceleadUrl);
				} else {
					showError("Operation failed, please try again");
				}

			}
		});
	}
}

$('body').on('blur', '#bgcTemplateFrm', function() {
	$("#bgcTemplateFrm").showHelpOnFocus();
	$("#bgcTemplateFrm").validateOnBlur(false, validationSettings);
});
/* function  ediBgcTemplateById(TemplateId) {
			$.ajax({
			type : "GET",
			url : vmsUrl + "/bgc/getBgcTemplateById?id="+TemplateId,
			success : function(data) {
				console.log(data);
				if (data) {
					$(".mask, #modalTasktype").fadeIn();
					$("#TemplateName").val(data.name);
					$("#id").val(data.id);
					$("textarea#description").val(data.description);
				} else {
					showError("Operation failed, please try again");
				}
			}

		});
	} */
</script>

<script>

var templateUuid =' ';
$(document).ready(function(){
	customizeFields(sourceleadUrl,templateUuid);
});

$("ul#ctBtn li").click(function(e){
   e.stopPropagation();
});

$("#addComponentType").click(function() {
  $("#componenttypepop, .mask").fadeIn();
})
function flushComponent() {
  $("#componenttypepop, .mask").fadeOut();
}
$(".popClose").click(function() {
  $("#componenttypepop, .mask").fadeOut();
})
</script>	