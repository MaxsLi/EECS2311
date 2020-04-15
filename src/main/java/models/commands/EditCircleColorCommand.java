package models.commands;

import controllers.ShapeSceneController;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class EditCircleColorCommand implements Command {

	private final ShapeSceneController shapesceneController;
	private Circle circle;
	private Paint oldColor;
	private Paint newColor;

	public EditCircleColorCommand(ShapeSceneController shapeSceneController, Circle circle, Paint oldColor, Paint newColor) {
		this.shapesceneController = shapeSceneController;
		this.circle = circle;
		this.oldColor = oldColor;
		this.newColor = newColor;
	}

	public EditCircleColorCommand(ShapeSceneController shapesceneController) {
		this.shapesceneController = shapesceneController;
	}

	public void setCircle(Circle circle) {
		this.circle = circle;
	}

	public void setOldColor(Paint oldColor) {
		this.oldColor = oldColor;
	}


	public void setNewColor(Paint newColor) {
		this.newColor = newColor;
	}

	@Override
	public void execute() {
		shapesceneController.changeCircleColor(newColor, circle);
	}

	@Override
	public void undo() {
		shapesceneController.changeCircleColor(oldColor, circle);

	}

	@Override
	public void redo() {
		execute();
	}
}
