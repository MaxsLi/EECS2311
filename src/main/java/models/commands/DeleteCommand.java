package models.commands;

import controllers.ShapeSceneController;
import javafx.scene.control.TextField;

public class DeleteCommand implements Command {

	private final ShapeSceneController shapesceneController;
	private final TextField textField;
	private final double posX;
	private final double posY;

	public DeleteCommand(ShapeSceneController shapeSceneController, TextField textField, double posX, double posY) {
		this.shapesceneController = shapeSceneController;
		this.textField = textField;
		this.posX = posX;
		this.posY = posY;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		shapesceneController.deleteSpecficText(textField);
		shapesceneController.eraseItem(textField.getText());
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		shapesceneController.addTextField(textField.getText(), textField, posX, posY);

	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		execute();
	}
}
