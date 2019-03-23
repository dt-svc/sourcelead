<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<div class="wraper"> 
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Add Questionnaire</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; 
      <a href="${SourceLeadWeb_sourceleadUrl}/questionnaireList">Questionnaire </a>&raquo; 
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
		  <div class="col-md-12 mb">
          <div class="quetionwidget" style="display: none;">
            <div class="row">
				<span class="closePop"></span>
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
			  <div class="row mt">
			  	<div class="col-md-6">
					<div class="row">
						<div class="col-md-6">
							<div class="row">
							<label class="col-md-4 mt10">Points</label>
							<div class="col-md-8">
							<input type="text" class="form-control" placeholder="Points">
							</div>
								</div>
						</div>  
						<div class="col-md-6">
							<div class="row">
							<label class="col-md-4 mt10">Attempt</label>
							<div class="col-md-8">
							<input type="text" class="form-control" placeholder="Points">
							</div>
								</div>
						</div>  
					</div>
				</div>
			  <div class="col-md-6">
					<div class="row">
						<div class="col-md-12">
							<div class="row">
							<label class="col-md-4 mt10">Time Limit</label>
							<div class="col-md-4">
							<input type="text" class="form-control" placeholder="Minits">
							</div>
							<div class="col-md-4">
							<input type="text" class="form-control" placeholder="Seconds">
							</div>
								</div>
						</div>  
						  
					</div>
				</div>
			  </div>
            
			  <div class="clearfix"></div>
          <div class="clearfix">
              <div class="clearfix col-md-12 mb">
                <div class="pull-right">
                  <input value="Save" class="btn btn-primary" type="button">
                  <input value="Cancel" class="btn btn-default ml" type="button" onclick = "cancelAddQuestionnaire();">
                </div>
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
		$("#dropdownDiv, #matrixRatingScale, #rankingDiv, #sliderDiv, #multipletextboxes, #contactinformtionDiv, #matrixDropdownm, #datetime").hide();
	}
	else if(questionli == "Dropdown") {
		$("#dropdownDiv").show();
		$("#checkBoxDiv, #matrixRatingScale, #rankingDiv, #sliderDiv, #multipletextboxes, #contactinformtionDiv, #matrixDropdownm, #datetime").hide();
	}
	else if(questionli == "Matrix / Rating Scale") {
		$("#matrixRatingScale").show();
		$("#checkBoxDiv, #dropdownDiv, #rankingDiv, #sliderDiv, #multipletextboxes, #contactinformtionDiv, #matrixDropdownm, #datetime").hide();
	}
	else if(questionli == "Ranking") {
		$("#rankingDiv").show();
		$("#checkBoxDiv, #dropdownDiv, #matrixRatingScale, #sliderDiv, #multipletextboxes, #contactinformtionDiv, #matrixDropdownm, #datetime").hide();
	}
	else if(questionli == "Slider") {
		$("#sliderDiv").show();
		$("#checkBoxDiv, #dropdownDiv, #matrixRatingScale, #rankingDiv, #multipletextboxes, #contactinformtionDiv, #matrixDropdownm, #datetime").hide();
	}
	else if(questionli == "Multiple Textboxes") {
		$("#multipletextboxes").show();
		$("#checkBoxDiv, #dropdownDiv, #matrixRatingScale, #rankingDiv, #sliderDiv, #contactinformtionDiv, #matrixDropdownm, #datetime").hide();
	}
	else if(questionli == "Contact Information") {
		$("#contactinformtionDiv").show();
		$("#checkBoxDiv, #dropdownDiv, #matrixRatingScale, #rankingDiv, #sliderDiv, #multipletextboxes, #matrixDropdownm, #datetime").hide();
	}
	else if(questionli == "Matrix of Dropdown Menus") {
		$("#matrixDropdownm").show();
		$("#checkBoxDiv, #dropdownDiv, #matrixRatingScale, #rankingDiv, #sliderDiv, #multipletextboxes, #contactinformtionDiv, #datetime").hide();
	}
	else if(questionli == "Date / Time") {
		$("#datetime").show();
$("#checkBoxDiv, #dropdownDiv, #matrixRatingScale, #rankingDiv, #sliderDiv, #multipletextboxes, #contactinformtionDiv, #matrixDropdownm").hide();
	}
	else {
		$("#checkBoxDiv, #dropdownDiv, #matrixRatingScale, #rankingDiv, #sliderDiv, #multipletextboxes, #contactinformtionDiv, #matrixDropdownm, #datetime").hide();
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
<script>
function cancelAddQuestionnaire(){
	window.location.replace("${SourceLeadWeb_sourceleadUrl}/questionnaireList");
}
</script>
<jsp:include page="../masterFooter.jsp" />