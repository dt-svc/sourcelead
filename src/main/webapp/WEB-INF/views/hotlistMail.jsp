<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<link rel="stylesheet" href="/resources/css/bootstrap-multiselect.css?staticTime=${staticTime}" type="text/css">
<link rel="stylesheet" href="/resources/css/bootstrap3-wysihtml5.min.css?staticTime=${staticTime}" type="text/css">


<style>
.modal-backdrop{
display: none;
}
.nicEdit-main {
	overflow: auto !important;
}
.mt40{
 margin-top: 50px !important;
}
.compose-mail .chosen-container-multi .chosen-choices li.search-field input[type="text"] {
	height: 35px; line-height: 35px; width: 500px !important;
}
</style>


<script>
 $(document).ready(function(){
	 $(".clearUpload").hide();
	 var UUID = '${UUID}';
	 if(null != UUID && UUID != '' && UUID != 'undefined'){
		 
		 var mailType = '${mailType}';
		 if(null != mailType && mailType != '' && mailType != 'undefined'){
			 loadHotMailBoxDetails(UUID);
		 }
	 }
	 getSenders(); 
});
</script> 

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Hotlist Mail Box</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='${appPath}/campaigns/hotcampaigns'/>"> Mailbox</a>
			&raquo; Hotlist Campaign</div>
    </section>
    
    
   
    <div class="content-area clearfix">
    <div id="mailLoading"></div>
    <div id ="popUp"></div>
    <div id ="textBoxPopUp"></div>
      <div class="form-horizontal compose-mail col-md-10 col-md-offset-1">
       <form:form id="hotlistCampaign_form" name="hotlistCampaign_form"  >
        <div class="col-md-12 clearfix">
          <label class="col-md-2 control-label text-muted small">From</label>
          <div class="col-md-9 clearfix mailFromRefreBtn">
          <select class="chosen-select form-control" id="senderIdentity" name="senderIdentity" data-validation="required" field-name="Sender">             
			</select>	
		<div class="input-group">
		<span class="input-group-addon cursorPointer bg-blue" onclick="getSenders();"><i class="fa fa-refresh "></i></span>	
		</div>			
          </div>

          <div class="col-md-1">
         <a class="" onclick="campaignSender('','','hotList');"><i class="fa fa-plus btn btn-default pull-right minHeBtnCam"></i></a>
          </div>

        </div>
        <div class="col-md-12 clearfix">
          <label class="col-md-2 control-label text-primary small">To</label>
          <div class="col-md-9 positionRelative disable-choosen-dropdown">
          
<div class="input-group positionRelative col-md-12 noPad" onclick="manuallyEnterHotlistToEmail();">
<select class="form-control"  field-name="To List" multiple data-placeholder="Select Multiple Mails List" id="emailsList"  data-validation="required" name="Toemails"></select>


<!-- 		<span class="refreshicon-new input-group-addon cursorPointer bg-blue" onclick="getRecipientContactsList();"><i class="fa fa-refresh "></i></span>	 -->
		
		
<span class="select-multiple poLeft" data-target="#multiplemail" onclick="getRecipientContactsList();" data-toggle="modal"><i class="fa fa-list-alt" title="Multiple Mail"></i></span> 					 
<!-- <a class="input-group-addon" onclick="campaignRecipient('','','hotList');"><i class="fa fa-plus cursorPointer"></i></a>
<!-- <span class="input-group-addon cursorPointer bg-blue" onclick="getContractTemplates();"><i class="fa fa-refresh "></i></span> -->
</div>
            <!-- Modal content Multiple mail -->
            <div role="dialog" id="multiplemail" class="modal fade">
              <div class="modal-dialog modal-lg">
                <div class="modal-content">
                  <div class="modal-header">
                    <button data-dismiss="modal" class="close" type="button">×</button>
                    <h4 class="modal-title">Select Mailer</h4>
                   <!--  <label class="paddingLeft paddingRight">
               	    <input  type="radio" id="clientsCheck" name="users" checked onclick="listChange()"><strong class="paddingLeft">Clients</strong></label>
               	    <label class="paddingLeft">
               	    <input type="radio" id="contactsCheck" name="users" onclick="getContactsList()"><strong class="paddingLeft">My Contacts</strong></label> -->
                  </div>
                  
                   <div class="modal-body clearfix">
                   <!-- <div class="col-xs-12 col-md-4 col-lg-4 selectpicker100 select-field paddingLeft mb10" id="clientsListDiv" style="display: none">
                          <select class="form-control" id="clientListType" onchange="listChange();">
                           <option value="listbyContact" selected>List by contact</option>
                            <option value="listbyClient">List by client</option>
                          </select>
                    </div>-->
                    <div class="clearfix"></div>
                    <!-- modal-body-custom mailerpopup filter-position --> 
                    <div class="clearfix">
                    <!--  <div id="clientsList" class="datatable_filter"></div> -->
                       <div id="mailBoxVendorsList" class="datatable_filter"></div>
                    </div>
                  </div> 
                  
                </div>
              </div>
            </div>
            <!-- AMultiple mail modal popup ends --> 
          </div>         
         
          <div class="col-md-1">
<a class="" onclick="campaignRecipient('','','hotList');"><i class="fa fa-plus btn btn-default pull-right minHeBtnCam"></i></a>
          </div>
          
        </div>
        <div class="col-md-12 clearfix">
          <label class="col-md-2 control-label text-warning small">Others</label>
          <div class="col-md-10 email-icon positionRelative disable-choosen-dropdown">
                <select multiple data-placeholder="Enter Emails And Press Enter-Key Or Comma(,) Or Space To Add New Email" data-validation="validate_email" data-validation-optional="true" class="chosen-select form-control" name="otherEmails" id="otherEmails" >
                </select>
          </div>
        </div>
        <div class="col-md-12 clearfix">
          <label class="col-md-2 control-label text-danger small">Subject</label>
          <div class="col-md-10" id="campaignSubjectDiv">
            <input type="text" name="subject" data-validation="required" field-name="Subject" id="campaignSubject" class="form-control input-sm" onblur="checkUniqueSubject();">
          </div>
        </div>
        <div class="col-md-12 clearfix">
          <label class="col-md-2 control-label text-success small">Candidates</label>
          <div class="col-md-10 positionRelative disable-choosen-dropdown">
            <select class="chosen-select" name="hotlistcandidates" data-validation="required" field-name="Candidates" multiple data-placeholder="&nbsp;" id="selectedCandidates">
            </select>
            <span class="select-multiple" data-target="#requirement" onclick="candidateListChange()" data-toggle="modal"><i class="fa fa-th-large" title="Select Candidates"></i></span> 
            <!-- Modal content Multiple mail -->
            <div role="dialog" id="requirement" class="modal fade custom-modal">
              <div class="modal-dialog modal-lg">
                <div class="modal-content">
                  <div class="modal-header">
                    <button data-dismiss="modal" class="close" type="button">×</button>
                    <h4 class="modal-title">Select Candidates / Associates</h4>
                  </div>
                  
                  <div class="modal-body clearfix">
<!--                     <div class="form-horizontal custom-form">
                      <div class="row">
                        <div class="col-xs-12 col-md-4 col-lg-4 selectpicker100 select-field">
                          <select class="form-control" id="candidatesListType" onchange="candidateListChange();">
                            <option value="consultants" selected>Associates</option>
                            <option value="candidates">Candidates</option>
                             <option value="openHotlist">Open Hotlist</option>
                            <option value="hotlistConsultants">Hostlist Associates</option>
                          </select>
                        </div>
                      </div>
                    </div> -->
                    
                    <div class="clearfix">
                        <div class="col-xs-12 col-sm-4 paddingLeft dropdown-select mb10">
                          <select class="form-control" id="candidatesListType" onchange="candidateListChange();">
                            <option value="consultants" selected>Associates</option>
                            <option value="candidates">Candidates</option>
                             <option value="openHotlist">Open Hotlist</option>
                           <!-- <option value="hotlistConsultants">Hotlist Associates</option> -->
                          </select>
                        </div>
                        <div id="popupId"></div>                    
                     <div id="candidatesList" class="mailFil datatable_filter"></div>
                     <div id="candidatesLoading"></div>
                    </div>
                     
                  </div> 
                </div>
              </div>
            </div>
            <!-- AMultiple mail modal popup ends --> 
          </div>
        </div>
        <div class="clearfix">
          <div class="col-md-6">
            <label class="col-md-4 control-label text-info small">Salutation</label>
            <div class="col-md-8 saluation field-border">
              <select id="saluation" name="saluation">
                <option value="Hi" selected>Hi</option>
                <option value="Hello">Hello</option>
              </select>
            </div>
          </div>
          <div class="col-md-6">
            <label class="col-md-4 control-label text-info small">Name Fields</label>
            <div class="col-md-8 saluation field-border">
              <select id="namefield" name="namefield">
                <option value="firstname">First Name</option>
                <option value="firstnameandlastName">First Name + Last Name</option>
                <option value="1letterfirstnamelastname">1st Letter in the First Name + Last Name</option>
                <option value="firstname1stletterlastname">First Name + 1st Letter in the Last Name </option>
                <option value="1letterfirstname1letterlastname">1st Letter in the First Name & Last Name</option>
              </select>
            </div>
          </div>
          
        </div>
        
        <!-- Text Area -->
        <div class="col-md-12 clearfix mb10 mt10">
          <div class="col-md-12">
            <div class="clearfix txt-area">
              <textarea id="bodytext" name="bodytext" class="form-control" rows="5" placeholder="Message" style="height: 250px">
                    
			  </textarea>
            </div>
          </div>
        </div>
        <!-- Text Area Ends --> 
        
        <!-- Attachment button-->
       <!--  <div class="col-md-12 clearfix mb10">
          <div class="col-md-12 attachment">
            <div class="input-group">
              <label class="input-group-btn"> <b class="btn btn-primary"><i class="fa fa-paperclip"></i>
                <input type="file" name="attachfile" style="display: none;"  id="attachfileBtn" class="upload">
                </b> </label>
              <input type="text" class="form-control" readonly id="attachfile-mail" placeholder="Files are not selected...">
              <div class="clearUpload"></div>
            </div>
          </div>
        </div> -->
        <!-- Attachment button --> 
        
        <!-- Attachment button-->
        <div class="col-md-12 clearfix mt">
            <div class="pull-right"> 
              <!--<input type="button" value="Schedule" class="btn btn-sm btn-primary">-->
              <button type="button" class="btn btn-sm btn-success" onclick="sendHotListCampaign();"><i class="fa fa-envelope-o"></i>&nbsp;Send</button>
              <button type="button" class="btn btn-sm btn-default" onClick="javascript:window.location.href = '<c:url value='${appPath}/campaigns/hotcampaigns'/>'" ><i class="fa fa-times"></i>&nbsp;Cancel</button>
            </div>
        </div>
        <!-- Attachment button --> 
         </form:form>
      </div>
      
      
    </div>
   
    
    
  </section>
  
<!--   
  <script type="text/javascript" src="/resources/js/bootstrap-multiselect.js"></script>
  <script src="/resources/js/bootstrap3-wysihtml5.all.min.js"></script>  -->
  
  
  <script src="/resources/js/nicEdit.js"></script> 
  
  <script>
  $(function() { 
		tagIt("otherEmails", 'Enter Emails And Press Enter-Key Or Comma(,) Or Space To Add New Email', 'tagItBykeyForEmails','emails');
		tagIt("emailsList", 'Enter Emails And Press Enter-Key Or Comma(,) Or Space To Add New Email', 'tagItBykeyForEmails','emails');
		
	});
  </script>
  <script>
  function selectRadio(){
	  document.getElementById("clientsCheck").checked = true;
	  document.getElementById("contactsCheck").checked = false;
  }
  </script>
   

<script type="text/javascript">
$(function(){
	       var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
	       
	       
	    
	       
	       $('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
		  })
});

$(function() {
	$("#attachfileBtn").change(function() {
		fileUpload("attachfileBtn", "Hotlist Campaign Documents", "hotlistCampaign_form");
		$(".clearUpload").show();
	});
 });
 
</script>

  
  
  <script type="text/javascript">
  function manuallyEnterHotlistToEmail(){
	  $('#emailsList_chosen ul li input').attr('readonly', true);
  }
  
  
	function sendHotListCampaign() {
		if ($('#hotlistCampaign_form').validate(false, validationSettings)){ 
		nicEditors.findEditor('bodytext').saveContent();
  		
		 var form = $('#hotlistCampaign_form')[0];
  		 var formData = new FormData(form)
	  		
		
  		/* var toEmails =  $('#emailsList_chosen span').size();
  		var otherEmails =  $('#otherEmails_chosen span').size();
  		
  		var selectedEmails = toEmails + otherEmails; */
  		
  		 var toEmails =  $('#emailsList').val();
  		 var otherEmails =  $('#otherEmails').val();
  		 
  		
  	    if(toEmails == null && otherEmails == null){
  	    	
  	    	alertMailerMsg();
  	    }
  	    else{
	  	    	
	  	    	$("#mailLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  			$.ajax({
	  				type : "POST",
	  	    	    data: formData,
	  	    	    enctype: 'multipart/form-data',
	  		        processData: false,
	  		        contentType:false, 
	  				url : sourceleadUrl+"/sendHotListCampaign?${_csrf.parameterName}=${_csrf.token}",
	  				success : function(result) {
	  						location.replace(sourceleadUrl+"/campaigns/hotcampaigns");
	  				},
	  			}); 
	  	    
  	    }
		 return true
		 
		}else{
			return false;
		  }
		
	}
	
	$('body').on('blur', '#hotlistCampaign_form', function() {
		$("#hotlistCampaign_form").showHelpOnFocus();
		$("#hotlistCampaign_form").validateOnBlur(false, validationSettings);  
   });
</script>

<script type="text/javascript">
function alertMailerMsg(){
	
	var result = "";
	result = result +"<div class='project_inn_mask'></div>";
	result = result +"<div id='mailerPopup' class='remove_user_pop remove_user_pop_a'>";
	result = result +"<p><strong>Provide at least one mail In To or Others</strong></p>";
	result = result +"<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"<b href='javascript:void(0)'><input type='button'  value='Ok' onclick='cancel();' style='background-color:#1ab394;width:100%'/></b>";
	result = result +"</div>";
	$("#popUp").append(result);
	$("#mailerPopup").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>


<script type="text/javascript">
function cancel(){
	$("#mailerPopup").slideUp(200);
	$(".project_inn_mask").hide();
}
</script>


<script type="text/javascript">
function alertTextboxMsg(msgText){
	
	var result = "";
	result = result +"<div class='project_inn_mask'></div>";
	result = result +"<div id='textBoxmailerPopup' class='remove_user_pop remove_user_pop_a'>";
	result = result +"<p><strong>"+msgText+"</strong></p>";
	result = result +"<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"<b href='javascript:void(0)'><input type='button'  value='Ok' onclick='textBoxCancel();' style='background-color:#1ab394;width:100%'/></b>";
	result = result +"</div>";
	$("#textBoxPopUp").append(result);
	$("#textBoxmailerPopup").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>


<script type="text/javascript">
function textBoxCancel(){
	$("#textBoxmailerPopup").slideUp(200);
	$(".project_inn_mask").hide();
}
</script>



<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {

			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};
	})(jQuery);
</script>

<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };

        
</script> 
  
  
  
  <script>
  function listChange(){
	  var listType = document.getElementById("clientListType").value;
	  if(listType == 'listbyContact'){
		  $("#clientsList").empty(); 
		  getClientContacts();
	  }else{
		  $("#clientsList").empty(); 
		  getClientList();
	  }
	  
	  
	  document.getElementById("clientsListDiv").style.display="";
	 
  }
  </script>
  
  
  
  
<script>
function getClientContacts(){ 
	  $("#clientsList").empty();
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getClientContacts?${_csrf.parameterName}=${_csrf.token}",    
            success :function(data) {
            	
                 if(data != null && data != 'undefined' && data != "" && data != '[]'){
               		
	                	   
               			    var result = '';
	                		result = result +'     <div class="modal-bodyCustom clearfix popupFilter hdrAlign">'; 
	                	    result = result +'       <table id="example" class="table table-bordered table-striped  clearfix" cellspacing="0" width="100%" >';
	                		result = result +'           <thead>';
	                		result = result +'           <tr>';
	                		result = result +'             <th><input type="checkbox" id="checkAllContacts" onclick="toggleClientContacts(this);"></th>';
	                		result = result +'             <th>Contact Name</th>';
	                		result = result +'             <th>Email</th>';
	                		result = result +'             <th>Phone</th>';
	                		result = result +'             <th>Client Name</th>';
	                		result = result +'             <th>Status</th>';
	                		result = result +'           </tr>';
	                		result = result +'           </thead>';
		                    result = result +"<tbody>"; 
		                    var json = JSON.parse(data);
		                    for (var i = 0; i < json.length; i++) { 
		                    	 var opt = json[i]; 
		                    	 var mobilephone = '';
		                    	 
		                    	 if(opt.spocMobilePhone != null && opt.spocMobilePhone != 'undefined' && opt.spocMobilePhone != ""){
		                    		 mobilephone = opt.spocMobilePhone;
		                    	 }else{
		                    		 mobilephone = "&nbsp;&nbsp;-----------";
		                    	 }
		                    	 
		                    	 
		                    	 result = result +'<tr>';
		                    	 result = result +'             <td>&nbsp;&nbsp;<input id="'+opt.id+'" value="'+opt.spocEmail+'"  type="checkbox" name="emails[]" class="myCheckbox" ></td>';
		                    	 result = result +'             <td>'+opt.spocName+'</td>';
		                    	 result = result +'             <td>'+opt.spocEmail+'</td>';
		                    	 result = result +'             <td>'+mobilephone+'</td>';
		                    	 result = result +'             <td>'+opt.orgName+'</td>';
		                    	 result = result +'             <td>'+opt.spocStatus+'</td>';
		                    	 result = result +'           </tr>';
		                    	
	                     }
		                    
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     result = result +'     </div>'; 
	                 	 result = result +'     <div class="clearfix">';
	                 	
	                 	 result = result +'       </div>'; 
	                 	 result = result +'   </div>'; 
	                 	 result = result +'   <div class="clearfix">';
	                 	result = result +'   <div class="pull-right mr">';
	                 	 result = result +'     <button data-dismiss="modal" class="btn btn-primary" type="button" onClick="getEmails();">Assign</button>';
	                 	 result = result +'     <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>';
	                 	 result = result +'   </div>'; 
	                 	result = result +'   </div>';
	                     $("#clientsList").append(result); 
	                     pagination(); 
                 }else{
  	    	         
                	var result ="";
	    	        result = result +'<div class="modal-body clearfix">';
	           		result = result +'     </div>';
	           		result = result +'     <div class="modal-bodyCustom mt40 clearfix popupFilter">'; 
	           	    result = result +'       <table id="example" class="table table-bordered table-striped  clearfix" cellspacing="0" width="100%" >';
	           		result = result +'           <thead>';
	           		result = result +'           <tr>';
	           		result = result +'             <th><input type="checkbox" id="checkAll" onclick="toggle(this);"></th>';
	           		result = result +'             <th>Contact Name</th>';
	           		result = result +'             <th>Email</th>';
	           		result = result +'             <th>Phone</th>';
	           		result = result +'             <th>Client Name</th>';
	           		result = result +'             <th>Status</th>';
	           		result = result +'           </tr>';
	           		result = result +'           </thead>';
	        		result = result +'           </table>';
	        		result = result +'     </div>';
	        		result = result +'     </div>';
				    $("#clientsList").append(result); 
				    pagination();  
                }
          },
            
       }); 
  } 
</script>


  <script>
function getClientList(){ 
	 $("#clientsList").empty();
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getClientsListForHotlist?${_csrf.parameterName}=${_csrf.token}",    
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
	                	    result = result +'<div class="clearfix">';
	                		result = result +'     </div>';
	                		result = result +'     <div class="clearfix">'; 
	                	    result = result +'       <table id="example" class="table table-bordered table-striped  clearfix" cellspacing="0" width="100%" >';
	                		result = result +'           <thead>';
	                		result = result +'           <tr>';
	                		result = result +'             <th><input type="checkbox" id="checkAllClients" onclick="toggleClients(this);"></th>';
	                		result = result +'             <th>Client Name</th>';
	                		result = result +'             <th>Website</th>';
	                		result = result +'             <th>Location</th>';
	                		result = result +'             <th>Tier</th>';
	                		result = result +'             <th>No:of Contacts</th>';
	                		result = result +'           </tr>';
	                		result = result +'           </thead>';
		                    result = result +"<tbody>"; 
		                    var json = JSON.parse(data);
		                    for (var i = 0; i < json.length; i++) { 
		                    	 var opt = json[i]; 
		                    	 var location = '';
		                    	 var website = ''
		                    	 
		                    	 if(opt.state != null && opt.state != 'undefined' && opt.state != ""){
		                    		 location = opt.country+", "+opt.state ;
		                    	 }else{
		                    		 location = opt.country;
		                    	 }
		                    	 
		                    	 if(opt.webSite != null && opt.webSite != 'undefined' && opt.webSite != ""){
		                    		 website = opt.webSite ;
		                    	 }else{
		                    		 website = "&nbsp;&nbsp;-----------";
		                    	 }
		                    	 
		                    	 result = result +'<tr>';
		                    	 result = result +'             <td>&nbsp;&nbsp;<input id="'+opt.id+'" value="'+opt.id+'"  type="checkbox" name="orgRelationIds[]" class="myCheckbox" ></td>';
		                    	 result = result +'             <td>'+opt.orgName+'</td>';
		                    	 result = result +'             <td>'+website+'</td>';
		                    	 result = result +'             <td>'+location+'</td>';
		                    	 result = result +'             <td>'+opt.level+'</td>';
		                    	 result = result +'             <td>'+opt.noOfContatcsForCampaign+'</td>';
		                    	 result = result +'           </tr>';
		                    	
	                     }
		                    
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     result = result +'     </div>'; 
	                 	 result = result +'     <div class="clearfix">';
	                 	
	                 	 result = result +'       </div>'; 
	                 	 result = result +'   </div>'; 
	                 	result = result +'   <div class="clearfix">';
	                 	result = result +'   <div class="pull-right mr">';
	                 	 result = result +'     <button data-dismiss="modal" class="btn btn-primary" type="button" onClick="getContactEmails();">Assign</button>';
	                 	 result = result +'     <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>';
	                 	 result = result +'   </div>'; 
	                 	result = result +'   </div>';
	                     $("#clientsList").append(result); 
	                     pagination(); 
			    	} 
                 }else{
  	    	         
                 	var result ="";
 	    	        result = result +'<div class="modal-body clearfix">';
 	           		result = result +'     </div>';
 	           		result = result +'     <div class="modal-bodyCustom mt40 clearfix popupFilter">'; 
 	           	    result = result +'       <table id="example" class="table table-bordered table-striped  clearfix" cellspacing="0" width="100%" >';
 	           		result = result +'           <thead>';
 	           		result = result +'           <tr>';
 	           		result = result +'             <th><input type="checkbox" id="checkAll" onclick="toggle(this);"></th>';
	 	           	result = result +'             <th>Client Name</th>';
	        		result = result +'             <th>Website</th>';
	        		result = result +'             <th>Location</th>';
	        		result = result +'             <th>Tier</th>';
	        		result = result +'             <th>No:of Contacts</th>';
 	           		result = result +'           </tr>';
 	           		result = result +'           </thead>';
 	        		result = result +'           </table>';
 	        		result = result +'     </div>';
 	        		result = result +'     </div>';
 				    $("#clientsList").append(result); 
 				    pagination();  
                 }
          },
            
       }); 
  } 
</script>


<script>
function getContactsList(){
	
	document.getElementById("clientsListDiv").style.display="none";
	$("#clientsList").empty();
	
	    var result = "";
	 	result = result +" <div class='remove_search'>";
	 	result = result +" <input type='text' placeholder='Search' id='contactsDataTableSearch'/>";
	 	result = result +"</div>";
	 	
	 	
	 	result = result +'       <div class="col-xs-12 col-md-4 paddingLeft selectpicker100 mb10 mt">';
 		result = result +'     <select id="groupsFilter" name="groupsFilter" class="selectpicker" multiple data-actions-box="true" onchange="contactsDataTableList();" style="display:block !important" title="--Select Group--">';
		<c:forEach var="conGroup" items="${contactsGroupList}">
		result = result +' <option value="${conGroup.id}">${conGroup.groupName}</option>';
        </c:forEach>
 		result = result +'          </select>';
 		result = result +'       </div>';
	 	
	    result = result +'<div class="clearfix popupFilter">';
  		result = result +'<div class="mt clearfix popupFilter"><br/><br/>'; 
	 	
  		result = result +'<table id="example" class="display table table-stripped table-bordered" cellspacing="0" width="100%"  >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +' <th><input type="checkbox" id="checkAllUsers" onclick="toggleClientContacts(this);"></th>';
		result = result +'<th>Contact Name</th>';
		result = result +'<th>Email</th>';
		result = result +'<th>Group</th>';
		result = result +'<th>Source</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    
	    result = result +'</div>'; 
    	result = result +'<div class="clearfix">';
    	result = result +'</div>'; 
    	result = result +'</div>';
    	
    	result = result +'<div class="clearfix pull-right mr mb">';
     	result = result +'<button class="btn btn-primary"  type="button" data-dismiss="modal" id="assignbtnId" onclick="getAllEmailContacts();">Add</button>';
     	result = result +' <button data-dismiss="modal" class="btn btn-default" type="button">Close</button>';
     	result = result +' </div>'; 
	    
     	$("#clientsList").append(result);
     	contactUsers = [];
	    contactsDataTableList();
	    $("#groupsFilter").selectpicker("refresh");
}
</script>

<script>
function contactsDataTableList(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#contactsDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 
	 var contactsGroups = '';
	 if($("#groupsFilter").val() != null){
		 contactsGroups = $("#groupsFilter").val();
	 }
	 
	 var url = sourceleadUrl+"/getUserContactsListForMalling?searchValue="+searchValue+"&groupIdsList="+contactsGroups;
	 contactsPagination(url);
}
</script>



<script>
function contactsPagination(url,sourceleadUrl){
	var colIndex = 0;
    $('#example').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'GET',
          pages: 2
        }),
        "columns": [
        	{ "data": "ischeck" },
            { "data": "contactName" },
            { "data": "email"},
            { "data": "groups" },
            { "data": "source"}
           
        ],
        "columnDefs": [ {
    	  	"targets": colIndex++,
    	  	"data": "ischeck",
    	  	"render": function(data, type, opt, meta) {
    	  		var result = "";
    	  		var flag = false;
   	  		 
	   	  		  for(var e in contactUsers) {
	   	  			 if(opt.email+"" === contactUsers[e]) {
	   	  				 flag = true;
	   	  				 break;
	   	  			 }
	   	  		 }
   	  		 
   	  		  result = result +'<td>&nbsp;&nbsp;<input'+(flag ? " checked='checked'" : "")+' id="'+opt.id+'" value="'+opt.email+'" onchange="checkContactUsers(this)"  type="checkbox" name="emails[]"  class="myCheckbox" ></td>';
	              return result;
    	  	}
         },{
	    	  	"targets": colIndex++,
	    	  	"data": "contactName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		  result = result + '<td data-title="Id"><div class="tblminwidth-75"  title="'+opt.fullName+'">'+opt.fullName+'</div></td>';
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "email",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  result = result + '<td data-title="Id"><div class="tblminwidth-75" title="'+opt.email+'">'+opt.email+'</div></td>';
	              return result;
              		
              }
	      
	          },{
		    	  "targets": colIndex++,
	              "data": "groups",
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  if(opt.groupsList != null){
	            		  result = result + '<td data-title="Id"><div class="tbldata-dotdot" title="'+opt.groupsList+'">'+opt.groupsList+'</div></td>';
	            	  }else{
	            		  result = result + '<td data-title="Id"><div>----</div></td>';
	            	  }
	            	  
		              return result;
	              		
	              }
	          },{
		    	  "targets": colIndex++,
	              "data": "source",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            		result = result + '<td><div class="tblminwidth-75"><span title="'+opt.source+'">'+opt.source+'</span></div></td>';
	            		return result;
	              }
		     }],
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": false,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#example").wrap("<div class='modal-body-custom modalAlign mailerpopup' style='overflow-x: auto;'></div>"); 
    $("input[type=search]").hide();
    $("#contactsDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   contactsDataTableList();
    	   }
    	});
}

</script>

<script>
function pagination(){
    $("#example").DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 10, 15, -1], [10, 10, 15, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"aaSorting": [[1,'desc']],
    	"ordering": true,
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 0
			}],
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $("#example").wrap("<div class='modal-body-custom mailerpopup modalAlign'></div>");
}
</script>





<script>
function getCandidates(candidatesListType){
	
	  var sourceleadUrl ='${sourceleadUrl}';
	  
	  $("#candidatesLoading").attr('style','position:absolute; width:98%; height:49%; background-color:rgba(255,255,255,0.8); top:140px; left:17px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getCandidatesForCampaign?${_csrf.parameterName}=${_csrf.token}&candidatesListType="+candidatesListType,    
            success :function(data) {
            	
                 if(data != null && data != 'undefined' && data != "" && data != '[]'){
	                	   
               			    var result = '';
	                		result = result +'     <div class="modal-bodyCustom clearfix popupFilter hdrAlign">'; 
	                	    result = result +'       <table id="candidates" class="table table-bordered table-striped  clearfix" cellspacing="0" width="100%" >';
	                		result = result +'           <thead>';
	                		result = result +'           <tr>';
	                		result = result +'             <th><input type="checkbox" id="checkAllCandidates" onclick="toggleCandidates(this);"></th>';
	                		result = result +'             <th>Name</th>';
	                		result = result +'             <th>Designation</th>';
	                		result = result +'             <th>Email</th>';
	                		result = result +'             <th>Phone</th>';
	                		if(candidatesListType == 'openHotlist'){
	                			result = result +'<th>Type</th>';
	                		}
	                		result = result +'             <th>Employment Type</th>';
	                		result = result +'           </tr>';
	                		result = result +'           </thead>';
		                    result = result +"<tbody>"; 
		                    var json = JSON.parse(data);
		                    for (var i = 0; i < json.length; i++) { 
		                    	 var opt = json[i]; 
		                    	 var name = '';
		                    	 var mobile = ''
		                    	 var designation = '';
		                    	 var designationType = '';
		                    	 
		                    	 if(opt.lastName != null && opt.lastName != 'undefined' && opt.lastName != ""){
		                    		 name = opt.firstName+" "+opt.lastName ;
		                    	 }else{
		                    		 name = opt.firstName;
		                    	 }
		                    	 
		                    	 if(opt.mobilePhone != null && opt.mobilePhone != 'undefined' && opt.mobilePhone != ""){
		                    		 mobile = opt.mobilePhone ;
		                    	 }else{
		                    		 mobile = "&nbsp;&nbsp;-----------";
		                    	 }
		                    	 
		                    	 if(opt.designation != null && opt.designation != 'undefined' && opt.designation != ""){
		                    		 designation = opt.designation ;
		                    	 }else{
		                    		 designation = "&nbsp;&nbsp;-----------";
		                    	 }
		                    	 
		                    	 if(opt.designationType != null && opt.designationType != 'undefined' && opt.designationType != ""){
		                    		 designationType = opt.designationType ;
		                    	 }else{
		                    		 designationType = "&nbsp;&nbsp;-----------";
		                    	 }
		                    	
		                    	 
		                    	 result = result +'<tr>';
		                    	 result = result +'<td>&nbsp;&nbsp;<input id="'+opt.candidateId+'" value="'+opt.candidateId+','+name+'"  type="checkbox" name="candidatesIds[]" class="myCheckbox" >';
		                    	 result = result +'</td>';
		                    	 result = result +'<td>'+name+'</td>';
		                    	 result = result +'<td>'+designation+'</td>';
		                    	 result = result +'<td>'+opt.email+'</td>';
		                    	 result = result +'<td>'+mobile+'</td>';
		                    	 if(candidatesListType == 'openHotlist'){
		                    		 if(opt.isAssociate == 'Y'){
		                    			 result = result +'<td>Associate</td>'; 
		                    		 }else{
		                    			 result = result +'<td>Candidate</td>'; 
		                    		 }
		                    		 
		                    	 }
		                    	 result = result +'<td>'+designationType+'</td>';
		                    	 result = result +'</tr>';
		                    	
	                     }
		                    
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     result = result +'     </div>'; 
	                 	 result = result +'     <div class="clearfix">';
	                 	
	                 	 result = result +'       </div>'; 
	                 	 result = result +'   </div>'; 
	                 	 result = result +'   <div class="clearfix">';
	                 	 result = result +'   <div class="pull-right mr">';
	                 	 result = result +'     <button class="btn btn-primary" type="button" onClick="getCheckedCandidates();">Assign</button>';
	                 	 result = result +'     <button id="closePopUp" class="btn btn-default" onClick="removeMessage();" type="button">Cancel</button>';
	                 	 result = result +'   </div>'; 
	                 	 result = result +'   </div>'; 
	                 	 $("#candidatesList").empty(); 
	                     $("#candidatesList").append(result); 
	                     candidatePagenation(); 
	                     $("#candidatesLoading").removeAttr("style");
                 }else{
  	    	         
                 	var result ="";
 	           		result = result +'     <div class="modal-bodyCustom clearfix popupFilter hdrAlign">'; 
 	           	    result = result +'       <table id="candidates" class="table table-bordered table-striped  clearfix" cellspacing="0" width="100%" >';
 	           		result = result +'           <thead>';
 	           		result = result +'           <tr>';
 	           		result = result +'             <th><input type="checkbox" id="checkAll" onclick="toggle(this);"></th>';
	 	           	result = result +'             <th>Name</th>';
	        		result = result +'             <th>Email</th>';
	        		result = result +'             <th>Phone</th>';
	        		result = result +'             <th>Designation</th>';
	        		if(candidatesListType == 'openHotlist'){
            			result = result +'<th>Type</th>';
            		}
	        		result = result +'             <th>Employment Type</th>';
 	           		result = result +'           </tr>';
 	           		result = result +'           </thead>';
 	        		result = result +'           </table>';
 	        		result = result +'     </div>';
 	        		result = result +'     </div>';
 					$("#candidatesList").empty();
 				    $("#candidatesList").append(result); 
 				    $("#candidatesLoading").removeAttr("style");
 				    candidatePagenation(); 
 				    
                 } 
          },
            
       }); 
  } 
</script>
<script>
	function removeMessage() {
		$('#popupId').parent().find('p.jquery_form_error_message').remove();
		$("#requirement").modal('hide');
	}
</script>



<script>
function candidatePagenation(){
    $("#candidates").DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 10, 15, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"aaSorting": [[1,'desc']],
    	"ordering": true,
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 0
			}],
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $("#candidates").wrap("<div class='modal-body-custom mailerpopup modalAlign'></div>");
}
</script>


 <script>
function toggleClientContacts(source) {
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i] != source)
            checkboxes[i].checked = source.checked;
            checkContactUsers(checkboxes[i])
        
    }
$('input:checkbox:not(#checkAllContacts)').click(function() {
    if(!this.checked) {
        $('#checkAllContacts').removeAttr('checked');
    }
});
}
</script>

<script>
var contactUsers = [];
function checkContactUsers(ele) {
	if($(ele).is(":checked")) {
		contactUsers[contactUsers.length] = $(ele).val();
	}
}
</script>

 <script>
function toggleClients(source) {
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i] != source)
            checkboxes[i].checked = source.checked;
        
    }
$('input:checkbox:not(#checkAllClients)').click(function() {
    if(!this.checked) {
        $('#checkAllClients').removeAttr('checked');
    }
});
}
</script>

 <script>
function toggleCandidates(source) {
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i] != source)
            checkboxes[i].checked = source.checked;
        
    }
$('input:checkbox:not(#checkAllCandidates)').click(function() {
    if(!this.checked) {
        $('#checkAllCandidates').removeAttr('checked');
    }
});
}
</script>



<script>
function getEmails(){
	
	var oTable = $('#example').dataTable();
	var rowcollection =  oTable.$("input[name='emails[]']:checked", {"page": "all"});
	
	
	var internalOutput = "";
	rowcollection.each(function(index,elem){
		 var checkbox_value = $(elem).val();
		 internalOutput = internalOutput+"<option value='" +checkbox_value+ "' selected>"+checkbox_value+"</option>";
	});
	 
	    $("#emailsList").append(internalOutput);
		$("#emailsList").trigger("chosen:updated");
		
		$("input[name='emails[]']:checked").removeAttr('checked');
		$('#checkAllContacts').removeAttr('checked');
		
}
</script>


<script>
function getAllEmailContacts(){
	var internalOutput = "";
	
	for(var e in contactUsers) {
		if(contactUsers[e] == 'Email' || contactUsers[e] == 'Notification' || contactUsers[e] == 'on')
			continue;
		internalOutput = internalOutput+"<option value='" +contactUsers[e]+ "' selected>"+contactUsers[e]+"</option>";
	}
	
	    $("#emailsList").append(internalOutput);
		$("#emailsList").trigger("chosen:updated");
		
		$("input[name='contactids[]']:checked").removeAttr('checked');
		$('#checkAllUsers').removeAttr('checked');
		
		
}
</script>

<script>
function getContactEmails(){
	
	var orgRelationIds = [];
	var internalOutput = "";
	
	 $("input[name='orgRelationIds[]']:checked").each( function (i) {
		 orgRelationIds[i] = $(this).val();
	});
	 
	 var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
          type : "POST",
          url: sourceleadUrl+"/getClientContactsEmailsByOrgRelationId?${_csrf.parameterName}=${_csrf.token}&orgRelationIds="+orgRelationIds,    
           success :function(data) {
        	    var str_array = data.replace(/\"/g, "").split(',');
				for(var i = 0; i < str_array.length; i++) {
				   str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
				   internalOutput = internalOutput+"<option value='" +str_array[i]+ "' selected>"+str_array[i]+"</option>";
				}
				
				 $("#emailsList").append(internalOutput);
				 $("#emailsList").trigger("chosen:updated");
				 
				 $("input[name='orgRelationIds[]']:checked").removeAttr('checked');
				 $('#checkAllClients').removeAttr('checked');
				
           },
           
      }); 
	 
}

</script>


<script>
function loadHotMailBoxDetails(UUID){
	var sourceleadUrl ='${sourceleadUrl}';
	
	$.ajax({
        type : "POST",
        url: sourceleadUrl+"/getHotListedCandidateDetailsByUUID?${_csrf.parameterName}=${_csrf.token}&UUID="+UUID,    
         success :function(data) {
        	 
        	 if(data != null && data != 'undefined' && data != "" && data != '[]'){
        		 var json = JSON.parse(data);
        		 var internalOutput = "";
        		 for (var i = 0; i < json.length; i++) { 
        			 var opt = json[i];
        			 internalOutput = internalOutput+"<option value='" +opt.id+ "' selected>"+opt.email+"</option>";
        		 }
        		 
       		     $("#selectedCandidates").append(internalOutput);
       			 $("#selectedCandidates").trigger("chosen:updated");
       			
        	 }
        	 
         }
	});
}
</script>




<script>
function getCheckedCandidates(){
	
	$('#popupId').parent().find('p.jquery_form_error_message').remove();
	var oTable = $('#candidates').dataTable();
	var rowcollection =  oTable.$("input[name='candidatesIds[]']:checked", {"page": "all"});
	
	
	var internalOutput = "";
	rowcollection.each(function(index,elem){
		 var checkbox_value = $(elem).val().split(",");
		 internalOutput = internalOutput+"<option value='" +checkbox_value[0]+ "' selected>"+checkbox_value[1]+"</option>";
	});
	    if($("input[name='candidatesIds[]']:checked").length == 0){
	    	$("#popupId").after("<p class='jquery_form_error_message'><span>Select Atleast One Candidate/Associates</span>");
	    }else{
	    	$("#requirement").modal('hide');
	    	$('#popupId').parent().find('p.jquery_form_error_message').remove();
	    }
	    	$("#selectedCandidates").append(internalOutput);
			$("#selectedCandidates").trigger("chosen:updated");
			
			$("input[name='candidatesIds[]']:checked").removeAttr('checked');
			$('#checkAllCandidates').removeAttr('checked'); 
	    
	    
		
}
</script>




  
  <script type="text/javascript">
	$(document).ready(function() {
		$('#example-getting-started, #namefields').multiselect({
		includeSelectAllOption: true
		});
	});
</script> 

<script>
function candidateListChange(){
	
	
	  var candidatesListType = document.getElementById("candidatesListType").value;
	  $("#candidatesList").empty(); 
	  getCandidates(candidatesListType);
}

</script>



  <script>
//upload file script -laptop-shiftA
document.getElementById("attachfileBtn").onchange = function () {
	$('#attachfile-mail').val($(this).val().replace(/C:\\fakepath\\/i, ''))
};
</script> 
  


<script type="text/javascript">
bkLib.onDomLoaded(function() {
    nicEditors.editors.push(
        new nicEditor().panelInstance(
            document.getElementById('bodytext')
        )
    );
});
</script>

<script>
function getSenders(){
		$.ajax({
		type : "GET",
		url: sourceleadUrl+"/getSenders?"+tokenParamName+"="+tokenValue,
		success :function(json) {
		var output = "";
		output = output + "<option value=''>Select Sender</option>";
		for (var i=0; i<json.length; i++)
		{
		var opt = json[i];
		output = output + "<option value='" +opt.senderId+ "'>"+opt.cpnSenderFromMail+"("+opt.cpnSenderName+")</option>";
		
		}
		
		$("#senderIdentity").empty();
		$("#senderIdentity").append(output);
		$('#senderIdentity').trigger("chosen:updated");
		},
		});
}
</script>

<script>
function getRecipient(){
	    var typeOfCampaign = "0";
		$.ajax({
		type : "GET",
		url: sourceleadUrl+"/getRecipientList?"+tokenParamName+"="+tokenValue+"&typeOfCampaign="+typeOfCampaign,
		success :function(json) {
		var output = "";
		output = output + "<option value=''>Select Recipient</option>";
		for (var i=0; i<json.length; i++)
		{
		var opt = json[i];
		output = output + "<option value='" +opt.listId+ "'>"+opt.recipientEmail+"("+opt.listName+")</option>";
		
		}
		
		$("#emailsList").empty();
		$("#emailsList").append(output);
		$('#emailsList').trigger("chosen:updated");
		},
		});
}
</script>

 <!-- Added by rajashekar -->
 <script>
function getRecipientContactsList(){
	    var result = "";
	 	result = result +" <div class='dynamic_search dynamic_search-new'>";
	 	result = result +" <input type='text' placeholder='Search' id='vendorContactsDataTableSearch'/>";
	 	result = result +"  <span class='cursorPointer' title='Search'><i class='fa fa-search' onclick='vendorContactDataTableList();'></i></span>";     

	 	result = result +"</div>";
	 	
	    result = result +'<div class="clearfix popupFilter">';
  		result = result +'<div class="mt clearfix popupFilter"><br/><br/>'; 
	 	
  		result = result +'<table id="vendorconDataTable"   class="display table table-stripped table-bordered" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		 result = result +' <th><input type="checkbox" id="checkAllRecipantsntacts" onclick="toggleRecipantsContacts(this);"></th>'; 
		result = result +'  <th>Contact List</th>';
	   	result = result +'	<th>Contacts Count</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    result = result +'</div>'; 
    	result = result +'<div class="clearfix">';
    	result = result +'</div>'; 
    	result = result +'</div>';
    	result = result +'<div class="clearfix pull-right mr mb">';
     	result = result +'<button class="btn btn-primary"  type="button" data-dismiss="modal" id="vendorContactbtnId" onclick="getRecipantsContacts();">Add</button>';
     	result = result +' <button data-dismiss="modal" class="btn btn-default" type="button">Close</button>';
     	result = result +' </div>'; 
	    $("#mailBoxVendorsList").empty();
	    $("#mailBoxVendorsList").append(result);
	    recepiantscontactsList = {}
	    vendorContactDataTableList1();
	    
	   
}
</script>

<script>
function vendorContactDataTableList1(){
	 var searchValue = $("#vendorContactsDataTableSearch").val();
	 var responderType ="JobCampaignVendor";
	 searchValue = searchValue ? searchValue : "";
	 var typeOfCampaign = "0";
	 var url = sourceleadUrl+"/getRecipientList?searchValue="+searchValue+"&responderType="+responderType+"&typeOfCampaign="+typeOfCampaign;
	 vendorContactpagination1(url);
}
</script>

<script>
var recepiantscontactsList = {};
function checkRecepiantsContacts(ele){
	if($(ele).is(":checked")) {
		recepiantscontactsList[$(ele).data("id")] = $(ele).val();
	}
}
</script>

<script>
function toggleRecipantsContacts(source) {

if($(source).is(":checked"))
	  $('input[class="myRecipantsContactsCheckbox"]').prop('checked', 'checked');
else 
	  $('input[class="myRecipantsContactsCheckbox"]').removeProp('checked');

$('input[class="myRecipantsContactsCheckbox"]').each(function(i) {
	  if($(this).is(":checked")) {
		  recepiantscontactsList[$(this).data("id")] = $(this).val();
	  } else {
		  recepiantscontactsList[$(this).data("id")] = "";
	  }
});

}
</script>

<script>
	function vendorContactpagination1(url){
		var colIndex = 0;
		 $('#vendorconDataTable').DataTable( { 
	    	"processing": true,
	        "serverSide": true,
	        "searching": false,
	        "ajax": $.fn.dataTable.pipeline( {
	            url: url,
	            method: 'GET',
	            pages: 10
	        }),
	        "columns": [
	        	{ "data": "" },
	            { "data": "listName" },
	            { "data": "listCount"},
	        ],
	        "columnDefs": [ {
		    	  	"targets": colIndex++,
		    	  	"data": "ischeck",
		    	  	"render": function(data, type, report, meta) {
		    	  		 var result = "";
		    	  		 var flag = false;
		    	  		
		    	  	   for(var e in recepiantscontactsList) {
		    	  		 if(report.listId+"" === recepiantscontactsList[e].split(",")[0]) {
		    	  				 flag = true;
		    	  				 break;
		    	  			 }
		    	  		 }  
		    	  		  result = result +'<td>&nbsp;&nbsp;<input'+(flag ? " checked='checked'" : "")+' id="'+report.listId+'" data-id="'+report.listId+'" value="'+report.listId+','+report.listName+'" value="'+report.id+'" onchange="checkRecepiantsContacts(this);"  type="checkbox" name="contactids[]"  class="myRecipantsContactsCheckbox" ></td>'; 
		    	  		
		    	  		  return result;
		    	  	}
		      },{
	            "targets": colIndex++,
	            "data": "listName",
	            "render": function ( data, type, report, meta ) {
	            	     var result = "";
		    	  		 result = result +'<input type="hidden" value="row'+report.id+'" id="row'+report.id+'">';
		    	  		 result = result +'<td><div class="tbldata-dotdot-100" title="'+report.listName+'">'+report.listName+'</div></td>';
			             return  result;
	            }
	          },{
		            "targets": colIndex++,
		            "data": "spocName",
		            "render": function ( data, type, report, meta ) {
		            	     
		            	    
		            	     var result = "";
				   			 result = result +'<td><div class="proj_show" title="'+report.recipientEmail+'">'+report.listCount+'<a class="listView" onclick="listViewPop('+report.id+');">View list</a></div></td>';
			                 return result; 
		            }
		          }],
		    	"scrollCollapse": false,
		    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
		    	"aaSorting": [[1,'desc']],
		    	"bLengthChange": true,
		    	"bDestroy": true,
		    	"ordering": false,
		    	"oLanguage": {
		     		"sSearch": "",
		     		 "sZeroRecords": "", "sEmptyTable": ""
		     		
		     }
	    } );
		$("#vendorconDataTable").wrap("<div class='modal-body-custom modalAlign mailerpopup' style='overflow-x: auto;'></div>");
	    $("input[type=search]").hide();
	    $("#vendorContactsDataTableSearch").keyup(function(e) {
	    	   if(e.which === 13) {
	    		   vendorContactDataTableList1();
	    	   }
	    	});
}
</script>

 <!-- Vendor contacts List Ends Here Here-->
 
 
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

	 

function getRecipantsContacts(){
		
		
		var internalOutput = "";
		 for(var e in recepiantscontactsList) {
			 var checkbox_value = recepiantscontactsList[e].split(",");
			 var elist=$("#emailsList").chosen().find("option:selected" ).text();
			 var searchString=elist.search(checkbox_value[1]);
			 if(searchString==-1)
				{
				 internalOutput = internalOutput+"<option value='" +checkbox_value[0]+ "' selected>"+checkbox_value[1]+"</option>";
			    }
			 
			 
		  }
		
            $("#emailsList").append(internalOutput);
			$("#emailsList").trigger("chosen:updated");
			
			$("input[name='contactids[]']:checked").removeAttr('checked');
			$('#checkAllRecipantsntacts').removeAttr('checked');
		
}


	  
	  // this function is run when the country changes
	
</script>
  <!-- End -->
  
  <script>
function checkUniqueSubject(){
	var locationId = '${SourceLeadWeb_orgLocationId}';
	var name = $("#campaignSubject").val();
	if(name != null && name != '' && name != 'undefined'){
			$.get(sourceleadUrl+"/validateCampaignSubject?&orgLocationId="+locationId+"&campaignName="+name+"&campaignType=0", function(data) {
	          	if(data == true){
	          		$('#campaignSubjectDiv').find('p.jquery_form_error_message').remove();
	          		$("#campaignSend").removeAttr("disabled");
			        $("#campaignSend").attr("disabled", "disabled");
					$("input#campaignSubject").after( "<p class='jquery_form_error_message'>" + name + " is already existed</span>");
	                 }else{
	                	$("#campaignSend").removeAttr("disabled");
	                	$('#campaignSubjectDiv').find('p.jquery_form_error_message').remove();
	                 }
	            });
		}
}
</script>
  
  <script>
	$(".clearUpload").click(function(){
		$("#attachfile-mail").val("");
		$("#attachfileBtn").val("");
	        $("#hotlistCampaign_form input[data-id*=attachfileBtn]").remove();
	        $(".clearUpload").hide();
	});
</script>

	

<jsp:include page="masterFooter.jsp" />