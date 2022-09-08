package application;
	
import controllers.SceneController;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			new SceneController(stage);
			SceneController.switchToHome();
			stage.setWidth(Constants.width);
			stage.setHeight(Constants.height);
			stage.setTitle("Cinema Ticketing System");
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
