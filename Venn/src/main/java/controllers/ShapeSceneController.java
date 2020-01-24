package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class ShapeSceneController implements Initializable {
	
	@FXML
	private AnchorPane mainScene;
	
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
		
				
		
		
	}

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
