package tests;


import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import views.MainApp;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MainTest extends ApplicationTest {

	protected static final String TEST_INPUT_STRING = "Foo Bar";

	/* A shortcut to retrieve widgets in the GUI. */
	protected <T extends Node> T find(final String query) {
		/* TestFX provides many operations to retrieve elements from the loaded GUI. */
		return lookup(query).query();
	}

	protected void writeInputAndAssert(String input, TextField textField) {
		write(input);
		assertEquals(textField.getText(), input);
	}

	protected void enterShapeScene() {
		clickOn((Button) find("#createNewBttn"));
		while (!MainApp.primaryStage.isFocused()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ignored) {
			}
		}
	}

	protected void launchApplication() throws Exception {
		ApplicationTest.launch(MainApp.class);
	}

	protected void exitApplication() throws Exception {
		/* Close the window. It will be re-opened at the next test. */
		FxToolkit.hideStage();
		release(new KeyCode[]{});
		release(new MouseButton[]{});
	}
}
