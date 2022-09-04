package scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomeScene {
	public Scene scene;
	public HomeScene(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
			scene = new Scene(root, 800, 600);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
