<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="/resources/js/jquery.maskedinput-1.3.min.js?staticTime=${staticTime}"></script>

<script>
$(document).ready(function(){
	hub();
	getUsersByDepartment();
});
</script>

<section class="content-wraper contentWraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Batches</h2>
    </div>
    <div class="pull-right brud-crum">Home &raquo; Batches</div>
  </section>
  <div class="timesheet_out">
    <div class="tablelist-wrap">
      <div class="timesheetHead minHeight40 mt minHeight40">
	<div class="pull-right">
		<ul class="list-unstyled list-inline">
			<li>
			<div class="dynamic_search">
			<input type="text" placeholder="Search" id='dataTableSearch' />
			<i class="fa fa-search"></i>
			</div>              
			</li>
			<li>
			<a onClick="addbatches()" class="addBtn" id="createbatches">+ Create</a>
		</li>
		</ul>
	</div>
</div>
<!-- <div class="dynamic_search">
			<input type="text" placeholder="Search" id='dataTableSearch' onclick="getAllDeductionType();"/>
			<i class="fa fa-search"></i>
		 </div> -->
      <div class="styleTables datatable_filter">
      <div id="userGroupDiv"></div>
               <!--  <table width="100%">
                  <thead>
                    <tr>
                      <th>Group ID</th>
                      <th>Name</th>
                      <th>No. of Associates</th>
                      <th>Description</th>
                      <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                     <tr>
                      <td>
						  <div class="proj_show">
							<span  class="tbldata-dotdot-vsm inlineBlock"><a href="javascript:void(0)">Group-00245</a></span>
							<i class="fa fa-binoculars"></i>
						  </div>
                      </td>
                      <td><div class="tbldata-dotdot">Group Name</div></td>
                      <td><div class="tblminwidth-50">25</div></td>
                      <td><div class="tbldata-dotdot-custom">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries.</div></td>
                      <td><div><i class="fa fa-pencil mr"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                  </tbody>
                </table> -->
              </div>
     <!--  <nav>
        <ul id="paging" class="pagination pull-right">
          <li><a href="#">Previous</a></li>
          <li><a href="#">2</a></li>
          <li><a href="#">3</a></li>
          <li><a href="#">4</a></li>
          <li><a href="#">5</a></li>
          <li class="disabled"><a href="#">6</a></li>
          <li data-page="last"><a href="#">Next</a></li>
        </ul>
        <div class="clearfix"></div>
      </nav> -->
    <div class="clearfix"></div>
    </div>
  </div>
      <div class="side_mask"><div class="proj_close"></div></div>
    <div class="clients_side_wrap">
      <div class="clients_side_wrap_inn">
			
	<div class="clerfix showDetails">
      <div class="project_tabs minHeight_225 form-horizontal modal-body">
          <div class="viewgrid-header pull-right zIndex">
              <a href="javascript:void(0)" class="btn btn-default btn-sm" title="Edit"><i class="fa fa-pencil paddingRight"></i>Edit</a>
          </div>
          <div class="row">
              <div class="col-md-12">
                <h4>Details</h4>
              </div>
              <div class="clearfix">
              <div class="col-md-5 mb">
                 <div class="row">
                  <label class="col-md-4 control-label"><b>Group ID:</b></label>
                  <div class="col-md-8">
                      <span>Batch-00245</span>
                  </div>
                  </div>
              </div>
              <div class="col-md-5 mb">
                 <div class="row">
                  <label class="col-md-4 control-label"><b>Group Name:</b></label>
                  <div class="col-md-8">
                      <span>Rajashekar Chelpuri</span>
                  </div>
                  </div>
              </div>
              <div class="col-md-5 mb">
                  <div class="row">
                  <label class="col-md-4 control-label"><b>No of Assocaites:</b></label>
                  <div class="col-md-8">
                      <span>25</span>
                  </div>
				  </div>
              </div>
              <div class="col-md-5 mb">
                  <div class="row">
                  <label class="col-md-4 control-label"><b>Emp ID:</b></label>
                  <div class="col-md-8">
                      <span>rajkumar00001@gmail.com</span>
                  </div>
				  </div>
              </div>
             <div class="col-md-5 mb">
                  <div class="row">
                  <label class="col-md-4 control-label"><b>Name:</b></label>
                  <div class="col-md-8">
                      <span>Rajkumar Santoshi</span>
                  </div>
				 </div>
              </div>
              <div class="col-md-5 mb">
                  <div class="row">
                  <label class="col-md-4 control-label"><b>Role:</b></label>
                  <div class="col-md-8">
                      <span>Admin</span>
                  </div>
				  </div>
              </div>
              <div class="col-md-5 mb">
                  <div class="row">
                  <label class="col-md-4 control-label"><b>Status:</b></label>
                  <div class="col-md-8">
                      <span>Success</span>
                  </div>
				  </div>
              </div>
              <div class="col-md-5 mb">
                  <div class="row">
                  <label class="col-md-4 control-label"><b>Description:</b></label>
                  <div class="col-md-8 wordWrap-breakword">
                      <span>Is this template really for free? That's unbelievable!  Is this template really for free? That's unbelievable! </span>
                  </div>
              <div class="clearfix"></div>
          </div>
              </div>
                  </div>
      </div>
	</div>
</div>  
		</div> 
   </div> 
    </section>
    
    <!-- Content Area End -->
    
    <!--  Popups -->
<div class="mask" ></div>
<div class="popupWrap popBig">
 <form:form id="add_user_group_form"  name="add_user_group_form" method="post">
 <div id="loading"></div>
    <div class="popupHead">Add Group <i class="closePop pull-right popClose"></i></div>
    <div class="popupMid">
                    <div class="row minHeightcol5">
                        <div class="col-md-6">
                            <label>Group Id<span class="text-danger">*</span> </label>
                            <div class="clearfix">
                                <input type="text" class="form-control" placeholder="Enter" id="groupId" name="groupId" data-validation="required" field-name="Group Id">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label>Group Name<span class="text-danger">*</span> </label>
                            <div class="clearfix">
                                <input type="text" class="form-control" placeholder="Enter" id="groupName" name="groupName" data-validation="required" field-name="Group Name">
                            </div>
                        </div>
                
                        <div class="col-md-12">
                            <div class="popTabl mb clearfix">
                                
                                <div class="row">
                                <div class="col-md-4 mb">
                                <select class="form-control" onchange="getUsersByDepartment();" id="department" name="department">
                                     <option value="">--Select Department--</option>
                        				  <c:forEach var="dept" items="${departmentList}">
										   <option value="${dept.id}">${dept.departmentName}</option>
									</c:forEach>
                                </select>                                    
                                </div>
                                
                                <div class="col-md-3 mb col-md-offset-3	">
                                <h4>Associate <span class="text-danger">*</span></h4>
                                </div>
                                
                                </div>
                                
                                <table>
                                    <tr>
                                        <td>
                                           <select class="movSel" multiple="multiple" id="assocByDept">
                                            </select>                                            
                                        </td>
                                        <td><input type="button" id="btnRight" value ="  >  " /></td>
                                        <td>
                                        <div id="userErrorMsgDic">
                                           <select class="movSel" multiple="multiple" id="userId" name="userId"  field-name="Associate">
                                            </select>    
                                            </div>                                        
                                        </td>
                                    </tr>    
                                </table>
                            </div>
                        </div>
          	         <div class="col-md-6">
							<label>Pay Frequencey<span class="text-danger">*</span> </label>
							<div class="clearfix" >
								<select class="form-control sel" id="payFrequency" name="payFrequency" data-validation="required" field-name="Pay Frequency">
									<option value="">Selected</option>
									<option value="Weekly">Weekly</option>
									<option value="Bi-Weekly">Bi-Weekly</option>
									<option value="Monthly">Monthly</option>
									<option value="Annually">Annually</option>
								</select>
						</div>
						</div>
                
                    <div class="col-md-6">
                            <label>Description</label>
                            <div class="clearfix">
                                <textarea rows="2" class="form-control"  placeholder="Enter" id="description" name="description" data-validation="validate_Space validate_length length1-100" data-validation-optional="true" field-name="Description"></textarea>
                            </div>
                        </div>
                    </div>                
    </div>
    <div class="popupFoot text-right">
        <button type="button" class="slBtn colorBtn" id="save_user_group_button" onclick="createUserGroup();">Save</button>
        <button type="button" class="slBtn" onclick="closeBatch();">Cancel</button>
    </div>
    </form:form>
</div>
<!--  Popups -->

<script>
$(document).ready(function() {
	$('#btnRight').click(function(e) {
        var selectedOpts = $('#assocByDept option:selected');
        if (selectedOpts.length == 0) {
            alert("Nothing to move.");
            e.preventDefault();
        }
        $('#userId').append($(selectedOpts).clone());
        $(selectedOpts).remove();
        e.preventDefault();
    });
});    
</script>

 <script type="text/javascript">
 function createUserGroup() {
	 var  arr = [] ;   
	 $("#userId option").each(function()
			 {
			     arr.push($(this).val());
			 });
	 if ($('#add_user_group_form').validate(false, validationSettings)){ 
		 if(arr != null && arr != '' && arr != 'undefined'){

			    var formData = getFormData("add_user_group_form");
				$("#save_user_group_button").attr("disabled", "disabled");
				$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	            $( "#save_user_group_button" ).addClass('button').val('Processing..');
	            $.ajax({
					type : "POST",
	        	    data: formData,
					url : sourceleadUrl+"/createUserGroup?"+tokenParamName+"="+tokenValue+"&associateIds="+arr,
					success : function(result) {
						 if (result && result.response === "INVALID_DATA") {
			                    $("#save_user_group_button").removeAttr("disabled");
			                    $("#save_user_group_button").removeAttr('button').val('Save');
			                    showError(result.responseMessage);
			                } else if (result.response === "AWKWARD") {
			                    location.href = "/errorPage";
			                } else if (result.response === "SUCCESS") {
			                	$("#loading").removeAttr('style');
			                	 closeBatch();
			                	 document.getElementById("add_user_group_form").reset(); 
			                	 $("#userId").empty();
			                } else if (result.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
			                    $("#save_user_group_button").removeAttr("disabled");
			                    $("#save_user_group_button").addClass('button').val('Save');
			                    showError("Operation failed, please try again");
			                }
						
					},
				});
		 }else{
			 $('#userErrorMsgDic').find('p.jquery_form_error_message').remove();
			  $("#userId").after("<p class='jquery_form_error_message'>Associate Required</span>");
			 
		 }
	        return true;
	  }else{
		return false;
	  }
    }
 
   $('body').on('blur', '#add_user_group_form', function() {
		$("#add_user_group_form").showHelpOnFocus();
		$("#add_user_group_form").validateOnBlur(false, validationSettings);  
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
/* function addbatches() {
  $(".popupWrap, .mask").fadeIn();
} */
function closeBatch() {
	$(".popupWrap, .mask").fadeOut();
	 document.getElementById("add_user_group_form").reset(); 
	 $("#userId").empty();
}
document.getElementById("createbatches").addEventListener("click", function(){
	$(".popupWrap, .mask").fadeIn();
});
</script> 





<script>
function getUsersByDepartment(){
	var department = $("#department").val();
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getUsersByDepartment?"+tokenParamName+"="+tokenValue+"&departmentId="+department                  , 
		        success: function(json) {
		       var output = "";
				for (var i=0; i<json.length; i++)
				{
				    var opt = json[i];
				    if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefined'){
				    	output = output + "<option value='" +opt.userId+ "'>"+opt.firstName+" "+opt.lastName+" ("+opt.displayName+")</option>";
				    }else{
				    	output = output + "<option value='" +opt.userId+ "'>"+opt.firstName+" ("+opt.displayName+")</option>";
				    }
				}
				$("#assocByDept").empty();
				$("#assocByDept").append(output);
				$('#assocByDept').trigger("chosen:updated");
		        },
		    });
	 
}

</script>


<!-- <script>
function getUserGroupList(){
	 $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getUserGroupList?"+tokenParamName+"="+tokenValue+""                  , 
	        success: function(json) {
	   
	        },
	    });
}
</script> -->



<script>
  function hub(){
	    alert("Hi"); 
	     var sourceleadUrl ='${sourceleadUrl}';
		var searchValue = $("#dataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getUserGroupList?searchValue="+searchValue;
		getUserGroupList(url); 
  }
</script>

<script>
function getUserGroupList(url){
	   alert("Hi-1"); 
	    $("#userGroupDiv").empty();
 	    var result = "";
 	    /* result = result +" <div class='modal-header'>";
 	    result = result +"</div>"; */
 	    result = result +"<table id='userGroupPagination' class='display' cellspacing='0' width='100%' >"; 
        result = result +"<thead>"; 
        result = result + '<tr>';
		result = result + '<th>Group Id</th>';
		result = result + '<th>Group Name</th>';
		result = result + '<th>No.Of Associates</th>';
		result = result + '<th>Pay Frequency</th>';
		result = result + '<th>Action</th>';
		result = result + '</tr>';
        result = result +"</thead>";
        result = result +"<tbody>"; 
        result = result +" </tbody>";
        result = result +"</table>";
        $("#userGroupDiv").append(result);
        userGroupPaginationFun(url); 
}

</script>

<script>
function userGroupPaginationFun(url){
	var colIndex = 0;
    $('#userGroupPagination').DataTable( { 
    	"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10
        }),
        "columns": [
            { "data": "groupId" },
            { "data": "groupName"},
            { "data": "numOfAssociates"},
            { "data": "payFrequency" },
            { "data": "Action"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "payrollDeductionType",
	    	  	"sortable": true,
	    	  	"render": function(data, type, opt, meta) {
	    	  		        var result = "";
							result = result + ' <td><div class="tblminwidth-75"><span>'+opt.groupId+'</span></div></td>';
		                     return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "description",
              "sortable": false,
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
					result = result + '  <td><div class="tbldata-dotdot-verybig">'+opt.groupName+'</div></td>';
	              return result;
              		
              }
	          },{
            "targets": colIndex++,
            "data": "payrollDeductionMethod",
            "render": function ( data, type, opt, meta ) { 
          			  var result = "";
					 result = result + ' <td><div  class="tblminwidth-100">'+opt.numOfAssociates+'</div></td>';
            return result;
            }
          }, {
              "targets": colIndex++,
              "data": "deductionAmount",
              "render": function ( data, type, opt, meta ) {
            	  return ' <td><div  class="tblminwidth-100">'+opt.payFrequency+'</div></td>';
              }
            }, {
                "targets": colIndex++,
                "data": "Action",
                "sortable": false,
                "render": function ( data, type, opt, meta ) {
                		var result = "";
                		result = result + '<td><div class="tbldata-dotdot_50"><i class="fa fa-pencil mr"></i><i class="glyphicon glyphicon-trash"></i>';
                		result = result + '</div>';
                		result = result + '</td>';
    	              return result;
                }
          } ],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[3,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#userGroupPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getUserGroupList();
    	   }
    	});
}
</script>


<jsp:include page="../masterFooter.jsp" />

