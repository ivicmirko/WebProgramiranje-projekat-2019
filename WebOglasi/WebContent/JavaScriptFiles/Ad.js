/**
 * 
 */
var image="";
var selectedCategory=1;
var username="";
var adDetails;
var editAdId;

function addAdFormShow(){
	$("#registrationForm").hide();
	$("#allUsersDiv").hide();
	$("#addCategoryForm").hide();
	$("#allCategoriesDiv").hide();
	$("#addAdForm").show();
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
	
	$("#addAdButton").show();
	$('#editAdButton').hide()
	$("#catOfAdDiv").show();
	
	$.ajax({
		url:"rest/categories/getAllCategories",
		type:"GET",
		contentType: "application/json",
		success: function(categories) {
			
			let i;
			$('#selectCategoryForAdId').empty();
			
			
			for(i=0; i<categories.length; i++){
				let cat=categories[i];
				let rbr=i+1;
				let up;
					
				$('#selectCategoryForAdId').append("<option value=\""+cat.id+"\">"+cat.name+"</option>"
				);
			}
		}
	});
}

function selectedCategoryChanged(){
	var selectElement = document.getElementById("selectCategoryForAdId");
	var selectedItem = selectElement.options[selectElement.selectedIndex].value;
	selectedCategory=selectedItem;
	console.log(selectedCategory+"hohoh");
}

//document.getElementById("adImage").onchange = function(event) {
//	console.log('jou');
//    var reader = new FileReader();
//    reader.readAsDataURL(event.srcElement.files[0]);
//    var me = this;
//    reader.onload = function () {
//      image = reader.result;
//	  
//    }
//}

function adImageSelected(){
	console.log('jou');
  var reader = new FileReader();
  reader.readAsDataURL($('#adImage')[0].files[0]);
  var me = this;
  reader.onload = function () {
    image = reader.result;
//	  
//  }
}
}

function addAd(){
	$("#editAdButton").hide();

	
	console.log('hmm');
	let d = new Date($('#addAdDate').val());
	let day=d.getDate();
	let month=d.getMonth()+1;
	let year=d.getFullYear();
	let expirationDate=day+"."+month+"."+year+".";
	
	
	let name=$("#addAdName")[0].value;
	let price=$("#addAdPrice")[0].value;
	let description=$("#addAdDescription")[0].value;
	let city=$("#addAdCity")[0].value;
	

	let validation=true;
	
	if(name){
		addAdName.style.borderColor= "white";
	}else{
		validation=false;
	    addAdName.style.borderColor= "red";
	}
	
	if(price){
		addAdPrice.style.borderColor= "white";
	}else{
		validation=false;
	    addAdPrice.style.borderColor= "red";
	}
	
//	let reg=/[a-zA-Z][a-zA-Z]-[0-9]+/;
//	let numbers = /^[0-9]+$/;
    if(description)
    {
    	addAdDescription.style.borderColor= "white";
    }else{
    	validation=false;
    	addAdDescription.style.borderColor= "red";
    }
    
    if(city)
    {
    	addAdCity.style.borderColor= "white";
    }else{
    	validation=false;
    	addAdCity.style.borderColor= "red";
    }
    
    
//    var fd = new FormData();
//    var files = $('#adImageSelection')[0].files[0];
//    fd.append('file',files);
//    console.log($('#adImageSelection')[0].files[0]);
      
    console.log('kategorija'+selectedCategory);
	if(validation){
		let a=JSON.stringify({name,price,description,expirationDate,city,image});
		console.log(a);
		$.ajax({
			url:"rest/ads/addAd/"+selectedCategory,
			type: "POST",
			data:a,
			contentType: "application/json",
			success: function(newAd){
				if(newAd===null){
					alert('Neuspesno dodavanje oglasa');
					return;
				}
				$("#addAdName").val="";
				$("#addAdPrice").val=0;
				$("#addAdDescription").val="";
				$("#addAdCity")[0].val="";
				$('#addAdForm').hide();
				$("#editAdButton").show();

			}
		
		});
	
	}
}

//function imageSelected(event){
//	console.log('usa');
//	let a=$(event).val();
//	console.log(a);
//	//image=event.target.files[0];
////    console.log(event.val());
//
//}}

function showAdsByCategoryId(id){
	$("#registrationForm").hide();
	$("#allUsersDiv").hide();
	$("#addCategoryForm").hide();
	$("#allCategoriesDiv").hide();
	$("#addAdForm").hide();
	$("#adsShowingDiv").show();
	$("#adsShowingDiv2").show();
	$("#newMessageForm").hide();
	$("#messagesDiv").hide();
	$("#messagesDiv1").hide();
	$("#adDetailsDiv").hide();
	$("#newRecensionForm").hide();
	$("#recensionsDiv").hide();
	$("#recensionsDiv1").hide();
	$("#findUserForm").hide();
	$("#findAdForm").hide();
		
	let urlGetAds="/rest/categories/categoryAds/"+id;
	$.ajax({
		url:"rest/categories/categoryAds/"+id,
		type:"GET",
		contentType: "application/json",
		success:function(ads){
			let i;
			$('#adsShowingDiv2').empty();
			console.log(ads.length);
			
			for(i=0; i<ads.length; i++){
				var ad=ads[i];
				let rbr=i+1;
				let up;
				let imageShow=ad.image;
				console.log(imageShow);
				$('#adsShowingDiv2').append("<div style=\"margin-top:9px;\" class=\"col-sm-6 col-md-4 col-lg-3 mt-4\">"+
											"<div class=\"card card-inverse card-info\">"+
											"<img class=\"card-img-top\" src=\""+imageShow+"\">"+
											"<div class=\"card-block\">"+
                    "<h4 class=\"card-title\">"+ad.name+"</h4>"+
                    "<div class=\"card-text\">"+
                      ad.description+
                    "</div>"+
                "</div>"+
                "<div class=\"card-footer\">"+
                    "<small>Postavljen:"+ad.date+"</small>"+
                    "<button style=\"margin-left:9px;\" onClick=\"adDetails("+ad.id+")\" class=\"btn btn-info float-right btn-sm\">Detaljnije</button>"+
                "</div>"+
            "</div>"+
        "</div");		
			
			}
		}
	});

}

function adDetails(id){

	$("#registrationForm").hide();
	$("#allUsersDiv").hide();
	$("#addCategoryForm").hide();
	$("#allCategoriesDiv").hide();
	$("#addAdForm").hide();
	$("#adsShowingDiv").hide();
	$("#adsShowingDiv2").hide();
	$("#newMessageForm").hide();
	$("#messagesDiv").hide();
	$("#messagesDiv1").hide();
	$("#adDetailsDiv").show();
	$("#newRecensionForm").hide();
	$("#recensionsDiv").hide();
	$("#recensionsDiv1").hide();
	$("#findUserForm").hide();
	$("#findAdForm").hide();
	$.ajax({
		url:"rest/users/userOfAd/"+id,
		type:"GET",
		contentType: "application/json",
		success:function(user){
			$.ajax({
				url:"rest/ads/getAd/"+id,
				type:"GET",
				contentType: "application/json",
				success:function(adi){
					ad=adi;
					
					
					$('#adDetailsDiv').empty();
					
					$('#adDetailsDiv').append("<form method=\"post\">"+
												"<div class=\"row\">"+
												"<div class=\"col-md-4\">"+
												"<div class=\"profile-img\">"+
												"<img src=\""+ad.image+"\" alt=\"\"/>"+
												"</div>"+
												"</div>"+
												"<div class=\"col-md-6\">"+
												"<div class=\"profile-head\">"+
												"<div class=\"row\">"+
				                                "<div class=\"col-md-6\">"+
				                                "<label>"+'Naziv oglasa'+":</label>"+
				                                "</div>"+
				                                "<div class=\"col-md-6\">"+
				                                "<p>"+ad.name+"</p>"+
				                                "</div>"+
				                                "</div>"+
				                                "<div class=\"row\">"+
				                                "<div class=\"col-md-6\">"+
				                                "<label>"+'Cena'+":</label>"+
				                                "</div>"+
				                                "<div class=\"col-md-6\">"+
				                                "<p>"+ad.price+"</p>"+
				                                "</div>"+
				                                "</div>"+
				                                "<div class=\"row\">"+
				                                "<div class=\"col-md-6\">"+
				                                "<label>"+'Mesto'+":</label>"+
				                                "</div>"+
				                                "<div class=\"col-md-6\">"+
				                                "<p>"+ad.city+"</p>"+
				                                "</div>"+
				                                "</div>"+
				                                "<div class=\"row\">"+
				                                "<div class=\"col-md-6\">"+
				                                "<label>"+'Datum postavljanja:'+":</label>"+
				                                "</div>"+
				                                "<div class=\"col-md-6\">"+
				                                "<p>"+ad.date+"</p>"+
				                                "</div>"+
				                                "</div>"+
				                                "<div class=\"row\">"+
				                                "<div class=\"col-md-6\">"+
				                                "<label>"+'Datum isteka:'+":</label>"+
				                                "</div>"+
				                                "<div class=\"col-md-6\">"+
				                                "<p>"+ad.expirationDate+"</p>"+
				                                "</div>"+
				                                "</div>"+
				                                "<div class=\"row\">"+
				                                "<div class=\"col-md-6\">"+
				                                "<label>"+'Opis:'+":</label>"+
				                                "</div>"+
				                                "<div class=\"col-md-6\">"+
				                                "<p>"+ad.description+"</p>"+
				                                "</div>"+
				                                "</div>"+
				                                "<div class=\"row\">"+
				                                "<div class=\"col-md-6\">"+
				                                "<label>"+'Oglašicač:'+":</label>"+
				                                "</div>"+
				                                "<div class=\"col-md-6\">"+
				                                "<p>"+user.username+"</p>"+
				                                "</div>"+
				                                "</div>"+
				                                "</div>"+
				                                "</div>"+
				                                "<div class=\"col-md-2\">"+
				"<button style=\"display:none;\" id=\"orderAdButton\" type=\"button\" onClick=\"orderAd("+ad.id+")\" class=\"btn btn-primary btn-lg btn-block login-button\">Poruči</button>"+
				"<button style=\"display:none;\" id=\"removeAdButton\" type=\"button\" onClick=\"removeAdId("+ad.id+")\" class=\"btn btn-primary btn-lg btn-block login-button\">Obriši</button>"+
				"<button style=\"display:none;\" id=\"editAdDetailButton\" type=\"button\" onClick=\"editAdClick('"+ad.id+","+ad.image+"')\" class=\"btn btn-primary btn-lg btn-block login-button\">Izmeni</button>"+
				"<button style=\"display:none;\" id=\"likeAdButton\" type=\"button\" onClick=\"likeAd("+ad.id+")\" class=\"btn btn-primary btn-lg btn-block login-button\">Like proizvod</button>"+
				"<button style=\"display:none;\" id=\"dislikeAdButton\" type=\"button\" onClick=\"dislikeAd("+ad.id+")\" class=\"btn btn-primary btn-lg btn-block login-button\">Dislike proizvod</button>"+
				"<button style=\"display:none;\" id=\"addRecensionAdButton\" type=\"button\" onClick=\"addRecensionAdClick("+ad.id+")\" class=\"btn btn-primary btn-lg btn-block login-button\">"+'Ostavi recenziju'+"</button>"+
				"<button style=\"display:none;\" id=\"likeSellerAdButton\" type=\"button\" onClick=\"likeSellerAd("+ad.id+")\" class=\"btn btn-primary btn-lg btn-block login-button\">Like prodavac</button>"+
				"<button style=\"display:none;\" id=\"dislikeSellerAdButton\" type=\"button\" onClick=\"dislikeSellerAd("+ad.id+")\" class=\"btn btn-primary btn-lg btn-block login-button\">Dislike prodavac</button>"+
				"<button style=\"display:none;\" id=\"addSellerRecensionAdButton\" type=\"button\" onClick=\"addSellerRecensionAdClick("+ad.id+")\" class=\"btn btn-primary btn-lg btn-block login-button\">"+'Recenzija prodavca'+"</button>"+
				"<button style=\"display:none;\" id=\"reportAdButton\" type=\"button\" onClick=\"reportAd("+ad.id+")\" class=\"btn btn-primary btn-lg btn-block login-button\">Prijavi</button>"+
				"<button style=\"display:none;\" id=\"addToFavouritesButton\" type=\"button\" onClick=\"addToFavourites("+ad.id+")\" class=\"btn btn-primary btn-lg btn-block login-button\">Dodaj u omiljenje</button>"+
				"<button style=\"display:none;\" id=\"realizeAdButton\" type=\"button\" onClick=\"realizeAd("+ad.id+")\" class=\"btn btn-primary btn-lg btn-block login-button\">Dostavljen</button>"+
				       "</div>"+
				       "</div>"+
				       "</form>");
				
				
				
				$.ajax({
					url: "rest/auth/getLogged",
					type: "GET",
					contentType: "application/json",
					success: function(logged) {
						if(logged != null){
							let rola=logged.role;
							let i;
							let isko=false;
							let favs=logged.favouriteAds
							for(i=0;i<favs.length;i++){
								if(favs[i]==ad.id){
									isko=true;
								}
							}
							
							if(!isko){
								$("#addToFavouritesButton").show();
							}else{
								$("#addToFavouritesButton").hide();
							}

							if(rola==="kupac"){
								if(ad.status==="aktivan"){
									$('#orderAdButton').show();
								}else if(ad.status==="u realizaciji"){
									$("#addToFavouritesButton").hide();
									$('#realizeAdButton').show();
								}else if(ad.status==="dostavljeno"){
									$('#likeAdButton').show();
									$('#likeSellerAdButton').show();
									$('#dislikeAdButton').show();
									$('#dislikeSellerAdButton').show();

									$('#reportAdButton').show();
									$('#addRecensionAdButton').show();
									$('#addSellerRecensionAdButton').show();
									$("#addToFavouritesButton").hide();

								}
							
							
							}else{
								$("#addToFavouritesButton").hide();
								$('#removeAdButton').show();
								$('#editAdDetailButton').show();
							}
							
							if(ad.reported){
								$('#reportAdButton').hide();
							}
							
							if(ad.commented){
								$('#addRecensionAdButton').hide();

							}
							
							if(ad.rated){
								$('#likeAdButton').hide();
								$('#dislikeAdButton').hide();
							}
							
							if(ad.sellerRated){
								$('#likeSellerAdButton').hide();
								$('#dislikeSellerAdButton').hide();

							}
							
							if(ad.sellerCommented){
								$('#addSellerRecensionAdButton').hide();

							}
							
						}
						
					}
				})
				
				}	
			});
		}	
	});
	

	let ad=adDetails;
	
}

function editAdClick(parameters){
	let niz=parameters.split(",");
	let id=niz[0];
	image=parameters.split(/,(.+)/)[1]
	$("#registrationForm").hide();
	$("#allUsersDiv").hide();
	$("#addCategoryForm").hide();
	$("#allCategoriesDiv").hide();
	$("#addAdForm").show();
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
	
	$("#addAdButton").hide();
	$("#editAdButton").show();
	$("#catOfAdDiv").hide();
	fillForAdForm(id);
	editAdId=id;
}

function fillForAdForm(id){
	

	$.ajax({
		url:"rest/ads/getAd/"+id,
		type:"GET",
		contentType: "application/json",
		success:function(ad){
			$("#addAdName").val(ad.name);
			$("#addAdPrice").val(ad.price);
			$("#addAdDescription").val(ad.description);
			$("#addAdCity").val(ad.city);
			var parts =ad.expirationDate.split('.');
			let month=Number(parts[1])+1;
			let date3=new Date(parts[2],parts[1],parts[0])
			$("#addAdDate").val(parts[2]+"-"+parts[1]+"-"+parts[0]);
		
		}
	});	
	
	console.log(image);
}

function editAd(){
	
	let d = new Date($('#addAdDate').val());
	let day=d.getDate();
	let month=d.getMonth()+1;
	let year=d.getFullYear();
	let expirationDate=day+"."+month+"."+year+".";
	
	
	let name=$("#addAdName")[0].value;
	let price=$("#addAdPrice")[0].value;
	let description=$("#addAdDescription")[0].value;
	let city=$("#addAdCity")[0].value;
	

	let validation=true;
	
	if(name){
		addAdName.style.borderColor= "white";
	}else{
		validation=false;
	    addAdName.style.borderColor= "red";
	}
	
	if(price){
		addAdPrice.style.borderColor= "white";
	}else{
		validation=false;
	    addAdPrice.style.borderColor= "red";
	}

    if(description)
    {
    	addAdDescription.style.borderColor= "white";
    }else{
    	validation=false;
    	addAdDescription.style.borderColor= "red";
    }
    
    if(city)
    {
    	addAdCity.style.borderColor= "white";
    }else{
    	validation=false;
    	addAdCity.style.borderColor= "red";
    }
    let id=editAdId;
    console.log('slikica');
    console.log(image);
	if(validation){
		let a=JSON.stringify({id,name,price,description,expirationDate,city,image});
		console.log(a);
		$.ajax({
			url:"rest/ads/editAd",
			type: "PUT",
			data:a,
			contentType: "application/json",
			success: function(newAd){
				if(newAd===null){
					alert('Nije mogućе izmeniti oglas');
					return;
				}
				$('#addAdForm').hide();
				$('catOfAdDiv').show();
				$("#addAdButton").show();
			}
		
		});
	
	}
}

function removeAdId(id){
	console.log('hop');
	let urldel="/rest/ads/removeAd/"+id;
	$.ajax({
		url:"rest/ads/test/"+id,
		type: "GET",
		contentType: "application/json",
		success: function(removedAd){
			if(removedAd==null){
				alert('Nije moguce obrisati oglas');
				return;
			}
			window.location.replace("index.html");	

		}
	
	});
}
