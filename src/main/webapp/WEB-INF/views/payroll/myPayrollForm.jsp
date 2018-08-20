<!-- Content Area -->
<!-- <section class="content-wraper contentWraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>My Forms</h2>
  </div>
  <div class="pull-right brud-crum">Home &raquo; My Forms</div>
</section> 
 
   <div class="tabsMenu">
   	<ul class="list-unstyled">
	   <li><a href="myPays-us.html" class="active">Pays</a></li>
	   <li class="current"><a href="myForm-us.html">Forms</a></li>
	   <li><a href="myPayrolls-us.html" class="active">Loans &amp; Advances</a></li>
	 </ul>
   </div> -->
 
    <div class="clearfix"></div>
    <div class="timesheet_out" id="myPayrollFormDiv">
      <div class="tablelist-wrap">
        <div class="table_head minHeight40 mt">
              <ul class="pull-right list-inline list-unstyled">
                <li>
                   <button type="button" class="btn btn-primary mr" onClick="uploaddocument();"><i class="fa fa-upload mr"></i>Upload</button>
                </li>
                <li class="positionRelative">
                    <div class="remove_search"><input placeholder="Search" type="text"></div>
				</li>
              </ul>
        </div>
          <div class="styleTables clearfix mt">
            <table>
              <thead>
                <tr>
                  <th>Form Name</th>
                  <th>Filling Date</th>
                  <th>Year</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>
                  	<div class="tblminwidth-150"><span class="mr">01/02/2018</span><span>02/02/2019</span></div>
                  </td>
                  <td>
                  	<div class="tblminwidth-150 clearfix">IT Declaration</div>
                  </td>
                  <td>
					  <span class="tblminwidth-100">
							25/02/2018                 	
					  </span>
                  </td>
                  <td><div class="tblminwidth-150 clearfix" title="Download"><i class="fa fa-download cursorPointer"></i></div></td>
                </tr>
                 <tr>
                  <td>
                  	<div class="tblminwidth-150"><span class="mr">01/02/2018</span><span>02/02/2019</span></div>
                  </td>
                  <td>
                  	<div class="tblminwidth-150 clearfix">Form-16</div>
                  </td>
                  <td>
					  <span class="tblminwidth-100">
							31/05/2018                 	
					  </span>
                  </td>
                  <td>
                  	<div class="tblminwidth-150 clearfix">Processed</div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        <div class="clearfix"></div>
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
    </div>
    
<script type="text/javascript">
	function uploaddocument() {
		alert("hu");
		$(".uploaddocument, .sideMask").fadeIn();
	}
	function clsoerequestPop() {
		$(".uploaddocument, .sideMask").fadeOut();
	}
		$("#uploadBtn").change(function () {
			//var m = $(this).val();
		$('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''))
	})
</script>

<!--   </section> -->