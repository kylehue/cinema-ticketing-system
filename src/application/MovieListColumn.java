package application;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;

public class MovieListColumn {
	public ColumnConstraints columnConstraints;
	public GridPane gridPane;
	
	public MovieListColumn(GridPane gridPane) {
		this.gridPane = gridPane;
		this.columnConstraints = new ColumnConstraints();
		
		columnConstraints.setFillWidth(true);
		columnConstraints.setPrefWidth(150);
		columnConstraints.setMinWidth(Region.USE_PREF_SIZE);
		columnConstraints.setMaxWidth(Region.USE_PREF_SIZE);
		columnConstraints.setPercentWidth(-1);
		columnConstraints.setHalignment(HPos.CENTER);
		this.gridPane.getColumnConstraints().add(this.columnConstraints);
	}
}
