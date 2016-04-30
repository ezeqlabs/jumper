package br.com.ezeqlabs.jumper.elementos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

import br.com.ezeqlabs.jumper.R;
import br.com.ezeqlabs.jumper.engine.Tela;

public class Cano {
    private static final int TAMANHO_DO_CANO = 250;
    private static final int LARGURA_DO_CANO = 100;
    private int posicao;
    private final int alturaDoCanoSuperior;
    private final Bitmap canoInferior;
    private final Bitmap canoSuperior;
    private final int yCanoInferior;
    private static final int yCanoSuperior = 0;
    private static final int[] respiroPassaroCanos = {25, 35, 50, 65};
    private final Passaro passaro;
    private final Tela tela;

    public Cano(Context context, Tela tela, int posicao, Passaro passaro){
        this.posicao = posicao;
        this.passaro = passaro;
        this.tela = tela;

        this.alturaDoCanoSuperior = calculaAlturaCanoSuperior();
        int alturaDoCanoInferior = calculaAlturaCanoInferior();
        this.yCanoInferior = this.alturaDoCanoSuperior + calculaEspacoEntreCanos();

        Bitmap bp = BitmapFactory.decodeResource(context.getResources(), R.drawable.cano);
        this.canoSuperior = Bitmap.createScaledBitmap(bp, LARGURA_DO_CANO, this.alturaDoCanoSuperior, false);
        this.canoInferior = Bitmap.createScaledBitmap(bp, LARGURA_DO_CANO, alturaDoCanoInferior, false);
    }

    private int calculaAlturaCanoSuperior(){
        int alturaCano = TAMANHO_DO_CANO + valorAleatorio();
        int limite = alturaCano + calculaEspacoEntreCanos();

        if(this.tela.getAltura() <= limite){
            return calculaAlturaCanoSuperior();
        }else{
            return alturaCano;
        }
    }

    private int calculaAlturaCanoInferior(){
        return this.tela.getAltura();
    }

    private int calculaEspacoEntreCanos(){
        return this.passaro.getTamanhoPassaro() + Passaro.DESLOCAMENTO_DO_PULO * selecionaRespiroPassaro();
    }

    private int selecionaRespiroPassaro(){
        return respiroPassaroCanos[new Random().nextInt(4)];
    }

    public void desenhaNo(Canvas canvas){
        desenhaCanoSuperiorNo(canvas);
        desenhaCanoInferiorNo(canvas);
    }

    private void desenhaCanoSuperiorNo(Canvas canvas){
        canvas.drawBitmap(this.canoSuperior, this.posicao, this.yCanoSuperior, null);
    }

    private void desenhaCanoInferiorNo(Canvas canvas){
        canvas.drawBitmap(this.canoInferior, this.posicao, this.yCanoInferior, null);
    }

    public void move(){
        this.posicao -= 5;
    }

    private int valorAleatorio(){
        return (int) ((Math.random() * 250) * (Math.random() * 7));
    }

    public boolean saiuDaTela(){
        return this.posicao + LARGURA_DO_CANO < 0;
    }

    public int getPosicao(){
        return  this.posicao;
    }

    public boolean cruzouVerticalmenteCom(Passaro passaro){
        return passaro.getAltura() - Passaro.RAIO < this.alturaDoCanoSuperior ||
                passaro.getAltura() + Passaro.RAIO > this.yCanoInferior;
    }

    public boolean cruzouHorizontalmenteComPassaro(){
        return this.posicao - Passaro.X < Passaro.RAIO;
    }
}
