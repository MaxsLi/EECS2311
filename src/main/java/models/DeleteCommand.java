package models;

import controllers.ShapeSceneController;
import javafx.scene.control.TextField;

public class DeleteCommand implements Command {

	private ShapeSceneController shapesceneController;
	private TextField textField;
	public DeleteCommand(ShapeSceneController shapeSceneController, TextField textField) {
		this.shapesceneController=shapeSceneController;
		this.textField=textField;
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		shapesceneController.deleteSpecficText(textField);
	}
	@Override
	public void undo() {
		// TODO Auto-generated method stub
		shapesceneController.addTextField(textField.getText(),textField);
	}
	@Override
	public void redo() {
		// TODO Auto-generated method stub
		execute();
	}
}
