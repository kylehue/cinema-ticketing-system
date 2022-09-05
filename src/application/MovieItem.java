package application;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class MovieItem {
	public Button button;
	public ImageView poster;
	public String title;
	public MovieListColumn column;
	public Pane posterWrapper;
	
	public boolean hidden = false;
	
	public MovieItem(String posterURL) {
		initialize(posterURL);
	}
	
	public MovieItem(String posterURL, String title) {
		this.title = title;
		initialize(posterURL);
	}
	
	private void initialize(String posterURL) {
		//Create wrapper for poster
		posterWrapper = new Pane();
		//posterWrapper.setStyle("-fx-background-color:#111;");
		//gridPane.setGridLinesVisible(true);
		
		//Create image for poster
		poster = new ImageView(new Image(posterURL));
		Utils.cornerRadius(poster, 40);
		
		//We only added poster wrapper so we can have its size as the basis of poster's size
		//Why? because ImageView only allows specific size values
		poster.setPreserveRatio(true);
		poster.fitWidthProperty().bind(posterWrapper.widthProperty());
		poster.fitHeightProperty().bind(posterWrapper.heightProperty());
		
		//Create "buy ticket" button
		button = new Button("Buy ticket");
		button.getStyleClass().add("button-primary");
		
		//Add tooltip to image
		String tooltipMessage = title != null ? title : "";
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
			button.setVisible(false);
			poster.setDisable(true);
			button.setDisable(true);
			hidden = true;
		}
	}
	
	public void show() {
		if(hidden) {
			column.columnConstraints.setMinWidth(minWidth);
			column.columnConstraints.setPrefWidth(prefWidth);
			poster.setVisible(true);
			button.setVisible(true);
			poster.setDisable(false);
			button.setDisable(false);
			hidden = false;
		}
	}
	
	public void addToGridPane(GridPane gridPane) {

		//Create column and add it to gridpane
		column = new MovieListColumn(gridPane);
		
		//Add poster wrapper and poster to column
		//The poster doesn't necessarily need to be inside the poster wrapper
		int currentColumnLength = gridPane.getColumnCount();
		int index = currentColumnLength - 1;
		column.gridPane.addColumn(index, posterWrapper);
		button.setMaxWidth(300);
		column.gridPane.add(poster, index, 0);
		column.gridPane.add(button, index, 1);
	}
}
