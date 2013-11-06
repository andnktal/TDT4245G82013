package no.ntnu.tdt4245firefighters;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import sheep.game.State;
import sheep.graphics.Image;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;

public class FirefighterGame extends State implements WidgetListener{

	
	private Image map;
	
	private Matrix matrix;
	
	public FirefighterGame(int screenWidth, int screenHeight)
	{
		map = new Image(R.drawable.testmap);
		matrix = new Matrix();
		
		RectF screen = new RectF(0,0, screenWidth, screenHeight);
		RectF image = new RectF(0,0, map.getWidth(), map.getHeight());
		
		matrix.setRectToRect(image, screen, Matrix.ScaleToFit.CENTER);
		matrix.setRotate(90, screenWidth/2, (screenWidth/2));
		
	}
	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		//map.draw(canvas, 0, 0);
		map.draw(canvas, matrix);
	}	
	
	@Override
	public void actionPerformed(WidgetAction action) {
		// TODO Auto-generated method stub
		
	}

}