package components;

import application.Movie;
import application.Utils;
import controllers.SceneController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MovieItem {
	public Button button;
	public ImageView poster;
	public ListColumn column;
	public Pane posterWrapper;
	public Label label;
	public Movie movie;
	
	public boolean hidden = false;
	
	public MovieItem(Movie movie) {
		this.movie = movie;
		initialize(movie);
	}
	
	private void initialize(Movie movie) {
		//Create wrapper for poster
		posterWrapper = new Pane();
		posterWrapper.minWidth(10);
		posterWrapper.minHeight(15);
		//posterWrapper.setStyle("-fx-background-color:#111;");
		//gridPane.setGridLinesVisible(true);
		
		//Create image for poster
		poster = new ImageView(new Image(movie.posterURL));
		Utils.cornerRadius(poster, 40);
		
		//We only added poster wrapper so we can have its size as the basis of poster's size
		//Why? because ImageView only allows specific size values
		poster.setPreserveRatio(true);
		poster.fitWidthProperty().bind(posterWrapper.widthProperty());
		poster.fitHeightProperty().bind(posterWrapper.heightProperty());
		
		//Create label for title
		label = new Label();
		label.setText(movie.title);
		label.getStyleClass().add("movie-label");
		
		//Create "buy ticket" button
		button = new Button("Buy ticket");
		button.getStyleClass().add("button-primary");
		
		//Add click event on the button
		//When clicked, go to schedule scene
		button.setOnMouseClicked((MouseEvent event) -> {
			SceneController.scheduleController.setMovie(movie);
			SceneController.ticketController.setMovie(movie);
			SceneController.seatsController.setMovie(movie);
			SceneController.billingController.setMovie(movie);
			SceneController.overviewController.setMovie(movie);
			SceneController.successController.setMovie(movie);
			SceneController.switchToSchedule();
		});
		
		//Add tooltip to image
		String tooltipMessage = movie.title != null ? movie.title : "";
		if(tooltipMessage.length() > 0) {
			Tooltip details = new Tooltip(tooltipMessage);
			Tooltip.install(poster, details);
		}
	}

	private double prefWidth;
	private double minWidth;
	public void hide() {
		if (!hidden) {
			minWidth = column.columnConstraints.getMinWidth();
			prefWidth = column.columnConstraints.getPrefWidth();
			column.columnConstraints.setMinWidth(0);
			column.columnConstraints.setPrefWidth(0);
			poster.setVisible(false);
			label.setVisible(false);
			button.setVisible(false);
			hidden = true;
		}
	}
	
	public void show() {
		if(hidden) {
			column.columnConstraints.setMinWidth(minWidth);
			column.columnConstraints.setPrefWidth(prefWidth);
			poster.setVisible(true);
			label.setVisible(true);
			button.setVisible(true);
			hidden = false;
		}
	}
	
	public void addToGridPane(GridPane gridPane) {

		//Create column and add it to gridpane
		column = new ListColumn(gridPane);
		
		//Add poster wrapper and poster to column
		//The poster doesn't necessarily need to be inside the poster wrapper
		int currentColumnLength = gridPane.getColumnCount();
		int index = currentColumnLength - 1;
		column.gridPane.addColumn(index, posterWrapper);
		button.setMaxWidth(300);
		column.gridPane.add(poster, index, 0);
		column.gridPane.add(label, index, 1);
		column.gridPane.add(button, index, 2);
	}
}
