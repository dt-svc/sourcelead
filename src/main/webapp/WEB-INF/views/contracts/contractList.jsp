<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="resources/js/formvalidator.js"></script>
<!-- <script src="resources/js/chosen.jquery.min.js"></script> --> 


<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<!-- end files for autocomplete with tagit -->

<!-- Content Area -->
<section class="content-wraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>List by status</h2>
  </div>
  <div class="pull-right brud-crum">Home &raquo; List by status</div>
</section>

<section class="contrHead">
	<ul>
	  <li><a href="<c:url value='/contractListbyStatus'/>" class="active">Draft</a></li>
	  <!-- <li><a href="Listby_Status-waitingtosigned.html">Waiting to be signed</a></li>
	  <li><a href="Listby_Status-waitingforapproval.html">Waiting for Approval</a></li>
	  <li><a href="Listby_Status-finalised.html">Finalised</a></li>
	  <li><a href="Listby_Status-expired.html">Expired</a></li> -->
	  <div class="conBtn">
		  <span>Add<i class="fa fa-caret-down"></i></span>
		  <ul>
			  <li><a href="new-contract.html"><i class="fa fa-caret-right"></i>New Contract</a></li>
			  <li><a href="existing-contract.html"><i class="fa fa-caret-right"></i>Existing Contract</a></li>
		  </ul>
	  </div>
		<div class="clearfix"></div>
  </ul>
</section>
	
<div class="content-area content-new clearfix">
 <div class="project_wrap">
	<div class="timesheet_head">
        	<ul>
				<li id="export-pop"><i class="glyphicon glyphicon-export"></i>
                	<div class="export_pop">
                    	<ul>
                        	<li>PDF<i class="fa fa-file-pdf-o"></i></li>
                            <li>Excel<i class="fa fa-file-excel-o"></i></li>
                            <li>Word<i class="fa fa-file-word-o"></i></li>
                        </ul>
                    </div>
                </li>	
                <div class="clearfix"></div>
            </ul>
            <div class="clearfix"></div>
            
            <div class="remove_search">
            	<input placeholder="search" type="text">
            </div>              
            
        </div>
    <div class="project_wrap_table">
    	<table>
        	<tbody>
			</tbody><thead>
            	<tr>
                	<th>Id</th>
                    <th>Name</th>
                    <th>Manager</th>
                    <th>Date</th>
                    <th>Client</th>
                    <th>Team</th>
                    <th>Status</th>
                </tr>
			</thead>	
                <tbody><tr>
                	<td><div>BIS2616464165</div></td>
                    <td><div>Vannam</div></td>
                    <td><div>Mahesh</div></td>
                    <td><div>form 15/9 to 20/9</div></td>
                    <td><div>Client 1</div></td>
                    <td><div>Team a</div></td>
                    <td><div><b class="proj_active">Active</b></div></td>
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
</section>
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
				})
				$(document).ready(function(){
					$("#cd").click(function(){
						$(".clientDetails").toggleClass('hidden', 500);
					});
	
	});
 		</script> 
<script>
    $("#uasrch").selectpicker('refresh');
	
// Export Popup
$(document).ready(function() {
    $("#export-pop").click(function(){
		$(".export_pop").show();
	});
    $("html").click(function() {
        $(".export_pop").hide();
    });
    $(".export_pop, #export-pop").click(function(e) {
        e.stopPropagation();
    });	
});		
	
    </script>
<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->




