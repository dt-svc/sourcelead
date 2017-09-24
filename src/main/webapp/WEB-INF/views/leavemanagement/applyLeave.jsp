<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script> 
<script type="text/javascript" src="https://www.amcharts.com/lib/3/amcharts.js"></script>
<script type="text/javascript" src="https://www.amcharts.com/lib/3/pie.js"></script>


<!-- Side Nav Bar --> 
    <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Apply Leave</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Leave Management &raquo; Apply Leave</div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="clearfix form-horizontal inner-content">
          <div class="col-xs-12 col-sm-12 col-md-6 col-md-offset-1">
              <div class="form-group">
                    <label class="checkBox pull-left mr10">
					<input id="checkbox-1" class="checkbox-custom checkboxCustom onbehalf" name="checkbox-1" type="checkbox" onClick="onbehalf()">
					<label for="checkbox-1" class="checkbox-custom-label"></label>
                  </label>
                  <span class="pull-left">On behalf of</span>
              </div>
               <div class="form-group" style="display: none" id="onbehalf">
              <label class="col-md-3">Select Employee</label>
              <div class="col-md-9 dropdown-select">
                <select class="form-control chosen-select">
                  <option selected>--Select Employee--</option>
                  <option>Employee1</option>
                  <option>Employee2</option>
                  <option>Employee3</option>
                  <option>Employee3</option>
                </select>
              </div>
            </div>
              
              
            <div class="form-group">
              <label class="col-md-3">From<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-9">
                <div class="row">
                  <div class="col-md-7 paddingRight">
                    <input type="text" class="form-control calendar" id="" placeholder="From Date">
                  </div>
                  <div class="col-md-5 paddingLeft">
                    <label class="checkBox mt5">
                      <label class="checkBox pull-left mr10">
					<input id="checkbox-2" class="checkbox-custom checkboxCustom" name="checkbox-2" type="checkbox">
					<label for="checkbox-2" class="checkbox-custom-label"></label>
                  </label>
                        
                      Half a Day </label>
                  </div>
                </div>
              </div>
            </div>
              <div class="form-group">
              <label class="col-md-3">To<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-9">
                <div class="row">
                  <div class="col-md-7 paddingRight">
                    <input type="text" class="form-control calendar" id="" placeholder="To Date">
                  </div>
                  <div class="col-md-5 paddingLeft">
                    <label class="checkBox mt5">
                      <label class="checkBox pull-left mr10">
					<input id="checkbox-3" class="checkbox-custom checkboxCustom" name="checkbox-2" type="checkbox">
					<label for="checkbox-3" class="checkbox-custom-label"></label>
                  </label>
                      Half a Day </label>
                  </div>
                </div>
              </div>
            </div>
              <div class="form-group">
              <label class="col-md-3">Leave Type</label>
              <div class="col-md-9 dropdown-select">
                <select class="form-control">
                  <option selected>--Select Leave Type--</option>
                  <option>Sick Leave</option>
                  <option>Casual Leave</option>
                  <option>Special Leave</option>
                  <option>Maternity</option>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Reason<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-9">
                <textarea class="form-control" rows="2" placeholder="Reason"></textarea>
              </div>
            </div>
              
              <div class="form-group">
              <label class="col-md-3">Attachment</label>
              <div class="col-md-9">
                  <div style="float:left; width:100%; border:1px solid #ddd; margin:0 15px 15px 0; padding:5px;overflow: hidden; background-color:#fff; color:#333; ">
                      <input id="file0" type="file" class="pull-left valid" onchange="uploadButton(0);">
                      <a id="Button0value=">
                      <i class="fa fa-times btn btn-danger btn-sm btn-sm2 pull-right"></i>
                      </a>
                  </div>
              </div>
            </div>
            <p class="form-control-static clearfix"><strong>Approval</strong></p>
            <div class="form-group">
              <label class="col-md-3">Approved By<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-9 dropdown-select">
                <div class="form-icon">
                  <select class="form-control">
                    <option selected>--Select Approver--</option>
                    <option>Approver-1</option>
                    <option>Approver-2</option>
                    <option>Approver-3</option>
                  </select>
                  <i class="fa fa-plus formic1-new btn btn-default" title="Add Approver" onClick="approvedlevel();"></i> </div>
                  <div class="approvedlevel" style="display: none">
                  <select class="form-control mt10">
                  <option selected>--Select Approver--</option>
                  <option>Approver-1</option>
                  <option>Approver-2</option>
                  <option>Approver-3</option>
                </select>
                      </div>
              </div>
            </div>
              
              
              
              
          </div>
          <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
            <div class="well-sm well well-custom clearfix amchartAnchor-Hide">
                 <h5>Leave Stats</h5>
                <div id="leavestatsu" style="width: 100%; height: 150px;"></div>
                <div class="row">
                <ul class="list-unstyled leavelist col-md-10 mt25 col-md-offset-1 list-group">
                    <li>
                        <label>Balance Leaves</label>
                        <span class="label label-info">100</span>
                    </li>
                    <li>
                        <label>Sick Leaves</label>
                    <span class="label label-primary">100</span>
                    </li>
                    <li><label>Casual Leaves</label>
                    <span class="label label-warning">100</span>
                    </li>
                    <li><label>Leave Request</label>
                    <span class="label label-success">100</span>
                    </li>
                    <li><label>Cancel Request</label>
                    <span class="label label-danger">100</span>
                    </li>
                </ul>
                    </div>
                
            </div>
          </div>
      </div>
      <div class="form-footer clearfix mt">
        <div class="col-lg-12">
          <div class="pull-right">
            <input type="button" value="Draft" class="btn btn-warning">
            <input type="button" value="Save" class="btn btn-primary ml mr">
            <input type="button" value="Cancel" class="btn btn-default">
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
 		</script> 
 		
 		
 		 <script type="text/javascript">
        //ApplyLeves piechart
			AmCharts.makeChart("leavestatsu",
{
					"type": "pie",
					"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b></span>",
					"innerRadius": "40%",
					"minRadius": 0,
					"pieX": "50%",
					"pieY": "50%",
					"pullOutRadius": "0%",
					"startRadius": "0%",
					"colors": [
						"#81c784",
						"#ffd54f",
						"#26c6da"
					],
					"labelsEnabled": false,
					"marginBottom": 0,
					"marginLeft": 2,
					"marginTop": 0,
					"maxLabelWidth": 0,
					"titleField": "category",
					"valueField": "column-1",
					"allLabels": [],
					"balloon": {},
					"titles": [],
					"dataProvider": [
						{
							"category": "Approved",
							"column-1": "8"
						},
						{
							"category": "Pending",
							"column-1": "2"
						},
						{
							"category": "Balance",
							"column-1": 2
						}
					]
				}				
			);
		</script>
    <script>
    function approvedlevel(){
        alet("hi");
        $(".approvedlevel").toggle();
    }
    </script>

<jsp:include page="../masterFooter.jsp" />
