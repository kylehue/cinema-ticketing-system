package scenes;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.Movie;
import application.ScheduleDateItem;
import application.ScheduleTimeItem;
import application.Utils;
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
	private GridPane scheduleDateContainer;
	
	@FXML
	private GridPane scheduleTimeContainer;

	private ArrayList<ScheduleDateItem> scheduleDateItems = new ArrayList<ScheduleDateItem>();
	private ArrayList<ScheduleTimeItem> scheduleTimeItems = new ArrayList<ScheduleTimeItem>();


	@FXML
	public void goBack(ActionEvent event) {
		SceneController.switchToHome();
	}
	
	public void proceed() {
		String date = "";
		for (int i = 0; i < scheduleDateItems.size(); i++) {
			GridPane ithDate = scheduleDateItems.get(i).dateItemWrapper;
			if (ithDate.getStyleClass().contains("schedule-date-item-active")) {
				Label month = (Label)(ithDate.getChildren().get(0));
				Label day = (Label)(ithDate.getChildren().get(1));
				Label dayName = (Label)(ithDate.getChildren().get(2));
				date = month.getText() + " " + day.getText() + " " + dayName.getText();
				break;
			}
		}

		String time = "";
		for (int i = 0; i < scheduleTimeItems.size(); i++) {
			GridPane ithTime = scheduleTimeItems.get(i).timeItemWrapper;
			if (ithTime.getStyleClass().contains("schedule-time-item-active")) {
				Label hours = (Label)(ithTime.getChildren().get(0));
				Label minutes = (Label)(ithTime.getChildren().get(1));
				Label meridiem = (Label)(ithTime.getChildren().get(2));
				time = hours.getText() + ":" + minutes.getText() + " " + meridiem.getText();
				break;
			}
		}
		

		SceneController.ticketController.setSchedule(date, time);
		SceneController.switchToTicket();
	}
	
	public void setMovie(Movie movie) {
		Image posterImage = new Image(movie.posterURL);
		poster.setImage(posterImage);
	}
	
	public void addDate(String month, String day, String dayName) {
		ScheduleDateItem scheduleDateItem = new ScheduleDateItem(month, day, dayName);
		scheduleDateItem.addToGridPane(scheduleDateContainer);
		scheduleDateItems.add(scheduleDateItem);
	}
	
	public void addTime(String hours, String minutes, String meridiem) {
		ScheduleTimeItem scheduleTimeItem = new ScheduleTimeItem(hours, minutes, meridiem);
		scheduleTimeItem.addToGridPane(scheduleTimeContainer);
		scheduleTimeItems.add(scheduleTimeItem);
	}
	
	@FXML
	public void filterDates(KeyEvent e) {
		String entry = scheduleDateSearch.getText();
		Pattern pattern = Utils.getKeywords(entry);

		System.out.println(entry);
		System.out.println(pattern);
			
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
	
	public void clearDates() {
		ObservableList<Node> dates = scheduleDateContainer.getChildren();
		if(dates.size() > 0) {
			dates.clear();
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
		
		//poster.setImage(posterImage);
		addDate("DEC", "29", "Wed");
		addDate("NOV", "17", "Mon");
		addDate("AUG", "08", "Thu");
		addDate("JAN", "11", "Sat");
		addDate("FEB", "22", "Tue");
		addTime("11", "30", "AM");
	}
}
