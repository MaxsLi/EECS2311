package models.commands;

import controllers.ShapeSceneController;
import javafx.scene.control.TextField;

public class EditTextCommand implements Command {

	private final ShapeSceneController shapesceneController;
	private final TextField textField;
	private final String oldText;
	private String newText;

	public EditTextCommand(ShapeSceneController shapeSceneController, TextField textField, String oldText) {
		this.shapesceneController = shapeSceneController;
		this.textField = textField;
		this.oldText = oldText;
	}

	@Override
	public void execute() {
		shapesceneController.setText(textField, newText);
	}

	@Override
	public void undo() {
		this.newText = textField.getText();
		shapesceneController.setText(textField, oldText);

	}

	@Override
	public void redo() {
		execute();
	}
}
