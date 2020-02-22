package tests;

import controllers.ShapeSceneController;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;

public class ShapeSceneContTest {
		
		ShapeSceneController cont;
		JFXPanel fxPanel;
		@Before
		public void setUp() {
		  cont=new ShapeSceneController();
		  fxPanel=new JFXPanel();
		}
		
	
		@Test
		public void testSave() throws Exception {
		
			String[] expected=new String[5];
			TextField tf;
			for (int i = 0; i < expected.length; i++) {
				tf=new TextField();
				tf.setText("I am test");
				tf.setTranslateX(i);
				tf.setTranslateY(i);
				cont.getTextFields().add(tf);
				expected[i]=tf.getText()+", "+tf.getTranslateX()+", "+tf.getTranslateY();
				
			}
			cont.saveVenn(cont.getTextFields());
			FileReader fr=new FileReader(System.getProperty("user.dir")+"\\src\\main\\java\\application\\save.csv");
			BufferedReader br=new BufferedReader(fr);
			String[] s=new String[5];
			for (int i = 0; i < s.length; i++) {
				s[i]=br.readLine();
				
			}

			assertArrayEquals(expected, s);
		}
	
		@Test
		public void testLoad() throws IOException {
			
		
			String[] expected=new String[5];
			TextField tf;
			cont=new ShapeSceneController();
			for (int i = 0; i < expected.length; i++) {
				tf=new TextField();
				tf.setText("I am test");
				tf.setTranslateX(i);
				tf.setTranslateY(i);
				cont.getTextFields().add(tf);
				expected[i]=tf.getText()+", "+tf.getTranslateX()+", "+tf.getTranslateY();
				
			}
			cont.saveVenn(cont.getTextFields());
			cont=new ShapeSceneController();
			cont.setStackPane(new StackPane());
			cont.loadVenn("save.csv");
			String[] s=new String[5];
			
			for (int i = 0; i < s.length; i++) {
				tf=cont.getTextFields().get(i);
				s[i]=tf.getText()+", "+tf.getTranslateX()+", "+tf.getTranslateY();
				
			}
			assertArrayEquals(expected, s);
		}
}