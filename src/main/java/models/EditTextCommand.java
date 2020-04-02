package models;

import controllers.ShapeSceneController;
import javafx.scene.control.TextField;

public class EditTextCommand implements Command {

	private ShapeSceneController shapesceneController;
	private TextField textField;
	private String oldText;
	private String newText;
	public EditTextCommand(ShapeSceneController shapeSceneController, TextField textField, String oldText) {
		this.shapesceneController=shapeSceneController;
		this.textField=textField;
		this.oldText=oldText;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
//		shapesceneController.setText(textField, newText);
	}
	@Override
	public void undo() {
		// TODO Auto-generated method stub
		this.newText=textField.getText();
//		shapesceneController.setText(textField, oldText);
		
	}
	@Override
	public void redo() {
		// TODO Auto-generated method stub
		execute();
	}
}
