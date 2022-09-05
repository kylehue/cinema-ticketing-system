package application;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class GridRow {
	private RowConstraints row;
	private GridPane gridPane;
	
	public GridRow(GridPane gridPane) {
		this.gridPane = gridPane;
		this.row = new RowConstraints();
		
		row.setFillHeight(true);
		row.setMinHeight(0);
		row.setPrefHeight(222);
		row.setMaxHeight(252);
		row.setPercentHeight(-1);

		this.gridPane.getRowConstraints().add(this.row);
	}
}
