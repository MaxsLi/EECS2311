package controllers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.MainApp;
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
	
	private MainApp mainApp;
	
	private double orgSceneX;
	private double orgSceneY;
	private double orgTranslateX;
	private double orgTranslateY;
	
	private ArrayList<TextField> current;
	
	public ShapeSceneController() {
		current=new ArrayList<>();
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
      
		} else {
			String newText = this.diagramText.getText();

			TextField newTextBox = new TextField(newText);
			newTextBox.setEditable(false);
			newTextBox.resizeRelocate(blueCircle.getCenterX(), blueCircle.getCenterY(), 1, 1);
			newTextBox.resize(50, 50);
			newTextBox.setMinWidth(50);
			newTextBox.setPrefWidth(50);
			newTextBox.setMaxWidth(400);


			stackPane.getChildren().add(newTextBox);
			current.add(newTextBox);
			newTextBox.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {

				orgSceneX = e.getSceneX();
				orgSceneY = e.getSceneY();
				orgTranslateX = newTextBox.getTranslateX();
				orgTranslateY = newTextBox.getTranslateY();

				newTextBox.toFront();
			});

			newTextBox.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {

				System.out.println("is dragged");

				double offsetX = e.getSceneX() - orgSceneX;
				double offsetY = e.getSceneY() - orgSceneY;
				double newTranslateX = orgTranslateX + offsetX;
				double newTranslateY = orgTranslateY + offsetY;

				newTextBox.setTranslateX(newTranslateX);
				newTextBox.setTranslateY(newTranslateY);
				
			});
		}
	

	    }
		 
		public void saveVenn() {
			try {
				String dir=System.getProperty("user.dir");
				
				FileWriter fw=new FileWriter(dir+"\\src\\main\\java\\application\\save.csv",false);
				BufferedWriter bw=new BufferedWriter(fw);
				PrintWriter pw=new PrintWriter(bw);
				for (TextField textField : current) {
					
					pw.write(textField.getText()+", "+textField.getTranslateX()+", "+textField.getTranslateY()+"\n");
					pw.flush();
				}
				pw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		 
	
		
	
		   /**
	     * Is called by the main application to give a reference back to itself.
	     * 
	     * @param mainApp
	     */
	    public void setMainApp(MainApp mainApp) {
	        this.mainApp = mainApp;

	   
	    }

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
