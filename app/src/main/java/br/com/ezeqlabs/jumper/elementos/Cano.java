package br.com.ezeqlabs.jumper.elementos;

import android.graphics.Canvas;
import android.graphics.Paint;

import br.com.ezeqlabs.jumper.engine.Cores;
import br.com.ezeqlabs.jumper.engine.Tela;

public class Cano {
    private static final int TAMANHO_DO_CANO = 250;
    private static final int LARGURA_DO_CANO = 100;
    private static final Paint VERDE = Cores.getCorDoCano();
    private int alturaDoCanoInferior;
    private Tela tela;
    private int posicao;
    private int alturaDoCanoSuperior;

    public Cano(Tela tela, int posicao){
        this.tela = tela;
        this.posicao = posicao;
        this.alturaDoCanoInferior = tela.getAltura() - TAMANHO_DO_CANO - valorAleatorio();
        this.alturaDoCanoSuperior = 0 + TAMANHO_DO_CANO + valorAleatorio();
    }

    public void desenhaNo(Canvas canvas){
        desenhaCanoSuperiorNo(canvas);
        desenhaCanoInferiorNo(canvas);
    }

    private void desenhaCanoSuperiorNo(Canvas canvas){
        canvas.drawRect(this.posicao, 0, this.posicao + LARGURA_DO_CANO, this.alturaDoCanoSuperior, VERDE);
    }

    private void desenhaCanoInferiorNo(Canvas canvas){
        canvas.drawRect(this.posicao, this.alturaDoCanoInferior, this.posicao + LARGURA_DO_CANO, this.tela.getAltura(), VERDE);
    }

    public void move(){
        this.posicao -= 5;
    }

    private int valorAleatorio(){
        return (int) (Math.random() * 150);
    }

    public boolean saiuDaTela(){
        return this.posicao + LARGURA_DO_CANO < 0;
    }

    public int getPosicao(){
        return  this.posicao;
    }
}
