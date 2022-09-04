package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import scenes.HomeScene;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			HomeScene home = new HomeScene(stage);
			stage.setScene(home.scene);
			stage.setWidth(800);
			stage.setHeight(600);
			stage.setTitle("Cinema Ticketing System");
			//stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
