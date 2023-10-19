package application;
// The post class
public class Post { 
	private int id;
	private String content;
	private String author;
	private int likes;
	private int shares;
	private String dateTime;
	// The constructor for the post class 
	public Post(int id, String content, String author, 
				int likes, int shares, String dateTime) {
	    this.id = id;
	    this.content = content;
	    this.author = author;
	    this.likes = likes;
	    this.shares = shares;
	    this.dateTime = dateTime;
	}
	// The getters for the id, content, author
    //, likes, shares, and dateTime 
	public int getId() {
		return this.id;
	}
	//
	public String getContent() {
		return this.content;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public int getLikes() {
		return this.likes;
	}
	
	public int getShares() {
		return this.shares;
	}
	
	public String getDateTime() {
		return this.dateTime;
	}
	
	// The setters for the id, content, author
    //, likes, shares, and dateTime
	@Override
	public String toString() {
	    return String.format("Post ID: %d\nContent: %s\nAuthor: %s\nLikes: %d\nShares: %d\nDate and Time: %s",
	            id, content, author, likes, shares, dateTime);
	}
}

