<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>



<link rel="stylesheet" href="resources/css/chosen.min.css">
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/jquery-ui.css">
<link href="resources/css/timesheet.css" rel="stylesheet">
<link href="resources/css/sourcelead-main.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/jquery.tree-multiselect.css">



<!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Create Role</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Create Role</div>
    </section>
    
   <!-- Role Persmission -->
    <section class="clearfix">
     <div class="select-role clearfix section-action-btns">
     <div class="clearfix mt">
	<select id="test-select" multiple="multiple">
		 <c:forEach var="action" items="${actionsList}">
		  <option value="${action.id}" data-section="${action.tabs.tabName}" >${action.actionName}</option>
	     </c:forEach>
    </select>
     </div>

        <div class="form-footer clearfix mt">
            <div class="col-lg-12">
                <div class="pull-right">
                 <input type="button" value="Save" id="save_client" onclick="saveActions();" class="btn btn-primary"> 
			     <input type="button" value="Cancel" onClick="javascript:location.href = '<c:url value='${sourceleadUrl}/orgRolesList'/>'" class="btn btn-default ml">
                </div>
            </div>
        </div>
        </div>
    </section>
   <!-- Role Persmission Ends -->

  </section>
  <!-- Content Area Ends--> 


<script src="resources/js/jquery-1.11.3.min.js"></script> 
<script src="resources/js/jquery-ui.min.js"></script>
<script src="resources/js/jquery.tree-multiselect.js"></script>



<jsp:include page="masterFooter.jsp" />	
<div class="backtotop" style="display: none;"><img src="resources/images/backtotop.png"></div>  



<!-- Include all compiled plugins (below), or include individual files as needed --> 

<script>
// Back To Top 
    $(window).scroll(function() {
        if ($(this).scrollTop() > 200) {
            $('.backtotop').fadeIn('slow');
        } else {
            $('.backtotop').fadeOut('slow');
        }
    });
    $('.backtotop').click(function() {
        $("html, body").animate({scrollTop: 0}, 500);
        return false;
    });
</script>

 <script type="text/javascript">
 // Multiselect script
      $("#test-select").treeMultiselect({ enableSelectAll: true, sortable: true });
    </script>
    
    
    
    <script type="text/javascript">
	function saveActions() {
		
		var actionIds = [];
		  
		 $("input[name='actionsList[]']:checked").each( function (i) {
			 actionIds[i] = $(this).val();
		});
		 
		
		alert(actionIds);
	}

</script>
    
    
    
    
    
    
    
    
    
    
    
    
</body>
</html>