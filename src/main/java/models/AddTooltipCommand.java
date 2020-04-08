package models;

import controllers.ShapeSceneController;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class AddTooltipCommand implements Command {

	private String text;
	private TextField textField;
	private ShapeSceneController shapeSceneController;
	
	public AddTooltipCommand(ShapeSceneController shapeSceneController, String text, TextField textField) {
		// TODO Auto-generated constructor stub
		this.text=text;
		this.textField=textField;
		this.shapeSceneController=shapeSceneController;
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		shapeSceneController.addTooltip(textField, new Tooltip(text));
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		shapeSceneController.removeTooltip(textField);
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		execute();
	}

}
