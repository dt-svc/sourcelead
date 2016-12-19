<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />


<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script> 
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>

<script>
 window.onload = function() {
	 getMails('Inbox');
};
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Mailbox</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Mailbox</div>
    </section>
    <div class="content-dashboard clearfix">
      <section class="action-btns clearfix hidden">
        <ul class="pull-right list-unstyled list-inline">
          <li><a href="#" class="faclose"><i aria-hidden="true" class="fa fa-close"></i></a></li>
          <li><a href="#" class="collpase-div"><i aria-hidden="true" class="fa fa-angle-down"></i></a></li>
        </ul>
      </section>
      <div class="clearfix form-area form-horizontal">
        <div class="row">
		<!-- Mail view area -->
			<!-- Mail side-bar -->
			<div class="col-md-3">
          <a class="btn btn-primary btn-block mb compose" href="${sourceleadUrl}/hotlistMail">
		  <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
			Compose
		  </a>

          <div class="mail-box">
            <div class="mail-box-header">
              <h3>Folders</h3>
            </div>
            <div>
              <ul class="nav nav-pills nav-stacked">
                <li id="inboxActive" class=""><a href="#" onclick="getMails('Inbox');"><i class="fa fa-inbox"></i> Inbox
                <li id="sentActive" class=""><a href="#" onclick="getMails('Sent');"><i class="fa fa-envelope-o"></i> Sent</a></li>
                <li id="starredActive" class=""><a href="#" onclick="getMails('Starred');"><i class="fa fa-star"></i> Starred</a></li>
                <li id="requestedActive" class=""><a href="#" onclick="getMails('Requested');"><i class="fa fa-file-text-o"></i> Requested</a></li>
                <li id="trashActive" class=""><a href="#" onclick="getMails('Trash');"><i class="fa fa-trash-o"></i> Trash</a></li>
              </ul>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /. box -->
         <!--  <div class="mail-box">
            <div class="mail-box-header">
              <h3>Labels</h3>
            </div>
            <div class="box-body no-padding">
              <ul class="nav nav-pills nav-stacked">
                <li><a href="#"><i class="fa fa-circle-o text-danger"></i> Important</a></li>
                <li><a href="#"><i class="fa fa-circle-o text-warning"></i> Promotions</a></li>
                <li><a href="#"><i class="fa fa-circle-o text-primary"></i> Social</a></li>
              </ul>
            </div>
            /.box-body
          </div> -->
          <!-- /.box -->
        </div>
			<!-- mail side-bar ends -->
			<div class="col-md-9" id="inbox">
			 <div class="mail-box mail-content">
			 <div class="mail-box-header">
              <h3 class="box-title" id="mailboxLabel"></h3>
              <!-- <div class="box-tools pull-right">
                <div class="has-feedback">
                  <input type="text" placeholder="Search Mail" class="form-control input-sm">
                  <span class="glyphicon glyphicon-search form-control-feedback"></span>
                </div>
              </div> -->
              <!-- /.box-tools -->
            </div>
			
			<!-- /.box-tools -->
			<div class="mailbox-controls">
			<div>
			<input id="checkAllEmails" class="checkbox-custom" onclick="toggleEmails(this);deleteMail();" name="checkAllEmails" type="checkbox">
			<label for="checkAllEmails" class="checkbox-custom-label"></label>
			</div>
				<button class="btn btn-danger mailbtn-custom" type="button" onclick="getMailBoxIds();" id="trashBtn" style="display: none;"><i class="fa fa-trash-o"></i></button>
                <button class="btn btn-default mailbtn-custom" type="button" onclick="refreshList();"><i class="fa fa-refresh"></i></button>
               
               <!--  <div class="pull-right">
                  1-50/200
                  <div class="btn-group">
                    <button class="btn btn-default btn-sm" type="button"><i class="fa fa-chevron-left"></i></button>
                    <button class="btn btn-default btn-sm" type="button"><i class="fa fa-chevron-right"></i></button>
                  </div>
                  /.btn-group
                </div> -->
                <!-- /.pull-right -->
              </div>
			  <!-- /.box-tools -->
			
	<!-- mail box table -->
			<div class="table-responsive mailbox-messages">
                 <div id="mailBoxList"></div>
              </div>
	<!-- mail box table ends -->   

			</div>
			</div>
			
			<div class="col-md-9" id="details">
			 <div class="mail-box mail-content">
			 <div class="mail-widget">
              <h3 class="box-title">Read Mail</h3>
              <div id="mailDetails"></div>
              </div>
              </div>
              </div>
              

		<!-- Mail view area -->

			

			</div>
		   </div>
		</div>
  </section>

  
  <script>
  function getMails(type){
	  var sourceleadUrl ='${sourceleadUrl}';
	  
	  document.getElementById("inbox").style.display="";
	  document.getElementById("details").style.display="none";
	  $('#mailboxLabel').html(type);
	  
	  setActiveTab(type)
	  
	  $.ajax({
          type : "POST",
          url: sourceleadUrl+"/getUserMails?${_csrf.parameterName}=${_csrf.token}&type="+type,    
           success :function(data) {
           	
                if(data != null && data != 'undefined' && data != "" && data != '[]'){
              		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
              		 else if (data.indexOf('exception') >= 0) {
              			 location.replace(sourceleadUrl+"/errorPage");
              		 }
              		 else{
              			 
              			var result = '';
              			result = result +'<input type="hidden" id="emailListType" value='+type+'>';
              			result = result +'<table id="mails" class="table table-hover table-striped">';
              			result = result +'<tbody>';
              			
              			 var json = JSON.parse(data);
		                    for (var i = 0; i < json.length; i++) { 
		                    	 var opt = json[i]; 
		                    	 
		                    	 var isFavourite = "";
		                    	 var isFavClass = "";
		                    	 
	                    		 if(opt.isFavourite == 1){
				            		isFavourite = "checked";
				            		isFavClass = "activestar-rating";
				            	 }
		                    	 
		                    	result = result +' <tr id="checkbox'+opt.id+'">';
		              			result = result +'<td>';
		              			result = result +'<input id="'+opt.id+'" value="'+opt.id+'" onclick="deleteMail();"  type="checkbox" name="mailIds[]" class="myCheckbox mrleft8" ></td>';
		              			result = result +'</td>';
		              			
	              			    if(opt.mailStatus != 'Trash'){
	              			    	result = result +"<td style='width : 25px;'>";
			                        result = result +"<div class='star-rating mailbox-rating positionRelative "+isFavClass+"' id='favourite"+opt.id+"'>";
			                        result = result +"<input type='checkbox' id='fav"+opt.id+"' "+isFavourite+" onclick='addFavourite("+opt.id+")'></input>";
	              			    }
		              			
		                        result = result +" <label></label>";
		                        result = result +"</div>";
		                        result = result +"</td>";
		              			
		              			result = result +'<td class="mailbox-subject"><a href="#" onclick="getEmailDetails(\''+opt.uuid+'\')">'+opt.subject+'</a></td>';
		              			result = result +'<td class="mailbox-date">'+opt.calDays+' days ago</td>';
		              			result = result +'</tr>';
		                    }
		                    
		                    
              			
	                 	 $("#mailBoxList").empty(); 
	                 	 $("#mailDetails").empty(); 
	                     $("#mailBoxList").append(result); 
	                     pagination(); 
			    	} 
                }else{
 	    	         
               	var result ="";
               	    result = result +'<input type="hidden" id="emailListType" value='+type+'>';
	    	        result = result +'<div class="modal-body clearfix">';
	           		result = result +'     </div>';
	           		result = result +'     <div class="modal-bodyCustom mt clearfix popupFilter">'; 
	           		result = result +'<table id="mails" class="table table-hover table-striped">';
	           		result = result +'           <thead>';
	           		result = result +'           <p class="text-center text-muted"><i class="fa fa-envelope fa-3x"></i><br/><br/>No Mails In Your '+type+' </p>';
	           		result = result +'           </thead>';
	        		result = result +'           </table>';
	        		result = result +'     </div>';
	        		result = result +'     </div>';
					$("#mailBoxList").empty();
					$("#mailDetails").empty(); 
				    $("#mailBoxList").append(result); 
				    pagination();  
               }
         },
           
      }); 
	  
  }
  
  </script>
  
  <script>
function pagination(){
    $("#mails").DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
    } );
}
</script>

<script>
function setActiveTab(type){
	
	if(type == 'Inbox'){
		  document.getElementById("sentActive").removeAttribute("class")
		  document.getElementById("starredActive").removeAttribute("class")
		  document.getElementById("requestedActive").removeAttribute("class")
		  document.getElementById("trashActive").removeAttribute("class")
		  document.getElementById('inboxActive').className = 'active'
	}
	
	else if(type == 'Sent'){
		  document.getElementById("sentActive").className = 'active'
		  document.getElementById("starredActive").removeAttribute("class")
		  document.getElementById("requestedActive").removeAttribute("class")
		  document.getElementById("trashActive").removeAttribute("class")
		  document.getElementById('inboxActive').removeAttribute("class")
	}
	
	else if(type == 'Starred'){
		  document.getElementById("starredActive").className = 'active'
		  document.getElementById("sentActive").removeAttribute("class")
		  document.getElementById("requestedActive").removeAttribute("class")
		  document.getElementById("trashActive").removeAttribute("class")
		  document.getElementById('inboxActive').removeAttribute("class")
	}
	
	else if(type == 'Requested'){
		  document.getElementById("requestedActive").className = 'active'
		  document.getElementById("sentActive").removeAttribute("class")
		  document.getElementById("starredActive").removeAttribute("class")
		  document.getElementById("trashActive").removeAttribute("class")
		  document.getElementById('inboxActive').removeAttribute("class")
	}
	
	else if(type == 'Trash'){
		  document.getElementById("trashActive").className = 'active'
		  document.getElementById("sentActive").removeAttribute("class")
		  document.getElementById("starredActive").removeAttribute("class")
		  document.getElementById("requestedActive").removeAttribute("class")
		  document.getElementById('inboxActive').removeAttribute("class")
	}
	 
}
</script>


<script>
function addFavourite(mailboxId){
	
	var checked = document.getElementById("fav"+mailboxId).checked;
	var sourceleadUrl = '${sourceleadUrl}';
	
	
	  	$.ajax({
			type : "POST",
			url : sourceleadUrl+"/saveMailAsFavourite?${_csrf.parameterName}=${_csrf.token}&mailboxId="+mailboxId,
			success : function(result) {
				if(checked){
					 document.getElementById("favourite"+mailboxId).removeAttribute("class")
					 document.getElementById("favourite"+mailboxId).className = 'star-rating mailbox-rating positionRelative activestar-rating' 
				}else{
					 document.getElementById("favourite"+mailboxId).removeAttribute("class")
					 document.getElementById("favourite"+mailboxId).className = 'star-rating mailbox-rating positionRelative' 
				}
				
			},
		});  
		 
		
	
}
</script>



<script>
function toggleEmails(source) {
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i] != source)
            checkboxes[i].checked = source.checked;
        
    }
$('input:checkbox:not(#checkAllEmails)').click(function() {
    if(!this.checked) {
        $('#checkAllEmails').removeAttr('checked');
    }
});
}
</script>

<script>
function deleteMail(){
	
	var mailIds = [];
	 $("input[name='mailIds[]']:checked").each( function (i) {
		 mailIds[i] = $(this).val();
	});
	 
	 if(mailIds.length > 0){
		 document.getElementById("trashBtn").style.display="";
	 }else{
		 document.getElementById("trashBtn").style.display="none";
	 }
	
}
</script>

<script>
function getMailBoxIds(){
	var sourceleadUrl = '${sourceleadUrl}';
	var mailIds = [];
	 $("input[name='mailIds[]']:checked").each( function (i) {
		 mailIds[i] = $(this).val();
		 $("#checkbox"+mailIds[i]).html("<td colspan='4'  id='loading"+mailIds[i]+"'></td>");
	     $("#loading"+mailIds[i]).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
		 
	});
	 
	for (var i=0; i< mailIds.length; i++) {
		 document.getElementById("checkbox"+mailIds[i]).remove();					 
	}
	 
	
	 
		 $.ajax({
			type : "POST",
			url : sourceleadUrl+"/sendMailsToTrash?${_csrf.parameterName}=${_csrf.token}&mailIds="+mailIds,
			success : function(result) {
				
				for (var i=0; i< mailIds.length; i++) {
					 document.getElementById("checkbox"+mailIds[i]).remove();					 
				}
				 
				 
				
			},
		});   
		
		
}
</script>


<script>
function refreshList(){
	var type =  document.getElementById("emailListType").value;
	getMails(type);
}
</script>


<script>
function getEmailDetails(uuid){
	var sourceleadUrl = '${sourceleadUrl}';
	
	 document.getElementById("details").style.display="";
	 document.getElementById("inbox").style.display="none";
	 
	
	$.ajax({
		type : "POST",
		url : sourceleadUrl+"/getEmailDetails?${_csrf.parameterName}=${_csrf.token}&UUID="+uuid,
		success : function(data) {
			
			 var json = JSON.parse(data);
             for (var i = 0; i < json.length; i++) { 
             	 var opt = json[i]; 
                 var result =""; 
             	 
              	result = result +'<div class="box-tools pull-right">';
              	result = result +'<div class="btn-group">';
              	result = result +'<button class="btn btn-default btn-sm" type="button"><i class="fa fa-chevron-left"></i></button>';
              	result = result +'<button class="btn btn-default btn-sm" type="button"><i class="fa fa-chevron-right"></i></button>';
              	result = result +'</div>';
                result = result +'</div>';
                result = result +'</div>';
			    result = result +'<div class="mailbox-read-info">';
			    result = result +'<h3>'+opt.subject+'</h3>';
			    result = result +'<h5>From: '+opt.fromMail+'' 
			    result = result +'<h5>To: '+opt.toMail+'' 
			    result = result +'<span class="mailbox-read-time pull-right">'+opt.calDays+' days ago</span></h5>';
			    result = result +'</div>	';

	   			result = result +'<div class="mailbox-controls clearfix">';
	   			result = result +'<div class="pull-left">';
	   			result = result +'<div class="btn-group">';
	   			result = result +'<button class="btn btn-danger" type="button" title="Delete"><i class="fa fa-trash-o"></i></button>';
	   			result = result +'<button class="btn btn-warning" type="button" title="Reply"><i class="fa fa-reply"></i></button>';
	   			result = result +'<button class="btn btn-info" type="button" title="Forward"><i class="fa fa-share"></i></button>';
	   			result = result +'</div>';
	   			result = result +'</div>';
	   			result = result +'<div class="pull-right">';
	   			result = result +'<button class="btn btn-default" type="button" title="Refresh"><i class="fa fa-refresh"></i></button>';
	   			result = result +'<button class="btn btn-default" type="button" title="Print"><i class="fa fa-print"></i></button>';
	   			result = result +'</div>';
	   			result = result +'</div>';
	   			  
	   			result = result +'<div class="mailbox-read-message clearfix borderbottm-none">';
	   			
	   			result = result +'<p>Dear,</p><br/>';
	   			
	   			if(opt.comments != null && opt.comments != 'undefined' && opt.comments != ""){
	   				result = result +'<p>'+opt.comments+'</p>';
	   			}else if(opt.body != null && opt.body != 'undefined' && opt.body != ""){
	   				result = result +'<p>'+opt.body+'</p><br/>';
	   			}
	   			
	   			

	   			result = result +'<div class="clearfix project_wrap_table mail-table  mb">';
	   			result = result +'<table>';
			  	
	   			result = result +'<thead>';
	   			result = result +'<tr>';
	   			result = result +'<th>Name</th>';
	   			result = result +'<th>Email</th>';
	   			result = result +'<th>Ph</th>';
	   			result = result +'<th>Experience</th>';
	   			result = result +'<th>Postion Type</th>';
	   			result = result +'</tr>';
	   			result = result +'<tbody>';
	   			
	   			
	   		 for (var i = 0; i < opt.candidatesList.length; i++) { 
	   			 
	   			var cand = opt.candidatesList[i]; 
	   			
	   			var mobile = '';
	   			var designation = '';
	   			
	   			if(cand.mobilePhone != null && cand.mobilePhone != 'undefined' && cand.mobilePhone != ""){
	   				mobile = cand.mobilePhone;
	   			}else{
	   				mobile = '------';
	   			}
	   			
	   			if(cand.designation != null && cand.designation != 'undefined' && cand.designation != ""){
	   				designation = cand.designation;
	   			}else{
	   				designation = '------';
	   			}
	   			
	   			result = result +'<tr>';
	   			result = result +'<td>'+cand.candidateName+'</td>';
	   			result = result +'<td>'+cand.email+'</td>';
	   			result = result +'<td>'+mobile+'</td>';
	   			result = result +'<td>'+cand.totalExp+'</td>';
	   			result = result +'<td>'+designation+'</td>';
	   			result = result +'</tr>';
	   		 } 	
	   			result = result +'</tbody>';
	   			result = result +'</thead>';
	   			result = result +'</table>';
	   			result = result +'</div>';
	   			
	   			if(opt.fileName != null && opt.fileName != "" && opt.fileName != 'undefined'){
	   				result = result +'<div class="require_wrap_details_cta require_uploadfile"><b>Attachment:</b> <span>'+opt.fileName+'</span><a class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadMailAttachment?UUID='+opt.uuid+'\"></a> ';
	   			    result = result +'<div class="clearfix"></div>';
	   				result = result +'</div>';
	   				result = result +'<div class="clearfix"></div>';
	   			}
	            
                
              if(opt.signatureId != null && opt.signatureId != "" && opt.signatureId != 'undefined'){
            	  result = result +'<p>'+opt.signatureId.signature+'</p>';
              }else{
            	  result = result +'<p>Thanks,<br>'+opt.fromMail+'</p>';
              }
	   			 

  			  
            
            $("#mailBoxList").empty(); 
            $("#mailDetails").empty(); 
            $("#mailDetails").append(result);
            
   				  

             }
			
		},
	});  
	
}

</script>



<jsp:include page="masterFooter.jsp" />