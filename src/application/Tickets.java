package application;

import java.util.ArrayList;

public class Tickets {
	private static ArrayList<Ticket> tickets = new ArrayList<Ticket>();
	public static void add(String movieID, String date, String time, String seats) {
		Ticket newTicket = new Ticket(movieID, date, time, seats);
		tickets.add(newTicket);
	}
	
	public static boolean has(String movieID, String date, String time, String seatId) {
		for (int i = 0; i < tickets.size(); i ++) {
			Ticket ithTicket = tickets.get(i);
			if (ithTicket.movieID.equals(movieID) && ithTicket.date.equalsIgnoreCase(date) && ithTicket.time.equalsIgnoreCase(time) && ithTicket.seats.contains(seatId)) {
				return true;
			}
		}
		
		return false;
	}
}
