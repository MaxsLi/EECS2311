package tests;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContextMenuTest extends MainTest {

	private ListView itemList;
	private Button undoBttn;
	private Button redoBttn;

	private void initializeVariables() {
		itemList = find("#itemList");
		undoBttn = find("#undoBttn");
		redoBttn = find("#redoBttn");
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

	private void dragToLeftCircleTest() {
		addDiagramText();
		Node textFiled = ((StackPane) find("#stackPane")).getChildren().get(2);
		clickOn(textFiled);
		type(KeyCode.TAB);
		type(KeyCode.SPACE);
		clickOn(textFiled).drag(MouseButton.PRIMARY).moveBy(300, 300);
		release(MouseButton.PRIMARY);
	}

	@BeforeEach
	public void setUp() throws Exception {
		launchApplication();
		enterShapeScene();
		initializeVariables();
	}

	@AfterEach
	public void tearDown() throws Exception {
		exitApplication();
	}

	@Test
	public void deleteDiagramTextTest() {
		dragToLeftCircleTest();
		clickOn(MouseButton.SECONDARY);
		moveBy(20, 20);
		clickOn(MouseButton.PRIMARY);
		assertTrue(itemList.getItems().isEmpty());
	}

	@Test
	public void editDiagramTextTest() {
		dragToLeftCircleTest();
		clickOn(MouseButton.SECONDARY);
		moveBy(20, 40);
		clickOn(MouseButton.PRIMARY);
		type(KeyCode.A, 5);
		assertTrue(true);
	}

	@Test
	public void addLongerDescriptionTest() {
		dragToLeftCircleTest();
		clickOn(MouseButton.SECONDARY);
		moveBy(20, 60);
		clickOn(MouseButton.PRIMARY);
		type(KeyCode.A, 5);
		type(KeyCode.TAB);
		type(KeyCode.SPACE);
		assertTrue(true);
	}

	@Test
	public void undoDeleteDiagramTextTest() {
		deleteDiagramTextTest();
		clickOn(undoBttn);
		assertFalse(itemList.getItems().isEmpty());
	}

	@Test
	public void redoDeleteDiagramTextTest() {
		undoDeleteDiagramTextTest();
		clickOn(redoBttn);
		assertTrue(itemList.getItems().isEmpty());
	}

	@Test
	public void undoAddLongerDescriptionTest() {
		addLongerDescriptionTest();
		clickOn(undoBttn);
		assertTrue(true);
	}

	@Test
	public void redoAddLongerDescriptionTest() {
		undoAddLongerDescriptionTest();
		clickOn(redoBttn);
		assertTrue(true);
	}

	@Test
	public void undoEditDiagramTextTest() {
		editDiagramTextTest();
		clickOn(undoBttn);
		assertTrue(true);
	}

	@Test
	public void redoEditDiagramTextTest() {
		undoEditDiagramTextTest();
		clickOn(redoBttn);
		assertTrue(true);
	}

	@Test
	public void toggleLeftCircleHoverTest() {
		moveTo((Circle)find("#leftCircle"));
		clickOn(MouseButton.SECONDARY);
		moveBy(20, 20);
		clickOn(MouseButton.PRIMARY);
		assertTrue(true);
	}

	@Test
	public void toggleRightCircleHoverTest() {
		moveTo((Circle)find("#rightCircle"));
		clickOn(MouseButton.SECONDARY);
		moveBy(20, 20);
		clickOn(MouseButton.PRIMARY);
		assertTrue(true);
	}

	@Test
	public void deleteAllTest(){
		dragToLeftCircleTest();
		moveBy(-50, -100);
		clickOn(MouseButton.SECONDARY);
		moveBy(20, 40);
		clickOn(MouseButton.PRIMARY);
		assertTrue(itemList.getItems().isEmpty());
	}

	@Test
	public void undoDeleteAllTest(){
		deleteAllTest();
		clickOn(undoBttn);
		assertFalse(itemList.getItems().isEmpty());
	}

	@Test
	public void redoDeleteAllTest(){
		undoDeleteAllTest();
		clickOn(redoBttn);
		assertTrue(itemList.getItems().isEmpty());
	}
}
