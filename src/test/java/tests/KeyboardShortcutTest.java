package tests;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KeyboardShortcutTest extends MainTest {

	@BeforeEach
	public void setUp() throws Exception {
		launchApplication();
		enterShapeScene();
	}

	@AfterEach
	public void tearDown() throws Exception {
		exitApplication();
	}

	private void toggleNavBar() {
		ToggleButton toggleButton = find("#toggle");
		clickOn(toggleButton);
		while (toggleButton.isDisable()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ignored) {
			}
		}
	}


	private void addDiagramText() {
		toggleNavBar();
		clickOn((TextField) find("#diagramText"));
		writeInputAndAssert(TEST_INPUT_STRING, find("#diagramText"));
		clickOn((Button) find("#addBttn"));
	}


	@Test
	public void undoShortcutAddDiagramTextTest() {
		addDiagramText();
		press(KeyCode.CONTROL);
		type(KeyCode.Z);
		release(KeyCode.CONTROL);
		assertTrue(((ListView) find("#itemList")).getItems().isEmpty());
	}

	@Test
	public void redoShortcutAddDiagramTextTest() {
		undoShortcutAddDiagramTextTest();
		press(KeyCode.CONTROL);
		press(KeyCode.SHIFT);
		type(KeyCode.Y);
		release(KeyCode.SHIFT);
		release(KeyCode.CONTROL);
		assertFalse(((ListView) find("#itemList")).getItems().isEmpty());
	}

}
