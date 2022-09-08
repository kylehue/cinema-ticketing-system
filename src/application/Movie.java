package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Movie {
	public String title;
	public String premiereDate;
	public String posterURL;
	public double price;
	public ArrayList<Schedule> schedules = new ArrayList<Schedule>(); 
	public Movie(String posterURL, String title, String premiereDate, double price) {
		this.posterURL = posterURL;
		this.title = title;
		this.premiereDate = premiereDate;
		this.price = price;
		
	}
}
