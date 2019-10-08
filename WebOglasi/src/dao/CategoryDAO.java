package dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Ad;
import beans.Category;
import beans.Message;
import beans.User;

public class CategoryDAO {
	
private HashMap<Integer, Category> categories=new HashMap<>();
	
	
	public HashMap<Integer, Category> getCategories() {
		return categories;
	}

	public void setCategories(HashMap<Integer, Category> categories) {
		this.categories = categories;
	}

	public CategoryDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public CategoryDAO(String contextPath) {
		loadCategories(contextPath);
	}
	
	public void loadCategories(String contextPath) {
		
		try {
			File file=new File(contextPath+ "/jsonFiles/categories.json");
			ObjectMapper objectMapper=new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			Category[] catArray=objectMapper.readValue(file, Category[].class);
			
			for(Category cat:catArray) {
				categories.put(cat.getId(), cat);
			}
			System.out.println("Vozila "+categories.size());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			
		}
	}
	
	public void saveCategories(String contextPath) {
		try {
			//File file=new File(contextPath+"/jsonFiles/vozila.json");
			ObjectMapper objectMapper=new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			ArrayList<Category> catArray=new ArrayList<>();
			
			for(Category cat: categories.values()) {
				catArray.add(cat);
			}
			

			objectMapper.writeValue(new File(contextPath+"/jsonFiles/categories.json"), catArray);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public Category addCategory(Category cat, String contextPath) {
		
		for(Category c:categories.values()) {
			if(c.getName().equals(cat.getName())) {
				return null;
			}
		}
		
		int id=generateNewId();
		cat.setRemoved(false);
		cat.setAds(new ArrayList<>());
		cat.setId(id);
		categories.put(cat.getId(), cat);
		saveCategories(contextPath);
		return cat;
		
	}
	
	public void updateCategory(Category cat, String contextPath) {
		categories.replace(cat.getId(),cat);
		saveCategories(contextPath);
	}
	
	public void removeCategory(int id, String contextPath) {
		Category cat=categories.get(id);
		cat.setRemoved(true);
		categories.put(id,cat);
		saveCategories(contextPath);
	}
	
	public Collection<Category> getAllCategories(){
		ArrayList<Category> retVal=new ArrayList<>();
		for(Category cat:categories.values()) {
			if(!cat.isRemoved()) {
				retVal.add(cat);
			}
		}
		
		return retVal;
	}
	
	public void addAdToCategory(String contextPath,int adId,int catId) {
		Category category=categories.get(catId);
		category.getAds().add(adId);
		saveCategories(contextPath);
	}
	
	public List<Integer> getCategoryAds(int catId){
		Category cat=categories.get(catId);
		List<Integer> ads=new ArrayList<>(); 
		for(int c:cat.getAds()) {
				ads.add(c);
		}
		return ads;
	}
	
	
	public int generateNewId() {
		int id=1;
		for(Category c:categories.values()) {
			if(c.getId()==id) {
				id++;
			}
		}
		return id;
	}

}
