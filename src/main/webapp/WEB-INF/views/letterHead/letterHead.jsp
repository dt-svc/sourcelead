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
      <div class="pull-right brud-crum"><a href="<c:url value='/app/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Letterhead Template</div>
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
            <label class="col-md-12">Letterhead Name</label>
             <div class="col-md-12">
                     <input type="text" class="form-control" id="letterHeadName" name="letterHeadName"  field-name="Letterhead Name">
              </div>
              </div>
                    
                <div class="form-group row">    
               <label class="col-md-12">Letterhead Description</label>
              <div class="col-md-12">
                <textarea class="form-control" rows="3" id="letterHeadDescription" name="letterHeadDescription"></textarea>
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
                      	<input type="color" id="templateBackgroundColor" name="templateBackgroundColor" value="#f5f5f5" style="width: 100%; height: 34px;">
						</div>
                  </div>
                  <div class="row form-group clearfix">
                    <label class="col-md-12">Template Type</label>
                    <div class="col-md-12">
                      <div class="clearfix">
                        <select id="templateType" class="sel form-control" name="templateType" onChange="templateType()">
                      	<option value="">-- Select --</option>
                      	<option value="Letter">Letter (8.5" x 11")</option>	
                      	<option value="A4">A4 (8.268" x 11.693")</option>
                      	 <option value="A6">A6 (4.134" x 5.827")</option>	
                      	<option value="Legal">Legal (8.5" x 14")</option>	
                      </select>
                      </div>
                    </div>
                </div>
                <div class="row form-group clearfix">
                    <label class="col-md-12">Padding</label>
                    <div class="col-md-3 paddingRight">
                      <input type="text" name="paddingTop" placeholder="Top" class="form-control input-sm" value="15">
                    </div>
                    <div class="col-md-3 paddingLeft paddingRight">
                      <input type="text" name="paddingRight" placeholder="Right" class="form-control input-sm" value="15">
                    </div>
                    <div class="col-md-3 paddingLeft paddingRight">
                      <input type="text" name="paddingBottom" placeholder="Bottom" class="form-control input-sm" value="15">
                    </div>
                    <div class="col-md-3 paddingLeft">
                      <input type="text" name="paddingLeft" placeholder="Left" class="form-control input-sm" value="15">
                    </div>
                </div>
            </div>
              
              <!-- Letterheadeer Body Area -->
				<div class="letterhead-Header clearfix  col-md-3">
				<h5>Header</h5>
					<div class="row form-group clearfix">
					<label class="col-md-12">Upload Logo</label>
					<div class="clearfix col-md-12">
					<div class="input-group positionRelative">
					<label class="input-group-btn">
					<span class="btn btn-primary">
					Browse <input style="display: none;" id="uploadBtn" multiple type="file">
					</span>
					</label>
					<input class="form-control input-custom" readonly type="text">
					<i class="refreshinputfile"></i>
					</div>
					</div>
					<div class="col-md-12 mt">
					<div class="row">
					<div class="col-md-8 paddingRight">
					<label class="clearfix">Align logo</label>
					<select class="form-control sel" id="alignLogo" name="alignLogo">
						<option value="">-- Select --</option>
						<option value="Left">Left</option>
						<option value="Middle">Middle</option>
						<option value="Right">Right</option>
					</select>
					</div>
					<div class="col-md-4 paddingLeft">
               		<label class="clearfix">Background</label>
               			<input type="color" id="headerBackgroundColor" name="headerBackgroundColor" value="#f5f5f5" style="width: 100%; height: 34px;">
					</div>
						</div>
					</div>
					
					</div>

					<div class="row form-group clearfix">
					<label class="col-md-12">Text</label>
					<div class="col-md-12">
					<input type="text" class="form-control" id="headerText" name="headerText" value=""  data-validation="validate_Space validate_length length1-100">
					</div>
					
					<div class="col-md-12 mt">
					<div class="row">
					<div class="col-md-8 paddingRight">
					<label class="clearfix">Align Text</label>
					<select class="form-control sel" id="headerTextAlign" name="headerTextAlign">
						<option value="">-- Select --</option>
						<option value="Left">Left</option>
						<option value="Middle">Middle</option>
						<option value="Right">Right</option>
					</select>
					</div>
					 <div class="col-md-4 paddingLeft">
               		<label class="clearfix">Text Color</label>
               			<input type="color" id="headerTextColor" name="headerTextColor" value="#f5f5f5" style="width: 100%; height: 34px;">
					</div> 
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
						<input type="text" class="form-control" id="footerText" name="footerText" value=""  data-validation="validate_Space validate_length length1-100">
					</div>
					<div class="col-md-12 mt10">
						<div class="row">
						<div class="col-md-8 paddingRight">
						<label class="clearfix">Align footer</label>
						<select class="form-control sel" id="alignFooter" name="alignFooter">
						<option value="">-- Select --</option>
						<option value="Left">Left</option>
						<option value="Middle">Middle</option>
						<option value="Right">Right</option>
						</select>
						</div>
						<div class="col-md-4 paddingLeft">
						<label class="clearfix">Background</label>
						<input type="color" id="footerBackgroundColor" name="footerBackgroundColor" value="#f5f5f5" style="width: 100%; height: 34px;">
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
                  <button type="button" class="btn btn-default">Stored</button>
                  <button type="button" class="btn btn-default">Preview</button>
                  <button type="button" class="btn btn-default" id="saveButton" onclick="saveLetterheadData();">Save</button>
                  <button type="button" class="btn btn-primary">Print</button>
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
  
  function saveLetterheadData(){
// 	  		if ($('#letterHead_form').validate(false, validationSettings)) {
			var formData = $($('#letterHead_form')[0]).serialize();
            $.post(sourceleadUrl+ "/saveLetterheadData", formData, function(data) {
                       if(data == "success"){
                    	   alert("in")
                       }
            });
			return true;
// 		} else {
// 			return false;
// 		}
  }
  
  </script>
  
  <script>
  $(function() {
		$("#uploadBtn").change(function() {
			fileUpload("uploadBtn", "LetterHeadDocs", "letterHead_form");
		});
	 });
  
  </script>
  
  <jsp:include page="../masterFooter.jsp" />