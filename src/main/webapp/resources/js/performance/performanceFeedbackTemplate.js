   
$('body').on('submit', '#sjfb', function(e){
	  if ($(this).validate(false, validationSettings)){
        e.preventDefault();
        var frm = $('#sjfb').serializeFormJSON();
        var con = JSON.stringify(frm);
        con = con.replace(/[{}]/g, "");
        var jsonData = encodeURIComponent(con);
        var isRemoved = '';
        var isLabelModified = 'N';
        //Loop through fields and save field data to array
        var fields = [];
        var submitTemplate = true;
        var fieldLabel = '';
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
	                req: fieldReq,
	                choices: choices,
	                isRemoved : isRemoved,
	                isLabelModified : isLabelModified
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
        	  /* $( "#save_button" ).addClass('button').val('Processing..');
      	  $.ajax({
      	    	type: "POST",
      	        url: sourceleadUrl+"/saveOrgSubmissionTemplate?${_csrf.parameterName}=${_csrf.token}&templateId="+templateId+"&jsonData="+jsonData+"&jsonStringData="+jsonStringData, 
      	        success: function(result) {
      	        	if((result == 'home')){
      	        		location.replace(sourceleadUrl+"/orgSubmissionTemplate"); 
      		        } 
      	        },
      	    });   */
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
   	  
// Back To Top 
    $(window).scroll(function() {
        if ($(this).scrollTop() > 200) {
            $('.backtotop').fadeIn('slow');
        } else {
            $('.backtotop').fadeOut('slow');
        }
    });
    $('.backtotop').click(function() {
        $("html, body").animate({scrollTop: 0}, 500);
        return false;
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
