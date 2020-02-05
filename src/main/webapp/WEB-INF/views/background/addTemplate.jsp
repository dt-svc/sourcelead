	
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
        <h2>Add Template</h2>
      </div>
      <div class="pull-right brud-crum">Settings &raquo; Performance &raquo; Add Template</div>
    </section>
    <div class="clearfix">
      <div class="timesheet_out clearfix minHeightcol5 box-shadow-large rounded-2">
        <div class="mt25 clearfix"></div>
        <div class="col-md-6">
          <label class="col-md-4 mt5">Template Name</label>
          <div class="col-md-8">
            <input type="text" class="form-control">
          </div>
        </div>
        <div class="clearfix"></div>
        <div class="col-md-6">
          <label class="col-md-4 mt5">Description</label>
          <div class="col-md-8">
            <textarea class="form-control" rows="3"></textarea>
          </div>
        </div>
        <div class="clearfix"></div>

        <div class="col-md-6 mt25">
          <div class="clearfix">
            <label class="col-md-4 mt10">Status</label>
            <div class="col-md-7 padding_right">
            <div class="dropdown">
              <button class="btn btn-default dropdown-toggle width100percnt" type="button" id="menu1" data-toggle="dropdown">-- Select Status --
              <span class="caret mt10 pull-right"></span></button>
              <ul class="dropdown-menu width100percnt" role="menu" aria-labelledby="menu1" id="ctBtn">
                <li>
                  <div class="checkbox-new clearfix ml mt5">
                    <input id="status2" class="checkbox-custom" name="status" type="checkbox">
                    <label for="status2" class="checkbox-custom-label"><span class="ml5">Status-1</span></label>
                  </div>
                </li>
                <li role="Address">
                  <div class="checkbox-new clearfix ml mt5">
                    <input id="status3" class="checkbox-custom" name="status" type="checkbox">
                    <label for="status3" class="checkbox-custom-label"><span class="ml5">Status-1</span></label>
                  </div>
                </li>
                <li role="Address">
                  <div class="checkbox-new clearfix ml mt5">
                    <input id="status4" class="checkbox-custom" name="status" type="checkbox">
                    <label for="status4" class="checkbox-custom-label"><span class="ml5">Status-1</span></label>
                  </div>
                </li>
                <li role="Address">
                  <div class="checkbox-new clearfix ml mt5">
                    <input id="status1" class="checkbox-custom" name="status" type="checkbox">
                    <label for="status1" class="checkbox-custom-label"><span class="ml5">Status-1</span></label>
                  </div>
                </li>
                <li role="Address">
                  <a href="status-list.html" class="text-primary">
                    <i class="fa fa-plus paddingRight mr10"></i>Add Status
                  </a>
                </li>
              </ul>
            </div>
          </div>
          <div class="col-md-1 text-center padding_left">
              <a class="fa fa-refresh btn btn-primary newRefreshbtn"></a>
          </div>
          </div>
        </div>
        <div class="clearfix"></div>
        <div class="clearfix">
        <div class="col-md-6">
          <div class="clearfix">
            <label class="col-md-4 mt10">Component Type</label>
            <div class="col-md-7 padding_right">
            <div class="dropdown">
              <button class="btn btn-default dropdown-toggle width100percnt" type="button" id="menu1" data-toggle="dropdown">-- Select Component Type --
              <span class="caret"></span></button>
              <ul class="dropdown-menu ml width100percnt" role="menu" aria-labelledby="menu1" id="ctBtn">
                <li>
                  <div class="checkbox-new clearfix ml mt5">
                    <input id="address" class="checkbox-custom" name="componentType" type="checkbox">
                    <label for="address" class="checkbox-custom-label"><span class="ml5">Address</span></label>
                  </div>
                </li>

                <li>
                  <div class="checkbox-new clearfix ml mt5">
                    <input id="criminialBackground" class="checkbox-custom" name="componentType" type="checkbox">
                    <label for="criminialBackground" class="checkbox-custom-label"><span class="ml5">Criminial Background</span></label>
                  </div>
                </li>
                <li>
                  <div class="checkbox-new clearfix ml mt5">
                    <input id="educational" class="checkbox-custom" name="componentType" type="checkbox">
                    <label for="educational" class="checkbox-custom-label"><span class="ml5">Educational</span></label>
                  </div>
                </li>
                <li>
                  <div class="checkbox-new clearfix ml mt5">
                    <input id="previousEmployment" class="checkbox-custom" name="componentType" type="checkbox">
                    <label for="previousEmployment" class="checkbox-custom-label"><span class="ml5">Previous Employment</span></label>
                  </div>
                </li>
                <li>
                  <div class="checkbox-new clearfix ml mt5">
                    <input id="socialMedia" class="checkbox-custom" name="componentType" type="checkbox">
                    <label for="socialMedia" class="checkbox-custom-label"><span class="ml5">Social Media</span></label>
                  </div>
                </li>
                <li role="Address">
                  <a id="addComponentType">
                    <i class="fa fa-plus paddingRight mr10 text-primary"></i>Add Component Type
                  </a>
                </li>
              </ul>
            </div>
          </div>
          <div class="col-md-1 text-center padding_left">
            <a class="fa fa-refresh btn btn-primary newRefreshbtn"></a>
          </div>
          </div>
        </div>
      </div>
        <div class="clearfix mt mb"></div>
        <div class="clearfix mt mb">
          <div class="col-md-2 col-md-offset-8">
              <div class="dropdown">
              <button class="btn btn-success dropdown-toggle btn-block" type="button" data-toggle="dropdown" id="addFiled-dropdown" aria-expanded="false">Add Type
              <span class="caret"></span></button>
                <ul id="add-field" class="dropdown-menu pull-right">
                  <li><a id="add-text" data-type="text" href="javascript:void(0)"><span class="fa fa-text-width">&nbsp;&nbsp;</span>Text Box</a>
                  </li><li><a id="add-textarea" data-type="textarea" href="javascript:void(0)"><span class="fa fa-text-width">&nbsp;&nbsp;</span>Text Area</a></li>
                  <li><a id="add-select" data-type="select" href="javascript:void(0)"><span class="fa fa-caret-down">&nbsp;&nbsp;&nbsp;</span>Select Box</a></li>
                  <li><a id="add-checkbox" data-type="checkbox" href="javascript:void(0)"><span class="fa fa-check-square-o">&nbsp;&nbsp;</span>Check Box</a></li>
                  <li><a id="add-radio" data-type="radio" href="javascript:void(0)"><span class="fa fa-check-circle-o">&nbsp;&nbsp;</span>Radio Button</a></li>
                  <li><a id="add-comments" data-type="comments" href="javascript:void(0)"><span class="fa fa-comment-o">&nbsp;&nbsp;</span>Comments</a></li>
                  <li><a id="add-star" data-type="star" href="javascript:void(0)"><span class="fa fa-star-o">&nbsp;&nbsp;</span>Star</a></li>
                  <li><a id="add-scale" data-type="scale" href="javascript:void(0)"><span class="fa fa-sliders">&nbsp;&nbsp;</span>Scale</a></li>
                  <li><a id="add-calendar" data-type="calendar" href="javascript:void(0)"><span class="fa fa-calendar">&nbsp;&nbsp;</span>Calendar</a></li>
                  <li><a id="add-file" data-type="file" href="javascript:void(0)"><span class="fa fa-cloud-upload">&nbsp;&nbsp;</span>File Upload</a></li>
                </ul>
              </div>
            </div>
          </div>
      
        <div class="col-md-10">
            <div class="quetionwidget mb" style="display:block;">
              <div class="checkbox-new">
                <input id="address" class="checkbox-custom" name="paidComponent" type="checkbox">
                <label for="address" class="checkbox-custom-label"></label>
                Address
              </div>
              <div class="clearfix"></div>
              <div class="row mt"> <span class="closePop"></span>
                <div class="col-md-6">
                      <label>Status<span class="colorRed ml5">*</span></label>
                      <div class="clearfix">
                        <input type="text" class="form-control">
                      </div>
                </div>
                <div class="col-md-6">
                    <label>Sent to</label>
                    <div class="clearfix">
                      <input type="text" class="form-control">
                    </div>
              </div>
                <div class="col-md-12">
                  <label>Commment</label>
                  <div class="clearfix">
                    <textarea class="form-control" rows="4"></textarea>
                  </div>
                </div>
              </div>
              <div class="clearfix"></div>
              <div class="quetionwidgetFooter clearfix mt">
                <div class="pull-right">
                  <button type="button" class="btn btn-default mr">Cancel</button>
                  <button type="button" class="btn btn-primary">Save</button>
                </div>
              </div>
            </div>
          </div>


          <div class="col-md-10">
            <div class="quetionwidget mb" style="display:block;">
              <div class="checkbox-new">
                <input id="address" class="checkbox-custom" name="paidComponent" type="checkbox">
                <label for="address" class="checkbox-custom-label"></label>
                Criminial Background
              </div>
              <div class="clearfix"></div>
              <div class="row mt"> <span class="closePop"></span>
                <div class="col-md-6">
                      <label>Status<span class="colorRed ml5">*</span></label>
                      <div class="clearfix">
                        <input type="text" class="form-control">
                      </div>
                </div>
                <div class="col-md-6">
                    <label>Component</label>
                    <div class="clearfix">
                      <input type="text" class="form-control">
                    </div>
              </div>
              </div>
              <div class="clearfix"></div>
              <div class="quetionwidgetFooter clearfix mt">
                <div class="pull-right">
                  <button type="button" class="btn btn-default mr">Cancel</button>
                  <button type="button" class="btn btn-primary">Save</button>
                </div>
              </div>
            </div>
          </div>

      </div>
    </div>
  </section>
    </div>
  </section>
        <!-- Content Area Ends-->
<jsp:include page="../masterFooter.jsp" />
<script>
var vmsUrl = '${SourceLeadWeb_vmsUrl}';
var sourceleadUrl = '${SourceLeadWeb_vmsUrl}';

$(document).ready(function() {
})

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
function  ediBgcTemplateById(TemplateId) {
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
	}
</script>
