package dao;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.bcel.internal.generic.NEW;

import beans.Ad;
import beans.User;

public class UserDAO {
	
private HashMap<Integer, User> users=new HashMap<>();
	
	public HashMap<Integer, User> getUsers() {
		return users;
	}

	public void setUsers(HashMap<Integer, User> users) {
		this.users = users;
	}

	public UserDAO() {
		
	}
	
	public  UserDAO(String contextPath) {

		loadUsers(contextPath);
	}
	
	public void loadUsers(String contextPath) {
	System.out.println("ucitavanjee: "+contextPath);

		try {
			File file=new File(contextPath+ "/jsonFiles/users.json");
			ObjectMapper objectMapper=new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			
			User[] usersA=objectMapper.readValue(file, User[].class);
			
			for(User u:usersA) {
				users.put(u.getId(), u);
			}
			System.out.println(usersA.length+"a=velicina");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			
		}
	}
	
	public User checkLogin(String username,String password) {
		
		for(User u:users.values()) {
			if(u.getUsername().equals(username)) {
				if(u.getPassword().equals(password)) {
					return u;
				}
			}
		}
		
		return null;
		
	}
	
	public User findByUsername(String username) {
		for(User user:users.values()) {
			if(user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}
	
	public List<User> getAllSellers(){
		List<User> retVal=new ArrayList<>();
		for(User u:users.values()) {
			if(u.getRole().equals("prodavac") && u.getActive()) {
				retVal.add(u);
			}
		}
		return retVal;
	}
	
	public List<User> getAllUsers(User user){
		List<User> retVal=new ArrayList<>();
		for(User u:users.values()) {
			if(!user.getUsername().equals(u.getUsername())) {
				retVal.add(u);
			}
		}
		return retVal;
	}
	
	public List<User> getAllAdmins(){
		List<User> retVal=new ArrayList<>();
		for(User u:users.values()) {
			if(u.getActive() && u.getRole().equals("admin")) {
				retVal.add(u);
			}
		}
		return retVal;
	}
	
	public boolean checkUserName(String username) {
		
		for(User u:users.values()) {
			if(u.getUsername().equals(username)) {
				return true;
			}
		}
		
		return false;

	}
	
	public void addUser(User user,String contextPath) {
		int id=generateNewId();
		user.setId(id);
		user.setActive(true);
		user.setReports(0);
		user.setRole("kupac");
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		String date=formatter.format(new Date());
		user.setDate(date);
		users.put(id, user);
		saveUsers(contextPath);
	}
	
	public int generateNewId() {
		int id=1;
		for(User u:users.values()) {
			if(u.getId()==id) {
				id++;
			}
		}
		return id;
	}
	
	public User editUser(User u, String contextPath) {
		User oldUser=users.get(u.getId());
		oldUser.setRole(u.getRole());
		users.replace(u.getId(), oldUser);
		saveUsers(contextPath);
		return oldUser;
		
	}
	
	public User unmarkUser(int id, String contextPath) {
		User user=users.get(id);
		user.setActive(true);
		user.setReports(0);
		users.replace(id, user);
		saveUsers(contextPath);
		return user;
		
	}
	
	public void saveUsers(String contextPath) {
		System.out.println("kthhh  "+contextPath);
		try {
			File file=new File(contextPath+"/jsonFiles/users.json");
			ObjectMapper objectMapper=new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			ArrayList<User> usersArray=new ArrayList<>();
			
			for(User u: users.values()) {
				usersArray.add(u);
			}
			
			System.out.println("aaaaa"+usersArray.size());
			objectMapper.writeValue(new File(contextPath+"/jsonFiles/users.json"), usersArray);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void addPublishedAd(String contextPath,int  adId,int userId) {
		User user=users.get(userId);
		user.getPublishedAds().add(adId);
		users.replace(user.getId(), user);
		
		saveUsers(contextPath);
	}
	
	public List<Integer> getPublicshedAds(int userId){
		User user=users.get(userId);
		List<Integer> ads=new ArrayList<>();
		
		for(int a:user.getPublishedAds()) {
				ads.add(a);
		}
		return ads;
	}
	
//	public List<Ad> getRealizedAds(int id){
//		User user=users.get(id);
//		List<Ad> ads=new ArrayList<>();
//		
//		for(Ad a:user.getRealizedAds()) {
//			if(!a.isRemoved() && a.getStatus().equals("dostavljeno")) {
//				ads.add(a);
//			}
//		}
//		return ads;	}
//	
//	public List<Ad> getDeliveredAds(int id){
//		User user=users.get(id);
//		List<Ad> ads=new ArrayList<>();
//		
//		for(Ad a:user.getDeliveredAds()) {
//			if(!a.isRemoved()  && a.getStatus().equals("dostavljeno")) {
//				ads.add(a);
//			}
//		}
//		return ads;
//	}
//	public List<Ad> getOrderedAds(int id){
//		User user=users.get(id);
//		List<Ad> ads=new ArrayList<>();
//		
//		for(Ad a:user.getOrderedAds() ) {
//			if(!a.isRemoved()  && a.getStatus().equals("u realizaciji")) {
//				ads.add(a);
//			}
//		}
//		return ads;
//	}
	public List<Integer> getFavouriteAds(int userId){
		User user=users.get(userId);
		List<Integer> ads=new ArrayList<>();
		
		for(int a:user.getFavouriteAds()) {
			ads.add(a);
		}
		return ads;
	}
	
	public List<Integer> getActiveAds(int userId){
		User user=users.get(userId);

		return user.getPublishedAds();
	}
	
//	public List<Ad> getInRealizationAds(int id){
//		User user=users.get(id);
//		List<Ad> ads=new ArrayList<>();
//		
//		for(Ad a:user.getPublishedAds()) {
//			if(a.getStatus().equals("u realizaciji") && !a.isRemoved()) {
//				ads.add(a);
//			}
//		}
//		return ads;
//	}
	
	

}
