<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
var atsUrl ='${SourceLeadWeb_atsUrl}';
</script>


<!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Candidate Source</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Candidate Source</div>
    </section>
    <div class="project_wrap clearfix">
      <div class="timesheet_out" id="payrollRegister" style="display: block;">
        <div class="clearfix">
          <div class="positionRelative reportHead clearfix">
            <ul class="pull-right list-unstyled list-inline">
				<li>
				<a class="newAdBtn mr5" title="Add" onClick="addsourceHandler();">+ Source</a>	
					
				</li>
              <li class="">
                <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
                <span class="repSearchIcon" onclick="getDynamciUserList();"></span></li>
            </ul>
          </div>
          <div class="require_wrap_table clearfix">
            <table>
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Description</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>
					  <div class="tbldata-dotdot-verybig" title="Rajkumar Santhoshi Konidala"><a href="javascript:void(0)">Ramkumar Joshi Anamma</a></div>
					</td>
                <td>
					<div class="tbldata-dotdot-verybig" title="Get more fox in your inbox.  Sign up for user tips, booster packs and internet intel.">
						  Get more fox in your inbox.  Sign up for user tips, booster packs and internet intel.
					</div>
				</td>
                  <td>
                  <div class="width90px">
                  	<ul class="list-inline list-unstyled">
                  		<li><a href="javascript:void(0)"><i class="fa fa-pencil cursorPointer" title="Edit"></i></a></li>
                  		<li><i class="fa fa-trash cursorPointer" title="Delete"></i></li>
                  	</ul>	
                  </div>
                  </td>
                </tr>
				  <tr>
          <td>
					  <div class="tbldata-dotdot-verybig"><a href="questionnaire-view.html">Sample2</a></div>
					</td>
                <td>
					<div class="tbldata-dotdot-verybig" title="Get more fox in your inbox.  Sign up for user tips, booster packs and internet intel.">
						  Get more fox in your inbox.  Sign up for user tips, booster packs and internet intel.
					</div>
				</td>
                  <td>
                  <div class="width90px">
                  	<ul class="list-inline list-unstyled">
                  		<li><a href="editworkflow.html"><i class="fa fa-pencil cursorPointer" title="Edit"></i></a></li>
                  		<li><i class="fa fa-trash cursorPointer" title="Delete"></i></li>
                  	</ul>	
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
      
      <!-- Content Area Ends--> 
     </div>
  </section> 
      
      
      <!-- Source popup -->
<div class="mask"></div>
<div class="popupWrap popupWrap2" id="addSource" style="display: none;">
    <div class="popupHead clearfix"> Add Source
      <i class="closePop pull-right clPopClose" onClick="closesourceHandler();"></i>
    </div>
    <div class="clockWrap">

      <div class="form-group">
        <label class="clearfix">Name</label>
        <input type="text" class="form-control">
      </div>
      <div class="form-group">
          <label class="clearfix">Description</label>
          <textarea class="form-control" rows="3"></textarea>
        </div>


      <div class="clearfix"></div>
      <div class="clearfix mt">
        <div class="pull-right">
          <button type="buton" class="btn btn-primary mr">Save</button>
          <button type="buton" class="btn btn-default" onClick="closesourceHandler();">Cancel</button>
        </div>
      </div>

    </div>
    </div>
<!-- -->

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
function addsourceHandler() {
  $(".mask, #addSource").fadeIn();
}
function closesourceHandler() {
  $(".mask, #addSource").fadeOut();
}
</script>

<jsp:include page="masterFooter.jsp" />	

