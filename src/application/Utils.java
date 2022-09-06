package application;

import java.awt.Dimension;

import javafx.scene.SnapshotParameters;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import scenes.SceneController;

public class Utils {
	public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {
	    int original_width = imgSize.width;
	    int original_height = imgSize.height;
	    int bound_width = boundary.width;
	    int bound_height = boundary.height;
	    int new_width = original_width;
	    int new_height = original_height;

	    // first check if we need to scale width
	    if (original_width > bound_width) {
	        //scale width to fit
	        new_width = bound_width;
	        //scale height to maintain aspect ratio
	        new_height = (new_width * original_height) / original_width;
	    }

	    // then check if we need to scale even with the new height
	    if (new_height > bound_height) {
	        //scale height to fit instead
	        new_height = bound_height;
	        //scale width to maintain aspect ratio
	        new_width = (new_height * original_width) / original_height;
	    }

	    return new Dimension(new_width, new_height);
	}
	
	public static void coverImageView(ImageView img, ScrollPane wrapper) {
		double width = img.getBoundsInLocal().getWidth();
		double wrapperWidth = wrapper.getBoundsInLocal().getWidth();
		if (wrapperWidth >= width) {
			//Prioritize width
			img.fitHeightProperty().unbind();
			img.setFitHeight(Double.MAX_VALUE);
			img.fitWidthProperty().bind(wrapper.widthProperty());
		}
		
		double height = img.getBoundsInLocal().getHeight();
		double wrapperHeight = wrapper.getBoundsInLocal().getHeight();
		if (wrapperHeight >= height) {
			//Prioritize height
			img.fitWidthProperty().unbind();
			img.setFitWidth(Double.MAX_VALUE);
			img.fitHeightProperty().bind(wrapper.heightProperty());
		}
	}
	
	public static void wrapImageView(ImageView img, ScrollPane wrapper, GridPane mainContainer) {
		img.setPreserveRatio(true);
		img.setFitWidth(Double.MAX_VALUE);
		//img.setFitHeight(Double.MAX_VALUE);
		//img.fitWidthProperty().bind(wrapper.widthProperty());
		img.fitHeightProperty().bind(wrapper.heightProperty());
		
		SceneController.stage.widthProperty().addListener((obs, oldVal, newVal) -> {
			coverImageView(img, wrapper);
		});
		
		SceneController.stage.heightProperty().addListener((obs, oldVal, newVal) -> {
			coverImageView(img, wrapper);
		});

		wrapper.widthProperty().addListener((obs, oldVal, newVal) -> {
			coverImageView(img, wrapper);
		});
		
		wrapper.heightProperty().addListener((obs, oldVal, newVal) -> {
			coverImageView(img, wrapper);
		});

		mainContainer.widthProperty().addListener((obs, oldVal, newVal) -> {
			coverImageView(img, wrapper);
		});
		
		mainContainer.heightProperty().addListener((obs, oldVal, newVal) -> {
			coverImageView(img, wrapper);
		});
	}
	
	public static void cornerRadius(ImageView imageView, double radius) {
		// set a clip to apply rounded border to the original image.
		double width = imageView.getImage().getWidth();
		double height = imageView.getImage().getHeight();
        Dimension orig = new Dimension();
        orig.setSize(width, height);
        Dimension newSize = new Dimension();
        newSize.setSize(300, 444);
        Dimension dim = getScaledDimension(orig, newSize);
        Rectangle clip = new Rectangle(0, 0, dim.getWidth(), dim.getHeight());
        imageView.setFitWidth(dim.getWidth());
        imageView.setFitHeight(dim.getHeight());
        clip.setArcWidth(radius);
        clip.setArcHeight(radius);
        imageView.setClip(clip);

        // snapshot the rounded image.
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = imageView.snapshot(parameters, null);

        // remove the rounding clip so that our effect can show through.
        imageView.setClip(null);

        // store the rounded image in the imageView.
        imageView.setImage(image);
	}
}
