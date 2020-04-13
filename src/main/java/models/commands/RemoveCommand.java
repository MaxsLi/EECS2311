package models.commands;

import controllers.ShapeSceneController;
import javafx.scene.control.TextField;

public class RemoveCommand implements Command {

	private final ShapeSceneController shapesceneController;
	private final TextField textField;

	public RemoveCommand(ShapeSceneController shapeSceneController, TextField textField) {
		this.shapesceneController = shapeSceneController;
		this.textField = textField;
	}

	@Override
	public void execute() {
//		shapesceneController.removeTextField(textField);
	}

	@Override
	public void undo() {
//		shapesceneController.addText(textField);
	}

	@Override
	public void redo() {
		execute();
	}
}
