<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 
<!-- Content Area -->
      <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Expenses Category</h2>
          </div>
          <div class="pull-right brud-crum">Home &raquo; Expenses Category</div>
        </section>
        
		<!-- Timesheet -->

<div class="timesheet_out">

<div class="project_wrap">

<div class="project_tabs_inn_head">
  <a href="javascript:void(0)" class="addcon-pop"> <i class="glyphicon glyphicon-plus"></i>Add Category</a> 

  <!-- Popup Div -->
  <div class="collapse-div-adduser col-md-4"> <i class="fa fa-close close-div"></i>
	<div class="form-horizontal clearfix">
  
	  <div class="clearfix mb10">
		<div class="col-md-12">
		  <input type="text" class="form-control input-sm" value="" placeholder="Category Name *">
		</div>
	  </div>
	  <div class="clearfix mb10">
		<div class="col-md-12">
			<div class="btn-group btn_Block pull-left">
			  <button type="button" class="btn btn-sm btn-primary active" id="percentageBtn" value="percentage" onclick="getRewardType('Percentage')">%</button>
			  <button type="button" class="btn btn-sm btn-primary" id="flatBtn" value="flat" onclick="getRewardType('Flat')">Flat</button>
			  <input name="rewardType" id="rewardType" value="Percentage" type="hidden">
			</div>
		  <input type="text" class="form-control input-sm ExpAmFild" value="" placeholder="Amount *">
			<div class="clearfix"></div>
		</div>
	  </div>	  
	  <div class="clearfix mb10">
		<div class="col-md-12">
		  <textarea rows="2" placeholder="Description" class="form-control"></textarea>
		</div>
	  </div>
	  <div class="clearfix mr">
		<button type="submit" class="btn btn-sm btn-primary pull-right">Add</button>
	  </div>
	</div>
  </div>
  <!-- Popup Div --> 

</div>
    <div class="project_wrap_table">
    	<table>
        	<tbody>
			</tbody><thead>
            	<tr>
                    <th>Category Name</th>
                    <th>Amount</th>
                    <th>Type</th>
                    <th>Descrption</th>
                    <th>Action</th>
                </tr>
			</thead>	
                <tbody>
                <tr>
                	<td><div>Category 1</div></td>
                    <td><div>5000</div></td>
                    <td><div>Flat</div></td>
                    <td><div>Descrption Descrption</div></td>
                    <td>
                    	<div class="tbldata-dotdot_50">
                   		 	<i class="fa fa-pencil-square-o"></i>
                    		 <i class="glyphicon glyphicon-trash"></i>
						</div>
                    </td>
                </tr> 
                <tr>
                	<td><div>Category 1</div></td>
                    <td><div>5000</div></td>
                    <td><div>Flat</div></td>
                    <td><div>Descrption Descrption</div></td>
                    <td>
                    	<div class="tbldata-dotdot_50">
                   		 	<i class="fa fa-pencil-square-o"></i>
                    		 <i class="glyphicon glyphicon-trash"></i>
						</div>
                    </td>
                </tr> 
                <tr>
                	<td><div>Category 1</div></td>
                    <td><div>5000</div></td>
                    <td><div>Flat</div></td>
                    <td><div>Descrption Descrption</div></td>
                    <td>
                    	<div class="tbldata-dotdot_50">
                   		 	<i class="fa fa-pencil-square-o"></i>
                    		 <i class="glyphicon glyphicon-trash"></i>
						</div>
                    </td>
                </tr> 
                <tr>
                	<td><div>Category 1</div></td>
                    <td><div>5000</div></td>
                    <td><div>Flat</div></td>
                    <td><div>Descrption Descrption</div></td>
                    <td>
                    	<div class="tbldata-dotdot_50">
                   		 	<i class="fa fa-pencil-square-o"></i>
                    		 <i class="glyphicon glyphicon-trash"></i>
						</div>
                    </td>
                </tr> 
                <tr>
                	<td><div>Category 1</div></td>
                    <td><div>5000</div></td>
                    <td><div>Percentage</div></td>
                    <td><div>Descrption Descrption</div></td>
                    <td>
                    	<div class="tbldata-dotdot_50">
                   		 	<i class="fa fa-pencil-square-o"></i>
                    		 <i class="glyphicon glyphicon-trash"></i>
						</div>
                    </td>
                </tr> 
                <tr>
                	<td><div>Category 1</div></td>
                    <td><div>5000</div></td>
                    <td><div>Percentage</div></td>
                    <td><div>Descrption Descrption</div></td>
                    <td>
                    	<div class="tbldata-dotdot_50">
                   		 	<i class="fa fa-pencil-square-o"></i>
                    		 <i class="glyphicon glyphicon-trash"></i>
						</div>
                    </td>
                </tr>
                <tr>
                	<td><div>Category 1</div></td>
                    <td><div>5000</div></td>
                    <td><div>Flat</div></td>
                    <td><div>Descrption Descrption</div></td>
                    <td>
                    	<div class="tbldata-dotdot_50">
                   		 	<i class="fa fa-pencil-square-o"></i>
                    		 <i class="glyphicon glyphicon-trash"></i>
						</div>
                    </td>
                </tr> 
                <tr>
                	<td><div>Category 1</div></td>
                    <td><div>5000</div></td>
                    <td><div>Percentage</div></td>
                    <td><div>Descrption Descrption</div></td>
                    <td>
                    	<div class="tbldata-dotdot_50">
                   		 	<i class="fa fa-pencil-square-o"></i>
                    		 <i class="glyphicon glyphicon-trash"></i>
						</div>
                    </td>
                </tr> 
                <tr>
                	<td><div>Category 1</div></td>
                    <td><div>5000</div></td>
                    <td><div>Flat</div></td>
                    <td><div>Descrption Descrption</div></td>
                    <td>
                    	<div class="tbldata-dotdot_50">
                   		 	<i class="fa fa-pencil-square-o"></i>
                    		 <i class="glyphicon glyphicon-trash"></i>
						</div>
                    </td>
                </tr> 
                <tr>
                	<td><div>Category 1</div></td>
                    <td><div>5000</div></td>
                    <td><div>Percentage</div></td>
                    <td><div>Descrption Descrption</div></td>
                    <td>
                    	<div class="tbldata-dotdot_50">
                   		 	<i class="fa fa-pencil-square-o"></i>
                    		 <i class="glyphicon glyphicon-trash"></i>
						</div>
                    </td>
                </tr>	
            </tbody>
        </table>
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
		
      </div>  
	
  <!--   Error Message   -->
  <div class="vendor_wrap_mask"></div>
  <div class="vendor_error">
    <textarea placeholder="Enter message"></textarea>
    <a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>
  
  <!--   Error Message   --> 

		
      </section>
      <!-- Content Area Ends--> 

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
		// Error pop 
		$(document).ready(function() {
		    $(".glyphicon-trash").click(function(){
				$(".vendor_error").slideToggle(200);
				$(".vendor_wrap_mask").fadeIn(200);
			});
			$(".vendor_error a").click(function(){
				$(".vendor_error").slideUp(200);
				$(".vendor_wrap_mask").fadeOut(200);
			});
		});	
	</script>
 
	<script>
		// Error pop ExpShow
		$(document).ready(function() {
		    $(".ExpShow").click(function(){
				/*$(".ExpDe").slideToggle(100); fa-plus-square-o*/
				$(this).parent().parent().next().slideToggle(100);
				$(this).find("i").toggleClass("closExp");
			});
		});	
	</script>  
                
	<script>
		$(document).ready(function() {
		    $("#hover").mouseenter(function(){
				$(".app_error_mes").addClass("app_error_mes_active");
			});
		    $("#hover").mouseleave(function(){
				$(".app_error_mes").removeClass("app_error_mes_active");
			});	
		});
	</script>    
    	
   
	
	<script>
		$(".addcon-pop").click(function(){
			 $(".collapse-div-adduser").toggleClass("show-div");
		 });
		 $(".close-div").click(function(){
			 $(this).parent().removeClass("show-div");
			 });
		 $(".addcon-pop, .collapse-div-adduser").click(function(e){
			 e.stopPropagation();
		 });
		 $('html').click(function(){
		    $(".collapse-div-adduser").removeClass("show-div");
		   });		
	</script>


 
<jsp:include page="../masterFooter.jsp" />