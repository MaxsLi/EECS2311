package models;

import controllers.ShapeSceneController;
import javafx.geometry.Point2D;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class VennSet extends ArrayList<TextField> {

	private final VennShape vennShape;

	public VennSet(VennShape vennShape) {
		this.vennShape = vennShape;
	}

	public Location getLocation(TextField textField) throws Exception {
		Circle leftCircle = (Circle) this.vennShape.getLeftShape();
		Circle rightCircle = (Circle) this.vennShape.getRightShape();
		Circle extraCircle;


		Point2D leftCenter = leftCircle.localToParent(leftCircle.getCenterX(), leftCircle.getCenterY());
		Point2D rightCenter = rightCircle.localToParent(rightCircle.getCenterX(), rightCircle.getCenterY());
		Point2D extraCenter;


		double leftRadius = leftCircle.getRadius();
		double rightRadius = rightCircle.getRadius();
		double extraCircleRadius = 0;

		Point2D textFieldLocation = textField.localToParent(textField.getScene().getX(),
				textField.getScene().getY());

		double distanceToLeft = textFieldLocation.distance(leftCenter);
		double distanceToRight = textFieldLocation.distance(rightCenter);
		double distanceToBottom = 0;


		if (ShapeSceneController.EXTRA_CIRCLE_ADDED) {
			extraCircle = (Circle) this.vennShape.getBottomShape();
			extraCenter = extraCircle.localToParent(extraCircle.getCenterX(), extraCircle.getCenterY());
			extraCircleRadius = extraCircle.getRadius();
			distanceToBottom = textFieldLocation.distance(extraCenter);
		}


		if (ShapeSceneController.EXTRA_CIRCLE_ADDED &&
				distanceToBottom <= extraCircleRadius && distanceToLeft <= leftRadius && distanceToRight <= rightRadius) {
			return Location.INTERSECTING_ALL;
		} else if (ShapeSceneController.EXTRA_CIRCLE_ADDED &&
				distanceToBottom <= extraCircleRadius && distanceToLeft <= leftRadius) {
			return Location.INTERSECTING_BOTTOM_LEFT;
		} else if (ShapeSceneController.EXTRA_CIRCLE_ADDED &&
				distanceToBottom <= extraCircleRadius && distanceToRight <= rightRadius) {
			return Location.INTERSECTING_BOTTOM_RIGHT;
		} else if (ShapeSceneController.EXTRA_CIRCLE_ADDED &&
				distanceToLeft <= leftRadius && distanceToRight <= rightRadius) {
			return Location.INTERSECTING_LEFT_RIGHT;
		}
		/*
		 * If TextField location is within radial distance of the left and right circle,
		 * it must be somewhere in the intersection of the two circles
		 */
		else if (distanceToLeft <= leftRadius && distanceToRight <= rightRadius) {
			return Location.INTERSECTING_LEFT_RIGHT;
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
		} else if (ShapeSceneController.EXTRA_CIRCLE_ADDED) {
			return Location.BOTTOM;
		} else {
			throw new Exception("Invalid location");
		}
	}

}