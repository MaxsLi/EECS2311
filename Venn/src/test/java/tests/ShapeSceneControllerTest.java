package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import views.MainApp;

class ShapeSceneControllerTest extends ApplicationTest {

	@BeforeEach
	public void setUpClass() throws Exception {
		FxToolkit.registerPrimaryStage();
		FxToolkit.setupApplication(MainApp.class);
	}

	@AfterEach
	public void cleanUp() throws TimeoutException {
		 FxToolkit.cleanupStages();
		 FxToolkit.hideStage();
	}

	@Test
	public void testCreateNew() {
		//when
		clickOn(".createNewBttn");
	}


}
