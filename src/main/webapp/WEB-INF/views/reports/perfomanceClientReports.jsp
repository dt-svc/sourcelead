<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
$(document).ready(function(){
	getTopFiveConracts();
	getTopFiveReceivsblesReports();
	topTenClientReports();
	getTimeToFillReports();
});
</script>


<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Client(s) Report</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/performanceReportList'/>">Performance Reports</a> &raquo; Client(s) Report</div>
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
      <div class="col-md-2 dropdown-select positionRelative col-md-offset-9">
        <select class="form-control" id="dateFrequency" onchange="showCustomFields();">
          <option value="Last_7_days" selected>Last 7 days</option>
          <option value="Last_15_days">Last 15 days</option>
          <option value="1_month">1 month</option>
          <option value="3_months">3 months</option>
          <option value="Last_Year">Last year</option>
          <option value="Custom">Custom</option>
        </select>
        <!-- Top Leave Takers Custom Date -->
        <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="custom-select" style="display: none; width: 346px;">
          <div class="pull-left">
            <div class="pull-left mr5" style="width: 140px" id="custFromDateDiv">
              <input value="" name="Date" id="custFromDate" placeholder="From" class="form-control calendar input-sm" type="text">
            </div>
            <div class="pull-left mr5" style="width: 140px" id="custToDateDiv">
              <input value="" name="Date" id="custToDate" placeholder="To" class="form-control calendar input-sm" type="text">
            </div>
            <button type="button" class="btn btn-primary btn-sm" onclick="validateCustDates();"><i class="fa fa-search"></i></button>
          </div>
        </div>
        <!-- Top Leave Takers Custom Date --> 
      </div>
      
       <div class="col-md-1" id = "RunDivId">
        <button type="button" class="btn btn-primary btn-sm	mt2 pull-right" id="customize_btn" onclick="refreshWithNewFreq();">Run</button>
		</div>
<!--       <div class="col-md-1">
        <div class="btn-group bootstrap_dropdownMenu dropdownPadding">
          <button type="button" class="btn btn-primary btn-sm" title="Actions"><i class="fa fa-retweet"></i></button>
          <button type="button" class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown"> <span class="caret"></span> </button>
          <ul class="dropdown-menu clearfix  dropdown-menu-right">
            <li role="Export" class="mt5 cursorPointer"><i class="fa fa-file-pdf-o paddingRight"></i>Export</li>
           
                        <li role="Print" class="mt5 cursorPointer"><i class="fa fa-print paddingRight"></i>Print</li>
                        <li role="Download" class="mt5 cursorPointer"><i class="fa fa-download paddingRight"></i>Download</li>
                        <li role="Share" class="mt5 mb5 cursorPointer"><i class="fa fa-share-alt paddingRight"></i>Share</li>

          </ul>
        </div>
      </div> -->
    </div>
    <div class="GraView">
      <div class="content-area mt">
            <div class="clearfix mb"></div>
            <div class="clearfix mb row">
              <div class="col-md-6">
                <div class="projectDashboad clearfix positionRelative"><h4>Top 5 Receivables</h4>
                <div id="top5receivablesDiv" style="width:100%; height:250px"></div>
                  <div id="top5receivables" style="width:100%; height: 250px" class="amchartAnchor-Hide"></div>
                  
                  <ul class="list-unstyled list-inline vendor-listcount mt">
                        <li>
                            <label>Draft</label>
                            <span class="text-warning" id="draftTotalAmount">0</span>
                        </li>
                        <li>
                            <label>OverDue</label>
                            <span class="text-danger" id="unpaidTotalAmount">0</span>
                        </li>
                        <li>
                            <label>Open</label>
                            <span class="text-info" id="openTotalAmount">0</span>
                        </li>
                        <li>
                            <label>Paid</label>
                            <span class="text-success" id="paidTotalAmount">0</span>
                        </li>
                    </ul>
                </div>
              </div>
              <div class="col-md-6">
                <div class="projectDashboad clearfix positionRelative"><h4>Top 5 Engaged Contracts</h4>
                <div id="engagedContractsDiv" style="width:100%; height:250px"></div>
                  <div id="engagedContracts" style="width:100%; height:250px" class="amchartAnchor-Hide"></div>
                  <ul class="list-unstyled list-inline vendor-listcount mt">
                        <li>
                            <label>MSA</label>
                            <span class="" id="msaCount">0</span>
                        </li>
                        <li>
                            <label>SOW</label>
                            <span class="" id="sowCount">0</span>
                        </li>
                        <li>
                            <label>NDA</label>
                            <span class="" id="ndaCount">0</span>
                        </li>
                        <li>
                            <label>NSA</label>
                            <span class="" id="nsaCount">0</span>
                        </li>
                    </ul>
                </div>
              </div>
            </div>
            <div class="clearfix mt mb row">
              <div class="col-md-6">
                <div class="projectDashboad clearfix positionRelative"><h4>Average Time to Fill</h4>
                  <div id="timeToFill" style="width:100%; height: 250px" class="amchartAnchor-Hide"></div>
                </div>
              </div>
              <div class="col-md-6">
                <div class="projectDashboad clearfix positionRelative"><h4>Success Percentage</h4>
                  <div id="successPercentage" style="width:100%; height: 250px" class="amchartAnchor-Hide"></div>
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
                <h4 class="col-md-8" id="vr">Top 10 Clients Billing Report</h4>
                <h4 class="col-md-8" style="display: none;" id="cr">Contact Report</h4>
                <div class="select-fild col-md-2">
                  <select id="topClientList" onchange="changeTopTenList();">
                    <option value="byClient">By Client</option>
                    <option value="byClientContact">By Contact</option>
                  </select>
                </div>
                <div class="col-md-2" id="clientSelectDiv">
                  <select class="chosen-select" data-placeholder="Select Client" id="topTenClientSelect" onchange="clientSummary()"></select>
                </div>
                <div class="col-md-2" id="clientContactSelectDiv" style="display: none;">
                  <select class="chosen-select" data-placeholder="Select Contact" id="topTenClientContactSelect" onchange="clientSummary()"></select>
                </div>
              </div>
              <div id="topClientloadingDiv"></div>
              <div class="require_wrap_table" id="topTenClientList"></div>
              <div class="require_wrap_table" id="topTenClientContactList"></div>
              
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

<script>
function changeTopTenList(){
    var selectedList = $("#topClientList").val();
    if(selectedList == "byClient"){
        topTenClientReports();
    }else if(selectedList == "byClientContact"){
        topTenClientContactReports();
    }
}
</script>
  
  <!-- Top5 Vendor Expenses Report AmChart --> 
<script>
function showCustomFields(){
    var frequency = $("#dateFrequency").val();
    if(frequency == "Custom"){
        $("#custom-select").show();
         $('#custFromDate').attr('data-validation',"required");
         $('#custToDate').attr('data-validation',"required");
    }else{
        $("#custom-select").hide();
        $('#custFromDate').removeAttr('data-validation');
        $('#custToDate').attr('data-validation',"required");
    }
    
    var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
      var finalDateFormatEndDate = '';
      if(locationDate.match("yyyy-MM-dd")){
          finalDateFormatEndDate = 'yy-mm-dd';
      }if(locationDate.match("dd-MM-yyyy")){
          finalDateFormatEndDate = 'dd-mm-yy'; 
      }if(locationDate.match("MM-dd-yyyy")){
          finalDateFormatEndDate = 'mm-dd-yy'; 
      }if(locationDate.match("yyyy-dd-MM")){
          finalDateFormatEndDate = 'yy-dd-mm'; 
      }

      $( "#custFromDate" ).datepicker({
           dateFormat : finalDateFormatEndDate,
            changeMonth: true,
            changeYear: true,
            maxDate: new Date() ,
            onSelect : function(selected) {
            var dt = $(this).datepicker('getDate', '+1d');
            dt.setDate(dt.getDate());
            $("#custToDate").datepicker("option", "minDate", dt);
             removeStartValid();
            } 
      
      });
      
      $( "#custToDate" ).datepicker({
           dateFormat : finalDateFormatEndDate,
            changeMonth: true,
            changeYear: true,
          maxDate: new Date() ,
      onSelect : function(selected) {
            var dt = $(this).datepicker('getDate', '+1d');
            dt.setDate(dt.getDate());
            $("#custFromDate").datepicker("option", "maxDate", dt);
             removeEndValid();
            }});
}

function removeStartValid(){
    $('#custFromDateDiv').find('p.jquery_form_error_message').remove();
}
function removeEndValid(){
    $('#custToDateDiv').find('p.jquery_form_error_message').remove();
}

function validateCustDates(){
    var frequency = $("#dateFrequency").val();
    var fromDate = $("#custFromDate").val();
    var toDate = $("#custToDate").val();
    if(frequency == "Custom"){
        if(fromDate == ''){
            $('#custFromDateDiv').find('p.jquery_form_error_message').remove();
            $("input#custFromDate").after( "<p class='jquery_form_error_message'>FromDate is required.</span>");
        }
        if(toDate == ''){
            $('#custToDateDiv').find('p.jquery_form_error_message').remove();
            $("input#custToDate").after( "<p class='jquery_form_error_message'>ToDate is required.</span>");
        }
        if((fromDate != '' && fromDate != 'undefined' && fromDate != null) && (toDate != '' && toDate != 'undefined' && toDate != null)){
            $('#custFromDateDiv').find('p.jquery_form_error_message').remove();
            $('#custToDateDiv').find('p.jquery_form_error_message').remove();
            refreshWithNewFreq();
        }
    }else{
        $('#custFromDateDiv').find('p.jquery_form_error_message').remove();
        $('#custToDateDiv').find('p.jquery_form_error_message').remove();
        refreshWithNewFreq();    
    } 
}
</script>

<script>
function refreshWithNewFreq(){
    getTopFiveConracts();
    getTopFiveReceivsblesReports();
    getTimeToFillReports();
    topTenClientReports();
}
</script>


<!-- engagedContracts Report AmChart --> 
<script type="text/javascript">
function prepareContractChart(client1,client2,client3,client4,client5,client1Count,client2Count,client3Count,client4Count,client5Count,clientOthersCount,clientOthers){
    
    var mainChar = [];
    if(client1Count>0){
    mainChar.push( {
        "category": client1,
        "column-1": client1Count,
        "color": "#26A69A"
       } );
    }
    
    if(client2Count>0){
        mainChar.push( {
            "category": client2,
            "column-1": client2Count,
            "color": "#26C6DA"
           } );
        }
    
    if(client3Count>0){
        mainChar.push( {
            "category": client3,
            "column-1": client3Count,
            "color": "#9CCC65"
           } );
        }
    
    if(client4Count>0){
        mainChar.push( {
            "category": client4,
            "column-1": client4Count,
            "color": "#8D6E63"
           } );
        }
    
    if(client5Count>0){
        mainChar.push( {
            "category": client5,
            "column-1": client5Count,
            "color": "#607D8B"
           } );
        }
    
    if(clientOthersCount>0){
        mainChar.push( {
            "category": "Others",
            "column-1": clientOthersCount,
            "color": "#FFD54F"
           } );
        }
    
    
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
//             "allLabels": [
//                 {
//                     "bold": true,
//                     "color": "#3F51B5",
//                     "id": "Label-1",
//                     "size": 15,
//                      "text": "Top 5 Engaged Contracts",
//                     "x": "5%",
//                     "y": "5%"
//                 }
//             ],
//            "balloon": {},
            "legend": {
                "enabled": true,
                "position": "left"
            },
            "titles": [],
            "dataProvider": mainChar
            }
);
}
</script> 
        

<!-- amCharts javascript code --> 


<script>
function getTopFiveConracts(){
    var frequency = $("#dateFrequency").val();
    var fromDate = $("#custFromDate").val();
    var toDate = $("#custToDate").val();
    var result = '';
    if(frequency != "Custom" || ((fromDate != null && fromDate != '' && fromDate != 'undefined')) && (toDate != null && toDate != '' && toDate != 'undefined')){
        $.get(sourceleadUrl+ "/getTopFiveConracts?frequency="+frequency+"&fromDate="+fromDate+"&toDate="+toDate+"&templateSubmissionStatus='FINALIZED'", function(data) {
            if(data != null && data != '' && data != 'undefined'){
                var client1 = "";
                var client2 = "";
                var client3 = "";
                var client4 = "";
                var client5 = "";
                var clientOthers = "";
                var client1Count = 0;
                var client2Count = 0;
                var client3Count = 0;
                var client4Count = 0;
                var client5Count = 0;
                var clientOthersCount = 0;
                if(data.length != 1){
                    for(i=0; i<data.length-1; i++){
                        var opt = data[i];
                            if(i == 0){
                                client1 = opt.clientName;
                                client1Count = opt.contractsCount;
                            }else if(i == 1){
                                client2 = opt.clientName;
                                client2Count = opt.contractsCount;
                            }else if(i == 2){
                                client3 = opt.clientName;
                                client3Count = opt.contractsCount;
                            }else if(i == 3){
                                client4 = opt.clientName;
                                client4Count = opt.contractsCount;
                            }else if(i == 4){
                                client5 = opt.clientName;
                                client5Count = opt.contractsCount;
                            }else{
                                clientOthers = opt.clientName;
                                clientOthersCount += opt.contractsCount;
                            }
                        }
                    $("#engagedContracts").show();
                    $("#engagedContractsDiv").hide();
                }else{
                	result = result + '<div class="dataTables_empty center" ></div>';
                	$("#engagedContractsDiv").show();
                	$("#engagedContractsDiv").empty();
                	$("#engagedContractsDiv").append(result);
                	$("#engagedContracts").hide();
                }
                var report = data[data.length-1];
                $("#msaCount").html(report.msaCount);
                $("#sowCount").html(report.sowCount);
                $("#nsaCount").html(report.nsaCount);
                $("#ndaCount").html(report.ndaCount);
                    
                prepareContractChart(client1,client2,client3,client4,client5,client1Count,client2Count,client3Count,client4Count,client5Count,clientOthersCount,clientOthers);
            }else{
            	result = result + '<div class="dataTables_empty center" ></div>';
            	$("#engagedContractsDiv").append(result);
            	$("#engagedContracts").hide();
            }
        });
    }
}


function getTopFiveReceivsblesReports(){
    var frequency = $("#dateFrequency").val();
    var fromDate = $("#custFromDate").val();
    var toDate = $("#custToDate").val();
    $.ajax({
        type : "GET",
        url : sourceleadUrl+"/getTopFiveReceivablesReports?&dateFrequency="+frequency+"&fromDate="+fromDate+"&toDate="+toDate+"&type=Receivable",
        success: function(data) {
            if(data.draftInvoiceTotal != null && data.draftInvoiceTotal != 'undefined' && data.draftInvoiceTotal != ''){
            	$("#draftTotalAmount").html(data.draftInvoiceTotal)
            }else{
            	$("#draftTotalAmount").html(0)
            }
            if(data.overDueInvoicetotal != null && data.overDueInvoicetotal != 'undefined' && data.overDueInvoicetotal != ''){
            	$("#unpaidTotalAmount").html(data.overDueInvoicetotal)
            }else{
            	$("#unpaidTotalAmount").html(0)
            }
            if(data.allPaidInvoiceTotal != null && data.allPaidInvoiceTotal != 'undefined' && data.allPaidInvoiceTotal != ''){
            	$("#paidTotalAmount").html(data.allPaidInvoiceTotal)
            }else{
            	$("#paidTotalAmount").html(0)
            }
            if(data.openInvoiceTotal != null && data.openInvoiceTotal != 'undefined' && data.openInvoiceTotal != ''){
            	$("#openTotalAmount").html(data.openInvoiceTotal)
            }else{
            	$("#openTotalAmount").html(0)
            }
            getAmchartData(data);
         }
    });
}
function getAmchartData(data){
    var clientName = '';
    var amount = '';
    var colors = ["#7E57C2", "#5C6BC0", "#64B5F6", "#81C784", "#FFB74D", "#FF7043"];
    var i = 0;
    var mainAmcahrt = [];
    if(data.newAmountList != null){
    	 for(var k in data.newAmountList) {
    	        mainAmcahrt[i] = {"category": k, "column-1": data.newAmountList[k].toFixed(2), "color": colors[i++] };
    	    }
    	 $("#top5receivables").show();
    	 $("#top5receivablesDiv").hide();
    }else{
    	var result = '';
    	result = result + '<div class="dataTables_empty center" ></div>';
    	 $("#top5receivablesDiv").show();
    	 $("#top5receivablesDiv").empty();
    	 $("#top5receivablesDiv").append(result);
    	 $("#top5receivables").hide();
    }
   
    if(data.othersAmountList != null && data.othersAmountList != '' && data.othersAmountList != 'undefined'){
        for(var k in data.othersAmountList) {
            mainAmcahrt[i] = {"category": k, "column-1": data.othersAmountList[k].toFixed(2), "color": colors[i++] };
        }
    }
    
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
//                 "allLabels": [
//                     {
//                         "bold": true,
//                         "color": "#CC0000",
//                         "id": "Label-1",
//                         "size": 15,
//                         "text": "Top 5 Receivables",
//                         "x": "5%",
//                         "y": "5%"
//                     }
//                 ],
//                "balloon": {},
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
                "dataProvider": mainAmcahrt
            }
        );
}
</script>

<script>
function topTenClientReports(){
    var frequency = $("#dateFrequency").val();
    var fromDate = $("#custFromDate").val();
    var toDate = $("#custToDate").val();
    if(frequency != "Custom" || ((fromDate != null && fromDate != '' && fromDate != 'undefined')) && (toDate != null && toDate != '' && toDate != 'undefined')){
        $("#topClientloadingDiv").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
        $.ajax({
            type : "GET",
            url : sourceleadUrl+"/getTopTenClientReports?&frequency="+frequency+"&fromDate="+fromDate+"&toDate="+toDate+"&templateSubmissionStatus=FINALIZED",
            success: function(data) {
            	var result = "";
                if(data.length > 0){
                    
                    result += '<table>';
                    result += '<thead>';
                    result += '  <tr>';
                    result += '    <th>Client Name</th>';
                    result += '    <th>No. of Contacts</th>';
                    result += '    <th>Total Jobs Released</th>';
                    result += '    <th>Submission</th>';
                    result += '    <th>Hired</th>';
                    result += '    <th>Avg.TTF</th>';
                    result += '    <th>Total Contracts</th>';
                    result += '    <th>Billing</th>';
                    result += '  </tr>';
                    result += '</thead>';
                    result += '<tbody>';
                    var listLength;
                    if(data.length > 10){
                        listLength = 10;
                    }else{
                        listLength = data.length;
                    }
                    for(i=0; i<listLength; i++){
                        var opt = data[i];
                        result += '  <tr>';
                        result += '    <td><div class="tbldata-dotdot"><a href="'+sourceleadUrl+'/clientReportSummary?&orgRelationUUID='+opt.orgRelationUUID+'&invoiceType=Receivable&type=byClient">'+opt.clientName+'</a> </div></td>';
                        result += '    <td><div class="tblminwidth-30">'+opt.noOfContacts+'</div></td>';
                        result += '    <td><div class="tblminwidth-30">'+opt.releasedJobs+'</div></td>';
                        result += '    <td><div class="tblminwidth-30"> <strong class="">'+opt.submissionCount+'</strong></div></td>';
                        result += '    <td><div class="tblminwidth-30"> <strong class="">'+opt.hiredCount+'</strong></div></td>';
                        result += '    <td><div class="tblminwidth-30">10 days</div></td>';
                        result += '    <td><div class="tbldata-dotdot-vsm">'+opt.totalContracts+'</div></td>';
                        result += '    <td><div class="tbldata-dotdot-vsm">'+opt.totalPaidAmount+'</div></td>';
                        result += '  </tr>';
                    }
                    
                    result += '</tbody>';
                       result += '</table>';
                       
                       var opt = "";
                       opt += '<option value="">Select Client</option>';
                       for(i=0; i<data.length; i++){
                           report = data[i];
                           opt += '<option value='+report.orgRelationUUID+'>'+report.clientName+'</option>';
                       }

                       $("#topTenClientList").empty();
                       $("#topTenClientList").show();
                       $("#topTenClientContactList").hide();
                       $("#topClientloadingDiv").removeAttr('style');
                       $("#topTenClientList").append(result);
                    $("#topTenClientSelect").empty();
                    $("#topTenClientSelect").append(opt);
                    $("#clientSelectDiv").show();
                    $("#clientContactSelectDiv").hide();
                    $("#topTenClientSelect").trigger("chosen:updated");
                }else{
                	result = result + '<div class="dataTables_empty center" ></div>';
                	$("#topTenClientList").empty();
                    $("#topTenClientList").show();
                    $("#topTenClientContactList").hide();
                    $("#topClientloadingDiv").removeAttr('style');
                    $("#topTenClientList").append(result);
                    
                    $("#topTenClientSelect").empty();
                    $("#topTenClientSelect").trigger("chosen:updated");
             }
            }
        });
    }
    
}
</script>

<script>
function getTimeToFillReports(){
    var frequency = $("#dateFrequency").val();
    var fromDate = $("#custFromDate").val();
    var toDate = $("#custToDate").val();
    if(frequency != "Custom" || ((fromDate != null && fromDate != '' && fromDate != 'undefined')) && (toDate != null && toDate != '' && toDate != 'undefined')){
//         $("#topClientloadingDiv").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
    $.ajax({
        type : "POST",
        url : sourceleadUrl+"/getAllJobsPipelineReoportsList?&dateSelection="+frequency+"&fromDate="+fromDate+"&toDate="+toDate+"&fromtype=clientReport",
        success: function(data) {
            getTimeToFillAmchartData(data);
            getSuccessPercentageAmchartData(data);
         }
    });
    }
}

</script>

<script>
function getTimeToFillAmchartData(data){
    
    AmCharts.makeChart("timeToFill",
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
// "titles": [
// {
//     "id": "Title-1",
//     "text": "Average Time to Fill"
// }
// ],
"dataProvider": [
{
    "category": "Applicants",
    "column-1": data.object.applicantsDays,
    "color": "#80CBC4"
},
{
    "category": "Shortlisted",
    "column-1": data.object.shortlistedDays,
    "color": "#FFB74D"
},
{
    "category": "Submit to Client",
    "column-1": data.object.submitToClientDays,
    "color": "#9575CD"
},
{
    "category": "Interviewed",
    "column-1": data.object.interviewsDays,
    "color": "#4DD0E1"
},
{
    "category": "Offered",
    "column-1": data.object.offeredDays,
    "color": "#AED581"
},
{
    "category": "Joined",
    "column-1": data.object.offeredAcceptedDays,
    "color": "#81D4FA"
}
]
}
    );
}
</script>        

<script>
function getSuccessPercentageAmchartData(data){
    
    AmCharts.makeChart("successPercentage",
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
                    "labelsEnabled": false,
                    "axisThickness": 0,
                    "gridThickness": 0,
                    "totalText": "",
                    "unit": "",
                    "title": ""
                }
            ],
            "allLabels": [],
            "balloon": {},
//             "titles": [
//                 {
//                     "id": "Title-1",
//                     "size": 15,
//                     "text": "Success Percentage"
//                 }
//             ],
            "dataProvider": [
                {
                    "category": "Applicants",
                    "column-1": data.object.applicantsMove,
                    "color": "#9575CD"
                },
                {
                    "category": "Shortlisted",
                    "color": "#9575CD",
                    "column-1": data.object.shortlistedMove,
                },
                {
                    "category": "Submit to Client",
                    "column-1": data.object.submitToClientMove,
                    "color": "#FFB74D"
                },
                {
                    "category": "Interviewed",
                    "column-1": data.object.interviewsMove,
                    "color": "#5C6BC0"
                },
                {
                    "category": "Offered",
                    "column-1": data.object.offeredMove,
                    "color": "#9CCC65"
                },
                {
                    "category": "Joined",
                    "column-1": data.object.offeredAcceptedMove,
                    "color": "#1E88E5"
                }
            ]
        }
    );
}
</script>

<script>
function topTenClientContactReports(){
    var frequency = $("#dateFrequency").val();
    var fromDate = $("#custFromDate").val();
    var toDate = $("#custToDate").val();
    if(frequency != "Custom" || ((fromDate != null && fromDate != '' && fromDate != 'undefined')) && (toDate != null && toDate != '' && toDate != 'undefined')){
        $("#topClientloadingDiv").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
        $.ajax({
            type : "GET",
            url : sourceleadUrl+"/getTopTenClientContactReports?&frequency="+frequency+"&fromDate="+fromDate+"&toDate="+toDate+"&templateSubmissionStatus=FINALIZED",
            success: function(data) {
            	var result = "";
                if(data.length > 0){
                    result += '<table>';
                    result += '<thead>';
                    result += '  <tr>';
                    result += '    <th>Contact Name</th>';
                    result += '    <th>Client Name</th>';
                    result += '    <th>Total Jobs Released</th>';
                    result += '    <th>Submission</th>';
                    result += '    <th>Hired</th>';
                    result += '    <th>Avg.TTF</th>';
                    result += '    <th>Total Contracts</th>';
                    result += '    <th>Billing</th>';
                    result += '  </tr>';
                    result += '</thead>';
                    result += '<tbody>';
                    var listLength;
                    if(data.length > 10){
                        listLength = 10;
                    }else{
                        listLength = data.length;
                    }
                    for(i=0; i<listLength; i++){
                        var opt = data[i];
                        result += '  <tr>';
                        result += '    <td><div class="tbldata-dotdot"><a href="'+sourceleadUrl+'/clientReportSummary?&orgSpocUUID='+opt.orgSpocUUID+'&invoiceType=Receivable&type=byContact">'+opt.clientContactName+'</a> </div></td>';
                        result += '    <td><div class="tblminwidth-30">'+opt.clientName+'</div></td>';
                        result += '    <td><div class="tblminwidth-30">'+opt.releasedJobs+'</div></td>';
                        result += '    <td><div class="tblminwidth-30"> <strong class="">'+opt.submissionCount+'</strong></div></td>';
                        result += '    <td><div class="tblminwidth-30"> <strong class="">'+opt.hiredCount+'</strong></div></td>';
                        result += '    <td><div class="tblminwidth-30">10 days</div></td>';
                        result += '    <td><div class="tbldata-dotdot-vsm">'+opt.totalContracts+'</div></td>';
                        result += '    <td><div class="tbldata-dotdot-vsm">'+opt.totalPaidAmount+'</div></td>';
                        result += '  </tr>';
                    }
                    
                    result += '</tbody>';
                       result += '</table>';
                       
                       var opt = "";
                       opt += '<option value="">Select Client Contact</option>';
                       for(i=0; i<data.length; i++){
                           repo = data[i];
                           opt += '<option value='+repo.orgSpocUUID+'>'+repo.clientContactName+'</option>';
                       }
                       
                       $("#topTenClientContactList").empty();
                       $("#topTenClientContactList").show();
                       $("#topTenClientList").hide();
                       $("#topClientloadingDiv").removeAttr('style');
                       $("#topTenClientContactList").append(result);
                       $("#topTenClientContactSelect").empty();
                       $("#topTenClientContactSelect").append(opt);
                       $("#clientSelectDiv").hide();
                    $("#clientContactSelectDiv").show();
                       $("#topTenClientContactSelect").trigger("chosen:updated");
                }else{
                	result = result + '<div class="dataTables_empty center" ></div>';
                	$("#topTenClientContactList").empty();
                    $("#topTenClientContactList").show();
                    $("#topTenClientList").hide();
                    $("#topClientloadingDiv").removeAttr('style');
                    $("#topTenClientContactList").append(result);
                    
                    $("#topTenClientContactSelect").empty();
                    $("#topTenClientContactSelect").trigger("chosen:updated");
                }
             }
        });
    }
}
</script>
<script>
function clientSummary(){
    var orgRelationUUID = $("#topTenClientSelect").val();
    var spocUUID = $("#topTenClientContactSelect").val();
    if(orgRelationUUID != null && orgRelationUUID != '' && orgRelationUUID != 'undefined'){
     location.replace(sourceleadUrl+"/clientReportSummary?orgRelationUUID="+orgRelationUUID+"&invoiceType=Receivable&type=byClient");
    }else if(spocUUID != null && spocUUID != '' && spocUUID != 'undefined'){
     location.replace(sourceleadUrl+"/clientReportSummary?orgSpocUUID="+spocUUID+"&invoiceType=Receivable&type=byContact");
    }
}
</script>

<jsp:include page="../masterFooter.jsp" />