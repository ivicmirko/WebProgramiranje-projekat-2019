package beans;

public class Recension {
	
	private int id;
	private int adId;
	private String adName;
	private int sellerId;
	private String author; //username of author
	private String title;
	private String text;
	private String image;
	private boolean trueDescription;
	private boolean dealRespect;
	private boolean removed;
	private boolean adRecension;
	private String message;
	
	public Recension() {
		
	}
	
	
	
	
	public String getMessage() {
		return message;
	}




	public void setMessage(String message) {
		this.message = message;
	}




	public boolean isAdRecension() {
		return adRecension;
	}




	public void setAdRecension(boolean adRecension) {
		this.adRecension = adRecension;
	}




	public String getAdName() {
		return adName;
	}



	public void setAdName(String adName) {
		this.adName = adName;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAdId() {
		return adId;
	}

	public void setAdId(int adId) {
		this.adId = adId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isTrueDescription() {
		return trueDescription;
	}

	public void setTrueDescription(boolean trueDescription) {
		this.trueDescription = trueDescription;
	}

	public boolean isDealRespect() {
		return dealRespect;
	}

	public void setDealRespect(boolean dealRespect) {
		this.dealRespect = dealRespect;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	
	

}
