package no.ntnu.tdt4245firefighters;

import sheep.graphics.Image;
import sheep.gui.TextButton;
import sheep.math.BoundingBox;

public class Event {
	
	private Image image;
	private float x;
	private float y;
	private String name;
	private String description;
	private BoundingBox bb;
	private boolean seen = false;
	private boolean handled = false;
	
	
	public Event(Image image, float x, float y)
	{
		this.image = image;
		this.x = x;
		this.y = y;
		
		bb = new BoundingBox(x - 10, x + image.getWidth() + 10, y - 10, y + image.getHeight() + 10);
	}
	
	public Image getImage()
	{
		return image;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public BoundingBox getBoundingBox()
	{
		return bb;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setSeen() {
		this.seen = true;
	}
	
	public boolean getSeen() {
		return seen;
	}
	
	public void setHandled() {
		this.handled = true;
	}
	
	public boolean getHandled() {
		return this.handled;
	}
	
}
