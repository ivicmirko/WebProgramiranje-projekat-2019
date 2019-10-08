/**
 * 
 */

function addToFavourites(id){
	
	$.ajax({
		url:"rest/users/addToFavourites/"+id,
		type:"GET",
		contentType:"application/json",
		success(){
			$("#addToFavouritesButton").hide();
		}
	})
}

function orderAd(id){
	$.ajax({
		url:"rest/users/orderAd/"+id,
		type:"GET",
		contentType:"application/json",
		success(ad3){
			if(ad3===null){
				alert("trenutno nije moguce dodati oglas");
				
			}else{
				alert("Proizvod porucen");
				//window.location.replace("index.html");
				$('#orderAdButton').hide()
			}
		}
	})
}

function realizeAd(id){
	$.ajax({
		url:"rest/users/realizeAd/"+id,
		type:"GET",
		contentType:"application/json",
		success(ad){
			if(ad===null){
				alert('nesupesno');
			}else{
				$('#realizeAdButton').hide();
			}
		}
	});
}

function reportAd(id){
	$.ajax({
		url: "rest/users/reportSeller/"+id,
		type:"GET",
		contentType:"application/json",
		success(ad){
			console.log("reportovan");
			$('#reportAdButton').hide();
		}
	});
}