<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
		$(document).ready(function() {
			getCampaignStats();
		});
	</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Campaigns Stats</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='hotListCampaigns'/>">Bank Office</a> &raquo; Campaigns Stats</div>
    </section>
      <div class="clearfix">
          <div class="clearfix tableStyles">
          <div id="campaignStatsDev" class="datatable_filter"></div>
        </div>
      </div>
  </section>
          
<script type="text/javascript">
function getCampaignStats(){
		var searchValue = $("#campaignStatsTableSearch").val();
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getCampaignStats?searchValue="+searchValue+"&orgLocationId="+orgLocationId;
	    prepareCampaignStatsList(url);
}
</script> 

<script>
function prepareCampaignStatsList(url){
	 $("#campaignStatsDev").removeAttr("style");
 	 $("#campaignStatsDev").empty();
 	 var result = "";
	 	result = result +" <div class='tableStyles_head clearfix positionRelative'>";
	 	result = result +" <div class='remove_search'>";
	 	result = result +" <input type='text' placeholder='Search' id='campaignStatsTableSearch'/>";
	 	result = result +"</div>";
	 	result = result +"</div>";
 	    result = result +"<table id='campaignStatsPagination' class='display table table-bordered' cellspacing='0' width='99.9%' >"; 
 	    result = result + '<thead>';
		result = result + '<tr>';
		result = result +' <th>Category Name</th>';
	    result = result +' <th>Opens</th>';
	    result = result +' <th>Delivered</th>';
	    result = result +' <th>Unique Opens</th>';
	    result = result +' <th>Unique Clicks</th>';
	    result = result +' <th>Unsubscribes</th>';
	    result = result +' <th>Actions</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
        result = result +"</table>";
      $("#campaignStatsDev").append(result);
      campaignStatsPagination(url); 
}
</script>


<script>
function campaignStatsPagination(url){
	var colIndex = 0;
    $('#campaignStatsPagination').DataTable( { 
   		"processing": true,
       "serverSide": true,
       "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'GET',
            pages: 10
        }),
        "columns": [
            { "data": "categoryName" },
            { "data": "opens"},
            { "data": "delivered"},
            { "data": "uniqueOpens" },
            { "data": "uniqueClicks"},
            { "data": "unsubscribes" },
            { "data": "action" }
            

        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "categoryName",
	    	  	"render": function(data, type, report, meta) {
	    	  		  var result = "";
	    	  		   result = result +'  <div class="tbldata-dotdot-sm"><a href="'+sourceleadUrl+'/campaignStatsGraph?campaignId='+report.campaignId+'" title="'+report.categoryName+'">'+report.categoryName+'</a></div>';
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "opens",
              "render": function ( data, type, report, meta ) {
            	  var result = "";
            	   result = result +    '<div class="tbldata-dotdot-sm" title="'+report.opens+'">'+report.opens+'</div>';
	              return result;
              		
              }
	          },{
            "targets": colIndex++,
            "data": "delivered",
            "render": function ( data, type, report, meta ) {
            	var result = "";
            	  result = result +    '<div class="tbldata-dotdot-sm" title="'+report.delivered+'">'+report.delivered+'</div>';
	            return result;
            }
          }, {
              "targets": colIndex++,
              "data": "uniqueOpens",
              "render": function ( data, type, report, meta ) {
            	var result = "";
            	  result = result +    '<div class="tbldata-dotdot-sm" title="'+report.uniqueOpens+'">'+report.uniqueOpens+'</div>';
            	 return result;
              }
            }, {
                "targets": colIndex++,
                "data": "uniqueClicks",
                "render": function ( data, type, report, meta ) {
                	var result = "";
                		result = result +    '<div class="tbldata-dotdot-sm" title="'+report.uniqueClicks+'">'+report.uniqueClicks+'</div>';
    	              return result;
                }
          },{
	    	  	"targets": colIndex++,
	    	  	"data": "unsubscribes",
	    	  	"render": function(data, type, report, meta) {
	    	  		var result = "";
	    	  		result = result +    '<div class="tbldata-dotdot-sm" title="'+report.unsubscribes+'">'+report.unsubscribes+'</div>';
  	                return result;
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "action",
	    	  	"render": function(data, type, report, meta) {
	    	  		var result = "";
	    	  		result = result +'  <td>';                                                                                                                  
	    	           result = result +'    <div class="action-div tblminwidth-50 text-center">';
	    	           result = result +'    <i class="fa fa-ellipsis-h cursorPointer"></i>';
	    	           result = result +'    <div class="action-inner">';
	    	           result = result +'    <ul>';
	    	           result = result +'          <li><div title="Edit"><i class="fa fa-pie-chart cursorPointer" onclick="campaignRecipient(this,'+report.id+');"></i></div></li>';       
	    	           result = result +'          <li><div title="Delete"><i onclick= "deleteCampaignList(\''+report.id+'\');" class="fa fa-trash-o text-danger cursorPointer"></i></div></li>';  
	    	           result = result +'    </ul>';
	    	           result = result +'    </div>';
	    	           result = result +'    </div>';                                                                                                              
	    	           result = result +'  </td>';
	    	  		return result;
	    	  	}
	    	  } ],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[2,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#campaignStatsPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#campaignStatsTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getCampaignStats();
    	   }
    	});
}
</script>

  
  <jsp:include page="../masterFooter.jsp" />