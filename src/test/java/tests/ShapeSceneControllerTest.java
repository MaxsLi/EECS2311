package tests;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShapeSceneControllerTest extends MainTest {

	private ToggleButton toggleButton;
	private TextField appTitle;
	private TextField leftTitle;
	private TextField rightTitle;
	private TextField diagramText;
	private Button addBttn;
	private ListView itemList;

	private void initializeVariables() {
		toggleButton = find("#toggle");
		appTitle = find("#appTitle");
		leftTitle = find("#leftTitle");
		rightTitle = find("#rightTitle");
		diagramText = find("#diagramText");
		addBttn = find("#addBttn");
		diagramText = find("#diagramText");
		itemList = find("#itemList");
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
	protected void setUp() throws Exception {
		launchApplication();
		enterShapeScene();
		initializeVariables();
	}

	@AfterEach
	protected void tearDown() throws Exception {
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
	}

	@Test
	public void addMultipleDiagramTextTest() {
		toggleNavBar();
		int count = 3;
		while (count > 0) {
			clickOn(diagramText);
			writeInputAndAssert(TEST_INPUT_STRING + count, diagramText);
			type(KeyCode.ENTER);
			count--;
		}
	}


//
//	@Test
//	public void changeLeftColor() throws InterruptedException {
//		clickOn("#leftColorPicker").type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.ENTER);
//		assertEquals(leftCircle.getFill(), Color.valueOf("#ffffb3"));
//	}
//
//	@Test
//	public void changeRightColor() throws InterruptedException {
//		clickOn("#rightColorPicker").type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.UP).type(KeyCode.UP).type(KeyCode.UP).type(KeyCode.UP).type(KeyCode.ENTER);
//		assertEquals(rightCircle.getFill(), Color.valueOf("#ffb366"));
//	}
//

//
//	@Test
//	public void testPopup() throws InterruptedException {
//		clickOn("#diagramText");
//		write("Good Test Mate");
//		type(KeyCode.ENTER);
//		assertEquals(stackPane.getChildren().size(), 3);
//
//	}
//
//	@Test
//	public void changeColor_2() throws InterruptedException {
//		clickOn("#leftColorPicker").type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.UP).type(KeyCode.UP).type(KeyCode.UP).type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.ENTER);
//
//	}
//
//
//	@Test
//	public void changeColor_3() throws InterruptedException {
//		clickOn("#leftColorPicker").type(KeyCode.LEFT).type(KeyCode.RIGHT).type(KeyCode.DOWN).type(KeyCode.UP).type(KeyCode.UP).type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.LEFT).type(KeyCode.ENTER);
//
//	}
//
//	@Test
//	public void testDrag_03() throws Exception {
//		clickOn("#diagramText");
//		write("This is a Junit Test");
//		type(KeyCode.ENTER);
//
//	}
//
//	@Test
//	public void testDrag_04() throws Exception {
//		clickOn("#diagramText");
//		write("This is a Junit Test LOOOOOL");
//		type(KeyCode.ENTER);
//		Node tf = stackPane.getChildren().get(2);
//
//		tf.setTranslateX(-174);
//		Thread.sleep(1000);
//		clickOn(leftCircle);
//		Thread.sleep(1000);
//
//	}
//
//	@Test
//	public void testDrag_05() throws Exception {
//		clickOn("#diagramText");
//		write("This is a Junit Test SIDE ADDED YA");
//		type(KeyCode.ENTER);
//		Node tf = stackPane.getChildren().get(2);
//
//		tf.setTranslateX(-174);
//		Thread.sleep(1000);
//		clickOn(leftCircle);
//		Thread.sleep(1000);
//		assertEquals(sideAdded.getText(), "Left!");
//
//	}
//
//	@Test
//	public void testDrag_06() throws Exception {
//		clickOn("#diagramText");
//		write("This is a Junit Test SIDE ADDED YA");
//		type(KeyCode.ENTER);
//		Node tf = stackPane.getChildren().get(2);
//
//		tf.setTranslateX(-174);
//		Thread.sleep(1000);
//		clickOn(leftCircle).drag(MouseButton.PRIMARY).dropTo(rightCircle);
//		Thread.sleep(1000);
//
//
//	}
//
//	@Test
//	public void testDrag_07() throws Exception {
//		clickOn("#diagramText");
//		write("This is a Junit Test SIDE ADDED YA");
//		type(KeyCode.ENTER);
//		Node tf = stackPane.getChildren().get(2);
//
//		tf.setTranslateX(-174);
//		Thread.sleep(1000);
//		clickOn(leftCircle).drag(MouseButton.PRIMARY).dropTo(rightCircle);
//		Thread.sleep(1000);
//		assertEquals(sideAdded.getText(), "Intersection!");
//	}
//
//	@Test
//	public void testLeftTitle1() throws Exception {
//		clickOn("#leftTitle");
//		write("Benefits");
//
//		assertEquals(leftTitle.getText(), "Benefits");
//
//
//	}
//
//	@Test
//	public void testRightTitle1() throws Exception {
//		clickOn("#rightTitle");
//		write("Doubts");
//
//		assertEquals(rightTitle.getText(), "Doubts");
//
//
//	}

}
