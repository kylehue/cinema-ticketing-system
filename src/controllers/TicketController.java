package controllers;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

	private DecimalFormat decimalFormat = new DecimalFormat("0.00");
	
	@FXML
	private Label subtotalLabel;

	@FXML
	private Label quantityLabel;

	@FXML
	private Button incrementQuantityButton;

	@FXML
	private Button decrementQuantityButton;
	
	public int quantity = 1;
	public Movie movie;

	@FXML
	public void goBack(ActionEvent event) {
		SceneController.switchToSchedule();
	}
	
	private void updateSubtotal() {
		String formattedSubtotal = decimalFormat.format(movie.price * quantity);
		subtotalLabel.setText(formattedSubtotal);
	}

	@FXML
	public void incrementQuantity(ActionEvent event) {
		quantity++;
		quantityLabel.setText(String.valueOf(quantity));
		updateSubtotal();
		SceneController.seatsController.reset();
	}

	@FXML
	public void decrementQuantity(ActionEvent event) {
		if (quantity - 1 > 0) {
			quantity--;
			quantityLabel.setText(String.valueOf(quantity));
			updateSubtotal();
			SceneController.seatsController.reset();
		}
	}
	
	public void proceed() {
		SceneController.seatsController.setMaxTickets(quantity);
		SceneController.billingController.setQuantity(quantity);
		SceneController.overviewController.setQuantity(quantity);
		SceneController.switchToSeats();
	}
	
	public void setMovie(Movie movie) {
		this.movie = movie;
		
		//Reset quantity
		quantity = 1;
		quantityLabel.setText(String.valueOf(quantity));
		
		//Set movie title
		filmLabel.setText(movie.title);
		
		//Set ticket's price
		String formattedPrice = decimalFormat.format(movie.price);
		ticketPriceLabel.setText(formattedPrice);
		
		//Set poster
		Image posterImage = new Image(movie.posterURL);
		poster.setImage(posterImage);
		
		//Update subtotal
		updateSubtotal();
	}
	
	public void setSchedule(String date, String time) {
		//Format
		SimpleDateFormat defaultFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat targetFormat = new SimpleDateFormat("MMMM dd, yyyy");
		try {
		    String formattedDate = targetFormat.format(defaultFormat.parse(date));
		    date = formattedDate;
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		
		scheduledDateLabel.setText(date);
		scheduledTimeLabel.setText(time);
	}

	@FXML
	public void initialize() {
		Utils.wrapImageView(poster, posterWrapper, mainContainer);
	}
}
