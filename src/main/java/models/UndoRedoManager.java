package models;

import java.util.Stack;

import controllers.ShapeSceneController;

public class UndoRedoManager {

	private Stack<Command> undoStack;
	private Stack<Command> redoStack;
	private ShapeSceneController shapeSceneController;
	public UndoRedoManager(ShapeSceneController shapeSceneController) {
		// TODO Auto-generated constructor stub
		this.undoStack=new Stack<>();
		this.redoStack=new Stack<>();
	    this.shapeSceneController=shapeSceneController;
	}
	
	public void addCommand(Command a) {
		undoStack.push(a);
		redoStack.clear();
		shapeSceneController.undoBtn(true);
		
	}
	
	public void undo() {
		
		Command a=undoStack.pop();
		redoStack.push(a);
		shapeSceneController.redoBtn(true);
		shapeSceneController.undoBtn(!(undoStack.isEmpty()));
		a.undo();
	}
	public void redo() {
	
		Command a=redoStack.pop();
		undoStack.push(a);
		shapeSceneController.undoBtn(true);
		shapeSceneController.redoBtn(!(redoStack.isEmpty()));
		a.redo();
		
	}
}
