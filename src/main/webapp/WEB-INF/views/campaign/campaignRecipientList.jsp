<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Recipient list</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Recipient list</div>
    </section>
    <div class="timesheet_out">
      <div class="vendor_wrap">
        <div class="timesheet_head candidateHead pull-right">
          <ul class="pull-right">
            <li class="hidden"><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop"></i>
              <div class="export_pop">
                <ul>
                  <li>PDF<i class="fa fa-file-pdf-o"></i></li>
                  <li>Excel<i class="fa fa-file-excel-o"></i></li>
                  <li>Word<i class="fa fa-file-word-o"></i></li>
                </ul>
              </div>
            </li>
            <li><a href="javascript:void(0)" title="" id="addRep" onclick="campaignRecipient(this);"><i class="fa fa-plus"></i>Add Recipients</a></li>
            <div class="clearfix"></div>
          </ul>
          <div class="clearfix"></div>
          <div class="remove_search">
            <input type="text" placeholder="search"/>
          </div>
        </div>
        
<div class="project_wrap_table">
    	<table>
			<thead>
            	<tr>
                	<th>Recipients name</th>
                    <th>No of Recipients</th>
                    <th>Action</th>
                </tr>
			</thead>	
                <tbody>
                <tr>
                	<td><div>Name one</div></td>
                    <td><div>20</div></td>
                    <td><div><i class="glyphicon glyphicon-pencil"></i><i class="glyphicon glyphicon-trash ml10"></i></div></td>
                </tr>
                <tr>
                	<td><div>Name one</div></td>
                    <td><div>20</div></td>
                    <td><div><i class="glyphicon glyphicon-pencil"></i><i class="glyphicon glyphicon-trash ml10"></i></div></td>
                </tr>
                <tr>
                	<td><div>Name one</div></td>
                    <td><div>20</div></td>
                    <td><div><i class="glyphicon glyphicon-pencil"></i><i class="glyphicon glyphicon-trash ml10"></i></div></td>
                </tr>
                <tr>
                	<td><div>Name one</div></td>
                    <td><div>20</div></td>
                    <td><div><i class="glyphicon glyphicon-pencil"></i><i class="glyphicon glyphicon-trash ml10"></i></div></td>
                </tr>
                <tr>
                	<td><div>Name one</div></td>
                    <td><div>20</div></td>
                    <td><div><i class="glyphicon glyphicon-pencil"></i><i class="glyphicon glyphicon-trash ml10"></i></div></td>
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
      
      <!-- Project --> 
    </div>
  </section>
  
  
  
<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->



<!--    <script>
// Error pop addSen
$(document).ready(function() {
    $("#addRep").click(function(){
		$(".popMask").fadeIn(200);
		$(".popUpReci").fadeIn(100);
	});
	$(".popCan").click(function(){
		$(".popMask").fadeOut(200);
		$(".popUpReci").fadeOut(100);
	});
});	
	   </script> -->

 


 
 

 
 
