package no.ntnu.tdt4245firefighters;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import sheep.game.State;
import sheep.graphics.Image;
import sheep.gui.TextButton;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;
import sheep.input.TouchListener;

public class FirefighterGame extends State implements WidgetListener{

	
	private Image map;
	private Image p1pos;
	
	private Image event1;
	private Image event2;
	private Image event3;
	
	private float fP1X;
	private float fP1Y;
	
	private Matrix matrix;
	
	private TextButton test;
	
	public FirefighterGame(int screenWidth, int screenHeight)
	{
		map = new Image(R.drawable.testmap);
		matrix = new Matrix();
		
		RectF screen = new RectF(0,0, screenWidth, screenHeight);
		RectF image = new RectF(0,0, map.getWidth(), map.getHeight());
		
		matrix.setRectToRect(image, screen, Matrix.ScaleToFit.CENTER);
		matrix.setRotate(90, screenWidth/2, (screenWidth/2));
		
		event1 = new Image(R.drawable.baby);
		event2 = new Image(R.drawable.baby);
		event3 = new Image(R.drawable.baby);
		
		p1pos = new Image(R.drawable.ff_pos1);
		fP1X = 50.0f;
		fP1Y = screenHeight - p1pos.getHeight();
		
		TouchListener touchMove = new TouchListener(){

			@Override
			public boolean onTouchDown(MotionEvent event) {
				fP1X = event.getX() - p1pos.getWidth()/2;
				fP1Y = event.getY() - p1pos.getHeight()/2;
				Log.i("COORDINATES Touch", "(" + Float.toString(fP1X) + " / " + Float.toString(fP1Y) + ")");
				return true;
			}

			@Override
			public boolean onTouchUp(MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onTouchMove(MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
			
		};
		
		this.addTouchListener(touchMove);
	}
	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		//map.draw(canvas, 0, 0);
		map.draw(canvas, matrix);
		event1.draw(canvas, 160.5f, 215.5f);
		event2.draw(canvas, 184.5f, 371.5f);
		event3.draw(canvas, 306.5f, 593.5f);
		p1pos.draw(canvas, fP1X, fP1Y);
	}	
	
	@Override
	public void actionPerformed(WidgetAction action) {
		// TODO Auto-generated method stub
		
	}

}
