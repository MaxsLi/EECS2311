package models.commands;

import controllers.ShapeSceneController;
import javafx.scene.control.TextField;

public class DragCommand implements Command {

	private final ShapeSceneController shapesceneController;
	private final TextField textField;
	private final double origPosX;
	private final double origPosY;
	private double offsetX;
	private double offsetY;

	public DragCommand(ShapeSceneController shapeSceneController, TextField textField, double origPosX, double origPosY) {
		this.shapesceneController = shapeSceneController;
		this.textField = textField;
		this.origPosX = origPosX;
		this.origPosY = origPosY;
	}

	public void setOffsetX(double offsetX) {
		this.offsetX = offsetX;
	}

	public void setOffsetY(double offsetY) {
		this.offsetY = offsetY;
	}

	@Override
	public void execute() {
		shapesceneController.moveTextField(textField, offsetX, offsetY);
	}

	@Override
	public void undo() {
		shapesceneController.moveTextField(textField, origPosX, origPosY);
		shapesceneController.getLocation(textField);
	}

	@Override
	public void redo() {
		execute();
	}
}
