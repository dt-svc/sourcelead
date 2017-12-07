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
      <div class="pull-right brud-crum"><a href="<c:url value='hotListCampaigns'/>">Bank Office</a> &raquo; Campaigns List</div>
    </section>
      <div class="clearfix">
        <div class="tableStyles_head clearfix positionRelative">
        </div> 
          <div class="clearfix tableStyles">
          <div id="campaignStatsDev" class="datatable_filter"></div>
        </div>
      </div>
  </section>
<!--           <table class="table table-bordered"> -->
          
 <script>
function getCampaignStats(){
       var orgLocationId = '${SourceLeadWeb_orgLocationId}';
       $.ajax({
       type : "GET",
        url: sourceleadUrl+"/getCampaignStats?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId,       
        success :function(json) {
        	populategCampaignStats(json);
       },
        
   }); 
}
function populategCampaignStats(json){
       var result = '';
       result = result +' <table width="100%" width="100%" cellpadding="0" cellspacing="0" id="campaignTableId" class="table table-bordered">';
       result = result +' <thead>';
       result = result +' <tr>';
       result = result +' <th>Opens</th>';
       result = result +' <th>Category Name</th>';
       result = result +' <th>Delivered</th>';
       result = result +' <th>Unique Opens</th>';
       result = result +' <th>Unique Clicks</th>';
       result = result +' <th>Unsubscribes</th>';
       result = result +' <th>Actions</th>';
       result = result +' </tr>';
       result = result +' </thead>';
       result = result +' <tbody>';
       
   for (var i=0; i<json.length; i++){
           var report = json[i];
           result = result +'  <tr>';                                                                                                                  
           result = result +'  <td>';                                                                                                                  
           result = result +    '<div class="tbldata-dotdot-sm" title="'+report.opens+'">'+report.opens+'</div>';
           result = result +'  </td>';  
           result = result +'  <td>';   
           result = result +    '<a href="'+sourceleadUrl+'/campaignStatsGraph"<div class="tbldata-dotdot-sm" title="'+report.categoryName+'">'+report.categoryName+'</div></a>';
           result = result +'  </td>';
           result = result +'  <td>';   
           result = result +    '<div class="tbldata-dotdot-sm" title="'+report.delivered+'">'+report.delivered+'</div>';
           result = result +'  </td>';
           result = result +'  <td>';   
           result = result +    '<div class="tbldata-dotdot-sm" title="'+report.uniqueOpens+'">'+report.uniqueOpens+'</div>';
           result = result +'  </td>';
           result = result +'  <td>';   
           result = result +    '<div class="tbldata-dotdot-sm" title="'+report.uniqueClicks+'">'+report.uniqueClicks+'</div>';
           result = result +'  </td>';
           result = result +'  <td>';   
           result = result +    '<div class="tbldata-dotdot-sm" title="'+report.unsubscribes+'">'+report.unsubscribes+'</div>';
           result = result +'  </td>';
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
           result = result +'</tr>';                                                                                                                                                                                                                                        
           
   } 
   result = result +' </tbody>';
   result = result +' </table>';
   $("#campaignStatsDev").empty();
   $("#campaignStatsDev").append(result);
   campaignStatsListPagination();
}
</script>


<script>
function campaignStatsListPagination(){
        $('#campaignTableId').DataTable( { 
                   "scrollCollapse": false,
                   "lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
                   "bLengthChange": true,
                   "bDestroy": true,
                   "ordering": true,
                   "oLanguage": {
                   "sSearch": "",
                   "sZeroRecords": "", "sEmptyTable": ""
              }
           });
           $("#campaignTableId").wrap("<div class='overflow_Auto width100percnt'></div>")
}
</script>

  
  <jsp:include page="../masterFooter.jsp" />