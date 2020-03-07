package models;

import controllers.ShapeSceneController;

public class AddCommand implements Command {

	private ShapeSceneController shapesceneController;
	private String text;
	public AddCommand(ShapeSceneController shapeSceneController, String text) {
		this.shapesceneController=shapeSceneController;
		this.text=text;
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		shapesceneController.addText(text);
	}
}
