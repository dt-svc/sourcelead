<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<div class="wraper"> 
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Add Questionnaire</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home </a> &raquo; 
      <a href="${SourceLeadWeb_sourceleadUrl}/questionnaireList">Questionnaire List</a> &raquo;
      Add Questionnaire</div>
    </section>
    <div class="clearfix">
      <div class="timesheet_out clearfix minHeightcol5">
        <div class="mt clearfix"></div>
        <div class="col-md-5">
          <label class="clearfix">Name<span class="colorRed PaddingLeft">*</span></label>
          <div class="clearfix">
            <input type="text" class="form-control">
          </div>
        </div>
        <div class="col-md-5">
          <label class="clearfix">Description<span class="colorRed PaddingLeft">*</span></label>
          <div class="clearfix">
            <textarea class="form-control" rows="2"></textarea>
          </div>
        </div>
        <div class="clearfix mt mb"></div>
        <div class="clearfix mt mb">
          <div class="col-md-4">
            <div class="btn-group pull-left mr">
              <button type="button" class="btn btn-primary" id="newQuestion"><i class="fa fa-plus paddingRight"></i>Add Question </button>
            </div>
          </div>
        </div>
      
        <div class="col-md-12">
            <div class="quetionwidget mb" style="display: none;">
              <div class="row"> <span class="closePop"></span>
                <div class="col-md-6">
                  <div class="row">
                    <div class="col-md-12">
                      <label>Question<span class="colorRed ml5">*</span></label>
                      <div class="clearfix">
                        <input type="text" class="form-control" placeholder="Enter your question">
                      </div>
                    </div>
                  </div>
                  <div class="row mt10">
                    <div class="col-md-12">
                      <label>Type<span class="colorRed ml5">*</span></label>
                      <div class="clearfix positionRelative">
                        <button type="button" class="btn btn-default btn-block dropdownQuestionBtn"> <span class="pull-left" id="valueType">Select Type</span><span class="caret pull-right mt10"></span></button>
                        <div class="dropdownQuestion">
                          <div class="dropdownQuestionInner">
                            <ul class="parentUlQuestion list-unstyled clearfix mb0">
                              <li><a href="javascript:void(0)"><i class="fa fa-list mr"></i>Multiple Choice</a></li>
                              <li><a href="javascript:void(0)"><i class="fa fa-unsorted mr"></i>Dropdown</a></li>
                              <li><a href="javascript:void(0)"><i class="fa fa-check-square-o mr"></i>Checkboxes</a></li>
                              <li><a href="javascript:void(0)"><i class="fa fa-th mr"></i>Matrix / Rating Scale</a></li>
                              <li><a href="javascript:void(0)"><i class="fa fa-star-o mr"></i>Star Rating</a></li>
                              <li><a href="javascript:void(0)"><i class="fa fa-bars mr"></i>Ranking</a></li>
                              <li><a href="javascript:void(0)"><i class="fa fa-cloud-upload mr"></i>File Upload</a></li>
                              <li><a href="javascript:void(0)"><i class="fa fa-sliders mr"></i>Slider</a></li>
                              <li><a href="javascript:void(0)"><i class="fa fa-window-minimize mr"></i>Single Textbox</a></li>
                              <li><a href="javascript:void(0)"><i class="fa fa-server mr"></i>Multiple Textboxes</a></li>
                              <li><a href="javascript:void(0)"><i class="fa fa-comment-o mr"></i>Comment Box</a></li>
                              <li><a href="javascript:void(0)"><i class="fa fa-user-o mr"></i>Contact Information</a></li>
                              <li><a href="javascript:void(0)"><i class="fa fa-sort-desc mr"></i>Matrix of Dropdown Menus</a></li>
                              <li><a href="javascript:void(0)"><i class="fa fa-calendar-check-o mr"></i>Date / Time</a></li>
                            </ul>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-md-6">
                  <label>Help Text<span class="colorRed ml5">*</span></label>
                  <div class="clearfix">
                    <textarea class="form-control" placeholder="Help Text" rows="4"></textarea>
                  </div>
                </div>
              </div>
              <div class="clearfix"></div>
              
              <!-- quetionwidgetcontentArea -->
              <div class="quetionwidgetOption clearfix mt mb" id="radioboxDiv" style="display: none;">
                  <table style="width: 100%;">
                    <tbody>
                      <tr>
                        <td>
                          <div class="width50px">
                            <div class="radioStyles">
                                <input id="radiobox" name="applicableFor" type="radio">
                                <label for="radiobox" class="top-12"></label>
                            </div>
                          </div>
                        </td>
                        <td style="width: 85%;"><div>
                            <input type="text" placeholder="Enter an answer choice" class="form-control">
                          </div></td>
                        <td><ul class="list-unstyled list-inline width90px mt5">
                            <li><i class="fa fa-plus-circle fa-2x"></i></li>
                            <li><i class="fa fa-minus-circle fa-2x"></i></li>
                          </ul></td>
                      </tr>
                    </tbody>
                  </table>
                </div>


              <div class="quetionwidgetOption clearfix mt mb" id="checkBoxDiv" style="display: none;">
                <table style="width: 100%;">
                  <tbody>
                    <tr>
                      <td><div class="width50px">
                          <div class="checkbox-new ml mr pull-left">
                            <input id="qscheck" class="checkbox-custom" name="PartofaCTC" type="checkbox">
                            <label for="qscheck" class="checkbox-custom-label"></label>
                          </div>
                        </div></td>
                      <td style="width: 85%;"><div>
                          <input type="text" placeholder="Enter an answer choice" class="form-control">
                        </div></td>
                      <td><ul class="list-unstyled list-inline width90px mt5">
                          <li><i class="fa fa-plus-circle fa-2x"></i></li>
                          <li><i class="fa fa-minus-circle fa-2x"></i></li>
                        </ul></td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <div class="quetionwidgetOption clearfix mt mb" id="dropdownDiv" style="display: none;">
                <table style="width: 100%;">
                  <tbody>
                    <tr>
                      <td style="width: 90%;"><div>
                          <input type="text" placeholder="Enter an answer choice" class="form-control">
                        </div></td>
                      <td><ul class="list-unstyled list-inline width90px mt5">
                          <li><i class="fa fa-plus-circle fa-2x"></i></li>
                          <li><i class="fa fa-minus-circle fa-2x"></i></li>
                        </ul></td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <div class="quetionwidgetOption clearfix mt mb" id="rankingDiv" style="display: none;">
                <table style="width: 100%;">
                  <tbody>
                    <tr>
                      <td style="width: 90%;"><div>
                          <input type="text" placeholder="Enter a row label" class="form-control">
                        </div></td>
                      <td><ul class="list-unstyled list-inline width90px mt5">
                          <li><i class="fa fa-plus-circle fa-2x"></i></li>
                          <li><i class="fa fa-minus-circle fa-2x"></i></li>
                        </ul></td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <div class="quetionwidgetOption clearfix mt mb" id="matrixRatingScale" style="display: none;">
                <table style="width: 100%;">
                  <tbody>
                    <tr>
                      <td style="width: 90%;"><div>
                          <input type="text" placeholder="Enter an answer choice" class="form-control">
                        </div></td>
                      <td><ul class="list-unstyled list-inline width90px mt5">
                          <li><i class="fa fa-plus-circle fa-2x"></i></li>
                          <li><i class="fa fa-minus-circle fa-2x"></i></li>
                        </ul></td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <div class="quetionwidgetOption clearfix mt mb" id="sliderDiv" style="display: none;">
                <h4 class="col-md-12 margin-top0 mb">Scale Range Labels</h4>
                <div class="clearfix mb"></div>
                <table style="width: 100%;">
                  <tbody>
                    <tr>
                      <td style="width: 100%;"><div class="clearfix">
                          <label class="col-md-2 mt10">Left Side</label>
                          <div class="col-md-7">
                            <input type="text" placeholder="0" class="form-control">
                          </div>
                        </div></td>
                    </tr>
                    <tr>
                      <td style="width: 100%;"><div class="clearfix mt mb">
                          <label class="col-md-2 mt10">Center</label>
                          <div class="col-md-7">
                            <input type="text" placeholder="Enter a lable (optional)" class="form-control">
                          </div>
                        </div></td>
                    </tr>
                    <tr>
                      <td style="width: 100%;"><div class="clearfix">
                          <label class="col-md-2 mt10">Right Side</label>
                          <div class="col-md-7">
                            <input type="text" placeholder="100" class="form-control">
                          </div>
                        </div></td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <div class="quetionwidgetOption clearfix mt mb" id="multipletextboxes" style="display: none;">
                <table style="width: 100%;">
                  <tbody>
                    <tr>
                      <td style="width: 85%;"><div class="clearfix mb">
                          <label class="col-md-2 mt10">Label 1</label>
                          <div class="col-md-10">
                            <input type="text" placeholder="0" class="form-control">
                          </div>
                        </div></td>
                      <td><ul class="list-unstyled list-inline width90px mt5">
                          <li><i class="fa fa-plus-circle fa-2x"></i></li>
                          <li><i class="fa fa-minus-circle fa-2x"></i></li>
                        </ul></td>
                    </tr>
                    <tr>
                      <td style="width: 85%;"><div class="clearfix mb">
                          <label class="col-md-2 mt10">Label 2</label>
                          <div class="col-md-10">
                            <input type="text" placeholder="0" class="form-control">
                          </div>
                        </div></td>
                      <td><ul class="list-unstyled list-inline width90px mt5">
                          <li><i class="fa fa-plus-circle fa-2x"></i></li>
                          <li><i class="fa fa-minus-circle fa-2x"></i></li>
                        </ul></td>
                    </tr>
                    <tr>
                      <td style="width: 85%;"><div class="clearfix mb">
                          <label class="col-md-2 mt10">Label 3</label>
                          <div class="col-md-10">
                            <input type="text" placeholder="0" class="form-control">
                          </div>
                        </div></td>
                      <td><ul class="list-unstyled list-inline width90px mt5">
                          <li><i class="fa fa-plus-circle fa-2x"></i></li>
                          <li><i class="fa fa-minus-circle fa-2x"></i></li>
                        </ul></td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <div class="quetionwidgetOption-second clearfix mt mb" id="contactinformtionDiv" style="display:none;">
                <table class="table">
                  <thead>
                  <th>Type</th>
                    <th>Label</th>
                    <th>Visible</th>
                      </thead>
                  <tbody>
                    <tr>
                      <td><span class="col-md-12 noPad text-left">Name</span></td>
                      <td><div class="clearfix">
                          <input type="text" placeholder="Name" class="form-control">
                        </div></td>
                      <td><div class="checkbox-new clearfix text-center">
                          <input id="name" class="checkbox-custom" name="contactinformation" type="checkbox">
                          <label for="name" class="checkbox-custom-label"></label>
                        </div></td>
                    </tr>
                    <tr>
                      <td><span class="col-md-12 noPad text-left">Company</span></td>
                      <td><div class="clearfix">
                          <input type="text" placeholder="Company" class="form-control">
                        </div></td>
                      <td><div class="checkbox-new clearfix text-center">
                          <input id="company" class="checkbox-custom" name="contactinformation" type="checkbox">
                          <label for="company" class="checkbox-custom-label"></label>
                        </div></td>
                    </tr>
                    <tr>
                      <td><span class="col-md-12 noPad text-left">Address</span></td>
                      <td><div class="clearfix">
                          <input type="text" placeholder="Address" class="form-control">
                        </div></td>
                      <td><div class="checkbox-new clearfix text-center">
                          <input id="address" class="checkbox-custom" name="contactinformation" type="checkbox">
                          <label for="address" class="checkbox-custom-label"></label>
                        </div></td>
                    </tr>
                    <tr>
                      <td><span class="col-md-12 noPad text-left">Address 2</span></td>
                      <td><div class="clearfix">
                          <input type="text" placeholder="Address 2" class="form-control">
                        </div></td>
                      <td><div class="checkbox-new clearfix text-center">
                          <input id="address2" class="checkbox-custom" name="contactinformation" type="checkbox">
                          <label for="address2" class="checkbox-custom-label"></label>
                        </div></td>
                    </tr>
                    <tr>
                      <td><span class="col-md-12 noPad text-left">City / Town</span></td>
                      <td><div class="clearfix">
                          <input type="text" placeholder="City / Town" class="form-control">
                        </div></td>
                      <td><div class="checkbox-new clearfix text-center">
                          <input id="citytown" class="checkbox-custom" name="contactinformation" type="checkbox">
                          <label for="citytown" class="checkbox-custom-label"></label>
                        </div></td>
                    </tr>
                    <tr>
                      <td><span class="col-md-12 noPad text-left">State / Province</span></td>
                      <td><div class="clearfix">
                          <input type="text" placeholder="State / Province" class="form-control">
                        </div></td>
                      <td><div class="checkbox-new clearfix text-center">
                          <input id="stateprovince" class="checkbox-custom" name="contactinformation" type="checkbox">
                          <label for="stateprovince" class="checkbox-custom-label"></label>
                        </div></td>
                    </tr>
                    <tr>
                      <td><span class="col-md-12 noPad text-left">ZIP / Postal Code</span></td>
                      <td><div class="clearfix">
                          <input type="text" placeholder="ZIP / Postal Code" class="form-control">
                        </div></td>
                      <td><div class="checkbox-new clearfix text-center">
                          <input id="zIPPostalCode" class="checkbox-custom" name="contactinformation" type="checkbox">
                          <label for="zIPPostalCode" class="checkbox-custom-label"></label>
                        </div></td>
                    </tr>
                    <tr>
                      <td><span class="col-md-12 noPad text-left">Country</span></td>
                      <td><div class="clearfix">
                          <input type="text" placeholder="Country" class="form-control">
                        </div></td>
                      <td><div class="checkbox-new clearfix text-center">
                          <input id="country" class="checkbox-custom" name="contactinformation" type="checkbox">
                          <label for="country" class="checkbox-custom-label"></label>
                        </div></td>
                    </tr>
                    <tr>
                      <td><span class="col-md-12 noPad text-left">Email</span></td>
                      <td><div class="clearfix">
                          <input type="text" placeholder="Email" class="form-control">
                        </div></td>
                      <td><div class="checkbox-new clearfix text-center">
                          <input id="email" class="checkbox-custom" name="contactinformation" type="checkbox">
                          <label for="email" class="checkbox-custom-label"></label>
                        </div></td>
                    </tr>
                    <tr>
                      <td><span class="col-md-12 noPad text-left">Phone</span></td>
                      <td><div class="clearfix">
                          <input type="text" placeholder="Phone" class="form-control">
                        </div></td>
                      <td><div class="checkbox-new clearfix text-center">
                          <input id="phone" class="checkbox-custom" name="contactinformation" type="checkbox">
                          <label for="phone" class="checkbox-custom-label"></label>
                        </div></td>
                    </tr>
                  </tbody>
                </table>
              </div>
          
         
          
          
              <div class="quetionwidgetOption clearfix mt mb" id="matrixDropdownm" style="display: none;">
                <table style="width: 100%;">
                  <tbody>
                    <tr>
                      <td style="width: 90%;"><div>
                          <input type="text" placeholder="Enter a row label" class="form-control">
                        </div></td>
                      <td><ul class="list-unstyled list-inline width90px mt5">
                          <li><i class="fa fa-plus-circle fa-2x"></i></li>
                          <li><i class="fa fa-minus-circle fa-2x"></i></li>
                        </ul></td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <div class="quetionwidgetOption clearfix mt mb" id="datetime" style="display:none;">
                <table style="width: 100%;">
                  <tbody>
                    <tr>
                      <td style="width:10%;"><div> Label 1 </div></td>
                      <td style="width: 80%;"><div>
                          <input type="text" placeholder="Enter a row label" class="form-control">
                        </div></td>
                      <td><ul class="list-unstyled list-inline width90px mt5">
                          <li><i class="fa fa-plus-circle fa-2x"></i></li>
                          <li><i class="fa fa-minus-circle fa-2x"></i></li>
                        </ul></td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <!-- quetionwidgetcontentAreaEnds -->

              <!-- Star Rating -->
              <div class="quetionwidgetOption clearfix mt mb" id="starRatingDiv" style="display:none;">
                  <div class="col-md-2">
                    <select class="sel" id="starRatingSel">
                      <option selected>Create Star</option>
                      <option value="1">1</option>
                      <option value="2">2</option>
                      <option value="3">3</option>
                      <option value="4">4</option>
                      <option value="5">5</option>
                    </select>
                  </div>
                  <div class="col-md-10">
                      <ul class="list-unstyled list-inline mb0 mt5" id="starRating"></ul>
                  </div>
                  </div>
              <!-- Star Rating -->

              <div class="quetionwidgetFooter clearfix mt">
                <div class="pull-left">
                  <button type="button" class="btn btn-primary"> <i class="fa fa-plus-circle paddingRight"></i>Next Question </button>
                </div>
                <div class="pull-right">
                  <button type="button" class="btn btn-default mr">Cancel</button>
                  <button type="button" class="btn btn-primary">Save</button>
                </div>
              </div>
            </div>
          </div>



      </div>
      
      <!-- Content Area Ends--> 
    </div>
  </section>
</div>
<script>
$(function(){
$("#addPop").click(function() {
	$(".mask, #modalTasktype").fadeIn();
});
$(".popClose").click(function() {
	$(".mask, #modalTasktype").fadeOut();
});

$(".dropdownQuestionBtn").click(function(){
	$(this).next(".dropdownQuestion").fadeToggle();
})	
$(".dropdownQuestionBtn, .dropdownQuestion").click(function(e) {
	e.stopPropagation();
});
$("html").click(function() {
	$(".dropdownQuestion").fadeOut();
})
$(".closePop").click(function() {
	$(this).parents(".quetionwidget").fadeOut();
})
$("#newQuestion").click(function() {
	$(".quetionwidget").fadeIn();
});
//Questionary section li
$(".parentUlQuestion").on("click", "li", function() {
	$(this).addClass("questionliks").siblings().removeClass("questionliks");
	var questionli = $(this).text();
	document.getElementById("valueType").innerHTML = questionli;
	$(".dropdownQuestion").fadeOut();
	if(questionli == "Checkboxes") {
		$("#checkBoxDiv").show();
		$("#radioboxDiv, #starRatingDiv, #dropdownDiv, #matrixRatingScale, #rankingDiv, #sliderDiv, #multipletextboxes, #contactinformtionDiv, #matrixDropdownm, #datetime").hide();
	}
  else if(questionli == "Multiple Choice") {
		$("#radioboxDiv").show();
		$("#starRatingDiv, #checkBoxDiv, #matrixRatingScale, #rankingDiv, #sliderDiv, #multipletextboxes, #contactinformtionDiv, #matrixDropdownm, #datetime").hide();
	}
	else if(questionli == "Dropdown") {
		$("#dropdownDiv").show();
		$("#radioboxDiv, #starRatingDiv, #checkBoxDiv, #matrixRatingScale, #rankingDiv, #sliderDiv, #multipletextboxes, #contactinformtionDiv, #matrixDropdownm, #datetime").hide();
	}
	else if(questionli == "Matrix / Rating Scale") {
		$("#matrixRatingScale").show();
		$("#radioboxDiv, #starRatingDiv, #checkBoxDiv, #dropdownDiv, #rankingDiv, #sliderDiv, #multipletextboxes, #contactinformtionDiv, #matrixDropdownm, #datetime").hide();
	}
	else if(questionli == "Ranking") {
		$("#rankingDiv").show();
		$("#radioboxDiv, #starRatingDiv, #checkBoxDiv, #dropdownDiv, #matrixRatingScale, #sliderDiv, #multipletextboxes, #contactinformtionDiv, #matrixDropdownm, #datetime").hide();
	}
	else if(questionli == "Slider") {
		$("#sliderDiv").show();
		$("#radioboxDiv, #starRatingDiv, #checkBoxDiv, #dropdownDiv, #matrixRatingScale, #rankingDiv, #multipletextboxes, #contactinformtionDiv, #matrixDropdownm, #datetime").hide();
	}
	else if(questionli == "Multiple Textboxes") {
		$("#multipletextboxes").show();
		$("#radioboxDiv, #starRatingDiv, #checkBoxDiv, #dropdownDiv, #matrixRatingScale, #rankingDiv, #sliderDiv, #contactinformtionDiv, #matrixDropdownm, #datetime").hide();
	}
	else if(questionli == "Contact Information") {
		$("#contactinformtionDiv").show();
		$("#radioboxDiv, #starRatingDiv, #checkBoxDiv, #dropdownDiv, #matrixRatingScale, #rankingDiv, #sliderDiv, #multipletextboxes, #matrixDropdownm, #datetime").hide();
	}
	else if(questionli == "Matrix of Dropdown Menus") {
		$("#matrixDropdownm").show();
		$("#radioboxDiv, #starRatingDiv, #checkBoxDiv, #dropdownDiv, #matrixRatingScale, #rankingDiv, #sliderDiv, #multipletextboxes, #contactinformtionDiv, #datetime").hide();
	}
	else if(questionli == "Date / Time") {
		$("#datetime").show();
$("#radioboxDiv, #starRatingDiv, #checkBoxDiv, #dropdownDiv, #matrixRatingScale, #rankingDiv, #sliderDiv, #multipletextboxes, #contactinformtionDiv, #matrixDropdownm").hide();
  }
  
  else if(questionli == "Star Rating") {
  $("#starRatingDiv").show();
  $("#radioboxDiv, #checkBoxDiv, #dropdownDiv, #matrixRatingScale, #rankingDiv, #sliderDiv, #multipletextboxes, #contactinformtionDiv, #matrixDropdownm, #datetime").hide();
  }
  
	else {
		$("#radioboxDiv, #starRatingDiv, #checkBoxDiv, #dropdownDiv, #matrixRatingScale, #rankingDiv, #sliderDiv, #multipletextboxes, #contactinformtionDiv, #matrixDropdownm, #datetime").hide();
	}
	
});
});
var slider = document.getElementById("myRange");
var output = document.getElementById("demo");
output.innerHTML = slider.value;
slider.oninput = function() {
  output.innerHTML = this.value;
}
</script>
<script>
	$("#starRatingSel").change(function() {
		$("#starRating").empty();
		$.each(new Array(+this.value), function(i) {
        $('<li />', {}).addClass("fa fa-star textWarning").appendTo('#starRating');
    })
    });
</script>
<jsp:include page="../masterFooter.jsp" /> 