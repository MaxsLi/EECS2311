package models.commands;

import controllers.ShapeSceneController;
import javafx.scene.control.TextField;

public class AddCommand implements Command {

	private final ShapeSceneController shapesceneController;
	private final TextField textField;

	public AddCommand(ShapeSceneController shapeSceneController, TextField textField) {
		this.shapesceneController = shapeSceneController;
		this.textField = textField;
	}

	@Override
	public void execute() {
		shapesceneController.addTextField(textField.getText(), textField);
	}

	@Override
	public void undo() {
		shapesceneController.deleteSpecficText(textField);
		shapesceneController.eraseItem(textField.getText());
	}

	@Override
	public void redo() {
		execute();
	}
}
