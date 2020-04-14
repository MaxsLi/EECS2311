package models.commands;

import controllers.ShapeSceneController;

public class AddCircleCommand implements Command {

	private final ShapeSceneController shapeSceneController;

	public AddCircleCommand(ShapeSceneController shapeSceneController) {
		this.shapeSceneController = shapeSceneController;
	}

	@Override
	public void execute() {
		shapeSceneController.addThirdCircle();
	}

	@Override
	public void undo() {
		shapeSceneController.deleteExtraCircle();
	}

	@Override
	public void redo() {
		execute();
	}

}