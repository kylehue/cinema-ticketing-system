package application;

import java.util.ArrayList;

public class Movie {
	public String title;
	public String premiereDate;
	public String posterURL;
	public double price;
	public ArrayList<String> availableDates = new ArrayList<String>(); 
	public Movie(String posterURL, String title, double price) {
		this.posterURL = posterURL;
		this.title = title;
		this.price = price;
	}
}
