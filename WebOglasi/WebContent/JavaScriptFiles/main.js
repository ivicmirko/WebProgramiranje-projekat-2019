/**
 * 
 */

$(document).ready(()=> {
	//ucitajKorisnike();
	getCategoriesForNav();
	
	$.ajax({
		url: "rest/auth/getLogged",
		type: "GET",
		contentType: "application/json",
		success: function(user) {
			
			if(user != null){
				adaptToUser(user);
				
			}
			
			$.ajax({
				url:"rest/ads/getTopTen",
				type:"GET",
				contentType:"application/json",
				success(ads){
					$('#adsShowingDiv').show();
					$('#adsShowingDiv2').show();
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
	})
});

function getAllsers(){
	//alert(window.location.pathname);
	$.ajax({
		url: "rest/auth/getAllUsers",
		type: "GET",
		contentType: 'application/json',
		success: function(user){
		}
	})
}

function nekoUlogovan(){
	
	
}

function login(){
	let checkForm=true;
	
	if(!$("#emaillog")[0].value){

		emaillog.style.borderColor= "red";	
		checkForm=false;
	}else{
		emaillog.style.borderColor= "white";
	}
	
	if(!$("#passwordlog")[0].value){

		passwordlog.style.borderColor= "red";	
		checkForm=false;
	}else{
		passwordlog.style.borderColor= "white";
	}
	
	if(checkForm){
		let username=emaillog.value;
		let password=passwordlog.value;
		
		let u=JSON.stringify({username,password});
		
		$.ajax({
			url: "rest/auth/login",
			type: "POST",
			data: u,
			contentType: 'application/json',
			dataType: "json",
			success: function(user){
				if(user != null){
					
					emaillog.value="";
					passwordlog.value="";	
					
					adaptToUser(user);
				}else{
					alert("Pogrešno korisničko ime ili lozinka");
				}
				
			}
		})
		
	}else{
		alert("Popunite polja za prijavu!")
	}
	
}

function adaptToUser(u){
	$("#signin").hide();
	$("#nalog")[0].style.display = "list-item";
	$("#loggoutButton")[0].style.display="list-item";
	$("#newMessageli")[0].style.display="list-item";

	$('#registrationForm').hide();
	let child = document.createElement('p');
	child.innerHTML =" "+u.username;
	child = child.firstChild;
	$("#txtnalog")[0].appendChild(child);
	console.log(u);
	
	let uloga=u.role
	
	if(u.role=="kupac"){
		//$("#favouriteAdsli")[0].style.display="list-item";
		$("#messagesli")[0].style.display="list-item";
		$("#buyerAdsNav")[0].style.display="list-item";
		$('#userRecensionsli')[0].style.display="list-item";


	} 
	
	if(u.role==="admin"){
		$('#allAdsAdminli')[0].style.display="list-item";
		$("#categoriesli")[0].style.display="list-item";
		$("#usersli")[0].style.display="list-item";
		$("#messagesli")[0].style.display="list-item";
	}
	
	if(uloga==="prodavac"){
		console.log(u.role);
		console.log('muu');
		
		$('#addAdli')[0].style.display="list-item";
//		$("#publishedAdsli")[0].style.display="list-item";
//		$("#realizedAdsli")[0].style.display="list-item";
		$("#sellerLikesLi")
		let child1 = document.createElement('p');
		child1.innerHTML =" "+u.likes;
		child1 = child1.firstChild;
		$("#likeLi")[0].appendChild(child1);
		
		let child2 = document.createElement('p');
		child2.innerHTML =" "+u.dislikes;
		child2 = child2.firstChild;
		$("#dislikeLi")[0].appendChild(child2);

		$('#sellerRecensionsli')[0].style.display="list-item";
		$("#messagesli")[0].style.display="list-item";
		$("#sellerAdsNav")[0].style.display="list-item";
		$("#sellerlikesLi")[0].style.display="list-item";
		$("#sellerDislikesLi")[0].style.display="list-item";
		
		
	}
	
	if(!u.active){
		$('#addAdli').hide();
	}
	
	 
}

function homePageClick(){
	window.location.replace("index.html");

	//$('#registracijaforma').hide();
	//$('#top10jela').show();
	//$('#top10pica').show();
}

function registrationClick(){
	$("#registrationForm").show();
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
	$("#addCategoryForm").hide();
}

function logout(){
	$.ajax({
			url:"rest/auth/logout",
			type:"POST",
			contentType:'application/json',
			success: function(){
				window.location.replace("index.html");
			},
			error : function(){
				alert("Neuspesna odjava!")
			}
		})
	
}


function getCategoriesForNav(){
	
	$.ajax({
		url:"rest/categories/getAllCategories",
		type:"GET",
		contentType: "application/json",
		success: function(categories) {
			
			
			let i;
			$('#categoriesUl').empty();

			
			for(i=0; i<categories.length; i++){
				let cat=categories[i];
				let rbr=i+1;
				let up;
					
				$('#categoriesUl').append("<li><a onclick=\"showAdsByCategoryId("+categories[i].id+")\" >"+categories[i].name+"</a></li>");		
			
			}
		}
	});
}

function showAdsByCategoryId(id){
	$("#registrationForm").hide();
	$("#allUsersDiv").hide();
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
	$("#addCategoryForm").hide();
	
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
				let ad=ads[i];
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
                    "<button style=\"margin-left:9px;\ onClick=\"adDetails("+ad.id+")\" class=\"btn btn-info float-right btn-sm\">Detaljnije</button>"+
                "</div>"+
            "</div>"+
        "</div");		
			
			}
		}
	});

}

function adminShowAllAds(){
	$("#registrationForm").hide();
	$("#allUsersDiv").hide();
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
	$("#addCategoryForm").hide();
	
	document.getElementById("adsShowingDiv2").innerHTML = "";
	$.ajax({
		url:"rest/ads/getAllAds",
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