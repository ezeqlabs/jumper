package br.com.ezeqlabs.jumper.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import br.com.ezeqlabs.jumper.elementos.Passaro;

public class Game extends SurfaceView implements Runnable {
    private boolean estaRodando = true;
    private final SurfaceHolder holder = getHolder();
    private Passaro passaro;

    public Game(Context context) {
        super(context);
        inicializaElementos();
    }

    @Override
    public void run() {
        while(this.estaRodando){
            if(!this.holder.getSurface().isValid()) continue;

            Canvas canvas = this.holder.lockCanvas();

            this.passaro.desenhaNo(canvas);
            this.passaro.cai();

            this.holder.unlockCanvasAndPost(canvas);
        }
    }

    public void cancela(){
        this.estaRodando = false;
    }

    public void inicia(){
        this.estaRodando = true;
    }

    private void inicializaElementos(){
        this.passaro = new Passaro();
    }
}
