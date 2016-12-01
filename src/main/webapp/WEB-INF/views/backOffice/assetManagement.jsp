<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page trimDirectiveWhitespaces="true" %>

<script type="text/javascript" src="jquery-1.2.6.min.js"></script>
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="resources/js/formvalidator.js"></script>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>

<link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/bootstrap-select.min.js"></script> 


<script>
$(document).ready(function(){
	getAssetList();
});
</script> 

<section class="content-wraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Asset Management</h2>
  </div>
  <div class="pull-right brud-crum">Home &raquo; Back Office &raquo; Assetmanagement</div>
</section>
<div class="timesheet_out">
  <div class="vendor_wrap">
    <div class="timesheet_head">
      <ul>
        
        <li><i class="fa fa-filter"></i><span>Category</span><i class="glyphicon glyphicon-play" id="appro-pop"></i>|
          <div class="approved_pop">
            <ul>
              <li>
                <input type="checkbox">
                &nbsp;&nbsp;Desktop</li>
              <li>
                <input type="checkbox">
                &nbsp;&nbsp;Laptop</li>
              <li>
                <input type="checkbox">
                &nbsp;&nbsp;Other</li>
            </ul>
          </div>
        </li>
        <li><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop"></i>|
          <div class="export_pop">
            <ul>
              <li>PDF<i class="fa fa-file-pdf-o"></i></li>
              <li>Excel<i class="fa fa-file-excel-o"></i></li>
              <li>Word<i class="fa fa-file-word-o"></i></li>
            </ul>
          </div>
        </li>
        <li><i class="glyphicon glyphicon-export"></i><span>Import</span><i class="glyphicon glyphicon-play" id="export-pop"></i>|
          <div class="export_pop">
            <ul>
              <li>PDF<i class="fa fa-file-pdf-o"></i></li>
              <li>Excel<i class="fa fa-file-excel-o"></i></li>
              <li>Word<i class="fa fa-file-word-o"></i></li>
            </ul>
          </div>
        </li>
        <li>
          <a href="javascript:void(0)" class="addcon-pop"><i class="fa fa-plus"></i>Add Asset</a> 
          <!-- Add Asset Popup -->
          <div class="collapse-div-adduser pop-div col-md-12 assetaddpop">
           <i class="fa fa-close close-div"></i>
			  <div class="clearfix">
            <div class="clearfix assetaddpop-ul col-md-6">
				<select class="chosen-select" id="assetmn" name="assetmn" onChange="myFunction(this)">
					<option selected disabled>-Selct Category-</option>
					<option value="laptop">Laptop</option>
					<option value="desktop">Desktop</option>
					<option value="others">Others..</option>
				</select>	
            </div>
            <div class="clearfix assetaddpop-ul col-md-6">
				<select class="chosen-select" id="assetmn" name="assetmn">
					<option selected disabled>-Selct Working Status-</option>
					<option value="laptop">Working</option>
					<option value="desktop">Desktop</option>
					<option value="others">Others..</option>
				</select>	
            </div>
            </div>
			  <div class="clearfix mb10"></div>
            <!-- Laptop popup div -->
            <div class="clearfix" id="laptop">
              <div class="form-horizontal clearfix">
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Serial Number *">
                  </div>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Make">
                  </div>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Model *">
                  </div>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Memory *">
                  </div>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="CPU *">
                  </div>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Hard Disk *">
                    <textarea class="form-control hidden" rows="2" placeholder="Description"></textarea>
                  </div>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <textarea class="form-control" rows="2" placeholder="Description"></textarea>
                  </div>
                </div>
                <!-- Shift A Fields -->
                <div class="clearfix">
                  <h4 class="col-md-12">Shift A</h4>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <select class="form-control chosen-select">
                      <option selected>-Select Employee-</option>
                      <option>Employee1</option>
                      <option>Employee2</option>
                      <option>Employee3</option>
                    </select>
                  </div>
                  <div class="col-md-6">
                    <div class="input-group">
                      <label class="input-group-btn"> <b class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                        <input type="file" style="display: none;" multiple id="uploadBtn-lapA" class="upload">
                        </b> </label>
                      <input type="text" class="form-control" readonly id="uploadFile-lapA" placeholder="Files are not selected...">
                    </div>
                  </div>
                </div>
                <!-- Shift A Fields --> 
                <!-- Shift B Fields -->
                <div class="clearfix">
                  <h4 class="col-md-12">Shift B</h4>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <select class="form-control chosen-select">
                      <option selected>-Select Employee-</option>
                      <option>Employee1</option>
                      <option>Employee2</option>
                      <option>Employee3</option>
                    </select>
                  </div>
                  <div class="col-md-6">
                    <div class="input-group">
                      <label class="input-group-btn"> <b class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                        <input type="file" style="display: none;" multiple id="uploadBtn-lapB" class="upload">
                        </b> </label>
                      <input type="text" class="form-control" readonly id="uploadFile-lapB" placeholder="Files are not selected...">
                    </div>
                  </div>
                </div>
                <!-- Shift A Fields -->
                <div class="clearfix mr">
                  <div class="pull-right">
                    <button type="submit" class="btn btn-sm btn-primary">Add</button>
                    <button type="submit" class="btn btn-sm btn-default ml10">Cancel</button>
                  </div>
                </div>
              </div>
            </div>
            <!-- Laptop popup div --> 
            <!-- Desktop popup div -->
            <div class="clearfix" id="desktop">
              <div class="form-horizontal clearfix">
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Serial Number *">
                  </div>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Make *">
                  </div>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Model *">
                  </div>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Memory *">
                  </div>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-4">
                    <input type="text" class="form-control input-sm" value="" placeholder="CPU *">
                  </div>
                  <div class="col-md-4">
                    <input type="text" class="form-control input-sm" value="" placeholder="Hard Disk *">
                  </div>
                  <div class="col-md-4">
                    <input type="text" class="form-control input-sm" value="" placeholder="Monitor *">
                  </div>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <select class="form-control chosen-select">
                      <option selected>-Select Location-</option>
                      <option>India</option>
                      <option>USA</option>
                    </select>
                  </div>
                  <div class="col-md-6">
                    <textarea class="form-control" rows="2" placeholder="Description"></textarea>
                  </div>
                </div>
                <!-- Shift A Fields -->
                <div class="clearfix">
                  <h4 class="col-md-12">Shift A</h4>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <select class="form-control chosen-select">
                      <option selected>-Select Employee-</option>
                      <option>Employee1</option>
                      <option>Employee2</option>
                      <option>Employee3</option>
                    </select>
                  </div>
                  <div class="col-md-6">
                    <div class="input-group">
                      <label class="input-group-btn"> <b class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                        <input type="file" style="display: none;" multiple id="uploadBtn-dskA" class="upload">
                        </b> </label>
                      <input type="text" class="form-control" readonly id="uploadFile-dskA" placeholder="Files are not selected...">
                    </div>
                  </div>
                </div>
                <!-- Shift A Fields --> 
                <!-- Shift B Fields -->
                <div class="clearfix">
                  <h4 class="col-md-12">Shift B</h4>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <select class="form-control chosen-select">
                      <option selected>-Select Employee-</option>
                      <option>Employee1</option>
                      <option>Employee2</option>
                      <option>Employee3</option>
                    </select>
                  </div>
                  <div class="col-md-6">
                    <div class="input-group">
                      <label class="input-group-btn"> <b class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                        <input type="file" style="display: none;" multiple id="uploadBtn-dskB" class="upload">
                        </b> </label>
                      <input type="text" class="form-control" readonly id="uploadFile-dskB" placeholder="Files are not selected...">
                    </div>
                  </div>
                </div>
                <!-- Shift A Fields -->
                <div class="clearfix mr">
                  <div class="pull-right">
                    <button type="submit" class="btn btn-sm btn-primary">Add</button>
                    <button type="submit" class="btn btn-sm btn-default ml10">Cancel</button>
                  </div>
                </div>
              </div>
            </div>
            <!-- Desktop popup div --> 
            <!-- Others popup div -->
            <div class="clearfix" id="others">
              <div class="form-horizontal clearfix">
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Serial Number *">
                  </div>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Name *">
                  </div>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Make *">
                  </div>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Model *">
                  </div>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Memory *">
                  </div>
                  <div class="col-md-6">
                    <select class="form-control chosen-select">
                      <option selected>-Select Location-</option>
                      <option>India</option>
                      <option>USA</option>
                    </select>
                  </div>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-12">
                    <textarea class="form-control" rows="2" placeholder="Description"></textarea>
                  </div>
                </div>
                <div class="clearfix mr">
                  <div class="pull-right">
                    <button type="submit" class="btn btn-sm btn-primary">Add</button>
                    <button type="submit" class="btn btn-sm btn-default ml10">Cancel</button>
                  </div>
                </div>
              </div>
            </div>
            <!-- Others popup div --> 
          </div>
          <!-- Add Asset Popup --> 
        </li>
        <li>
        <a href="<c:url value='/assetCategory'/>" class=""><i class="fa fa-plus"></i>Categories</a>
        </li>
        <div class="clearfix"></div>
      </ul>
      <div class="clearfix"></div>
      <div class="remove_search">
        <input type="text" placeholder="search" />
      </div>
    </div>
    <!-- list Table -->
    <div class="require_wrap_table table-wrap">
    <table id="assetManagementList" class="display" cellspacing="0" width="100%"> </table>
      
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
    <!-- list Table -->
  </div>
  <div class="side_mask">
    <div class="proj_close"></div>
  </div>
  <div class="clients_side_wrap">
    <div class="clients_side_wrap_inn">
      <h1>Asset Management Details</h1>
      <div class="project_side_wrap_inn_tabs">
        <div class="project_tabs">
          <div id="tab-1" class="project_tabs_inn" style="display: block;">
            <div class="require_wrap_details">
              <h1>Asset Management details: <a id="edit-user" href="javascript:void(0)">Edit<i class="glyphicon glyphicon-pencil"></i></a></h1>
              <div class="require_one">
                <div class="require_wrap_details_cta"> <b>S. No.:</b> <span>00012..</span>
                  <div class="clearfix"></div>
                </div>
                <div class="require_wrap_details_cta"> <b>Make:</b> <span>Make text here..</span>
                  <div class="clearfix"></div>
                </div>
                <div class="require_wrap_details_cta"> <b>Model:</b> <span>Model text here..</span>
                  <div class="clearfix"></div>
                </div>
                <div class="require_wrap_details_cta"> <b>Memory:</b> <span>Memory text here...</span>
                  <div class="clearfix"></div>
                </div>
                <div class="require_wrap_details_cta"> <b>CPU:</b> <span>CPU</span>
                  <div class="clearfix"></div>
                </div>
                <div class="require_wrap_details_cta"> <b>Hard Disk:</b> <span>HardDisk here..</span>
                  <div class="clearfix"></div>
                </div>
                <div class="require_wrap_details_cta"> <b>Location:</b> <span>Hyderabad, Telangana, India</span>
                  <div class="clearfix"></div>
                </div>
                <div class="require_wrap_details_cta"> <b>Description:</b> <span>Description here..</span>
                  <div class="clearfix"></div>
                </div>
                <div class="require_wrap_details_cta"> <b>Shfit A:</b> <span>Sudhakar Reddy</span>
                  <div class="clearfix"></div>
                </div>
                <div class="require_wrap_details_cta"> <b>Attched File:</b> <span>Attched File</span>
                  <div class="clearfix"></div>
                </div>
                                <div class="require_wrap_details_cta"> <b>Shfit B:</b> <span>Sudhakar Reddy</span>
                  <div class="clearfix"></div>
                </div>
                <div class="require_wrap_details_cta"> <b>Attched File:</b> <span>Attched File</span>
                  <div class="clearfix"></div>
                </div>
                <div class="clearfix"></div>
                <div class="user_summary_edit client_summary_edit" style="display: none;">
                  <div class="form-horizontal req-dtls myForm row">
                  
                            <!-- Add Asset Popup -->
          <div class="col-md-12 edit-category">
            <div class="clearfix assetaddpop-ul col-md-6 category">
				<div class="row">
				<label class="col-md-4">Select Category</label>
				<div class="col-md-8">
					<select class="chosen-select" onChange="myasetEdit(this)">
					<option value="laptop-edit" selected>Laptop</option>
					<option value="desktop-edit">Desktop</option>
					<option value="others-edit">Others..</option>
				</select>
          </div>
           </div>
            </div>
			  <div class="clearfix mb10"></div>
            <!-- Laption popup div -->
            <div class="clearfix" id="laptop-edit">
              <div class="form-horizontal clearfix">
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Serial Number *">
                  </div>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Make">
                  </div>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Model *">
                  </div>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Memory *">
                  </div>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="CPU *">
                  </div>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Hard Disk *">
                    <textarea class="form-control hidden" rows="2" placeholder="Description"></textarea>
                  </div>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <select class="form-control chosen-select">
                      <option selected>-Select Location-</option>
                      <option>India</option>
                      <option>USA</option>
                    </select>
                  </div>
                  <div class="col-md-6">
                    <textarea class="form-control" rows="2" placeholder="Description"></textarea>
                  </div>
                </div>
                <!-- Shift A Fields -->
                <div class="clearfix">
                  <h4 class="col-md-12">Shift A</h4>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <select class="form-control chosen-select">
                      <option selected>-Select Employee-</option>
                      <option>Employee1</option>
                      <option>Employee2</option>
                      <option>Employee3</option>
                    </select>
                  </div>
                  <div class="col-md-6">
                    <div class="input-group">
                      <label class="input-group-btn"> <b class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                        <input type="file" style="display: none;" multiple id="uploadBtn-lapA" class="upload">
                        </b> </label>
                      <input type="text" class="form-control" readonly id="uploadFile-lapA" placeholder="Files are not selected...">
                    </div>
                  </div>
                </div>
                <!-- Shift A Fields --> 
                <!-- Shift B Fields -->
                <div class="clearfix">
                  <h4 class="col-md-12">Shift B</h4>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <select class="form-control chosen-select">
                      <option selected>-Select Employee-</option>
                      <option>Employee1</option>
                      <option>Employee2</option>
                      <option>Employee3</option>
                    </select>
                  </div>
                  <div class="col-md-6">
                    <div class="input-group">
                      <label class="input-group-btn"> <b class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                        <input type="file" style="display: none;" multiple id="uploadBtn-lapB" class="upload">
                        </b> </label>
                      <input type="text" class="form-control" readonly id="uploadFile-lapB" placeholder="Files are not selected...">
                    </div>
                  </div>
                </div>
                <!-- Shift A Fields -->
                <div class="clearfix mr">
                  <div class="pull-right">
                    <button type="submit" class="btn btn-sm btn-primary">Add</button>
                    <button type="submit" class="btn btn-sm btn-default ml10">Cancel</button>
                  </div>
                </div>
              </div>
            </div>
            <!-- Laption popup div --> 
            <!-- Desktop popup div -->
            <div class="clearfix" id="desktop-edit">
              <div class="form-horizontal clearfix">
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Serial Number *">
                  </div>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Make *">
                  </div>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Model *">
                  </div>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Memory *">
                  </div>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-4">
                    <input type="text" class="form-control input-sm" value="" placeholder="CPU *">
                  </div>
                  <div class="col-md-4">
                    <input type="text" class="form-control input-sm" value="" placeholder="Hard Disk *">
                  </div>
                  <div class="col-md-4">
                    <input type="text" class="form-control input-sm" value="" placeholder="Monitor *">
                  </div>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <select class="form-control chosen-select">
                      <option selected>-Select Location-</option>
                      <option>India</option>
                      <option>USA</option>
                    </select>
                  </div>
                  <div class="col-md-6">
                    <textarea class="form-control" rows="2" placeholder="Description"></textarea>
                  </div>
                </div>
                <!-- Shift A Fields -->
                <div class="clearfix">
                  <h4 class="col-md-12">Shift A</h4>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <select class="form-control chosen-select">
                      <option selected>-Select Employee-</option>
                      <option>Employee1</option>
                      <option>Employee2</option>
                      <option>Employee3</option>
                    </select>
                  </div>
                  <div class="col-md-6">
                    <div class="input-group">
                      <label class="input-group-btn"> <b class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                        <input type="file" style="display: none;" multiple id="uploadBtn-dskA" class="upload">
                        </b> </label>
                      <input type="text" class="form-control" readonly id="uploadFile-dskA" placeholder="Files are not selected...">
                    </div>
                  </div>
                </div>
                <!-- Shift A Fields --> 
                <!-- Shift B Fields -->
                <div class="clearfix">
                  <h4 class="col-md-12">Shift B</h4>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <select class="form-control chosen-select">
                      <option selected>-Select Employee-</option>
                      <option>Employee1</option>
                      <option>Employee2</option>
                      <option>Employee3</option>
                    </select>
                  </div>
                  <div class="col-md-6">
                    <div class="input-group">
                      <label class="input-group-btn"> <b class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                        <input type="file" style="display: none;" multiple id="uploadBtn-dskB" class="upload">
                        </b> </label>
                      <input type="text" class="form-control" readonly id="uploadFile-dskB" placeholder="Files are not selected...">
                    </div>
                  </div>
                </div>
                <!-- Shift A Fields -->
                <div class="clearfix mr">
                  <div class="pull-right">
                    <button type="submit" class="btn btn-sm btn-primary">Add</button>
                    <button type="submit" class="btn btn-sm btn-default ml10">Cancel</button>
                  </div>
                </div>
              </div>
            </div>
            <!-- Desktop popup div --> 
            <!-- Others popup div -->
            <div class="clearfix" id="others-edit">
              <div class="form-horizontal clearfix">
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Serial Number *">
                  </div>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Name *">
                  </div>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Make *">
                  </div>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Model *">
                  </div>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" value="" placeholder="Memory *">
                  </div>
                  <div class="col-md-6">
                    <select class="form-control chosen-select">
                      <option selected>-Select Location-</option>
                      <option>India</option>
                      <option>USA</option>
                    </select>
                  </div>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-12">
                    <textarea class="form-control" rows="2" placeholder="Description"></textarea>
                  </div>
                </div>
                <div class="clearfix mr">
                  <div class="pull-right">
                    <button type="submit" class="btn btn-sm btn-primary">Add</button>
                    <button type="submit" class="btn btn-sm btn-default ml10">Cancel</button>
                  </div>
                </div>
              </div>
            </div>
            <!-- Others popup div --> 
          </div>
					  
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Project --> 
  </div>
  </section>
  
  
  
  <script>
	function getAssetList(){
		
		var sourceleadUrl ='${sourceleadUrl}';
		
		  $.ajax({
			   	type: "POST",
			    url: sourceleadUrl+"/getAssetList?${_csrf.parameterName}=${_csrf.token}", 	   
	   		    success: function(data) {
	   		    	
	   		    	
	   		    	var json = JSON.parse(data);
	   	        	var result = "";
	   	        	result = result +'<thead>';
	   	        	result = result +'<tr>';
	   	        	result = result +'	<th>S.No</th>';
	   	        	result = result +'  <th>Category</th>';
	   	        	result = result +'  <th>Model</th>';
	   	        	result = result +'  <th>Shift-A</th>';
	   	        	result = result +'  <th>Shift-B</th>';
	   	        	result = result +' <th>Status</th>';
	   	        	result = result +' <th>Action</th>';
	   	        	result = result +'</tr>';
	   	        	result = result +'	</thead>';
	   	        	result = result +'<tbody>';
	   	        	
		   	     	for (var i=0; i<json.length; i++)
	   				{  
		   	     	    var report = json[i];
		   	     	    
		   	     	    result = result+'<tr>';
			   	        result = result+'<td><div class="tbldata-dotdot-100" title="'+report.slno+'">'+report.slno+'</div></td>';
				   	    result = result+'<td><a href="javascript:void(0)" class="tbldata-dotdot">';
					   	result = result+'<div class="proj_show"> <span class="tbldata-dotdot-sm">'+report.assetCategory.categoryName+'</span> <i class="fa fa-binoculars"></i> </div>';
					   	result = result+'</a></td>';
					   	result = result+'<td><div class="tbldata-dotdot-vsm">'+report.model+'</div></td>';
					   	result = result+'<td><div class="tbldata-dotdot-vsm" title="'+report.shiftAEmployee.user.firstName+' '+report.shiftAEmployee.user.lastName+'">'+report.shiftAEmployee.user.firstName+' '+report.shiftAEmployee.user.lastName+'</div></td>';
					   	result = result+'<td><div class="tbldata-dotdot-vsm" title="'+report.shiftBEmployee.user.firstName+' '+report.shiftBEmployee.user.lastName+'">'+report.shiftBEmployee.user.firstName+' '+report.shiftBEmployee.user.lastName+'</div></td>';
					   	result = result+'<td><div class="tbldata-dotdot-100">'+report.workingStatus+'</div></td>';
					   	result = result+'<td><div class="tblminwidth-50"><i class="fa fa-trash-o text-danger"></i></div></td>';
			   	        result = result +'</tr>';
	   				}
		   	     	
	   	        	
	   	        	result = result +'</tbody>';
	   	    	    $("#assetManagementList").empty();  
	   	    	    $("#assetManagementList").append(result)
	   	    	   
	   	    	    
	   		    	
	   		    }
		  });
	}
</script>
  
  
  
   <script> 
	   $('#laptop, #desktop, #others').addClass('asstmake');
		  $('#laptop').removeClass('asstmake');
	 function myFunction(local){
		 if(local.value == 'desktop'){
			$('#desktop').removeClass('asstmake');
			$('#laptop, #others').addClass('asstmake');
			}
	     if(local.value == 'others'){  
			$('#others').removeClass('asstmake');
			$('#laptop, #desktop').addClass('asstmake');
		 }
		 if(local.value === 'laptop'){
			$('#laptop').removeClass('asstmake');
			$('#desktop, #others').addClass('asstmake');
		 }
	 };    
   </script>
  <script>
	  $('#laptop-edit, #desktop-edit, #others-edit').addClass('asstmake');
		  $('#laptop-edit').removeClass('asstmake');
	  function myasetEdit(local){
         if(local.value == 'desktop-edit'){
			$('#desktop-edit').removeClass('asstmake');
			$('#laptop-edit, #others-edit').addClass('asstmake');
			}
	     if(local.value == 'others-edit'){  
			$('#others-edit').removeClass('asstmake');
			$('#laptop-edit, #desktop-edit').addClass('asstmake');
		 }
		 if(local.value === 'laptop-edit'){
			$('#laptop-edit').removeClass('asstmake');
			$('#desktop-edit, #others-edit').addClass('asstmake');
		 }
	 };  
    </script> 
  <script>
// Edit pop
$(document).ready(function() {	
	$("#edit-user").click(function(){
		$(".user_summary_edit").slideToggle(100);
	});
    $("html, #canBTN, .us-btn_a").click(function() {
        $(".user_summary_edit").slideUp(100);
    });
    $("#edit-user, .user_summary_edit").click(function(e) {
        e.stopPropagation();
    });		
});
</script> 
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

// Approved Popup
$(document).ready(function() {
    $("#appro-pop").click(function(){
		$(".approved_pop").slideToggle(200);
	});
    $("html").click(function() {
        $(".approved_pop").slideUp(200);
    });
    $(".approved_pop, #appro-pop").click(function(e) {
        e.stopPropagation();
    });	
});

// Export Popup
$(document).ready(function() {
    $("#export-pop").click(function(){
		$(".export_pop").slideToggle(200);
	});
    $("html").click(function() {
        $(".export_pop").slideUp(200);
    });
    $(".export_pop, #export-pop").click(function(e) {
        e.stopPropagation();
    });	
});	
	
// project side bar
$(document).ready(function() {
    $(".proj_show").click(function(){
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
	});
    $(".proj_close, .side_mask").click(function(){
		$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});
});

// project side bar tabs
$(document).ready(function() {
	$("#tab-1").show()
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});

// project side bar tabs
$(document).ready(function() {
	$("#tabb-1").show()
    $(".new-tabs a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".new-tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});

// script for scroll Popups:: Add or Edit details
$(document).ready(function(event) {	
$(".addUser-org").click(function(){
	 $(".edit-details").toggleClass("edit-details-active");
	 $(".collapse-div").removeClass("edit-details-active");
 });
 $(".close-div").click(function(){
	 $(this).closest(".edit-details").toggleClass("edit-details-active");
	 });
 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
	 e.stopPropagation();
 });
 $('html').click(function(){
	$(".edit-details").removeClass("edit-details-active");
   });	
   $("#endDate").click(function(){
	   $(".endDate").toggle(200);
   });
   event.preventDefault();
});

$(".addSkills").click(function(){
	$(".add-skills").toggleClass('add-skills-active');
	});
$(".close-div").click(function(){
	$(".add-skills").removeClass('add-skills-active');
	});
$(".add-skills").click(function(e){
	 e.stopPropagation();
 });


// Bar chat pop
$(document).ready(function() {
    $(".graf_icon").click(function(){
		$(".pipeline_graf").slideToggle(200);
	});
$( "ul#canName-ch li").click(function(){
   var m = $(this).text();
  $("#canName").text(m);
});
});
</script> 
  <script>
$( "#pri-ch li").click(function(){
   var m = $(this).text();
    var mid = $(this).attr('id');
    $("#pri-val").text(m);
     if('h' === $(this).attr("id")){
          $("#pri-val").removeClass("text-warning text-primary").addClass("text-danger");
     }else if('m' === $(this).attr("id")){
        $("#pri-val").removeClass("text-danger text-warning").addClass("text-primary");
     }else{
      $("#pri-val").removeClass("text-danger text-primary").addClass("text-warning");
     }   
});
</script> 
  <script>
$(document).ready(function(){
	// Levels adding script
$(".add-offer").click(function(){
	 $(".addOffer-Req").toggleClass("show-div");
	 $(".addInterview-Re").removeClass("show-div");
	 $(".addApplicant-Re").removeClass("show-div");
 });
$(".add-interview").click(function(){
	 $(".addInterview-Re").toggleClass("show-div");
	 $(".addOffer-Req").removeClass("show-div");
	 $(".addApplicant-Re").removeClass("show-div");
 });
$(".add-applicant").click(function(){
	 $(".addApplicant-Re").toggleClass("show-div");
	 $(".addOffer-Req").removeClass("show-div");
	 $(".addInterview-Re").removeClass("show-div");
 });
$(".reminder").click(function(){
	 $(".reminder-div").toggleClass("reminder-div-active");
 });

 $(".close-div").click(function(){
	 $(this).parent().toggleClass("show-div");
	 });
 $(".div-close").click(function(){
	 $(this).parent().toggleClass("showDiv");
	 });

 $(".reminder").click(function(e){
	 e.stopPropagation();
 });
 $('html').click(function(){
    $("").removeClass("show-div");
   });	
   $("#endDate").click(function(){
	   $(".endDate").toggle(200);
   });
   });
   
// new SlideToggle
 $("#id1").click(function(){
	  $(".hide-row").slideToggle(200);
	  $(".profile_details h4 i").toggleClass('icon-add');
	 });	 
   
   
   
</script> 
  <script type="text/javascript">
            $(function () {
                $('#datetimepicker5').datetimepicker({
					locale: 'ru'
                });
            });
        </script>
  <div class="theme-color"> <span class="icon-theme" title="click here"><i class="fa fa-gears"></i></span>
    <ul class="list-unstyled">
      <li><a href="#" class="" id="btn1"><span class="dark-blue"></span><span class="light-grey"></span></a></li>
      <li><a href="#" class="" id="btn2"><span class="flat-green"></span><span class="dark-purle"></span></a></li>
      <li><a href="#" class="" id="btn3"><span class="hdr-blue"></span><span class="sidenav-blue"></span></a></li>
    </ul>
  </div>
</div>
<script>
$(".addcon-pop").click(function(){
	 $(".collapse-div-adduser").toggleClass("show-div");
	 $(".collapse-div").removeClass("show-div");
 });
 $(".close-div").click(function(){
	 $(this).parent().removeClass("show-div");
	 });
 $(".addcon-pop, .adddoc-pop, .collapse-div-adduser, .collapse-div").click(function(e){
	 e.stopPropagation();
 });
 $('html').click(function(){
    $(".collapse-div-adduser").removeClass("show-div");
	$(".collapse-div").removeClass("show-div");
   });
    //upload file script -laptop-shiftA
     document.getElementById("uploadBtn-lapA").onchange = function () {
     $('#uploadFile-lapA').val($(this).val().replace(/C:\\fakepath\\/i, ''))
     };
	
	//upload file script -laptop-shiftB
     document.getElementById("uploadBtn-lapB").onchange = function () {
     $('#uploadFile-lapB').val($(this).val().replace(/C:\\fakepath\\/i, ''))
     };
    //upload file script -Desktop-shiftA
     document.getElementById("uploadBtn-dskA").onchange = function () {
     $('#uploadFile-dskA').val($(this).val().replace(/C:\\fakepath\\/i, ''))
     };
	
	//upload file script -Desktop-shiftB
     document.getElementById("uploadBtn-dskB").onchange = function () {
     $('#uploadFile-dskB').val($(this).val().replace(/C:\\fakepath\\/i, ''))
     };

	
	</script>
<div class="clearfix mb10 hidden">
  <div class="col-md-7">
    <input type="text" class="form-control input-sm" value="" placeholder="Amount *">
  </div>
  <div class="col-md-5 select-field">
    <select class="form-control input-sm">
      <option>Debit</option>
      <option>Credit</option>
    </select>
  </div>
</div>
 
<jsp:include page="../masterFooter.jsp" />