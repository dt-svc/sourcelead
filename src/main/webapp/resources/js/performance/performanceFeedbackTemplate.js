   
$('body').on('submit', '#sjfb', function(e){
	  if ($(this).validate(false, validationSettings)){
        e.preventDefault();
        var frm = $('#sjfb').serializeFormJSON();
        var con = JSON.stringify(frm);
        con = con.replace(/[{}]/g, "");
        var jsonData = encodeURIComponent(con);
        var isRemoved = '';
        //Loop through fields and save field data to array
        var fields = [];
        var submitTemplate = true;
        var fieldLabel = '';
        var fieldhint = '';
        var fieldDropDown = '';
        var myArray = new Array();
        $('.field').each(function() {
            var $this = $(this);

            //field type
            var fieldType = $this.data('type');
            
            if(fieldType == 'comments'){
            	fieldType = 'textarea';
            }

            //field label
            fieldLabel = $this.find('.field-label').val();
            
           //field hint
            fieldhint = $this.find('.field-hint').val(); 
            
            //field type dropdown
            fieldDropDown = $this.find('.field-dropdown').val(); 

            //field required
            var fieldReq = $this.hasClass('required') ? 1 : 0;
            
            
            if(fieldType == 'checkbox' || fieldType == 'radio' ||  fieldType == 'select'){
            	if($this.find('.choices li').length == 0) {
            		submitTemplate = false;
            		alertMessage("Provide choice for "+fieldLabel)
            	}
            }
            
            myArray.push(fieldLabel);

            //check if this field has choices
            if($this.find('.choices li').length >= 1) {

                var choices = [];

                $this.find('.choices li').each(function() {

                    var $thisChoice = $(this);

                    //choice label
                    var choiceLabel = $thisChoice.find('.choice-label').val();

                    //choice selected
                    var choiceSel = $thisChoice.hasClass('selected') ? 1 : 0;

                    if($thisChoice.find('.choice-label').val() != null && $thisChoice.find('.choice-label').val() != '' && $thisChoice.find('.choice-label').val() != 'undefined'){
	                    choices.push({
	                        label: choiceLabel,
	                        sel: choiceSel
	                    });
                    }

                });
            }
            
            fields.push({
            		type: fieldType,
	                label: fieldLabel,
	                hint: fieldhint,
	                dropdown : fieldDropDown,
	                req: fieldReq,
	                choices: choices,
	                isRemoved : isRemoved,
            });

        });
        
        for (var i = 0; i < myArray.length; i++) 
        {
            for (var j = 0; j < myArray.length; j++) 
            {
                if (i != j) 
                {
                    if (myArray[i].toLowerCase() == myArray[j].toLowerCase()) 
                    {
                    	submitTemplate = false;
                    	alertMessage(myArray[i]+" label duplicated")
                    }
                }
            }
        }
        
        
        var frontEndFormHTML = '';
       var templateId = document.getElementById("templateId").value;
        if(templateId == 'undefined' || templateId == null || templateId == ''){
        	templateId = '';
        }
        //Save form to database
        //Demo doesn't actually save. Download project files for save
        var con2 = JSON.stringify(fields);
        var jsonStringData = encodeURIComponent(con2);
        
        
        if(submitTemplate){
         $( "#template_save_button" ).addClass('button').val('Processing..');
      	  $.ajax({
      	    	type: "POST",
      	        url: sourceleadUrl+"/savePerformanceFeedbackTemplate?templateId="+templateId+"&jsonData="+jsonData+"&jsonStringData="+jsonStringData, 
      	        success: function(result) {
      	           if (result === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (result === "SUCCESS") {
		        		performanceManagement.getFeedbackList();
		        		$(".mask, #modalTasktype").fadeOut();
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
      	        },
      	    });   
              return true;
        }else{
        	alertMessage("Provide choice for "+fieldLabel)
        }
    	  
      }
      else{
      	return false;	
         }
    });
    	
   	$('body').on('blur', '#sjfb', function() {
        $("#sjfb").showHelpOnFocus();
        $("#sjfb").validateOnBlur(false, validationSettings);  
    });
   	  
    
    function alertMessage(message){
        var result = "";
    	result = result +"	<div class='project_inn_mask'></div>";
    	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
    	result = result +"		<p>"+message+"</p>";
    	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
    	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
    	result = result +"	</div>";
    	$("#choicepopUp").append(result);
    	$("#remove_user_pop").slideDown(200);
    	$(".project_inn_mask").fadeIn(200);
    }
    
    function cancel(){
    	$("#choicepopUp").empty();
    	$(".project_inn_mask").fadeOut(200);
    }
   
