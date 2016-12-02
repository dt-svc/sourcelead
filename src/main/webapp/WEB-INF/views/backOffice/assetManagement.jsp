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
	loadCategory();
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
        <li><i class="fa fa-filter"></i><span>Category</span><i class="glyphicon glyphicon-play" id="category"></i>|
          <div class="approved_pop">
            <ul>
                   <c:forEach var="report" items="${assetCategoriesList}">  
		               <li><input  id="category${report.id}" name="categoryId" value ="${report.id}"  onclick="getAssetList();"  type="checkbox"><label>${report.categoryName}</label></li>
				   </c:forEach>
            </ul>
          </div>
        </li>
         <li><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop"></i>|
          <div class="export_pop">
            <ul>
              <li><i class="fa fa-file-pdf-o"></i>&nbsp;PDF</li>
              <li><i class="fa fa-file-excel-o"></i>&nbsp;Excel</li>
              <li><i class="fa fa-file-word-o"></i>&nbsp;Word</li>
            </ul>
          </div>
        </li>
       <!--  <li><i class="glyphicon glyphicon-save"></i><span>Import</span>|</li> -->
            
              
        <li>
          <a href="javascript:void(0)" class="addcon-pop"><i class="fa fa-plus"></i>Add Asset</a> 
          <!-- Add Asset Popup -->
          <div class="collapse-div-adduser pop-div col-md-12 assetaddpop">
          <div id="loading"></div>
          <form:form id="addAsset_form"  name="addAsset_form" enctype='multipart/form-data'> 
           <i class="fa fa-close close-div" onclick="flushAsset();"></i>
			  <div class="clearfix">
            <div class="clearfix assetaddpop-ul col-md-6">
				<select class="chosen-select" id="category" name="category" onChange="assetCategory(this)">
					<c:forEach var="option" items="${categoryList}">
							<option value="${option.categoryName},${option.id}" ${option.categoryName  == 'Laptop' ? 'selected' : ''}>${option.categoryName}</option>
					</c:forEach>
				</select>	
            </div>
            <div class="clearfix assetaddpop-ul col-md-6">
				<select class="chosen-select" id="workingStatus" name="workingStatus" data-validation="required" field-name="Working Status">
					<option selected disabled>-Selct Working Status-</option>
					<option value="Working">Working</option>
					<option value="Out Of Service">Out Of Service</option>
				</select>	
            </div>
            </div>
			  <div class="clearfix mb10"></div>
         
            <div class="clearfix">
              <div class="form-horizontal clearfix">
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" id="sno" name="sno" placeholder="Serial Number *" field-name="Sl No" data-validation="required">
                  </div>
                  <div class="col-md-6">
                    <input type="text" class="form-control input-sm" id="make" name="make" placeholder="Make">
                  </div>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-4">
                    <input type="text" class="form-control input-sm" id="model" name="model" placeholder="Model">
                  </div>
                  <div class="col-md-4">
                    <input type="text" class="form-control input-sm" name="name" id="name" placeholder="Name" style="display: none;">
                  </div>
                  <div class="col-md-4">
                    <input type="text" class="form-control input-sm" name="memory" id="memory" placeholder="Memory" style="display: none;">
                  </div>
                  
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-4">
                    <input type="text" class="form-control input-sm" name="cpu" id="cpu" placeholder="CPU" style="display: none;">
                  </div>
                  <div class="col-md-4">
                    <input type="text" class="form-control input-sm" name="harddisk" id="harddisk" placeholder="Hard Disk" style="display: none;">
                  </div>
                  <div class="col-md-4">
                    <input type="text" class="form-control input-sm" id="monitor" name="monitor" placeholder="Monitor" style="display: none;">
                  </div>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <div class="input-group">
                      <label class="input-group-btn"> <b class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                        <input type="file" name="attachment" id="attachment"  class="hidden upload">
                        </b> </label>
                      <input type="text" class="form-control" name="attachment" readonly id="uploadFileAttachment" placeholder="Files are not selected...">
                    </div>
                  </div>
                  <div class="col-md-6">
                    <textarea class="form-control" rows="2" name="description" id="description" placeholder="Description"></textarea>
                  </div>
                </div>
                <!-- Shift A Fields -->
                <div class="clearfix" id="shiftADetails" style="display: none;">
                <div class="clearfix">
                  <h4 class="col-md-12">Shift A</h4>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <select class="form-control chosen-select" id="shiftAEmp" name="shiftAEmp">
                      <option selected value="">-Select Employee-</option>
                      <c:forEach var="option" items="${employeesList}">
					     <option value="${option.id}">${option.user.firstName} ${option.user.lastName}</option>
					  </c:forEach>
                    </select>
                  </div>
                  <div class="col-md-6">
                    <div class="input-group">
                      <label class="input-group-btn"> <b class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                        <input type="file" name="uploadBtn-dskA" id="uploadBtn-dskA"  class="hidden upload">
                        </b> </label>
                      <input type="text"  class="form-control" name="shiftAAttachment" readonly id="uploadFileShiftA" placeholder="Files are not selected...">
                    </div>
                  </div>
                </div>
                </div>
                <!-- Shift A Fields --> 
                
                <!-- Shift B Fields -->
               <div class="clearfix" id="shiftBDetails" style="display: none;">
                <div class="clearfix">
                  <h4 class="col-md-12">Shift B</h4>
                </div>
                <div class="clearfix mb10">
                  <div class="col-md-6">
                    <select class="form-control chosen-select" id="shiftBEmp" name="shiftBEmp">
                      <option selected value="">-Select Employee-</option>
                      <c:forEach var="option" items="${employeesList}">
					     <option value="${option.id}">${option.user.firstName} ${option.user.lastName}</option>
					  </c:forEach>
                    </select>
                  </div>
                  <div class="col-md-6">
                    <div class="input-group">
                      <label class="input-group-btn"> <b class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                         <input type="file" name="uploadBtn-dskB" id="uploadBtn-dskB"  class="hidden upload">
                        </b> </label>
                      <input type="text"  class="form-control"  name="shiftBAttachment" readonly id="uploadFileShiftB" placeholder="Files are not selected...">
                    </div>
                  </div>
                </div>
                </div>
                
                <!-- Shift A Fields -->
                <div class="clearfix mr">
                  <div class="pull-right">
                   <button type="submit" id="add_asset" class="btn btn-sm btn-primary">Add</button>
                  </div>
                </div>
              </div>
            </div>
            </form:form>
          </div>
        </li>
        <li>
        <a href="<c:url value='/assetCategory'/>" class=""><i class="fa fa-plus"></i>Categories</a>
        </li>
        <div class="clearfix"></div>
      </ul>
      <div class="clearfix"></div>
      
    <!-- list Table -->
    <div class="require_wrap_table table-wrap">
    <table id="assetManagementList" class="display" cellspacing="0" width="100%"> </table>
      
    </div>
    
    <!-- list Table -->
  </div></div>
  <div class="side_mask">
    <div class="proj_close"></div>
  </div>
  <div class="clients_side_wrap">
    <div class="clients_side_wrap_inn">
      <h1>Asset Management Details</h1>
      <div class="project_side_wrap_inn_tabs">
      <div id="projectSideDiv">
		     </div> 
        <div class="project_tabs">
          <div id="tab-1" class="project_tabs_inn" style="display: block;">
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
  
  
  
  <script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#addAsset_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		    e.preventDefault(); 
	          		
        		    var frm = $('#addAsset_form').serializeFormJSON();
        	        var con = JSON.stringify(frm);
        	        con = con.replace(/[{}]/g, "");
        	        
        	        //It will escape all the special characters
        	        var jsonData = encodeURIComponent(con);
        	        var form = $('#addAsset_form')[0];
	           		var formData = new FormData(form);
	          		var sourceleadUrl ='${sourceleadUrl}';
	          		
	          		
	          		 $("#add_asset").attr("disabled", "disabled");
	          		 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	          		
	          	      $.ajax({
	     		    	type: "POST",
	     		    	data: formData,
              	        contentType : 'application/json; charset=utf-8',
              	        enctype: 'multipart/form-data',
          	            processData: false,
          	            contentType:false,
              	        dataType : 'json',
	     		    	 url: sourceleadUrl+"/addCategoryAssets?${_csrf.parameterName}=${_csrf.token}&json="+jsonData, 
	     		        success: function(result) {
	     		        	if(result == 'home'){
	     	   	        	    location.replace(sourceleadUrl+ "/assetManagement");
	     		        	}
	     			        
	     		        },
	     		    }); 
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         $('#addAsset_form').showHelpOnFocus();
         $('#addAsset_form').validateOnBlur(false, validationSettings); 
            
     
</script> 


<script type="text/javascript">
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
               }
               o[this.name].push(this.value || '');
           } else {
               o[this.name] = this.value || '';
           }
       });
       return o;
   };
})(jQuery);
</script>
  
  <script>
  function flushAsset(){
	  $("#sno").val('');
	  $("#model").val('');
	  $("#monitor").val('');
	  $("#name").val('');
	  $("#make").val('');
	  $("#memory").val('');
	  $("#description").val('');
	  $("#cpu").val('');
	  $("#harddisk").val('');
	  $("#uploadFile-dskA").val('');
	  $("#uploadFile-dskB").val('');
	  
  }
  
  </script>
  
  <script>
	function getAssetList(){
		
		var sourceleadUrl ='${sourceleadUrl}';
		
		var categoryIds = [];
		$('input:checkbox[name=categoryId]:checked').each(function() 
	    {
			categoryIds.push($(this).val());
	    });
		  $.ajax({
			   	type: "POST",
			    url: sourceleadUrl+"/getAssetList?${_csrf.parameterName}=${_csrf.token}&categoryIds="+categoryIds, 	   
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
		   	     	    var shiftAEmpName = "--- ";
					    var shiftBEmpName = "--- ";
					    var model = "---";
					    var report = json[i];
					    
					    if (report.shiftAEmpName != null && report.shiftAEmpName != '' && report.shiftAEmpName != 'undefined') {
							shiftAEmpName = report.shiftAEmpName;
						}
						
						if (report.shiftBEmpName != null && report.shiftBEmpName != '' && report.shiftBEmpName != 'undefined') {
							shiftBEmpName = report.shiftBEmpName;
						}
						
						if (report.model != null && report.model != '' && report.model != 'undefined') {
							model = report.model;
						}

						result = result+'<tr>';
			   	        result = result+'<td><a href="javascript:void(0)" class="tbldata-dotdot-sm">';
					   	result = result+'<div class="proj_show" onclick="assetSideBar('+report.id+');"> <span class="tbldata-dotdot-100">'+report.slno+'</span> <i class="fa fa-binoculars"></i> </div>';
					   	result = result+'</a></td>';
					   	result = result+'<td><div class="tbldata-dotdot-sm" title="'+report.categoryName+'">'+report.categoryName+'</div></td>';
						result = result+'<td><div class="tbldata-dotdot-vsm">'+model+'</div></td>';
					   	result = result+'<td><div class="tbldata-dotdot-vsm" >'+shiftAEmpName+'</div></td>';
					   	result = result+'<td><div class="tbldata-dotdot-vsm" >'+shiftBEmpName+'</div></td>';
					   	result = result+'<td><div class="tbldata-dotdot-100">'+report.workingStatus+'</div></td>';
					   	result = result+'<td><div class="tblminwidth-50"><i class="fa fa-trash-o text-danger"></i></div></td>';
			   	        result = result +'</tr>';
	   				}
		   	     	
	   	        	result = result +'</tbody>';
	   	    	    $("#assetManagementList").empty();  
	   	    	    $("#assetManagementList").append(result)
	   	    	    pagination1();
	   	    	     
	   	    	    
	   		    	
	   		    }
		  });
	}
</script>


<script>
function pagination1(){ 
    $('#assetManagementList').DataTable( { 
    	"scrollCollapse": false,
    	"chosen-select": true,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
    })
}
</script>

<script>
 function assetSideBar(assetId){ 
		    $(".clients_side_wrap").addClass("clients_side_wrap_active");
		    $(".side_mask").fadeIn(200);
			$("body").css('overflow-y', 'hidden');
			assetDetails(assetId);
 }
</script> 
  
  
   <script>
  function assetCategory(category){
	  var selectedCategory = category.value;
	  var  assestCategory = selectedCategory.split(",");
	
	   if(assestCategory[0] == 'Desktop'){
		  document.getElementById("monitor").style.display="";
		  document.getElementById("name").style.display="";
		  document.getElementById("memory").style.display="";
		  document.getElementById("cpu").style.display="";
		  document.getElementById("harddisk").style.display="";
		  document.getElementById("shiftADetails").style.display="";
		  document.getElementById("shiftBDetails").style.display="";
		  
	  }else if((assestCategory[0] != 'Desktop' && assestCategory[0] == 'Laptop') || category == 'Laptop'){
		  document.getElementById("monitor").style.display="none";
		  document.getElementById("name").style.display="";
		  document.getElementById("memory").style.display="";
		  document.getElementById("cpu").style.display="";
		  document.getElementById("harddisk").style.display="";
		  document.getElementById("shiftADetails").style.display="";
		  document.getElementById("shiftBDetails").style.display="";
		  
		  $("#monitor").val('');
	  }else{
		  document.getElementById("monitor").style.display="none";
		  document.getElementById("memory").style.display="none";
		  document.getElementById("cpu").style.display="none";
		  document.getElementById("harddisk").style.display="none";
		  document.getElementById("shiftADetails").style.display="none";
		  document.getElementById("shiftBDetails").style.display="none";
		  $("#monitor").val('');
		  $("#name").val('');
		  $("#memory").val('');
		  $("#cpu").val('');
		  $("#harddisk").val('');
		  $("#uploadFile-dskA").val('');
		  $("#uploadFile-dskB").val('');
	  } 
	 
  }
  </script>
  
  
  <script>
  function loadCategory(){
	  document.getElementById("monitor").style.display="none";
	  document.getElementById("name").style.display="";
	  document.getElementById("memory").style.display="";
	  document.getElementById("cpu").style.display="";
	  document.getElementById("harddisk").style.display="";
	  document.getElementById("harddisk").style.display="";
	  document.getElementById("shiftADetails").style.display="";
	  document.getElementById("shiftBDetails").style.display="";
  }
  </script>
  
  
  
   <!-- <script> 
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
   </script> -->
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


// Approved Popup
    $("#category").click(function(){
		$(".approved_pop").toggle();
	});
    $("html").click(function() {
        $(".approved_pop").slideUp();
    });
    $(".approved_pop, #category").click(function(e) {
        e.stopPropagation();
    });	

// Export Popup
    $("#export-pop").click(function(){
		$(".export_pop").toggle();
	});
    $("html").click(function() {
        $(".export_pop").slideUp();
    });
    $(".export_pop, #export-pop").click(function(e) {
        e.stopPropagation();
    });	
	
// project side bar
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
 $(".collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
	 e.stopPropagation();
 });
   event.preventDefault();
});

$(".close-div").click(function(){
	 $(this).parents().toggleClass("show-div");
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

 
//upload file script -Desktop-shiftA
 document.getElementById("uploadBtn-dskA").onchange = function () {
 $('#uploadFileShiftA').val($(this).val().replace(/C:\\fakepath\\/i, ''))
 };

//upload file script -Desktop-shiftB
 document.getElementById("uploadBtn-dskB").onchange = function () {
 $('#uploadFileShiftB').val($(this).val().replace(/C:\\fakepath\\/i, ''))
 };
 
 document.getElementById("attachment").onchange = function () {
	 $('#uploadFileAttachment').val($(this).val().replace(/C:\\fakepath\\/i, ''))
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
<jsp:include page="assetManagementDetails.jsp" />
<jsp:include page="../masterFooter.jsp" />