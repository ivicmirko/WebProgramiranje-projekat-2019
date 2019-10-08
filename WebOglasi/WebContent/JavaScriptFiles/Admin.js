/**
 * 
 */

function getAllUsers(){
	
	$.ajax({
		url:"rest/auth/getAllUsers",
		type:"GET",
		contentType: "application/json",
		success: function(users) {
			$("#registrationForm").hide();
			$("#allUsersDiv").show();
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
			$("#findUserForm").hide();
			$("#findAdForm").hide();
			
			let i;
			$('#allUsersTable tbody').empty();
			
			
			for(i=0; i<users.length; i++){
				let user=users[i];
				let rbr=i+1;
				$('#allUsersTable tbody').append("<tr><th scope=\"row\">"+rbr+"</th>"+
				"<td>"+user.username+"</td><td>"+user.name+"</td><td>"+user.surname+"</td><td>"+user.email+"</td><td>"
				+user.phone+"</td><td>"+user.city+"</td><td><select style=\"height: 30px;\" id=\""+user.id+"\"><option>admin</option>"+
				"<option>kupac</option><option>prodavac</option></select></td>" +
				"<td><button id=\"btnActivate"+user.id+"\" type=\"button\" onclick=\"activateUser('"+user.id+"')\" class=\"btn btn-primary \">Demarkiraj</button></td>"+
				"<td><button type=\"button\" onclick=\"changeUserRole('"+user.id+"')\" class=\"btn btn-primary \">Izmeni</button></td></tr>"		
				);
				
				if(user.active){
					$("#btnActivate"+user.id).hide();
				}
				
				
				$("#"+user.id).val(user.role);
			}
		}
	})
}

function activateUser(id){
	let urlUnmark="rest/auth/unmarkUser/"+id;
	$.ajax({
		url:urlUnmark,
		type:"GET",
		contentType: "application/json",
		success: function(user) {
			if(user !=null){
				getAllUsers();
			}else{
				alert("Ne mozete menjati svoju ulogu");
			}
			
		}
	})
}

function changeUserRole(id){

	let role=$("#"+id).val();
	
	$.ajax({
		url:"rest/auth/changeRole",
		type:"POST",
		data: JSON.stringify({id,role}),
		dataType: "json",
		contentType: "application/json",
		success: function(user) {
			getAllUsers();
		},

		statusCode: {
			401: function() {
				alert("Nije moguće promeniti ulogu korisnika");
				//$('#error').text("Greska pri unosu, korisnicko ime vec postoji!");
				//$("#error").show().delay(5000).fadeOut();
			}
		}
	})
}