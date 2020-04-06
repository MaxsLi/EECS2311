package models;

import controllers.ShapeSceneController;
import javafx.scene.control.TextField;

public class DragCommand implements Command{

	private ShapeSceneController shapesceneController;
	private TextField textField;
	private double origPosX;
	private double origPosY;
	private double offsetX;
	private double offsetY;
	public DragCommand(ShapeSceneController shapeSceneController, TextField textField, double origPosX, double origPosY) {
		this.shapesceneController=shapeSceneController;
		this.textField=textField;
		this.origPosX=origPosX;
		this.origPosY=origPosY;
	}
	public void setOffsetX(double offsetX) {
		this.offsetX = offsetX;
	}
	public void setOffsetY(double offsetY) {
		this.offsetY = offsetY;
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		shapesceneController.moveTextField(textField, offsetX, offsetY);
	}
	@Override
	public void undo() {
		// TODO Auto-generated method stub
		shapesceneController.moveTextField(textField, origPosX, origPosY);
	}
	@Override
	public void redo() {
		// TODO Auto-generated method stub
		execute();
//		shapesceneController.getLocation(textField);
	}
}
