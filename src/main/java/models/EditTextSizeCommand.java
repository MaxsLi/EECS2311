package models;

import controllers.ShapeSceneController;

public class EditTextSizeCommand implements Command {

	private final ShapeSceneController shapesceneController;
	private final Location location;
	private final double oldSize;
	private final double newSize;

	public EditTextSizeCommand(ShapeSceneController shapesceneController, Location location, double oldSize, double newSize) {
		this.shapesceneController = shapesceneController;
		this.location = location;
		this.oldSize = oldSize;
		this.newSize = newSize;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		shapesceneController.changeTextSize(newSize, location);
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		shapesceneController.changeTextSize(oldSize, location);

	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		execute();
	}
}
