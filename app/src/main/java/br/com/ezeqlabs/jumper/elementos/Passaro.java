package br.com.ezeqlabs.jumper.elementos;

import android.graphics.Canvas;
import android.graphics.Paint;

import br.com.ezeqlabs.jumper.engine.Cores;
import br.com.ezeqlabs.jumper.engine.Tela;

public class Passaro {
    public static final int X = 100;
    public static final int RAIO = 50;
    private static final Paint VERMELHO = Cores.getCorDoPassaro();
    private Tela tela;

    private int altura;

    public Passaro(Tela tela){
        this.altura = 100;
        this.tela = tela;
    }

    public void desenhaNo(Canvas canvas){
        canvas.drawCircle(X, this.altura, RAIO, VERMELHO);
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
