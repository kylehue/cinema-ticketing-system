package application;

import java.util.ArrayList;

public class Schedule {
	public String dateText;
	public ArrayList<String> availableTimes = new ArrayList<String>();
	public Schedule(String dateText) {
		this.dateText = dateText;
	}
}
