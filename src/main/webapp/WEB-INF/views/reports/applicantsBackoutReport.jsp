<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />


<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link href="/resources/css/jquery-tagit.css" rel="stylesheet">
<script src="/resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<script src="/resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="/resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>

<script type="text/javascript" src="https://www.amcharts.com/lib/3/amcharts.js"></script>
<script type="text/javascript" src="https://www.amcharts.com/lib/3/serial.js"></script>
<script type="text/javascript" src="https://www.amcharts.com/lib/3/plugins/export/export.min.js"></script>


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
      <div class="pull-right brud-crum">Home &raquo; Applicant(s) Backout</div>
    </section>
    <!-- hiring Process  -->
    <div class="content-dashboard clearfix">
      <div class="ReqRepHead clearfix">
        <div class="col-md-3 pull-left reqListBtn">
        <a href="applicants-backout.html" title="Graphical View" class="btn btn-default"><i class="fa fa-pie-chart"></i></a>
        <a href="applicantsbackout-list.html" title="List View" class="btn btn-default"><i class="fa fa-list"></i></a>
        </div>
        <div class="col-md-4">
          <select class="chosen-select form-control" multiple data-placeholder="Select Job" name = "selectJob" id = "reportJobList">
          <c:forEach var = "requirementReport"  items="${requirementDTOList}">
         	 <option  value= "${requirementReport.requirementId }">${requirementReport.jobTitle}</option>
          </c:forEach>
          </select>
        </div>
        
        <div class="col-md-2 dropdown-select positionRelative">
           <select class="form-control" id="custom-dates" name="dateSelection" id="applicantBackoutdateSelection">
            <option value=''>Select Date</option>
            <option value='Last 7 days' selected>Last 7 days</option>
            <option value='Last 15 days'>Last 15 days</option>
            <option value='1 month'>1 month</option>
            <option value='3 months'>3 months</option>
            <option value='YTD'>YTD</option>
            <option value='Last Year'>Last Year</option>
            <option value="custom">Custom</option>
          </select>
          <!-- Top Leave Takers Custom Date -->
          <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="customLeaves" style="display: none; width: 346px;">
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
        <div class="col-md-1 noPad">
        <button type="button" class="btn btn-primary btn-sm	mt2" onclick = "getApplicantBackoutReportCount();">Customize</button>
        </div>
          <div class="col-md-2 text-right">
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
      <div class="row">
        <div class="col-md-12 mt25">
          <div class="clients_wrap_table dashboard-widget">
            <h4 class="pull-left">Backout Reasons</h4>
            <div id="backoutReason" style="width:100%; height: 250px; margin-bottom: 25px;" class="amchartAnchor-Hide"></div>
          </div>
        </div>
      </div>
      <!-- Decline Reasons -->
      
      <!-- Best recruiters  -->
      <div class="content-dashboard clearfix hidden"> 
        <!-- Assigned Requirements -->
        <div class="clearfix dashboard-widget mt25 col-md-12">
          <div class="clearfix">
          <h4 class="pull-left">Applicant Backout Lists</h4>
          	<div class="remove_search pull-right">
                  <input placeholder="Search" type="text">
                </div>
          </div>
          <div class="clearfix require_wrap_table dashboard-data">
            <table class="tableBor">
              <thead>
                <tr>
                  <th>Jobs</th>
                  <th>Current Company</th>
                  <th>Compensation</th>
                  <th>Better Offer</th>
                  <th>Cold Feet</th>
                  <th>Personal</th>
                  <th>Other</th>
                  <th>Total</th>
                </tr>
                <tr>
				  <td><div>Jobs</div></td>
                  <td><div>Current Company</div></td>
                  <td><div>Compensation</div></td>
                  <td><div>Better Offer</div></td>
                  <td><div>Cold Feet</div></td>
                  <td><div>Personal</div></td>
                  <td><div>Other</div></td>
                  <td><div>200</div></td> 
                  </tr>
              </thead>
              <tbody>
              </tbody>
            </table>
            <!-- Pagenation -->
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
        </div>
        <!-- Pagenation Ends --> 
      </div>
      <!-- Best recruiters Ends  --> 
      
    </div>
  </section>
  
  <script>
  function getApplicantBackoutReportCount(){
	var jobIds = $("#reportJobList").val();
	  if(jobIds == null){
		  jobIds = '';
	  }
	  var dateSelection = $("#applicantBackoutdateSelection").val(); 
	  $.ajax({ 
		   	type: "GET",
		   	url: sourceleadUrl+"/getApplicantBackoutReport?${_csrf.parameterName}=${_csrf.token}&jobIds="+jobIds+"&applicantBackoutdateSelection="+dateSelection,
		    success: function(json) {
        		 loadApplicantSourceChart(json);
		        }
		 });
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
  
  
  <script type="text/javascript">
  function loadApplicantSourceChart(json){
	   var mainChartData = [
		   {
				"category": "Current Company",
				"column-1": json['Current Company'],
				"color": "#9FA8DA"
			},
			{
				"category": "Compensation",
				"color": "#90CAF9",
				"column-1": json['Compensation']
			},
			{
				"category": "Better Offer",
				"column-1": json['Better Offer'],
				"color": "#FFAB91"
			},
			{
				"category": "Cold Feet",
				"column-1": json['Cold Feet'],
				"color": "#A5D6A7"
			},
			{
				"category": "Personal",
				"column-1": json['Personal'],
				"color": "#FFE0B2"
			},
			{
				"category": "Other",
				"column-1": json['other'],
				"color": "#BCAAA4"
			}
		];
	   
	  AmCharts.makeChart("backoutReason",
				{
					"type": "serial",
					"categoryField": "category",
					"rotate": true,
					"marginBottom": 10,
					"marginLeft": 10,
					"marginRight": 50,
					"marginTop": 10,
					"startDuration": 1,
					"export": {
						"enabled": true
					},
					"categoryAxis": {
						"gridPosition": "start",
						"position": "left",
						"title": ""
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
					"dataProvider":  mainChartData
				}
			);
			 
  }
		</script>