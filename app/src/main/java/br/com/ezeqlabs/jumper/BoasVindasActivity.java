package br.com.ezeqlabs.jumper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;

import java.io.Serializable;

import br.com.ezeqlabs.jumper.elementos.Pontuacao;
import br.com.ezeqlabs.jumper.helpers.Constantes;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

public class BoasVindasActivity extends BaseGameActivity{
    private Pontuacao pontuacao;
    private Context that;
    private SharedPreferences sharedPreferences;
    private Button jogar;
    private TextView moedas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boas_vindas);



        inicializaElementos();
        exibeMoedas();
        reduzQuantidadeDeConexao();
        montaTextos();
        trataBotao(jogar);
        trataBotoesGooglePlay();
        montaAnuncio();
        montaTutorial(jogar);
    }

    @Override
    public void onSignInSucceeded() {
        findViewById(R.id.sign_in_button).setVisibility(View.GONE);
        findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
    }
    @Override
    public void onSignInFailed() {
        findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
        findViewById(R.id.sign_out_button).setVisibility(View.GONE);
    }

    private void inicializaElementos(){
        this.sharedPreferences = getSharedPreferences(Constantes.JUMPER_PREF, 0);
        this.jogar = (Button) findViewById(R.id.menu_principal_jogar);
        this.moedas = (TextView) findViewById(R.id.boas_vindas_moedas);
        this.pontuacao = new Pontuacao(this, null, this.sharedPreferences, getApiClient());
        this.that = this;
    }

    private void exibeMoedas(){
        this.moedas.setText( ""+this.sharedPreferences.getInt("moedas", 0) );
    }

    private void reduzQuantidadeDeConexao(){
        if( this.sharedPreferences.getInt("tentivas_conectar", 1) == 0 ){
            getGameHelper().setMaxAutoSignInAttempts(0);
        }else{
            SharedPreferences.Editor editor = this.sharedPreferences.edit();
            editor.putInt("tentivas_conectar", 0);
            editor.commit();
        }
    }

    private void montaTextos(){
        TextView texto = (TextView) findViewById(R.id.menu_principal_texto);

        String textoPontuacao = getString(R.string.seu_recorde, this.pontuacao.getPontuacaoMaxima());
        texto.setText(textoPontuacao);
    }

    private void trataBotao(Button jogar){
        jogar.setText(getString(R.string.botao_jogar).toUpperCase());
        jogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(BoasVindasActivity.this, MainActivity.class);
                startActivity(main);
                finish();
            }
        });
    }

    private void montaAnuncio(){
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void montaTutorial(Button jogar){
        new MaterialShowcaseView.Builder(this)
                .setTarget(jogar)
                .setDismissText(R.string.tutorial_botao)
                .setContentText(R.string.tutorial_texto)
                .setDelay(500)
                .singleUse("1")
                .show();
    }

    private void trataBotoesGooglePlay(){
        SignInButton entrar = (SignInButton) findViewById(R.id.sign_in_button);
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginUserInitiatedSignIn();
            }
        });

        ImageView sair = (ImageView) findViewById(R.id.sign_out_button);
        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
                findViewById(R.id.sign_out_button).setVisibility(View.GONE);
            }
        });

        ImageView conquistas = (ImageView) findViewById(R.id.show_achievements);
        conquistas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( isSignedIn() ){
                    startActivityForResult(Games.Achievements.getAchievementsIntent(getApiClient()), 1);
                }else{
                    Toast.makeText(that, getString(R.string.erro_play_deslogado, getString(R.string.conquistas)), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView placar = (ImageView) findViewById(R.id.show_leaderboard);
        placar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( isSignedIn() ){
                    startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), Constantes.PLACAR), 2);
                }else{
                    Toast.makeText(that, getString(R.string.erro_play_deslogado, getString(R.string.placar)), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
