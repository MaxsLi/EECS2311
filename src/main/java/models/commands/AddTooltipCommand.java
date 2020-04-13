package models.commands;

import controllers.ShapeSceneController;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class AddTooltipCommand implements Command {

	private final String text;
	private final TextField textField;
	private final ShapeSceneController shapeSceneController;

	public AddTooltipCommand(ShapeSceneController shapeSceneController, String text, TextField textField) {
		this.text = text;
		this.textField = textField;
		this.shapeSceneController = shapeSceneController;
	}

	@Override
	public void execute() {
		shapeSceneController.addTooltip(textField, new Tooltip(text));
	}

	@Override
	public void undo() {
		shapeSceneController.removeTooltip(textField);
	}

	@Override
	public void redo() {
		execute();
	}

}
