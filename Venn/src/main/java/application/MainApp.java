package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	
	private Stage primaryStage;
	private StackPane vennPane;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) throws IOException {
		
		this.primaryStage = primaryStage; //Setting the Primary Stage
		this.primaryStage.setTitle("Venn Diagram Maker App");
		
		//Loading the Rootlayout menubar from FXML code to Java Code **DOESNT WORK STILL FIXING**
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("rootLayout.fxml")); 
		
		this.rootLayout = (BorderPane) loader.load(); // Loads the MenuBar Scene into rootLayout
		Scene scene = new Scene(this.rootLayout);
		this.primaryStage.setScene(scene);
		this.primaryStage.show();
	
//		//now I want to load shapeScene fxml file
		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("shapeScene.fxml"));
		
		this.vennPane = (StackPane) loader.load();
	
		rootLayout.setCenter(this.vennPane); //make the center of the Menubar Scene to the rootLayout
	
		
	
	}
	
    
    public static void main(String[] args) {
		launch(args);
	}
    
   
}
