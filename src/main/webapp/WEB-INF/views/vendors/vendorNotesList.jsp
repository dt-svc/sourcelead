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
		
		result = result + '</ul>';
		result = result  +'  <div class="clearfix"></div> ';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		result  = result + '   <div id="vendorNotesListDiv" ></div>';
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
	
   /*  vendorNotes(orgRelationId); */
    
}
</script>

 <script>
function vendorNotes(orgRelationId){
	var urlForm = "";
	
    	urlForm = "/getNotes?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationId;
	
	var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+urlForm,        
         success :function(data) {
	var output = "";
    if(data != null && data != 'undefined' && data != "" && data != '[]'){
    var json = JSON.parse(data);
	
	 output  = output + '   <div class="project_tabs_inn_head addUser-org"> ';
	  output  = output + '              <div class="project_tabs_inn_table vendor_wrap_inn_table">';
     for(var i=0;i<json.length;i++){
       	var report = json[i];
   	  	output = output+'<div class="comments-div comments-inner clearfix">';
   	    output = output+'    <div class="commentsimg">';
   	    output = output+'    <i class="fa fa-comments"></i>';
   	    output = output+'        <div class="commentsimg-inner">';
   	    output = output+'        	<img onerror="predefineProfileImage(this);" src="'+sourceleadUrl+'/displayUserImage/'+report.userId+'"/>';
   	    output = output+'        </div>';
   	    output = output+'    </div>';
   	    output = output+'     <div class="comments-content">';
   	    output = output+'         <div class="pull-left">';
   	    output = output+'         <p class="comment-by">';
   	    output = output+'            By <span class="text-danger">'+report.userName+'</span>';
   	    output = output+'            <span class="small">'+report.createdDateString+'</span></p>';
   	    output = output+'         <span class="commentText pull-left">'+report.comment+'</span>';
   	    output = output+'         </div>';
   	    output = output+'     </div>';
   	    output = output+'</div>';
     }
}else{
     var output ="";
    output = output+"   <div class='dataTables_empty'>"; 
	 output = output+"  </div> ";
}
output  = output + '              </div>';
output  = output + '        </div>';
	$("#vendorNotesListDiv").empty(); 
    $("#vendorNotesListDiv").append(output);
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
	var result = "";
	result = result  +'<!-- Popup Div -->';
	result = result  +'          <form role="form"  id="vendorNotes_form" name="vendorNotes_form" method="post" > ';
	result = result  +'          <input type="hidden" id="noteType" name="noteType" value="Vendor"> ';
	result = result  +'          <input type="hidden" id="orgRelationId" name="orgRelationId" value="'+orgRelationId+'"> ';
	result = result  +'          <i class="fa fa-close close-div"  onClick="closeDocPopup();"></i> ';
	result = result  +'                <div class="form-horizontal clearfix"> ';
	result = result+'	                  <div class="clearfix">';
	result = result+'	                    <div class="col-md-12">';
	result = result+'	                      <textarea rows="2" placeholder="Description" value="" id="noteDescription" name="noteDescription" data-validation="required validate_length length2-500" field-name="Description" class="form-control"></textarea>';
	result = result+'	                    </div>';
	result = result+'	                  </div>';
	result = result  +'                  </div>';                                                                                                                            
	
	result = result  +'                  <div class="clearfix mr mt" id="docAddButton"> ';
    result = result +'                     <input type="button" value="Add" name="btnAddPr" id="save_note" class="btn btn-sm btn-primary pull-right" onClick ="storeVendorNotes('+orgRelationId+');" />'; 
	result = result  +'                  </div> ';
	result = result  +'                </div>';
	
	result = result  +'                </form>';
	
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


<script type="text/javascript">
	function storeVendorNotes(orgRelationId){
		if ($('#vendorNotes_form').validate(false, validationSettings)){ 
				var frm = $('#vendorNotes_form').serializeFormJSON();
				var con = JSON.stringify(frm);
				con = con.replace(/[{}]/g, "");
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
							closeDocPopup();
							vendorNotes(orgRelationId);
							
						},
					});
					$('.add-comment').toggle(200);
					return true;
			 
		}else {
			return false;	
		}
		}
		$('body').on('blur', '#vendorNotes_form', function() {
		$("#vendorNotes_form").showHelpOnFocus();
		$("#vendorNotes_form").validateOnBlur(false, validationSettings);
		
	});
</script>

