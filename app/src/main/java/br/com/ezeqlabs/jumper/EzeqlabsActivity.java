package br.com.ezeqlabs.jumper;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

public class EzeqlabsActivity extends Activity {
    private Scene scene;
    private Transition transition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >= 21){
            setContentView(R.layout.activity_ezeqlabs_init);
            RelativeLayout baseLayout = (RelativeLayout) findViewById(R.id.ezeq_layout);

            scene = Scene.getSceneForLayout(baseLayout, R.layout.activity_ezeqlabs, this);

            transition = new AutoTransition();
            transition.setDuration(1000);
            transition.setInterpolator(new AccelerateDecelerateInterpolator());
            TransitionManager.go(scene, transition);
        }else{
            setContentView(R.layout.activity_ezeqlabs);
        }

        mudaTela();

    }

    private void mudaTela(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent lugares = new Intent(EzeqlabsActivity.this, BoasVindasActivity.class);
                startActivity(lugares);
                finish();
            }
        }, 3000);
    }
}
