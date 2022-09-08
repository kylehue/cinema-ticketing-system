package components;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;

public class ListColumn {
	public ColumnConstraints columnConstraints;
	public GridPane gridPane;
	
	private void initialize() {
		this.columnConstraints = new ColumnConstraints();
		columnConstraints.setFillWidth(true);
		columnConstraints.setPrefWidth(150);
		columnConstraints.setMinWidth(Region.USE_PREF_SIZE);
		columnConstraints.setMaxWidth(Region.USE_PREF_SIZE);
		columnConstraints.setPercentWidth(-1);
		columnConstraints.setHalignment(HPos.CENTER);
		columnConstraints.setHgrow(Priority.ALWAYS);
	}

	public ListColumn(GridPane gridPane) {
		this.gridPane = gridPane;
		initialize();
		this.gridPane.getColumnConstraints().add(this.columnConstraints);
	}
	
	public ListColumn(GridPane gridPane, double prefWidth) {
		this.gridPane = gridPane;
		initialize();
		columnConstraints.setPrefWidth(prefWidth);
		this.gridPane.getColumnConstraints().add(this.columnConstraints);
	}
}
