package no.ntnu.tdt4245firefighters;

import sheep.graphics.Image;
import sheep.math.BoundingBox;

public class Fire {
	
	private Image image;
	private float x;
	private float y;
	
	private BoundingBox bb;
	
	public Fire(float x, float y)
	{
		image = new Image(R.drawable.fire);
		
		this.x = x;
		this.y = y;
		
		bb = new BoundingBox(x - image.getWidth(), x + image.getWidth(), y - image.getHeight(), y + image.getHeight());
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
}
