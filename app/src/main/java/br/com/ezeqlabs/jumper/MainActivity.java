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

public class MainActivity extends BaseGameActivity {
    private Game game;
    private static final String JUMPER_PREF = "JUMPER_PREF";
    private FrameLayout container;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.container = (FrameLayout) findViewById(R.id.container);
        this.preferences = getSharedPreferences(JUMPER_PREF, 0);
        
        getGameHelper().setMaxAutoSignInAttempts(0);

        this.game = new Game(this, this.preferences, getApiClient());
        this.container.addView(this.game);
    }

    @Override
    protected void onPause(){
        super.onPause();
        this.game.cancela();
    }

    private void iniciaJogo(){
        this.game.inicia();
        new Thread(this.game).start();
    }

    @Override
    public void onSignInFailed() {
        iniciaJogo();
    }

    @Override
    public void onSignInSucceeded() {
        iniciaJogo();
    }
}
