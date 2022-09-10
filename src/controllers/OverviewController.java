package controllers;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import application.Movie;
import application.Tickets;
import application.Utils;
import application.Voucher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class OverviewController {
	@FXML
	private GridPane mainContainer;

	@FXML
	private ScrollPane posterWrapper;
	
	@FXML
	private ImageView poster;

	@FXML
	private Label filmLabel;

	@FXML
	private Button proceedButton;
	
	@FXML
	private Label scheduledDateLabel;
	
	@FXML
	private Label scheduledTimeLabel;

	@FXML
	private Label ticketPriceLabel;

	@FXML
	private Label subtotalLabel;

	@FXML
	private Label quantityLabel;

	@FXML
	private Label seatsLabel;

	@FXML
	private Label paymentAmountLabel;

	@FXML
	private Label totalAmountLabel;

	@FXML
	private Label changeLabel;

	@FXML
	private Label discountTitleLabel;

	@FXML
	private Label discountRateLabel;

	@FXML
	private Label discountedAmountLabel;

	private Movie movie;
	private String date;
	private String time;
	private String seats;
	private int quantity = 1;
	private double paymentAmount = 0;
	private double changeAmount = 0;
	private double discountedPriceAmount = 0;
	private double discountRate = 0;
	private Voucher voucher = null;
	private DecimalFormat decimalFormat = new DecimalFormat("0.00");

	@FXML
	public void goBack() {
		SceneController.switchToBilling();
	}
	
	@FXML
	public void proceed() {
		Tickets.add(movie.id, date, time, seats);
		SceneController.switchToSuccess();
	}

	@FXML
	public void initialize() {
		Utils.wrapImageView(poster, posterWrapper, mainContainer);
		
	}
	
	public void setMovie(Movie movie) {
		this.movie = movie;
		
		//Reset all
		quantity = 1;
		seatsLabel.setText("");
		paymentAmount = 0;
		changeAmount = 0;
		discountedPriceAmount = 0;
		discountRate = 0;
		voucher = null;
		
		//Set movie title
		filmLabel.setText(movie.title);
		
		//Set ticket's price
		String formattedPrice = decimalFormat.format(movie.price);
		ticketPriceLabel.setText(formattedPrice);
		
		//Set poster
		Image posterImage = new Image(movie.posterURL);
		poster.setImage(posterImage);
	}
	
	public void setSchedule(String date, String time) {
		this.date = date;
		this.time = time;
		
		//Format
		SimpleDateFormat defaultFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat targetFormat = new SimpleDateFormat("MMMM dd, yyyy");
		try {
		    String formattedDate = targetFormat.format(defaultFormat.parse(date));
			scheduledDateLabel.setText(formattedDate);
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		
		scheduledTimeLabel.setText(time);
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
		double subtotal = movie.price * quantity;

		quantityLabel.setText(String.valueOf(quantity));
		subtotalLabel.setText(decimalFormat.format(subtotal));
	}
	
	public void setSeats(String seats) {
		this.seats = seats;
		seatsLabel.setText(seats);
	}
	
	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
	}
	
	public void compute() {
		String discountTitleText = "None";
		String discountRateText = "0.00%";
		String discountAmountText = "";
		String totalAmountText = "";
		String paymentAmountText = "";
		String changeText = "";
		double subtotal = movie.price * quantity;
		if (voucher != null) {
			discountRate = voucher.getDiscountRate();
			//Compute
			discountedPriceAmount = subtotal * discountRate;
			double discountedTotal = subtotal - discountedPriceAmount;
			changeAmount = paymentAmount - discountedTotal;
			
			//Update text
			discountTitleText = voucher.title;
			discountRateText = String.valueOf(decimalFormat.format(discountRate * 100) + "%");
			discountAmountText = "(" + decimalFormat.format(subtotal) + " - " + decimalFormat.format(discountedPriceAmount) + ")";
			totalAmountText = String.valueOf(decimalFormat.format(discountedTotal));
			paymentAmountText = String.valueOf(decimalFormat.format(paymentAmount));
			changeText = String.valueOf(decimalFormat.format(changeAmount));
		} else {
			//Compute
			changeAmount = paymentAmount - subtotal;
			
			//Update text
			totalAmountText = String.valueOf(decimalFormat.format(subtotal));
			paymentAmountText = String.valueOf(decimalFormat.format(paymentAmount));
			changeText = String.valueOf(decimalFormat.format(changeAmount));
		}
		
		//Set texts
		discountTitleLabel.setText(discountTitleText);
		discountRateLabel.setText(discountRateText);
		discountedAmountLabel.setText(discountAmountText);
		totalAmountLabel.setText(totalAmountText);
		paymentAmountLabel.setText(paymentAmountText);
		changeLabel.setText(changeText);
	}
}
