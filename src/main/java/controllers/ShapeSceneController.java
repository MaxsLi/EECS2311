package controllers;

import java.awt.Point;
import java.awt.geom.Point2D;
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
import java.util.Stack;

import com.sun.scenario.effect.impl.prism.ps.PPSBlend_ADDPeer;

import javafx.scene.control.*;
import javafx.scene.paint.Color;
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
import javafx.stage.FileChooser;
import javafx.stage.Window;
import jdk.nashorn.internal.ir.RuntimeNode.Request;
import models.AddCommand;
import models.Command;
import models.DragCommand;
import models.EditColorCommand;
import models.EditTextCommand;
import models.Location;
import models.RemoveCommand;
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
	
	private File currentFile;

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

	private Stack<Command> undoStack;
	private Stack<Command> redoStack;
	
	public ShapeSceneController() {
		// Note that function `initialize` will do the init
	}

	public static final String COMMA = ",";

	public void addText(TextField newTextField) {
		
		Point2D.Double position=new Point2D.Double(leftCircle.getCenterX(), leftCircle.getCenterY());
		position=getFreePos(position);
		
		newTextField.setEditable(false);
		newTextField.resizeRelocate(leftCircle.getCenterX(), leftCircle.getCenterY(), 1, 1);
		
		this.addDragEvent(newTextField);
		this.addContext(newTextField);

		newTextField.setTranslateX(position.getX());
		newTextField.setTranslateY(position.getY());
		this.stackPane.getChildren().add(newTextField);
		this.vennSet.add(newTextField);
		this.sideAdded.clear();
	}
	/*
	Gets the first possible free positon for the Textbox
	@param p the  center of circles */
	private Point2D.Double getFreePos(Point2D.Double p) {
	//keep track of even odd so every other textbox goes above
		int j=0;
		for (int i = 0; i < vennSet.size(); i++) {
			double x=vennSet.get(i).getTranslateX();
			double y=vennSet.get(i).getTranslateY();
			if (x==p.getX()&&y==p.getY()&&(j%2)==0) {
				p.y+=(j+1)*vennSet.get(i).getHeight();
				j++;
			}
			else if (x==p.getX()&&y==p.getY()) {
				p.y-=(j+1)*vennSet.get(i).getHeight();
				j++;
			}
		}
		
		return p;
	}
	public void removeTextField(TextField textField) {
		stackPane.getChildren().remove(textField);
	}
	public void setText(TextField textField, String text) {
		textField.setText(text);
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
			TextField newTextField=new TextField();
			this.addAutoResize(newTextField);
			newTextField.setText(this.diagramText.getText());
			Command a=new AddCommand(this, newTextField);
			redoStack.clear();
			a.execute();
			undoStack.push(a);
		}
	}
	
	public void undo() {
		if (!(undoStack.empty())) {
			Command a=undoStack.pop();
			a.undo();
			redoStack.push(a);
		}
	}

	public void redo() {
		if (!(redoStack.empty())) {
			Command a=redoStack.pop();
			a.redo();
			undoStack.push(a);	
		}
	}

	public void moveTextField(TextField textField, double offsetX, double offsetY) {
		double newTranslateX = orgTranslateX + offsetX;
		double newTranslateY = orgTranslateY + offsetY;

		textField.setTranslateX(newTranslateX);
		textField.setTranslateY(newTranslateY);
	}
	/**
	 * Adds Drag Events to created TextFields
	 * 
	 * @param textField the TextField to be added
	 */
	private DragCommand drag;
	private void addDragEvent(TextField textField) {
		
		textField.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {

			this.diagramText.clear();
			orgSceneX = e.getSceneX();
			orgSceneY = e.getSceneY();
			orgTranslateX = textField.getTranslateX();
			orgTranslateY = textField.getTranslateY();

			textField.toFront();
			drag=new DragCommand(this, textField, orgTranslateX, orgTranslateY);
			
		});

		 /*
		 * On Mouse Drag Moves the TextField Around the Screen
		 */
		textField.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {

			double offsetX = e.getSceneX() - orgSceneX;
			double offsetY = e.getSceneY() - orgSceneY;
			drag.setOffsetX(offsetX);
			drag.setOffsetY(offsetY);
			drag.execute();
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
			drag.setOffsetX(textField.getTranslateX());
			drag.setOffsetY(textField.getTranslateY());
			redoStack.clear();
			undoStack.push(drag);
			getLocation(textField);
		});

	}

	public void getLocation(TextField textField) {
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

		delete.setOnAction((event) ->{
			RemoveCommand remove=new RemoveCommand(this, textField);
			redoStack.clear();
			undoStack.push(remove);
			remove.execute();
			
		});

		edit.setOnAction((event) -> {
			EditTextCommand e=new EditTextCommand(this, textField, textField.getText());
			redoStack.clear();
			undoStack.push(e);
			textField.setEditable(true);
			
		});

	}

	private void setTitleBoxes() {
		
		appTitle.addEventHandler(MouseEvent.MOUSE_PRESSED, e  -> {
			EditTextCommand edit=new EditTextCommand(this, appTitle, appTitle.getText());
			redoStack.clear();
			undoStack.push(edit);
		});
		
		leftTitle.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			EditTextCommand edit=new EditTextCommand(this, leftTitle, leftTitle.getText());
			redoStack.clear();
			undoStack.push(edit);
			
		});
		
		rightTitle.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			EditTextCommand edit=new EditTextCommand(this, rightTitle, rightTitle.getText());
			redoStack.clear();
			undoStack.push(edit);
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
	public void loadVenn(File fileToLoad) {

		try {
			
			FileReader fr = new FileReader(fileToLoad);
			BufferedReader br = new BufferedReader(fr);

			this.currentFileName = fileToLoad.getName().substring(0, fileToLoad.getName().length() - 4); // Cuts off the ".csv" extension
			
			this.currentFile = fileToLoad;
			
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
			if(this.currentFile == null) {
				Window stage = this.stackPane.getScene().getWindow();
				fileChooser = new FileChooser();
				
				File recordsDir = new File(System.getProperty("user.home"), "VennCreateFiles" + File.separator);
				
				if(! recordsDir.exists()) {
					recordsDir.mkdirs();
				}
				
				fileChooser.setInitialDirectory(recordsDir);
				fileChooser.setTitle("Save File");
				fileChooser.setInitialFileName(titleOfApp);
				fileChooser.getExtensionFilters().addAll(
						new FileChooser.ExtensionFilter("CSV files", "*.csv")
			);
			
			this.currentFile = fileChooser.showSaveDialog(stage);
			}	
				
				
			if(this.currentFile != null) { //Throw Alert if Somehow the currentWorking File is Still null, if not null, write to it
				fw = new FileWriter(this.currentFile,false);
				
				//fileChooser.setInitialDirectory(this.currentFile);
			}
			else {
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

	public void setCircleColor(Circle circle, Paint color) {
		if (leftCircle.equals(circle)) {
			leftCircle.setFill(color);
			leftColorPicker.setValue((Color) color);
		}
		else if (rightCircle.equals(circle)) {
			rightCircle.setFill(color);
			rightColorPicker.setValue((Color) color); 
		}
	}
	/**
	 * A method that changes the color of the leftCircle
	 */
	public void changeLeftColor() {
		EditColorCommand edit=new EditColorCommand(this, leftCircle, leftCircle.getFill(), leftColorPicker.getValue());
		undoStack.push(edit);
		edit.execute();
	}

	/**
	 * A method that changes the color of the rightCircle
	 */
	public void changeRightColor() {
		EditColorCommand edit=new EditColorCommand(this, rightCircle, rightCircle.getFill(), rightColorPicker.getValue());
		undoStack.push(edit);
		edit.execute();
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
		this.undoStack=new Stack<>();
		this.redoStack=new Stack<>();
		this.setTitleBoxes();
	}
}
