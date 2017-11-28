<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">
$(document).ready(function(){
    getSendersList();
});
</script>


<!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
    <div class="success_pop" id="selector1" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
     </div>
      <div class="pull-left">
        <h2>Senders list</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Senders list </div>
    </section>
    <div class="timesheet_out">
      <div class="vendor_wrap">
        <div class="timesheet_head candidateHead pull-right">
          <ul class="pull-right">
            <li class="hidden"><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop"></i>
              <div class="export_pop">
                <ul>
                  <li>PDF<i class="fa fa-file-pdf-o"></i></li>
                  <li>Excel<i class="fa fa-file-excel-o"></i></li>
                  <li>Word<i class="fa fa-file-word-o"></i></li>
                </ul>
              </div>
            </li>
            <li><a href="javascript:void(0)" title="" id="addSen" onclick="campaignSender(this,'');"><i class="fa fa-plus"></i>Add Sender</a></li>
            <div class="clearfix"></div>
          </ul>
          <div class="clearfix"></div>
         <!--  <div class="remove_search">
            <input type="text" placeholder="search"/>
          </div> -->
        </div>

<div id="loading"></div>
<div class="project_wrap_table campaignWrap datatable_filter">
    <div id="sendersListDiv"></div>
	</div>        
      </div>
      
      <!-- Project --> 
    </div>
  </section>
  <div id="popUp"></div>

<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->



<script type="text/javascript">
function getSendersList(){
	var locationId = '${SourceLeadWeb_orgLocationId}';
	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:999;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.get( sourceleadUrl+ "/getSendersByLocationId?&orgLocationId="+locationId, function(json) {
	    	 var result = "";
	    	 result += '<table id="sendersListTable" class="display" cellspacing="0" width="100%" >';
	         result += '<thead>';
	         result += '  <tr>';
	         result += '    <th>Name</th>';
	         result += '    <th>Nikname</th>';
	         result += '    <th>From mail</th>';
	         result += '    <th>Reply mail</th>';
	         result += '    <th>Verified</th>';
	         result += '    <th>Action</th>';
	         result += '  </tr>';
	         result += '</thead>';
	         result += '<tbody>';
	         var data = json.data;
	         if(data !=null && data !=''){
	        	 for(i=0;i < data.length;i++){
		    		 var report = data[i];
		    		 result += '<tr>';
			         result += '    <td>';
			         if(report.cpnSenderName != null && report.cpnSenderName != '' && report.cpnSenderName != 'undefined'){
			        	 result += '          <div class="pull-left tbldata-dotdot-me" title="'+report.cpnSenderName+'">'+report.cpnSenderName+'</div>';
			        	 result += '          <i class="fa fa-binoculars pull-right"></i>';
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
			        	 result += '      <div title="'+report.cpnSenderFromMail+'" class="tbldata-dotdot-100">'+report.cpnSenderFromMail+'</div>'; 
			         }
			         result += '    </td>';
			         result += '    <td>';
			         if(report.cpnReplyMail != null && report.cpnReplyMail != '' && report.cpnReplyMail != 'undefined'){
			        	 result += '        <div title="'+report.cpnReplyMail+'" class="tblminwidth-75">'+report.cpnReplyMail+'</div>'; 
			         }
			         result += '    </td>';
			         
			         if(report.isSenderEmailVerified != null && report.isSenderEmailVerified != '' && report.isSenderEmailVerified != 'undefined'){
			        	 result += '  <td><div><i class="glyphicon glyphicon-ok"></i></div></td>';
			         }else{
			        	 result += '  <td><div><i class="glyphicon glyphicon-ok"></i></div></td>';
			         }
			         result += ' <td><div><i class="glyphicon glyphicon-pencil" onclick="campaignSender(this,'+report.id+');"></i><i class="glyphicon glyphicon-trash ml10" onclick="deleteIdentity('+report.id+');"></i></div></td>';
			         result += '  </tr>';
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
 function msgFadein() {
	$("#selector1").fadeIn(200);
    $('#selector1').delay(2000).fadeOut('slow');
}
</script>

<script>
function deleteIdentity(senderId){
	$(".project_inn_mask").remove();
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure you want to Cancel ?</p>";
	result = result +"		<textarea id='reason' class='form-control' field-name='Delete Identity'  readonly data-validation='required validate_Space validate_Description validate_length length2-250' name='reason' value='' placeholder='Are you sure you want to delete ?' rows='2'></textarea>";
	result = result + ' <div id="deleteIdentityDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onclick='deleteSenderIdentity(\""+senderId+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelDeleteIdentity();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>
 
 
 <script>
 function deleteSenderIdentity(senderId){
	 $("#deleteIdentityDiv").empty();
	 $.get( sourceleadUrl+ "/deleteSenderIdentity?"+tokenParamName+"="+tokenValue+"&senderId="+senderId, function(json) {
			if(json == true) {
				 $("#selector1").removeAttr("style");
	  			 $("#selector1").css({ display: "block" });
	  			 $("#msgDiv").html("Successfullyy deleted sender identity");
	  			 $("#popUp").empty();
	 			 $(".project_inn_mask").fadeOut(200);
	             msgFadein();	
	             location.replace(sourceleadUrl+ "/campaignSendersList");
			}
		});
		
	}
 </script>
 
 
 <script>
function cancelDeleteIdentity(){
	$("#popUp").empty();
	 $(".project_inn_mask").fadeOut(200);
}

</script>