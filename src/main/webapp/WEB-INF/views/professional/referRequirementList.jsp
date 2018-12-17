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
    <div class="success_pop" id="selector" style="display: none;"><i class="fa fa-check-circle"></i><span id="msgDiv"></span></div>
    <div class="pull-left"><h2>Contacts List</h2></div> 
    <div class="pull-right brud-crum" id="contactsListUrl"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Contacts List</div>
</section>
  
<div class="project_wrap" id="contactsListSheet">


<div class="positionRelative reportHead clearfix">
<span class="pull-left refreIcon"><a href="javascript:void(0)" class="refeIcon" title="Refresh" onclick="resetContactFilter();"></a></span>
      <ul class="pull-right list-unstyled list-inline">
      		<li><a href="javascript:void(0)" class="folderIcon mr5" title="Move Folder" onclick="movefolderPop();"></a></li>
      		<li><a href="javascript:void(0)" class="manFolderIcon mr5" title="Manage Group" onClick="getContactsGroupList();showLable();"></a></li>
			<li>
				<a class="action newAdBtn" onclick="getActions();" href="javascript:void(0)">+ Add <i class="fa fa-caret-down"></i></a>
				<div class="action-pop">
					<span> 
					<a href="javascript:void(0)" id="addPop">Add Contact</a>
					<a href="javascript:void(0)" id="imCon">Import Contacts</a>
					</span>
				</div>
			</li>
			<li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="filterPop();"></a></li>
		  <li class=""><input type="text" class="repSearchBar" placeholder='Search' id='contactsDataTableSearch'/><span class="repSearchIcon" onClick=""></span></li>
        </ul>
      </div>
        
      <div id="refercontactListDiv" class="datatable_filter"></div>
      </div>
	<div id="connnectionsSheet" class="conneWrap"></div>
</section>

<div id="deleteGroupPopUp"></div>
<div id="contactsPopUp"></div>

<div class="popupWrap popupWrapFixed" id="movefolder-popdiv">
<form id="addcontacttogroup_form"  name="addcontacttogroup_form">
	<div class="popupHead"><span>Move Folder</span><i class="closePop pull-right" onClick="removeGroupsPopUp();"></i></div>
	<div class="popupContent popupContentFixed">
		<ul>
		<c:forEach var="conGroup" items="${contactsGroupList}">
			<li title="${conGroup.groupName}"><input type="radio" name="groupName" id="consGroup${conGroup.id}" value="${conGroup.id}"><span> ${conGroup.groupName}</span></li>
	   </c:forEach>
	   </ul>
   </div>
   <div class="popupFoot text-right">
  	 	<button class="slBtn md colorBtn"  type="button" id="addContactsToGroup" onclick="refercontactListToGroup();">Add to Group</button>
   		<button type="button" class="slBtn md popClose" onClick="removeGroupsPopUp();">Cancel</button>
   </div>
   </form>
</div>

<div class="popupWrap popupWrapFixed" id="add_group_Div">
	 <div class="popupHead"><span>Manage Group</span><i class="closePop pull-right closemld"></i></div>
	<div class="popupContent">
		<div id="groupList"></div>
	</div>
	<div class="popupFoot text-right">
		<button type="button" class="slBtn md colorBtn" onClick="addLabelPop();">Add Group</button>
		<button type="button" class="slBtn md closemld">Cancel</button>
	</div>
	
</div> 

<!-- Create Label div html -->
 <div class="popupWrapZin" id="createLabel-popdiv">
  	 <div id="addGroupDiv"> </div> 
	 <div id="groupEditDiv"></div>
</div>
  	
  <!-- Create Label div html -->

<form:form id="addcontact_form"  name="addcontact_form" method="post"> 
	<div class="popupWrap popupWrapFixed" id="addConPop">
	<div class="popupHead"><span>Add Contact</span><i class="closePop pull-right canBtn"></i></div>
	<div id="loading"></div> 
	  <div class="popupContent popupContentFixed">
		<div class="clearfix mb" id="firstNameDiv">
		  <label class="clearfix">First Name <b class="valStar">*</b></label>
		  <div class="clearfix">
			<input type="text" class="form-control margin-top0" name="firstName" id="firstName" field-name="First Name" data-validation="required validate_Space validate_Alphanumeric validate_length length1-100">
			<span class="hidden"></span> 
		  </div>
		</div> 
		
		<div class="clearfix mb" id="lastNameDiv">
		  <label class="clearfix">Last Name</label>
		  <div class="clearfix">
			<input type="text" class="form-control margin-top0" name="lastName" id="lastName" field-name="Last Name" data-validation-optional="true" data-validation="validate_Space validate_Alphanumeric validate_length length1-100">
			<span class="hidden"></span> </div>
		</div> 
		
		<div class="clearfix mb" id="emailDiv">
		  <label class="clearfix">Email Id <b class="valStar">*</b></label>
		  <div class="clearfix">
			<input type="text" class="form-control margin-top0" name="email" id="email" onblur="validateContactEmail()"  field-name="Email" data-validation="required validate_Space validate_length length2-100 validate_email">
			<span class="hidden"></span> 
		 </div>
<!-- 		 <div class="col-md-2 padding_left">
		 <div class="input-group-addon input-groupaddon-custom" id="emailStatusIconDiv" >
	                 <i class="fa fa-check text-muted" style="color:#f6f6f6"></i>
	                 </div>
	              </div> -->
		</div> 
		
		<div class="clearfix mb" id="phoneDiv">
		  <label class="clearfix">Phone </label>
		  <div class="clearfix" >
			<input type="text" class="form-control margin-top0"  name="phone" id="phone" field-name="Phone" data-validation-optional="true" data-validation="validate_Space validate_phone validate_length length9-20">
			<span class="hidden"></span> </div>
		</div> 
		
		<div class="clearfix mb">
				<label class="clearfix" >Contact Type</label>
				<div class="clearfix select-field">
					<select class="form-control margin-top0" name="contactType" id="contactType"  field-name="contactType">
						<option value="">Select ContactType</option>
						<option value="Vendor">Vendor</option>
						<option value="Candidate">Candidate</option>
						<option value="Client">Client</option>
						<option value="Other">Other</option>
					</select> <span class="hidden">Select Status</span>
				</div>
			</div>
			
		<div class="clearfix mb" id="companyName">
		  <label class="clearfix">Company Name</label>
		  <div class="clearfix">
			<input type="text" class="form-control margin-top0" name="companyName" id="companyName" field-name="companyName" >
			<span class="hidden"></span> </div>
		</div> 
		
		 </div>
		 
		<div class="popupFoot text-right">            
		  <input type="button" value="Add Contact" id="add_contact" onclick="saveReferContacts();" class="slBtn md colorBtn">
		  <input type="button" value="Cancel" onclick="flushContactDetails()" class="slBtn md canBtn">
		</div> 
	 
	 </div> 
	</form:form>

<script>
function addContactsGroup(){
	var result = "";
	result = result + '<form id="contactgroup_form"  name="contactgroup_form">'; 
	result = result + '<div class="popupContent" id="groupNameDiv">';
	result = result + '		<label class="noPad">Group Name <b class="valStar">*</b></label>';
    result = result + '		<input type="text" name="groupName" id="groupName" field-name="Group Name" class="form-control" data-validation="required validate_Space validate_Alphanumeric validate_length length1-100" onblur="validateGroupName();">';
    result = result + '		<div class="underline"></div>';
	result = result + '</div >';
	result = result + '<div class="popupFoot text-right">';
	result = result +'<input type="button" value="Save" id="add_contactgroup" onClick="contactgrp()" class="slBtn md colorBtn">';
	result = result +'<input type="button" value="Cancel" onClick="maskFade()" class="slBtn md">';
	 result = result + '</div >';
	 result = result + '</form>';
	 
	 $("#addGroupDiv").empty();
     $("#groupEditDiv").empty();
	 $("#addGroupDiv").append(result)
	 
	 $(".maskTwo").show();
	 $("#createLabel-popdiv").show();
	 $('#addcontacttogroup_form').reset();
}

</script>
<jsp:include page="../masterFooter.jsp" />
<div class="backtotop" style="display: none;"><img src="/resources/images/backtotop.png"></div> 


<script>

function refercontactList(){
	    var result = "";
/* 	 	result = result +" <div class='remove_search'>";
	 	result = result +" <input type='text' placeholder='Search' id='contactsDataTableSearch'/>";
	 	result = result +"</div>"; */
	 	
	    result = result +'<div class="clearfix">';
  		result = result +'<table id="example" class="display" cellspacing="0" width="100%"  >';
		result = result + '<thead>';
		result = result + '<tr>';
// 		result = result +'<th><input type="checkbox" id="checkReferContacts" onclick="toggleContactsGroup(this);"></th>';
		result = result +'<th>Contact Name</th>';
		result = result +'<th>Email</th>';
		result = result +'<th>Groups</th>';
		result = result +'<th>Phone</th>';
		result = result +'<th>Source</th>';
		result = result +'<th>Company Name</th>';
		result = result +'<th>Action</th>';
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
		
	 var contactTypes = [];
		$('input:checkbox[name=contactTypename]:checked').each(function() 
	    {
			contactTypes.push($(this).val());
	    });
	 
	 var searchValue = $("#contactsDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/getRefercontactList?searchValue="+searchValue+"&groupIdsList="+groupIdsList+"&contactType="+contactTypes;
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
            { "data": "source"},
            { "data": "contactType"},
        	{ "data": "contactAction"}
        ],
        "columnDefs": [/* {
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
         }, */{
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
	            	    if(opt.source != null && opt.source != '' && opt.source != 'undefined'){
	            	    	source = opt.source;
	              	  }else{
	              		source = '---';
	              	  }
	            		result = result + '<td><div class="tblminwidth-75"><span title="'+source+'">'+source+'</span></div></td>';
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "companyName",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    var contactType = "";
	              	  if(opt.companyName != null && opt.companyName != '' && opt.companyName != 'undefined'){
	              		companyName = opt.companyName;
	              	  }else{
	              		companyName = '---';
	              	  }
	            		result = result + '<td><div class="tblminwidth-75"><span title="'+companyName+'">'+companyName+'</span></div></td>';
	            		return result;
	              }
		     },{
	              "targets": colIndex++,
	              "data": "contactAction",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	               
	              	var result = "";
	              	result = result	+ '<td><div class="tbldata-dotdot_50" >';
	              	result = result	+ '<a class="fa fa-pencil" title="Edit" onClick="editContactType('+opt.id+');" ></a> <a class="fa fa-trash text-danger" title="Delete" onclick="deleteContactType('+opt.id+');"></a>';
	              	result = result	+ '</div></td>';
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
						     }else{
					       		 $("#add_contact").removeAttr("disabled");
								 $('input#email').removeAttr( "record-exist record-exist-errormsg");
				        		 $('#emailDiv').find('p.jquery_form_error_message').remove(); 
								 $( "#add_contact" ).removeAttr("style");
								 validateEmailForExistenceUniversally(email, "input#email");
								 $( "#add_contact" ).click(function() {
								 if ($(this).validate(false, validationSettings)){
								 }
								});
								
					       	}
				       	}
			      },
			       
			}); 
	}else{
		$("#emailStatusIconDiv").find("i").remove();
  		$("#emailStatusIconDiv").append('<i class="fa fa-check text-muted" style="color:#f6f6f6"></i>');
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
			var formData = getFormData1("addcontact_form");
	  		$("#add_contact").attr("disabled", "disabled");
	  		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.ajax({
					type : "POST",
					 data: formData,
	  		         processData: false,
	  		         contentType:false, 
					url : sourceleadUrl+"/addCrowdSourcingUserContact?"+tokenParamName+"="+tokenValue+"",
					success : function(result) {
						
						if (result && result.response === "INVALID_DATA") {
						    showError(result.responseMessage);
						    $("#loading").hide();
						} else if (result.response === "AWKWARD") {
						    location.href = "/errorPage";
						} else if (result.response === "SUCCESS") {
							location.replace(sourceleadUrl+ "/referRequirementList");
						} else if (result.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
						    showError("Operation failed, please try again");
						}
						
					},
				});
    
	    return true;
		}else{
			return false;
		}
}
$('body').on('blur', '#addcontact_form', function() {
$("#addcontact_form").showHelpOnFocus();
$("#addcontact_form").validateOnBlur(false, validationSettings);  
 });
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
		$("#addConPop").fadeIn(100);
		$(".mask").show();
	});
    $(".canBtn").click(function(){
		$("#addConPop").fadeOut(100);
		$(".mask").hide();
	});		
</script> 
	
   <div class="impConPop">
      <div id="registerPopClose" class="pavClose" onclick="closePop();"><i class="fa fa-times"></i></div>
      <div class="socialinviter" type="contactimporter"></div>
   </div>
<script>	
	$("#imCon").click(function(){
		$(".referWrap").removeClass("referWrapActive");
		$(".mask").show();
		$(".impConPop").fadeIn(100);
	});
</script> 
<script>
    function closePop(){
    	$(".mask").hide();
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
	        				if (result && result.response === "INVALID_DATA") {
							    showError(result.responseMessage);
							} else if (result.response === "AWKWARD") {
							    location.href = "/errorPage";
							} else if (result.response === "SUCCESS") {
								location.replace(sourceleadUrl+"/referRequirementList");
							} else if (result.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
							    showError("Operation failed, please try again");
							}
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
				             result = result +"<td><div class='tbldata-dotdot-big' title='"+opt.groupName+"'>"+opt.groupName+"</div></td>";
				             }else{
				            	 result = result +"<td>-----</td>";
				             }
				             result = result +'<td><i class="fa fa-pencil paddingRight mr5" title="Edit" onclick="editContactsGroup('+opt.id+',\''+opt.groupName+'\');"></i>';
				             result = result +'<i class="fa fa-trash paddingLeft" title="Delete" onclick="deleteContactsGroup('+opt.id+',\''+opt.groupName+'\');"></i></td>'; 
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
	        	outPut = outPut+ '<div class="popupContent" id="groupNameDiv">';
	        	outPut = outPut +'<input type="hidden" class="form-control input-sm" name="groupId"  value="'+groupId+'">';
	        	outPut = outPut+ '<label class="noPad">Group Name <b class="valStar">*</b></label>';
	        	outPut = outPut+ '<div class="clearfix positionRelative">';
	        	outPut = outPut+ '<input type="text" name="groupName" id="groupName" value = "'+groupName+'" field-name="Group Name" class="form-control input-sm" data-validation="required validate_Space validate_Alphanumeric validate_length length1-100" onblur="validateGroupName('+groupId+');">';
	        	outPut = outPut+ '</div>';
	        	outPut = outPut+ '</div>';
	        	outPut = outPut+ '<div class="popupFoot text-right">';
	        	outPut = outPut +'<input type="button" value="Save" id="add_contactgroup" onClick="contactgrp()" class="slBtn md colorBtn">';
	        	outPut = outPut +'<input type="button" value="Cancel" onClick="maskFade()"  class="slBtn md">';
				outPut = outPut+ '</div>';
	        	outPut = outPut+ '</form>';
	            $("#addGroupDiv").empty();
	            $("#groupEditDiv").empty();
	        	$("#groupEditDiv").append(outPut);
	        	$(".maskTwo").show();
	        	$("#createLabel-popdiv").show();
	        	$('#addcontacttogroup_form').reset();
}
</script>


<script>
function deleteContactsGroup(groupId,groupNmae){
	var result = "";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure to delete <strong>"+groupNmae+"</strong> ?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteSelectedContactsGroup("+groupId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#deleteGroupPopUp").append(result);
	$("#remove_user_pop").fadeIn(100);
	$(".maskTwo").show();
	$('#addcontacttogroup_form').reset();
	
}
</script>  

<script type="text/javascript">
function cancel(){
	$("#deleteGroupPopUp").empty();
	$(".maskTwo").hide();
}
</script>

<script>
function deleteSelectedContactsGroup(groupId){
	 if (groupId) {
	     $("#deleteGroupPopUp").empty();
		 $(".maskTwo").hide();
		 $("#deleteGroup"+groupId).html("<td colspan='5'  id='loading"+groupId+"'></td>");
		 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/deleteContactsGroupById?"+tokenParamName+"="+tokenValue+"&groupId="+groupId, 
   		    success: function(data) {
   		    	
   		    	if (result && result.response === "INVALID_DATA") {
				    showError(result.responseMessage);
				} else if (result.response === "AWKWARD") {
				    location.href = "/errorPage";
				} else if (result.response === "SUCCESS") {
					 $("#loading"+groupId).removeAttr("style");
          			document.getElementById("deleteGroup"+groupId).remove();
          			location.replace(sourceleadUrl+"/referRequirementList");
				} else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
				    showError("Operation failed, please try again");
				}
   		    }
	     }); 
	} 
}
</script>

<script>
	function showLable(){
		$(".mask").show();
		$("#add_group_Div").fadeIn(100);
	}
	$(".closemld").click(function(){
		$(".mask").hide();
		$("#add_group_Div").hide();
	});
</script>
	
<script>
function addLabelPop(){
	addContactsGroup();
};
function maskFade(){
	$(".maskTwo").hide();
	$("#createLabel-popdiv").hide();
}
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
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancelpopUp();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelpopUp();'></b>";
	result = result +"	</div>";
	$("#contactsPopUp").append(result);
	$("#remove_user_pop").fadeIn(100);
	$(".maskTwo").show();
}


function cancelpopUp(){
	$("#contactsPopUp").empty();
	$(".maskTwo").hide();
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
 function validateGroupName(groupId){
	 if(groupId != null && groupId != "" && groupId != "undefined"){
		 groupId = groupId;
	 }else{
		 groupId = "";
	 }
	 var groupName = document.getElementById("groupName").value;
	 $('#groupNameDiv').find('p.jquery_form_error_message').remove();
	 if(groupName != null && groupName != "" && groupId != null){
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/validateGroupName?"+tokenParamName+"="+tokenValue+"&groupName="+groupName+"&groupId="+groupId, 
		        success: function(data) {
		        	if(data == true){
		        		  $('#groupNameDiv').find('p.jquery_form_error_message').remove();
						  $("input#groupName").after("<p class='jquery_form_error_message'>"+groupName+" is Already Existed</span>");
						  document.getElementById("groupName").setAttribute('record-exist','yes');
						  document.getElementById("groupName").setAttribute('record-exist-errorMsg',groupName+' is Already Existed'); 
						  $("#add_contactgroup").attr('disabled','disabled');
						  
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
function filterPop(){
	$("#contactFilterPopup").toggle();
	$(".mask").show();
};
function closeFilter(){
	$("#contactFilterPopup").toggle();
	$(".mask").hide();

}

function resetContactFilter(){
	$("ul#contactType > li input:checkbox").prop("checked",false);
	$("ul#contactgroup > li input:checkbox").prop("checked",false);
	$("#contactsDataTableSearch").val('');
	contactsDataTableList()
}
</script>

<!-- For filter -->

<div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search" id="contactFilterPopup">
<h5 class="headerAdsrch">Filter <a href="javascript:void(0)" class="close-div" onClick="closeFilter()"><i class="fa fa-close"></i></a></h5>
<div class="addInner">
<div class="row">

<div  class="col-md-2">
			<label><strong>Type</strong></label>
			<div class='clearfix'>
								<ul id="contactType" class="list-unstyled">
									<li class="checkBox positionRelative"><input id="c11" type="checkbox" name="contactTypename" value="Candidate" ><label for="c11">Candidate </label></li>
									<li class="checkBox positionRelative"><input id="c12" type="checkbox" name="contactTypename" value="Vendor" ><label for="c12">Vendor </label></li>
									<li class="checkBox positionRelative"><input id="c13" type="checkbox" name="contactTypename" value="Client" ><label for="c13">Client </label></li>
									<li class="checkBox positionRelative"><input id="c14" type="checkbox" name="contactTypename" value="Other" ><label for="c14">Other </label></li>
								</ul>
				</div>
							</div>
						
						<div  class="col-md-2">
						<label><strong>Filter Group</strong></label>
						<div class='clearfix'>	
				<ul id="contactgroup" class="list-unstyled" >
                    	  <c:forEach var="report" items="${contactsGroupList}">
                        	<li><label class="tbldata-dotdot-big" title="${report.groupName}"><input   name="groupsFilter" value ="${report.id}"  onclick="refercontactList();"  type="checkbox"> ${report.groupName}</label></li>
						  </c:forEach>
				</ul>
				</div>
				</div>	
				</div>			
							
							

			<div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Apply" id="search_requiremet" 
								onclick="closeFilter();contactsDataTableList();" class="btn btn-sm btn-success" />
							<input type="button" value="Reset" id="clear_search"
								onclick="resetContactFilter();" class="btn btn-sm btn-primary" />
						</div>
					</div>

</div>
</div> 
 <script>
 function movefolderPop() {
	 $("#movefolder-popdiv").fadeIn(100);
	 $(".mask").show();
 }
 function removeGroupsPopUp(){
	 $("#movefolder-popdiv").fadeOut(100);
	 $(".mask").hide();
 }
 
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
 <div id="popUpContactDiv" class="popupWrap popupWrapFixed"></div>
 <script>
 function editContactType(contactId){
	 $.ajax({	type : "POST",
			url : sourceleadUrl+ "/getContactById?"+tokenParamName+"="+tokenValue+"&id="+contactId,
			success : function(json) {
				var firstName = "";
				var lastName = "";
				var email = "";
				var contactType  = "";
				var companyName  = "";
				var phone  = "";
				
				if(json.firstName !=null && json.firstName !='undefined' && json.firstName !=''){
					firstName = json.firstName;
				}
				if(json.lastName !=null && json.lastName !='undefined' && json.lastName !=''){
					lastName = json.lastName;
				}
				if(json.email !=null && json.email !='undefined' && json.email !=''){
					email = json.email;
				}
				if(json.phone !=null && json.phone !='undefined' && json.phone !=''){
					phone = json.phone;
				}
				if(json.contactType !=null && json.contactType !='undefined' && json.contactType !=''){
					contactType = json.contactType;
				}
				if(json.companyName !=null && json.companyName !='undefined' && json.companyName !=''){
					companyName = json.companyName;
				}
				
				var result = "";
				
		result = result  +'<form id="editcontact_form"  name="editcontact_form" method="post"> ';
		result = result  +'<div class="popupHead"><span>Edit Contact</span><i class="closePop pull-right" onClick="closeEditContactDiv();"></i></div>';
		result = result  +'<div id="loading"></div>                                                                                                                                                                                               ';
		result = result  +'  <div class="popupContent popupContentFixed">                                                                                                                                                                                           ';
		result = result  +'	<div class="clearfix mb" id="firstNameDiv">                                                                                                                                                                   ';
		result = result  +'	  <label class="clearfix">First Name <b class="valStar">*</b></label>                                                                                                                                                                       ';
		result = result  +'	  <div class="clearfix">                                                                                                                                                                                             ';
		result = result  +'		<input type="text" class="form-control" name="firstName" id="firstName" field-name="First Name" value="'+firstName+'" data-validation="required validate_Space validate_Alphanumeric validate_length length1-100">                      ';
		result = result  +'		<span class="hidden"></span> </div>                                                                                                                                                                               ';
		result = result  +'	</div>                                                                                                                                                                                                                ';
		result = result  +'	<div class="clearfix mb" id="lastNameDiv">                                                                                                                                                                    ';
		result = result  +'	  <label class="clearfix">Last Name</label>                                                                                                                                                                          ';
		result = result  +'	  <div class="clearfix">                                                                                                                                                                                             ';
		result = result  +'		<input type="text" class="form-control" name="lastName" id="lastName" field-name="Last Name" data-validation-optional="true" value="'+lastName+'" data-validation="validate_Space validate_Alphanumeric validate_length length1-100">  ';
		result = result  +'		<span class="hidden"></span> </div>                                                                                                                                                                               ';
		result = result  +'	</div>                                                                                                                                                                                                                ';
		
		result = result  +'	<div class="clearfix mb" id="emailDiv">                                                                                                                                                                       ';
		result = result  +'	  <label class="clearfix">Email Id <b class="valStar">*</b></label>';
		result = result  +'	  <div class="clearfix">                                                                                                                                                                                             ';
		result = result  +'		<input type="text" class="form-control" name="email" id="email" onblur="validateContactEmail()"  field-name="Email" value="'+email+'" data-validation="required validate_Space validate_length length2-100 validate_email">';
		result = result  +'		<span class="hidden"></span>';
		
/* 		result = result  +'<span class="input-group-addon" id="emailStatusIconDiv">';
		result = result  +'    <i class="fa fa-check text-muted" style="color:#f6f6f6"></i>';
		result = result  +'</span>'; */
		result = result  +'	</div> ';
		result = result  +'	</div> ';
		
		result = result  +'	<div class="clearfix mb" id="phoneDiv">                                                                                                                                                     ';
		result = result  +'	  <label class="clearfix">Phone </label>                                                                                                                                                           ';
		result = result  +'	  <div class="clearfix" >                                                                                                                                                                          ';
		result = result  +'		<input type="text" class="form-control"  name="phone" id="phone" field-name="Phone" value="'+phone+'" data-validation-optional="true" data-validation="validate_Space validate_phone validate_length length9-20"> ';
		result = result  +'		<span class="hidden"></span> </div>                                                                             ';
		result = result  +'	</div>                                                                                                              ';
		result = result  +'	                                                                                                                    ';
		result = result  +'	<div class="clearfix mb">                                                                                   ';
		result = result  +'			<label class="clearfix" >Contact Type</label>                                                              ';
		result = result  +'			<div class="clearfix select-field">                                                                        ';
		result = result  +'				<select class="form-control" name="contactType" id="contactType" value="'+contactType+'" field-name="contactType">             ';
		
		result = result  +'					<option value="">Select ContactType</option>                                                        ';
							if (contactType == "Vendor"){
								result = result  +'	<option selected>Vendor</option>   ';
							}else{
								result = result  +'	<option value="Vendor">Vendor</option>   ';
							}
							if (contactType == "Candidate"){
								result = result  +'	<option selected>Candidate</option>   ';
							}else{
								result = result  +'	<option value="Candidate">Candidate</option>   ';
							}
							if (contactType == "Client"){
								result = result  +'	<option selected>Client</option>   ';
							}else{
								result = result  +'	<option value="Client">Client</option>   ';
							}
							if (contactType == "Other"){
								result = result  +'	<option selected>Other</option>   ';
							}else{
								result = result  +'	<option value="Other">Other</option>   ';
							}
								
		result = result  +'				</select> <span class="hidden">Select Status</span>                                                     ';
		result = result  +'			</div>                                                                                                      ';
		result = result  +'		</div>                                                                                                          ';
		result = result  +'		                                                                                                                ';
		result = result  +'		<div class="clearfix mb" id="companyName">                                                              ';
		result = result  +'	  <label class="clearfix">Company Name</label>                                                                     ';
		result = result  +'	  <div class="clearfix">                                                                                           ';
		result = result  +'		<input type="text" class="form-control" name="companyName" value="'+companyName+'" id="companyName" field-name="companyName" >          ';
		result = result  +'		<span class="hidden"></span> </div>                                                                             ';
		result = result  +'	</div>                                                                                                              ';
		result = result  +'	 </div>                                                                                                                   ';
		
		result = result  +'	<div class="popupFoot text-right">                                                                               ';
		result = result  +'	  <input type="button" value="Update Contact" id="edit_contact" onclick="saveReEditContacts('+json.id+');" class="slBtn md colorBtn"> ';
		result = result  +'	  <input type="button" value="Cancel" onClick="closeEditContactDiv();" class="slBtn md canBtn">           ';
		result = result  +'	</div>                                                                                                               ';
        result = result  +'                                                                                                                      ';
		result = result  +' </div>                                                                                                               ';
		result = result  +'</form>';
		$("#popUpContactDiv").empty();
		$("#popUpContactDiv").append(result);
		
			}
	 });
	 $(".mask").show();
	 $("#popUpContactDiv").fadeIn(100);
 }
 </script>
 <script>
 function closeEditContactDiv(){
	 $(".mask").hide();
	 $("#popUpContactDiv").fadeOut(100);
 }
 </script>
 
 
 <script type="text/javascript">
function saveReEditContacts(contactId){
	       var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
			if ($('#editcontact_form').validate(false, validationSettings)){ 
			var formData = getFormData1("editcontact_form");
	  		$("#edit_contact").attr("disabled", "disabled");
	  		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	     
		    $.ajax({
					 type : "POST",
					 data: formData,
	  		         processData: false,
	  		         contentType:false, 
					//url : sourceleadUrl+"/addCrowdSourcingUserContact?"+tokenParamName+"="+tokenValue+"",
					url : sourceleadUrl+ "/addCrowdSourcingUserContact?"+tokenParamName+"="+tokenValue+"&id="+contactId,
					success : function(result) {
						
						if (result && result.response === "INVALID_DATA") {
						    showError(result.responseMessage);
						    $("#loading").hide();
						} else if (result.response === "AWKWARD") {
						    location.href = "/errorPage";
						} else if (result.response === "SUCCESS") {
							location.replace(sourceleadUrl+ "/referRequirementList");
						} else if (result.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
						    showError("Operation failed, please try again");
						}
						
					},
				});
    
	    return true;
		}else{
			return false;
		}
}
$('body').on('blur', '#addcontact_form', function() {
$("#addcontact_form").showHelpOnFocus();
$("#addcontact_form").validateOnBlur(false, validationSettings);  
 });
</script> 
 
 <script type="text/javascript">
function deleteContPopup(){
	$("#remove_user_pop, .mask").fadeOut();
}
</script>

<div id="deleteContPopUp"></div>
 
 <script type="text/javascript">
function deleteContactType(contactId){
	var result = "";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Do you want to delete the contact</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteSelectedContact("+contactId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='deleteContPopup();'></b>";
	result = result +"	</div>";
	$("#deleteContPopUp").empty();
	$("#deleteContPopUp").append(result);
	$("#remove_user_pop").fadeIn(100);
	$(".mask").show();

}
</script>
  
<script>
function deleteSelectedContact(contactId){
	
	$.ajax({	type : "POST",
		url : sourceleadUrl+ "/deleteContact?"+tokenParamName+"="+tokenValue+"&id="+contactId,
		success : function(result) {

	    	 if (result && result.response === "INVALID_DATA") {
				    showError(result.responseMessage);
				} else if (result.response === "AWKWARD") {
				    location.href = "/errorPage";
				} else if (result.response === "SUCCESS") {
					contactsDataTableList();
					$("#remove_user_pop, .mask").fadeOut();
				} else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
				    showError("Operation failed, please try again");
				}
	    },
	    });		
}

</script>

<script>
function getActions(){
	$(".action-pop").slideToggle(200);
	$(".newActPop").slideUp(200);
}	
$("html").click(function(){
	$(".action-pop").slideUp(200);
});
$(".action, .action-pop").click(function(e){
 	e.stopPropagation();
});	
</script>
