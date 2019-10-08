package beans;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	//univerzalno za sve korisnike
	private int id;
	private String username;
	private String password;
	private String name;
	private String surname;
	private String role; //admin/kupac/prodavac
	private String phone;
	private String city;
	private String email;
	private String date;
	
	
	//ZA KUPCA
	private List<Integer> orderedAds=new ArrayList<>();
	private List<Integer> deliveredAds=new ArrayList<>();
	private List<Integer> favouriteAds=new ArrayList<>();

//	private List<Ad> orderedAds=new ArrayList<>();
//	private List<Ad> deliveredAds=new ArrayList<>();
//	private List<Ad> favouriteAds=new ArrayList<>();
	
	//ZA PRODAVCA
	private List<Integer> publishedAds=new ArrayList<>();
	private List<Integer> realizedAds=new ArrayList<>();
//	private List<Ad> publishedAds=new ArrayList<>();
//	private List<Ad> realizedAds=new ArrayList<>();
	private boolean active;
	private int reports=0;
	private int likes=0;
	private int dislikes=0;
	
	
	private List<Message> sentMessages=new ArrayList<>();
	private List<Message> inboxMessages=new ArrayList<>();
	
	public User() {
		this.username="";
		this.password="";
		this.name="";
		this.surname="";
		this.role="buyer";
		this.phone="";
		this.city="";
		this.email="";
		this.active=true;
		this.reports=0;
		this.date="";
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.name="";
		this.surname="";
		this.role="buyer";
		this.phone="";
		this.city="";
		this.email="";
		this.active=true;
		this.reports=0;
		this.date="";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean isActive) {
		this.active = isActive;
	}

	public int getReports() {
		return reports;
	}

	public void setReports(int reports) {
		this.reports = reports;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}


	public List<Integer> getOrderedAds() {
		return orderedAds;
	}

	public void setOrderedAds(List<Integer> orderedAds) {
		this.orderedAds = orderedAds;
	}

	public List<Integer> getDeliveredAds() {
		return deliveredAds;
	}

	public void setDeliveredAds(List<Integer> deliveredAds) {
		this.deliveredAds = deliveredAds;
	}

	public List<Integer> getFavouriteAds() {
		return favouriteAds;
	}

	public void setFavouriteAds(List<Integer> favouriteAds) {
		this.favouriteAds = favouriteAds;
	}

	public List<Integer> getPublishedAds() {
		return publishedAds;
	}

	public void setPublishedAds(List<Integer> publishedAds) {
		this.publishedAds = publishedAds;
	}

	public List<Integer> getRealizedAds() {
		return realizedAds;
	}

	public void setRealizedAds(List<Integer> realizedAds) {
		this.realizedAds = realizedAds;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getDislikes() {
		return dislikes;
	}

	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}

	public List<Message> getSentMessages() {
		return sentMessages;
	}

	public void setSentMessages(List<Message> sendMessages) {
		this.sentMessages = sendMessages;
	}

	public List<Message> getInboxMessages() {
		return inboxMessages;
	}

	public void setInboxMessages(List<Message> inboxMessages) {
		this.inboxMessages = inboxMessages;
	}
	
	
	
	
	
	
	

}
