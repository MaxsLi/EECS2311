package controllers;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.sound.midi.VoiceStatus;

import org.junit.platform.commons.function.Try;
import org.junit.runner.Request;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import views.MainApp;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import models.AddCircleCommand;
import models.AddCommand;
import models.AddTooltipCommand;
import models.Command;
import models.DeleteAllCommand;
import models.DeleteCommand;
import models.DragCommand;
import models.EditBackgroundColorCommand;
import models.EditCircleColorCommand;
import models.EditCircleSizeCommand;
import models.EditHoverColorCommand;
import models.EditTextColorCommand;
import models.EditTextCommand;
import models.EditTextSizeCommand;
import models.EditTitleCommand;
import models.Location;
import models.UndoRedoManager;
import models.VennSet;
import models.VennShape;
import utilities.TextUtils;

public class ShapeSceneController implements Initializable {

	private final static String DEFAULT_BACKGROUND_COLOR = "#F5DEB3";
	private final static String DEFAULT_LEFTCIRCLE_COLOR = "#87ceeb";
	private final static String DEFAULT_RIGHTCIRCLE_COLOR = "#a0522d";
	protected final static String DEFAULT_EXTRACIRCLE_COLOR = "#9ACD32";
	protected final static String DEFAULT_LEFT_HOVER_COLOR = "#1a3399";
	protected final static String DEFAULT_RIGHT_HOVER_COLOR = "#990000";
	protected final static String DEFAULT_EXTRA_HOVER_COLOR = "#ffff4d";
	private final static String DEFAULT_TITLE_COLOR = "#000000";
	private final static String DEFAULT_LEFTTEXT_COLOR = "#000000";
	private final static String DEFAULT_RIGHTTEXT_COLOR = "#000000";
	private final static String DEFAULT_EXTRATEXT_COLOR = "#000000";
	protected static int NUM_OF_CIRCLES = 2;

	public static boolean REMIND_OUTOF_BOUNDS = true;

	public static boolean REMIND_EMPTY_TEXTFIELD = true;

	private static boolean LEFT_CIRCLE_HOVER = true;
	private static boolean RIGHT_CIRCLE_HOVER = true;
	protected static boolean EXTRA_CIRCLE_HOVER = true;

	protected static boolean NAV_IS_SHOWING = false;

	public static boolean APPLICATION_IS_SAVED = true;

	public static boolean ANIMATION_DONE = true;

	@FXML
	protected AnchorPane mainScene;

	@FXML
	protected StackPane stackPane;

	@FXML
	protected Circle leftCircle;

	@FXML
	protected Circle rightCircle;

	@FXML
	private Button addBttn;

	@FXML
	private Button saveBttn;

	@FXML
	private Button loadBttn;

	@FXML
	private Button removeButton;

	@FXML
	private TextField diagramText;

	@FXML
	protected TextField appTitle;

	@FXML
	protected ColorPicker leftColorPicker;

	@FXML
	protected ColorPicker rightColorPicker;

	@FXML
	protected ContextMenu textFieldContext;

	@FXML
	protected TextField sideAdded;

	protected MainApp mainApp;

	private String currentFileName;

	private File currentFile;

	@FXML
	protected TextField leftTitle;

	@FXML
	protected TextField rightTitle;

	@FXML
	protected ToggleButton toggle;

	@FXML
	protected ListView<String> itemList;

	@FXML
	protected ColorPicker backgroundColor;

	@FXML
	protected ColorPicker titleColors;

	@FXML
	protected Slider leftSlider;

	@FXML
	protected Slider rightSlider;

	@FXML
	protected ColorPicker leftHoverColor;

	@FXML
	protected ColorPicker rightHoverColor;

	@FXML
	protected ButtonBar listBttns;

	@FXML
	protected Button clearListBttn;

	@FXML
	protected Button eraseItemBttn;

	@FXML
	protected Button createItemBttn;

	@FXML
	protected Button removeItemButton;

	@FXML
	protected Button addCircleBttn;

	@FXML
	protected VBox navBox;

	@FXML
	protected TitledPane appearancePane;

	@FXML
	protected VBox scrollBox;

	@FXML
	protected MenuItem createNewVenn;

	@FXML
	protected MenuItem openMenuItem;

	@FXML
	protected MenuItem addCircleMenuItem;

	@FXML
	protected MenuItem aboutItem;

	@FXML
	private MenuItem undoBtn;

	@FXML
	private MenuItem redoBtn;

	@FXML
	protected Slider leftFontSlider;

//	@FXML
//	private TextField leftFontTextField;

	@FXML
	protected Slider rightFontSlider;

//	@FXML
//	private TextField rightFontTextField;

	@FXML
	protected ColorPicker leftTextColor;

	@FXML
	protected ColorPicker rightTextColor;

	@FXML
	protected Button testModeBttn;

	@FXML
	protected Button undoBttn;

	@FXML
	protected Button redoBttn;

	@FXML
	protected MenuItem exportJPG;

	@FXML
	protected MenuItem exportPNG;

	@FXML
	protected HBox saveBox;
	// -----------------------Extra Circle #1's Properties May or may not be needed
	protected Circle extraCircle;
	protected Slider extra1Slider;
	protected ColorPicker extra1Color;
	protected Label extra1Label = new Label("Circle 3");
	protected Label extra1LabelColor = new Label("Extra Circle #1 Color");
	protected Label extra1LabelSize = new Label("Extra Circle #1 Size");
	protected Label extra1HoverLabel = new Label("Hover Color");
	protected ColorPicker extra1ColorHover;
	protected Separator extra1Seperator;
	protected Label textProperties = new Label("Text Properties");
	protected Label extraFontSize = new Label("Font Size");
	protected HBox sliderBox;
	protected Label extraTextColor = new Label("Text Color");
	protected Slider extraFontSlider;
	protected ColorPicker extraTextColorPicker;
	protected TextField extraTitle;

	public static boolean EXTRA_CIRCLE_ADDED = false;
	// --------------------------------------

	/**
	 * An Set of `TextLabel`s
	 */
	protected VennSet vennSet;

	protected HashMap<TextField, Location> tfLocations = new HashMap<>();
	private HashMap<TextField, String> tooltipHolder = new HashMap<>(); // A hashmap that holds the longer descriptions
																		// of text if necessary

	/**
	 * A static variable to allow the user to choice if they want to continue to be
	 * reminded that they're placing a textfield out of bounds
	 */

	/**
	 * An Set of `Shape`s
	 */
	protected VennShape vennShape;

	protected double orgSceneX;
	protected double orgSceneY;
	protected double orgTranslateX;
	protected double orgTranslateY;

	// size of left circle
	private double leftCircleSize;
	// size of right circle
	private double rightCircleSize;
	// size of extra circle
	private double extraCircleSize;

	// size of left circle text
	private double leftTextSize;
	// size of right circle text
	private double rightTextSize;
	// size of extra circle text
	private double extraTextSize;

	private UndoRedoManager undoRedoManager;

	private DragCommand dragCommand;

	private DeleteCommand deleteCommand;

	private EditTextCommand editTextCommand;

//	private EditCircleSizeCommand editLeftCircleSizeCommand;

//	private EditTextSizeCommand editTextSizeCommand;

	private boolean init;

	private boolean isMouse;

	private String leftHover;
	private String rightHover;
	private String extraHover;
	/**
	 * An array containing possible locations for a new textfield to be placed on
	 * the scene when entered
	 */
	protected Point2D[] textFieldPointLocations = { new Point2D(-375, -375), new Point2D(-375, -325),
			new Point2D(-375, -275), new Point2D(-375, -225), new Point2D(-375, -175), new Point2D(-375, -125),
			new Point2D(-375, -75), new Point2D(-375, -25), new Point2D(-375, 25), new Point2D(-375, 75),
			new Point2D(-375, 125) };
	public int textFieldPointLocationsIndex = 0;

	public static Color LEFTCIRCLECOLOR = Color.valueOf("#ff8a8a");
	public static Color RIGHTCIRCLECOLOR = Color.valueOf("#a7ff8f");

	public ShapeSceneController() {
		// Note that function `initialize` will do the init
	}

	public static final String COMMA = ",";

	private void addKeyShortcuts() {
//		mainApp.primaryStage.getScene().setOnKeyPressed(e -> {
//			if (e.isControlDown() && e.getCode() == KeyCode.Z) {
//				this.undo();
//			} else if (e.isControlDown() && e.isShiftDown() && e.getCode() == KeyCode.Y) {
//				this.redo();
//			}
//		});
		undoBtn.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
		redoBtn.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCodeCombination.SHIFT_DOWN, KeyCodeCombination.CONTROL_DOWN));
		createNewVenn.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
		openMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
		
	}

	/**
	 * On click, creates a textArea which can be dragged into Respective Circle
	 */
	public void addTextToDiagram() {

		if ((this.diagramText.getText().isEmpty() || this.diagramText.getText().trim().equals(""))) {

			if (REMIND_EMPTY_TEXTFIELD) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Dialog");
				alert.setHeaderText("Empty TextField");
				alert.setContentText("Please Enter Text Before you click Add" + "\n"
						+ "Would you like to be reminded of this again?");

				ButtonType remindMe = new ButtonType("Remind Me");
				ButtonType dontRemindMe = new ButtonType("Do not Remind Me");

				alert.getButtonTypes().setAll(remindMe, dontRemindMe);

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == remindMe) {
					REMIND_EMPTY_TEXTFIELD = true;
				} else {
					REMIND_EMPTY_TEXTFIELD = false;
				}
			}

		} else {
			String newText = this.diagramText.getText();
			/*
			 * In Text fields only the first 20 or so characters are visible to the user
			 * Everything after the 20th character is invisible, so store the first 18
			 * characters and append "..." to it. Make the whole text avalible as a longer
			 * description aka ToolTip
			 */
			String first18;
			if (newText.charAt(newText.length() - 1) == ',') {
				newText = newText.substring(0, newText.length() - 1);
			}

			if (newText.length() > 20) {
				first18 = newText.substring(0, 19);
				first18 += "...";
				Tooltip tt = new Tooltip(newText);
				TextField newTextField = new TextField();

				Command a = new AddCommand(this, newTextField);
				undoRedoManager.addCommand(a);
				addTextField(first18, newTextField);
				addTooltip(newTextField, tt);

			}
			else {
				TextField newTextField = new TextField();

				Command a = new AddCommand(this, newTextField);
				undoRedoManager.addCommand(a);
				addTextField(newText, newTextField);
			}
		}

		ShapeSceneController.APPLICATION_IS_SAVED = false;
		changesMade();
	}

	public TextField addTextField(String newText, TextField newTextField) {
		newTextField.setTranslateX(textFieldPointLocations[textFieldPointLocationsIndex].getX());
		newTextField.setTranslateY(textFieldPointLocations[textFieldPointLocationsIndex].getY());

		adjustNewTextLocation();

		return setupTextField(newText, newTextField);
	}

	private TextField setupTextField(String newText, TextField newTextField) {
		newTextField.setEditable(false);
		newTextField.setStyle("-fx-background-color:transparent; -fx-font-size:18px; ");
		newTextField.setMinWidth(Control.USE_PREF_SIZE);
		newTextField.setPrefWidth(Control.USE_COMPUTED_SIZE);
		newTextField.setMaxWidth(Control.USE_PREF_SIZE);

		this.addDragEvent(newTextField);
		this.addContext(newTextField);

		if (!this.itemList.getItems().contains(newText)) {
			this.itemList.getItems().add(newText);
		}

		newTextField.setText(newText);
		this.stackPane.getChildren().add(newTextField);
		this.vennSet.add(newTextField);
		this.sideAdded.clear();
		this.diagramText.clear();
		syncColor(newTextField);
		return newTextField;
	}

	public TextField addTextField(String newText, TextField newTextField, double posX, double posY) {

		newTextField.setTranslateX(posX);
		newTextField.setTranslateY(posY);

		return setupTextField(newText, newTextField);
	}

	private void changesMade() {
		String filename = this.currentFileName;
		if (filename == null) {
			filename = "Untitled";
		}

		if (ShapeSceneController.APPLICATION_IS_SAVED) {
			MainApp.primaryStage.setTitle(filename + " - Saved");
		} else {
			MainApp.primaryStage.setTitle(filename + " - UnSaved");
		}
	}

	protected void adjustNewTextLocation() {
		this.textFieldPointLocationsIndex = (this.textFieldPointLocationsIndex + 1) % textFieldPointLocations.length;
	}

	protected void resetTextFieldPointLocationsIndex() {
		this.textFieldPointLocationsIndex = 0;
	}

	@FXML
	protected void createItem() {
		if (itemList.getItems().isEmpty())
			return;
		String newItem = itemList.getSelectionModel().getSelectedItem();
		diagramText.setText(newItem);
		addTextToDiagram();
	}

	@FXML
	protected void clearList() {
		try {
			this.itemList.getItems().remove(0, this.itemList.getItems().size());
		} catch (ArrayIndexOutOfBoundsException e) {
			return;
		}
	}

	@FXML
	protected void eraseItem() {
		try {
			int index = itemList.getSelectionModel().getSelectedIndex();
			itemList.getItems().remove(index);
		} catch (ArrayIndexOutOfBoundsException e) {
			return;
		}
	}

	public void eraseItem(String text) {
		try {
			int index = -1;
			boolean found = false;
			for (int i = 0; i < itemList.getItems().size() && !found; i++) {
				if (itemList.getItems().get(i).equals(text)) {
					index = i;
					found = true;
				}
			}
			itemList.getItems().remove(index);
		} catch (ArrayIndexOutOfBoundsException e) {
			return;
		}
	}

	public void addItem(String newString) {
		itemList.getItems().add(newString);
	}

	/**
	 * Adds Drag Events to created TextFields
	 * 
	 * @param textField the TextField to be added
	 */
	protected void addDragEvent(TextField textField) {

		textField.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {

			this.diagramText.clear();
			orgSceneX = e.getSceneX();
			orgSceneY = e.getSceneY();
			orgTranslateX = textField.getTranslateX();
			orgTranslateY = textField.getTranslateY();

			textField.toFront();
			dragCommand = new DragCommand(this, textField, orgTranslateX, orgTranslateY);
		});

		/*
		 * On Mouse Drag Moves the TextField Around the Screen
		 */
		textField.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {

			double offsetX = e.getSceneX() - orgSceneX;
			double offsetY = e.getSceneY() - orgSceneY;
			double newTranslateX = orgTranslateX + offsetX;
			double newTranslateY = orgTranslateY + offsetY;

			dragCommand.setOffsetX(newTranslateX);
			dragCommand.setOffsetY(newTranslateY);

			dragCommand.execute();

		});

		/*
		 * On Mouse Release Calculates Distances with circles. to determine where this
		 * circle has been placed
		 * 
		 * Uses Basic Distance Between point calculations to do so
		 * 
		 * Stores the string contents of the textField in leftSet, rightSet or
		 * intersectionSet
		 */
		textField.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {

			getLocation(textField);
			syncColor(textField);
			undoRedoManager.addCommand(dragCommand);

		});

		textField.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {

			textField.setEditable(false);

		});

	}

	public void getLocation(TextField textField) {
		Location textBoxLocation;

		try {
			textBoxLocation = this.vennSet.getLocation(textField);

		} catch (Exception exception) {
			if (REMIND_OUTOF_BOUNDS) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Dialog");
				alert.setHeaderText("TextField Out of Bounds");
				alert.setContentText("Please place the textfield within the bounds of the circle." + "\n"
						+ "Would you like to be reminded of this again?");

				ButtonType remindMe = new ButtonType("Remind Me");
				ButtonType dontRemindMe = new ButtonType("Do not Remind Me");

				alert.getButtonTypes().setAll(remindMe, dontRemindMe);

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == remindMe) {
					REMIND_OUTOF_BOUNDS = true;
				} else {
					REMIND_OUTOF_BOUNDS = false;
				}

			}
			return;
		}

		if (textBoxLocation == Location.INTERSECTING_ALL) {
			sideAdded.setText("Intersecting All Circles!");
			sideAdded.setEditable(false);
			sideAdded.setStyle("-fx-text-fill: purple; -fx-font-size: 18px;-fx-background-color:transparent;");
			tfLocations.put(textField, Location.INTERSECTING_ALL);
		}

		else if (textBoxLocation == Location.INTERSECTING_BOTTOM_LEFT) {
			sideAdded.setText("Intersecting Left & Bottom!");
			sideAdded.setEditable(false);
			sideAdded.setStyle("-fx-text-fill: purple; -fx-font-size: 18px;-fx-background-color:transparent;");
			tfLocations.put(textField, Location.INTERSECTING_BOTTOM_LEFT);
		} else if (textBoxLocation == Location.INTERSECTING_LEFT_RIGHT) {
			sideAdded.setText("Intersecting Left & Right!");
			sideAdded.setEditable(false);
			sideAdded.setStyle("-fx-text-fill: purple; -fx-font-size: 18px;-fx-background-color:transparent;");
			tfLocations.put(textField, Location.INTERSECTING_LEFT_RIGHT);
		} else if (textBoxLocation == Location.INTERSECTING_BOTTOM_RIGHT) {
			sideAdded.setText("Intersecting Right & Bottom!");
			sideAdded.setEditable(false);
			sideAdded.setStyle("-fx-text-fill: purple; -fx-font-size: 18px;-fx-background-color:transparent;");
			tfLocations.put(textField, Location.INTERSECTING_BOTTOM_RIGHT);
		}

		else if (textBoxLocation == Location.LEFT) {
			sideAdded.setText("Left!");
			sideAdded.setEditable(false);
			sideAdded.setStyle("-fx-text-fill: blue; -fx-font-size: 18px;-fx-background-color:transparent;");
			tfLocations.put(textField, Location.LEFT);
		} else if (textBoxLocation == Location.RIGHT) {
			sideAdded.setText("Right!");
			sideAdded.setEditable(false);
			sideAdded.setStyle("-fx-text-fill: red; -fx-font-size: 18px;-fx-background-color:transparent;");
			tfLocations.put(textField, Location.RIGHT);
		} else if (textBoxLocation == Location.BOTTOM) {
			sideAdded.setText("Bottom!");
			sideAdded.setEditable(false);
			sideAdded.setStyle("-fx-text-fill: red; -fx-font-size: 18px;-fx-background-color:transparent;");
			tfLocations.put(textField, Location.BOTTOM);
		}
	}

	public void moveTextField(TextField textField, double newTranslateX, double newTranslateY) {

		textField.setTranslateX(newTranslateX);
		textField.setTranslateY(newTranslateY);

		resetTextFieldPointLocationsIndex();

		ShapeSceneController.APPLICATION_IS_SAVED = false;

		changesMade();
	}

	/**
	 * A Method that gives a right-click feature on each textField added to the
	 * screen, On right-click of a textfield added, gives a contextMenu
	 * 
	 * @param textField the TextField to be added
	 */
	protected void addContext(TextField textField) {
		ContextMenu context = new ContextMenu();
		MenuItem delete = new MenuItem("Delete");
		MenuItem edit = new MenuItem("Edit");
		MenuItem addDescription = new MenuItem("Add Longer Description");
		context.getItems().add(delete);
		context.getItems().add(edit);
		context.getItems().add(addDescription);
		textField.setContextMenu(context);

		delete.setOnAction((event) -> {

			deleteCommand = new DeleteCommand(this, textField, textField.getTranslateX(), textField.getTranslateY());
			undoRedoManager.addCommand(deleteCommand);
			deleteCommand.execute();

		});

		edit.setOnAction((event) -> {
			editTextCommand = new EditTextCommand(this, textField, textField.getText());
			undoRedoManager.addCommand(editTextCommand);
			textField.setEditable(true);
		});

		addDescription.setOnAction((event) -> addLongerDescription(textField));

	}

	protected void addLongerDescription(TextField tf) {
		TextInputDialog dialog = new TextInputDialog("Longer Description here...");
		dialog.setTitle("Add Description");
		dialog.setHeaderText("Add A longer Description to Textfield");
		dialog.setContentText("Description: ");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			AddTooltipCommand tooltipCommand = new AddTooltipCommand(this, result.get(), tf);
			undoRedoManager.addCommand(tooltipCommand);
			tooltipCommand.execute();

		} else {
			return;
		}
	}

	public void addTooltip(TextField tf, Tooltip tt) {
		Tooltip.install(tf, tt);
		tooltipHolder.put(tf, tt.getText());
	}

	public void removeTooltip(TextField tf) {
		Tooltip.uninstall(tf, tf.getTooltip());
		tooltipHolder.remove(tf);
	}

	public void deleteSpecficText(TextField tf) {
		String contents = tf.getText();
		stackPane.getChildren().remove(tf);
		for (int i = 0; i < this.vennSet.size(); i++) {
			if (this.vennSet.get(i) == tf) {
				this.vennSet.remove(i);
			}
		}

		if (!itemList.getItems().contains(contents)) {
			itemList.getItems().add(contents);
		}

		ShapeSceneController.APPLICATION_IS_SAVED = false;
		changesMade();

	}

	/**
	 * A Method that gives allows the given TextField auto-resize its width
	 * according to the content.
	 *
	 * @param textField the TextField to be added
	 */
//	private void addAutoResize(TextField textField) {
//		textField.textProperty().addListener((ob, o, n) -> {
//			// expand the textfield
//			textField.setMaxWidth(TextUtils.computeTextWidth(textField.getFont(), textField.getText(), 0.0D) + 20);
//		});
//	}

	/**
	 * A Method that loads all comma delimited rows from save.csv and puts them on
	 * the screen
	 */
	public void loadVenn(File fileToLoad) {
		int appTitleCol = 0;
		int leftTitleCol = 1;
		int rightTitleCol = 2;
		int leftCircelColorCol = 3;
		int rightCircleColorCol = 4;
		int mainSceneColorCol = 5;
		int titleTextColorCol = 6;
		int numOfCirclesCol = 7;

		try {

			FileReader fr = new FileReader(fileToLoad);
			BufferedReader br = new BufferedReader(fr);

			this.currentFileName = fileToLoad.getName().substring(0, fileToLoad.getName().length() - 4); // Cuts off the
																											// ".csv"
																											// extension

			this.currentFile = fileToLoad;

			// System.out.println(this.currentFileName);

			String[] parts;
			String s;
			TextField tf;
			int lineCounter = 1;

			try {
				String[] firstLineInfo = br.readLine().split(COMMA);

				// System.out.println(Arrays.toString(firstLineInfo));

				this.appTitle.setText(firstLineInfo[appTitleCol]);
				this.leftTitle.setText(firstLineInfo[leftTitleCol]);
				this.rightTitle.setText(firstLineInfo[rightTitleCol]);

				if (firstLineInfo[leftCircelColorCol].equals("0xffffffff")) {
					this.leftCircle.setFill(Color.valueOf(ShapeSceneController.DEFAULT_LEFTCIRCLE_COLOR));
				} else {
					this.leftCircle.setFill(Paint.valueOf(firstLineInfo[leftCircelColorCol]));
				}

				if (firstLineInfo[rightCircleColorCol].equals("0xffffffff")) {
					this.rightCircle.setFill(Color.valueOf(ShapeSceneController.DEFAULT_RIGHTCIRCLE_COLOR));
				} else {
					this.rightCircle.setFill(Paint.valueOf(firstLineInfo[rightCircleColorCol]));
				}

				if (firstLineInfo[mainSceneColorCol].equals("0xffffffff")) {
					this.mainScene
							.setStyle("-fx-background-color:" + ShapeSceneController.DEFAULT_BACKGROUND_COLOR + ";");
				} else {

					this.mainScene
							.setStyle("-fx-background-color:#"
									+ Paint.valueOf(firstLineInfo[mainSceneColorCol]).toString().substring(2,
											Paint.valueOf(firstLineInfo[mainSceneColorCol]).toString().length() - 2)
									+ ";");

				}

				if (firstLineInfo[titleTextColorCol].equals("0xffffffff")) {
					this.appTitle.setStyle("-fx-text-fill:" + ShapeSceneController.DEFAULT_TITLE_COLOR + ";"
							+ "-fx-background-color: transparent; -fx-font-size:25px;");
					this.leftTitle.setStyle("-fx-text-fill:" + ShapeSceneController.DEFAULT_TITLE_COLOR + ";"
							+ "-fx-background-color: transparent;-fx-font-size:20px;");
					this.rightTitle.setStyle("-fx-text-fill:" + ShapeSceneController.DEFAULT_TITLE_COLOR + ";"
							+ "-fx-background-color: transparent;-fx-font-size:20px;");
				} else {
					this.appTitle
							.setStyle(
									"-fx-text-fill:#"
											+ Paint.valueOf(firstLineInfo[titleTextColorCol]).toString().substring(2,
													Paint.valueOf(firstLineInfo[titleTextColorCol]).toString().length()
															- 2)
											+ ";" + "-fx-background-color: transparent; -fx-font-size:25px;");
					this.leftTitle
							.setStyle(
									"-fx-text-fill:#"
											+ Paint.valueOf(firstLineInfo[titleTextColorCol]).toString().substring(2,
													Paint.valueOf(firstLineInfo[titleTextColorCol]).toString().length()
															- 2)
											+ ";" + "-fx-background-color: transparent;-fx-font-size:20px;");
					this.rightTitle
							.setStyle(
									"-fx-text-fill:#"
											+ Paint.valueOf(firstLineInfo[titleTextColorCol]).toString().substring(2,
													Paint.valueOf(firstLineInfo[titleTextColorCol]).toString().length()
															- 2)
											+ ";" + "-fx-background-color: transparent;-fx-font-size:20px;");

				}
				if (firstLineInfo[numOfCirclesCol].equals("3")) {
					addCircle();
				}
				// System.out.println(lineCounter);

				lineCounter++;

			} catch (IllegalArgumentException ex) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning Dialog");
				alert.setHeaderText("Trouble Parsing First Line");
				alert.setContentText(
						"There is something wrong with the first line of the CSV File, and cannot be parsed");
				alert.showAndWait();
			}

			while ((s = br.readLine()) != null) {
				parts = s.split(COMMA);

				// System.out.println("The line number is: " + lineCounter);

				lineCounter++;
				tf = new TextField();
				tf.setMinWidth(Control.USE_PREF_SIZE);
				tf.setPrefWidth(Control.USE_COMPUTED_SIZE);
				tf.setMaxWidth(Control.USE_PREF_SIZE);
				this.addContext(tf);
				tf.setText(parts[0]);// parts[0] is the text column of the line

				if (!parts[1].equals("")) {
					Tooltip tt = new Tooltip(parts[1]);
					Tooltip.install(tf, tt);
				}

				tf.setEditable(false);
				tf.resizeRelocate(0, 0, 1, 1);
				tf.resize(50, 50);

				try {

					double textFieldX = Double.parseDouble(parts[2]);
					double textFieldY = Double.parseDouble(parts[3]);
					tf.setTranslateX(textFieldX);
					tf.setTranslateY(textFieldY);
					stackPane.getChildren().add(tf);
					this.vennSet.add(tf);
					tf.setStyle("-fx-background-color:transparent; -fx-font-size:18px; ");

					// System.out.println(parts[3]);

					addDragEvent(tf);

					// System.out.println(lineCounter);

					lineCounter++;
				} catch (NumberFormatException NFE) {
					if (lineCounter == 2) {
						lineCounter++;
						continue; // Known Error reading line 2, don't say anything just continue
					}
				} catch (Exception ex) {
					if (lineCounter == 2) {
						lineCounter++;
						continue; // Known Error reading line 2, don't say anything just continue
					}

					ex.printStackTrace();
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning Dialog");
					alert.setHeaderText("Trouble parsing CSV File");
					alert.setContentText("An Error Occured on Parsing the CSV File on line:" + lineCounter
							+ " Please check this line and try again");
					alert.showAndWait();
					lineCounter++;
					/*
					 * If the user touches the CSV file and changes the Numbers that represent x and
					 * y location into say a string, an error will occur, so catch it.
					 */
				}
			}
			fr.close();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("Oops, Something went seriously wrong :(");
			alert.setContentText(
					"Something went wrong with trying to parse your CSV file, please make sure they abide VennCreates standards and try again");
			alert.showAndWait();
		}
	}

	// for tester
	public void setStackPane(StackPane sp) {
		this.stackPane = sp;

	}

	public ArrayList<TextField> getTextFields() {
		return this.vennSet;
	}

	/**
	 * A method that saves all text-fields on scene to a csv file
	 * 
	 * @param write A List with all the TextFields that are to be written to the
	 *              save.csv file
	 */
	public void saveVenn(ArrayList<TextField> write) {

		String dummyLine = "TEXT COLUMN" + COMMA + "Additional Description" + COMMA + "TextField X Coor" + COMMA
				+ "TextField Y Coor" + COMMA + "Location of TextField" + COMMA + "<------DO NOT MODIFY THIS LINE"; // Program
																													// not
																													// reading
																													// line
																													// two
		// adding dummyLine

		/*
		 * Set the First Line of the CSV File Accordingly
		 */
		String firstLine = this.appTitle.getText() + COMMA + this.leftTitle.getText() + COMMA
				+ this.rightTitle.getText() + COMMA + this.leftColorPicker.getValue().toString() + COMMA
				+ this.rightColorPicker.getValue().toString() + COMMA + this.backgroundColor.getValue().toString()
				+ COMMA + this.titleColors.getValue().toString() + COMMA + ShapeSceneController.NUM_OF_CIRCLES + COMMA
				+ "<---DO NOT MODIFY THIS LINE" + "\n" + dummyLine;

		try {
			String titleOfApp;
			FileWriter fw = null;
			FileChooser fileChooser = null;

			/*
			 * Upon Loading a Previous file, this.currentFileName is initialized. If it is
			 * created brand new, this.currentFileName will be null and ill know to make a
			 * new file and not write an an existing one
			 */
			if (this.currentFile == null) {
				TextInputDialog dialog = new TextInputDialog("Untitled1");
				dialog.setTitle("Title your Project");
				dialog.setHeaderText("Please Enter a Title for your VennCreate Project");
				dialog.setContentText("Title of Project:");

				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()) {
					titleOfApp = result.get();
				} else {
					return;
				}
			} else {
				titleOfApp = this.currentFileName;
			}
			try {
				if (this.currentFile == null) {
					Window stage = this.stackPane.getScene().getWindow();
					fileChooser = new FileChooser();

					File recordsDir = new File(System.getProperty("user.home"), "VennCreateFiles" + File.separator);

					if (!recordsDir.exists()) {
						recordsDir.mkdirs();
					}

					fileChooser.setInitialDirectory(recordsDir);
					fileChooser.setTitle("Save File");
					fileChooser.setInitialFileName(titleOfApp);
					fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV files", "*.csv"));

					this.currentFile = fileChooser.showSaveDialog(stage);
				}

				if (this.currentFile != null) { // Throw Alert if Somehow the currentWorking File is Still null, if not
												// null, write to it
					fw = new FileWriter(this.currentFile, false);

					// fileChooser.setInitialDirectory(this.currentFile);
				} else {
					Alert alertError = new Alert(AlertType.ERROR);
					alertError.setTitle("Error");
					alertError.setHeaderText("File Could Not be Saved to");
					alertError.setContentText("The File You want to write to by Closing this window"
							+ ", is open in another process. Please Close that File before trying to close this window.");
					alertError.showAndWait();
				}

			} catch (FileNotFoundException ex) {
				Alert alertWarn = new Alert(AlertType.WARNING);
				alertWarn.setTitle("WARNING");
				alertWarn.setHeaderText(this.currentFileName + "Is Open in Another Location");
				alertWarn.setContentText(
						"If you close the Main Window without closing the other file, your changes will not be saved");
				alertWarn.showAndWait();

				Alert alertError = new Alert(AlertType.ERROR);
				alertError.setTitle("Error");
				alertError.setHeaderText("File Could Not be Saved to");
				alertError.setContentText("The File You want to write to by Closing this window"
						+ ", is open in another process. Please Close that File before trying to close this window.");
				alertError.showAndWait();
				return;

			}
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);

			pw.println(firstLine); // Prints important AppAttributes to the first Line
			boolean linePrinted = true;

			int writeIndexer = 0; // A Indexer for the write ArrayList argument

			while (writeIndexer < write.size()) {
				if (linePrinted) {
					linePrinted = false;
					continue;
				}

				TextField textField = write.get(writeIndexer);

				try { // If Nothing Was Added on GetExisting, the program crashes, this is so it
						// doesn't crash
						// System.out.println(this.vennSet.getLocation(textField)));
					String longerDescription = null;
					if (tooltipHolder.containsKey(textField)) {
						longerDescription = tooltipHolder.get(textField);
					} else {
						longerDescription = "";
					}
					pw.println(textField.getText() + COMMA + longerDescription + COMMA + textField.getTranslateX()
							+ COMMA + textField.getTranslateY() + COMMA + this.vennSet.getLocation(textField));

					writeIndexer++;
				} catch (Exception excep) {
					writeIndexer++;
					continue;
				}
				pw.flush();
			}
			pw.close();
			fw.close();
		} catch (Exception e) {
			Alert alertWarn = new Alert(AlertType.WARNING);
			alertWarn.setTitle("WARNING");
			alertWarn.setHeaderText("An Unexpected Error Occured");
			alertWarn.setContentText(
					"Please Make sure the file you are trying to save to is not open in another process and try again");
			alertWarn.showAndWait();
		}

		ShapeSceneController.APPLICATION_IS_SAVED = true;
		changesMade();
		playSaveAnimation();

	}

	@FXML
	public void saveVennBttn() {
		try {
			saveVenn(this.getTextFields());
		} catch (Exception NPE) {
			Alert alertWarn = new Alert(AlertType.WARNING);
			alertWarn.setTitle("WARNING");
			alertWarn.setHeaderText("An Unexpected Error Occured");
			alertWarn.setContentText(
					"Please Make sure the file you are trying to save to is not open in another process and try again");
			alertWarn.showAndWait();
		}
	}

	private void playSaveAnimation() {
		TranslateTransition translate = new TranslateTransition();

		translate.setByX(-350);

		translate.setDuration(Duration.millis(1750));

		translate.setAutoReverse(true);

		translate.setNode(this.saveBox);

		translate.setCycleCount(2);

		translate.play();

	}

	/**
	 * A method that changes the color of the leftCircle
	 */
	public void changeLeftColor() {
		EditCircleColorCommand a = new EditCircleColorCommand(this, leftCircle, leftCircle.getFill(),
				leftColorPicker.getValue());
		undoRedoManager.addCommand(a);
		a.execute();

		ShapeSceneController.APPLICATION_IS_SAVED = false;
		changesMade();
	}

	/**
	 * A method that changes the color of the rightCircle
	 */
	public void changeRightColor() {
		rightCircle.setFill(rightColorPicker.getValue());
		EditCircleColorCommand a = new EditCircleColorCommand(this, rightCircle, rightCircle.getFill(),
				rightColorPicker.getValue());
		undoRedoManager.addCommand(a);
		a.execute();

		ShapeSceneController.APPLICATION_IS_SAVED = false;
		changesMade();
	}

	public void changeCircleColor(Paint newColor, Circle circle) {
		if (circle.equals(leftCircle)) {
			leftCircle.setFill(newColor);
			leftColorPicker.setValue((Color) newColor);

		} else if (circle.equals(rightCircle)) {
			rightCircle.setFill(newColor);
			rightColorPicker.setValue((Color) newColor);
		} else if (circle.equals(extraCircle)) {
			extraCircle.setFill(newColor);
			extra1Color.setValue((Color) newColor);
		}
	}

	public void changebackgroundColor() {
		String newStyle = "-fx-background-color: #"
				+ backgroundColor.getValue().toString().substring(2, backgroundColor.getValue().toString().length() - 2)
				+ ";";
		EditBackgroundColorCommand a = new EditBackgroundColorCommand(this, mainScene.getStyle().toString(), newStyle);
		undoRedoManager.addCommand(a);
		a.execute();
		ShapeSceneController.APPLICATION_IS_SAVED = false;
		changesMade();
	}

	public void setBackgrondColor(String newStyle) {
		mainScene.setStyle(newStyle);
		backgroundColor.setValue(Color.web(newStyle.substring(22, newStyle.length() - 1)));
	}

	public void startHoverLeft() {
		if (ShapeSceneController.LEFT_CIRCLE_HOVER) {
			leftCircle.setStyle("-fx-stroke:" + leftHover + ";" + " -fx-stroke-width: 5;");
			this.mainScene.setCursor(Cursor.HAND);
		}
	}

	public void endHoverLeft() {
		leftCircle.setStyle("-fx-stroke:black;");
		this.mainScene.setCursor(Cursor.DEFAULT);
	}

	public void startHoverRight() {
		if (ShapeSceneController.RIGHT_CIRCLE_HOVER) {
			rightCircle.setStyle("-fx-stroke:" + rightHover + ";" + " -fx-stroke-width: 5;");
			this.mainScene.setCursor(Cursor.HAND);
		}
	}

	public void endHoverRight() {
		rightCircle.setStyle("-fx-stroke:black;");
		this.mainScene.setCursor(Cursor.DEFAULT);
	}

	public void changeLeftHoverColor() {
		String newColor = "#"
				+ leftHoverColor.getValue().toString().substring(2, leftHoverColor.getValue().toString().length() - 2);
		String oldColor = leftHover;
		EditHoverColorCommand a = new EditHoverColorCommand(this, leftCircle, oldColor, newColor);
		undoRedoManager.addCommand(a);
		a.execute();
	}

	public void setCircleHover(String color, Circle circle) {

		if (circle.equals(leftCircle)) {
			leftHover = color;
			leftHoverColor.setValue(Color.web(color));
		} else if (circle.equals(rightCircle)) {
			rightHover = color;
			rightHoverColor.setValue(Color.web(color));
		} else if (circle.equals(extraCircle)) {
			extraHover = color;
			extra1ColorHover.setValue(Color.web(color));
		}
	}

	public void changeRightHoverColor() {
		String newColor = "#" + rightHoverColor.getValue().toString().substring(2,
				rightHoverColor.getValue().toString().length() - 2);
		String oldColor = rightHover;
		EditHoverColorCommand a = new EditHoverColorCommand(this, rightCircle, oldColor, newColor);
		undoRedoManager.addCommand(a);
		a.execute();
	}

	public void changetitleColors() {

		String newColor = titleColors.getValue().toString().substring(2,
				titleColors.getValue().toString().length() - 2);
		EditTitleCommand a = new EditTitleCommand(this, appTitle.getStyle().substring(51, 57), newColor);
		undoRedoManager.addCommand(a);
		a.execute();

		ShapeSceneController.APPLICATION_IS_SAVED = false;
		changesMade();
	}

	public void setTitleColor(String newColor) {

		appTitle.setStyle("-fx-background-color: transparent;\n-fx-text-fill: #" + newColor + ";-fx-font-size:25px;");
		leftTitle.setStyle("-fx-background-color: transparent;\n-fx-text-fill: #" + newColor + ";-fx-font-size:20px;");
		rightTitle.setStyle("-fx-background-color: transparent;\n-fx-text-fill: #" + newColor + ";-fx-font-size:20px;");
		if (EXTRA_CIRCLE_ADDED) {
			extraTitle.setStyle(
					"-fx-background-color: transparent;\n-fx-text-fill: #" + newColor + ";-fx-font-size:20px;");
		}
		titleColors.setValue(Color.web(newColor));
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp a reference to the mainStage
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.vennShape = new VennShape(this.leftCircle, this.rightCircle);
		this.vennSet = new VennSet(this.vennShape);
		ShapeSceneController.EXTRA_CIRCLE_ADDED = false;

		// this.drawerHolder.setSidePane(this.navBox);
		this.navBox.setVisible(false);
		this.toggle.setText("SHOW");
		this.toggle.setStyle("-fx-font-size:18");
		this.toggle.setStyle("-fx-background-color:#FF69B4");

		initSliders();

		initColorPickers();
		initCircleContext();

		this.undoRedoManager = new UndoRedoManager(this);

		init = true;
		leftTitle.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue && init) {
				leftTitle.getParent().requestFocus();
				init = false;
			}
		});
		isMouse = false;
		addKeyShortcuts();
		addTitleListeners();
		undoBtn(false);
		redoBtn(false);
		setTitleColor(DEFAULT_TITLE_COLOR.substring(1));
		leftTextColor.setValue(Color.web("#000000"));
		rightTextColor.setValue(Color.web("#000000"));
		setCircleHover(DEFAULT_LEFT_HOVER_COLOR, leftCircle);
		setCircleHover(DEFAULT_RIGHT_HOVER_COLOR, rightCircle);
	}

	public void undoBtn(boolean set) {

		undoBtn.setDisable(!set);
	}

	public void redoBtn(boolean set) {

		redoBtn.setDisable(!set);
	}

	protected void initColorPickers() {
		this.backgroundColor.setValue(Color.valueOf(DEFAULT_BACKGROUND_COLOR));
		this.titleColors.setValue(Color.valueOf(DEFAULT_TITLE_COLOR));
		this.leftTextColor.setValue(Color.valueOf(DEFAULT_LEFTTEXT_COLOR));
		this.rightTextColor.setValue(Color.valueOf(DEFAULT_RIGHTTEXT_COLOR));

		this.leftColorPicker.setValue(Color.valueOf(ShapeSceneController.DEFAULT_LEFTCIRCLE_COLOR));
		this.rightColorPicker.setValue(Color.valueOf(ShapeSceneController.DEFAULT_RIGHTCIRCLE_COLOR));

		this.leftHoverColor.setValue(Color.valueOf(ShapeSceneController.DEFAULT_LEFT_HOVER_COLOR));
		this.rightHoverColor.setValue(Color.valueOf(ShapeSceneController.DEFAULT_RIGHT_HOVER_COLOR));
	}

	/**
	 * A Method that lets the sliders listen for changes and act accordingly
	 */
	protected void initSliders() {

		// Adding Listener to value property.
//		leftSlider.valueProperty().addListener(new ChangeListener<Number>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Number> observable, //
//					Number oldValue, Number newValue) {
//
//			
//				
//			}
//		});
		leftSlider.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			leftCircleSize = leftCircle.getRadius();
			if (e.getX() != leftSlider.getValue()) {
				// clicked random spot
				circleSliderAction(leftCircle);

			}

			leftCircleSize = leftSlider.getValue();

		});

		leftSlider.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
			circleSliderAction(leftCircle);

		});

		leftSlider.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
			leftCircleSize = leftCircle.getRadius();
		});

//		// Adding Listener to value property.
//		rightSlider.valueProperty().addListener(new ChangeListener<Number>() {
// 
//			@Override
//			public void changed(ObservableValue<? extends Number> observable, //
//					Number oldValue, Number newValue) {
//
//				System.out.println(oldValue);
//				editCircleSizeCommand.setOldSize(rightCircle.getRadius());
//				editCircleSizeCommand.setNewSize((double) newValue); 
//				undoRedoManager.addCommand(editCircleSizeCommand);
//				editCircleSizeCommand.execute();
//				
//			}
//		});

		rightSlider.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			rightCircleSize = rightCircle.getRadius();
			if (e.getX() != rightSlider.getValue()) {
				// clicked random spot
				circleSliderAction(rightCircle);

			}

			rightCircleSize = rightCircle.getRadius();

		});

		rightSlider.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
			circleSliderAction(rightCircle);

		});

		rightSlider.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
			rightCircleSize = rightCircle.getRadius();
		});
		// Adding Listener to value property.
//		leftFontSlider.valueProperty().addListener(new ChangeListener<Number>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Number> observable, //
//					Number oldValue, Number newValue) {
//
//				leftFontTextField.setText(((double) newValue) + "");
//				 System.out.println(tfLocations.toString());
//				editTextSizeCommand.setLocation(Location.LEFT);
//				editTextSizeCommand.setOldSize((double) oldValue);
//				editTextSizeCommand.setNewSize((double) newValue);
//				undoRedoManager.addCommand(editTextSizeCommand);
//				editTextSizeCommand.execute();
//				
//			}
//		});

		leftFontSlider.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			leftTextSize = getTextSize(Location.LEFT);
			if (e.getX() != leftFontSlider.getValue()) {
				fontSliderAction(Location.LEFT);
			}
			leftTextSize = getTextSize(Location.LEFT);
		});
		leftFontSlider.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
			fontSliderAction(Location.LEFT);
		});

		leftFontSlider.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
			leftTextSize = getTextSize(Location.LEFT);
		});
		rightFontSlider.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			rightTextSize = getTextSize(Location.RIGHT);
			if (e.getX() != rightFontSlider.getValue()) {
				fontSliderAction(Location.RIGHT);
			}
			rightTextSize = getTextSize(Location.RIGHT);
		});
		rightFontSlider.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
			fontSliderAction(Location.RIGHT);
		});
		rightFontSlider.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
			rightTextSize = getTextSize(Location.RIGHT);
		});
		// editRightTextSizeCommand.setOldSize(rightFontSlider.getValue());
//		rightFontSlider.valueProperty().addListener(new ChangeListener<Number>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Number> observable, //
//					Number oldValue, Number newValue) {
//
//				
//				 rightFontTextField.setText(((double) newValue) + "");
//				editTextSizeCommand.setLocation(Location.RIGHT);
//				editTextSizeCommand.setOldSize((double) oldValue);
//				editTextSizeCommand.setNewSize((double) newValue);
//				undoRedoManager.addCommand(editTextSizeCommand);
//				editTextSizeCommand.execute();
//				
//			}
//		});

		leftSlider.setValue(leftSlider.getMin());
		rightSlider.setValue(rightSlider.getMin());
		leftTextSize = leftFontSlider.getValue();
		rightTextSize = rightFontSlider.getValue();
//		leftFontSlider.setValue(leftFontSlider.getMin());
//		rightFontSlider.setValue(rightFontSlider.getMin());

	}

	private void circleSliderAction(Circle circle) {
		EditCircleSizeCommand editCircleSizeCommand;
		if (circle.equals(leftCircle)) {
			editCircleSizeCommand = new EditCircleSizeCommand(this, circle, leftCircleSize, leftSlider.getValue());
		} else if (circle.equals(rightCircle)) {
			editCircleSizeCommand = new EditCircleSizeCommand(this, circle, rightCircleSize, rightSlider.getValue());
		} else if (circle.equals(extraCircle)) {
			editCircleSizeCommand = new EditCircleSizeCommand(this, circle, rightCircleSize, extra1Slider.getValue());
		} else {
			return;
		}
		undoRedoManager.addCommand(editCircleSizeCommand);
		editCircleSizeCommand.execute();
	}

	public void changeCircleSize(double newSize, Circle circle) {
		if (circle.equals(leftCircle)) {
			leftCircle.setRadius(newSize);
			leftSlider.setValue(newSize);
		} else if (circle.equals(rightCircle)) {
			rightCircle.setRadius(newSize);
			rightSlider.setValue(newSize);
		} else if (circle.equals(extraCircle)) {
			extraCircle.setRadius(newSize);
			extra1Slider.setValue(newSize);
		}
	}

	public void changeTextSize(double newSize, Location location) {
		for (TextField tf : tfLocations.keySet()) {
			if (tfLocations.get(tf).equals(location)) {
				int newFont = ((int) Math.round(newSize));
				String font = "" + newFont;
				tf.setStyle("-fx-font-size:" + font + "px;-fx-background-color:transparent;");
				tf.setMinWidth(Control.USE_PREF_SIZE);
				tf.setPrefWidth(Control.USE_COMPUTED_SIZE);
				tf.setMaxWidth(Control.USE_PREF_SIZE);
			}
		}
		if (location.equals(Location.LEFT)) {
			// leftTextSize=newSize;
			leftFontSlider.setValue(newSize);
		} else if (location.equals(Location.RIGHT)) {
			// rightTextSize=newSize;
			rightFontSlider.setValue(newSize);
		} else if (location.equals(Location.BOTTOM)) {
			// extraTextSize=newSize;
			extraFontSlider.setValue(newSize);
		}
	}

	private int getTextSize(Location location) {
		if (!(tfLocations.isEmpty())) {
			for (TextField tf : tfLocations.keySet()) {
				if (tfLocations.get(tf).equals(location)) {
					return Integer.parseInt(tf.getStyle().substring(14, 16));
				}
			}
		}
		return -1;
	}

	private void fontSliderAction(Location location) {
		EditTextSizeCommand editTextSizeCommand;
		if (location.equals(Location.LEFT)) {
			editTextSizeCommand = new EditTextSizeCommand(this, location, leftTextSize, leftFontSlider.getValue());
		} else if (location.equals(Location.RIGHT)) {
			editTextSizeCommand = new EditTextSizeCommand(this, location, rightTextSize, rightFontSlider.getValue());
		} else if (location.equals(Location.BOTTOM)) {
			editTextSizeCommand = new EditTextSizeCommand(this, location, extraTextSize, extraFontSlider.getValue());
		} else {
			return;
		}
		undoRedoManager.addCommand(editTextSizeCommand);
		editTextSizeCommand.execute();
	}

	/**
	 * A Method that adds the rightClick option to all circles in the Scene
	 */
	protected void initCircleContext() {
		ContextMenu leftContext = new ContextMenu();
		ContextMenu rightContext = new ContextMenu();

		MenuItem leftHoverToggle = new MenuItem("Toggle Left Circle Hover");
		MenuItem leftDelete = new MenuItem("Delete All");
		MenuItem rightHoverToggle = new MenuItem("Toggle Right Circle Hover");
		MenuItem rightDelete = new MenuItem("Delete All");

		DeleteAllCommand deleteAllCommand = new DeleteAllCommand(this);
		leftHoverToggle.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (ShapeSceneController.LEFT_CIRCLE_HOVER) {
					ShapeSceneController.LEFT_CIRCLE_HOVER = false;
				} else {
					ShapeSceneController.LEFT_CIRCLE_HOVER = true;
				}
			}
		});

		rightHoverToggle.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (ShapeSceneController.RIGHT_CIRCLE_HOVER) {
					ShapeSceneController.RIGHT_CIRCLE_HOVER = false;
				} else {
					ShapeSceneController.RIGHT_CIRCLE_HOVER = true;
				}
			}
		});

		leftDelete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ArrayList<TextField> tf = new ArrayList<>();
				for (TextField textField : tfLocations.keySet()) {
					if (tfLocations.get(textField).equals(Location.LEFT)) {
						tf.add(textField);
					}
				}
				deleteAllCommand.setTextFields(tf);
				undoRedoManager.addCommand(deleteAllCommand);
				deleteAllCommand.execute();
			}
		});

		rightDelete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ArrayList<TextField> tf = new ArrayList<>();
				for (TextField textField : tfLocations.keySet()) {
					if (tfLocations.get(textField).equals(Location.RIGHT)) {
						tf.add(textField);
					}
				}
				deleteAllCommand.setTextFields(tf);
				undoRedoManager.addCommand(deleteAllCommand);
				deleteAllCommand.execute();
			}
		});

		leftContext.getItems().addAll(leftHoverToggle, leftDelete);
		rightContext.getItems().addAll(rightHoverToggle, rightDelete);

		leftCircle.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

			@Override
			public void handle(ContextMenuEvent event) {

				leftContext.show(leftCircle, event.getScreenX(), event.getScreenY());
			}
		});

		rightCircle.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

			@Override
			public void handle(ContextMenuEvent event) {

				rightContext.show(rightCircle, event.getScreenX(), event.getScreenY());
			}
		});

		if (ShapeSceneController.EXTRA_CIRCLE_ADDED) {
			ContextMenu extraContext = new ContextMenu();
			MenuItem extraHoverToggle = new MenuItem("Toggle Bottom Circle Hover");
			MenuItem extraDelete = new MenuItem("Delete All");

			extraContext.getItems().addAll(extraHoverToggle, extraDelete);

			extraHoverToggle.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					if (ShapeSceneController.EXTRA_CIRCLE_HOVER) {
						ShapeSceneController.EXTRA_CIRCLE_HOVER = false;
					} else {
						ShapeSceneController.EXTRA_CIRCLE_HOVER = true;
					}
				}
			});

			extraDelete.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					ArrayList<TextField> tf = new ArrayList<>();
					for (TextField textField : tfLocations.keySet()) {
						if (tfLocations.get(textField).equals(Location.BOTTOM)) {
							tf.add(textField);
						}
					}
					deleteAllCommand.setTextFields(tf);
					undoRedoManager.addCommand(deleteAllCommand);
					deleteAllCommand.execute();
				}
			});

			extraCircle.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

				@Override
				public void handle(ContextMenuEvent event) {

					extraContext.show(extraCircle, event.getScreenX(), event.getScreenY());
				}
			});

		}

	}

//	private void deleteAll(Location location) {
//		if (location.equals(Location.LEFT)) {
//			deleteAllLeft();
//		}
//		else if (location.equals(Location.RIGHT)) {
//			deleteAllRight();
//		}
//		else if (location.equals(Location.BOTTOM)) {
//			deleteAllExtra();
//		}
//	}
//	protected void deleteAllLeft() {
//		for (TextField tf : tfLocations.keySet()) {
//			if (tfLocations.get(tf) == Location.LEFT) {
//				deleteSpecficText(tf);
//			}
//		}
//		
//		ShapeSceneController.APPLICATION_IS_SAVED = false;
//		changesMade();
//	}
//
//	protected void deleteAllRight() {
//		for (TextField tf : tfLocations.keySet()) {
//			if (tfLocations.get(tf) == Location.RIGHT) {
//				deleteSpecficText(tf);
//			}
//		}
//		
//		ShapeSceneController.APPLICATION_IS_SAVED = false;
//		changesMade();
//	}
//
//	protected void deleteAllExtra() {
//		for (TextField tf : tfLocations.keySet()) {
//			if (tfLocations.get(tf) == Location.BOTTOM) {
//				deleteSpecficText(tf);
//			}
//		}
//		
//		ShapeSceneController.APPLICATION_IS_SAVED = false;
//		changesMade();
//	}

	@FXML
	protected void changeRightTextColor() {
		String oldColor = getColor(Location.RIGHT);
		String newColor = this.rightTextColor.getValue().toString().substring(2,
				rightTextColor.getValue().toString().length() - 2);
		EditTextColorCommand a = new EditTextColorCommand(this, Location.RIGHT, oldColor, newColor);
		undoRedoManager.addCommand(a);
		a.execute();

	}

	public String getColor(Location location) {
		String color = "";
		int k = 0;

		for (int i = 0; i < tfLocations.keySet().size() && k == 0; i++) {
			TextField tf = (TextField) tfLocations.keySet().toArray()[i];
			if (tfLocations.get(tf).equals(location) && k == 0) {
				for (int j = 0; j < tf.getStyle().length() && k == 0; j++) {
					if (tf.getStyle().charAt(j) == '#') {
						k = j;
					}
				}
				color = tf.getStyle().substring(k + 1, tf.getStyle().length() - 1);
			}
		}
		return color;
	}

	public void setTextColor(String newColor, Location location) {
		// int newFont;
		for (TextField tf : tfLocations.keySet()) {
			if (location.equals(Location.RIGHT) && tfLocations.get(tf).equals(Location.RIGHT)) {
				// newFont = ((int) Math.round((double) this.rightFontSlider.getValue()));
				tf.setStyle("-fx-font-size:" + (int) Math.round(rightTextSize)
						+ "px;-fx-background-color:transparent;-fx-text-fill: #" + newColor + ";");
				tf.setMinWidth(Control.USE_PREF_SIZE);
				tf.setPrefWidth(Control.USE_COMPUTED_SIZE);
				tf.setMaxWidth(Control.USE_PREF_SIZE);
				rightColorPicker.setValue(Color.web("#" + newColor));
			} else if (location.equals(Location.LEFT) && tfLocations.get(tf).equals(Location.LEFT)) {
				// newFont = ((int) Math.round((double) this.leftFontSlider.getValue()));
				tf.setStyle("-fx-font-size:" + (int) Math.round(leftTextSize)
						+ "px;-fx-background-color:transparent;-fx-text-fill: #" + newColor + ";");
				tf.setMinWidth(Control.USE_PREF_SIZE);
				tf.setPrefWidth(Control.USE_COMPUTED_SIZE);
				tf.setMaxWidth(Control.USE_PREF_SIZE);
				leftColorPicker.setValue(Color.web("#" + newColor));
			} else if (location.equals(Location.BOTTOM) && tfLocations.get(tf).equals(Location.BOTTOM)) {
				// newFont = ((int) Math.round((double) this.extraFontSlider.getValue()));
				tf.setStyle("-fx-font-size:" + (int) Math.round(extraTextSize)
						+ "px;-fx-background-color:transparent;-fx-text-fill: #" + newColor + ";");
				tf.setMinWidth(Control.USE_PREF_SIZE);
				tf.setPrefWidth(Control.USE_COMPUTED_SIZE);
				tf.setMaxWidth(Control.USE_PREF_SIZE);
				extraTextColorPicker.setValue(Color.web("#" + newColor));
			}
		}

	}

	public void deleteExtraCircle() {
		if (ShapeSceneController.EXTRA_CIRCLE_ADDED) {
			this.stackPane.getChildren().remove(this.extraCircle);
			this.scrollBox.getChildren().remove(32, this.scrollBox.getChildren().size());
		}
	}

	private void syncColor(TextField tf) {

		Location location = tfLocations.get(tf);
		String newColor;
		if (location != null) {
			if (location.equals(Location.LEFT)) {

				newColor = this.leftTextColor.getValue().toString().substring(2,
						leftTextColor.getValue().toString().length() - 2);
				tf.setStyle("-fx-font-size:" + (int) Math.round(leftTextSize)
						+ "px;-fx-background-color:transparent;-fx-text-fill: #" + newColor + ";");
			} else if (location.equals(Location.RIGHT)) {
				newColor = this.rightTextColor.getValue().toString().substring(2,
						rightTextColor.getValue().toString().length() - 2);
				tf.setStyle("-fx-font-size:" + (int) Math.round(rightTextSize)
						+ "px;-fx-background-color:transparent;-fx-text-fill: #" + newColor + ";");
			} else if (location.equals(Location.BOTTOM)) {
				newColor = this.extraTextColorPicker.getValue().toString().substring(2,
						extraTextColorPicker.getValue().toString().length() - 2);
				tf.setStyle("-fx-font-size:" + (int) Math.round(extraTextSize)
						+ "px;-fx-background-color:transparent;-fx-text-fill: #" + newColor + ";");
			} else {
				tf.setStyle("-fx-font-size:25px;-fx-background-color:transparent;-fx-text-fill:#000000;");
			}
		}
	}

	@FXML
	protected void changeLeftTextColor() {
		String oldColor = getColor(Location.LEFT);
		String newColor = this.leftTextColor.getValue().toString().substring(2,
				leftTextColor.getValue().toString().length() - 2);
		EditTextColorCommand a = new EditTextColorCommand(this, Location.LEFT, oldColor, newColor);
		undoRedoManager.addCommand(a);
		a.execute();
	}

//	@FXML
//	private void setRightFontSlider() {
//		try {
//			double input = Double.parseDouble(this.rightFontTextField.getText());
//			if(input > 25) {
//				input = 25;
//			}
//			else if(input < 11) {
//				input = 11;
//			}
//			this.rightFontSlider.setValue(input);
//			//Fix the width of the textfield
//			for(TextField tf: tfLocations.keySet()) {
//				if(tfLocations.get(tf).equals(Location.RIGHT)) {
//					tf.setPrefWidth(Control.USE_COMPUTED_SIZE);
//				}
//			}
//			
//		}
//		catch(NumberFormatException ne) {
//			Alert alert1 = new Alert(AlertType.WARNING);
//			alert1.setTitle("Warning Dialog");
//			alert1.setHeaderText("Invalid Number");
//			alert1.setContentText("Please Enter A Valid Number in the range [11,25]");
//			alert1.showAndWait();
//		}
//	}
//	
//	@FXML
//	private void setLeftFontSlider() {
//		try {
//			double input = Double.parseDouble(this.leftFontTextField.getText());
//			if(input > 25) {
//				input = 25;
//			}
//			else if(input < 11) {
//				input = 11;
//			}
//			this.leftFontSlider.setValue(input);
//			//Fix the width of the textfield
//			for(TextField tf: tfLocations.keySet()) {
//				if(tfLocations.get(tf).equals(Location.LEFT)) {
//					tf.setPrefWidth(Control.USE_COMPUTED_SIZE);
//				}
//			}
//		}
//		catch(Exception ne) {
//			Alert alert1 = new Alert(AlertType.WARNING);
//			alert1.setTitle("Warning Dialog");
//			alert1.setHeaderText("Invalid Number");
//			alert1.setContentText("Please Enter A Valid Number in the range [11,25]");
//			alert1.showAndWait();
//		}
//	}

	/**
	 * A method to Translate items on screen
	 */
	@FXML
	protected void toggleDrawer() {
		toggle.setDisable(true);
		this.toggle.setStyle("-fx-font-size:18");
		TranslateTransition translate = new TranslateTransition();
		TranslateTransition translateStk = new TranslateTransition();
		TranslateTransition translateLeftTitle = new TranslateTransition();
		TranslateTransition translateRightTitle = new TranslateTransition();
		TranslateTransition translateMainTitle = new TranslateTransition();

		FadeTransition ft = new FadeTransition(Duration.millis(1000), this.navBox);
		if (!toggle.isSelected()) {
			ShapeSceneController.NAV_IS_SHOWING = false;
			// NAV SHOULD BE VISIBLE
			// System.out.println("I was selected!");
			this.toggle.setStyle("-fx-background-color:#FF69B4; -fx-font-size:18px;"); // pinkish
			this.toggle.setText("SHOW");
			ft.setFromValue(1.0);
			ft.setToValue(0.0);
			ft.setAutoReverse(true);

			translate.setByX(-353);
			translateStk.setByX(-198);
			translateLeftTitle.setByX(-198);
			translateRightTitle.setByX(-198);
			translateMainTitle.setByX(-198);

			translate.setDuration(Duration.millis(1000));
			translateStk.setDuration(Duration.millis(1000));
			translateLeftTitle.setDuration(Duration.millis(1000));
			translateRightTitle.setDuration(Duration.millis(1000));
			translateMainTitle.setDuration(Duration.millis(1000));

			translate.setAutoReverse(false);
			translateStk.setAutoReverse(false);
			translateLeftTitle.setAutoReverse(false);
			translateRightTitle.setAutoReverse(false);
			translateMainTitle.setAutoReverse(false);

			translate.setNode(this.navBox);
			translateStk.setNode(this.stackPane);
			translateLeftTitle.setNode(this.leftTitle);
			translateRightTitle.setNode(this.rightTitle);
			translateMainTitle.setNode(this.appTitle);

			translate.setCycleCount(1);
			translateStk.setCycleCount(1);
			translateLeftTitle.setCycleCount(1);
			translateRightTitle.setCycleCount(1);
			translateMainTitle.setCycleCount(1);

			translate.setOnFinished(evt -> toggle.setDisable(false));

			ft.play();
			translate.play();
			translateStk.play();
			translateLeftTitle.play();
			translateRightTitle.play();
			translateMainTitle.play();
			this.navBox.setVisible(false);

		} else if (toggle.isSelected()) {

			ShapeSceneController.NAV_IS_SHOWING = true;
			// NAV SHOULD BE INVISIBLE
			// System.out.println("I was not selected!");
			this.navBox.setVisible(true);

			this.toggle.setStyle("-fx-background-color:#E0FFFF;-fx-font-size:18px;"); // blueish
			this.toggle.setText("HIDE");
			ft.setFromValue(0);
			ft.setToValue(1);
			ft.setAutoReverse(true);

			translate.setByX(+353);
			translateStk.setByX(+198);
			translateLeftTitle.setByX(+198);
			translateRightTitle.setByX(+198);
			translateMainTitle.setByX(+198);

			translate.setDuration(Duration.millis(1000));
			translateStk.setDuration(Duration.millis(1000));
			translateLeftTitle.setDuration(Duration.millis(1000));
			translateRightTitle.setDuration(Duration.millis(1000));
			translateMainTitle.setDuration(Duration.millis(1000));

			translate.setAutoReverse(false);
			translateStk.setAutoReverse(false);
			translateLeftTitle.setAutoReverse(false);
			translateRightTitle.setAutoReverse(false);
			translateMainTitle.setAutoReverse(false);

			translate.setNode(this.navBox);
			translateStk.setNode(this.stackPane);
			translateLeftTitle.setNode(this.leftTitle);
			translateRightTitle.setNode(this.rightTitle);
			translateMainTitle.setNode(this.appTitle);

			translate.setCycleCount(1);
			translateStk.setCycleCount(1);
			translateLeftTitle.setCycleCount(1);
			translateRightTitle.setCycleCount(1);
			translateMainTitle.setCycleCount(1);

			translate.setOnFinished(evt -> toggle.setDisable(false));

			ft.play();
			translate.play();
			translateStk.play();
			translateLeftTitle.play();
			translateRightTitle.play();
			translateMainTitle.play();

		}

	}

	/**
	 * A method to add a new circle to the Scene, When a person clicks "Add new
	 * circle" It adds a new circle to the stack pane and adds support for a new
	 * circle in the vertical navigation drawer
	 */
	public void addCircle() {
		if (!EXTRA_CIRCLE_ADDED) {
			AddCircleCommand a = new AddCircleCommand(this);
			undoRedoManager.addCommand(a);
			a.execute();

		}

	}

	public void addThirdCircle() {
		ShapeSceneController.EXTRA_CIRCLE_ADDED = true;
		ShapeSceneController.NUM_OF_CIRCLES++;

		// --------------------------Circle Starting to be added
		Circle extraCircle = new Circle(225);

		this.extraCircle = extraCircle;

		this.vennShape.add(extraCircle);
		extraCircle.setOpacity(0.6);

		extraCircle.setBlendMode(BlendMode.MULTIPLY);
		extraCircle.setFill(Color.valueOf("#9ACD32"));

		this.stackPane.getChildren().add(extraCircle);

		StackPane.setMargin(extraCircle, new Insets(250, 0, 0, 0));
		// -------Circle Done Added

		// ----TextField extraTitle starting to be added
		extraTitle = new TextField();
		extraTitle.setLayoutX(1000);
		extraTitle.setLayoutY(751);
		extraTitle.setStyle("-fx-font-size:20px;-fx-background-color: transparent;");
		extraTitle.setPromptText("Diagram #3's Name");
		extraTitle.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			Command a = new EditTextCommand(this, extraTitle, extraTitle.getText());
			undoRedoManager.addCommand(a);
		});
		setTitleColor(titleColors.getValue().toString().substring(2, titleColors.getValue().toString().length() - 2));
		mainScene.getChildren().add(extraTitle);

		// ------

		// ---Label Starting to be Added
		this.extra1Label.setStyle("-fx-font-size:15px;");
		this.scrollBox.getChildren().add(this.extra1Label);

		VBox.setMargin(this.extra1Label, new Insets(10, 0, 0, 30));

		this.extra1LabelColor.setStyle("-fx-font-size:12px;");
		this.scrollBox.getChildren().add(this.extra1LabelColor);
		VBox.setMargin(this.extra1LabelColor, new Insets(10, 0, 0, 50));
		// ----Label Finished Adding

		// ---Color Picker starting to be added
		this.extra1Color = new ColorPicker();
		this.extra1Color.setValue(Color.valueOf(ShapeSceneController.DEFAULT_EXTRACIRCLE_COLOR));
		this.extra1Color.setMinHeight(28);
		this.extra1Color.setMinWidth(137);
		this.extra1Color.setMaxHeight(28);
		this.extra1Color.setMaxWidth(137);

		EditCircleColorCommand editCircleColorCommand = new EditCircleColorCommand(this);
		extra1Color.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				editCircleColorCommand.setCircle(extraCircle);
				editCircleColorCommand.setNewColor(extra1Color.getValue());
				editCircleColorCommand.setOldColor(extraCircle.getFill());
				undoRedoManager.addCommand(editCircleColorCommand);
				editCircleColorCommand.execute();
			}
		});

		this.scrollBox.getChildren().add(this.extra1Color);

		VBox.setMargin(this.extra1Color, new Insets(10, 0, 0, 50));
		// ------Color picker done being added

		// ---Label Starting to be added
		this.scrollBox.getChildren().add(this.extra1LabelSize);
		this.extra1LabelSize.setStyle("-fx-font-size:12px;");

		VBox.setMargin(this.extra1LabelSize, new Insets(10, 0, 0, 50));
		// ---Label Done being added

		// ----Slider Starting to be added
		this.extra1Slider = new Slider();
		this.extra1Slider.setMin(225);
		this.extra1Slider.setMax(250);
		this.extra1Slider.setMinHeight(Control.USE_COMPUTED_SIZE);
		this.extra1Slider.setMinWidth(Control.USE_COMPUTED_SIZE);
		this.extra1Slider.prefWidth(178);
		this.extra1Slider.prefHeight(24);
		this.extra1Slider.setMaxHeight(Control.USE_PREF_SIZE);
		this.extra1Slider.setMaxWidth(Control.USE_PREF_SIZE);

		this.scrollBox.getChildren().add(this.extra1Slider);
		VBox.setMargin(this.extra1Slider, new Insets(5, 0, 0, 50));
		// -----Slider done being added

		// -------Label Starting to be added
		this.extra1HoverLabel.setStyle("-fx-font-size:12px;");
		this.scrollBox.getChildren().add(this.extra1HoverLabel);
		VBox.setMargin(this.extra1HoverLabel, new Insets(10, 0, 0, 50));
		// ------Label done being added

		// -----Color picker starting to be added
		this.extra1ColorHover = new ColorPicker();
		this.extra1ColorHover.setMinHeight(28);
		this.extra1ColorHover.setMinWidth(137);
		this.extra1ColorHover.setMaxHeight(28);
		this.extra1ColorHover.setMaxWidth(137);

		setCircleHover(DEFAULT_EXTRA_HOVER_COLOR, extraCircle);
		EditHoverColorCommand editHoverColorCommand = new EditHoverColorCommand(this);
		extra1ColorHover.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String oldHover = extraHover;
				String newHover = "#" + extra1ColorHover.getValue().toString().substring(2,
						extra1ColorHover.getValue().toString().length() - 2);
				editHoverColorCommand.setCircle(extraCircle);
				editHoverColorCommand.setOldColor(oldHover);
				editHoverColorCommand.setNewColor(newHover);
				undoRedoManager.addCommand(editHoverColorCommand);
				editHoverColorCommand.execute();
			}
		});

		extraCircle.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (ShapeSceneController.EXTRA_CIRCLE_HOVER) {
					extraCircle.setStyle("-fx-stroke:" + extraHover + ";" + " -fx-stroke-width: 5;");

					mainScene.setCursor(Cursor.HAND);
				}
			}
		});

		extraCircle.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				extraCircle.setStyle("-fx-stroke:black;");

				mainScene.setCursor(Cursor.DEFAULT);
			}
		});

//			extra1Slider.valueProperty().addListener(new ChangeListener<Number>() {
//
//				@Override
//				public void changed(ObservableValue<? extends Number> observable, //
//						Number oldValue, Number newValue) {
//
//					extraCircle.setRadius((double) newValue);
//				}
//			});

		extra1Slider.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			extraCircleSize = extraCircle.getRadius();
			if (e.getX() != extra1Slider.getValue()) {
				// clicked random spot
				circleSliderAction(extraCircle);

			}

			extraCircleSize = extra1Slider.getValue();

		});

		extra1Slider.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
			circleSliderAction(extraCircle);

		});

		extra1Slider.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
			extraCircleSize = extraCircle.getRadius();
		});

		this.scrollBox.getChildren().add(this.extra1ColorHover);
		VBox.setMargin(this.extra1ColorHover, new Insets(10, 0, 0, 50));
		// -------------------Color picker done being added

		for (TextField t : this.vennSet) {
			t.toFront();
		}

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Circle 3 Has Been Addded!");
		alert.setHeaderText("Success!");
		alert.setContentText("Support for a third circle has been added in the Apperance pane.");
		alert.showAndWait();

		// ---Label starting to be added
		this.textProperties.setStyle("-fx-font-size:15px;");
		this.scrollBox.getChildren().add(this.textProperties);

		VBox.setMargin(this.textProperties, new Insets(10, 0, 0, 30));
		// ---Label done being added

		// -----Label starting to be added
		this.extraFontSize.setStyle("-fx-font-size:12px;");
		this.scrollBox.getChildren().add(this.extraFontSize);
		VBox.setMargin(this.extraFontSize, new Insets(10, 0, 0, 50));
		// ----Label done being added

		// ----HBox starting to be added
		sliderBox = new HBox();

		this.scrollBox.getChildren().add(this.sliderBox);
		// ---HBox done being added

		// -------Slider starting to be added
		this.extraFontSlider = new Slider();
		this.extraFontSlider.setMin(11);
		this.extraFontSlider.setMax(25);
		this.extraFontSlider.setShowTickLabels(true);
		this.extraFontSlider.setSnapToTicks(true);
		this.extraFontSlider.setMinHeight(Control.USE_COMPUTED_SIZE);
		this.extraFontSlider.setMinWidth(Control.USE_COMPUTED_SIZE);
		this.extraFontSlider.prefWidth(178);
		this.extraFontSlider.prefHeight(24);
		this.extraFontSlider.setMaxHeight(Control.USE_PREF_SIZE);
		this.extraFontSlider.setMaxWidth(Control.USE_PREF_SIZE);
		extraFontSlider.setValue(extraFontSlider.getMax());
		extraTextSize = extraFontSlider.getValue();

//		extraFontSlider.valueProperty().addListener(new ChangeListener<Number>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Number> observable, //
//					Number oldValue, Number newValue) {
//
//				// rightFontTextField.setText(((double) newValue) + "");
//				for (TextField tf : tfLocations.keySet()) {
//					if (tfLocations.get(tf).equals(Location.BOTTOM)) {
//						int newFont = ((int) Math.round((double) newValue));
//						String font = "" + newFont;
//						tf.setStyle("-fx-font-size:" + font + "px;-fx-background-color:transparent;");
//						tf.setMinWidth(Control.USE_PREF_SIZE);
//						tf.setPrefWidth(Control.USE_COMPUTED_SIZE);
//						tf.setMaxWidth(Control.USE_PREF_SIZE);
//					}
//				}
//			}
//		});

		extraFontSlider.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			extraTextSize = getTextSize(Location.BOTTOM);
			if (e.getX() != extra1Slider.getValue()) {
				fontSliderAction(Location.BOTTOM);
			}
			extraTextSize = getTextSize(Location.BOTTOM);
		});
		extraFontSlider.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
			fontSliderAction(Location.BOTTOM);
		});
		extraFontSlider.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
			extraTextSize = getTextSize(Location.BOTTOM);
		});

		this.sliderBox.getChildren().add(this.extraFontSlider);
		HBox.setMargin(this.extraFontSlider, new Insets(5, 0, 0, 50));
		// ----Slider done being added

		// ---Label starting to be added
		this.extraTextColor.setStyle("-fx-font-size:12px;");
		this.scrollBox.getChildren().add(this.extraTextColor);
		VBox.setMargin(this.extraTextColor, new Insets(10, 0, 0, 50));
		// ---Label done being added

		// ----Color picker starting to be added
		this.extraTextColorPicker = new ColorPicker();
		this.extraTextColorPicker.setMinHeight(28);
		this.extraTextColorPicker.setMinWidth(137);
		this.extraTextColorPicker.setMaxHeight(28);
		this.extraTextColorPicker.setMaxWidth(137);
		this.extraTextColorPicker.setValue(Color.valueOf(DEFAULT_EXTRATEXT_COLOR)); // Which is black as well
		extraTextColorPicker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				changeExtraTextColor();
			}
		});
		this.scrollBox.getChildren().add(this.extraTextColorPicker);
		VBox.setMargin(this.extraTextColorPicker, new Insets(10, 0, 0, 50));
		// ----Color picker done being added

		initCircleContext();

		ShapeSceneController.APPLICATION_IS_SAVED = false;
		changesMade();
	}

	protected void changeExtraTextColor() {
		String oldColor = getColor(Location.BOTTOM);
		String newColor = extraTextColorPicker.getValue().toString().substring(2,
				extraTextColorPicker.getValue().toString().length() - 2);
		EditTextColorCommand a = new EditTextColorCommand(this, Location.BOTTOM, oldColor, newColor);
		undoRedoManager.addCommand(a);
		a.execute();
	}

	@FXML
	protected void addTitleListeners() {
		appTitle.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			Command a = new EditTextCommand(this, appTitle, appTitle.getText());
			undoRedoManager.addCommand(a);

		});

		leftTitle.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			Command a = new EditTextCommand(this, leftTitle, leftTitle.getText());
			undoRedoManager.addCommand(a);

		});

		rightTitle.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			Command a = new EditTextCommand(this, rightTitle, rightTitle.getText());
			undoRedoManager.addCommand(a);

		});
	}

	@FXML
	protected void undo() {
		undoRedoManager.undo();
		resetFocus();

	}

	private void resetFocus() {
		if (leftTitle.isFocused() || appTitle.isFocused() || rightTitle.isFocused()
				|| (EXTRA_CIRCLE_ADDED && extraTitle.isFocused())) {
			appTitle.getParent().requestFocus();

		}
	}

	@FXML
	protected void redo() {
		undoRedoManager.redo();
		resetFocus();
	}

	@FXML
	protected void createNew() throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Create New Venn Diagram");
		alert.setContentText(
				"Any Unsaved Changes Will be lost. Are you Sure you would like to create a new Venn Diagram? ");

		ButtonType ok = new ButtonType("Ok");
		ButtonType cancel = new ButtonType("Cancel");

		alert.getButtonTypes().setAll(ok, cancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ok) {
			mainApp.switchScene("shapeScene", null);
		} else {
			return;
		}

		ShapeSceneController.APPLICATION_IS_SAVED = true;
		changesMade();
	}

	@FXML
	private void openExisting() throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Open Existing Venn Diagram");
		alert.setContentText(
				"Any Unsaved Changes Will be lost. Are you sure you would like to open an Existing Venn Diagram?");

		ButtonType ok = new ButtonType("Ok");
		ButtonType cancel = new ButtonType("Cancel");

		alert.getButtonTypes().setAll(ok, cancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ok) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");

			File currentDir = new File(
					System.getProperty("user.home") + File.separator + "VennCreateFiles" + File.separator);

			if (!currentDir.exists()) {
				currentDir = new File(System.getProperty("user.home"));
			}

			fileChooser.setInitialDirectory(currentDir);
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*.csv"));
			File selectedFile = fileChooser.showOpenDialog(null);

			if (selectedFile == null) {
				Alert alert1 = new Alert(AlertType.WARNING);
				alert1.setTitle("Warning Dialog");
				alert1.setHeaderText("CSV Not Chosen");
				alert1.setContentText("Please Chose Correct CSV and try again");
				alert1.showAndWait();
			} else {
				ShapeSceneController.APPLICATION_IS_SAVED = true;
				changesMade();
				mainApp.switchScene("load", selectedFile);
			}
		} else {
			return;
		}
	}

	// Method to close not using menuBar
	public static void closeProgram(WindowEvent e) {
		MainApp.primaryStage.close();
	}

	public void openUserManual() {
		String currentDir = System.getProperty("user.dir");
		try {
			File userManual = new File(currentDir + "\\src\\main\\java\\resources\\Venn-UM.pdf");
			if (userManual.exists()) {
				if (Desktop.isDesktopSupported()) {
					try {
						Desktop.getDesktop().open(userManual);
					} catch (IOException e) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Warning Dialog");
						alert.setHeaderText("An Error Occurred");
						alert.setContentText("User Manual could not be opened.");
						alert.showAndWait();

					}
				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning Dialog");
					alert.setHeaderText("An Error Occurred");
					alert.setContentText("Desktop is Not Supported!");
					alert.showAndWait();
				}
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning Dialog");
				alert.setHeaderText("An Error Occurred");
				alert.setContentText("User Manual Does not Exist!");
				alert.showAndWait();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("An Error Occurred");
			alert.setContentText("User Manual could not be opened.");
			alert.showAndWait();

		}
	}

	@FXML
	private void goTestMode() throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Scene Switch");
		alert.setContentText("Are you sure you would like to go into test mode? Any Unsaved Changes will be lost.");

		ButtonType yes = new ButtonType("Yes");
		ButtonType goBack = new ButtonType("Go Back");

		alert.getButtonTypes().setAll(yes, goBack);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == yes) {
			mainApp.switchScene("testMode", null); // null should be a file
		} else {
			return;
		}
	}

	@FXML
	private void exportJPG() {

	}

	@FXML
	private void exportPNG() {

	}

	public void setText(TextField textField, String newText) {
		// TODO Auto-generated method stub
		textField.setText(newText);

	}

}
