/**
 * 
 */

function showMyActiveAds(){
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
	$('#adsShowingDiv2').empty();

	$.ajax({
		url:"rest/users/getMyActiveAds",
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
                    "<button style=\"margin-left:9px;\" onClick=\"adDetails("+ad.id+")\" class=\"btn btn-info float-right btn-sm\">Detaljnije</button>"+
                "</div>"+
            "</div>"+
        "</div");		
			
			}
		}
	});

}

function showMyInRealizationAds(){
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
	$('#adsShowingDiv2').empty();

	$.ajax({
		url:"rest/users/getMyInRealizationAds",
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
                    "<button style=\"margin-left:9px;\" onClick=\"adDetails("+ad.id+")\" class=\"btn btn-info float-right btn-sm\">Detaljnije</button>"+
                "</div>"+
            "</div>"+
        "</div");		
			
			}
		}
	});

}

function showMyRealizedAds(){
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
	$('#adsShowingDiv2').empty();

	$.ajax({
		url:"rest/users/getMyRealizedAds",
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
                    "<button style=\"margin-left:9px;\" onClick=\"adDetails("+ad.id+")\" class=\"btn btn-info float-right btn-sm\">Detaljnije</button>"+
                "</div>"+
            "</div>"+
        "</div");		
			
			}
		}
	});

}

function showMyOrderedAds(){
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
	$('#adsShowingDiv2').empty();

	$.ajax({
		url:"rest/users/getMyOrderedAds",
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
                    "<button style=\"margin-left:9px;\" onClick=\"adDetails("+ad.id+")\" class=\"btn btn-info float-right btn-sm\">Detaljnije</button>"+
                "</div>"+
            "</div>"+
        "</div");		
			
			}
		}
	});

}

function showMyDeliveredAds(){
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
	$('#adsShowingDiv2').empty();

	$.ajax({
		url:"rest/users/getMyDeliveredAds",
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
                    "<button style=\"margin-left:9px;\" onClick=\"adDetails("+ad.id+")\" class=\"btn btn-info float-right btn-sm\">Detaljnije</button>"+
                "</div>"+
            "</div>"+
        "</div");		
			
			}
		}
	});
}

function showMyFavouritesAds(){
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
	$('#adsShowingDiv2').show();
	//$('#adsShowingDiv2')

	$.ajax({
		url:"rest/users/getMyFavouriteAds",
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
                    "<button style=\"margin-left:9px;\" onClick=\"adDetails("+ad.id+")\" class=\"btn btn-info float-right btn-sm\">Detaljnije</button>"+
                "</div>"+
            "</div>"+
        "</div");		
			
			}
		}
	});
}

function myRecensionsShow(){
	console.log("recenzije");
}

function myInboxMessages(){
	
}

function mySentMessages(){
	
}