package tests;

import javafx.scene.shape.Circle;
import models.VennSet;
import models.VennShape;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class VennSetTest {

	private VennSet vennSet;
	private VennShape vennShape;
	private Circle leftCircle;
	private Circle rightCircle;

	@BeforeEach
	public void setUp() {
		this.leftCircle = new Circle();
		this.leftCircle.setCenterX(0);
		this.leftCircle.setCenterY(0);
		this.rightCircle = new Circle();
		this.rightCircle.setCenterX(50);
		this.rightCircle.setCenterY(50);
		this.vennShape = new VennShape(this.leftCircle, this.rightCircle);
		this.vennSet = new VennSet(this.vennShape);
	}

	@Test
	public void test() {
		assertTrue(true);
	}
}
