package dao;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xml.internal.serializer.utils.Messages;

import beans.Ad;
import beans.Category;
import beans.Message;
import beans.User;

public class MessageDAO {
	
	private HashMap<Integer, Message> messages=new HashMap<>();
	

	public HashMap<Integer, Message> getMessages() {
		return messages;
	}

	public void setMessages(HashMap<Integer, Message> messages) {
		this.messages = messages;
	}

	public MessageDAO() {
		
	}
	
	public MessageDAO(String contextPath) {
		loadMessages(contextPath);
	}
	
	public void loadMessages(String contextPath){
		try {
			File file=new File(contextPath+ "/jsonFiles/messages.json");
			ObjectMapper objectMapper=new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			Message[] msgArray=objectMapper.readValue(file, Message[].class);
			
			for(Message msg:msgArray) {
				messages.put(msg.getId(), msg);
			}
			System.out.println("Poruke "+messages.size());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			
		}
	}
	
	public void saveMessages(String contextPath) {
		
		try {
			//File file=new File(contextPath+"/jsonFiles/vozila.json");
			ObjectMapper objectMapper=new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			ArrayList<Message> msgArray=new ArrayList<>();
			
			for(Message msg: messages.values()) {
				msgArray.add(msg);
			}
			

			objectMapper.writeValue(new File(contextPath+"/jsonFiles/messages.json"), msgArray);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public Message addMessage(Message msg, String contextPath) {
		
		
		int id=generateNewId();
		msg.setSenderRemoved(false);
		msg.setReceiverRemoved(false);
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
		String date=formatter.format(new Date());
		msg.setDate(date);
		msg.setId(id);
		messages.put(msg.getId(), msg);
		saveMessages(contextPath);
		return msg;
		
	}
	
	public void updateMessage(Message msg, String contextPath) {
		messages.replace(msg.getId(),msg);
		saveMessages(contextPath);
	}
	
	public void removeMessage(int id,User user, String contextPath) {
		Message msg=messages.get(id);
		if(msg.getSenderName().equals(user.getUsername())) {
			msg.setSenderRemoved(true);
		}else {
			msg.setReceiverRemoved(true);
		}
		
		messages.replace(msg.getId(), msg);
		saveMessages(contextPath);
	}
	
	public List<Message> getMySentMesssages(String name){
		
		List<Message> retVal=new ArrayList<>();
		List<Message> allMesages=new ArrayList<>();
		for(Message m:messages.values()) {
			allMesages.add(m);
		}
		
		Collections.reverse(allMesages);
		for(Message msg:allMesages) {
			if(msg.getSenderName().equals(name) && !msg.isSenderRemoved()) {
				retVal.add(msg);
			}
		}
		
		return retVal;
	}
	
public List<Message> getMyInboxMesssages(String name){

	List<Message> retVal=new ArrayList<>();
	List<Message> allMesages=new ArrayList<>();
	for(Message m:messages.values()) {
		allMesages.add(m);
	}
	
		Collections.reverse(allMesages);
		for(Message msg:allMesages) {
			if(msg.getReceiverName().equals(name) && !msg.isReceiverRemoved()) {
				retVal.add(msg);
			}
		}
		
		return retVal;
}

	
	public int generateNewId() {
		int id=1;
		for(Message m:messages.values()) {
			if(m.getId()==id) {
				id++;
			}
		}
		return id;
	}
	
	public void removeAdBySeller(Ad ad, User user, String contextPath) {
		int id=generateNewId();
		Message msg=new Message();
		msg.setId(id);
		msg.setAdName(ad.getName());
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
		String date=formatter.format(new Date());
		msg.setDate(date);
		msg.setReceiverName("admin");
		msg.setSenderName(user.getUsername());
		msg.setReceiverRemoved(false);
		msg.setSenderRemoved(false);
		msg.setTitle("Obaveštenje o brisnaju oglasa");
		msg.setText("Oglas id="+ad.getId()+" naziva="+ad.getName()+" je obrisan od strane korinsika "+user.getUsername());
		messages.put(msg.getId(), msg);
		saveMessages(contextPath);

	}
	
	public void removeActiveAdByAdmin(Ad ad,User user,String contextPath) {
		int id=generateNewId();
		Message msg=new Message();
		msg.setId(id);
		msg.setAdName(ad.getName());
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
		String date=formatter.format(new Date());
		msg.setDate(date);
		msg.setReceiverName(ad.getPublisher());
		msg.setSenderName(user.getUsername());
		msg.setReceiverRemoved(false);
		msg.setSenderRemoved(false);
		msg.setTitle("Obaveštenje o brisnaju oglasa");
		msg.setText("Oglas id="+ad.getId()+" naziva="+ad.getName()+" je obrisan od strane administratora "+user.getUsername());
		messages.put(msg.getId(), msg);
		saveMessages(contextPath);

	}
	
	public void removeUnActiveAdByAdmin(Ad ad,User user,String contextPath,String buyer) {
		int id=generateNewId();
		Message msg=new Message();
		msg.setId(id);
		msg.setAdName(ad.getName());
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
		String date=formatter.format(new Date());
		msg.setDate(date);
		msg.setReceiverName(buyer);
		msg.setSenderName(user.getUsername());
		msg.setReceiverRemoved(false);
		msg.setSenderRemoved(false);
		msg.setTitle("Obaveštenje o brisnaju oglasa");
		msg.setText("Oglas id="+ad.getId()+" naziva="+ad.getName()+" je obrisan od strane administratora "+user.getUsername());
		messages.put(msg.getId(), msg);
		saveMessages(contextPath);

	}
	
	public void editActiveAdByAdmin(Ad ad,User user,String contextPath) {
		int id=generateNewId();
		Message msg=new Message();
		msg.setId(id);
		msg.setAdName(ad.getName());
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
		String date=formatter.format(new Date());
		msg.setDate(date);
		msg.setReceiverName(ad.getPublisher());
		msg.setSenderName(user.getUsername());
		msg.setReceiverRemoved(false);
		msg.setSenderRemoved(false);
		msg.setTitle("Obaveštenje o izmeni oglasa");
		msg.setText("Oglas id="+ad.getId()+" naziva="+ad.getName()+" je izmenjen od strane administratora "+user.getUsername());
		messages.put(msg.getId(), msg);
		saveMessages(contextPath);
	}
	
	public void editUnActiveAdByAdmin(Ad ad,User user,String contextPath,String buyer) {
		int id=generateNewId();
		Message msg=new Message();
		msg.setId(id);
		msg.setAdName(ad.getName());
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
		String date=formatter.format(new Date());
		msg.setDate(date);
		msg.setReceiverName(buyer);
		msg.setSenderName(user.getUsername());
		msg.setReceiverRemoved(false);
		msg.setSenderRemoved(false);
		msg.setTitle("Obaveštenje o izmeni oglasa");
		msg.setText("Oglas id="+ad.getId()+" naziva="+ad.getName()+" je izmenjen od strane administratora "+user.getUsername());
		messages.put(msg.getId(), msg);
		saveMessages(contextPath);

	}
	
	public void reportMessage(Ad ad,User user,String contextPath) {
		int id=generateNewId();
		Message msg=new Message();
		msg.setId(id);
		msg.setAdName(ad.getName());
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
		String date=formatter.format(new Date());
		msg.setDate(date);
		msg.setReceiverName(user.getUsername());
		msg.setSenderName("admin");
		msg.setReceiverRemoved(false);
		msg.setSenderRemoved(false);
		msg.setTitle("Obaveštenje o prijavi neispravnosti");
		msg.setText("Oglas id="+ad.getId()+" naziva="+ad.getName()+" je prijavljen kao prevara/neispravan od strane kupca!!! ");
		messages.put(msg.getId(), msg);
		saveMessages(contextPath);
		
	}
	
	public void addRecensionMessage(Ad ad,String user,String contextPath) {
		int id=generateNewId();
		Message msg=new Message();
		msg.setId(id);
		msg.setAdName(ad.getName());
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
		String date=formatter.format(new Date());
		msg.setDate(date);
		msg.setReceiverName(ad.getPublisher());
		msg.setSenderName(user);
		msg.setReceiverRemoved(false);
		msg.setSenderRemoved(false);
		msg.setTitle("Obaveštenje o novoj recenziji");
		msg.setText("Oglas id="+ad.getId()+" naziva="+ad.getName()+" je komentarisan od strane kupca:"+user);
		messages.put(msg.getId(), msg);
		saveMessages(contextPath);
	}
	
	public void ediRecensionMessage(Ad ad,String user,String contextPath) {
		int id=generateNewId();
		Message msg=new Message();
		msg.setId(id);
		msg.setAdName(ad.getName());
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
		String date=formatter.format(new Date());
		msg.setDate(date);
		msg.setReceiverName(ad.getPublisher());
		msg.setSenderName(user);
		msg.setReceiverRemoved(false);
		msg.setSenderRemoved(false);
		msg.setTitle("Obaveštenje o izmeni recenziji");
		msg.setText("Oglas id="+ad.getId()+" naziva="+ad.getName()+" je komentarisan od strane kupca:"+user);
		messages.put(msg.getId(), msg);
		saveMessages(contextPath);
	}
}
