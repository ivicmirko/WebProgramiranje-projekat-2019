/**
 * 
 */
var userId;
var responseReceiverName;
function newMessageShow(){
	$("#registrationForm").hide();
	$("#allUsersDiv").hide();
	$("#addCategoryForm").hide();
	$("#allCategoriesDiv").hide();
	$("#addAdForm").hide();
	$("#adsShowingDiv").hide();
	$("#adsShowingDiv2").hide();
	$("#newMessageForm").show();
	$("#messagesDiv").hide();
	$("#messagesDiv1").hide();
	$("#adDetailsDiv").hide();
	$("#newRecensionForm").hide();
	$("#recensionsDiv").hide();
	$("#recensionsDiv1").hide();
	$("#findUserForm").hide();
	$("#findAdForm").hide();

	$("#answerButton").hide();
	$("#editMessageButton").hide();
	$("#addMessageButton").show();


	
	$.ajax({
		url:"rest/auth/getUsersForMessage",
		type:"GET",
		contentType: "application/json",
		success: function(users) {
			
			let i;
			$('#selectReceiverForMessageId').empty();

			
			for(i=0; i<users.length; i++){
				let u=users[i];
				let rbr=i+1;
				let up;
					
				$('#selectReceiverForMessageId').append("<option value=\""+u.id+"\">"+u.username+"</option>"
				);
			}
			$('#selectReceiverForMessageId').val(users[0].id);
			userId=users[0].id;
			//$("#answerButton").show();

		}
	});
}

function selectedMessageReceiverChanged(){
	let selectElement = document.getElementById("selectReceiverForMessageId");
	let selectedItem = selectElement.options[selectElement.selectedIndex].value;
	userId=selectedItem;
	console.log(userId);
}

function sendMessage(){
	let adName=$("#nameAdNewMessage")[0].value;
	let title=$("#titleNewMessage")[0].value;
	let text=$("#textNewMessage")[0].value;
	

	let validation=true;
	
	if(adName){
		nameAdNewMessage.style.borderColor= "white";
	}else{
		validation=false;
	    nameAdNewMessage.style.borderColor= "red";
	}
	
	if(title){
		titleNewMessage.style.borderColor= "white";
	}else{
		validation=false;
	    titleNewMessage.style.borderColor= "red";
	}

    if(text)
    {
    	textNewMessage.style.borderColor= "white";
    }else{
    	validation=false;
    	textNewMessage.style.borderColor= "red";
    }
    
    

	if(validation){
		let m=JSON.stringify({adName,title,text});
		console.log(m);
		$.ajax({
			url:"rest/messages/newMessage/"+userId,
			type: "POST",
			data:m,
			contentType: "application/json",
			success: function(message){
				if(message===null){
					alert('Neuspesno slanje po');
					return;
				}
				$('#editMessageButton').show();
				$("#nameAdNewMessage").val("");
				$("#titleNewMessage").val("");
				$("#textNewMessage").val("");
				$('#newMessageForm').hide();
				myInboxMessages();
			},
			statusCode: {
				401: function(){
					alert("Neuspešno slanje poruke!");
				}
			}
		
		});
	
	}
}

function myInboxMessages(){
	$("#registrationForm").hide();
	$("#allUsersDiv").hide();
	$("#addCategoryForm").hide();
	$("#allCategoriesDiv").hide();
	$("#addAdForm").hide();
	$("#adsShowingDiv").hide();
	$("#adsShowingDiv2").hide();
	$("#newMessageForm").hide();
	$("#messagesDiv").show();
	$("#messagesDiv1").show();
	$("#adDetailsDiv").hide();
	$("#newRecensionForm").hide();
	$("#recensionsDiv").hide();
	$("#recensionsDiv1").hide();
	$("#findUserForm").hide();
	$("#findAdForm").hide();

	
	$.ajax({
		url:"rest/messages/getMyInboxMessages",
		type:"GET",
		contentType:"application/json",
		success:function(messages){
			let i;
			
			document.getElementById("messagesDiv1").innerHTML = "";
			 
			console.log(messages);
			for(i=0; i<messages.length; i++){
				let m=messages[i];
				let rbr=i+1;
				let up;
					
				$('#messagesDiv1').append("<div class=\"poruka\">"+
											"<div class=\"alert-message alert-message-success\">"+
											"<h4>"+m.adName+"   "+m.date+"</h4>"+
											"<h3>"+m.title+"</h3>"+
											"<p>"+m.senderName+"</p>"+
											"<p>"+m.text+"</p>"+
               "<button  id=\"addResponseButton\" type=\"button\" onClick=\"responseMessageClick('"+m.senderName+"')\" class=\"btn btn-primary\">Odgovori</button>"+
               "<button  id=\"editMessageButton\" type=\"button\" onClick=\"editMessage("+m.id+")\" class=\"btn btn-primary\">Izmeni</button>"+
               "<button  id=\"removeMessageButton\" type=\"button\" onClick=\"removeMessage("+m.id+")\" class=\"btn btn-primary\">Izbriši</button>"+                         
            "</div>"+
        "</div>"
				);
			}

		}
	});
}

function responseMessageClick(receiverName) {
	$('#primalacDiv').hide();
	$("#registrationForm").hide();
	$("#allUsersDiv").hide();
	$("#addCategoryForm").hide();
	$("#allCategoriesDiv").hide();
	$("#addAdForm").hide();
	$("#adsShowingDiv").hide();
	$("#adsShowingDiv2").hide();
	$("#newMessageForm").show();
	$("#messagesDiv").hide();
	$("#messagesDiv1").show();
	$("#adDetailsDiv").hide();
	$("#newRecensionForm").hide();
	$("#recensionsDiv").hide();
	$("#recensionsDiv1").hide();
	$("#findUserForm").hide();
	$("#findAdForm").hide();
	$('#addMessageButton').hide();
	$("#answerButton").show();
	$("#editMessageButton").hide();
	
	responseReceiverName=receiverName;
}

function responseMessage(){
	let adName=$("#nameAdNewMessage")[0].value;
	let title=$("#titleNewMessage")[0].value;
	let text=$("#textNewMessage")[0].value;
	

	let validation=true;
	
	if(adName){
		nameAdNewMessage.style.borderColor= "white";
	}else{
		validation=false;
	    nameAdNewMessage.style.borderColor= "red";
	}
	
	if(title){
		titleNewMessage.style.borderColor= "white";
	}else{
		validation=false;
	    titleNewMessage.style.borderColor= "red";
	}

    if(text)
    {
    	textNewMessage.style.borderColor= "white";
    }else{
    	validation=false;
    	textNewMessage.style.borderColor= "red";
    }
    
    
    let receiverName=responseReceiverName;
	if(validation){
		let m=JSON.stringify({adName,title,text,receiverName});
		console.log(m);
		$.ajax({
			url:"rest/messages/responseMessage",
			type: "POST",
			data:m,
			contentType: "application/json",
			success: function(message){
				if(message===null){
					alert('neuspesno slanje');
					return;
				}
				$('#editMessageButton').show();
				$("#nameAdNewMessage").val("");
				$("#titleNewMessage").val("");
				$("#textNewMessage").val("");
				$('#newMessageForm').hide();
				myInboxMessages();
			}
		
		});
	
	}
}

function mySentMessages(){

	$("#registrationForm").hide();
	$("#allUsersDiv").hide();
	$("#addCategoryForm").hide();
	$("#allCategoriesDiv").hide();
	$("#addAdForm").hide();
	$("#adsShowingDiv").hide();
	$("#adsShowingDiv2").hide();
	$("#newMessageForm").hide();
	$("#messagesDiv").show();
	$("#messagesDiv1").show();
	$("#adDetailsDiv").hide();
	$("#newRecensionForm").hide();
	$("#recensionsDiv").hide();
	$("#recensionsDiv1").hide();
	$("#findUserForm").hide();
	$("#findAdForm").hide();
	
	$.ajax({
		url:"rest/messages/getMySentMessages",
		type:"GET",
		contentType:"application/json",
		success:function(messages){
			let i;
//			$('#messageDiv1').innerHTML="";
			document.getElementById("messagesDiv1").innerHTML = "";

			console.log(messages);
			for(i=0; i<messages.length; i++){
				let m=messages[i];
				let rbr=i+1;
				let up;
					
				$('#messagesDiv1').append("<div class=\"poruka\">"+
											"<div class=\"alert-message alert-message-success\">"+
											"<h4>"+m.adName+"   "+m.date+"</h4>"+
											"<h3>"+m.title+"</h3>"+
											"<p>"+m.receiverName+"</p>"+
											"<p>"+m.text+"</p>"+
               "<button  id=\"editMessagePrevButton\" type=\"button\" onClick=\"editMessage("+m.id+")\" class=\"btn btn-primary\">Izmeni</button>"+
               "<button  id=\"removeMessageButton\" type=\"button\" onClick=\"removeMessage("+m.id+")\" class=\"btn btn-primary\">Izbriši</button>"+                         
            "</div>"+
        "</div>"
				);
				

			}

		}
	});
}

function removeMessage(id){
	$.ajax({
		url:"rest/messages/removeMessage/"+id,
		type:"PUT",
		contentType:"application/json",
		success:function(){
			myInboxMessages();
		},
		statusCode:{
			401:function(){
				alert("Neuspešno brisanje poruke!")
			}
		}
	})
}