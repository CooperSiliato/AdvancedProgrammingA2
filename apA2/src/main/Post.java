package main;

public class Post {

	private int id;
    private String content;
    private String author;
    private int likes;
    private int shares;
    private String dateTime;
    private String owner;

    public Post(int id, String content, String author, int postLikes, int postShares, String dateTime, String owner) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.likes = postLikes;
        this.shares = postShares;
        this.dateTime = dateTime;
        this.owner = owner;
    }


   //Getters and Setters

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    
    public String getOwner() {
    	return owner;
    }
    
    public void setOwner(String owner) {
    	this.owner = owner;
    }

}
