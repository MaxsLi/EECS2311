package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class ShapeSceneController implements Initializable {
	
	
	@FXML
	private AnchorPane mainScene;
	
	@FXML
	private StackPane stackPane;
	
	@FXML
	private Circle blueCircle;
	
	@FXML 
	private Circle redCircle;
	
	@FXML
	private Button addBttn;
	 
	@FXML
	private Button editBttn;
	
	@FXML 
	private Button removeButton;
	
	@FXML
	private TextField diagramText;
	
	
	
	public ShapeSceneController() {
		
	}
	/**
	 * On click, creates a textArea which can be dragged into Respective Circle
	 * @param event
	 */
	public void addTextToDiagram(ActionEvent event) {
		if(this.diagramText.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("Empty TextField");
			alert.setContentText("Please enter some Text to the TextField under the Venn Diagram");
			alert.showAndWait();
		}
		
		String newText = this.diagramText.getText();
		
		TextField newTextBox = new TextField(newText);
		newTextBox.setEditable(false);
		newTextBox.resizeRelocate(blueCircle.getCenterX(), blueCircle.getCenterY(), 1, 1);
		newTextBox.resize(50, 50);
		
		stackPane.getChildren().add(newTextBox);
		
		
		 
		 final Stage stageRef = (Stage) mainScene.getScene().getWindow();
		 
		 // When mouse button is pressed, save the initial position of textField
		 newTextBox.setOnMousePressed(new EventHandler<MouseEvent>() {
		 public void handle(MouseEvent me) {
		 double dragAnchorX = me.getX() - newTextBox.getLayoutX();
		 double dragAnchorY = me.getY() - newTextBox.getLayoutY();
		 }
		 }); 
		 
		 // When screen is dragged, translate it accordingly
		 newTextBox.setOnMouseDragged(new EventHandler<MouseEvent>() {
		 public void handle(MouseEvent me) {
			 newTextBox.relocate(me.getX(), me.getY());
		 }
		 }); 
		 
		 
		
	}

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
