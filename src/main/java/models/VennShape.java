package models;

import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Arrays;

public class VennShape extends ArrayList<Shape> {

	public VennShape(Shape... shape) {
		super.addAll(Arrays.asList(shape));
	}

	public Shape getLeftShape() {
		return this.get(0);
	}

	public Shape getRightShape() {
		return this.get(1);
	}

	public Shape getBottomShape() {
		return this.get(2);
	}

}
