package br.com.ezeqlabs.jumper.elementos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import br.com.ezeqlabs.jumper.R;
import br.com.ezeqlabs.jumper.engine.Cores;
import br.com.ezeqlabs.jumper.engine.Tela;

public class Passaro {
    public static final int X = 100;
    public static final int RAIO = 50;
    private static final Paint VERMELHO = Cores.getCorDoPassaro();
    private Tela tela;
    private final Bitmap passaro;

    private int altura;

    public Passaro(Context context, Tela tela){
        this.altura = 100;
        this.tela = tela;
        Bitmap bp = BitmapFactory.decodeResource(context.getResources(), R.drawable.passaro);
        this.passaro = Bitmap.createScaledBitmap(bp, RAIO*2, RAIO*2, false);
    }

    public void desenhaNo(Canvas canvas){
        canvas.drawBitmap(this.passaro, X - RAIO, this.altura - RAIO, null);
    }

    public void cai(){
        boolean chegouNoChao = this.altura + RAIO > tela.getAltura();

        if(!chegouNoChao) {
            this.altura += 5;
        }
    }

    public void pula() {
        if (this.altura > RAIO) {
            this.altura -= 150;
        }
    }

    public int getAltura(){
        return this.altura;
    }
}
