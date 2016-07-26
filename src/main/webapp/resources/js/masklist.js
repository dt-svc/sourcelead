 function masklist(){
	 
     var countryfield = $("#CountryAutocomplete").val();//<--Get Hidden Country Value
	 var countryfield = countryfield.replace(/\s/g,'');//<--Replace white spaces
 	 if(countryfield != ''){ //<------Check if hidden value is null | if not null proceed to enable i.p and apply mask pass the country as a variable to the function
 		 applymask(countryfield);//<-- Apply Mask	  
	   	 } 
 	 else{
 		  disableip();//alert("mask disabled No-country Value");
 	 }	  
	 $('#country').focusout(function(){ //<-- on select country and focus out
								  var countryfield = $("#CountryAutocomplete").val(); //<-- get hidden value and trim white spaces
								  var countryfield = countryfield.replace(/\s/g,'');
								  if(countryfield != ''){  
									   enableip();
									   applymask(countryfield); 								    
								   } 
							 	     else{disableip(); 
							 	   }
	 });

 }		
 
function applymask(countryfield){
		var country=null;
		country = countryfield;
		if(countryfield == 'United States'){
			country="UnitedStates";
		}else if(countryfield == 'United Kingdom'){
			 country="UnitedKingdom";
		}
		else{
			country=countryfield;
		}
		var Phcode={
				 	Argentina:'+54 (##) ####-####',
					Australia:'+61 # #### #### ',
					Brazil:'+55(##)####-####',
					Canada:'+1-###-###-####',
					China:'+86##########',
					France:'+33 # ## ## ## ##',
					Germany:'+49-(##) ########',
					India:'+91-####-######', 
					Indonesia:'+62(##)####-####',  
					Italy:'+39 ## ### #####', 
					Japan:'+81 ##-###-####',
					Mexico:'+52-##-###-#######',
					Russia:'+7-###-###-##-##',
					SaudiArabia:'+966-##-###-####',
					SouthAfrica:'+27-###-###-####',
					SouthKorea:' +82 ## #### ###',
					Turkey:'+90-###-#######',
					UnitedKingdom:'+44-##-####-####',
					UnitedStates:'+1-###-###-####'
		  }
		var Mobcode={
				    Argentina:'+54(##)####-####',
					Australia:'####',
					Brazil:'+55(#)#####-####',
					Canada:'+1-###-###-####',
					China:'+86##########',
					France:'+33 # ## ## ## ##',
					Germany:'+49-(##) ########',
					India:'+91-#####-#####', 
					Indonesia:'+62(##)####-####', 
					Italy:'+39 ## ### #####', 
					Japan:'+81 ##-###-####',
					Mexico:'+52-##-###-#######',
					Russia:'+7-###-###-##-##',
					SaudiArabia:'+966-##-###-####',
					SouthAfrica:'+27-###-###-####', 
					SouthKorea:' +82 ## #### ###',
					Turkey:'+90-###-#######',
					UnitedKingdom:'+44-##-####-####',
					UnitedStates:'+1-###-###-####'
		  }
		var Pincode={
			        Argentina:'####',
					Australia:'####',
					Brazil:'#####-###',
					Canada:'a#a a#a',
					China:'######',
					France:'#####',
					Germany:'####',
					India:'### ###', 
					Indonesia:'#####', 
					Italy:'#####', 
					Japan:'###-#####',
					Mexico:'#####',
					Russia:'######',
					SaudiArabia:'#####',
					SouthAfrica:'####', 
					SouthKorea:'###-###',
					Turkey:'#####',
					UnitedKingdom:'aa#a#aa',
					UnitedStates:'#####'
		}
		var Taxcode={
				    Argentina:'###########',//
					Australia:'#########',//
					Brazil:'## ### ###',//
					Canada:'###â€“###â€“###',//
					China:'######-####-##-##-####',//
					France:'# #### ## ### ### ##',//
					Germany:'#########',//
					India:'aaaaa####a', //
					Indonesia:'################', //
					Italy:'################', //
					Japan:'###-#####',
					Mexico:'### ##### ###',//
					Russia:'############',//
					SaudiArabia:'#####',
					SouthAfrica:'####', 
					SouthKorea:'###-###',
					Turkey:'##### #####',//
					UnitedKingdom:'GB### #### ##',//
					UnitedStates:'9##-##-####'//
		}
		var Homecode={
				    Argentina:'+54(##)####-####',
					Australia:'####',
					Brazil:'+55(#)#####-####',
					Canada:'+1-###-###-####',
					China:'+86##########',
					France:'+33 # ## ## ## ##',
					Germany:'+49-(##) ########',
					India:'+91-####-######', 
					Indonesia:'+62(##)####-####', 
					Italy:'+39 ## ### #####', 
					Japan:'+81 ##-###-####',
					Mexico:'+52-##-###-#######',
					Russia:'+7-###-###-##-##',
					SaudiArabia:'+966-##-###-####',
					SouthAfrica:'+27-###-###-####', 
					SouthKorea:' +82 ## #### ###',
					Turkey:'+90-###-#######',
					UnitedKingdom:'+44-##-####-####',
					UnitedStates:'+1-###-###-####'
		}
		var Faxcode={
				    Argentina:'+54(##)####-####',
					Australia:'####',
					Brazil:'+55(#)#####-####',
					Canada:'+1-###-###-####',
					China:'+86##########',
					France:'+33 # ## ## ## ##',
					Germany:'+49-(##) ########',
					India:'+91-####-######', 
					Indonesia:'+62(##)####-####', 
					Italy:'+39 ## ### #####', 
					Japan:'+81 ##-###-####',
					Mexico:'+52-##-###-#######',
					Russia:'+7-###-###-##-##',
					SaudiArabia:'+966-##-###-####',
					SouthAfrica:'+27-###-###-####', 
					SouthKorea:' +82 ## #### ###',
					Turkey:'+90-###-#######',
					UnitedKingdom:'+44-##-####-####',
					UnitedStates:'+1-###-###-####'
		}
		
		var SSNcode={
				UnitedStates:'####'
		}
		
		var phauth=Phcode[country];
		var pinauth=Pincode[country];
		var mobauth=Mobcode[country];
		var taxauth=Taxcode[country];
		var Homeauth=Homecode[country];
		var Faxauth=Faxcode[country];
		var SSNauth=SSNcode[country];
		
		   //
		$("#mobile").mask(mobauth,{placeholder:"x"});
		$("#phone").mask(phauth,{placeholder:"x"});
		$("#zipcode").mask(pinauth,{placeholder:"x"});
		$("#taxid").mask(taxauth,{placeholder:"x"});
		$("#home").mask(Homeauth,{placeholder:"x"});
		$("#faxPhone").mask(Faxauth,{placeholder:"x"});
		$("#conferencePhone").mask(Faxauth,{placeholder:"x"});
		$(".mobileOffer").mask(Faxauth,{placeholder:"x"});
		 if(SSNauth != null && SSNauth != 'undefined'  && SSNauth != ''){
			 $("#SSN").mask(SSNauth,{placeholder:"x"});
		 }
							
							
}
						 
function enableip(){
  $('#mobile').removeAttr("disabled").css('background-color', '#fff').val('');
  $('#phone').removeAttr("disabled").css('background-color', '#fff').val('');
  $('#zipcode').removeAttr("disabled").css('background-color', '#fff').val('');
  $('#taxid').removeAttr("disabled").css('background-color', '#fff').val('');
  $('#home').removeAttr("disabled").css('background-color', '#fff');
  $('#faxPhone').removeAttr("disabled").css('background-color', '#fff').val('');
  $('#conferencePhone').removeAttr("disabled").css('background-color', '#fff').val('');
  $(".mobileOffer").removeAttr("disabled").css('background-color', '#fff').val('');
  if(SSNcode != null && SSNcode != 'undefined'  && SSNcode != ''){
	  $('#SSN').removeAttr("disabled").css('background-color', '#fff').val('');
  }
 
}
 /////////////**********************************************///////////////////
 function disableip(){
 $('#zipcode').unmask();
  $('input#zipcode').attr("disabled","").css('background-color', '#eee').val('');
  $('#mobile').unmask();
  $('input#mobile').attr("disabled","").css('background-color', '#eee').val('');
  $('#phone').unmask();
  $('input#phone').attr("disabled","").css('background-color', '#eee').val('');
  $('#home').unmask();
  $('input#home').attr("disabled","").css('background-color', '#eee').val('');
  $('#faxPhone').unmask();
  $('input#faxPhone').attr("disabled","").css('background-color', '#eee').val('');
  $('#taxid').unmask();
  $('input#taxid').attr("disabled","").css('background-color', '#eee').val('');
  
  $('#conferencePhone').unmask();
  $('input#conferencePhone').attr("disabled","").css('background-color', '#eee').val('');
  
  $(".mobileOffer").unmask();
  $(".mobileOffer").attr("disabled","").css('background-color', '#eee').val('');
  if(SSNcode != null && SSNcode != 'undefined'  && SSNcode != ''){
	  $('#SSN').unmask();
	  $('input#SSN').attr("disabled","").css('background-color', '#eee').val('');
  }

   }
 
 
 //
