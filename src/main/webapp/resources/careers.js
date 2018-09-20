var path = "";
function loadPage() {
    var fileName = "careers.js";

    var b = document.getElementsByTagName("script");
    for (var i = 0; i < b.length; i++) {
        if (b[i].src.indexOf(fileName) !== -1) {
//            console.log(b[i].src);
            path = b[i].src.replace("resources/careers.js", "ws/job-list-page");
        }
    }
//    console.log(path);
    $.ajax(path, {
        success: function (res) {
            $("#jobList").html(res);
            path = path.replace("ws/job-list-page", "ws/jobs");
//            console.log(path);
            loadOffers(true);
            $("#jobSearchButton").click(function () {
                searchPageNo = 0;
                loadOffers(true);
            });
        }
    });
}

function getParams(a) {
    var b = document.getElementsByTagName("script");
    for (var i = 0; i < b.length; i++) {
        if (b[i].src.indexOf("/" + a) > -1) {
            var c = b[i].src.split("?").pop().split("&");
            var p = {};
            for (var j = 0; j < c.length; j++) {
                var d = c[j].split("=");
                p[d[0]] = d[1];
            }
            return p;
        }
    }
    return{};
}

var searchPageNo = 0, searchPageSize = 10;
function loadOffers(flag) {
    var search = $("#jobSearchText").val().trim();
    $.post(path, {search: search, employmentType: $("#jobEmploymentType").val(), location: $("#jobLocation").val(), pageNo: searchPageNo, pageSize: searchPageSize}, function (data) {
        var text = "";
        var d = data.data;
        for (var e in d) {
        	 var description = (d[e].description === null ? '--' : d[e].description); 
        	 var minExp = (d[e].minExp === null ? '0' : d[e].minExp);
        	 var maxExp = (d[e].maxExp === null ? '0' : d[e].maxExp);
            text += '<div class="srchresulwidget mt">';
            text += ' <p><a target="_blank" href="' + surl + "/" +"job"+ "/" + d[e].url + "-" + d[e].id + '">' + d[e].jobTitle + '</a></p>';
            text += '<p><span class="width85">Experience :</span> ' + minExp + ' - ' + maxExp + ' years</p>';
            text += '<p><span class="width85">Positions :</span> ' + d[e].positions + '</p>';
            text += '<p><span class="width85">Posted on :</span> ' + d[e].createdDate + '</p>';
            text += '<p><span class="width85">Location :</span>' + d[e].city + ', ' + d[e].state + ', ' + d[e].country + '</p>';
            text += '<p>Job Description : <div class="desc">' +description + '</div></p>';
            text += '<div class="clearfix mt"><div class="pull-right"><a target="_blank" href="' + surl + "/" +"job"+ "/" + d[e].url + "-" + d[e].id + '" class="btn btn-default mr">View</a><a target="_blank" onclick = "guestUserRest(\''+d[e].requirementUUID+'\')" class="btn btn-primary">Apply</a></div></div></div>';
        }
        text += "<div id='searchFoooterActions' class='clearfix mt'>";
        if (searchPageNo+1 > 1) {
            text += "<div  class='pull-left'><input type='button' class='btn btn-primary' value='Previous' id='searchPrevPage'/></div>";
        }
        if ((searchPageNo+1) * searchPageSize < data.recordsTotal) {
            text += "<div class='pull-right'><input type='button' class='btn btn-primary' value='Next' id='searchNextPage'/></div>";
        }
        text += "</div>";
//        if (!flag)
//            $("#jobResultList").append(text);
//        else
        $("#jobResultList").html(text);
        $("#searchPrevPage").click(function() {
            searchPageNo = searchPageNo - 1;
            loadOffers(true);
        });
        $("#searchNextPage").click(function() {
            searchPageNo = searchPageNo + 1;
            loadOffers(true);
        });
        if(data.recordsTotal>0)
        	$("#resultTitle").html("Showing "+(searchPageNo*searchPageSize+1)+" - "+(((searchPageNo+1)*searchPageSize) > data.recordsTotal ? data.recordsTotal : ((searchPageNo+1)*searchPageSize))+" of "+data.recordsTotal + " results");
        	else
        	$("#resultTitle").html("No Jobs Found");

    });
}


function guestUserRest(requirementUUID){
		window.location.href = "/guestApplyToJob?&requirementUUID="+requirementUUID;
}

loadPage();
