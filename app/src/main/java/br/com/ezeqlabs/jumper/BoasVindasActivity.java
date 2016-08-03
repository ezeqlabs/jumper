package br.com.ezeqlabs.jumper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import br.com.ezeqlabs.jumper.elementos.Pontuacao;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

public class BoasVindasActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boas_vindas);

        Button jogar = (Button) findViewById(R.id.menu_principal_jogar);

        montaTextos();
        trataBotao(jogar);
        montaAnuncio();
        montaTutorial(jogar);
    }

    private void montaTextos(){
        TextView texto = (TextView) findViewById(R.id.menu_principal_texto);
        Pontuacao pontuacao = new Pontuacao(null, getSharedPreferences(Pontuacao.JUMPER_PREF, 0));

        String textoPontuacao = getString(R.string.seu_recorde) + "\n" + pontuacao.getPontuacaoMaxima() + " " + getString(R.string.canos);
        texto.setText(textoPontuacao);
    }

    private void trataBotao(Button jogar){
        jogar.setText(getString(R.string.botao_jogar));
        jogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(BoasVindasActivity.this, MainActivity.class);
                startActivity(main);
                finish();
            }
        });
    }

    private void montaAnuncio(){
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void montaTutorial(Button jogar){
        new MaterialShowcaseView.Builder(this)
                .setTarget(jogar)
                .setDismissText(R.string.tutorial_botao)
                .setContentText(R.string.tutorial_texto)
                .setDelay(500)
                .singleUse("1")
                .show();
    }
}
