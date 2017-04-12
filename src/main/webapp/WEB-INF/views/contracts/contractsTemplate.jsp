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
    <h2>Contract Template</h2>
  </div>
  <div class="pull-right brud-crum">Home &raquo; List by status</div>
</section>
	
<div class="content-area content-new clearfix">
 <div class="project_wrap">
	<div class="timesheet_head">
        	<ul>
				<li><a href="<c:url value="/addContractTemplate" />"><i class="fa fa-plus"></i>Add Template</a></li>	
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
                	<th>Template name</th>
                    <th>Template type</th>
                    <th>Description</th>
                    <th>Document</th>
                </tr>
			</thead>	
                <tbody>
                <tr>
                	<td><div>Template name</div></td>
                    <td><div>Template type</div></td>
                    <td><div>Description Description</div></td>
					<td><div><a href="" class="glyphicon glyphicon-paperclip pinCo"></a></div></td>
                </tr>
                <tr>
                	<td><div>Template name</div></td>
                    <td><div>Template type</div></td>
                    <td><div>Description Description</div></td>
					<td><div><a href="" class="glyphicon glyphicon-paperclip pinCo"></a></div></td>
                </tr>
                <tr>
                	<td><div>Template name</div></td>
                    <td><div>Template type</div></td>
                    <td><div>Description Description</div></td>
					<td><div><a href="" class="glyphicon glyphicon-paperclip pinCo"></a></div></td>
                </tr>
                <tr>
                	<td><div>Template name</div></td>
                    <td><div>Template type</div></td>
                    <td><div>Description Description</div></td>
					<td><div><a href="" class="glyphicon glyphicon-paperclip pinCo"></a></div></td>
                </tr>
                <tr>
                	<td><div>Template name</div></td>
                    <td><div>Template type</div></td>
                    <td><div>Description Description</div></td>
					<td><div><a href="" class="glyphicon glyphicon-paperclip pinCo"></a></div></td>
                </tr>
                <tr>
                	<td><div>Template name</div></td>
                    <td><div>Template type</div></td>
                    <td><div>Description Description</div></td>
					<td><div><a href="" class="glyphicon glyphicon-paperclip pinCo"></a></div></td>
                </tr>
                <tr>
                	<td><div>Template name</div></td>
                    <td><div>Template type</div></td>
                    <td><div>Description Description</div></td>
					<td><div><a href="" class="glyphicon glyphicon-paperclip pinCo"></a></div></td>
                </tr>
                <tr>
                	<td><div>Template name</div></td>
                    <td><div>Template type</div></td>
                    <td><div>Description Description</div></td>
					<td><div><a href="" class="glyphicon glyphicon-paperclip pinCo"></a></div></td>
                </tr>
                <tr>
                	<td><div>Template name</div></td>
                    <td><div>Template type</div></td>
                    <td><div>Description Description</div></td>
					<td><div><a href="" class="glyphicon glyphicon-paperclip pinCo"></a></div></td>
                </tr>
                <tr>
                	<td><div>Template name</div></td>
                    <td><div>Template type</div></td>
                    <td><div>Description Description</div></td>
					<td><div><a href="" class="glyphicon glyphicon-paperclip pinCo"></a></div></td>
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
      

<!-- Content Area Ends-->

<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->




