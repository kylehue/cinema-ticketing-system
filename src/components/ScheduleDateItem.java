package components;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import application.Schedule;
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

public class ScheduleDateItem {
	public GridPane dateItemWrapper;
	public Label monthLabel;
	public Label dayLabel;
	public Label dayNameLabel;
	public String month, day, dayName;
	public ListColumn column;
	public Schedule schedule;

	public boolean isActive = false;
	public boolean hidden = false;
	
	public ScheduleDateItem(Schedule schedule) {
		this.schedule = schedule;
		
		//Format
		SimpleDateFormat defaultFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat targetFormat = new SimpleDateFormat("MMM/dd/EEE");
		try {
		    String[] formattedDate = targetFormat.format(defaultFormat.parse(schedule.dateText)).split("/");
			this.month = formattedDate[0].toUpperCase();
			this.day = formattedDate[1];
			this.dayName = formattedDate[2];
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		
		//Create wrapper for the item
		dateItemWrapper = new GridPane();
		dateItemWrapper.setPadding(new Insets(10, 10, 10, 10));
		GridPane.setMargin(dateItemWrapper, new Insets(10, 5, 10, 5));
		dateItemWrapper.getStyleClass().add("schedule-date-item");
		dateItemWrapper.setAlignment(Pos.CENTER);
		dateItemWrapper.setMinHeight(140);
		
		//Create column for date item wrapper
		ColumnConstraints dateItemColumn = new ColumnConstraints();
		dateItemColumn.setFillWidth(true);
		dateItemColumn.setHgrow(Priority.SOMETIMES);
		dateItemColumn.setMinWidth(Region.USE_COMPUTED_SIZE);
		dateItemColumn.setPrefWidth(Region.USE_COMPUTED_SIZE);
		dateItemColumn.setMaxWidth(Region.USE_COMPUTED_SIZE);
		dateItemColumn.setHalignment(HPos.CENTER);
		dateItemColumn.setPercentWidth(100);

		//Create rows for date item wrapper
		//Row for month label
		RowConstraints monthRow = new RowConstraints();
		monthRow.setFillHeight(true);
		monthRow.setMinHeight(5);
		monthRow.setPrefHeight(30);
		monthRow.setMaxHeight(Region.USE_PREF_SIZE);
		monthRow.setPercentHeight(-1);
		monthRow.setVgrow(Priority.NEVER);
		monthRow.setValignment(VPos.CENTER);
		//Row for day label
		RowConstraints dayRow = new RowConstraints();
		dayRow.setFillHeight(true);
		dayRow.setMinHeight(10);
		dayRow.setPrefHeight(50);
		dayRow.setMaxHeight(Region.USE_PREF_SIZE);
		dayRow.setPercentHeight(-1);
		dayRow.setVgrow(Priority.NEVER);
		dayRow.setValignment(VPos.CENTER);
		//Row for day name label
		RowConstraints dayNameRow = new RowConstraints();
		dayNameRow.setFillHeight(true);
		dayNameRow.setMinHeight(5);
		dayNameRow.setPrefHeight(30);
		dayNameRow.setMaxHeight(Region.USE_PREF_SIZE);
		dayNameRow.setPercentHeight(-1);
		dayNameRow.setVgrow(Priority.NEVER);
		dayNameRow.setValignment(VPos.CENTER);
		
		//Add the rows and columns to date item wrapper
		dateItemWrapper.getColumnConstraints().add(dateItemColumn);
		dateItemWrapper.getRowConstraints().addAll(monthRow, dayRow, dayNameRow);
		
		//Create label for month
		monthLabel = new Label();
		monthLabel.setText(month);
		monthLabel.getStyleClass().add("schedule-date-item-month");

		//Create label for day
		dayLabel = new Label();
		dayLabel.setText(day);
		dayLabel.getStyleClass().add("schedule-date-item-day");

		//Create label for day name
		dayNameLabel = new Label();
		dayNameLabel.setText(dayName);
		dayNameLabel.getStyleClass().add("schedule-date-item-day-name");
		
		//Add all
		dateItemWrapper.add(monthLabel, 0, 0);
		dateItemWrapper.add(dayLabel, 0, 1);
		dateItemWrapper.add(dayNameLabel, 0, 2);
	}

	private double prefWidth;
	private double minWidth;
	public void hide() {
		if (!hidden) {
			minWidth = column.columnConstraints.getMinWidth();
			prefWidth = column.columnConstraints.getPrefWidth();
			column.columnConstraints.setMinWidth(0);
			column.columnConstraints.setPrefWidth(0);
			dateItemWrapper.setVisible(false);
			hidden = true;
		}
	}
	
	public void show() {
		if(hidden) {
			column.columnConstraints.setMinWidth(minWidth);
			column.columnConstraints.setPrefWidth(prefWidth);
			dateItemWrapper.setVisible(true);
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
		column.gridPane.add(dateItemWrapper, index, 0);
	}
}
