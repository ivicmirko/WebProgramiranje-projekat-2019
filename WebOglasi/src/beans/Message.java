package beans;

public class Message {
	
	private int id;
	private String adName;
	private String senderName;
	private String receiverName;
	private String title;
	private String text;
	private String date;
	private boolean senderRemoved=false;
	private boolean receiverRemoved=false;
	
	public Message() {
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getAdName() {
		return adName;
	}


	public void setAdName(String adName) {
		this.adName = adName;
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


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public boolean isSenderRemoved() {
		return senderRemoved;
	}


	public void setSenderRemoved(boolean senderRemoved) {
		this.senderRemoved = senderRemoved;
	}


	public boolean isReceiverRemoved() {
		return receiverRemoved;
	}


	public void setReceiverRemoved(boolean receiverRemoved) {
		this.receiverRemoved = receiverRemoved;
	}


	public String getSenderName() {
		return senderName;
	}


	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}


	public String getReceiverName() {
		return receiverName;
	}


	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	
	
	

}
