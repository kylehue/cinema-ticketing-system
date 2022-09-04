package scenes;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class HomeController {
	@FXML
	private Button homeSceneButton;
	@FXML
	private Button adminSceneButton;
	@FXML
	private Button refundSceneButton;
	@FXML
	private ImageView poster_Test;
	@FXML
	private Pane posterWrapper_Test;
	@FXML
	private GridPane moviesList;
	
	public void test() {
		
	}
	
	private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
	    for (Node node : gridPane.getChildren()) {
	        if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
	            return node;
	        }
	    }
	    return null;
	}
	
	public void initialize() {
		//TODO
		//1. Programmatically add a grid column
		//2. Add a panel inside it
		//3. Add an image inside the panel
		//4. Make them all responsive
		ImageView test = new ImageView("./resources/posters/7.jpg");
		Pane testWrapper = new Pane();
		//testWrapper.getChildren().add(test);
		testWrapper.setStyle("-fx-background-color:#111;");
		moviesList.add(testWrapper, 0, 0, 1, 1);
		moviesList.add(test, 0, 0, 1, 1);
		testWrapper.getParent().prefWidth(150);
		testWrapper.getParent().maxWidth(170);
		testWrapper.getParent().minWidth(150);
		
		Node nd = getNodeFromGridPane(moviesList, 0, 0);
		nd.prefWidth(150);
		nd.maxWidth(170);
		nd.minWidth(150);
		//TODO CONTINUE HERE
		ObservableList<ColumnConstraints> columnConstraints = moviesList.getColumnConstraints();
		columnConstraints.get(0).setFillWidth(true);
		
		testWrapper.minWidth(Region.USE_COMPUTED_SIZE);
		testWrapper.minHeight(Region.USE_COMPUTED_SIZE);
		testWrapper.prefWidth(Region.USE_COMPUTED_SIZE);
		testWrapper.prefHeight(Region.USE_COMPUTED_SIZE);
		testWrapper.maxWidth(Region.USE_COMPUTED_SIZE);
		testWrapper.maxHeight(Region.USE_COMPUTED_SIZE);
		
		test.setPreserveRatio(true);
		test.fitWidthProperty().bind(testWrapper.widthProperty());
		test.fitHeightProperty().bind(testWrapper.heightProperty());
	}
}
