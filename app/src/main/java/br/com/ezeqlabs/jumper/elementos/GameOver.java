package br.com.ezeqlabs.jumper.elementos;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import br.com.ezeqlabs.jumper.engine.Cores;
import br.com.ezeqlabs.jumper.engine.Tela;

public class GameOver {
    private final Tela tela;
    private static final Paint GAME_OVER = Cores.getCorDoGameOver();
    private static final Paint PONTUACAO = Cores.getCorPontuacaoDoGameOver();
    private static final Paint NOVAMENTE = Cores.getCorNovamenteDoGameOver();
    private Pontuacao pontuacao;
    private int quantidadePontos;

    public GameOver(Tela tela, Pontuacao pontuacao){
        this.tela = tela;
        this.pontuacao = pontuacao;
        this.quantidadePontos = pontuacao.getPontos();
    }

    public void desenhaNo(Canvas canvas){
        String gameOver = "Você perdeu";
        String pontuacao = textoPontuacao();
        String pontuacaoMaxima = textoMelhorPontuacao();
        String novamente = "Clique para jogar novamente";

        canvas.drawText(gameOver, centralizaTexto(gameOver, GAME_OVER), (this.tela.getAltura() / 2) - 300, GAME_OVER);
        canvas.drawText(pontuacao, centralizaTexto(pontuacao, PONTUACAO), this.tela.getAltura() / 2 - 100, PONTUACAO);
        canvas.drawText(pontuacaoMaxima , centralizaTexto(pontuacaoMaxima, PONTUACAO), this.tela.getAltura() / 2, PONTUACAO);
        canvas.drawText(novamente, centralizaTexto(novamente, NOVAMENTE), (this.tela.getAltura() / 2) + 70, NOVAMENTE);
    }

    public int centralizaTexto(String texto, Paint corTexto){
        Rect limiteDoTexto = new Rect();
        corTexto.getTextBounds(texto, 0, texto.length(), limiteDoTexto);
        int centroHorizontal = this.tela.getLargura()/2 - (limiteDoTexto.right - limiteDoTexto.left)/2;
        return centroHorizontal;
    }

    private String textoPontuacao(){
        if(this.quantidadePontos == 1){
            return "Você passou por " + this.quantidadePontos + " cano";
        }else{
            return "Você passou por " + this.quantidadePontos + " canos";
        }
    }

    private String textoMelhorPontuacao(){
        if(this.pontuacao.getPontuacaoMaxima() == 1){
            return "Melhor pontuação: " + this.pontuacao.getPontuacaoMaxima() + " cano";
        }else{
            return "Melhor pontuação: " + this.pontuacao.getPontuacaoMaxima() + " canos";
        }
    }
}
