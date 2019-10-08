package dao;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Message;
import beans.Recension;
import beans.User;

public class RecensionDAO {
	
	private HashMap<Integer, Recension> recensions=new HashMap<>();
	
	public RecensionDAO() {
		
	}
	
	public RecensionDAO(String contextPath) {
		loadRecensions(contextPath);
	}

	public HashMap<Integer, Recension> getRecensions() {
		return recensions;
	}

	public void setRecensions(HashMap<Integer, Recension> recensions) {
		this.recensions = recensions;
	}
	
	public void loadRecensions(String contextPath){
		try {
			File file=new File(contextPath+ "/jsonFiles/recensions.json");
			ObjectMapper objectMapper=new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			Recension[] recArray=objectMapper.readValue(file, Recension[].class);
			
			for(Recension rec:recArray) {
				recensions.put(rec.getId(), rec);
			}
			System.out.println("Recenzije "+recensions.size());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			
		}
	}
	
	public void saveRecensions(String contextPath) {
		
		try {
			//File file=new File(contextPath+"/jsonFiles/vozila.json");
			ObjectMapper objectMapper=new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			ArrayList<Recension> recArray=new ArrayList<>();
			
			for(Recension rec: recensions.values()) {
				recArray.add(rec);
			}
			

			objectMapper.writeValue(new File(contextPath+"/jsonFiles/recensions.json"), recArray);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public Recension addRecension(Recension rec, String contextPath) {
		
		
		int id=generateNewId();
		rec.setId(id);
		rec.setRemoved(false);
		recensions.put(rec.getId(), rec);
		saveRecensions(contextPath);
		return rec;
		
	}
	
	public Recension updateRecension(Recension rec, String contextPath) {
		Recension old=recensions.get(rec.getId());
		rec.setAdRecension(old.isAdRecension());
		rec.setAdName(old.getAdName());
		rec.setAdId(old.getAdId());
		rec.setAuthor(old.getAuthor());
		rec.setRemoved(old.isRemoved());
		rec.setSellerId(rec.getSellerId());
		recensions.replace(rec.getId(), rec);
		System.out.println("usao");
		saveRecensions(contextPath);
		
		return rec;
	}
	
	public void removeRecension(int id, String contextPath) {
		Recension rec=recensions.get(id);
		rec.setRemoved(true);
		recensions.replace(rec.getId(), rec);
		saveRecensions(contextPath);
	}
	
	public List<Recension> getMyRecensions(String buyer){
		List<Recension> retVal=new ArrayList<>();
		List<Recension> allRec=new ArrayList<>();
		for(Recension r:recensions.values()) {
			allRec.add(r);
		}
		
			Collections.reverse(allRec);
			for(Recension rec:allRec) {
				if(!rec.isRemoved() && rec.getAuthor().equals(buyer)) {
					retVal.add(rec);
				}
			}
			
			return retVal;
	}
	
	public List<Recension> getSellerRecensions(int id){
		List<Recension> retVal=new ArrayList<>();
		List<Recension> allRec=new ArrayList<>();
		for(Recension r:recensions.values()) {
			allRec.add(r);
		}
		
			Collections.reverse(allRec);
			for(Recension rec:allRec) {
				if(!rec.isRemoved() && (rec.getSellerId()==id)) {
					retVal.add(rec);
				}
			}
			
			return retVal;
	}
	
	public List<Recension> getAdRecensions(int id){
		List<Recension> retVal=new ArrayList<>();
		List<Recension> allRec=new ArrayList<>();
		for(Recension r:recensions.values()) {
			allRec.add(r);
		}
		
			Collections.reverse(allRec);
			for(Recension rec:allRec) {
				if(!rec.isRemoved() && (rec.getAdId()==id) && rec.isAdRecension()) {
					retVal.add(rec);
				}
			}
			
			return retVal;
	}
	

	
	public int generateNewId() {
		int id=1;
		for(Recension r:recensions.values()) {
			if(r.getId()==id) {
				id++;
			}
		}
		return id;
	}

}
