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


<script>
$(document).ready(function(){
	getAssetCategoryList();
});
</script> 
                         
                         
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
        <li> <a href="javascript:void(0)" class="addcon-pop"><i class="fa fa-plus"></i>Add Categories</a> 
          <!-- Add Asset Popup -->
          <div class="collapse-div-adduser col-md-4 category"><i class="fa fa-close close-div"></i>
			  <div class="clearfix mb10"></div>
            <!-- Laption popup div -->
            <div class="clearfix">
              <div class="form-horizontal clearfix">
                <div class="clearfix mb10">
                  <div class="col-md-12">
                    <input type="text" class="form-control input-sm" value="" placeholder="Category Number *">
                  </div>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-12">
                    <textarea class="form-control" rows="2" placeholder="Description"></textarea>
                  </div>
                </div>

                <!-- Shift A Fields -->
                <div class="clearfix mr">
                  <div class="pull-right">
                    <button type="submit" class="btn btn-sm btn-primary">Add</button>
                    <button type="submit" class="btn btn-sm btn-default ml10">Cancel</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!-- Add Category Popup --> 
        </li>
        <li>
        </li>
        <div class="clearfix"></div>
      </ul>
      <div class="clearfix"></div>
      <div class="remove_search">
        <input type="text" placeholder="search" />
      </div>
    </div>
    <div class="require_wrap_table table-wrap">
    <table id="assetCategoryList" class="display" cellspacing="0" width="100%"> </table>
      <!-- <table>
        <thead>
          <tr>
            <th>Category</th>
            <th>Description</th>
            <th>Created By</th>
            <th>Date</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>
             <a href="javascript:void(0)" class="tbldata-dotdot">
              <div class="proj_show"> <span class="tbldata-dotdot-sm">Laptop</span> <i class="fa fa-binoculars"></i> </div>
              </a>
              </td>
            <td><div class="tbldata-dotdot-vsm">It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.</div></td>
            <td><div class="tbldata-dotdot-vsm">Abhibhushpam Kumar Sarangapani</div></td>
            <td><div class="tbldata-dotdot-vsm" title="HitechCity, Hyderabad">25/09/2016</div></td>
			  <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
          </tr>
        </tbody>
      </table> -->
    </div>
    <nav>
      <ul id="paging" class="pagination">
        <li><a href="#">Previous</a></li>
        <li><a href="#">2</a></li>
        <li><a href="#">3</a></li>
        <li><a href="#">4</a></li>
        <li><a href="#">5</a></li>
        <li class="disabled"><a href="#">6</a></li>
        <li data-page="last"><a href="#">Next</a></li>
      </ul>
      <div class="clearfix"></div>
    </nav>
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
		   	     	    
			    	    result = result+'<tr>';
			   	     	result = result +'<td>';
			   	        result = result +'<a href="javascript:void(0)" class="tbldata-dotdot">';
				   	    result = result +'<div class="proj_show"> <span class="tbldata-dotdot-sm">'+report.categoryName+'</span> <i class="fa fa-binoculars"></i> </div>';
					   	result = result +'</a>';
					   	result = result +'</td>';
					   	result = result +'<td><div class="tbldata-dotdot-vsm">'+report.description+'</div></td>';
					   	result = result +'<td><div class="tbldata-dotdot-vsm">'+report.createdUser+'</div></td>';
					   	result = result +'<td><div class="tbldata-dotdot-vsm" title="'+report.dateStr+'">'+report.dateStr+'</div></td>';
					   	result = result +'<td><div><i class="glyphicon glyphicon-trash"></i></div></td>';
			   	        result = result +'</tr>';
	   				}
		   	     	
	   	        	
	   	        	result = result +'</tbody>';
	   	    	    $("#assetCategoryList").empty();  
	   	    	    $("#assetCategoryList").append(result)
	   	    	   
	   	    	    
	   		    	
	   		    }
		  });
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
 $(".addcon-pop, .adddoc-pop, .collapse-div-adduser, .collapse-div").click(function(e){
	 e.stopPropagation();
 });
 $('html').click(function(){
    $(".collapse-div-adduser").removeClass("show-div");
	$(".collapse-div").removeClass("show-div");
   });
	
	</script>

</body>
</html>