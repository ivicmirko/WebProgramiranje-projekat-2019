/**
 * 
 */

function showFindUser(){
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
	$("#recensionsDiv").hide();
	$("#recensionsDiv1").hide();
	$("#findUserForm").show();
	$("#findAdForm").hide();

}

function findUser(){
	let username=$("#findUsername")[0].value;
	let city=$("#findCity")[0].value;
	
	$.ajax({
		url:"/rest/find/user",
		type:"POST",
		contentType:"application/json",
		success(users){
			$('#findUserForm').hide();
			let i;
			document.getElementById("adsShowingDiv2").innerHTML = "";
			
			
			for(i=0; i<users.length; i++){
				var u=users[i];
				let rbr=i+1;
				let up;
				
				
				$('#adsShowingDiv2').append("<div style=\"margin-top:9px;\" class=\"col-sm-6 col-md-4 col-lg-3 mt-4\">"+
											"<div class=\"card card-inverse card-info\">"+
											
											"<div class=\"card-block\">"+
                    "<h4 class=\"card-title\">"+u.name+" "+u.surname+"</h4>"+
                    "<h4 class=\"card-title\">"+u.name+" "+u.surname+"</h4>"+
                    "<h4 class=\"card-title\">"+u.username+"</h4>"+
                    "<h4 class=\"card-title\">Like"+u.likes+"</h4>"+
                    "<h4 class=\"card-title\">Dislikes"+u.dislikes+"</h4>"+
                    "<div class=\"card-text\">"+
                      
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
