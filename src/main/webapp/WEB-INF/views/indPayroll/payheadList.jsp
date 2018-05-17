<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section class="content-wraper contentWraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Payroll</h2>
  </div>
  <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/payrollConfiguration'/>">Payroll Configure</a> &raquo; Payhead List</div>
</section>
 <div class="clearfix"></div>
 
  <div class="tablelist-wrap">
   
    <div class="timesheet_head">
      <ul>
        <li>
               <a href="<c:url value='${appPath}/addPayhead'/>" class="addcon-pop"><i class="fa fa-plus paddingRight"></i>Add</a>
               
         </li>
      <li>
      <a href="#" class="addcon-pop"><i class="fa fa-plus paddingRight"></i>Salary Setup</a><small class="text-muted ml">|</small>
      </li>
        <li></li>
        <div class="clearfix"></div>
      </ul>
      <div class="clearfix"></div>
      <div class="remove_search">
        <input type="text" placeholder="Search" />
      </div>
    </div>
    
<div class="styleTables">
    	<table>
			<thead>
				<tr>
					<th>Pay head</th>
					<th>short code</th>
					<th>Type</th>
					<th>Taxable</th>
					<th>Action</th>
				</tr>
			</thead>	
			<tbody>
			   	<tr>
                    <td><div class="proj_show"><span>HRA</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div>HHSD</div></td>
                    <td><div>Earning</div></td>
                    <td><div>Yes</div></td>
                    <td><div class="tbldata-dotdot_50">
                   		 	<i class="fa fa-pencil-square-o"></i>
                    		 <i class="glyphicon glyphicon-trash"></i>
						</div>
               		</td>
                </tr>
			   	<tr>
                    <td><div class="proj_show"><span>HRA</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div>Earning</div></td>
                    <td><div>HHSD</div></td>
                    <td><div>Yes</div></td>
                    <td><div class="tbldata-dotdot_50">
                   		 	<i class="fa fa-pencil-square-o"></i>
                    		 <i class="glyphicon glyphicon-trash"></i>
						</div>
               		</td>
                </tr>      			
            </tbody>
        </table>
	</div>
    
    <nav>
      <ul id="paging" class="pagination pull-right mr">
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
</section>


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

<script type="text/javascript">
	$(function () {
		$('#datetimepicker5').datetimepicker({
			locale: 'ru'
		});
	});
</script>

<script>	
	$("#aa").click(function() {
	  $(".subMenuView").slideToggle(100);
	});
</script>

<jsp:include page="../masterFooter.jsp" />