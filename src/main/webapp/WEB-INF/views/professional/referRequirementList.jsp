<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<jsp:include page="../socialinviterscripts.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
$(document).ready(function() {
	 $("#myModal").hide();
	socialInviterScripts();
	refercontactList();
});	
</script>
<style type="text/css">
.emailWidth {
	width:400px;
}
.model-err-msg {
    clear: both;  margin: 0 auto;  padding: 0px;
    color: #E65C8D; text-align: center; width: 54%;
    margin-bottom: 10px; margin-top: -18px; font-size: 12px;
}
img.contact-images { width:24px; height: 24px; margin-right: 10px;
}
.importcsv-accordion .panel-title a {
	display: block; width:100%;
}
</style>
<script>
 function contactsImportSucess(service,status){
	 closePop();
	 msgFadein();
     $("#msgDiv").html(service+" Contacts Are Imported Successfully..");
	 refercontactList();
 }
</script>

<script>
	function msgFadein() {
		$("#selector").fadeIn(200);
        $('#selector').delay(2000).fadeOut('slow');
	}
</script> 


<div class="fade" id="myModal" role="dialog">
   <form:form id="uploadContactCSV_form" name="uploadContactCSV_form" enctype="multipart/form-data">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body">
          <div class="clearfix minHeight40 form-group">
          <a href="<c:url value='${appPath}/downloadContactCSVTemplate'/>" class="btn btn-info col-md-4 col-md-offset-4">
					Download Contact CSV<i class="fa fa-download paddingLeft"></i>
		  </a>
          </div>
          
          <div class="clearfix minHeight40 form-group">
            <label class="col-md-3 padding_left mt5"><strong>Choose CSV File</strong></label>
            <div class="clearfix col-md-9">
              <div class="row">
                <div style="margin:0 auto" class="input-group col-md-9 pull-left errorInptgroup">
                  <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                    <input type="file" name="file" style="display: none;" multiple id="uploadBtn" class="upload" >
                    </span></label>
                  <input class="form-control" readonly id="uploadFile" placeholder="No file chosen" field-name="Contact CSV"  data-validation="required">
                  <div class="clearUpload" style="display: none"></div>
                </div>
                <div class="col-md-3 padding_right">
                  <button type="button" class="btn btn-primary btn-block" id="uploadContact_id" onclick = "saveContactCSV();">Upload</button>
                </div>
              </div>
            </div>
          </div>
          
          <div class="model-err-msg" style="display: none">Please upload a file of type *.CSV</div>
          
          <h5>How to Export Contacts(.csv) ? </h5>
          <div class="clearfix mt10"></div>
          <div class="bs-example importcsv-accordion">
            <div class="panel-group mb10" id="accordion">
            
            <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo"><span class="fa fa-angle-down pull-right"></span><img src="/resources/images/gmail-icon.png" class="contact-images">Gmail</a> </h4>
                </div>
                <div id="collapseTwo" class="panel-collapse collapse">
                  <div class="panel-body">
                  <ol class="col-md-12 mb0">
                    <li>From your Gmail account, choose Gmail >Contacts</li>
					<li>Select More >Export.</li>
					<li>Choose the contact group you want to export.</li>
					<li>Choose the export format Outlook CSV (for importing into Outlook or another application).</li>
					<li>Choose Export.</li>
					<li>When prompted, choose Save As, and browse to a location to save the file.</li>
				</ol>
                  </div>
                </div>
              </div>
              
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne"><span class="fa fa-angle-down pull-right"></span><img src="/resources/images/outlook-icon.png" class="contact-images">Outlook</a> </h4>
                </div>
                <div id="collapseOne" class="panel-collapse collapse">
                  <div class="panel-body">
                    <ol class="col-md-12 mb0">
                      <li>Choose Open &amp; Export > Import/Export. </li>
                      <li>Choose Export to a file. </li>
                      <li>Select Comma Separated Values (Windows) and click Next. </li>
                      <li>Select Contacts and click Next. A screen appears where you can specify where to save the file. </li>
                      <li>Click Browse and go to where to save the file, and then enter a name for the .CSV file. </li>
                    </ol>
                  </div>
                </div>
              </div>
              
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree"><span class="fa fa-angle-down pull-right"></span><img src="/resources/images/thunderbird-ic.png" class="contact-images">Thunderbird</a> </h4>
                </div>
                <div id="collapseThree" class="panel-collapse collapse">
                  <div class="panel-body">
                  <ol class="col-md-12 mb0">
	                  <li> Click Tools and select Address Book</li>
					  <li> Select the address book you want to export.</li>
					  <li> Then click Tools again in the Address Book and select Export... while keeping your address book selected.</li>
				      <li> Save the file somewhere convenient as an LDIF file with whatever filename you wish.</li>
			        </ol>
                  </div>
                </div>
              </div>
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseFour"><span class="fa fa-angle-down pull-right"></span><img src="/resources/images/yahoo-icon.png" class="contact-images">Yahoo Mail</a> </h4>
                </div>
                <div id="collapseFour" class="panel-collapse collapse">
                  <div class="panel-body">
                   <ol class="col-md-12 mb0">
                     <li>Click on the Contacts tab.</li>
					 <li>Click the Actions dropdown box.</li>
					 <li>Choose Export.</li>
					 <li>Click Export Now next to "Yahoo! CSV" which is the type of program to which you want to export your contacts.</li>
					 <li>After clicking Export Now, you will see a verification code. Write it in the text box above the security code box and then click Export Now.</li>
					</ol>
                  </div>
                </div>
              </div>
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseFive"><span class="fa fa-angle-down pull-right"></span><img src="/resources/images/linkedin-icon.png" class="contact-images">LinkedIn</a> </h4>
                </div>
                <div id="collapseFive" class="panel-collapse collapse">
                  <div class="panel-body">
                    <ol class="col-md-12 mb0">
                      <li>Click on My Network icon at top of your page</li>
                      <li>Click on "See all" in the network list</li>
                      <li>Click on "Manage synced and imported contacts</li>
                      <li>Click on Export Contacts on left side of the page</li>
                      <li>Enable the "Connections" Checkbox and click on Request archive.</li>
                      <li>Authorize account by providing password of LinkedIn. </li>
                      <li>The mail will be forwarded to your mail with download link.</li>
                    </ol>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="clearfix">
          <div class="small pull-left"><span class="text-muted">Note:</span><span class="text-info ml">Email is required to Import a Contact</span></div>
          <button type="button" class="btn btn-default pull-right ml10" data-dismiss="modal" onclick="resetForm();">Close</button>
          </div>
        </div>

      </div>
    </div>
     </form:form>
  </div>
  <script type="text/javascript">
	//script for upload file
	document.getElementById("uploadBtn").onchange = function () {
		var uploadBtn = $("#uploadBtn").val();
		 var splitFile = uploadBtn.split(".")
		if(splitFile[1] == "csv"){
			document.getElementById("uploadFile").value = this.value.split( '\\' ).pop();
			$(".clearUpload").show();
			$("#uploadContact_id").removeAttr("disabled");
			$(".model-err-msg").hide();
			$(".errorInptgroup").find('p.jquery_form_error_message').remove();
		}else{
			$("#uploadContact_id").attr("disabled", "disabled");
			$(".errorInptgroup").find('p.jquery_form_error_message').remove();
			$(".model-err-msg").show();
		}
	
		
	}
	$(".clearUpload").click(function(){
		$("#uploadFile").val('');
		$(".clearUpload").hide();
		$("#uploadContactCSV_form input[data-id*=uploadBtn]").remove();
	});
	function resetForm(){
		$("#uploadContactCSV_form")[0].reset();
		$("#uploadContact_id").removeAttr("disabled");
		$(".model-err-msg").hide();
		$("#uploadFile").val('');
		$(".clearUpload").hide();
		$("#myModal").hide();
		$("#myModal").removeClass("modal");
		$("#myModal").addClass("fade");
		$("#accordion").find(".in").removeClass("in");
	}
</script>
  <script>
  function saveContactCSV(){
	if ($('#uploadContactCSV_form').validate(false, validationSettings)){
		var frm = $('#uploadContactCSV_form').serializeFormJSON();
		$("#uploadContact_id").attr("disabled", "disabled");
		var con = JSON.stringify(frm);
		con = con.replace(/[{}]/g, "");
		
		//It will escape all the special characters
		var jsonData = encodeURIComponent(con);
		var form = $('#uploadContactCSV_form')[0];
		var formData = new FormData(form); 
		 $.ajax({
			   	type: "POST",
			   	data: formData,
	    	    contentType : 'application/json; charset=utf-8',
	    	    enctype: 'multipart/form-data',
		        processData: false,
		        contentType:false,
	    	    dataType : 'json',
			    url : sourceleadUrl+"/storeCsv?"+tokenParamName+"="+tokenValue+"", 	   
	 		    success : function(data) {
	 		    	$("#uploadContact_id").removeAttr("disabled");
	 		    	$("#myModal").modal("hide");
	 		    	location.reload();
	 		    },
		 });
		 
	}
  }
  </script>
  <script>
	//File Accordion Script
    $(document).ready(function(){
        // Add minus icon for collapse element which is open by default
        $(".collapse.in").each(function(){
        	$(this).siblings(".panel-heading").find(".fa").addClass("fa-angle-up").removeClass("fa-angle-down");
        });
        // Toggle plus minus icon on show hide of collapse element
        $(".collapse").on('show.bs.collapse', function(){
        	$(this).parent().find(".fa").removeClass("fa-angle-down").addClass("fa-angle-up");
        }).on('hide.bs.collapse', function(){
        	$(this).parent().find(".fa").removeClass("fa-angle-up").addClass("fa-angle-down");
        });
        
    });
</script>
<section class="content-wraper">
<section class="content-header clearfix">
     <div class="success_pop" id="selector" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
     </div>
    <div class="pull-left">
      <h2>Contacts List</h2>
      </div> 
    <div class="pull-right brud-crum" id="contactsListUrl"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Contacts List</div>
  </section>
  <div id="contactsListSheet" class="timesheet_out">
      <div class="vendor_wrap">
      <div class="timesheet_head">
          <ul>
            <li class="hidden"><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop"></i>
            </li>
            
            <li>
            	<span  id="moveFolder" class="moveFolder" onClick="movefolderPop();"><i class="fa fa-folder mr" title="Move"></i>|</span>
            </li>
            
            <li class="hidden">
				<div class="dropdown dropdown-label hidden " style="position: relative; top: -2px;">
				<span class="dropdown-toggle cursorPointer paddingRight" type="button" id="menu1" data-toggle="dropdown">
				<i class="fa fa-folder paddingRight"></i>Move
				<span class="caret" style="position: relative; top: 0px;"></span></span> <span class="paddingLeft">|</span>
				<ul class="dropdown-menu dropdown-menu-right checkBox paddingLeft paddingRight" role="menu" aria-labelledby="menu1">
					<li>
		                    <input value="None" id="c1" type="checkbox">
		                    <!-- <div id="groupList"></div> -->
		             </li>
				
				</ul>
				</div>
				
				<%-- <div id="groupDiv" class="pull-left dropdown-select hidden">
					<select class="form-control chosen-select" id="consGroup" onchange="refercontactListToGroup();">
						<option value="">&nbsp;--Select Group--</option>
						<c:forEach var="conGroup" items="${contactsGroupList}">
			                 <option value="${conGroup.id}">${conGroup.groupName}</option>
		                </c:forEach>
					</select>
				</div> --%>
				
			</li>
          
          <li>
				<div class="pull-left cursorPointer">
					<span title="Manage Group" class="paddingRight" onClick="getContactsGroupList();showLable();"><strong>Manage Group
					 <i class="paddingLeft fa fa-caret-right"></i></strong></span> |
			  	<!-- ManageLabels div -->
			  	<div class="manageLables-div" id="add_group_Div">
       			<div class="clearfix">
       			<button type="button" class="btn btn-sm btn-primary" onClick="addLabelPop();">
       				<i class="fa fa-plus paddingRight"></i>Add
       			</button>
       			
       			<div id="groupList"></div>
       			
			  </div>
      		<a href="javascript:void(0)" class="pull-right closemld" title="Close Div">&times;</a>
       </div>
       <!-- ManageLabels div -->
			  	</div>
			</li>
			<li>
				<div class="dropdown bootstrap_dropdownMenu filterActive dropdown-label" style="position: relative; top: -2px;">
				<span class="dropdown-toggle cursorPointer" type="button" id="menu1" data-toggle="dropdown">
				<i class="fa fa-filter paddingRight text-info"></i>Filter Group
				<i class="caret" style="position: relative; top: 0px;"></i></span>
				<div class="dropdown-menu bootstrap_dropdownMenu dropdownPadding">
				<ul class="checkBox" role="menu" aria-labelledby="menu1">
                    	  <c:forEach var="report" items="${contactsGroupList}">
                        	<li><label class="tbldata-dotdot-big" title="${report.groupName}"><input   name="groupsFilter" value ="${report.id}"  onclick="refercontactList();"  type="checkbox"> ${report.groupName}</label></li>
						  </c:forEach>
				</ul>
				</div>
			</div>
			</li>
            
            <li><a href="javascript:void(0)" id="imCon"><i class="fa fa-plus"></i>Import Contacts</a></li>
            <li><a id="addPop" href="javascript:void(0)"><i class="fa fa-plus"></i>Add Contact</a>
            
            <form:form id="addcontact_form"  name="addcontact_form" method="post"> 
				<div class="referWrap referWrap_custom">
				<div id="loading"></div> 
				  <div class="referWrapInn">
					<div class="form-group col-md-6" id="firstNameDiv">
					  <label class="col-md-12">First Name *</label>
					  <div class="col-md-12">
						<input type="text" class="form-control" name="firstName" id="firstName" field-name="First Name" data-validation="required validate_Space validate_Alphanumeric validate_length length1-100">
						<span class="hidden"></span> </div>
					</div> 
					<div class="form-group col-md-6" id="lastNameDiv">
					  <label class="col-md-12">Last Name</label>
					  <div class="col-md-12">
						<input type="text" class="form-control" name="lastName" id="lastName" field-name="Last Name" data-validation-optional="true" data-validation="validate_Space validate_Alphanumeric validate_length length1-100">
						<span class="hidden"></span> </div>
					</div> 
					<div class="form-group col-md-6" id="emailDiv">
					  <label class="col-md-12">Email Id *</label>
					  <div class="col-md-12">
						<input type="text" class="form-control" name="email" id="email" onblur="validateContactEmail()"  field-name="Email" data-validation="required validate_Space validate_length length2-100 validate_email">
						<span class="hidden"></span> </div>
					</div> 
					<div class="form-group col-md-6" id="phoneDiv">
					  <label class="col-md-12">Phone </label>
					  <div class="col-md-12" >
						<input type="text" class="form-control"  name="phone" id="phone" field-name="Phone" data-validation-optional="true" data-validation="validate_Space validate_phone validate_length length9-20">
						<span class="hidden"></span> </div>
					</div> 
					<div class="col-md-12 mt10 clearfix">            
					<div class="pull-right">
					  <input type="submit" value="Add Contact" id="add_contact" onclick="saveReferContacts();" class="btn btn-primary">
					  <input type="button" value="Cancel" onclick="flushContactDetails()" class="btn btn-default ml10 canBtn">
					</div>    
					</div> 

				  <div class="clearfix"></div>
				  </div>
			  </div> 
		</form:form>
            </li>
          </ul>
          <div class="clearfix"></div>
        </div>
      <div id="refercontactListDiv" class="datatable_filter"></div>
      </div>
	</div>
	
	<div id="connnectionsSheet" class="conneWrap">
	
	
	</div>
  
</section>

<div id="deleteGroupPopUp"></div>
<div id="contactsPopUp"></div>
<div class="sideMask"></div>
<div class="movefolder-popdiv">
<form id="addcontacttogroup_form"  name="addcontacttogroup_form">
	<a class="close">&times;</a>
	<ul class="list-unstyled">
	<c:forEach var="conGroup" items="${contactsGroupList}">
		<li class="tbldata-dotdot-big" title="${conGroup.groupName}"><input type="radio" name="groupName" id="consGroup${conGroup.id}" value="${conGroup.id}"> ${conGroup.groupName}</li>
   </c:forEach>
   </ul>
   <div class="clearfix mt5">
   <button class="btn btn-primary pull-right"  type="button" data-dismiss="modal" id="addContactsToGroup" onclick="refercontactListToGroup();">Add to Group</button>
   <button type="button" class="btn mr btn-default btn-sm pull-right" onClick="removeGroupsPopUp();">Cancel</button>
   </div>
   </form>
</div>
<!-- Create Label div html -->
  	<div class="comMask" onClick="maskFade();"></div>
  	<div class="createLabel-popdiv">
  	  <div id="addGroupDiv"> </div> 
  		<div id="groupEditDiv"></div>
  	</div>
  	
  <!-- Create Label div html -->

<script>



function addContactsGroup(){
	var result = "";
	result = result + '<form id="contactgroup_form"  name="contactgroup_form">'; 
	result = result + '<div class="form-group" id="groupNameDiv">';
	result = result + '	<label class="small text-muted">Group Name *</label>';
    result = result + '		<input type="text" name="groupName" id="groupName" field-name="Group Name" class="form-control input-sm" data-validation="required validate_Space validate_Alphanumeric validate_length length1-100" onblur="validateGroupName();">';
    result = result + '		<div class="underline"></div>';
	result = result + '</div >';
	result = result +'<div class="clearfix"></div >';
	result = result + '<div class="form-group">';
	result = result + '<div class="pull-right">';
     result = result + '	<button type="button" class="btn mr btn-default btn-sm" onClick="maskFade();">Cancel</button>';
	 result = result + '	<button type="button" value="Save" onclick= "contactgrp();" id="add_contactgroup" class="btn btn-sm btn-primary">Save</button>';
	 result = result + '</div >';
	 result = result + '</div >';
	 result = result + '</form>';
	 
	 $("#addGroupDiv").empty();
     $("#groupEditDiv").empty();
	 $("#addGroupDiv").append(result)
	 
	 $(".comMask").fadeIn();
	 $(".createLabel-popdiv").fadeIn();
	 $('#addcontacttogroup_form').reset();
}

</script>
<jsp:include page="../masterFooter.jsp" />
<div class="backtotop" style="display: none;"><img src="/resources/images/backtotop.png"></div> 


<script>

function refercontactList(){
	    var result = "";
	 	result = result +" <div class='remove_search'>";
	 	result = result +" <input type='text' placeholder='Search' id='contactsDataTableSearch'/>";
	 	result = result +"</div>";
	 	
	    result = result +'<div class="clearfix">';
  		result = result +'<table id="example" class="display" cellspacing="0" width="100%"  >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +'<th><input type="checkbox" id="checkReferContacts" onclick="toggleContactsGroup(this);"></th>';
		result = result +'<th>Contact Name</th>';
		result = result +'<th>Email</th>';
		result = result +'<th>Groups</th>';
		result = result +'<th>Phone</th>';
		result = result +'<th>Source</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    
	    result = result +'</div>'; 
    	result = result +'<div class="clearfix">';
    	result = result +'</div>';
	    $("#refercontactListDiv").empty();
	    $("#refercontactListDiv").append(result);
	    contactsList = {};
	    contactsDataTableList();
}
</script>


<script>
function contactsDataTableList(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 
	 var groupIdsList = [];
		$('input:checkbox[name=groupsFilter]:checked').each(function() 
	    {
			groupIdsList.push($(this).val());
	    });
		
	 
	 var searchValue = $("#contactsDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/getRefercontactList?searchValue="+searchValue+"&groupIdsList="+groupIdsList;
	 contactsPagination(url);
}
</script>

<script>
var contactsList = {};
function checkContactList(ele){
	if($(ele).is(":checked")) {
		contactsList[$(ele).data("id")] = $(ele).val();
	}else {
		delete contactsList[$(ele).val()];
	}
}
</script>


<script>
function toggleContactsGroup(source){
	
	
	var checkboxes = document.querySelectorAll('input[class="myGroupCheckbox"]');
	if($(source).is(":checked"))
		  $('input[class="myGroupCheckbox"]').prop('checked', 'checked');
	else 
		  $('input[class="myGroupCheckbox"]').removeProp('checked');

	$('input[class="myGroupCheckbox"]').each(function(i) {
		  if($(this).is(":checked")) {
			  contactsList[$(this).data("id")] = $(this).val();
		  } else {
			  contactsList[$(this).data("id")] = "";
		  }
	});
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
        	{ "data": "contactName" },
        	{ "data": "email"},
        	{ "data": "groups" },
            { "data": "source"}
           
        ],
        "columnDefs": [{
    	  	"targets": colIndex++,
    	  	"data": "ischeck",
    	  	"render": function(data, type, opt, meta) {
    	  		 var result = "";
    	  		 var flag = false;
    	  		
    	  		for(var e in contactsList) {
	   	  			 if(opt.id+"" === contactsList[e]) {
	   	  				 flag = true;
	   	  				 break;
	   	  			 }
	   	  		 } 
    	  		result = result + '<td><input'+(flag ? " checked='checked'" : "")+' type="checkbox" onchange="checkContactList(this);"    class="myGroupCheckbox" id="'+opt.id+'" value="'+opt.id+'" data-id="'+opt.id+'" name="contactsList[]" ></td>';
    	  		  return result;
    	  	}
         },{
	    	  	"targets": colIndex++,
	    	  	"data": "contactName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		  result = result + '<td data-title="Id"><div class="tbldata-dotdot"  title="'+opt.fullName+'">'+opt.fullName+'</div></td>';
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "email",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  result = result + '<td data-title="Id"><div class="tbldata-dotdot" title="'+opt.email+'">'+opt.email+'</div></td>';
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
              "data": "phone",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  var phone = "";
            	  if(opt.phone != null && opt.phone != '' && opt.phone != 'undefined'){
            		  phone = opt.phone;
            	  }else{
            		  phone = '------';
            	  }
            	  result = result + '<td data-title="Id"><div class="tblminwidth-50" title="'+phone+'">'+phone+'</div></td>';
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
    $("#example").wrap("<div class='project_tabs_inn_table vendor_wrap_inn_table' style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#contactsDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   contactsDataTableList();
    	   }
    	});
}



</script>




<script>
	function predefineProfileImage(imageId){
		$(imageId).attr('src','/resources/images/userprofileImg.jpg');
	}
</script>

<script>
function refercontactpagenation(){
	
	  $('#refercontactpagenationid').DataTable( { 
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
function validateContactEmail(){
	var contactEmail = document.getElementById("email").value;
	if(contactEmail){
		  $("#add_contact").attr("disabled", "disabled");
			$.ajax({
			   	type: "GET",
			       url: sourceleadUrl+"/validateContactEmail?${_csrf.parameterName}=${_csrf.token}&contactEmail="+contactEmail,
			       success: function(data) {
			    		if(data != null && data != ''){
						     if(data == 'Invalid'){
						    	    $('#emailDiv').find('p.jquery_form_error_message').remove(); 
						    	    $("input#email").after("<p class='jquery_form_error_message'>"+contactEmail+" is already added as Contact</span>");
				    				$('input#email').attr('record-exist','yes');
				    				$('input#email').attr('record-exist-errorMsg',contactEmail+' is already added as Contact'); 
						     } 
						     
					       	else{
					       		 $("#add_contact").removeAttr("disabled");
								 $('input#email').removeAttr( "record-exist record-exist-errormsg");
				        		 $('#emailDiv').find('p.jquery_form_error_message').remove(); 
								 $( "#add_contact" ).removeAttr("style");
								 $( "#add_contact" ).click(function() {
								 if ($(this).validate(false, validationSettings)){
								 }
								});
					       	}
				       	}
			    	   
			      },
			       
			}); 
	}
}
</script>


<script type="text/javascript">
function saveReferContacts(){
	var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
	   };  
	if ($('#addcontact_form').validate(false, validationSettings)){
  		$("#add_contact").attr("disabled", "disabled");
  		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	     var formData = getFormData1("addcontact_form");
		$.ajax({
					type : "POST",
					 data: formData,
	  		         processData: false,
	  		         contentType:false, 
					url : sourceleadUrl+"/addCrowdSourcingUserContact?"+tokenParamName+"="+tokenValue+"",
					success : function(result) {
						location.replace(sourceleadUrl+ "/referRequirementList");
					},
				});
    
	    return true;
}
}
	
         /* $('body').on('submit', '#addcontact_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
	          		
	          		$("#add_contact").attr("disabled", "disabled");
	          		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	        	     var formData = getFormData1("addcontact_form");
	        		$.ajax({
	        					type : "POST",
	        					 data: formData,
	        	  		         processData: false,
	        	  		         contentType:false, 
	        					url : sourceleadUrl+"/addCrowdSourcingUserContact?"+tokenParamName+"="+tokenValue+"",
	        					success : function(result) {
	        						location.replace(sourceleadUrl+ "/referRequirementList");
	        					},
	        				});
                
            	    return true;
            }
            else{
            	return false;	
               }
            }); */
         $('#addcontact_form').showHelpOnFocus();
         $('#addcontact_form').validateOnBlur(false, validationSettings); 
            
     
</script> 
<script>
function flushContactDetails(){
	$('#firstName').val('');
	$('#lastName').val('');
	$('#email').val('');
	$('#phone').val('');
	$('#firstNameDiv').find('p.jquery_form_error_message').remove();
	$('#lastNameDiv').find('p.jquery_form_error_message').remove();
	$('#emailDiv').find('p.jquery_form_error_message').remove();
	$('#phoneDiv').find('p.jquery_form_error_message').remove();
}
</script>
<script type="text/javascript">
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
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
    $("#addPop").click(function(){
		$(this).parent("li").find(".referWrap").toggleClass("referWrapActive");
		$(".side_mask").fadeOut(200);
		$(".impConPop").fadeOut(100);
	});
    $(".canBtn").click(function(){
		$(".referWrap").removeClass("referWrapActive");
	});		
</script> 
	
   <div class="side_mask"></div>
   <div class="impConPop">
      <div id="registerPopClose" class="pavClose" onclick="closePop();"><i class="fa fa-times"></i></div>
      <div class="socialinviter" type="contactimporter"></div>
   </div>
<script>	
	$("#imCon").click(function(){
		$(".referWrap").removeClass("referWrapActive");
		$(".side_mask").fadeIn(200);
		$(".impConPop").fadeIn(100);
	});
</script> 
<script>
    function closePop(){
    	$(".side_mask").fadeOut(200);
		$(".impConPop").fadeOut(100);
    }
</script>
<script>
// Back To Top 
    $(window).scroll(function() {
        if ($(this).scrollTop() > 200) {
            $('.backtotop').fadeIn('slow');
        } else {
            $('.backtotop').fadeOut('slow');
        }
    });
    $('.backtotop').click(function() {
        $("html, body").animate({scrollTop: 0}, 500);
        return false;
    });
</script>
<script>
//Filter Script
$("#contactsFilter").click(function(){
			$("#contactsFilterPop").slideToggle(200);
			$(".export_pop").slideUp(200);
		});
	    $("html").click(function() {
	        $("#contactsFilterPop").slideUp(200);
	    });
	    $("#contactsFilterPop, #contactsFilter").click(function(e) {  
	        e.stopPropagation();
	    });	
</script>
<!-- Add Group Script Starts Here -->


<script type="text/javascript">
	var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
	   };  
	function contactgrp(){
        	  if ($('#contactgroup_form').validate(false, validationSettings)){
	          		$("#add_contactgroup").attr("disabled", "disabled");
	          		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	        	     var formData = getFormData("contactgroup_form");
	        		$.ajax({
	        					type : "POST",
	        					data: formData,
	        					url : sourceleadUrl+"/addContactsGroup?"+tokenParamName+"="+tokenValue+"",
	        					success : function(result) {
	        						location.replace(sourceleadUrl+"/referRequirementList");
	        					},
	        				});
                
            	    return true;
            }
            else{
            	return false;	
               }
	}
	$('body').on('blur', '#contactgroup_form', function() {
		$("#contactgroup_form").showHelpOnFocus();
		$("#contactgroup_form").validateOnBlur(false, validationSettings);
		
	});   
     
</script> 
<script>
function getContactsGroupList(){  
	  $.ajax({
		  type : "POST",
            url: sourceleadUrl+"/getContactsGroupList?"+tokenParamName+"="+tokenValue+"",       
            success :function(json) {
			    	     $("#groupList").removeAttr("style");
	                	 $("#groupList").empty();
	                	 var result = "";
	                	 result = result +"<table id='' class='display mgList' cellspacing='0' width='100%' >"; 
	                     result = result +"<tbody>"; 
	                     
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
	                    	 result = result +"<tr id='deleteGroup"+opt.id+"'>";
				             
				             if(opt.groupName != null && opt.groupName != '' && opt.groupName != 'undefined'){
				             result = result +"<td><div class='tbldata-dotdot-sm' title='"+opt.groupName+"'>"+opt.groupName+"</div></td>";
				             }else{
				            	 result = result +"<td>-----</td>";
				             }
				             result = result +'<td><i class="glyphicon glyphicon-pencil paddingRight" title="Edit" onclick="editContactsGroup('+opt.id+',\''+opt.groupName+'\');"></i></td>';
				             result = result +'<td><i class="glyphicon glyphicon-trash paddingLeft" title="Delete" onclick="deleteContactsGroup('+opt.id+',\''+opt.groupName+'\');"></i></td>'; 
				             result = result +"</tr>";
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#groupList").empty();
	                     $("#groupList").append(result); 
	                    
          },
            
       }); 
  } 
</script>

<script type="text/javascript">
function editContactsGroup(groupId,groupName){
	        	var outPut = '';
	        	outPut = outPut+'<form role="form"  id="contactgroup_form" name="contactgroup_form" method="post">';
	        	outPut = outPut+ '<div class="form-group" id="groupNameDiv">';
	        	outPut = outPut +'<input type="hidden" class="form-control input-sm" name="groupId"  value="'+groupId+'">';
	        	outPut = outPut+ '<label class="small text-muted">Group Name *</label>';
	        	outPut = outPut+ '<div class="clearfix positionRelative">';
	        	outPut = outPut+ '<input type="text" name="groupName" id="groupName" value = "'+groupName+'" field-name="Group Name" class="form-control input-sm" data-validation="required validate_Space validate_Alphanumeric validate_length length1-100" onblur="validateGroupName();">';
	      				
	        	outPut = outPut+ '<div class="underline">';
	        	outPut = outPut+ '</div>';
	        	outPut = outPut+ '</div>';
	        	outPut = outPut+ '</div>';
	        	outPut = outPut+ '<div class="form-group">';
	        	outPut = outPut+ '<div class="pull-right">';
	        	outPut = outPut+ '<button type="button" class="btn mr btn-default btn-sm" onClick="maskFade();">Cancel</button>';
	        	outPut = outPut+ '	<button type="submit" value="Save" id="edit_contactgroup" onclick= "contactgrp();" class="btn btn-sm btn-primary">Save</button>';
				outPut = outPut+ '</div>';
	        	outPut = outPut+ '</div>';
	        	outPut = outPut+ '</form>';
	        	
	            $("#addGroupDiv").empty();
	            $("#groupEditDiv").empty();
	        	$("#groupEditDiv").append(outPut);
	        	$(".comMask").fadeIn();
	        	$(".createLabel-popdiv").fadeIn();
	        	 
	        	$('#addcontacttogroup_form').reset();
}
</script>


<script>
function deleteContactsGroup(groupId,groupNmae){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure to delete <strong>"+groupNmae+"</strong> ?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteSelectedContactsGroup("+groupId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#deleteGroupPopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
	$('#addcontacttogroup_form').reset();
	
}
</script>  

<script type="text/javascript">
function cancel(){
	$("#deleteGroupPopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<script>
function deleteSelectedContactsGroup(groupId){
	 if (groupId) {
		 
	     $("#deleteGroupPopUp").empty();
		 $(".project_inn_mask").fadeOut(200);
			
		 $("#deleteGroup"+groupId).html("<td colspan='5'  id='loading"+groupId+"'></td>");
		 
		 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/deleteContactsGroupById?"+tokenParamName+"="+tokenValue+"&groupId="+groupId, 
   		    success: function(data) {
   		    	
   		    	if(data != null && data != 'undefined' && data != ""){
   		    		    $("#loading"+groupId).removeAttr("style");
             			document.getElementById("deleteGroup"+groupId).remove();
             			location.replace(sourceleadUrl+"/referRequirementList");
   		    	}
   		    }
	     }); 
	} 
}
</script>


<script>
	function showLable(){
	$(".manageLables-div").fadeIn();
	}
	
	$(".closemld").click(function(){
	$(".manageLables-div").fadeOut();
	});
</script>
	
<script>
function addLabelPop(){
	addContactsGroup();
};
function maskFade(){
	 $(".comMask").fadeOut();
	$(".createLabel-popdiv").fadeOut();
	}
	$('ul.dropdown-menu').on('click', function(event){
    event.stopPropagation();
   });
  $('.dropdown').on('click', function(){
		$(".manageLables-div").fadeOut();
	});
</script>


 <script>
function refercontactListToGroup(){
	var groupId = $("input:radio[name=groupName]:checked").val()
	var contactsIdsList = new Array();
	 for(var e in contactsList) {
		 if(!e || !contactsList[e] || contactsList[e] === "")
			   continue;
		 contactsIdsList.push(contactsList[e]);
	 }
	var sourceleadUrl = '${sourceleadUrl}';
	if(groupId && contactsIdsList.length > 0){
		 var formData = getFormData1("addcontacttogroup_form");
		 	 $.ajax({
				 type : "POST",
				 data: formData,
				 processData: false,
  		         contentType:false, 
				url : sourceleadUrl+"/refercontactListToGroup?"+tokenParamName+"="+tokenValue+"&groupId="+groupId+"&contactsList="+contactsIdsList,
				success : function(result) {
					if(result == "success"){
						
						location.replace(sourceleadUrl+"/referRequirementList");
					} 
				},
			});  
		
	}else{
		if(contactsIdsList.length == 0){
			groupAndContacsAlertMsg('Select atleast one contact');
		}else{
			groupAndContacsAlertMsg('Please select group');
		}
		
	} 
}
</script>


<script>
function groupAndContacsAlertMsg(message){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancelpopUp();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelpopUp();'></b>";
	result = result +"	</div>";
	$("#contactsPopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}


function cancelpopUp(){
	$("#contactsPopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<script>
function selectedContactList(){
	var contactsList = [];
	 $("input[name='contactsList[]']:checked").each( function (i) {
		 contactsList[i] = $(this).val();
	});
	 
	 if(contactsList.length > 0){
			 var sourceleadUrl ='${sourceleadUrl}';
				 $.ajax({
				    	type: "POST",
				        url: sourceleadUrl+"/getGroups?"+tokenParamName+"="+tokenValue+"", 
				        success: function(data) {
				        	var json = JSON.parse(data);
				        	var output = "";
				        	output = output + "<option value=''>-- Add To Group --</option>"; 
				        	for (var i=0; i<json.length; i++)
							{
					        		var opt = json[i];
					        		output = output + "<option value='" +opt.id+ "'>"+opt.groupName+"</option>";
							}
				        	$("#consGroup").empty();
					        $("#consGroup").append(output);
					        $('#consGroup').trigger("chosen:updated");
				        },
				    });
		 
		 document.getElementById("groupDiv").style.display="";
	 }else{
		 document.getElementById("groupDiv").style.display="none";
		 
		 var output = "";
     	 output = output + "<option value=''>-- Add To Group --</option>"; 
     	 $("#consGroup").empty();
	     $("#consGroup").append(output);
	     $('#consGroup').trigger("chosen:updated");
		 
	 }
		
}
</script>


<script type="text/javascript">
 function validateGroupName(){
	 var groupName = document.getElementById("groupName").value;
	 var groupId = '${groupId}';
	 $('#groupNameDiv').find('p.jquery_form_error_message').remove();
	 if(groupName != null && groupName != "" && groupId != null){
		
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/validateGroupName?"+tokenParamName+"="+tokenValue+"&groupName="+groupName+"&groupId="+groupId, 
		        success: function(data) {
		        	if(data == 'Invalid'){
		        		  $('#groupNameDiv').find('p.jquery_form_error_message').remove();
						  $("input#groupName").after("<p class='jquery_form_error_message'>"+groupName+" is Already Existed</span>");
						  document.getElementById("groupName").setAttribute('record-exist','yes');
						  document.getElementById("groupName").setAttribute('record-exist-errorMsg',groupName+' is Already Existed'); 
		        	}
		        	else{
		        		 $('input#groupName').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#groupNameDiv').find('p.jquery_form_error_message').remove(); 
		        		 $("#add_contactgroup" ).addClass('button').val('Save');
                         $("#add_contactgroup").removeAttr("disabled");
		        	}
		        },
		    });
	 }
 }
 </script>
 
 
 
 <script>
 function removeGroupsPopUp(){
	 $(".movefolder-popdiv").fadeOut();
	 $(".sideMask").fadeOut();
 }
 
 function movefolderPop() {
	 $(".movefolder-popdiv").fadeIn();
	 $(".sideMask").fadeIn();
	 $("#add_group_Div").hide();
	 
 }
 $(".close").click(function(){
	 $(".movefolder-popdiv").fadeOut();
	 $(".sideMask").fadeOut();
 });
 $(".sideMask").click(function(){
	 $(".movefolder-popdiv").fadeOut();
	 $(".sideMask").fadeOut();
 });
 
 $("#connectionsId").click(function(){
	 $("#contactsListSheet").hide();
	 $("#contactsListUrl").hide();
	 $("#connectionsUrl").show();
	 $("#connnectionsSheet").show();
	 connectionsList();
	 
 });
 $("#contactsListId").click(function(){
	 $("#contactsListUrl").show();
	 $("#connectionsUrl").hide();
	 $("#contactsListSheet").show();
	 $("#connnectionsSheet").hide();
 });
 
 </script>
