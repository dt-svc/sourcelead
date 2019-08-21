<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Content Area -->

<script type="text/javascript">
window.onload = function() {
	getBlogCategoryList()
};
</script>

<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Category List</h2>
    </div>
   <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/blogs">Blogs</a> &raquo;Blog Category</div>
  </section>
  <div class="content-area content-new clearfix">
   <div class="timesheet_head minHeight50 list-borderright">
						<ul>
							<li><a href="javascript:void(0)" title="Add Blog" onClick="addBlogCategory();"><i class="fa fa-plus paddingRight"></i>Add Category</a></li>
							<li><a href="${SourceLeadWeb_sourceleadUrl}/addBlog" title="Add Blog"><i class="fa fa-plus paddingRight"></i>Add Blog</a></li>
							
						</ul>
						<div class="clearfix"></div>
 						<div class="clearfix"></div>
					<div class="remove_search">
					<input placeholder="Search" type="text" id="blogCategoryTableSearch">
					</div>
 </div>
 <div id="popUpDivBlogCategory" class="edit-details col-md-4"></div>
  <!-- <div id="popUpDiv" class="edit-details col-md-4"> </div>   -->
 
	
	<div class="project_wrap_table datatable_filter backgroundWhite">
	<div id="blogCategoryList"></div>
	</div>
	
  </div>
</section>

</div>
<div class="sideMask"></div>

<script>
function addBlogCategory(uuid){ 
	var result = '';
	result = result+'<form role="form"  id="blog_category_form" name="blog_category_form" method="post" > ';
	result = result+ '<i class="fa fa-close close-div"  onClick="closeBlogCategoryPopup();"></i>';
	result = result+ '<div class="form-horizontal clearfix">';
	
	result = result+ '<div class="clearfix mb10">';
	result = result+ '<div class="col-md-12" id="categoryNameDiv">';
	result = result+ '<label>Category Name<b class="text-danger">*</b> </label>';
	result = result+ '<input type="text" class="form-control"  placeholder="Category Name" id="categoryName" name="categoryName" field-name="Category Name" data-validation="required validate_Space validate_AlphaNumaric validate_length length1-250"  onblur="validateBlogCategoryName(\''+uuid+'\');">';
	result = result+ ' </div>';
	result = result+ '  </div>';
	
	result = result+ '<div class="clearfix mb10">';
	result = result+ '<div class="col-md-12">';
	result = result+ '<label>Description</label>';
	result = result + '<textarea cols="3" class="form-control" name="description" id="description" field-name="Description" data-validation="validate_length length1-500" data-validation-optional="true">';
    result = result + '</textarea>';
	result = result+ '                </div>';
	result = result+ '              </div> ';
	result = result+ '              <div class="clearfix mr">';
	if(uuid != null && uuid !="" && uuid != 'undefined'){
		result = result+ '<button type="button" id="update_BlogCategory" class="btn btn-sm btn-primary pull-right" onclick="saveBlogCategory(\''+uuid+'\');">Update</button>';
	}else{
		result = result+ '<button type="button" id="add_BlogCategory" class="btn btn-sm btn-primary pull-right" onclick="saveBlogCategory();">Save</button>';
	}
	result = result+ '              </div>';
	result = result+ '            </div>';
	result = result+ '</form>';
	$("#popUpDivBlogCategory").empty();
	$("#popUpDivBlogCategory").append(result);
	
	if(uuid != null && uuid !="" && uuid != 'undefined'){
		getBlogCategoryByUUID(uuid);
	}
	
	
	
	$(".edit-details").toggleClass("edit-details-active");
	$(".close-div").click(function(){
		$(this).parent("div.edit-details").removeClass("edit-details-active");
		 });
	 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
		 e.stopPropagation();
	 });
	 
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
          });
	
   }
</script>

 
<script>
	function addCategory() {
		$(".sideMask").fadeIn();
		$(".modal-category").fadeIn();
	}
	function closeCategory() {
		$(".sideMask").fadeOut();
		$(".modal-category").fadeOut();
	}
</script>
	
<script>
function closeBlogCategoryPopup(){
	
	 $("#popUpDivBlogCategory").removeClass("edit-details-active");
}
</script>

<script type="text/javascript">
function saveBlogCategory(uuid) {
	if ($('#blog_category_form').validate(false, validationSettings)) {
	  	var formData = $($('#blog_category_form')[0]).serialize();
		$("#blogCategoryLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.post(sourceleadUrl+"/addBlogCategory?"+tokenParamName+"="+tokenValue+"&categoryUUID="+uuid, formData, function(result) {
			
			if (result && result.response === "INVALID_DATA") {
				$("#blogCategoryLoading").hide();
			    showError(result.responseMessage);
			} else if (result.response === "AWKWARD") {
			    location.href = "/errorPage";
			} else if (result.response === "SUCCESS") {
				location.replace(sourceleadUrl+ "/categoryList");
			} else if (result.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
			    showError("Operation failed, please try again");
			}
		}); 
		return true;
		} else {
			return false;
		}
}

$('body').on('blur', '#blog_category_form', function() {
	$("#blog_category_form").showHelpOnFocus();
	$("#blog_category_form").validateOnBlur(false, validationSettings);
});
</script>



<!-- Dynamic category list -->

<script>
function getBlogCategoryList(){
	
	var searchValue = $("#blogCategoryTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	var url = sourceleadUrl+"/blogCategoryList?"+tokenParamName+"="+tokenValue+"&searchValue="+searchValue;
	prepareBlogCategoryList(url);
}
</script>

<script>
function prepareBlogCategoryList(url){
	 	var result = "";
		result = result	+ '<div class="clearfix minHeight35 hidden">';
	    result = result	+ '<div class="remove_search">';
	    result = result +" <input type='text' placeholder='Search' id='blogCategoryTableSearch'/>";
		result = result	+ '</div>';
		result = result	+ '</div>';
		result = result	+ '<div class="clearfix">';
		result = result + '<table id="blogCategoryListView" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result  = result + '<th>Name</th>';
     	result  = result + '<th>Description</th>';
        result  = result + '<th>No.Blogs</th>'; 
     	result  = result + '<th>Created By</th>';
     	result  = result + '<th>Action</th>';
		result = result + '</tr>';
		result = result + '</thead>';
		result = result + '</table>';
		result = result	+ '</div>';
		$("#blogCategoryList").empty();
		$("#blogCategoryList").append(result);
		blogCategoryListPagination(url);
}
</script>

<script>
function blogCategoryListPagination(url){
	var colIndex = 0;
    $('#blogCategoryListView').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 10
        }),
        "columns": [
            { "data": "categoryName" },
            { "data": "description"},
             { "data": "blogCount"}, 
            { "data": "createdBy"},
            { "data": "Action"}
        ],
		 "columnDefs": [ {
		    	  "targets": colIndex++,
		          "data": "categoryName",
		          "render": function ( data, type, opt, meta ) {
		        	  var result = "";
		        	  if(opt.categoryName !=null && opt.categoryName !='' && opt.categoryName !='undefined'){
							result = result + ' <td><div class="tbldata-dotdot" title = "'+opt.categoryName+'">'+opt.categoryName+'</div></td>';
						}else{
							result = result + ' <td><div class="tbldata-dotdot">--</div></td>';
						}
		              return result;
		          		
		          }
		          },{
		        "targets": colIndex++,
		        "data": "description",
		        "render": function ( data, type, opt, meta ) {
		        	if(opt.description != null && opt.description != ''){
		        		description = opt.description;
					    }else{
					    	description = "--"
					    }
		        	  var result = "";
							result = result + ' <td><div class="tbldata-dotdot-100" title = "'+description+'">'+description+'</div></td>';
		        	 return result;
		        }
		      }, {
		          "targets": colIndex++,
		          "data": "blogCount",
		          "sortable": false
		          "render": function ( data, type, opt, meta ) {
		        	  var result = "";
		        	  result = result + ' <td><div class="tbldata-dotdot-100" title = "'+opt.blogCount+'">'+opt.blogCount+'</div></td>';
		        	  return result;
		          }
		        },{
			          "targets": colIndex++,
			          "data": "createdBy",
			          "render": function ( data, type, opt, meta ) {
			        	  var result = "";
			        	  result = result + ' <td><div class="tbldata-dotdot-100" title = "'+opt.createdBy+'">'+opt.createdBy+'</div></td>';
			        	  return result;
			          }
			        },{
		          "targets": colIndex++,
		          "data": "Action",
		          "sortable": false,
		          "render": function ( data, type, opt, meta ) {
		          	var result = "";
		          	 if(opt.isDefaultCategory == 'N' ){
							result = result + ' <td><div class="tbldata-dotdot-100"><a href="javascript:void(0)" class="text-warning" title="Edit" onclick="addBlogCategory(\''+opt.categoryUUID+'\');"><i class="fa fa-pencil"></i></a>&nbsp;&nbsp;<a href="javascript:void(0)" class="text-danger" title="Delete" onclick="deleteBlogCategory('+opt.id+');"><i class="fa fa-trash"></i></div></td>';
						}else{
							result = result + ' <td><div class="tbldata-dotdot-100">--</div></td>';
						}
			              return result;
		          }
		    }],
		    "scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"initComplete": function( settings, json ) {
	    		 $('#blogCategoryTableSearch').removeAttr('disabled');
	    	  },
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#blogCategoryListView").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
}

  $("#blogCategoryTableSearch").keydown(function(e) {
	   if(e.which === 13) {
		   getBlogCategoryList();
	   }
	});
</script> 

<script type="text/javascript">
function deleteBlogCategory(blogCategoryId) {
		 $.ajax({  
	          type : "POST",
	          url: sourceleadUrl+"/removeBlogCategoryByBlogId?"+tokenParamName+"="+tokenValue+"&blogCategoryId="+blogCategoryId,        
	          success :function(result) {

	        	  if (result && result.response === "INVALID_DATA") {
					    showError(result.responseMessage);
					} else if (result.response === "AWKWARD") {
					    location.href = "/errorPage";
					} else if (result.response === "SUCCESS") {
						location.replace(sourceleadUrl+"/categoryList");
					} else if (result.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
					    showError("Operation failed, please try again");
					}
					
	        },
	          
	     }); 
}

</script>

<!-- Update Blog Category -->

<script>
function getBlogCategoryByUUID(uuid) {
	$.ajax({	type : "POST",
				url : sourceleadUrl+ "/getBlogCategoryById?"+tokenParamName+"="+tokenValue+"&categoryUUID="+uuid,
				success : function(data) {
					
					if(data !=null){
	        			 
       					 if(data.categoryName != null && data.categoryName != "undefined" && data.categoryName != ""){
   	        				 $("input#categoryName").val(data.categoryName);
   	        			 }
	        			 
	        			 if(data.description != null && data.description != "undefined" && data.description != ""){
	        				 $("textarea#description").val(data.description);
	        			 }
	        	 }
					
			 },
						
			});
			}
</script>

<script>
function closePopup(){
	 $("#popUpDiv").removeClass("edit-details-active");
}
</script>
 
 <script>
function validateBlogCategoryName(uuid){
	 if(uuid != null && uuid != "" && uuid != "undefined"){
		 uuid = uuid;
		}else{
			uuid = "";
		}
	 var categoryName = $('#categoryName').val(); 
	 $('#categoryNameDiv').find('p.jquery_form_error_message').remove();
	$.ajax({
			type : "POST",
			url: sourceleadUrl+"/validateBlogCategoryName?"+tokenParamName+"="+tokenValue+"&categoryName="+categoryName+"&categoryUUID="+uuid, 
			success : function(data) {
				if(data == true){
	        		  $('#categoryNameDiv').find('p.jquery_form_error_message').remove();
					  $("input#categoryName").after("<p class='jquery_form_error_message'>"+categoryName+" is Already Existed</span>");
					  document.getElementById("categoryName").setAttribute('record-exist','yes');
					  document.getElementById("categoryName").setAttribute('record-exist-errorMsg',categoryName+' is Already Existed'); 
	        	}
	        	else{
	        		 $('input#categoryName').removeAttr( "record-exist record-exist-errormsg");
	        		 $('#categoryNameDiv').find('p.jquery_form_error_message').remove(); 
	        	}
			},
		});
	 
}
</script>   
