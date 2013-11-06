package no.ntnu.tdt4245firefighters;

import sheep.game.Game;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.View;

public class MainMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    
    /** Called when the user clicks the Send button */
    public void sendHostingAnewGameMessage(View view) {
        // Do something in response to button
    	
    	//Intent intent = new Intent(this, DisplayHostingNewGameMessageActivity.class);
    	//startActivity(intent);
        Game game = new Game(this, null);
        
        Display display = getWindowManager().getDefaultDisplay();
        //Point size = new Point();
        //display.getSize(size);
        int width = display.getWidth();
        int height = display.getHeight();
        
        game.pushState(new FirefighterGame(width, height));
        
        setContentView(game);
    }
    
    public void sendConnectingToAGameMessage(View view) {
    	Intent intent = new Intent(this, DisplayConnectingToAGameActivity.class);
    	startActivity(intent);
    	
    }
    
    
    
}
