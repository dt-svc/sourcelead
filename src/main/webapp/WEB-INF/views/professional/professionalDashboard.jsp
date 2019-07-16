<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- <script src="resources/js/amcharts.js?staticTime=${staticTime}"></script>
<script src="resources/js/serial.js?staticTime=${staticTime}"></script>
<script src="resources/js/light.js?staticTime=${staticTime}"></script>
<script src="resources/js/pie.js?staticTime=${staticTime}"></script> --%>

<script type="text/javascript">
$(document).ready(function(){
	getJobSummarySerialChart();
	getJobSummaryDoNutChart();
	getReferToMeJobPortlets();
	getReferByMeJobPortlets();
});
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Dashboard</h2>
      </div>
    </section>
    <div class="content-new clearfix"></div>
      <div class="row">
          <div class="col-md-7">
          <div class="chartArea">
	          <div class="row">
	          <div class="col-md-3">
		          <h4>Daily Report</h4>
	          </div>
          <div class="col-md-9 select-field monthSearch_Custom">
           <div class="row">
		 		<div class="col-md-4 pull-right">
					<label class="btn_currentmonth" onclick="dailyStatusChangeMonth('currentMonth')">15 Days</label>
					<i class="fa fa-calendar pull-right" onclick="dailyStatusChangeMonth('customize')" title="Customize Dates"></i>
				</div>
	          <div class="col-md-8" style="visibility:hidden" id="dailyStatus" >
					    <div class="row">
						  <div class="col-md-5 paddingRight">
						     <input type="text" value="" placeholder="From Date" class="form-control input-sm calendar" id="serialfromdate" name="From Date" readonly="readonly">
						  </div>
				  		  <div class="col-md-5 paddingRight paddingLeft">
						     <input type="text" value="" placeholder="To Date" class="form-control input-sm calendar" id="serialtodate" name="To Date" readonly="readonly">
						  </div>
						  <div class="col-md-1 noPad">
							  <button type="button" class="btn btn-sm btn-primary" onclick="getJobSummarySerialChart();"><i class="fa fa-search"></i></button>
						  </div>
					  </div>
	 		   </div>
			 </div>
		 </div>
		 </div>
          

              <div id="chartdiv" style="width: 100%;"></div>
          </div>
          </div>

          <div class="col-md-5 text-center paddingLeft">
            <div class="chartArea">
            <div class="row">
	             <div class="col-md-3">
	            	<h4>Summary</h4>
	             </div>
		         <div class="col-md-5 monthSearch_Custom pull-right">
	           		<label class="btn_currentmonth" onclick="summaryChangeMonth('currentMonth')">Current Month</label>
					<i class="fa fa-calendar pull-right" onclick="summaryChangeMonth('customize')" title="Customize Dates"></i>
				</div>
			</div>
        <div class="row summarySearch">
        <div class="col-md-12">
				    <div class="row">
					  <div class="col-md-5 paddingRight">
					     <input type="text" value="" placeholder="From Date" class="form-control input-sm calendar" id="dpfromdate" name="From Date" readonly="readonly">
					  </div>
			  		  <div class="col-md-5 paddingLeft paddingRight">
					     <input type="text" value="" placeholder="To Date" class="form-control input-sm calendar" id="dptodate" name="To Date" readonly="readonly">
					  </div>
					  <div class="col-md-1 noPad">
						  <button type="button" class="btn btn-sm btn-primary" onclick="getJobSummaryDoNutChart();"><i class="fa fa-search"></i></button>
					  </div>
				  </div>
 		   </div>
 		   </div>
 		   <div class="content-new clearfix">
             <div id="jobSummary">
         
             </div>
          </div>
      </div></div>
      <div class="clearfix"></div>
      </div>
      
     <div class="row">
      <div class="col-md-12">
		<div class="dashboard-widget clearfix">
				    
				    <div class="row">
				    <div class="col-md-3 paddingRight">
				    	<h4>Jobs Referred To Me</h4>
				    </div>
				    
				    <div class="col-md-2 select-field monthSearch_Custom pull-right">
					    <label class="btn_currentmonth" onclick="refertomeChangeMonth('currentMonth')">Current Month</label>
					     <i class="fa fa-calendar pull-right" onclick="refertomeChangeMonth('customize')" title="Customize Dates"></i>
					  </div>
				    
					  <div style="visibility:hidden" id="customDate" class="col-md-offset-2 pull-left">
					  <div class="col-md-5 paddingRight">
					     <input type="text" value="" placeholder="From Date" class="form-control input-sm calendar" id="portletfromdate" name="From Date" readonly="readonly">
					  </div>
			  		  <div class="col-md-5 paddingLeft paddingRight">
					     <input type="text" value="" placeholder="To Date" class="form-control input-sm calendar" id="portlettodate" name="To Date" readonly="readonly">
					  </div>
					  <div class="col-md-1 noPad">
						  <button type="button" class="btn btn-sm btn-primary btn-block" onclick="getReferToMeJobPortlets();"><i class="fa fa-search"></i></button>
					  </div>
					  </div>
				  </div>
      <div id="jobPortletsDiv" class="activeJobs row"></div>
      </div>
      </div>
      <div class="clearfix"></div>
      </div>
      
      <div class="row mt">
      <div class="col-md-12">
		<div class="dashboard-widget clearfix">
				    <div class="row">
				    <div class="col-md-3 paddingRight">
				    	<h4>Candidates Referred By Me</h4>
				    </div>
				    <div class="col-md-2 select-field monthSearch_Custom pull-right">
					    <label class="btn_currentmonth" onclick="referbymeChangeMonth('currentMonth')">Current Month</label>
					     <i class="fa fa-calendar pull-right" onclick="referbymeChangeMonth('customize')" title="Customize Dates"></i>
					  </div>
					  
					  <div style="visibility:hidden" id="customReferByMeDate" class="col-md-offset-2 pull-left">
					  <div class="col-md-5 paddingRight">
					     <input type="text" value="" placeholder="From Date" class="form-control input-sm calendar" id="portletreferByMefromdate" name="From Date" readonly="readonly">
					  </div>
			  		  <div class="col-md-5 paddingLeft paddingRight">
					     <input type="text" value="" placeholder="To Date" class="form-control input-sm calendar" id="portletreferByMetodate" name="To Date" readonly="readonly">
					  </div>
					  <div class="col-md-1 noPad">
						  <button type="button" class="btn btn-sm btn-primary btn-block" onclick="getReferByMeJobPortlets();"><i class="fa fa-search"></i></button>
					  </div>
					  </div>
				  </div>
      <div id="referByMeJobPortletsDiv" class="activeJobs row"></div>
      </div>
      </div>

      </div>
	
	<!-- professional Event DatePicker  -->
	       <div class="row mt">
	       <div class="col-md-12">
	       <jsp:include page="professionalDashboardEvents.jsp"></jsp:include>
	       </div>
	       </div>
	       
	       <div class="clearfix"></div> 
	       	   
  </section>
  
  
<jsp:include page="../masterFooter.jsp" />
<div class="backtotop" style="display: none;"><img src="/resources/images/backtotop.png"></div> 

<script>
  function dailyStatusChangeMonth(type){
	    $("#serialtodate").val('');
	    $("#serialfromdate").val(''); 
	  if(type == "customize"){
		  document.getElementById("dailyStatus").style.visibility='visible';
	  }else{
		  document.getElementById("dailyStatus").style.visibility='hidden';
		  getJobSummarySerialChart();
	  } 
  }
  </script>

<script>
  function summaryChangeMonth(type){
	    $("#dpfromdate").val('');
	    $("#dptodate").val(''); 
	  if(type == "customize"){
		  $('.summarySearch').show();
	  }else{
		  $('.summarySearch').hide();
		  getJobSummaryDoNutChart();
	  } 
  }
  </script>
  
  <script>
  function refertomeChangeMonth(type){
	    $("#portletfromdate").val('');
	    $("#portlettodate").val(''); 
	    
	  if(type == "customize"){
		  document.getElementById("customDate").style.visibility='visible';
	  }else{
		  document.getElementById("customDate").style.visibility='hidden';
		  getReferToMeJobPortlets();
	  } 
  }
  </script>
  
  
  
  <script>
  function referbymeChangeMonth(type){
	  $("#portletreferByMefromdate").val('');
	  $("#portletreferByMetodate").val(''); 
	    
	  if(type == "customize"){
		  document.getElementById("customReferByMeDate").style.visibility='visible';
	  }else{
		  document.getElementById("customReferByMeDate").style.visibility='hidden';
		  getReferByMeJobPortlets();
	  }
  }
  </script>
  
<script>
function flushDate(){
	$("#datepicker-int").val('');
}
</script>

	 
  <script>
  function getReferToMeJobPortlets(){
	  
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
              result = result +'<span class="label label-warning">Referred To Me</span>';
              result = result +'<img src="/resources/images/refer-icon.png" title="Referrals" alt="Referrals">';
              if(json['referToMeJobs'] > 0){
            	  result = result +'<a href="#" onClick="getReferredToMeProtletList(\''+startDate+'\',\''+endDate+'\',\'referToMe\');"><span class="count text-primary">'+json['referToMeJobs']+'</span></a>';
              }else{
            	  result = result +'<span class="count">0</span>';
              }
              
              result = result +'</div>';
              result = result +' </div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-success">Applied</span>';
                        
              result = result +'<img src="/resources/images/applied_icon.png" title="Applied" alt="Applied">';
              if(json['appliedJobs'] > 0){
            	  result = result +'<a href="#" onClick="getReferredToMeProtletList(\''+startDate+'\',\''+endDate+'\',\'\');"><span class="count text-primary">'+json['appliedJobs']+'</span></a>';  
              }else{
            	  result = result +'<span class="count text-primary">0</span>';
              }
              result = result +'</div>';

              result = result +'</div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-info">Shortlisted</span>';
              result = result +'<img src="/resources/images/short_listed.png" title="Shortlisted" alt="Shortlisted">';
              if(json['shortlistJobs'] > 0){
            	  result = result +'<a href="#" onClick="getReferredToMeProtletList(\''+startDate+'\',\''+endDate+'\',\'SHORTLIST\');"><span class="count text-primary">'+json['shortlistJobs']+'</span></a>';  
              }else{
            	  result = result +'<span class="count">0</span>';
              }
              
              result = result +'</div>';

              result = result +'</div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-success">Interviews</span>';
              result = result +'<img src="/resources/images/interview_icons.png" title="Interviews" alt="Interviews">';
              if(json['interviews'] > 0){
            	  result = result +'<a href="#" onClick="getReferredToMeProtletList(\''+startDate+'\',\''+endDate+'\',\'INTERVIEW\');"><span class="count text-primary">'+json['interviews']+'</span></a>';  
              }else{
            	  result = result +'<span class="count">0</span>';
              }
              
              result = result +'</div>';

              result = result +'</div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-info">Offered</span>';
                        
              result = result +'<img src="/resources/images/offered_icon.png" title="Offered" alt="Offered">';
              if(json['offeredJobs'] > 0){
            	  result = result +'<a href="#" onClick="getReferredToMeProtletList(\''+startDate+'\',\''+endDate+'\',\'OFFERMADE\');"><span class="count text-primary">'+json['offeredJobs']+'</span></a>';  
              }else{
            	  result = result +'<span class="count">0</span>';
              }
              
              result = result +'</div>';

              result = result +'</div>';
              
/*               result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-primary">Hired</span>';
              
              result = result +'<img src="/resources/images/hired_icon.png" title="Hired" alt="Hired">';
              result = result +'<span class="count">0</span>';
              result = result +'</div>';

              result = result +'</div>'; */
              
              
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-danger">Rejected';
              result = result +'</span>';
              
              result = result +'<img src="/resources/images/reject_icon.png" title="Rejected" alt="Rejected">';
              if(json['rejectedJobs'] > 0){
            	  result = result +'<a href="#" onClick="getReferredToMeProtletList(\''+startDate+'\',\''+endDate+'\',\'REJECTED\');"><span class="count text-primary">'+json['rejectedJobs']+'</span></a>';
              }else{
            	  result = result +'<span class="count">0</span>';
              }
             
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
function getReferredToMeProtletList(startDate,endDate,status){
	window.location.href = sourceleadUrl + '/referredToMePortletList?${_csrf.parameterName}=${_csrf.token}&startDate='+startDate+'&endDate='+endDate+'&requirementStatus='+status;
}
</script>
  
  
  <script>
  function getReferByMeJobPortlets(){
	  
	var startDate = document.getElementById("portletreferByMefromdate").value; 
	var endDate = document.getElementById("portletreferByMetodate").value; 
	  
	$.ajax({ 
		   	type: "GET",
		   	url: sourceleadUrl+"/getReferByMeJobSummaryPortlets?${_csrf.parameterName}=${_csrf.token}&startDate="+startDate+"&endDate="+endDate,
		    success: function(data) {
	    	 var json = JSON.parse(data);
		    	
	    	  var result = ''
			 
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-warning">Referred By Me</span>';
              result = result +'<img src="/resources/images/refer-icon.png" title="Referrals" alt="Referrals">';
          	 if(json['referByMeJobs'] > 0){
          		result = result +'<a href="#" onClick="getReferredByMeProtletList(\''+startDate+'\',\''+endDate+'\',\'referByMe\');"><span class="count text-primary">'+json['referByMeJobs']+'</span></a>'; 
          	 }else{
          		result = result +'<span class="count">0</span>';
          	 }
              
              result = result +'</div>';
              result = result +' </div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-success">Applied</span>';
                        
              result = result +'<img src="/resources/images/applied_icon.png" title="Applied" alt="Applied">';
              if(json['appliedJobs'] > 0){
            	  result = result +'<a href="#" onClick="getReferredByMeProtletList(\''+startDate+'\',\''+endDate+'\',\'\');"><span class="count text-primary">'+json['appliedJobs']+'</span></a>';  
              }else{
            	  result = result +'<span class="count text-primary">0</span>';
              }
              result = result +'</div>';

              result = result +'</div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-info">Shortlisted</span>';
              result = result +'<img src="/resources/images/short_listed.png" title="Shortlisted" alt="Shortlisted">';
              if(json['shortlistJobs'] > 0){
            	  result = result +'<a href="#" onClick="getReferredByMeProtletList(\''+startDate+'\',\''+endDate+'\',\'SHORTLIST\');"><span class="count text-primary">'+json['shortlistJobs']+'</span></a>';  
              }else{
            	  result = result +'<span class="count">0</span>';
              }
              
              result = result +'</div>';

              result = result +'</div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-success">Interviews</span>';
              result = result +'<img src="/resources/images/interview_icons.png" title="Interviews" alt="Interviews">';
              if(json['interviews'] > 0){
            	  result = result +'<a href="#" onClick="getReferredByMeProtletList(\''+startDate+'\',\''+endDate+'\',\'INTERVIEW\');"><span class="count text-primary">'+json['interviews']+'</span></a>';  
              }else{
            	  result = result +'<span class="count">0</span>';
              }
              
              result = result +'</div>';

              result = result +'</div>';
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-info">Offered</span>';
                        
              result = result +'<img src="/resources/images/offered_icon.png" title="Offered" alt="Offered">';
              if(json['offeredJobs'] > 0){
            	  result = result +'<a href="#" onClick="getReferredByMeProtletList(\''+startDate+'\',\''+endDate+'\',\'OFFERMADE\');"><span class="count text-primary">'+json['offeredJobs']+'</span></a>';  
              }else{
            	  result = result +'<span class="count">0</span>';
              }
              
              result = result +'</div>';

              result = result +'</div>';
             
          /*  result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-primary">Hired</span>';
              
              result = result +'<img src="/resources/images/hired_icon.png" title="Hired" alt="Hired">';
              result = result +'<span class="count">0</span>';
              result = result +'</div>';

              result = result +'</div>'; */
             
              
              result = result +'<div class="col-sm-6 col-md-3">';
              result = result +'<div class="content_widget jobs-graphview buttonView">';
              result = result +'<span class="label label-danger">Rejected';
              result = result +'</span>';
              
              result = result +'<img src="/resources/images/reject_icon.png" title="Rejected" alt="Rejected">';
              if(json['rejectedJobs'] > 0){
            	  result = result +'<a href="#" onClick="getReferredByMeProtletList(\''+startDate+'\',\''+endDate+'\',\'REJECTED\');"><span class="count text-primary">'+json['rejectedJobs']+'</span></a>';
              }else{
            	  result = result +'<span class="count">0</span>';
              }
             
              result = result +'</div>';

              result = result +'</div>';
              
              $("#referByMeJobPortletsDiv").empty(); 
              $("#referByMeJobPortletsDiv").append(result); 
		    },
	        error: function(e){
	        }
	 });
  }
  
  </script>
  
<script>
	function getReferredByMeProtletList(startDate,endDate,status){
			window.location.href = sourceleadUrl + '/referredByMePortletList?${_csrf.parameterName}=${_csrf.token}&startDate='+startDate+'&endDate='+endDate+'&requirementStatus='+status;
	}
</script>
  
  
 <script>
  function getJobSummaryDoNutChart(){
	
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
			    "title": "Referred\n To Me ("+referToMeJobs+")",
			    "value": referToMeJobs
			  }, {
			    "title": "Referred\n By Me ("+referByMeJobs+")",
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
	        "balloonText": "Referred To Me: [[value]]",
	        "bullet": "round",
	        "title": "Referred To Me",
	        "valueField": "referedToMe",
			"fillAlphas": 0
	    }, {
	        "balloonText": "Referred By Me: [[value]]",
	        "bullet": "round",
	        "title": "Referred By Me",
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
	  
	  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormatProDashboard = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormatProDashboard = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormatProDashboard = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormatProDashboard = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormatProDashboard = 'yy-dd-mm'; 
	  }
	  
	  $('#dpfromdate').datepicker({
	      dateFormat: finalDateFormatProDashboard,
	      changeMonth: true,
	  	  changeYear: true, 
	  	  onSelect : function(selected) {
				//var dt = new Date(selected);
				var dt = $(this).datepicker('getDate', '+1d');
				dt.setDate(dt.getDate() + 1);
				$("#dptodate").datepicker("option", "minDate", dt);
			}
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  
	  $('#dptodate').datepicker({
	      dateFormat: finalDateFormatProDashboard,
	      changeMonth: true,
	  	  changeYear: true, 
	  	   onSelect : function(selected) {
			//var dt = new Date(selected);
			var dt = $(this).datepicker('getDate', '-1d');
			dt.setDate(dt.getDate() - 1);
			$("#dpfromdate").datepicker("option", "maxDate", dt);
	  	}
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  

	  $('#portletfromdate').datepicker({
	      dateFormat: finalDateFormatProDashboard,
	      changeMonth: true,
	  	  changeYear: true, 
	  	  onSelect : function(selected) {
				//var dt = new Date(selected);
				var dt = $(this).datepicker('getDate', '+1d');
				dt.setDate(dt.getDate() + 1);
				$("#portlettodate").datepicker("option", "minDate", dt);
			}
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  
	  $('#portlettodate').datepicker({
	      dateFormat: finalDateFormatProDashboard,
	      changeMonth: true,
	  	  changeYear: true, 
		  onSelect : function(selected) {
				//var dt = new Date(selected);
				var dt = $(this).datepicker('getDate', '-1d');
				dt.setDate(dt.getDate() - 1);
				$("#portletfromdate").datepicker("option", "maxDate", dt);
		  	}
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  
	  $('#serialfromdate').datepicker({
	      dateFormat: finalDateFormatProDashboard,
	      changeMonth: true,
	  	  changeYear: true, 
		  onSelect : function(selected) {
				var dt = new Date(selected);
				dt.setDate(dt.getDate() + 1);
				$("#serialtodate").datepicker("option", "minDate", dt);
			}
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  
	  $('#serialtodate').datepicker({
	      dateFormat: finalDateFormatProDashboard,
	      changeMonth: true,
	  	  changeYear: true, 
	  	  onSelect : function(selected) {
			var dt = new Date(selected);
			dt.setDate(dt.getDate() - 1);
			$("#serialfromdate").datepicker("option", "maxDate", dt);
	  	}
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  
	  
	  $('#portletreferByMefromdate').datepicker({
	      dateFormat: finalDateFormatProDashboard,
	      changeMonth: true,
	  	  changeYear: true, 
	  	  onSelect : function(selected) {
				var dt = new Date(selected);
				dt.setDate(dt.getDate() + 1);
				$("#portletreferByMetodate").datepicker("option", "minDate", dt);
			}
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  
	  $('#portletreferByMetodate').datepicker({
	      dateFormat: finalDateFormatProDashboard,
	      changeMonth: true,
	  	  changeYear: true, 
		  onSelect : function(selected) {
				var dt = new Date(selected);
				dt.setDate(dt.getDate() - 1);
				$("#portletreferByMefromdate").datepicker("option", "maxDate", dt);
		  	}
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  
	  
      
  });
</script>

<script>
// Back To Top 
    $(window).scroll(function() {
        if ($(this).scrollTop() > 200) {
            $('.backtotop').fadeIn('slow');
        } else {
            $('.backtotop').fadeOut('slow');
        }
    });
    $('.backtotop').click(function() {
        $("html, body").animate({scrollTop: 0}, 500);
        return false;
    });
</script>