var orgRelationId = 0, contactUserId = 0;
$(document).ready(function() {
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
	$("#cd").click(function(){
		$(".clientDetails").toggleClass('hidden', 500);
	});
	$('#custom-dates').change(function(){
		var optval = $(this).val();
		if(optval === "custom") {
			$("#custom-select").show();
		} else {
			$("#custom-select").hide();
		}
	});
	
	$("#listView").click(function() {
		$("#listView").addClass("rAct");
		$("#graphView").removeClass("rAct");
		
		$("#listViewFilters").show();
		
		$("#vendorReportsDiv").show();
		$("#vendorReportsChartDiv").hide();
		$("#releasedJobsResultDiv").hide();
		$('#releasedApplicantsDeclineDiv').hide();
		$('#releasedApplicantsBackOutDiv').hide();
		$('#releasedApplicantsHiredListDiv').hide();
		getVendorsReportList();
	});
	$("#graphView").click(function() {
		$("#graphView").addClass("rAct");
		$("#listView").removeClass("rAct");
		
		$("#listViewFilters").hide();
		$("#releasedJobsResultDiv").hide();
		$('#releasedApplicantsDeclineDiv').hide();
		$('#releasedApplicantsBackOutDiv').hide();
		$("#vendorReportsChartDiv").show();
		$("#vendorReportsDiv").hide();
		$('#releasedApplicantsHiredListDiv').hide();
		
		getVendorsReportGraphData();
	});
	
	$("#getVendorPerformance").click(function() {
		orgRelationId = $("#vendorList").val() != null ? $("#vendorList").val() : 0;
		contactUserId = $("#contactList").val() != null ? $("#contactList").val() : 0;
		if(orgRelationId > 0) {
			getVendorsReportGraphData();
		} else {
			alert("Please select Vendor");
		}
	});
	
//	$("#graphView").click();
	getVendorsList();
});

function getVendorsList() {
	$.ajax({
		type : "POST",
		url : sourceleadUrl + "/getOrgRelationList",
		success : function(response) {
			if (response.response === "AWKWARD") {
                location.href = "/errorPage";
            } else if (response.response === "SUCCESS") {
            	$("#vendorList").empty();
            	$("#vendorList").append("<option value=''>Select Vendor</option>");
            	if(response && response.data.length > 0) {
    				for(var e in response.data) {
    					$("#vendorList").append("<option value='"+response.data[e].id+"'>" + response.data[e].orgName + "</option>");
    				}
    			}            	
            	$('#vendorList').trigger("chosen:updated");
            } else if (response.response === "SESSION_TIMEDOUT" || response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
                showError("Operation failed, please try again");
            }
		},
	}); 
}

function getContactList() {
	var orgRelationId = $("#vendorList").val();
	$("#contactList").empty();
	if(orgRelationId !== "") {
		$.ajax({
			type : "POST",
			url : sourceleadUrl + "/getContactListByOrgRelationId?orgRelationId=" + orgRelationId,
			success : function(response) {
				if (response.response === "AWKWARD") {
	                location.href = "/errorPage";
	            } else if (response.response === "SUCCESS") {
	            	if(response && response.data.length > 0) {
	            		$("#contactList").append("<option value='0'>Select Contact</option>");
	    				for(var e in response.data) {
	    					$("#contactList").append("<option value='"+response.data[e].id+"'>" + response.data[e].ownerName + "(" +response.data[e].spocName + ")</option>");
	    				}
	    			}            	
	            	$('#contactList').trigger("chosen:updated");
	            } else if (response.response === "SESSION_TIMEDOUT" || response.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
	                showError("Operation failed, please try again");
	            }
			},
		});
	} else {
		$('#contactList').trigger("chosen:updated");
	}
}

function getVendorsReportGraphData() {
	if(orgRelationId > 0) {
		getReleasedJobsGraph();
		
		}
}

// Released Jobs
function getReleasedJobsGraph() {
	$("#releaseJobsloadingDiv").show();
	$("#releaseJobs").hide();
	$("#releaseJobsNoRecordsDiv").hide();
	$("#releasedJobsCount").html(0);
	$.ajax({
		type : "POST",
		url : sourceleadUrl + "/getVendorJobsForReleasedJobReport?orgRelationId=" + orgRelationId + "&spocId=" + contactUserId,
		success : function(response) {
			if (response.response === "AWKWARD") {
                location.href = "/errorPage";
            } else if (response.response === "SUCCESS") {
            	var openCount = response.object.openRequirementsCount;
            	var closedCount = response.object.closedRequirementsCount;
            	var cancelledCount = response.object.cancelRequirementsCount;
            	var onHoldCount = response.object.onholdRequirementsCount;
            	var totalReleasedCount = totalReleasedCount = openCount + closedCount + cancelledCount + onHoldCount;
            	if(totalReleasedCount > 0) {
            		AmCharts.makeChart("releaseJobs", {
            			"type": "pie",
            			"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
            			"labelText": "",
            			"minRadius": 20,
            			"pieX": "50%",
            			"pieY": "50%",
            			"radius": "45%",
            			"colorField": "color",
            			"pullOutEffect": "elastic",
            			"pullOutOnlyOne": true,
            			"startEffect": "elastic",
            			"titleField": "category",
            			"valueField": "column-1",
            			"allLabels": [],
            			"balloon": {},
            			"legend": {
            				"enabled": true,
            				"align": "center",
            				"autoMargins": false,
            				"fontSize": 14,
            				"horizontalGap": 10,
            				"markerLabelGap": 10,
            				"markerSize": 15,
            				"position": "right",
            				"useMarkerColorForLabels": false
            			},
            			"titles": [],
            			"dataProvider": [
            				{
            					"category": "Open",
            					"column-1": openCount,
            					"color": "#A5D6A7"
            				},
            				{
            					"category": "Closed",
            					"column-1": closedCount,
            					"color": "#ef9a9a"
            				},
            				{
            					"category": "Cancelled",
            					"column-1": cancelledCount,
            					"color": "#FFB74D"
            				},
            				{
            					"category": "On Hold",
            					"column-1": onHoldCount,
            					"color": "#4FC3F7"
            				}
            			]
            		});
            		$("#releasedJobsCount").html(totalReleasedCount);
            		$("#releaseJobs").show();
            		getOfferAcceptanceRateGraph();
            		getAveragePercentageMovingForwardGraph();
            		getAverageTimeToFillGraph();
            		getQualityCandidatesGraph();
            		getApplicantDeclineGraph();
            		getApplicantBackoutGraph();
					getAverageTimeToFillGraph();
            	} else {
            		$("#releaseJobsNoRecordsDiv").show();
					$('#releasedJobsResultDiv').hide();
					
					$("#avgPercentMovingFwdNoRecordsDiv").show();
					$("#avgPercentMovingFwdChartDiv").hide();
					
					$("#qualityCandidatesNoRecordsDiv").show();
					$("#qualityCandidatesChartDiv").hide();
					
					$("#applicantsDeclineCount").html(0);
					$("#applicantDeclineNoRecordsDiv").show();
					$("#applicantDeclineChartDiv").hide();
					
					$("#backoutCandidatesCount").html(0);
					$("#backoutCandidatesNoRecordsDiv").show();
					$("#backoutCandidatesChartDiv").hide();
					
					$("#offerAcceptanceRateNoRecordsDiv").show();
					$("#offerAcceptanceRateChartDiv").hide();
					
					$("#totalReceivedCandidates").html(0);
					$("#hiredReceivedCandidates").html(0);
					$("#rejectedReceivedCandidates").html(0);
					
					
            	}
        		$("#releaseJobsloadingDiv").hide();
            } else if (response.response === "SESSION_TIMEDOUT" || response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
                showError("Operation failed, please try again");
            }
		},
	});
}
// released jobs List
function releasedJobsToVendors() {
	if($("#releasedJobsCount").html() != "" && parseInt($("#releasedJobsCount").html()) > 0) {
		$("#releasedJobsResultDiv").show();
		$("#vendorReportsChartDiv").hide();
		$("#vendorReportsDiv").hide();
		$.ajax({
			type : "POST",
			url : sourceleadUrl + "/releasedJobsListByOrgRelationId?orgRelationId="+orgRelationId+"&spocId="+contactUserId,
			success :function(result) {
	       		$('#releasedJobsResultDiv').empty();
	       		$('#releasedJobsResultDiv').append(result);
	       		releasedToVendorJobsList();
	        }
		});	
	}
}
// Offer acceptance rate
function getOfferAcceptanceRateGraph() {
	$("#offerAcceptanceRateloadingDiv").show();
	$("#offerAcceptanceRateNoRecordsDiv").hide();
	$("#offerAcceptanceRateChartDiv").hide();

	var filteredDate = $("#filterDate").val();
	var filterType = $("#filterDate :selected").data("type");
	var filterValue = $("#filterDate :selected").data("name");
	
	$.ajax({
		type : "POST",
		url : sourceleadUrl + "/offerAcceptanceRateListForVendor?orgRelationId=" + orgRelationId + "&spocId=" + contactUserId +"&filteredDate=" + filteredDate + "&filterType=" + filterType,
		success : function(response) {
			if (response.response === "AWKWARD") {
                location.href = "/errorPage";
            } else if (response.response === "SUCCESS") {
            	if(response.data !== null && response.data !== "" && response.data.length > 0) {
            		var reportData = [];
            		var totalOfferCount=0, totalAcceptedCount=0, totalRejectedCount=0;
        			for(var e in response.data) {
        				totalOfferCount += response.data[e].offerCount;
        				totalAcceptedCount += response.data[e].acceptedCount;
        				totalRejectedCount += response.data[e].rejectedCount;
        				reportData.push({
        					"date" : response.data[e].offerDate,
        					"offerRejected" : response.data[e].rejectedCount,
        					"offerMade" : response.data[e].offerCount,
        					"offerAccepted" : response.data[e].acceptedCount
        				});
        			}
        			
        			$("#oarCharttotalOfferCount").html(totalOfferCount);
        			$("#oarCharttotalAcceptedCount").html(totalAcceptedCount);
        			$("#oarCharttotalRejectedCount").html(totalRejectedCount);
        			
        			var title = (response.responseMessage === "month" ? "Monthly" : response.responseMessage === "year" ? "Yearly" : "Year(s) Comparison") + " Graph" + (filterValue !== "" ? " for " + filterValue : "");
        			var dateFormat = response.responseMessage === "month" ? "YYYY-MM-DD" : response.responseMessage === "year" ? "YYYY-MM" : "YYYY";
        			
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
        				"dataProvider" : reportData
        			});
            		$("#offerAcceptanceRateChartDiv").show();
            	} else {
            		$("#offerAcceptanceRateNoRecordsDiv").show();
            	}
            	$("#offerAcceptanceRateloadingDiv").hide();         	
            } else if (response.response === "SESSION_TIMEDOUT" || response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
                showError("Operation failed, please try again");
            }
		},
	});
}

function getAveragePercentageMovingForwardGraph() {
	$("#avgPercentMovingFwdloadingDiv").show();
	$("#avgPercentMovingFwdNoRecordsDiv").hide();
	$("#avgPercentMovingFwdChartDiv").hide();
	$.ajax({
		type : "POST",
		url : sourceleadUrl + "/getAllJobsPipelineReoportsList?orgRelationId=" + orgRelationId + "&spocId=" + contactUserId,
		success : function(response) {
			if (response.response === "AWKWARD") {
                location.href = "/errorPage";
            } else if (response.response === "SUCCESS") {
            	if(response.object && response.object !== null) {
            		var mainChar = [];
            		var hasResults = false;
                	if(response.object.applicantsCount>0){
                		hasResults = true;
                		mainChar.push( {
                			"category": "Applicant(s)",
                			"column-1": response.object.applicantsCount,
                			"Color": "#9575CD",
                			"Days": response.object.applicantsDays,
                			"moveforward": "100"
                		    } );
                	}
                	
                	if(response.object.shortlistedCount>0){
                		hasResults = true;
                		mainChar.push( {
                			"category": "Shortlisted",
                			"column-1": response.object.shortlistedCount,
                			"Color": "#90CAF9",
                			"Days": response.object.shortlistedDays,
                			"moveforward": response.object.shortlistedMove
                		    } );
                	}
                	
                	if(response.object.submitToClientCount>0){
                		hasResults = true;
                		mainChar.push( {
                			"category": "Submit To Client",
                			"column-1": response.object.submitToClientCount,
                			"Color": "#FFB74D",
                			"Days": response.object.submitToClientDays,
                			"moveforward": response.object.submitToClientMove
                		    } );
                	}
                	if(response.object.interviewsCount>0){
                		hasResults = true;
                		mainChar.push( {
                			"category": "Interviewed",
                		    "column-1": response.object.interviewsCount,
                		    "Color": "#5C6BC0",
                		    "Days": response.object.interviewsDays,
                		    "moveforward": response.object.interviewsMove
                		} );
                	}
                	
                	if(response.object.offeredCount>0){
                		hasResults = true;
                		mainChar.push({
                			"category": "Offered",
                			"column-1": response.object.offeredCount,
                			"Color": "#9CCC65",
                			"Days": response.object.offeredDays,
                			"moveforward": response.object.offeredMove
                		});
                	}
                	if(response.object.offeredAcceptedCount>0){
                		hasResults = true;
                		mainChar.push({
                			"category": "Offers Accepted",
                			"column-1": response.object.offeredAcceptedCount,
                			"Color": "#1E88E5",
                			"Days": response.object.offeredAcceptedDays,
                			"moveforward": response.object.offeredAcceptedMove
                		});
                	}
                	
                	if(response.object.backoutCount>0){
                		hasResults = true;
                		mainChar.push({
                			"category": "Backout(s)",
                			"column-1": response.object.backoutCount,
                			"Color": "#E57373",
                			"Days": response.object.backoutDays,
                			"moveforward": 84
                		});
                	}
                	if(hasResults) {
	                	AmCharts.makeChart("avgPercentMovingFwdChartDiv", {
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
	        				    "pageOrigin": false,
	        				    "fileName":"Average Percentage Moving Forward"
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
	        						"text": "Jobs Pipeline"
	        					}
	        				],
	        				"dataProvider": mainChar
	                    });
	                	$("#avgPercentMovingFwdChartDiv").show();
                	} else {
                		$("#avgPercentMovingFwdNoRecordsDiv").show();	
                	} 
            	} else {
            		$("#avgPercentMovingFwdNoRecordsDiv").show();
            	}
            	$("#avgPercentMovingFwdloadingDiv").hide();
            } else if (response.response === "SESSION_TIMEDOUT" || response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
                showError("Operation failed, please try again");
            }
		},
	});
}

// Quality Candidates
function getQualityCandidatesGraph() {
	$("#qualityCandidatesloadingDiv").show();
	$("#qualityCandidatesNoRecordsDiv").hide();
	$("#qualityCandidatesChartDiv").hide();
	$.ajax({
		type : "POST",
		url : sourceleadUrl + "/getQualityCandidatesListForVendor?orgRelationId=" + orgRelationId + "&spocId=" + contactUserId,
		success : function(response) {
			if (response.response === "AWKWARD") {
                location.href = "/errorPage";
            } else if (response.response === "SUCCESS") {
            	var joinedCount = response.object.split("<>")[0].split(":")[1];
            	var rejectedCount = response.object.split("<>")[1].split(":")[1];
            	if(joinedCount > 0 || rejectedCount > 0) {
	            	AmCharts.makeChart("qualityCandidatesChartDiv", {
	            		"type": "pie",
	            		"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
	            		"labelText": "",
	            		"minRadius": 20,
	            		"pieX": "50%",
	            		"pieY": "50%",
	            		"radius": "45%",
	            		"colorField": "color",
	            		"colors": [],
	            		"pullOutEffect": "elastic",
	            		"pullOutOnlyOne": true,
	            		"startEffect": "elastic",
	            		"titleField": "category",
	            		"valueField": "column-1",
	            		"autoDisplay": true,
	            		"percentPrecision": 0,
	            		"allLabels": [],
	            		"balloon": {},
	            		/*"legend": {
	            			"enabled": true,
	            			"align": "center",
	            			"autoMargins": false,
	            			"fontSize": 14,
	            			"horizontalGap": 10,
	            			"markerLabelGap": 10,
	            			"markerSize": 15,
	            			"position": "right",
	            			"useMarkerColorForLabels": false,
	            			"valueText": "[[value]]  ([[percents]])%",
	            			"valueWidth": 100
	            		},
	            		"titles": [],*/
	            		"dataProvider": [
	            			{
	            				"category": "Hired",
	            				"column-1": joinedCount,
	            				"color": "#A5D6A7"
	            			},
	            			{
	            				"category": "Rejected",
	            				"column-1": rejectedCount,
	            				"color": "#ef9a9a"
	            			}
	            		]
	            	});
	            	$("#totalReceivedCandidates").html(parseInt(joinedCount) + parseInt(rejectedCount));
	            	$("#hiredReceivedCandidates").html(parseInt(joinedCount));
	            	$("#rejectedReceivedCandidates").html(parseInt(rejectedCount));
	            	$("#qualityCandidatesChartDiv").show();
            	} else {
            		$("#qualityCandidatesNoRecordsDiv").show();
            		$("#totalReceivedCandidates").html(0);
	            	$("#hiredReceivedCandidates").html(0);
	            	$("#rejectedReceivedCandidates").html(0);
            	}
            	$("#qualityCandidatesloadingDiv").hide();  
            } else if (response.response === "SESSION_TIMEDOUT" || response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
                showError("Operation failed, please try again");
            }
		},
	});
}

/*Applicant Decline*/
function getApplicantDeclineGraph() {
	$("#applicantsDeclineCount").html(0);
	$("#applicantDeclineloadingDiv").show();
	$("#applicantDeclineNoRecordsDiv").hide();
	$("#applicantDeclineChartDiv").hide();
	$.ajax({
		type : "POST",
		url : sourceleadUrl + "/getApplicantDeclineCountsForVendor?orgRelationId=" + orgRelationId + "&spocId=" + contactUserId,
		success : function(response) {
			if (response.response === "AWKWARD") {
                location.href = "/errorPage";
            } else if (response.response === "SUCCESS") {
            	var totalCount = response.object.totalCount;
            	if(totalCount > 0) {
            		AmCharts.makeChart("applicantDeclineChartDiv",
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
                				"titles": [],
                				"dataProvider": [
                					{
                						"category": "Skills",
                						"column-1": response.object.skillsCount,
                						"color": "#9FA8DA"
                					},
                					{
                						"category": "Unsuitable Personality",
                						"color": "#90CAF9",
                						"column-1": response.object.unsuitableCount
                					},
                					{
                						"category": "Bad Fit",
                						"column-1": response.object.badfitCount,
                						"color": "#FFAB91"
                					},
                					{
                						"category": "Interview",
                						"column-1": response.object.interviewCount,
                						"color": "#A5D6A7"
                					},
                					{
                						"category": "Salary",
                						"column-1": response.object.salaryCount,
                						"color": "#FFE0B2"
                					},
                					{
                						"category": "Other",
                						"column-1": response.object.otherCount,
                						"color": "#BCAAA4"
                					}
                				]
                			}
                		);
            		$("#applicantsDeclineCount").html(totalCount);
                	$("#applicantDeclineChartDiv").show();
            	} else {
            		$("#applicantDeclineNoRecordsDiv").show();
            		$("#applicantsDeclineCount").html(0);
            	}
            	$("#applicantDeclineloadingDiv").hide();
            } else if (response.response === "SESSION_TIMEDOUT" || response.indexOf('Sign In') !== -1) {
            	location.replace(sourceleadUrl);
     		} else {
     			showError("Operation failed, please try again");
            }
     	},
     });
}

//Applicant Decline List
function releasedJobsToVendorsApplicantDecline() {
	if($("#applicantsDeclineCount").html() != "" && parseInt($("#applicantsDeclineCount").html()) > 0) {
		$("#releasedApplicantsDeclineDiv").show();
		$("#vendorReportsChartDiv").hide();
		$("#vendorReportsDiv").hide();
		$.ajax({
			type : "POST",
			url : sourceleadUrl + "/releasedJobsApplicantsDeclineListByOrgRelationId?orgRelationId="+orgRelationId+"&spocId="+contactUserId,
			success :function(result) {
	       		$('#releasedApplicantsDeclineDiv').empty();
	       		$('#releasedApplicantsDeclineDiv').append(result);
	       		vendorJobsApplicantsDeclineList();
	        }
		});	
	}
}

//Applicant Hired List
function releasedJobsToVendorsApplicantHired() {
	if($("#hiredReceivedCandidates").html() != "" && parseInt($("#hiredReceivedCandidates").html()) > 0) {
		$("#releasedApplicantsHiredListDiv").show();
		$("#vendorReportsChartDiv").hide();
		$("#vendorReportsDiv").hide();
		$.ajax({
			type : "POST",
			url : sourceleadUrl + "/releasedJobsApplicantsHiredListByOrgRelationId?orgRelationId="+orgRelationId+"&spocId="+contactUserId,
			success :function(result) {
	       		$('#releasedApplicantsHiredListDiv').empty();
	       		$('#releasedApplicantsHiredListDiv').append(result);
	       		vendorJobsApplicantsHiredList();
	        }
		});	
	}
}

//Applicant Backout List
function releasedJobsToVendorsApplicantBackOut() {
	if($("#backoutCandidatesCount").html() != "" && parseInt($("#backoutCandidatesCount").html()) > 0) { 
		$("#releasedApplicantsBackOutDiv").show();
		$("#vendorReportsChartDiv").hide();
		$("#vendorReportsDiv").hide();
		$.ajax({
			type : "POST",
			url : sourceleadUrl + "/releasedJobsApplicantsBackOutListByOrgRelationId?orgRelationId="+orgRelationId+"&spocId="+contactUserId,
			success :function(result) {
	       		$('#releasedApplicantsBackOutDiv').empty();
	       		$('#releasedApplicantsBackOutDiv').append(result);
	       		vendorJobsApplicantsBackOutList();
	        }
		});	
	}
}

/*Backout Candidates*/
function getApplicantBackoutGraph() {
	$("#backoutCandidatesCount").html(0);
	$("#backoutCandidatesloadingDiv").show();
	$("#backoutCandidatesNoRecordsDiv").hide();
	$("#backoutCandidatesChartDiv").hide();
	$.ajax({
		type : "POST",
		url : sourceleadUrl + "/getApplicantBackoutCountsForVendor?orgRelationId=" + orgRelationId + "&spocId=" + contactUserId,
		success : function(response) {
			if (response.response === "AWKWARD") {
                location.href = "/errorPage";
            } else if (response.response === "SUCCESS") {
            	var totalCount = response.object.totalCount;
            	if(totalCount > 0) {
            		AmCharts.makeChart("backoutCandidatesChartDiv",
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
                				"titles": [],
                				"dataProvider": [
                					{
                						"category": "Current Company",
                						"column-1": response.object.currentCompany,
                						"color": "#9FA8DA"
                					},
                					{
                						"category": "Compensation",
                						"color": "#90CAF9",
                						"column-1": response.object.compensation
                					},
                					{
                						"category": "Better Offer",
                						"column-1": response.object.betterOffer,
                						"color": "#FFAB91"
                					},
                					{
                						"category": "Cold Feet",
                						"column-1": response.object.coldFeet,
                						"color": "#A5D6A7"
                					},
                					{
                						"category": "Personal",
                						"column-1": response.object.personal,
                						"color": "#FFE0B2"
                					},
                					{
                						"category": "Other",
                						"column-1": response.object.otherCount,
                						"color": "#BCAAA4"
                					}
                				]
                			}
                		);
            		$("#backoutCandidatesCount").html(totalCount);
                	$("#backoutCandidatesChartDiv").show();
            	} else {
            		$("#backoutCandidatesNoRecordsDiv").show();
            		$("#backoutCandidatesCount").html(0);
            	}
            	$("#backoutCandidatesloadingDiv").hide();
            } else if (response.response === "SESSION_TIMEDOUT" || response.indexOf('Sign In') !== -1) {
            	location.replace(sourceleadUrl);
     		} else {
     			showError("Operation failed, please try again");
            }
     	},
     });
}
   
// Average time to fill
function getAverageTimeToFillGraph() {
	AmCharts.makeChart("avgttf", {
		"type": "pie",
		"innerRadius": "0%",
		"pieX": "50%",
		"pieY": "55%",
		"radius": "42%",
		"colorField": "color",
		"labelsEnabled": false,
		"titleField": "category",
		"valueField": "column-1",
		"allLabels": [],
		"balloon": {
			"showBullet": true
		},
		"legend": {
			"enabled": true,
			"markerLabelGap": 10,
			"markerSize": 12,
			"position": "left",
			"spacing": 25,
			"valueWidth": 35,
			"valueText": "[[value]] Days",
			"verticalGap": 15
		},
		"titles": [
			{
				"id": "Title-1",
				"text": "Average Time to Fill: 30 Days"
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
	});
}