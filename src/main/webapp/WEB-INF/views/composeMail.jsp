<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />

<link href="resources/css/summernote.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/bootstrap-multiselect.css" type="text/css">
<link rel="stylesheet" href="resources/css/bootstrap3-wysihtml5.min.css" type="text/css">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<style>
.modal-backdrop{
display: none;
}

.nicEdit-main {
	overflow: auto !important;
}
</style>

 <script>
 $(document).ready(function(){
	 var UUID = '${UUID}';
	
	 if(null != UUID && UUID != '' && UUID != 'undefined'){
		 
		 var mailType = '${mailType}';
		 
		 if(null != mailType && mailType != '' && mailType != 'undefined'){
			 loadMailBoxDetails(UUID);
		 }else{
			 $("input[name=subject]").val('${subject}');
			 var fromEmail = '${fromEmail}';
			 
			 $("#emailsList").empty();
			 $("#emailsList").append("<option value='" +fromEmail+ "' selected>"+fromEmail+"</option>");
			 $("#emailsList").trigger("chosen:updated");
		 }
	 }
});
</script> 


<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Compose Mail</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='/mailBox'/>"> Mailbox</a>
			&raquo; Compose Email</div>
    </section>
    
    
   
    <div class="content-area clearfix">
    <div id="mailLoading"></div>
     <div id ="popUp"></div>
      <div id ="textBoxPopUp"></div>
      <div class="form-horizontal compose-mail col-md-10 col-md-offset-1">
       <form:form id="email_form" name="email_form" content="text/html" enctype='multipart/form-data' >
        <div class="col-md-12 clearfix">
          <label class="col-md-2 control-label text-muted small">From</label>
          <div class="col-md-10">
            <input type="text" value="${SourceLeadWeb_user.email}" class="form-control input-sm" readonly>
          </div>
        </div>
        <div class="col-md-12 clearfix">
          <label class="col-md-2 control-label text-primary small">To</label>
          <div class="col-md-10 positionRelative disable-choosen-dropdown">

          <select class="form-control"   field-name="To List" multiple data-placeholder="Enter Emails And Press Enter-Key Or Comma(,) Or Space To Add New Email" id="emailsList" name="Toemails" data-validation-optional="true" data-validation="validate_email"  field-name="Toemails" >
            </select>
            <span class="select-multiple" data-target="#multiplemail" onclick="getUserList('All');" data-toggle="modal"><i class="fa fa-list-alt" title="Multiple Mail"></i></span> 
            <!-- Modal content Multiple mail -->
            <div role="dialog" id="multiplemail" class="modal fade">
              <div class="modal-dialog modal-lg">
                <div class="modal-content">
                  <div class="modal-header">
                    <button data-dismiss="modal" class="close" type="button">×</button>
                    <h4 class="modal-title">Select Mailer</h4>
                  </div>
                  
                   <div class="modal-body mail-body clearfix">
                    <div class="modal-body-custom mailerpopup mt25 clearfix filter-position">
                     <div id="userList" class="datatable_filter"></div>
                    </div>
                  </div> 
                  
                </div>
              </div>
            </div>
            <!-- AMultiple mail modal popup ends --> 
          </div>
        </div>
        <div class="col-md-12 clearfix">
          <label class="col-md-2 control-label text-danger small">Subject</label>
          <div class="col-md-10">
            <input type="text" name="subject" data-validation="required" field-name="Subject" id="subject" value="" class="form-control input-sm">
          </div>
        </div>
        <div class="clearfix">
          <div class="col-md-6">
            <label class="col-md-4 control-label text-info small">Salutation</label>
            <div class="col-md-3 saluation field-border">
              <select id="saluation" name="saluation">
                <option value="Hi" selected>Hi</option>
                <option value="Hello">Hello</option>
              </select>
            </div>
          </div>
        </div>
        
        <!-- Text Area -->
        <div class="col-md-12 clearfix mb10 mt10">
          <div class="col-md-12">
            <div class="clearfix txt-area">
              <textarea  id="bodytext" name="bodytext" class="form-control" rows="5" placeholder="Message" style="height: 250px; overflow: auto;">
                    
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
              <input type="text" class="form-control" readonly id="attachfile-mail" placeholder="Upload files ...">
            </div>
          </div>
        </div>
        <!-- Attachment button --> 
        
        <!-- Attachment button-->
          
          <div class="col-md-12  clearfix mt">
            <div class="pull-right"> 
              <!--<input type="button" value="Schedule" class="btn btn-sm btn-primary">-->
              <button type="button" class="btn btn-sm btn-success" onclick="sendEmails();"><i class="fa fa-envelope-o"></i>&nbsp;Send</button>
              <button type="button" class="btn btn-sm btn-default" onClick="javascript:window.location.href = '<c:url value='mailBox'/>'" ><i class="fa fa-times"></i>&nbsp;Cancel</button>
            </div>
          </div>
        <!-- Attachment button --> 
         </form:form>
      </div>
      
      
    </div>
   
    
    
  </section>
  
  
  <!-- <script type="text/javascript" src="resources/js/bootstrap-multiselect.js"></script>
  <script src="resources/js/bootstrap3-wysihtml5.all.min.js"></script>  -->
  
  <!-- <script src="resources/js/nicEdit.js"></script> -->
  
  
   <script>
  function loadMailBoxDetails(UUID){
	  var sourceleadUrl = '${sourceleadUrl}';
	  
			 $.ajax({
				 
				 type: "POST",
			        url: sourceleadUrl+"/getUserMailBoxDetailsByUUID?${_csrf.parameterName}=${_csrf.token}&UUID="+UUID, 
			        success: function(data) {
			        	var json = JSON.parse(data);
			        	
			        	
			        	 $("input[name=subject]").val(json['subject']);
			    		 
			        	 if(json['comments'] != '' && json['comments'] != null && json['comments'] != 'undefied'){
			        		// nicEditors.findEditor('bodytext').setContent('<br/>'+json['comments']+'<br/><br/>'+json['body']); 
			        		$('#bodytext').summernote('editor.pasteHTML','<br/>'+json['comments']+'<br/><br/>'+json['body']);
			        	 }
			        	 
			        	 if(json['comments'] == null && json['body'] != '' && json['body'] != null && json['body'] != 'undefied'){
			        		 //nicEditors.findEditor('bodytext').setContent('<br/><br/>'+json['body']);
			        		 $('#bodytext').summernote('editor.pasteHTML','<br/><br/>'+json['body']);
			        	 }
			        	
			        }
			 });
	  
  }
  </script> 
 

<script src="resources/js/summernote.js"></script>
<script>
	$('#bodytext').summernote({
  toolbar: [
    // [groupName, [list of button]]
    ['style', ['bold', 'italic', 'underline', 'clear']],
    ['font', ['strikethrough', 'superscript', 'subscript']],
    ['fontsize', ['fontsize']],
    ['color', ['color']],
    ['para', ['ul', 'ol', 'paragraph']],
	['table',['table']],
    ['height', ['height']]
  ]
});
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
</script>

  
  
  <script type="text/javascript">
	function sendEmails() {
		if ($('#email_form').validate(false, validationSettings)){ 
			
				var sourceleadUrl = '${sourceleadUrl}';
				//nicEditors.findEditor('bodytext').saveContent();
				
				var frm = $('#email_form').serializeFormJSON();
				var jsonData = JSON.stringify(frm);
				/* con = con.replace(/[{}]/g, ""); 
			    var jsonData = encodeURIComponent(con); */
			    var form = $('#email_form')[0];
		  		var formData = new FormData(form);
		  		formData.append("json",jsonData)
		  		var toEmails =  $('#emailsList_chosen span').size();
		  		 
		  		if(toEmails < 1){
		   	    	alertMailerMsg("Provide at least one To mail");
		   	    }
		  		else{
		  			//var bodytext = nicEditors.findEditor('bodytext').getContent();
		  			var bodytext = $("#bodytext").summernote('code');
		  			/*  if(bodytext.length < 60000){  */	
		  			
			  			$("#mailLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			  		 	$.ajax({
			  				type : "POST",
			  	    	    data: formData,
			  	    	    contentType : 'application/json; charset=utf-8',
			  	    	    enctype: 'multipart/form-data',
			  		        processData: false,
			  		        contentType:false, 
			  	    	    dataType : 'json',
			  				url : sourceleadUrl+"/sendEmailToUsers?${_csrf.parameterName}=${_csrf.token}",
			  				success : function(result) {
			  						location.replace(sourceleadUrl+"/mailBox");
			  				},
			  			}); 
			  		 /*  }else{
			  			alertTextboxMsg("Message body is too large. If you have more data, Plz go with attachment");
			  		 }   */
		  		}
		  		  
				 return true
		 
		 
		}else{
			return false;
		  }
		
	}
		
	
	$('body').on('blur', '#email_form', function() {
		$("#email_form").showHelpOnFocus();
		$("#email_form").validateOnBlur(false, validationSettings);  
   });
</script>


<script type="text/javascript">
function alertMailerMsg(msgText){
	
	var result = "";
	result = result +"<div class='project_inn_mask'></div>";
	result = result +"<div id='mailerPopup' class='remove_user_pop remove_user_pop_a'>";
	result = result +"<p><strong>"+msgText+"</strong></p>";
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
function getUserList(teamType){  
	  var sourceleadUrl ='${sourceleadUrl}';
	  $("#userList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getMailingUserList?${_csrf.parameterName}=${_csrf.token}&teamType="+teamType,    
            success :function(data) {
            	
            	getTeamsList(teamType);
                 if(data != null && data != 'undefined' && data != "" && data != '[]'){
               		
			    	     $("#userList").removeAttr("style");
	                	    var result = '';
	                	    result = result +'<div class="modal-bodyCustom mt clearfix popupFilter">';
	                	    result = result +'<div class="form-horizontal custom-form">';
	                		result = result +'     <div class="row">';
	                		result = result +'       <div class="col-xs-12 col-md-5 col-lg-5 selectpicker100">';
	                		result = result +'          <select id="rolesDiv" onchange="filterTeamMembers();" class="selectpicker form-control" style="display:block !important">';
	                		result = result +'          </select>';
	                		result = result +'     </div>';
	                		result = result +'     </div>';
	                		result = result +'     </div>';
	                		result = result +'     <div class="modal-bodyCustom mt clearfix popupFilter"><br/><br/>'; 
	                		result = result +'       <table id="example"  class="display table table-stripped table-bordered" cellspacing="0" width="100%"  >';
	                		result = result +'           <thead>';
	                		result = result +'           <tr>';
	                		result = result +'             <th><input type="checkbox" id="checkAllUsers" onclick="toggleAll(this);"></th>';
	                		result = result +'             <th>Name</th>';
	                		result = result +'             <th>Email</th>';
	                		result = result +'             <th>Role</th>';
	                		result = result +'           </tr>';
	                		result = result +'           </thead>';
		                    result = result +"<tbody>"; 
		                    var json = JSON.parse(data);
		                    for (var i = 0; i < json.length; i++) { 
		                    	 var opt = json[i]; 
		                    	 result = result +'<tr>';
		                    	 result = result +'             <td>&nbsp;&nbsp;<input id="'+opt.id+'" value="'+opt.email+'"  type="checkbox" name="emails[]" class="myCheckbox" ></td>';
		                    	 result = result +'             <td>'+opt.firstName+' '+opt.lastName+'</td>';
		                    	 result = result +'             <td>'+opt.email+'</td>';
		                    	 result = result +'             <td>'+opt.userType+'</td>';
		                    	 result = result +'           </tr>';
		                    	
	                     }
		                    
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     result = result +'     </div>'; 
	                 	 result = result +'     <div class="clearfix">';
	                 	 result = result +'       </div>'; 
	                 	 result = result +'   </div>'; 
	                 	 result = result +'   <div class="clearfix pull-right mr mt10">';
	                 	 result = result +'     <button class="btn btn-primary"  type="button" data-dismiss="modal" id="assignbtnId" onClick="getEmails();">Add</button>';
	                 	 result = result +'     <button data-dismiss="modal" class="btn btn-default" type="button">Close</button>';
	                 	 result = result +'   </div>'; 
	                 	 $("#userList").empty(); 
	                     $("#userList").append(result); 
	                     pagination(); 
                 }else{
                	 $("#userList").removeAttr("style");
  	    	         var result ="";
		    	    
  	    	       result = result +'<div class="modal-bodyCustom mt clearfix popupFilter">';
 	    	        result = result +'<div class="form-horizontal custom-form">';
	           		result = result +'     <div class="row">';
	           		result = result +'       <div class="col-xs-12 col-md-8 col-lg-8 hidden">';
	           		result = result +'         <div class="input-group">';
	           		result = result +'           <input type="search" class="form-control" value="">';
	           		result = result +'           <span class="input-group-addon bt-primary"><i class="fa fa-search"></i></span> </div>';
	           		result = result +'       </div>';
	           		result = result +'       <div class="col-xs-12 col-md-4 col-lg-4 selectpicker100">';
	           		result = result +'          <select id="rolesDiv" onchange="filterTeamMembers();" class="selectpicker form-control" style="display:block !important">';
	           		result = result +'          </select>';
	           		result = result +'     </div>';
	           		result = result +'     </div>';
	           		result = result +'     </div>';
	           		
	           		result = result +'     <div class="modal-bodyCustom mt clearfix popupFilter"><br/><br/>'; 
	        		result = result +'       <table id="example" class="display table table-stripped table-bordered" cellspacing="0" width="100%" >';
	        		result = result +'           <thead>';
	        		result = result +'           <tr>';
	        		result = result +'             <th class="sorting_disabled" rowspan="1" colspan="1" style="width: 30px;"><input type="checkbox"></th>';
	        		result = result +'             <th class="sorting_disabled" rowspan="1" colspan="1" style="width: 30px;">Name</th>';
	        		result = result +'             <th class="sorting_disabled" rowspan="1" colspan="1" style="width: 30px;">Email</th>';
	        		result = result +'             <th class="sorting_disabled" rowspan="1" colspan="1" style="width: 30px;">Role</th>';
	        		result = result +'             <th class="sorting_disabled" rowspan="1" colspan="1" style="width: 30px;">Active Jobs</th>';
	        		result = result +'           </tr>';
	        		result = result +'           </thead>';
	        		result = result +'           </table>';
	        		result = result +'     </div>';
	        		result = result +'     </div>';
					$("#userList").empty();
				    $("#userList").append(result); 
				    pagination();  
                }
          },
            
       }); 
  } 
</script>

<script>
function getTeamsList(teamType){  
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getComposeMailTeamsList?${_csrf.parameterName}=${_csrf.token}",    
            success :function(data) {
            	var json = JSON.parse(data);
				var output = "";
				output = output +'          <option selected disabled>Select Role</option>';
				if(teamType == 'All'){
					output = output +'           <option value="All" selected>All</option>';
				}else{
					output = output +'           <option value="All">All</option>';
				}
			
			  for (var i = 0; i < json.teams.length; i++) {
					var opt = json.teams[i];
					if(teamType == opt.id+"role"){
						output = output + "<option value='"+opt.id+"' selected>"+ opt.name + "</option>";
					}else{
						output = output + "<option value='"+opt.id+"'>"+ opt.name + "</option>";	
					}
				}
			  
				for (var i = 0; i < json.roles.length; i++) {
					var opt = json.roles[i];
					if(teamType == opt.id+"role"){
						output = output + "<option value='"+opt.id+"role' selected>"+ opt.displayName + "</option>";
					}else{
						output = output + "<option value='"+opt.id+"role'>"+ opt.displayName + "</option>";	
					}
				}
				$("#rolesDiv").empty();
			    $("#rolesDiv").append(output);
            	
            },
      }); 
 } 
</script>

<script type="text/javascript">
function filterTeamMembers(){
	var teamType = $("#rolesDiv").val();
	getUserList(teamType);
	
}
</script>


 <script>
function toggleAll(source) {
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i] != source)
            checkboxes[i].checked = source.checked;
        
    }
$('input:checkbox:not(#checkAllUsers)').click(function() {
    if(!this.checked) {
        $('#checkAllUsers').removeAttr('checked');
    }
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
    	"ordering": true,
    	"aaSorting": [[1,'desc']],
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 0
			}],
    	"oLanguage": {
     		"sSearch": ""
     	}
    } );
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
		$('#checkAllUsers').removeAttr('checked');
		
		
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
		    select = $("#emailsList");
		    select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Space Or Tab To Add New Email:' });
		    chosen = select.data('chosen');
		    chosen.search_field.bind(('keydown keyup'), function(e)
		    { 
		    	 
		    	 var enterEmailArray = this.value.split(/[ ,]+/);
		    	 if(enterEmailArray.length > 1){
	        		 for (var i=0; i < enterEmailArray.length; i++) {
	        			 var option = $("<option>").val(enterEmailArray[i].replace(",","")).text(enterEmailArray[i].replace(",",""));
	        			 select.append(option);
	 		             select.find(option).prop('selected', true);
	 	        		 $("#emailsList").trigger("chosen:updated");
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
function validateEmail(email){
	var re = /(\w+)\@(\w+)\.[a-zA-Z]/g;
	var testEmail = emailRegularExp.test(email);
	if(!testEmail){
        alert("Please enter a valid email");
    }
}
</script>



<script>
function candidateListChange(){
	
	
	  var candidatesListType = document.getElementById("candidatesListType").value;
	  $("#candidatesList").empty(); 
	  getCandidates(candidatesListType);
}

</script>



  <script>
  $(function () {
    //Add text editor
    $("#compose-textarea").wysihtml5();
	  $("#create-signature").wysihtml5();
  });
//upload file script -laptop-shiftA
document.getElementById("attachfileBtn").onchange = function () {
	$('#attachfile-mail').val($(this).val().replace(/C:\\fakepath\\/i, ''))
};
</script> 
  <script type="text/javascript">
	function createSign(){
	   $(".createSign-pop").fadeToggle(200);
	}
		function closeSign(){
	   $(".createSign-pop").fadeOut(200);
	}
	</script> 
  
  
<script>
	wysihtml5.dom.getPastedHtml = function(event) {
	var html;
	if (event.clipboardData) {
	html = event.clipboardData.getData('text/plain');
	}
	return html;
	};
</script>

<!-- 

<script type="text/javascript">
	bkLib.onDomLoaded(function() { nicEditors.allTextAreas() });
</script>
-->

  
<jsp:include page="masterFooter.jsp" />