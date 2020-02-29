//package models;
//
//import com.sun.javafx.geom.BaseBounds;
//import com.sun.javafx.geom.transform.BaseTransform;
//import com.sun.javafx.jmx.MXNodeAlgorithm;
//import com.sun.javafx.jmx.MXNodeAlgorithmContext;
//import com.sun.javafx.sg.prism.NGNode;
//
//import javafx.scene.Group;
//import javafx.scene.Node;
//import javafx.scene.control.TextArea;
//import javafx.scene.image.Image;
//import javafx.scene.shape.Rectangle;
//
//public class ThumbnailNode extends Node {
//
//	private Group group;
//	private Rectangle backgroundShape;
//	private Image thumbailClip;
//	private TextArea info;
//	private Thumbnail thumbnail;
//
//	public ThumbnailNode(Thumbnail thumbnail) {
//		this.group = new Group();
//		this.backgroundShape = new Rectangle(100, 200);
//
//	}
//
//	@Override
//	protected NGNode impl_createPeer() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public BaseBounds impl_computeGeomBounds(BaseBounds bounds, BaseTransform tx) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	protected boolean impl_computeContains(double localX, double localY) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public Object impl_processMXNode(MXNodeAlgorithm alg, MXNodeAlgorithmContext ctx) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}