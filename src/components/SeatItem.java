package components;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;

public class SeatItem {
	public String id;
	public boolean isActive = false;
	public Button button = new Button();
	
	public SeatItem(String id, double size) {
		this.id = id;
		button.setPrefSize(size, size);
		button.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		button.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		button.getStyleClass().addAll("seat", "seat-button");
		
		Tooltip tooltip = new Tooltip();
		tooltip.setText(id);
		Tooltip.install(button, tooltip);
	}
}
