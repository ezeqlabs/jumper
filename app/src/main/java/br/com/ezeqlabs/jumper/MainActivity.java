package br.com.ezeqlabs.jumper;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import br.com.ezeqlabs.jumper.engine.Game;

public class MainActivity extends Activity {
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout container = (FrameLayout) findViewById(R.id.container);

        this.game = new Game(this);
        container.addView(this.game);
    }

    @Override
    protected void onPause(){
        super.onPause();
        this.game.cancela();
    }

    @Override
    protected void onResume(){
        super.onResume();
        this.game.inicia();
        new Thread(this.game).start();
    }
}
