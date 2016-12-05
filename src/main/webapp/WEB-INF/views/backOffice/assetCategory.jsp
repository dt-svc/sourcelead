<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page trimDirectiveWhitespaces="true" %>

<script type="text/javascript" src="jquery-1.2.6.min.js"></script>
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="resources/js/formvalidator.js"></script>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>

<link rel="stylesheet" href="resources/css/chosen.min.css">



<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/bootstrap-select.min.js"></script>
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css">
<link rel="stylesheet" href="resources/css/chosen.min.css">
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 <script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>

<script>
$(document).ready(function(){
	getAssetCategoryList();
});
</script> 

<style>
.table-wrap{
      overflow-y: visible;
  }
</style>
                         
                         
<section class="content-wraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Category</h2>
  </div>
  <div class="pull-right brud-crum">Home &raquo; Back Office &raquo; Category</div>
</section>
<div class="timesheet_out">
  <div class="vendor_wrap">
    <div class="timesheet_head">
      <ul>
        <li> <a href="javascript:void(0)" class="addcon-pop" onClick="addCategoryDoc();"><i class="fa fa-plus"></i>Add Categories</a> 
          <!-- Add Asset Popup -->
           <div id="popUpDiv"> 
           </div>
        </li>
        <li>
        </li>
        <div class="clearfix"></div>
      </ul>
      <div class="clearfix"></div>
    </div>
    <div class="require_wrap_table table-wrap">
    <div id="popUp"></div>
    <table id="assetCategoryList" class="display" cellspacing="0" width="100%"> </table>
     
    </div>
  </div>
  <div class="side_mask">
    <div class="proj_close"></div>
  </div>
  </section>
  
  
  
  <script>
	function getAssetCategoryList(){
		
		var sourceleadUrl ='${sourceleadUrl}';
		
		  $.ajax({
			   	type: "POST",
			    url: sourceleadUrl+"/getAssetCategoryList?${_csrf.parameterName}=${_csrf.token}", 	   
	   		    success: function(data) {
	   		    	
	   		    	var json = JSON.parse(data);
	   	        	var result = "";
	   	        	result = result +'<thead>';
	   	        	result = result +'<tr>';
	   	        	result = result +'	<th>Category</th>';
	   	        	result = result +'  <th>Description</th>';
	   	        	result = result +'  <th>Created By</th>';
	   	        	result = result +'  <th>Date</th>';
	   	        	result = result +' <th>Action</th>';
	   	        	result = result +'</tr>';
	   	        	result = result +'	</thead>';
	   	        	result = result +'<tbody>';
	   	        	
		   	     	for (var i=0; i<json.length; i++)
	   				{  
		   	     	    var report = json[i];
		   	     	    
		   	     	    var description = '';
		   	     	    if(report.description != null && report.description != '' && report.description != 'undefined') {
		   	     		   description = report.description;
		   	     	    }else{
		   	     	       description = "----";
		   	     	    }
		   	     	    
			    	    result = result+'<tr id="category'+report.id+'">';
			   	     	result = result +'<td>';
			   	        result = result +'<a href="javascript:void(0)" class="tbldata-dotdot">';
				   	    result = result +'<div class="proj_show"> <span class="tbldata-dotdot-sm">'+report.categoryName+'</span> <i class="fa fa-binoculars"></i> </div>';
					   	result = result +'</a>';
					   	result = result +'</td>';
					   	result = result +'<td><div class="tbldata-dotdot-vsm">'+description+'</div></td>';
					   	result = result +'<td><div class="tbldata-dotdot-vsm">'+report.createdUser+'</div></td>';
					   	result = result +'<td><div class="tbldata-dotdot-vsm" title="'+report.dateStr+'">'+report.dateStr+'</div></td>';
					   	result = result +'<td><div>';
					   	if(report.defaultCategory !=1){
					   		if(report.status == 0){
					   			result = result +'In-Active';
					   		}
					   		else{
					   		result = result +'<a href="javascript:void(0)" class="addcon-pop1"  onClick="deleteCategory('+report.id+');"><i class="fa fa-trash removeIcon"></i>';
					   		}
					   		result = result +'&nbsp;|&nbsp;<a href="javascript:void(0)" class="addcon-pop1" onClick="addCategoryDoc();editCategory('+report.orgLocationId+','+report.id+');"><i class="glyphicon glyphicon-pencil" ></i></div>';
					   	}else{
					   		result = result +'---';
					   	}
					   		
					   
					   	result = result +'</div></td>';
					   	result = result +'</tr>';
	   				}
		   	     	
	   	        	
	   	        	result = result +'</tbody>';
	   	    	    $("#assetCategoryList").empty();  
	   	    	    $("#assetCategoryList").append(result)
	   	    	    pagination();
	   	    	 $(".addcon-pop1").click(function(){
	   	    		 $(".collapse-div-adduser").toggleClass("show-div");
	   	    		 $(".collapse-div").removeClass("show-div");
	   	    	 });
	   	    	    
	   		    	
	   		    }
		  });
	}
</script>

<script>
function pagination(){  
    $('#assetCategoryList').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
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
$(".addcon-pop").click(function(){
	 $(".collapse-div-adduser").toggleClass("show-div");
	 $(".collapse-div").removeClass("show-div");
 });
 $(".close-div").click(function(){
	 $(this).parent().removeClass("show-div");
	 });
 $(".addcon-pop, .adddoc-pop, .collapse-div-adduser, .collapse-div, #save_button").click(function(e){
	 e.stopPropagation();
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

<script type="text/javascript">
	function addCategory(catLocationId,categoryId){
			var validationSettings = {
			        errorMessagePosition : 'element',
			        borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
		         }; 
			 if ($('#addCategory_form').validate(false, validationSettings)){
				var frm = $('#addCategory_form').serializeFormJSON();
				var con = JSON.stringify(frm);
		        con = con.replace(/[{}]/g, "");
		        var jsonData = encodeURIComponent(con);
				$("#save_button").attr("disabled", "disabled");
				$( "#save_button" ).addClass('button').val('Processing..');
				var sourceleadUrl = '${sourceleadUrl}';
				$("#addCategory_form").attr("disabled", "disabled");
				//It will escape all the special characters
				$.ajax({
						type : "POST",
						url : sourceleadUrl+"/saveCategory?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&categoryId="+categoryId+"&catLocationId="+catLocationId,
						success : function(result) {
							 if(result.indexOf('Sign In') >= 0){
					    		location.replace(sourceleadUrl);
					    	 }
		            		 else if (result.indexOf('exception') >= 0) {
		            			 location.replace(sourceleadUrl+"/errorPage");
		            		 }
		            		 else{ 
		            			  document.getElementById("popUpDiv").style.display="none"; 
		            			 getAssetCategoryList();
		            		  } 
						},
					});
					return true;
			 }else{ 
					return false;
			 } 
		}
		$('body').on('blur', '#addCategory_form', function() {
		$("#addCategory_form").showHelpOnFocus();
		$("#addCategory_form").validateOnBlur(false, validationSettings);
	});
</script>
<script>
function closePopup(){
	document.getElementById("popUpDiv").style.display="none";
}
</script>

<script>
function closePopupDemo(){
	$("#popUpDiv").hide();
}
</script>

<script>
function editCategory(catLocationId,categoryId){
	 var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/getCategoryById?${_csrf.parameterName}=${_csrf.token}&categoryId="+categoryId+"&catLocationId="+catLocationId, 	   
		    success: function(data) {
		    	var json = JSON.parse(data);
		    	if( data.indexOf('Sign In') >= 0){
	    		location.replace(sourceleadUrl);
	    	 	}
    		 else if (data.indexOf('exception') >= 0) {
    			 location.replace(sourceleadUrl+"/errorPage");
    		 }
    		 else{
    			 $("#update_button").show(); 
    			 $("#save_button").hide();
    			 
    			 
    			 if(json.categoryName != null && json.categoryName != "undefined" && json.categoryName != ""){
    				 document.getElementById("categoryName").value = json.categoryName;
    			 }  			 
    			 
    			 if(json.description != null && json.description != "undefined" && json.description != ""){
    				 document.getElementById("description").value = json.description;
    			 }
    			 
    			 $("#update_button").attr("onclick", "addCategory("+catLocationId+","+categoryId+");");
    		  }
		    }
     }); 
}
</script>
          

<script>
function addCategoryDoc(){
	var result = "";
	result = result  +'<!-- Popup Div -->';
	result = result  +'	<form role="form"  id="addCategory_form" name="addCategory_form" method="post" > ';
	result = result  +'	          <div class="collapse-div-adduser col-md-4 category"><i class="fa fa-close close-div" onClick="closePopup();"></i>';
	result = result  +'				  <div class="clearfix mb10"></div>';
	result = result  +'	            <!-- Laption popup div -->';
	result = result  +'	            <div class="clearfix">';
	result = result  +'              <div class="form-horizontal clearfix">';
	result = result  +'	                <div class="clearfix mb10">';
	result = result  +'	                  <div class="col-md-12" id="categoryNameDiv">';
	result = result  +'	                    <input type="text" class="form-control input-sm" field-name="Category name" id="categoryName" data-validation="required validate_Space validate_length length2-100" name="categoryName" onkeyup = "validateCategoryName();" placeholder="Category Name *">';
	result = result  +'	                  </div>';
	result = result  +'	                </div>';
	result = result  +'	                <div class="clearfix mb10">';
	result = result  +'	                  <div class="col-md-12">';
	result = result  +'	                    <textarea class="form-control" rows="2" data-validation-optional="true" placeholder="Description"  data-validation="validate_length length5-500" id="description" name="description"></textarea>';
	result = result  +'	                  </div>';
	result = result  +'	                </div>';
		
	result = result  +'	                <div class="clearfix mr">';
	result = result  +'	                  <div class="pull-right">';
	result = result  +'		                 <button type="button" id="update_button" style="display: none;" class="btn btn-sm btn-primary ">Update</button>';
	result = result  +'	                    <button type="button" class="btn btn-sm btn-primary" value="Add" name="btnAddPr" id="save_button" onclick="addCategory(null,null);">Add</button>';
	result = result  +'	                    <button type="button" class="btn btn-sm btn-default ml10" onclick="closePopupDemo();">Cancel</button>';
	result = result  +'	                  </div>';
	result = result  +'	                </div>';
	result = result  +'	              </div>';
	result = result  +'	            </div>';
	result = result  +'	          </div>';
	result = result  +'	          </form>';
	$("#popUpDiv").empty();
	$("#popUpDiv").append(result);
	 document.getElementById("popUpDiv").style.display="block";
}
</script>
<script type="text/javascript">
function validateCategoryName(){
	var categoryName = document.getElementById("categoryName").value;
	$("#save_button").removeAttr("disabled");
	$('input#categoryName').removeAttr("class record-exist record-exist-errormsg");
	$('input#categoryName').attr('class','valid form-control');
	$('#categoryNameDiv').find('p.jquery_form_error_message').remove();
	var sourceleadUrl = '${sourceleadUrl}';
	if (categoryName != null && categoryName != '') {
		//$("#save_client").attr("disabled", "disabled");
		// $( "#save_client" ).addClass('button').val('Processing..');
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/validateCategoryName?${_csrf.parameterName}=${_csrf.token}&categoryName="+categoryName,
			success : function(data) {
				var result = data;
				if (result == true) {
					//duplicate client
					$("#save_button").removeAttr("disabled");
			        $("#save_button").attr("disabled", "disabled");
					$("input#categoryName").after( "<p class='jquery_form_error_message'>" + categoryName + " is already existed </span>");
					document.getElementById("categoryName").setAttribute( 'record-exist', 'yes');
					document.getElementById("categoryName").setAttribute( 'record-exist-errorMsg', categoryName +"  already existed ");
					$('input#categoryName').removeAttr( "record-exist record-exist-errorMsg");
					
				}else {
					//New WebSite class="form-control"
					//$("#save_client").removeAttr("disabled");
					$("#save_button").removeAttr("disabled");
					$('input#categoryName').removeAttr("class record-exist record-exist-errormsg");
					$('input#categoryName').attr('class','valid form-control');
					$('#categoryNameDiv').find('p.jquery_form_error_message').remove();
					$("#save_button").click(function() {
						
					});
				}
			},

		});
	}
  }
</script>  


<script type="text/javascript">
function deleteCategory(categoryId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_category' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure ?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='terminateCategory("+categoryId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_category").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

 
<script type="text/javascript">
function terminateCategory(categoryId){
	var sourceleadUrl = '${sourceleadUrl}';
	if (categoryId != null && categoryId != '') {
		$("#category"+categoryId).html("<td colspan='5'  id='loading"+categoryId+"'></td>");
		$("#loading"+categoryId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/terminateCategory?${_csrf.parameterName}=${_csrf.token}&categoryId="+categoryId,
			success : function(data) {
				$("#popUp").empty();
				$(".project_inn_mask").fadeOut(200);
					document.getElementById("category"+categoryId).remove();
				},
		});
	}
  }
</script>  


<script type="text/javascript">
function cancel(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

</body>
</html>