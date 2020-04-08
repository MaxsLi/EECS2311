package models;

import controllers.ShapeSceneController;
import javafx.scene.shape.Circle;

public class EditCircleSizeCommand implements Command{

	private ShapeSceneController shapesceneController;
	private Circle circle;
	private double oldSize;
	private double newSize;
	public EditCircleSizeCommand(ShapeSceneController shapeSceneController) {
		this.shapesceneController=shapeSceneController;
	}


	public void setCircle(Circle circle) {
		this.circle = circle;
	}


	public void setOldSize(double oldSize) {
		this.oldSize = oldSize;
	}


	public void setNewSize(double newSize) {
		this.newSize = newSize;
	}


	@Override
	public void execute() {
		// TODO Auto-generated method stub
		shapesceneController.changeCircleSize(newSize, circle);
	}
	@Override
	public void undo() {
		// TODO Auto-generated method stub
		shapesceneController.changeCircleSize(oldSize, circle);
		
	}
	@Override
	public void redo() {
		// TODO Auto-generated method stub
		execute();
	}
}
