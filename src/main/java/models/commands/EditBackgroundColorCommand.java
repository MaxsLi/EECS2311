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
		// TODO Auto-generated method stub
		shapesceneController.setBackgrondColor(newStyle);
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		shapesceneController.setBackgrondColor(oldStyle);
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		execute();
	}

}
