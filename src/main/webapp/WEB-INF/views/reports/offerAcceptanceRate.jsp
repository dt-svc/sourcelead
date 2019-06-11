<%@page import="java.util.Calendar" %>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="/resources/amcharts/amcharts.js"></script>
<script src="/resources/amcharts/serial.js"></script>
<script src="/resources/amcharts/plugins/export/export.min.js"></script>
<link rel="stylesheet" href="/resources/amcharts/plugins/export/export.css" type="text/css" media="all" />

<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Offer Acceptance Rate</h2>
		</div>
		<div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${SourceLeadWeb_sourceleadUrl}/reportsDashboard">Reports Dashboard</a> &raquo; <a href="${SourceLeadWeb_sourceleadUrl}/performanceReportList">Performance Reports</a> &raquo; Offer Acceptance Rate</div>
	</section>
	<div class="ReqRepHead clearfix">
		<!-- <div class="col-md-2 pull-left reqListBtn">
			<span class="rAct" title="Graphical View" id="GView"><i
				class="fa fa-pie-chart"></i></span> <span class="" title="List View"
				id="LView"><i class="fa fa-list"></i></span>
		</div> -->
		<div class="col-md-7">
			<select id="clientList" class="chosen-select form-control" multiple data-placeholder="Select Client">
				
			</select>
		</div>
		<c:set var="year" value="<%=Calendar.getInstance().get(Calendar.YEAR)%>"></c:set>
		<div class="col-md-2 dropdown-select positionRelative">
			<select class="form-control" id="filterDate">
				<option value="" data-name="">Select Date</option>
				<c:set var="count" value="1"></c:set>
				<c:forTokens items="January,Febuary,March,April,May,June,July,August,September,October,November,December" delims="," var="month">
					<option value="${count}" data-type="month" data-name="${month}">${month}</option>
					<c:set var="count" value="${count + 1}"></c:set>
				</c:forTokens>
				<c:forEach begin="1" end="5">
					<option value="${year}" data-type="year" data-name="${year}">${year}</option>
					<c:set var="year" value="${year-1}"></c:set>
				</c:forEach>
				<option value="compare" data-type="compare" data-name="Year Comparison">Compare Previous Year(s)</option>
			</select>
		</div>
		<div id="runButtonDiv" class="repSelBtn">
			<button type="button" class="btn btn-primary btn-sm" onclick="getOfferAcceptanceRateReport();">Run</button>
		</div>
		<!-- <div class="col-md-1">
			<div class="btn-group bootstrap_dropdownMenu dropdownPadding">
				<button type="button" class="btn btn-primary btn-sm" title="Actions">
					<i class="fa fa-retweet"></i>
				</button>
				<button type="button" class="btn btn-primary dropdown-toggle btn-sm"
					data-toggle="dropdown">
					<span class="caret"></span>
				</button>
				<ul class="dropdown-menu clearfix  dropdown-menu-right">
					<li role="Export" class="mt5 cursorPointer"> <i class="fa fa-file-pdf-o paddingRight"></i>Export</li>
					<li role="Print" class="mt5 cursorPointer"><i class="fa fa-print paddingRight"></i>Print</li>
					<li role="Download" class="mt5 cursorPointer"><i class="fa fa-download paddingRight"></i>Download</li>
					<li role="Share" class="mt5 mb5 cursorPointer"><i class="fa fa-share-alt paddingRight"></i>Share</li>
				</ul>
			</div>
		</div> -->
	</div>
	<div class="GraView">
		<div class="clearfix row mt">
			<div class="col-md-12">
				<div class="projectDashboad chartDashboard clearfix positionRelative">
					<div id="offerAcceptanceRate" style="width: 100%; height: 400px;" class="amchartAnchor-Hide"></div>
					<div id="noRecordsDiv" class='dataTables_empty' style="display: none;"></div>
					<div id="loadingDiv" style="position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url(&quot;/resources/images/loading.gif&quot;); background-position:center; background-repeat:no-repeat; background-size:75px;"></div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix mt"></div>
</section>

<script type="text/javascript">
var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var config = {
		'.chosen-select' : {},
		'.chosen-select-deselect' : {
			allow_single_deselect : true
		},
		'.chosen-select-no-single' : {
			disable_search_threshold : 10
		},
		'.chosen-select-no-results' : {
			no_results_text : 'Oops, nothing found!'
		},
		'.chosen-select-width' : {
			width : "95%"
		}
	}
	for (var selector in config) {
		$(selector).chosen(config[selector]);
	}
	$('.chosen-select').each(function() {
		var $this = $(this);
		$this.next().css({
			'width' : '100%'
		});
	})
	$(document).ready(function() {
		$("#cd").click(function() {
			$(".clientDetails").toggleClass('hidden', 500);
		});
		
		$("#clientList").trigger('chosen:update');
		
		/* $("#clientList").change(function() {
			getOfferAcceptanceRateReport();
		}); */
		
		getClients();
		getOfferAcceptanceRateReport();
	});

	$(function() {
		$('#customDates').change(function() {
			var optval = $(this).val();
			if (optval == "custom") {
				document.getElementById("customLeaves").style.display = "block";
			} else {
				document.getElementById("customLeaves").style.display = "none";
			}
		});
	});
	
	function prepareGraph(title, dateFormat, jsonData) {
		var categoryAxis = dateFormat === "YYYY-MM-DD" ? {"parseDates" : true} : dateFormat === "YYYY-MM" ? {"parseDates" : true, "minPeriod": "MM"} : {"parseDates" : true, "minPeriod": "YYYY"};
		AmCharts.makeChart("offerAcceptanceRate", {
			"type" : "serial",
			"categoryField" : "date",
			"dataDateFormat" : dateFormat,
			"theme" : "default",
			"export": {
				"enabled": true
			},
			"categoryAxis" : categoryAxis,
			"chartCursor" : {
				"enabled" : true,
				"categoryBalloonDateFormat": dateFormat === "YYYY-MM-DD" ? "MMM DD, YYYY" : dateFormat === "YYYY-MM" ? "MMM YYYY" : "YYYY"
			},
			"chartScrollbar": {
				"enabled": true
			},
			"trendLines" : [],
			"graphs" : [ {
				"bullet" : "square",
				"id" : "offerMade",
				"title" : "Offer Made",
				"type": "smoothedLine",
				"lineColor" : "#3498db",
				"valueField" : "offerMade"
			}, {
				"bullet" : "triangleDown",
				"id" : "offer Rejected",
				"title" : "Offer Rejected",
				"type": "smoothedLine",
				"lineColor" : "#FF0000",
				"valueField" : "offerRejected"
			}, {
				"bullet" : "bubble",
				"id" : "offerAccepted",
				"title" : "Offer Accepted",
				"type": "smoothedLine",
				"lineColor" : "#2ECC71",
				"valueField" : "offerAccepted"
			} ],
			"guides" : [],
			"valueAxes" : [ {
				"id" : "ValueAxis-1",
				"axisColor" : "#AAB3B3",
				"gridThickness" : 0,
				"title" : "No.of Applicants"
			} ],
			"allLabels" : [],
			"balloon" : {
				"fixedPosition" : false
			},
			"legend" : {
				"enabled" : true,
				"useGraphSettings" : true,
				"valueAlign": "left"
			},
			/* "titles": [{
				"id": "Title-1",
				"size": 15,
				"text": title
			}], */
			"dataProvider" : jsonData
		});
		$("#offerAcceptanceRate").show();
		$("#noRecordsDiv").hide();
	}
	
	function getOfferAcceptanceRateReport() {
		$("#loadingDiv").show();
		var clients = $('#clientList').val() === null ? "" : $('#clientList').val();
		var selectedClients = "";
		for(var e in clients) {
			selectedClients += (selectedClients !== "" ? "," : "") + clients[e];
		}
		var filteredDate = $("#filterDate").val();
		var filterType = $("#filterDate :selected").data("type");
		var filterValue = $("#filterDate :selected").data("name");
		$.ajax({
		   	type: "POST",
		   	url: sourceleadUrl+"/offerAcceptanceRateList?${_csrf.parameterName}=${_csrf.token}&selectedClients=" + selectedClients + "&filteredDate=" + filteredDate + "&filterType=" + filterType,
		    success: function(data) {
		    	if(data && data.response === "SUCCESS") {
		    		if(data.data !== null && data.data !== "" && data.data.length > 0) {
		    			var reportData = [];
		    			for(var e in data.data) {
		    				reportData.push({
		    					"date" : data.data[e].offerDate,
		    					"offerRejected" : data.data[e].rejectedCount,
		    					"offerMade" : data.data[e].offerCount,
		    					"offerAccepted" : data.data[e].acceptedCount
		    				});
		    			}
		    			var title = (data.responseMessage === "month" ? "Monthly" : data.responseMessage === "year" ? "Yearly" : "Year(s) Comparison") + " Graph" + (filterValue !== "" ? " for " + filterValue : "");
		    			var dateFormat = data.responseMessage === "month" ? "YYYY-MM-DD" : data.responseMessage === "year" ? "YYYY-MM" : "YYYY";
		    			prepareGraph(title, dateFormat, reportData);
		    		} else {
		    			$("#offerAcceptanceRate").hide();
			    		$("#noRecordsDiv").show();
		    		}
		    	} else {
		    		alert(data.responseMessage);
		    	}
		    	$("#loadingDiv").hide();
		    }
		});
	}
	
	function getClients(){
		var orgRelationId ="${orgRelationId}";
		$.ajax({
			type : "GET",
			url: vmsUrl+"/getClientList?"+tokenParamName+"="+tokenValue+"",
			success :function(json) {
				var output = "";
				for (var i=0; i<json.data.length; i++) {
					var opt = json.data[i];
					if(orgRelationId == opt.id){
						var selected = 'selected';
						output = output + "<option value='" +opt.id+ "'"+selected+" data-responder_location_id=" + opt.responderId + ">"+opt.orgName+"</option>";
					} else {
						output = output + "<option value='" +opt.id+ "' data-responder_location_id=" + opt.responderId + ">"+opt.orgName+"</option>";
					}
				}
				
				$("#clientList").empty();
				$("#clientList").append(output);
				$("#clientList").trigger("chosen:updated");
				
			},
		});

	}
</script>