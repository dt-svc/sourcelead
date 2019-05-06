
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> 
<script type="text/javascript" src="/resources/js/immigration.js?staticTime=${staticTime}"></script>
<script type="text/javascript" src="/resources/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="/resources/js/ColReorderWithResize.js"></script>
<!-- <script type="text/javascript" src="/resources/js/colResizable-1.6.min.js"></script> -->

<section class="content-wraper content-wraperNew">

    <section class="content-header clearfix cusFieldHead">
        <div class="pull-left positionRelative"><h2>Petition Templates</h2></div>
        <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;<a href="<c:url value='${appPath}/orgTemplates'/>"> Templates</a>&raquo; Petition Templates</div>
        <div class="clearfix"></div>
    </section>

    <section class="immigraTabsWrap">
        <div class="clearfix positionRelative">

            <div class="immigraTabsHead">
            <div class="pull-left">
				<div onclick="columPop();" title="Column Chooser" class="btn btn-sm btn-default pull-left"><i class="fa fa-table"></i></div>
			</div>
			<div class="colPop">
	<div class="colPopMid coldropdown-parent">
	<div class="clearfix">
		<div class="row">
				<div class="col-md-10 mb10 mt5 col-md-offset-2">
				<div class="checkbox-new  pull-left mr">
					<input id="columnsPickerUnCheckAll" class="radio-custom" name="columnsPicker" onclick="checkAllColumns(this);"  type="radio">
					<label for="columnsPickerUnCheckAll" class="radio-custom-label">Uncheck All</label>
				</div>
				<div class="checkbox-new pull-left">
					<input id="columnsPickerCheckAll" class="radio-custom" name="columnsPicker" onclick="checkAllColumns(this);"  type="radio" checked="">
					<label for="columnsPickerCheckAll" class="radio-custom-label" >Check All</label>
				</div>
			</div>
		</div>
	<div class="clearfix"></div>
	<div class="coldropdown-inner">
		<ul>
			<!-- <li>
				<div class="checkbox-new clearfix">
					<input id="a" class="checkbox-custom toggle-vis" name="paidComponent" data-column="0" type="checkbox" checked="">
					<label for="a" class="checkbox-custom-label" >Name</label>
				</div>
			</li> -->
			<li>
				<div class="checkbox-new clearfix">
					<input id="c" class="checkbox-custom toggle-vis" name="paidComponent" data-column="1" type="checkbox" checked="">
					<label for="c" class="checkbox-custom-label" >Country</label>
				</div>
			</li>
			<li>
				<div class="checkbox-new clearfix">
					<input id="d" class="checkbox-custom toggle-vis" name="paidComponent" data-column="2" type="checkbox" checked="">
					<label for="d" class="checkbox-custom-label" >Petition Type</label>
				</div>
			</li>
			<li>
				<div class="checkbox-new clearfix">
					<input id="f" class="checkbox-custom toggle-vis" name="paidComponent" data-column="3" type="checkbox" checked="">
					<label for="f" class="checkbox-custom-label" >Description</label>
				</div>
			</li>
			<li>
				<div class="checkbox-new clearfix">
					<input id="g" class="checkbox-custom toggle-vis" name="paidComponent" data-column="4" type="checkbox" checked="">
					<label for="g" class="checkbox-custom-label" >No. of Docs</label>
				</div>
			</li>
			<li>
				<div class="checkbox-new clearfix">
					<input id="h" class="checkbox-custom toggle-vis" name="paidComponent" data-column="5" type="checkbox" checked="">
					<label for="h" class="checkbox-custom-label" >No. of Forms</label>
				</div>
			</li>
			<li>
				<div class="checkbox-new clearfix">
					<input id="i" class="checkbox-custom toggle-vis" name="paidComponent" data-column="6" type="checkbox" checked="">
					<label for="i" class="checkbox-custom-label" >No. of Tasks</label>
				</div>
			</li>
			<li>
				<div class="checkbox-new clearfix">
					<input id="j" class="checkbox-custom toggle-vis" name="paidComponent" data-column="7" type="checkbox" checked="">
					<label for="j" class="checkbox-custom-label" >Status</label>
				</div>
			</li>
			<!-- <li>
				<div class="checkbox-new clearfix">
					<input id="j" class="checkbox-custom toggle-vis" name="paidComponent" data-column="7"  type="checkbox" checked="">
					<label for="j" class="checkbox-custom-label" >Action</label>
				</div>
			</li> -->
			<!-- <li>
				<div class="checkbox-new clearfix">
					<input id="k" class="checkbox-custom toggle-vis" name="paidComponent" type="checkbox"  data-column="10" checked="">
					<label for="k" class="checkbox-custom-label" >LCA End Date</label>
				</div>
			</li> -->
		</ul>
		</div>
	</div>
	<div class="colPopFot">
		<a href="javascript:void(0)" onclick="columHide();">Apply</a>
		<a href="javascript:void(0)"   onclick="columClose(); ">Cancel</a>
	</div>
</div>				
			</div>
						<span>
						<div class="dropdown mr">
					<span class="dropdown-toggle" id="" data-toggle="dropdown" aria-expanded="false">
						<i class="fa fa-filter"></i><span class="noPad ml5">Country</span><i class="fa fa-caret-down paddingLeft"></i></span>
					<div id="showListCountry" class="dropdown-menu bootstrap_dropdownMenu" role="menu" aria-labelledby="menu1">
					<ul class="list-unstyled ml10 mr10">
					  <li>
						<input value="Australia" name="resourcerequest" id="ch15" type="checkbox">
						<label for="ch15">Australia</label>
					  </li>
					  <li>
						<input id="ch16" value="Canada" name="resourcerequest" type="checkbox">
						<label for="ch16">Canada</label>
					  </li>
					  <li>
						<input id="ch17" value="India" name="resourcerequest" type="checkbox">
						<label for="ch17">India</label>
					  </li>
					  <li>
						<input id="ch18" value="Singapore" name="resourcerequest" type="checkbox">
						<label for="ch18">Singapore</label>
					  </li>
					  	<li>
						<input id="ch19" value="United Kingdom" name="resourcerequest" type="checkbox">
						<label for="ch19">United Kingdom</label>
					  </li>
					  <li>
						<input id="ch20" value="United States" name="resourcerequest" type="checkbox">
						<label for="ch20">United States</label>
					  </li>						  
					  						  
					</ul>
				  </div>
						</div>
						</span>

                <select id="statusFilter" onchange="getDynamicTemplateList($(this).val(),'')" style="padding: 3px 5px;">
                    <option value="-1">All</option>
                    <option value="1" selected="selected">Active</option>
                    <option value="0">Inactive</option>
                      <option value="2">Draft</option>
                </select>
                <sec:authorize access="hasAnyRole('ROLE_ADD_PETITION_TEMPLATE','ROLE_EDIT_VIEW_PETITION_TEMPLATE','ROLE_DELETE_PETITION_TEMPLATE')">
                <a href="<c:url value='${appPath}/immigration/addNewPetitionTemplate'/>"><i class="glyphicon glyphicon-plus"></i> Add Template</a>
 				</sec:authorize>
                <span><input class="immigraTabSerach" id ="immiTemplateSerach" placeholder="Name/Description" type="text"></span>
            </div>
            <div id ="dynamicImmigrationListViewDiv" class="project_wrap_table datatable_filter"></div>
        </div>
    </section>    
</section>
<jsp:include page="../masterFooter.jsp" />
<script>
var hideColumns = [];
var checkedCountries = [];
$(document).on('change', "#showListCountry ul li input[type=checkbox]", function(){
	if($(this).prop('checked')==true){
		checkedCountries.push($(this).val());
	}
	if($(this).prop('checked')==false){
		var uncheckedVal = $(this).val();
		checkedCountries = jQuery.grep(checkedCountries, function(value) {
			  return value != uncheckedVal;
		}); 
	}
	getDynamicTemplateList("",checkedCountries);
});


    var table = "";
    $(document).ready(function () {

        getDynamicTemplateList("","");

/*         $('a.toggle-vis').on('click', function (e) {
            e.preventDefault();
            // Get the column API object
            var column = table.column($(this).attr('data-column'));
            $(this).toggleClass("active");
            // Toggle the visibility
            column.visible(!column.visible());
        });
 */
        $("#statusFilter a").on('click', function (e) {
            alert($(this).data("status"));
            getDynamicTemplateList($(this).data("status"));
        });
    });

    function getDynamicTemplateList(status,countriesList) {
        var result = "";
        result = result + '<table id="dynamicTemplateListTbl">';
        result = result + '<thead>';
        result = result + '<tr>';
        result = result + '<th>Template Name</th>';
        result = result + '<th>Country</th>';
        result = result + '<th>Petition Type</th>';
        result = result + '<th>Description</th>';
        result = result + '<th>No. of Docs</th>';
        result = result + '<th>No. of Forms</th>';
        result = result + '<th>No. of Tasks</th>';
        result = result + '<th>Status</th>';
        result = result + '<th>Action</th>';
        result = result + '</tr>';
        result = result + '</thead>';

        result = result + '</table>';
        $("#dynamicImmigrationListViewDiv").empty();
        $("#dynamicImmigrationListViewDiv").append(result);

        var sourceleadUrl = '${sourceleadUrl}';
//        var statusFilter = $("#statusFilter").val();
        var searchValue = $("#immiTemplateSerach").val();
        searchValue = searchValue ? searchValue : "";

        var url = sourceleadUrl + "/immigration/getImmigrationTemplateList?searchValue=" + searchValue + "&status=" + (status ? status : 1)+"&countriesList="+countriesList;
        dynamicImmigrationListPagination(url);
    }

    function dynamicImmigrationListPagination(url) {
        var colIndex = 0;
        table = $('#dynamicTemplateListTbl').DataTable({
            "processing": true,
            "serverSide": true,
            "searching": false,
            "colReorder": true,
            //'dom': 'Rlfrtip',
            // "stateSave": true, // used to save the page state local or server side.
            "ajax": $.fn.dataTable.pipeline({
                url: url,
                method: 'POST',
                pages: 10
            }),
            "columns": [
                {"data": "name"},
                {"data": "country"},
                {"data": "petitionType"},
                {"data": "description"},
                {"data": "docsCount"},
                {"data": "formsCount"},
                {"data": "tasksCount"},
                {"data": "status"},
                {"data": "id"}
            ],
            "columnDefs": [
                {
                    "targets": colIndex++,
                    "data": "name",
                    "render": function (data, type, opt, meta) {
                        var result = "";
                        if (opt.name !== null && opt.name !== '' && opt.name !== 'undefined') {
                            result = result + "<td><div class='tbldata-dotdot' title='"+opt.name+"' >" + opt.name + "</div></td>";
                        } else {
                            result = result + "<td><div class='tbldata-dotdot'>---</div></td>";
                        }
                        return result;
                    }
                },
                {
                    "targets": colIndex++,
                    "data": "country",
                    "render": function (data, type, opt, meta) {
                        var result = "";
                        if (opt.country !== null && opt.country !== '' && opt.country !== 'undefined') {
                            result = result + "<td><div class='tbldata-dotdot-vsm'  title='"+opt.country+"'>" + opt.country + "</div></td>";
                        } else {
                            result = result + "<td><div class='tbldata-dotdot-vsm'>---</div></td>";
                        }
                        return result;
                    }
                },
                {
                    "targets": colIndex++,
                    "data": "petitionType",
                    "render": function (data, type, opt, meta) {
                        var result = "";
                        if (opt.petitionType !== null && opt.petitionType !== '' && opt.petitionType !== 'undefined') {
                            result = result + "<td><div class='tbldata-dotdot-vsm'  title='"+opt.petitionType+"'>" + opt.petitionType + "</div></td>";
                        } else {
                            result = result + "<td><div class='tbldata-dotdot-vsm'>---</div></td>";
                        }
                        return result;
                    }
                },
                {
                    "targets": colIndex++,
                    "data": "description",
                    "render": function (data, type, opt, meta) {
                        var result = "";
                        if (opt.description !== null && opt.description !== '' && opt.description !== 'undefined') {
                            result = result + "<td><div class='tbldata-dotdot' title='"+opt.description+"'>" + opt.description + "</div></td>";
                        } else {
                            result = result + "<td><div class='tbldata-dotdot'>---</div></td>";
                        }
                        return result;
                    }
                },
                {
                    "targets": colIndex++,
                    "data": "docsCount",
                    "render": function (data, type, opt, meta) {
                        var result = "";
                        if (opt.docsCount !== null && opt.docsCount !== 'undefined') {
                            result = result + "<td><div class='tbldata-dotdot_80'>" + opt.docsCount + "</div></td>";
                        } else {
                            result = result + "<td><div class='tbldata-dotdot_80'>---</div></td>";
                        }
                        return result;
                    }
                },
                {
                    "targets": colIndex++,
                    "data": "formsCount",
                    "render": function (data, type, opt, meta) {
                        var result = "";
                        if (opt.formsCount !== null && opt.formsCount !== 'undefined') {
                            result = result + "<td><div class='tbldata-dotdot_80'>" + opt.formsCount + "</div></td>";
                        } else {
                            result = result + "<td><div class='tbldata-dotdot_80'>---</div></td>";
                        }
                        return result;
                    }
                },
                {
                    "targets": colIndex++,
                    "data": "tasksCount",
                    "render": function (data, type, opt, meta) {
                        var result = "";
                        if (opt.tasksCount !== null && opt.tasksCount !== 'undefined') {
                            result = result + "<td><div class='tbldata-dotdot_80'>" + opt.tasksCount + "</div></td>";
                        } else {
                            result = result + "<td><div class='tbldata-dotdot_80'>---</div></td>";
                        }

                        return result;
                    }
                },
                {
                    "targets": colIndex++,
                    "data": "status",
                    "sortable": false,
                    "render": function (data, type, opt, meta) {
                        var result = "";
                        if (opt.status !== null && opt.status !== 'undefined') {
                        	var tempStatusVal = opt.status;
                        	var tmpStatusClass='';
                        	if(opt.status == 0){
                        		tempStatusVal ="Inactive";
                        		tmpStatusClass ="temp_inactive";
                        	}else if(opt.status == 1){
                        		tempStatusVal ="Active";
                        		tmpStatusClass ="temp_active";
                        	}if(opt.status ==2){
                        		tempStatusVal ="Draft";
                        		tmpStatusClass ="temp_Draft";
                        	}
                            result = result + "<td><div class='tbldata-dotdot_80  " + tmpStatusClass + "'>" + tempStatusVal + "</div></td>";
                        } else {
                            result = result + "<td><div class='tbldata-dotdot_80 '>---</div></td>";
                        }

                        return result;
                    }
                },
                {
                    "targets": colIndex++,
                    "data": "action",
                    "sortable": false,
                    "render": function (data, type, opt, meta) {
                    	  var result = "";
                    	if (opt.status !== null && opt.status !== '' && opt.status !== 0) {
                        result = result + '<td><div>';
                        '<sec:authorize access="hasAnyRole(\'ROLE_EDIT_VIEW_PETITION_TEMPLATE\')">';
                        result = result +' <i id="edit' + opt.templateUUID + '" onclick="immigration.editTemplateDetailsByRowId(\''+ opt.templateUUID + '\');" class="glyphicon glyphicon-pencil"></i>';
                        '</sec:authorize>';
                        '<sec:authorize access="hasAnyRole(\'ROLE_DELETE_PETITION_TEMPLATE\')">';
                        result = result +' <i id="delete' + opt.templateUUID + '" onclick="disableTemplateStatus(\''+ opt.templateUUID + '\');" class="glyphicon glyphicon-trash ml10"></i></div></td>';
                        '</sec:authorize>';
                        
                    	}else{
                    		
                    		result = result +' <div class="tblminwidth-75">';
                    		result = result + "<span class='glyphicon glyphicon-comment ml10 edit-reason text-info cursorPointer'><div class='edit-status'><p>"+opt.rejectReason+"</p></div></span>";
                            result = result +' <span onclick="immigration.reactiveImmigrationTemplateById(\''+ opt.templateUUID + '\');" class="cursorPointer glyphicon fa fa-repeat ml10 text-success"></span></div></td>';
                            result = result +' </div>';

                    	}
                    	 return result;
                    }
                }
            ],
            "scrollCollapse": false,
            "lengthMenu": [[10, 15, 20], [10, 15, 20]],
            "aaSorting": [],
            "bLengthChange": true,
            "bDestroy": true,
            "ordering": true,
            "initComplete": function (settings, json) {
            },
            "oLanguage": {
                "sSearch": "",
                "sZeroRecords": "", "sEmptyTable": "",
                "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
            }
        });
      //  table.column(8).visible(false);
        $("#dynamicTemplateListTbl").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");

        $("#immiTemplateSerach").keyup(function (e) {
            if (e.which === 13) {
                getDynamicTemplateList( $("#statusFilter").val(),"");
                for (var i=0; i<hideColumns.length; i++) {
           		 table.column(hideColumns[i]).visible(false); // hide the columns after filters also.
           		}
            }
        });

       // $("table").colResizable(); // for resizing columns in tables.
    }
</script>
<div id="popUp"></div>
<script>
function cancelTerminationReson(){
	$("#remove_template_pop, .project_inn_mask").fadeOut();
	$("#reasonForTemplate").val('');
	$("#reasonForTemplateDiv").find("p.jquery_form_error_message").remove();
	$("#reasonForTemplateDiv").empty(); 
}
function disableTemplateStatus(temlateUuid){ // only stauts updated with reason. active 1 , deactive 0.
	$(".project_inn_mask").remove();
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_template_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<textarea  id='reasonForTemplate' name='reasonForSpoc' data-validation='required validate_Space validate_Description validate_length length2-250' placeholder='Reason' onkeypress=';' ></textarea>";
	result = result + ' <div id="reasonForTemplateDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onclick='immigration.deleteTempalteDetailsByRowId(\""+temlateUuid+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTerminationReson();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_template_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function columPop(){
	$(".colPop").show();
}
function columClose(){
	$(".colPop").hide();
	$("#columnsPickerUnCheckAll").prop('checked',false);
	$("#columnsPickerCheckAll").prop('checked',true);
	$('.toggle-vis:input:checkbox').prop('checked',true);
}
function columHide(){
	hideColumns=[]; // reset the array object.
	$(".toggle-vis").each(function() {
	   var chkdVal= $(this).prop('checked');
	   var column = table.column($(this).attr('data-column'));
		  if(chkdVal == true){
		         column.visible(true);
		    }
		    else{
		      column.visible(false);
		      hideColumns.push($(this).attr('data-column')); // used for hiding the columns when user searched the data.
		  }
    });
	$(".colPop").hide();
}

function checkAllColumns(obj){
	var id = obj.id;
	if(id =='columnsPickerCheckAll'){
		$('.toggle-vis:input:checkbox').prop('checked',true);
     }
	else if(id =='columnsPickerUnCheckAll'){
		$('.toggle-vis:input:checkbox').prop('checked',false);
	}
}

</script>

