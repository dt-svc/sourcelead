<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style>
.validation_p p.jquery_form_error_message {
    bottom: -26px;
}
</style>
<!-- this script is for Tasks list functionality at Projects screen -->
<script>
	function projectDocuments(projectId, status) {
		var result = "";
		$("#tasksLoadingImg").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var sourceleadUrl = '${sourceleadUrl}';
		var status = status;
		$.ajax({
					type : "POST",
					url : sourceleadUrl	+ "/getProjectDocumentsList?${_csrf.parameterName}=${_csrf.token}&projectId="+ projectId,
					success : function(data) {
						var json = JSON.parse(data);

						var result = "";
						result = result + '<div class="project_tabs_inn_head">';
							result = result	+ '<span class="pad-sea">';
							result = result	+ '<a href="javascript:void(0)" onclick="addProjectDocument('+projectId+');"><i class="glyphicon glyphicon-plus"></i>Add Documents</a>';
							result = result  +'     <div id="popUpDiv" class="edit-details col-md-4"> ';
							result = result  +'     </div>  '; 
							result = result	+ '</span>';
						result = result	+ '<input type="hidden" id="projectStatus" value="'+status+'">';
						
						
						result = result	+ '</div><div class="project_tabs_inn_table project_tabs_inn_table_a">';
						result = result	+ '<div id="tasksLoadingImg"></div>';
						result = result + '<table id="projectTaskPagination" class="display" cellspacing="0" width="100%">';
						result = result + '<thead>';
						result = result + '<tr id="projectTaskTableId">';
						result = result + '<th>Document Name</th>';
						result = result + '<th>Document</th>';
						result = result + '<th>Document Description</th>';
						result = result + '<th>Action</th>';
						result = result + '</tr>';
						result = result + '</thead>';
						result = result + '<tbody>';
						for (var i = 0; i < json.length; i++) {
							var report = json[i];
							var description = "";
							if(report.description != null && report.description != '' && report.description !="undefined"){
								description = report.description
							}else{
								description = "--";
							} 
							result = result + '<tr>';
							result = result+ '<td><div class="tbldata-dotdot-me"><span title="'+report.fileName+'" >'+ report.fileName+ '</span></div></td>';
							result = result+ '<td><div class="tblminwidth-50"><a href="javascript:void(0)" onclick="projectDocumentView(\''+report.Id+'\',\''+report.fileName+'\');" class="fa fa-eye ml10 paddingRight"></a><a class="paddingLeft glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadProjectDoc?porjectDocumentId='+report.Id+'\"></a></div></td>';
							result = result+ '<td><div class="tbldata-dotdot-big"><span title="'+description+'" >'+description+ '</span></div></td>';
							result = result	+ '<td><div><a class="glyphicon glyphicon-pencil padddigRight" title="Edit" onclick="getDocument('+projectId+', '+report.Id+');"></a><i title="Delete" class="glyphicon glyphicon-trash paddingLeft" onclick="removeProjectDocuments('+report.Id+','+projectId+');"></i></div></td>';
							result = result + '</tr>';

						}
						result = result + '</tbody>';
						result = result + '</table>   ';
						result = result + '</div>';
						$("#tab-1").empty();
						$("#tab-3").empty();
						$("#tab-2").empty();
						$("#tab-4").empty();
						$("#tasksLoadingImg").empty();
						$("#tab-3").append(result);
						$("#tab-3").show();
						
						$(".project-tabs-menu a").click(
								function(event) {
									event.preventDefault();
									$(this).parent().addClass("current");
									$(this).parent().siblings().removeClass(
											"current");
									var tab = $(this).attr("href");
									$(".project_tabs_inn").not(tab).css(
											"display", "none");
									$(tab).fadeIn();
								});
						taskPagination();

					}
				});

	}
</script>

<script>
function addProjectDocument(projectId) {
	var result = "";
	result = result  +'<!-- Popup Div -->';
	result = result  +'<form role="form"  id="projectDocumentType_form" name="projectDocumentType_form" method="post" > ';
	result = result  +'<i class="fa fa-close close-div"  onClick="closePopup();"></i> ';
	result = result  +'<div class="form-horizontal clearfix"> ';
	result = result  +'   ';
	
// 	result = result + '<div class="col-md-6 mb10 clearfix"><lable class="paddingRight">Is Confidential ?</label> <input type="checkbox" name="isclient" value="N" class="pull-right" /></div>';
	result = result+'<div class="clearfix mb10" id="uploadDocDiv">';
	result = result+'<div class="col-md-12 validation_p">';
	result = result+'<div class="input-group positionRelative" id="uploadFileGroup">';
	result = result+'<label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
	result = result+'<input type="file" name="uploadBtn" id="uploadBtn" style="display: none;"  multiple="" class="upload">';
	result = result+'</span> </label>';
	result = result+'<input type="text" class="form-control" readonly id="uploadFile" name="docFile" placeholder="Upload Document..." field-name="Document" data-validation="required validate_Space validate_docimgpdf">';
	result = result+'</div>';
	result = result  +'<div class="clearfix"></div>';
	result = result+'</div>';  
	result = result+'</div>'; 
 
	result = result+'<div class="clearfix mb10 mt25">';
	result = result+'<div class="col-md-12">';
	result = result+'<textarea rows="2" placeholder="Description" value="" id="description" name="description" data-validation-optional="true" data-validation="validate_length length1-250"  field-name="Description" class="form-control"></textarea>';
	result = result+'</div>';
	result = result+'</div>';  
	
	
	result = result  +'<div class="clearfix mr" id="docAddButton">';
    result = result +'<input type="button" value="Upload" name="btnAddPr" id="save_doc" class="btn btn-sm btn-primary pull-right" onClick ="storeProjectDocument('+projectId+');" />'; 
	result = result  +'</div>';
	result = result  +'</div>';
	result = result  +'</form>';
	$("#popUpDiv").empty();
	$("#popUpDiv").append(result);
	
	$(".edit-details").toggleClass("edit-details-active");
	 $(".close-div").click(function(){ 
		 $(this).parent("div.edit-details").removeClass("edit-details-active");
		 });
	 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
		 e.stopPropagation();
	 });
	 
	 /* upload file script to show selected file name*/
	    document.getElementById("uploadBtn").onchange = function () {
	    	$('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''))
	   }; 
	    
	    /* upload file script for size validation 2MB*/
	/*     var selectedFile = document.getElementById("uploadBtn");
	    selectedFile.addEventListener("change", function() {
	      var docSize = this.files[0].size;
	      $("#uploadFileGroup").find('p.jquery_form_error_message').remove();
	      if(docSize > 2097152){
	    	  document.getElementById("uploadFile").value = '';
	    	  $("#uploadFileGroup").append("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </p>");
	      }
	    }); */
 
	}
</script>

<script>
function getDocument(projectId, documentId){
	var sourceleadUrl ='${sourceleadUrl}';
		$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getProjectDocument?${_csrf.parameterName}=${_csrf.token}&documentId="+documentId, 	   
   		    success: function(data) {
   		    	var json = JSON.parse(data);
   		    	var result = "";
   		    	var description = "";
   		    	if(json.description != null && json.description !='' && json.description != 'undefined'){
   		    		description = json.description;
   		    	}
	   		 	result = result  +'<!-- Popup Div -->';
	   		 	result = result  +'<form role="form"  id="editProjectDocumentType_form" name="editProjectDocumentType_form" method="post" > ';
	   		 	result = result  +'<i class="fa fa-close close-div"  onClick="closePopup();"></i> ';
	   		 	result = result  +'<div class="form-horizontal clearfix"> ';
	   		 	result = result  +'   ';
	   		 	
	   		 	result = result+'<div class="clearfix mb10" id="uploadDocDiv" style="display: none;">';
	   		 	result = result+'<div class="col-md-12 validation_p">';
	   		 	result = result+'<div class="input-group positionRelative" id="uploadFileGroup">';
	   		 	result = result+'<label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
	   		 	result = result+'<input type="file" name="uploadBtn" id="uploadBtn" style="display: none;"  multiple="" class="upload">';
	   		 	result = result+'</span> </label>';
	   		 	result = result+'<input type="text" class="form-control" readonly id="uploadFile" name="docFile" placeholder="Upload Document..." field-name="Document">';
	   		 	result = result+'</div>';
	   		 	result = result+'</div>';  
	   		 	result = result+'</div>'; 
	   		  
	   			result = result+'<div class="clearfix mb10" id="docNameDiv">';
	   			result = result+'<div class="col-md-12 mt">';
	   			result = result+'<label id="docsName" name="docName" class="text-primary tbldata-dotdot-me pull-left paddingRight" title="'+json.fileName+'">'+json.fileName+'</label><span id="delete_doc" onclick="removeDocNameDiv();"><i class="cursorPointer text-danger paddingLeft fa fa-trash removeIcon" title="Delete"></i></span>';
	   			result = result+'</div>';
	   			result = result+'</div>';
	   			
	   		 	result = result+'<div class="clearfix mb10 mt25">';
	   		 	result = result+'<div class="col-md-12">';
	   		 	result = result+'<textarea rows="2" placeholder="Description" value="" id="description" name="description" data-validation-optional="true" data-validation="validate_length length1-250"  field-name="Description" class="form-control">'+description+'</textarea>';
	   		 	result = result+'</div>';
	   		 	result = result+'</div>';  
	   		 	
	   		 	result = result  +'<div class="clearfix mr" id="docAddButton">';
	   		     result = result +'<input type="button" value="Upload" name="btnAddPr" id="save_doc" class="btn btn-sm btn-primary pull-right" onClick ="editStoreProjectDocument('+projectId+','+documentId+');" />'; 
	   		 	result = result  +'</div>';
	   		 	result = result  +'</div>';
	   		 	result = result  +'</form>';
	   		 	$("#popUpDiv").empty();
	   		 	$("#popUpDiv").append(result);
	   		 	
	   		 	$(".edit-details").toggleClass("edit-details-active");
	   		 	 $(".close-div").click(function(){ 
	   		 		 $(this).parent("div.edit-details").removeClass("edit-details-active");
	   		 		 });
	   		 	 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
	   		 		 e.stopPropagation();
	   		 	 });
	   		 	 
	   		 	 /* upload file script to show selected file name*/
	   		 	    document.getElementById("uploadBtn").onchange = function () {
	   		 	    	$('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''))
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
   		    }
	     });
}
</script>

<script type="text/javascript">
	function storeProjectDocument(projectId){
			 if ($('#projectDocumentType_form').validate(false, validationSettings)){
				var frm = $('#projectDocumentType_form').serializeFormJSON();
				var con = JSON.stringify(frm);
				con = con.replace(/[{}]/g, "");
				var form = $('#projectDocumentType_form')[0];
	      		var formData = new FormData(form); 
				var sourceleadUrl = '${sourceleadUrl}';
				$("#save_doc").attr("disabled", "disabled");
         		$("#save_doc" ).addClass('button').val('Processing..');
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
						url : sourceleadUrl+"/saveProjectDocument?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&projectId="+projectId,
						success : function(result) {
								closePopup();
								$("#popUpDiv").removeClass("edit-details-active");
								projectDocuments(projectId);
						},
					}); 
					return true;
			 }else{ 
					return false;
			 } 
		}
		$('body').on('blur', '#projectDocumentType_form', function() {
		$("#projectDocumentType_form").showHelpOnFocus();
		$("#projectDocumentType_form").validateOnBlur(false, validationSettings);
	});
		
</script>

<script type="text/javascript">
	function editStoreProjectDocument(projectId,documentId){
			 if ($('#editProjectDocumentType_form').validate(false, validationSettings)){
				var frm = $('#editProjectDocumentType_form').serializeFormJSON();
				var con = JSON.stringify(frm);
				con = con.replace(/[{}]/g, "");
				var form = $('#editProjectDocumentType_form')[0];
	      		var formData = new FormData(form); 
				var sourceleadUrl = '${sourceleadUrl}';
				$("#save_doc").attr("disabled", "disabled");
         		$("#save_doc" ).addClass('button').val('Processing..');
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
						url : sourceleadUrl+"/saveProjectDocument?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&projectId="+projectId+"&documentId="+documentId,
						success : function(result) {
								closePopup();
								$("#popUpDiv").removeClass("edit-details-active");
								projectDocuments(projectId);
						},
					}); 
					return true;
			 }else{ 
					return false;
			 } 
		}
		$('body').on('blur', '#editProjectDocumentType_form', function() {
		$("#editProjectDocumentType_form").showHelpOnFocus();
		$("#editProjectDocumentType_form").validateOnBlur(false, validationSettings);
	});
		
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
<script>
var validationSettings = {
        errorMessagePosition : 'element',
        borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
     }; 


function closePopup(){
	 $("#popUpDiv").removeClass("edit-details-active");
}
</script>
<script>
function removeDocNameDiv(){
	document.getElementById("uploadDocDiv").style.display="block";
	document.getElementById("docNameDiv").style.display="none";
	$("#uploadFile").attr("data-validation", "required validate_Space validate_docimgpdf");  
}
</script>

<script>
function removeProjectDocuments(documentId,projectId){
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/terminateProjectDoc?${_csrf.parameterName}=${_csrf.token}&porjectDocumentId="+documentId, 	   
		    success: function(data) {
		    	projectDocuments(projectId);
		    },
     });

}


</script>
<script>
function projectDocumentView(documentUUID,title){
	 var sourceleadUrl ='${sourceleadUrl}';
		if(documentUUID != null && documentUUID != ""){
			window.open(sourceleadUrl+'/viewProjectDocument/'+title+'?documentUUID='+documentUUID,title,"height=600,width=600");
		}
}

</script>
<script> 	
// Edit pop
<script type="text/javascript">
$(document).ready(function() {
    $(".glyphicon-edit").click(function(){
		$(".settPop").slideDown(200);
		$(".vendor_wrap_mask").fadeIn(200);
	});
	$(".settPop i, .btn-default").click(function(){
		$(".settPop").slideUp(200);
		$(".vendor_wrap_mask").fadeOut(200);
	});
});		
	
</script> 
<!-- Add Task Script start -->

<script>
function taskPagination(){  
    $('#projectTaskPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"autoWidth": false,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    } );
}
</script>







