var commentUsers = [], selectedUsers = [], commentConfig = {};

function commentMembers(projectId){
	commentUsers = [];
	selectedUsers = [];
		$.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getProjectEmployeeListById?projectId="+projectId+"&status=Active", 
	        success: function(json) {
	        	for (var i=0; i<json.length; i++)
				{
	        		var opt = json[i];
	        		commentUsers.push({"id": opt.id, "fullName": opt.empName, "email": opt.empEmail});
				}
	        	initializeFroalaEditor();
	        }
		});	
} 


function initializeFroalaEditor() {
	
    commentConfig = {
      at: "@",
      data: commentUsers,
      displayTpl: '<li data-id="${id}">${fullName} <small>${email}</small></li>',
      limit: 200
    }
    
    $('#taskComment').atwho(commentConfig);
    $('#taskComment').on('inserted.atwho', function (event, $li, bEvent) {
        selectedUsers.push($($li).data("id"));
      }).on('reposition.atwho', function (event, offset) {
    	  var removeUser = $("#taskComment").find("span[class='atwho-query']").data("id");
    	  if(removeUser) {
	    	  selectedUsers.splice($.inArray(removeUser, selectedUsers), 1);
    	  }
        })
}
