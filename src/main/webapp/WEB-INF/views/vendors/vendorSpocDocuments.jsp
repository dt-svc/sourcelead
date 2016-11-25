<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/bootstrap-select.min.js"></script>
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css">
<link rel="stylesheet" href="resources/css/chosen.min.css">
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
function vendorSpocDocuments(orgRelationId,clientLocationId,spocName,userId){
	var result = "";
	result = result  +'<div class="timesheet_head">';
	result = result  +'    <div class="project_tabs_inn_head addUser-org"> ';  
	    result = result + '<ul>';
		result = result  +'    	<a href="javascript:void(0)" onClick="addDocumentForSpoc('+clientLocationId+','+orgRelationId+',\''+spocName+'\','+userId+'); getSpocsList('+orgRelationId+');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Documents</a>                                                            ';
		result = result  +'     <div id="popUpDiv" class="edit-details col-md-4"> ';
		result = result  +'     </div>  ';
		result = result  +'  <div class="clearfix"></div> ';
		result = result + '</ul>';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		result  = result + '   <div id="spocDocumentsList" ></div>';
		result  = result + '   </div>';
	result = result+'	    </div>';
	result = result+'	    </div>';

    $("#tab-1").empty();
    $("#tab-2").empty(); 
   // $("#tab-3").empty();  
    $("#tab-2").append(result);  
	
    spocDocumentsList(clientLocationId,orgRelationId,userId,spocName);
    
}
</script>

<script>
function spocDocumentsList(clientLocationId,orgRelationId,userId,spocName){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getclientDocumentsListByResponderLocationIdAndSpocUserId?${_csrf.parameterName}=${_csrf.token}&responderLocationId="+clientLocationId+"&userId="+userId,        
          success :function(data) {
               if(data != null && data != 'undefined' && data != "" && data != '[]'){
             		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
             		 else if (data.indexOf('exception') >= 0) {
             			 location.replace(sourceleadUrl+"/errorPage");
             		 }
             		 else{
	                	 $("#spocDocumentsList").empty();
	                	 var result = "";
	                	 result = result +"<table id='vendorDocumentsPagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result  = result + '           <th>Name</th>';
	                 	 result  = result + '           <th>Type</th>';
	                 	 result  = result + '           <th>Contact Person</th>';
	                 	 result  = result + '           <th>Attachment</th>';
	                 	 result  = result + '           <th>Start date</th>';
	                 	 result  = result + '           <th>End date</th>';
	                 	 result  = result + '           <th>Owner</th>';
	                 	 result  = result + '           <th>Action</th>';
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     var json = JSON.parse(data);
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i];  
	                    	 result  = result + '         <tr>';
	                    	 if(opt.docName != null && opt.docName != '' && opt.docName != 'undefined'){
	                    	    result  = result + '<td><div><span>'+opt.docName+'</span></div></td>';
                    	     }else{
                    	    	 result  = result + ' <td><div><span>--</span></div></td>';
                    	     }
	                    	 
	                    	 if(opt.contractType != null && opt.contractType != '' && opt.contractType != 'undefined'){
	                    	 result  = result + ' <td><div><span>'+opt.contractType+'</span></div></td>';
	                    	 }else{
	                    		 result  = result + ' <td><div><span>--</span></div></td>';
	                    	 }
	                    	 if(opt.spocName != null && opt.spocName != '' && opt.spocName != 'undefined'){
		                    	 result  = result + '<td><div><span>'+opt.spocName+'</span></div></td>';
	                    	 }else{
	                    		 result  = result + '<td><div><span>--</span></div></td>'; 
	                    	 }
	                    	 
	                    	 if(opt.fileName != null && opt.fileName != '' && opt.fileName != 'undefined'){
		                    	 result  = result + '<td><div><span>'+opt.fileName+'</span></div></td>';
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
	                    	      result  = result + ' <td><div><span>'+opt.ownerName+'</span></div></td>';
	                    	 }else{
	                    		 result  = result + ' <td><div><span>--</span></div></td>';
	                    	 }
	                    	 
	                    	 if(opt.id != null && opt.id != '' && opt.id != 'undefined'){
	                    		 result = result+'	 <td><div class="tblicons"><a class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadOrgDocument?docId='+opt.id+'\"></a>';
	                    		 result = result+'	 <a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick="addDocumentForSpoc('+clientLocationId+',\''+spocName+'\','+userId+');  getVendorSpocDocument('+clientLocationId+','+orgRelationId+',\''+spocName+'\','+userId+','+opt.id+');"></a>';
					           	 result = result+'	 <i class="glyphicon glyphicon-trash" onclick="deleteDocument(\''+clientLocationId+'\',\''+orgRelationId+'\',\''+spocName+'\',\''+userId+'\',\''+opt.id+'\');"></i>';
					           	 result = result+'	 </div></td>';
	                    	 }
	                    	
	                    	 result  = result + '</tr>';
	                      } 
	                     result = result +" </tbody>";
	                     result = result +"</table>";
	                     $("#spocDocumentsList").append(result); 
	                     spocDocumentpagination(); 
			    	} 
               }else{
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
					$("#spocDocumentsList").empty();
				    $("#spocDocumentsList").append(result); 
               }
        },
          
     }); 
}
</script>
<div id="deleteDocpopUp"></div>
<script>
function spocDocumentpagination(){  
    $('#vendorDocumentsPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [5, 15, 20, "All"]],
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
function addDocumentForSpoc(clientLocationId,orgRelationId,spocName,userId) {	
	
	var result = "";
	result = result  +'<!-- Popup Div -->';
	result = result  +'          <form role="form"  id="document_form_spoc" name="document_form_spoc" method="post" > ';
	result = result  +'          <i class="fa fa-close close-div"  onClick="closePopup();"></i> ';
	result = result  +'                <div class="form-horizontal clearfix"> ';
	result = result  +'   ';
	
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                                                                                                                                                       ';
	result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
	result = result  +'                    <div class="col-md-12">                                                                                                            ';
	result = result  +'                      <input type="text" name="docName"  id="docName" field-name="Name" data-validation="required validate_Space validate_length length2-100" class="form-control input-sm" value="" placeholder="Name *">                                          ';
	result = result  +'                    </div>                                                                                                                             ';
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                                                                                                                                                       ';
	
	result  = result + '                 <div class="clearfix mb10">';
	result = result+'	                    <div class="col-md-12">';
	result = result  +'	                     <select style="width: 100%;" id="contractType" class="chosen-select" name="contractType" id="contractType">';
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
	result = result+'							<div class="input-group positionRelative" id="uploadFileGroup">';
	result = result+'	                          <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
	result = result+'	                            <input type="file" name="uploadBtn" id="uploadBtn" style="display: none;"  multiple="" class="upload">';
	result = result+'	                            </span> </label>';
	result = result+'	                          <input type="text" class="form-control" readonly id="uploadFile" name="docFile" placeholder="Upload File." field-name="Document" data-validation="required validate_Space validatedoc">';
	result = result+'	                        </div>';
	result = result+'	                    </div>  ';  
	result = result+'	                  </div>   ';  
	
	result = result  +'	                 <div class="clearfix mb10 mt">';
	result = result  +'	                   <div class="col-md-6">';
	result = result  +'                      <input type="text" name="spocName"  id="spocName" field-name="spocName"  readonly class="form-control input-sm" value="'+spocName+'" >                                          ';
	result = result + '							<input type="hidden" name="userId" id="userId" value="'+userId+'" >';
    result = result  +'	                     </div>';
	
	result = result  +'	                  <div class="col-md-6">';
	result = result  +'	                      <select class="chosen-select" name="owner" id="owner" field-name="Owner">';
	result = result  +' 					      <option value="" selected="selected">Owner</option>';
	result = result  +'	                          <c:forEach var="user" items="${userList}">';
	result = result  +'	                              <option value="${user.id}" ${user.id  == SourceLeadWeb_userId ? "selected" : ''}>${user.firstName} ${user.lastName}</option>';
	result = result  +'	                          </c:forEach>';
    result = result  +'	                        </select>';
    result = result  +'	                     </div>';
    result = result  +'	                   </div>';
	
	result = result  +'                  <div class="clearfix mb10">                                                                                                          ';
	result = result  +'                    <div class="col-md-6 positionRelative">                                                                                            ';
	result = result  +'                      <input type="text" id="startDate" name="startDate" class="form-control input-sm" field-name="startDate" id="startDate" onclick="educationStartDate();" data-validation="required" class="form-control input-sm" data-validation-optional="true" value="" placeholder="Start Date" id="edu-startDate" readonly>                           ';
	result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>                                           ';
	result = result  +'                    </div>                                                                                                                             ';
	
	result = result  +'                    <div class="col-md-6 positionRelative">                                                                                            ';
	result = result  +'                      <input type="text" id="endDate" name="endDate" class="form-control input-sm" field-name="endDate" id="endDate" data-validation="required" class="form-control input-sm" data-validation-optional="true" value="" placeholder="End Date" id="edu-endDate" readonly>                               ';
	result = result  +'                      <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>                                           ';
	result = result  +'                    </div>                                                                                                                             ';
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                                                                                                                                                       ';
	
	result = result+'	                  <div class="clearfix mb10">';
	result = result+'	                    <div class="col-md-12">';
	result = result+'	                      <textarea rows="2" placeholder="Description" value="" id="description" name="description" data-validation="validate_length length2-500" data-validation-optional="true" field-name="Description" class="form-control"></textarea>';
	result = result+'	                    </div>';
	result = result+'	                  </div>';
	
	result = result+'	                  <div class="clearfix mb10" id="docNameDiv" style="display: none;">';
	result = result+'	                    <div class="col-md-12 mt">';
	result = result+'	                      <label id="documentName" name="documentName" class="text-primary tbldata-dotdot-vsm pull-left"></label>&nbsp;&nbsp;<span id="delete_resume" onclick="removeResumeNameDiv();"><i class="fa fa-trash removeIcon"></i></span>';
	result = result+'	                    </div>';
	result = result+'	                  </div>';
	
	result = result+'	                  <div class="clearfix mr mt" id="docUpdateButton" style="display: none;">';
	result = result+'	                    <button type="button" id="update_doc" class="btn btn-sm btn-primary pull-right">Update</button>';
	result = result+'	                  </div>';
	
	result = result  +'                  <div class="clearfix mr" id="docAddButton">                                                                                                            ';
    result = result +'                     <input type="button" value="Add" name="btnAddPr" id="save_button" class="btn btn-sm btn-primary pull-right" onClick ="storeSpocDocument('+clientLocationId+','+orgRelationId+','+userId+',\''+spocName+'\',null);" />'; 
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                </div>                                                                                                                                 ';
	result = result  +'                </form>                                                                                                                                ';
	$("#popUpDiv").empty();
	$("#popUpDiv").append(result);
	
	function educationStartDate(){
	}
	
	$('#startDate').datepicker({
	      dateFormat: 'yy-mm-dd',
	      onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() + 1);
	            $("#endDate").datepicker("option", "minDate", dt);
	        }
	  });
	  
	  $('#endDate').datepicker({
	      dateFormat: 'yy-mm-dd',
	        onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() - 1);
	            $("#startDate").datepicker("option", "maxDate", dt);
	        }
	  });
	
	  /* upload file script to show selected file name*/
	    document.getElementById("uploadBtn").onchange = function () {
	    document.getElementById("uploadFile").value = this.value.split( '\\' ).pop();;
	    };
	    
	    /* upload file script for size validation 2MB*/
	    var selectedFile = document.getElementById("uploadBtn");
	    selectedFile.addEventListener("change", function() {
	      var docSize = this.files[0].size;
	      $("#uploadFileGroup").find('p.jquery_form_error_message').remove();
	      if(docSize > 2097152){
	    	  document.getElementById("uploadFile").value = '';
	    	  $("#uploadFileGroup").append("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </p>");
	      }
	    });
	  
	$(".edit-details").toggleClass("edit-details-active");
	 $(".close-div").click(function(){ 
		 $(this).parent("div.edit-details").removeClass("edit-details-active");
		 });
	 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
		 e.stopPropagation();
	 });
	 /* $('html').click(function(){
		$(".edit-details").removeClass("edit-details-active");
	   });	 */
	 
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
function closePopup(){
	 $("#popUpDiv").removeClass("edit-details-active");
}
</script>

<script type="text/javascript">
	function storeSpocDocument(clientLocationId,orgRelationId,userId,spocName,docId){
		var validationSettings = {
		        errorMessagePosition : 'element',
		        borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
		 if ($('#document_form_spoc').validate(false, validationSettings)){
			var frm = $('#document_form_spoc').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			$("#save_button").attr("disabled", "disabled");
			$("#save_button" ).addClass('button').val('Processing..');
			var form = $('#document_form_spoc')[0];
      		var formData = new FormData(form); 
			var sourceleadUrl = '${sourceleadUrl}';
			$("#document_form_spoc").attr("disabled", "disabled");
			
			//It will escape all the special characters
	        var jsonData = encodeURIComponent(con);
			$.ajax({
					type : "POST",
	        	    data: formData,
	        	    contentType : 'application/json; charset=utf-8',
	        	    enctype: 'multipart/form-data',
	    	        processData: false,
	    	        contentType:false,
	        	    dataType : 'json',
					url : sourceleadUrl+"/saveDocuments?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&responderLocationId="+clientLocationId+"&docId="+docId+"&sourceType=Vendor",
					success : function(result) {
						 if( result.indexOf('Sign In') >= 0){
				    		location.replace(sourceleadUrl);
				    	 }
	            		 else if (result.indexOf('exception') >= 0) {
	            			 location.replace(sourceleadUrl+"/errorPage");
	            		 }
	            		 else{
	            			$("#popUpDiv").removeClass("edit-details-active");
	            			spocDocumentsList(clientLocationId,orgRelationId,userId,spocName);
	            		 }
					},
				});
				return true;
		 }else{ 
				return false;
		 } 
	}
	$('body').on('blur', '#document_form_spoc', function() {
	$("#document_form_spoc").showHelpOnFocus();
	$("#document_form_spoc").validateOnBlur(false, validationSettings);
});
</script>

<!-- <script>
function getSpocsList(orgRelationId){
	 var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
    	type: "POST",
        url: sourceleadUrl+"/getOrgSpocsList?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationId, 
        success: function(data) {
        	var json = JSON.parse(data);
        	var output = "";
        	output = output + "<option value=''>Client Contact</option>"; 
        	for (var i=0; i<json.length; i++)
			{
	        		var opt = json[i];
	        		output = output + "<option value='" +opt.spocUserId+ "'>"+opt.spocName+"</option>";
			}
        	$("#spoc").empty();
	        $("#spoc").append(output);
	        $('#spoc').trigger("chosen:updated");
	        //$('.selectpicker').selectpicker('refresh'); 
        },
    });
}

</script> -->
<script>
function deleteDocument(clientLocationId,orgRelationId,spocName,userId,docId){
	 var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>User delete message here</p>";
	result = result +"		<textarea  id='terminateReason' name='terminateReason'  placeholder='Comments here'></textarea>";
	result = result + ' <div id="terminateReasonDiv"/>';
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Submit" onclick="saveReason('+clientLocationId+','+orgRelationId+',\''+spocName+'\','+userId+','+docId+');"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelDocTermination();"></b>';
	result = result +"	</div>";
	$("#deleteDocpopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200); 
}
</script>

<script>
function saveReason(clientLocationId,orgRelationId,spocName,userId,docId){
	var sourceleadUrl ='${sourceleadUrl}';
	var terminateReason = document.getElementById("terminateReason").value;
	if(terminateReason.match(/^[^\s-](.*)*$/) != null){
		$("#terminateReasonDiv").empty();
		$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/terminateOrgDocumnet?${_csrf.parameterName}=${_csrf.token}&docId="+docId+"&terminateReason="+terminateReason, 	   
   		    success: function(data) {
   		    	if( data.indexOf('Sign In') >= 0){
		    		location.replace(sourceleadUrl);
		    	 }
        		 else if (data.indexOf('exception') >= 0) {
        			 location.replace(sourceleadUrl+"/errorPage");
        		 }
        		 else{
        			 $("#deleteDocpopUp").empty();
        			 $(".project_inn_mask").fadeOut(200);
        			 spocDocumentsList(clientLocationId,orgRelationId,userId,spocName);
        		 }
   		    }
	     });
	}else{
		if(terminateReason){
			$("#terminateReasonDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Must not Start With Space</font>") ;
		}else{
			$("#terminateReasonDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
		}
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
function getVendorSpocDocument(clientLocationId,orgRelationId,spocName,userId,docId){
	var sourceleadUrl ='${sourceleadUrl}';
	var sourceLeadUserId = '${SourceLeadWeb_userId}';
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/getOrgDocumentbySpocId?${_csrf.parameterName}=${_csrf.token}&docId="+docId+"&orgRelationId="+orgRelationId+"&userId="+userId, 	   
		    success: function(data) {
		    	var json = JSON.parse(data);
		    	var spoc = "";
		    	var owner = "";
		    	if( data.indexOf('Sign In') >= 0){
	    		location.replace(sourceleadUrl);
	    	 }
    		 else if (data.indexOf('exception') >= 0) {
    			 location.replace(sourceleadUrl+"/errorPage");
    		 }
    		 else{
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
    			
    			 $('#spocName').val(spocName);
    			 $('#userId').val(userId);
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
    				 document.getElementById("description").value = json.description;
    			 }
    			 
    			 $("#update_doc").attr("onclick", 'storeSpocDocument(\''+clientLocationId+'\',\''+orgRelationId+'\',\''+userId+'\',\''+spocName+'\',\''+json.id+'\');');
    			 
    			 manageDates();
    		 }
		    }
     });
}
</script>

<script>
function manageDates() {
	
	  if($("#endDate").val() == "" ){
		     
			  var selected = $("#startDate").val();
			  var dt = new Date(selected);
			  dt.setDate(dt.getDate() + 1);
			  $("#endDate").datepicker("option", "minDate", dt);
			  
		 	  $('#endDate').datepicker({
		            dateFormat: 'yy-mm-dd',
		            onSelect: function (selected) {
			            var dt = new Date(selected);
			            dt.setDate(dt.getDate() - 1);
			            $("#startDate").datepicker("option", "maxDate", dt);
		           }
	          });
	  }
	  else{
		   var selected = $("#startDate").val();
		   var dt = new Date(selected);
           dt.setDate(dt.getDate());
		   
		   var actualStartDate = $("#startDate").val();
		   var startDate = "";
           if(actualStartDate != null && actualStartDate != "" && actualStartDate != 'undefined'){
        	   startDate = new Date(actualStartDate);
        	   startDate.setDate(startDate.getDate() + 1); 
           }
           
           var actualEndDate = $("#endDate").val();
           var endDate = "";
           if(actualEndDate != null && actualEndDate != "" && actualEndDate != 'undefined'){
          	  endDate = new Date(actualEndDate);
	          endDate.setDate(endDate.getDate() - 1); 
           }
           
 		   $("#endDate").datepicker("option", "minDate", startDate);
	       $("#startDate").datepicker("option", "maxDate", endDate);
		  
	  }
  } 
</script>