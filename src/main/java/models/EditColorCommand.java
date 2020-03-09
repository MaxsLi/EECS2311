package models;

import controllers.ShapeSceneController;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class EditColorCommand implements Command {

	private ShapeSceneController shapesceneController;
	private Circle circle;
	private Paint oldColor;
	private Paint newColor;
	public EditColorCommand(ShapeSceneController shapeSceneController, Circle circle, Paint oldColor, Paint newColor) {
		this.shapesceneController=shapeSceneController;
		this.circle=circle;
		this.oldColor=oldColor;
		this.newColor=newColor;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		shapesceneController.setCircleColor(circle, newColor);
	}
	@Override
	public void undo() {
		// TODO Auto-generated method stub
		shapesceneController.setCircleColor(circle, oldColor);
		
	}
	@Override
	public void redo() {
		// TODO Auto-generated method stub
		execute();
	}
}
