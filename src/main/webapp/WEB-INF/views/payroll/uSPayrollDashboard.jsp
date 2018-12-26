<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" src="https://www.amcharts.com/lib/3/amcharts.js"></script> 
<script type="text/javascript" src="https://www.amcharts.com/lib/3/serial.js"></script> 
<script type="text/javascript" src="https://www.amcharts.com/lib/3/pie.js"></script>
<style type="text/css">
.require_wrap_table.tableHDRBgColor th {
  background-color: #d0d4dc !important; color: #333; border-right: 1px solid #bec2ca !important;
}
.btnBgColor {
  background-color: #d0d4dc !important; border: 1px solid #bec2ca !important;
}
</style>



<script>
$(document).ready(function(){
	nextPayrollDate();
});

</script>

 <!-- Content Area -->
<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left"><h2>Total Pays</h2></div>
      <div class="pull-right brud-crum">Home &raquo; Total Pays</div>
    </section>
    
	<div class="clearfix project_wrap">
	<div class="mt mb clearfix">
		<div class="col-md-8">
		  <div class="positionRelative clearfix"> 
            <div class="col-md-4 positionRelative noPad col-md-offset-8">
        <select class="sel" id="payFreqency" name = "payFreqency">
			<option selected value="Weekly">Weekly</option>
			<option value="Bi-Weekly">Bi-Weekly</option>
			<option value="Monthly">Monthly</option>
			<option value="Annually">Annually</option>
        </select>
				
	
    <div class="clearfix pull-lefet col-md-12 paddingRight" id="customLeaves" style="display: none; width: 346px; position: absolute; right: 0px; background: #fff; padding: 10px; padding-right: 10px; box-shadow: 0px 0px 0px 1px #ccc; top:auto; z-index: 99;">
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
      </div>
          </div>
		<div id="agWeekdays" style="width: 100%; height: 250px; display: none;" class="amchartAnchor-Hide"></div>
			<div id="fifteenDays" style="width: 100%; height: 250px; display: none;" class="amchartAnchor-Hide"></div>
			<div id="oneMonth" style="width: 100%; height: 250px; display: none;" class="amchartAnchor-Hide"></div>
			<div id="threeMonths" style="width: 100%; height: 250px; display: none;" class="amchartAnchor-Hide"></div>
			<div id="agYear" style="width: 100%; height: 250px;" class="amchartAnchor-Hide"></div>
		<div class="clearfix"></div>
		</div>
		<div class="col-md-4">
			
			<div class="clearfix row">
				<h4 class="text-muted col-md-12 margin-top0">Next Payroll</h4>
			</div>
			
			<div class="clearfix mt">
				<div class="media">
				  <div class="media-left">
					<i class="calendarFrequency"></i>
				  </div>
				  <div class="media-body">
					<h5 class="media-heading text-muted">Frequency :</h5>
					<p class="fontSmall">01/11-2018 - 30/11/2018</p>
				  </div>
				</div>
			</div>
			<div class="clearfix mt10 mb10">
				<div class="media">
				  <div class="media-left">
					<i class="calendarRundate"></i>
				  </div>
				  <div class="media-body">
					<h5 class="media-heading text-primary">Run Date :</h5>
					<p class="fontSmall">29/11/2018</p>
				  </div>
				</div>
			</div>
			<div class="clearfix">
				<div class="media">
				  <div class="media-left">
					<i class="calendarPaydate"></i>
				  </div>
				  <div class="media-body">
					<h5 class="media-heading text-green">Pay Date :</h5>
					<p class="fontSmall">02/12/2018</p>
				  </div>
				</div>
			</div>
<div class="clearfix mt col-md-8 padding_left">
    <a href="javascript:void(0)" class="btn btn-default btn-block btnBgColor">Initiate Payroll</a>
 </div>

		</div>
  <div class="clearfix"></div>

  <div class="col-md-12">
    <h4 class="clearfix">Previous Payroll</h4>
    <div class="require_wrap_table clearfix tableHDRBgColor">
            <table>
              <thead>
                <tr>
                  <th>Prequency</th>
                  <th>Pay Group</th>
                  <th>Run Date</th>
                  <th>Check Date</th>
          <th>No. Employees</th>
          <th>No. D.D</th>
          <th>No. Checks</th>
          <th>Net Pay</th>
                </tr>
              </thead>
              <tbody>
                 <tr>
           <td>
             <div class="tblminwidth-50">01/01/2018</div>
             <div class="tblminwidth-50">03/03/2018</div>
           </td>
           <td><div class="tblminwidth-75">Pay Group</div></td>
           <td><div class="tblminwidth-75">23/12/2018</div></td>
           <td><div class="tblminwidth-75">23/12/2018</div></td>
           <td><div class="tblminwidth-75">99885566</div></td>
           <td><div class="tblminwidth-75">22558877</div></td>
           <td><div class="tblminwidth-50">22558877</div></td>
          <td><div class="tblminwidth-75">9988554455</div></td>
                </tr>
              </tbody>
            </table>
          </div>



  </div>

	</div>
		
		<div class="clearfix"></div>
	</div>
  </section>
  
  
  <!-- amCharts javascript sources --> 
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
				})
				$(document).ready(function(){
					$("#cd").click(function(){
						$(".clientDetails").toggleClass('hidden', 500);
				});
	});
</script>
<script>
$(function() {
        $('#customDates').change(function(){
			var optval = $(this).val();
			if(optval == "custom") {
				document.getElementById("customLeaves").style.display="block";
			}else{
				document.getElementById("customLeaves").style.display="none";
			}
        });
    });
	
$("html").click(function() {
	document.getElementById("customLeaves").style.display="none";
})
$("#customLeaves, #customDates, .customDates").click(function(e) {
	e.stopPropagation();
})
$("#datepicker4, #datepicker2").datepicker();
</script>
<script>
	$(function(){
		$("#customDates").change(function() {
			var customDates = $(this).val();
			if (customDates === "agYear") {
				$("#agWeekdays").hide();
				$("#fifteenDays").hide();
				$("#oneMonth").hide();
				$("#threeMonths").hide();
				$("#agYear").show();
			}else if (customDates === "weekDays") {
				$("#agWeekdays").show();
				$("#fifteenDays").hide();
				$("#oneMonth").hide();
				$("#threeMonths").hide();
				$("#agYear").hide();
			}else if (customDates === "fifteenDays") {
				$("#agWeekdays").hide();
				$("#fifteenDays").show();
				$("#oneMonth").hide();
				$("#threeMonths").hide();
				$("#agYear").hide();

			}else if (customDates === "oneMonth") {
				$("#agWeekdays").hide();
				$("#fifteenDays").hide();
				$("#oneMonth").show();
				$("#threeMonths").hide();
				$("#agYear").hide();

			}else if (customDates === "threeMonths") {
				$("#agWeekdays").hide();
				$("#fifteenDays").hide();
				$("#oneMonth").hide();
				$("#threeMonths").show();
				$("#agYear").hide();
			}else {
				$("#agWeekdays").hide();
				$("#fifteenDays").hide();
				$("#oneMonth").hide();
				$("#threeMonths").hide();
				$("#agYear").show();
			}
		});
	})
</script>
<script type="text/javascript">
	AmCharts.makeChart("agYear",
	{
	"type": "serial",
	"categoryField": "date",
	"dataDateFormat": "YYYY-MM",
	"theme": "default",
	"categoryAxis": {
		"autoRotateAngle": 0,
		"autoWrap": true,
		"equalSpacing": true,
		"gridPosition": "start",
		"minPeriod": "MM",
		"parseDates": true,
		"tickPosition": "start",
		"twoLineMode": true,
		"axisAlpha": 0,
		"axisColor": "#FFFFFF",
		"axisThickness": 0,
		"gridAlpha": 0,
		"gridColor": "#6FC9FD",
		"gridThickness": 0,
		"labelRotation": 90,
		"markPeriodChange": false,
		"minHorizontalGap": 0,
		"titleBold": false
	},
	"trendLines": [],
	"graphs": [
		{
			"bullet": "round",
			"fillAlphas": 0.1,
			"id": "totalPays",
			"lineColor": "#6FC9FD",
			"title": "Totalpays",
			"valueField": "column-1"
		}
	],
	"guides": [],
	"valueAxes": [
		{
			"id": "ValueAxis-1",
			"axisAlpha": 0.5,
			"axisColor": "#6FC9FD",
			"gridAlpha": 0.5,
			"gridColor": "#6FC9FD",
			"gridThickness": 0,
			"title": ""
		}
	],
	"allLabels": [],
	"balloon": {},
	"titles": [],
	"dataProvider": [
		{
			"date": "2018-01",
			"column-1": 12
		},
		{
			"date": "2018-02",
			"column-1": 2
		},
		{
			"date": "2018-03",
			"column-1": 3
		},
		{
			"date": "2018-04",
			"column-1": 5
		},
		{
			"date": "2018-05",
			"column-1": 6
		},
		{
			"date": "2018-06",
			"column-1": 10
		},
		{
			"date": "2018-07",
			"column-1": 12
		},
		{
			"date": "2018-08",
			"column-1": 8
		},
		{
			"date": "2018-09",
			"column-1": 12
		},
		{
			"date": "2018-10",
			"column-1": 10
		},
		{
			"date": "2018-11",
			"column-1": 11
		},
		{
			"date": "2018-12",
			"column-1": 12
		}
	]
}
);
	
		//WeekDays
	AmCharts.makeChart("agWeekdays",
	{
	"type": "serial",
	"categoryField": "category",
		"columnWidth": 1,
	"dataDateFormat": "YYYY-MM-DD",
	"colors": [],
	"startDuration": 1,
	"categoryAxis": {
		"equalSpacing": true,
		"gridPosition": "start",
		"parseDates": true,
		"axisColor": "#636E72",
		"firstDayOfWeek": 0,
		"gridCount": 0,
		"gridThickness": 0,
		"markPeriodChange": false,
		"minHorizontalGap": 35,
		"minVerticalGap": 10
	},
	"trendLines": [],
	"graphs": [
		{
			"balloonText": "No. of Assets [[value]] <br/> Category [[value]]",
			"fillAlphas": 1,
			"fillColors": "#74B9FF",
			"id": "assetGrowth",
			"lineColor": "#74B9FF",
			"title": "graph 1",
			"type": "column",
			"valueField": "column-1",
			"fixedColumnWidth": 50
		}
	],
	"guides": [],
	"valueAxes": [
		{
			"id": "Quanity",
			"axisColor": "#636E72",
			"dateFormats": [
				{
					"period": "fff",
					"format": "JJ:NN:SS"
				},
				{
					"period": "ss",
					"format": "JJ:NN:SS"
				},
				{
					"period": "mm",
					"format": "JJ:NN"
				},
				{
					"period": "hh",
					"format": "JJ:NN"
				},
				{
					"period": "DD",
					"format": "MMM DD"
				},
				{
					"period": "WW",
					"format": "MMM DD"
				},
				{
					"period": "MM",
					"format": "MMM"
				},
				{
					"period": "YYYY",
					"format": "YYYY"
				}
			],
			"firstDayOfWeek": 0,
			"gridThickness": 0,
			//"title": "Quantity"
		}
	],
	"allLabels": [],
	"balloon": {},
	"titles": [
		{
			"id": "Title-1",
			"size": 15,
			"text": ""
		}
	],
	"dataProvider": [
		{
			"category": "2018-03-01",
			"column-1": 8
		},
		{
			"category": "2018-03-02",
			"column-1": 16
		},
		{
			"category": "2018-03-03",
			"column-1": 2
		},
		{
			"category": "2018-03-04",
			"column-1": 7
		},
		{
			"category": "2018-03-05",
			"column-1": 5
		},
		{
			"category": "2018-03-06",
			"column-1": 9
		},
{
			"category": "2018-03-07",
			"column-1": 9
		}
	]
}
    );
	
	//abpr 15days
	AmCharts.makeChart("fifteenDays",
{
	"type": "serial",
	"categoryField": "date",
	"dataDateFormat": "YYYY-MM-DD",
	"categoryAxis": {
		"parseDates": true,
		"axisAlpha": 0,
		"axisColor": "#FFFFFF",
		"axisThickness": 0,
		"gridAlpha": 0,
		"gridColor": "#6FC9FD",
		"gridThickness": 0,
		"labelRotation": 90,
		"markPeriodChange": false,
		"minHorizontalGap": 0,
		"titleBold": false
	},
	"trendLines": [],
	"graphs": [
		{
			"bullet": "round",
			"fillAlphas": 0.1,
			"id": "totalPays",
			"lineColor": "#6FC9FD",
			"title": "Totalpays",
			"valueField": "column-1"
		},
		{
			"bullet": "square",
			"id": "AmGraph-2",
			"title": "graph 2",
			"valueField": "column-2"
		}
	],
	"guides": [],
	"valueAxes": [
		{
			"id": "ValueAxis-1",
			"axisAlpha": 0.5,
			"axisColor": "#6FC9FD",
			"gridAlpha": 0.5,
			"gridColor": "#6FC9FD",
			"gridThickness": 0,
			"title": ""
		}
	],
	"allLabels": [],
	"balloon": {},
	"legend": {
		"enabled": false,
		"useGraphSettings": true
	},
	"titles": [
		{
			"id": "Title-1",
			"size": 15,
			"text": ""
		}
	],
	"dataProvider": [
		{
			"date": "2018-03-01",
			"column-1": 88
		},
		{
			"date": "2018-03-02",
			"column-1": 66
		},
		{
			"date": "2018-03-03",
			"column-1": 25
		},
		{
			"date": "2018-03-04",
			"column-1": 19
		},
		{
			"date": "2018-03-05",
			"column-1": 12
		},
		{
			"date": "2018-03-06",
			"column-1": 35
		},
		{
			"date": "2018-03-07",
			"column-1": 18
		},
		{
			"date": "2018-03-08",
			"column-1": 12
		},
		{
			"date": "2018-03-09",
			"column-1": 15
		},
		{
			"date": "2018-03-10",
			"column-1": 22
		},
		{
			"date": "2018-03-11",
			"column-1": 20
		},
		{
			"date": "2018-03-12",
			"column-1": 7
		},
		{
			"date": "2018-03-13",
			"column-1": 8
		},
		{
			"date": "2018-03-14",
			"column-1": 10
		},
		{
			"date": "2018-03-15",
			"column-1": 12
		}

	]
}					  );
		
		
			//abpr oneMonth
	AmCharts.makeChart("oneMonth",
{
	"type": "serial",
	"categoryField": "category",
	"dataDateFormat": "YYYY-MM-DD",
	"colors": [],
	"startDuration": 1,
	"categoryAxis": {
		"autoRotateAngle": 90,
		"autoRotateCount": 0,
		"gridPosition": "start",
		"parseDates": true,
		"axisColor": "#636E72",
		"centerRotatedLabels": true,
		"gridCount": 0,
		"gridThickness": 0,
		"markPeriodChange": false,
		"minHorizontalGap": 25,
		"minVerticalGap": 5
		
	},
	"trendLines": [],
	"graphs": [
		{
			"balloonText": "No. of Assets:  [[value]] <br/> Category:  [[value]]\"",
			"fillAlphas": 1,
			"fillColors": "#74B9FF",
			"id": "assetGrowth",
			"lineColor": "#74B9FF",
			"title": "graph 1",
			"type": "column",
			"valueField": "column-1"
		}
	],
	"guides": [],
	"valueAxes": [
		{
			"id": "Quanity",
			"axisColor": "#636E72",
			"gridThickness": 0,
			//"title": "Quantity"
		}
	],
	"allLabels": [],
	"balloon": {},
	"titles": [
		{
			"id": "Title-1",
			"size": 15,
			"text": ""
		}
	],
	"dataProvider": [
		{
			"category": "2018-03-01",
			"column-1": 8
		},
		{
			"category": "2018-03-02",
			"column-1": 16
		},
		{
			"category": "2018-03-03",
			"column-1": 2
		},
		{
			"category": "2018-03-04",
			"column-1": 7
		},
		{
			"category": "2018-03-05",
			"column-1": 5
		},
		{
			"category": "2018-03-06",
			"column-1": 9
		},
		{
			"category": "2018-03-07",
			"column-1": 4
		},
		{
			"category": "2018-03-08",
			"column-1": 15
		},
		{
			"category": "2018-03-09",
			"column-1": 12
		},
		{
			"category": "2018-03-10",
			"column-1": 17
		},
		{
			"category": "2018-03-11",
			"column-1": 18
		},
		{
			"category": "2018-03-12",
			"column-1": 21
		},
		{
			"category": "2018-03-13",
			"column-1": 24
		},
		{
			"category": "2018-03-14",
			"column-1": 23
		},
		{
			"category": "2018-03-15",
			"column-1": 24
		},
		{
			"category": "2018-03-16",
			"column-1": 8
		},
		{
			"category": "2018-03-17",
			"column-1": 16
		},
		{
			"category": "2018-03-18",
			"column-1": 2
		},
		{
			"category": "2018-03-19",
			"column-1": 7
		},
		{
			"category": "2018-03-20",
			"column-1": 5
		},
		{
			"category": "2018-03-21",
			"column-1": 9
		},
		{
			"category": "2018-03-22",
			"column-1": 4
		},
		{
			"category": "2018-03-23",
			"column-1": 15
		},
		{
			"category": "2018-03-24",
			"column-1": 12
		},
		{
			"category": "2018-03-25",
			"column-1": 17
		},
		{
			"category": "2018-03-26",
			"column-1": 18
		},
		{
			"category": "2018-03-27",
			"column-1": 21
		},
		{
			"category": "2018-03-28",
			"column-1": 24
		},
		{
			"category": "2018-03-29",
			"column-1": 23
		},
		{
			"category": "2018-03-30",
			"column-1": 24
		},
		{
			"category": "2018-03-31",
			"column-1": 24
		}
	]
}
);
	
//abpr Thrree Months
	AmCharts.makeChart("threeMonths",
{
	"type": "serial",
	"categoryField": "category",
	"dataDateFormat": "YYYY-MM-DD",
	"colors": [],
	"startDuration": 1,
	"categoryAxis": {
		"autoRotateAngle": 0,
		"autoRotateCount": 0,
		"gridPosition": "start",
		"parseDates": false,
		"axisColor": "#636E72",
		"centerRotatedLabels": true,
		"gridCount": 0,
		"gridThickness": 0,
		"markPeriodChange": false,
		"minHorizontalGap": 15,
		"minVerticalGap": 5
	},
	"trendLines": [],
	"graphs": [
		{
			"balloonText": "No. of Assets:  [[value]] <br/> Category:  [[value]]\"",
			"fillAlphas": 1,
			"fillColors": "#74B9FF",
			"fixedColumnWidth": 100,
			"id": "assetGrowth",
			"lineColor": "#74B9FF",
			"title": "graph 1",
			"type": "column",
			"valueField": "column-1"
		}
	],
	"guides": [],
	"valueAxes": [
		{
			"id": "Quanity",
			"axisColor": "#636E72",
			"gridThickness": 0,
			//"title": "Quantity"
		}
	],
	"allLabels": [],
	"balloon": {},
	"titles": [
		{
			"id": "Title-1",
			"size": 15,
			"text": ""
		}
	],
	"dataProvider": [
		{
			"category": "January",
			"column-1": 8
		},
		{
			"category": "February",
			"column-1": 16
		},
		{
			"category": "March",
			"column-1": 2
		}
	]
}	
);
</script>
<script>
$(function() {
    $("#payslipCalendar").datepicker({
	  showOtherMonths: true,
      selectOtherMonths: true
	});
});
</script>

<script>
  function nextPayrollDate(){
	  var payFreqency = $("#payFreqency").val();
		  $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getNextPayrollDate?"+tokenParamName+"="+tokenValue+"&payFreqency="+payFreqency, 
		        success: function(data) {
		        	
		        },
		    });
	}
  </script>



<jsp:include page="../masterFooter.jsp" />