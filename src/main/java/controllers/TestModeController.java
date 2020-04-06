package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import models.Location;
import models.VennSet;
import models.VennShape;
import views.MainApp;

public class TestModeController extends ShapeSceneController implements Initializable {

	@FXML
	private Button importTxtBttn;

	Map<String, Location> correctLocation = new HashMap<>();
	ArrayList<String> elements = new ArrayList<>();
	
	@FXML
	Text text;
	
	@FXML
	private ToggleButton toggleTimeBttn;
	
	Timeline timeline;
	
	int mins = 0, secs = 0, millis = 0;
	
	int elementsImported;
	
	public static boolean TEST_HAS_STARTED = false;

	public static final String LEFT = "left";
	public static final String RIGHT = "right";
	public static final String BOTTOM = "bottom";
	public static final String INTERSECTING_3 = "left+right+bottom";
	public static final String INTERSECTING_LEFT_RIGHT = "left+right";
	public static final String INTERSECTING_LEFT_BOTTOM = "left+bottom";
	public static final String INTERSECTING_RIGHT_BOTTOM = "right+bottom";

	public static final int NO_ELEMENTS_IMPORTED = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ShapeSceneController.APPLICATION_IS_SAVED = true; //So it wont ask us to save testModeController if the user closes the window
		vennShape = new VennShape(this.leftCircle, this.rightCircle);
		vennSet = new VennSet(this.vennShape);
	       
	  
	    FadeTransition ft = new FadeTransition(Duration.millis(1000), this.importTxtBttn);  
	  	ft.setFromValue(0);
		ft.setToValue(1.0);
		ft.setCycleCount(Timeline.INDEFINITE);
		ft.setAutoReverse(true);
		ft.play();
		
		text.setVisible(false);
		TEST_HAS_STARTED = false;
		toggle.setDisable(true);

	}

//	private void goShapeScene() throws IOException {
//
//		FXMLLoader loader = new FXMLLoader();
//		loader.setLocation(getClass().getResource("/fxml/shapeScene.fxml"));
//
//		MainApp.primaryStage.setScene(new Scene(loader.load()));
//	}

	@FXML
	private void importTxt() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
				new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

		File selectedFile = fileChooser.showOpenDialog(MainApp.primaryStage);
		boolean successfullyImported = false;
		

		if (selectedFile == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("Error Importing File");
			alert.setContentText("Please Import a .txt or .csv file and try again");

			alert.showAndWait();
		} else {
			successfullyImported = true;
			fileChooser.setInitialDirectory(selectedFile.getParentFile());
			readAndParseTXT(selectedFile);
			startTestMode();
		}

		if (successfullyImported) {

			super.stackPane.getChildren().remove(importTxtBttn);

		}

	}

	/**
	 * A method that reads are parses the txt file for test mode
	 * 
	 * @param file - .txt file to be read
	 * @return - The number of elements text elements sucessfully read (excluding
	 *         the first line of the txt file which holds the # of circles the user
	 *         wants) return 0 for no elements sucessfuly read (error) and a number
	 *         greater than 0 otherwise
	 * 
	 */
	public int readAndParseTXT(File file) {
		elementsImported = 0;
		String st;
		int lineCount = 0;
		int numOfCircles = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			try {
				while ((st = br.readLine()) != null) {
					if (lineCount == 0) {
						numOfCircles = getCircleNumber(st);
						if (numOfCircles == 0) { // Number of circles could not be read
							String message = "The Number of Cirlces on the first line of the TXT file was wrongly specified."
									+ "\n" + "It should be either 2, or 3.";
							throwImproperFormatting(message);
							return TestModeController.NO_ELEMENTS_IMPORTED;
						}
						else if(numOfCircles == 2) {
							//Do nothing
						}
						else if(numOfCircles == 3) {
							addCircle();
						}

					}
					else if(lineCount == 1) {
						appTitle.setText(st);
					}
					else if(lineCount == 2) {
						leftTitle.setText(st);
					}
					else if(lineCount == 3) {
						rightTitle.setText(st);
					}
					else if(lineCount == 4 && numOfCircles == 3) {
						extraTitle.setText(st);
					}
					else {	// lineCount > 4
						String[] parts = st.split(",");
						if (checkLine(parts)) {
							if (assignLocation(parts[0], parts[1]) == true) {
								elementsImported++;
							} else {
								throwImproperFormatting("An error occurend in reading line: " + lineCount
										+ "of your .txt file,"
										+ " plese reffer to the VennCreate user manual to see how to address this issue.");
								return TestModeController.NO_ELEMENTS_IMPORTED;
							}
						} else {
							throwImproperFormatting("An error occurend in reading line: " + lineCount
									+ "of your .txt file,"
									+ " plese occur to the VennCreate user manual to see how to address this issue.");
							return TestModeController.NO_ELEMENTS_IMPORTED;
						}
						
					}
					lineCount++;
				}

			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return elementsImported;

	}

	public boolean checkLine(String[] parts) {
		if (parts.length == 2) { // line was sucessfully read
			return true;
		} else {
			return false;
		}
	}

	/**
	 * A Method process a single line of .txt file and put it in the correctLocation
	 * hashmap
	 * 
	 * @param text     the Key portion of the correctLocation hashmap
	 * @param location the value portion of the hashmap
	 * @return true if hashmap successfully assigned an element, false otherwise
	 */
	public boolean assignLocation(String text, String location) {
		if (location.trim().equals(LEFT)) {
			correctLocation.put(text.trim(), Location.LEFT);
			elements.add(text.trim());
			return true;
		} else if (location.trim().equals(RIGHT)) {
			correctLocation.put(text.trim(), Location.RIGHT);
			elements.add(text.trim());
			return true;
		} else if (location.trim().equals(BOTTOM)) {
			correctLocation.put(text.trim(), Location.BOTTOM);
			elements.add(text.trim());
			return true;
		} else if (location.trim().equals(INTERSECTING_3)) {
			correctLocation.put(text.trim(), Location.INTERSECTING_ALL);
			elements.add(text.trim());
			return true;
		} else if (location.trim().equals(INTERSECTING_LEFT_RIGHT)) {
			correctLocation.put(text.trim(), Location.INTERSECTING_LEFT_RIGHT);
			elements.add(text.trim());
			return true;
		} else if (location.trim().equals(INTERSECTING_LEFT_BOTTOM)) {
			correctLocation.put(text.trim(), Location.INTERSECTING_BOTTOM_LEFT);
			elements.add(text.trim());
			return true;
		} else if (location.trim().equals(INTERSECTING_RIGHT_BOTTOM)) {
			correctLocation.put(text.trim(), Location.INTERSECTING_BOTTOM_RIGHT);
			elements.add(text.trim());
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Parses a string that represents how many circles the user wants in the test
	 * mode
	 * 
	 * @param s the string representation of the int
	 * @return an int representation of the string, returns 0 if there was an error
	 *         parsing the string
	 */
	public int getCircleNumber(String s) {
		int result = 0;
		try {
			result = Integer.parseInt(s.trim());
			if(result == 2 || result == 3) {
			return result;
			}
			else {
				return 0;
			}
		} catch (NumberFormatException nfe) {
			return result;
		}
	}

	public void throwImproperFormatting(String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Improper Formatting");
		alert.setHeaderText("Error");
		alert.setContentText(message);

		alert.showAndWait();
	}
	
	public void startTestMode() {
//		if(NAV_IS_SHOWING == false) {
//			super.toggleDrawer();
//		}
		for(String s: this.elements) {
			itemList.getItems().add(s);
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText("Test Mode Entered");
		alert.setContentText("Welcome to VennCreate's test mode. In this mode you are able to input a .txt file"
				+ " with the format specified in VennCreate's user manual, and practice placing the text in the correct location."
				+ "The timer will constantly be running, so go as fast as possible!");;

		alert.showAndWait();
		
		

		timeline = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
            	change(text);
			}
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(false);
		TEST_HAS_STARTED = true;
		toggle.setDisable(false);
		timeline.play();
		
	
	
	}
	
	void change(Text text) {
		if(millis == 1000) {
			secs++;
			millis = 0;
		}
		if(secs == 60) {
			mins++;
			secs = 0;
		}
		text.setText((((mins/10) == 0) ? "0" : "") + mins + ":"
		 + (((secs/10) == 0) ? "0" : "") + secs + ":" 
			+ (((millis/10) == 0) ? "00" : (((millis/100) == 0) ? "0" : "")) + millis++);
    }
	
	@FXML
	void submitResults(){
		if(!TEST_HAS_STARTED) { //If test hasn't started return
			return;
		}
		timeline.pause();
		if(this.vennSet.size() != this.elementsImported) { // the user hasnt dragged all the elements, there is still stuff to do
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText(null);
			alert.setContentText("You havent added all the text you imported to the Venn Diagrams. Any Un-imported text will automatically be marked incorrect" 
					+ "\n" + "Are you ok with this?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
			    int finalScore = calculateScore();
			    try {
					displayFinalResults(finalScore);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
			    return;
			}
		}
		else {
			  int finalScore = calculateScore();
			    try {
					displayFinalResults(finalScore);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	public int calculateScore() {
		int score = 0;
		for(int i = 0; i < this.vennSet.size(); i++) {
			if(this.correctLocation.get(this.vennSet.get(i).getText()) == super.tfLocations.get(this.vennSet.get(i))) { //if this textfield is in same location as it was in the .txt file score++
				score++;
			}
		}
		
		return score;
	}
	
	public void displayFinalResults(int finalScore) throws IOException {
		double percent = calculatePercent(finalScore, elementsImported);
		String title = null;
		if(percent < 50) {
			title = "You need some work :(";
		}
		else if(percent > 80) {
			title = "Great Work!";
		}
		else {
			title = "You did ok.";
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("VennCreate Test Completed");
		alert.setHeaderText(title);
		alert.setContentText("Test Completed." + "\n" + 
		"Your Score:" + finalScore + "/" + elementsImported + "\n" +
				"Time Taken: " + text.getText() + " (mins, seconds, miliseconds)" + "\n" 
		+ "Would you like to play again?");

		ButtonType yes = new ButtonType("Yes");
		ButtonType noExitTestMode = new ButtonType("No, Exit Test Mode");
		ButtonType noExitApplication = new ButtonType("No, Exit Application");
		
		alert.getButtonTypes().setAll(yes, noExitTestMode, noExitApplication);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == yes){
			mainApp.switchScene("testMode", null); //null should be a file
		} else if (result.get() == noExitTestMode) {
			createNew();
		} else if (result.get() == noExitApplication) {
		    MainApp.primaryStage.close();
		} 
	}
	
	public double calculatePercent(int finalScore, int elementsImported) {
		try {
			double result = (finalScore / elementsImported) * 100;
			return result;
		}
		catch(Exception e) {
			double result = 0;
			return result;
		}
	}
	
	@Override
	protected void createItem() {
		if(itemList.getSelectionModel().isEmpty()) return;
		if(itemList.getItems().isEmpty()) return;
		String newItem = itemList.getSelectionModel().getSelectedItem();
		addTextToDiagram(newItem);
		if(itemList.getItems().isEmpty()) createItemBttn.setDisable(true);
	}
	
	
	public void addTextToDiagram(String text) {

		if ((text.isEmpty() || text.trim().equals(""))) {

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
			String newText = text;
			if (newText.charAt(newText.length() - 1) == ',') {
				newText = newText.substring(0, newText.length() - 1);
			}

			TextField newTextField = new TextField();

			newTextField.setEditable(false);
			newTextField.setTranslateX(textFieldPointLocations[textFieldPointLocationsIndex].getX());
			newTextField.setTranslateY(textFieldPointLocations[textFieldPointLocationsIndex].getY());

			adjustNewTextLocation();

			newTextField.setStyle("-fx-background-color:transparent; -fx-font-size:18px; ");

			newTextField.setMinWidth(Control.USE_PREF_SIZE);
			newTextField.setPrefWidth(Control.USE_COMPUTED_SIZE);
			newTextField.setMaxWidth(Control.USE_PREF_SIZE);

			this.addDragEvent(newTextField);

			if (!this.itemList.getItems().contains(newText)) {
				this.itemList.getItems().add(newText);
			}

			newTextField.setText(newText);
			this.stackPane.getChildren().add(newTextField);
			this.vennSet.add(newTextField);
			this.sideAdded.clear();
			this.itemList.getItems().remove(newTextField.getText());
		}
		
		
		
		
	}
	
	@Override
	protected void addDragEvent(TextField textField) {
		textField.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {

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

			resetTextFieldPointLocationsIndex();


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

		});
		
		textField.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {

			textField.setEditable(false);

		});
		
	
	}
	
	@Override
	public void saveVenn(ArrayList<TextField> write) {
		//Test Mode Shouldnt be able to be saved so do nothing
	}
	
	@Override
	public void saveVennBttn() {
		saveVenn(null); //Test Mode has no saving feature
	}
	
	@Override
	protected void createNew() throws IOException {
			mainApp.switchScene("shapeScene", null);
	}
	
	@FXML
	private void exitTest() throws IOException {
		if(TEST_HAS_STARTED) {
			timeline.pause();
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText("Test In Progress");
			alert.setContentText("Your test is current in progress, are you sure you would like to quit?");

			ButtonType yes = new ButtonType("Yes");
			ButtonType cancel = new ButtonType("Cancel");

			alert.getButtonTypes().setAll(yes, cancel);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == yes) {
				mainApp.switchScene("shapeScene", null);
			} else {
				timeline.play();
				return;
			}
		}
		else
		mainApp.switchScene("shapeScene", null);
	}
	
	@FXML
	private void toggleTimer() {
		
		if(this.text.isVisible()) {
			text.setVisible(false);
		}
		else {
			text.setVisible(true);
		}
	}
	
	@Override
	public void addCircle() {
		if (!EXTRA_CIRCLE_ADDED) {
			ShapeSceneController.EXTRA_CIRCLE_ADDED = false;

			//--------------------------Circle Starting to be added
			Circle extraCircle = new Circle(225);

			this.extraCircle = extraCircle;

			this.vennShape.add(extraCircle);
			extraCircle.setOpacity(0.6);

			extraCircle.setBlendMode(BlendMode.MULTIPLY);
			extraCircle.setFill(Color.valueOf("#9ACD32"));

			this.stackPane.getChildren().add(extraCircle);
			

			StackPane.setMargin(extraCircle, new Insets(250, 0, 0, 0));
			//-------Circle Done Added
			
			//----TextField extraTitle starting to be added
			extraTitle = new TextField();
			extraTitle.setLayoutX(1000);
			extraTitle.setLayoutY(751);
			extraTitle.setStyle("-fx-font-size:20px;-fx-background-color: transparent;");
			mainScene.getChildren().add(extraTitle);
			//------

			//---Label Starting to be Added
			this.extra1Label.setStyle("-fx-font-size:15px;");
			this.scrollBox.getChildren().add(this.extra1Label);

			VBox.setMargin(this.extra1Label, new Insets(10, 0, 0, 30));

			this.extra1LabelColor.setStyle("-fx-font-size:12px;");
			this.scrollBox.getChildren().add(this.extra1LabelColor);
			VBox.setMargin(this.extra1LabelColor, new Insets(10, 0, 0, 50));
			//----Label Finished Adding

			
			//---Color Picker starting to be added
			this.extra1Color = new ColorPicker();
			this.extra1Color.setValue(Color.valueOf(ShapeSceneController.DEFAULT_EXTRACIRCLE_COLOR));
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
			//------Color picker done being added
			
			
			//---Label Starting to be added
			this.scrollBox.getChildren().add(this.extra1LabelSize);
			this.extra1LabelSize.setStyle("-fx-font-size:12px;");

			VBox.setMargin(this.extra1LabelSize, new Insets(10, 0, 0, 50));
			//---Label Done being added
			
			
			
			//----Slider Starting to be added
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
			//-----Slider done being added
			
			
			//-------Label Starting to be added
			this.extra1HoverLabel.setStyle("-fx-font-size:12px;");
			this.scrollBox.getChildren().add(this.extra1HoverLabel);
			VBox.setMargin(this.extra1HoverLabel, new Insets(10, 0, 0, 50));
			//------Label done being added

			//-----Color picker starting to be added
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
					if (ShapeSceneController.EXTRA_CIRCLE_HOVER) {
						extraCircle.setStyle("-fx-stroke:#"
								+ exrtra1ColorHover.getValue().toString().substring(2,
										exrtra1ColorHover.getValue().toString().length() - 2)
								+ ";" + " -fx-stroke-width: 5;");

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

			extra1Slider.valueProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable, //
						Number oldValue, Number newValue) {

					extraCircle.setRadius((double) newValue);
				}
			});

			this.scrollBox.getChildren().add(this.exrtra1ColorHover);
			VBox.setMargin(this.exrtra1ColorHover, new Insets(10, 0, 0, 50));
			//-------------------Color picker done being added
			
			

			for (TextField t : this.vennSet) {
				t.toFront();
			}

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Circle 3 Has Been Addded!");
			alert.setHeaderText("Success!");
			alert.setContentText("Support for a third circle has been added in the Apperance pane.");
			alert.showAndWait();

		}
		
		//---Label starting to be added
		this.textProperties.setStyle("-fx-font-size:15px;");
		this.scrollBox.getChildren().add(this.textProperties);

		VBox.setMargin(this.textProperties, new Insets(10, 0, 0, 30));
		//---Label done being added
		
		
		//-----Label starting to be added
		this.extraFontSize.setStyle("-fx-font-size:12px;");
		this.scrollBox.getChildren().add(this.extraFontSize);
		VBox.setMargin(this.extraFontSize, new Insets(10, 0, 0, 50));
		//----Label done being added
		
		
		//----HBox starting to be added
		sliderBox = new HBox();

		this.scrollBox.getChildren().add(this.sliderBox);
		//---HBox done being added
		
		
		//-------Slider starting to be added
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

		extraFontSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, //
					Number oldValue, Number newValue) {

				// rightFontTextField.setText(((double) newValue) + "");
				for (TextField tf : tfLocations.keySet()) {
					if (tfLocations.get(tf).equals(Location.BOTTOM)) {
						int newFont = ((int) Math.round((double) newValue));
						String font = "" + newFont;
						tf.setStyle("-fx-font-size:" + font + "px;-fx-background-color:transparent;");
						tf.setMinWidth(Control.USE_PREF_SIZE);
						tf.setPrefWidth(Control.USE_COMPUTED_SIZE);
						tf.setMaxWidth(Control.USE_PREF_SIZE);
					}
				}
			}
		});

		this.sliderBox.getChildren().add(this.extraFontSlider);
		HBox.setMargin(this.extraFontSlider, new Insets(5, 0, 0, 50));
		//----Slider done being added
		
		
		//---Label starting to be added
		this.extraTextColor.setStyle("-fx-font-size:12px;");
		this.scrollBox.getChildren().add(this.extraTextColor);
		VBox.setMargin(this.extraTextColor, new Insets(10, 0, 0, 50));
		//---Label done being added

		
		//----Color picker starting to be added
		this.extraTextColorPicker = new ColorPicker();
		this.extraTextColorPicker.setMinHeight(28);
		this.extraTextColorPicker.setMinWidth(137);
		this.extraTextColorPicker.setMaxHeight(28);
		this.extraTextColorPicker.setMaxWidth(137);

		extraTextColorPicker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				for (TextField tf : tfLocations.keySet()) {
					if (tfLocations.get(tf).equals(Location.BOTTOM)) {
						int newFont = ((int) Math.round((double) extraFontSlider.getValue()));
						String newColor = extraTextColorPicker.getValue().toString().substring(2,
								leftTextColor.getValue().toString().length() - 2);
						tf.setStyle("-fx-font-size:" + newFont + "px;-fx-background-color:transparent;-fx-text-fill: #"
								+ newColor + ";");
						tf.setMinWidth(Control.USE_PREF_SIZE);
						tf.setPrefWidth(Control.USE_COMPUTED_SIZE);
						tf.setMaxWidth(Control.USE_PREF_SIZE);
					}
				}
			}
		});

		this.scrollBox.getChildren().add(this.extraTextColorPicker);
		VBox.setMargin(this.extraTextColorPicker, new Insets(10, 0, 0, 50));
		//----Color picker done being added

		initCircleContext();
	}

}
