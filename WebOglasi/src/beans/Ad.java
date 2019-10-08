package beans;

import java.util.ArrayList;
import java.util.List;

public class Ad {
	
	private int id;
	private String name;
	private float price;
	private String description;
	private int likes=0;
	private int dislikes=0;
	private String image="";
	private int favourite;
	private String date;
	private String expirationDate;
	private boolean active=true;
	private boolean removed=false;
	private String city;
	private String status="aktivan"; //aktivan/u realizaciji/dostavljeno
	private String publisher;
	private boolean reported;
	private boolean rated;
	private boolean commented;
	private boolean sellerRated;
	private boolean sellerCommented;
	private List<Integer> recensions=new ArrayList<>();
	
	public Ad() {
		
	}
	
	

	public boolean isRated() {
		return rated;
	}

	

	public boolean isSellerRated() {
		return sellerRated;
	}



	public void setSellerRated(boolean sellerRated) {
		this.sellerRated = sellerRated;
	}



	public boolean isSellerCommented() {
		return sellerCommented;
	}



	public void setSellerCommented(boolean sellerCommented) {
		this.sellerCommented = sellerCommented;
	}



	public void setRated(boolean rated) {
		this.rated = rated;
	}



	public boolean isCommented() {
		return commented;
	}



	public void setCommented(boolean commented) {
		this.commented = commented;
	}



	public boolean isReported() {
		return reported;
	}



	public void setReported(boolean reported) {
		this.reported = reported;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Integer> getRecensions() {
		return recensions;
	}

	public void setRecensions(List<Integer> recensions) {
		this.recensions = recensions;
	}

	public int getFavourite() {
		return favourite;
	}

	public void setFavourite(int favourite) {
		this.favourite = favourite;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	

}
