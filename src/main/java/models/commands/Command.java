package models.commands;

public interface Command {

	void execute();

	void undo();

	void redo();
}
