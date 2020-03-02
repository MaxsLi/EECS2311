package models;

import javafx.geometry.Point2D;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class VennSet extends ArrayList<TextField> {

	private VennShape vennShape;

	public VennSet(VennShape vennShape) {
		this.vennShape = vennShape;
	}

	public Location getLocation(TextField textField) throws Exception {
		Circle leftCircle = (Circle)this.vennShape.getLeftShape();
		Circle rightCircle = (Circle)this.vennShape.getRightShape();

		Point2D leftCenter = leftCircle.localToParent(leftCircle.getCenterX(), leftCircle.getCenterY());
		Point2D rightCenter = rightCircle.localToParent(rightCircle.getCenterX(), rightCircle.getCenterY());

		double leftRadius = leftCircle.getRadius();
		double rightRadius = rightCircle.getRadius();

		Point2D textFieldLocation = textField.localToParent(textField.getScene().getX(),
				textField.getScene().getY());

		double distanceToLeft = textFieldLocation.distance(leftCenter);
		double distanceToRight = textFieldLocation.distance(rightCenter);

		/*
		 * If TextField location is within radial distance of the left and right circle,
		 * it must be somewhere in the intersection of the two circles
		 */
		if (distanceToLeft <= leftRadius && distanceToRight <= rightRadius) {
			return Location.MIDDLE;
		}
		/*
		 * Else if, if its within radial distance of the left Circle, it must be in the
		 * left circle
		 */
		else if (distanceToLeft <= leftRadius) {
			return Location.LEFT;
		}
		/*
		 * Else if, if its within radial distance of the left Circle, it must be in the
		 * right circle
		 */
		else if (distanceToRight <= rightRadius) {
			return Location.RIGHT;
		}

		else {
			throw new Exception("Invalid location");
		}
	}

}