package controllers;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import application.Movie;
import application.Utils;
import application.Voucher;
import application.Vouchers;
import components.SeatItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class BillingController {
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
	private TextField paymentAmountInput;

	@FXML
	private TextField voucherCodeInput;

	public Movie movie;
	public int quantity = 1;
	private Voucher voucher = null;
	private DecimalFormat decimalFormat = new DecimalFormat("0.00");

	@FXML
	public void goBack() {
		SceneController.switchToSeats();
	}
	
	@FXML
	public void proceed() {
		double paymentAmount = Double.parseDouble(paymentAmountInput.getText());
		SceneController.overviewController.setPaymentAmount(paymentAmount);
		SceneController.overviewController.setVoucher(voucher);
		SceneController.overviewController.compute();
		
		SceneController.switchToOverview();
	}
	
	@FXML
	public void validatePaymentAmount(KeyEvent event) {
		String inputValue = paymentAmountInput.getText();
		if(inputValue.matches("\\d*(\\.\\d*)?") && inputValue.length() > 0 && !(inputValue.startsWith(".") && inputValue.length() == 1)) {
			double paymentAmount = Double.parseDouble(inputValue);
			if (paymentAmount >= movie.price * quantity) {
				proceedButton.setDisable(false);
			} else {
				proceedButton.setDisable(true);
			}
        }
		
	}
	
	public void reset() {
		proceedButton.setDisable(true);
		paymentAmountInput.setText("");
		voucherCodeInput.setText("");
	}
	
	@FXML
	public void validateVoucherCode(KeyEvent event) {
		String inputValue = voucherCodeInput.getText();
		ArrayList<Voucher> vouchers = Vouchers.getVouchers();
		Voucher voucher = null;
		for (int i = 0; i < vouchers.size(); i++) {
			Voucher ithVoucher = vouchers.get(i);
			if (ithVoucher.getCode().equals(inputValue)) {
				voucher = ithVoucher;
			}
		}
		
		if (voucher != null || inputValue.length() == 0) {
			this.voucher = voucher;
			proceedButton.setDisable(false);
		} else {
			proceedButton.setDisable(true);
		}
	}

	@FXML
	public void initialize() {
		Utils.wrapImageView(poster, posterWrapper, mainContainer);
		
		//Only allow numbers in payment amount textfield
		paymentAmountInput.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		    	if(!newValue.matches("\\d*(\\.\\d*)?")) {
		    		paymentAmountInput.setText(oldValue);
		        }
		    }
		});
	}
	
	public void setMovie(Movie movie) {
		this.movie = movie;
		
		//Reset all
		quantity = 1;
		paymentAmountInput.setText("");
		voucherCodeInput.setText("");
		seatsLabel.setText("");
		proceedButton.setDisable(true);
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
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
		double subtotal = movie.price * quantity;

		quantityLabel.setText(String.valueOf(quantity));
		subtotalLabel.setText(decimalFormat.format(subtotal));
	}
	
	public void setSeats(String seats) {
		seatsLabel.setText(seats);
	}
}
