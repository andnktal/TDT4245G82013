package no.ntnu.tdt4245firefighters;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import sheep.game.State;
import sheep.graphics.Font;
import sheep.graphics.Image;
import sheep.gui.TextButton;
import sheep.input.TouchListener;
import sheep.math.BoundingBox;

public class FirefighterPlayer extends State{

	
	private Image map;
	private Image p1pos;
	
	private float fP1X = 216f;
	private float fP1Y = 162f;	

	private boolean bPFirehazard = false;
	
	private BoundingBox bbP1;
	
	private BoundingBox rescuePoint;
	
	private String p1String = "Rescue baby?";
	private Paint p1Paint;
	private Event p1Action = null;
	private Event p1Carrying = null;
	private boolean p1NearEvent = false;
	
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

	private List<Event> gameEvents = new ArrayList<Event>();
	private List<Fire> firehazards = new ArrayList<Fire>();
	
	private Vibrator v;
	private Paint rescuePaint;
	
	public FirefighterPlayer(int screenWidth, int screenHeight, Vibrator v) {
		
		this.v = v;
		
		rescuePaint = new Paint();
		rescuePaint.setColor(Color.BLACK);
		
		rescuePoint = new BoundingBox(196f, 231f, 128f, 190f);
			
		DisplayMap(0);
		SetupPlayers(0);		
	
		timeValue = "15:00";
		
		timer = new TextButton(screenWidth - 50, 30, timeValue, new Paint[]{Font.BLUE_SANS_BOLD_20, Font.BLUE_SANS_BOLD_20});
		
		p1pos = new Image(R.drawable.ff_pos1);
		
		timerBarRectangle =  new Rect(10, 10, screenWidth - 10 , 30);
		bbP1 = new BoundingBox(fP1X, fP1X + p1pos.getWidth(), fP1Y, fP1Y + p1pos.getHeight());
		
		SetupEvents(0);
		SetupFire(0);
		
		TouchListener touchMove = new TouchListener(){

			@Override
			public boolean onTouchDown(MotionEvent event) {
				fP1X = event.getX() - p1pos.getWidth()/2;
				fP1Y = event.getY() - p1pos.getHeight()/2;
				Log.i("COORDINATES Touch", "(" + Float.toString(fP1X) + " / " + Float.toString(fP1Y) + ")");
				
				if(p1NearEvent && p1Action != null && !p1Action.getHandled() && p1Carrying == null)
				{
					p1Action.setHandled();
					p1Carrying = p1Action;
				}
				
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

	
	private void DisplayMap(int nMapsize)
	{		
		map = new Image(R.drawable.map480x800);	
	}
	
	private void SetupPlayers(int nMapSize)
	{
		fP1X = 222.0f;
		fP1Y = 162.0f;
		
		p1pos = new Image(R.drawable.ff_pos1);
		p1Paint = new Paint();
	}	

	private void SetupEvents(int nMapSize)
	{
		Event ev = new Event(new Image(R.drawable.baby), 129.0f, 240.0f);
		ev.setName("Baby Cradle");
		ev.setDescription("A baby has been left here.");
		gameEvents.add(ev);
	}
	
	private void SetupFire(int nMapSize)
	{
		firehazards.add(new Fire(33.0f, 390f));
		firehazards.add(new Fire(129.0f, 289f));
		firehazards.add(new Fire(228.0f, 609f));
	}	

	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		map.draw(canvas, 0, 0);

		//Draw event icons
		for(Event ev : gameEvents)
		{
			if(ev.getSeen()) {
				ev.getImage().draw(canvas, ev.getX(), ev.getY());
				
				if(ev.getHandled())
					canvas.drawText(ev.getName() + " [Rescued]", ev.getX(), ev.getY(), rescuePaint);
			}
		}
		
		for(Fire fir : firehazards)
		{
			if(fir.getEncountered())
				fir.getImage().draw(canvas, fir.getX(), fir.getY());
		}
		
		p1pos.draw(canvas, fP1X, fP1Y);
		
		timer.draw(canvas);
		
		canvas.drawRect(timerBarRectangle, timerBar); //drawLine(10, 10, timerBarLength, 10, timerBar);

		canvas.drawText(p1String, fP1X, fP1Y - 5, p1Paint);
		
		if(bPFirehazard)
			canvas.drawColor(Color.RED, PorterDuff.Mode.DARKEN);
	}
	
	@Override
	public void update(float dt) {
		
		bbP1 = new BoundingBox(fP1X, fP1X, fP1Y, fP1Y);
		
		p1String = "";
		bPFirehazard = false;
		p1NearEvent = false;
		
		for(Event ev : gameEvents)
		{
			if(ev.getBoundingBox().contains(bbP1))
			{
				if(!ev.getHandled())
					p1String = "Rescue " + ev.getName() + "?";
				
				p1Action = ev;
				p1NearEvent = true;
				p1Paint.setColor(Color.BLUE);
				
				ev.setSeen();
			}
		}
		
		for(Fire fir : firehazards)
		{
			if(fir.getBoundingBox().contains(bbP1))
			{
				p1Paint.setColor(Color.RED);
				
				fir.setEncountered();
				
				bPFirehazard = true;
				
				if(dt % 15000f == 0f) {
						v.vibrate(30);
				}
				
			}
		}
		
		if(rescuePoint.contains(bbP1))
		{
			p1String = "Rescue Point";
			
			if(p1Carrying != null)
			{
				p1String = "Brought " + p1Carrying.getName() + " to safety!";
				
				if(dt % 15000f == 0f)
					p1Carrying = null;
			}
			
		}
	}
}
