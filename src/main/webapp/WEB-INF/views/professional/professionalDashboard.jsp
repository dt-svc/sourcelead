<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="resources/js/amcharts.js"></script>
<script src="resources/js/serial.js"></script>
<script src="resources/js/light.js"></script>
<script src="resources/js/pie.js"></script>


<!-- <script src="https://www.amcharts.com/lib/3/plugins/export/export.min.js"></script>
<link rel="stylesheet" href="https://www.amcharts.com/lib/3/plugins/export/export.css" type="text/css" media="all" /> --> 


<style>
.dataTables_empty {
    height: 285px !important;
}
</style>

<script type="text/javascript">
$(document).ready(function(){
	getJobSummarySerialChart();
	getJobSummaryDoNutChart();
	getJobPortlets();
});
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Jobs Summary</h2>
      </div>
    </section>
    <div class="content-new clearfix mt25"></div>
    
      <div class="row">
          <div class="col-md-7 paddingRight">
          <div class="col-md-10">
				    <div class="row">
					  <div class="col-md-5 paddingRight">
					     <input type="text" value="" placeholder="From Date" class="form-control input-sm calendar" id="serialfromdate" name="From Date">
					  </div>
			  		  <div class="col-md-5 paddingRight paddingLeft">
					     <input type="text" value="" placeholder="To Date" class="form-control input-sm calendar" id="serialtodate" name="To Date">
					  </div>
					  <div class="col-md-2 paddingLeft">
						  <button type="button" class="btn btn-sm btn-primary" onclick="getJobSummarySerialChart();"><i class="fa fa-search"></i>&nbsp;&nbsp;Search</button>
					  </div>
				  </div>
 		   </div>
              <div id="chartdiv" style="width: 100%;">
              
              </div>
          </div>

          <div class="col-md-5 text-center paddingLeft">
            
          <div class="col-md-10">
				    <div class="row">
					  <div class="col-md-5 paddingRight">
					     <input type="text" value="" placeholder="From Date" class="form-control input-sm calendar" id="dpfromdate" name="From Date">
					  </div>
			  		  <div class="col-md-5 paddingLeft paddingRight">
					     <input type="text" value="" placeholder="To Date" class="form-control input-sm calendar" id="dptodate" name="To Date">
					  </div>
					  <div class="col-md-2 paddingLeft">
						  <button type="button" class="btn btn-sm btn-primary" onclick="getJobSummaryDoNutChart();"><i class="fa fa-search"></i>&nbsp;&nbsp;Search</button>
					  </div>
				  </div>
 		   </div>
 		   <div class="content-new clearfix mt25">
             <div id="jobSummary">
         
             </div>
          </div>
      </div>
      <div class="clearfix"></div>
      <div class="dashboard-widget">
      
      <div class="row">
      <div class="col-md-10">
				    <div class="row">
					  <div class="col-md-3 paddingRight">
					     <input type="text" value="" placeholder="From Date" class="form-control input-sm calendar" id="portletfromdate" name="From Date">
					  </div>
			  		  <div class="col-md-3 paddingLeft paddingRight">
					     <input type="text" value="" placeholder="To Date" class="form-control input-sm calendar" id="portlettodate" name="To Date">
					  </div>
					  <div class="col-md-2 paddingLeft">
						  <button type="button" class="btn btn-sm btn-primary" onclick="getJobPortlets();"><i class="fa fa-search"></i>&nbsp;&nbsp;Search</button>
					  </div>
				  </div>
 		   </div>
      <div id="jobPortletsDiv" class="activeJobs"></div>
      </div>
      </div>

      </div>
  </section>
  
  
  
  <script>
  function getJobPortlets(){
	  
    var sourceleadUrl = '${sourceleadUrl}';
	
	var startDate = document.getElementById("portletfromdate").value; 
	var endDate = document.getElementById("portlettodate").value; 
	  
	$.ajax({ 
		   	type: "GET",
		   	url: sourceleadUrl+"/getJobSummaryPortlets?${_csrf.parameterName}=${_csrf.token}&startDate="+startDate+"&endDate="+endDate,
		    success: function(data) {
	    	 var json = JSON.parse(data);
		    	
	    	  var result = ''
			  result = result +'<div class="col-sm-6 col-md-3">';
			  result = result +'<div class="content_widget jobs-graphview buttonView">';
			  result = result +'<span class="label label-primary">Active Jobs</span>';
			  result = result +' <img src="resources/images/active_jobs.png" title="Active Jobs" alt="Active Jobs">';
			  result = result +'<span class="count">';
			  result = result +'100';
              result = result +'</span>';
              result = result +'</div>';
              result = result +' </div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-warning">Referrals</span>';
              result = result +'<img src="resources/images/refer-icon.png" title="Referrals" alt="Referrals">';
          
              result = result +'<span class="count">100</span>';
              result = result +'</div>';
              result = result +' </div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-success">Applied</span>';
                        
              result = result +'<img src="resources/images/applied_icon.png" title="Applied" alt="Applied">';
              result = result +'<span class="count">'+json['appliedJobs']+'</span>';
              result = result +'</div>';

              result = result +'</div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-info">Shortlisted</span>';
              result = result +'<img src="resources/images/short_listed.png" title="Shortlisted" alt="Shortlisted">';
              result = result +'<span class="count">'+json['shortlistJobs']+'</span>';
              result = result +'</div>';

              result = result +'</div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-success">Interviews</span>';
                        
              result = result +'<img src="resources/images/interview_icons.png" title="Interviews" alt="Interviews">';
              result = result +'<span class="count">'+json['interviews']+'</span>';
              result = result +'</div>';

              result = result +'</div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-info">Offered</span>';
                        
              result = result +'<img src="resources/images/offered_icon.png" title="Offered" alt="Offered">';
              result = result +'<span class="count">'+json['offeredJobs']+'</span>';
              result = result +'</div>';

              result = result +'</div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-primary">Hired</span>';
              
              result = result +'<img src="resources/images/hired_icon.png" title="Hired" alt="Hired">';
              result = result +'<span class="count">'+json['hiredJobs']+'</span>';
              result = result +'</div>';

              result = result +'</div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-danger">Rejected';
              result = result +'</span>';
              
              result = result +'<img src="resources/images/reject_icon.png" title="Rejected" alt="Rejected">';
              result = result +'<span class="count">'+json['rejectedJobs']+'</span>';
              result = result +'</div>';

              result = result +'</div>';
              
              $("#jobPortletsDiv").empty(); 
              $("#jobPortletsDiv").append(result); 
		    },
	        error: function(e){
	        }
	 });
  }
  
  </script>
  
  
  <script>
  function getJobSummaryDoNutChart(){
	var sourceleadUrl = '${sourceleadUrl}';
	
	var startDate = document.getElementById("dpfromdate").value; 
	var endDate = document.getElementById("dptodate").value; 
	
	$.ajax({ 
		   	type: "GET",
		   	url: sourceleadUrl+"/getJobSummaryDoNutChart?${_csrf.parameterName}=${_csrf.token}&startDate="+startDate+"&endDate="+endDate,
		    success: function(data) {
	        	var json = JSON.parse(data);
	        	
	        	loadJobSummaryDoNutChart(json['appliedJobs'],json['referToMeJobs'],json['referByMeJobs'])
	        },
	        error: function(e){
	        }
	 });
  }
  </script>
  
  <script>
  function getJobSummarySerialChart(){
	  var sourceleadUrl = '${sourceleadUrl}';
		
		
		var startDate = document.getElementById("serialfromdate").value; 
		var endDate = document.getElementById("serialtodate").value; 
		
		
		$.ajax({ 
			   	type: "GET",
			   	url: sourceleadUrl+"/getJobSummarySerialChart?${_csrf.parameterName}=${_csrf.token}&startDate="+startDate+"&endDate="+endDate,
			    success: function(data) {
		        	var json = JSON.parse(data);
		        	
		        	 if(data != null && data !='undefined' && data !=null &&  data != [] && json.length >0){ 
			        	if(json.length > 0)	
			 	   		{
			        		    var chartData = [];
								for(var i=0; i < json.length ; i++){
			        			 
			        			 var jobs = json[i];
			        			    chartData.push( {
			        			      day: jobs.dateString,
			        			      referedToMe:jobs.referToMeJobs,
			        			      referedByMe:jobs.referByMeJobs,
			        			      applied: jobs.appliedJobs
			        			     
			        			    } );
			        			  }
			        	
			        		 loadJobSummarySerialChart(chartData);
			 	   		}
		        	 }
		        	
		        },
		        error: function(e){
		        }
		 });
  }
  </script>
  
  
  
  
  <!-- For donut chart -->
<script>
function loadJobSummaryDoNutChart(appliedJobs,referToMeJobs,referByMeJobs){
	if(appliedJobs == 0 && referToMeJobs == 0 && referByMeJobs == 0){
		
	    var result ="";
        result = result+"<div class='dataTables_empty mt'>"; 
	    result = result+"</div> "; 
		$("#jobSummary").empty();
	    $("#jobSummary").append(result); 
	    
	    /* $("#dpfromdate").val('');
	    $("#dptodate").val(''); */
		
	}else{
		var chart = AmCharts.makeChart( "jobSummary", {
			  "type": "pie",
			  "theme": "light",
			  "dataProvider": [ {
			    "title": "Refered To Me ("+referToMeJobs+")",
			    "value": referToMeJobs
			  }, {
			    "title": "Refered By Me ("+referByMeJobs+")",
			    "value": referByMeJobs
			  }, {
			    "title": "Applied ("+appliedJobs+")",
			    "value": appliedJobs
			  } ],
			  "titleField": "title",
			  "valueField": "value",
			  "labelRadius": 5,
			
			  "radius": "35%",
			  "innerRadius": "40%",
			  "labelText": "[[title]]",
			  "export": {
			    "enabled": true
			  }
			} );
			 
	}
 
}
    
</script>

 <script>
 function loadJobSummarySerialChart(result){
  var chart = AmCharts.makeChart("chartdiv", {
	    "type": "serial",
	    "theme": "light",
	    "legend": {
	        "useGraphSettings": true
	    },
	    "dataProvider": result,
	    "valueAxes": [{
	        "integersOnly": true,
	        "maximum": 50,
	        "minimum": 1,
	        "reversed": false,
	        "axisAlpha": 0,
	        "dashLength": 5,
	        "gridCount": 10,
	        "position": "left",
	        "title": "Jobs"
	    }],
	    "startDuration": 0.5,
	    "graphs": [{
	        "balloonText": "Refered To Me: [[value]]",
	        "bullet": "round",
	        "title": "Refered To Me",
	        "valueField": "referedToMe",
			"fillAlphas": 0
	    }, {
	        "balloonText": "Refered By Me: [[value]]",
	        "bullet": "round",
	        "title": "Refferd By Me",
	        "valueField": "referedByMe",
			"fillAlphas": 0
	    },{
	        "balloonText": "Applied: [[value]]",
	        "bullet": "round",
	        "title": "Applied",
	        "valueField": "applied",
			"fillAlphas": 0
	    }],
	    "chartCursor": {
	        "cursorAlpha": 0,
	        "zoomable": false
	    },
	    "categoryField": "day",
	    "categoryAxis": {
	        "gridPosition": "start",
	        "axisAlpha": 0,
	        "fillAlpha": 0.05,
	        "fillColor": "#000000",
	        "gridAlpha": 0,
	        "position": "top"
	    },
	    "export": {
	    	"enabled": true,
	        "position": "bottom-right"
	     }
	});
 }
  </script> 
  
  
  
  
    
    
    
 <script type="text/javascript">
  $( function() {
	  
	  
	  $('#dpfromdate').datepicker({
	      dateFormat: 'yy-mm-dd',
	      changeMonth: true,
	  	  changeYear: true, 
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  
	  $('#dptodate').datepicker({
	      dateFormat: 'yy-mm-dd',
	      changeMonth: true,
	  	  changeYear: true, 
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  

	  $('#portletfromdate').datepicker({
	      dateFormat: 'yy-mm-dd',
	      changeMonth: true,
	  	  changeYear: true, 
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  
	  $('#portlettodate').datepicker({
	      dateFormat: 'yy-mm-dd',
	      changeMonth: true,
	  	  changeYear: true, 
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  
	  $('#serialfromdate').datepicker({
	      dateFormat: 'yy-mm-dd',
	      changeMonth: true,
	  	  changeYear: true, 
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  
	  $('#serialtodate').datepicker({
	      dateFormat: 'yy-mm-dd',
	      changeMonth: true,
	  	  changeYear: true, 
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  
	  
      
  });
</script>

 

<jsp:include page="../masterFooter.jsp" />