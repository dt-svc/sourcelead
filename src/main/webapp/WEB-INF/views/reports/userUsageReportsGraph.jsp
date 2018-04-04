<script>
$(document).ready(function() {
	userUsageReportsGraph();
});
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
							"id": "active",
							"title": "Active",
							"valueField": "column-1"
						},
						{
							"bullet": "square",
							"id": "inactive",
							"title": "Inactive",
							"valueField": "column-2"
						},
						{
							"bullet": "diamond",
							"id": "notLogin",
							"title": "Not Login",
							"valueField": "column-3"
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
					"dataProvider": [
						{
							"date": "2017-01",
							"column-1": 8,
							"column-2": 5,
							"column-3": "10"
						},
						{
							"date": "2017-02",
							"column-1": 6,
							"column-2": 7,
							"column-3": "8"
						},
						{
							"date": "2017-03",
							"column-1": 2,
							"column-2": 3,
							"column-3": "6"
						},
						{
							"date": "2017-04",
							"column-1": 1,
							"column-2": 3,
							"column-3": "8"
						},
						{
							"date": "2017-05",
							"column-1": 2,
							"column-2": 1,
							"column-3": "12"
						},
						{
							"date": "2017-06",
							"column-1": 3,
							"column-2": 2,
							"column-3": "10"
						},
						{
							"date": "2017-07",
							"column-1": 6,
							"column-2": 8,
							"column-3": "8"
						}
					]
				}
			);
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

