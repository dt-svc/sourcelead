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
      <div class="pull-left"><h2>Campaign(s) Report</h2></div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo;<a href="${SourceLeadWeb_sourceleadUrl}/reportsDashboard"> Reports Dashboard</a> &raquo; <a href="${SourceLeadWeb_sourceleadUrl}/associateReportsList">Associate Reports</a> &raquo; Campaign(s) Report</div>
    </section>
    
          <div class="clearfix vendor_wrap">
          
		<div class="reportHead">
		    	<ul class="clearfix pull-right">
		    		<li><input type="text" class="repSearchBar" placeholder='Search' id='campaignStatsTableSearch'/>
		    			<span class="repSearchIcon" onclick=""></span>
		    		</li>
		    	</ul>
		    </div>     
		    <div class="clearfix backWhite">       
          		<div id="campaignStatsDev" class="datatable_filter"></div>
          </div>
        </div>
      <div id="popUp"></div>   
  </section>
       
<script type="text/javascript">
function getCampaignStats(){
		var searchValue = $("#campaignStatsTableSearch").val();
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
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
	 	result = result +"<div class='require_wrap_table'>";
 	    result = result +"<table id='campaignStatsPagination' class='display table table-bordered' cellspacing='0' width='99.9%' >"; 
 	    result = result + '<thead>';
		result = result + '<tr>';
		result = result +' <th>Category Name</th>';
		 result = result +' <th>List Name</th>';
		 result = result +' <th>No.Of Recipients</th>';
	    result = result +' <th>Opens</th>';
	    result = result +' <th>Delivered</th>';
	    result = result +' <th>Clicks</th>';
	    result = result +' <th>Bounces</th>';
	    result = result +' <th>Action</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
        result = result +"</table>";
        result = result +"</div>";
      $("#campaignStatsDev").append(result);
      campaignStatsPagination(url); 
}
</script>


<script>
function campaignStatsPagination(url){
	var atsUrl ='${SourceLeadWeb_atsUrl}';
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
            { "data": "listName"},
            { "data": "numberOfRecipients"},
            { "data": "opens"},
            { "data": "delivered"},
            { "data": "clicks" },
            { "data": "bounces"},
            { "data": "action" }
            

        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "categoryName",
	    	  	"render": function(data, type, report, meta) {
	    	  		  var result = "";
	    	  		   result = result +'  <div class="tbldata-dotdot-sm"><a href="'+atsUrl+'/campaignStatsGraph?campaignName='+report.categoryName+'" title="'+report.categoryName+'">'+report.categoryName+'</a></div>';
		             return result;
	    	  	}
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "listName",
	    	  	"render": function(data, type, report, meta) {
	    	  		var result = "";
	    	  	     if(report.listName !=null && report.listName !='' && report.listName !='undefined'){
	    	  	    	result = result +    '<div class="tbldata-dotdot-sm" title="'+report.listName+'">'+report.listName+'</div>';
	    	  	     }else{
	    	  	    	result = result +    '<div class="tbldata-dotdot-sm">--</div>'; 
	    	  	     }
	    	  		return result;
	    	  	}
	    	  },{
		    	  	"targets": colIndex++,
		    	  	"data": "numberOfRecipients",
		    	  	"render": function(data, type, report, meta) {
		    	  		var result = "";
		    	  		result = result +    '<div class="tbldata-dotdot-sm" title="'+report.numberOfRecipients+'">'+report.numberOfRecipients+'</div>';
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
            	  result = result +    '<div class="tbldata-dotdot-sm" title="'+report.clicks+'">'+report.clicks+'</div>';
            	 return result;
              }
            }, {
                "targets": colIndex++,
                "data": "uniqueClicks",
                "render": function ( data, type, report, meta ) {
                	var result = "";
                		result = result +    '<div class="tbldata-dotdot-sm" title="'+report.bounces+'">'+report.bounces+'</div>';
    	              return result;
                }
          },{
	    	  	"targets": colIndex++,
	    	  	"data": "action",
	    	  	"render": function(data, type, report, meta) {
	    	  		var result = "";
	    	  		result = result +'  <td>';                                                                                                                  
	    	           result = result +'    <div class="action-div tblminwidth-50 text-center">';
	    	           result = result +'    <i class="fa fa-trash-o text-danger cursorPointer" onclick="deleteCampaignStas(\''+report.categoryName+'\');"></i>';
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
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
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

 <script>
 function deleteCampaignStas(categoryName){
	 var result = "";
		result = result +"	<div class='project_inn_mask'></div>"; 
		result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Are you sure you want to delete this campaign?</p>";
		 /* result = result +"		<textarea  id='deleteStats' name='reasonForInvoice'  placeholder='Comments here' ></textarea>";  */
		result = result + ' <div id="deleteStatsDiv"/>';
		result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Delete" onclick="deleteStats(\''+categoryName+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
		result = result +"	</div>";
		$("#popUp").append(result);
		$("#remove_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
 }
 </script>
 
 <script>
 function deleteStats(categoryName){
	 var atsUrl ='${SourceLeadWeb_atsUrl}';
				 $.ajax({
					   	type: "GET",
					    url: atsUrl+"/deleteStats?"+tokenParamName+"="+tokenValue+"&categoryName="+categoryName,
			   		    success: function(data) {
			   		    	if(data == true){
			   		    		$("#popUp").empty();
				   			 	$(".project_inn_mask").fadeOut(200);
				   			 	location.replace(atsUrl+"/campaignStats");	
			   		    	}
			   		    }
				     });
		
 }
 </script>
 
 <script>
 function cancelTermination(){
		$("#popUp").empty();
		 $(".project_inn_mask").fadeOut(200);
	}
 </script>
  
  <jsp:include page="../masterFooter.jsp" />