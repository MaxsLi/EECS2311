package controllers;

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

import javafx.scene.Node;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.BlendMode;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Duration;
import models.Location;
import models.VennSet;
import models.VennShape;
import utilities.TextUtils;

public class ShapeSceneController implements Initializable {

	private final static String DEFAULT_BACKGROUND_COLOR = "#F5DEB3";
	private final static String DEFAULT_LEFTCIRCLE_COLOR = "#87CEEB";
	private final static String DEFAULT_RIGHTCIRCLE_COLOR = "#A0522D";
	private final static String DEFAULT_TITLE_COLOR = "#000000";

	public static boolean REMIND_OUTOF_BOUNDS = true;

	public static boolean REMIND_EMPTY_TEXTFIELD = true;

	@FXML
	public Label sideLabel;

	@FXML
	private AnchorPane mainScene;

	@FXML
	private StackPane stackPane;

	@FXML
	private Circle leftCircle;

	@FXML
	private Circle rightCircle;

	@FXML
	private Button addBttn;

	@FXML
	private Button saveBttn;

	@FXML
	private Button removeButton;

	@FXML
	private TextField diagramText;

	@FXML
	private TextField appTitle;

	@FXML
	private ColorPicker leftColorPicker;

	@FXML
	private ColorPicker rightColorPicker;

	@FXML
	private ContextMenu textFieldContext;

	@FXML
	private TextField sideAdded;

	private MainApp mainApp;

	private String currentFileName;

	private File currentFile;

	@FXML
	private TextField leftTitle;

	@FXML
	private TextField rightTitle;

	@FXML
	private ToggleButton toggle;

	@FXML
	private ListView<String> itemList;

	@FXML
	private ColorPicker backgroundColor;

	@FXML
	private ColorPicker titleColors;

	@FXML
	private Slider leftSlider;

	@FXML
	private Slider rightSlider;

	@FXML
	private ColorPicker leftHoverColor;

	@FXML
	private ColorPicker rightHoverColor;

	@FXML
	private ButtonBar listBttns;

	@FXML
	private Button clearListBttn;

	@FXML
	private Button removeItemButton;

	@FXML
	private Button addCircleBttn;

	@FXML
	private VBox navBox;

	@FXML
	private TitledPane appearancePane;

	@FXML
	private VBox scrollBox;

	// -----------------------Extra Circle #1's Properties May or may not be needed
	private Slider extra1Slider;
	private ColorPicker extra1Color;
	private Label extra1Label = new Label("Circle 3");
	private Label extra1LabelColor = new Label("Extra Circle #1 Color");
	private Label extra1LabelSize = new Label("Extra Circle #1 Size");
	private Label extra1HoverLabel = new Label("Hover Color");
	private ColorPicker exrtra1ColorHover;
	private Separator extra1Seperator;

	public static boolean EXTRA_CIRCLE_ADDED = false;
	// --------------------------------------

	/**
	 * An Set of `TextLabel`s
	 */
	private VennSet vennSet;

	private HashMap<Location, TextField> tfLocations = new HashMap<>();

	/**
	 * A static variable to allow the user to choice if they want to continue to be
	 * reminded that they're placing a textfield out of bounds
	 */

	/**
	 * An Set of `Shape`s
	 */
	private VennShape vennShape;

	private double orgSceneX;
	private double orgSceneY;
	private double orgTranslateX;
	private double orgTranslateY;

	static Color LEFTCIRCLECOLOR = Color.valueOf("#ff8a8a");
	static Color RIGHTCIRCLECOLOR = Color.valueOf("#a7ff8f");

	public ShapeSceneController() {
		// Note that function `initialize` will do the init
	}

	public static final String COMMA = ",";

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
			if(newText.charAt(newText.length()-1) == ',') {
				newText = newText.substring(0, newText.length()-1);
			}
			
			TextField newTextField = new TextField();

			newTextField.setEditable(false);
			newTextField.resizeRelocate(leftCircle.getCenterX(), leftCircle.getCenterY(), 1, 1);

			this.addAutoResize(newTextField);

			newTextField.setStyle("-fx-background-color:transparent; -fx-border-color:red; ");

			this.addDragEvent(newTextField);
			this.addContext(newTextField);

			this.itemList.getItems().add(newText);

			newTextField.setText(newText);
			this.stackPane.getChildren().add(newTextField);
			this.vennSet.add(newTextField);
			this.sideAdded.clear();
			this.diagramText.clear();
		}
	}

	@FXML
	private void clearList() {
		this.itemList.getItems().remove(0, this.itemList.getItems().size());
	}

	/**
	 * Adds Drag Events to created TextFields
	 * 
	 * @param textField the TextField to be added
	 */
	private void addDragEvent(TextField textField) {
		textField.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {

			this.diagramText.clear();
			orgSceneX = e.getSceneX();
			orgSceneY = e.getSceneY();
			orgTranslateX = textField.getTranslateX();
			orgTranslateY = textField.getTranslateY();

			textField.toFront();

		});

		/*
		 * On Mouse Drag Moves the TextField Around the Screen
		 */
		textField.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {

			double offsetX = e.getSceneX() - orgSceneX;
			double offsetY = e.getSceneY() - orgSceneY;
			double newTranslateX = orgTranslateX + offsetX;
			double newTranslateY = orgTranslateY + offsetY;

			textField.setTranslateX(newTranslateX);
			textField.setTranslateY(newTranslateY);

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
				tfLocations.put(Location.INTERSECTING_ALL, textField);
			}

			else if (textBoxLocation == Location.INTERSECTING_BOTTOM_LEFT) {
				sideAdded.setText("Intersecting Left & Bottom!");
				sideAdded.setEditable(false);
				sideAdded.setStyle("-fx-text-fill: purple; -fx-font-size: 18px;-fx-background-color:transparent;");
				tfLocations.put(Location.INTERSECTING_BOTTOM_LEFT, textField);
			} else if (textBoxLocation == Location.INTERSECTING_LEFT_RIGHT) {
				sideAdded.setText("Intersecting Left & Right!");
				sideAdded.setEditable(false);
				sideAdded.setStyle("-fx-text-fill: purple; -fx-font-size: 18px;-fx-background-color:transparent;");
				tfLocations.put(Location.INTERSECTING_LEFT_RIGHT, textField);
			} else if (textBoxLocation == Location.INTERSECTING_BOTTOM_RIGHT) {
				sideAdded.setText("Intersecting Right & Bottom!");
				sideAdded.setEditable(false);
				sideAdded.setStyle("-fx-text-fill: purple; -fx-font-size: 18px;-fx-background-color:transparent;");
				tfLocations.put(Location.INTERSECTING_BOTTOM_RIGHT, textField);
			}

			else if (textBoxLocation == Location.LEFT) {
				sideAdded.setText("Left!");
				sideAdded.setEditable(false);
				sideAdded.setStyle("-fx-text-fill: blue; -fx-font-size: 18px;-fx-background-color:transparent;");
				tfLocations.put(Location.LEFT, textField);
			} else if (textBoxLocation == Location.RIGHT) {
				sideAdded.setText("Right!");
				sideAdded.setEditable(false);
				sideAdded.setStyle("-fx-text-fill: red; -fx-font-size: 18px;-fx-background-color:transparent;");
				tfLocations.put(Location.RIGHT, textField);
			} else if (textBoxLocation == Location.BOTTOM) {
				sideAdded.setText("Bottom!");
				sideAdded.setEditable(false);
				sideAdded.setStyle("-fx-text-fill: red; -fx-font-size: 18px;-fx-background-color:transparent;");
				tfLocations.put(Location.BOTTOM, textField);
			}

		});

	}

	/**
	 * A Method that gives a right-click feature on each textField added to the
	 * screen, On right-click of a textfield added, gives a contextMenu
	 * 
	 * @param textField the TextField to be added
	 */
	private void addContext(TextField textField) {
		ContextMenu context = new ContextMenu();
		MenuItem delete = new MenuItem("Delete");
		MenuItem edit = new MenuItem("Edit");
		context.getItems().add(delete);
		context.getItems().add(edit);
		textField.setContextMenu(context);

		delete.setOnAction((event) -> stackPane.getChildren().remove(textField));

		edit.setOnAction((event) -> textField.setEditable(true));

	}

	/**
	 * A Method that gives allows the given TextField auto-resize its width
	 * according to the content.
	 *
	 * @param textField the TextField to be added
	 */
	private void addAutoResize(TextField textField) {
		textField.textProperty().addListener((ob, o, n) -> {
			// expand the textfield
			textField.setMaxWidth(TextUtils.computeTextWidth(textField.getFont(), textField.getText(), 0.0D) + 20);
		});
	}

	/**
	 * A Method that loads all comma delimited rows from save.csv and puts them on
	 * the screen
	 */
	public void loadVenn(File fileToLoad) {

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

				this.appTitle.setText(firstLineInfo[0]);
				this.leftTitle.setText(firstLineInfo[1]);
				this.rightTitle.setText(firstLineInfo[2]);

				if (firstLineInfo[3].equals("0xffffffff")) {
					this.leftCircle.setFill(Color.valueOf(ShapeSceneController.DEFAULT_LEFTCIRCLE_COLOR));
				} else {
					this.leftCircle.setFill(Paint.valueOf(firstLineInfo[3]));
				}

				if (firstLineInfo[4].equals("0xffffffff")) {
					this.rightCircle.setFill(Color.valueOf(ShapeSceneController.DEFAULT_RIGHTCIRCLE_COLOR));
				} else {
					this.rightCircle.setFill(Paint.valueOf(firstLineInfo[4]));
				}

				if (firstLineInfo[5].equals("0xffffffff")) {
					this.mainScene
							.setStyle("-fx-background-color:" + ShapeSceneController.DEFAULT_BACKGROUND_COLOR + ";");
				} else {
					this.mainScene.setStyle("-fx-background-color:#" + Paint.valueOf(firstLineInfo[5]).toString()
							.substring(2, Paint.valueOf(firstLineInfo[5]).toString().length() - 2) + ";");
				}

				if (firstLineInfo[6].equals("0xffffffff")) {
					this.appTitle.setStyle("-fx-text-fill:" + ShapeSceneController.DEFAULT_TITLE_COLOR + ";"
							+ "-fx-background-color: transparent; -fx-font-size:25px;");
					this.leftTitle.setStyle("-fx-text-fill:" + ShapeSceneController.DEFAULT_TITLE_COLOR + ";"
							+ "-fx-background-color: transparent;-fx-font-size:20px;");
					this.rightTitle.setStyle("-fx-text-fill:" + ShapeSceneController.DEFAULT_TITLE_COLOR + ";"
							+ "-fx-background-color: transparent;-fx-font-size:20px;");
				} else {
					this.appTitle.setStyle("-fx-text-fill:#"
							+ Paint.valueOf(firstLineInfo[6]).toString().substring(2,
									Paint.valueOf(firstLineInfo[6]).toString().length() - 2)
							+ ";" + "-fx-background-color: transparent; -fx-font-size:25px;");
					this.leftTitle.setStyle("-fx-text-fill:#"
							+ Paint.valueOf(firstLineInfo[6]).toString().substring(2,
									Paint.valueOf(firstLineInfo[6]).toString().length() - 2)
							+ ";" + "-fx-background-color: transparent;-fx-font-size:20px;");
					this.rightTitle.setStyle("-fx-text-fill:#"
							+ Paint.valueOf(firstLineInfo[6]).toString().substring(2,
									Paint.valueOf(firstLineInfo[6]).toString().length() - 2)
							+ ";" + "-fx-background-color: transparent;-fx-font-size:20px;");

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
				if (lineCounter == 1) { // Make sure to not touch first Line
					try {
						String[] firstLineInfo = s.split(COMMA);

						// System.out.println(Arrays.toString(firstLineInfo));

						this.appTitle.setText(firstLineInfo[0]);
						this.leftTitle.setText(firstLineInfo[1]);
						this.rightTitle.setText(firstLineInfo[2]);
						this.leftCircle.setFill(Paint.valueOf(firstLineInfo[3]));
						this.rightCircle.setFill(Paint.valueOf(firstLineInfo[4]));

						// System.out.println("The line number is: " + lineCounter);

						lineCounter++;
						continue;

					} catch (IllegalArgumentException ex) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Warning Dialog");
						alert.setHeaderText("Trouble Parsing First Line");
						alert.setContentText(
								"There is something wrong with the first line of the CSV File, and cannot be parsed");
						alert.showAndWait();
					}

				}
				parts = s.split(COMMA);

				// System.out.println("The line number is: " + lineCounter);

				lineCounter++;
				tf = new TextField();
				this.addAutoResize(tf);
				this.addContext(tf);
				tf.setText(parts[0]); // parts[0] is the text column of the line
				tf.setEditable(false);
				tf.resizeRelocate(0, 0, 1, 1);
				tf.resize(50, 50);

				try {

					double textFieldX = Double.parseDouble(parts[1]);
					double textFieldY = Double.parseDouble(parts[2]);
					tf.setTranslateX(textFieldX);
					tf.setTranslateY(textFieldY);
					stackPane.getChildren().add(tf);
					this.vennSet.add(tf);
					tf.setStyle("-fx-background-color:transparent; -fx-border-color:red; ");

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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		AppAttributes appSaver = new AppAttributes(this.appTitle.getText(), this.leftTitle.getText(),
				this.rightTitle.getText(), this.leftCircle.getFill(), this.rightCircle.getFill(),
				this.backgroundColor.getValue(), this.titleColors.getValue());

		String dummyLine = "TEXT COLUMN" + COMMA + "TextField X Coor" + COMMA + "TextField Y Coor" + COMMA
				+ "Location of TextField" + COMMA + "<--DO NOT MODIFY THIS LINE"; // Program not reading Line two,
		// adding dummyLine

		/*
		 * Set the First Line of the CSV File Accordingly
		 */
		String firstLine = appSaver.attrAppTitle + COMMA + appSaver.attrLeftTitle + COMMA + appSaver.attrRightTitle
				+ COMMA + appSaver.attrLeftColor.toString() + COMMA + appSaver.attrRightColor.toString() + COMMA
				+ appSaver.attrSceneColor.toString() + COMMA + appSaver.attrTitleColor.toString() + COMMA
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
					Date today = new Date();
					titleOfApp = "untitledVC:Made on[" + today.toString() + "]";
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
					pw.println(textField.getText() + COMMA + textField.getTranslateX() + COMMA
							+ textField.getTranslateY() + COMMA + this.vennSet.getLocation(textField));

					writeIndexer++;
				} catch (Exception excep) {
					writeIndexer++;
					continue;
				}
				pw.flush();
			}
			pw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	public void saveVennBttn() {
		saveVenn(this.getTextFields());
	}

	/**
	 * An Object to store All important Details on an App Instance
	 *
	 */
	private class AppAttributes {
		String attrAppTitle = appTitle.getText();
		String attrLeftTitle = leftTitle.getText();
		String attrRightTitle = rightTitle.getText();
		Paint attrLeftColor = leftCircle.getFill();
		Paint attrRightColor = rightCircle.getFill();
		Paint attrSceneColor = backgroundColor.getValue();
		Paint attrTitleColor = titleColors.getValue();

		public AppAttributes(String attributeAppTitle, String leftTitle, String rightTitle, Paint leftColor,
				Paint rightColor, Paint attrSceneColor, Paint attrTitleColor) {
			super();
			if (this.attrAppTitle.trim().isEmpty()) {
				this.attrAppTitle = "DefaultTitle";
			} else {
				this.attrAppTitle = attributeAppTitle;
			}

			if (this.attrLeftTitle.trim().isEmpty()) {
				this.attrLeftTitle = "DefaultLeftTitle";
			} else {
				this.attrLeftTitle = leftTitle;
			}

			if (this.attrRightTitle.trim().isEmpty()) {
				this.attrRightTitle = "DefaultRightTitle";
			} else {
				this.attrRightTitle = rightTitle;
			}

			this.attrLeftColor = leftColor;
			this.attrRightColor = rightColor;
			this.attrSceneColor = backgroundColor.getValue();
			this.attrLeftColor = titleColors.getValue();

		}

	}

	/**
	 * A method that changes the color of the leftCircle
	 */
	public void changeLeftColor() {
		leftCircle.setFill(leftColorPicker.getValue());
	}

	/**
	 * A method that changes the color of the rightCircle
	 */
	public void changeRightColor() {
		rightCircle.setFill(rightColorPicker.getValue());
	}

	public void changebackgroundColor() {
		mainScene.setStyle("-fx-background-color: #"
				+ backgroundColor.getValue().toString().substring(2, backgroundColor.getValue().toString().length() - 2)
				+ ";");
	}

	public void startHoverLeft() {
		leftCircle.setStyle("-fx-stroke:#"
				+ leftHoverColor.getValue().toString().substring(2, leftHoverColor.getValue().toString().length() - 2)
				+ ";" + " -fx-stroke-width: 5;");

	}

	public void endHoverLeft() {
		leftCircle.setStyle("-fx-stroke:black;");
	}

	public void startHoverRight() {
		rightCircle.setStyle("-fx-stroke:#"
				+ rightHoverColor.getValue().toString().substring(2, rightHoverColor.getValue().toString().length() - 2)
				+ ";" + " -fx-stroke-width: 5;");

	}

	public void endHoverRight() {
		rightCircle.setStyle("-fx-stroke:black;");
	}

	public void changetitleColors() {
		appTitle.setStyle("-fx-background-color: transparent;\n-fx-text-fill: #"
				+ titleColors.getValue().toString().substring(2, titleColors.getValue().toString().length() - 2) + ";"
				+ "-fx-font-size:25px;");
		leftTitle.setStyle("-fx-background-color: transparent;\n-fx-text-fill: #"
				+ titleColors.getValue().toString().substring(2, titleColors.getValue().toString().length() - 2) + ";"
				+ "-fx-font-size:20px;");
		rightTitle.setStyle("-fx-background-color: transparent;\n-fx-text-fill: #"
				+ titleColors.getValue().toString().substring(2, titleColors.getValue().toString().length() - 2) + ";"
				+ "-fx-font-size:20px;");
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

		// this.drawerHolder.setSidePane(this.navBox);
		this.navBox.setVisible(false);
		this.toggle.setText("SHOW");
		this.toggle.setStyle("-fx-font-size:18");
		this.toggle.setStyle("-fx-background-color:#FF69B4");

		// Adding Listener to value property.
		leftSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, //
					Number oldValue, Number newValue) {

				leftCircle.setRadius((double) newValue);
			}
		});

		// Adding Listener to value property.
		rightSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, //
					Number oldValue, Number newValue) {

				rightCircle.setRadius((double) newValue);
			}
		});

	}

	/**
	 * A method to Translate items on screen
	 */
	@FXML
	private void toggleDrawer() {
		this.toggle.setStyle("-fx-font-size:18");
		TranslateTransition translate = new TranslateTransition();
		TranslateTransition translateStk = new TranslateTransition();
		TranslateTransition translateLeftTitle = new TranslateTransition();
		TranslateTransition translateRightTitle = new TranslateTransition();
		TranslateTransition translateMainTitle = new TranslateTransition();

		FadeTransition ft = new FadeTransition(Duration.millis(1000), this.navBox);
		if (!toggle.isSelected()) {// NAV SHOULD BE VISIBLE
			// System.out.println("I was selected!");
			this.toggle.setStyle("-fx-background-color:#FF69B4"); // pinkish
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

			translate.setAutoReverse(true);
			translateStk.setAutoReverse(true);
			translateLeftTitle.setAutoReverse(true);
			translateRightTitle.setAutoReverse(true);
			translateMainTitle.setAutoReverse(true);

			translate.setNode(this.navBox);
			translateStk.setNode(this.stackPane);
			translateLeftTitle.setNode(this.leftTitle);
			translateRightTitle.setNode(this.rightTitle);
			translateMainTitle.setNode(this.appTitle);

			ft.play();
			translate.play();
			translateStk.play();
			translateLeftTitle.play();
			translateRightTitle.play();
			translateMainTitle.play();
			this.navBox.setVisible(false);

		} else if (toggle.isSelected()) {// NAV SHOULD BE INVISIBLE
			// System.out.println("I was not selected!");
			this.navBox.setVisible(true);

			this.toggle.setStyle("-fx-background-color:#E0FFFF"); // blueish
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

			translate.setAutoReverse(true);
			translateStk.setAutoReverse(true);
			translateLeftTitle.setAutoReverse(true);
			translateRightTitle.setAutoReverse(true);
			translateMainTitle.setAutoReverse(true);

			translate.setNode(this.navBox);
			translateStk.setNode(this.stackPane);
			translateLeftTitle.setNode(this.leftTitle);
			translateRightTitle.setNode(this.rightTitle);
			translateMainTitle.setNode(this.appTitle);

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
			ShapeSceneController.EXTRA_CIRCLE_ADDED = true;

			Circle extraCircle = new Circle(225);

			this.vennShape.add(extraCircle);

			extraCircle.setBlendMode(BlendMode.MULTIPLY);
			extraCircle.setFill(Color.valueOf("#9ACD32"));

			this.stackPane.getChildren().add(extraCircle);

			StackPane.setMargin(extraCircle, new Insets(250, 0, 0, 0));

			this.extra1Label.setStyle("-fx-font-size:15px;");
			this.scrollBox.getChildren().add(this.extra1Label);

			VBox.setMargin(this.extra1Label, new Insets(10, 0, 0, 30));

			this.extra1LabelColor.setStyle("-fx-font-size:12px;");
			this.scrollBox.getChildren().add(this.extra1LabelColor);
			VBox.setMargin(this.extra1LabelColor, new Insets(10, 0, 0, 50));

			this.extra1Color = new ColorPicker();
			this.extra1Color.setMinHeight(28);
			this.extra1Color.setMinWidth(137);
			this.extra1Color.setMaxHeight(28);
			this.extra1Color.setMaxWidth(137);

			extra1Color.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					extraCircle.setFill(extra1Color.getValue());
				}
			});

			this.scrollBox.getChildren().add(this.extra1Color);

			VBox.setMargin(this.extra1Color, new Insets(10, 0, 0, 50));

			this.scrollBox.getChildren().add(this.extra1LabelSize);
			this.extra1LabelSize.setStyle("-fx-font-size:12px;");

			VBox.setMargin(this.extra1LabelSize, new Insets(10, 0, 0, 50));

			this.extra1Slider = new Slider();
			this.extra1Slider.setMin(225);
			this.extra1Slider.setMax(300);
			this.extra1Slider.setMinHeight(Control.USE_COMPUTED_SIZE);
			this.extra1Slider.setMinWidth(Control.USE_COMPUTED_SIZE);
			this.extra1Slider.prefWidth(178);
			this.extra1Slider.prefHeight(24);
			this.extra1Slider.setMaxHeight(Control.USE_PREF_SIZE);
			this.extra1Slider.setMaxWidth(Control.USE_PREF_SIZE);

			this.scrollBox.getChildren().add(this.extra1Slider);
			VBox.setMargin(this.extra1Slider, new Insets(5, 0, 0, 50));

			this.extra1HoverLabel.setStyle("-fx-font-size:12px;");
			this.scrollBox.getChildren().add(this.extra1HoverLabel);
			VBox.setMargin(this.extra1HoverLabel, new Insets(10, 0, 0, 50));

			this.exrtra1ColorHover = new ColorPicker();
			this.exrtra1ColorHover.setMinHeight(28);
			this.exrtra1ColorHover.setMinWidth(137);
			this.exrtra1ColorHover.setMaxHeight(28);
			this.exrtra1ColorHover.setMaxWidth(137);

			exrtra1ColorHover.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					extraCircle.setFill(extra1Color.getValue());
				}
			});

			extraCircle.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					extraCircle.setStyle("-fx-stroke:#"
							+ exrtra1ColorHover.getValue().toString().substring(2,
									exrtra1ColorHover.getValue().toString().length() - 2)
							+ ";" + " -fx-stroke-width: 5;");
				}
			});

			extraCircle.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					extraCircle.setStyle("-fx-stroke:black;");
				}
			});

			extra1Slider.valueProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable, //
						Number oldValue, Number newValue) {

					extraCircle.setRadius((double) newValue);
				}
			});

			this.scrollBox.getChildren().add(this.exrtra1ColorHover);
			VBox.setMargin(this.exrtra1ColorHover, new Insets(10, 0, 0, 50));

			for (TextField t : this.vennSet) {
				t.toFront();
			}

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Circle 3 Has Been Addded!");
			alert.setHeaderText("Success!");
			alert.setContentText("Support for a third circle has been added in the Apperance pane.");
			alert.showAndWait();

		}
	}

}
