package ar.edu.itba.it.paw.model;

public class Comment {

	String text;
	int rating;
	
	public Comment(String text, int rating) {
		this.text = text;
		this.rating = rating;
	}
	
	public String getText() {
		return text;
	}
	
	public int getRating() {
		return rating;
	}
}
