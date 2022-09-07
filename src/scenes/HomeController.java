package scenes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.Movie;
import application.MovieItem;
import application.Utils;
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
	
	private void addMovie(String posterURL, String title) {
		Movie movie = new Movie(posterURL, title, 250 * Math.random() + 100);
		MovieItem movieItem = new MovieItem(movie);
		movieItem.addToGridPane(movieListNS);
		movieItems.add(movieItem);
	}
	
	private void addMovie(String posterURL, String title, String premiereDate) {
		Movie movie = new Movie(posterURL, title, 250 * Math.random() + 100);
		
		MovieItem movieItem = new MovieItem(movie);
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date now = new Date();
			//Add "now showing" list if the premiere date has passed.
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
	}
	
	@FXML
	public void initialize() {
		movieListNS.getColumnConstraints().get(0).setPrefWidth(0);
		movieListNS.getColumnConstraints().get(0).setMinWidth(0);
		movieListCS.getColumnConstraints().get(0).setPrefWidth(0);
		movieListCS.getColumnConstraints().get(0).setMinWidth(0);

		addMovie("./resources/posters/1.jpg", "Black Panther", "09/20/2023");
		addMovie("./resources/posters/2.jpg", "Baby Driver", "09/20/2023");
		addMovie("./resources/posters/3.jpg", "Captain Marvel");
		addMovie("./resources/posters/4.jpg", "Spider-Man - Homecoming");
		addMovie("./resources/posters/5.jpg", "Moonlight");
		addMovie("./resources/posters/6.jpg", "Thor - Ragnarok");
		addMovie("./resources/posters/7.jpg", "Bohemian Rhapsody");
		addMovie("./resources/posters/8.jpg", "Spider-Man - Into the Spiderverse");
		addMovie("./resources/posters/9.jpg", "The Batman");
	}
}
