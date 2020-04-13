package models.commands;

import controllers.ShapeSceneController;
import javafx.scene.shape.Circle;

public class EditCircleSizeCommand implements Command {

	private final ShapeSceneController shapesceneController;
	private final Circle circle;
	private final double oldSize;
	private final double newSize;

	public EditCircleSizeCommand(ShapeSceneController shapeSceneController, Circle circle, double oldSize, double newSize) {
		this.shapesceneController = shapeSceneController;
		this.circle = circle;
		this.oldSize = oldSize;
		this.newSize = newSize;
	}

	@Override
	public void execute() {
		shapesceneController.changeCircleSize(newSize, circle);
	}

	@Override
	public void undo() {
		shapesceneController.changeCircleSize(oldSize, circle);

	}

	@Override
	public void redo() {
		execute();
	}
}
