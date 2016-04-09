package br.com.ezeqlabs.jumper.elementos;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import br.com.ezeqlabs.jumper.engine.Tela;

public class Canos {
    private static final int QUANTIDADE_DE_CANOS = 5;
    private static final int DISTANCIA_ENTRE_CANOS = 250;
    private final List<Cano> canos = new ArrayList<Cano>();
    private Tela tela;
    private final Pontuacao pontuacao;

    public Canos(Tela tela, Pontuacao pontuacao){
        int posicao = 200;
        this.tela = tela;
        this.pontuacao = pontuacao;

        for(int i = 0; i < QUANTIDADE_DE_CANOS; i++){
            posicao += DISTANCIA_ENTRE_CANOS;
            this.canos.add(new Cano(tela, posicao));
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
            Cano cano = (Cano) iterator.next();
            cano.move();

            if(cano.saiuDaTela()){
                this.pontuacao.aumenta();

                iterator.remove();
                Cano outroCano = new Cano(this.tela, maiorPosicao() + DISTANCIA_ENTRE_CANOS);
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
        for(Cano cano : this.canos){
            if(cano.cruzouHorizontalmenteComPassaro() && cano.cruzouVerticalmenteCom(passaro)){
                return true;
            }
        }
        return false;
    }
}
