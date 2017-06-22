<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style type="text/css">
.edit-details {
text-align:left;
}
.edit-details ul {
float:none;
}
.edit-details ul li {
float:none;
}

.addUser-org {
    overflow-x: auto;
}


#uploadFileGroup p.jquery_form_error_message{
	position: absolute;
	left:0;
	bottom:-16px;
	}
	.tblicons a{
	font-size:13px;
	}
	.tblicons a:first-child{
	margin-left: 0;
	}
	.tblicons .glyphicon-trash{
	margin-left: 5px;
	}
	.tblicons a{
	margin:0px 8px;
	padding: 0 !important;
	} 
	.vendor_wrap_inn_table table tr th:last-child{
	width:100px;
	}
	.removeIcon{
	font-size:22px; position:relative; top:-5px; margin-left:15px; color:#F22613; cursor: pointer;
	}
</style>

<div id="deleteDocpopUp"></div>

<script>
function vendorNotesList(orgRelationId){
	alert("vendor Req");
	var result = "";
	result = result  +'<div class="timesheet_head">';
	result = result  +'    <div class="project_tabs_inn_head addUser-org"> '; 
	result  = result + '<div class="success_pop" id="vendorDocSelector" style="display: none;">';
    result  = result + '<i class="fa fa-check-circle"></i><span id="vendorDocMsgDiv"></span>';
    result  = result + '</div>';
	    result = result + '<ul>';
	    
	    '<sec:authorize access="hasAnyRole(\"ROLE_CREATE_VENDOR_DOCUMENT\")">';
	        	result = result  +'    	<a href="javascript:void(0)" onClick="addNotes('+orgRelationId+');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Documents</a>                                                            ';
		 '</sec:authorize>';
		 
		 '<sec:authorize access="!hasAnyRole(\"ROLE_CREATE_VENDOR_DOCUMENT\")">';
		      result = result  +'    	<a href="javascript:void(0)" class="addUser-org" ><i class=""></i></a>                                                            ';
		 '</sec:authorize>';
		result = result  +'     <div id="popUpDiv" class="edit-details col-md-4"> ';
		result = result  +'     </div>  ';
		result = result  +'  <div class="clearfix"></div> ';
		result = result + '</ul>';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		result  = result + '   <div id="vendorDocumentsList" ></div>';
		result  = result + '   </div>';
	result = result+'	    </div>';
	result = result+'	    </div>';

    $("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty();  
    $("#tab-4").empty();  
    $("#tab-6").empty();  
    $("#tab-7").empty();  
    $("#tab-8").empty(); 
    $("#tab-8").append(result);  
	
    vendorNotes(orgRelationId);
    
}
</script>

<script>
function vendorNotes(orgRelationId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getclientDocumentsListByResponderLocationId?${_csrf.parameterName}=${_csrf.token}&responderLocationId="+vendorLocationId+"&sourceType=Vendor",        
          success :function(data) {
               /* if(data != null && data != 'undefined' && data != "" && data != '[]'){ */
	                	 $("#vendorDocumentsList").empty();
	                	 var result = "";
	                	 result = result +"<table id='clientDocumentsPagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result  = result + '           <th>Name</th>';
	                 	 result  = result + '           <th>Type</th>';
	                 	 result  = result + '           <th>Contact Person</th>';
	                 	 result  = result + '           <th>Attachment</th>';
	                 	 result  = result + '           <th>Start Date</th>';
	                 	 result  = result + '           <th>End Date</th>';
	                 	 result  = result + '           <th>Owner</th>';
	                 	 result  = result + '           <th>Description</th>';
	                 	 result  = result + '           <th>Action</th>';
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     var json = JSON.parse(data);
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
	                    	 result = result+'	 <td><div class="tblicons tblminwidth-75">';
	                    	 '<sec:authorize access="hasAnyRole(\"ROLE_DOWNLOAD_VENODR_DOCS\")">';
	                    	 result = result+'<a class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadOrgDocument?docId='+opt.id+'\"></a>';
	                    	 '</sec:authorize>'; 
	                    	  
	                    	 if(opt.id != null && opt.id != '' && opt.id != 'undefined'){
	                    		 
	                    		 '<sec:authorize access="hasAnyRole(\"ROLE_EDIT_VENDOR_DOCUMENT\")">';
	                    				 result = result+'<a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick="addNotes('+orgRelationId+');"></a>';
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
	                     documentpagination(); 
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
function documentpagination(){  
    $('#clientDocumentsPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
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
function addNotes(orgRelationId) {	
	alert("storeVendorNotes(");
	var result = "";
	result = result  +'<!-- Popup Div -->';
	result = result  +'          <form role="form"  id="vendorNotes_form" name="vendorNotes_form" method="post" > ';
	result = result  +'          <input type="hidden" id="noteType" name="noteType" value="Vendor"> ';
	result = result  +'          <input type="hidden" id="orgRelationId" name="orgRelationId" value="'+orgRelationId+'"> ';
	result = result  +'          <i class="fa fa-close close-div"  onClick="closeDocPopup();"></i> ';
	result = result  +'                <div class="form-horizontal clearfix"> ';
	result = result+'	                  <div class="clearfix">';
	result = result+'	                    <div class="col-md-12">';
	result = result+'	                      <textarea rows="2" placeholder="Description" value="" id="noteDescription" name="noteDescription" data-validation="validate_length length2-500" data-validation-optional="true" field-name="Description" class="form-control"></textarea>';
	result = result+'	                    </div>';
	result = result+'	                  </div>';
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                                                                                                                                                       ';
	
	result = result+'	                  <div class="clearfix mb" id="uploadDocumentDiv" style="display:none">';
	result = result+'	                    <div class="col-md-12">';
	result = result+'							<div class="input-group positionRelative" id="uploadFileGroup">';
	result = result+'	                          <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
	result = result+'	                            <input type="file" name="uploadBtn" id="uploadBtn" style="display: none;"  multiple="" class="upload">';
	result = result+'	                            </span> </label>';
	result = result+'	                          <input type="text" class="form-control" readonly id="uploadFile" name="docFile" placeholder="Upload File." field-name="Document" data-validation="required validate_Space validatedoc">';
	result = result+'	                        </div>';
	result = result+'	                    </div>  ';  
	result = result+'	                  </div>   ';
	
	result = result  +'                  <div class="clearfix mr mt" id="docAddButton">                                                                                                            ';
    result = result +'                     <input type="button" value="Add" name="btnAddPr" id="save_note" class="btn btn-sm btn-primary pull-right" onClick ="storeVendorNotes('+orgRelationId+');" />'; 
	result = result  +'                  </div>                                                                                                                               ';
	result = result  +'                </div>                                                                                                                                 ';
	result = result  +'                </form>                                                                                                                                ';
	$("#popUpDiv").empty();
	$("#popUpDiv").append(result);
	
	function educationStartDate(){
	}
	
	$('#startDate').datepicker({
	      dateFormat: 'dd-mm-yy',
	      changeMonth: true,
	  	  changeYear: true, 
	      onSelect: function (selected) {
	           // var dt = new Date(selected);
	           var dt = toDate("#startDate");
	           dt.setDate(dt.getDate() + 1);
	           $("#endDate").datepicker("option", "minDate", dt);
	        }
	  });
	  
	  $('#endDate').datepicker({
	      dateFormat: 'dd-mm-yy',
	      changeMonth: true,
	  	  changeYear: true, 
	        onSelect: function (selected) {
	           // var dt = new Date(selected);
	            var dt = toDate("#endDate");
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
<script>
var validationSettings = {
			        errorMessagePosition : 'element',
			        borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
		         }; 
</script>
<!-- <script type="text/javascript">
	function storeVendorNotes(orgRelationId){
			alert("save")
			 if ($('#vendorNotes_form').validate(false, validationSettings)){
				var frm = $('#vendorNotes_form').serializeFormJSON();
				var con = JSON.stringify(frm);
				con = con.replace(/[{}]/g, "");
				$("#save_note").attr("disabled", "disabled");
				$("#save_note" ).addClass('button').val('Processing..');
				var form = $('#vendorNotes_form')[0];
	      		var formData = new FormData(form); 
				var sourceleadUrl = '${sourceleadUrl}';
				$("#vendorNotes_form").attr("disabled", "disabled");
				
				//It will escape all the special characters
		        var jsonData = encodeURIComponent(con);
				alert("jsonData "+jsonData);
				$.ajax({
						type : "POST",
		        	    data: formData,
		        	    contentType : 'application/json; charset=utf-8',
		        	    enctype: 'multipart/form-data',
		    	        processData: false,
		    	        contentType:false,
		        	    dataType : 'json',
						url : sourceleadUrl+"/storeNotes?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
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
		            			vendorDocumentsList(orgRelationId);
		            			vendorDocMsgFadein();
		            		 }
						},
					});
					return true;
			 }else{ 
					return false;
			 } 
		}
		$('body').on('blur', '#vendorNotes_form', function() {
		$("#vendorNotes_form").showHelpOnFocus();
		$("#vendorNotes_form").validateOnBlur(false, validationSettings);
	});
</script>
 -->

<script type="text/javascript">
	function storeVendorNotes(orgRelationId){
			var validationSettings = {
			        errorMessagePosition : 'element',
			        borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
		         }; 
				var frm = $('#vendorNotes_form').serializeFormJSON();
				var con = JSON.stringify(frm);
				con = con.replace(/[{}]/g, "");
				alert("con"+con);
				var form = $('#vendorNotes_form')[0];
	      		var formData = new FormData(form); 
				var sourceleadUrl = '${sourceleadUrl}';
				$("#submit_comment").attr("disabled", "disabled");
         		$("#submit_comment" ).addClass('button').val('Processing..');
		        var jsonData = encodeURIComponent(con);
				$.ajax({
						type : "POST",
		        	    data: formData,
		        	    contentType : 'application/json; charset=utf-8',
		        	    enctype: 'multipart/form-data',
		    	        processData: false,
		    	        contentType:false,
		        	    dataType : 'json',
						url : sourceleadUrl+"/storeNotes?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
						success : function(result) {
							savedCommentAndCount(candidateId);
							 /* if( result.indexOf('Sign In') >= 0){
					    		location.replace(sourceleadUrl);
					    	 }
		            		 else if (result.indexOf('exception') >= 0) {
		            			 location.replace(sourceleadUrl+"/errorPage");
		            		 }
		            		 else{
						 		$("#popUpDivResume").removeClass("edit-details-active");
						 		candidateResumesList(candidateId);
		            		 } */
						},
					});
					$('.add-comment').toggle(200);
					return true;
			 /* }else{ 
					return false;
			 } */
		}
		$('body').on('blur', '#vendorNotes_form', function() {
		$("#vendorNotes_form").showHelpOnFocus();
		$("#vendorNotes_form").validateOnBlur(false, validationSettings);
		
	});
</script>

