package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.Movie;
import application.Schedule;
import application.Utils;
import components.MovieItem;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class HomeController {
	@FXML
	private GridPane mainContainer;
	
	@FXML
	private Button homeSceneButton;
	
	@FXML
	private Button adminSceneButton;
	
	@FXML
	private Button refundSceneButton;
	
	@FXML
	private GridPane movieListNS;
	
	@FXML
	private ScrollPane scrollPaneNS;
	
	@FXML
	private GridPane movieListCS;
	
	@FXML
	private ScrollPane scrollPaneCS;
	
	@FXML
	private TextField movieSearch;
	
	ArrayList<MovieItem> movieItems = new ArrayList<MovieItem>();

	@FXML
	public void filterMovies(KeyEvent e) {
		String entry = movieSearch.getText();
		Pattern pattern = Utils.getKeywords(entry);
			
		for(int i = 0; i < movieItems.size(); i++) {
			MovieItem ithMovie = movieItems.get(i);
			String formattedTitle = ithMovie.movie.title.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
			Matcher matcher = pattern.matcher(formattedTitle);
			if(matcher.find()) {
				ithMovie.show();
			} else {
				ithMovie.hide();
			}
		}
	}
	
	private MovieItem addMovie(String posterURL, String title, String premiereDate, double price) {
		Movie movie = new Movie(posterURL, title, premiereDate, price);
		
		MovieItem movieItem = new MovieItem(movie);
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date now = new Date();
			//Add to "now showing" list if the premiere date has passed.
			if(dateFormat.parse(premiereDate).before(now)) {
				movieItem.addToGridPane(movieListNS);
			} else {
				//...if not, add to "coming soon".
				movieItem.addToGridPane(movieListCS);
				movieItem.button.setDisable(true);
				movieItem.button.setText(premiereDate);
			}
			movieItems.add(movieItem);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return movieItem;
	}
	
	@FXML
	public void initialize() {
		movieListNS.getColumnConstraints().get(0).setPrefWidth(0);
		movieListNS.getColumnConstraints().get(0).setMinWidth(0);
		movieListCS.getColumnConstraints().get(0).setPrefWidth(0);
		movieListCS.getColumnConstraints().get(0).setMinWidth(0);

		MovieItem bp = addMovie("./resources/posters/1.jpg", "Black Panther", "09/20/2021", 350.75);
		Schedule sched1 = new Schedule("09/21/2021");
		sched1.availableTimes.add("9:30 AM");
		sched1.availableTimes.add("12:00 PM");
		sched1.availableTimes.add("2:30 PM");
		sched1.availableTimes.add("5:00 PM");
		sched1.availableTimes.add("7:30 PM");
		bp.movie.schedules.add(sched1);
		Schedule sched2 = new Schedule("09/22/2021");
		sched2.availableTimes.add("10:30 AM");
		sched2.availableTimes.add("1:00 PM");
		sched2.availableTimes.add("3:30 PM");
		sched2.availableTimes.add("6:00 PM");
		sched2.availableTimes.add("8:30 PM");
		bp.movie.schedules.add(sched2);
		MovieItem bd = addMovie("./resources/posters/2.jpg", "Baby Driver", "08/14/2022", 405.50);
		Schedule sched3 = new Schedule("08/14/2021");
		sched3.availableTimes.add("10:30 AM");
		sched3.availableTimes.add("1:00 PM");
		sched3.availableTimes.add("3:30 PM");
		sched3.availableTimes.add("6:00 PM");
		sched3.availableTimes.add("8:30 PM");
		bd.movie.schedules.add(sched3);
		Schedule sched4 = new Schedule("08/15/2021");
		sched4.availableTimes.add("10:30 AM");
		sched4.availableTimes.add("1:00 PM");
		sched4.availableTimes.add("3:30 PM");
		sched4.availableTimes.add("6:00 PM");
		sched4.availableTimes.add("8:30 PM");
		bd.movie.schedules.add(sched4);
		MovieItem cm = addMovie("./resources/posters/3.jpg", "Captain Marvel", "09/18/2022", 300);
		MovieItem smh = addMovie("./resources/posters/4.jpg", "Spider-Man - Homecoming", "06/14/2022", 375);
		MovieItem ml = addMovie("./resources/posters/5.jpg", "Moonlight", "07/13/2022", 420);
		MovieItem tr = addMovie("./resources/posters/6.jpg", "Thor - Ragnarok", "08/06/2022", 275);
		MovieItem br = addMovie("./resources/posters/7.jpg", "Bohemian Rhapsody", "06/05/2023", 325);
		MovieItem smits = addMovie("./resources/posters/8.jpg", "Spider-Man - Into the Spiderverse", "05/14/2022", 300);
		MovieItem tb = addMovie("./resources/posters/9.jpg", "The Batman", "05/27/2023", 450);
	}
}
