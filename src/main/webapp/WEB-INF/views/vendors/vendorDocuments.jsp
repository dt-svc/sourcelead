<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<link href="/resources/css/jquery-tagit.css" rel="stylesheet">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div id="deleteDocpopUp"></div>

<script>
$(".addUser-org").css({"overflow-x": "auto"});

function vendorDocuments(orgRelationId,vendorLocationId){
	
	var result = "";
	result = result  +'<div class="timesheet_head">';
	result = result  +'    <div class="project_tabs_inn_head addUser-org"> '; 
	result  = result + '<div class="success_pop" id="vendorDocSelector" style="display: none;">';
    result  = result + '<i class="fa fa-check-circle"></i><span id="vendorDocMsgDiv"></span>';
    result  = result + '</div>';
	    result = result + '<ul>';
	    
	    '<sec:authorize access="hasAnyRole(\"ROLE_CREATE_VENDOR_DOCUMENT\")">';
	        	result = result  +'    	<a href="javascript:void(0)" onClick="addDocument('+vendorLocationId+','+orgRelationId+'); getSpocsList('+orgRelationId+');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Documents</a>                                                            ';
		 '</sec:authorize>';
		 
		 '<sec:authorize access="!hasAnyRole(\"ROLE_CREATE_VENDOR_DOCUMENT\")">';
		      result = result  +'    	<a href="javascript:void(0)" class="addUser-org" ><i class=""></i></a>                                                            ';
		 '</sec:authorize>';
		result = result  +'     <div id="popUpDiv" class="edit-details col-md-4 editDetails"> ';
		result = result  +'     </div>  ';
		result = result  +'  <div class="clearfix"></div> ';
		result = result + '</ul>';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table vendor_wrap_inn_table_last_child">';
		result  = result + '   <div id="vendorDocumentsList" class="datatable_filter"></div>';
		result  = result + '   </div>';
	result = result+'	    </div>';
	result = result+'	    </div>';

    $("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty();  
    $("#tab-4").empty();  
    $("#tab-6").empty();  
    $("#tab-8").empty();
    $("#tab-7").empty();
    $("#tab-3").append(result);  
	
    vendorDocumentsList(vendorLocationId,orgRelationId);
    
}
</script>

<script>
function vendorDocumentsList(vendorLocationId,orgRelationId){
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  docListVendorSpocDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  docListVendorSpocDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  docListVendorSpocDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  docListVendorSpocDate = 'YYYY-DD-MM'; 
	  }
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getclientDocumentsListByResponderLocationId?"+tokenParamName+"="+tokenValue+"&responderLocationId="+vendorLocationId+"&sourceType=Vendor",        
          success :function(json) {
	                	 $("#vendorDocumentsList").empty();
	                	 var result = "";
	                	 result = result +"<table id='clientDocumentsPagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result  = result + '           <th>Name</th>';
	                 	 result  = result + '           <th>Type</th>';
	                 	 result  = result + '           <th>Contact Person</th>';
	                 	 result  = result + '           <th>Attachment</th>';
	                 	 result  = result + '           <th>Start Date <br>(<small class="custom-small">'+docListVendorSpocDate+'</small>)</th>';
	                 	 result  = result + '           <th>End Date <br>(<small class="custom-small">'+docListVendorSpocDate+'</small>)</th>';
	                 	 result  = result + '           <th>Owner</th>';
	                 	 result  = result + '           <th>Description</th>';
	                 	 result  = result + '           <th>Action</th>';
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i];  
	                    	 result  = result + '         <tr>';
	                    	 if(opt.docName != null && opt.docName != '' && opt.docName != 'undefined'){
	                    	    result  = result + '<td><div><span title="'+opt.docName+'">'+opt.docName+'</span></div></td>';
                    	     }else{
                    	    	 result  = result + ' <td><div><span>--</span></div></td>';
                    	     }
	                    	 
	                    	 if(opt.contractType != null && opt.contractType != '' && opt.contractType != 'undefined'){
	                    	 result  = result + ' <td><div><span>'+opt.contractType+'</span></div></td>';
	                    	 }else{
	                    		 result  = result + ' <td><div><span>--</span></div></td>';
	                    	 }
	                    	 if(opt.spocName != null && opt.spocName != '' && opt.spocName != 'undefined'){
		                    	 result  = result + '<td><div><span title="'+opt.spocName+'">'+opt.spocName+'</span></div></td>';
	                    	 }else{
	                    		 result  = result + '<td><div><span>--</span></div></td>'; 
	                    	 }
	                    	 
	                    	 if(opt.fileName != null && opt.fileName != '' && opt.fileName != 'undefined'){
		                    	 result  = result + '<td><div><span title="'+opt.fileName+'">'+opt.fileName+'</span></div></td>';
	                    	 }else{
	                    		 result  = result + '<td><div><span>--</span></div></td>';
	                    	 }
	                    	
	                    	 if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined'){
	                    	     result  = result + ' <td><div><span>'+opt.startDateStr+'</span></div></td>';
	                    	 }else{
	                    		 result  = result + ' <td><div><span>--</span></div></td>';
	                    	 }
	                    	 
	                    	 if(opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){
	                    	     result  = result + '<td><div><span>'+opt.endDateStr+'</span></div></td>';
	                    	 }else{
	                    		 result  = result + '<td><div><span>--</span></div></td>';
	                    	 }
	                    	 
	                    	 if(opt.ownerName != null && opt.ownerName != '' && opt.ownerName != 'undefined'){
	                    	      result  = result + ' <td><div><span title="'+opt.ownerName+'">'+opt.ownerName+'</span></div></td>';
	                    	 }else{
	                    		 result  = result + ' <td><div><span>--</span></div></td>';
	                    	 }
	                    	 if(opt.description != null && opt.description != '' && opt.description != 'undefined'){
	                    	      result  = result + ' <td><div><span title="'+opt.description+'">'+opt.description+'</span></div></td>';
	                    	 }else{
	                    		 result  = result + ' <td><div><span>--</span></div></td>';
	                    	 }
	                    	 result = result+'	 <td><div class="tbl_Icons tblminwidth-75">';
	                    	 '<sec:authorize access="hasAnyRole(\"ROLE_DOWNLOAD_VENODR_DOCS\")">';
	                    	 result = result+'<a download class="glyphicon glyphicon-download-alt" target="_blank" href=\"'+opt.fileUrl+'\"></a>';
	                    	 result = result+'<a class="fa fa-eye" onClick="viewFile(\''+opt.fileUrl+'\',\''+opt.fileName+'\',\''+opt.fileType+'\');"></a>';
	                    	 '</sec:authorize>'; 
	                    	  
	                    	 if(opt.id != null && opt.id != '' && opt.id != 'undefined'){
	                    		 
	                    		 '<sec:authorize access="hasAnyRole(\"ROLE_EDIT_VENDOR_DOCUMENT\")">';
	                    				 result = result+'<a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick="addDocument('+vendorLocationId+','+orgRelationId+'); getSpocsList('+orgRelationId+'); getClientDocument('+vendorLocationId+','+orgRelationId+', '+opt.id+');"></a>';
	                    		 '</sec:authorize>';
	                    		 
	                    		 '<sec:authorize access="hasAnyRole(\"ROLE_DELETE_VENDOR_DOCUMENT\")">';
					           	 		result = result+'<i class="glyphicon glyphicon-trash" onclick="deleteVendorDocument('+vendorLocationId+','+orgRelationId+','+opt.id+',\''+opt.docName+'\');"></i>';
					           	 '</sec:authorize>';
	                    	 }
	                    	 result = result+'	            </div></td>';
	                    	 result  = result + '</tr>';
	                      } 
	                     result = result +" </tbody>";
	                     result = result +"</table>";
	                    
	                     $("#vendorDocumentsList").append(result); 
	                     vendorNewPagination(); 
               /* }else{
	    	         var result ="";
		    	     result = result+"   <div class='row'>"; 
					 result = result+"	<div class='col-lg-12'>"; 
					 result = result+"		<div class='content-area clearfix'>"; 
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No Documents Found</p>";
					 result = result+"			 </div>";
					 result = result+"		</div>"; 
					 result = result+"	</div>"; 
					 result = result+"  </div> "; 
					$("#vendorDocumentsList").empty();
				    $("#vendorDocumentsList").append(result); 
               } */
        },
          
     }); 
}
</script>

<script>
function viewVendorDocument(docId,title){
	window.open(sourceleadUrl+'/viewClientDocViewerDocument/'+title+'?docId='+docId,title,"height=600,width=600");
}
</script>

<script>
function vendorNewPagination(){  
    $('#clientDocumentsPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 8
			}],
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>

<script>
function addDocument(vendorLocationId,orgRelationId) {	
	
	var result = "";
	result = result  +'<!-- Popup Div -->';
	result = result  +'          <form role="form"  id="document_form" name="document_form" method="post" > ';
	result = result  +'          <i class="fa fa-close close-div"  onClick="closeDocPopup();"></i> ';
	result = result  +'                <div class="form-horizontal clearfix"> ';
	result = result  +'   ';
	
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                                                                                                                                                       ';
	result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
	result = result  +'                    <div class="col-md-12">                                                                                                            ';
	result = result  +'                      <input type="text" name="docName" id="docName" field-name="Document Name" data-validation="required validate_Space validate_length length2-100" class="form-control input-sm" value="" placeholder="Document Name *">                                          ';
	result = result  +'                    </div>                                                                                                                             ';
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                                                                                                                                                       ';
	
	result  = result + '                 <div class="clearfix mb10">';
	result = result  +'                    <div class="col-md-12"> ';
	result = result  +'	                     <select style="width: 100%;" class="chosen-select" name="contractType" id="contractType">';
	result = result  +' 					   <option value="" selected="selected">Contract Type</option>';
	result = result  +'	                       <option value="MSA">MSA</option>';
	result = result  +'	                       <option value="SOW">SOW</option>';
	result = result  +'	                       <option value="NDA">NDA</option>';
	result = result  +'	                       <option value="NSA">NSA</option>';
	result = result  +'	                      </select>';
	result = result  +'	                   </div>';
	result = result  +'	                   </div>';
	
	result = result+'	                  <div class="clearfix mb" id="uploadDocumentDiv">';
	result = result+'	                    <div class="col-md-12">';
	result = result+'							<div class="input-group uploadFileGroup_error_message" id="uploadFileGroup">';
	result = result+'	                          <label class="input-group-btn"> <span class="btn btn-primary" onChange="vendorDocFileSaving();"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
	result = result+'	                            <input type="file"  id="uploadBtn" style="display: none;"  class="upload">';
	result = result+'	                            </span> </label>';
	result = result+'	                          <input type="text" class="form-control" readonly id="uploadFile" name="docFile" placeholder="Upload File." field-name="Document" data-validation="required validate_Space validatedoc">';
	result = result+'	                        </div>';
	result = result+'	                    </div>  ';  
	result = result+'	                  </div>   ';  
	
	result = result  +'	                 <div class="clearfix mb10 mt">';
	result = result  +'	                   <div class="col-md-6">';
    result = result + '                      <select class="chosen-select form-control" name="spoc" id="spoc" field-name="Client Contact"  >';
	result = result + '                      </select>';
    result = result  +'	                     </div>';
	
	result = result  +'	                  <div class="col-md-6">';
	result = result  +'	                      <select class="chosen-select" name="owner" id="owner" field-name="Owner">';
	result = result  +' 					      <option value="">Owner</option>';
	result = result  +'	                          <c:forEach var="user" items="${userList}">';
	result = result  +'	                              <option value="${user.id}" ${user.id  == SourceLeadWeb_userId ? "selected" : ''}>${user.firstName} ${user.lastName}</option>';
	result = result  +'	                          </c:forEach>';
    result = result  +'	                        </select>';
    result = result  +'	                     </div>';
    result = result  +'	                   </div>';
	
	result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
	result = result  +'                    <div class="col-md-6">                                                                                            ';
	result  = result + '                   <div>';
	result = result  +'                      <input type="text" name="startDate" class="form-control input-sm" field-name="startDate" id="startDate" onclick="educationStartDate();" data-validation="required" class="form-control input-sm" data-validation-optional="true" value="" placeholder="Start Date" id="edu-startDate" readonly>                           ';
	result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>                                           ';
	result  = result + '					<div class="clearDate" onClick="clearVendorDocuStartDate();"></div>';
	result  = result + '                   </div>';
	result = result  +'                    </div>                                                                                                                             ';
	
	result = result  +'                    <div class="col-md-6">                                                                                            ';
	result  = result + '                   <div>';
	result = result  +'                      <input type="text" name="endDate" class="form-control input-sm" field-name="endDate" id="endDate" data-validation="required" class="form-control input-sm" data-validation-optional="true" value="" placeholder="End Date" id="edu-endDate" readonly>                               ';
	result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>                                           ';
	result  = result + '					<div class="clearDate" onClick="clearVendorDocuEndDate();"></div>';
	result  = result + '                   </div>';
	result = result  +'                    </div>                                                                                                                             ';
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                                                                                                                                                       ';
	
	result = result+'	                  <div class="clearfix mb10">';
	result = result+'	                    <div class="col-md-12">';
	result = result+'	                      <textarea rows="2" placeholder="Description" value="" id="docDescription" name="description" data-validation="validate_length length2-500" data-validation-optional="true" field-name="Description" class="form-control"></textarea>';
	result = result+'	                    </div>';
	result = result+'	                  </div>';
	
	result = result+'	                  <div class="clearfix mb10" id="docNameDiv" style="display: none;">';
	result = result+'	                    <div class="col-md-12 mt">';
	result = result+'	                      <label id="documentName" name="documentName" class="text-primary tbldata-dotdot-vsm pull-left"></label>&nbsp;&nbsp;<span id="delete_resume" onclick="removeResumeNameDiv();"><i class="fa fa-trash removeIcon_styles"></i></span>';
	result = result+'	                    </div>';
	result = result+'	                  </div>';
	
	result = result+'	                  <div class="clearfix mr mt" id="docUpdateButton" style="display: none;">';
	result = result+'	                    <button type="button" id="update_doc" class="btn btn-sm btn-primary pull-right">Update</button>';
	result = result+'	                  </div>';
	
	result = result  +'                  <div class="clearfix mr" id="docAddButton">                                                                                                            ';
    result = result +'                     <input type="button" value="Add" name="btnAddPr" id="save_button" class="btn btn-sm btn-primary pull-right" onClick ="storeClientDocument('+vendorLocationId+','+orgRelationId+', null);" />'; 
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                </div>                                                                                                                                 ';
	result = result  +'                </form>                                                                                                                                ';
	$("#popUpDiv").empty();
	$("#popUpDiv").append(result);
	
	function educationStartDate(){
	}
	 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormatVendorDocSave = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormatVendorDocSave = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormatVendorDocSave = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormatVendorDocSave = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormatVendorDocSave = 'yy-dd-mm'; 
	  }
	$('#startDate').datepicker({
	      dateFormat: finalDateFormatVendorDocSave,
	      changeMonth: true,
	  	  changeYear: true, 
	      onSelect: function (selected) {
	           // var dt = new Date(selected);
	    	  var dt = $(this).datepicker('getDate', '+1d');
	            dt.setDate(dt.getDate() + 1);
	            $("#endDate").datepicker("option", "minDate", dt);
	        }
	  });
	  
	  $('#endDate').datepicker({
	      dateFormat: finalDateFormatVendorDocSave,
	      changeMonth: true,
	  	  changeYear: true, 
	        onSelect: function (selected) {
	           // var dt = new Date(selected);
	        	 var dt = $(this).datepicker('getDate', '-1d');
		            dt.setDate(dt.getDate() - 1);
		            $("#startDate").datepicker("option", "maxDate", dt);
	        }
	  });
	
	  /* upload file script to show selected file name*/
	    document.getElementById("uploadBtn").onchange = function () {
	    document.getElementById("uploadFile").value = this.value.split( '\\' ).pop();;
	    };
	    
	    /* upload file script for size validation 2MB*/
	    
	  
	$(".edit-details").toggleClass("edit-details-active");
	 $(".close-div").click(function(){ 
		 $(this).parent("div.edit-details").removeClass("edit-details-active");
		 });
	 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details, .ui-datepicker-prev, .ui-datepicker-next").click(function(e){
		 e.stopPropagation();
	 });
	 /* $('html').click(function(){
		$(".edit-details").removeClass("edit-details-active");
	   }); */	
	 
	 var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				})
	}
</script>

<script>
function closeDocPopup(){
	 $("#popUpDiv").removeClass("edit-details-active");
}
</script>

<script type="text/javascript">
	function storeClientDocument(vendorLocationId,orgRelationId,docId){
			var validationSettings = {
			        errorMessagePosition : 'element',
			        borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
		         }; 
			 if ($('#document_form').validate(false, validationSettings)){
				$("#save_button").attr("disabled", "disabled");
				$("#save_button" ).addClass('button').val('Processing..');
				var form = $('#document_form')[0];
	      		var formData = new FormData(form); 
				$("#document_form").attr("disabled", "disabled");
				//It will escape all the special characters
				$.ajax({
						type : "POST",
		        	    data: formData,
		    	        processData: false,
		    	        contentType:false,
						url : sourceleadUrl+"/saveDocuments?"+tokenParamName+"="+tokenValue+"&responderLocationId="+vendorLocationId+"&docId="+docId+"&sourceType=Vendor",
						success : function(result) {
							 if( result.indexOf('Sign In') >= 0){
					    		location.replace(sourceleadUrl);
					    	 }
		            		 else if (result.indexOf('exception') >= 0) {
		            			 location.replace(sourceleadUrl+"/errorPage");
		            		 }
		            		 else{
		            			$("#popUpDiv").removeClass("edit-details-active");
		            			$("#vendorDocSelector").removeAttr("style");
		            			$("#vendorDocSelector").css({ display: "block" });
		            			if(docId != null){
		            				$("#vendorDocMsgDiv").html("Success!! Document details updated");
		            			}else{
		            				$("#vendorDocMsgDiv").html("Success!! New document added");
		            			}
		            			vendorDocumentsList(vendorLocationId,orgRelationId);
		            			vendorDocMsgFadein();
		            		 }
						},
					});
					return true;
			 }else{ 
					return false;
			 } 
		}
		$('body').on('blur', '#document_form', function() {
		$("#document_form").showHelpOnFocus();
		$("#document_form").validateOnBlur(false, validationSettings);
	});
</script>

<script>
function getSpocsList(orgRelationId){
	$.ajax({
    	type: "POST",
        url: sourceleadUrl+"/getOrgSpocsList?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId+"&type=Vendor",  
        success: function(json) {
        	//var json = JSON.parse(data);
        	var output = "";
        	output = output + "<option value=''>Vendor Contact</option>"; 
        	for (var i=0; i<json.data.length; i++)
			{
	        		var opt = json.data[i];
	        		output = output + "<option value='" +opt.spocUserId+ "'>"+opt.spocName+"&nbsp;("+opt.ownerName+")</option>";
			}
        	$("#spoc").empty();
	        $("#spoc").append(output);
	        $('#spoc').trigger("chosen:updated");
	        //$('.selectpicker').selectpicker('refresh'); 
        },
    });
}

</script>


<script>
function getClientDocument(vendorLocationId,orgRelationId,docId){
	var sourceLeadUserId = '${SourceLeadWeb_userId}';
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/getOrgDocument?"+tokenParamName+"="+tokenValue+"&docId="+docId+"&orgRelationId="+orgRelationId+"&type=Vendor", 	   
		    success: function(json) {
		    	//var json = JSON.parse(data);
		    	var spoc = "";
		    	var owner = "";
    			 $("#uploadDocumentDiv").hide();
    			 $("#docAddButton").hide();
    			 $("#uploadFile").removeAttr("data-validation");
    			 $("#docNameDiv").show();
    			 $("#documentName").append(json.fileName);
    			 $("#docUpdateButton").show();
    			 
    			 
    			 if(json.docName != null && json.docName != "undefined" && json.docName != ""){
    				 document.getElementById("docName").value = json.docName;
    			 }
    			 if(json.contractType != null && json.contractType != "undefined" && json.contractType != ""){
    				 document.getElementById("contractType").value = json.contractType;
    				 $('#contractType').trigger("chosen:updated");
    			 }
    			 
    			     			 
    			 if(json.orgSpocsList != null && json.orgSpocsList != "" && json.orgSpocsList != "undefined" && json.orgSpocsList.length > 0){
    				 $('#spoc').empty();
    		         $('#spoc').append('<option value="">Vendor Contact</option>');
    		            for(var i=0; i<json.orgSpocsList.length; i++){
    		            	 spoc = json.orgSpocsList[i];
	    		 	    	  if(json.spocUser != null && json.spocUser != "" && json.spocUser != 'undefined' && json.spocUser.id == spoc.vendorUser.id){
	    		 	    		  $('#spoc').append('<option value="'+spoc.spocUserId+'" selected>'+spoc.spocName+'&nbsp;('+spoc.ownerName+')</option>');
	    		 	    	  }else{
	    		 	    		 $('#spoc').append('<option value="'+spoc.spocUserId+'">'+spoc.spocName+'</option>');
	    		 	    	  }
    		            }
    				
    				 $('#spoc').trigger("chosen:updated");
    			 }
    			 
    			 if(json.ownersList != null && json.ownersList != "undefined" && json.ownersList != "" && json.ownersList.length > 0){
    				 $('#owner').empty();
    		         $('#owner').append('<option value="">Owner</option>');
    		            for(var j=0; j<json.ownersList.length; j++){
    		            	  owner = json.ownersList[j];
	    		 	    	  if(json.ownerUser != null && json.ownerUser != "" && json.ownerUser != 'undefined' && json.ownerId == owner.id){
	    		 	    		  $('#owner').append('<option value="'+owner.id+'" selected>'+owner.firstName+' '+owner.lastName+'</option>');
	    		 	    	  }
	    		 	    	  else if((json.ownerUser == null || json.ownerUser == "" || json.ownerUser == 'undefined') && (owner.id == sourceLeadUserId)){
	    		 	    		 $('#owner').append('<option value="'+owner.id+'" selected>'+owner.firstName+' '+owner.lastName+'</option>');
	    		 	    	  }
	    		 	    	  else{
	    		 	    		 $('#owner').append('<option value="'+owner.id+'">'+owner.firstName+' '+owner.lastName+'</option>');
	    		 	    	  }
    		            }
    				
    				 $('#owner').trigger("chosen:updated");
    			 }
    			 
    			 if(json.startDateStr != null && json.startDateStr != "undefined" && json.startDateStr != ""){
    				 document.getElementById("startDate").value = json.startDateStr;
    			 }
    			 
    			 if(json.endDateStr != null && json.endDateStr != "undefined" && json.endDateStr != ""){
    				 document.getElementById("endDate").value = json.endDateStr;
    			 }
    			 
    			 
    			 if(json.description != null && json.description != "undefined" && json.description != ""){
    				 document.getElementById("docDescription").value = json.description;
    			 }
    			 
    			 $("#update_doc").attr("onclick", "storeClientDocument("+vendorLocationId+","+orgRelationId+","+json.id+");");
    			 
    			 //manageDates(json.startDateStr,json.endDateStr);
		    }
     });
}
</script>

<script>
function manageDates(startDateStr,endDateStr) {
	  if($("#endDate").val() == "" ){
		     
			  var selected = $("#startDate").val();
			  var dt = toDate("#startDate");
			  dt.setDate(dt.getDate() + 1);
			  $("#endDate").datepicker("option", "minDate", dt);
			  
		 	  /* $('#endDate').datepicker({
		            dateFormat: 'yy-mm-dd',
		            onSelect: function (selected) {
			            var dt = new Date(selected);
			            dt.setDate(dt.getDate() - 1);
			            $("#startDate").datepicker("option", "maxDate", dt);
		           }
	          }); */
	  }
	  else{
		   var selected = $("#startDate").val();
		   var dt = toDate("#startDate");
           dt.setDate(dt.getDate());
		   
		  // var actualStartDate = $("#startDate").val();
		   var startDate = "";
           if(dt != null && dt != "" && dt != 'undefined'){
        	   startDate = dt;
        	   startDate.setDate(startDate.getDate() + 1); 
           }
           
           //var actualEndDate = $("#endDate").val();
           var endDate = "";
           var enddt = toDate("#endDate");
           if(enddt != null && enddt != "" && enddt != 'undefined'){
          	  endDate = enddt;
	          endDate.setDate(endDate.getDate() - 1); 
           }
           
 		   $("#endDate").datepicker("option", "minDate", startDate);
	       $("#startDate").datepicker("option", "maxDate", endDate);
		  
	  }
  } 
</script>

<script>
function removeResumeNameDiv(){
	$("#docNameDiv").hide();
	$("#uploadDocumentDiv").show();
	$("#uploadFile").attr("data-validation", "required validate_Space validatedoc");
}
</script>

<script>
function deleteVendorDocument(vendorLocationId,orgRelationId,docId,docName){
	var result = "";
	result = result  +'          <form role="form"  id="docu_form" name="docu_form" method="post" > ';
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a remove_uservalidation'>";
	result = result +"		<p>Document delete message here</p>";
	result = result +"		<textarea  id='terminateReason' name='terminateReason'  data-validation = 'required' field-name='comment' placeholder='Comments here'></textarea>";
	result = result + ' <div id="terminateReasonDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Submit' onclick='saveReasonForVendorDoc("+vendorLocationId+","+orgRelationId+","+docId+",\""+docName+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelDocTermination();'></b>";
	result = result +"	</div>";
	result = result  +' </form> ';
	$("#deleteDocpopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script>
function cancelDocTermination(){
$("#deleteDocpopUp").empty();
$(".project_inn_mask").fadeOut(200);
}
</script>

<script>
function saveReasonForVendorDoc(vendorLocationId,orgRelationId,docId,docName){
	var terminateReason = document.getElementById("terminateReason").value;
	if ($('#docu_form').validate(false, validationSettings)){
		$("#terminateReasonDiv").empty();
		 var formData = new FormData();
	     formData.append("tokenParamName", tokenValue);
	     formData.append("docId", docId);
	     formData.append("terminateReason", terminateReason);
		$.ajax({
			 type : "POST",
	          data: formData,
	          processData: false,
	          contentType:false, 
	     	  url: sourceleadUrl+"/terminateOrgDocumnet",
// 		    url: sourceleadUrl+"/terminateOrgDocumnet?"+tokenParamName+"="+tokenValue+"&docId="+docId+"&terminateReason="+terminateReason, 	   
   		    success: function(data) {
        			 $("#deleteDocpopUp").empty();
        			 $(".project_inn_mask").fadeOut(200);
        			 $("#vendorDocSelector").removeAttr("style");
         			 $("#vendorDocSelector").css({ display: "block" });
         			 $("#vendorDocMsgDiv").html(""+docName+" Removed");
        			 vendorDocumentsList(vendorLocationId,orgRelationId);
        			 vendorDocMsgFadein();
   		    }
	     });
		
	}else{
		return false;
	  }
	
	 /* else{
	if(terminateReason){
		$("#terminateReasonDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Must not Start With Space</font>") ;
	}else{
		$("#terminateReasonDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
	}
} */

}

</script>
<!-- Show success message after form submission script -->
<script>
	function vendorDocMsgFadein() {
	        $("#vendorDocSelector").fadeIn(200);
	        $('#vendorDocSelector').delay(2000).fadeOut('slow');
	}
</script> 
<!-- End -->

<!-- script for converting dateformat in datepicker -->
<script>
function toDate(selector) {
    var from = $(selector).val().split("-");
    return new Date(from[2], from[1] - 1, from[0]);
}
</script>
<script>
function vendorDocFileSaving()
{
	fileUpload("uploadBtn", "clientDocuments", "document_form");
}
</script>
<!-- script for dateformat in datepicker End -->
<!-- <script>
	function clearUpload(){
		$("#uploadFile").val("");
	}
</script> -->
<script>
function clearVendorDocuStartDate(){
	$("#startDate").val("");
	};
function clearVendorDocuEndDate(){
	$("#endDate").val("");
	};
</script>