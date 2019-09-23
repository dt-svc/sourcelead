<script>
/* $(document).ready(function() {
	//userUsageReportsGraph(); //unused fucntion due to script issue.29-April-2019
	// getAllOrgs(); //unused fucntion due to script issue.29-April-2019
}); */
</script>

  <script>
    $(document).tooltip({
    position: {
        my: "center bottom",
        at: "center top-10",
        collision: "flip",
        using: function( position, feedback ) {
            $( this ).addClass( feedback.vertical )
                .css( position );
        }
    }
});
  </script>
    <!-- amCharts javascript code -->
<script type="text/javascript">
 function prepareAmchart(data){
		 var chartData = [];
		 for (var i = 0; i < data.length; i++) { 
		 var opt = data[i]; 
		 chartData.push({
			 date: opt.dates,
			 activeCount: opt.activeCount,
			 inactiveCount: opt.inactiveCount,
			 notLoggedinCount: opt.notLoggedinCount
		 });
		 }
			AmCharts.makeChart("useractiveGraph",
				{
					"type": "serial",
					"categoryField": "date",
					"dataDateFormat": "YYYY-MM",
					"plotAreaFillAlphas": 1,
					"colors": [
						"#1976d2",
						"#ff9100",
						"#c62828"
					],
					"fontSize": 10,
					"theme": "default",
					"export": {
						"enabled": true
					},
					"categoryAxis": {
						"minPeriod": "MM",
						"parseDates": true
					},
					"chartCursor": {
						"enabled": true,
						"bulletsEnabled": true,
						"categoryBalloonDateFormat": "MMM YYYY"
					},
					"chartScrollbar": {
						"enabled": true
					},
					"trendLines": [],
					"graphs": [
						{
							"bullet": "round",
// 							"id": "active",
							"title": "Active",
							"valueField": "activeCount"
						},
						{
							"bullet": "square",
// 							"id": "inactive",
							"title": "Inactive",
							"valueField": "inactiveCount"
						},
						{
							"bullet": "diamond",
// 							"id": "notLogin",
							"title": "Not Login",
							"valueField": "notLoggedinCount"
						}
					],
					"guides": [],
					"valueAxes": [],
					"allLabels": [],
					"balloon": {
						"offsetX": 15
					},
					"legend": {
						"enabled": true,
						"markerBorderThickness": 0,
						"markerLabelGap": 10,
						"markerSize": 15,
                        "position": "top"
					},
					"dataProvider": chartData
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

