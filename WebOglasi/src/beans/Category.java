package beans;

import java.util.ArrayList;
import java.util.List;

public class Category {
	
	private int id;
	private String name;
	private String description;
	private List<Integer> ads=new ArrayList<>();
	private boolean removed;
	
	private Category() {
		
	}
	
	private Category(String name) {
		this.name=name;
		this.setRemoved(false);
		this.ads=new ArrayList<>();
	}
	
	private Category(String name, String description) {
		this.description=description;
		this.name=name;
		this.setRemoved(false);
		this.ads=new ArrayList<>();
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

	public List<Integer> getAds() {
		return ads;
	}

	public void setAds(List<Integer> ads) {
		this.ads = ads;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
	

}
