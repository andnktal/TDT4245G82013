package no.ntnu.tdt4245firefighters;

import sheep.game.Game;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.Menu;

public class MainMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Game game = new Game(this, null);
        
        Display display = getWindowManager().getDefaultDisplay();
        //Point size = new Point();
        //display.getSize(size);
        int width = display.getWidth();
        int height = display.getHeight();
        
        game.pushState(new FirefighterGame(width, height));
        
        setContentView(game);
        //setContentView(R.layout.activity_main_menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    
}
