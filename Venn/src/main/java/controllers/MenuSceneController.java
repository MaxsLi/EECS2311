package controllers;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.MainApp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class MenuSceneController implements Initializable {
	
	@FXML
	private Button createNewBttn;
	
	@FXML
	private Button getExistingBttn;
	
	//Reference to the Main Application
	private MainApp mainApp;
	
	
	 /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
	
	public MenuSceneController() {
		//this.setMainApp(mainApp);
		
	}
	
	@FXML
	private void createNew() throws IOException {
	
		mainApp.switcher("shapeScene");
	}
	public void switcher(String scene) {
		
	}
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded. 
     */
	@FXML
	private void initalize() {
    	
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
