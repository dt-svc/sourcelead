<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link href="/resources/css/summernote-new.css" rel="stylesheet" type="text/css">
<script src="/resources/js/summernote.min.js"></script>



<div class="wraper">

<!-- Content Area -->
<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Add Blog</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='/app/blogs'/>">Blogs</a> &raquo; Add Blog</div>
  </section>
  <div class="content-area content-new clearfix">
   <div id="blogLoading"></div>
  <form:form id="add_blog_form" name="add_blog_form" method="post">
    <div class="row form-horizontal mt myForm">
      <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
        
         <c:if test="${empty sourceleadBlog.blogUUID}">
        <div class="form-group">
          <label class="col-md-3">Blog Title<span class="text-danger paddingLeft">&nbsp;*</span></label>
          <div class="col-md-9" id="blogTitleDiv">
            <input type="text" name="blogTitle" id="blogTitle" class="form-control" field-name="Blog Title"
								data-validation="required validate_length length1-500"
								  onblur="validateBlogTitle();">
          </div>
        </div>
        </c:if> 
        
        <c:if test="${!empty sourceleadBlog.blogUUID}">
        <div class="form-group">
          <label class="col-md-3">Blog Title <span class="text-danger paddingLeft">&nbsp;*</span></label>
          <div class="col-md-9" id="blogTitleDiv">
            <input type="text" name="blogTitle" id="blogTitle" class="form-control" field-name="Blog Title"
								data-validation="required validate_length length1-500"
								value="${sourceleadBlog.blogTitle}"  onblur="validateBlogTitle('${sourceleadBlog.blogUUID}');">
          </div>
        </div>
        </c:if>
        
        <div class="form-group">
			<label class="col-md-3">Blog Category</label>
			<div class="col-md-9 mySkillsUl">
				<select  data-placeholder="Select Blog Category"
					class="chosen-select form-control" name="categoryId"
					id="categoryId" data-validation-optional="true">
					
					<c:forEach var="blogCategory" items="${blogCatslist}" >
					  <option  ${sourceleadBlog.categoryId.id  == blogCategory.id ? 'selected' : ''} value="${blogCategory.id}">${blogCategory.categoryName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		<c:if test="${empty sourceleadBlog.fileName}">			
	        <div class="form-group" >
	          <label class="col-md-3">Image</label>
	          <div class="col-md-9">
	           <div class="input-group col-md-12" style="margin:0 auto">
	            <label class="input-group-btn" onchange="uploadFileDoc();"> <span class="btn btn-danger" ><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
	              <input type="file" style="display: none;" multiple id="uploadBtn"  class="upload" >
	              </span> </label>
	            <input type="text" class="form-control" readonly id="uploadFile" name="docFile" placeholder="Upload Resume ...">
	          </div>
	        </div>
	        </div>
        </c:if>
        
        <c:if test="${!empty sourceleadBlog.fileName}">	
        <div class="form-group" id= "uploadImgGroup123" style="display: none;">
          <label class="col-md-3">Image</label>
          <div class="col-md-9">
           <div class="input-group col-md-12" style="margin:0 auto">
            <label class="input-group-btn" onchange="uploadFileDoc();"> <span class="btn btn-danger" ><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
              <input type="file" style="display: none;" multiple id="uploadBtn"  class="upload" >
              </span> </label>
            <input type="text" class="form-control" readonly id="uploadFile" name="docFile" placeholder="Upload Resume ...">
          </div>
        </div>
        </div>
        
        <div class="col-md-6" id="uploadImgNameDiv" >
				 <label id="uploadImgName" name="uploadDocumentName" class="text-primary tbldata-dotdot-vsm pull-left" title="${sourceleadBlog.fileName}" >${sourceleadBlog.fileName}</label>&nbsp;&nbsp;<span id="delete_shiftAFile" onclick="removeBlogImg(${sourceleadBlog.id});"><i class="fa fa-trash removeIcon"></i></span>
				 <input type="hidden" id="uploadExisitngFile" name="uploadExisitngFile" value="${sourceleadBlog.fileName}">
		</div>
     </c:if>
        
        <div class="form-group">
          <label class="col-md-3">Keywords</label>
          <div class="col-md-9">
                
                <select class="form-control" field-name="Keywords" multiple data-placeholder="Enter Keywords And Press Enter-Key Or Comma(,) Or Space To Add New Keywords" id="blogKeywords" name="keywords" field-name="Keywords" >
                  <c:forEach var="blogKeywords" items="${keywordsArray}">
                    <option  selected value="${blogKeywords}">${blogKeywords}</option>
                  </c:forEach>
                </select>
          </div>
        </div>
       
        <div class="form-group">
          <label class="col-md-3">Published On</label>
          <div class="col-md-9">
				<input type="text" name="publishOn"   class="form-control calendar" id="publishOn" value="${publishon}">
          </div>
        </div>
        
        </div>
        </div>
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<div class="form-group row">
				<label class="col-md-12">Description <span class="text-danger paddingLeft">&nbsp;*</span></label>
				<div class="col-md-12">
					<textarea rows="2" class="form-control" name="description" id="summernote" field-name="Description" >${sourceleadBlog.description}</textarea>
					<div id="editor"></div>
				</div>
			</div>  
		</div>
    
        <div class="clearfix"></div>
         <div class="form-footer mt">
      <div class="col-lg-12">
        <div class="pull-right">
          <input type="button" value="Publish" class="btn btn-primary" id="publish" onclick="saveBlog('published');">
           <input type="hidden" value="${sourceleadBlog.blogUUID}" id="blogUUID">
          <input type="button" value="Save" class="btn btn-info ml mr" id="save" onclick="saveBlog('saved');">
          <input type="button" value="cancel" id="cancel" onClick="javascript:window.location.href = '<c:url value='${appPath}/blogs'/>'" class="btn btn-default">
        </div>
      </div>
    </div>
    </form:form>
    </div>
</section>

	</div>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script type="text/javascript">
function saveBlog(saveType) {
	var blogUUID = $("#blogUUID").val();
	var ee = $("#summernote").val($('.note-editable').html()); 
	
	if ($('#add_blog_form').validate(false, validationSettings)) {
	  	var formData = $($('#add_blog_form')[0]).serialize();
		$("#blogLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.post(sourceleadUrl+"/addBlog?"+tokenParamName+"="+tokenValue+"&blogUUID="+blogUUID+"&blogStatus="+saveType, formData, function(data) {
			if(data == true){
				location.replace(sourceleadUrl+ "/blogs");
			}
		}); 
		return true;
		} else {
			return false;
		}
}

$('body').on('blur', '#add_blog_form', function() {
	$("#add_blog_form").showHelpOnFocus();
	$("#add_blog_form").validateOnBlur(false, validationSettings);
});
</script>

<script>
function uploadFileDoc(){
	fileUpload("uploadBtn", "sourceleadBlogs", "add_blog_form");
}
</script>

<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
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
 		
$("#blogKeywords").trigger("chosen:updated");
$("#publishOn").datetimepicker(); 



$(document).ready(function() {
	$('#summernote').summernote();
	$('.note-editor').css({"min-height":"200px"});
	$('.note-editable').css({"min-height":"200px"});
});
</script>
	
	
<script>
	 /* upload file script to show selected file name*/
    document.getElementById("uploadBtn").onchange = function () {
    	$('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''))
   }; 
</script>

<script type="text/javascript">
function removeBlogImg(blogId){
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/deleteBlogImg?"+tokenParamName+"="+tokenValue+"&blogId="+blogId, 	   
		    success: function(data) {
		    	$("#uploadImgNameDiv").hide();
		     	$("#uploadImgGroup123").show();
		     	$("#uploadExisitngFile").val('');
		     	$('#uploadFile').attr("data-validation-optional","true"); 
		     	$("#uploadFile").attr("data-validation", "validatedoc"); 
		    },
		    });		
	
}
</script>


  
<script type="text/javascript">
$(function() { 
	tagIt("blogKeywords", 'Enter Keywords And Press Enter-Key Or Comma(,) Or Space To Add New Keywords', 'tagItBykeyForEmails','skills');
});
</script>
 
 <script>
function validateBlogTitle(blogUUID){
	if(blogUUID != null && blogUUID != "" && blogUUID != "undefined"){
		blogUUID = blogUUID;
	}else{
		blogUUID = "";
	}
	var blogTitle = $('#blogTitle').val(); 
	$('#blogTitleDiv').find('p.jquery_form_error_message').remove();
	$.ajax({
			type : "POST",
			url : sourceleadUrl + "/validateBlogTitle?"+tokenParamName+"="+tokenValue+"&blogUUID="+blogUUID+"&blogTitle="+blogTitle,
			success : function(data) {
				if(data == true){
	        		  $('#blogTitleDiv').find('p.jquery_form_error_message').remove();
					  $("input#blogTitle").after("<p class='jquery_form_error_message'>"+blogTitle+" is Already Existed</span>");
					  document.getElementById("blogTitle").setAttribute('record-exist','yes');
					  document.getElementById("blogTitle").setAttribute('record-exist-errorMsg',blogTitle+' is Already Existed'); 
	        	}
	        	else{
	        		 $('input#blogTitle').removeAttr( "record-exist record-exist-errormsg");
	        		 $('#blogTitleDiv').find('p.jquery_form_error_message').remove(); 
	        	}
			},
		});
	 
}
</script>  
  
 
