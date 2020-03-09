package models;

import controllers.ShapeSceneController;
import javafx.scene.control.TextField;

public class RemoveCommand implements Command {

	private ShapeSceneController shapesceneController;
	private TextField textField;
	public RemoveCommand(ShapeSceneController shapeSceneController, TextField textField) {
		this.shapesceneController=shapeSceneController;
		this.textField=textField;
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		shapesceneController.removeTextField(textField);
	}
	@Override
	public void undo() {
		// TODO Auto-generated method stub
		shapesceneController.addText(textField);
		
	}
	@Override
	public void redo() {
		// TODO Auto-generated method stub
		execute();
	}
}
