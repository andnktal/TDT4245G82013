package no.ntnu.tdt4245firefighters;

import sheep.graphics.Image;
import sheep.math.BoundingBox;

public class Fire {
	
	private Image image;
	private float x;
	private float y;
	private boolean encountered = false;
	
	private BoundingBox bb;
	
	public Fire(float x, float y)
	{
		image = new Image(R.drawable.fire);
		
		this.x = x;
		this.y = y;
		
		bb = new BoundingBox(x - 20, x + image.getWidth() + 20, y - 20, y + image.getHeight() + 20);
	}
	
	public Image getImage()
	{
		return image;
	}
	
	public BoundingBox getBoundingBox()
	{
		return bb;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public void setEncountered()
	{
		encountered = true;
	}
	
	public boolean getEncountered()
	{
		return encountered;
	}
}
