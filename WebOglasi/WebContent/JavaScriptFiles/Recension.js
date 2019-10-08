/**
 * 
 */
var imageR="";
var adIdd=0;
var recId=0;
function userRecensions(){
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
	$("#adDetailsDiv").hide();
	$("#newRecensionForm").hide();
	$("#recensionsDiv").show();
	$("#recensionsDiv1").show();
	$("#findUserForm").hide();
	$("#findAdForm").hide();
	
	$.ajax({
		url:"rest/recensions/getUserRecensions",
		type:"GET",
		contentType: "application/json",
		success:function(rec){
			let i;
			document.getElementById("recensionsDiv1").innerHTML = "";

			
			for(i=0; i<rec.length; i++){
				var r=rec[i];
				let rbr=i+1;
				let up;
				let imageShow=r.image;
				$('#recensionsDiv1').append("<div style=\"margin-top:9px;\" class=\"col-sm-6 col-md-4 col-lg-3 mt-4\">"+
											"<div class=\"card card-inverse card-info\">"+
											"<img class=\"card-img-top\" src=\""+imageShow+"\">"+
											"<div class=\"card-block\">"+
					"<h4 class=\"card-title\">Naslov: "+r.title+"</h4>"+
                    "<h3 class=\"card-title\">Oglas: "+r.adName+"</h3>"+
                    "<h2 class=\"card-title\">Autor: "+r.author+"</h2>"+
                    "<p  class=\"card-title\">Opis tacan "+r.trueDescription+"</p>"+
                    "<p  class=\"card-title\">Prodavac korektan "+r.dealRespect+"</p>"+
                    "<h1 class=\"card-title\">"+r.message+"</h1>"+
                    "<div class=\"card-text\">"+r.text+
                    "</div>"+
                "</div>"+
                "<div class=\"card-footer\">"+
                    "<button style=\"margin-left:9px;\" onClick=\"editRecensionClick('"+r.id+","+r.image+"')\" class=\"btn btn-info float-right btn-sm\">Izmeni</button>"+
                    "<button style=\"margin-left:9px;\" onClick=\"removeRecension("+r.id+")\" class=\"btn btn-info float-right btn-sm\">Obriši</button>"+
                "</div>"+
            "</div>"+
        "</div");		

			
			
			}
		}
	});

}

function sellerRecension(){
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
	$("#adDetailsDiv").hide();
	$("#newRecensionForm").hide();
	$("#recensionsDiv").show();
	$("#recensionsDiv1").show();
	$("#findUserForm").hide();
	$("#findAdForm").hide();
	
	$.ajax({
		url:"rest/recensions/getSellerRecensions",
		type:"GET",
		contentType: "application/json",
		success:function(rec){
			let i;
			document.getElementById("recensionsDiv1").innerHTML = "";

			console.log(rec);
			for(i=0; i<rec.length; i++){
				var r=rec[i];
				let rbr=i+1;
				let up;
				let imageShow=r.image;
				$('#recensionsDiv1').append("<div style=\"margin-top:9px;\" class=\"col-sm-6 col-md-4 col-lg-3 mt-4\">"+
											"<div class=\"card card-inverse card-info\">"+
											"<img class=\"card-img-top\" src=\""+imageShow+"\">"+
											"<div class=\"card-block\">"+
					"<h4 class=\"card-title\">Naslov: "+r.title+"</h4>"+
                    "<h3 class=\"card-title\">Oglas: "+r.adName+"</h3>"+
                    "<h2 class=\"card-title\">Autor :"+r.author+"</h2>"+
                    "<p  class=\"card-title\">Opis tacan "+r.trueDescription+"</p>"+
                    "<p  class=\"card-title\">Prodavac korektan "+r.dealRespect+"</p>"+
                    "<h1 class=\"card-title\">"+r.message+"</h1>"+
                    "<div class=\"card-text\">"+r.text+
                    "</div>"+
                "</div>"+
                "<div class=\"card-footer\">"+
               "</div>"+
            "</div>"+
        "</div");		
			
			}
		}
	});

}



function addRecensionAdClick(id){
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
	$("#adDetailsDiv").hide();
	$("#newRecensionForm").show();
	$("#recensionsDiv").hide();
	$("#recensionsDiv1").hide();
	$("#findUserForm").hide();
	$("#findAdForm").hide();
	
	$("#addRecensionButton").show();
	$("#editRecensionButton").hide();
	$("#sellerRecensionButton").hide();
	adIdd=id;
}

function recensionImageSelected(){
  var reader = new FileReader();
  reader.readAsDataURL($('#newRecensionImage')[0].files[0]);
  var me = this;
  reader.onload = function () {
    imageR = reader.result;
//	  
//  }
}
}


function addSellerRecensionAdClick(id){
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
	$("#adDetailsDiv").hide();
	$("#newRecensionForm").show();
	$("#recensionsDiv").hide();
	$("#recensionsDiv1").hide();
	$("#findUserForm").hide();
	$("#findAdForm").hide();
	
	$("#addRecensionButton").hide();
	$("#editRecensionButton").hide();
	$("#sellerRecensionButton").show();
	adIdd=id;
}

function addSellerRecension(){

	
	let adId=adIdd;
	let title=$("#newRecensionTitle")[0].value;
	let text=$("#newRecensionText")[0].value;
	let trueDescription=$("#newRecensionCorrect").is(':checked');
	

	let dealRespect=$("#newRecensionRespect").is(':checked');


	let validation=true;
	
	if(title){
		newRecensionTitle.style.borderColor= "white";
	}else{
		validation=false;
	    newRecensionTitle.style.borderColor= "red";
	}
	
	if(text){
		newRecensionText.style.borderColor= "white";
	}else{
		validation=false;
		newRecensionText.style.borderColor= "red";
	}
	let image=imageR;
    console.log('recenzija');
	if(validation){
		let r=JSON.stringify({adId,title,text,trueDescription,dealRespect,image});
		console.log(r);
		$.ajax({
			url:"rest/recensions/newSellerRecension",
			type: "POST",
			data:r,
			contentType: "application/json",
			success: function(newRec){
				if(newRec===null){
					alert('neuspesno dodavanje recenizje');
					return;
				}
				window.location.replace("index.html");


			}
		
		});
	
	}
}

function addRecension(){

		
	let adId=adIdd;
	let title=$("#newRecensionTitle")[0].value;
	let text=$("#newRecensionText")[0].value;
	let trueDescription=$("#newRecensionCorrect").is(':checked');
	

	let dealRespect=$("#newRecensionRespect").is(':checked');


	let validation=true;
	
	if(title){
		newRecensionTitle.style.borderColor= "white";
	}else{
		validation=false;
	    newRecensionTitle.style.borderColor= "red";
	}
	
	if(text){
		newRecensionText.style.borderColor= "white";
	}else{
		validation=false;
		newRecensionText.style.borderColor= "red";
	}
	let image=imageR;
    console.log('recenzija');
	if(validation){
		let r=JSON.stringify({adId,title,text,trueDescription,dealRespect,image});
		console.log(r);
		$.ajax({
			url:"rest/recensions/newRecension",
			type: "POST",
			data:r,
			contentType: "application/json",
			success: function(newRec){
				if(newRec===null){
					alert('neuspesno dodavanje recenizje');
					return;
				}
				window.location.replace("index.html");


			}
		
		});
	
	}
}

function editRecensionClick(parameters){
	let niz=parameters.split(",");
	let id=niz[0];
	imageR=parameters.split(/,(.+)/)[1]
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
	$("#adDetailsDiv").hide();
	$("#newRecensionForm").show();
	$("#recensionsDiv").hide();
	$("#recensionsDiv1").hide();
	$("#findUserForm").hide();
	$("#findAdForm").hide();

	$("#addRecensionButton").hide();
	$("#editRecensionButton").show();
	$("#sellerRecensionButton").hide();
	fillForRecensionForm(id);
	recId=id;
}

function fillForRecensionForm(id){
	

	$.ajax({
		url:"rest/recensions/getRecension/"+id,
		type:"GET",
		contentType: "application/json",
		success:function(r){
			$("#newRecensionTitle").val(r.title);
			$("#newRecensionText").val(r.text);
			if(r.dealRespect!=true){
				$('#newRecensionRespect').removeAttr('checked');
			}
//			$('#idCheckbox').attr('checked', true);
//			$('#idCheckbox').removeAttr('checked');
			if(r.trueDescription!=true){
				console.log('ipak');
				$('#newRecensionCorrect').removeAttr('checked');
			}
			
		}
	});	
	
	console.log(image);
}

function editRecension(){
	
	$("#editRecensionButton").hide();

	
	let title=$("#newRecensionTitle")[0].value;
	let text=$("#newRecensionText")[0].value;
	let trueDescription=$("#newRecensionCorrect").is(':checked');
	

	let dealRespect=$("#newRecensionRespect").is(':checked');


	let validation=true;
	
	if(title){
		newRecensionTitle.style.borderColor= "white";
	}else{
		validation=false;
	    newRecensionTitle.style.borderColor= "red";
	}
	
	if(text){
		newRecensionText.style.borderColor= "white";
	}else{
		validation=false;
		newRecensionText.style.borderColor= "red";
	}
	let image=imageR;
	let id=recId;
    console.log('recenzija');
	if(validation){
		let r=JSON.stringify({id,title,text,trueDescription,dealRespect,image});
		console.log(r);

		$.ajax({
			url:"rest/recensions/editRecension",
			type: "POST",
			data:r,
			contentType: "application/json",
			success: function(newRec){
				if(newRec===null){
					alert('neuspesno dodavanje recenizje');
					return;
				}
				userRecensions();
				

			}
		
		});
	
	}
}

function removeRecension(id){
	console.log('hoprec');
	$.ajax({
		url:"rest/recensions/removeRecension/"+id,
		type: "GET",
		contentType: "application/json",
		success: function(removedRec){
			if(removedRec===null){
				alert('Nije obrisati oglas');
				userRecensions();
				return;
			}
			window.location.replace("index.html");

		}
	
	});
}


function likeAd(id){
	$.ajax({
		url:"rest/recensions/likeAd/"+id,
		type:"GET",
		contentType:"application/json",
		success(){
			$("#likeSellerAdButton").hide();
			$("#dislikeSellerAdButton").hide();
		}
	});
}

function dislikeAd(id){
	$.ajax({
		url:"rest/recensions/dislikeAd/"+id,
		type:"GET",
		contentType:"application/json",
		success(){
			$("#likeAdButton").hide();
			$("#dislikeAdButton").hide();
		}
	});
}

function likeSellerAd(id){
	$.ajax({
		url:"rest/recensions/likeSeller/"+id,
		type:"GET",
		contentType:"application/json",
		success(){
			$("#likeAdButton").hide();
			$("#dislikeAdButton").hide();
		}
	});
}

function dislikeSellerAd(id){
	$.ajax({
		url:"rest/recensions/dislikeSeller/"+id,
		type:"GET",
		contentType:"application/json",
		success(){
			$("#likeSellerAdButton").hide();
			$("#dislikeSellerAdButton").hide();
		}
	});
}