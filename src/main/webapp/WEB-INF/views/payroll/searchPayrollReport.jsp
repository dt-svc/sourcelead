<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="/resources/js/jquery.multi-select.js"></script>

<!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Employee Filer</h2>
      </div>
      <div class="pull-right brud-crum">Home&raquo;Employee Filter</div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="clearfix form-horizontal inner-content">
            <div class="col-md-2 mb">
              <label>Select a Year</label>
              <div class="clearfix">
                <select class="form-control chosen-select">
                  <option>Tier 1</option>
                  <option>Tier 2</option>
                  <option>Tier 3</option>
                  <option>Tier 4</option>
                </select>
              </div>
            </div>
            <div class="col-md-3 mb">
              <label>Report Type</label>
              <div class="clearfix">
                <select class="form-control chosen-select">
                  <option>India</option>
                  <option>USA</option>
                  <option>UK</option>
                  <option>Japan</option>
                </select>
              </div>
            </div>
            <div class="col-md-3 mb">
              <label>Sort By</label>
              <div class="clearfix">
                <select class="form-control chosen-select">
                  <option>India</option>
                  <option>USA</option>
                  <option>UK</option>
                  <option>Japan</option>
                </select>
              </div>
            </div>
            <div class="clearfix"></div>
            <div class="clearfix">
              <div class="col-md-12 checkBox">
                <label>
                  <input type="checkbox" value="" id="cd">
                  <strong>Create for all Employees</strong></label>
              </div>
            </div>
            <div class="clearfix"></div>
            <h4  class="col-md-12 mb mt25">Apply Employee Filter</h4>
            <div class="clearfix">
            	
            <div class="col-md-5 mb">
             <div class="row">
              <label class="col-md-4 mt5">Employment Status</label>
              <div class="col-md-8">
                <select class="form-control chosen-select">
                  <option>Tier 1</option>
                  <option>Tier 2</option>
                  <option>Tier 3</option>
                  <option>Tier 4</option>
                </select>
              </div>
              </div>
            </div>
            <div class="clearfix"></div>
            
            	
            <div class="col-md-5 mb">
             <div class="row">
              <label class="col-md-4 mt5">Location</label>
              <div class="col-md-8">
                <select class="form-control chosen-select">
                  <option>Tier 1</option>
                  <option>Tier 2</option>
                  <option>Tier 3</option>
                  <option>Tier 4</option>
                </select>
              </div>
              </div>
            </div>
            <div class="clearfix"></div>
            
            
            <div class="col-md-5 mb">
             <div class="row">
              <label class="col-md-4 mt5">Department</label>
              <div class="col-md-8">
                <select class="form-control chosen-select">
                  <option>Tier 1</option>
                  <option>Tier 2</option>
                  <option>Tier 3</option>
                  <option>Tier 4</option>
                </select>
              </div>
              </div>
            </div>
            <div class="clearfix"></div>
            </div>
            
              <div class="col-md-12 clearfix mb">
                <button type="button" class="btn btn-primary btn-sm">Show Employees</button>
              </div>

            <div class="clearfix"></div>
            <div class="clearfix col-md-12 mt25">
              <h4 class="header-align">To search for an employee:</h4>
              <div class="clearfix mt5"></div>
              <small class="text-muted">Click inside available employees and start typping to search for an entry</small> </div>
      </div>
      <div class="clearfix mt">
  <select id='pre-selected-options' multiple='multiple'>
    <option value='elem_1' selected>elem 1</option>
    <option value='elem_2'>elem 2</option>
    <option value='elem_3'>elem 3</option>
    <option value='elem_4' selected>elem 4</option>
    <option value='elem_100'>elem 100</option>
  </select>
      <div class="hidden">
      <div class="col-md-5">
      	<h4 class="text-muted">Available Employees (212)</h4>
      	<div class="clearfix mt5 empfilterblock">
      		
      	</div>
      </div>
      <div class="col-md-2 text-center positionRelative">
      		<div class="filterButtons">
      		<button type="button" class="btn btn-sm btn-primary"><i class="fa fa-angle-left fa-2x"></i></button>
      		<button type="button" class="btn btn-sm btn-primary"><i class="fa fa-angle-right fa-2x"></i></button>
      		</div>
      </div>
      <div class="col-md-5">
      	<h4 class="text-muted">Selected Employees (0)</h4>
      	<div class="clearfix mt5 empfilterblock">
      		
      	</div>
      </div>
      </div>
      </div>
       <div class="form-footer clearfix">
        <div class="col-lg-12">
          <div class="pull-right">
            <input type="button" value="Reset" class="btn btn-default mr">
            <input type="button" value="Submit" class="btn btn-primary">
          </div>
        </div>
      </div>
      <!-- Content Area Ends--> 
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
				})
				$(document).ready(function(){
					$("#cd").click(function(){
						$(".clientDetails").toggleClass('hidden', 500);
					});
	
	});
  // run pre selected options
  $('#pre-selected-options').multiSelect();
  </script>

<jsp:include page="../masterFooter.jsp" />