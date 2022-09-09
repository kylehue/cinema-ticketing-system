package controllers;

import java.util.ArrayList;

import application.Movie;
import components.SeatItem;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;

public class SeatsController {
	@FXML
	private GridPane mainContainer;
	
	@FXML
	private Button proceedButton;

	@FXML
	private GridPane seatsGridPane;

	public String rowNames = "ABCDEFGHIJKLMNOPQRSTUVQXYZ";
	public int rowCount = rowNames.length() - 15;
	public int columnCount = 24;
	public double buttonSize = 20;
	public double gap = 10;
	private int maxTickets = 1;
	private ArrayList<SeatItem> seatItems = new ArrayList<SeatItem>();
	private Movie movie;

	@FXML
	public void goBack() {
		SceneController.switchToTicket();
	}

	@FXML
	public void proceed() {
		String seats = "";
		for (int i = 0; i < seatItems.size(); i++) {
			SeatItem ithSeatItem = seatItems.get(i);
			if (ithSeatItem.isActive) {
				seats += ithSeatItem.id + " ";
			}
		}
		
		seats = seats.trim().replaceAll("\s+", ", ");
		
		SceneController.billingController.setSeats(seats);
		SceneController.switchToBilling();
	}
	
	public void reset() {
		//Reset all
		maxTickets = 1;
		
		for (int i = 0; i < seatItems.size(); i++) {
			SeatItem ithSeatItem = seatItems.get(i);
			if (ithSeatItem.isActive) {
				ithSeatItem.isActive = false;
				ithSeatItem.button.getStyleClass().remove("seat-button-active");
			}
		}
		
		proceedButton.setDisable(true);
	}
	
	public void setMovie(Movie movie) {
		this.movie = movie;
		
		reset();
	}
	
	public void setMaxTickets(int maxTickets) {
		this.maxTickets = maxTickets;
	}
	
	private int getSelectedSeatsCount() {
		int selectedSeatsCount = 0;
		for (int i = 0; i < seatItems.size(); i++) {
			SeatItem ithSeatItem = seatItems.get(i);
			if (ithSeatItem.isActive) {
				selectedSeatsCount++;
			}
		}
		
		return selectedSeatsCount;
	}
	
	private void addSeat(String id, GridPane gridPane, int rowIndex, int columnIndex) {
		SeatItem seat = new SeatItem(id, buttonSize);
		
		
		//Add event on click
		seat.button.setOnMouseClicked((MouseEvent event) -> {
			int selectedSeatsCount = getSelectedSeatsCount();
			
			//Toggle
			if (!seat.isActive) {
				//Only allow toggling to active if the selected seats count is less than max tickets
				if(selectedSeatsCount < maxTickets) {
					seat.button.getStyleClass().add("seat-button-active");
					seat.isActive = true;
					
					if (selectedSeatsCount + 1 == maxTickets) {
						proceedButton.setDisable(false);
					}
				}
			} else {
				seat.button.getStyleClass().remove("seat-button-active");
				seat.isActive = false;
				
				if (selectedSeatsCount - 1 < maxTickets) {
					proceedButton.setDisable(true);
				}
			}
		});
		
		seatItems.add(seat);
		gridPane.add(seat.button, columnIndex, rowIndex);
	}

	private int threshold1 = 6;
	private boolean isSkippable(int rowIndex, int columnIndex) {
		if (isSkippableRow(rowIndex, columnIndex)) return true;
		if (columnIndex == threshold1 + 1 || columnIndex == columnCount - threshold1 + 1) return true;
		
		return false;
	}

	private boolean isSkippableRow(int rowIndex, int columnIndex) {
		//Skips the nth to the last row (in the center) of the cinema
		int threshold2 = 3;
		if (rowIndex == rowCount - threshold2 + 1 && columnIndex > threshold1 + 1 && columnIndex < columnCount - threshold1 + 1) return true;
		//Skips the front nth rows on the left and right side of the cinema
		int threshold3 = 2;
		if (rowIndex < threshold3 + 1 && (columnIndex < threshold1 + 1 || columnIndex > columnCount - threshold1 + 1)) return true;
		
		return false;
	}

	private void initializeSeats() {
		//Create cells for the table then add seats
		initializeCells();
		
		//Add default seats
		int rowCounter = 0;
		int columnCounter = 0;
		for (int rowIndex = 1; rowIndex <= rowCount + 1; rowIndex++) {
			for (int columnIndex = 1; columnIndex <= columnCount + 1; columnIndex++) {
				String id = String.valueOf(rowNames.charAt(rowCounter)) + (columnCounter + 1);
				if (isSkippableRow(rowIndex, columnIndex)) columnCounter++;
				if (!isSkippable(rowIndex, columnIndex)) {
					addSeat(id, seatsGridPane, rowIndex, columnIndex);
					columnCounter++;
				}
			}
			rowCounter++;
			columnCounter = 0;
		}
	}
	
	private void addRow(GridPane gridPane) {
		//Create row
		RowConstraints row = new RowConstraints();
		row.setPrefHeight(buttonSize + gap);
		row.setValignment(VPos.CENTER);
		row.setMaxHeight(Region.USE_PREF_SIZE);
		row.setMinHeight(Region.USE_PREF_SIZE);
		
		gridPane.getRowConstraints().add(row);
	}
	
	private void addColumn(GridPane gridPane) {
		//Create column
		ColumnConstraints column = new ColumnConstraints();
		column.setPrefWidth(buttonSize + gap);
		column.setHalignment(HPos.CENTER);
		column.setMaxWidth(Region.USE_PREF_SIZE);
		column.setMinWidth(Region.USE_PREF_SIZE);
		
		gridPane.getColumnConstraints().add(column);
	}
	
	private void clearCells() {
		seatsGridPane.getRowConstraints().clear();
		seatsGridPane.getColumnConstraints().clear();
	}
	
	private void initializeCells() {
		//Clear the rows and columns first
		clearCells();
		
		//Then add
		//Add 2 for the row & column cell labels
		for (int rowIndex = 0; rowIndex <= rowCount + 2; rowIndex++) {
			addRow(seatsGridPane);
		}
		
		for (int columnIndex = 0; columnIndex <= columnCount + 2; columnIndex++) {
			addColumn(seatsGridPane);
		}
		
		//Add labels
		//Row labels on left side
		for (int rowIndex = 1; rowIndex <= rowCount + 1; rowIndex++) {
			Label rowNameLabel = new Label();
			rowNameLabel.setText(Character.toString(rowNames.charAt(rowIndex - 1)));
			rowNameLabel.setStyle("-fx-font-size: 0.8em; -fx-text-fill: -slate-200; -fx-font-weight: bold;");
			seatsGridPane.add(rowNameLabel, 0, rowIndex);
		}
		
		//Row labels on right side
		for (int rowIndex = 1; rowIndex <= rowCount + 1; rowIndex++) {
			Label rowNameLabel = new Label();
			rowNameLabel.setText(Character.toString(rowNames.charAt(rowIndex - 1)));
			rowNameLabel.setStyle("-fx-font-size: 0.8em; -fx-text-fill: -slate-200; -fx-font-weight: bold;");
			seatsGridPane.add(rowNameLabel, columnCount + 2, rowIndex);
		}
		
		//Column labels on top side
		for (int columnIndex = 1, counter = 0; columnIndex <= columnCount + 1; columnIndex++) {
			if (isSkippable(rowCount, columnIndex)) continue;
			else counter++;
			Label rowNameLabel = new Label();
			rowNameLabel.setText(String.valueOf(counter));
			rowNameLabel.setStyle("-fx-font-size: 0.8em; -fx-text-fill: -slate-200; -fx-font-weight: bold;");
			seatsGridPane.add(rowNameLabel, columnIndex, 0);
		}
		
		//Column labels on bottom side
		for (int columnIndex = 1, counter = 0; columnIndex <= columnCount + 1; columnIndex++) {
			if (isSkippable(rowCount + 2, columnIndex)) continue;
			else counter++;
			Label rowNameLabel = new Label();
			rowNameLabel.setText(String.valueOf(counter));
			rowNameLabel.setStyle("-fx-font-size: 0.8em; -fx-text-fill: -slate-200; -fx-font-weight: bold;");
			seatsGridPane.add(rowNameLabel, columnIndex, rowCount + 2);
		}
		
	}
	
	@FXML
	public void initialize() {
		initializeSeats();
		//seatsGridPane.setGridLinesVisible(true);
	}
}
