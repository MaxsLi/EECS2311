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
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import application.MainApp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import models.Location;
import models.VennSet;

public class ShapeSceneController implements Initializable {

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

	@FXML
	private TextField leftTitle;

	@FXML
	private TextField rightTitle;

	/**
	 * An Set that stores Strings for the leftCircle
	 */
	private VennSet leftSet = new VennSet();

	/**
	 * An Set that stores Strings for the rightCircle
	 */
	private VennSet rightSet = new VennSet();

	/**
	 * An Set that stores Strings for the middleCircle
	 */
	private VennSet intersectionSet = new VennSet();

	/**
	 * A Map that stores the location of all textFields in the application (where
	 * they were placed)
	 */
	private Map<String, Location> masterMap;

	private double orgSceneX;
	private double orgSceneY;
	private double orgTranslateX;
	private double orgTranslateY;

	/**
	 * A List that stores all textFields on the scene
	 */
	private ArrayList<TextField> current;

	public ShapeSceneController() {
		current = new ArrayList<>();
		masterMap = new HashMap<>();
	}

	public static final String COMMA = ",";

	/**
	 * On click, creates a textArea which can be dragged into Respective Circle
	 */
	public void addTextToDiagram() {
		if (this.diagramText.getText().isEmpty() || this.diagramText.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("Empty TextField");
			alert.setContentText("Please enter some Text to the TextField under the Venn Diagram");
			alert.showAndWait();

		} else {
			String newText = this.diagramText.getText();

			TextField newTextBox = new TextField(newText);
			newTextBox.setEditable(false);
			newTextBox.resizeRelocate(leftCircle.getCenterX(), leftCircle.getCenterY(), 1, 1);

			if (newText.length() <= 3) {
				newTextBox.setMaxWidth(newText.length() * 20);
			}
			newTextBox.setMaxWidth(newText.length() * 20);

			stackPane.getChildren().add(newTextBox);
			current.add(newTextBox);
			addDragEvent(newTextBox);
			addContext(newTextBox);
		}

	}

	/**
	 * Adds Drag Events to created TextFields
	 * 
	 * @param newTextBox
	 */
	private void addDragEvent(TextField newTextBox) {
		newTextBox.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {

			this.diagramText.clear();
			orgSceneX = e.getSceneX();
			orgSceneY = e.getSceneY();
			orgTranslateX = newTextBox.getTranslateX();
			orgTranslateY = newTextBox.getTranslateY();

			newTextBox.toFront();

			/*
			 * On Every MousePress on an Added TextField, if the VennSet already contains
			 * the text, remove it. On Mouse Release it will be added back Anyway
			 */
			if (leftSet.contains(newTextBox.getText())) {
				leftSet.remove(newTextBox.getText());
			} else if (rightSet.contains(newTextBox.getText())) {
				rightSet.remove(newTextBox.getText());
			} else if (intersectionSet.contains(newTextBox.getText())) {
				intersectionSet.remove(newTextBox.getText());
			}

		});

		/**
		 * On Mouse Drag Moves the TextField Around the Screen
		 */
		newTextBox.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {

			double offsetX = e.getSceneX() - orgSceneX;
			double offsetY = e.getSceneY() - orgSceneY;
			double newTranslateX = orgTranslateX + offsetX;
			double newTranslateY = orgTranslateY + offsetY;

			newTextBox.setTranslateX(newTranslateX);
			newTextBox.setTranslateY(newTranslateY);

		});

		/**
		 * On Mouse Release Calculates Distances with circles. to determine where this
		 * circle has been placed
		 * 
		 * Uses Basic Distance Between point calculations to do so
		 * 
		 * Stores the string contents of the textField in leftSet, rightSet or
		 * intersectionSet
		 */
		newTextBox.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {

			/*
			 * Point2D, points to represent locations on the scene
			 */
			Point2D leftCenter = leftCircle.localToParent(leftCircle.getCenterX(), leftCircle.getCenterY());
			Point2D rightCenter = rightCircle.localToParent(rightCircle.getCenterX(), rightCircle.getCenterY());

			double leftRadius = leftCircle.getRadius();
			double rightRadius = rightCircle.getRadius();

			Point2D textFieldLocation = newTextBox.localToParent(newTextBox.getScene().getX(),
					newTextBox.getScene().getY());

			double distanceToLeft = textFieldLocation.distance(leftCenter);
			double distanceToRight = textFieldLocation.distance(rightCenter);

			/*
			 * If TextField location is within radial distance of the left and right circle,
			 * it must be somewhere in the intersection of the two circles
			 */
			if (distanceToLeft <= leftRadius && distanceToRight <= rightRadius) {
				intersectionSet.add(newTextBox.getText());
				sideAdded.setText("Intersection!");
				sideAdded.setEditable(false);
				masterMap.put(newTextBox.getText(), Location.MIDDLE);
				sideAdded.setStyle("-fx-text-fill: purple; -fx-font-size: 25px;");
			}
			/*
			 * Else if, if its within radial distance of the left Circle, it must be in the
			 * left circle
			 */
			else if (distanceToLeft <= leftRadius) {
				leftSet.add(newTextBox.getText());
				sideAdded.setText("Left!");
				sideAdded.setEditable(false);
				masterMap.put(newTextBox.getText(), Location.LEFT);
				sideAdded.setStyle("-fx-text-fill: blue; -fx-font-size: 25px;");
			}
			/*
			 * Else if, if its within radial distance of the left Circle, it must be in the
			 * right circle
			 */
			else if (distanceToRight <= rightRadius) {
				rightSet.add(newTextBox.getText());
				sideAdded.setText("Right!");
				sideAdded.setEditable(false);
				masterMap.put(newTextBox.getText(), Location.RIGHT);
				sideAdded.setStyle("-fx-text-fill: red; -fx-font-size: 25px;");
			}

			/*
			 * Else, it must be outside the circles, give a warning.
			 */
			else {

				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning Dialog");
				alert.setHeaderText("TextField Out of Bounds");
				alert.setContentText(
						"If you dont place the textField inside the bounds, I wont be able to add it to the CSV File.");
				alert.showAndWait();
			}

		});

	}

	/**
	 * A Method that gives a right-click feature on each textField added to the
	 * screen, On right-click of a textfield added, gives a contextMenu
	 * 
	 * @param text
	 */
	public void addContext(TextField text) {
		ContextMenu context = new ContextMenu();
		MenuItem delete = new MenuItem("Delete");
		MenuItem edit = new MenuItem("Edit");
		context.getItems().add(delete);
		context.getItems().add(edit);
		text.setContextMenu(context);

		delete.setOnAction((event) -> {
			stackPane.getChildren().remove(text);
		});

		edit.setOnAction((event) -> {
			text.setEditable(true);
		});

	}

	/**
	 * A Method that loads all comma delimeted rows from save.csv and puts them on
	 * the screen
	 */
	public void loadVenn(String fileName) {

		try {
			FileReader fr = new FileReader(System.getProperty("user.dir") + File.separator + "src" + File.separator
					+ "main" + File.separator + "java" + File.separator + "resources" + File.separator + fileName);
			BufferedReader br = new BufferedReader(fr);

			this.currentFileName = fileName.substring(0, fileName.length() - 4); // Cuts off the ".csv" extension
			System.out.println(this.currentFileName);

			String[] parts;
			String s;
			TextField tf;
			int lineCounter = 1;

			try {
				String[] firstLineInfo = br.readLine().split(COMMA);

				System.out.println(Arrays.toString(firstLineInfo));

				this.appTitle.setText(firstLineInfo[0]);
				this.leftTitle.setText(firstLineInfo[1]);
				this.rightTitle.setText(firstLineInfo[2]);
				this.leftCircle.setFill(Paint.valueOf(firstLineInfo[3]));
				this.rightCircle.setFill(Paint.valueOf(firstLineInfo[4]));
				System.out.println(lineCounter);
				lineCounter++;

			} catch (IllegalArgumentException ex) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning Dialog");
				alert.setHeaderText("Trouble Parsing First Line");
				alert.setContentText(
						"There is something wrong with the first line of the CSV File, and cannot be parsed");
				alert.showAndWait();
			}

			boolean linePrinted = true; // Dont touch the first Line

			while ((s = br.readLine()) != null) {
				if (lineCounter == 1) { // Make sure to not touch first Line
					try {
						String[] firstLineInfo = s.split(COMMA);

						System.out.println(Arrays.toString(firstLineInfo));

						this.appTitle.setText(firstLineInfo[0]);
						this.leftTitle.setText(firstLineInfo[1]);
						this.rightTitle.setText(firstLineInfo[2]);
						this.leftCircle.setFill(Paint.valueOf(firstLineInfo[3]));
						this.rightCircle.setFill(Paint.valueOf(firstLineInfo[4]));
						System.out.println("The line number is: " + lineCounter);
						lineCounter++;
						linePrinted = false;
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

				System.out.println("The line number is: " + lineCounter);
				lineCounter++;
				tf = new TextField();
				tf.setText(parts[0]); // parts[0] is the text column of the line
				tf.setEditable(false);
				tf.resizeRelocate(0, 0, 1, 1);
				tf.resize(50, 50);
				tf.setMaxWidth(tf.getText().length() * 20);

				try {

					double textFieldX = Double.parseDouble(parts[1]);
					double textFieldY = Double.parseDouble(parts[2]);
					tf.setTranslateX(textFieldX);
					tf.setTranslateY(textFieldY);
					stackPane.getChildren().add(tf);

					current.add(tf);
					System.out.println(parts[3]);
					masterMap.put(parts[0], Location.valueOf(parts[3].trim()));

					addDragEvent(tf);

					System.out.println(lineCounter);
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
					continue;
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
		return current;
	}

	/**
	 * A method that saves all text-fields on scene to a csv file
	 * 
	 * @param write A List with all the TextFields that are to be written to the
	 *              save.csv file
	 */
	public void saveVenn(ArrayList<TextField> write) {
		AppAtributes appSaver = new AppAtributes(this.appTitle.getText(), this.leftTitle.getText(),
				this.rightTitle.getText(), this.leftCircle.getFill(), this.rightCircle.getFill());

		String dummyLine = "This" + "," + "is" + "," + "a" + "," + "DummyLine---"; // Program not reading Line two,
																					// adding dummyLine

		/*
		 * Set the First Line of the CSV File Accordingly
		 */
		String firstLine = appSaver.attriAppTitle + "," + appSaver.attriLeftTitle + "," + appSaver.attriRightTitle + ","
				+ appSaver.attriLeftColor.toString() + "," + appSaver.attriRightColor.toString() + ","
				+ "<---DO NOT MODIFY THIS LINE" + "\n" + dummyLine;

		try {
			String titleOfApp;
			FileWriter fw = null;

			/*
			 * Upon Loading a Previous file, this.currentFileName is initialized. If it is
			 * created brand new, this.currentFileName will be null and ill know to make a
			 * new file and not write an an existing one
			 */
			if (this.currentFileName == null) {
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
				fw = new FileWriter(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
						+ File.separator + "java" + File.separator + "resources" + File.separator + titleOfApp + ".csv",
						false);
			} catch (FileNotFoundException FNFE) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("File Could Not be Saved to");
				alert.setContentText(FNFE.toString());
				alert.showAndWait();

			}
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);

			pw.println(firstLine); // Prints important AppAtributtes to the first Line
			boolean linePrinted = true;

			int writeIndexer = 0; // A Indexer for the write ArrayList argument

			while (writeIndexer < write.size()) {
				if (linePrinted == true) {
					linePrinted = false;
					continue;
				}

				TextField textField = write.get(writeIndexer);

				try { // If Nothing Was Added on GetExisting, the program crashes, this is so it
						// doesn't crash
					System.out.println(masterMap.get(textField.getText()));
					pw.println(textField.getText() + COMMA + textField.getTranslateX() + COMMA
							+ textField.getTranslateY() + COMMA + masterMap.get(textField.getText()).toString());

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

	/**
	 * An Object to store All important Details on an App Instance
	 * 
	 *
	 */
	private class AppAtributes {
		String attriAppTitle = appTitle.getText();
		String attriLeftTitle = leftTitle.getText();
		String attriRightTitle = rightTitle.getText();
		Paint attriLeftColor = leftCircle.getFill();
		Paint attriRightColor = rightCircle.getFill();

		public AppAtributes(String attributeAppTitle, String leftTitle, String rightTitle, Paint leftColor,
				Paint rightColor) {
			super();
			if (this.attriAppTitle.trim().isEmpty()) {
				this.attriAppTitle = "DefaultTitle";
			} else {
				this.attriAppTitle = attributeAppTitle;
			}

			if (this.attriLeftTitle.trim().isEmpty()) {
				this.attriLeftTitle = "DefaultLeftTitle";
			} else {
				this.attriLeftTitle = leftTitle;
			}

			if (this.attriRightTitle.trim().isEmpty()) {
				this.attriRightTitle = "DefaultRightTitle";
			} else {
				this.attriRightTitle = rightTitle;
			}

			this.attriLeftColor = leftColor;
			this.attriRightColor = rightColor;

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
		// TODO Auto-generated method stub

	}

}
