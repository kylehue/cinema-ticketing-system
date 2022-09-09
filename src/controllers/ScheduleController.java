package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.Movie;
import application.Schedule;
import application.Utils;
import components.ScheduleDateItem;
import components.ScheduleTimeItem;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class ScheduleController {
	@FXML
	private GridPane mainContainer;

	@FXML
	private ScrollPane posterWrapper;
	
	@FXML
	private Button proceedButton;

	@FXML
	private ImageView poster;

	@FXML
	private TextField scheduleDateSearch;
	
	@FXML
	private TextField scheduleTimeSearch;

	@FXML
	private GridPane scheduleDateContainer;

	@FXML
	private GridPane scheduleTimeContainer;
	
	@FXML
	private GridPane scheduleTimeMainContainer;
	
	public Movie movie;
	private ArrayList<ScheduleDateItem> scheduleDateItems = new ArrayList<ScheduleDateItem>();
	private ArrayList<ScheduleTimeItem> scheduleTimeItems = new ArrayList<ScheduleTimeItem>();


	@FXML
	public void goBack(ActionEvent event) {
		SceneController.switchToHome();
	}
	
	public void proceed() {
		String date = "";
		for (int i = 0; i < scheduleDateItems.size(); i++) {
			ScheduleDateItem ithDate = scheduleDateItems.get(i);
			if (ithDate.isActive) {
				date = ithDate.schedule.dateText;
				break;
			}
		}

		String time = "";
		for (int i = 0; i < scheduleTimeItems.size(); i++) {
			ScheduleTimeItem ithTime = scheduleTimeItems.get(i);
			if (ithTime.isActive) {
				time = ithTime.time;
				break;
			}
		}
		

		SceneController.ticketController.setSchedule(date, time);
		SceneController.billingController.setSchedule(date, time);
		SceneController.switchToTicket();
	}
	
	private void clearDates() {
		//Remove all actives
		for (int i = 0; i < scheduleDateItems.size(); i++) {
			ScheduleDateItem ithScheduleDateItem = scheduleDateItems.get(i);
			ithScheduleDateItem.dateItemWrapper.getStyleClass().remove("schedule-date-item-active");
			ithScheduleDateItem.isActive = false;
		}
		
		scheduleDateContainer.getChildren().clear();
		scheduleDateContainer.getColumnConstraints().clear();
	}
	
	private void clearTimes() {
		//Remove all actives
		for (int i = 0; i < scheduleTimeItems.size(); i++) {
			ScheduleTimeItem ithScheduleTimeItem = scheduleTimeItems.get(i);
			ithScheduleTimeItem.timeItemWrapper.getStyleClass().remove("schedule-date-item-active");
			ithScheduleTimeItem.isActive = false;
		}
		
		//Disable proceed button
		proceedButton.setDisable(true);
		
		scheduleTimeMainContainer.setVisible(false);
		scheduleTimeContainer.getChildren().clear();
		scheduleTimeContainer.getColumnConstraints().clear();
	}
	
	public void setMovie(Movie movie) {
		this.movie = movie;
		
		//Set poster
		Image posterImage = new Image(movie.posterURL);
		poster.setImage(posterImage);
		
		//Set date schedules
		//Clear schedules then set
		clearDates();
		clearTimes();
		//Add available schedules for the chosen movie
	    for(int i = 0; i < movie.schedules.size(); i++) {
			Schedule ithSchedule = movie.schedules.get(i);
		    addDate(ithSchedule);
		}
	}
	
	public void addDate(Schedule schedule) {
		ScheduleDateItem scheduleDateItem = new ScheduleDateItem(schedule);
		scheduleDateItem.dateItemWrapper.setOnMouseClicked((MouseEvent event) -> {
			//Remove all actives
			for (int i = 0; i < scheduleDateItems.size(); i++) {
				ScheduleDateItem ithDate = scheduleDateItems.get(i);
				ithDate.dateItemWrapper.getStyleClass().remove("schedule-date-item-active");
				ithDate.isActive = false;
			}
			
			//Make the clicked node active
			scheduleDateItem.isActive = true;
			scheduleDateItem.dateItemWrapper.getStyleClass().add("schedule-date-item-active");
			
			//Clear times
			clearTimes();
			scheduleTimeMainContainer.setVisible(true);
			
			for (int i = 0; i < schedule.availableTimes.size(); i++) {
				String ithTime = schedule.availableTimes.get(i);
				addTime(ithTime);
			}
		});
		scheduleDateItem.addToGridPane(scheduleDateContainer);
		scheduleDateItems.add(scheduleDateItem);
	}
	
	public void addTime(String time) {
		ScheduleTimeItem scheduleTimeItem = new ScheduleTimeItem(time);
		//Add click event on the time wrapper
		//When clicked, set to active.
		scheduleTimeItem.timeItemWrapper.setOnMouseClicked((MouseEvent event) -> {
			//Remove all actives
			for (int i = 0; i < scheduleTimeItems.size(); i++) {
				ScheduleTimeItem ithTime = scheduleTimeItems.get(i);
				ithTime.isActive = false;
				ithTime.timeItemWrapper.getStyleClass().remove("schedule-time-item-active");
			}
			
			//Make the clicked node active
			scheduleTimeItem.isActive = true;
			scheduleTimeItem.timeItemWrapper.getStyleClass().add("schedule-time-item-active");
			
			//Enable proceed button
			proceedButton.setDisable(false);
		});
		
		scheduleTimeItem.addToGridPane(scheduleTimeContainer);
		scheduleTimeItems.add(scheduleTimeItem);
	}
	
	@FXML
	public void filterDates(KeyEvent e) {
		String entry = scheduleDateSearch.getText();
		Pattern pattern = Utils.getKeywords(entry);
			
		for(int i = 0; i < scheduleDateItems.size(); i++) {
			ScheduleDateItem ithDate = scheduleDateItems.get(i);
			String scheduleDate = (ithDate.month + " " + ithDate.day + " " + ithDate.dayName).toLowerCase();
			Matcher matcher = pattern.matcher(scheduleDate);
			if(matcher.find()) {
				ithDate.show();
			} else {
				ithDate.hide();
			}
		}
	}
	
	@FXML
	public void filterTimes(KeyEvent e) {
		String entry = scheduleTimeSearch.getText().replaceAll(":", " ");
		Pattern pattern = Utils.getKeywords(entry);
			
		for(int i = 0; i < scheduleTimeItems.size(); i++) {
			ScheduleTimeItem ithTime = scheduleTimeItems.get(i);
			String scheduleDate = (ithTime.hours + " " + ithTime.minutes + " " + ithTime.meridiem).toLowerCase();
			Matcher matcher = pattern.matcher(scheduleDate);
			if(matcher.find()) {
				ithTime.show();
			} else {
				ithTime.hide();
			}
		}
	}

	@FXML
	public void initialize() {
		Utils.wrapImageView(poster, posterWrapper, mainContainer);
		scheduleDateContainer.getColumnConstraints().get(0).setPrefWidth(0);
		scheduleDateContainer.getColumnConstraints().get(0).setMinWidth(0);
		scheduleDateContainer.getColumnConstraints().get(0).setPrefWidth(0);
		scheduleDateContainer.getColumnConstraints().get(0).setMinWidth(0);
		scheduleDateContainer.getChildren().get(0).setVisible(false);
		scheduleTimeContainer.getColumnConstraints().get(0).setPrefWidth(0);
		scheduleTimeContainer.getColumnConstraints().get(0).setMinWidth(0);
		scheduleTimeContainer.getColumnConstraints().get(0).setPrefWidth(0);
		scheduleTimeContainer.getColumnConstraints().get(0).setMinWidth(0);
		scheduleTimeContainer.getChildren().get(0).setVisible(false);

		
//		addDate("DEC", "29", "Wed");
//		addDate("NOV", "17", "Mon");
//		addDate("AUG", "08", "Thu");
//		addDate("JAN", "11", "Sat");
//		addDate("FEB", "22", "Tue");
//		addTime("11", "30", "AM");
	}
}
