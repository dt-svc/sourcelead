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
    <div class="success_pop" id="selector11" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgeDiv"></span>
     </div>
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
           result = result +    '<div class="tbldata-dotdot-big" title="'+report.recipientEmail+'">'+report.listCount+' <a class="listView" onclick="listViewPop('+report.id+');">View list</a></div>';
           result = result +'  </td>';
           result = result +'  <td>';                                                                                                                  
           result = result +'    <div class="tblminwidth-50 text-center">';
           result = result +'          <div title="Edit" class="paddingRight pull-left ml"><i class="fa fa-pencil text-primary cursorPointer" onclick="campaignRecipient(this,'+report.id+');"></i></div>';       
           result = result +'          <div title="Delete" class="paddingLeft pull-left"><i onclick= "deleteCampaignList(\''+report.id+'\');" class="fa fa-trash text-danger cursorPointer"></i></div>';  
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

<div class="ReciViewPop">
		<div class="popupHead">
			<h2>View recipient list</h2><a onclick="viewListClose();" href="javascript:void(0)" class="fa fa-close popCan clossIconPosi"></a>
		</div>
		<div id="loading"></div>
		
		<div class="popupMid popupMidPad clearfix">
		
          <div class="clients_wrap_table">
          <div id= "viewListDev" class="datatable_filter"></div>
			</div>	
	
		</div>
		
		<div class="popupFoot">
            <input value="Close" class="btn btn-default ml popCan" onclick="viewListClose();" type="button">
          </div>		
		
</div>	

<script>
function listViewPop(viewListId){
	$(".popMask").show();
	$(".ReciViewPop").show();	
	
	$.ajax({
	       type : "GET",
	        url: sourceleadUrl+"/listViewPop?"+tokenParamName+"="+tokenValue+"&viewListId="+viewListId,       
	        success :function(json) {
	        	populategViewList(json);
	       },
	        
	   }); 


function populategViewList(json){
    var result = '';
    result = result +' <table width="100%" width="100%" cellpadding="0" cellspacing="0" id="viewListTableId">';
    result = result +' <thead>';
    result = result +' <tr>';
    result = result +' <th>Email</th>';
    result = result +' <th>First Name</th>';
    result = result +' <th>Last Name</th>';
    result = result +' </tr>';
    result = result +' </thead>';
    result = result +' <tbody>';
    
for (var i=0; i<json.length; i++){
        var report = json[i];
        result = result +'  <tr>';                                                                                                                  
        result = result +'  <td>'; 
        result = result +    '<div class="tbldata-dotdot-sm" title="'+report.email+'">'+report.email+'</div>';
        result = result +'  </td>';  
        result = result +'  <td>'; 
        if(report.firstName != null && report.firstName !='' && report.firstName !='undefined'){
        	result = result +    '<div class="tbldata-dotdot-sm" title="'+report.firstName+'">'+report.firstName+'</div>';
        }else{
        	result = result +  '<div class="tbldata-dotdot-sm">--</div>';
        }
        result = result +'  </td>';
        result = result +'  <td>';                                                                                                                  
        if(report.lastName != null && report.lastName !='' && report.lastName !='undefined'){
        	result = result +    '<div class="tbldata-dotdot-sm" title="'+report.lastName+'">'+report.lastName+'</div>';
        }else{
        	result = result +  '<div class="tbldata-dotdot-sm">--</div>';
        }                                                                                                            
        result = result +'  </td>';
        result = result +'</tr>';                                                                                                                                                                                                                                        
        
  } 
	result = result +' </tbody>';
	result = result +' </table>';
	$("#viewListDev").empty();
	$("#viewListDev").append(result);
	listPagination();
  }
}

function viewListClose(){
	$(".popMask").hide();
	$(".ReciViewPop").hide();	
}
</script>

<script>
function listPagination(){
        $('#viewListTableId').DataTable( { 
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
           $("#viewListTableId").wrap("<div class='overflow_Auto width100percnt'></div>")
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
  
  <div id="popUp"></div>
  
  <script>
function deleteCampaignList(id){
	$(".project_inn_mask").remove();
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure you want to Cancel ?</p>";
	result = result +"		<textarea id='reason' class='form-control' field-name='Delete Identity'  readonly data-validation='required validate_Space validate_Description validate_length length2-250' name='reason' value='' placeholder='Are you sure you want to delete ?' rows='2'></textarea>";
	result = result + ' <div id="deleteIdentityDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onclick='deleteCampaignContactList(\""+id+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelDeleteCampaignList();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>
  
  <script>
function deleteCampaignContactList(id){
	  var orgLocationId = '${SourceLeadWeb_orgLocationId}';
      $.ajax({
      type : "GET",
       url: sourceleadUrl+"/deleteCampaignContactList?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&id="+id,       
       success :function(data){ 
			 $("#selector11").removeAttr("style");
  			 $("#selector11").css({ display: "block" });
  			 $("#msgeDiv").html("Record deleted Successfully");
  			 $("#popUp").empty();
 			 $(".project_inn_mask").fadeOut(200);
             msgFadein();
             location.replace(sourceleadUrl+ "/recipientsList");
    	   
      }
       
  }); 

}
</script>


<script>
function cancelDeleteCampaignList(){
	$("#popUp").empty();
	 $(".project_inn_mask").fadeOut(200);
}
</script>

<script>
 function msgFadein() {
	$("#selector11").fadeIn(200);
    $('#selector11').delay(2000).fadeOut('slow');
}
</script>


<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->