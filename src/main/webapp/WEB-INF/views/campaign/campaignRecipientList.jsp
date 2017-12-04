<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
 $( document ).ready(function() {
	 getCampaignRecipientList();
	});
 </script>

 <section class="content-wraper">
    <section class="content-header clearfix">
    <div class="pull-left">
            <h2>Contact List<span class="select-fild">
                	<select id="typeOfCampaign" onchange="getCampaignRecipientList();">
                  		 <option value="1">Job List</option>
                         <option value="0" selected="selected">Hot List</option> 
                    </select>
                </span>            	
            </h2>
          </div>
      <div class="pull-right brud-crum">Home &raquo; Recipient list</div>
    </section>
    <div class="timesheet_out">
      <div class="vendor_wrap">
        <div class="timesheet_head minHeight">
        <ul>
        <li><a href="javascript:void(0)" title="" id="addRep" onclick="campaignRecipient(this,'');"><i class="fa fa-plus"></i>Add Recipients</a></li>
        </ul>
        </div>
        <div class= "project_tabs_inn_table vendor_wrap_inn_table datatable_filter">
        <div id= "recipientListDev"></div> 
          <div class="clearfix"></div>
        </div>
        
<div class="project_wrap_table">

<script>
function getCampaignRecipientList(){
       var orgLocationId = '${SourceLeadWeb_orgLocationId}';
       var userId = '${SourceLeadWeb_user.id}';
       var typeOfCampaign = $("#typeOfCampaign").val();
       alert(typeOfCampaign);
       $.ajax({
       type : "GET",
        url: sourceleadUrl+"/getCampaignRecipientList?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId+"&typeOfCampaign="+typeOfCampaign,       
        success :function(json) {
                populategRecipientList(json);
       },
        
   }); 
}
function populategRecipientList(json){
       var result = '';
       result = result +' <table width="100%" width="100%" cellpadding="0" cellspacing="0" id="recipientTableId">';
       result = result +' <thead>';
       result = result +' <tr>';
       result = result +' <th>List Name</th>';
       result = result +' <th>No of Recipients</th>';
       result = result +' <th>Action</th>';
       result = result +' </tr>';
       result = result +' </thead>';
       result = result +' <tbody>';
       
   for (var i=0; i<json.length; i++){
           var report = json[i];
           result = result +'  <tr>';                                                                                                                  
           result = result +'  <td>';                                                                                                                  
           result = result +    '<div class="tbldata-dotdot-verybig" title="'+report.listName+'">'+report.listName+'</div>';
           result = result +'  </td>';  
           result = result +'  <td>';   
           result = result +    '<div class="tbldata-dotdot-big" title="'+report.recipientEmail+'">'+report.listCount+'</div>';
           result = result +'  </td>';
           result = result +'  <td>';                                                                                                                  
           result = result +'    <div class="tblminwidth-50 text-center">';
           result = result +'          <div title="Edit" class="paddingRight pull-left ml"><i class="fa fa-pencil text-primary cursorPointer" onclick="campaignRecipient(this,'+report.id+');"></i></div>';       
           result = result +'          <div title="Delete" class="paddingLeft pull-left"><i onclick= "deleteCampaignContactList(\''+report.id+'\');" class="fa fa-trash text-danger cursorPointer"></i></div>';  
           result = result +'    </div>';                                                                                                              
           result = result +'  </td>';
           result = result +'</tr>';                                                                                                                                                                                                                                        
           
   } 
   result = result +' </tbody>';
   result = result +' </table>';
   $("#recipientListDev").empty();
   $("#recipientListDev").append(result);
   recipientListPagination();
}
</script>

<script>
function recipientListPagination(){
        $('#recipientTableId').DataTable( { 
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
           $("#recipientTableId").wrap("<div class='overflow_Auto width100percnt'></div>")
}
</script>
	</div>        
        
      </div>
      
      <!-- Project --> 
    </div>
  </section>
  
  <script>
function deleteCampaignContactList(id){
	  var orgLocationId = '${SourceLeadWeb_orgLocationId}';
      $.ajax({
      type : "GET",
       url: sourceleadUrl+"/deleteCampaignContactList?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&id="+id,       
       success :function(data) {
    	   location.replace(sourceleadUrl+ "/recipientsList");
      }
       
  }); 

}
</script>


  
<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->



 


 
 

 
 
