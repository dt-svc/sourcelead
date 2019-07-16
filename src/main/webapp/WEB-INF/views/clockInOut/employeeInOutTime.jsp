<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />


  
  <script type="text/javascript" src="https://www.amcharts.com/lib/3/amcharts.js"></script> 
<script type="text/javascript" src="https://www.amcharts.com/lib/3/serial.js"></script> 
<script type="text/javascript" src="https://www.amcharts.com/lib/3/pie.js"></script>
<script type="text/javascript" src="../js/amcharts_export.min.js"></script>

<script>
var sourceleadUrl = '${SourceLeadWeb_sourceleadUrl}';

$('document').ready(function () {
	 getCurrentDates('today');
	 
});
</script>


<!-- Content Area --> 
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Clock in List</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a>&raquo; Clock in list</div>
    <div id="clockLoading"></div>
    </section>

    <div class="clockListWrap clearfix">
            <div class="positionRelative reportHead clearfix">

                <div class="pull-left timeRepList">
                    <a title="Graphical View" id="graphicalId" onclick="graphicalViewPage();"><i class="fa fa-pie-chart"></i></a>
                    <a title="List View" id="listId" class="act" onclick="listViewPage();"><i class="fa fa-list"></i></a>
                    </div> 
                    
                         <div id="graphViewId">

            <div class="clDate">
                <i class="fa fa-chevron-left" id="previousId" onclick="getCurrentDates('previous');"></i>
                <p id="frequencyId"></p>
                <i class="fa fa-chevron-right" id="nextId" onclick="getCurrentDates('next');"></i>
              </div>            


  </div>    
				<div id="listDivId">
                    <ul class="pull-right list-unstyled list-inline dataTabPad">
                      <li id="filterId"><a class="filtIcon" id="filPop"></a>
                        <div class="approved_pop">
                            <ul>
                                <li><input type="radio" name="a" value="today" checked id="a" onclick="getCurrentDates('today');"><label for="a">Today</label></li> 
                                <li><input type="radio" name="a" value="Last_7_days" id="b" onclick="getCurrentDates('Last_7_days');"><label for="b">This Week</label></li> 
                                <li><input type="radio" name="a" value="current_month" id="c" onclick="getCurrentDates('current_month');"><label for="c">This Month</label></li> 
                                <li><input type="radio" name="a" value="Current_Year" id="d" onclick="getCurrentDates('Current_Year');"><label for="d">This Year</label></li> 
                            </ul>
                          </div>                      
                      </li>
                  
                    </ul>
                    </div>
                    
                     <div class="repclo" id="graphId"  style="display: none;">
        <div id="jobsAdded" class="amchartAnchor-Hide timRep"></div>
        <div id="graphIdDiv" class=""></div>
    </div>
                    
                  </div>
               </div>
               
               
               
               <div class="clockTable tableFirstSecond clearfix">
               <div id="userTimeEntriesDiv" class="datatable_filter"></div>
               </div>
  



    
    
    

  </section>
  
  <script>
  function graphicalViewPage(){
	  
	  $("#listId").removeClass("act");
	  $("#graphicalId").addClass("act");
	  document.getElementById("graphId").style.display="block";
	  document.getElementById("userTimeEntriesDiv").style.display="none";
	  document.getElementById("searchId").style.display="none";
  }
  
  
  function listViewPage(){
	  $("#graphicalId").removeClass("act");
	  $("#listId").addClass("act");
	  document.getElementById("userTimeEntriesDiv").style.display="block";
	  document.getElementById("listDivId").style.display="block";
	  document.getElementById("graphId").style.display="none";
	  document.getElementById("searchId").style.display="block";
  }
  
//clock list pop 
  $("#filPop").click(function() {
  	$(".approved_pop").slideToggle(100);
  	$(".newActPop").slideUp(100);
  });
  $("#exPop").click(function() {
  	$(".newActPop").slideToggle(100);
  	$(".approved_pop").slideUp(100);
  });
  
 $("body").click(function(){
	 $(".newActPop, .approved_pop").slideUp(100);
 }); 
 $("#filPop, #exPop, .newActPop, .approved_pop").click(function(e) {
	   e.stopPropagation();
 });
  
//list sub pop 
  $(".clSub").click(function() {
  	$(this).parent().find(".clSubView").slideToggle(100);
  });

  $("#clSub").click(function(){
    $(".clockSub").slideToggle(10);
  });
  
  </script>
  
 <!--  <script>
  function getUserTimeEntries(){
	var searchValue = $("#dataTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	var url = sourceleadUrl+"/getUserTimeEntries?searchValue="+searchValue;
	getUserTimeEntriesTable(url);	
  }
  
  </script>
  
  <script>
	function getUserTimeEntriesTable(url) {
		
		var result = "";
		result = result + '<table id="userEntryTable" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Clock In</th>';
		result = result + '<th>No of hours</th>';
		result = result + '<th>Task</th>';
		result = result + '<th>Location</th>';
		result = result + '<th>Comments</th>';
		result = result + '<th>Clock Out</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
		result = result + "</table>";
		$("#userTimeEntriesDiv").empty();
		$("#userTimeEntriesDiv").append(result);
		getUserTimeEntriesPagination(url);

	}
</script>

<script>
function getUserTimeEntriesPagination(url){
	var colIndex = 0;
    $('#userEntryTable').DataTable( { 
    	"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'GET',
            pages: 10
        }),
        "columns": [
            { "data": "clockIn" },
            { "data": "noOfHours"},
            { "data": "task"},
            { "data": "location"},
            { "data": "comments"},
            { "data": "clockOut"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "clockIn",
	    	  	"render": function(data, type, opt, meta) {
	    	  		
	    	  		var result = "";
	    	  		result = result + '	 <tr class="parent" id="2479">        ';
	    	  	    result = result + '    <td><span class="click">12-04-2019 <i class="fa fa-caret-down" aria-hidden="true"></i></span></td>                            '; 
	    	  		return result; 
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "noOfHours",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  result = result + '    <td>08</td>         ';
            	  return result;
              		
              }
	          },
	      
	      {
	    	  "targets": colIndex++,
              "data": "task",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  result = result + '    <td>&nbsp;</td>   ';
            	  return result;
              		
              }
	          },
	          {
		    	  "targets": colIndex++,
	              "data": "location",
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  result = result + '    <td>&nbsp;</td>  ';
	            	  return result;
	              		
	              }
		          },{
                      "targets": colIndex++,
                      "data": "comments",
                      "render": function ( data, type, opt, meta ) {
                    	  var result = "";
                    	  result = result + '    <td>&nbsp;</td>              ';
      	    	  		return result;
                      }
               },{
                     "targets": colIndex++,
                     "data": "clockOut",
                     "render": function ( data, type, opt, meta ) {
                   	  var result = "";
                   	result = result + '    <td>12-04-2019</td>  ';
     	    	  		return result;
     	    	  		result = result + '  </tr>  ';
                     }
                   } ],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
// 	    	"aaSorting": [[3,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#offerpagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
//     		   getApplicantsJoinedList();
    	   }
    	});
	
}

</script> -->



<script>
 function getUserTimeEntries(type){
	 var currentDate = $("#frequencyId").html();
	 var dayWisetype = null;
	 $('input:radio[name=a]:checked').each(function() {
		 dayWisetype = $(this).val();
	 });
		$("#clockLoading").attr('style','position:absolute; width:100%; height:250%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
			type : "GET",
			url : sourceleadUrl+ "/getUserTimeEntries?${_csrf.parameterName}=${_csrf.token}&dayWisetype="+dayWisetype+"&type="+type+"&currentDate="+currentDate,
			success : function(json) {
   	        	var result = "";
//    	        	var frequency = "";
   	        	result = result +'<table id="userTimePagination">';
   		    	result = result +'<thead>';
   		    	result = result +'	<tr>';
	   		 	result = result + '<th>Clock In</th>';
	   			result = result + '<th>No of hours</th>';
	   			result = result + '<th>Task</th>';
	   			result = result + '<th>Location</th>';
	   			result = result + '<th>Comments</th>';
	   			result = result + '<th>Clock Out</th>';
   		    	result = result +'    </tr>';
   		    	result = result +'</thead>	';
   		    	
   		    	for (var i=0; i<json.data.length; i++)
   				{
   		    		var report = json.data[i];
   		    		result = result +'<tbody>';
   		    		
   		    		result = result +'<tr class="parent" id="2479'+i+'">';
   		    		if(dayWisetype == 'Current_Year'){
   		    	  	    result = result + '    <td><span class="click">'+report.clockInDateStr+'</span></td>                            '; 
   		    		}else{
   		    	  	    result = result + '    <td><span class="click">'+report.clockInDateStr+'<i class="fa fa-caret-down"></i></span></td>                            '; 
   		    		}

	            	  result = result + '    <td>'+report.totalTime+'</td>         ';
	            	  
	            	  result = result + '    <td>&nbsp;</td>   ';
	            	  
  					   if(report.geoLocationData != null && report.geoLocationData != "undefined" && report.geoLocationData != ""){
  		            	  result = result + '    <td>'+report.geoLocationData+'</td>  ';
  					   }else{
  		            	  result = result + '    <td></td>  ';
  					   }
	            	  
                	  result = result + '    <td>&nbsp;</td>              ';
                	  
                     result = result + '    <td>'+report.clockOutDateStr+'</td>  ';

   		    		
                     if("Current_Year" != type){
                    		for(var j=0; j<report.subList.length; j++){
           		    			var opt = report.subList[j];
         		    		   result = result +'	 <tr class="subTab child-2479'+i+'" >              '; 
           					   result = result +'	        <td>'+opt.clockInTime+' - '+opt.clockOutTime+'</td>                    '; 
           					   result = result +'	        <td>'+opt.individualNoOfHours+'</td>                                     '; 
           			
           					   if(opt.taskName != null && opt.taskName != "undefined" && opt.taskName != ""){
           	   					   result = result +'	        <td>'+opt.taskName+'</td>                                 '; 
           					   }else{
           	   					   result = result +'	        <td>--</td>                                 '; 

           					   }
           					   
           					  if(opt.comments != null && opt.comments != "undefined" && opt.comments != ""){
          	   					   result = result +'	        <td>'+opt.comments+'</td>                                 '; 
          					   }else{
          	   					   result = result +'	        <td>--</td>                                 '; 

          					   }
           					   result = result +'	        <td>--</td>                              '; 
           					   result = result +'	        <td>'+opt.clockOutDate+'</td>                             '; 
           					   result = result +'	  </tr>                                                 '; 
           		    			
           		    		}  
                     }
   		    	  
   		    	 result = result +'</tbody>';
   				}
   		    	
   		    	$("#userTimeEntriesDiv").empty();  
   	    	    $("#userTimeEntriesDiv").append(result);
   	    	 userTimeEntriesPagination();
   	    	 $("#clockLoading").removeAttr('style');
   	    	 setTimeout('refresh()', 500);
   	    	showClockInClockOutGraph(json,type);
			},
		});
 }
 </script>
 
 <script>
function userTimeEntriesPagination(){  
    $('#userTimePagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": "",
     		"sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>

<script>

function refresh(){
	$('tr.parent td span.click').on("click", function(){
        var idOfParent = $(this).parents('tr').attr('id');
        $('tr.child-'+idOfParent).toggle(100);
    });
$('tr[class^=child-]').hide().children('td');
}
</script> 

<script>
function getCurrentDates(type){
	var currentDate = $("#frequencyId").html();
	 var dayWisetype = null;
	 $('input:radio[name=a]:checked').each(function() {
		 dayWisetype = $(this).val();
	 });
	$.ajax({
		type : "GET",
		url : sourceleadUrl+ "/getCurrentDates?${_csrf.parameterName}=${_csrf.token}&type="+type+"&currentDate="+currentDate+"&filterType="+dayWisetype,
		success : function(dates) {
			
			if(dates != null && dates != "" && dates != "undefined"){
				$("#frequencyId").html(dates);
				getUserTimeEntries(type);
				$("#previousId").show();
				$("#nextId").show();
			}else{
				if(type == 'previous'){
					$("#previousId").hide();
					$("#nextId").show();
				}else if(type == 'next'){
					$("#nextId").hide();
					$("#previousId").show();
				}
			}
		}
});
}

</script>

<script>
function showClockInClockOutGraph(json,type){
	if(json.data.length >0){
		$("#graphIdDiv").removeClass('dataTables_empty'); 
		$("#jobsAdded").show();
		var format = "";
		 if("Current_Year" != type){
			 format =  "YYYY-MM-DD";
		 }else{
			 format =  "YYYY-MM";
		 }
		 var chartData = [];
		   for (var i = 0; i < json.data.length; i++) { 
		 		 var opt = json.data[i]; 
		 		 chartData.push({
		        		"category": opt.graphForamatDate,
				         "column-1": opt.graphTotalTime
		       });
		   }
		AmCharts.makeChart("jobsAdded",
			      {
			      "type": "serial",
			      "categoryField": "category",
			      "dataDateFormat": format,
			      "colors": [],
			      "startDuration": 1,
			      "categoryAxis": {
			        "equalSpacing": true,
			        "gridPosition": "start",
			        "parseDates": true,
			        "axisColor": "#ACACAC",
			        "gridThickness": 0,
			        "markPeriodChange": false,
			        "minHorizontalGap": 35,
			        "minVerticalGap": 25
			      },
			      "trendLines": [],
			      "graphs": [
			        {
			          "balloonText": "[[title]] : [[value]]",
			          "bulletField": "category",
			          "fillAlphas": 1,
			          "fillColors": "#6AB7F3",
			          "fixedColumnWidth": 30,
			          "id": "jobsAdded",
			          "lineColor": "#6AB7F3",
			          "title": "Clock In/Out Time",
			          "type": "column",
			          "valueField": "column-1"
			        }
			      ],
			      "guides": [],
			      "valueAxes": [
			        {
			          "id": "ValueAxis-1",
			          "axisColor": "#ACACAC",
			          "firstDayOfWeek": 0,
			          "gridThickness": 0,
			          "title": ""
			        }
			      ],
			      "allLabels": [],
			      "balloon": {
			        "fixedPosition": false,
			        "horizontalPadding": 10,
			        "textAlign": "left",
			        "verticalPadding": 10
			      },
			      "titles": [],
			      "dataProvider": chartData
			    });
	}else{
		$("#graphIdDiv").addClass('dataTables_empty');
		$("#jobsAdded").hide();
	}
	
}
    </script>

  
  <jsp:include page="../masterFooter.jsp" />
  
  
  