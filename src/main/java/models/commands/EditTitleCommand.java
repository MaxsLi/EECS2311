package models.commands;

import controllers.ShapeSceneController;

public class EditTitleCommand implements Command {


	private final ShapeSceneController shapesceneController;
	private final String oldColor;
	private final String newColor;

	public EditTitleCommand(ShapeSceneController shapeSceneController, String oldColor, String newColor) {
		this.shapesceneController = shapeSceneController;
		this.oldColor = oldColor;
		this.newColor = newColor;
	}

	@Override
	public void execute() {
		shapesceneController.setTitleColor(newColor);
	}

	@Override
	public void undo() {
		shapesceneController.setTitleColor(oldColor);
	}

	@Override
	public void redo() {
		execute();
	}

}