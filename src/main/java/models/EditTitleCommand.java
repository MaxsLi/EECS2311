package models;

import controllers.ShapeSceneController;
import javafx.scene.control.TextField;

public class EditTitleCommand implements Command {

	
	private ShapeSceneController shapesceneController;
	private String oldColor;
	private String newColor;
	public EditTitleCommand(ShapeSceneController shapeSceneController, String oldColor, String newColor) {
		this.shapesceneController=shapeSceneController;
		this.oldColor=oldColor;
		this.newColor=newColor;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		shapesceneController.setTitleColor(newColor);
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		shapesceneController.setTitleColor(oldColor);
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		execute();
	}

}