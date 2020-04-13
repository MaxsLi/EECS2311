package models.commands;

import controllers.ShapeSceneController;

public class EditBackgroundColorCommand implements Command {


	private final ShapeSceneController shapesceneController;
	private final String oldStyle;
	private final String newStyle;

	public EditBackgroundColorCommand(ShapeSceneController shapeSceneController, String oldStyle, String newStyle) {
		this.shapesceneController = shapeSceneController;
		this.oldStyle = oldStyle;
		this.newStyle = newStyle;
	}

	@Override
	public void execute() {
		shapesceneController.setBackgrondColor(newStyle);
	}

	@Override
	public void undo() {
		shapesceneController.setBackgrondColor(oldStyle);
	}

	@Override
	public void redo() {
		execute();
	}

}
