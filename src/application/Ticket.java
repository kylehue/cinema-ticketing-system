package application;

import java.util.ArrayList;

public class Ticket {
	public String movieID, date, time;
	public ArrayList<String> seats = new ArrayList<String>();
	public Ticket(String movieID, String date, String time, String seats) {
		this.movieID = movieID;
		this.date = date;
		this.time = time;

		String[] seatsArray = seats.split(", ");
		for(int i = 0; i < seatsArray.length; i++) {
			this.seats.add(seatsArray[i]);
		}
	}
}
