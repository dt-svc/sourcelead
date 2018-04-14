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
<script type="text/javascript" src="/resources/js/immigration.js"></script>
<script type="text/javascript" src="/resources/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="/resources/js/ColReorderWithResize.js"></script>
<script type="text/javascript" src="/resources/js/colResizable-1.6.min.js"></script>


<style type="text/css">
    .active {
        color: red !important;
    }

</style>
<section class="content-wraper content-wraperNew">

    <section class="content-header clearfix cusFieldHead">
        <div class="pull-left positionRelative"><h2>Petition Templates</h2></div>
        <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo; Petition Templates</div>
        <div class="clearfix"></div>
    </section>

    <section class="immigraTabsWrap">
        <div class="cusFielInn">

            <div class="immigraTabsHead">
                <div class="pull-left">
                    <div class="dropdown">
                        <strong>Column Chooser </strong>
                        <button class="glyphicon glyphicon-plus" type="button" id="menu1" data-toggle="dropdown"></button>
                        <ul class="dropdown-menu" role="menu" aria-labelledby="menu1">
                            <li><a class="toggle-vis " data-column="0">Name</a></li>
                            <li><a class="toggle-vis" data-column="1">Description</a></li>
                            <li><a class="toggle-vis" data-column="2">No. of Docs</a></li>
                            <li><a class="toggle-vis" data-column="3">No. of Forms</a></li>
                            <li><a class="toggle-vis" data-column="4">No. of Tasks</a></li>
                            <li><a class="toggle-vis" data-column="6">Status</a></li>
                            <li><a class="toggle-vis" data-column="7">Action</a></li>
                        </ul>
                    </div>
                </div>
                <!--                <div class="pull-left">
                                    <div class="dropdown">
                                        <strong>Status</strong>
                                        <button class="glyphicon glyphicon-plus" type="button"
                                                data-toggle="dropdown"></button>
                                        <ul class="dropdown-menu" role="menu" aria-labelledby="menu1" id="statusFilter">
                                            <li><a class="" data-status="-1">All</a></li>
                                            <li><a class="" data-status="1">Active</a></li>
                                            <li><a class="" data-status="0">Inactive</a></li>
                                        </ul>
                                    </div>
                                </div>-->
						<span>
						<div class="dropdown mr">
					<span class="dropdown-toggle" id="" data-toggle="dropdown" aria-expanded="false">
						<i class="fa fa-filter"></i><span>Country</span><i class="fa fa-caret-down paddingLeft"></i></span>
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

        $('a.toggle-vis').on('click', function (e) {
            e.preventDefault();
            // Get the column API object
            var column = table.column($(this).attr('data-column'));
            $(this).toggleClass("active");
            // Toggle the visibility
            column.visible(!column.visible());
        });

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
        result = result + '<th>id</th>';
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
                            result = result + "<td><div class='tbldata-dotdot-me'>" + opt.name + "</div></td>";
                        } else {
                            result = result + "<td><div class='tbldata-dotdot-me'>---</div></td>";
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
                            result = result + "<td><div class='tbldata-dotdot-big'>" + opt.country + "</div></td>";
                        } else {
                            result = result + "<td><div class='tbldata-dotdot-big'>---</div></td>";
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
                            result = result + "<td><div class='tbldata-dotdot-big'>" + opt.petitionType + "</div></td>";
                        } else {
                            result = result + "<td><div class='tbldata-dotdot-big'>---</div></td>";
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
                            result = result + "<td><div class='tbldata-dotdot-big'>" + opt.description + "</div></td>";
                        } else {
                            result = result + "<td><div class='tbldata-dotdot-big'>---</div></td>";
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
                            result = result + "<td><div class='tbldata-dotdot-vsm'>" + opt.docsCount + "</div></td>";
                        } else {
                            result = result + "<td><div class='tbldata-dotdot-vsm'>---</div></td>";
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
                            result = result + "<td><div class='tbldata-dotdot-vsm'>" + opt.formsCount + "</div></td>";
                        } else {
                            result = result + "<td><div class='tbldata-dotdot-vsm'>---</div></td>";
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
                            result = result + "<td><div class='tbldata-dotdot-vsm'>" + opt.tasksCount + "</div></td>";
                        } else {
                            result = result + "<td><div class='tbldata-dotdot-vsm'>---</div></td>";
                        }

                        return result;
                    }
                },
                {
                    "targets": colIndex++,
                    "data": "status",
                    "render": function (data, type, opt, meta) {
                        var result = "";
                        if (opt.status !== null && opt.status !== 'undefined') {
                            result = result + "<td><div class='tbldata-dotdot-vsm " + (opt.status === 1 ? 'temp_active' : 'temp_inactive') + "'>" + (opt.status === 1 ? "Active" : "Inactive") + "</div></td>";
                        } else {
                            result = result + "<td><div class='tbldata-dotdot-vsm'>---</div></td>";
                        }

                        return result;
                    }
                },
                {
                    "targets": colIndex++,
                    "data": "id",
                    "render": function (data, type, opt, meta) {
                        var result = "";
                        if (opt.id !== null && opt.id !== '' && opt.id !== 'undefined') {
                            result = result + "<td><div class='tbldata-dotdot-me'>" + opt.id + "</div></td>";
                        } else {
                            result = result + "<td><div class='tbldata-dotdot-me'>---</div></td>";
                        }
                        return result;
                    }
                },
                {
                    "targets": colIndex++,
                    /*"data": "formsCount",*/
                    "render": function (data, type, opt, meta) {
                        var result = "";
                        result = result + '<td><div>';
                        '<sec:authorize access="hasAnyRole(\'ROLE_EDIT_VIEW_PETITION_TEMPLATE\')">';
                        result = result +' <i id="edit' + opt.id + '" onclick="immigration.editTemplateDetailsByRowId(' + opt.id + ');" class="glyphicon glyphicon-pencil"></i>';
                        '</sec:authorize>';
                        '<sec:authorize access="hasAnyRole(\'ROLE_DELETE_PETITION_TEMPLATE\')">';
                        result = result +' <i id="delete' + opt.id + '" onclick="immigration.deleteTempalteDetailsByRowId(' + opt.id + ');" class="glyphicon glyphicon-trash ml10"></i></div></td>';
                        '</sec:authorize>';
                        return result;
                    }
                }
            ],
            "scrollCollapse": false,
            "lengthMenu": [[10, 15, 20], [10, 15, 20]],
            "aaSorting": [[6, 'desc']],
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
        table.column(8).visible(false);
        $("#dynamicTemplateListTbl").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");

        $("#immiTemplateSerach").keyup(function (e) {
            if (e.which === 13) {
                getDynamicTemplateList("","");
            }
        });

       // $("table").colResizable(); // for resizing columns in tables.
    }
</script>