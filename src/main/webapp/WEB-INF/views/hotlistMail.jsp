<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<link rel="stylesheet" href="resources/css/bootstrap-multiselect.css?staticTime=${staticTime}" type="text/css">
<link rel="stylesheet" href="resources/css/bootstrap3-wysihtml5.min.css?staticTime=${staticTime}" type="text/css">


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
</style>


<script>
 $(document).ready(function(){
	 var UUID = '${UUID}';
	 if(null != UUID && UUID != '' && UUID != 'undefined'){
		 
		 var mailType = '${mailType}';
		 if(null != mailType && mailType != '' && mailType != 'undefined'){
			 loadHotMailBoxDetails(UUID);
		 }
	 }
});
</script> 

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Hotlist Mail Box</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='/mailBox'/>"> Mailbox</a>
			&raquo; Hotlist Campaign</div>
    </section>
    
    
   
    <div class="content-area clearfix">
    <div id="mailLoading"></div>
    <div id ="popUp"></div>
    <div id ="textBoxPopUp"></div>
      <div class="form-horizontal compose-mail col-md-10 col-md-offset-1">
       <form:form id="hotlistCampaign_form" name="hotlistCampaign_form"  enctype='multipart/form-data' >
        <div class="col-md-12 clearfix">
          <label class="col-md-2 control-label text-muted small">From</label>
          <div class="col-md-10">
            <input type="text" value="${SourceLeadWeb_user.email}" class="form-control input-sm" readonly>
          </div>
        </div>
        <div class="col-md-12 clearfix">
          <label class="col-md-2 control-label text-primary small">To</label>
          <div class="col-md-10 positionRelative disable-choosen-dropdown">

            <select class="form-control"  field-name="To List" multiple data-placeholder="Enter Emails And Press Enter-Key Or Comma(,) Or Space To Add New Email" id="emailsList" data-validation="validate_email" data-validation-optional="true" name="Toemails">

            </select>
            <span class="select-multiple" data-target="#multiplemail" onclick="listChange();" data-toggle="modal"><i class="fa fa-list-alt" title="Multiple Mail"></i></span> 
            <!-- Modal content Multiple mail -->
            <div role="dialog" id="multiplemail" class="modal fade">
              <div class="modal-dialog modal-lg">
                <div class="modal-content">
                  <div class="modal-header">
                    <button data-dismiss="modal" class="close" type="button">×</button>
                    <h4 class="modal-title">Select Mailer</h4>
                  </div>
                  
                   <div class="modal-body clearfix">
                    <div class="form-horizontal custom-form">
                      <div class="row">
                        <div class="col-xs-12 col-md-4 col-lg-4 selectpicker100 select-field">
                          <select class="form-control" id="clientListType" onchange="listChange();">
                           <option value="listbyContact" selected>List by contact</option>
                            <option value="listbyClient">List by client</option>
                          </select>
                        </div>
                      </div>
                    </div>
                    <div class="modal-body-custom mailerpopup mt25 clearfix filter-position">
                     <div id="clientsList" class="datatable_filter"></div>
                    </div>
                  </div> 
                  
                </div>
              </div>
            </div>
            <!-- AMultiple mail modal popup ends --> 
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
          <div class="col-md-10">
            <input type="text" name="subject" data-validation="required" field-name="Subject" id="subject" class="form-control input-sm">
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
                  
                  <div class="modal-body clearfix hotmailSelOut">
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
                    
                    <div class="modal-body-custom clearfix poMail">
                        <div class="hotmailSel">
                          <select class="form-control" id="candidatesListType" onchange="candidateListChange();">
                            <option value="consultants" selected>Associates</option>
                            <option value="candidates">Candidates</option>
                             <option value="openHotlist">Open Hotlist</option>
                            <option value="hotlistConsultants">Hostlist Associates</option>
                          </select>
                        </div>                    
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
        <div class="col-md-12 clearfix mb10">
          <div class="col-md-12 attachment">
            <div class="input-group">
              <label class="input-group-btn"> <b class="btn btn-primary"><i class="fa fa-paperclip"></i>
                <input type="file" name="attachfile" style="display: none;" multiple id="attachfileBtn" class="upload">
                </b> </label>
              <input type="text" class="form-control" readonly id="attachfile-mail" placeholder="Files are not selected...">
            </div>
          </div>
        </div>
        <!-- Attachment button --> 
        
        <!-- Attachment button-->
        <div class="col-md-12 clearfix mt">
            <div class="pull-right"> 
              <!--<input type="button" value="Schedule" class="btn btn-sm btn-primary">-->
              <button type="button" class="btn btn-sm btn-success" onclick="sendHotListCampaign();"><i class="fa fa-envelope-o"></i>&nbsp;Send</button>
              <button type="button" class="btn btn-sm btn-default" onClick="javascript:window.location.href = '<c:url value='mailBox'/>'" ><i class="fa fa-times"></i>&nbsp;Cancel</button>
            </div>
        </div>
        <!-- Attachment button --> 
         </form:form>
      </div>
      
      
    </div>
   
    
    
  </section>
  
<!--   
  <script type="text/javascript" src="resources/js/bootstrap-multiselect.js"></script>
  <script src="resources/js/bootstrap3-wysihtml5.all.min.js"></script>  -->
  
  
  <script src="resources/js/nicEdit.js"></script> 
   

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
</script>

  
  
  <script type="text/javascript">
	function sendHotListCampaign() {
		if ($('#hotlistCampaign_form').validate(false, validationSettings)){ 
		var sourceleadUrl = '${sourceleadUrl}';
		nicEditors.findEditor('bodytext').saveContent();
		var frm = $('#hotlistCampaign_form').serializeFormJSON();
		var con = JSON.stringify(frm);
		con = con.replace(/[{}]/g, ""); 
	    var jsonData = encodeURIComponent(con);
	    var form = $('#hotlistCampaign_form')[0];
  		var formData = new FormData(form)
  		
  		var toEmails =  $('#emailsList_chosen span').size();
  		var otherEmails =  $('#otherEmails_chosen span').size();
  		
  		var selectedEmails = toEmails + otherEmails;
  		
  	    if(selectedEmails < 1){
  	    	
  	    	alertMailerMsg();
  	    }
  	    else{
	  	    	var bodytext = nicEditors.findEditor('bodytext').getContent();
	  			if(bodytext.length < 60000){
	  	    	
	  	    	$("#mailLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  			$.ajax({
	  				type : "POST",
	  	    	    data: formData,
	  	    	    contentType : 'application/json; charset=utf-8',
	  	    	    enctype: 'multipart/form-data',
	  		        processData: false,
	  		        contentType:false, 
	  	    	    dataType : 'json',
	  				url : sourceleadUrl+"/sendHotListCampaign?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
	  				success : function(result) {
	  						location.replace(sourceleadUrl+"/mailBox");
	  				},
	  			}); 
	  	    }else{
	  	    	alertTextboxMsg("Message body is too large. If you have more data, Plz go with attachment");
	  	    }
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
	 
  }
  </script>
  
  
  
  
<script>
function getClientContacts(){ 
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getClientContacts?${_csrf.parameterName}=${_csrf.token}",    
            success :function(data) {
            	
                 if(data != null && data != 'undefined' && data != "" && data != '[]'){
               		
	                	   
               			    var result = '';
	                		result = result +'     <div class="modal-bodyCustom mt40 clearfix popupFilter hdrAlign">'; 
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
	                 	 result = result +'   <div class="modal-footer">';
	                 	 result = result +'     <button data-dismiss="modal" class="btn btn-primary" type="button" onClick="getEmails();">Assign</button>';
	                 	 result = result +'     <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>';
	                 	 result = result +'   </div>'; 
	                 	 $("#clientsList").empty(); 
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
					$("#clientsList").empty();
				    $("#clientsList").append(result); 
				    pagination();  
                }
          },
            
       }); 
  } 
</script>
  
  
  
  
  <script>
function getClientList(){ 
	  var sourceleadUrl ='${sourceleadUrl}';
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
	                	    result = result +'<div class="modal-body clearfix">';
	                		result = result +'     </div>';
	                		result = result +'     <div class="modal-bodyCustom mt40 clearfix popupFilter">'; 
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
	                 	 result = result +'   <div class="modal-footer">';
	                 	 result = result +'     <button data-dismiss="modal" class="btn btn-primary" type="button" onClick="getContactEmails();">Assign</button>';
	                 	 result = result +'     <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>';
	                 	 result = result +'   </div>'; 
	                 	 $("#clientsList").empty(); 
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
 					$("#clientsList").empty();
 				    $("#clientsList").append(result); 
 				    pagination();  
                 }
          },
            
       }); 
  } 
</script>
  
<script>
function pagination(){
    $("#example").DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
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
    $("#example").wrap("<div class='overflow_inherint'></div>");
}
</script>





<script>
function getCandidates(candidatesListType){
	
	  var sourceleadUrl ='${sourceleadUrl}';
	  
	  $("#candidatesLoading").attr('style','position:absolute; width:98%; height:49%; background-color:rgba(255,255,255,0.8); top:140px; left:17px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 
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
	                 	 result = result +'   <div class="modal-footer">';
	                 	 result = result +'     <button data-dismiss="modal" class="btn btn-primary" type="button" onClick="getCheckedCandidates();">Assign</button>';
	                 	 result = result +'     <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>';
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
    } );
}
</script>


 <script>
function toggleClientContacts(source) {
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i] != source)
            checkboxes[i].checked = source.checked;
        
    }
$('input:checkbox:not(#checkAllContacts)').click(function() {
    if(!this.checked) {
        $('#checkAllContacts').removeAttr('checked');
    }
});
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
	
	var oTable = $('#candidates').dataTable();
	var rowcollection =  oTable.$("input[name='candidatesIds[]']:checked", {"page": "all"});
	
	
	var internalOutput = "";
	rowcollection.each(function(index,elem){
		 var checkbox_value = $(elem).val().split(",");
		 internalOutput = internalOutput+"<option value='" +checkbox_value[0]+ "' selected>"+checkbox_value[1]+"</option>";
	});
	 
	  
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
  <script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			
			
			  //Adding Customized Emails to the list
	        var select, chosen;
		    select = $("#otherEmails");
		    select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Space Or Tab To Add New Email:' });
		    chosen = select.data('chosen');
		    chosen.search_field.bind(('keydown keyup'), function(e)
		    {
		    	 var otherEmailArray = this.value.split(/[ ,]+/);
		    	 if(otherEmailArray.length > 1){
		    		 for (var i=0; i < otherEmailArray.length; i++) {
	        			 var option = $("<option>").val(otherEmailArray[i].replace(",","")).text(otherEmailArray[i].replace(",",""));
	        			 select.append(option);
	 		             select.find(option).prop('selected', true);
	 	        		 $("#otherEmails").trigger("chosen:updated");
	                 }
		    	 }else{
		    		 var newEmail = this.value.replace(","," ");
		    		   if (tagItBykeyForEmails(e) && (newEmail != null  && newEmail != '' && newEmail != 'undefined'))
				        {
		    			         preventTabkey(e); 
			        		     var par = this.parent;
				        		 var option = $("<option>").val(newEmail.replace(",","")).text(newEmail.replace(",",""));
				        		 select.append(option);
					             select.find(option).prop('selected', true);
				        		 $("#otherEmails").trigger("chosen:updated");
				        
				           
			        		
				        } 
		    	 }
		    	
		    });
		    

		    var selectone, chosenone;
		    selectone = $("#emailsList");
		    selectone.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Space Or Tab To Add New Email:' });
		    chosenone = selectone.data('chosen');
		    chosenone.search_field.bind(('keydown keyup'), function(e)
		    {
		    	 var toEmailArray = this.value.split(/[ ,]+/);
		    	 if(toEmailArray.length > 1){
		    		 for (var i=0; i < toEmailArray.length; i++) {
	        			 var option = $("<option>").val(toEmailArray[i].replace(",","")).text(toEmailArray[i].replace(",",""));
	        			 selectone.append(option);
	        			 selectone.find(option).prop('selected', true);
	 	        		 $("#emailsList").trigger("chosen:updated");
	                 }
		    	 }else{
		    			var newEmail = this.value.replace(","," ");
		    			if (tagItBykeyForEmails(e) && (newEmail != null  && newEmail != '' && newEmail != 'undefined'))
				        {
		    				preventTabkey(e); 
				        	var par = this.parent;
				        		 var option = $("<option>").val(newEmail.replace(",","")).text(newEmail.replace(",",""));
				        		 selectone.append(option);
				        		 selectone.find(option).prop('selected', true);
					        		$("#emailsList").trigger("chosen:updated");
				        }
		    	 }
		    
		    });
		    
		    

			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
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
	bkLib.onDomLoaded(function() { nicEditors.allTextAreas() });
</script>
  
<jsp:include page="masterFooter.jsp" />