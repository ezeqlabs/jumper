package br.com.ezeqlabs.jumper.engine;

import android.content.Context;
import android.view.SurfaceView;

public class Game extends SurfaceView implements Runnable {
    private boolean estaRodando = true;
    public Game(Context context) {
        super(context);
    }

    @Override
    public void run() {
        while(this.estaRodando){

        }
    }

    public void cancela(){
        this.estaRodando = false;
    }

    public void inicia(){
        this.estaRodando = true;
    }
}
