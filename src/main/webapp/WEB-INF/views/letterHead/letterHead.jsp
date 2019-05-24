<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 

 <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Letterhead Template</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/app/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;  <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; <a href="<c:url value='${appPath}/orgTemplates'/>"> Templates</a>&raquo;  Letterhead Template</div>
    </section>
    <div class="content-area content-new clearfix">
    <form:form id="letterHead_form" name="letterHead_form" method="post">
      <div class="modal-body clearfix">
        <div class="row"> 
          <!-- Letterhead Area -->
          <div class="letterheadTemplate clearfix">
            <div class="">
            
            <div class="letterhead-body clearfix col-md-3">
            <h5>Letterhead Details</h5>
            <div class="form-group row">
            <label class="col-md-12">Letterhead Name<span class="text-danger paddingLeft">*</span></label>
             <div class="col-md-12" id="letterHeadNameDiv">
                     <input type="text" class="form-control" id="letterHeadName" onblur="validateletterHeadName();" name="letterHeadName"  field-name="Letterhead Name" value="${letterHead.letterHeadName}"  data-validation="required validate_AlphaNumber validate_Space validate_length length1-250" data-validation="required">
              </div>
              </div>
                  
                <div class="form-group row">    
               <label class="col-md-12">Letterhead Description</label>
              <div class="col-md-12">
                <textarea class="form-control" rows="3" id="letterHeadDescription" name="letterHeadDescription">${letterHead.letterHeadDescription}</textarea>
              </div>
              </div>
              </div>
            
              <!-- Letterheadeer Body Area -->
             <div class="letterhead-body clearfix col-md-3">
                 <h5>Template Area</h5>
                  <div class="row form-group">
                    
                     <div class="col-md-8 paddingRight">
                      <div class="clearfix"></div>
                      <label  class="mt5"><strong>Background Color</strong></label>
                      </div>
                      <div class="col-md-4 paddingLeft">
                      	<input type="color" id="templateBackgroundColor" name="templateBackgroundColor" value="${letterHead.templateBgColor}" style="width: 100%; height: 34px;">
						</div>
                  </div>
                  <div class="row form-group clearfix">
                    <label class="col-md-12">Template Type</label>
                    <div class="col-md-12">
                      <div class="clearfix">
                        <select id="templateType" class="sel form-control" name="templateType" >
                      	<option value="">-- Select --</option>
                      	<option value="Letter" ${letterHead.templateType eq 'Letter' ? 'selected' : ''}>Letter (8.5" x 11")</option>	
                      	<option value="A4" ${letterHead.templateType eq 'A4' ? 'selected' : ''}>A4 (8.268" x 11.693")</option>
                       	 <option value="A6" ${letterHead.templateType eq 'A6' ? 'selected' : ''}>A6 (4.134" x 5.827")</option>	 
                      	<option value="Legal" ${letterHead.templateType eq 'Legal' ? 'selected' : ''}>Legal (8.5" x 14")</option>	
                      </select>
                      </div>
                    </div>
                </div>
                <div class="row form-group clearfix" id="lead">
                <div id="Source">
                    <label class="col-md-12">Padding</label>
                    <div class="col-md-3 paddingRight">
                      <input type="text" id="paddingTop" name="paddingTop" placeholder="Top" onblur="validateTemplateType();"class="form-control input-sm" value="${letterHead.paddingTop}">
                    </div>
                    <div class="col-md-3 paddingLeft paddingRight">
                      <input type="text" id="paddingRight" name="paddingRight" placeholder="Right" class="form-control input-sm" onblur="validateTemplateType();" value="${letterHead.paddingRight}">
                    </div>
                    <div class="col-md-3 paddingLeft paddingRight">
                      <input type="text" id="paddingBottom"  name="paddingBottom" placeholder="Bottom" class="form-control input-sm"onblur="validateTemplateType();" value="${letterHead.paddingBottom}">
                    </div>
                    <div class="col-md-3 paddingLeft">
                      <input type="text" id="paddingLeft"  name="paddingLeft" placeholder="Left" class="form-control input-sm" onblur="validateTemplateType();" value="${letterHead.paddingLeft}">
                    </div>
                </div>
            </div> 
            </div>
              
              <!-- Letterheadeer Body Area -->
				<div class="letterhead-Header clearfix  col-md-3">
				<h5>Header</h5>
					<div class="row form-group clearfix">
					<label class="col-md-12">Upload Logo<span class="text-danger paddingLeft">*</span></label>
					<div class="clearfix col-md-12">
					<div class="input-group positionRelative">
					<label class="input-group-btn">
					<span class="btn btn-primary">
					Browse <input style="display: none;" id="uploadBtn" multiple type="file">
					</span>
					</label>
					<input class="form-control input-custom" id="letterHeadFile" placeholder="Upload logo." field-name="Logo" readonly type="text" value="${letterHead.fileName}" data-validation="required validate_Space validateImg">
					<i class="refreshinputfile"></i>
					</div>
					</div>
					<div class="col-md-12 mt">
					<div class="row">
					<div class="col-md-8 paddingRight">
					<label class="clearfix">Align logo</label>
					<select class="form-control sel" id="alignLogo" name="alignLogo">
						<option value="">-- Select --</option>
						<option value="Left" ${letterHead.alignLogo eq 'Left' ? 'selected' : ''}>Left</option>
						<option value="Middle" ${letterHead.alignLogo eq 'Middle' ? 'selected' : ''}>Middle</option>
						<option value="Right" ${letterHead.alignLogo eq 'Right' ? 'selected' : ''}>Right</option>
					</select>
					</div>
					<div class="col-md-4 paddingLeft">
               		<label class="clearfix">Background</label>
               			<input type="color" id="headerBackgroundColor" name="headerBackgroundColor" value="${letterHead.headerBgColor}" style="width: 100%; height: 34px;">
					</div>
						</div>
					</div>
					
					</div>

					<div class="row form-group clearfix">
					
					<label class="col-md-12">Text</label>
					<div class="col-md-12">
					<input type="text" class="form-control" id="headerText" name="headerText" value="${letterHead.headerText}" data-validation-optional="true"  data-validation="validate_Space validate_length length2-100">
					</div>
					
					<div class="col-md-12 mt">
					<div class="row">
					<div class="col-md-8 paddingRight">
					<label class="clearfix">Align Text</label>
					<select class="form-control sel" id="headerTextAlign" name="headerTextAlign">
						<option value="">-- Select --</option>
						<option value="Left" ${letterHead.alignHeaderText eq 'Left' ? 'selected' : ''}>Left</option>
						<option value="Middle"  ${letterHead.alignHeaderText eq 'Middle' ? 'selected' : ''}>Middle</option>
						<option value="Right" ${letterHead.alignHeaderText eq 'Right' ? 'selected' : ''}>Right</option>
					</select>
					</div>
					<!--  <div class="col-md-4 paddingLeft">
               		<label class="clearfix">Text Color</label>
               			<input type="color" id="headerTextColor" name="headerTextColor" value="#f5f5f5" style="width: 100%; height: 34px;">
					</div>  -->
						</div>
					</div>
					</div>
					
				</div>
				

              <!-- Letterhead-Footerarea -->
              <div class="letterhead-Footer clearfix col-md-3">
              	<h5>Footer</h5>
              	   <div class="row form-group clearfix">
                    <label class="col-md-12">Text</label>
					<div class="col-md-12">
						<input type="text" class="form-control" id="footerText" name="footerText" value="${letterHead.footerText}" data-validation-optional="true" data-validation="validate_Space validate_length length2-100">
					</div>
					<div class="col-md-12 mt10">
						<div class="row">
						<div class="col-md-8 paddingRight">
						<label class="clearfix">Align footer</label>
						<select class="form-control sel" id="alignFooter" name="alignFooter">
						<option value="">-- Select --</option>
						<option value="Left" ${letterHead.alignFooterText eq 'Left' ? 'selected' : ''}>Left</option>
						<option value="Middle" ${letterHead.alignFooterText eq 'Middle' ? 'selected' : ''}>Middle</option>
						<option value="Right" ${letterHead.alignFooterText eq 'Right' ? 'selected' : ''}>Right</option>
						</select>
						</div>
						<div class="col-md-4 paddingLeft">
						<label class="clearfix">Background</label>
						<input type="color" id="footerBackgroundColor" name="footerBackgroundColor" value="${letterHead.footerBgColor}" style="width: 100%; height: 34px;">
						</div>
						</div>
					</div>
                </div>
              </div>
              <!-- Letterhead-Footerarea -->
              
               
              </div>
            </div>
            
             <div class="clearfix mt"></div>
             <div class="letterhead-savefooter clearfix mt">
              <div class="col-md-12">
                <div class="pull-right">
<!--                   <button type="button" class="btn btn-default">Stored</button> -->
<!--                   <button type="button" class="btn btn-default">Preview</button> -->
                  <button type="button" class="btn btn-primary" id="saveButton" onmouseover="validateTemplateType();"   onclick="saveLetterheadData();">Save</button>
<!--                   <button type="button" class="btn btn-primary">Print</button> -->
						<button type="button" class="btn btn-default" onClick="javascript:window.location.href = '<c:url value='${appPath}/letterHeadList'/>'">Cancel</button>
                </div>
              </div>
            </div>
            <!-- Letterhead Area --> 
          </div>
        </div>
      </div>
      </form:form>
    </div>
  </section>
  <script>
  function validateTemplateType(){
	  var templateType = $("#templateType").val();
	  var paddingTop = $("#paddingTop").val();
	  var paddingRight = $("#paddingRight").val();
	  var paddingBottom = $("#paddingBottom").val();
	  var paddingLeft = $("#paddingLeft").val();
	  if(templateType != null && templateType != "" && templateType != "undefined"){
		  if((paddingTop == null || paddingTop == "" || paddingTop == "undefined") ||
			  (paddingRight == null || paddingRight == "" || paddingRight == "undefined") ||
				  (paddingBottom == null || paddingBottom == "" || paddingBottom == "undefined") ||
					  (paddingLeft == null || paddingLeft == "" || paddingLeft == "undefined")){
			  $("#lead").find('div.jquery_form_error_message').remove();
			 $("#Source").after("<div class='jquery_form_error_message'>Mandatory give the input</span>");
			 $("#saveButton").attr("disabled",true);
		  }else{
			  $("#lead").find('div.jquery_form_error_message').remove();
			  $("#saveButton").removeAttr("disabled");
		  }
	  }else{
		  $("#lead").find('div.jquery_form_error_message').remove();
		  $("#saveButton").removeAttr("disabled");
	  }	  
  }
  
  function saveLetterheadData(){
		  var letterHeadUUID = '${letterHeadUUID}';
	  		if ($('#letterHead_form').validate(false, validationSettings)) {
			var formData = $($('#letterHead_form')[0]).serialize();
            $.post(sourceleadUrl+ "/saveLetterheadData?${_csrf.parameterName}=${_csrf.token}&letterHeadUUID="+letterHeadUUID, formData, function(data) {
                       if(data == "success"){
                    	   location.replace(sourceleadUrl+"/letterHeadList");
                       }
            });
			return true;
		} else {
			return false;
		} 
	 
  }
  
  </script>
  
  
  <script type="text/javascript">
 function validateletterHeadName() {
	 var letterHeadName = document.getElementById("letterHeadName").value;
	 var letterHeadId = "${letterHead.id}";
	  if(letterHeadName != null && letterHeadName != "undefined" && letterHeadName != ""){
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/validateletterHeadName?"+tokenParamName+"="+tokenValue+"&letterHeadName="+letterHeadName+"&letterHeadId="+letterHeadId, 
		        success: function(data) {
		        	 if(data == true){
		        		 $('#letterHeadNameDiv').find('p.jquery_form_error_message').remove(); 
						  $("input#letterHeadName").after("<p class='jquery_form_error_message'>"+letterHeadName+" is Already Existed</span>");
						  $("#saveButton").attr("disabled", "disabled");
		        	}
		        	else{
		        		 $('input#letterHeadName').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#letterHeadNameDiv').find('p.jquery_form_error_message').remove(); 
                         $("#saveButton").removeAttr("disabled");
                      
		        	}
		        },
		    });
	 } 
 }
 </script>
 
  <script>
  $(function() {
		$("#uploadBtn").change(function() {
			fileUpload("uploadBtn", "LetterHeadDocs", "letterHead_form");
		});
		
 		document.getElementById("uploadBtn").onchange = function () {
 	    document.getElementById("letterHeadFile").value = this.value.split( '\\' ).pop();
 	    };
		 	    
	 });
  
  </script>
  
  <jsp:include page="../masterFooter.jsp" />