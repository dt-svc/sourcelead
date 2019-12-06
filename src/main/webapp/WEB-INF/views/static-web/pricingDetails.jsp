  <!--   HTML   -->	  
<jsp:include page="../staticHeader.jsp" />   
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="pricingpagesBanner">
	<img src="/resources/static-web/images/pricingBanner.jpg" alt="Image" class="img-responsive">
</div>
<div class="clearfix"></div>

<section class="container-fluid">
		
<div class="clearfix container contentinner_area">
        
    <div class="prcingButton">
            <div class="clearfix">
                    <ul class="pull-right pricingButton">
                        <li id="monthly" title="Monthly" class="pricebtnActive">Monthly</li>
                        <li id="annullay" title="Annually">Annually</li>
                    </ul>
                    <div class="clearfix"></div>
            </div>
    </div>
    <div class="pricingChart mt">
        <div class="clearfix"></div>
       <div class="pricingchart-widget firstWidget">
        <div class="prichingHeader">   
        <h5>Basic</h5>
           <div class="clearfix"></div>
           <div class="clearfix">
                <h4 class="text-info">FREE</h4>
                <span>3 Months</span>
            </div>
        </div>
                    <div class="clearfix">
                        <ul class="list-unstyled pricingchart-widget-list">
                             <li>ATS,<br/>PMS, CMS</li>
                             <li>3 Months</li>
                             <li>Job Postings</li>
                             <li>E-mail configuration</li>
                             <li>Data Migration</li>
                             <li>Reports Limited to ATS, PMS</li>
                        </ul>
					</div>
					
					<div class="clearfix">
							<h3 class="text-primary"></h3>
							<p class="text-danger">* Terms and Conditions apply</p>
					</div>
       </div>
       <div class="pricingchart-widget pricingchart-startup">
            
        <div class="">
            <div class="prichingHeader redHdr">
        <h5>START UP</h5>
            <div class="clearfix"></div>
           <div class="clearfix">
                <h4>&#36;<b id="startupMonthly">299</b></h4>
                <span id="startMonth">Per Month</span>
                <span id="startYear" style="display:none">Per Year</span>
           </div>
        </div>


           <div class="clearfix">
               <ul class="list-unstyled pricingchart-widget-list">
                    <li>Basic + HRMS, BGC<br/>with generic reports</li>
                    <li>Per Month / Year</li>
                   <li> Performance Management</li>
                    <li>Invoice management</li>
                   <li> On / Off Boarding</li>
                   <li>24 * 7 Support & assistance</li>
               </ul>
           </div>
           <div class="clearfix">
                <h3 class="text-primary">
                    15% Annual discount
                </h3>
                    <p class="text-danger">* Terms and Conditions apply</p>
            </div>
        </div>
        </div>
        <div class="pricingchart-widget lastWidget">
                <div class="prichingHeader blueHdr">
                <h5>BUSINES</h5>
                <div class="clearfix"></div>
           <div class="clearfix">
                <h4>&#36;<b id="businessMonthly">599</b></h4>
                <span id="businessMoth">Per Month</span>
                <span id="businessYear" style="display:none">Per Year</span>
           </div>
        </div>
           <div class="clearfix pricingchart-widget-list">
                <ul class="list-unstyled">
                        <li>Start Up + VMS, immigration,<br/>Merger & acquisition</li>
                        <li>Per Month / Year</li>
                        <li>Dedicated Account Manager</li>
                        <li>API Integrations</li>
                        <li>All reports</li>
                        <li>Completely Configurable</li>
                </ul>
            </div>
            <div class="clearfix">
                <h3 class="text-primary">
                    15% Annual discount
                </h3>
                    <p class="text-danger">* Terms and Conditions apply</p>
            </div>
            </div>
   </div>

<div class="clearfix mt"></div>

<div class="price-tablelist mt">
	<table class="table table-bordered table-striped mt" id="priceListTable">
		<thead>
			<tr>
				<th>Features</th>
				<th>Basic (3 Months)</th>
				<th>Start Up</th>
				<th>Business</th>
			</tr>
		</thead>
		<tbody>
				<tr>
					<td>Job Posting</td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr>
					<td>Resume parsing</td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr>
					<td>Configurable workflow</td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr>
					<td>Data Migration</td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr>
					<td>Time Sheets</td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr>
					<td>Candidates</td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr>
					<td>Mail Notifications</td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr>
					<td>Reports  <span class="text-danger">*</span></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr>
					<td>Dashboard</td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>Associates Role Configuration, Teams</td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>Jobs-Crowd Sourcing</td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>Clients</td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>Vendors</td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>Contracts-e-Signature</td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>On / Off Boarding - Documents request</td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>Projects - Internal, Client, Client Managed</td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>Time Sheets - configurable Sequential & Parallel approval mechanism</td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>Expenses</td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>Asset management</td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>Campaigns</td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>Leave Management - configurable approval mechanism, Holiday Calendar, Reminders</td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>PayRoll</td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>Immigration</td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>Performance Management</td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>Clock In / out</td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>Location Switch</td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>User Profile</td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>Ticket Management</td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>Mail Box (Configured)</td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>Job Board Integration</td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>Custom Dashboard</td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>Market Place</td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>Master Service provider</td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>Merges</td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>Acquisition</td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>Immigration Management</td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>API Integration</td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>24*7 Support</td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
				<tr><td>iOS & Andriod mobile Apps</td>
					<td><div class="pricelistUncheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
					<td><div class="pricelistCheckmark"></div></td>
				</tr>
		</tbody>
	</table>
</div>
</div>
</section>



<jsp:include page="../staticFooter.jsp" />
 <script type="text/javascript">
window.onload = function() {
	$("#releaseNoteID").addClass("slactive");
}
</script>
<script>
	$(document).ready(function() {
		$("#monthly").click(function(){
			$(this).addClass("pricebtnActive");
			$('#annullay').removeClass("pricebtnActive");
			$("#startupMonthly").text("299");
			$("#businessMonthly").text("599");
			$("#startMonth, #businessMoth").show();
			$("#startYear, #businessYear").hide();
		});
	});
	    
	$(document).ready(function() {
		$("#annullay").click(function(){
			$(this).addClass("pricebtnActive");
			$('#monthly').removeClass("pricebtnActive");
			$("#startupMonthly").text("3588");
			$("#businessMonthly").text("7188");
			$("#startMonth, #businessMoth").hide();
			$("#startYear, #businessYear").show();
		});
	});
</script>