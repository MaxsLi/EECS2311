package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import controllers.ShapeSceneController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.VerticalDirection;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import models.VennSet;
import models.VennShape;
import views.MainApp;

class ShapeSceneControllerTest extends ApplicationTest {

	@FXML
	public Label sideLabel;

	@FXML
	public AnchorPane mainScene;

	@FXML
	public StackPane stackPane;

	@FXML
	public Circle leftCircle;

	@FXML
	public Circle rightCircle;

	@FXML
	public Button addBttn;

	@FXML
	public Button saveBttn;

	@FXML
	public Button removeButton;

	@FXML
	public TextField diagramText;

	@FXML
	public TextField appTitle;

	@FXML
	public ColorPicker leftColorPicker;

	@FXML
	public ColorPicker rightColorPicker;

	@FXML
	public ContextMenu textFieldContext;

	@FXML
	public TextField sideAdded;

	public MainApp mainApp;

	public String currentFileName;

	@FXML
	public TextField leftTitle;

	@FXML
	public TextField rightTitle;

	/**
	 * An Set of `TextLabel`s
	 */
	public VennSet vennSet;

	public Parent shapeScene;

	/**
	 * An Set of `Shape`s
	 */
	public VennShape vennShape;
	
	ShapeSceneController controller;
	
	public TextField randomTF;
	
//	@BeforeEach
//	public void setUpClass() throws Exception {
//		ApplicationTest.launch(MainApp.class);
//	}
	
	@BeforeEach
	public void setUpClass() throws Exception {
		ApplicationTest.launch(ShapeSceneMain.class);
	}
	
	/* This operation comes from ApplicationTest and loads the GUI to test. */
	@Override
	public void start(Stage primaryStage) throws IOException {
		  Parent shapeScene = FXMLLoader.load(getClass().getResource("shapeScene.fxml"));
		  primaryStage.setScene(new Scene(shapeScene));
		  primaryStage.show();
		  primaryStage.toFront();
	}


    /* Just a shortcut to retrieve widgets in the GUI. */
    public <T extends Node> T find(final String query) {
        /** TestFX provides many operations to retrieve elements from the loaded GUI. */
        return lookup(query).query();
    }

	@BeforeEach
	public void setUp() throws Exception {
		diagramText = find("#diagramText");
		appTitle = find("#appTitle");
		leftTitle = find("#leftTitle");
		rightTitle = find("#rightTitle");
		leftCircle = find("#leftCircle");
		sideAdded = find("#sideAdded");
		stackPane = find("#stackPane");
		rightCircle = find("#rightCircle");
		leftColorPicker = find("#leftColorPicker");
		rightColorPicker = find("#rightColorPicker");
		
	}

	@AfterEach
	public void tearDown() throws Exception {
		/* Close the window. It will be re-opened at the next test. */
		FxToolkit.hideStage();
		release(new KeyCode[] {});
		release(new MouseButton[] {});
	}

	@Test
	public void bottomTextFieldTest() throws InterruptedException {
		clickOn("#diagramText");
		write("Stop it");
		assertEquals(diagramText.getText(), "Stop it");
	}

	@Test
	public void test_appTitle() throws InterruptedException {
		clickOn("#appTitle");
		write("This is my Title");
		assertEquals(appTitle.getText(), "This is my Title");
	}
	
	@Test
	public void changeLeftColor() throws InterruptedException {
		clickOn("#leftColorPicker").type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.ENTER);
		assertEquals(leftCircle.getFill(), Color.valueOf("#ffffb3"));
	}
	
	@Test
	public void changeRightColor() throws InterruptedException {
		clickOn("#rightColorPicker").type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.UP).type(KeyCode.UP).type(KeyCode.UP).type(KeyCode.UP).type(KeyCode.ENTER);
		assertEquals(rightCircle.getFill(), Color.valueOf("#ffb366"));
	}
	
	@Test
	public void addTextToDiagramTest() throws InterruptedException {
		clickOn("#diagramText");
		write("LOOOL");
		type(KeyCode.ENTER);
		
	
	}
	
	@Test
	public void testPopup() throws InterruptedException {
		clickOn("#diagramText");
		write("Good Test Mate");
		type(KeyCode.ENTER);
		assertEquals(stackPane.getChildren().size(), 3);
	
	}
	
	@Test
	public void changeColor_2() throws InterruptedException {
		clickOn("#leftColorPicker").type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.UP).type(KeyCode.UP).type(KeyCode.UP).type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.ENTER);
	
	}
	
	
	@Test
	public void changeColor_3() throws InterruptedException {
		clickOn("#leftColorPicker").type(KeyCode.LEFT).type(KeyCode.RIGHT).type(KeyCode.DOWN).type(KeyCode.UP).type(KeyCode.UP).type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.ENTER);
	
	}
	
	@Test
	public void testDrag_03() throws Exception {
		clickOn("#diagramText");
		write("This is a Junit Test");
		type(KeyCode.ENTER);	
	
	}
	
	@Test
	public void testDrag_04() throws Exception {
		clickOn("#diagramText");
		write("This is a Junit Test LOOOOOL");
		type(KeyCode.ENTER);
		Node tf = stackPane.getChildren().get(2);
		
		tf.setTranslateX(-174);
		Thread.sleep(1000);
		clickOn(leftCircle);
		Thread.sleep(1000);
	
	}
	
	@Test
	public void testDrag_05() throws Exception {
		clickOn("#diagramText");
		write("This is a Junit Test SIDE ADDED YA");
		type(KeyCode.ENTER);
		Node tf = stackPane.getChildren().get(2);
		
		tf.setTranslateX(-174);
		Thread.sleep(1000);
		clickOn(leftCircle);
		Thread.sleep(1000);
		assertEquals(sideAdded.getText(), "Left!");
	
	}
	
	@Test
	public void testDrag_06() throws Exception {
		clickOn("#diagramText");
		write("This is a Junit Test SIDE ADDED YA");
		type(KeyCode.ENTER);
		Node tf = stackPane.getChildren().get(2);
		
		tf.setTranslateX(-174);
		Thread.sleep(1000);
		clickOn(leftCircle).drag(MouseButton.PRIMARY).dropTo(rightCircle);
		Thread.sleep(1000);
		
	
	}
	
	@Test
	public void testDrag_07() throws Exception {
		clickOn("#diagramText");
		write("This is a Junit Test SIDE ADDED YA");
		type(KeyCode.ENTER);
		Node tf = stackPane.getChildren().get(2);
		
		tf.setTranslateX(-174);
		Thread.sleep(1000);
		clickOn(leftCircle).drag(MouseButton.PRIMARY).dropTo(rightCircle);
		Thread.sleep(1000);
		assertEquals(sideAdded.getText(), "Intersection!");
	}
	
	@Test
	public void testLeftTitle1() throws Exception {
		clickOn("#leftTitle");
		write("Benefits");
		
		assertEquals(leftTitle.getText(), "Benefits");
		
	
		
	}
	
	@Test
	public void testRightTitle1() throws Exception {
		clickOn("#rightTitle");
		write("Doubts");
		
		assertEquals(rightTitle.getText(), "Doubts");
	
		
	}
	
}
