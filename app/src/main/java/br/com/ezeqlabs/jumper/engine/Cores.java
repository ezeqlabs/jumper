package br.com.ezeqlabs.jumper.engine;

import android.graphics.Paint;
import android.graphics.Typeface;

public class Cores {
    public static Paint getCorDoPassaro(){
        Paint vermelho = new Paint();
        vermelho.setColor(0xFFFF0000); //0xFF representa a opacidade
        return vermelho;
    }

    public static Paint getCorDoCano(){
        Paint verde = new Paint();
        verde.setColor(0xFF00FF00);
        return verde;
    }

    public static Paint getCorDaPontuacao(){
        Paint branco = new Paint();
        branco.setColor(0xFFFFFFFF);
        branco.setTextSize(80);
        branco.setTypeface(Typeface.DEFAULT_BOLD);
        branco.setShadowLayer(3, 5, 5, 0xFF4682B4);
        return branco;
    }

    public static Paint getCorDoGameOver(){
        Paint vermelho = new Paint();
        vermelho.setColor(0xFFCF0505);
        vermelho.setTextSize(100);
        vermelho.setTypeface(Typeface.DEFAULT_BOLD);
        vermelho.setShadowLayer(2, 3, 3, 0xFF4682B4);
        return vermelho;
    }

    public static Paint getCorPontuacaoDoGameOver(){
        Paint marrom = new Paint();
        marrom.setColor(0xFFFFA500);
        marrom.setTextSize(70);
        marrom.setTypeface(Typeface.DEFAULT_BOLD);
        marrom.setShadowLayer(2, 3, 3, 0xFF4682B4);
        return marrom;
    }

    public static Paint getCorNovamenteDoGameOver(){
        Paint marrom = new Paint();
        marrom.setColor(0xFFFFA500);
        marrom.setTextSize(50);
        marrom.setTypeface(Typeface.DEFAULT_BOLD);
        marrom.setShadowLayer(2, 3, 3, 0xFF4682B4);
        return marrom;
    }
}
