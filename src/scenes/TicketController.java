package scenes;

import java.text.DecimalFormat;

import application.Movie;
import application.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class TicketController {
	@FXML
	private GridPane mainContainer;

	@FXML
	private ScrollPane posterWrapper;
	
	@FXML
	private ImageView poster;

	@FXML
	private Button proceedButton;
	
	@FXML
	private Label filmLabel;
	@FXML
	private Label scheduledDateLabel;
	@FXML
	private Label scheduledTimeLabel;
	@FXML
	private Label ticketPriceLabel;

	@FXML
	public void goBack(ActionEvent event) {
		SceneController.switchToSchedule();
	}
	
	public void proceed() {
		SceneController.switchToTicket();
	}
	
	public void setMovie(Movie movie) {
		filmLabel.setText(movie.title);
		
		DecimalFormat df = new DecimalFormat("0.00");
		String formattedPrice = df.format(movie.price);
		ticketPriceLabel.setText(formattedPrice);
		Image posterImage = new Image(movie.posterURL);
		poster.setImage(posterImage);
	}
	
	public void setSchedule(String date, String time) {
		scheduledDateLabel.setText(date);
		scheduledTimeLabel.setText(time);
	}

	@FXML
	public void initialize() {
		Utils.wrapImageView(poster, posterWrapper, mainContainer);
	}
}
