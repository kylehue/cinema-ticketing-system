package application;

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
	public Label hoursLabel;
	public Label minutesLabel;
	public Label meridiemLabel;
	public String hours, minutes, meridiem;
	public ListColumn column;
	
	public boolean hidden = false;
	
	public ScheduleTimeItem(String hours, String minutes, String meridiem) {
		this.hours = hours;
		this.minutes = minutes;
		this.meridiem = meridiem;
		//Create wrapper for the item
		timeItemWrapper = new GridPane();
		timeItemWrapper.setPadding(new Insets(10, 10, 10, 10));
		GridPane.setMargin(timeItemWrapper, new Insets(10, 5, 10, 5));
		timeItemWrapper.getStyleClass().add("schedule-time-item");
		timeItemWrapper.setAlignment(Pos.CENTER);
		
		//Create column for time item wrapper
		ColumnConstraints timeItemColumn = new ColumnConstraints();
		timeItemColumn.setFillWidth(true);
		timeItemColumn.setHgrow(Priority.SOMETIMES);
		timeItemColumn.setMinWidth(Region.USE_COMPUTED_SIZE);
		timeItemColumn.setPrefWidth(Region.USE_COMPUTED_SIZE);
		timeItemColumn.setMaxWidth(Region.USE_COMPUTED_SIZE);
		timeItemColumn.setHalignment(HPos.CENTER);
		timeItemColumn.setPercentWidth(100);

		//Create rows for time item wrapper
		//Row for month label
		RowConstraints hoursRow = new RowConstraints();
		hoursRow.setFillHeight(true);
		hoursRow.setMinHeight(5);
		hoursRow.setPrefHeight(40);
		hoursRow.setMaxHeight(Region.USE_PREF_SIZE);
		hoursRow.setPercentHeight(-1);
		hoursRow.setVgrow(Priority.NEVER);
		hoursRow.setValignment(VPos.CENTER);
		//Row for day label
		RowConstraints minutesRow = new RowConstraints();
		minutesRow.setFillHeight(true);
		minutesRow.setMinHeight(10);
		minutesRow.setPrefHeight(40);
		minutesRow.setMaxHeight(Region.USE_PREF_SIZE);
		minutesRow.setPercentHeight(-1);
		minutesRow.setVgrow(Priority.NEVER);
		minutesRow.setValignment(VPos.CENTER);
		//Row for day name label
		RowConstraints meridiemRow = new RowConstraints();
		meridiemRow.setFillHeight(true);
		meridiemRow.setMinHeight(5);
		meridiemRow.setPrefHeight(40);
		meridiemRow.setMaxHeight(Region.USE_PREF_SIZE);
		meridiemRow.setPercentHeight(-1);
		meridiemRow.setVgrow(Priority.NEVER);
		meridiemRow.setValignment(VPos.CENTER);
		
		//Add the rows and columns to time item wrapper
		timeItemWrapper.getColumnConstraints().add(timeItemColumn);
		timeItemWrapper.getRowConstraints().addAll(hoursRow, minutesRow, meridiemRow);
		
		//Create label for hours
		hoursLabel = new Label();
		hoursLabel.setText(hours);
		hoursLabel.getStyleClass().add("schedule-time-item-hours");

		//Create label for minutes
		minutesLabel = new Label();
		minutesLabel.setText(minutes);
		minutesLabel.getStyleClass().add("schedule-time-item-minutes");

		//Create label for meridiem
		meridiemLabel = new Label();
		meridiemLabel.setText(meridiem);
		meridiemLabel.getStyleClass().add("schedule-time-item-meridiem");
		
		//Add all
		timeItemWrapper.add(hoursLabel, 0, 0);
		timeItemWrapper.add(minutesLabel, 0, 1);
		timeItemWrapper.add(meridiemLabel, 0, 2);
		
		
		//Add click event on the time wrapper
		//When clicked, set to active.
		timeItemWrapper.setOnMouseClicked((MouseEvent event) -> {
			GridPane container = (GridPane)((Node)event.getSource()).getParent();
			ObservableList<Node> children =  container.getChildren();
			
			//Remove all actives
			for (int i = 0; i < children.size(); i++) {
				GridPane ithChild = (GridPane)children.get(i);
				ithChild.getStyleClass().remove("schedule-time-item-active");
			}
			
			//Make the clicked node active
			timeItemWrapper.getStyleClass().add("schedule-time-item-active");
		});
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
