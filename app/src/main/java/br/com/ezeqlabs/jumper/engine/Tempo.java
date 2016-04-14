package br.com.ezeqlabs.jumper.engine;

public class Tempo {
    private double atual;

    public double atual(){
        return atual;
    }

    public void passa(){
        atual += 0.05;
    }

    public void reinicia(){
        atual = 0;
    }
}
