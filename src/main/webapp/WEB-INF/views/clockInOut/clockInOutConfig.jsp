<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

  
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="../js/moment.min.js"></script>
<script src="../js/bootstrap-datetimepicker.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="../js/masterHeaderAction.js"></script>
<script src="../js/bootstrap-select.min.js"></script>
<script src="../js/chosen.jquery.min.js"></script> -->


<script>
	$(document).ready(function() {
		getClockInOutConfigData();
	});
</script>


<!-- Content Area --> 
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Clock in & Clock out</h2>
      </div>
          <div class="pull-right brud-crum"><a href="${appPath}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${appPath}/settings">Settings</a> &raquo;Clock In & Clock Out</div>

    </section>
    
    <form name="clock_InOut_form" id="clock_InOut_form">
    <input type="hidden" id="configId" name="clockInOutId"/>
    <div class="content-area clearfix timesheet_out pad20">
		<div id="clockInOutLoading"></div>
        <div class="clockCta">
          <p>Track employee location when they clock in?</p> 
          <div class="clockRadio">
            <input id="radio-1" name="isTrackEmpLocation" type="radio" value="YES">
            <label for="radio-1" class="radio-label">Yes</label>
          </div>
          <div class="clockRadio">
            <input id="radio-2" name="isTrackEmpLocation" type="radio" value="NO" checked>
            <label for="radio-2" class="radio-label">No</label>
          </div>          
        </div>

        <div class="clockCta">
          <p>Track employee clock in hours based on the tasks assigned?</p> 
          <div class="clockRadio">
            <input id="radio-4" name="isTrackTaskAssigned" type="radio" value="YES">
            <label for="radio-4" class="radio-label" id="clSub">Yes</label>
          </div>
          <div class="clockRadio">
            <input id="radio-3" name="isTrackTaskAssigned" type="radio" value="NO" checked>
            <label for="radio-3" class="radio-label" id="hideClSub">No</label>
          </div> 
          <div class="clockSub">
              <div class="clockcheck"><input type="checkbox" id="a" name="isJobChecked" value="YES"> <label for="a">Jobs</label></div>
              <div class="clockcheck"><input type="checkbox" id="b" name="isProjectTasksChecked" value="YES"> <label for="b">Project Tasks</label></div>
              <div class="clockcheck"><input type="checkbox" id="c" name="isGeneralTaskChecked" value="YES"> <label for="c">General Tasks</label></div>
<!--               <div class="clockcheck"><input type="checkbox" id="d" name="isGoalChecked" value="YES"> <label for="d">Goals</label></div> -->
<!--               <div class="clockcheck"><input type="checkbox" id="e" name="isOtherChecked" value="YES"> <label for="e">Other</label></div> -->
          </div>
        </div>

        <div class="clockCta">
          <p>Employees need to comment when clock in</p> 
          <div class="clockRadio">
            <input id="radio-a" name="isClockInComment" type="radio" value="YES">
            <label for="radio-a" class="radio-label">Yes</label>
          </div>
          <div class="clockRadio">
            <input id="radio-b" name="isClockInComment" value="NO" type="radio" checked>
            <label for="radio-b" class="radio-label">No</label>
          </div> 
        </div>

        <div class="clockCta">
          <p>Track employee breaks</p> 
          <div class="clockRadio">
            <input id="radio-c" name="isBreak" type="radio" value="YES">
            <label for="radio-c" class="radio-label">Yes</label>
          </div>
          <div class="clockRadio">
            <input id="radio-d" name="isBreak" type="radio" value="NO" checked>
            <label for="radio-d" class="radio-label">No</label>
          </div> 
        </div>

        <div class="clockCta">
          <p>Automatic clock out when shift hours end</p> 
          <div class="clockRadio">
            <input id="radio-e" name="isAutomaticCheckOut" type="radio" value="YES">
            <label for="radio-e" class="radio-label">Yes</label>
          </div>
          <div class="clockRadio">
            <input id="radio-f" name="isAutomaticCheckOut" type="radio" value="NO" checked>
            <label for="radio-f" class="radio-label">No</label>
          </div> 
        </div>  
        
        <div class="clockCta">
          <p>Fill Timesheet Automatically When clock out</p> 
          <div class="clockRadio">
            <input id="radio-g" name="isTimesheetFill" type="radio" value="YES">
            <label for="radio-g" class="radio-label">Yes</label>
          </div>
          <div class="clockRadio">
            <input id="radio-h" name="isTimesheetFill" type="radio" value="NO" checked>
            <label for="radio-h" class="radio-label">No</label>
          </div> 
        </div>       

        <div class="">
          <a class="timeEnt" onclick="saveClockInOutConfigure();">Save</a>
          <a class="timeEnt timeEntCan" onclick="javascript:window.location.href ='${SourceLeadWeb_sourceleadUrl}/settings'">Cancel</a>
        </div>

    </div>
    
    </form>
  </section>
  
  
  <script type="text/javascript">
	var config = {
	'.chosen-select'           : {},
	'.chosen-select-deselect'  : {allow_single_deselect:true},
	'.chosen-select-no-single' : {disable_search_threshold:10},
	'.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
	'.chosen-select-width'     : {width:"95%"}
	}

	for (var selector in config) {
	$(selector).chosen(config[selector]);
	}
	$('.chosen-select').each(function() {
	var $this = $(this);
	$this.next().css({'width': '100%'});
	})
	$(document).ready(function(){
	$("#cd").click(function(){
	$(".clientDetails").toggleClass('hidden', 500);
	});
	});
</script> 

<script type="text/javascript">

// clock sub
$("#clSub").click(function(){
  $(".clockSub").show();
});

$("#hideClSub").click(function(){
  $(".clockSub").hide();
});

$(".clockAni").click(function(){
  $(this).css("background-image","url(../images/clock-ani.gif)");
  $(".clockAniAct").show();
});

$("#clPop").click(function() {
	$(".mask, #clockPop").fadeIn();
});
$(".popClose").click(function() {
	$(".mask, #clockPop").fadeOut();
});
  
 </script>
 
 <script>
 function saveClockInOutConfigure(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var formData = getFormData("clock_InOut_form");
	 $("#clockInOutLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
     $.ajax({
 	      type: "POST",
 	      data: formData,
       	  url:sourceleadUrl+"/saveClockInOutConfigure",
         	success: function(json) {
         		getClockInOutConfigData();
         		$("#clockInOutLoading").removeAttr('style');
	        },  
     });
	 
 }
 
 </script>
 
 <script>
 function getClockInOutConfigData(){
	 var sourceleadUrl ='${sourceleadUrl}';
     $.ajax({
 	      type: "GET",
       	  url:sourceleadUrl+"/getClockInOutConfigData",
         	success: function(data) {
         		$("#configId").val(data.id);
         		displayAlreadyConfiguredData(data);
	        },  
     });
 }
 </script>
 
 <script>
 function displayAlreadyConfiguredData(data){
	 if(data.isTrackEmpLocation == true){
		 $("#radio-1").prop("checked", true);
	 }else{
		 $("#radio-2").prop("checked", true);
	 }
	 
	 if(data.isTrackTaskAssigned == true){
		 
		 if(data.isJobChecked == true){
			 $("#a").prop("checked", true);
		 }else{
			 $("#a").prop("checked", false);
		 }
		 
		 if(data.isProjectTasksChecked == true){
			 $("#b").prop("checked", true);
		 }else{
			 $("#b").prop("checked", false);
		 }
		 
		 if(data.isGeneralTaskChecked == true){
			 $("#c").prop("checked", true);
		 }else{
			 $("#c").prop("checked", false);
		 }
		 
		 if(data.isGoalChecked == true){
			 $("#d").prop("checked", true);
		 }else{
			 $("#d").prop("checked", false);
		 }
		 
		 if(data.isOtherChecked == true){
			 $("#e").prop("checked", true);
		 }else{
			 $("#e").prop("checked", false);
		 }
		 
		 $("#radio-4").prop("checked", true);
		 $(".clockSub").show();
	 }else{
		 $("#radio-3").prop("checked", true);
		 $(".clockSub").hide();
	 }
	 
	 if(data.isClockInComment == true){
		 $("#radio-a").prop("checked", true);
	 }else{
		 $("#radio-b").prop("checked", true);
	 }
	 
	 if(data.isBreak == true){
		 $("#radio-c").prop("checked", true);
	 }else{
		 $("#radio-d").prop("checked", true);
	 }
	 
	 if(data.isAutomaticCheckOut == true){
		 $("#radio-e").prop("checked", true);
	 }else{
		 $("#radio-f").prop("checked", true);
	 }
	 
	 if(data.isTimesheetFill == true){
		 $("#radio-g").prop("checked", true);
	 }else{
		 $("#radio-h").prop("checked", true);
	 }
 
 }

 </script>


<jsp:include page="../masterFooter.jsp" />

