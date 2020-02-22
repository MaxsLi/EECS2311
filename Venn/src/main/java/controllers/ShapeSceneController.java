package controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.*;
import views.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
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

		delete.setOnAction((event) -> {
			stackPane.getChildren().remove(textField);
		});

		edit.setOnAction((event) -> {
			textField.setEditable(true);
		});

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
	public void loadVenn() {
		try {
			FileReader fr = new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\resources\\save.csv");
			BufferedReader br = new BufferedReader(fr);
			String[] parts;
			String s;
			TextField tf;
			while ((s = br.readLine()) != null) {
				parts = s.split(", ");
				tf = new TextField();
				tf.setText(parts[0]); // parts[0] is the text column of the line
				tf.setEditable(false);
				tf.resizeRelocate(0, 0, 1, 1);
				tf.resize(50, 50);
				tf.setMaxWidth(tf.getText().length() * 12);

				int textFieldGetX = 1;
				int textFieldGetY = 2;

				tf.setTranslateX(Double.parseDouble(parts[textFieldGetX]));
				tf.setTranslateY(Double.parseDouble(parts[textFieldGetY]));
				stackPane.getChildren().add(tf);
				this.vennSet.add(tf);
				addDragEvent(tf);
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
		try {
			FileWriter fw = new FileWriter(System.getProperty("user.dir") + "\\src\\main\\java\\resources\\save.csv",
					false);

			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			for (TextField textField : write) {

				try { // If Nothing Was Added on GetExisting, the program crashes, this is so it
						// doesn't crash
					pw.write(textField.getText() + ", " + textField.getTranslateX() + ", " + textField.getTranslateY()
							+ ", " + this.vennSet.getLocation(textField) + "\n");
				} catch (Exception excep) {
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
