package scenes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.MovieItem;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
	
	ArrayList<MovieItem> movies = new ArrayList<MovieItem>();

	@FXML
	public void filterMovies(KeyEvent e) {
		String entry = movieSearch.getText();
		String formattedEntry = entry.trim().toLowerCase().replaceAll("[^a-zA-Z0-9\\s+]", "");
		String[] keywords = formattedEntry.split("\\s+");
		String keywordsRegex = "(" + String.join("|", keywords) + ")";
		Pattern p = Pattern.compile(keywordsRegex);
			
		for(int i = 0; i < movies.size(); i++) {
			MovieItem ithMovie = movies.get(i);
			String formattedTitle = ithMovie.title.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
			Matcher match = p.matcher(formattedTitle);
			if(match.find()) {
				ithMovie.show();
			} else {
				ithMovie.hide();
			}
		}
	}
	
	public void test() {
		System.out.println();
	}
	
	private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
	    for (Node node : gridPane.getChildren()) {
	        if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
	            return node;
	        }
	    }
	    return null;
	}
	
	private void addMovie(String posterURL, String title) {
		MovieItem movie = new MovieItem(posterURL, title);
		movie.addToGridPane(movieListNS);
		movies.add(movie);
	}
	
	private void addMovie(String posterURL, String title, String premiereDate) {
		MovieItem movie = new MovieItem(posterURL, title);
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date now = new Date();
			if(dateFormat.parse(premiereDate).before(now)) {
				movie.addToGridPane(movieListNS);
			} else {
				movie.addToGridPane(movieListCS);
				movie.button.setDisable(true);
				movie.button.setText(premiereDate);
			}
			movies.add(movie);
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
