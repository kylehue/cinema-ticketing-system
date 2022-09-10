package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Movie {
	public String id;
	public String title;
	public String premiereDate;
	public String posterURL;
	public double price;
	public ArrayList<Schedule> schedules = new ArrayList<Schedule>(); 
	public Movie(String title, String premiereDate, double price, String posterURL) {
		this.id = Utils.createUID();
		this.posterURL = posterURL;
		this.title = title;
		this.premiereDate = premiereDate;
		this.price = price;
		
	}
}
