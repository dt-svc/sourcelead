<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<!-- <script src="/resources/js/summernote.js"></script> -->

<%-- <link rel="stylesheet" href="/resources/css/bootstrap3-wysihtml5.min.css?staticTime=${staticTime}" type="text/css"> --%>


<style>
	.modal-backdrop{ display: none;	}
	.nicEdit-main { overflow: auto !important; }
	.mt40 { margin-top: 50px !important; }
	.compose-mail .chosen-container-multi .chosen-choices li.search-field input[type="text"] {
	    height: 30px;
	    line-height: 35px;
	    width: 500px !important;
	}
	.txt-area > div, .nicEdit-main { width: 100% !important; max-height: 170px !important;}
	.chosen-container-multi .chosen-choices { margin-bottom: 0px; }
	.nicEdit-pane { z-index: 9999999; top: 100px;}
	#dynamicmailcompose {top: 3% !important;}
</style>

<!-- <script>
 window.onload = function() {
	 getMails('Inbox');
};
</script> -->	

<div id="loading"></div>
<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
       <div id="loading"></div>
        <h2>Mailbox</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Mailbox</div>
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
           <%-- <a class="btn btn-primary btn-block mb compose" href="${sourceleadUrl}/hotlistMail">
		  <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
			Compose
		  </a>  --%>
		  
		      <div class="dropdown mb10 mailBtn">
					<c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
					
					<c:if test="${mailType == 'mails'}">
						<button id="composeSourceleadMail" class="btn-block btn btn-primary dropdown-toggle display-block" type="button" onClick="javascript:window.location.href = '<c:url value='${sourceleadUrl}/composeMail'/>'"> <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
							Compose
						</button>
						<button id="composeDynamicMail" class="btn-block btn btn-primary dropdown-toggle" type="button" onClick="showComposeEmailPopup();"> <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
							Compose Dynamic EMail
						</button>
					</c:if>
					
					<c:if test="${mailType == 'hotcampaigns'}">
					<button class="btn-block btn btn-primary dropdown-toggle display-block" type="button" data-toggle="dropdown"> <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
						Campaigns
							&nbsp;&nbsp;<span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
						  <sec:authorize access="hasRole('ROLE_HOTLIST_CAMPAIGN')">
						  	<li><a href="${sourceleadUrl}/hotlistMail">Hotlist Campaign</a></li>
						  	 </sec:authorize>
						  	<sec:authorize access="hasRole('ROLE_JOB_CAMPAIGN')">
						  	<li><a href="${sourceleadUrl}/joblistMail">Job Campaign</a></li>
						  </sec:authorize>
						</ul>
					</c:if>
					</c:if>
					
					<c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL' || SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
					<button class="btn-block btn btn-primary dropdown-toggle display-block" type="button" onClick="javascript:window.location.href = '<c:url value='${sourceleadUrl}/composeMail'/>'"> <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
						Compose
						</button>
					</c:if>
			  </div> 

		<div class="mail-box" id="loading">
			<ul class="userMailList">
				<li class="Act">
					<span class="colors">S</span>
					<p onclick="activeEmail(0, this);">${SourceLeadWeb_user.email}</p>
				</li>
				<c:forEach var="emailAccountList" items="${emailAccountSettingsServiceList}">
					<li>
						<span class="color${emailAccountList.email.split("@")[1].charAt(0)}">${emailAccountList.email.split("@")[1].charAt(0)}</span><i onclick="deleteConMail1(${emailAccountList.id});" class="glyphicon glyphicon-trash"></i>
						<p onclick="activeEmail(${emailAccountList.id}, this);">${emailAccountList.email}</p>
					</li>
				</c:forEach>				
			</ul>
        </div>


          <div class="mail-box" id="sourceleadEmailFolder" style="display:block">
            <div class="mail-box-header">
              <h3>Folders</h3>
            </div>
            <div>
              <ul class="nav nav-pills nav-stacked">
                <li id="inboxActive" class=""><a href="#inbox" onclick="getMails('Inbox');"><i class="fa fa-inbox"></i> Inbox <c:if test="${inboxUnreadMailsCount > 0}"><span class="label label-primary pull-right" id="inboxUnread">${inboxUnreadMailsCount}</span></c:if>
                <li id="sentActive" class=""><a href="#sent" onclick="getMails('Sent');"><i class="fa fa-envelope-o"></i> Sent</a></li>
                <li id="starredActive" class=""><a href="#starred" onclick="getMails('Starred');"><i class="fa fa-star"></i> Starred</a></li>
                <c:if test="${mailType == 'hotcampaigns'}">
                <li id="requestedActive" class=""><a href="#requested" onclick="getMails('Requested');"><i class="fa fa-file-text-o"></i> Requested <c:if test="${requestedUnreadMailsCount > 0}"><span class="label label-info pull-right" id="requestedUnread">${requestedUnreadMailsCount}</span></c:if></a></li> 
                </c:if>
                <li id="trashActive" class=""><a href="#trash" onclick="getMails('Trash');"><i class="fa fa-trash-o"></i> Trash</a></li>
                <c:if test="${mailType == 'mails'}">
                <li id="settingsActive" class=""><a href="#settings" onclick="getEmialSettings();"><i class="fa fa-cog"></i> Settings</a></li>
                </c:if>
                
              </ul>
            </div>
            <!-- /.box-body -->
          </div>
          <div class="mail-box" id="dynamicEmailFolder" style="display: none;"></div>



        </div>
			<!-- mail side-bar ends -->
			<div class="col-md-9" id="inbox">
			<div id="mailLoading"></div>
			 <div class="mail-box mail-content">
			
			 <div class="mail-box-header">
			 	<input type="checkbox" onclick="selectOrDeselectAllEmails(this)">
              <h3 class="box-title" id="mailboxLabel"></h3>
              <div class="mail-widget" id="mailControlsMaster" style="display: none;">
	              <i aria-hidden="true" class="fa fa-trash-o moveToTrash" onclick="moveEmailsToTrash();" style="display: none;"></i>
	              <i class="deleteForever" onclick="deleteEmailsForEver();" style="display: none;">Delete forever</i>
              </div>
              <!-- <div class="box-tools pull-right">
                <div class="has-feedback">
                  <input type="text" placeholder="Search Mail" class="form-control input-sm">
                  <span class="glyphicon glyphicon-search form-control-feedback"></span>
                </div>
              </div> -->
              <!-- /.box-tools -->
            </div>
            
			<%-- commented by Manoj, TODO controls functionaliy
			<!-- /.box-tools -->
			<div class="mailbox-controls">
			<div>
			<input id="checkAllEmails" class="checkbox-custom" onclick="toggleEmails(this);deleteMail();" name="checkAllEmails" type="checkbox">
			<label for="checkAllEmails" id="checkAllBtn" class="checkbox-custom-label"></label>
			</div>
				<button class="btn btn-danger mailbtn-custom" type="button" onclick="getMailBoxIds();" id="trashBtn"><i class="fa fa-trash-o" id="trashtype"></i></button>
                <button class="btn btn-default mailbtn-custom" type="button" id="refreshBtn" onclick="refreshList();"><i class="fa fa-refresh"></i></button>
               
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
			 --%>
			 
	<!-- mail box table -->
			<div class="mailbox-messages datatable_filter mailbox-table">
                 <div id="mailBoxList"></div>
              </div>
	<!-- mail box table ends -->   

			</div>
			</div>

			<div class="col-md-9" id="details">
				<div id="mailContentLoading"></div>
				<div class="mail-box mail-content clearfix" id="body-details">
				</div>
			</div>



				<!-- Mail view area -->

			

			</div>
		   </div>
		</div>
  </section>

<!-- Dynamic Mail Compose popup-->
<div class="side_mask"></div>
<div class="modal" id="dynamicmailcompose1" style="display: none;">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" onclick="closeComposeEmailPopup();">&times;</button>
				<h4 class="modal-title">Compose Mail</h4>
			</div>
			<div class="modal-body clearfix">

				
				
				
			</div>
		</div>
	</div>
</div>

<div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search-small" id="dynamicmailcompose" style="display: none;">
	<h5 class="headerAdsrch">
		<span>Compose Mail</span>
		<a href="javascript:void(0)" class="close-div" onclick="closeComposeEmailPopup();"><i class="fa fa-close"></i></a>
	</h5>
	<div class="addInner">
		<div class="row">
			<div class="col-md-12">
				<div class="form-horizontal margin-top0 noPad">
					<form:form id="email_form" name="email_form">
						<div class="col-md-12 clearfix">
							<label class="col-md-1 control-label text-muted small">From</label>
							<div class="col-md-11">
								<select id="fromEmail" name="from" data-placeholder="Choose an Email to send ...." class="chosen-select form-control">
									<option value=""></option>
									<c:forEach var="emailAccountList" items="${emailAccountSettingsServiceList}">
										<option data-id="${emailAccountList.id}" value="${emailAccountList.email}">${emailAccountList.email}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-md-12 clearfix mt10">
							<label class="col-md-1 control-label text-primary small">To</label>
							<div class="col-md-11 positionRelative disable-choosen-dropdown">

								<select class="form-control chosen-select" field-name="To List" multiple
									data-placeholder="Enter Emails And Press Enter-Key Or Comma(,) Or Space To Add New Email"
									id="emailsList" name="to" data-validation-optional="true"
									data-validation="validate_email" field-name="Toemails">
								</select>
							</div>
						</div>
						<div class="col-md-12 clearfix mt10">
							<label class="col-md-1 control-label text-primary small">Cc</label>
							<div class="col-md-11 positionRelative disable-choosen-dropdown">

								<select class="form-control chosen-select" field-name="Cc List" multiple
									data-placeholder="Enter Emails And Press Enter-Key Or Comma(,) Or Space To Add New Email"
									id="ccList" name="cc" data-validation-optional="true"
									data-validation="validate_email" field-name="Toemails">
								</select>
							</div>
						</div>
						<div class="col-md-12 clearfix mt10">
							<label class="col-md-1 control-label text-danger small">Subject</label>
							<div class="col-md-11">
								<input type="text" name="subject" data-validation="required" field-name="Subject" id="sendSubject" value="" class="form-control input-sm">
							</div>
						</div>
						<div class="clearfix">
							<!-- Text Area -->
							<div class="col-md-12 clearfix mb10 mt10">
								<div class="col-md-12">
									<div class="clearfix txt-area">
									  <textarea id="bodytext" name="body" class="form-control" rows="5" placeholder="Message" style="height: 170px; max-height: 170px !important; width:100%;"></textarea>
									</div>
								</div>
							</div>
							
							<!-- Text Area Ends -->

							<!-- Attachment button-->
							<!-- <div class="col-md-12 clearfix mb10">
								<div class="col-md-12 attachment">
									<div class="input-group">
										<label class="input-group-btn">
											<b class="btn btn-primary">
												<i class="fa fa-paperclip"></i>
												<input type="file" name="attachfile" style="display: none;" id="attachfileBtn" class="upload">
											</b>
										</label>
										<input type="text" class="form-control" readonly id="attachfile-mail" placeholder="Files are not selected...">
										<div class="clearUpload" style="display: none;"></div>
									</div>
								</div>
							</div> -->

							<div onclick="alert('div');">
								<b class="btn btn-primary"> <i class="fa fa-paperclip"></i>
									<input name="attachfile" style="display: none;" id="attachfileBtn" class="upload" type="file">
								</b>
							</div>
							<!-- Attachment button-->
							<!-- Attachments preview-->
							<div class="col-md-12 clearfix mb10" id="mailAttachmentsDiv">
							</div>
							<!-- Attachments preview-->
						</div>
						<!-- Attachment button -->
					</form:form>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix advancedsearch-footer">
		<div class="pull-right mr">
			<input value="Send" id="sendEmail" onclick="sendEmailFromMailBox('compose');" class="btn btn-sm btn-success" type="button">
			<input value="Reply" id="replyEmail" onclick="replyEmailFromMailBox('reply');" class="btn btn-sm btn-success" type="button" style="display: none;">
			<input value="Forward" id="forwardEmail" onclick="sendEmailFromMailBox('forward');" class="btn btn-sm btn-success" type="button" style="display: none;">
			<input value="Cancel" id="clear_search" onclick="closeComposeEmailPopup();" class="btn btn-sm btn-primary" type="button">
		</div>
	</div>
</div>

<script src="/resources/js/nicEdit-latest.js"></script>
<script src="/resources/js/nicEdit.js"></script>

<script type="text/javascript">
	$(function() { 
		tagIt("emailsList", 'Enter Emails And Press Enter-Key Or Comma(,) Or Space To Add New Email', 'tagItBykeyForEmails','emails');
		tagIt("ccList", 'Enter Emails And Press Enter-Key Or Comma(,) Or Space To Add New Email', 'tagItBykeyForEmails','emails');
		
	
		$("#attachfileBtn").change(function() {
			uploadAttachment("attachfileBtn", "Email Documents", "email_form");
			$(".clearUpload").show();
		});
		
		$(".clearUpload").click(function(){
			alert("Remove Attachment");
			/* $("#attachfile-mail").val("");
			$("#attachfileBtn").val("");
		    $("#email_form input[data-id*=attachfileBtn]").remove();
		    $(".clearUpload").hide(); */
		});
		
	});

  function getMails(type) {
	  var sourceleadUrl ='${sourceleadUrl}';
	  
	  document.getElementById("inbox").style.display="";
	  document.getElementById("details").style.display="none";
	  /* document.getElementById("trashBtn").style.display="none"; */
	  $('#checkAllEmails').removeAttr('checked');
	  $('#mailboxLabel').html(type);
	  
	  if(type == "Trash"){
		  $('#trashtype').html(' <b>Delete For Ever</b>');
	  }else{
		  $('#trashtype').html('');
	  }
	  
	  setActiveTab(type)
	  var mailType = '${mailType}';
	  
	  $("#mailLoading").attr('style','position:absolute; width:97%; height:99%; background-color:rgba(255,255,255,0.8); top:-1px; left:14px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat; background-size:100px;');
	  
	  $.ajax({
          type : "POST",
          url: sourceleadUrl+"/getUserMailsFromELK?${_csrf.parameterName}=${_csrf.token}&type="+type+"&mailType="+mailType,    
           success :function(data) {
           	
                if(data != null && data != 'undefined' && data != "" && data != '[]'){
              		
                	 document.getElementById("checkAllBtn").style.display="";
                	 document.getElementById("refreshBtn").style.display="";
              			 
              			var result = '';
              			result = result +'<input type="hidden" id="emailListType" value='+type+'>';
              			result = result +'<table id="mails" class="table table-hover table-striped">';
              			
                		result = result +'<thead>';
                		result = result +'<tr>';
                		result = result +'<th></th>';
                		result = result +'<th></th>';
                		result = result +'<th></th>';
                		result = result +'<th></th>';
                		result = result +'</tr>';
                		result = result +'</thead>';
                		
              			result = result +'<tbody>';
              			
              			 var json = JSON.parse(data);
		                    for (var i = 0; i < json.length; i++) { 
		                    	 var opt = json[i]; 
		                    	 
		                    	 var isFavourite = "";
		                    	 var isFavClass = "";
		                    	 var isMailRead = "";
		                    	 
	                    		 if(opt.isFavourite == 1){
				            		isFavourite = "checked";
				            		isFavClass = "activestar-rating";
				            	 }
	                    		 
	                    		 if(opt.isMailRead == 1){
	                    			 isMailRead = "mail-read"
	                    		 }else{
	                    			 isMailRead = "mail-unread"
	                    		 }
		                    	 
		                    	result = result +' <tr id="checkbox'+opt.mailBoxId+'">';
		              			result = result +'<td style="width : 20px;">';
		              			result = result +'<div class="mrleft8">';
		              			result = result +'<input id="check'+opt.mailBoxId+'" value="'+opt.mailBoxId+'" class="checkbox-custom" onclick="deleteMail();" name="mailIds[]" type="checkbox">';
		              			result = result +'<label for="check'+opt.mailBoxId+'" class="checkbox-custom-label"></label>';
		              			result = result +'</div>';
		              			result = result +'</td>';
		              			
	              			    if(opt.mailStatus != 'Trash'){
	              			    	result = result +"<td style='width : 25px;'>";
			                        result = result +"<div class='star-rating mailbox-rating positionRelative "+isFavClass+"' id='favourite"+opt.mailBoxId+"'>";
			                        result = result +"<input type='checkbox' id='fav"+opt.mailBoxId+"' "+isFavourite+" onclick='addFavourite("+opt.mailBoxId+")'></input>";
		              			
		                        result = result +" <label></label>";
		                        result = result +"</div>";
		                        result = result +"</td>";
	              			  }
		                        
		                        if(opt.mailStatus == 'Trash'){
		                        	result = result +'<td></td>';
		              			 }
			              			
		              			
		              			result = result +'<td class="mailbox-subject '+isMailRead+'"><a href="#" onclick="getEmailDetails(\''+opt.uuid+'\',\''+type+'\',\''+mailType+'\')">'+opt.subject+'</a></td>';
		              			result = result +'<td class="mailbox-date">'+opt.calDays+'</td>';
		              			result = result +'</tr>';
		                    }
		                    
		                     result = result +"</tbody>";
		                     result = result +"</table>"; 
		                     result = result +'</div>'; 
              			
	                 	 $("#mailBoxList").empty(); 
	                 	 $("#mailDetails").empty(); 
	                     $("#mailBoxList").append(result); 
	                     pagination(); 
	                     $("#mailLoading").removeAttr("style"); 
                }else{
 	    	         
               	var result ="";
               	    result = result +'<input type="hidden" id="emailListType" value='+type+'>';
	    	        result = result +'<div class="modal-body clearfix">';
	           		result = result +'     </div>';
	           		result = result +'     <div class="modal-bodyCustom mt clearfix popupFilter">'; 
	           		result = result +'<table class="table table-hover table-striped">';
	           		result = result +'           <thead>';
	           		result = result +'           <p class="text-center text-muted"><i class="noMailImg"></i><br/>No mails in your '+type+' </p>';
	           		result = result +'           </thead>';
	        		result = result +'           </table>';
	        		result = result +'     </div>';
	        		result = result +'     </div>';
					$("#mailBoxList").empty();
					$("#mailDetails").empty(); 
				    $("#mailBoxList").append(result); 
				    $("#mailLoading").removeAttr("style"); 
               }
         },
           
      }); 
	  
  }
  
  </script>

<script>
function pagination(){
    $("#mails").DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[20, 10, 15, -1], [20, 10, 15, "All"]],
    	"bLengthChange": false,
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
function setActiveTab(type){
	
	var mailType = '${mailType}';
	
	if(type == 'Inbox'){
		  document.getElementById("sentActive").removeAttribute("class")
		  document.getElementById("starredActive").removeAttribute("class")
		  if(mailType == 'hotcampaigns'){
			  document.getElementById("requestedActive").removeAttribute("class")
		  }
		  document.getElementById("trashActive").removeAttribute("class")
		  if(mailType == 'mails'){
		  	document.getElementById("settingsActive").removeAttribute("class")
		   }
		  document.getElementById('inboxActive').className = 'active'
	}
	
	else if(type == 'Sent'){
		  document.getElementById("sentActive").className = 'active'
		  document.getElementById("starredActive").removeAttribute("class")
		  if(mailType == 'hotcampaigns'){
			  document.getElementById("requestedActive").removeAttribute("class") 
		  }
		  document.getElementById("trashActive").removeAttribute("class")
		  if(mailType == 'mails'){
			  document.getElementById("settingsActive").removeAttribute("class")
		  }
		  
		  document.getElementById('inboxActive').removeAttribute("class")
	}
	
	else if(type == 'Starred'){
		  document.getElementById("starredActive").className = 'active'
		  document.getElementById("sentActive").removeAttribute("class")
		  if(mailType == 'hotcampaigns'){
			  document.getElementById("requestedActive").removeAttribute("class")  
		  }
		  document.getElementById("trashActive").removeAttribute("class")
		  if(mailType == 'mails'){
			  document.getElementById("settingsActive").removeAttribute("class")
		  }
		  
		  document.getElementById('inboxActive').removeAttribute("class")
	}
	
	else if(type == 'Requested'){
		 if(mailType == 'hotcampaigns'){
			 document.getElementById("requestedActive").className = 'active' 
		 }
		  document.getElementById("sentActive").removeAttribute("class")
		  document.getElementById("starredActive").removeAttribute("class")
		  document.getElementById("trashActive").removeAttribute("class")
		  document.getElementById('inboxActive').removeAttribute("class")
		  if(mailType == 'mails'){
			  document.getElementById("settingsActive").removeAttribute("class")
		  }
		   
	}
	
	else if(type == 'Trash'){
		  document.getElementById("trashActive").className = 'active'
		  document.getElementById("sentActive").removeAttribute("class")
		  document.getElementById("starredActive").removeAttribute("class")
		  if(mailType == 'hotcampaigns'){
			  document.getElementById("requestedActive").removeAttribute("class")
		  }
		  document.getElementById('inboxActive').removeAttribute("class")
		   if(mailType == 'mails'){
			   document.getElementById("settingsActive").removeAttribute("class")
		   }
		  
	}
	else if(type == 'Settings'){
		  document.getElementById("settingsActive").className = 'active'
		  document.getElementById("trashActive").removeAttribute("class")
		  document.getElementById("sentActive").removeAttribute("class")
		  document.getElementById("starredActive").removeAttribute("class")
		  if(mailType == 'hotcampaigns'){
			  document.getElementById("requestedActive").removeAttribute("class")
		  }
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
					 document.getElementById("favourite"+mailboxId).removeAttribute("class");
					 document.getElementById("favourite"+mailboxId).className = 'star-rating mailbox-rating positionRelative activestar-rating' 
				}else{
					 document.getElementById("favourite"+mailboxId).removeAttribute("class");
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
	
}
</script>

<script>
function getMailBoxIds(){
	var sourceleadUrl = '${sourceleadUrl}';
	var mailIds = [];
	 $("input[name='mailIds[]']:checked").each( function (i) {
		 mailIds[i] = $(this).val();
		 $("#checkbox"+mailIds[i]).html("<td colspan='4'  id='loading"+mailIds[i]+"'></td>");
	     $("#loading"+mailIds[i]).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
		 
	});
	 
	for (var i=0; i< mailIds.length; i++) {
		 document.getElementById("checkbox"+mailIds[i]).remove();					 
	}
	 
	
	 $("#mailLoading").attr('style','position:absolute; width:97%; height:99%; background-color:rgba(255,255,255,0.8); top:-1px; left:14px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat; background-size:100px;');
		 $.ajax({
			type : "POST",
			url : sourceleadUrl+"/sendMailsToTrash?${_csrf.parameterName}=${_csrf.token}&mailIds="+mailIds,
			success : function(data) {
				
				var json = JSON.parse(data);
				
				 $('#inboxUnread').html(json['unreadInboxCount']);
         	     $('#requestedUnread').html(json['unreadRequestedCount']);
         	     
         	     $("#mailLoading").removeAttr("style");
				
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
function getEmailDetails(uuid,type) {
	var sourceleadUrl = '${sourceleadUrl}';
	
	 document.getElementById("details").style.display="";
	 document.getElementById("inbox").style.display="none";
	 
	 $("#mailContentLoading").attr('style','position:absolute; width:97%; height:99%; background-color:rgba(255,255,255,0.8); top:-1px; left:14px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat; background-size:100px;');
	 var mailType = '${mailType}';
	$.ajax({
		type : "POST",
		url : sourceleadUrl+"/getEmailDetails?${_csrf.parameterName}=${_csrf.token}&UUID="+uuid+"&type="+type+"&mailType="+mailType,
		success : function(data) {
			
			 var json = JSON.parse(data);
             for (var i = 0; i < json.length; i++) { 
             	 var opt = json[i]; 
             	 
           	     $('#inboxUnread').html(opt.unreadInboxCount);
           	     $('#requestedUnread').html(opt.unreadRequestedCount);
           	     $('#unreadMailsCount').html(opt.totalUnReadMailsCount);
            	     
                 var result =""; 
                 
              	result = result +'<div class="box-tools pull-right">';
              	result = result +'<div class="btn-group">';
              	if(opt.previousMailUUID != null && opt.previousMailUUID != '' && opt.previousMailUUID != 'undefined'){
              		result = result +'<button class="btn btn-default btn-sm" type="button" onclick="getEmailDetails(\''+opt.previousMailUUID+'\',\''+type+'\')"><i class="fa fa-chevron-left"></i></button>';
              	}else{
              		result = result +'<button class="btn btn-default btn-sm" type="button" ><i class="fa fa-chevron-left mail-next-previous-muted"></i></button>';
              	}
              	
              	if(opt.nextMailUUID != null && opt.nextMailUUID != '' && opt.nextMailUUID != 'undefined'){
              		result = result +'<button class="btn btn-default btn-sm" type="button" onclick="getEmailDetails(\''+opt.nextMailUUID+'\',\''+type+'\')"><i class="fa fa-chevron-right"></i></button>';
              	}else{
              		result = result +'<button class="btn btn-default btn-sm" type="button" ><i class="fa fa-chevron-right mail-next-previous-muted"></i></button>';
              	}
              	
              	
              	result = result +'</div>'; 
                result = result +'</div>';
                result = result +'</div>';
			    result = result +'<div class="mailbox-read-info">';
			    result = result +'<h3 class="col-md-4 padding_left">'+opt.subject+'</h3>';
// 			    result = result +' <a href="<c:url value='/campaignStats'/>" class="pull-left btn btn-sm btn-primary">Stats</a>';
			    result = result +'<div class="clearfix"></div>';
			    result = result +'<h5>From: '+opt.fromMail+'' 
			    result = result +'<h5 class="mail-height">To: '+opt.toMail+'' 
			    result = result +'<span class="mailbox-read-time pull-right">'+opt.calDays+'</span></h5>';
			    result = result +'</div>	';

	   			result = result +'<div class="mailbox-controls clearfix">';
	   			result = result +'<div class="pull-left">';
	   			result = result +'<div class="btn-group">';
	   			result = result +'<button class="btn btn-danger" type="button" title="Delete" onclick="deleteReadMail(\''+uuid+'\',\''+opt.nextMailUUID+'\',\''+type+'\');"><i class="fa fa-trash-o"></i></button>';
	   		    result = result +'<button class="btn btn-warning" type="button" title="Reply" onclick="replayToMail(\''+uuid+'\');"><i class="fa fa-reply"></i></button>';
	   		    if(type == 'Sent' && opt.candidateId != null){
	   		    	result = result +'<button class="btn btn-info" type="button" title="Forward" onclick="forwardEmailToHotMail(\''+uuid+'\');"><i class="fa fa-share"></i></button>';
	   		    }else{
	   		    	result = result +'<button class="btn btn-info" type="button" title="Forward" onclick="forwardEmail(\''+uuid+'\');"><i class="fa fa-share"></i></button>';
	   		    }
	   		     
	   			result = result +'</div>';
	   			result = result +'</div>';
	   			result = result +'</div>'; 
	   			  
	   			result = result +'<div class="mailbox-read-message clearfix borderbottm-none wordWrap-breakword">';
	   			
	   		 if(opt.salutation != null && opt.salutation != 'undefined' && opt.salutation != ""){
	   			result = result +'<p>'+opt.salutation+',</p>';
	   		 }
	   			
	   			 if(opt.comments != null && opt.comments != 'undefined' && opt.comments != ""){
	   				result = result +'<p>'+opt.comments+'</p>';
	   			}
	   			
	   			if(opt.body != null && opt.body != 'undefined' && opt.body != "" && opt.mailType == '1'){
	   				result = result +'<p>'+opt.body+'</p><br/>';
	   			}  
	   			if(opt.campaignTableData != null && opt.campaignTableData != 'undefined' && opt.campaignTableData != ""){
	   				result = result +'<p>'+opt.campaignTableData+'</p><br/>';
	   			}
	   			
	   			if(opt.fileName != null && opt.fileName != "" && opt.fileName != 'undefined'){
	   				result = result +'<div class="require_wrap_details_cta require_uploadfile"><b>Attachment:</b> <span>'+opt.fileName+'</span><a download class="glyphicon glyphicon-download-alt" href=\"'+opt.fileUrl+'\"></a> ';
	   			    result = result +'<div class="clearfix"></div>';
	   				result = result +'</div>';
	   				result = result +'<div class="clearfix"></div>';
	   			}
	   		if(opt.mailType == '1'){
	   			if(opt.signature != null && opt.signature != "" && opt.signature != 'undefined'){
	            	  result = result +'<p>'+opt.signature+'</p>';
	              }else{
	            	  result = result +'<p>Thanks,<br>'+opt.fromMail+'</p>';
	              } 
	   		}	
            
            $("#mailBoxList").empty(); 
            $("#mailDetails").empty(); 
            $("#mailDetails").append(result);
            $("#mailContentLoading").removeAttr("style"); 
             }
		},
	});  
}

</script>


<script>
function getEmialSettings(){
	setActiveTab('Settings');
	
	 var result ="";
	 
	 result = result +'<div class="mail-box mail-content">';
	 result = result +'<div class="mail-box-header"><h3 class="box-title">Mail account setting</h3></div>';
	 result = result +'<div class="newMailCon">';
	 result = result +'<h4>Setup email account for sending</h4>';
	 result = result +'<p>Please select your provider</p>';
	 result = result +'<ul>';
	 result = result +'  <li>';
	 result = result +'<a href="<c:url value="/app/emailSettings?source=gmail"/>">';
	 result = result +'  <img src="/resources/images/gmail-logo.jpg" alt=""/>Gmail</a>';
	 result = result +'  </li>';
	 result = result +' <li>';
	 result = result +'	  <a href="<c:url value="/app/emailSettings?source=yahoo"/>">';
	 result = result +' <img src="/resources/images/yahoo-logo.jpg" alt=""/>Yahoo</a>';
	 result = result +' </li>';
	 result = result +' <li>';
	 result = result +' <a href="<c:url value="/app/emailSettings?source=others"/>">';
	 result = result +' <img src="/resources/images/mail-icon.jpg" alt="" />Other</a>';
	 result = result +'</li>	';				  					  
	 result = result +' <div class="clear"></div>';
	 result = result +'</ul>';
	 result = result +'</div>';
	 result = result +'</div>';
	 
	 
	$("#mailboxLabel").html('Settings');
	$("#mailBoxList").empty(); 
	$("#mailDetails").empty(); 
	$("#mailBoxList").append(result);
	
	
}
</script>

<script>
function deleteReadMail(uuid,nextMailUUID,type){
	var sourceleadUrl = '${sourceleadUrl}';
	
	 $("#mailContentLoading").attr('style','position:absolute; width:97%; height:99%; background-color:rgba(255,255,255,0.8); top:-1px; left:14px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat; background-size:100px;');
	 
	$.ajax({
		type : "POST",
		url : sourceleadUrl+"/deleteReadMail?${_csrf.parameterName}=${_csrf.token}&UUID="+uuid+"&type="+type,
		success : function(data) {
			getMails(type);
		},
	});   
}
</script>
<script>
function activeEmail(id, ele){
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	$(".compose-mail").hide();
	$(".userMailList").find("li").removeClass("Act");
	$(ele).parent().addClass("Act");
	if(id === 0) {
		getMails('Inbox');
		$("#composeSourceleadMail").show();
		$("#composeDynamicMail").hide();
		$("#sourceleadEmailFolder").show();
		$("#dynamicEmailFolder").hide();
		$("#loading").removeAttr("style");
	} else {
		$("#composeSourceleadMail").hide();
		$("#composeDynamicMail").show();
		$("#sourceleadEmailFolder").hide();
		$("#dynamicEmailFolder").show();
	
		$("#activeClass"+id).removeClass("Active");
		$("#activeClass"+id).addClass("Active");
		$("#mailBoxList").empty();
		$("#mailBoxList").append("<p class='text-center text-muted'><i class='noMailImg'></i></p>");
		$("#body-details").empty();
	
		$.ajax({
			type : "POST",
			url : sourceleadUrl+"/emailAccountRead?${_csrf.parameterName}=${_csrf.token}&id="+id,
			success : function(response) {
				if(response && response.data != null && response.data.length > 0){
				 var result = "";
				 for(var e in response.data) {
					 var report = response.data[e];
					 var margin = report.split("<<>>")[1].split(".").length > 1 ? (report.split("<<>>")[1].split(".").length - (report.split("<<>>")[0] === "INBOX" ? 1 : 2)) : 0;
					 result = result + "<div style='margin-left : " + (margin * 20) + "px;'>";
					 result = result + "<ul class='Cursor nav nav-pills nav-stacked  inBox-list'>";
					 result = result + "<li name='mailFolder' data-index='" + e + "' onClick='readFolder(\"" + report.split("<<>>")[0] + "\",\"" + report.split("<<>>")[1] + "\"," + id + ", 0, 10, " + e + ");'>" + report.split("<<>>")[0] + "</li>";
					 /* result = result + "<li onClick='readFolder(\"" + report + "\"," + id + ");'>" + report + "</li>"; */
					 result = result + "</ul>";
					 result = result + "</div>";
				 }
				 $("#loading").removeAttr("style");
				 $("#dynamicEmailFolder").empty();
				 $("#dynamicEmailFolder").append(result);
				 autoSelectFirstEmailFolder();
				} else {
					/* alert("Configured credentials were wrong,Please configure with right credentials") */
					alert(response.responseMessage);
					window.setTimeout(function() {
						location.replace(sourceleadUrl+"/mailBox/mails");
			        }, 1000);
				}
			},
		}); 
	}
}

function autoSelectFirstEmailFolder() {
	/* $("#dynamicEmailFolder").children(0).find("li").click(); */
	/* console.log($("#dynamicEmailFolder").children(0).find("li").html()); */
	if($("li[name='mailFolder']").length > 0)
		$("li[name='mailFolder']")[0].click();
}
</script>
<script type="text/javascript">
function readFolder(name, fullName, id, pageNo, pageSize, index){
	$('#mailboxLabel').html(name);
	$("#mailBoxList").empty();
	$("#mailBoxList").append("<p class='text-center text-muted'><i class='noMailImg'></i></p>");
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	$("#body-details").empty();
	
	$("#dynamicEmailFolder li").removeClass("active");
	$("#dynamicEmailFolder li[data-index=" + index + "]").addClass("active");
	
	$.ajax({
		type : "POST",
		url : sourceleadUrl+"/emailFolderRead?${_csrf.parameterName}=${_csrf.token}&folderName="+fullName+"&id="+id+"&pageNo="+pageNo+"&pageSize="+pageSize,
		success : function(data) {
			
					var result = '';
		  			result = result +'<table id="emailMailDetailsPagination" class="table table-hover table-striped">';
		    		result = result +'<tbody>';
		    		var emailList = data.data;
		    		if(emailList.length > 0) {
			    		for(var e in emailList) {
						 	result = result +' <tr id="checkbox' + emailList[e].messageId + '" data-id=' + emailList[e].messageId + '>';
						 
			       			result = result +'<td style="width : 20px;">';
			       			result = result +'<div class="mrleft8">';
			       			result = result +'<input id="check' + emailList[e].messageId + '" value=" " class="checkbox-custom"  name="mailIds[]" type="checkbox" onclick="showOrHideMailControls();">';
			       			result = result +'<label for="check' + emailList[e].messageId + '" class="checkbox-custom-label"></label>';
			       			result = result +'</div>';
			       			result = result +'</td>';
			       			
			       			result = result +'<td style="text-align: left !important;" class="mailbox-subject" onclick="getEmailBodyForMailBox(' + id + ',' + emailList[e].messageId + ',\''+fullName+'\')" data-id="' + emailList[e].messageId + '">';
			       			result = result +'<div>';
			       			result = result +'	<div>' + (fullName.indexOf("Sent") !== -1 ? (emailList[e].to != null ? emailList[e].to.replace(/</g, "&lt;").replace(/>/g,"&gt;") : "") : (emailList[e].from != null ? emailList[e].from.replace(/</g, "&lt;").replace(/>/g,"&gt;") : "")) + '</div>';
			       			result = result +'	<div style="font-size: 12px;color: #666;">' + emailList[e].subject + '</div>';
			       			result = result +'</div>';
			       			result = result +'</td>';
			       			/* result = result +'<td class="mailbox-subject" onclick="getEmailBody(' + i + ')" data-id="' + i + '">' + opt.subject + '</td>'; */
			       			result = result +'<td class="mailbox-date" data-id="' + emailList[e].messageId + '">' + emailList[e].sentDate + '</td>';
			       			if(emailList[e].attachmentsCount > 0)
			       				result = result + '<td><i class="fa fa-paperclip"></i></td>';
			       			else 
			       				result = result + '<td></td>';
			       			result = result +'</tr>';
			    		}
			    		result = result +'</tbody>';
			    		result = result +"</table>";
			    		$("#mailBoxList").empty();
			    		$("#mailDetails").empty(); 
					    $("#mailBoxList").append(result); 
					    /* emailMailDetailsPagination(); */
					    prepareDynamicPagination(name, fullName, id, data.paginator, index);
		    		}
				    $("#loading").removeAttr("style"); 
				    $("#inbox").show();
		},
	});
}
function prepareDynamicPagination(name, fullName, id, paginator, index) {
	var text = "<div class='clearfix'>";
	text += "<div class='dataTables_length pull-left' id='emailMailDetailsPagination_length'>";
	text += "	<label>Show ";
	text += "		<select name='emailMailDetailsPagination_length' aria-controls='emailMailDetailsPagination' class=''>";
	text += "			<option value='10'>10</option>";
	text += "			<option value='15'>15</option>";
	text += "			<option value='20'>20</option>";
	text += "			<option value='-1'>All</option>";
	text += "		</select>";
	text += "	entries</label>";
	text += "</div>";

	var resultsFrom = (paginator.pageNo * paginator.pageSize) + 1;
	var resultsTo = (paginator.pageNo + 1) * paginator.pageSize > paginator.totalRows ? paginator.totalRows : (paginator.pageNo + 1) * paginator.pageSize;	
	text += "<div class='dataTables_info pull-left' id='emailMailDetailsPagination_info' role='status' aria-live='polite'>Showing " + resultsFrom + " to " + resultsTo + " of " + paginator.totalRows + " entries</div>";

	var prevFlag = resultsFrom !== 1;
	var nextFlag = resultsTo !== paginator.totalRows;
	
	text += "<div class='dataTables_paginate paging_simple_numbers pull-right' id='emailMailDetailsPagination_paginate' style='position: relative; top: 11px; right: 10px;'>";
	text += "	<a class='fa fa-angle-left paginate_button previous'";
	if(prevFlag)
		text += " onclick='readFolder(\"" + name + "\", \"" + fullName + "\", " + id + ", " + (paginator.pageNo - 1) + ", " + paginator.pageSize + ", " + index + ");'>";
	else
		text += " style='cursor: no-drop;' >";
		text += "	</a>";
	text += "	<a class='fa fa-angle-right paginate_button next'";
	if(nextFlag)
		text += " onclick='readFolder(\"" + name + "\", \"" + fullName + "\", " + id + ", " + (paginator.pageNo + 1) + ", " + paginator.pageSize + ", " + index + ");'>";
	else
		text += " style='cursor: no-drop;' >";
	text += " </a>";
	text += "</div>";
	text += "</div>";
	$("#mailBoxList").append(text);
}
</script>
<script type="text/javascript">
function deleteConMail1(id){
	alert("Delete Id : " + id);
	/* $.ajax({
		type : "POST",
		url : sourceleadUrl+"/deleteEmailAccount?${_csrf.parameterName}=${_csrf.token}&id="+id,
		success : function(data) {
				location.replace(sourceleadUrl+"/mailBox/mails");
		},
	});  */
}
</script>



<!-- <script src="resources/js/jquery.dataTables.min.js" type="text/javascript" charset="utf-8"></script> -->

<script>
function emailMailDetailsPagination(){ 
    $('#emailMailDetailsPagination').DataTable( { 
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
function backToEmailList(){
	$("#inbox").show();
    $("#details").hide();
    emailDetails = null;
}

</script>

<script type="text/javascript">
	var emailDetails = null;
	function getEmailBodyForMailBox(emailAccountId, uid, folderName){
	    /* $("#mailDetails").html($("#mailBody"+id).html()); */
	    $("#body-details").empty();
	    $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		$.ajax({
			type : "POST",
			url : sourceleadUrl+"/getEmailByUID?${_csrf.parameterName}=${_csrf.token}&emailAccountId=" + emailAccountId + "&uid="+uid + "&folderName=" + folderName,
			success : function(data) {
				if(data && data != null) {
					emailDetails = data;
					var body ="<div class='mail-widget'>";
					body += "	<i onclick='backToEmailList();' aria-hidden='true' class='fa fa-arrow-left' title='back'></i>";
					body += "	<div class='pull-right'>";
					if($("#mailboxLabel").html().toLowerCase() === "trash") 
						body += "		<i title='delete' onclick='deleteEmailForEver(" + emailAccountId + ", " + data.messageId + ", \"" + folderName + "\");'>Delete forever</i>";
					else
						body += "		<i aria-hidden='true' class='fa fa-trash-o' title='Move to Trash' onclick='moveEmailToTrash(" + emailAccountId + ", " + data.messageId + ", \"" + folderName + "\");'></i>";
					body += "		<i aria-hidden='true' class='fa fa-reply' title='Reply to sender' onclick='showReplyEmailPopup(" + emailAccountId + ", " + data.messageId + ", \"" + folderName + "\", false);'></i>";
					body += "		<i aria-hidden='true' class='fa fa-reply-all' title='Reply to all senders/recipients' onclick='showReplyEmailPopup(" + emailAccountId + ", " + data.messageId + ", \"" + folderName + "\", true);'></i>";
					body += "		<i aria-hidden='true' class='fa fa-arrow-right' title='Forward selected message' onclick='showForwardEmailPopup(" + emailAccountId + ", " + data.messageId + ", \"" + folderName + "\");'></i>";
					body += "	</div>";
					body += "</div>";
					body+="<div class='mailbox-read-info' id='mailBody" + data.messageId + "'>"; 
					body+="<h4 class='clearfix' style='border-bottom:1px solid #ddd; padding-bottom:10px;'>" + data.subject + "</h4>";
	
					body+="<div class='row'>";
					/* body+="<p class='clearfix mb10 col-md-8'><i class='text-muted fa fa-user paddingRight'></i>" + (folderName.indexOf("Sent") !== -1 ? data.to : data.from) + "</p>"; */
					body+="<p class='clearfix mb10 col-md-8'> From : " + data.from.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "</p>";
					body+="<p class='clearfix mb10 col-md-8'> To : " + data.to.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "</p>";
					/* body+="<p class='clearfix mb10 col-md-8'>" + (folderName.indexOf("Sent") !== -1 ? "To" : "From") +" : " + (folderName.indexOf("Sent") !== -1 ? data.to.replace(/</g, "&lt;").replace(/>/g,"&gt;") : data.from.replace(/</g, "&lt;").replace(/>/g,"&gt;")) + "</p>";
					body+="<p class='clearfix mb10 col-md-8'>" + (folderName.indexOf("Sent") !== -1 ? "From" : "To") +" : " + (folderName.indexOf("Sent") !== -1 ? data.from.replace(/</g, "&lt;").replace(/>/g,"&gt;") : data.to.replace(/</g, "&lt;").replace(/>/g,"&gt;")) + "</p>"; */						
					if(data.cc != null && data.cc !== "") {
						body+="<p class='clearfix mb10 col-md-8'>Cc : " + data.cc.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "</p>";
					}
					body+="<p class='clearfix mb10 col-md-4 text-right'>" + data.sentDate + "</p>";
					body+="</div>";
					body+="<p class='col-md-12 clearfix'>" + data.body + "</p>";
					if(data.filesList != null && data.filesList.length > 0) {
						var text = "<div class='mailAttaWrap clearfix'>";
						text += "       <h2>Attachments</h2>";
	
						for(var e in data.filesList) {
							var attachment = data.filesList[e];
							
							text += "           <div class='AttaCta'>";
							var attachClass = "fa-file-o";
							
							if(attachment.fileExtension === ".jpg" || attachment.fileExtension === ".jpeg" || attachment.fileExtension === ".png") {
								attachClass = "fa-file-image-o";
							} else if(attachment.fileExtension === ".pdf") {
								attachClass = "fa-file-pdf-o";
							} else if(attachment.fileExtension === ".xls" || attachment.fileExtension === ".xlsx") {
								attachClass = "fa-file-excel-o";
							} else if(attachment.fileExtension === ".doc" || attachment.fileExtension === ".docx") {
								attachClass = "fa-file-word-o";
							} else if(attachment.fileExtension === ".txt") {
								attachClass = "fa-file-text-o";
							} else if(attachment.fileExtension === ".rar") {
								attachClass = "fa-file-archive-o";
							}
							
							text += "               <div class='AttaIcon'>";
							if(attachment.fileExtension === ".jpg" || attachment.fileExtension === ".jpeg" || attachment.fileExtension === ".png")
								text += "					<img src='" + (attachment.url + "/" + attachment.fileName) + "' alt='" + attachment.fileName + "'></img>";
							else
								text += "					<i class='fa " + attachClass + "' aria-hidden='true'></i>";
							text += "				</div>";
							text += "               <div class='AttaText'><i class='fa fa-paperclip' aria-hidden='true'></i>" + attachment.fileName + "</div>";
							text += "               <div class='AttaCtaAct'>";
							text += "                   <i class='fa fa-paperclip' aria-hidden='true'></i>";
							text += "                   <p>" + attachment.fileName + "</p>";
							text += "                   <a href='" + (attachment.url + "/" + attachment.fileName) + "'><span class='fa fa-arrow-down' aria-hidden='true'></span></a>";
							text += "               </div>";
							text += "           </div>";
							
							/* body += "<div class='col-md-10' style='width: auto;border: 1px solid #CECECE;'>";
							body += "<img src='" + attachment.url + "/" + attachment.fileName + "' style='width: 100px;'>";
							body += "<span>" + attachment.fileName + " : " + attachment.fileSize.toFixed(2) + " " + attachment.sizeUnit + "</span></div>"; */
						}
						text += "       </div>";
						body += text;
					}
					body+="</div>";
					
					$("#body-details").append(body);
					$("#inbox").hide();
				    $("#details").show();
				} else {
					alert("error while reading email");
				}
				$("#loading").removeAttr("style");
			}
		});
	}

</script>

<script>
$(document).ready(function(){
    $("#one").click(function(){
        $(".inboxShow").hide();
        $(".newmailShow").show();
    });
    $(".aa").click(function(){
        $(".inboxShow").show();
        $(".newmailShow").hide();
    });    
});        
    
    </script>

<script>
function replayToMail(uuid){
	var sourceleadUrl = '${sourceleadUrl}';
	location.replace(sourceleadUrl+"/composeMail?UUID="+uuid);  
}
</script>

<script>
function forwardEmail(uuid){
	var sourceleadUrl = '${sourceleadUrl}';
	location.replace(sourceleadUrl+"/forwardEmail?UUID="+uuid+"&mailType=composeMail");  
}
</script>

<script>
function forwardEmailToHotMail(uuid){
	var sourceleadUrl = '${sourceleadUrl}';
	location.replace(sourceleadUrl+"/forwardEmail?UUID="+uuid+"&mailType=composeHotMail");  
}
</script>

<script type="text/javascript">
	$(document).ready(function() {
		getMails('Inbox');
		/* $($(".newMailSet").find("li")[0]).find("a").click(); */
	});
	
	function selectOrDeselectAllEmails(ele) {
		var flag = $(ele).is(":checked");
		$("#emailMailDetailsPagination tr").each(function () {
			$(this).find("input[type='checkbox']").prop("checked", flag);
		});
		/* $(ele).prop("checked", !flag); */
	}
	
	function showOrHideMailControls() {
		var flag = false;
		$("#emailMailDetailsPagination tr").each(function () {
			if($(this).find("input[type='checkbox']").is(":checked")) {
				flag = true;
				return;
			}
		});
		if(flag) {
			if($("#mailboxLabel").html().toLowerCase() === "trash") {
				$(".deleteForever").show();
				$(".moveToTrash").hide();				
			} else {
				$(".moveToTrash").show();
				$(".deleteForever").hide();			
			}
			$("#mailControlsMaster").show();
		} else {
			$("#mailControlsMaster").hide();
		}	
	}
	
	function moveEmailsToTrash() {
		var emailIds = [];
		$("#emailMailDetailsPagination tr").each(function () {
			var cb = $(this).find("input[type='checkbox']").is(":checked");
			if($(this).find("input[type='checkbox']").is(":checked")) {
				emailIds.push($(this).data("id"));
			}
		});
		alert(emailIds);
	}
	
	function moveEmailToTrash(emailAccountId, messageId, folderName) {
		alert("EmailAccountId : " + emailAccountId + " Delete Mail : " + messageId + " in folder : '" + folderName + "'");
		 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
			$.ajax({
				type : "POST",
				url : sourceleadUrl+"/deleteEmailsByUID?${_csrf.parameterName}=${_csrf.token}&emailAccountId=" + emailAccountId + "&messageIds=" + messageId + "&folderName=" + folderName,
				success : function(data) {
					if(data && data != null && data.response === "SUCCESS") {
						alert("Emails moved to Trash");
					} else {
						alert(data.responseMessage)
					}
					$("#loading").removeAttr("style");
				}
			});
	}
	
	function deleteEmailsForEver() {
		alert("Delete multiple mail forever")
	}
	function deleteEmailForEver(emailAccountId, messageId, folderName) {
		alert("Delete mail forever")
	}
	function showComposeEmailPopup() {
		refreshComposeMailFields();
		$('.side_mask').show();
		$('#dynamicmailcompose').show();
	}
	function closeComposeEmailPopup() {
		refreshComposeMailFields();
		$('.side_mask').hide();
		$('#dynamicmailcompose').hide();
		$("#sendEmail").show();
		$("#replyEmail").hide();
		$("#forwardEmail").hide();
		$(".headerAdsrch span").html("Send Email");
	}
	function refreshComposeMailFields() {
		$("#fromEmail").val("");
		$("#fromEmail").prop("disabled", false);
		$("#fromEmail").trigger("chosen:updated");
		$("#emailsList").val("");
		$("#emailsList").trigger("chosen:updated");
		$("#ccList").val("");
		$("#ccList").trigger("chosen:updated");
		$("#sendSubject").val("");
		nicEditors.findEditor('bodytext').setContent("");
	}
	
	/* function to show compose email popup with auto-filled values required for reply / replyAll */
	function showReplyEmailPopup(emailAccountId, messageId, folderName, replyToAllFlag) {
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		refreshComposeMailFields();
		var activeEmail = $("li[class='Act']").find("p").html();
		$("#fromEmail").prop("disabled",true);
		$("#fromEmail").trigger("chosen:updated");
		
		if(emailDetails !== null) {
			$("#fromEmail").val(activeEmail);
			$("#fromEmail").trigger("chosen:updated");
			$("#emailsList").append("<option selected value='" + emailDetails.from.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "'>" + emailDetails.from.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "</option>");
			$("#emailsList").trigger("chosen:updated");
			if(replyToAllFlag) {
				if(emailDetails.cc !== null && emailDetails.cc !== "" && emailDetails.to.replace(/</g, "&lt;").replace(/>/g,"&gt;").indexOf(activeEmail) === -1)
					$("#ccList").append("<option selected value='" + emailDetails.to.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "'>" + emailDetails.to.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "</option>");
				for(var e in emailDetails.cc.split(";")) {
					var ccMail = emailDetails.cc.split(";")[e].trim().replace(/</g, "&lt;").replace(/>/g,"&gt;");
					if(ccMail.indexOf(activeEmail) === -1)
						$("#ccList").append("<option selected value='" + ccMail + "'>" + ccMail + "</option>");
				}
				$("#ccList").trigger("chosen:updated");
			}
			$("#sendSubject").val("RE: " + emailDetails.subject);
			
			var text = "<p style=\"margin:0;padding:0;font-family: 'times new roman'; font-size: 10pt; word-wrap: break-word;\">";
			text += "	<br/>";
			text += "	<br/>";
			text += "	-----Original Message-----<br/>";
			text += "	From: " + emailDetails.from.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "<br/>";
			text += "	Sent: " + emailDetails.sentDate + "<br/>";
			text += "	To: " + emailDetails.to.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "<br/>";
			if(emailDetails.cc !== null && emailDetails.cc !== "")
				text += "	Cc: " + emailDetails.cc.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "<br/>";
			text += "	Subject: " + emailDetails.subject + "<br/>";
			text += "</p>";
			
			nicEditors.findEditor('bodytext').setContent(text + "<br/> " + emailDetails.body);
			
			$(".headerAdsrch span").html("Reply Email");
			$("#sendEmail").hide();
			$("#replyEmail").show();
			$('.side_mask').show();
			$('#dynamicmailcompose').show();
			$("#loading").removeAttr("style");
		} else {
			$.ajax({
				type : "POST",
				url : sourceleadUrl+"/getEmailByUID?${_csrf.parameterName}=${_csrf.token}&emailAccountId=" + emailAccountId + "&uid=" + messageId + "&folderName=" + folderName,
				success : function(data) {
					if(data && data != null) {
						$("#fromEmail").val(activeEmail);
						$("#fromEmail").trigger("chosen:updated");
						$("#emailsList").append("<option selected value='" + data.from.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "'>" + data.from.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "</option>");
						$("#emailsList").trigger("chosen:updated");
						if(replyToAllFlag) {
							if(emailDetails.cc !== null && emailDetails.cc !== "")
								$("#ccList").append("<option selected value='" + data.to.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "'>" + data.to.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "</option>");
							for(var e in data.cc.split(";")) {
								var ccMail = data.cc.split(";")[e].trim().replace(/</g, "&lt;").replace(/>/g,"&gt;");
								if(ccMail.indexOf(activeEmail) === -1)
									$("#ccList").append("<option selected value='" + ccMail + "'>" + ccMail + "</option>");
							}
							$("#ccList").trigger("chosen:updated");
						}
						$("#sendSubject").val("RE: " + data.subject);
						
						var text = "<p style=\"margin:0;padding:0;font-family: 'times new roman'; font-size: 10pt; word-wrap: break-word;\">";
						text += "	<br/>";
						text += "	<br/>";
						text += "	-----Original Message-----<br/>";
						text += "	From: " + data.from.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "<br/>";
						text += "	Sent: " + data.sentDate + "<br/>";
						text += "	To: " + data.to.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "<br/>";
						if(data.cc !== null && data.cc !== "")
							text += "	Cc: " + data.cc.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "<br/>";
						text += "	Subject: " + data.subject + "<br/>";
						text += "</p>";
						
						nicEditors.findEditor('bodytext').setContent(text + "<br/> " + data.body);
						
						$(".headerAdsrch span").html("Reply Email");
						$("#sendEmail").hide();
						$("#replyEmail").show();
						$('.side_mask').show();
						$('#dynamicmailcompose').show();
					} else {
						alert("error while reading email");
					}
					$("#loading").removeAttr("style");
				}
			});
		}
	}
	
	/* function to send reply message */
	function replyEmailFromMailBox(sendType) {
		if ($('#email_form').validate(false, validationSettings)) {
			var sourceleadUrl = '${sourceleadUrl}';
			
			nicEditors.findEditor('bodytext').saveContent();
					
			var form = $('#email_form')[0];
		  	var formData = new FormData(form)
		  		
		  	var toEmails =  $('#emailsList').val();
		  		 
		  	if(toEmails == null) {
		   		alert("Provide atleast one email id");
		   	} else {
		   		var emailAccountId = $("#fromEmail option:selected").data("id");
		   		var from = $("#fromEmail option:selected").val();
				$("#mailLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
				$.ajax({
					type : "POST",
				  	data: formData,
				  	enctype: 'multipart/form-data',
				  	processData: false,
				  	contentType:false, 
				  	url : sourceleadUrl+"/sendEmailFromMailBox?${_csrf.parameterName}=${_csrf.token}&emailAccountId=" + emailAccountId + "&from=" + from + "&sendType=" + sendType,
				  	success : function(result) {
				  		$("#mailLoading").removeAttr('style');
				  	},
				});
			}
			return true;
		} else {
			return false;
		}		
	}

	/* function to show compose email popup with auto-filled values required for forwarding */
	function showForwardEmailPopup(emailAccountId, messageId, folderName) {
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		refreshComposeMailFields();
		var activeEmail = $("li[class='Act']").find("p").html();
		$("#fromEmail").prop("disabled",true);
		$("#fromEmail").trigger("chosen:updated");
		if(emailDetails !== null) {
			$("#sendSubject").val("FW: " + emailDetails.subject);
			var text = "<p style=\"margin:0;padding:0;font-family: 'times new roman'; font-size: 10pt; word-wrap: break-word;\">";
			text += "	<br/>";
			text += "	<br/>";
			text += "	-----Original Message-----<br/>";
			text += "	From: " + emailDetails.from.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "<br/>";
			text += "	Sent: " + emailDetails.sentDate + "<br/>";
			text += "	To: " + emailDetails.to.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "<br/>";
			if(emailDetails.cc !== null && emailDetails.cc !== "")
				text += "	Cc: " + emailDetails.cc.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "<br/>";
			text += "	Subject: " + emailDetails.subject + "<br/>";
			text += "</p>";
			
			$("#fromEmail").val(activeEmail);
			$("#fromEmail").trigger("chosen:updated");
			nicEditors.findEditor('bodytext').setContent(text + "<br/> " + emailDetails.body);
			
			$(".headerAdsrch span").html("Forward Email");
			$("#sendEmail").hide();
			$("#replyEmail").hide();
			$("#forwardEmail").show();
			$(".side_mask").show();
			$("#dynamicmailcompose").show();
			$("#loading").removeAttr("style");
		} else {
			$.ajax({
				type : "POST",
				url : sourceleadUrl+"/getEmailByUID?${_csrf.parameterName}=${_csrf.token}&emailAccountId=" + emailAccountId + "&uid=" + messageId + "&folderName=" + folderName,
				success : function(data) {
					if(data && data != null) {
						$("#sendSubject").val("FW: " + data.subject);
						var text = "<p style=\"margin:0;padding:0;font-family: 'times new roman'; font-size: 10pt; word-wrap: break-word;\">";
						text += "	<br/>";
						text += "	<br/>";
						text += "	-----Original Message-----<br/>";
						text += "	From: " + data.from.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "<br/>";
						text += "	Sent: " + data.sentDate + "<br/>";
						text += "	To: " + data.to.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "<br/>";
						if(data.cc !== null && data.cc !== "")
							text += "	Cc: " + data.cc.replace(/</g, "&lt;").replace(/>/g,"&gt;") + "<br/>";
						text += "	Subject: " + data.subject + "<br/>";
						text += "</p>";
						
						nicEditors.findEditor('bodytext').setContent(text + "<br/> " + data.body);
						
						$(".headerAdsrch span").html("Forward Email");
						$("#sendEmail").hide();
						$("#replyEmail").hide();
						$("#forwardEmail").show();
						$('.side_mask').show();
						$('#dynamicmailcompose').show();
					} else {
						alert("error while reading email");
					}
					$("#loading").removeAttr("style");
				}
			});
		}
	}
	
	/* function for sending / forwarding emails */
	function sendEmailFromMailBox(sendType) {
		if ($('#email_form').validate(false, validationSettings)) {
			var sourceleadUrl = '${sourceleadUrl}';
					
			nicEditors.findEditor('bodytext').saveContent();
					
			var form = $('#email_form')[0];
		  	var formData = new FormData(form)
		  		
		  	var toEmails =  $('#emailsList').val();
		  		 
		  	if(toEmails == null) {
		   		alert("Provide atleast one email id");
		   	} else {
		   		var emailAccountId = $("#fromEmail option:selected").data("id");
		   		var from = $("#fromEmail option:selected").val();
				$("#mailLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
				$.ajax({
					type : "POST",
				  	data: formData,
				  	enctype: 'multipart/form-data',
				  	processData: false,
				  	contentType:false, 
				  	url : sourceleadUrl+"/sendEmailFromMailBox?${_csrf.parameterName}=${_csrf.token}&emailAccountId=" + emailAccountId + "&from=" + from + "&sendType=" + sendType,
				  	success : function(result) {
				  		$("#mailLoading").removeAttr('style');
				  	},
				});
			}
			return true;
		} else {
			return false;
		}
	}
</script>
<script type="text/javascript">
	bkLib.onDomLoaded(function() {
	    nicEditors.editors.push(
	    	new nicEditor({fullPanel: true}).panelInstance(document.getElementById('bodytext'))
	    );
	});
	
	function uploadAttachment() {
		$(".file_mask").show();
		
		var data = new FormData();
		var files = document.getElementById("attachfileBtn").files;
		var count = 0;
		expr = /.zip/;
		ecpr1=/.rar/;
		for(var e in files) {
			if(!$.isNumeric(e))
				continue;
			//if(files[e].name.toLowerCase().endsWith(".zip") || files[e].name.toLowerCase().endsWith(".rar"))
			if(files[e].name.toLowerCase().match(expr) || files[e].name.toLowerCase().match(ecpr1))
				continue;
			data.append('file', files[e]);
			count ++;
		}
		if(count === 0) {
			$("#email_form input[data-id*= 'attachfileBtn']").remove();
			$(".file_mask").hide();
			alert("Please select valid file (zip files and rar files are not allowed)");
			return;
		}
		$.ajax({ 
			url: sourceleadUrl + "/ws/upload/Email Documents", 
			data: data, 
			processData: false, 
			type: 'POST', 
			contentType: false, 
			success: function (data) {
				if(data && data.length > 0) {
					for(var e in data) {						
						var fileURL = data[e].fileURL.replace("Email Documents", "temp");
						var text = "<p>" + data[e].fileName + "<i onclick='removeAttachment(this);' class='fa fa-close' style='cursor: pointer;'></i></p>";
						$("#mailAttachmentsDiv").append(text);
					}
					/* applyData(data, "email_form", "fileInfo", "attachfileBtn"); */
					$(".file_mask").hide();
				}
			}, 
			error: function (err) { 
				 $(".file_mask").hide();
			}
		});
	}
	
	function removeAttachment(ele) {
		$(ele).parent().remove();
	}
</script>		
 	
<jsp:include page="masterFooter.jsp" />