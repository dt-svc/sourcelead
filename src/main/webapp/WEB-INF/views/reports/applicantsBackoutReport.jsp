
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" src="https://www.amcharts.com/lib/3/amcharts.js"></script>
<script type="text/javascript" src="https://www.amcharts.com/lib/3/serial.js"></script>

<script type="text/javascript" src="https://www.amcharts.com/lib/3/plugins/export/export.min.js"></script>
<link rel="stylesheet" href="https://www.amcharts.com/lib/3/plugins/export/export.css">

<script>
$(window).load(function() {
	getApplicantBackoutReportCount();
});
</script>

 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Applicant(s) Backout</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; Applicants(s) backOut </div>
   
    </section>
    <!-- hiring Process  -->
    <div class="content-dashboard clearfix">
      <div class="ReqRepHead clearfix">
        <div class="col-md-2 pull-left reqListBtn">
       		 <a href="${appPath}/applicantsBackoutReport" title="List View" id="GView" class="btn btn-default"><i class="fa fa-pie-chart"></i></a>
              <a href="${appPath}/applicantBackoutReportsList" title="List View" id="LView" class="btn btn-default"><i class="fa fa-list"></i></a>
        </div>
        <div class="col-md-6 ReqRepHead-chosen">
          <select class="chosen-select form-control" multiple data-placeholder="Select Job" name="jobIds" id="jobIds">
            <c:forEach var="option" items="${requirementsList}">
				<option value="${option.requirementId}">${option.jobTitle}</option>
			</c:forEach>
          </select>
        </div>
        
        <div class="col-md-2 dropdown-select positionRelative">
            <select class="form-control" name="dateSelection" id="dateSelection" onchange="getCustomDates();">
            <option value=''>Select Date</option>
            <option value='Last_7_days' selected >Last 7 days</option>
            <option value='Last_15_days'>Last 15 days</option>
            <option value='1_month'>1 month</option>
            <option value='3_months'>3 months</option>
            <option value='YTD'>YTD</option>
            <option value='Last_Year'>Last Year</option>
            <option value="custom">Custom</option>
          </select>
          <!-- Top Leave Takers Custom Date -->
          <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="customSearch" style="display: none; width: 346px;">
            <div class="pull-left">
              <div class="pull-left mr5" style="width: 140px">
                <input type="text" class="form-control calendar" value="" id="fromDate" name="fromDate"  placeholder="From Date"  readonly="readonly" >
                <p id="fromDateDiv" style="display: none; font-size: 12px;"><font color="red">From Date Required</font></p>
              </div>
              
              <div class="pull-left mr5" style="width: 140px">
                <input type="text" class="form-control calendar" value="" id="toDate" name="toDate"  placeholder="To Date" field-name="From Date" readonly="readonly" >
                <p id="toDateDiv" style="display: none; font-size: 12px;"><font color="red">To Date Required</font></p>
              </div>
              <button type="button" class="btn btn-primary btn-sm" onclick="getApplicantBackoutReportCount();"><i class="fa fa-search"></i></button>
            </div>
          </div>
          <!-- Top Leave Takers Custom Date --> 
          
        </div>
        <div class="col-md-1 noPad">
        <button type="button" class="btn btn-primary btn-sm	mt2" id="customize_btn" onclick = "getApplicantBackoutReportCount();">Run</button>
        </div>
      </div>
      <!-- Decline Reasons -->
      <div class="row" id="applicantBackoutGraph" style="display:block">
      <div class="row">
        <div class="col-md-12 mt25">
          <div class="clients_wrap_table dashboard-widget">
            <h4 class="pull-left">Backout Reasons</h4>
            <div id="backoutReason" style="width:100%; height: 250px; margin-bottom: 25px;" ></div>
          </div>
        </div>
      </div>
      </div>
      <!-- Decline Reasons -->
      
      <!-- Best recruiters  -->
    <div class="clearfix dashboard-widget mt col-md-12" id="applicantBackoutGraphList" style="display:none">
          <div class="clearfix">
          <h4 class="pull-left"></h4>
          </div>
          <div class="clearfix tableStyles mt datatable_filter">
          <div id="applicantBackoutListDiv"></div>
             
            
          </div>
        </div>
      
            <nav class="clearfix">
              <ul id="paging" class="pagination pull-right">
                <li><a href="#">Previous</a></li>
                <li class="active"><a href="#">1</a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">5</a></li>
                <li data-page="last"><a href="#">Next</a></li>
              </ul>
            </nav>
            
            
          </div>
  
  </section>
  
  <script>
  function getApplicantBackoutReportCount(){
	  
	  document.getElementById("applicantBackoutGraphList").style.display="none";
		document.getElementById("applicantBackoutGraph").style.display="block";
	  
	  
		var flag = true;
		  var jobIds = $("#jobIds").val();
		  if(jobIds == null){
			  jobIds = '';
		  }
		  var dateSelection = $("#dateSelection").val(); 
		  var fromDate = '';
		  var toDate = '';
		  if(dateSelection == 'custom'){
			   fromDate = $("#fromDate").val();
			   toDate = $("#toDate").val();
			  
			  if(fromDate == '' || toDate == ''){
				  flag = false;
				  if(fromDate == ''){
					  document.getElementById("fromDateDiv").style.display="block";
				  }else{
					  document.getElementById("fromDateDiv").style.display="none";
				  }
				  if(toDate == ''){
					  document.getElementById("toDateDiv").style.display="block";
				  }else{
					  document.getElementById("toDateDiv").style.display="none";
				  }
			  }else{
				  document.getElementById("fromDateDiv").style.display="none";
				  document.getElementById("toDateDiv").style.display="none";
			  }
			  
		  }
		  
		  if(flag){
			  
			  $.ajax({ 
				   	type: "GET",
				   	url: sourceleadUrl+"/getApplicantBackoutReport?${_csrf.parameterName}=${_csrf.token}&jobIds="+jobIds+"&dateSelection="+dateSelection+"&fromDate="+fromDate+"&toDate="+toDate,
				    success: function(json) {
		        		 loadApplicantBackoutChart(json);
		        		 
				},
		error: function(e){
		}
				 });
		}
			  
		  }
	  
  </script>
  
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
    });
    $('#priority').trigger('chosen:updated');
</script> 
  
<script>
///////////////////// Back To Top 
$(document).ready(function() {
    $(window).scroll(function() {
        if ($(this).scrollTop() > 200) {
            $('.backtotop').fadeIn('slow');
        } else {
            $('.backtotop').fadeOut('slow');
        }
    });
    $('.backtotop').click(function() {
        $("html, body").animate({scrollTop: 0}, 500);
        return false;
    });
});
</script> 

<script>
function getCustomDates(){
	
	 var dateSelection = $("#dateSelection").val(); 
	 if(dateSelection == 'custom'){
		 document.getElementById("customSearch").style.display="block";
		 $("#customize_btn").attr("disabled", "disabled");
		 
	 }else{
		 document.getElementById("customSearch").style.display="none";
		 $("#customize_btn").removeAttr("disabled");
		 $("#fromDate").val('');
		 $("#toDate").val('');
		 document.getElementById("fromDateDiv").style.display="none";
		  document.getElementById("toDateDiv").style.display="none";
	 }
}

</script>
  
  
  <script type="text/javascript">
  function loadApplicantBackoutChart(json){
	  var total = (json.currentCompany + json.compensation + json.betterOffer + json.coldFeet +json.personal + json.other);
	  var mainChar = [];
	  if(json.currentCompany>0){
		  mainChar.push({
				"category": "Current Company",
				"column-1": json.currentCompany,
				"color": "#9FA8DA",
				"count": json.currentCompany,
				"percentage": (((json.currentCompany/total)*100)).toFixed(2)
			});
	  }
	  if(json.compensation>0){
		  mainChar.push({
			  "category": "Compensation",
				"color": "#90CAF9",
				"column-1": json.compensation,
				"count": json.compensation,
				"percentage": (((json.compensation/total)*100)).toFixed(2)
			});
	  }
	  if(json.betterOffer>0){
		  mainChar.push( {
			  "category": "Better Offer",
				"column-1": json.betterOffer,
				"color": "#FFAB91",
				"count": json.betterOffer,
				"percentage": (((json.betterOffer/total)*100)).toFixed(2)
			} );
	  }
	  if(json.coldFeet>0){
		  mainChar.push( {
			  "category": "Cold Feet",
				"column-1": json.coldFeet,
				"color": "#A5D6A7",
				"count": json.coldFeet,
				"percentage": (((json.coldFeet/total)*100)).toFixed(2)
			} );
	  }
	  if(json.personal>0){
		  mainChar.push( {
			  "category": "Personal",
				"column-1": json.personal,
				"color": "#FFE0B2",
				"count": json.personal,
				"percentage": (((json.personal/total)*100)).toFixed(2)
			} );
	  }
	  if(json.other>0){
		  mainChar.push( {
			  "category": "Other",
				"column-1": json.other,
				"color": "#BCAAA4",
				"count": json.other,
				"percentage": (((json.other/total)*100)).toFixed(2)
			} );
	  }
	  
	  AmCharts.makeChart("backoutReason",
				{
		  "type": "serial",
			"categoryField": "category",
			"rotate": true,
			"marginBottom": 0,
			"marginLeft": 0,
			"marginTop": 10,
			"startDuration": 1,
			"categoryAxis": {
				"gridPosition": "start",
				"position": "left",
				"axisThickness": 0,
				"gridThickness": 0,
				"title": ""
			},
			"export": {

            	"enabled": true
            	},
			"trendLines": [],
			"graphs": [
				{
					"balloonText": "[[value]]%",
					"colorField": "color",
					"fillAlphas": 1,
					"id": "AmGraph-1",
					"labelText": "[[count]]<br>[[percentage]]%",
					"legendValueText": "",
					"lineColorField": "color",
					"title": "graph 1",
					"type": "column",
					"valueField": "column-1"
				}
			],
			"guides": [],
			"valueAxes": [
				{
					"axisFrequency": 0,
					"id": "ValueAxis-1",
					"totalText": "",
					"unit": "",
					"axisThickness": 0,
					"gridThickness": 0,
					"labelsEnabled": false,
					"title": ""
				}
			],
			"allLabels": [],
			"balloon": {},
			"titles": [],
			"dataProvider":  mainChar
				}
			);
	  $("#paging").empty();
  }
		</script>
		

<script>
  $( function() {
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatProjectSave = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatProjectSave = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatProjectSave = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatProjectSave = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatProjectSave = 'yy-dd-mm'; 
		  }
    
	  $('#fromDate').datepicker({
			dateFormat : finalDateFormatProjectSave,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '+1d');
				dt.setDate(dt.getDate());
				$("#toDate").datepicker("option", "minDate", dt);
				removeErrMsg();
			}
		});
	  
	  $('#toDate').datepicker({
			dateFormat : finalDateFormatProjectSave,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '-1d');
				dt.setDate(dt.getDate());
				$("#fromDate").datepicker("option", "maxDate", dt);
				removeErrMsgTo();
			}
		});
  } );
  function removeErrMsg(){
	  document.getElementById("fromDateDiv").style.display="none";
  }
  function removeErrMsgTo(){
	  document.getElementById("toDateDiv").style.display="none";
	  
  }
  </script>
 
 <script>
$(function() {
        $('#dateSelection').change(function(){
			var optval = $(this).val();
			if(optval === "custom") {
				$(".cusTomShow").show();
			}else{
				$(".cusTomShow").hide();
			}
        });
    });
</script>
 <script>
  function dobDatePicker() {
	  $('#dateSelection').datepicker({
	      dateFormat: 'yy-mm-dd',
	  });
	  
  }
</script>
 
 