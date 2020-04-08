package models;

import controllers.ShapeSceneController;
import javafx.scene.paint.Paint;

public class EditTextColorCommand implements Command {

	private ShapeSceneController shapesceneController;
	private Location location;
	private String oldColor;
	private String newColor;
	public EditTextColorCommand(ShapeSceneController shapeSceneController, Location location, String oldColor, String newColor) {
		this.shapesceneController=shapeSceneController;
		this.location=location;
		this.oldColor=oldColor;
		this.newColor=newColor;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		shapesceneController.setTextColor(newColor, location);
	}
	@Override
	public void undo() {
		// TODO Auto-generated method stub
		shapesceneController.setTextColor(oldColor, location);
		
	}
	@Override
	public void redo() {
		// TODO Auto-generated method stub
		execute();
	}
}
