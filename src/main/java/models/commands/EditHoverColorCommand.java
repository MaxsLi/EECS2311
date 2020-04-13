package models.commands;

import controllers.ShapeSceneController;
import javafx.scene.shape.Circle;

public class EditHoverColorCommand implements Command {

	private final ShapeSceneController shapesceneController;
	private Circle circle;
	private String oldColor;
	private String newColor;

	public EditHoverColorCommand(ShapeSceneController shapeSceneController, Circle circle, String oldColor, String newColor) {
		this.shapesceneController = shapeSceneController;
		this.circle = circle;
		this.oldColor = oldColor;
		this.newColor = newColor;
	}

	public EditHoverColorCommand(ShapeSceneController shapesceneController) {
		this.shapesceneController = shapesceneController;
	}

	public void setCircle(Circle circle) {
		this.circle = circle;
	}

	public void setOldColor(String oldColor) {
		this.oldColor = oldColor;
	}

	public void setNewColor(String newColor) {
		this.newColor = newColor;
	}

	@Override
	public void execute() {
		shapesceneController.setCircleHover(newColor, circle);
	}

	@Override
	public void undo() {
		shapesceneController.setCircleHover(oldColor, circle);

	}

	@Override
	public void redo() {
		execute();
	}
}
