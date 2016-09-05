package br.com.ezeqlabs.jumper.engine;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.api.GoogleApiClient;

import br.com.ezeqlabs.jumper.BoasVindasActivity;
import br.com.ezeqlabs.jumper.GameOverActivity;
import br.com.ezeqlabs.jumper.R;
import br.com.ezeqlabs.jumper.elementos.Canos;
import br.com.ezeqlabs.jumper.elementos.GameOver;
import br.com.ezeqlabs.jumper.elementos.Passaro;
import br.com.ezeqlabs.jumper.elementos.Pontuacao;
import br.com.ezeqlabs.jumper.helpers.Constantes;

public class Game extends SurfaceView implements Runnable, View.OnTouchListener {
    private boolean estaRodando = true;
    private final SurfaceHolder holder = getHolder();
    private Passaro passaro;
    private Bitmap background;
    private final Tela tela;
    private Canos canos;
    private Pontuacao pontuacao;
    private VerificadorDeColisao verificadorDeColisao;
    private final Activity activity;
    private final Som som;
    private Tempo tempo;
    private final SharedPreferences preferences;
    private InterstitialAd mInterstitialAd;
    private final GoogleApiClient googleApiClient;
    private Intent gameOver;

    public Game(final Activity activity, SharedPreferences preferences, GoogleApiClient googleApiClient) {
        super(activity);
        this.tela = new Tela(activity);
        this.activity = activity;
        this.som = new Som(activity);
        this.preferences = preferences;
        this.googleApiClient = googleApiClient;
        inicializaElementos();
        setOnTouchListener(this);
        trataPublicidade();

    }

    @Override
    public void run() {
        while(this.estaRodando){
            if(!this.holder.getSurface().isValid()) continue;

            Canvas canvas = this.holder.lockCanvas();

            this.tempo.passa();

            canvas.drawBitmap(this.background, 0, 0, null);
            this.passaro.desenhaNo(canvas);
            this.passaro.voa();

            this.canos.desenhaNo(canvas);
            this.canos.move();

            this.pontuacao.desenhaNo(canvas);

            if(this.verificadorDeColisao.temColisao()){
                this.som.toca(Som.COLISAO);
                cancela();

                gameOver.putExtra("pontuacao", this.pontuacao.getPontos());
                activity.startActivity(gameOver);
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
        this.tempo = new Tempo();
        this.background = Bitmap.createScaledBitmap(back, back.getWidth(), tela.getAltura(), false);
        this.passaro = new Passaro(this.activity, this.tela, this.som, this.tempo);
        this.pontuacao = new Pontuacao(this.activity, this.som, this.preferences, this.googleApiClient);
        this.canos = new Canos(this.activity, this.tela, this.pontuacao, this.passaro);
        this.verificadorDeColisao = new VerificadorDeColisao(this.passaro, this.canos);
        this.gameOver = new Intent(activity, GameOverActivity.class);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        this.passaro.pula();
        return false;
    }

    private void reiniciaJogo(){
        this.som.eliminaSom();
        this.background.recycle();
        Intent boasVindas = new Intent(this.activity, BoasVindasActivity.class);
        this.activity.startActivity(boasVindas);
        this.activity.finish();
    }

    private void trataPublicidade(){
        mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId(Constantes.ID_INTERSTITIAL_AD);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                activity.startActivity(gameOver);
            }
        });
        requestNewInterstitial();
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }
}