package models;

import javafx.scene.image.Image;

import java.util.Date;

/**
 * This is a Class to represent a Thumbnail Slot in "Get Recent Venn Diagrams"
 */
public class Thumbnail {

	private Image imageOfVenn;
	private Date dateCreated;
	private String titleOfProject;
	private String leftCircleName;
	private String rightCircleName;

	public Thumbnail(Image imageOfVenn, Date dateCreated, String titleOfProject, String leftCircleName,
					 String rightCircleName) {
		super();
		this.imageOfVenn = imageOfVenn;
		this.dateCreated = dateCreated;
		this.titleOfProject = titleOfProject;
		this.leftCircleName = leftCircleName;
		this.rightCircleName = rightCircleName;
	}

	public Image getImageOfVenn() {
		return imageOfVenn;
	}

	public void setImageOfVenn(Image imageOfVenn) {
		this.imageOfVenn = imageOfVenn;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getTitleOfProject() {
		return titleOfProject;
	}

	public void setTitleOfProject(String titleOfProject) {
		this.titleOfProject = titleOfProject;
	}

	public String getLeftCircleName() {
		return leftCircleName;
	}

	public void setLeftCircleName(String leftCircleName) {
		this.leftCircleName = leftCircleName;
	}

	public String getRightCircleName() {
		return rightCircleName;
	}

	public void setRightCircleName(String rightCircleName) {
		this.rightCircleName = rightCircleName;
	}

}
