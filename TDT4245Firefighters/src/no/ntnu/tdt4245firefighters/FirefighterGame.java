package no.ntnu.tdt4245firefighters;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import sheep.game.State;
import sheep.graphics.Font;
import sheep.graphics.Image;
import sheep.gui.TextButton;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;
import sheep.input.TouchListener;
import android.graphics.Rect;
import android.os.CountDownTimer;
import sheep.math.BoundingBox;

import java.util.ArrayList;
import java.util.List;

public class FirefighterGame extends State implements WidgetListener{

	
	private Image map;
	private Image p1pos, p2pos, p3pos, p4pos;
	
	private float fP1X = 273f;
	private float fP1Y = 243f;
	
	private float fP2X = 388f;
	private float fP2Y = 249f;
	
	private float fP3X = 400f;
	private float fP3Y = 617f;
	
	private float fP4X = 141f;
	private float fP4Y = 695f;
	
	private BoundingBox bbP1;
	
	private String p1String = "Rescue baby?";
	private Paint p1Paint;
	
	private TextButton timer;
	private String timeValue;
	
	private TextButton eventPanelBtn;	
	
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
	
	public FirefighterGame(int screenWidth, int screenHeight)
	{
		
		DisplayMap(0);
		SetupPlayers(0);
		
		timeValue = "15:00";
		
		eventPanelBtn = new TextButton(350, 680, "Event Panel", new Paint[]{Font.BLUE_SANS_BOLD_20, Font.BLUE_SANS_BOLD_20});
		eventPanelBtn.addWidgetListener(this);
		addTouchListener(eventPanelBtn);
		
		timer = new TextButton(screenWidth - 50, 30, timeValue, new Paint[]{Font.BLUE_SANS_BOLD_20, Font.BLUE_SANS_BOLD_20});
		
		p1pos = new Image(R.drawable.ff_pos1);
		p2pos = new Image(R.drawable.ff_pos2);
		p3pos = new Image(R.drawable.ff_pos3);
		p4pos = new Image(R.drawable.ff_pos4);
		
		
		//fP1X = 50.0f;
		//fP1Y = screenHeight - p1pos.getHeight();
		
		timerBarRectangle =  new Rect(10, 10, screenWidth - 10 , 30);
		bbP1 = new BoundingBox(fP1X, fP1X + p1pos.getWidth(), fP1Y, fP1Y + p1pos.getHeight());
		
		SetupEvents(0);
		//SetupFire(0);
		
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
		 
	}
	
	private void DisplayMap(int nMapsize)
	{		
		map = new Image(R.drawable.map480x800);	
	}
	
	private void SetupPlayers(int nMapSize)
	{
		fP1X = 216.0f;
		fP1Y = 162.0f;
		
		p1pos = new Image(R.drawable.ff_pos1);
		p1Paint = new Paint();
		p1Paint.setColor(Color.BLUE);
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
			ev.getImage().draw(canvas, ev.getX(), ev.getY());
		}
		
		for(Fire fir : firehazards)
		{
			fir.getImage().draw(canvas, fir.getX(), fir.getY());
		}
		
		//==|| The players in the map
		p1pos.draw(canvas, fP1X, fP1Y);
		p2pos.draw(canvas, fP2X, fP2Y);
		p3pos.draw(canvas, fP3X, fP3Y);
		p4pos.draw(canvas, fP4X, fP4Y);
		
		//==|| The timer 
		timer.draw(canvas);
		
		//==|| The event panel button
		//eventPanelBtn.draw(canvas);
		
		//canvas.drawRect(timerBarRectangle, timerBar); //drawLine(10, 10, timerBarLength, 10, timerBar);

		canvas.drawText(p1String, fP1X, fP1Y - 5, p1Paint);
	}
	
	@Override
	public void update(float dt) {
		
		bbP1 = new BoundingBox(fP1X - 20, fP1X + p1pos.getWidth() + 20, fP1Y - 20, fP1Y + p1pos.getHeight() + 20);
		
		p1String = "";
		
		for(Event ev : gameEvents)
		{
			if(bbP1.contains(ev.getBoundingBox()))
			{
				p1String = "Rescue " + ev.getName() + "?";
				p1Paint.setColor(Color.BLUE);
			}
		}
		
		for(Fire fir : firehazards)
		{
			if(bbP1.contains(fir.getBoundingBox()))
			{
				p1String = "FIRE!!! FIRE!!! FIRE!!!";
				p1Paint.setColor(Color.RED);
			}
		}
	}
	
	@Override
	public void actionPerformed(WidgetAction action) {
		// TODO Auto-generated method stub
		if(action.getSource() == eventPanelBtn)
		{
			Log.i("Event panel btn","Clicked");
			
			//Intent intent = new Intent(this, CoordinatorEventsActivity.class);
			//Intent intent = new Intent();
			//intent.setClassName(this, CoordinatorEventsActivity.class);	
			CoordinatorEventsActivity newOne = new CoordinatorEventsActivity();
			//View view = new View(newOne);
			
			
			
			 // startActivity(intent);
			  
		}
		
	}

}
