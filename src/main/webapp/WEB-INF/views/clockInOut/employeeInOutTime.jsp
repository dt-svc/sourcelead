<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />


  
  <script type="text/javascript" src="https://www.amcharts.com/lib/3/amcharts.js"></script> 
<script type="text/javascript" src="https://www.amcharts.com/lib/3/serial.js"></script> 
<script type="text/javascript" src="https://www.amcharts.com/lib/3/pie.js"></script>
<script type="text/javascript" src="../js/amcharts_export.min.js"></script>

<script>
var sourceleadUrl = '${SourceLeadWeb_sourceleadUrl}';

$('document').ready(function () {
	 getUserTimeEntries();
});
</script>


<!-- Content Area --> 
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Clock in List</h2>
      </div>
      <div class="pull-right brud-crum">Home  &raquo; Clock in list</div>
    </section>

    <div class="clockListWrap clearfix">
            <div class="positionRelative reportHead clearfix">

                <div class="pull-left timeRepList">
                    <a title="Graphical View" id="graphicalId" onclick="graphicalViewPage();"><i class="fa fa-pie-chart"></i></a>
                    <a title="List View" id="listId" class="act" onclick="listViewPage();"><i class="fa fa-list"></i></a>
                    </div> 
                    
                         <div id="graphViewId">

            <div class="clDate">
                <i class="fa fa-chevron-left"></i>
                <p>11-03-19 to 18-03-19</p>
                <i class="fa fa-chevron-right"></i>
              </div>            

   

    <div class="repclo" id="graphId"  style="display: none;">
        <div id="jobsAdded" class="amchartAnchor-Hide timRep"></div>
    </div>

  </div>    
				<div id="listDivId">
                    <ul class="pull-right list-unstyled list-inline">
                      <li><a class="filtIcon" id="filPop"></a>
                        <div class="approved_pop">
                            <ul>
                                <li><input type="checkbox" id="a"><label for="a">Today</label></li> 
                                <li><input type="checkbox" id="b"><label for="b">Yesterday</label></li> 
                                <li><input type="checkbox" id="c"><label for="c">Last Week</label></li> 
                                <li><input type="checkbox" id="d"><label for="d">This Week</label></li> 
                                <li><input type="checkbox" id="d"><label for="d">This Month</label></li> 
                                <li><input type="checkbox" id="d"><label for="d">Last Month</label></li> 
                            </ul>
                          </div>                      
                      </li>
                      <li><a href="javascript:void(0)" class="overIcon" title="Actions" id="exPop"></a>
                        <div class="newActPop">
                            <span>
                               <a href="javascript:void(0)" onclick="exportToPdf('pdf');" title="PDF">Export PDF</a>
                              <a href="javascript:void(0)" onclick="exportToPdf('excel');" title="Excel">Export Excel</a>
                            </span>
                          </div>                      
                      </li>
                      <li class="">
                        <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
                        <span class="repSearchIcon" onclick="getDynamciUserList();"></span></li>
                    </ul>
                    </div>
                  </div>
               </div>
               
               
               
               <div class="clockTable tableFirstSecond clearfix">
               <div id="userTimeEntriesDiv" class="datatable_filter"></div>
               </div>
  


<script>
    // Jobs added Column Chart
      AmCharts.makeChart("jobsAdded",
      {
      "type": "serial",
      "categoryField": "category",
      "dataDateFormat": "YYYY-MM-DD",
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
          "title": "Jobs Added",
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
      "dataProvider": [
        {
          "category": "2019-03-11",
          "column-1": "10"
        },
        {
          "category": "2019-03-12",
          "column-1": "6"
        },
        {
          "category": "2019-03-13",
          "column-1": "16"
        },
        {
          "category": "2019-03-14",
          "column-1": "6"
        },
        {
          "category": "2019-03-15",
          "column-1": "2"
        },
        {
          "category": "2019-03-16",
          "column-1": "4"
        },
        {
          "category": "2019-03-17",
          "column-1": "8"
        }                                                
      ]
    });
    </script>
    
    
    

  </section>
  
  <script>
  function graphicalViewPage(){
	  
	  $("#listId").removeClass("act");
	  $("#graphicalId").addClass("act");
	  document.getElementById("graphId").style.display="block";
	  document.getElementById("userTimeEntriesDiv").style.display="none";
	  document.getElementById("listDivId").style.display="none";
  }
  
  
  function listViewPage(){
	  $("#graphicalId").removeClass("act");
	  $("#listId").addClass("act");
	  document.getElementById("userTimeEntriesDiv").style.display="block";
	  document.getElementById("listDivId").style.display="block";
	  document.getElementById("graphId").style.display="none";
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
 function getUserTimeEntries(){
		
		$.ajax({
			type : "GET",
			url : sourceleadUrl+ "/getUserTimeEntries?${_csrf.parameterName}=${_csrf.token}",
			success : function(json) {
   	        	var result = "";
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
	    	  	    result = result + '    <td><span class="click">'+report.clockInDateStr+'<i class="fa fa-caret-down"></i></span></td>                            '; 

	            	  result = result + '    <td>08</td>         ';
   		    		
	            	  result = result + '    <td>&nbsp;</td>   ';
	            	  
	            	  result = result + '    <td>&nbsp;</td>  ';
	            	  
                	  result = result + '    <td>&nbsp;</td>              ';
                	  
                     result = result + '    <td>'+report.clockOutDateStr+'</td>  ';

   		    		
   		    		
   		    	  	for(var j=0; j<report.subList.length; j++){
   		    			var opt = report.subList[j];
   		    			
   		    		   result = result +'	 <tr class="subTab child-2479'+i+'" >              '; 
   					   result = result +'	        <td>'+opt.clockInTime+' - '+opt.clockOutTime+'</td>                    '; 
   					   result = result +'	        <td>'+opt.individualNoOfHours+'</td>                                     '; 
   					   result = result +'	        <td>'+opt.taskName+'</td>                                 '; 
   					   result = result +'	        <td>Hyderabad</td>                              '; 
   					   result = result +'	        <td>'+opt.comments+'</td>                       '; 
   					   result = result +'	        <td>'+opt.clockOutDate+'</td>                             '; 
   					   result = result +'	  </tr>                                                 '; 
   		    			
   		    		}  
   		    	 result = result +'</tbody>';
   				}
   		    	
   		    	$("#userTimeEntriesDiv").empty();  
   	    	    $("#userTimeEntriesDiv").append(result);
   	    	 userTimeEntriesPagination();
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
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		"sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>

<script>
$(function() {
	alert(1)
    $('tr.parent td span.click').on("click", function(){
            var idOfParent = $(this).parents('tr').attr('id');
            $('tr.child-'+idOfParent).toggle(100);
        });
    $('tr[class^=child-]').hide().children('td');
});
</script> 

<!-- <script>
function clickTab(){ 
	$(".subTab").toggle();
}
</script> -->


  
  <jsp:include page="../masterFooter.jsp" />
  
  
  