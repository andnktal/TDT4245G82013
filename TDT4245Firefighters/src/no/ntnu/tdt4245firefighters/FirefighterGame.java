package no.ntnu.tdt4245firefighters;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import sheep.game.State;
import sheep.graphics.Font;
import sheep.graphics.Image;
import sheep.gui.TextButton;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;
import sheep.input.TouchListener;
import android.content.pm.LabeledIntent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import sheep.math.BoundingBox;

public class FirefighterGame extends State implements WidgetListener{

	
	private Image map;
	private Image p1pos;
	
	private Image event1;
	private Image event2;
	private Image event3;
	
	private float fP1X;
	private float fP1Y;
	
	private BoundingBox bbP1;
	private BoundingBox bbEvent1;
	
	private Matrix matrix;
	
	private TextButton msgBox;
	private TextButton timer;
	private String timeValue;
	
	//==|| For the timerline
	private int countDownMaxSeconds = 30; // 30 seconds
	private int maxTimeInMilliseconds = countDownMaxSeconds * 1000;
	private int countDownUnitInSeconds = 1; //1 seconds
	private int countDownUnitInMilliseconds = countDownUnitInSeconds * 1000; 
	private int timerBarLength = 0;
	private int eachUnitLength = timerBarLength / countDownMaxSeconds;
	
	private Paint timerBar = new Paint();
	private Rect timerBarRectangle; //
	
	private int screenWidthIs;
	private TextButton test;
	private boolean rescueText = false;
	private String rescueSTR = "Rescue baby?";
	
	public FirefighterGame(int screenWidth, int screenHeight)
	{
		map = new Image(R.drawable.testmap);
		matrix = new Matrix();
		
		RectF screen = new RectF(0,0, screenWidth, screenHeight);
		RectF image = new RectF(0,0, map.getWidth(), map.getHeight());
		
		matrix.setRectToRect(image, screen, Matrix.ScaleToFit.CENTER);
		matrix.setRotate(90, screenWidth/2, (screenWidth/2));
		
		timeValue = "15:00";
	
		
		timer = new TextButton(screenWidth - 50, 30, timeValue, new Paint[]{Font.BLUE_SANS_BOLD_20, Font.BLUE_SANS_BOLD_20});
		
		//msgBox = new TextButton(screenWidth - 70, 30, "something", new Paint[]{Font.BLUE_SANS_BOLD_20, Font.BLUE_SANS_BOLD_20});
		//timer.
		
		event1 = new Image(R.drawable.baby);
		event2 = new Image(R.drawable.baby);
		event3 = new Image(R.drawable.baby);
		
		p1pos = new Image(R.drawable.ff_pos1);
		fP1X = 50.0f;
		fP1Y = screenHeight - p1pos.getHeight();
		
		timerBarRectangle =  new Rect(10, 10, screenWidth - 10 , 30);
		bbP1 = new BoundingBox(fP1X, fP1X + p1pos.getWidth(), fP1Y, fP1Y + p1pos.getHeight());
		
		bbEvent1 = new BoundingBox(150.5f, 150.5f + event1.getWidth()*2, 205.5f, 205.5f + event1.getHeight()*2);
		
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
		
		
		screenWidthIs = screenWidth;
		timerBarLength = screenWidth - 10;
		eachUnitLength = timerBarLength / countDownMaxSeconds;
		
		//==|| The Timer
		new CountDownTimer(maxTimeInMilliseconds, countDownUnitInMilliseconds) {

		     public void onTick(long millisUntilFinished) {		    	 
		    	 String val = Long.toString(millisUntilFinished / 1000);
		    	 timer.setLabel(val);
		    	 timerBarLength = timerBarLength - eachUnitLength;
		    	// msgBox.setLabel(Integer.toString(timerBarLength));
		    	 
		    	 //Log.i("screenWidthIs", Integer.toString(screenWidthIs) + " , timerBarLength = " + Integer.toString(timerBarLength) + " , percentage = "+ Float.toString((float)timerBarLength / (float)(screenWidthIs - 20)));
		    	 
		    	 timerBarRectangle.set(10, 10, timerBarLength, 30);
		    	 
		    	 if((float)timerBarLength /(float) (screenWidthIs - 20) * 100 > 75 ){
		    		 timerBar.setColor(Color.GREEN);
		    	 }
		    	 else if((float)timerBarLength /(float) (screenWidthIs - 20) * 100 > 50 ){
		    		 timerBar.setColor(Color.BLUE);
		    	 }
		    	 else if((float)timerBarLength /(float) (screenWidthIs - 20) * 100 > 25 ){
		    		 timerBar.setColor(Color.YELLOW);
		    	 }
		    	 else {
		    		 timerBar.setColor(Color.RED);
		    	 }
		    	 
		     }

		     public void onFinish() {
		    	 timer.setLabel("0");
		     }
		  }.start();
		 
		
		this.addTouchListener(touchMove);
	}
	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		//map.draw(canvas, 0, 0);
		map.draw(canvas, matrix);
		event1.draw(canvas, 160.5f, 215.5f);
		//event2.draw(canvas, 184.5f, 371.5f);
		//event3.draw(canvas, 306.5f, 593.5f);
		p1pos.draw(canvas, fP1X, fP1Y);
		
		timer.draw(canvas);
		//msgBox.draw(canvas);
		
		canvas.drawRect(timerBarRectangle, timerBar); //drawLine(10, 10, timerBarLength, 10, timerBar);

		if(rescueText)
			canvas.drawText(rescueSTR, fP1X, fP1Y + 30, new Paint());
	}
	
	@Override
	public void update(float dt) {
		
		bbP1 = new BoundingBox(fP1X, fP1X + p1pos.getWidth(), fP1Y, fP1Y + p1pos.getHeight());
		
		if(bbEvent1.contains(bbP1))
		{
			event1 = new Image(R.drawable.babyrescued);
			rescueText = true;
		}
	}
	
	@Override
	public void actionPerformed(WidgetAction action) {
		// TODO Auto-generated method stub
		
	}

}
