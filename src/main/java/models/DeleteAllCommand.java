package models;

import java.util.ArrayList;

import controllers.ShapeSceneController;
import javafx.scene.control.TextField;

public class DeleteAllCommand implements Command{

	private ShapeSceneController shapeSceneController;
	private ArrayList<DeleteCommand> deleteCommands;
	
	public DeleteAllCommand(ShapeSceneController shapeSceneController) {
		// TODO Auto-generated constructor stub
		this.shapeSceneController=shapeSceneController;
		
	}
	public void setTextFields(ArrayList<TextField> textFields) {
		deleteCommands=new ArrayList<>();
		for (TextField textField : textFields) {
			deleteCommands.add(new DeleteCommand(shapeSceneController, textField, textField.getTranslateX(), textField.getTranslateY()));
		}
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		for (DeleteCommand deleteCommand : deleteCommands) {
			deleteCommand.execute();
		}
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		for (DeleteCommand deleteCommand : deleteCommands) {
			deleteCommand.undo();
		}
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		execute();
	}

}
