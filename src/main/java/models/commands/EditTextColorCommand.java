package models.commands;

import controllers.ShapeSceneController;
import models.Location;

public class EditTextColorCommand implements Command {

	private final ShapeSceneController shapesceneController;
	private final Location location;
	private final String oldColor;
	private final String newColor;

	public EditTextColorCommand(ShapeSceneController shapeSceneController, Location location, String oldColor, String newColor) {
		this.shapesceneController = shapeSceneController;
		this.location = location;
		this.oldColor = oldColor;
		this.newColor = newColor;
	}

	@Override
	public void execute() {
		shapesceneController.setTextColor(newColor, location);
	}

	@Override
	public void undo() {
		shapesceneController.setTextColor(oldColor, location);

	}

	@Override
	public void redo() {
		execute();
	}
}
