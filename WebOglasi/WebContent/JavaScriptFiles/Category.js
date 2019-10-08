/**
 * 
 */
var editCatID;


function adminShowCategories(){
	
	$.ajax({
		url:"rest/categories/getAllCategories",
		type:"GET",
		contentType: "application/json",
		success: function(categories) {
			$("#registrationForm").hide();
			$("#allUsersDiv").hide();
			$("#addCategoryForm").hide();
			$("#allCategoriesDiv").show();
			$("#addAdForm").hide();
			$("#adsShowingDiv").hide();
			$("#adsShowingDiv2").hide();
			$("#newMessageForm").hide();
			$("#messagesDiv").hide();
			$("#messagesDiv1").hide();
			$("#adDetailsDiv").hide();
			$("#newRecensionForm").hide();
			$("#recensionsDiv").hide();
			$("#recensionsDiv1").hide();
			$("#findUserForm").hide();
			$("#findAdForm").hide();
			
			let i;
			$('#allCategoriesTable tbody').empty();

			
			for(i=0; i<categories.length; i++){
				let cat=categories[i];
				let rbr=i+1;
				let up;
					
				$('#allCategoriesTable tbody').append("<tr><th scope=\"row\">"+rbr+"</th>"+
				"<td>"+cat.name+"</td><td>"+cat.description+"</td>"+
				"<td><button type=\"button\" onclick=\"editCategoryClick('"+cat.id+","+cat.name+","+cat.description+
				"')\" class=\"btn btn-primary \">Izmeni</button>" +
				"<button type=\"button\" onclick=\"removeCategory('"+cat.id+"')\" class=\"btn btn-primary \">Izbriši</button></td></tr>"		
				);
			}
		}
	});
}
	

function addCategory() {

	
	let name=$("#nameCategory")[0].value;
	let description=$("#descriptionCategory")[0].value;
	console.log(description);

	let validation=true;
	
	if(name){
		nameCategory.style.borderColor= "white";
	}else{
		validation=false;
	    nameCategory.style.borderColor= "red";
	}
	
	if(description){
		descriptionCategory.style.borderColor= "white";
	}else{
		validation=false;
	    descriptionCategory.style.borderColor= "red";
	}
		
//	let reg=/[a-zA-Z][a-zA-Z]-[0-9]+/;
//	let numbers = /^[0-9]+$/;

    
//    if(regOznaka.match(reg))
//    {
//    	regoznf.style.borderColor= "white";
//    }else{
//    	validacija=false;
//    	regoznf.style.borderColor= "red";
//    }
//    
//    if(godProizvodnje.match(numbers))
//    {
//    	godproizvf.style.borderColor= "white";
//    }else{
//    	validacija=false;
//    	godproizvf.style.borderColor= "red";
//    }
   	
	if(validation){
		let cat=JSON.stringify({name,description});
		
		$.ajax({
			url:"rest/categories/addCategory",
			type: "POST",
			data:cat,
			contentType: "application/json",
			success: function(){
				adminShowCategories();
			},
			
			statusCode: {
				400: function() {
					alert("Vec postoji kategorija s ovim imenom");
					//$('#error').text("Greska pri unosu, korisnicko ime vec postoji!");
					//$("#error").show().delay(5000).fadeOut();
				}
			}
			
			
		})
}
	
}

function addCategoryClick(){
	$("#registrationForm").hide();
	$("#allUsersDiv").hide();
	$("#addCategoryForm").show();
	$("#allCategoriesDiv").hide();
	$("#addAdForm").hide();
	$("#adsShowingDiv").hide();
	$("#adsShowingDiv2").hide();
	$("#newMessageForm").hide();
	$("#messagesDiv").hide();
	$("#messagesDiv1").hide();
	$("#adDetailsDiv").hide();
	$("#newRecensionForm").hide();
	$("#recensionsDiv").hide();
	$("#recensionsDiv1").hide();
	$("#findUserForm").hide();
	$("#findAdForm").hide();
	$("#addCategoryButton").show();
	$("#editCategoryButton").hide();

}

function removeCategory(id){
	let urlRemove="rest/categories/removeCategory/"+id;

	$.ajax({
		url: urlRemove,
		type: "GET", 
		contentType: "application/json",
		success: function(data) {
			if(data==0){
				
				adminShowCategories();
			}else if(data==1){
				alert("Desila se greska");
			}
			
		}, 
		error: function() {
			alert("Doslo je do greske!");
			adminShowVozila();
		}
	});
}

function editCategoryClick(parametri){
	$("#registrationForm").hide();
	$("#allUsersDiv").hide();
	$("#allCategoriesDiv").hide();
	$("#addAdForm").hide();
	$("#adsShowingDiv").hide();
	$("#adsShowingDiv2").hide();
	$("#newMessageForm").hide();
	$("#messagesDiv").hide();
	$("#messagesDiv1").hide();
	$("#adDetailsDiv").hide();
	$("#newRecensionForm").hide();
	$("#recensionsDiv").hide();
	$("#recensionsDiv1").hide();
	$("#findUserForm").hide();
	$("#findAdForm").hide();
	$("#addCategoryButton").hide();
	$("#editCategoryButton").show();
	fillForCategoryForm(parametri);
	$("#addCategoryForm").show();
}

function fillForCategoryForm(parametri){
	let niz=parametri.split(",");
	editCategoryId=niz[0];
	$('#nameCategory').val(niz[1]);
	$('#descriptionCategory').val(niz[2]);

}

function editCategory(){
	let id=editCategoryId;
	let name=$("#nameCategory")[0].value;
	let description=$("#descriptionCategory")[0].value;

	
	let validation=true;
	
	if(name){
		nameCategory.style.borderColor= "white";
	}else{
		validation=false;
	    nameCategory.style.borderColor= "red";
	}
	
	if(description){
		descriptionCategory.style.borderColor= "white";
	}else{
		validation=false;
	    descriptionCategory.style.borderColor= "red";
	}
	
//	let numbers = /^[0-9]+$/;
//    if(regOznaka)
//    {
//    	regoznf.style.borderColor= "white";
//    }else{
//    	validacija=false;
//    	regoznf.style.borderColor= "red";
//    }
//    
//    
//    
//    
//    if(godProizvodnje.match(numbers))
//    {
//    	godproizvf.style.borderColor= "white";
//    }else{
//    	validacija=false;
//    	godproizvf.style.borderColor= "red";
//    }
  
	
	if(validation){
		let cat=JSON.stringify({id,name,description});
		
		$.ajax({
			url:"rest/categories/editCategory",
			type: "PUT",
			data:cat,
			contentType: "application/json",
			success: function(){
				$('#nameCategory').val('');
				$('#descriptionCategory').val('');
				adminShowCategories();
			},
			
			statusCode: {
				400: function() {
					alert("Vec postoji vec ovaj naziv kategorije");
					//$('#error').text("Greska pri unosu, korisnicko ime vec postoji!");
					//$("#error").show().delay(5000).fadeOut();
				}
			}
			
			
		})
}
}