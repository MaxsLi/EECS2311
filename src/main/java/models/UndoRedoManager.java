package models;

import java.util.Stack;

public class UndoRedoManager {

	private Stack<Command> undoStack;
	private Stack<Command> redoStack;
	
	public UndoRedoManager() {
		// TODO Auto-generated constructor stub
		this.undoStack=new Stack<>();
		this.redoStack=new Stack<>();
	}
	
	public void addCommand(Command a) {
		
		undoStack.push(a);
		redoStack.clear();
		
	}
	
	public void undo() {
		
		Command a=undoStack.pop();
		redoStack.push(a);
		a.undo();
	}
	public void redo() {
	
		Command a=redoStack.pop();
		undoStack.push(a);
		a.redo();
	}
}
