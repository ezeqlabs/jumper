package br.com.ezeqlabs.jumper.elementos;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import br.com.ezeqlabs.jumper.R;
import br.com.ezeqlabs.jumper.engine.Cores;
import br.com.ezeqlabs.jumper.engine.Som;
import br.com.ezeqlabs.jumper.helpers.Constantes;

public class Pontuacao{
    private static final Paint BRANCO = Cores.getCorDaPontuacao();
    public static final String JUMPER_PREF = "JUMPER_PREF";
    private int pontos = 0;
    private final Som som;
    private final SharedPreferences preferences;
    private final GoogleApiClient googleApiClient;
    private Set<String> conquistasGanhas = new HashSet<String>();

    public Pontuacao(Som som, SharedPreferences preferences, GoogleApiClient googleApiClient){
        this.som = som;
        this.preferences = preferences;
        this.googleApiClient = googleApiClient;
    }

    public void aumenta(){
        this.som.toca(Som.PONTUACAO);
        this.pontos++;
        verificaConquistas(pontos);

        if(this.pontos > getPontuacaoMaxima()){
            salvaPontuacaoMaxima(this.pontos);
        }
    }

    public void desenhaNo(Canvas canvas){
        canvas.drawText("Placar: " + String.valueOf(this.pontos), 50, 100, BRANCO);
    }

    public int getPontos(){
        return pontos;
    }

    public int getPontuacaoMaxima(){
        return this.preferences.getInt("maximo", 0);
    }

    private void salvaPontuacaoMaxima(int pontos){
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putInt("maximo", pontos);
        editor.putStringSet("conquistas", conquistasGanhas);
        editor.apply();
        if( temGoogleApiConectada() ) {
            Games.Leaderboards.submitScore(this.googleApiClient, Constantes.PLACAR, pontos);
        }
    }

    private void verificaConquistas(int pontos){
        switch (pontos){
            case 1:
                liberaConquista(Constantes.CONQUISTA_1, 0);
                break;

            case 5:
                liberaConquista(Constantes.CONQUISTA_5, 2);
                break;

            case 7:
                liberaConquista(Constantes.CONQUISTA_7, 3);
                break;

            case 10:
                liberaConquista(Constantes.CONQUISTA_10, 4);
                break;

            case 15:
                liberaConquista(Constantes.CONQUISTA_15, 5);
                break;

            case 25:
                liberaConquista(Constantes.CONQUISTA_25, 6);
                break;

            case 50:
                liberaConquista(Constantes.CONQUISTA_50, 7);
                break;
        }
    }

    private void liberaConquista(String codigo, int etapas){
        if(temGoogleApiConectada()) {
            if(etapas > 0) {
                Games.Achievements.increment(this.googleApiClient, codigo, etapas);
            }else{
                Games.Achievements.unlock(this.googleApiClient, codigo);
            }
        }
    }

    private boolean temGoogleApiConectada(){
        return this.googleApiClient.isConnected();
    }
}
