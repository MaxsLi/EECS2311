package tests;


import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MenuBarTest extends MainTest {

	@BeforeEach
	public void setUp() throws Exception {
		launchApplication();
		enterShapeScene();
	}

	@AfterEach
	public void tearDown() throws Exception {
		exitApplication();
	}

	@Test
	public void newVennDiagramTest() {
		moveBy(-430, -450);
		clickOn(MouseButton.PRIMARY);
		moveBy(0, 20);
		clickOn(MouseButton.PRIMARY);
		type(KeyCode.SPACE);
		assertEquals(((TextField) find("#appTitle")).getText(), "");
	}

	@Test
	public void addCircleTest() {
		moveBy(-360, -450);
		clickOn(MouseButton.PRIMARY);
		moveBy(0, 20);
		clickOn(MouseButton.PRIMARY);
		type(KeyCode.SPACE);
		assertEquals(((StackPane) find("#stackPane")).getChildren().size(), 3);
	}

}
