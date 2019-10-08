/**
 * 
 */
function registration(){
	let name=$("#nameReg")[0].value;
	let surname=$("#surnameReg")[0].value;
	let email=$("#emailReg")[0].value;
	let username=$("#usernameReg")[0].value;
	let password=$("#passwordReg")[0].value;
	let confirm=$('#confirmReg').val();
    let phone=$("#phoneReg")[0].value;
    let city=$("#cityReg")[0].value;

	let validation=true;
	
	let letters = /^[A-Za-z]+$/;
	
	if(name.match(letters)){
		nameReg.style.borderColor= "white";
	}else{
		validation=false;
	    nameReg.style.borderColor= "red";
	}
	
	if(surname.match(letters)){
		surnameReg.style.borderColor= "white";
	}else{
		validation=false;
	    surnameReg.style.borderColor= "red";
	}
	
	let numbers = /^[0-9]+$/;
    if(phone.match(numbers))
    {
    	phoneReg.style.borderColor= "white";
    }else{
    	validatiion=false;
    	phoneReg.style.borderColor= "red";
    }
    
    
    
    if(password)
    {
    	passwordReg.style.borderColor= "white";

    }else{
    	validation=false;
    	passwordReg.style.borderColor= "red";
    }
    
    if(confirm.length == password.length)
    {
    	confirmReg.style.borderColor= "white";

    }else{
    	validation=false;
    	confirmReg.style.borderColor= "red";
    }
    
    if(city)
    {
    	cityReg.style.borderColor= "white";

    }else{
    	validation=false;
    	cityReg.style.borderColor= "red";
    }
        
	
	if(validation){
		let u=JSON.stringify({username,password,name,surname,phone,email,city});
		
		$.ajax({
			url:"rest/auth/signUp",
			type: "POST",
			data:u,
			contentType: "application/json",
			success: function(){
				alert('Usepsna registracija!');
				window.location.replace("index.html");
				
			},
			
			statusCode: {
				400: function() {
					alert("Vec postoji username");
					//$('#error').text("Greska pri unosu, korisnicko ime vec postoji!");
					//$("#error").show().delay(5000).fadeOut();
				}
			}
			
			
		})
	}
}