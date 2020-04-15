package tests;

import javafx.scene.control.Button;
import views.MainApp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controllers.TestModeController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

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
	
	@Test
	void testTestModeLoadedCorrectElements() {
		TestModeController tmc = new TestModeController();
		File testFile = new File("threeCirclestest.txt");
		assertTrue(tmc.readAndParseTXT(testFile) != TestModeController.NO_ELEMENTS_IMPORTED);
		
	}

}
