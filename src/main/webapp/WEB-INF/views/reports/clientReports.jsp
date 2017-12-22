<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<script type="text/javascript">
$(document).ready(function(){
	getTopTenClientReports();
	getTopFiveReceivsblesReports();
});
</script>


<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Clients Report</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Clients Report</div>
    </section>
    <div class="clearfix"></div>
    <div class="clearfix row mb">
      <div class="col-md-12 text-center">
        <div class="projectDashboad clearfix">
          <ul class="list-unstyled list-inline aboutusersCount">
            <li class="col-md-4">
              <div class="text-center well well-sm" id="activeLi">
                <div class="vedors-board clearfix">
                  <div class="pull-left ml"> <i class="fa fa-address-book-o fa-2x text-primary mt"></i> </div>
                  <div class="pull-left col-md-10 padding_right">
                    <div class="clearfix">
                      <h4 class="pull-left">Active Clients</h4>
                      <label class="pull-right ven-count" onClick="usageactiveList()">${activeClientsCount}</label>
                    </div>
                    <div class="clearfix">
                      <h4 class="pull-left">Contact</h4>
                      <label class="pull-right ven-count" onClick="usageactiveList()">${activeSpocCount}</label>
                    </div>
                  </div>
                </div>
              </div>
            </li>
            <li class="col-md-4">
              <div class="text-center well well-sm" id="activeLi">
                <div class="vedors-board clearfix">
                  <div class="pull-left ml"> <i class="fa fa-address-book fa-2x text-danger mt"></i> </div>
                  <div class="pull-left col-md-10 padding_right">
                    <div class="clearfix">
                      <h4 class="pull-left">Inactive Clients</h4>
                      <label class="pull-right ven-count" onClick="usageactiveList()">${inActiveClientsCount}</label>
                    </div>
                    <div class="clearfix">
                      <h4 class="pull-left">Contact</h4>
                      <label class="pull-right ven-count" onClick="usageactiveList()">${inActiveSpocCount}</label>
                    </div>
                  </div>
                </div>
              </div>
            </li>
            <li class="col-md-4">
              <div class="text-center well well-sm" id="activeLi">
                <div class="vedors-board clearfix">
                  <div class="pull-left ml"> <i class="fa fa-briefcase fa-2x text-info mt"></i> </div>
                  <div class="pull-left col-md-10 padding_right">
                    <div class="clearfix">
                      <h4 class="pull-left">Total Clients</h4>
                      <label class="pull-right ven-count" onClick="usageactiveList()">${totalClientsCount}</label>
                    </div>
                    <div class="clearfix">
                      <h4 class="pull-left">Total Contacts</h4>
                      <label class="pull-right ven-count" onClick="usageactiveList()">${totalSpocCount}</label>
                    </div>
                  </div>
                </div>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="ReqRepHead clearfix">
      <div class="col-md-5 pull-left reqListBtn"> <a href="clients-report.html" title="Graphical View" class="btn btn-default"><i class="fa fa-pie-chart"></i></a> <a href="clientsreport-list.html" title="List View" class="btn btn-default"><i class="fa fa-list"></i></a>
        <div class="hidden"> <span class="rAct" title="Graphical View" id="GView"><i class="fa fa-pie-chart"></i></span> <span class="" title="List View" id="LView"><i class="fa fa-list"></i></span> </div>
      </div>
      <div class="col-md-2 dropdown-select positionRelative col-md-offset-4">
        <select class="form-control" id="dateFrequency">
          <option>Select Date</option>
          <option value="Last_7_days" selected>Last 7 days</option>
          <option value="Last_15_days">Last 15 days</option>
          <option value="1_month">1 month</option>
          <option value="3_months">3 months</option>
           <option value="YTD">last year</option>
          <option value="Last_Year">last year</option>
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
      <div class="col-md-1">
        <div class="btn-group bootstrap_dropdownMenu dropdownPadding">
          <button type="button" class="btn btn-primary btn-sm" title="Actions"><i class="fa fa-retweet"></i></button>
          <button type="button" class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown"> <span class="caret"></span> </button>
          <ul class="dropdown-menu clearfix  dropdown-menu-right">
            <li role="Export" class="mt5 cursorPointer"><i class="fa fa-file-pdf-o paddingRight"></i>Export</li>
            <!--
						<li role="Print" class="mt5 cursorPointer"><i class="fa fa-print paddingRight"></i>Print</li>
						<li role="Download" class="mt5 cursorPointer"><i class="fa fa-download paddingRight"></i>Download</li>
						<li role="Share" class="mt5 mb5 cursorPointer"><i class="fa fa-share-alt paddingRight"></i>Share</li>
-->
          </ul>
        </div>
      </div>
    </div>
    <div class="GraView">
      <div class="content-area mt">
			<div class="clearfix mb"></div>
			<div class="clearfix mb row">
			  <div class="col-md-6">
				<div class="projectDashboad clearfix positionRelative">
				  <div id="top5receivables" style="width:100%; height: 250px" class="amchartAnchor-Hide"></div>
				</div>
			  </div>
			  <div class="col-md-6">
				<div class="projectDashboad clearfix positionRelative">
				  <div id="engagedContracts" style="width:100%; height:250px" class="amchartAnchor-Hide"></div>
				</div>
			  </div>
			</div>
			<div class="clearfix mt mb row">
			  <div class="col-md-6">
				<div class="projectDashboad clearfix positionRelative">
				  <div id="avgttf" style="width:100%; height: 250px" class="amchartAnchor-Hide"></div>
				</div>
			  </div>
			  <div class="col-md-6">
				<div class="projectDashboad clearfix positionRelative">
				  <div id="avgtth" style="width:100%; height: 250px" class="amchartAnchor-Hide"></div>
				</div>
			  </div>
			</div>
			<div class="clearfix mt mb"></div>
			<div class="clearfix"></div>
	  </div>
        <div class="clearfix mt row">
          <div class="col-md-12">
            <div class="projectDashboad clearfix positionRelative">
              <div class="row">
                <h4 class="col-md-8" id="vr">Top 10 Clients Report</h4>
                <h4 class="col-md-8" style="display: none;" id="cr">Contact Report</h4>
                <div class="select-fild col-md-2">
                  <select id="vendorContact">
                    <option value="byVendor">By Client</option>
                    <option value="byContract">By Contact</option>
                  </select>
                </div>
                <div class="col-md-2" id="sv">
                  <select class="chosen-select" data-placeholder="Select Client">
                    <option>Select Client</option>
                    <option>Client-1</option>
                    <option>Client-2</option>
                    <option>Client-3</option>
                    <option>Client-4</option>
                    <option>Client-5</option>
                  </select>
                </div>
                <div class="col-md-2" id="sc" style="display: none;">
                  <select class="chosen-select" data-placeholder="Select Contractor">
                    <option>Select Contact</option>
                    <option>Contact-1</option>
                    <option>Contact-2</option>
                    <option>Contact-3</option>
                    <option>Contact-4</option>
                    <option>Contact-5</option>
                  </select>
                </div>
              </div>
              <div class="require_wrap_table" id="byVendor">
                <table>
                  <thead>
                    <tr>
                      <th>Company Name</th>
                      <th>No. of Contacts</th>
                      <th>Total Jobs Released</th>
                      <th>Submission</th>
                      <th>Hired</th>
                      <th>Avg.TTF</th>
                      <th>Total Contracts</th>
                      <th>Billing</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td><div class="tbldata-dotdot"><a href="client-report-v1.html">Tata Consultancy Sevices Pvt. Limited</a> </div></td>
                      <td><div class="tblminwidth-30">100</div></td>
                      <td><div class="tblminwidth-30">100</div></td>
                      <td><div class="tblminwidth-30"> <strong class="">200</strong> <small class="text-danger">20%<i class="fa fa-sort-desc"></i></small> </div></td>
                      <td><div class="tblminwidth-30"> <strong class="">100</strong> <small class="text-danger">8%<i class="fa fa-sort-asc"></i></small> </div></td>
                      <td><div class="tblminwidth-30">10 days</div></td>
                      <td><div class="tbldata-dotdot-vsm">200</div></td>
                      <td><div class="tbldata-dotdot-vsm"><i class="fa fa-dollar paddingRight"></i>12365489788</div></td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <div class="require_wrap_table" id="byContract" style="display: none;">
                <table>
                  <thead>
                    <tr>
                      <th>Contract Name</th>
                      <th>Company Name</th>
                      <th>Release Jobs</th>
                      <th>Submission</th>
                      <th>Hired</th>
                      <th>Avg.TTF</th>
                      <th>Total Contracts</th>
                      <th>Billing</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td><div class="tbldata-dotdot-vsm"><a href="contract-report.html">Tataconsultancy Services Pvt. Ltd.</a></div></td>
                      <td><div class="tbldata-dotdot-vsm">Rajkumar Santoshi</div></td>
                      <td><div class="tblminwidth-30">100</div></td>
                      <td><div class="tblminwidth-30"> <strong class="">200</strong> <small class="text-danger">20%<i class="fa fa-sort-desc"></i></small> </div></td>
                      <td><div class="tblminwidth-30"> <strong class="">100</strong> <small class="text-danger">8%<i class="fa fa-sort-asc"></i></small> </div></td>
                      <td><div class="tblminwidth-30">10 days</div></td>
                      <td><div class="tbldata-dotdot-vsm">200</div></td>
                      <td><div class="tbldata-dotdot-vsm"><i class="fa fa-dollar paddingRight"></i>12365489788</div></td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
          <div class="clearfix"></div>
        </div>
      </div>

    <div class="clearfix"></div>
    <div class="LisView mt">
      <div class="vendor_wrap">
        <div class="candidate_wrap_table recruiterReports_table">
          <table>
            <thead>
              <tr>
                <th> <i class="fa fa fa-address-book fa-2x"></i>
                  <div class="small">Name</div>
                </th>
                <th> <i class="fa fa-briefcase fa-2x"></i>
                  <div class="small">Requirements</div>
                </th>
                <th> <i class="fa fa-user-circle fa-2x"></i>
                  <div class="small"> Candidates</div></th>
                <th> <i class="fa fa-users fa-2x"></i>
                  <div class="small"> Applicants </div>
                </th>
                <th><i class="fa fa-list-ul fa-2x"></i>
                  <div class="small">Shortlisted</div></th>
                <th><i class="fa fa-send fa-2x"></i>
                  <div class="small">Submitted</div></th>
                <th><i class="fa fa-handshake-o fa-2x"></i>
                  <div class="small">Interviewed</div></th>
                <th><i class="fa fa-envelope-open-o fa-2x"></i>
                  <div class="small">Offers</div></th>
                <th><i class="fa fa-ban fa-2x"></i>
                  <div class="small">Rejected</div></th>
                <th title="Average time to fill"><i class="fa fa-clock-o fa-2x"></i>
                  <div class="small">Avg. T F</div></th>
                <th title="Average time to fill"><i class="fa fa-object-ungroup fa-2x"></i>
                  <div class="small">Total</div></th>
                <th title="Average time to fill"><i class="fa fa-dollar fa-2x"></i>
                  <div class="small">Billing</div></th>
              </tr>
              <tr>
                <td><div class="tbldata-dotdot-sm">Muralimanohar</div></td>
                <td><div class="small">200</div></td>
                <td><div class="small">100</div></td>
                <td><div class="small">80</div></td>
                <td><div class="small">30</div></td>
                <td><div class="small">40</div></td>
                <td><div class="small">10</div></td>
                <td><div class="small">11</div></td>
                <td><div class="small">32</div></td>
                <td><div class="small">32</div></td>
                <td><div class="small">100</div></td>
                <td><div class="small"><i class="fa fa-dollar paddingLeft"></i>10000</div></td>
              </tr>
            </thead>
            <tbody>
            </tbody>
          </table>
        </div>
        <nav>
          <ul id="paging" class="pagination">
            <li><a href="#">Previous</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li class="disabled"><a href="#">6</a></li>
            <li data-page="last"><a href="#">Next</a></li>
          </ul>
          <div class="clearfix"></div>
        </nav>
      </div>
    </div>
  </section>
  
  
  <!-- Top5 Vendor Expenses Report AmChart --> 
<script type="text/javascript">
			AmCharts.makeChart("top5receivables",
{
	"type": "pie",
	"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
	"innerRadius": -1,
	"labelRadius": 15,
	"labelText": "[[title]]:  <br> $  [[value]]  ([[percents]]%)",
	"pieX": "50%",
	"pieY": "55%",
	"radius": "40%",
	"colorField": "color",
	"colors": [],
	"labelsEnabled": false,
	"marginBottom": 0,
	"marginTop": 0,
	"maxLabelWidth": 75,
	"outlineThickness": 2,
	"titleField": "category",
	"valueField": "column-1",
	"addClassNames": true,
	"handDrawThickness": 10,
	"responsive": {
		"enabled": true
	},
	"export": {
		"enabled": true
	},
	"allLabels": [
		{
			"bold": true,
			"color": "#CC0000",
			"id": "Label-1",
			"size": 15,
			"text": "Top 5 Receivables",
			"x": "5%",
			"y": "5%"
		}
	],
	"balloon": {},
	"legend": {
		"enabled": true,
		"labelWidth": 125,
		"marginLeft": 0,
		"marginRight": 0,
		"marginTop": 25,
		"markerSize": 10,
		"position": "left",
		"spacing": 5
	},
	"titles": [],
	"dataProvider": [
		{
			"category": "Tata Consultancy Services Pvt. Ltd",
			"column-1": "1000",
			"color": "#7E57C2"
		},
		{
			"category": "Client2",
			"column-1": "2000",
			"color": "#5C6BC0"
		},
		{
			"category": "Client3",
			"column-1": "3000",
			"color": "#64B5F6"
		},
		{
			"category": "Client4",
			"column-1": "4000",
			"color": "#81C784"
		},
		{
			"category": "Client5",
			"column-1": "8000",
			"color": "#FFB74D"
		},
		{
			"category": "Others",
			"column-1": "4000",
			"color": "#FF7043"
		}
	]
}
							  );
		</script> 

<!-- engagedContracts Report AmChart --> 
<script type="text/javascript">
			AmCharts.makeChart("engagedContracts",
				{
	"type": "pie",
	"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
	"labelRadius": 15,
	"labelText": "[[title]]:  <br> [[value]]",
	"pieX": "50%",
	"pieY": "55%",
	"radius": "40%",
	"colorField": "color",
	"colors": [],
	"labelsEnabled": false,
	"marginBottom": 0,
	"marginTop": 0,
	"outlineThickness": 2,
	"titleField": "category",
	"valueField": "column-1",
	"addClassNames": true,
	"handDrawThickness": 10,
	"export": {
		"enabled": true
	},
	"allLabels": [
		{
			"bold": true,
			"color": "#3F51B5",
			"id": "Label-1",
			"size": 15,
			"text": "Top 5 Engaged Contracts",
			"x": "5%",
			"y": "5%"
		}
	],
	"balloon": {},
	"legend": {
		"enabled": true,
		"position": "left"
	},
	"titles": [],
	"dataProvider": [
		{
			"category": "Contract1",
			"column-1": "100",
			"color": "#26A69A"
		},
		{
			"category": "Contract2",
			"column-1": "200",
			"color": "#26C6DA"
		},
		{
			"category": "Contract3",
			"column-1": "300",
			"color": "#9CCC65"
		},
		{
			"category": "Contract4",
			"column-1": "300",
			"color": "#8D6E63"
		},
		{
			"category": "Contract5",
			"column-1": "800",
			"color": "#607D8B"
		},
		{
			"category": "Others",
			"column-1": "400",
			"color": "#FFD54F"
		}
	]
}
			);
		</script> 
		
<script type="text/javascript">
			AmCharts.makeChart("avgtth",
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
					"titles": [
						{
							"id": "Title-1",
							"size": 15,
							"text": "Success Percentage"
						}
					],
					"dataProvider": [
						{
							"category": "Applicants",
							"column-1": "100",
							"color": "#9575CD"
						},
						{
							"category": "Shortlisted",
							"color": "#90CAF9",
							"column-1": "80"
						},
						{
							"category": "Submit to Client",
							"column-1": "50",
							"color": "#FFB74D"
						},
						{
							"category": "Interviewed",
							"column-1": "30",
							"color": "#5C6BC0"
						},
						{
							"category": "Offered",
							"column-1": "20",
							"color": "#9CCC65"
						},
						{
							"category": "Joined",
							"column-1": "10",
							"color": "#1E88E5"
						}
					]
				}
			);
	</script> 
<!-- amCharts javascript code --> 
<script type="text/javascript">
			AmCharts.makeChart("avgttf",
				{
	"type": "pie",
	"innerRadius": "0%",
	"pieX": "50%",
	"pieY": "55%",
	"radius": "40%",
	"colorField": "color",
	"labelsEnabled": false,
	"titleField": "category",
	"valueField": "column-1",
	"export": {
		"enabled": true
	},
	"allLabels": [],
	"balloon": {
		"showBullet": true
	},
	"legend": {
		"enabled": true,
		"markerLabelGap": 10,
		"markerSize": 15,
		"position": "left",
		"spacing": 25,
		"valueText": "[[value]] Days",
		"valueWidth": 35,
		"verticalGap": 15
	},
	"titles": [
		{
			"id": "Title-1",
			"text": "Average Time to Fill"
		}
	],
	"dataProvider": [
		{
			"category": "Applicants",
			"column-1": 6,
			"color": "#80CBC4"
		},
		{
			"category": "Shortlisted",
			"column-1": 2,
			"color": "#FFB74D"
		},
		{
			"category": "Submit to Client",
			"column-1": 2,
			"color": "#9575CD"
		},
		{
			"category": "Interviewed",
			"column-1": 2,
			"color": "#4DD0E1"
		},
		{
			"category": "Offered",
			"column-1": 2,
			"color": "#AED581"
		},
		{
			"category": "Joined",
			"column-1": "3",
			"color": "#81D4FA"
		}
	]
}
			);
</script> 

<script>
function getTopTenClientReports(){
	$.get(sourceleadUrl+ "/getTopTenFiveConracts", function(data) {
		
	});
}


function getTopFiveReceivsblesReports(){
	var frequency = $("#dateFrequency").val();
	$.ajax({
		type : "GET",
		url : sourceleadUrl+"/getTopFiveReceivsblesReports?&dateFrequency="+frequency+"&type=Receivable",
// 	    success: function(data) {
//  	    	$("#popUp").empty();
//  		    $(".project_inn_mask").fadeOut(200);
// 	    		location.replace(sourceleadUrl+"/invoiceList");
// 	    }
	});
}
</script>
		

<jsp:include page="../masterFooter.jsp" />