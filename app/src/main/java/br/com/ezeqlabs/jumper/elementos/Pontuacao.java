package br.com.ezeqlabs.jumper.elementos;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

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
    private int pontos = 0;
    private final Som som;
    private final SharedPreferences preferences;
    private final GoogleApiClient googleApiClient;
    private Set<String> conquistasGanhas = new HashSet<String>();
    private Bitmap moeda;
    private Context context;

    public Pontuacao(Context context, Som som, SharedPreferences preferences, GoogleApiClient googleApiClient){
        this.som = som;
        this.preferences = preferences;
        this.googleApiClient = googleApiClient;
        this.context = context;

        Bitmap bp = BitmapFactory.decodeResource(context.getResources(), R.drawable.moeda);
        this.moeda = Bitmap.createScaledBitmap(bp, 50, 59, false);
    }

    public void aumenta(){
        this.som.toca(Som.PONTUACAO);
        this.pontos++;
        aumentaMoedas();
        verificaConquistas(pontos);

        if(this.pontos > getPontuacaoMaxima()){
            salvaPontuacaoMaxima(this.pontos);
        }
    }

    public void desenhaNo(Canvas canvas){
        canvas.drawText("Placar: " + String.valueOf(this.pontos), 25, 100, BRANCO);
        desenhaMoedas(canvas);
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

    private void aumentaMoedas(){
        int moedas = this.preferences.getInt("moedas", 0);
        int moedasTotais = this.preferences.getInt("moedasTotais", 0);
        SharedPreferences.Editor editor = this.preferences.edit();

        moedas += 1;
        moedasTotais += 1;

        editor.putInt("moedas", moedas);
        editor.putInt("moedasTotais", moedasTotais);
        editor.apply();
    }

    private void desenhaMoedas(Canvas canvas){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        canvas.drawBitmap(this.moeda, size.x - 250, 42, null);
        canvas.drawText(String.valueOf(this.preferences.getInt("moedas", 0)), size.x - 180, 100, BRANCO);
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
