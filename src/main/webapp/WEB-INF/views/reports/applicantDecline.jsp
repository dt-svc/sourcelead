<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- <script src="/resources/js/amcharts.js"></script>
<script src="/resources/js/serial.js"></script>
<script src="/resources/js/light.js"></script>
<script src="/resources/js/pie.js"></script> -->


<script type="text/javascript" src="https://www.amcharts.com/lib/3/amcharts.js"></script>
<script type="text/javascript" src="https://www.amcharts.com/lib/3/serial.js"></script>

<script type="text/javascript" src="https://www.amcharts.com/lib/3/plugins/export/export.min.js"></script>
<link rel="stylesheet" href="https://www.amcharts.com/lib/3/plugins/export/export.css">

$(window).load(function() {
	getDeclineApplicants();
});
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Applicant Decline</h2>
      </div>
      <div class="pull-right brud-crum">Home » Applicant Decline</div>
    </section>
    <!-- hiring Process  -->
    <div class="content-dashboard clearfix"> 
     
    <div class="ReqRepHead clearfix">
    	
    	<div class="col-md-2 pull-left reqListBtn">
        <a href="javascript:void(0)" title="Graphical View" id="graphicView" onClick="getDeclineApplicants();" class="btn btn-default"><i class="fa fa-pie-chart"></i></a>
        <a href="javascript:void(0)" onClick="getDeclineApplicantList();" title="List View" class="btn btn-default"><i class="fa fa-list"></i></a>
        </div>
    
		<div class="col-md-4">
          <select class="chosen-select form-control" multiple data-placeholder="Select Job" name="jobIds" id="jobIds">
            <c:forEach var="option" items="${requirementsList}">
				<option value="${option.requirementId}">${option.jobTitle}</option>
			</c:forEach>
          </select>
        </div>
	<div class="col-md-3 dropdown-select positionRelative">
          <select class="form-control" name="dateSelection" id="dateSelection" onchange="getCustomDates();">
            <option value=''>Select Date</option>
            <option value='Last_7_days'>Last 7 days</option>
            <option value='Last_15_days'>Last 15 days</option>
            <option value='1_month'>1 month</option>
            <option value='3_months'>3 months</option>
            <option value='YTD' selected>YTD</option>
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
              <button type="button" class="btn btn-primary btn-sm" onclick="getDeclineApplicants();"><i class="fa fa-search"></i></button>
            </div>
          </div>
          </div>
                  
          <div class="col-md-2">
        <button type="button" class="btn btn-primary btn-sm	mt2" id="customize_btn" onclick="getDeclineApplicants();">Run</button>
		</div>
          <div class="col-md-1">
          <div class="btn-group bootstrap_dropdownMenu dropdownPadding">
            <button type="button" class="btn btn-primary btn-sm" title="Actions"><i class="fa fa-retweet"></i></button>
            <button type="button" class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown"> <span class="caret"></span> </button>
            <ul class="dropdown-menu clearfix  dropdown-menu-right">
              <li role="Export" class="mt5 cursorPointer"><i class="fa fa-file-pdf-o paddingRight"></i>Export</li>
            </ul>
          </div>
        </div>
        </div>
        
        
     <!-- Decline Reasons -->
     <div class="row" id="applicantDeclineGraph" style="display:block">
        <div class="col-md-12 mt">
        <div class="clients_wrap_table dashboard-widget">
			<div id="applicantDecline" style="width: 100%; height: 250px; overflow: hidden; text-align: left;"><div class="amcharts-main-div" style="position: relative;"><div class="amcharts-chart-div" style="overflow: hidden; position: relative; text-align: left; width: 1078px; height: 250px; padding: 0px; touch-action: auto;"><svg version="1.1" style="position: absolute; width: 1078px; height: 250px; top: 0px; left: 0px;"><desc>JavaScript chart by amCharts 3.21.12</desc><g><path cs="100,100" d="M0.5,0.5 L1077.5,0.5 L1077.5,249.5 L0.5,249.5 Z" fill="#FFFFFF" stroke="#000000" fill-opacity="0" stroke-width="1" stroke-opacity="0"></path><path cs="100,100" d="M0.5,0.5 L951.5,0.5 L951.5,204.5 L0.5,204.5 L0.5,0.5 Z" fill="#FFFFFF" stroke="#000000" fill-opacity="0" stroke-width="1" stroke-opacity="0" transform="translate(106,10)"></path></g><g><g transform="translate(106,10)"><g><path cs="100,100" d="M0.5,17.5 L6.5,17.5" fill="none" stroke-width="1" stroke-opacity="1" stroke="#000000" transform="translate(-6,0)"></path><path cs="100,100" d="M0.5,0.5 L0.5,0.5 L951.5,0.5" fill="none" stroke-width="1" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M0.5,51.5 L6.5,51.5" fill="none" stroke-width="1" stroke-opacity="1" stroke="#000000" transform="translate(-6,0)"></path><path cs="100,100" d="M0.5,34.5 L0.5,34.5 L951.5,34.5" fill="none" stroke-width="1" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M0.5,85.5 L6.5,85.5" fill="none" stroke-width="1" stroke-opacity="1" stroke="#000000" transform="translate(-6,0)"></path><path cs="100,100" d="M0.5,68.5 L0.5,68.5 L951.5,68.5" fill="none" stroke-width="1" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M0.5,119.5 L6.5,119.5" fill="none" stroke-width="1" stroke-opacity="1" stroke="#000000" transform="translate(-6,0)"></path><path cs="100,100" d="M0.5,102.5 L0.5,102.5 L951.5,102.5" fill="none" stroke-width="1" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M0.5,153.5 L6.5,153.5" fill="none" stroke-width="1" stroke-opacity="1" stroke="#000000" transform="translate(-6,0)"></path><path cs="100,100" d="M0.5,136.5 L0.5,136.5 L951.5,136.5" fill="none" stroke-width="1" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M0.5,187.5 L6.5,187.5" fill="none" stroke-width="1" stroke-opacity="1" stroke="#000000" transform="translate(-6,0)"></path><path cs="100,100" d="M0.5,170.5 L0.5,170.5 L951.5,170.5" fill="none" stroke-width="1" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M0.5,204.5 L0.5,204.5 L951.5,204.5" fill="none" stroke-width="1" stroke-opacity="0.15" stroke="#000000"></path></g></g><g transform="translate(106,10)" visibility="visible"><g><path cs="100,100" d="M0.5,0.5 L0.5,5.5" fill="none" stroke-width="1" stroke-opacity="1" stroke="#000000" transform="translate(0,204)"></path><path cs="100,100" d="M0.5,204.5 L0.5,204.5 L0.5,0.5" fill="none" stroke-width="1" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M86.5,0.5 L86.5,5.5" fill="none" stroke-width="1" stroke-opacity="1" stroke="#000000" transform="translate(0,204)"></path><path cs="100,100" d="M86.5,204.5 L86.5,204.5 L86.5,0.5" fill="none" stroke-width="1" stroke-opacity="0.15" stroke="#000000"></path></g><g>
		<path cs="100,100" d="M173.5,0.5 L173.5,5.5" fill="none" stroke-width="1" stroke-opacity="1" stroke="#000000" transform="translate(0,204)"></path><path cs="100,100" d="M173.5,204.5 L173.5,204.5 L173.5,0.5" fill="none" stroke-width="1" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M259.5,0.5 L259.5,5.5" fill="none" stroke-width="1" stroke-opacity="1" stroke="#000000" transform="translate(0,204)"></path><path cs="100,100" d="M259.5,204.5 L259.5,204.5 L259.5,0.5" fill="none" stroke-width="1" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M346.5,0.5 L346.5,5.5" fill="none" stroke-width="1" stroke-opacity="1" stroke="#000000" transform="translate(0,204)"></path><path cs="100,100" d="M346.5,204.5 L346.5,204.5 L346.5,0.5" fill="none" stroke-width="1" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M432.5,0.5 L432.5,5.5" fill="none" stroke-width="1" stroke-opacity="1" stroke="#000000" transform="translate(0,204)"></path><path cs="100,100" d="M432.5,204.5 L432.5,204.5 L432.5,0.5" fill="none" stroke-width="1" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M519.5,0.5 L519.5,5.5" fill="none" stroke-width="1" stroke-opacity="1" stroke="#000000" transform="translate(0,204)"></path><path cs="100,100" d="M519.5,204.5 L519.5,204.5 L519.5,0.5" fill="none" stroke-width="1" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M605.5,0.5 L605.5,5.5" fill="none" stroke-width="1" stroke-opacity="1" stroke="#000000" transform="translate(0,204)"></path><path cs="100,100" d="M605.5,204.5 L605.5,204.5 L605.5,0.5" fill="none" stroke-width="1" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M692.5,0.5 L692.5,5.5" fill="none" stroke-width="1" stroke-opacity="1" stroke="#000000" transform="translate(0,204)"></path><path cs="100,100" d="M692.5,204.5 L692.5,204.5 L692.5,0.5" fill="none" stroke-width="1" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M778.5,0.5 L778.5,5.5" fill="none" stroke-width="1" stroke-opacity="1" stroke="#000000" transform="translate(0,204)"></path><path cs="100,100" d="M778.5,204.5 L778.5,204.5 L778.5,0.5" fill="none" stroke-width="1" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M865.5,0.5 L865.5,5.5" fill="none" stroke-width="1" stroke-opacity="1" stroke="#000000" transform="translate(0,204)"></path><path cs="100,100" d="M865.5,204.5 L865.5,204.5 L865.5,0.5" fill="none" stroke-width="1" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M951.5,0.5 L951.5,5.5" fill="none" stroke-width="1" stroke-opacity="1" stroke="#000000" transform="translate(0,204)"></path><path cs="100,100" d="M951.5,204.5 L951.5,204.5 L951.5,0.5" fill="none" stroke-width="1" stroke-opacity="0.15" stroke="#000000"></path></g></g></g><g></g><g></g><g></g><g></g><g><g transform="translate(106,10)"><g><g transform="translate(0,3)" visibility="visible" aria-label="graph 1 Skills 100"><path cs="100,100" d="M0.5,0.5 L0.5,27.5 L865.5,27.5 L865.5,0.5 L0.5,0.5 Z" fill="#9FA8DA" stroke="#9FA8DA" fill-opacity="1" stroke-width="1" stroke-opacity="1"></path></g><g transform="translate(0,37)" visibility="visible" aria-label="graph 1 Over Qualified 80"><path cs="100,100" d="M0.5,0.5 L0.5,27.5 L692.5,27.5 L692.5,0.5 L0.5,0.5 Z" fill="#90CAF9" stroke="#90CAF9" fill-opacity="1" stroke-width="1" stroke-opacity="1"></path></g><g transform="translate(0,71)" visibility="visible" aria-label="graph 1 Bad Fit 50"><path cs="100,100" d="M0.5,0.5 L0.5,27.5 L432.5,27.5 L432.5,0.5 L0.5,0.5 Z" fill="#FFAB91" stroke="#FFAB91" fill-opacity="1" stroke-width="1" stroke-opacity="1"></path></g><g transform="translate(0,105)" visibility="visible" aria-label="graph 1 Interview 30"><path cs="100,100" d="M0.5,0.5 L0.5,27.5 L259.5,27.5 L259.5,0.5 L0.5,0.5 Z" fill="#A5D6A7" stroke="#A5D6A7" fill-opacity="1" stroke-width="1" stroke-opacity="1"></path></g><g transform="translate(0,139)" visibility="visible" aria-label="graph 1 Salary 20"><path cs="100,100" d="M0.5,0.5 L0.5,27.5 L173.5,27.5 L173.5,0.5 L0.5,0.5 Z" fill="#FFE0B2" stroke="#FFE0B2" fill-opacity="1" stroke-width="1" stroke-opacity="1"></path></g><g transform="translate(0,173)" visibility="visible" aria-label="graph 1 Other 10"><path cs="100,100" d="M0.5,0.5 L0.5,27.5 L86.5,27.5 L86.5,0.5 L0.5,0.5 Z" fill="#BCAAA4" stroke="#BCAAA4" fill-opacity="1" stroke-width="1" stroke-opacity="1"></path></g></g></g></g><g></g><g><path cs="100,100" d="M0.5,204.5 L0.5,0.5 L0.5,0.5" fill="none" stroke-width="1" stroke-opacity="0.3" stroke="#000000" transform="translate(106,10)"></path><g><path cs="100,100" d="M0.5,0.5 L0.5,204.5" fill="none" stroke-width="1" stroke-opacity="1" stroke="#000000" transform="translate(106,10)"></path></g><g><path cs="100,100" d="M0.5,0.5 L951.5,0.5" fill="none" stroke-width="1" stroke-opacity="1" stroke="#000000" transform="translate(106,214)" visibility="visible"></path></g></g><g></g><g></g><g><g transform="translate(106,10)"></g></g><g><g></g></g><g><g transform="translate(106,10)" visibility="visible"><text y="6" fill="#000000" font-family="Verdana" font-size="11px" opacity="1" text-anchor="end" transform="translate(-12,16)"><tspan y="6" x="0">Skills</tspan></text><text y="6" fill="#000000" font-family="Verdana" font-size="11px" opacity="1" text-anchor="end" transform="translate(-12,50)"><tspan y="6" x="0">Over Qualified</tspan></text><text y="6" fill="#000000" font-family="Verdana" font-size="11px" opacity="1" text-anchor="end" transform="translate(-12,84)"><tspan y="6" x="0">Bad Fit</tspan></text><text y="6" fill="#000000" font-family="Verdana" font-size="11px" opacity="1" text-anchor="end" transform="translate(-12,118)"><tspan y="6" x="0">Interview</tspan></text><text y="6" fill="#000000" font-family="Verdana" font-size="11px" opacity="1" text-anchor="end" transform="translate(-12,152)"><tspan y="6" x="0">Salary</tspan></text><text y="6" fill="#000000" font-family="Verdana" font-size="11px" opacity="1" text-anchor="end" transform="translate(-12,186)"><tspan y="6" x="0">Other</tspan></text></g><g transform="translate(106,10)" visibility="visible"><text y="6" fill="#000000" font-family="Verdana" font-size="11px" opacity="1" text-anchor="middle" transform="translate(0,221.5)"><tspan y="6" x="0">0</tspan></text><text y="6" fill="#000000" font-family="Verdana" font-size="11px" opacity="1" text-anchor="middle" transform="translate(86,221.5)"><tspan y="6" x="0">10</tspan></text><text y="6" fill="#000000" font-family="Verdana" font-size="11px" opacity="1" text-anchor="middle" transform="translate(173,221.5)"><tspan y="6" x="0">20</tspan></text><text y="6" fill="#000000" font-family="Verdana" font-size="11px" opacity="1" text-anchor="middle" transform="translate(259,221.5)"><tspan y="6" x="0">30</tspan></text><text y="6" fill="#000000" font-family="Verdana" font-size="11px" opacity="1" text-anchor="middle" transform="translate(346,221.5)"><tspan y="6" x="0">40</tspan></text><text y="6" fill="#000000" font-family="Verdana" font-size="11px" opacity="1" text-anchor="middle" transform="translate(432,221.5)"><tspan y="6" x="0">50</tspan></text><text y="6" fill="#000000" font-family="Verdana" font-size="11px" opacity="1" text-anchor="middle" transform="translate(519,221.5)"><tspan y="6" x="0">60</tspan></text><text y="6" fill="#000000" font-family="Verdana" font-size="11px" opacity="1" text-anchor="middle" transform="translate(605,221.5)"><tspan y="6" x="0">70</tspan></text><text y="6" fill="#000000" font-family="Verdana" font-size="11px" opacity="1" text-anchor="middle" transform="translate(692,221.5)"><tspan y="6" x="0">80</tspan></text><text y="6" fill="#000000" font-family="Verdana" font-size="11px" opacity="1" text-anchor="middle" transform="translate(778,221.5)"><tspan y="6" x="0">90</tspan></text><text y="6" fill="#000000" font-family="Verdana" font-size="11px" opacity="1" text-anchor="middle" transform="translate(865,221.5)"><tspan y="6" x="0">100</tspan></text><text y="6" fill="#000000" font-family="Verdana" font-size="11px" opacity="1" text-anchor="middle" transform="translate(951,221.5)"><tspan y="6" x="0">110</tspan></text></g></g><g></g><g transform="translate(106,10)"></g><g></g><g></g></svg><a href="http://www.amcharts.com" title="JavaScript charts" style="position: absolute; text-decoration: none; color: rgb(0, 0, 0); font-family: Verdana; font-size: 11px; opacity: 0.7; display: block; left: 111px; top: 15px;">JS chart by amCharts</a></div></div></div>
        </div>
      </div>
      </div>
      </
      <!-- Decline Reasons -->
     
    <!-- Best recruiters  -->
    <div class="clearfix dashboard-widget mt col-md-12" id="applicantDeclineGraphList" style="display:none">
          <div class="clearfix">
          <h4 class="pull-left"></h4>
          </div>
          <div class="clearfix tableStyles mt datatable_filter">
          <div id="applicantDeclineListDiv"></div>
             
            
          </div>
        </div>
    <!-- Best recruiters Ends  --> 
    </div>
      
  </section>
  
<script type="text/javascript">

function getDeclineApplicants(){
	
	document.getElementById("applicantDeclineGraphList").style.display="none";
	document.getElementById("applicantDeclineGraph").style.display="block";
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
			   	url: sourceleadUrl+"/getDeclinedApplicantsCount?${_csrf.parameterName}=${_csrf.token}&jobIds="+jobIds+"&dateSelection="+dateSelection+"&fromDate="+fromDate+"&toDate="+toDate,
			    success: function(data) {
			    	getAplicantDeclineAmChart(data);
			    	
			    }
		        	 
			 });
	  }
	  
}
	  
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

<script>
function getAplicantDeclineAmChart(data){
	 var mainChar = [];
	 if(data.skillsCount>0){
		 mainChar.push( {
			    "category": "Skills",
				"column-1": data.skillsCount,
				"color": "#9FA8DA"
		    } );
		 }
	 if(data.unsuitableCount>0){
		 mainChar.push( {
			    "category": "Over Qualified",
				"color": "#90CAF9",
				"column-1": data.unsuitableCount
		    } );
		 }
	 if(data.badfitCount>0){
		 mainChar.push( {
			 "category": "Bad Fit",
				"column-1": data.badfitCount,
				"color": "#FFAB91"
		    } );
		 }
	 if(data.interviewCount>0){
		 mainChar.push( {
			    "category": "Interview",
				"column-1": data.interviewCount,
				"color": "#A5D6A7"
		    } );
		 }
	 if(data.salaryCount>0){
		 mainChar.push( {
			    "category": "Salary",
				"column-1": data.salaryCount,
				"color": "#FFE0B2"
		    } );
		 }
	 if(data.otherCount>0){
		 mainChar.push( {
				"category": "Other",
				"column-1": data.otherCount,
				"color": "#BCAAA4"
		    } );
		 }
	
	AmCharts.makeChart("applicantDecline",
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
						"title": ""
					}
				],
				"allLabels": [],
				"balloon": {},
				"titles": [],
				"dataProvider": mainChar
			}
		);
}

</script>  
  
  <script>
 $(document).ready(function(){
$(function() {
        $('#dateSelection').change(function(){
			var optval = $(this).val();
			if(optval === "custom") {
				$(".customPop").show();
			}else{
				$(".customPop").hide();
			}
        });
    });
  }); 
    
</script>

<script type="text/javascript">

function getDeclineApplicantList(){
	
	$("#applicantDeclineGraphList").show();
	$("#applicantDeclineGraph").hide();
	$("#applicantDeclineList").show();
	var jobIds = $("#jobIds").val();
	  if(jobIds == null){
		  jobIds = '';
	  }
	  var dateSelection = $("#dateSelection").val(); 
	  $.ajax({ 
		   	type: "GET",
		   	url: sourceleadUrl+"/getDeclinedApplicantsList?${_csrf.parameterName}=${_csrf.token}&jobIds="+jobIds+"&dateSelection="+dateSelection,
		    success: function(data) {
		    	$("#applicantDeclineList").empty();
           	 var result = "";
             result  = result + '    <table class="table table-bordered" id="applicantDeclinePagenationId">     ';
			 result  = result + '     <thead>                                 ';
			 result  = result + '       <tr>                                  ';
			 result  = result + '         <th>Jobs</th>                       ';
			 result  = result + '         <th>Skills</th>                     ';
			 result  = result + '         <th>Over Qualified</th>             ';
			 result  = result + '         <th>Bad Fit</th>                    ';
			 result  = result + '         <th>Interview</th>                  ';
			 result  = result + '         <th>Salary</th>                     ';
			 result  = result + '         <th>Other</th>                      ';
			 result  = result + '         <th>Total</th>                      ';
			 result  = result + '       </tr>                                 ';
			 result  = result + '       </thead>                              ';
			 result  = result + '       <tbody>                               ';
			 for (var i = 0; i < data.length; i++) { 
				 var report = data[i];
				 result  = result + '       <tr>                                  ';
				 result  = result + '		  <td><div>'+report.jobName+'</div></td>  ';
				 result  = result + '         <td><div>'+report.skillsCount+'</div></td>               ';
				 result  = result + '         <td><div>'+report.unsuitableCount+'</div></td>               ';
				 result  = result + '         <td><div>'+report.badfitCount+'</div></td>               ';
				 result  = result + '         <td><div>'+report.interviewCount+'</div></td>               ';
				 result  = result + '         <td><div>'+report.salaryCount+'</div></td>               ';
				 result  = result + '         <td><div>'+report.otherCount+'</div></td>               ';
				 result  = result + '         <td><div>'+report.totalCount+'</div></td>               ';
				 //result  = result + '         <td><div>31</div></td>              ';
				 result  = result + '         </tr>                               ';
			 }
			
			 
			 result  = result + '     </tbody>                                ';
			 result  = result + '   </table>                                  ';
			 
			 $("#applicantDeclineListDiv").empty();
			 $("#applicantDeclineListDiv").append(result);
			 applicantDeclineListPagination();
		    	
		    }
		 });
}
function applicantDeclineListPagination(){
	$('#applicantDeclinePagenationId').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
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

  

<jsp:include page="../masterFooter.jsp" />