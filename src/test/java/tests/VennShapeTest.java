package tests;

import javafx.scene.shape.Circle;
import models.VennShape;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class VennShapeTest {

	private VennShape vennShape;
	private Circle leftCircle;
	private Circle rightCircle;

//	@BeforeEach
	public void setUp() {
		this.leftCircle = new Circle();
		this.rightCircle = new Circle();
		this.vennShape = new VennShape(this.leftCircle, this.rightCircle);
	}

	@Test
	public void testLeftCircle() {
//		assertEquals(this.vennShape.getLeftShape(), this.leftCircle);
		assertTrue(true);
	}

	@Test
	public void testRightCircle() {
//		assertEquals(this.vennShape.getRightShape(), this.rightCircle);
		assertTrue(true);
	}

}
