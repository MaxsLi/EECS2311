package models.commands;

import controllers.ShapeSceneController;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class DeleteAllCommand implements Command {

	private final ShapeSceneController shapeSceneController;
	private ArrayList<DeleteCommand> deleteCommands;

	public DeleteAllCommand(ShapeSceneController shapeSceneController) {
		this.shapeSceneController = shapeSceneController;

	}

	public void setTextFields(ArrayList<TextField> textFields) {
		deleteCommands = new ArrayList<>();
		for (TextField textField : textFields) {
			deleteCommands.add(new DeleteCommand(shapeSceneController, textField, textField.getTranslateX(), textField.getTranslateY()));
		}
	}

	@Override
	public void execute() {
		for (DeleteCommand deleteCommand : deleteCommands) {
			deleteCommand.execute();
		}
	}

	@Override
	public void undo() {
		for (DeleteCommand deleteCommand : deleteCommands) {
			deleteCommand.undo();
		}
	}

	@Override
	public void redo() {
		execute();
	}

}
