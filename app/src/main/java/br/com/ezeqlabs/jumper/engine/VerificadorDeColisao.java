package br.com.ezeqlabs.jumper.engine;

import br.com.ezeqlabs.jumper.elementos.Canos;
import br.com.ezeqlabs.jumper.elementos.Passaro;

public class VerificadorDeColisao {
    private final Passaro passaro;
    private final Canos canos;

    public VerificadorDeColisao(Passaro passaro, Canos canos){
        this.passaro = passaro;
        this.canos = canos;
    }

    public boolean temColisao(){
        return canos.temColisaoCom(passaro);
    }
}
