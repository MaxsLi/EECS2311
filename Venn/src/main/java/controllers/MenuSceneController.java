package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import views.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class MenuSceneController implements Initializable {

	@FXML
	private Button createNewBttn;

	@FXML
	private Button getExistingBttn;

	// Reference to the Main Application
	private MainApp mainApp;

	/**
	 * The constructor. The constructor is called before the initialize() method.
	 */

	public MenuSceneController() {
		// this.setMainApp(mainApp);

	}

	@FXML
	private void createNew() throws IOException {

		mainApp.switchScene("shapeScene");
	}

	@FXML
	private void loadLast() throws IOException {

		mainApp.switchScene("load");

	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
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
