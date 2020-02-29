package tests;

import javafx.scene.shape.Circle;
import models.VennShape;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VennShapeTest {

	private VennShape vennShape;
	private Circle leftCircle;
	private Circle rightCircle;

	@Before
	public void setUp() {
		this.leftCircle = new Circle();
		this.rightCircle = new Circle();
		this.vennShape = new VennShape(this.leftCircle, this.rightCircle);
	}

	@Test
	public void testLeftCircle() {
		assertEquals(this.vennShape.getLeftShape(), this.leftCircle);
	}

	@Test
	public void testRightCircle() {
		assertEquals(this.vennShape.getRightShape(), this.rightCircle);
	}

}
