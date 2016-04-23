package br.com.ezeqlabs.jumper.elementos;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;

import br.com.ezeqlabs.jumper.engine.Cores;
import br.com.ezeqlabs.jumper.engine.Som;

public class Pontuacao{
    private static final Paint BRANCO = Cores.getCorDaPontuacao();
    public static final String JUMPER_PREF = "JUMPER_PREF";
    private int pontos = 0;
    private final Som som;
    private final SharedPreferences preferences;

    public Pontuacao(Som som, SharedPreferences preferences){
        this.som = som;
        this.preferences = preferences;
    }

    public void aumenta(){
        this.som.toca(Som.PONTUACAO);
        this.pontos++;
        if(this.pontos > getPontuacaoMaxima()){
            salvaPontuacaoMaxima(this.pontos);
        }
    }

    public void desenhaNo(Canvas canvas){
        canvas.drawText(String.valueOf(this.pontos), 100, 100, BRANCO);
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
        editor.apply();
    }
}
