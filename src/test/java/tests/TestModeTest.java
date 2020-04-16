package tests;

import javafx.scene.control.Button;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestModeTest extends MainTest {

	@BeforeEach
	protected void setUp() throws Exception {
		launchApplication();
		enterTestMode();
	}

	@AfterEach
	protected void tearDown() throws Exception {
		exitApplication();
	}

	@Test
	public void enterTestModeTest() {
		assertTrue(true);
	}

	@Test
	public void exitTestModeTest() {
		clickOn((Button) find("#exitTestBttn"));
		assertTrue(true);
	}

}
