package controllers;

import application.Movie;
import application.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class SuccessController {
	@FXML
	private GridPane mainContainer;

	@FXML
	private ScrollPane posterWrapper;
	
	@FXML
	private ImageView poster;

	@FXML
	private Button proceedButton;

	public Movie movie;
	
	public void proceed() {
		SceneController.switchToHome();
	}
	
	public void setMovie(Movie movie) {
		this.movie = movie;
		
		//Set poster
		Image posterImage = new Image(movie.posterURL);
		poster.setImage(posterImage);
	}

	@FXML
	public void initialize() {
		Utils.wrapImageView(poster, posterWrapper, mainContainer);
	}
}
