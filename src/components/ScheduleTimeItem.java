package components;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;

public class ScheduleTimeItem {
	public GridPane timeItemWrapper;
	public Label timeLabel;
	public Label meridiemLabel;
	public String time;
	public String hours, minutes, meridiem;
	public ListColumn column;
	public boolean isActive = false;
	
	public boolean hidden = false;
	
	public ScheduleTimeItem(String time) {
		this.time = time;
		
		//Format
		SimpleDateFormat defaultFormat = new SimpleDateFormat("hh:mm a");
		SimpleDateFormat targetFormat = new SimpleDateFormat("hh/mm/a");
		try {
		    String[] formattedTime = targetFormat.format(defaultFormat.parse(time)).split("/");
			this.hours = formattedTime[0];
			this.minutes = formattedTime[1];
			this.meridiem = formattedTime[2];
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		
		//Create wrapper for the item
		timeItemWrapper = new GridPane();
		timeItemWrapper.setPadding(new Insets(10, 10, 10, 10));
		GridPane.setMargin(timeItemWrapper, new Insets(10, 5, 10, 5));
		timeItemWrapper.getStyleClass().add("schedule-time-item");
		timeItemWrapper.setAlignment(Pos.CENTER);
		timeItemWrapper.setMinHeight(140);
		
		//Create column for time item wrapper
		ColumnConstraints timeItemColumn = new ColumnConstraints();
		timeItemColumn.setFillWidth(true);
		timeItemColumn.setHgrow(Priority.SOMETIMES);
		timeItemColumn.setMinWidth(Region.USE_COMPUTED_SIZE);
		timeItemColumn.setPrefWidth(Region.USE_COMPUTED_SIZE);
		timeItemColumn.setMaxWidth(Region.USE_COMPUTED_SIZE);
		timeItemColumn.setHalignment(HPos.CENTER);
		timeItemColumn.setPercentWidth(100);

		//Row for time label
		RowConstraints timeRow = new RowConstraints();
		timeRow.setFillHeight(true);
		timeRow.setMinHeight(10);
		timeRow.setPrefHeight(40);
		timeRow.setMaxHeight(Region.USE_PREF_SIZE);
		timeRow.setPercentHeight(-1);
		timeRow.setVgrow(Priority.NEVER);
		timeRow.setValignment(VPos.CENTER);
		
		//Row for meridiem label
		RowConstraints meridiemRow = new RowConstraints();
		meridiemRow.setFillHeight(true);
		meridiemRow.setMinHeight(5);
		meridiemRow.setPrefHeight(30);
		meridiemRow.setMaxHeight(Region.USE_PREF_SIZE);
		meridiemRow.setPercentHeight(-1);
		meridiemRow.setVgrow(Priority.NEVER);
		meridiemRow.setValignment(VPos.CENTER);
		
		//Add the rows and columns to time item wrapper
		timeItemWrapper.getColumnConstraints().add(timeItemColumn);
		timeItemWrapper.getRowConstraints().addAll(timeRow, meridiemRow);
		
		//Create label for minutes
		timeLabel = new Label();
		timeLabel.setText(hours + ":" + minutes);
		timeLabel.getStyleClass().add("schedule-time-item-minutes");

		//Create label for meridiem
		meridiemLabel = new Label();
		meridiemLabel.setText(meridiem);
		meridiemLabel.getStyleClass().add("schedule-time-item-meridiem");
		
		//Add all
		timeItemWrapper.add(timeLabel, 0, 0);
		timeItemWrapper.add(meridiemLabel, 0, 1);
	}

	private double prefWidth;
	private double minWidth;
	public void hide() {
		if (!hidden) {
			minWidth = column.columnConstraints.getMinWidth();
			prefWidth = column.columnConstraints.getPrefWidth();
			column.columnConstraints.setMinWidth(0);
			column.columnConstraints.setPrefWidth(0);
			timeItemWrapper.setVisible(false);
			hidden = true;
		}
	}
	
	public void show() {
		if(hidden) {
			column.columnConstraints.setMinWidth(minWidth);
			column.columnConstraints.setPrefWidth(prefWidth);
			timeItemWrapper.setVisible(true);
			hidden = false;
		}
	}
	
	public void addToGridPane(GridPane gridPane) {
		//Create column and add it to gridpane
		column = new ListColumn(gridPane, 120);
		
		//Add poster wrapper and poster to column
		//The poster doesn't necessarily need to be inside the poster wrapper
		int currentColumnLength = gridPane.getColumnCount();
		int index = currentColumnLength - 1;
		column.gridPane.add(timeItemWrapper, index, 0);
	}
}
