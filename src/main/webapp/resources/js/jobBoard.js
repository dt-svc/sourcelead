$(function () {

    var validationSettings = {
        errorMessagePosition: 'element',
        borderColorOnError: '',
        scrollToTopOnError: true,
        dateFormat: 'yyyy/mm/dd'
    };

    $("input#user[name=applicableFor]").click(function () {
        $("#user-div").show();
        $("#team-div").show();
    });

    $("input#all[name=applicableFor]").click(function () {
        $("#user-div").hide();
        $("#team-div").hide();
    });

    $("input#none[name=applicableFor]").click(function () {
        $("#user-div").hide();
        $("#team-div").hide();
    });

    $("div[data-compname=jobBoard]").click(function () {
        var id = $(this).data('id');
        var ele = $(this);
        var jobBoardId = $(this).data('jobboardid');
        if (id === "0" || id === 0) {
            $.post(sourceleadUrl + "/save-job-board", {jobBoardId: jobBoardId}, function (data) {
                if (data.response === "SESSION_TIMEDOUT") {
                    showMessage(data.responseMessage);
                    window.setTimeout('location.href="/login"', 2000);
                } else if (data.response === "SUCCESS") {
                    $("#msgDiv").html("Job board activated");
                    msgFadein();
                    $(ele).data('id', data.object);
                } else {
                    showMessage(data.responseMessage);
                }
            });
        } else {
            $.post(sourceleadUrl + "/change-job-board-satatus", {orgJobBoardId: id}, function (data) {
                if (data.response === "SESSION_TIMEDOUT") {
                    showMessage(data.responseMessage);
                    window.setTimeout('location.href="/login"', 2000);
                } else if (data.response === "SUCCESS") {
                    if ($(ele).parent("label").find("input").is(":checked"))
                        $("#msgDiv").html("Job board activated");
                    else
                        $("#msgDiv").html("Job board deactivated");
                    msgFadein();
                } else {
                    showMessage(data.responseMessage);
                }
            });
        }
    });

    $("#jobBoardFilter").change(function () {
        filterJobBoardAccounts($(this).val(), "");
    });

    $("#jobBoardFilterSearch").keyup(function (e) {
//        if (e.which === 13) {
//            filterJobBoardAccounts($("#jobBoardFilter").val(), $(this).val().trim());
//        }
    });

    $("#addAccountBtn").click(function () {
        $("#jobBoardId").removeProp('disabled');
        $(".mask").show();
        $(".adduser-popup").fadeIn(100);
        $("#jobBoardId").val('');
        $("#accountIdHidden").val('0');
        $("#accountAccessHidden").val('false');
        $("#accountActiveHidden").val('false');
        showOrHideJobBoardFormFields(0);
        $('#jobBoardAccountForm').showHelpOnFocus();
        $('#jobBoardAccountForm').validateOnBlur(false, validationSettings);
    });

    $("#saveJobBoardAccount").click(function () {
        if ($("#jobBoardAccountForm").validate(false, validationSettings)) {
            $("#saveJobBoardAccount").val("Please wait...");
            $("#saveJobBoardAccount").prop('disabled', 'disabled');
            var data = {};
            data["jobBoardId"] = $("#jobBoardId").val();
            data["id"] = $("#accountIdHidden").val();
            data["active"] = $("#accountActiveHidden").val();
            data["accessToAll"] = $("#accountAccessHidden").val();
            $("[data-fieldtype=account]:visible").each(function () {
                data[$(this).data('prop')] = $(this).find("input").val().trim();
            });
            data["orgJobBoardId"] = $("#jobBoardId option:selected").data('orgjobboardid');
            console.log(data);
            $.post(sourceleadUrl + "/save-job-board-account", data, function (data) {
                $("#saveJobBoardAccount").removeProp('disabled');
                $("#saveJobBoardAccount").val("Save");
                if (data.response === "SESSION_TIMEDOUT") {
                    showMessage(data.responseMessage);
                    window.setTimeout('location.href="/login"', 2000);
                } else if (data.response === "SUCCESS") {
                    $("#msgDiv").html("Job board account " + (parseFloat($("#accountIdHidden").val()) > 0 ? "updated" : "created"));
                    msgFadein();
                    $(".mask").hide();
                    $(".adduser-popup").fadeOut(100);
                    $("#jobBoardFilter").val('0');
                    $("#jobBoardFilterSearch").val('');
                    filterJobBoardAccounts(0, "");
                } else {
                    showMessage(data.responseMessage);
                }
            });
        }
    });

    $("#saveJBAccessInfo").click(function () {
        $("#saveJBAccessInfo").val("Please wait...");
        $("#saveJBAccessInfo").prop('disabled', 'disabled');
        var data = {};
        data["orgJobBoardAccountId"] = $("#orgJobBoardAccountId").val();
        data["allFlag"] = $("input#all[name=applicableFor]").is(":checked");
        if ($("input#user[name=applicableFor]").is(":checked")) {
            if($("#userSelectList").val() !== "")
                data["userList"] = $("#userSelectList").val();
            if($("#teamSelectList").val() !== "")
                data["teamList"] = $("#teamSelectList").val();
        }
        $.post(sourceleadUrl + "/save-job-board-account-access", data, function (data) {
            $("#saveJBAccessInfo").removeProp('disabled');
            $("#saveJBAccessInfo").val("Save");
            if (data.response === "SESSION_TIMEDOUT") {
                showMessage(data.responseMessage);
                window.setTimeout('location.href="/login"', 2000);
            } else if (data.response === "SUCCESS") {
                $("#msgDiv").html("Job board account access updated");
                msgFadein();
                $(".mask").hide();
                $(".accountAssign-popup").fadeOut(100);
                $("#jobBoardFilter").val('0');
                $("#jobBoardFilterSearch").val('');
                filterJobBoardAccounts(0, "");
            } else {
                showMessage(data.responseMessage);
            }
        });
    });
});

var jobBoardAccountData = {};
function filterJobBoardAccounts(id, search) {
    if (id === 0 || id === "0") {
        $("#jobBoardFilter").val('0');
    }
    $.post(sourceleadUrl + "/org-job-board-accounts", {jobBoardId: id, search: search}, function (data) {
        if (data.response === "SESSION_TIMEDOUT") {
            window.setTimeout('location.href="/login"', 2000);
        } else if (data.response === "SUCCESS") {
            $("#jobBoardAccountTableBody").empty();
            var html = $("#jobBoardAccountTableDiv table").html();
            $("#jobBoardAccountTableDiv").empty();
            var result = "";
            jobBoardAccountData = data.data;
            for (var e in data.data) {
                var report = data.data[e];
                result = result + '<tr>';
                result = result + '<td>';
                result = result + '<div class="tblminwidth-75">' + (report.jobBoardName) + '</div>';
                result = result + '</td>';
                result = result + '<td>';
                result = result + '<div class="tblminwidth-125">' + (report.name) + '</div>';
                result = result + '</td>';
                result = result + '<td>';
                result = result + '<div class="tblminwidth-125">' + (report.username ? report.username : "") + '</div>';
                result = result + '</td>';
                result = result + '<td>';
                result = result + '<div class="tblminwidth-75">' + (report.accessToAll ? "All" : report.assignCount !== "0 / 0" ? report.assignCount : "None") + '</div>';
                result = result + '</td>';
                
                result = result + '<td>';
                result = result + '<div class="tblicons"><span>' + (report.access ? "" : "<a href='javascript:void(0)' onclick='assignMembersToJBAccount(" + report.id + ")' class='mr10'> Assign</a>") + '</span>';
                result = result + '<span><a href="javascript:void(0)" onclick="editJobBoardAccount(' + report.id + ')"> Edit</a></span></div>';
                result = result + '</td>';
                
                result = result + '<td>';
                result = result + '<div class="tblminwidth-50"><div class="toggle-button"><label class="switch" style="top: 5px;"><input onclick="enableOrDisableJBAccount(' + report.id + ')" type="checkbox" ' + (report.active ? 'checked' : '') + '/> <div class="slider round"></div> </label> </div></div>';
                result = result + '</td>';
                result = result + '</tr>';
            }
            $("#jobBoardAccountTableDiv").html('<table id="jobBoardAccountTable">' + html + '</table>');
            $('#jobBoardAccountTableBody').html(result);
            $('#jobBoardAccountTable').DataTable({
                "scrollCollapse": false,
                "lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
                "bLengthChange": true,
                "bDestroy": true,
                "ordering": true,
                "oLanguage": {
                    "sSearch": "",
                    "sZeroRecords": "",
                    "sEmptyTable": ""
                }
            });
        }
    });

}

function closeUserPop() {
    $(".mask").hide();
    $(".accountAssign-popup, .adduser-popup").fadeOut(100);
}

function enableOrDisableJBAccount(id) {
    var record = {};
    for (var e in jobBoardAccountData) {
        if (jobBoardAccountData[e].id === id) {
            record = jobBoardAccountData[e];
        }
    }
    if (record.length === 0) {
        showMessage("Unable to process your request, please try again");
    } else {
        $.post(sourceleadUrl + "/change-job-board-account-satatus", {orgJobBoardAccountId: id}, function (data) {
            if (data.response === "SESSION_TIMEDOUT") {
                showMessage(data.responseMessage);
                window.setTimeout('location.href="/login"', 2000);
            } else if (data.response === "SUCCESS") {
                $("#msgDiv").html("Job board account status changed");
                msgFadein();
                $("#jobBoardFilter").val('0');
                $("#jobBoardFilterSearch").val('');
                filterJobBoardAccounts(0, "");
            } else {
                showMessage(data.responseMessage);
            }
        });
    }
}

function editJobBoardAccount(id) {
    var record = {};
    for (var e in jobBoardAccountData) {
        if (jobBoardAccountData[e].id === id) {
            record = jobBoardAccountData[e];
        }
    }
    if (record.length === 0) {
        showMessage("Unable to process your request, please try again");
    } else {
        $("#jobBoardId").val(record.jobBoardId);
        $("#jobBoardId").prop('disabled', 'disabled');
        $(".mask").show();
        $(".adduser-popup").fadeIn(100);
        $("#accountActiveHidden").val(record.active);
        $("#accountAccessHidden").val(record.accessToAll);
        showOrHideJobBoardFormFields(record.jobBoardId, record);
        $('#jobBoardAccountForm').showHelpOnFocus();
        $('#jobBoardAccountForm').validateOnBlur(false, validationSettings);
        $("#accountIdHidden").val(record.id);
    }
}

function msgFadein() {
    $("#selector1").fadeIn(200);
    $('#selector1').delay(2000).fadeOut('slow');
}

function assignMembersToJBAccount(id) {
    var record = {};
    for (var e in jobBoardAccountData) {
        if (jobBoardAccountData[e].id === id) {
            record = jobBoardAccountData[e];
        }
    }
    if (record.length === 0) {
        showMessage("Unable to process your request, please try again");
    } else {
        $("#accountName").html(record.name);
        $("#user-div").hide();
        $("#team-div").hide();
        if (record.accessToAll)
            $("input#all[name=applicableFor]").prop('checked', 'checked');
        else if (record.assignCount && record.assignCount !== "0 / 0") {
            $("input#user[name=applicableFor]").prop('checked', 'checked');
            $("#user-div").show();
            $("#team-div").show();
            $("#userSelectList option").each(function () {
                $(this).removeProp('selected');
            });
            $("#teamSelectList option").each(function () {
                $(this).removeProp('selected');
            });
            $('#userSelectList').trigger("chosen:updated");
            $('#teamSelectList').trigger("chosen:updated");
            $.post(sourceleadUrl + "/org-job-board-account-user-list", {orgJobBoardAccountId: id}, function (data) {
                if (data.response === "SESSION_TIMEDOUT") {
                    showMessage(data.responseMessage);
                    window.setTimeout('location.href="/login"', 2000);
                } else if (data.response === "SUCCESS") {
                    var a = [];
                    var i = 0;
                    for (var e in data.data) {
                        if (data.data[e].userId > 0)
                            a[i++] = data.data[e].userId;
                    }
                    $("#userSelectList").val(a);
                    var a = [];
                    var i = 0;
                    for (var e in data.data) {
                        if (data.data[e].teamId > 0)
                            a[i++] = data.data[e].teamId;
                    }
                    $("#teamSelectList").val(a);
                    $('#userSelectList').trigger("chosen:updated");
                    $('#teamSelectList').trigger("chosen:updated");
                } else {
                    showMessage(data.responseMessage);
                }
            });
            $('#userSelectList').trigger("chosen:updated");
            $('#teamSelectList').trigger("chosen:updated");
        } else
            $("input#none[name=applicableFor]").prop('checked', 'checked');
        $("#orgJobBoardAccountId").val(id);
        $(".mask").show();
        $(".accountAssign-popup").fadeIn(100);
    }
}

function showOrHideJobBoardFormFields(id, record) {
    if (id === "0" || id === 0 || id === "") {
        $("[data-fieldtype=account]").hide();
        $("[data-fieldtype=account]").find(".text-danger").hide();
        $("[data-fieldtype=account]").find("input").removeAttr('data-validation');
    } else {
        $("[data-fieldtype=account]").hide();
        $("[data-fieldtype=account]").each(function () {
            var val = $(this).data('fieldfor');
            var val1 = $(this).data('requiredfor');
            $(this).find(".text-danger").hide();
            $(this).find("input").removeAttr('data-validation');

            if (val === "0" || val === 0) {
                $(this).show();
                if (record) {
                    $(this).find("input[type=text]").val(record[$(this).data('prop')]);
                } else {
                    $(this).find("input[type=text]").val('');
                }
            }
            if (val1 === "0" || val1 === 0) {
                $(this).find(".text-danger").show();
                $(this).find("input").attr('data-validation', 'required');
            }
            if (typeof val === "number") {
                if (parseInt(val) === parseInt(id)) {
                    $(this).show();
                    if (record) {
                        $(this).find("input[type=text]").val(record[$(this).data('prop')]);
                    } else {
                        $(this).find("input[type=text]").val('');
                    }
                }
            } else {
                console.log(val);
                val = val.split(",");
                for (var e in val) {
                    if (parseInt(val[e]) === parseInt(id)) {
                        $(this).show();
                        if (record) {
                            $(this).find("input[type=text]").val(record[$(this).data('prop')]);
                        } else {
                            $(this).find("input[type=text]").val('');
                        }
                    }
                }
            }

            if (typeof val1 === "number") {
                if (parseInt(val1) === parseInt(id)) {
                    $(this).find(".text-danger").show();
                    $(this).find("input").attr('data-validation', 'required');
                }
            } else {
                val1 = val1.split(",");
                for (var e in val1) {
                    if (parseInt(val1[e]) === parseInt(id)) {
                        $(this).find(".text-danger").show();
                        $(this).find("input").attr('data-validation', 'required');
                    }
                }
            }
        });
    }
}