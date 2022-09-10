package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.Movie;
import application.Schedule;
import application.Utils;
import application.Voucher;
import application.Vouchers;
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
	
	private MovieItem addMovie(Movie movie) {
		MovieItem movieItem = new MovieItem(movie);
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date now = new Date();
			//Add to "now showing" list if the premiere date has passed.
			if(dateFormat.parse(movie.premiereDate).before(now)) {
				movieItem.addToGridPane(movieListNS);
			} else {
				//...if not, add to "coming soon".
				movieItem.addToGridPane(movieListCS);
				movieItem.button.setDisable(true);
				movieItem.button.setText(movie.premiereDate);
			}
			movieItems.add(movieItem);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return movieItem;
	}
	
	@FXML
	public void initialize() throws ParseException {
		movieListNS.getColumnConstraints().get(0).setPrefWidth(0);
		movieListNS.getColumnConstraints().get(0).setMinWidth(0);
		movieListCS.getColumnConstraints().get(0).setPrefWidth(0);
		movieListCS.getColumnConstraints().get(0).setMinWidth(0);

		Movie blackPanther = new Movie("Black Panther", "9/20/2021", 350, "./resources/posters/1.jpg");
		Movie babyDriver = new Movie("Baby Driver", "08/14/2022", 405.50, "./resources/posters/2.jpg");
		Movie captainMarvel = new Movie("Captain Marvel", "09/18/2022", 300, "./resources/posters/3.jpg");
		Movie SMHomecoming = new Movie("Spider-Man - Homecoming", "06/14/2022", 375, "./resources/posters/4.jpg");
		Movie moonlight = new Movie("Moonlight", "07/13/2022", 420, "./resources/posters/5.jpg");
		Movie thorRagnarok = new Movie("Thor - Ragnarok", "08/06/2022", 275, "./resources/posters/6.jpg");
		Movie bohemianRhapsody = new Movie("Bohemian Rhapsody", "06/05/2023", 325, "./resources/posters/7.jpg");
		Movie SMIntoTheSpiderverse = new Movie("Spider-Man - Into the Spiderverse", "05/14/2022", 300, "./resources/posters/8.jpg");
		Movie theBatman = new Movie("The Batman", "05/27/2023", 450, "./resources/posters/9.jpg");
		
		ArrayList<Movie> movies = new ArrayList<Movie>();
		movies.add(blackPanther);
		movies.add(babyDriver);
		movies.add(captainMarvel);
		movies.add(SMHomecoming);
		movies.add(moonlight);
		movies.add(thorRagnarok);
		movies.add(bohemianRhapsody);
		movies.add(SMIntoTheSpiderverse);
		movies.add(theBatman);
		for (int i = 0; i < movies.size(); i++) {
			Movie ithMovie = movies.get(i);
			
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			Date premiereDate = format.parse(ithMovie.premiereDate);
			
			
			Schedule sched1 = new Schedule(ithMovie.premiereDate);
			sched1.availableTimes.add("9:30 AM");
			sched1.availableTimes.add("12:00 PM");
			sched1.availableTimes.add("2:30 PM");
			sched1.availableTimes.add("5:00 PM");
			sched1.availableTimes.add("7:30 PM");
			ithMovie.schedules.add(sched1);
			
			Date premiereDate1 = new Date(premiereDate.getTime() +  + TimeUnit.DAYS.toMillis(1));
			Schedule sched2 = new Schedule(format.format(premiereDate1));
			sched2.availableTimes.add("10:30 AM");
			sched2.availableTimes.add("1:00 PM");
			sched2.availableTimes.add("3:30 PM");
			sched2.availableTimes.add("6:00 PM");
			sched2.availableTimes.add("8:30 PM");
			ithMovie.schedules.add(sched2);
			
			Date premiereDate2 = new Date(premiereDate.getTime() +  + TimeUnit.DAYS.toMillis(2));
			Schedule sched3 = new Schedule(format.format(premiereDate2));
			sched3.availableTimes.add("11:30 AM");
			sched3.availableTimes.add("2:00 PM");
			sched3.availableTimes.add("4:30 PM");
			sched3.availableTimes.add("7:00 PM");
			sched3.availableTimes.add("9:30 PM");
			ithMovie.schedules.add(sched3);
			 
			addMovie(ithMovie);
		}

		Voucher voucher1 = Vouchers.generate("Cool voucher", 0.125);
		Voucher voucher2 = Vouchers.generate("Summer promo", 0.25);
		Voucher voucher3 = Vouchers.generate("Epic voucher", 0.5);
		System.out.println("Sample vouchers:");
		System.out.println("Voucher 1: (12.5%)");
		System.out.println(voucher1.getCode());
		System.out.println("Voucher 2: (25%)");
		System.out.println(voucher2.getCode());
		System.out.println("Voucher 3: (50%)");
		System.out.println(voucher3.getCode());

	}
}
