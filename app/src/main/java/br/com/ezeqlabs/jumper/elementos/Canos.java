package br.com.ezeqlabs.jumper.elementos;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import br.com.ezeqlabs.jumper.engine.Tela;

public class Canos {
    private static final int QUANTIDADE_DE_CANOS = 5;
    private static final int DISTANCIA_ENTRE_CANOS = 500;
    private final List<Cano> canos = new ArrayList<>();
    private final Tela tela;
    private final Pontuacao pontuacao;
    private final Context context;
    private final Passaro passaro;

    public Canos(Context context, Tela tela, Pontuacao pontuacao, Passaro passaro){
        this.context = context;
        int posicao = 500;
        this.tela = tela;
        this.pontuacao = pontuacao;
        this.passaro = passaro;

        for(int i = 0; i < QUANTIDADE_DE_CANOS; i++){
            posicao += DISTANCIA_ENTRE_CANOS;
            this.canos.add(new Cano(context, tela, posicao, passaro));
        }
    }

    public void desenhaNo(Canvas canvas){
        for(Cano cano : this.canos){
            cano.desenhaNo(canvas);
        }
    }

    public void move(){
        ListIterator<Cano> iterator = this.canos.listIterator();

        while(iterator.hasNext()){
            Cano cano = iterator.next();
            cano.move();

            if(cano.saiuDaTela()){
                this.pontuacao.aumenta();

                iterator.remove();
                cano.eliminaInutilizados();
                Cano outroCano = new Cano(this.context, this.tela, maiorPosicao() + DISTANCIA_ENTRE_CANOS, this.passaro);
                iterator.add(outroCano);
            }
        }
    }

    private int maiorPosicao(){
        int maximo = 0;
        for(Cano cano : this.canos){
            maximo = Math.max(cano.getPosicao(), maximo);
        }
        return maximo;
    }

    public boolean temColisaoCom(Passaro passaro){
        if(passaro.chegouNoChao()){
            return true;
        }
        
        for(Cano cano : this.canos){
            if(cano.cruzouHorizontalmenteComPassaro() && cano.cruzouVerticalmenteCom(passaro)){
                return true;
            }
        }
        return false;
    }

    public void limpaCanos(){
        for(Cano cano : this.canos){
            cano.eliminaInutilizados();
        }
        this.canos.clear();
    }
}
