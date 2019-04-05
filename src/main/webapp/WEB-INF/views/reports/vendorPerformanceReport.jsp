<%@page import="java.util.Calendar"%>
<script type="text/javascript" src="/resources/js/jquery.mousewheel.js"></script> 
<script type="text/javascript" src="/resources/js/jquery.jscrollpane.min.js"></script> 
<!-- amCharts javascript sources --> 
<script type="text/javascript" src="/resources/amcharts/amcharts.js"></script>
<script type="text/javascript" src="/resources/amcharts/serial.js"></script>
<script type="text/javascript" src="/resources/amcharts/pie.js"></script>
<link rel="stylesheet" href="/resources/css/export.css">
<script type="text/javascript" src="/resources/amcharts/plugins/responsive/responsive.min.js"></script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" src='/resources/js/vendor-performance.js'></script>
	<!-- Header Area-->
<div class="ReqRepHead clearfix">
	<div class="row">
		<div class="col-md-4">
			<label class="col-md-3 mt5">Vendor</label>
			<div class="col-md-9">
				<select id="vendorList" onchange="getContactList();" class="chosen-select form-control" data-placeholder="Vendor">
					<option selected>Select Vendor</option>
				</select>
			</div>
		</div>
		<div class="col-md-4">
			<label class="col-md-3 mt5">Contact</label>
			<div class="col-md-9">
				<select id="contactList" class="form-control chosen-select" data-placeholder="Vendor Contact(s)">
				</select>
			</div>
		</div>
		<div class="repSelBtn"><button type="button" class="btn btn-primary btn-sm" id="getVendorPerformance" onclick="">Run</button></div> 
	</div>
</div>
<!-- Header Area ends -->

<div class="GraView mt">
	<!-- Released Jobs -->
	
	<div class="clearfix row mt mb">
		<div class="col-md-12">
			<div class="projectDashboad clearfix positionRelative" style="height: 220px;">
				<div id="releaseJobsloadingDiv" style="display:none; position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url(&quot;/resources/images/loading.gif&quot;); background-position:center; background-repeat:no-repeat; background-size:75px;"></div>
				<div class="col-md-4">
					<div class="text-center" style="margin-top: 75px;">
						<h4 class="mt5">Released Jobs</h4>
						<a class="ml text-muted" id="releasedJobsCount" onclick="releasedJobsToVendors();">0</a>
					</div>
				</div>
				<div class="col-md-8">
					<div id="releaseJobsNoRecordsDiv" class='dataTables_empty' style="display: none;"></div>
					<div id="releaseJobs" style="width: 100%; height: 200px;" class="amchartAnchor-Hide"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- Released Jobs -->
	<div class="clearfix row">
		<!--Offer acceptance rate-->
		<div class="col-md-6 clearfix">
			<div class="projectDashboad clearfix" style="height: 400px;">
				<div id="offerAcceptanceRateloadingDiv" style="display:none; position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url(&quot;/resources/images/loading.gif&quot;); background-position:center; background-repeat:no-repeat; background-size:75px;"></div>
				<div class="clearfix positionRelative mb5 row">
					<div class="col-md-6">
						<h4>Offer Acceptance Rate</h4>
					</div>
					<c:set var="year" value="<%=Calendar.getInstance().get(Calendar.YEAR)%>"></c:set>
					<div class="col-md-4 dropdown-select positionRelative">
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
						<button type="button" class="btn btn-primary btn-sm" onclick="getOfferAcceptanceRateGraph();">Run</button>
					</div>
				</div>
				<div id="offerAcceptanceRateNoRecordsDiv" class='dataTables_empty' style="height: 335px !important; display: none;"></div>
				<div class="clearfix positionRelative" id="offerAcceptanceRateChartDiv">
					<div id="offerAcceptanceRate" style="width: 100%; height: 300px;"
						class="amchartAnchor-Hide"></div>
					<div class="clearfix"></div>
					<ul class="list-unstyled list-inline col-md-12">
						<li><label>Total Offers : <a href="javascript:void(0)" class="text-primary ml" id="oarCharttotalOfferCount">0</a></label></li>
						<li><label>Offer Accepted : <a href="javascript:void(0)" class="textSuccess ml" id="oarCharttotalAcceptedCount">0</a></label></li>
						<li><label>Offer Rejected : <a href="javascript:void(0)" class="colorRed ml" id="oarCharttotalRejectedCount">0</a></label></li>
					</ul>
				</div>
			</div>
		</div>
		<!--Offer acceptance rate-->
		<!--Quality Candidates-->
		<div class="col-md-6">
			<div id="qualityCandidatesloadingDiv" style="display:none; position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url(&quot;/resources/images/loading.gif&quot;); background-position:center; background-repeat:no-repeat; background-size:75px;"></div>
			<div class="projectDashboad clearfix positionRelative" style="height: 400px;">
				<h4>Quality Candidates</h4>
				<div class="pull-right" style="position: absolute; right: 15px; top: 50px; z-index: 99;">
						<h5 class="pull-right mr">Total Received:<a class="paddingLeft mr paddingLeft mt" id="totalReceivedCandidates">0</a></h5>
						<div class="clearfix"></div>
						<h5 class="pull-right mr">Hired:<a title="Hired" href="javascript:void(0)" class="paddingLeft mr paddingLeft mt" id="hiredReceivedCandidates" onclick="releasedJobsToVendorsApplicantHired();">0</a></h5>
						<div class="clearfix"></div>
						<h5 class="pull-right mr">Rejected:<a title="Rejected" href="javascript:void(0)" class="paddingLeft mr paddingLeft mt" id="rejectedReceivedCandidates" onclick="releasedJobsToVendorsApplicantDecline();">0</a></h5>
				</div>
				<div id="qualityCandidatesChartDiv" style="width: 100%; height: 200px;" class="amchartAnchor-Hide"></div>
				<div id="qualityCandidatesNoRecordsDiv" class='dataTables_empty' style="height: 335px !important; display: none;"></div>
			</div>
		</div>
		<!--Quality Candidates-->
	</div>

	<div class="clearfix row mt">
		<!--Average Time to Fill-->
		<div class="col-md-6 clearfix" style="display: none;">
			<div class="projectDashboad clearfix">
				<div class="clearfix positionRelative mb5 row">
					<div id="avgttf" style="width: 100%; height: 221px; overflow: hidden; text-align: left;" class="amchartAnchor-Hide"></div>
				</div>
			</div>
		</div>
		<!--Average Time to Fill-->
		
		<!-- Average percentage moving forward -->
		<div class="col-md-12 clearfix">
			<div class="projectDashboad clearfix" style="height: 400px;">
			<div id="avgPercentMovingFwdloadingDiv" style="display:none; position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url(&quot;/resources/images/loading.gif&quot;); background-position:center; background-repeat:no-repeat; background-size:75px;"></div>
				<div class="clearfix positionRelative mb5 row">
					<div class="col-md-12">
						<h4>Average Percentage Moving Forward</h4>
					</div>
				</div>
				<div class="clearfix positionRelative">
					<div id="avgPercentMovingFwdChartDiv" style="width: 100%; height: 335px" class="amchartAnchor-Hide"></div>
					<div id="avgPercentMovingFwdNoRecordsDiv" class='dataTables_empty' style="height: 335px !important; display: none;"></div>
				</div>
			</div>
		</div>
		<!-- Average percentage moving forward -->
	</div>

	<div class="clearfix"></div>

	<div class="clearfix row mt">
		<!--Applicant Decline-->
			<div class="col-md-6 clearfix">
				<div class="projectDashboad clearfix" style="height: 400px;">
					<div id="applicantDeclineloadingDiv" style="display:none; position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url(&quot;/resources/images/loading.gif&quot;); background-position:center; background-repeat:no-repeat; background-size:75px;"></div>
					<div class="clearfix positionRelative mb5 row">
						<div class="col-md-12">
							<h4>Applicant Decline<a href="javascript:void(0)" class="ml mt10" id="applicantsDeclineCount" onclick="releasedJobsToVendorsApplicantDecline();">0</a></h4>
						</div>
					</div>
					<div class="clearfix positionRelative">
						<div id="applicantDeclineChartDiv" style="width: 100%; height: 335px" class="amchartAnchor-Hide"></div>
						<div id="applicantDeclineNoRecordsDiv" class='dataTables_empty' style="height: 335px !important; display: none;"></div>
					</div>
				</div>
			</div>
		<!--Applicant Decline-->
		
		<!--Backout Candidates-->
			<div class="col-md-6 clearfix">
				<div class="projectDashboad clearfix" style="height: 400px;">
					<div id="backoutCandidatesloadingDiv" style="display:none; position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url(&quot;/resources/images/loading.gif&quot;); background-position:center; background-repeat:no-repeat; background-size:75px;"></div>
					<div class="clearfix positionRelative mb5 row">
						<div class="col-md-12">
							<h4>Backout Candidates<a href="javascript:void(0)" class="ml mt10" id="backoutCandidatesCount" onclick="releasedJobsToVendorsApplicantBackOut();">0</a></h4>
						</div>
					</div>
					<div class="clearfix positionRelative">
						<div id="backoutCandidatesChartDiv" style="width: 100%; height: 335px" class="amchartAnchor-Hide"></div>
						<div id="backoutCandidatesNoRecordsDiv" class='dataTables_empty' style="height: 335px !important; display: none;"></div>
					</div>
				</div>
			</div>
		<!--Backout Candidates-->
	</div>
</div>
<script type="text/javascript">
	var vmsURL = '${SourceLeadWeb_vmsUrl}';
</script>