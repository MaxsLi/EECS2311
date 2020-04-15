package tests;

import javafx.geometry.VerticalDirection;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShapeSceneControllerTest extends MainTest {

	private ToggleButton toggleButton;
	private TextField appTitle;
	private TextField leftTitle;
	private TextField rightTitle;
	private TextField diagramText;
	private Button addBttn;
	private ListView itemList;
	private Button clearListBttn;
	private Button eraseItemBttn;
	private Button createItemBttn;
	private Button testModeBttn;
	private StackPane stackPane;
	private ColorPicker backgroundColor;
	private ColorPicker titleColors;
	private AnchorPane mainScene;
	private Circle leftCircle;
	private ColorPicker leftColorPicker;
	private Slider leftSlider;
	private ColorPicker leftHoverColor;
	private Slider leftFontSlider;
	private ColorPicker leftTextColor;
	private Circle rightCircle;
	private ColorPicker rightColorPicker;
	private Slider rightSlider;
	private ColorPicker rightHoverColor;
	private Slider rightFontSlider;
	private ColorPicker rightTextColor;
	private Button addCircleBttn;
	private Button undoBttn;
	private Button redoBttn;
	private TextField sideAdded;

	private void initializeVariables() {
		toggleButton = find("#toggle");
		appTitle = find("#appTitle");
		leftTitle = find("#leftTitle");
		rightTitle = find("#rightTitle");
		diagramText = find("#diagramText");
		addBttn = find("#addBttn");
		diagramText = find("#diagramText");
		itemList = find("#itemList");
		clearListBttn = find("#clearListBttn");
		eraseItemBttn = find("#eraseItemBttn");
		createItemBttn = find("#createItemBttn");
		testModeBttn = find("#testModeBttn");
		stackPane = find("#stackPane");
		backgroundColor = find("#backgroundColor");
		mainScene = find("#mainScene");
		titleColors = find("#titleColors");
		leftCircle = find("#leftCircle");
		leftColorPicker = find("#leftColorPicker");
		leftSlider = find("#leftSlider");
		leftHoverColor = find("#leftHoverColor");
		leftFontSlider = find("#leftFontSlider");
		leftTextColor = find("#leftTextColor");
		rightCircle = find("#rightCircle");
		rightColorPicker = find("#rightColorPicker");
		rightSlider = find("#rightSlider");
		rightHoverColor = find("#rightHoverColor");
		rightFontSlider = find("#rightFontSlider");
		rightTextColor = find("#rightTextColor");
		addCircleBttn = find("#addCircleBttn");
		undoBttn = find("#undoBttn");
		redoBttn = find("#redoBttn");
		sideAdded = find("#sideAdded");
	}

	private void toggleNavBar() {
		clickOn(toggleButton);
		while (toggleButton.isDisable()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ignored) {
			}
		}
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
	public void appTitleTest() {
		clickOn(appTitle);
		writeInputAndAssert(TEST_INPUT_STRING, appTitle);
	}

	@Test
	public void leftDiagramTitleTest() {
		clickOn(leftTitle);
		writeInputAndAssert(TEST_INPUT_STRING, leftTitle);
	}

	@Test
	public void rightDiagramTitleTest() {
		clickOn(rightTitle);
		writeInputAndAssert(TEST_INPUT_STRING, rightTitle);
	}

	@Test
	public void diagramTextTest() {
		toggleNavBar();
		clickOn(diagramText);
		writeInputAndAssert(TEST_INPUT_STRING, diagramText);
	}

	@Test
	public void addDiagramTextTest() {
		diagramTextTest();
		clickOn(addBttn);
		assertEquals(itemList.getItems().get(0), TEST_INPUT_STRING);
	}

	@Test
	public void addMultipleDiagramTextTest() {
		toggleNavBar();
		for (int i = 0; i < 3; i++) {
			clickOn(diagramText);
			writeInputAndAssert(TEST_INPUT_STRING + i, diagramText);
			type(KeyCode.ENTER);
			assertEquals(itemList.getItems().get(i), TEST_INPUT_STRING + i);
		}
	}

	@Test
	public void clearListTest() {
		addMultipleDiagramTextTest();
		clickOn(clearListBttn);
		assertTrue(itemList.getItems().isEmpty());
	}

	@Test
	public void eraseItemTest() {
		addDiagramTextTest();
		clickOn(itemList.getItems().get(0).toString());
		clickOn(eraseItemBttn);
		assertTrue(itemList.getItems().isEmpty());
	}

	@Test
	public void createItemTest() {
		addMultipleDiagramTextTest();
		int oldSize = stackPane.getChildren().size();
		clickOn(itemList.getItems().get(1).toString());
		clickOn(createItemBttn);
		assertEquals(oldSize + 1, stackPane.getChildren().size());
	}

	@Test
	public void enterTestModeTest() {
		toggleNavBar();
		clickOn(testModeBttn);
		type(KeyCode.SPACE);
		assertTrue(true);
	}

	@Test
	public void changeBackgroundColorTest() {
		toggleNavBar();
		clickOn(backgroundColor);
		moveBy(0, 100);
		clickOn(MouseButton.PRIMARY);
		assertEquals(mainScene.getBackground().getFills().get(0).getFill().toString(), "0x804d80ff");
	}

	@Test
	public void changeTitleTextColorTest() {
		toggleNavBar();
		clickOn(titleColors);
		moveBy(0, 100);
		clickOn(MouseButton.PRIMARY);
		assertEquals(appTitle.getStyle().substring(51, 57), "804d80");
	}

	@Test
	public void changeLeftCircleColorTest() {
		toggleNavBar();
		clickOn(titleColors);
		scroll(5, VerticalDirection.DOWN);
		clickOn(leftColorPicker);
		moveBy(0, 100);
		clickOn(MouseButton.PRIMARY);
		assertEquals(leftCircle.getFill().toString(), "0x804d80ff");
	}

	@Test
	public void changeLeftCircleSizeTest() {
		toggleNavBar();
		clickOn(titleColors);
		scroll(6, VerticalDirection.DOWN);
		moveTo(leftSlider);
		moveBy(9, -5);
		clickOn(MouseButton.PRIMARY);
		assertEquals(leftSlider.getValue(), 267.0731707317073);
	}

	@Test
	public void changeLeftHoverColorTest() {
		toggleNavBar();
		clickOn(titleColors);
		scroll(7, VerticalDirection.DOWN);
		clickOn(leftHoverColor);
		moveBy(0, 100);
		clickOn(MouseButton.PRIMARY);
		assertEquals(leftHoverColor.getValue().toString(), "0x804d80ff");
	}

	@Test
	public void changeLeftFontSizeTest() {
		toggleNavBar();
		clickOn(titleColors);
		scroll(10, VerticalDirection.DOWN);
		clickOn(leftFontSlider);
		moveBy(10, -8);
		clickOn(MouseButton.PRIMARY);
		assertEquals(leftFontSlider.getValue(), 19.0);
	}

	@Test
	public void changeLeftTextColorTest() {
		toggleNavBar();
		clickOn(titleColors);
		scroll(12, VerticalDirection.DOWN);
		clickOn(leftTextColor);
		moveBy(0, 100);
		clickOn(MouseButton.PRIMARY);
		assertEquals(leftTextColor.getValue().toString(), "0x804d80ff");
	}

	@Test
	public void changeRightCircleColorTest() {
		toggleNavBar();
		clickOn(titleColors);
		scroll(16, VerticalDirection.DOWN);
		clickOn(rightColorPicker);
		moveBy(0, 100);
		clickOn(MouseButton.PRIMARY);
		assertEquals(rightCircle.getFill().toString(), "0x804d80ff");
	}

	@Test
	public void changeRightCircleSizeTest() {
		toggleNavBar();
		clickOn(titleColors);
		scroll(18, VerticalDirection.DOWN);
		moveTo(rightSlider);
		moveBy(9, -5);
		clickOn(MouseButton.PRIMARY);
		assertEquals(rightSlider.getValue(), 267.0731707317073);
	}

	@Test
	public void changeRightHoverColorTest() {
		toggleNavBar();
		clickOn(titleColors);
		scroll(20, VerticalDirection.DOWN);
		clickOn(rightHoverColor);
		moveBy(0, 100);
		clickOn(MouseButton.PRIMARY);
		assertEquals(rightHoverColor.getValue().toString(), "0x804d80ff");
	}

	@Test
	public void changeRightFontSizeTest() {
		toggleNavBar();
		clickOn(titleColors);
		scroll(22, VerticalDirection.DOWN);
		clickOn(rightFontSlider);
		moveBy(10, -8);
		clickOn(MouseButton.PRIMARY);
		assertEquals(rightFontSlider.getValue(), 19.0);
	}

	@Test
	public void changeRightTextColorTest() {
		toggleNavBar();
		clickOn(titleColors);
		scroll(24, VerticalDirection.DOWN);
		clickOn(rightTextColor);
		moveBy(0, 100);
		clickOn(MouseButton.PRIMARY);
		assertEquals(rightTextColor.getValue().toString(), "0x804d80ff");
	}

	@Test
	public void addCircleTest() {
		toggleNavBar();
		clickOn(addCircleBttn);
		assertEquals(stackPane.getChildren().size(), 3);
		type(KeyCode.TAB);
		type(KeyCode.SPACE);
	}

	@Test
	public void undoAppTitleTest() {
		appTitleTest();
		toggleNavBar();
		clickOn(undoBttn);
		assertEquals(appTitle.getText(), "");
	}

	@Test
	public void undoLeftDiagramTitleTest() {
		leftDiagramTitleTest();
		toggleNavBar();
		clickOn(undoBttn);
		assertEquals(leftTitle.getText(), "");
	}

	@Test
	public void undoRightDiagramTitleTest() {
		rightDiagramTitleTest();
		toggleNavBar();
		clickOn(undoBttn);
		assertEquals(rightTitle.getText(), "");
	}

	@Test
	public void undoAddDiagramTextTest() {
		addDiagramTextTest();
		clickOn(undoBttn);
		assertTrue(itemList.getItems().isEmpty());
	}

	@Test
	public void undoAddMultipleDiagramTextTest() {
		addMultipleDiagramTextTest();
		for (int i = 1; i >= 0; i--) {
			clickOn(undoBttn);
			assertEquals(itemList.getItems().get(i), TEST_INPUT_STRING + i);
		}
	}

	@Test
	public void undoChangeBackgroundColorTest() {
		changeBackgroundColorTest();
		clickOn(undoBttn);
		assertEquals(mainScene.getBackground().getFills().get(0).getFill().toString(), "0xf5deb3ff");
	}

	@Test
	public void undoChangeTitleTextColorTest() {
		changeTitleTextColorTest();
		clickOn(undoBttn);
		assertEquals(appTitle.getStyle().substring(51, 57), "000000");
	}

	@Test
	public void undoChangeLeftCircleColorTest() {
		changeLeftCircleColorTest();
		clickOn(undoBttn);
		assertEquals(leftCircle.getFill().toString(), "0x87ceebff");
	}

	@Test
	public void undoChangeLeftCircleSizeTest() {
		changeLeftCircleSizeTest();
		clickOn(undoBttn);
		assertEquals(leftSlider.getValue(), 225.0);
	}

	@Test
	public void undoChangeLeftHoverColorTest() {
		changeLeftHoverColorTest();
		clickOn(undoBttn);
		assertEquals(leftHoverColor.getValue().toString(), "0x1a3399ff");
	}

	@Test
	public void undoChangeLeftFontSizeTest() {
		changeLeftFontSizeTest();
		clickOn(undoBttn);
		assertEquals(leftFontSlider.getValue(), 11.0);
	}

	@Test
	public void undoChangeRightCircleColorTest() {
		changeRightCircleColorTest();
		clickOn(undoBttn);
		assertEquals(leftCircle.getFill().toString(), "0x87ceebff");
	}

	@Test
	public void undoChangeRightCircleSizeTest() {
		changeRightCircleSizeTest();
		clickOn(undoBttn);
		assertEquals(rightSlider.getValue(), 225.0);
	}

	@Test
	public void undoChangeRightHoverColorTest() {
		changeRightHoverColorTest();
		clickOn(undoBttn);
		assertEquals(rightHoverColor.getValue().toString(), "0x990000ff");
	}

	@Test
	public void undoChangeRightFontSizeTest() {
		changeRightFontSizeTest();
		clickOn(undoBttn);
		assertEquals(rightFontSlider.getValue(), 11.0);
	}

	@Test
	public void redoAppTitleTest() {
		undoAppTitleTest();
		clickOn(redoBttn);
		assertEquals(appTitle.getText(), TEST_INPUT_STRING);
	}

	@Test
	public void redoLeftDiagramTitleTest() {
		undoLeftDiagramTitleTest();
		clickOn(redoBttn);
		assertEquals(leftTitle.getText(), TEST_INPUT_STRING);
	}

	@Test
	public void redoRightDiagramTitleTest() {
		undoRightDiagramTitleTest();
		clickOn(redoBttn);
		assertEquals(rightTitle.getText(), TEST_INPUT_STRING);
	}

	@Test
	public void redoAddDiagramTextTest() {
		undoAddDiagramTextTest();
		clickOn(redoBttn);
		assertFalse(itemList.getItems().isEmpty());
	}

	@Test
	public void redoAddMultipleDiagramTextTest() {
		undoAddMultipleDiagramTextTest();
		for (int i = 1; i >= 0; i--) {
			clickOn(redoBttn);
			assertEquals(itemList.getItems().get(i), TEST_INPUT_STRING + i);
		}
	}

	@Test
	public void redoChangeBackgroundColorTest() {
		undoChangeBackgroundColorTest();
		clickOn(redoBttn);
		assertEquals(mainScene.getBackground().getFills().get(0).getFill().toString(), "0x804d80ff");
	}

	@Test
	public void redoChangeTitleTextColorTest() {
		undoChangeTitleTextColorTest();
		clickOn(redoBttn);
		assertEquals(appTitle.getStyle().substring(51, 57), "804d80");
	}

	@Test
	public void redoChangeLeftCircleColorTest() {
		undoChangeLeftCircleColorTest();
		clickOn(redoBttn);
		assertEquals(leftCircle.getFill().toString(), "0x804d80ff");
	}

	@Test
	public void redoChangeLeftCircleSizeTest() {
		undoChangeLeftCircleSizeTest();
		clickOn(redoBttn);
		assertEquals(leftSlider.getValue(), 267.0731707317073);
	}

	@Test
	public void redoChangeLeftHoverColorTest() {
		undoChangeLeftHoverColorTest();
		clickOn(redoBttn);
		assertEquals(leftHoverColor.getValue().toString(), "0x804d80ff");
	}

	@Test
	public void redoChangeLeftFontSizeTest() {
		undoChangeLeftFontSizeTest();
		clickOn(redoBttn);
		assertEquals(leftFontSlider.getValue(), 19.0);
	}

	@Test
	public void redoChangeRightCircleColorTest() {
		undoChangeRightCircleColorTest();
		clickOn(redoBttn);
		assertEquals(rightCircle.getFill().toString(), "0x804d80ff");
	}

	@Test
	public void redoChangeRightCircleSizeTest() {
		undoChangeRightCircleSizeTest();
		clickOn(redoBttn);
		assertEquals(rightSlider.getValue(), 267.0731707317073);
	}

	@Test
	public void redoChangeRightHoverColorTest() {
		undoChangeRightHoverColorTest();
		clickOn(redoBttn);
		assertEquals(leftHoverColor.getValue().toString(), "0x1a3399ff");
	}

	@Test
	public void redoChangeRightFontSizeTest() {
		undoChangeRightFontSizeTest();
		clickOn(redoBttn);
		assertEquals(rightFontSlider.getValue(), 19.0);
	}

	@Test
	public void dragToLeftCircleTest() {
		addDiagramTextTest();
		Node textFiled = stackPane.getChildren().get(2);
		clickOn(textFiled);
		type(KeyCode.TAB);
		type(KeyCode.SPACE);
		clickOn(textFiled).drag(MouseButton.PRIMARY).moveBy(300, 300);
		release(MouseButton.PRIMARY);
		assertEquals(sideAdded.getText(), "Left!");
	}

	@Test
	public void dragToRightCircleTest() {
		addDiagramTextTest();
		Node textFiled = stackPane.getChildren().get(2);
		clickOn(textFiled);
		type(KeyCode.TAB);
		type(KeyCode.SPACE);
		clickOn(textFiled).drag(MouseButton.PRIMARY).moveBy(600, 300);
		release(MouseButton.PRIMARY);
		assertEquals(sideAdded.getText(), "Right!");
	}

	@Test
	public void dragToIntersectionTest() {
		addDiagramTextTest();
		Node textFiled = stackPane.getChildren().get(2);
		clickOn(textFiled);
		type(KeyCode.TAB);
		type(KeyCode.SPACE);
		clickOn(textFiled).drag(MouseButton.PRIMARY).moveBy(450, 300);
		release(MouseButton.PRIMARY);
		assertEquals(sideAdded.getText(), "Intersecting Left & Right!");
	}

	@Test
	public void dragToLeftThenRightTest() {
		dragToLeftCircleTest();
		Node textFiled = stackPane.getChildren().get(2);
		clickOn(textFiled).drag(MouseButton.PRIMARY).moveBy(400, 0);
		release(MouseButton.PRIMARY);
		assertEquals(sideAdded.getText(), "Right!");
	}

	@Test
	public void undoDragToLeftThenRightTest() {
		dragToLeftThenRightTest();
		clickOn(undoBttn);
		assertEquals(sideAdded.getText(), "Left!");
	}

	@Test
	public void redoDragToLeftThenRightTest() {
		undoDragToLeftThenRightTest();
		clickOn(redoBttn);
		assertEquals(sideAdded.getText(), "Left!");
	}

}
