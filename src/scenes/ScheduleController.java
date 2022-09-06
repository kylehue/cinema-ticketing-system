package scenes;

import application.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ScheduleController {
	@FXML
	private GridPane mainContainer;

	@FXML
	private ScrollPane posterWrapper;

	@FXML
	private ImageView poster;
	
	@FXML
	private GridPane scheduleContainer;

	@FXML
	public void goBack(ActionEvent event) {
		SceneController.switchToHome();
	}
	
	@FXML
	public void setPoster(String posterURL) {
		Image posterImage = new Image(posterURL);
		poster.setImage(posterImage);
	}

	@FXML
	public void initialize() {
		
		Utils.wrapImageView(poster, posterWrapper, mainContainer);
		
		//poster.setImage(posterImage);

		
	}
}
