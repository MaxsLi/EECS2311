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
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import views.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import models.Location;
import models.VennSet;
import models.VennShape;
import utilities.TextUtils;

public class ShapeSceneController implements Initializable {

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

	@FXML
	private TextField leftTitle;

	@FXML
	private TextField rightTitle;

	/**
	 * An Set of `TextLabel`s
	 */
	private VennSet vennSet;

	/**
	 * An Set of `Shape`s
	 */
	private VennShape vennShape;

	private double orgSceneX;
	private double orgSceneY;
	private double orgTranslateX;
	private double orgTranslateY;

	public ShapeSceneController() {
		// Note that function `initialize` will do the init
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
			TextField newTextField = new TextField();

			newTextField.setEditable(false);
			newTextField.resizeRelocate(leftCircle.getCenterX(), leftCircle.getCenterY(), 1, 1);

			this.addAutoResize(newTextField);
			this.addDragEvent(newTextField);
			this.addContext(newTextField);

			newTextField.setText(newText);
			this.stackPane.getChildren().add(newTextField);
			this.vennSet.add(newTextField);
			this.sideAdded.clear();
		}
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
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning Dialog");
				alert.setHeaderText("TextField Out of Bounds");
				alert.setContentText(
						"Please place the label inside the one of the shape.");
				alert.showAndWait();
				return;
			}

			/*
			 * If TextField location is within radial distance of the left and right circle,
			 * it must be somewhere in the intersection of the two circles
			 */
			if (textBoxLocation == Location.MIDDLE) {
				sideAdded.setText("Intersection!");
				sideAdded.setEditable(false);
				sideAdded.setStyle("-fx-text-fill: purple; -fx-font-size: 25px;");
			}
			/*
			 * Else if, if its within radial distance of the left Circle, it must be in the
			 * left circle
			 */
			else if (textBoxLocation == Location.LEFT) {
				sideAdded.setText("Left!");
				sideAdded.setEditable(false);
				sideAdded.setStyle("-fx-text-fill: blue; -fx-font-size: 25px;");
			}
			/*
			 * Else if, if its within radial distance of the left Circle, it must be in the
			 * right circle
			 */
			else if (textBoxLocation == Location.RIGHT) {
				sideAdded.setText("Right!");
				sideAdded.setEditable(false);
				sideAdded.setStyle("-fx-text-fill: red; -fx-font-size: 25px;");
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
			textField.setMaxWidth(TextUtils.computeTextWidth(textField.getFont(),
					textField.getText(), 0.0D) + 20);
		});
	}

	/**
	 * A Method that loads all comma delimited rows from save.csv and puts them on
	 * the screen
	 */
	public void loadVenn(String fileName) {

		try {
			FileReader fr = new FileReader(System.getProperty("user.dir") + File.separator + "src" + File.separator
					+ "main" + File.separator + "java" + File.separator + "resources" + File.separator + fileName);
			BufferedReader br = new BufferedReader(fr);

			this.currentFileName = fileName.substring(0, fileName.length() - 4); // Cuts off the ".csv" extension
			
			//System.out.println(this.currentFileName);

			String[] parts;
			String s;
			TextField tf;
			int lineCounter = 1;

			try {
				String[] firstLineInfo = br.readLine().split(COMMA);

				//System.out.println(Arrays.toString(firstLineInfo));

				this.appTitle.setText(firstLineInfo[0]);
				this.leftTitle.setText(firstLineInfo[1]);
				this.rightTitle.setText(firstLineInfo[2]);
				this.leftCircle.setFill(Paint.valueOf(firstLineInfo[3]));
				this.rightCircle.setFill(Paint.valueOf(firstLineInfo[4]));
				
				//System.out.println(lineCounter);
				
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

						//System.out.println(Arrays.toString(firstLineInfo));

						this.appTitle.setText(firstLineInfo[0]);
						this.leftTitle.setText(firstLineInfo[1]);
						this.rightTitle.setText(firstLineInfo[2]);
						this.leftCircle.setFill(Paint.valueOf(firstLineInfo[3]));
						this.rightCircle.setFill(Paint.valueOf(firstLineInfo[4]));
						
						
						//System.out.println("The line number is: " + lineCounter);
						
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

				//System.out.println("The line number is: " + lineCounter);
				
				
				lineCounter++;
				tf = new TextField();
				this.addAutoResize(tf);
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
					
					//System.out.println(parts[3]);


					addDragEvent(tf);

					//System.out.println(lineCounter);

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
				this.rightTitle.getText(), this.leftCircle.getFill(), this.rightCircle.getFill());

		String dummyLine = "TEXT COLUMN" + COMMA + "TextField X Coor" + COMMA + "TextField Y Coor" + COMMA + "Location of TextField" + COMMA + "<--DO NOT MODIFY THIS LINE"; // Program not reading Line two,
																					// adding dummyLine

		/*
		 * Set the First Line of the CSV File Accordingly
		 */
		String firstLine = appSaver.attrAppTitle + "," + appSaver.attrLeftTitle + "," + appSaver.attrRightTitle + ","
				+ appSaver.attrLeftColor.toString() + "," + appSaver.attrRightColor.toString() + ","
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
			} catch (FileNotFoundException ex) {
				Alert alertWarn = new Alert(AlertType.WARNING);
				alertWarn.setTitle("WARNING");
				alertWarn.setHeaderText("Dangerous Action");
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
					//System.out.println(this.vennSet.getLocation(textField)));
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

		public AppAttributes(String attributeAppTitle, String leftTitle, String rightTitle, Paint leftColor,
							 Paint rightColor) {
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
		this.vennShape = new VennShape(this.leftCircle, this.rightCircle);
		this.vennSet = new VennSet(this.vennShape);
	}

}
