<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


 <body>
<div class="wraper">

<script type="text/javascript">
window.onload = function() {
	getSourceleadBlogList()
};
</script>
<!-- Content Area -->
<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Blog List</h2>
    </div>
   <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Blog </div>
  </section>
  <div class="content-area content-new clearfix">
   <div class="timesheet_head minHeight50 list-borderright">
		<ul>
			<li><a href="/app/categoryList" title="Add Blog"><i class="fa fa-plus paddingRight"></i>Category</a></li>
			<li><a href="/app/addBlog" title="Add Blog"><i class="fa fa-plus paddingRight"></i>Add Blog</a></li>
		</ul>
	<div class="clearfix"></div>
	<div class="remove_search">
	<input placeholder="Search" type="text" id="blogTableSearch">
	</div>
 </div>
	<div class="project_wrap_table datatable_filter backgroundWhite">
	<div id="sourceleadBlogList"></div>
	</div>
  </div>
</section>
</div>
<!-- Dynamic Blogs List -->

<script>
function getSourceleadBlogList(){
	
	var searchValue = $("#blogTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	var url = sourceleadUrl+"/blogsList?"+tokenParamName+"="+tokenValue+"&searchValue="+searchValue;
	prepareBlogList(url);
}
</script>

<script>
function prepareBlogList(url){
	 	var result = "";
		result = result	+ '<div class="clearfix minHeight35 hidden">';
	    result = result	+ '<div class="remove_search">';
	    result = result +" <input type='text' placeholder='Search' id='blogTableSearch'/>";
		result = result	+ '</div>';
		result = result	+ '</div>';
		result = result	+ '<div class="">';
		result = result + '<table id="blogListView" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result  = result + '<th>Blog Title</th>';
     	result  = result + '<th>Category</th>';
     	result  = result + '<th>Posted By</th>';
     	result  = result + '<th>Published On</th>';
        result  = result + '<th>Status</th>';
     	result  = result + '<th>Action</th>';
		result = result + '</tr>';
		result = result + '</thead>';
		result = result + '</table>';
		result = result	+ '</div>';
		$("#sourceleadBlogList").empty();
		$("#sourceleadBlogList").append(result);
		blogListPagination(url);
}
</script>

<script>
function blogListPagination(url){
	var colIndex = 0;
    $('#blogListView').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 10
        }),
        "columns": [
            { "data": "blogTitle" },
            { "data": "categoryId"},
            { "data": "createdBy"},
            { "data": "publishedBy"},
            { "data": "status"},
            { "data": "Action"}
        ],
		 "columnDefs": [ {
		    	  "targets": colIndex++,
		          "data": "blogTitle",
		          "render": function ( data, type, opt, meta ) {
		        	  var result = "";
		        	  if(opt.blogTitle !=null && opt.blogTitle !='' && opt.blogTitle !='undefined'){
							result = result + ' <td><div class="tbldata-dotdot" title = "'+opt.blogTitle+'">'+opt.blogTitle+'</div></td>';
						}else{
							result = result + ' <td><div class="tbldata-dotdot">--</div></td>';
						}
		              return result;
		          		
		          }
		          },{
		        "targets": colIndex++,
		        "data": "categoryId",
		        "render": function ( data, type, opt, meta ) {
		        	if(opt.categoryId != null && opt.categoryId != ''){
		        		categoryId = opt.categoryId;
					    }else{
					    	categoryId = "--"
					    }
		        	  var result = "";
							result = result + ' <td><div class="tbldata-dotdot-100" title = "'+categoryId+'">'+categoryId+'</div></td>';
		        	 return result;
		        }
		      }, {
		          "targets": colIndex++,
		          "data": "createdBy",
		          "render": function ( data, type, opt, meta ) {
		        	  var result = "";
		        	  result = result + ' <td><div class="tbldata-dotdot-100" title = "'+opt.createdBy+'">'+opt.createdBy+'</div></td>';
		        	  return result;
		          }
		        }, {
		            "targets": colIndex++,
		            "data": "publishOn",
		            "sortable": false,
		            "render": function ( data, type, opt, meta ) {
		            	var result = "";
		            	if(opt.publishOn !=null && opt.publishOn !='' && opt.publishOn !='undefined'){
							result = result + ' <td><div class="tbldata-dotdot-sm" title = "'+opt.publishOn+'">'+opt.publishOn+'</div></td>';
						}else{
							result = result + ' <td><div class="tbldata-dotdot-sm">--</div></td>';
						}
			              return result;
		            }
		      },
		      {
		          "targets": colIndex++,
		          "data": "publishStatus",
		          "sortable": false,
		          "render": function ( data, type, opt, meta ) {
		          	var result = "";
		          	 if(opt.publishStatus !=null && opt.publishStatus !='' && opt.publishStatus !='undefined' && opt.publishStatus ==='Publish'){
							result = result + ' <td><div class="tbldata-dotdot-100" title = "'+opt.publishStatus+'"><span ><a href="javascript:void(0)" onclick="blogPublish('+opt.id+');">'+opt.publishStatus+'</span ></a></div></td>';
						}else if(opt.publishStatus !=null && opt.publishStatus !='' && opt.publishStatus !='undefined'){
							result = result + ' <td><div class="tbldata-dotdot-100" title = "'+opt.publishStatus+'">'+opt.publishStatus+'</div></td>';
						}else{
							result = result + ' <td><div class="tbldata-dotdot-100">--</div></td>';
						}
			              return result;
		          }
		    },{
		          "targets": colIndex++,
		          "data": "Action",
		          "sortable": false,
		          "render": function ( data, type, opt, meta ) {
		          	var result = ""; 
		          	 if(opt.status !=null && opt.status !='' && opt.status !='undefined'){
							result = result + ' <td><div class="tbldata-dotdot-100"><a href="<c:url value="${appPath}/blogPreview?blogUUID='+opt.blogUUID+'"/>" class="text-primary" title="View"><i class="fa fa-eye"></i></a>&nbsp;&nbsp;<a href="<c:url value="${appPath}/addBlog?blogUUID='+opt.blogUUID+'"/>" class="text-warning" title="Edit"  ><i class="fa fa-pencil"></i></a>&nbsp;&nbsp;<a href="javascript:void(0)" class="text-danger" title="Delete" onclick="deleteBlog('+opt.id+');"><i class="fa fa-trash"></i></div></td>';
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
	    		 $('#blogTableSearch').removeAttr('disabled');
	    	  },
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#blogListView").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
}

  $("#blogTableSearch").keydown(function(e) {
	   if(e.which === 13) {
		   getSourceleadBlogList();
	   }
	});
</script> 

<script type="text/javascript">
function deleteBlog(blogId) {
		 $.ajax({  
	          type : "POST",
	          url: sourceleadUrl+"/removeSourceleadBlogByBlogId?"+tokenParamName+"="+tokenValue+"&blogId="+blogId,        
	          success :function(result) {
	        	  
	        	  if (result && result.response === "INVALID_DATA") {
					    showError(result.responseMessage);
					} else if (result.response === "AWKWARD") {
					    location.href = "/errorPage";
					} else if (result.response === "SUCCESS") {
						location.replace(sourceleadUrl+"/blogs");
					} else if (result.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
					    showError("Operation failed, please try again");
					}

					/* if(data != null && data != 'undefined' && data != ""){
	             		
						 location.replace(sourceleadUrl+"/blogs");
	               }  */
	        },
	          
	     }); 
}

</script>


<script type="text/javascript">
    $(function () {
        $('#datetimepicker3').datetimepicker({
            format: 'LT'
        });
    });
</script> 
<script type="text/javascript">
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
</script> 

 <script>
	 function blogPublish(id){
		 var sourceleadUrl = '${sourceleadUrl}';
			$.ajax({
				type : "POST",
				url : sourceleadUrl+ "/publishBlog?${_csrf.parameterName}=${_csrf.token}&blogId="+id,
				success : function(data) {
					location.replace(sourceleadUrl+ "/blogs");
				}
			});	
		 
	 }
 </script>
 
</body>

<jsp:include page="../masterFooter.jsp" />