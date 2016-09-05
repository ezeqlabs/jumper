package br.com.ezeqlabs.jumper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import br.com.ezeqlabs.jumper.helpers.Constantes;

public class GameOverActivity extends Activity {
    private int resultado;
    private TextView textViewResultado;
    private TextView textViewRecorde;
    private SharedPreferences sharedPreferences;
    private Button jogarNovamente;
    private Button compartilharResultado;
    private Activity activity;
    private Button menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        FacebookSdk.sdkInitialize(getApplicationContext());
        MultiDex.install(this);

        inicializaElementos();
        trataTextos();
        trataBotoes();
    }

    private void inicializaElementos(){
        Intent jogoIntent = getIntent();
        this.resultado = jogoIntent.getIntExtra("pontuacao", 0);
        this.textViewResultado = (TextView) findViewById(R.id.text_gameover_resultado);
        this.textViewRecorde = (TextView) findViewById(R.id.text_gameover_recorde);
        this.sharedPreferences  = getSharedPreferences(Constantes.JUMPER_PREF, 0);
        this.jogarNovamente = (Button) findViewById(R.id.bt_gameover_restart);
        this.compartilharResultado = (Button) findViewById(R.id.fb_share_gameover);
        this.activity = this;
        this.menu = (Button) findViewById(R.id.bt_gameover_menu);
    }

    private void trataTextos(){
        textViewResultado.setText("Você passou por "+ resultado +" canos");
        textViewRecorde.setText("Recorde: "+ sharedPreferences.getInt(Constantes.PREFS_MAXIMA_PONTUACAO, 0) +" canos");
    }

    private void trataBotoes(){
        // JOGAR NOVAMENTE
        jogarNovamente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vaiParaJogo();
            }
        });

        // COMPARTILHAR RESULTADO
        compartilharResultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareDialog shareDialog = new ShareDialog(activity);
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Novo resultado no Jumper")
                            .setQuote("Acabei de passar por "+ resultado +" canos no Jumper. Venha jogar comigo!")
                            .setContentDescription("Acabei de passar por "+ resultado +" canos no Jumper. Venha jogar comigo!")
                            .setContentUrl(Uri.parse(getString(R.string.share_melhor_link)))
                            .build();

                    shareDialog.show(linkContent);
                }
            }
        });

        // MENU
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vaiParaMenu();
            }
        });
    }

    private void vaiParaMenu(){
        Intent intentMenu = new Intent(this, BoasVindasActivity.class);
        startActivity(intentMenu);
    }

    private void vaiParaJogo(){
        Intent intentJogo = new Intent(this, MainActivity.class);
        startActivity(intentJogo);
    }
}
