<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<script src="/resources/js/campaignSender.js?staticTime=${staticTime}"></script>
<script type="text/javascript">
$(document).ready(function(){
    getSendersList();
});
</script>


<!-- Content Area -->
  <section class="content-wraper">
   <section class="content-header clearfix">
    <div class="success_pop" id="campainSenderAlertMsg" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="campainSenderAlertMsgDiv"></span>
     </div>
      <div class="pull-left">
        <h2>Senders list</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; Senders list </div>
    </section>
    <div class="project_wrap">
    
 	<div class="reportHead reportHeadDataTab  clearfix">
      	<ul class="pull-right list-unstyled list-inline">
			<li>
				<a href="javascript:void(0)" class="newAdBtn" title="" id="addSen" onclick="campaignSender(this,'');">+ Sender</a>
			</li>
        </ul>
	</div>    
	<div id="loading"></div>
	<div class="project_wrap_table campaignWrap datatable_filter dataSeaTop">
   		<div id="sendersListDiv"></div>
	</div>        
</div>
      
      <!-- Project --> 
  </section>
  <div id="deleteSenderId"></div>

<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->



<script type="text/javascript">
function getSendersList(type){
	var locationId = '${SourceLeadWeb_orgLocationId}';
	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:999;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.get( sourceleadUrl+ "/getSendersByLocationId?&orgLocationId="+locationId, function(json) {
		 
		 
		 
	    	 var result = "";
	    	 result += '<table id="sendersListTable" class="display" cellspacing="0" width="100%" >';
	         result += '<thead>';
	         result += '  <tr>';
	         /* result += '    <th>Default Mail <br> Sender</th>'; */
	         result += '    <th>Name</th>';
	         result += '    <th>Nick name</th>';
	         result += '    <th>From mail</th>';
	         result += '    <th>Reply mail</th>';
	        /*  result += '    <th>Verified</th>'; */
	         result += '    <th>Action</th>';
	         result += '  </tr>';
	         result += '</thead>';
	         result += '<tbody>';
	         var data = json.data;
	         if(data !=null && data !=''){
	        	 for(i=0;i < data.length;i++){
		    		 var report = data[i];
		    		 result += '<tr>';
		    		/*  result += '    <td><div class="tblminwidth-30"><input type="radio" onchange="selectMailSender('+report.id+');" name="mails"></div></td>'; */
			         result += '    <td>';
			         if(report.cpnSenderName != null && report.cpnSenderName != '' && report.cpnSenderName != 'undefined'){
			        	 result += '          <div class="pull-left tbldata-dotdot-me" title="'+report.cpnSenderName+'">'+report.cpnSenderName+'</div>';
			        	/*  result += '          <i class="fa fa-binoculars pull-right"></i>'; */
				         result += '      </div>';
			         } 
			         result += '    </td>';
			         result += '    <td>';
			         if(report.cpnNickName != null && report.cpnNickName != '' && report.cpnNickName != 'undefined'){
			        	 result += '        <div title="'+report.cpnNickName+'" class="tblminwidth-75">'+report.cpnNickName+'</div>';
			         }
			         result += '    </td>';
			         result += '    <td>';
			         if(report.cpnSenderFromMail != null && report.cpnSenderFromMail != '' && report.cpnSenderFromMail != 'undefined'){
			        	 result += '      <div title="'+report.cpnSenderFromMail+'" class="tbldata-dotdot">'+report.cpnSenderFromMail+'</div>'; 
			         }
			         result += '    </td>';
			         result += '    <td>';
			         if(report.cpnReplyMail != null && report.cpnReplyMail != '' && report.cpnReplyMail != 'undefined'){
			        	 result += '        <div title="'+report.cpnReplyMail+'" class="tblminwidth">'+report.cpnReplyMail+'</div>'; 
			         }
			         result += '    </td>';
			         
			        /*  if(report.isSenderEmailVerified != null && report.isSenderEmailVerified != '' && report.isSenderEmailVerified != 'undefined'){
			        	 result += '  <td><div><i class="glyphicon glyphicon-ok"></i></div></td>';
			         }else{
			        	 result += '  <td><div><i class="glyphicon glyphicon-ok"></i></div></td>';
			         } */
			         result = result +'  <td>';                                                                                                                  
			           result = result +'    <div class="tblicons">';
			           result = result +'          <a title="Edit" class="mr10" href="javascript:void(0)"><i class="fa fa-pencil text-primary cursorPointer" onclick="campaignSender(this,'+report.id+');"></i></a>';       
			           result = result +'          <a title="Delete" href="javascript:void(0)"><i onclick= "deleteIdentity(\''+report.id+'\');" class="fa fa-trash text-danger cursorPointer"></i></a>';  
			           result = result +'    </div>';                                                                                                              
			           result = result +'  </td>';
			           result = result +'</tr>';                                                                                                                                                                                                                                        
			      
			   } 
	         }
	    	
	         result += '  </tbody>';
      		 result += '</table> ';
      		 $("#sendersListDiv").empty();
      		 $("#sendersListDiv").append(result);
      		 $("#loading").removeAttr('style');
      		 senderListPagination();
    });
}
</script>

<script>
 function senderListPagination(){
		$('#sendersListTable').DataTable({ 
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
		$("#sendersListTable").wrap("<div class='overflow_Auto' style='width:100%;'></div>");  

	}
 </script>
 
 <script>
 function campainSenderAlertMsgFadein() {
	$("#campainSenderAlertMsg").fadeIn(300);
    $('#campainSenderAlertMsg').delay(3000).fadeOut('slow');
}
</script>

<script>
function deleteIdentity(senderId){
	$("#deleteSenderId").show();
	$(".mask").remove();
	var result = "";
	result = result +"	<div class='mask'></div>";
	result = result +"	<div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"	<p>Are you sure want to delete ?</p>";
	//result = result +"		<textarea id='reason' class='form-control' field-name='Delete Identity'  readonly data-validation='required validate_Space validate_Description validate_length length2-250' name='reason' value='' placeholder='Are you sure you want to delete ?' rows='2'></textarea>";
	result = result + ' <div id="deleteIdentityDiv"/>';
	result = result + "<b href='javascript:void(0)'><input id='confirm-id1' type='button' value='Delete' onclick='deleteSenderIdentity(\""+senderId+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelDeleteIdentity();'></b>";
    result = result +"	</div>";
 	$("#deleteSenderId").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".mask").show();
}
</script>
 
 
 <script>
 function deleteSenderIdentity(senderId){
	 $("#deleteIdentityDiv").empty();
	 $.get( sourceleadUrl+ "/deleteSenderIdentity?"+tokenParamName+"="+tokenValue+"&senderId="+senderId, function(data) {
			if(data == true) {
				 $("#campainSenderAlertMsg").removeAttr("style");
	  			 $("#campainSenderAlertMsg").css({ display: "block" });
	  			 $("#campainSenderAlertMsgDiv").html("Successfully deleted sender identity");
	  			 $("#deleteSenderId").hide();
	 			 $(".mask").hide();
	             campainSenderAlertMsgFadein();	
	             location.replace(sourceleadUrl+ "/campaignSendersList");
			}
		});
		
	}
 </script>
 
 
 <script>
function cancelDeleteIdentity(){
	$("#deleteSenderId").hide();
	 $(".mask").fadeOut(200);
}

</script>

<script>
function selectMailSender(senderId){
			$.get(sourceleadUrl+"/selectMailSender?&senderId="+senderId, function(data) {
	            });
}
</script>