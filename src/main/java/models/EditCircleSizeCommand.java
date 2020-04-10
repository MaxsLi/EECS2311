package models;

import controllers.ShapeSceneController;
import javafx.scene.shape.Circle;

public class EditCircleSizeCommand implements Command{

	private ShapeSceneController shapesceneController;
	private Circle circle;
	private double oldSize;
	private double newSize;
	public EditCircleSizeCommand(ShapeSceneController shapeSceneController, Circle circle, double oldSize, double newSize) {
		this.shapesceneController=shapeSceneController;
		this.circle = circle;
		this.oldSize=oldSize;
		this.newSize=newSize;
	}
//
//	public void setOldSize(double oldSize) {
//		this.oldSize = oldSize;
//	}
//
//
//	public void setNewSize(double newSize) {
//		this.newSize = newSize;
//	}
//

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
