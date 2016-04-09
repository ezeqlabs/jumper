package br.com.ezeqlabs.jumper.elementos;

import android.graphics.Canvas;
import android.graphics.Paint;

import br.com.ezeqlabs.jumper.engine.Cores;

public class Passaro {
    private static final int X = 100;
    private static final int RAIO = 50;
    private static final Paint VERMELHO = Cores.getCorDoPassaro();

    private int altura;

    public Passaro(){
        this.altura = 100;
    }

    public void desenhaNo(Canvas canvas){
        canvas.drawCircle(X, this.altura, RAIO, VERMELHO);
    }

    public void cai(){
        this.altura += 5;
    }

    public void pula(){
        this.altura -= 150;
    }
}
