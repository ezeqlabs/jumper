package br.com.ezeqlabs.jumper.engine;

import android.graphics.Paint;

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
}
