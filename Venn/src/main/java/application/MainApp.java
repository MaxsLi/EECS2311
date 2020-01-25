package application;

import java.io.IOException;

import controllers.MenuBarController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	
	public static Stage primaryStage;
	//private AnchorPane vennPane;
	private StackPane vennPane;
	private BorderPane rootLayout;
	private MenuBar menuBar;
	private FXMLLoader loader;

	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		
		loadRootLayout();
		loadMenubar();
		loadShapeScene();
		

		Scene scene = new Scene(this.rootLayout, 300, 300);


		this.primaryStage.setScene(scene);
		this.primaryStage.sizeToScene();
		
		this.primaryStage.setMinWidth(primaryStage.getWidth());
	    this.primaryStage.setMinHeight(primaryStage.getHeight());
		this.primaryStage.show();

	
	}
	
	private void loadRootLayout() throws IOException {
		this.loader = new FXMLLoader();
		this.loader.setLocation(getClass().getResource("rootLayout.fxml")); 		
		this.rootLayout = (BorderPane) loader.load();
	}
	
	private void loadMenubar() throws IOException  {
		this.loader = new FXMLLoader();
		this.loader.setLocation(getClass().getResource("menuBar.fxml"));
		this.menuBar = (MenuBar) loader.load();
		this.rootLayout.setTop(this.menuBar);
	}
	
	private void loadShapeScene() throws IOException {
		this.loader = new FXMLLoader();
		this.loader.setLocation(getClass().getResource("shapeScene.fxml"));
		this.vennPane = (StackPane) loader.load();
		this.rootLayout.setCenter(this.vennPane);
		//this.vennPane = (AnchorPane) loader.load();
	
		//rootLayout.setCenter(this.vennPane); //make the center of the Menubar Scene to the rootLayout
	
		
	
	}
	
    
    public static void main(String[] args) {
		launch(args);
	}
    
   
}
