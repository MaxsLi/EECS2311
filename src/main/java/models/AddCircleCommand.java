package models;

import controllers.ShapeSceneController;

public class AddCircleCommand implements Command {

	private ShapeSceneController shapeSceneController;
	public AddCircleCommand(ShapeSceneController shapeSceneController) {
		// TODO Auto-generated constructor stub
		this.shapeSceneController=shapeSceneController;
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		shapeSceneController.addCircle();
		
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		execute();
	}

}