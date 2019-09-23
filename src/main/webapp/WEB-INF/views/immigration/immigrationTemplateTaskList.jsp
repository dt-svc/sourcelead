<script type="text/javascript" src="/resources/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="/resources/js/ColReorderWithResize.js"></script>
<script type="text/javascript" src="/resources/js/colResizable-1.6.min.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "docStatus" scope = "page" value = '<%= session.getAttribute("success")%>'/>
<section class="addImmiWrap">
    <div class="addImmiInnWrap">
        <div class="immiStepsHead clearfix">
            <h2>Task Details</h2>
            <c:if test="${empty docStatus}">
                <ul class="addImmiSteps">
                    <li class="active page-updated">1</li>
                    <li class="active page-updated">2</li>
                    <li class="active page-updated">3</li>
                    <li class="active">4</li>
                    <b></b>
                    <div class="clearfix"></div>
                </ul>
            </c:if>
            <c:if test="${!empty docStatus}">
                <ul class="addImmiSteps">
                    <li class="active page-updated">1</li>
                    <li class="active page-updated">2</li>
                    <li class="active page-updated">3</li>
                    <li class="active page-updated">4</li>
                    <b></b>
                    <div class="clearfix"></div>
                </ul>
            </c:if>
        </div>
        <!-- <div class="dropdown">
        <strong>Column Chooser </strong>
        <button class="glyphicon glyphicon-plus" type="button" id="menu1"
                data-toggle="dropdown"></button>

        <ul class="dropdown-menu" role="menu" aria-labelledby="menu1">
                <li><a class="toggle-vis " data-column="0">Subject</a></li>
                <li><a class="toggle-vis" data-column="1">Assign To</a></li>
                <li><a class="toggle-vis" data-column="2">Priority</a></li>
                <li><a class="toggle-vis" data-column="3">Repeat</a></li>
                <li><a class="toggle-vis" data-column="4">Action</a></li>
        </ul>
</div> -->
        <div class="addImmiContent clearfix">
            <div class="immigraTabsHead">
                <a href="javascript:void(0)"  onclick="getAssignToUsersList(null);" id="addTask"><i class="glyphicon glyphicon-plus"></i> Add Task</a> 
                <span><input id="immigraListTabSerach" class="immigraTabSerach" placeholder="Subject/Priority/Repeat type="text"></span>
            </div>	
            <div id="immigrationTempDynamicTaskList" class="project_wrap_table datatable_filter">
            </div>
        </div>

        <div class="addImmiFoot">
            <a href="javascript:void(0)" onclick="immigration.gotoTemplatePreviousPage('formDetails')" class="pull-left">Back</a>
            <a href="javascript:void(0)" onclick="immigration.savePetitionTemplateDetailsValues()" class="nextBtn">Save Template</a>
            <div class="clearfix"></div>
        </div>   
    </div>
</section>
<!--   Popup Code  -->
<div class="popMask"></div>
<div id="deletetemplate"></div>
<div class="popUpOut popUpOutBig" id="taskViewPopup">
    <div class="popupHead">
        <h2>Add Task</h2><a href="javascript:void(0)" onclick="immigration.removeFormValidationErrors('petitionTemplateTaskDetails')" class="fa fa-close popCan"></a>
    </div>
    <form id="petitionTemplateTaskDetails" name="petitionTemplateTaskDetails">
        <input type="hidden" name="templateId" id="immiTempId"/>
        <input type="hidden" name="id" id="templateId"/>
        <div class="popupMid popupMidOver clearfix mt">
            <div class="row clearfix">
                <div class="col-md-6 noPad">
                    <div class="col-md-12 mb10" id = "taskNameDiv">
                        <label>Subject<span class="text-danger">*</span></label>
                        <input type="text" id ="taskName" name="taskName" class="form-control input-sm error" placeholder="Subject" field-name= "Subject" data-validation = "required validate_Space validate_length length2-100 validate_AlphaNumber">
                    </div>
                    <div class="col-md-12 mb10">
                        <label>Priority</label>
                        <select class="form-control input-sm valid" id="priority1" name="priority">
                            <option value="" selected>-- Select Priority --</option>
                            <option value="High">High</option>
                            <option value="Low">Low</option>
                            <option value="Medium">Medium</option>
                        </select>
                    </div>
                    <div class="col-md-12 mb10">
                        <label>Repeat</label>
                        <select class="form-control input-sm valid" id="taskRepeat" name="taskRepeat">
                            <option value="" selected>-- Select Repeat --</option>
                            <option value="Never">Never</option>
                            <option value="Daily">Daily</option>
                            <option value="Weekly">Weekly</option>
                            <option value="Monthly">Monthly</option>
                            <option value="Yearly">Yearly</option>
                        </select>
                    </div>

                    <div class="col-md-12">
                        <div class="clearfix">
                            <label class="paddingRight checkBox"><input id="reminder" name="Reminder" type="checkbox">Reminder</label>
                        </div>
                        <div class="mt5" id="reminderdropdown" style="display:none;">
                            <select class="chosen-select" id="taskReminderSelect" field-name= "Reminder" name="taskReminder"  multiple>
                                <option value="1-days">1 days</option>
                                <option value="1-Week">1 Week</option>
                                <option value="15-days">15 days</option>
                                <option value="1-Month">1 Month</option>
                                <option value="90-days">90 days</option>
                                <option value="180-days">180 days</option>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="col-md-6 noPad">
                    <div class="col-md-12 mb10">

                        <div class="clearfix">
                            <label>Assign To<span class="text-danger">*</span></label>
                            <div class="clearfix">
                                <label class="paddingRight"> <input type="radio"  name="assignToType" id="assignedToB" checked="checked" onclick="validateAssignToType('assingToCandidate');"> Beneficiary </label>
                                <label class="paddingLeft"> <input type="radio"  name="assignToType" id="assignedToU"  onclick="validateAssignToType('assingToUser');"> User </label>
                            </div>
                        </div>

                        <div class="clearfix" id="assignToUserDiv" style="display:none;">
                            <select class="chosen-select" multiple data-placeholder="Select User"  id="petitionAssignedTo1" name="petitionAssignedTo"  field-name="AssignTo"  data-validation-optional="true">
                            </select>
                        </div>

                    </div>
                    <div class="col-md-12 mb10">
                        <div class="row">
                            <div class="col-md-12">
                                <label><strong>Due after:<span class="text-danger">*</span></strong></label>        
                                <!-- Form inline Due after -->
                                <div class="row">
                                    <div class="col-md-6 paddingRight">
                                        <label>Months</label> <br>
                                        <div class="select-field">
                                            <select id="dueAfterMonths" name="dueAfterMonths" field-name= "Months" class="form-control input-sm" data-validation = "required">
                                                <option selected="" value="">--Select Month--</option>
                                                <c:forEach begin="0" end="12" var="m">
                                                    <option>${m}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="col-md-6 paddingLeft">
                                        <label>Days</label>
                                        <div class="select-field">
                                            <select id="dueAfterDays" name="dueAfterDays" class="form-control input-sm" field-name = "Days" data-validation = "required">
                                                <option selected="" value="">--Select Days--</option>
                                                <c:forEach begin="0" end="30" var="d">
                                                    <option>${d}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- Form inline Due after --> 
                        </div>
                    </div>
                    <div class="col-md-12 clearfix mb10">
                        <label>Description</label>
                        <textarea id="taskDescription" name="taskDescription" rows="2" placeholder="Description" class="form-control valid" field-name= "Description" data-validation = "validate_length length2-500" data-validation-optional = "true"></textarea>
                    </div>	
                </div>				
            </div>				
        </div>
    </form>
    <div class="immiPopFoot">
        <input value="Add" onclick ="immigration.savePetitionTemplateTaskDetails()" class="" id="addTaskBtn" type="button">
        <input value="Cancel" onclick="immigration.removeFormValidationErrors('petitionTemplateTaskDetails')" class="popCan" type="button">
    </div>		
</div>	


<!-- Template List code -->	
<script type="text/javascript">
    function immigrationDataTableList() {
        var result = "";
        result = result + "<table id='templateTaskListTable'><thead><tr><th>Subject</th><th>Assign To</th><th>Priority</th><th>Repeat</th><th>Action</th></tr></thead></table>";
        $("#immigrationTempDynamicTaskList").empty();
        $("#immigrationTempDynamicTaskList").append(result);
        var sourceleadUrl = '${sourceleadUrl}';
        var searchValue = $("#immigraListTabSerach").val();
        searchValue = searchValue ? searchValue : "";
        var url = sourceleadUrl + immiModulePath + "/getTemplateTaskList?templateId=" + immTemplId + "&searchValue=" + searchValue;
        immigrationPagination(url);
    }

    function immigrationPagination(url) {
        var colIndex = 0;
        $('#templateTaskListTable').DataTable({
            "processing": true,
            "serverSide": true,
            "searching": false,
            "colReorder": true,
            //'dom': 'Rlfrtip',
            "ajax": $.fn.dataTable.pipeline({
                url: url,
                method: 'POST',
                pages: 10
            }),
            "columns": [
                {"data": "taskName"},
                {"data": "assignedTo"},
                {"data": "priority"},
                {"data": "taskRepeat"}
                /*{ "data": "id"},*/
            ],
            "columnDefs": [{
                    "targets": colIndex++,
                    "data": "taskName",
                    "render": function (data, type, opt, meta) {
                        var result = "";
                        if (opt.taskName != null && opt.taskName != '' && opt.taskName != 'undefined') {
                            result = result + '<td name="subject"  data-name="subject"><div class="tbldata-dotdot-me" title = "'+opt.taskName+'">' + opt.taskName + '</div></td>';
                            result = result + '<input type="hidden" id="startDate' + opt.id + '" value="' + opt.taskName + '"/>';
                        } else {
                            result = result + '<td>---</td>';
                        }
                        return result;
                    }
                },
                {
                    "targets": colIndex++,
                    /* "data": "assignedTo", */
                    "sortable": false,
                    "render": function (data, type, opt, meta) {
                        var result = "";
                        if (opt.assignedTo != null && opt.assignedTo != '' && opt.assignedTo != 'undefined') {
                            result = result + '<td>' + opt.assignedTo + '</td><input type="hidden" id="endDate' + opt.id + '" value="' + opt.assignedTo + '"/>';
                        } else {
                            result = result + '<td>---</td>';
                        }
                        return result;
                    }
                }, {
                    "targets": colIndex++,
                    "data": "priority",
                    "render": function (data, type, opt, meta) {
                        var result = "";
                        if (opt.priority != null && opt.priority != '' && opt.priority != 'undefined') {
                            result = result + '<td>' + opt.priority + '</td><input type="hidden" id="scheduleMessage' + opt.id + '" value="' + opt.priority + '"/>';
                        } else {
                            result = result + '<td>---</td>';
                        }
                        return result;
                    }
                },
                {
                    "targets": colIndex++,
                    "data": "taskRepeat",
                    "render": function (data, type, opt, meta) {
                        var result = "";
                        if (opt.taskRepeat != null && opt.taskRepeat != '' && opt.taskRepeat != 'undefined') {
                            result = result + '<td>' + opt.taskRepeat + '</td><input type="hidden" id="scheduleMessage' + opt.id + '" value="' + opt.taskRepeat + '"/>';
                        } else {
                            result = result + '<td>---</td>';
                        }
                        return result;
                    }
                },
                {
                    "targets": colIndex++,
                    /* "data": "id", */
                    "sortable": false,
                    "render": function (data, type, opt, meta) {
                        var result = "";
                        if (opt.id != null && opt.id != '' && opt.id != 'undefined') {
                            result = result + "<td><div><i id='edit" + opt.id + "' onclick='editImmiTaskList(" + opt.id + ");' class='glyphicon glyphicon-pencil'></i><i id='delete" + opt.id + "' onclick='deleteImmiTaskList(\""+ opt.id +"\",\""+ opt.taskName +"\");' class='glyphicon glyphicon-trash ml10'></i></div></td>";
                        }
                        return result;
                    }
                }],
            "scrollCollapse": false,
            "lengthMenu": [[10, 15, 20], [10, 15, 20]],
            "aaSorting": [[3, 'desc']],
            "bLengthChange": true,
            "bDestroy": true,
            "ordering": true,
            "initComplete": function (settings, json) {
                $('#immigraListTabSerach').removeAttr('disabled');
            },
            "oLanguage": {
                "sSearch": "",
                "sZeroRecords": "", "sEmptyTable": "",
                "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
            }
        });
        $("#templateTaskListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
        $("#immigraListTabSerach").keyup(function (e) {
            if (e.which === 13) {
                immigrationDataTableList();
            }
        });
    }
</script>
<!--   Popup Code  --> 
<script>

// Error pop addSen
    $(document).ready(function () {
        $("table").colResizable(); // for resizing columns in tables.
        immigrationDataTableList();
        $("#addTask").click(function () {
            document.getElementById("petitionTemplateTaskDetails").reset();
            $("#assignToUserDiv").hide();
            $("#reminderdropdown").hide();
            $("#petitionAssignedTo1 option:selected").prop("selected", false);
            $('#taskReminderSelect').trigger("chosen:updated");
            $(".popMask").fadeIn(200);
            $("#taskViewPopup").fadeIn(100);
            immiTaskId = "";
        });
        $(".popCan").click(function () {
            $(".popMask").fadeOut(200);
            $("#taskViewPopup").fadeOut(100);
        });
//        $("#taskName").unbind("blur");
        $("#taskName").on("keyup", function () {
            immigration.checkTaskNames($(this).val().trim(),immiTaskId);
        });
    });
</script>

<script type="text/javascript">
    var config = {
        '.chosen-select': {},
        '.chosen-select-deselect': {allow_single_deselect: true},
        '.chosen-select-no-single': {disable_search_threshold: 10},
        '.chosen-select-no-results': {no_results_text: 'Oops, nothing found!'},
        '.chosen-select-width': {width: "95%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
    $('.chosen-select').each(function () {
        var $this = $(this);
        $this.next().css({'width': '100%'});
    })
    $(document).ready(function () {
        $("#cd").click(function () {
            $(".clientDetails").toggleClass('hidden', 500);
        });

    });
</script>
<script>
//Reminder Funciton
    $(function () {
        $("#reminder").click(function () {
            if ($(this).is(":checked")) {
                $("#reminderdropdown").show();
                $("#taskReminderSelect").attr("data-validation","required");
                $("#taskReminderSelect option:selected").prop("selected", false);
                $('#taskReminderSelect').trigger("chosen:updated");
            } else {
                $("#taskReminderSelect").val("")
                $("#reminderdropdown").hide();
                $("#taskReminderSelect").removeAttr("data-validation","required");
                $("#taskReminderSelect option:selected").prop("selected", false);
                $('#taskReminderSelect').trigger("chosen:updated");
            }
        });
    });

	var immiTaskId;
    function editImmiTaskList(id) {
        $.ajax({
            type: "GET",
            url: sourceleadUrl + immiModulePath + "/editImmigrationTaskList?id=" + id,
            success: function (data) {
                console.log(data);
                document.getElementById("petitionTemplateTaskDetails").reset();
                $(".popMask").fadeIn(200);
                $("#taskViewPopup").fadeIn(100);
                $("#taskName").val(data.taskName);
                $("#priority1").val(data.priority);
                $("#taskRepeat").val(data.taskRepeat);
                if (data.taskReminder != null) {
                    $("#reminder").prop('checked', true);
                    $("#reminderdropdown").show();
                    var id = data.taskReminder;
                    var ids = id.split(',');
                    for (j = 0; j < ids.length; j++) {
                        $('#taskReminderSelect option[value="' + ids[j] + '"]').prop("selected", true);
                        $('#taskReminderSelect').trigger("chosen:updated");
                    }
                }
                if (data.taskEmail != null) {
                    $("#taskEmail").prop('checked', true);
                }
                if (data.taskNotification != null) {
                    $("#taskNotification").prop('checked', true);
                }
                $("#taskEmail").val(data.taskEmail);
                $("#taskNotification").val(data.taskNotification);
                if (data.petitionAssignedTo != null) {
                    $("#assignedToU").prop('checked', true);
                    $("#assignToUserDiv").show();
                    getAssignToUsersList(data.petitionAssignedTo);
                } else if (data.petitionAssignedTo == null) {
                    getAssignToUsersList(null);
                    $("#assignedToB").prop('checked', true);
                    $("#assignToUserDiv").hide();
                }
                $("#dueAfterMonths").val(data.dueAfterMonths);
                $("#dueAfterDays").val(data.dueAfterDays);
                $("#taskDescription").val(data.taskDescription);
                $("#immiTempId").val(data.templateId);
                $("#templateId").val(data.id);
            }
        });
        immiTaskId = id;
    }


	function deleteImmiTaskList(id, taskName){
    	$(".project_inn_mask").remove();
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='remove_template_popup' class='remove_user_pop remove_user_pop_a'>";
		result = result +" <b>Do you want to remove record permanently?</b>";
		result = result + ' <div id="reasonForTemplateDiv"/>';
		result = result +"	<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onclick='deleteImmigrationTask("+id+", \""+taskName+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTemplateDelete();'></b>";
		result = result +"	</div>";
		$("#deletetemplate").append(result);
		$("#remove_template_popup").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
   
		}
    
    function deleteImmigrationTask(id, taskName) {
        $.ajax({
            type: "GET",
            url: sourceleadUrl + immiModulePath + "/deleteImmigrationTaskList?${_csrf.parameterName}=${_csrf.token}&id=" + id+"&taskName="+taskName,
            success: function (json) {
            	showTemplateMsg("Successfully Task Deleted");
                immigrationDataTableList();
                cancelTemplateDelete();
            }
        });
    }
    
    function cancelTemplateDelete(){
		$("#remove_template_popup").fadeOut(200);
		$(".project_inn_mask").fadeOut(200);
		$("#deletetemplate").empty();
	}
    
    
</script>
<script>
    function getAssignToUsersList(id) {
        $.ajax({
            type: "POST",
            url: sourceleadUrl + "/getOnboardTasksAssignToUsersList?${_csrf.parameterName}=${_csrf.token}",
            success: function (json) {
                var result = "";
                for (var i = 0; i < json.data.length; i++) {
                    var report = json.data[i];
                    $('#petitionAssignedTo1 option[value="' + report.userId + '"]').remove();
                    $('#petitionAssignedTo1')
                            .append($("<option></option>")
                                    .attr("value", report.userId)
                                    .text(report.userName));
                    $('#petitionAssignedTo1').trigger("chosen:updated");
                    if (id != null) {
                        var ids = id.split(',');
                        for (j = 0; j < ids.length; j++) {
                            if (ids[j] == report.userId) {
                                $('#petitionAssignedTo1 option[value="' + ids[j] + '"]').prop("selected", true);
                                $('#petitionAssignedTo1').trigger("chosen:updated");
                            }
                        }
                    }
                }
            }
        });
    }

</script>
<script>
    function validateDueAfter() {
        var taskMonths = document.getElementById("dueAfterMonths").value;
        var taskDays = document.getElementById("dueAfterDays").value;
        if (taskMonths == '' && taskDays == '') {
            $('#dueAfterMonths').removeAttr('data-validation-optional');
            $("#dueAfterMonths").attr("data-validation", "required");
        } else {
            $('#dueAfterDays').removeAttr('data-validation');
            $("#dueAfterMonths").attr("data-validation-optional", "true");
        }
    }
</script>  
<script>
    function validateAssignToType(assignToType) {
        if (assignToType == 'assingToUser') {
            $("#assignToUserDiv").show();
            $("#petitionAssignedTo1 option:selected").prop("selected", false);
            $('#petitionAssignedTo1').trigger("chosen:updated");

        } else {
            $("#assignToUserDiv").hide();
            $("#petitionAssignedTo1 option:selected").prop("selected", false);
            $('#petitionAssignedTo1').trigger("chosen:updated");

        }
    }
</script>

