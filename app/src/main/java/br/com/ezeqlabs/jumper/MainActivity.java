package br.com.ezeqlabs.jumper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.example.games.basegameutils.BaseGameActivity;

import br.com.ezeqlabs.jumper.engine.Game;

public class MainActivity extends Activity {
    private Game game;
    private static final String JUMPER_PREF = "JUMPER_PREF";
    public static GoogleApiClient googleApiClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout container = (FrameLayout) findViewById(R.id.container);
        SharedPreferences preferences = getSharedPreferences(JUMPER_PREF, 0);

        this.game = new Game(this, preferences, this.googleApiClient);
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
