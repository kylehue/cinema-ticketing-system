package scenes;

import java.io.IOException;

import application.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {
	public static Stage stage;
	public static Scene homeScene, scheduleScene;
	public static HomeController homeController;
	public static ScheduleController scheduleController;
	
	public SceneController(Stage stage) throws IOException {
		SceneController.stage = stage;
		
		//Setup scene for home
		FXMLLoader home = new FXMLLoader(getClass().getResource("./../scenes/Home.fxml"));
		Parent homeRoot = home.load();
		homeController = home.getController();
		homeScene = new Scene(homeRoot, Constants.width, Constants.height);
		
		//Setup scene for schedule
		FXMLLoader schedule = new FXMLLoader(getClass().getResource("./../scenes/Schedule.fxml"));
		Parent scheduleRoot = schedule.load();
		scheduleController = schedule.getController();
		scheduleScene = new Scene(scheduleRoot, Constants.width, Constants.height);
	}
	
	public static void switchToHome() {
		stage.setScene(homeScene);
	}
	
	public static void switchToSchedule() {
		stage.setScene(scheduleScene);
	}
}
