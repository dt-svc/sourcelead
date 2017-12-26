<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <script src="/resources/js/pie.js"></script> 
 <script src="/resources/js/amcharts.js"></script> 
 <script src="/resources/js/serial.js"></script> 
 <script src="/resources/reports/export.min.js"></script> 
 <script src="/resources/js/themes/light.js"></script> 
 
<!--  <script type="text/javascript" src="https://www.amcharts.com/lib/3/plugins/export/export.min.js"></script> -->
<!-- 		<link rel="stylesheet" href="https://www.amcharts.com/lib/3/plugins/export/export.css"> -->
<script>
$(document).ready(function() {
	getJobPipelineReports();
});	
</script>

 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Jobs Pipeline</h2>
      </div>
      <div class="pull-right brud-crum">Home » Jobs Pipeline</div>
    </section>
    <!-- hiring Process  -->
    <div class="clearfix">
      <div class="ReqRepHead clearfix">
    		      <div class="col-md-2 pull-left reqListBtn">
          <a href="${appPath}/jobPipelineReports" title="List View" id="GView" class="btn btn-default"><i class="fa fa-pie-chart"></i></a>
          <a href="${appPath}/jobPipelineReportsList" title="List View" id="LView" class="btn btn-default"><i class="fa fa-list"></i></a>
    		      </div>
     		<!--<a href="jobsPipline-list.html" class="btn btn-primary">List</a>-->
       
        <div class="col-md-4">
    <div class="dropdown-scroll">
		<select class="chosen-select form-control" multiple data-placeholder="Select Job" name="jobIds" id="jobIds">
            <c:forEach var="option" items="${requirementsList}">
				<option value="${option.requirementId}">${option.jobTitle}</option>
			</c:forEach>
          </select>
	</div>
        </div>
        <div class="col-md-3 dropdown-select">
        <div class="dropdown-select positionRelative">
        <select class="form-control" name="dateSelection" id="dateSelection">
            <option value=''>Select Date</option>
            <option value='Last_7_days' selected>Last 7 days</option>
            <option value='Last_15_days'>Last 15 days</option>
            <option value='1_month'>1 month</option>
            <option value='3_months'>3 months</option>
            <option value='YTD'>YTD</option>
            <option value='Last_Year'>Last Year</option>
            <option value="custom">Custom</option>
          </select>
        <!-- Top Leave Takers Custom Date -->
    <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="custom-select" style="display: none; width: 346px;">
    <div class="pull-left">
    <div class="pull-left mr5" style="width: 140px">
    <input value="" name="Date" id="datepicker4" placeholder="From" class="form-control calendar input-sm" type="text">
    </div>
    <div class="pull-left mr5" style="width: 140px">
    <input value="" name="Date" id="datepicker2" placeholder="To" class="form-control calendar input-sm" type="text">
    </div>
    <button type="button" class="btn btn-primary btn-sm"><i class="fa fa-search"></i></button>
    </div>
    </div>
<!-- Top Leave Takers Custom Date --> 
      </div>

        </div>
		        <div class="col-md-2">
        <button type="button" class="btn btn-primary btn-sm	mt2">Customize</button>
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
    </div>
    
    <div class="dashboard-widget clearfix mt"> 
      <!-- Jobs Pipline -->
      <div id="jobspipline" style="width: 100%; height: 350px; overflow: hidden; text-align: left;"><div class="amcharts-main-div" style="position: relative;"><div class="amcharts-chart-div" style="overflow: hidden; position: relative; text-align: left; width: 1078px; height: 350px; padding: 0px; touch-action: auto;"><svg version="1.1" style="position: absolute; width: 1078px; height: 350px; top: 0px; left: 0px;"><desc>JavaScript chart by amCharts 3.21.12</desc><g><path cs="100,100" d="M0.5,0.5 L1077.5,0.5 L1077.5,349.5 L0.5,349.5 Z" fill="#FFFFFF" stroke="#000000" fill-opacity="0" stroke-width="1" stroke-opacity="0"></path><path cs="100,100" d="M0.5,0.5 L932.5,0.5 L932.5,306.5 L0.5,306.5 L0.5,0.5 Z" fill="#FFFFFF" stroke="#000000" fill-opacity="0" stroke-width="1" stroke-opacity="0" transform="translate(115,33)"></path></g><g><g transform="translate(115,33)"><g><path cs="100,100" d="M0.5,27.5 L11.5,27.5" fill="none" stroke-width="0" stroke-opacity="1" stroke="#000000" transform="translate(-10,0)"></path><path cs="100,100" d="M0.5,1.5 L0.5,1.5 L932.5,1.5" fill="none" stroke-width="0" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M0.5,78.5 L11.5,78.5" fill="none" stroke-width="0" stroke-opacity="1" stroke="#000000" transform="translate(-10,0)"></path><path cs="100,100" d="M0.5,52.5 L0.5,52.5 L932.5,52.5" fill="none" stroke-width="0" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M0.5,129.5 L11.5,129.5" fill="none" stroke-width="0" stroke-opacity="1" stroke="#000000" transform="translate(-10,0)"></path><path cs="100,100" d="M0.5,103.5 L0.5,103.5 L932.5,103.5" fill="none" stroke-width="0" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M0.5,180.5 L11.5,180.5" fill="none" stroke-width="0" stroke-opacity="1" stroke="#000000" transform="translate(-10,0)"></path><path cs="100,100" d="M0.5,154.5 L0.5,154.5 L932.5,154.5" fill="none" stroke-width="0" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M0.5,231.5 L11.5,231.5" fill="none" stroke-width="0" stroke-opacity="1" stroke="#000000" transform="translate(-10,0)"></path><path cs="100,100" d="M0.5,205.5 L0.5,205.5 L932.5,205.5" fill="none" stroke-width="0" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M0.5,282.5 L11.5,282.5" fill="none" stroke-width="0" stroke-opacity="1" stroke="#000000" transform="translate(-10,0)"></path><path cs="100,100" d="M0.5,256.5 L0.5,256.5 L932.5,256.5" fill="none" stroke-width="0" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M0.5,307.5 L0.5,307.5 L932.5,307.5" fill="none" stroke-width="0" stroke-opacity="0.15" stroke="#000000"></path></g></g><g transform="translate(115,33)" visibility="visible"><g><path cs="100,100" d="M0.5,0.5 L0.5,5.5" fill="none" stroke-width="0" stroke-opacity="1" stroke="#000000" transform="translate(0,305)"></path><path cs="100,100" d="M0.5,306.5 L0.5,306.5 L0.5,0.5" fill="none" stroke-width="0" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M85.5,0.5 L85.5,5.5" fill="none" stroke-width="0" stroke-opacity="1" stroke="#000000" transform="translate(0,305)"></path><path cs="100,100" d="M85.5,306.5 L85.5,306.5 L85.5,0.5" fill="none" stroke-width="0" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M169.5,0.5 L169.5,5.5" fill="none" stroke-width="0" stroke-opacity="1" stroke="#000000" transform="translate(0,305)"></path><path cs="100,100" d="M169.5,306.5 L169.5,306.5 L169.5,0.5" fill="none" stroke-width="0" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M254.5,0.5 L254.5,5.5" fill="none" stroke-width="0" stroke-opacity="1" stroke="#000000" transform="translate(0,305)"></path><path cs="100,100" d="M254.5,306.5 L254.5,306.5 L254.5,0.5" fill="none" stroke-width="0" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M339.5,0.5 L339.5,5.5" fill="none" stroke-width="0" stroke-opacity="1" stroke="#000000" transform="translate(0,305)"></path><path cs="100,100" d="M339.5,306.5 L339.5,306.5 L339.5,0.5" fill="none" stroke-width="0" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M424.5,0.5 L424.5,5.5" fill="none" stroke-width="0" stroke-opacity="1" stroke="#000000" transform="translate(0,305)"></path><path cs="100,100" d="M424.5,306.5 L424.5,306.5 L424.5,0.5" fill="none" stroke-width="0" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M508.5,0.5 L508.5,5.5" fill="none" stroke-width="0" stroke-opacity="1" stroke="#000000" transform="translate(0,305)"></path><path cs="100,100" d="M508.5,306.5 L508.5,306.5 L508.5,0.5" fill="none" stroke-width="0" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M593.5,0.5 L593.5,5.5" fill="none" stroke-width="0" stroke-opacity="1" stroke="#000000" transform="translate(0,305)"></path><path cs="100,100" d="M593.5,306.5 L593.5,306.5 L593.5,0.5" fill="none" stroke-width="0" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M678.5,0.5 L678.5,5.5" fill="none" stroke-width="0" stroke-opacity="1" stroke="#000000" transform="translate(0,305)"></path><path cs="100,100" d="M678.5,306.5 L678.5,306.5 L678.5,0.5" fill="none" stroke-width="0" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M763.5,0.5 L763.5,5.5" fill="none" stroke-width="0" stroke-opacity="1" stroke="#000000" transform="translate(0,305)"></path><path cs="100,100" d="M763.5,306.5 L763.5,306.5 L763.5,0.5" fill="none" stroke-width="0" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M847.5,0.5 L847.5,5.5" fill="none" stroke-width="0" stroke-opacity="1" stroke="#000000" transform="translate(0,305)"></path><path cs="100,100" d="M847.5,306.5 L847.5,306.5 L847.5,0.5" fill="none" stroke-width="0" stroke-opacity="0.15" stroke="#000000"></path></g><g><path cs="100,100" d="M932.5,0.5 L932.5,5.5" fill="none" stroke-width="0" stroke-opacity="1" stroke="#000000" transform="translate(0,305)"></path><path cs="100,100" d="M932.5,306.5 L932.5,306.5 L932.5,0.5" fill="none" stroke-width="0" stroke-opacity="0.15" stroke="#000000"></path></g></g></g><g></g><g></g><g></g><g></g><g><g transform="translate(115,33)"><g><g transform="translate(0,5)" visibility="visible" aria-label="graph 1 Applicant(s) 100"><path cs="100,100" d="M0.5,0.5 L0.5,41.5 L847.5,41.5 L847.5,0.5 L0.5,0.5 Z" fill="#90CAF9" stroke="#90CAF9" fill-opacity="1" stroke-width="0" stroke-opacity="1"></path></g><g transform="translate(0,56)" visibility="visible" aria-label="graph 1 Shortlisted 75"><path cs="100,100" d="M0.5,0.5 L0.5,41.5 L635.5,41.5 L635.5,0.5 L0.5,0.5 Z" fill="#F48FB1" stroke="#F48FB1" fill-opacity="1" stroke-width="0" stroke-opacity="1"></path></g><g transform="translate(0,107)" visibility="visible" aria-label="graph 1 Interviewed 50"><path cs="100,100" d="M0.5,0.5 L0.5,41.5 L424.5,41.5 L424.5,0.5 L0.5,0.5 Z" fill="#FFCC80" stroke="#FFCC80" fill-opacity="1" stroke-width="0" stroke-opacity="1"></path></g><g transform="translate(0,158)" visibility="visible" aria-label="graph 1 Offered 50"><path cs="100,100" d="M0.5,0.5 L0.5,41.5 L424.5,41.5 L424.5,0.5 L0.5,0.5 Z" fill="#B39DDB" stroke="#B39DDB" fill-opacity="1" stroke-width="0" stroke-opacity="1"></path></g><g transform="translate(0,209)" visibility="visible" aria-label="graph 1 Offers Accepted 30"><path cs="100,100" d="M0.5,0.5 L0.5,41.5 L254.5,41.5 L254.5,0.5 L0.5,0.5 Z" fill="#81C784" stroke="#81C784" fill-opacity="1" stroke-width="0" stroke-opacity="1"></path></g><g transform="translate(0,260)" visibility="visible" aria-label="graph 1 Backout(s) 10"><path cs="100,100" d="M0.5,0.5 L0.5,41.5 L85.5,41.5 L85.5,0.5 L0.5,0.5 Z" fill="#E57373" stroke="#E57373" fill-opacity="1" stroke-width="0" stroke-opacity="1"></path></g></g></g><g transform="translate(115,33)" opacity="1" visibility="visible"><g></g></g></g><g></g><g><path cs="100,100" d="M0.5,306.5 L0.5,0.5 L0.5,0.5" fill="none" stroke-width="1" stroke-opacity="0.3" stroke="#000000" transform="translate(115,33)"></path><g><path cs="100,100" d="M0.5,0.5 L0.5,306.5" fill="none" stroke-width="0" stroke-opacity="1" stroke="#000000" transform="translate(115,33)"></path></g><g><path cs="100,100" d="M0.5,0.5 L932.5,0.5" fill="none" stroke-width="0" stroke-opacity="1" stroke="#000000" transform="translate(115,338)" visibility="visible"></path></g></g><g></g><g></g><g><g transform="translate(115,33)"><text y="5" fill="#000000" font-family="Verdana" font-size="10px" opacity="1" text-anchor="start" style="pointer-events: none;" transform="translate(850,13)"><tspan y="5" x="0">100 </tspan><tspan y="17" x="0"> 80 Day(s) </tspan><tspan y="29" x="0">78% Moveforward</tspan></text><text y="5" fill="#000000" font-family="Verdana" font-size="10px" opacity="1" text-anchor="start" style="pointer-events: none;" transform="translate(638,64)"><tspan y="5" x="0">75 </tspan><tspan y="17" x="0"> 35 Day(s) </tspan><tspan y="29" x="0">8% Moveforward</tspan></text><text y="5" fill="#000000" font-family="Verdana" font-size="10px" opacity="1" text-anchor="start" style="pointer-events: none;" transform="translate(427,115)"><tspan y="5" x="0">50 </tspan><tspan y="17" x="0"> 68 Day(s) </tspan><tspan y="29" x="0">39% Moveforward</tspan></text><text y="5" fill="#000000" font-family="Verdana" font-size="10px" opacity="1" text-anchor="start" style="pointer-events: none;" transform="translate(427,166)"><tspan y="5" x="0">50 </tspan><tspan y="17" x="0"> 51 Day(s) </tspan><tspan y="29" x="0">50% Moveforward</tspan></text><text y="5" fill="#000000" font-family="Verdana" font-size="10px" opacity="1" text-anchor="start" style="pointer-events: none;" transform="translate(257,217)"><tspan y="5" x="0">30 </tspan><tspan y="17" x="0"> 15 Day(s) </tspan><tspan y="29" x="0">50% Moveforward</tspan></text><text y="5" fill="#000000" font-family="Verdana" font-size="10px" opacity="1" text-anchor="start" style="pointer-events: none;" transform="translate(88,268)"><tspan y="5" x="0">10 </tspan><tspan y="17" x="0"> 34 Day(s) </tspan><tspan y="29" x="0">84% Moveforward</tspan></text></g><g transform="translate(115,33)" opacity="1" visibility="visible"></g></g><g><g></g><text y="8" fill="#000000" font-family="Verdana" font-size="15px" opacity="1" font-weight="bold" text-anchor="middle" transform="translate(582,20)" style="pointer-events: none;"><tspan y="8" x="0">Jobs Pipline</tspan></text></g><g><g transform="translate(115,33)" visibility="visible"><text y="5" fill="#000000" font-family="Verdana" font-size="10px" opacity="1" text-anchor="end" transform="translate(-16,25.5)"><tspan y="5" x="0">Applicant(s)</tspan></text><text y="5" fill="#000000" font-family="Verdana" font-size="10px" opacity="1" text-anchor="end" transform="translate(-16,76.5)"><tspan y="5" x="0">Shortlisted</tspan></text><text y="5" fill="#000000" font-family="Verdana" font-size="10px" opacity="1" text-anchor="end" transform="translate(-16,127.5)"><tspan y="5" x="0">Interviewed</tspan></text><text y="5" fill="#000000" font-family="Verdana" font-size="10px" opacity="1" text-anchor="end" transform="translate(-16,178.5)"><tspan y="5" x="0">Offered</tspan></text><text y="5" fill="#000000" font-family="Verdana" font-size="10px" opacity="1" text-anchor="end" transform="translate(-16,229.5)"><tspan y="5" x="0">Offers Accepted</tspan></text><text y="5" fill="#000000" font-family="Verdana" font-size="10px" opacity="1" text-anchor="end" transform="translate(-16,280.5)"><tspan y="5" x="0">Backout(s)</tspan></text></g><g transform="translate(115,33)" visibility="visible"></g></g><g></g><g transform="translate(115,33)"></g><g></g><g></g></svg><a href="http://www.amcharts.com" title="JavaScript charts" style="position: absolute; text-decoration: none; color: rgb(0, 0, 0); font-family: Verdana; font-size: 11px; opacity: 0.7; display: block; left: 120px; top: 38px;">JS chart by amCharts</a></div></div></div>
      <!-- Jobs Pipline --> 
    </div>
    
  </section>

<script>
function getJobPipelineReports(){
	
	 var jobIds = $("#jobIds").val();
	  if(jobIds == null){
		  jobIds = '';
	  }
	  var dateSelection = $("#dateSelection").val(); 
		
	  $.ajax({ 
		   	type: "POST",
		   	url: sourceleadUrl+"/getAllJobsPipelineReoportsList?${_csrf.parameterName}=${_csrf.token}&jobIds="+jobIds+"&dateSelection="+dateSelection,
		    success: function(data) {
		    	getJobsPipelineAmcahrt(data);
		    }
		 });
	  
}

</script>

 <script src="../resources/js/export.min.js"></script> 
 <link href="../resources/css/export.css" rel="stylesheet" type="text/css">
<script>
function getJobsPipelineAmcahrt(data){
	var mainChar = [];
	if(data.applicantsCount>0){
		mainChar.push( {
			"category": "Applicant(s)",
			"column-1": data.applicantsCount,
			"Color": "#90CAF9",
			"Days": data.applicantsDays,
			"moveforward": "100"
		    } );
	}
	
	if(data.shortlistedCount>0){
		mainChar.push( {
			"category": "Shortlisted",
			"column-1": data.shortlistedCount,
			"Color": "#F48FB1",
			"Days": data.shortlistedDays,
			"moveforward": data.shortlistedMove
		    } );
	}
	
	if(data.submitToClientCount>0){
		mainChar.push( {
			"category": "Submit To Client",
			"column-1": data.submitToClientCount,
			"Color": "#EAEA23",
			"Days": data.shortlistedDays,
			"moveforward": 10
		    } );
	}
	if(data.interviewsCount>0){
		mainChar.push( {
			"category": "Interviewed",
		    "column-1": data.interviewsCount,
		    "Color": "#FFCC80",
		    "Days": data.interviewsDays,
		    "moveforward": data.interviewsMove
		} );
	}
	
	if(data.offeredCount>0){
		mainChar.push({
			"category": "Offered",
			"column-1": data.offeredCount,
			"Color": "#B39DDB",
			"Days": data.offeredDays,
			"moveforward": data.offeredMove
		});
	}
	if(data.offeredAcceptedCount>0){
		mainChar.push({
			"category": "Offers Accepted",
			"column-1": data.offeredAcceptedCount,
			"Color": "#81C784",
			"Days": data.offeredAcceptedDays,
			"moveforward": data.offeredAcceptedMove
		});
	}
	
	if(data.backoutCount>0){
		mainChar.push({
			"category": "Backout(s)",
			"column-1": data.backoutCount,
			"Color": "#E57373",
			"Days": data.backoutDays,
			"moveforward": 84
		});
	}
	
	
	AmCharts.makeChart("jobspipline",
			{
				"type": "serial",
				"categoryField": "category",
				"rotate": true,
				"marginBottom": 0,
				"marginLeft": 0,
				"marginRight": 30,
				"marginTop": 0,
				"colors": [],
				"startDuration": 1,
				"fontSize": 10,
				"categoryAxis": {
					"gridPosition": "start",
					"axisThickness": 0,
					"gridThickness": 0,
					"tickLength": 10,
					"titleRotation": -1
				},
				"export": {
				    "enabled": true,
				    "pageOrigin": false
				},
				"trendLines": [],
				"graphs": [
					{
						"descriptionField": "Days",
						"fillAlphas": 1,
						"fillColorsField": "Color",
						"id": "AmGraph-1",
						"labelPosition": "right",
						"labelText": "[[value]] <br> [[Days]] Day(s) <br>[[moveforward]]% Moveforward",
						"lineColorField": "Color",
						"lineThickness": 0,
						"maxBulletSize": 0,
						"title": "graph 1",
						"type": "column",
						"valueField": "column-1"
					},
					{
						"id": "AmGraph-2",
						"title": "graph 2",
						"valueField": "Color"
					}
				],
				"guides": [],
				"valueAxes": [
					{
						"id": "ValueAxis-1",
						"axisThickness": 0,
						"gridThickness": 0,
						"labelsEnabled": false,
						"title": ""
					}
				],
				"allLabels": [],
				"balloon": {},
				"titles": [
					{
						"id": "Title-1",
						"size": 15,
						"text": "Jobs Pipline"
					}
				],
				"dataProvider": mainChar
            }
		); 
	 
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
<jsp:include page="../masterFooter.jsp" />