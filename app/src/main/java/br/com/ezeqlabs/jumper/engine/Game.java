package br.com.ezeqlabs.jumper.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import br.com.ezeqlabs.jumper.R;
import br.com.ezeqlabs.jumper.elementos.Canos;
import br.com.ezeqlabs.jumper.elementos.Passaro;
import br.com.ezeqlabs.jumper.elementos.Pontuacao;

public class Game extends SurfaceView implements Runnable, View.OnTouchListener {
    private boolean estaRodando = true;
    private final SurfaceHolder holder = getHolder();
    private Passaro passaro;
    private Bitmap background;
    private Tela tela;
    private Canos canos;
    private Pontuacao pontuacao;
    private VerificadorDeColisao verificadorDeColisao;

    public Game(Context context) {
        super(context);
        this.tela = new Tela(context);
        inicializaElementos();
        setOnTouchListener(this);
    }

    @Override
    public void run() {
        while(this.estaRodando){
            if(!this.holder.getSurface().isValid()) continue;

            Canvas canvas = this.holder.lockCanvas();

            canvas.drawBitmap(this.background, 0, 0, null);
            this.passaro.desenhaNo(canvas);
            this.passaro.cai();

            this.canos.desenhaNo(canvas);
            this.canos.move();

            this.pontuacao.desenhaNo(canvas);

            if(this.verificadorDeColisao.temColisao()){
                cancela();
            }

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
        Bitmap back = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        this.background = Bitmap.createScaledBitmap(back, back.getWidth(), tela.getAltura(), false);
        this.passaro = new Passaro(this.tela);
        this.pontuacao = new Pontuacao();
        this.canos = new Canos(this.tela, this.pontuacao);
        this.verificadorDeColisao = new VerificadorDeColisao(this.passaro, this.canos);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        this.passaro.pula();
        return false;
    }
}
